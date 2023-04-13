package com.be4tech.b4carecollect.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.be4tech.b4carecollect.IntegrationTest;
import com.be4tech.b4carecollect.domain.NutritionSummary;
import com.be4tech.b4carecollect.repository.NutritionSummaryRepository;
import com.be4tech.b4carecollect.service.criteria.NutritionSummaryCriteria;
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
import org.springframework.util.Base64Utils;

/**
 * Integration tests for the {@link NutritionSummaryResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class NutritionSummaryResourceIT {

    private static final String DEFAULT_USUARIO_ID = "AAAAAAAAAA";
    private static final String UPDATED_USUARIO_ID = "BBBBBBBBBB";

    private static final String DEFAULT_EMPRESA_ID = "AAAAAAAAAA";
    private static final String UPDATED_EMPRESA_ID = "BBBBBBBBBB";

    private static final String DEFAULT_MEAL_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_MEAL_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_NUTRIENT = "AAAAAAAAAA";
    private static final String UPDATED_NUTRIENT = "BBBBBBBBBB";

    private static final Instant DEFAULT_START_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_START_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_END_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_END_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/nutrition-summaries";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private NutritionSummaryRepository nutritionSummaryRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restNutritionSummaryMockMvc;

    private NutritionSummary nutritionSummary;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static NutritionSummary createEntity(EntityManager em) {
        NutritionSummary nutritionSummary = new NutritionSummary()
            .usuarioId(DEFAULT_USUARIO_ID)
            .empresaId(DEFAULT_EMPRESA_ID)
            .mealType(DEFAULT_MEAL_TYPE)
            .nutrient(DEFAULT_NUTRIENT)
            .startTime(DEFAULT_START_TIME)
            .endTime(DEFAULT_END_TIME);
        return nutritionSummary;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static NutritionSummary createUpdatedEntity(EntityManager em) {
        NutritionSummary nutritionSummary = new NutritionSummary()
            .usuarioId(UPDATED_USUARIO_ID)
            .empresaId(UPDATED_EMPRESA_ID)
            .mealType(UPDATED_MEAL_TYPE)
            .nutrient(UPDATED_NUTRIENT)
            .startTime(UPDATED_START_TIME)
            .endTime(UPDATED_END_TIME);
        return nutritionSummary;
    }

    @BeforeEach
    public void initTest() {
        nutritionSummary = createEntity(em);
    }

    @Test
    @Transactional
    void createNutritionSummary() throws Exception {
        int databaseSizeBeforeCreate = nutritionSummaryRepository.findAll().size();
        // Create the NutritionSummary
        restNutritionSummaryMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(nutritionSummary))
            )
            .andExpect(status().isCreated());

        // Validate the NutritionSummary in the database
        List<NutritionSummary> nutritionSummaryList = nutritionSummaryRepository.findAll();
        assertThat(nutritionSummaryList).hasSize(databaseSizeBeforeCreate + 1);
        NutritionSummary testNutritionSummary = nutritionSummaryList.get(nutritionSummaryList.size() - 1);
        assertThat(testNutritionSummary.getUsuarioId()).isEqualTo(DEFAULT_USUARIO_ID);
        assertThat(testNutritionSummary.getEmpresaId()).isEqualTo(DEFAULT_EMPRESA_ID);
        assertThat(testNutritionSummary.getMealType()).isEqualTo(DEFAULT_MEAL_TYPE);
        assertThat(testNutritionSummary.getNutrient()).isEqualTo(DEFAULT_NUTRIENT);
        assertThat(testNutritionSummary.getStartTime()).isEqualTo(DEFAULT_START_TIME);
        assertThat(testNutritionSummary.getEndTime()).isEqualTo(DEFAULT_END_TIME);
    }

    @Test
    @Transactional
    void createNutritionSummaryWithExistingId() throws Exception {
        // Create the NutritionSummary with an existing ID
        nutritionSummaryRepository.saveAndFlush(nutritionSummary);

        int databaseSizeBeforeCreate = nutritionSummaryRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restNutritionSummaryMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(nutritionSummary))
            )
            .andExpect(status().isBadRequest());

        // Validate the NutritionSummary in the database
        List<NutritionSummary> nutritionSummaryList = nutritionSummaryRepository.findAll();
        assertThat(nutritionSummaryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllNutritionSummaries() throws Exception {
        // Initialize the database
        nutritionSummaryRepository.saveAndFlush(nutritionSummary);

        // Get all the nutritionSummaryList
        restNutritionSummaryMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(nutritionSummary.getId().toString())))
            .andExpect(jsonPath("$.[*].usuarioId").value(hasItem(DEFAULT_USUARIO_ID)))
            .andExpect(jsonPath("$.[*].empresaId").value(hasItem(DEFAULT_EMPRESA_ID)))
            .andExpect(jsonPath("$.[*].mealType").value(hasItem(DEFAULT_MEAL_TYPE.toString())))
            .andExpect(jsonPath("$.[*].nutrient").value(hasItem(DEFAULT_NUTRIENT.toString())))
            .andExpect(jsonPath("$.[*].startTime").value(hasItem(DEFAULT_START_TIME.toString())))
            .andExpect(jsonPath("$.[*].endTime").value(hasItem(DEFAULT_END_TIME.toString())));
    }

    @Test
    @Transactional
    void getNutritionSummary() throws Exception {
        // Initialize the database
        nutritionSummaryRepository.saveAndFlush(nutritionSummary);

        // Get the nutritionSummary
        restNutritionSummaryMockMvc
            .perform(get(ENTITY_API_URL_ID, nutritionSummary.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(nutritionSummary.getId().toString()))
            .andExpect(jsonPath("$.usuarioId").value(DEFAULT_USUARIO_ID))
            .andExpect(jsonPath("$.empresaId").value(DEFAULT_EMPRESA_ID))
            .andExpect(jsonPath("$.mealType").value(DEFAULT_MEAL_TYPE.toString()))
            .andExpect(jsonPath("$.nutrient").value(DEFAULT_NUTRIENT.toString()))
            .andExpect(jsonPath("$.startTime").value(DEFAULT_START_TIME.toString()))
            .andExpect(jsonPath("$.endTime").value(DEFAULT_END_TIME.toString()));
    }

    @Test
    @Transactional
    void getNutritionSummariesByIdFiltering() throws Exception {
        // Initialize the database
        nutritionSummaryRepository.saveAndFlush(nutritionSummary);

        UUID id = nutritionSummary.getId();

        defaultNutritionSummaryShouldBeFound("id.equals=" + id);
        defaultNutritionSummaryShouldNotBeFound("id.notEquals=" + id);
    }

    @Test
    @Transactional
    void getAllNutritionSummariesByUsuarioIdIsEqualToSomething() throws Exception {
        // Initialize the database
        nutritionSummaryRepository.saveAndFlush(nutritionSummary);

        // Get all the nutritionSummaryList where usuarioId equals to DEFAULT_USUARIO_ID
        defaultNutritionSummaryShouldBeFound("usuarioId.equals=" + DEFAULT_USUARIO_ID);

        // Get all the nutritionSummaryList where usuarioId equals to UPDATED_USUARIO_ID
        defaultNutritionSummaryShouldNotBeFound("usuarioId.equals=" + UPDATED_USUARIO_ID);
    }

    @Test
    @Transactional
    void getAllNutritionSummariesByUsuarioIdIsInShouldWork() throws Exception {
        // Initialize the database
        nutritionSummaryRepository.saveAndFlush(nutritionSummary);

        // Get all the nutritionSummaryList where usuarioId in DEFAULT_USUARIO_ID or UPDATED_USUARIO_ID
        defaultNutritionSummaryShouldBeFound("usuarioId.in=" + DEFAULT_USUARIO_ID + "," + UPDATED_USUARIO_ID);

        // Get all the nutritionSummaryList where usuarioId equals to UPDATED_USUARIO_ID
        defaultNutritionSummaryShouldNotBeFound("usuarioId.in=" + UPDATED_USUARIO_ID);
    }

    @Test
    @Transactional
    void getAllNutritionSummariesByUsuarioIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        nutritionSummaryRepository.saveAndFlush(nutritionSummary);

        // Get all the nutritionSummaryList where usuarioId is not null
        defaultNutritionSummaryShouldBeFound("usuarioId.specified=true");

        // Get all the nutritionSummaryList where usuarioId is null
        defaultNutritionSummaryShouldNotBeFound("usuarioId.specified=false");
    }

    @Test
    @Transactional
    void getAllNutritionSummariesByUsuarioIdContainsSomething() throws Exception {
        // Initialize the database
        nutritionSummaryRepository.saveAndFlush(nutritionSummary);

        // Get all the nutritionSummaryList where usuarioId contains DEFAULT_USUARIO_ID
        defaultNutritionSummaryShouldBeFound("usuarioId.contains=" + DEFAULT_USUARIO_ID);

        // Get all the nutritionSummaryList where usuarioId contains UPDATED_USUARIO_ID
        defaultNutritionSummaryShouldNotBeFound("usuarioId.contains=" + UPDATED_USUARIO_ID);
    }

    @Test
    @Transactional
    void getAllNutritionSummariesByUsuarioIdNotContainsSomething() throws Exception {
        // Initialize the database
        nutritionSummaryRepository.saveAndFlush(nutritionSummary);

        // Get all the nutritionSummaryList where usuarioId does not contain DEFAULT_USUARIO_ID
        defaultNutritionSummaryShouldNotBeFound("usuarioId.doesNotContain=" + DEFAULT_USUARIO_ID);

        // Get all the nutritionSummaryList where usuarioId does not contain UPDATED_USUARIO_ID
        defaultNutritionSummaryShouldBeFound("usuarioId.doesNotContain=" + UPDATED_USUARIO_ID);
    }

    @Test
    @Transactional
    void getAllNutritionSummariesByEmpresaIdIsEqualToSomething() throws Exception {
        // Initialize the database
        nutritionSummaryRepository.saveAndFlush(nutritionSummary);

        // Get all the nutritionSummaryList where empresaId equals to DEFAULT_EMPRESA_ID
        defaultNutritionSummaryShouldBeFound("empresaId.equals=" + DEFAULT_EMPRESA_ID);

        // Get all the nutritionSummaryList where empresaId equals to UPDATED_EMPRESA_ID
        defaultNutritionSummaryShouldNotBeFound("empresaId.equals=" + UPDATED_EMPRESA_ID);
    }

    @Test
    @Transactional
    void getAllNutritionSummariesByEmpresaIdIsInShouldWork() throws Exception {
        // Initialize the database
        nutritionSummaryRepository.saveAndFlush(nutritionSummary);

        // Get all the nutritionSummaryList where empresaId in DEFAULT_EMPRESA_ID or UPDATED_EMPRESA_ID
        defaultNutritionSummaryShouldBeFound("empresaId.in=" + DEFAULT_EMPRESA_ID + "," + UPDATED_EMPRESA_ID);

        // Get all the nutritionSummaryList where empresaId equals to UPDATED_EMPRESA_ID
        defaultNutritionSummaryShouldNotBeFound("empresaId.in=" + UPDATED_EMPRESA_ID);
    }

    @Test
    @Transactional
    void getAllNutritionSummariesByEmpresaIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        nutritionSummaryRepository.saveAndFlush(nutritionSummary);

        // Get all the nutritionSummaryList where empresaId is not null
        defaultNutritionSummaryShouldBeFound("empresaId.specified=true");

        // Get all the nutritionSummaryList where empresaId is null
        defaultNutritionSummaryShouldNotBeFound("empresaId.specified=false");
    }

    @Test
    @Transactional
    void getAllNutritionSummariesByEmpresaIdContainsSomething() throws Exception {
        // Initialize the database
        nutritionSummaryRepository.saveAndFlush(nutritionSummary);

        // Get all the nutritionSummaryList where empresaId contains DEFAULT_EMPRESA_ID
        defaultNutritionSummaryShouldBeFound("empresaId.contains=" + DEFAULT_EMPRESA_ID);

        // Get all the nutritionSummaryList where empresaId contains UPDATED_EMPRESA_ID
        defaultNutritionSummaryShouldNotBeFound("empresaId.contains=" + UPDATED_EMPRESA_ID);
    }

    @Test
    @Transactional
    void getAllNutritionSummariesByEmpresaIdNotContainsSomething() throws Exception {
        // Initialize the database
        nutritionSummaryRepository.saveAndFlush(nutritionSummary);

        // Get all the nutritionSummaryList where empresaId does not contain DEFAULT_EMPRESA_ID
        defaultNutritionSummaryShouldNotBeFound("empresaId.doesNotContain=" + DEFAULT_EMPRESA_ID);

        // Get all the nutritionSummaryList where empresaId does not contain UPDATED_EMPRESA_ID
        defaultNutritionSummaryShouldBeFound("empresaId.doesNotContain=" + UPDATED_EMPRESA_ID);
    }

    @Test
    @Transactional
    void getAllNutritionSummariesByStartTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        nutritionSummaryRepository.saveAndFlush(nutritionSummary);

        // Get all the nutritionSummaryList where startTime equals to DEFAULT_START_TIME
        defaultNutritionSummaryShouldBeFound("startTime.equals=" + DEFAULT_START_TIME);

        // Get all the nutritionSummaryList where startTime equals to UPDATED_START_TIME
        defaultNutritionSummaryShouldNotBeFound("startTime.equals=" + UPDATED_START_TIME);
    }

    @Test
    @Transactional
    void getAllNutritionSummariesByStartTimeIsInShouldWork() throws Exception {
        // Initialize the database
        nutritionSummaryRepository.saveAndFlush(nutritionSummary);

        // Get all the nutritionSummaryList where startTime in DEFAULT_START_TIME or UPDATED_START_TIME
        defaultNutritionSummaryShouldBeFound("startTime.in=" + DEFAULT_START_TIME + "," + UPDATED_START_TIME);

        // Get all the nutritionSummaryList where startTime equals to UPDATED_START_TIME
        defaultNutritionSummaryShouldNotBeFound("startTime.in=" + UPDATED_START_TIME);
    }

    @Test
    @Transactional
    void getAllNutritionSummariesByStartTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        nutritionSummaryRepository.saveAndFlush(nutritionSummary);

        // Get all the nutritionSummaryList where startTime is not null
        defaultNutritionSummaryShouldBeFound("startTime.specified=true");

        // Get all the nutritionSummaryList where startTime is null
        defaultNutritionSummaryShouldNotBeFound("startTime.specified=false");
    }

    @Test
    @Transactional
    void getAllNutritionSummariesByEndTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        nutritionSummaryRepository.saveAndFlush(nutritionSummary);

        // Get all the nutritionSummaryList where endTime equals to DEFAULT_END_TIME
        defaultNutritionSummaryShouldBeFound("endTime.equals=" + DEFAULT_END_TIME);

        // Get all the nutritionSummaryList where endTime equals to UPDATED_END_TIME
        defaultNutritionSummaryShouldNotBeFound("endTime.equals=" + UPDATED_END_TIME);
    }

    @Test
    @Transactional
    void getAllNutritionSummariesByEndTimeIsInShouldWork() throws Exception {
        // Initialize the database
        nutritionSummaryRepository.saveAndFlush(nutritionSummary);

        // Get all the nutritionSummaryList where endTime in DEFAULT_END_TIME or UPDATED_END_TIME
        defaultNutritionSummaryShouldBeFound("endTime.in=" + DEFAULT_END_TIME + "," + UPDATED_END_TIME);

        // Get all the nutritionSummaryList where endTime equals to UPDATED_END_TIME
        defaultNutritionSummaryShouldNotBeFound("endTime.in=" + UPDATED_END_TIME);
    }

    @Test
    @Transactional
    void getAllNutritionSummariesByEndTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        nutritionSummaryRepository.saveAndFlush(nutritionSummary);

        // Get all the nutritionSummaryList where endTime is not null
        defaultNutritionSummaryShouldBeFound("endTime.specified=true");

        // Get all the nutritionSummaryList where endTime is null
        defaultNutritionSummaryShouldNotBeFound("endTime.specified=false");
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultNutritionSummaryShouldBeFound(String filter) throws Exception {
        restNutritionSummaryMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(nutritionSummary.getId().toString())))
            .andExpect(jsonPath("$.[*].usuarioId").value(hasItem(DEFAULT_USUARIO_ID)))
            .andExpect(jsonPath("$.[*].empresaId").value(hasItem(DEFAULT_EMPRESA_ID)))
            .andExpect(jsonPath("$.[*].mealType").value(hasItem(DEFAULT_MEAL_TYPE.toString())))
            .andExpect(jsonPath("$.[*].nutrient").value(hasItem(DEFAULT_NUTRIENT.toString())))
            .andExpect(jsonPath("$.[*].startTime").value(hasItem(DEFAULT_START_TIME.toString())))
            .andExpect(jsonPath("$.[*].endTime").value(hasItem(DEFAULT_END_TIME.toString())));

        // Check, that the count call also returns 1
        restNutritionSummaryMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultNutritionSummaryShouldNotBeFound(String filter) throws Exception {
        restNutritionSummaryMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restNutritionSummaryMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingNutritionSummary() throws Exception {
        // Get the nutritionSummary
        restNutritionSummaryMockMvc.perform(get(ENTITY_API_URL_ID, UUID.randomUUID().toString())).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingNutritionSummary() throws Exception {
        // Initialize the database
        nutritionSummaryRepository.saveAndFlush(nutritionSummary);

        int databaseSizeBeforeUpdate = nutritionSummaryRepository.findAll().size();

        // Update the nutritionSummary
        NutritionSummary updatedNutritionSummary = nutritionSummaryRepository.findById(nutritionSummary.getId()).get();
        // Disconnect from session so that the updates on updatedNutritionSummary are not directly saved in db
        em.detach(updatedNutritionSummary);
        updatedNutritionSummary
            .usuarioId(UPDATED_USUARIO_ID)
            .empresaId(UPDATED_EMPRESA_ID)
            .mealType(UPDATED_MEAL_TYPE)
            .nutrient(UPDATED_NUTRIENT)
            .startTime(UPDATED_START_TIME)
            .endTime(UPDATED_END_TIME);

        restNutritionSummaryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedNutritionSummary.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedNutritionSummary))
            )
            .andExpect(status().isOk());

        // Validate the NutritionSummary in the database
        List<NutritionSummary> nutritionSummaryList = nutritionSummaryRepository.findAll();
        assertThat(nutritionSummaryList).hasSize(databaseSizeBeforeUpdate);
        NutritionSummary testNutritionSummary = nutritionSummaryList.get(nutritionSummaryList.size() - 1);
        assertThat(testNutritionSummary.getUsuarioId()).isEqualTo(UPDATED_USUARIO_ID);
        assertThat(testNutritionSummary.getEmpresaId()).isEqualTo(UPDATED_EMPRESA_ID);
        assertThat(testNutritionSummary.getMealType()).isEqualTo(UPDATED_MEAL_TYPE);
        assertThat(testNutritionSummary.getNutrient()).isEqualTo(UPDATED_NUTRIENT);
        assertThat(testNutritionSummary.getStartTime()).isEqualTo(UPDATED_START_TIME);
        assertThat(testNutritionSummary.getEndTime()).isEqualTo(UPDATED_END_TIME);
    }

    @Test
    @Transactional
    void putNonExistingNutritionSummary() throws Exception {
        int databaseSizeBeforeUpdate = nutritionSummaryRepository.findAll().size();
        nutritionSummary.setId(UUID.randomUUID());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNutritionSummaryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, nutritionSummary.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(nutritionSummary))
            )
            .andExpect(status().isBadRequest());

        // Validate the NutritionSummary in the database
        List<NutritionSummary> nutritionSummaryList = nutritionSummaryRepository.findAll();
        assertThat(nutritionSummaryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchNutritionSummary() throws Exception {
        int databaseSizeBeforeUpdate = nutritionSummaryRepository.findAll().size();
        nutritionSummary.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNutritionSummaryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(nutritionSummary))
            )
            .andExpect(status().isBadRequest());

        // Validate the NutritionSummary in the database
        List<NutritionSummary> nutritionSummaryList = nutritionSummaryRepository.findAll();
        assertThat(nutritionSummaryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamNutritionSummary() throws Exception {
        int databaseSizeBeforeUpdate = nutritionSummaryRepository.findAll().size();
        nutritionSummary.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNutritionSummaryMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(nutritionSummary))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the NutritionSummary in the database
        List<NutritionSummary> nutritionSummaryList = nutritionSummaryRepository.findAll();
        assertThat(nutritionSummaryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateNutritionSummaryWithPatch() throws Exception {
        // Initialize the database
        nutritionSummaryRepository.saveAndFlush(nutritionSummary);

        int databaseSizeBeforeUpdate = nutritionSummaryRepository.findAll().size();

        // Update the nutritionSummary using partial update
        NutritionSummary partialUpdatedNutritionSummary = new NutritionSummary();
        partialUpdatedNutritionSummary.setId(nutritionSummary.getId());

        partialUpdatedNutritionSummary.empresaId(UPDATED_EMPRESA_ID).mealType(UPDATED_MEAL_TYPE).nutrient(UPDATED_NUTRIENT);

        restNutritionSummaryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNutritionSummary.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedNutritionSummary))
            )
            .andExpect(status().isOk());

        // Validate the NutritionSummary in the database
        List<NutritionSummary> nutritionSummaryList = nutritionSummaryRepository.findAll();
        assertThat(nutritionSummaryList).hasSize(databaseSizeBeforeUpdate);
        NutritionSummary testNutritionSummary = nutritionSummaryList.get(nutritionSummaryList.size() - 1);
        assertThat(testNutritionSummary.getUsuarioId()).isEqualTo(DEFAULT_USUARIO_ID);
        assertThat(testNutritionSummary.getEmpresaId()).isEqualTo(UPDATED_EMPRESA_ID);
        assertThat(testNutritionSummary.getMealType()).isEqualTo(UPDATED_MEAL_TYPE);
        assertThat(testNutritionSummary.getNutrient()).isEqualTo(UPDATED_NUTRIENT);
        assertThat(testNutritionSummary.getStartTime()).isEqualTo(DEFAULT_START_TIME);
        assertThat(testNutritionSummary.getEndTime()).isEqualTo(DEFAULT_END_TIME);
    }

    @Test
    @Transactional
    void fullUpdateNutritionSummaryWithPatch() throws Exception {
        // Initialize the database
        nutritionSummaryRepository.saveAndFlush(nutritionSummary);

        int databaseSizeBeforeUpdate = nutritionSummaryRepository.findAll().size();

        // Update the nutritionSummary using partial update
        NutritionSummary partialUpdatedNutritionSummary = new NutritionSummary();
        partialUpdatedNutritionSummary.setId(nutritionSummary.getId());

        partialUpdatedNutritionSummary
            .usuarioId(UPDATED_USUARIO_ID)
            .empresaId(UPDATED_EMPRESA_ID)
            .mealType(UPDATED_MEAL_TYPE)
            .nutrient(UPDATED_NUTRIENT)
            .startTime(UPDATED_START_TIME)
            .endTime(UPDATED_END_TIME);

        restNutritionSummaryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNutritionSummary.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedNutritionSummary))
            )
            .andExpect(status().isOk());

        // Validate the NutritionSummary in the database
        List<NutritionSummary> nutritionSummaryList = nutritionSummaryRepository.findAll();
        assertThat(nutritionSummaryList).hasSize(databaseSizeBeforeUpdate);
        NutritionSummary testNutritionSummary = nutritionSummaryList.get(nutritionSummaryList.size() - 1);
        assertThat(testNutritionSummary.getUsuarioId()).isEqualTo(UPDATED_USUARIO_ID);
        assertThat(testNutritionSummary.getEmpresaId()).isEqualTo(UPDATED_EMPRESA_ID);
        assertThat(testNutritionSummary.getMealType()).isEqualTo(UPDATED_MEAL_TYPE);
        assertThat(testNutritionSummary.getNutrient()).isEqualTo(UPDATED_NUTRIENT);
        assertThat(testNutritionSummary.getStartTime()).isEqualTo(UPDATED_START_TIME);
        assertThat(testNutritionSummary.getEndTime()).isEqualTo(UPDATED_END_TIME);
    }

    @Test
    @Transactional
    void patchNonExistingNutritionSummary() throws Exception {
        int databaseSizeBeforeUpdate = nutritionSummaryRepository.findAll().size();
        nutritionSummary.setId(UUID.randomUUID());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNutritionSummaryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, nutritionSummary.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(nutritionSummary))
            )
            .andExpect(status().isBadRequest());

        // Validate the NutritionSummary in the database
        List<NutritionSummary> nutritionSummaryList = nutritionSummaryRepository.findAll();
        assertThat(nutritionSummaryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchNutritionSummary() throws Exception {
        int databaseSizeBeforeUpdate = nutritionSummaryRepository.findAll().size();
        nutritionSummary.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNutritionSummaryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(nutritionSummary))
            )
            .andExpect(status().isBadRequest());

        // Validate the NutritionSummary in the database
        List<NutritionSummary> nutritionSummaryList = nutritionSummaryRepository.findAll();
        assertThat(nutritionSummaryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamNutritionSummary() throws Exception {
        int databaseSizeBeforeUpdate = nutritionSummaryRepository.findAll().size();
        nutritionSummary.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNutritionSummaryMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(nutritionSummary))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the NutritionSummary in the database
        List<NutritionSummary> nutritionSummaryList = nutritionSummaryRepository.findAll();
        assertThat(nutritionSummaryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteNutritionSummary() throws Exception {
        // Initialize the database
        nutritionSummaryRepository.saveAndFlush(nutritionSummary);

        int databaseSizeBeforeDelete = nutritionSummaryRepository.findAll().size();

        // Delete the nutritionSummary
        restNutritionSummaryMockMvc
            .perform(delete(ENTITY_API_URL_ID, nutritionSummary.getId().toString()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<NutritionSummary> nutritionSummaryList = nutritionSummaryRepository.findAll();
        assertThat(nutritionSummaryList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
