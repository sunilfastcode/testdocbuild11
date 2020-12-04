package com.fastcode.testdocbuild11.restcontrollers.core;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fastcode.testdocbuild11.application.core.authorization.permission.PermissionAppService;
import com.fastcode.testdocbuild11.application.core.authorization.role.RoleAppService;
import com.fastcode.testdocbuild11.application.core.authorization.rolepermission.RolepermissionAppService;
import com.fastcode.testdocbuild11.application.core.authorization.rolepermission.dto.*;
import com.fastcode.testdocbuild11.commons.logging.LoggingHelper;
import com.fastcode.testdocbuild11.domain.core.authorization.permission.IPermissionRepository;
import com.fastcode.testdocbuild11.domain.core.authorization.permission.PermissionEntity;
import com.fastcode.testdocbuild11.domain.core.authorization.role.IRoleRepository;
import com.fastcode.testdocbuild11.domain.core.authorization.role.RoleEntity;
import com.fastcode.testdocbuild11.domain.core.authorization.rolepermission.IRolepermissionRepository;
import com.fastcode.testdocbuild11.domain.core.authorization.rolepermission.RolepermissionEntity;
import com.fastcode.testdocbuild11.domain.core.authorization.rolepermission.RolepermissionId;
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
public class RolepermissionControllerTest {

    @Autowired
    protected SortHandlerMethodArgumentResolver sortArgumentResolver;

    @Autowired
    @Qualifier("rolepermissionRepository")
    protected IRolepermissionRepository rolepermission_repository;

    @Autowired
    @Qualifier("permissionRepository")
    protected IPermissionRepository permissionRepository;

    @Autowired
    @Qualifier("roleRepository")
    protected IRoleRepository roleRepository;

    @SpyBean
    @Qualifier("rolepermissionAppService")
    protected RolepermissionAppService rolepermissionAppService;

    @SpyBean
    @Qualifier("permissionAppService")
    protected PermissionAppService permissionAppService;

    @SpyBean
    @Qualifier("roleAppService")
    protected RoleAppService roleAppService;

    @SpyBean
    protected JWTAppService jwtAppService;

    @SpyBean
    protected LoggingHelper logHelper;

    @SpyBean
    protected Environment env;

    @Mock
    protected Logger loggerMock;

    protected RolepermissionEntity rolepermission;

    protected MockMvc mvc;

    @Autowired
    EntityManagerFactory emf;

    static EntityManagerFactory emfs;

    int count = 10;

    int countRole = 10;
    int countPermission = 10;

    @PostConstruct
    public void init() {
        emfs = emf;
    }

    @AfterClass
    public static void cleanup() {
        EntityManager em = emfs.createEntityManager();
        em.getTransaction().begin();
        em.createNativeQuery("SET REFERENTIAL_INTEGRITY FALSE").executeUpdate();
        em.createNativeQuery("truncate table public.rolepermission").executeUpdate();
        em.createNativeQuery("truncate table public.permission").executeUpdate();
        em.createNativeQuery("truncate table public.role").executeUpdate();
        em.createNativeQuery("SET REFERENTIAL_INTEGRITY TRUE").executeUpdate();
        em.getTransaction().commit();
    }

    public RoleEntity createRoleEntity() {
        if (countRole > 99) {
            countRole = 10;
        }

        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setDisplayName(String.valueOf(countRole));
        roleEntity.setId(Long.valueOf(countRole));
        roleEntity.setName(String.valueOf(countRole));
        roleEntity.setVersiono(0L);
        if (!roleRepository.findAll().contains(roleEntity)) {
            roleEntity = roleRepository.save(roleEntity);
        }
        countRole++;
        return roleEntity;
    }

    public PermissionEntity createPermissionEntity() {
        if (countPermission > 99) {
            countPermission = 10;
        }

        PermissionEntity permissionEntity = new PermissionEntity();
        permissionEntity.setDisplayName(String.valueOf(countPermission));
        permissionEntity.setId(Long.valueOf(countPermission));
        permissionEntity.setName(String.valueOf(countPermission));
        permissionEntity.setVersiono(0L);
        if (!permissionRepository.findAll().contains(permissionEntity)) {
            permissionEntity = permissionRepository.save(permissionEntity);
        }
        countPermission++;
        return permissionEntity;
    }

    public RolepermissionEntity createEntity() {
        PermissionEntity permission = createPermissionEntity();
        RoleEntity role = createRoleEntity();

        RolepermissionEntity rolepermissionEntity = new RolepermissionEntity();
        rolepermissionEntity.setPermissionId(1L);
        rolepermissionEntity.setRoleId(1L);
        rolepermissionEntity.setVersiono(0L);
        rolepermissionEntity.setPermission(permission);
        rolepermissionEntity.setPermissionId(Long.parseLong(permission.getId().toString()));
        rolepermissionEntity.setRole(role);
        rolepermissionEntity.setRoleId(Long.parseLong(role.getId().toString()));

        return rolepermissionEntity;
    }

    public CreateRolepermissionInput createRolepermissionInput() {
        CreateRolepermissionInput rolepermissionInput = new CreateRolepermissionInput();
        rolepermissionInput.setPermissionId(5L);
        rolepermissionInput.setRoleId(5L);

        return rolepermissionInput;
    }

    public RolepermissionEntity createNewEntity() {
        RolepermissionEntity rolepermission = new RolepermissionEntity();
        rolepermission.setPermissionId(3L);
        rolepermission.setRoleId(3L);

        return rolepermission;
    }

    public RolepermissionEntity createUpdateEntity() {
        RolepermissionEntity rolepermission = new RolepermissionEntity();
        rolepermission.setPermissionId(4L);
        rolepermission.setRoleId(4L);

        return rolepermission;
    }

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        final RolepermissionController rolepermissionController = new RolepermissionController(
            rolepermissionAppService,
            permissionAppService,
            roleAppService,
            logHelper,
            env
        );
        when(logHelper.getLogger()).thenReturn(loggerMock);
        doNothing().when(loggerMock).error(anyString());

        this.mvc =
            MockMvcBuilders
                .standaloneSetup(rolepermissionController)
                .setCustomArgumentResolvers(sortArgumentResolver)
                .setControllerAdvice()
                .build();
    }

    @Before
    public void initTest() {
        rolepermission = createEntity();
        List<RolepermissionEntity> list = rolepermission_repository.findAll();
        if (!list.contains(rolepermission)) {
            rolepermission = rolepermission_repository.save(rolepermission);
        }
    }

    @Test
    public void FindById_IdIsValid_ReturnStatusOk() throws Exception {
        mvc
            .perform(
                get(
                    "/rolepermission/permissionId=" +
                    rolepermission.getPermissionId() +
                    ",roleId=" +
                    rolepermission.getRoleId()
                )
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isOk());
    }

    @Test
    public void FindById_IdIsNotValid_ReturnStatusNotFound() {
        org.assertj.core.api.Assertions
            .assertThatThrownBy(
                () ->
                    mvc
                        .perform(
                            get("/rolepermission/permissionId=999,roleId=999").contentType(MediaType.APPLICATION_JSON)
                        )
                        .andExpect(status().isOk())
            )
            .hasCause(new EntityNotFoundException("Not found"));
    }

    @Test
    public void CreateRolepermission_RolepermissionDoesNotExist_ReturnStatusOk() throws Exception {
        CreateRolepermissionInput rolepermissionInput = createRolepermissionInput();

        PermissionEntity permission = createPermissionEntity();

        rolepermissionInput.setPermissionId(Long.parseLong(permission.getId().toString()));
        RoleEntity role = createRoleEntity();

        rolepermissionInput.setRoleId(Long.parseLong(role.getId().toString()));

        doNothing().when(rolepermissionAppService).deleteUserTokens(anyLong());
        ObjectWriter ow = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
            .writer()
            .withDefaultPrettyPrinter();

        String json = ow.writeValueAsString(rolepermissionInput);

        mvc
            .perform(post("/rolepermission").contentType(MediaType.APPLICATION_JSON).content(json))
            .andExpect(status().isOk());
    }

    @Test
    public void CreateRolepermission_permissionDoesNotExists_ThrowEntityNotFoundException() throws Exception {
        CreateRolepermissionInput rolepermission = createRolepermissionInput();
        ObjectWriter ow = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
            .writer()
            .withDefaultPrettyPrinter();

        String json = ow.writeValueAsString(rolepermission);

        org.assertj.core.api.Assertions.assertThatThrownBy(
            () ->
                mvc
                    .perform(post("/rolepermission").contentType(MediaType.APPLICATION_JSON).content(json))
                    .andExpect(status().isNotFound())
        );
    }

    @Test
    public void CreateRolepermission_roleDoesNotExists_ThrowEntityNotFoundException() throws Exception {
        CreateRolepermissionInput rolepermission = createRolepermissionInput();
        ObjectWriter ow = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
            .writer()
            .withDefaultPrettyPrinter();

        String json = ow.writeValueAsString(rolepermission);

        org.assertj.core.api.Assertions.assertThatThrownBy(
            () ->
                mvc
                    .perform(post("/rolepermission").contentType(MediaType.APPLICATION_JSON).content(json))
                    .andExpect(status().isNotFound())
        );
    }

    @Test
    public void DeleteRolepermission_IdIsNotValid_ThrowEntityNotFoundException() {
        doReturn(null).when(rolepermissionAppService).findById(new RolepermissionId(999L, 999L));
        org.assertj.core.api.Assertions
            .assertThatThrownBy(
                () ->
                    mvc
                        .perform(
                            delete("/rolepermission/permissionId=999,roleId=999")
                                .contentType(MediaType.APPLICATION_JSON)
                        )
                        .andExpect(status().isOk())
            )
            .hasCause(
                new EntityNotFoundException(
                    "There does not exist a rolepermission with a id=permissionId=999,roleId=999"
                )
            );
    }

    @Test
    public void Delete_IdIsValid_ReturnStatusNoContent() throws Exception {
        RolepermissionEntity entity = createNewEntity();
        entity.setVersiono(0L);
        PermissionEntity permission = createPermissionEntity();
        entity.setPermissionId(Long.parseLong(permission.getId().toString()));
        entity.setPermission(permission);
        RoleEntity role = createRoleEntity();
        entity.setRoleId(Long.parseLong(role.getId().toString()));
        entity.setRole(role);
        entity = rolepermission_repository.save(entity);

        FindRolepermissionByIdOutput output = new FindRolepermissionByIdOutput();
        output.setPermissionId(entity.getPermissionId());
        output.setRoleId(entity.getRoleId());

        //    Mockito.when(rolepermissionAppService.findById(new RolepermissionId(entity.getPermissionId(), entity.getRoleId()))).thenReturn(output);
        Mockito
            .doReturn(output)
            .when(rolepermissionAppService)
            .findById(new RolepermissionId(entity.getPermissionId(), entity.getRoleId()));

        doNothing().when(rolepermissionAppService).deleteUserTokens(anyLong());
        mvc
            .perform(
                delete("/rolepermission/permissionId=" + entity.getPermissionId() + ",roleId=" + entity.getRoleId())
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isNoContent());
    }

    @Test
    public void UpdateRolepermission_RolepermissionDoesNotExist_ReturnStatusNotFound() throws Exception {
        doReturn(null).when(rolepermissionAppService).findById(new RolepermissionId(999L, 999L));

        UpdateRolepermissionInput rolepermission = new UpdateRolepermissionInput();
        rolepermission.setPermissionId(999L);
        rolepermission.setRoleId(999L);

        ObjectWriter ow = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
            .writer()
            .withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(rolepermission);

        org.assertj.core.api.Assertions
            .assertThatThrownBy(
                () ->
                    mvc
                        .perform(
                            put("/rolepermission/permissionId=999,roleId=999")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json)
                        )
                        .andExpect(status().isOk())
            )
            .hasCause(
                new EntityNotFoundException(
                    "Unable to update. Rolepermission with id=permissionId=999,roleId=999 not found."
                )
            );
    }

    @Test
    public void UpdateRolepermission_RolepermissionExists_ReturnStatusOk() throws Exception {
        RolepermissionEntity entity = createUpdateEntity();
        entity.setVersiono(0L);

        PermissionEntity permission = createPermissionEntity();
        entity.setPermissionId(Long.parseLong(permission.getId().toString()));
        entity.setPermission(permission);
        RoleEntity role = createRoleEntity();
        entity.setRoleId(Long.parseLong(role.getId().toString()));
        entity.setRole(role);
        entity = rolepermission_repository.save(entity);
        FindRolepermissionByIdOutput output = new FindRolepermissionByIdOutput();
        output.setPermissionId(entity.getPermissionId());
        output.setRoleId(entity.getRoleId());
        output.setVersiono(entity.getVersiono());

        Mockito
            .when(rolepermissionAppService.findById(new RolepermissionId(entity.getPermissionId(), entity.getRoleId())))
            .thenReturn(output);

        UpdateRolepermissionInput rolepermissionInput = new UpdateRolepermissionInput();
        rolepermissionInput.setPermissionId(entity.getPermissionId());
        rolepermissionInput.setRoleId(entity.getRoleId());

        doNothing().when(rolepermissionAppService).deleteUserTokens(anyLong());

        ObjectWriter ow = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
            .writer()
            .withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(rolepermissionInput);

        mvc
            .perform(
                put("/rolepermission/permissionId=" + entity.getPermissionId() + ",roleId=" + entity.getRoleId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(json)
            )
            .andExpect(status().isOk());

        RolepermissionEntity de = createUpdateEntity();
        de.setPermissionId(entity.getPermissionId());
        de.setRoleId(entity.getRoleId());
        rolepermission_repository.delete(de);
    }

    @Test
    public void FindAll_SearchIsNotNullAndPropertyIsValid_ReturnStatusOk() throws Exception {
        mvc
            .perform(
                get("/rolepermission?search=permissionId[equals]=1&limit=10&offset=1")
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isOk());
    }

    @Test
    public void FindAll_SearchIsNotNullAndPropertyIsNotValid_ThrowException() throws Exception {
        org.assertj.core.api.Assertions
            .assertThatThrownBy(
                () ->
                    mvc
                        .perform(
                            get("/rolepermission?search=rolepermissionpermissionId[equals]=1&limit=10&offset=1")
                                .contentType(MediaType.APPLICATION_JSON)
                        )
                        .andExpect(status().isOk())
            )
            .hasCause(new Exception("Wrong URL Format: Property rolepermissionpermissionId not found!"));
    }

    @Test
    public void GetPermission_IdIsNotEmptyAndIdIsNotValid_ThrowException() {
        org.assertj.core.api.Assertions
            .assertThatThrownBy(
                () ->
                    mvc
                        .perform(
                            get("/rolepermission/permissionId999/permission").contentType(MediaType.APPLICATION_JSON)
                        )
                        .andExpect(status().isOk())
            )
            .hasCause(new EntityNotFoundException("Invalid id=permissionId999"));
    }

    @Test
    public void GetPermission_IdIsNotEmptyAndIdDoesNotExist_ReturnNotFound() {
        org.assertj.core.api.Assertions
            .assertThatThrownBy(
                () ->
                    mvc
                        .perform(
                            get("/rolepermission/permissionId=999,roleId=999/permission")
                                .contentType(MediaType.APPLICATION_JSON)
                        )
                        .andExpect(status().isOk())
            )
            .hasCause(new EntityNotFoundException("Not found"));
    }

    @Test
    public void GetPermission_searchIsNotEmptyAndPropertyIsValid_ReturnList() throws Exception {
        mvc
            .perform(
                get(
                    "/rolepermission/permissionId=" +
                    rolepermission.getPermissionId() +
                    ",roleId=" +
                    rolepermission.getRoleId() +
                    "/permission"
                )
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isOk());
    }

    @Test
    public void GetRole_IdIsNotEmptyAndIdIsNotValid_ThrowException() {
        org.assertj.core.api.Assertions
            .assertThatThrownBy(
                () ->
                    mvc
                        .perform(get("/rolepermission/permissionId999/role").contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
            )
            .hasCause(new EntityNotFoundException("Invalid id=permissionId999"));
    }

    @Test
    public void GetRole_IdIsNotEmptyAndIdDoesNotExist_ReturnNotFound() {
        org.assertj.core.api.Assertions
            .assertThatThrownBy(
                () ->
                    mvc
                        .perform(
                            get("/rolepermission/permissionId=999,roleId=999/role")
                                .contentType(MediaType.APPLICATION_JSON)
                        )
                        .andExpect(status().isOk())
            )
            .hasCause(new EntityNotFoundException("Not found"));
    }

    @Test
    public void GetRole_searchIsNotEmptyAndPropertyIsValid_ReturnList() throws Exception {
        mvc
            .perform(
                get(
                    "/rolepermission/permissionId=" +
                    rolepermission.getPermissionId() +
                    ",roleId=" +
                    rolepermission.getRoleId() +
                    "/role"
                )
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isOk());
    }
}
