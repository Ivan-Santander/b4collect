package com.be4tech.b4carecollect.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.be4tech.b4carecollect.IntegrationTest;
import com.be4tech.b4carecollect.domain.BloodPressureSummary;
import com.be4tech.b4carecollect.repository.BloodPressureSummaryRepository;
import com.be4tech.b4carecollect.service.criteria.BloodPressureSummaryCriteria;
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
 * Integration tests for the {@link BloodPressureSummaryResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class BloodPressureSummaryResourceIT {

    private static final String DEFAULT_USUARIO_ID = "AAAAAAAAAA";
    private static final String UPDATED_USUARIO_ID = "BBBBBBBBBB";

    private static final String DEFAULT_EMPRESA_ID = "AAAAAAAAAA";
    private static final String UPDATED_EMPRESA_ID = "BBBBBBBBBB";

    private static final Float DEFAULT_FIELD_SISTOLIC_AVERAGE = 1F;
    private static final Float UPDATED_FIELD_SISTOLIC_AVERAGE = 2F;
    private static final Float SMALLER_FIELD_SISTOLIC_AVERAGE = 1F - 1F;

    private static final Float DEFAULT_FIELD_SISTOLIC_MAX = 1F;
    private static final Float UPDATED_FIELD_SISTOLIC_MAX = 2F;
    private static final Float SMALLER_FIELD_SISTOLIC_MAX = 1F - 1F;

    private static final Float DEFAULT_FIELD_SISTOLIC_MIN = 1F;
    private static final Float UPDATED_FIELD_SISTOLIC_MIN = 2F;
    private static final Float SMALLER_FIELD_SISTOLIC_MIN = 1F - 1F;

    private static final Float DEFAULT_FIELD_DIASATOLIC_AVERAGE = 1F;
    private static final Float UPDATED_FIELD_DIASATOLIC_AVERAGE = 2F;
    private static final Float SMALLER_FIELD_DIASATOLIC_AVERAGE = 1F - 1F;

    private static final Float DEFAULT_FIELD_DIASTOLIC_MAX = 1F;
    private static final Float UPDATED_FIELD_DIASTOLIC_MAX = 2F;
    private static final Float SMALLER_FIELD_DIASTOLIC_MAX = 1F - 1F;

    private static final Float DEFAULT_FIELD_DIASTOLIC_MIN = 1F;
    private static final Float UPDATED_FIELD_DIASTOLIC_MIN = 2F;
    private static final Float SMALLER_FIELD_DIASTOLIC_MIN = 1F - 1F;

    private static final Integer DEFAULT_BODY_POSITION = 1;
    private static final Integer UPDATED_BODY_POSITION = 2;
    private static final Integer SMALLER_BODY_POSITION = 1 - 1;

    private static final Integer DEFAULT_MEASUREMENT_LOCATION = 1;
    private static final Integer UPDATED_MEASUREMENT_LOCATION = 2;
    private static final Integer SMALLER_MEASUREMENT_LOCATION = 1 - 1;

    private static final Instant DEFAULT_START_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_START_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_END_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_END_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/blood-pressure-summaries";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private BloodPressureSummaryRepository bloodPressureSummaryRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBloodPressureSummaryMockMvc;

    private BloodPressureSummary bloodPressureSummary;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BloodPressureSummary createEntity(EntityManager em) {
        BloodPressureSummary bloodPressureSummary = new BloodPressureSummary()
            .usuarioId(DEFAULT_USUARIO_ID)
            .empresaId(DEFAULT_EMPRESA_ID)
            .fieldSistolicAverage(DEFAULT_FIELD_SISTOLIC_AVERAGE)
            .fieldSistolicMax(DEFAULT_FIELD_SISTOLIC_MAX)
            .fieldSistolicMin(DEFAULT_FIELD_SISTOLIC_MIN)
            .fieldDiasatolicAverage(DEFAULT_FIELD_DIASATOLIC_AVERAGE)
            .fieldDiastolicMax(DEFAULT_FIELD_DIASTOLIC_MAX)
            .fieldDiastolicMin(DEFAULT_FIELD_DIASTOLIC_MIN)
            .bodyPosition(DEFAULT_BODY_POSITION)
            .measurementLocation(DEFAULT_MEASUREMENT_LOCATION)
            .startTime(DEFAULT_START_TIME)
            .endTime(DEFAULT_END_TIME);
        return bloodPressureSummary;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BloodPressureSummary createUpdatedEntity(EntityManager em) {
        BloodPressureSummary bloodPressureSummary = new BloodPressureSummary()
            .usuarioId(UPDATED_USUARIO_ID)
            .empresaId(UPDATED_EMPRESA_ID)
            .fieldSistolicAverage(UPDATED_FIELD_SISTOLIC_AVERAGE)
            .fieldSistolicMax(UPDATED_FIELD_SISTOLIC_MAX)
            .fieldSistolicMin(UPDATED_FIELD_SISTOLIC_MIN)
            .fieldDiasatolicAverage(UPDATED_FIELD_DIASATOLIC_AVERAGE)
            .fieldDiastolicMax(UPDATED_FIELD_DIASTOLIC_MAX)
            .fieldDiastolicMin(UPDATED_FIELD_DIASTOLIC_MIN)
            .bodyPosition(UPDATED_BODY_POSITION)
            .measurementLocation(UPDATED_MEASUREMENT_LOCATION)
            .startTime(UPDATED_START_TIME)
            .endTime(UPDATED_END_TIME);
        return bloodPressureSummary;
    }

    @BeforeEach
    public void initTest() {
        bloodPressureSummary = createEntity(em);
    }

    @Test
    @Transactional
    void createBloodPressureSummary() throws Exception {
        int databaseSizeBeforeCreate = bloodPressureSummaryRepository.findAll().size();
        // Create the BloodPressureSummary
        restBloodPressureSummaryMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bloodPressureSummary))
            )
            .andExpect(status().isCreated());

        // Validate the BloodPressureSummary in the database
        List<BloodPressureSummary> bloodPressureSummaryList = bloodPressureSummaryRepository.findAll();
        assertThat(bloodPressureSummaryList).hasSize(databaseSizeBeforeCreate + 1);
        BloodPressureSummary testBloodPressureSummary = bloodPressureSummaryList.get(bloodPressureSummaryList.size() - 1);
        assertThat(testBloodPressureSummary.getUsuarioId()).isEqualTo(DEFAULT_USUARIO_ID);
        assertThat(testBloodPressureSummary.getEmpresaId()).isEqualTo(DEFAULT_EMPRESA_ID);
        assertThat(testBloodPressureSummary.getFieldSistolicAverage()).isEqualTo(DEFAULT_FIELD_SISTOLIC_AVERAGE);
        assertThat(testBloodPressureSummary.getFieldSistolicMax()).isEqualTo(DEFAULT_FIELD_SISTOLIC_MAX);
        assertThat(testBloodPressureSummary.getFieldSistolicMin()).isEqualTo(DEFAULT_FIELD_SISTOLIC_MIN);
        assertThat(testBloodPressureSummary.getFieldDiasatolicAverage()).isEqualTo(DEFAULT_FIELD_DIASATOLIC_AVERAGE);
        assertThat(testBloodPressureSummary.getFieldDiastolicMax()).isEqualTo(DEFAULT_FIELD_DIASTOLIC_MAX);
        assertThat(testBloodPressureSummary.getFieldDiastolicMin()).isEqualTo(DEFAULT_FIELD_DIASTOLIC_MIN);
        assertThat(testBloodPressureSummary.getBodyPosition()).isEqualTo(DEFAULT_BODY_POSITION);
        assertThat(testBloodPressureSummary.getMeasurementLocation()).isEqualTo(DEFAULT_MEASUREMENT_LOCATION);
        assertThat(testBloodPressureSummary.getStartTime()).isEqualTo(DEFAULT_START_TIME);
        assertThat(testBloodPressureSummary.getEndTime()).isEqualTo(DEFAULT_END_TIME);
    }

    @Test
    @Transactional
    void createBloodPressureSummaryWithExistingId() throws Exception {
        // Create the BloodPressureSummary with an existing ID
        bloodPressureSummaryRepository.saveAndFlush(bloodPressureSummary);

        int databaseSizeBeforeCreate = bloodPressureSummaryRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBloodPressureSummaryMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bloodPressureSummary))
            )
            .andExpect(status().isBadRequest());

        // Validate the BloodPressureSummary in the database
        List<BloodPressureSummary> bloodPressureSummaryList = bloodPressureSummaryRepository.findAll();
        assertThat(bloodPressureSummaryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllBloodPressureSummaries() throws Exception {
        // Initialize the database
        bloodPressureSummaryRepository.saveAndFlush(bloodPressureSummary);

        // Get all the bloodPressureSummaryList
        restBloodPressureSummaryMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bloodPressureSummary.getId().toString())))
            .andExpect(jsonPath("$.[*].usuarioId").value(hasItem(DEFAULT_USUARIO_ID)))
            .andExpect(jsonPath("$.[*].empresaId").value(hasItem(DEFAULT_EMPRESA_ID)))
            .andExpect(jsonPath("$.[*].fieldSistolicAverage").value(hasItem(DEFAULT_FIELD_SISTOLIC_AVERAGE.doubleValue())))
            .andExpect(jsonPath("$.[*].fieldSistolicMax").value(hasItem(DEFAULT_FIELD_SISTOLIC_MAX.doubleValue())))
            .andExpect(jsonPath("$.[*].fieldSistolicMin").value(hasItem(DEFAULT_FIELD_SISTOLIC_MIN.doubleValue())))
            .andExpect(jsonPath("$.[*].fieldDiasatolicAverage").value(hasItem(DEFAULT_FIELD_DIASATOLIC_AVERAGE.doubleValue())))
            .andExpect(jsonPath("$.[*].fieldDiastolicMax").value(hasItem(DEFAULT_FIELD_DIASTOLIC_MAX.doubleValue())))
            .andExpect(jsonPath("$.[*].fieldDiastolicMin").value(hasItem(DEFAULT_FIELD_DIASTOLIC_MIN.doubleValue())))
            .andExpect(jsonPath("$.[*].bodyPosition").value(hasItem(DEFAULT_BODY_POSITION)))
            .andExpect(jsonPath("$.[*].measurementLocation").value(hasItem(DEFAULT_MEASUREMENT_LOCATION)))
            .andExpect(jsonPath("$.[*].startTime").value(hasItem(DEFAULT_START_TIME.toString())))
            .andExpect(jsonPath("$.[*].endTime").value(hasItem(DEFAULT_END_TIME.toString())));
    }

    @Test
    @Transactional
    void getBloodPressureSummary() throws Exception {
        // Initialize the database
        bloodPressureSummaryRepository.saveAndFlush(bloodPressureSummary);

        // Get the bloodPressureSummary
        restBloodPressureSummaryMockMvc
            .perform(get(ENTITY_API_URL_ID, bloodPressureSummary.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(bloodPressureSummary.getId().toString()))
            .andExpect(jsonPath("$.usuarioId").value(DEFAULT_USUARIO_ID))
            .andExpect(jsonPath("$.empresaId").value(DEFAULT_EMPRESA_ID))
            .andExpect(jsonPath("$.fieldSistolicAverage").value(DEFAULT_FIELD_SISTOLIC_AVERAGE.doubleValue()))
            .andExpect(jsonPath("$.fieldSistolicMax").value(DEFAULT_FIELD_SISTOLIC_MAX.doubleValue()))
            .andExpect(jsonPath("$.fieldSistolicMin").value(DEFAULT_FIELD_SISTOLIC_MIN.doubleValue()))
            .andExpect(jsonPath("$.fieldDiasatolicAverage").value(DEFAULT_FIELD_DIASATOLIC_AVERAGE.doubleValue()))
            .andExpect(jsonPath("$.fieldDiastolicMax").value(DEFAULT_FIELD_DIASTOLIC_MAX.doubleValue()))
            .andExpect(jsonPath("$.fieldDiastolicMin").value(DEFAULT_FIELD_DIASTOLIC_MIN.doubleValue()))
            .andExpect(jsonPath("$.bodyPosition").value(DEFAULT_BODY_POSITION))
            .andExpect(jsonPath("$.measurementLocation").value(DEFAULT_MEASUREMENT_LOCATION))
            .andExpect(jsonPath("$.startTime").value(DEFAULT_START_TIME.toString()))
            .andExpect(jsonPath("$.endTime").value(DEFAULT_END_TIME.toString()));
    }

    @Test
    @Transactional
    void getBloodPressureSummariesByIdFiltering() throws Exception {
        // Initialize the database
        bloodPressureSummaryRepository.saveAndFlush(bloodPressureSummary);

        UUID id = bloodPressureSummary.getId();

        defaultBloodPressureSummaryShouldBeFound("id.equals=" + id);
        defaultBloodPressureSummaryShouldNotBeFound("id.notEquals=" + id);
    }

    @Test
    @Transactional
    void getAllBloodPressureSummariesByUsuarioIdIsEqualToSomething() throws Exception {
        // Initialize the database
        bloodPressureSummaryRepository.saveAndFlush(bloodPressureSummary);

        // Get all the bloodPressureSummaryList where usuarioId equals to DEFAULT_USUARIO_ID
        defaultBloodPressureSummaryShouldBeFound("usuarioId.equals=" + DEFAULT_USUARIO_ID);

        // Get all the bloodPressureSummaryList where usuarioId equals to UPDATED_USUARIO_ID
        defaultBloodPressureSummaryShouldNotBeFound("usuarioId.equals=" + UPDATED_USUARIO_ID);
    }

    @Test
    @Transactional
    void getAllBloodPressureSummariesByUsuarioIdIsInShouldWork() throws Exception {
        // Initialize the database
        bloodPressureSummaryRepository.saveAndFlush(bloodPressureSummary);

        // Get all the bloodPressureSummaryList where usuarioId in DEFAULT_USUARIO_ID or UPDATED_USUARIO_ID
        defaultBloodPressureSummaryShouldBeFound("usuarioId.in=" + DEFAULT_USUARIO_ID + "," + UPDATED_USUARIO_ID);

        // Get all the bloodPressureSummaryList where usuarioId equals to UPDATED_USUARIO_ID
        defaultBloodPressureSummaryShouldNotBeFound("usuarioId.in=" + UPDATED_USUARIO_ID);
    }

    @Test
    @Transactional
    void getAllBloodPressureSummariesByUsuarioIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        bloodPressureSummaryRepository.saveAndFlush(bloodPressureSummary);

        // Get all the bloodPressureSummaryList where usuarioId is not null
        defaultBloodPressureSummaryShouldBeFound("usuarioId.specified=true");

        // Get all the bloodPressureSummaryList where usuarioId is null
        defaultBloodPressureSummaryShouldNotBeFound("usuarioId.specified=false");
    }

    @Test
    @Transactional
    void getAllBloodPressureSummariesByUsuarioIdContainsSomething() throws Exception {
        // Initialize the database
        bloodPressureSummaryRepository.saveAndFlush(bloodPressureSummary);

        // Get all the bloodPressureSummaryList where usuarioId contains DEFAULT_USUARIO_ID
        defaultBloodPressureSummaryShouldBeFound("usuarioId.contains=" + DEFAULT_USUARIO_ID);

        // Get all the bloodPressureSummaryList where usuarioId contains UPDATED_USUARIO_ID
        defaultBloodPressureSummaryShouldNotBeFound("usuarioId.contains=" + UPDATED_USUARIO_ID);
    }

    @Test
    @Transactional
    void getAllBloodPressureSummariesByUsuarioIdNotContainsSomething() throws Exception {
        // Initialize the database
        bloodPressureSummaryRepository.saveAndFlush(bloodPressureSummary);

        // Get all the bloodPressureSummaryList where usuarioId does not contain DEFAULT_USUARIO_ID
        defaultBloodPressureSummaryShouldNotBeFound("usuarioId.doesNotContain=" + DEFAULT_USUARIO_ID);

        // Get all the bloodPressureSummaryList where usuarioId does not contain UPDATED_USUARIO_ID
        defaultBloodPressureSummaryShouldBeFound("usuarioId.doesNotContain=" + UPDATED_USUARIO_ID);
    }

    @Test
    @Transactional
    void getAllBloodPressureSummariesByEmpresaIdIsEqualToSomething() throws Exception {
        // Initialize the database
        bloodPressureSummaryRepository.saveAndFlush(bloodPressureSummary);

        // Get all the bloodPressureSummaryList where empresaId equals to DEFAULT_EMPRESA_ID
        defaultBloodPressureSummaryShouldBeFound("empresaId.equals=" + DEFAULT_EMPRESA_ID);

        // Get all the bloodPressureSummaryList where empresaId equals to UPDATED_EMPRESA_ID
        defaultBloodPressureSummaryShouldNotBeFound("empresaId.equals=" + UPDATED_EMPRESA_ID);
    }

    @Test
    @Transactional
    void getAllBloodPressureSummariesByEmpresaIdIsInShouldWork() throws Exception {
        // Initialize the database
        bloodPressureSummaryRepository.saveAndFlush(bloodPressureSummary);

        // Get all the bloodPressureSummaryList where empresaId in DEFAULT_EMPRESA_ID or UPDATED_EMPRESA_ID
        defaultBloodPressureSummaryShouldBeFound("empresaId.in=" + DEFAULT_EMPRESA_ID + "," + UPDATED_EMPRESA_ID);

        // Get all the bloodPressureSummaryList where empresaId equals to UPDATED_EMPRESA_ID
        defaultBloodPressureSummaryShouldNotBeFound("empresaId.in=" + UPDATED_EMPRESA_ID);
    }

    @Test
    @Transactional
    void getAllBloodPressureSummariesByEmpresaIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        bloodPressureSummaryRepository.saveAndFlush(bloodPressureSummary);

        // Get all the bloodPressureSummaryList where empresaId is not null
        defaultBloodPressureSummaryShouldBeFound("empresaId.specified=true");

        // Get all the bloodPressureSummaryList where empresaId is null
        defaultBloodPressureSummaryShouldNotBeFound("empresaId.specified=false");
    }

    @Test
    @Transactional
    void getAllBloodPressureSummariesByEmpresaIdContainsSomething() throws Exception {
        // Initialize the database
        bloodPressureSummaryRepository.saveAndFlush(bloodPressureSummary);

        // Get all the bloodPressureSummaryList where empresaId contains DEFAULT_EMPRESA_ID
        defaultBloodPressureSummaryShouldBeFound("empresaId.contains=" + DEFAULT_EMPRESA_ID);

        // Get all the bloodPressureSummaryList where empresaId contains UPDATED_EMPRESA_ID
        defaultBloodPressureSummaryShouldNotBeFound("empresaId.contains=" + UPDATED_EMPRESA_ID);
    }

    @Test
    @Transactional
    void getAllBloodPressureSummariesByEmpresaIdNotContainsSomething() throws Exception {
        // Initialize the database
        bloodPressureSummaryRepository.saveAndFlush(bloodPressureSummary);

        // Get all the bloodPressureSummaryList where empresaId does not contain DEFAULT_EMPRESA_ID
        defaultBloodPressureSummaryShouldNotBeFound("empresaId.doesNotContain=" + DEFAULT_EMPRESA_ID);

        // Get all the bloodPressureSummaryList where empresaId does not contain UPDATED_EMPRESA_ID
        defaultBloodPressureSummaryShouldBeFound("empresaId.doesNotContain=" + UPDATED_EMPRESA_ID);
    }

    @Test
    @Transactional
    void getAllBloodPressureSummariesByFieldSistolicAverageIsEqualToSomething() throws Exception {
        // Initialize the database
        bloodPressureSummaryRepository.saveAndFlush(bloodPressureSummary);

        // Get all the bloodPressureSummaryList where fieldSistolicAverage equals to DEFAULT_FIELD_SISTOLIC_AVERAGE
        defaultBloodPressureSummaryShouldBeFound("fieldSistolicAverage.equals=" + DEFAULT_FIELD_SISTOLIC_AVERAGE);

        // Get all the bloodPressureSummaryList where fieldSistolicAverage equals to UPDATED_FIELD_SISTOLIC_AVERAGE
        defaultBloodPressureSummaryShouldNotBeFound("fieldSistolicAverage.equals=" + UPDATED_FIELD_SISTOLIC_AVERAGE);
    }

    @Test
    @Transactional
    void getAllBloodPressureSummariesByFieldSistolicAverageIsInShouldWork() throws Exception {
        // Initialize the database
        bloodPressureSummaryRepository.saveAndFlush(bloodPressureSummary);

        // Get all the bloodPressureSummaryList where fieldSistolicAverage in DEFAULT_FIELD_SISTOLIC_AVERAGE or UPDATED_FIELD_SISTOLIC_AVERAGE
        defaultBloodPressureSummaryShouldBeFound(
            "fieldSistolicAverage.in=" + DEFAULT_FIELD_SISTOLIC_AVERAGE + "," + UPDATED_FIELD_SISTOLIC_AVERAGE
        );

        // Get all the bloodPressureSummaryList where fieldSistolicAverage equals to UPDATED_FIELD_SISTOLIC_AVERAGE
        defaultBloodPressureSummaryShouldNotBeFound("fieldSistolicAverage.in=" + UPDATED_FIELD_SISTOLIC_AVERAGE);
    }

    @Test
    @Transactional
    void getAllBloodPressureSummariesByFieldSistolicAverageIsNullOrNotNull() throws Exception {
        // Initialize the database
        bloodPressureSummaryRepository.saveAndFlush(bloodPressureSummary);

        // Get all the bloodPressureSummaryList where fieldSistolicAverage is not null
        defaultBloodPressureSummaryShouldBeFound("fieldSistolicAverage.specified=true");

        // Get all the bloodPressureSummaryList where fieldSistolicAverage is null
        defaultBloodPressureSummaryShouldNotBeFound("fieldSistolicAverage.specified=false");
    }

    @Test
    @Transactional
    void getAllBloodPressureSummariesByFieldSistolicAverageIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        bloodPressureSummaryRepository.saveAndFlush(bloodPressureSummary);

        // Get all the bloodPressureSummaryList where fieldSistolicAverage is greater than or equal to DEFAULT_FIELD_SISTOLIC_AVERAGE
        defaultBloodPressureSummaryShouldBeFound("fieldSistolicAverage.greaterThanOrEqual=" + DEFAULT_FIELD_SISTOLIC_AVERAGE);

        // Get all the bloodPressureSummaryList where fieldSistolicAverage is greater than or equal to UPDATED_FIELD_SISTOLIC_AVERAGE
        defaultBloodPressureSummaryShouldNotBeFound("fieldSistolicAverage.greaterThanOrEqual=" + UPDATED_FIELD_SISTOLIC_AVERAGE);
    }

    @Test
    @Transactional
    void getAllBloodPressureSummariesByFieldSistolicAverageIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        bloodPressureSummaryRepository.saveAndFlush(bloodPressureSummary);

        // Get all the bloodPressureSummaryList where fieldSistolicAverage is less than or equal to DEFAULT_FIELD_SISTOLIC_AVERAGE
        defaultBloodPressureSummaryShouldBeFound("fieldSistolicAverage.lessThanOrEqual=" + DEFAULT_FIELD_SISTOLIC_AVERAGE);

        // Get all the bloodPressureSummaryList where fieldSistolicAverage is less than or equal to SMALLER_FIELD_SISTOLIC_AVERAGE
        defaultBloodPressureSummaryShouldNotBeFound("fieldSistolicAverage.lessThanOrEqual=" + SMALLER_FIELD_SISTOLIC_AVERAGE);
    }

    @Test
    @Transactional
    void getAllBloodPressureSummariesByFieldSistolicAverageIsLessThanSomething() throws Exception {
        // Initialize the database
        bloodPressureSummaryRepository.saveAndFlush(bloodPressureSummary);

        // Get all the bloodPressureSummaryList where fieldSistolicAverage is less than DEFAULT_FIELD_SISTOLIC_AVERAGE
        defaultBloodPressureSummaryShouldNotBeFound("fieldSistolicAverage.lessThan=" + DEFAULT_FIELD_SISTOLIC_AVERAGE);

        // Get all the bloodPressureSummaryList where fieldSistolicAverage is less than UPDATED_FIELD_SISTOLIC_AVERAGE
        defaultBloodPressureSummaryShouldBeFound("fieldSistolicAverage.lessThan=" + UPDATED_FIELD_SISTOLIC_AVERAGE);
    }

    @Test
    @Transactional
    void getAllBloodPressureSummariesByFieldSistolicAverageIsGreaterThanSomething() throws Exception {
        // Initialize the database
        bloodPressureSummaryRepository.saveAndFlush(bloodPressureSummary);

        // Get all the bloodPressureSummaryList where fieldSistolicAverage is greater than DEFAULT_FIELD_SISTOLIC_AVERAGE
        defaultBloodPressureSummaryShouldNotBeFound("fieldSistolicAverage.greaterThan=" + DEFAULT_FIELD_SISTOLIC_AVERAGE);

        // Get all the bloodPressureSummaryList where fieldSistolicAverage is greater than SMALLER_FIELD_SISTOLIC_AVERAGE
        defaultBloodPressureSummaryShouldBeFound("fieldSistolicAverage.greaterThan=" + SMALLER_FIELD_SISTOLIC_AVERAGE);
    }

    @Test
    @Transactional
    void getAllBloodPressureSummariesByFieldSistolicMaxIsEqualToSomething() throws Exception {
        // Initialize the database
        bloodPressureSummaryRepository.saveAndFlush(bloodPressureSummary);

        // Get all the bloodPressureSummaryList where fieldSistolicMax equals to DEFAULT_FIELD_SISTOLIC_MAX
        defaultBloodPressureSummaryShouldBeFound("fieldSistolicMax.equals=" + DEFAULT_FIELD_SISTOLIC_MAX);

        // Get all the bloodPressureSummaryList where fieldSistolicMax equals to UPDATED_FIELD_SISTOLIC_MAX
        defaultBloodPressureSummaryShouldNotBeFound("fieldSistolicMax.equals=" + UPDATED_FIELD_SISTOLIC_MAX);
    }

    @Test
    @Transactional
    void getAllBloodPressureSummariesByFieldSistolicMaxIsInShouldWork() throws Exception {
        // Initialize the database
        bloodPressureSummaryRepository.saveAndFlush(bloodPressureSummary);

        // Get all the bloodPressureSummaryList where fieldSistolicMax in DEFAULT_FIELD_SISTOLIC_MAX or UPDATED_FIELD_SISTOLIC_MAX
        defaultBloodPressureSummaryShouldBeFound("fieldSistolicMax.in=" + DEFAULT_FIELD_SISTOLIC_MAX + "," + UPDATED_FIELD_SISTOLIC_MAX);

        // Get all the bloodPressureSummaryList where fieldSistolicMax equals to UPDATED_FIELD_SISTOLIC_MAX
        defaultBloodPressureSummaryShouldNotBeFound("fieldSistolicMax.in=" + UPDATED_FIELD_SISTOLIC_MAX);
    }

    @Test
    @Transactional
    void getAllBloodPressureSummariesByFieldSistolicMaxIsNullOrNotNull() throws Exception {
        // Initialize the database
        bloodPressureSummaryRepository.saveAndFlush(bloodPressureSummary);

        // Get all the bloodPressureSummaryList where fieldSistolicMax is not null
        defaultBloodPressureSummaryShouldBeFound("fieldSistolicMax.specified=true");

        // Get all the bloodPressureSummaryList where fieldSistolicMax is null
        defaultBloodPressureSummaryShouldNotBeFound("fieldSistolicMax.specified=false");
    }

    @Test
    @Transactional
    void getAllBloodPressureSummariesByFieldSistolicMaxIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        bloodPressureSummaryRepository.saveAndFlush(bloodPressureSummary);

        // Get all the bloodPressureSummaryList where fieldSistolicMax is greater than or equal to DEFAULT_FIELD_SISTOLIC_MAX
        defaultBloodPressureSummaryShouldBeFound("fieldSistolicMax.greaterThanOrEqual=" + DEFAULT_FIELD_SISTOLIC_MAX);

        // Get all the bloodPressureSummaryList where fieldSistolicMax is greater than or equal to UPDATED_FIELD_SISTOLIC_MAX
        defaultBloodPressureSummaryShouldNotBeFound("fieldSistolicMax.greaterThanOrEqual=" + UPDATED_FIELD_SISTOLIC_MAX);
    }

    @Test
    @Transactional
    void getAllBloodPressureSummariesByFieldSistolicMaxIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        bloodPressureSummaryRepository.saveAndFlush(bloodPressureSummary);

        // Get all the bloodPressureSummaryList where fieldSistolicMax is less than or equal to DEFAULT_FIELD_SISTOLIC_MAX
        defaultBloodPressureSummaryShouldBeFound("fieldSistolicMax.lessThanOrEqual=" + DEFAULT_FIELD_SISTOLIC_MAX);

        // Get all the bloodPressureSummaryList where fieldSistolicMax is less than or equal to SMALLER_FIELD_SISTOLIC_MAX
        defaultBloodPressureSummaryShouldNotBeFound("fieldSistolicMax.lessThanOrEqual=" + SMALLER_FIELD_SISTOLIC_MAX);
    }

    @Test
    @Transactional
    void getAllBloodPressureSummariesByFieldSistolicMaxIsLessThanSomething() throws Exception {
        // Initialize the database
        bloodPressureSummaryRepository.saveAndFlush(bloodPressureSummary);

        // Get all the bloodPressureSummaryList where fieldSistolicMax is less than DEFAULT_FIELD_SISTOLIC_MAX
        defaultBloodPressureSummaryShouldNotBeFound("fieldSistolicMax.lessThan=" + DEFAULT_FIELD_SISTOLIC_MAX);

        // Get all the bloodPressureSummaryList where fieldSistolicMax is less than UPDATED_FIELD_SISTOLIC_MAX
        defaultBloodPressureSummaryShouldBeFound("fieldSistolicMax.lessThan=" + UPDATED_FIELD_SISTOLIC_MAX);
    }

    @Test
    @Transactional
    void getAllBloodPressureSummariesByFieldSistolicMaxIsGreaterThanSomething() throws Exception {
        // Initialize the database
        bloodPressureSummaryRepository.saveAndFlush(bloodPressureSummary);

        // Get all the bloodPressureSummaryList where fieldSistolicMax is greater than DEFAULT_FIELD_SISTOLIC_MAX
        defaultBloodPressureSummaryShouldNotBeFound("fieldSistolicMax.greaterThan=" + DEFAULT_FIELD_SISTOLIC_MAX);

        // Get all the bloodPressureSummaryList where fieldSistolicMax is greater than SMALLER_FIELD_SISTOLIC_MAX
        defaultBloodPressureSummaryShouldBeFound("fieldSistolicMax.greaterThan=" + SMALLER_FIELD_SISTOLIC_MAX);
    }

    @Test
    @Transactional
    void getAllBloodPressureSummariesByFieldSistolicMinIsEqualToSomething() throws Exception {
        // Initialize the database
        bloodPressureSummaryRepository.saveAndFlush(bloodPressureSummary);

        // Get all the bloodPressureSummaryList where fieldSistolicMin equals to DEFAULT_FIELD_SISTOLIC_MIN
        defaultBloodPressureSummaryShouldBeFound("fieldSistolicMin.equals=" + DEFAULT_FIELD_SISTOLIC_MIN);

        // Get all the bloodPressureSummaryList where fieldSistolicMin equals to UPDATED_FIELD_SISTOLIC_MIN
        defaultBloodPressureSummaryShouldNotBeFound("fieldSistolicMin.equals=" + UPDATED_FIELD_SISTOLIC_MIN);
    }

    @Test
    @Transactional
    void getAllBloodPressureSummariesByFieldSistolicMinIsInShouldWork() throws Exception {
        // Initialize the database
        bloodPressureSummaryRepository.saveAndFlush(bloodPressureSummary);

        // Get all the bloodPressureSummaryList where fieldSistolicMin in DEFAULT_FIELD_SISTOLIC_MIN or UPDATED_FIELD_SISTOLIC_MIN
        defaultBloodPressureSummaryShouldBeFound("fieldSistolicMin.in=" + DEFAULT_FIELD_SISTOLIC_MIN + "," + UPDATED_FIELD_SISTOLIC_MIN);

        // Get all the bloodPressureSummaryList where fieldSistolicMin equals to UPDATED_FIELD_SISTOLIC_MIN
        defaultBloodPressureSummaryShouldNotBeFound("fieldSistolicMin.in=" + UPDATED_FIELD_SISTOLIC_MIN);
    }

    @Test
    @Transactional
    void getAllBloodPressureSummariesByFieldSistolicMinIsNullOrNotNull() throws Exception {
        // Initialize the database
        bloodPressureSummaryRepository.saveAndFlush(bloodPressureSummary);

        // Get all the bloodPressureSummaryList where fieldSistolicMin is not null
        defaultBloodPressureSummaryShouldBeFound("fieldSistolicMin.specified=true");

        // Get all the bloodPressureSummaryList where fieldSistolicMin is null
        defaultBloodPressureSummaryShouldNotBeFound("fieldSistolicMin.specified=false");
    }

    @Test
    @Transactional
    void getAllBloodPressureSummariesByFieldSistolicMinIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        bloodPressureSummaryRepository.saveAndFlush(bloodPressureSummary);

        // Get all the bloodPressureSummaryList where fieldSistolicMin is greater than or equal to DEFAULT_FIELD_SISTOLIC_MIN
        defaultBloodPressureSummaryShouldBeFound("fieldSistolicMin.greaterThanOrEqual=" + DEFAULT_FIELD_SISTOLIC_MIN);

        // Get all the bloodPressureSummaryList where fieldSistolicMin is greater than or equal to UPDATED_FIELD_SISTOLIC_MIN
        defaultBloodPressureSummaryShouldNotBeFound("fieldSistolicMin.greaterThanOrEqual=" + UPDATED_FIELD_SISTOLIC_MIN);
    }

    @Test
    @Transactional
    void getAllBloodPressureSummariesByFieldSistolicMinIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        bloodPressureSummaryRepository.saveAndFlush(bloodPressureSummary);

        // Get all the bloodPressureSummaryList where fieldSistolicMin is less than or equal to DEFAULT_FIELD_SISTOLIC_MIN
        defaultBloodPressureSummaryShouldBeFound("fieldSistolicMin.lessThanOrEqual=" + DEFAULT_FIELD_SISTOLIC_MIN);

        // Get all the bloodPressureSummaryList where fieldSistolicMin is less than or equal to SMALLER_FIELD_SISTOLIC_MIN
        defaultBloodPressureSummaryShouldNotBeFound("fieldSistolicMin.lessThanOrEqual=" + SMALLER_FIELD_SISTOLIC_MIN);
    }

    @Test
    @Transactional
    void getAllBloodPressureSummariesByFieldSistolicMinIsLessThanSomething() throws Exception {
        // Initialize the database
        bloodPressureSummaryRepository.saveAndFlush(bloodPressureSummary);

        // Get all the bloodPressureSummaryList where fieldSistolicMin is less than DEFAULT_FIELD_SISTOLIC_MIN
        defaultBloodPressureSummaryShouldNotBeFound("fieldSistolicMin.lessThan=" + DEFAULT_FIELD_SISTOLIC_MIN);

        // Get all the bloodPressureSummaryList where fieldSistolicMin is less than UPDATED_FIELD_SISTOLIC_MIN
        defaultBloodPressureSummaryShouldBeFound("fieldSistolicMin.lessThan=" + UPDATED_FIELD_SISTOLIC_MIN);
    }

    @Test
    @Transactional
    void getAllBloodPressureSummariesByFieldSistolicMinIsGreaterThanSomething() throws Exception {
        // Initialize the database
        bloodPressureSummaryRepository.saveAndFlush(bloodPressureSummary);

        // Get all the bloodPressureSummaryList where fieldSistolicMin is greater than DEFAULT_FIELD_SISTOLIC_MIN
        defaultBloodPressureSummaryShouldNotBeFound("fieldSistolicMin.greaterThan=" + DEFAULT_FIELD_SISTOLIC_MIN);

        // Get all the bloodPressureSummaryList where fieldSistolicMin is greater than SMALLER_FIELD_SISTOLIC_MIN
        defaultBloodPressureSummaryShouldBeFound("fieldSistolicMin.greaterThan=" + SMALLER_FIELD_SISTOLIC_MIN);
    }

    @Test
    @Transactional
    void getAllBloodPressureSummariesByFieldDiasatolicAverageIsEqualToSomething() throws Exception {
        // Initialize the database
        bloodPressureSummaryRepository.saveAndFlush(bloodPressureSummary);

        // Get all the bloodPressureSummaryList where fieldDiasatolicAverage equals to DEFAULT_FIELD_DIASATOLIC_AVERAGE
        defaultBloodPressureSummaryShouldBeFound("fieldDiasatolicAverage.equals=" + DEFAULT_FIELD_DIASATOLIC_AVERAGE);

        // Get all the bloodPressureSummaryList where fieldDiasatolicAverage equals to UPDATED_FIELD_DIASATOLIC_AVERAGE
        defaultBloodPressureSummaryShouldNotBeFound("fieldDiasatolicAverage.equals=" + UPDATED_FIELD_DIASATOLIC_AVERAGE);
    }

    @Test
    @Transactional
    void getAllBloodPressureSummariesByFieldDiasatolicAverageIsInShouldWork() throws Exception {
        // Initialize the database
        bloodPressureSummaryRepository.saveAndFlush(bloodPressureSummary);

        // Get all the bloodPressureSummaryList where fieldDiasatolicAverage in DEFAULT_FIELD_DIASATOLIC_AVERAGE or UPDATED_FIELD_DIASATOLIC_AVERAGE
        defaultBloodPressureSummaryShouldBeFound(
            "fieldDiasatolicAverage.in=" + DEFAULT_FIELD_DIASATOLIC_AVERAGE + "," + UPDATED_FIELD_DIASATOLIC_AVERAGE
        );

        // Get all the bloodPressureSummaryList where fieldDiasatolicAverage equals to UPDATED_FIELD_DIASATOLIC_AVERAGE
        defaultBloodPressureSummaryShouldNotBeFound("fieldDiasatolicAverage.in=" + UPDATED_FIELD_DIASATOLIC_AVERAGE);
    }

    @Test
    @Transactional
    void getAllBloodPressureSummariesByFieldDiasatolicAverageIsNullOrNotNull() throws Exception {
        // Initialize the database
        bloodPressureSummaryRepository.saveAndFlush(bloodPressureSummary);

        // Get all the bloodPressureSummaryList where fieldDiasatolicAverage is not null
        defaultBloodPressureSummaryShouldBeFound("fieldDiasatolicAverage.specified=true");

        // Get all the bloodPressureSummaryList where fieldDiasatolicAverage is null
        defaultBloodPressureSummaryShouldNotBeFound("fieldDiasatolicAverage.specified=false");
    }

    @Test
    @Transactional
    void getAllBloodPressureSummariesByFieldDiasatolicAverageIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        bloodPressureSummaryRepository.saveAndFlush(bloodPressureSummary);

        // Get all the bloodPressureSummaryList where fieldDiasatolicAverage is greater than or equal to DEFAULT_FIELD_DIASATOLIC_AVERAGE
        defaultBloodPressureSummaryShouldBeFound("fieldDiasatolicAverage.greaterThanOrEqual=" + DEFAULT_FIELD_DIASATOLIC_AVERAGE);

        // Get all the bloodPressureSummaryList where fieldDiasatolicAverage is greater than or equal to UPDATED_FIELD_DIASATOLIC_AVERAGE
        defaultBloodPressureSummaryShouldNotBeFound("fieldDiasatolicAverage.greaterThanOrEqual=" + UPDATED_FIELD_DIASATOLIC_AVERAGE);
    }

    @Test
    @Transactional
    void getAllBloodPressureSummariesByFieldDiasatolicAverageIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        bloodPressureSummaryRepository.saveAndFlush(bloodPressureSummary);

        // Get all the bloodPressureSummaryList where fieldDiasatolicAverage is less than or equal to DEFAULT_FIELD_DIASATOLIC_AVERAGE
        defaultBloodPressureSummaryShouldBeFound("fieldDiasatolicAverage.lessThanOrEqual=" + DEFAULT_FIELD_DIASATOLIC_AVERAGE);

        // Get all the bloodPressureSummaryList where fieldDiasatolicAverage is less than or equal to SMALLER_FIELD_DIASATOLIC_AVERAGE
        defaultBloodPressureSummaryShouldNotBeFound("fieldDiasatolicAverage.lessThanOrEqual=" + SMALLER_FIELD_DIASATOLIC_AVERAGE);
    }

    @Test
    @Transactional
    void getAllBloodPressureSummariesByFieldDiasatolicAverageIsLessThanSomething() throws Exception {
        // Initialize the database
        bloodPressureSummaryRepository.saveAndFlush(bloodPressureSummary);

        // Get all the bloodPressureSummaryList where fieldDiasatolicAverage is less than DEFAULT_FIELD_DIASATOLIC_AVERAGE
        defaultBloodPressureSummaryShouldNotBeFound("fieldDiasatolicAverage.lessThan=" + DEFAULT_FIELD_DIASATOLIC_AVERAGE);

        // Get all the bloodPressureSummaryList where fieldDiasatolicAverage is less than UPDATED_FIELD_DIASATOLIC_AVERAGE
        defaultBloodPressureSummaryShouldBeFound("fieldDiasatolicAverage.lessThan=" + UPDATED_FIELD_DIASATOLIC_AVERAGE);
    }

    @Test
    @Transactional
    void getAllBloodPressureSummariesByFieldDiasatolicAverageIsGreaterThanSomething() throws Exception {
        // Initialize the database
        bloodPressureSummaryRepository.saveAndFlush(bloodPressureSummary);

        // Get all the bloodPressureSummaryList where fieldDiasatolicAverage is greater than DEFAULT_FIELD_DIASATOLIC_AVERAGE
        defaultBloodPressureSummaryShouldNotBeFound("fieldDiasatolicAverage.greaterThan=" + DEFAULT_FIELD_DIASATOLIC_AVERAGE);

        // Get all the bloodPressureSummaryList where fieldDiasatolicAverage is greater than SMALLER_FIELD_DIASATOLIC_AVERAGE
        defaultBloodPressureSummaryShouldBeFound("fieldDiasatolicAverage.greaterThan=" + SMALLER_FIELD_DIASATOLIC_AVERAGE);
    }

    @Test
    @Transactional
    void getAllBloodPressureSummariesByFieldDiastolicMaxIsEqualToSomething() throws Exception {
        // Initialize the database
        bloodPressureSummaryRepository.saveAndFlush(bloodPressureSummary);

        // Get all the bloodPressureSummaryList where fieldDiastolicMax equals to DEFAULT_FIELD_DIASTOLIC_MAX
        defaultBloodPressureSummaryShouldBeFound("fieldDiastolicMax.equals=" + DEFAULT_FIELD_DIASTOLIC_MAX);

        // Get all the bloodPressureSummaryList where fieldDiastolicMax equals to UPDATED_FIELD_DIASTOLIC_MAX
        defaultBloodPressureSummaryShouldNotBeFound("fieldDiastolicMax.equals=" + UPDATED_FIELD_DIASTOLIC_MAX);
    }

    @Test
    @Transactional
    void getAllBloodPressureSummariesByFieldDiastolicMaxIsInShouldWork() throws Exception {
        // Initialize the database
        bloodPressureSummaryRepository.saveAndFlush(bloodPressureSummary);

        // Get all the bloodPressureSummaryList where fieldDiastolicMax in DEFAULT_FIELD_DIASTOLIC_MAX or UPDATED_FIELD_DIASTOLIC_MAX
        defaultBloodPressureSummaryShouldBeFound("fieldDiastolicMax.in=" + DEFAULT_FIELD_DIASTOLIC_MAX + "," + UPDATED_FIELD_DIASTOLIC_MAX);

        // Get all the bloodPressureSummaryList where fieldDiastolicMax equals to UPDATED_FIELD_DIASTOLIC_MAX
        defaultBloodPressureSummaryShouldNotBeFound("fieldDiastolicMax.in=" + UPDATED_FIELD_DIASTOLIC_MAX);
    }

    @Test
    @Transactional
    void getAllBloodPressureSummariesByFieldDiastolicMaxIsNullOrNotNull() throws Exception {
        // Initialize the database
        bloodPressureSummaryRepository.saveAndFlush(bloodPressureSummary);

        // Get all the bloodPressureSummaryList where fieldDiastolicMax is not null
        defaultBloodPressureSummaryShouldBeFound("fieldDiastolicMax.specified=true");

        // Get all the bloodPressureSummaryList where fieldDiastolicMax is null
        defaultBloodPressureSummaryShouldNotBeFound("fieldDiastolicMax.specified=false");
    }

    @Test
    @Transactional
    void getAllBloodPressureSummariesByFieldDiastolicMaxIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        bloodPressureSummaryRepository.saveAndFlush(bloodPressureSummary);

        // Get all the bloodPressureSummaryList where fieldDiastolicMax is greater than or equal to DEFAULT_FIELD_DIASTOLIC_MAX
        defaultBloodPressureSummaryShouldBeFound("fieldDiastolicMax.greaterThanOrEqual=" + DEFAULT_FIELD_DIASTOLIC_MAX);

        // Get all the bloodPressureSummaryList where fieldDiastolicMax is greater than or equal to UPDATED_FIELD_DIASTOLIC_MAX
        defaultBloodPressureSummaryShouldNotBeFound("fieldDiastolicMax.greaterThanOrEqual=" + UPDATED_FIELD_DIASTOLIC_MAX);
    }

    @Test
    @Transactional
    void getAllBloodPressureSummariesByFieldDiastolicMaxIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        bloodPressureSummaryRepository.saveAndFlush(bloodPressureSummary);

        // Get all the bloodPressureSummaryList where fieldDiastolicMax is less than or equal to DEFAULT_FIELD_DIASTOLIC_MAX
        defaultBloodPressureSummaryShouldBeFound("fieldDiastolicMax.lessThanOrEqual=" + DEFAULT_FIELD_DIASTOLIC_MAX);

        // Get all the bloodPressureSummaryList where fieldDiastolicMax is less than or equal to SMALLER_FIELD_DIASTOLIC_MAX
        defaultBloodPressureSummaryShouldNotBeFound("fieldDiastolicMax.lessThanOrEqual=" + SMALLER_FIELD_DIASTOLIC_MAX);
    }

    @Test
    @Transactional
    void getAllBloodPressureSummariesByFieldDiastolicMaxIsLessThanSomething() throws Exception {
        // Initialize the database
        bloodPressureSummaryRepository.saveAndFlush(bloodPressureSummary);

        // Get all the bloodPressureSummaryList where fieldDiastolicMax is less than DEFAULT_FIELD_DIASTOLIC_MAX
        defaultBloodPressureSummaryShouldNotBeFound("fieldDiastolicMax.lessThan=" + DEFAULT_FIELD_DIASTOLIC_MAX);

        // Get all the bloodPressureSummaryList where fieldDiastolicMax is less than UPDATED_FIELD_DIASTOLIC_MAX
        defaultBloodPressureSummaryShouldBeFound("fieldDiastolicMax.lessThan=" + UPDATED_FIELD_DIASTOLIC_MAX);
    }

    @Test
    @Transactional
    void getAllBloodPressureSummariesByFieldDiastolicMaxIsGreaterThanSomething() throws Exception {
        // Initialize the database
        bloodPressureSummaryRepository.saveAndFlush(bloodPressureSummary);

        // Get all the bloodPressureSummaryList where fieldDiastolicMax is greater than DEFAULT_FIELD_DIASTOLIC_MAX
        defaultBloodPressureSummaryShouldNotBeFound("fieldDiastolicMax.greaterThan=" + DEFAULT_FIELD_DIASTOLIC_MAX);

        // Get all the bloodPressureSummaryList where fieldDiastolicMax is greater than SMALLER_FIELD_DIASTOLIC_MAX
        defaultBloodPressureSummaryShouldBeFound("fieldDiastolicMax.greaterThan=" + SMALLER_FIELD_DIASTOLIC_MAX);
    }

    @Test
    @Transactional
    void getAllBloodPressureSummariesByFieldDiastolicMinIsEqualToSomething() throws Exception {
        // Initialize the database
        bloodPressureSummaryRepository.saveAndFlush(bloodPressureSummary);

        // Get all the bloodPressureSummaryList where fieldDiastolicMin equals to DEFAULT_FIELD_DIASTOLIC_MIN
        defaultBloodPressureSummaryShouldBeFound("fieldDiastolicMin.equals=" + DEFAULT_FIELD_DIASTOLIC_MIN);

        // Get all the bloodPressureSummaryList where fieldDiastolicMin equals to UPDATED_FIELD_DIASTOLIC_MIN
        defaultBloodPressureSummaryShouldNotBeFound("fieldDiastolicMin.equals=" + UPDATED_FIELD_DIASTOLIC_MIN);
    }

    @Test
    @Transactional
    void getAllBloodPressureSummariesByFieldDiastolicMinIsInShouldWork() throws Exception {
        // Initialize the database
        bloodPressureSummaryRepository.saveAndFlush(bloodPressureSummary);

        // Get all the bloodPressureSummaryList where fieldDiastolicMin in DEFAULT_FIELD_DIASTOLIC_MIN or UPDATED_FIELD_DIASTOLIC_MIN
        defaultBloodPressureSummaryShouldBeFound("fieldDiastolicMin.in=" + DEFAULT_FIELD_DIASTOLIC_MIN + "," + UPDATED_FIELD_DIASTOLIC_MIN);

        // Get all the bloodPressureSummaryList where fieldDiastolicMin equals to UPDATED_FIELD_DIASTOLIC_MIN
        defaultBloodPressureSummaryShouldNotBeFound("fieldDiastolicMin.in=" + UPDATED_FIELD_DIASTOLIC_MIN);
    }

    @Test
    @Transactional
    void getAllBloodPressureSummariesByFieldDiastolicMinIsNullOrNotNull() throws Exception {
        // Initialize the database
        bloodPressureSummaryRepository.saveAndFlush(bloodPressureSummary);

        // Get all the bloodPressureSummaryList where fieldDiastolicMin is not null
        defaultBloodPressureSummaryShouldBeFound("fieldDiastolicMin.specified=true");

        // Get all the bloodPressureSummaryList where fieldDiastolicMin is null
        defaultBloodPressureSummaryShouldNotBeFound("fieldDiastolicMin.specified=false");
    }

    @Test
    @Transactional
    void getAllBloodPressureSummariesByFieldDiastolicMinIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        bloodPressureSummaryRepository.saveAndFlush(bloodPressureSummary);

        // Get all the bloodPressureSummaryList where fieldDiastolicMin is greater than or equal to DEFAULT_FIELD_DIASTOLIC_MIN
        defaultBloodPressureSummaryShouldBeFound("fieldDiastolicMin.greaterThanOrEqual=" + DEFAULT_FIELD_DIASTOLIC_MIN);

        // Get all the bloodPressureSummaryList where fieldDiastolicMin is greater than or equal to UPDATED_FIELD_DIASTOLIC_MIN
        defaultBloodPressureSummaryShouldNotBeFound("fieldDiastolicMin.greaterThanOrEqual=" + UPDATED_FIELD_DIASTOLIC_MIN);
    }

    @Test
    @Transactional
    void getAllBloodPressureSummariesByFieldDiastolicMinIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        bloodPressureSummaryRepository.saveAndFlush(bloodPressureSummary);

        // Get all the bloodPressureSummaryList where fieldDiastolicMin is less than or equal to DEFAULT_FIELD_DIASTOLIC_MIN
        defaultBloodPressureSummaryShouldBeFound("fieldDiastolicMin.lessThanOrEqual=" + DEFAULT_FIELD_DIASTOLIC_MIN);

        // Get all the bloodPressureSummaryList where fieldDiastolicMin is less than or equal to SMALLER_FIELD_DIASTOLIC_MIN
        defaultBloodPressureSummaryShouldNotBeFound("fieldDiastolicMin.lessThanOrEqual=" + SMALLER_FIELD_DIASTOLIC_MIN);
    }

    @Test
    @Transactional
    void getAllBloodPressureSummariesByFieldDiastolicMinIsLessThanSomething() throws Exception {
        // Initialize the database
        bloodPressureSummaryRepository.saveAndFlush(bloodPressureSummary);

        // Get all the bloodPressureSummaryList where fieldDiastolicMin is less than DEFAULT_FIELD_DIASTOLIC_MIN
        defaultBloodPressureSummaryShouldNotBeFound("fieldDiastolicMin.lessThan=" + DEFAULT_FIELD_DIASTOLIC_MIN);

        // Get all the bloodPressureSummaryList where fieldDiastolicMin is less than UPDATED_FIELD_DIASTOLIC_MIN
        defaultBloodPressureSummaryShouldBeFound("fieldDiastolicMin.lessThan=" + UPDATED_FIELD_DIASTOLIC_MIN);
    }

    @Test
    @Transactional
    void getAllBloodPressureSummariesByFieldDiastolicMinIsGreaterThanSomething() throws Exception {
        // Initialize the database
        bloodPressureSummaryRepository.saveAndFlush(bloodPressureSummary);

        // Get all the bloodPressureSummaryList where fieldDiastolicMin is greater than DEFAULT_FIELD_DIASTOLIC_MIN
        defaultBloodPressureSummaryShouldNotBeFound("fieldDiastolicMin.greaterThan=" + DEFAULT_FIELD_DIASTOLIC_MIN);

        // Get all the bloodPressureSummaryList where fieldDiastolicMin is greater than SMALLER_FIELD_DIASTOLIC_MIN
        defaultBloodPressureSummaryShouldBeFound("fieldDiastolicMin.greaterThan=" + SMALLER_FIELD_DIASTOLIC_MIN);
    }

    @Test
    @Transactional
    void getAllBloodPressureSummariesByBodyPositionIsEqualToSomething() throws Exception {
        // Initialize the database
        bloodPressureSummaryRepository.saveAndFlush(bloodPressureSummary);

        // Get all the bloodPressureSummaryList where bodyPosition equals to DEFAULT_BODY_POSITION
        defaultBloodPressureSummaryShouldBeFound("bodyPosition.equals=" + DEFAULT_BODY_POSITION);

        // Get all the bloodPressureSummaryList where bodyPosition equals to UPDATED_BODY_POSITION
        defaultBloodPressureSummaryShouldNotBeFound("bodyPosition.equals=" + UPDATED_BODY_POSITION);
    }

    @Test
    @Transactional
    void getAllBloodPressureSummariesByBodyPositionIsInShouldWork() throws Exception {
        // Initialize the database
        bloodPressureSummaryRepository.saveAndFlush(bloodPressureSummary);

        // Get all the bloodPressureSummaryList where bodyPosition in DEFAULT_BODY_POSITION or UPDATED_BODY_POSITION
        defaultBloodPressureSummaryShouldBeFound("bodyPosition.in=" + DEFAULT_BODY_POSITION + "," + UPDATED_BODY_POSITION);

        // Get all the bloodPressureSummaryList where bodyPosition equals to UPDATED_BODY_POSITION
        defaultBloodPressureSummaryShouldNotBeFound("bodyPosition.in=" + UPDATED_BODY_POSITION);
    }

    @Test
    @Transactional
    void getAllBloodPressureSummariesByBodyPositionIsNullOrNotNull() throws Exception {
        // Initialize the database
        bloodPressureSummaryRepository.saveAndFlush(bloodPressureSummary);

        // Get all the bloodPressureSummaryList where bodyPosition is not null
        defaultBloodPressureSummaryShouldBeFound("bodyPosition.specified=true");

        // Get all the bloodPressureSummaryList where bodyPosition is null
        defaultBloodPressureSummaryShouldNotBeFound("bodyPosition.specified=false");
    }

    @Test
    @Transactional
    void getAllBloodPressureSummariesByBodyPositionIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        bloodPressureSummaryRepository.saveAndFlush(bloodPressureSummary);

        // Get all the bloodPressureSummaryList where bodyPosition is greater than or equal to DEFAULT_BODY_POSITION
        defaultBloodPressureSummaryShouldBeFound("bodyPosition.greaterThanOrEqual=" + DEFAULT_BODY_POSITION);

        // Get all the bloodPressureSummaryList where bodyPosition is greater than or equal to UPDATED_BODY_POSITION
        defaultBloodPressureSummaryShouldNotBeFound("bodyPosition.greaterThanOrEqual=" + UPDATED_BODY_POSITION);
    }

    @Test
    @Transactional
    void getAllBloodPressureSummariesByBodyPositionIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        bloodPressureSummaryRepository.saveAndFlush(bloodPressureSummary);

        // Get all the bloodPressureSummaryList where bodyPosition is less than or equal to DEFAULT_BODY_POSITION
        defaultBloodPressureSummaryShouldBeFound("bodyPosition.lessThanOrEqual=" + DEFAULT_BODY_POSITION);

        // Get all the bloodPressureSummaryList where bodyPosition is less than or equal to SMALLER_BODY_POSITION
        defaultBloodPressureSummaryShouldNotBeFound("bodyPosition.lessThanOrEqual=" + SMALLER_BODY_POSITION);
    }

    @Test
    @Transactional
    void getAllBloodPressureSummariesByBodyPositionIsLessThanSomething() throws Exception {
        // Initialize the database
        bloodPressureSummaryRepository.saveAndFlush(bloodPressureSummary);

        // Get all the bloodPressureSummaryList where bodyPosition is less than DEFAULT_BODY_POSITION
        defaultBloodPressureSummaryShouldNotBeFound("bodyPosition.lessThan=" + DEFAULT_BODY_POSITION);

        // Get all the bloodPressureSummaryList where bodyPosition is less than UPDATED_BODY_POSITION
        defaultBloodPressureSummaryShouldBeFound("bodyPosition.lessThan=" + UPDATED_BODY_POSITION);
    }

    @Test
    @Transactional
    void getAllBloodPressureSummariesByBodyPositionIsGreaterThanSomething() throws Exception {
        // Initialize the database
        bloodPressureSummaryRepository.saveAndFlush(bloodPressureSummary);

        // Get all the bloodPressureSummaryList where bodyPosition is greater than DEFAULT_BODY_POSITION
        defaultBloodPressureSummaryShouldNotBeFound("bodyPosition.greaterThan=" + DEFAULT_BODY_POSITION);

        // Get all the bloodPressureSummaryList where bodyPosition is greater than SMALLER_BODY_POSITION
        defaultBloodPressureSummaryShouldBeFound("bodyPosition.greaterThan=" + SMALLER_BODY_POSITION);
    }

    @Test
    @Transactional
    void getAllBloodPressureSummariesByMeasurementLocationIsEqualToSomething() throws Exception {
        // Initialize the database
        bloodPressureSummaryRepository.saveAndFlush(bloodPressureSummary);

        // Get all the bloodPressureSummaryList where measurementLocation equals to DEFAULT_MEASUREMENT_LOCATION
        defaultBloodPressureSummaryShouldBeFound("measurementLocation.equals=" + DEFAULT_MEASUREMENT_LOCATION);

        // Get all the bloodPressureSummaryList where measurementLocation equals to UPDATED_MEASUREMENT_LOCATION
        defaultBloodPressureSummaryShouldNotBeFound("measurementLocation.equals=" + UPDATED_MEASUREMENT_LOCATION);
    }

    @Test
    @Transactional
    void getAllBloodPressureSummariesByMeasurementLocationIsInShouldWork() throws Exception {
        // Initialize the database
        bloodPressureSummaryRepository.saveAndFlush(bloodPressureSummary);

        // Get all the bloodPressureSummaryList where measurementLocation in DEFAULT_MEASUREMENT_LOCATION or UPDATED_MEASUREMENT_LOCATION
        defaultBloodPressureSummaryShouldBeFound(
            "measurementLocation.in=" + DEFAULT_MEASUREMENT_LOCATION + "," + UPDATED_MEASUREMENT_LOCATION
        );

        // Get all the bloodPressureSummaryList where measurementLocation equals to UPDATED_MEASUREMENT_LOCATION
        defaultBloodPressureSummaryShouldNotBeFound("measurementLocation.in=" + UPDATED_MEASUREMENT_LOCATION);
    }

    @Test
    @Transactional
    void getAllBloodPressureSummariesByMeasurementLocationIsNullOrNotNull() throws Exception {
        // Initialize the database
        bloodPressureSummaryRepository.saveAndFlush(bloodPressureSummary);

        // Get all the bloodPressureSummaryList where measurementLocation is not null
        defaultBloodPressureSummaryShouldBeFound("measurementLocation.specified=true");

        // Get all the bloodPressureSummaryList where measurementLocation is null
        defaultBloodPressureSummaryShouldNotBeFound("measurementLocation.specified=false");
    }

    @Test
    @Transactional
    void getAllBloodPressureSummariesByMeasurementLocationIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        bloodPressureSummaryRepository.saveAndFlush(bloodPressureSummary);

        // Get all the bloodPressureSummaryList where measurementLocation is greater than or equal to DEFAULT_MEASUREMENT_LOCATION
        defaultBloodPressureSummaryShouldBeFound("measurementLocation.greaterThanOrEqual=" + DEFAULT_MEASUREMENT_LOCATION);

        // Get all the bloodPressureSummaryList where measurementLocation is greater than or equal to UPDATED_MEASUREMENT_LOCATION
        defaultBloodPressureSummaryShouldNotBeFound("measurementLocation.greaterThanOrEqual=" + UPDATED_MEASUREMENT_LOCATION);
    }

    @Test
    @Transactional
    void getAllBloodPressureSummariesByMeasurementLocationIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        bloodPressureSummaryRepository.saveAndFlush(bloodPressureSummary);

        // Get all the bloodPressureSummaryList where measurementLocation is less than or equal to DEFAULT_MEASUREMENT_LOCATION
        defaultBloodPressureSummaryShouldBeFound("measurementLocation.lessThanOrEqual=" + DEFAULT_MEASUREMENT_LOCATION);

        // Get all the bloodPressureSummaryList where measurementLocation is less than or equal to SMALLER_MEASUREMENT_LOCATION
        defaultBloodPressureSummaryShouldNotBeFound("measurementLocation.lessThanOrEqual=" + SMALLER_MEASUREMENT_LOCATION);
    }

    @Test
    @Transactional
    void getAllBloodPressureSummariesByMeasurementLocationIsLessThanSomething() throws Exception {
        // Initialize the database
        bloodPressureSummaryRepository.saveAndFlush(bloodPressureSummary);

        // Get all the bloodPressureSummaryList where measurementLocation is less than DEFAULT_MEASUREMENT_LOCATION
        defaultBloodPressureSummaryShouldNotBeFound("measurementLocation.lessThan=" + DEFAULT_MEASUREMENT_LOCATION);

        // Get all the bloodPressureSummaryList where measurementLocation is less than UPDATED_MEASUREMENT_LOCATION
        defaultBloodPressureSummaryShouldBeFound("measurementLocation.lessThan=" + UPDATED_MEASUREMENT_LOCATION);
    }

    @Test
    @Transactional
    void getAllBloodPressureSummariesByMeasurementLocationIsGreaterThanSomething() throws Exception {
        // Initialize the database
        bloodPressureSummaryRepository.saveAndFlush(bloodPressureSummary);

        // Get all the bloodPressureSummaryList where measurementLocation is greater than DEFAULT_MEASUREMENT_LOCATION
        defaultBloodPressureSummaryShouldNotBeFound("measurementLocation.greaterThan=" + DEFAULT_MEASUREMENT_LOCATION);

        // Get all the bloodPressureSummaryList where measurementLocation is greater than SMALLER_MEASUREMENT_LOCATION
        defaultBloodPressureSummaryShouldBeFound("measurementLocation.greaterThan=" + SMALLER_MEASUREMENT_LOCATION);
    }

    @Test
    @Transactional
    void getAllBloodPressureSummariesByStartTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        bloodPressureSummaryRepository.saveAndFlush(bloodPressureSummary);

        // Get all the bloodPressureSummaryList where startTime equals to DEFAULT_START_TIME
        defaultBloodPressureSummaryShouldBeFound("startTime.equals=" + DEFAULT_START_TIME);

        // Get all the bloodPressureSummaryList where startTime equals to UPDATED_START_TIME
        defaultBloodPressureSummaryShouldNotBeFound("startTime.equals=" + UPDATED_START_TIME);
    }

    @Test
    @Transactional
    void getAllBloodPressureSummariesByStartTimeIsInShouldWork() throws Exception {
        // Initialize the database
        bloodPressureSummaryRepository.saveAndFlush(bloodPressureSummary);

        // Get all the bloodPressureSummaryList where startTime in DEFAULT_START_TIME or UPDATED_START_TIME
        defaultBloodPressureSummaryShouldBeFound("startTime.in=" + DEFAULT_START_TIME + "," + UPDATED_START_TIME);

        // Get all the bloodPressureSummaryList where startTime equals to UPDATED_START_TIME
        defaultBloodPressureSummaryShouldNotBeFound("startTime.in=" + UPDATED_START_TIME);
    }

    @Test
    @Transactional
    void getAllBloodPressureSummariesByStartTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        bloodPressureSummaryRepository.saveAndFlush(bloodPressureSummary);

        // Get all the bloodPressureSummaryList where startTime is not null
        defaultBloodPressureSummaryShouldBeFound("startTime.specified=true");

        // Get all the bloodPressureSummaryList where startTime is null
        defaultBloodPressureSummaryShouldNotBeFound("startTime.specified=false");
    }

    @Test
    @Transactional
    void getAllBloodPressureSummariesByEndTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        bloodPressureSummaryRepository.saveAndFlush(bloodPressureSummary);

        // Get all the bloodPressureSummaryList where endTime equals to DEFAULT_END_TIME
        defaultBloodPressureSummaryShouldBeFound("endTime.equals=" + DEFAULT_END_TIME);

        // Get all the bloodPressureSummaryList where endTime equals to UPDATED_END_TIME
        defaultBloodPressureSummaryShouldNotBeFound("endTime.equals=" + UPDATED_END_TIME);
    }

    @Test
    @Transactional
    void getAllBloodPressureSummariesByEndTimeIsInShouldWork() throws Exception {
        // Initialize the database
        bloodPressureSummaryRepository.saveAndFlush(bloodPressureSummary);

        // Get all the bloodPressureSummaryList where endTime in DEFAULT_END_TIME or UPDATED_END_TIME
        defaultBloodPressureSummaryShouldBeFound("endTime.in=" + DEFAULT_END_TIME + "," + UPDATED_END_TIME);

        // Get all the bloodPressureSummaryList where endTime equals to UPDATED_END_TIME
        defaultBloodPressureSummaryShouldNotBeFound("endTime.in=" + UPDATED_END_TIME);
    }

    @Test
    @Transactional
    void getAllBloodPressureSummariesByEndTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        bloodPressureSummaryRepository.saveAndFlush(bloodPressureSummary);

        // Get all the bloodPressureSummaryList where endTime is not null
        defaultBloodPressureSummaryShouldBeFound("endTime.specified=true");

        // Get all the bloodPressureSummaryList where endTime is null
        defaultBloodPressureSummaryShouldNotBeFound("endTime.specified=false");
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultBloodPressureSummaryShouldBeFound(String filter) throws Exception {
        restBloodPressureSummaryMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bloodPressureSummary.getId().toString())))
            .andExpect(jsonPath("$.[*].usuarioId").value(hasItem(DEFAULT_USUARIO_ID)))
            .andExpect(jsonPath("$.[*].empresaId").value(hasItem(DEFAULT_EMPRESA_ID)))
            .andExpect(jsonPath("$.[*].fieldSistolicAverage").value(hasItem(DEFAULT_FIELD_SISTOLIC_AVERAGE.doubleValue())))
            .andExpect(jsonPath("$.[*].fieldSistolicMax").value(hasItem(DEFAULT_FIELD_SISTOLIC_MAX.doubleValue())))
            .andExpect(jsonPath("$.[*].fieldSistolicMin").value(hasItem(DEFAULT_FIELD_SISTOLIC_MIN.doubleValue())))
            .andExpect(jsonPath("$.[*].fieldDiasatolicAverage").value(hasItem(DEFAULT_FIELD_DIASATOLIC_AVERAGE.doubleValue())))
            .andExpect(jsonPath("$.[*].fieldDiastolicMax").value(hasItem(DEFAULT_FIELD_DIASTOLIC_MAX.doubleValue())))
            .andExpect(jsonPath("$.[*].fieldDiastolicMin").value(hasItem(DEFAULT_FIELD_DIASTOLIC_MIN.doubleValue())))
            .andExpect(jsonPath("$.[*].bodyPosition").value(hasItem(DEFAULT_BODY_POSITION)))
            .andExpect(jsonPath("$.[*].measurementLocation").value(hasItem(DEFAULT_MEASUREMENT_LOCATION)))
            .andExpect(jsonPath("$.[*].startTime").value(hasItem(DEFAULT_START_TIME.toString())))
            .andExpect(jsonPath("$.[*].endTime").value(hasItem(DEFAULT_END_TIME.toString())));

        // Check, that the count call also returns 1
        restBloodPressureSummaryMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultBloodPressureSummaryShouldNotBeFound(String filter) throws Exception {
        restBloodPressureSummaryMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restBloodPressureSummaryMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingBloodPressureSummary() throws Exception {
        // Get the bloodPressureSummary
        restBloodPressureSummaryMockMvc.perform(get(ENTITY_API_URL_ID, UUID.randomUUID().toString())).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingBloodPressureSummary() throws Exception {
        // Initialize the database
        bloodPressureSummaryRepository.saveAndFlush(bloodPressureSummary);

        int databaseSizeBeforeUpdate = bloodPressureSummaryRepository.findAll().size();

        // Update the bloodPressureSummary
        BloodPressureSummary updatedBloodPressureSummary = bloodPressureSummaryRepository.findById(bloodPressureSummary.getId()).get();
        // Disconnect from session so that the updates on updatedBloodPressureSummary are not directly saved in db
        em.detach(updatedBloodPressureSummary);
        updatedBloodPressureSummary
            .usuarioId(UPDATED_USUARIO_ID)
            .empresaId(UPDATED_EMPRESA_ID)
            .fieldSistolicAverage(UPDATED_FIELD_SISTOLIC_AVERAGE)
            .fieldSistolicMax(UPDATED_FIELD_SISTOLIC_MAX)
            .fieldSistolicMin(UPDATED_FIELD_SISTOLIC_MIN)
            .fieldDiasatolicAverage(UPDATED_FIELD_DIASATOLIC_AVERAGE)
            .fieldDiastolicMax(UPDATED_FIELD_DIASTOLIC_MAX)
            .fieldDiastolicMin(UPDATED_FIELD_DIASTOLIC_MIN)
            .bodyPosition(UPDATED_BODY_POSITION)
            .measurementLocation(UPDATED_MEASUREMENT_LOCATION)
            .startTime(UPDATED_START_TIME)
            .endTime(UPDATED_END_TIME);

        restBloodPressureSummaryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedBloodPressureSummary.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedBloodPressureSummary))
            )
            .andExpect(status().isOk());

        // Validate the BloodPressureSummary in the database
        List<BloodPressureSummary> bloodPressureSummaryList = bloodPressureSummaryRepository.findAll();
        assertThat(bloodPressureSummaryList).hasSize(databaseSizeBeforeUpdate);
        BloodPressureSummary testBloodPressureSummary = bloodPressureSummaryList.get(bloodPressureSummaryList.size() - 1);
        assertThat(testBloodPressureSummary.getUsuarioId()).isEqualTo(UPDATED_USUARIO_ID);
        assertThat(testBloodPressureSummary.getEmpresaId()).isEqualTo(UPDATED_EMPRESA_ID);
        assertThat(testBloodPressureSummary.getFieldSistolicAverage()).isEqualTo(UPDATED_FIELD_SISTOLIC_AVERAGE);
        assertThat(testBloodPressureSummary.getFieldSistolicMax()).isEqualTo(UPDATED_FIELD_SISTOLIC_MAX);
        assertThat(testBloodPressureSummary.getFieldSistolicMin()).isEqualTo(UPDATED_FIELD_SISTOLIC_MIN);
        assertThat(testBloodPressureSummary.getFieldDiasatolicAverage()).isEqualTo(UPDATED_FIELD_DIASATOLIC_AVERAGE);
        assertThat(testBloodPressureSummary.getFieldDiastolicMax()).isEqualTo(UPDATED_FIELD_DIASTOLIC_MAX);
        assertThat(testBloodPressureSummary.getFieldDiastolicMin()).isEqualTo(UPDATED_FIELD_DIASTOLIC_MIN);
        assertThat(testBloodPressureSummary.getBodyPosition()).isEqualTo(UPDATED_BODY_POSITION);
        assertThat(testBloodPressureSummary.getMeasurementLocation()).isEqualTo(UPDATED_MEASUREMENT_LOCATION);
        assertThat(testBloodPressureSummary.getStartTime()).isEqualTo(UPDATED_START_TIME);
        assertThat(testBloodPressureSummary.getEndTime()).isEqualTo(UPDATED_END_TIME);
    }

    @Test
    @Transactional
    void putNonExistingBloodPressureSummary() throws Exception {
        int databaseSizeBeforeUpdate = bloodPressureSummaryRepository.findAll().size();
        bloodPressureSummary.setId(UUID.randomUUID());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBloodPressureSummaryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, bloodPressureSummary.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bloodPressureSummary))
            )
            .andExpect(status().isBadRequest());

        // Validate the BloodPressureSummary in the database
        List<BloodPressureSummary> bloodPressureSummaryList = bloodPressureSummaryRepository.findAll();
        assertThat(bloodPressureSummaryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchBloodPressureSummary() throws Exception {
        int databaseSizeBeforeUpdate = bloodPressureSummaryRepository.findAll().size();
        bloodPressureSummary.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBloodPressureSummaryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bloodPressureSummary))
            )
            .andExpect(status().isBadRequest());

        // Validate the BloodPressureSummary in the database
        List<BloodPressureSummary> bloodPressureSummaryList = bloodPressureSummaryRepository.findAll();
        assertThat(bloodPressureSummaryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamBloodPressureSummary() throws Exception {
        int databaseSizeBeforeUpdate = bloodPressureSummaryRepository.findAll().size();
        bloodPressureSummary.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBloodPressureSummaryMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bloodPressureSummary))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the BloodPressureSummary in the database
        List<BloodPressureSummary> bloodPressureSummaryList = bloodPressureSummaryRepository.findAll();
        assertThat(bloodPressureSummaryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateBloodPressureSummaryWithPatch() throws Exception {
        // Initialize the database
        bloodPressureSummaryRepository.saveAndFlush(bloodPressureSummary);

        int databaseSizeBeforeUpdate = bloodPressureSummaryRepository.findAll().size();

        // Update the bloodPressureSummary using partial update
        BloodPressureSummary partialUpdatedBloodPressureSummary = new BloodPressureSummary();
        partialUpdatedBloodPressureSummary.setId(bloodPressureSummary.getId());

        partialUpdatedBloodPressureSummary
            .fieldSistolicAverage(UPDATED_FIELD_SISTOLIC_AVERAGE)
            .fieldSistolicMax(UPDATED_FIELD_SISTOLIC_MAX)
            .fieldDiasatolicAverage(UPDATED_FIELD_DIASATOLIC_AVERAGE)
            .fieldDiastolicMax(UPDATED_FIELD_DIASTOLIC_MAX)
            .bodyPosition(UPDATED_BODY_POSITION)
            .endTime(UPDATED_END_TIME);

        restBloodPressureSummaryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBloodPressureSummary.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBloodPressureSummary))
            )
            .andExpect(status().isOk());

        // Validate the BloodPressureSummary in the database
        List<BloodPressureSummary> bloodPressureSummaryList = bloodPressureSummaryRepository.findAll();
        assertThat(bloodPressureSummaryList).hasSize(databaseSizeBeforeUpdate);
        BloodPressureSummary testBloodPressureSummary = bloodPressureSummaryList.get(bloodPressureSummaryList.size() - 1);
        assertThat(testBloodPressureSummary.getUsuarioId()).isEqualTo(DEFAULT_USUARIO_ID);
        assertThat(testBloodPressureSummary.getEmpresaId()).isEqualTo(DEFAULT_EMPRESA_ID);
        assertThat(testBloodPressureSummary.getFieldSistolicAverage()).isEqualTo(UPDATED_FIELD_SISTOLIC_AVERAGE);
        assertThat(testBloodPressureSummary.getFieldSistolicMax()).isEqualTo(UPDATED_FIELD_SISTOLIC_MAX);
        assertThat(testBloodPressureSummary.getFieldSistolicMin()).isEqualTo(DEFAULT_FIELD_SISTOLIC_MIN);
        assertThat(testBloodPressureSummary.getFieldDiasatolicAverage()).isEqualTo(UPDATED_FIELD_DIASATOLIC_AVERAGE);
        assertThat(testBloodPressureSummary.getFieldDiastolicMax()).isEqualTo(UPDATED_FIELD_DIASTOLIC_MAX);
        assertThat(testBloodPressureSummary.getFieldDiastolicMin()).isEqualTo(DEFAULT_FIELD_DIASTOLIC_MIN);
        assertThat(testBloodPressureSummary.getBodyPosition()).isEqualTo(UPDATED_BODY_POSITION);
        assertThat(testBloodPressureSummary.getMeasurementLocation()).isEqualTo(DEFAULT_MEASUREMENT_LOCATION);
        assertThat(testBloodPressureSummary.getStartTime()).isEqualTo(DEFAULT_START_TIME);
        assertThat(testBloodPressureSummary.getEndTime()).isEqualTo(UPDATED_END_TIME);
    }

    @Test
    @Transactional
    void fullUpdateBloodPressureSummaryWithPatch() throws Exception {
        // Initialize the database
        bloodPressureSummaryRepository.saveAndFlush(bloodPressureSummary);

        int databaseSizeBeforeUpdate = bloodPressureSummaryRepository.findAll().size();

        // Update the bloodPressureSummary using partial update
        BloodPressureSummary partialUpdatedBloodPressureSummary = new BloodPressureSummary();
        partialUpdatedBloodPressureSummary.setId(bloodPressureSummary.getId());

        partialUpdatedBloodPressureSummary
            .usuarioId(UPDATED_USUARIO_ID)
            .empresaId(UPDATED_EMPRESA_ID)
            .fieldSistolicAverage(UPDATED_FIELD_SISTOLIC_AVERAGE)
            .fieldSistolicMax(UPDATED_FIELD_SISTOLIC_MAX)
            .fieldSistolicMin(UPDATED_FIELD_SISTOLIC_MIN)
            .fieldDiasatolicAverage(UPDATED_FIELD_DIASATOLIC_AVERAGE)
            .fieldDiastolicMax(UPDATED_FIELD_DIASTOLIC_MAX)
            .fieldDiastolicMin(UPDATED_FIELD_DIASTOLIC_MIN)
            .bodyPosition(UPDATED_BODY_POSITION)
            .measurementLocation(UPDATED_MEASUREMENT_LOCATION)
            .startTime(UPDATED_START_TIME)
            .endTime(UPDATED_END_TIME);

        restBloodPressureSummaryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBloodPressureSummary.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBloodPressureSummary))
            )
            .andExpect(status().isOk());

        // Validate the BloodPressureSummary in the database
        List<BloodPressureSummary> bloodPressureSummaryList = bloodPressureSummaryRepository.findAll();
        assertThat(bloodPressureSummaryList).hasSize(databaseSizeBeforeUpdate);
        BloodPressureSummary testBloodPressureSummary = bloodPressureSummaryList.get(bloodPressureSummaryList.size() - 1);
        assertThat(testBloodPressureSummary.getUsuarioId()).isEqualTo(UPDATED_USUARIO_ID);
        assertThat(testBloodPressureSummary.getEmpresaId()).isEqualTo(UPDATED_EMPRESA_ID);
        assertThat(testBloodPressureSummary.getFieldSistolicAverage()).isEqualTo(UPDATED_FIELD_SISTOLIC_AVERAGE);
        assertThat(testBloodPressureSummary.getFieldSistolicMax()).isEqualTo(UPDATED_FIELD_SISTOLIC_MAX);
        assertThat(testBloodPressureSummary.getFieldSistolicMin()).isEqualTo(UPDATED_FIELD_SISTOLIC_MIN);
        assertThat(testBloodPressureSummary.getFieldDiasatolicAverage()).isEqualTo(UPDATED_FIELD_DIASATOLIC_AVERAGE);
        assertThat(testBloodPressureSummary.getFieldDiastolicMax()).isEqualTo(UPDATED_FIELD_DIASTOLIC_MAX);
        assertThat(testBloodPressureSummary.getFieldDiastolicMin()).isEqualTo(UPDATED_FIELD_DIASTOLIC_MIN);
        assertThat(testBloodPressureSummary.getBodyPosition()).isEqualTo(UPDATED_BODY_POSITION);
        assertThat(testBloodPressureSummary.getMeasurementLocation()).isEqualTo(UPDATED_MEASUREMENT_LOCATION);
        assertThat(testBloodPressureSummary.getStartTime()).isEqualTo(UPDATED_START_TIME);
        assertThat(testBloodPressureSummary.getEndTime()).isEqualTo(UPDATED_END_TIME);
    }

    @Test
    @Transactional
    void patchNonExistingBloodPressureSummary() throws Exception {
        int databaseSizeBeforeUpdate = bloodPressureSummaryRepository.findAll().size();
        bloodPressureSummary.setId(UUID.randomUUID());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBloodPressureSummaryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, bloodPressureSummary.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(bloodPressureSummary))
            )
            .andExpect(status().isBadRequest());

        // Validate the BloodPressureSummary in the database
        List<BloodPressureSummary> bloodPressureSummaryList = bloodPressureSummaryRepository.findAll();
        assertThat(bloodPressureSummaryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchBloodPressureSummary() throws Exception {
        int databaseSizeBeforeUpdate = bloodPressureSummaryRepository.findAll().size();
        bloodPressureSummary.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBloodPressureSummaryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(bloodPressureSummary))
            )
            .andExpect(status().isBadRequest());

        // Validate the BloodPressureSummary in the database
        List<BloodPressureSummary> bloodPressureSummaryList = bloodPressureSummaryRepository.findAll();
        assertThat(bloodPressureSummaryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamBloodPressureSummary() throws Exception {
        int databaseSizeBeforeUpdate = bloodPressureSummaryRepository.findAll().size();
        bloodPressureSummary.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBloodPressureSummaryMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(bloodPressureSummary))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the BloodPressureSummary in the database
        List<BloodPressureSummary> bloodPressureSummaryList = bloodPressureSummaryRepository.findAll();
        assertThat(bloodPressureSummaryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteBloodPressureSummary() throws Exception {
        // Initialize the database
        bloodPressureSummaryRepository.saveAndFlush(bloodPressureSummary);

        int databaseSizeBeforeDelete = bloodPressureSummaryRepository.findAll().size();

        // Delete the bloodPressureSummary
        restBloodPressureSummaryMockMvc
            .perform(delete(ENTITY_API_URL_ID, bloodPressureSummary.getId().toString()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<BloodPressureSummary> bloodPressureSummaryList = bloodPressureSummaryRepository.findAll();
        assertThat(bloodPressureSummaryList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
