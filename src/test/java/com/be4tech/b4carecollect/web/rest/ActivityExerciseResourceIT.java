package com.be4tech.b4carecollect.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.be4tech.b4carecollect.IntegrationTest;
import com.be4tech.b4carecollect.domain.ActivityExercise;
import com.be4tech.b4carecollect.repository.ActivityExerciseRepository;
import com.be4tech.b4carecollect.service.criteria.ActivityExerciseCriteria;
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
 * Integration tests for the {@link ActivityExerciseResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ActivityExerciseResourceIT {

    private static final String DEFAULT_USUARIO_ID = "AAAAAAAAAA";
    private static final String UPDATED_USUARIO_ID = "BBBBBBBBBB";

    private static final String DEFAULT_EMPRESA_ID = "AAAAAAAAAA";
    private static final String UPDATED_EMPRESA_ID = "BBBBBBBBBB";

    private static final String DEFAULT_EXERCISE = "AAAAAAAAAA";
    private static final String UPDATED_EXERCISE = "BBBBBBBBBB";

    private static final Integer DEFAULT_REPETITIONS = 1;
    private static final Integer UPDATED_REPETITIONS = 2;
    private static final Integer SMALLER_REPETITIONS = 1 - 1;

    private static final String DEFAULT_TYPE_RESISTENCE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE_RESISTENCE = "BBBBBBBBBB";

    private static final Float DEFAULT_RESISTENCE_KG = 1F;
    private static final Float UPDATED_RESISTENCE_KG = 2F;
    private static final Float SMALLER_RESISTENCE_KG = 1F - 1F;

    private static final Integer DEFAULT_DURATION = 1;
    private static final Integer UPDATED_DURATION = 2;
    private static final Integer SMALLER_DURATION = 1 - 1;

    private static final Instant DEFAULT_START_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_START_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_END_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_END_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/activity-exercises";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ActivityExerciseRepository activityExerciseRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restActivityExerciseMockMvc;

    private ActivityExercise activityExercise;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ActivityExercise createEntity(EntityManager em) {
        ActivityExercise activityExercise = new ActivityExercise()
            .usuarioId(DEFAULT_USUARIO_ID)
            .empresaId(DEFAULT_EMPRESA_ID)
            .exercise(DEFAULT_EXERCISE)
            .repetitions(DEFAULT_REPETITIONS)
            .typeResistence(DEFAULT_TYPE_RESISTENCE)
            .resistenceKg(DEFAULT_RESISTENCE_KG)
            .duration(DEFAULT_DURATION)
            .startTime(DEFAULT_START_TIME)
            .endTime(DEFAULT_END_TIME);
        return activityExercise;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ActivityExercise createUpdatedEntity(EntityManager em) {
        ActivityExercise activityExercise = new ActivityExercise()
            .usuarioId(UPDATED_USUARIO_ID)
            .empresaId(UPDATED_EMPRESA_ID)
            .exercise(UPDATED_EXERCISE)
            .repetitions(UPDATED_REPETITIONS)
            .typeResistence(UPDATED_TYPE_RESISTENCE)
            .resistenceKg(UPDATED_RESISTENCE_KG)
            .duration(UPDATED_DURATION)
            .startTime(UPDATED_START_TIME)
            .endTime(UPDATED_END_TIME);
        return activityExercise;
    }

    @BeforeEach
    public void initTest() {
        activityExercise = createEntity(em);
    }

    @Test
    @Transactional
    void createActivityExercise() throws Exception {
        int databaseSizeBeforeCreate = activityExerciseRepository.findAll().size();
        // Create the ActivityExercise
        restActivityExerciseMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(activityExercise))
            )
            .andExpect(status().isCreated());

        // Validate the ActivityExercise in the database
        List<ActivityExercise> activityExerciseList = activityExerciseRepository.findAll();
        assertThat(activityExerciseList).hasSize(databaseSizeBeforeCreate + 1);
        ActivityExercise testActivityExercise = activityExerciseList.get(activityExerciseList.size() - 1);
        assertThat(testActivityExercise.getUsuarioId()).isEqualTo(DEFAULT_USUARIO_ID);
        assertThat(testActivityExercise.getEmpresaId()).isEqualTo(DEFAULT_EMPRESA_ID);
        assertThat(testActivityExercise.getExercise()).isEqualTo(DEFAULT_EXERCISE);
        assertThat(testActivityExercise.getRepetitions()).isEqualTo(DEFAULT_REPETITIONS);
        assertThat(testActivityExercise.getTypeResistence()).isEqualTo(DEFAULT_TYPE_RESISTENCE);
        assertThat(testActivityExercise.getResistenceKg()).isEqualTo(DEFAULT_RESISTENCE_KG);
        assertThat(testActivityExercise.getDuration()).isEqualTo(DEFAULT_DURATION);
        assertThat(testActivityExercise.getStartTime()).isEqualTo(DEFAULT_START_TIME);
        assertThat(testActivityExercise.getEndTime()).isEqualTo(DEFAULT_END_TIME);
    }

    @Test
    @Transactional
    void createActivityExerciseWithExistingId() throws Exception {
        // Create the ActivityExercise with an existing ID
        activityExerciseRepository.saveAndFlush(activityExercise);

        int databaseSizeBeforeCreate = activityExerciseRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restActivityExerciseMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(activityExercise))
            )
            .andExpect(status().isBadRequest());

        // Validate the ActivityExercise in the database
        List<ActivityExercise> activityExerciseList = activityExerciseRepository.findAll();
        assertThat(activityExerciseList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllActivityExercises() throws Exception {
        // Initialize the database
        activityExerciseRepository.saveAndFlush(activityExercise);

        // Get all the activityExerciseList
        restActivityExerciseMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(activityExercise.getId().toString())))
            .andExpect(jsonPath("$.[*].usuarioId").value(hasItem(DEFAULT_USUARIO_ID)))
            .andExpect(jsonPath("$.[*].empresaId").value(hasItem(DEFAULT_EMPRESA_ID)))
            .andExpect(jsonPath("$.[*].exercise").value(hasItem(DEFAULT_EXERCISE)))
            .andExpect(jsonPath("$.[*].repetitions").value(hasItem(DEFAULT_REPETITIONS)))
            .andExpect(jsonPath("$.[*].typeResistence").value(hasItem(DEFAULT_TYPE_RESISTENCE)))
            .andExpect(jsonPath("$.[*].resistenceKg").value(hasItem(DEFAULT_RESISTENCE_KG.doubleValue())))
            .andExpect(jsonPath("$.[*].duration").value(hasItem(DEFAULT_DURATION)))
            .andExpect(jsonPath("$.[*].startTime").value(hasItem(DEFAULT_START_TIME.toString())))
            .andExpect(jsonPath("$.[*].endTime").value(hasItem(DEFAULT_END_TIME.toString())));
    }

    @Test
    @Transactional
    void getActivityExercise() throws Exception {
        // Initialize the database
        activityExerciseRepository.saveAndFlush(activityExercise);

        // Get the activityExercise
        restActivityExerciseMockMvc
            .perform(get(ENTITY_API_URL_ID, activityExercise.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(activityExercise.getId().toString()))
            .andExpect(jsonPath("$.usuarioId").value(DEFAULT_USUARIO_ID))
            .andExpect(jsonPath("$.empresaId").value(DEFAULT_EMPRESA_ID))
            .andExpect(jsonPath("$.exercise").value(DEFAULT_EXERCISE))
            .andExpect(jsonPath("$.repetitions").value(DEFAULT_REPETITIONS))
            .andExpect(jsonPath("$.typeResistence").value(DEFAULT_TYPE_RESISTENCE))
            .andExpect(jsonPath("$.resistenceKg").value(DEFAULT_RESISTENCE_KG.doubleValue()))
            .andExpect(jsonPath("$.duration").value(DEFAULT_DURATION))
            .andExpect(jsonPath("$.startTime").value(DEFAULT_START_TIME.toString()))
            .andExpect(jsonPath("$.endTime").value(DEFAULT_END_TIME.toString()));
    }

    @Test
    @Transactional
    void getActivityExercisesByIdFiltering() throws Exception {
        // Initialize the database
        activityExerciseRepository.saveAndFlush(activityExercise);

        UUID id = activityExercise.getId();

        defaultActivityExerciseShouldBeFound("id.equals=" + id);
        defaultActivityExerciseShouldNotBeFound("id.notEquals=" + id);
    }

    @Test
    @Transactional
    void getAllActivityExercisesByUsuarioIdIsEqualToSomething() throws Exception {
        // Initialize the database
        activityExerciseRepository.saveAndFlush(activityExercise);

        // Get all the activityExerciseList where usuarioId equals to DEFAULT_USUARIO_ID
        defaultActivityExerciseShouldBeFound("usuarioId.equals=" + DEFAULT_USUARIO_ID);

        // Get all the activityExerciseList where usuarioId equals to UPDATED_USUARIO_ID
        defaultActivityExerciseShouldNotBeFound("usuarioId.equals=" + UPDATED_USUARIO_ID);
    }

    @Test
    @Transactional
    void getAllActivityExercisesByUsuarioIdIsInShouldWork() throws Exception {
        // Initialize the database
        activityExerciseRepository.saveAndFlush(activityExercise);

        // Get all the activityExerciseList where usuarioId in DEFAULT_USUARIO_ID or UPDATED_USUARIO_ID
        defaultActivityExerciseShouldBeFound("usuarioId.in=" + DEFAULT_USUARIO_ID + "," + UPDATED_USUARIO_ID);

        // Get all the activityExerciseList where usuarioId equals to UPDATED_USUARIO_ID
        defaultActivityExerciseShouldNotBeFound("usuarioId.in=" + UPDATED_USUARIO_ID);
    }

    @Test
    @Transactional
    void getAllActivityExercisesByUsuarioIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        activityExerciseRepository.saveAndFlush(activityExercise);

        // Get all the activityExerciseList where usuarioId is not null
        defaultActivityExerciseShouldBeFound("usuarioId.specified=true");

        // Get all the activityExerciseList where usuarioId is null
        defaultActivityExerciseShouldNotBeFound("usuarioId.specified=false");
    }

    @Test
    @Transactional
    void getAllActivityExercisesByUsuarioIdContainsSomething() throws Exception {
        // Initialize the database
        activityExerciseRepository.saveAndFlush(activityExercise);

        // Get all the activityExerciseList where usuarioId contains DEFAULT_USUARIO_ID
        defaultActivityExerciseShouldBeFound("usuarioId.contains=" + DEFAULT_USUARIO_ID);

        // Get all the activityExerciseList where usuarioId contains UPDATED_USUARIO_ID
        defaultActivityExerciseShouldNotBeFound("usuarioId.contains=" + UPDATED_USUARIO_ID);
    }

    @Test
    @Transactional
    void getAllActivityExercisesByUsuarioIdNotContainsSomething() throws Exception {
        // Initialize the database
        activityExerciseRepository.saveAndFlush(activityExercise);

        // Get all the activityExerciseList where usuarioId does not contain DEFAULT_USUARIO_ID
        defaultActivityExerciseShouldNotBeFound("usuarioId.doesNotContain=" + DEFAULT_USUARIO_ID);

        // Get all the activityExerciseList where usuarioId does not contain UPDATED_USUARIO_ID
        defaultActivityExerciseShouldBeFound("usuarioId.doesNotContain=" + UPDATED_USUARIO_ID);
    }

    @Test
    @Transactional
    void getAllActivityExercisesByEmpresaIdIsEqualToSomething() throws Exception {
        // Initialize the database
        activityExerciseRepository.saveAndFlush(activityExercise);

        // Get all the activityExerciseList where empresaId equals to DEFAULT_EMPRESA_ID
        defaultActivityExerciseShouldBeFound("empresaId.equals=" + DEFAULT_EMPRESA_ID);

        // Get all the activityExerciseList where empresaId equals to UPDATED_EMPRESA_ID
        defaultActivityExerciseShouldNotBeFound("empresaId.equals=" + UPDATED_EMPRESA_ID);
    }

    @Test
    @Transactional
    void getAllActivityExercisesByEmpresaIdIsInShouldWork() throws Exception {
        // Initialize the database
        activityExerciseRepository.saveAndFlush(activityExercise);

        // Get all the activityExerciseList where empresaId in DEFAULT_EMPRESA_ID or UPDATED_EMPRESA_ID
        defaultActivityExerciseShouldBeFound("empresaId.in=" + DEFAULT_EMPRESA_ID + "," + UPDATED_EMPRESA_ID);

        // Get all the activityExerciseList where empresaId equals to UPDATED_EMPRESA_ID
        defaultActivityExerciseShouldNotBeFound("empresaId.in=" + UPDATED_EMPRESA_ID);
    }

    @Test
    @Transactional
    void getAllActivityExercisesByEmpresaIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        activityExerciseRepository.saveAndFlush(activityExercise);

        // Get all the activityExerciseList where empresaId is not null
        defaultActivityExerciseShouldBeFound("empresaId.specified=true");

        // Get all the activityExerciseList where empresaId is null
        defaultActivityExerciseShouldNotBeFound("empresaId.specified=false");
    }

    @Test
    @Transactional
    void getAllActivityExercisesByEmpresaIdContainsSomething() throws Exception {
        // Initialize the database
        activityExerciseRepository.saveAndFlush(activityExercise);

        // Get all the activityExerciseList where empresaId contains DEFAULT_EMPRESA_ID
        defaultActivityExerciseShouldBeFound("empresaId.contains=" + DEFAULT_EMPRESA_ID);

        // Get all the activityExerciseList where empresaId contains UPDATED_EMPRESA_ID
        defaultActivityExerciseShouldNotBeFound("empresaId.contains=" + UPDATED_EMPRESA_ID);
    }

    @Test
    @Transactional
    void getAllActivityExercisesByEmpresaIdNotContainsSomething() throws Exception {
        // Initialize the database
        activityExerciseRepository.saveAndFlush(activityExercise);

        // Get all the activityExerciseList where empresaId does not contain DEFAULT_EMPRESA_ID
        defaultActivityExerciseShouldNotBeFound("empresaId.doesNotContain=" + DEFAULT_EMPRESA_ID);

        // Get all the activityExerciseList where empresaId does not contain UPDATED_EMPRESA_ID
        defaultActivityExerciseShouldBeFound("empresaId.doesNotContain=" + UPDATED_EMPRESA_ID);
    }

    @Test
    @Transactional
    void getAllActivityExercisesByExerciseIsEqualToSomething() throws Exception {
        // Initialize the database
        activityExerciseRepository.saveAndFlush(activityExercise);

        // Get all the activityExerciseList where exercise equals to DEFAULT_EXERCISE
        defaultActivityExerciseShouldBeFound("exercise.equals=" + DEFAULT_EXERCISE);

        // Get all the activityExerciseList where exercise equals to UPDATED_EXERCISE
        defaultActivityExerciseShouldNotBeFound("exercise.equals=" + UPDATED_EXERCISE);
    }

    @Test
    @Transactional
    void getAllActivityExercisesByExerciseIsInShouldWork() throws Exception {
        // Initialize the database
        activityExerciseRepository.saveAndFlush(activityExercise);

        // Get all the activityExerciseList where exercise in DEFAULT_EXERCISE or UPDATED_EXERCISE
        defaultActivityExerciseShouldBeFound("exercise.in=" + DEFAULT_EXERCISE + "," + UPDATED_EXERCISE);

        // Get all the activityExerciseList where exercise equals to UPDATED_EXERCISE
        defaultActivityExerciseShouldNotBeFound("exercise.in=" + UPDATED_EXERCISE);
    }

    @Test
    @Transactional
    void getAllActivityExercisesByExerciseIsNullOrNotNull() throws Exception {
        // Initialize the database
        activityExerciseRepository.saveAndFlush(activityExercise);

        // Get all the activityExerciseList where exercise is not null
        defaultActivityExerciseShouldBeFound("exercise.specified=true");

        // Get all the activityExerciseList where exercise is null
        defaultActivityExerciseShouldNotBeFound("exercise.specified=false");
    }

    @Test
    @Transactional
    void getAllActivityExercisesByExerciseContainsSomething() throws Exception {
        // Initialize the database
        activityExerciseRepository.saveAndFlush(activityExercise);

        // Get all the activityExerciseList where exercise contains DEFAULT_EXERCISE
        defaultActivityExerciseShouldBeFound("exercise.contains=" + DEFAULT_EXERCISE);

        // Get all the activityExerciseList where exercise contains UPDATED_EXERCISE
        defaultActivityExerciseShouldNotBeFound("exercise.contains=" + UPDATED_EXERCISE);
    }

    @Test
    @Transactional
    void getAllActivityExercisesByExerciseNotContainsSomething() throws Exception {
        // Initialize the database
        activityExerciseRepository.saveAndFlush(activityExercise);

        // Get all the activityExerciseList where exercise does not contain DEFAULT_EXERCISE
        defaultActivityExerciseShouldNotBeFound("exercise.doesNotContain=" + DEFAULT_EXERCISE);

        // Get all the activityExerciseList where exercise does not contain UPDATED_EXERCISE
        defaultActivityExerciseShouldBeFound("exercise.doesNotContain=" + UPDATED_EXERCISE);
    }

    @Test
    @Transactional
    void getAllActivityExercisesByRepetitionsIsEqualToSomething() throws Exception {
        // Initialize the database
        activityExerciseRepository.saveAndFlush(activityExercise);

        // Get all the activityExerciseList where repetitions equals to DEFAULT_REPETITIONS
        defaultActivityExerciseShouldBeFound("repetitions.equals=" + DEFAULT_REPETITIONS);

        // Get all the activityExerciseList where repetitions equals to UPDATED_REPETITIONS
        defaultActivityExerciseShouldNotBeFound("repetitions.equals=" + UPDATED_REPETITIONS);
    }

    @Test
    @Transactional
    void getAllActivityExercisesByRepetitionsIsInShouldWork() throws Exception {
        // Initialize the database
        activityExerciseRepository.saveAndFlush(activityExercise);

        // Get all the activityExerciseList where repetitions in DEFAULT_REPETITIONS or UPDATED_REPETITIONS
        defaultActivityExerciseShouldBeFound("repetitions.in=" + DEFAULT_REPETITIONS + "," + UPDATED_REPETITIONS);

        // Get all the activityExerciseList where repetitions equals to UPDATED_REPETITIONS
        defaultActivityExerciseShouldNotBeFound("repetitions.in=" + UPDATED_REPETITIONS);
    }

    @Test
    @Transactional
    void getAllActivityExercisesByRepetitionsIsNullOrNotNull() throws Exception {
        // Initialize the database
        activityExerciseRepository.saveAndFlush(activityExercise);

        // Get all the activityExerciseList where repetitions is not null
        defaultActivityExerciseShouldBeFound("repetitions.specified=true");

        // Get all the activityExerciseList where repetitions is null
        defaultActivityExerciseShouldNotBeFound("repetitions.specified=false");
    }

    @Test
    @Transactional
    void getAllActivityExercisesByRepetitionsIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        activityExerciseRepository.saveAndFlush(activityExercise);

        // Get all the activityExerciseList where repetitions is greater than or equal to DEFAULT_REPETITIONS
        defaultActivityExerciseShouldBeFound("repetitions.greaterThanOrEqual=" + DEFAULT_REPETITIONS);

        // Get all the activityExerciseList where repetitions is greater than or equal to UPDATED_REPETITIONS
        defaultActivityExerciseShouldNotBeFound("repetitions.greaterThanOrEqual=" + UPDATED_REPETITIONS);
    }

    @Test
    @Transactional
    void getAllActivityExercisesByRepetitionsIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        activityExerciseRepository.saveAndFlush(activityExercise);

        // Get all the activityExerciseList where repetitions is less than or equal to DEFAULT_REPETITIONS
        defaultActivityExerciseShouldBeFound("repetitions.lessThanOrEqual=" + DEFAULT_REPETITIONS);

        // Get all the activityExerciseList where repetitions is less than or equal to SMALLER_REPETITIONS
        defaultActivityExerciseShouldNotBeFound("repetitions.lessThanOrEqual=" + SMALLER_REPETITIONS);
    }

    @Test
    @Transactional
    void getAllActivityExercisesByRepetitionsIsLessThanSomething() throws Exception {
        // Initialize the database
        activityExerciseRepository.saveAndFlush(activityExercise);

        // Get all the activityExerciseList where repetitions is less than DEFAULT_REPETITIONS
        defaultActivityExerciseShouldNotBeFound("repetitions.lessThan=" + DEFAULT_REPETITIONS);

        // Get all the activityExerciseList where repetitions is less than UPDATED_REPETITIONS
        defaultActivityExerciseShouldBeFound("repetitions.lessThan=" + UPDATED_REPETITIONS);
    }

    @Test
    @Transactional
    void getAllActivityExercisesByRepetitionsIsGreaterThanSomething() throws Exception {
        // Initialize the database
        activityExerciseRepository.saveAndFlush(activityExercise);

        // Get all the activityExerciseList where repetitions is greater than DEFAULT_REPETITIONS
        defaultActivityExerciseShouldNotBeFound("repetitions.greaterThan=" + DEFAULT_REPETITIONS);

        // Get all the activityExerciseList where repetitions is greater than SMALLER_REPETITIONS
        defaultActivityExerciseShouldBeFound("repetitions.greaterThan=" + SMALLER_REPETITIONS);
    }

    @Test
    @Transactional
    void getAllActivityExercisesByTypeResistenceIsEqualToSomething() throws Exception {
        // Initialize the database
        activityExerciseRepository.saveAndFlush(activityExercise);

        // Get all the activityExerciseList where typeResistence equals to DEFAULT_TYPE_RESISTENCE
        defaultActivityExerciseShouldBeFound("typeResistence.equals=" + DEFAULT_TYPE_RESISTENCE);

        // Get all the activityExerciseList where typeResistence equals to UPDATED_TYPE_RESISTENCE
        defaultActivityExerciseShouldNotBeFound("typeResistence.equals=" + UPDATED_TYPE_RESISTENCE);
    }

    @Test
    @Transactional
    void getAllActivityExercisesByTypeResistenceIsInShouldWork() throws Exception {
        // Initialize the database
        activityExerciseRepository.saveAndFlush(activityExercise);

        // Get all the activityExerciseList where typeResistence in DEFAULT_TYPE_RESISTENCE or UPDATED_TYPE_RESISTENCE
        defaultActivityExerciseShouldBeFound("typeResistence.in=" + DEFAULT_TYPE_RESISTENCE + "," + UPDATED_TYPE_RESISTENCE);

        // Get all the activityExerciseList where typeResistence equals to UPDATED_TYPE_RESISTENCE
        defaultActivityExerciseShouldNotBeFound("typeResistence.in=" + UPDATED_TYPE_RESISTENCE);
    }

    @Test
    @Transactional
    void getAllActivityExercisesByTypeResistenceIsNullOrNotNull() throws Exception {
        // Initialize the database
        activityExerciseRepository.saveAndFlush(activityExercise);

        // Get all the activityExerciseList where typeResistence is not null
        defaultActivityExerciseShouldBeFound("typeResistence.specified=true");

        // Get all the activityExerciseList where typeResistence is null
        defaultActivityExerciseShouldNotBeFound("typeResistence.specified=false");
    }

    @Test
    @Transactional
    void getAllActivityExercisesByTypeResistenceContainsSomething() throws Exception {
        // Initialize the database
        activityExerciseRepository.saveAndFlush(activityExercise);

        // Get all the activityExerciseList where typeResistence contains DEFAULT_TYPE_RESISTENCE
        defaultActivityExerciseShouldBeFound("typeResistence.contains=" + DEFAULT_TYPE_RESISTENCE);

        // Get all the activityExerciseList where typeResistence contains UPDATED_TYPE_RESISTENCE
        defaultActivityExerciseShouldNotBeFound("typeResistence.contains=" + UPDATED_TYPE_RESISTENCE);
    }

    @Test
    @Transactional
    void getAllActivityExercisesByTypeResistenceNotContainsSomething() throws Exception {
        // Initialize the database
        activityExerciseRepository.saveAndFlush(activityExercise);

        // Get all the activityExerciseList where typeResistence does not contain DEFAULT_TYPE_RESISTENCE
        defaultActivityExerciseShouldNotBeFound("typeResistence.doesNotContain=" + DEFAULT_TYPE_RESISTENCE);

        // Get all the activityExerciseList where typeResistence does not contain UPDATED_TYPE_RESISTENCE
        defaultActivityExerciseShouldBeFound("typeResistence.doesNotContain=" + UPDATED_TYPE_RESISTENCE);
    }

    @Test
    @Transactional
    void getAllActivityExercisesByResistenceKgIsEqualToSomething() throws Exception {
        // Initialize the database
        activityExerciseRepository.saveAndFlush(activityExercise);

        // Get all the activityExerciseList where resistenceKg equals to DEFAULT_RESISTENCE_KG
        defaultActivityExerciseShouldBeFound("resistenceKg.equals=" + DEFAULT_RESISTENCE_KG);

        // Get all the activityExerciseList where resistenceKg equals to UPDATED_RESISTENCE_KG
        defaultActivityExerciseShouldNotBeFound("resistenceKg.equals=" + UPDATED_RESISTENCE_KG);
    }

    @Test
    @Transactional
    void getAllActivityExercisesByResistenceKgIsInShouldWork() throws Exception {
        // Initialize the database
        activityExerciseRepository.saveAndFlush(activityExercise);

        // Get all the activityExerciseList where resistenceKg in DEFAULT_RESISTENCE_KG or UPDATED_RESISTENCE_KG
        defaultActivityExerciseShouldBeFound("resistenceKg.in=" + DEFAULT_RESISTENCE_KG + "," + UPDATED_RESISTENCE_KG);

        // Get all the activityExerciseList where resistenceKg equals to UPDATED_RESISTENCE_KG
        defaultActivityExerciseShouldNotBeFound("resistenceKg.in=" + UPDATED_RESISTENCE_KG);
    }

    @Test
    @Transactional
    void getAllActivityExercisesByResistenceKgIsNullOrNotNull() throws Exception {
        // Initialize the database
        activityExerciseRepository.saveAndFlush(activityExercise);

        // Get all the activityExerciseList where resistenceKg is not null
        defaultActivityExerciseShouldBeFound("resistenceKg.specified=true");

        // Get all the activityExerciseList where resistenceKg is null
        defaultActivityExerciseShouldNotBeFound("resistenceKg.specified=false");
    }

    @Test
    @Transactional
    void getAllActivityExercisesByResistenceKgIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        activityExerciseRepository.saveAndFlush(activityExercise);

        // Get all the activityExerciseList where resistenceKg is greater than or equal to DEFAULT_RESISTENCE_KG
        defaultActivityExerciseShouldBeFound("resistenceKg.greaterThanOrEqual=" + DEFAULT_RESISTENCE_KG);

        // Get all the activityExerciseList where resistenceKg is greater than or equal to UPDATED_RESISTENCE_KG
        defaultActivityExerciseShouldNotBeFound("resistenceKg.greaterThanOrEqual=" + UPDATED_RESISTENCE_KG);
    }

    @Test
    @Transactional
    void getAllActivityExercisesByResistenceKgIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        activityExerciseRepository.saveAndFlush(activityExercise);

        // Get all the activityExerciseList where resistenceKg is less than or equal to DEFAULT_RESISTENCE_KG
        defaultActivityExerciseShouldBeFound("resistenceKg.lessThanOrEqual=" + DEFAULT_RESISTENCE_KG);

        // Get all the activityExerciseList where resistenceKg is less than or equal to SMALLER_RESISTENCE_KG
        defaultActivityExerciseShouldNotBeFound("resistenceKg.lessThanOrEqual=" + SMALLER_RESISTENCE_KG);
    }

    @Test
    @Transactional
    void getAllActivityExercisesByResistenceKgIsLessThanSomething() throws Exception {
        // Initialize the database
        activityExerciseRepository.saveAndFlush(activityExercise);

        // Get all the activityExerciseList where resistenceKg is less than DEFAULT_RESISTENCE_KG
        defaultActivityExerciseShouldNotBeFound("resistenceKg.lessThan=" + DEFAULT_RESISTENCE_KG);

        // Get all the activityExerciseList where resistenceKg is less than UPDATED_RESISTENCE_KG
        defaultActivityExerciseShouldBeFound("resistenceKg.lessThan=" + UPDATED_RESISTENCE_KG);
    }

    @Test
    @Transactional
    void getAllActivityExercisesByResistenceKgIsGreaterThanSomething() throws Exception {
        // Initialize the database
        activityExerciseRepository.saveAndFlush(activityExercise);

        // Get all the activityExerciseList where resistenceKg is greater than DEFAULT_RESISTENCE_KG
        defaultActivityExerciseShouldNotBeFound("resistenceKg.greaterThan=" + DEFAULT_RESISTENCE_KG);

        // Get all the activityExerciseList where resistenceKg is greater than SMALLER_RESISTENCE_KG
        defaultActivityExerciseShouldBeFound("resistenceKg.greaterThan=" + SMALLER_RESISTENCE_KG);
    }

    @Test
    @Transactional
    void getAllActivityExercisesByDurationIsEqualToSomething() throws Exception {
        // Initialize the database
        activityExerciseRepository.saveAndFlush(activityExercise);

        // Get all the activityExerciseList where duration equals to DEFAULT_DURATION
        defaultActivityExerciseShouldBeFound("duration.equals=" + DEFAULT_DURATION);

        // Get all the activityExerciseList where duration equals to UPDATED_DURATION
        defaultActivityExerciseShouldNotBeFound("duration.equals=" + UPDATED_DURATION);
    }

    @Test
    @Transactional
    void getAllActivityExercisesByDurationIsInShouldWork() throws Exception {
        // Initialize the database
        activityExerciseRepository.saveAndFlush(activityExercise);

        // Get all the activityExerciseList where duration in DEFAULT_DURATION or UPDATED_DURATION
        defaultActivityExerciseShouldBeFound("duration.in=" + DEFAULT_DURATION + "," + UPDATED_DURATION);

        // Get all the activityExerciseList where duration equals to UPDATED_DURATION
        defaultActivityExerciseShouldNotBeFound("duration.in=" + UPDATED_DURATION);
    }

    @Test
    @Transactional
    void getAllActivityExercisesByDurationIsNullOrNotNull() throws Exception {
        // Initialize the database
        activityExerciseRepository.saveAndFlush(activityExercise);

        // Get all the activityExerciseList where duration is not null
        defaultActivityExerciseShouldBeFound("duration.specified=true");

        // Get all the activityExerciseList where duration is null
        defaultActivityExerciseShouldNotBeFound("duration.specified=false");
    }

    @Test
    @Transactional
    void getAllActivityExercisesByDurationIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        activityExerciseRepository.saveAndFlush(activityExercise);

        // Get all the activityExerciseList where duration is greater than or equal to DEFAULT_DURATION
        defaultActivityExerciseShouldBeFound("duration.greaterThanOrEqual=" + DEFAULT_DURATION);

        // Get all the activityExerciseList where duration is greater than or equal to UPDATED_DURATION
        defaultActivityExerciseShouldNotBeFound("duration.greaterThanOrEqual=" + UPDATED_DURATION);
    }

    @Test
    @Transactional
    void getAllActivityExercisesByDurationIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        activityExerciseRepository.saveAndFlush(activityExercise);

        // Get all the activityExerciseList where duration is less than or equal to DEFAULT_DURATION
        defaultActivityExerciseShouldBeFound("duration.lessThanOrEqual=" + DEFAULT_DURATION);

        // Get all the activityExerciseList where duration is less than or equal to SMALLER_DURATION
        defaultActivityExerciseShouldNotBeFound("duration.lessThanOrEqual=" + SMALLER_DURATION);
    }

    @Test
    @Transactional
    void getAllActivityExercisesByDurationIsLessThanSomething() throws Exception {
        // Initialize the database
        activityExerciseRepository.saveAndFlush(activityExercise);

        // Get all the activityExerciseList where duration is less than DEFAULT_DURATION
        defaultActivityExerciseShouldNotBeFound("duration.lessThan=" + DEFAULT_DURATION);

        // Get all the activityExerciseList where duration is less than UPDATED_DURATION
        defaultActivityExerciseShouldBeFound("duration.lessThan=" + UPDATED_DURATION);
    }

    @Test
    @Transactional
    void getAllActivityExercisesByDurationIsGreaterThanSomething() throws Exception {
        // Initialize the database
        activityExerciseRepository.saveAndFlush(activityExercise);

        // Get all the activityExerciseList where duration is greater than DEFAULT_DURATION
        defaultActivityExerciseShouldNotBeFound("duration.greaterThan=" + DEFAULT_DURATION);

        // Get all the activityExerciseList where duration is greater than SMALLER_DURATION
        defaultActivityExerciseShouldBeFound("duration.greaterThan=" + SMALLER_DURATION);
    }

    @Test
    @Transactional
    void getAllActivityExercisesByStartTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        activityExerciseRepository.saveAndFlush(activityExercise);

        // Get all the activityExerciseList where startTime equals to DEFAULT_START_TIME
        defaultActivityExerciseShouldBeFound("startTime.equals=" + DEFAULT_START_TIME);

        // Get all the activityExerciseList where startTime equals to UPDATED_START_TIME
        defaultActivityExerciseShouldNotBeFound("startTime.equals=" + UPDATED_START_TIME);
    }

    @Test
    @Transactional
    void getAllActivityExercisesByStartTimeIsInShouldWork() throws Exception {
        // Initialize the database
        activityExerciseRepository.saveAndFlush(activityExercise);

        // Get all the activityExerciseList where startTime in DEFAULT_START_TIME or UPDATED_START_TIME
        defaultActivityExerciseShouldBeFound("startTime.in=" + DEFAULT_START_TIME + "," + UPDATED_START_TIME);

        // Get all the activityExerciseList where startTime equals to UPDATED_START_TIME
        defaultActivityExerciseShouldNotBeFound("startTime.in=" + UPDATED_START_TIME);
    }

    @Test
    @Transactional
    void getAllActivityExercisesByStartTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        activityExerciseRepository.saveAndFlush(activityExercise);

        // Get all the activityExerciseList where startTime is not null
        defaultActivityExerciseShouldBeFound("startTime.specified=true");

        // Get all the activityExerciseList where startTime is null
        defaultActivityExerciseShouldNotBeFound("startTime.specified=false");
    }

    @Test
    @Transactional
    void getAllActivityExercisesByEndTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        activityExerciseRepository.saveAndFlush(activityExercise);

        // Get all the activityExerciseList where endTime equals to DEFAULT_END_TIME
        defaultActivityExerciseShouldBeFound("endTime.equals=" + DEFAULT_END_TIME);

        // Get all the activityExerciseList where endTime equals to UPDATED_END_TIME
        defaultActivityExerciseShouldNotBeFound("endTime.equals=" + UPDATED_END_TIME);
    }

    @Test
    @Transactional
    void getAllActivityExercisesByEndTimeIsInShouldWork() throws Exception {
        // Initialize the database
        activityExerciseRepository.saveAndFlush(activityExercise);

        // Get all the activityExerciseList where endTime in DEFAULT_END_TIME or UPDATED_END_TIME
        defaultActivityExerciseShouldBeFound("endTime.in=" + DEFAULT_END_TIME + "," + UPDATED_END_TIME);

        // Get all the activityExerciseList where endTime equals to UPDATED_END_TIME
        defaultActivityExerciseShouldNotBeFound("endTime.in=" + UPDATED_END_TIME);
    }

    @Test
    @Transactional
    void getAllActivityExercisesByEndTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        activityExerciseRepository.saveAndFlush(activityExercise);

        // Get all the activityExerciseList where endTime is not null
        defaultActivityExerciseShouldBeFound("endTime.specified=true");

        // Get all the activityExerciseList where endTime is null
        defaultActivityExerciseShouldNotBeFound("endTime.specified=false");
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultActivityExerciseShouldBeFound(String filter) throws Exception {
        restActivityExerciseMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(activityExercise.getId().toString())))
            .andExpect(jsonPath("$.[*].usuarioId").value(hasItem(DEFAULT_USUARIO_ID)))
            .andExpect(jsonPath("$.[*].empresaId").value(hasItem(DEFAULT_EMPRESA_ID)))
            .andExpect(jsonPath("$.[*].exercise").value(hasItem(DEFAULT_EXERCISE)))
            .andExpect(jsonPath("$.[*].repetitions").value(hasItem(DEFAULT_REPETITIONS)))
            .andExpect(jsonPath("$.[*].typeResistence").value(hasItem(DEFAULT_TYPE_RESISTENCE)))
            .andExpect(jsonPath("$.[*].resistenceKg").value(hasItem(DEFAULT_RESISTENCE_KG.doubleValue())))
            .andExpect(jsonPath("$.[*].duration").value(hasItem(DEFAULT_DURATION)))
            .andExpect(jsonPath("$.[*].startTime").value(hasItem(DEFAULT_START_TIME.toString())))
            .andExpect(jsonPath("$.[*].endTime").value(hasItem(DEFAULT_END_TIME.toString())));

        // Check, that the count call also returns 1
        restActivityExerciseMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultActivityExerciseShouldNotBeFound(String filter) throws Exception {
        restActivityExerciseMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restActivityExerciseMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingActivityExercise() throws Exception {
        // Get the activityExercise
        restActivityExerciseMockMvc.perform(get(ENTITY_API_URL_ID, UUID.randomUUID().toString())).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingActivityExercise() throws Exception {
        // Initialize the database
        activityExerciseRepository.saveAndFlush(activityExercise);

        int databaseSizeBeforeUpdate = activityExerciseRepository.findAll().size();

        // Update the activityExercise
        ActivityExercise updatedActivityExercise = activityExerciseRepository.findById(activityExercise.getId()).get();
        // Disconnect from session so that the updates on updatedActivityExercise are not directly saved in db
        em.detach(updatedActivityExercise);
        updatedActivityExercise
            .usuarioId(UPDATED_USUARIO_ID)
            .empresaId(UPDATED_EMPRESA_ID)
            .exercise(UPDATED_EXERCISE)
            .repetitions(UPDATED_REPETITIONS)
            .typeResistence(UPDATED_TYPE_RESISTENCE)
            .resistenceKg(UPDATED_RESISTENCE_KG)
            .duration(UPDATED_DURATION)
            .startTime(UPDATED_START_TIME)
            .endTime(UPDATED_END_TIME);

        restActivityExerciseMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedActivityExercise.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedActivityExercise))
            )
            .andExpect(status().isOk());

        // Validate the ActivityExercise in the database
        List<ActivityExercise> activityExerciseList = activityExerciseRepository.findAll();
        assertThat(activityExerciseList).hasSize(databaseSizeBeforeUpdate);
        ActivityExercise testActivityExercise = activityExerciseList.get(activityExerciseList.size() - 1);
        assertThat(testActivityExercise.getUsuarioId()).isEqualTo(UPDATED_USUARIO_ID);
        assertThat(testActivityExercise.getEmpresaId()).isEqualTo(UPDATED_EMPRESA_ID);
        assertThat(testActivityExercise.getExercise()).isEqualTo(UPDATED_EXERCISE);
        assertThat(testActivityExercise.getRepetitions()).isEqualTo(UPDATED_REPETITIONS);
        assertThat(testActivityExercise.getTypeResistence()).isEqualTo(UPDATED_TYPE_RESISTENCE);
        assertThat(testActivityExercise.getResistenceKg()).isEqualTo(UPDATED_RESISTENCE_KG);
        assertThat(testActivityExercise.getDuration()).isEqualTo(UPDATED_DURATION);
        assertThat(testActivityExercise.getStartTime()).isEqualTo(UPDATED_START_TIME);
        assertThat(testActivityExercise.getEndTime()).isEqualTo(UPDATED_END_TIME);
    }

    @Test
    @Transactional
    void putNonExistingActivityExercise() throws Exception {
        int databaseSizeBeforeUpdate = activityExerciseRepository.findAll().size();
        activityExercise.setId(UUID.randomUUID());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restActivityExerciseMockMvc
            .perform(
                put(ENTITY_API_URL_ID, activityExercise.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(activityExercise))
            )
            .andExpect(status().isBadRequest());

        // Validate the ActivityExercise in the database
        List<ActivityExercise> activityExerciseList = activityExerciseRepository.findAll();
        assertThat(activityExerciseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchActivityExercise() throws Exception {
        int databaseSizeBeforeUpdate = activityExerciseRepository.findAll().size();
        activityExercise.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restActivityExerciseMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(activityExercise))
            )
            .andExpect(status().isBadRequest());

        // Validate the ActivityExercise in the database
        List<ActivityExercise> activityExerciseList = activityExerciseRepository.findAll();
        assertThat(activityExerciseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamActivityExercise() throws Exception {
        int databaseSizeBeforeUpdate = activityExerciseRepository.findAll().size();
        activityExercise.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restActivityExerciseMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(activityExercise))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ActivityExercise in the database
        List<ActivityExercise> activityExerciseList = activityExerciseRepository.findAll();
        assertThat(activityExerciseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateActivityExerciseWithPatch() throws Exception {
        // Initialize the database
        activityExerciseRepository.saveAndFlush(activityExercise);

        int databaseSizeBeforeUpdate = activityExerciseRepository.findAll().size();

        // Update the activityExercise using partial update
        ActivityExercise partialUpdatedActivityExercise = new ActivityExercise();
        partialUpdatedActivityExercise.setId(activityExercise.getId());

        partialUpdatedActivityExercise
            .usuarioId(UPDATED_USUARIO_ID)
            .exercise(UPDATED_EXERCISE)
            .repetitions(UPDATED_REPETITIONS)
            .typeResistence(UPDATED_TYPE_RESISTENCE)
            .duration(UPDATED_DURATION)
            .endTime(UPDATED_END_TIME);

        restActivityExerciseMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedActivityExercise.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedActivityExercise))
            )
            .andExpect(status().isOk());

        // Validate the ActivityExercise in the database
        List<ActivityExercise> activityExerciseList = activityExerciseRepository.findAll();
        assertThat(activityExerciseList).hasSize(databaseSizeBeforeUpdate);
        ActivityExercise testActivityExercise = activityExerciseList.get(activityExerciseList.size() - 1);
        assertThat(testActivityExercise.getUsuarioId()).isEqualTo(UPDATED_USUARIO_ID);
        assertThat(testActivityExercise.getEmpresaId()).isEqualTo(DEFAULT_EMPRESA_ID);
        assertThat(testActivityExercise.getExercise()).isEqualTo(UPDATED_EXERCISE);
        assertThat(testActivityExercise.getRepetitions()).isEqualTo(UPDATED_REPETITIONS);
        assertThat(testActivityExercise.getTypeResistence()).isEqualTo(UPDATED_TYPE_RESISTENCE);
        assertThat(testActivityExercise.getResistenceKg()).isEqualTo(DEFAULT_RESISTENCE_KG);
        assertThat(testActivityExercise.getDuration()).isEqualTo(UPDATED_DURATION);
        assertThat(testActivityExercise.getStartTime()).isEqualTo(DEFAULT_START_TIME);
        assertThat(testActivityExercise.getEndTime()).isEqualTo(UPDATED_END_TIME);
    }

    @Test
    @Transactional
    void fullUpdateActivityExerciseWithPatch() throws Exception {
        // Initialize the database
        activityExerciseRepository.saveAndFlush(activityExercise);

        int databaseSizeBeforeUpdate = activityExerciseRepository.findAll().size();

        // Update the activityExercise using partial update
        ActivityExercise partialUpdatedActivityExercise = new ActivityExercise();
        partialUpdatedActivityExercise.setId(activityExercise.getId());

        partialUpdatedActivityExercise
            .usuarioId(UPDATED_USUARIO_ID)
            .empresaId(UPDATED_EMPRESA_ID)
            .exercise(UPDATED_EXERCISE)
            .repetitions(UPDATED_REPETITIONS)
            .typeResistence(UPDATED_TYPE_RESISTENCE)
            .resistenceKg(UPDATED_RESISTENCE_KG)
            .duration(UPDATED_DURATION)
            .startTime(UPDATED_START_TIME)
            .endTime(UPDATED_END_TIME);

        restActivityExerciseMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedActivityExercise.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedActivityExercise))
            )
            .andExpect(status().isOk());

        // Validate the ActivityExercise in the database
        List<ActivityExercise> activityExerciseList = activityExerciseRepository.findAll();
        assertThat(activityExerciseList).hasSize(databaseSizeBeforeUpdate);
        ActivityExercise testActivityExercise = activityExerciseList.get(activityExerciseList.size() - 1);
        assertThat(testActivityExercise.getUsuarioId()).isEqualTo(UPDATED_USUARIO_ID);
        assertThat(testActivityExercise.getEmpresaId()).isEqualTo(UPDATED_EMPRESA_ID);
        assertThat(testActivityExercise.getExercise()).isEqualTo(UPDATED_EXERCISE);
        assertThat(testActivityExercise.getRepetitions()).isEqualTo(UPDATED_REPETITIONS);
        assertThat(testActivityExercise.getTypeResistence()).isEqualTo(UPDATED_TYPE_RESISTENCE);
        assertThat(testActivityExercise.getResistenceKg()).isEqualTo(UPDATED_RESISTENCE_KG);
        assertThat(testActivityExercise.getDuration()).isEqualTo(UPDATED_DURATION);
        assertThat(testActivityExercise.getStartTime()).isEqualTo(UPDATED_START_TIME);
        assertThat(testActivityExercise.getEndTime()).isEqualTo(UPDATED_END_TIME);
    }

    @Test
    @Transactional
    void patchNonExistingActivityExercise() throws Exception {
        int databaseSizeBeforeUpdate = activityExerciseRepository.findAll().size();
        activityExercise.setId(UUID.randomUUID());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restActivityExerciseMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, activityExercise.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(activityExercise))
            )
            .andExpect(status().isBadRequest());

        // Validate the ActivityExercise in the database
        List<ActivityExercise> activityExerciseList = activityExerciseRepository.findAll();
        assertThat(activityExerciseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchActivityExercise() throws Exception {
        int databaseSizeBeforeUpdate = activityExerciseRepository.findAll().size();
        activityExercise.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restActivityExerciseMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(activityExercise))
            )
            .andExpect(status().isBadRequest());

        // Validate the ActivityExercise in the database
        List<ActivityExercise> activityExerciseList = activityExerciseRepository.findAll();
        assertThat(activityExerciseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamActivityExercise() throws Exception {
        int databaseSizeBeforeUpdate = activityExerciseRepository.findAll().size();
        activityExercise.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restActivityExerciseMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(activityExercise))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ActivityExercise in the database
        List<ActivityExercise> activityExerciseList = activityExerciseRepository.findAll();
        assertThat(activityExerciseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteActivityExercise() throws Exception {
        // Initialize the database
        activityExerciseRepository.saveAndFlush(activityExercise);

        int databaseSizeBeforeDelete = activityExerciseRepository.findAll().size();

        // Delete the activityExercise
        restActivityExerciseMockMvc
            .perform(delete(ENTITY_API_URL_ID, activityExercise.getId().toString()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ActivityExercise> activityExerciseList = activityExerciseRepository.findAll();
        assertThat(activityExerciseList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
