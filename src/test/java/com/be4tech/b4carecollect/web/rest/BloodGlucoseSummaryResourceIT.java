package com.be4tech.b4carecollect.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.be4tech.b4carecollect.IntegrationTest;
import com.be4tech.b4carecollect.domain.BloodGlucoseSummary;
import com.be4tech.b4carecollect.repository.BloodGlucoseSummaryRepository;
import com.be4tech.b4carecollect.service.criteria.BloodGlucoseSummaryCriteria;
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
import org.springframework.util.Base64Utils;

/**
 * Integration tests for the {@link BloodGlucoseSummaryResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class BloodGlucoseSummaryResourceIT {

    private static final String DEFAULT_USUARIO_ID = "AAAAAAAAAA";
    private static final String UPDATED_USUARIO_ID = "BBBBBBBBBB";

    private static final String DEFAULT_EMPRESA_ID = "AAAAAAAAAA";
    private static final String UPDATED_EMPRESA_ID = "BBBBBBBBBB";

    private static final Float DEFAULT_FIELD_AVERAGE = 1F;
    private static final Float UPDATED_FIELD_AVERAGE = 2F;
    private static final Float SMALLER_FIELD_AVERAGE = 1F - 1F;

    private static final Float DEFAULT_FIELD_MAX = 1F;
    private static final Float UPDATED_FIELD_MAX = 2F;
    private static final Float SMALLER_FIELD_MAX = 1F - 1F;

    private static final Float DEFAULT_FIELD_MIN = 1F;
    private static final Float UPDATED_FIELD_MIN = 2F;
    private static final Float SMALLER_FIELD_MIN = 1F - 1F;

    private static final Integer DEFAULT_INTERVAL_FOOD = 1;
    private static final Integer UPDATED_INTERVAL_FOOD = 2;
    private static final Integer SMALLER_INTERVAL_FOOD = 1 - 1;

    private static final String DEFAULT_MEAL_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_MEAL_TYPE = "BBBBBBBBBB";

    private static final Integer DEFAULT_RELATION_TEMPORAL_SLEEP = 1;
    private static final Integer UPDATED_RELATION_TEMPORAL_SLEEP = 2;
    private static final Integer SMALLER_RELATION_TEMPORAL_SLEEP = 1 - 1;

    private static final Integer DEFAULT_SAMPLE_SOURCE = 1;
    private static final Integer UPDATED_SAMPLE_SOURCE = 2;
    private static final Integer SMALLER_SAMPLE_SOURCE = 1 - 1;

    private static final Instant DEFAULT_START_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_START_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_END_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_END_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/blood-glucose-summaries";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private BloodGlucoseSummaryRepository bloodGlucoseSummaryRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBloodGlucoseSummaryMockMvc;

    private BloodGlucoseSummary bloodGlucoseSummary;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BloodGlucoseSummary createEntity(EntityManager em) {
        BloodGlucoseSummary bloodGlucoseSummary = new BloodGlucoseSummary()
            .usuarioId(DEFAULT_USUARIO_ID)
            .empresaId(DEFAULT_EMPRESA_ID)
            .fieldAverage(DEFAULT_FIELD_AVERAGE)
            .fieldMax(DEFAULT_FIELD_MAX)
            .fieldMin(DEFAULT_FIELD_MIN)
            .intervalFood(DEFAULT_INTERVAL_FOOD)
            .mealType(DEFAULT_MEAL_TYPE)
            .relationTemporalSleep(DEFAULT_RELATION_TEMPORAL_SLEEP)
            .sampleSource(DEFAULT_SAMPLE_SOURCE)
            .startTime(DEFAULT_START_TIME)
            .endTime(DEFAULT_END_TIME);
        return bloodGlucoseSummary;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BloodGlucoseSummary createUpdatedEntity(EntityManager em) {
        BloodGlucoseSummary bloodGlucoseSummary = new BloodGlucoseSummary()
            .usuarioId(UPDATED_USUARIO_ID)
            .empresaId(UPDATED_EMPRESA_ID)
            .fieldAverage(UPDATED_FIELD_AVERAGE)
            .fieldMax(UPDATED_FIELD_MAX)
            .fieldMin(UPDATED_FIELD_MIN)
            .intervalFood(UPDATED_INTERVAL_FOOD)
            .mealType(UPDATED_MEAL_TYPE)
            .relationTemporalSleep(UPDATED_RELATION_TEMPORAL_SLEEP)
            .sampleSource(UPDATED_SAMPLE_SOURCE)
            .startTime(UPDATED_START_TIME)
            .endTime(UPDATED_END_TIME);
        return bloodGlucoseSummary;
    }

    @BeforeEach
    public void initTest() {
        bloodGlucoseSummary = createEntity(em);
    }

    @Test
    @Transactional
    void createBloodGlucoseSummary() throws Exception {
        int databaseSizeBeforeCreate = bloodGlucoseSummaryRepository.findAll().size();
        // Create the BloodGlucoseSummary
        restBloodGlucoseSummaryMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bloodGlucoseSummary))
            )
            .andExpect(status().isCreated());

        // Validate the BloodGlucoseSummary in the database
        List<BloodGlucoseSummary> bloodGlucoseSummaryList = bloodGlucoseSummaryRepository.findAll();
        assertThat(bloodGlucoseSummaryList).hasSize(databaseSizeBeforeCreate + 1);
        BloodGlucoseSummary testBloodGlucoseSummary = bloodGlucoseSummaryList.get(bloodGlucoseSummaryList.size() - 1);
        assertThat(testBloodGlucoseSummary.getUsuarioId()).isEqualTo(DEFAULT_USUARIO_ID);
        assertThat(testBloodGlucoseSummary.getEmpresaId()).isEqualTo(DEFAULT_EMPRESA_ID);
        assertThat(testBloodGlucoseSummary.getFieldAverage()).isEqualTo(DEFAULT_FIELD_AVERAGE);
        assertThat(testBloodGlucoseSummary.getFieldMax()).isEqualTo(DEFAULT_FIELD_MAX);
        assertThat(testBloodGlucoseSummary.getFieldMin()).isEqualTo(DEFAULT_FIELD_MIN);
        assertThat(testBloodGlucoseSummary.getIntervalFood()).isEqualTo(DEFAULT_INTERVAL_FOOD);
        assertThat(testBloodGlucoseSummary.getMealType()).isEqualTo(DEFAULT_MEAL_TYPE);
        assertThat(testBloodGlucoseSummary.getRelationTemporalSleep()).isEqualTo(DEFAULT_RELATION_TEMPORAL_SLEEP);
        assertThat(testBloodGlucoseSummary.getSampleSource()).isEqualTo(DEFAULT_SAMPLE_SOURCE);
        assertThat(testBloodGlucoseSummary.getStartTime()).isEqualTo(DEFAULT_START_TIME);
        assertThat(testBloodGlucoseSummary.getEndTime()).isEqualTo(DEFAULT_END_TIME);
    }

    @Test
    @Transactional
    void createBloodGlucoseSummaryWithExistingId() throws Exception {
        // Create the BloodGlucoseSummary with an existing ID
        bloodGlucoseSummaryRepository.saveAndFlush(bloodGlucoseSummary);

        int databaseSizeBeforeCreate = bloodGlucoseSummaryRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBloodGlucoseSummaryMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bloodGlucoseSummary))
            )
            .andExpect(status().isBadRequest());

        // Validate the BloodGlucoseSummary in the database
        List<BloodGlucoseSummary> bloodGlucoseSummaryList = bloodGlucoseSummaryRepository.findAll();
        assertThat(bloodGlucoseSummaryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllBloodGlucoseSummaries() throws Exception {
        // Initialize the database
        bloodGlucoseSummaryRepository.saveAndFlush(bloodGlucoseSummary);

        // Get all the bloodGlucoseSummaryList
        restBloodGlucoseSummaryMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bloodGlucoseSummary.getId().toString())))
            .andExpect(jsonPath("$.[*].usuarioId").value(hasItem(DEFAULT_USUARIO_ID)))
            .andExpect(jsonPath("$.[*].empresaId").value(hasItem(DEFAULT_EMPRESA_ID)))
            .andExpect(jsonPath("$.[*].fieldAverage").value(hasItem(DEFAULT_FIELD_AVERAGE.doubleValue())))
            .andExpect(jsonPath("$.[*].fieldMax").value(hasItem(DEFAULT_FIELD_MAX.doubleValue())))
            .andExpect(jsonPath("$.[*].fieldMin").value(hasItem(DEFAULT_FIELD_MIN.doubleValue())))
            .andExpect(jsonPath("$.[*].intervalFood").value(hasItem(DEFAULT_INTERVAL_FOOD)))
            .andExpect(jsonPath("$.[*].mealType").value(hasItem(DEFAULT_MEAL_TYPE.toString())))
            .andExpect(jsonPath("$.[*].relationTemporalSleep").value(hasItem(DEFAULT_RELATION_TEMPORAL_SLEEP)))
            .andExpect(jsonPath("$.[*].sampleSource").value(hasItem(DEFAULT_SAMPLE_SOURCE)))
            .andExpect(jsonPath("$.[*].startTime").value(hasItem(DEFAULT_START_TIME.toString())))
            .andExpect(jsonPath("$.[*].endTime").value(hasItem(DEFAULT_END_TIME.toString())));
    }

    @Test
    @Transactional
    void getBloodGlucoseSummary() throws Exception {
        // Initialize the database
        bloodGlucoseSummaryRepository.saveAndFlush(bloodGlucoseSummary);

        // Get the bloodGlucoseSummary
        restBloodGlucoseSummaryMockMvc
            .perform(get(ENTITY_API_URL_ID, bloodGlucoseSummary.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(bloodGlucoseSummary.getId().toString()))
            .andExpect(jsonPath("$.usuarioId").value(DEFAULT_USUARIO_ID))
            .andExpect(jsonPath("$.empresaId").value(DEFAULT_EMPRESA_ID))
            .andExpect(jsonPath("$.fieldAverage").value(DEFAULT_FIELD_AVERAGE.doubleValue()))
            .andExpect(jsonPath("$.fieldMax").value(DEFAULT_FIELD_MAX.doubleValue()))
            .andExpect(jsonPath("$.fieldMin").value(DEFAULT_FIELD_MIN.doubleValue()))
            .andExpect(jsonPath("$.intervalFood").value(DEFAULT_INTERVAL_FOOD))
            .andExpect(jsonPath("$.mealType").value(DEFAULT_MEAL_TYPE.toString()))
            .andExpect(jsonPath("$.relationTemporalSleep").value(DEFAULT_RELATION_TEMPORAL_SLEEP))
            .andExpect(jsonPath("$.sampleSource").value(DEFAULT_SAMPLE_SOURCE))
            .andExpect(jsonPath("$.startTime").value(DEFAULT_START_TIME.toString()))
            .andExpect(jsonPath("$.endTime").value(DEFAULT_END_TIME.toString()));
    }

    @Test
    @Transactional
    void getBloodGlucoseSummariesByIdFiltering() throws Exception {
        // Initialize the database
        bloodGlucoseSummaryRepository.saveAndFlush(bloodGlucoseSummary);

        UUID id = bloodGlucoseSummary.getId();

        defaultBloodGlucoseSummaryShouldBeFound("id.equals=" + id);
        defaultBloodGlucoseSummaryShouldNotBeFound("id.notEquals=" + id);
    }

    @Test
    @Transactional
    void getAllBloodGlucoseSummariesByUsuarioIdIsEqualToSomething() throws Exception {
        // Initialize the database
        bloodGlucoseSummaryRepository.saveAndFlush(bloodGlucoseSummary);

        // Get all the bloodGlucoseSummaryList where usuarioId equals to DEFAULT_USUARIO_ID
        defaultBloodGlucoseSummaryShouldBeFound("usuarioId.equals=" + DEFAULT_USUARIO_ID);

        // Get all the bloodGlucoseSummaryList where usuarioId equals to UPDATED_USUARIO_ID
        defaultBloodGlucoseSummaryShouldNotBeFound("usuarioId.equals=" + UPDATED_USUARIO_ID);
    }

    @Test
    @Transactional
    void getAllBloodGlucoseSummariesByUsuarioIdIsInShouldWork() throws Exception {
        // Initialize the database
        bloodGlucoseSummaryRepository.saveAndFlush(bloodGlucoseSummary);

        // Get all the bloodGlucoseSummaryList where usuarioId in DEFAULT_USUARIO_ID or UPDATED_USUARIO_ID
        defaultBloodGlucoseSummaryShouldBeFound("usuarioId.in=" + DEFAULT_USUARIO_ID + "," + UPDATED_USUARIO_ID);

        // Get all the bloodGlucoseSummaryList where usuarioId equals to UPDATED_USUARIO_ID
        defaultBloodGlucoseSummaryShouldNotBeFound("usuarioId.in=" + UPDATED_USUARIO_ID);
    }

    @Test
    @Transactional
    void getAllBloodGlucoseSummariesByUsuarioIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        bloodGlucoseSummaryRepository.saveAndFlush(bloodGlucoseSummary);

        // Get all the bloodGlucoseSummaryList where usuarioId is not null
        defaultBloodGlucoseSummaryShouldBeFound("usuarioId.specified=true");

        // Get all the bloodGlucoseSummaryList where usuarioId is null
        defaultBloodGlucoseSummaryShouldNotBeFound("usuarioId.specified=false");
    }

    @Test
    @Transactional
    void getAllBloodGlucoseSummariesByUsuarioIdContainsSomething() throws Exception {
        // Initialize the database
        bloodGlucoseSummaryRepository.saveAndFlush(bloodGlucoseSummary);

        // Get all the bloodGlucoseSummaryList where usuarioId contains DEFAULT_USUARIO_ID
        defaultBloodGlucoseSummaryShouldBeFound("usuarioId.contains=" + DEFAULT_USUARIO_ID);

        // Get all the bloodGlucoseSummaryList where usuarioId contains UPDATED_USUARIO_ID
        defaultBloodGlucoseSummaryShouldNotBeFound("usuarioId.contains=" + UPDATED_USUARIO_ID);
    }

    @Test
    @Transactional
    void getAllBloodGlucoseSummariesByUsuarioIdNotContainsSomething() throws Exception {
        // Initialize the database
        bloodGlucoseSummaryRepository.saveAndFlush(bloodGlucoseSummary);

        // Get all the bloodGlucoseSummaryList where usuarioId does not contain DEFAULT_USUARIO_ID
        defaultBloodGlucoseSummaryShouldNotBeFound("usuarioId.doesNotContain=" + DEFAULT_USUARIO_ID);

        // Get all the bloodGlucoseSummaryList where usuarioId does not contain UPDATED_USUARIO_ID
        defaultBloodGlucoseSummaryShouldBeFound("usuarioId.doesNotContain=" + UPDATED_USUARIO_ID);
    }

    @Test
    @Transactional
    void getAllBloodGlucoseSummariesByEmpresaIdIsEqualToSomething() throws Exception {
        // Initialize the database
        bloodGlucoseSummaryRepository.saveAndFlush(bloodGlucoseSummary);

        // Get all the bloodGlucoseSummaryList where empresaId equals to DEFAULT_EMPRESA_ID
        defaultBloodGlucoseSummaryShouldBeFound("empresaId.equals=" + DEFAULT_EMPRESA_ID);

        // Get all the bloodGlucoseSummaryList where empresaId equals to UPDATED_EMPRESA_ID
        defaultBloodGlucoseSummaryShouldNotBeFound("empresaId.equals=" + UPDATED_EMPRESA_ID);
    }

    @Test
    @Transactional
    void getAllBloodGlucoseSummariesByEmpresaIdIsInShouldWork() throws Exception {
        // Initialize the database
        bloodGlucoseSummaryRepository.saveAndFlush(bloodGlucoseSummary);

        // Get all the bloodGlucoseSummaryList where empresaId in DEFAULT_EMPRESA_ID or UPDATED_EMPRESA_ID
        defaultBloodGlucoseSummaryShouldBeFound("empresaId.in=" + DEFAULT_EMPRESA_ID + "," + UPDATED_EMPRESA_ID);

        // Get all the bloodGlucoseSummaryList where empresaId equals to UPDATED_EMPRESA_ID
        defaultBloodGlucoseSummaryShouldNotBeFound("empresaId.in=" + UPDATED_EMPRESA_ID);
    }

    @Test
    @Transactional
    void getAllBloodGlucoseSummariesByEmpresaIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        bloodGlucoseSummaryRepository.saveAndFlush(bloodGlucoseSummary);

        // Get all the bloodGlucoseSummaryList where empresaId is not null
        defaultBloodGlucoseSummaryShouldBeFound("empresaId.specified=true");

        // Get all the bloodGlucoseSummaryList where empresaId is null
        defaultBloodGlucoseSummaryShouldNotBeFound("empresaId.specified=false");
    }

    @Test
    @Transactional
    void getAllBloodGlucoseSummariesByEmpresaIdContainsSomething() throws Exception {
        // Initialize the database
        bloodGlucoseSummaryRepository.saveAndFlush(bloodGlucoseSummary);

        // Get all the bloodGlucoseSummaryList where empresaId contains DEFAULT_EMPRESA_ID
        defaultBloodGlucoseSummaryShouldBeFound("empresaId.contains=" + DEFAULT_EMPRESA_ID);

        // Get all the bloodGlucoseSummaryList where empresaId contains UPDATED_EMPRESA_ID
        defaultBloodGlucoseSummaryShouldNotBeFound("empresaId.contains=" + UPDATED_EMPRESA_ID);
    }

    @Test
    @Transactional
    void getAllBloodGlucoseSummariesByEmpresaIdNotContainsSomething() throws Exception {
        // Initialize the database
        bloodGlucoseSummaryRepository.saveAndFlush(bloodGlucoseSummary);

        // Get all the bloodGlucoseSummaryList where empresaId does not contain DEFAULT_EMPRESA_ID
        defaultBloodGlucoseSummaryShouldNotBeFound("empresaId.doesNotContain=" + DEFAULT_EMPRESA_ID);

        // Get all the bloodGlucoseSummaryList where empresaId does not contain UPDATED_EMPRESA_ID
        defaultBloodGlucoseSummaryShouldBeFound("empresaId.doesNotContain=" + UPDATED_EMPRESA_ID);
    }

    @Test
    @Transactional
    void getAllBloodGlucoseSummariesByFieldAverageIsEqualToSomething() throws Exception {
        // Initialize the database
        bloodGlucoseSummaryRepository.saveAndFlush(bloodGlucoseSummary);

        // Get all the bloodGlucoseSummaryList where fieldAverage equals to DEFAULT_FIELD_AVERAGE
        defaultBloodGlucoseSummaryShouldBeFound("fieldAverage.equals=" + DEFAULT_FIELD_AVERAGE);

        // Get all the bloodGlucoseSummaryList where fieldAverage equals to UPDATED_FIELD_AVERAGE
        defaultBloodGlucoseSummaryShouldNotBeFound("fieldAverage.equals=" + UPDATED_FIELD_AVERAGE);
    }

    @Test
    @Transactional
    void getAllBloodGlucoseSummariesByFieldAverageIsInShouldWork() throws Exception {
        // Initialize the database
        bloodGlucoseSummaryRepository.saveAndFlush(bloodGlucoseSummary);

        // Get all the bloodGlucoseSummaryList where fieldAverage in DEFAULT_FIELD_AVERAGE or UPDATED_FIELD_AVERAGE
        defaultBloodGlucoseSummaryShouldBeFound("fieldAverage.in=" + DEFAULT_FIELD_AVERAGE + "," + UPDATED_FIELD_AVERAGE);

        // Get all the bloodGlucoseSummaryList where fieldAverage equals to UPDATED_FIELD_AVERAGE
        defaultBloodGlucoseSummaryShouldNotBeFound("fieldAverage.in=" + UPDATED_FIELD_AVERAGE);
    }

    @Test
    @Transactional
    void getAllBloodGlucoseSummariesByFieldAverageIsNullOrNotNull() throws Exception {
        // Initialize the database
        bloodGlucoseSummaryRepository.saveAndFlush(bloodGlucoseSummary);

        // Get all the bloodGlucoseSummaryList where fieldAverage is not null
        defaultBloodGlucoseSummaryShouldBeFound("fieldAverage.specified=true");

        // Get all the bloodGlucoseSummaryList where fieldAverage is null
        defaultBloodGlucoseSummaryShouldNotBeFound("fieldAverage.specified=false");
    }

    @Test
    @Transactional
    void getAllBloodGlucoseSummariesByFieldAverageIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        bloodGlucoseSummaryRepository.saveAndFlush(bloodGlucoseSummary);

        // Get all the bloodGlucoseSummaryList where fieldAverage is greater than or equal to DEFAULT_FIELD_AVERAGE
        defaultBloodGlucoseSummaryShouldBeFound("fieldAverage.greaterThanOrEqual=" + DEFAULT_FIELD_AVERAGE);

        // Get all the bloodGlucoseSummaryList where fieldAverage is greater than or equal to UPDATED_FIELD_AVERAGE
        defaultBloodGlucoseSummaryShouldNotBeFound("fieldAverage.greaterThanOrEqual=" + UPDATED_FIELD_AVERAGE);
    }

    @Test
    @Transactional
    void getAllBloodGlucoseSummariesByFieldAverageIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        bloodGlucoseSummaryRepository.saveAndFlush(bloodGlucoseSummary);

        // Get all the bloodGlucoseSummaryList where fieldAverage is less than or equal to DEFAULT_FIELD_AVERAGE
        defaultBloodGlucoseSummaryShouldBeFound("fieldAverage.lessThanOrEqual=" + DEFAULT_FIELD_AVERAGE);

        // Get all the bloodGlucoseSummaryList where fieldAverage is less than or equal to SMALLER_FIELD_AVERAGE
        defaultBloodGlucoseSummaryShouldNotBeFound("fieldAverage.lessThanOrEqual=" + SMALLER_FIELD_AVERAGE);
    }

    @Test
    @Transactional
    void getAllBloodGlucoseSummariesByFieldAverageIsLessThanSomething() throws Exception {
        // Initialize the database
        bloodGlucoseSummaryRepository.saveAndFlush(bloodGlucoseSummary);

        // Get all the bloodGlucoseSummaryList where fieldAverage is less than DEFAULT_FIELD_AVERAGE
        defaultBloodGlucoseSummaryShouldNotBeFound("fieldAverage.lessThan=" + DEFAULT_FIELD_AVERAGE);

        // Get all the bloodGlucoseSummaryList where fieldAverage is less than UPDATED_FIELD_AVERAGE
        defaultBloodGlucoseSummaryShouldBeFound("fieldAverage.lessThan=" + UPDATED_FIELD_AVERAGE);
    }

    @Test
    @Transactional
    void getAllBloodGlucoseSummariesByFieldAverageIsGreaterThanSomething() throws Exception {
        // Initialize the database
        bloodGlucoseSummaryRepository.saveAndFlush(bloodGlucoseSummary);

        // Get all the bloodGlucoseSummaryList where fieldAverage is greater than DEFAULT_FIELD_AVERAGE
        defaultBloodGlucoseSummaryShouldNotBeFound("fieldAverage.greaterThan=" + DEFAULT_FIELD_AVERAGE);

        // Get all the bloodGlucoseSummaryList where fieldAverage is greater than SMALLER_FIELD_AVERAGE
        defaultBloodGlucoseSummaryShouldBeFound("fieldAverage.greaterThan=" + SMALLER_FIELD_AVERAGE);
    }

    @Test
    @Transactional
    void getAllBloodGlucoseSummariesByFieldMaxIsEqualToSomething() throws Exception {
        // Initialize the database
        bloodGlucoseSummaryRepository.saveAndFlush(bloodGlucoseSummary);

        // Get all the bloodGlucoseSummaryList where fieldMax equals to DEFAULT_FIELD_MAX
        defaultBloodGlucoseSummaryShouldBeFound("fieldMax.equals=" + DEFAULT_FIELD_MAX);

        // Get all the bloodGlucoseSummaryList where fieldMax equals to UPDATED_FIELD_MAX
        defaultBloodGlucoseSummaryShouldNotBeFound("fieldMax.equals=" + UPDATED_FIELD_MAX);
    }

    @Test
    @Transactional
    void getAllBloodGlucoseSummariesByFieldMaxIsInShouldWork() throws Exception {
        // Initialize the database
        bloodGlucoseSummaryRepository.saveAndFlush(bloodGlucoseSummary);

        // Get all the bloodGlucoseSummaryList where fieldMax in DEFAULT_FIELD_MAX or UPDATED_FIELD_MAX
        defaultBloodGlucoseSummaryShouldBeFound("fieldMax.in=" + DEFAULT_FIELD_MAX + "," + UPDATED_FIELD_MAX);

        // Get all the bloodGlucoseSummaryList where fieldMax equals to UPDATED_FIELD_MAX
        defaultBloodGlucoseSummaryShouldNotBeFound("fieldMax.in=" + UPDATED_FIELD_MAX);
    }

    @Test
    @Transactional
    void getAllBloodGlucoseSummariesByFieldMaxIsNullOrNotNull() throws Exception {
        // Initialize the database
        bloodGlucoseSummaryRepository.saveAndFlush(bloodGlucoseSummary);

        // Get all the bloodGlucoseSummaryList where fieldMax is not null
        defaultBloodGlucoseSummaryShouldBeFound("fieldMax.specified=true");

        // Get all the bloodGlucoseSummaryList where fieldMax is null
        defaultBloodGlucoseSummaryShouldNotBeFound("fieldMax.specified=false");
    }

    @Test
    @Transactional
    void getAllBloodGlucoseSummariesByFieldMaxIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        bloodGlucoseSummaryRepository.saveAndFlush(bloodGlucoseSummary);

        // Get all the bloodGlucoseSummaryList where fieldMax is greater than or equal to DEFAULT_FIELD_MAX
        defaultBloodGlucoseSummaryShouldBeFound("fieldMax.greaterThanOrEqual=" + DEFAULT_FIELD_MAX);

        // Get all the bloodGlucoseSummaryList where fieldMax is greater than or equal to UPDATED_FIELD_MAX
        defaultBloodGlucoseSummaryShouldNotBeFound("fieldMax.greaterThanOrEqual=" + UPDATED_FIELD_MAX);
    }

    @Test
    @Transactional
    void getAllBloodGlucoseSummariesByFieldMaxIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        bloodGlucoseSummaryRepository.saveAndFlush(bloodGlucoseSummary);

        // Get all the bloodGlucoseSummaryList where fieldMax is less than or equal to DEFAULT_FIELD_MAX
        defaultBloodGlucoseSummaryShouldBeFound("fieldMax.lessThanOrEqual=" + DEFAULT_FIELD_MAX);

        // Get all the bloodGlucoseSummaryList where fieldMax is less than or equal to SMALLER_FIELD_MAX
        defaultBloodGlucoseSummaryShouldNotBeFound("fieldMax.lessThanOrEqual=" + SMALLER_FIELD_MAX);
    }

    @Test
    @Transactional
    void getAllBloodGlucoseSummariesByFieldMaxIsLessThanSomething() throws Exception {
        // Initialize the database
        bloodGlucoseSummaryRepository.saveAndFlush(bloodGlucoseSummary);

        // Get all the bloodGlucoseSummaryList where fieldMax is less than DEFAULT_FIELD_MAX
        defaultBloodGlucoseSummaryShouldNotBeFound("fieldMax.lessThan=" + DEFAULT_FIELD_MAX);

        // Get all the bloodGlucoseSummaryList where fieldMax is less than UPDATED_FIELD_MAX
        defaultBloodGlucoseSummaryShouldBeFound("fieldMax.lessThan=" + UPDATED_FIELD_MAX);
    }

    @Test
    @Transactional
    void getAllBloodGlucoseSummariesByFieldMaxIsGreaterThanSomething() throws Exception {
        // Initialize the database
        bloodGlucoseSummaryRepository.saveAndFlush(bloodGlucoseSummary);

        // Get all the bloodGlucoseSummaryList where fieldMax is greater than DEFAULT_FIELD_MAX
        defaultBloodGlucoseSummaryShouldNotBeFound("fieldMax.greaterThan=" + DEFAULT_FIELD_MAX);

        // Get all the bloodGlucoseSummaryList where fieldMax is greater than SMALLER_FIELD_MAX
        defaultBloodGlucoseSummaryShouldBeFound("fieldMax.greaterThan=" + SMALLER_FIELD_MAX);
    }

    @Test
    @Transactional
    void getAllBloodGlucoseSummariesByFieldMinIsEqualToSomething() throws Exception {
        // Initialize the database
        bloodGlucoseSummaryRepository.saveAndFlush(bloodGlucoseSummary);

        // Get all the bloodGlucoseSummaryList where fieldMin equals to DEFAULT_FIELD_MIN
        defaultBloodGlucoseSummaryShouldBeFound("fieldMin.equals=" + DEFAULT_FIELD_MIN);

        // Get all the bloodGlucoseSummaryList where fieldMin equals to UPDATED_FIELD_MIN
        defaultBloodGlucoseSummaryShouldNotBeFound("fieldMin.equals=" + UPDATED_FIELD_MIN);
    }

    @Test
    @Transactional
    void getAllBloodGlucoseSummariesByFieldMinIsInShouldWork() throws Exception {
        // Initialize the database
        bloodGlucoseSummaryRepository.saveAndFlush(bloodGlucoseSummary);

        // Get all the bloodGlucoseSummaryList where fieldMin in DEFAULT_FIELD_MIN or UPDATED_FIELD_MIN
        defaultBloodGlucoseSummaryShouldBeFound("fieldMin.in=" + DEFAULT_FIELD_MIN + "," + UPDATED_FIELD_MIN);

        // Get all the bloodGlucoseSummaryList where fieldMin equals to UPDATED_FIELD_MIN
        defaultBloodGlucoseSummaryShouldNotBeFound("fieldMin.in=" + UPDATED_FIELD_MIN);
    }

    @Test
    @Transactional
    void getAllBloodGlucoseSummariesByFieldMinIsNullOrNotNull() throws Exception {
        // Initialize the database
        bloodGlucoseSummaryRepository.saveAndFlush(bloodGlucoseSummary);

        // Get all the bloodGlucoseSummaryList where fieldMin is not null
        defaultBloodGlucoseSummaryShouldBeFound("fieldMin.specified=true");

        // Get all the bloodGlucoseSummaryList where fieldMin is null
        defaultBloodGlucoseSummaryShouldNotBeFound("fieldMin.specified=false");
    }

    @Test
    @Transactional
    void getAllBloodGlucoseSummariesByFieldMinIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        bloodGlucoseSummaryRepository.saveAndFlush(bloodGlucoseSummary);

        // Get all the bloodGlucoseSummaryList where fieldMin is greater than or equal to DEFAULT_FIELD_MIN
        defaultBloodGlucoseSummaryShouldBeFound("fieldMin.greaterThanOrEqual=" + DEFAULT_FIELD_MIN);

        // Get all the bloodGlucoseSummaryList where fieldMin is greater than or equal to UPDATED_FIELD_MIN
        defaultBloodGlucoseSummaryShouldNotBeFound("fieldMin.greaterThanOrEqual=" + UPDATED_FIELD_MIN);
    }

    @Test
    @Transactional
    void getAllBloodGlucoseSummariesByFieldMinIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        bloodGlucoseSummaryRepository.saveAndFlush(bloodGlucoseSummary);

        // Get all the bloodGlucoseSummaryList where fieldMin is less than or equal to DEFAULT_FIELD_MIN
        defaultBloodGlucoseSummaryShouldBeFound("fieldMin.lessThanOrEqual=" + DEFAULT_FIELD_MIN);

        // Get all the bloodGlucoseSummaryList where fieldMin is less than or equal to SMALLER_FIELD_MIN
        defaultBloodGlucoseSummaryShouldNotBeFound("fieldMin.lessThanOrEqual=" + SMALLER_FIELD_MIN);
    }

    @Test
    @Transactional
    void getAllBloodGlucoseSummariesByFieldMinIsLessThanSomething() throws Exception {
        // Initialize the database
        bloodGlucoseSummaryRepository.saveAndFlush(bloodGlucoseSummary);

        // Get all the bloodGlucoseSummaryList where fieldMin is less than DEFAULT_FIELD_MIN
        defaultBloodGlucoseSummaryShouldNotBeFound("fieldMin.lessThan=" + DEFAULT_FIELD_MIN);

        // Get all the bloodGlucoseSummaryList where fieldMin is less than UPDATED_FIELD_MIN
        defaultBloodGlucoseSummaryShouldBeFound("fieldMin.lessThan=" + UPDATED_FIELD_MIN);
    }

    @Test
    @Transactional
    void getAllBloodGlucoseSummariesByFieldMinIsGreaterThanSomething() throws Exception {
        // Initialize the database
        bloodGlucoseSummaryRepository.saveAndFlush(bloodGlucoseSummary);

        // Get all the bloodGlucoseSummaryList where fieldMin is greater than DEFAULT_FIELD_MIN
        defaultBloodGlucoseSummaryShouldNotBeFound("fieldMin.greaterThan=" + DEFAULT_FIELD_MIN);

        // Get all the bloodGlucoseSummaryList where fieldMin is greater than SMALLER_FIELD_MIN
        defaultBloodGlucoseSummaryShouldBeFound("fieldMin.greaterThan=" + SMALLER_FIELD_MIN);
    }

    @Test
    @Transactional
    void getAllBloodGlucoseSummariesByIntervalFoodIsEqualToSomething() throws Exception {
        // Initialize the database
        bloodGlucoseSummaryRepository.saveAndFlush(bloodGlucoseSummary);

        // Get all the bloodGlucoseSummaryList where intervalFood equals to DEFAULT_INTERVAL_FOOD
        defaultBloodGlucoseSummaryShouldBeFound("intervalFood.equals=" + DEFAULT_INTERVAL_FOOD);

        // Get all the bloodGlucoseSummaryList where intervalFood equals to UPDATED_INTERVAL_FOOD
        defaultBloodGlucoseSummaryShouldNotBeFound("intervalFood.equals=" + UPDATED_INTERVAL_FOOD);
    }

    @Test
    @Transactional
    void getAllBloodGlucoseSummariesByIntervalFoodIsInShouldWork() throws Exception {
        // Initialize the database
        bloodGlucoseSummaryRepository.saveAndFlush(bloodGlucoseSummary);

        // Get all the bloodGlucoseSummaryList where intervalFood in DEFAULT_INTERVAL_FOOD or UPDATED_INTERVAL_FOOD
        defaultBloodGlucoseSummaryShouldBeFound("intervalFood.in=" + DEFAULT_INTERVAL_FOOD + "," + UPDATED_INTERVAL_FOOD);

        // Get all the bloodGlucoseSummaryList where intervalFood equals to UPDATED_INTERVAL_FOOD
        defaultBloodGlucoseSummaryShouldNotBeFound("intervalFood.in=" + UPDATED_INTERVAL_FOOD);
    }

    @Test
    @Transactional
    void getAllBloodGlucoseSummariesByIntervalFoodIsNullOrNotNull() throws Exception {
        // Initialize the database
        bloodGlucoseSummaryRepository.saveAndFlush(bloodGlucoseSummary);

        // Get all the bloodGlucoseSummaryList where intervalFood is not null
        defaultBloodGlucoseSummaryShouldBeFound("intervalFood.specified=true");

        // Get all the bloodGlucoseSummaryList where intervalFood is null
        defaultBloodGlucoseSummaryShouldNotBeFound("intervalFood.specified=false");
    }

    @Test
    @Transactional
    void getAllBloodGlucoseSummariesByIntervalFoodIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        bloodGlucoseSummaryRepository.saveAndFlush(bloodGlucoseSummary);

        // Get all the bloodGlucoseSummaryList where intervalFood is greater than or equal to DEFAULT_INTERVAL_FOOD
        defaultBloodGlucoseSummaryShouldBeFound("intervalFood.greaterThanOrEqual=" + DEFAULT_INTERVAL_FOOD);

        // Get all the bloodGlucoseSummaryList where intervalFood is greater than or equal to UPDATED_INTERVAL_FOOD
        defaultBloodGlucoseSummaryShouldNotBeFound("intervalFood.greaterThanOrEqual=" + UPDATED_INTERVAL_FOOD);
    }

    @Test
    @Transactional
    void getAllBloodGlucoseSummariesByIntervalFoodIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        bloodGlucoseSummaryRepository.saveAndFlush(bloodGlucoseSummary);

        // Get all the bloodGlucoseSummaryList where intervalFood is less than or equal to DEFAULT_INTERVAL_FOOD
        defaultBloodGlucoseSummaryShouldBeFound("intervalFood.lessThanOrEqual=" + DEFAULT_INTERVAL_FOOD);

        // Get all the bloodGlucoseSummaryList where intervalFood is less than or equal to SMALLER_INTERVAL_FOOD
        defaultBloodGlucoseSummaryShouldNotBeFound("intervalFood.lessThanOrEqual=" + SMALLER_INTERVAL_FOOD);
    }

    @Test
    @Transactional
    void getAllBloodGlucoseSummariesByIntervalFoodIsLessThanSomething() throws Exception {
        // Initialize the database
        bloodGlucoseSummaryRepository.saveAndFlush(bloodGlucoseSummary);

        // Get all the bloodGlucoseSummaryList where intervalFood is less than DEFAULT_INTERVAL_FOOD
        defaultBloodGlucoseSummaryShouldNotBeFound("intervalFood.lessThan=" + DEFAULT_INTERVAL_FOOD);

        // Get all the bloodGlucoseSummaryList where intervalFood is less than UPDATED_INTERVAL_FOOD
        defaultBloodGlucoseSummaryShouldBeFound("intervalFood.lessThan=" + UPDATED_INTERVAL_FOOD);
    }

    @Test
    @Transactional
    void getAllBloodGlucoseSummariesByIntervalFoodIsGreaterThanSomething() throws Exception {
        // Initialize the database
        bloodGlucoseSummaryRepository.saveAndFlush(bloodGlucoseSummary);

        // Get all the bloodGlucoseSummaryList where intervalFood is greater than DEFAULT_INTERVAL_FOOD
        defaultBloodGlucoseSummaryShouldNotBeFound("intervalFood.greaterThan=" + DEFAULT_INTERVAL_FOOD);

        // Get all the bloodGlucoseSummaryList where intervalFood is greater than SMALLER_INTERVAL_FOOD
        defaultBloodGlucoseSummaryShouldBeFound("intervalFood.greaterThan=" + SMALLER_INTERVAL_FOOD);
    }

    @Test
    @Transactional
    void getAllBloodGlucoseSummariesByRelationTemporalSleepIsEqualToSomething() throws Exception {
        // Initialize the database
        bloodGlucoseSummaryRepository.saveAndFlush(bloodGlucoseSummary);

        // Get all the bloodGlucoseSummaryList where relationTemporalSleep equals to DEFAULT_RELATION_TEMPORAL_SLEEP
        defaultBloodGlucoseSummaryShouldBeFound("relationTemporalSleep.equals=" + DEFAULT_RELATION_TEMPORAL_SLEEP);

        // Get all the bloodGlucoseSummaryList where relationTemporalSleep equals to UPDATED_RELATION_TEMPORAL_SLEEP
        defaultBloodGlucoseSummaryShouldNotBeFound("relationTemporalSleep.equals=" + UPDATED_RELATION_TEMPORAL_SLEEP);
    }

    @Test
    @Transactional
    void getAllBloodGlucoseSummariesByRelationTemporalSleepIsInShouldWork() throws Exception {
        // Initialize the database
        bloodGlucoseSummaryRepository.saveAndFlush(bloodGlucoseSummary);

        // Get all the bloodGlucoseSummaryList where relationTemporalSleep in DEFAULT_RELATION_TEMPORAL_SLEEP or UPDATED_RELATION_TEMPORAL_SLEEP
        defaultBloodGlucoseSummaryShouldBeFound(
            "relationTemporalSleep.in=" + DEFAULT_RELATION_TEMPORAL_SLEEP + "," + UPDATED_RELATION_TEMPORAL_SLEEP
        );

        // Get all the bloodGlucoseSummaryList where relationTemporalSleep equals to UPDATED_RELATION_TEMPORAL_SLEEP
        defaultBloodGlucoseSummaryShouldNotBeFound("relationTemporalSleep.in=" + UPDATED_RELATION_TEMPORAL_SLEEP);
    }

    @Test
    @Transactional
    void getAllBloodGlucoseSummariesByRelationTemporalSleepIsNullOrNotNull() throws Exception {
        // Initialize the database
        bloodGlucoseSummaryRepository.saveAndFlush(bloodGlucoseSummary);

        // Get all the bloodGlucoseSummaryList where relationTemporalSleep is not null
        defaultBloodGlucoseSummaryShouldBeFound("relationTemporalSleep.specified=true");

        // Get all the bloodGlucoseSummaryList where relationTemporalSleep is null
        defaultBloodGlucoseSummaryShouldNotBeFound("relationTemporalSleep.specified=false");
    }

    @Test
    @Transactional
    void getAllBloodGlucoseSummariesByRelationTemporalSleepIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        bloodGlucoseSummaryRepository.saveAndFlush(bloodGlucoseSummary);

        // Get all the bloodGlucoseSummaryList where relationTemporalSleep is greater than or equal to DEFAULT_RELATION_TEMPORAL_SLEEP
        defaultBloodGlucoseSummaryShouldBeFound("relationTemporalSleep.greaterThanOrEqual=" + DEFAULT_RELATION_TEMPORAL_SLEEP);

        // Get all the bloodGlucoseSummaryList where relationTemporalSleep is greater than or equal to UPDATED_RELATION_TEMPORAL_SLEEP
        defaultBloodGlucoseSummaryShouldNotBeFound("relationTemporalSleep.greaterThanOrEqual=" + UPDATED_RELATION_TEMPORAL_SLEEP);
    }

    @Test
    @Transactional
    void getAllBloodGlucoseSummariesByRelationTemporalSleepIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        bloodGlucoseSummaryRepository.saveAndFlush(bloodGlucoseSummary);

        // Get all the bloodGlucoseSummaryList where relationTemporalSleep is less than or equal to DEFAULT_RELATION_TEMPORAL_SLEEP
        defaultBloodGlucoseSummaryShouldBeFound("relationTemporalSleep.lessThanOrEqual=" + DEFAULT_RELATION_TEMPORAL_SLEEP);

        // Get all the bloodGlucoseSummaryList where relationTemporalSleep is less than or equal to SMALLER_RELATION_TEMPORAL_SLEEP
        defaultBloodGlucoseSummaryShouldNotBeFound("relationTemporalSleep.lessThanOrEqual=" + SMALLER_RELATION_TEMPORAL_SLEEP);
    }

    @Test
    @Transactional
    void getAllBloodGlucoseSummariesByRelationTemporalSleepIsLessThanSomething() throws Exception {
        // Initialize the database
        bloodGlucoseSummaryRepository.saveAndFlush(bloodGlucoseSummary);

        // Get all the bloodGlucoseSummaryList where relationTemporalSleep is less than DEFAULT_RELATION_TEMPORAL_SLEEP
        defaultBloodGlucoseSummaryShouldNotBeFound("relationTemporalSleep.lessThan=" + DEFAULT_RELATION_TEMPORAL_SLEEP);

        // Get all the bloodGlucoseSummaryList where relationTemporalSleep is less than UPDATED_RELATION_TEMPORAL_SLEEP
        defaultBloodGlucoseSummaryShouldBeFound("relationTemporalSleep.lessThan=" + UPDATED_RELATION_TEMPORAL_SLEEP);
    }

    @Test
    @Transactional
    void getAllBloodGlucoseSummariesByRelationTemporalSleepIsGreaterThanSomething() throws Exception {
        // Initialize the database
        bloodGlucoseSummaryRepository.saveAndFlush(bloodGlucoseSummary);

        // Get all the bloodGlucoseSummaryList where relationTemporalSleep is greater than DEFAULT_RELATION_TEMPORAL_SLEEP
        defaultBloodGlucoseSummaryShouldNotBeFound("relationTemporalSleep.greaterThan=" + DEFAULT_RELATION_TEMPORAL_SLEEP);

        // Get all the bloodGlucoseSummaryList where relationTemporalSleep is greater than SMALLER_RELATION_TEMPORAL_SLEEP
        defaultBloodGlucoseSummaryShouldBeFound("relationTemporalSleep.greaterThan=" + SMALLER_RELATION_TEMPORAL_SLEEP);
    }

    @Test
    @Transactional
    void getAllBloodGlucoseSummariesBySampleSourceIsEqualToSomething() throws Exception {
        // Initialize the database
        bloodGlucoseSummaryRepository.saveAndFlush(bloodGlucoseSummary);

        // Get all the bloodGlucoseSummaryList where sampleSource equals to DEFAULT_SAMPLE_SOURCE
        defaultBloodGlucoseSummaryShouldBeFound("sampleSource.equals=" + DEFAULT_SAMPLE_SOURCE);

        // Get all the bloodGlucoseSummaryList where sampleSource equals to UPDATED_SAMPLE_SOURCE
        defaultBloodGlucoseSummaryShouldNotBeFound("sampleSource.equals=" + UPDATED_SAMPLE_SOURCE);
    }

    @Test
    @Transactional
    void getAllBloodGlucoseSummariesBySampleSourceIsInShouldWork() throws Exception {
        // Initialize the database
        bloodGlucoseSummaryRepository.saveAndFlush(bloodGlucoseSummary);

        // Get all the bloodGlucoseSummaryList where sampleSource in DEFAULT_SAMPLE_SOURCE or UPDATED_SAMPLE_SOURCE
        defaultBloodGlucoseSummaryShouldBeFound("sampleSource.in=" + DEFAULT_SAMPLE_SOURCE + "," + UPDATED_SAMPLE_SOURCE);

        // Get all the bloodGlucoseSummaryList where sampleSource equals to UPDATED_SAMPLE_SOURCE
        defaultBloodGlucoseSummaryShouldNotBeFound("sampleSource.in=" + UPDATED_SAMPLE_SOURCE);
    }

    @Test
    @Transactional
    void getAllBloodGlucoseSummariesBySampleSourceIsNullOrNotNull() throws Exception {
        // Initialize the database
        bloodGlucoseSummaryRepository.saveAndFlush(bloodGlucoseSummary);

        // Get all the bloodGlucoseSummaryList where sampleSource is not null
        defaultBloodGlucoseSummaryShouldBeFound("sampleSource.specified=true");

        // Get all the bloodGlucoseSummaryList where sampleSource is null
        defaultBloodGlucoseSummaryShouldNotBeFound("sampleSource.specified=false");
    }

    @Test
    @Transactional
    void getAllBloodGlucoseSummariesBySampleSourceIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        bloodGlucoseSummaryRepository.saveAndFlush(bloodGlucoseSummary);

        // Get all the bloodGlucoseSummaryList where sampleSource is greater than or equal to DEFAULT_SAMPLE_SOURCE
        defaultBloodGlucoseSummaryShouldBeFound("sampleSource.greaterThanOrEqual=" + DEFAULT_SAMPLE_SOURCE);

        // Get all the bloodGlucoseSummaryList where sampleSource is greater than or equal to UPDATED_SAMPLE_SOURCE
        defaultBloodGlucoseSummaryShouldNotBeFound("sampleSource.greaterThanOrEqual=" + UPDATED_SAMPLE_SOURCE);
    }

    @Test
    @Transactional
    void getAllBloodGlucoseSummariesBySampleSourceIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        bloodGlucoseSummaryRepository.saveAndFlush(bloodGlucoseSummary);

        // Get all the bloodGlucoseSummaryList where sampleSource is less than or equal to DEFAULT_SAMPLE_SOURCE
        defaultBloodGlucoseSummaryShouldBeFound("sampleSource.lessThanOrEqual=" + DEFAULT_SAMPLE_SOURCE);

        // Get all the bloodGlucoseSummaryList where sampleSource is less than or equal to SMALLER_SAMPLE_SOURCE
        defaultBloodGlucoseSummaryShouldNotBeFound("sampleSource.lessThanOrEqual=" + SMALLER_SAMPLE_SOURCE);
    }

    @Test
    @Transactional
    void getAllBloodGlucoseSummariesBySampleSourceIsLessThanSomething() throws Exception {
        // Initialize the database
        bloodGlucoseSummaryRepository.saveAndFlush(bloodGlucoseSummary);

        // Get all the bloodGlucoseSummaryList where sampleSource is less than DEFAULT_SAMPLE_SOURCE
        defaultBloodGlucoseSummaryShouldNotBeFound("sampleSource.lessThan=" + DEFAULT_SAMPLE_SOURCE);

        // Get all the bloodGlucoseSummaryList where sampleSource is less than UPDATED_SAMPLE_SOURCE
        defaultBloodGlucoseSummaryShouldBeFound("sampleSource.lessThan=" + UPDATED_SAMPLE_SOURCE);
    }

    @Test
    @Transactional
    void getAllBloodGlucoseSummariesBySampleSourceIsGreaterThanSomething() throws Exception {
        // Initialize the database
        bloodGlucoseSummaryRepository.saveAndFlush(bloodGlucoseSummary);

        // Get all the bloodGlucoseSummaryList where sampleSource is greater than DEFAULT_SAMPLE_SOURCE
        defaultBloodGlucoseSummaryShouldNotBeFound("sampleSource.greaterThan=" + DEFAULT_SAMPLE_SOURCE);

        // Get all the bloodGlucoseSummaryList where sampleSource is greater than SMALLER_SAMPLE_SOURCE
        defaultBloodGlucoseSummaryShouldBeFound("sampleSource.greaterThan=" + SMALLER_SAMPLE_SOURCE);
    }

    @Test
    @Transactional
    void getAllBloodGlucoseSummariesByStartTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        bloodGlucoseSummaryRepository.saveAndFlush(bloodGlucoseSummary);

        // Get all the bloodGlucoseSummaryList where startTime equals to DEFAULT_START_TIME
        defaultBloodGlucoseSummaryShouldBeFound("startTime.equals=" + DEFAULT_START_TIME);

        // Get all the bloodGlucoseSummaryList where startTime equals to UPDATED_START_TIME
        defaultBloodGlucoseSummaryShouldNotBeFound("startTime.equals=" + UPDATED_START_TIME);
    }

    @Test
    @Transactional
    void getAllBloodGlucoseSummariesByStartTimeIsInShouldWork() throws Exception {
        // Initialize the database
        bloodGlucoseSummaryRepository.saveAndFlush(bloodGlucoseSummary);

        // Get all the bloodGlucoseSummaryList where startTime in DEFAULT_START_TIME or UPDATED_START_TIME
        defaultBloodGlucoseSummaryShouldBeFound("startTime.in=" + DEFAULT_START_TIME + "," + UPDATED_START_TIME);

        // Get all the bloodGlucoseSummaryList where startTime equals to UPDATED_START_TIME
        defaultBloodGlucoseSummaryShouldNotBeFound("startTime.in=" + UPDATED_START_TIME);
    }

    @Test
    @Transactional
    void getAllBloodGlucoseSummariesByStartTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        bloodGlucoseSummaryRepository.saveAndFlush(bloodGlucoseSummary);

        // Get all the bloodGlucoseSummaryList where startTime is not null
        defaultBloodGlucoseSummaryShouldBeFound("startTime.specified=true");

        // Get all the bloodGlucoseSummaryList where startTime is null
        defaultBloodGlucoseSummaryShouldNotBeFound("startTime.specified=false");
    }

    @Test
    @Transactional
    void getAllBloodGlucoseSummariesByEndTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        bloodGlucoseSummaryRepository.saveAndFlush(bloodGlucoseSummary);

        // Get all the bloodGlucoseSummaryList where endTime equals to DEFAULT_END_TIME
        defaultBloodGlucoseSummaryShouldBeFound("endTime.equals=" + DEFAULT_END_TIME);

        // Get all the bloodGlucoseSummaryList where endTime equals to UPDATED_END_TIME
        defaultBloodGlucoseSummaryShouldNotBeFound("endTime.equals=" + UPDATED_END_TIME);
    }

    @Test
    @Transactional
    void getAllBloodGlucoseSummariesByEndTimeIsInShouldWork() throws Exception {
        // Initialize the database
        bloodGlucoseSummaryRepository.saveAndFlush(bloodGlucoseSummary);

        // Get all the bloodGlucoseSummaryList where endTime in DEFAULT_END_TIME or UPDATED_END_TIME
        defaultBloodGlucoseSummaryShouldBeFound("endTime.in=" + DEFAULT_END_TIME + "," + UPDATED_END_TIME);

        // Get all the bloodGlucoseSummaryList where endTime equals to UPDATED_END_TIME
        defaultBloodGlucoseSummaryShouldNotBeFound("endTime.in=" + UPDATED_END_TIME);
    }

    @Test
    @Transactional
    void getAllBloodGlucoseSummariesByEndTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        bloodGlucoseSummaryRepository.saveAndFlush(bloodGlucoseSummary);

        // Get all the bloodGlucoseSummaryList where endTime is not null
        defaultBloodGlucoseSummaryShouldBeFound("endTime.specified=true");

        // Get all the bloodGlucoseSummaryList where endTime is null
        defaultBloodGlucoseSummaryShouldNotBeFound("endTime.specified=false");
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultBloodGlucoseSummaryShouldBeFound(String filter) throws Exception {
        restBloodGlucoseSummaryMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bloodGlucoseSummary.getId().toString())))
            .andExpect(jsonPath("$.[*].usuarioId").value(hasItem(DEFAULT_USUARIO_ID)))
            .andExpect(jsonPath("$.[*].empresaId").value(hasItem(DEFAULT_EMPRESA_ID)))
            .andExpect(jsonPath("$.[*].fieldAverage").value(hasItem(DEFAULT_FIELD_AVERAGE.doubleValue())))
            .andExpect(jsonPath("$.[*].fieldMax").value(hasItem(DEFAULT_FIELD_MAX.doubleValue())))
            .andExpect(jsonPath("$.[*].fieldMin").value(hasItem(DEFAULT_FIELD_MIN.doubleValue())))
            .andExpect(jsonPath("$.[*].intervalFood").value(hasItem(DEFAULT_INTERVAL_FOOD)))
            .andExpect(jsonPath("$.[*].mealType").value(hasItem(DEFAULT_MEAL_TYPE.toString())))
            .andExpect(jsonPath("$.[*].relationTemporalSleep").value(hasItem(DEFAULT_RELATION_TEMPORAL_SLEEP)))
            .andExpect(jsonPath("$.[*].sampleSource").value(hasItem(DEFAULT_SAMPLE_SOURCE)))
            .andExpect(jsonPath("$.[*].startTime").value(hasItem(DEFAULT_START_TIME.toString())))
            .andExpect(jsonPath("$.[*].endTime").value(hasItem(DEFAULT_END_TIME.toString())));

        // Check, that the count call also returns 1
        restBloodGlucoseSummaryMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultBloodGlucoseSummaryShouldNotBeFound(String filter) throws Exception {
        restBloodGlucoseSummaryMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restBloodGlucoseSummaryMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingBloodGlucoseSummary() throws Exception {
        // Get the bloodGlucoseSummary
        restBloodGlucoseSummaryMockMvc.perform(get(ENTITY_API_URL_ID, UUID.randomUUID().toString())).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingBloodGlucoseSummary() throws Exception {
        // Initialize the database
        bloodGlucoseSummaryRepository.saveAndFlush(bloodGlucoseSummary);

        int databaseSizeBeforeUpdate = bloodGlucoseSummaryRepository.findAll().size();

        // Update the bloodGlucoseSummary
        BloodGlucoseSummary updatedBloodGlucoseSummary = bloodGlucoseSummaryRepository.findById(bloodGlucoseSummary.getId()).get();
        // Disconnect from session so that the updates on updatedBloodGlucoseSummary are not directly saved in db
        em.detach(updatedBloodGlucoseSummary);
        updatedBloodGlucoseSummary
            .usuarioId(UPDATED_USUARIO_ID)
            .empresaId(UPDATED_EMPRESA_ID)
            .fieldAverage(UPDATED_FIELD_AVERAGE)
            .fieldMax(UPDATED_FIELD_MAX)
            .fieldMin(UPDATED_FIELD_MIN)
            .intervalFood(UPDATED_INTERVAL_FOOD)
            .mealType(UPDATED_MEAL_TYPE)
            .relationTemporalSleep(UPDATED_RELATION_TEMPORAL_SLEEP)
            .sampleSource(UPDATED_SAMPLE_SOURCE)
            .startTime(UPDATED_START_TIME)
            .endTime(UPDATED_END_TIME);

        restBloodGlucoseSummaryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedBloodGlucoseSummary.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedBloodGlucoseSummary))
            )
            .andExpect(status().isOk());

        // Validate the BloodGlucoseSummary in the database
        List<BloodGlucoseSummary> bloodGlucoseSummaryList = bloodGlucoseSummaryRepository.findAll();
        assertThat(bloodGlucoseSummaryList).hasSize(databaseSizeBeforeUpdate);
        BloodGlucoseSummary testBloodGlucoseSummary = bloodGlucoseSummaryList.get(bloodGlucoseSummaryList.size() - 1);
        assertThat(testBloodGlucoseSummary.getUsuarioId()).isEqualTo(UPDATED_USUARIO_ID);
        assertThat(testBloodGlucoseSummary.getEmpresaId()).isEqualTo(UPDATED_EMPRESA_ID);
        assertThat(testBloodGlucoseSummary.getFieldAverage()).isEqualTo(UPDATED_FIELD_AVERAGE);
        assertThat(testBloodGlucoseSummary.getFieldMax()).isEqualTo(UPDATED_FIELD_MAX);
        assertThat(testBloodGlucoseSummary.getFieldMin()).isEqualTo(UPDATED_FIELD_MIN);
        assertThat(testBloodGlucoseSummary.getIntervalFood()).isEqualTo(UPDATED_INTERVAL_FOOD);
        assertThat(testBloodGlucoseSummary.getMealType()).isEqualTo(UPDATED_MEAL_TYPE);
        assertThat(testBloodGlucoseSummary.getRelationTemporalSleep()).isEqualTo(UPDATED_RELATION_TEMPORAL_SLEEP);
        assertThat(testBloodGlucoseSummary.getSampleSource()).isEqualTo(UPDATED_SAMPLE_SOURCE);
        assertThat(testBloodGlucoseSummary.getStartTime()).isEqualTo(UPDATED_START_TIME);
        assertThat(testBloodGlucoseSummary.getEndTime()).isEqualTo(UPDATED_END_TIME);
    }

    @Test
    @Transactional
    void putNonExistingBloodGlucoseSummary() throws Exception {
        int databaseSizeBeforeUpdate = bloodGlucoseSummaryRepository.findAll().size();
        bloodGlucoseSummary.setId(UUID.randomUUID());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBloodGlucoseSummaryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, bloodGlucoseSummary.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bloodGlucoseSummary))
            )
            .andExpect(status().isBadRequest());

        // Validate the BloodGlucoseSummary in the database
        List<BloodGlucoseSummary> bloodGlucoseSummaryList = bloodGlucoseSummaryRepository.findAll();
        assertThat(bloodGlucoseSummaryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchBloodGlucoseSummary() throws Exception {
        int databaseSizeBeforeUpdate = bloodGlucoseSummaryRepository.findAll().size();
        bloodGlucoseSummary.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBloodGlucoseSummaryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bloodGlucoseSummary))
            )
            .andExpect(status().isBadRequest());

        // Validate the BloodGlucoseSummary in the database
        List<BloodGlucoseSummary> bloodGlucoseSummaryList = bloodGlucoseSummaryRepository.findAll();
        assertThat(bloodGlucoseSummaryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamBloodGlucoseSummary() throws Exception {
        int databaseSizeBeforeUpdate = bloodGlucoseSummaryRepository.findAll().size();
        bloodGlucoseSummary.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBloodGlucoseSummaryMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bloodGlucoseSummary))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the BloodGlucoseSummary in the database
        List<BloodGlucoseSummary> bloodGlucoseSummaryList = bloodGlucoseSummaryRepository.findAll();
        assertThat(bloodGlucoseSummaryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateBloodGlucoseSummaryWithPatch() throws Exception {
        // Initialize the database
        bloodGlucoseSummaryRepository.saveAndFlush(bloodGlucoseSummary);

        int databaseSizeBeforeUpdate = bloodGlucoseSummaryRepository.findAll().size();

        // Update the bloodGlucoseSummary using partial update
        BloodGlucoseSummary partialUpdatedBloodGlucoseSummary = new BloodGlucoseSummary();
        partialUpdatedBloodGlucoseSummary.setId(bloodGlucoseSummary.getId());

        partialUpdatedBloodGlucoseSummary
            .empresaId(UPDATED_EMPRESA_ID)
            .fieldAverage(UPDATED_FIELD_AVERAGE)
            .fieldMax(UPDATED_FIELD_MAX)
            .intervalFood(UPDATED_INTERVAL_FOOD)
            .sampleSource(UPDATED_SAMPLE_SOURCE)
            .endTime(UPDATED_END_TIME);

        restBloodGlucoseSummaryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBloodGlucoseSummary.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBloodGlucoseSummary))
            )
            .andExpect(status().isOk());

        // Validate the BloodGlucoseSummary in the database
        List<BloodGlucoseSummary> bloodGlucoseSummaryList = bloodGlucoseSummaryRepository.findAll();
        assertThat(bloodGlucoseSummaryList).hasSize(databaseSizeBeforeUpdate);
        BloodGlucoseSummary testBloodGlucoseSummary = bloodGlucoseSummaryList.get(bloodGlucoseSummaryList.size() - 1);
        assertThat(testBloodGlucoseSummary.getUsuarioId()).isEqualTo(DEFAULT_USUARIO_ID);
        assertThat(testBloodGlucoseSummary.getEmpresaId()).isEqualTo(UPDATED_EMPRESA_ID);
        assertThat(testBloodGlucoseSummary.getFieldAverage()).isEqualTo(UPDATED_FIELD_AVERAGE);
        assertThat(testBloodGlucoseSummary.getFieldMax()).isEqualTo(UPDATED_FIELD_MAX);
        assertThat(testBloodGlucoseSummary.getFieldMin()).isEqualTo(DEFAULT_FIELD_MIN);
        assertThat(testBloodGlucoseSummary.getIntervalFood()).isEqualTo(UPDATED_INTERVAL_FOOD);
        assertThat(testBloodGlucoseSummary.getMealType()).isEqualTo(DEFAULT_MEAL_TYPE);
        assertThat(testBloodGlucoseSummary.getRelationTemporalSleep()).isEqualTo(DEFAULT_RELATION_TEMPORAL_SLEEP);
        assertThat(testBloodGlucoseSummary.getSampleSource()).isEqualTo(UPDATED_SAMPLE_SOURCE);
        assertThat(testBloodGlucoseSummary.getStartTime()).isEqualTo(DEFAULT_START_TIME);
        assertThat(testBloodGlucoseSummary.getEndTime()).isEqualTo(UPDATED_END_TIME);
    }

    @Test
    @Transactional
    void fullUpdateBloodGlucoseSummaryWithPatch() throws Exception {
        // Initialize the database
        bloodGlucoseSummaryRepository.saveAndFlush(bloodGlucoseSummary);

        int databaseSizeBeforeUpdate = bloodGlucoseSummaryRepository.findAll().size();

        // Update the bloodGlucoseSummary using partial update
        BloodGlucoseSummary partialUpdatedBloodGlucoseSummary = new BloodGlucoseSummary();
        partialUpdatedBloodGlucoseSummary.setId(bloodGlucoseSummary.getId());

        partialUpdatedBloodGlucoseSummary
            .usuarioId(UPDATED_USUARIO_ID)
            .empresaId(UPDATED_EMPRESA_ID)
            .fieldAverage(UPDATED_FIELD_AVERAGE)
            .fieldMax(UPDATED_FIELD_MAX)
            .fieldMin(UPDATED_FIELD_MIN)
            .intervalFood(UPDATED_INTERVAL_FOOD)
            .mealType(UPDATED_MEAL_TYPE)
            .relationTemporalSleep(UPDATED_RELATION_TEMPORAL_SLEEP)
            .sampleSource(UPDATED_SAMPLE_SOURCE)
            .startTime(UPDATED_START_TIME)
            .endTime(UPDATED_END_TIME);

        restBloodGlucoseSummaryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBloodGlucoseSummary.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBloodGlucoseSummary))
            )
            .andExpect(status().isOk());

        // Validate the BloodGlucoseSummary in the database
        List<BloodGlucoseSummary> bloodGlucoseSummaryList = bloodGlucoseSummaryRepository.findAll();
        assertThat(bloodGlucoseSummaryList).hasSize(databaseSizeBeforeUpdate);
        BloodGlucoseSummary testBloodGlucoseSummary = bloodGlucoseSummaryList.get(bloodGlucoseSummaryList.size() - 1);
        assertThat(testBloodGlucoseSummary.getUsuarioId()).isEqualTo(UPDATED_USUARIO_ID);
        assertThat(testBloodGlucoseSummary.getEmpresaId()).isEqualTo(UPDATED_EMPRESA_ID);
        assertThat(testBloodGlucoseSummary.getFieldAverage()).isEqualTo(UPDATED_FIELD_AVERAGE);
        assertThat(testBloodGlucoseSummary.getFieldMax()).isEqualTo(UPDATED_FIELD_MAX);
        assertThat(testBloodGlucoseSummary.getFieldMin()).isEqualTo(UPDATED_FIELD_MIN);
        assertThat(testBloodGlucoseSummary.getIntervalFood()).isEqualTo(UPDATED_INTERVAL_FOOD);
        assertThat(testBloodGlucoseSummary.getMealType()).isEqualTo(UPDATED_MEAL_TYPE);
        assertThat(testBloodGlucoseSummary.getRelationTemporalSleep()).isEqualTo(UPDATED_RELATION_TEMPORAL_SLEEP);
        assertThat(testBloodGlucoseSummary.getSampleSource()).isEqualTo(UPDATED_SAMPLE_SOURCE);
        assertThat(testBloodGlucoseSummary.getStartTime()).isEqualTo(UPDATED_START_TIME);
        assertThat(testBloodGlucoseSummary.getEndTime()).isEqualTo(UPDATED_END_TIME);
    }

    @Test
    @Transactional
    void patchNonExistingBloodGlucoseSummary() throws Exception {
        int databaseSizeBeforeUpdate = bloodGlucoseSummaryRepository.findAll().size();
        bloodGlucoseSummary.setId(UUID.randomUUID());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBloodGlucoseSummaryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, bloodGlucoseSummary.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(bloodGlucoseSummary))
            )
            .andExpect(status().isBadRequest());

        // Validate the BloodGlucoseSummary in the database
        List<BloodGlucoseSummary> bloodGlucoseSummaryList = bloodGlucoseSummaryRepository.findAll();
        assertThat(bloodGlucoseSummaryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchBloodGlucoseSummary() throws Exception {
        int databaseSizeBeforeUpdate = bloodGlucoseSummaryRepository.findAll().size();
        bloodGlucoseSummary.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBloodGlucoseSummaryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(bloodGlucoseSummary))
            )
            .andExpect(status().isBadRequest());

        // Validate the BloodGlucoseSummary in the database
        List<BloodGlucoseSummary> bloodGlucoseSummaryList = bloodGlucoseSummaryRepository.findAll();
        assertThat(bloodGlucoseSummaryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamBloodGlucoseSummary() throws Exception {
        int databaseSizeBeforeUpdate = bloodGlucoseSummaryRepository.findAll().size();
        bloodGlucoseSummary.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBloodGlucoseSummaryMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(bloodGlucoseSummary))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the BloodGlucoseSummary in the database
        List<BloodGlucoseSummary> bloodGlucoseSummaryList = bloodGlucoseSummaryRepository.findAll();
        assertThat(bloodGlucoseSummaryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteBloodGlucoseSummary() throws Exception {
        // Initialize the database
        bloodGlucoseSummaryRepository.saveAndFlush(bloodGlucoseSummary);

        int databaseSizeBeforeDelete = bloodGlucoseSummaryRepository.findAll().size();

        // Delete the bloodGlucoseSummary
        restBloodGlucoseSummaryMockMvc
            .perform(delete(ENTITY_API_URL_ID, bloodGlucoseSummary.getId().toString()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<BloodGlucoseSummary> bloodGlucoseSummaryList = bloodGlucoseSummaryRepository.findAll();
        assertThat(bloodGlucoseSummaryList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
