package com.fastcode.testdocbuild11.application.core.authorization.user;

import com.fastcode.testdocbuild11.addons.reporting.domain.dashboardversion.*;
import com.fastcode.testdocbuild11.addons.reporting.domain.dashboardversionreport.*;
import com.fastcode.testdocbuild11.addons.reporting.domain.reportversion.*;
import com.fastcode.testdocbuild11.application.core.authorization.user.dto.*;
import com.fastcode.testdocbuild11.commons.logging.LoggingHelper;
import com.fastcode.testdocbuild11.commons.search.*;
import com.fastcode.testdocbuild11.domain.core.authorization.user.IUserManager;
import com.fastcode.testdocbuild11.domain.core.authorization.user.QUserEntity;
import com.fastcode.testdocbuild11.domain.core.authorization.user.UserEntity;
import com.fastcode.testdocbuild11.domain.core.authorization.userpreference.IUserpreferenceManager;
import com.fastcode.testdocbuild11.domain.core.authorization.userpreference.UserpreferenceEntity;
import com.fastcode.testdocbuild11.security.SecurityUtils;
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

@Service("userAppService")
@RequiredArgsConstructor
public class UserAppService implements IUserAppService {

    @Qualifier("dashboardversionManager")
    @NonNull
    protected final IDashboardversionManager _dashboardversionManager;

    @Qualifier("reportversionManager")
    @NonNull
    protected final IReportversionManager _reportversionManager;

    @Qualifier("dashboardversionreportManager")
    @NonNull
    protected final IDashboardversionreportManager _reportDashboardManager;

    public static final long PASSWORD_TOKEN_EXPIRATION_TIME = 3_600_000; // 1 hour

    @Qualifier("userManager")
    @NonNull
    protected final IUserManager _userManager;

    @Qualifier("userpreferenceManager")
    @NonNull
    protected final IUserpreferenceManager _userpreferenceManager;

    @Qualifier("IUserMapperImpl")
    @NonNull
    protected final IUserMapper mapper;

    @NonNull
    protected final LoggingHelper logHelper;

    @Transactional(propagation = Propagation.REQUIRED)
    public CreateUserOutput create(CreateUserInput input) {
        UserEntity user = mapper.createUserInputToUserEntity(input);

        UserEntity createdUser = _userManager.create(user);
        UserpreferenceEntity userPreference = createDefaultUserPreference(createdUser);
        return mapper.userEntityToCreateUserOutput(createdUser, userPreference);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public UpdateUserOutput update(Long userId, UpdateUserInput input) {
        UserEntity user = mapper.updateUserInputToUserEntity(input);

        UserEntity updatedUser = _userManager.update(user);
        return mapper.userEntityToUpdateUserOutput(updatedUser);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void delete(Long userId) {
        UserEntity existing = _userManager.findById(userId);

        List<DashboardversionreportEntity> dvrList = _reportDashboardManager.findByUserId(userId);
        for (DashboardversionreportEntity dvr : dvrList) {
            _reportDashboardManager.delete(dvr);
        }

        List<DashboardversionEntity> dvList = _dashboardversionManager.findByUserId(userId);
        for (DashboardversionEntity du : dvList) {
            _dashboardversionManager.delete(du);
        }

        List<ReportversionEntity> rvList = _reportversionManager.findByUserId(userId);
        for (ReportversionEntity rv : rvList) {
            _reportversionManager.delete(rv);
        }

        UserpreferenceEntity userpreference = _userpreferenceManager.findById(userId);
        _userpreferenceManager.delete(userpreference);
        _userManager.delete(existing);
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public FindUserByIdOutput findById(Long userId) {
        UserEntity foundUser = _userManager.findById(userId);
        if (foundUser == null) return null;

        UserpreferenceEntity userPreference = _userpreferenceManager.findById(userId);

        return mapper.userEntityToFindUserByIdOutput(foundUser, userPreference);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public UserpreferenceEntity createDefaultUserPreference(UserEntity user) {
        UserpreferenceEntity userpreference = new UserpreferenceEntity();
        userpreference.setTheme("default-theme");
        userpreference.setLanguage("en");
        userpreference.setId(user.getId());
        userpreference.setUser(user);

        return _userpreferenceManager.create(userpreference);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void updateTheme(UserEntity user, String theme) {
        UserpreferenceEntity userpreference = _userpreferenceManager.findById(user.getId());
        userpreference.setTheme(theme);

        _userpreferenceManager.update(userpreference);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void updateLanguage(UserEntity user, String language) {
        UserpreferenceEntity userpreference = _userpreferenceManager.findById(user.getId());
        userpreference.setLanguage(language);

        _userpreferenceManager.update(userpreference);
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public FindUserWithAllFieldsByIdOutput findWithAllFieldsById(Long userId) {
        UserEntity foundUser = _userManager.findById(userId);
        if (foundUser == null) return null;

        return mapper.userEntityToFindUserWithAllFieldsByIdOutput(foundUser);
    }

    public UserProfile getProfile(FindUserByIdOutput user) {
        return mapper.findUserByIdOutputToUserProfile(user);
    }

    public UserProfile updateUserProfile(FindUserWithAllFieldsByIdOutput user, UserProfile userProfile) {
        UpdateUserInput userInput = mapper.findUserWithAllFieldsByIdOutputAndUserProfileToUpdateUserInput(
            user,
            userProfile
        );
        UpdateUserOutput output = update(user.getId(), userInput);

        return mapper.updateUserOutputToUserProfile(output);
    }

    @Transactional(readOnly = true)
    public UserEntity getUser() {
        return _userManager.findByUserName(SecurityUtils.getCurrentUserLogin().orElse(null));
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public FindUserByNameOutput findByUserName(String userName) {
        UserEntity foundUser = _userManager.findByUserName(userName);
        if (foundUser == null) {
            return null;
        }

        return mapper.userEntityToFindUserByNameOutput(foundUser);
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public FindUserByNameOutput findByEmailAddress(String emailAddress) {
        UserEntity foundUser = _userManager.findByEmailAddress(emailAddress);
        if (foundUser == null) {
            return null;
        }

        return mapper.userEntityToFindUserByNameOutput(foundUser);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void updateUserData(FindUserWithAllFieldsByIdOutput user) {
        UserEntity foundUser = mapper.findUserWithAllFieldsByIdOutputToUserEntity(user);
        _userManager.update(foundUser);
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public List<FindUserByIdOutput> find(SearchCriteria search, Pageable pageable) throws Exception {
        Page<UserEntity> foundUser = _userManager.findAll(search(search), pageable);
        List<UserEntity> userList = foundUser.getContent();
        Iterator<UserEntity> userIterator = userList.iterator();
        List<FindUserByIdOutput> output = new ArrayList<>();

        while (userIterator.hasNext()) {
            UserEntity user = userIterator.next();
            UserpreferenceEntity userPreference = _userpreferenceManager.findById(user.getId());
            output.add(mapper.userEntityToFindUserByIdOutput(user, userPreference));
        }
        return output;
    }

    protected BooleanBuilder search(SearchCriteria search) throws Exception {
        QUserEntity user = QUserEntity.userEntity;
        if (search != null) {
            Map<String, SearchFields> map = new HashMap<>();
            for (SearchFields fieldDetails : search.getFields()) {
                map.put(fieldDetails.getFieldName(), fieldDetails);
            }
            List<String> keysList = new ArrayList<String>(map.keySet());
            checkProperties(keysList);
            return searchKeyValuePair(user, map, search.getJoinColumns());
        }
        return null;
    }

    protected void checkProperties(List<String> list) throws Exception {
        for (int i = 0; i < list.size(); i++) {
            if (
                !(
                    list.get(i).replace("%20", "").trim().equals("emailAddress") ||
                    list.get(i).replace("%20", "").trim().equals("firstName") ||
                    list.get(i).replace("%20", "").trim().equals("id") ||
                    list.get(i).replace("%20", "").trim().equals("isActive") ||
                    list.get(i).replace("%20", "").trim().equals("isEmailConfirmed") ||
                    list.get(i).replace("%20", "").trim().equals("lastName") ||
                    list.get(i).replace("%20", "").trim().equals("password") ||
                    list.get(i).replace("%20", "").trim().equals("phoneNumber") ||
                    list.get(i).replace("%20", "").trim().equals("userName")
                )
            ) {
                throw new Exception("Wrong URL Format: Property " + list.get(i) + " not found!");
            }
        }
    }

    protected BooleanBuilder searchKeyValuePair(
        QUserEntity user,
        Map<String, SearchFields> map,
        Map<String, String> joinColumns
    ) {
        BooleanBuilder builder = new BooleanBuilder();

        for (Map.Entry<String, SearchFields> details : map.entrySet()) {
            if (details.getKey().replace("%20", "").trim().equals("emailAddress")) {
                if (details.getValue().getOperator().equals("contains")) builder.and(
                    user.emailAddress.likeIgnoreCase("%" + details.getValue().getSearchValue() + "%")
                ); else if (details.getValue().getOperator().equals("equals")) builder.and(
                    user.emailAddress.eq(details.getValue().getSearchValue())
                ); else if (details.getValue().getOperator().equals("notEqual")) builder.and(
                    user.emailAddress.ne(details.getValue().getSearchValue())
                );
            }
            if (details.getKey().replace("%20", "").trim().equals("firstName")) {
                if (details.getValue().getOperator().equals("contains")) builder.and(
                    user.firstName.likeIgnoreCase("%" + details.getValue().getSearchValue() + "%")
                ); else if (details.getValue().getOperator().equals("equals")) builder.and(
                    user.firstName.eq(details.getValue().getSearchValue())
                ); else if (details.getValue().getOperator().equals("notEqual")) builder.and(
                    user.firstName.ne(details.getValue().getSearchValue())
                );
            }
            if (details.getKey().replace("%20", "").trim().equals("id")) {
                if (
                    details.getValue().getOperator().equals("equals") &&
                    StringUtils.isNumeric(details.getValue().getSearchValue())
                ) builder.and(user.id.eq(Long.valueOf(details.getValue().getSearchValue()))); else if (
                    details.getValue().getOperator().equals("notEqual") &&
                    StringUtils.isNumeric(details.getValue().getSearchValue())
                ) builder.and(user.id.ne(Long.valueOf(details.getValue().getSearchValue()))); else if (
                    details.getValue().getOperator().equals("range")
                ) {
                    if (
                        StringUtils.isNumeric(details.getValue().getStartingValue()) &&
                        StringUtils.isNumeric(details.getValue().getEndingValue())
                    ) builder.and(
                        user.id.between(
                            Long.valueOf(details.getValue().getStartingValue()),
                            Long.valueOf(details.getValue().getEndingValue())
                        )
                    ); else if (StringUtils.isNumeric(details.getValue().getStartingValue())) builder.and(
                        user.id.goe(Long.valueOf(details.getValue().getStartingValue()))
                    ); else if (StringUtils.isNumeric(details.getValue().getEndingValue())) builder.and(
                        user.id.loe(Long.valueOf(details.getValue().getEndingValue()))
                    );
                }
            }
            if (details.getKey().replace("%20", "").trim().equals("isActive")) {
                if (
                    details.getValue().getOperator().equals("equals") &&
                    (
                        details.getValue().getSearchValue().equalsIgnoreCase("true") ||
                        details.getValue().getSearchValue().equalsIgnoreCase("false")
                    )
                ) builder.and(user.isActive.eq(Boolean.parseBoolean(details.getValue().getSearchValue()))); else if (
                    details.getValue().getOperator().equals("notEqual") &&
                    (
                        details.getValue().getSearchValue().equalsIgnoreCase("true") ||
                        details.getValue().getSearchValue().equalsIgnoreCase("false")
                    )
                ) builder.and(user.isActive.ne(Boolean.parseBoolean(details.getValue().getSearchValue())));
            }
            if (details.getKey().replace("%20", "").trim().equals("isEmailConfirmed")) {
                if (
                    details.getValue().getOperator().equals("equals") &&
                    (
                        details.getValue().getSearchValue().equalsIgnoreCase("true") ||
                        details.getValue().getSearchValue().equalsIgnoreCase("false")
                    )
                ) builder.and(
                    user.isEmailConfirmed.eq(Boolean.parseBoolean(details.getValue().getSearchValue()))
                ); else if (
                    details.getValue().getOperator().equals("notEqual") &&
                    (
                        details.getValue().getSearchValue().equalsIgnoreCase("true") ||
                        details.getValue().getSearchValue().equalsIgnoreCase("false")
                    )
                ) builder.and(user.isEmailConfirmed.ne(Boolean.parseBoolean(details.getValue().getSearchValue())));
            }
            if (details.getKey().replace("%20", "").trim().equals("lastName")) {
                if (details.getValue().getOperator().equals("contains")) builder.and(
                    user.lastName.likeIgnoreCase("%" + details.getValue().getSearchValue() + "%")
                ); else if (details.getValue().getOperator().equals("equals")) builder.and(
                    user.lastName.eq(details.getValue().getSearchValue())
                ); else if (details.getValue().getOperator().equals("notEqual")) builder.and(
                    user.lastName.ne(details.getValue().getSearchValue())
                );
            }
            if (details.getKey().replace("%20", "").trim().equals("password")) {
                if (details.getValue().getOperator().equals("contains")) builder.and(
                    user.password.likeIgnoreCase("%" + details.getValue().getSearchValue() + "%")
                ); else if (details.getValue().getOperator().equals("equals")) builder.and(
                    user.password.eq(details.getValue().getSearchValue())
                ); else if (details.getValue().getOperator().equals("notEqual")) builder.and(
                    user.password.ne(details.getValue().getSearchValue())
                );
            }
            if (details.getKey().replace("%20", "").trim().equals("phoneNumber")) {
                if (details.getValue().getOperator().equals("contains")) builder.and(
                    user.phoneNumber.likeIgnoreCase("%" + details.getValue().getSearchValue() + "%")
                ); else if (details.getValue().getOperator().equals("equals")) builder.and(
                    user.phoneNumber.eq(details.getValue().getSearchValue())
                ); else if (details.getValue().getOperator().equals("notEqual")) builder.and(
                    user.phoneNumber.ne(details.getValue().getSearchValue())
                );
            }
            if (details.getKey().replace("%20", "").trim().equals("userName")) {
                if (details.getValue().getOperator().equals("contains")) builder.and(
                    user.userName.likeIgnoreCase("%" + details.getValue().getSearchValue() + "%")
                ); else if (details.getValue().getOperator().equals("equals")) builder.and(
                    user.userName.eq(details.getValue().getSearchValue())
                ); else if (details.getValue().getOperator().equals("notEqual")) builder.and(
                    user.userName.ne(details.getValue().getSearchValue())
                );
            }
        }

        return builder;
    }

    public Map<String, String> parseDashboardsJoinColumn(String keysString) {
        Map<String, String> joinColumnMap = new HashMap<String, String>();
        joinColumnMap.put("userId", keysString);

        return joinColumnMap;
    }

    public Map<String, String> parseDashboardversionsJoinColumn(String keysString) {
        Map<String, String> joinColumnMap = new HashMap<String, String>();
        joinColumnMap.put("userId", keysString);

        return joinColumnMap;
    }

    public Map<String, String> parseReportsJoinColumn(String keysString) {
        Map<String, String> joinColumnMap = new HashMap<String, String>();
        joinColumnMap.put("userId", keysString);

        return joinColumnMap;
    }

    public Map<String, String> parseReportversionsJoinColumn(String keysString) {
        Map<String, String> joinColumnMap = new HashMap<String, String>();
        joinColumnMap.put("userId", keysString);

        return joinColumnMap;
    }

    public Map<String, String> parseUserpermissionsJoinColumn(String keysString) {
        Map<String, String> joinColumnMap = new HashMap<String, String>();
        joinColumnMap.put("userId", keysString);

        return joinColumnMap;
    }

    public Map<String, String> parseUserrolesJoinColumn(String keysString) {
        Map<String, String> joinColumnMap = new HashMap<String, String>();
        joinColumnMap.put("userId", keysString);

        return joinColumnMap;
    }
}
