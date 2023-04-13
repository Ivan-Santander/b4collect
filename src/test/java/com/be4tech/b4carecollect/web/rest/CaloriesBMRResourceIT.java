package com.be4tech.b4carecollect.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.be4tech.b4carecollect.IntegrationTest;
import com.be4tech.b4carecollect.domain.CaloriesBMR;
import com.be4tech.b4carecollect.repository.CaloriesBMRRepository;
import com.be4tech.b4carecollect.service.criteria.CaloriesBMRCriteria;
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
 * Integration tests for the {@link CaloriesBMRResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CaloriesBMRResourceIT {

    private static final String DEFAULT_USUARIO_ID = "AAAAAAAAAA";
    private static final String UPDATED_USUARIO_ID = "BBBBBBBBBB";

    private static final String DEFAULT_EMPRESA_ID = "AAAAAAAAAA";
    private static final String UPDATED_EMPRESA_ID = "BBBBBBBBBB";

    private static final Float DEFAULT_CALORIAS = 1F;
    private static final Float UPDATED_CALORIAS = 2F;
    private static final Float SMALLER_CALORIAS = 1F - 1F;

    private static final Instant DEFAULT_START_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_START_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_END_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_END_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/calories-bmrs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private CaloriesBMRRepository caloriesBMRRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCaloriesBMRMockMvc;

    private CaloriesBMR caloriesBMR;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CaloriesBMR createEntity(EntityManager em) {
        CaloriesBMR caloriesBMR = new CaloriesBMR()
            .usuarioId(DEFAULT_USUARIO_ID)
            .empresaId(DEFAULT_EMPRESA_ID)
            .calorias(DEFAULT_CALORIAS)
            .startTime(DEFAULT_START_TIME)
            .endTime(DEFAULT_END_TIME);
        return caloriesBMR;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CaloriesBMR createUpdatedEntity(EntityManager em) {
        CaloriesBMR caloriesBMR = new CaloriesBMR()
            .usuarioId(UPDATED_USUARIO_ID)
            .empresaId(UPDATED_EMPRESA_ID)
            .calorias(UPDATED_CALORIAS)
            .startTime(UPDATED_START_TIME)
            .endTime(UPDATED_END_TIME);
        return caloriesBMR;
    }

    @BeforeEach
    public void initTest() {
        caloriesBMR = createEntity(em);
    }

    @Test
    @Transactional
    void createCaloriesBMR() throws Exception {
        int databaseSizeBeforeCreate = caloriesBMRRepository.findAll().size();
        // Create the CaloriesBMR
        restCaloriesBMRMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(caloriesBMR)))
            .andExpect(status().isCreated());

        // Validate the CaloriesBMR in the database
        List<CaloriesBMR> caloriesBMRList = caloriesBMRRepository.findAll();
        assertThat(caloriesBMRList).hasSize(databaseSizeBeforeCreate + 1);
        CaloriesBMR testCaloriesBMR = caloriesBMRList.get(caloriesBMRList.size() - 1);
        assertThat(testCaloriesBMR.getUsuarioId()).isEqualTo(DEFAULT_USUARIO_ID);
        assertThat(testCaloriesBMR.getEmpresaId()).isEqualTo(DEFAULT_EMPRESA_ID);
        assertThat(testCaloriesBMR.getCalorias()).isEqualTo(DEFAULT_CALORIAS);
        assertThat(testCaloriesBMR.getStartTime()).isEqualTo(DEFAULT_START_TIME);
        assertThat(testCaloriesBMR.getEndTime()).isEqualTo(DEFAULT_END_TIME);
    }

    @Test
    @Transactional
    void createCaloriesBMRWithExistingId() throws Exception {
        // Create the CaloriesBMR with an existing ID
        caloriesBMRRepository.saveAndFlush(caloriesBMR);

        int databaseSizeBeforeCreate = caloriesBMRRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCaloriesBMRMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(caloriesBMR)))
            .andExpect(status().isBadRequest());

        // Validate the CaloriesBMR in the database
        List<CaloriesBMR> caloriesBMRList = caloriesBMRRepository.findAll();
        assertThat(caloriesBMRList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllCaloriesBMRS() throws Exception {
        // Initialize the database
        caloriesBMRRepository.saveAndFlush(caloriesBMR);

        // Get all the caloriesBMRList
        restCaloriesBMRMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(caloriesBMR.getId().toString())))
            .andExpect(jsonPath("$.[*].usuarioId").value(hasItem(DEFAULT_USUARIO_ID)))
            .andExpect(jsonPath("$.[*].empresaId").value(hasItem(DEFAULT_EMPRESA_ID)))
            .andExpect(jsonPath("$.[*].calorias").value(hasItem(DEFAULT_CALORIAS.doubleValue())))
            .andExpect(jsonPath("$.[*].startTime").value(hasItem(DEFAULT_START_TIME.toString())))
            .andExpect(jsonPath("$.[*].endTime").value(hasItem(DEFAULT_END_TIME.toString())));
    }

    @Test
    @Transactional
    void getCaloriesBMR() throws Exception {
        // Initialize the database
        caloriesBMRRepository.saveAndFlush(caloriesBMR);

        // Get the caloriesBMR
        restCaloriesBMRMockMvc
            .perform(get(ENTITY_API_URL_ID, caloriesBMR.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(caloriesBMR.getId().toString()))
            .andExpect(jsonPath("$.usuarioId").value(DEFAULT_USUARIO_ID))
            .andExpect(jsonPath("$.empresaId").value(DEFAULT_EMPRESA_ID))
            .andExpect(jsonPath("$.calorias").value(DEFAULT_CALORIAS.doubleValue()))
            .andExpect(jsonPath("$.startTime").value(DEFAULT_START_TIME.toString()))
            .andExpect(jsonPath("$.endTime").value(DEFAULT_END_TIME.toString()));
    }

    @Test
    @Transactional
    void getCaloriesBMRSByIdFiltering() throws Exception {
        // Initialize the database
        caloriesBMRRepository.saveAndFlush(caloriesBMR);

        UUID id = caloriesBMR.getId();

        defaultCaloriesBMRShouldBeFound("id.equals=" + id);
        defaultCaloriesBMRShouldNotBeFound("id.notEquals=" + id);
    }

    @Test
    @Transactional
    void getAllCaloriesBMRSByUsuarioIdIsEqualToSomething() throws Exception {
        // Initialize the database
        caloriesBMRRepository.saveAndFlush(caloriesBMR);

        // Get all the caloriesBMRList where usuarioId equals to DEFAULT_USUARIO_ID
        defaultCaloriesBMRShouldBeFound("usuarioId.equals=" + DEFAULT_USUARIO_ID);

        // Get all the caloriesBMRList where usuarioId equals to UPDATED_USUARIO_ID
        defaultCaloriesBMRShouldNotBeFound("usuarioId.equals=" + UPDATED_USUARIO_ID);
    }

    @Test
    @Transactional
    void getAllCaloriesBMRSByUsuarioIdIsInShouldWork() throws Exception {
        // Initialize the database
        caloriesBMRRepository.saveAndFlush(caloriesBMR);

        // Get all the caloriesBMRList where usuarioId in DEFAULT_USUARIO_ID or UPDATED_USUARIO_ID
        defaultCaloriesBMRShouldBeFound("usuarioId.in=" + DEFAULT_USUARIO_ID + "," + UPDATED_USUARIO_ID);

        // Get all the caloriesBMRList where usuarioId equals to UPDATED_USUARIO_ID
        defaultCaloriesBMRShouldNotBeFound("usuarioId.in=" + UPDATED_USUARIO_ID);
    }

    @Test
    @Transactional
    void getAllCaloriesBMRSByUsuarioIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        caloriesBMRRepository.saveAndFlush(caloriesBMR);

        // Get all the caloriesBMRList where usuarioId is not null
        defaultCaloriesBMRShouldBeFound("usuarioId.specified=true");

        // Get all the caloriesBMRList where usuarioId is null
        defaultCaloriesBMRShouldNotBeFound("usuarioId.specified=false");
    }

    @Test
    @Transactional
    void getAllCaloriesBMRSByUsuarioIdContainsSomething() throws Exception {
        // Initialize the database
        caloriesBMRRepository.saveAndFlush(caloriesBMR);

        // Get all the caloriesBMRList where usuarioId contains DEFAULT_USUARIO_ID
        defaultCaloriesBMRShouldBeFound("usuarioId.contains=" + DEFAULT_USUARIO_ID);

        // Get all the caloriesBMRList where usuarioId contains UPDATED_USUARIO_ID
        defaultCaloriesBMRShouldNotBeFound("usuarioId.contains=" + UPDATED_USUARIO_ID);
    }

    @Test
    @Transactional
    void getAllCaloriesBMRSByUsuarioIdNotContainsSomething() throws Exception {
        // Initialize the database
        caloriesBMRRepository.saveAndFlush(caloriesBMR);

        // Get all the caloriesBMRList where usuarioId does not contain DEFAULT_USUARIO_ID
        defaultCaloriesBMRShouldNotBeFound("usuarioId.doesNotContain=" + DEFAULT_USUARIO_ID);

        // Get all the caloriesBMRList where usuarioId does not contain UPDATED_USUARIO_ID
        defaultCaloriesBMRShouldBeFound("usuarioId.doesNotContain=" + UPDATED_USUARIO_ID);
    }

    @Test
    @Transactional
    void getAllCaloriesBMRSByEmpresaIdIsEqualToSomething() throws Exception {
        // Initialize the database
        caloriesBMRRepository.saveAndFlush(caloriesBMR);

        // Get all the caloriesBMRList where empresaId equals to DEFAULT_EMPRESA_ID
        defaultCaloriesBMRShouldBeFound("empresaId.equals=" + DEFAULT_EMPRESA_ID);

        // Get all the caloriesBMRList where empresaId equals to UPDATED_EMPRESA_ID
        defaultCaloriesBMRShouldNotBeFound("empresaId.equals=" + UPDATED_EMPRESA_ID);
    }

    @Test
    @Transactional
    void getAllCaloriesBMRSByEmpresaIdIsInShouldWork() throws Exception {
        // Initialize the database
        caloriesBMRRepository.saveAndFlush(caloriesBMR);

        // Get all the caloriesBMRList where empresaId in DEFAULT_EMPRESA_ID or UPDATED_EMPRESA_ID
        defaultCaloriesBMRShouldBeFound("empresaId.in=" + DEFAULT_EMPRESA_ID + "," + UPDATED_EMPRESA_ID);

        // Get all the caloriesBMRList where empresaId equals to UPDATED_EMPRESA_ID
        defaultCaloriesBMRShouldNotBeFound("empresaId.in=" + UPDATED_EMPRESA_ID);
    }

    @Test
    @Transactional
    void getAllCaloriesBMRSByEmpresaIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        caloriesBMRRepository.saveAndFlush(caloriesBMR);

        // Get all the caloriesBMRList where empresaId is not null
        defaultCaloriesBMRShouldBeFound("empresaId.specified=true");

        // Get all the caloriesBMRList where empresaId is null
        defaultCaloriesBMRShouldNotBeFound("empresaId.specified=false");
    }

    @Test
    @Transactional
    void getAllCaloriesBMRSByEmpresaIdContainsSomething() throws Exception {
        // Initialize the database
        caloriesBMRRepository.saveAndFlush(caloriesBMR);

        // Get all the caloriesBMRList where empresaId contains DEFAULT_EMPRESA_ID
        defaultCaloriesBMRShouldBeFound("empresaId.contains=" + DEFAULT_EMPRESA_ID);

        // Get all the caloriesBMRList where empresaId contains UPDATED_EMPRESA_ID
        defaultCaloriesBMRShouldNotBeFound("empresaId.contains=" + UPDATED_EMPRESA_ID);
    }

    @Test
    @Transactional
    void getAllCaloriesBMRSByEmpresaIdNotContainsSomething() throws Exception {
        // Initialize the database
        caloriesBMRRepository.saveAndFlush(caloriesBMR);

        // Get all the caloriesBMRList where empresaId does not contain DEFAULT_EMPRESA_ID
        defaultCaloriesBMRShouldNotBeFound("empresaId.doesNotContain=" + DEFAULT_EMPRESA_ID);

        // Get all the caloriesBMRList where empresaId does not contain UPDATED_EMPRESA_ID
        defaultCaloriesBMRShouldBeFound("empresaId.doesNotContain=" + UPDATED_EMPRESA_ID);
    }

    @Test
    @Transactional
    void getAllCaloriesBMRSByCaloriasIsEqualToSomething() throws Exception {
        // Initialize the database
        caloriesBMRRepository.saveAndFlush(caloriesBMR);

        // Get all the caloriesBMRList where calorias equals to DEFAULT_CALORIAS
        defaultCaloriesBMRShouldBeFound("calorias.equals=" + DEFAULT_CALORIAS);

        // Get all the caloriesBMRList where calorias equals to UPDATED_CALORIAS
        defaultCaloriesBMRShouldNotBeFound("calorias.equals=" + UPDATED_CALORIAS);
    }

    @Test
    @Transactional
    void getAllCaloriesBMRSByCaloriasIsInShouldWork() throws Exception {
        // Initialize the database
        caloriesBMRRepository.saveAndFlush(caloriesBMR);

        // Get all the caloriesBMRList where calorias in DEFAULT_CALORIAS or UPDATED_CALORIAS
        defaultCaloriesBMRShouldBeFound("calorias.in=" + DEFAULT_CALORIAS + "," + UPDATED_CALORIAS);

        // Get all the caloriesBMRList where calorias equals to UPDATED_CALORIAS
        defaultCaloriesBMRShouldNotBeFound("calorias.in=" + UPDATED_CALORIAS);
    }

    @Test
    @Transactional
    void getAllCaloriesBMRSByCaloriasIsNullOrNotNull() throws Exception {
        // Initialize the database
        caloriesBMRRepository.saveAndFlush(caloriesBMR);

        // Get all the caloriesBMRList where calorias is not null
        defaultCaloriesBMRShouldBeFound("calorias.specified=true");

        // Get all the caloriesBMRList where calorias is null
        defaultCaloriesBMRShouldNotBeFound("calorias.specified=false");
    }

    @Test
    @Transactional
    void getAllCaloriesBMRSByCaloriasIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        caloriesBMRRepository.saveAndFlush(caloriesBMR);

        // Get all the caloriesBMRList where calorias is greater than or equal to DEFAULT_CALORIAS
        defaultCaloriesBMRShouldBeFound("calorias.greaterThanOrEqual=" + DEFAULT_CALORIAS);

        // Get all the caloriesBMRList where calorias is greater than or equal to UPDATED_CALORIAS
        defaultCaloriesBMRShouldNotBeFound("calorias.greaterThanOrEqual=" + UPDATED_CALORIAS);
    }

    @Test
    @Transactional
    void getAllCaloriesBMRSByCaloriasIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        caloriesBMRRepository.saveAndFlush(caloriesBMR);

        // Get all the caloriesBMRList where calorias is less than or equal to DEFAULT_CALORIAS
        defaultCaloriesBMRShouldBeFound("calorias.lessThanOrEqual=" + DEFAULT_CALORIAS);

        // Get all the caloriesBMRList where calorias is less than or equal to SMALLER_CALORIAS
        defaultCaloriesBMRShouldNotBeFound("calorias.lessThanOrEqual=" + SMALLER_CALORIAS);
    }

    @Test
    @Transactional
    void getAllCaloriesBMRSByCaloriasIsLessThanSomething() throws Exception {
        // Initialize the database
        caloriesBMRRepository.saveAndFlush(caloriesBMR);

        // Get all the caloriesBMRList where calorias is less than DEFAULT_CALORIAS
        defaultCaloriesBMRShouldNotBeFound("calorias.lessThan=" + DEFAULT_CALORIAS);

        // Get all the caloriesBMRList where calorias is less than UPDATED_CALORIAS
        defaultCaloriesBMRShouldBeFound("calorias.lessThan=" + UPDATED_CALORIAS);
    }

    @Test
    @Transactional
    void getAllCaloriesBMRSByCaloriasIsGreaterThanSomething() throws Exception {
        // Initialize the database
        caloriesBMRRepository.saveAndFlush(caloriesBMR);

        // Get all the caloriesBMRList where calorias is greater than DEFAULT_CALORIAS
        defaultCaloriesBMRShouldNotBeFound("calorias.greaterThan=" + DEFAULT_CALORIAS);

        // Get all the caloriesBMRList where calorias is greater than SMALLER_CALORIAS
        defaultCaloriesBMRShouldBeFound("calorias.greaterThan=" + SMALLER_CALORIAS);
    }

    @Test
    @Transactional
    void getAllCaloriesBMRSByStartTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        caloriesBMRRepository.saveAndFlush(caloriesBMR);

        // Get all the caloriesBMRList where startTime equals to DEFAULT_START_TIME
        defaultCaloriesBMRShouldBeFound("startTime.equals=" + DEFAULT_START_TIME);

        // Get all the caloriesBMRList where startTime equals to UPDATED_START_TIME
        defaultCaloriesBMRShouldNotBeFound("startTime.equals=" + UPDATED_START_TIME);
    }

    @Test
    @Transactional
    void getAllCaloriesBMRSByStartTimeIsInShouldWork() throws Exception {
        // Initialize the database
        caloriesBMRRepository.saveAndFlush(caloriesBMR);

        // Get all the caloriesBMRList where startTime in DEFAULT_START_TIME or UPDATED_START_TIME
        defaultCaloriesBMRShouldBeFound("startTime.in=" + DEFAULT_START_TIME + "," + UPDATED_START_TIME);

        // Get all the caloriesBMRList where startTime equals to UPDATED_START_TIME
        defaultCaloriesBMRShouldNotBeFound("startTime.in=" + UPDATED_START_TIME);
    }

    @Test
    @Transactional
    void getAllCaloriesBMRSByStartTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        caloriesBMRRepository.saveAndFlush(caloriesBMR);

        // Get all the caloriesBMRList where startTime is not null
        defaultCaloriesBMRShouldBeFound("startTime.specified=true");

        // Get all the caloriesBMRList where startTime is null
        defaultCaloriesBMRShouldNotBeFound("startTime.specified=false");
    }

    @Test
    @Transactional
    void getAllCaloriesBMRSByEndTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        caloriesBMRRepository.saveAndFlush(caloriesBMR);

        // Get all the caloriesBMRList where endTime equals to DEFAULT_END_TIME
        defaultCaloriesBMRShouldBeFound("endTime.equals=" + DEFAULT_END_TIME);

        // Get all the caloriesBMRList where endTime equals to UPDATED_END_TIME
        defaultCaloriesBMRShouldNotBeFound("endTime.equals=" + UPDATED_END_TIME);
    }

    @Test
    @Transactional
    void getAllCaloriesBMRSByEndTimeIsInShouldWork() throws Exception {
        // Initialize the database
        caloriesBMRRepository.saveAndFlush(caloriesBMR);

        // Get all the caloriesBMRList where endTime in DEFAULT_END_TIME or UPDATED_END_TIME
        defaultCaloriesBMRShouldBeFound("endTime.in=" + DEFAULT_END_TIME + "," + UPDATED_END_TIME);

        // Get all the caloriesBMRList where endTime equals to UPDATED_END_TIME
        defaultCaloriesBMRShouldNotBeFound("endTime.in=" + UPDATED_END_TIME);
    }

    @Test
    @Transactional
    void getAllCaloriesBMRSByEndTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        caloriesBMRRepository.saveAndFlush(caloriesBMR);

        // Get all the caloriesBMRList where endTime is not null
        defaultCaloriesBMRShouldBeFound("endTime.specified=true");

        // Get all the caloriesBMRList where endTime is null
        defaultCaloriesBMRShouldNotBeFound("endTime.specified=false");
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCaloriesBMRShouldBeFound(String filter) throws Exception {
        restCaloriesBMRMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(caloriesBMR.getId().toString())))
            .andExpect(jsonPath("$.[*].usuarioId").value(hasItem(DEFAULT_USUARIO_ID)))
            .andExpect(jsonPath("$.[*].empresaId").value(hasItem(DEFAULT_EMPRESA_ID)))
            .andExpect(jsonPath("$.[*].calorias").value(hasItem(DEFAULT_CALORIAS.doubleValue())))
            .andExpect(jsonPath("$.[*].startTime").value(hasItem(DEFAULT_START_TIME.toString())))
            .andExpect(jsonPath("$.[*].endTime").value(hasItem(DEFAULT_END_TIME.toString())));

        // Check, that the count call also returns 1
        restCaloriesBMRMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCaloriesBMRShouldNotBeFound(String filter) throws Exception {
        restCaloriesBMRMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCaloriesBMRMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingCaloriesBMR() throws Exception {
        // Get the caloriesBMR
        restCaloriesBMRMockMvc.perform(get(ENTITY_API_URL_ID, UUID.randomUUID().toString())).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingCaloriesBMR() throws Exception {
        // Initialize the database
        caloriesBMRRepository.saveAndFlush(caloriesBMR);

        int databaseSizeBeforeUpdate = caloriesBMRRepository.findAll().size();

        // Update the caloriesBMR
        CaloriesBMR updatedCaloriesBMR = caloriesBMRRepository.findById(caloriesBMR.getId()).get();
        // Disconnect from session so that the updates on updatedCaloriesBMR are not directly saved in db
        em.detach(updatedCaloriesBMR);
        updatedCaloriesBMR
            .usuarioId(UPDATED_USUARIO_ID)
            .empresaId(UPDATED_EMPRESA_ID)
            .calorias(UPDATED_CALORIAS)
            .startTime(UPDATED_START_TIME)
            .endTime(UPDATED_END_TIME);

        restCaloriesBMRMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedCaloriesBMR.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedCaloriesBMR))
            )
            .andExpect(status().isOk());

        // Validate the CaloriesBMR in the database
        List<CaloriesBMR> caloriesBMRList = caloriesBMRRepository.findAll();
        assertThat(caloriesBMRList).hasSize(databaseSizeBeforeUpdate);
        CaloriesBMR testCaloriesBMR = caloriesBMRList.get(caloriesBMRList.size() - 1);
        assertThat(testCaloriesBMR.getUsuarioId()).isEqualTo(UPDATED_USUARIO_ID);
        assertThat(testCaloriesBMR.getEmpresaId()).isEqualTo(UPDATED_EMPRESA_ID);
        assertThat(testCaloriesBMR.getCalorias()).isEqualTo(UPDATED_CALORIAS);
        assertThat(testCaloriesBMR.getStartTime()).isEqualTo(UPDATED_START_TIME);
        assertThat(testCaloriesBMR.getEndTime()).isEqualTo(UPDATED_END_TIME);
    }

    @Test
    @Transactional
    void putNonExistingCaloriesBMR() throws Exception {
        int databaseSizeBeforeUpdate = caloriesBMRRepository.findAll().size();
        caloriesBMR.setId(UUID.randomUUID());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCaloriesBMRMockMvc
            .perform(
                put(ENTITY_API_URL_ID, caloriesBMR.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(caloriesBMR))
            )
            .andExpect(status().isBadRequest());

        // Validate the CaloriesBMR in the database
        List<CaloriesBMR> caloriesBMRList = caloriesBMRRepository.findAll();
        assertThat(caloriesBMRList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCaloriesBMR() throws Exception {
        int databaseSizeBeforeUpdate = caloriesBMRRepository.findAll().size();
        caloriesBMR.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCaloriesBMRMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(caloriesBMR))
            )
            .andExpect(status().isBadRequest());

        // Validate the CaloriesBMR in the database
        List<CaloriesBMR> caloriesBMRList = caloriesBMRRepository.findAll();
        assertThat(caloriesBMRList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCaloriesBMR() throws Exception {
        int databaseSizeBeforeUpdate = caloriesBMRRepository.findAll().size();
        caloriesBMR.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCaloriesBMRMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(caloriesBMR)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the CaloriesBMR in the database
        List<CaloriesBMR> caloriesBMRList = caloriesBMRRepository.findAll();
        assertThat(caloriesBMRList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCaloriesBMRWithPatch() throws Exception {
        // Initialize the database
        caloriesBMRRepository.saveAndFlush(caloriesBMR);

        int databaseSizeBeforeUpdate = caloriesBMRRepository.findAll().size();

        // Update the caloriesBMR using partial update
        CaloriesBMR partialUpdatedCaloriesBMR = new CaloriesBMR();
        partialUpdatedCaloriesBMR.setId(caloriesBMR.getId());

        partialUpdatedCaloriesBMR
            .usuarioId(UPDATED_USUARIO_ID)
            .empresaId(UPDATED_EMPRESA_ID)
            .calorias(UPDATED_CALORIAS)
            .endTime(UPDATED_END_TIME);

        restCaloriesBMRMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCaloriesBMR.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCaloriesBMR))
            )
            .andExpect(status().isOk());

        // Validate the CaloriesBMR in the database
        List<CaloriesBMR> caloriesBMRList = caloriesBMRRepository.findAll();
        assertThat(caloriesBMRList).hasSize(databaseSizeBeforeUpdate);
        CaloriesBMR testCaloriesBMR = caloriesBMRList.get(caloriesBMRList.size() - 1);
        assertThat(testCaloriesBMR.getUsuarioId()).isEqualTo(UPDATED_USUARIO_ID);
        assertThat(testCaloriesBMR.getEmpresaId()).isEqualTo(UPDATED_EMPRESA_ID);
        assertThat(testCaloriesBMR.getCalorias()).isEqualTo(UPDATED_CALORIAS);
        assertThat(testCaloriesBMR.getStartTime()).isEqualTo(DEFAULT_START_TIME);
        assertThat(testCaloriesBMR.getEndTime()).isEqualTo(UPDATED_END_TIME);
    }

    @Test
    @Transactional
    void fullUpdateCaloriesBMRWithPatch() throws Exception {
        // Initialize the database
        caloriesBMRRepository.saveAndFlush(caloriesBMR);

        int databaseSizeBeforeUpdate = caloriesBMRRepository.findAll().size();

        // Update the caloriesBMR using partial update
        CaloriesBMR partialUpdatedCaloriesBMR = new CaloriesBMR();
        partialUpdatedCaloriesBMR.setId(caloriesBMR.getId());

        partialUpdatedCaloriesBMR
            .usuarioId(UPDATED_USUARIO_ID)
            .empresaId(UPDATED_EMPRESA_ID)
            .calorias(UPDATED_CALORIAS)
            .startTime(UPDATED_START_TIME)
            .endTime(UPDATED_END_TIME);

        restCaloriesBMRMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCaloriesBMR.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCaloriesBMR))
            )
            .andExpect(status().isOk());

        // Validate the CaloriesBMR in the database
        List<CaloriesBMR> caloriesBMRList = caloriesBMRRepository.findAll();
        assertThat(caloriesBMRList).hasSize(databaseSizeBeforeUpdate);
        CaloriesBMR testCaloriesBMR = caloriesBMRList.get(caloriesBMRList.size() - 1);
        assertThat(testCaloriesBMR.getUsuarioId()).isEqualTo(UPDATED_USUARIO_ID);
        assertThat(testCaloriesBMR.getEmpresaId()).isEqualTo(UPDATED_EMPRESA_ID);
        assertThat(testCaloriesBMR.getCalorias()).isEqualTo(UPDATED_CALORIAS);
        assertThat(testCaloriesBMR.getStartTime()).isEqualTo(UPDATED_START_TIME);
        assertThat(testCaloriesBMR.getEndTime()).isEqualTo(UPDATED_END_TIME);
    }

    @Test
    @Transactional
    void patchNonExistingCaloriesBMR() throws Exception {
        int databaseSizeBeforeUpdate = caloriesBMRRepository.findAll().size();
        caloriesBMR.setId(UUID.randomUUID());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCaloriesBMRMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, caloriesBMR.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(caloriesBMR))
            )
            .andExpect(status().isBadRequest());

        // Validate the CaloriesBMR in the database
        List<CaloriesBMR> caloriesBMRList = caloriesBMRRepository.findAll();
        assertThat(caloriesBMRList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCaloriesBMR() throws Exception {
        int databaseSizeBeforeUpdate = caloriesBMRRepository.findAll().size();
        caloriesBMR.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCaloriesBMRMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(caloriesBMR))
            )
            .andExpect(status().isBadRequest());

        // Validate the CaloriesBMR in the database
        List<CaloriesBMR> caloriesBMRList = caloriesBMRRepository.findAll();
        assertThat(caloriesBMRList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCaloriesBMR() throws Exception {
        int databaseSizeBeforeUpdate = caloriesBMRRepository.findAll().size();
        caloriesBMR.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCaloriesBMRMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(caloriesBMR))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CaloriesBMR in the database
        List<CaloriesBMR> caloriesBMRList = caloriesBMRRepository.findAll();
        assertThat(caloriesBMRList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCaloriesBMR() throws Exception {
        // Initialize the database
        caloriesBMRRepository.saveAndFlush(caloriesBMR);

        int databaseSizeBeforeDelete = caloriesBMRRepository.findAll().size();

        // Delete the caloriesBMR
        restCaloriesBMRMockMvc
            .perform(delete(ENTITY_API_URL_ID, caloriesBMR.getId().toString()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CaloriesBMR> caloriesBMRList = caloriesBMRRepository.findAll();
        assertThat(caloriesBMRList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
