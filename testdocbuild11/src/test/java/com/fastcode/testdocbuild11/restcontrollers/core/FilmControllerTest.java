package com.fastcode.testdocbuild11.restcontrollers.core;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fastcode.testdocbuild11.application.core.film.FilmAppService;
import com.fastcode.testdocbuild11.application.core.film.dto.*;
import com.fastcode.testdocbuild11.application.core.filmactor.FilmActorAppService;
import com.fastcode.testdocbuild11.application.core.filmcategory.FilmCategoryAppService;
import com.fastcode.testdocbuild11.application.core.inventory.InventoryAppService;
import com.fastcode.testdocbuild11.application.core.language.LanguageAppService;
import com.fastcode.testdocbuild11.commons.logging.LoggingHelper;
import com.fastcode.testdocbuild11.commons.search.SearchUtils;
import com.fastcode.testdocbuild11.domain.core.address.AddressEntity;
import com.fastcode.testdocbuild11.domain.core.address.IAddressRepository;
import com.fastcode.testdocbuild11.domain.core.city.CityEntity;
import com.fastcode.testdocbuild11.domain.core.city.ICityRepository;
import com.fastcode.testdocbuild11.domain.core.country.CountryEntity;
import com.fastcode.testdocbuild11.domain.core.country.ICountryRepository;
import com.fastcode.testdocbuild11.domain.core.customer.CustomerEntity;
import com.fastcode.testdocbuild11.domain.core.customer.ICustomerRepository;
import com.fastcode.testdocbuild11.domain.core.film.FilmEntity;
import com.fastcode.testdocbuild11.domain.core.film.FilmEntity;
import com.fastcode.testdocbuild11.domain.core.film.IFilmRepository;
import com.fastcode.testdocbuild11.domain.core.film.IFilmRepository;
import com.fastcode.testdocbuild11.domain.core.inventory.IInventoryRepository;
import com.fastcode.testdocbuild11.domain.core.inventory.InventoryEntity;
import com.fastcode.testdocbuild11.domain.core.language.ILanguageRepository;
import com.fastcode.testdocbuild11.domain.core.language.LanguageEntity;
import com.fastcode.testdocbuild11.domain.core.rental.IRentalRepository;
import com.fastcode.testdocbuild11.domain.core.rental.RentalEntity;
import com.fastcode.testdocbuild11.domain.core.staff.IStaffRepository;
import com.fastcode.testdocbuild11.domain.core.staff.StaffEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.math.BigDecimal;
import java.time.*;
import java.util.*;
import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.core.env.Environment;
import org.springframework.data.web.SortHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = "spring.profiles.active=test")
public class FilmControllerTest {

    @Autowired
    protected SortHandlerMethodArgumentResolver sortArgumentResolver;

    @Autowired
    @Qualifier("filmRepository")
    protected IFilmRepository film_repository;

    @Autowired
    @Qualifier("languageRepository")
    protected ILanguageRepository languageRepository;

    @Autowired
    @Qualifier("rentalRepository")
    protected IRentalRepository rentalRepository;

    @Autowired
    @Qualifier("addressRepository")
    protected IAddressRepository addressRepository;

    @Autowired
    @Qualifier("filmRepository")
    protected IFilmRepository filmRepository;

    @Autowired
    @Qualifier("customerRepository")
    protected ICustomerRepository customerRepository;

    @Autowired
    @Qualifier("staffRepository")
    protected IStaffRepository staffRepository;

    @Autowired
    @Qualifier("countryRepository")
    protected ICountryRepository countryRepository;

    @Autowired
    @Qualifier("cityRepository")
    protected ICityRepository cityRepository;

    @Autowired
    @Qualifier("inventoryRepository")
    protected IInventoryRepository inventoryRepository;

    @SpyBean
    @Qualifier("filmAppService")
    protected FilmAppService filmAppService;

    @SpyBean
    @Qualifier("filmActorAppService")
    protected FilmActorAppService filmActorAppService;

    @SpyBean
    @Qualifier("filmCategoryAppService")
    protected FilmCategoryAppService filmCategoryAppService;

    @SpyBean
    @Qualifier("inventoryAppService")
    protected InventoryAppService inventoryAppService;

    @SpyBean
    @Qualifier("languageAppService")
    protected LanguageAppService languageAppService;

    @SpyBean
    protected LoggingHelper logHelper;

    @SpyBean
    protected Environment env;

    @Mock
    protected Logger loggerMock;

    protected FilmEntity film;

    protected MockMvc mvc;

    @Autowired
    EntityManagerFactory emf;

    static EntityManagerFactory emfs;

    int count = 10;

    int countLanguage = 10;
    int countRental = 10;
    int countAddress = 10;
    int countFilm = 10;
    int countCustomer = 10;
    int countStaff = 10;
    int countCountry = 10;
    int countCity = 10;
    int countInventory = 10;

    @PostConstruct
    public void init() {
        emfs = emf;
    }

    @AfterClass
    public static void cleanup() {
        EntityManager em = emfs.createEntityManager();
        em.getTransaction().begin();
        em.createNativeQuery("SET REFERENTIAL_INTEGRITY FALSE").executeUpdate();
        em.createNativeQuery("truncate table public.film").executeUpdate();
        em.createNativeQuery("truncate table public.language").executeUpdate();
        em.createNativeQuery("truncate table public.rental").executeUpdate();
        em.createNativeQuery("truncate table public.address").executeUpdate();
        em.createNativeQuery("truncate table public.film").executeUpdate();
        em.createNativeQuery("truncate table public.customer").executeUpdate();
        em.createNativeQuery("truncate table public.staff").executeUpdate();
        em.createNativeQuery("truncate table public.country").executeUpdate();
        em.createNativeQuery("truncate table public.city").executeUpdate();
        em.createNativeQuery("truncate table public.inventory").executeUpdate();
        em.createNativeQuery("SET REFERENTIAL_INTEGRITY TRUE").executeUpdate();
        em.getTransaction().commit();
    }

    public LanguageEntity createLanguageEntity() {
        if (countLanguage > 99) {
            countLanguage = 10;
        }

        LanguageEntity languageEntity = new LanguageEntity();
        languageEntity.setLanguageId(countLanguage);
        languageEntity.setLastUpdate(SearchUtils.stringToLocalDateTime("19" + countLanguage + "-09-01 05:25:22"));
        languageEntity.setName(String.valueOf(countLanguage));
        languageEntity.setVersiono(0L);
        if (!languageRepository.findAll().contains(languageEntity)) {
            languageEntity = languageRepository.save(languageEntity);
        }
        countLanguage++;
        return languageEntity;
    }

    public RentalEntity createRentalEntity() {
        if (countRental > 99) {
            countRental = 10;
        }

        RentalEntity rentalEntity = new RentalEntity();
        rentalEntity.setLastUpdate(SearchUtils.stringToLocalDateTime("19" + countRental + "-09-01 05:25:22"));
        rentalEntity.setRentalDate(SearchUtils.stringToLocalDateTime("19" + countRental + "-09-01 05:25:22"));
        rentalEntity.setRentalId(countRental);
        rentalEntity.setReturnDate(SearchUtils.stringToLocalDateTime("19" + countRental + "-09-01 05:25:22"));
        rentalEntity.setVersiono(0L);
        StaffEntity staff = createStaffEntity();
        rentalEntity.setStaff(staff);
        InventoryEntity inventory = createInventoryEntity();
        rentalEntity.setInventory(inventory);
        CustomerEntity customer = createCustomerEntity();
        rentalEntity.setCustomer(customer);
        if (!rentalRepository.findAll().contains(rentalEntity)) {
            rentalEntity = rentalRepository.save(rentalEntity);
        }
        countRental++;
        return rentalEntity;
    }

    public AddressEntity createAddressEntity() {
        if (countAddress > 99) {
            countAddress = 10;
        }

        AddressEntity addressEntity = new AddressEntity();
        addressEntity.setAddress(String.valueOf(countAddress));
        addressEntity.setAddress2(String.valueOf(countAddress));
        addressEntity.setAddressId(countAddress);
        addressEntity.setDistrict(String.valueOf(countAddress));
        addressEntity.setLastUpdate(SearchUtils.stringToLocalDateTime("19" + countAddress + "-09-01 05:25:22"));
        addressEntity.setPhone(String.valueOf(countAddress));
        addressEntity.setPostalCode(String.valueOf(countAddress));
        addressEntity.setVersiono(0L);
        CityEntity city = createCityEntity();
        addressEntity.setCity(city);
        if (!addressRepository.findAll().contains(addressEntity)) {
            addressEntity = addressRepository.save(addressEntity);
        }
        countAddress++;
        return addressEntity;
    }

    public FilmEntity createFilmEntity() {
        if (countFilm > 99) {
            countFilm = 10;
        }

        FilmEntity filmEntity = new FilmEntity();
        filmEntity.setDescription(String.valueOf(countFilm));
        filmEntity.setFilmId(countFilm);
        filmEntity.setLastUpdate(SearchUtils.stringToLocalDateTime("19" + countFilm + "-09-01 05:25:22"));
        filmEntity.setLength((short) countFilm);
        filmEntity.setRating(String.valueOf(countFilm));
        filmEntity.setReleaseYear(countFilm);
        filmEntity.setRentalDuration((short) countFilm);
        filmEntity.setRentalRate(BigDecimal.valueOf(countFilm));
        filmEntity.setReplacementCost(BigDecimal.valueOf(countFilm));
        filmEntity.setTitle(String.valueOf(countFilm));
        filmEntity.setVersiono(0L);
        LanguageEntity language = createLanguageEntity();
        filmEntity.setLanguage(language);
        if (!filmRepository.findAll().contains(filmEntity)) {
            filmEntity = filmRepository.save(filmEntity);
        }
        countFilm++;
        return filmEntity;
    }

    public CustomerEntity createCustomerEntity() {
        if (countCustomer > 99) {
            countCustomer = 10;
        }

        CustomerEntity customerEntity = new CustomerEntity();
        customerEntity.setActive(countCustomer);
        customerEntity.setActivebool(false);
        customerEntity.setCreateDate(SearchUtils.stringToLocalDate("19" + countCustomer + "-09-01"));
        customerEntity.setCustomerId(countCustomer);
        customerEntity.setEmail(String.valueOf(countCustomer));
        customerEntity.setFirstName(String.valueOf(countCustomer));
        customerEntity.setLastName(String.valueOf(countCustomer));
        customerEntity.setLastUpdate(SearchUtils.stringToLocalDateTime("19" + countCustomer + "-09-01 05:25:22"));
        customerEntity.setStoreId((short) countCustomer);
        customerEntity.setVersiono(0L);
        AddressEntity address = createAddressEntity();
        customerEntity.setAddress(address);
        if (!customerRepository.findAll().contains(customerEntity)) {
            customerEntity = customerRepository.save(customerEntity);
        }
        countCustomer++;
        return customerEntity;
    }

    public StaffEntity createStaffEntity() {
        if (countStaff > 99) {
            countStaff = 10;
        }

        StaffEntity staffEntity = new StaffEntity();
        staffEntity.setActive(false);
        staffEntity.setEmail(String.valueOf(countStaff));
        staffEntity.setFirstName(String.valueOf(countStaff));
        staffEntity.setLastName(String.valueOf(countStaff));
        staffEntity.setLastUpdate(SearchUtils.stringToLocalDateTime("19" + countStaff + "-09-01 05:25:22"));
        staffEntity.setPassword(String.valueOf(countStaff));
        staffEntity.setStaffId(countStaff);
        staffEntity.setStoreId((short) countStaff);
        staffEntity.setUsername(String.valueOf(countStaff));
        staffEntity.setVersiono(0L);
        AddressEntity address = createAddressEntity();
        staffEntity.setAddress(address);
        if (!staffRepository.findAll().contains(staffEntity)) {
            staffEntity = staffRepository.save(staffEntity);
        }
        countStaff++;
        return staffEntity;
    }

    public CountryEntity createCountryEntity() {
        if (countCountry > 99) {
            countCountry = 10;
        }

        CountryEntity countryEntity = new CountryEntity();
        countryEntity.setCountry(String.valueOf(countCountry));
        countryEntity.setCountryId(countCountry);
        countryEntity.setLastUpdate(SearchUtils.stringToLocalDateTime("19" + countCountry + "-09-01 05:25:22"));
        countryEntity.setVersiono(0L);
        if (!countryRepository.findAll().contains(countryEntity)) {
            countryEntity = countryRepository.save(countryEntity);
        }
        countCountry++;
        return countryEntity;
    }

    public CityEntity createCityEntity() {
        if (countCity > 99) {
            countCity = 10;
        }

        CityEntity cityEntity = new CityEntity();
        cityEntity.setCity(String.valueOf(countCity));
        cityEntity.setCityId(countCity);
        cityEntity.setLastUpdate(SearchUtils.stringToLocalDateTime("19" + countCity + "-09-01 05:25:22"));
        cityEntity.setVersiono(0L);
        CountryEntity country = createCountryEntity();
        cityEntity.setCountry(country);
        if (!cityRepository.findAll().contains(cityEntity)) {
            cityEntity = cityRepository.save(cityEntity);
        }
        countCity++;
        return cityEntity;
    }

    public InventoryEntity createInventoryEntity() {
        if (countInventory > 99) {
            countInventory = 10;
        }

        InventoryEntity inventoryEntity = new InventoryEntity();
        inventoryEntity.setInventoryId(countInventory);
        inventoryEntity.setLastUpdate(SearchUtils.stringToLocalDateTime("19" + countInventory + "-09-01 05:25:22"));
        inventoryEntity.setStoreId((short) countInventory);
        inventoryEntity.setVersiono(0L);
        FilmEntity film = createFilmEntity();
        inventoryEntity.setFilm(film);
        if (!inventoryRepository.findAll().contains(inventoryEntity)) {
            inventoryEntity = inventoryRepository.save(inventoryEntity);
        }
        countInventory++;
        return inventoryEntity;
    }

    public FilmEntity createEntity() {
        LanguageEntity language = createLanguageEntity();

        FilmEntity filmEntity = new FilmEntity();
        filmEntity.setDescription("1");
        filmEntity.setFilmId(1);
        filmEntity.setLastUpdate(SearchUtils.stringToLocalDateTime("1996-09-01 09:15:22"));
        filmEntity.setLength((short) 1);
        filmEntity.setRating("1");
        filmEntity.setReleaseYear(1);
        filmEntity.setRentalDuration((short) 1);
        filmEntity.setRentalRate(new BigDecimal("1"));
        filmEntity.setReplacementCost(new BigDecimal("1"));
        filmEntity.setTitle("1");
        filmEntity.setVersiono(0L);
        filmEntity.setLanguage(language);

        return filmEntity;
    }

    public CreateFilmInput createFilmInput() {
        CreateFilmInput filmInput = new CreateFilmInput();
        filmInput.setDescription("5");
        filmInput.setLastUpdate(SearchUtils.stringToLocalDateTime("1996-08-10 05:25:22"));
        filmInput.setLength((short) 5);
        filmInput.setRating("5");
        filmInput.setReleaseYear(5);
        filmInput.setRentalDuration((short) 5);
        filmInput.setRentalRate(new BigDecimal("5"));
        filmInput.setReplacementCost(new BigDecimal("5"));
        filmInput.setTitle("5");

        return filmInput;
    }

    public FilmEntity createNewEntity() {
        FilmEntity film = new FilmEntity();
        film.setDescription("3");
        film.setFilmId(3);
        film.setLastUpdate(SearchUtils.stringToLocalDateTime("1996-08-11 05:35:22"));
        film.setLength((short) 3);
        film.setRating("3");
        film.setReleaseYear(3);
        film.setRentalDuration((short) 3);
        film.setRentalRate(new BigDecimal("3"));
        film.setReplacementCost(new BigDecimal("3"));
        film.setTitle("3");

        return film;
    }

    public FilmEntity createUpdateEntity() {
        FilmEntity film = new FilmEntity();
        film.setDescription("4");
        film.setFilmId(4);
        film.setLastUpdate(SearchUtils.stringToLocalDateTime("1996-09-09 05:45:22"));
        film.setLength((short) 4);
        film.setRating("4");
        film.setReleaseYear(4);
        film.setRentalDuration((short) 4);
        film.setRentalRate(new BigDecimal("3"));
        film.setReplacementCost(new BigDecimal("3"));
        film.setTitle("4");

        return film;
    }

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        final FilmController filmController = new FilmController(
            filmAppService,
            filmActorAppService,
            filmCategoryAppService,
            inventoryAppService,
            languageAppService,
            logHelper,
            env
        );
        when(logHelper.getLogger()).thenReturn(loggerMock);
        doNothing().when(loggerMock).error(anyString());

        this.mvc =
            MockMvcBuilders
                .standaloneSetup(filmController)
                .setCustomArgumentResolvers(sortArgumentResolver)
                .setControllerAdvice()
                .build();
    }

    @Before
    public void initTest() {
        film = createEntity();
        List<FilmEntity> list = film_repository.findAll();
        if (!list.contains(film)) {
            film = film_repository.save(film);
        }
    }

    @Test
    public void FindById_IdIsValid_ReturnStatusOk() throws Exception {
        mvc
            .perform(get("/film/" + film.getFilmId()).contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
    }

    @Test
    public void FindById_IdIsNotValid_ReturnStatusNotFound() {
        org.assertj.core.api.Assertions
            .assertThatThrownBy(
                () -> mvc.perform(get("/film/999").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
            )
            .hasCause(new EntityNotFoundException("Not found"));
    }

    @Test
    public void CreateFilm_FilmDoesNotExist_ReturnStatusOk() throws Exception {
        CreateFilmInput filmInput = createFilmInput();

        LanguageEntity language = createLanguageEntity();

        filmInput.setLanguageId(Short.parseShort(language.getLanguageId().toString()));

        ObjectWriter ow = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
            .writer()
            .withDefaultPrettyPrinter();

        String json = ow.writeValueAsString(filmInput);

        mvc.perform(post("/film").contentType(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isOk());
    }

    @Test
    public void CreateFilm_languageDoesNotExists_ThrowEntityNotFoundException() throws Exception {
        CreateFilmInput film = createFilmInput();
        ObjectWriter ow = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
            .writer()
            .withDefaultPrettyPrinter();

        String json = ow.writeValueAsString(film);

        org.assertj.core.api.Assertions.assertThatThrownBy(
            () ->
                mvc
                    .perform(post("/film").contentType(MediaType.APPLICATION_JSON).content(json))
                    .andExpect(status().isNotFound())
        );
    }

    @Test
    public void DeleteFilm_IdIsNotValid_ThrowEntityNotFoundException() {
        doReturn(null).when(filmAppService).findById(999);
        org.assertj.core.api.Assertions
            .assertThatThrownBy(
                () ->
                    mvc.perform(delete("/film/999").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
            )
            .hasCause(new EntityNotFoundException("There does not exist a film with a id=999"));
    }

    @Test
    public void Delete_IdIsValid_ReturnStatusNoContent() throws Exception {
        FilmEntity entity = createNewEntity();
        entity.setVersiono(0L);
        LanguageEntity language = createLanguageEntity();
        entity.setLanguage(language);
        entity = film_repository.save(entity);

        FindFilmByIdOutput output = new FindFilmByIdOutput();
        output.setFilmId(entity.getFilmId());
        output.setLastUpdate(entity.getLastUpdate());
        output.setRentalDuration(entity.getRentalDuration());
        output.setRentalRate(entity.getRentalRate());
        output.setReplacementCost(entity.getReplacementCost());
        output.setTitle(entity.getTitle());

        Mockito.doReturn(output).when(filmAppService).findById(entity.getFilmId());

        //    Mockito.when(filmAppService.findById(entity.getFilmId())).thenReturn(output);

        mvc
            .perform(delete("/film/" + entity.getFilmId()).contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());
    }

    @Test
    public void UpdateFilm_FilmDoesNotExist_ReturnStatusNotFound() throws Exception {
        doReturn(null).when(filmAppService).findById(999);

        UpdateFilmInput film = new UpdateFilmInput();
        film.setDescription("999");
        film.setFilmId(999);
        film.setLastUpdate(SearchUtils.stringToLocalDateTime("1996-09-28 07:15:22"));
        film.setLength((short) 999);
        film.setRating("999");
        film.setReleaseYear(999);
        film.setRentalDuration((short) 999);
        film.setRentalRate(new BigDecimal("999"));
        film.setReplacementCost(new BigDecimal("999"));
        film.setTitle("999");

        ObjectWriter ow = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
            .writer()
            .withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(film);

        org.assertj.core.api.Assertions
            .assertThatThrownBy(
                () ->
                    mvc
                        .perform(put("/film/999").contentType(MediaType.APPLICATION_JSON).content(json))
                        .andExpect(status().isOk())
            )
            .hasCause(new EntityNotFoundException("Unable to update. Film with id=999 not found."));
    }

    @Test
    public void UpdateFilm_FilmExists_ReturnStatusOk() throws Exception {
        FilmEntity entity = createUpdateEntity();
        entity.setVersiono(0L);

        LanguageEntity language = createLanguageEntity();
        entity.setLanguage(language);
        entity = film_repository.save(entity);
        FindFilmByIdOutput output = new FindFilmByIdOutput();
        output.setDescription(entity.getDescription());
        output.setFilmId(entity.getFilmId());
        output.setLastUpdate(entity.getLastUpdate());
        output.setLength(entity.getLength());
        output.setRating(entity.getRating());
        output.setReleaseYear(entity.getReleaseYear());
        output.setRentalDuration(entity.getRentalDuration());
        output.setRentalRate(entity.getRentalRate());
        output.setReplacementCost(entity.getReplacementCost());
        output.setTitle(entity.getTitle());
        output.setVersiono(entity.getVersiono());

        Mockito.when(filmAppService.findById(entity.getFilmId())).thenReturn(output);

        UpdateFilmInput filmInput = new UpdateFilmInput();
        filmInput.setFilmId(entity.getFilmId());
        filmInput.setLastUpdate(entity.getLastUpdate());
        filmInput.setRentalDuration(entity.getRentalDuration());
        filmInput.setRentalRate(entity.getRentalRate());
        filmInput.setReplacementCost(entity.getReplacementCost());
        filmInput.setTitle(entity.getTitle());

        filmInput.setLanguageId(Short.parseShort(language.getLanguageId().toString()));

        ObjectWriter ow = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
            .writer()
            .withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(filmInput);

        mvc
            .perform(put("/film/" + entity.getFilmId()).contentType(MediaType.APPLICATION_JSON).content(json))
            .andExpect(status().isOk());

        FilmEntity de = createUpdateEntity();
        de.setFilmId(entity.getFilmId());
        film_repository.delete(de);
    }

    @Test
    public void FindAll_SearchIsNotNullAndPropertyIsValid_ReturnStatusOk() throws Exception {
        mvc
            .perform(get("/film?search=filmId[equals]=1&limit=10&offset=1").contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
    }

    @Test
    public void FindAll_SearchIsNotNullAndPropertyIsNotValid_ThrowException() throws Exception {
        org.assertj.core.api.Assertions
            .assertThatThrownBy(
                () ->
                    mvc
                        .perform(
                            get("/film?search=filmfilmId[equals]=1&limit=10&offset=1")
                                .contentType(MediaType.APPLICATION_JSON)
                        )
                        .andExpect(status().isOk())
            )
            .hasCause(new Exception("Wrong URL Format: Property filmfilmId not found!"));
    }

    @Test
    public void GetFilmActors_searchIsNotEmptyAndPropertyIsNotValid_ThrowException() {
        Map<String, String> joinCol = new HashMap<String, String>();
        joinCol.put("filmId", "1");

        Mockito.when(filmAppService.parseFilmActorsJoinColumn("filmid")).thenReturn(joinCol);
        org.assertj.core.api.Assertions
            .assertThatThrownBy(
                () ->
                    mvc
                        .perform(
                            get("/film/1/filmActors?search=abc[equals]=1&limit=10&offset=1")
                                .contentType(MediaType.APPLICATION_JSON)
                        )
                        .andExpect(status().isOk())
            )
            .hasCause(new Exception("Wrong URL Format: Property abc not found!"));
    }

    @Test
    public void GetFilmActors_searchIsNotEmptyAndPropertyIsValid_ReturnList() throws Exception {
        Map<String, String> joinCol = new HashMap<String, String>();
        joinCol.put("filmId", "1");

        Mockito.when(filmAppService.parseFilmActorsJoinColumn("filmId")).thenReturn(joinCol);
        mvc
            .perform(
                get("/film/1/filmActors?search=filmId[equals]=1&limit=10&offset=1")
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isOk());
    }

    @Test
    public void GetFilmActors_searchIsNotEmpty() {
        Mockito.when(filmAppService.parseFilmActorsJoinColumn(anyString())).thenReturn(null);

        org.assertj.core.api.Assertions
            .assertThatThrownBy(
                () ->
                    mvc
                        .perform(
                            get("/film/1/filmActors?search=filmId[equals]=1&limit=10&offset=1")
                                .contentType(MediaType.APPLICATION_JSON)
                        )
                        .andExpect(status().isOk())
            )
            .hasCause(new EntityNotFoundException("Invalid join column"));
    }

    @Test
    public void GetFilmCategorys_searchIsNotEmptyAndPropertyIsNotValid_ThrowException() {
        Map<String, String> joinCol = new HashMap<String, String>();
        joinCol.put("filmId", "1");

        Mockito.when(filmAppService.parseFilmCategorysJoinColumn("filmid")).thenReturn(joinCol);
        org.assertj.core.api.Assertions
            .assertThatThrownBy(
                () ->
                    mvc
                        .perform(
                            get("/film/1/filmCategorys?search=abc[equals]=1&limit=10&offset=1")
                                .contentType(MediaType.APPLICATION_JSON)
                        )
                        .andExpect(status().isOk())
            )
            .hasCause(new Exception("Wrong URL Format: Property abc not found!"));
    }

    @Test
    public void GetFilmCategorys_searchIsNotEmptyAndPropertyIsValid_ReturnList() throws Exception {
        Map<String, String> joinCol = new HashMap<String, String>();
        joinCol.put("filmId", "1");

        Mockito.when(filmAppService.parseFilmCategorysJoinColumn("filmId")).thenReturn(joinCol);
        mvc
            .perform(
                get("/film/1/filmCategorys?search=filmId[equals]=1&limit=10&offset=1")
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isOk());
    }

    @Test
    public void GetFilmCategorys_searchIsNotEmpty() {
        Mockito.when(filmAppService.parseFilmCategorysJoinColumn(anyString())).thenReturn(null);

        org.assertj.core.api.Assertions
            .assertThatThrownBy(
                () ->
                    mvc
                        .perform(
                            get("/film/1/filmCategorys?search=filmId[equals]=1&limit=10&offset=1")
                                .contentType(MediaType.APPLICATION_JSON)
                        )
                        .andExpect(status().isOk())
            )
            .hasCause(new EntityNotFoundException("Invalid join column"));
    }

    @Test
    public void GetInventorys_searchIsNotEmptyAndPropertyIsNotValid_ThrowException() {
        Map<String, String> joinCol = new HashMap<String, String>();
        joinCol.put("filmId", "1");

        Mockito.when(filmAppService.parseInventorysJoinColumn("filmid")).thenReturn(joinCol);
        org.assertj.core.api.Assertions
            .assertThatThrownBy(
                () ->
                    mvc
                        .perform(
                            get("/film/1/inventorys?search=abc[equals]=1&limit=10&offset=1")
                                .contentType(MediaType.APPLICATION_JSON)
                        )
                        .andExpect(status().isOk())
            )
            .hasCause(new Exception("Wrong URL Format: Property abc not found!"));
    }

    @Test
    public void GetInventorys_searchIsNotEmptyAndPropertyIsValid_ReturnList() throws Exception {
        Map<String, String> joinCol = new HashMap<String, String>();
        joinCol.put("filmId", "1");

        Mockito.when(filmAppService.parseInventorysJoinColumn("filmId")).thenReturn(joinCol);
        mvc
            .perform(
                get("/film/1/inventorys?search=filmId[equals]=1&limit=10&offset=1")
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isOk());
    }

    @Test
    public void GetInventorys_searchIsNotEmpty() {
        Mockito.when(filmAppService.parseInventorysJoinColumn(anyString())).thenReturn(null);

        org.assertj.core.api.Assertions
            .assertThatThrownBy(
                () ->
                    mvc
                        .perform(
                            get("/film/1/inventorys?search=filmId[equals]=1&limit=10&offset=1")
                                .contentType(MediaType.APPLICATION_JSON)
                        )
                        .andExpect(status().isOk())
            )
            .hasCause(new EntityNotFoundException("Invalid join column"));
    }

    @Test
    public void GetLanguage_IdIsNotEmptyAndIdDoesNotExist_ReturnNotFound() {
        org.assertj.core.api.Assertions
            .assertThatThrownBy(
                () ->
                    mvc
                        .perform(get("/film/999/language").contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
            )
            .hasCause(new EntityNotFoundException("Not found"));
    }

    @Test
    public void GetLanguage_searchIsNotEmptyAndPropertyIsValid_ReturnList() throws Exception {
        mvc
            .perform(get("/film/" + film.getFilmId() + "/language").contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
    }
}
