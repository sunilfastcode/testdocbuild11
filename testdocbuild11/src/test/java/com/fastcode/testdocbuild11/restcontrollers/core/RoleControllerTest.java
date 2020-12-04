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

import com.fastcode.testdocbuild11.application.core.authorization.role.RoleAppService;
import com.fastcode.testdocbuild11.application.core.authorization.role.dto.*;
import com.fastcode.testdocbuild11.application.core.authorization.rolepermission.RolepermissionAppService;
import com.fastcode.testdocbuild11.application.core.authorization.userrole.UserroleAppService;
import com.fastcode.testdocbuild11.commons.logging.LoggingHelper;
import com.fastcode.testdocbuild11.domain.core.authorization.role.IRoleRepository;
import com.fastcode.testdocbuild11.domain.core.authorization.role.RoleEntity;
import com.fastcode.testdocbuild11.security.JWTAppService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
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
public class RoleControllerTest {

    @Autowired
    protected SortHandlerMethodArgumentResolver sortArgumentResolver;

    @Autowired
    @Qualifier("roleRepository")
    protected IRoleRepository role_repository;

    @SpyBean
    @Qualifier("roleAppService")
    protected RoleAppService roleAppService;

    @SpyBean
    @Qualifier("rolepermissionAppService")
    protected RolepermissionAppService rolepermissionAppService;

    @SpyBean
    @Qualifier("userroleAppService")
    protected UserroleAppService userroleAppService;

    @SpyBean
    protected JWTAppService jwtAppService;

    @SpyBean
    protected LoggingHelper logHelper;

    @SpyBean
    protected Environment env;

    @Mock
    protected Logger loggerMock;

    protected RoleEntity role;

    protected MockMvc mvc;

    @Autowired
    EntityManagerFactory emf;

    static EntityManagerFactory emfs;

    int count = 10;

    @PostConstruct
    public void init() {
        emfs = emf;
    }

    @AfterClass
    public static void cleanup() {
        EntityManager em = emfs.createEntityManager();
        em.getTransaction().begin();
        em.createNativeQuery("SET REFERENTIAL_INTEGRITY FALSE").executeUpdate();
        em.createNativeQuery("truncate table public.role").executeUpdate();
        em.createNativeQuery("SET REFERENTIAL_INTEGRITY TRUE").executeUpdate();
        em.getTransaction().commit();
    }

    public RoleEntity createEntity() {
        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setDisplayName("1");
        roleEntity.setId(1L);
        roleEntity.setName("1");
        roleEntity.setVersiono(0L);

        return roleEntity;
    }

    public CreateRoleInput createRoleInput() {
        CreateRoleInput roleInput = new CreateRoleInput();
        roleInput.setDisplayName("5");
        roleInput.setName("5");

        return roleInput;
    }

    public RoleEntity createNewEntity() {
        RoleEntity role = new RoleEntity();
        role.setDisplayName("3");
        role.setId(3L);
        role.setName("3");

        return role;
    }

    public RoleEntity createUpdateEntity() {
        RoleEntity role = new RoleEntity();
        role.setDisplayName("4");
        role.setId(4L);
        role.setName("4");

        return role;
    }

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        final RoleController roleController = new RoleController(
            roleAppService,
            rolepermissionAppService,
            userroleAppService,
            logHelper,
            env
        );
        when(logHelper.getLogger()).thenReturn(loggerMock);
        doNothing().when(loggerMock).error(anyString());

        this.mvc =
            MockMvcBuilders
                .standaloneSetup(roleController)
                .setCustomArgumentResolvers(sortArgumentResolver)
                .setControllerAdvice()
                .build();
    }

    @Before
    public void initTest() {
        role = createEntity();
        List<RoleEntity> list = role_repository.findAll();
        if (!list.contains(role)) {
            role = role_repository.save(role);
        }
    }

    @Test
    public void FindById_IdIsValid_ReturnStatusOk() throws Exception {
        mvc.perform(get("/role/" + role.getId()).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    @Test
    public void FindById_IdIsNotValid_ReturnStatusNotFound() {
        org.assertj.core.api.Assertions
            .assertThatThrownBy(
                () -> mvc.perform(get("/role/999").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
            )
            .hasCause(new EntityNotFoundException("Not found"));
    }

    @Test
    public void CreateRole_RoleDoesNotExist_ReturnStatusOk() throws Exception {
        CreateRoleInput roleInput = createRoleInput();

        ObjectWriter ow = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
            .writer()
            .withDefaultPrettyPrinter();

        String json = ow.writeValueAsString(roleInput);

        mvc.perform(post("/role").contentType(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isOk());
    }

    @Test
    public void DeleteRole_IdIsNotValid_ThrowEntityNotFoundException() {
        doReturn(null).when(roleAppService).findById(999L);
        org.assertj.core.api.Assertions
            .assertThatThrownBy(
                () ->
                    mvc.perform(delete("/role/999").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
            )
            .hasCause(new EntityNotFoundException("There does not exist a role with a id=999"));
    }

    @Test
    public void Delete_IdIsValid_ReturnStatusNoContent() throws Exception {
        RoleEntity entity = createNewEntity();
        entity.setVersiono(0L);
        entity = role_repository.save(entity);

        FindRoleByIdOutput output = new FindRoleByIdOutput();
        output.setDisplayName(entity.getDisplayName());
        output.setId(entity.getId());
        output.setName(entity.getName());

        Mockito.doReturn(output).when(roleAppService).findById(entity.getId());

        //    Mockito.when(roleAppService.findById(entity.getId())).thenReturn(output);

        mvc
            .perform(delete("/role/" + entity.getId()).contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());
    }

    @Test
    public void UpdateRole_RoleDoesNotExist_ReturnStatusNotFound() throws Exception {
        doReturn(null).when(roleAppService).findById(999L);

        UpdateRoleInput role = new UpdateRoleInput();
        role.setDisplayName("999");
        role.setId(999L);
        role.setName("999");

        ObjectWriter ow = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
            .writer()
            .withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(role);

        org.assertj.core.api.Assertions
            .assertThatThrownBy(
                () ->
                    mvc
                        .perform(put("/role/999").contentType(MediaType.APPLICATION_JSON).content(json))
                        .andExpect(status().isOk())
            )
            .hasCause(new EntityNotFoundException("Unable to update. Role with id=999 not found."));
    }

    @Test
    public void UpdateRole_RoleExists_ReturnStatusOk() throws Exception {
        RoleEntity entity = createUpdateEntity();
        entity.setVersiono(0L);

        entity = role_repository.save(entity);
        FindRoleByIdOutput output = new FindRoleByIdOutput();
        output.setDisplayName(entity.getDisplayName());
        output.setId(entity.getId());
        output.setName(entity.getName());
        output.setVersiono(entity.getVersiono());

        Mockito.when(roleAppService.findById(entity.getId())).thenReturn(output);

        UpdateRoleInput roleInput = new UpdateRoleInput();
        roleInput.setDisplayName(entity.getDisplayName());
        roleInput.setId(entity.getId());
        roleInput.setName(entity.getName());

        ObjectWriter ow = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
            .writer()
            .withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(roleInput);

        mvc
            .perform(put("/role/" + entity.getId()).contentType(MediaType.APPLICATION_JSON).content(json))
            .andExpect(status().isOk());

        RoleEntity de = createUpdateEntity();
        de.setId(entity.getId());
        role_repository.delete(de);
    }

    @Test
    public void FindAll_SearchIsNotNullAndPropertyIsValid_ReturnStatusOk() throws Exception {
        mvc
            .perform(get("/role?search=id[equals]=1&limit=10&offset=1").contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
    }

    @Test
    public void FindAll_SearchIsNotNullAndPropertyIsNotValid_ThrowException() throws Exception {
        org.assertj.core.api.Assertions
            .assertThatThrownBy(
                () ->
                    mvc
                        .perform(
                            get("/role?search=roleid[equals]=1&limit=10&offset=1")
                                .contentType(MediaType.APPLICATION_JSON)
                        )
                        .andExpect(status().isOk())
            )
            .hasCause(new Exception("Wrong URL Format: Property roleid not found!"));
    }

    @Test
    public void GetRolepermissions_searchIsNotEmptyAndPropertyIsNotValid_ThrowException() {
        Map<String, String> joinCol = new HashMap<String, String>();
        joinCol.put("id", "1");

        Mockito.when(roleAppService.parseRolepermissionsJoinColumn("roleid")).thenReturn(joinCol);
        org.assertj.core.api.Assertions
            .assertThatThrownBy(
                () ->
                    mvc
                        .perform(
                            get("/role/1/rolepermissions?search=abc[equals]=1&limit=10&offset=1")
                                .contentType(MediaType.APPLICATION_JSON)
                        )
                        .andExpect(status().isOk())
            )
            .hasCause(new Exception("Wrong URL Format: Property abc not found!"));
    }

    @Test
    public void GetRolepermissions_searchIsNotEmptyAndPropertyIsValid_ReturnList() throws Exception {
        Map<String, String> joinCol = new HashMap<String, String>();
        joinCol.put("id", "1");

        Mockito.when(roleAppService.parseRolepermissionsJoinColumn("roleId")).thenReturn(joinCol);
        mvc
            .perform(
                get("/role/1/rolepermissions?search=roleId[equals]=1&limit=10&offset=1")
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isOk());
    }

    @Test
    public void GetRolepermissions_searchIsNotEmpty() {
        Mockito.when(roleAppService.parseRolepermissionsJoinColumn(anyString())).thenReturn(null);

        org.assertj.core.api.Assertions
            .assertThatThrownBy(
                () ->
                    mvc
                        .perform(
                            get("/role/1/rolepermissions?search=roleId[equals]=1&limit=10&offset=1")
                                .contentType(MediaType.APPLICATION_JSON)
                        )
                        .andExpect(status().isOk())
            )
            .hasCause(new EntityNotFoundException("Invalid join column"));
    }

    @Test
    public void GetUserroles_searchIsNotEmptyAndPropertyIsNotValid_ThrowException() {
        Map<String, String> joinCol = new HashMap<String, String>();
        joinCol.put("id", "1");

        Mockito.when(roleAppService.parseUserrolesJoinColumn("roleid")).thenReturn(joinCol);
        org.assertj.core.api.Assertions
            .assertThatThrownBy(
                () ->
                    mvc
                        .perform(
                            get("/role/1/userroles?search=abc[equals]=1&limit=10&offset=1")
                                .contentType(MediaType.APPLICATION_JSON)
                        )
                        .andExpect(status().isOk())
            )
            .hasCause(new Exception("Wrong URL Format: Property abc not found!"));
    }

    @Test
    public void GetUserroles_searchIsNotEmptyAndPropertyIsValid_ReturnList() throws Exception {
        Map<String, String> joinCol = new HashMap<String, String>();
        joinCol.put("id", "1");

        Mockito.when(roleAppService.parseUserrolesJoinColumn("roleId")).thenReturn(joinCol);
        mvc
            .perform(
                get("/role/1/userroles?search=roleId[equals]=1&limit=10&offset=1")
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isOk());
    }

    @Test
    public void GetUserroles_searchIsNotEmpty() {
        Mockito.when(roleAppService.parseUserrolesJoinColumn(anyString())).thenReturn(null);

        org.assertj.core.api.Assertions
            .assertThatThrownBy(
                () ->
                    mvc
                        .perform(
                            get("/role/1/userroles?search=roleId[equals]=1&limit=10&offset=1")
                                .contentType(MediaType.APPLICATION_JSON)
                        )
                        .andExpect(status().isOk())
            )
            .hasCause(new EntityNotFoundException("Invalid join column"));
    }
}
