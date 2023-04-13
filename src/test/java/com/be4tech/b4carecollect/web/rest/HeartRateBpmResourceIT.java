package com.be4tech.b4carecollect.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.be4tech.b4carecollect.IntegrationTest;
import com.be4tech.b4carecollect.domain.HeartRateBpm;
import com.be4tech.b4carecollect.repository.HeartRateBpmRepository;
import com.be4tech.b4carecollect.service.criteria.HeartRateBpmCriteria;
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
 * Integration tests for the {@link HeartRateBpmResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class HeartRateBpmResourceIT {

    private static final String DEFAULT_USUARIO_ID = "AAAAAAAAAA";
    private static final String UPDATED_USUARIO_ID = "BBBBBBBBBB";

    private static final String DEFAULT_EMPRESA_ID = "AAAAAAAAAA";
    private static final String UPDATED_EMPRESA_ID = "BBBBBBBBBB";

    private static final Float DEFAULT_PPM = 1F;
    private static final Float UPDATED_PPM = 2F;
    private static final Float SMALLER_PPM = 1F - 1F;

    private static final Instant DEFAULT_END_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_END_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/heart-rate-bpms";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private HeartRateBpmRepository heartRateBpmRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restHeartRateBpmMockMvc;

    private HeartRateBpm heartRateBpm;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static HeartRateBpm createEntity(EntityManager em) {
        HeartRateBpm heartRateBpm = new HeartRateBpm()
            .usuarioId(DEFAULT_USUARIO_ID)
            .empresaId(DEFAULT_EMPRESA_ID)
            .ppm(DEFAULT_PPM)
            .endTime(DEFAULT_END_TIME);
        return heartRateBpm;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static HeartRateBpm createUpdatedEntity(EntityManager em) {
        HeartRateBpm heartRateBpm = new HeartRateBpm()
            .usuarioId(UPDATED_USUARIO_ID)
            .empresaId(UPDATED_EMPRESA_ID)
            .ppm(UPDATED_PPM)
            .endTime(UPDATED_END_TIME);
        return heartRateBpm;
    }

    @BeforeEach
    public void initTest() {
        heartRateBpm = createEntity(em);
    }

    @Test
    @Transactional
    void createHeartRateBpm() throws Exception {
        int databaseSizeBeforeCreate = heartRateBpmRepository.findAll().size();
        // Create the HeartRateBpm
        restHeartRateBpmMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(heartRateBpm)))
            .andExpect(status().isCreated());

        // Validate the HeartRateBpm in the database
        List<HeartRateBpm> heartRateBpmList = heartRateBpmRepository.findAll();
        assertThat(heartRateBpmList).hasSize(databaseSizeBeforeCreate + 1);
        HeartRateBpm testHeartRateBpm = heartRateBpmList.get(heartRateBpmList.size() - 1);
        assertThat(testHeartRateBpm.getUsuarioId()).isEqualTo(DEFAULT_USUARIO_ID);
        assertThat(testHeartRateBpm.getEmpresaId()).isEqualTo(DEFAULT_EMPRESA_ID);
        assertThat(testHeartRateBpm.getPpm()).isEqualTo(DEFAULT_PPM);
        assertThat(testHeartRateBpm.getEndTime()).isEqualTo(DEFAULT_END_TIME);
    }

    @Test
    @Transactional
    void createHeartRateBpmWithExistingId() throws Exception {
        // Create the HeartRateBpm with an existing ID
        heartRateBpmRepository.saveAndFlush(heartRateBpm);

        int databaseSizeBeforeCreate = heartRateBpmRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restHeartRateBpmMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(heartRateBpm)))
            .andExpect(status().isBadRequest());

        // Validate the HeartRateBpm in the database
        List<HeartRateBpm> heartRateBpmList = heartRateBpmRepository.findAll();
        assertThat(heartRateBpmList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllHeartRateBpms() throws Exception {
        // Initialize the database
        heartRateBpmRepository.saveAndFlush(heartRateBpm);

        // Get all the heartRateBpmList
        restHeartRateBpmMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(heartRateBpm.getId().toString())))
            .andExpect(jsonPath("$.[*].usuarioId").value(hasItem(DEFAULT_USUARIO_ID)))
            .andExpect(jsonPath("$.[*].empresaId").value(hasItem(DEFAULT_EMPRESA_ID)))
            .andExpect(jsonPath("$.[*].ppm").value(hasItem(DEFAULT_PPM.doubleValue())))
            .andExpect(jsonPath("$.[*].endTime").value(hasItem(DEFAULT_END_TIME.toString())));
    }

    @Test
    @Transactional
    void getHeartRateBpm() throws Exception {
        // Initialize the database
        heartRateBpmRepository.saveAndFlush(heartRateBpm);

        // Get the heartRateBpm
        restHeartRateBpmMockMvc
            .perform(get(ENTITY_API_URL_ID, heartRateBpm.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(heartRateBpm.getId().toString()))
            .andExpect(jsonPath("$.usuarioId").value(DEFAULT_USUARIO_ID))
            .andExpect(jsonPath("$.empresaId").value(DEFAULT_EMPRESA_ID))
            .andExpect(jsonPath("$.ppm").value(DEFAULT_PPM.doubleValue()))
            .andExpect(jsonPath("$.endTime").value(DEFAULT_END_TIME.toString()));
    }

    @Test
    @Transactional
    void getHeartRateBpmsByIdFiltering() throws Exception {
        // Initialize the database
        heartRateBpmRepository.saveAndFlush(heartRateBpm);

        UUID id = heartRateBpm.getId();

        defaultHeartRateBpmShouldBeFound("id.equals=" + id);
        defaultHeartRateBpmShouldNotBeFound("id.notEquals=" + id);
    }

    @Test
    @Transactional
    void getAllHeartRateBpmsByUsuarioIdIsEqualToSomething() throws Exception {
        // Initialize the database
        heartRateBpmRepository.saveAndFlush(heartRateBpm);

        // Get all the heartRateBpmList where usuarioId equals to DEFAULT_USUARIO_ID
        defaultHeartRateBpmShouldBeFound("usuarioId.equals=" + DEFAULT_USUARIO_ID);

        // Get all the heartRateBpmList where usuarioId equals to UPDATED_USUARIO_ID
        defaultHeartRateBpmShouldNotBeFound("usuarioId.equals=" + UPDATED_USUARIO_ID);
    }

    @Test
    @Transactional
    void getAllHeartRateBpmsByUsuarioIdIsInShouldWork() throws Exception {
        // Initialize the database
        heartRateBpmRepository.saveAndFlush(heartRateBpm);

        // Get all the heartRateBpmList where usuarioId in DEFAULT_USUARIO_ID or UPDATED_USUARIO_ID
        defaultHeartRateBpmShouldBeFound("usuarioId.in=" + DEFAULT_USUARIO_ID + "," + UPDATED_USUARIO_ID);

        // Get all the heartRateBpmList where usuarioId equals to UPDATED_USUARIO_ID
        defaultHeartRateBpmShouldNotBeFound("usuarioId.in=" + UPDATED_USUARIO_ID);
    }

    @Test
    @Transactional
    void getAllHeartRateBpmsByUsuarioIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        heartRateBpmRepository.saveAndFlush(heartRateBpm);

        // Get all the heartRateBpmList where usuarioId is not null
        defaultHeartRateBpmShouldBeFound("usuarioId.specified=true");

        // Get all the heartRateBpmList where usuarioId is null
        defaultHeartRateBpmShouldNotBeFound("usuarioId.specified=false");
    }

    @Test
    @Transactional
    void getAllHeartRateBpmsByUsuarioIdContainsSomething() throws Exception {
        // Initialize the database
        heartRateBpmRepository.saveAndFlush(heartRateBpm);

        // Get all the heartRateBpmList where usuarioId contains DEFAULT_USUARIO_ID
        defaultHeartRateBpmShouldBeFound("usuarioId.contains=" + DEFAULT_USUARIO_ID);

        // Get all the heartRateBpmList where usuarioId contains UPDATED_USUARIO_ID
        defaultHeartRateBpmShouldNotBeFound("usuarioId.contains=" + UPDATED_USUARIO_ID);
    }

    @Test
    @Transactional
    void getAllHeartRateBpmsByUsuarioIdNotContainsSomething() throws Exception {
        // Initialize the database
        heartRateBpmRepository.saveAndFlush(heartRateBpm);

        // Get all the heartRateBpmList where usuarioId does not contain DEFAULT_USUARIO_ID
        defaultHeartRateBpmShouldNotBeFound("usuarioId.doesNotContain=" + DEFAULT_USUARIO_ID);

        // Get all the heartRateBpmList where usuarioId does not contain UPDATED_USUARIO_ID
        defaultHeartRateBpmShouldBeFound("usuarioId.doesNotContain=" + UPDATED_USUARIO_ID);
    }

    @Test
    @Transactional
    void getAllHeartRateBpmsByEmpresaIdIsEqualToSomething() throws Exception {
        // Initialize the database
        heartRateBpmRepository.saveAndFlush(heartRateBpm);

        // Get all the heartRateBpmList where empresaId equals to DEFAULT_EMPRESA_ID
        defaultHeartRateBpmShouldBeFound("empresaId.equals=" + DEFAULT_EMPRESA_ID);

        // Get all the heartRateBpmList where empresaId equals to UPDATED_EMPRESA_ID
        defaultHeartRateBpmShouldNotBeFound("empresaId.equals=" + UPDATED_EMPRESA_ID);
    }

    @Test
    @Transactional
    void getAllHeartRateBpmsByEmpresaIdIsInShouldWork() throws Exception {
        // Initialize the database
        heartRateBpmRepository.saveAndFlush(heartRateBpm);

        // Get all the heartRateBpmList where empresaId in DEFAULT_EMPRESA_ID or UPDATED_EMPRESA_ID
        defaultHeartRateBpmShouldBeFound("empresaId.in=" + DEFAULT_EMPRESA_ID + "," + UPDATED_EMPRESA_ID);

        // Get all the heartRateBpmList where empresaId equals to UPDATED_EMPRESA_ID
        defaultHeartRateBpmShouldNotBeFound("empresaId.in=" + UPDATED_EMPRESA_ID);
    }

    @Test
    @Transactional
    void getAllHeartRateBpmsByEmpresaIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        heartRateBpmRepository.saveAndFlush(heartRateBpm);

        // Get all the heartRateBpmList where empresaId is not null
        defaultHeartRateBpmShouldBeFound("empresaId.specified=true");

        // Get all the heartRateBpmList where empresaId is null
        defaultHeartRateBpmShouldNotBeFound("empresaId.specified=false");
    }

    @Test
    @Transactional
    void getAllHeartRateBpmsByEmpresaIdContainsSomething() throws Exception {
        // Initialize the database
        heartRateBpmRepository.saveAndFlush(heartRateBpm);

        // Get all the heartRateBpmList where empresaId contains DEFAULT_EMPRESA_ID
        defaultHeartRateBpmShouldBeFound("empresaId.contains=" + DEFAULT_EMPRESA_ID);

        // Get all the heartRateBpmList where empresaId contains UPDATED_EMPRESA_ID
        defaultHeartRateBpmShouldNotBeFound("empresaId.contains=" + UPDATED_EMPRESA_ID);
    }

    @Test
    @Transactional
    void getAllHeartRateBpmsByEmpresaIdNotContainsSomething() throws Exception {
        // Initialize the database
        heartRateBpmRepository.saveAndFlush(heartRateBpm);

        // Get all the heartRateBpmList where empresaId does not contain DEFAULT_EMPRESA_ID
        defaultHeartRateBpmShouldNotBeFound("empresaId.doesNotContain=" + DEFAULT_EMPRESA_ID);

        // Get all the heartRateBpmList where empresaId does not contain UPDATED_EMPRESA_ID
        defaultHeartRateBpmShouldBeFound("empresaId.doesNotContain=" + UPDATED_EMPRESA_ID);
    }

    @Test
    @Transactional
    void getAllHeartRateBpmsByPpmIsEqualToSomething() throws Exception {
        // Initialize the database
        heartRateBpmRepository.saveAndFlush(heartRateBpm);

        // Get all the heartRateBpmList where ppm equals to DEFAULT_PPM
        defaultHeartRateBpmShouldBeFound("ppm.equals=" + DEFAULT_PPM);

        // Get all the heartRateBpmList where ppm equals to UPDATED_PPM
        defaultHeartRateBpmShouldNotBeFound("ppm.equals=" + UPDATED_PPM);
    }

    @Test
    @Transactional
    void getAllHeartRateBpmsByPpmIsInShouldWork() throws Exception {
        // Initialize the database
        heartRateBpmRepository.saveAndFlush(heartRateBpm);

        // Get all the heartRateBpmList where ppm in DEFAULT_PPM or UPDATED_PPM
        defaultHeartRateBpmShouldBeFound("ppm.in=" + DEFAULT_PPM + "," + UPDATED_PPM);

        // Get all the heartRateBpmList where ppm equals to UPDATED_PPM
        defaultHeartRateBpmShouldNotBeFound("ppm.in=" + UPDATED_PPM);
    }

    @Test
    @Transactional
    void getAllHeartRateBpmsByPpmIsNullOrNotNull() throws Exception {
        // Initialize the database
        heartRateBpmRepository.saveAndFlush(heartRateBpm);

        // Get all the heartRateBpmList where ppm is not null
        defaultHeartRateBpmShouldBeFound("ppm.specified=true");

        // Get all the heartRateBpmList where ppm is null
        defaultHeartRateBpmShouldNotBeFound("ppm.specified=false");
    }

    @Test
    @Transactional
    void getAllHeartRateBpmsByPpmIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        heartRateBpmRepository.saveAndFlush(heartRateBpm);

        // Get all the heartRateBpmList where ppm is greater than or equal to DEFAULT_PPM
        defaultHeartRateBpmShouldBeFound("ppm.greaterThanOrEqual=" + DEFAULT_PPM);

        // Get all the heartRateBpmList where ppm is greater than or equal to UPDATED_PPM
        defaultHeartRateBpmShouldNotBeFound("ppm.greaterThanOrEqual=" + UPDATED_PPM);
    }

    @Test
    @Transactional
    void getAllHeartRateBpmsByPpmIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        heartRateBpmRepository.saveAndFlush(heartRateBpm);

        // Get all the heartRateBpmList where ppm is less than or equal to DEFAULT_PPM
        defaultHeartRateBpmShouldBeFound("ppm.lessThanOrEqual=" + DEFAULT_PPM);

        // Get all the heartRateBpmList where ppm is less than or equal to SMALLER_PPM
        defaultHeartRateBpmShouldNotBeFound("ppm.lessThanOrEqual=" + SMALLER_PPM);
    }

    @Test
    @Transactional
    void getAllHeartRateBpmsByPpmIsLessThanSomething() throws Exception {
        // Initialize the database
        heartRateBpmRepository.saveAndFlush(heartRateBpm);

        // Get all the heartRateBpmList where ppm is less than DEFAULT_PPM
        defaultHeartRateBpmShouldNotBeFound("ppm.lessThan=" + DEFAULT_PPM);

        // Get all the heartRateBpmList where ppm is less than UPDATED_PPM
        defaultHeartRateBpmShouldBeFound("ppm.lessThan=" + UPDATED_PPM);
    }

    @Test
    @Transactional
    void getAllHeartRateBpmsByPpmIsGreaterThanSomething() throws Exception {
        // Initialize the database
        heartRateBpmRepository.saveAndFlush(heartRateBpm);

        // Get all the heartRateBpmList where ppm is greater than DEFAULT_PPM
        defaultHeartRateBpmShouldNotBeFound("ppm.greaterThan=" + DEFAULT_PPM);

        // Get all the heartRateBpmList where ppm is greater than SMALLER_PPM
        defaultHeartRateBpmShouldBeFound("ppm.greaterThan=" + SMALLER_PPM);
    }

    @Test
    @Transactional
    void getAllHeartRateBpmsByEndTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        heartRateBpmRepository.saveAndFlush(heartRateBpm);

        // Get all the heartRateBpmList where endTime equals to DEFAULT_END_TIME
        defaultHeartRateBpmShouldBeFound("endTime.equals=" + DEFAULT_END_TIME);

        // Get all the heartRateBpmList where endTime equals to UPDATED_END_TIME
        defaultHeartRateBpmShouldNotBeFound("endTime.equals=" + UPDATED_END_TIME);
    }

    @Test
    @Transactional
    void getAllHeartRateBpmsByEndTimeIsInShouldWork() throws Exception {
        // Initialize the database
        heartRateBpmRepository.saveAndFlush(heartRateBpm);

        // Get all the heartRateBpmList where endTime in DEFAULT_END_TIME or UPDATED_END_TIME
        defaultHeartRateBpmShouldBeFound("endTime.in=" + DEFAULT_END_TIME + "," + UPDATED_END_TIME);

        // Get all the heartRateBpmList where endTime equals to UPDATED_END_TIME
        defaultHeartRateBpmShouldNotBeFound("endTime.in=" + UPDATED_END_TIME);
    }

    @Test
    @Transactional
    void getAllHeartRateBpmsByEndTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        heartRateBpmRepository.saveAndFlush(heartRateBpm);

        // Get all the heartRateBpmList where endTime is not null
        defaultHeartRateBpmShouldBeFound("endTime.specified=true");

        // Get all the heartRateBpmList where endTime is null
        defaultHeartRateBpmShouldNotBeFound("endTime.specified=false");
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultHeartRateBpmShouldBeFound(String filter) throws Exception {
        restHeartRateBpmMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(heartRateBpm.getId().toString())))
            .andExpect(jsonPath("$.[*].usuarioId").value(hasItem(DEFAULT_USUARIO_ID)))
            .andExpect(jsonPath("$.[*].empresaId").value(hasItem(DEFAULT_EMPRESA_ID)))
            .andExpect(jsonPath("$.[*].ppm").value(hasItem(DEFAULT_PPM.doubleValue())))
            .andExpect(jsonPath("$.[*].endTime").value(hasItem(DEFAULT_END_TIME.toString())));

        // Check, that the count call also returns 1
        restHeartRateBpmMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultHeartRateBpmShouldNotBeFound(String filter) throws Exception {
        restHeartRateBpmMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restHeartRateBpmMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingHeartRateBpm() throws Exception {
        // Get the heartRateBpm
        restHeartRateBpmMockMvc.perform(get(ENTITY_API_URL_ID, UUID.randomUUID().toString())).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingHeartRateBpm() throws Exception {
        // Initialize the database
        heartRateBpmRepository.saveAndFlush(heartRateBpm);

        int databaseSizeBeforeUpdate = heartRateBpmRepository.findAll().size();

        // Update the heartRateBpm
        HeartRateBpm updatedHeartRateBpm = heartRateBpmRepository.findById(heartRateBpm.getId()).get();
        // Disconnect from session so that the updates on updatedHeartRateBpm are not directly saved in db
        em.detach(updatedHeartRateBpm);
        updatedHeartRateBpm.usuarioId(UPDATED_USUARIO_ID).empresaId(UPDATED_EMPRESA_ID).ppm(UPDATED_PPM).endTime(UPDATED_END_TIME);

        restHeartRateBpmMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedHeartRateBpm.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedHeartRateBpm))
            )
            .andExpect(status().isOk());

        // Validate the HeartRateBpm in the database
        List<HeartRateBpm> heartRateBpmList = heartRateBpmRepository.findAll();
        assertThat(heartRateBpmList).hasSize(databaseSizeBeforeUpdate);
        HeartRateBpm testHeartRateBpm = heartRateBpmList.get(heartRateBpmList.size() - 1);
        assertThat(testHeartRateBpm.getUsuarioId()).isEqualTo(UPDATED_USUARIO_ID);
        assertThat(testHeartRateBpm.getEmpresaId()).isEqualTo(UPDATED_EMPRESA_ID);
        assertThat(testHeartRateBpm.getPpm()).isEqualTo(UPDATED_PPM);
        assertThat(testHeartRateBpm.getEndTime()).isEqualTo(UPDATED_END_TIME);
    }

    @Test
    @Transactional
    void putNonExistingHeartRateBpm() throws Exception {
        int databaseSizeBeforeUpdate = heartRateBpmRepository.findAll().size();
        heartRateBpm.setId(UUID.randomUUID());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHeartRateBpmMockMvc
            .perform(
                put(ENTITY_API_URL_ID, heartRateBpm.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(heartRateBpm))
            )
            .andExpect(status().isBadRequest());

        // Validate the HeartRateBpm in the database
        List<HeartRateBpm> heartRateBpmList = heartRateBpmRepository.findAll();
        assertThat(heartRateBpmList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchHeartRateBpm() throws Exception {
        int databaseSizeBeforeUpdate = heartRateBpmRepository.findAll().size();
        heartRateBpm.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHeartRateBpmMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(heartRateBpm))
            )
            .andExpect(status().isBadRequest());

        // Validate the HeartRateBpm in the database
        List<HeartRateBpm> heartRateBpmList = heartRateBpmRepository.findAll();
        assertThat(heartRateBpmList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamHeartRateBpm() throws Exception {
        int databaseSizeBeforeUpdate = heartRateBpmRepository.findAll().size();
        heartRateBpm.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHeartRateBpmMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(heartRateBpm)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the HeartRateBpm in the database
        List<HeartRateBpm> heartRateBpmList = heartRateBpmRepository.findAll();
        assertThat(heartRateBpmList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateHeartRateBpmWithPatch() throws Exception {
        // Initialize the database
        heartRateBpmRepository.saveAndFlush(heartRateBpm);

        int databaseSizeBeforeUpdate = heartRateBpmRepository.findAll().size();

        // Update the heartRateBpm using partial update
        HeartRateBpm partialUpdatedHeartRateBpm = new HeartRateBpm();
        partialUpdatedHeartRateBpm.setId(heartRateBpm.getId());

        partialUpdatedHeartRateBpm.empresaId(UPDATED_EMPRESA_ID).ppm(UPDATED_PPM);

        restHeartRateBpmMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedHeartRateBpm.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedHeartRateBpm))
            )
            .andExpect(status().isOk());

        // Validate the HeartRateBpm in the database
        List<HeartRateBpm> heartRateBpmList = heartRateBpmRepository.findAll();
        assertThat(heartRateBpmList).hasSize(databaseSizeBeforeUpdate);
        HeartRateBpm testHeartRateBpm = heartRateBpmList.get(heartRateBpmList.size() - 1);
        assertThat(testHeartRateBpm.getUsuarioId()).isEqualTo(DEFAULT_USUARIO_ID);
        assertThat(testHeartRateBpm.getEmpresaId()).isEqualTo(UPDATED_EMPRESA_ID);
        assertThat(testHeartRateBpm.getPpm()).isEqualTo(UPDATED_PPM);
        assertThat(testHeartRateBpm.getEndTime()).isEqualTo(DEFAULT_END_TIME);
    }

    @Test
    @Transactional
    void fullUpdateHeartRateBpmWithPatch() throws Exception {
        // Initialize the database
        heartRateBpmRepository.saveAndFlush(heartRateBpm);

        int databaseSizeBeforeUpdate = heartRateBpmRepository.findAll().size();

        // Update the heartRateBpm using partial update
        HeartRateBpm partialUpdatedHeartRateBpm = new HeartRateBpm();
        partialUpdatedHeartRateBpm.setId(heartRateBpm.getId());

        partialUpdatedHeartRateBpm.usuarioId(UPDATED_USUARIO_ID).empresaId(UPDATED_EMPRESA_ID).ppm(UPDATED_PPM).endTime(UPDATED_END_TIME);

        restHeartRateBpmMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedHeartRateBpm.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedHeartRateBpm))
            )
            .andExpect(status().isOk());

        // Validate the HeartRateBpm in the database
        List<HeartRateBpm> heartRateBpmList = heartRateBpmRepository.findAll();
        assertThat(heartRateBpmList).hasSize(databaseSizeBeforeUpdate);
        HeartRateBpm testHeartRateBpm = heartRateBpmList.get(heartRateBpmList.size() - 1);
        assertThat(testHeartRateBpm.getUsuarioId()).isEqualTo(UPDATED_USUARIO_ID);
        assertThat(testHeartRateBpm.getEmpresaId()).isEqualTo(UPDATED_EMPRESA_ID);
        assertThat(testHeartRateBpm.getPpm()).isEqualTo(UPDATED_PPM);
        assertThat(testHeartRateBpm.getEndTime()).isEqualTo(UPDATED_END_TIME);
    }

    @Test
    @Transactional
    void patchNonExistingHeartRateBpm() throws Exception {
        int databaseSizeBeforeUpdate = heartRateBpmRepository.findAll().size();
        heartRateBpm.setId(UUID.randomUUID());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHeartRateBpmMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, heartRateBpm.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(heartRateBpm))
            )
            .andExpect(status().isBadRequest());

        // Validate the HeartRateBpm in the database
        List<HeartRateBpm> heartRateBpmList = heartRateBpmRepository.findAll();
        assertThat(heartRateBpmList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchHeartRateBpm() throws Exception {
        int databaseSizeBeforeUpdate = heartRateBpmRepository.findAll().size();
        heartRateBpm.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHeartRateBpmMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(heartRateBpm))
            )
            .andExpect(status().isBadRequest());

        // Validate the HeartRateBpm in the database
        List<HeartRateBpm> heartRateBpmList = heartRateBpmRepository.findAll();
        assertThat(heartRateBpmList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamHeartRateBpm() throws Exception {
        int databaseSizeBeforeUpdate = heartRateBpmRepository.findAll().size();
        heartRateBpm.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHeartRateBpmMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(heartRateBpm))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the HeartRateBpm in the database
        List<HeartRateBpm> heartRateBpmList = heartRateBpmRepository.findAll();
        assertThat(heartRateBpmList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteHeartRateBpm() throws Exception {
        // Initialize the database
        heartRateBpmRepository.saveAndFlush(heartRateBpm);

        int databaseSizeBeforeDelete = heartRateBpmRepository.findAll().size();

        // Delete the heartRateBpm
        restHeartRateBpmMockMvc
            .perform(delete(ENTITY_API_URL_ID, heartRateBpm.getId().toString()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<HeartRateBpm> heartRateBpmList = heartRateBpmRepository.findAll();
        assertThat(heartRateBpmList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
