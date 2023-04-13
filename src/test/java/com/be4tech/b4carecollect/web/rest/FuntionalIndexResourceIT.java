package com.be4tech.b4carecollect.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.be4tech.b4carecollect.IntegrationTest;
import com.be4tech.b4carecollect.domain.FuntionalIndex;
import com.be4tech.b4carecollect.repository.FuntionalIndexRepository;
import com.be4tech.b4carecollect.service.criteria.FuntionalIndexCriteria;
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
 * Integration tests for the {@link FuntionalIndexResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class FuntionalIndexResourceIT {

    private static final String DEFAULT_USUARIO_ID = "AAAAAAAAAA";
    private static final String UPDATED_USUARIO_ID = "BBBBBBBBBB";

    private static final String DEFAULT_EMPRESA_ID = "AAAAAAAAAA";
    private static final String UPDATED_EMPRESA_ID = "BBBBBBBBBB";

    private static final Integer DEFAULT_BODY_HEALTH_SCORE = 1;
    private static final Integer UPDATED_BODY_HEALTH_SCORE = 2;
    private static final Integer SMALLER_BODY_HEALTH_SCORE = 1 - 1;

    private static final Integer DEFAULT_MENTAL_HEALTH_SCORE = 1;
    private static final Integer UPDATED_MENTAL_HEALTH_SCORE = 2;
    private static final Integer SMALLER_MENTAL_HEALTH_SCORE = 1 - 1;

    private static final Integer DEFAULT_SLEEP_HEALTH_SCORE = 1;
    private static final Integer UPDATED_SLEEP_HEALTH_SCORE = 2;
    private static final Integer SMALLER_SLEEP_HEALTH_SCORE = 1 - 1;

    private static final Integer DEFAULT_FUNTIONAL_INDEX = 1;
    private static final Integer UPDATED_FUNTIONAL_INDEX = 2;
    private static final Integer SMALLER_FUNTIONAL_INDEX = 1 - 1;

    private static final Integer DEFAULT_ALARM_RISK_SCORE = 1;
    private static final Integer UPDATED_ALARM_RISK_SCORE = 2;
    private static final Integer SMALLER_ALARM_RISK_SCORE = 1 - 1;

    private static final Instant DEFAULT_START_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_START_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/funtional-indices";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private FuntionalIndexRepository funtionalIndexRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFuntionalIndexMockMvc;

    private FuntionalIndex funtionalIndex;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FuntionalIndex createEntity(EntityManager em) {
        FuntionalIndex funtionalIndex = new FuntionalIndex()
            .usuarioId(DEFAULT_USUARIO_ID)
            .empresaId(DEFAULT_EMPRESA_ID)
            .bodyHealthScore(DEFAULT_BODY_HEALTH_SCORE)
            .mentalHealthScore(DEFAULT_MENTAL_HEALTH_SCORE)
            .sleepHealthScore(DEFAULT_SLEEP_HEALTH_SCORE)
            .funtionalIndex(DEFAULT_FUNTIONAL_INDEX)
            .alarmRiskScore(DEFAULT_ALARM_RISK_SCORE)
            .startTime(DEFAULT_START_TIME);
        return funtionalIndex;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FuntionalIndex createUpdatedEntity(EntityManager em) {
        FuntionalIndex funtionalIndex = new FuntionalIndex()
            .usuarioId(UPDATED_USUARIO_ID)
            .empresaId(UPDATED_EMPRESA_ID)
            .bodyHealthScore(UPDATED_BODY_HEALTH_SCORE)
            .mentalHealthScore(UPDATED_MENTAL_HEALTH_SCORE)
            .sleepHealthScore(UPDATED_SLEEP_HEALTH_SCORE)
            .funtionalIndex(UPDATED_FUNTIONAL_INDEX)
            .alarmRiskScore(UPDATED_ALARM_RISK_SCORE)
            .startTime(UPDATED_START_TIME);
        return funtionalIndex;
    }

    @BeforeEach
    public void initTest() {
        funtionalIndex = createEntity(em);
    }

    @Test
    @Transactional
    void createFuntionalIndex() throws Exception {
        int databaseSizeBeforeCreate = funtionalIndexRepository.findAll().size();
        // Create the FuntionalIndex
        restFuntionalIndexMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(funtionalIndex))
            )
            .andExpect(status().isCreated());

        // Validate the FuntionalIndex in the database
        List<FuntionalIndex> funtionalIndexList = funtionalIndexRepository.findAll();
        assertThat(funtionalIndexList).hasSize(databaseSizeBeforeCreate + 1);
        FuntionalIndex testFuntionalIndex = funtionalIndexList.get(funtionalIndexList.size() - 1);
        assertThat(testFuntionalIndex.getUsuarioId()).isEqualTo(DEFAULT_USUARIO_ID);
        assertThat(testFuntionalIndex.getEmpresaId()).isEqualTo(DEFAULT_EMPRESA_ID);
        assertThat(testFuntionalIndex.getBodyHealthScore()).isEqualTo(DEFAULT_BODY_HEALTH_SCORE);
        assertThat(testFuntionalIndex.getMentalHealthScore()).isEqualTo(DEFAULT_MENTAL_HEALTH_SCORE);
        assertThat(testFuntionalIndex.getSleepHealthScore()).isEqualTo(DEFAULT_SLEEP_HEALTH_SCORE);
        assertThat(testFuntionalIndex.getFuntionalIndex()).isEqualTo(DEFAULT_FUNTIONAL_INDEX);
        assertThat(testFuntionalIndex.getAlarmRiskScore()).isEqualTo(DEFAULT_ALARM_RISK_SCORE);
        assertThat(testFuntionalIndex.getStartTime()).isEqualTo(DEFAULT_START_TIME);
    }

    @Test
    @Transactional
    void createFuntionalIndexWithExistingId() throws Exception {
        // Create the FuntionalIndex with an existing ID
        funtionalIndexRepository.saveAndFlush(funtionalIndex);

        int databaseSizeBeforeCreate = funtionalIndexRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFuntionalIndexMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(funtionalIndex))
            )
            .andExpect(status().isBadRequest());

        // Validate the FuntionalIndex in the database
        List<FuntionalIndex> funtionalIndexList = funtionalIndexRepository.findAll();
        assertThat(funtionalIndexList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllFuntionalIndices() throws Exception {
        // Initialize the database
        funtionalIndexRepository.saveAndFlush(funtionalIndex);

        // Get all the funtionalIndexList
        restFuntionalIndexMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(funtionalIndex.getId().toString())))
            .andExpect(jsonPath("$.[*].usuarioId").value(hasItem(DEFAULT_USUARIO_ID)))
            .andExpect(jsonPath("$.[*].empresaId").value(hasItem(DEFAULT_EMPRESA_ID)))
            .andExpect(jsonPath("$.[*].bodyHealthScore").value(hasItem(DEFAULT_BODY_HEALTH_SCORE)))
            .andExpect(jsonPath("$.[*].mentalHealthScore").value(hasItem(DEFAULT_MENTAL_HEALTH_SCORE)))
            .andExpect(jsonPath("$.[*].sleepHealthScore").value(hasItem(DEFAULT_SLEEP_HEALTH_SCORE)))
            .andExpect(jsonPath("$.[*].funtionalIndex").value(hasItem(DEFAULT_FUNTIONAL_INDEX)))
            .andExpect(jsonPath("$.[*].alarmRiskScore").value(hasItem(DEFAULT_ALARM_RISK_SCORE)))
            .andExpect(jsonPath("$.[*].startTime").value(hasItem(DEFAULT_START_TIME.toString())));
    }

    @Test
    @Transactional
    void getFuntionalIndex() throws Exception {
        // Initialize the database
        funtionalIndexRepository.saveAndFlush(funtionalIndex);

        // Get the funtionalIndex
        restFuntionalIndexMockMvc
            .perform(get(ENTITY_API_URL_ID, funtionalIndex.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(funtionalIndex.getId().toString()))
            .andExpect(jsonPath("$.usuarioId").value(DEFAULT_USUARIO_ID))
            .andExpect(jsonPath("$.empresaId").value(DEFAULT_EMPRESA_ID))
            .andExpect(jsonPath("$.bodyHealthScore").value(DEFAULT_BODY_HEALTH_SCORE))
            .andExpect(jsonPath("$.mentalHealthScore").value(DEFAULT_MENTAL_HEALTH_SCORE))
            .andExpect(jsonPath("$.sleepHealthScore").value(DEFAULT_SLEEP_HEALTH_SCORE))
            .andExpect(jsonPath("$.funtionalIndex").value(DEFAULT_FUNTIONAL_INDEX))
            .andExpect(jsonPath("$.alarmRiskScore").value(DEFAULT_ALARM_RISK_SCORE))
            .andExpect(jsonPath("$.startTime").value(DEFAULT_START_TIME.toString()));
    }

    @Test
    @Transactional
    void getFuntionalIndicesByIdFiltering() throws Exception {
        // Initialize the database
        funtionalIndexRepository.saveAndFlush(funtionalIndex);

        UUID id = funtionalIndex.getId();

        defaultFuntionalIndexShouldBeFound("id.equals=" + id);
        defaultFuntionalIndexShouldNotBeFound("id.notEquals=" + id);
    }

    @Test
    @Transactional
    void getAllFuntionalIndicesByUsuarioIdIsEqualToSomething() throws Exception {
        // Initialize the database
        funtionalIndexRepository.saveAndFlush(funtionalIndex);

        // Get all the funtionalIndexList where usuarioId equals to DEFAULT_USUARIO_ID
        defaultFuntionalIndexShouldBeFound("usuarioId.equals=" + DEFAULT_USUARIO_ID);

        // Get all the funtionalIndexList where usuarioId equals to UPDATED_USUARIO_ID
        defaultFuntionalIndexShouldNotBeFound("usuarioId.equals=" + UPDATED_USUARIO_ID);
    }

    @Test
    @Transactional
    void getAllFuntionalIndicesByUsuarioIdIsInShouldWork() throws Exception {
        // Initialize the database
        funtionalIndexRepository.saveAndFlush(funtionalIndex);

        // Get all the funtionalIndexList where usuarioId in DEFAULT_USUARIO_ID or UPDATED_USUARIO_ID
        defaultFuntionalIndexShouldBeFound("usuarioId.in=" + DEFAULT_USUARIO_ID + "," + UPDATED_USUARIO_ID);

        // Get all the funtionalIndexList where usuarioId equals to UPDATED_USUARIO_ID
        defaultFuntionalIndexShouldNotBeFound("usuarioId.in=" + UPDATED_USUARIO_ID);
    }

    @Test
    @Transactional
    void getAllFuntionalIndicesByUsuarioIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        funtionalIndexRepository.saveAndFlush(funtionalIndex);

        // Get all the funtionalIndexList where usuarioId is not null
        defaultFuntionalIndexShouldBeFound("usuarioId.specified=true");

        // Get all the funtionalIndexList where usuarioId is null
        defaultFuntionalIndexShouldNotBeFound("usuarioId.specified=false");
    }

    @Test
    @Transactional
    void getAllFuntionalIndicesByUsuarioIdContainsSomething() throws Exception {
        // Initialize the database
        funtionalIndexRepository.saveAndFlush(funtionalIndex);

        // Get all the funtionalIndexList where usuarioId contains DEFAULT_USUARIO_ID
        defaultFuntionalIndexShouldBeFound("usuarioId.contains=" + DEFAULT_USUARIO_ID);

        // Get all the funtionalIndexList where usuarioId contains UPDATED_USUARIO_ID
        defaultFuntionalIndexShouldNotBeFound("usuarioId.contains=" + UPDATED_USUARIO_ID);
    }

    @Test
    @Transactional
    void getAllFuntionalIndicesByUsuarioIdNotContainsSomething() throws Exception {
        // Initialize the database
        funtionalIndexRepository.saveAndFlush(funtionalIndex);

        // Get all the funtionalIndexList where usuarioId does not contain DEFAULT_USUARIO_ID
        defaultFuntionalIndexShouldNotBeFound("usuarioId.doesNotContain=" + DEFAULT_USUARIO_ID);

        // Get all the funtionalIndexList where usuarioId does not contain UPDATED_USUARIO_ID
        defaultFuntionalIndexShouldBeFound("usuarioId.doesNotContain=" + UPDATED_USUARIO_ID);
    }

    @Test
    @Transactional
    void getAllFuntionalIndicesByEmpresaIdIsEqualToSomething() throws Exception {
        // Initialize the database
        funtionalIndexRepository.saveAndFlush(funtionalIndex);

        // Get all the funtionalIndexList where empresaId equals to DEFAULT_EMPRESA_ID
        defaultFuntionalIndexShouldBeFound("empresaId.equals=" + DEFAULT_EMPRESA_ID);

        // Get all the funtionalIndexList where empresaId equals to UPDATED_EMPRESA_ID
        defaultFuntionalIndexShouldNotBeFound("empresaId.equals=" + UPDATED_EMPRESA_ID);
    }

    @Test
    @Transactional
    void getAllFuntionalIndicesByEmpresaIdIsInShouldWork() throws Exception {
        // Initialize the database
        funtionalIndexRepository.saveAndFlush(funtionalIndex);

        // Get all the funtionalIndexList where empresaId in DEFAULT_EMPRESA_ID or UPDATED_EMPRESA_ID
        defaultFuntionalIndexShouldBeFound("empresaId.in=" + DEFAULT_EMPRESA_ID + "," + UPDATED_EMPRESA_ID);

        // Get all the funtionalIndexList where empresaId equals to UPDATED_EMPRESA_ID
        defaultFuntionalIndexShouldNotBeFound("empresaId.in=" + UPDATED_EMPRESA_ID);
    }

    @Test
    @Transactional
    void getAllFuntionalIndicesByEmpresaIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        funtionalIndexRepository.saveAndFlush(funtionalIndex);

        // Get all the funtionalIndexList where empresaId is not null
        defaultFuntionalIndexShouldBeFound("empresaId.specified=true");

        // Get all the funtionalIndexList where empresaId is null
        defaultFuntionalIndexShouldNotBeFound("empresaId.specified=false");
    }

    @Test
    @Transactional
    void getAllFuntionalIndicesByEmpresaIdContainsSomething() throws Exception {
        // Initialize the database
        funtionalIndexRepository.saveAndFlush(funtionalIndex);

        // Get all the funtionalIndexList where empresaId contains DEFAULT_EMPRESA_ID
        defaultFuntionalIndexShouldBeFound("empresaId.contains=" + DEFAULT_EMPRESA_ID);

        // Get all the funtionalIndexList where empresaId contains UPDATED_EMPRESA_ID
        defaultFuntionalIndexShouldNotBeFound("empresaId.contains=" + UPDATED_EMPRESA_ID);
    }

    @Test
    @Transactional
    void getAllFuntionalIndicesByEmpresaIdNotContainsSomething() throws Exception {
        // Initialize the database
        funtionalIndexRepository.saveAndFlush(funtionalIndex);

        // Get all the funtionalIndexList where empresaId does not contain DEFAULT_EMPRESA_ID
        defaultFuntionalIndexShouldNotBeFound("empresaId.doesNotContain=" + DEFAULT_EMPRESA_ID);

        // Get all the funtionalIndexList where empresaId does not contain UPDATED_EMPRESA_ID
        defaultFuntionalIndexShouldBeFound("empresaId.doesNotContain=" + UPDATED_EMPRESA_ID);
    }

    @Test
    @Transactional
    void getAllFuntionalIndicesByBodyHealthScoreIsEqualToSomething() throws Exception {
        // Initialize the database
        funtionalIndexRepository.saveAndFlush(funtionalIndex);

        // Get all the funtionalIndexList where bodyHealthScore equals to DEFAULT_BODY_HEALTH_SCORE
        defaultFuntionalIndexShouldBeFound("bodyHealthScore.equals=" + DEFAULT_BODY_HEALTH_SCORE);

        // Get all the funtionalIndexList where bodyHealthScore equals to UPDATED_BODY_HEALTH_SCORE
        defaultFuntionalIndexShouldNotBeFound("bodyHealthScore.equals=" + UPDATED_BODY_HEALTH_SCORE);
    }

    @Test
    @Transactional
    void getAllFuntionalIndicesByBodyHealthScoreIsInShouldWork() throws Exception {
        // Initialize the database
        funtionalIndexRepository.saveAndFlush(funtionalIndex);

        // Get all the funtionalIndexList where bodyHealthScore in DEFAULT_BODY_HEALTH_SCORE or UPDATED_BODY_HEALTH_SCORE
        defaultFuntionalIndexShouldBeFound("bodyHealthScore.in=" + DEFAULT_BODY_HEALTH_SCORE + "," + UPDATED_BODY_HEALTH_SCORE);

        // Get all the funtionalIndexList where bodyHealthScore equals to UPDATED_BODY_HEALTH_SCORE
        defaultFuntionalIndexShouldNotBeFound("bodyHealthScore.in=" + UPDATED_BODY_HEALTH_SCORE);
    }

    @Test
    @Transactional
    void getAllFuntionalIndicesByBodyHealthScoreIsNullOrNotNull() throws Exception {
        // Initialize the database
        funtionalIndexRepository.saveAndFlush(funtionalIndex);

        // Get all the funtionalIndexList where bodyHealthScore is not null
        defaultFuntionalIndexShouldBeFound("bodyHealthScore.specified=true");

        // Get all the funtionalIndexList where bodyHealthScore is null
        defaultFuntionalIndexShouldNotBeFound("bodyHealthScore.specified=false");
    }

    @Test
    @Transactional
    void getAllFuntionalIndicesByBodyHealthScoreIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        funtionalIndexRepository.saveAndFlush(funtionalIndex);

        // Get all the funtionalIndexList where bodyHealthScore is greater than or equal to DEFAULT_BODY_HEALTH_SCORE
        defaultFuntionalIndexShouldBeFound("bodyHealthScore.greaterThanOrEqual=" + DEFAULT_BODY_HEALTH_SCORE);

        // Get all the funtionalIndexList where bodyHealthScore is greater than or equal to UPDATED_BODY_HEALTH_SCORE
        defaultFuntionalIndexShouldNotBeFound("bodyHealthScore.greaterThanOrEqual=" + UPDATED_BODY_HEALTH_SCORE);
    }

    @Test
    @Transactional
    void getAllFuntionalIndicesByBodyHealthScoreIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        funtionalIndexRepository.saveAndFlush(funtionalIndex);

        // Get all the funtionalIndexList where bodyHealthScore is less than or equal to DEFAULT_BODY_HEALTH_SCORE
        defaultFuntionalIndexShouldBeFound("bodyHealthScore.lessThanOrEqual=" + DEFAULT_BODY_HEALTH_SCORE);

        // Get all the funtionalIndexList where bodyHealthScore is less than or equal to SMALLER_BODY_HEALTH_SCORE
        defaultFuntionalIndexShouldNotBeFound("bodyHealthScore.lessThanOrEqual=" + SMALLER_BODY_HEALTH_SCORE);
    }

    @Test
    @Transactional
    void getAllFuntionalIndicesByBodyHealthScoreIsLessThanSomething() throws Exception {
        // Initialize the database
        funtionalIndexRepository.saveAndFlush(funtionalIndex);

        // Get all the funtionalIndexList where bodyHealthScore is less than DEFAULT_BODY_HEALTH_SCORE
        defaultFuntionalIndexShouldNotBeFound("bodyHealthScore.lessThan=" + DEFAULT_BODY_HEALTH_SCORE);

        // Get all the funtionalIndexList where bodyHealthScore is less than UPDATED_BODY_HEALTH_SCORE
        defaultFuntionalIndexShouldBeFound("bodyHealthScore.lessThan=" + UPDATED_BODY_HEALTH_SCORE);
    }

    @Test
    @Transactional
    void getAllFuntionalIndicesByBodyHealthScoreIsGreaterThanSomething() throws Exception {
        // Initialize the database
        funtionalIndexRepository.saveAndFlush(funtionalIndex);

        // Get all the funtionalIndexList where bodyHealthScore is greater than DEFAULT_BODY_HEALTH_SCORE
        defaultFuntionalIndexShouldNotBeFound("bodyHealthScore.greaterThan=" + DEFAULT_BODY_HEALTH_SCORE);

        // Get all the funtionalIndexList where bodyHealthScore is greater than SMALLER_BODY_HEALTH_SCORE
        defaultFuntionalIndexShouldBeFound("bodyHealthScore.greaterThan=" + SMALLER_BODY_HEALTH_SCORE);
    }

    @Test
    @Transactional
    void getAllFuntionalIndicesByMentalHealthScoreIsEqualToSomething() throws Exception {
        // Initialize the database
        funtionalIndexRepository.saveAndFlush(funtionalIndex);

        // Get all the funtionalIndexList where mentalHealthScore equals to DEFAULT_MENTAL_HEALTH_SCORE
        defaultFuntionalIndexShouldBeFound("mentalHealthScore.equals=" + DEFAULT_MENTAL_HEALTH_SCORE);

        // Get all the funtionalIndexList where mentalHealthScore equals to UPDATED_MENTAL_HEALTH_SCORE
        defaultFuntionalIndexShouldNotBeFound("mentalHealthScore.equals=" + UPDATED_MENTAL_HEALTH_SCORE);
    }

    @Test
    @Transactional
    void getAllFuntionalIndicesByMentalHealthScoreIsInShouldWork() throws Exception {
        // Initialize the database
        funtionalIndexRepository.saveAndFlush(funtionalIndex);

        // Get all the funtionalIndexList where mentalHealthScore in DEFAULT_MENTAL_HEALTH_SCORE or UPDATED_MENTAL_HEALTH_SCORE
        defaultFuntionalIndexShouldBeFound("mentalHealthScore.in=" + DEFAULT_MENTAL_HEALTH_SCORE + "," + UPDATED_MENTAL_HEALTH_SCORE);

        // Get all the funtionalIndexList where mentalHealthScore equals to UPDATED_MENTAL_HEALTH_SCORE
        defaultFuntionalIndexShouldNotBeFound("mentalHealthScore.in=" + UPDATED_MENTAL_HEALTH_SCORE);
    }

    @Test
    @Transactional
    void getAllFuntionalIndicesByMentalHealthScoreIsNullOrNotNull() throws Exception {
        // Initialize the database
        funtionalIndexRepository.saveAndFlush(funtionalIndex);

        // Get all the funtionalIndexList where mentalHealthScore is not null
        defaultFuntionalIndexShouldBeFound("mentalHealthScore.specified=true");

        // Get all the funtionalIndexList where mentalHealthScore is null
        defaultFuntionalIndexShouldNotBeFound("mentalHealthScore.specified=false");
    }

    @Test
    @Transactional
    void getAllFuntionalIndicesByMentalHealthScoreIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        funtionalIndexRepository.saveAndFlush(funtionalIndex);

        // Get all the funtionalIndexList where mentalHealthScore is greater than or equal to DEFAULT_MENTAL_HEALTH_SCORE
        defaultFuntionalIndexShouldBeFound("mentalHealthScore.greaterThanOrEqual=" + DEFAULT_MENTAL_HEALTH_SCORE);

        // Get all the funtionalIndexList where mentalHealthScore is greater than or equal to UPDATED_MENTAL_HEALTH_SCORE
        defaultFuntionalIndexShouldNotBeFound("mentalHealthScore.greaterThanOrEqual=" + UPDATED_MENTAL_HEALTH_SCORE);
    }

    @Test
    @Transactional
    void getAllFuntionalIndicesByMentalHealthScoreIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        funtionalIndexRepository.saveAndFlush(funtionalIndex);

        // Get all the funtionalIndexList where mentalHealthScore is less than or equal to DEFAULT_MENTAL_HEALTH_SCORE
        defaultFuntionalIndexShouldBeFound("mentalHealthScore.lessThanOrEqual=" + DEFAULT_MENTAL_HEALTH_SCORE);

        // Get all the funtionalIndexList where mentalHealthScore is less than or equal to SMALLER_MENTAL_HEALTH_SCORE
        defaultFuntionalIndexShouldNotBeFound("mentalHealthScore.lessThanOrEqual=" + SMALLER_MENTAL_HEALTH_SCORE);
    }

    @Test
    @Transactional
    void getAllFuntionalIndicesByMentalHealthScoreIsLessThanSomething() throws Exception {
        // Initialize the database
        funtionalIndexRepository.saveAndFlush(funtionalIndex);

        // Get all the funtionalIndexList where mentalHealthScore is less than DEFAULT_MENTAL_HEALTH_SCORE
        defaultFuntionalIndexShouldNotBeFound("mentalHealthScore.lessThan=" + DEFAULT_MENTAL_HEALTH_SCORE);

        // Get all the funtionalIndexList where mentalHealthScore is less than UPDATED_MENTAL_HEALTH_SCORE
        defaultFuntionalIndexShouldBeFound("mentalHealthScore.lessThan=" + UPDATED_MENTAL_HEALTH_SCORE);
    }

    @Test
    @Transactional
    void getAllFuntionalIndicesByMentalHealthScoreIsGreaterThanSomething() throws Exception {
        // Initialize the database
        funtionalIndexRepository.saveAndFlush(funtionalIndex);

        // Get all the funtionalIndexList where mentalHealthScore is greater than DEFAULT_MENTAL_HEALTH_SCORE
        defaultFuntionalIndexShouldNotBeFound("mentalHealthScore.greaterThan=" + DEFAULT_MENTAL_HEALTH_SCORE);

        // Get all the funtionalIndexList where mentalHealthScore is greater than SMALLER_MENTAL_HEALTH_SCORE
        defaultFuntionalIndexShouldBeFound("mentalHealthScore.greaterThan=" + SMALLER_MENTAL_HEALTH_SCORE);
    }

    @Test
    @Transactional
    void getAllFuntionalIndicesBySleepHealthScoreIsEqualToSomething() throws Exception {
        // Initialize the database
        funtionalIndexRepository.saveAndFlush(funtionalIndex);

        // Get all the funtionalIndexList where sleepHealthScore equals to DEFAULT_SLEEP_HEALTH_SCORE
        defaultFuntionalIndexShouldBeFound("sleepHealthScore.equals=" + DEFAULT_SLEEP_HEALTH_SCORE);

        // Get all the funtionalIndexList where sleepHealthScore equals to UPDATED_SLEEP_HEALTH_SCORE
        defaultFuntionalIndexShouldNotBeFound("sleepHealthScore.equals=" + UPDATED_SLEEP_HEALTH_SCORE);
    }

    @Test
    @Transactional
    void getAllFuntionalIndicesBySleepHealthScoreIsInShouldWork() throws Exception {
        // Initialize the database
        funtionalIndexRepository.saveAndFlush(funtionalIndex);

        // Get all the funtionalIndexList where sleepHealthScore in DEFAULT_SLEEP_HEALTH_SCORE or UPDATED_SLEEP_HEALTH_SCORE
        defaultFuntionalIndexShouldBeFound("sleepHealthScore.in=" + DEFAULT_SLEEP_HEALTH_SCORE + "," + UPDATED_SLEEP_HEALTH_SCORE);

        // Get all the funtionalIndexList where sleepHealthScore equals to UPDATED_SLEEP_HEALTH_SCORE
        defaultFuntionalIndexShouldNotBeFound("sleepHealthScore.in=" + UPDATED_SLEEP_HEALTH_SCORE);
    }

    @Test
    @Transactional
    void getAllFuntionalIndicesBySleepHealthScoreIsNullOrNotNull() throws Exception {
        // Initialize the database
        funtionalIndexRepository.saveAndFlush(funtionalIndex);

        // Get all the funtionalIndexList where sleepHealthScore is not null
        defaultFuntionalIndexShouldBeFound("sleepHealthScore.specified=true");

        // Get all the funtionalIndexList where sleepHealthScore is null
        defaultFuntionalIndexShouldNotBeFound("sleepHealthScore.specified=false");
    }

    @Test
    @Transactional
    void getAllFuntionalIndicesBySleepHealthScoreIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        funtionalIndexRepository.saveAndFlush(funtionalIndex);

        // Get all the funtionalIndexList where sleepHealthScore is greater than or equal to DEFAULT_SLEEP_HEALTH_SCORE
        defaultFuntionalIndexShouldBeFound("sleepHealthScore.greaterThanOrEqual=" + DEFAULT_SLEEP_HEALTH_SCORE);

        // Get all the funtionalIndexList where sleepHealthScore is greater than or equal to UPDATED_SLEEP_HEALTH_SCORE
        defaultFuntionalIndexShouldNotBeFound("sleepHealthScore.greaterThanOrEqual=" + UPDATED_SLEEP_HEALTH_SCORE);
    }

    @Test
    @Transactional
    void getAllFuntionalIndicesBySleepHealthScoreIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        funtionalIndexRepository.saveAndFlush(funtionalIndex);

        // Get all the funtionalIndexList where sleepHealthScore is less than or equal to DEFAULT_SLEEP_HEALTH_SCORE
        defaultFuntionalIndexShouldBeFound("sleepHealthScore.lessThanOrEqual=" + DEFAULT_SLEEP_HEALTH_SCORE);

        // Get all the funtionalIndexList where sleepHealthScore is less than or equal to SMALLER_SLEEP_HEALTH_SCORE
        defaultFuntionalIndexShouldNotBeFound("sleepHealthScore.lessThanOrEqual=" + SMALLER_SLEEP_HEALTH_SCORE);
    }

    @Test
    @Transactional
    void getAllFuntionalIndicesBySleepHealthScoreIsLessThanSomething() throws Exception {
        // Initialize the database
        funtionalIndexRepository.saveAndFlush(funtionalIndex);

        // Get all the funtionalIndexList where sleepHealthScore is less than DEFAULT_SLEEP_HEALTH_SCORE
        defaultFuntionalIndexShouldNotBeFound("sleepHealthScore.lessThan=" + DEFAULT_SLEEP_HEALTH_SCORE);

        // Get all the funtionalIndexList where sleepHealthScore is less than UPDATED_SLEEP_HEALTH_SCORE
        defaultFuntionalIndexShouldBeFound("sleepHealthScore.lessThan=" + UPDATED_SLEEP_HEALTH_SCORE);
    }

    @Test
    @Transactional
    void getAllFuntionalIndicesBySleepHealthScoreIsGreaterThanSomething() throws Exception {
        // Initialize the database
        funtionalIndexRepository.saveAndFlush(funtionalIndex);

        // Get all the funtionalIndexList where sleepHealthScore is greater than DEFAULT_SLEEP_HEALTH_SCORE
        defaultFuntionalIndexShouldNotBeFound("sleepHealthScore.greaterThan=" + DEFAULT_SLEEP_HEALTH_SCORE);

        // Get all the funtionalIndexList where sleepHealthScore is greater than SMALLER_SLEEP_HEALTH_SCORE
        defaultFuntionalIndexShouldBeFound("sleepHealthScore.greaterThan=" + SMALLER_SLEEP_HEALTH_SCORE);
    }

    @Test
    @Transactional
    void getAllFuntionalIndicesByFuntionalIndexIsEqualToSomething() throws Exception {
        // Initialize the database
        funtionalIndexRepository.saveAndFlush(funtionalIndex);

        // Get all the funtionalIndexList where funtionalIndex equals to DEFAULT_FUNTIONAL_INDEX
        defaultFuntionalIndexShouldBeFound("funtionalIndex.equals=" + DEFAULT_FUNTIONAL_INDEX);

        // Get all the funtionalIndexList where funtionalIndex equals to UPDATED_FUNTIONAL_INDEX
        defaultFuntionalIndexShouldNotBeFound("funtionalIndex.equals=" + UPDATED_FUNTIONAL_INDEX);
    }

    @Test
    @Transactional
    void getAllFuntionalIndicesByFuntionalIndexIsInShouldWork() throws Exception {
        // Initialize the database
        funtionalIndexRepository.saveAndFlush(funtionalIndex);

        // Get all the funtionalIndexList where funtionalIndex in DEFAULT_FUNTIONAL_INDEX or UPDATED_FUNTIONAL_INDEX
        defaultFuntionalIndexShouldBeFound("funtionalIndex.in=" + DEFAULT_FUNTIONAL_INDEX + "," + UPDATED_FUNTIONAL_INDEX);

        // Get all the funtionalIndexList where funtionalIndex equals to UPDATED_FUNTIONAL_INDEX
        defaultFuntionalIndexShouldNotBeFound("funtionalIndex.in=" + UPDATED_FUNTIONAL_INDEX);
    }

    @Test
    @Transactional
    void getAllFuntionalIndicesByFuntionalIndexIsNullOrNotNull() throws Exception {
        // Initialize the database
        funtionalIndexRepository.saveAndFlush(funtionalIndex);

        // Get all the funtionalIndexList where funtionalIndex is not null
        defaultFuntionalIndexShouldBeFound("funtionalIndex.specified=true");

        // Get all the funtionalIndexList where funtionalIndex is null
        defaultFuntionalIndexShouldNotBeFound("funtionalIndex.specified=false");
    }

    @Test
    @Transactional
    void getAllFuntionalIndicesByFuntionalIndexIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        funtionalIndexRepository.saveAndFlush(funtionalIndex);

        // Get all the funtionalIndexList where funtionalIndex is greater than or equal to DEFAULT_FUNTIONAL_INDEX
        defaultFuntionalIndexShouldBeFound("funtionalIndex.greaterThanOrEqual=" + DEFAULT_FUNTIONAL_INDEX);

        // Get all the funtionalIndexList where funtionalIndex is greater than or equal to UPDATED_FUNTIONAL_INDEX
        defaultFuntionalIndexShouldNotBeFound("funtionalIndex.greaterThanOrEqual=" + UPDATED_FUNTIONAL_INDEX);
    }

    @Test
    @Transactional
    void getAllFuntionalIndicesByFuntionalIndexIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        funtionalIndexRepository.saveAndFlush(funtionalIndex);

        // Get all the funtionalIndexList where funtionalIndex is less than or equal to DEFAULT_FUNTIONAL_INDEX
        defaultFuntionalIndexShouldBeFound("funtionalIndex.lessThanOrEqual=" + DEFAULT_FUNTIONAL_INDEX);

        // Get all the funtionalIndexList where funtionalIndex is less than or equal to SMALLER_FUNTIONAL_INDEX
        defaultFuntionalIndexShouldNotBeFound("funtionalIndex.lessThanOrEqual=" + SMALLER_FUNTIONAL_INDEX);
    }

    @Test
    @Transactional
    void getAllFuntionalIndicesByFuntionalIndexIsLessThanSomething() throws Exception {
        // Initialize the database
        funtionalIndexRepository.saveAndFlush(funtionalIndex);

        // Get all the funtionalIndexList where funtionalIndex is less than DEFAULT_FUNTIONAL_INDEX
        defaultFuntionalIndexShouldNotBeFound("funtionalIndex.lessThan=" + DEFAULT_FUNTIONAL_INDEX);

        // Get all the funtionalIndexList where funtionalIndex is less than UPDATED_FUNTIONAL_INDEX
        defaultFuntionalIndexShouldBeFound("funtionalIndex.lessThan=" + UPDATED_FUNTIONAL_INDEX);
    }

    @Test
    @Transactional
    void getAllFuntionalIndicesByFuntionalIndexIsGreaterThanSomething() throws Exception {
        // Initialize the database
        funtionalIndexRepository.saveAndFlush(funtionalIndex);

        // Get all the funtionalIndexList where funtionalIndex is greater than DEFAULT_FUNTIONAL_INDEX
        defaultFuntionalIndexShouldNotBeFound("funtionalIndex.greaterThan=" + DEFAULT_FUNTIONAL_INDEX);

        // Get all the funtionalIndexList where funtionalIndex is greater than SMALLER_FUNTIONAL_INDEX
        defaultFuntionalIndexShouldBeFound("funtionalIndex.greaterThan=" + SMALLER_FUNTIONAL_INDEX);
    }

    @Test
    @Transactional
    void getAllFuntionalIndicesByAlarmRiskScoreIsEqualToSomething() throws Exception {
        // Initialize the database
        funtionalIndexRepository.saveAndFlush(funtionalIndex);

        // Get all the funtionalIndexList where alarmRiskScore equals to DEFAULT_ALARM_RISK_SCORE
        defaultFuntionalIndexShouldBeFound("alarmRiskScore.equals=" + DEFAULT_ALARM_RISK_SCORE);

        // Get all the funtionalIndexList where alarmRiskScore equals to UPDATED_ALARM_RISK_SCORE
        defaultFuntionalIndexShouldNotBeFound("alarmRiskScore.equals=" + UPDATED_ALARM_RISK_SCORE);
    }

    @Test
    @Transactional
    void getAllFuntionalIndicesByAlarmRiskScoreIsInShouldWork() throws Exception {
        // Initialize the database
        funtionalIndexRepository.saveAndFlush(funtionalIndex);

        // Get all the funtionalIndexList where alarmRiskScore in DEFAULT_ALARM_RISK_SCORE or UPDATED_ALARM_RISK_SCORE
        defaultFuntionalIndexShouldBeFound("alarmRiskScore.in=" + DEFAULT_ALARM_RISK_SCORE + "," + UPDATED_ALARM_RISK_SCORE);

        // Get all the funtionalIndexList where alarmRiskScore equals to UPDATED_ALARM_RISK_SCORE
        defaultFuntionalIndexShouldNotBeFound("alarmRiskScore.in=" + UPDATED_ALARM_RISK_SCORE);
    }

    @Test
    @Transactional
    void getAllFuntionalIndicesByAlarmRiskScoreIsNullOrNotNull() throws Exception {
        // Initialize the database
        funtionalIndexRepository.saveAndFlush(funtionalIndex);

        // Get all the funtionalIndexList where alarmRiskScore is not null
        defaultFuntionalIndexShouldBeFound("alarmRiskScore.specified=true");

        // Get all the funtionalIndexList where alarmRiskScore is null
        defaultFuntionalIndexShouldNotBeFound("alarmRiskScore.specified=false");
    }

    @Test
    @Transactional
    void getAllFuntionalIndicesByAlarmRiskScoreIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        funtionalIndexRepository.saveAndFlush(funtionalIndex);

        // Get all the funtionalIndexList where alarmRiskScore is greater than or equal to DEFAULT_ALARM_RISK_SCORE
        defaultFuntionalIndexShouldBeFound("alarmRiskScore.greaterThanOrEqual=" + DEFAULT_ALARM_RISK_SCORE);

        // Get all the funtionalIndexList where alarmRiskScore is greater than or equal to UPDATED_ALARM_RISK_SCORE
        defaultFuntionalIndexShouldNotBeFound("alarmRiskScore.greaterThanOrEqual=" + UPDATED_ALARM_RISK_SCORE);
    }

    @Test
    @Transactional
    void getAllFuntionalIndicesByAlarmRiskScoreIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        funtionalIndexRepository.saveAndFlush(funtionalIndex);

        // Get all the funtionalIndexList where alarmRiskScore is less than or equal to DEFAULT_ALARM_RISK_SCORE
        defaultFuntionalIndexShouldBeFound("alarmRiskScore.lessThanOrEqual=" + DEFAULT_ALARM_RISK_SCORE);

        // Get all the funtionalIndexList where alarmRiskScore is less than or equal to SMALLER_ALARM_RISK_SCORE
        defaultFuntionalIndexShouldNotBeFound("alarmRiskScore.lessThanOrEqual=" + SMALLER_ALARM_RISK_SCORE);
    }

    @Test
    @Transactional
    void getAllFuntionalIndicesByAlarmRiskScoreIsLessThanSomething() throws Exception {
        // Initialize the database
        funtionalIndexRepository.saveAndFlush(funtionalIndex);

        // Get all the funtionalIndexList where alarmRiskScore is less than DEFAULT_ALARM_RISK_SCORE
        defaultFuntionalIndexShouldNotBeFound("alarmRiskScore.lessThan=" + DEFAULT_ALARM_RISK_SCORE);

        // Get all the funtionalIndexList where alarmRiskScore is less than UPDATED_ALARM_RISK_SCORE
        defaultFuntionalIndexShouldBeFound("alarmRiskScore.lessThan=" + UPDATED_ALARM_RISK_SCORE);
    }

    @Test
    @Transactional
    void getAllFuntionalIndicesByAlarmRiskScoreIsGreaterThanSomething() throws Exception {
        // Initialize the database
        funtionalIndexRepository.saveAndFlush(funtionalIndex);

        // Get all the funtionalIndexList where alarmRiskScore is greater than DEFAULT_ALARM_RISK_SCORE
        defaultFuntionalIndexShouldNotBeFound("alarmRiskScore.greaterThan=" + DEFAULT_ALARM_RISK_SCORE);

        // Get all the funtionalIndexList where alarmRiskScore is greater than SMALLER_ALARM_RISK_SCORE
        defaultFuntionalIndexShouldBeFound("alarmRiskScore.greaterThan=" + SMALLER_ALARM_RISK_SCORE);
    }

    @Test
    @Transactional
    void getAllFuntionalIndicesByStartTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        funtionalIndexRepository.saveAndFlush(funtionalIndex);

        // Get all the funtionalIndexList where startTime equals to DEFAULT_START_TIME
        defaultFuntionalIndexShouldBeFound("startTime.equals=" + DEFAULT_START_TIME);

        // Get all the funtionalIndexList where startTime equals to UPDATED_START_TIME
        defaultFuntionalIndexShouldNotBeFound("startTime.equals=" + UPDATED_START_TIME);
    }

    @Test
    @Transactional
    void getAllFuntionalIndicesByStartTimeIsInShouldWork() throws Exception {
        // Initialize the database
        funtionalIndexRepository.saveAndFlush(funtionalIndex);

        // Get all the funtionalIndexList where startTime in DEFAULT_START_TIME or UPDATED_START_TIME
        defaultFuntionalIndexShouldBeFound("startTime.in=" + DEFAULT_START_TIME + "," + UPDATED_START_TIME);

        // Get all the funtionalIndexList where startTime equals to UPDATED_START_TIME
        defaultFuntionalIndexShouldNotBeFound("startTime.in=" + UPDATED_START_TIME);
    }

    @Test
    @Transactional
    void getAllFuntionalIndicesByStartTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        funtionalIndexRepository.saveAndFlush(funtionalIndex);

        // Get all the funtionalIndexList where startTime is not null
        defaultFuntionalIndexShouldBeFound("startTime.specified=true");

        // Get all the funtionalIndexList where startTime is null
        defaultFuntionalIndexShouldNotBeFound("startTime.specified=false");
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultFuntionalIndexShouldBeFound(String filter) throws Exception {
        restFuntionalIndexMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(funtionalIndex.getId().toString())))
            .andExpect(jsonPath("$.[*].usuarioId").value(hasItem(DEFAULT_USUARIO_ID)))
            .andExpect(jsonPath("$.[*].empresaId").value(hasItem(DEFAULT_EMPRESA_ID)))
            .andExpect(jsonPath("$.[*].bodyHealthScore").value(hasItem(DEFAULT_BODY_HEALTH_SCORE)))
            .andExpect(jsonPath("$.[*].mentalHealthScore").value(hasItem(DEFAULT_MENTAL_HEALTH_SCORE)))
            .andExpect(jsonPath("$.[*].sleepHealthScore").value(hasItem(DEFAULT_SLEEP_HEALTH_SCORE)))
            .andExpect(jsonPath("$.[*].funtionalIndex").value(hasItem(DEFAULT_FUNTIONAL_INDEX)))
            .andExpect(jsonPath("$.[*].alarmRiskScore").value(hasItem(DEFAULT_ALARM_RISK_SCORE)))
            .andExpect(jsonPath("$.[*].startTime").value(hasItem(DEFAULT_START_TIME.toString())));

        // Check, that the count call also returns 1
        restFuntionalIndexMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultFuntionalIndexShouldNotBeFound(String filter) throws Exception {
        restFuntionalIndexMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restFuntionalIndexMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingFuntionalIndex() throws Exception {
        // Get the funtionalIndex
        restFuntionalIndexMockMvc.perform(get(ENTITY_API_URL_ID, UUID.randomUUID().toString())).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingFuntionalIndex() throws Exception {
        // Initialize the database
        funtionalIndexRepository.saveAndFlush(funtionalIndex);

        int databaseSizeBeforeUpdate = funtionalIndexRepository.findAll().size();

        // Update the funtionalIndex
        FuntionalIndex updatedFuntionalIndex = funtionalIndexRepository.findById(funtionalIndex.getId()).get();
        // Disconnect from session so that the updates on updatedFuntionalIndex are not directly saved in db
        em.detach(updatedFuntionalIndex);
        updatedFuntionalIndex
            .usuarioId(UPDATED_USUARIO_ID)
            .empresaId(UPDATED_EMPRESA_ID)
            .bodyHealthScore(UPDATED_BODY_HEALTH_SCORE)
            .mentalHealthScore(UPDATED_MENTAL_HEALTH_SCORE)
            .sleepHealthScore(UPDATED_SLEEP_HEALTH_SCORE)
            .funtionalIndex(UPDATED_FUNTIONAL_INDEX)
            .alarmRiskScore(UPDATED_ALARM_RISK_SCORE)
            .startTime(UPDATED_START_TIME);

        restFuntionalIndexMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedFuntionalIndex.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedFuntionalIndex))
            )
            .andExpect(status().isOk());

        // Validate the FuntionalIndex in the database
        List<FuntionalIndex> funtionalIndexList = funtionalIndexRepository.findAll();
        assertThat(funtionalIndexList).hasSize(databaseSizeBeforeUpdate);
        FuntionalIndex testFuntionalIndex = funtionalIndexList.get(funtionalIndexList.size() - 1);
        assertThat(testFuntionalIndex.getUsuarioId()).isEqualTo(UPDATED_USUARIO_ID);
        assertThat(testFuntionalIndex.getEmpresaId()).isEqualTo(UPDATED_EMPRESA_ID);
        assertThat(testFuntionalIndex.getBodyHealthScore()).isEqualTo(UPDATED_BODY_HEALTH_SCORE);
        assertThat(testFuntionalIndex.getMentalHealthScore()).isEqualTo(UPDATED_MENTAL_HEALTH_SCORE);
        assertThat(testFuntionalIndex.getSleepHealthScore()).isEqualTo(UPDATED_SLEEP_HEALTH_SCORE);
        assertThat(testFuntionalIndex.getFuntionalIndex()).isEqualTo(UPDATED_FUNTIONAL_INDEX);
        assertThat(testFuntionalIndex.getAlarmRiskScore()).isEqualTo(UPDATED_ALARM_RISK_SCORE);
        assertThat(testFuntionalIndex.getStartTime()).isEqualTo(UPDATED_START_TIME);
    }

    @Test
    @Transactional
    void putNonExistingFuntionalIndex() throws Exception {
        int databaseSizeBeforeUpdate = funtionalIndexRepository.findAll().size();
        funtionalIndex.setId(UUID.randomUUID());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFuntionalIndexMockMvc
            .perform(
                put(ENTITY_API_URL_ID, funtionalIndex.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(funtionalIndex))
            )
            .andExpect(status().isBadRequest());

        // Validate the FuntionalIndex in the database
        List<FuntionalIndex> funtionalIndexList = funtionalIndexRepository.findAll();
        assertThat(funtionalIndexList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchFuntionalIndex() throws Exception {
        int databaseSizeBeforeUpdate = funtionalIndexRepository.findAll().size();
        funtionalIndex.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFuntionalIndexMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(funtionalIndex))
            )
            .andExpect(status().isBadRequest());

        // Validate the FuntionalIndex in the database
        List<FuntionalIndex> funtionalIndexList = funtionalIndexRepository.findAll();
        assertThat(funtionalIndexList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamFuntionalIndex() throws Exception {
        int databaseSizeBeforeUpdate = funtionalIndexRepository.findAll().size();
        funtionalIndex.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFuntionalIndexMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(funtionalIndex)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the FuntionalIndex in the database
        List<FuntionalIndex> funtionalIndexList = funtionalIndexRepository.findAll();
        assertThat(funtionalIndexList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateFuntionalIndexWithPatch() throws Exception {
        // Initialize the database
        funtionalIndexRepository.saveAndFlush(funtionalIndex);

        int databaseSizeBeforeUpdate = funtionalIndexRepository.findAll().size();

        // Update the funtionalIndex using partial update
        FuntionalIndex partialUpdatedFuntionalIndex = new FuntionalIndex();
        partialUpdatedFuntionalIndex.setId(funtionalIndex.getId());

        partialUpdatedFuntionalIndex
            .usuarioId(UPDATED_USUARIO_ID)
            .bodyHealthScore(UPDATED_BODY_HEALTH_SCORE)
            .sleepHealthScore(UPDATED_SLEEP_HEALTH_SCORE)
            .funtionalIndex(UPDATED_FUNTIONAL_INDEX)
            .alarmRiskScore(UPDATED_ALARM_RISK_SCORE);

        restFuntionalIndexMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFuntionalIndex.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFuntionalIndex))
            )
            .andExpect(status().isOk());

        // Validate the FuntionalIndex in the database
        List<FuntionalIndex> funtionalIndexList = funtionalIndexRepository.findAll();
        assertThat(funtionalIndexList).hasSize(databaseSizeBeforeUpdate);
        FuntionalIndex testFuntionalIndex = funtionalIndexList.get(funtionalIndexList.size() - 1);
        assertThat(testFuntionalIndex.getUsuarioId()).isEqualTo(UPDATED_USUARIO_ID);
        assertThat(testFuntionalIndex.getEmpresaId()).isEqualTo(DEFAULT_EMPRESA_ID);
        assertThat(testFuntionalIndex.getBodyHealthScore()).isEqualTo(UPDATED_BODY_HEALTH_SCORE);
        assertThat(testFuntionalIndex.getMentalHealthScore()).isEqualTo(DEFAULT_MENTAL_HEALTH_SCORE);
        assertThat(testFuntionalIndex.getSleepHealthScore()).isEqualTo(UPDATED_SLEEP_HEALTH_SCORE);
        assertThat(testFuntionalIndex.getFuntionalIndex()).isEqualTo(UPDATED_FUNTIONAL_INDEX);
        assertThat(testFuntionalIndex.getAlarmRiskScore()).isEqualTo(UPDATED_ALARM_RISK_SCORE);
        assertThat(testFuntionalIndex.getStartTime()).isEqualTo(DEFAULT_START_TIME);
    }

    @Test
    @Transactional
    void fullUpdateFuntionalIndexWithPatch() throws Exception {
        // Initialize the database
        funtionalIndexRepository.saveAndFlush(funtionalIndex);

        int databaseSizeBeforeUpdate = funtionalIndexRepository.findAll().size();

        // Update the funtionalIndex using partial update
        FuntionalIndex partialUpdatedFuntionalIndex = new FuntionalIndex();
        partialUpdatedFuntionalIndex.setId(funtionalIndex.getId());

        partialUpdatedFuntionalIndex
            .usuarioId(UPDATED_USUARIO_ID)
            .empresaId(UPDATED_EMPRESA_ID)
            .bodyHealthScore(UPDATED_BODY_HEALTH_SCORE)
            .mentalHealthScore(UPDATED_MENTAL_HEALTH_SCORE)
            .sleepHealthScore(UPDATED_SLEEP_HEALTH_SCORE)
            .funtionalIndex(UPDATED_FUNTIONAL_INDEX)
            .alarmRiskScore(UPDATED_ALARM_RISK_SCORE)
            .startTime(UPDATED_START_TIME);

        restFuntionalIndexMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFuntionalIndex.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFuntionalIndex))
            )
            .andExpect(status().isOk());

        // Validate the FuntionalIndex in the database
        List<FuntionalIndex> funtionalIndexList = funtionalIndexRepository.findAll();
        assertThat(funtionalIndexList).hasSize(databaseSizeBeforeUpdate);
        FuntionalIndex testFuntionalIndex = funtionalIndexList.get(funtionalIndexList.size() - 1);
        assertThat(testFuntionalIndex.getUsuarioId()).isEqualTo(UPDATED_USUARIO_ID);
        assertThat(testFuntionalIndex.getEmpresaId()).isEqualTo(UPDATED_EMPRESA_ID);
        assertThat(testFuntionalIndex.getBodyHealthScore()).isEqualTo(UPDATED_BODY_HEALTH_SCORE);
        assertThat(testFuntionalIndex.getMentalHealthScore()).isEqualTo(UPDATED_MENTAL_HEALTH_SCORE);
        assertThat(testFuntionalIndex.getSleepHealthScore()).isEqualTo(UPDATED_SLEEP_HEALTH_SCORE);
        assertThat(testFuntionalIndex.getFuntionalIndex()).isEqualTo(UPDATED_FUNTIONAL_INDEX);
        assertThat(testFuntionalIndex.getAlarmRiskScore()).isEqualTo(UPDATED_ALARM_RISK_SCORE);
        assertThat(testFuntionalIndex.getStartTime()).isEqualTo(UPDATED_START_TIME);
    }

    @Test
    @Transactional
    void patchNonExistingFuntionalIndex() throws Exception {
        int databaseSizeBeforeUpdate = funtionalIndexRepository.findAll().size();
        funtionalIndex.setId(UUID.randomUUID());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFuntionalIndexMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, funtionalIndex.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(funtionalIndex))
            )
            .andExpect(status().isBadRequest());

        // Validate the FuntionalIndex in the database
        List<FuntionalIndex> funtionalIndexList = funtionalIndexRepository.findAll();
        assertThat(funtionalIndexList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchFuntionalIndex() throws Exception {
        int databaseSizeBeforeUpdate = funtionalIndexRepository.findAll().size();
        funtionalIndex.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFuntionalIndexMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(funtionalIndex))
            )
            .andExpect(status().isBadRequest());

        // Validate the FuntionalIndex in the database
        List<FuntionalIndex> funtionalIndexList = funtionalIndexRepository.findAll();
        assertThat(funtionalIndexList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamFuntionalIndex() throws Exception {
        int databaseSizeBeforeUpdate = funtionalIndexRepository.findAll().size();
        funtionalIndex.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFuntionalIndexMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(funtionalIndex))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the FuntionalIndex in the database
        List<FuntionalIndex> funtionalIndexList = funtionalIndexRepository.findAll();
        assertThat(funtionalIndexList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteFuntionalIndex() throws Exception {
        // Initialize the database
        funtionalIndexRepository.saveAndFlush(funtionalIndex);

        int databaseSizeBeforeDelete = funtionalIndexRepository.findAll().size();

        // Delete the funtionalIndex
        restFuntionalIndexMockMvc
            .perform(delete(ENTITY_API_URL_ID, funtionalIndex.getId().toString()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<FuntionalIndex> funtionalIndexList = funtionalIndexRepository.findAll();
        assertThat(funtionalIndexList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
