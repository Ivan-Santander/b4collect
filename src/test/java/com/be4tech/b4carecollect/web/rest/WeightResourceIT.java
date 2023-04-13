package com.be4tech.b4carecollect.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.be4tech.b4carecollect.IntegrationTest;
import com.be4tech.b4carecollect.domain.Weight;
import com.be4tech.b4carecollect.repository.WeightRepository;
import com.be4tech.b4carecollect.service.criteria.WeightCriteria;
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
 * Integration tests for the {@link WeightResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class WeightResourceIT {

    private static final String DEFAULT_USUARIO_ID = "AAAAAAAAAA";
    private static final String UPDATED_USUARIO_ID = "BBBBBBBBBB";

    private static final String DEFAULT_EMPRESA_ID = "AAAAAAAAAA";
    private static final String UPDATED_EMPRESA_ID = "BBBBBBBBBB";

    private static final Float DEFAULT_FIELD_WEIGHT = 1F;
    private static final Float UPDATED_FIELD_WEIGHT = 2F;
    private static final Float SMALLER_FIELD_WEIGHT = 1F - 1F;

    private static final Instant DEFAULT_END_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_END_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/weights";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private WeightRepository weightRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restWeightMockMvc;

    private Weight weight;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Weight createEntity(EntityManager em) {
        Weight weight = new Weight()
            .usuarioId(DEFAULT_USUARIO_ID)
            .empresaId(DEFAULT_EMPRESA_ID)
            .fieldWeight(DEFAULT_FIELD_WEIGHT)
            .endTime(DEFAULT_END_TIME);
        return weight;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Weight createUpdatedEntity(EntityManager em) {
        Weight weight = new Weight()
            .usuarioId(UPDATED_USUARIO_ID)
            .empresaId(UPDATED_EMPRESA_ID)
            .fieldWeight(UPDATED_FIELD_WEIGHT)
            .endTime(UPDATED_END_TIME);
        return weight;
    }

    @BeforeEach
    public void initTest() {
        weight = createEntity(em);
    }

    @Test
    @Transactional
    void createWeight() throws Exception {
        int databaseSizeBeforeCreate = weightRepository.findAll().size();
        // Create the Weight
        restWeightMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(weight)))
            .andExpect(status().isCreated());

        // Validate the Weight in the database
        List<Weight> weightList = weightRepository.findAll();
        assertThat(weightList).hasSize(databaseSizeBeforeCreate + 1);
        Weight testWeight = weightList.get(weightList.size() - 1);
        assertThat(testWeight.getUsuarioId()).isEqualTo(DEFAULT_USUARIO_ID);
        assertThat(testWeight.getEmpresaId()).isEqualTo(DEFAULT_EMPRESA_ID);
        assertThat(testWeight.getFieldWeight()).isEqualTo(DEFAULT_FIELD_WEIGHT);
        assertThat(testWeight.getEndTime()).isEqualTo(DEFAULT_END_TIME);
    }

    @Test
    @Transactional
    void createWeightWithExistingId() throws Exception {
        // Create the Weight with an existing ID
        weightRepository.saveAndFlush(weight);

        int databaseSizeBeforeCreate = weightRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restWeightMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(weight)))
            .andExpect(status().isBadRequest());

        // Validate the Weight in the database
        List<Weight> weightList = weightRepository.findAll();
        assertThat(weightList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllWeights() throws Exception {
        // Initialize the database
        weightRepository.saveAndFlush(weight);

        // Get all the weightList
        restWeightMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(weight.getId().toString())))
            .andExpect(jsonPath("$.[*].usuarioId").value(hasItem(DEFAULT_USUARIO_ID)))
            .andExpect(jsonPath("$.[*].empresaId").value(hasItem(DEFAULT_EMPRESA_ID)))
            .andExpect(jsonPath("$.[*].fieldWeight").value(hasItem(DEFAULT_FIELD_WEIGHT.doubleValue())))
            .andExpect(jsonPath("$.[*].endTime").value(hasItem(DEFAULT_END_TIME.toString())));
    }

    @Test
    @Transactional
    void getWeight() throws Exception {
        // Initialize the database
        weightRepository.saveAndFlush(weight);

        // Get the weight
        restWeightMockMvc
            .perform(get(ENTITY_API_URL_ID, weight.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(weight.getId().toString()))
            .andExpect(jsonPath("$.usuarioId").value(DEFAULT_USUARIO_ID))
            .andExpect(jsonPath("$.empresaId").value(DEFAULT_EMPRESA_ID))
            .andExpect(jsonPath("$.fieldWeight").value(DEFAULT_FIELD_WEIGHT.doubleValue()))
            .andExpect(jsonPath("$.endTime").value(DEFAULT_END_TIME.toString()));
    }

    @Test
    @Transactional
    void getWeightsByIdFiltering() throws Exception {
        // Initialize the database
        weightRepository.saveAndFlush(weight);

        UUID id = weight.getId();

        defaultWeightShouldBeFound("id.equals=" + id);
        defaultWeightShouldNotBeFound("id.notEquals=" + id);
    }

    @Test
    @Transactional
    void getAllWeightsByUsuarioIdIsEqualToSomething() throws Exception {
        // Initialize the database
        weightRepository.saveAndFlush(weight);

        // Get all the weightList where usuarioId equals to DEFAULT_USUARIO_ID
        defaultWeightShouldBeFound("usuarioId.equals=" + DEFAULT_USUARIO_ID);

        // Get all the weightList where usuarioId equals to UPDATED_USUARIO_ID
        defaultWeightShouldNotBeFound("usuarioId.equals=" + UPDATED_USUARIO_ID);
    }

    @Test
    @Transactional
    void getAllWeightsByUsuarioIdIsInShouldWork() throws Exception {
        // Initialize the database
        weightRepository.saveAndFlush(weight);

        // Get all the weightList where usuarioId in DEFAULT_USUARIO_ID or UPDATED_USUARIO_ID
        defaultWeightShouldBeFound("usuarioId.in=" + DEFAULT_USUARIO_ID + "," + UPDATED_USUARIO_ID);

        // Get all the weightList where usuarioId equals to UPDATED_USUARIO_ID
        defaultWeightShouldNotBeFound("usuarioId.in=" + UPDATED_USUARIO_ID);
    }

    @Test
    @Transactional
    void getAllWeightsByUsuarioIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        weightRepository.saveAndFlush(weight);

        // Get all the weightList where usuarioId is not null
        defaultWeightShouldBeFound("usuarioId.specified=true");

        // Get all the weightList where usuarioId is null
        defaultWeightShouldNotBeFound("usuarioId.specified=false");
    }

    @Test
    @Transactional
    void getAllWeightsByUsuarioIdContainsSomething() throws Exception {
        // Initialize the database
        weightRepository.saveAndFlush(weight);

        // Get all the weightList where usuarioId contains DEFAULT_USUARIO_ID
        defaultWeightShouldBeFound("usuarioId.contains=" + DEFAULT_USUARIO_ID);

        // Get all the weightList where usuarioId contains UPDATED_USUARIO_ID
        defaultWeightShouldNotBeFound("usuarioId.contains=" + UPDATED_USUARIO_ID);
    }

    @Test
    @Transactional
    void getAllWeightsByUsuarioIdNotContainsSomething() throws Exception {
        // Initialize the database
        weightRepository.saveAndFlush(weight);

        // Get all the weightList where usuarioId does not contain DEFAULT_USUARIO_ID
        defaultWeightShouldNotBeFound("usuarioId.doesNotContain=" + DEFAULT_USUARIO_ID);

        // Get all the weightList where usuarioId does not contain UPDATED_USUARIO_ID
        defaultWeightShouldBeFound("usuarioId.doesNotContain=" + UPDATED_USUARIO_ID);
    }

    @Test
    @Transactional
    void getAllWeightsByEmpresaIdIsEqualToSomething() throws Exception {
        // Initialize the database
        weightRepository.saveAndFlush(weight);

        // Get all the weightList where empresaId equals to DEFAULT_EMPRESA_ID
        defaultWeightShouldBeFound("empresaId.equals=" + DEFAULT_EMPRESA_ID);

        // Get all the weightList where empresaId equals to UPDATED_EMPRESA_ID
        defaultWeightShouldNotBeFound("empresaId.equals=" + UPDATED_EMPRESA_ID);
    }

    @Test
    @Transactional
    void getAllWeightsByEmpresaIdIsInShouldWork() throws Exception {
        // Initialize the database
        weightRepository.saveAndFlush(weight);

        // Get all the weightList where empresaId in DEFAULT_EMPRESA_ID or UPDATED_EMPRESA_ID
        defaultWeightShouldBeFound("empresaId.in=" + DEFAULT_EMPRESA_ID + "," + UPDATED_EMPRESA_ID);

        // Get all the weightList where empresaId equals to UPDATED_EMPRESA_ID
        defaultWeightShouldNotBeFound("empresaId.in=" + UPDATED_EMPRESA_ID);
    }

    @Test
    @Transactional
    void getAllWeightsByEmpresaIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        weightRepository.saveAndFlush(weight);

        // Get all the weightList where empresaId is not null
        defaultWeightShouldBeFound("empresaId.specified=true");

        // Get all the weightList where empresaId is null
        defaultWeightShouldNotBeFound("empresaId.specified=false");
    }

    @Test
    @Transactional
    void getAllWeightsByEmpresaIdContainsSomething() throws Exception {
        // Initialize the database
        weightRepository.saveAndFlush(weight);

        // Get all the weightList where empresaId contains DEFAULT_EMPRESA_ID
        defaultWeightShouldBeFound("empresaId.contains=" + DEFAULT_EMPRESA_ID);

        // Get all the weightList where empresaId contains UPDATED_EMPRESA_ID
        defaultWeightShouldNotBeFound("empresaId.contains=" + UPDATED_EMPRESA_ID);
    }

    @Test
    @Transactional
    void getAllWeightsByEmpresaIdNotContainsSomething() throws Exception {
        // Initialize the database
        weightRepository.saveAndFlush(weight);

        // Get all the weightList where empresaId does not contain DEFAULT_EMPRESA_ID
        defaultWeightShouldNotBeFound("empresaId.doesNotContain=" + DEFAULT_EMPRESA_ID);

        // Get all the weightList where empresaId does not contain UPDATED_EMPRESA_ID
        defaultWeightShouldBeFound("empresaId.doesNotContain=" + UPDATED_EMPRESA_ID);
    }

    @Test
    @Transactional
    void getAllWeightsByFieldWeightIsEqualToSomething() throws Exception {
        // Initialize the database
        weightRepository.saveAndFlush(weight);

        // Get all the weightList where fieldWeight equals to DEFAULT_FIELD_WEIGHT
        defaultWeightShouldBeFound("fieldWeight.equals=" + DEFAULT_FIELD_WEIGHT);

        // Get all the weightList where fieldWeight equals to UPDATED_FIELD_WEIGHT
        defaultWeightShouldNotBeFound("fieldWeight.equals=" + UPDATED_FIELD_WEIGHT);
    }

    @Test
    @Transactional
    void getAllWeightsByFieldWeightIsInShouldWork() throws Exception {
        // Initialize the database
        weightRepository.saveAndFlush(weight);

        // Get all the weightList where fieldWeight in DEFAULT_FIELD_WEIGHT or UPDATED_FIELD_WEIGHT
        defaultWeightShouldBeFound("fieldWeight.in=" + DEFAULT_FIELD_WEIGHT + "," + UPDATED_FIELD_WEIGHT);

        // Get all the weightList where fieldWeight equals to UPDATED_FIELD_WEIGHT
        defaultWeightShouldNotBeFound("fieldWeight.in=" + UPDATED_FIELD_WEIGHT);
    }

    @Test
    @Transactional
    void getAllWeightsByFieldWeightIsNullOrNotNull() throws Exception {
        // Initialize the database
        weightRepository.saveAndFlush(weight);

        // Get all the weightList where fieldWeight is not null
        defaultWeightShouldBeFound("fieldWeight.specified=true");

        // Get all the weightList where fieldWeight is null
        defaultWeightShouldNotBeFound("fieldWeight.specified=false");
    }

    @Test
    @Transactional
    void getAllWeightsByFieldWeightIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        weightRepository.saveAndFlush(weight);

        // Get all the weightList where fieldWeight is greater than or equal to DEFAULT_FIELD_WEIGHT
        defaultWeightShouldBeFound("fieldWeight.greaterThanOrEqual=" + DEFAULT_FIELD_WEIGHT);

        // Get all the weightList where fieldWeight is greater than or equal to UPDATED_FIELD_WEIGHT
        defaultWeightShouldNotBeFound("fieldWeight.greaterThanOrEqual=" + UPDATED_FIELD_WEIGHT);
    }

    @Test
    @Transactional
    void getAllWeightsByFieldWeightIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        weightRepository.saveAndFlush(weight);

        // Get all the weightList where fieldWeight is less than or equal to DEFAULT_FIELD_WEIGHT
        defaultWeightShouldBeFound("fieldWeight.lessThanOrEqual=" + DEFAULT_FIELD_WEIGHT);

        // Get all the weightList where fieldWeight is less than or equal to SMALLER_FIELD_WEIGHT
        defaultWeightShouldNotBeFound("fieldWeight.lessThanOrEqual=" + SMALLER_FIELD_WEIGHT);
    }

    @Test
    @Transactional
    void getAllWeightsByFieldWeightIsLessThanSomething() throws Exception {
        // Initialize the database
        weightRepository.saveAndFlush(weight);

        // Get all the weightList where fieldWeight is less than DEFAULT_FIELD_WEIGHT
        defaultWeightShouldNotBeFound("fieldWeight.lessThan=" + DEFAULT_FIELD_WEIGHT);

        // Get all the weightList where fieldWeight is less than UPDATED_FIELD_WEIGHT
        defaultWeightShouldBeFound("fieldWeight.lessThan=" + UPDATED_FIELD_WEIGHT);
    }

    @Test
    @Transactional
    void getAllWeightsByFieldWeightIsGreaterThanSomething() throws Exception {
        // Initialize the database
        weightRepository.saveAndFlush(weight);

        // Get all the weightList where fieldWeight is greater than DEFAULT_FIELD_WEIGHT
        defaultWeightShouldNotBeFound("fieldWeight.greaterThan=" + DEFAULT_FIELD_WEIGHT);

        // Get all the weightList where fieldWeight is greater than SMALLER_FIELD_WEIGHT
        defaultWeightShouldBeFound("fieldWeight.greaterThan=" + SMALLER_FIELD_WEIGHT);
    }

    @Test
    @Transactional
    void getAllWeightsByEndTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        weightRepository.saveAndFlush(weight);

        // Get all the weightList where endTime equals to DEFAULT_END_TIME
        defaultWeightShouldBeFound("endTime.equals=" + DEFAULT_END_TIME);

        // Get all the weightList where endTime equals to UPDATED_END_TIME
        defaultWeightShouldNotBeFound("endTime.equals=" + UPDATED_END_TIME);
    }

    @Test
    @Transactional
    void getAllWeightsByEndTimeIsInShouldWork() throws Exception {
        // Initialize the database
        weightRepository.saveAndFlush(weight);

        // Get all the weightList where endTime in DEFAULT_END_TIME or UPDATED_END_TIME
        defaultWeightShouldBeFound("endTime.in=" + DEFAULT_END_TIME + "," + UPDATED_END_TIME);

        // Get all the weightList where endTime equals to UPDATED_END_TIME
        defaultWeightShouldNotBeFound("endTime.in=" + UPDATED_END_TIME);
    }

    @Test
    @Transactional
    void getAllWeightsByEndTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        weightRepository.saveAndFlush(weight);

        // Get all the weightList where endTime is not null
        defaultWeightShouldBeFound("endTime.specified=true");

        // Get all the weightList where endTime is null
        defaultWeightShouldNotBeFound("endTime.specified=false");
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultWeightShouldBeFound(String filter) throws Exception {
        restWeightMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(weight.getId().toString())))
            .andExpect(jsonPath("$.[*].usuarioId").value(hasItem(DEFAULT_USUARIO_ID)))
            .andExpect(jsonPath("$.[*].empresaId").value(hasItem(DEFAULT_EMPRESA_ID)))
            .andExpect(jsonPath("$.[*].fieldWeight").value(hasItem(DEFAULT_FIELD_WEIGHT.doubleValue())))
            .andExpect(jsonPath("$.[*].endTime").value(hasItem(DEFAULT_END_TIME.toString())));

        // Check, that the count call also returns 1
        restWeightMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultWeightShouldNotBeFound(String filter) throws Exception {
        restWeightMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restWeightMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingWeight() throws Exception {
        // Get the weight
        restWeightMockMvc.perform(get(ENTITY_API_URL_ID, UUID.randomUUID().toString())).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingWeight() throws Exception {
        // Initialize the database
        weightRepository.saveAndFlush(weight);

        int databaseSizeBeforeUpdate = weightRepository.findAll().size();

        // Update the weight
        Weight updatedWeight = weightRepository.findById(weight.getId()).get();
        // Disconnect from session so that the updates on updatedWeight are not directly saved in db
        em.detach(updatedWeight);
        updatedWeight
            .usuarioId(UPDATED_USUARIO_ID)
            .empresaId(UPDATED_EMPRESA_ID)
            .fieldWeight(UPDATED_FIELD_WEIGHT)
            .endTime(UPDATED_END_TIME);

        restWeightMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedWeight.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedWeight))
            )
            .andExpect(status().isOk());

        // Validate the Weight in the database
        List<Weight> weightList = weightRepository.findAll();
        assertThat(weightList).hasSize(databaseSizeBeforeUpdate);
        Weight testWeight = weightList.get(weightList.size() - 1);
        assertThat(testWeight.getUsuarioId()).isEqualTo(UPDATED_USUARIO_ID);
        assertThat(testWeight.getEmpresaId()).isEqualTo(UPDATED_EMPRESA_ID);
        assertThat(testWeight.getFieldWeight()).isEqualTo(UPDATED_FIELD_WEIGHT);
        assertThat(testWeight.getEndTime()).isEqualTo(UPDATED_END_TIME);
    }

    @Test
    @Transactional
    void putNonExistingWeight() throws Exception {
        int databaseSizeBeforeUpdate = weightRepository.findAll().size();
        weight.setId(UUID.randomUUID());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWeightMockMvc
            .perform(
                put(ENTITY_API_URL_ID, weight.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(weight))
            )
            .andExpect(status().isBadRequest());

        // Validate the Weight in the database
        List<Weight> weightList = weightRepository.findAll();
        assertThat(weightList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchWeight() throws Exception {
        int databaseSizeBeforeUpdate = weightRepository.findAll().size();
        weight.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWeightMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(weight))
            )
            .andExpect(status().isBadRequest());

        // Validate the Weight in the database
        List<Weight> weightList = weightRepository.findAll();
        assertThat(weightList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamWeight() throws Exception {
        int databaseSizeBeforeUpdate = weightRepository.findAll().size();
        weight.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWeightMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(weight)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Weight in the database
        List<Weight> weightList = weightRepository.findAll();
        assertThat(weightList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateWeightWithPatch() throws Exception {
        // Initialize the database
        weightRepository.saveAndFlush(weight);

        int databaseSizeBeforeUpdate = weightRepository.findAll().size();

        // Update the weight using partial update
        Weight partialUpdatedWeight = new Weight();
        partialUpdatedWeight.setId(weight.getId());

        partialUpdatedWeight.empresaId(UPDATED_EMPRESA_ID);

        restWeightMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedWeight.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedWeight))
            )
            .andExpect(status().isOk());

        // Validate the Weight in the database
        List<Weight> weightList = weightRepository.findAll();
        assertThat(weightList).hasSize(databaseSizeBeforeUpdate);
        Weight testWeight = weightList.get(weightList.size() - 1);
        assertThat(testWeight.getUsuarioId()).isEqualTo(DEFAULT_USUARIO_ID);
        assertThat(testWeight.getEmpresaId()).isEqualTo(UPDATED_EMPRESA_ID);
        assertThat(testWeight.getFieldWeight()).isEqualTo(DEFAULT_FIELD_WEIGHT);
        assertThat(testWeight.getEndTime()).isEqualTo(DEFAULT_END_TIME);
    }

    @Test
    @Transactional
    void fullUpdateWeightWithPatch() throws Exception {
        // Initialize the database
        weightRepository.saveAndFlush(weight);

        int databaseSizeBeforeUpdate = weightRepository.findAll().size();

        // Update the weight using partial update
        Weight partialUpdatedWeight = new Weight();
        partialUpdatedWeight.setId(weight.getId());

        partialUpdatedWeight
            .usuarioId(UPDATED_USUARIO_ID)
            .empresaId(UPDATED_EMPRESA_ID)
            .fieldWeight(UPDATED_FIELD_WEIGHT)
            .endTime(UPDATED_END_TIME);

        restWeightMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedWeight.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedWeight))
            )
            .andExpect(status().isOk());

        // Validate the Weight in the database
        List<Weight> weightList = weightRepository.findAll();
        assertThat(weightList).hasSize(databaseSizeBeforeUpdate);
        Weight testWeight = weightList.get(weightList.size() - 1);
        assertThat(testWeight.getUsuarioId()).isEqualTo(UPDATED_USUARIO_ID);
        assertThat(testWeight.getEmpresaId()).isEqualTo(UPDATED_EMPRESA_ID);
        assertThat(testWeight.getFieldWeight()).isEqualTo(UPDATED_FIELD_WEIGHT);
        assertThat(testWeight.getEndTime()).isEqualTo(UPDATED_END_TIME);
    }

    @Test
    @Transactional
    void patchNonExistingWeight() throws Exception {
        int databaseSizeBeforeUpdate = weightRepository.findAll().size();
        weight.setId(UUID.randomUUID());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWeightMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, weight.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(weight))
            )
            .andExpect(status().isBadRequest());

        // Validate the Weight in the database
        List<Weight> weightList = weightRepository.findAll();
        assertThat(weightList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchWeight() throws Exception {
        int databaseSizeBeforeUpdate = weightRepository.findAll().size();
        weight.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWeightMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(weight))
            )
            .andExpect(status().isBadRequest());

        // Validate the Weight in the database
        List<Weight> weightList = weightRepository.findAll();
        assertThat(weightList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamWeight() throws Exception {
        int databaseSizeBeforeUpdate = weightRepository.findAll().size();
        weight.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWeightMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(weight)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Weight in the database
        List<Weight> weightList = weightRepository.findAll();
        assertThat(weightList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteWeight() throws Exception {
        // Initialize the database
        weightRepository.saveAndFlush(weight);

        int databaseSizeBeforeDelete = weightRepository.findAll().size();

        // Delete the weight
        restWeightMockMvc
            .perform(delete(ENTITY_API_URL_ID, weight.getId().toString()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Weight> weightList = weightRepository.findAll();
        assertThat(weightList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
