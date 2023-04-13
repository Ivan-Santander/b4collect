package com.be4tech.b4carecollect.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.be4tech.b4carecollect.IntegrationTest;
import com.be4tech.b4carecollect.domain.HeightSummary;
import com.be4tech.b4carecollect.repository.HeightSummaryRepository;
import com.be4tech.b4carecollect.service.criteria.HeightSummaryCriteria;
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
 * Integration tests for the {@link HeightSummaryResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class HeightSummaryResourceIT {

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

    private static final String ENTITY_API_URL = "/api/height-summaries";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private HeightSummaryRepository heightSummaryRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restHeightSummaryMockMvc;

    private HeightSummary heightSummary;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static HeightSummary createEntity(EntityManager em) {
        HeightSummary heightSummary = new HeightSummary()
            .usuarioId(DEFAULT_USUARIO_ID)
            .empresaId(DEFAULT_EMPRESA_ID)
            .fieldAverage(DEFAULT_FIELD_AVERAGE)
            .fieldMax(DEFAULT_FIELD_MAX)
            .fieldMin(DEFAULT_FIELD_MIN)
            .startTime(DEFAULT_START_TIME)
            .endTime(DEFAULT_END_TIME);
        return heightSummary;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static HeightSummary createUpdatedEntity(EntityManager em) {
        HeightSummary heightSummary = new HeightSummary()
            .usuarioId(UPDATED_USUARIO_ID)
            .empresaId(UPDATED_EMPRESA_ID)
            .fieldAverage(UPDATED_FIELD_AVERAGE)
            .fieldMax(UPDATED_FIELD_MAX)
            .fieldMin(UPDATED_FIELD_MIN)
            .startTime(UPDATED_START_TIME)
            .endTime(UPDATED_END_TIME);
        return heightSummary;
    }

    @BeforeEach
    public void initTest() {
        heightSummary = createEntity(em);
    }

    @Test
    @Transactional
    void createHeightSummary() throws Exception {
        int databaseSizeBeforeCreate = heightSummaryRepository.findAll().size();
        // Create the HeightSummary
        restHeightSummaryMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(heightSummary)))
            .andExpect(status().isCreated());

        // Validate the HeightSummary in the database
        List<HeightSummary> heightSummaryList = heightSummaryRepository.findAll();
        assertThat(heightSummaryList).hasSize(databaseSizeBeforeCreate + 1);
        HeightSummary testHeightSummary = heightSummaryList.get(heightSummaryList.size() - 1);
        assertThat(testHeightSummary.getUsuarioId()).isEqualTo(DEFAULT_USUARIO_ID);
        assertThat(testHeightSummary.getEmpresaId()).isEqualTo(DEFAULT_EMPRESA_ID);
        assertThat(testHeightSummary.getFieldAverage()).isEqualTo(DEFAULT_FIELD_AVERAGE);
        assertThat(testHeightSummary.getFieldMax()).isEqualTo(DEFAULT_FIELD_MAX);
        assertThat(testHeightSummary.getFieldMin()).isEqualTo(DEFAULT_FIELD_MIN);
        assertThat(testHeightSummary.getStartTime()).isEqualTo(DEFAULT_START_TIME);
        assertThat(testHeightSummary.getEndTime()).isEqualTo(DEFAULT_END_TIME);
    }

    @Test
    @Transactional
    void createHeightSummaryWithExistingId() throws Exception {
        // Create the HeightSummary with an existing ID
        heightSummaryRepository.saveAndFlush(heightSummary);

        int databaseSizeBeforeCreate = heightSummaryRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restHeightSummaryMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(heightSummary)))
            .andExpect(status().isBadRequest());

        // Validate the HeightSummary in the database
        List<HeightSummary> heightSummaryList = heightSummaryRepository.findAll();
        assertThat(heightSummaryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllHeightSummaries() throws Exception {
        // Initialize the database
        heightSummaryRepository.saveAndFlush(heightSummary);

        // Get all the heightSummaryList
        restHeightSummaryMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(heightSummary.getId().toString())))
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
    void getHeightSummary() throws Exception {
        // Initialize the database
        heightSummaryRepository.saveAndFlush(heightSummary);

        // Get the heightSummary
        restHeightSummaryMockMvc
            .perform(get(ENTITY_API_URL_ID, heightSummary.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(heightSummary.getId().toString()))
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
    void getHeightSummariesByIdFiltering() throws Exception {
        // Initialize the database
        heightSummaryRepository.saveAndFlush(heightSummary);

        UUID id = heightSummary.getId();

        defaultHeightSummaryShouldBeFound("id.equals=" + id);
        defaultHeightSummaryShouldNotBeFound("id.notEquals=" + id);
    }

    @Test
    @Transactional
    void getAllHeightSummariesByUsuarioIdIsEqualToSomething() throws Exception {
        // Initialize the database
        heightSummaryRepository.saveAndFlush(heightSummary);

        // Get all the heightSummaryList where usuarioId equals to DEFAULT_USUARIO_ID
        defaultHeightSummaryShouldBeFound("usuarioId.equals=" + DEFAULT_USUARIO_ID);

        // Get all the heightSummaryList where usuarioId equals to UPDATED_USUARIO_ID
        defaultHeightSummaryShouldNotBeFound("usuarioId.equals=" + UPDATED_USUARIO_ID);
    }

    @Test
    @Transactional
    void getAllHeightSummariesByUsuarioIdIsInShouldWork() throws Exception {
        // Initialize the database
        heightSummaryRepository.saveAndFlush(heightSummary);

        // Get all the heightSummaryList where usuarioId in DEFAULT_USUARIO_ID or UPDATED_USUARIO_ID
        defaultHeightSummaryShouldBeFound("usuarioId.in=" + DEFAULT_USUARIO_ID + "," + UPDATED_USUARIO_ID);

        // Get all the heightSummaryList where usuarioId equals to UPDATED_USUARIO_ID
        defaultHeightSummaryShouldNotBeFound("usuarioId.in=" + UPDATED_USUARIO_ID);
    }

    @Test
    @Transactional
    void getAllHeightSummariesByUsuarioIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        heightSummaryRepository.saveAndFlush(heightSummary);

        // Get all the heightSummaryList where usuarioId is not null
        defaultHeightSummaryShouldBeFound("usuarioId.specified=true");

        // Get all the heightSummaryList where usuarioId is null
        defaultHeightSummaryShouldNotBeFound("usuarioId.specified=false");
    }

    @Test
    @Transactional
    void getAllHeightSummariesByUsuarioIdContainsSomething() throws Exception {
        // Initialize the database
        heightSummaryRepository.saveAndFlush(heightSummary);

        // Get all the heightSummaryList where usuarioId contains DEFAULT_USUARIO_ID
        defaultHeightSummaryShouldBeFound("usuarioId.contains=" + DEFAULT_USUARIO_ID);

        // Get all the heightSummaryList where usuarioId contains UPDATED_USUARIO_ID
        defaultHeightSummaryShouldNotBeFound("usuarioId.contains=" + UPDATED_USUARIO_ID);
    }

    @Test
    @Transactional
    void getAllHeightSummariesByUsuarioIdNotContainsSomething() throws Exception {
        // Initialize the database
        heightSummaryRepository.saveAndFlush(heightSummary);

        // Get all the heightSummaryList where usuarioId does not contain DEFAULT_USUARIO_ID
        defaultHeightSummaryShouldNotBeFound("usuarioId.doesNotContain=" + DEFAULT_USUARIO_ID);

        // Get all the heightSummaryList where usuarioId does not contain UPDATED_USUARIO_ID
        defaultHeightSummaryShouldBeFound("usuarioId.doesNotContain=" + UPDATED_USUARIO_ID);
    }

    @Test
    @Transactional
    void getAllHeightSummariesByEmpresaIdIsEqualToSomething() throws Exception {
        // Initialize the database
        heightSummaryRepository.saveAndFlush(heightSummary);

        // Get all the heightSummaryList where empresaId equals to DEFAULT_EMPRESA_ID
        defaultHeightSummaryShouldBeFound("empresaId.equals=" + DEFAULT_EMPRESA_ID);

        // Get all the heightSummaryList where empresaId equals to UPDATED_EMPRESA_ID
        defaultHeightSummaryShouldNotBeFound("empresaId.equals=" + UPDATED_EMPRESA_ID);
    }

    @Test
    @Transactional
    void getAllHeightSummariesByEmpresaIdIsInShouldWork() throws Exception {
        // Initialize the database
        heightSummaryRepository.saveAndFlush(heightSummary);

        // Get all the heightSummaryList where empresaId in DEFAULT_EMPRESA_ID or UPDATED_EMPRESA_ID
        defaultHeightSummaryShouldBeFound("empresaId.in=" + DEFAULT_EMPRESA_ID + "," + UPDATED_EMPRESA_ID);

        // Get all the heightSummaryList where empresaId equals to UPDATED_EMPRESA_ID
        defaultHeightSummaryShouldNotBeFound("empresaId.in=" + UPDATED_EMPRESA_ID);
    }

    @Test
    @Transactional
    void getAllHeightSummariesByEmpresaIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        heightSummaryRepository.saveAndFlush(heightSummary);

        // Get all the heightSummaryList where empresaId is not null
        defaultHeightSummaryShouldBeFound("empresaId.specified=true");

        // Get all the heightSummaryList where empresaId is null
        defaultHeightSummaryShouldNotBeFound("empresaId.specified=false");
    }

    @Test
    @Transactional
    void getAllHeightSummariesByEmpresaIdContainsSomething() throws Exception {
        // Initialize the database
        heightSummaryRepository.saveAndFlush(heightSummary);

        // Get all the heightSummaryList where empresaId contains DEFAULT_EMPRESA_ID
        defaultHeightSummaryShouldBeFound("empresaId.contains=" + DEFAULT_EMPRESA_ID);

        // Get all the heightSummaryList where empresaId contains UPDATED_EMPRESA_ID
        defaultHeightSummaryShouldNotBeFound("empresaId.contains=" + UPDATED_EMPRESA_ID);
    }

    @Test
    @Transactional
    void getAllHeightSummariesByEmpresaIdNotContainsSomething() throws Exception {
        // Initialize the database
        heightSummaryRepository.saveAndFlush(heightSummary);

        // Get all the heightSummaryList where empresaId does not contain DEFAULT_EMPRESA_ID
        defaultHeightSummaryShouldNotBeFound("empresaId.doesNotContain=" + DEFAULT_EMPRESA_ID);

        // Get all the heightSummaryList where empresaId does not contain UPDATED_EMPRESA_ID
        defaultHeightSummaryShouldBeFound("empresaId.doesNotContain=" + UPDATED_EMPRESA_ID);
    }

    @Test
    @Transactional
    void getAllHeightSummariesByFieldAverageIsEqualToSomething() throws Exception {
        // Initialize the database
        heightSummaryRepository.saveAndFlush(heightSummary);

        // Get all the heightSummaryList where fieldAverage equals to DEFAULT_FIELD_AVERAGE
        defaultHeightSummaryShouldBeFound("fieldAverage.equals=" + DEFAULT_FIELD_AVERAGE);

        // Get all the heightSummaryList where fieldAverage equals to UPDATED_FIELD_AVERAGE
        defaultHeightSummaryShouldNotBeFound("fieldAverage.equals=" + UPDATED_FIELD_AVERAGE);
    }

    @Test
    @Transactional
    void getAllHeightSummariesByFieldAverageIsInShouldWork() throws Exception {
        // Initialize the database
        heightSummaryRepository.saveAndFlush(heightSummary);

        // Get all the heightSummaryList where fieldAverage in DEFAULT_FIELD_AVERAGE or UPDATED_FIELD_AVERAGE
        defaultHeightSummaryShouldBeFound("fieldAverage.in=" + DEFAULT_FIELD_AVERAGE + "," + UPDATED_FIELD_AVERAGE);

        // Get all the heightSummaryList where fieldAverage equals to UPDATED_FIELD_AVERAGE
        defaultHeightSummaryShouldNotBeFound("fieldAverage.in=" + UPDATED_FIELD_AVERAGE);
    }

    @Test
    @Transactional
    void getAllHeightSummariesByFieldAverageIsNullOrNotNull() throws Exception {
        // Initialize the database
        heightSummaryRepository.saveAndFlush(heightSummary);

        // Get all the heightSummaryList where fieldAverage is not null
        defaultHeightSummaryShouldBeFound("fieldAverage.specified=true");

        // Get all the heightSummaryList where fieldAverage is null
        defaultHeightSummaryShouldNotBeFound("fieldAverage.specified=false");
    }

    @Test
    @Transactional
    void getAllHeightSummariesByFieldAverageIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        heightSummaryRepository.saveAndFlush(heightSummary);

        // Get all the heightSummaryList where fieldAverage is greater than or equal to DEFAULT_FIELD_AVERAGE
        defaultHeightSummaryShouldBeFound("fieldAverage.greaterThanOrEqual=" + DEFAULT_FIELD_AVERAGE);

        // Get all the heightSummaryList where fieldAverage is greater than or equal to UPDATED_FIELD_AVERAGE
        defaultHeightSummaryShouldNotBeFound("fieldAverage.greaterThanOrEqual=" + UPDATED_FIELD_AVERAGE);
    }

    @Test
    @Transactional
    void getAllHeightSummariesByFieldAverageIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        heightSummaryRepository.saveAndFlush(heightSummary);

        // Get all the heightSummaryList where fieldAverage is less than or equal to DEFAULT_FIELD_AVERAGE
        defaultHeightSummaryShouldBeFound("fieldAverage.lessThanOrEqual=" + DEFAULT_FIELD_AVERAGE);

        // Get all the heightSummaryList where fieldAverage is less than or equal to SMALLER_FIELD_AVERAGE
        defaultHeightSummaryShouldNotBeFound("fieldAverage.lessThanOrEqual=" + SMALLER_FIELD_AVERAGE);
    }

    @Test
    @Transactional
    void getAllHeightSummariesByFieldAverageIsLessThanSomething() throws Exception {
        // Initialize the database
        heightSummaryRepository.saveAndFlush(heightSummary);

        // Get all the heightSummaryList where fieldAverage is less than DEFAULT_FIELD_AVERAGE
        defaultHeightSummaryShouldNotBeFound("fieldAverage.lessThan=" + DEFAULT_FIELD_AVERAGE);

        // Get all the heightSummaryList where fieldAverage is less than UPDATED_FIELD_AVERAGE
        defaultHeightSummaryShouldBeFound("fieldAverage.lessThan=" + UPDATED_FIELD_AVERAGE);
    }

    @Test
    @Transactional
    void getAllHeightSummariesByFieldAverageIsGreaterThanSomething() throws Exception {
        // Initialize the database
        heightSummaryRepository.saveAndFlush(heightSummary);

        // Get all the heightSummaryList where fieldAverage is greater than DEFAULT_FIELD_AVERAGE
        defaultHeightSummaryShouldNotBeFound("fieldAverage.greaterThan=" + DEFAULT_FIELD_AVERAGE);

        // Get all the heightSummaryList where fieldAverage is greater than SMALLER_FIELD_AVERAGE
        defaultHeightSummaryShouldBeFound("fieldAverage.greaterThan=" + SMALLER_FIELD_AVERAGE);
    }

    @Test
    @Transactional
    void getAllHeightSummariesByFieldMaxIsEqualToSomething() throws Exception {
        // Initialize the database
        heightSummaryRepository.saveAndFlush(heightSummary);

        // Get all the heightSummaryList where fieldMax equals to DEFAULT_FIELD_MAX
        defaultHeightSummaryShouldBeFound("fieldMax.equals=" + DEFAULT_FIELD_MAX);

        // Get all the heightSummaryList where fieldMax equals to UPDATED_FIELD_MAX
        defaultHeightSummaryShouldNotBeFound("fieldMax.equals=" + UPDATED_FIELD_MAX);
    }

    @Test
    @Transactional
    void getAllHeightSummariesByFieldMaxIsInShouldWork() throws Exception {
        // Initialize the database
        heightSummaryRepository.saveAndFlush(heightSummary);

        // Get all the heightSummaryList where fieldMax in DEFAULT_FIELD_MAX or UPDATED_FIELD_MAX
        defaultHeightSummaryShouldBeFound("fieldMax.in=" + DEFAULT_FIELD_MAX + "," + UPDATED_FIELD_MAX);

        // Get all the heightSummaryList where fieldMax equals to UPDATED_FIELD_MAX
        defaultHeightSummaryShouldNotBeFound("fieldMax.in=" + UPDATED_FIELD_MAX);
    }

    @Test
    @Transactional
    void getAllHeightSummariesByFieldMaxIsNullOrNotNull() throws Exception {
        // Initialize the database
        heightSummaryRepository.saveAndFlush(heightSummary);

        // Get all the heightSummaryList where fieldMax is not null
        defaultHeightSummaryShouldBeFound("fieldMax.specified=true");

        // Get all the heightSummaryList where fieldMax is null
        defaultHeightSummaryShouldNotBeFound("fieldMax.specified=false");
    }

    @Test
    @Transactional
    void getAllHeightSummariesByFieldMaxIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        heightSummaryRepository.saveAndFlush(heightSummary);

        // Get all the heightSummaryList where fieldMax is greater than or equal to DEFAULT_FIELD_MAX
        defaultHeightSummaryShouldBeFound("fieldMax.greaterThanOrEqual=" + DEFAULT_FIELD_MAX);

        // Get all the heightSummaryList where fieldMax is greater than or equal to UPDATED_FIELD_MAX
        defaultHeightSummaryShouldNotBeFound("fieldMax.greaterThanOrEqual=" + UPDATED_FIELD_MAX);
    }

    @Test
    @Transactional
    void getAllHeightSummariesByFieldMaxIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        heightSummaryRepository.saveAndFlush(heightSummary);

        // Get all the heightSummaryList where fieldMax is less than or equal to DEFAULT_FIELD_MAX
        defaultHeightSummaryShouldBeFound("fieldMax.lessThanOrEqual=" + DEFAULT_FIELD_MAX);

        // Get all the heightSummaryList where fieldMax is less than or equal to SMALLER_FIELD_MAX
        defaultHeightSummaryShouldNotBeFound("fieldMax.lessThanOrEqual=" + SMALLER_FIELD_MAX);
    }

    @Test
    @Transactional
    void getAllHeightSummariesByFieldMaxIsLessThanSomething() throws Exception {
        // Initialize the database
        heightSummaryRepository.saveAndFlush(heightSummary);

        // Get all the heightSummaryList where fieldMax is less than DEFAULT_FIELD_MAX
        defaultHeightSummaryShouldNotBeFound("fieldMax.lessThan=" + DEFAULT_FIELD_MAX);

        // Get all the heightSummaryList where fieldMax is less than UPDATED_FIELD_MAX
        defaultHeightSummaryShouldBeFound("fieldMax.lessThan=" + UPDATED_FIELD_MAX);
    }

    @Test
    @Transactional
    void getAllHeightSummariesByFieldMaxIsGreaterThanSomething() throws Exception {
        // Initialize the database
        heightSummaryRepository.saveAndFlush(heightSummary);

        // Get all the heightSummaryList where fieldMax is greater than DEFAULT_FIELD_MAX
        defaultHeightSummaryShouldNotBeFound("fieldMax.greaterThan=" + DEFAULT_FIELD_MAX);

        // Get all the heightSummaryList where fieldMax is greater than SMALLER_FIELD_MAX
        defaultHeightSummaryShouldBeFound("fieldMax.greaterThan=" + SMALLER_FIELD_MAX);
    }

    @Test
    @Transactional
    void getAllHeightSummariesByFieldMinIsEqualToSomething() throws Exception {
        // Initialize the database
        heightSummaryRepository.saveAndFlush(heightSummary);

        // Get all the heightSummaryList where fieldMin equals to DEFAULT_FIELD_MIN
        defaultHeightSummaryShouldBeFound("fieldMin.equals=" + DEFAULT_FIELD_MIN);

        // Get all the heightSummaryList where fieldMin equals to UPDATED_FIELD_MIN
        defaultHeightSummaryShouldNotBeFound("fieldMin.equals=" + UPDATED_FIELD_MIN);
    }

    @Test
    @Transactional
    void getAllHeightSummariesByFieldMinIsInShouldWork() throws Exception {
        // Initialize the database
        heightSummaryRepository.saveAndFlush(heightSummary);

        // Get all the heightSummaryList where fieldMin in DEFAULT_FIELD_MIN or UPDATED_FIELD_MIN
        defaultHeightSummaryShouldBeFound("fieldMin.in=" + DEFAULT_FIELD_MIN + "," + UPDATED_FIELD_MIN);

        // Get all the heightSummaryList where fieldMin equals to UPDATED_FIELD_MIN
        defaultHeightSummaryShouldNotBeFound("fieldMin.in=" + UPDATED_FIELD_MIN);
    }

    @Test
    @Transactional
    void getAllHeightSummariesByFieldMinIsNullOrNotNull() throws Exception {
        // Initialize the database
        heightSummaryRepository.saveAndFlush(heightSummary);

        // Get all the heightSummaryList where fieldMin is not null
        defaultHeightSummaryShouldBeFound("fieldMin.specified=true");

        // Get all the heightSummaryList where fieldMin is null
        defaultHeightSummaryShouldNotBeFound("fieldMin.specified=false");
    }

    @Test
    @Transactional
    void getAllHeightSummariesByFieldMinIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        heightSummaryRepository.saveAndFlush(heightSummary);

        // Get all the heightSummaryList where fieldMin is greater than or equal to DEFAULT_FIELD_MIN
        defaultHeightSummaryShouldBeFound("fieldMin.greaterThanOrEqual=" + DEFAULT_FIELD_MIN);

        // Get all the heightSummaryList where fieldMin is greater than or equal to UPDATED_FIELD_MIN
        defaultHeightSummaryShouldNotBeFound("fieldMin.greaterThanOrEqual=" + UPDATED_FIELD_MIN);
    }

    @Test
    @Transactional
    void getAllHeightSummariesByFieldMinIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        heightSummaryRepository.saveAndFlush(heightSummary);

        // Get all the heightSummaryList where fieldMin is less than or equal to DEFAULT_FIELD_MIN
        defaultHeightSummaryShouldBeFound("fieldMin.lessThanOrEqual=" + DEFAULT_FIELD_MIN);

        // Get all the heightSummaryList where fieldMin is less than or equal to SMALLER_FIELD_MIN
        defaultHeightSummaryShouldNotBeFound("fieldMin.lessThanOrEqual=" + SMALLER_FIELD_MIN);
    }

    @Test
    @Transactional
    void getAllHeightSummariesByFieldMinIsLessThanSomething() throws Exception {
        // Initialize the database
        heightSummaryRepository.saveAndFlush(heightSummary);

        // Get all the heightSummaryList where fieldMin is less than DEFAULT_FIELD_MIN
        defaultHeightSummaryShouldNotBeFound("fieldMin.lessThan=" + DEFAULT_FIELD_MIN);

        // Get all the heightSummaryList where fieldMin is less than UPDATED_FIELD_MIN
        defaultHeightSummaryShouldBeFound("fieldMin.lessThan=" + UPDATED_FIELD_MIN);
    }

    @Test
    @Transactional
    void getAllHeightSummariesByFieldMinIsGreaterThanSomething() throws Exception {
        // Initialize the database
        heightSummaryRepository.saveAndFlush(heightSummary);

        // Get all the heightSummaryList where fieldMin is greater than DEFAULT_FIELD_MIN
        defaultHeightSummaryShouldNotBeFound("fieldMin.greaterThan=" + DEFAULT_FIELD_MIN);

        // Get all the heightSummaryList where fieldMin is greater than SMALLER_FIELD_MIN
        defaultHeightSummaryShouldBeFound("fieldMin.greaterThan=" + SMALLER_FIELD_MIN);
    }

    @Test
    @Transactional
    void getAllHeightSummariesByStartTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        heightSummaryRepository.saveAndFlush(heightSummary);

        // Get all the heightSummaryList where startTime equals to DEFAULT_START_TIME
        defaultHeightSummaryShouldBeFound("startTime.equals=" + DEFAULT_START_TIME);

        // Get all the heightSummaryList where startTime equals to UPDATED_START_TIME
        defaultHeightSummaryShouldNotBeFound("startTime.equals=" + UPDATED_START_TIME);
    }

    @Test
    @Transactional
    void getAllHeightSummariesByStartTimeIsInShouldWork() throws Exception {
        // Initialize the database
        heightSummaryRepository.saveAndFlush(heightSummary);

        // Get all the heightSummaryList where startTime in DEFAULT_START_TIME or UPDATED_START_TIME
        defaultHeightSummaryShouldBeFound("startTime.in=" + DEFAULT_START_TIME + "," + UPDATED_START_TIME);

        // Get all the heightSummaryList where startTime equals to UPDATED_START_TIME
        defaultHeightSummaryShouldNotBeFound("startTime.in=" + UPDATED_START_TIME);
    }

    @Test
    @Transactional
    void getAllHeightSummariesByStartTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        heightSummaryRepository.saveAndFlush(heightSummary);

        // Get all the heightSummaryList where startTime is not null
        defaultHeightSummaryShouldBeFound("startTime.specified=true");

        // Get all the heightSummaryList where startTime is null
        defaultHeightSummaryShouldNotBeFound("startTime.specified=false");
    }

    @Test
    @Transactional
    void getAllHeightSummariesByEndTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        heightSummaryRepository.saveAndFlush(heightSummary);

        // Get all the heightSummaryList where endTime equals to DEFAULT_END_TIME
        defaultHeightSummaryShouldBeFound("endTime.equals=" + DEFAULT_END_TIME);

        // Get all the heightSummaryList where endTime equals to UPDATED_END_TIME
        defaultHeightSummaryShouldNotBeFound("endTime.equals=" + UPDATED_END_TIME);
    }

    @Test
    @Transactional
    void getAllHeightSummariesByEndTimeIsInShouldWork() throws Exception {
        // Initialize the database
        heightSummaryRepository.saveAndFlush(heightSummary);

        // Get all the heightSummaryList where endTime in DEFAULT_END_TIME or UPDATED_END_TIME
        defaultHeightSummaryShouldBeFound("endTime.in=" + DEFAULT_END_TIME + "," + UPDATED_END_TIME);

        // Get all the heightSummaryList where endTime equals to UPDATED_END_TIME
        defaultHeightSummaryShouldNotBeFound("endTime.in=" + UPDATED_END_TIME);
    }

    @Test
    @Transactional
    void getAllHeightSummariesByEndTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        heightSummaryRepository.saveAndFlush(heightSummary);

        // Get all the heightSummaryList where endTime is not null
        defaultHeightSummaryShouldBeFound("endTime.specified=true");

        // Get all the heightSummaryList where endTime is null
        defaultHeightSummaryShouldNotBeFound("endTime.specified=false");
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultHeightSummaryShouldBeFound(String filter) throws Exception {
        restHeightSummaryMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(heightSummary.getId().toString())))
            .andExpect(jsonPath("$.[*].usuarioId").value(hasItem(DEFAULT_USUARIO_ID)))
            .andExpect(jsonPath("$.[*].empresaId").value(hasItem(DEFAULT_EMPRESA_ID)))
            .andExpect(jsonPath("$.[*].fieldAverage").value(hasItem(DEFAULT_FIELD_AVERAGE.doubleValue())))
            .andExpect(jsonPath("$.[*].fieldMax").value(hasItem(DEFAULT_FIELD_MAX.doubleValue())))
            .andExpect(jsonPath("$.[*].fieldMin").value(hasItem(DEFAULT_FIELD_MIN.doubleValue())))
            .andExpect(jsonPath("$.[*].startTime").value(hasItem(DEFAULT_START_TIME.toString())))
            .andExpect(jsonPath("$.[*].endTime").value(hasItem(DEFAULT_END_TIME.toString())));

        // Check, that the count call also returns 1
        restHeightSummaryMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultHeightSummaryShouldNotBeFound(String filter) throws Exception {
        restHeightSummaryMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restHeightSummaryMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingHeightSummary() throws Exception {
        // Get the heightSummary
        restHeightSummaryMockMvc.perform(get(ENTITY_API_URL_ID, UUID.randomUUID().toString())).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingHeightSummary() throws Exception {
        // Initialize the database
        heightSummaryRepository.saveAndFlush(heightSummary);

        int databaseSizeBeforeUpdate = heightSummaryRepository.findAll().size();

        // Update the heightSummary
        HeightSummary updatedHeightSummary = heightSummaryRepository.findById(heightSummary.getId()).get();
        // Disconnect from session so that the updates on updatedHeightSummary are not directly saved in db
        em.detach(updatedHeightSummary);
        updatedHeightSummary
            .usuarioId(UPDATED_USUARIO_ID)
            .empresaId(UPDATED_EMPRESA_ID)
            .fieldAverage(UPDATED_FIELD_AVERAGE)
            .fieldMax(UPDATED_FIELD_MAX)
            .fieldMin(UPDATED_FIELD_MIN)
            .startTime(UPDATED_START_TIME)
            .endTime(UPDATED_END_TIME);

        restHeightSummaryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedHeightSummary.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedHeightSummary))
            )
            .andExpect(status().isOk());

        // Validate the HeightSummary in the database
        List<HeightSummary> heightSummaryList = heightSummaryRepository.findAll();
        assertThat(heightSummaryList).hasSize(databaseSizeBeforeUpdate);
        HeightSummary testHeightSummary = heightSummaryList.get(heightSummaryList.size() - 1);
        assertThat(testHeightSummary.getUsuarioId()).isEqualTo(UPDATED_USUARIO_ID);
        assertThat(testHeightSummary.getEmpresaId()).isEqualTo(UPDATED_EMPRESA_ID);
        assertThat(testHeightSummary.getFieldAverage()).isEqualTo(UPDATED_FIELD_AVERAGE);
        assertThat(testHeightSummary.getFieldMax()).isEqualTo(UPDATED_FIELD_MAX);
        assertThat(testHeightSummary.getFieldMin()).isEqualTo(UPDATED_FIELD_MIN);
        assertThat(testHeightSummary.getStartTime()).isEqualTo(UPDATED_START_TIME);
        assertThat(testHeightSummary.getEndTime()).isEqualTo(UPDATED_END_TIME);
    }

    @Test
    @Transactional
    void putNonExistingHeightSummary() throws Exception {
        int databaseSizeBeforeUpdate = heightSummaryRepository.findAll().size();
        heightSummary.setId(UUID.randomUUID());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHeightSummaryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, heightSummary.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(heightSummary))
            )
            .andExpect(status().isBadRequest());

        // Validate the HeightSummary in the database
        List<HeightSummary> heightSummaryList = heightSummaryRepository.findAll();
        assertThat(heightSummaryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchHeightSummary() throws Exception {
        int databaseSizeBeforeUpdate = heightSummaryRepository.findAll().size();
        heightSummary.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHeightSummaryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(heightSummary))
            )
            .andExpect(status().isBadRequest());

        // Validate the HeightSummary in the database
        List<HeightSummary> heightSummaryList = heightSummaryRepository.findAll();
        assertThat(heightSummaryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamHeightSummary() throws Exception {
        int databaseSizeBeforeUpdate = heightSummaryRepository.findAll().size();
        heightSummary.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHeightSummaryMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(heightSummary)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the HeightSummary in the database
        List<HeightSummary> heightSummaryList = heightSummaryRepository.findAll();
        assertThat(heightSummaryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateHeightSummaryWithPatch() throws Exception {
        // Initialize the database
        heightSummaryRepository.saveAndFlush(heightSummary);

        int databaseSizeBeforeUpdate = heightSummaryRepository.findAll().size();

        // Update the heightSummary using partial update
        HeightSummary partialUpdatedHeightSummary = new HeightSummary();
        partialUpdatedHeightSummary.setId(heightSummary.getId());

        restHeightSummaryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedHeightSummary.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedHeightSummary))
            )
            .andExpect(status().isOk());

        // Validate the HeightSummary in the database
        List<HeightSummary> heightSummaryList = heightSummaryRepository.findAll();
        assertThat(heightSummaryList).hasSize(databaseSizeBeforeUpdate);
        HeightSummary testHeightSummary = heightSummaryList.get(heightSummaryList.size() - 1);
        assertThat(testHeightSummary.getUsuarioId()).isEqualTo(DEFAULT_USUARIO_ID);
        assertThat(testHeightSummary.getEmpresaId()).isEqualTo(DEFAULT_EMPRESA_ID);
        assertThat(testHeightSummary.getFieldAverage()).isEqualTo(DEFAULT_FIELD_AVERAGE);
        assertThat(testHeightSummary.getFieldMax()).isEqualTo(DEFAULT_FIELD_MAX);
        assertThat(testHeightSummary.getFieldMin()).isEqualTo(DEFAULT_FIELD_MIN);
        assertThat(testHeightSummary.getStartTime()).isEqualTo(DEFAULT_START_TIME);
        assertThat(testHeightSummary.getEndTime()).isEqualTo(DEFAULT_END_TIME);
    }

    @Test
    @Transactional
    void fullUpdateHeightSummaryWithPatch() throws Exception {
        // Initialize the database
        heightSummaryRepository.saveAndFlush(heightSummary);

        int databaseSizeBeforeUpdate = heightSummaryRepository.findAll().size();

        // Update the heightSummary using partial update
        HeightSummary partialUpdatedHeightSummary = new HeightSummary();
        partialUpdatedHeightSummary.setId(heightSummary.getId());

        partialUpdatedHeightSummary
            .usuarioId(UPDATED_USUARIO_ID)
            .empresaId(UPDATED_EMPRESA_ID)
            .fieldAverage(UPDATED_FIELD_AVERAGE)
            .fieldMax(UPDATED_FIELD_MAX)
            .fieldMin(UPDATED_FIELD_MIN)
            .startTime(UPDATED_START_TIME)
            .endTime(UPDATED_END_TIME);

        restHeightSummaryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedHeightSummary.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedHeightSummary))
            )
            .andExpect(status().isOk());

        // Validate the HeightSummary in the database
        List<HeightSummary> heightSummaryList = heightSummaryRepository.findAll();
        assertThat(heightSummaryList).hasSize(databaseSizeBeforeUpdate);
        HeightSummary testHeightSummary = heightSummaryList.get(heightSummaryList.size() - 1);
        assertThat(testHeightSummary.getUsuarioId()).isEqualTo(UPDATED_USUARIO_ID);
        assertThat(testHeightSummary.getEmpresaId()).isEqualTo(UPDATED_EMPRESA_ID);
        assertThat(testHeightSummary.getFieldAverage()).isEqualTo(UPDATED_FIELD_AVERAGE);
        assertThat(testHeightSummary.getFieldMax()).isEqualTo(UPDATED_FIELD_MAX);
        assertThat(testHeightSummary.getFieldMin()).isEqualTo(UPDATED_FIELD_MIN);
        assertThat(testHeightSummary.getStartTime()).isEqualTo(UPDATED_START_TIME);
        assertThat(testHeightSummary.getEndTime()).isEqualTo(UPDATED_END_TIME);
    }

    @Test
    @Transactional
    void patchNonExistingHeightSummary() throws Exception {
        int databaseSizeBeforeUpdate = heightSummaryRepository.findAll().size();
        heightSummary.setId(UUID.randomUUID());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHeightSummaryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, heightSummary.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(heightSummary))
            )
            .andExpect(status().isBadRequest());

        // Validate the HeightSummary in the database
        List<HeightSummary> heightSummaryList = heightSummaryRepository.findAll();
        assertThat(heightSummaryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchHeightSummary() throws Exception {
        int databaseSizeBeforeUpdate = heightSummaryRepository.findAll().size();
        heightSummary.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHeightSummaryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(heightSummary))
            )
            .andExpect(status().isBadRequest());

        // Validate the HeightSummary in the database
        List<HeightSummary> heightSummaryList = heightSummaryRepository.findAll();
        assertThat(heightSummaryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamHeightSummary() throws Exception {
        int databaseSizeBeforeUpdate = heightSummaryRepository.findAll().size();
        heightSummary.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHeightSummaryMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(heightSummary))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the HeightSummary in the database
        List<HeightSummary> heightSummaryList = heightSummaryRepository.findAll();
        assertThat(heightSummaryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteHeightSummary() throws Exception {
        // Initialize the database
        heightSummaryRepository.saveAndFlush(heightSummary);

        int databaseSizeBeforeDelete = heightSummaryRepository.findAll().size();

        // Delete the heightSummary
        restHeightSummaryMockMvc
            .perform(delete(ENTITY_API_URL_ID, heightSummary.getId().toString()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<HeightSummary> heightSummaryList = heightSummaryRepository.findAll();
        assertThat(heightSummaryList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
