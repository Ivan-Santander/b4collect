package com.be4tech.b4carecollect.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.be4tech.b4carecollect.IntegrationTest;
import com.be4tech.b4carecollect.domain.CiclingPedalingCadence;
import com.be4tech.b4carecollect.repository.CiclingPedalingCadenceRepository;
import com.be4tech.b4carecollect.service.criteria.CiclingPedalingCadenceCriteria;
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
 * Integration tests for the {@link CiclingPedalingCadenceResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CiclingPedalingCadenceResourceIT {

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

    private static final String ENTITY_API_URL = "/api/cicling-pedaling-cadences";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private CiclingPedalingCadenceRepository ciclingPedalingCadenceRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCiclingPedalingCadenceMockMvc;

    private CiclingPedalingCadence ciclingPedalingCadence;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CiclingPedalingCadence createEntity(EntityManager em) {
        CiclingPedalingCadence ciclingPedalingCadence = new CiclingPedalingCadence()
            .usuarioId(DEFAULT_USUARIO_ID)
            .empresaId(DEFAULT_EMPRESA_ID)
            .rpm(DEFAULT_RPM)
            .startTime(DEFAULT_START_TIME)
            .endTime(DEFAULT_END_TIME);
        return ciclingPedalingCadence;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CiclingPedalingCadence createUpdatedEntity(EntityManager em) {
        CiclingPedalingCadence ciclingPedalingCadence = new CiclingPedalingCadence()
            .usuarioId(UPDATED_USUARIO_ID)
            .empresaId(UPDATED_EMPRESA_ID)
            .rpm(UPDATED_RPM)
            .startTime(UPDATED_START_TIME)
            .endTime(UPDATED_END_TIME);
        return ciclingPedalingCadence;
    }

    @BeforeEach
    public void initTest() {
        ciclingPedalingCadence = createEntity(em);
    }

    @Test
    @Transactional
    void createCiclingPedalingCadence() throws Exception {
        int databaseSizeBeforeCreate = ciclingPedalingCadenceRepository.findAll().size();
        // Create the CiclingPedalingCadence
        restCiclingPedalingCadenceMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ciclingPedalingCadence))
            )
            .andExpect(status().isCreated());

        // Validate the CiclingPedalingCadence in the database
        List<CiclingPedalingCadence> ciclingPedalingCadenceList = ciclingPedalingCadenceRepository.findAll();
        assertThat(ciclingPedalingCadenceList).hasSize(databaseSizeBeforeCreate + 1);
        CiclingPedalingCadence testCiclingPedalingCadence = ciclingPedalingCadenceList.get(ciclingPedalingCadenceList.size() - 1);
        assertThat(testCiclingPedalingCadence.getUsuarioId()).isEqualTo(DEFAULT_USUARIO_ID);
        assertThat(testCiclingPedalingCadence.getEmpresaId()).isEqualTo(DEFAULT_EMPRESA_ID);
        assertThat(testCiclingPedalingCadence.getRpm()).isEqualTo(DEFAULT_RPM);
        assertThat(testCiclingPedalingCadence.getStartTime()).isEqualTo(DEFAULT_START_TIME);
        assertThat(testCiclingPedalingCadence.getEndTime()).isEqualTo(DEFAULT_END_TIME);
    }

    @Test
    @Transactional
    void createCiclingPedalingCadenceWithExistingId() throws Exception {
        // Create the CiclingPedalingCadence with an existing ID
        ciclingPedalingCadenceRepository.saveAndFlush(ciclingPedalingCadence);

        int databaseSizeBeforeCreate = ciclingPedalingCadenceRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCiclingPedalingCadenceMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ciclingPedalingCadence))
            )
            .andExpect(status().isBadRequest());

        // Validate the CiclingPedalingCadence in the database
        List<CiclingPedalingCadence> ciclingPedalingCadenceList = ciclingPedalingCadenceRepository.findAll();
        assertThat(ciclingPedalingCadenceList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllCiclingPedalingCadences() throws Exception {
        // Initialize the database
        ciclingPedalingCadenceRepository.saveAndFlush(ciclingPedalingCadence);

        // Get all the ciclingPedalingCadenceList
        restCiclingPedalingCadenceMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ciclingPedalingCadence.getId().toString())))
            .andExpect(jsonPath("$.[*].usuarioId").value(hasItem(DEFAULT_USUARIO_ID)))
            .andExpect(jsonPath("$.[*].empresaId").value(hasItem(DEFAULT_EMPRESA_ID)))
            .andExpect(jsonPath("$.[*].rpm").value(hasItem(DEFAULT_RPM)))
            .andExpect(jsonPath("$.[*].startTime").value(hasItem(DEFAULT_START_TIME.toString())))
            .andExpect(jsonPath("$.[*].endTime").value(hasItem(DEFAULT_END_TIME.toString())));
    }

    @Test
    @Transactional
    void getCiclingPedalingCadence() throws Exception {
        // Initialize the database
        ciclingPedalingCadenceRepository.saveAndFlush(ciclingPedalingCadence);

        // Get the ciclingPedalingCadence
        restCiclingPedalingCadenceMockMvc
            .perform(get(ENTITY_API_URL_ID, ciclingPedalingCadence.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(ciclingPedalingCadence.getId().toString()))
            .andExpect(jsonPath("$.usuarioId").value(DEFAULT_USUARIO_ID))
            .andExpect(jsonPath("$.empresaId").value(DEFAULT_EMPRESA_ID))
            .andExpect(jsonPath("$.rpm").value(DEFAULT_RPM))
            .andExpect(jsonPath("$.startTime").value(DEFAULT_START_TIME.toString()))
            .andExpect(jsonPath("$.endTime").value(DEFAULT_END_TIME.toString()));
    }

    @Test
    @Transactional
    void getCiclingPedalingCadencesByIdFiltering() throws Exception {
        // Initialize the database
        ciclingPedalingCadenceRepository.saveAndFlush(ciclingPedalingCadence);

        UUID id = ciclingPedalingCadence.getId();

        defaultCiclingPedalingCadenceShouldBeFound("id.equals=" + id);
        defaultCiclingPedalingCadenceShouldNotBeFound("id.notEquals=" + id);
    }

    @Test
    @Transactional
    void getAllCiclingPedalingCadencesByUsuarioIdIsEqualToSomething() throws Exception {
        // Initialize the database
        ciclingPedalingCadenceRepository.saveAndFlush(ciclingPedalingCadence);

        // Get all the ciclingPedalingCadenceList where usuarioId equals to DEFAULT_USUARIO_ID
        defaultCiclingPedalingCadenceShouldBeFound("usuarioId.equals=" + DEFAULT_USUARIO_ID);

        // Get all the ciclingPedalingCadenceList where usuarioId equals to UPDATED_USUARIO_ID
        defaultCiclingPedalingCadenceShouldNotBeFound("usuarioId.equals=" + UPDATED_USUARIO_ID);
    }

    @Test
    @Transactional
    void getAllCiclingPedalingCadencesByUsuarioIdIsInShouldWork() throws Exception {
        // Initialize the database
        ciclingPedalingCadenceRepository.saveAndFlush(ciclingPedalingCadence);

        // Get all the ciclingPedalingCadenceList where usuarioId in DEFAULT_USUARIO_ID or UPDATED_USUARIO_ID
        defaultCiclingPedalingCadenceShouldBeFound("usuarioId.in=" + DEFAULT_USUARIO_ID + "," + UPDATED_USUARIO_ID);

        // Get all the ciclingPedalingCadenceList where usuarioId equals to UPDATED_USUARIO_ID
        defaultCiclingPedalingCadenceShouldNotBeFound("usuarioId.in=" + UPDATED_USUARIO_ID);
    }

    @Test
    @Transactional
    void getAllCiclingPedalingCadencesByUsuarioIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        ciclingPedalingCadenceRepository.saveAndFlush(ciclingPedalingCadence);

        // Get all the ciclingPedalingCadenceList where usuarioId is not null
        defaultCiclingPedalingCadenceShouldBeFound("usuarioId.specified=true");

        // Get all the ciclingPedalingCadenceList where usuarioId is null
        defaultCiclingPedalingCadenceShouldNotBeFound("usuarioId.specified=false");
    }

    @Test
    @Transactional
    void getAllCiclingPedalingCadencesByUsuarioIdContainsSomething() throws Exception {
        // Initialize the database
        ciclingPedalingCadenceRepository.saveAndFlush(ciclingPedalingCadence);

        // Get all the ciclingPedalingCadenceList where usuarioId contains DEFAULT_USUARIO_ID
        defaultCiclingPedalingCadenceShouldBeFound("usuarioId.contains=" + DEFAULT_USUARIO_ID);

        // Get all the ciclingPedalingCadenceList where usuarioId contains UPDATED_USUARIO_ID
        defaultCiclingPedalingCadenceShouldNotBeFound("usuarioId.contains=" + UPDATED_USUARIO_ID);
    }

    @Test
    @Transactional
    void getAllCiclingPedalingCadencesByUsuarioIdNotContainsSomething() throws Exception {
        // Initialize the database
        ciclingPedalingCadenceRepository.saveAndFlush(ciclingPedalingCadence);

        // Get all the ciclingPedalingCadenceList where usuarioId does not contain DEFAULT_USUARIO_ID
        defaultCiclingPedalingCadenceShouldNotBeFound("usuarioId.doesNotContain=" + DEFAULT_USUARIO_ID);

        // Get all the ciclingPedalingCadenceList where usuarioId does not contain UPDATED_USUARIO_ID
        defaultCiclingPedalingCadenceShouldBeFound("usuarioId.doesNotContain=" + UPDATED_USUARIO_ID);
    }

    @Test
    @Transactional
    void getAllCiclingPedalingCadencesByEmpresaIdIsEqualToSomething() throws Exception {
        // Initialize the database
        ciclingPedalingCadenceRepository.saveAndFlush(ciclingPedalingCadence);

        // Get all the ciclingPedalingCadenceList where empresaId equals to DEFAULT_EMPRESA_ID
        defaultCiclingPedalingCadenceShouldBeFound("empresaId.equals=" + DEFAULT_EMPRESA_ID);

        // Get all the ciclingPedalingCadenceList where empresaId equals to UPDATED_EMPRESA_ID
        defaultCiclingPedalingCadenceShouldNotBeFound("empresaId.equals=" + UPDATED_EMPRESA_ID);
    }

    @Test
    @Transactional
    void getAllCiclingPedalingCadencesByEmpresaIdIsInShouldWork() throws Exception {
        // Initialize the database
        ciclingPedalingCadenceRepository.saveAndFlush(ciclingPedalingCadence);

        // Get all the ciclingPedalingCadenceList where empresaId in DEFAULT_EMPRESA_ID or UPDATED_EMPRESA_ID
        defaultCiclingPedalingCadenceShouldBeFound("empresaId.in=" + DEFAULT_EMPRESA_ID + "," + UPDATED_EMPRESA_ID);

        // Get all the ciclingPedalingCadenceList where empresaId equals to UPDATED_EMPRESA_ID
        defaultCiclingPedalingCadenceShouldNotBeFound("empresaId.in=" + UPDATED_EMPRESA_ID);
    }

    @Test
    @Transactional
    void getAllCiclingPedalingCadencesByEmpresaIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        ciclingPedalingCadenceRepository.saveAndFlush(ciclingPedalingCadence);

        // Get all the ciclingPedalingCadenceList where empresaId is not null
        defaultCiclingPedalingCadenceShouldBeFound("empresaId.specified=true");

        // Get all the ciclingPedalingCadenceList where empresaId is null
        defaultCiclingPedalingCadenceShouldNotBeFound("empresaId.specified=false");
    }

    @Test
    @Transactional
    void getAllCiclingPedalingCadencesByEmpresaIdContainsSomething() throws Exception {
        // Initialize the database
        ciclingPedalingCadenceRepository.saveAndFlush(ciclingPedalingCadence);

        // Get all the ciclingPedalingCadenceList where empresaId contains DEFAULT_EMPRESA_ID
        defaultCiclingPedalingCadenceShouldBeFound("empresaId.contains=" + DEFAULT_EMPRESA_ID);

        // Get all the ciclingPedalingCadenceList where empresaId contains UPDATED_EMPRESA_ID
        defaultCiclingPedalingCadenceShouldNotBeFound("empresaId.contains=" + UPDATED_EMPRESA_ID);
    }

    @Test
    @Transactional
    void getAllCiclingPedalingCadencesByEmpresaIdNotContainsSomething() throws Exception {
        // Initialize the database
        ciclingPedalingCadenceRepository.saveAndFlush(ciclingPedalingCadence);

        // Get all the ciclingPedalingCadenceList where empresaId does not contain DEFAULT_EMPRESA_ID
        defaultCiclingPedalingCadenceShouldNotBeFound("empresaId.doesNotContain=" + DEFAULT_EMPRESA_ID);

        // Get all the ciclingPedalingCadenceList where empresaId does not contain UPDATED_EMPRESA_ID
        defaultCiclingPedalingCadenceShouldBeFound("empresaId.doesNotContain=" + UPDATED_EMPRESA_ID);
    }

    @Test
    @Transactional
    void getAllCiclingPedalingCadencesByRpmIsEqualToSomething() throws Exception {
        // Initialize the database
        ciclingPedalingCadenceRepository.saveAndFlush(ciclingPedalingCadence);

        // Get all the ciclingPedalingCadenceList where rpm equals to DEFAULT_RPM
        defaultCiclingPedalingCadenceShouldBeFound("rpm.equals=" + DEFAULT_RPM);

        // Get all the ciclingPedalingCadenceList where rpm equals to UPDATED_RPM
        defaultCiclingPedalingCadenceShouldNotBeFound("rpm.equals=" + UPDATED_RPM);
    }

    @Test
    @Transactional
    void getAllCiclingPedalingCadencesByRpmIsInShouldWork() throws Exception {
        // Initialize the database
        ciclingPedalingCadenceRepository.saveAndFlush(ciclingPedalingCadence);

        // Get all the ciclingPedalingCadenceList where rpm in DEFAULT_RPM or UPDATED_RPM
        defaultCiclingPedalingCadenceShouldBeFound("rpm.in=" + DEFAULT_RPM + "," + UPDATED_RPM);

        // Get all the ciclingPedalingCadenceList where rpm equals to UPDATED_RPM
        defaultCiclingPedalingCadenceShouldNotBeFound("rpm.in=" + UPDATED_RPM);
    }

    @Test
    @Transactional
    void getAllCiclingPedalingCadencesByRpmIsNullOrNotNull() throws Exception {
        // Initialize the database
        ciclingPedalingCadenceRepository.saveAndFlush(ciclingPedalingCadence);

        // Get all the ciclingPedalingCadenceList where rpm is not null
        defaultCiclingPedalingCadenceShouldBeFound("rpm.specified=true");

        // Get all the ciclingPedalingCadenceList where rpm is null
        defaultCiclingPedalingCadenceShouldNotBeFound("rpm.specified=false");
    }

    @Test
    @Transactional
    void getAllCiclingPedalingCadencesByRpmIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        ciclingPedalingCadenceRepository.saveAndFlush(ciclingPedalingCadence);

        // Get all the ciclingPedalingCadenceList where rpm is greater than or equal to DEFAULT_RPM
        defaultCiclingPedalingCadenceShouldBeFound("rpm.greaterThanOrEqual=" + DEFAULT_RPM);

        // Get all the ciclingPedalingCadenceList where rpm is greater than or equal to UPDATED_RPM
        defaultCiclingPedalingCadenceShouldNotBeFound("rpm.greaterThanOrEqual=" + UPDATED_RPM);
    }

    @Test
    @Transactional
    void getAllCiclingPedalingCadencesByRpmIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        ciclingPedalingCadenceRepository.saveAndFlush(ciclingPedalingCadence);

        // Get all the ciclingPedalingCadenceList where rpm is less than or equal to DEFAULT_RPM
        defaultCiclingPedalingCadenceShouldBeFound("rpm.lessThanOrEqual=" + DEFAULT_RPM);

        // Get all the ciclingPedalingCadenceList where rpm is less than or equal to SMALLER_RPM
        defaultCiclingPedalingCadenceShouldNotBeFound("rpm.lessThanOrEqual=" + SMALLER_RPM);
    }

    @Test
    @Transactional
    void getAllCiclingPedalingCadencesByRpmIsLessThanSomething() throws Exception {
        // Initialize the database
        ciclingPedalingCadenceRepository.saveAndFlush(ciclingPedalingCadence);

        // Get all the ciclingPedalingCadenceList where rpm is less than DEFAULT_RPM
        defaultCiclingPedalingCadenceShouldNotBeFound("rpm.lessThan=" + DEFAULT_RPM);

        // Get all the ciclingPedalingCadenceList where rpm is less than UPDATED_RPM
        defaultCiclingPedalingCadenceShouldBeFound("rpm.lessThan=" + UPDATED_RPM);
    }

    @Test
    @Transactional
    void getAllCiclingPedalingCadencesByRpmIsGreaterThanSomething() throws Exception {
        // Initialize the database
        ciclingPedalingCadenceRepository.saveAndFlush(ciclingPedalingCadence);

        // Get all the ciclingPedalingCadenceList where rpm is greater than DEFAULT_RPM
        defaultCiclingPedalingCadenceShouldNotBeFound("rpm.greaterThan=" + DEFAULT_RPM);

        // Get all the ciclingPedalingCadenceList where rpm is greater than SMALLER_RPM
        defaultCiclingPedalingCadenceShouldBeFound("rpm.greaterThan=" + SMALLER_RPM);
    }

    @Test
    @Transactional
    void getAllCiclingPedalingCadencesByStartTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        ciclingPedalingCadenceRepository.saveAndFlush(ciclingPedalingCadence);

        // Get all the ciclingPedalingCadenceList where startTime equals to DEFAULT_START_TIME
        defaultCiclingPedalingCadenceShouldBeFound("startTime.equals=" + DEFAULT_START_TIME);

        // Get all the ciclingPedalingCadenceList where startTime equals to UPDATED_START_TIME
        defaultCiclingPedalingCadenceShouldNotBeFound("startTime.equals=" + UPDATED_START_TIME);
    }

    @Test
    @Transactional
    void getAllCiclingPedalingCadencesByStartTimeIsInShouldWork() throws Exception {
        // Initialize the database
        ciclingPedalingCadenceRepository.saveAndFlush(ciclingPedalingCadence);

        // Get all the ciclingPedalingCadenceList where startTime in DEFAULT_START_TIME or UPDATED_START_TIME
        defaultCiclingPedalingCadenceShouldBeFound("startTime.in=" + DEFAULT_START_TIME + "," + UPDATED_START_TIME);

        // Get all the ciclingPedalingCadenceList where startTime equals to UPDATED_START_TIME
        defaultCiclingPedalingCadenceShouldNotBeFound("startTime.in=" + UPDATED_START_TIME);
    }

    @Test
    @Transactional
    void getAllCiclingPedalingCadencesByStartTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        ciclingPedalingCadenceRepository.saveAndFlush(ciclingPedalingCadence);

        // Get all the ciclingPedalingCadenceList where startTime is not null
        defaultCiclingPedalingCadenceShouldBeFound("startTime.specified=true");

        // Get all the ciclingPedalingCadenceList where startTime is null
        defaultCiclingPedalingCadenceShouldNotBeFound("startTime.specified=false");
    }

    @Test
    @Transactional
    void getAllCiclingPedalingCadencesByEndTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        ciclingPedalingCadenceRepository.saveAndFlush(ciclingPedalingCadence);

        // Get all the ciclingPedalingCadenceList where endTime equals to DEFAULT_END_TIME
        defaultCiclingPedalingCadenceShouldBeFound("endTime.equals=" + DEFAULT_END_TIME);

        // Get all the ciclingPedalingCadenceList where endTime equals to UPDATED_END_TIME
        defaultCiclingPedalingCadenceShouldNotBeFound("endTime.equals=" + UPDATED_END_TIME);
    }

    @Test
    @Transactional
    void getAllCiclingPedalingCadencesByEndTimeIsInShouldWork() throws Exception {
        // Initialize the database
        ciclingPedalingCadenceRepository.saveAndFlush(ciclingPedalingCadence);

        // Get all the ciclingPedalingCadenceList where endTime in DEFAULT_END_TIME or UPDATED_END_TIME
        defaultCiclingPedalingCadenceShouldBeFound("endTime.in=" + DEFAULT_END_TIME + "," + UPDATED_END_TIME);

        // Get all the ciclingPedalingCadenceList where endTime equals to UPDATED_END_TIME
        defaultCiclingPedalingCadenceShouldNotBeFound("endTime.in=" + UPDATED_END_TIME);
    }

    @Test
    @Transactional
    void getAllCiclingPedalingCadencesByEndTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        ciclingPedalingCadenceRepository.saveAndFlush(ciclingPedalingCadence);

        // Get all the ciclingPedalingCadenceList where endTime is not null
        defaultCiclingPedalingCadenceShouldBeFound("endTime.specified=true");

        // Get all the ciclingPedalingCadenceList where endTime is null
        defaultCiclingPedalingCadenceShouldNotBeFound("endTime.specified=false");
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCiclingPedalingCadenceShouldBeFound(String filter) throws Exception {
        restCiclingPedalingCadenceMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ciclingPedalingCadence.getId().toString())))
            .andExpect(jsonPath("$.[*].usuarioId").value(hasItem(DEFAULT_USUARIO_ID)))
            .andExpect(jsonPath("$.[*].empresaId").value(hasItem(DEFAULT_EMPRESA_ID)))
            .andExpect(jsonPath("$.[*].rpm").value(hasItem(DEFAULT_RPM)))
            .andExpect(jsonPath("$.[*].startTime").value(hasItem(DEFAULT_START_TIME.toString())))
            .andExpect(jsonPath("$.[*].endTime").value(hasItem(DEFAULT_END_TIME.toString())));

        // Check, that the count call also returns 1
        restCiclingPedalingCadenceMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCiclingPedalingCadenceShouldNotBeFound(String filter) throws Exception {
        restCiclingPedalingCadenceMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCiclingPedalingCadenceMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingCiclingPedalingCadence() throws Exception {
        // Get the ciclingPedalingCadence
        restCiclingPedalingCadenceMockMvc.perform(get(ENTITY_API_URL_ID, UUID.randomUUID().toString())).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingCiclingPedalingCadence() throws Exception {
        // Initialize the database
        ciclingPedalingCadenceRepository.saveAndFlush(ciclingPedalingCadence);

        int databaseSizeBeforeUpdate = ciclingPedalingCadenceRepository.findAll().size();

        // Update the ciclingPedalingCadence
        CiclingPedalingCadence updatedCiclingPedalingCadence = ciclingPedalingCadenceRepository
            .findById(ciclingPedalingCadence.getId())
            .get();
        // Disconnect from session so that the updates on updatedCiclingPedalingCadence are not directly saved in db
        em.detach(updatedCiclingPedalingCadence);
        updatedCiclingPedalingCadence
            .usuarioId(UPDATED_USUARIO_ID)
            .empresaId(UPDATED_EMPRESA_ID)
            .rpm(UPDATED_RPM)
            .startTime(UPDATED_START_TIME)
            .endTime(UPDATED_END_TIME);

        restCiclingPedalingCadenceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedCiclingPedalingCadence.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedCiclingPedalingCadence))
            )
            .andExpect(status().isOk());

        // Validate the CiclingPedalingCadence in the database
        List<CiclingPedalingCadence> ciclingPedalingCadenceList = ciclingPedalingCadenceRepository.findAll();
        assertThat(ciclingPedalingCadenceList).hasSize(databaseSizeBeforeUpdate);
        CiclingPedalingCadence testCiclingPedalingCadence = ciclingPedalingCadenceList.get(ciclingPedalingCadenceList.size() - 1);
        assertThat(testCiclingPedalingCadence.getUsuarioId()).isEqualTo(UPDATED_USUARIO_ID);
        assertThat(testCiclingPedalingCadence.getEmpresaId()).isEqualTo(UPDATED_EMPRESA_ID);
        assertThat(testCiclingPedalingCadence.getRpm()).isEqualTo(UPDATED_RPM);
        assertThat(testCiclingPedalingCadence.getStartTime()).isEqualTo(UPDATED_START_TIME);
        assertThat(testCiclingPedalingCadence.getEndTime()).isEqualTo(UPDATED_END_TIME);
    }

    @Test
    @Transactional
    void putNonExistingCiclingPedalingCadence() throws Exception {
        int databaseSizeBeforeUpdate = ciclingPedalingCadenceRepository.findAll().size();
        ciclingPedalingCadence.setId(UUID.randomUUID());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCiclingPedalingCadenceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, ciclingPedalingCadence.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ciclingPedalingCadence))
            )
            .andExpect(status().isBadRequest());

        // Validate the CiclingPedalingCadence in the database
        List<CiclingPedalingCadence> ciclingPedalingCadenceList = ciclingPedalingCadenceRepository.findAll();
        assertThat(ciclingPedalingCadenceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCiclingPedalingCadence() throws Exception {
        int databaseSizeBeforeUpdate = ciclingPedalingCadenceRepository.findAll().size();
        ciclingPedalingCadence.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCiclingPedalingCadenceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ciclingPedalingCadence))
            )
            .andExpect(status().isBadRequest());

        // Validate the CiclingPedalingCadence in the database
        List<CiclingPedalingCadence> ciclingPedalingCadenceList = ciclingPedalingCadenceRepository.findAll();
        assertThat(ciclingPedalingCadenceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCiclingPedalingCadence() throws Exception {
        int databaseSizeBeforeUpdate = ciclingPedalingCadenceRepository.findAll().size();
        ciclingPedalingCadence.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCiclingPedalingCadenceMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ciclingPedalingCadence))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CiclingPedalingCadence in the database
        List<CiclingPedalingCadence> ciclingPedalingCadenceList = ciclingPedalingCadenceRepository.findAll();
        assertThat(ciclingPedalingCadenceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCiclingPedalingCadenceWithPatch() throws Exception {
        // Initialize the database
        ciclingPedalingCadenceRepository.saveAndFlush(ciclingPedalingCadence);

        int databaseSizeBeforeUpdate = ciclingPedalingCadenceRepository.findAll().size();

        // Update the ciclingPedalingCadence using partial update
        CiclingPedalingCadence partialUpdatedCiclingPedalingCadence = new CiclingPedalingCadence();
        partialUpdatedCiclingPedalingCadence.setId(ciclingPedalingCadence.getId());

        partialUpdatedCiclingPedalingCadence.usuarioId(UPDATED_USUARIO_ID).rpm(UPDATED_RPM).endTime(UPDATED_END_TIME);

        restCiclingPedalingCadenceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCiclingPedalingCadence.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCiclingPedalingCadence))
            )
            .andExpect(status().isOk());

        // Validate the CiclingPedalingCadence in the database
        List<CiclingPedalingCadence> ciclingPedalingCadenceList = ciclingPedalingCadenceRepository.findAll();
        assertThat(ciclingPedalingCadenceList).hasSize(databaseSizeBeforeUpdate);
        CiclingPedalingCadence testCiclingPedalingCadence = ciclingPedalingCadenceList.get(ciclingPedalingCadenceList.size() - 1);
        assertThat(testCiclingPedalingCadence.getUsuarioId()).isEqualTo(UPDATED_USUARIO_ID);
        assertThat(testCiclingPedalingCadence.getEmpresaId()).isEqualTo(DEFAULT_EMPRESA_ID);
        assertThat(testCiclingPedalingCadence.getRpm()).isEqualTo(UPDATED_RPM);
        assertThat(testCiclingPedalingCadence.getStartTime()).isEqualTo(DEFAULT_START_TIME);
        assertThat(testCiclingPedalingCadence.getEndTime()).isEqualTo(UPDATED_END_TIME);
    }

    @Test
    @Transactional
    void fullUpdateCiclingPedalingCadenceWithPatch() throws Exception {
        // Initialize the database
        ciclingPedalingCadenceRepository.saveAndFlush(ciclingPedalingCadence);

        int databaseSizeBeforeUpdate = ciclingPedalingCadenceRepository.findAll().size();

        // Update the ciclingPedalingCadence using partial update
        CiclingPedalingCadence partialUpdatedCiclingPedalingCadence = new CiclingPedalingCadence();
        partialUpdatedCiclingPedalingCadence.setId(ciclingPedalingCadence.getId());

        partialUpdatedCiclingPedalingCadence
            .usuarioId(UPDATED_USUARIO_ID)
            .empresaId(UPDATED_EMPRESA_ID)
            .rpm(UPDATED_RPM)
            .startTime(UPDATED_START_TIME)
            .endTime(UPDATED_END_TIME);

        restCiclingPedalingCadenceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCiclingPedalingCadence.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCiclingPedalingCadence))
            )
            .andExpect(status().isOk());

        // Validate the CiclingPedalingCadence in the database
        List<CiclingPedalingCadence> ciclingPedalingCadenceList = ciclingPedalingCadenceRepository.findAll();
        assertThat(ciclingPedalingCadenceList).hasSize(databaseSizeBeforeUpdate);
        CiclingPedalingCadence testCiclingPedalingCadence = ciclingPedalingCadenceList.get(ciclingPedalingCadenceList.size() - 1);
        assertThat(testCiclingPedalingCadence.getUsuarioId()).isEqualTo(UPDATED_USUARIO_ID);
        assertThat(testCiclingPedalingCadence.getEmpresaId()).isEqualTo(UPDATED_EMPRESA_ID);
        assertThat(testCiclingPedalingCadence.getRpm()).isEqualTo(UPDATED_RPM);
        assertThat(testCiclingPedalingCadence.getStartTime()).isEqualTo(UPDATED_START_TIME);
        assertThat(testCiclingPedalingCadence.getEndTime()).isEqualTo(UPDATED_END_TIME);
    }

    @Test
    @Transactional
    void patchNonExistingCiclingPedalingCadence() throws Exception {
        int databaseSizeBeforeUpdate = ciclingPedalingCadenceRepository.findAll().size();
        ciclingPedalingCadence.setId(UUID.randomUUID());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCiclingPedalingCadenceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, ciclingPedalingCadence.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(ciclingPedalingCadence))
            )
            .andExpect(status().isBadRequest());

        // Validate the CiclingPedalingCadence in the database
        List<CiclingPedalingCadence> ciclingPedalingCadenceList = ciclingPedalingCadenceRepository.findAll();
        assertThat(ciclingPedalingCadenceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCiclingPedalingCadence() throws Exception {
        int databaseSizeBeforeUpdate = ciclingPedalingCadenceRepository.findAll().size();
        ciclingPedalingCadence.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCiclingPedalingCadenceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(ciclingPedalingCadence))
            )
            .andExpect(status().isBadRequest());

        // Validate the CiclingPedalingCadence in the database
        List<CiclingPedalingCadence> ciclingPedalingCadenceList = ciclingPedalingCadenceRepository.findAll();
        assertThat(ciclingPedalingCadenceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCiclingPedalingCadence() throws Exception {
        int databaseSizeBeforeUpdate = ciclingPedalingCadenceRepository.findAll().size();
        ciclingPedalingCadence.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCiclingPedalingCadenceMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(ciclingPedalingCadence))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CiclingPedalingCadence in the database
        List<CiclingPedalingCadence> ciclingPedalingCadenceList = ciclingPedalingCadenceRepository.findAll();
        assertThat(ciclingPedalingCadenceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCiclingPedalingCadence() throws Exception {
        // Initialize the database
        ciclingPedalingCadenceRepository.saveAndFlush(ciclingPedalingCadence);

        int databaseSizeBeforeDelete = ciclingPedalingCadenceRepository.findAll().size();

        // Delete the ciclingPedalingCadence
        restCiclingPedalingCadenceMockMvc
            .perform(delete(ENTITY_API_URL_ID, ciclingPedalingCadence.getId().toString()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CiclingPedalingCadence> ciclingPedalingCadenceList = ciclingPedalingCadenceRepository.findAll();
        assertThat(ciclingPedalingCadenceList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
