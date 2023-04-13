package com.be4tech.b4carecollect.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.be4tech.b4carecollect.IntegrationTest;
import com.be4tech.b4carecollect.domain.PowerSummary;
import com.be4tech.b4carecollect.repository.PowerSummaryRepository;
import com.be4tech.b4carecollect.service.criteria.PowerSummaryCriteria;
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
 * Integration tests for the {@link PowerSummaryResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PowerSummaryResourceIT {

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

    private static final String ENTITY_API_URL = "/api/power-summaries";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private PowerSummaryRepository powerSummaryRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPowerSummaryMockMvc;

    private PowerSummary powerSummary;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PowerSummary createEntity(EntityManager em) {
        PowerSummary powerSummary = new PowerSummary()
            .usuarioId(DEFAULT_USUARIO_ID)
            .empresaId(DEFAULT_EMPRESA_ID)
            .fieldAverage(DEFAULT_FIELD_AVERAGE)
            .fieldMax(DEFAULT_FIELD_MAX)
            .fieldMin(DEFAULT_FIELD_MIN)
            .startTime(DEFAULT_START_TIME)
            .endTime(DEFAULT_END_TIME);
        return powerSummary;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PowerSummary createUpdatedEntity(EntityManager em) {
        PowerSummary powerSummary = new PowerSummary()
            .usuarioId(UPDATED_USUARIO_ID)
            .empresaId(UPDATED_EMPRESA_ID)
            .fieldAverage(UPDATED_FIELD_AVERAGE)
            .fieldMax(UPDATED_FIELD_MAX)
            .fieldMin(UPDATED_FIELD_MIN)
            .startTime(UPDATED_START_TIME)
            .endTime(UPDATED_END_TIME);
        return powerSummary;
    }

    @BeforeEach
    public void initTest() {
        powerSummary = createEntity(em);
    }

    @Test
    @Transactional
    void createPowerSummary() throws Exception {
        int databaseSizeBeforeCreate = powerSummaryRepository.findAll().size();
        // Create the PowerSummary
        restPowerSummaryMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(powerSummary)))
            .andExpect(status().isCreated());

        // Validate the PowerSummary in the database
        List<PowerSummary> powerSummaryList = powerSummaryRepository.findAll();
        assertThat(powerSummaryList).hasSize(databaseSizeBeforeCreate + 1);
        PowerSummary testPowerSummary = powerSummaryList.get(powerSummaryList.size() - 1);
        assertThat(testPowerSummary.getUsuarioId()).isEqualTo(DEFAULT_USUARIO_ID);
        assertThat(testPowerSummary.getEmpresaId()).isEqualTo(DEFAULT_EMPRESA_ID);
        assertThat(testPowerSummary.getFieldAverage()).isEqualTo(DEFAULT_FIELD_AVERAGE);
        assertThat(testPowerSummary.getFieldMax()).isEqualTo(DEFAULT_FIELD_MAX);
        assertThat(testPowerSummary.getFieldMin()).isEqualTo(DEFAULT_FIELD_MIN);
        assertThat(testPowerSummary.getStartTime()).isEqualTo(DEFAULT_START_TIME);
        assertThat(testPowerSummary.getEndTime()).isEqualTo(DEFAULT_END_TIME);
    }

    @Test
    @Transactional
    void createPowerSummaryWithExistingId() throws Exception {
        // Create the PowerSummary with an existing ID
        powerSummaryRepository.saveAndFlush(powerSummary);

        int databaseSizeBeforeCreate = powerSummaryRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPowerSummaryMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(powerSummary)))
            .andExpect(status().isBadRequest());

        // Validate the PowerSummary in the database
        List<PowerSummary> powerSummaryList = powerSummaryRepository.findAll();
        assertThat(powerSummaryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllPowerSummaries() throws Exception {
        // Initialize the database
        powerSummaryRepository.saveAndFlush(powerSummary);

        // Get all the powerSummaryList
        restPowerSummaryMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(powerSummary.getId().toString())))
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
    void getPowerSummary() throws Exception {
        // Initialize the database
        powerSummaryRepository.saveAndFlush(powerSummary);

        // Get the powerSummary
        restPowerSummaryMockMvc
            .perform(get(ENTITY_API_URL_ID, powerSummary.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(powerSummary.getId().toString()))
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
    void getPowerSummariesByIdFiltering() throws Exception {
        // Initialize the database
        powerSummaryRepository.saveAndFlush(powerSummary);

        UUID id = powerSummary.getId();

        defaultPowerSummaryShouldBeFound("id.equals=" + id);
        defaultPowerSummaryShouldNotBeFound("id.notEquals=" + id);
    }

    @Test
    @Transactional
    void getAllPowerSummariesByUsuarioIdIsEqualToSomething() throws Exception {
        // Initialize the database
        powerSummaryRepository.saveAndFlush(powerSummary);

        // Get all the powerSummaryList where usuarioId equals to DEFAULT_USUARIO_ID
        defaultPowerSummaryShouldBeFound("usuarioId.equals=" + DEFAULT_USUARIO_ID);

        // Get all the powerSummaryList where usuarioId equals to UPDATED_USUARIO_ID
        defaultPowerSummaryShouldNotBeFound("usuarioId.equals=" + UPDATED_USUARIO_ID);
    }

    @Test
    @Transactional
    void getAllPowerSummariesByUsuarioIdIsInShouldWork() throws Exception {
        // Initialize the database
        powerSummaryRepository.saveAndFlush(powerSummary);

        // Get all the powerSummaryList where usuarioId in DEFAULT_USUARIO_ID or UPDATED_USUARIO_ID
        defaultPowerSummaryShouldBeFound("usuarioId.in=" + DEFAULT_USUARIO_ID + "," + UPDATED_USUARIO_ID);

        // Get all the powerSummaryList where usuarioId equals to UPDATED_USUARIO_ID
        defaultPowerSummaryShouldNotBeFound("usuarioId.in=" + UPDATED_USUARIO_ID);
    }

    @Test
    @Transactional
    void getAllPowerSummariesByUsuarioIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        powerSummaryRepository.saveAndFlush(powerSummary);

        // Get all the powerSummaryList where usuarioId is not null
        defaultPowerSummaryShouldBeFound("usuarioId.specified=true");

        // Get all the powerSummaryList where usuarioId is null
        defaultPowerSummaryShouldNotBeFound("usuarioId.specified=false");
    }

    @Test
    @Transactional
    void getAllPowerSummariesByUsuarioIdContainsSomething() throws Exception {
        // Initialize the database
        powerSummaryRepository.saveAndFlush(powerSummary);

        // Get all the powerSummaryList where usuarioId contains DEFAULT_USUARIO_ID
        defaultPowerSummaryShouldBeFound("usuarioId.contains=" + DEFAULT_USUARIO_ID);

        // Get all the powerSummaryList where usuarioId contains UPDATED_USUARIO_ID
        defaultPowerSummaryShouldNotBeFound("usuarioId.contains=" + UPDATED_USUARIO_ID);
    }

    @Test
    @Transactional
    void getAllPowerSummariesByUsuarioIdNotContainsSomething() throws Exception {
        // Initialize the database
        powerSummaryRepository.saveAndFlush(powerSummary);

        // Get all the powerSummaryList where usuarioId does not contain DEFAULT_USUARIO_ID
        defaultPowerSummaryShouldNotBeFound("usuarioId.doesNotContain=" + DEFAULT_USUARIO_ID);

        // Get all the powerSummaryList where usuarioId does not contain UPDATED_USUARIO_ID
        defaultPowerSummaryShouldBeFound("usuarioId.doesNotContain=" + UPDATED_USUARIO_ID);
    }

    @Test
    @Transactional
    void getAllPowerSummariesByEmpresaIdIsEqualToSomething() throws Exception {
        // Initialize the database
        powerSummaryRepository.saveAndFlush(powerSummary);

        // Get all the powerSummaryList where empresaId equals to DEFAULT_EMPRESA_ID
        defaultPowerSummaryShouldBeFound("empresaId.equals=" + DEFAULT_EMPRESA_ID);

        // Get all the powerSummaryList where empresaId equals to UPDATED_EMPRESA_ID
        defaultPowerSummaryShouldNotBeFound("empresaId.equals=" + UPDATED_EMPRESA_ID);
    }

    @Test
    @Transactional
    void getAllPowerSummariesByEmpresaIdIsInShouldWork() throws Exception {
        // Initialize the database
        powerSummaryRepository.saveAndFlush(powerSummary);

        // Get all the powerSummaryList where empresaId in DEFAULT_EMPRESA_ID or UPDATED_EMPRESA_ID
        defaultPowerSummaryShouldBeFound("empresaId.in=" + DEFAULT_EMPRESA_ID + "," + UPDATED_EMPRESA_ID);

        // Get all the powerSummaryList where empresaId equals to UPDATED_EMPRESA_ID
        defaultPowerSummaryShouldNotBeFound("empresaId.in=" + UPDATED_EMPRESA_ID);
    }

    @Test
    @Transactional
    void getAllPowerSummariesByEmpresaIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        powerSummaryRepository.saveAndFlush(powerSummary);

        // Get all the powerSummaryList where empresaId is not null
        defaultPowerSummaryShouldBeFound("empresaId.specified=true");

        // Get all the powerSummaryList where empresaId is null
        defaultPowerSummaryShouldNotBeFound("empresaId.specified=false");
    }

    @Test
    @Transactional
    void getAllPowerSummariesByEmpresaIdContainsSomething() throws Exception {
        // Initialize the database
        powerSummaryRepository.saveAndFlush(powerSummary);

        // Get all the powerSummaryList where empresaId contains DEFAULT_EMPRESA_ID
        defaultPowerSummaryShouldBeFound("empresaId.contains=" + DEFAULT_EMPRESA_ID);

        // Get all the powerSummaryList where empresaId contains UPDATED_EMPRESA_ID
        defaultPowerSummaryShouldNotBeFound("empresaId.contains=" + UPDATED_EMPRESA_ID);
    }

    @Test
    @Transactional
    void getAllPowerSummariesByEmpresaIdNotContainsSomething() throws Exception {
        // Initialize the database
        powerSummaryRepository.saveAndFlush(powerSummary);

        // Get all the powerSummaryList where empresaId does not contain DEFAULT_EMPRESA_ID
        defaultPowerSummaryShouldNotBeFound("empresaId.doesNotContain=" + DEFAULT_EMPRESA_ID);

        // Get all the powerSummaryList where empresaId does not contain UPDATED_EMPRESA_ID
        defaultPowerSummaryShouldBeFound("empresaId.doesNotContain=" + UPDATED_EMPRESA_ID);
    }

    @Test
    @Transactional
    void getAllPowerSummariesByFieldAverageIsEqualToSomething() throws Exception {
        // Initialize the database
        powerSummaryRepository.saveAndFlush(powerSummary);

        // Get all the powerSummaryList where fieldAverage equals to DEFAULT_FIELD_AVERAGE
        defaultPowerSummaryShouldBeFound("fieldAverage.equals=" + DEFAULT_FIELD_AVERAGE);

        // Get all the powerSummaryList where fieldAverage equals to UPDATED_FIELD_AVERAGE
        defaultPowerSummaryShouldNotBeFound("fieldAverage.equals=" + UPDATED_FIELD_AVERAGE);
    }

    @Test
    @Transactional
    void getAllPowerSummariesByFieldAverageIsInShouldWork() throws Exception {
        // Initialize the database
        powerSummaryRepository.saveAndFlush(powerSummary);

        // Get all the powerSummaryList where fieldAverage in DEFAULT_FIELD_AVERAGE or UPDATED_FIELD_AVERAGE
        defaultPowerSummaryShouldBeFound("fieldAverage.in=" + DEFAULT_FIELD_AVERAGE + "," + UPDATED_FIELD_AVERAGE);

        // Get all the powerSummaryList where fieldAverage equals to UPDATED_FIELD_AVERAGE
        defaultPowerSummaryShouldNotBeFound("fieldAverage.in=" + UPDATED_FIELD_AVERAGE);
    }

    @Test
    @Transactional
    void getAllPowerSummariesByFieldAverageIsNullOrNotNull() throws Exception {
        // Initialize the database
        powerSummaryRepository.saveAndFlush(powerSummary);

        // Get all the powerSummaryList where fieldAverage is not null
        defaultPowerSummaryShouldBeFound("fieldAverage.specified=true");

        // Get all the powerSummaryList where fieldAverage is null
        defaultPowerSummaryShouldNotBeFound("fieldAverage.specified=false");
    }

    @Test
    @Transactional
    void getAllPowerSummariesByFieldAverageIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        powerSummaryRepository.saveAndFlush(powerSummary);

        // Get all the powerSummaryList where fieldAverage is greater than or equal to DEFAULT_FIELD_AVERAGE
        defaultPowerSummaryShouldBeFound("fieldAverage.greaterThanOrEqual=" + DEFAULT_FIELD_AVERAGE);

        // Get all the powerSummaryList where fieldAverage is greater than or equal to UPDATED_FIELD_AVERAGE
        defaultPowerSummaryShouldNotBeFound("fieldAverage.greaterThanOrEqual=" + UPDATED_FIELD_AVERAGE);
    }

    @Test
    @Transactional
    void getAllPowerSummariesByFieldAverageIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        powerSummaryRepository.saveAndFlush(powerSummary);

        // Get all the powerSummaryList where fieldAverage is less than or equal to DEFAULT_FIELD_AVERAGE
        defaultPowerSummaryShouldBeFound("fieldAverage.lessThanOrEqual=" + DEFAULT_FIELD_AVERAGE);

        // Get all the powerSummaryList where fieldAverage is less than or equal to SMALLER_FIELD_AVERAGE
        defaultPowerSummaryShouldNotBeFound("fieldAverage.lessThanOrEqual=" + SMALLER_FIELD_AVERAGE);
    }

    @Test
    @Transactional
    void getAllPowerSummariesByFieldAverageIsLessThanSomething() throws Exception {
        // Initialize the database
        powerSummaryRepository.saveAndFlush(powerSummary);

        // Get all the powerSummaryList where fieldAverage is less than DEFAULT_FIELD_AVERAGE
        defaultPowerSummaryShouldNotBeFound("fieldAverage.lessThan=" + DEFAULT_FIELD_AVERAGE);

        // Get all the powerSummaryList where fieldAverage is less than UPDATED_FIELD_AVERAGE
        defaultPowerSummaryShouldBeFound("fieldAverage.lessThan=" + UPDATED_FIELD_AVERAGE);
    }

    @Test
    @Transactional
    void getAllPowerSummariesByFieldAverageIsGreaterThanSomething() throws Exception {
        // Initialize the database
        powerSummaryRepository.saveAndFlush(powerSummary);

        // Get all the powerSummaryList where fieldAverage is greater than DEFAULT_FIELD_AVERAGE
        defaultPowerSummaryShouldNotBeFound("fieldAverage.greaterThan=" + DEFAULT_FIELD_AVERAGE);

        // Get all the powerSummaryList where fieldAverage is greater than SMALLER_FIELD_AVERAGE
        defaultPowerSummaryShouldBeFound("fieldAverage.greaterThan=" + SMALLER_FIELD_AVERAGE);
    }

    @Test
    @Transactional
    void getAllPowerSummariesByFieldMaxIsEqualToSomething() throws Exception {
        // Initialize the database
        powerSummaryRepository.saveAndFlush(powerSummary);

        // Get all the powerSummaryList where fieldMax equals to DEFAULT_FIELD_MAX
        defaultPowerSummaryShouldBeFound("fieldMax.equals=" + DEFAULT_FIELD_MAX);

        // Get all the powerSummaryList where fieldMax equals to UPDATED_FIELD_MAX
        defaultPowerSummaryShouldNotBeFound("fieldMax.equals=" + UPDATED_FIELD_MAX);
    }

    @Test
    @Transactional
    void getAllPowerSummariesByFieldMaxIsInShouldWork() throws Exception {
        // Initialize the database
        powerSummaryRepository.saveAndFlush(powerSummary);

        // Get all the powerSummaryList where fieldMax in DEFAULT_FIELD_MAX or UPDATED_FIELD_MAX
        defaultPowerSummaryShouldBeFound("fieldMax.in=" + DEFAULT_FIELD_MAX + "," + UPDATED_FIELD_MAX);

        // Get all the powerSummaryList where fieldMax equals to UPDATED_FIELD_MAX
        defaultPowerSummaryShouldNotBeFound("fieldMax.in=" + UPDATED_FIELD_MAX);
    }

    @Test
    @Transactional
    void getAllPowerSummariesByFieldMaxIsNullOrNotNull() throws Exception {
        // Initialize the database
        powerSummaryRepository.saveAndFlush(powerSummary);

        // Get all the powerSummaryList where fieldMax is not null
        defaultPowerSummaryShouldBeFound("fieldMax.specified=true");

        // Get all the powerSummaryList where fieldMax is null
        defaultPowerSummaryShouldNotBeFound("fieldMax.specified=false");
    }

    @Test
    @Transactional
    void getAllPowerSummariesByFieldMaxIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        powerSummaryRepository.saveAndFlush(powerSummary);

        // Get all the powerSummaryList where fieldMax is greater than or equal to DEFAULT_FIELD_MAX
        defaultPowerSummaryShouldBeFound("fieldMax.greaterThanOrEqual=" + DEFAULT_FIELD_MAX);

        // Get all the powerSummaryList where fieldMax is greater than or equal to UPDATED_FIELD_MAX
        defaultPowerSummaryShouldNotBeFound("fieldMax.greaterThanOrEqual=" + UPDATED_FIELD_MAX);
    }

    @Test
    @Transactional
    void getAllPowerSummariesByFieldMaxIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        powerSummaryRepository.saveAndFlush(powerSummary);

        // Get all the powerSummaryList where fieldMax is less than or equal to DEFAULT_FIELD_MAX
        defaultPowerSummaryShouldBeFound("fieldMax.lessThanOrEqual=" + DEFAULT_FIELD_MAX);

        // Get all the powerSummaryList where fieldMax is less than or equal to SMALLER_FIELD_MAX
        defaultPowerSummaryShouldNotBeFound("fieldMax.lessThanOrEqual=" + SMALLER_FIELD_MAX);
    }

    @Test
    @Transactional
    void getAllPowerSummariesByFieldMaxIsLessThanSomething() throws Exception {
        // Initialize the database
        powerSummaryRepository.saveAndFlush(powerSummary);

        // Get all the powerSummaryList where fieldMax is less than DEFAULT_FIELD_MAX
        defaultPowerSummaryShouldNotBeFound("fieldMax.lessThan=" + DEFAULT_FIELD_MAX);

        // Get all the powerSummaryList where fieldMax is less than UPDATED_FIELD_MAX
        defaultPowerSummaryShouldBeFound("fieldMax.lessThan=" + UPDATED_FIELD_MAX);
    }

    @Test
    @Transactional
    void getAllPowerSummariesByFieldMaxIsGreaterThanSomething() throws Exception {
        // Initialize the database
        powerSummaryRepository.saveAndFlush(powerSummary);

        // Get all the powerSummaryList where fieldMax is greater than DEFAULT_FIELD_MAX
        defaultPowerSummaryShouldNotBeFound("fieldMax.greaterThan=" + DEFAULT_FIELD_MAX);

        // Get all the powerSummaryList where fieldMax is greater than SMALLER_FIELD_MAX
        defaultPowerSummaryShouldBeFound("fieldMax.greaterThan=" + SMALLER_FIELD_MAX);
    }

    @Test
    @Transactional
    void getAllPowerSummariesByFieldMinIsEqualToSomething() throws Exception {
        // Initialize the database
        powerSummaryRepository.saveAndFlush(powerSummary);

        // Get all the powerSummaryList where fieldMin equals to DEFAULT_FIELD_MIN
        defaultPowerSummaryShouldBeFound("fieldMin.equals=" + DEFAULT_FIELD_MIN);

        // Get all the powerSummaryList where fieldMin equals to UPDATED_FIELD_MIN
        defaultPowerSummaryShouldNotBeFound("fieldMin.equals=" + UPDATED_FIELD_MIN);
    }

    @Test
    @Transactional
    void getAllPowerSummariesByFieldMinIsInShouldWork() throws Exception {
        // Initialize the database
        powerSummaryRepository.saveAndFlush(powerSummary);

        // Get all the powerSummaryList where fieldMin in DEFAULT_FIELD_MIN or UPDATED_FIELD_MIN
        defaultPowerSummaryShouldBeFound("fieldMin.in=" + DEFAULT_FIELD_MIN + "," + UPDATED_FIELD_MIN);

        // Get all the powerSummaryList where fieldMin equals to UPDATED_FIELD_MIN
        defaultPowerSummaryShouldNotBeFound("fieldMin.in=" + UPDATED_FIELD_MIN);
    }

    @Test
    @Transactional
    void getAllPowerSummariesByFieldMinIsNullOrNotNull() throws Exception {
        // Initialize the database
        powerSummaryRepository.saveAndFlush(powerSummary);

        // Get all the powerSummaryList where fieldMin is not null
        defaultPowerSummaryShouldBeFound("fieldMin.specified=true");

        // Get all the powerSummaryList where fieldMin is null
        defaultPowerSummaryShouldNotBeFound("fieldMin.specified=false");
    }

    @Test
    @Transactional
    void getAllPowerSummariesByFieldMinIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        powerSummaryRepository.saveAndFlush(powerSummary);

        // Get all the powerSummaryList where fieldMin is greater than or equal to DEFAULT_FIELD_MIN
        defaultPowerSummaryShouldBeFound("fieldMin.greaterThanOrEqual=" + DEFAULT_FIELD_MIN);

        // Get all the powerSummaryList where fieldMin is greater than or equal to UPDATED_FIELD_MIN
        defaultPowerSummaryShouldNotBeFound("fieldMin.greaterThanOrEqual=" + UPDATED_FIELD_MIN);
    }

    @Test
    @Transactional
    void getAllPowerSummariesByFieldMinIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        powerSummaryRepository.saveAndFlush(powerSummary);

        // Get all the powerSummaryList where fieldMin is less than or equal to DEFAULT_FIELD_MIN
        defaultPowerSummaryShouldBeFound("fieldMin.lessThanOrEqual=" + DEFAULT_FIELD_MIN);

        // Get all the powerSummaryList where fieldMin is less than or equal to SMALLER_FIELD_MIN
        defaultPowerSummaryShouldNotBeFound("fieldMin.lessThanOrEqual=" + SMALLER_FIELD_MIN);
    }

    @Test
    @Transactional
    void getAllPowerSummariesByFieldMinIsLessThanSomething() throws Exception {
        // Initialize the database
        powerSummaryRepository.saveAndFlush(powerSummary);

        // Get all the powerSummaryList where fieldMin is less than DEFAULT_FIELD_MIN
        defaultPowerSummaryShouldNotBeFound("fieldMin.lessThan=" + DEFAULT_FIELD_MIN);

        // Get all the powerSummaryList where fieldMin is less than UPDATED_FIELD_MIN
        defaultPowerSummaryShouldBeFound("fieldMin.lessThan=" + UPDATED_FIELD_MIN);
    }

    @Test
    @Transactional
    void getAllPowerSummariesByFieldMinIsGreaterThanSomething() throws Exception {
        // Initialize the database
        powerSummaryRepository.saveAndFlush(powerSummary);

        // Get all the powerSummaryList where fieldMin is greater than DEFAULT_FIELD_MIN
        defaultPowerSummaryShouldNotBeFound("fieldMin.greaterThan=" + DEFAULT_FIELD_MIN);

        // Get all the powerSummaryList where fieldMin is greater than SMALLER_FIELD_MIN
        defaultPowerSummaryShouldBeFound("fieldMin.greaterThan=" + SMALLER_FIELD_MIN);
    }

    @Test
    @Transactional
    void getAllPowerSummariesByStartTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        powerSummaryRepository.saveAndFlush(powerSummary);

        // Get all the powerSummaryList where startTime equals to DEFAULT_START_TIME
        defaultPowerSummaryShouldBeFound("startTime.equals=" + DEFAULT_START_TIME);

        // Get all the powerSummaryList where startTime equals to UPDATED_START_TIME
        defaultPowerSummaryShouldNotBeFound("startTime.equals=" + UPDATED_START_TIME);
    }

    @Test
    @Transactional
    void getAllPowerSummariesByStartTimeIsInShouldWork() throws Exception {
        // Initialize the database
        powerSummaryRepository.saveAndFlush(powerSummary);

        // Get all the powerSummaryList where startTime in DEFAULT_START_TIME or UPDATED_START_TIME
        defaultPowerSummaryShouldBeFound("startTime.in=" + DEFAULT_START_TIME + "," + UPDATED_START_TIME);

        // Get all the powerSummaryList where startTime equals to UPDATED_START_TIME
        defaultPowerSummaryShouldNotBeFound("startTime.in=" + UPDATED_START_TIME);
    }

    @Test
    @Transactional
    void getAllPowerSummariesByStartTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        powerSummaryRepository.saveAndFlush(powerSummary);

        // Get all the powerSummaryList where startTime is not null
        defaultPowerSummaryShouldBeFound("startTime.specified=true");

        // Get all the powerSummaryList where startTime is null
        defaultPowerSummaryShouldNotBeFound("startTime.specified=false");
    }

    @Test
    @Transactional
    void getAllPowerSummariesByEndTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        powerSummaryRepository.saveAndFlush(powerSummary);

        // Get all the powerSummaryList where endTime equals to DEFAULT_END_TIME
        defaultPowerSummaryShouldBeFound("endTime.equals=" + DEFAULT_END_TIME);

        // Get all the powerSummaryList where endTime equals to UPDATED_END_TIME
        defaultPowerSummaryShouldNotBeFound("endTime.equals=" + UPDATED_END_TIME);
    }

    @Test
    @Transactional
    void getAllPowerSummariesByEndTimeIsInShouldWork() throws Exception {
        // Initialize the database
        powerSummaryRepository.saveAndFlush(powerSummary);

        // Get all the powerSummaryList where endTime in DEFAULT_END_TIME or UPDATED_END_TIME
        defaultPowerSummaryShouldBeFound("endTime.in=" + DEFAULT_END_TIME + "," + UPDATED_END_TIME);

        // Get all the powerSummaryList where endTime equals to UPDATED_END_TIME
        defaultPowerSummaryShouldNotBeFound("endTime.in=" + UPDATED_END_TIME);
    }

    @Test
    @Transactional
    void getAllPowerSummariesByEndTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        powerSummaryRepository.saveAndFlush(powerSummary);

        // Get all the powerSummaryList where endTime is not null
        defaultPowerSummaryShouldBeFound("endTime.specified=true");

        // Get all the powerSummaryList where endTime is null
        defaultPowerSummaryShouldNotBeFound("endTime.specified=false");
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultPowerSummaryShouldBeFound(String filter) throws Exception {
        restPowerSummaryMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(powerSummary.getId().toString())))
            .andExpect(jsonPath("$.[*].usuarioId").value(hasItem(DEFAULT_USUARIO_ID)))
            .andExpect(jsonPath("$.[*].empresaId").value(hasItem(DEFAULT_EMPRESA_ID)))
            .andExpect(jsonPath("$.[*].fieldAverage").value(hasItem(DEFAULT_FIELD_AVERAGE.doubleValue())))
            .andExpect(jsonPath("$.[*].fieldMax").value(hasItem(DEFAULT_FIELD_MAX.doubleValue())))
            .andExpect(jsonPath("$.[*].fieldMin").value(hasItem(DEFAULT_FIELD_MIN.doubleValue())))
            .andExpect(jsonPath("$.[*].startTime").value(hasItem(DEFAULT_START_TIME.toString())))
            .andExpect(jsonPath("$.[*].endTime").value(hasItem(DEFAULT_END_TIME.toString())));

        // Check, that the count call also returns 1
        restPowerSummaryMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultPowerSummaryShouldNotBeFound(String filter) throws Exception {
        restPowerSummaryMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restPowerSummaryMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingPowerSummary() throws Exception {
        // Get the powerSummary
        restPowerSummaryMockMvc.perform(get(ENTITY_API_URL_ID, UUID.randomUUID().toString())).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingPowerSummary() throws Exception {
        // Initialize the database
        powerSummaryRepository.saveAndFlush(powerSummary);

        int databaseSizeBeforeUpdate = powerSummaryRepository.findAll().size();

        // Update the powerSummary
        PowerSummary updatedPowerSummary = powerSummaryRepository.findById(powerSummary.getId()).get();
        // Disconnect from session so that the updates on updatedPowerSummary are not directly saved in db
        em.detach(updatedPowerSummary);
        updatedPowerSummary
            .usuarioId(UPDATED_USUARIO_ID)
            .empresaId(UPDATED_EMPRESA_ID)
            .fieldAverage(UPDATED_FIELD_AVERAGE)
            .fieldMax(UPDATED_FIELD_MAX)
            .fieldMin(UPDATED_FIELD_MIN)
            .startTime(UPDATED_START_TIME)
            .endTime(UPDATED_END_TIME);

        restPowerSummaryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedPowerSummary.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedPowerSummary))
            )
            .andExpect(status().isOk());

        // Validate the PowerSummary in the database
        List<PowerSummary> powerSummaryList = powerSummaryRepository.findAll();
        assertThat(powerSummaryList).hasSize(databaseSizeBeforeUpdate);
        PowerSummary testPowerSummary = powerSummaryList.get(powerSummaryList.size() - 1);
        assertThat(testPowerSummary.getUsuarioId()).isEqualTo(UPDATED_USUARIO_ID);
        assertThat(testPowerSummary.getEmpresaId()).isEqualTo(UPDATED_EMPRESA_ID);
        assertThat(testPowerSummary.getFieldAverage()).isEqualTo(UPDATED_FIELD_AVERAGE);
        assertThat(testPowerSummary.getFieldMax()).isEqualTo(UPDATED_FIELD_MAX);
        assertThat(testPowerSummary.getFieldMin()).isEqualTo(UPDATED_FIELD_MIN);
        assertThat(testPowerSummary.getStartTime()).isEqualTo(UPDATED_START_TIME);
        assertThat(testPowerSummary.getEndTime()).isEqualTo(UPDATED_END_TIME);
    }

    @Test
    @Transactional
    void putNonExistingPowerSummary() throws Exception {
        int databaseSizeBeforeUpdate = powerSummaryRepository.findAll().size();
        powerSummary.setId(UUID.randomUUID());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPowerSummaryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, powerSummary.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(powerSummary))
            )
            .andExpect(status().isBadRequest());

        // Validate the PowerSummary in the database
        List<PowerSummary> powerSummaryList = powerSummaryRepository.findAll();
        assertThat(powerSummaryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPowerSummary() throws Exception {
        int databaseSizeBeforeUpdate = powerSummaryRepository.findAll().size();
        powerSummary.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPowerSummaryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(powerSummary))
            )
            .andExpect(status().isBadRequest());

        // Validate the PowerSummary in the database
        List<PowerSummary> powerSummaryList = powerSummaryRepository.findAll();
        assertThat(powerSummaryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPowerSummary() throws Exception {
        int databaseSizeBeforeUpdate = powerSummaryRepository.findAll().size();
        powerSummary.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPowerSummaryMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(powerSummary)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the PowerSummary in the database
        List<PowerSummary> powerSummaryList = powerSummaryRepository.findAll();
        assertThat(powerSummaryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePowerSummaryWithPatch() throws Exception {
        // Initialize the database
        powerSummaryRepository.saveAndFlush(powerSummary);

        int databaseSizeBeforeUpdate = powerSummaryRepository.findAll().size();

        // Update the powerSummary using partial update
        PowerSummary partialUpdatedPowerSummary = new PowerSummary();
        partialUpdatedPowerSummary.setId(powerSummary.getId());

        partialUpdatedPowerSummary
            .empresaId(UPDATED_EMPRESA_ID)
            .fieldAverage(UPDATED_FIELD_AVERAGE)
            .fieldMax(UPDATED_FIELD_MAX)
            .fieldMin(UPDATED_FIELD_MIN)
            .endTime(UPDATED_END_TIME);

        restPowerSummaryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPowerSummary.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPowerSummary))
            )
            .andExpect(status().isOk());

        // Validate the PowerSummary in the database
        List<PowerSummary> powerSummaryList = powerSummaryRepository.findAll();
        assertThat(powerSummaryList).hasSize(databaseSizeBeforeUpdate);
        PowerSummary testPowerSummary = powerSummaryList.get(powerSummaryList.size() - 1);
        assertThat(testPowerSummary.getUsuarioId()).isEqualTo(DEFAULT_USUARIO_ID);
        assertThat(testPowerSummary.getEmpresaId()).isEqualTo(UPDATED_EMPRESA_ID);
        assertThat(testPowerSummary.getFieldAverage()).isEqualTo(UPDATED_FIELD_AVERAGE);
        assertThat(testPowerSummary.getFieldMax()).isEqualTo(UPDATED_FIELD_MAX);
        assertThat(testPowerSummary.getFieldMin()).isEqualTo(UPDATED_FIELD_MIN);
        assertThat(testPowerSummary.getStartTime()).isEqualTo(DEFAULT_START_TIME);
        assertThat(testPowerSummary.getEndTime()).isEqualTo(UPDATED_END_TIME);
    }

    @Test
    @Transactional
    void fullUpdatePowerSummaryWithPatch() throws Exception {
        // Initialize the database
        powerSummaryRepository.saveAndFlush(powerSummary);

        int databaseSizeBeforeUpdate = powerSummaryRepository.findAll().size();

        // Update the powerSummary using partial update
        PowerSummary partialUpdatedPowerSummary = new PowerSummary();
        partialUpdatedPowerSummary.setId(powerSummary.getId());

        partialUpdatedPowerSummary
            .usuarioId(UPDATED_USUARIO_ID)
            .empresaId(UPDATED_EMPRESA_ID)
            .fieldAverage(UPDATED_FIELD_AVERAGE)
            .fieldMax(UPDATED_FIELD_MAX)
            .fieldMin(UPDATED_FIELD_MIN)
            .startTime(UPDATED_START_TIME)
            .endTime(UPDATED_END_TIME);

        restPowerSummaryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPowerSummary.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPowerSummary))
            )
            .andExpect(status().isOk());

        // Validate the PowerSummary in the database
        List<PowerSummary> powerSummaryList = powerSummaryRepository.findAll();
        assertThat(powerSummaryList).hasSize(databaseSizeBeforeUpdate);
        PowerSummary testPowerSummary = powerSummaryList.get(powerSummaryList.size() - 1);
        assertThat(testPowerSummary.getUsuarioId()).isEqualTo(UPDATED_USUARIO_ID);
        assertThat(testPowerSummary.getEmpresaId()).isEqualTo(UPDATED_EMPRESA_ID);
        assertThat(testPowerSummary.getFieldAverage()).isEqualTo(UPDATED_FIELD_AVERAGE);
        assertThat(testPowerSummary.getFieldMax()).isEqualTo(UPDATED_FIELD_MAX);
        assertThat(testPowerSummary.getFieldMin()).isEqualTo(UPDATED_FIELD_MIN);
        assertThat(testPowerSummary.getStartTime()).isEqualTo(UPDATED_START_TIME);
        assertThat(testPowerSummary.getEndTime()).isEqualTo(UPDATED_END_TIME);
    }

    @Test
    @Transactional
    void patchNonExistingPowerSummary() throws Exception {
        int databaseSizeBeforeUpdate = powerSummaryRepository.findAll().size();
        powerSummary.setId(UUID.randomUUID());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPowerSummaryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, powerSummary.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(powerSummary))
            )
            .andExpect(status().isBadRequest());

        // Validate the PowerSummary in the database
        List<PowerSummary> powerSummaryList = powerSummaryRepository.findAll();
        assertThat(powerSummaryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPowerSummary() throws Exception {
        int databaseSizeBeforeUpdate = powerSummaryRepository.findAll().size();
        powerSummary.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPowerSummaryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(powerSummary))
            )
            .andExpect(status().isBadRequest());

        // Validate the PowerSummary in the database
        List<PowerSummary> powerSummaryList = powerSummaryRepository.findAll();
        assertThat(powerSummaryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPowerSummary() throws Exception {
        int databaseSizeBeforeUpdate = powerSummaryRepository.findAll().size();
        powerSummary.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPowerSummaryMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(powerSummary))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PowerSummary in the database
        List<PowerSummary> powerSummaryList = powerSummaryRepository.findAll();
        assertThat(powerSummaryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePowerSummary() throws Exception {
        // Initialize the database
        powerSummaryRepository.saveAndFlush(powerSummary);

        int databaseSizeBeforeDelete = powerSummaryRepository.findAll().size();

        // Delete the powerSummary
        restPowerSummaryMockMvc
            .perform(delete(ENTITY_API_URL_ID, powerSummary.getId().toString()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PowerSummary> powerSummaryList = powerSummaryRepository.findAll();
        assertThat(powerSummaryList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
