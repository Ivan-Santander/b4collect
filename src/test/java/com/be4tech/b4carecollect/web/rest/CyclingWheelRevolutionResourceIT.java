package com.be4tech.b4carecollect.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.be4tech.b4carecollect.IntegrationTest;
import com.be4tech.b4carecollect.domain.CyclingWheelRevolution;
import com.be4tech.b4carecollect.repository.CyclingWheelRevolutionRepository;
import com.be4tech.b4carecollect.service.criteria.CyclingWheelRevolutionCriteria;
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
 * Integration tests for the {@link CyclingWheelRevolutionResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CyclingWheelRevolutionResourceIT {

    private static final String DEFAULT_USUARIO_ID = "AAAAAAAAAA";
    private static final String UPDATED_USUARIO_ID = "BBBBBBBBBB";

    private static final String DEFAULT_EMPRESA_ID = "AAAAAAAAAA";
    private static final String UPDATED_EMPRESA_ID = "BBBBBBBBBB";

    private static final Integer DEFAULT_REVOLUTIONS = 1;
    private static final Integer UPDATED_REVOLUTIONS = 2;
    private static final Integer SMALLER_REVOLUTIONS = 1 - 1;

    private static final Instant DEFAULT_START_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_START_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_END_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_END_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/cycling-wheel-revolutions";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private CyclingWheelRevolutionRepository cyclingWheelRevolutionRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCyclingWheelRevolutionMockMvc;

    private CyclingWheelRevolution cyclingWheelRevolution;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CyclingWheelRevolution createEntity(EntityManager em) {
        CyclingWheelRevolution cyclingWheelRevolution = new CyclingWheelRevolution()
            .usuarioId(DEFAULT_USUARIO_ID)
            .empresaId(DEFAULT_EMPRESA_ID)
            .revolutions(DEFAULT_REVOLUTIONS)
            .startTime(DEFAULT_START_TIME)
            .endTime(DEFAULT_END_TIME);
        return cyclingWheelRevolution;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CyclingWheelRevolution createUpdatedEntity(EntityManager em) {
        CyclingWheelRevolution cyclingWheelRevolution = new CyclingWheelRevolution()
            .usuarioId(UPDATED_USUARIO_ID)
            .empresaId(UPDATED_EMPRESA_ID)
            .revolutions(UPDATED_REVOLUTIONS)
            .startTime(UPDATED_START_TIME)
            .endTime(UPDATED_END_TIME);
        return cyclingWheelRevolution;
    }

    @BeforeEach
    public void initTest() {
        cyclingWheelRevolution = createEntity(em);
    }

    @Test
    @Transactional
    void createCyclingWheelRevolution() throws Exception {
        int databaseSizeBeforeCreate = cyclingWheelRevolutionRepository.findAll().size();
        // Create the CyclingWheelRevolution
        restCyclingWheelRevolutionMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(cyclingWheelRevolution))
            )
            .andExpect(status().isCreated());

        // Validate the CyclingWheelRevolution in the database
        List<CyclingWheelRevolution> cyclingWheelRevolutionList = cyclingWheelRevolutionRepository.findAll();
        assertThat(cyclingWheelRevolutionList).hasSize(databaseSizeBeforeCreate + 1);
        CyclingWheelRevolution testCyclingWheelRevolution = cyclingWheelRevolutionList.get(cyclingWheelRevolutionList.size() - 1);
        assertThat(testCyclingWheelRevolution.getUsuarioId()).isEqualTo(DEFAULT_USUARIO_ID);
        assertThat(testCyclingWheelRevolution.getEmpresaId()).isEqualTo(DEFAULT_EMPRESA_ID);
        assertThat(testCyclingWheelRevolution.getRevolutions()).isEqualTo(DEFAULT_REVOLUTIONS);
        assertThat(testCyclingWheelRevolution.getStartTime()).isEqualTo(DEFAULT_START_TIME);
        assertThat(testCyclingWheelRevolution.getEndTime()).isEqualTo(DEFAULT_END_TIME);
    }

    @Test
    @Transactional
    void createCyclingWheelRevolutionWithExistingId() throws Exception {
        // Create the CyclingWheelRevolution with an existing ID
        cyclingWheelRevolutionRepository.saveAndFlush(cyclingWheelRevolution);

        int databaseSizeBeforeCreate = cyclingWheelRevolutionRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCyclingWheelRevolutionMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(cyclingWheelRevolution))
            )
            .andExpect(status().isBadRequest());

        // Validate the CyclingWheelRevolution in the database
        List<CyclingWheelRevolution> cyclingWheelRevolutionList = cyclingWheelRevolutionRepository.findAll();
        assertThat(cyclingWheelRevolutionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllCyclingWheelRevolutions() throws Exception {
        // Initialize the database
        cyclingWheelRevolutionRepository.saveAndFlush(cyclingWheelRevolution);

        // Get all the cyclingWheelRevolutionList
        restCyclingWheelRevolutionMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cyclingWheelRevolution.getId().toString())))
            .andExpect(jsonPath("$.[*].usuarioId").value(hasItem(DEFAULT_USUARIO_ID)))
            .andExpect(jsonPath("$.[*].empresaId").value(hasItem(DEFAULT_EMPRESA_ID)))
            .andExpect(jsonPath("$.[*].revolutions").value(hasItem(DEFAULT_REVOLUTIONS)))
            .andExpect(jsonPath("$.[*].startTime").value(hasItem(DEFAULT_START_TIME.toString())))
            .andExpect(jsonPath("$.[*].endTime").value(hasItem(DEFAULT_END_TIME.toString())));
    }

    @Test
    @Transactional
    void getCyclingWheelRevolution() throws Exception {
        // Initialize the database
        cyclingWheelRevolutionRepository.saveAndFlush(cyclingWheelRevolution);

        // Get the cyclingWheelRevolution
        restCyclingWheelRevolutionMockMvc
            .perform(get(ENTITY_API_URL_ID, cyclingWheelRevolution.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(cyclingWheelRevolution.getId().toString()))
            .andExpect(jsonPath("$.usuarioId").value(DEFAULT_USUARIO_ID))
            .andExpect(jsonPath("$.empresaId").value(DEFAULT_EMPRESA_ID))
            .andExpect(jsonPath("$.revolutions").value(DEFAULT_REVOLUTIONS))
            .andExpect(jsonPath("$.startTime").value(DEFAULT_START_TIME.toString()))
            .andExpect(jsonPath("$.endTime").value(DEFAULT_END_TIME.toString()));
    }

    @Test
    @Transactional
    void getCyclingWheelRevolutionsByIdFiltering() throws Exception {
        // Initialize the database
        cyclingWheelRevolutionRepository.saveAndFlush(cyclingWheelRevolution);

        UUID id = cyclingWheelRevolution.getId();

        defaultCyclingWheelRevolutionShouldBeFound("id.equals=" + id);
        defaultCyclingWheelRevolutionShouldNotBeFound("id.notEquals=" + id);
    }

    @Test
    @Transactional
    void getAllCyclingWheelRevolutionsByUsuarioIdIsEqualToSomething() throws Exception {
        // Initialize the database
        cyclingWheelRevolutionRepository.saveAndFlush(cyclingWheelRevolution);

        // Get all the cyclingWheelRevolutionList where usuarioId equals to DEFAULT_USUARIO_ID
        defaultCyclingWheelRevolutionShouldBeFound("usuarioId.equals=" + DEFAULT_USUARIO_ID);

        // Get all the cyclingWheelRevolutionList where usuarioId equals to UPDATED_USUARIO_ID
        defaultCyclingWheelRevolutionShouldNotBeFound("usuarioId.equals=" + UPDATED_USUARIO_ID);
    }

    @Test
    @Transactional
    void getAllCyclingWheelRevolutionsByUsuarioIdIsInShouldWork() throws Exception {
        // Initialize the database
        cyclingWheelRevolutionRepository.saveAndFlush(cyclingWheelRevolution);

        // Get all the cyclingWheelRevolutionList where usuarioId in DEFAULT_USUARIO_ID or UPDATED_USUARIO_ID
        defaultCyclingWheelRevolutionShouldBeFound("usuarioId.in=" + DEFAULT_USUARIO_ID + "," + UPDATED_USUARIO_ID);

        // Get all the cyclingWheelRevolutionList where usuarioId equals to UPDATED_USUARIO_ID
        defaultCyclingWheelRevolutionShouldNotBeFound("usuarioId.in=" + UPDATED_USUARIO_ID);
    }

    @Test
    @Transactional
    void getAllCyclingWheelRevolutionsByUsuarioIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        cyclingWheelRevolutionRepository.saveAndFlush(cyclingWheelRevolution);

        // Get all the cyclingWheelRevolutionList where usuarioId is not null
        defaultCyclingWheelRevolutionShouldBeFound("usuarioId.specified=true");

        // Get all the cyclingWheelRevolutionList where usuarioId is null
        defaultCyclingWheelRevolutionShouldNotBeFound("usuarioId.specified=false");
    }

    @Test
    @Transactional
    void getAllCyclingWheelRevolutionsByUsuarioIdContainsSomething() throws Exception {
        // Initialize the database
        cyclingWheelRevolutionRepository.saveAndFlush(cyclingWheelRevolution);

        // Get all the cyclingWheelRevolutionList where usuarioId contains DEFAULT_USUARIO_ID
        defaultCyclingWheelRevolutionShouldBeFound("usuarioId.contains=" + DEFAULT_USUARIO_ID);

        // Get all the cyclingWheelRevolutionList where usuarioId contains UPDATED_USUARIO_ID
        defaultCyclingWheelRevolutionShouldNotBeFound("usuarioId.contains=" + UPDATED_USUARIO_ID);
    }

    @Test
    @Transactional
    void getAllCyclingWheelRevolutionsByUsuarioIdNotContainsSomething() throws Exception {
        // Initialize the database
        cyclingWheelRevolutionRepository.saveAndFlush(cyclingWheelRevolution);

        // Get all the cyclingWheelRevolutionList where usuarioId does not contain DEFAULT_USUARIO_ID
        defaultCyclingWheelRevolutionShouldNotBeFound("usuarioId.doesNotContain=" + DEFAULT_USUARIO_ID);

        // Get all the cyclingWheelRevolutionList where usuarioId does not contain UPDATED_USUARIO_ID
        defaultCyclingWheelRevolutionShouldBeFound("usuarioId.doesNotContain=" + UPDATED_USUARIO_ID);
    }

    @Test
    @Transactional
    void getAllCyclingWheelRevolutionsByEmpresaIdIsEqualToSomething() throws Exception {
        // Initialize the database
        cyclingWheelRevolutionRepository.saveAndFlush(cyclingWheelRevolution);

        // Get all the cyclingWheelRevolutionList where empresaId equals to DEFAULT_EMPRESA_ID
        defaultCyclingWheelRevolutionShouldBeFound("empresaId.equals=" + DEFAULT_EMPRESA_ID);

        // Get all the cyclingWheelRevolutionList where empresaId equals to UPDATED_EMPRESA_ID
        defaultCyclingWheelRevolutionShouldNotBeFound("empresaId.equals=" + UPDATED_EMPRESA_ID);
    }

    @Test
    @Transactional
    void getAllCyclingWheelRevolutionsByEmpresaIdIsInShouldWork() throws Exception {
        // Initialize the database
        cyclingWheelRevolutionRepository.saveAndFlush(cyclingWheelRevolution);

        // Get all the cyclingWheelRevolutionList where empresaId in DEFAULT_EMPRESA_ID or UPDATED_EMPRESA_ID
        defaultCyclingWheelRevolutionShouldBeFound("empresaId.in=" + DEFAULT_EMPRESA_ID + "," + UPDATED_EMPRESA_ID);

        // Get all the cyclingWheelRevolutionList where empresaId equals to UPDATED_EMPRESA_ID
        defaultCyclingWheelRevolutionShouldNotBeFound("empresaId.in=" + UPDATED_EMPRESA_ID);
    }

    @Test
    @Transactional
    void getAllCyclingWheelRevolutionsByEmpresaIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        cyclingWheelRevolutionRepository.saveAndFlush(cyclingWheelRevolution);

        // Get all the cyclingWheelRevolutionList where empresaId is not null
        defaultCyclingWheelRevolutionShouldBeFound("empresaId.specified=true");

        // Get all the cyclingWheelRevolutionList where empresaId is null
        defaultCyclingWheelRevolutionShouldNotBeFound("empresaId.specified=false");
    }

    @Test
    @Transactional
    void getAllCyclingWheelRevolutionsByEmpresaIdContainsSomething() throws Exception {
        // Initialize the database
        cyclingWheelRevolutionRepository.saveAndFlush(cyclingWheelRevolution);

        // Get all the cyclingWheelRevolutionList where empresaId contains DEFAULT_EMPRESA_ID
        defaultCyclingWheelRevolutionShouldBeFound("empresaId.contains=" + DEFAULT_EMPRESA_ID);

        // Get all the cyclingWheelRevolutionList where empresaId contains UPDATED_EMPRESA_ID
        defaultCyclingWheelRevolutionShouldNotBeFound("empresaId.contains=" + UPDATED_EMPRESA_ID);
    }

    @Test
    @Transactional
    void getAllCyclingWheelRevolutionsByEmpresaIdNotContainsSomething() throws Exception {
        // Initialize the database
        cyclingWheelRevolutionRepository.saveAndFlush(cyclingWheelRevolution);

        // Get all the cyclingWheelRevolutionList where empresaId does not contain DEFAULT_EMPRESA_ID
        defaultCyclingWheelRevolutionShouldNotBeFound("empresaId.doesNotContain=" + DEFAULT_EMPRESA_ID);

        // Get all the cyclingWheelRevolutionList where empresaId does not contain UPDATED_EMPRESA_ID
        defaultCyclingWheelRevolutionShouldBeFound("empresaId.doesNotContain=" + UPDATED_EMPRESA_ID);
    }

    @Test
    @Transactional
    void getAllCyclingWheelRevolutionsByRevolutionsIsEqualToSomething() throws Exception {
        // Initialize the database
        cyclingWheelRevolutionRepository.saveAndFlush(cyclingWheelRevolution);

        // Get all the cyclingWheelRevolutionList where revolutions equals to DEFAULT_REVOLUTIONS
        defaultCyclingWheelRevolutionShouldBeFound("revolutions.equals=" + DEFAULT_REVOLUTIONS);

        // Get all the cyclingWheelRevolutionList where revolutions equals to UPDATED_REVOLUTIONS
        defaultCyclingWheelRevolutionShouldNotBeFound("revolutions.equals=" + UPDATED_REVOLUTIONS);
    }

    @Test
    @Transactional
    void getAllCyclingWheelRevolutionsByRevolutionsIsInShouldWork() throws Exception {
        // Initialize the database
        cyclingWheelRevolutionRepository.saveAndFlush(cyclingWheelRevolution);

        // Get all the cyclingWheelRevolutionList where revolutions in DEFAULT_REVOLUTIONS or UPDATED_REVOLUTIONS
        defaultCyclingWheelRevolutionShouldBeFound("revolutions.in=" + DEFAULT_REVOLUTIONS + "," + UPDATED_REVOLUTIONS);

        // Get all the cyclingWheelRevolutionList where revolutions equals to UPDATED_REVOLUTIONS
        defaultCyclingWheelRevolutionShouldNotBeFound("revolutions.in=" + UPDATED_REVOLUTIONS);
    }

    @Test
    @Transactional
    void getAllCyclingWheelRevolutionsByRevolutionsIsNullOrNotNull() throws Exception {
        // Initialize the database
        cyclingWheelRevolutionRepository.saveAndFlush(cyclingWheelRevolution);

        // Get all the cyclingWheelRevolutionList where revolutions is not null
        defaultCyclingWheelRevolutionShouldBeFound("revolutions.specified=true");

        // Get all the cyclingWheelRevolutionList where revolutions is null
        defaultCyclingWheelRevolutionShouldNotBeFound("revolutions.specified=false");
    }

    @Test
    @Transactional
    void getAllCyclingWheelRevolutionsByRevolutionsIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        cyclingWheelRevolutionRepository.saveAndFlush(cyclingWheelRevolution);

        // Get all the cyclingWheelRevolutionList where revolutions is greater than or equal to DEFAULT_REVOLUTIONS
        defaultCyclingWheelRevolutionShouldBeFound("revolutions.greaterThanOrEqual=" + DEFAULT_REVOLUTIONS);

        // Get all the cyclingWheelRevolutionList where revolutions is greater than or equal to UPDATED_REVOLUTIONS
        defaultCyclingWheelRevolutionShouldNotBeFound("revolutions.greaterThanOrEqual=" + UPDATED_REVOLUTIONS);
    }

    @Test
    @Transactional
    void getAllCyclingWheelRevolutionsByRevolutionsIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        cyclingWheelRevolutionRepository.saveAndFlush(cyclingWheelRevolution);

        // Get all the cyclingWheelRevolutionList where revolutions is less than or equal to DEFAULT_REVOLUTIONS
        defaultCyclingWheelRevolutionShouldBeFound("revolutions.lessThanOrEqual=" + DEFAULT_REVOLUTIONS);

        // Get all the cyclingWheelRevolutionList where revolutions is less than or equal to SMALLER_REVOLUTIONS
        defaultCyclingWheelRevolutionShouldNotBeFound("revolutions.lessThanOrEqual=" + SMALLER_REVOLUTIONS);
    }

    @Test
    @Transactional
    void getAllCyclingWheelRevolutionsByRevolutionsIsLessThanSomething() throws Exception {
        // Initialize the database
        cyclingWheelRevolutionRepository.saveAndFlush(cyclingWheelRevolution);

        // Get all the cyclingWheelRevolutionList where revolutions is less than DEFAULT_REVOLUTIONS
        defaultCyclingWheelRevolutionShouldNotBeFound("revolutions.lessThan=" + DEFAULT_REVOLUTIONS);

        // Get all the cyclingWheelRevolutionList where revolutions is less than UPDATED_REVOLUTIONS
        defaultCyclingWheelRevolutionShouldBeFound("revolutions.lessThan=" + UPDATED_REVOLUTIONS);
    }

    @Test
    @Transactional
    void getAllCyclingWheelRevolutionsByRevolutionsIsGreaterThanSomething() throws Exception {
        // Initialize the database
        cyclingWheelRevolutionRepository.saveAndFlush(cyclingWheelRevolution);

        // Get all the cyclingWheelRevolutionList where revolutions is greater than DEFAULT_REVOLUTIONS
        defaultCyclingWheelRevolutionShouldNotBeFound("revolutions.greaterThan=" + DEFAULT_REVOLUTIONS);

        // Get all the cyclingWheelRevolutionList where revolutions is greater than SMALLER_REVOLUTIONS
        defaultCyclingWheelRevolutionShouldBeFound("revolutions.greaterThan=" + SMALLER_REVOLUTIONS);
    }

    @Test
    @Transactional
    void getAllCyclingWheelRevolutionsByStartTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        cyclingWheelRevolutionRepository.saveAndFlush(cyclingWheelRevolution);

        // Get all the cyclingWheelRevolutionList where startTime equals to DEFAULT_START_TIME
        defaultCyclingWheelRevolutionShouldBeFound("startTime.equals=" + DEFAULT_START_TIME);

        // Get all the cyclingWheelRevolutionList where startTime equals to UPDATED_START_TIME
        defaultCyclingWheelRevolutionShouldNotBeFound("startTime.equals=" + UPDATED_START_TIME);
    }

    @Test
    @Transactional
    void getAllCyclingWheelRevolutionsByStartTimeIsInShouldWork() throws Exception {
        // Initialize the database
        cyclingWheelRevolutionRepository.saveAndFlush(cyclingWheelRevolution);

        // Get all the cyclingWheelRevolutionList where startTime in DEFAULT_START_TIME or UPDATED_START_TIME
        defaultCyclingWheelRevolutionShouldBeFound("startTime.in=" + DEFAULT_START_TIME + "," + UPDATED_START_TIME);

        // Get all the cyclingWheelRevolutionList where startTime equals to UPDATED_START_TIME
        defaultCyclingWheelRevolutionShouldNotBeFound("startTime.in=" + UPDATED_START_TIME);
    }

    @Test
    @Transactional
    void getAllCyclingWheelRevolutionsByStartTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        cyclingWheelRevolutionRepository.saveAndFlush(cyclingWheelRevolution);

        // Get all the cyclingWheelRevolutionList where startTime is not null
        defaultCyclingWheelRevolutionShouldBeFound("startTime.specified=true");

        // Get all the cyclingWheelRevolutionList where startTime is null
        defaultCyclingWheelRevolutionShouldNotBeFound("startTime.specified=false");
    }

    @Test
    @Transactional
    void getAllCyclingWheelRevolutionsByEndTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        cyclingWheelRevolutionRepository.saveAndFlush(cyclingWheelRevolution);

        // Get all the cyclingWheelRevolutionList where endTime equals to DEFAULT_END_TIME
        defaultCyclingWheelRevolutionShouldBeFound("endTime.equals=" + DEFAULT_END_TIME);

        // Get all the cyclingWheelRevolutionList where endTime equals to UPDATED_END_TIME
        defaultCyclingWheelRevolutionShouldNotBeFound("endTime.equals=" + UPDATED_END_TIME);
    }

    @Test
    @Transactional
    void getAllCyclingWheelRevolutionsByEndTimeIsInShouldWork() throws Exception {
        // Initialize the database
        cyclingWheelRevolutionRepository.saveAndFlush(cyclingWheelRevolution);

        // Get all the cyclingWheelRevolutionList where endTime in DEFAULT_END_TIME or UPDATED_END_TIME
        defaultCyclingWheelRevolutionShouldBeFound("endTime.in=" + DEFAULT_END_TIME + "," + UPDATED_END_TIME);

        // Get all the cyclingWheelRevolutionList where endTime equals to UPDATED_END_TIME
        defaultCyclingWheelRevolutionShouldNotBeFound("endTime.in=" + UPDATED_END_TIME);
    }

    @Test
    @Transactional
    void getAllCyclingWheelRevolutionsByEndTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        cyclingWheelRevolutionRepository.saveAndFlush(cyclingWheelRevolution);

        // Get all the cyclingWheelRevolutionList where endTime is not null
        defaultCyclingWheelRevolutionShouldBeFound("endTime.specified=true");

        // Get all the cyclingWheelRevolutionList where endTime is null
        defaultCyclingWheelRevolutionShouldNotBeFound("endTime.specified=false");
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCyclingWheelRevolutionShouldBeFound(String filter) throws Exception {
        restCyclingWheelRevolutionMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cyclingWheelRevolution.getId().toString())))
            .andExpect(jsonPath("$.[*].usuarioId").value(hasItem(DEFAULT_USUARIO_ID)))
            .andExpect(jsonPath("$.[*].empresaId").value(hasItem(DEFAULT_EMPRESA_ID)))
            .andExpect(jsonPath("$.[*].revolutions").value(hasItem(DEFAULT_REVOLUTIONS)))
            .andExpect(jsonPath("$.[*].startTime").value(hasItem(DEFAULT_START_TIME.toString())))
            .andExpect(jsonPath("$.[*].endTime").value(hasItem(DEFAULT_END_TIME.toString())));

        // Check, that the count call also returns 1
        restCyclingWheelRevolutionMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCyclingWheelRevolutionShouldNotBeFound(String filter) throws Exception {
        restCyclingWheelRevolutionMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCyclingWheelRevolutionMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingCyclingWheelRevolution() throws Exception {
        // Get the cyclingWheelRevolution
        restCyclingWheelRevolutionMockMvc.perform(get(ENTITY_API_URL_ID, UUID.randomUUID().toString())).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingCyclingWheelRevolution() throws Exception {
        // Initialize the database
        cyclingWheelRevolutionRepository.saveAndFlush(cyclingWheelRevolution);

        int databaseSizeBeforeUpdate = cyclingWheelRevolutionRepository.findAll().size();

        // Update the cyclingWheelRevolution
        CyclingWheelRevolution updatedCyclingWheelRevolution = cyclingWheelRevolutionRepository
            .findById(cyclingWheelRevolution.getId())
            .get();
        // Disconnect from session so that the updates on updatedCyclingWheelRevolution are not directly saved in db
        em.detach(updatedCyclingWheelRevolution);
        updatedCyclingWheelRevolution
            .usuarioId(UPDATED_USUARIO_ID)
            .empresaId(UPDATED_EMPRESA_ID)
            .revolutions(UPDATED_REVOLUTIONS)
            .startTime(UPDATED_START_TIME)
            .endTime(UPDATED_END_TIME);

        restCyclingWheelRevolutionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedCyclingWheelRevolution.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedCyclingWheelRevolution))
            )
            .andExpect(status().isOk());

        // Validate the CyclingWheelRevolution in the database
        List<CyclingWheelRevolution> cyclingWheelRevolutionList = cyclingWheelRevolutionRepository.findAll();
        assertThat(cyclingWheelRevolutionList).hasSize(databaseSizeBeforeUpdate);
        CyclingWheelRevolution testCyclingWheelRevolution = cyclingWheelRevolutionList.get(cyclingWheelRevolutionList.size() - 1);
        assertThat(testCyclingWheelRevolution.getUsuarioId()).isEqualTo(UPDATED_USUARIO_ID);
        assertThat(testCyclingWheelRevolution.getEmpresaId()).isEqualTo(UPDATED_EMPRESA_ID);
        assertThat(testCyclingWheelRevolution.getRevolutions()).isEqualTo(UPDATED_REVOLUTIONS);
        assertThat(testCyclingWheelRevolution.getStartTime()).isEqualTo(UPDATED_START_TIME);
        assertThat(testCyclingWheelRevolution.getEndTime()).isEqualTo(UPDATED_END_TIME);
    }

    @Test
    @Transactional
    void putNonExistingCyclingWheelRevolution() throws Exception {
        int databaseSizeBeforeUpdate = cyclingWheelRevolutionRepository.findAll().size();
        cyclingWheelRevolution.setId(UUID.randomUUID());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCyclingWheelRevolutionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, cyclingWheelRevolution.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(cyclingWheelRevolution))
            )
            .andExpect(status().isBadRequest());

        // Validate the CyclingWheelRevolution in the database
        List<CyclingWheelRevolution> cyclingWheelRevolutionList = cyclingWheelRevolutionRepository.findAll();
        assertThat(cyclingWheelRevolutionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCyclingWheelRevolution() throws Exception {
        int databaseSizeBeforeUpdate = cyclingWheelRevolutionRepository.findAll().size();
        cyclingWheelRevolution.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCyclingWheelRevolutionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(cyclingWheelRevolution))
            )
            .andExpect(status().isBadRequest());

        // Validate the CyclingWheelRevolution in the database
        List<CyclingWheelRevolution> cyclingWheelRevolutionList = cyclingWheelRevolutionRepository.findAll();
        assertThat(cyclingWheelRevolutionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCyclingWheelRevolution() throws Exception {
        int databaseSizeBeforeUpdate = cyclingWheelRevolutionRepository.findAll().size();
        cyclingWheelRevolution.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCyclingWheelRevolutionMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(cyclingWheelRevolution))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CyclingWheelRevolution in the database
        List<CyclingWheelRevolution> cyclingWheelRevolutionList = cyclingWheelRevolutionRepository.findAll();
        assertThat(cyclingWheelRevolutionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCyclingWheelRevolutionWithPatch() throws Exception {
        // Initialize the database
        cyclingWheelRevolutionRepository.saveAndFlush(cyclingWheelRevolution);

        int databaseSizeBeforeUpdate = cyclingWheelRevolutionRepository.findAll().size();

        // Update the cyclingWheelRevolution using partial update
        CyclingWheelRevolution partialUpdatedCyclingWheelRevolution = new CyclingWheelRevolution();
        partialUpdatedCyclingWheelRevolution.setId(cyclingWheelRevolution.getId());

        partialUpdatedCyclingWheelRevolution.startTime(UPDATED_START_TIME);

        restCyclingWheelRevolutionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCyclingWheelRevolution.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCyclingWheelRevolution))
            )
            .andExpect(status().isOk());

        // Validate the CyclingWheelRevolution in the database
        List<CyclingWheelRevolution> cyclingWheelRevolutionList = cyclingWheelRevolutionRepository.findAll();
        assertThat(cyclingWheelRevolutionList).hasSize(databaseSizeBeforeUpdate);
        CyclingWheelRevolution testCyclingWheelRevolution = cyclingWheelRevolutionList.get(cyclingWheelRevolutionList.size() - 1);
        assertThat(testCyclingWheelRevolution.getUsuarioId()).isEqualTo(DEFAULT_USUARIO_ID);
        assertThat(testCyclingWheelRevolution.getEmpresaId()).isEqualTo(DEFAULT_EMPRESA_ID);
        assertThat(testCyclingWheelRevolution.getRevolutions()).isEqualTo(DEFAULT_REVOLUTIONS);
        assertThat(testCyclingWheelRevolution.getStartTime()).isEqualTo(UPDATED_START_TIME);
        assertThat(testCyclingWheelRevolution.getEndTime()).isEqualTo(DEFAULT_END_TIME);
    }

    @Test
    @Transactional
    void fullUpdateCyclingWheelRevolutionWithPatch() throws Exception {
        // Initialize the database
        cyclingWheelRevolutionRepository.saveAndFlush(cyclingWheelRevolution);

        int databaseSizeBeforeUpdate = cyclingWheelRevolutionRepository.findAll().size();

        // Update the cyclingWheelRevolution using partial update
        CyclingWheelRevolution partialUpdatedCyclingWheelRevolution = new CyclingWheelRevolution();
        partialUpdatedCyclingWheelRevolution.setId(cyclingWheelRevolution.getId());

        partialUpdatedCyclingWheelRevolution
            .usuarioId(UPDATED_USUARIO_ID)
            .empresaId(UPDATED_EMPRESA_ID)
            .revolutions(UPDATED_REVOLUTIONS)
            .startTime(UPDATED_START_TIME)
            .endTime(UPDATED_END_TIME);

        restCyclingWheelRevolutionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCyclingWheelRevolution.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCyclingWheelRevolution))
            )
            .andExpect(status().isOk());

        // Validate the CyclingWheelRevolution in the database
        List<CyclingWheelRevolution> cyclingWheelRevolutionList = cyclingWheelRevolutionRepository.findAll();
        assertThat(cyclingWheelRevolutionList).hasSize(databaseSizeBeforeUpdate);
        CyclingWheelRevolution testCyclingWheelRevolution = cyclingWheelRevolutionList.get(cyclingWheelRevolutionList.size() - 1);
        assertThat(testCyclingWheelRevolution.getUsuarioId()).isEqualTo(UPDATED_USUARIO_ID);
        assertThat(testCyclingWheelRevolution.getEmpresaId()).isEqualTo(UPDATED_EMPRESA_ID);
        assertThat(testCyclingWheelRevolution.getRevolutions()).isEqualTo(UPDATED_REVOLUTIONS);
        assertThat(testCyclingWheelRevolution.getStartTime()).isEqualTo(UPDATED_START_TIME);
        assertThat(testCyclingWheelRevolution.getEndTime()).isEqualTo(UPDATED_END_TIME);
    }

    @Test
    @Transactional
    void patchNonExistingCyclingWheelRevolution() throws Exception {
        int databaseSizeBeforeUpdate = cyclingWheelRevolutionRepository.findAll().size();
        cyclingWheelRevolution.setId(UUID.randomUUID());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCyclingWheelRevolutionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, cyclingWheelRevolution.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(cyclingWheelRevolution))
            )
            .andExpect(status().isBadRequest());

        // Validate the CyclingWheelRevolution in the database
        List<CyclingWheelRevolution> cyclingWheelRevolutionList = cyclingWheelRevolutionRepository.findAll();
        assertThat(cyclingWheelRevolutionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCyclingWheelRevolution() throws Exception {
        int databaseSizeBeforeUpdate = cyclingWheelRevolutionRepository.findAll().size();
        cyclingWheelRevolution.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCyclingWheelRevolutionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(cyclingWheelRevolution))
            )
            .andExpect(status().isBadRequest());

        // Validate the CyclingWheelRevolution in the database
        List<CyclingWheelRevolution> cyclingWheelRevolutionList = cyclingWheelRevolutionRepository.findAll();
        assertThat(cyclingWheelRevolutionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCyclingWheelRevolution() throws Exception {
        int databaseSizeBeforeUpdate = cyclingWheelRevolutionRepository.findAll().size();
        cyclingWheelRevolution.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCyclingWheelRevolutionMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(cyclingWheelRevolution))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CyclingWheelRevolution in the database
        List<CyclingWheelRevolution> cyclingWheelRevolutionList = cyclingWheelRevolutionRepository.findAll();
        assertThat(cyclingWheelRevolutionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCyclingWheelRevolution() throws Exception {
        // Initialize the database
        cyclingWheelRevolutionRepository.saveAndFlush(cyclingWheelRevolution);

        int databaseSizeBeforeDelete = cyclingWheelRevolutionRepository.findAll().size();

        // Delete the cyclingWheelRevolution
        restCyclingWheelRevolutionMockMvc
            .perform(delete(ENTITY_API_URL_ID, cyclingWheelRevolution.getId().toString()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CyclingWheelRevolution> cyclingWheelRevolutionList = cyclingWheelRevolutionRepository.findAll();
        assertThat(cyclingWheelRevolutionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
