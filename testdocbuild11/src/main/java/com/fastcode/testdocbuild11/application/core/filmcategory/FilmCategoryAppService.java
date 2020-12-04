package com.fastcode.testdocbuild11.application.core.filmcategory;

import com.fastcode.testdocbuild11.application.core.filmcategory.dto.*;
import com.fastcode.testdocbuild11.commons.logging.LoggingHelper;
import com.fastcode.testdocbuild11.commons.search.*;
import com.fastcode.testdocbuild11.domain.core.category.CategoryEntity;
import com.fastcode.testdocbuild11.domain.core.category.ICategoryManager;
import com.fastcode.testdocbuild11.domain.core.film.FilmEntity;
import com.fastcode.testdocbuild11.domain.core.film.IFilmManager;
import com.fastcode.testdocbuild11.domain.core.filmcategory.FilmCategoryEntity;
import com.fastcode.testdocbuild11.domain.core.filmcategory.FilmCategoryId;
import com.fastcode.testdocbuild11.domain.core.filmcategory.IFilmCategoryManager;
import com.fastcode.testdocbuild11.domain.core.filmcategory.QFilmCategoryEntity;
import com.querydsl.core.BooleanBuilder;
import java.time.*;
import java.util.*;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("filmCategoryAppService")
@RequiredArgsConstructor
public class FilmCategoryAppService implements IFilmCategoryAppService {

    @Qualifier("filmCategoryManager")
    @NonNull
    protected final IFilmCategoryManager _filmCategoryManager;

    @Qualifier("categoryManager")
    @NonNull
    protected final ICategoryManager _categoryManager;

    @Qualifier("filmManager")
    @NonNull
    protected final IFilmManager _filmManager;

    @Qualifier("IFilmCategoryMapperImpl")
    @NonNull
    protected final IFilmCategoryMapper mapper;

    @NonNull
    protected final LoggingHelper logHelper;

    @Transactional(propagation = Propagation.REQUIRED)
    public CreateFilmCategoryOutput create(CreateFilmCategoryInput input) {
        FilmCategoryEntity filmCategory = mapper.createFilmCategoryInputToFilmCategoryEntity(input);
        CategoryEntity foundCategory = null;
        FilmEntity foundFilm = null;
        if (input.getCategoryId() != null) {
            foundCategory = _categoryManager.findById(Integer.parseInt(input.getCategoryId().toString()));

            if (foundCategory != null) {
                filmCategory.setCategory(foundCategory);
            }
        }
        if (input.getFilmId() != null) {
            foundFilm = _filmManager.findById(Integer.parseInt(input.getFilmId().toString()));

            if (foundFilm != null) {
                filmCategory.setFilm(foundFilm);
            }
        }

        FilmCategoryEntity createdFilmCategory = _filmCategoryManager.create(filmCategory);
        return mapper.filmCategoryEntityToCreateFilmCategoryOutput(createdFilmCategory);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public UpdateFilmCategoryOutput update(FilmCategoryId filmCategoryId, UpdateFilmCategoryInput input) {
        FilmCategoryEntity filmCategory = mapper.updateFilmCategoryInputToFilmCategoryEntity(input);
        CategoryEntity foundCategory = null;
        FilmEntity foundFilm = null;

        if (input.getCategoryId() != null) {
            foundCategory = _categoryManager.findById(Integer.parseInt(input.getCategoryId().toString()));

            if (foundCategory != null) {
                filmCategory.setCategory(foundCategory);
            }
        }

        if (input.getFilmId() != null) {
            foundFilm = _filmManager.findById(Integer.parseInt(input.getFilmId().toString()));

            if (foundFilm != null) {
                filmCategory.setFilm(foundFilm);
            }
        }

        FilmCategoryEntity updatedFilmCategory = _filmCategoryManager.update(filmCategory);
        return mapper.filmCategoryEntityToUpdateFilmCategoryOutput(updatedFilmCategory);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void delete(FilmCategoryId filmCategoryId) {
        FilmCategoryEntity existing = _filmCategoryManager.findById(filmCategoryId);
        _filmCategoryManager.delete(existing);
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public FindFilmCategoryByIdOutput findById(FilmCategoryId filmCategoryId) {
        FilmCategoryEntity foundFilmCategory = _filmCategoryManager.findById(filmCategoryId);
        if (foundFilmCategory == null) return null;

        return mapper.filmCategoryEntityToFindFilmCategoryByIdOutput(foundFilmCategory);
    }

    //Category
    // ReST API Call - GET /filmCategory/1/category
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public GetCategoryOutput getCategory(FilmCategoryId filmCategoryId) {
        FilmCategoryEntity foundFilmCategory = _filmCategoryManager.findById(filmCategoryId);
        if (foundFilmCategory == null) {
            logHelper.getLogger().error("There does not exist a filmCategory wth a id=%s", filmCategoryId);
            return null;
        }
        CategoryEntity re = _filmCategoryManager.getCategory(filmCategoryId);
        return mapper.categoryEntityToGetCategoryOutput(re, foundFilmCategory);
    }

    //Film
    // ReST API Call - GET /filmCategory/1/film
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public GetFilmOutput getFilm(FilmCategoryId filmCategoryId) {
        FilmCategoryEntity foundFilmCategory = _filmCategoryManager.findById(filmCategoryId);
        if (foundFilmCategory == null) {
            logHelper.getLogger().error("There does not exist a filmCategory wth a id=%s", filmCategoryId);
            return null;
        }
        FilmEntity re = _filmCategoryManager.getFilm(filmCategoryId);
        return mapper.filmEntityToGetFilmOutput(re, foundFilmCategory);
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public List<FindFilmCategoryByIdOutput> find(SearchCriteria search, Pageable pageable) throws Exception {
        Page<FilmCategoryEntity> foundFilmCategory = _filmCategoryManager.findAll(search(search), pageable);
        List<FilmCategoryEntity> filmCategoryList = foundFilmCategory.getContent();
        Iterator<FilmCategoryEntity> filmCategoryIterator = filmCategoryList.iterator();
        List<FindFilmCategoryByIdOutput> output = new ArrayList<>();

        while (filmCategoryIterator.hasNext()) {
            FilmCategoryEntity filmCategory = filmCategoryIterator.next();
            output.add(mapper.filmCategoryEntityToFindFilmCategoryByIdOutput(filmCategory));
        }
        return output;
    }

    protected BooleanBuilder search(SearchCriteria search) throws Exception {
        QFilmCategoryEntity filmCategory = QFilmCategoryEntity.filmCategoryEntity;
        if (search != null) {
            Map<String, SearchFields> map = new HashMap<>();
            for (SearchFields fieldDetails : search.getFields()) {
                map.put(fieldDetails.getFieldName(), fieldDetails);
            }
            List<String> keysList = new ArrayList<String>(map.keySet());
            checkProperties(keysList);
            return searchKeyValuePair(filmCategory, map, search.getJoinColumns());
        }
        return null;
    }

    protected void checkProperties(List<String> list) throws Exception {
        for (int i = 0; i < list.size(); i++) {
            if (
                !(
                    list.get(i).replace("%20", "").trim().equals("categoryId") ||
                    list.get(i).replace("%20", "").trim().equals("filmId") ||
                    list.get(i).replace("%20", "").trim().equals("lastUpdate")
                )
            ) {
                throw new Exception("Wrong URL Format: Property " + list.get(i) + " not found!");
            }
        }
    }

    protected BooleanBuilder searchKeyValuePair(
        QFilmCategoryEntity filmCategory,
        Map<String, SearchFields> map,
        Map<String, String> joinColumns
    ) {
        BooleanBuilder builder = new BooleanBuilder();

        for (Map.Entry<String, SearchFields> details : map.entrySet()) {
            if (details.getKey().replace("%20", "").trim().equals("categoryId")) {
                if (
                    details.getValue().getOperator().equals("equals") &&
                    StringUtils.isNumeric(details.getValue().getSearchValue())
                ) builder.and(filmCategory.categoryId.eq(Short.valueOf(details.getValue().getSearchValue()))); else if (
                    details.getValue().getOperator().equals("notEqual") &&
                    StringUtils.isNumeric(details.getValue().getSearchValue())
                ) builder.and(filmCategory.categoryId.ne(Short.valueOf(details.getValue().getSearchValue()))); else if (
                    details.getValue().getOperator().equals("range")
                ) {
                    if (
                        StringUtils.isNumeric(details.getValue().getStartingValue()) &&
                        StringUtils.isNumeric(details.getValue().getEndingValue())
                    ) builder.and(
                        filmCategory.categoryId.between(
                            Short.valueOf(details.getValue().getStartingValue()),
                            Short.valueOf(details.getValue().getEndingValue())
                        )
                    ); else if (StringUtils.isNumeric(details.getValue().getStartingValue())) builder.and(
                        filmCategory.categoryId.goe(Short.valueOf(details.getValue().getStartingValue()))
                    ); else if (StringUtils.isNumeric(details.getValue().getEndingValue())) builder.and(
                        filmCategory.categoryId.loe(Short.valueOf(details.getValue().getEndingValue()))
                    );
                }
            }
            if (details.getKey().replace("%20", "").trim().equals("filmId")) {
                if (
                    details.getValue().getOperator().equals("equals") &&
                    StringUtils.isNumeric(details.getValue().getSearchValue())
                ) builder.and(filmCategory.filmId.eq(Short.valueOf(details.getValue().getSearchValue()))); else if (
                    details.getValue().getOperator().equals("notEqual") &&
                    StringUtils.isNumeric(details.getValue().getSearchValue())
                ) builder.and(filmCategory.filmId.ne(Short.valueOf(details.getValue().getSearchValue()))); else if (
                    details.getValue().getOperator().equals("range")
                ) {
                    if (
                        StringUtils.isNumeric(details.getValue().getStartingValue()) &&
                        StringUtils.isNumeric(details.getValue().getEndingValue())
                    ) builder.and(
                        filmCategory.filmId.between(
                            Short.valueOf(details.getValue().getStartingValue()),
                            Short.valueOf(details.getValue().getEndingValue())
                        )
                    ); else if (StringUtils.isNumeric(details.getValue().getStartingValue())) builder.and(
                        filmCategory.filmId.goe(Short.valueOf(details.getValue().getStartingValue()))
                    ); else if (StringUtils.isNumeric(details.getValue().getEndingValue())) builder.and(
                        filmCategory.filmId.loe(Short.valueOf(details.getValue().getEndingValue()))
                    );
                }
            }
            if (details.getKey().replace("%20", "").trim().equals("lastUpdate")) {
                if (
                    details.getValue().getOperator().equals("equals") &&
                    SearchUtils.stringToLocalDateTime(details.getValue().getSearchValue()) != null
                ) builder.and(
                    filmCategory.lastUpdate.eq(SearchUtils.stringToLocalDateTime(details.getValue().getSearchValue()))
                ); else if (
                    details.getValue().getOperator().equals("notEqual") &&
                    SearchUtils.stringToLocalDateTime(details.getValue().getSearchValue()) != null
                ) builder.and(
                    filmCategory.lastUpdate.ne(SearchUtils.stringToLocalDateTime(details.getValue().getSearchValue()))
                ); else if (details.getValue().getOperator().equals("range")) {
                    LocalDateTime startLocalDateTime = SearchUtils.stringToLocalDateTime(
                        details.getValue().getStartingValue()
                    );
                    LocalDateTime endLocalDateTime = SearchUtils.stringToLocalDateTime(
                        details.getValue().getEndingValue()
                    );
                    if (startLocalDateTime != null && endLocalDateTime != null) builder.and(
                        filmCategory.lastUpdate.between(startLocalDateTime, endLocalDateTime)
                    ); else if (endLocalDateTime != null) builder.and(
                        filmCategory.lastUpdate.loe(endLocalDateTime)
                    ); else if (startLocalDateTime != null) builder.and(
                        filmCategory.lastUpdate.goe(startLocalDateTime)
                    );
                }
            }
        }

        for (Map.Entry<String, String> joinCol : joinColumns.entrySet()) {
            if (joinCol != null && joinCol.getKey().equals("categoryId")) {
                builder.and(filmCategory.category.categoryId.eq(Integer.parseInt(joinCol.getValue())));
            }
        }
        for (Map.Entry<String, String> joinCol : joinColumns.entrySet()) {
            if (joinCol != null && joinCol.getKey().equals("filmId")) {
                builder.and(filmCategory.film.filmId.eq(Integer.parseInt(joinCol.getValue())));
            }
        }
        return builder;
    }

    public FilmCategoryId parseFilmCategoryKey(String keysString) {
        String[] keyEntries = keysString.split(",");
        FilmCategoryId filmCategoryId = new FilmCategoryId();

        Map<String, String> keyMap = new HashMap<String, String>();
        if (keyEntries.length > 1) {
            for (String keyEntry : keyEntries) {
                String[] keyEntryArr = keyEntry.split("=");
                if (keyEntryArr.length > 1) {
                    keyMap.put(keyEntryArr[0], keyEntryArr[1]);
                } else {
                    return null;
                }
            }
        } else {
            String[] keyEntryArr = keysString.split("=");
            if (keyEntryArr.length > 1) {
                keyMap.put(keyEntryArr[0], keyEntryArr[1]);
            } else return null;
        }

        filmCategoryId.setCategoryId(Short.valueOf(keyMap.get("categoryId")));
        filmCategoryId.setFilmId(Short.valueOf(keyMap.get("filmId")));
        return filmCategoryId;
    }
}
