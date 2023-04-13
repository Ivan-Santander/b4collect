package com.be4tech.b4carecollect.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.be4tech.b4carecollect.IntegrationTest;
import com.be4tech.b4carecollect.domain.StepCountCadence;
import com.be4tech.b4carecollect.repository.StepCountCadenceRepository;
import com.be4tech.b4carecollect.service.criteria.StepCountCadenceCriteria;
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
 * Integration tests for the {@link StepCountCadenceResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class StepCountCadenceResourceIT {

    private static final String DEFAULT_USUARIO_ID = "AAAAAAAAAA";
    private static final String UPDATED_USUARIO_ID = "BBBBBBBBBB";

    private static final String DEFAULT_EMPRESA_ID = "AAAAAAAAAA";
    private static final String UPDATED_EMPRESA_ID = "BBBBBBBBBB";

    private static final Integer DEFAULT_RPM = 1;
    private static final Integer UPDATED_RPM = 2;
    private static final Integer SMALLER_RPM = 1 - 1;

    private static final Instant DEFAULT_START_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_START_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_END_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_END_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/step-count-cadences";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private StepCountCadenceRepository stepCountCadenceRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restStepCountCadenceMockMvc;

    private StepCountCadence stepCountCadence;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static StepCountCadence createEntity(EntityManager em) {
        StepCountCadence stepCountCadence = new StepCountCadence()
            .usuarioId(DEFAULT_USUARIO_ID)
            .empresaId(DEFAULT_EMPRESA_ID)
            .rpm(DEFAULT_RPM)
            .startTime(DEFAULT_START_TIME)
            .endTime(DEFAULT_END_TIME);
        return stepCountCadence;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static StepCountCadence createUpdatedEntity(EntityManager em) {
        StepCountCadence stepCountCadence = new StepCountCadence()
            .usuarioId(UPDATED_USUARIO_ID)
            .empresaId(UPDATED_EMPRESA_ID)
            .rpm(UPDATED_RPM)
            .startTime(UPDATED_START_TIME)
            .endTime(UPDATED_END_TIME);
        return stepCountCadence;
    }

    @BeforeEach
    public void initTest() {
        stepCountCadence = createEntity(em);
    }

    @Test
    @Transactional
    void createStepCountCadence() throws Exception {
        int databaseSizeBeforeCreate = stepCountCadenceRepository.findAll().size();
        // Create the StepCountCadence
        restStepCountCadenceMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(stepCountCadence))
            )
            .andExpect(status().isCreated());

        // Validate the StepCountCadence in the database
        List<StepCountCadence> stepCountCadenceList = stepCountCadenceRepository.findAll();
        assertThat(stepCountCadenceList).hasSize(databaseSizeBeforeCreate + 1);
        StepCountCadence testStepCountCadence = stepCountCadenceList.get(stepCountCadenceList.size() - 1);
        assertThat(testStepCountCadence.getUsuarioId()).isEqualTo(DEFAULT_USUARIO_ID);
        assertThat(testStepCountCadence.getEmpresaId()).isEqualTo(DEFAULT_EMPRESA_ID);
        assertThat(testStepCountCadence.getRpm()).isEqualTo(DEFAULT_RPM);
        assertThat(testStepCountCadence.getStartTime()).isEqualTo(DEFAULT_START_TIME);
        assertThat(testStepCountCadence.getEndTime()).isEqualTo(DEFAULT_END_TIME);
    }

    @Test
    @Transactional
    void createStepCountCadenceWithExistingId() throws Exception {
        // Create the StepCountCadence with an existing ID
        stepCountCadenceRepository.saveAndFlush(stepCountCadence);

        int databaseSizeBeforeCreate = stepCountCadenceRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restStepCountCadenceMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(stepCountCadence))
            )
            .andExpect(status().isBadRequest());

        // Validate the StepCountCadence in the database
        List<StepCountCadence> stepCountCadenceList = stepCountCadenceRepository.findAll();
        assertThat(stepCountCadenceList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllStepCountCadences() throws Exception {
        // Initialize the database
        stepCountCadenceRepository.saveAndFlush(stepCountCadence);

        // Get all the stepCountCadenceList
        restStepCountCadenceMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(stepCountCadence.getId().toString())))
            .andExpect(jsonPath("$.[*].usuarioId").value(hasItem(DEFAULT_USUARIO_ID)))
            .andExpect(jsonPath("$.[*].empresaId").value(hasItem(DEFAULT_EMPRESA_ID)))
            .andExpect(jsonPath("$.[*].rpm").value(hasItem(DEFAULT_RPM)))
            .andExpect(jsonPath("$.[*].startTime").value(hasItem(DEFAULT_START_TIME.toString())))
            .andExpect(jsonPath("$.[*].endTime").value(hasItem(DEFAULT_END_TIME.toString())));
    }

    @Test
    @Transactional
    void getStepCountCadence() throws Exception {
        // Initialize the database
        stepCountCadenceRepository.saveAndFlush(stepCountCadence);

        // Get the stepCountCadence
        restStepCountCadenceMockMvc
            .perform(get(ENTITY_API_URL_ID, stepCountCadence.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(stepCountCadence.getId().toString()))
            .andExpect(jsonPath("$.usuarioId").value(DEFAULT_USUARIO_ID))
            .andExpect(jsonPath("$.empresaId").value(DEFAULT_EMPRESA_ID))
            .andExpect(jsonPath("$.rpm").value(DEFAULT_RPM))
            .andExpect(jsonPath("$.startTime").value(DEFAULT_START_TIME.toString()))
            .andExpect(jsonPath("$.endTime").value(DEFAULT_END_TIME.toString()));
    }

    @Test
    @Transactional
    void getStepCountCadencesByIdFiltering() throws Exception {
        // Initialize the database
        stepCountCadenceRepository.saveAndFlush(stepCountCadence);

        UUID id = stepCountCadence.getId();

        defaultStepCountCadenceShouldBeFound("id.equals=" + id);
        defaultStepCountCadenceShouldNotBeFound("id.notEquals=" + id);
    }

    @Test
    @Transactional
    void getAllStepCountCadencesByUsuarioIdIsEqualToSomething() throws Exception {
        // Initialize the database
        stepCountCadenceRepository.saveAndFlush(stepCountCadence);

        // Get all the stepCountCadenceList where usuarioId equals to DEFAULT_USUARIO_ID
        defaultStepCountCadenceShouldBeFound("usuarioId.equals=" + DEFAULT_USUARIO_ID);

        // Get all the stepCountCadenceList where usuarioId equals to UPDATED_USUARIO_ID
        defaultStepCountCadenceShouldNotBeFound("usuarioId.equals=" + UPDATED_USUARIO_ID);
    }

    @Test
    @Transactional
    void getAllStepCountCadencesByUsuarioIdIsInShouldWork() throws Exception {
        // Initialize the database
        stepCountCadenceRepository.saveAndFlush(stepCountCadence);

        // Get all the stepCountCadenceList where usuarioId in DEFAULT_USUARIO_ID or UPDATED_USUARIO_ID
        defaultStepCountCadenceShouldBeFound("usuarioId.in=" + DEFAULT_USUARIO_ID + "," + UPDATED_USUARIO_ID);

        // Get all the stepCountCadenceList where usuarioId equals to UPDATED_USUARIO_ID
        defaultStepCountCadenceShouldNotBeFound("usuarioId.in=" + UPDATED_USUARIO_ID);
    }

    @Test
    @Transactional
    void getAllStepCountCadencesByUsuarioIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        stepCountCadenceRepository.saveAndFlush(stepCountCadence);

        // Get all the stepCountCadenceList where usuarioId is not null
        defaultStepCountCadenceShouldBeFound("usuarioId.specified=true");

        // Get all the stepCountCadenceList where usuarioId is null
        defaultStepCountCadenceShouldNotBeFound("usuarioId.specified=false");
    }

    @Test
    @Transactional
    void getAllStepCountCadencesByUsuarioIdContainsSomething() throws Exception {
        // Initialize the database
        stepCountCadenceRepository.saveAndFlush(stepCountCadence);

        // Get all the stepCountCadenceList where usuarioId contains DEFAULT_USUARIO_ID
        defaultStepCountCadenceShouldBeFound("usuarioId.contains=" + DEFAULT_USUARIO_ID);

        // Get all the stepCountCadenceList where usuarioId contains UPDATED_USUARIO_ID
        defaultStepCountCadenceShouldNotBeFound("usuarioId.contains=" + UPDATED_USUARIO_ID);
    }

    @Test
    @Transactional
    void getAllStepCountCadencesByUsuarioIdNotContainsSomething() throws Exception {
        // Initialize the database
        stepCountCadenceRepository.saveAndFlush(stepCountCadence);

        // Get all the stepCountCadenceList where usuarioId does not contain DEFAULT_USUARIO_ID
        defaultStepCountCadenceShouldNotBeFound("usuarioId.doesNotContain=" + DEFAULT_USUARIO_ID);

        // Get all the stepCountCadenceList where usuarioId does not contain UPDATED_USUARIO_ID
        defaultStepCountCadenceShouldBeFound("usuarioId.doesNotContain=" + UPDATED_USUARIO_ID);
    }

    @Test
    @Transactional
    void getAllStepCountCadencesByEmpresaIdIsEqualToSomething() throws Exception {
        // Initialize the database
        stepCountCadenceRepository.saveAndFlush(stepCountCadence);

        // Get all the stepCountCadenceList where empresaId equals to DEFAULT_EMPRESA_ID
        defaultStepCountCadenceShouldBeFound("empresaId.equals=" + DEFAULT_EMPRESA_ID);

        // Get all the stepCountCadenceList where empresaId equals to UPDATED_EMPRESA_ID
        defaultStepCountCadenceShouldNotBeFound("empresaId.equals=" + UPDATED_EMPRESA_ID);
    }

    @Test
    @Transactional
    void getAllStepCountCadencesByEmpresaIdIsInShouldWork() throws Exception {
        // Initialize the database
        stepCountCadenceRepository.saveAndFlush(stepCountCadence);

        // Get all the stepCountCadenceList where empresaId in DEFAULT_EMPRESA_ID or UPDATED_EMPRESA_ID
        defaultStepCountCadenceShouldBeFound("empresaId.in=" + DEFAULT_EMPRESA_ID + "," + UPDATED_EMPRESA_ID);

        // Get all the stepCountCadenceList where empresaId equals to UPDATED_EMPRESA_ID
        defaultStepCountCadenceShouldNotBeFound("empresaId.in=" + UPDATED_EMPRESA_ID);
    }

    @Test
    @Transactional
    void getAllStepCountCadencesByEmpresaIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        stepCountCadenceRepository.saveAndFlush(stepCountCadence);

        // Get all the stepCountCadenceList where empresaId is not null
        defaultStepCountCadenceShouldBeFound("empresaId.specified=true");

        // Get all the stepCountCadenceList where empresaId is null
        defaultStepCountCadenceShouldNotBeFound("empresaId.specified=false");
    }

    @Test
    @Transactional
    void getAllStepCountCadencesByEmpresaIdContainsSomething() throws Exception {
        // Initialize the database
        stepCountCadenceRepository.saveAndFlush(stepCountCadence);

        // Get all the stepCountCadenceList where empresaId contains DEFAULT_EMPRESA_ID
        defaultStepCountCadenceShouldBeFound("empresaId.contains=" + DEFAULT_EMPRESA_ID);

        // Get all the stepCountCadenceList where empresaId contains UPDATED_EMPRESA_ID
        defaultStepCountCadenceShouldNotBeFound("empresaId.contains=" + UPDATED_EMPRESA_ID);
    }

    @Test
    @Transactional
    void getAllStepCountCadencesByEmpresaIdNotContainsSomething() throws Exception {
        // Initialize the database
        stepCountCadenceRepository.saveAndFlush(stepCountCadence);

        // Get all the stepCountCadenceList where empresaId does not contain DEFAULT_EMPRESA_ID
        defaultStepCountCadenceShouldNotBeFound("empresaId.doesNotContain=" + DEFAULT_EMPRESA_ID);

        // Get all the stepCountCadenceList where empresaId does not contain UPDATED_EMPRESA_ID
        defaultStepCountCadenceShouldBeFound("empresaId.doesNotContain=" + UPDATED_EMPRESA_ID);
    }

    @Test
    @Transactional
    void getAllStepCountCadencesByRpmIsEqualToSomething() throws Exception {
        // Initialize the database
        stepCountCadenceRepository.saveAndFlush(stepCountCadence);

        // Get all the stepCountCadenceList where rpm equals to DEFAULT_RPM
        defaultStepCountCadenceShouldBeFound("rpm.equals=" + DEFAULT_RPM);

        // Get all the stepCountCadenceList where rpm equals to UPDATED_RPM
        defaultStepCountCadenceShouldNotBeFound("rpm.equals=" + UPDATED_RPM);
    }

    @Test
    @Transactional
    void getAllStepCountCadencesByRpmIsInShouldWork() throws Exception {
        // Initialize the database
        stepCountCadenceRepository.saveAndFlush(stepCountCadence);

        // Get all the stepCountCadenceList where rpm in DEFAULT_RPM or UPDATED_RPM
        defaultStepCountCadenceShouldBeFound("rpm.in=" + DEFAULT_RPM + "," + UPDATED_RPM);

        // Get all the stepCountCadenceList where rpm equals to UPDATED_RPM
        defaultStepCountCadenceShouldNotBeFound("rpm.in=" + UPDATED_RPM);
    }

    @Test
    @Transactional
    void getAllStepCountCadencesByRpmIsNullOrNotNull() throws Exception {
        // Initialize the database
        stepCountCadenceRepository.saveAndFlush(stepCountCadence);

        // Get all the stepCountCadenceList where rpm is not null
        defaultStepCountCadenceShouldBeFound("rpm.specified=true");

        // Get all the stepCountCadenceList where rpm is null
        defaultStepCountCadenceShouldNotBeFound("rpm.specified=false");
    }

    @Test
    @Transactional
    void getAllStepCountCadencesByRpmIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        stepCountCadenceRepository.saveAndFlush(stepCountCadence);

        // Get all the stepCountCadenceList where rpm is greater than or equal to DEFAULT_RPM
        defaultStepCountCadenceShouldBeFound("rpm.greaterThanOrEqual=" + DEFAULT_RPM);

        // Get all the stepCountCadenceList where rpm is greater than or equal to UPDATED_RPM
        defaultStepCountCadenceShouldNotBeFound("rpm.greaterThanOrEqual=" + UPDATED_RPM);
    }

    @Test
    @Transactional
    void getAllStepCountCadencesByRpmIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        stepCountCadenceRepository.saveAndFlush(stepCountCadence);

        // Get all the stepCountCadenceList where rpm is less than or equal to DEFAULT_RPM
        defaultStepCountCadenceShouldBeFound("rpm.lessThanOrEqual=" + DEFAULT_RPM);

        // Get all the stepCountCadenceList where rpm is less than or equal to SMALLER_RPM
        defaultStepCountCadenceShouldNotBeFound("rpm.lessThanOrEqual=" + SMALLER_RPM);
    }

    @Test
    @Transactional
    void getAllStepCountCadencesByRpmIsLessThanSomething() throws Exception {
        // Initialize the database
        stepCountCadenceRepository.saveAndFlush(stepCountCadence);

        // Get all the stepCountCadenceList where rpm is less than DEFAULT_RPM
        defaultStepCountCadenceShouldNotBeFound("rpm.lessThan=" + DEFAULT_RPM);

        // Get all the stepCountCadenceList where rpm is less than UPDATED_RPM
        defaultStepCountCadenceShouldBeFound("rpm.lessThan=" + UPDATED_RPM);
    }

    @Test
    @Transactional
    void getAllStepCountCadencesByRpmIsGreaterThanSomething() throws Exception {
        // Initialize the database
        stepCountCadenceRepository.saveAndFlush(stepCountCadence);

        // Get all the stepCountCadenceList where rpm is greater than DEFAULT_RPM
        defaultStepCountCadenceShouldNotBeFound("rpm.greaterThan=" + DEFAULT_RPM);

        // Get all the stepCountCadenceList where rpm is greater than SMALLER_RPM
        defaultStepCountCadenceShouldBeFound("rpm.greaterThan=" + SMALLER_RPM);
    }

    @Test
    @Transactional
    void getAllStepCountCadencesByStartTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        stepCountCadenceRepository.saveAndFlush(stepCountCadence);

        // Get all the stepCountCadenceList where startTime equals to DEFAULT_START_TIME
        defaultStepCountCadenceShouldBeFound("startTime.equals=" + DEFAULT_START_TIME);

        // Get all the stepCountCadenceList where startTime equals to UPDATED_START_TIME
        defaultStepCountCadenceShouldNotBeFound("startTime.equals=" + UPDATED_START_TIME);
    }

    @Test
    @Transactional
    void getAllStepCountCadencesByStartTimeIsInShouldWork() throws Exception {
        // Initialize the database
        stepCountCadenceRepository.saveAndFlush(stepCountCadence);

        // Get all the stepCountCadenceList where startTime in DEFAULT_START_TIME or UPDATED_START_TIME
        defaultStepCountCadenceShouldBeFound("startTime.in=" + DEFAULT_START_TIME + "," + UPDATED_START_TIME);

        // Get all the stepCountCadenceList where startTime equals to UPDATED_START_TIME
        defaultStepCountCadenceShouldNotBeFound("startTime.in=" + UPDATED_START_TIME);
    }

    @Test
    @Transactional
    void getAllStepCountCadencesByStartTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        stepCountCadenceRepository.saveAndFlush(stepCountCadence);

        // Get all the stepCountCadenceList where startTime is not null
        defaultStepCountCadenceShouldBeFound("startTime.specified=true");

        // Get all the stepCountCadenceList where startTime is null
        defaultStepCountCadenceShouldNotBeFound("startTime.specified=false");
    }

    @Test
    @Transactional
    void getAllStepCountCadencesByEndTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        stepCountCadenceRepository.saveAndFlush(stepCountCadence);

        // Get all the stepCountCadenceList where endTime equals to DEFAULT_END_TIME
        defaultStepCountCadenceShouldBeFound("endTime.equals=" + DEFAULT_END_TIME);

        // Get all the stepCountCadenceList where endTime equals to UPDATED_END_TIME
        defaultStepCountCadenceShouldNotBeFound("endTime.equals=" + UPDATED_END_TIME);
    }

    @Test
    @Transactional
    void getAllStepCountCadencesByEndTimeIsInShouldWork() throws Exception {
        // Initialize the database
        stepCountCadenceRepository.saveAndFlush(stepCountCadence);

        // Get all the stepCountCadenceList where endTime in DEFAULT_END_TIME or UPDATED_END_TIME
        defaultStepCountCadenceShouldBeFound("endTime.in=" + DEFAULT_END_TIME + "," + UPDATED_END_TIME);

        // Get all the stepCountCadenceList where endTime equals to UPDATED_END_TIME
        defaultStepCountCadenceShouldNotBeFound("endTime.in=" + UPDATED_END_TIME);
    }

    @Test
    @Transactional
    void getAllStepCountCadencesByEndTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        stepCountCadenceRepository.saveAndFlush(stepCountCadence);

        // Get all the stepCountCadenceList where endTime is not null
        defaultStepCountCadenceShouldBeFound("endTime.specified=true");

        // Get all the stepCountCadenceList where endTime is null
        defaultStepCountCadenceShouldNotBeFound("endTime.specified=false");
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultStepCountCadenceShouldBeFound(String filter) throws Exception {
        restStepCountCadenceMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(stepCountCadence.getId().toString())))
            .andExpect(jsonPath("$.[*].usuarioId").value(hasItem(DEFAULT_USUARIO_ID)))
            .andExpect(jsonPath("$.[*].empresaId").value(hasItem(DEFAULT_EMPRESA_ID)))
            .andExpect(jsonPath("$.[*].rpm").value(hasItem(DEFAULT_RPM)))
            .andExpect(jsonPath("$.[*].startTime").value(hasItem(DEFAULT_START_TIME.toString())))
            .andExpect(jsonPath("$.[*].endTime").value(hasItem(DEFAULT_END_TIME.toString())));

        // Check, that the count call also returns 1
        restStepCountCadenceMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultStepCountCadenceShouldNotBeFound(String filter) throws Exception {
        restStepCountCadenceMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restStepCountCadenceMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingStepCountCadence() throws Exception {
        // Get the stepCountCadence
        restStepCountCadenceMockMvc.perform(get(ENTITY_API_URL_ID, UUID.randomUUID().toString())).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingStepCountCadence() throws Exception {
        // Initialize the database
        stepCountCadenceRepository.saveAndFlush(stepCountCadence);

        int databaseSizeBeforeUpdate = stepCountCadenceRepository.findAll().size();

        // Update the stepCountCadence
        StepCountCadence updatedStepCountCadence = stepCountCadenceRepository.findById(stepCountCadence.getId()).get();
        // Disconnect from session so that the updates on updatedStepCountCadence are not directly saved in db
        em.detach(updatedStepCountCadence);
        updatedStepCountCadence
            .usuarioId(UPDATED_USUARIO_ID)
            .empresaId(UPDATED_EMPRESA_ID)
            .rpm(UPDATED_RPM)
            .startTime(UPDATED_START_TIME)
            .endTime(UPDATED_END_TIME);

        restStepCountCadenceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedStepCountCadence.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedStepCountCadence))
            )
            .andExpect(status().isOk());

        // Validate the StepCountCadence in the database
        List<StepCountCadence> stepCountCadenceList = stepCountCadenceRepository.findAll();
        assertThat(stepCountCadenceList).hasSize(databaseSizeBeforeUpdate);
        StepCountCadence testStepCountCadence = stepCountCadenceList.get(stepCountCadenceList.size() - 1);
        assertThat(testStepCountCadence.getUsuarioId()).isEqualTo(UPDATED_USUARIO_ID);
        assertThat(testStepCountCadence.getEmpresaId()).isEqualTo(UPDATED_EMPRESA_ID);
        assertThat(testStepCountCadence.getRpm()).isEqualTo(UPDATED_RPM);
        assertThat(testStepCountCadence.getStartTime()).isEqualTo(UPDATED_START_TIME);
        assertThat(testStepCountCadence.getEndTime()).isEqualTo(UPDATED_END_TIME);
    }

    @Test
    @Transactional
    void putNonExistingStepCountCadence() throws Exception {
        int databaseSizeBeforeUpdate = stepCountCadenceRepository.findAll().size();
        stepCountCadence.setId(UUID.randomUUID());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStepCountCadenceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, stepCountCadence.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(stepCountCadence))
            )
            .andExpect(status().isBadRequest());

        // Validate the StepCountCadence in the database
        List<StepCountCadence> stepCountCadenceList = stepCountCadenceRepository.findAll();
        assertThat(stepCountCadenceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchStepCountCadence() throws Exception {
        int databaseSizeBeforeUpdate = stepCountCadenceRepository.findAll().size();
        stepCountCadence.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStepCountCadenceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(stepCountCadence))
            )
            .andExpect(status().isBadRequest());

        // Validate the StepCountCadence in the database
        List<StepCountCadence> stepCountCadenceList = stepCountCadenceRepository.findAll();
        assertThat(stepCountCadenceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamStepCountCadence() throws Exception {
        int databaseSizeBeforeUpdate = stepCountCadenceRepository.findAll().size();
        stepCountCadence.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStepCountCadenceMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(stepCountCadence))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the StepCountCadence in the database
        List<StepCountCadence> stepCountCadenceList = stepCountCadenceRepository.findAll();
        assertThat(stepCountCadenceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateStepCountCadenceWithPatch() throws Exception {
        // Initialize the database
        stepCountCadenceRepository.saveAndFlush(stepCountCadence);

        int databaseSizeBeforeUpdate = stepCountCadenceRepository.findAll().size();

        // Update the stepCountCadence using partial update
        StepCountCadence partialUpdatedStepCountCadence = new StepCountCadence();
        partialUpdatedStepCountCadence.setId(stepCountCadence.getId());

        partialUpdatedStepCountCadence.rpm(UPDATED_RPM).startTime(UPDATED_START_TIME).endTime(UPDATED_END_TIME);

        restStepCountCadenceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedStepCountCadence.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedStepCountCadence))
            )
            .andExpect(status().isOk());

        // Validate the StepCountCadence in the database
        List<StepCountCadence> stepCountCadenceList = stepCountCadenceRepository.findAll();
        assertThat(stepCountCadenceList).hasSize(databaseSizeBeforeUpdate);
        StepCountCadence testStepCountCadence = stepCountCadenceList.get(stepCountCadenceList.size() - 1);
        assertThat(testStepCountCadence.getUsuarioId()).isEqualTo(DEFAULT_USUARIO_ID);
        assertThat(testStepCountCadence.getEmpresaId()).isEqualTo(DEFAULT_EMPRESA_ID);
        assertThat(testStepCountCadence.getRpm()).isEqualTo(UPDATED_RPM);
        assertThat(testStepCountCadence.getStartTime()).isEqualTo(UPDATED_START_TIME);
        assertThat(testStepCountCadence.getEndTime()).isEqualTo(UPDATED_END_TIME);
    }

    @Test
    @Transactional
    void fullUpdateStepCountCadenceWithPatch() throws Exception {
        // Initialize the database
        stepCountCadenceRepository.saveAndFlush(stepCountCadence);

        int databaseSizeBeforeUpdate = stepCountCadenceRepository.findAll().size();

        // Update the stepCountCadence using partial update
        StepCountCadence partialUpdatedStepCountCadence = new StepCountCadence();
        partialUpdatedStepCountCadence.setId(stepCountCadence.getId());

        partialUpdatedStepCountCadence
            .usuarioId(UPDATED_USUARIO_ID)
            .empresaId(UPDATED_EMPRESA_ID)
            .rpm(UPDATED_RPM)
            .startTime(UPDATED_START_TIME)
            .endTime(UPDATED_END_TIME);

        restStepCountCadenceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedStepCountCadence.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedStepCountCadence))
            )
            .andExpect(status().isOk());

        // Validate the StepCountCadence in the database
        List<StepCountCadence> stepCountCadenceList = stepCountCadenceRepository.findAll();
        assertThat(stepCountCadenceList).hasSize(databaseSizeBeforeUpdate);
        StepCountCadence testStepCountCadence = stepCountCadenceList.get(stepCountCadenceList.size() - 1);
        assertThat(testStepCountCadence.getUsuarioId()).isEqualTo(UPDATED_USUARIO_ID);
        assertThat(testStepCountCadence.getEmpresaId()).isEqualTo(UPDATED_EMPRESA_ID);
        assertThat(testStepCountCadence.getRpm()).isEqualTo(UPDATED_RPM);
        assertThat(testStepCountCadence.getStartTime()).isEqualTo(UPDATED_START_TIME);
        assertThat(testStepCountCadence.getEndTime()).isEqualTo(UPDATED_END_TIME);
    }

    @Test
    @Transactional
    void patchNonExistingStepCountCadence() throws Exception {
        int databaseSizeBeforeUpdate = stepCountCadenceRepository.findAll().size();
        stepCountCadence.setId(UUID.randomUUID());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStepCountCadenceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, stepCountCadence.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(stepCountCadence))
            )
            .andExpect(status().isBadRequest());

        // Validate the StepCountCadence in the database
        List<StepCountCadence> stepCountCadenceList = stepCountCadenceRepository.findAll();
        assertThat(stepCountCadenceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchStepCountCadence() throws Exception {
        int databaseSizeBeforeUpdate = stepCountCadenceRepository.findAll().size();
        stepCountCadence.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStepCountCadenceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(stepCountCadence))
            )
            .andExpect(status().isBadRequest());

        // Validate the StepCountCadence in the database
        List<StepCountCadence> stepCountCadenceList = stepCountCadenceRepository.findAll();
        assertThat(stepCountCadenceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamStepCountCadence() throws Exception {
        int databaseSizeBeforeUpdate = stepCountCadenceRepository.findAll().size();
        stepCountCadence.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStepCountCadenceMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(stepCountCadence))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the StepCountCadence in the database
        List<StepCountCadence> stepCountCadenceList = stepCountCadenceRepository.findAll();
        assertThat(stepCountCadenceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteStepCountCadence() throws Exception {
        // Initialize the database
        stepCountCadenceRepository.saveAndFlush(stepCountCadence);

        int databaseSizeBeforeDelete = stepCountCadenceRepository.findAll().size();

        // Delete the stepCountCadence
        restStepCountCadenceMockMvc
            .perform(delete(ENTITY_API_URL_ID, stepCountCadence.getId().toString()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<StepCountCadence> stepCountCadenceList = stepCountCadenceRepository.findAll();
        assertThat(stepCountCadenceList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
