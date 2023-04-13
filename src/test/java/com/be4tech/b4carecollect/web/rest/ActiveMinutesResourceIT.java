package com.be4tech.b4carecollect.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.be4tech.b4carecollect.IntegrationTest;
import com.be4tech.b4carecollect.domain.ActiveMinutes;
import com.be4tech.b4carecollect.repository.ActiveMinutesRepository;
import com.be4tech.b4carecollect.service.criteria.ActiveMinutesCriteria;
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
 * Integration tests for the {@link ActiveMinutesResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ActiveMinutesResourceIT {

    private static final String DEFAULT_USUARIO_ID = "AAAAAAAAAA";
    private static final String UPDATED_USUARIO_ID = "BBBBBBBBBB";

    private static final String DEFAULT_EMPRESA_ID = "AAAAAAAAAA";
    private static final String UPDATED_EMPRESA_ID = "BBBBBBBBBB";

    private static final Integer DEFAULT_CALORIAS = 1;
    private static final Integer UPDATED_CALORIAS = 2;
    private static final Integer SMALLER_CALORIAS = 1 - 1;

    private static final Instant DEFAULT_START_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_START_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_END_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_END_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/active-minutes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ActiveMinutesRepository activeMinutesRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restActiveMinutesMockMvc;

    private ActiveMinutes activeMinutes;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ActiveMinutes createEntity(EntityManager em) {
        ActiveMinutes activeMinutes = new ActiveMinutes()
            .usuarioId(DEFAULT_USUARIO_ID)
            .empresaId(DEFAULT_EMPRESA_ID)
            .calorias(DEFAULT_CALORIAS)
            .startTime(DEFAULT_START_TIME)
            .endTime(DEFAULT_END_TIME);
        return activeMinutes;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ActiveMinutes createUpdatedEntity(EntityManager em) {
        ActiveMinutes activeMinutes = new ActiveMinutes()
            .usuarioId(UPDATED_USUARIO_ID)
            .empresaId(UPDATED_EMPRESA_ID)
            .calorias(UPDATED_CALORIAS)
            .startTime(UPDATED_START_TIME)
            .endTime(UPDATED_END_TIME);
        return activeMinutes;
    }

    @BeforeEach
    public void initTest() {
        activeMinutes = createEntity(em);
    }

    @Test
    @Transactional
    void createActiveMinutes() throws Exception {
        int databaseSizeBeforeCreate = activeMinutesRepository.findAll().size();
        // Create the ActiveMinutes
        restActiveMinutesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(activeMinutes)))
            .andExpect(status().isCreated());

        // Validate the ActiveMinutes in the database
        List<ActiveMinutes> activeMinutesList = activeMinutesRepository.findAll();
        assertThat(activeMinutesList).hasSize(databaseSizeBeforeCreate + 1);
        ActiveMinutes testActiveMinutes = activeMinutesList.get(activeMinutesList.size() - 1);
        assertThat(testActiveMinutes.getUsuarioId()).isEqualTo(DEFAULT_USUARIO_ID);
        assertThat(testActiveMinutes.getEmpresaId()).isEqualTo(DEFAULT_EMPRESA_ID);
        assertThat(testActiveMinutes.getCalorias()).isEqualTo(DEFAULT_CALORIAS);
        assertThat(testActiveMinutes.getStartTime()).isEqualTo(DEFAULT_START_TIME);
        assertThat(testActiveMinutes.getEndTime()).isEqualTo(DEFAULT_END_TIME);
    }

    @Test
    @Transactional
    void createActiveMinutesWithExistingId() throws Exception {
        // Create the ActiveMinutes with an existing ID
        activeMinutesRepository.saveAndFlush(activeMinutes);

        int databaseSizeBeforeCreate = activeMinutesRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restActiveMinutesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(activeMinutes)))
            .andExpect(status().isBadRequest());

        // Validate the ActiveMinutes in the database
        List<ActiveMinutes> activeMinutesList = activeMinutesRepository.findAll();
        assertThat(activeMinutesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllActiveMinutes() throws Exception {
        // Initialize the database
        activeMinutesRepository.saveAndFlush(activeMinutes);

        // Get all the activeMinutesList
        restActiveMinutesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(activeMinutes.getId().toString())))
            .andExpect(jsonPath("$.[*].usuarioId").value(hasItem(DEFAULT_USUARIO_ID)))
            .andExpect(jsonPath("$.[*].empresaId").value(hasItem(DEFAULT_EMPRESA_ID)))
            .andExpect(jsonPath("$.[*].calorias").value(hasItem(DEFAULT_CALORIAS)))
            .andExpect(jsonPath("$.[*].startTime").value(hasItem(DEFAULT_START_TIME.toString())))
            .andExpect(jsonPath("$.[*].endTime").value(hasItem(DEFAULT_END_TIME.toString())));
    }

    @Test
    @Transactional
    void getActiveMinutes() throws Exception {
        // Initialize the database
        activeMinutesRepository.saveAndFlush(activeMinutes);

        // Get the activeMinutes
        restActiveMinutesMockMvc
            .perform(get(ENTITY_API_URL_ID, activeMinutes.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(activeMinutes.getId().toString()))
            .andExpect(jsonPath("$.usuarioId").value(DEFAULT_USUARIO_ID))
            .andExpect(jsonPath("$.empresaId").value(DEFAULT_EMPRESA_ID))
            .andExpect(jsonPath("$.calorias").value(DEFAULT_CALORIAS))
            .andExpect(jsonPath("$.startTime").value(DEFAULT_START_TIME.toString()))
            .andExpect(jsonPath("$.endTime").value(DEFAULT_END_TIME.toString()));
    }

    @Test
    @Transactional
    void getActiveMinutesByIdFiltering() throws Exception {
        // Initialize the database
        activeMinutesRepository.saveAndFlush(activeMinutes);

        UUID id = activeMinutes.getId();

        defaultActiveMinutesShouldBeFound("id.equals=" + id);
        defaultActiveMinutesShouldNotBeFound("id.notEquals=" + id);
    }

    @Test
    @Transactional
    void getAllActiveMinutesByUsuarioIdIsEqualToSomething() throws Exception {
        // Initialize the database
        activeMinutesRepository.saveAndFlush(activeMinutes);

        // Get all the activeMinutesList where usuarioId equals to DEFAULT_USUARIO_ID
        defaultActiveMinutesShouldBeFound("usuarioId.equals=" + DEFAULT_USUARIO_ID);

        // Get all the activeMinutesList where usuarioId equals to UPDATED_USUARIO_ID
        defaultActiveMinutesShouldNotBeFound("usuarioId.equals=" + UPDATED_USUARIO_ID);
    }

    @Test
    @Transactional
    void getAllActiveMinutesByUsuarioIdIsInShouldWork() throws Exception {
        // Initialize the database
        activeMinutesRepository.saveAndFlush(activeMinutes);

        // Get all the activeMinutesList where usuarioId in DEFAULT_USUARIO_ID or UPDATED_USUARIO_ID
        defaultActiveMinutesShouldBeFound("usuarioId.in=" + DEFAULT_USUARIO_ID + "," + UPDATED_USUARIO_ID);

        // Get all the activeMinutesList where usuarioId equals to UPDATED_USUARIO_ID
        defaultActiveMinutesShouldNotBeFound("usuarioId.in=" + UPDATED_USUARIO_ID);
    }

    @Test
    @Transactional
    void getAllActiveMinutesByUsuarioIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        activeMinutesRepository.saveAndFlush(activeMinutes);

        // Get all the activeMinutesList where usuarioId is not null
        defaultActiveMinutesShouldBeFound("usuarioId.specified=true");

        // Get all the activeMinutesList where usuarioId is null
        defaultActiveMinutesShouldNotBeFound("usuarioId.specified=false");
    }

    @Test
    @Transactional
    void getAllActiveMinutesByUsuarioIdContainsSomething() throws Exception {
        // Initialize the database
        activeMinutesRepository.saveAndFlush(activeMinutes);

        // Get all the activeMinutesList where usuarioId contains DEFAULT_USUARIO_ID
        defaultActiveMinutesShouldBeFound("usuarioId.contains=" + DEFAULT_USUARIO_ID);

        // Get all the activeMinutesList where usuarioId contains UPDATED_USUARIO_ID
        defaultActiveMinutesShouldNotBeFound("usuarioId.contains=" + UPDATED_USUARIO_ID);
    }

    @Test
    @Transactional
    void getAllActiveMinutesByUsuarioIdNotContainsSomething() throws Exception {
        // Initialize the database
        activeMinutesRepository.saveAndFlush(activeMinutes);

        // Get all the activeMinutesList where usuarioId does not contain DEFAULT_USUARIO_ID
        defaultActiveMinutesShouldNotBeFound("usuarioId.doesNotContain=" + DEFAULT_USUARIO_ID);

        // Get all the activeMinutesList where usuarioId does not contain UPDATED_USUARIO_ID
        defaultActiveMinutesShouldBeFound("usuarioId.doesNotContain=" + UPDATED_USUARIO_ID);
    }

    @Test
    @Transactional
    void getAllActiveMinutesByEmpresaIdIsEqualToSomething() throws Exception {
        // Initialize the database
        activeMinutesRepository.saveAndFlush(activeMinutes);

        // Get all the activeMinutesList where empresaId equals to DEFAULT_EMPRESA_ID
        defaultActiveMinutesShouldBeFound("empresaId.equals=" + DEFAULT_EMPRESA_ID);

        // Get all the activeMinutesList where empresaId equals to UPDATED_EMPRESA_ID
        defaultActiveMinutesShouldNotBeFound("empresaId.equals=" + UPDATED_EMPRESA_ID);
    }

    @Test
    @Transactional
    void getAllActiveMinutesByEmpresaIdIsInShouldWork() throws Exception {
        // Initialize the database
        activeMinutesRepository.saveAndFlush(activeMinutes);

        // Get all the activeMinutesList where empresaId in DEFAULT_EMPRESA_ID or UPDATED_EMPRESA_ID
        defaultActiveMinutesShouldBeFound("empresaId.in=" + DEFAULT_EMPRESA_ID + "," + UPDATED_EMPRESA_ID);

        // Get all the activeMinutesList where empresaId equals to UPDATED_EMPRESA_ID
        defaultActiveMinutesShouldNotBeFound("empresaId.in=" + UPDATED_EMPRESA_ID);
    }

    @Test
    @Transactional
    void getAllActiveMinutesByEmpresaIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        activeMinutesRepository.saveAndFlush(activeMinutes);

        // Get all the activeMinutesList where empresaId is not null
        defaultActiveMinutesShouldBeFound("empresaId.specified=true");

        // Get all the activeMinutesList where empresaId is null
        defaultActiveMinutesShouldNotBeFound("empresaId.specified=false");
    }

    @Test
    @Transactional
    void getAllActiveMinutesByEmpresaIdContainsSomething() throws Exception {
        // Initialize the database
        activeMinutesRepository.saveAndFlush(activeMinutes);

        // Get all the activeMinutesList where empresaId contains DEFAULT_EMPRESA_ID
        defaultActiveMinutesShouldBeFound("empresaId.contains=" + DEFAULT_EMPRESA_ID);

        // Get all the activeMinutesList where empresaId contains UPDATED_EMPRESA_ID
        defaultActiveMinutesShouldNotBeFound("empresaId.contains=" + UPDATED_EMPRESA_ID);
    }

    @Test
    @Transactional
    void getAllActiveMinutesByEmpresaIdNotContainsSomething() throws Exception {
        // Initialize the database
        activeMinutesRepository.saveAndFlush(activeMinutes);

        // Get all the activeMinutesList where empresaId does not contain DEFAULT_EMPRESA_ID
        defaultActiveMinutesShouldNotBeFound("empresaId.doesNotContain=" + DEFAULT_EMPRESA_ID);

        // Get all the activeMinutesList where empresaId does not contain UPDATED_EMPRESA_ID
        defaultActiveMinutesShouldBeFound("empresaId.doesNotContain=" + UPDATED_EMPRESA_ID);
    }

    @Test
    @Transactional
    void getAllActiveMinutesByCaloriasIsEqualToSomething() throws Exception {
        // Initialize the database
        activeMinutesRepository.saveAndFlush(activeMinutes);

        // Get all the activeMinutesList where calorias equals to DEFAULT_CALORIAS
        defaultActiveMinutesShouldBeFound("calorias.equals=" + DEFAULT_CALORIAS);

        // Get all the activeMinutesList where calorias equals to UPDATED_CALORIAS
        defaultActiveMinutesShouldNotBeFound("calorias.equals=" + UPDATED_CALORIAS);
    }

    @Test
    @Transactional
    void getAllActiveMinutesByCaloriasIsInShouldWork() throws Exception {
        // Initialize the database
        activeMinutesRepository.saveAndFlush(activeMinutes);

        // Get all the activeMinutesList where calorias in DEFAULT_CALORIAS or UPDATED_CALORIAS
        defaultActiveMinutesShouldBeFound("calorias.in=" + DEFAULT_CALORIAS + "," + UPDATED_CALORIAS);

        // Get all the activeMinutesList where calorias equals to UPDATED_CALORIAS
        defaultActiveMinutesShouldNotBeFound("calorias.in=" + UPDATED_CALORIAS);
    }

    @Test
    @Transactional
    void getAllActiveMinutesByCaloriasIsNullOrNotNull() throws Exception {
        // Initialize the database
        activeMinutesRepository.saveAndFlush(activeMinutes);

        // Get all the activeMinutesList where calorias is not null
        defaultActiveMinutesShouldBeFound("calorias.specified=true");

        // Get all the activeMinutesList where calorias is null
        defaultActiveMinutesShouldNotBeFound("calorias.specified=false");
    }

    @Test
    @Transactional
    void getAllActiveMinutesByCaloriasIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        activeMinutesRepository.saveAndFlush(activeMinutes);

        // Get all the activeMinutesList where calorias is greater than or equal to DEFAULT_CALORIAS
        defaultActiveMinutesShouldBeFound("calorias.greaterThanOrEqual=" + DEFAULT_CALORIAS);

        // Get all the activeMinutesList where calorias is greater than or equal to UPDATED_CALORIAS
        defaultActiveMinutesShouldNotBeFound("calorias.greaterThanOrEqual=" + UPDATED_CALORIAS);
    }

    @Test
    @Transactional
    void getAllActiveMinutesByCaloriasIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        activeMinutesRepository.saveAndFlush(activeMinutes);

        // Get all the activeMinutesList where calorias is less than or equal to DEFAULT_CALORIAS
        defaultActiveMinutesShouldBeFound("calorias.lessThanOrEqual=" + DEFAULT_CALORIAS);

        // Get all the activeMinutesList where calorias is less than or equal to SMALLER_CALORIAS
        defaultActiveMinutesShouldNotBeFound("calorias.lessThanOrEqual=" + SMALLER_CALORIAS);
    }

    @Test
    @Transactional
    void getAllActiveMinutesByCaloriasIsLessThanSomething() throws Exception {
        // Initialize the database
        activeMinutesRepository.saveAndFlush(activeMinutes);

        // Get all the activeMinutesList where calorias is less than DEFAULT_CALORIAS
        defaultActiveMinutesShouldNotBeFound("calorias.lessThan=" + DEFAULT_CALORIAS);

        // Get all the activeMinutesList where calorias is less than UPDATED_CALORIAS
        defaultActiveMinutesShouldBeFound("calorias.lessThan=" + UPDATED_CALORIAS);
    }

    @Test
    @Transactional
    void getAllActiveMinutesByCaloriasIsGreaterThanSomething() throws Exception {
        // Initialize the database
        activeMinutesRepository.saveAndFlush(activeMinutes);

        // Get all the activeMinutesList where calorias is greater than DEFAULT_CALORIAS
        defaultActiveMinutesShouldNotBeFound("calorias.greaterThan=" + DEFAULT_CALORIAS);

        // Get all the activeMinutesList where calorias is greater than SMALLER_CALORIAS
        defaultActiveMinutesShouldBeFound("calorias.greaterThan=" + SMALLER_CALORIAS);
    }

    @Test
    @Transactional
    void getAllActiveMinutesByStartTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        activeMinutesRepository.saveAndFlush(activeMinutes);

        // Get all the activeMinutesList where startTime equals to DEFAULT_START_TIME
        defaultActiveMinutesShouldBeFound("startTime.equals=" + DEFAULT_START_TIME);

        // Get all the activeMinutesList where startTime equals to UPDATED_START_TIME
        defaultActiveMinutesShouldNotBeFound("startTime.equals=" + UPDATED_START_TIME);
    }

    @Test
    @Transactional
    void getAllActiveMinutesByStartTimeIsInShouldWork() throws Exception {
        // Initialize the database
        activeMinutesRepository.saveAndFlush(activeMinutes);

        // Get all the activeMinutesList where startTime in DEFAULT_START_TIME or UPDATED_START_TIME
        defaultActiveMinutesShouldBeFound("startTime.in=" + DEFAULT_START_TIME + "," + UPDATED_START_TIME);

        // Get all the activeMinutesList where startTime equals to UPDATED_START_TIME
        defaultActiveMinutesShouldNotBeFound("startTime.in=" + UPDATED_START_TIME);
    }

    @Test
    @Transactional
    void getAllActiveMinutesByStartTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        activeMinutesRepository.saveAndFlush(activeMinutes);

        // Get all the activeMinutesList where startTime is not null
        defaultActiveMinutesShouldBeFound("startTime.specified=true");

        // Get all the activeMinutesList where startTime is null
        defaultActiveMinutesShouldNotBeFound("startTime.specified=false");
    }

    @Test
    @Transactional
    void getAllActiveMinutesByEndTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        activeMinutesRepository.saveAndFlush(activeMinutes);

        // Get all the activeMinutesList where endTime equals to DEFAULT_END_TIME
        defaultActiveMinutesShouldBeFound("endTime.equals=" + DEFAULT_END_TIME);

        // Get all the activeMinutesList where endTime equals to UPDATED_END_TIME
        defaultActiveMinutesShouldNotBeFound("endTime.equals=" + UPDATED_END_TIME);
    }

    @Test
    @Transactional
    void getAllActiveMinutesByEndTimeIsInShouldWork() throws Exception {
        // Initialize the database
        activeMinutesRepository.saveAndFlush(activeMinutes);

        // Get all the activeMinutesList where endTime in DEFAULT_END_TIME or UPDATED_END_TIME
        defaultActiveMinutesShouldBeFound("endTime.in=" + DEFAULT_END_TIME + "," + UPDATED_END_TIME);

        // Get all the activeMinutesList where endTime equals to UPDATED_END_TIME
        defaultActiveMinutesShouldNotBeFound("endTime.in=" + UPDATED_END_TIME);
    }

    @Test
    @Transactional
    void getAllActiveMinutesByEndTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        activeMinutesRepository.saveAndFlush(activeMinutes);

        // Get all the activeMinutesList where endTime is not null
        defaultActiveMinutesShouldBeFound("endTime.specified=true");

        // Get all the activeMinutesList where endTime is null
        defaultActiveMinutesShouldNotBeFound("endTime.specified=false");
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultActiveMinutesShouldBeFound(String filter) throws Exception {
        restActiveMinutesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(activeMinutes.getId().toString())))
            .andExpect(jsonPath("$.[*].usuarioId").value(hasItem(DEFAULT_USUARIO_ID)))
            .andExpect(jsonPath("$.[*].empresaId").value(hasItem(DEFAULT_EMPRESA_ID)))
            .andExpect(jsonPath("$.[*].calorias").value(hasItem(DEFAULT_CALORIAS)))
            .andExpect(jsonPath("$.[*].startTime").value(hasItem(DEFAULT_START_TIME.toString())))
            .andExpect(jsonPath("$.[*].endTime").value(hasItem(DEFAULT_END_TIME.toString())));

        // Check, that the count call also returns 1
        restActiveMinutesMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultActiveMinutesShouldNotBeFound(String filter) throws Exception {
        restActiveMinutesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restActiveMinutesMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingActiveMinutes() throws Exception {
        // Get the activeMinutes
        restActiveMinutesMockMvc.perform(get(ENTITY_API_URL_ID, UUID.randomUUID().toString())).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingActiveMinutes() throws Exception {
        // Initialize the database
        activeMinutesRepository.saveAndFlush(activeMinutes);

        int databaseSizeBeforeUpdate = activeMinutesRepository.findAll().size();

        // Update the activeMinutes
        ActiveMinutes updatedActiveMinutes = activeMinutesRepository.findById(activeMinutes.getId()).get();
        // Disconnect from session so that the updates on updatedActiveMinutes are not directly saved in db
        em.detach(updatedActiveMinutes);
        updatedActiveMinutes
            .usuarioId(UPDATED_USUARIO_ID)
            .empresaId(UPDATED_EMPRESA_ID)
            .calorias(UPDATED_CALORIAS)
            .startTime(UPDATED_START_TIME)
            .endTime(UPDATED_END_TIME);

        restActiveMinutesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedActiveMinutes.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedActiveMinutes))
            )
            .andExpect(status().isOk());

        // Validate the ActiveMinutes in the database
        List<ActiveMinutes> activeMinutesList = activeMinutesRepository.findAll();
        assertThat(activeMinutesList).hasSize(databaseSizeBeforeUpdate);
        ActiveMinutes testActiveMinutes = activeMinutesList.get(activeMinutesList.size() - 1);
        assertThat(testActiveMinutes.getUsuarioId()).isEqualTo(UPDATED_USUARIO_ID);
        assertThat(testActiveMinutes.getEmpresaId()).isEqualTo(UPDATED_EMPRESA_ID);
        assertThat(testActiveMinutes.getCalorias()).isEqualTo(UPDATED_CALORIAS);
        assertThat(testActiveMinutes.getStartTime()).isEqualTo(UPDATED_START_TIME);
        assertThat(testActiveMinutes.getEndTime()).isEqualTo(UPDATED_END_TIME);
    }

    @Test
    @Transactional
    void putNonExistingActiveMinutes() throws Exception {
        int databaseSizeBeforeUpdate = activeMinutesRepository.findAll().size();
        activeMinutes.setId(UUID.randomUUID());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restActiveMinutesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, activeMinutes.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(activeMinutes))
            )
            .andExpect(status().isBadRequest());

        // Validate the ActiveMinutes in the database
        List<ActiveMinutes> activeMinutesList = activeMinutesRepository.findAll();
        assertThat(activeMinutesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchActiveMinutes() throws Exception {
        int databaseSizeBeforeUpdate = activeMinutesRepository.findAll().size();
        activeMinutes.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restActiveMinutesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(activeMinutes))
            )
            .andExpect(status().isBadRequest());

        // Validate the ActiveMinutes in the database
        List<ActiveMinutes> activeMinutesList = activeMinutesRepository.findAll();
        assertThat(activeMinutesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamActiveMinutes() throws Exception {
        int databaseSizeBeforeUpdate = activeMinutesRepository.findAll().size();
        activeMinutes.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restActiveMinutesMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(activeMinutes)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ActiveMinutes in the database
        List<ActiveMinutes> activeMinutesList = activeMinutesRepository.findAll();
        assertThat(activeMinutesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateActiveMinutesWithPatch() throws Exception {
        // Initialize the database
        activeMinutesRepository.saveAndFlush(activeMinutes);

        int databaseSizeBeforeUpdate = activeMinutesRepository.findAll().size();

        // Update the activeMinutes using partial update
        ActiveMinutes partialUpdatedActiveMinutes = new ActiveMinutes();
        partialUpdatedActiveMinutes.setId(activeMinutes.getId());

        partialUpdatedActiveMinutes.calorias(UPDATED_CALORIAS).endTime(UPDATED_END_TIME);

        restActiveMinutesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedActiveMinutes.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedActiveMinutes))
            )
            .andExpect(status().isOk());

        // Validate the ActiveMinutes in the database
        List<ActiveMinutes> activeMinutesList = activeMinutesRepository.findAll();
        assertThat(activeMinutesList).hasSize(databaseSizeBeforeUpdate);
        ActiveMinutes testActiveMinutes = activeMinutesList.get(activeMinutesList.size() - 1);
        assertThat(testActiveMinutes.getUsuarioId()).isEqualTo(DEFAULT_USUARIO_ID);
        assertThat(testActiveMinutes.getEmpresaId()).isEqualTo(DEFAULT_EMPRESA_ID);
        assertThat(testActiveMinutes.getCalorias()).isEqualTo(UPDATED_CALORIAS);
        assertThat(testActiveMinutes.getStartTime()).isEqualTo(DEFAULT_START_TIME);
        assertThat(testActiveMinutes.getEndTime()).isEqualTo(UPDATED_END_TIME);
    }

    @Test
    @Transactional
    void fullUpdateActiveMinutesWithPatch() throws Exception {
        // Initialize the database
        activeMinutesRepository.saveAndFlush(activeMinutes);

        int databaseSizeBeforeUpdate = activeMinutesRepository.findAll().size();

        // Update the activeMinutes using partial update
        ActiveMinutes partialUpdatedActiveMinutes = new ActiveMinutes();
        partialUpdatedActiveMinutes.setId(activeMinutes.getId());

        partialUpdatedActiveMinutes
            .usuarioId(UPDATED_USUARIO_ID)
            .empresaId(UPDATED_EMPRESA_ID)
            .calorias(UPDATED_CALORIAS)
            .startTime(UPDATED_START_TIME)
            .endTime(UPDATED_END_TIME);

        restActiveMinutesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedActiveMinutes.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedActiveMinutes))
            )
            .andExpect(status().isOk());

        // Validate the ActiveMinutes in the database
        List<ActiveMinutes> activeMinutesList = activeMinutesRepository.findAll();
        assertThat(activeMinutesList).hasSize(databaseSizeBeforeUpdate);
        ActiveMinutes testActiveMinutes = activeMinutesList.get(activeMinutesList.size() - 1);
        assertThat(testActiveMinutes.getUsuarioId()).isEqualTo(UPDATED_USUARIO_ID);
        assertThat(testActiveMinutes.getEmpresaId()).isEqualTo(UPDATED_EMPRESA_ID);
        assertThat(testActiveMinutes.getCalorias()).isEqualTo(UPDATED_CALORIAS);
        assertThat(testActiveMinutes.getStartTime()).isEqualTo(UPDATED_START_TIME);
        assertThat(testActiveMinutes.getEndTime()).isEqualTo(UPDATED_END_TIME);
    }

    @Test
    @Transactional
    void patchNonExistingActiveMinutes() throws Exception {
        int databaseSizeBeforeUpdate = activeMinutesRepository.findAll().size();
        activeMinutes.setId(UUID.randomUUID());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restActiveMinutesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, activeMinutes.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(activeMinutes))
            )
            .andExpect(status().isBadRequest());

        // Validate the ActiveMinutes in the database
        List<ActiveMinutes> activeMinutesList = activeMinutesRepository.findAll();
        assertThat(activeMinutesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchActiveMinutes() throws Exception {
        int databaseSizeBeforeUpdate = activeMinutesRepository.findAll().size();
        activeMinutes.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restActiveMinutesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(activeMinutes))
            )
            .andExpect(status().isBadRequest());

        // Validate the ActiveMinutes in the database
        List<ActiveMinutes> activeMinutesList = activeMinutesRepository.findAll();
        assertThat(activeMinutesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamActiveMinutes() throws Exception {
        int databaseSizeBeforeUpdate = activeMinutesRepository.findAll().size();
        activeMinutes.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restActiveMinutesMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(activeMinutes))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ActiveMinutes in the database
        List<ActiveMinutes> activeMinutesList = activeMinutesRepository.findAll();
        assertThat(activeMinutesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteActiveMinutes() throws Exception {
        // Initialize the database
        activeMinutesRepository.saveAndFlush(activeMinutes);

        int databaseSizeBeforeDelete = activeMinutesRepository.findAll().size();

        // Delete the activeMinutes
        restActiveMinutesMockMvc
            .perform(delete(ENTITY_API_URL_ID, activeMinutes.getId().toString()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ActiveMinutes> activeMinutesList = activeMinutesRepository.findAll();
        assertThat(activeMinutesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
