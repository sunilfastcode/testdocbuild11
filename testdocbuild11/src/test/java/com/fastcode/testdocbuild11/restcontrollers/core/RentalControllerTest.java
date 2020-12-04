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

import com.fastcode.testdocbuild11.application.core.customer.CustomerAppService;
import com.fastcode.testdocbuild11.application.core.inventory.InventoryAppService;
import com.fastcode.testdocbuild11.application.core.payment.PaymentAppService;
import com.fastcode.testdocbuild11.application.core.rental.RentalAppService;
import com.fastcode.testdocbuild11.application.core.rental.dto.*;
import com.fastcode.testdocbuild11.application.core.staff.StaffAppService;
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
import com.fastcode.testdocbuild11.domain.core.film.IFilmRepository;
import com.fastcode.testdocbuild11.domain.core.inventory.IInventoryRepository;
import com.fastcode.testdocbuild11.domain.core.inventory.InventoryEntity;
import com.fastcode.testdocbuild11.domain.core.language.ILanguageRepository;
import com.fastcode.testdocbuild11.domain.core.language.LanguageEntity;
import com.fastcode.testdocbuild11.domain.core.rental.IRentalRepository;
import com.fastcode.testdocbuild11.domain.core.rental.IRentalRepository;
import com.fastcode.testdocbuild11.domain.core.rental.RentalEntity;
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
public class RentalControllerTest {

    @Autowired
    protected SortHandlerMethodArgumentResolver sortArgumentResolver;

    @Autowired
    @Qualifier("rentalRepository")
    protected IRentalRepository rental_repository;

    @Autowired
    @Qualifier("customerRepository")
    protected ICustomerRepository customerRepository;

    @Autowired
    @Qualifier("inventoryRepository")
    protected IInventoryRepository inventoryRepository;

    @Autowired
    @Qualifier("staffRepository")
    protected IStaffRepository staffRepository;

    @Autowired
    @Qualifier("addressRepository")
    protected IAddressRepository addressRepository;

    @Autowired
    @Qualifier("rentalRepository")
    protected IRentalRepository rentalRepository;

    @Autowired
    @Qualifier("languageRepository")
    protected ILanguageRepository languageRepository;

    @Autowired
    @Qualifier("filmRepository")
    protected IFilmRepository filmRepository;

    @Autowired
    @Qualifier("countryRepository")
    protected ICountryRepository countryRepository;

    @Autowired
    @Qualifier("cityRepository")
    protected ICityRepository cityRepository;

    @SpyBean
    @Qualifier("rentalAppService")
    protected RentalAppService rentalAppService;

    @SpyBean
    @Qualifier("customerAppService")
    protected CustomerAppService customerAppService;

    @SpyBean
    @Qualifier("inventoryAppService")
    protected InventoryAppService inventoryAppService;

    @SpyBean
    @Qualifier("paymentAppService")
    protected PaymentAppService paymentAppService;

    @SpyBean
    @Qualifier("staffAppService")
    protected StaffAppService staffAppService;

    @SpyBean
    protected LoggingHelper logHelper;

    @SpyBean
    protected Environment env;

    @Mock
    protected Logger loggerMock;

    protected RentalEntity rental;

    protected MockMvc mvc;

    @Autowired
    EntityManagerFactory emf;

    static EntityManagerFactory emfs;

    int count = 10;

    int countAddress = 10;
    int countRental = 10;
    int countLanguage = 10;
    int countCustomer = 10;
    int countFilm = 10;
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
        em.createNativeQuery("truncate table public.rental").executeUpdate();
        em.createNativeQuery("truncate table public.customer").executeUpdate();
        em.createNativeQuery("truncate table public.inventory").executeUpdate();
        em.createNativeQuery("truncate table public.staff").executeUpdate();
        em.createNativeQuery("truncate table public.address").executeUpdate();
        em.createNativeQuery("truncate table public.rental").executeUpdate();
        em.createNativeQuery("truncate table public.language").executeUpdate();
        em.createNativeQuery("truncate table public.film").executeUpdate();
        em.createNativeQuery("truncate table public.country").executeUpdate();
        em.createNativeQuery("truncate table public.city").executeUpdate();
        em.createNativeQuery("SET REFERENTIAL_INTEGRITY TRUE").executeUpdate();
        em.getTransaction().commit();
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

    public RentalEntity createEntity() {
        CustomerEntity customer = createCustomerEntity();
        InventoryEntity inventory = createInventoryEntity();
        StaffEntity staff = createStaffEntity();

        RentalEntity rentalEntity = new RentalEntity();
        rentalEntity.setLastUpdate(SearchUtils.stringToLocalDateTime("1996-09-01 09:15:22"));
        rentalEntity.setRentalDate(SearchUtils.stringToLocalDateTime("1996-09-01 09:15:22"));
        rentalEntity.setRentalId(1);
        rentalEntity.setReturnDate(SearchUtils.stringToLocalDateTime("1996-09-01 09:15:22"));
        rentalEntity.setVersiono(0L);
        rentalEntity.setCustomer(customer);
        rentalEntity.setInventory(inventory);
        rentalEntity.setStaff(staff);

        return rentalEntity;
    }

    public CreateRentalInput createRentalInput() {
        CreateRentalInput rentalInput = new CreateRentalInput();
        rentalInput.setLastUpdate(SearchUtils.stringToLocalDateTime("1996-08-10 05:25:22"));
        rentalInput.setRentalDate(SearchUtils.stringToLocalDateTime("1996-08-10 05:25:22"));
        rentalInput.setReturnDate(SearchUtils.stringToLocalDateTime("1996-08-10 05:25:22"));

        return rentalInput;
    }

    public RentalEntity createNewEntity() {
        RentalEntity rental = new RentalEntity();
        rental.setLastUpdate(SearchUtils.stringToLocalDateTime("1996-08-11 05:35:22"));
        rental.setRentalDate(SearchUtils.stringToLocalDateTime("1996-08-11 05:35:22"));
        rental.setRentalId(3);
        rental.setReturnDate(SearchUtils.stringToLocalDateTime("1996-08-11 05:35:22"));

        return rental;
    }

    public RentalEntity createUpdateEntity() {
        RentalEntity rental = new RentalEntity();
        rental.setLastUpdate(SearchUtils.stringToLocalDateTime("1996-09-09 05:45:22"));
        rental.setRentalDate(SearchUtils.stringToLocalDateTime("1996-09-09 05:45:22"));
        rental.setRentalId(4);
        rental.setReturnDate(SearchUtils.stringToLocalDateTime("1996-09-09 05:45:22"));

        return rental;
    }

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        final RentalController rentalController = new RentalController(
            rentalAppService,
            customerAppService,
            inventoryAppService,
            paymentAppService,
            staffAppService,
            logHelper,
            env
        );
        when(logHelper.getLogger()).thenReturn(loggerMock);
        doNothing().when(loggerMock).error(anyString());

        this.mvc =
            MockMvcBuilders
                .standaloneSetup(rentalController)
                .setCustomArgumentResolvers(sortArgumentResolver)
                .setControllerAdvice()
                .build();
    }

    @Before
    public void initTest() {
        rental = createEntity();
        List<RentalEntity> list = rental_repository.findAll();
        if (!list.contains(rental)) {
            rental = rental_repository.save(rental);
        }
    }

    @Test
    public void FindById_IdIsValid_ReturnStatusOk() throws Exception {
        mvc
            .perform(get("/rental/" + rental.getRentalId()).contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
    }

    @Test
    public void FindById_IdIsNotValid_ReturnStatusNotFound() {
        org.assertj.core.api.Assertions
            .assertThatThrownBy(
                () -> mvc.perform(get("/rental/999").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
            )
            .hasCause(new EntityNotFoundException("Not found"));
    }

    @Test
    public void CreateRental_RentalDoesNotExist_ReturnStatusOk() throws Exception {
        CreateRentalInput rentalInput = createRentalInput();

        CustomerEntity customer = createCustomerEntity();

        rentalInput.setCustomerId(Short.parseShort(customer.getCustomerId().toString()));
        InventoryEntity inventory = createInventoryEntity();

        rentalInput.setInventoryId(Integer.parseInt(inventory.getInventoryId().toString()));
        StaffEntity staff = createStaffEntity();

        rentalInput.setStaffId(Short.parseShort(staff.getStaffId().toString()));

        ObjectWriter ow = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
            .writer()
            .withDefaultPrettyPrinter();

        String json = ow.writeValueAsString(rentalInput);

        mvc.perform(post("/rental").contentType(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isOk());
    }

    @Test
    public void CreateRental_customerDoesNotExists_ThrowEntityNotFoundException() throws Exception {
        CreateRentalInput rental = createRentalInput();
        ObjectWriter ow = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
            .writer()
            .withDefaultPrettyPrinter();

        String json = ow.writeValueAsString(rental);

        org.assertj.core.api.Assertions.assertThatThrownBy(
            () ->
                mvc
                    .perform(post("/rental").contentType(MediaType.APPLICATION_JSON).content(json))
                    .andExpect(status().isNotFound())
        );
    }

    @Test
    public void CreateRental_inventoryDoesNotExists_ThrowEntityNotFoundException() throws Exception {
        CreateRentalInput rental = createRentalInput();
        ObjectWriter ow = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
            .writer()
            .withDefaultPrettyPrinter();

        String json = ow.writeValueAsString(rental);

        org.assertj.core.api.Assertions.assertThatThrownBy(
            () ->
                mvc
                    .perform(post("/rental").contentType(MediaType.APPLICATION_JSON).content(json))
                    .andExpect(status().isNotFound())
        );
    }

    @Test
    public void CreateRental_staffDoesNotExists_ThrowEntityNotFoundException() throws Exception {
        CreateRentalInput rental = createRentalInput();
        ObjectWriter ow = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
            .writer()
            .withDefaultPrettyPrinter();

        String json = ow.writeValueAsString(rental);

        org.assertj.core.api.Assertions.assertThatThrownBy(
            () ->
                mvc
                    .perform(post("/rental").contentType(MediaType.APPLICATION_JSON).content(json))
                    .andExpect(status().isNotFound())
        );
    }

    @Test
    public void DeleteRental_IdIsNotValid_ThrowEntityNotFoundException() {
        doReturn(null).when(rentalAppService).findById(999);
        org.assertj.core.api.Assertions
            .assertThatThrownBy(
                () ->
                    mvc
                        .perform(delete("/rental/999").contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
            )
            .hasCause(new EntityNotFoundException("There does not exist a rental with a id=999"));
    }

    @Test
    public void Delete_IdIsValid_ReturnStatusNoContent() throws Exception {
        RentalEntity entity = createNewEntity();
        entity.setVersiono(0L);
        CustomerEntity customer = createCustomerEntity();
        entity.setCustomer(customer);
        InventoryEntity inventory = createInventoryEntity();
        entity.setInventory(inventory);
        StaffEntity staff = createStaffEntity();
        entity.setStaff(staff);
        entity = rental_repository.save(entity);

        FindRentalByIdOutput output = new FindRentalByIdOutput();
        output.setLastUpdate(entity.getLastUpdate());
        output.setRentalDate(entity.getRentalDate());
        output.setRentalId(entity.getRentalId());

        Mockito.doReturn(output).when(rentalAppService).findById(entity.getRentalId());

        //    Mockito.when(rentalAppService.findById(entity.getRentalId())).thenReturn(output);

        mvc
            .perform(delete("/rental/" + entity.getRentalId()).contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());
    }

    @Test
    public void UpdateRental_RentalDoesNotExist_ReturnStatusNotFound() throws Exception {
        doReturn(null).when(rentalAppService).findById(999);

        UpdateRentalInput rental = new UpdateRentalInput();
        rental.setLastUpdate(SearchUtils.stringToLocalDateTime("1996-09-28 07:15:22"));
        rental.setRentalDate(SearchUtils.stringToLocalDateTime("1996-09-28 07:15:22"));
        rental.setRentalId(999);
        rental.setReturnDate(SearchUtils.stringToLocalDateTime("1996-09-28 07:15:22"));

        ObjectWriter ow = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
            .writer()
            .withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(rental);

        org.assertj.core.api.Assertions
            .assertThatThrownBy(
                () ->
                    mvc
                        .perform(put("/rental/999").contentType(MediaType.APPLICATION_JSON).content(json))
                        .andExpect(status().isOk())
            )
            .hasCause(new EntityNotFoundException("Unable to update. Rental with id=999 not found."));
    }

    @Test
    public void UpdateRental_RentalExists_ReturnStatusOk() throws Exception {
        RentalEntity entity = createUpdateEntity();
        entity.setVersiono(0L);

        CustomerEntity customer = createCustomerEntity();
        entity.setCustomer(customer);
        InventoryEntity inventory = createInventoryEntity();
        entity.setInventory(inventory);
        StaffEntity staff = createStaffEntity();
        entity.setStaff(staff);
        entity = rental_repository.save(entity);
        FindRentalByIdOutput output = new FindRentalByIdOutput();
        output.setLastUpdate(entity.getLastUpdate());
        output.setRentalDate(entity.getRentalDate());
        output.setRentalId(entity.getRentalId());
        output.setReturnDate(entity.getReturnDate());
        output.setVersiono(entity.getVersiono());

        Mockito.when(rentalAppService.findById(entity.getRentalId())).thenReturn(output);

        UpdateRentalInput rentalInput = new UpdateRentalInput();
        rentalInput.setLastUpdate(entity.getLastUpdate());
        rentalInput.setRentalDate(entity.getRentalDate());
        rentalInput.setRentalId(entity.getRentalId());

        rentalInput.setCustomerId(Short.parseShort(customer.getCustomerId().toString()));
        rentalInput.setInventoryId(Integer.parseInt(inventory.getInventoryId().toString()));
        rentalInput.setStaffId(Short.parseShort(staff.getStaffId().toString()));

        ObjectWriter ow = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
            .writer()
            .withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(rentalInput);

        mvc
            .perform(put("/rental/" + entity.getRentalId()).contentType(MediaType.APPLICATION_JSON).content(json))
            .andExpect(status().isOk());

        RentalEntity de = createUpdateEntity();
        de.setRentalId(entity.getRentalId());
        rental_repository.delete(de);
    }

    @Test
    public void FindAll_SearchIsNotNullAndPropertyIsValid_ReturnStatusOk() throws Exception {
        mvc
            .perform(get("/rental?search=rentalId[equals]=1&limit=10&offset=1").contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
    }

    @Test
    public void FindAll_SearchIsNotNullAndPropertyIsNotValid_ThrowException() throws Exception {
        org.assertj.core.api.Assertions
            .assertThatThrownBy(
                () ->
                    mvc
                        .perform(
                            get("/rental?search=rentalrentalId[equals]=1&limit=10&offset=1")
                                .contentType(MediaType.APPLICATION_JSON)
                        )
                        .andExpect(status().isOk())
            )
            .hasCause(new Exception("Wrong URL Format: Property rentalrentalId not found!"));
    }

    @Test
    public void GetCustomer_IdIsNotEmptyAndIdDoesNotExist_ReturnNotFound() {
        org.assertj.core.api.Assertions
            .assertThatThrownBy(
                () ->
                    mvc
                        .perform(get("/rental/999/customer").contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
            )
            .hasCause(new EntityNotFoundException("Not found"));
    }

    @Test
    public void GetCustomer_searchIsNotEmptyAndPropertyIsValid_ReturnList() throws Exception {
        mvc
            .perform(get("/rental/" + rental.getRentalId() + "/customer").contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
    }

    @Test
    public void GetInventory_IdIsNotEmptyAndIdDoesNotExist_ReturnNotFound() {
        org.assertj.core.api.Assertions
            .assertThatThrownBy(
                () ->
                    mvc
                        .perform(get("/rental/999/inventory").contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
            )
            .hasCause(new EntityNotFoundException("Not found"));
    }

    @Test
    public void GetInventory_searchIsNotEmptyAndPropertyIsValid_ReturnList() throws Exception {
        mvc
            .perform(get("/rental/" + rental.getRentalId() + "/inventory").contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
    }

    @Test
    public void GetPayments_searchIsNotEmptyAndPropertyIsNotValid_ThrowException() {
        Map<String, String> joinCol = new HashMap<String, String>();
        joinCol.put("rentalId", "1");

        Mockito.when(rentalAppService.parsePaymentsJoinColumn("rentalid")).thenReturn(joinCol);
        org.assertj.core.api.Assertions
            .assertThatThrownBy(
                () ->
                    mvc
                        .perform(
                            get("/rental/1/payments?search=abc[equals]=1&limit=10&offset=1")
                                .contentType(MediaType.APPLICATION_JSON)
                        )
                        .andExpect(status().isOk())
            )
            .hasCause(new Exception("Wrong URL Format: Property abc not found!"));
    }

    @Test
    public void GetPayments_searchIsNotEmptyAndPropertyIsValid_ReturnList() throws Exception {
        Map<String, String> joinCol = new HashMap<String, String>();
        joinCol.put("rentalId", "1");

        Mockito.when(rentalAppService.parsePaymentsJoinColumn("rentalId")).thenReturn(joinCol);
        mvc
            .perform(
                get("/rental/1/payments?search=rentalId[equals]=1&limit=10&offset=1")
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isOk());
    }

    @Test
    public void GetPayments_searchIsNotEmpty() {
        Mockito.when(rentalAppService.parsePaymentsJoinColumn(anyString())).thenReturn(null);

        org.assertj.core.api.Assertions
            .assertThatThrownBy(
                () ->
                    mvc
                        .perform(
                            get("/rental/1/payments?search=rentalId[equals]=1&limit=10&offset=1")
                                .contentType(MediaType.APPLICATION_JSON)
                        )
                        .andExpect(status().isOk())
            )
            .hasCause(new EntityNotFoundException("Invalid join column"));
    }

    @Test
    public void GetStaff_IdIsNotEmptyAndIdDoesNotExist_ReturnNotFound() {
        org.assertj.core.api.Assertions
            .assertThatThrownBy(
                () ->
                    mvc
                        .perform(get("/rental/999/staff").contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
            )
            .hasCause(new EntityNotFoundException("Not found"));
    }

    @Test
    public void GetStaff_searchIsNotEmptyAndPropertyIsValid_ReturnList() throws Exception {
        mvc
            .perform(get("/rental/" + rental.getRentalId() + "/staff").contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
    }
}
