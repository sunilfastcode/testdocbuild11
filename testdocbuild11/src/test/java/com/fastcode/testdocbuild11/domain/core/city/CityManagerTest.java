package com.fastcode.testdocbuild11.domain.core.city;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.fastcode.testdocbuild11.commons.logging.LoggingHelper;
import com.fastcode.testdocbuild11.commons.search.*;
import com.fastcode.testdocbuild11.domain.core.address.IAddressRepository;
import com.fastcode.testdocbuild11.domain.core.country.CountryEntity;
import com.fastcode.testdocbuild11.domain.core.country.ICountryRepository;
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
public class CityManagerTest {

    @InjectMocks
    protected CityManager _cityManager;

    @Mock
    protected ICityRepository _cityRepository;

    @Mock
    protected IAddressRepository _addressRepository;

    @Mock
    protected ICountryRepository _countryRepository;

    @Mock
    protected Logger loggerMock;

    @Mock
    protected LoggingHelper logHelper;

    protected static Integer ID = 15;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(_cityManager);
        when(logHelper.getLogger()).thenReturn(loggerMock);
        doNothing().when(loggerMock).error(anyString());
    }

    @Test
    public void findCityById_IdIsNotNullAndIdExists_ReturnCity() {
        CityEntity city = mock(CityEntity.class);

        Optional<CityEntity> dbCity = Optional.of((CityEntity) city);
        Mockito.when(_cityRepository.findById(any(Integer.class))).thenReturn(dbCity);
        Assertions.assertThat(_cityManager.findById(ID)).isEqualTo(city);
    }

    @Test
    public void findCityById_IdIsNotNullAndIdDoesNotExist_ReturnNull() {
        Mockito.<Optional<CityEntity>>when(_cityRepository.findById(any(Integer.class))).thenReturn(Optional.empty());
        Assertions.assertThat(_cityManager.findById(ID)).isEqualTo(null);
    }

    @Test
    public void createCity_CityIsNotNullAndCityDoesNotExist_StoreCity() {
        CityEntity city = mock(CityEntity.class);
        Mockito.when(_cityRepository.save(any(CityEntity.class))).thenReturn(city);
        Assertions.assertThat(_cityManager.create(city)).isEqualTo(city);
    }

    @Test
    public void deleteCity_CityExists_RemoveCity() {
        CityEntity city = mock(CityEntity.class);
        _cityManager.delete(city);
        verify(_cityRepository).delete(city);
    }

    @Test
    public void updateCity_CityIsNotNullAndCityExists_UpdateCity() {
        CityEntity city = mock(CityEntity.class);
        Mockito.when(_cityRepository.save(any(CityEntity.class))).thenReturn(city);
        Assertions.assertThat(_cityManager.update(city)).isEqualTo(city);
    }

    @Test
    public void findAll_PageableIsNotNull_ReturnPage() {
        Page<CityEntity> city = mock(Page.class);
        Pageable pageable = mock(Pageable.class);
        Predicate predicate = mock(Predicate.class);

        Mockito.when(_cityRepository.findAll(any(Predicate.class), any(Pageable.class))).thenReturn(city);
        Assertions.assertThat(_cityManager.findAll(predicate, pageable)).isEqualTo(city);
    }

    //Country
    @Test
    public void getCountry_if_CityIdIsNotNull_returnCountry() {
        CityEntity cityEntity = mock(CityEntity.class);
        CountryEntity country = mock(CountryEntity.class);

        Optional<CityEntity> dbCity = Optional.of((CityEntity) cityEntity);
        Mockito.<Optional<CityEntity>>when(_cityRepository.findById(any(Integer.class))).thenReturn(dbCity);
        Mockito.when(cityEntity.getCountry()).thenReturn(country);
        Assertions.assertThat(_cityManager.getCountry(ID)).isEqualTo(country);
    }
}
