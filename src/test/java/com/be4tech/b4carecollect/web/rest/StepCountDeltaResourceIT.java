package com.be4tech.b4carecollect.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.be4tech.b4carecollect.IntegrationTest;
import com.be4tech.b4carecollect.domain.StepCountDelta;
import com.be4tech.b4carecollect.repository.StepCountDeltaRepository;
import com.be4tech.b4carecollect.service.criteria.StepCountDeltaCriteria;
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
 * Integration tests for the {@link StepCountDeltaResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class StepCountDeltaResourceIT {

    private static final String DEFAULT_USUARIO_ID = "AAAAAAAAAA";
    private static final String UPDATED_USUARIO_ID = "BBBBBBBBBB";

    private static final String DEFAULT_EMPRESA_ID = "AAAAAAAAAA";
    private static final String UPDATED_EMPRESA_ID = "BBBBBBBBBB";

    private static final Integer DEFAULT_STEPS = 1;
    private static final Integer UPDATED_STEPS = 2;
    private static final Integer SMALLER_STEPS = 1 - 1;

    private static final Instant DEFAULT_START_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_START_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_END_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_END_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/step-count-deltas";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private StepCountDeltaRepository stepCountDeltaRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restStepCountDeltaMockMvc;

    private StepCountDelta stepCountDelta;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static StepCountDelta createEntity(EntityManager em) {
        StepCountDelta stepCountDelta = new StepCountDelta()
            .usuarioId(DEFAULT_USUARIO_ID)
            .empresaId(DEFAULT_EMPRESA_ID)
            .steps(DEFAULT_STEPS)
            .startTime(DEFAULT_START_TIME)
            .endTime(DEFAULT_END_TIME);
        return stepCountDelta;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static StepCountDelta createUpdatedEntity(EntityManager em) {
        StepCountDelta stepCountDelta = new StepCountDelta()
            .usuarioId(UPDATED_USUARIO_ID)
            .empresaId(UPDATED_EMPRESA_ID)
            .steps(UPDATED_STEPS)
            .startTime(UPDATED_START_TIME)
            .endTime(UPDATED_END_TIME);
        return stepCountDelta;
    }

    @BeforeEach
    public void initTest() {
        stepCountDelta = createEntity(em);
    }

    @Test
    @Transactional
    void createStepCountDelta() throws Exception {
        int databaseSizeBeforeCreate = stepCountDeltaRepository.findAll().size();
        // Create the StepCountDelta
        restStepCountDeltaMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(stepCountDelta))
            )
            .andExpect(status().isCreated());

        // Validate the StepCountDelta in the database
        List<StepCountDelta> stepCountDeltaList = stepCountDeltaRepository.findAll();
        assertThat(stepCountDeltaList).hasSize(databaseSizeBeforeCreate + 1);
        StepCountDelta testStepCountDelta = stepCountDeltaList.get(stepCountDeltaList.size() - 1);
        assertThat(testStepCountDelta.getUsuarioId()).isEqualTo(DEFAULT_USUARIO_ID);
        assertThat(testStepCountDelta.getEmpresaId()).isEqualTo(DEFAULT_EMPRESA_ID);
        assertThat(testStepCountDelta.getSteps()).isEqualTo(DEFAULT_STEPS);
        assertThat(testStepCountDelta.getStartTime()).isEqualTo(DEFAULT_START_TIME);
        assertThat(testStepCountDelta.getEndTime()).isEqualTo(DEFAULT_END_TIME);
    }

    @Test
    @Transactional
    void createStepCountDeltaWithExistingId() throws Exception {
        // Create the StepCountDelta with an existing ID
        stepCountDeltaRepository.saveAndFlush(stepCountDelta);

        int databaseSizeBeforeCreate = stepCountDeltaRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restStepCountDeltaMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(stepCountDelta))
            )
            .andExpect(status().isBadRequest());

        // Validate the StepCountDelta in the database
        List<StepCountDelta> stepCountDeltaList = stepCountDeltaRepository.findAll();
        assertThat(stepCountDeltaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllStepCountDeltas() throws Exception {
        // Initialize the database
        stepCountDeltaRepository.saveAndFlush(stepCountDelta);

        // Get all the stepCountDeltaList
        restStepCountDeltaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(stepCountDelta.getId().toString())))
            .andExpect(jsonPath("$.[*].usuarioId").value(hasItem(DEFAULT_USUARIO_ID)))
            .andExpect(jsonPath("$.[*].empresaId").value(hasItem(DEFAULT_EMPRESA_ID)))
            .andExpect(jsonPath("$.[*].steps").value(hasItem(DEFAULT_STEPS)))
            .andExpect(jsonPath("$.[*].startTime").value(hasItem(DEFAULT_START_TIME.toString())))
            .andExpect(jsonPath("$.[*].endTime").value(hasItem(DEFAULT_END_TIME.toString())));
    }

    @Test
    @Transactional
    void getStepCountDelta() throws Exception {
        // Initialize the database
        stepCountDeltaRepository.saveAndFlush(stepCountDelta);

        // Get the stepCountDelta
        restStepCountDeltaMockMvc
            .perform(get(ENTITY_API_URL_ID, stepCountDelta.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(stepCountDelta.getId().toString()))
            .andExpect(jsonPath("$.usuarioId").value(DEFAULT_USUARIO_ID))
            .andExpect(jsonPath("$.empresaId").value(DEFAULT_EMPRESA_ID))
            .andExpect(jsonPath("$.steps").value(DEFAULT_STEPS))
            .andExpect(jsonPath("$.startTime").value(DEFAULT_START_TIME.toString()))
            .andExpect(jsonPath("$.endTime").value(DEFAULT_END_TIME.toString()));
    }

    @Test
    @Transactional
    void getStepCountDeltasByIdFiltering() throws Exception {
        // Initialize the database
        stepCountDeltaRepository.saveAndFlush(stepCountDelta);

        UUID id = stepCountDelta.getId();

        defaultStepCountDeltaShouldBeFound("id.equals=" + id);
        defaultStepCountDeltaShouldNotBeFound("id.notEquals=" + id);
    }

    @Test
    @Transactional
    void getAllStepCountDeltasByUsuarioIdIsEqualToSomething() throws Exception {
        // Initialize the database
        stepCountDeltaRepository.saveAndFlush(stepCountDelta);

        // Get all the stepCountDeltaList where usuarioId equals to DEFAULT_USUARIO_ID
        defaultStepCountDeltaShouldBeFound("usuarioId.equals=" + DEFAULT_USUARIO_ID);

        // Get all the stepCountDeltaList where usuarioId equals to UPDATED_USUARIO_ID
        defaultStepCountDeltaShouldNotBeFound("usuarioId.equals=" + UPDATED_USUARIO_ID);
    }

    @Test
    @Transactional
    void getAllStepCountDeltasByUsuarioIdIsInShouldWork() throws Exception {
        // Initialize the database
        stepCountDeltaRepository.saveAndFlush(stepCountDelta);

        // Get all the stepCountDeltaList where usuarioId in DEFAULT_USUARIO_ID or UPDATED_USUARIO_ID
        defaultStepCountDeltaShouldBeFound("usuarioId.in=" + DEFAULT_USUARIO_ID + "," + UPDATED_USUARIO_ID);

        // Get all the stepCountDeltaList where usuarioId equals to UPDATED_USUARIO_ID
        defaultStepCountDeltaShouldNotBeFound("usuarioId.in=" + UPDATED_USUARIO_ID);
    }

    @Test
    @Transactional
    void getAllStepCountDeltasByUsuarioIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        stepCountDeltaRepository.saveAndFlush(stepCountDelta);

        // Get all the stepCountDeltaList where usuarioId is not null
        defaultStepCountDeltaShouldBeFound("usuarioId.specified=true");

        // Get all the stepCountDeltaList where usuarioId is null
        defaultStepCountDeltaShouldNotBeFound("usuarioId.specified=false");
    }

    @Test
    @Transactional
    void getAllStepCountDeltasByUsuarioIdContainsSomething() throws Exception {
        // Initialize the database
        stepCountDeltaRepository.saveAndFlush(stepCountDelta);

        // Get all the stepCountDeltaList where usuarioId contains DEFAULT_USUARIO_ID
        defaultStepCountDeltaShouldBeFound("usuarioId.contains=" + DEFAULT_USUARIO_ID);

        // Get all the stepCountDeltaList where usuarioId contains UPDATED_USUARIO_ID
        defaultStepCountDeltaShouldNotBeFound("usuarioId.contains=" + UPDATED_USUARIO_ID);
    }

    @Test
    @Transactional
    void getAllStepCountDeltasByUsuarioIdNotContainsSomething() throws Exception {
        // Initialize the database
        stepCountDeltaRepository.saveAndFlush(stepCountDelta);

        // Get all the stepCountDeltaList where usuarioId does not contain DEFAULT_USUARIO_ID
        defaultStepCountDeltaShouldNotBeFound("usuarioId.doesNotContain=" + DEFAULT_USUARIO_ID);

        // Get all the stepCountDeltaList where usuarioId does not contain UPDATED_USUARIO_ID
        defaultStepCountDeltaShouldBeFound("usuarioId.doesNotContain=" + UPDATED_USUARIO_ID);
    }

    @Test
    @Transactional
    void getAllStepCountDeltasByEmpresaIdIsEqualToSomething() throws Exception {
        // Initialize the database
        stepCountDeltaRepository.saveAndFlush(stepCountDelta);

        // Get all the stepCountDeltaList where empresaId equals to DEFAULT_EMPRESA_ID
        defaultStepCountDeltaShouldBeFound("empresaId.equals=" + DEFAULT_EMPRESA_ID);

        // Get all the stepCountDeltaList where empresaId equals to UPDATED_EMPRESA_ID
        defaultStepCountDeltaShouldNotBeFound("empresaId.equals=" + UPDATED_EMPRESA_ID);
    }

    @Test
    @Transactional
    void getAllStepCountDeltasByEmpresaIdIsInShouldWork() throws Exception {
        // Initialize the database
        stepCountDeltaRepository.saveAndFlush(stepCountDelta);

        // Get all the stepCountDeltaList where empresaId in DEFAULT_EMPRESA_ID or UPDATED_EMPRESA_ID
        defaultStepCountDeltaShouldBeFound("empresaId.in=" + DEFAULT_EMPRESA_ID + "," + UPDATED_EMPRESA_ID);

        // Get all the stepCountDeltaList where empresaId equals to UPDATED_EMPRESA_ID
        defaultStepCountDeltaShouldNotBeFound("empresaId.in=" + UPDATED_EMPRESA_ID);
    }

    @Test
    @Transactional
    void getAllStepCountDeltasByEmpresaIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        stepCountDeltaRepository.saveAndFlush(stepCountDelta);

        // Get all the stepCountDeltaList where empresaId is not null
        defaultStepCountDeltaShouldBeFound("empresaId.specified=true");

        // Get all the stepCountDeltaList where empresaId is null
        defaultStepCountDeltaShouldNotBeFound("empresaId.specified=false");
    }

    @Test
    @Transactional
    void getAllStepCountDeltasByEmpresaIdContainsSomething() throws Exception {
        // Initialize the database
        stepCountDeltaRepository.saveAndFlush(stepCountDelta);

        // Get all the stepCountDeltaList where empresaId contains DEFAULT_EMPRESA_ID
        defaultStepCountDeltaShouldBeFound("empresaId.contains=" + DEFAULT_EMPRESA_ID);

        // Get all the stepCountDeltaList where empresaId contains UPDATED_EMPRESA_ID
        defaultStepCountDeltaShouldNotBeFound("empresaId.contains=" + UPDATED_EMPRESA_ID);
    }

    @Test
    @Transactional
    void getAllStepCountDeltasByEmpresaIdNotContainsSomething() throws Exception {
        // Initialize the database
        stepCountDeltaRepository.saveAndFlush(stepCountDelta);

        // Get all the stepCountDeltaList where empresaId does not contain DEFAULT_EMPRESA_ID
        defaultStepCountDeltaShouldNotBeFound("empresaId.doesNotContain=" + DEFAULT_EMPRESA_ID);

        // Get all the stepCountDeltaList where empresaId does not contain UPDATED_EMPRESA_ID
        defaultStepCountDeltaShouldBeFound("empresaId.doesNotContain=" + UPDATED_EMPRESA_ID);
    }

    @Test
    @Transactional
    void getAllStepCountDeltasByStepsIsEqualToSomething() throws Exception {
        // Initialize the database
        stepCountDeltaRepository.saveAndFlush(stepCountDelta);

        // Get all the stepCountDeltaList where steps equals to DEFAULT_STEPS
        defaultStepCountDeltaShouldBeFound("steps.equals=" + DEFAULT_STEPS);

        // Get all the stepCountDeltaList where steps equals to UPDATED_STEPS
        defaultStepCountDeltaShouldNotBeFound("steps.equals=" + UPDATED_STEPS);
    }

    @Test
    @Transactional
    void getAllStepCountDeltasByStepsIsInShouldWork() throws Exception {
        // Initialize the database
        stepCountDeltaRepository.saveAndFlush(stepCountDelta);

        // Get all the stepCountDeltaList where steps in DEFAULT_STEPS or UPDATED_STEPS
        defaultStepCountDeltaShouldBeFound("steps.in=" + DEFAULT_STEPS + "," + UPDATED_STEPS);

        // Get all the stepCountDeltaList where steps equals to UPDATED_STEPS
        defaultStepCountDeltaShouldNotBeFound("steps.in=" + UPDATED_STEPS);
    }

    @Test
    @Transactional
    void getAllStepCountDeltasByStepsIsNullOrNotNull() throws Exception {
        // Initialize the database
        stepCountDeltaRepository.saveAndFlush(stepCountDelta);

        // Get all the stepCountDeltaList where steps is not null
        defaultStepCountDeltaShouldBeFound("steps.specified=true");

        // Get all the stepCountDeltaList where steps is null
        defaultStepCountDeltaShouldNotBeFound("steps.specified=false");
    }

    @Test
    @Transactional
    void getAllStepCountDeltasByStepsIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        stepCountDeltaRepository.saveAndFlush(stepCountDelta);

        // Get all the stepCountDeltaList where steps is greater than or equal to DEFAULT_STEPS
        defaultStepCountDeltaShouldBeFound("steps.greaterThanOrEqual=" + DEFAULT_STEPS);

        // Get all the stepCountDeltaList where steps is greater than or equal to UPDATED_STEPS
        defaultStepCountDeltaShouldNotBeFound("steps.greaterThanOrEqual=" + UPDATED_STEPS);
    }

    @Test
    @Transactional
    void getAllStepCountDeltasByStepsIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        stepCountDeltaRepository.saveAndFlush(stepCountDelta);

        // Get all the stepCountDeltaList where steps is less than or equal to DEFAULT_STEPS
        defaultStepCountDeltaShouldBeFound("steps.lessThanOrEqual=" + DEFAULT_STEPS);

        // Get all the stepCountDeltaList where steps is less than or equal to SMALLER_STEPS
        defaultStepCountDeltaShouldNotBeFound("steps.lessThanOrEqual=" + SMALLER_STEPS);
    }

    @Test
    @Transactional
    void getAllStepCountDeltasByStepsIsLessThanSomething() throws Exception {
        // Initialize the database
        stepCountDeltaRepository.saveAndFlush(stepCountDelta);

        // Get all the stepCountDeltaList where steps is less than DEFAULT_STEPS
        defaultStepCountDeltaShouldNotBeFound("steps.lessThan=" + DEFAULT_STEPS);

        // Get all the stepCountDeltaList where steps is less than UPDATED_STEPS
        defaultStepCountDeltaShouldBeFound("steps.lessThan=" + UPDATED_STEPS);
    }

    @Test
    @Transactional
    void getAllStepCountDeltasByStepsIsGreaterThanSomething() throws Exception {
        // Initialize the database
        stepCountDeltaRepository.saveAndFlush(stepCountDelta);

        // Get all the stepCountDeltaList where steps is greater than DEFAULT_STEPS
        defaultStepCountDeltaShouldNotBeFound("steps.greaterThan=" + DEFAULT_STEPS);

        // Get all the stepCountDeltaList where steps is greater than SMALLER_STEPS
        defaultStepCountDeltaShouldBeFound("steps.greaterThan=" + SMALLER_STEPS);
    }

    @Test
    @Transactional
    void getAllStepCountDeltasByStartTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        stepCountDeltaRepository.saveAndFlush(stepCountDelta);

        // Get all the stepCountDeltaList where startTime equals to DEFAULT_START_TIME
        defaultStepCountDeltaShouldBeFound("startTime.equals=" + DEFAULT_START_TIME);

        // Get all the stepCountDeltaList where startTime equals to UPDATED_START_TIME
        defaultStepCountDeltaShouldNotBeFound("startTime.equals=" + UPDATED_START_TIME);
    }

    @Test
    @Transactional
    void getAllStepCountDeltasByStartTimeIsInShouldWork() throws Exception {
        // Initialize the database
        stepCountDeltaRepository.saveAndFlush(stepCountDelta);

        // Get all the stepCountDeltaList where startTime in DEFAULT_START_TIME or UPDATED_START_TIME
        defaultStepCountDeltaShouldBeFound("startTime.in=" + DEFAULT_START_TIME + "," + UPDATED_START_TIME);

        // Get all the stepCountDeltaList where startTime equals to UPDATED_START_TIME
        defaultStepCountDeltaShouldNotBeFound("startTime.in=" + UPDATED_START_TIME);
    }

    @Test
    @Transactional
    void getAllStepCountDeltasByStartTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        stepCountDeltaRepository.saveAndFlush(stepCountDelta);

        // Get all the stepCountDeltaList where startTime is not null
        defaultStepCountDeltaShouldBeFound("startTime.specified=true");

        // Get all the stepCountDeltaList where startTime is null
        defaultStepCountDeltaShouldNotBeFound("startTime.specified=false");
    }

    @Test
    @Transactional
    void getAllStepCountDeltasByEndTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        stepCountDeltaRepository.saveAndFlush(stepCountDelta);

        // Get all the stepCountDeltaList where endTime equals to DEFAULT_END_TIME
        defaultStepCountDeltaShouldBeFound("endTime.equals=" + DEFAULT_END_TIME);

        // Get all the stepCountDeltaList where endTime equals to UPDATED_END_TIME
        defaultStepCountDeltaShouldNotBeFound("endTime.equals=" + UPDATED_END_TIME);
    }

    @Test
    @Transactional
    void getAllStepCountDeltasByEndTimeIsInShouldWork() throws Exception {
        // Initialize the database
        stepCountDeltaRepository.saveAndFlush(stepCountDelta);

        // Get all the stepCountDeltaList where endTime in DEFAULT_END_TIME or UPDATED_END_TIME
        defaultStepCountDeltaShouldBeFound("endTime.in=" + DEFAULT_END_TIME + "," + UPDATED_END_TIME);

        // Get all the stepCountDeltaList where endTime equals to UPDATED_END_TIME
        defaultStepCountDeltaShouldNotBeFound("endTime.in=" + UPDATED_END_TIME);
    }

    @Test
    @Transactional
    void getAllStepCountDeltasByEndTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        stepCountDeltaRepository.saveAndFlush(stepCountDelta);

        // Get all the stepCountDeltaList where endTime is not null
        defaultStepCountDeltaShouldBeFound("endTime.specified=true");

        // Get all the stepCountDeltaList where endTime is null
        defaultStepCountDeltaShouldNotBeFound("endTime.specified=false");
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultStepCountDeltaShouldBeFound(String filter) throws Exception {
        restStepCountDeltaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(stepCountDelta.getId().toString())))
            .andExpect(jsonPath("$.[*].usuarioId").value(hasItem(DEFAULT_USUARIO_ID)))
            .andExpect(jsonPath("$.[*].empresaId").value(hasItem(DEFAULT_EMPRESA_ID)))
            .andExpect(jsonPath("$.[*].steps").value(hasItem(DEFAULT_STEPS)))
            .andExpect(jsonPath("$.[*].startTime").value(hasItem(DEFAULT_START_TIME.toString())))
            .andExpect(jsonPath("$.[*].endTime").value(hasItem(DEFAULT_END_TIME.toString())));

        // Check, that the count call also returns 1
        restStepCountDeltaMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultStepCountDeltaShouldNotBeFound(String filter) throws Exception {
        restStepCountDeltaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restStepCountDeltaMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingStepCountDelta() throws Exception {
        // Get the stepCountDelta
        restStepCountDeltaMockMvc.perform(get(ENTITY_API_URL_ID, UUID.randomUUID().toString())).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingStepCountDelta() throws Exception {
        // Initialize the database
        stepCountDeltaRepository.saveAndFlush(stepCountDelta);

        int databaseSizeBeforeUpdate = stepCountDeltaRepository.findAll().size();

        // Update the stepCountDelta
        StepCountDelta updatedStepCountDelta = stepCountDeltaRepository.findById(stepCountDelta.getId()).get();
        // Disconnect from session so that the updates on updatedStepCountDelta are not directly saved in db
        em.detach(updatedStepCountDelta);
        updatedStepCountDelta
            .usuarioId(UPDATED_USUARIO_ID)
            .empresaId(UPDATED_EMPRESA_ID)
            .steps(UPDATED_STEPS)
            .startTime(UPDATED_START_TIME)
            .endTime(UPDATED_END_TIME);

        restStepCountDeltaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedStepCountDelta.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedStepCountDelta))
            )
            .andExpect(status().isOk());

        // Validate the StepCountDelta in the database
        List<StepCountDelta> stepCountDeltaList = stepCountDeltaRepository.findAll();
        assertThat(stepCountDeltaList).hasSize(databaseSizeBeforeUpdate);
        StepCountDelta testStepCountDelta = stepCountDeltaList.get(stepCountDeltaList.size() - 1);
        assertThat(testStepCountDelta.getUsuarioId()).isEqualTo(UPDATED_USUARIO_ID);
        assertThat(testStepCountDelta.getEmpresaId()).isEqualTo(UPDATED_EMPRESA_ID);
        assertThat(testStepCountDelta.getSteps()).isEqualTo(UPDATED_STEPS);
        assertThat(testStepCountDelta.getStartTime()).isEqualTo(UPDATED_START_TIME);
        assertThat(testStepCountDelta.getEndTime()).isEqualTo(UPDATED_END_TIME);
    }

    @Test
    @Transactional
    void putNonExistingStepCountDelta() throws Exception {
        int databaseSizeBeforeUpdate = stepCountDeltaRepository.findAll().size();
        stepCountDelta.setId(UUID.randomUUID());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStepCountDeltaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, stepCountDelta.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(stepCountDelta))
            )
            .andExpect(status().isBadRequest());

        // Validate the StepCountDelta in the database
        List<StepCountDelta> stepCountDeltaList = stepCountDeltaRepository.findAll();
        assertThat(stepCountDeltaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchStepCountDelta() throws Exception {
        int databaseSizeBeforeUpdate = stepCountDeltaRepository.findAll().size();
        stepCountDelta.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStepCountDeltaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(stepCountDelta))
            )
            .andExpect(status().isBadRequest());

        // Validate the StepCountDelta in the database
        List<StepCountDelta> stepCountDeltaList = stepCountDeltaRepository.findAll();
        assertThat(stepCountDeltaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamStepCountDelta() throws Exception {
        int databaseSizeBeforeUpdate = stepCountDeltaRepository.findAll().size();
        stepCountDelta.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStepCountDeltaMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(stepCountDelta)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the StepCountDelta in the database
        List<StepCountDelta> stepCountDeltaList = stepCountDeltaRepository.findAll();
        assertThat(stepCountDeltaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateStepCountDeltaWithPatch() throws Exception {
        // Initialize the database
        stepCountDeltaRepository.saveAndFlush(stepCountDelta);

        int databaseSizeBeforeUpdate = stepCountDeltaRepository.findAll().size();

        // Update the stepCountDelta using partial update
        StepCountDelta partialUpdatedStepCountDelta = new StepCountDelta();
        partialUpdatedStepCountDelta.setId(stepCountDelta.getId());

        partialUpdatedStepCountDelta
            .usuarioId(UPDATED_USUARIO_ID)
            .empresaId(UPDATED_EMPRESA_ID)
            .steps(UPDATED_STEPS)
            .endTime(UPDATED_END_TIME);

        restStepCountDeltaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedStepCountDelta.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedStepCountDelta))
            )
            .andExpect(status().isOk());

        // Validate the StepCountDelta in the database
        List<StepCountDelta> stepCountDeltaList = stepCountDeltaRepository.findAll();
        assertThat(stepCountDeltaList).hasSize(databaseSizeBeforeUpdate);
        StepCountDelta testStepCountDelta = stepCountDeltaList.get(stepCountDeltaList.size() - 1);
        assertThat(testStepCountDelta.getUsuarioId()).isEqualTo(UPDATED_USUARIO_ID);
        assertThat(testStepCountDelta.getEmpresaId()).isEqualTo(UPDATED_EMPRESA_ID);
        assertThat(testStepCountDelta.getSteps()).isEqualTo(UPDATED_STEPS);
        assertThat(testStepCountDelta.getStartTime()).isEqualTo(DEFAULT_START_TIME);
        assertThat(testStepCountDelta.getEndTime()).isEqualTo(UPDATED_END_TIME);
    }

    @Test
    @Transactional
    void fullUpdateStepCountDeltaWithPatch() throws Exception {
        // Initialize the database
        stepCountDeltaRepository.saveAndFlush(stepCountDelta);

        int databaseSizeBeforeUpdate = stepCountDeltaRepository.findAll().size();

        // Update the stepCountDelta using partial update
        StepCountDelta partialUpdatedStepCountDelta = new StepCountDelta();
        partialUpdatedStepCountDelta.setId(stepCountDelta.getId());

        partialUpdatedStepCountDelta
            .usuarioId(UPDATED_USUARIO_ID)
            .empresaId(UPDATED_EMPRESA_ID)
            .steps(UPDATED_STEPS)
            .startTime(UPDATED_START_TIME)
            .endTime(UPDATED_END_TIME);

        restStepCountDeltaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedStepCountDelta.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedStepCountDelta))
            )
            .andExpect(status().isOk());

        // Validate the StepCountDelta in the database
        List<StepCountDelta> stepCountDeltaList = stepCountDeltaRepository.findAll();
        assertThat(stepCountDeltaList).hasSize(databaseSizeBeforeUpdate);
        StepCountDelta testStepCountDelta = stepCountDeltaList.get(stepCountDeltaList.size() - 1);
        assertThat(testStepCountDelta.getUsuarioId()).isEqualTo(UPDATED_USUARIO_ID);
        assertThat(testStepCountDelta.getEmpresaId()).isEqualTo(UPDATED_EMPRESA_ID);
        assertThat(testStepCountDelta.getSteps()).isEqualTo(UPDATED_STEPS);
        assertThat(testStepCountDelta.getStartTime()).isEqualTo(UPDATED_START_TIME);
        assertThat(testStepCountDelta.getEndTime()).isEqualTo(UPDATED_END_TIME);
    }

    @Test
    @Transactional
    void patchNonExistingStepCountDelta() throws Exception {
        int databaseSizeBeforeUpdate = stepCountDeltaRepository.findAll().size();
        stepCountDelta.setId(UUID.randomUUID());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStepCountDeltaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, stepCountDelta.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(stepCountDelta))
            )
            .andExpect(status().isBadRequest());

        // Validate the StepCountDelta in the database
        List<StepCountDelta> stepCountDeltaList = stepCountDeltaRepository.findAll();
        assertThat(stepCountDeltaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchStepCountDelta() throws Exception {
        int databaseSizeBeforeUpdate = stepCountDeltaRepository.findAll().size();
        stepCountDelta.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStepCountDeltaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(stepCountDelta))
            )
            .andExpect(status().isBadRequest());

        // Validate the StepCountDelta in the database
        List<StepCountDelta> stepCountDeltaList = stepCountDeltaRepository.findAll();
        assertThat(stepCountDeltaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamStepCountDelta() throws Exception {
        int databaseSizeBeforeUpdate = stepCountDeltaRepository.findAll().size();
        stepCountDelta.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStepCountDeltaMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(stepCountDelta))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the StepCountDelta in the database
        List<StepCountDelta> stepCountDeltaList = stepCountDeltaRepository.findAll();
        assertThat(stepCountDeltaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteStepCountDelta() throws Exception {
        // Initialize the database
        stepCountDeltaRepository.saveAndFlush(stepCountDelta);

        int databaseSizeBeforeDelete = stepCountDeltaRepository.findAll().size();

        // Delete the stepCountDelta
        restStepCountDeltaMockMvc
            .perform(delete(ENTITY_API_URL_ID, stepCountDelta.getId().toString()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<StepCountDelta> stepCountDeltaList = stepCountDeltaRepository.findAll();
        assertThat(stepCountDeltaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
