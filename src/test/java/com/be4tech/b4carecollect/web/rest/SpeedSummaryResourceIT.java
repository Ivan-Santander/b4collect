package com.be4tech.b4carecollect.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.be4tech.b4carecollect.IntegrationTest;
import com.be4tech.b4carecollect.domain.SpeedSummary;
import com.be4tech.b4carecollect.repository.SpeedSummaryRepository;
import com.be4tech.b4carecollect.service.criteria.SpeedSummaryCriteria;
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
 * Integration tests for the {@link SpeedSummaryResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SpeedSummaryResourceIT {

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

    private static final Instant DEFAULT_START_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_START_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_END_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_END_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/speed-summaries";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private SpeedSummaryRepository speedSummaryRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSpeedSummaryMockMvc;

    private SpeedSummary speedSummary;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SpeedSummary createEntity(EntityManager em) {
        SpeedSummary speedSummary = new SpeedSummary()
            .usuarioId(DEFAULT_USUARIO_ID)
            .empresaId(DEFAULT_EMPRESA_ID)
            .fieldAverage(DEFAULT_FIELD_AVERAGE)
            .fieldMax(DEFAULT_FIELD_MAX)
            .fieldMin(DEFAULT_FIELD_MIN)
            .startTime(DEFAULT_START_TIME)
            .endTime(DEFAULT_END_TIME);
        return speedSummary;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SpeedSummary createUpdatedEntity(EntityManager em) {
        SpeedSummary speedSummary = new SpeedSummary()
            .usuarioId(UPDATED_USUARIO_ID)
            .empresaId(UPDATED_EMPRESA_ID)
            .fieldAverage(UPDATED_FIELD_AVERAGE)
            .fieldMax(UPDATED_FIELD_MAX)
            .fieldMin(UPDATED_FIELD_MIN)
            .startTime(UPDATED_START_TIME)
            .endTime(UPDATED_END_TIME);
        return speedSummary;
    }

    @BeforeEach
    public void initTest() {
        speedSummary = createEntity(em);
    }

    @Test
    @Transactional
    void createSpeedSummary() throws Exception {
        int databaseSizeBeforeCreate = speedSummaryRepository.findAll().size();
        // Create the SpeedSummary
        restSpeedSummaryMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(speedSummary)))
            .andExpect(status().isCreated());

        // Validate the SpeedSummary in the database
        List<SpeedSummary> speedSummaryList = speedSummaryRepository.findAll();
        assertThat(speedSummaryList).hasSize(databaseSizeBeforeCreate + 1);
        SpeedSummary testSpeedSummary = speedSummaryList.get(speedSummaryList.size() - 1);
        assertThat(testSpeedSummary.getUsuarioId()).isEqualTo(DEFAULT_USUARIO_ID);
        assertThat(testSpeedSummary.getEmpresaId()).isEqualTo(DEFAULT_EMPRESA_ID);
        assertThat(testSpeedSummary.getFieldAverage()).isEqualTo(DEFAULT_FIELD_AVERAGE);
        assertThat(testSpeedSummary.getFieldMax()).isEqualTo(DEFAULT_FIELD_MAX);
        assertThat(testSpeedSummary.getFieldMin()).isEqualTo(DEFAULT_FIELD_MIN);
        assertThat(testSpeedSummary.getStartTime()).isEqualTo(DEFAULT_START_TIME);
        assertThat(testSpeedSummary.getEndTime()).isEqualTo(DEFAULT_END_TIME);
    }

    @Test
    @Transactional
    void createSpeedSummaryWithExistingId() throws Exception {
        // Create the SpeedSummary with an existing ID
        speedSummaryRepository.saveAndFlush(speedSummary);

        int databaseSizeBeforeCreate = speedSummaryRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSpeedSummaryMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(speedSummary)))
            .andExpect(status().isBadRequest());

        // Validate the SpeedSummary in the database
        List<SpeedSummary> speedSummaryList = speedSummaryRepository.findAll();
        assertThat(speedSummaryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllSpeedSummaries() throws Exception {
        // Initialize the database
        speedSummaryRepository.saveAndFlush(speedSummary);

        // Get all the speedSummaryList
        restSpeedSummaryMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(speedSummary.getId().toString())))
            .andExpect(jsonPath("$.[*].usuarioId").value(hasItem(DEFAULT_USUARIO_ID)))
            .andExpect(jsonPath("$.[*].empresaId").value(hasItem(DEFAULT_EMPRESA_ID)))
            .andExpect(jsonPath("$.[*].fieldAverage").value(hasItem(DEFAULT_FIELD_AVERAGE.doubleValue())))
            .andExpect(jsonPath("$.[*].fieldMax").value(hasItem(DEFAULT_FIELD_MAX.doubleValue())))
            .andExpect(jsonPath("$.[*].fieldMin").value(hasItem(DEFAULT_FIELD_MIN.doubleValue())))
            .andExpect(jsonPath("$.[*].startTime").value(hasItem(DEFAULT_START_TIME.toString())))
            .andExpect(jsonPath("$.[*].endTime").value(hasItem(DEFAULT_END_TIME.toString())));
    }

    @Test
    @Transactional
    void getSpeedSummary() throws Exception {
        // Initialize the database
        speedSummaryRepository.saveAndFlush(speedSummary);

        // Get the speedSummary
        restSpeedSummaryMockMvc
            .perform(get(ENTITY_API_URL_ID, speedSummary.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(speedSummary.getId().toString()))
            .andExpect(jsonPath("$.usuarioId").value(DEFAULT_USUARIO_ID))
            .andExpect(jsonPath("$.empresaId").value(DEFAULT_EMPRESA_ID))
            .andExpect(jsonPath("$.fieldAverage").value(DEFAULT_FIELD_AVERAGE.doubleValue()))
            .andExpect(jsonPath("$.fieldMax").value(DEFAULT_FIELD_MAX.doubleValue()))
            .andExpect(jsonPath("$.fieldMin").value(DEFAULT_FIELD_MIN.doubleValue()))
            .andExpect(jsonPath("$.startTime").value(DEFAULT_START_TIME.toString()))
            .andExpect(jsonPath("$.endTime").value(DEFAULT_END_TIME.toString()));
    }

    @Test
    @Transactional
    void getSpeedSummariesByIdFiltering() throws Exception {
        // Initialize the database
        speedSummaryRepository.saveAndFlush(speedSummary);

        UUID id = speedSummary.getId();

        defaultSpeedSummaryShouldBeFound("id.equals=" + id);
        defaultSpeedSummaryShouldNotBeFound("id.notEquals=" + id);
    }

    @Test
    @Transactional
    void getAllSpeedSummariesByUsuarioIdIsEqualToSomething() throws Exception {
        // Initialize the database
        speedSummaryRepository.saveAndFlush(speedSummary);

        // Get all the speedSummaryList where usuarioId equals to DEFAULT_USUARIO_ID
        defaultSpeedSummaryShouldBeFound("usuarioId.equals=" + DEFAULT_USUARIO_ID);

        // Get all the speedSummaryList where usuarioId equals to UPDATED_USUARIO_ID
        defaultSpeedSummaryShouldNotBeFound("usuarioId.equals=" + UPDATED_USUARIO_ID);
    }

    @Test
    @Transactional
    void getAllSpeedSummariesByUsuarioIdIsInShouldWork() throws Exception {
        // Initialize the database
        speedSummaryRepository.saveAndFlush(speedSummary);

        // Get all the speedSummaryList where usuarioId in DEFAULT_USUARIO_ID or UPDATED_USUARIO_ID
        defaultSpeedSummaryShouldBeFound("usuarioId.in=" + DEFAULT_USUARIO_ID + "," + UPDATED_USUARIO_ID);

        // Get all the speedSummaryList where usuarioId equals to UPDATED_USUARIO_ID
        defaultSpeedSummaryShouldNotBeFound("usuarioId.in=" + UPDATED_USUARIO_ID);
    }

    @Test
    @Transactional
    void getAllSpeedSummariesByUsuarioIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        speedSummaryRepository.saveAndFlush(speedSummary);

        // Get all the speedSummaryList where usuarioId is not null
        defaultSpeedSummaryShouldBeFound("usuarioId.specified=true");

        // Get all the speedSummaryList where usuarioId is null
        defaultSpeedSummaryShouldNotBeFound("usuarioId.specified=false");
    }

    @Test
    @Transactional
    void getAllSpeedSummariesByUsuarioIdContainsSomething() throws Exception {
        // Initialize the database
        speedSummaryRepository.saveAndFlush(speedSummary);

        // Get all the speedSummaryList where usuarioId contains DEFAULT_USUARIO_ID
        defaultSpeedSummaryShouldBeFound("usuarioId.contains=" + DEFAULT_USUARIO_ID);

        // Get all the speedSummaryList where usuarioId contains UPDATED_USUARIO_ID
        defaultSpeedSummaryShouldNotBeFound("usuarioId.contains=" + UPDATED_USUARIO_ID);
    }

    @Test
    @Transactional
    void getAllSpeedSummariesByUsuarioIdNotContainsSomething() throws Exception {
        // Initialize the database
        speedSummaryRepository.saveAndFlush(speedSummary);

        // Get all the speedSummaryList where usuarioId does not contain DEFAULT_USUARIO_ID
        defaultSpeedSummaryShouldNotBeFound("usuarioId.doesNotContain=" + DEFAULT_USUARIO_ID);

        // Get all the speedSummaryList where usuarioId does not contain UPDATED_USUARIO_ID
        defaultSpeedSummaryShouldBeFound("usuarioId.doesNotContain=" + UPDATED_USUARIO_ID);
    }

    @Test
    @Transactional
    void getAllSpeedSummariesByEmpresaIdIsEqualToSomething() throws Exception {
        // Initialize the database
        speedSummaryRepository.saveAndFlush(speedSummary);

        // Get all the speedSummaryList where empresaId equals to DEFAULT_EMPRESA_ID
        defaultSpeedSummaryShouldBeFound("empresaId.equals=" + DEFAULT_EMPRESA_ID);

        // Get all the speedSummaryList where empresaId equals to UPDATED_EMPRESA_ID
        defaultSpeedSummaryShouldNotBeFound("empresaId.equals=" + UPDATED_EMPRESA_ID);
    }

    @Test
    @Transactional
    void getAllSpeedSummariesByEmpresaIdIsInShouldWork() throws Exception {
        // Initialize the database
        speedSummaryRepository.saveAndFlush(speedSummary);

        // Get all the speedSummaryList where empresaId in DEFAULT_EMPRESA_ID or UPDATED_EMPRESA_ID
        defaultSpeedSummaryShouldBeFound("empresaId.in=" + DEFAULT_EMPRESA_ID + "," + UPDATED_EMPRESA_ID);

        // Get all the speedSummaryList where empresaId equals to UPDATED_EMPRESA_ID
        defaultSpeedSummaryShouldNotBeFound("empresaId.in=" + UPDATED_EMPRESA_ID);
    }

    @Test
    @Transactional
    void getAllSpeedSummariesByEmpresaIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        speedSummaryRepository.saveAndFlush(speedSummary);

        // Get all the speedSummaryList where empresaId is not null
        defaultSpeedSummaryShouldBeFound("empresaId.specified=true");

        // Get all the speedSummaryList where empresaId is null
        defaultSpeedSummaryShouldNotBeFound("empresaId.specified=false");
    }

    @Test
    @Transactional
    void getAllSpeedSummariesByEmpresaIdContainsSomething() throws Exception {
        // Initialize the database
        speedSummaryRepository.saveAndFlush(speedSummary);

        // Get all the speedSummaryList where empresaId contains DEFAULT_EMPRESA_ID
        defaultSpeedSummaryShouldBeFound("empresaId.contains=" + DEFAULT_EMPRESA_ID);

        // Get all the speedSummaryList where empresaId contains UPDATED_EMPRESA_ID
        defaultSpeedSummaryShouldNotBeFound("empresaId.contains=" + UPDATED_EMPRESA_ID);
    }

    @Test
    @Transactional
    void getAllSpeedSummariesByEmpresaIdNotContainsSomething() throws Exception {
        // Initialize the database
        speedSummaryRepository.saveAndFlush(speedSummary);

        // Get all the speedSummaryList where empresaId does not contain DEFAULT_EMPRESA_ID
        defaultSpeedSummaryShouldNotBeFound("empresaId.doesNotContain=" + DEFAULT_EMPRESA_ID);

        // Get all the speedSummaryList where empresaId does not contain UPDATED_EMPRESA_ID
        defaultSpeedSummaryShouldBeFound("empresaId.doesNotContain=" + UPDATED_EMPRESA_ID);
    }

    @Test
    @Transactional
    void getAllSpeedSummariesByFieldAverageIsEqualToSomething() throws Exception {
        // Initialize the database
        speedSummaryRepository.saveAndFlush(speedSummary);

        // Get all the speedSummaryList where fieldAverage equals to DEFAULT_FIELD_AVERAGE
        defaultSpeedSummaryShouldBeFound("fieldAverage.equals=" + DEFAULT_FIELD_AVERAGE);

        // Get all the speedSummaryList where fieldAverage equals to UPDATED_FIELD_AVERAGE
        defaultSpeedSummaryShouldNotBeFound("fieldAverage.equals=" + UPDATED_FIELD_AVERAGE);
    }

    @Test
    @Transactional
    void getAllSpeedSummariesByFieldAverageIsInShouldWork() throws Exception {
        // Initialize the database
        speedSummaryRepository.saveAndFlush(speedSummary);

        // Get all the speedSummaryList where fieldAverage in DEFAULT_FIELD_AVERAGE or UPDATED_FIELD_AVERAGE
        defaultSpeedSummaryShouldBeFound("fieldAverage.in=" + DEFAULT_FIELD_AVERAGE + "," + UPDATED_FIELD_AVERAGE);

        // Get all the speedSummaryList where fieldAverage equals to UPDATED_FIELD_AVERAGE
        defaultSpeedSummaryShouldNotBeFound("fieldAverage.in=" + UPDATED_FIELD_AVERAGE);
    }

    @Test
    @Transactional
    void getAllSpeedSummariesByFieldAverageIsNullOrNotNull() throws Exception {
        // Initialize the database
        speedSummaryRepository.saveAndFlush(speedSummary);

        // Get all the speedSummaryList where fieldAverage is not null
        defaultSpeedSummaryShouldBeFound("fieldAverage.specified=true");

        // Get all the speedSummaryList where fieldAverage is null
        defaultSpeedSummaryShouldNotBeFound("fieldAverage.specified=false");
    }

    @Test
    @Transactional
    void getAllSpeedSummariesByFieldAverageIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        speedSummaryRepository.saveAndFlush(speedSummary);

        // Get all the speedSummaryList where fieldAverage is greater than or equal to DEFAULT_FIELD_AVERAGE
        defaultSpeedSummaryShouldBeFound("fieldAverage.greaterThanOrEqual=" + DEFAULT_FIELD_AVERAGE);

        // Get all the speedSummaryList where fieldAverage is greater than or equal to UPDATED_FIELD_AVERAGE
        defaultSpeedSummaryShouldNotBeFound("fieldAverage.greaterThanOrEqual=" + UPDATED_FIELD_AVERAGE);
    }

    @Test
    @Transactional
    void getAllSpeedSummariesByFieldAverageIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        speedSummaryRepository.saveAndFlush(speedSummary);

        // Get all the speedSummaryList where fieldAverage is less than or equal to DEFAULT_FIELD_AVERAGE
        defaultSpeedSummaryShouldBeFound("fieldAverage.lessThanOrEqual=" + DEFAULT_FIELD_AVERAGE);

        // Get all the speedSummaryList where fieldAverage is less than or equal to SMALLER_FIELD_AVERAGE
        defaultSpeedSummaryShouldNotBeFound("fieldAverage.lessThanOrEqual=" + SMALLER_FIELD_AVERAGE);
    }

    @Test
    @Transactional
    void getAllSpeedSummariesByFieldAverageIsLessThanSomething() throws Exception {
        // Initialize the database
        speedSummaryRepository.saveAndFlush(speedSummary);

        // Get all the speedSummaryList where fieldAverage is less than DEFAULT_FIELD_AVERAGE
        defaultSpeedSummaryShouldNotBeFound("fieldAverage.lessThan=" + DEFAULT_FIELD_AVERAGE);

        // Get all the speedSummaryList where fieldAverage is less than UPDATED_FIELD_AVERAGE
        defaultSpeedSummaryShouldBeFound("fieldAverage.lessThan=" + UPDATED_FIELD_AVERAGE);
    }

    @Test
    @Transactional
    void getAllSpeedSummariesByFieldAverageIsGreaterThanSomething() throws Exception {
        // Initialize the database
        speedSummaryRepository.saveAndFlush(speedSummary);

        // Get all the speedSummaryList where fieldAverage is greater than DEFAULT_FIELD_AVERAGE
        defaultSpeedSummaryShouldNotBeFound("fieldAverage.greaterThan=" + DEFAULT_FIELD_AVERAGE);

        // Get all the speedSummaryList where fieldAverage is greater than SMALLER_FIELD_AVERAGE
        defaultSpeedSummaryShouldBeFound("fieldAverage.greaterThan=" + SMALLER_FIELD_AVERAGE);
    }

    @Test
    @Transactional
    void getAllSpeedSummariesByFieldMaxIsEqualToSomething() throws Exception {
        // Initialize the database
        speedSummaryRepository.saveAndFlush(speedSummary);

        // Get all the speedSummaryList where fieldMax equals to DEFAULT_FIELD_MAX
        defaultSpeedSummaryShouldBeFound("fieldMax.equals=" + DEFAULT_FIELD_MAX);

        // Get all the speedSummaryList where fieldMax equals to UPDATED_FIELD_MAX
        defaultSpeedSummaryShouldNotBeFound("fieldMax.equals=" + UPDATED_FIELD_MAX);
    }

    @Test
    @Transactional
    void getAllSpeedSummariesByFieldMaxIsInShouldWork() throws Exception {
        // Initialize the database
        speedSummaryRepository.saveAndFlush(speedSummary);

        // Get all the speedSummaryList where fieldMax in DEFAULT_FIELD_MAX or UPDATED_FIELD_MAX
        defaultSpeedSummaryShouldBeFound("fieldMax.in=" + DEFAULT_FIELD_MAX + "," + UPDATED_FIELD_MAX);

        // Get all the speedSummaryList where fieldMax equals to UPDATED_FIELD_MAX
        defaultSpeedSummaryShouldNotBeFound("fieldMax.in=" + UPDATED_FIELD_MAX);
    }

    @Test
    @Transactional
    void getAllSpeedSummariesByFieldMaxIsNullOrNotNull() throws Exception {
        // Initialize the database
        speedSummaryRepository.saveAndFlush(speedSummary);

        // Get all the speedSummaryList where fieldMax is not null
        defaultSpeedSummaryShouldBeFound("fieldMax.specified=true");

        // Get all the speedSummaryList where fieldMax is null
        defaultSpeedSummaryShouldNotBeFound("fieldMax.specified=false");
    }

    @Test
    @Transactional
    void getAllSpeedSummariesByFieldMaxIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        speedSummaryRepository.saveAndFlush(speedSummary);

        // Get all the speedSummaryList where fieldMax is greater than or equal to DEFAULT_FIELD_MAX
        defaultSpeedSummaryShouldBeFound("fieldMax.greaterThanOrEqual=" + DEFAULT_FIELD_MAX);

        // Get all the speedSummaryList where fieldMax is greater than or equal to UPDATED_FIELD_MAX
        defaultSpeedSummaryShouldNotBeFound("fieldMax.greaterThanOrEqual=" + UPDATED_FIELD_MAX);
    }

    @Test
    @Transactional
    void getAllSpeedSummariesByFieldMaxIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        speedSummaryRepository.saveAndFlush(speedSummary);

        // Get all the speedSummaryList where fieldMax is less than or equal to DEFAULT_FIELD_MAX
        defaultSpeedSummaryShouldBeFound("fieldMax.lessThanOrEqual=" + DEFAULT_FIELD_MAX);

        // Get all the speedSummaryList where fieldMax is less than or equal to SMALLER_FIELD_MAX
        defaultSpeedSummaryShouldNotBeFound("fieldMax.lessThanOrEqual=" + SMALLER_FIELD_MAX);
    }

    @Test
    @Transactional
    void getAllSpeedSummariesByFieldMaxIsLessThanSomething() throws Exception {
        // Initialize the database
        speedSummaryRepository.saveAndFlush(speedSummary);

        // Get all the speedSummaryList where fieldMax is less than DEFAULT_FIELD_MAX
        defaultSpeedSummaryShouldNotBeFound("fieldMax.lessThan=" + DEFAULT_FIELD_MAX);

        // Get all the speedSummaryList where fieldMax is less than UPDATED_FIELD_MAX
        defaultSpeedSummaryShouldBeFound("fieldMax.lessThan=" + UPDATED_FIELD_MAX);
    }

    @Test
    @Transactional
    void getAllSpeedSummariesByFieldMaxIsGreaterThanSomething() throws Exception {
        // Initialize the database
        speedSummaryRepository.saveAndFlush(speedSummary);

        // Get all the speedSummaryList where fieldMax is greater than DEFAULT_FIELD_MAX
        defaultSpeedSummaryShouldNotBeFound("fieldMax.greaterThan=" + DEFAULT_FIELD_MAX);

        // Get all the speedSummaryList where fieldMax is greater than SMALLER_FIELD_MAX
        defaultSpeedSummaryShouldBeFound("fieldMax.greaterThan=" + SMALLER_FIELD_MAX);
    }

    @Test
    @Transactional
    void getAllSpeedSummariesByFieldMinIsEqualToSomething() throws Exception {
        // Initialize the database
        speedSummaryRepository.saveAndFlush(speedSummary);

        // Get all the speedSummaryList where fieldMin equals to DEFAULT_FIELD_MIN
        defaultSpeedSummaryShouldBeFound("fieldMin.equals=" + DEFAULT_FIELD_MIN);

        // Get all the speedSummaryList where fieldMin equals to UPDATED_FIELD_MIN
        defaultSpeedSummaryShouldNotBeFound("fieldMin.equals=" + UPDATED_FIELD_MIN);
    }

    @Test
    @Transactional
    void getAllSpeedSummariesByFieldMinIsInShouldWork() throws Exception {
        // Initialize the database
        speedSummaryRepository.saveAndFlush(speedSummary);

        // Get all the speedSummaryList where fieldMin in DEFAULT_FIELD_MIN or UPDATED_FIELD_MIN
        defaultSpeedSummaryShouldBeFound("fieldMin.in=" + DEFAULT_FIELD_MIN + "," + UPDATED_FIELD_MIN);

        // Get all the speedSummaryList where fieldMin equals to UPDATED_FIELD_MIN
        defaultSpeedSummaryShouldNotBeFound("fieldMin.in=" + UPDATED_FIELD_MIN);
    }

    @Test
    @Transactional
    void getAllSpeedSummariesByFieldMinIsNullOrNotNull() throws Exception {
        // Initialize the database
        speedSummaryRepository.saveAndFlush(speedSummary);

        // Get all the speedSummaryList where fieldMin is not null
        defaultSpeedSummaryShouldBeFound("fieldMin.specified=true");

        // Get all the speedSummaryList where fieldMin is null
        defaultSpeedSummaryShouldNotBeFound("fieldMin.specified=false");
    }

    @Test
    @Transactional
    void getAllSpeedSummariesByFieldMinIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        speedSummaryRepository.saveAndFlush(speedSummary);

        // Get all the speedSummaryList where fieldMin is greater than or equal to DEFAULT_FIELD_MIN
        defaultSpeedSummaryShouldBeFound("fieldMin.greaterThanOrEqual=" + DEFAULT_FIELD_MIN);

        // Get all the speedSummaryList where fieldMin is greater than or equal to UPDATED_FIELD_MIN
        defaultSpeedSummaryShouldNotBeFound("fieldMin.greaterThanOrEqual=" + UPDATED_FIELD_MIN);
    }

    @Test
    @Transactional
    void getAllSpeedSummariesByFieldMinIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        speedSummaryRepository.saveAndFlush(speedSummary);

        // Get all the speedSummaryList where fieldMin is less than or equal to DEFAULT_FIELD_MIN
        defaultSpeedSummaryShouldBeFound("fieldMin.lessThanOrEqual=" + DEFAULT_FIELD_MIN);

        // Get all the speedSummaryList where fieldMin is less than or equal to SMALLER_FIELD_MIN
        defaultSpeedSummaryShouldNotBeFound("fieldMin.lessThanOrEqual=" + SMALLER_FIELD_MIN);
    }

    @Test
    @Transactional
    void getAllSpeedSummariesByFieldMinIsLessThanSomething() throws Exception {
        // Initialize the database
        speedSummaryRepository.saveAndFlush(speedSummary);

        // Get all the speedSummaryList where fieldMin is less than DEFAULT_FIELD_MIN
        defaultSpeedSummaryShouldNotBeFound("fieldMin.lessThan=" + DEFAULT_FIELD_MIN);

        // Get all the speedSummaryList where fieldMin is less than UPDATED_FIELD_MIN
        defaultSpeedSummaryShouldBeFound("fieldMin.lessThan=" + UPDATED_FIELD_MIN);
    }

    @Test
    @Transactional
    void getAllSpeedSummariesByFieldMinIsGreaterThanSomething() throws Exception {
        // Initialize the database
        speedSummaryRepository.saveAndFlush(speedSummary);

        // Get all the speedSummaryList where fieldMin is greater than DEFAULT_FIELD_MIN
        defaultSpeedSummaryShouldNotBeFound("fieldMin.greaterThan=" + DEFAULT_FIELD_MIN);

        // Get all the speedSummaryList where fieldMin is greater than SMALLER_FIELD_MIN
        defaultSpeedSummaryShouldBeFound("fieldMin.greaterThan=" + SMALLER_FIELD_MIN);
    }

    @Test
    @Transactional
    void getAllSpeedSummariesByStartTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        speedSummaryRepository.saveAndFlush(speedSummary);

        // Get all the speedSummaryList where startTime equals to DEFAULT_START_TIME
        defaultSpeedSummaryShouldBeFound("startTime.equals=" + DEFAULT_START_TIME);

        // Get all the speedSummaryList where startTime equals to UPDATED_START_TIME
        defaultSpeedSummaryShouldNotBeFound("startTime.equals=" + UPDATED_START_TIME);
    }

    @Test
    @Transactional
    void getAllSpeedSummariesByStartTimeIsInShouldWork() throws Exception {
        // Initialize the database
        speedSummaryRepository.saveAndFlush(speedSummary);

        // Get all the speedSummaryList where startTime in DEFAULT_START_TIME or UPDATED_START_TIME
        defaultSpeedSummaryShouldBeFound("startTime.in=" + DEFAULT_START_TIME + "," + UPDATED_START_TIME);

        // Get all the speedSummaryList where startTime equals to UPDATED_START_TIME
        defaultSpeedSummaryShouldNotBeFound("startTime.in=" + UPDATED_START_TIME);
    }

    @Test
    @Transactional
    void getAllSpeedSummariesByStartTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        speedSummaryRepository.saveAndFlush(speedSummary);

        // Get all the speedSummaryList where startTime is not null
        defaultSpeedSummaryShouldBeFound("startTime.specified=true");

        // Get all the speedSummaryList where startTime is null
        defaultSpeedSummaryShouldNotBeFound("startTime.specified=false");
    }

    @Test
    @Transactional
    void getAllSpeedSummariesByEndTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        speedSummaryRepository.saveAndFlush(speedSummary);

        // Get all the speedSummaryList where endTime equals to DEFAULT_END_TIME
        defaultSpeedSummaryShouldBeFound("endTime.equals=" + DEFAULT_END_TIME);

        // Get all the speedSummaryList where endTime equals to UPDATED_END_TIME
        defaultSpeedSummaryShouldNotBeFound("endTime.equals=" + UPDATED_END_TIME);
    }

    @Test
    @Transactional
    void getAllSpeedSummariesByEndTimeIsInShouldWork() throws Exception {
        // Initialize the database
        speedSummaryRepository.saveAndFlush(speedSummary);

        // Get all the speedSummaryList where endTime in DEFAULT_END_TIME or UPDATED_END_TIME
        defaultSpeedSummaryShouldBeFound("endTime.in=" + DEFAULT_END_TIME + "," + UPDATED_END_TIME);

        // Get all the speedSummaryList where endTime equals to UPDATED_END_TIME
        defaultSpeedSummaryShouldNotBeFound("endTime.in=" + UPDATED_END_TIME);
    }

    @Test
    @Transactional
    void getAllSpeedSummariesByEndTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        speedSummaryRepository.saveAndFlush(speedSummary);

        // Get all the speedSummaryList where endTime is not null
        defaultSpeedSummaryShouldBeFound("endTime.specified=true");

        // Get all the speedSummaryList where endTime is null
        defaultSpeedSummaryShouldNotBeFound("endTime.specified=false");
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultSpeedSummaryShouldBeFound(String filter) throws Exception {
        restSpeedSummaryMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(speedSummary.getId().toString())))
            .andExpect(jsonPath("$.[*].usuarioId").value(hasItem(DEFAULT_USUARIO_ID)))
            .andExpect(jsonPath("$.[*].empresaId").value(hasItem(DEFAULT_EMPRESA_ID)))
            .andExpect(jsonPath("$.[*].fieldAverage").value(hasItem(DEFAULT_FIELD_AVERAGE.doubleValue())))
            .andExpect(jsonPath("$.[*].fieldMax").value(hasItem(DEFAULT_FIELD_MAX.doubleValue())))
            .andExpect(jsonPath("$.[*].fieldMin").value(hasItem(DEFAULT_FIELD_MIN.doubleValue())))
            .andExpect(jsonPath("$.[*].startTime").value(hasItem(DEFAULT_START_TIME.toString())))
            .andExpect(jsonPath("$.[*].endTime").value(hasItem(DEFAULT_END_TIME.toString())));

        // Check, that the count call also returns 1
        restSpeedSummaryMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultSpeedSummaryShouldNotBeFound(String filter) throws Exception {
        restSpeedSummaryMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restSpeedSummaryMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingSpeedSummary() throws Exception {
        // Get the speedSummary
        restSpeedSummaryMockMvc.perform(get(ENTITY_API_URL_ID, UUID.randomUUID().toString())).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingSpeedSummary() throws Exception {
        // Initialize the database
        speedSummaryRepository.saveAndFlush(speedSummary);

        int databaseSizeBeforeUpdate = speedSummaryRepository.findAll().size();

        // Update the speedSummary
        SpeedSummary updatedSpeedSummary = speedSummaryRepository.findById(speedSummary.getId()).get();
        // Disconnect from session so that the updates on updatedSpeedSummary are not directly saved in db
        em.detach(updatedSpeedSummary);
        updatedSpeedSummary
            .usuarioId(UPDATED_USUARIO_ID)
            .empresaId(UPDATED_EMPRESA_ID)
            .fieldAverage(UPDATED_FIELD_AVERAGE)
            .fieldMax(UPDATED_FIELD_MAX)
            .fieldMin(UPDATED_FIELD_MIN)
            .startTime(UPDATED_START_TIME)
            .endTime(UPDATED_END_TIME);

        restSpeedSummaryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedSpeedSummary.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedSpeedSummary))
            )
            .andExpect(status().isOk());

        // Validate the SpeedSummary in the database
        List<SpeedSummary> speedSummaryList = speedSummaryRepository.findAll();
        assertThat(speedSummaryList).hasSize(databaseSizeBeforeUpdate);
        SpeedSummary testSpeedSummary = speedSummaryList.get(speedSummaryList.size() - 1);
        assertThat(testSpeedSummary.getUsuarioId()).isEqualTo(UPDATED_USUARIO_ID);
        assertThat(testSpeedSummary.getEmpresaId()).isEqualTo(UPDATED_EMPRESA_ID);
        assertThat(testSpeedSummary.getFieldAverage()).isEqualTo(UPDATED_FIELD_AVERAGE);
        assertThat(testSpeedSummary.getFieldMax()).isEqualTo(UPDATED_FIELD_MAX);
        assertThat(testSpeedSummary.getFieldMin()).isEqualTo(UPDATED_FIELD_MIN);
        assertThat(testSpeedSummary.getStartTime()).isEqualTo(UPDATED_START_TIME);
        assertThat(testSpeedSummary.getEndTime()).isEqualTo(UPDATED_END_TIME);
    }

    @Test
    @Transactional
    void putNonExistingSpeedSummary() throws Exception {
        int databaseSizeBeforeUpdate = speedSummaryRepository.findAll().size();
        speedSummary.setId(UUID.randomUUID());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSpeedSummaryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, speedSummary.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(speedSummary))
            )
            .andExpect(status().isBadRequest());

        // Validate the SpeedSummary in the database
        List<SpeedSummary> speedSummaryList = speedSummaryRepository.findAll();
        assertThat(speedSummaryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSpeedSummary() throws Exception {
        int databaseSizeBeforeUpdate = speedSummaryRepository.findAll().size();
        speedSummary.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSpeedSummaryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(speedSummary))
            )
            .andExpect(status().isBadRequest());

        // Validate the SpeedSummary in the database
        List<SpeedSummary> speedSummaryList = speedSummaryRepository.findAll();
        assertThat(speedSummaryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSpeedSummary() throws Exception {
        int databaseSizeBeforeUpdate = speedSummaryRepository.findAll().size();
        speedSummary.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSpeedSummaryMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(speedSummary)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the SpeedSummary in the database
        List<SpeedSummary> speedSummaryList = speedSummaryRepository.findAll();
        assertThat(speedSummaryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSpeedSummaryWithPatch() throws Exception {
        // Initialize the database
        speedSummaryRepository.saveAndFlush(speedSummary);

        int databaseSizeBeforeUpdate = speedSummaryRepository.findAll().size();

        // Update the speedSummary using partial update
        SpeedSummary partialUpdatedSpeedSummary = new SpeedSummary();
        partialUpdatedSpeedSummary.setId(speedSummary.getId());

        partialUpdatedSpeedSummary.usuarioId(UPDATED_USUARIO_ID).fieldMin(UPDATED_FIELD_MIN);

        restSpeedSummaryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSpeedSummary.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSpeedSummary))
            )
            .andExpect(status().isOk());

        // Validate the SpeedSummary in the database
        List<SpeedSummary> speedSummaryList = speedSummaryRepository.findAll();
        assertThat(speedSummaryList).hasSize(databaseSizeBeforeUpdate);
        SpeedSummary testSpeedSummary = speedSummaryList.get(speedSummaryList.size() - 1);
        assertThat(testSpeedSummary.getUsuarioId()).isEqualTo(UPDATED_USUARIO_ID);
        assertThat(testSpeedSummary.getEmpresaId()).isEqualTo(DEFAULT_EMPRESA_ID);
        assertThat(testSpeedSummary.getFieldAverage()).isEqualTo(DEFAULT_FIELD_AVERAGE);
        assertThat(testSpeedSummary.getFieldMax()).isEqualTo(DEFAULT_FIELD_MAX);
        assertThat(testSpeedSummary.getFieldMin()).isEqualTo(UPDATED_FIELD_MIN);
        assertThat(testSpeedSummary.getStartTime()).isEqualTo(DEFAULT_START_TIME);
        assertThat(testSpeedSummary.getEndTime()).isEqualTo(DEFAULT_END_TIME);
    }

    @Test
    @Transactional
    void fullUpdateSpeedSummaryWithPatch() throws Exception {
        // Initialize the database
        speedSummaryRepository.saveAndFlush(speedSummary);

        int databaseSizeBeforeUpdate = speedSummaryRepository.findAll().size();

        // Update the speedSummary using partial update
        SpeedSummary partialUpdatedSpeedSummary = new SpeedSummary();
        partialUpdatedSpeedSummary.setId(speedSummary.getId());

        partialUpdatedSpeedSummary
            .usuarioId(UPDATED_USUARIO_ID)
            .empresaId(UPDATED_EMPRESA_ID)
            .fieldAverage(UPDATED_FIELD_AVERAGE)
            .fieldMax(UPDATED_FIELD_MAX)
            .fieldMin(UPDATED_FIELD_MIN)
            .startTime(UPDATED_START_TIME)
            .endTime(UPDATED_END_TIME);

        restSpeedSummaryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSpeedSummary.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSpeedSummary))
            )
            .andExpect(status().isOk());

        // Validate the SpeedSummary in the database
        List<SpeedSummary> speedSummaryList = speedSummaryRepository.findAll();
        assertThat(speedSummaryList).hasSize(databaseSizeBeforeUpdate);
        SpeedSummary testSpeedSummary = speedSummaryList.get(speedSummaryList.size() - 1);
        assertThat(testSpeedSummary.getUsuarioId()).isEqualTo(UPDATED_USUARIO_ID);
        assertThat(testSpeedSummary.getEmpresaId()).isEqualTo(UPDATED_EMPRESA_ID);
        assertThat(testSpeedSummary.getFieldAverage()).isEqualTo(UPDATED_FIELD_AVERAGE);
        assertThat(testSpeedSummary.getFieldMax()).isEqualTo(UPDATED_FIELD_MAX);
        assertThat(testSpeedSummary.getFieldMin()).isEqualTo(UPDATED_FIELD_MIN);
        assertThat(testSpeedSummary.getStartTime()).isEqualTo(UPDATED_START_TIME);
        assertThat(testSpeedSummary.getEndTime()).isEqualTo(UPDATED_END_TIME);
    }

    @Test
    @Transactional
    void patchNonExistingSpeedSummary() throws Exception {
        int databaseSizeBeforeUpdate = speedSummaryRepository.findAll().size();
        speedSummary.setId(UUID.randomUUID());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSpeedSummaryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, speedSummary.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(speedSummary))
            )
            .andExpect(status().isBadRequest());

        // Validate the SpeedSummary in the database
        List<SpeedSummary> speedSummaryList = speedSummaryRepository.findAll();
        assertThat(speedSummaryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSpeedSummary() throws Exception {
        int databaseSizeBeforeUpdate = speedSummaryRepository.findAll().size();
        speedSummary.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSpeedSummaryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(speedSummary))
            )
            .andExpect(status().isBadRequest());

        // Validate the SpeedSummary in the database
        List<SpeedSummary> speedSummaryList = speedSummaryRepository.findAll();
        assertThat(speedSummaryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSpeedSummary() throws Exception {
        int databaseSizeBeforeUpdate = speedSummaryRepository.findAll().size();
        speedSummary.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSpeedSummaryMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(speedSummary))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the SpeedSummary in the database
        List<SpeedSummary> speedSummaryList = speedSummaryRepository.findAll();
        assertThat(speedSummaryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSpeedSummary() throws Exception {
        // Initialize the database
        speedSummaryRepository.saveAndFlush(speedSummary);

        int databaseSizeBeforeDelete = speedSummaryRepository.findAll().size();

        // Delete the speedSummary
        restSpeedSummaryMockMvc
            .perform(delete(ENTITY_API_URL_ID, speedSummary.getId().toString()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SpeedSummary> speedSummaryList = speedSummaryRepository.findAll();
        assertThat(speedSummaryList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
