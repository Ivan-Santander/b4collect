package com.be4tech.b4carecollect.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.be4tech.b4carecollect.IntegrationTest;
import com.be4tech.b4carecollect.domain.HeartRateSummary;
import com.be4tech.b4carecollect.repository.HeartRateSummaryRepository;
import com.be4tech.b4carecollect.service.criteria.HeartRateSummaryCriteria;
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
 * Integration tests for the {@link HeartRateSummaryResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class HeartRateSummaryResourceIT {

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

    private static final String ENTITY_API_URL = "/api/heart-rate-summaries";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private HeartRateSummaryRepository heartRateSummaryRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restHeartRateSummaryMockMvc;

    private HeartRateSummary heartRateSummary;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static HeartRateSummary createEntity(EntityManager em) {
        HeartRateSummary heartRateSummary = new HeartRateSummary()
            .usuarioId(DEFAULT_USUARIO_ID)
            .empresaId(DEFAULT_EMPRESA_ID)
            .fieldAverage(DEFAULT_FIELD_AVERAGE)
            .fieldMax(DEFAULT_FIELD_MAX)
            .fieldMin(DEFAULT_FIELD_MIN)
            .startTime(DEFAULT_START_TIME)
            .endTime(DEFAULT_END_TIME);
        return heartRateSummary;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static HeartRateSummary createUpdatedEntity(EntityManager em) {
        HeartRateSummary heartRateSummary = new HeartRateSummary()
            .usuarioId(UPDATED_USUARIO_ID)
            .empresaId(UPDATED_EMPRESA_ID)
            .fieldAverage(UPDATED_FIELD_AVERAGE)
            .fieldMax(UPDATED_FIELD_MAX)
            .fieldMin(UPDATED_FIELD_MIN)
            .startTime(UPDATED_START_TIME)
            .endTime(UPDATED_END_TIME);
        return heartRateSummary;
    }

    @BeforeEach
    public void initTest() {
        heartRateSummary = createEntity(em);
    }

    @Test
    @Transactional
    void createHeartRateSummary() throws Exception {
        int databaseSizeBeforeCreate = heartRateSummaryRepository.findAll().size();
        // Create the HeartRateSummary
        restHeartRateSummaryMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(heartRateSummary))
            )
            .andExpect(status().isCreated());

        // Validate the HeartRateSummary in the database
        List<HeartRateSummary> heartRateSummaryList = heartRateSummaryRepository.findAll();
        assertThat(heartRateSummaryList).hasSize(databaseSizeBeforeCreate + 1);
        HeartRateSummary testHeartRateSummary = heartRateSummaryList.get(heartRateSummaryList.size() - 1);
        assertThat(testHeartRateSummary.getUsuarioId()).isEqualTo(DEFAULT_USUARIO_ID);
        assertThat(testHeartRateSummary.getEmpresaId()).isEqualTo(DEFAULT_EMPRESA_ID);
        assertThat(testHeartRateSummary.getFieldAverage()).isEqualTo(DEFAULT_FIELD_AVERAGE);
        assertThat(testHeartRateSummary.getFieldMax()).isEqualTo(DEFAULT_FIELD_MAX);
        assertThat(testHeartRateSummary.getFieldMin()).isEqualTo(DEFAULT_FIELD_MIN);
        assertThat(testHeartRateSummary.getStartTime()).isEqualTo(DEFAULT_START_TIME);
        assertThat(testHeartRateSummary.getEndTime()).isEqualTo(DEFAULT_END_TIME);
    }

    @Test
    @Transactional
    void createHeartRateSummaryWithExistingId() throws Exception {
        // Create the HeartRateSummary with an existing ID
        heartRateSummaryRepository.saveAndFlush(heartRateSummary);

        int databaseSizeBeforeCreate = heartRateSummaryRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restHeartRateSummaryMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(heartRateSummary))
            )
            .andExpect(status().isBadRequest());

        // Validate the HeartRateSummary in the database
        List<HeartRateSummary> heartRateSummaryList = heartRateSummaryRepository.findAll();
        assertThat(heartRateSummaryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllHeartRateSummaries() throws Exception {
        // Initialize the database
        heartRateSummaryRepository.saveAndFlush(heartRateSummary);

        // Get all the heartRateSummaryList
        restHeartRateSummaryMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(heartRateSummary.getId().toString())))
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
    void getHeartRateSummary() throws Exception {
        // Initialize the database
        heartRateSummaryRepository.saveAndFlush(heartRateSummary);

        // Get the heartRateSummary
        restHeartRateSummaryMockMvc
            .perform(get(ENTITY_API_URL_ID, heartRateSummary.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(heartRateSummary.getId().toString()))
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
    void getHeartRateSummariesByIdFiltering() throws Exception {
        // Initialize the database
        heartRateSummaryRepository.saveAndFlush(heartRateSummary);

        UUID id = heartRateSummary.getId();

        defaultHeartRateSummaryShouldBeFound("id.equals=" + id);
        defaultHeartRateSummaryShouldNotBeFound("id.notEquals=" + id);
    }

    @Test
    @Transactional
    void getAllHeartRateSummariesByUsuarioIdIsEqualToSomething() throws Exception {
        // Initialize the database
        heartRateSummaryRepository.saveAndFlush(heartRateSummary);

        // Get all the heartRateSummaryList where usuarioId equals to DEFAULT_USUARIO_ID
        defaultHeartRateSummaryShouldBeFound("usuarioId.equals=" + DEFAULT_USUARIO_ID);

        // Get all the heartRateSummaryList where usuarioId equals to UPDATED_USUARIO_ID
        defaultHeartRateSummaryShouldNotBeFound("usuarioId.equals=" + UPDATED_USUARIO_ID);
    }

    @Test
    @Transactional
    void getAllHeartRateSummariesByUsuarioIdIsInShouldWork() throws Exception {
        // Initialize the database
        heartRateSummaryRepository.saveAndFlush(heartRateSummary);

        // Get all the heartRateSummaryList where usuarioId in DEFAULT_USUARIO_ID or UPDATED_USUARIO_ID
        defaultHeartRateSummaryShouldBeFound("usuarioId.in=" + DEFAULT_USUARIO_ID + "," + UPDATED_USUARIO_ID);

        // Get all the heartRateSummaryList where usuarioId equals to UPDATED_USUARIO_ID
        defaultHeartRateSummaryShouldNotBeFound("usuarioId.in=" + UPDATED_USUARIO_ID);
    }

    @Test
    @Transactional
    void getAllHeartRateSummariesByUsuarioIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        heartRateSummaryRepository.saveAndFlush(heartRateSummary);

        // Get all the heartRateSummaryList where usuarioId is not null
        defaultHeartRateSummaryShouldBeFound("usuarioId.specified=true");

        // Get all the heartRateSummaryList where usuarioId is null
        defaultHeartRateSummaryShouldNotBeFound("usuarioId.specified=false");
    }

    @Test
    @Transactional
    void getAllHeartRateSummariesByUsuarioIdContainsSomething() throws Exception {
        // Initialize the database
        heartRateSummaryRepository.saveAndFlush(heartRateSummary);

        // Get all the heartRateSummaryList where usuarioId contains DEFAULT_USUARIO_ID
        defaultHeartRateSummaryShouldBeFound("usuarioId.contains=" + DEFAULT_USUARIO_ID);

        // Get all the heartRateSummaryList where usuarioId contains UPDATED_USUARIO_ID
        defaultHeartRateSummaryShouldNotBeFound("usuarioId.contains=" + UPDATED_USUARIO_ID);
    }

    @Test
    @Transactional
    void getAllHeartRateSummariesByUsuarioIdNotContainsSomething() throws Exception {
        // Initialize the database
        heartRateSummaryRepository.saveAndFlush(heartRateSummary);

        // Get all the heartRateSummaryList where usuarioId does not contain DEFAULT_USUARIO_ID
        defaultHeartRateSummaryShouldNotBeFound("usuarioId.doesNotContain=" + DEFAULT_USUARIO_ID);

        // Get all the heartRateSummaryList where usuarioId does not contain UPDATED_USUARIO_ID
        defaultHeartRateSummaryShouldBeFound("usuarioId.doesNotContain=" + UPDATED_USUARIO_ID);
    }

    @Test
    @Transactional
    void getAllHeartRateSummariesByEmpresaIdIsEqualToSomething() throws Exception {
        // Initialize the database
        heartRateSummaryRepository.saveAndFlush(heartRateSummary);

        // Get all the heartRateSummaryList where empresaId equals to DEFAULT_EMPRESA_ID
        defaultHeartRateSummaryShouldBeFound("empresaId.equals=" + DEFAULT_EMPRESA_ID);

        // Get all the heartRateSummaryList where empresaId equals to UPDATED_EMPRESA_ID
        defaultHeartRateSummaryShouldNotBeFound("empresaId.equals=" + UPDATED_EMPRESA_ID);
    }

    @Test
    @Transactional
    void getAllHeartRateSummariesByEmpresaIdIsInShouldWork() throws Exception {
        // Initialize the database
        heartRateSummaryRepository.saveAndFlush(heartRateSummary);

        // Get all the heartRateSummaryList where empresaId in DEFAULT_EMPRESA_ID or UPDATED_EMPRESA_ID
        defaultHeartRateSummaryShouldBeFound("empresaId.in=" + DEFAULT_EMPRESA_ID + "," + UPDATED_EMPRESA_ID);

        // Get all the heartRateSummaryList where empresaId equals to UPDATED_EMPRESA_ID
        defaultHeartRateSummaryShouldNotBeFound("empresaId.in=" + UPDATED_EMPRESA_ID);
    }

    @Test
    @Transactional
    void getAllHeartRateSummariesByEmpresaIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        heartRateSummaryRepository.saveAndFlush(heartRateSummary);

        // Get all the heartRateSummaryList where empresaId is not null
        defaultHeartRateSummaryShouldBeFound("empresaId.specified=true");

        // Get all the heartRateSummaryList where empresaId is null
        defaultHeartRateSummaryShouldNotBeFound("empresaId.specified=false");
    }

    @Test
    @Transactional
    void getAllHeartRateSummariesByEmpresaIdContainsSomething() throws Exception {
        // Initialize the database
        heartRateSummaryRepository.saveAndFlush(heartRateSummary);

        // Get all the heartRateSummaryList where empresaId contains DEFAULT_EMPRESA_ID
        defaultHeartRateSummaryShouldBeFound("empresaId.contains=" + DEFAULT_EMPRESA_ID);

        // Get all the heartRateSummaryList where empresaId contains UPDATED_EMPRESA_ID
        defaultHeartRateSummaryShouldNotBeFound("empresaId.contains=" + UPDATED_EMPRESA_ID);
    }

    @Test
    @Transactional
    void getAllHeartRateSummariesByEmpresaIdNotContainsSomething() throws Exception {
        // Initialize the database
        heartRateSummaryRepository.saveAndFlush(heartRateSummary);

        // Get all the heartRateSummaryList where empresaId does not contain DEFAULT_EMPRESA_ID
        defaultHeartRateSummaryShouldNotBeFound("empresaId.doesNotContain=" + DEFAULT_EMPRESA_ID);

        // Get all the heartRateSummaryList where empresaId does not contain UPDATED_EMPRESA_ID
        defaultHeartRateSummaryShouldBeFound("empresaId.doesNotContain=" + UPDATED_EMPRESA_ID);
    }

    @Test
    @Transactional
    void getAllHeartRateSummariesByFieldAverageIsEqualToSomething() throws Exception {
        // Initialize the database
        heartRateSummaryRepository.saveAndFlush(heartRateSummary);

        // Get all the heartRateSummaryList where fieldAverage equals to DEFAULT_FIELD_AVERAGE
        defaultHeartRateSummaryShouldBeFound("fieldAverage.equals=" + DEFAULT_FIELD_AVERAGE);

        // Get all the heartRateSummaryList where fieldAverage equals to UPDATED_FIELD_AVERAGE
        defaultHeartRateSummaryShouldNotBeFound("fieldAverage.equals=" + UPDATED_FIELD_AVERAGE);
    }

    @Test
    @Transactional
    void getAllHeartRateSummariesByFieldAverageIsInShouldWork() throws Exception {
        // Initialize the database
        heartRateSummaryRepository.saveAndFlush(heartRateSummary);

        // Get all the heartRateSummaryList where fieldAverage in DEFAULT_FIELD_AVERAGE or UPDATED_FIELD_AVERAGE
        defaultHeartRateSummaryShouldBeFound("fieldAverage.in=" + DEFAULT_FIELD_AVERAGE + "," + UPDATED_FIELD_AVERAGE);

        // Get all the heartRateSummaryList where fieldAverage equals to UPDATED_FIELD_AVERAGE
        defaultHeartRateSummaryShouldNotBeFound("fieldAverage.in=" + UPDATED_FIELD_AVERAGE);
    }

    @Test
    @Transactional
    void getAllHeartRateSummariesByFieldAverageIsNullOrNotNull() throws Exception {
        // Initialize the database
        heartRateSummaryRepository.saveAndFlush(heartRateSummary);

        // Get all the heartRateSummaryList where fieldAverage is not null
        defaultHeartRateSummaryShouldBeFound("fieldAverage.specified=true");

        // Get all the heartRateSummaryList where fieldAverage is null
        defaultHeartRateSummaryShouldNotBeFound("fieldAverage.specified=false");
    }

    @Test
    @Transactional
    void getAllHeartRateSummariesByFieldAverageIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        heartRateSummaryRepository.saveAndFlush(heartRateSummary);

        // Get all the heartRateSummaryList where fieldAverage is greater than or equal to DEFAULT_FIELD_AVERAGE
        defaultHeartRateSummaryShouldBeFound("fieldAverage.greaterThanOrEqual=" + DEFAULT_FIELD_AVERAGE);

        // Get all the heartRateSummaryList where fieldAverage is greater than or equal to UPDATED_FIELD_AVERAGE
        defaultHeartRateSummaryShouldNotBeFound("fieldAverage.greaterThanOrEqual=" + UPDATED_FIELD_AVERAGE);
    }

    @Test
    @Transactional
    void getAllHeartRateSummariesByFieldAverageIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        heartRateSummaryRepository.saveAndFlush(heartRateSummary);

        // Get all the heartRateSummaryList where fieldAverage is less than or equal to DEFAULT_FIELD_AVERAGE
        defaultHeartRateSummaryShouldBeFound("fieldAverage.lessThanOrEqual=" + DEFAULT_FIELD_AVERAGE);

        // Get all the heartRateSummaryList where fieldAverage is less than or equal to SMALLER_FIELD_AVERAGE
        defaultHeartRateSummaryShouldNotBeFound("fieldAverage.lessThanOrEqual=" + SMALLER_FIELD_AVERAGE);
    }

    @Test
    @Transactional
    void getAllHeartRateSummariesByFieldAverageIsLessThanSomething() throws Exception {
        // Initialize the database
        heartRateSummaryRepository.saveAndFlush(heartRateSummary);

        // Get all the heartRateSummaryList where fieldAverage is less than DEFAULT_FIELD_AVERAGE
        defaultHeartRateSummaryShouldNotBeFound("fieldAverage.lessThan=" + DEFAULT_FIELD_AVERAGE);

        // Get all the heartRateSummaryList where fieldAverage is less than UPDATED_FIELD_AVERAGE
        defaultHeartRateSummaryShouldBeFound("fieldAverage.lessThan=" + UPDATED_FIELD_AVERAGE);
    }

    @Test
    @Transactional
    void getAllHeartRateSummariesByFieldAverageIsGreaterThanSomething() throws Exception {
        // Initialize the database
        heartRateSummaryRepository.saveAndFlush(heartRateSummary);

        // Get all the heartRateSummaryList where fieldAverage is greater than DEFAULT_FIELD_AVERAGE
        defaultHeartRateSummaryShouldNotBeFound("fieldAverage.greaterThan=" + DEFAULT_FIELD_AVERAGE);

        // Get all the heartRateSummaryList where fieldAverage is greater than SMALLER_FIELD_AVERAGE
        defaultHeartRateSummaryShouldBeFound("fieldAverage.greaterThan=" + SMALLER_FIELD_AVERAGE);
    }

    @Test
    @Transactional
    void getAllHeartRateSummariesByFieldMaxIsEqualToSomething() throws Exception {
        // Initialize the database
        heartRateSummaryRepository.saveAndFlush(heartRateSummary);

        // Get all the heartRateSummaryList where fieldMax equals to DEFAULT_FIELD_MAX
        defaultHeartRateSummaryShouldBeFound("fieldMax.equals=" + DEFAULT_FIELD_MAX);

        // Get all the heartRateSummaryList where fieldMax equals to UPDATED_FIELD_MAX
        defaultHeartRateSummaryShouldNotBeFound("fieldMax.equals=" + UPDATED_FIELD_MAX);
    }

    @Test
    @Transactional
    void getAllHeartRateSummariesByFieldMaxIsInShouldWork() throws Exception {
        // Initialize the database
        heartRateSummaryRepository.saveAndFlush(heartRateSummary);

        // Get all the heartRateSummaryList where fieldMax in DEFAULT_FIELD_MAX or UPDATED_FIELD_MAX
        defaultHeartRateSummaryShouldBeFound("fieldMax.in=" + DEFAULT_FIELD_MAX + "," + UPDATED_FIELD_MAX);

        // Get all the heartRateSummaryList where fieldMax equals to UPDATED_FIELD_MAX
        defaultHeartRateSummaryShouldNotBeFound("fieldMax.in=" + UPDATED_FIELD_MAX);
    }

    @Test
    @Transactional
    void getAllHeartRateSummariesByFieldMaxIsNullOrNotNull() throws Exception {
        // Initialize the database
        heartRateSummaryRepository.saveAndFlush(heartRateSummary);

        // Get all the heartRateSummaryList where fieldMax is not null
        defaultHeartRateSummaryShouldBeFound("fieldMax.specified=true");

        // Get all the heartRateSummaryList where fieldMax is null
        defaultHeartRateSummaryShouldNotBeFound("fieldMax.specified=false");
    }

    @Test
    @Transactional
    void getAllHeartRateSummariesByFieldMaxIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        heartRateSummaryRepository.saveAndFlush(heartRateSummary);

        // Get all the heartRateSummaryList where fieldMax is greater than or equal to DEFAULT_FIELD_MAX
        defaultHeartRateSummaryShouldBeFound("fieldMax.greaterThanOrEqual=" + DEFAULT_FIELD_MAX);

        // Get all the heartRateSummaryList where fieldMax is greater than or equal to UPDATED_FIELD_MAX
        defaultHeartRateSummaryShouldNotBeFound("fieldMax.greaterThanOrEqual=" + UPDATED_FIELD_MAX);
    }

    @Test
    @Transactional
    void getAllHeartRateSummariesByFieldMaxIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        heartRateSummaryRepository.saveAndFlush(heartRateSummary);

        // Get all the heartRateSummaryList where fieldMax is less than or equal to DEFAULT_FIELD_MAX
        defaultHeartRateSummaryShouldBeFound("fieldMax.lessThanOrEqual=" + DEFAULT_FIELD_MAX);

        // Get all the heartRateSummaryList where fieldMax is less than or equal to SMALLER_FIELD_MAX
        defaultHeartRateSummaryShouldNotBeFound("fieldMax.lessThanOrEqual=" + SMALLER_FIELD_MAX);
    }

    @Test
    @Transactional
    void getAllHeartRateSummariesByFieldMaxIsLessThanSomething() throws Exception {
        // Initialize the database
        heartRateSummaryRepository.saveAndFlush(heartRateSummary);

        // Get all the heartRateSummaryList where fieldMax is less than DEFAULT_FIELD_MAX
        defaultHeartRateSummaryShouldNotBeFound("fieldMax.lessThan=" + DEFAULT_FIELD_MAX);

        // Get all the heartRateSummaryList where fieldMax is less than UPDATED_FIELD_MAX
        defaultHeartRateSummaryShouldBeFound("fieldMax.lessThan=" + UPDATED_FIELD_MAX);
    }

    @Test
    @Transactional
    void getAllHeartRateSummariesByFieldMaxIsGreaterThanSomething() throws Exception {
        // Initialize the database
        heartRateSummaryRepository.saveAndFlush(heartRateSummary);

        // Get all the heartRateSummaryList where fieldMax is greater than DEFAULT_FIELD_MAX
        defaultHeartRateSummaryShouldNotBeFound("fieldMax.greaterThan=" + DEFAULT_FIELD_MAX);

        // Get all the heartRateSummaryList where fieldMax is greater than SMALLER_FIELD_MAX
        defaultHeartRateSummaryShouldBeFound("fieldMax.greaterThan=" + SMALLER_FIELD_MAX);
    }

    @Test
    @Transactional
    void getAllHeartRateSummariesByFieldMinIsEqualToSomething() throws Exception {
        // Initialize the database
        heartRateSummaryRepository.saveAndFlush(heartRateSummary);

        // Get all the heartRateSummaryList where fieldMin equals to DEFAULT_FIELD_MIN
        defaultHeartRateSummaryShouldBeFound("fieldMin.equals=" + DEFAULT_FIELD_MIN);

        // Get all the heartRateSummaryList where fieldMin equals to UPDATED_FIELD_MIN
        defaultHeartRateSummaryShouldNotBeFound("fieldMin.equals=" + UPDATED_FIELD_MIN);
    }

    @Test
    @Transactional
    void getAllHeartRateSummariesByFieldMinIsInShouldWork() throws Exception {
        // Initialize the database
        heartRateSummaryRepository.saveAndFlush(heartRateSummary);

        // Get all the heartRateSummaryList where fieldMin in DEFAULT_FIELD_MIN or UPDATED_FIELD_MIN
        defaultHeartRateSummaryShouldBeFound("fieldMin.in=" + DEFAULT_FIELD_MIN + "," + UPDATED_FIELD_MIN);

        // Get all the heartRateSummaryList where fieldMin equals to UPDATED_FIELD_MIN
        defaultHeartRateSummaryShouldNotBeFound("fieldMin.in=" + UPDATED_FIELD_MIN);
    }

    @Test
    @Transactional
    void getAllHeartRateSummariesByFieldMinIsNullOrNotNull() throws Exception {
        // Initialize the database
        heartRateSummaryRepository.saveAndFlush(heartRateSummary);

        // Get all the heartRateSummaryList where fieldMin is not null
        defaultHeartRateSummaryShouldBeFound("fieldMin.specified=true");

        // Get all the heartRateSummaryList where fieldMin is null
        defaultHeartRateSummaryShouldNotBeFound("fieldMin.specified=false");
    }

    @Test
    @Transactional
    void getAllHeartRateSummariesByFieldMinIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        heartRateSummaryRepository.saveAndFlush(heartRateSummary);

        // Get all the heartRateSummaryList where fieldMin is greater than or equal to DEFAULT_FIELD_MIN
        defaultHeartRateSummaryShouldBeFound("fieldMin.greaterThanOrEqual=" + DEFAULT_FIELD_MIN);

        // Get all the heartRateSummaryList where fieldMin is greater than or equal to UPDATED_FIELD_MIN
        defaultHeartRateSummaryShouldNotBeFound("fieldMin.greaterThanOrEqual=" + UPDATED_FIELD_MIN);
    }

    @Test
    @Transactional
    void getAllHeartRateSummariesByFieldMinIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        heartRateSummaryRepository.saveAndFlush(heartRateSummary);

        // Get all the heartRateSummaryList where fieldMin is less than or equal to DEFAULT_FIELD_MIN
        defaultHeartRateSummaryShouldBeFound("fieldMin.lessThanOrEqual=" + DEFAULT_FIELD_MIN);

        // Get all the heartRateSummaryList where fieldMin is less than or equal to SMALLER_FIELD_MIN
        defaultHeartRateSummaryShouldNotBeFound("fieldMin.lessThanOrEqual=" + SMALLER_FIELD_MIN);
    }

    @Test
    @Transactional
    void getAllHeartRateSummariesByFieldMinIsLessThanSomething() throws Exception {
        // Initialize the database
        heartRateSummaryRepository.saveAndFlush(heartRateSummary);

        // Get all the heartRateSummaryList where fieldMin is less than DEFAULT_FIELD_MIN
        defaultHeartRateSummaryShouldNotBeFound("fieldMin.lessThan=" + DEFAULT_FIELD_MIN);

        // Get all the heartRateSummaryList where fieldMin is less than UPDATED_FIELD_MIN
        defaultHeartRateSummaryShouldBeFound("fieldMin.lessThan=" + UPDATED_FIELD_MIN);
    }

    @Test
    @Transactional
    void getAllHeartRateSummariesByFieldMinIsGreaterThanSomething() throws Exception {
        // Initialize the database
        heartRateSummaryRepository.saveAndFlush(heartRateSummary);

        // Get all the heartRateSummaryList where fieldMin is greater than DEFAULT_FIELD_MIN
        defaultHeartRateSummaryShouldNotBeFound("fieldMin.greaterThan=" + DEFAULT_FIELD_MIN);

        // Get all the heartRateSummaryList where fieldMin is greater than SMALLER_FIELD_MIN
        defaultHeartRateSummaryShouldBeFound("fieldMin.greaterThan=" + SMALLER_FIELD_MIN);
    }

    @Test
    @Transactional
    void getAllHeartRateSummariesByStartTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        heartRateSummaryRepository.saveAndFlush(heartRateSummary);

        // Get all the heartRateSummaryList where startTime equals to DEFAULT_START_TIME
        defaultHeartRateSummaryShouldBeFound("startTime.equals=" + DEFAULT_START_TIME);

        // Get all the heartRateSummaryList where startTime equals to UPDATED_START_TIME
        defaultHeartRateSummaryShouldNotBeFound("startTime.equals=" + UPDATED_START_TIME);
    }

    @Test
    @Transactional
    void getAllHeartRateSummariesByStartTimeIsInShouldWork() throws Exception {
        // Initialize the database
        heartRateSummaryRepository.saveAndFlush(heartRateSummary);

        // Get all the heartRateSummaryList where startTime in DEFAULT_START_TIME or UPDATED_START_TIME
        defaultHeartRateSummaryShouldBeFound("startTime.in=" + DEFAULT_START_TIME + "," + UPDATED_START_TIME);

        // Get all the heartRateSummaryList where startTime equals to UPDATED_START_TIME
        defaultHeartRateSummaryShouldNotBeFound("startTime.in=" + UPDATED_START_TIME);
    }

    @Test
    @Transactional
    void getAllHeartRateSummariesByStartTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        heartRateSummaryRepository.saveAndFlush(heartRateSummary);

        // Get all the heartRateSummaryList where startTime is not null
        defaultHeartRateSummaryShouldBeFound("startTime.specified=true");

        // Get all the heartRateSummaryList where startTime is null
        defaultHeartRateSummaryShouldNotBeFound("startTime.specified=false");
    }

    @Test
    @Transactional
    void getAllHeartRateSummariesByEndTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        heartRateSummaryRepository.saveAndFlush(heartRateSummary);

        // Get all the heartRateSummaryList where endTime equals to DEFAULT_END_TIME
        defaultHeartRateSummaryShouldBeFound("endTime.equals=" + DEFAULT_END_TIME);

        // Get all the heartRateSummaryList where endTime equals to UPDATED_END_TIME
        defaultHeartRateSummaryShouldNotBeFound("endTime.equals=" + UPDATED_END_TIME);
    }

    @Test
    @Transactional
    void getAllHeartRateSummariesByEndTimeIsInShouldWork() throws Exception {
        // Initialize the database
        heartRateSummaryRepository.saveAndFlush(heartRateSummary);

        // Get all the heartRateSummaryList where endTime in DEFAULT_END_TIME or UPDATED_END_TIME
        defaultHeartRateSummaryShouldBeFound("endTime.in=" + DEFAULT_END_TIME + "," + UPDATED_END_TIME);

        // Get all the heartRateSummaryList where endTime equals to UPDATED_END_TIME
        defaultHeartRateSummaryShouldNotBeFound("endTime.in=" + UPDATED_END_TIME);
    }

    @Test
    @Transactional
    void getAllHeartRateSummariesByEndTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        heartRateSummaryRepository.saveAndFlush(heartRateSummary);

        // Get all the heartRateSummaryList where endTime is not null
        defaultHeartRateSummaryShouldBeFound("endTime.specified=true");

        // Get all the heartRateSummaryList where endTime is null
        defaultHeartRateSummaryShouldNotBeFound("endTime.specified=false");
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultHeartRateSummaryShouldBeFound(String filter) throws Exception {
        restHeartRateSummaryMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(heartRateSummary.getId().toString())))
            .andExpect(jsonPath("$.[*].usuarioId").value(hasItem(DEFAULT_USUARIO_ID)))
            .andExpect(jsonPath("$.[*].empresaId").value(hasItem(DEFAULT_EMPRESA_ID)))
            .andExpect(jsonPath("$.[*].fieldAverage").value(hasItem(DEFAULT_FIELD_AVERAGE.doubleValue())))
            .andExpect(jsonPath("$.[*].fieldMax").value(hasItem(DEFAULT_FIELD_MAX.doubleValue())))
            .andExpect(jsonPath("$.[*].fieldMin").value(hasItem(DEFAULT_FIELD_MIN.doubleValue())))
            .andExpect(jsonPath("$.[*].startTime").value(hasItem(DEFAULT_START_TIME.toString())))
            .andExpect(jsonPath("$.[*].endTime").value(hasItem(DEFAULT_END_TIME.toString())));

        // Check, that the count call also returns 1
        restHeartRateSummaryMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultHeartRateSummaryShouldNotBeFound(String filter) throws Exception {
        restHeartRateSummaryMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restHeartRateSummaryMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingHeartRateSummary() throws Exception {
        // Get the heartRateSummary
        restHeartRateSummaryMockMvc.perform(get(ENTITY_API_URL_ID, UUID.randomUUID().toString())).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingHeartRateSummary() throws Exception {
        // Initialize the database
        heartRateSummaryRepository.saveAndFlush(heartRateSummary);

        int databaseSizeBeforeUpdate = heartRateSummaryRepository.findAll().size();

        // Update the heartRateSummary
        HeartRateSummary updatedHeartRateSummary = heartRateSummaryRepository.findById(heartRateSummary.getId()).get();
        // Disconnect from session so that the updates on updatedHeartRateSummary are not directly saved in db
        em.detach(updatedHeartRateSummary);
        updatedHeartRateSummary
            .usuarioId(UPDATED_USUARIO_ID)
            .empresaId(UPDATED_EMPRESA_ID)
            .fieldAverage(UPDATED_FIELD_AVERAGE)
            .fieldMax(UPDATED_FIELD_MAX)
            .fieldMin(UPDATED_FIELD_MIN)
            .startTime(UPDATED_START_TIME)
            .endTime(UPDATED_END_TIME);

        restHeartRateSummaryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedHeartRateSummary.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedHeartRateSummary))
            )
            .andExpect(status().isOk());

        // Validate the HeartRateSummary in the database
        List<HeartRateSummary> heartRateSummaryList = heartRateSummaryRepository.findAll();
        assertThat(heartRateSummaryList).hasSize(databaseSizeBeforeUpdate);
        HeartRateSummary testHeartRateSummary = heartRateSummaryList.get(heartRateSummaryList.size() - 1);
        assertThat(testHeartRateSummary.getUsuarioId()).isEqualTo(UPDATED_USUARIO_ID);
        assertThat(testHeartRateSummary.getEmpresaId()).isEqualTo(UPDATED_EMPRESA_ID);
        assertThat(testHeartRateSummary.getFieldAverage()).isEqualTo(UPDATED_FIELD_AVERAGE);
        assertThat(testHeartRateSummary.getFieldMax()).isEqualTo(UPDATED_FIELD_MAX);
        assertThat(testHeartRateSummary.getFieldMin()).isEqualTo(UPDATED_FIELD_MIN);
        assertThat(testHeartRateSummary.getStartTime()).isEqualTo(UPDATED_START_TIME);
        assertThat(testHeartRateSummary.getEndTime()).isEqualTo(UPDATED_END_TIME);
    }

    @Test
    @Transactional
    void putNonExistingHeartRateSummary() throws Exception {
        int databaseSizeBeforeUpdate = heartRateSummaryRepository.findAll().size();
        heartRateSummary.setId(UUID.randomUUID());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHeartRateSummaryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, heartRateSummary.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(heartRateSummary))
            )
            .andExpect(status().isBadRequest());

        // Validate the HeartRateSummary in the database
        List<HeartRateSummary> heartRateSummaryList = heartRateSummaryRepository.findAll();
        assertThat(heartRateSummaryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchHeartRateSummary() throws Exception {
        int databaseSizeBeforeUpdate = heartRateSummaryRepository.findAll().size();
        heartRateSummary.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHeartRateSummaryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(heartRateSummary))
            )
            .andExpect(status().isBadRequest());

        // Validate the HeartRateSummary in the database
        List<HeartRateSummary> heartRateSummaryList = heartRateSummaryRepository.findAll();
        assertThat(heartRateSummaryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamHeartRateSummary() throws Exception {
        int databaseSizeBeforeUpdate = heartRateSummaryRepository.findAll().size();
        heartRateSummary.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHeartRateSummaryMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(heartRateSummary))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the HeartRateSummary in the database
        List<HeartRateSummary> heartRateSummaryList = heartRateSummaryRepository.findAll();
        assertThat(heartRateSummaryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateHeartRateSummaryWithPatch() throws Exception {
        // Initialize the database
        heartRateSummaryRepository.saveAndFlush(heartRateSummary);

        int databaseSizeBeforeUpdate = heartRateSummaryRepository.findAll().size();

        // Update the heartRateSummary using partial update
        HeartRateSummary partialUpdatedHeartRateSummary = new HeartRateSummary();
        partialUpdatedHeartRateSummary.setId(heartRateSummary.getId());

        partialUpdatedHeartRateSummary
            .usuarioId(UPDATED_USUARIO_ID)
            .empresaId(UPDATED_EMPRESA_ID)
            .fieldAverage(UPDATED_FIELD_AVERAGE)
            .fieldMin(UPDATED_FIELD_MIN)
            .endTime(UPDATED_END_TIME);

        restHeartRateSummaryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedHeartRateSummary.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedHeartRateSummary))
            )
            .andExpect(status().isOk());

        // Validate the HeartRateSummary in the database
        List<HeartRateSummary> heartRateSummaryList = heartRateSummaryRepository.findAll();
        assertThat(heartRateSummaryList).hasSize(databaseSizeBeforeUpdate);
        HeartRateSummary testHeartRateSummary = heartRateSummaryList.get(heartRateSummaryList.size() - 1);
        assertThat(testHeartRateSummary.getUsuarioId()).isEqualTo(UPDATED_USUARIO_ID);
        assertThat(testHeartRateSummary.getEmpresaId()).isEqualTo(UPDATED_EMPRESA_ID);
        assertThat(testHeartRateSummary.getFieldAverage()).isEqualTo(UPDATED_FIELD_AVERAGE);
        assertThat(testHeartRateSummary.getFieldMax()).isEqualTo(DEFAULT_FIELD_MAX);
        assertThat(testHeartRateSummary.getFieldMin()).isEqualTo(UPDATED_FIELD_MIN);
        assertThat(testHeartRateSummary.getStartTime()).isEqualTo(DEFAULT_START_TIME);
        assertThat(testHeartRateSummary.getEndTime()).isEqualTo(UPDATED_END_TIME);
    }

    @Test
    @Transactional
    void fullUpdateHeartRateSummaryWithPatch() throws Exception {
        // Initialize the database
        heartRateSummaryRepository.saveAndFlush(heartRateSummary);

        int databaseSizeBeforeUpdate = heartRateSummaryRepository.findAll().size();

        // Update the heartRateSummary using partial update
        HeartRateSummary partialUpdatedHeartRateSummary = new HeartRateSummary();
        partialUpdatedHeartRateSummary.setId(heartRateSummary.getId());

        partialUpdatedHeartRateSummary
            .usuarioId(UPDATED_USUARIO_ID)
            .empresaId(UPDATED_EMPRESA_ID)
            .fieldAverage(UPDATED_FIELD_AVERAGE)
            .fieldMax(UPDATED_FIELD_MAX)
            .fieldMin(UPDATED_FIELD_MIN)
            .startTime(UPDATED_START_TIME)
            .endTime(UPDATED_END_TIME);

        restHeartRateSummaryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedHeartRateSummary.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedHeartRateSummary))
            )
            .andExpect(status().isOk());

        // Validate the HeartRateSummary in the database
        List<HeartRateSummary> heartRateSummaryList = heartRateSummaryRepository.findAll();
        assertThat(heartRateSummaryList).hasSize(databaseSizeBeforeUpdate);
        HeartRateSummary testHeartRateSummary = heartRateSummaryList.get(heartRateSummaryList.size() - 1);
        assertThat(testHeartRateSummary.getUsuarioId()).isEqualTo(UPDATED_USUARIO_ID);
        assertThat(testHeartRateSummary.getEmpresaId()).isEqualTo(UPDATED_EMPRESA_ID);
        assertThat(testHeartRateSummary.getFieldAverage()).isEqualTo(UPDATED_FIELD_AVERAGE);
        assertThat(testHeartRateSummary.getFieldMax()).isEqualTo(UPDATED_FIELD_MAX);
        assertThat(testHeartRateSummary.getFieldMin()).isEqualTo(UPDATED_FIELD_MIN);
        assertThat(testHeartRateSummary.getStartTime()).isEqualTo(UPDATED_START_TIME);
        assertThat(testHeartRateSummary.getEndTime()).isEqualTo(UPDATED_END_TIME);
    }

    @Test
    @Transactional
    void patchNonExistingHeartRateSummary() throws Exception {
        int databaseSizeBeforeUpdate = heartRateSummaryRepository.findAll().size();
        heartRateSummary.setId(UUID.randomUUID());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHeartRateSummaryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, heartRateSummary.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(heartRateSummary))
            )
            .andExpect(status().isBadRequest());

        // Validate the HeartRateSummary in the database
        List<HeartRateSummary> heartRateSummaryList = heartRateSummaryRepository.findAll();
        assertThat(heartRateSummaryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchHeartRateSummary() throws Exception {
        int databaseSizeBeforeUpdate = heartRateSummaryRepository.findAll().size();
        heartRateSummary.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHeartRateSummaryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(heartRateSummary))
            )
            .andExpect(status().isBadRequest());

        // Validate the HeartRateSummary in the database
        List<HeartRateSummary> heartRateSummaryList = heartRateSummaryRepository.findAll();
        assertThat(heartRateSummaryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamHeartRateSummary() throws Exception {
        int databaseSizeBeforeUpdate = heartRateSummaryRepository.findAll().size();
        heartRateSummary.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHeartRateSummaryMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(heartRateSummary))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the HeartRateSummary in the database
        List<HeartRateSummary> heartRateSummaryList = heartRateSummaryRepository.findAll();
        assertThat(heartRateSummaryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteHeartRateSummary() throws Exception {
        // Initialize the database
        heartRateSummaryRepository.saveAndFlush(heartRateSummary);

        int databaseSizeBeforeDelete = heartRateSummaryRepository.findAll().size();

        // Delete the heartRateSummary
        restHeartRateSummaryMockMvc
            .perform(delete(ENTITY_API_URL_ID, heartRateSummary.getId().toString()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<HeartRateSummary> heartRateSummaryList = heartRateSummaryRepository.findAll();
        assertThat(heartRateSummaryList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
