package com.be4tech.b4carecollect.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.be4tech.b4carecollect.IntegrationTest;
import com.be4tech.b4carecollect.domain.DistanceDelta;
import com.be4tech.b4carecollect.repository.DistanceDeltaRepository;
import com.be4tech.b4carecollect.service.criteria.DistanceDeltaCriteria;
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
 * Integration tests for the {@link DistanceDeltaResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class DistanceDeltaResourceIT {

    private static final String DEFAULT_USUARIO_ID = "AAAAAAAAAA";
    private static final String UPDATED_USUARIO_ID = "BBBBBBBBBB";

    private static final String DEFAULT_EMPRESA_ID = "AAAAAAAAAA";
    private static final String UPDATED_EMPRESA_ID = "BBBBBBBBBB";

    private static final Integer DEFAULT_DISTANCE = 1;
    private static final Integer UPDATED_DISTANCE = 2;
    private static final Integer SMALLER_DISTANCE = 1 - 1;

    private static final Instant DEFAULT_START_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_START_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_END_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_END_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/distance-deltas";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private DistanceDeltaRepository distanceDeltaRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDistanceDeltaMockMvc;

    private DistanceDelta distanceDelta;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DistanceDelta createEntity(EntityManager em) {
        DistanceDelta distanceDelta = new DistanceDelta()
            .usuarioId(DEFAULT_USUARIO_ID)
            .empresaId(DEFAULT_EMPRESA_ID)
            .distance(DEFAULT_DISTANCE)
            .startTime(DEFAULT_START_TIME)
            .endTime(DEFAULT_END_TIME);
        return distanceDelta;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DistanceDelta createUpdatedEntity(EntityManager em) {
        DistanceDelta distanceDelta = new DistanceDelta()
            .usuarioId(UPDATED_USUARIO_ID)
            .empresaId(UPDATED_EMPRESA_ID)
            .distance(UPDATED_DISTANCE)
            .startTime(UPDATED_START_TIME)
            .endTime(UPDATED_END_TIME);
        return distanceDelta;
    }

    @BeforeEach
    public void initTest() {
        distanceDelta = createEntity(em);
    }

    @Test
    @Transactional
    void createDistanceDelta() throws Exception {
        int databaseSizeBeforeCreate = distanceDeltaRepository.findAll().size();
        // Create the DistanceDelta
        restDistanceDeltaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(distanceDelta)))
            .andExpect(status().isCreated());

        // Validate the DistanceDelta in the database
        List<DistanceDelta> distanceDeltaList = distanceDeltaRepository.findAll();
        assertThat(distanceDeltaList).hasSize(databaseSizeBeforeCreate + 1);
        DistanceDelta testDistanceDelta = distanceDeltaList.get(distanceDeltaList.size() - 1);
        assertThat(testDistanceDelta.getUsuarioId()).isEqualTo(DEFAULT_USUARIO_ID);
        assertThat(testDistanceDelta.getEmpresaId()).isEqualTo(DEFAULT_EMPRESA_ID);
        assertThat(testDistanceDelta.getDistance()).isEqualTo(DEFAULT_DISTANCE);
        assertThat(testDistanceDelta.getStartTime()).isEqualTo(DEFAULT_START_TIME);
        assertThat(testDistanceDelta.getEndTime()).isEqualTo(DEFAULT_END_TIME);
    }

    @Test
    @Transactional
    void createDistanceDeltaWithExistingId() throws Exception {
        // Create the DistanceDelta with an existing ID
        distanceDeltaRepository.saveAndFlush(distanceDelta);

        int databaseSizeBeforeCreate = distanceDeltaRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDistanceDeltaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(distanceDelta)))
            .andExpect(status().isBadRequest());

        // Validate the DistanceDelta in the database
        List<DistanceDelta> distanceDeltaList = distanceDeltaRepository.findAll();
        assertThat(distanceDeltaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllDistanceDeltas() throws Exception {
        // Initialize the database
        distanceDeltaRepository.saveAndFlush(distanceDelta);

        // Get all the distanceDeltaList
        restDistanceDeltaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(distanceDelta.getId().toString())))
            .andExpect(jsonPath("$.[*].usuarioId").value(hasItem(DEFAULT_USUARIO_ID)))
            .andExpect(jsonPath("$.[*].empresaId").value(hasItem(DEFAULT_EMPRESA_ID)))
            .andExpect(jsonPath("$.[*].distance").value(hasItem(DEFAULT_DISTANCE)))
            .andExpect(jsonPath("$.[*].startTime").value(hasItem(DEFAULT_START_TIME.toString())))
            .andExpect(jsonPath("$.[*].endTime").value(hasItem(DEFAULT_END_TIME.toString())));
    }

    @Test
    @Transactional
    void getDistanceDelta() throws Exception {
        // Initialize the database
        distanceDeltaRepository.saveAndFlush(distanceDelta);

        // Get the distanceDelta
        restDistanceDeltaMockMvc
            .perform(get(ENTITY_API_URL_ID, distanceDelta.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(distanceDelta.getId().toString()))
            .andExpect(jsonPath("$.usuarioId").value(DEFAULT_USUARIO_ID))
            .andExpect(jsonPath("$.empresaId").value(DEFAULT_EMPRESA_ID))
            .andExpect(jsonPath("$.distance").value(DEFAULT_DISTANCE))
            .andExpect(jsonPath("$.startTime").value(DEFAULT_START_TIME.toString()))
            .andExpect(jsonPath("$.endTime").value(DEFAULT_END_TIME.toString()));
    }

    @Test
    @Transactional
    void getDistanceDeltasByIdFiltering() throws Exception {
        // Initialize the database
        distanceDeltaRepository.saveAndFlush(distanceDelta);

        UUID id = distanceDelta.getId();

        defaultDistanceDeltaShouldBeFound("id.equals=" + id);
        defaultDistanceDeltaShouldNotBeFound("id.notEquals=" + id);
    }

    @Test
    @Transactional
    void getAllDistanceDeltasByUsuarioIdIsEqualToSomething() throws Exception {
        // Initialize the database
        distanceDeltaRepository.saveAndFlush(distanceDelta);

        // Get all the distanceDeltaList where usuarioId equals to DEFAULT_USUARIO_ID
        defaultDistanceDeltaShouldBeFound("usuarioId.equals=" + DEFAULT_USUARIO_ID);

        // Get all the distanceDeltaList where usuarioId equals to UPDATED_USUARIO_ID
        defaultDistanceDeltaShouldNotBeFound("usuarioId.equals=" + UPDATED_USUARIO_ID);
    }

    @Test
    @Transactional
    void getAllDistanceDeltasByUsuarioIdIsInShouldWork() throws Exception {
        // Initialize the database
        distanceDeltaRepository.saveAndFlush(distanceDelta);

        // Get all the distanceDeltaList where usuarioId in DEFAULT_USUARIO_ID or UPDATED_USUARIO_ID
        defaultDistanceDeltaShouldBeFound("usuarioId.in=" + DEFAULT_USUARIO_ID + "," + UPDATED_USUARIO_ID);

        // Get all the distanceDeltaList where usuarioId equals to UPDATED_USUARIO_ID
        defaultDistanceDeltaShouldNotBeFound("usuarioId.in=" + UPDATED_USUARIO_ID);
    }

    @Test
    @Transactional
    void getAllDistanceDeltasByUsuarioIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        distanceDeltaRepository.saveAndFlush(distanceDelta);

        // Get all the distanceDeltaList where usuarioId is not null
        defaultDistanceDeltaShouldBeFound("usuarioId.specified=true");

        // Get all the distanceDeltaList where usuarioId is null
        defaultDistanceDeltaShouldNotBeFound("usuarioId.specified=false");
    }

    @Test
    @Transactional
    void getAllDistanceDeltasByUsuarioIdContainsSomething() throws Exception {
        // Initialize the database
        distanceDeltaRepository.saveAndFlush(distanceDelta);

        // Get all the distanceDeltaList where usuarioId contains DEFAULT_USUARIO_ID
        defaultDistanceDeltaShouldBeFound("usuarioId.contains=" + DEFAULT_USUARIO_ID);

        // Get all the distanceDeltaList where usuarioId contains UPDATED_USUARIO_ID
        defaultDistanceDeltaShouldNotBeFound("usuarioId.contains=" + UPDATED_USUARIO_ID);
    }

    @Test
    @Transactional
    void getAllDistanceDeltasByUsuarioIdNotContainsSomething() throws Exception {
        // Initialize the database
        distanceDeltaRepository.saveAndFlush(distanceDelta);

        // Get all the distanceDeltaList where usuarioId does not contain DEFAULT_USUARIO_ID
        defaultDistanceDeltaShouldNotBeFound("usuarioId.doesNotContain=" + DEFAULT_USUARIO_ID);

        // Get all the distanceDeltaList where usuarioId does not contain UPDATED_USUARIO_ID
        defaultDistanceDeltaShouldBeFound("usuarioId.doesNotContain=" + UPDATED_USUARIO_ID);
    }

    @Test
    @Transactional
    void getAllDistanceDeltasByEmpresaIdIsEqualToSomething() throws Exception {
        // Initialize the database
        distanceDeltaRepository.saveAndFlush(distanceDelta);

        // Get all the distanceDeltaList where empresaId equals to DEFAULT_EMPRESA_ID
        defaultDistanceDeltaShouldBeFound("empresaId.equals=" + DEFAULT_EMPRESA_ID);

        // Get all the distanceDeltaList where empresaId equals to UPDATED_EMPRESA_ID
        defaultDistanceDeltaShouldNotBeFound("empresaId.equals=" + UPDATED_EMPRESA_ID);
    }

    @Test
    @Transactional
    void getAllDistanceDeltasByEmpresaIdIsInShouldWork() throws Exception {
        // Initialize the database
        distanceDeltaRepository.saveAndFlush(distanceDelta);

        // Get all the distanceDeltaList where empresaId in DEFAULT_EMPRESA_ID or UPDATED_EMPRESA_ID
        defaultDistanceDeltaShouldBeFound("empresaId.in=" + DEFAULT_EMPRESA_ID + "," + UPDATED_EMPRESA_ID);

        // Get all the distanceDeltaList where empresaId equals to UPDATED_EMPRESA_ID
        defaultDistanceDeltaShouldNotBeFound("empresaId.in=" + UPDATED_EMPRESA_ID);
    }

    @Test
    @Transactional
    void getAllDistanceDeltasByEmpresaIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        distanceDeltaRepository.saveAndFlush(distanceDelta);

        // Get all the distanceDeltaList where empresaId is not null
        defaultDistanceDeltaShouldBeFound("empresaId.specified=true");

        // Get all the distanceDeltaList where empresaId is null
        defaultDistanceDeltaShouldNotBeFound("empresaId.specified=false");
    }

    @Test
    @Transactional
    void getAllDistanceDeltasByEmpresaIdContainsSomething() throws Exception {
        // Initialize the database
        distanceDeltaRepository.saveAndFlush(distanceDelta);

        // Get all the distanceDeltaList where empresaId contains DEFAULT_EMPRESA_ID
        defaultDistanceDeltaShouldBeFound("empresaId.contains=" + DEFAULT_EMPRESA_ID);

        // Get all the distanceDeltaList where empresaId contains UPDATED_EMPRESA_ID
        defaultDistanceDeltaShouldNotBeFound("empresaId.contains=" + UPDATED_EMPRESA_ID);
    }

    @Test
    @Transactional
    void getAllDistanceDeltasByEmpresaIdNotContainsSomething() throws Exception {
        // Initialize the database
        distanceDeltaRepository.saveAndFlush(distanceDelta);

        // Get all the distanceDeltaList where empresaId does not contain DEFAULT_EMPRESA_ID
        defaultDistanceDeltaShouldNotBeFound("empresaId.doesNotContain=" + DEFAULT_EMPRESA_ID);

        // Get all the distanceDeltaList where empresaId does not contain UPDATED_EMPRESA_ID
        defaultDistanceDeltaShouldBeFound("empresaId.doesNotContain=" + UPDATED_EMPRESA_ID);
    }

    @Test
    @Transactional
    void getAllDistanceDeltasByDistanceIsEqualToSomething() throws Exception {
        // Initialize the database
        distanceDeltaRepository.saveAndFlush(distanceDelta);

        // Get all the distanceDeltaList where distance equals to DEFAULT_DISTANCE
        defaultDistanceDeltaShouldBeFound("distance.equals=" + DEFAULT_DISTANCE);

        // Get all the distanceDeltaList where distance equals to UPDATED_DISTANCE
        defaultDistanceDeltaShouldNotBeFound("distance.equals=" + UPDATED_DISTANCE);
    }

    @Test
    @Transactional
    void getAllDistanceDeltasByDistanceIsInShouldWork() throws Exception {
        // Initialize the database
        distanceDeltaRepository.saveAndFlush(distanceDelta);

        // Get all the distanceDeltaList where distance in DEFAULT_DISTANCE or UPDATED_DISTANCE
        defaultDistanceDeltaShouldBeFound("distance.in=" + DEFAULT_DISTANCE + "," + UPDATED_DISTANCE);

        // Get all the distanceDeltaList where distance equals to UPDATED_DISTANCE
        defaultDistanceDeltaShouldNotBeFound("distance.in=" + UPDATED_DISTANCE);
    }

    @Test
    @Transactional
    void getAllDistanceDeltasByDistanceIsNullOrNotNull() throws Exception {
        // Initialize the database
        distanceDeltaRepository.saveAndFlush(distanceDelta);

        // Get all the distanceDeltaList where distance is not null
        defaultDistanceDeltaShouldBeFound("distance.specified=true");

        // Get all the distanceDeltaList where distance is null
        defaultDistanceDeltaShouldNotBeFound("distance.specified=false");
    }

    @Test
    @Transactional
    void getAllDistanceDeltasByDistanceIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        distanceDeltaRepository.saveAndFlush(distanceDelta);

        // Get all the distanceDeltaList where distance is greater than or equal to DEFAULT_DISTANCE
        defaultDistanceDeltaShouldBeFound("distance.greaterThanOrEqual=" + DEFAULT_DISTANCE);

        // Get all the distanceDeltaList where distance is greater than or equal to UPDATED_DISTANCE
        defaultDistanceDeltaShouldNotBeFound("distance.greaterThanOrEqual=" + UPDATED_DISTANCE);
    }

    @Test
    @Transactional
    void getAllDistanceDeltasByDistanceIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        distanceDeltaRepository.saveAndFlush(distanceDelta);

        // Get all the distanceDeltaList where distance is less than or equal to DEFAULT_DISTANCE
        defaultDistanceDeltaShouldBeFound("distance.lessThanOrEqual=" + DEFAULT_DISTANCE);

        // Get all the distanceDeltaList where distance is less than or equal to SMALLER_DISTANCE
        defaultDistanceDeltaShouldNotBeFound("distance.lessThanOrEqual=" + SMALLER_DISTANCE);
    }

    @Test
    @Transactional
    void getAllDistanceDeltasByDistanceIsLessThanSomething() throws Exception {
        // Initialize the database
        distanceDeltaRepository.saveAndFlush(distanceDelta);

        // Get all the distanceDeltaList where distance is less than DEFAULT_DISTANCE
        defaultDistanceDeltaShouldNotBeFound("distance.lessThan=" + DEFAULT_DISTANCE);

        // Get all the distanceDeltaList where distance is less than UPDATED_DISTANCE
        defaultDistanceDeltaShouldBeFound("distance.lessThan=" + UPDATED_DISTANCE);
    }

    @Test
    @Transactional
    void getAllDistanceDeltasByDistanceIsGreaterThanSomething() throws Exception {
        // Initialize the database
        distanceDeltaRepository.saveAndFlush(distanceDelta);

        // Get all the distanceDeltaList where distance is greater than DEFAULT_DISTANCE
        defaultDistanceDeltaShouldNotBeFound("distance.greaterThan=" + DEFAULT_DISTANCE);

        // Get all the distanceDeltaList where distance is greater than SMALLER_DISTANCE
        defaultDistanceDeltaShouldBeFound("distance.greaterThan=" + SMALLER_DISTANCE);
    }

    @Test
    @Transactional
    void getAllDistanceDeltasByStartTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        distanceDeltaRepository.saveAndFlush(distanceDelta);

        // Get all the distanceDeltaList where startTime equals to DEFAULT_START_TIME
        defaultDistanceDeltaShouldBeFound("startTime.equals=" + DEFAULT_START_TIME);

        // Get all the distanceDeltaList where startTime equals to UPDATED_START_TIME
        defaultDistanceDeltaShouldNotBeFound("startTime.equals=" + UPDATED_START_TIME);
    }

    @Test
    @Transactional
    void getAllDistanceDeltasByStartTimeIsInShouldWork() throws Exception {
        // Initialize the database
        distanceDeltaRepository.saveAndFlush(distanceDelta);

        // Get all the distanceDeltaList where startTime in DEFAULT_START_TIME or UPDATED_START_TIME
        defaultDistanceDeltaShouldBeFound("startTime.in=" + DEFAULT_START_TIME + "," + UPDATED_START_TIME);

        // Get all the distanceDeltaList where startTime equals to UPDATED_START_TIME
        defaultDistanceDeltaShouldNotBeFound("startTime.in=" + UPDATED_START_TIME);
    }

    @Test
    @Transactional
    void getAllDistanceDeltasByStartTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        distanceDeltaRepository.saveAndFlush(distanceDelta);

        // Get all the distanceDeltaList where startTime is not null
        defaultDistanceDeltaShouldBeFound("startTime.specified=true");

        // Get all the distanceDeltaList where startTime is null
        defaultDistanceDeltaShouldNotBeFound("startTime.specified=false");
    }

    @Test
    @Transactional
    void getAllDistanceDeltasByEndTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        distanceDeltaRepository.saveAndFlush(distanceDelta);

        // Get all the distanceDeltaList where endTime equals to DEFAULT_END_TIME
        defaultDistanceDeltaShouldBeFound("endTime.equals=" + DEFAULT_END_TIME);

        // Get all the distanceDeltaList where endTime equals to UPDATED_END_TIME
        defaultDistanceDeltaShouldNotBeFound("endTime.equals=" + UPDATED_END_TIME);
    }

    @Test
    @Transactional
    void getAllDistanceDeltasByEndTimeIsInShouldWork() throws Exception {
        // Initialize the database
        distanceDeltaRepository.saveAndFlush(distanceDelta);

        // Get all the distanceDeltaList where endTime in DEFAULT_END_TIME or UPDATED_END_TIME
        defaultDistanceDeltaShouldBeFound("endTime.in=" + DEFAULT_END_TIME + "," + UPDATED_END_TIME);

        // Get all the distanceDeltaList where endTime equals to UPDATED_END_TIME
        defaultDistanceDeltaShouldNotBeFound("endTime.in=" + UPDATED_END_TIME);
    }

    @Test
    @Transactional
    void getAllDistanceDeltasByEndTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        distanceDeltaRepository.saveAndFlush(distanceDelta);

        // Get all the distanceDeltaList where endTime is not null
        defaultDistanceDeltaShouldBeFound("endTime.specified=true");

        // Get all the distanceDeltaList where endTime is null
        defaultDistanceDeltaShouldNotBeFound("endTime.specified=false");
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultDistanceDeltaShouldBeFound(String filter) throws Exception {
        restDistanceDeltaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(distanceDelta.getId().toString())))
            .andExpect(jsonPath("$.[*].usuarioId").value(hasItem(DEFAULT_USUARIO_ID)))
            .andExpect(jsonPath("$.[*].empresaId").value(hasItem(DEFAULT_EMPRESA_ID)))
            .andExpect(jsonPath("$.[*].distance").value(hasItem(DEFAULT_DISTANCE)))
            .andExpect(jsonPath("$.[*].startTime").value(hasItem(DEFAULT_START_TIME.toString())))
            .andExpect(jsonPath("$.[*].endTime").value(hasItem(DEFAULT_END_TIME.toString())));

        // Check, that the count call also returns 1
        restDistanceDeltaMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultDistanceDeltaShouldNotBeFound(String filter) throws Exception {
        restDistanceDeltaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restDistanceDeltaMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingDistanceDelta() throws Exception {
        // Get the distanceDelta
        restDistanceDeltaMockMvc.perform(get(ENTITY_API_URL_ID, UUID.randomUUID().toString())).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingDistanceDelta() throws Exception {
        // Initialize the database
        distanceDeltaRepository.saveAndFlush(distanceDelta);

        int databaseSizeBeforeUpdate = distanceDeltaRepository.findAll().size();

        // Update the distanceDelta
        DistanceDelta updatedDistanceDelta = distanceDeltaRepository.findById(distanceDelta.getId()).get();
        // Disconnect from session so that the updates on updatedDistanceDelta are not directly saved in db
        em.detach(updatedDistanceDelta);
        updatedDistanceDelta
            .usuarioId(UPDATED_USUARIO_ID)
            .empresaId(UPDATED_EMPRESA_ID)
            .distance(UPDATED_DISTANCE)
            .startTime(UPDATED_START_TIME)
            .endTime(UPDATED_END_TIME);

        restDistanceDeltaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedDistanceDelta.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedDistanceDelta))
            )
            .andExpect(status().isOk());

        // Validate the DistanceDelta in the database
        List<DistanceDelta> distanceDeltaList = distanceDeltaRepository.findAll();
        assertThat(distanceDeltaList).hasSize(databaseSizeBeforeUpdate);
        DistanceDelta testDistanceDelta = distanceDeltaList.get(distanceDeltaList.size() - 1);
        assertThat(testDistanceDelta.getUsuarioId()).isEqualTo(UPDATED_USUARIO_ID);
        assertThat(testDistanceDelta.getEmpresaId()).isEqualTo(UPDATED_EMPRESA_ID);
        assertThat(testDistanceDelta.getDistance()).isEqualTo(UPDATED_DISTANCE);
        assertThat(testDistanceDelta.getStartTime()).isEqualTo(UPDATED_START_TIME);
        assertThat(testDistanceDelta.getEndTime()).isEqualTo(UPDATED_END_TIME);
    }

    @Test
    @Transactional
    void putNonExistingDistanceDelta() throws Exception {
        int databaseSizeBeforeUpdate = distanceDeltaRepository.findAll().size();
        distanceDelta.setId(UUID.randomUUID());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDistanceDeltaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, distanceDelta.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(distanceDelta))
            )
            .andExpect(status().isBadRequest());

        // Validate the DistanceDelta in the database
        List<DistanceDelta> distanceDeltaList = distanceDeltaRepository.findAll();
        assertThat(distanceDeltaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDistanceDelta() throws Exception {
        int databaseSizeBeforeUpdate = distanceDeltaRepository.findAll().size();
        distanceDelta.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDistanceDeltaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(distanceDelta))
            )
            .andExpect(status().isBadRequest());

        // Validate the DistanceDelta in the database
        List<DistanceDelta> distanceDeltaList = distanceDeltaRepository.findAll();
        assertThat(distanceDeltaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDistanceDelta() throws Exception {
        int databaseSizeBeforeUpdate = distanceDeltaRepository.findAll().size();
        distanceDelta.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDistanceDeltaMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(distanceDelta)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the DistanceDelta in the database
        List<DistanceDelta> distanceDeltaList = distanceDeltaRepository.findAll();
        assertThat(distanceDeltaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDistanceDeltaWithPatch() throws Exception {
        // Initialize the database
        distanceDeltaRepository.saveAndFlush(distanceDelta);

        int databaseSizeBeforeUpdate = distanceDeltaRepository.findAll().size();

        // Update the distanceDelta using partial update
        DistanceDelta partialUpdatedDistanceDelta = new DistanceDelta();
        partialUpdatedDistanceDelta.setId(distanceDelta.getId());

        restDistanceDeltaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDistanceDelta.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDistanceDelta))
            )
            .andExpect(status().isOk());

        // Validate the DistanceDelta in the database
        List<DistanceDelta> distanceDeltaList = distanceDeltaRepository.findAll();
        assertThat(distanceDeltaList).hasSize(databaseSizeBeforeUpdate);
        DistanceDelta testDistanceDelta = distanceDeltaList.get(distanceDeltaList.size() - 1);
        assertThat(testDistanceDelta.getUsuarioId()).isEqualTo(DEFAULT_USUARIO_ID);
        assertThat(testDistanceDelta.getEmpresaId()).isEqualTo(DEFAULT_EMPRESA_ID);
        assertThat(testDistanceDelta.getDistance()).isEqualTo(DEFAULT_DISTANCE);
        assertThat(testDistanceDelta.getStartTime()).isEqualTo(DEFAULT_START_TIME);
        assertThat(testDistanceDelta.getEndTime()).isEqualTo(DEFAULT_END_TIME);
    }

    @Test
    @Transactional
    void fullUpdateDistanceDeltaWithPatch() throws Exception {
        // Initialize the database
        distanceDeltaRepository.saveAndFlush(distanceDelta);

        int databaseSizeBeforeUpdate = distanceDeltaRepository.findAll().size();

        // Update the distanceDelta using partial update
        DistanceDelta partialUpdatedDistanceDelta = new DistanceDelta();
        partialUpdatedDistanceDelta.setId(distanceDelta.getId());

        partialUpdatedDistanceDelta
            .usuarioId(UPDATED_USUARIO_ID)
            .empresaId(UPDATED_EMPRESA_ID)
            .distance(UPDATED_DISTANCE)
            .startTime(UPDATED_START_TIME)
            .endTime(UPDATED_END_TIME);

        restDistanceDeltaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDistanceDelta.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDistanceDelta))
            )
            .andExpect(status().isOk());

        // Validate the DistanceDelta in the database
        List<DistanceDelta> distanceDeltaList = distanceDeltaRepository.findAll();
        assertThat(distanceDeltaList).hasSize(databaseSizeBeforeUpdate);
        DistanceDelta testDistanceDelta = distanceDeltaList.get(distanceDeltaList.size() - 1);
        assertThat(testDistanceDelta.getUsuarioId()).isEqualTo(UPDATED_USUARIO_ID);
        assertThat(testDistanceDelta.getEmpresaId()).isEqualTo(UPDATED_EMPRESA_ID);
        assertThat(testDistanceDelta.getDistance()).isEqualTo(UPDATED_DISTANCE);
        assertThat(testDistanceDelta.getStartTime()).isEqualTo(UPDATED_START_TIME);
        assertThat(testDistanceDelta.getEndTime()).isEqualTo(UPDATED_END_TIME);
    }

    @Test
    @Transactional
    void patchNonExistingDistanceDelta() throws Exception {
        int databaseSizeBeforeUpdate = distanceDeltaRepository.findAll().size();
        distanceDelta.setId(UUID.randomUUID());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDistanceDeltaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, distanceDelta.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(distanceDelta))
            )
            .andExpect(status().isBadRequest());

        // Validate the DistanceDelta in the database
        List<DistanceDelta> distanceDeltaList = distanceDeltaRepository.findAll();
        assertThat(distanceDeltaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDistanceDelta() throws Exception {
        int databaseSizeBeforeUpdate = distanceDeltaRepository.findAll().size();
        distanceDelta.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDistanceDeltaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(distanceDelta))
            )
            .andExpect(status().isBadRequest());

        // Validate the DistanceDelta in the database
        List<DistanceDelta> distanceDeltaList = distanceDeltaRepository.findAll();
        assertThat(distanceDeltaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDistanceDelta() throws Exception {
        int databaseSizeBeforeUpdate = distanceDeltaRepository.findAll().size();
        distanceDelta.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDistanceDeltaMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(distanceDelta))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the DistanceDelta in the database
        List<DistanceDelta> distanceDeltaList = distanceDeltaRepository.findAll();
        assertThat(distanceDeltaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDistanceDelta() throws Exception {
        // Initialize the database
        distanceDeltaRepository.saveAndFlush(distanceDelta);

        int databaseSizeBeforeDelete = distanceDeltaRepository.findAll().size();

        // Delete the distanceDelta
        restDistanceDeltaMockMvc
            .perform(delete(ENTITY_API_URL_ID, distanceDelta.getId().toString()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DistanceDelta> distanceDeltaList = distanceDeltaRepository.findAll();
        assertThat(distanceDeltaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
