package com.be4tech.b4carecollect.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.be4tech.b4carecollect.IntegrationTest;
import com.be4tech.b4carecollect.domain.PoweSample;
import com.be4tech.b4carecollect.repository.PoweSampleRepository;
import com.be4tech.b4carecollect.service.criteria.PoweSampleCriteria;
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
 * Integration tests for the {@link PoweSampleResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PoweSampleResourceIT {

    private static final String DEFAULT_USUARIO_ID = "AAAAAAAAAA";
    private static final String UPDATED_USUARIO_ID = "BBBBBBBBBB";

    private static final String DEFAULT_EMPRESA_ID = "AAAAAAAAAA";
    private static final String UPDATED_EMPRESA_ID = "BBBBBBBBBB";

    private static final Float DEFAULT_VATIOS = 1F;
    private static final Float UPDATED_VATIOS = 2F;
    private static final Float SMALLER_VATIOS = 1F - 1F;

    private static final Instant DEFAULT_START_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_START_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_END_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_END_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/powe-samples";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private PoweSampleRepository poweSampleRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPoweSampleMockMvc;

    private PoweSample poweSample;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PoweSample createEntity(EntityManager em) {
        PoweSample poweSample = new PoweSample()
            .usuarioId(DEFAULT_USUARIO_ID)
            .empresaId(DEFAULT_EMPRESA_ID)
            .vatios(DEFAULT_VATIOS)
            .startTime(DEFAULT_START_TIME)
            .endTime(DEFAULT_END_TIME);
        return poweSample;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PoweSample createUpdatedEntity(EntityManager em) {
        PoweSample poweSample = new PoweSample()
            .usuarioId(UPDATED_USUARIO_ID)
            .empresaId(UPDATED_EMPRESA_ID)
            .vatios(UPDATED_VATIOS)
            .startTime(UPDATED_START_TIME)
            .endTime(UPDATED_END_TIME);
        return poweSample;
    }

    @BeforeEach
    public void initTest() {
        poweSample = createEntity(em);
    }

    @Test
    @Transactional
    void createPoweSample() throws Exception {
        int databaseSizeBeforeCreate = poweSampleRepository.findAll().size();
        // Create the PoweSample
        restPoweSampleMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(poweSample)))
            .andExpect(status().isCreated());

        // Validate the PoweSample in the database
        List<PoweSample> poweSampleList = poweSampleRepository.findAll();
        assertThat(poweSampleList).hasSize(databaseSizeBeforeCreate + 1);
        PoweSample testPoweSample = poweSampleList.get(poweSampleList.size() - 1);
        assertThat(testPoweSample.getUsuarioId()).isEqualTo(DEFAULT_USUARIO_ID);
        assertThat(testPoweSample.getEmpresaId()).isEqualTo(DEFAULT_EMPRESA_ID);
        assertThat(testPoweSample.getVatios()).isEqualTo(DEFAULT_VATIOS);
        assertThat(testPoweSample.getStartTime()).isEqualTo(DEFAULT_START_TIME);
        assertThat(testPoweSample.getEndTime()).isEqualTo(DEFAULT_END_TIME);
    }

    @Test
    @Transactional
    void createPoweSampleWithExistingId() throws Exception {
        // Create the PoweSample with an existing ID
        poweSampleRepository.saveAndFlush(poweSample);

        int databaseSizeBeforeCreate = poweSampleRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPoweSampleMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(poweSample)))
            .andExpect(status().isBadRequest());

        // Validate the PoweSample in the database
        List<PoweSample> poweSampleList = poweSampleRepository.findAll();
        assertThat(poweSampleList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllPoweSamples() throws Exception {
        // Initialize the database
        poweSampleRepository.saveAndFlush(poweSample);

        // Get all the poweSampleList
        restPoweSampleMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(poweSample.getId().toString())))
            .andExpect(jsonPath("$.[*].usuarioId").value(hasItem(DEFAULT_USUARIO_ID)))
            .andExpect(jsonPath("$.[*].empresaId").value(hasItem(DEFAULT_EMPRESA_ID)))
            .andExpect(jsonPath("$.[*].vatios").value(hasItem(DEFAULT_VATIOS.doubleValue())))
            .andExpect(jsonPath("$.[*].startTime").value(hasItem(DEFAULT_START_TIME.toString())))
            .andExpect(jsonPath("$.[*].endTime").value(hasItem(DEFAULT_END_TIME.toString())));
    }

    @Test
    @Transactional
    void getPoweSample() throws Exception {
        // Initialize the database
        poweSampleRepository.saveAndFlush(poweSample);

        // Get the poweSample
        restPoweSampleMockMvc
            .perform(get(ENTITY_API_URL_ID, poweSample.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(poweSample.getId().toString()))
            .andExpect(jsonPath("$.usuarioId").value(DEFAULT_USUARIO_ID))
            .andExpect(jsonPath("$.empresaId").value(DEFAULT_EMPRESA_ID))
            .andExpect(jsonPath("$.vatios").value(DEFAULT_VATIOS.doubleValue()))
            .andExpect(jsonPath("$.startTime").value(DEFAULT_START_TIME.toString()))
            .andExpect(jsonPath("$.endTime").value(DEFAULT_END_TIME.toString()));
    }

    @Test
    @Transactional
    void getPoweSamplesByIdFiltering() throws Exception {
        // Initialize the database
        poweSampleRepository.saveAndFlush(poweSample);

        UUID id = poweSample.getId();

        defaultPoweSampleShouldBeFound("id.equals=" + id);
        defaultPoweSampleShouldNotBeFound("id.notEquals=" + id);
    }

    @Test
    @Transactional
    void getAllPoweSamplesByUsuarioIdIsEqualToSomething() throws Exception {
        // Initialize the database
        poweSampleRepository.saveAndFlush(poweSample);

        // Get all the poweSampleList where usuarioId equals to DEFAULT_USUARIO_ID
        defaultPoweSampleShouldBeFound("usuarioId.equals=" + DEFAULT_USUARIO_ID);

        // Get all the poweSampleList where usuarioId equals to UPDATED_USUARIO_ID
        defaultPoweSampleShouldNotBeFound("usuarioId.equals=" + UPDATED_USUARIO_ID);
    }

    @Test
    @Transactional
    void getAllPoweSamplesByUsuarioIdIsInShouldWork() throws Exception {
        // Initialize the database
        poweSampleRepository.saveAndFlush(poweSample);

        // Get all the poweSampleList where usuarioId in DEFAULT_USUARIO_ID or UPDATED_USUARIO_ID
        defaultPoweSampleShouldBeFound("usuarioId.in=" + DEFAULT_USUARIO_ID + "," + UPDATED_USUARIO_ID);

        // Get all the poweSampleList where usuarioId equals to UPDATED_USUARIO_ID
        defaultPoweSampleShouldNotBeFound("usuarioId.in=" + UPDATED_USUARIO_ID);
    }

    @Test
    @Transactional
    void getAllPoweSamplesByUsuarioIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        poweSampleRepository.saveAndFlush(poweSample);

        // Get all the poweSampleList where usuarioId is not null
        defaultPoweSampleShouldBeFound("usuarioId.specified=true");

        // Get all the poweSampleList where usuarioId is null
        defaultPoweSampleShouldNotBeFound("usuarioId.specified=false");
    }

    @Test
    @Transactional
    void getAllPoweSamplesByUsuarioIdContainsSomething() throws Exception {
        // Initialize the database
        poweSampleRepository.saveAndFlush(poweSample);

        // Get all the poweSampleList where usuarioId contains DEFAULT_USUARIO_ID
        defaultPoweSampleShouldBeFound("usuarioId.contains=" + DEFAULT_USUARIO_ID);

        // Get all the poweSampleList where usuarioId contains UPDATED_USUARIO_ID
        defaultPoweSampleShouldNotBeFound("usuarioId.contains=" + UPDATED_USUARIO_ID);
    }

    @Test
    @Transactional
    void getAllPoweSamplesByUsuarioIdNotContainsSomething() throws Exception {
        // Initialize the database
        poweSampleRepository.saveAndFlush(poweSample);

        // Get all the poweSampleList where usuarioId does not contain DEFAULT_USUARIO_ID
        defaultPoweSampleShouldNotBeFound("usuarioId.doesNotContain=" + DEFAULT_USUARIO_ID);

        // Get all the poweSampleList where usuarioId does not contain UPDATED_USUARIO_ID
        defaultPoweSampleShouldBeFound("usuarioId.doesNotContain=" + UPDATED_USUARIO_ID);
    }

    @Test
    @Transactional
    void getAllPoweSamplesByEmpresaIdIsEqualToSomething() throws Exception {
        // Initialize the database
        poweSampleRepository.saveAndFlush(poweSample);

        // Get all the poweSampleList where empresaId equals to DEFAULT_EMPRESA_ID
        defaultPoweSampleShouldBeFound("empresaId.equals=" + DEFAULT_EMPRESA_ID);

        // Get all the poweSampleList where empresaId equals to UPDATED_EMPRESA_ID
        defaultPoweSampleShouldNotBeFound("empresaId.equals=" + UPDATED_EMPRESA_ID);
    }

    @Test
    @Transactional
    void getAllPoweSamplesByEmpresaIdIsInShouldWork() throws Exception {
        // Initialize the database
        poweSampleRepository.saveAndFlush(poweSample);

        // Get all the poweSampleList where empresaId in DEFAULT_EMPRESA_ID or UPDATED_EMPRESA_ID
        defaultPoweSampleShouldBeFound("empresaId.in=" + DEFAULT_EMPRESA_ID + "," + UPDATED_EMPRESA_ID);

        // Get all the poweSampleList where empresaId equals to UPDATED_EMPRESA_ID
        defaultPoweSampleShouldNotBeFound("empresaId.in=" + UPDATED_EMPRESA_ID);
    }

    @Test
    @Transactional
    void getAllPoweSamplesByEmpresaIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        poweSampleRepository.saveAndFlush(poweSample);

        // Get all the poweSampleList where empresaId is not null
        defaultPoweSampleShouldBeFound("empresaId.specified=true");

        // Get all the poweSampleList where empresaId is null
        defaultPoweSampleShouldNotBeFound("empresaId.specified=false");
    }

    @Test
    @Transactional
    void getAllPoweSamplesByEmpresaIdContainsSomething() throws Exception {
        // Initialize the database
        poweSampleRepository.saveAndFlush(poweSample);

        // Get all the poweSampleList where empresaId contains DEFAULT_EMPRESA_ID
        defaultPoweSampleShouldBeFound("empresaId.contains=" + DEFAULT_EMPRESA_ID);

        // Get all the poweSampleList where empresaId contains UPDATED_EMPRESA_ID
        defaultPoweSampleShouldNotBeFound("empresaId.contains=" + UPDATED_EMPRESA_ID);
    }

    @Test
    @Transactional
    void getAllPoweSamplesByEmpresaIdNotContainsSomething() throws Exception {
        // Initialize the database
        poweSampleRepository.saveAndFlush(poweSample);

        // Get all the poweSampleList where empresaId does not contain DEFAULT_EMPRESA_ID
        defaultPoweSampleShouldNotBeFound("empresaId.doesNotContain=" + DEFAULT_EMPRESA_ID);

        // Get all the poweSampleList where empresaId does not contain UPDATED_EMPRESA_ID
        defaultPoweSampleShouldBeFound("empresaId.doesNotContain=" + UPDATED_EMPRESA_ID);
    }

    @Test
    @Transactional
    void getAllPoweSamplesByVatiosIsEqualToSomething() throws Exception {
        // Initialize the database
        poweSampleRepository.saveAndFlush(poweSample);

        // Get all the poweSampleList where vatios equals to DEFAULT_VATIOS
        defaultPoweSampleShouldBeFound("vatios.equals=" + DEFAULT_VATIOS);

        // Get all the poweSampleList where vatios equals to UPDATED_VATIOS
        defaultPoweSampleShouldNotBeFound("vatios.equals=" + UPDATED_VATIOS);
    }

    @Test
    @Transactional
    void getAllPoweSamplesByVatiosIsInShouldWork() throws Exception {
        // Initialize the database
        poweSampleRepository.saveAndFlush(poweSample);

        // Get all the poweSampleList where vatios in DEFAULT_VATIOS or UPDATED_VATIOS
        defaultPoweSampleShouldBeFound("vatios.in=" + DEFAULT_VATIOS + "," + UPDATED_VATIOS);

        // Get all the poweSampleList where vatios equals to UPDATED_VATIOS
        defaultPoweSampleShouldNotBeFound("vatios.in=" + UPDATED_VATIOS);
    }

    @Test
    @Transactional
    void getAllPoweSamplesByVatiosIsNullOrNotNull() throws Exception {
        // Initialize the database
        poweSampleRepository.saveAndFlush(poweSample);

        // Get all the poweSampleList where vatios is not null
        defaultPoweSampleShouldBeFound("vatios.specified=true");

        // Get all the poweSampleList where vatios is null
        defaultPoweSampleShouldNotBeFound("vatios.specified=false");
    }

    @Test
    @Transactional
    void getAllPoweSamplesByVatiosIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        poweSampleRepository.saveAndFlush(poweSample);

        // Get all the poweSampleList where vatios is greater than or equal to DEFAULT_VATIOS
        defaultPoweSampleShouldBeFound("vatios.greaterThanOrEqual=" + DEFAULT_VATIOS);

        // Get all the poweSampleList where vatios is greater than or equal to UPDATED_VATIOS
        defaultPoweSampleShouldNotBeFound("vatios.greaterThanOrEqual=" + UPDATED_VATIOS);
    }

    @Test
    @Transactional
    void getAllPoweSamplesByVatiosIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        poweSampleRepository.saveAndFlush(poweSample);

        // Get all the poweSampleList where vatios is less than or equal to DEFAULT_VATIOS
        defaultPoweSampleShouldBeFound("vatios.lessThanOrEqual=" + DEFAULT_VATIOS);

        // Get all the poweSampleList where vatios is less than or equal to SMALLER_VATIOS
        defaultPoweSampleShouldNotBeFound("vatios.lessThanOrEqual=" + SMALLER_VATIOS);
    }

    @Test
    @Transactional
    void getAllPoweSamplesByVatiosIsLessThanSomething() throws Exception {
        // Initialize the database
        poweSampleRepository.saveAndFlush(poweSample);

        // Get all the poweSampleList where vatios is less than DEFAULT_VATIOS
        defaultPoweSampleShouldNotBeFound("vatios.lessThan=" + DEFAULT_VATIOS);

        // Get all the poweSampleList where vatios is less than UPDATED_VATIOS
        defaultPoweSampleShouldBeFound("vatios.lessThan=" + UPDATED_VATIOS);
    }

    @Test
    @Transactional
    void getAllPoweSamplesByVatiosIsGreaterThanSomething() throws Exception {
        // Initialize the database
        poweSampleRepository.saveAndFlush(poweSample);

        // Get all the poweSampleList where vatios is greater than DEFAULT_VATIOS
        defaultPoweSampleShouldNotBeFound("vatios.greaterThan=" + DEFAULT_VATIOS);

        // Get all the poweSampleList where vatios is greater than SMALLER_VATIOS
        defaultPoweSampleShouldBeFound("vatios.greaterThan=" + SMALLER_VATIOS);
    }

    @Test
    @Transactional
    void getAllPoweSamplesByStartTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        poweSampleRepository.saveAndFlush(poweSample);

        // Get all the poweSampleList where startTime equals to DEFAULT_START_TIME
        defaultPoweSampleShouldBeFound("startTime.equals=" + DEFAULT_START_TIME);

        // Get all the poweSampleList where startTime equals to UPDATED_START_TIME
        defaultPoweSampleShouldNotBeFound("startTime.equals=" + UPDATED_START_TIME);
    }

    @Test
    @Transactional
    void getAllPoweSamplesByStartTimeIsInShouldWork() throws Exception {
        // Initialize the database
        poweSampleRepository.saveAndFlush(poweSample);

        // Get all the poweSampleList where startTime in DEFAULT_START_TIME or UPDATED_START_TIME
        defaultPoweSampleShouldBeFound("startTime.in=" + DEFAULT_START_TIME + "," + UPDATED_START_TIME);

        // Get all the poweSampleList where startTime equals to UPDATED_START_TIME
        defaultPoweSampleShouldNotBeFound("startTime.in=" + UPDATED_START_TIME);
    }

    @Test
    @Transactional
    void getAllPoweSamplesByStartTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        poweSampleRepository.saveAndFlush(poweSample);

        // Get all the poweSampleList where startTime is not null
        defaultPoweSampleShouldBeFound("startTime.specified=true");

        // Get all the poweSampleList where startTime is null
        defaultPoweSampleShouldNotBeFound("startTime.specified=false");
    }

    @Test
    @Transactional
    void getAllPoweSamplesByEndTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        poweSampleRepository.saveAndFlush(poweSample);

        // Get all the poweSampleList where endTime equals to DEFAULT_END_TIME
        defaultPoweSampleShouldBeFound("endTime.equals=" + DEFAULT_END_TIME);

        // Get all the poweSampleList where endTime equals to UPDATED_END_TIME
        defaultPoweSampleShouldNotBeFound("endTime.equals=" + UPDATED_END_TIME);
    }

    @Test
    @Transactional
    void getAllPoweSamplesByEndTimeIsInShouldWork() throws Exception {
        // Initialize the database
        poweSampleRepository.saveAndFlush(poweSample);

        // Get all the poweSampleList where endTime in DEFAULT_END_TIME or UPDATED_END_TIME
        defaultPoweSampleShouldBeFound("endTime.in=" + DEFAULT_END_TIME + "," + UPDATED_END_TIME);

        // Get all the poweSampleList where endTime equals to UPDATED_END_TIME
        defaultPoweSampleShouldNotBeFound("endTime.in=" + UPDATED_END_TIME);
    }

    @Test
    @Transactional
    void getAllPoweSamplesByEndTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        poweSampleRepository.saveAndFlush(poweSample);

        // Get all the poweSampleList where endTime is not null
        defaultPoweSampleShouldBeFound("endTime.specified=true");

        // Get all the poweSampleList where endTime is null
        defaultPoweSampleShouldNotBeFound("endTime.specified=false");
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultPoweSampleShouldBeFound(String filter) throws Exception {
        restPoweSampleMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(poweSample.getId().toString())))
            .andExpect(jsonPath("$.[*].usuarioId").value(hasItem(DEFAULT_USUARIO_ID)))
            .andExpect(jsonPath("$.[*].empresaId").value(hasItem(DEFAULT_EMPRESA_ID)))
            .andExpect(jsonPath("$.[*].vatios").value(hasItem(DEFAULT_VATIOS.doubleValue())))
            .andExpect(jsonPath("$.[*].startTime").value(hasItem(DEFAULT_START_TIME.toString())))
            .andExpect(jsonPath("$.[*].endTime").value(hasItem(DEFAULT_END_TIME.toString())));

        // Check, that the count call also returns 1
        restPoweSampleMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultPoweSampleShouldNotBeFound(String filter) throws Exception {
        restPoweSampleMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restPoweSampleMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingPoweSample() throws Exception {
        // Get the poweSample
        restPoweSampleMockMvc.perform(get(ENTITY_API_URL_ID, UUID.randomUUID().toString())).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingPoweSample() throws Exception {
        // Initialize the database
        poweSampleRepository.saveAndFlush(poweSample);

        int databaseSizeBeforeUpdate = poweSampleRepository.findAll().size();

        // Update the poweSample
        PoweSample updatedPoweSample = poweSampleRepository.findById(poweSample.getId()).get();
        // Disconnect from session so that the updates on updatedPoweSample are not directly saved in db
        em.detach(updatedPoweSample);
        updatedPoweSample
            .usuarioId(UPDATED_USUARIO_ID)
            .empresaId(UPDATED_EMPRESA_ID)
            .vatios(UPDATED_VATIOS)
            .startTime(UPDATED_START_TIME)
            .endTime(UPDATED_END_TIME);

        restPoweSampleMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedPoweSample.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedPoweSample))
            )
            .andExpect(status().isOk());

        // Validate the PoweSample in the database
        List<PoweSample> poweSampleList = poweSampleRepository.findAll();
        assertThat(poweSampleList).hasSize(databaseSizeBeforeUpdate);
        PoweSample testPoweSample = poweSampleList.get(poweSampleList.size() - 1);
        assertThat(testPoweSample.getUsuarioId()).isEqualTo(UPDATED_USUARIO_ID);
        assertThat(testPoweSample.getEmpresaId()).isEqualTo(UPDATED_EMPRESA_ID);
        assertThat(testPoweSample.getVatios()).isEqualTo(UPDATED_VATIOS);
        assertThat(testPoweSample.getStartTime()).isEqualTo(UPDATED_START_TIME);
        assertThat(testPoweSample.getEndTime()).isEqualTo(UPDATED_END_TIME);
    }

    @Test
    @Transactional
    void putNonExistingPoweSample() throws Exception {
        int databaseSizeBeforeUpdate = poweSampleRepository.findAll().size();
        poweSample.setId(UUID.randomUUID());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPoweSampleMockMvc
            .perform(
                put(ENTITY_API_URL_ID, poweSample.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(poweSample))
            )
            .andExpect(status().isBadRequest());

        // Validate the PoweSample in the database
        List<PoweSample> poweSampleList = poweSampleRepository.findAll();
        assertThat(poweSampleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPoweSample() throws Exception {
        int databaseSizeBeforeUpdate = poweSampleRepository.findAll().size();
        poweSample.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPoweSampleMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(poweSample))
            )
            .andExpect(status().isBadRequest());

        // Validate the PoweSample in the database
        List<PoweSample> poweSampleList = poweSampleRepository.findAll();
        assertThat(poweSampleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPoweSample() throws Exception {
        int databaseSizeBeforeUpdate = poweSampleRepository.findAll().size();
        poweSample.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPoweSampleMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(poweSample)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the PoweSample in the database
        List<PoweSample> poweSampleList = poweSampleRepository.findAll();
        assertThat(poweSampleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePoweSampleWithPatch() throws Exception {
        // Initialize the database
        poweSampleRepository.saveAndFlush(poweSample);

        int databaseSizeBeforeUpdate = poweSampleRepository.findAll().size();

        // Update the poweSample using partial update
        PoweSample partialUpdatedPoweSample = new PoweSample();
        partialUpdatedPoweSample.setId(poweSample.getId());

        partialUpdatedPoweSample.usuarioId(UPDATED_USUARIO_ID).startTime(UPDATED_START_TIME);

        restPoweSampleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPoweSample.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPoweSample))
            )
            .andExpect(status().isOk());

        // Validate the PoweSample in the database
        List<PoweSample> poweSampleList = poweSampleRepository.findAll();
        assertThat(poweSampleList).hasSize(databaseSizeBeforeUpdate);
        PoweSample testPoweSample = poweSampleList.get(poweSampleList.size() - 1);
        assertThat(testPoweSample.getUsuarioId()).isEqualTo(UPDATED_USUARIO_ID);
        assertThat(testPoweSample.getEmpresaId()).isEqualTo(DEFAULT_EMPRESA_ID);
        assertThat(testPoweSample.getVatios()).isEqualTo(DEFAULT_VATIOS);
        assertThat(testPoweSample.getStartTime()).isEqualTo(UPDATED_START_TIME);
        assertThat(testPoweSample.getEndTime()).isEqualTo(DEFAULT_END_TIME);
    }

    @Test
    @Transactional
    void fullUpdatePoweSampleWithPatch() throws Exception {
        // Initialize the database
        poweSampleRepository.saveAndFlush(poweSample);

        int databaseSizeBeforeUpdate = poweSampleRepository.findAll().size();

        // Update the poweSample using partial update
        PoweSample partialUpdatedPoweSample = new PoweSample();
        partialUpdatedPoweSample.setId(poweSample.getId());

        partialUpdatedPoweSample
            .usuarioId(UPDATED_USUARIO_ID)
            .empresaId(UPDATED_EMPRESA_ID)
            .vatios(UPDATED_VATIOS)
            .startTime(UPDATED_START_TIME)
            .endTime(UPDATED_END_TIME);

        restPoweSampleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPoweSample.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPoweSample))
            )
            .andExpect(status().isOk());

        // Validate the PoweSample in the database
        List<PoweSample> poweSampleList = poweSampleRepository.findAll();
        assertThat(poweSampleList).hasSize(databaseSizeBeforeUpdate);
        PoweSample testPoweSample = poweSampleList.get(poweSampleList.size() - 1);
        assertThat(testPoweSample.getUsuarioId()).isEqualTo(UPDATED_USUARIO_ID);
        assertThat(testPoweSample.getEmpresaId()).isEqualTo(UPDATED_EMPRESA_ID);
        assertThat(testPoweSample.getVatios()).isEqualTo(UPDATED_VATIOS);
        assertThat(testPoweSample.getStartTime()).isEqualTo(UPDATED_START_TIME);
        assertThat(testPoweSample.getEndTime()).isEqualTo(UPDATED_END_TIME);
    }

    @Test
    @Transactional
    void patchNonExistingPoweSample() throws Exception {
        int databaseSizeBeforeUpdate = poweSampleRepository.findAll().size();
        poweSample.setId(UUID.randomUUID());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPoweSampleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, poweSample.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(poweSample))
            )
            .andExpect(status().isBadRequest());

        // Validate the PoweSample in the database
        List<PoweSample> poweSampleList = poweSampleRepository.findAll();
        assertThat(poweSampleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPoweSample() throws Exception {
        int databaseSizeBeforeUpdate = poweSampleRepository.findAll().size();
        poweSample.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPoweSampleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(poweSample))
            )
            .andExpect(status().isBadRequest());

        // Validate the PoweSample in the database
        List<PoweSample> poweSampleList = poweSampleRepository.findAll();
        assertThat(poweSampleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPoweSample() throws Exception {
        int databaseSizeBeforeUpdate = poweSampleRepository.findAll().size();
        poweSample.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPoweSampleMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(poweSample))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PoweSample in the database
        List<PoweSample> poweSampleList = poweSampleRepository.findAll();
        assertThat(poweSampleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePoweSample() throws Exception {
        // Initialize the database
        poweSampleRepository.saveAndFlush(poweSample);

        int databaseSizeBeforeDelete = poweSampleRepository.findAll().size();

        // Delete the poweSample
        restPoweSampleMockMvc
            .perform(delete(ENTITY_API_URL_ID, poweSample.getId().toString()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PoweSample> poweSampleList = poweSampleRepository.findAll();
        assertThat(poweSampleList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
