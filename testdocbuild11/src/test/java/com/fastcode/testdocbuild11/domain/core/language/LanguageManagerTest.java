package com.fastcode.testdocbuild11.domain.core.language;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.fastcode.testdocbuild11.commons.logging.LoggingHelper;
import com.fastcode.testdocbuild11.commons.search.*;
import com.fastcode.testdocbuild11.domain.core.film.IFilmRepository;
import com.querydsl.core.types.Predicate;
import java.time.*;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
public class LanguageManagerTest {

    @InjectMocks
    protected LanguageManager _languageManager;

    @Mock
    protected ILanguageRepository _languageRepository;

    @Mock
    protected IFilmRepository _filmRepository;

    @Mock
    protected Logger loggerMock;

    @Mock
    protected LoggingHelper logHelper;

    protected static Integer ID = 15;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(_languageManager);
        when(logHelper.getLogger()).thenReturn(loggerMock);
        doNothing().when(loggerMock).error(anyString());
    }

    @Test
    public void findLanguageById_IdIsNotNullAndIdExists_ReturnLanguage() {
        LanguageEntity language = mock(LanguageEntity.class);

        Optional<LanguageEntity> dbLanguage = Optional.of((LanguageEntity) language);
        Mockito.when(_languageRepository.findById(any(Integer.class))).thenReturn(dbLanguage);
        Assertions.assertThat(_languageManager.findById(ID)).isEqualTo(language);
    }

    @Test
    public void findLanguageById_IdIsNotNullAndIdDoesNotExist_ReturnNull() {
        Mockito
            .<Optional<LanguageEntity>>when(_languageRepository.findById(any(Integer.class)))
            .thenReturn(Optional.empty());
        Assertions.assertThat(_languageManager.findById(ID)).isEqualTo(null);
    }

    @Test
    public void createLanguage_LanguageIsNotNullAndLanguageDoesNotExist_StoreLanguage() {
        LanguageEntity language = mock(LanguageEntity.class);
        Mockito.when(_languageRepository.save(any(LanguageEntity.class))).thenReturn(language);
        Assertions.assertThat(_languageManager.create(language)).isEqualTo(language);
    }

    @Test
    public void deleteLanguage_LanguageExists_RemoveLanguage() {
        LanguageEntity language = mock(LanguageEntity.class);
        _languageManager.delete(language);
        verify(_languageRepository).delete(language);
    }

    @Test
    public void updateLanguage_LanguageIsNotNullAndLanguageExists_UpdateLanguage() {
        LanguageEntity language = mock(LanguageEntity.class);
        Mockito.when(_languageRepository.save(any(LanguageEntity.class))).thenReturn(language);
        Assertions.assertThat(_languageManager.update(language)).isEqualTo(language);
    }

    @Test
    public void findAll_PageableIsNotNull_ReturnPage() {
        Page<LanguageEntity> language = mock(Page.class);
        Pageable pageable = mock(Pageable.class);
        Predicate predicate = mock(Predicate.class);

        Mockito.when(_languageRepository.findAll(any(Predicate.class), any(Pageable.class))).thenReturn(language);
        Assertions.assertThat(_languageManager.findAll(predicate, pageable)).isEqualTo(language);
    }
}
