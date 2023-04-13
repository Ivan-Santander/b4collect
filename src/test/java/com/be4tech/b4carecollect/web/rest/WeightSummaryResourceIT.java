package com.be4tech.b4carecollect.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.be4tech.b4carecollect.IntegrationTest;
import com.be4tech.b4carecollect.domain.WeightSummary;
import com.be4tech.b4carecollect.repository.WeightSummaryRepository;
import com.be4tech.b4carecollect.service.criteria.WeightSummaryCriteria;
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
 * Integration tests for the {@link WeightSummaryResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class WeightSummaryResourceIT {

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

    private static final String ENTITY_API_URL = "/api/weight-summaries";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private WeightSummaryRepository weightSummaryRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restWeightSummaryMockMvc;

    private WeightSummary weightSummary;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static WeightSummary createEntity(EntityManager em) {
        WeightSummary weightSummary = new WeightSummary()
            .usuarioId(DEFAULT_USUARIO_ID)
            .empresaId(DEFAULT_EMPRESA_ID)
            .fieldAverage(DEFAULT_FIELD_AVERAGE)
            .fieldMax(DEFAULT_FIELD_MAX)
            .fieldMin(DEFAULT_FIELD_MIN)
            .startTime(DEFAULT_START_TIME)
            .endTime(DEFAULT_END_TIME);
        return weightSummary;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static WeightSummary createUpdatedEntity(EntityManager em) {
        WeightSummary weightSummary = new WeightSummary()
            .usuarioId(UPDATED_USUARIO_ID)
            .empresaId(UPDATED_EMPRESA_ID)
            .fieldAverage(UPDATED_FIELD_AVERAGE)
            .fieldMax(UPDATED_FIELD_MAX)
            .fieldMin(UPDATED_FIELD_MIN)
            .startTime(UPDATED_START_TIME)
            .endTime(UPDATED_END_TIME);
        return weightSummary;
    }

    @BeforeEach
    public void initTest() {
        weightSummary = createEntity(em);
    }

    @Test
    @Transactional
    void createWeightSummary() throws Exception {
        int databaseSizeBeforeCreate = weightSummaryRepository.findAll().size();
        // Create the WeightSummary
        restWeightSummaryMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(weightSummary)))
            .andExpect(status().isCreated());

        // Validate the WeightSummary in the database
        List<WeightSummary> weightSummaryList = weightSummaryRepository.findAll();
        assertThat(weightSummaryList).hasSize(databaseSizeBeforeCreate + 1);
        WeightSummary testWeightSummary = weightSummaryList.get(weightSummaryList.size() - 1);
        assertThat(testWeightSummary.getUsuarioId()).isEqualTo(DEFAULT_USUARIO_ID);
        assertThat(testWeightSummary.getEmpresaId()).isEqualTo(DEFAULT_EMPRESA_ID);
        assertThat(testWeightSummary.getFieldAverage()).isEqualTo(DEFAULT_FIELD_AVERAGE);
        assertThat(testWeightSummary.getFieldMax()).isEqualTo(DEFAULT_FIELD_MAX);
        assertThat(testWeightSummary.getFieldMin()).isEqualTo(DEFAULT_FIELD_MIN);
        assertThat(testWeightSummary.getStartTime()).isEqualTo(DEFAULT_START_TIME);
        assertThat(testWeightSummary.getEndTime()).isEqualTo(DEFAULT_END_TIME);
    }

    @Test
    @Transactional
    void createWeightSummaryWithExistingId() throws Exception {
        // Create the WeightSummary with an existing ID
        weightSummaryRepository.saveAndFlush(weightSummary);

        int databaseSizeBeforeCreate = weightSummaryRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restWeightSummaryMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(weightSummary)))
            .andExpect(status().isBadRequest());

        // Validate the WeightSummary in the database
        List<WeightSummary> weightSummaryList = weightSummaryRepository.findAll();
        assertThat(weightSummaryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllWeightSummaries() throws Exception {
        // Initialize the database
        weightSummaryRepository.saveAndFlush(weightSummary);

        // Get all the weightSummaryList
        restWeightSummaryMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(weightSummary.getId().toString())))
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
    void getWeightSummary() throws Exception {
        // Initialize the database
        weightSummaryRepository.saveAndFlush(weightSummary);

        // Get the weightSummary
        restWeightSummaryMockMvc
            .perform(get(ENTITY_API_URL_ID, weightSummary.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(weightSummary.getId().toString()))
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
    void getWeightSummariesByIdFiltering() throws Exception {
        // Initialize the database
        weightSummaryRepository.saveAndFlush(weightSummary);

        UUID id = weightSummary.getId();

        defaultWeightSummaryShouldBeFound("id.equals=" + id);
        defaultWeightSummaryShouldNotBeFound("id.notEquals=" + id);
    }

    @Test
    @Transactional
    void getAllWeightSummariesByUsuarioIdIsEqualToSomething() throws Exception {
        // Initialize the database
        weightSummaryRepository.saveAndFlush(weightSummary);

        // Get all the weightSummaryList where usuarioId equals to DEFAULT_USUARIO_ID
        defaultWeightSummaryShouldBeFound("usuarioId.equals=" + DEFAULT_USUARIO_ID);

        // Get all the weightSummaryList where usuarioId equals to UPDATED_USUARIO_ID
        defaultWeightSummaryShouldNotBeFound("usuarioId.equals=" + UPDATED_USUARIO_ID);
    }

    @Test
    @Transactional
    void getAllWeightSummariesByUsuarioIdIsInShouldWork() throws Exception {
        // Initialize the database
        weightSummaryRepository.saveAndFlush(weightSummary);

        // Get all the weightSummaryList where usuarioId in DEFAULT_USUARIO_ID or UPDATED_USUARIO_ID
        defaultWeightSummaryShouldBeFound("usuarioId.in=" + DEFAULT_USUARIO_ID + "," + UPDATED_USUARIO_ID);

        // Get all the weightSummaryList where usuarioId equals to UPDATED_USUARIO_ID
        defaultWeightSummaryShouldNotBeFound("usuarioId.in=" + UPDATED_USUARIO_ID);
    }

    @Test
    @Transactional
    void getAllWeightSummariesByUsuarioIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        weightSummaryRepository.saveAndFlush(weightSummary);

        // Get all the weightSummaryList where usuarioId is not null
        defaultWeightSummaryShouldBeFound("usuarioId.specified=true");

        // Get all the weightSummaryList where usuarioId is null
        defaultWeightSummaryShouldNotBeFound("usuarioId.specified=false");
    }

    @Test
    @Transactional
    void getAllWeightSummariesByUsuarioIdContainsSomething() throws Exception {
        // Initialize the database
        weightSummaryRepository.saveAndFlush(weightSummary);

        // Get all the weightSummaryList where usuarioId contains DEFAULT_USUARIO_ID
        defaultWeightSummaryShouldBeFound("usuarioId.contains=" + DEFAULT_USUARIO_ID);

        // Get all the weightSummaryList where usuarioId contains UPDATED_USUARIO_ID
        defaultWeightSummaryShouldNotBeFound("usuarioId.contains=" + UPDATED_USUARIO_ID);
    }

    @Test
    @Transactional
    void getAllWeightSummariesByUsuarioIdNotContainsSomething() throws Exception {
        // Initialize the database
        weightSummaryRepository.saveAndFlush(weightSummary);

        // Get all the weightSummaryList where usuarioId does not contain DEFAULT_USUARIO_ID
        defaultWeightSummaryShouldNotBeFound("usuarioId.doesNotContain=" + DEFAULT_USUARIO_ID);

        // Get all the weightSummaryList where usuarioId does not contain UPDATED_USUARIO_ID
        defaultWeightSummaryShouldBeFound("usuarioId.doesNotContain=" + UPDATED_USUARIO_ID);
    }

    @Test
    @Transactional
    void getAllWeightSummariesByEmpresaIdIsEqualToSomething() throws Exception {
        // Initialize the database
        weightSummaryRepository.saveAndFlush(weightSummary);

        // Get all the weightSummaryList where empresaId equals to DEFAULT_EMPRESA_ID
        defaultWeightSummaryShouldBeFound("empresaId.equals=" + DEFAULT_EMPRESA_ID);

        // Get all the weightSummaryList where empresaId equals to UPDATED_EMPRESA_ID
        defaultWeightSummaryShouldNotBeFound("empresaId.equals=" + UPDATED_EMPRESA_ID);
    }

    @Test
    @Transactional
    void getAllWeightSummariesByEmpresaIdIsInShouldWork() throws Exception {
        // Initialize the database
        weightSummaryRepository.saveAndFlush(weightSummary);

        // Get all the weightSummaryList where empresaId in DEFAULT_EMPRESA_ID or UPDATED_EMPRESA_ID
        defaultWeightSummaryShouldBeFound("empresaId.in=" + DEFAULT_EMPRESA_ID + "," + UPDATED_EMPRESA_ID);

        // Get all the weightSummaryList where empresaId equals to UPDATED_EMPRESA_ID
        defaultWeightSummaryShouldNotBeFound("empresaId.in=" + UPDATED_EMPRESA_ID);
    }

    @Test
    @Transactional
    void getAllWeightSummariesByEmpresaIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        weightSummaryRepository.saveAndFlush(weightSummary);

        // Get all the weightSummaryList where empresaId is not null
        defaultWeightSummaryShouldBeFound("empresaId.specified=true");

        // Get all the weightSummaryList where empresaId is null
        defaultWeightSummaryShouldNotBeFound("empresaId.specified=false");
    }

    @Test
    @Transactional
    void getAllWeightSummariesByEmpresaIdContainsSomething() throws Exception {
        // Initialize the database
        weightSummaryRepository.saveAndFlush(weightSummary);

        // Get all the weightSummaryList where empresaId contains DEFAULT_EMPRESA_ID
        defaultWeightSummaryShouldBeFound("empresaId.contains=" + DEFAULT_EMPRESA_ID);

        // Get all the weightSummaryList where empresaId contains UPDATED_EMPRESA_ID
        defaultWeightSummaryShouldNotBeFound("empresaId.contains=" + UPDATED_EMPRESA_ID);
    }

    @Test
    @Transactional
    void getAllWeightSummariesByEmpresaIdNotContainsSomething() throws Exception {
        // Initialize the database
        weightSummaryRepository.saveAndFlush(weightSummary);

        // Get all the weightSummaryList where empresaId does not contain DEFAULT_EMPRESA_ID
        defaultWeightSummaryShouldNotBeFound("empresaId.doesNotContain=" + DEFAULT_EMPRESA_ID);

        // Get all the weightSummaryList where empresaId does not contain UPDATED_EMPRESA_ID
        defaultWeightSummaryShouldBeFound("empresaId.doesNotContain=" + UPDATED_EMPRESA_ID);
    }

    @Test
    @Transactional
    void getAllWeightSummariesByFieldAverageIsEqualToSomething() throws Exception {
        // Initialize the database
        weightSummaryRepository.saveAndFlush(weightSummary);

        // Get all the weightSummaryList where fieldAverage equals to DEFAULT_FIELD_AVERAGE
        defaultWeightSummaryShouldBeFound("fieldAverage.equals=" + DEFAULT_FIELD_AVERAGE);

        // Get all the weightSummaryList where fieldAverage equals to UPDATED_FIELD_AVERAGE
        defaultWeightSummaryShouldNotBeFound("fieldAverage.equals=" + UPDATED_FIELD_AVERAGE);
    }

    @Test
    @Transactional
    void getAllWeightSummariesByFieldAverageIsInShouldWork() throws Exception {
        // Initialize the database
        weightSummaryRepository.saveAndFlush(weightSummary);

        // Get all the weightSummaryList where fieldAverage in DEFAULT_FIELD_AVERAGE or UPDATED_FIELD_AVERAGE
        defaultWeightSummaryShouldBeFound("fieldAverage.in=" + DEFAULT_FIELD_AVERAGE + "," + UPDATED_FIELD_AVERAGE);

        // Get all the weightSummaryList where fieldAverage equals to UPDATED_FIELD_AVERAGE
        defaultWeightSummaryShouldNotBeFound("fieldAverage.in=" + UPDATED_FIELD_AVERAGE);
    }

    @Test
    @Transactional
    void getAllWeightSummariesByFieldAverageIsNullOrNotNull() throws Exception {
        // Initialize the database
        weightSummaryRepository.saveAndFlush(weightSummary);

        // Get all the weightSummaryList where fieldAverage is not null
        defaultWeightSummaryShouldBeFound("fieldAverage.specified=true");

        // Get all the weightSummaryList where fieldAverage is null
        defaultWeightSummaryShouldNotBeFound("fieldAverage.specified=false");
    }

    @Test
    @Transactional
    void getAllWeightSummariesByFieldAverageIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        weightSummaryRepository.saveAndFlush(weightSummary);

        // Get all the weightSummaryList where fieldAverage is greater than or equal to DEFAULT_FIELD_AVERAGE
        defaultWeightSummaryShouldBeFound("fieldAverage.greaterThanOrEqual=" + DEFAULT_FIELD_AVERAGE);

        // Get all the weightSummaryList where fieldAverage is greater than or equal to UPDATED_FIELD_AVERAGE
        defaultWeightSummaryShouldNotBeFound("fieldAverage.greaterThanOrEqual=" + UPDATED_FIELD_AVERAGE);
    }

    @Test
    @Transactional
    void getAllWeightSummariesByFieldAverageIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        weightSummaryRepository.saveAndFlush(weightSummary);

        // Get all the weightSummaryList where fieldAverage is less than or equal to DEFAULT_FIELD_AVERAGE
        defaultWeightSummaryShouldBeFound("fieldAverage.lessThanOrEqual=" + DEFAULT_FIELD_AVERAGE);

        // Get all the weightSummaryList where fieldAverage is less than or equal to SMALLER_FIELD_AVERAGE
        defaultWeightSummaryShouldNotBeFound("fieldAverage.lessThanOrEqual=" + SMALLER_FIELD_AVERAGE);
    }

    @Test
    @Transactional
    void getAllWeightSummariesByFieldAverageIsLessThanSomething() throws Exception {
        // Initialize the database
        weightSummaryRepository.saveAndFlush(weightSummary);

        // Get all the weightSummaryList where fieldAverage is less than DEFAULT_FIELD_AVERAGE
        defaultWeightSummaryShouldNotBeFound("fieldAverage.lessThan=" + DEFAULT_FIELD_AVERAGE);

        // Get all the weightSummaryList where fieldAverage is less than UPDATED_FIELD_AVERAGE
        defaultWeightSummaryShouldBeFound("fieldAverage.lessThan=" + UPDATED_FIELD_AVERAGE);
    }

    @Test
    @Transactional
    void getAllWeightSummariesByFieldAverageIsGreaterThanSomething() throws Exception {
        // Initialize the database
        weightSummaryRepository.saveAndFlush(weightSummary);

        // Get all the weightSummaryList where fieldAverage is greater than DEFAULT_FIELD_AVERAGE
        defaultWeightSummaryShouldNotBeFound("fieldAverage.greaterThan=" + DEFAULT_FIELD_AVERAGE);

        // Get all the weightSummaryList where fieldAverage is greater than SMALLER_FIELD_AVERAGE
        defaultWeightSummaryShouldBeFound("fieldAverage.greaterThan=" + SMALLER_FIELD_AVERAGE);
    }

    @Test
    @Transactional
    void getAllWeightSummariesByFieldMaxIsEqualToSomething() throws Exception {
        // Initialize the database
        weightSummaryRepository.saveAndFlush(weightSummary);

        // Get all the weightSummaryList where fieldMax equals to DEFAULT_FIELD_MAX
        defaultWeightSummaryShouldBeFound("fieldMax.equals=" + DEFAULT_FIELD_MAX);

        // Get all the weightSummaryList where fieldMax equals to UPDATED_FIELD_MAX
        defaultWeightSummaryShouldNotBeFound("fieldMax.equals=" + UPDATED_FIELD_MAX);
    }

    @Test
    @Transactional
    void getAllWeightSummariesByFieldMaxIsInShouldWork() throws Exception {
        // Initialize the database
        weightSummaryRepository.saveAndFlush(weightSummary);

        // Get all the weightSummaryList where fieldMax in DEFAULT_FIELD_MAX or UPDATED_FIELD_MAX
        defaultWeightSummaryShouldBeFound("fieldMax.in=" + DEFAULT_FIELD_MAX + "," + UPDATED_FIELD_MAX);

        // Get all the weightSummaryList where fieldMax equals to UPDATED_FIELD_MAX
        defaultWeightSummaryShouldNotBeFound("fieldMax.in=" + UPDATED_FIELD_MAX);
    }

    @Test
    @Transactional
    void getAllWeightSummariesByFieldMaxIsNullOrNotNull() throws Exception {
        // Initialize the database
        weightSummaryRepository.saveAndFlush(weightSummary);

        // Get all the weightSummaryList where fieldMax is not null
        defaultWeightSummaryShouldBeFound("fieldMax.specified=true");

        // Get all the weightSummaryList where fieldMax is null
        defaultWeightSummaryShouldNotBeFound("fieldMax.specified=false");
    }

    @Test
    @Transactional
    void getAllWeightSummariesByFieldMaxIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        weightSummaryRepository.saveAndFlush(weightSummary);

        // Get all the weightSummaryList where fieldMax is greater than or equal to DEFAULT_FIELD_MAX
        defaultWeightSummaryShouldBeFound("fieldMax.greaterThanOrEqual=" + DEFAULT_FIELD_MAX);

        // Get all the weightSummaryList where fieldMax is greater than or equal to UPDATED_FIELD_MAX
        defaultWeightSummaryShouldNotBeFound("fieldMax.greaterThanOrEqual=" + UPDATED_FIELD_MAX);
    }

    @Test
    @Transactional
    void getAllWeightSummariesByFieldMaxIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        weightSummaryRepository.saveAndFlush(weightSummary);

        // Get all the weightSummaryList where fieldMax is less than or equal to DEFAULT_FIELD_MAX
        defaultWeightSummaryShouldBeFound("fieldMax.lessThanOrEqual=" + DEFAULT_FIELD_MAX);

        // Get all the weightSummaryList where fieldMax is less than or equal to SMALLER_FIELD_MAX
        defaultWeightSummaryShouldNotBeFound("fieldMax.lessThanOrEqual=" + SMALLER_FIELD_MAX);
    }

    @Test
    @Transactional
    void getAllWeightSummariesByFieldMaxIsLessThanSomething() throws Exception {
        // Initialize the database
        weightSummaryRepository.saveAndFlush(weightSummary);

        // Get all the weightSummaryList where fieldMax is less than DEFAULT_FIELD_MAX
        defaultWeightSummaryShouldNotBeFound("fieldMax.lessThan=" + DEFAULT_FIELD_MAX);

        // Get all the weightSummaryList where fieldMax is less than UPDATED_FIELD_MAX
        defaultWeightSummaryShouldBeFound("fieldMax.lessThan=" + UPDATED_FIELD_MAX);
    }

    @Test
    @Transactional
    void getAllWeightSummariesByFieldMaxIsGreaterThanSomething() throws Exception {
        // Initialize the database
        weightSummaryRepository.saveAndFlush(weightSummary);

        // Get all the weightSummaryList where fieldMax is greater than DEFAULT_FIELD_MAX
        defaultWeightSummaryShouldNotBeFound("fieldMax.greaterThan=" + DEFAULT_FIELD_MAX);

        // Get all the weightSummaryList where fieldMax is greater than SMALLER_FIELD_MAX
        defaultWeightSummaryShouldBeFound("fieldMax.greaterThan=" + SMALLER_FIELD_MAX);
    }

    @Test
    @Transactional
    void getAllWeightSummariesByFieldMinIsEqualToSomething() throws Exception {
        // Initialize the database
        weightSummaryRepository.saveAndFlush(weightSummary);

        // Get all the weightSummaryList where fieldMin equals to DEFAULT_FIELD_MIN
        defaultWeightSummaryShouldBeFound("fieldMin.equals=" + DEFAULT_FIELD_MIN);

        // Get all the weightSummaryList where fieldMin equals to UPDATED_FIELD_MIN
        defaultWeightSummaryShouldNotBeFound("fieldMin.equals=" + UPDATED_FIELD_MIN);
    }

    @Test
    @Transactional
    void getAllWeightSummariesByFieldMinIsInShouldWork() throws Exception {
        // Initialize the database
        weightSummaryRepository.saveAndFlush(weightSummary);

        // Get all the weightSummaryList where fieldMin in DEFAULT_FIELD_MIN or UPDATED_FIELD_MIN
        defaultWeightSummaryShouldBeFound("fieldMin.in=" + DEFAULT_FIELD_MIN + "," + UPDATED_FIELD_MIN);

        // Get all the weightSummaryList where fieldMin equals to UPDATED_FIELD_MIN
        defaultWeightSummaryShouldNotBeFound("fieldMin.in=" + UPDATED_FIELD_MIN);
    }

    @Test
    @Transactional
    void getAllWeightSummariesByFieldMinIsNullOrNotNull() throws Exception {
        // Initialize the database
        weightSummaryRepository.saveAndFlush(weightSummary);

        // Get all the weightSummaryList where fieldMin is not null
        defaultWeightSummaryShouldBeFound("fieldMin.specified=true");

        // Get all the weightSummaryList where fieldMin is null
        defaultWeightSummaryShouldNotBeFound("fieldMin.specified=false");
    }

    @Test
    @Transactional
    void getAllWeightSummariesByFieldMinIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        weightSummaryRepository.saveAndFlush(weightSummary);

        // Get all the weightSummaryList where fieldMin is greater than or equal to DEFAULT_FIELD_MIN
        defaultWeightSummaryShouldBeFound("fieldMin.greaterThanOrEqual=" + DEFAULT_FIELD_MIN);

        // Get all the weightSummaryList where fieldMin is greater than or equal to UPDATED_FIELD_MIN
        defaultWeightSummaryShouldNotBeFound("fieldMin.greaterThanOrEqual=" + UPDATED_FIELD_MIN);
    }

    @Test
    @Transactional
    void getAllWeightSummariesByFieldMinIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        weightSummaryRepository.saveAndFlush(weightSummary);

        // Get all the weightSummaryList where fieldMin is less than or equal to DEFAULT_FIELD_MIN
        defaultWeightSummaryShouldBeFound("fieldMin.lessThanOrEqual=" + DEFAULT_FIELD_MIN);

        // Get all the weightSummaryList where fieldMin is less than or equal to SMALLER_FIELD_MIN
        defaultWeightSummaryShouldNotBeFound("fieldMin.lessThanOrEqual=" + SMALLER_FIELD_MIN);
    }

    @Test
    @Transactional
    void getAllWeightSummariesByFieldMinIsLessThanSomething() throws Exception {
        // Initialize the database
        weightSummaryRepository.saveAndFlush(weightSummary);

        // Get all the weightSummaryList where fieldMin is less than DEFAULT_FIELD_MIN
        defaultWeightSummaryShouldNotBeFound("fieldMin.lessThan=" + DEFAULT_FIELD_MIN);

        // Get all the weightSummaryList where fieldMin is less than UPDATED_FIELD_MIN
        defaultWeightSummaryShouldBeFound("fieldMin.lessThan=" + UPDATED_FIELD_MIN);
    }

    @Test
    @Transactional
    void getAllWeightSummariesByFieldMinIsGreaterThanSomething() throws Exception {
        // Initialize the database
        weightSummaryRepository.saveAndFlush(weightSummary);

        // Get all the weightSummaryList where fieldMin is greater than DEFAULT_FIELD_MIN
        defaultWeightSummaryShouldNotBeFound("fieldMin.greaterThan=" + DEFAULT_FIELD_MIN);

        // Get all the weightSummaryList where fieldMin is greater than SMALLER_FIELD_MIN
        defaultWeightSummaryShouldBeFound("fieldMin.greaterThan=" + SMALLER_FIELD_MIN);
    }

    @Test
    @Transactional
    void getAllWeightSummariesByStartTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        weightSummaryRepository.saveAndFlush(weightSummary);

        // Get all the weightSummaryList where startTime equals to DEFAULT_START_TIME
        defaultWeightSummaryShouldBeFound("startTime.equals=" + DEFAULT_START_TIME);

        // Get all the weightSummaryList where startTime equals to UPDATED_START_TIME
        defaultWeightSummaryShouldNotBeFound("startTime.equals=" + UPDATED_START_TIME);
    }

    @Test
    @Transactional
    void getAllWeightSummariesByStartTimeIsInShouldWork() throws Exception {
        // Initialize the database
        weightSummaryRepository.saveAndFlush(weightSummary);

        // Get all the weightSummaryList where startTime in DEFAULT_START_TIME or UPDATED_START_TIME
        defaultWeightSummaryShouldBeFound("startTime.in=" + DEFAULT_START_TIME + "," + UPDATED_START_TIME);

        // Get all the weightSummaryList where startTime equals to UPDATED_START_TIME
        defaultWeightSummaryShouldNotBeFound("startTime.in=" + UPDATED_START_TIME);
    }

    @Test
    @Transactional
    void getAllWeightSummariesByStartTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        weightSummaryRepository.saveAndFlush(weightSummary);

        // Get all the weightSummaryList where startTime is not null
        defaultWeightSummaryShouldBeFound("startTime.specified=true");

        // Get all the weightSummaryList where startTime is null
        defaultWeightSummaryShouldNotBeFound("startTime.specified=false");
    }

    @Test
    @Transactional
    void getAllWeightSummariesByEndTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        weightSummaryRepository.saveAndFlush(weightSummary);

        // Get all the weightSummaryList where endTime equals to DEFAULT_END_TIME
        defaultWeightSummaryShouldBeFound("endTime.equals=" + DEFAULT_END_TIME);

        // Get all the weightSummaryList where endTime equals to UPDATED_END_TIME
        defaultWeightSummaryShouldNotBeFound("endTime.equals=" + UPDATED_END_TIME);
    }

    @Test
    @Transactional
    void getAllWeightSummariesByEndTimeIsInShouldWork() throws Exception {
        // Initialize the database
        weightSummaryRepository.saveAndFlush(weightSummary);

        // Get all the weightSummaryList where endTime in DEFAULT_END_TIME or UPDATED_END_TIME
        defaultWeightSummaryShouldBeFound("endTime.in=" + DEFAULT_END_TIME + "," + UPDATED_END_TIME);

        // Get all the weightSummaryList where endTime equals to UPDATED_END_TIME
        defaultWeightSummaryShouldNotBeFound("endTime.in=" + UPDATED_END_TIME);
    }

    @Test
    @Transactional
    void getAllWeightSummariesByEndTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        weightSummaryRepository.saveAndFlush(weightSummary);

        // Get all the weightSummaryList where endTime is not null
        defaultWeightSummaryShouldBeFound("endTime.specified=true");

        // Get all the weightSummaryList where endTime is null
        defaultWeightSummaryShouldNotBeFound("endTime.specified=false");
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultWeightSummaryShouldBeFound(String filter) throws Exception {
        restWeightSummaryMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(weightSummary.getId().toString())))
            .andExpect(jsonPath("$.[*].usuarioId").value(hasItem(DEFAULT_USUARIO_ID)))
            .andExpect(jsonPath("$.[*].empresaId").value(hasItem(DEFAULT_EMPRESA_ID)))
            .andExpect(jsonPath("$.[*].fieldAverage").value(hasItem(DEFAULT_FIELD_AVERAGE.doubleValue())))
            .andExpect(jsonPath("$.[*].fieldMax").value(hasItem(DEFAULT_FIELD_MAX.doubleValue())))
            .andExpect(jsonPath("$.[*].fieldMin").value(hasItem(DEFAULT_FIELD_MIN.doubleValue())))
            .andExpect(jsonPath("$.[*].startTime").value(hasItem(DEFAULT_START_TIME.toString())))
            .andExpect(jsonPath("$.[*].endTime").value(hasItem(DEFAULT_END_TIME.toString())));

        // Check, that the count call also returns 1
        restWeightSummaryMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultWeightSummaryShouldNotBeFound(String filter) throws Exception {
        restWeightSummaryMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restWeightSummaryMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingWeightSummary() throws Exception {
        // Get the weightSummary
        restWeightSummaryMockMvc.perform(get(ENTITY_API_URL_ID, UUID.randomUUID().toString())).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingWeightSummary() throws Exception {
        // Initialize the database
        weightSummaryRepository.saveAndFlush(weightSummary);

        int databaseSizeBeforeUpdate = weightSummaryRepository.findAll().size();

        // Update the weightSummary
        WeightSummary updatedWeightSummary = weightSummaryRepository.findById(weightSummary.getId()).get();
        // Disconnect from session so that the updates on updatedWeightSummary are not directly saved in db
        em.detach(updatedWeightSummary);
        updatedWeightSummary
            .usuarioId(UPDATED_USUARIO_ID)
            .empresaId(UPDATED_EMPRESA_ID)
            .fieldAverage(UPDATED_FIELD_AVERAGE)
            .fieldMax(UPDATED_FIELD_MAX)
            .fieldMin(UPDATED_FIELD_MIN)
            .startTime(UPDATED_START_TIME)
            .endTime(UPDATED_END_TIME);

        restWeightSummaryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedWeightSummary.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedWeightSummary))
            )
            .andExpect(status().isOk());

        // Validate the WeightSummary in the database
        List<WeightSummary> weightSummaryList = weightSummaryRepository.findAll();
        assertThat(weightSummaryList).hasSize(databaseSizeBeforeUpdate);
        WeightSummary testWeightSummary = weightSummaryList.get(weightSummaryList.size() - 1);
        assertThat(testWeightSummary.getUsuarioId()).isEqualTo(UPDATED_USUARIO_ID);
        assertThat(testWeightSummary.getEmpresaId()).isEqualTo(UPDATED_EMPRESA_ID);
        assertThat(testWeightSummary.getFieldAverage()).isEqualTo(UPDATED_FIELD_AVERAGE);
        assertThat(testWeightSummary.getFieldMax()).isEqualTo(UPDATED_FIELD_MAX);
        assertThat(testWeightSummary.getFieldMin()).isEqualTo(UPDATED_FIELD_MIN);
        assertThat(testWeightSummary.getStartTime()).isEqualTo(UPDATED_START_TIME);
        assertThat(testWeightSummary.getEndTime()).isEqualTo(UPDATED_END_TIME);
    }

    @Test
    @Transactional
    void putNonExistingWeightSummary() throws Exception {
        int databaseSizeBeforeUpdate = weightSummaryRepository.findAll().size();
        weightSummary.setId(UUID.randomUUID());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWeightSummaryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, weightSummary.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(weightSummary))
            )
            .andExpect(status().isBadRequest());

        // Validate the WeightSummary in the database
        List<WeightSummary> weightSummaryList = weightSummaryRepository.findAll();
        assertThat(weightSummaryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchWeightSummary() throws Exception {
        int databaseSizeBeforeUpdate = weightSummaryRepository.findAll().size();
        weightSummary.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWeightSummaryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(weightSummary))
            )
            .andExpect(status().isBadRequest());

        // Validate the WeightSummary in the database
        List<WeightSummary> weightSummaryList = weightSummaryRepository.findAll();
        assertThat(weightSummaryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamWeightSummary() throws Exception {
        int databaseSizeBeforeUpdate = weightSummaryRepository.findAll().size();
        weightSummary.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWeightSummaryMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(weightSummary)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the WeightSummary in the database
        List<WeightSummary> weightSummaryList = weightSummaryRepository.findAll();
        assertThat(weightSummaryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateWeightSummaryWithPatch() throws Exception {
        // Initialize the database
        weightSummaryRepository.saveAndFlush(weightSummary);

        int databaseSizeBeforeUpdate = weightSummaryRepository.findAll().size();

        // Update the weightSummary using partial update
        WeightSummary partialUpdatedWeightSummary = new WeightSummary();
        partialUpdatedWeightSummary.setId(weightSummary.getId());

        partialUpdatedWeightSummary
            .usuarioId(UPDATED_USUARIO_ID)
            .fieldAverage(UPDATED_FIELD_AVERAGE)
            .fieldMax(UPDATED_FIELD_MAX)
            .fieldMin(UPDATED_FIELD_MIN)
            .startTime(UPDATED_START_TIME);

        restWeightSummaryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedWeightSummary.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedWeightSummary))
            )
            .andExpect(status().isOk());

        // Validate the WeightSummary in the database
        List<WeightSummary> weightSummaryList = weightSummaryRepository.findAll();
        assertThat(weightSummaryList).hasSize(databaseSizeBeforeUpdate);
        WeightSummary testWeightSummary = weightSummaryList.get(weightSummaryList.size() - 1);
        assertThat(testWeightSummary.getUsuarioId()).isEqualTo(UPDATED_USUARIO_ID);
        assertThat(testWeightSummary.getEmpresaId()).isEqualTo(DEFAULT_EMPRESA_ID);
        assertThat(testWeightSummary.getFieldAverage()).isEqualTo(UPDATED_FIELD_AVERAGE);
        assertThat(testWeightSummary.getFieldMax()).isEqualTo(UPDATED_FIELD_MAX);
        assertThat(testWeightSummary.getFieldMin()).isEqualTo(UPDATED_FIELD_MIN);
        assertThat(testWeightSummary.getStartTime()).isEqualTo(UPDATED_START_TIME);
        assertThat(testWeightSummary.getEndTime()).isEqualTo(DEFAULT_END_TIME);
    }

    @Test
    @Transactional
    void fullUpdateWeightSummaryWithPatch() throws Exception {
        // Initialize the database
        weightSummaryRepository.saveAndFlush(weightSummary);

        int databaseSizeBeforeUpdate = weightSummaryRepository.findAll().size();

        // Update the weightSummary using partial update
        WeightSummary partialUpdatedWeightSummary = new WeightSummary();
        partialUpdatedWeightSummary.setId(weightSummary.getId());

        partialUpdatedWeightSummary
            .usuarioId(UPDATED_USUARIO_ID)
            .empresaId(UPDATED_EMPRESA_ID)
            .fieldAverage(UPDATED_FIELD_AVERAGE)
            .fieldMax(UPDATED_FIELD_MAX)
            .fieldMin(UPDATED_FIELD_MIN)
            .startTime(UPDATED_START_TIME)
            .endTime(UPDATED_END_TIME);

        restWeightSummaryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedWeightSummary.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedWeightSummary))
            )
            .andExpect(status().isOk());

        // Validate the WeightSummary in the database
        List<WeightSummary> weightSummaryList = weightSummaryRepository.findAll();
        assertThat(weightSummaryList).hasSize(databaseSizeBeforeUpdate);
        WeightSummary testWeightSummary = weightSummaryList.get(weightSummaryList.size() - 1);
        assertThat(testWeightSummary.getUsuarioId()).isEqualTo(UPDATED_USUARIO_ID);
        assertThat(testWeightSummary.getEmpresaId()).isEqualTo(UPDATED_EMPRESA_ID);
        assertThat(testWeightSummary.getFieldAverage()).isEqualTo(UPDATED_FIELD_AVERAGE);
        assertThat(testWeightSummary.getFieldMax()).isEqualTo(UPDATED_FIELD_MAX);
        assertThat(testWeightSummary.getFieldMin()).isEqualTo(UPDATED_FIELD_MIN);
        assertThat(testWeightSummary.getStartTime()).isEqualTo(UPDATED_START_TIME);
        assertThat(testWeightSummary.getEndTime()).isEqualTo(UPDATED_END_TIME);
    }

    @Test
    @Transactional
    void patchNonExistingWeightSummary() throws Exception {
        int databaseSizeBeforeUpdate = weightSummaryRepository.findAll().size();
        weightSummary.setId(UUID.randomUUID());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWeightSummaryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, weightSummary.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(weightSummary))
            )
            .andExpect(status().isBadRequest());

        // Validate the WeightSummary in the database
        List<WeightSummary> weightSummaryList = weightSummaryRepository.findAll();
        assertThat(weightSummaryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchWeightSummary() throws Exception {
        int databaseSizeBeforeUpdate = weightSummaryRepository.findAll().size();
        weightSummary.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWeightSummaryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(weightSummary))
            )
            .andExpect(status().isBadRequest());

        // Validate the WeightSummary in the database
        List<WeightSummary> weightSummaryList = weightSummaryRepository.findAll();
        assertThat(weightSummaryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamWeightSummary() throws Exception {
        int databaseSizeBeforeUpdate = weightSummaryRepository.findAll().size();
        weightSummary.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWeightSummaryMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(weightSummary))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the WeightSummary in the database
        List<WeightSummary> weightSummaryList = weightSummaryRepository.findAll();
        assertThat(weightSummaryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteWeightSummary() throws Exception {
        // Initialize the database
        weightSummaryRepository.saveAndFlush(weightSummary);

        int databaseSizeBeforeDelete = weightSummaryRepository.findAll().size();

        // Delete the weightSummary
        restWeightSummaryMockMvc
            .perform(delete(ENTITY_API_URL_ID, weightSummary.getId().toString()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<WeightSummary> weightSummaryList = weightSummaryRepository.findAll();
        assertThat(weightSummaryList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
