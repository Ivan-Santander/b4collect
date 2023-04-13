package com.be4tech.b4carecollect.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.be4tech.b4carecollect.IntegrationTest;
import com.be4tech.b4carecollect.domain.BloodGlucose;
import com.be4tech.b4carecollect.repository.BloodGlucoseRepository;
import com.be4tech.b4carecollect.service.criteria.BloodGlucoseCriteria;
import java.time.Instant;
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
 * Integration tests for the {@link BloodGlucoseResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class BloodGlucoseResourceIT {

    private static final String DEFAULT_USUARIO_ID = "AAAAAAAAAA";
    private static final String UPDATED_USUARIO_ID = "BBBBBBBBBB";

    private static final String DEFAULT_EMPRESA_ID = "AAAAAAAAAA";
    private static final String UPDATED_EMPRESA_ID = "BBBBBBBBBB";

    private static final Float DEFAULT_FIELD_BLOOD_GLUCOSE_LEVEL = 1F;
    private static final Float UPDATED_FIELD_BLOOD_GLUCOSE_LEVEL = 2F;
    private static final Float SMALLER_FIELD_BLOOD_GLUCOSE_LEVEL = 1F - 1F;

    private static final Integer DEFAULT_FIELD_TEMPORAL_RELATION_TO_MEAL = 1;
    private static final Integer UPDATED_FIELD_TEMPORAL_RELATION_TO_MEAL = 2;
    private static final Integer SMALLER_FIELD_TEMPORAL_RELATION_TO_MEAL = 1 - 1;

    private static final Integer DEFAULT_FIELD_MEAL_TYPE = 1;
    private static final Integer UPDATED_FIELD_MEAL_TYPE = 2;
    private static final Integer SMALLER_FIELD_MEAL_TYPE = 1 - 1;

    private static final Integer DEFAULT_FIELD_TEMPORAL_RELATION_TO_SLEEP = 1;
    private static final Integer UPDATED_FIELD_TEMPORAL_RELATION_TO_SLEEP = 2;
    private static final Integer SMALLER_FIELD_TEMPORAL_RELATION_TO_SLEEP = 1 - 1;

    private static final Integer DEFAULT_FIELD_BLOOD_GLUCOSE_SPECIMEN_SOURCE = 1;
    private static final Integer UPDATED_FIELD_BLOOD_GLUCOSE_SPECIMEN_SOURCE = 2;
    private static final Integer SMALLER_FIELD_BLOOD_GLUCOSE_SPECIMEN_SOURCE = 1 - 1;

    private static final Instant DEFAULT_END_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_END_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/blood-glucoses";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private BloodGlucoseRepository bloodGlucoseRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBloodGlucoseMockMvc;

    private BloodGlucose bloodGlucose;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BloodGlucose createEntity(EntityManager em) {
        BloodGlucose bloodGlucose = new BloodGlucose()
            .usuarioId(DEFAULT_USUARIO_ID)
            .empresaId(DEFAULT_EMPRESA_ID)
            .fieldBloodGlucoseLevel(DEFAULT_FIELD_BLOOD_GLUCOSE_LEVEL)
            .fieldTemporalRelationToMeal(DEFAULT_FIELD_TEMPORAL_RELATION_TO_MEAL)
            .fieldMealType(DEFAULT_FIELD_MEAL_TYPE)
            .fieldTemporalRelationToSleep(DEFAULT_FIELD_TEMPORAL_RELATION_TO_SLEEP)
            .fieldBloodGlucoseSpecimenSource(DEFAULT_FIELD_BLOOD_GLUCOSE_SPECIMEN_SOURCE)
            .endTime(DEFAULT_END_TIME);
        return bloodGlucose;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BloodGlucose createUpdatedEntity(EntityManager em) {
        BloodGlucose bloodGlucose = new BloodGlucose()
            .usuarioId(UPDATED_USUARIO_ID)
            .empresaId(UPDATED_EMPRESA_ID)
            .fieldBloodGlucoseLevel(UPDATED_FIELD_BLOOD_GLUCOSE_LEVEL)
            .fieldTemporalRelationToMeal(UPDATED_FIELD_TEMPORAL_RELATION_TO_MEAL)
            .fieldMealType(UPDATED_FIELD_MEAL_TYPE)
            .fieldTemporalRelationToSleep(UPDATED_FIELD_TEMPORAL_RELATION_TO_SLEEP)
            .fieldBloodGlucoseSpecimenSource(UPDATED_FIELD_BLOOD_GLUCOSE_SPECIMEN_SOURCE)
            .endTime(UPDATED_END_TIME);
        return bloodGlucose;
    }

    @BeforeEach
    public void initTest() {
        bloodGlucose = createEntity(em);
    }

    @Test
    @Transactional
    void createBloodGlucose() throws Exception {
        int databaseSizeBeforeCreate = bloodGlucoseRepository.findAll().size();
        // Create the BloodGlucose
        restBloodGlucoseMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bloodGlucose)))
            .andExpect(status().isCreated());

        // Validate the BloodGlucose in the database
        List<BloodGlucose> bloodGlucoseList = bloodGlucoseRepository.findAll();
        assertThat(bloodGlucoseList).hasSize(databaseSizeBeforeCreate + 1);
        BloodGlucose testBloodGlucose = bloodGlucoseList.get(bloodGlucoseList.size() - 1);
        assertThat(testBloodGlucose.getUsuarioId()).isEqualTo(DEFAULT_USUARIO_ID);
        assertThat(testBloodGlucose.getEmpresaId()).isEqualTo(DEFAULT_EMPRESA_ID);
        assertThat(testBloodGlucose.getFieldBloodGlucoseLevel()).isEqualTo(DEFAULT_FIELD_BLOOD_GLUCOSE_LEVEL);
        assertThat(testBloodGlucose.getFieldTemporalRelationToMeal()).isEqualTo(DEFAULT_FIELD_TEMPORAL_RELATION_TO_MEAL);
        assertThat(testBloodGlucose.getFieldMealType()).isEqualTo(DEFAULT_FIELD_MEAL_TYPE);
        assertThat(testBloodGlucose.getFieldTemporalRelationToSleep()).isEqualTo(DEFAULT_FIELD_TEMPORAL_RELATION_TO_SLEEP);
        assertThat(testBloodGlucose.getFieldBloodGlucoseSpecimenSource()).isEqualTo(DEFAULT_FIELD_BLOOD_GLUCOSE_SPECIMEN_SOURCE);
        assertThat(testBloodGlucose.getEndTime()).isEqualTo(DEFAULT_END_TIME);
    }

    @Test
    @Transactional
    void createBloodGlucoseWithExistingId() throws Exception {
        // Create the BloodGlucose with an existing ID
        bloodGlucoseRepository.saveAndFlush(bloodGlucose);

        int databaseSizeBeforeCreate = bloodGlucoseRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBloodGlucoseMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bloodGlucose)))
            .andExpect(status().isBadRequest());

        // Validate the BloodGlucose in the database
        List<BloodGlucose> bloodGlucoseList = bloodGlucoseRepository.findAll();
        assertThat(bloodGlucoseList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllBloodGlucoses() throws Exception {
        // Initialize the database
        bloodGlucoseRepository.saveAndFlush(bloodGlucose);

        // Get all the bloodGlucoseList
        restBloodGlucoseMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bloodGlucose.getId().toString())))
            .andExpect(jsonPath("$.[*].usuarioId").value(hasItem(DEFAULT_USUARIO_ID)))
            .andExpect(jsonPath("$.[*].empresaId").value(hasItem(DEFAULT_EMPRESA_ID)))
            .andExpect(jsonPath("$.[*].fieldBloodGlucoseLevel").value(hasItem(DEFAULT_FIELD_BLOOD_GLUCOSE_LEVEL.doubleValue())))
            .andExpect(jsonPath("$.[*].fieldTemporalRelationToMeal").value(hasItem(DEFAULT_FIELD_TEMPORAL_RELATION_TO_MEAL)))
            .andExpect(jsonPath("$.[*].fieldMealType").value(hasItem(DEFAULT_FIELD_MEAL_TYPE)))
            .andExpect(jsonPath("$.[*].fieldTemporalRelationToSleep").value(hasItem(DEFAULT_FIELD_TEMPORAL_RELATION_TO_SLEEP)))
            .andExpect(jsonPath("$.[*].fieldBloodGlucoseSpecimenSource").value(hasItem(DEFAULT_FIELD_BLOOD_GLUCOSE_SPECIMEN_SOURCE)))
            .andExpect(jsonPath("$.[*].endTime").value(hasItem(DEFAULT_END_TIME.toString())));
    }

    @Test
    @Transactional
    void getBloodGlucose() throws Exception {
        // Initialize the database
        bloodGlucoseRepository.saveAndFlush(bloodGlucose);

        // Get the bloodGlucose
        restBloodGlucoseMockMvc
            .perform(get(ENTITY_API_URL_ID, bloodGlucose.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(bloodGlucose.getId().toString()))
            .andExpect(jsonPath("$.usuarioId").value(DEFAULT_USUARIO_ID))
            .andExpect(jsonPath("$.empresaId").value(DEFAULT_EMPRESA_ID))
            .andExpect(jsonPath("$.fieldBloodGlucoseLevel").value(DEFAULT_FIELD_BLOOD_GLUCOSE_LEVEL.doubleValue()))
            .andExpect(jsonPath("$.fieldTemporalRelationToMeal").value(DEFAULT_FIELD_TEMPORAL_RELATION_TO_MEAL))
            .andExpect(jsonPath("$.fieldMealType").value(DEFAULT_FIELD_MEAL_TYPE))
            .andExpect(jsonPath("$.fieldTemporalRelationToSleep").value(DEFAULT_FIELD_TEMPORAL_RELATION_TO_SLEEP))
            .andExpect(jsonPath("$.fieldBloodGlucoseSpecimenSource").value(DEFAULT_FIELD_BLOOD_GLUCOSE_SPECIMEN_SOURCE))
            .andExpect(jsonPath("$.endTime").value(DEFAULT_END_TIME.toString()));
    }

    @Test
    @Transactional
    void getBloodGlucosesByIdFiltering() throws Exception {
        // Initialize the database
        bloodGlucoseRepository.saveAndFlush(bloodGlucose);

        UUID id = bloodGlucose.getId();

        defaultBloodGlucoseShouldBeFound("id.equals=" + id);
        defaultBloodGlucoseShouldNotBeFound("id.notEquals=" + id);
    }

    @Test
    @Transactional
    void getAllBloodGlucosesByUsuarioIdIsEqualToSomething() throws Exception {
        // Initialize the database
        bloodGlucoseRepository.saveAndFlush(bloodGlucose);

        // Get all the bloodGlucoseList where usuarioId equals to DEFAULT_USUARIO_ID
        defaultBloodGlucoseShouldBeFound("usuarioId.equals=" + DEFAULT_USUARIO_ID);

        // Get all the bloodGlucoseList where usuarioId equals to UPDATED_USUARIO_ID
        defaultBloodGlucoseShouldNotBeFound("usuarioId.equals=" + UPDATED_USUARIO_ID);
    }

    @Test
    @Transactional
    void getAllBloodGlucosesByUsuarioIdIsInShouldWork() throws Exception {
        // Initialize the database
        bloodGlucoseRepository.saveAndFlush(bloodGlucose);

        // Get all the bloodGlucoseList where usuarioId in DEFAULT_USUARIO_ID or UPDATED_USUARIO_ID
        defaultBloodGlucoseShouldBeFound("usuarioId.in=" + DEFAULT_USUARIO_ID + "," + UPDATED_USUARIO_ID);

        // Get all the bloodGlucoseList where usuarioId equals to UPDATED_USUARIO_ID
        defaultBloodGlucoseShouldNotBeFound("usuarioId.in=" + UPDATED_USUARIO_ID);
    }

    @Test
    @Transactional
    void getAllBloodGlucosesByUsuarioIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        bloodGlucoseRepository.saveAndFlush(bloodGlucose);

        // Get all the bloodGlucoseList where usuarioId is not null
        defaultBloodGlucoseShouldBeFound("usuarioId.specified=true");

        // Get all the bloodGlucoseList where usuarioId is null
        defaultBloodGlucoseShouldNotBeFound("usuarioId.specified=false");
    }

    @Test
    @Transactional
    void getAllBloodGlucosesByUsuarioIdContainsSomething() throws Exception {
        // Initialize the database
        bloodGlucoseRepository.saveAndFlush(bloodGlucose);

        // Get all the bloodGlucoseList where usuarioId contains DEFAULT_USUARIO_ID
        defaultBloodGlucoseShouldBeFound("usuarioId.contains=" + DEFAULT_USUARIO_ID);

        // Get all the bloodGlucoseList where usuarioId contains UPDATED_USUARIO_ID
        defaultBloodGlucoseShouldNotBeFound("usuarioId.contains=" + UPDATED_USUARIO_ID);
    }

    @Test
    @Transactional
    void getAllBloodGlucosesByUsuarioIdNotContainsSomething() throws Exception {
        // Initialize the database
        bloodGlucoseRepository.saveAndFlush(bloodGlucose);

        // Get all the bloodGlucoseList where usuarioId does not contain DEFAULT_USUARIO_ID
        defaultBloodGlucoseShouldNotBeFound("usuarioId.doesNotContain=" + DEFAULT_USUARIO_ID);

        // Get all the bloodGlucoseList where usuarioId does not contain UPDATED_USUARIO_ID
        defaultBloodGlucoseShouldBeFound("usuarioId.doesNotContain=" + UPDATED_USUARIO_ID);
    }

    @Test
    @Transactional
    void getAllBloodGlucosesByEmpresaIdIsEqualToSomething() throws Exception {
        // Initialize the database
        bloodGlucoseRepository.saveAndFlush(bloodGlucose);

        // Get all the bloodGlucoseList where empresaId equals to DEFAULT_EMPRESA_ID
        defaultBloodGlucoseShouldBeFound("empresaId.equals=" + DEFAULT_EMPRESA_ID);

        // Get all the bloodGlucoseList where empresaId equals to UPDATED_EMPRESA_ID
        defaultBloodGlucoseShouldNotBeFound("empresaId.equals=" + UPDATED_EMPRESA_ID);
    }

    @Test
    @Transactional
    void getAllBloodGlucosesByEmpresaIdIsInShouldWork() throws Exception {
        // Initialize the database
        bloodGlucoseRepository.saveAndFlush(bloodGlucose);

        // Get all the bloodGlucoseList where empresaId in DEFAULT_EMPRESA_ID or UPDATED_EMPRESA_ID
        defaultBloodGlucoseShouldBeFound("empresaId.in=" + DEFAULT_EMPRESA_ID + "," + UPDATED_EMPRESA_ID);

        // Get all the bloodGlucoseList where empresaId equals to UPDATED_EMPRESA_ID
        defaultBloodGlucoseShouldNotBeFound("empresaId.in=" + UPDATED_EMPRESA_ID);
    }

    @Test
    @Transactional
    void getAllBloodGlucosesByEmpresaIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        bloodGlucoseRepository.saveAndFlush(bloodGlucose);

        // Get all the bloodGlucoseList where empresaId is not null
        defaultBloodGlucoseShouldBeFound("empresaId.specified=true");

        // Get all the bloodGlucoseList where empresaId is null
        defaultBloodGlucoseShouldNotBeFound("empresaId.specified=false");
    }

    @Test
    @Transactional
    void getAllBloodGlucosesByEmpresaIdContainsSomething() throws Exception {
        // Initialize the database
        bloodGlucoseRepository.saveAndFlush(bloodGlucose);

        // Get all the bloodGlucoseList where empresaId contains DEFAULT_EMPRESA_ID
        defaultBloodGlucoseShouldBeFound("empresaId.contains=" + DEFAULT_EMPRESA_ID);

        // Get all the bloodGlucoseList where empresaId contains UPDATED_EMPRESA_ID
        defaultBloodGlucoseShouldNotBeFound("empresaId.contains=" + UPDATED_EMPRESA_ID);
    }

    @Test
    @Transactional
    void getAllBloodGlucosesByEmpresaIdNotContainsSomething() throws Exception {
        // Initialize the database
        bloodGlucoseRepository.saveAndFlush(bloodGlucose);

        // Get all the bloodGlucoseList where empresaId does not contain DEFAULT_EMPRESA_ID
        defaultBloodGlucoseShouldNotBeFound("empresaId.doesNotContain=" + DEFAULT_EMPRESA_ID);

        // Get all the bloodGlucoseList where empresaId does not contain UPDATED_EMPRESA_ID
        defaultBloodGlucoseShouldBeFound("empresaId.doesNotContain=" + UPDATED_EMPRESA_ID);
    }

    @Test
    @Transactional
    void getAllBloodGlucosesByFieldBloodGlucoseLevelIsEqualToSomething() throws Exception {
        // Initialize the database
        bloodGlucoseRepository.saveAndFlush(bloodGlucose);

        // Get all the bloodGlucoseList where fieldBloodGlucoseLevel equals to DEFAULT_FIELD_BLOOD_GLUCOSE_LEVEL
        defaultBloodGlucoseShouldBeFound("fieldBloodGlucoseLevel.equals=" + DEFAULT_FIELD_BLOOD_GLUCOSE_LEVEL);

        // Get all the bloodGlucoseList where fieldBloodGlucoseLevel equals to UPDATED_FIELD_BLOOD_GLUCOSE_LEVEL
        defaultBloodGlucoseShouldNotBeFound("fieldBloodGlucoseLevel.equals=" + UPDATED_FIELD_BLOOD_GLUCOSE_LEVEL);
    }

    @Test
    @Transactional
    void getAllBloodGlucosesByFieldBloodGlucoseLevelIsInShouldWork() throws Exception {
        // Initialize the database
        bloodGlucoseRepository.saveAndFlush(bloodGlucose);

        // Get all the bloodGlucoseList where fieldBloodGlucoseLevel in DEFAULT_FIELD_BLOOD_GLUCOSE_LEVEL or UPDATED_FIELD_BLOOD_GLUCOSE_LEVEL
        defaultBloodGlucoseShouldBeFound(
            "fieldBloodGlucoseLevel.in=" + DEFAULT_FIELD_BLOOD_GLUCOSE_LEVEL + "," + UPDATED_FIELD_BLOOD_GLUCOSE_LEVEL
        );

        // Get all the bloodGlucoseList where fieldBloodGlucoseLevel equals to UPDATED_FIELD_BLOOD_GLUCOSE_LEVEL
        defaultBloodGlucoseShouldNotBeFound("fieldBloodGlucoseLevel.in=" + UPDATED_FIELD_BLOOD_GLUCOSE_LEVEL);
    }

    @Test
    @Transactional
    void getAllBloodGlucosesByFieldBloodGlucoseLevelIsNullOrNotNull() throws Exception {
        // Initialize the database
        bloodGlucoseRepository.saveAndFlush(bloodGlucose);

        // Get all the bloodGlucoseList where fieldBloodGlucoseLevel is not null
        defaultBloodGlucoseShouldBeFound("fieldBloodGlucoseLevel.specified=true");

        // Get all the bloodGlucoseList where fieldBloodGlucoseLevel is null
        defaultBloodGlucoseShouldNotBeFound("fieldBloodGlucoseLevel.specified=false");
    }

    @Test
    @Transactional
    void getAllBloodGlucosesByFieldBloodGlucoseLevelIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        bloodGlucoseRepository.saveAndFlush(bloodGlucose);

        // Get all the bloodGlucoseList where fieldBloodGlucoseLevel is greater than or equal to DEFAULT_FIELD_BLOOD_GLUCOSE_LEVEL
        defaultBloodGlucoseShouldBeFound("fieldBloodGlucoseLevel.greaterThanOrEqual=" + DEFAULT_FIELD_BLOOD_GLUCOSE_LEVEL);

        // Get all the bloodGlucoseList where fieldBloodGlucoseLevel is greater than or equal to UPDATED_FIELD_BLOOD_GLUCOSE_LEVEL
        defaultBloodGlucoseShouldNotBeFound("fieldBloodGlucoseLevel.greaterThanOrEqual=" + UPDATED_FIELD_BLOOD_GLUCOSE_LEVEL);
    }

    @Test
    @Transactional
    void getAllBloodGlucosesByFieldBloodGlucoseLevelIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        bloodGlucoseRepository.saveAndFlush(bloodGlucose);

        // Get all the bloodGlucoseList where fieldBloodGlucoseLevel is less than or equal to DEFAULT_FIELD_BLOOD_GLUCOSE_LEVEL
        defaultBloodGlucoseShouldBeFound("fieldBloodGlucoseLevel.lessThanOrEqual=" + DEFAULT_FIELD_BLOOD_GLUCOSE_LEVEL);

        // Get all the bloodGlucoseList where fieldBloodGlucoseLevel is less than or equal to SMALLER_FIELD_BLOOD_GLUCOSE_LEVEL
        defaultBloodGlucoseShouldNotBeFound("fieldBloodGlucoseLevel.lessThanOrEqual=" + SMALLER_FIELD_BLOOD_GLUCOSE_LEVEL);
    }

    @Test
    @Transactional
    void getAllBloodGlucosesByFieldBloodGlucoseLevelIsLessThanSomething() throws Exception {
        // Initialize the database
        bloodGlucoseRepository.saveAndFlush(bloodGlucose);

        // Get all the bloodGlucoseList where fieldBloodGlucoseLevel is less than DEFAULT_FIELD_BLOOD_GLUCOSE_LEVEL
        defaultBloodGlucoseShouldNotBeFound("fieldBloodGlucoseLevel.lessThan=" + DEFAULT_FIELD_BLOOD_GLUCOSE_LEVEL);

        // Get all the bloodGlucoseList where fieldBloodGlucoseLevel is less than UPDATED_FIELD_BLOOD_GLUCOSE_LEVEL
        defaultBloodGlucoseShouldBeFound("fieldBloodGlucoseLevel.lessThan=" + UPDATED_FIELD_BLOOD_GLUCOSE_LEVEL);
    }

    @Test
    @Transactional
    void getAllBloodGlucosesByFieldBloodGlucoseLevelIsGreaterThanSomething() throws Exception {
        // Initialize the database
        bloodGlucoseRepository.saveAndFlush(bloodGlucose);

        // Get all the bloodGlucoseList where fieldBloodGlucoseLevel is greater than DEFAULT_FIELD_BLOOD_GLUCOSE_LEVEL
        defaultBloodGlucoseShouldNotBeFound("fieldBloodGlucoseLevel.greaterThan=" + DEFAULT_FIELD_BLOOD_GLUCOSE_LEVEL);

        // Get all the bloodGlucoseList where fieldBloodGlucoseLevel is greater than SMALLER_FIELD_BLOOD_GLUCOSE_LEVEL
        defaultBloodGlucoseShouldBeFound("fieldBloodGlucoseLevel.greaterThan=" + SMALLER_FIELD_BLOOD_GLUCOSE_LEVEL);
    }

    @Test
    @Transactional
    void getAllBloodGlucosesByFieldTemporalRelationToMealIsEqualToSomething() throws Exception {
        // Initialize the database
        bloodGlucoseRepository.saveAndFlush(bloodGlucose);

        // Get all the bloodGlucoseList where fieldTemporalRelationToMeal equals to DEFAULT_FIELD_TEMPORAL_RELATION_TO_MEAL
        defaultBloodGlucoseShouldBeFound("fieldTemporalRelationToMeal.equals=" + DEFAULT_FIELD_TEMPORAL_RELATION_TO_MEAL);

        // Get all the bloodGlucoseList where fieldTemporalRelationToMeal equals to UPDATED_FIELD_TEMPORAL_RELATION_TO_MEAL
        defaultBloodGlucoseShouldNotBeFound("fieldTemporalRelationToMeal.equals=" + UPDATED_FIELD_TEMPORAL_RELATION_TO_MEAL);
    }

    @Test
    @Transactional
    void getAllBloodGlucosesByFieldTemporalRelationToMealIsInShouldWork() throws Exception {
        // Initialize the database
        bloodGlucoseRepository.saveAndFlush(bloodGlucose);

        // Get all the bloodGlucoseList where fieldTemporalRelationToMeal in DEFAULT_FIELD_TEMPORAL_RELATION_TO_MEAL or UPDATED_FIELD_TEMPORAL_RELATION_TO_MEAL
        defaultBloodGlucoseShouldBeFound(
            "fieldTemporalRelationToMeal.in=" + DEFAULT_FIELD_TEMPORAL_RELATION_TO_MEAL + "," + UPDATED_FIELD_TEMPORAL_RELATION_TO_MEAL
        );

        // Get all the bloodGlucoseList where fieldTemporalRelationToMeal equals to UPDATED_FIELD_TEMPORAL_RELATION_TO_MEAL
        defaultBloodGlucoseShouldNotBeFound("fieldTemporalRelationToMeal.in=" + UPDATED_FIELD_TEMPORAL_RELATION_TO_MEAL);
    }

    @Test
    @Transactional
    void getAllBloodGlucosesByFieldTemporalRelationToMealIsNullOrNotNull() throws Exception {
        // Initialize the database
        bloodGlucoseRepository.saveAndFlush(bloodGlucose);

        // Get all the bloodGlucoseList where fieldTemporalRelationToMeal is not null
        defaultBloodGlucoseShouldBeFound("fieldTemporalRelationToMeal.specified=true");

        // Get all the bloodGlucoseList where fieldTemporalRelationToMeal is null
        defaultBloodGlucoseShouldNotBeFound("fieldTemporalRelationToMeal.specified=false");
    }

    @Test
    @Transactional
    void getAllBloodGlucosesByFieldTemporalRelationToMealIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        bloodGlucoseRepository.saveAndFlush(bloodGlucose);

        // Get all the bloodGlucoseList where fieldTemporalRelationToMeal is greater than or equal to DEFAULT_FIELD_TEMPORAL_RELATION_TO_MEAL
        defaultBloodGlucoseShouldBeFound("fieldTemporalRelationToMeal.greaterThanOrEqual=" + DEFAULT_FIELD_TEMPORAL_RELATION_TO_MEAL);

        // Get all the bloodGlucoseList where fieldTemporalRelationToMeal is greater than or equal to UPDATED_FIELD_TEMPORAL_RELATION_TO_MEAL
        defaultBloodGlucoseShouldNotBeFound("fieldTemporalRelationToMeal.greaterThanOrEqual=" + UPDATED_FIELD_TEMPORAL_RELATION_TO_MEAL);
    }

    @Test
    @Transactional
    void getAllBloodGlucosesByFieldTemporalRelationToMealIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        bloodGlucoseRepository.saveAndFlush(bloodGlucose);

        // Get all the bloodGlucoseList where fieldTemporalRelationToMeal is less than or equal to DEFAULT_FIELD_TEMPORAL_RELATION_TO_MEAL
        defaultBloodGlucoseShouldBeFound("fieldTemporalRelationToMeal.lessThanOrEqual=" + DEFAULT_FIELD_TEMPORAL_RELATION_TO_MEAL);

        // Get all the bloodGlucoseList where fieldTemporalRelationToMeal is less than or equal to SMALLER_FIELD_TEMPORAL_RELATION_TO_MEAL
        defaultBloodGlucoseShouldNotBeFound("fieldTemporalRelationToMeal.lessThanOrEqual=" + SMALLER_FIELD_TEMPORAL_RELATION_TO_MEAL);
    }

    @Test
    @Transactional
    void getAllBloodGlucosesByFieldTemporalRelationToMealIsLessThanSomething() throws Exception {
        // Initialize the database
        bloodGlucoseRepository.saveAndFlush(bloodGlucose);

        // Get all the bloodGlucoseList where fieldTemporalRelationToMeal is less than DEFAULT_FIELD_TEMPORAL_RELATION_TO_MEAL
        defaultBloodGlucoseShouldNotBeFound("fieldTemporalRelationToMeal.lessThan=" + DEFAULT_FIELD_TEMPORAL_RELATION_TO_MEAL);

        // Get all the bloodGlucoseList where fieldTemporalRelationToMeal is less than UPDATED_FIELD_TEMPORAL_RELATION_TO_MEAL
        defaultBloodGlucoseShouldBeFound("fieldTemporalRelationToMeal.lessThan=" + UPDATED_FIELD_TEMPORAL_RELATION_TO_MEAL);
    }

    @Test
    @Transactional
    void getAllBloodGlucosesByFieldTemporalRelationToMealIsGreaterThanSomething() throws Exception {
        // Initialize the database
        bloodGlucoseRepository.saveAndFlush(bloodGlucose);

        // Get all the bloodGlucoseList where fieldTemporalRelationToMeal is greater than DEFAULT_FIELD_TEMPORAL_RELATION_TO_MEAL
        defaultBloodGlucoseShouldNotBeFound("fieldTemporalRelationToMeal.greaterThan=" + DEFAULT_FIELD_TEMPORAL_RELATION_TO_MEAL);

        // Get all the bloodGlucoseList where fieldTemporalRelationToMeal is greater than SMALLER_FIELD_TEMPORAL_RELATION_TO_MEAL
        defaultBloodGlucoseShouldBeFound("fieldTemporalRelationToMeal.greaterThan=" + SMALLER_FIELD_TEMPORAL_RELATION_TO_MEAL);
    }

    @Test
    @Transactional
    void getAllBloodGlucosesByFieldMealTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        bloodGlucoseRepository.saveAndFlush(bloodGlucose);

        // Get all the bloodGlucoseList where fieldMealType equals to DEFAULT_FIELD_MEAL_TYPE
        defaultBloodGlucoseShouldBeFound("fieldMealType.equals=" + DEFAULT_FIELD_MEAL_TYPE);

        // Get all the bloodGlucoseList where fieldMealType equals to UPDATED_FIELD_MEAL_TYPE
        defaultBloodGlucoseShouldNotBeFound("fieldMealType.equals=" + UPDATED_FIELD_MEAL_TYPE);
    }

    @Test
    @Transactional
    void getAllBloodGlucosesByFieldMealTypeIsInShouldWork() throws Exception {
        // Initialize the database
        bloodGlucoseRepository.saveAndFlush(bloodGlucose);

        // Get all the bloodGlucoseList where fieldMealType in DEFAULT_FIELD_MEAL_TYPE or UPDATED_FIELD_MEAL_TYPE
        defaultBloodGlucoseShouldBeFound("fieldMealType.in=" + DEFAULT_FIELD_MEAL_TYPE + "," + UPDATED_FIELD_MEAL_TYPE);

        // Get all the bloodGlucoseList where fieldMealType equals to UPDATED_FIELD_MEAL_TYPE
        defaultBloodGlucoseShouldNotBeFound("fieldMealType.in=" + UPDATED_FIELD_MEAL_TYPE);
    }

    @Test
    @Transactional
    void getAllBloodGlucosesByFieldMealTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        bloodGlucoseRepository.saveAndFlush(bloodGlucose);

        // Get all the bloodGlucoseList where fieldMealType is not null
        defaultBloodGlucoseShouldBeFound("fieldMealType.specified=true");

        // Get all the bloodGlucoseList where fieldMealType is null
        defaultBloodGlucoseShouldNotBeFound("fieldMealType.specified=false");
    }

    @Test
    @Transactional
    void getAllBloodGlucosesByFieldMealTypeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        bloodGlucoseRepository.saveAndFlush(bloodGlucose);

        // Get all the bloodGlucoseList where fieldMealType is greater than or equal to DEFAULT_FIELD_MEAL_TYPE
        defaultBloodGlucoseShouldBeFound("fieldMealType.greaterThanOrEqual=" + DEFAULT_FIELD_MEAL_TYPE);

        // Get all the bloodGlucoseList where fieldMealType is greater than or equal to UPDATED_FIELD_MEAL_TYPE
        defaultBloodGlucoseShouldNotBeFound("fieldMealType.greaterThanOrEqual=" + UPDATED_FIELD_MEAL_TYPE);
    }

    @Test
    @Transactional
    void getAllBloodGlucosesByFieldMealTypeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        bloodGlucoseRepository.saveAndFlush(bloodGlucose);

        // Get all the bloodGlucoseList where fieldMealType is less than or equal to DEFAULT_FIELD_MEAL_TYPE
        defaultBloodGlucoseShouldBeFound("fieldMealType.lessThanOrEqual=" + DEFAULT_FIELD_MEAL_TYPE);

        // Get all the bloodGlucoseList where fieldMealType is less than or equal to SMALLER_FIELD_MEAL_TYPE
        defaultBloodGlucoseShouldNotBeFound("fieldMealType.lessThanOrEqual=" + SMALLER_FIELD_MEAL_TYPE);
    }

    @Test
    @Transactional
    void getAllBloodGlucosesByFieldMealTypeIsLessThanSomething() throws Exception {
        // Initialize the database
        bloodGlucoseRepository.saveAndFlush(bloodGlucose);

        // Get all the bloodGlucoseList where fieldMealType is less than DEFAULT_FIELD_MEAL_TYPE
        defaultBloodGlucoseShouldNotBeFound("fieldMealType.lessThan=" + DEFAULT_FIELD_MEAL_TYPE);

        // Get all the bloodGlucoseList where fieldMealType is less than UPDATED_FIELD_MEAL_TYPE
        defaultBloodGlucoseShouldBeFound("fieldMealType.lessThan=" + UPDATED_FIELD_MEAL_TYPE);
    }

    @Test
    @Transactional
    void getAllBloodGlucosesByFieldMealTypeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        bloodGlucoseRepository.saveAndFlush(bloodGlucose);

        // Get all the bloodGlucoseList where fieldMealType is greater than DEFAULT_FIELD_MEAL_TYPE
        defaultBloodGlucoseShouldNotBeFound("fieldMealType.greaterThan=" + DEFAULT_FIELD_MEAL_TYPE);

        // Get all the bloodGlucoseList where fieldMealType is greater than SMALLER_FIELD_MEAL_TYPE
        defaultBloodGlucoseShouldBeFound("fieldMealType.greaterThan=" + SMALLER_FIELD_MEAL_TYPE);
    }

    @Test
    @Transactional
    void getAllBloodGlucosesByFieldTemporalRelationToSleepIsEqualToSomething() throws Exception {
        // Initialize the database
        bloodGlucoseRepository.saveAndFlush(bloodGlucose);

        // Get all the bloodGlucoseList where fieldTemporalRelationToSleep equals to DEFAULT_FIELD_TEMPORAL_RELATION_TO_SLEEP
        defaultBloodGlucoseShouldBeFound("fieldTemporalRelationToSleep.equals=" + DEFAULT_FIELD_TEMPORAL_RELATION_TO_SLEEP);

        // Get all the bloodGlucoseList where fieldTemporalRelationToSleep equals to UPDATED_FIELD_TEMPORAL_RELATION_TO_SLEEP
        defaultBloodGlucoseShouldNotBeFound("fieldTemporalRelationToSleep.equals=" + UPDATED_FIELD_TEMPORAL_RELATION_TO_SLEEP);
    }

    @Test
    @Transactional
    void getAllBloodGlucosesByFieldTemporalRelationToSleepIsInShouldWork() throws Exception {
        // Initialize the database
        bloodGlucoseRepository.saveAndFlush(bloodGlucose);

        // Get all the bloodGlucoseList where fieldTemporalRelationToSleep in DEFAULT_FIELD_TEMPORAL_RELATION_TO_SLEEP or UPDATED_FIELD_TEMPORAL_RELATION_TO_SLEEP
        defaultBloodGlucoseShouldBeFound(
            "fieldTemporalRelationToSleep.in=" + DEFAULT_FIELD_TEMPORAL_RELATION_TO_SLEEP + "," + UPDATED_FIELD_TEMPORAL_RELATION_TO_SLEEP
        );

        // Get all the bloodGlucoseList where fieldTemporalRelationToSleep equals to UPDATED_FIELD_TEMPORAL_RELATION_TO_SLEEP
        defaultBloodGlucoseShouldNotBeFound("fieldTemporalRelationToSleep.in=" + UPDATED_FIELD_TEMPORAL_RELATION_TO_SLEEP);
    }

    @Test
    @Transactional
    void getAllBloodGlucosesByFieldTemporalRelationToSleepIsNullOrNotNull() throws Exception {
        // Initialize the database
        bloodGlucoseRepository.saveAndFlush(bloodGlucose);

        // Get all the bloodGlucoseList where fieldTemporalRelationToSleep is not null
        defaultBloodGlucoseShouldBeFound("fieldTemporalRelationToSleep.specified=true");

        // Get all the bloodGlucoseList where fieldTemporalRelationToSleep is null
        defaultBloodGlucoseShouldNotBeFound("fieldTemporalRelationToSleep.specified=false");
    }

    @Test
    @Transactional
    void getAllBloodGlucosesByFieldTemporalRelationToSleepIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        bloodGlucoseRepository.saveAndFlush(bloodGlucose);

        // Get all the bloodGlucoseList where fieldTemporalRelationToSleep is greater than or equal to DEFAULT_FIELD_TEMPORAL_RELATION_TO_SLEEP
        defaultBloodGlucoseShouldBeFound("fieldTemporalRelationToSleep.greaterThanOrEqual=" + DEFAULT_FIELD_TEMPORAL_RELATION_TO_SLEEP);

        // Get all the bloodGlucoseList where fieldTemporalRelationToSleep is greater than or equal to UPDATED_FIELD_TEMPORAL_RELATION_TO_SLEEP
        defaultBloodGlucoseShouldNotBeFound("fieldTemporalRelationToSleep.greaterThanOrEqual=" + UPDATED_FIELD_TEMPORAL_RELATION_TO_SLEEP);
    }

    @Test
    @Transactional
    void getAllBloodGlucosesByFieldTemporalRelationToSleepIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        bloodGlucoseRepository.saveAndFlush(bloodGlucose);

        // Get all the bloodGlucoseList where fieldTemporalRelationToSleep is less than or equal to DEFAULT_FIELD_TEMPORAL_RELATION_TO_SLEEP
        defaultBloodGlucoseShouldBeFound("fieldTemporalRelationToSleep.lessThanOrEqual=" + DEFAULT_FIELD_TEMPORAL_RELATION_TO_SLEEP);

        // Get all the bloodGlucoseList where fieldTemporalRelationToSleep is less than or equal to SMALLER_FIELD_TEMPORAL_RELATION_TO_SLEEP
        defaultBloodGlucoseShouldNotBeFound("fieldTemporalRelationToSleep.lessThanOrEqual=" + SMALLER_FIELD_TEMPORAL_RELATION_TO_SLEEP);
    }

    @Test
    @Transactional
    void getAllBloodGlucosesByFieldTemporalRelationToSleepIsLessThanSomething() throws Exception {
        // Initialize the database
        bloodGlucoseRepository.saveAndFlush(bloodGlucose);

        // Get all the bloodGlucoseList where fieldTemporalRelationToSleep is less than DEFAULT_FIELD_TEMPORAL_RELATION_TO_SLEEP
        defaultBloodGlucoseShouldNotBeFound("fieldTemporalRelationToSleep.lessThan=" + DEFAULT_FIELD_TEMPORAL_RELATION_TO_SLEEP);

        // Get all the bloodGlucoseList where fieldTemporalRelationToSleep is less than UPDATED_FIELD_TEMPORAL_RELATION_TO_SLEEP
        defaultBloodGlucoseShouldBeFound("fieldTemporalRelationToSleep.lessThan=" + UPDATED_FIELD_TEMPORAL_RELATION_TO_SLEEP);
    }

    @Test
    @Transactional
    void getAllBloodGlucosesByFieldTemporalRelationToSleepIsGreaterThanSomething() throws Exception {
        // Initialize the database
        bloodGlucoseRepository.saveAndFlush(bloodGlucose);

        // Get all the bloodGlucoseList where fieldTemporalRelationToSleep is greater than DEFAULT_FIELD_TEMPORAL_RELATION_TO_SLEEP
        defaultBloodGlucoseShouldNotBeFound("fieldTemporalRelationToSleep.greaterThan=" + DEFAULT_FIELD_TEMPORAL_RELATION_TO_SLEEP);

        // Get all the bloodGlucoseList where fieldTemporalRelationToSleep is greater than SMALLER_FIELD_TEMPORAL_RELATION_TO_SLEEP
        defaultBloodGlucoseShouldBeFound("fieldTemporalRelationToSleep.greaterThan=" + SMALLER_FIELD_TEMPORAL_RELATION_TO_SLEEP);
    }

    @Test
    @Transactional
    void getAllBloodGlucosesByFieldBloodGlucoseSpecimenSourceIsEqualToSomething() throws Exception {
        // Initialize the database
        bloodGlucoseRepository.saveAndFlush(bloodGlucose);

        // Get all the bloodGlucoseList where fieldBloodGlucoseSpecimenSource equals to DEFAULT_FIELD_BLOOD_GLUCOSE_SPECIMEN_SOURCE
        defaultBloodGlucoseShouldBeFound("fieldBloodGlucoseSpecimenSource.equals=" + DEFAULT_FIELD_BLOOD_GLUCOSE_SPECIMEN_SOURCE);

        // Get all the bloodGlucoseList where fieldBloodGlucoseSpecimenSource equals to UPDATED_FIELD_BLOOD_GLUCOSE_SPECIMEN_SOURCE
        defaultBloodGlucoseShouldNotBeFound("fieldBloodGlucoseSpecimenSource.equals=" + UPDATED_FIELD_BLOOD_GLUCOSE_SPECIMEN_SOURCE);
    }

    @Test
    @Transactional
    void getAllBloodGlucosesByFieldBloodGlucoseSpecimenSourceIsInShouldWork() throws Exception {
        // Initialize the database
        bloodGlucoseRepository.saveAndFlush(bloodGlucose);

        // Get all the bloodGlucoseList where fieldBloodGlucoseSpecimenSource in DEFAULT_FIELD_BLOOD_GLUCOSE_SPECIMEN_SOURCE or UPDATED_FIELD_BLOOD_GLUCOSE_SPECIMEN_SOURCE
        defaultBloodGlucoseShouldBeFound(
            "fieldBloodGlucoseSpecimenSource.in=" +
            DEFAULT_FIELD_BLOOD_GLUCOSE_SPECIMEN_SOURCE +
            "," +
            UPDATED_FIELD_BLOOD_GLUCOSE_SPECIMEN_SOURCE
        );

        // Get all the bloodGlucoseList where fieldBloodGlucoseSpecimenSource equals to UPDATED_FIELD_BLOOD_GLUCOSE_SPECIMEN_SOURCE
        defaultBloodGlucoseShouldNotBeFound("fieldBloodGlucoseSpecimenSource.in=" + UPDATED_FIELD_BLOOD_GLUCOSE_SPECIMEN_SOURCE);
    }

    @Test
    @Transactional
    void getAllBloodGlucosesByFieldBloodGlucoseSpecimenSourceIsNullOrNotNull() throws Exception {
        // Initialize the database
        bloodGlucoseRepository.saveAndFlush(bloodGlucose);

        // Get all the bloodGlucoseList where fieldBloodGlucoseSpecimenSource is not null
        defaultBloodGlucoseShouldBeFound("fieldBloodGlucoseSpecimenSource.specified=true");

        // Get all the bloodGlucoseList where fieldBloodGlucoseSpecimenSource is null
        defaultBloodGlucoseShouldNotBeFound("fieldBloodGlucoseSpecimenSource.specified=false");
    }

    @Test
    @Transactional
    void getAllBloodGlucosesByFieldBloodGlucoseSpecimenSourceIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        bloodGlucoseRepository.saveAndFlush(bloodGlucose);

        // Get all the bloodGlucoseList where fieldBloodGlucoseSpecimenSource is greater than or equal to DEFAULT_FIELD_BLOOD_GLUCOSE_SPECIMEN_SOURCE
        defaultBloodGlucoseShouldBeFound(
            "fieldBloodGlucoseSpecimenSource.greaterThanOrEqual=" + DEFAULT_FIELD_BLOOD_GLUCOSE_SPECIMEN_SOURCE
        );

        // Get all the bloodGlucoseList where fieldBloodGlucoseSpecimenSource is greater than or equal to UPDATED_FIELD_BLOOD_GLUCOSE_SPECIMEN_SOURCE
        defaultBloodGlucoseShouldNotBeFound(
            "fieldBloodGlucoseSpecimenSource.greaterThanOrEqual=" + UPDATED_FIELD_BLOOD_GLUCOSE_SPECIMEN_SOURCE
        );
    }

    @Test
    @Transactional
    void getAllBloodGlucosesByFieldBloodGlucoseSpecimenSourceIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        bloodGlucoseRepository.saveAndFlush(bloodGlucose);

        // Get all the bloodGlucoseList where fieldBloodGlucoseSpecimenSource is less than or equal to DEFAULT_FIELD_BLOOD_GLUCOSE_SPECIMEN_SOURCE
        defaultBloodGlucoseShouldBeFound("fieldBloodGlucoseSpecimenSource.lessThanOrEqual=" + DEFAULT_FIELD_BLOOD_GLUCOSE_SPECIMEN_SOURCE);

        // Get all the bloodGlucoseList where fieldBloodGlucoseSpecimenSource is less than or equal to SMALLER_FIELD_BLOOD_GLUCOSE_SPECIMEN_SOURCE
        defaultBloodGlucoseShouldNotBeFound(
            "fieldBloodGlucoseSpecimenSource.lessThanOrEqual=" + SMALLER_FIELD_BLOOD_GLUCOSE_SPECIMEN_SOURCE
        );
    }

    @Test
    @Transactional
    void getAllBloodGlucosesByFieldBloodGlucoseSpecimenSourceIsLessThanSomething() throws Exception {
        // Initialize the database
        bloodGlucoseRepository.saveAndFlush(bloodGlucose);

        // Get all the bloodGlucoseList where fieldBloodGlucoseSpecimenSource is less than DEFAULT_FIELD_BLOOD_GLUCOSE_SPECIMEN_SOURCE
        defaultBloodGlucoseShouldNotBeFound("fieldBloodGlucoseSpecimenSource.lessThan=" + DEFAULT_FIELD_BLOOD_GLUCOSE_SPECIMEN_SOURCE);

        // Get all the bloodGlucoseList where fieldBloodGlucoseSpecimenSource is less than UPDATED_FIELD_BLOOD_GLUCOSE_SPECIMEN_SOURCE
        defaultBloodGlucoseShouldBeFound("fieldBloodGlucoseSpecimenSource.lessThan=" + UPDATED_FIELD_BLOOD_GLUCOSE_SPECIMEN_SOURCE);
    }

    @Test
    @Transactional
    void getAllBloodGlucosesByFieldBloodGlucoseSpecimenSourceIsGreaterThanSomething() throws Exception {
        // Initialize the database
        bloodGlucoseRepository.saveAndFlush(bloodGlucose);

        // Get all the bloodGlucoseList where fieldBloodGlucoseSpecimenSource is greater than DEFAULT_FIELD_BLOOD_GLUCOSE_SPECIMEN_SOURCE
        defaultBloodGlucoseShouldNotBeFound("fieldBloodGlucoseSpecimenSource.greaterThan=" + DEFAULT_FIELD_BLOOD_GLUCOSE_SPECIMEN_SOURCE);

        // Get all the bloodGlucoseList where fieldBloodGlucoseSpecimenSource is greater than SMALLER_FIELD_BLOOD_GLUCOSE_SPECIMEN_SOURCE
        defaultBloodGlucoseShouldBeFound("fieldBloodGlucoseSpecimenSource.greaterThan=" + SMALLER_FIELD_BLOOD_GLUCOSE_SPECIMEN_SOURCE);
    }

    @Test
    @Transactional
    void getAllBloodGlucosesByEndTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        bloodGlucoseRepository.saveAndFlush(bloodGlucose);

        // Get all the bloodGlucoseList where endTime equals to DEFAULT_END_TIME
        defaultBloodGlucoseShouldBeFound("endTime.equals=" + DEFAULT_END_TIME);

        // Get all the bloodGlucoseList where endTime equals to UPDATED_END_TIME
        defaultBloodGlucoseShouldNotBeFound("endTime.equals=" + UPDATED_END_TIME);
    }

    @Test
    @Transactional
    void getAllBloodGlucosesByEndTimeIsInShouldWork() throws Exception {
        // Initialize the database
        bloodGlucoseRepository.saveAndFlush(bloodGlucose);

        // Get all the bloodGlucoseList where endTime in DEFAULT_END_TIME or UPDATED_END_TIME
        defaultBloodGlucoseShouldBeFound("endTime.in=" + DEFAULT_END_TIME + "," + UPDATED_END_TIME);

        // Get all the bloodGlucoseList where endTime equals to UPDATED_END_TIME
        defaultBloodGlucoseShouldNotBeFound("endTime.in=" + UPDATED_END_TIME);
    }

    @Test
    @Transactional
    void getAllBloodGlucosesByEndTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        bloodGlucoseRepository.saveAndFlush(bloodGlucose);

        // Get all the bloodGlucoseList where endTime is not null
        defaultBloodGlucoseShouldBeFound("endTime.specified=true");

        // Get all the bloodGlucoseList where endTime is null
        defaultBloodGlucoseShouldNotBeFound("endTime.specified=false");
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultBloodGlucoseShouldBeFound(String filter) throws Exception {
        restBloodGlucoseMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bloodGlucose.getId().toString())))
            .andExpect(jsonPath("$.[*].usuarioId").value(hasItem(DEFAULT_USUARIO_ID)))
            .andExpect(jsonPath("$.[*].empresaId").value(hasItem(DEFAULT_EMPRESA_ID)))
            .andExpect(jsonPath("$.[*].fieldBloodGlucoseLevel").value(hasItem(DEFAULT_FIELD_BLOOD_GLUCOSE_LEVEL.doubleValue())))
            .andExpect(jsonPath("$.[*].fieldTemporalRelationToMeal").value(hasItem(DEFAULT_FIELD_TEMPORAL_RELATION_TO_MEAL)))
            .andExpect(jsonPath("$.[*].fieldMealType").value(hasItem(DEFAULT_FIELD_MEAL_TYPE)))
            .andExpect(jsonPath("$.[*].fieldTemporalRelationToSleep").value(hasItem(DEFAULT_FIELD_TEMPORAL_RELATION_TO_SLEEP)))
            .andExpect(jsonPath("$.[*].fieldBloodGlucoseSpecimenSource").value(hasItem(DEFAULT_FIELD_BLOOD_GLUCOSE_SPECIMEN_SOURCE)))
            .andExpect(jsonPath("$.[*].endTime").value(hasItem(DEFAULT_END_TIME.toString())));

        // Check, that the count call also returns 1
        restBloodGlucoseMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultBloodGlucoseShouldNotBeFound(String filter) throws Exception {
        restBloodGlucoseMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restBloodGlucoseMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingBloodGlucose() throws Exception {
        // Get the bloodGlucose
        restBloodGlucoseMockMvc.perform(get(ENTITY_API_URL_ID, UUID.randomUUID().toString())).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingBloodGlucose() throws Exception {
        // Initialize the database
        bloodGlucoseRepository.saveAndFlush(bloodGlucose);

        int databaseSizeBeforeUpdate = bloodGlucoseRepository.findAll().size();

        // Update the bloodGlucose
        BloodGlucose updatedBloodGlucose = bloodGlucoseRepository.findById(bloodGlucose.getId()).get();
        // Disconnect from session so that the updates on updatedBloodGlucose are not directly saved in db
        em.detach(updatedBloodGlucose);
        updatedBloodGlucose
            .usuarioId(UPDATED_USUARIO_ID)
            .empresaId(UPDATED_EMPRESA_ID)
            .fieldBloodGlucoseLevel(UPDATED_FIELD_BLOOD_GLUCOSE_LEVEL)
            .fieldTemporalRelationToMeal(UPDATED_FIELD_TEMPORAL_RELATION_TO_MEAL)
            .fieldMealType(UPDATED_FIELD_MEAL_TYPE)
            .fieldTemporalRelationToSleep(UPDATED_FIELD_TEMPORAL_RELATION_TO_SLEEP)
            .fieldBloodGlucoseSpecimenSource(UPDATED_FIELD_BLOOD_GLUCOSE_SPECIMEN_SOURCE)
            .endTime(UPDATED_END_TIME);

        restBloodGlucoseMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedBloodGlucose.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedBloodGlucose))
            )
            .andExpect(status().isOk());

        // Validate the BloodGlucose in the database
        List<BloodGlucose> bloodGlucoseList = bloodGlucoseRepository.findAll();
        assertThat(bloodGlucoseList).hasSize(databaseSizeBeforeUpdate);
        BloodGlucose testBloodGlucose = bloodGlucoseList.get(bloodGlucoseList.size() - 1);
        assertThat(testBloodGlucose.getUsuarioId()).isEqualTo(UPDATED_USUARIO_ID);
        assertThat(testBloodGlucose.getEmpresaId()).isEqualTo(UPDATED_EMPRESA_ID);
        assertThat(testBloodGlucose.getFieldBloodGlucoseLevel()).isEqualTo(UPDATED_FIELD_BLOOD_GLUCOSE_LEVEL);
        assertThat(testBloodGlucose.getFieldTemporalRelationToMeal()).isEqualTo(UPDATED_FIELD_TEMPORAL_RELATION_TO_MEAL);
        assertThat(testBloodGlucose.getFieldMealType()).isEqualTo(UPDATED_FIELD_MEAL_TYPE);
        assertThat(testBloodGlucose.getFieldTemporalRelationToSleep()).isEqualTo(UPDATED_FIELD_TEMPORAL_RELATION_TO_SLEEP);
        assertThat(testBloodGlucose.getFieldBloodGlucoseSpecimenSource()).isEqualTo(UPDATED_FIELD_BLOOD_GLUCOSE_SPECIMEN_SOURCE);
        assertThat(testBloodGlucose.getEndTime()).isEqualTo(UPDATED_END_TIME);
    }

    @Test
    @Transactional
    void putNonExistingBloodGlucose() throws Exception {
        int databaseSizeBeforeUpdate = bloodGlucoseRepository.findAll().size();
        bloodGlucose.setId(UUID.randomUUID());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBloodGlucoseMockMvc
            .perform(
                put(ENTITY_API_URL_ID, bloodGlucose.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bloodGlucose))
            )
            .andExpect(status().isBadRequest());

        // Validate the BloodGlucose in the database
        List<BloodGlucose> bloodGlucoseList = bloodGlucoseRepository.findAll();
        assertThat(bloodGlucoseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchBloodGlucose() throws Exception {
        int databaseSizeBeforeUpdate = bloodGlucoseRepository.findAll().size();
        bloodGlucose.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBloodGlucoseMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bloodGlucose))
            )
            .andExpect(status().isBadRequest());

        // Validate the BloodGlucose in the database
        List<BloodGlucose> bloodGlucoseList = bloodGlucoseRepository.findAll();
        assertThat(bloodGlucoseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamBloodGlucose() throws Exception {
        int databaseSizeBeforeUpdate = bloodGlucoseRepository.findAll().size();
        bloodGlucose.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBloodGlucoseMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bloodGlucose)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the BloodGlucose in the database
        List<BloodGlucose> bloodGlucoseList = bloodGlucoseRepository.findAll();
        assertThat(bloodGlucoseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateBloodGlucoseWithPatch() throws Exception {
        // Initialize the database
        bloodGlucoseRepository.saveAndFlush(bloodGlucose);

        int databaseSizeBeforeUpdate = bloodGlucoseRepository.findAll().size();

        // Update the bloodGlucose using partial update
        BloodGlucose partialUpdatedBloodGlucose = new BloodGlucose();
        partialUpdatedBloodGlucose.setId(bloodGlucose.getId());

        partialUpdatedBloodGlucose
            .usuarioId(UPDATED_USUARIO_ID)
            .fieldBloodGlucoseLevel(UPDATED_FIELD_BLOOD_GLUCOSE_LEVEL)
            .fieldMealType(UPDATED_FIELD_MEAL_TYPE)
            .fieldBloodGlucoseSpecimenSource(UPDATED_FIELD_BLOOD_GLUCOSE_SPECIMEN_SOURCE)
            .endTime(UPDATED_END_TIME);

        restBloodGlucoseMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBloodGlucose.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBloodGlucose))
            )
            .andExpect(status().isOk());

        // Validate the BloodGlucose in the database
        List<BloodGlucose> bloodGlucoseList = bloodGlucoseRepository.findAll();
        assertThat(bloodGlucoseList).hasSize(databaseSizeBeforeUpdate);
        BloodGlucose testBloodGlucose = bloodGlucoseList.get(bloodGlucoseList.size() - 1);
        assertThat(testBloodGlucose.getUsuarioId()).isEqualTo(UPDATED_USUARIO_ID);
        assertThat(testBloodGlucose.getEmpresaId()).isEqualTo(DEFAULT_EMPRESA_ID);
        assertThat(testBloodGlucose.getFieldBloodGlucoseLevel()).isEqualTo(UPDATED_FIELD_BLOOD_GLUCOSE_LEVEL);
        assertThat(testBloodGlucose.getFieldTemporalRelationToMeal()).isEqualTo(DEFAULT_FIELD_TEMPORAL_RELATION_TO_MEAL);
        assertThat(testBloodGlucose.getFieldMealType()).isEqualTo(UPDATED_FIELD_MEAL_TYPE);
        assertThat(testBloodGlucose.getFieldTemporalRelationToSleep()).isEqualTo(DEFAULT_FIELD_TEMPORAL_RELATION_TO_SLEEP);
        assertThat(testBloodGlucose.getFieldBloodGlucoseSpecimenSource()).isEqualTo(UPDATED_FIELD_BLOOD_GLUCOSE_SPECIMEN_SOURCE);
        assertThat(testBloodGlucose.getEndTime()).isEqualTo(UPDATED_END_TIME);
    }

    @Test
    @Transactional
    void fullUpdateBloodGlucoseWithPatch() throws Exception {
        // Initialize the database
        bloodGlucoseRepository.saveAndFlush(bloodGlucose);

        int databaseSizeBeforeUpdate = bloodGlucoseRepository.findAll().size();

        // Update the bloodGlucose using partial update
        BloodGlucose partialUpdatedBloodGlucose = new BloodGlucose();
        partialUpdatedBloodGlucose.setId(bloodGlucose.getId());

        partialUpdatedBloodGlucose
            .usuarioId(UPDATED_USUARIO_ID)
            .empresaId(UPDATED_EMPRESA_ID)
            .fieldBloodGlucoseLevel(UPDATED_FIELD_BLOOD_GLUCOSE_LEVEL)
            .fieldTemporalRelationToMeal(UPDATED_FIELD_TEMPORAL_RELATION_TO_MEAL)
            .fieldMealType(UPDATED_FIELD_MEAL_TYPE)
            .fieldTemporalRelationToSleep(UPDATED_FIELD_TEMPORAL_RELATION_TO_SLEEP)
            .fieldBloodGlucoseSpecimenSource(UPDATED_FIELD_BLOOD_GLUCOSE_SPECIMEN_SOURCE)
            .endTime(UPDATED_END_TIME);

        restBloodGlucoseMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBloodGlucose.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBloodGlucose))
            )
            .andExpect(status().isOk());

        // Validate the BloodGlucose in the database
        List<BloodGlucose> bloodGlucoseList = bloodGlucoseRepository.findAll();
        assertThat(bloodGlucoseList).hasSize(databaseSizeBeforeUpdate);
        BloodGlucose testBloodGlucose = bloodGlucoseList.get(bloodGlucoseList.size() - 1);
        assertThat(testBloodGlucose.getUsuarioId()).isEqualTo(UPDATED_USUARIO_ID);
        assertThat(testBloodGlucose.getEmpresaId()).isEqualTo(UPDATED_EMPRESA_ID);
        assertThat(testBloodGlucose.getFieldBloodGlucoseLevel()).isEqualTo(UPDATED_FIELD_BLOOD_GLUCOSE_LEVEL);
        assertThat(testBloodGlucose.getFieldTemporalRelationToMeal()).isEqualTo(UPDATED_FIELD_TEMPORAL_RELATION_TO_MEAL);
        assertThat(testBloodGlucose.getFieldMealType()).isEqualTo(UPDATED_FIELD_MEAL_TYPE);
        assertThat(testBloodGlucose.getFieldTemporalRelationToSleep()).isEqualTo(UPDATED_FIELD_TEMPORAL_RELATION_TO_SLEEP);
        assertThat(testBloodGlucose.getFieldBloodGlucoseSpecimenSource()).isEqualTo(UPDATED_FIELD_BLOOD_GLUCOSE_SPECIMEN_SOURCE);
        assertThat(testBloodGlucose.getEndTime()).isEqualTo(UPDATED_END_TIME);
    }

    @Test
    @Transactional
    void patchNonExistingBloodGlucose() throws Exception {
        int databaseSizeBeforeUpdate = bloodGlucoseRepository.findAll().size();
        bloodGlucose.setId(UUID.randomUUID());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBloodGlucoseMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, bloodGlucose.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(bloodGlucose))
            )
            .andExpect(status().isBadRequest());

        // Validate the BloodGlucose in the database
        List<BloodGlucose> bloodGlucoseList = bloodGlucoseRepository.findAll();
        assertThat(bloodGlucoseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchBloodGlucose() throws Exception {
        int databaseSizeBeforeUpdate = bloodGlucoseRepository.findAll().size();
        bloodGlucose.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBloodGlucoseMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(bloodGlucose))
            )
            .andExpect(status().isBadRequest());

        // Validate the BloodGlucose in the database
        List<BloodGlucose> bloodGlucoseList = bloodGlucoseRepository.findAll();
        assertThat(bloodGlucoseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamBloodGlucose() throws Exception {
        int databaseSizeBeforeUpdate = bloodGlucoseRepository.findAll().size();
        bloodGlucose.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBloodGlucoseMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(bloodGlucose))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the BloodGlucose in the database
        List<BloodGlucose> bloodGlucoseList = bloodGlucoseRepository.findAll();
        assertThat(bloodGlucoseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteBloodGlucose() throws Exception {
        // Initialize the database
        bloodGlucoseRepository.saveAndFlush(bloodGlucose);

        int databaseSizeBeforeDelete = bloodGlucoseRepository.findAll().size();

        // Delete the bloodGlucose
        restBloodGlucoseMockMvc
            .perform(delete(ENTITY_API_URL_ID, bloodGlucose.getId().toString()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<BloodGlucose> bloodGlucoseList = bloodGlucoseRepository.findAll();
        assertThat(bloodGlucoseList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
