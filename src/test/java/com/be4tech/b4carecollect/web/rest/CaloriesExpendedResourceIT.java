package com.be4tech.b4carecollect.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.be4tech.b4carecollect.IntegrationTest;
import com.be4tech.b4carecollect.domain.CaloriesExpended;
import com.be4tech.b4carecollect.repository.CaloriesExpendedRepository;
import com.be4tech.b4carecollect.service.criteria.CaloriesExpendedCriteria;
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
 * Integration tests for the {@link CaloriesExpendedResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CaloriesExpendedResourceIT {

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

    private static final String ENTITY_API_URL = "/api/calories-expendeds";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private CaloriesExpendedRepository caloriesExpendedRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCaloriesExpendedMockMvc;

    private CaloriesExpended caloriesExpended;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CaloriesExpended createEntity(EntityManager em) {
        CaloriesExpended caloriesExpended = new CaloriesExpended()
            .usuarioId(DEFAULT_USUARIO_ID)
            .empresaId(DEFAULT_EMPRESA_ID)
            .calorias(DEFAULT_CALORIAS)
            .startTime(DEFAULT_START_TIME)
            .endTime(DEFAULT_END_TIME);
        return caloriesExpended;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CaloriesExpended createUpdatedEntity(EntityManager em) {
        CaloriesExpended caloriesExpended = new CaloriesExpended()
            .usuarioId(UPDATED_USUARIO_ID)
            .empresaId(UPDATED_EMPRESA_ID)
            .calorias(UPDATED_CALORIAS)
            .startTime(UPDATED_START_TIME)
            .endTime(UPDATED_END_TIME);
        return caloriesExpended;
    }

    @BeforeEach
    public void initTest() {
        caloriesExpended = createEntity(em);
    }

    @Test
    @Transactional
    void createCaloriesExpended() throws Exception {
        int databaseSizeBeforeCreate = caloriesExpendedRepository.findAll().size();
        // Create the CaloriesExpended
        restCaloriesExpendedMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(caloriesExpended))
            )
            .andExpect(status().isCreated());

        // Validate the CaloriesExpended in the database
        List<CaloriesExpended> caloriesExpendedList = caloriesExpendedRepository.findAll();
        assertThat(caloriesExpendedList).hasSize(databaseSizeBeforeCreate + 1);
        CaloriesExpended testCaloriesExpended = caloriesExpendedList.get(caloriesExpendedList.size() - 1);
        assertThat(testCaloriesExpended.getUsuarioId()).isEqualTo(DEFAULT_USUARIO_ID);
        assertThat(testCaloriesExpended.getEmpresaId()).isEqualTo(DEFAULT_EMPRESA_ID);
        assertThat(testCaloriesExpended.getCalorias()).isEqualTo(DEFAULT_CALORIAS);
        assertThat(testCaloriesExpended.getStartTime()).isEqualTo(DEFAULT_START_TIME);
        assertThat(testCaloriesExpended.getEndTime()).isEqualTo(DEFAULT_END_TIME);
    }

    @Test
    @Transactional
    void createCaloriesExpendedWithExistingId() throws Exception {
        // Create the CaloriesExpended with an existing ID
        caloriesExpendedRepository.saveAndFlush(caloriesExpended);

        int databaseSizeBeforeCreate = caloriesExpendedRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCaloriesExpendedMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(caloriesExpended))
            )
            .andExpect(status().isBadRequest());

        // Validate the CaloriesExpended in the database
        List<CaloriesExpended> caloriesExpendedList = caloriesExpendedRepository.findAll();
        assertThat(caloriesExpendedList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllCaloriesExpendeds() throws Exception {
        // Initialize the database
        caloriesExpendedRepository.saveAndFlush(caloriesExpended);

        // Get all the caloriesExpendedList
        restCaloriesExpendedMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(caloriesExpended.getId().toString())))
            .andExpect(jsonPath("$.[*].usuarioId").value(hasItem(DEFAULT_USUARIO_ID)))
            .andExpect(jsonPath("$.[*].empresaId").value(hasItem(DEFAULT_EMPRESA_ID)))
            .andExpect(jsonPath("$.[*].calorias").value(hasItem(DEFAULT_CALORIAS.doubleValue())))
            .andExpect(jsonPath("$.[*].startTime").value(hasItem(DEFAULT_START_TIME.toString())))
            .andExpect(jsonPath("$.[*].endTime").value(hasItem(DEFAULT_END_TIME.toString())));
    }

    @Test
    @Transactional
    void getCaloriesExpended() throws Exception {
        // Initialize the database
        caloriesExpendedRepository.saveAndFlush(caloriesExpended);

        // Get the caloriesExpended
        restCaloriesExpendedMockMvc
            .perform(get(ENTITY_API_URL_ID, caloriesExpended.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(caloriesExpended.getId().toString()))
            .andExpect(jsonPath("$.usuarioId").value(DEFAULT_USUARIO_ID))
            .andExpect(jsonPath("$.empresaId").value(DEFAULT_EMPRESA_ID))
            .andExpect(jsonPath("$.calorias").value(DEFAULT_CALORIAS.doubleValue()))
            .andExpect(jsonPath("$.startTime").value(DEFAULT_START_TIME.toString()))
            .andExpect(jsonPath("$.endTime").value(DEFAULT_END_TIME.toString()));
    }

    @Test
    @Transactional
    void getCaloriesExpendedsByIdFiltering() throws Exception {
        // Initialize the database
        caloriesExpendedRepository.saveAndFlush(caloriesExpended);

        UUID id = caloriesExpended.getId();

        defaultCaloriesExpendedShouldBeFound("id.equals=" + id);
        defaultCaloriesExpendedShouldNotBeFound("id.notEquals=" + id);
    }

    @Test
    @Transactional
    void getAllCaloriesExpendedsByUsuarioIdIsEqualToSomething() throws Exception {
        // Initialize the database
        caloriesExpendedRepository.saveAndFlush(caloriesExpended);

        // Get all the caloriesExpendedList where usuarioId equals to DEFAULT_USUARIO_ID
        defaultCaloriesExpendedShouldBeFound("usuarioId.equals=" + DEFAULT_USUARIO_ID);

        // Get all the caloriesExpendedList where usuarioId equals to UPDATED_USUARIO_ID
        defaultCaloriesExpendedShouldNotBeFound("usuarioId.equals=" + UPDATED_USUARIO_ID);
    }

    @Test
    @Transactional
    void getAllCaloriesExpendedsByUsuarioIdIsInShouldWork() throws Exception {
        // Initialize the database
        caloriesExpendedRepository.saveAndFlush(caloriesExpended);

        // Get all the caloriesExpendedList where usuarioId in DEFAULT_USUARIO_ID or UPDATED_USUARIO_ID
        defaultCaloriesExpendedShouldBeFound("usuarioId.in=" + DEFAULT_USUARIO_ID + "," + UPDATED_USUARIO_ID);

        // Get all the caloriesExpendedList where usuarioId equals to UPDATED_USUARIO_ID
        defaultCaloriesExpendedShouldNotBeFound("usuarioId.in=" + UPDATED_USUARIO_ID);
    }

    @Test
    @Transactional
    void getAllCaloriesExpendedsByUsuarioIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        caloriesExpendedRepository.saveAndFlush(caloriesExpended);

        // Get all the caloriesExpendedList where usuarioId is not null
        defaultCaloriesExpendedShouldBeFound("usuarioId.specified=true");

        // Get all the caloriesExpendedList where usuarioId is null
        defaultCaloriesExpendedShouldNotBeFound("usuarioId.specified=false");
    }

    @Test
    @Transactional
    void getAllCaloriesExpendedsByUsuarioIdContainsSomething() throws Exception {
        // Initialize the database
        caloriesExpendedRepository.saveAndFlush(caloriesExpended);

        // Get all the caloriesExpendedList where usuarioId contains DEFAULT_USUARIO_ID
        defaultCaloriesExpendedShouldBeFound("usuarioId.contains=" + DEFAULT_USUARIO_ID);

        // Get all the caloriesExpendedList where usuarioId contains UPDATED_USUARIO_ID
        defaultCaloriesExpendedShouldNotBeFound("usuarioId.contains=" + UPDATED_USUARIO_ID);
    }

    @Test
    @Transactional
    void getAllCaloriesExpendedsByUsuarioIdNotContainsSomething() throws Exception {
        // Initialize the database
        caloriesExpendedRepository.saveAndFlush(caloriesExpended);

        // Get all the caloriesExpendedList where usuarioId does not contain DEFAULT_USUARIO_ID
        defaultCaloriesExpendedShouldNotBeFound("usuarioId.doesNotContain=" + DEFAULT_USUARIO_ID);

        // Get all the caloriesExpendedList where usuarioId does not contain UPDATED_USUARIO_ID
        defaultCaloriesExpendedShouldBeFound("usuarioId.doesNotContain=" + UPDATED_USUARIO_ID);
    }

    @Test
    @Transactional
    void getAllCaloriesExpendedsByEmpresaIdIsEqualToSomething() throws Exception {
        // Initialize the database
        caloriesExpendedRepository.saveAndFlush(caloriesExpended);

        // Get all the caloriesExpendedList where empresaId equals to DEFAULT_EMPRESA_ID
        defaultCaloriesExpendedShouldBeFound("empresaId.equals=" + DEFAULT_EMPRESA_ID);

        // Get all the caloriesExpendedList where empresaId equals to UPDATED_EMPRESA_ID
        defaultCaloriesExpendedShouldNotBeFound("empresaId.equals=" + UPDATED_EMPRESA_ID);
    }

    @Test
    @Transactional
    void getAllCaloriesExpendedsByEmpresaIdIsInShouldWork() throws Exception {
        // Initialize the database
        caloriesExpendedRepository.saveAndFlush(caloriesExpended);

        // Get all the caloriesExpendedList where empresaId in DEFAULT_EMPRESA_ID or UPDATED_EMPRESA_ID
        defaultCaloriesExpendedShouldBeFound("empresaId.in=" + DEFAULT_EMPRESA_ID + "," + UPDATED_EMPRESA_ID);

        // Get all the caloriesExpendedList where empresaId equals to UPDATED_EMPRESA_ID
        defaultCaloriesExpendedShouldNotBeFound("empresaId.in=" + UPDATED_EMPRESA_ID);
    }

    @Test
    @Transactional
    void getAllCaloriesExpendedsByEmpresaIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        caloriesExpendedRepository.saveAndFlush(caloriesExpended);

        // Get all the caloriesExpendedList where empresaId is not null
        defaultCaloriesExpendedShouldBeFound("empresaId.specified=true");

        // Get all the caloriesExpendedList where empresaId is null
        defaultCaloriesExpendedShouldNotBeFound("empresaId.specified=false");
    }

    @Test
    @Transactional
    void getAllCaloriesExpendedsByEmpresaIdContainsSomething() throws Exception {
        // Initialize the database
        caloriesExpendedRepository.saveAndFlush(caloriesExpended);

        // Get all the caloriesExpendedList where empresaId contains DEFAULT_EMPRESA_ID
        defaultCaloriesExpendedShouldBeFound("empresaId.contains=" + DEFAULT_EMPRESA_ID);

        // Get all the caloriesExpendedList where empresaId contains UPDATED_EMPRESA_ID
        defaultCaloriesExpendedShouldNotBeFound("empresaId.contains=" + UPDATED_EMPRESA_ID);
    }

    @Test
    @Transactional
    void getAllCaloriesExpendedsByEmpresaIdNotContainsSomething() throws Exception {
        // Initialize the database
        caloriesExpendedRepository.saveAndFlush(caloriesExpended);

        // Get all the caloriesExpendedList where empresaId does not contain DEFAULT_EMPRESA_ID
        defaultCaloriesExpendedShouldNotBeFound("empresaId.doesNotContain=" + DEFAULT_EMPRESA_ID);

        // Get all the caloriesExpendedList where empresaId does not contain UPDATED_EMPRESA_ID
        defaultCaloriesExpendedShouldBeFound("empresaId.doesNotContain=" + UPDATED_EMPRESA_ID);
    }

    @Test
    @Transactional
    void getAllCaloriesExpendedsByCaloriasIsEqualToSomething() throws Exception {
        // Initialize the database
        caloriesExpendedRepository.saveAndFlush(caloriesExpended);

        // Get all the caloriesExpendedList where calorias equals to DEFAULT_CALORIAS
        defaultCaloriesExpendedShouldBeFound("calorias.equals=" + DEFAULT_CALORIAS);

        // Get all the caloriesExpendedList where calorias equals to UPDATED_CALORIAS
        defaultCaloriesExpendedShouldNotBeFound("calorias.equals=" + UPDATED_CALORIAS);
    }

    @Test
    @Transactional
    void getAllCaloriesExpendedsByCaloriasIsInShouldWork() throws Exception {
        // Initialize the database
        caloriesExpendedRepository.saveAndFlush(caloriesExpended);

        // Get all the caloriesExpendedList where calorias in DEFAULT_CALORIAS or UPDATED_CALORIAS
        defaultCaloriesExpendedShouldBeFound("calorias.in=" + DEFAULT_CALORIAS + "," + UPDATED_CALORIAS);

        // Get all the caloriesExpendedList where calorias equals to UPDATED_CALORIAS
        defaultCaloriesExpendedShouldNotBeFound("calorias.in=" + UPDATED_CALORIAS);
    }

    @Test
    @Transactional
    void getAllCaloriesExpendedsByCaloriasIsNullOrNotNull() throws Exception {
        // Initialize the database
        caloriesExpendedRepository.saveAndFlush(caloriesExpended);

        // Get all the caloriesExpendedList where calorias is not null
        defaultCaloriesExpendedShouldBeFound("calorias.specified=true");

        // Get all the caloriesExpendedList where calorias is null
        defaultCaloriesExpendedShouldNotBeFound("calorias.specified=false");
    }

    @Test
    @Transactional
    void getAllCaloriesExpendedsByCaloriasIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        caloriesExpendedRepository.saveAndFlush(caloriesExpended);

        // Get all the caloriesExpendedList where calorias is greater than or equal to DEFAULT_CALORIAS
        defaultCaloriesExpendedShouldBeFound("calorias.greaterThanOrEqual=" + DEFAULT_CALORIAS);

        // Get all the caloriesExpendedList where calorias is greater than or equal to UPDATED_CALORIAS
        defaultCaloriesExpendedShouldNotBeFound("calorias.greaterThanOrEqual=" + UPDATED_CALORIAS);
    }

    @Test
    @Transactional
    void getAllCaloriesExpendedsByCaloriasIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        caloriesExpendedRepository.saveAndFlush(caloriesExpended);

        // Get all the caloriesExpendedList where calorias is less than or equal to DEFAULT_CALORIAS
        defaultCaloriesExpendedShouldBeFound("calorias.lessThanOrEqual=" + DEFAULT_CALORIAS);

        // Get all the caloriesExpendedList where calorias is less than or equal to SMALLER_CALORIAS
        defaultCaloriesExpendedShouldNotBeFound("calorias.lessThanOrEqual=" + SMALLER_CALORIAS);
    }

    @Test
    @Transactional
    void getAllCaloriesExpendedsByCaloriasIsLessThanSomething() throws Exception {
        // Initialize the database
        caloriesExpendedRepository.saveAndFlush(caloriesExpended);

        // Get all the caloriesExpendedList where calorias is less than DEFAULT_CALORIAS
        defaultCaloriesExpendedShouldNotBeFound("calorias.lessThan=" + DEFAULT_CALORIAS);

        // Get all the caloriesExpendedList where calorias is less than UPDATED_CALORIAS
        defaultCaloriesExpendedShouldBeFound("calorias.lessThan=" + UPDATED_CALORIAS);
    }

    @Test
    @Transactional
    void getAllCaloriesExpendedsByCaloriasIsGreaterThanSomething() throws Exception {
        // Initialize the database
        caloriesExpendedRepository.saveAndFlush(caloriesExpended);

        // Get all the caloriesExpendedList where calorias is greater than DEFAULT_CALORIAS
        defaultCaloriesExpendedShouldNotBeFound("calorias.greaterThan=" + DEFAULT_CALORIAS);

        // Get all the caloriesExpendedList where calorias is greater than SMALLER_CALORIAS
        defaultCaloriesExpendedShouldBeFound("calorias.greaterThan=" + SMALLER_CALORIAS);
    }

    @Test
    @Transactional
    void getAllCaloriesExpendedsByStartTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        caloriesExpendedRepository.saveAndFlush(caloriesExpended);

        // Get all the caloriesExpendedList where startTime equals to DEFAULT_START_TIME
        defaultCaloriesExpendedShouldBeFound("startTime.equals=" + DEFAULT_START_TIME);

        // Get all the caloriesExpendedList where startTime equals to UPDATED_START_TIME
        defaultCaloriesExpendedShouldNotBeFound("startTime.equals=" + UPDATED_START_TIME);
    }

    @Test
    @Transactional
    void getAllCaloriesExpendedsByStartTimeIsInShouldWork() throws Exception {
        // Initialize the database
        caloriesExpendedRepository.saveAndFlush(caloriesExpended);

        // Get all the caloriesExpendedList where startTime in DEFAULT_START_TIME or UPDATED_START_TIME
        defaultCaloriesExpendedShouldBeFound("startTime.in=" + DEFAULT_START_TIME + "," + UPDATED_START_TIME);

        // Get all the caloriesExpendedList where startTime equals to UPDATED_START_TIME
        defaultCaloriesExpendedShouldNotBeFound("startTime.in=" + UPDATED_START_TIME);
    }

    @Test
    @Transactional
    void getAllCaloriesExpendedsByStartTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        caloriesExpendedRepository.saveAndFlush(caloriesExpended);

        // Get all the caloriesExpendedList where startTime is not null
        defaultCaloriesExpendedShouldBeFound("startTime.specified=true");

        // Get all the caloriesExpendedList where startTime is null
        defaultCaloriesExpendedShouldNotBeFound("startTime.specified=false");
    }

    @Test
    @Transactional
    void getAllCaloriesExpendedsByEndTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        caloriesExpendedRepository.saveAndFlush(caloriesExpended);

        // Get all the caloriesExpendedList where endTime equals to DEFAULT_END_TIME
        defaultCaloriesExpendedShouldBeFound("endTime.equals=" + DEFAULT_END_TIME);

        // Get all the caloriesExpendedList where endTime equals to UPDATED_END_TIME
        defaultCaloriesExpendedShouldNotBeFound("endTime.equals=" + UPDATED_END_TIME);
    }

    @Test
    @Transactional
    void getAllCaloriesExpendedsByEndTimeIsInShouldWork() throws Exception {
        // Initialize the database
        caloriesExpendedRepository.saveAndFlush(caloriesExpended);

        // Get all the caloriesExpendedList where endTime in DEFAULT_END_TIME or UPDATED_END_TIME
        defaultCaloriesExpendedShouldBeFound("endTime.in=" + DEFAULT_END_TIME + "," + UPDATED_END_TIME);

        // Get all the caloriesExpendedList where endTime equals to UPDATED_END_TIME
        defaultCaloriesExpendedShouldNotBeFound("endTime.in=" + UPDATED_END_TIME);
    }

    @Test
    @Transactional
    void getAllCaloriesExpendedsByEndTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        caloriesExpendedRepository.saveAndFlush(caloriesExpended);

        // Get all the caloriesExpendedList where endTime is not null
        defaultCaloriesExpendedShouldBeFound("endTime.specified=true");

        // Get all the caloriesExpendedList where endTime is null
        defaultCaloriesExpendedShouldNotBeFound("endTime.specified=false");
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCaloriesExpendedShouldBeFound(String filter) throws Exception {
        restCaloriesExpendedMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(caloriesExpended.getId().toString())))
            .andExpect(jsonPath("$.[*].usuarioId").value(hasItem(DEFAULT_USUARIO_ID)))
            .andExpect(jsonPath("$.[*].empresaId").value(hasItem(DEFAULT_EMPRESA_ID)))
            .andExpect(jsonPath("$.[*].calorias").value(hasItem(DEFAULT_CALORIAS.doubleValue())))
            .andExpect(jsonPath("$.[*].startTime").value(hasItem(DEFAULT_START_TIME.toString())))
            .andExpect(jsonPath("$.[*].endTime").value(hasItem(DEFAULT_END_TIME.toString())));

        // Check, that the count call also returns 1
        restCaloriesExpendedMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCaloriesExpendedShouldNotBeFound(String filter) throws Exception {
        restCaloriesExpendedMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCaloriesExpendedMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingCaloriesExpended() throws Exception {
        // Get the caloriesExpended
        restCaloriesExpendedMockMvc.perform(get(ENTITY_API_URL_ID, UUID.randomUUID().toString())).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingCaloriesExpended() throws Exception {
        // Initialize the database
        caloriesExpendedRepository.saveAndFlush(caloriesExpended);

        int databaseSizeBeforeUpdate = caloriesExpendedRepository.findAll().size();

        // Update the caloriesExpended
        CaloriesExpended updatedCaloriesExpended = caloriesExpendedRepository.findById(caloriesExpended.getId()).get();
        // Disconnect from session so that the updates on updatedCaloriesExpended are not directly saved in db
        em.detach(updatedCaloriesExpended);
        updatedCaloriesExpended
            .usuarioId(UPDATED_USUARIO_ID)
            .empresaId(UPDATED_EMPRESA_ID)
            .calorias(UPDATED_CALORIAS)
            .startTime(UPDATED_START_TIME)
            .endTime(UPDATED_END_TIME);

        restCaloriesExpendedMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedCaloriesExpended.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedCaloriesExpended))
            )
            .andExpect(status().isOk());

        // Validate the CaloriesExpended in the database
        List<CaloriesExpended> caloriesExpendedList = caloriesExpendedRepository.findAll();
        assertThat(caloriesExpendedList).hasSize(databaseSizeBeforeUpdate);
        CaloriesExpended testCaloriesExpended = caloriesExpendedList.get(caloriesExpendedList.size() - 1);
        assertThat(testCaloriesExpended.getUsuarioId()).isEqualTo(UPDATED_USUARIO_ID);
        assertThat(testCaloriesExpended.getEmpresaId()).isEqualTo(UPDATED_EMPRESA_ID);
        assertThat(testCaloriesExpended.getCalorias()).isEqualTo(UPDATED_CALORIAS);
        assertThat(testCaloriesExpended.getStartTime()).isEqualTo(UPDATED_START_TIME);
        assertThat(testCaloriesExpended.getEndTime()).isEqualTo(UPDATED_END_TIME);
    }

    @Test
    @Transactional
    void putNonExistingCaloriesExpended() throws Exception {
        int databaseSizeBeforeUpdate = caloriesExpendedRepository.findAll().size();
        caloriesExpended.setId(UUID.randomUUID());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCaloriesExpendedMockMvc
            .perform(
                put(ENTITY_API_URL_ID, caloriesExpended.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(caloriesExpended))
            )
            .andExpect(status().isBadRequest());

        // Validate the CaloriesExpended in the database
        List<CaloriesExpended> caloriesExpendedList = caloriesExpendedRepository.findAll();
        assertThat(caloriesExpendedList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCaloriesExpended() throws Exception {
        int databaseSizeBeforeUpdate = caloriesExpendedRepository.findAll().size();
        caloriesExpended.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCaloriesExpendedMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(caloriesExpended))
            )
            .andExpect(status().isBadRequest());

        // Validate the CaloriesExpended in the database
        List<CaloriesExpended> caloriesExpendedList = caloriesExpendedRepository.findAll();
        assertThat(caloriesExpendedList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCaloriesExpended() throws Exception {
        int databaseSizeBeforeUpdate = caloriesExpendedRepository.findAll().size();
        caloriesExpended.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCaloriesExpendedMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(caloriesExpended))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CaloriesExpended in the database
        List<CaloriesExpended> caloriesExpendedList = caloriesExpendedRepository.findAll();
        assertThat(caloriesExpendedList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCaloriesExpendedWithPatch() throws Exception {
        // Initialize the database
        caloriesExpendedRepository.saveAndFlush(caloriesExpended);

        int databaseSizeBeforeUpdate = caloriesExpendedRepository.findAll().size();

        // Update the caloriesExpended using partial update
        CaloriesExpended partialUpdatedCaloriesExpended = new CaloriesExpended();
        partialUpdatedCaloriesExpended.setId(caloriesExpended.getId());

        partialUpdatedCaloriesExpended.calorias(UPDATED_CALORIAS).startTime(UPDATED_START_TIME);

        restCaloriesExpendedMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCaloriesExpended.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCaloriesExpended))
            )
            .andExpect(status().isOk());

        // Validate the CaloriesExpended in the database
        List<CaloriesExpended> caloriesExpendedList = caloriesExpendedRepository.findAll();
        assertThat(caloriesExpendedList).hasSize(databaseSizeBeforeUpdate);
        CaloriesExpended testCaloriesExpended = caloriesExpendedList.get(caloriesExpendedList.size() - 1);
        assertThat(testCaloriesExpended.getUsuarioId()).isEqualTo(DEFAULT_USUARIO_ID);
        assertThat(testCaloriesExpended.getEmpresaId()).isEqualTo(DEFAULT_EMPRESA_ID);
        assertThat(testCaloriesExpended.getCalorias()).isEqualTo(UPDATED_CALORIAS);
        assertThat(testCaloriesExpended.getStartTime()).isEqualTo(UPDATED_START_TIME);
        assertThat(testCaloriesExpended.getEndTime()).isEqualTo(DEFAULT_END_TIME);
    }

    @Test
    @Transactional
    void fullUpdateCaloriesExpendedWithPatch() throws Exception {
        // Initialize the database
        caloriesExpendedRepository.saveAndFlush(caloriesExpended);

        int databaseSizeBeforeUpdate = caloriesExpendedRepository.findAll().size();

        // Update the caloriesExpended using partial update
        CaloriesExpended partialUpdatedCaloriesExpended = new CaloriesExpended();
        partialUpdatedCaloriesExpended.setId(caloriesExpended.getId());

        partialUpdatedCaloriesExpended
            .usuarioId(UPDATED_USUARIO_ID)
            .empresaId(UPDATED_EMPRESA_ID)
            .calorias(UPDATED_CALORIAS)
            .startTime(UPDATED_START_TIME)
            .endTime(UPDATED_END_TIME);

        restCaloriesExpendedMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCaloriesExpended.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCaloriesExpended))
            )
            .andExpect(status().isOk());

        // Validate the CaloriesExpended in the database
        List<CaloriesExpended> caloriesExpendedList = caloriesExpendedRepository.findAll();
        assertThat(caloriesExpendedList).hasSize(databaseSizeBeforeUpdate);
        CaloriesExpended testCaloriesExpended = caloriesExpendedList.get(caloriesExpendedList.size() - 1);
        assertThat(testCaloriesExpended.getUsuarioId()).isEqualTo(UPDATED_USUARIO_ID);
        assertThat(testCaloriesExpended.getEmpresaId()).isEqualTo(UPDATED_EMPRESA_ID);
        assertThat(testCaloriesExpended.getCalorias()).isEqualTo(UPDATED_CALORIAS);
        assertThat(testCaloriesExpended.getStartTime()).isEqualTo(UPDATED_START_TIME);
        assertThat(testCaloriesExpended.getEndTime()).isEqualTo(UPDATED_END_TIME);
    }

    @Test
    @Transactional
    void patchNonExistingCaloriesExpended() throws Exception {
        int databaseSizeBeforeUpdate = caloriesExpendedRepository.findAll().size();
        caloriesExpended.setId(UUID.randomUUID());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCaloriesExpendedMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, caloriesExpended.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(caloriesExpended))
            )
            .andExpect(status().isBadRequest());

        // Validate the CaloriesExpended in the database
        List<CaloriesExpended> caloriesExpendedList = caloriesExpendedRepository.findAll();
        assertThat(caloriesExpendedList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCaloriesExpended() throws Exception {
        int databaseSizeBeforeUpdate = caloriesExpendedRepository.findAll().size();
        caloriesExpended.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCaloriesExpendedMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(caloriesExpended))
            )
            .andExpect(status().isBadRequest());

        // Validate the CaloriesExpended in the database
        List<CaloriesExpended> caloriesExpendedList = caloriesExpendedRepository.findAll();
        assertThat(caloriesExpendedList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCaloriesExpended() throws Exception {
        int databaseSizeBeforeUpdate = caloriesExpendedRepository.findAll().size();
        caloriesExpended.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCaloriesExpendedMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(caloriesExpended))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CaloriesExpended in the database
        List<CaloriesExpended> caloriesExpendedList = caloriesExpendedRepository.findAll();
        assertThat(caloriesExpendedList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCaloriesExpended() throws Exception {
        // Initialize the database
        caloriesExpendedRepository.saveAndFlush(caloriesExpended);

        int databaseSizeBeforeDelete = caloriesExpendedRepository.findAll().size();

        // Delete the caloriesExpended
        restCaloriesExpendedMockMvc
            .perform(delete(ENTITY_API_URL_ID, caloriesExpended.getId().toString()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CaloriesExpended> caloriesExpendedList = caloriesExpendedRepository.findAll();
        assertThat(caloriesExpendedList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
