package com.be4tech.b4carecollect.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.be4tech.b4carecollect.IntegrationTest;
import com.be4tech.b4carecollect.domain.AlarmRiskIndexSummary;
import com.be4tech.b4carecollect.repository.AlarmRiskIndexSummaryRepository;
import com.be4tech.b4carecollect.service.criteria.AlarmRiskIndexSummaryCriteria;
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
 * Integration tests for the {@link AlarmRiskIndexSummaryResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AlarmRiskIndexSummaryResourceIT {

    private static final String DEFAULT_USUARIO_ID = "AAAAAAAAAA";
    private static final String UPDATED_USUARIO_ID = "BBBBBBBBBB";

    private static final String DEFAULT_EMPRESA_ID = "AAAAAAAAAA";
    private static final String UPDATED_EMPRESA_ID = "BBBBBBBBBB";

    private static final Float DEFAULT_FIELD_ALARM_RISK_AVERAGE = 1F;
    private static final Float UPDATED_FIELD_ALARM_RISK_AVERAGE = 2F;
    private static final Float SMALLER_FIELD_ALARM_RISK_AVERAGE = 1F - 1F;

    private static final Float DEFAULT_FIELD_ALARM_RISK_MAX = 1F;
    private static final Float UPDATED_FIELD_ALARM_RISK_MAX = 2F;
    private static final Float SMALLER_FIELD_ALARM_RISK_MAX = 1F - 1F;

    private static final Float DEFAULT_FIELD_ALARM_RISK_MIN = 1F;
    private static final Float UPDATED_FIELD_ALARM_RISK_MIN = 2F;
    private static final Float SMALLER_FIELD_ALARM_RISK_MIN = 1F - 1F;

    private static final Instant DEFAULT_START_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_START_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_END_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_END_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/alarm-risk-index-summaries";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private AlarmRiskIndexSummaryRepository alarmRiskIndexSummaryRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAlarmRiskIndexSummaryMockMvc;

    private AlarmRiskIndexSummary alarmRiskIndexSummary;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AlarmRiskIndexSummary createEntity(EntityManager em) {
        AlarmRiskIndexSummary alarmRiskIndexSummary = new AlarmRiskIndexSummary()
            .usuarioId(DEFAULT_USUARIO_ID)
            .empresaId(DEFAULT_EMPRESA_ID)
            .fieldAlarmRiskAverage(DEFAULT_FIELD_ALARM_RISK_AVERAGE)
            .fieldAlarmRiskMax(DEFAULT_FIELD_ALARM_RISK_MAX)
            .fieldAlarmRiskMin(DEFAULT_FIELD_ALARM_RISK_MIN)
            .startTime(DEFAULT_START_TIME)
            .endTime(DEFAULT_END_TIME);
        return alarmRiskIndexSummary;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AlarmRiskIndexSummary createUpdatedEntity(EntityManager em) {
        AlarmRiskIndexSummary alarmRiskIndexSummary = new AlarmRiskIndexSummary()
            .usuarioId(UPDATED_USUARIO_ID)
            .empresaId(UPDATED_EMPRESA_ID)
            .fieldAlarmRiskAverage(UPDATED_FIELD_ALARM_RISK_AVERAGE)
            .fieldAlarmRiskMax(UPDATED_FIELD_ALARM_RISK_MAX)
            .fieldAlarmRiskMin(UPDATED_FIELD_ALARM_RISK_MIN)
            .startTime(UPDATED_START_TIME)
            .endTime(UPDATED_END_TIME);
        return alarmRiskIndexSummary;
    }

    @BeforeEach
    public void initTest() {
        alarmRiskIndexSummary = createEntity(em);
    }

    @Test
    @Transactional
    void createAlarmRiskIndexSummary() throws Exception {
        int databaseSizeBeforeCreate = alarmRiskIndexSummaryRepository.findAll().size();
        // Create the AlarmRiskIndexSummary
        restAlarmRiskIndexSummaryMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(alarmRiskIndexSummary))
            )
            .andExpect(status().isCreated());

        // Validate the AlarmRiskIndexSummary in the database
        List<AlarmRiskIndexSummary> alarmRiskIndexSummaryList = alarmRiskIndexSummaryRepository.findAll();
        assertThat(alarmRiskIndexSummaryList).hasSize(databaseSizeBeforeCreate + 1);
        AlarmRiskIndexSummary testAlarmRiskIndexSummary = alarmRiskIndexSummaryList.get(alarmRiskIndexSummaryList.size() - 1);
        assertThat(testAlarmRiskIndexSummary.getUsuarioId()).isEqualTo(DEFAULT_USUARIO_ID);
        assertThat(testAlarmRiskIndexSummary.getEmpresaId()).isEqualTo(DEFAULT_EMPRESA_ID);
        assertThat(testAlarmRiskIndexSummary.getFieldAlarmRiskAverage()).isEqualTo(DEFAULT_FIELD_ALARM_RISK_AVERAGE);
        assertThat(testAlarmRiskIndexSummary.getFieldAlarmRiskMax()).isEqualTo(DEFAULT_FIELD_ALARM_RISK_MAX);
        assertThat(testAlarmRiskIndexSummary.getFieldAlarmRiskMin()).isEqualTo(DEFAULT_FIELD_ALARM_RISK_MIN);
        assertThat(testAlarmRiskIndexSummary.getStartTime()).isEqualTo(DEFAULT_START_TIME);
        assertThat(testAlarmRiskIndexSummary.getEndTime()).isEqualTo(DEFAULT_END_TIME);
    }

    @Test
    @Transactional
    void createAlarmRiskIndexSummaryWithExistingId() throws Exception {
        // Create the AlarmRiskIndexSummary with an existing ID
        alarmRiskIndexSummaryRepository.saveAndFlush(alarmRiskIndexSummary);

        int databaseSizeBeforeCreate = alarmRiskIndexSummaryRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAlarmRiskIndexSummaryMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(alarmRiskIndexSummary))
            )
            .andExpect(status().isBadRequest());

        // Validate the AlarmRiskIndexSummary in the database
        List<AlarmRiskIndexSummary> alarmRiskIndexSummaryList = alarmRiskIndexSummaryRepository.findAll();
        assertThat(alarmRiskIndexSummaryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllAlarmRiskIndexSummaries() throws Exception {
        // Initialize the database
        alarmRiskIndexSummaryRepository.saveAndFlush(alarmRiskIndexSummary);

        // Get all the alarmRiskIndexSummaryList
        restAlarmRiskIndexSummaryMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(alarmRiskIndexSummary.getId().toString())))
            .andExpect(jsonPath("$.[*].usuarioId").value(hasItem(DEFAULT_USUARIO_ID)))
            .andExpect(jsonPath("$.[*].empresaId").value(hasItem(DEFAULT_EMPRESA_ID)))
            .andExpect(jsonPath("$.[*].fieldAlarmRiskAverage").value(hasItem(DEFAULT_FIELD_ALARM_RISK_AVERAGE.doubleValue())))
            .andExpect(jsonPath("$.[*].fieldAlarmRiskMax").value(hasItem(DEFAULT_FIELD_ALARM_RISK_MAX.doubleValue())))
            .andExpect(jsonPath("$.[*].fieldAlarmRiskMin").value(hasItem(DEFAULT_FIELD_ALARM_RISK_MIN.doubleValue())))
            .andExpect(jsonPath("$.[*].startTime").value(hasItem(DEFAULT_START_TIME.toString())))
            .andExpect(jsonPath("$.[*].endTime").value(hasItem(DEFAULT_END_TIME.toString())));
    }

    @Test
    @Transactional
    void getAlarmRiskIndexSummary() throws Exception {
        // Initialize the database
        alarmRiskIndexSummaryRepository.saveAndFlush(alarmRiskIndexSummary);

        // Get the alarmRiskIndexSummary
        restAlarmRiskIndexSummaryMockMvc
            .perform(get(ENTITY_API_URL_ID, alarmRiskIndexSummary.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(alarmRiskIndexSummary.getId().toString()))
            .andExpect(jsonPath("$.usuarioId").value(DEFAULT_USUARIO_ID))
            .andExpect(jsonPath("$.empresaId").value(DEFAULT_EMPRESA_ID))
            .andExpect(jsonPath("$.fieldAlarmRiskAverage").value(DEFAULT_FIELD_ALARM_RISK_AVERAGE.doubleValue()))
            .andExpect(jsonPath("$.fieldAlarmRiskMax").value(DEFAULT_FIELD_ALARM_RISK_MAX.doubleValue()))
            .andExpect(jsonPath("$.fieldAlarmRiskMin").value(DEFAULT_FIELD_ALARM_RISK_MIN.doubleValue()))
            .andExpect(jsonPath("$.startTime").value(DEFAULT_START_TIME.toString()))
            .andExpect(jsonPath("$.endTime").value(DEFAULT_END_TIME.toString()));
    }

    @Test
    @Transactional
    void getAlarmRiskIndexSummariesByIdFiltering() throws Exception {
        // Initialize the database
        alarmRiskIndexSummaryRepository.saveAndFlush(alarmRiskIndexSummary);

        UUID id = alarmRiskIndexSummary.getId();

        defaultAlarmRiskIndexSummaryShouldBeFound("id.equals=" + id);
        defaultAlarmRiskIndexSummaryShouldNotBeFound("id.notEquals=" + id);
    }

    @Test
    @Transactional
    void getAllAlarmRiskIndexSummariesByUsuarioIdIsEqualToSomething() throws Exception {
        // Initialize the database
        alarmRiskIndexSummaryRepository.saveAndFlush(alarmRiskIndexSummary);

        // Get all the alarmRiskIndexSummaryList where usuarioId equals to DEFAULT_USUARIO_ID
        defaultAlarmRiskIndexSummaryShouldBeFound("usuarioId.equals=" + DEFAULT_USUARIO_ID);

        // Get all the alarmRiskIndexSummaryList where usuarioId equals to UPDATED_USUARIO_ID
        defaultAlarmRiskIndexSummaryShouldNotBeFound("usuarioId.equals=" + UPDATED_USUARIO_ID);
    }

    @Test
    @Transactional
    void getAllAlarmRiskIndexSummariesByUsuarioIdIsInShouldWork() throws Exception {
        // Initialize the database
        alarmRiskIndexSummaryRepository.saveAndFlush(alarmRiskIndexSummary);

        // Get all the alarmRiskIndexSummaryList where usuarioId in DEFAULT_USUARIO_ID or UPDATED_USUARIO_ID
        defaultAlarmRiskIndexSummaryShouldBeFound("usuarioId.in=" + DEFAULT_USUARIO_ID + "," + UPDATED_USUARIO_ID);

        // Get all the alarmRiskIndexSummaryList where usuarioId equals to UPDATED_USUARIO_ID
        defaultAlarmRiskIndexSummaryShouldNotBeFound("usuarioId.in=" + UPDATED_USUARIO_ID);
    }

    @Test
    @Transactional
    void getAllAlarmRiskIndexSummariesByUsuarioIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        alarmRiskIndexSummaryRepository.saveAndFlush(alarmRiskIndexSummary);

        // Get all the alarmRiskIndexSummaryList where usuarioId is not null
        defaultAlarmRiskIndexSummaryShouldBeFound("usuarioId.specified=true");

        // Get all the alarmRiskIndexSummaryList where usuarioId is null
        defaultAlarmRiskIndexSummaryShouldNotBeFound("usuarioId.specified=false");
    }

    @Test
    @Transactional
    void getAllAlarmRiskIndexSummariesByUsuarioIdContainsSomething() throws Exception {
        // Initialize the database
        alarmRiskIndexSummaryRepository.saveAndFlush(alarmRiskIndexSummary);

        // Get all the alarmRiskIndexSummaryList where usuarioId contains DEFAULT_USUARIO_ID
        defaultAlarmRiskIndexSummaryShouldBeFound("usuarioId.contains=" + DEFAULT_USUARIO_ID);

        // Get all the alarmRiskIndexSummaryList where usuarioId contains UPDATED_USUARIO_ID
        defaultAlarmRiskIndexSummaryShouldNotBeFound("usuarioId.contains=" + UPDATED_USUARIO_ID);
    }

    @Test
    @Transactional
    void getAllAlarmRiskIndexSummariesByUsuarioIdNotContainsSomething() throws Exception {
        // Initialize the database
        alarmRiskIndexSummaryRepository.saveAndFlush(alarmRiskIndexSummary);

        // Get all the alarmRiskIndexSummaryList where usuarioId does not contain DEFAULT_USUARIO_ID
        defaultAlarmRiskIndexSummaryShouldNotBeFound("usuarioId.doesNotContain=" + DEFAULT_USUARIO_ID);

        // Get all the alarmRiskIndexSummaryList where usuarioId does not contain UPDATED_USUARIO_ID
        defaultAlarmRiskIndexSummaryShouldBeFound("usuarioId.doesNotContain=" + UPDATED_USUARIO_ID);
    }

    @Test
    @Transactional
    void getAllAlarmRiskIndexSummariesByEmpresaIdIsEqualToSomething() throws Exception {
        // Initialize the database
        alarmRiskIndexSummaryRepository.saveAndFlush(alarmRiskIndexSummary);

        // Get all the alarmRiskIndexSummaryList where empresaId equals to DEFAULT_EMPRESA_ID
        defaultAlarmRiskIndexSummaryShouldBeFound("empresaId.equals=" + DEFAULT_EMPRESA_ID);

        // Get all the alarmRiskIndexSummaryList where empresaId equals to UPDATED_EMPRESA_ID
        defaultAlarmRiskIndexSummaryShouldNotBeFound("empresaId.equals=" + UPDATED_EMPRESA_ID);
    }

    @Test
    @Transactional
    void getAllAlarmRiskIndexSummariesByEmpresaIdIsInShouldWork() throws Exception {
        // Initialize the database
        alarmRiskIndexSummaryRepository.saveAndFlush(alarmRiskIndexSummary);

        // Get all the alarmRiskIndexSummaryList where empresaId in DEFAULT_EMPRESA_ID or UPDATED_EMPRESA_ID
        defaultAlarmRiskIndexSummaryShouldBeFound("empresaId.in=" + DEFAULT_EMPRESA_ID + "," + UPDATED_EMPRESA_ID);

        // Get all the alarmRiskIndexSummaryList where empresaId equals to UPDATED_EMPRESA_ID
        defaultAlarmRiskIndexSummaryShouldNotBeFound("empresaId.in=" + UPDATED_EMPRESA_ID);
    }

    @Test
    @Transactional
    void getAllAlarmRiskIndexSummariesByEmpresaIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        alarmRiskIndexSummaryRepository.saveAndFlush(alarmRiskIndexSummary);

        // Get all the alarmRiskIndexSummaryList where empresaId is not null
        defaultAlarmRiskIndexSummaryShouldBeFound("empresaId.specified=true");

        // Get all the alarmRiskIndexSummaryList where empresaId is null
        defaultAlarmRiskIndexSummaryShouldNotBeFound("empresaId.specified=false");
    }

    @Test
    @Transactional
    void getAllAlarmRiskIndexSummariesByEmpresaIdContainsSomething() throws Exception {
        // Initialize the database
        alarmRiskIndexSummaryRepository.saveAndFlush(alarmRiskIndexSummary);

        // Get all the alarmRiskIndexSummaryList where empresaId contains DEFAULT_EMPRESA_ID
        defaultAlarmRiskIndexSummaryShouldBeFound("empresaId.contains=" + DEFAULT_EMPRESA_ID);

        // Get all the alarmRiskIndexSummaryList where empresaId contains UPDATED_EMPRESA_ID
        defaultAlarmRiskIndexSummaryShouldNotBeFound("empresaId.contains=" + UPDATED_EMPRESA_ID);
    }

    @Test
    @Transactional
    void getAllAlarmRiskIndexSummariesByEmpresaIdNotContainsSomething() throws Exception {
        // Initialize the database
        alarmRiskIndexSummaryRepository.saveAndFlush(alarmRiskIndexSummary);

        // Get all the alarmRiskIndexSummaryList where empresaId does not contain DEFAULT_EMPRESA_ID
        defaultAlarmRiskIndexSummaryShouldNotBeFound("empresaId.doesNotContain=" + DEFAULT_EMPRESA_ID);

        // Get all the alarmRiskIndexSummaryList where empresaId does not contain UPDATED_EMPRESA_ID
        defaultAlarmRiskIndexSummaryShouldBeFound("empresaId.doesNotContain=" + UPDATED_EMPRESA_ID);
    }

    @Test
    @Transactional
    void getAllAlarmRiskIndexSummariesByFieldAlarmRiskAverageIsEqualToSomething() throws Exception {
        // Initialize the database
        alarmRiskIndexSummaryRepository.saveAndFlush(alarmRiskIndexSummary);

        // Get all the alarmRiskIndexSummaryList where fieldAlarmRiskAverage equals to DEFAULT_FIELD_ALARM_RISK_AVERAGE
        defaultAlarmRiskIndexSummaryShouldBeFound("fieldAlarmRiskAverage.equals=" + DEFAULT_FIELD_ALARM_RISK_AVERAGE);

        // Get all the alarmRiskIndexSummaryList where fieldAlarmRiskAverage equals to UPDATED_FIELD_ALARM_RISK_AVERAGE
        defaultAlarmRiskIndexSummaryShouldNotBeFound("fieldAlarmRiskAverage.equals=" + UPDATED_FIELD_ALARM_RISK_AVERAGE);
    }

    @Test
    @Transactional
    void getAllAlarmRiskIndexSummariesByFieldAlarmRiskAverageIsInShouldWork() throws Exception {
        // Initialize the database
        alarmRiskIndexSummaryRepository.saveAndFlush(alarmRiskIndexSummary);

        // Get all the alarmRiskIndexSummaryList where fieldAlarmRiskAverage in DEFAULT_FIELD_ALARM_RISK_AVERAGE or UPDATED_FIELD_ALARM_RISK_AVERAGE
        defaultAlarmRiskIndexSummaryShouldBeFound(
            "fieldAlarmRiskAverage.in=" + DEFAULT_FIELD_ALARM_RISK_AVERAGE + "," + UPDATED_FIELD_ALARM_RISK_AVERAGE
        );

        // Get all the alarmRiskIndexSummaryList where fieldAlarmRiskAverage equals to UPDATED_FIELD_ALARM_RISK_AVERAGE
        defaultAlarmRiskIndexSummaryShouldNotBeFound("fieldAlarmRiskAverage.in=" + UPDATED_FIELD_ALARM_RISK_AVERAGE);
    }

    @Test
    @Transactional
    void getAllAlarmRiskIndexSummariesByFieldAlarmRiskAverageIsNullOrNotNull() throws Exception {
        // Initialize the database
        alarmRiskIndexSummaryRepository.saveAndFlush(alarmRiskIndexSummary);

        // Get all the alarmRiskIndexSummaryList where fieldAlarmRiskAverage is not null
        defaultAlarmRiskIndexSummaryShouldBeFound("fieldAlarmRiskAverage.specified=true");

        // Get all the alarmRiskIndexSummaryList where fieldAlarmRiskAverage is null
        defaultAlarmRiskIndexSummaryShouldNotBeFound("fieldAlarmRiskAverage.specified=false");
    }

    @Test
    @Transactional
    void getAllAlarmRiskIndexSummariesByFieldAlarmRiskAverageIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        alarmRiskIndexSummaryRepository.saveAndFlush(alarmRiskIndexSummary);

        // Get all the alarmRiskIndexSummaryList where fieldAlarmRiskAverage is greater than or equal to DEFAULT_FIELD_ALARM_RISK_AVERAGE
        defaultAlarmRiskIndexSummaryShouldBeFound("fieldAlarmRiskAverage.greaterThanOrEqual=" + DEFAULT_FIELD_ALARM_RISK_AVERAGE);

        // Get all the alarmRiskIndexSummaryList where fieldAlarmRiskAverage is greater than or equal to UPDATED_FIELD_ALARM_RISK_AVERAGE
        defaultAlarmRiskIndexSummaryShouldNotBeFound("fieldAlarmRiskAverage.greaterThanOrEqual=" + UPDATED_FIELD_ALARM_RISK_AVERAGE);
    }

    @Test
    @Transactional
    void getAllAlarmRiskIndexSummariesByFieldAlarmRiskAverageIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        alarmRiskIndexSummaryRepository.saveAndFlush(alarmRiskIndexSummary);

        // Get all the alarmRiskIndexSummaryList where fieldAlarmRiskAverage is less than or equal to DEFAULT_FIELD_ALARM_RISK_AVERAGE
        defaultAlarmRiskIndexSummaryShouldBeFound("fieldAlarmRiskAverage.lessThanOrEqual=" + DEFAULT_FIELD_ALARM_RISK_AVERAGE);

        // Get all the alarmRiskIndexSummaryList where fieldAlarmRiskAverage is less than or equal to SMALLER_FIELD_ALARM_RISK_AVERAGE
        defaultAlarmRiskIndexSummaryShouldNotBeFound("fieldAlarmRiskAverage.lessThanOrEqual=" + SMALLER_FIELD_ALARM_RISK_AVERAGE);
    }

    @Test
    @Transactional
    void getAllAlarmRiskIndexSummariesByFieldAlarmRiskAverageIsLessThanSomething() throws Exception {
        // Initialize the database
        alarmRiskIndexSummaryRepository.saveAndFlush(alarmRiskIndexSummary);

        // Get all the alarmRiskIndexSummaryList where fieldAlarmRiskAverage is less than DEFAULT_FIELD_ALARM_RISK_AVERAGE
        defaultAlarmRiskIndexSummaryShouldNotBeFound("fieldAlarmRiskAverage.lessThan=" + DEFAULT_FIELD_ALARM_RISK_AVERAGE);

        // Get all the alarmRiskIndexSummaryList where fieldAlarmRiskAverage is less than UPDATED_FIELD_ALARM_RISK_AVERAGE
        defaultAlarmRiskIndexSummaryShouldBeFound("fieldAlarmRiskAverage.lessThan=" + UPDATED_FIELD_ALARM_RISK_AVERAGE);
    }

    @Test
    @Transactional
    void getAllAlarmRiskIndexSummariesByFieldAlarmRiskAverageIsGreaterThanSomething() throws Exception {
        // Initialize the database
        alarmRiskIndexSummaryRepository.saveAndFlush(alarmRiskIndexSummary);

        // Get all the alarmRiskIndexSummaryList where fieldAlarmRiskAverage is greater than DEFAULT_FIELD_ALARM_RISK_AVERAGE
        defaultAlarmRiskIndexSummaryShouldNotBeFound("fieldAlarmRiskAverage.greaterThan=" + DEFAULT_FIELD_ALARM_RISK_AVERAGE);

        // Get all the alarmRiskIndexSummaryList where fieldAlarmRiskAverage is greater than SMALLER_FIELD_ALARM_RISK_AVERAGE
        defaultAlarmRiskIndexSummaryShouldBeFound("fieldAlarmRiskAverage.greaterThan=" + SMALLER_FIELD_ALARM_RISK_AVERAGE);
    }

    @Test
    @Transactional
    void getAllAlarmRiskIndexSummariesByFieldAlarmRiskMaxIsEqualToSomething() throws Exception {
        // Initialize the database
        alarmRiskIndexSummaryRepository.saveAndFlush(alarmRiskIndexSummary);

        // Get all the alarmRiskIndexSummaryList where fieldAlarmRiskMax equals to DEFAULT_FIELD_ALARM_RISK_MAX
        defaultAlarmRiskIndexSummaryShouldBeFound("fieldAlarmRiskMax.equals=" + DEFAULT_FIELD_ALARM_RISK_MAX);

        // Get all the alarmRiskIndexSummaryList where fieldAlarmRiskMax equals to UPDATED_FIELD_ALARM_RISK_MAX
        defaultAlarmRiskIndexSummaryShouldNotBeFound("fieldAlarmRiskMax.equals=" + UPDATED_FIELD_ALARM_RISK_MAX);
    }

    @Test
    @Transactional
    void getAllAlarmRiskIndexSummariesByFieldAlarmRiskMaxIsInShouldWork() throws Exception {
        // Initialize the database
        alarmRiskIndexSummaryRepository.saveAndFlush(alarmRiskIndexSummary);

        // Get all the alarmRiskIndexSummaryList where fieldAlarmRiskMax in DEFAULT_FIELD_ALARM_RISK_MAX or UPDATED_FIELD_ALARM_RISK_MAX
        defaultAlarmRiskIndexSummaryShouldBeFound(
            "fieldAlarmRiskMax.in=" + DEFAULT_FIELD_ALARM_RISK_MAX + "," + UPDATED_FIELD_ALARM_RISK_MAX
        );

        // Get all the alarmRiskIndexSummaryList where fieldAlarmRiskMax equals to UPDATED_FIELD_ALARM_RISK_MAX
        defaultAlarmRiskIndexSummaryShouldNotBeFound("fieldAlarmRiskMax.in=" + UPDATED_FIELD_ALARM_RISK_MAX);
    }

    @Test
    @Transactional
    void getAllAlarmRiskIndexSummariesByFieldAlarmRiskMaxIsNullOrNotNull() throws Exception {
        // Initialize the database
        alarmRiskIndexSummaryRepository.saveAndFlush(alarmRiskIndexSummary);

        // Get all the alarmRiskIndexSummaryList where fieldAlarmRiskMax is not null
        defaultAlarmRiskIndexSummaryShouldBeFound("fieldAlarmRiskMax.specified=true");

        // Get all the alarmRiskIndexSummaryList where fieldAlarmRiskMax is null
        defaultAlarmRiskIndexSummaryShouldNotBeFound("fieldAlarmRiskMax.specified=false");
    }

    @Test
    @Transactional
    void getAllAlarmRiskIndexSummariesByFieldAlarmRiskMaxIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        alarmRiskIndexSummaryRepository.saveAndFlush(alarmRiskIndexSummary);

        // Get all the alarmRiskIndexSummaryList where fieldAlarmRiskMax is greater than or equal to DEFAULT_FIELD_ALARM_RISK_MAX
        defaultAlarmRiskIndexSummaryShouldBeFound("fieldAlarmRiskMax.greaterThanOrEqual=" + DEFAULT_FIELD_ALARM_RISK_MAX);

        // Get all the alarmRiskIndexSummaryList where fieldAlarmRiskMax is greater than or equal to UPDATED_FIELD_ALARM_RISK_MAX
        defaultAlarmRiskIndexSummaryShouldNotBeFound("fieldAlarmRiskMax.greaterThanOrEqual=" + UPDATED_FIELD_ALARM_RISK_MAX);
    }

    @Test
    @Transactional
    void getAllAlarmRiskIndexSummariesByFieldAlarmRiskMaxIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        alarmRiskIndexSummaryRepository.saveAndFlush(alarmRiskIndexSummary);

        // Get all the alarmRiskIndexSummaryList where fieldAlarmRiskMax is less than or equal to DEFAULT_FIELD_ALARM_RISK_MAX
        defaultAlarmRiskIndexSummaryShouldBeFound("fieldAlarmRiskMax.lessThanOrEqual=" + DEFAULT_FIELD_ALARM_RISK_MAX);

        // Get all the alarmRiskIndexSummaryList where fieldAlarmRiskMax is less than or equal to SMALLER_FIELD_ALARM_RISK_MAX
        defaultAlarmRiskIndexSummaryShouldNotBeFound("fieldAlarmRiskMax.lessThanOrEqual=" + SMALLER_FIELD_ALARM_RISK_MAX);
    }

    @Test
    @Transactional
    void getAllAlarmRiskIndexSummariesByFieldAlarmRiskMaxIsLessThanSomething() throws Exception {
        // Initialize the database
        alarmRiskIndexSummaryRepository.saveAndFlush(alarmRiskIndexSummary);

        // Get all the alarmRiskIndexSummaryList where fieldAlarmRiskMax is less than DEFAULT_FIELD_ALARM_RISK_MAX
        defaultAlarmRiskIndexSummaryShouldNotBeFound("fieldAlarmRiskMax.lessThan=" + DEFAULT_FIELD_ALARM_RISK_MAX);

        // Get all the alarmRiskIndexSummaryList where fieldAlarmRiskMax is less than UPDATED_FIELD_ALARM_RISK_MAX
        defaultAlarmRiskIndexSummaryShouldBeFound("fieldAlarmRiskMax.lessThan=" + UPDATED_FIELD_ALARM_RISK_MAX);
    }

    @Test
    @Transactional
    void getAllAlarmRiskIndexSummariesByFieldAlarmRiskMaxIsGreaterThanSomething() throws Exception {
        // Initialize the database
        alarmRiskIndexSummaryRepository.saveAndFlush(alarmRiskIndexSummary);

        // Get all the alarmRiskIndexSummaryList where fieldAlarmRiskMax is greater than DEFAULT_FIELD_ALARM_RISK_MAX
        defaultAlarmRiskIndexSummaryShouldNotBeFound("fieldAlarmRiskMax.greaterThan=" + DEFAULT_FIELD_ALARM_RISK_MAX);

        // Get all the alarmRiskIndexSummaryList where fieldAlarmRiskMax is greater than SMALLER_FIELD_ALARM_RISK_MAX
        defaultAlarmRiskIndexSummaryShouldBeFound("fieldAlarmRiskMax.greaterThan=" + SMALLER_FIELD_ALARM_RISK_MAX);
    }

    @Test
    @Transactional
    void getAllAlarmRiskIndexSummariesByFieldAlarmRiskMinIsEqualToSomething() throws Exception {
        // Initialize the database
        alarmRiskIndexSummaryRepository.saveAndFlush(alarmRiskIndexSummary);

        // Get all the alarmRiskIndexSummaryList where fieldAlarmRiskMin equals to DEFAULT_FIELD_ALARM_RISK_MIN
        defaultAlarmRiskIndexSummaryShouldBeFound("fieldAlarmRiskMin.equals=" + DEFAULT_FIELD_ALARM_RISK_MIN);

        // Get all the alarmRiskIndexSummaryList where fieldAlarmRiskMin equals to UPDATED_FIELD_ALARM_RISK_MIN
        defaultAlarmRiskIndexSummaryShouldNotBeFound("fieldAlarmRiskMin.equals=" + UPDATED_FIELD_ALARM_RISK_MIN);
    }

    @Test
    @Transactional
    void getAllAlarmRiskIndexSummariesByFieldAlarmRiskMinIsInShouldWork() throws Exception {
        // Initialize the database
        alarmRiskIndexSummaryRepository.saveAndFlush(alarmRiskIndexSummary);

        // Get all the alarmRiskIndexSummaryList where fieldAlarmRiskMin in DEFAULT_FIELD_ALARM_RISK_MIN or UPDATED_FIELD_ALARM_RISK_MIN
        defaultAlarmRiskIndexSummaryShouldBeFound(
            "fieldAlarmRiskMin.in=" + DEFAULT_FIELD_ALARM_RISK_MIN + "," + UPDATED_FIELD_ALARM_RISK_MIN
        );

        // Get all the alarmRiskIndexSummaryList where fieldAlarmRiskMin equals to UPDATED_FIELD_ALARM_RISK_MIN
        defaultAlarmRiskIndexSummaryShouldNotBeFound("fieldAlarmRiskMin.in=" + UPDATED_FIELD_ALARM_RISK_MIN);
    }

    @Test
    @Transactional
    void getAllAlarmRiskIndexSummariesByFieldAlarmRiskMinIsNullOrNotNull() throws Exception {
        // Initialize the database
        alarmRiskIndexSummaryRepository.saveAndFlush(alarmRiskIndexSummary);

        // Get all the alarmRiskIndexSummaryList where fieldAlarmRiskMin is not null
        defaultAlarmRiskIndexSummaryShouldBeFound("fieldAlarmRiskMin.specified=true");

        // Get all the alarmRiskIndexSummaryList where fieldAlarmRiskMin is null
        defaultAlarmRiskIndexSummaryShouldNotBeFound("fieldAlarmRiskMin.specified=false");
    }

    @Test
    @Transactional
    void getAllAlarmRiskIndexSummariesByFieldAlarmRiskMinIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        alarmRiskIndexSummaryRepository.saveAndFlush(alarmRiskIndexSummary);

        // Get all the alarmRiskIndexSummaryList where fieldAlarmRiskMin is greater than or equal to DEFAULT_FIELD_ALARM_RISK_MIN
        defaultAlarmRiskIndexSummaryShouldBeFound("fieldAlarmRiskMin.greaterThanOrEqual=" + DEFAULT_FIELD_ALARM_RISK_MIN);

        // Get all the alarmRiskIndexSummaryList where fieldAlarmRiskMin is greater than or equal to UPDATED_FIELD_ALARM_RISK_MIN
        defaultAlarmRiskIndexSummaryShouldNotBeFound("fieldAlarmRiskMin.greaterThanOrEqual=" + UPDATED_FIELD_ALARM_RISK_MIN);
    }

    @Test
    @Transactional
    void getAllAlarmRiskIndexSummariesByFieldAlarmRiskMinIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        alarmRiskIndexSummaryRepository.saveAndFlush(alarmRiskIndexSummary);

        // Get all the alarmRiskIndexSummaryList where fieldAlarmRiskMin is less than or equal to DEFAULT_FIELD_ALARM_RISK_MIN
        defaultAlarmRiskIndexSummaryShouldBeFound("fieldAlarmRiskMin.lessThanOrEqual=" + DEFAULT_FIELD_ALARM_RISK_MIN);

        // Get all the alarmRiskIndexSummaryList where fieldAlarmRiskMin is less than or equal to SMALLER_FIELD_ALARM_RISK_MIN
        defaultAlarmRiskIndexSummaryShouldNotBeFound("fieldAlarmRiskMin.lessThanOrEqual=" + SMALLER_FIELD_ALARM_RISK_MIN);
    }

    @Test
    @Transactional
    void getAllAlarmRiskIndexSummariesByFieldAlarmRiskMinIsLessThanSomething() throws Exception {
        // Initialize the database
        alarmRiskIndexSummaryRepository.saveAndFlush(alarmRiskIndexSummary);

        // Get all the alarmRiskIndexSummaryList where fieldAlarmRiskMin is less than DEFAULT_FIELD_ALARM_RISK_MIN
        defaultAlarmRiskIndexSummaryShouldNotBeFound("fieldAlarmRiskMin.lessThan=" + DEFAULT_FIELD_ALARM_RISK_MIN);

        // Get all the alarmRiskIndexSummaryList where fieldAlarmRiskMin is less than UPDATED_FIELD_ALARM_RISK_MIN
        defaultAlarmRiskIndexSummaryShouldBeFound("fieldAlarmRiskMin.lessThan=" + UPDATED_FIELD_ALARM_RISK_MIN);
    }

    @Test
    @Transactional
    void getAllAlarmRiskIndexSummariesByFieldAlarmRiskMinIsGreaterThanSomething() throws Exception {
        // Initialize the database
        alarmRiskIndexSummaryRepository.saveAndFlush(alarmRiskIndexSummary);

        // Get all the alarmRiskIndexSummaryList where fieldAlarmRiskMin is greater than DEFAULT_FIELD_ALARM_RISK_MIN
        defaultAlarmRiskIndexSummaryShouldNotBeFound("fieldAlarmRiskMin.greaterThan=" + DEFAULT_FIELD_ALARM_RISK_MIN);

        // Get all the alarmRiskIndexSummaryList where fieldAlarmRiskMin is greater than SMALLER_FIELD_ALARM_RISK_MIN
        defaultAlarmRiskIndexSummaryShouldBeFound("fieldAlarmRiskMin.greaterThan=" + SMALLER_FIELD_ALARM_RISK_MIN);
    }

    @Test
    @Transactional
    void getAllAlarmRiskIndexSummariesByStartTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        alarmRiskIndexSummaryRepository.saveAndFlush(alarmRiskIndexSummary);

        // Get all the alarmRiskIndexSummaryList where startTime equals to DEFAULT_START_TIME
        defaultAlarmRiskIndexSummaryShouldBeFound("startTime.equals=" + DEFAULT_START_TIME);

        // Get all the alarmRiskIndexSummaryList where startTime equals to UPDATED_START_TIME
        defaultAlarmRiskIndexSummaryShouldNotBeFound("startTime.equals=" + UPDATED_START_TIME);
    }

    @Test
    @Transactional
    void getAllAlarmRiskIndexSummariesByStartTimeIsInShouldWork() throws Exception {
        // Initialize the database
        alarmRiskIndexSummaryRepository.saveAndFlush(alarmRiskIndexSummary);

        // Get all the alarmRiskIndexSummaryList where startTime in DEFAULT_START_TIME or UPDATED_START_TIME
        defaultAlarmRiskIndexSummaryShouldBeFound("startTime.in=" + DEFAULT_START_TIME + "," + UPDATED_START_TIME);

        // Get all the alarmRiskIndexSummaryList where startTime equals to UPDATED_START_TIME
        defaultAlarmRiskIndexSummaryShouldNotBeFound("startTime.in=" + UPDATED_START_TIME);
    }

    @Test
    @Transactional
    void getAllAlarmRiskIndexSummariesByStartTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        alarmRiskIndexSummaryRepository.saveAndFlush(alarmRiskIndexSummary);

        // Get all the alarmRiskIndexSummaryList where startTime is not null
        defaultAlarmRiskIndexSummaryShouldBeFound("startTime.specified=true");

        // Get all the alarmRiskIndexSummaryList where startTime is null
        defaultAlarmRiskIndexSummaryShouldNotBeFound("startTime.specified=false");
    }

    @Test
    @Transactional
    void getAllAlarmRiskIndexSummariesByEndTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        alarmRiskIndexSummaryRepository.saveAndFlush(alarmRiskIndexSummary);

        // Get all the alarmRiskIndexSummaryList where endTime equals to DEFAULT_END_TIME
        defaultAlarmRiskIndexSummaryShouldBeFound("endTime.equals=" + DEFAULT_END_TIME);

        // Get all the alarmRiskIndexSummaryList where endTime equals to UPDATED_END_TIME
        defaultAlarmRiskIndexSummaryShouldNotBeFound("endTime.equals=" + UPDATED_END_TIME);
    }

    @Test
    @Transactional
    void getAllAlarmRiskIndexSummariesByEndTimeIsInShouldWork() throws Exception {
        // Initialize the database
        alarmRiskIndexSummaryRepository.saveAndFlush(alarmRiskIndexSummary);

        // Get all the alarmRiskIndexSummaryList where endTime in DEFAULT_END_TIME or UPDATED_END_TIME
        defaultAlarmRiskIndexSummaryShouldBeFound("endTime.in=" + DEFAULT_END_TIME + "," + UPDATED_END_TIME);

        // Get all the alarmRiskIndexSummaryList where endTime equals to UPDATED_END_TIME
        defaultAlarmRiskIndexSummaryShouldNotBeFound("endTime.in=" + UPDATED_END_TIME);
    }

    @Test
    @Transactional
    void getAllAlarmRiskIndexSummariesByEndTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        alarmRiskIndexSummaryRepository.saveAndFlush(alarmRiskIndexSummary);

        // Get all the alarmRiskIndexSummaryList where endTime is not null
        defaultAlarmRiskIndexSummaryShouldBeFound("endTime.specified=true");

        // Get all the alarmRiskIndexSummaryList where endTime is null
        defaultAlarmRiskIndexSummaryShouldNotBeFound("endTime.specified=false");
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultAlarmRiskIndexSummaryShouldBeFound(String filter) throws Exception {
        restAlarmRiskIndexSummaryMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(alarmRiskIndexSummary.getId().toString())))
            .andExpect(jsonPath("$.[*].usuarioId").value(hasItem(DEFAULT_USUARIO_ID)))
            .andExpect(jsonPath("$.[*].empresaId").value(hasItem(DEFAULT_EMPRESA_ID)))
            .andExpect(jsonPath("$.[*].fieldAlarmRiskAverage").value(hasItem(DEFAULT_FIELD_ALARM_RISK_AVERAGE.doubleValue())))
            .andExpect(jsonPath("$.[*].fieldAlarmRiskMax").value(hasItem(DEFAULT_FIELD_ALARM_RISK_MAX.doubleValue())))
            .andExpect(jsonPath("$.[*].fieldAlarmRiskMin").value(hasItem(DEFAULT_FIELD_ALARM_RISK_MIN.doubleValue())))
            .andExpect(jsonPath("$.[*].startTime").value(hasItem(DEFAULT_START_TIME.toString())))
            .andExpect(jsonPath("$.[*].endTime").value(hasItem(DEFAULT_END_TIME.toString())));

        // Check, that the count call also returns 1
        restAlarmRiskIndexSummaryMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultAlarmRiskIndexSummaryShouldNotBeFound(String filter) throws Exception {
        restAlarmRiskIndexSummaryMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restAlarmRiskIndexSummaryMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingAlarmRiskIndexSummary() throws Exception {
        // Get the alarmRiskIndexSummary
        restAlarmRiskIndexSummaryMockMvc.perform(get(ENTITY_API_URL_ID, UUID.randomUUID().toString())).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingAlarmRiskIndexSummary() throws Exception {
        // Initialize the database
        alarmRiskIndexSummaryRepository.saveAndFlush(alarmRiskIndexSummary);

        int databaseSizeBeforeUpdate = alarmRiskIndexSummaryRepository.findAll().size();

        // Update the alarmRiskIndexSummary
        AlarmRiskIndexSummary updatedAlarmRiskIndexSummary = alarmRiskIndexSummaryRepository.findById(alarmRiskIndexSummary.getId()).get();
        // Disconnect from session so that the updates on updatedAlarmRiskIndexSummary are not directly saved in db
        em.detach(updatedAlarmRiskIndexSummary);
        updatedAlarmRiskIndexSummary
            .usuarioId(UPDATED_USUARIO_ID)
            .empresaId(UPDATED_EMPRESA_ID)
            .fieldAlarmRiskAverage(UPDATED_FIELD_ALARM_RISK_AVERAGE)
            .fieldAlarmRiskMax(UPDATED_FIELD_ALARM_RISK_MAX)
            .fieldAlarmRiskMin(UPDATED_FIELD_ALARM_RISK_MIN)
            .startTime(UPDATED_START_TIME)
            .endTime(UPDATED_END_TIME);

        restAlarmRiskIndexSummaryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedAlarmRiskIndexSummary.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedAlarmRiskIndexSummary))
            )
            .andExpect(status().isOk());

        // Validate the AlarmRiskIndexSummary in the database
        List<AlarmRiskIndexSummary> alarmRiskIndexSummaryList = alarmRiskIndexSummaryRepository.findAll();
        assertThat(alarmRiskIndexSummaryList).hasSize(databaseSizeBeforeUpdate);
        AlarmRiskIndexSummary testAlarmRiskIndexSummary = alarmRiskIndexSummaryList.get(alarmRiskIndexSummaryList.size() - 1);
        assertThat(testAlarmRiskIndexSummary.getUsuarioId()).isEqualTo(UPDATED_USUARIO_ID);
        assertThat(testAlarmRiskIndexSummary.getEmpresaId()).isEqualTo(UPDATED_EMPRESA_ID);
        assertThat(testAlarmRiskIndexSummary.getFieldAlarmRiskAverage()).isEqualTo(UPDATED_FIELD_ALARM_RISK_AVERAGE);
        assertThat(testAlarmRiskIndexSummary.getFieldAlarmRiskMax()).isEqualTo(UPDATED_FIELD_ALARM_RISK_MAX);
        assertThat(testAlarmRiskIndexSummary.getFieldAlarmRiskMin()).isEqualTo(UPDATED_FIELD_ALARM_RISK_MIN);
        assertThat(testAlarmRiskIndexSummary.getStartTime()).isEqualTo(UPDATED_START_TIME);
        assertThat(testAlarmRiskIndexSummary.getEndTime()).isEqualTo(UPDATED_END_TIME);
    }

    @Test
    @Transactional
    void putNonExistingAlarmRiskIndexSummary() throws Exception {
        int databaseSizeBeforeUpdate = alarmRiskIndexSummaryRepository.findAll().size();
        alarmRiskIndexSummary.setId(UUID.randomUUID());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAlarmRiskIndexSummaryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, alarmRiskIndexSummary.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(alarmRiskIndexSummary))
            )
            .andExpect(status().isBadRequest());

        // Validate the AlarmRiskIndexSummary in the database
        List<AlarmRiskIndexSummary> alarmRiskIndexSummaryList = alarmRiskIndexSummaryRepository.findAll();
        assertThat(alarmRiskIndexSummaryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAlarmRiskIndexSummary() throws Exception {
        int databaseSizeBeforeUpdate = alarmRiskIndexSummaryRepository.findAll().size();
        alarmRiskIndexSummary.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAlarmRiskIndexSummaryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(alarmRiskIndexSummary))
            )
            .andExpect(status().isBadRequest());

        // Validate the AlarmRiskIndexSummary in the database
        List<AlarmRiskIndexSummary> alarmRiskIndexSummaryList = alarmRiskIndexSummaryRepository.findAll();
        assertThat(alarmRiskIndexSummaryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAlarmRiskIndexSummary() throws Exception {
        int databaseSizeBeforeUpdate = alarmRiskIndexSummaryRepository.findAll().size();
        alarmRiskIndexSummary.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAlarmRiskIndexSummaryMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(alarmRiskIndexSummary))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AlarmRiskIndexSummary in the database
        List<AlarmRiskIndexSummary> alarmRiskIndexSummaryList = alarmRiskIndexSummaryRepository.findAll();
        assertThat(alarmRiskIndexSummaryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAlarmRiskIndexSummaryWithPatch() throws Exception {
        // Initialize the database
        alarmRiskIndexSummaryRepository.saveAndFlush(alarmRiskIndexSummary);

        int databaseSizeBeforeUpdate = alarmRiskIndexSummaryRepository.findAll().size();

        // Update the alarmRiskIndexSummary using partial update
        AlarmRiskIndexSummary partialUpdatedAlarmRiskIndexSummary = new AlarmRiskIndexSummary();
        partialUpdatedAlarmRiskIndexSummary.setId(alarmRiskIndexSummary.getId());

        partialUpdatedAlarmRiskIndexSummary
            .usuarioId(UPDATED_USUARIO_ID)
            .empresaId(UPDATED_EMPRESA_ID)
            .fieldAlarmRiskMin(UPDATED_FIELD_ALARM_RISK_MIN)
            .startTime(UPDATED_START_TIME);

        restAlarmRiskIndexSummaryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAlarmRiskIndexSummary.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAlarmRiskIndexSummary))
            )
            .andExpect(status().isOk());

        // Validate the AlarmRiskIndexSummary in the database
        List<AlarmRiskIndexSummary> alarmRiskIndexSummaryList = alarmRiskIndexSummaryRepository.findAll();
        assertThat(alarmRiskIndexSummaryList).hasSize(databaseSizeBeforeUpdate);
        AlarmRiskIndexSummary testAlarmRiskIndexSummary = alarmRiskIndexSummaryList.get(alarmRiskIndexSummaryList.size() - 1);
        assertThat(testAlarmRiskIndexSummary.getUsuarioId()).isEqualTo(UPDATED_USUARIO_ID);
        assertThat(testAlarmRiskIndexSummary.getEmpresaId()).isEqualTo(UPDATED_EMPRESA_ID);
        assertThat(testAlarmRiskIndexSummary.getFieldAlarmRiskAverage()).isEqualTo(DEFAULT_FIELD_ALARM_RISK_AVERAGE);
        assertThat(testAlarmRiskIndexSummary.getFieldAlarmRiskMax()).isEqualTo(DEFAULT_FIELD_ALARM_RISK_MAX);
        assertThat(testAlarmRiskIndexSummary.getFieldAlarmRiskMin()).isEqualTo(UPDATED_FIELD_ALARM_RISK_MIN);
        assertThat(testAlarmRiskIndexSummary.getStartTime()).isEqualTo(UPDATED_START_TIME);
        assertThat(testAlarmRiskIndexSummary.getEndTime()).isEqualTo(DEFAULT_END_TIME);
    }

    @Test
    @Transactional
    void fullUpdateAlarmRiskIndexSummaryWithPatch() throws Exception {
        // Initialize the database
        alarmRiskIndexSummaryRepository.saveAndFlush(alarmRiskIndexSummary);

        int databaseSizeBeforeUpdate = alarmRiskIndexSummaryRepository.findAll().size();

        // Update the alarmRiskIndexSummary using partial update
        AlarmRiskIndexSummary partialUpdatedAlarmRiskIndexSummary = new AlarmRiskIndexSummary();
        partialUpdatedAlarmRiskIndexSummary.setId(alarmRiskIndexSummary.getId());

        partialUpdatedAlarmRiskIndexSummary
            .usuarioId(UPDATED_USUARIO_ID)
            .empresaId(UPDATED_EMPRESA_ID)
            .fieldAlarmRiskAverage(UPDATED_FIELD_ALARM_RISK_AVERAGE)
            .fieldAlarmRiskMax(UPDATED_FIELD_ALARM_RISK_MAX)
            .fieldAlarmRiskMin(UPDATED_FIELD_ALARM_RISK_MIN)
            .startTime(UPDATED_START_TIME)
            .endTime(UPDATED_END_TIME);

        restAlarmRiskIndexSummaryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAlarmRiskIndexSummary.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAlarmRiskIndexSummary))
            )
            .andExpect(status().isOk());

        // Validate the AlarmRiskIndexSummary in the database
        List<AlarmRiskIndexSummary> alarmRiskIndexSummaryList = alarmRiskIndexSummaryRepository.findAll();
        assertThat(alarmRiskIndexSummaryList).hasSize(databaseSizeBeforeUpdate);
        AlarmRiskIndexSummary testAlarmRiskIndexSummary = alarmRiskIndexSummaryList.get(alarmRiskIndexSummaryList.size() - 1);
        assertThat(testAlarmRiskIndexSummary.getUsuarioId()).isEqualTo(UPDATED_USUARIO_ID);
        assertThat(testAlarmRiskIndexSummary.getEmpresaId()).isEqualTo(UPDATED_EMPRESA_ID);
        assertThat(testAlarmRiskIndexSummary.getFieldAlarmRiskAverage()).isEqualTo(UPDATED_FIELD_ALARM_RISK_AVERAGE);
        assertThat(testAlarmRiskIndexSummary.getFieldAlarmRiskMax()).isEqualTo(UPDATED_FIELD_ALARM_RISK_MAX);
        assertThat(testAlarmRiskIndexSummary.getFieldAlarmRiskMin()).isEqualTo(UPDATED_FIELD_ALARM_RISK_MIN);
        assertThat(testAlarmRiskIndexSummary.getStartTime()).isEqualTo(UPDATED_START_TIME);
        assertThat(testAlarmRiskIndexSummary.getEndTime()).isEqualTo(UPDATED_END_TIME);
    }

    @Test
    @Transactional
    void patchNonExistingAlarmRiskIndexSummary() throws Exception {
        int databaseSizeBeforeUpdate = alarmRiskIndexSummaryRepository.findAll().size();
        alarmRiskIndexSummary.setId(UUID.randomUUID());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAlarmRiskIndexSummaryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, alarmRiskIndexSummary.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(alarmRiskIndexSummary))
            )
            .andExpect(status().isBadRequest());

        // Validate the AlarmRiskIndexSummary in the database
        List<AlarmRiskIndexSummary> alarmRiskIndexSummaryList = alarmRiskIndexSummaryRepository.findAll();
        assertThat(alarmRiskIndexSummaryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAlarmRiskIndexSummary() throws Exception {
        int databaseSizeBeforeUpdate = alarmRiskIndexSummaryRepository.findAll().size();
        alarmRiskIndexSummary.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAlarmRiskIndexSummaryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(alarmRiskIndexSummary))
            )
            .andExpect(status().isBadRequest());

        // Validate the AlarmRiskIndexSummary in the database
        List<AlarmRiskIndexSummary> alarmRiskIndexSummaryList = alarmRiskIndexSummaryRepository.findAll();
        assertThat(alarmRiskIndexSummaryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAlarmRiskIndexSummary() throws Exception {
        int databaseSizeBeforeUpdate = alarmRiskIndexSummaryRepository.findAll().size();
        alarmRiskIndexSummary.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAlarmRiskIndexSummaryMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(alarmRiskIndexSummary))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AlarmRiskIndexSummary in the database
        List<AlarmRiskIndexSummary> alarmRiskIndexSummaryList = alarmRiskIndexSummaryRepository.findAll();
        assertThat(alarmRiskIndexSummaryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAlarmRiskIndexSummary() throws Exception {
        // Initialize the database
        alarmRiskIndexSummaryRepository.saveAndFlush(alarmRiskIndexSummary);

        int databaseSizeBeforeDelete = alarmRiskIndexSummaryRepository.findAll().size();

        // Delete the alarmRiskIndexSummary
        restAlarmRiskIndexSummaryMockMvc
            .perform(delete(ENTITY_API_URL_ID, alarmRiskIndexSummary.getId().toString()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AlarmRiskIndexSummary> alarmRiskIndexSummaryList = alarmRiskIndexSummaryRepository.findAll();
        assertThat(alarmRiskIndexSummaryList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
