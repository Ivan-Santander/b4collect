package com.be4tech.b4carecollect.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.be4tech.b4carecollect.IntegrationTest;
import com.be4tech.b4carecollect.domain.MentalHealthSummary;
import com.be4tech.b4carecollect.repository.MentalHealthSummaryRepository;
import com.be4tech.b4carecollect.service.criteria.MentalHealthSummaryCriteria;
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
 * Integration tests for the {@link MentalHealthSummaryResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class MentalHealthSummaryResourceIT {

    private static final String DEFAULT_USUARIO_ID = "AAAAAAAAAA";
    private static final String UPDATED_USUARIO_ID = "BBBBBBBBBB";

    private static final String DEFAULT_EMPRESA_ID = "AAAAAAAAAA";
    private static final String UPDATED_EMPRESA_ID = "BBBBBBBBBB";

    private static final String DEFAULT_EMOTION_DESCRIP_MAIN = "AAAAAAAAAA";
    private static final String UPDATED_EMOTION_DESCRIP_MAIN = "BBBBBBBBBB";

    private static final String DEFAULT_EMOTION_DESCRIP_SECOND = "AAAAAAAAAA";
    private static final String UPDATED_EMOTION_DESCRIP_SECOND = "BBBBBBBBBB";

    private static final Float DEFAULT_FIELD_MENTAL_HEALTH_AVERAGE = 1F;
    private static final Float UPDATED_FIELD_MENTAL_HEALTH_AVERAGE = 2F;
    private static final Float SMALLER_FIELD_MENTAL_HEALTH_AVERAGE = 1F - 1F;

    private static final Float DEFAULT_FIELD_MENTAL_HEALTH_MAX = 1F;
    private static final Float UPDATED_FIELD_MENTAL_HEALTH_MAX = 2F;
    private static final Float SMALLER_FIELD_MENTAL_HEALTH_MAX = 1F - 1F;

    private static final Float DEFAULT_FIELD_MENTAL_HEALTH_MIN = 1F;
    private static final Float UPDATED_FIELD_MENTAL_HEALTH_MIN = 2F;
    private static final Float SMALLER_FIELD_MENTAL_HEALTH_MIN = 1F - 1F;

    private static final Float DEFAULT_SCORE_MENTAL_RISK = 1F;
    private static final Float UPDATED_SCORE_MENTAL_RISK = 2F;
    private static final Float SMALLER_SCORE_MENTAL_RISK = 1F - 1F;

    private static final Instant DEFAULT_START_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_START_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_END_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_END_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/mental-health-summaries";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private MentalHealthSummaryRepository mentalHealthSummaryRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMentalHealthSummaryMockMvc;

    private MentalHealthSummary mentalHealthSummary;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MentalHealthSummary createEntity(EntityManager em) {
        MentalHealthSummary mentalHealthSummary = new MentalHealthSummary()
            .usuarioId(DEFAULT_USUARIO_ID)
            .empresaId(DEFAULT_EMPRESA_ID)
            .emotionDescripMain(DEFAULT_EMOTION_DESCRIP_MAIN)
            .emotionDescripSecond(DEFAULT_EMOTION_DESCRIP_SECOND)
            .fieldMentalHealthAverage(DEFAULT_FIELD_MENTAL_HEALTH_AVERAGE)
            .fieldMentalHealthMax(DEFAULT_FIELD_MENTAL_HEALTH_MAX)
            .fieldMentalHealthMin(DEFAULT_FIELD_MENTAL_HEALTH_MIN)
            .scoreMentalRisk(DEFAULT_SCORE_MENTAL_RISK)
            .startTime(DEFAULT_START_TIME)
            .endTime(DEFAULT_END_TIME);
        return mentalHealthSummary;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MentalHealthSummary createUpdatedEntity(EntityManager em) {
        MentalHealthSummary mentalHealthSummary = new MentalHealthSummary()
            .usuarioId(UPDATED_USUARIO_ID)
            .empresaId(UPDATED_EMPRESA_ID)
            .emotionDescripMain(UPDATED_EMOTION_DESCRIP_MAIN)
            .emotionDescripSecond(UPDATED_EMOTION_DESCRIP_SECOND)
            .fieldMentalHealthAverage(UPDATED_FIELD_MENTAL_HEALTH_AVERAGE)
            .fieldMentalHealthMax(UPDATED_FIELD_MENTAL_HEALTH_MAX)
            .fieldMentalHealthMin(UPDATED_FIELD_MENTAL_HEALTH_MIN)
            .scoreMentalRisk(UPDATED_SCORE_MENTAL_RISK)
            .startTime(UPDATED_START_TIME)
            .endTime(UPDATED_END_TIME);
        return mentalHealthSummary;
    }

    @BeforeEach
    public void initTest() {
        mentalHealthSummary = createEntity(em);
    }

    @Test
    @Transactional
    void createMentalHealthSummary() throws Exception {
        int databaseSizeBeforeCreate = mentalHealthSummaryRepository.findAll().size();
        // Create the MentalHealthSummary
        restMentalHealthSummaryMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(mentalHealthSummary))
            )
            .andExpect(status().isCreated());

        // Validate the MentalHealthSummary in the database
        List<MentalHealthSummary> mentalHealthSummaryList = mentalHealthSummaryRepository.findAll();
        assertThat(mentalHealthSummaryList).hasSize(databaseSizeBeforeCreate + 1);
        MentalHealthSummary testMentalHealthSummary = mentalHealthSummaryList.get(mentalHealthSummaryList.size() - 1);
        assertThat(testMentalHealthSummary.getUsuarioId()).isEqualTo(DEFAULT_USUARIO_ID);
        assertThat(testMentalHealthSummary.getEmpresaId()).isEqualTo(DEFAULT_EMPRESA_ID);
        assertThat(testMentalHealthSummary.getEmotionDescripMain()).isEqualTo(DEFAULT_EMOTION_DESCRIP_MAIN);
        assertThat(testMentalHealthSummary.getEmotionDescripSecond()).isEqualTo(DEFAULT_EMOTION_DESCRIP_SECOND);
        assertThat(testMentalHealthSummary.getFieldMentalHealthAverage()).isEqualTo(DEFAULT_FIELD_MENTAL_HEALTH_AVERAGE);
        assertThat(testMentalHealthSummary.getFieldMentalHealthMax()).isEqualTo(DEFAULT_FIELD_MENTAL_HEALTH_MAX);
        assertThat(testMentalHealthSummary.getFieldMentalHealthMin()).isEqualTo(DEFAULT_FIELD_MENTAL_HEALTH_MIN);
        assertThat(testMentalHealthSummary.getScoreMentalRisk()).isEqualTo(DEFAULT_SCORE_MENTAL_RISK);
        assertThat(testMentalHealthSummary.getStartTime()).isEqualTo(DEFAULT_START_TIME);
        assertThat(testMentalHealthSummary.getEndTime()).isEqualTo(DEFAULT_END_TIME);
    }

    @Test
    @Transactional
    void createMentalHealthSummaryWithExistingId() throws Exception {
        // Create the MentalHealthSummary with an existing ID
        mentalHealthSummaryRepository.saveAndFlush(mentalHealthSummary);

        int databaseSizeBeforeCreate = mentalHealthSummaryRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMentalHealthSummaryMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(mentalHealthSummary))
            )
            .andExpect(status().isBadRequest());

        // Validate the MentalHealthSummary in the database
        List<MentalHealthSummary> mentalHealthSummaryList = mentalHealthSummaryRepository.findAll();
        assertThat(mentalHealthSummaryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllMentalHealthSummaries() throws Exception {
        // Initialize the database
        mentalHealthSummaryRepository.saveAndFlush(mentalHealthSummary);

        // Get all the mentalHealthSummaryList
        restMentalHealthSummaryMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mentalHealthSummary.getId().toString())))
            .andExpect(jsonPath("$.[*].usuarioId").value(hasItem(DEFAULT_USUARIO_ID)))
            .andExpect(jsonPath("$.[*].empresaId").value(hasItem(DEFAULT_EMPRESA_ID)))
            .andExpect(jsonPath("$.[*].emotionDescripMain").value(hasItem(DEFAULT_EMOTION_DESCRIP_MAIN)))
            .andExpect(jsonPath("$.[*].emotionDescripSecond").value(hasItem(DEFAULT_EMOTION_DESCRIP_SECOND)))
            .andExpect(jsonPath("$.[*].fieldMentalHealthAverage").value(hasItem(DEFAULT_FIELD_MENTAL_HEALTH_AVERAGE.doubleValue())))
            .andExpect(jsonPath("$.[*].fieldMentalHealthMax").value(hasItem(DEFAULT_FIELD_MENTAL_HEALTH_MAX.doubleValue())))
            .andExpect(jsonPath("$.[*].fieldMentalHealthMin").value(hasItem(DEFAULT_FIELD_MENTAL_HEALTH_MIN.doubleValue())))
            .andExpect(jsonPath("$.[*].scoreMentalRisk").value(hasItem(DEFAULT_SCORE_MENTAL_RISK.doubleValue())))
            .andExpect(jsonPath("$.[*].startTime").value(hasItem(DEFAULT_START_TIME.toString())))
            .andExpect(jsonPath("$.[*].endTime").value(hasItem(DEFAULT_END_TIME.toString())));
    }

    @Test
    @Transactional
    void getMentalHealthSummary() throws Exception {
        // Initialize the database
        mentalHealthSummaryRepository.saveAndFlush(mentalHealthSummary);

        // Get the mentalHealthSummary
        restMentalHealthSummaryMockMvc
            .perform(get(ENTITY_API_URL_ID, mentalHealthSummary.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(mentalHealthSummary.getId().toString()))
            .andExpect(jsonPath("$.usuarioId").value(DEFAULT_USUARIO_ID))
            .andExpect(jsonPath("$.empresaId").value(DEFAULT_EMPRESA_ID))
            .andExpect(jsonPath("$.emotionDescripMain").value(DEFAULT_EMOTION_DESCRIP_MAIN))
            .andExpect(jsonPath("$.emotionDescripSecond").value(DEFAULT_EMOTION_DESCRIP_SECOND))
            .andExpect(jsonPath("$.fieldMentalHealthAverage").value(DEFAULT_FIELD_MENTAL_HEALTH_AVERAGE.doubleValue()))
            .andExpect(jsonPath("$.fieldMentalHealthMax").value(DEFAULT_FIELD_MENTAL_HEALTH_MAX.doubleValue()))
            .andExpect(jsonPath("$.fieldMentalHealthMin").value(DEFAULT_FIELD_MENTAL_HEALTH_MIN.doubleValue()))
            .andExpect(jsonPath("$.scoreMentalRisk").value(DEFAULT_SCORE_MENTAL_RISK.doubleValue()))
            .andExpect(jsonPath("$.startTime").value(DEFAULT_START_TIME.toString()))
            .andExpect(jsonPath("$.endTime").value(DEFAULT_END_TIME.toString()));
    }

    @Test
    @Transactional
    void getMentalHealthSummariesByIdFiltering() throws Exception {
        // Initialize the database
        mentalHealthSummaryRepository.saveAndFlush(mentalHealthSummary);

        UUID id = mentalHealthSummary.getId();

        defaultMentalHealthSummaryShouldBeFound("id.equals=" + id);
        defaultMentalHealthSummaryShouldNotBeFound("id.notEquals=" + id);
    }

    @Test
    @Transactional
    void getAllMentalHealthSummariesByUsuarioIdIsEqualToSomething() throws Exception {
        // Initialize the database
        mentalHealthSummaryRepository.saveAndFlush(mentalHealthSummary);

        // Get all the mentalHealthSummaryList where usuarioId equals to DEFAULT_USUARIO_ID
        defaultMentalHealthSummaryShouldBeFound("usuarioId.equals=" + DEFAULT_USUARIO_ID);

        // Get all the mentalHealthSummaryList where usuarioId equals to UPDATED_USUARIO_ID
        defaultMentalHealthSummaryShouldNotBeFound("usuarioId.equals=" + UPDATED_USUARIO_ID);
    }

    @Test
    @Transactional
    void getAllMentalHealthSummariesByUsuarioIdIsInShouldWork() throws Exception {
        // Initialize the database
        mentalHealthSummaryRepository.saveAndFlush(mentalHealthSummary);

        // Get all the mentalHealthSummaryList where usuarioId in DEFAULT_USUARIO_ID or UPDATED_USUARIO_ID
        defaultMentalHealthSummaryShouldBeFound("usuarioId.in=" + DEFAULT_USUARIO_ID + "," + UPDATED_USUARIO_ID);

        // Get all the mentalHealthSummaryList where usuarioId equals to UPDATED_USUARIO_ID
        defaultMentalHealthSummaryShouldNotBeFound("usuarioId.in=" + UPDATED_USUARIO_ID);
    }

    @Test
    @Transactional
    void getAllMentalHealthSummariesByUsuarioIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        mentalHealthSummaryRepository.saveAndFlush(mentalHealthSummary);

        // Get all the mentalHealthSummaryList where usuarioId is not null
        defaultMentalHealthSummaryShouldBeFound("usuarioId.specified=true");

        // Get all the mentalHealthSummaryList where usuarioId is null
        defaultMentalHealthSummaryShouldNotBeFound("usuarioId.specified=false");
    }

    @Test
    @Transactional
    void getAllMentalHealthSummariesByUsuarioIdContainsSomething() throws Exception {
        // Initialize the database
        mentalHealthSummaryRepository.saveAndFlush(mentalHealthSummary);

        // Get all the mentalHealthSummaryList where usuarioId contains DEFAULT_USUARIO_ID
        defaultMentalHealthSummaryShouldBeFound("usuarioId.contains=" + DEFAULT_USUARIO_ID);

        // Get all the mentalHealthSummaryList where usuarioId contains UPDATED_USUARIO_ID
        defaultMentalHealthSummaryShouldNotBeFound("usuarioId.contains=" + UPDATED_USUARIO_ID);
    }

    @Test
    @Transactional
    void getAllMentalHealthSummariesByUsuarioIdNotContainsSomething() throws Exception {
        // Initialize the database
        mentalHealthSummaryRepository.saveAndFlush(mentalHealthSummary);

        // Get all the mentalHealthSummaryList where usuarioId does not contain DEFAULT_USUARIO_ID
        defaultMentalHealthSummaryShouldNotBeFound("usuarioId.doesNotContain=" + DEFAULT_USUARIO_ID);

        // Get all the mentalHealthSummaryList where usuarioId does not contain UPDATED_USUARIO_ID
        defaultMentalHealthSummaryShouldBeFound("usuarioId.doesNotContain=" + UPDATED_USUARIO_ID);
    }

    @Test
    @Transactional
    void getAllMentalHealthSummariesByEmpresaIdIsEqualToSomething() throws Exception {
        // Initialize the database
        mentalHealthSummaryRepository.saveAndFlush(mentalHealthSummary);

        // Get all the mentalHealthSummaryList where empresaId equals to DEFAULT_EMPRESA_ID
        defaultMentalHealthSummaryShouldBeFound("empresaId.equals=" + DEFAULT_EMPRESA_ID);

        // Get all the mentalHealthSummaryList where empresaId equals to UPDATED_EMPRESA_ID
        defaultMentalHealthSummaryShouldNotBeFound("empresaId.equals=" + UPDATED_EMPRESA_ID);
    }

    @Test
    @Transactional
    void getAllMentalHealthSummariesByEmpresaIdIsInShouldWork() throws Exception {
        // Initialize the database
        mentalHealthSummaryRepository.saveAndFlush(mentalHealthSummary);

        // Get all the mentalHealthSummaryList where empresaId in DEFAULT_EMPRESA_ID or UPDATED_EMPRESA_ID
        defaultMentalHealthSummaryShouldBeFound("empresaId.in=" + DEFAULT_EMPRESA_ID + "," + UPDATED_EMPRESA_ID);

        // Get all the mentalHealthSummaryList where empresaId equals to UPDATED_EMPRESA_ID
        defaultMentalHealthSummaryShouldNotBeFound("empresaId.in=" + UPDATED_EMPRESA_ID);
    }

    @Test
    @Transactional
    void getAllMentalHealthSummariesByEmpresaIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        mentalHealthSummaryRepository.saveAndFlush(mentalHealthSummary);

        // Get all the mentalHealthSummaryList where empresaId is not null
        defaultMentalHealthSummaryShouldBeFound("empresaId.specified=true");

        // Get all the mentalHealthSummaryList where empresaId is null
        defaultMentalHealthSummaryShouldNotBeFound("empresaId.specified=false");
    }

    @Test
    @Transactional
    void getAllMentalHealthSummariesByEmpresaIdContainsSomething() throws Exception {
        // Initialize the database
        mentalHealthSummaryRepository.saveAndFlush(mentalHealthSummary);

        // Get all the mentalHealthSummaryList where empresaId contains DEFAULT_EMPRESA_ID
        defaultMentalHealthSummaryShouldBeFound("empresaId.contains=" + DEFAULT_EMPRESA_ID);

        // Get all the mentalHealthSummaryList where empresaId contains UPDATED_EMPRESA_ID
        defaultMentalHealthSummaryShouldNotBeFound("empresaId.contains=" + UPDATED_EMPRESA_ID);
    }

    @Test
    @Transactional
    void getAllMentalHealthSummariesByEmpresaIdNotContainsSomething() throws Exception {
        // Initialize the database
        mentalHealthSummaryRepository.saveAndFlush(mentalHealthSummary);

        // Get all the mentalHealthSummaryList where empresaId does not contain DEFAULT_EMPRESA_ID
        defaultMentalHealthSummaryShouldNotBeFound("empresaId.doesNotContain=" + DEFAULT_EMPRESA_ID);

        // Get all the mentalHealthSummaryList where empresaId does not contain UPDATED_EMPRESA_ID
        defaultMentalHealthSummaryShouldBeFound("empresaId.doesNotContain=" + UPDATED_EMPRESA_ID);
    }

    @Test
    @Transactional
    void getAllMentalHealthSummariesByEmotionDescripMainIsEqualToSomething() throws Exception {
        // Initialize the database
        mentalHealthSummaryRepository.saveAndFlush(mentalHealthSummary);

        // Get all the mentalHealthSummaryList where emotionDescripMain equals to DEFAULT_EMOTION_DESCRIP_MAIN
        defaultMentalHealthSummaryShouldBeFound("emotionDescripMain.equals=" + DEFAULT_EMOTION_DESCRIP_MAIN);

        // Get all the mentalHealthSummaryList where emotionDescripMain equals to UPDATED_EMOTION_DESCRIP_MAIN
        defaultMentalHealthSummaryShouldNotBeFound("emotionDescripMain.equals=" + UPDATED_EMOTION_DESCRIP_MAIN);
    }

    @Test
    @Transactional
    void getAllMentalHealthSummariesByEmotionDescripMainIsInShouldWork() throws Exception {
        // Initialize the database
        mentalHealthSummaryRepository.saveAndFlush(mentalHealthSummary);

        // Get all the mentalHealthSummaryList where emotionDescripMain in DEFAULT_EMOTION_DESCRIP_MAIN or UPDATED_EMOTION_DESCRIP_MAIN
        defaultMentalHealthSummaryShouldBeFound(
            "emotionDescripMain.in=" + DEFAULT_EMOTION_DESCRIP_MAIN + "," + UPDATED_EMOTION_DESCRIP_MAIN
        );

        // Get all the mentalHealthSummaryList where emotionDescripMain equals to UPDATED_EMOTION_DESCRIP_MAIN
        defaultMentalHealthSummaryShouldNotBeFound("emotionDescripMain.in=" + UPDATED_EMOTION_DESCRIP_MAIN);
    }

    @Test
    @Transactional
    void getAllMentalHealthSummariesByEmotionDescripMainIsNullOrNotNull() throws Exception {
        // Initialize the database
        mentalHealthSummaryRepository.saveAndFlush(mentalHealthSummary);

        // Get all the mentalHealthSummaryList where emotionDescripMain is not null
        defaultMentalHealthSummaryShouldBeFound("emotionDescripMain.specified=true");

        // Get all the mentalHealthSummaryList where emotionDescripMain is null
        defaultMentalHealthSummaryShouldNotBeFound("emotionDescripMain.specified=false");
    }

    @Test
    @Transactional
    void getAllMentalHealthSummariesByEmotionDescripMainContainsSomething() throws Exception {
        // Initialize the database
        mentalHealthSummaryRepository.saveAndFlush(mentalHealthSummary);

        // Get all the mentalHealthSummaryList where emotionDescripMain contains DEFAULT_EMOTION_DESCRIP_MAIN
        defaultMentalHealthSummaryShouldBeFound("emotionDescripMain.contains=" + DEFAULT_EMOTION_DESCRIP_MAIN);

        // Get all the mentalHealthSummaryList where emotionDescripMain contains UPDATED_EMOTION_DESCRIP_MAIN
        defaultMentalHealthSummaryShouldNotBeFound("emotionDescripMain.contains=" + UPDATED_EMOTION_DESCRIP_MAIN);
    }

    @Test
    @Transactional
    void getAllMentalHealthSummariesByEmotionDescripMainNotContainsSomething() throws Exception {
        // Initialize the database
        mentalHealthSummaryRepository.saveAndFlush(mentalHealthSummary);

        // Get all the mentalHealthSummaryList where emotionDescripMain does not contain DEFAULT_EMOTION_DESCRIP_MAIN
        defaultMentalHealthSummaryShouldNotBeFound("emotionDescripMain.doesNotContain=" + DEFAULT_EMOTION_DESCRIP_MAIN);

        // Get all the mentalHealthSummaryList where emotionDescripMain does not contain UPDATED_EMOTION_DESCRIP_MAIN
        defaultMentalHealthSummaryShouldBeFound("emotionDescripMain.doesNotContain=" + UPDATED_EMOTION_DESCRIP_MAIN);
    }

    @Test
    @Transactional
    void getAllMentalHealthSummariesByEmotionDescripSecondIsEqualToSomething() throws Exception {
        // Initialize the database
        mentalHealthSummaryRepository.saveAndFlush(mentalHealthSummary);

        // Get all the mentalHealthSummaryList where emotionDescripSecond equals to DEFAULT_EMOTION_DESCRIP_SECOND
        defaultMentalHealthSummaryShouldBeFound("emotionDescripSecond.equals=" + DEFAULT_EMOTION_DESCRIP_SECOND);

        // Get all the mentalHealthSummaryList where emotionDescripSecond equals to UPDATED_EMOTION_DESCRIP_SECOND
        defaultMentalHealthSummaryShouldNotBeFound("emotionDescripSecond.equals=" + UPDATED_EMOTION_DESCRIP_SECOND);
    }

    @Test
    @Transactional
    void getAllMentalHealthSummariesByEmotionDescripSecondIsInShouldWork() throws Exception {
        // Initialize the database
        mentalHealthSummaryRepository.saveAndFlush(mentalHealthSummary);

        // Get all the mentalHealthSummaryList where emotionDescripSecond in DEFAULT_EMOTION_DESCRIP_SECOND or UPDATED_EMOTION_DESCRIP_SECOND
        defaultMentalHealthSummaryShouldBeFound(
            "emotionDescripSecond.in=" + DEFAULT_EMOTION_DESCRIP_SECOND + "," + UPDATED_EMOTION_DESCRIP_SECOND
        );

        // Get all the mentalHealthSummaryList where emotionDescripSecond equals to UPDATED_EMOTION_DESCRIP_SECOND
        defaultMentalHealthSummaryShouldNotBeFound("emotionDescripSecond.in=" + UPDATED_EMOTION_DESCRIP_SECOND);
    }

    @Test
    @Transactional
    void getAllMentalHealthSummariesByEmotionDescripSecondIsNullOrNotNull() throws Exception {
        // Initialize the database
        mentalHealthSummaryRepository.saveAndFlush(mentalHealthSummary);

        // Get all the mentalHealthSummaryList where emotionDescripSecond is not null
        defaultMentalHealthSummaryShouldBeFound("emotionDescripSecond.specified=true");

        // Get all the mentalHealthSummaryList where emotionDescripSecond is null
        defaultMentalHealthSummaryShouldNotBeFound("emotionDescripSecond.specified=false");
    }

    @Test
    @Transactional
    void getAllMentalHealthSummariesByEmotionDescripSecondContainsSomething() throws Exception {
        // Initialize the database
        mentalHealthSummaryRepository.saveAndFlush(mentalHealthSummary);

        // Get all the mentalHealthSummaryList where emotionDescripSecond contains DEFAULT_EMOTION_DESCRIP_SECOND
        defaultMentalHealthSummaryShouldBeFound("emotionDescripSecond.contains=" + DEFAULT_EMOTION_DESCRIP_SECOND);

        // Get all the mentalHealthSummaryList where emotionDescripSecond contains UPDATED_EMOTION_DESCRIP_SECOND
        defaultMentalHealthSummaryShouldNotBeFound("emotionDescripSecond.contains=" + UPDATED_EMOTION_DESCRIP_SECOND);
    }

    @Test
    @Transactional
    void getAllMentalHealthSummariesByEmotionDescripSecondNotContainsSomething() throws Exception {
        // Initialize the database
        mentalHealthSummaryRepository.saveAndFlush(mentalHealthSummary);

        // Get all the mentalHealthSummaryList where emotionDescripSecond does not contain DEFAULT_EMOTION_DESCRIP_SECOND
        defaultMentalHealthSummaryShouldNotBeFound("emotionDescripSecond.doesNotContain=" + DEFAULT_EMOTION_DESCRIP_SECOND);

        // Get all the mentalHealthSummaryList where emotionDescripSecond does not contain UPDATED_EMOTION_DESCRIP_SECOND
        defaultMentalHealthSummaryShouldBeFound("emotionDescripSecond.doesNotContain=" + UPDATED_EMOTION_DESCRIP_SECOND);
    }

    @Test
    @Transactional
    void getAllMentalHealthSummariesByFieldMentalHealthAverageIsEqualToSomething() throws Exception {
        // Initialize the database
        mentalHealthSummaryRepository.saveAndFlush(mentalHealthSummary);

        // Get all the mentalHealthSummaryList where fieldMentalHealthAverage equals to DEFAULT_FIELD_MENTAL_HEALTH_AVERAGE
        defaultMentalHealthSummaryShouldBeFound("fieldMentalHealthAverage.equals=" + DEFAULT_FIELD_MENTAL_HEALTH_AVERAGE);

        // Get all the mentalHealthSummaryList where fieldMentalHealthAverage equals to UPDATED_FIELD_MENTAL_HEALTH_AVERAGE
        defaultMentalHealthSummaryShouldNotBeFound("fieldMentalHealthAverage.equals=" + UPDATED_FIELD_MENTAL_HEALTH_AVERAGE);
    }

    @Test
    @Transactional
    void getAllMentalHealthSummariesByFieldMentalHealthAverageIsInShouldWork() throws Exception {
        // Initialize the database
        mentalHealthSummaryRepository.saveAndFlush(mentalHealthSummary);

        // Get all the mentalHealthSummaryList where fieldMentalHealthAverage in DEFAULT_FIELD_MENTAL_HEALTH_AVERAGE or UPDATED_FIELD_MENTAL_HEALTH_AVERAGE
        defaultMentalHealthSummaryShouldBeFound(
            "fieldMentalHealthAverage.in=" + DEFAULT_FIELD_MENTAL_HEALTH_AVERAGE + "," + UPDATED_FIELD_MENTAL_HEALTH_AVERAGE
        );

        // Get all the mentalHealthSummaryList where fieldMentalHealthAverage equals to UPDATED_FIELD_MENTAL_HEALTH_AVERAGE
        defaultMentalHealthSummaryShouldNotBeFound("fieldMentalHealthAverage.in=" + UPDATED_FIELD_MENTAL_HEALTH_AVERAGE);
    }

    @Test
    @Transactional
    void getAllMentalHealthSummariesByFieldMentalHealthAverageIsNullOrNotNull() throws Exception {
        // Initialize the database
        mentalHealthSummaryRepository.saveAndFlush(mentalHealthSummary);

        // Get all the mentalHealthSummaryList where fieldMentalHealthAverage is not null
        defaultMentalHealthSummaryShouldBeFound("fieldMentalHealthAverage.specified=true");

        // Get all the mentalHealthSummaryList where fieldMentalHealthAverage is null
        defaultMentalHealthSummaryShouldNotBeFound("fieldMentalHealthAverage.specified=false");
    }

    @Test
    @Transactional
    void getAllMentalHealthSummariesByFieldMentalHealthAverageIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mentalHealthSummaryRepository.saveAndFlush(mentalHealthSummary);

        // Get all the mentalHealthSummaryList where fieldMentalHealthAverage is greater than or equal to DEFAULT_FIELD_MENTAL_HEALTH_AVERAGE
        defaultMentalHealthSummaryShouldBeFound("fieldMentalHealthAverage.greaterThanOrEqual=" + DEFAULT_FIELD_MENTAL_HEALTH_AVERAGE);

        // Get all the mentalHealthSummaryList where fieldMentalHealthAverage is greater than or equal to UPDATED_FIELD_MENTAL_HEALTH_AVERAGE
        defaultMentalHealthSummaryShouldNotBeFound("fieldMentalHealthAverage.greaterThanOrEqual=" + UPDATED_FIELD_MENTAL_HEALTH_AVERAGE);
    }

    @Test
    @Transactional
    void getAllMentalHealthSummariesByFieldMentalHealthAverageIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mentalHealthSummaryRepository.saveAndFlush(mentalHealthSummary);

        // Get all the mentalHealthSummaryList where fieldMentalHealthAverage is less than or equal to DEFAULT_FIELD_MENTAL_HEALTH_AVERAGE
        defaultMentalHealthSummaryShouldBeFound("fieldMentalHealthAverage.lessThanOrEqual=" + DEFAULT_FIELD_MENTAL_HEALTH_AVERAGE);

        // Get all the mentalHealthSummaryList where fieldMentalHealthAverage is less than or equal to SMALLER_FIELD_MENTAL_HEALTH_AVERAGE
        defaultMentalHealthSummaryShouldNotBeFound("fieldMentalHealthAverage.lessThanOrEqual=" + SMALLER_FIELD_MENTAL_HEALTH_AVERAGE);
    }

    @Test
    @Transactional
    void getAllMentalHealthSummariesByFieldMentalHealthAverageIsLessThanSomething() throws Exception {
        // Initialize the database
        mentalHealthSummaryRepository.saveAndFlush(mentalHealthSummary);

        // Get all the mentalHealthSummaryList where fieldMentalHealthAverage is less than DEFAULT_FIELD_MENTAL_HEALTH_AVERAGE
        defaultMentalHealthSummaryShouldNotBeFound("fieldMentalHealthAverage.lessThan=" + DEFAULT_FIELD_MENTAL_HEALTH_AVERAGE);

        // Get all the mentalHealthSummaryList where fieldMentalHealthAverage is less than UPDATED_FIELD_MENTAL_HEALTH_AVERAGE
        defaultMentalHealthSummaryShouldBeFound("fieldMentalHealthAverage.lessThan=" + UPDATED_FIELD_MENTAL_HEALTH_AVERAGE);
    }

    @Test
    @Transactional
    void getAllMentalHealthSummariesByFieldMentalHealthAverageIsGreaterThanSomething() throws Exception {
        // Initialize the database
        mentalHealthSummaryRepository.saveAndFlush(mentalHealthSummary);

        // Get all the mentalHealthSummaryList where fieldMentalHealthAverage is greater than DEFAULT_FIELD_MENTAL_HEALTH_AVERAGE
        defaultMentalHealthSummaryShouldNotBeFound("fieldMentalHealthAverage.greaterThan=" + DEFAULT_FIELD_MENTAL_HEALTH_AVERAGE);

        // Get all the mentalHealthSummaryList where fieldMentalHealthAverage is greater than SMALLER_FIELD_MENTAL_HEALTH_AVERAGE
        defaultMentalHealthSummaryShouldBeFound("fieldMentalHealthAverage.greaterThan=" + SMALLER_FIELD_MENTAL_HEALTH_AVERAGE);
    }

    @Test
    @Transactional
    void getAllMentalHealthSummariesByFieldMentalHealthMaxIsEqualToSomething() throws Exception {
        // Initialize the database
        mentalHealthSummaryRepository.saveAndFlush(mentalHealthSummary);

        // Get all the mentalHealthSummaryList where fieldMentalHealthMax equals to DEFAULT_FIELD_MENTAL_HEALTH_MAX
        defaultMentalHealthSummaryShouldBeFound("fieldMentalHealthMax.equals=" + DEFAULT_FIELD_MENTAL_HEALTH_MAX);

        // Get all the mentalHealthSummaryList where fieldMentalHealthMax equals to UPDATED_FIELD_MENTAL_HEALTH_MAX
        defaultMentalHealthSummaryShouldNotBeFound("fieldMentalHealthMax.equals=" + UPDATED_FIELD_MENTAL_HEALTH_MAX);
    }

    @Test
    @Transactional
    void getAllMentalHealthSummariesByFieldMentalHealthMaxIsInShouldWork() throws Exception {
        // Initialize the database
        mentalHealthSummaryRepository.saveAndFlush(mentalHealthSummary);

        // Get all the mentalHealthSummaryList where fieldMentalHealthMax in DEFAULT_FIELD_MENTAL_HEALTH_MAX or UPDATED_FIELD_MENTAL_HEALTH_MAX
        defaultMentalHealthSummaryShouldBeFound(
            "fieldMentalHealthMax.in=" + DEFAULT_FIELD_MENTAL_HEALTH_MAX + "," + UPDATED_FIELD_MENTAL_HEALTH_MAX
        );

        // Get all the mentalHealthSummaryList where fieldMentalHealthMax equals to UPDATED_FIELD_MENTAL_HEALTH_MAX
        defaultMentalHealthSummaryShouldNotBeFound("fieldMentalHealthMax.in=" + UPDATED_FIELD_MENTAL_HEALTH_MAX);
    }

    @Test
    @Transactional
    void getAllMentalHealthSummariesByFieldMentalHealthMaxIsNullOrNotNull() throws Exception {
        // Initialize the database
        mentalHealthSummaryRepository.saveAndFlush(mentalHealthSummary);

        // Get all the mentalHealthSummaryList where fieldMentalHealthMax is not null
        defaultMentalHealthSummaryShouldBeFound("fieldMentalHealthMax.specified=true");

        // Get all the mentalHealthSummaryList where fieldMentalHealthMax is null
        defaultMentalHealthSummaryShouldNotBeFound("fieldMentalHealthMax.specified=false");
    }

    @Test
    @Transactional
    void getAllMentalHealthSummariesByFieldMentalHealthMaxIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mentalHealthSummaryRepository.saveAndFlush(mentalHealthSummary);

        // Get all the mentalHealthSummaryList where fieldMentalHealthMax is greater than or equal to DEFAULT_FIELD_MENTAL_HEALTH_MAX
        defaultMentalHealthSummaryShouldBeFound("fieldMentalHealthMax.greaterThanOrEqual=" + DEFAULT_FIELD_MENTAL_HEALTH_MAX);

        // Get all the mentalHealthSummaryList where fieldMentalHealthMax is greater than or equal to UPDATED_FIELD_MENTAL_HEALTH_MAX
        defaultMentalHealthSummaryShouldNotBeFound("fieldMentalHealthMax.greaterThanOrEqual=" + UPDATED_FIELD_MENTAL_HEALTH_MAX);
    }

    @Test
    @Transactional
    void getAllMentalHealthSummariesByFieldMentalHealthMaxIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mentalHealthSummaryRepository.saveAndFlush(mentalHealthSummary);

        // Get all the mentalHealthSummaryList where fieldMentalHealthMax is less than or equal to DEFAULT_FIELD_MENTAL_HEALTH_MAX
        defaultMentalHealthSummaryShouldBeFound("fieldMentalHealthMax.lessThanOrEqual=" + DEFAULT_FIELD_MENTAL_HEALTH_MAX);

        // Get all the mentalHealthSummaryList where fieldMentalHealthMax is less than or equal to SMALLER_FIELD_MENTAL_HEALTH_MAX
        defaultMentalHealthSummaryShouldNotBeFound("fieldMentalHealthMax.lessThanOrEqual=" + SMALLER_FIELD_MENTAL_HEALTH_MAX);
    }

    @Test
    @Transactional
    void getAllMentalHealthSummariesByFieldMentalHealthMaxIsLessThanSomething() throws Exception {
        // Initialize the database
        mentalHealthSummaryRepository.saveAndFlush(mentalHealthSummary);

        // Get all the mentalHealthSummaryList where fieldMentalHealthMax is less than DEFAULT_FIELD_MENTAL_HEALTH_MAX
        defaultMentalHealthSummaryShouldNotBeFound("fieldMentalHealthMax.lessThan=" + DEFAULT_FIELD_MENTAL_HEALTH_MAX);

        // Get all the mentalHealthSummaryList where fieldMentalHealthMax is less than UPDATED_FIELD_MENTAL_HEALTH_MAX
        defaultMentalHealthSummaryShouldBeFound("fieldMentalHealthMax.lessThan=" + UPDATED_FIELD_MENTAL_HEALTH_MAX);
    }

    @Test
    @Transactional
    void getAllMentalHealthSummariesByFieldMentalHealthMaxIsGreaterThanSomething() throws Exception {
        // Initialize the database
        mentalHealthSummaryRepository.saveAndFlush(mentalHealthSummary);

        // Get all the mentalHealthSummaryList where fieldMentalHealthMax is greater than DEFAULT_FIELD_MENTAL_HEALTH_MAX
        defaultMentalHealthSummaryShouldNotBeFound("fieldMentalHealthMax.greaterThan=" + DEFAULT_FIELD_MENTAL_HEALTH_MAX);

        // Get all the mentalHealthSummaryList where fieldMentalHealthMax is greater than SMALLER_FIELD_MENTAL_HEALTH_MAX
        defaultMentalHealthSummaryShouldBeFound("fieldMentalHealthMax.greaterThan=" + SMALLER_FIELD_MENTAL_HEALTH_MAX);
    }

    @Test
    @Transactional
    void getAllMentalHealthSummariesByFieldMentalHealthMinIsEqualToSomething() throws Exception {
        // Initialize the database
        mentalHealthSummaryRepository.saveAndFlush(mentalHealthSummary);

        // Get all the mentalHealthSummaryList where fieldMentalHealthMin equals to DEFAULT_FIELD_MENTAL_HEALTH_MIN
        defaultMentalHealthSummaryShouldBeFound("fieldMentalHealthMin.equals=" + DEFAULT_FIELD_MENTAL_HEALTH_MIN);

        // Get all the mentalHealthSummaryList where fieldMentalHealthMin equals to UPDATED_FIELD_MENTAL_HEALTH_MIN
        defaultMentalHealthSummaryShouldNotBeFound("fieldMentalHealthMin.equals=" + UPDATED_FIELD_MENTAL_HEALTH_MIN);
    }

    @Test
    @Transactional
    void getAllMentalHealthSummariesByFieldMentalHealthMinIsInShouldWork() throws Exception {
        // Initialize the database
        mentalHealthSummaryRepository.saveAndFlush(mentalHealthSummary);

        // Get all the mentalHealthSummaryList where fieldMentalHealthMin in DEFAULT_FIELD_MENTAL_HEALTH_MIN or UPDATED_FIELD_MENTAL_HEALTH_MIN
        defaultMentalHealthSummaryShouldBeFound(
            "fieldMentalHealthMin.in=" + DEFAULT_FIELD_MENTAL_HEALTH_MIN + "," + UPDATED_FIELD_MENTAL_HEALTH_MIN
        );

        // Get all the mentalHealthSummaryList where fieldMentalHealthMin equals to UPDATED_FIELD_MENTAL_HEALTH_MIN
        defaultMentalHealthSummaryShouldNotBeFound("fieldMentalHealthMin.in=" + UPDATED_FIELD_MENTAL_HEALTH_MIN);
    }

    @Test
    @Transactional
    void getAllMentalHealthSummariesByFieldMentalHealthMinIsNullOrNotNull() throws Exception {
        // Initialize the database
        mentalHealthSummaryRepository.saveAndFlush(mentalHealthSummary);

        // Get all the mentalHealthSummaryList where fieldMentalHealthMin is not null
        defaultMentalHealthSummaryShouldBeFound("fieldMentalHealthMin.specified=true");

        // Get all the mentalHealthSummaryList where fieldMentalHealthMin is null
        defaultMentalHealthSummaryShouldNotBeFound("fieldMentalHealthMin.specified=false");
    }

    @Test
    @Transactional
    void getAllMentalHealthSummariesByFieldMentalHealthMinIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mentalHealthSummaryRepository.saveAndFlush(mentalHealthSummary);

        // Get all the mentalHealthSummaryList where fieldMentalHealthMin is greater than or equal to DEFAULT_FIELD_MENTAL_HEALTH_MIN
        defaultMentalHealthSummaryShouldBeFound("fieldMentalHealthMin.greaterThanOrEqual=" + DEFAULT_FIELD_MENTAL_HEALTH_MIN);

        // Get all the mentalHealthSummaryList where fieldMentalHealthMin is greater than or equal to UPDATED_FIELD_MENTAL_HEALTH_MIN
        defaultMentalHealthSummaryShouldNotBeFound("fieldMentalHealthMin.greaterThanOrEqual=" + UPDATED_FIELD_MENTAL_HEALTH_MIN);
    }

    @Test
    @Transactional
    void getAllMentalHealthSummariesByFieldMentalHealthMinIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mentalHealthSummaryRepository.saveAndFlush(mentalHealthSummary);

        // Get all the mentalHealthSummaryList where fieldMentalHealthMin is less than or equal to DEFAULT_FIELD_MENTAL_HEALTH_MIN
        defaultMentalHealthSummaryShouldBeFound("fieldMentalHealthMin.lessThanOrEqual=" + DEFAULT_FIELD_MENTAL_HEALTH_MIN);

        // Get all the mentalHealthSummaryList where fieldMentalHealthMin is less than or equal to SMALLER_FIELD_MENTAL_HEALTH_MIN
        defaultMentalHealthSummaryShouldNotBeFound("fieldMentalHealthMin.lessThanOrEqual=" + SMALLER_FIELD_MENTAL_HEALTH_MIN);
    }

    @Test
    @Transactional
    void getAllMentalHealthSummariesByFieldMentalHealthMinIsLessThanSomething() throws Exception {
        // Initialize the database
        mentalHealthSummaryRepository.saveAndFlush(mentalHealthSummary);

        // Get all the mentalHealthSummaryList where fieldMentalHealthMin is less than DEFAULT_FIELD_MENTAL_HEALTH_MIN
        defaultMentalHealthSummaryShouldNotBeFound("fieldMentalHealthMin.lessThan=" + DEFAULT_FIELD_MENTAL_HEALTH_MIN);

        // Get all the mentalHealthSummaryList where fieldMentalHealthMin is less than UPDATED_FIELD_MENTAL_HEALTH_MIN
        defaultMentalHealthSummaryShouldBeFound("fieldMentalHealthMin.lessThan=" + UPDATED_FIELD_MENTAL_HEALTH_MIN);
    }

    @Test
    @Transactional
    void getAllMentalHealthSummariesByFieldMentalHealthMinIsGreaterThanSomething() throws Exception {
        // Initialize the database
        mentalHealthSummaryRepository.saveAndFlush(mentalHealthSummary);

        // Get all the mentalHealthSummaryList where fieldMentalHealthMin is greater than DEFAULT_FIELD_MENTAL_HEALTH_MIN
        defaultMentalHealthSummaryShouldNotBeFound("fieldMentalHealthMin.greaterThan=" + DEFAULT_FIELD_MENTAL_HEALTH_MIN);

        // Get all the mentalHealthSummaryList where fieldMentalHealthMin is greater than SMALLER_FIELD_MENTAL_HEALTH_MIN
        defaultMentalHealthSummaryShouldBeFound("fieldMentalHealthMin.greaterThan=" + SMALLER_FIELD_MENTAL_HEALTH_MIN);
    }

    @Test
    @Transactional
    void getAllMentalHealthSummariesByScoreMentalRiskIsEqualToSomething() throws Exception {
        // Initialize the database
        mentalHealthSummaryRepository.saveAndFlush(mentalHealthSummary);

        // Get all the mentalHealthSummaryList where scoreMentalRisk equals to DEFAULT_SCORE_MENTAL_RISK
        defaultMentalHealthSummaryShouldBeFound("scoreMentalRisk.equals=" + DEFAULT_SCORE_MENTAL_RISK);

        // Get all the mentalHealthSummaryList where scoreMentalRisk equals to UPDATED_SCORE_MENTAL_RISK
        defaultMentalHealthSummaryShouldNotBeFound("scoreMentalRisk.equals=" + UPDATED_SCORE_MENTAL_RISK);
    }

    @Test
    @Transactional
    void getAllMentalHealthSummariesByScoreMentalRiskIsInShouldWork() throws Exception {
        // Initialize the database
        mentalHealthSummaryRepository.saveAndFlush(mentalHealthSummary);

        // Get all the mentalHealthSummaryList where scoreMentalRisk in DEFAULT_SCORE_MENTAL_RISK or UPDATED_SCORE_MENTAL_RISK
        defaultMentalHealthSummaryShouldBeFound("scoreMentalRisk.in=" + DEFAULT_SCORE_MENTAL_RISK + "," + UPDATED_SCORE_MENTAL_RISK);

        // Get all the mentalHealthSummaryList where scoreMentalRisk equals to UPDATED_SCORE_MENTAL_RISK
        defaultMentalHealthSummaryShouldNotBeFound("scoreMentalRisk.in=" + UPDATED_SCORE_MENTAL_RISK);
    }

    @Test
    @Transactional
    void getAllMentalHealthSummariesByScoreMentalRiskIsNullOrNotNull() throws Exception {
        // Initialize the database
        mentalHealthSummaryRepository.saveAndFlush(mentalHealthSummary);

        // Get all the mentalHealthSummaryList where scoreMentalRisk is not null
        defaultMentalHealthSummaryShouldBeFound("scoreMentalRisk.specified=true");

        // Get all the mentalHealthSummaryList where scoreMentalRisk is null
        defaultMentalHealthSummaryShouldNotBeFound("scoreMentalRisk.specified=false");
    }

    @Test
    @Transactional
    void getAllMentalHealthSummariesByScoreMentalRiskIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mentalHealthSummaryRepository.saveAndFlush(mentalHealthSummary);

        // Get all the mentalHealthSummaryList where scoreMentalRisk is greater than or equal to DEFAULT_SCORE_MENTAL_RISK
        defaultMentalHealthSummaryShouldBeFound("scoreMentalRisk.greaterThanOrEqual=" + DEFAULT_SCORE_MENTAL_RISK);

        // Get all the mentalHealthSummaryList where scoreMentalRisk is greater than or equal to UPDATED_SCORE_MENTAL_RISK
        defaultMentalHealthSummaryShouldNotBeFound("scoreMentalRisk.greaterThanOrEqual=" + UPDATED_SCORE_MENTAL_RISK);
    }

    @Test
    @Transactional
    void getAllMentalHealthSummariesByScoreMentalRiskIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mentalHealthSummaryRepository.saveAndFlush(mentalHealthSummary);

        // Get all the mentalHealthSummaryList where scoreMentalRisk is less than or equal to DEFAULT_SCORE_MENTAL_RISK
        defaultMentalHealthSummaryShouldBeFound("scoreMentalRisk.lessThanOrEqual=" + DEFAULT_SCORE_MENTAL_RISK);

        // Get all the mentalHealthSummaryList where scoreMentalRisk is less than or equal to SMALLER_SCORE_MENTAL_RISK
        defaultMentalHealthSummaryShouldNotBeFound("scoreMentalRisk.lessThanOrEqual=" + SMALLER_SCORE_MENTAL_RISK);
    }

    @Test
    @Transactional
    void getAllMentalHealthSummariesByScoreMentalRiskIsLessThanSomething() throws Exception {
        // Initialize the database
        mentalHealthSummaryRepository.saveAndFlush(mentalHealthSummary);

        // Get all the mentalHealthSummaryList where scoreMentalRisk is less than DEFAULT_SCORE_MENTAL_RISK
        defaultMentalHealthSummaryShouldNotBeFound("scoreMentalRisk.lessThan=" + DEFAULT_SCORE_MENTAL_RISK);

        // Get all the mentalHealthSummaryList where scoreMentalRisk is less than UPDATED_SCORE_MENTAL_RISK
        defaultMentalHealthSummaryShouldBeFound("scoreMentalRisk.lessThan=" + UPDATED_SCORE_MENTAL_RISK);
    }

    @Test
    @Transactional
    void getAllMentalHealthSummariesByScoreMentalRiskIsGreaterThanSomething() throws Exception {
        // Initialize the database
        mentalHealthSummaryRepository.saveAndFlush(mentalHealthSummary);

        // Get all the mentalHealthSummaryList where scoreMentalRisk is greater than DEFAULT_SCORE_MENTAL_RISK
        defaultMentalHealthSummaryShouldNotBeFound("scoreMentalRisk.greaterThan=" + DEFAULT_SCORE_MENTAL_RISK);

        // Get all the mentalHealthSummaryList where scoreMentalRisk is greater than SMALLER_SCORE_MENTAL_RISK
        defaultMentalHealthSummaryShouldBeFound("scoreMentalRisk.greaterThan=" + SMALLER_SCORE_MENTAL_RISK);
    }

    @Test
    @Transactional
    void getAllMentalHealthSummariesByStartTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        mentalHealthSummaryRepository.saveAndFlush(mentalHealthSummary);

        // Get all the mentalHealthSummaryList where startTime equals to DEFAULT_START_TIME
        defaultMentalHealthSummaryShouldBeFound("startTime.equals=" + DEFAULT_START_TIME);

        // Get all the mentalHealthSummaryList where startTime equals to UPDATED_START_TIME
        defaultMentalHealthSummaryShouldNotBeFound("startTime.equals=" + UPDATED_START_TIME);
    }

    @Test
    @Transactional
    void getAllMentalHealthSummariesByStartTimeIsInShouldWork() throws Exception {
        // Initialize the database
        mentalHealthSummaryRepository.saveAndFlush(mentalHealthSummary);

        // Get all the mentalHealthSummaryList where startTime in DEFAULT_START_TIME or UPDATED_START_TIME
        defaultMentalHealthSummaryShouldBeFound("startTime.in=" + DEFAULT_START_TIME + "," + UPDATED_START_TIME);

        // Get all the mentalHealthSummaryList where startTime equals to UPDATED_START_TIME
        defaultMentalHealthSummaryShouldNotBeFound("startTime.in=" + UPDATED_START_TIME);
    }

    @Test
    @Transactional
    void getAllMentalHealthSummariesByStartTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        mentalHealthSummaryRepository.saveAndFlush(mentalHealthSummary);

        // Get all the mentalHealthSummaryList where startTime is not null
        defaultMentalHealthSummaryShouldBeFound("startTime.specified=true");

        // Get all the mentalHealthSummaryList where startTime is null
        defaultMentalHealthSummaryShouldNotBeFound("startTime.specified=false");
    }

    @Test
    @Transactional
    void getAllMentalHealthSummariesByEndTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        mentalHealthSummaryRepository.saveAndFlush(mentalHealthSummary);

        // Get all the mentalHealthSummaryList where endTime equals to DEFAULT_END_TIME
        defaultMentalHealthSummaryShouldBeFound("endTime.equals=" + DEFAULT_END_TIME);

        // Get all the mentalHealthSummaryList where endTime equals to UPDATED_END_TIME
        defaultMentalHealthSummaryShouldNotBeFound("endTime.equals=" + UPDATED_END_TIME);
    }

    @Test
    @Transactional
    void getAllMentalHealthSummariesByEndTimeIsInShouldWork() throws Exception {
        // Initialize the database
        mentalHealthSummaryRepository.saveAndFlush(mentalHealthSummary);

        // Get all the mentalHealthSummaryList where endTime in DEFAULT_END_TIME or UPDATED_END_TIME
        defaultMentalHealthSummaryShouldBeFound("endTime.in=" + DEFAULT_END_TIME + "," + UPDATED_END_TIME);

        // Get all the mentalHealthSummaryList where endTime equals to UPDATED_END_TIME
        defaultMentalHealthSummaryShouldNotBeFound("endTime.in=" + UPDATED_END_TIME);
    }

    @Test
    @Transactional
    void getAllMentalHealthSummariesByEndTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        mentalHealthSummaryRepository.saveAndFlush(mentalHealthSummary);

        // Get all the mentalHealthSummaryList where endTime is not null
        defaultMentalHealthSummaryShouldBeFound("endTime.specified=true");

        // Get all the mentalHealthSummaryList where endTime is null
        defaultMentalHealthSummaryShouldNotBeFound("endTime.specified=false");
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultMentalHealthSummaryShouldBeFound(String filter) throws Exception {
        restMentalHealthSummaryMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mentalHealthSummary.getId().toString())))
            .andExpect(jsonPath("$.[*].usuarioId").value(hasItem(DEFAULT_USUARIO_ID)))
            .andExpect(jsonPath("$.[*].empresaId").value(hasItem(DEFAULT_EMPRESA_ID)))
            .andExpect(jsonPath("$.[*].emotionDescripMain").value(hasItem(DEFAULT_EMOTION_DESCRIP_MAIN)))
            .andExpect(jsonPath("$.[*].emotionDescripSecond").value(hasItem(DEFAULT_EMOTION_DESCRIP_SECOND)))
            .andExpect(jsonPath("$.[*].fieldMentalHealthAverage").value(hasItem(DEFAULT_FIELD_MENTAL_HEALTH_AVERAGE.doubleValue())))
            .andExpect(jsonPath("$.[*].fieldMentalHealthMax").value(hasItem(DEFAULT_FIELD_MENTAL_HEALTH_MAX.doubleValue())))
            .andExpect(jsonPath("$.[*].fieldMentalHealthMin").value(hasItem(DEFAULT_FIELD_MENTAL_HEALTH_MIN.doubleValue())))
            .andExpect(jsonPath("$.[*].scoreMentalRisk").value(hasItem(DEFAULT_SCORE_MENTAL_RISK.doubleValue())))
            .andExpect(jsonPath("$.[*].startTime").value(hasItem(DEFAULT_START_TIME.toString())))
            .andExpect(jsonPath("$.[*].endTime").value(hasItem(DEFAULT_END_TIME.toString())));

        // Check, that the count call also returns 1
        restMentalHealthSummaryMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultMentalHealthSummaryShouldNotBeFound(String filter) throws Exception {
        restMentalHealthSummaryMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restMentalHealthSummaryMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingMentalHealthSummary() throws Exception {
        // Get the mentalHealthSummary
        restMentalHealthSummaryMockMvc.perform(get(ENTITY_API_URL_ID, UUID.randomUUID().toString())).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingMentalHealthSummary() throws Exception {
        // Initialize the database
        mentalHealthSummaryRepository.saveAndFlush(mentalHealthSummary);

        int databaseSizeBeforeUpdate = mentalHealthSummaryRepository.findAll().size();

        // Update the mentalHealthSummary
        MentalHealthSummary updatedMentalHealthSummary = mentalHealthSummaryRepository.findById(mentalHealthSummary.getId()).get();
        // Disconnect from session so that the updates on updatedMentalHealthSummary are not directly saved in db
        em.detach(updatedMentalHealthSummary);
        updatedMentalHealthSummary
            .usuarioId(UPDATED_USUARIO_ID)
            .empresaId(UPDATED_EMPRESA_ID)
            .emotionDescripMain(UPDATED_EMOTION_DESCRIP_MAIN)
            .emotionDescripSecond(UPDATED_EMOTION_DESCRIP_SECOND)
            .fieldMentalHealthAverage(UPDATED_FIELD_MENTAL_HEALTH_AVERAGE)
            .fieldMentalHealthMax(UPDATED_FIELD_MENTAL_HEALTH_MAX)
            .fieldMentalHealthMin(UPDATED_FIELD_MENTAL_HEALTH_MIN)
            .scoreMentalRisk(UPDATED_SCORE_MENTAL_RISK)
            .startTime(UPDATED_START_TIME)
            .endTime(UPDATED_END_TIME);

        restMentalHealthSummaryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedMentalHealthSummary.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedMentalHealthSummary))
            )
            .andExpect(status().isOk());

        // Validate the MentalHealthSummary in the database
        List<MentalHealthSummary> mentalHealthSummaryList = mentalHealthSummaryRepository.findAll();
        assertThat(mentalHealthSummaryList).hasSize(databaseSizeBeforeUpdate);
        MentalHealthSummary testMentalHealthSummary = mentalHealthSummaryList.get(mentalHealthSummaryList.size() - 1);
        assertThat(testMentalHealthSummary.getUsuarioId()).isEqualTo(UPDATED_USUARIO_ID);
        assertThat(testMentalHealthSummary.getEmpresaId()).isEqualTo(UPDATED_EMPRESA_ID);
        assertThat(testMentalHealthSummary.getEmotionDescripMain()).isEqualTo(UPDATED_EMOTION_DESCRIP_MAIN);
        assertThat(testMentalHealthSummary.getEmotionDescripSecond()).isEqualTo(UPDATED_EMOTION_DESCRIP_SECOND);
        assertThat(testMentalHealthSummary.getFieldMentalHealthAverage()).isEqualTo(UPDATED_FIELD_MENTAL_HEALTH_AVERAGE);
        assertThat(testMentalHealthSummary.getFieldMentalHealthMax()).isEqualTo(UPDATED_FIELD_MENTAL_HEALTH_MAX);
        assertThat(testMentalHealthSummary.getFieldMentalHealthMin()).isEqualTo(UPDATED_FIELD_MENTAL_HEALTH_MIN);
        assertThat(testMentalHealthSummary.getScoreMentalRisk()).isEqualTo(UPDATED_SCORE_MENTAL_RISK);
        assertThat(testMentalHealthSummary.getStartTime()).isEqualTo(UPDATED_START_TIME);
        assertThat(testMentalHealthSummary.getEndTime()).isEqualTo(UPDATED_END_TIME);
    }

    @Test
    @Transactional
    void putNonExistingMentalHealthSummary() throws Exception {
        int databaseSizeBeforeUpdate = mentalHealthSummaryRepository.findAll().size();
        mentalHealthSummary.setId(UUID.randomUUID());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMentalHealthSummaryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, mentalHealthSummary.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(mentalHealthSummary))
            )
            .andExpect(status().isBadRequest());

        // Validate the MentalHealthSummary in the database
        List<MentalHealthSummary> mentalHealthSummaryList = mentalHealthSummaryRepository.findAll();
        assertThat(mentalHealthSummaryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchMentalHealthSummary() throws Exception {
        int databaseSizeBeforeUpdate = mentalHealthSummaryRepository.findAll().size();
        mentalHealthSummary.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMentalHealthSummaryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(mentalHealthSummary))
            )
            .andExpect(status().isBadRequest());

        // Validate the MentalHealthSummary in the database
        List<MentalHealthSummary> mentalHealthSummaryList = mentalHealthSummaryRepository.findAll();
        assertThat(mentalHealthSummaryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamMentalHealthSummary() throws Exception {
        int databaseSizeBeforeUpdate = mentalHealthSummaryRepository.findAll().size();
        mentalHealthSummary.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMentalHealthSummaryMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(mentalHealthSummary))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the MentalHealthSummary in the database
        List<MentalHealthSummary> mentalHealthSummaryList = mentalHealthSummaryRepository.findAll();
        assertThat(mentalHealthSummaryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateMentalHealthSummaryWithPatch() throws Exception {
        // Initialize the database
        mentalHealthSummaryRepository.saveAndFlush(mentalHealthSummary);

        int databaseSizeBeforeUpdate = mentalHealthSummaryRepository.findAll().size();

        // Update the mentalHealthSummary using partial update
        MentalHealthSummary partialUpdatedMentalHealthSummary = new MentalHealthSummary();
        partialUpdatedMentalHealthSummary.setId(mentalHealthSummary.getId());

        partialUpdatedMentalHealthSummary
            .emotionDescripMain(UPDATED_EMOTION_DESCRIP_MAIN)
            .emotionDescripSecond(UPDATED_EMOTION_DESCRIP_SECOND)
            .scoreMentalRisk(UPDATED_SCORE_MENTAL_RISK)
            .startTime(UPDATED_START_TIME)
            .endTime(UPDATED_END_TIME);

        restMentalHealthSummaryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMentalHealthSummary.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMentalHealthSummary))
            )
            .andExpect(status().isOk());

        // Validate the MentalHealthSummary in the database
        List<MentalHealthSummary> mentalHealthSummaryList = mentalHealthSummaryRepository.findAll();
        assertThat(mentalHealthSummaryList).hasSize(databaseSizeBeforeUpdate);
        MentalHealthSummary testMentalHealthSummary = mentalHealthSummaryList.get(mentalHealthSummaryList.size() - 1);
        assertThat(testMentalHealthSummary.getUsuarioId()).isEqualTo(DEFAULT_USUARIO_ID);
        assertThat(testMentalHealthSummary.getEmpresaId()).isEqualTo(DEFAULT_EMPRESA_ID);
        assertThat(testMentalHealthSummary.getEmotionDescripMain()).isEqualTo(UPDATED_EMOTION_DESCRIP_MAIN);
        assertThat(testMentalHealthSummary.getEmotionDescripSecond()).isEqualTo(UPDATED_EMOTION_DESCRIP_SECOND);
        assertThat(testMentalHealthSummary.getFieldMentalHealthAverage()).isEqualTo(DEFAULT_FIELD_MENTAL_HEALTH_AVERAGE);
        assertThat(testMentalHealthSummary.getFieldMentalHealthMax()).isEqualTo(DEFAULT_FIELD_MENTAL_HEALTH_MAX);
        assertThat(testMentalHealthSummary.getFieldMentalHealthMin()).isEqualTo(DEFAULT_FIELD_MENTAL_HEALTH_MIN);
        assertThat(testMentalHealthSummary.getScoreMentalRisk()).isEqualTo(UPDATED_SCORE_MENTAL_RISK);
        assertThat(testMentalHealthSummary.getStartTime()).isEqualTo(UPDATED_START_TIME);
        assertThat(testMentalHealthSummary.getEndTime()).isEqualTo(UPDATED_END_TIME);
    }

    @Test
    @Transactional
    void fullUpdateMentalHealthSummaryWithPatch() throws Exception {
        // Initialize the database
        mentalHealthSummaryRepository.saveAndFlush(mentalHealthSummary);

        int databaseSizeBeforeUpdate = mentalHealthSummaryRepository.findAll().size();

        // Update the mentalHealthSummary using partial update
        MentalHealthSummary partialUpdatedMentalHealthSummary = new MentalHealthSummary();
        partialUpdatedMentalHealthSummary.setId(mentalHealthSummary.getId());

        partialUpdatedMentalHealthSummary
            .usuarioId(UPDATED_USUARIO_ID)
            .empresaId(UPDATED_EMPRESA_ID)
            .emotionDescripMain(UPDATED_EMOTION_DESCRIP_MAIN)
            .emotionDescripSecond(UPDATED_EMOTION_DESCRIP_SECOND)
            .fieldMentalHealthAverage(UPDATED_FIELD_MENTAL_HEALTH_AVERAGE)
            .fieldMentalHealthMax(UPDATED_FIELD_MENTAL_HEALTH_MAX)
            .fieldMentalHealthMin(UPDATED_FIELD_MENTAL_HEALTH_MIN)
            .scoreMentalRisk(UPDATED_SCORE_MENTAL_RISK)
            .startTime(UPDATED_START_TIME)
            .endTime(UPDATED_END_TIME);

        restMentalHealthSummaryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMentalHealthSummary.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMentalHealthSummary))
            )
            .andExpect(status().isOk());

        // Validate the MentalHealthSummary in the database
        List<MentalHealthSummary> mentalHealthSummaryList = mentalHealthSummaryRepository.findAll();
        assertThat(mentalHealthSummaryList).hasSize(databaseSizeBeforeUpdate);
        MentalHealthSummary testMentalHealthSummary = mentalHealthSummaryList.get(mentalHealthSummaryList.size() - 1);
        assertThat(testMentalHealthSummary.getUsuarioId()).isEqualTo(UPDATED_USUARIO_ID);
        assertThat(testMentalHealthSummary.getEmpresaId()).isEqualTo(UPDATED_EMPRESA_ID);
        assertThat(testMentalHealthSummary.getEmotionDescripMain()).isEqualTo(UPDATED_EMOTION_DESCRIP_MAIN);
        assertThat(testMentalHealthSummary.getEmotionDescripSecond()).isEqualTo(UPDATED_EMOTION_DESCRIP_SECOND);
        assertThat(testMentalHealthSummary.getFieldMentalHealthAverage()).isEqualTo(UPDATED_FIELD_MENTAL_HEALTH_AVERAGE);
        assertThat(testMentalHealthSummary.getFieldMentalHealthMax()).isEqualTo(UPDATED_FIELD_MENTAL_HEALTH_MAX);
        assertThat(testMentalHealthSummary.getFieldMentalHealthMin()).isEqualTo(UPDATED_FIELD_MENTAL_HEALTH_MIN);
        assertThat(testMentalHealthSummary.getScoreMentalRisk()).isEqualTo(UPDATED_SCORE_MENTAL_RISK);
        assertThat(testMentalHealthSummary.getStartTime()).isEqualTo(UPDATED_START_TIME);
        assertThat(testMentalHealthSummary.getEndTime()).isEqualTo(UPDATED_END_TIME);
    }

    @Test
    @Transactional
    void patchNonExistingMentalHealthSummary() throws Exception {
        int databaseSizeBeforeUpdate = mentalHealthSummaryRepository.findAll().size();
        mentalHealthSummary.setId(UUID.randomUUID());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMentalHealthSummaryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, mentalHealthSummary.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(mentalHealthSummary))
            )
            .andExpect(status().isBadRequest());

        // Validate the MentalHealthSummary in the database
        List<MentalHealthSummary> mentalHealthSummaryList = mentalHealthSummaryRepository.findAll();
        assertThat(mentalHealthSummaryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchMentalHealthSummary() throws Exception {
        int databaseSizeBeforeUpdate = mentalHealthSummaryRepository.findAll().size();
        mentalHealthSummary.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMentalHealthSummaryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(mentalHealthSummary))
            )
            .andExpect(status().isBadRequest());

        // Validate the MentalHealthSummary in the database
        List<MentalHealthSummary> mentalHealthSummaryList = mentalHealthSummaryRepository.findAll();
        assertThat(mentalHealthSummaryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamMentalHealthSummary() throws Exception {
        int databaseSizeBeforeUpdate = mentalHealthSummaryRepository.findAll().size();
        mentalHealthSummary.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMentalHealthSummaryMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(mentalHealthSummary))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the MentalHealthSummary in the database
        List<MentalHealthSummary> mentalHealthSummaryList = mentalHealthSummaryRepository.findAll();
        assertThat(mentalHealthSummaryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteMentalHealthSummary() throws Exception {
        // Initialize the database
        mentalHealthSummaryRepository.saveAndFlush(mentalHealthSummary);

        int databaseSizeBeforeDelete = mentalHealthSummaryRepository.findAll().size();

        // Delete the mentalHealthSummary
        restMentalHealthSummaryMockMvc
            .perform(delete(ENTITY_API_URL_ID, mentalHealthSummary.getId().toString()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MentalHealthSummary> mentalHealthSummaryList = mentalHealthSummaryRepository.findAll();
        assertThat(mentalHealthSummaryList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
