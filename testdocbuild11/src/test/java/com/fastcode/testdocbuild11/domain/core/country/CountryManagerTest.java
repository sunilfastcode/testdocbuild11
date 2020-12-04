package com.fastcode.testdocbuild11.domain.core.country;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.fastcode.testdocbuild11.commons.logging.LoggingHelper;
import com.fastcode.testdocbuild11.commons.search.*;
import com.fastcode.testdocbuild11.domain.core.city.ICityRepository;
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
public class CountryManagerTest {

    @InjectMocks
    protected CountryManager _countryManager;

    @Mock
    protected ICountryRepository _countryRepository;

    @Mock
    protected ICityRepository _cityRepository;

    @Mock
    protected Logger loggerMock;

    @Mock
    protected LoggingHelper logHelper;

    protected static Integer ID = 15;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(_countryManager);
        when(logHelper.getLogger()).thenReturn(loggerMock);
        doNothing().when(loggerMock).error(anyString());
    }

    @Test
    public void findCountryById_IdIsNotNullAndIdExists_ReturnCountry() {
        CountryEntity country = mock(CountryEntity.class);

        Optional<CountryEntity> dbCountry = Optional.of((CountryEntity) country);
        Mockito.when(_countryRepository.findById(any(Integer.class))).thenReturn(dbCountry);
        Assertions.assertThat(_countryManager.findById(ID)).isEqualTo(country);
    }

    @Test
    public void findCountryById_IdIsNotNullAndIdDoesNotExist_ReturnNull() {
        Mockito
            .<Optional<CountryEntity>>when(_countryRepository.findById(any(Integer.class)))
            .thenReturn(Optional.empty());
        Assertions.assertThat(_countryManager.findById(ID)).isEqualTo(null);
    }

    @Test
    public void createCountry_CountryIsNotNullAndCountryDoesNotExist_StoreCountry() {
        CountryEntity country = mock(CountryEntity.class);
        Mockito.when(_countryRepository.save(any(CountryEntity.class))).thenReturn(country);
        Assertions.assertThat(_countryManager.create(country)).isEqualTo(country);
    }

    @Test
    public void deleteCountry_CountryExists_RemoveCountry() {
        CountryEntity country = mock(CountryEntity.class);
        _countryManager.delete(country);
        verify(_countryRepository).delete(country);
    }

    @Test
    public void updateCountry_CountryIsNotNullAndCountryExists_UpdateCountry() {
        CountryEntity country = mock(CountryEntity.class);
        Mockito.when(_countryRepository.save(any(CountryEntity.class))).thenReturn(country);
        Assertions.assertThat(_countryManager.update(country)).isEqualTo(country);
    }

    @Test
    public void findAll_PageableIsNotNull_ReturnPage() {
        Page<CountryEntity> country = mock(Page.class);
        Pageable pageable = mock(Pageable.class);
        Predicate predicate = mock(Predicate.class);

        Mockito.when(_countryRepository.findAll(any(Predicate.class), any(Pageable.class))).thenReturn(country);
        Assertions.assertThat(_countryManager.findAll(predicate, pageable)).isEqualTo(country);
    }
}
