package com.be4tech.b4carecollect.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.be4tech.b4carecollect.IntegrationTest;
import com.be4tech.b4carecollect.domain.HeartMinutes;
import com.be4tech.b4carecollect.repository.HeartMinutesRepository;
import com.be4tech.b4carecollect.service.criteria.HeartMinutesCriteria;
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
 * Integration tests for the {@link HeartMinutesResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class HeartMinutesResourceIT {

    private static final String DEFAULT_USUARIO_ID = "AAAAAAAAAA";
    private static final String UPDATED_USUARIO_ID = "BBBBBBBBBB";

    private static final String DEFAULT_EMPRESA_ID = "AAAAAAAAAA";
    private static final String UPDATED_EMPRESA_ID = "BBBBBBBBBB";

    private static final Float DEFAULT_SEVERITY = 1F;
    private static final Float UPDATED_SEVERITY = 2F;
    private static final Float SMALLER_SEVERITY = 1F - 1F;

    private static final Instant DEFAULT_START_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_START_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_END_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_END_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/heart-minutes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private HeartMinutesRepository heartMinutesRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restHeartMinutesMockMvc;

    private HeartMinutes heartMinutes;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static HeartMinutes createEntity(EntityManager em) {
        HeartMinutes heartMinutes = new HeartMinutes()
            .usuarioId(DEFAULT_USUARIO_ID)
            .empresaId(DEFAULT_EMPRESA_ID)
            .severity(DEFAULT_SEVERITY)
            .startTime(DEFAULT_START_TIME)
            .endTime(DEFAULT_END_TIME);
        return heartMinutes;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static HeartMinutes createUpdatedEntity(EntityManager em) {
        HeartMinutes heartMinutes = new HeartMinutes()
            .usuarioId(UPDATED_USUARIO_ID)
            .empresaId(UPDATED_EMPRESA_ID)
            .severity(UPDATED_SEVERITY)
            .startTime(UPDATED_START_TIME)
            .endTime(UPDATED_END_TIME);
        return heartMinutes;
    }

    @BeforeEach
    public void initTest() {
        heartMinutes = createEntity(em);
    }

    @Test
    @Transactional
    void createHeartMinutes() throws Exception {
        int databaseSizeBeforeCreate = heartMinutesRepository.findAll().size();
        // Create the HeartMinutes
        restHeartMinutesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(heartMinutes)))
            .andExpect(status().isCreated());

        // Validate the HeartMinutes in the database
        List<HeartMinutes> heartMinutesList = heartMinutesRepository.findAll();
        assertThat(heartMinutesList).hasSize(databaseSizeBeforeCreate + 1);
        HeartMinutes testHeartMinutes = heartMinutesList.get(heartMinutesList.size() - 1);
        assertThat(testHeartMinutes.getUsuarioId()).isEqualTo(DEFAULT_USUARIO_ID);
        assertThat(testHeartMinutes.getEmpresaId()).isEqualTo(DEFAULT_EMPRESA_ID);
        assertThat(testHeartMinutes.getSeverity()).isEqualTo(DEFAULT_SEVERITY);
        assertThat(testHeartMinutes.getStartTime()).isEqualTo(DEFAULT_START_TIME);
        assertThat(testHeartMinutes.getEndTime()).isEqualTo(DEFAULT_END_TIME);
    }

    @Test
    @Transactional
    void createHeartMinutesWithExistingId() throws Exception {
        // Create the HeartMinutes with an existing ID
        heartMinutesRepository.saveAndFlush(heartMinutes);

        int databaseSizeBeforeCreate = heartMinutesRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restHeartMinutesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(heartMinutes)))
            .andExpect(status().isBadRequest());

        // Validate the HeartMinutes in the database
        List<HeartMinutes> heartMinutesList = heartMinutesRepository.findAll();
        assertThat(heartMinutesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllHeartMinutes() throws Exception {
        // Initialize the database
        heartMinutesRepository.saveAndFlush(heartMinutes);

        // Get all the heartMinutesList
        restHeartMinutesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(heartMinutes.getId().toString())))
            .andExpect(jsonPath("$.[*].usuarioId").value(hasItem(DEFAULT_USUARIO_ID)))
            .andExpect(jsonPath("$.[*].empresaId").value(hasItem(DEFAULT_EMPRESA_ID)))
            .andExpect(jsonPath("$.[*].severity").value(hasItem(DEFAULT_SEVERITY.doubleValue())))
            .andExpect(jsonPath("$.[*].startTime").value(hasItem(DEFAULT_START_TIME.toString())))
            .andExpect(jsonPath("$.[*].endTime").value(hasItem(DEFAULT_END_TIME.toString())));
    }

    @Test
    @Transactional
    void getHeartMinutes() throws Exception {
        // Initialize the database
        heartMinutesRepository.saveAndFlush(heartMinutes);

        // Get the heartMinutes
        restHeartMinutesMockMvc
            .perform(get(ENTITY_API_URL_ID, heartMinutes.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(heartMinutes.getId().toString()))
            .andExpect(jsonPath("$.usuarioId").value(DEFAULT_USUARIO_ID))
            .andExpect(jsonPath("$.empresaId").value(DEFAULT_EMPRESA_ID))
            .andExpect(jsonPath("$.severity").value(DEFAULT_SEVERITY.doubleValue()))
            .andExpect(jsonPath("$.startTime").value(DEFAULT_START_TIME.toString()))
            .andExpect(jsonPath("$.endTime").value(DEFAULT_END_TIME.toString()));
    }

    @Test
    @Transactional
    void getHeartMinutesByIdFiltering() throws Exception {
        // Initialize the database
        heartMinutesRepository.saveAndFlush(heartMinutes);

        UUID id = heartMinutes.getId();

        defaultHeartMinutesShouldBeFound("id.equals=" + id);
        defaultHeartMinutesShouldNotBeFound("id.notEquals=" + id);
    }

    @Test
    @Transactional
    void getAllHeartMinutesByUsuarioIdIsEqualToSomething() throws Exception {
        // Initialize the database
        heartMinutesRepository.saveAndFlush(heartMinutes);

        // Get all the heartMinutesList where usuarioId equals to DEFAULT_USUARIO_ID
        defaultHeartMinutesShouldBeFound("usuarioId.equals=" + DEFAULT_USUARIO_ID);

        // Get all the heartMinutesList where usuarioId equals to UPDATED_USUARIO_ID
        defaultHeartMinutesShouldNotBeFound("usuarioId.equals=" + UPDATED_USUARIO_ID);
    }

    @Test
    @Transactional
    void getAllHeartMinutesByUsuarioIdIsInShouldWork() throws Exception {
        // Initialize the database
        heartMinutesRepository.saveAndFlush(heartMinutes);

        // Get all the heartMinutesList where usuarioId in DEFAULT_USUARIO_ID or UPDATED_USUARIO_ID
        defaultHeartMinutesShouldBeFound("usuarioId.in=" + DEFAULT_USUARIO_ID + "," + UPDATED_USUARIO_ID);

        // Get all the heartMinutesList where usuarioId equals to UPDATED_USUARIO_ID
        defaultHeartMinutesShouldNotBeFound("usuarioId.in=" + UPDATED_USUARIO_ID);
    }

    @Test
    @Transactional
    void getAllHeartMinutesByUsuarioIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        heartMinutesRepository.saveAndFlush(heartMinutes);

        // Get all the heartMinutesList where usuarioId is not null
        defaultHeartMinutesShouldBeFound("usuarioId.specified=true");

        // Get all the heartMinutesList where usuarioId is null
        defaultHeartMinutesShouldNotBeFound("usuarioId.specified=false");
    }

    @Test
    @Transactional
    void getAllHeartMinutesByUsuarioIdContainsSomething() throws Exception {
        // Initialize the database
        heartMinutesRepository.saveAndFlush(heartMinutes);

        // Get all the heartMinutesList where usuarioId contains DEFAULT_USUARIO_ID
        defaultHeartMinutesShouldBeFound("usuarioId.contains=" + DEFAULT_USUARIO_ID);

        // Get all the heartMinutesList where usuarioId contains UPDATED_USUARIO_ID
        defaultHeartMinutesShouldNotBeFound("usuarioId.contains=" + UPDATED_USUARIO_ID);
    }

    @Test
    @Transactional
    void getAllHeartMinutesByUsuarioIdNotContainsSomething() throws Exception {
        // Initialize the database
        heartMinutesRepository.saveAndFlush(heartMinutes);

        // Get all the heartMinutesList where usuarioId does not contain DEFAULT_USUARIO_ID
        defaultHeartMinutesShouldNotBeFound("usuarioId.doesNotContain=" + DEFAULT_USUARIO_ID);

        // Get all the heartMinutesList where usuarioId does not contain UPDATED_USUARIO_ID
        defaultHeartMinutesShouldBeFound("usuarioId.doesNotContain=" + UPDATED_USUARIO_ID);
    }

    @Test
    @Transactional
    void getAllHeartMinutesByEmpresaIdIsEqualToSomething() throws Exception {
        // Initialize the database
        heartMinutesRepository.saveAndFlush(heartMinutes);

        // Get all the heartMinutesList where empresaId equals to DEFAULT_EMPRESA_ID
        defaultHeartMinutesShouldBeFound("empresaId.equals=" + DEFAULT_EMPRESA_ID);

        // Get all the heartMinutesList where empresaId equals to UPDATED_EMPRESA_ID
        defaultHeartMinutesShouldNotBeFound("empresaId.equals=" + UPDATED_EMPRESA_ID);
    }

    @Test
    @Transactional
    void getAllHeartMinutesByEmpresaIdIsInShouldWork() throws Exception {
        // Initialize the database
        heartMinutesRepository.saveAndFlush(heartMinutes);

        // Get all the heartMinutesList where empresaId in DEFAULT_EMPRESA_ID or UPDATED_EMPRESA_ID
        defaultHeartMinutesShouldBeFound("empresaId.in=" + DEFAULT_EMPRESA_ID + "," + UPDATED_EMPRESA_ID);

        // Get all the heartMinutesList where empresaId equals to UPDATED_EMPRESA_ID
        defaultHeartMinutesShouldNotBeFound("empresaId.in=" + UPDATED_EMPRESA_ID);
    }

    @Test
    @Transactional
    void getAllHeartMinutesByEmpresaIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        heartMinutesRepository.saveAndFlush(heartMinutes);

        // Get all the heartMinutesList where empresaId is not null
        defaultHeartMinutesShouldBeFound("empresaId.specified=true");

        // Get all the heartMinutesList where empresaId is null
        defaultHeartMinutesShouldNotBeFound("empresaId.specified=false");
    }

    @Test
    @Transactional
    void getAllHeartMinutesByEmpresaIdContainsSomething() throws Exception {
        // Initialize the database
        heartMinutesRepository.saveAndFlush(heartMinutes);

        // Get all the heartMinutesList where empresaId contains DEFAULT_EMPRESA_ID
        defaultHeartMinutesShouldBeFound("empresaId.contains=" + DEFAULT_EMPRESA_ID);

        // Get all the heartMinutesList where empresaId contains UPDATED_EMPRESA_ID
        defaultHeartMinutesShouldNotBeFound("empresaId.contains=" + UPDATED_EMPRESA_ID);
    }

    @Test
    @Transactional
    void getAllHeartMinutesByEmpresaIdNotContainsSomething() throws Exception {
        // Initialize the database
        heartMinutesRepository.saveAndFlush(heartMinutes);

        // Get all the heartMinutesList where empresaId does not contain DEFAULT_EMPRESA_ID
        defaultHeartMinutesShouldNotBeFound("empresaId.doesNotContain=" + DEFAULT_EMPRESA_ID);

        // Get all the heartMinutesList where empresaId does not contain UPDATED_EMPRESA_ID
        defaultHeartMinutesShouldBeFound("empresaId.doesNotContain=" + UPDATED_EMPRESA_ID);
    }

    @Test
    @Transactional
    void getAllHeartMinutesBySeverityIsEqualToSomething() throws Exception {
        // Initialize the database
        heartMinutesRepository.saveAndFlush(heartMinutes);

        // Get all the heartMinutesList where severity equals to DEFAULT_SEVERITY
        defaultHeartMinutesShouldBeFound("severity.equals=" + DEFAULT_SEVERITY);

        // Get all the heartMinutesList where severity equals to UPDATED_SEVERITY
        defaultHeartMinutesShouldNotBeFound("severity.equals=" + UPDATED_SEVERITY);
    }

    @Test
    @Transactional
    void getAllHeartMinutesBySeverityIsInShouldWork() throws Exception {
        // Initialize the database
        heartMinutesRepository.saveAndFlush(heartMinutes);

        // Get all the heartMinutesList where severity in DEFAULT_SEVERITY or UPDATED_SEVERITY
        defaultHeartMinutesShouldBeFound("severity.in=" + DEFAULT_SEVERITY + "," + UPDATED_SEVERITY);

        // Get all the heartMinutesList where severity equals to UPDATED_SEVERITY
        defaultHeartMinutesShouldNotBeFound("severity.in=" + UPDATED_SEVERITY);
    }

    @Test
    @Transactional
    void getAllHeartMinutesBySeverityIsNullOrNotNull() throws Exception {
        // Initialize the database
        heartMinutesRepository.saveAndFlush(heartMinutes);

        // Get all the heartMinutesList where severity is not null
        defaultHeartMinutesShouldBeFound("severity.specified=true");

        // Get all the heartMinutesList where severity is null
        defaultHeartMinutesShouldNotBeFound("severity.specified=false");
    }

    @Test
    @Transactional
    void getAllHeartMinutesBySeverityIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        heartMinutesRepository.saveAndFlush(heartMinutes);

        // Get all the heartMinutesList where severity is greater than or equal to DEFAULT_SEVERITY
        defaultHeartMinutesShouldBeFound("severity.greaterThanOrEqual=" + DEFAULT_SEVERITY);

        // Get all the heartMinutesList where severity is greater than or equal to UPDATED_SEVERITY
        defaultHeartMinutesShouldNotBeFound("severity.greaterThanOrEqual=" + UPDATED_SEVERITY);
    }

    @Test
    @Transactional
    void getAllHeartMinutesBySeverityIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        heartMinutesRepository.saveAndFlush(heartMinutes);

        // Get all the heartMinutesList where severity is less than or equal to DEFAULT_SEVERITY
        defaultHeartMinutesShouldBeFound("severity.lessThanOrEqual=" + DEFAULT_SEVERITY);

        // Get all the heartMinutesList where severity is less than or equal to SMALLER_SEVERITY
        defaultHeartMinutesShouldNotBeFound("severity.lessThanOrEqual=" + SMALLER_SEVERITY);
    }

    @Test
    @Transactional
    void getAllHeartMinutesBySeverityIsLessThanSomething() throws Exception {
        // Initialize the database
        heartMinutesRepository.saveAndFlush(heartMinutes);

        // Get all the heartMinutesList where severity is less than DEFAULT_SEVERITY
        defaultHeartMinutesShouldNotBeFound("severity.lessThan=" + DEFAULT_SEVERITY);

        // Get all the heartMinutesList where severity is less than UPDATED_SEVERITY
        defaultHeartMinutesShouldBeFound("severity.lessThan=" + UPDATED_SEVERITY);
    }

    @Test
    @Transactional
    void getAllHeartMinutesBySeverityIsGreaterThanSomething() throws Exception {
        // Initialize the database
        heartMinutesRepository.saveAndFlush(heartMinutes);

        // Get all the heartMinutesList where severity is greater than DEFAULT_SEVERITY
        defaultHeartMinutesShouldNotBeFound("severity.greaterThan=" + DEFAULT_SEVERITY);

        // Get all the heartMinutesList where severity is greater than SMALLER_SEVERITY
        defaultHeartMinutesShouldBeFound("severity.greaterThan=" + SMALLER_SEVERITY);
    }

    @Test
    @Transactional
    void getAllHeartMinutesByStartTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        heartMinutesRepository.saveAndFlush(heartMinutes);

        // Get all the heartMinutesList where startTime equals to DEFAULT_START_TIME
        defaultHeartMinutesShouldBeFound("startTime.equals=" + DEFAULT_START_TIME);

        // Get all the heartMinutesList where startTime equals to UPDATED_START_TIME
        defaultHeartMinutesShouldNotBeFound("startTime.equals=" + UPDATED_START_TIME);
    }

    @Test
    @Transactional
    void getAllHeartMinutesByStartTimeIsInShouldWork() throws Exception {
        // Initialize the database
        heartMinutesRepository.saveAndFlush(heartMinutes);

        // Get all the heartMinutesList where startTime in DEFAULT_START_TIME or UPDATED_START_TIME
        defaultHeartMinutesShouldBeFound("startTime.in=" + DEFAULT_START_TIME + "," + UPDATED_START_TIME);

        // Get all the heartMinutesList where startTime equals to UPDATED_START_TIME
        defaultHeartMinutesShouldNotBeFound("startTime.in=" + UPDATED_START_TIME);
    }

    @Test
    @Transactional
    void getAllHeartMinutesByStartTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        heartMinutesRepository.saveAndFlush(heartMinutes);

        // Get all the heartMinutesList where startTime is not null
        defaultHeartMinutesShouldBeFound("startTime.specified=true");

        // Get all the heartMinutesList where startTime is null
        defaultHeartMinutesShouldNotBeFound("startTime.specified=false");
    }

    @Test
    @Transactional
    void getAllHeartMinutesByEndTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        heartMinutesRepository.saveAndFlush(heartMinutes);

        // Get all the heartMinutesList where endTime equals to DEFAULT_END_TIME
        defaultHeartMinutesShouldBeFound("endTime.equals=" + DEFAULT_END_TIME);

        // Get all the heartMinutesList where endTime equals to UPDATED_END_TIME
        defaultHeartMinutesShouldNotBeFound("endTime.equals=" + UPDATED_END_TIME);
    }

    @Test
    @Transactional
    void getAllHeartMinutesByEndTimeIsInShouldWork() throws Exception {
        // Initialize the database
        heartMinutesRepository.saveAndFlush(heartMinutes);

        // Get all the heartMinutesList where endTime in DEFAULT_END_TIME or UPDATED_END_TIME
        defaultHeartMinutesShouldBeFound("endTime.in=" + DEFAULT_END_TIME + "," + UPDATED_END_TIME);

        // Get all the heartMinutesList where endTime equals to UPDATED_END_TIME
        defaultHeartMinutesShouldNotBeFound("endTime.in=" + UPDATED_END_TIME);
    }

    @Test
    @Transactional
    void getAllHeartMinutesByEndTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        heartMinutesRepository.saveAndFlush(heartMinutes);

        // Get all the heartMinutesList where endTime is not null
        defaultHeartMinutesShouldBeFound("endTime.specified=true");

        // Get all the heartMinutesList where endTime is null
        defaultHeartMinutesShouldNotBeFound("endTime.specified=false");
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultHeartMinutesShouldBeFound(String filter) throws Exception {
        restHeartMinutesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(heartMinutes.getId().toString())))
            .andExpect(jsonPath("$.[*].usuarioId").value(hasItem(DEFAULT_USUARIO_ID)))
            .andExpect(jsonPath("$.[*].empresaId").value(hasItem(DEFAULT_EMPRESA_ID)))
            .andExpect(jsonPath("$.[*].severity").value(hasItem(DEFAULT_SEVERITY.doubleValue())))
            .andExpect(jsonPath("$.[*].startTime").value(hasItem(DEFAULT_START_TIME.toString())))
            .andExpect(jsonPath("$.[*].endTime").value(hasItem(DEFAULT_END_TIME.toString())));

        // Check, that the count call also returns 1
        restHeartMinutesMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultHeartMinutesShouldNotBeFound(String filter) throws Exception {
        restHeartMinutesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restHeartMinutesMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingHeartMinutes() throws Exception {
        // Get the heartMinutes
        restHeartMinutesMockMvc.perform(get(ENTITY_API_URL_ID, UUID.randomUUID().toString())).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingHeartMinutes() throws Exception {
        // Initialize the database
        heartMinutesRepository.saveAndFlush(heartMinutes);

        int databaseSizeBeforeUpdate = heartMinutesRepository.findAll().size();

        // Update the heartMinutes
        HeartMinutes updatedHeartMinutes = heartMinutesRepository.findById(heartMinutes.getId()).get();
        // Disconnect from session so that the updates on updatedHeartMinutes are not directly saved in db
        em.detach(updatedHeartMinutes);
        updatedHeartMinutes
            .usuarioId(UPDATED_USUARIO_ID)
            .empresaId(UPDATED_EMPRESA_ID)
            .severity(UPDATED_SEVERITY)
            .startTime(UPDATED_START_TIME)
            .endTime(UPDATED_END_TIME);

        restHeartMinutesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedHeartMinutes.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedHeartMinutes))
            )
            .andExpect(status().isOk());

        // Validate the HeartMinutes in the database
        List<HeartMinutes> heartMinutesList = heartMinutesRepository.findAll();
        assertThat(heartMinutesList).hasSize(databaseSizeBeforeUpdate);
        HeartMinutes testHeartMinutes = heartMinutesList.get(heartMinutesList.size() - 1);
        assertThat(testHeartMinutes.getUsuarioId()).isEqualTo(UPDATED_USUARIO_ID);
        assertThat(testHeartMinutes.getEmpresaId()).isEqualTo(UPDATED_EMPRESA_ID);
        assertThat(testHeartMinutes.getSeverity()).isEqualTo(UPDATED_SEVERITY);
        assertThat(testHeartMinutes.getStartTime()).isEqualTo(UPDATED_START_TIME);
        assertThat(testHeartMinutes.getEndTime()).isEqualTo(UPDATED_END_TIME);
    }

    @Test
    @Transactional
    void putNonExistingHeartMinutes() throws Exception {
        int databaseSizeBeforeUpdate = heartMinutesRepository.findAll().size();
        heartMinutes.setId(UUID.randomUUID());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHeartMinutesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, heartMinutes.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(heartMinutes))
            )
            .andExpect(status().isBadRequest());

        // Validate the HeartMinutes in the database
        List<HeartMinutes> heartMinutesList = heartMinutesRepository.findAll();
        assertThat(heartMinutesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchHeartMinutes() throws Exception {
        int databaseSizeBeforeUpdate = heartMinutesRepository.findAll().size();
        heartMinutes.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHeartMinutesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(heartMinutes))
            )
            .andExpect(status().isBadRequest());

        // Validate the HeartMinutes in the database
        List<HeartMinutes> heartMinutesList = heartMinutesRepository.findAll();
        assertThat(heartMinutesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamHeartMinutes() throws Exception {
        int databaseSizeBeforeUpdate = heartMinutesRepository.findAll().size();
        heartMinutes.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHeartMinutesMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(heartMinutes)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the HeartMinutes in the database
        List<HeartMinutes> heartMinutesList = heartMinutesRepository.findAll();
        assertThat(heartMinutesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateHeartMinutesWithPatch() throws Exception {
        // Initialize the database
        heartMinutesRepository.saveAndFlush(heartMinutes);

        int databaseSizeBeforeUpdate = heartMinutesRepository.findAll().size();

        // Update the heartMinutes using partial update
        HeartMinutes partialUpdatedHeartMinutes = new HeartMinutes();
        partialUpdatedHeartMinutes.setId(heartMinutes.getId());

        partialUpdatedHeartMinutes
            .usuarioId(UPDATED_USUARIO_ID)
            .empresaId(UPDATED_EMPRESA_ID)
            .severity(UPDATED_SEVERITY)
            .startTime(UPDATED_START_TIME)
            .endTime(UPDATED_END_TIME);

        restHeartMinutesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedHeartMinutes.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedHeartMinutes))
            )
            .andExpect(status().isOk());

        // Validate the HeartMinutes in the database
        List<HeartMinutes> heartMinutesList = heartMinutesRepository.findAll();
        assertThat(heartMinutesList).hasSize(databaseSizeBeforeUpdate);
        HeartMinutes testHeartMinutes = heartMinutesList.get(heartMinutesList.size() - 1);
        assertThat(testHeartMinutes.getUsuarioId()).isEqualTo(UPDATED_USUARIO_ID);
        assertThat(testHeartMinutes.getEmpresaId()).isEqualTo(UPDATED_EMPRESA_ID);
        assertThat(testHeartMinutes.getSeverity()).isEqualTo(UPDATED_SEVERITY);
        assertThat(testHeartMinutes.getStartTime()).isEqualTo(UPDATED_START_TIME);
        assertThat(testHeartMinutes.getEndTime()).isEqualTo(UPDATED_END_TIME);
    }

    @Test
    @Transactional
    void fullUpdateHeartMinutesWithPatch() throws Exception {
        // Initialize the database
        heartMinutesRepository.saveAndFlush(heartMinutes);

        int databaseSizeBeforeUpdate = heartMinutesRepository.findAll().size();

        // Update the heartMinutes using partial update
        HeartMinutes partialUpdatedHeartMinutes = new HeartMinutes();
        partialUpdatedHeartMinutes.setId(heartMinutes.getId());

        partialUpdatedHeartMinutes
            .usuarioId(UPDATED_USUARIO_ID)
            .empresaId(UPDATED_EMPRESA_ID)
            .severity(UPDATED_SEVERITY)
            .startTime(UPDATED_START_TIME)
            .endTime(UPDATED_END_TIME);

        restHeartMinutesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedHeartMinutes.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedHeartMinutes))
            )
            .andExpect(status().isOk());

        // Validate the HeartMinutes in the database
        List<HeartMinutes> heartMinutesList = heartMinutesRepository.findAll();
        assertThat(heartMinutesList).hasSize(databaseSizeBeforeUpdate);
        HeartMinutes testHeartMinutes = heartMinutesList.get(heartMinutesList.size() - 1);
        assertThat(testHeartMinutes.getUsuarioId()).isEqualTo(UPDATED_USUARIO_ID);
        assertThat(testHeartMinutes.getEmpresaId()).isEqualTo(UPDATED_EMPRESA_ID);
        assertThat(testHeartMinutes.getSeverity()).isEqualTo(UPDATED_SEVERITY);
        assertThat(testHeartMinutes.getStartTime()).isEqualTo(UPDATED_START_TIME);
        assertThat(testHeartMinutes.getEndTime()).isEqualTo(UPDATED_END_TIME);
    }

    @Test
    @Transactional
    void patchNonExistingHeartMinutes() throws Exception {
        int databaseSizeBeforeUpdate = heartMinutesRepository.findAll().size();
        heartMinutes.setId(UUID.randomUUID());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHeartMinutesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, heartMinutes.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(heartMinutes))
            )
            .andExpect(status().isBadRequest());

        // Validate the HeartMinutes in the database
        List<HeartMinutes> heartMinutesList = heartMinutesRepository.findAll();
        assertThat(heartMinutesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchHeartMinutes() throws Exception {
        int databaseSizeBeforeUpdate = heartMinutesRepository.findAll().size();
        heartMinutes.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHeartMinutesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(heartMinutes))
            )
            .andExpect(status().isBadRequest());

        // Validate the HeartMinutes in the database
        List<HeartMinutes> heartMinutesList = heartMinutesRepository.findAll();
        assertThat(heartMinutesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamHeartMinutes() throws Exception {
        int databaseSizeBeforeUpdate = heartMinutesRepository.findAll().size();
        heartMinutes.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHeartMinutesMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(heartMinutes))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the HeartMinutes in the database
        List<HeartMinutes> heartMinutesList = heartMinutesRepository.findAll();
        assertThat(heartMinutesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteHeartMinutes() throws Exception {
        // Initialize the database
        heartMinutesRepository.saveAndFlush(heartMinutes);

        int databaseSizeBeforeDelete = heartMinutesRepository.findAll().size();

        // Delete the heartMinutes
        restHeartMinutesMockMvc
            .perform(delete(ENTITY_API_URL_ID, heartMinutes.getId().toString()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<HeartMinutes> heartMinutesList = heartMinutesRepository.findAll();
        assertThat(heartMinutesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
