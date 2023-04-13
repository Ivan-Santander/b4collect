package com.be4tech.b4carecollect.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.be4tech.b4carecollect.IntegrationTest;
import com.be4tech.b4carecollect.domain.MentalHealth;
import com.be4tech.b4carecollect.repository.MentalHealthRepository;
import com.be4tech.b4carecollect.service.criteria.MentalHealthCriteria;
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
 * Integration tests for the {@link MentalHealthResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class MentalHealthResourceIT {

    private static final String DEFAULT_USUARIO_ID = "AAAAAAAAAA";
    private static final String UPDATED_USUARIO_ID = "BBBBBBBBBB";

    private static final String DEFAULT_EMPRESA_ID = "AAAAAAAAAA";
    private static final String UPDATED_EMPRESA_ID = "BBBBBBBBBB";

    private static final String DEFAULT_EMOTION_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_EMOTION_DESCRIPTION = "BBBBBBBBBB";

    private static final Float DEFAULT_EMOTION_VALUE = 1F;
    private static final Float UPDATED_EMOTION_VALUE = 2F;
    private static final Float SMALLER_EMOTION_VALUE = 1F - 1F;

    private static final Instant DEFAULT_START_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_START_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_END_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_END_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Float DEFAULT_MENTAL_HEALTH_SCORE = 1F;
    private static final Float UPDATED_MENTAL_HEALTH_SCORE = 2F;
    private static final Float SMALLER_MENTAL_HEALTH_SCORE = 1F - 1F;

    private static final String ENTITY_API_URL = "/api/mental-healths";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private MentalHealthRepository mentalHealthRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMentalHealthMockMvc;

    private MentalHealth mentalHealth;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MentalHealth createEntity(EntityManager em) {
        MentalHealth mentalHealth = new MentalHealth()
            .usuarioId(DEFAULT_USUARIO_ID)
            .empresaId(DEFAULT_EMPRESA_ID)
            .emotionDescription(DEFAULT_EMOTION_DESCRIPTION)
            .emotionValue(DEFAULT_EMOTION_VALUE)
            .startDate(DEFAULT_START_DATE)
            .endDate(DEFAULT_END_DATE)
            .mentalHealthScore(DEFAULT_MENTAL_HEALTH_SCORE);
        return mentalHealth;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MentalHealth createUpdatedEntity(EntityManager em) {
        MentalHealth mentalHealth = new MentalHealth()
            .usuarioId(UPDATED_USUARIO_ID)
            .empresaId(UPDATED_EMPRESA_ID)
            .emotionDescription(UPDATED_EMOTION_DESCRIPTION)
            .emotionValue(UPDATED_EMOTION_VALUE)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .mentalHealthScore(UPDATED_MENTAL_HEALTH_SCORE);
        return mentalHealth;
    }

    @BeforeEach
    public void initTest() {
        mentalHealth = createEntity(em);
    }

    @Test
    @Transactional
    void createMentalHealth() throws Exception {
        int databaseSizeBeforeCreate = mentalHealthRepository.findAll().size();
        // Create the MentalHealth
        restMentalHealthMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(mentalHealth)))
            .andExpect(status().isCreated());

        // Validate the MentalHealth in the database
        List<MentalHealth> mentalHealthList = mentalHealthRepository.findAll();
        assertThat(mentalHealthList).hasSize(databaseSizeBeforeCreate + 1);
        MentalHealth testMentalHealth = mentalHealthList.get(mentalHealthList.size() - 1);
        assertThat(testMentalHealth.getUsuarioId()).isEqualTo(DEFAULT_USUARIO_ID);
        assertThat(testMentalHealth.getEmpresaId()).isEqualTo(DEFAULT_EMPRESA_ID);
        assertThat(testMentalHealth.getEmotionDescription()).isEqualTo(DEFAULT_EMOTION_DESCRIPTION);
        assertThat(testMentalHealth.getEmotionValue()).isEqualTo(DEFAULT_EMOTION_VALUE);
        assertThat(testMentalHealth.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testMentalHealth.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testMentalHealth.getMentalHealthScore()).isEqualTo(DEFAULT_MENTAL_HEALTH_SCORE);
    }

    @Test
    @Transactional
    void createMentalHealthWithExistingId() throws Exception {
        // Create the MentalHealth with an existing ID
        mentalHealthRepository.saveAndFlush(mentalHealth);

        int databaseSizeBeforeCreate = mentalHealthRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMentalHealthMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(mentalHealth)))
            .andExpect(status().isBadRequest());

        // Validate the MentalHealth in the database
        List<MentalHealth> mentalHealthList = mentalHealthRepository.findAll();
        assertThat(mentalHealthList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllMentalHealths() throws Exception {
        // Initialize the database
        mentalHealthRepository.saveAndFlush(mentalHealth);

        // Get all the mentalHealthList
        restMentalHealthMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mentalHealth.getId().toString())))
            .andExpect(jsonPath("$.[*].usuarioId").value(hasItem(DEFAULT_USUARIO_ID)))
            .andExpect(jsonPath("$.[*].empresaId").value(hasItem(DEFAULT_EMPRESA_ID)))
            .andExpect(jsonPath("$.[*].emotionDescription").value(hasItem(DEFAULT_EMOTION_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].emotionValue").value(hasItem(DEFAULT_EMOTION_VALUE.doubleValue())))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].mentalHealthScore").value(hasItem(DEFAULT_MENTAL_HEALTH_SCORE.doubleValue())));
    }

    @Test
    @Transactional
    void getMentalHealth() throws Exception {
        // Initialize the database
        mentalHealthRepository.saveAndFlush(mentalHealth);

        // Get the mentalHealth
        restMentalHealthMockMvc
            .perform(get(ENTITY_API_URL_ID, mentalHealth.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(mentalHealth.getId().toString()))
            .andExpect(jsonPath("$.usuarioId").value(DEFAULT_USUARIO_ID))
            .andExpect(jsonPath("$.empresaId").value(DEFAULT_EMPRESA_ID))
            .andExpect(jsonPath("$.emotionDescription").value(DEFAULT_EMOTION_DESCRIPTION))
            .andExpect(jsonPath("$.emotionValue").value(DEFAULT_EMOTION_VALUE.doubleValue()))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE.toString()))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE.toString()))
            .andExpect(jsonPath("$.mentalHealthScore").value(DEFAULT_MENTAL_HEALTH_SCORE.doubleValue()));
    }

    @Test
    @Transactional
    void getMentalHealthsByIdFiltering() throws Exception {
        // Initialize the database
        mentalHealthRepository.saveAndFlush(mentalHealth);

        UUID id = mentalHealth.getId();

        defaultMentalHealthShouldBeFound("id.equals=" + id);
        defaultMentalHealthShouldNotBeFound("id.notEquals=" + id);
    }

    @Test
    @Transactional
    void getAllMentalHealthsByUsuarioIdIsEqualToSomething() throws Exception {
        // Initialize the database
        mentalHealthRepository.saveAndFlush(mentalHealth);

        // Get all the mentalHealthList where usuarioId equals to DEFAULT_USUARIO_ID
        defaultMentalHealthShouldBeFound("usuarioId.equals=" + DEFAULT_USUARIO_ID);

        // Get all the mentalHealthList where usuarioId equals to UPDATED_USUARIO_ID
        defaultMentalHealthShouldNotBeFound("usuarioId.equals=" + UPDATED_USUARIO_ID);
    }

    @Test
    @Transactional
    void getAllMentalHealthsByUsuarioIdIsInShouldWork() throws Exception {
        // Initialize the database
        mentalHealthRepository.saveAndFlush(mentalHealth);

        // Get all the mentalHealthList where usuarioId in DEFAULT_USUARIO_ID or UPDATED_USUARIO_ID
        defaultMentalHealthShouldBeFound("usuarioId.in=" + DEFAULT_USUARIO_ID + "," + UPDATED_USUARIO_ID);

        // Get all the mentalHealthList where usuarioId equals to UPDATED_USUARIO_ID
        defaultMentalHealthShouldNotBeFound("usuarioId.in=" + UPDATED_USUARIO_ID);
    }

    @Test
    @Transactional
    void getAllMentalHealthsByUsuarioIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        mentalHealthRepository.saveAndFlush(mentalHealth);

        // Get all the mentalHealthList where usuarioId is not null
        defaultMentalHealthShouldBeFound("usuarioId.specified=true");

        // Get all the mentalHealthList where usuarioId is null
        defaultMentalHealthShouldNotBeFound("usuarioId.specified=false");
    }

    @Test
    @Transactional
    void getAllMentalHealthsByUsuarioIdContainsSomething() throws Exception {
        // Initialize the database
        mentalHealthRepository.saveAndFlush(mentalHealth);

        // Get all the mentalHealthList where usuarioId contains DEFAULT_USUARIO_ID
        defaultMentalHealthShouldBeFound("usuarioId.contains=" + DEFAULT_USUARIO_ID);

        // Get all the mentalHealthList where usuarioId contains UPDATED_USUARIO_ID
        defaultMentalHealthShouldNotBeFound("usuarioId.contains=" + UPDATED_USUARIO_ID);
    }

    @Test
    @Transactional
    void getAllMentalHealthsByUsuarioIdNotContainsSomething() throws Exception {
        // Initialize the database
        mentalHealthRepository.saveAndFlush(mentalHealth);

        // Get all the mentalHealthList where usuarioId does not contain DEFAULT_USUARIO_ID
        defaultMentalHealthShouldNotBeFound("usuarioId.doesNotContain=" + DEFAULT_USUARIO_ID);

        // Get all the mentalHealthList where usuarioId does not contain UPDATED_USUARIO_ID
        defaultMentalHealthShouldBeFound("usuarioId.doesNotContain=" + UPDATED_USUARIO_ID);
    }

    @Test
    @Transactional
    void getAllMentalHealthsByEmpresaIdIsEqualToSomething() throws Exception {
        // Initialize the database
        mentalHealthRepository.saveAndFlush(mentalHealth);

        // Get all the mentalHealthList where empresaId equals to DEFAULT_EMPRESA_ID
        defaultMentalHealthShouldBeFound("empresaId.equals=" + DEFAULT_EMPRESA_ID);

        // Get all the mentalHealthList where empresaId equals to UPDATED_EMPRESA_ID
        defaultMentalHealthShouldNotBeFound("empresaId.equals=" + UPDATED_EMPRESA_ID);
    }

    @Test
    @Transactional
    void getAllMentalHealthsByEmpresaIdIsInShouldWork() throws Exception {
        // Initialize the database
        mentalHealthRepository.saveAndFlush(mentalHealth);

        // Get all the mentalHealthList where empresaId in DEFAULT_EMPRESA_ID or UPDATED_EMPRESA_ID
        defaultMentalHealthShouldBeFound("empresaId.in=" + DEFAULT_EMPRESA_ID + "," + UPDATED_EMPRESA_ID);

        // Get all the mentalHealthList where empresaId equals to UPDATED_EMPRESA_ID
        defaultMentalHealthShouldNotBeFound("empresaId.in=" + UPDATED_EMPRESA_ID);
    }

    @Test
    @Transactional
    void getAllMentalHealthsByEmpresaIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        mentalHealthRepository.saveAndFlush(mentalHealth);

        // Get all the mentalHealthList where empresaId is not null
        defaultMentalHealthShouldBeFound("empresaId.specified=true");

        // Get all the mentalHealthList where empresaId is null
        defaultMentalHealthShouldNotBeFound("empresaId.specified=false");
    }

    @Test
    @Transactional
    void getAllMentalHealthsByEmpresaIdContainsSomething() throws Exception {
        // Initialize the database
        mentalHealthRepository.saveAndFlush(mentalHealth);

        // Get all the mentalHealthList where empresaId contains DEFAULT_EMPRESA_ID
        defaultMentalHealthShouldBeFound("empresaId.contains=" + DEFAULT_EMPRESA_ID);

        // Get all the mentalHealthList where empresaId contains UPDATED_EMPRESA_ID
        defaultMentalHealthShouldNotBeFound("empresaId.contains=" + UPDATED_EMPRESA_ID);
    }

    @Test
    @Transactional
    void getAllMentalHealthsByEmpresaIdNotContainsSomething() throws Exception {
        // Initialize the database
        mentalHealthRepository.saveAndFlush(mentalHealth);

        // Get all the mentalHealthList where empresaId does not contain DEFAULT_EMPRESA_ID
        defaultMentalHealthShouldNotBeFound("empresaId.doesNotContain=" + DEFAULT_EMPRESA_ID);

        // Get all the mentalHealthList where empresaId does not contain UPDATED_EMPRESA_ID
        defaultMentalHealthShouldBeFound("empresaId.doesNotContain=" + UPDATED_EMPRESA_ID);
    }

    @Test
    @Transactional
    void getAllMentalHealthsByEmotionDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        mentalHealthRepository.saveAndFlush(mentalHealth);

        // Get all the mentalHealthList where emotionDescription equals to DEFAULT_EMOTION_DESCRIPTION
        defaultMentalHealthShouldBeFound("emotionDescription.equals=" + DEFAULT_EMOTION_DESCRIPTION);

        // Get all the mentalHealthList where emotionDescription equals to UPDATED_EMOTION_DESCRIPTION
        defaultMentalHealthShouldNotBeFound("emotionDescription.equals=" + UPDATED_EMOTION_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllMentalHealthsByEmotionDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        mentalHealthRepository.saveAndFlush(mentalHealth);

        // Get all the mentalHealthList where emotionDescription in DEFAULT_EMOTION_DESCRIPTION or UPDATED_EMOTION_DESCRIPTION
        defaultMentalHealthShouldBeFound("emotionDescription.in=" + DEFAULT_EMOTION_DESCRIPTION + "," + UPDATED_EMOTION_DESCRIPTION);

        // Get all the mentalHealthList where emotionDescription equals to UPDATED_EMOTION_DESCRIPTION
        defaultMentalHealthShouldNotBeFound("emotionDescription.in=" + UPDATED_EMOTION_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllMentalHealthsByEmotionDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        mentalHealthRepository.saveAndFlush(mentalHealth);

        // Get all the mentalHealthList where emotionDescription is not null
        defaultMentalHealthShouldBeFound("emotionDescription.specified=true");

        // Get all the mentalHealthList where emotionDescription is null
        defaultMentalHealthShouldNotBeFound("emotionDescription.specified=false");
    }

    @Test
    @Transactional
    void getAllMentalHealthsByEmotionDescriptionContainsSomething() throws Exception {
        // Initialize the database
        mentalHealthRepository.saveAndFlush(mentalHealth);

        // Get all the mentalHealthList where emotionDescription contains DEFAULT_EMOTION_DESCRIPTION
        defaultMentalHealthShouldBeFound("emotionDescription.contains=" + DEFAULT_EMOTION_DESCRIPTION);

        // Get all the mentalHealthList where emotionDescription contains UPDATED_EMOTION_DESCRIPTION
        defaultMentalHealthShouldNotBeFound("emotionDescription.contains=" + UPDATED_EMOTION_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllMentalHealthsByEmotionDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        mentalHealthRepository.saveAndFlush(mentalHealth);

        // Get all the mentalHealthList where emotionDescription does not contain DEFAULT_EMOTION_DESCRIPTION
        defaultMentalHealthShouldNotBeFound("emotionDescription.doesNotContain=" + DEFAULT_EMOTION_DESCRIPTION);

        // Get all the mentalHealthList where emotionDescription does not contain UPDATED_EMOTION_DESCRIPTION
        defaultMentalHealthShouldBeFound("emotionDescription.doesNotContain=" + UPDATED_EMOTION_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllMentalHealthsByEmotionValueIsEqualToSomething() throws Exception {
        // Initialize the database
        mentalHealthRepository.saveAndFlush(mentalHealth);

        // Get all the mentalHealthList where emotionValue equals to DEFAULT_EMOTION_VALUE
        defaultMentalHealthShouldBeFound("emotionValue.equals=" + DEFAULT_EMOTION_VALUE);

        // Get all the mentalHealthList where emotionValue equals to UPDATED_EMOTION_VALUE
        defaultMentalHealthShouldNotBeFound("emotionValue.equals=" + UPDATED_EMOTION_VALUE);
    }

    @Test
    @Transactional
    void getAllMentalHealthsByEmotionValueIsInShouldWork() throws Exception {
        // Initialize the database
        mentalHealthRepository.saveAndFlush(mentalHealth);

        // Get all the mentalHealthList where emotionValue in DEFAULT_EMOTION_VALUE or UPDATED_EMOTION_VALUE
        defaultMentalHealthShouldBeFound("emotionValue.in=" + DEFAULT_EMOTION_VALUE + "," + UPDATED_EMOTION_VALUE);

        // Get all the mentalHealthList where emotionValue equals to UPDATED_EMOTION_VALUE
        defaultMentalHealthShouldNotBeFound("emotionValue.in=" + UPDATED_EMOTION_VALUE);
    }

    @Test
    @Transactional
    void getAllMentalHealthsByEmotionValueIsNullOrNotNull() throws Exception {
        // Initialize the database
        mentalHealthRepository.saveAndFlush(mentalHealth);

        // Get all the mentalHealthList where emotionValue is not null
        defaultMentalHealthShouldBeFound("emotionValue.specified=true");

        // Get all the mentalHealthList where emotionValue is null
        defaultMentalHealthShouldNotBeFound("emotionValue.specified=false");
    }

    @Test
    @Transactional
    void getAllMentalHealthsByEmotionValueIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mentalHealthRepository.saveAndFlush(mentalHealth);

        // Get all the mentalHealthList where emotionValue is greater than or equal to DEFAULT_EMOTION_VALUE
        defaultMentalHealthShouldBeFound("emotionValue.greaterThanOrEqual=" + DEFAULT_EMOTION_VALUE);

        // Get all the mentalHealthList where emotionValue is greater than or equal to UPDATED_EMOTION_VALUE
        defaultMentalHealthShouldNotBeFound("emotionValue.greaterThanOrEqual=" + UPDATED_EMOTION_VALUE);
    }

    @Test
    @Transactional
    void getAllMentalHealthsByEmotionValueIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mentalHealthRepository.saveAndFlush(mentalHealth);

        // Get all the mentalHealthList where emotionValue is less than or equal to DEFAULT_EMOTION_VALUE
        defaultMentalHealthShouldBeFound("emotionValue.lessThanOrEqual=" + DEFAULT_EMOTION_VALUE);

        // Get all the mentalHealthList where emotionValue is less than or equal to SMALLER_EMOTION_VALUE
        defaultMentalHealthShouldNotBeFound("emotionValue.lessThanOrEqual=" + SMALLER_EMOTION_VALUE);
    }

    @Test
    @Transactional
    void getAllMentalHealthsByEmotionValueIsLessThanSomething() throws Exception {
        // Initialize the database
        mentalHealthRepository.saveAndFlush(mentalHealth);

        // Get all the mentalHealthList where emotionValue is less than DEFAULT_EMOTION_VALUE
        defaultMentalHealthShouldNotBeFound("emotionValue.lessThan=" + DEFAULT_EMOTION_VALUE);

        // Get all the mentalHealthList where emotionValue is less than UPDATED_EMOTION_VALUE
        defaultMentalHealthShouldBeFound("emotionValue.lessThan=" + UPDATED_EMOTION_VALUE);
    }

    @Test
    @Transactional
    void getAllMentalHealthsByEmotionValueIsGreaterThanSomething() throws Exception {
        // Initialize the database
        mentalHealthRepository.saveAndFlush(mentalHealth);

        // Get all the mentalHealthList where emotionValue is greater than DEFAULT_EMOTION_VALUE
        defaultMentalHealthShouldNotBeFound("emotionValue.greaterThan=" + DEFAULT_EMOTION_VALUE);

        // Get all the mentalHealthList where emotionValue is greater than SMALLER_EMOTION_VALUE
        defaultMentalHealthShouldBeFound("emotionValue.greaterThan=" + SMALLER_EMOTION_VALUE);
    }

    @Test
    @Transactional
    void getAllMentalHealthsByStartDateIsEqualToSomething() throws Exception {
        // Initialize the database
        mentalHealthRepository.saveAndFlush(mentalHealth);

        // Get all the mentalHealthList where startDate equals to DEFAULT_START_DATE
        defaultMentalHealthShouldBeFound("startDate.equals=" + DEFAULT_START_DATE);

        // Get all the mentalHealthList where startDate equals to UPDATED_START_DATE
        defaultMentalHealthShouldNotBeFound("startDate.equals=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    void getAllMentalHealthsByStartDateIsInShouldWork() throws Exception {
        // Initialize the database
        mentalHealthRepository.saveAndFlush(mentalHealth);

        // Get all the mentalHealthList where startDate in DEFAULT_START_DATE or UPDATED_START_DATE
        defaultMentalHealthShouldBeFound("startDate.in=" + DEFAULT_START_DATE + "," + UPDATED_START_DATE);

        // Get all the mentalHealthList where startDate equals to UPDATED_START_DATE
        defaultMentalHealthShouldNotBeFound("startDate.in=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    void getAllMentalHealthsByStartDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        mentalHealthRepository.saveAndFlush(mentalHealth);

        // Get all the mentalHealthList where startDate is not null
        defaultMentalHealthShouldBeFound("startDate.specified=true");

        // Get all the mentalHealthList where startDate is null
        defaultMentalHealthShouldNotBeFound("startDate.specified=false");
    }

    @Test
    @Transactional
    void getAllMentalHealthsByEndDateIsEqualToSomething() throws Exception {
        // Initialize the database
        mentalHealthRepository.saveAndFlush(mentalHealth);

        // Get all the mentalHealthList where endDate equals to DEFAULT_END_DATE
        defaultMentalHealthShouldBeFound("endDate.equals=" + DEFAULT_END_DATE);

        // Get all the mentalHealthList where endDate equals to UPDATED_END_DATE
        defaultMentalHealthShouldNotBeFound("endDate.equals=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    void getAllMentalHealthsByEndDateIsInShouldWork() throws Exception {
        // Initialize the database
        mentalHealthRepository.saveAndFlush(mentalHealth);

        // Get all the mentalHealthList where endDate in DEFAULT_END_DATE or UPDATED_END_DATE
        defaultMentalHealthShouldBeFound("endDate.in=" + DEFAULT_END_DATE + "," + UPDATED_END_DATE);

        // Get all the mentalHealthList where endDate equals to UPDATED_END_DATE
        defaultMentalHealthShouldNotBeFound("endDate.in=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    void getAllMentalHealthsByEndDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        mentalHealthRepository.saveAndFlush(mentalHealth);

        // Get all the mentalHealthList where endDate is not null
        defaultMentalHealthShouldBeFound("endDate.specified=true");

        // Get all the mentalHealthList where endDate is null
        defaultMentalHealthShouldNotBeFound("endDate.specified=false");
    }

    @Test
    @Transactional
    void getAllMentalHealthsByMentalHealthScoreIsEqualToSomething() throws Exception {
        // Initialize the database
        mentalHealthRepository.saveAndFlush(mentalHealth);

        // Get all the mentalHealthList where mentalHealthScore equals to DEFAULT_MENTAL_HEALTH_SCORE
        defaultMentalHealthShouldBeFound("mentalHealthScore.equals=" + DEFAULT_MENTAL_HEALTH_SCORE);

        // Get all the mentalHealthList where mentalHealthScore equals to UPDATED_MENTAL_HEALTH_SCORE
        defaultMentalHealthShouldNotBeFound("mentalHealthScore.equals=" + UPDATED_MENTAL_HEALTH_SCORE);
    }

    @Test
    @Transactional
    void getAllMentalHealthsByMentalHealthScoreIsInShouldWork() throws Exception {
        // Initialize the database
        mentalHealthRepository.saveAndFlush(mentalHealth);

        // Get all the mentalHealthList where mentalHealthScore in DEFAULT_MENTAL_HEALTH_SCORE or UPDATED_MENTAL_HEALTH_SCORE
        defaultMentalHealthShouldBeFound("mentalHealthScore.in=" + DEFAULT_MENTAL_HEALTH_SCORE + "," + UPDATED_MENTAL_HEALTH_SCORE);

        // Get all the mentalHealthList where mentalHealthScore equals to UPDATED_MENTAL_HEALTH_SCORE
        defaultMentalHealthShouldNotBeFound("mentalHealthScore.in=" + UPDATED_MENTAL_HEALTH_SCORE);
    }

    @Test
    @Transactional
    void getAllMentalHealthsByMentalHealthScoreIsNullOrNotNull() throws Exception {
        // Initialize the database
        mentalHealthRepository.saveAndFlush(mentalHealth);

        // Get all the mentalHealthList where mentalHealthScore is not null
        defaultMentalHealthShouldBeFound("mentalHealthScore.specified=true");

        // Get all the mentalHealthList where mentalHealthScore is null
        defaultMentalHealthShouldNotBeFound("mentalHealthScore.specified=false");
    }

    @Test
    @Transactional
    void getAllMentalHealthsByMentalHealthScoreIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mentalHealthRepository.saveAndFlush(mentalHealth);

        // Get all the mentalHealthList where mentalHealthScore is greater than or equal to DEFAULT_MENTAL_HEALTH_SCORE
        defaultMentalHealthShouldBeFound("mentalHealthScore.greaterThanOrEqual=" + DEFAULT_MENTAL_HEALTH_SCORE);

        // Get all the mentalHealthList where mentalHealthScore is greater than or equal to UPDATED_MENTAL_HEALTH_SCORE
        defaultMentalHealthShouldNotBeFound("mentalHealthScore.greaterThanOrEqual=" + UPDATED_MENTAL_HEALTH_SCORE);
    }

    @Test
    @Transactional
    void getAllMentalHealthsByMentalHealthScoreIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mentalHealthRepository.saveAndFlush(mentalHealth);

        // Get all the mentalHealthList where mentalHealthScore is less than or equal to DEFAULT_MENTAL_HEALTH_SCORE
        defaultMentalHealthShouldBeFound("mentalHealthScore.lessThanOrEqual=" + DEFAULT_MENTAL_HEALTH_SCORE);

        // Get all the mentalHealthList where mentalHealthScore is less than or equal to SMALLER_MENTAL_HEALTH_SCORE
        defaultMentalHealthShouldNotBeFound("mentalHealthScore.lessThanOrEqual=" + SMALLER_MENTAL_HEALTH_SCORE);
    }

    @Test
    @Transactional
    void getAllMentalHealthsByMentalHealthScoreIsLessThanSomething() throws Exception {
        // Initialize the database
        mentalHealthRepository.saveAndFlush(mentalHealth);

        // Get all the mentalHealthList where mentalHealthScore is less than DEFAULT_MENTAL_HEALTH_SCORE
        defaultMentalHealthShouldNotBeFound("mentalHealthScore.lessThan=" + DEFAULT_MENTAL_HEALTH_SCORE);

        // Get all the mentalHealthList where mentalHealthScore is less than UPDATED_MENTAL_HEALTH_SCORE
        defaultMentalHealthShouldBeFound("mentalHealthScore.lessThan=" + UPDATED_MENTAL_HEALTH_SCORE);
    }

    @Test
    @Transactional
    void getAllMentalHealthsByMentalHealthScoreIsGreaterThanSomething() throws Exception {
        // Initialize the database
        mentalHealthRepository.saveAndFlush(mentalHealth);

        // Get all the mentalHealthList where mentalHealthScore is greater than DEFAULT_MENTAL_HEALTH_SCORE
        defaultMentalHealthShouldNotBeFound("mentalHealthScore.greaterThan=" + DEFAULT_MENTAL_HEALTH_SCORE);

        // Get all the mentalHealthList where mentalHealthScore is greater than SMALLER_MENTAL_HEALTH_SCORE
        defaultMentalHealthShouldBeFound("mentalHealthScore.greaterThan=" + SMALLER_MENTAL_HEALTH_SCORE);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultMentalHealthShouldBeFound(String filter) throws Exception {
        restMentalHealthMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mentalHealth.getId().toString())))
            .andExpect(jsonPath("$.[*].usuarioId").value(hasItem(DEFAULT_USUARIO_ID)))
            .andExpect(jsonPath("$.[*].empresaId").value(hasItem(DEFAULT_EMPRESA_ID)))
            .andExpect(jsonPath("$.[*].emotionDescription").value(hasItem(DEFAULT_EMOTION_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].emotionValue").value(hasItem(DEFAULT_EMOTION_VALUE.doubleValue())))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].mentalHealthScore").value(hasItem(DEFAULT_MENTAL_HEALTH_SCORE.doubleValue())));

        // Check, that the count call also returns 1
        restMentalHealthMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultMentalHealthShouldNotBeFound(String filter) throws Exception {
        restMentalHealthMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restMentalHealthMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingMentalHealth() throws Exception {
        // Get the mentalHealth
        restMentalHealthMockMvc.perform(get(ENTITY_API_URL_ID, UUID.randomUUID().toString())).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingMentalHealth() throws Exception {
        // Initialize the database
        mentalHealthRepository.saveAndFlush(mentalHealth);

        int databaseSizeBeforeUpdate = mentalHealthRepository.findAll().size();

        // Update the mentalHealth
        MentalHealth updatedMentalHealth = mentalHealthRepository.findById(mentalHealth.getId()).get();
        // Disconnect from session so that the updates on updatedMentalHealth are not directly saved in db
        em.detach(updatedMentalHealth);
        updatedMentalHealth
            .usuarioId(UPDATED_USUARIO_ID)
            .empresaId(UPDATED_EMPRESA_ID)
            .emotionDescription(UPDATED_EMOTION_DESCRIPTION)
            .emotionValue(UPDATED_EMOTION_VALUE)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .mentalHealthScore(UPDATED_MENTAL_HEALTH_SCORE);

        restMentalHealthMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedMentalHealth.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedMentalHealth))
            )
            .andExpect(status().isOk());

        // Validate the MentalHealth in the database
        List<MentalHealth> mentalHealthList = mentalHealthRepository.findAll();
        assertThat(mentalHealthList).hasSize(databaseSizeBeforeUpdate);
        MentalHealth testMentalHealth = mentalHealthList.get(mentalHealthList.size() - 1);
        assertThat(testMentalHealth.getUsuarioId()).isEqualTo(UPDATED_USUARIO_ID);
        assertThat(testMentalHealth.getEmpresaId()).isEqualTo(UPDATED_EMPRESA_ID);
        assertThat(testMentalHealth.getEmotionDescription()).isEqualTo(UPDATED_EMOTION_DESCRIPTION);
        assertThat(testMentalHealth.getEmotionValue()).isEqualTo(UPDATED_EMOTION_VALUE);
        assertThat(testMentalHealth.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testMentalHealth.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testMentalHealth.getMentalHealthScore()).isEqualTo(UPDATED_MENTAL_HEALTH_SCORE);
    }

    @Test
    @Transactional
    void putNonExistingMentalHealth() throws Exception {
        int databaseSizeBeforeUpdate = mentalHealthRepository.findAll().size();
        mentalHealth.setId(UUID.randomUUID());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMentalHealthMockMvc
            .perform(
                put(ENTITY_API_URL_ID, mentalHealth.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(mentalHealth))
            )
            .andExpect(status().isBadRequest());

        // Validate the MentalHealth in the database
        List<MentalHealth> mentalHealthList = mentalHealthRepository.findAll();
        assertThat(mentalHealthList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchMentalHealth() throws Exception {
        int databaseSizeBeforeUpdate = mentalHealthRepository.findAll().size();
        mentalHealth.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMentalHealthMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(mentalHealth))
            )
            .andExpect(status().isBadRequest());

        // Validate the MentalHealth in the database
        List<MentalHealth> mentalHealthList = mentalHealthRepository.findAll();
        assertThat(mentalHealthList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamMentalHealth() throws Exception {
        int databaseSizeBeforeUpdate = mentalHealthRepository.findAll().size();
        mentalHealth.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMentalHealthMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(mentalHealth)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the MentalHealth in the database
        List<MentalHealth> mentalHealthList = mentalHealthRepository.findAll();
        assertThat(mentalHealthList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateMentalHealthWithPatch() throws Exception {
        // Initialize the database
        mentalHealthRepository.saveAndFlush(mentalHealth);

        int databaseSizeBeforeUpdate = mentalHealthRepository.findAll().size();

        // Update the mentalHealth using partial update
        MentalHealth partialUpdatedMentalHealth = new MentalHealth();
        partialUpdatedMentalHealth.setId(mentalHealth.getId());

        partialUpdatedMentalHealth
            .usuarioId(UPDATED_USUARIO_ID)
            .empresaId(UPDATED_EMPRESA_ID)
            .emotionValue(UPDATED_EMOTION_VALUE)
            .endDate(UPDATED_END_DATE)
            .mentalHealthScore(UPDATED_MENTAL_HEALTH_SCORE);

        restMentalHealthMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMentalHealth.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMentalHealth))
            )
            .andExpect(status().isOk());

        // Validate the MentalHealth in the database
        List<MentalHealth> mentalHealthList = mentalHealthRepository.findAll();
        assertThat(mentalHealthList).hasSize(databaseSizeBeforeUpdate);
        MentalHealth testMentalHealth = mentalHealthList.get(mentalHealthList.size() - 1);
        assertThat(testMentalHealth.getUsuarioId()).isEqualTo(UPDATED_USUARIO_ID);
        assertThat(testMentalHealth.getEmpresaId()).isEqualTo(UPDATED_EMPRESA_ID);
        assertThat(testMentalHealth.getEmotionDescription()).isEqualTo(DEFAULT_EMOTION_DESCRIPTION);
        assertThat(testMentalHealth.getEmotionValue()).isEqualTo(UPDATED_EMOTION_VALUE);
        assertThat(testMentalHealth.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testMentalHealth.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testMentalHealth.getMentalHealthScore()).isEqualTo(UPDATED_MENTAL_HEALTH_SCORE);
    }

    @Test
    @Transactional
    void fullUpdateMentalHealthWithPatch() throws Exception {
        // Initialize the database
        mentalHealthRepository.saveAndFlush(mentalHealth);

        int databaseSizeBeforeUpdate = mentalHealthRepository.findAll().size();

        // Update the mentalHealth using partial update
        MentalHealth partialUpdatedMentalHealth = new MentalHealth();
        partialUpdatedMentalHealth.setId(mentalHealth.getId());

        partialUpdatedMentalHealth
            .usuarioId(UPDATED_USUARIO_ID)
            .empresaId(UPDATED_EMPRESA_ID)
            .emotionDescription(UPDATED_EMOTION_DESCRIPTION)
            .emotionValue(UPDATED_EMOTION_VALUE)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .mentalHealthScore(UPDATED_MENTAL_HEALTH_SCORE);

        restMentalHealthMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMentalHealth.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMentalHealth))
            )
            .andExpect(status().isOk());

        // Validate the MentalHealth in the database
        List<MentalHealth> mentalHealthList = mentalHealthRepository.findAll();
        assertThat(mentalHealthList).hasSize(databaseSizeBeforeUpdate);
        MentalHealth testMentalHealth = mentalHealthList.get(mentalHealthList.size() - 1);
        assertThat(testMentalHealth.getUsuarioId()).isEqualTo(UPDATED_USUARIO_ID);
        assertThat(testMentalHealth.getEmpresaId()).isEqualTo(UPDATED_EMPRESA_ID);
        assertThat(testMentalHealth.getEmotionDescription()).isEqualTo(UPDATED_EMOTION_DESCRIPTION);
        assertThat(testMentalHealth.getEmotionValue()).isEqualTo(UPDATED_EMOTION_VALUE);
        assertThat(testMentalHealth.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testMentalHealth.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testMentalHealth.getMentalHealthScore()).isEqualTo(UPDATED_MENTAL_HEALTH_SCORE);
    }

    @Test
    @Transactional
    void patchNonExistingMentalHealth() throws Exception {
        int databaseSizeBeforeUpdate = mentalHealthRepository.findAll().size();
        mentalHealth.setId(UUID.randomUUID());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMentalHealthMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, mentalHealth.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(mentalHealth))
            )
            .andExpect(status().isBadRequest());

        // Validate the MentalHealth in the database
        List<MentalHealth> mentalHealthList = mentalHealthRepository.findAll();
        assertThat(mentalHealthList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchMentalHealth() throws Exception {
        int databaseSizeBeforeUpdate = mentalHealthRepository.findAll().size();
        mentalHealth.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMentalHealthMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(mentalHealth))
            )
            .andExpect(status().isBadRequest());

        // Validate the MentalHealth in the database
        List<MentalHealth> mentalHealthList = mentalHealthRepository.findAll();
        assertThat(mentalHealthList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamMentalHealth() throws Exception {
        int databaseSizeBeforeUpdate = mentalHealthRepository.findAll().size();
        mentalHealth.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMentalHealthMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(mentalHealth))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the MentalHealth in the database
        List<MentalHealth> mentalHealthList = mentalHealthRepository.findAll();
        assertThat(mentalHealthList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteMentalHealth() throws Exception {
        // Initialize the database
        mentalHealthRepository.saveAndFlush(mentalHealth);

        int databaseSizeBeforeDelete = mentalHealthRepository.findAll().size();

        // Delete the mentalHealth
        restMentalHealthMockMvc
            .perform(delete(ENTITY_API_URL_ID, mentalHealth.getId().toString()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MentalHealth> mentalHealthList = mentalHealthRepository.findAll();
        assertThat(mentalHealthList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
