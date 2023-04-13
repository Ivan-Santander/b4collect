package com.be4tech.b4carecollect.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.be4tech.b4carecollect.IntegrationTest;
import com.be4tech.b4carecollect.domain.SleepScores;
import com.be4tech.b4carecollect.repository.SleepScoresRepository;
import com.be4tech.b4carecollect.service.criteria.SleepScoresCriteria;
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
 * Integration tests for the {@link SleepScoresResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SleepScoresResourceIT {

    private static final String DEFAULT_USUARIO_ID = "AAAAAAAAAA";
    private static final String UPDATED_USUARIO_ID = "BBBBBBBBBB";

    private static final String DEFAULT_EMPRESA_ID = "AAAAAAAAAA";
    private static final String UPDATED_EMPRESA_ID = "BBBBBBBBBB";

    private static final Integer DEFAULT_SLEEP_QUALITY_RATING_SCORE = 1;
    private static final Integer UPDATED_SLEEP_QUALITY_RATING_SCORE = 2;
    private static final Integer SMALLER_SLEEP_QUALITY_RATING_SCORE = 1 - 1;

    private static final Integer DEFAULT_SLEEP_EFFICIENCY_SCORE = 1;
    private static final Integer UPDATED_SLEEP_EFFICIENCY_SCORE = 2;
    private static final Integer SMALLER_SLEEP_EFFICIENCY_SCORE = 1 - 1;

    private static final Integer DEFAULT_SLEEP_GOOAL_SECONDS_SCORE = 1;
    private static final Integer UPDATED_SLEEP_GOOAL_SECONDS_SCORE = 2;
    private static final Integer SMALLER_SLEEP_GOOAL_SECONDS_SCORE = 1 - 1;

    private static final Integer DEFAULT_SLEEP_CONTINUITY_SCORE = 1;
    private static final Integer UPDATED_SLEEP_CONTINUITY_SCORE = 2;
    private static final Integer SMALLER_SLEEP_CONTINUITY_SCORE = 1 - 1;

    private static final Integer DEFAULT_SLEEP_CONTINUITY_RATING = 1;
    private static final Integer UPDATED_SLEEP_CONTINUITY_RATING = 2;
    private static final Integer SMALLER_SLEEP_CONTINUITY_RATING = 1 - 1;

    private static final String ENTITY_API_URL = "/api/sleep-scores";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private SleepScoresRepository sleepScoresRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSleepScoresMockMvc;

    private SleepScores sleepScores;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SleepScores createEntity(EntityManager em) {
        SleepScores sleepScores = new SleepScores()
            .usuarioId(DEFAULT_USUARIO_ID)
            .empresaId(DEFAULT_EMPRESA_ID)
            .sleepQualityRatingScore(DEFAULT_SLEEP_QUALITY_RATING_SCORE)
            .sleepEfficiencyScore(DEFAULT_SLEEP_EFFICIENCY_SCORE)
            .sleepGooalSecondsScore(DEFAULT_SLEEP_GOOAL_SECONDS_SCORE)
            .sleepContinuityScore(DEFAULT_SLEEP_CONTINUITY_SCORE)
            .sleepContinuityRating(DEFAULT_SLEEP_CONTINUITY_RATING);
        return sleepScores;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SleepScores createUpdatedEntity(EntityManager em) {
        SleepScores sleepScores = new SleepScores()
            .usuarioId(UPDATED_USUARIO_ID)
            .empresaId(UPDATED_EMPRESA_ID)
            .sleepQualityRatingScore(UPDATED_SLEEP_QUALITY_RATING_SCORE)
            .sleepEfficiencyScore(UPDATED_SLEEP_EFFICIENCY_SCORE)
            .sleepGooalSecondsScore(UPDATED_SLEEP_GOOAL_SECONDS_SCORE)
            .sleepContinuityScore(UPDATED_SLEEP_CONTINUITY_SCORE)
            .sleepContinuityRating(UPDATED_SLEEP_CONTINUITY_RATING);
        return sleepScores;
    }

    @BeforeEach
    public void initTest() {
        sleepScores = createEntity(em);
    }

    @Test
    @Transactional
    void createSleepScores() throws Exception {
        int databaseSizeBeforeCreate = sleepScoresRepository.findAll().size();
        // Create the SleepScores
        restSleepScoresMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(sleepScores)))
            .andExpect(status().isCreated());

        // Validate the SleepScores in the database
        List<SleepScores> sleepScoresList = sleepScoresRepository.findAll();
        assertThat(sleepScoresList).hasSize(databaseSizeBeforeCreate + 1);
        SleepScores testSleepScores = sleepScoresList.get(sleepScoresList.size() - 1);
        assertThat(testSleepScores.getUsuarioId()).isEqualTo(DEFAULT_USUARIO_ID);
        assertThat(testSleepScores.getEmpresaId()).isEqualTo(DEFAULT_EMPRESA_ID);
        assertThat(testSleepScores.getSleepQualityRatingScore()).isEqualTo(DEFAULT_SLEEP_QUALITY_RATING_SCORE);
        assertThat(testSleepScores.getSleepEfficiencyScore()).isEqualTo(DEFAULT_SLEEP_EFFICIENCY_SCORE);
        assertThat(testSleepScores.getSleepGooalSecondsScore()).isEqualTo(DEFAULT_SLEEP_GOOAL_SECONDS_SCORE);
        assertThat(testSleepScores.getSleepContinuityScore()).isEqualTo(DEFAULT_SLEEP_CONTINUITY_SCORE);
        assertThat(testSleepScores.getSleepContinuityRating()).isEqualTo(DEFAULT_SLEEP_CONTINUITY_RATING);
    }

    @Test
    @Transactional
    void createSleepScoresWithExistingId() throws Exception {
        // Create the SleepScores with an existing ID
        sleepScoresRepository.saveAndFlush(sleepScores);

        int databaseSizeBeforeCreate = sleepScoresRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSleepScoresMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(sleepScores)))
            .andExpect(status().isBadRequest());

        // Validate the SleepScores in the database
        List<SleepScores> sleepScoresList = sleepScoresRepository.findAll();
        assertThat(sleepScoresList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllSleepScores() throws Exception {
        // Initialize the database
        sleepScoresRepository.saveAndFlush(sleepScores);

        // Get all the sleepScoresList
        restSleepScoresMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sleepScores.getId().toString())))
            .andExpect(jsonPath("$.[*].usuarioId").value(hasItem(DEFAULT_USUARIO_ID)))
            .andExpect(jsonPath("$.[*].empresaId").value(hasItem(DEFAULT_EMPRESA_ID)))
            .andExpect(jsonPath("$.[*].sleepQualityRatingScore").value(hasItem(DEFAULT_SLEEP_QUALITY_RATING_SCORE)))
            .andExpect(jsonPath("$.[*].sleepEfficiencyScore").value(hasItem(DEFAULT_SLEEP_EFFICIENCY_SCORE)))
            .andExpect(jsonPath("$.[*].sleepGooalSecondsScore").value(hasItem(DEFAULT_SLEEP_GOOAL_SECONDS_SCORE)))
            .andExpect(jsonPath("$.[*].sleepContinuityScore").value(hasItem(DEFAULT_SLEEP_CONTINUITY_SCORE)))
            .andExpect(jsonPath("$.[*].sleepContinuityRating").value(hasItem(DEFAULT_SLEEP_CONTINUITY_RATING)));
    }

    @Test
    @Transactional
    void getSleepScores() throws Exception {
        // Initialize the database
        sleepScoresRepository.saveAndFlush(sleepScores);

        // Get the sleepScores
        restSleepScoresMockMvc
            .perform(get(ENTITY_API_URL_ID, sleepScores.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(sleepScores.getId().toString()))
            .andExpect(jsonPath("$.usuarioId").value(DEFAULT_USUARIO_ID))
            .andExpect(jsonPath("$.empresaId").value(DEFAULT_EMPRESA_ID))
            .andExpect(jsonPath("$.sleepQualityRatingScore").value(DEFAULT_SLEEP_QUALITY_RATING_SCORE))
            .andExpect(jsonPath("$.sleepEfficiencyScore").value(DEFAULT_SLEEP_EFFICIENCY_SCORE))
            .andExpect(jsonPath("$.sleepGooalSecondsScore").value(DEFAULT_SLEEP_GOOAL_SECONDS_SCORE))
            .andExpect(jsonPath("$.sleepContinuityScore").value(DEFAULT_SLEEP_CONTINUITY_SCORE))
            .andExpect(jsonPath("$.sleepContinuityRating").value(DEFAULT_SLEEP_CONTINUITY_RATING));
    }

    @Test
    @Transactional
    void getSleepScoresByIdFiltering() throws Exception {
        // Initialize the database
        sleepScoresRepository.saveAndFlush(sleepScores);

        UUID id = sleepScores.getId();

        defaultSleepScoresShouldBeFound("id.equals=" + id);
        defaultSleepScoresShouldNotBeFound("id.notEquals=" + id);
    }

    @Test
    @Transactional
    void getAllSleepScoresByUsuarioIdIsEqualToSomething() throws Exception {
        // Initialize the database
        sleepScoresRepository.saveAndFlush(sleepScores);

        // Get all the sleepScoresList where usuarioId equals to DEFAULT_USUARIO_ID
        defaultSleepScoresShouldBeFound("usuarioId.equals=" + DEFAULT_USUARIO_ID);

        // Get all the sleepScoresList where usuarioId equals to UPDATED_USUARIO_ID
        defaultSleepScoresShouldNotBeFound("usuarioId.equals=" + UPDATED_USUARIO_ID);
    }

    @Test
    @Transactional
    void getAllSleepScoresByUsuarioIdIsInShouldWork() throws Exception {
        // Initialize the database
        sleepScoresRepository.saveAndFlush(sleepScores);

        // Get all the sleepScoresList where usuarioId in DEFAULT_USUARIO_ID or UPDATED_USUARIO_ID
        defaultSleepScoresShouldBeFound("usuarioId.in=" + DEFAULT_USUARIO_ID + "," + UPDATED_USUARIO_ID);

        // Get all the sleepScoresList where usuarioId equals to UPDATED_USUARIO_ID
        defaultSleepScoresShouldNotBeFound("usuarioId.in=" + UPDATED_USUARIO_ID);
    }

    @Test
    @Transactional
    void getAllSleepScoresByUsuarioIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        sleepScoresRepository.saveAndFlush(sleepScores);

        // Get all the sleepScoresList where usuarioId is not null
        defaultSleepScoresShouldBeFound("usuarioId.specified=true");

        // Get all the sleepScoresList where usuarioId is null
        defaultSleepScoresShouldNotBeFound("usuarioId.specified=false");
    }

    @Test
    @Transactional
    void getAllSleepScoresByUsuarioIdContainsSomething() throws Exception {
        // Initialize the database
        sleepScoresRepository.saveAndFlush(sleepScores);

        // Get all the sleepScoresList where usuarioId contains DEFAULT_USUARIO_ID
        defaultSleepScoresShouldBeFound("usuarioId.contains=" + DEFAULT_USUARIO_ID);

        // Get all the sleepScoresList where usuarioId contains UPDATED_USUARIO_ID
        defaultSleepScoresShouldNotBeFound("usuarioId.contains=" + UPDATED_USUARIO_ID);
    }

    @Test
    @Transactional
    void getAllSleepScoresByUsuarioIdNotContainsSomething() throws Exception {
        // Initialize the database
        sleepScoresRepository.saveAndFlush(sleepScores);

        // Get all the sleepScoresList where usuarioId does not contain DEFAULT_USUARIO_ID
        defaultSleepScoresShouldNotBeFound("usuarioId.doesNotContain=" + DEFAULT_USUARIO_ID);

        // Get all the sleepScoresList where usuarioId does not contain UPDATED_USUARIO_ID
        defaultSleepScoresShouldBeFound("usuarioId.doesNotContain=" + UPDATED_USUARIO_ID);
    }

    @Test
    @Transactional
    void getAllSleepScoresByEmpresaIdIsEqualToSomething() throws Exception {
        // Initialize the database
        sleepScoresRepository.saveAndFlush(sleepScores);

        // Get all the sleepScoresList where empresaId equals to DEFAULT_EMPRESA_ID
        defaultSleepScoresShouldBeFound("empresaId.equals=" + DEFAULT_EMPRESA_ID);

        // Get all the sleepScoresList where empresaId equals to UPDATED_EMPRESA_ID
        defaultSleepScoresShouldNotBeFound("empresaId.equals=" + UPDATED_EMPRESA_ID);
    }

    @Test
    @Transactional
    void getAllSleepScoresByEmpresaIdIsInShouldWork() throws Exception {
        // Initialize the database
        sleepScoresRepository.saveAndFlush(sleepScores);

        // Get all the sleepScoresList where empresaId in DEFAULT_EMPRESA_ID or UPDATED_EMPRESA_ID
        defaultSleepScoresShouldBeFound("empresaId.in=" + DEFAULT_EMPRESA_ID + "," + UPDATED_EMPRESA_ID);

        // Get all the sleepScoresList where empresaId equals to UPDATED_EMPRESA_ID
        defaultSleepScoresShouldNotBeFound("empresaId.in=" + UPDATED_EMPRESA_ID);
    }

    @Test
    @Transactional
    void getAllSleepScoresByEmpresaIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        sleepScoresRepository.saveAndFlush(sleepScores);

        // Get all the sleepScoresList where empresaId is not null
        defaultSleepScoresShouldBeFound("empresaId.specified=true");

        // Get all the sleepScoresList where empresaId is null
        defaultSleepScoresShouldNotBeFound("empresaId.specified=false");
    }

    @Test
    @Transactional
    void getAllSleepScoresByEmpresaIdContainsSomething() throws Exception {
        // Initialize the database
        sleepScoresRepository.saveAndFlush(sleepScores);

        // Get all the sleepScoresList where empresaId contains DEFAULT_EMPRESA_ID
        defaultSleepScoresShouldBeFound("empresaId.contains=" + DEFAULT_EMPRESA_ID);

        // Get all the sleepScoresList where empresaId contains UPDATED_EMPRESA_ID
        defaultSleepScoresShouldNotBeFound("empresaId.contains=" + UPDATED_EMPRESA_ID);
    }

    @Test
    @Transactional
    void getAllSleepScoresByEmpresaIdNotContainsSomething() throws Exception {
        // Initialize the database
        sleepScoresRepository.saveAndFlush(sleepScores);

        // Get all the sleepScoresList where empresaId does not contain DEFAULT_EMPRESA_ID
        defaultSleepScoresShouldNotBeFound("empresaId.doesNotContain=" + DEFAULT_EMPRESA_ID);

        // Get all the sleepScoresList where empresaId does not contain UPDATED_EMPRESA_ID
        defaultSleepScoresShouldBeFound("empresaId.doesNotContain=" + UPDATED_EMPRESA_ID);
    }

    @Test
    @Transactional
    void getAllSleepScoresBySleepQualityRatingScoreIsEqualToSomething() throws Exception {
        // Initialize the database
        sleepScoresRepository.saveAndFlush(sleepScores);

        // Get all the sleepScoresList where sleepQualityRatingScore equals to DEFAULT_SLEEP_QUALITY_RATING_SCORE
        defaultSleepScoresShouldBeFound("sleepQualityRatingScore.equals=" + DEFAULT_SLEEP_QUALITY_RATING_SCORE);

        // Get all the sleepScoresList where sleepQualityRatingScore equals to UPDATED_SLEEP_QUALITY_RATING_SCORE
        defaultSleepScoresShouldNotBeFound("sleepQualityRatingScore.equals=" + UPDATED_SLEEP_QUALITY_RATING_SCORE);
    }

    @Test
    @Transactional
    void getAllSleepScoresBySleepQualityRatingScoreIsInShouldWork() throws Exception {
        // Initialize the database
        sleepScoresRepository.saveAndFlush(sleepScores);

        // Get all the sleepScoresList where sleepQualityRatingScore in DEFAULT_SLEEP_QUALITY_RATING_SCORE or UPDATED_SLEEP_QUALITY_RATING_SCORE
        defaultSleepScoresShouldBeFound(
            "sleepQualityRatingScore.in=" + DEFAULT_SLEEP_QUALITY_RATING_SCORE + "," + UPDATED_SLEEP_QUALITY_RATING_SCORE
        );

        // Get all the sleepScoresList where sleepQualityRatingScore equals to UPDATED_SLEEP_QUALITY_RATING_SCORE
        defaultSleepScoresShouldNotBeFound("sleepQualityRatingScore.in=" + UPDATED_SLEEP_QUALITY_RATING_SCORE);
    }

    @Test
    @Transactional
    void getAllSleepScoresBySleepQualityRatingScoreIsNullOrNotNull() throws Exception {
        // Initialize the database
        sleepScoresRepository.saveAndFlush(sleepScores);

        // Get all the sleepScoresList where sleepQualityRatingScore is not null
        defaultSleepScoresShouldBeFound("sleepQualityRatingScore.specified=true");

        // Get all the sleepScoresList where sleepQualityRatingScore is null
        defaultSleepScoresShouldNotBeFound("sleepQualityRatingScore.specified=false");
    }

    @Test
    @Transactional
    void getAllSleepScoresBySleepQualityRatingScoreIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        sleepScoresRepository.saveAndFlush(sleepScores);

        // Get all the sleepScoresList where sleepQualityRatingScore is greater than or equal to DEFAULT_SLEEP_QUALITY_RATING_SCORE
        defaultSleepScoresShouldBeFound("sleepQualityRatingScore.greaterThanOrEqual=" + DEFAULT_SLEEP_QUALITY_RATING_SCORE);

        // Get all the sleepScoresList where sleepQualityRatingScore is greater than or equal to UPDATED_SLEEP_QUALITY_RATING_SCORE
        defaultSleepScoresShouldNotBeFound("sleepQualityRatingScore.greaterThanOrEqual=" + UPDATED_SLEEP_QUALITY_RATING_SCORE);
    }

    @Test
    @Transactional
    void getAllSleepScoresBySleepQualityRatingScoreIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        sleepScoresRepository.saveAndFlush(sleepScores);

        // Get all the sleepScoresList where sleepQualityRatingScore is less than or equal to DEFAULT_SLEEP_QUALITY_RATING_SCORE
        defaultSleepScoresShouldBeFound("sleepQualityRatingScore.lessThanOrEqual=" + DEFAULT_SLEEP_QUALITY_RATING_SCORE);

        // Get all the sleepScoresList where sleepQualityRatingScore is less than or equal to SMALLER_SLEEP_QUALITY_RATING_SCORE
        defaultSleepScoresShouldNotBeFound("sleepQualityRatingScore.lessThanOrEqual=" + SMALLER_SLEEP_QUALITY_RATING_SCORE);
    }

    @Test
    @Transactional
    void getAllSleepScoresBySleepQualityRatingScoreIsLessThanSomething() throws Exception {
        // Initialize the database
        sleepScoresRepository.saveAndFlush(sleepScores);

        // Get all the sleepScoresList where sleepQualityRatingScore is less than DEFAULT_SLEEP_QUALITY_RATING_SCORE
        defaultSleepScoresShouldNotBeFound("sleepQualityRatingScore.lessThan=" + DEFAULT_SLEEP_QUALITY_RATING_SCORE);

        // Get all the sleepScoresList where sleepQualityRatingScore is less than UPDATED_SLEEP_QUALITY_RATING_SCORE
        defaultSleepScoresShouldBeFound("sleepQualityRatingScore.lessThan=" + UPDATED_SLEEP_QUALITY_RATING_SCORE);
    }

    @Test
    @Transactional
    void getAllSleepScoresBySleepQualityRatingScoreIsGreaterThanSomething() throws Exception {
        // Initialize the database
        sleepScoresRepository.saveAndFlush(sleepScores);

        // Get all the sleepScoresList where sleepQualityRatingScore is greater than DEFAULT_SLEEP_QUALITY_RATING_SCORE
        defaultSleepScoresShouldNotBeFound("sleepQualityRatingScore.greaterThan=" + DEFAULT_SLEEP_QUALITY_RATING_SCORE);

        // Get all the sleepScoresList where sleepQualityRatingScore is greater than SMALLER_SLEEP_QUALITY_RATING_SCORE
        defaultSleepScoresShouldBeFound("sleepQualityRatingScore.greaterThan=" + SMALLER_SLEEP_QUALITY_RATING_SCORE);
    }

    @Test
    @Transactional
    void getAllSleepScoresBySleepEfficiencyScoreIsEqualToSomething() throws Exception {
        // Initialize the database
        sleepScoresRepository.saveAndFlush(sleepScores);

        // Get all the sleepScoresList where sleepEfficiencyScore equals to DEFAULT_SLEEP_EFFICIENCY_SCORE
        defaultSleepScoresShouldBeFound("sleepEfficiencyScore.equals=" + DEFAULT_SLEEP_EFFICIENCY_SCORE);

        // Get all the sleepScoresList where sleepEfficiencyScore equals to UPDATED_SLEEP_EFFICIENCY_SCORE
        defaultSleepScoresShouldNotBeFound("sleepEfficiencyScore.equals=" + UPDATED_SLEEP_EFFICIENCY_SCORE);
    }

    @Test
    @Transactional
    void getAllSleepScoresBySleepEfficiencyScoreIsInShouldWork() throws Exception {
        // Initialize the database
        sleepScoresRepository.saveAndFlush(sleepScores);

        // Get all the sleepScoresList where sleepEfficiencyScore in DEFAULT_SLEEP_EFFICIENCY_SCORE or UPDATED_SLEEP_EFFICIENCY_SCORE
        defaultSleepScoresShouldBeFound("sleepEfficiencyScore.in=" + DEFAULT_SLEEP_EFFICIENCY_SCORE + "," + UPDATED_SLEEP_EFFICIENCY_SCORE);

        // Get all the sleepScoresList where sleepEfficiencyScore equals to UPDATED_SLEEP_EFFICIENCY_SCORE
        defaultSleepScoresShouldNotBeFound("sleepEfficiencyScore.in=" + UPDATED_SLEEP_EFFICIENCY_SCORE);
    }

    @Test
    @Transactional
    void getAllSleepScoresBySleepEfficiencyScoreIsNullOrNotNull() throws Exception {
        // Initialize the database
        sleepScoresRepository.saveAndFlush(sleepScores);

        // Get all the sleepScoresList where sleepEfficiencyScore is not null
        defaultSleepScoresShouldBeFound("sleepEfficiencyScore.specified=true");

        // Get all the sleepScoresList where sleepEfficiencyScore is null
        defaultSleepScoresShouldNotBeFound("sleepEfficiencyScore.specified=false");
    }

    @Test
    @Transactional
    void getAllSleepScoresBySleepEfficiencyScoreIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        sleepScoresRepository.saveAndFlush(sleepScores);

        // Get all the sleepScoresList where sleepEfficiencyScore is greater than or equal to DEFAULT_SLEEP_EFFICIENCY_SCORE
        defaultSleepScoresShouldBeFound("sleepEfficiencyScore.greaterThanOrEqual=" + DEFAULT_SLEEP_EFFICIENCY_SCORE);

        // Get all the sleepScoresList where sleepEfficiencyScore is greater than or equal to UPDATED_SLEEP_EFFICIENCY_SCORE
        defaultSleepScoresShouldNotBeFound("sleepEfficiencyScore.greaterThanOrEqual=" + UPDATED_SLEEP_EFFICIENCY_SCORE);
    }

    @Test
    @Transactional
    void getAllSleepScoresBySleepEfficiencyScoreIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        sleepScoresRepository.saveAndFlush(sleepScores);

        // Get all the sleepScoresList where sleepEfficiencyScore is less than or equal to DEFAULT_SLEEP_EFFICIENCY_SCORE
        defaultSleepScoresShouldBeFound("sleepEfficiencyScore.lessThanOrEqual=" + DEFAULT_SLEEP_EFFICIENCY_SCORE);

        // Get all the sleepScoresList where sleepEfficiencyScore is less than or equal to SMALLER_SLEEP_EFFICIENCY_SCORE
        defaultSleepScoresShouldNotBeFound("sleepEfficiencyScore.lessThanOrEqual=" + SMALLER_SLEEP_EFFICIENCY_SCORE);
    }

    @Test
    @Transactional
    void getAllSleepScoresBySleepEfficiencyScoreIsLessThanSomething() throws Exception {
        // Initialize the database
        sleepScoresRepository.saveAndFlush(sleepScores);

        // Get all the sleepScoresList where sleepEfficiencyScore is less than DEFAULT_SLEEP_EFFICIENCY_SCORE
        defaultSleepScoresShouldNotBeFound("sleepEfficiencyScore.lessThan=" + DEFAULT_SLEEP_EFFICIENCY_SCORE);

        // Get all the sleepScoresList where sleepEfficiencyScore is less than UPDATED_SLEEP_EFFICIENCY_SCORE
        defaultSleepScoresShouldBeFound("sleepEfficiencyScore.lessThan=" + UPDATED_SLEEP_EFFICIENCY_SCORE);
    }

    @Test
    @Transactional
    void getAllSleepScoresBySleepEfficiencyScoreIsGreaterThanSomething() throws Exception {
        // Initialize the database
        sleepScoresRepository.saveAndFlush(sleepScores);

        // Get all the sleepScoresList where sleepEfficiencyScore is greater than DEFAULT_SLEEP_EFFICIENCY_SCORE
        defaultSleepScoresShouldNotBeFound("sleepEfficiencyScore.greaterThan=" + DEFAULT_SLEEP_EFFICIENCY_SCORE);

        // Get all the sleepScoresList where sleepEfficiencyScore is greater than SMALLER_SLEEP_EFFICIENCY_SCORE
        defaultSleepScoresShouldBeFound("sleepEfficiencyScore.greaterThan=" + SMALLER_SLEEP_EFFICIENCY_SCORE);
    }

    @Test
    @Transactional
    void getAllSleepScoresBySleepGooalSecondsScoreIsEqualToSomething() throws Exception {
        // Initialize the database
        sleepScoresRepository.saveAndFlush(sleepScores);

        // Get all the sleepScoresList where sleepGooalSecondsScore equals to DEFAULT_SLEEP_GOOAL_SECONDS_SCORE
        defaultSleepScoresShouldBeFound("sleepGooalSecondsScore.equals=" + DEFAULT_SLEEP_GOOAL_SECONDS_SCORE);

        // Get all the sleepScoresList where sleepGooalSecondsScore equals to UPDATED_SLEEP_GOOAL_SECONDS_SCORE
        defaultSleepScoresShouldNotBeFound("sleepGooalSecondsScore.equals=" + UPDATED_SLEEP_GOOAL_SECONDS_SCORE);
    }

    @Test
    @Transactional
    void getAllSleepScoresBySleepGooalSecondsScoreIsInShouldWork() throws Exception {
        // Initialize the database
        sleepScoresRepository.saveAndFlush(sleepScores);

        // Get all the sleepScoresList where sleepGooalSecondsScore in DEFAULT_SLEEP_GOOAL_SECONDS_SCORE or UPDATED_SLEEP_GOOAL_SECONDS_SCORE
        defaultSleepScoresShouldBeFound(
            "sleepGooalSecondsScore.in=" + DEFAULT_SLEEP_GOOAL_SECONDS_SCORE + "," + UPDATED_SLEEP_GOOAL_SECONDS_SCORE
        );

        // Get all the sleepScoresList where sleepGooalSecondsScore equals to UPDATED_SLEEP_GOOAL_SECONDS_SCORE
        defaultSleepScoresShouldNotBeFound("sleepGooalSecondsScore.in=" + UPDATED_SLEEP_GOOAL_SECONDS_SCORE);
    }

    @Test
    @Transactional
    void getAllSleepScoresBySleepGooalSecondsScoreIsNullOrNotNull() throws Exception {
        // Initialize the database
        sleepScoresRepository.saveAndFlush(sleepScores);

        // Get all the sleepScoresList where sleepGooalSecondsScore is not null
        defaultSleepScoresShouldBeFound("sleepGooalSecondsScore.specified=true");

        // Get all the sleepScoresList where sleepGooalSecondsScore is null
        defaultSleepScoresShouldNotBeFound("sleepGooalSecondsScore.specified=false");
    }

    @Test
    @Transactional
    void getAllSleepScoresBySleepGooalSecondsScoreIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        sleepScoresRepository.saveAndFlush(sleepScores);

        // Get all the sleepScoresList where sleepGooalSecondsScore is greater than or equal to DEFAULT_SLEEP_GOOAL_SECONDS_SCORE
        defaultSleepScoresShouldBeFound("sleepGooalSecondsScore.greaterThanOrEqual=" + DEFAULT_SLEEP_GOOAL_SECONDS_SCORE);

        // Get all the sleepScoresList where sleepGooalSecondsScore is greater than or equal to UPDATED_SLEEP_GOOAL_SECONDS_SCORE
        defaultSleepScoresShouldNotBeFound("sleepGooalSecondsScore.greaterThanOrEqual=" + UPDATED_SLEEP_GOOAL_SECONDS_SCORE);
    }

    @Test
    @Transactional
    void getAllSleepScoresBySleepGooalSecondsScoreIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        sleepScoresRepository.saveAndFlush(sleepScores);

        // Get all the sleepScoresList where sleepGooalSecondsScore is less than or equal to DEFAULT_SLEEP_GOOAL_SECONDS_SCORE
        defaultSleepScoresShouldBeFound("sleepGooalSecondsScore.lessThanOrEqual=" + DEFAULT_SLEEP_GOOAL_SECONDS_SCORE);

        // Get all the sleepScoresList where sleepGooalSecondsScore is less than or equal to SMALLER_SLEEP_GOOAL_SECONDS_SCORE
        defaultSleepScoresShouldNotBeFound("sleepGooalSecondsScore.lessThanOrEqual=" + SMALLER_SLEEP_GOOAL_SECONDS_SCORE);
    }

    @Test
    @Transactional
    void getAllSleepScoresBySleepGooalSecondsScoreIsLessThanSomething() throws Exception {
        // Initialize the database
        sleepScoresRepository.saveAndFlush(sleepScores);

        // Get all the sleepScoresList where sleepGooalSecondsScore is less than DEFAULT_SLEEP_GOOAL_SECONDS_SCORE
        defaultSleepScoresShouldNotBeFound("sleepGooalSecondsScore.lessThan=" + DEFAULT_SLEEP_GOOAL_SECONDS_SCORE);

        // Get all the sleepScoresList where sleepGooalSecondsScore is less than UPDATED_SLEEP_GOOAL_SECONDS_SCORE
        defaultSleepScoresShouldBeFound("sleepGooalSecondsScore.lessThan=" + UPDATED_SLEEP_GOOAL_SECONDS_SCORE);
    }

    @Test
    @Transactional
    void getAllSleepScoresBySleepGooalSecondsScoreIsGreaterThanSomething() throws Exception {
        // Initialize the database
        sleepScoresRepository.saveAndFlush(sleepScores);

        // Get all the sleepScoresList where sleepGooalSecondsScore is greater than DEFAULT_SLEEP_GOOAL_SECONDS_SCORE
        defaultSleepScoresShouldNotBeFound("sleepGooalSecondsScore.greaterThan=" + DEFAULT_SLEEP_GOOAL_SECONDS_SCORE);

        // Get all the sleepScoresList where sleepGooalSecondsScore is greater than SMALLER_SLEEP_GOOAL_SECONDS_SCORE
        defaultSleepScoresShouldBeFound("sleepGooalSecondsScore.greaterThan=" + SMALLER_SLEEP_GOOAL_SECONDS_SCORE);
    }

    @Test
    @Transactional
    void getAllSleepScoresBySleepContinuityScoreIsEqualToSomething() throws Exception {
        // Initialize the database
        sleepScoresRepository.saveAndFlush(sleepScores);

        // Get all the sleepScoresList where sleepContinuityScore equals to DEFAULT_SLEEP_CONTINUITY_SCORE
        defaultSleepScoresShouldBeFound("sleepContinuityScore.equals=" + DEFAULT_SLEEP_CONTINUITY_SCORE);

        // Get all the sleepScoresList where sleepContinuityScore equals to UPDATED_SLEEP_CONTINUITY_SCORE
        defaultSleepScoresShouldNotBeFound("sleepContinuityScore.equals=" + UPDATED_SLEEP_CONTINUITY_SCORE);
    }

    @Test
    @Transactional
    void getAllSleepScoresBySleepContinuityScoreIsInShouldWork() throws Exception {
        // Initialize the database
        sleepScoresRepository.saveAndFlush(sleepScores);

        // Get all the sleepScoresList where sleepContinuityScore in DEFAULT_SLEEP_CONTINUITY_SCORE or UPDATED_SLEEP_CONTINUITY_SCORE
        defaultSleepScoresShouldBeFound("sleepContinuityScore.in=" + DEFAULT_SLEEP_CONTINUITY_SCORE + "," + UPDATED_SLEEP_CONTINUITY_SCORE);

        // Get all the sleepScoresList where sleepContinuityScore equals to UPDATED_SLEEP_CONTINUITY_SCORE
        defaultSleepScoresShouldNotBeFound("sleepContinuityScore.in=" + UPDATED_SLEEP_CONTINUITY_SCORE);
    }

    @Test
    @Transactional
    void getAllSleepScoresBySleepContinuityScoreIsNullOrNotNull() throws Exception {
        // Initialize the database
        sleepScoresRepository.saveAndFlush(sleepScores);

        // Get all the sleepScoresList where sleepContinuityScore is not null
        defaultSleepScoresShouldBeFound("sleepContinuityScore.specified=true");

        // Get all the sleepScoresList where sleepContinuityScore is null
        defaultSleepScoresShouldNotBeFound("sleepContinuityScore.specified=false");
    }

    @Test
    @Transactional
    void getAllSleepScoresBySleepContinuityScoreIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        sleepScoresRepository.saveAndFlush(sleepScores);

        // Get all the sleepScoresList where sleepContinuityScore is greater than or equal to DEFAULT_SLEEP_CONTINUITY_SCORE
        defaultSleepScoresShouldBeFound("sleepContinuityScore.greaterThanOrEqual=" + DEFAULT_SLEEP_CONTINUITY_SCORE);

        // Get all the sleepScoresList where sleepContinuityScore is greater than or equal to UPDATED_SLEEP_CONTINUITY_SCORE
        defaultSleepScoresShouldNotBeFound("sleepContinuityScore.greaterThanOrEqual=" + UPDATED_SLEEP_CONTINUITY_SCORE);
    }

    @Test
    @Transactional
    void getAllSleepScoresBySleepContinuityScoreIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        sleepScoresRepository.saveAndFlush(sleepScores);

        // Get all the sleepScoresList where sleepContinuityScore is less than or equal to DEFAULT_SLEEP_CONTINUITY_SCORE
        defaultSleepScoresShouldBeFound("sleepContinuityScore.lessThanOrEqual=" + DEFAULT_SLEEP_CONTINUITY_SCORE);

        // Get all the sleepScoresList where sleepContinuityScore is less than or equal to SMALLER_SLEEP_CONTINUITY_SCORE
        defaultSleepScoresShouldNotBeFound("sleepContinuityScore.lessThanOrEqual=" + SMALLER_SLEEP_CONTINUITY_SCORE);
    }

    @Test
    @Transactional
    void getAllSleepScoresBySleepContinuityScoreIsLessThanSomething() throws Exception {
        // Initialize the database
        sleepScoresRepository.saveAndFlush(sleepScores);

        // Get all the sleepScoresList where sleepContinuityScore is less than DEFAULT_SLEEP_CONTINUITY_SCORE
        defaultSleepScoresShouldNotBeFound("sleepContinuityScore.lessThan=" + DEFAULT_SLEEP_CONTINUITY_SCORE);

        // Get all the sleepScoresList where sleepContinuityScore is less than UPDATED_SLEEP_CONTINUITY_SCORE
        defaultSleepScoresShouldBeFound("sleepContinuityScore.lessThan=" + UPDATED_SLEEP_CONTINUITY_SCORE);
    }

    @Test
    @Transactional
    void getAllSleepScoresBySleepContinuityScoreIsGreaterThanSomething() throws Exception {
        // Initialize the database
        sleepScoresRepository.saveAndFlush(sleepScores);

        // Get all the sleepScoresList where sleepContinuityScore is greater than DEFAULT_SLEEP_CONTINUITY_SCORE
        defaultSleepScoresShouldNotBeFound("sleepContinuityScore.greaterThan=" + DEFAULT_SLEEP_CONTINUITY_SCORE);

        // Get all the sleepScoresList where sleepContinuityScore is greater than SMALLER_SLEEP_CONTINUITY_SCORE
        defaultSleepScoresShouldBeFound("sleepContinuityScore.greaterThan=" + SMALLER_SLEEP_CONTINUITY_SCORE);
    }

    @Test
    @Transactional
    void getAllSleepScoresBySleepContinuityRatingIsEqualToSomething() throws Exception {
        // Initialize the database
        sleepScoresRepository.saveAndFlush(sleepScores);

        // Get all the sleepScoresList where sleepContinuityRating equals to DEFAULT_SLEEP_CONTINUITY_RATING
        defaultSleepScoresShouldBeFound("sleepContinuityRating.equals=" + DEFAULT_SLEEP_CONTINUITY_RATING);

        // Get all the sleepScoresList where sleepContinuityRating equals to UPDATED_SLEEP_CONTINUITY_RATING
        defaultSleepScoresShouldNotBeFound("sleepContinuityRating.equals=" + UPDATED_SLEEP_CONTINUITY_RATING);
    }

    @Test
    @Transactional
    void getAllSleepScoresBySleepContinuityRatingIsInShouldWork() throws Exception {
        // Initialize the database
        sleepScoresRepository.saveAndFlush(sleepScores);

        // Get all the sleepScoresList where sleepContinuityRating in DEFAULT_SLEEP_CONTINUITY_RATING or UPDATED_SLEEP_CONTINUITY_RATING
        defaultSleepScoresShouldBeFound(
            "sleepContinuityRating.in=" + DEFAULT_SLEEP_CONTINUITY_RATING + "," + UPDATED_SLEEP_CONTINUITY_RATING
        );

        // Get all the sleepScoresList where sleepContinuityRating equals to UPDATED_SLEEP_CONTINUITY_RATING
        defaultSleepScoresShouldNotBeFound("sleepContinuityRating.in=" + UPDATED_SLEEP_CONTINUITY_RATING);
    }

    @Test
    @Transactional
    void getAllSleepScoresBySleepContinuityRatingIsNullOrNotNull() throws Exception {
        // Initialize the database
        sleepScoresRepository.saveAndFlush(sleepScores);

        // Get all the sleepScoresList where sleepContinuityRating is not null
        defaultSleepScoresShouldBeFound("sleepContinuityRating.specified=true");

        // Get all the sleepScoresList where sleepContinuityRating is null
        defaultSleepScoresShouldNotBeFound("sleepContinuityRating.specified=false");
    }

    @Test
    @Transactional
    void getAllSleepScoresBySleepContinuityRatingIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        sleepScoresRepository.saveAndFlush(sleepScores);

        // Get all the sleepScoresList where sleepContinuityRating is greater than or equal to DEFAULT_SLEEP_CONTINUITY_RATING
        defaultSleepScoresShouldBeFound("sleepContinuityRating.greaterThanOrEqual=" + DEFAULT_SLEEP_CONTINUITY_RATING);

        // Get all the sleepScoresList where sleepContinuityRating is greater than or equal to UPDATED_SLEEP_CONTINUITY_RATING
        defaultSleepScoresShouldNotBeFound("sleepContinuityRating.greaterThanOrEqual=" + UPDATED_SLEEP_CONTINUITY_RATING);
    }

    @Test
    @Transactional
    void getAllSleepScoresBySleepContinuityRatingIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        sleepScoresRepository.saveAndFlush(sleepScores);

        // Get all the sleepScoresList where sleepContinuityRating is less than or equal to DEFAULT_SLEEP_CONTINUITY_RATING
        defaultSleepScoresShouldBeFound("sleepContinuityRating.lessThanOrEqual=" + DEFAULT_SLEEP_CONTINUITY_RATING);

        // Get all the sleepScoresList where sleepContinuityRating is less than or equal to SMALLER_SLEEP_CONTINUITY_RATING
        defaultSleepScoresShouldNotBeFound("sleepContinuityRating.lessThanOrEqual=" + SMALLER_SLEEP_CONTINUITY_RATING);
    }

    @Test
    @Transactional
    void getAllSleepScoresBySleepContinuityRatingIsLessThanSomething() throws Exception {
        // Initialize the database
        sleepScoresRepository.saveAndFlush(sleepScores);

        // Get all the sleepScoresList where sleepContinuityRating is less than DEFAULT_SLEEP_CONTINUITY_RATING
        defaultSleepScoresShouldNotBeFound("sleepContinuityRating.lessThan=" + DEFAULT_SLEEP_CONTINUITY_RATING);

        // Get all the sleepScoresList where sleepContinuityRating is less than UPDATED_SLEEP_CONTINUITY_RATING
        defaultSleepScoresShouldBeFound("sleepContinuityRating.lessThan=" + UPDATED_SLEEP_CONTINUITY_RATING);
    }

    @Test
    @Transactional
    void getAllSleepScoresBySleepContinuityRatingIsGreaterThanSomething() throws Exception {
        // Initialize the database
        sleepScoresRepository.saveAndFlush(sleepScores);

        // Get all the sleepScoresList where sleepContinuityRating is greater than DEFAULT_SLEEP_CONTINUITY_RATING
        defaultSleepScoresShouldNotBeFound("sleepContinuityRating.greaterThan=" + DEFAULT_SLEEP_CONTINUITY_RATING);

        // Get all the sleepScoresList where sleepContinuityRating is greater than SMALLER_SLEEP_CONTINUITY_RATING
        defaultSleepScoresShouldBeFound("sleepContinuityRating.greaterThan=" + SMALLER_SLEEP_CONTINUITY_RATING);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultSleepScoresShouldBeFound(String filter) throws Exception {
        restSleepScoresMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sleepScores.getId().toString())))
            .andExpect(jsonPath("$.[*].usuarioId").value(hasItem(DEFAULT_USUARIO_ID)))
            .andExpect(jsonPath("$.[*].empresaId").value(hasItem(DEFAULT_EMPRESA_ID)))
            .andExpect(jsonPath("$.[*].sleepQualityRatingScore").value(hasItem(DEFAULT_SLEEP_QUALITY_RATING_SCORE)))
            .andExpect(jsonPath("$.[*].sleepEfficiencyScore").value(hasItem(DEFAULT_SLEEP_EFFICIENCY_SCORE)))
            .andExpect(jsonPath("$.[*].sleepGooalSecondsScore").value(hasItem(DEFAULT_SLEEP_GOOAL_SECONDS_SCORE)))
            .andExpect(jsonPath("$.[*].sleepContinuityScore").value(hasItem(DEFAULT_SLEEP_CONTINUITY_SCORE)))
            .andExpect(jsonPath("$.[*].sleepContinuityRating").value(hasItem(DEFAULT_SLEEP_CONTINUITY_RATING)));

        // Check, that the count call also returns 1
        restSleepScoresMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultSleepScoresShouldNotBeFound(String filter) throws Exception {
        restSleepScoresMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restSleepScoresMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingSleepScores() throws Exception {
        // Get the sleepScores
        restSleepScoresMockMvc.perform(get(ENTITY_API_URL_ID, UUID.randomUUID().toString())).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingSleepScores() throws Exception {
        // Initialize the database
        sleepScoresRepository.saveAndFlush(sleepScores);

        int databaseSizeBeforeUpdate = sleepScoresRepository.findAll().size();

        // Update the sleepScores
        SleepScores updatedSleepScores = sleepScoresRepository.findById(sleepScores.getId()).get();
        // Disconnect from session so that the updates on updatedSleepScores are not directly saved in db
        em.detach(updatedSleepScores);
        updatedSleepScores
            .usuarioId(UPDATED_USUARIO_ID)
            .empresaId(UPDATED_EMPRESA_ID)
            .sleepQualityRatingScore(UPDATED_SLEEP_QUALITY_RATING_SCORE)
            .sleepEfficiencyScore(UPDATED_SLEEP_EFFICIENCY_SCORE)
            .sleepGooalSecondsScore(UPDATED_SLEEP_GOOAL_SECONDS_SCORE)
            .sleepContinuityScore(UPDATED_SLEEP_CONTINUITY_SCORE)
            .sleepContinuityRating(UPDATED_SLEEP_CONTINUITY_RATING);

        restSleepScoresMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedSleepScores.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedSleepScores))
            )
            .andExpect(status().isOk());

        // Validate the SleepScores in the database
        List<SleepScores> sleepScoresList = sleepScoresRepository.findAll();
        assertThat(sleepScoresList).hasSize(databaseSizeBeforeUpdate);
        SleepScores testSleepScores = sleepScoresList.get(sleepScoresList.size() - 1);
        assertThat(testSleepScores.getUsuarioId()).isEqualTo(UPDATED_USUARIO_ID);
        assertThat(testSleepScores.getEmpresaId()).isEqualTo(UPDATED_EMPRESA_ID);
        assertThat(testSleepScores.getSleepQualityRatingScore()).isEqualTo(UPDATED_SLEEP_QUALITY_RATING_SCORE);
        assertThat(testSleepScores.getSleepEfficiencyScore()).isEqualTo(UPDATED_SLEEP_EFFICIENCY_SCORE);
        assertThat(testSleepScores.getSleepGooalSecondsScore()).isEqualTo(UPDATED_SLEEP_GOOAL_SECONDS_SCORE);
        assertThat(testSleepScores.getSleepContinuityScore()).isEqualTo(UPDATED_SLEEP_CONTINUITY_SCORE);
        assertThat(testSleepScores.getSleepContinuityRating()).isEqualTo(UPDATED_SLEEP_CONTINUITY_RATING);
    }

    @Test
    @Transactional
    void putNonExistingSleepScores() throws Exception {
        int databaseSizeBeforeUpdate = sleepScoresRepository.findAll().size();
        sleepScores.setId(UUID.randomUUID());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSleepScoresMockMvc
            .perform(
                put(ENTITY_API_URL_ID, sleepScores.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(sleepScores))
            )
            .andExpect(status().isBadRequest());

        // Validate the SleepScores in the database
        List<SleepScores> sleepScoresList = sleepScoresRepository.findAll();
        assertThat(sleepScoresList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSleepScores() throws Exception {
        int databaseSizeBeforeUpdate = sleepScoresRepository.findAll().size();
        sleepScores.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSleepScoresMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(sleepScores))
            )
            .andExpect(status().isBadRequest());

        // Validate the SleepScores in the database
        List<SleepScores> sleepScoresList = sleepScoresRepository.findAll();
        assertThat(sleepScoresList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSleepScores() throws Exception {
        int databaseSizeBeforeUpdate = sleepScoresRepository.findAll().size();
        sleepScores.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSleepScoresMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(sleepScores)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the SleepScores in the database
        List<SleepScores> sleepScoresList = sleepScoresRepository.findAll();
        assertThat(sleepScoresList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSleepScoresWithPatch() throws Exception {
        // Initialize the database
        sleepScoresRepository.saveAndFlush(sleepScores);

        int databaseSizeBeforeUpdate = sleepScoresRepository.findAll().size();

        // Update the sleepScores using partial update
        SleepScores partialUpdatedSleepScores = new SleepScores();
        partialUpdatedSleepScores.setId(sleepScores.getId());

        partialUpdatedSleepScores
            .usuarioId(UPDATED_USUARIO_ID)
            .sleepQualityRatingScore(UPDATED_SLEEP_QUALITY_RATING_SCORE)
            .sleepGooalSecondsScore(UPDATED_SLEEP_GOOAL_SECONDS_SCORE)
            .sleepContinuityRating(UPDATED_SLEEP_CONTINUITY_RATING);

        restSleepScoresMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSleepScores.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSleepScores))
            )
            .andExpect(status().isOk());

        // Validate the SleepScores in the database
        List<SleepScores> sleepScoresList = sleepScoresRepository.findAll();
        assertThat(sleepScoresList).hasSize(databaseSizeBeforeUpdate);
        SleepScores testSleepScores = sleepScoresList.get(sleepScoresList.size() - 1);
        assertThat(testSleepScores.getUsuarioId()).isEqualTo(UPDATED_USUARIO_ID);
        assertThat(testSleepScores.getEmpresaId()).isEqualTo(DEFAULT_EMPRESA_ID);
        assertThat(testSleepScores.getSleepQualityRatingScore()).isEqualTo(UPDATED_SLEEP_QUALITY_RATING_SCORE);
        assertThat(testSleepScores.getSleepEfficiencyScore()).isEqualTo(DEFAULT_SLEEP_EFFICIENCY_SCORE);
        assertThat(testSleepScores.getSleepGooalSecondsScore()).isEqualTo(UPDATED_SLEEP_GOOAL_SECONDS_SCORE);
        assertThat(testSleepScores.getSleepContinuityScore()).isEqualTo(DEFAULT_SLEEP_CONTINUITY_SCORE);
        assertThat(testSleepScores.getSleepContinuityRating()).isEqualTo(UPDATED_SLEEP_CONTINUITY_RATING);
    }

    @Test
    @Transactional
    void fullUpdateSleepScoresWithPatch() throws Exception {
        // Initialize the database
        sleepScoresRepository.saveAndFlush(sleepScores);

        int databaseSizeBeforeUpdate = sleepScoresRepository.findAll().size();

        // Update the sleepScores using partial update
        SleepScores partialUpdatedSleepScores = new SleepScores();
        partialUpdatedSleepScores.setId(sleepScores.getId());

        partialUpdatedSleepScores
            .usuarioId(UPDATED_USUARIO_ID)
            .empresaId(UPDATED_EMPRESA_ID)
            .sleepQualityRatingScore(UPDATED_SLEEP_QUALITY_RATING_SCORE)
            .sleepEfficiencyScore(UPDATED_SLEEP_EFFICIENCY_SCORE)
            .sleepGooalSecondsScore(UPDATED_SLEEP_GOOAL_SECONDS_SCORE)
            .sleepContinuityScore(UPDATED_SLEEP_CONTINUITY_SCORE)
            .sleepContinuityRating(UPDATED_SLEEP_CONTINUITY_RATING);

        restSleepScoresMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSleepScores.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSleepScores))
            )
            .andExpect(status().isOk());

        // Validate the SleepScores in the database
        List<SleepScores> sleepScoresList = sleepScoresRepository.findAll();
        assertThat(sleepScoresList).hasSize(databaseSizeBeforeUpdate);
        SleepScores testSleepScores = sleepScoresList.get(sleepScoresList.size() - 1);
        assertThat(testSleepScores.getUsuarioId()).isEqualTo(UPDATED_USUARIO_ID);
        assertThat(testSleepScores.getEmpresaId()).isEqualTo(UPDATED_EMPRESA_ID);
        assertThat(testSleepScores.getSleepQualityRatingScore()).isEqualTo(UPDATED_SLEEP_QUALITY_RATING_SCORE);
        assertThat(testSleepScores.getSleepEfficiencyScore()).isEqualTo(UPDATED_SLEEP_EFFICIENCY_SCORE);
        assertThat(testSleepScores.getSleepGooalSecondsScore()).isEqualTo(UPDATED_SLEEP_GOOAL_SECONDS_SCORE);
        assertThat(testSleepScores.getSleepContinuityScore()).isEqualTo(UPDATED_SLEEP_CONTINUITY_SCORE);
        assertThat(testSleepScores.getSleepContinuityRating()).isEqualTo(UPDATED_SLEEP_CONTINUITY_RATING);
    }

    @Test
    @Transactional
    void patchNonExistingSleepScores() throws Exception {
        int databaseSizeBeforeUpdate = sleepScoresRepository.findAll().size();
        sleepScores.setId(UUID.randomUUID());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSleepScoresMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, sleepScores.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(sleepScores))
            )
            .andExpect(status().isBadRequest());

        // Validate the SleepScores in the database
        List<SleepScores> sleepScoresList = sleepScoresRepository.findAll();
        assertThat(sleepScoresList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSleepScores() throws Exception {
        int databaseSizeBeforeUpdate = sleepScoresRepository.findAll().size();
        sleepScores.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSleepScoresMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(sleepScores))
            )
            .andExpect(status().isBadRequest());

        // Validate the SleepScores in the database
        List<SleepScores> sleepScoresList = sleepScoresRepository.findAll();
        assertThat(sleepScoresList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSleepScores() throws Exception {
        int databaseSizeBeforeUpdate = sleepScoresRepository.findAll().size();
        sleepScores.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSleepScoresMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(sleepScores))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the SleepScores in the database
        List<SleepScores> sleepScoresList = sleepScoresRepository.findAll();
        assertThat(sleepScoresList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSleepScores() throws Exception {
        // Initialize the database
        sleepScoresRepository.saveAndFlush(sleepScores);

        int databaseSizeBeforeDelete = sleepScoresRepository.findAll().size();

        // Delete the sleepScores
        restSleepScoresMockMvc
            .perform(delete(ENTITY_API_URL_ID, sleepScores.getId().toString()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SleepScores> sleepScoresList = sleepScoresRepository.findAll();
        assertThat(sleepScoresList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
