package com.be4tech.b4carecollect.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.be4tech.b4carecollect.IntegrationTest;
import com.be4tech.b4carecollect.domain.CaloriesBmrSummary;
import com.be4tech.b4carecollect.repository.CaloriesBmrSummaryRepository;
import com.be4tech.b4carecollect.service.criteria.CaloriesBmrSummaryCriteria;
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
 * Integration tests for the {@link CaloriesBmrSummaryResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CaloriesBmrSummaryResourceIT {

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

    private static final String ENTITY_API_URL = "/api/calories-bmr-summaries";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private CaloriesBmrSummaryRepository caloriesBmrSummaryRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCaloriesBmrSummaryMockMvc;

    private CaloriesBmrSummary caloriesBmrSummary;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CaloriesBmrSummary createEntity(EntityManager em) {
        CaloriesBmrSummary caloriesBmrSummary = new CaloriesBmrSummary()
            .usuarioId(DEFAULT_USUARIO_ID)
            .empresaId(DEFAULT_EMPRESA_ID)
            .fieldAverage(DEFAULT_FIELD_AVERAGE)
            .fieldMax(DEFAULT_FIELD_MAX)
            .fieldMin(DEFAULT_FIELD_MIN)
            .startTime(DEFAULT_START_TIME)
            .endTime(DEFAULT_END_TIME);
        return caloriesBmrSummary;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CaloriesBmrSummary createUpdatedEntity(EntityManager em) {
        CaloriesBmrSummary caloriesBmrSummary = new CaloriesBmrSummary()
            .usuarioId(UPDATED_USUARIO_ID)
            .empresaId(UPDATED_EMPRESA_ID)
            .fieldAverage(UPDATED_FIELD_AVERAGE)
            .fieldMax(UPDATED_FIELD_MAX)
            .fieldMin(UPDATED_FIELD_MIN)
            .startTime(UPDATED_START_TIME)
            .endTime(UPDATED_END_TIME);
        return caloriesBmrSummary;
    }

    @BeforeEach
    public void initTest() {
        caloriesBmrSummary = createEntity(em);
    }

    @Test
    @Transactional
    void createCaloriesBmrSummary() throws Exception {
        int databaseSizeBeforeCreate = caloriesBmrSummaryRepository.findAll().size();
        // Create the CaloriesBmrSummary
        restCaloriesBmrSummaryMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(caloriesBmrSummary))
            )
            .andExpect(status().isCreated());

        // Validate the CaloriesBmrSummary in the database
        List<CaloriesBmrSummary> caloriesBmrSummaryList = caloriesBmrSummaryRepository.findAll();
        assertThat(caloriesBmrSummaryList).hasSize(databaseSizeBeforeCreate + 1);
        CaloriesBmrSummary testCaloriesBmrSummary = caloriesBmrSummaryList.get(caloriesBmrSummaryList.size() - 1);
        assertThat(testCaloriesBmrSummary.getUsuarioId()).isEqualTo(DEFAULT_USUARIO_ID);
        assertThat(testCaloriesBmrSummary.getEmpresaId()).isEqualTo(DEFAULT_EMPRESA_ID);
        assertThat(testCaloriesBmrSummary.getFieldAverage()).isEqualTo(DEFAULT_FIELD_AVERAGE);
        assertThat(testCaloriesBmrSummary.getFieldMax()).isEqualTo(DEFAULT_FIELD_MAX);
        assertThat(testCaloriesBmrSummary.getFieldMin()).isEqualTo(DEFAULT_FIELD_MIN);
        assertThat(testCaloriesBmrSummary.getStartTime()).isEqualTo(DEFAULT_START_TIME);
        assertThat(testCaloriesBmrSummary.getEndTime()).isEqualTo(DEFAULT_END_TIME);
    }

    @Test
    @Transactional
    void createCaloriesBmrSummaryWithExistingId() throws Exception {
        // Create the CaloriesBmrSummary with an existing ID
        caloriesBmrSummaryRepository.saveAndFlush(caloriesBmrSummary);

        int databaseSizeBeforeCreate = caloriesBmrSummaryRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCaloriesBmrSummaryMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(caloriesBmrSummary))
            )
            .andExpect(status().isBadRequest());

        // Validate the CaloriesBmrSummary in the database
        List<CaloriesBmrSummary> caloriesBmrSummaryList = caloriesBmrSummaryRepository.findAll();
        assertThat(caloriesBmrSummaryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllCaloriesBmrSummaries() throws Exception {
        // Initialize the database
        caloriesBmrSummaryRepository.saveAndFlush(caloriesBmrSummary);

        // Get all the caloriesBmrSummaryList
        restCaloriesBmrSummaryMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(caloriesBmrSummary.getId().toString())))
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
    void getCaloriesBmrSummary() throws Exception {
        // Initialize the database
        caloriesBmrSummaryRepository.saveAndFlush(caloriesBmrSummary);

        // Get the caloriesBmrSummary
        restCaloriesBmrSummaryMockMvc
            .perform(get(ENTITY_API_URL_ID, caloriesBmrSummary.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(caloriesBmrSummary.getId().toString()))
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
    void getCaloriesBmrSummariesByIdFiltering() throws Exception {
        // Initialize the database
        caloriesBmrSummaryRepository.saveAndFlush(caloriesBmrSummary);

        UUID id = caloriesBmrSummary.getId();

        defaultCaloriesBmrSummaryShouldBeFound("id.equals=" + id);
        defaultCaloriesBmrSummaryShouldNotBeFound("id.notEquals=" + id);
    }

    @Test
    @Transactional
    void getAllCaloriesBmrSummariesByUsuarioIdIsEqualToSomething() throws Exception {
        // Initialize the database
        caloriesBmrSummaryRepository.saveAndFlush(caloriesBmrSummary);

        // Get all the caloriesBmrSummaryList where usuarioId equals to DEFAULT_USUARIO_ID
        defaultCaloriesBmrSummaryShouldBeFound("usuarioId.equals=" + DEFAULT_USUARIO_ID);

        // Get all the caloriesBmrSummaryList where usuarioId equals to UPDATED_USUARIO_ID
        defaultCaloriesBmrSummaryShouldNotBeFound("usuarioId.equals=" + UPDATED_USUARIO_ID);
    }

    @Test
    @Transactional
    void getAllCaloriesBmrSummariesByUsuarioIdIsInShouldWork() throws Exception {
        // Initialize the database
        caloriesBmrSummaryRepository.saveAndFlush(caloriesBmrSummary);

        // Get all the caloriesBmrSummaryList where usuarioId in DEFAULT_USUARIO_ID or UPDATED_USUARIO_ID
        defaultCaloriesBmrSummaryShouldBeFound("usuarioId.in=" + DEFAULT_USUARIO_ID + "," + UPDATED_USUARIO_ID);

        // Get all the caloriesBmrSummaryList where usuarioId equals to UPDATED_USUARIO_ID
        defaultCaloriesBmrSummaryShouldNotBeFound("usuarioId.in=" + UPDATED_USUARIO_ID);
    }

    @Test
    @Transactional
    void getAllCaloriesBmrSummariesByUsuarioIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        caloriesBmrSummaryRepository.saveAndFlush(caloriesBmrSummary);

        // Get all the caloriesBmrSummaryList where usuarioId is not null
        defaultCaloriesBmrSummaryShouldBeFound("usuarioId.specified=true");

        // Get all the caloriesBmrSummaryList where usuarioId is null
        defaultCaloriesBmrSummaryShouldNotBeFound("usuarioId.specified=false");
    }

    @Test
    @Transactional
    void getAllCaloriesBmrSummariesByUsuarioIdContainsSomething() throws Exception {
        // Initialize the database
        caloriesBmrSummaryRepository.saveAndFlush(caloriesBmrSummary);

        // Get all the caloriesBmrSummaryList where usuarioId contains DEFAULT_USUARIO_ID
        defaultCaloriesBmrSummaryShouldBeFound("usuarioId.contains=" + DEFAULT_USUARIO_ID);

        // Get all the caloriesBmrSummaryList where usuarioId contains UPDATED_USUARIO_ID
        defaultCaloriesBmrSummaryShouldNotBeFound("usuarioId.contains=" + UPDATED_USUARIO_ID);
    }

    @Test
    @Transactional
    void getAllCaloriesBmrSummariesByUsuarioIdNotContainsSomething() throws Exception {
        // Initialize the database
        caloriesBmrSummaryRepository.saveAndFlush(caloriesBmrSummary);

        // Get all the caloriesBmrSummaryList where usuarioId does not contain DEFAULT_USUARIO_ID
        defaultCaloriesBmrSummaryShouldNotBeFound("usuarioId.doesNotContain=" + DEFAULT_USUARIO_ID);

        // Get all the caloriesBmrSummaryList where usuarioId does not contain UPDATED_USUARIO_ID
        defaultCaloriesBmrSummaryShouldBeFound("usuarioId.doesNotContain=" + UPDATED_USUARIO_ID);
    }

    @Test
    @Transactional
    void getAllCaloriesBmrSummariesByEmpresaIdIsEqualToSomething() throws Exception {
        // Initialize the database
        caloriesBmrSummaryRepository.saveAndFlush(caloriesBmrSummary);

        // Get all the caloriesBmrSummaryList where empresaId equals to DEFAULT_EMPRESA_ID
        defaultCaloriesBmrSummaryShouldBeFound("empresaId.equals=" + DEFAULT_EMPRESA_ID);

        // Get all the caloriesBmrSummaryList where empresaId equals to UPDATED_EMPRESA_ID
        defaultCaloriesBmrSummaryShouldNotBeFound("empresaId.equals=" + UPDATED_EMPRESA_ID);
    }

    @Test
    @Transactional
    void getAllCaloriesBmrSummariesByEmpresaIdIsInShouldWork() throws Exception {
        // Initialize the database
        caloriesBmrSummaryRepository.saveAndFlush(caloriesBmrSummary);

        // Get all the caloriesBmrSummaryList where empresaId in DEFAULT_EMPRESA_ID or UPDATED_EMPRESA_ID
        defaultCaloriesBmrSummaryShouldBeFound("empresaId.in=" + DEFAULT_EMPRESA_ID + "," + UPDATED_EMPRESA_ID);

        // Get all the caloriesBmrSummaryList where empresaId equals to UPDATED_EMPRESA_ID
        defaultCaloriesBmrSummaryShouldNotBeFound("empresaId.in=" + UPDATED_EMPRESA_ID);
    }

    @Test
    @Transactional
    void getAllCaloriesBmrSummariesByEmpresaIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        caloriesBmrSummaryRepository.saveAndFlush(caloriesBmrSummary);

        // Get all the caloriesBmrSummaryList where empresaId is not null
        defaultCaloriesBmrSummaryShouldBeFound("empresaId.specified=true");

        // Get all the caloriesBmrSummaryList where empresaId is null
        defaultCaloriesBmrSummaryShouldNotBeFound("empresaId.specified=false");
    }

    @Test
    @Transactional
    void getAllCaloriesBmrSummariesByEmpresaIdContainsSomething() throws Exception {
        // Initialize the database
        caloriesBmrSummaryRepository.saveAndFlush(caloriesBmrSummary);

        // Get all the caloriesBmrSummaryList where empresaId contains DEFAULT_EMPRESA_ID
        defaultCaloriesBmrSummaryShouldBeFound("empresaId.contains=" + DEFAULT_EMPRESA_ID);

        // Get all the caloriesBmrSummaryList where empresaId contains UPDATED_EMPRESA_ID
        defaultCaloriesBmrSummaryShouldNotBeFound("empresaId.contains=" + UPDATED_EMPRESA_ID);
    }

    @Test
    @Transactional
    void getAllCaloriesBmrSummariesByEmpresaIdNotContainsSomething() throws Exception {
        // Initialize the database
        caloriesBmrSummaryRepository.saveAndFlush(caloriesBmrSummary);

        // Get all the caloriesBmrSummaryList where empresaId does not contain DEFAULT_EMPRESA_ID
        defaultCaloriesBmrSummaryShouldNotBeFound("empresaId.doesNotContain=" + DEFAULT_EMPRESA_ID);

        // Get all the caloriesBmrSummaryList where empresaId does not contain UPDATED_EMPRESA_ID
        defaultCaloriesBmrSummaryShouldBeFound("empresaId.doesNotContain=" + UPDATED_EMPRESA_ID);
    }

    @Test
    @Transactional
    void getAllCaloriesBmrSummariesByFieldAverageIsEqualToSomething() throws Exception {
        // Initialize the database
        caloriesBmrSummaryRepository.saveAndFlush(caloriesBmrSummary);

        // Get all the caloriesBmrSummaryList where fieldAverage equals to DEFAULT_FIELD_AVERAGE
        defaultCaloriesBmrSummaryShouldBeFound("fieldAverage.equals=" + DEFAULT_FIELD_AVERAGE);

        // Get all the caloriesBmrSummaryList where fieldAverage equals to UPDATED_FIELD_AVERAGE
        defaultCaloriesBmrSummaryShouldNotBeFound("fieldAverage.equals=" + UPDATED_FIELD_AVERAGE);
    }

    @Test
    @Transactional
    void getAllCaloriesBmrSummariesByFieldAverageIsInShouldWork() throws Exception {
        // Initialize the database
        caloriesBmrSummaryRepository.saveAndFlush(caloriesBmrSummary);

        // Get all the caloriesBmrSummaryList where fieldAverage in DEFAULT_FIELD_AVERAGE or UPDATED_FIELD_AVERAGE
        defaultCaloriesBmrSummaryShouldBeFound("fieldAverage.in=" + DEFAULT_FIELD_AVERAGE + "," + UPDATED_FIELD_AVERAGE);

        // Get all the caloriesBmrSummaryList where fieldAverage equals to UPDATED_FIELD_AVERAGE
        defaultCaloriesBmrSummaryShouldNotBeFound("fieldAverage.in=" + UPDATED_FIELD_AVERAGE);
    }

    @Test
    @Transactional
    void getAllCaloriesBmrSummariesByFieldAverageIsNullOrNotNull() throws Exception {
        // Initialize the database
        caloriesBmrSummaryRepository.saveAndFlush(caloriesBmrSummary);

        // Get all the caloriesBmrSummaryList where fieldAverage is not null
        defaultCaloriesBmrSummaryShouldBeFound("fieldAverage.specified=true");

        // Get all the caloriesBmrSummaryList where fieldAverage is null
        defaultCaloriesBmrSummaryShouldNotBeFound("fieldAverage.specified=false");
    }

    @Test
    @Transactional
    void getAllCaloriesBmrSummariesByFieldAverageIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        caloriesBmrSummaryRepository.saveAndFlush(caloriesBmrSummary);

        // Get all the caloriesBmrSummaryList where fieldAverage is greater than or equal to DEFAULT_FIELD_AVERAGE
        defaultCaloriesBmrSummaryShouldBeFound("fieldAverage.greaterThanOrEqual=" + DEFAULT_FIELD_AVERAGE);

        // Get all the caloriesBmrSummaryList where fieldAverage is greater than or equal to UPDATED_FIELD_AVERAGE
        defaultCaloriesBmrSummaryShouldNotBeFound("fieldAverage.greaterThanOrEqual=" + UPDATED_FIELD_AVERAGE);
    }

    @Test
    @Transactional
    void getAllCaloriesBmrSummariesByFieldAverageIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        caloriesBmrSummaryRepository.saveAndFlush(caloriesBmrSummary);

        // Get all the caloriesBmrSummaryList where fieldAverage is less than or equal to DEFAULT_FIELD_AVERAGE
        defaultCaloriesBmrSummaryShouldBeFound("fieldAverage.lessThanOrEqual=" + DEFAULT_FIELD_AVERAGE);

        // Get all the caloriesBmrSummaryList where fieldAverage is less than or equal to SMALLER_FIELD_AVERAGE
        defaultCaloriesBmrSummaryShouldNotBeFound("fieldAverage.lessThanOrEqual=" + SMALLER_FIELD_AVERAGE);
    }

    @Test
    @Transactional
    void getAllCaloriesBmrSummariesByFieldAverageIsLessThanSomething() throws Exception {
        // Initialize the database
        caloriesBmrSummaryRepository.saveAndFlush(caloriesBmrSummary);

        // Get all the caloriesBmrSummaryList where fieldAverage is less than DEFAULT_FIELD_AVERAGE
        defaultCaloriesBmrSummaryShouldNotBeFound("fieldAverage.lessThan=" + DEFAULT_FIELD_AVERAGE);

        // Get all the caloriesBmrSummaryList where fieldAverage is less than UPDATED_FIELD_AVERAGE
        defaultCaloriesBmrSummaryShouldBeFound("fieldAverage.lessThan=" + UPDATED_FIELD_AVERAGE);
    }

    @Test
    @Transactional
    void getAllCaloriesBmrSummariesByFieldAverageIsGreaterThanSomething() throws Exception {
        // Initialize the database
        caloriesBmrSummaryRepository.saveAndFlush(caloriesBmrSummary);

        // Get all the caloriesBmrSummaryList where fieldAverage is greater than DEFAULT_FIELD_AVERAGE
        defaultCaloriesBmrSummaryShouldNotBeFound("fieldAverage.greaterThan=" + DEFAULT_FIELD_AVERAGE);

        // Get all the caloriesBmrSummaryList where fieldAverage is greater than SMALLER_FIELD_AVERAGE
        defaultCaloriesBmrSummaryShouldBeFound("fieldAverage.greaterThan=" + SMALLER_FIELD_AVERAGE);
    }

    @Test
    @Transactional
    void getAllCaloriesBmrSummariesByFieldMaxIsEqualToSomething() throws Exception {
        // Initialize the database
        caloriesBmrSummaryRepository.saveAndFlush(caloriesBmrSummary);

        // Get all the caloriesBmrSummaryList where fieldMax equals to DEFAULT_FIELD_MAX
        defaultCaloriesBmrSummaryShouldBeFound("fieldMax.equals=" + DEFAULT_FIELD_MAX);

        // Get all the caloriesBmrSummaryList where fieldMax equals to UPDATED_FIELD_MAX
        defaultCaloriesBmrSummaryShouldNotBeFound("fieldMax.equals=" + UPDATED_FIELD_MAX);
    }

    @Test
    @Transactional
    void getAllCaloriesBmrSummariesByFieldMaxIsInShouldWork() throws Exception {
        // Initialize the database
        caloriesBmrSummaryRepository.saveAndFlush(caloriesBmrSummary);

        // Get all the caloriesBmrSummaryList where fieldMax in DEFAULT_FIELD_MAX or UPDATED_FIELD_MAX
        defaultCaloriesBmrSummaryShouldBeFound("fieldMax.in=" + DEFAULT_FIELD_MAX + "," + UPDATED_FIELD_MAX);

        // Get all the caloriesBmrSummaryList where fieldMax equals to UPDATED_FIELD_MAX
        defaultCaloriesBmrSummaryShouldNotBeFound("fieldMax.in=" + UPDATED_FIELD_MAX);
    }

    @Test
    @Transactional
    void getAllCaloriesBmrSummariesByFieldMaxIsNullOrNotNull() throws Exception {
        // Initialize the database
        caloriesBmrSummaryRepository.saveAndFlush(caloriesBmrSummary);

        // Get all the caloriesBmrSummaryList where fieldMax is not null
        defaultCaloriesBmrSummaryShouldBeFound("fieldMax.specified=true");

        // Get all the caloriesBmrSummaryList where fieldMax is null
        defaultCaloriesBmrSummaryShouldNotBeFound("fieldMax.specified=false");
    }

    @Test
    @Transactional
    void getAllCaloriesBmrSummariesByFieldMaxIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        caloriesBmrSummaryRepository.saveAndFlush(caloriesBmrSummary);

        // Get all the caloriesBmrSummaryList where fieldMax is greater than or equal to DEFAULT_FIELD_MAX
        defaultCaloriesBmrSummaryShouldBeFound("fieldMax.greaterThanOrEqual=" + DEFAULT_FIELD_MAX);

        // Get all the caloriesBmrSummaryList where fieldMax is greater than or equal to UPDATED_FIELD_MAX
        defaultCaloriesBmrSummaryShouldNotBeFound("fieldMax.greaterThanOrEqual=" + UPDATED_FIELD_MAX);
    }

    @Test
    @Transactional
    void getAllCaloriesBmrSummariesByFieldMaxIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        caloriesBmrSummaryRepository.saveAndFlush(caloriesBmrSummary);

        // Get all the caloriesBmrSummaryList where fieldMax is less than or equal to DEFAULT_FIELD_MAX
        defaultCaloriesBmrSummaryShouldBeFound("fieldMax.lessThanOrEqual=" + DEFAULT_FIELD_MAX);

        // Get all the caloriesBmrSummaryList where fieldMax is less than or equal to SMALLER_FIELD_MAX
        defaultCaloriesBmrSummaryShouldNotBeFound("fieldMax.lessThanOrEqual=" + SMALLER_FIELD_MAX);
    }

    @Test
    @Transactional
    void getAllCaloriesBmrSummariesByFieldMaxIsLessThanSomething() throws Exception {
        // Initialize the database
        caloriesBmrSummaryRepository.saveAndFlush(caloriesBmrSummary);

        // Get all the caloriesBmrSummaryList where fieldMax is less than DEFAULT_FIELD_MAX
        defaultCaloriesBmrSummaryShouldNotBeFound("fieldMax.lessThan=" + DEFAULT_FIELD_MAX);

        // Get all the caloriesBmrSummaryList where fieldMax is less than UPDATED_FIELD_MAX
        defaultCaloriesBmrSummaryShouldBeFound("fieldMax.lessThan=" + UPDATED_FIELD_MAX);
    }

    @Test
    @Transactional
    void getAllCaloriesBmrSummariesByFieldMaxIsGreaterThanSomething() throws Exception {
        // Initialize the database
        caloriesBmrSummaryRepository.saveAndFlush(caloriesBmrSummary);

        // Get all the caloriesBmrSummaryList where fieldMax is greater than DEFAULT_FIELD_MAX
        defaultCaloriesBmrSummaryShouldNotBeFound("fieldMax.greaterThan=" + DEFAULT_FIELD_MAX);

        // Get all the caloriesBmrSummaryList where fieldMax is greater than SMALLER_FIELD_MAX
        defaultCaloriesBmrSummaryShouldBeFound("fieldMax.greaterThan=" + SMALLER_FIELD_MAX);
    }

    @Test
    @Transactional
    void getAllCaloriesBmrSummariesByFieldMinIsEqualToSomething() throws Exception {
        // Initialize the database
        caloriesBmrSummaryRepository.saveAndFlush(caloriesBmrSummary);

        // Get all the caloriesBmrSummaryList where fieldMin equals to DEFAULT_FIELD_MIN
        defaultCaloriesBmrSummaryShouldBeFound("fieldMin.equals=" + DEFAULT_FIELD_MIN);

        // Get all the caloriesBmrSummaryList where fieldMin equals to UPDATED_FIELD_MIN
        defaultCaloriesBmrSummaryShouldNotBeFound("fieldMin.equals=" + UPDATED_FIELD_MIN);
    }

    @Test
    @Transactional
    void getAllCaloriesBmrSummariesByFieldMinIsInShouldWork() throws Exception {
        // Initialize the database
        caloriesBmrSummaryRepository.saveAndFlush(caloriesBmrSummary);

        // Get all the caloriesBmrSummaryList where fieldMin in DEFAULT_FIELD_MIN or UPDATED_FIELD_MIN
        defaultCaloriesBmrSummaryShouldBeFound("fieldMin.in=" + DEFAULT_FIELD_MIN + "," + UPDATED_FIELD_MIN);

        // Get all the caloriesBmrSummaryList where fieldMin equals to UPDATED_FIELD_MIN
        defaultCaloriesBmrSummaryShouldNotBeFound("fieldMin.in=" + UPDATED_FIELD_MIN);
    }

    @Test
    @Transactional
    void getAllCaloriesBmrSummariesByFieldMinIsNullOrNotNull() throws Exception {
        // Initialize the database
        caloriesBmrSummaryRepository.saveAndFlush(caloriesBmrSummary);

        // Get all the caloriesBmrSummaryList where fieldMin is not null
        defaultCaloriesBmrSummaryShouldBeFound("fieldMin.specified=true");

        // Get all the caloriesBmrSummaryList where fieldMin is null
        defaultCaloriesBmrSummaryShouldNotBeFound("fieldMin.specified=false");
    }

    @Test
    @Transactional
    void getAllCaloriesBmrSummariesByFieldMinIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        caloriesBmrSummaryRepository.saveAndFlush(caloriesBmrSummary);

        // Get all the caloriesBmrSummaryList where fieldMin is greater than or equal to DEFAULT_FIELD_MIN
        defaultCaloriesBmrSummaryShouldBeFound("fieldMin.greaterThanOrEqual=" + DEFAULT_FIELD_MIN);

        // Get all the caloriesBmrSummaryList where fieldMin is greater than or equal to UPDATED_FIELD_MIN
        defaultCaloriesBmrSummaryShouldNotBeFound("fieldMin.greaterThanOrEqual=" + UPDATED_FIELD_MIN);
    }

    @Test
    @Transactional
    void getAllCaloriesBmrSummariesByFieldMinIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        caloriesBmrSummaryRepository.saveAndFlush(caloriesBmrSummary);

        // Get all the caloriesBmrSummaryList where fieldMin is less than or equal to DEFAULT_FIELD_MIN
        defaultCaloriesBmrSummaryShouldBeFound("fieldMin.lessThanOrEqual=" + DEFAULT_FIELD_MIN);

        // Get all the caloriesBmrSummaryList where fieldMin is less than or equal to SMALLER_FIELD_MIN
        defaultCaloriesBmrSummaryShouldNotBeFound("fieldMin.lessThanOrEqual=" + SMALLER_FIELD_MIN);
    }

    @Test
    @Transactional
    void getAllCaloriesBmrSummariesByFieldMinIsLessThanSomething() throws Exception {
        // Initialize the database
        caloriesBmrSummaryRepository.saveAndFlush(caloriesBmrSummary);

        // Get all the caloriesBmrSummaryList where fieldMin is less than DEFAULT_FIELD_MIN
        defaultCaloriesBmrSummaryShouldNotBeFound("fieldMin.lessThan=" + DEFAULT_FIELD_MIN);

        // Get all the caloriesBmrSummaryList where fieldMin is less than UPDATED_FIELD_MIN
        defaultCaloriesBmrSummaryShouldBeFound("fieldMin.lessThan=" + UPDATED_FIELD_MIN);
    }

    @Test
    @Transactional
    void getAllCaloriesBmrSummariesByFieldMinIsGreaterThanSomething() throws Exception {
        // Initialize the database
        caloriesBmrSummaryRepository.saveAndFlush(caloriesBmrSummary);

        // Get all the caloriesBmrSummaryList where fieldMin is greater than DEFAULT_FIELD_MIN
        defaultCaloriesBmrSummaryShouldNotBeFound("fieldMin.greaterThan=" + DEFAULT_FIELD_MIN);

        // Get all the caloriesBmrSummaryList where fieldMin is greater than SMALLER_FIELD_MIN
        defaultCaloriesBmrSummaryShouldBeFound("fieldMin.greaterThan=" + SMALLER_FIELD_MIN);
    }

    @Test
    @Transactional
    void getAllCaloriesBmrSummariesByStartTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        caloriesBmrSummaryRepository.saveAndFlush(caloriesBmrSummary);

        // Get all the caloriesBmrSummaryList where startTime equals to DEFAULT_START_TIME
        defaultCaloriesBmrSummaryShouldBeFound("startTime.equals=" + DEFAULT_START_TIME);

        // Get all the caloriesBmrSummaryList where startTime equals to UPDATED_START_TIME
        defaultCaloriesBmrSummaryShouldNotBeFound("startTime.equals=" + UPDATED_START_TIME);
    }

    @Test
    @Transactional
    void getAllCaloriesBmrSummariesByStartTimeIsInShouldWork() throws Exception {
        // Initialize the database
        caloriesBmrSummaryRepository.saveAndFlush(caloriesBmrSummary);

        // Get all the caloriesBmrSummaryList where startTime in DEFAULT_START_TIME or UPDATED_START_TIME
        defaultCaloriesBmrSummaryShouldBeFound("startTime.in=" + DEFAULT_START_TIME + "," + UPDATED_START_TIME);

        // Get all the caloriesBmrSummaryList where startTime equals to UPDATED_START_TIME
        defaultCaloriesBmrSummaryShouldNotBeFound("startTime.in=" + UPDATED_START_TIME);
    }

    @Test
    @Transactional
    void getAllCaloriesBmrSummariesByStartTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        caloriesBmrSummaryRepository.saveAndFlush(caloriesBmrSummary);

        // Get all the caloriesBmrSummaryList where startTime is not null
        defaultCaloriesBmrSummaryShouldBeFound("startTime.specified=true");

        // Get all the caloriesBmrSummaryList where startTime is null
        defaultCaloriesBmrSummaryShouldNotBeFound("startTime.specified=false");
    }

    @Test
    @Transactional
    void getAllCaloriesBmrSummariesByEndTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        caloriesBmrSummaryRepository.saveAndFlush(caloriesBmrSummary);

        // Get all the caloriesBmrSummaryList where endTime equals to DEFAULT_END_TIME
        defaultCaloriesBmrSummaryShouldBeFound("endTime.equals=" + DEFAULT_END_TIME);

        // Get all the caloriesBmrSummaryList where endTime equals to UPDATED_END_TIME
        defaultCaloriesBmrSummaryShouldNotBeFound("endTime.equals=" + UPDATED_END_TIME);
    }

    @Test
    @Transactional
    void getAllCaloriesBmrSummariesByEndTimeIsInShouldWork() throws Exception {
        // Initialize the database
        caloriesBmrSummaryRepository.saveAndFlush(caloriesBmrSummary);

        // Get all the caloriesBmrSummaryList where endTime in DEFAULT_END_TIME or UPDATED_END_TIME
        defaultCaloriesBmrSummaryShouldBeFound("endTime.in=" + DEFAULT_END_TIME + "," + UPDATED_END_TIME);

        // Get all the caloriesBmrSummaryList where endTime equals to UPDATED_END_TIME
        defaultCaloriesBmrSummaryShouldNotBeFound("endTime.in=" + UPDATED_END_TIME);
    }

    @Test
    @Transactional
    void getAllCaloriesBmrSummariesByEndTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        caloriesBmrSummaryRepository.saveAndFlush(caloriesBmrSummary);

        // Get all the caloriesBmrSummaryList where endTime is not null
        defaultCaloriesBmrSummaryShouldBeFound("endTime.specified=true");

        // Get all the caloriesBmrSummaryList where endTime is null
        defaultCaloriesBmrSummaryShouldNotBeFound("endTime.specified=false");
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCaloriesBmrSummaryShouldBeFound(String filter) throws Exception {
        restCaloriesBmrSummaryMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(caloriesBmrSummary.getId().toString())))
            .andExpect(jsonPath("$.[*].usuarioId").value(hasItem(DEFAULT_USUARIO_ID)))
            .andExpect(jsonPath("$.[*].empresaId").value(hasItem(DEFAULT_EMPRESA_ID)))
            .andExpect(jsonPath("$.[*].fieldAverage").value(hasItem(DEFAULT_FIELD_AVERAGE.doubleValue())))
            .andExpect(jsonPath("$.[*].fieldMax").value(hasItem(DEFAULT_FIELD_MAX.doubleValue())))
            .andExpect(jsonPath("$.[*].fieldMin").value(hasItem(DEFAULT_FIELD_MIN.doubleValue())))
            .andExpect(jsonPath("$.[*].startTime").value(hasItem(DEFAULT_START_TIME.toString())))
            .andExpect(jsonPath("$.[*].endTime").value(hasItem(DEFAULT_END_TIME.toString())));

        // Check, that the count call also returns 1
        restCaloriesBmrSummaryMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCaloriesBmrSummaryShouldNotBeFound(String filter) throws Exception {
        restCaloriesBmrSummaryMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCaloriesBmrSummaryMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingCaloriesBmrSummary() throws Exception {
        // Get the caloriesBmrSummary
        restCaloriesBmrSummaryMockMvc.perform(get(ENTITY_API_URL_ID, UUID.randomUUID().toString())).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingCaloriesBmrSummary() throws Exception {
        // Initialize the database
        caloriesBmrSummaryRepository.saveAndFlush(caloriesBmrSummary);

        int databaseSizeBeforeUpdate = caloriesBmrSummaryRepository.findAll().size();

        // Update the caloriesBmrSummary
        CaloriesBmrSummary updatedCaloriesBmrSummary = caloriesBmrSummaryRepository.findById(caloriesBmrSummary.getId()).get();
        // Disconnect from session so that the updates on updatedCaloriesBmrSummary are not directly saved in db
        em.detach(updatedCaloriesBmrSummary);
        updatedCaloriesBmrSummary
            .usuarioId(UPDATED_USUARIO_ID)
            .empresaId(UPDATED_EMPRESA_ID)
            .fieldAverage(UPDATED_FIELD_AVERAGE)
            .fieldMax(UPDATED_FIELD_MAX)
            .fieldMin(UPDATED_FIELD_MIN)
            .startTime(UPDATED_START_TIME)
            .endTime(UPDATED_END_TIME);

        restCaloriesBmrSummaryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedCaloriesBmrSummary.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedCaloriesBmrSummary))
            )
            .andExpect(status().isOk());

        // Validate the CaloriesBmrSummary in the database
        List<CaloriesBmrSummary> caloriesBmrSummaryList = caloriesBmrSummaryRepository.findAll();
        assertThat(caloriesBmrSummaryList).hasSize(databaseSizeBeforeUpdate);
        CaloriesBmrSummary testCaloriesBmrSummary = caloriesBmrSummaryList.get(caloriesBmrSummaryList.size() - 1);
        assertThat(testCaloriesBmrSummary.getUsuarioId()).isEqualTo(UPDATED_USUARIO_ID);
        assertThat(testCaloriesBmrSummary.getEmpresaId()).isEqualTo(UPDATED_EMPRESA_ID);
        assertThat(testCaloriesBmrSummary.getFieldAverage()).isEqualTo(UPDATED_FIELD_AVERAGE);
        assertThat(testCaloriesBmrSummary.getFieldMax()).isEqualTo(UPDATED_FIELD_MAX);
        assertThat(testCaloriesBmrSummary.getFieldMin()).isEqualTo(UPDATED_FIELD_MIN);
        assertThat(testCaloriesBmrSummary.getStartTime()).isEqualTo(UPDATED_START_TIME);
        assertThat(testCaloriesBmrSummary.getEndTime()).isEqualTo(UPDATED_END_TIME);
    }

    @Test
    @Transactional
    void putNonExistingCaloriesBmrSummary() throws Exception {
        int databaseSizeBeforeUpdate = caloriesBmrSummaryRepository.findAll().size();
        caloriesBmrSummary.setId(UUID.randomUUID());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCaloriesBmrSummaryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, caloriesBmrSummary.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(caloriesBmrSummary))
            )
            .andExpect(status().isBadRequest());

        // Validate the CaloriesBmrSummary in the database
        List<CaloriesBmrSummary> caloriesBmrSummaryList = caloriesBmrSummaryRepository.findAll();
        assertThat(caloriesBmrSummaryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCaloriesBmrSummary() throws Exception {
        int databaseSizeBeforeUpdate = caloriesBmrSummaryRepository.findAll().size();
        caloriesBmrSummary.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCaloriesBmrSummaryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(caloriesBmrSummary))
            )
            .andExpect(status().isBadRequest());

        // Validate the CaloriesBmrSummary in the database
        List<CaloriesBmrSummary> caloriesBmrSummaryList = caloriesBmrSummaryRepository.findAll();
        assertThat(caloriesBmrSummaryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCaloriesBmrSummary() throws Exception {
        int databaseSizeBeforeUpdate = caloriesBmrSummaryRepository.findAll().size();
        caloriesBmrSummary.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCaloriesBmrSummaryMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(caloriesBmrSummary))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CaloriesBmrSummary in the database
        List<CaloriesBmrSummary> caloriesBmrSummaryList = caloriesBmrSummaryRepository.findAll();
        assertThat(caloriesBmrSummaryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCaloriesBmrSummaryWithPatch() throws Exception {
        // Initialize the database
        caloriesBmrSummaryRepository.saveAndFlush(caloriesBmrSummary);

        int databaseSizeBeforeUpdate = caloriesBmrSummaryRepository.findAll().size();

        // Update the caloriesBmrSummary using partial update
        CaloriesBmrSummary partialUpdatedCaloriesBmrSummary = new CaloriesBmrSummary();
        partialUpdatedCaloriesBmrSummary.setId(caloriesBmrSummary.getId());

        partialUpdatedCaloriesBmrSummary
            .usuarioId(UPDATED_USUARIO_ID)
            .fieldAverage(UPDATED_FIELD_AVERAGE)
            .fieldMax(UPDATED_FIELD_MAX)
            .endTime(UPDATED_END_TIME);

        restCaloriesBmrSummaryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCaloriesBmrSummary.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCaloriesBmrSummary))
            )
            .andExpect(status().isOk());

        // Validate the CaloriesBmrSummary in the database
        List<CaloriesBmrSummary> caloriesBmrSummaryList = caloriesBmrSummaryRepository.findAll();
        assertThat(caloriesBmrSummaryList).hasSize(databaseSizeBeforeUpdate);
        CaloriesBmrSummary testCaloriesBmrSummary = caloriesBmrSummaryList.get(caloriesBmrSummaryList.size() - 1);
        assertThat(testCaloriesBmrSummary.getUsuarioId()).isEqualTo(UPDATED_USUARIO_ID);
        assertThat(testCaloriesBmrSummary.getEmpresaId()).isEqualTo(DEFAULT_EMPRESA_ID);
        assertThat(testCaloriesBmrSummary.getFieldAverage()).isEqualTo(UPDATED_FIELD_AVERAGE);
        assertThat(testCaloriesBmrSummary.getFieldMax()).isEqualTo(UPDATED_FIELD_MAX);
        assertThat(testCaloriesBmrSummary.getFieldMin()).isEqualTo(DEFAULT_FIELD_MIN);
        assertThat(testCaloriesBmrSummary.getStartTime()).isEqualTo(DEFAULT_START_TIME);
        assertThat(testCaloriesBmrSummary.getEndTime()).isEqualTo(UPDATED_END_TIME);
    }

    @Test
    @Transactional
    void fullUpdateCaloriesBmrSummaryWithPatch() throws Exception {
        // Initialize the database
        caloriesBmrSummaryRepository.saveAndFlush(caloriesBmrSummary);

        int databaseSizeBeforeUpdate = caloriesBmrSummaryRepository.findAll().size();

        // Update the caloriesBmrSummary using partial update
        CaloriesBmrSummary partialUpdatedCaloriesBmrSummary = new CaloriesBmrSummary();
        partialUpdatedCaloriesBmrSummary.setId(caloriesBmrSummary.getId());

        partialUpdatedCaloriesBmrSummary
            .usuarioId(UPDATED_USUARIO_ID)
            .empresaId(UPDATED_EMPRESA_ID)
            .fieldAverage(UPDATED_FIELD_AVERAGE)
            .fieldMax(UPDATED_FIELD_MAX)
            .fieldMin(UPDATED_FIELD_MIN)
            .startTime(UPDATED_START_TIME)
            .endTime(UPDATED_END_TIME);

        restCaloriesBmrSummaryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCaloriesBmrSummary.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCaloriesBmrSummary))
            )
            .andExpect(status().isOk());

        // Validate the CaloriesBmrSummary in the database
        List<CaloriesBmrSummary> caloriesBmrSummaryList = caloriesBmrSummaryRepository.findAll();
        assertThat(caloriesBmrSummaryList).hasSize(databaseSizeBeforeUpdate);
        CaloriesBmrSummary testCaloriesBmrSummary = caloriesBmrSummaryList.get(caloriesBmrSummaryList.size() - 1);
        assertThat(testCaloriesBmrSummary.getUsuarioId()).isEqualTo(UPDATED_USUARIO_ID);
        assertThat(testCaloriesBmrSummary.getEmpresaId()).isEqualTo(UPDATED_EMPRESA_ID);
        assertThat(testCaloriesBmrSummary.getFieldAverage()).isEqualTo(UPDATED_FIELD_AVERAGE);
        assertThat(testCaloriesBmrSummary.getFieldMax()).isEqualTo(UPDATED_FIELD_MAX);
        assertThat(testCaloriesBmrSummary.getFieldMin()).isEqualTo(UPDATED_FIELD_MIN);
        assertThat(testCaloriesBmrSummary.getStartTime()).isEqualTo(UPDATED_START_TIME);
        assertThat(testCaloriesBmrSummary.getEndTime()).isEqualTo(UPDATED_END_TIME);
    }

    @Test
    @Transactional
    void patchNonExistingCaloriesBmrSummary() throws Exception {
        int databaseSizeBeforeUpdate = caloriesBmrSummaryRepository.findAll().size();
        caloriesBmrSummary.setId(UUID.randomUUID());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCaloriesBmrSummaryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, caloriesBmrSummary.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(caloriesBmrSummary))
            )
            .andExpect(status().isBadRequest());

        // Validate the CaloriesBmrSummary in the database
        List<CaloriesBmrSummary> caloriesBmrSummaryList = caloriesBmrSummaryRepository.findAll();
        assertThat(caloriesBmrSummaryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCaloriesBmrSummary() throws Exception {
        int databaseSizeBeforeUpdate = caloriesBmrSummaryRepository.findAll().size();
        caloriesBmrSummary.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCaloriesBmrSummaryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(caloriesBmrSummary))
            )
            .andExpect(status().isBadRequest());

        // Validate the CaloriesBmrSummary in the database
        List<CaloriesBmrSummary> caloriesBmrSummaryList = caloriesBmrSummaryRepository.findAll();
        assertThat(caloriesBmrSummaryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCaloriesBmrSummary() throws Exception {
        int databaseSizeBeforeUpdate = caloriesBmrSummaryRepository.findAll().size();
        caloriesBmrSummary.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCaloriesBmrSummaryMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(caloriesBmrSummary))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CaloriesBmrSummary in the database
        List<CaloriesBmrSummary> caloriesBmrSummaryList = caloriesBmrSummaryRepository.findAll();
        assertThat(caloriesBmrSummaryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCaloriesBmrSummary() throws Exception {
        // Initialize the database
        caloriesBmrSummaryRepository.saveAndFlush(caloriesBmrSummary);

        int databaseSizeBeforeDelete = caloriesBmrSummaryRepository.findAll().size();

        // Delete the caloriesBmrSummary
        restCaloriesBmrSummaryMockMvc
            .perform(delete(ENTITY_API_URL_ID, caloriesBmrSummary.getId().toString()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CaloriesBmrSummary> caloriesBmrSummaryList = caloriesBmrSummaryRepository.findAll();
        assertThat(caloriesBmrSummaryList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
