package com.be4tech.b4carecollect.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.be4tech.b4carecollect.IntegrationTest;
import com.be4tech.b4carecollect.domain.UserDemographics;
import com.be4tech.b4carecollect.repository.UserDemographicsRepository;
import com.be4tech.b4carecollect.service.criteria.UserDemographicsCriteria;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link UserDemographicsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class UserDemographicsResourceIT {

    private static final String DEFAULT_USUARIO_ID = "AAAAAAAAAA";
    private static final String UPDATED_USUARIO_ID = "BBBBBBBBBB";

    private static final String DEFAULT_EMPRESA_ID = "AAAAAAAAAA";
    private static final String UPDATED_EMPRESA_ID = "BBBBBBBBBB";

    private static final String DEFAULT_GENDER = "AAAAAAAAAA";
    private static final String UPDATED_GENDER = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE_OF_BIRD = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_OF_BIRD = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_DATE_OF_BIRD = LocalDate.ofEpochDay(-1L);

    private static final Integer DEFAULT_AGE = 1;
    private static final Integer UPDATED_AGE = 2;
    private static final Integer SMALLER_AGE = 1 - 1;

    private static final String DEFAULT_COUNTRY = "AAAAAAAAAA";
    private static final String UPDATED_COUNTRY = "BBBBBBBBBB";

    private static final String DEFAULT_STATE = "AAAAAAAAAA";
    private static final String UPDATED_STATE = "BBBBBBBBBB";

    private static final String DEFAULT_CITY = "AAAAAAAAAA";
    private static final String UPDATED_CITY = "BBBBBBBBBB";

    private static final String DEFAULT_ETHNICITY = "AAAAAAAAAA";
    private static final String UPDATED_ETHNICITY = "BBBBBBBBBB";

    private static final String DEFAULT_INCOME = "AAAAAAAAAA";
    private static final String UPDATED_INCOME = "BBBBBBBBBB";

    private static final String DEFAULT_MARITAL_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_MARITAL_STATUS = "BBBBBBBBBB";

    private static final String DEFAULT_EDUCATION = "AAAAAAAAAA";
    private static final String UPDATED_EDUCATION = "BBBBBBBBBB";

    private static final Instant DEFAULT_END_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_END_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/user-demographics";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private UserDemographicsRepository userDemographicsRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restUserDemographicsMockMvc;

    private UserDemographics userDemographics;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserDemographics createEntity(EntityManager em) {
        UserDemographics userDemographics = new UserDemographics()
            .usuarioId(DEFAULT_USUARIO_ID)
            .empresaId(DEFAULT_EMPRESA_ID)
            .gender(DEFAULT_GENDER)
            .dateOfBird(DEFAULT_DATE_OF_BIRD)
            .age(DEFAULT_AGE)
            .country(DEFAULT_COUNTRY)
            .state(DEFAULT_STATE)
            .city(DEFAULT_CITY)
            .ethnicity(DEFAULT_ETHNICITY)
            .income(DEFAULT_INCOME)
            .maritalStatus(DEFAULT_MARITAL_STATUS)
            .education(DEFAULT_EDUCATION)
            .endTime(DEFAULT_END_TIME);
        return userDemographics;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserDemographics createUpdatedEntity(EntityManager em) {
        UserDemographics userDemographics = new UserDemographics()
            .usuarioId(UPDATED_USUARIO_ID)
            .empresaId(UPDATED_EMPRESA_ID)
            .gender(UPDATED_GENDER)
            .dateOfBird(UPDATED_DATE_OF_BIRD)
            .age(UPDATED_AGE)
            .country(UPDATED_COUNTRY)
            .state(UPDATED_STATE)
            .city(UPDATED_CITY)
            .ethnicity(UPDATED_ETHNICITY)
            .income(UPDATED_INCOME)
            .maritalStatus(UPDATED_MARITAL_STATUS)
            .education(UPDATED_EDUCATION)
            .endTime(UPDATED_END_TIME);
        return userDemographics;
    }

    @BeforeEach
    public void initTest() {
        userDemographics = createEntity(em);
    }

    @Test
    @Transactional
    void createUserDemographics() throws Exception {
        int databaseSizeBeforeCreate = userDemographicsRepository.findAll().size();
        // Create the UserDemographics
        restUserDemographicsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(userDemographics))
            )
            .andExpect(status().isCreated());

        // Validate the UserDemographics in the database
        List<UserDemographics> userDemographicsList = userDemographicsRepository.findAll();
        assertThat(userDemographicsList).hasSize(databaseSizeBeforeCreate + 1);
        UserDemographics testUserDemographics = userDemographicsList.get(userDemographicsList.size() - 1);
        assertThat(testUserDemographics.getUsuarioId()).isEqualTo(DEFAULT_USUARIO_ID);
        assertThat(testUserDemographics.getEmpresaId()).isEqualTo(DEFAULT_EMPRESA_ID);
        assertThat(testUserDemographics.getGender()).isEqualTo(DEFAULT_GENDER);
        assertThat(testUserDemographics.getDateOfBird()).isEqualTo(DEFAULT_DATE_OF_BIRD);
        assertThat(testUserDemographics.getAge()).isEqualTo(DEFAULT_AGE);
        assertThat(testUserDemographics.getCountry()).isEqualTo(DEFAULT_COUNTRY);
        assertThat(testUserDemographics.getState()).isEqualTo(DEFAULT_STATE);
        assertThat(testUserDemographics.getCity()).isEqualTo(DEFAULT_CITY);
        assertThat(testUserDemographics.getEthnicity()).isEqualTo(DEFAULT_ETHNICITY);
        assertThat(testUserDemographics.getIncome()).isEqualTo(DEFAULT_INCOME);
        assertThat(testUserDemographics.getMaritalStatus()).isEqualTo(DEFAULT_MARITAL_STATUS);
        assertThat(testUserDemographics.getEducation()).isEqualTo(DEFAULT_EDUCATION);
        assertThat(testUserDemographics.getEndTime()).isEqualTo(DEFAULT_END_TIME);
    }

    @Test
    @Transactional
    void createUserDemographicsWithExistingId() throws Exception {
        // Create the UserDemographics with an existing ID
        userDemographicsRepository.saveAndFlush(userDemographics);

        int databaseSizeBeforeCreate = userDemographicsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserDemographicsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(userDemographics))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserDemographics in the database
        List<UserDemographics> userDemographicsList = userDemographicsRepository.findAll();
        assertThat(userDemographicsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllUserDemographics() throws Exception {
        // Initialize the database
        userDemographicsRepository.saveAndFlush(userDemographics);

        // Get all the userDemographicsList
        restUserDemographicsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userDemographics.getId().toString())))
            .andExpect(jsonPath("$.[*].usuarioId").value(hasItem(DEFAULT_USUARIO_ID)))
            .andExpect(jsonPath("$.[*].empresaId").value(hasItem(DEFAULT_EMPRESA_ID)))
            .andExpect(jsonPath("$.[*].gender").value(hasItem(DEFAULT_GENDER)))
            .andExpect(jsonPath("$.[*].dateOfBird").value(hasItem(DEFAULT_DATE_OF_BIRD.toString())))
            .andExpect(jsonPath("$.[*].age").value(hasItem(DEFAULT_AGE)))
            .andExpect(jsonPath("$.[*].country").value(hasItem(DEFAULT_COUNTRY)))
            .andExpect(jsonPath("$.[*].state").value(hasItem(DEFAULT_STATE)))
            .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY)))
            .andExpect(jsonPath("$.[*].ethnicity").value(hasItem(DEFAULT_ETHNICITY)))
            .andExpect(jsonPath("$.[*].income").value(hasItem(DEFAULT_INCOME)))
            .andExpect(jsonPath("$.[*].maritalStatus").value(hasItem(DEFAULT_MARITAL_STATUS)))
            .andExpect(jsonPath("$.[*].education").value(hasItem(DEFAULT_EDUCATION)))
            .andExpect(jsonPath("$.[*].endTime").value(hasItem(DEFAULT_END_TIME.toString())));
    }

    @Test
    @Transactional
    void getUserDemographics() throws Exception {
        // Initialize the database
        userDemographicsRepository.saveAndFlush(userDemographics);

        // Get the userDemographics
        restUserDemographicsMockMvc
            .perform(get(ENTITY_API_URL_ID, userDemographics.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(userDemographics.getId().toString()))
            .andExpect(jsonPath("$.usuarioId").value(DEFAULT_USUARIO_ID))
            .andExpect(jsonPath("$.empresaId").value(DEFAULT_EMPRESA_ID))
            .andExpect(jsonPath("$.gender").value(DEFAULT_GENDER))
            .andExpect(jsonPath("$.dateOfBird").value(DEFAULT_DATE_OF_BIRD.toString()))
            .andExpect(jsonPath("$.age").value(DEFAULT_AGE))
            .andExpect(jsonPath("$.country").value(DEFAULT_COUNTRY))
            .andExpect(jsonPath("$.state").value(DEFAULT_STATE))
            .andExpect(jsonPath("$.city").value(DEFAULT_CITY))
            .andExpect(jsonPath("$.ethnicity").value(DEFAULT_ETHNICITY))
            .andExpect(jsonPath("$.income").value(DEFAULT_INCOME))
            .andExpect(jsonPath("$.maritalStatus").value(DEFAULT_MARITAL_STATUS))
            .andExpect(jsonPath("$.education").value(DEFAULT_EDUCATION))
            .andExpect(jsonPath("$.endTime").value(DEFAULT_END_TIME.toString()));
    }

    @Test
    @Transactional
    void getUserDemographicsByIdFiltering() throws Exception {
        // Initialize the database
        userDemographicsRepository.saveAndFlush(userDemographics);

        UUID id = userDemographics.getId();

        defaultUserDemographicsShouldBeFound("id.equals=" + id);
        defaultUserDemographicsShouldNotBeFound("id.notEquals=" + id);
    }

    @Test
    @Transactional
    void getAllUserDemographicsByUsuarioIdIsEqualToSomething() throws Exception {
        // Initialize the database
        userDemographicsRepository.saveAndFlush(userDemographics);

        // Get all the userDemographicsList where usuarioId equals to DEFAULT_USUARIO_ID
        defaultUserDemographicsShouldBeFound("usuarioId.equals=" + DEFAULT_USUARIO_ID);

        // Get all the userDemographicsList where usuarioId equals to UPDATED_USUARIO_ID
        defaultUserDemographicsShouldNotBeFound("usuarioId.equals=" + UPDATED_USUARIO_ID);
    }

    @Test
    @Transactional
    void getAllUserDemographicsByUsuarioIdIsInShouldWork() throws Exception {
        // Initialize the database
        userDemographicsRepository.saveAndFlush(userDemographics);

        // Get all the userDemographicsList where usuarioId in DEFAULT_USUARIO_ID or UPDATED_USUARIO_ID
        defaultUserDemographicsShouldBeFound("usuarioId.in=" + DEFAULT_USUARIO_ID + "," + UPDATED_USUARIO_ID);

        // Get all the userDemographicsList where usuarioId equals to UPDATED_USUARIO_ID
        defaultUserDemographicsShouldNotBeFound("usuarioId.in=" + UPDATED_USUARIO_ID);
    }

    @Test
    @Transactional
    void getAllUserDemographicsByUsuarioIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        userDemographicsRepository.saveAndFlush(userDemographics);

        // Get all the userDemographicsList where usuarioId is not null
        defaultUserDemographicsShouldBeFound("usuarioId.specified=true");

        // Get all the userDemographicsList where usuarioId is null
        defaultUserDemographicsShouldNotBeFound("usuarioId.specified=false");
    }

    @Test
    @Transactional
    void getAllUserDemographicsByUsuarioIdContainsSomething() throws Exception {
        // Initialize the database
        userDemographicsRepository.saveAndFlush(userDemographics);

        // Get all the userDemographicsList where usuarioId contains DEFAULT_USUARIO_ID
        defaultUserDemographicsShouldBeFound("usuarioId.contains=" + DEFAULT_USUARIO_ID);

        // Get all the userDemographicsList where usuarioId contains UPDATED_USUARIO_ID
        defaultUserDemographicsShouldNotBeFound("usuarioId.contains=" + UPDATED_USUARIO_ID);
    }

    @Test
    @Transactional
    void getAllUserDemographicsByUsuarioIdNotContainsSomething() throws Exception {
        // Initialize the database
        userDemographicsRepository.saveAndFlush(userDemographics);

        // Get all the userDemographicsList where usuarioId does not contain DEFAULT_USUARIO_ID
        defaultUserDemographicsShouldNotBeFound("usuarioId.doesNotContain=" + DEFAULT_USUARIO_ID);

        // Get all the userDemographicsList where usuarioId does not contain UPDATED_USUARIO_ID
        defaultUserDemographicsShouldBeFound("usuarioId.doesNotContain=" + UPDATED_USUARIO_ID);
    }

    @Test
    @Transactional
    void getAllUserDemographicsByEmpresaIdIsEqualToSomething() throws Exception {
        // Initialize the database
        userDemographicsRepository.saveAndFlush(userDemographics);

        // Get all the userDemographicsList where empresaId equals to DEFAULT_EMPRESA_ID
        defaultUserDemographicsShouldBeFound("empresaId.equals=" + DEFAULT_EMPRESA_ID);

        // Get all the userDemographicsList where empresaId equals to UPDATED_EMPRESA_ID
        defaultUserDemographicsShouldNotBeFound("empresaId.equals=" + UPDATED_EMPRESA_ID);
    }

    @Test
    @Transactional
    void getAllUserDemographicsByEmpresaIdIsInShouldWork() throws Exception {
        // Initialize the database
        userDemographicsRepository.saveAndFlush(userDemographics);

        // Get all the userDemographicsList where empresaId in DEFAULT_EMPRESA_ID or UPDATED_EMPRESA_ID
        defaultUserDemographicsShouldBeFound("empresaId.in=" + DEFAULT_EMPRESA_ID + "," + UPDATED_EMPRESA_ID);

        // Get all the userDemographicsList where empresaId equals to UPDATED_EMPRESA_ID
        defaultUserDemographicsShouldNotBeFound("empresaId.in=" + UPDATED_EMPRESA_ID);
    }

    @Test
    @Transactional
    void getAllUserDemographicsByEmpresaIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        userDemographicsRepository.saveAndFlush(userDemographics);

        // Get all the userDemographicsList where empresaId is not null
        defaultUserDemographicsShouldBeFound("empresaId.specified=true");

        // Get all the userDemographicsList where empresaId is null
        defaultUserDemographicsShouldNotBeFound("empresaId.specified=false");
    }

    @Test
    @Transactional
    void getAllUserDemographicsByEmpresaIdContainsSomething() throws Exception {
        // Initialize the database
        userDemographicsRepository.saveAndFlush(userDemographics);

        // Get all the userDemographicsList where empresaId contains DEFAULT_EMPRESA_ID
        defaultUserDemographicsShouldBeFound("empresaId.contains=" + DEFAULT_EMPRESA_ID);

        // Get all the userDemographicsList where empresaId contains UPDATED_EMPRESA_ID
        defaultUserDemographicsShouldNotBeFound("empresaId.contains=" + UPDATED_EMPRESA_ID);
    }

    @Test
    @Transactional
    void getAllUserDemographicsByEmpresaIdNotContainsSomething() throws Exception {
        // Initialize the database
        userDemographicsRepository.saveAndFlush(userDemographics);

        // Get all the userDemographicsList where empresaId does not contain DEFAULT_EMPRESA_ID
        defaultUserDemographicsShouldNotBeFound("empresaId.doesNotContain=" + DEFAULT_EMPRESA_ID);

        // Get all the userDemographicsList where empresaId does not contain UPDATED_EMPRESA_ID
        defaultUserDemographicsShouldBeFound("empresaId.doesNotContain=" + UPDATED_EMPRESA_ID);
    }

    @Test
    @Transactional
    void getAllUserDemographicsByGenderIsEqualToSomething() throws Exception {
        // Initialize the database
        userDemographicsRepository.saveAndFlush(userDemographics);

        // Get all the userDemographicsList where gender equals to DEFAULT_GENDER
        defaultUserDemographicsShouldBeFound("gender.equals=" + DEFAULT_GENDER);

        // Get all the userDemographicsList where gender equals to UPDATED_GENDER
        defaultUserDemographicsShouldNotBeFound("gender.equals=" + UPDATED_GENDER);
    }

    @Test
    @Transactional
    void getAllUserDemographicsByGenderIsInShouldWork() throws Exception {
        // Initialize the database
        userDemographicsRepository.saveAndFlush(userDemographics);

        // Get all the userDemographicsList where gender in DEFAULT_GENDER or UPDATED_GENDER
        defaultUserDemographicsShouldBeFound("gender.in=" + DEFAULT_GENDER + "," + UPDATED_GENDER);

        // Get all the userDemographicsList where gender equals to UPDATED_GENDER
        defaultUserDemographicsShouldNotBeFound("gender.in=" + UPDATED_GENDER);
    }

    @Test
    @Transactional
    void getAllUserDemographicsByGenderIsNullOrNotNull() throws Exception {
        // Initialize the database
        userDemographicsRepository.saveAndFlush(userDemographics);

        // Get all the userDemographicsList where gender is not null
        defaultUserDemographicsShouldBeFound("gender.specified=true");

        // Get all the userDemographicsList where gender is null
        defaultUserDemographicsShouldNotBeFound("gender.specified=false");
    }

    @Test
    @Transactional
    void getAllUserDemographicsByGenderContainsSomething() throws Exception {
        // Initialize the database
        userDemographicsRepository.saveAndFlush(userDemographics);

        // Get all the userDemographicsList where gender contains DEFAULT_GENDER
        defaultUserDemographicsShouldBeFound("gender.contains=" + DEFAULT_GENDER);

        // Get all the userDemographicsList where gender contains UPDATED_GENDER
        defaultUserDemographicsShouldNotBeFound("gender.contains=" + UPDATED_GENDER);
    }

    @Test
    @Transactional
    void getAllUserDemographicsByGenderNotContainsSomething() throws Exception {
        // Initialize the database
        userDemographicsRepository.saveAndFlush(userDemographics);

        // Get all the userDemographicsList where gender does not contain DEFAULT_GENDER
        defaultUserDemographicsShouldNotBeFound("gender.doesNotContain=" + DEFAULT_GENDER);

        // Get all the userDemographicsList where gender does not contain UPDATED_GENDER
        defaultUserDemographicsShouldBeFound("gender.doesNotContain=" + UPDATED_GENDER);
    }

    @Test
    @Transactional
    void getAllUserDemographicsByDateOfBirdIsEqualToSomething() throws Exception {
        // Initialize the database
        userDemographicsRepository.saveAndFlush(userDemographics);

        // Get all the userDemographicsList where dateOfBird equals to DEFAULT_DATE_OF_BIRD
        defaultUserDemographicsShouldBeFound("dateOfBird.equals=" + DEFAULT_DATE_OF_BIRD);

        // Get all the userDemographicsList where dateOfBird equals to UPDATED_DATE_OF_BIRD
        defaultUserDemographicsShouldNotBeFound("dateOfBird.equals=" + UPDATED_DATE_OF_BIRD);
    }

    @Test
    @Transactional
    void getAllUserDemographicsByDateOfBirdIsInShouldWork() throws Exception {
        // Initialize the database
        userDemographicsRepository.saveAndFlush(userDemographics);

        // Get all the userDemographicsList where dateOfBird in DEFAULT_DATE_OF_BIRD or UPDATED_DATE_OF_BIRD
        defaultUserDemographicsShouldBeFound("dateOfBird.in=" + DEFAULT_DATE_OF_BIRD + "," + UPDATED_DATE_OF_BIRD);

        // Get all the userDemographicsList where dateOfBird equals to UPDATED_DATE_OF_BIRD
        defaultUserDemographicsShouldNotBeFound("dateOfBird.in=" + UPDATED_DATE_OF_BIRD);
    }

    @Test
    @Transactional
    void getAllUserDemographicsByDateOfBirdIsNullOrNotNull() throws Exception {
        // Initialize the database
        userDemographicsRepository.saveAndFlush(userDemographics);

        // Get all the userDemographicsList where dateOfBird is not null
        defaultUserDemographicsShouldBeFound("dateOfBird.specified=true");

        // Get all the userDemographicsList where dateOfBird is null
        defaultUserDemographicsShouldNotBeFound("dateOfBird.specified=false");
    }

    @Test
    @Transactional
    void getAllUserDemographicsByDateOfBirdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        userDemographicsRepository.saveAndFlush(userDemographics);

        // Get all the userDemographicsList where dateOfBird is greater than or equal to DEFAULT_DATE_OF_BIRD
        defaultUserDemographicsShouldBeFound("dateOfBird.greaterThanOrEqual=" + DEFAULT_DATE_OF_BIRD);

        // Get all the userDemographicsList where dateOfBird is greater than or equal to UPDATED_DATE_OF_BIRD
        defaultUserDemographicsShouldNotBeFound("dateOfBird.greaterThanOrEqual=" + UPDATED_DATE_OF_BIRD);
    }

    @Test
    @Transactional
    void getAllUserDemographicsByDateOfBirdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        userDemographicsRepository.saveAndFlush(userDemographics);

        // Get all the userDemographicsList where dateOfBird is less than or equal to DEFAULT_DATE_OF_BIRD
        defaultUserDemographicsShouldBeFound("dateOfBird.lessThanOrEqual=" + DEFAULT_DATE_OF_BIRD);

        // Get all the userDemographicsList where dateOfBird is less than or equal to SMALLER_DATE_OF_BIRD
        defaultUserDemographicsShouldNotBeFound("dateOfBird.lessThanOrEqual=" + SMALLER_DATE_OF_BIRD);
    }

    @Test
    @Transactional
    void getAllUserDemographicsByDateOfBirdIsLessThanSomething() throws Exception {
        // Initialize the database
        userDemographicsRepository.saveAndFlush(userDemographics);

        // Get all the userDemographicsList where dateOfBird is less than DEFAULT_DATE_OF_BIRD
        defaultUserDemographicsShouldNotBeFound("dateOfBird.lessThan=" + DEFAULT_DATE_OF_BIRD);

        // Get all the userDemographicsList where dateOfBird is less than UPDATED_DATE_OF_BIRD
        defaultUserDemographicsShouldBeFound("dateOfBird.lessThan=" + UPDATED_DATE_OF_BIRD);
    }

    @Test
    @Transactional
    void getAllUserDemographicsByDateOfBirdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        userDemographicsRepository.saveAndFlush(userDemographics);

        // Get all the userDemographicsList where dateOfBird is greater than DEFAULT_DATE_OF_BIRD
        defaultUserDemographicsShouldNotBeFound("dateOfBird.greaterThan=" + DEFAULT_DATE_OF_BIRD);

        // Get all the userDemographicsList where dateOfBird is greater than SMALLER_DATE_OF_BIRD
        defaultUserDemographicsShouldBeFound("dateOfBird.greaterThan=" + SMALLER_DATE_OF_BIRD);
    }

    @Test
    @Transactional
    void getAllUserDemographicsByAgeIsEqualToSomething() throws Exception {
        // Initialize the database
        userDemographicsRepository.saveAndFlush(userDemographics);

        // Get all the userDemographicsList where age equals to DEFAULT_AGE
        defaultUserDemographicsShouldBeFound("age.equals=" + DEFAULT_AGE);

        // Get all the userDemographicsList where age equals to UPDATED_AGE
        defaultUserDemographicsShouldNotBeFound("age.equals=" + UPDATED_AGE);
    }

    @Test
    @Transactional
    void getAllUserDemographicsByAgeIsInShouldWork() throws Exception {
        // Initialize the database
        userDemographicsRepository.saveAndFlush(userDemographics);

        // Get all the userDemographicsList where age in DEFAULT_AGE or UPDATED_AGE
        defaultUserDemographicsShouldBeFound("age.in=" + DEFAULT_AGE + "," + UPDATED_AGE);

        // Get all the userDemographicsList where age equals to UPDATED_AGE
        defaultUserDemographicsShouldNotBeFound("age.in=" + UPDATED_AGE);
    }

    @Test
    @Transactional
    void getAllUserDemographicsByAgeIsNullOrNotNull() throws Exception {
        // Initialize the database
        userDemographicsRepository.saveAndFlush(userDemographics);

        // Get all the userDemographicsList where age is not null
        defaultUserDemographicsShouldBeFound("age.specified=true");

        // Get all the userDemographicsList where age is null
        defaultUserDemographicsShouldNotBeFound("age.specified=false");
    }

    @Test
    @Transactional
    void getAllUserDemographicsByAgeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        userDemographicsRepository.saveAndFlush(userDemographics);

        // Get all the userDemographicsList where age is greater than or equal to DEFAULT_AGE
        defaultUserDemographicsShouldBeFound("age.greaterThanOrEqual=" + DEFAULT_AGE);

        // Get all the userDemographicsList where age is greater than or equal to UPDATED_AGE
        defaultUserDemographicsShouldNotBeFound("age.greaterThanOrEqual=" + UPDATED_AGE);
    }

    @Test
    @Transactional
    void getAllUserDemographicsByAgeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        userDemographicsRepository.saveAndFlush(userDemographics);

        // Get all the userDemographicsList where age is less than or equal to DEFAULT_AGE
        defaultUserDemographicsShouldBeFound("age.lessThanOrEqual=" + DEFAULT_AGE);

        // Get all the userDemographicsList where age is less than or equal to SMALLER_AGE
        defaultUserDemographicsShouldNotBeFound("age.lessThanOrEqual=" + SMALLER_AGE);
    }

    @Test
    @Transactional
    void getAllUserDemographicsByAgeIsLessThanSomething() throws Exception {
        // Initialize the database
        userDemographicsRepository.saveAndFlush(userDemographics);

        // Get all the userDemographicsList where age is less than DEFAULT_AGE
        defaultUserDemographicsShouldNotBeFound("age.lessThan=" + DEFAULT_AGE);

        // Get all the userDemographicsList where age is less than UPDATED_AGE
        defaultUserDemographicsShouldBeFound("age.lessThan=" + UPDATED_AGE);
    }

    @Test
    @Transactional
    void getAllUserDemographicsByAgeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        userDemographicsRepository.saveAndFlush(userDemographics);

        // Get all the userDemographicsList where age is greater than DEFAULT_AGE
        defaultUserDemographicsShouldNotBeFound("age.greaterThan=" + DEFAULT_AGE);

        // Get all the userDemographicsList where age is greater than SMALLER_AGE
        defaultUserDemographicsShouldBeFound("age.greaterThan=" + SMALLER_AGE);
    }

    @Test
    @Transactional
    void getAllUserDemographicsByCountryIsEqualToSomething() throws Exception {
        // Initialize the database
        userDemographicsRepository.saveAndFlush(userDemographics);

        // Get all the userDemographicsList where country equals to DEFAULT_COUNTRY
        defaultUserDemographicsShouldBeFound("country.equals=" + DEFAULT_COUNTRY);

        // Get all the userDemographicsList where country equals to UPDATED_COUNTRY
        defaultUserDemographicsShouldNotBeFound("country.equals=" + UPDATED_COUNTRY);
    }

    @Test
    @Transactional
    void getAllUserDemographicsByCountryIsInShouldWork() throws Exception {
        // Initialize the database
        userDemographicsRepository.saveAndFlush(userDemographics);

        // Get all the userDemographicsList where country in DEFAULT_COUNTRY or UPDATED_COUNTRY
        defaultUserDemographicsShouldBeFound("country.in=" + DEFAULT_COUNTRY + "," + UPDATED_COUNTRY);

        // Get all the userDemographicsList where country equals to UPDATED_COUNTRY
        defaultUserDemographicsShouldNotBeFound("country.in=" + UPDATED_COUNTRY);
    }

    @Test
    @Transactional
    void getAllUserDemographicsByCountryIsNullOrNotNull() throws Exception {
        // Initialize the database
        userDemographicsRepository.saveAndFlush(userDemographics);

        // Get all the userDemographicsList where country is not null
        defaultUserDemographicsShouldBeFound("country.specified=true");

        // Get all the userDemographicsList where country is null
        defaultUserDemographicsShouldNotBeFound("country.specified=false");
    }

    @Test
    @Transactional
    void getAllUserDemographicsByCountryContainsSomething() throws Exception {
        // Initialize the database
        userDemographicsRepository.saveAndFlush(userDemographics);

        // Get all the userDemographicsList where country contains DEFAULT_COUNTRY
        defaultUserDemographicsShouldBeFound("country.contains=" + DEFAULT_COUNTRY);

        // Get all the userDemographicsList where country contains UPDATED_COUNTRY
        defaultUserDemographicsShouldNotBeFound("country.contains=" + UPDATED_COUNTRY);
    }

    @Test
    @Transactional
    void getAllUserDemographicsByCountryNotContainsSomething() throws Exception {
        // Initialize the database
        userDemographicsRepository.saveAndFlush(userDemographics);

        // Get all the userDemographicsList where country does not contain DEFAULT_COUNTRY
        defaultUserDemographicsShouldNotBeFound("country.doesNotContain=" + DEFAULT_COUNTRY);

        // Get all the userDemographicsList where country does not contain UPDATED_COUNTRY
        defaultUserDemographicsShouldBeFound("country.doesNotContain=" + UPDATED_COUNTRY);
    }

    @Test
    @Transactional
    void getAllUserDemographicsByStateIsEqualToSomething() throws Exception {
        // Initialize the database
        userDemographicsRepository.saveAndFlush(userDemographics);

        // Get all the userDemographicsList where state equals to DEFAULT_STATE
        defaultUserDemographicsShouldBeFound("state.equals=" + DEFAULT_STATE);

        // Get all the userDemographicsList where state equals to UPDATED_STATE
        defaultUserDemographicsShouldNotBeFound("state.equals=" + UPDATED_STATE);
    }

    @Test
    @Transactional
    void getAllUserDemographicsByStateIsInShouldWork() throws Exception {
        // Initialize the database
        userDemographicsRepository.saveAndFlush(userDemographics);

        // Get all the userDemographicsList where state in DEFAULT_STATE or UPDATED_STATE
        defaultUserDemographicsShouldBeFound("state.in=" + DEFAULT_STATE + "," + UPDATED_STATE);

        // Get all the userDemographicsList where state equals to UPDATED_STATE
        defaultUserDemographicsShouldNotBeFound("state.in=" + UPDATED_STATE);
    }

    @Test
    @Transactional
    void getAllUserDemographicsByStateIsNullOrNotNull() throws Exception {
        // Initialize the database
        userDemographicsRepository.saveAndFlush(userDemographics);

        // Get all the userDemographicsList where state is not null
        defaultUserDemographicsShouldBeFound("state.specified=true");

        // Get all the userDemographicsList where state is null
        defaultUserDemographicsShouldNotBeFound("state.specified=false");
    }

    @Test
    @Transactional
    void getAllUserDemographicsByStateContainsSomething() throws Exception {
        // Initialize the database
        userDemographicsRepository.saveAndFlush(userDemographics);

        // Get all the userDemographicsList where state contains DEFAULT_STATE
        defaultUserDemographicsShouldBeFound("state.contains=" + DEFAULT_STATE);

        // Get all the userDemographicsList where state contains UPDATED_STATE
        defaultUserDemographicsShouldNotBeFound("state.contains=" + UPDATED_STATE);
    }

    @Test
    @Transactional
    void getAllUserDemographicsByStateNotContainsSomething() throws Exception {
        // Initialize the database
        userDemographicsRepository.saveAndFlush(userDemographics);

        // Get all the userDemographicsList where state does not contain DEFAULT_STATE
        defaultUserDemographicsShouldNotBeFound("state.doesNotContain=" + DEFAULT_STATE);

        // Get all the userDemographicsList where state does not contain UPDATED_STATE
        defaultUserDemographicsShouldBeFound("state.doesNotContain=" + UPDATED_STATE);
    }

    @Test
    @Transactional
    void getAllUserDemographicsByCityIsEqualToSomething() throws Exception {
        // Initialize the database
        userDemographicsRepository.saveAndFlush(userDemographics);

        // Get all the userDemographicsList where city equals to DEFAULT_CITY
        defaultUserDemographicsShouldBeFound("city.equals=" + DEFAULT_CITY);

        // Get all the userDemographicsList where city equals to UPDATED_CITY
        defaultUserDemographicsShouldNotBeFound("city.equals=" + UPDATED_CITY);
    }

    @Test
    @Transactional
    void getAllUserDemographicsByCityIsInShouldWork() throws Exception {
        // Initialize the database
        userDemographicsRepository.saveAndFlush(userDemographics);

        // Get all the userDemographicsList where city in DEFAULT_CITY or UPDATED_CITY
        defaultUserDemographicsShouldBeFound("city.in=" + DEFAULT_CITY + "," + UPDATED_CITY);

        // Get all the userDemographicsList where city equals to UPDATED_CITY
        defaultUserDemographicsShouldNotBeFound("city.in=" + UPDATED_CITY);
    }

    @Test
    @Transactional
    void getAllUserDemographicsByCityIsNullOrNotNull() throws Exception {
        // Initialize the database
        userDemographicsRepository.saveAndFlush(userDemographics);

        // Get all the userDemographicsList where city is not null
        defaultUserDemographicsShouldBeFound("city.specified=true");

        // Get all the userDemographicsList where city is null
        defaultUserDemographicsShouldNotBeFound("city.specified=false");
    }

    @Test
    @Transactional
    void getAllUserDemographicsByCityContainsSomething() throws Exception {
        // Initialize the database
        userDemographicsRepository.saveAndFlush(userDemographics);

        // Get all the userDemographicsList where city contains DEFAULT_CITY
        defaultUserDemographicsShouldBeFound("city.contains=" + DEFAULT_CITY);

        // Get all the userDemographicsList where city contains UPDATED_CITY
        defaultUserDemographicsShouldNotBeFound("city.contains=" + UPDATED_CITY);
    }

    @Test
    @Transactional
    void getAllUserDemographicsByCityNotContainsSomething() throws Exception {
        // Initialize the database
        userDemographicsRepository.saveAndFlush(userDemographics);

        // Get all the userDemographicsList where city does not contain DEFAULT_CITY
        defaultUserDemographicsShouldNotBeFound("city.doesNotContain=" + DEFAULT_CITY);

        // Get all the userDemographicsList where city does not contain UPDATED_CITY
        defaultUserDemographicsShouldBeFound("city.doesNotContain=" + UPDATED_CITY);
    }

    @Test
    @Transactional
    void getAllUserDemographicsByEthnicityIsEqualToSomething() throws Exception {
        // Initialize the database
        userDemographicsRepository.saveAndFlush(userDemographics);

        // Get all the userDemographicsList where ethnicity equals to DEFAULT_ETHNICITY
        defaultUserDemographicsShouldBeFound("ethnicity.equals=" + DEFAULT_ETHNICITY);

        // Get all the userDemographicsList where ethnicity equals to UPDATED_ETHNICITY
        defaultUserDemographicsShouldNotBeFound("ethnicity.equals=" + UPDATED_ETHNICITY);
    }

    @Test
    @Transactional
    void getAllUserDemographicsByEthnicityIsInShouldWork() throws Exception {
        // Initialize the database
        userDemographicsRepository.saveAndFlush(userDemographics);

        // Get all the userDemographicsList where ethnicity in DEFAULT_ETHNICITY or UPDATED_ETHNICITY
        defaultUserDemographicsShouldBeFound("ethnicity.in=" + DEFAULT_ETHNICITY + "," + UPDATED_ETHNICITY);

        // Get all the userDemographicsList where ethnicity equals to UPDATED_ETHNICITY
        defaultUserDemographicsShouldNotBeFound("ethnicity.in=" + UPDATED_ETHNICITY);
    }

    @Test
    @Transactional
    void getAllUserDemographicsByEthnicityIsNullOrNotNull() throws Exception {
        // Initialize the database
        userDemographicsRepository.saveAndFlush(userDemographics);

        // Get all the userDemographicsList where ethnicity is not null
        defaultUserDemographicsShouldBeFound("ethnicity.specified=true");

        // Get all the userDemographicsList where ethnicity is null
        defaultUserDemographicsShouldNotBeFound("ethnicity.specified=false");
    }

    @Test
    @Transactional
    void getAllUserDemographicsByEthnicityContainsSomething() throws Exception {
        // Initialize the database
        userDemographicsRepository.saveAndFlush(userDemographics);

        // Get all the userDemographicsList where ethnicity contains DEFAULT_ETHNICITY
        defaultUserDemographicsShouldBeFound("ethnicity.contains=" + DEFAULT_ETHNICITY);

        // Get all the userDemographicsList where ethnicity contains UPDATED_ETHNICITY
        defaultUserDemographicsShouldNotBeFound("ethnicity.contains=" + UPDATED_ETHNICITY);
    }

    @Test
    @Transactional
    void getAllUserDemographicsByEthnicityNotContainsSomething() throws Exception {
        // Initialize the database
        userDemographicsRepository.saveAndFlush(userDemographics);

        // Get all the userDemographicsList where ethnicity does not contain DEFAULT_ETHNICITY
        defaultUserDemographicsShouldNotBeFound("ethnicity.doesNotContain=" + DEFAULT_ETHNICITY);

        // Get all the userDemographicsList where ethnicity does not contain UPDATED_ETHNICITY
        defaultUserDemographicsShouldBeFound("ethnicity.doesNotContain=" + UPDATED_ETHNICITY);
    }

    @Test
    @Transactional
    void getAllUserDemographicsByIncomeIsEqualToSomething() throws Exception {
        // Initialize the database
        userDemographicsRepository.saveAndFlush(userDemographics);

        // Get all the userDemographicsList where income equals to DEFAULT_INCOME
        defaultUserDemographicsShouldBeFound("income.equals=" + DEFAULT_INCOME);

        // Get all the userDemographicsList where income equals to UPDATED_INCOME
        defaultUserDemographicsShouldNotBeFound("income.equals=" + UPDATED_INCOME);
    }

    @Test
    @Transactional
    void getAllUserDemographicsByIncomeIsInShouldWork() throws Exception {
        // Initialize the database
        userDemographicsRepository.saveAndFlush(userDemographics);

        // Get all the userDemographicsList where income in DEFAULT_INCOME or UPDATED_INCOME
        defaultUserDemographicsShouldBeFound("income.in=" + DEFAULT_INCOME + "," + UPDATED_INCOME);

        // Get all the userDemographicsList where income equals to UPDATED_INCOME
        defaultUserDemographicsShouldNotBeFound("income.in=" + UPDATED_INCOME);
    }

    @Test
    @Transactional
    void getAllUserDemographicsByIncomeIsNullOrNotNull() throws Exception {
        // Initialize the database
        userDemographicsRepository.saveAndFlush(userDemographics);

        // Get all the userDemographicsList where income is not null
        defaultUserDemographicsShouldBeFound("income.specified=true");

        // Get all the userDemographicsList where income is null
        defaultUserDemographicsShouldNotBeFound("income.specified=false");
    }

    @Test
    @Transactional
    void getAllUserDemographicsByIncomeContainsSomething() throws Exception {
        // Initialize the database
        userDemographicsRepository.saveAndFlush(userDemographics);

        // Get all the userDemographicsList where income contains DEFAULT_INCOME
        defaultUserDemographicsShouldBeFound("income.contains=" + DEFAULT_INCOME);

        // Get all the userDemographicsList where income contains UPDATED_INCOME
        defaultUserDemographicsShouldNotBeFound("income.contains=" + UPDATED_INCOME);
    }

    @Test
    @Transactional
    void getAllUserDemographicsByIncomeNotContainsSomething() throws Exception {
        // Initialize the database
        userDemographicsRepository.saveAndFlush(userDemographics);

        // Get all the userDemographicsList where income does not contain DEFAULT_INCOME
        defaultUserDemographicsShouldNotBeFound("income.doesNotContain=" + DEFAULT_INCOME);

        // Get all the userDemographicsList where income does not contain UPDATED_INCOME
        defaultUserDemographicsShouldBeFound("income.doesNotContain=" + UPDATED_INCOME);
    }

    @Test
    @Transactional
    void getAllUserDemographicsByMaritalStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        userDemographicsRepository.saveAndFlush(userDemographics);

        // Get all the userDemographicsList where maritalStatus equals to DEFAULT_MARITAL_STATUS
        defaultUserDemographicsShouldBeFound("maritalStatus.equals=" + DEFAULT_MARITAL_STATUS);

        // Get all the userDemographicsList where maritalStatus equals to UPDATED_MARITAL_STATUS
        defaultUserDemographicsShouldNotBeFound("maritalStatus.equals=" + UPDATED_MARITAL_STATUS);
    }

    @Test
    @Transactional
    void getAllUserDemographicsByMaritalStatusIsInShouldWork() throws Exception {
        // Initialize the database
        userDemographicsRepository.saveAndFlush(userDemographics);

        // Get all the userDemographicsList where maritalStatus in DEFAULT_MARITAL_STATUS or UPDATED_MARITAL_STATUS
        defaultUserDemographicsShouldBeFound("maritalStatus.in=" + DEFAULT_MARITAL_STATUS + "," + UPDATED_MARITAL_STATUS);

        // Get all the userDemographicsList where maritalStatus equals to UPDATED_MARITAL_STATUS
        defaultUserDemographicsShouldNotBeFound("maritalStatus.in=" + UPDATED_MARITAL_STATUS);
    }

    @Test
    @Transactional
    void getAllUserDemographicsByMaritalStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        userDemographicsRepository.saveAndFlush(userDemographics);

        // Get all the userDemographicsList where maritalStatus is not null
        defaultUserDemographicsShouldBeFound("maritalStatus.specified=true");

        // Get all the userDemographicsList where maritalStatus is null
        defaultUserDemographicsShouldNotBeFound("maritalStatus.specified=false");
    }

    @Test
    @Transactional
    void getAllUserDemographicsByMaritalStatusContainsSomething() throws Exception {
        // Initialize the database
        userDemographicsRepository.saveAndFlush(userDemographics);

        // Get all the userDemographicsList where maritalStatus contains DEFAULT_MARITAL_STATUS
        defaultUserDemographicsShouldBeFound("maritalStatus.contains=" + DEFAULT_MARITAL_STATUS);

        // Get all the userDemographicsList where maritalStatus contains UPDATED_MARITAL_STATUS
        defaultUserDemographicsShouldNotBeFound("maritalStatus.contains=" + UPDATED_MARITAL_STATUS);
    }

    @Test
    @Transactional
    void getAllUserDemographicsByMaritalStatusNotContainsSomething() throws Exception {
        // Initialize the database
        userDemographicsRepository.saveAndFlush(userDemographics);

        // Get all the userDemographicsList where maritalStatus does not contain DEFAULT_MARITAL_STATUS
        defaultUserDemographicsShouldNotBeFound("maritalStatus.doesNotContain=" + DEFAULT_MARITAL_STATUS);

        // Get all the userDemographicsList where maritalStatus does not contain UPDATED_MARITAL_STATUS
        defaultUserDemographicsShouldBeFound("maritalStatus.doesNotContain=" + UPDATED_MARITAL_STATUS);
    }

    @Test
    @Transactional
    void getAllUserDemographicsByEducationIsEqualToSomething() throws Exception {
        // Initialize the database
        userDemographicsRepository.saveAndFlush(userDemographics);

        // Get all the userDemographicsList where education equals to DEFAULT_EDUCATION
        defaultUserDemographicsShouldBeFound("education.equals=" + DEFAULT_EDUCATION);

        // Get all the userDemographicsList where education equals to UPDATED_EDUCATION
        defaultUserDemographicsShouldNotBeFound("education.equals=" + UPDATED_EDUCATION);
    }

    @Test
    @Transactional
    void getAllUserDemographicsByEducationIsInShouldWork() throws Exception {
        // Initialize the database
        userDemographicsRepository.saveAndFlush(userDemographics);

        // Get all the userDemographicsList where education in DEFAULT_EDUCATION or UPDATED_EDUCATION
        defaultUserDemographicsShouldBeFound("education.in=" + DEFAULT_EDUCATION + "," + UPDATED_EDUCATION);

        // Get all the userDemographicsList where education equals to UPDATED_EDUCATION
        defaultUserDemographicsShouldNotBeFound("education.in=" + UPDATED_EDUCATION);
    }

    @Test
    @Transactional
    void getAllUserDemographicsByEducationIsNullOrNotNull() throws Exception {
        // Initialize the database
        userDemographicsRepository.saveAndFlush(userDemographics);

        // Get all the userDemographicsList where education is not null
        defaultUserDemographicsShouldBeFound("education.specified=true");

        // Get all the userDemographicsList where education is null
        defaultUserDemographicsShouldNotBeFound("education.specified=false");
    }

    @Test
    @Transactional
    void getAllUserDemographicsByEducationContainsSomething() throws Exception {
        // Initialize the database
        userDemographicsRepository.saveAndFlush(userDemographics);

        // Get all the userDemographicsList where education contains DEFAULT_EDUCATION
        defaultUserDemographicsShouldBeFound("education.contains=" + DEFAULT_EDUCATION);

        // Get all the userDemographicsList where education contains UPDATED_EDUCATION
        defaultUserDemographicsShouldNotBeFound("education.contains=" + UPDATED_EDUCATION);
    }

    @Test
    @Transactional
    void getAllUserDemographicsByEducationNotContainsSomething() throws Exception {
        // Initialize the database
        userDemographicsRepository.saveAndFlush(userDemographics);

        // Get all the userDemographicsList where education does not contain DEFAULT_EDUCATION
        defaultUserDemographicsShouldNotBeFound("education.doesNotContain=" + DEFAULT_EDUCATION);

        // Get all the userDemographicsList where education does not contain UPDATED_EDUCATION
        defaultUserDemographicsShouldBeFound("education.doesNotContain=" + UPDATED_EDUCATION);
    }

    @Test
    @Transactional
    void getAllUserDemographicsByEndTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        userDemographicsRepository.saveAndFlush(userDemographics);

        // Get all the userDemographicsList where endTime equals to DEFAULT_END_TIME
        defaultUserDemographicsShouldBeFound("endTime.equals=" + DEFAULT_END_TIME);

        // Get all the userDemographicsList where endTime equals to UPDATED_END_TIME
        defaultUserDemographicsShouldNotBeFound("endTime.equals=" + UPDATED_END_TIME);
    }

    @Test
    @Transactional
    void getAllUserDemographicsByEndTimeIsInShouldWork() throws Exception {
        // Initialize the database
        userDemographicsRepository.saveAndFlush(userDemographics);

        // Get all the userDemographicsList where endTime in DEFAULT_END_TIME or UPDATED_END_TIME
        defaultUserDemographicsShouldBeFound("endTime.in=" + DEFAULT_END_TIME + "," + UPDATED_END_TIME);

        // Get all the userDemographicsList where endTime equals to UPDATED_END_TIME
        defaultUserDemographicsShouldNotBeFound("endTime.in=" + UPDATED_END_TIME);
    }

    @Test
    @Transactional
    void getAllUserDemographicsByEndTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        userDemographicsRepository.saveAndFlush(userDemographics);

        // Get all the userDemographicsList where endTime is not null
        defaultUserDemographicsShouldBeFound("endTime.specified=true");

        // Get all the userDemographicsList where endTime is null
        defaultUserDemographicsShouldNotBeFound("endTime.specified=false");
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultUserDemographicsShouldBeFound(String filter) throws Exception {
        restUserDemographicsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userDemographics.getId().toString())))
            .andExpect(jsonPath("$.[*].usuarioId").value(hasItem(DEFAULT_USUARIO_ID)))
            .andExpect(jsonPath("$.[*].empresaId").value(hasItem(DEFAULT_EMPRESA_ID)))
            .andExpect(jsonPath("$.[*].gender").value(hasItem(DEFAULT_GENDER)))
            .andExpect(jsonPath("$.[*].dateOfBird").value(hasItem(DEFAULT_DATE_OF_BIRD.toString())))
            .andExpect(jsonPath("$.[*].age").value(hasItem(DEFAULT_AGE)))
            .andExpect(jsonPath("$.[*].country").value(hasItem(DEFAULT_COUNTRY)))
            .andExpect(jsonPath("$.[*].state").value(hasItem(DEFAULT_STATE)))
            .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY)))
            .andExpect(jsonPath("$.[*].ethnicity").value(hasItem(DEFAULT_ETHNICITY)))
            .andExpect(jsonPath("$.[*].income").value(hasItem(DEFAULT_INCOME)))
            .andExpect(jsonPath("$.[*].maritalStatus").value(hasItem(DEFAULT_MARITAL_STATUS)))
            .andExpect(jsonPath("$.[*].education").value(hasItem(DEFAULT_EDUCATION)))
            .andExpect(jsonPath("$.[*].endTime").value(hasItem(DEFAULT_END_TIME.toString())));

        // Check, that the count call also returns 1
        restUserDemographicsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultUserDemographicsShouldNotBeFound(String filter) throws Exception {
        restUserDemographicsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restUserDemographicsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingUserDemographics() throws Exception {
        // Get the userDemographics
        restUserDemographicsMockMvc.perform(get(ENTITY_API_URL_ID, UUID.randomUUID().toString())).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingUserDemographics() throws Exception {
        // Initialize the database
        userDemographicsRepository.saveAndFlush(userDemographics);

        int databaseSizeBeforeUpdate = userDemographicsRepository.findAll().size();

        // Update the userDemographics
        UserDemographics updatedUserDemographics = userDemographicsRepository.findById(userDemographics.getId()).get();
        // Disconnect from session so that the updates on updatedUserDemographics are not directly saved in db
        em.detach(updatedUserDemographics);
        updatedUserDemographics
            .usuarioId(UPDATED_USUARIO_ID)
            .empresaId(UPDATED_EMPRESA_ID)
            .gender(UPDATED_GENDER)
            .dateOfBird(UPDATED_DATE_OF_BIRD)
            .age(UPDATED_AGE)
            .country(UPDATED_COUNTRY)
            .state(UPDATED_STATE)
            .city(UPDATED_CITY)
            .ethnicity(UPDATED_ETHNICITY)
            .income(UPDATED_INCOME)
            .maritalStatus(UPDATED_MARITAL_STATUS)
            .education(UPDATED_EDUCATION)
            .endTime(UPDATED_END_TIME);

        restUserDemographicsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedUserDemographics.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedUserDemographics))
            )
            .andExpect(status().isOk());

        // Validate the UserDemographics in the database
        List<UserDemographics> userDemographicsList = userDemographicsRepository.findAll();
        assertThat(userDemographicsList).hasSize(databaseSizeBeforeUpdate);
        UserDemographics testUserDemographics = userDemographicsList.get(userDemographicsList.size() - 1);
        assertThat(testUserDemographics.getUsuarioId()).isEqualTo(UPDATED_USUARIO_ID);
        assertThat(testUserDemographics.getEmpresaId()).isEqualTo(UPDATED_EMPRESA_ID);
        assertThat(testUserDemographics.getGender()).isEqualTo(UPDATED_GENDER);
        assertThat(testUserDemographics.getDateOfBird()).isEqualTo(UPDATED_DATE_OF_BIRD);
        assertThat(testUserDemographics.getAge()).isEqualTo(UPDATED_AGE);
        assertThat(testUserDemographics.getCountry()).isEqualTo(UPDATED_COUNTRY);
        assertThat(testUserDemographics.getState()).isEqualTo(UPDATED_STATE);
        assertThat(testUserDemographics.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testUserDemographics.getEthnicity()).isEqualTo(UPDATED_ETHNICITY);
        assertThat(testUserDemographics.getIncome()).isEqualTo(UPDATED_INCOME);
        assertThat(testUserDemographics.getMaritalStatus()).isEqualTo(UPDATED_MARITAL_STATUS);
        assertThat(testUserDemographics.getEducation()).isEqualTo(UPDATED_EDUCATION);
        assertThat(testUserDemographics.getEndTime()).isEqualTo(UPDATED_END_TIME);
    }

    @Test
    @Transactional
    void putNonExistingUserDemographics() throws Exception {
        int databaseSizeBeforeUpdate = userDemographicsRepository.findAll().size();
        userDemographics.setId(UUID.randomUUID());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserDemographicsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, userDemographics.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(userDemographics))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserDemographics in the database
        List<UserDemographics> userDemographicsList = userDemographicsRepository.findAll();
        assertThat(userDemographicsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchUserDemographics() throws Exception {
        int databaseSizeBeforeUpdate = userDemographicsRepository.findAll().size();
        userDemographics.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserDemographicsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(userDemographics))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserDemographics in the database
        List<UserDemographics> userDemographicsList = userDemographicsRepository.findAll();
        assertThat(userDemographicsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamUserDemographics() throws Exception {
        int databaseSizeBeforeUpdate = userDemographicsRepository.findAll().size();
        userDemographics.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserDemographicsMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(userDemographics))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the UserDemographics in the database
        List<UserDemographics> userDemographicsList = userDemographicsRepository.findAll();
        assertThat(userDemographicsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateUserDemographicsWithPatch() throws Exception {
        // Initialize the database
        userDemographicsRepository.saveAndFlush(userDemographics);

        int databaseSizeBeforeUpdate = userDemographicsRepository.findAll().size();

        // Update the userDemographics using partial update
        UserDemographics partialUpdatedUserDemographics = new UserDemographics();
        partialUpdatedUserDemographics.setId(userDemographics.getId());

        partialUpdatedUserDemographics
            .usuarioId(UPDATED_USUARIO_ID)
            .dateOfBird(UPDATED_DATE_OF_BIRD)
            .country(UPDATED_COUNTRY)
            .city(UPDATED_CITY)
            .ethnicity(UPDATED_ETHNICITY)
            .maritalStatus(UPDATED_MARITAL_STATUS)
            .endTime(UPDATED_END_TIME);

        restUserDemographicsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedUserDemographics.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedUserDemographics))
            )
            .andExpect(status().isOk());

        // Validate the UserDemographics in the database
        List<UserDemographics> userDemographicsList = userDemographicsRepository.findAll();
        assertThat(userDemographicsList).hasSize(databaseSizeBeforeUpdate);
        UserDemographics testUserDemographics = userDemographicsList.get(userDemographicsList.size() - 1);
        assertThat(testUserDemographics.getUsuarioId()).isEqualTo(UPDATED_USUARIO_ID);
        assertThat(testUserDemographics.getEmpresaId()).isEqualTo(DEFAULT_EMPRESA_ID);
        assertThat(testUserDemographics.getGender()).isEqualTo(DEFAULT_GENDER);
        assertThat(testUserDemographics.getDateOfBird()).isEqualTo(UPDATED_DATE_OF_BIRD);
        assertThat(testUserDemographics.getAge()).isEqualTo(DEFAULT_AGE);
        assertThat(testUserDemographics.getCountry()).isEqualTo(UPDATED_COUNTRY);
        assertThat(testUserDemographics.getState()).isEqualTo(DEFAULT_STATE);
        assertThat(testUserDemographics.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testUserDemographics.getEthnicity()).isEqualTo(UPDATED_ETHNICITY);
        assertThat(testUserDemographics.getIncome()).isEqualTo(DEFAULT_INCOME);
        assertThat(testUserDemographics.getMaritalStatus()).isEqualTo(UPDATED_MARITAL_STATUS);
        assertThat(testUserDemographics.getEducation()).isEqualTo(DEFAULT_EDUCATION);
        assertThat(testUserDemographics.getEndTime()).isEqualTo(UPDATED_END_TIME);
    }

    @Test
    @Transactional
    void fullUpdateUserDemographicsWithPatch() throws Exception {
        // Initialize the database
        userDemographicsRepository.saveAndFlush(userDemographics);

        int databaseSizeBeforeUpdate = userDemographicsRepository.findAll().size();

        // Update the userDemographics using partial update
        UserDemographics partialUpdatedUserDemographics = new UserDemographics();
        partialUpdatedUserDemographics.setId(userDemographics.getId());

        partialUpdatedUserDemographics
            .usuarioId(UPDATED_USUARIO_ID)
            .empresaId(UPDATED_EMPRESA_ID)
            .gender(UPDATED_GENDER)
            .dateOfBird(UPDATED_DATE_OF_BIRD)
            .age(UPDATED_AGE)
            .country(UPDATED_COUNTRY)
            .state(UPDATED_STATE)
            .city(UPDATED_CITY)
            .ethnicity(UPDATED_ETHNICITY)
            .income(UPDATED_INCOME)
            .maritalStatus(UPDATED_MARITAL_STATUS)
            .education(UPDATED_EDUCATION)
            .endTime(UPDATED_END_TIME);

        restUserDemographicsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedUserDemographics.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedUserDemographics))
            )
            .andExpect(status().isOk());

        // Validate the UserDemographics in the database
        List<UserDemographics> userDemographicsList = userDemographicsRepository.findAll();
        assertThat(userDemographicsList).hasSize(databaseSizeBeforeUpdate);
        UserDemographics testUserDemographics = userDemographicsList.get(userDemographicsList.size() - 1);
        assertThat(testUserDemographics.getUsuarioId()).isEqualTo(UPDATED_USUARIO_ID);
        assertThat(testUserDemographics.getEmpresaId()).isEqualTo(UPDATED_EMPRESA_ID);
        assertThat(testUserDemographics.getGender()).isEqualTo(UPDATED_GENDER);
        assertThat(testUserDemographics.getDateOfBird()).isEqualTo(UPDATED_DATE_OF_BIRD);
        assertThat(testUserDemographics.getAge()).isEqualTo(UPDATED_AGE);
        assertThat(testUserDemographics.getCountry()).isEqualTo(UPDATED_COUNTRY);
        assertThat(testUserDemographics.getState()).isEqualTo(UPDATED_STATE);
        assertThat(testUserDemographics.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testUserDemographics.getEthnicity()).isEqualTo(UPDATED_ETHNICITY);
        assertThat(testUserDemographics.getIncome()).isEqualTo(UPDATED_INCOME);
        assertThat(testUserDemographics.getMaritalStatus()).isEqualTo(UPDATED_MARITAL_STATUS);
        assertThat(testUserDemographics.getEducation()).isEqualTo(UPDATED_EDUCATION);
        assertThat(testUserDemographics.getEndTime()).isEqualTo(UPDATED_END_TIME);
    }

    @Test
    @Transactional
    void patchNonExistingUserDemographics() throws Exception {
        int databaseSizeBeforeUpdate = userDemographicsRepository.findAll().size();
        userDemographics.setId(UUID.randomUUID());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserDemographicsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, userDemographics.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(userDemographics))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserDemographics in the database
        List<UserDemographics> userDemographicsList = userDemographicsRepository.findAll();
        assertThat(userDemographicsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchUserDemographics() throws Exception {
        int databaseSizeBeforeUpdate = userDemographicsRepository.findAll().size();
        userDemographics.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserDemographicsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(userDemographics))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserDemographics in the database
        List<UserDemographics> userDemographicsList = userDemographicsRepository.findAll();
        assertThat(userDemographicsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamUserDemographics() throws Exception {
        int databaseSizeBeforeUpdate = userDemographicsRepository.findAll().size();
        userDemographics.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserDemographicsMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(userDemographics))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the UserDemographics in the database
        List<UserDemographics> userDemographicsList = userDemographicsRepository.findAll();
        assertThat(userDemographicsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteUserDemographics() throws Exception {
        // Initialize the database
        userDemographicsRepository.saveAndFlush(userDemographics);

        int databaseSizeBeforeDelete = userDemographicsRepository.findAll().size();

        // Delete the userDemographics
        restUserDemographicsMockMvc
            .perform(delete(ENTITY_API_URL_ID, userDemographics.getId().toString()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<UserDemographics> userDemographicsList = userDemographicsRepository.findAll();
        assertThat(userDemographicsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
