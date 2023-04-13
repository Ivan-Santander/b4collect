package com.be4tech.b4carecollect.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.be4tech.b4carecollect.IntegrationTest;
import com.be4tech.b4carecollect.domain.Nutrition;
import com.be4tech.b4carecollect.repository.NutritionRepository;
import com.be4tech.b4carecollect.service.criteria.NutritionCriteria;
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
 * Integration tests for the {@link NutritionResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class NutritionResourceIT {

    private static final String DEFAULT_USUARIO_ID = "AAAAAAAAAA";
    private static final String UPDATED_USUARIO_ID = "BBBBBBBBBB";

    private static final String DEFAULT_EMPRESA_ID = "AAAAAAAAAA";
    private static final String UPDATED_EMPRESA_ID = "BBBBBBBBBB";

    private static final Integer DEFAULT_MEAL_TYPE = 1;
    private static final Integer UPDATED_MEAL_TYPE = 2;
    private static final Integer SMALLER_MEAL_TYPE = 1 - 1;

    private static final String DEFAULT_FOOD = "AAAAAAAAAA";
    private static final String UPDATED_FOOD = "BBBBBBBBBB";

    private static final String DEFAULT_NUTRIENTS = "AAAAAAAAAA";
    private static final String UPDATED_NUTRIENTS = "BBBBBBBBBB";

    private static final Instant DEFAULT_END_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_END_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/nutritions";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private NutritionRepository nutritionRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restNutritionMockMvc;

    private Nutrition nutrition;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Nutrition createEntity(EntityManager em) {
        Nutrition nutrition = new Nutrition()
            .usuarioId(DEFAULT_USUARIO_ID)
            .empresaId(DEFAULT_EMPRESA_ID)
            .mealType(DEFAULT_MEAL_TYPE)
            .food(DEFAULT_FOOD)
            .nutrients(DEFAULT_NUTRIENTS)
            .endTime(DEFAULT_END_TIME);
        return nutrition;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Nutrition createUpdatedEntity(EntityManager em) {
        Nutrition nutrition = new Nutrition()
            .usuarioId(UPDATED_USUARIO_ID)
            .empresaId(UPDATED_EMPRESA_ID)
            .mealType(UPDATED_MEAL_TYPE)
            .food(UPDATED_FOOD)
            .nutrients(UPDATED_NUTRIENTS)
            .endTime(UPDATED_END_TIME);
        return nutrition;
    }

    @BeforeEach
    public void initTest() {
        nutrition = createEntity(em);
    }

    @Test
    @Transactional
    void createNutrition() throws Exception {
        int databaseSizeBeforeCreate = nutritionRepository.findAll().size();
        // Create the Nutrition
        restNutritionMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(nutrition)))
            .andExpect(status().isCreated());

        // Validate the Nutrition in the database
        List<Nutrition> nutritionList = nutritionRepository.findAll();
        assertThat(nutritionList).hasSize(databaseSizeBeforeCreate + 1);
        Nutrition testNutrition = nutritionList.get(nutritionList.size() - 1);
        assertThat(testNutrition.getUsuarioId()).isEqualTo(DEFAULT_USUARIO_ID);
        assertThat(testNutrition.getEmpresaId()).isEqualTo(DEFAULT_EMPRESA_ID);
        assertThat(testNutrition.getMealType()).isEqualTo(DEFAULT_MEAL_TYPE);
        assertThat(testNutrition.getFood()).isEqualTo(DEFAULT_FOOD);
        assertThat(testNutrition.getNutrients()).isEqualTo(DEFAULT_NUTRIENTS);
        assertThat(testNutrition.getEndTime()).isEqualTo(DEFAULT_END_TIME);
    }

    @Test
    @Transactional
    void createNutritionWithExistingId() throws Exception {
        // Create the Nutrition with an existing ID
        nutritionRepository.saveAndFlush(nutrition);

        int databaseSizeBeforeCreate = nutritionRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restNutritionMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(nutrition)))
            .andExpect(status().isBadRequest());

        // Validate the Nutrition in the database
        List<Nutrition> nutritionList = nutritionRepository.findAll();
        assertThat(nutritionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllNutritions() throws Exception {
        // Initialize the database
        nutritionRepository.saveAndFlush(nutrition);

        // Get all the nutritionList
        restNutritionMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(nutrition.getId().toString())))
            .andExpect(jsonPath("$.[*].usuarioId").value(hasItem(DEFAULT_USUARIO_ID)))
            .andExpect(jsonPath("$.[*].empresaId").value(hasItem(DEFAULT_EMPRESA_ID)))
            .andExpect(jsonPath("$.[*].mealType").value(hasItem(DEFAULT_MEAL_TYPE)))
            .andExpect(jsonPath("$.[*].food").value(hasItem(DEFAULT_FOOD.toString())))
            .andExpect(jsonPath("$.[*].nutrients").value(hasItem(DEFAULT_NUTRIENTS.toString())))
            .andExpect(jsonPath("$.[*].endTime").value(hasItem(DEFAULT_END_TIME.toString())));
    }

    @Test
    @Transactional
    void getNutrition() throws Exception {
        // Initialize the database
        nutritionRepository.saveAndFlush(nutrition);

        // Get the nutrition
        restNutritionMockMvc
            .perform(get(ENTITY_API_URL_ID, nutrition.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(nutrition.getId().toString()))
            .andExpect(jsonPath("$.usuarioId").value(DEFAULT_USUARIO_ID))
            .andExpect(jsonPath("$.empresaId").value(DEFAULT_EMPRESA_ID))
            .andExpect(jsonPath("$.mealType").value(DEFAULT_MEAL_TYPE))
            .andExpect(jsonPath("$.food").value(DEFAULT_FOOD.toString()))
            .andExpect(jsonPath("$.nutrients").value(DEFAULT_NUTRIENTS.toString()))
            .andExpect(jsonPath("$.endTime").value(DEFAULT_END_TIME.toString()));
    }

    @Test
    @Transactional
    void getNutritionsByIdFiltering() throws Exception {
        // Initialize the database
        nutritionRepository.saveAndFlush(nutrition);

        UUID id = nutrition.getId();

        defaultNutritionShouldBeFound("id.equals=" + id);
        defaultNutritionShouldNotBeFound("id.notEquals=" + id);
    }

    @Test
    @Transactional
    void getAllNutritionsByUsuarioIdIsEqualToSomething() throws Exception {
        // Initialize the database
        nutritionRepository.saveAndFlush(nutrition);

        // Get all the nutritionList where usuarioId equals to DEFAULT_USUARIO_ID
        defaultNutritionShouldBeFound("usuarioId.equals=" + DEFAULT_USUARIO_ID);

        // Get all the nutritionList where usuarioId equals to UPDATED_USUARIO_ID
        defaultNutritionShouldNotBeFound("usuarioId.equals=" + UPDATED_USUARIO_ID);
    }

    @Test
    @Transactional
    void getAllNutritionsByUsuarioIdIsInShouldWork() throws Exception {
        // Initialize the database
        nutritionRepository.saveAndFlush(nutrition);

        // Get all the nutritionList where usuarioId in DEFAULT_USUARIO_ID or UPDATED_USUARIO_ID
        defaultNutritionShouldBeFound("usuarioId.in=" + DEFAULT_USUARIO_ID + "," + UPDATED_USUARIO_ID);

        // Get all the nutritionList where usuarioId equals to UPDATED_USUARIO_ID
        defaultNutritionShouldNotBeFound("usuarioId.in=" + UPDATED_USUARIO_ID);
    }

    @Test
    @Transactional
    void getAllNutritionsByUsuarioIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        nutritionRepository.saveAndFlush(nutrition);

        // Get all the nutritionList where usuarioId is not null
        defaultNutritionShouldBeFound("usuarioId.specified=true");

        // Get all the nutritionList where usuarioId is null
        defaultNutritionShouldNotBeFound("usuarioId.specified=false");
    }

    @Test
    @Transactional
    void getAllNutritionsByUsuarioIdContainsSomething() throws Exception {
        // Initialize the database
        nutritionRepository.saveAndFlush(nutrition);

        // Get all the nutritionList where usuarioId contains DEFAULT_USUARIO_ID
        defaultNutritionShouldBeFound("usuarioId.contains=" + DEFAULT_USUARIO_ID);

        // Get all the nutritionList where usuarioId contains UPDATED_USUARIO_ID
        defaultNutritionShouldNotBeFound("usuarioId.contains=" + UPDATED_USUARIO_ID);
    }

    @Test
    @Transactional
    void getAllNutritionsByUsuarioIdNotContainsSomething() throws Exception {
        // Initialize the database
        nutritionRepository.saveAndFlush(nutrition);

        // Get all the nutritionList where usuarioId does not contain DEFAULT_USUARIO_ID
        defaultNutritionShouldNotBeFound("usuarioId.doesNotContain=" + DEFAULT_USUARIO_ID);

        // Get all the nutritionList where usuarioId does not contain UPDATED_USUARIO_ID
        defaultNutritionShouldBeFound("usuarioId.doesNotContain=" + UPDATED_USUARIO_ID);
    }

    @Test
    @Transactional
    void getAllNutritionsByEmpresaIdIsEqualToSomething() throws Exception {
        // Initialize the database
        nutritionRepository.saveAndFlush(nutrition);

        // Get all the nutritionList where empresaId equals to DEFAULT_EMPRESA_ID
        defaultNutritionShouldBeFound("empresaId.equals=" + DEFAULT_EMPRESA_ID);

        // Get all the nutritionList where empresaId equals to UPDATED_EMPRESA_ID
        defaultNutritionShouldNotBeFound("empresaId.equals=" + UPDATED_EMPRESA_ID);
    }

    @Test
    @Transactional
    void getAllNutritionsByEmpresaIdIsInShouldWork() throws Exception {
        // Initialize the database
        nutritionRepository.saveAndFlush(nutrition);

        // Get all the nutritionList where empresaId in DEFAULT_EMPRESA_ID or UPDATED_EMPRESA_ID
        defaultNutritionShouldBeFound("empresaId.in=" + DEFAULT_EMPRESA_ID + "," + UPDATED_EMPRESA_ID);

        // Get all the nutritionList where empresaId equals to UPDATED_EMPRESA_ID
        defaultNutritionShouldNotBeFound("empresaId.in=" + UPDATED_EMPRESA_ID);
    }

    @Test
    @Transactional
    void getAllNutritionsByEmpresaIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        nutritionRepository.saveAndFlush(nutrition);

        // Get all the nutritionList where empresaId is not null
        defaultNutritionShouldBeFound("empresaId.specified=true");

        // Get all the nutritionList where empresaId is null
        defaultNutritionShouldNotBeFound("empresaId.specified=false");
    }

    @Test
    @Transactional
    void getAllNutritionsByEmpresaIdContainsSomething() throws Exception {
        // Initialize the database
        nutritionRepository.saveAndFlush(nutrition);

        // Get all the nutritionList where empresaId contains DEFAULT_EMPRESA_ID
        defaultNutritionShouldBeFound("empresaId.contains=" + DEFAULT_EMPRESA_ID);

        // Get all the nutritionList where empresaId contains UPDATED_EMPRESA_ID
        defaultNutritionShouldNotBeFound("empresaId.contains=" + UPDATED_EMPRESA_ID);
    }

    @Test
    @Transactional
    void getAllNutritionsByEmpresaIdNotContainsSomething() throws Exception {
        // Initialize the database
        nutritionRepository.saveAndFlush(nutrition);

        // Get all the nutritionList where empresaId does not contain DEFAULT_EMPRESA_ID
        defaultNutritionShouldNotBeFound("empresaId.doesNotContain=" + DEFAULT_EMPRESA_ID);

        // Get all the nutritionList where empresaId does not contain UPDATED_EMPRESA_ID
        defaultNutritionShouldBeFound("empresaId.doesNotContain=" + UPDATED_EMPRESA_ID);
    }

    @Test
    @Transactional
    void getAllNutritionsByMealTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        nutritionRepository.saveAndFlush(nutrition);

        // Get all the nutritionList where mealType equals to DEFAULT_MEAL_TYPE
        defaultNutritionShouldBeFound("mealType.equals=" + DEFAULT_MEAL_TYPE);

        // Get all the nutritionList where mealType equals to UPDATED_MEAL_TYPE
        defaultNutritionShouldNotBeFound("mealType.equals=" + UPDATED_MEAL_TYPE);
    }

    @Test
    @Transactional
    void getAllNutritionsByMealTypeIsInShouldWork() throws Exception {
        // Initialize the database
        nutritionRepository.saveAndFlush(nutrition);

        // Get all the nutritionList where mealType in DEFAULT_MEAL_TYPE or UPDATED_MEAL_TYPE
        defaultNutritionShouldBeFound("mealType.in=" + DEFAULT_MEAL_TYPE + "," + UPDATED_MEAL_TYPE);

        // Get all the nutritionList where mealType equals to UPDATED_MEAL_TYPE
        defaultNutritionShouldNotBeFound("mealType.in=" + UPDATED_MEAL_TYPE);
    }

    @Test
    @Transactional
    void getAllNutritionsByMealTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        nutritionRepository.saveAndFlush(nutrition);

        // Get all the nutritionList where mealType is not null
        defaultNutritionShouldBeFound("mealType.specified=true");

        // Get all the nutritionList where mealType is null
        defaultNutritionShouldNotBeFound("mealType.specified=false");
    }

    @Test
    @Transactional
    void getAllNutritionsByMealTypeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        nutritionRepository.saveAndFlush(nutrition);

        // Get all the nutritionList where mealType is greater than or equal to DEFAULT_MEAL_TYPE
        defaultNutritionShouldBeFound("mealType.greaterThanOrEqual=" + DEFAULT_MEAL_TYPE);

        // Get all the nutritionList where mealType is greater than or equal to UPDATED_MEAL_TYPE
        defaultNutritionShouldNotBeFound("mealType.greaterThanOrEqual=" + UPDATED_MEAL_TYPE);
    }

    @Test
    @Transactional
    void getAllNutritionsByMealTypeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        nutritionRepository.saveAndFlush(nutrition);

        // Get all the nutritionList where mealType is less than or equal to DEFAULT_MEAL_TYPE
        defaultNutritionShouldBeFound("mealType.lessThanOrEqual=" + DEFAULT_MEAL_TYPE);

        // Get all the nutritionList where mealType is less than or equal to SMALLER_MEAL_TYPE
        defaultNutritionShouldNotBeFound("mealType.lessThanOrEqual=" + SMALLER_MEAL_TYPE);
    }

    @Test
    @Transactional
    void getAllNutritionsByMealTypeIsLessThanSomething() throws Exception {
        // Initialize the database
        nutritionRepository.saveAndFlush(nutrition);

        // Get all the nutritionList where mealType is less than DEFAULT_MEAL_TYPE
        defaultNutritionShouldNotBeFound("mealType.lessThan=" + DEFAULT_MEAL_TYPE);

        // Get all the nutritionList where mealType is less than UPDATED_MEAL_TYPE
        defaultNutritionShouldBeFound("mealType.lessThan=" + UPDATED_MEAL_TYPE);
    }

    @Test
    @Transactional
    void getAllNutritionsByMealTypeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        nutritionRepository.saveAndFlush(nutrition);

        // Get all the nutritionList where mealType is greater than DEFAULT_MEAL_TYPE
        defaultNutritionShouldNotBeFound("mealType.greaterThan=" + DEFAULT_MEAL_TYPE);

        // Get all the nutritionList where mealType is greater than SMALLER_MEAL_TYPE
        defaultNutritionShouldBeFound("mealType.greaterThan=" + SMALLER_MEAL_TYPE);
    }

    @Test
    @Transactional
    void getAllNutritionsByEndTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        nutritionRepository.saveAndFlush(nutrition);

        // Get all the nutritionList where endTime equals to DEFAULT_END_TIME
        defaultNutritionShouldBeFound("endTime.equals=" + DEFAULT_END_TIME);

        // Get all the nutritionList where endTime equals to UPDATED_END_TIME
        defaultNutritionShouldNotBeFound("endTime.equals=" + UPDATED_END_TIME);
    }

    @Test
    @Transactional
    void getAllNutritionsByEndTimeIsInShouldWork() throws Exception {
        // Initialize the database
        nutritionRepository.saveAndFlush(nutrition);

        // Get all the nutritionList where endTime in DEFAULT_END_TIME or UPDATED_END_TIME
        defaultNutritionShouldBeFound("endTime.in=" + DEFAULT_END_TIME + "," + UPDATED_END_TIME);

        // Get all the nutritionList where endTime equals to UPDATED_END_TIME
        defaultNutritionShouldNotBeFound("endTime.in=" + UPDATED_END_TIME);
    }

    @Test
    @Transactional
    void getAllNutritionsByEndTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        nutritionRepository.saveAndFlush(nutrition);

        // Get all the nutritionList where endTime is not null
        defaultNutritionShouldBeFound("endTime.specified=true");

        // Get all the nutritionList where endTime is null
        defaultNutritionShouldNotBeFound("endTime.specified=false");
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultNutritionShouldBeFound(String filter) throws Exception {
        restNutritionMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(nutrition.getId().toString())))
            .andExpect(jsonPath("$.[*].usuarioId").value(hasItem(DEFAULT_USUARIO_ID)))
            .andExpect(jsonPath("$.[*].empresaId").value(hasItem(DEFAULT_EMPRESA_ID)))
            .andExpect(jsonPath("$.[*].mealType").value(hasItem(DEFAULT_MEAL_TYPE)))
            .andExpect(jsonPath("$.[*].food").value(hasItem(DEFAULT_FOOD.toString())))
            .andExpect(jsonPath("$.[*].nutrients").value(hasItem(DEFAULT_NUTRIENTS.toString())))
            .andExpect(jsonPath("$.[*].endTime").value(hasItem(DEFAULT_END_TIME.toString())));

        // Check, that the count call also returns 1
        restNutritionMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultNutritionShouldNotBeFound(String filter) throws Exception {
        restNutritionMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restNutritionMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingNutrition() throws Exception {
        // Get the nutrition
        restNutritionMockMvc.perform(get(ENTITY_API_URL_ID, UUID.randomUUID().toString())).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingNutrition() throws Exception {
        // Initialize the database
        nutritionRepository.saveAndFlush(nutrition);

        int databaseSizeBeforeUpdate = nutritionRepository.findAll().size();

        // Update the nutrition
        Nutrition updatedNutrition = nutritionRepository.findById(nutrition.getId()).get();
        // Disconnect from session so that the updates on updatedNutrition are not directly saved in db
        em.detach(updatedNutrition);
        updatedNutrition
            .usuarioId(UPDATED_USUARIO_ID)
            .empresaId(UPDATED_EMPRESA_ID)
            .mealType(UPDATED_MEAL_TYPE)
            .food(UPDATED_FOOD)
            .nutrients(UPDATED_NUTRIENTS)
            .endTime(UPDATED_END_TIME);

        restNutritionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedNutrition.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedNutrition))
            )
            .andExpect(status().isOk());

        // Validate the Nutrition in the database
        List<Nutrition> nutritionList = nutritionRepository.findAll();
        assertThat(nutritionList).hasSize(databaseSizeBeforeUpdate);
        Nutrition testNutrition = nutritionList.get(nutritionList.size() - 1);
        assertThat(testNutrition.getUsuarioId()).isEqualTo(UPDATED_USUARIO_ID);
        assertThat(testNutrition.getEmpresaId()).isEqualTo(UPDATED_EMPRESA_ID);
        assertThat(testNutrition.getMealType()).isEqualTo(UPDATED_MEAL_TYPE);
        assertThat(testNutrition.getFood()).isEqualTo(UPDATED_FOOD);
        assertThat(testNutrition.getNutrients()).isEqualTo(UPDATED_NUTRIENTS);
        assertThat(testNutrition.getEndTime()).isEqualTo(UPDATED_END_TIME);
    }

    @Test
    @Transactional
    void putNonExistingNutrition() throws Exception {
        int databaseSizeBeforeUpdate = nutritionRepository.findAll().size();
        nutrition.setId(UUID.randomUUID());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNutritionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, nutrition.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(nutrition))
            )
            .andExpect(status().isBadRequest());

        // Validate the Nutrition in the database
        List<Nutrition> nutritionList = nutritionRepository.findAll();
        assertThat(nutritionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchNutrition() throws Exception {
        int databaseSizeBeforeUpdate = nutritionRepository.findAll().size();
        nutrition.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNutritionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(nutrition))
            )
            .andExpect(status().isBadRequest());

        // Validate the Nutrition in the database
        List<Nutrition> nutritionList = nutritionRepository.findAll();
        assertThat(nutritionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamNutrition() throws Exception {
        int databaseSizeBeforeUpdate = nutritionRepository.findAll().size();
        nutrition.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNutritionMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(nutrition)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Nutrition in the database
        List<Nutrition> nutritionList = nutritionRepository.findAll();
        assertThat(nutritionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateNutritionWithPatch() throws Exception {
        // Initialize the database
        nutritionRepository.saveAndFlush(nutrition);

        int databaseSizeBeforeUpdate = nutritionRepository.findAll().size();

        // Update the nutrition using partial update
        Nutrition partialUpdatedNutrition = new Nutrition();
        partialUpdatedNutrition.setId(nutrition.getId());

        partialUpdatedNutrition.empresaId(UPDATED_EMPRESA_ID).food(UPDATED_FOOD).nutrients(UPDATED_NUTRIENTS).endTime(UPDATED_END_TIME);

        restNutritionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNutrition.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedNutrition))
            )
            .andExpect(status().isOk());

        // Validate the Nutrition in the database
        List<Nutrition> nutritionList = nutritionRepository.findAll();
        assertThat(nutritionList).hasSize(databaseSizeBeforeUpdate);
        Nutrition testNutrition = nutritionList.get(nutritionList.size() - 1);
        assertThat(testNutrition.getUsuarioId()).isEqualTo(DEFAULT_USUARIO_ID);
        assertThat(testNutrition.getEmpresaId()).isEqualTo(UPDATED_EMPRESA_ID);
        assertThat(testNutrition.getMealType()).isEqualTo(DEFAULT_MEAL_TYPE);
        assertThat(testNutrition.getFood()).isEqualTo(UPDATED_FOOD);
        assertThat(testNutrition.getNutrients()).isEqualTo(UPDATED_NUTRIENTS);
        assertThat(testNutrition.getEndTime()).isEqualTo(UPDATED_END_TIME);
    }

    @Test
    @Transactional
    void fullUpdateNutritionWithPatch() throws Exception {
        // Initialize the database
        nutritionRepository.saveAndFlush(nutrition);

        int databaseSizeBeforeUpdate = nutritionRepository.findAll().size();

        // Update the nutrition using partial update
        Nutrition partialUpdatedNutrition = new Nutrition();
        partialUpdatedNutrition.setId(nutrition.getId());

        partialUpdatedNutrition
            .usuarioId(UPDATED_USUARIO_ID)
            .empresaId(UPDATED_EMPRESA_ID)
            .mealType(UPDATED_MEAL_TYPE)
            .food(UPDATED_FOOD)
            .nutrients(UPDATED_NUTRIENTS)
            .endTime(UPDATED_END_TIME);

        restNutritionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNutrition.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedNutrition))
            )
            .andExpect(status().isOk());

        // Validate the Nutrition in the database
        List<Nutrition> nutritionList = nutritionRepository.findAll();
        assertThat(nutritionList).hasSize(databaseSizeBeforeUpdate);
        Nutrition testNutrition = nutritionList.get(nutritionList.size() - 1);
        assertThat(testNutrition.getUsuarioId()).isEqualTo(UPDATED_USUARIO_ID);
        assertThat(testNutrition.getEmpresaId()).isEqualTo(UPDATED_EMPRESA_ID);
        assertThat(testNutrition.getMealType()).isEqualTo(UPDATED_MEAL_TYPE);
        assertThat(testNutrition.getFood()).isEqualTo(UPDATED_FOOD);
        assertThat(testNutrition.getNutrients()).isEqualTo(UPDATED_NUTRIENTS);
        assertThat(testNutrition.getEndTime()).isEqualTo(UPDATED_END_TIME);
    }

    @Test
    @Transactional
    void patchNonExistingNutrition() throws Exception {
        int databaseSizeBeforeUpdate = nutritionRepository.findAll().size();
        nutrition.setId(UUID.randomUUID());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNutritionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, nutrition.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(nutrition))
            )
            .andExpect(status().isBadRequest());

        // Validate the Nutrition in the database
        List<Nutrition> nutritionList = nutritionRepository.findAll();
        assertThat(nutritionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchNutrition() throws Exception {
        int databaseSizeBeforeUpdate = nutritionRepository.findAll().size();
        nutrition.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNutritionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(nutrition))
            )
            .andExpect(status().isBadRequest());

        // Validate the Nutrition in the database
        List<Nutrition> nutritionList = nutritionRepository.findAll();
        assertThat(nutritionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamNutrition() throws Exception {
        int databaseSizeBeforeUpdate = nutritionRepository.findAll().size();
        nutrition.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNutritionMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(nutrition))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Nutrition in the database
        List<Nutrition> nutritionList = nutritionRepository.findAll();
        assertThat(nutritionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteNutrition() throws Exception {
        // Initialize the database
        nutritionRepository.saveAndFlush(nutrition);

        int databaseSizeBeforeDelete = nutritionRepository.findAll().size();

        // Delete the nutrition
        restNutritionMockMvc
            .perform(delete(ENTITY_API_URL_ID, nutrition.getId().toString()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Nutrition> nutritionList = nutritionRepository.findAll();
        assertThat(nutritionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
