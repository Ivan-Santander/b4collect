package com.be4tech.b4carecollect.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.be4tech.b4carecollect.IntegrationTest;
import com.be4tech.b4carecollect.domain.UserBodyInfo;
import com.be4tech.b4carecollect.repository.UserBodyInfoRepository;
import com.be4tech.b4carecollect.service.criteria.UserBodyInfoCriteria;
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
 * Integration tests for the {@link UserBodyInfoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class UserBodyInfoResourceIT {

    private static final String DEFAULT_USUARIO_ID = "AAAAAAAAAA";
    private static final String UPDATED_USUARIO_ID = "BBBBBBBBBB";

    private static final String DEFAULT_EMPRESA_ID = "AAAAAAAAAA";
    private static final String UPDATED_EMPRESA_ID = "BBBBBBBBBB";

    private static final Float DEFAULT_WAIST_CIRCUMFERENCE = 1F;
    private static final Float UPDATED_WAIST_CIRCUMFERENCE = 2F;
    private static final Float SMALLER_WAIST_CIRCUMFERENCE = 1F - 1F;

    private static final Float DEFAULT_HIP_CIRCUMFERENCE = 1F;
    private static final Float UPDATED_HIP_CIRCUMFERENCE = 2F;
    private static final Float SMALLER_HIP_CIRCUMFERENCE = 1F - 1F;

    private static final Float DEFAULT_CHEST_CIRCUMFERENCE = 1F;
    private static final Float UPDATED_CHEST_CIRCUMFERENCE = 2F;
    private static final Float SMALLER_CHEST_CIRCUMFERENCE = 1F - 1F;

    private static final Float DEFAULT_BONE_COMPOSITION_PERCENTAJE = 1F;
    private static final Float UPDATED_BONE_COMPOSITION_PERCENTAJE = 2F;
    private static final Float SMALLER_BONE_COMPOSITION_PERCENTAJE = 1F - 1F;

    private static final Float DEFAULT_MUSCLE_COMPOSITION_PERCENTAJE = 1F;
    private static final Float UPDATED_MUSCLE_COMPOSITION_PERCENTAJE = 2F;
    private static final Float SMALLER_MUSCLE_COMPOSITION_PERCENTAJE = 1F - 1F;

    private static final Boolean DEFAULT_SMOKER = false;
    private static final Boolean UPDATED_SMOKER = true;

    private static final Float DEFAULT_WAIGHT_KG = 1F;
    private static final Float UPDATED_WAIGHT_KG = 2F;
    private static final Float SMALLER_WAIGHT_KG = 1F - 1F;

    private static final Float DEFAULT_HEIGHT_CM = 1F;
    private static final Float UPDATED_HEIGHT_CM = 2F;
    private static final Float SMALLER_HEIGHT_CM = 1F - 1F;

    private static final Float DEFAULT_BODY_HEALTH_SCORE = 1F;
    private static final Float UPDATED_BODY_HEALTH_SCORE = 2F;
    private static final Float SMALLER_BODY_HEALTH_SCORE = 1F - 1F;

    private static final Integer DEFAULT_CARDIOVASCULAR_RISK = 1;
    private static final Integer UPDATED_CARDIOVASCULAR_RISK = 2;
    private static final Integer SMALLER_CARDIOVASCULAR_RISK = 1 - 1;

    private static final String ENTITY_API_URL = "/api/user-body-infos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private UserBodyInfoRepository userBodyInfoRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restUserBodyInfoMockMvc;

    private UserBodyInfo userBodyInfo;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserBodyInfo createEntity(EntityManager em) {
        UserBodyInfo userBodyInfo = new UserBodyInfo()
            .usuarioId(DEFAULT_USUARIO_ID)
            .empresaId(DEFAULT_EMPRESA_ID)
            .waistCircumference(DEFAULT_WAIST_CIRCUMFERENCE)
            .hipCircumference(DEFAULT_HIP_CIRCUMFERENCE)
            .chestCircumference(DEFAULT_CHEST_CIRCUMFERENCE)
            .boneCompositionPercentaje(DEFAULT_BONE_COMPOSITION_PERCENTAJE)
            .muscleCompositionPercentaje(DEFAULT_MUSCLE_COMPOSITION_PERCENTAJE)
            .smoker(DEFAULT_SMOKER)
            .waightKg(DEFAULT_WAIGHT_KG)
            .heightCm(DEFAULT_HEIGHT_CM)
            .bodyHealthScore(DEFAULT_BODY_HEALTH_SCORE)
            .cardiovascularRisk(DEFAULT_CARDIOVASCULAR_RISK);
        return userBodyInfo;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserBodyInfo createUpdatedEntity(EntityManager em) {
        UserBodyInfo userBodyInfo = new UserBodyInfo()
            .usuarioId(UPDATED_USUARIO_ID)
            .empresaId(UPDATED_EMPRESA_ID)
            .waistCircumference(UPDATED_WAIST_CIRCUMFERENCE)
            .hipCircumference(UPDATED_HIP_CIRCUMFERENCE)
            .chestCircumference(UPDATED_CHEST_CIRCUMFERENCE)
            .boneCompositionPercentaje(UPDATED_BONE_COMPOSITION_PERCENTAJE)
            .muscleCompositionPercentaje(UPDATED_MUSCLE_COMPOSITION_PERCENTAJE)
            .smoker(UPDATED_SMOKER)
            .waightKg(UPDATED_WAIGHT_KG)
            .heightCm(UPDATED_HEIGHT_CM)
            .bodyHealthScore(UPDATED_BODY_HEALTH_SCORE)
            .cardiovascularRisk(UPDATED_CARDIOVASCULAR_RISK);
        return userBodyInfo;
    }

    @BeforeEach
    public void initTest() {
        userBodyInfo = createEntity(em);
    }

    @Test
    @Transactional
    void createUserBodyInfo() throws Exception {
        int databaseSizeBeforeCreate = userBodyInfoRepository.findAll().size();
        // Create the UserBodyInfo
        restUserBodyInfoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(userBodyInfo)))
            .andExpect(status().isCreated());

        // Validate the UserBodyInfo in the database
        List<UserBodyInfo> userBodyInfoList = userBodyInfoRepository.findAll();
        assertThat(userBodyInfoList).hasSize(databaseSizeBeforeCreate + 1);
        UserBodyInfo testUserBodyInfo = userBodyInfoList.get(userBodyInfoList.size() - 1);
        assertThat(testUserBodyInfo.getUsuarioId()).isEqualTo(DEFAULT_USUARIO_ID);
        assertThat(testUserBodyInfo.getEmpresaId()).isEqualTo(DEFAULT_EMPRESA_ID);
        assertThat(testUserBodyInfo.getWaistCircumference()).isEqualTo(DEFAULT_WAIST_CIRCUMFERENCE);
        assertThat(testUserBodyInfo.getHipCircumference()).isEqualTo(DEFAULT_HIP_CIRCUMFERENCE);
        assertThat(testUserBodyInfo.getChestCircumference()).isEqualTo(DEFAULT_CHEST_CIRCUMFERENCE);
        assertThat(testUserBodyInfo.getBoneCompositionPercentaje()).isEqualTo(DEFAULT_BONE_COMPOSITION_PERCENTAJE);
        assertThat(testUserBodyInfo.getMuscleCompositionPercentaje()).isEqualTo(DEFAULT_MUSCLE_COMPOSITION_PERCENTAJE);
        assertThat(testUserBodyInfo.getSmoker()).isEqualTo(DEFAULT_SMOKER);
        assertThat(testUserBodyInfo.getWaightKg()).isEqualTo(DEFAULT_WAIGHT_KG);
        assertThat(testUserBodyInfo.getHeightCm()).isEqualTo(DEFAULT_HEIGHT_CM);
        assertThat(testUserBodyInfo.getBodyHealthScore()).isEqualTo(DEFAULT_BODY_HEALTH_SCORE);
        assertThat(testUserBodyInfo.getCardiovascularRisk()).isEqualTo(DEFAULT_CARDIOVASCULAR_RISK);
    }

    @Test
    @Transactional
    void createUserBodyInfoWithExistingId() throws Exception {
        // Create the UserBodyInfo with an existing ID
        userBodyInfoRepository.saveAndFlush(userBodyInfo);

        int databaseSizeBeforeCreate = userBodyInfoRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserBodyInfoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(userBodyInfo)))
            .andExpect(status().isBadRequest());

        // Validate the UserBodyInfo in the database
        List<UserBodyInfo> userBodyInfoList = userBodyInfoRepository.findAll();
        assertThat(userBodyInfoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllUserBodyInfos() throws Exception {
        // Initialize the database
        userBodyInfoRepository.saveAndFlush(userBodyInfo);

        // Get all the userBodyInfoList
        restUserBodyInfoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userBodyInfo.getId().toString())))
            .andExpect(jsonPath("$.[*].usuarioId").value(hasItem(DEFAULT_USUARIO_ID)))
            .andExpect(jsonPath("$.[*].empresaId").value(hasItem(DEFAULT_EMPRESA_ID)))
            .andExpect(jsonPath("$.[*].waistCircumference").value(hasItem(DEFAULT_WAIST_CIRCUMFERENCE.doubleValue())))
            .andExpect(jsonPath("$.[*].hipCircumference").value(hasItem(DEFAULT_HIP_CIRCUMFERENCE.doubleValue())))
            .andExpect(jsonPath("$.[*].chestCircumference").value(hasItem(DEFAULT_CHEST_CIRCUMFERENCE.doubleValue())))
            .andExpect(jsonPath("$.[*].boneCompositionPercentaje").value(hasItem(DEFAULT_BONE_COMPOSITION_PERCENTAJE.doubleValue())))
            .andExpect(jsonPath("$.[*].muscleCompositionPercentaje").value(hasItem(DEFAULT_MUSCLE_COMPOSITION_PERCENTAJE.doubleValue())))
            .andExpect(jsonPath("$.[*].smoker").value(hasItem(DEFAULT_SMOKER.booleanValue())))
            .andExpect(jsonPath("$.[*].waightKg").value(hasItem(DEFAULT_WAIGHT_KG.doubleValue())))
            .andExpect(jsonPath("$.[*].heightCm").value(hasItem(DEFAULT_HEIGHT_CM.doubleValue())))
            .andExpect(jsonPath("$.[*].bodyHealthScore").value(hasItem(DEFAULT_BODY_HEALTH_SCORE.doubleValue())))
            .andExpect(jsonPath("$.[*].cardiovascularRisk").value(hasItem(DEFAULT_CARDIOVASCULAR_RISK)));
    }

    @Test
    @Transactional
    void getUserBodyInfo() throws Exception {
        // Initialize the database
        userBodyInfoRepository.saveAndFlush(userBodyInfo);

        // Get the userBodyInfo
        restUserBodyInfoMockMvc
            .perform(get(ENTITY_API_URL_ID, userBodyInfo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(userBodyInfo.getId().toString()))
            .andExpect(jsonPath("$.usuarioId").value(DEFAULT_USUARIO_ID))
            .andExpect(jsonPath("$.empresaId").value(DEFAULT_EMPRESA_ID))
            .andExpect(jsonPath("$.waistCircumference").value(DEFAULT_WAIST_CIRCUMFERENCE.doubleValue()))
            .andExpect(jsonPath("$.hipCircumference").value(DEFAULT_HIP_CIRCUMFERENCE.doubleValue()))
            .andExpect(jsonPath("$.chestCircumference").value(DEFAULT_CHEST_CIRCUMFERENCE.doubleValue()))
            .andExpect(jsonPath("$.boneCompositionPercentaje").value(DEFAULT_BONE_COMPOSITION_PERCENTAJE.doubleValue()))
            .andExpect(jsonPath("$.muscleCompositionPercentaje").value(DEFAULT_MUSCLE_COMPOSITION_PERCENTAJE.doubleValue()))
            .andExpect(jsonPath("$.smoker").value(DEFAULT_SMOKER.booleanValue()))
            .andExpect(jsonPath("$.waightKg").value(DEFAULT_WAIGHT_KG.doubleValue()))
            .andExpect(jsonPath("$.heightCm").value(DEFAULT_HEIGHT_CM.doubleValue()))
            .andExpect(jsonPath("$.bodyHealthScore").value(DEFAULT_BODY_HEALTH_SCORE.doubleValue()))
            .andExpect(jsonPath("$.cardiovascularRisk").value(DEFAULT_CARDIOVASCULAR_RISK));
    }

    @Test
    @Transactional
    void getUserBodyInfosByIdFiltering() throws Exception {
        // Initialize the database
        userBodyInfoRepository.saveAndFlush(userBodyInfo);

        UUID id = userBodyInfo.getId();

        defaultUserBodyInfoShouldBeFound("id.equals=" + id);
        defaultUserBodyInfoShouldNotBeFound("id.notEquals=" + id);
    }

    @Test
    @Transactional
    void getAllUserBodyInfosByUsuarioIdIsEqualToSomething() throws Exception {
        // Initialize the database
        userBodyInfoRepository.saveAndFlush(userBodyInfo);

        // Get all the userBodyInfoList where usuarioId equals to DEFAULT_USUARIO_ID
        defaultUserBodyInfoShouldBeFound("usuarioId.equals=" + DEFAULT_USUARIO_ID);

        // Get all the userBodyInfoList where usuarioId equals to UPDATED_USUARIO_ID
        defaultUserBodyInfoShouldNotBeFound("usuarioId.equals=" + UPDATED_USUARIO_ID);
    }

    @Test
    @Transactional
    void getAllUserBodyInfosByUsuarioIdIsInShouldWork() throws Exception {
        // Initialize the database
        userBodyInfoRepository.saveAndFlush(userBodyInfo);

        // Get all the userBodyInfoList where usuarioId in DEFAULT_USUARIO_ID or UPDATED_USUARIO_ID
        defaultUserBodyInfoShouldBeFound("usuarioId.in=" + DEFAULT_USUARIO_ID + "," + UPDATED_USUARIO_ID);

        // Get all the userBodyInfoList where usuarioId equals to UPDATED_USUARIO_ID
        defaultUserBodyInfoShouldNotBeFound("usuarioId.in=" + UPDATED_USUARIO_ID);
    }

    @Test
    @Transactional
    void getAllUserBodyInfosByUsuarioIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        userBodyInfoRepository.saveAndFlush(userBodyInfo);

        // Get all the userBodyInfoList where usuarioId is not null
        defaultUserBodyInfoShouldBeFound("usuarioId.specified=true");

        // Get all the userBodyInfoList where usuarioId is null
        defaultUserBodyInfoShouldNotBeFound("usuarioId.specified=false");
    }

    @Test
    @Transactional
    void getAllUserBodyInfosByUsuarioIdContainsSomething() throws Exception {
        // Initialize the database
        userBodyInfoRepository.saveAndFlush(userBodyInfo);

        // Get all the userBodyInfoList where usuarioId contains DEFAULT_USUARIO_ID
        defaultUserBodyInfoShouldBeFound("usuarioId.contains=" + DEFAULT_USUARIO_ID);

        // Get all the userBodyInfoList where usuarioId contains UPDATED_USUARIO_ID
        defaultUserBodyInfoShouldNotBeFound("usuarioId.contains=" + UPDATED_USUARIO_ID);
    }

    @Test
    @Transactional
    void getAllUserBodyInfosByUsuarioIdNotContainsSomething() throws Exception {
        // Initialize the database
        userBodyInfoRepository.saveAndFlush(userBodyInfo);

        // Get all the userBodyInfoList where usuarioId does not contain DEFAULT_USUARIO_ID
        defaultUserBodyInfoShouldNotBeFound("usuarioId.doesNotContain=" + DEFAULT_USUARIO_ID);

        // Get all the userBodyInfoList where usuarioId does not contain UPDATED_USUARIO_ID
        defaultUserBodyInfoShouldBeFound("usuarioId.doesNotContain=" + UPDATED_USUARIO_ID);
    }

    @Test
    @Transactional
    void getAllUserBodyInfosByEmpresaIdIsEqualToSomething() throws Exception {
        // Initialize the database
        userBodyInfoRepository.saveAndFlush(userBodyInfo);

        // Get all the userBodyInfoList where empresaId equals to DEFAULT_EMPRESA_ID
        defaultUserBodyInfoShouldBeFound("empresaId.equals=" + DEFAULT_EMPRESA_ID);

        // Get all the userBodyInfoList where empresaId equals to UPDATED_EMPRESA_ID
        defaultUserBodyInfoShouldNotBeFound("empresaId.equals=" + UPDATED_EMPRESA_ID);
    }

    @Test
    @Transactional
    void getAllUserBodyInfosByEmpresaIdIsInShouldWork() throws Exception {
        // Initialize the database
        userBodyInfoRepository.saveAndFlush(userBodyInfo);

        // Get all the userBodyInfoList where empresaId in DEFAULT_EMPRESA_ID or UPDATED_EMPRESA_ID
        defaultUserBodyInfoShouldBeFound("empresaId.in=" + DEFAULT_EMPRESA_ID + "," + UPDATED_EMPRESA_ID);

        // Get all the userBodyInfoList where empresaId equals to UPDATED_EMPRESA_ID
        defaultUserBodyInfoShouldNotBeFound("empresaId.in=" + UPDATED_EMPRESA_ID);
    }

    @Test
    @Transactional
    void getAllUserBodyInfosByEmpresaIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        userBodyInfoRepository.saveAndFlush(userBodyInfo);

        // Get all the userBodyInfoList where empresaId is not null
        defaultUserBodyInfoShouldBeFound("empresaId.specified=true");

        // Get all the userBodyInfoList where empresaId is null
        defaultUserBodyInfoShouldNotBeFound("empresaId.specified=false");
    }

    @Test
    @Transactional
    void getAllUserBodyInfosByEmpresaIdContainsSomething() throws Exception {
        // Initialize the database
        userBodyInfoRepository.saveAndFlush(userBodyInfo);

        // Get all the userBodyInfoList where empresaId contains DEFAULT_EMPRESA_ID
        defaultUserBodyInfoShouldBeFound("empresaId.contains=" + DEFAULT_EMPRESA_ID);

        // Get all the userBodyInfoList where empresaId contains UPDATED_EMPRESA_ID
        defaultUserBodyInfoShouldNotBeFound("empresaId.contains=" + UPDATED_EMPRESA_ID);
    }

    @Test
    @Transactional
    void getAllUserBodyInfosByEmpresaIdNotContainsSomething() throws Exception {
        // Initialize the database
        userBodyInfoRepository.saveAndFlush(userBodyInfo);

        // Get all the userBodyInfoList where empresaId does not contain DEFAULT_EMPRESA_ID
        defaultUserBodyInfoShouldNotBeFound("empresaId.doesNotContain=" + DEFAULT_EMPRESA_ID);

        // Get all the userBodyInfoList where empresaId does not contain UPDATED_EMPRESA_ID
        defaultUserBodyInfoShouldBeFound("empresaId.doesNotContain=" + UPDATED_EMPRESA_ID);
    }

    @Test
    @Transactional
    void getAllUserBodyInfosByWaistCircumferenceIsEqualToSomething() throws Exception {
        // Initialize the database
        userBodyInfoRepository.saveAndFlush(userBodyInfo);

        // Get all the userBodyInfoList where waistCircumference equals to DEFAULT_WAIST_CIRCUMFERENCE
        defaultUserBodyInfoShouldBeFound("waistCircumference.equals=" + DEFAULT_WAIST_CIRCUMFERENCE);

        // Get all the userBodyInfoList where waistCircumference equals to UPDATED_WAIST_CIRCUMFERENCE
        defaultUserBodyInfoShouldNotBeFound("waistCircumference.equals=" + UPDATED_WAIST_CIRCUMFERENCE);
    }

    @Test
    @Transactional
    void getAllUserBodyInfosByWaistCircumferenceIsInShouldWork() throws Exception {
        // Initialize the database
        userBodyInfoRepository.saveAndFlush(userBodyInfo);

        // Get all the userBodyInfoList where waistCircumference in DEFAULT_WAIST_CIRCUMFERENCE or UPDATED_WAIST_CIRCUMFERENCE
        defaultUserBodyInfoShouldBeFound("waistCircumference.in=" + DEFAULT_WAIST_CIRCUMFERENCE + "," + UPDATED_WAIST_CIRCUMFERENCE);

        // Get all the userBodyInfoList where waistCircumference equals to UPDATED_WAIST_CIRCUMFERENCE
        defaultUserBodyInfoShouldNotBeFound("waistCircumference.in=" + UPDATED_WAIST_CIRCUMFERENCE);
    }

    @Test
    @Transactional
    void getAllUserBodyInfosByWaistCircumferenceIsNullOrNotNull() throws Exception {
        // Initialize the database
        userBodyInfoRepository.saveAndFlush(userBodyInfo);

        // Get all the userBodyInfoList where waistCircumference is not null
        defaultUserBodyInfoShouldBeFound("waistCircumference.specified=true");

        // Get all the userBodyInfoList where waistCircumference is null
        defaultUserBodyInfoShouldNotBeFound("waistCircumference.specified=false");
    }

    @Test
    @Transactional
    void getAllUserBodyInfosByWaistCircumferenceIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        userBodyInfoRepository.saveAndFlush(userBodyInfo);

        // Get all the userBodyInfoList where waistCircumference is greater than or equal to DEFAULT_WAIST_CIRCUMFERENCE
        defaultUserBodyInfoShouldBeFound("waistCircumference.greaterThanOrEqual=" + DEFAULT_WAIST_CIRCUMFERENCE);

        // Get all the userBodyInfoList where waistCircumference is greater than or equal to UPDATED_WAIST_CIRCUMFERENCE
        defaultUserBodyInfoShouldNotBeFound("waistCircumference.greaterThanOrEqual=" + UPDATED_WAIST_CIRCUMFERENCE);
    }

    @Test
    @Transactional
    void getAllUserBodyInfosByWaistCircumferenceIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        userBodyInfoRepository.saveAndFlush(userBodyInfo);

        // Get all the userBodyInfoList where waistCircumference is less than or equal to DEFAULT_WAIST_CIRCUMFERENCE
        defaultUserBodyInfoShouldBeFound("waistCircumference.lessThanOrEqual=" + DEFAULT_WAIST_CIRCUMFERENCE);

        // Get all the userBodyInfoList where waistCircumference is less than or equal to SMALLER_WAIST_CIRCUMFERENCE
        defaultUserBodyInfoShouldNotBeFound("waistCircumference.lessThanOrEqual=" + SMALLER_WAIST_CIRCUMFERENCE);
    }

    @Test
    @Transactional
    void getAllUserBodyInfosByWaistCircumferenceIsLessThanSomething() throws Exception {
        // Initialize the database
        userBodyInfoRepository.saveAndFlush(userBodyInfo);

        // Get all the userBodyInfoList where waistCircumference is less than DEFAULT_WAIST_CIRCUMFERENCE
        defaultUserBodyInfoShouldNotBeFound("waistCircumference.lessThan=" + DEFAULT_WAIST_CIRCUMFERENCE);

        // Get all the userBodyInfoList where waistCircumference is less than UPDATED_WAIST_CIRCUMFERENCE
        defaultUserBodyInfoShouldBeFound("waistCircumference.lessThan=" + UPDATED_WAIST_CIRCUMFERENCE);
    }

    @Test
    @Transactional
    void getAllUserBodyInfosByWaistCircumferenceIsGreaterThanSomething() throws Exception {
        // Initialize the database
        userBodyInfoRepository.saveAndFlush(userBodyInfo);

        // Get all the userBodyInfoList where waistCircumference is greater than DEFAULT_WAIST_CIRCUMFERENCE
        defaultUserBodyInfoShouldNotBeFound("waistCircumference.greaterThan=" + DEFAULT_WAIST_CIRCUMFERENCE);

        // Get all the userBodyInfoList where waistCircumference is greater than SMALLER_WAIST_CIRCUMFERENCE
        defaultUserBodyInfoShouldBeFound("waistCircumference.greaterThan=" + SMALLER_WAIST_CIRCUMFERENCE);
    }

    @Test
    @Transactional
    void getAllUserBodyInfosByHipCircumferenceIsEqualToSomething() throws Exception {
        // Initialize the database
        userBodyInfoRepository.saveAndFlush(userBodyInfo);

        // Get all the userBodyInfoList where hipCircumference equals to DEFAULT_HIP_CIRCUMFERENCE
        defaultUserBodyInfoShouldBeFound("hipCircumference.equals=" + DEFAULT_HIP_CIRCUMFERENCE);

        // Get all the userBodyInfoList where hipCircumference equals to UPDATED_HIP_CIRCUMFERENCE
        defaultUserBodyInfoShouldNotBeFound("hipCircumference.equals=" + UPDATED_HIP_CIRCUMFERENCE);
    }

    @Test
    @Transactional
    void getAllUserBodyInfosByHipCircumferenceIsInShouldWork() throws Exception {
        // Initialize the database
        userBodyInfoRepository.saveAndFlush(userBodyInfo);

        // Get all the userBodyInfoList where hipCircumference in DEFAULT_HIP_CIRCUMFERENCE or UPDATED_HIP_CIRCUMFERENCE
        defaultUserBodyInfoShouldBeFound("hipCircumference.in=" + DEFAULT_HIP_CIRCUMFERENCE + "," + UPDATED_HIP_CIRCUMFERENCE);

        // Get all the userBodyInfoList where hipCircumference equals to UPDATED_HIP_CIRCUMFERENCE
        defaultUserBodyInfoShouldNotBeFound("hipCircumference.in=" + UPDATED_HIP_CIRCUMFERENCE);
    }

    @Test
    @Transactional
    void getAllUserBodyInfosByHipCircumferenceIsNullOrNotNull() throws Exception {
        // Initialize the database
        userBodyInfoRepository.saveAndFlush(userBodyInfo);

        // Get all the userBodyInfoList where hipCircumference is not null
        defaultUserBodyInfoShouldBeFound("hipCircumference.specified=true");

        // Get all the userBodyInfoList where hipCircumference is null
        defaultUserBodyInfoShouldNotBeFound("hipCircumference.specified=false");
    }

    @Test
    @Transactional
    void getAllUserBodyInfosByHipCircumferenceIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        userBodyInfoRepository.saveAndFlush(userBodyInfo);

        // Get all the userBodyInfoList where hipCircumference is greater than or equal to DEFAULT_HIP_CIRCUMFERENCE
        defaultUserBodyInfoShouldBeFound("hipCircumference.greaterThanOrEqual=" + DEFAULT_HIP_CIRCUMFERENCE);

        // Get all the userBodyInfoList where hipCircumference is greater than or equal to UPDATED_HIP_CIRCUMFERENCE
        defaultUserBodyInfoShouldNotBeFound("hipCircumference.greaterThanOrEqual=" + UPDATED_HIP_CIRCUMFERENCE);
    }

    @Test
    @Transactional
    void getAllUserBodyInfosByHipCircumferenceIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        userBodyInfoRepository.saveAndFlush(userBodyInfo);

        // Get all the userBodyInfoList where hipCircumference is less than or equal to DEFAULT_HIP_CIRCUMFERENCE
        defaultUserBodyInfoShouldBeFound("hipCircumference.lessThanOrEqual=" + DEFAULT_HIP_CIRCUMFERENCE);

        // Get all the userBodyInfoList where hipCircumference is less than or equal to SMALLER_HIP_CIRCUMFERENCE
        defaultUserBodyInfoShouldNotBeFound("hipCircumference.lessThanOrEqual=" + SMALLER_HIP_CIRCUMFERENCE);
    }

    @Test
    @Transactional
    void getAllUserBodyInfosByHipCircumferenceIsLessThanSomething() throws Exception {
        // Initialize the database
        userBodyInfoRepository.saveAndFlush(userBodyInfo);

        // Get all the userBodyInfoList where hipCircumference is less than DEFAULT_HIP_CIRCUMFERENCE
        defaultUserBodyInfoShouldNotBeFound("hipCircumference.lessThan=" + DEFAULT_HIP_CIRCUMFERENCE);

        // Get all the userBodyInfoList where hipCircumference is less than UPDATED_HIP_CIRCUMFERENCE
        defaultUserBodyInfoShouldBeFound("hipCircumference.lessThan=" + UPDATED_HIP_CIRCUMFERENCE);
    }

    @Test
    @Transactional
    void getAllUserBodyInfosByHipCircumferenceIsGreaterThanSomething() throws Exception {
        // Initialize the database
        userBodyInfoRepository.saveAndFlush(userBodyInfo);

        // Get all the userBodyInfoList where hipCircumference is greater than DEFAULT_HIP_CIRCUMFERENCE
        defaultUserBodyInfoShouldNotBeFound("hipCircumference.greaterThan=" + DEFAULT_HIP_CIRCUMFERENCE);

        // Get all the userBodyInfoList where hipCircumference is greater than SMALLER_HIP_CIRCUMFERENCE
        defaultUserBodyInfoShouldBeFound("hipCircumference.greaterThan=" + SMALLER_HIP_CIRCUMFERENCE);
    }

    @Test
    @Transactional
    void getAllUserBodyInfosByChestCircumferenceIsEqualToSomething() throws Exception {
        // Initialize the database
        userBodyInfoRepository.saveAndFlush(userBodyInfo);

        // Get all the userBodyInfoList where chestCircumference equals to DEFAULT_CHEST_CIRCUMFERENCE
        defaultUserBodyInfoShouldBeFound("chestCircumference.equals=" + DEFAULT_CHEST_CIRCUMFERENCE);

        // Get all the userBodyInfoList where chestCircumference equals to UPDATED_CHEST_CIRCUMFERENCE
        defaultUserBodyInfoShouldNotBeFound("chestCircumference.equals=" + UPDATED_CHEST_CIRCUMFERENCE);
    }

    @Test
    @Transactional
    void getAllUserBodyInfosByChestCircumferenceIsInShouldWork() throws Exception {
        // Initialize the database
        userBodyInfoRepository.saveAndFlush(userBodyInfo);

        // Get all the userBodyInfoList where chestCircumference in DEFAULT_CHEST_CIRCUMFERENCE or UPDATED_CHEST_CIRCUMFERENCE
        defaultUserBodyInfoShouldBeFound("chestCircumference.in=" + DEFAULT_CHEST_CIRCUMFERENCE + "," + UPDATED_CHEST_CIRCUMFERENCE);

        // Get all the userBodyInfoList where chestCircumference equals to UPDATED_CHEST_CIRCUMFERENCE
        defaultUserBodyInfoShouldNotBeFound("chestCircumference.in=" + UPDATED_CHEST_CIRCUMFERENCE);
    }

    @Test
    @Transactional
    void getAllUserBodyInfosByChestCircumferenceIsNullOrNotNull() throws Exception {
        // Initialize the database
        userBodyInfoRepository.saveAndFlush(userBodyInfo);

        // Get all the userBodyInfoList where chestCircumference is not null
        defaultUserBodyInfoShouldBeFound("chestCircumference.specified=true");

        // Get all the userBodyInfoList where chestCircumference is null
        defaultUserBodyInfoShouldNotBeFound("chestCircumference.specified=false");
    }

    @Test
    @Transactional
    void getAllUserBodyInfosByChestCircumferenceIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        userBodyInfoRepository.saveAndFlush(userBodyInfo);

        // Get all the userBodyInfoList where chestCircumference is greater than or equal to DEFAULT_CHEST_CIRCUMFERENCE
        defaultUserBodyInfoShouldBeFound("chestCircumference.greaterThanOrEqual=" + DEFAULT_CHEST_CIRCUMFERENCE);

        // Get all the userBodyInfoList where chestCircumference is greater than or equal to UPDATED_CHEST_CIRCUMFERENCE
        defaultUserBodyInfoShouldNotBeFound("chestCircumference.greaterThanOrEqual=" + UPDATED_CHEST_CIRCUMFERENCE);
    }

    @Test
    @Transactional
    void getAllUserBodyInfosByChestCircumferenceIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        userBodyInfoRepository.saveAndFlush(userBodyInfo);

        // Get all the userBodyInfoList where chestCircumference is less than or equal to DEFAULT_CHEST_CIRCUMFERENCE
        defaultUserBodyInfoShouldBeFound("chestCircumference.lessThanOrEqual=" + DEFAULT_CHEST_CIRCUMFERENCE);

        // Get all the userBodyInfoList where chestCircumference is less than or equal to SMALLER_CHEST_CIRCUMFERENCE
        defaultUserBodyInfoShouldNotBeFound("chestCircumference.lessThanOrEqual=" + SMALLER_CHEST_CIRCUMFERENCE);
    }

    @Test
    @Transactional
    void getAllUserBodyInfosByChestCircumferenceIsLessThanSomething() throws Exception {
        // Initialize the database
        userBodyInfoRepository.saveAndFlush(userBodyInfo);

        // Get all the userBodyInfoList where chestCircumference is less than DEFAULT_CHEST_CIRCUMFERENCE
        defaultUserBodyInfoShouldNotBeFound("chestCircumference.lessThan=" + DEFAULT_CHEST_CIRCUMFERENCE);

        // Get all the userBodyInfoList where chestCircumference is less than UPDATED_CHEST_CIRCUMFERENCE
        defaultUserBodyInfoShouldBeFound("chestCircumference.lessThan=" + UPDATED_CHEST_CIRCUMFERENCE);
    }

    @Test
    @Transactional
    void getAllUserBodyInfosByChestCircumferenceIsGreaterThanSomething() throws Exception {
        // Initialize the database
        userBodyInfoRepository.saveAndFlush(userBodyInfo);

        // Get all the userBodyInfoList where chestCircumference is greater than DEFAULT_CHEST_CIRCUMFERENCE
        defaultUserBodyInfoShouldNotBeFound("chestCircumference.greaterThan=" + DEFAULT_CHEST_CIRCUMFERENCE);

        // Get all the userBodyInfoList where chestCircumference is greater than SMALLER_CHEST_CIRCUMFERENCE
        defaultUserBodyInfoShouldBeFound("chestCircumference.greaterThan=" + SMALLER_CHEST_CIRCUMFERENCE);
    }

    @Test
    @Transactional
    void getAllUserBodyInfosByBoneCompositionPercentajeIsEqualToSomething() throws Exception {
        // Initialize the database
        userBodyInfoRepository.saveAndFlush(userBodyInfo);

        // Get all the userBodyInfoList where boneCompositionPercentaje equals to DEFAULT_BONE_COMPOSITION_PERCENTAJE
        defaultUserBodyInfoShouldBeFound("boneCompositionPercentaje.equals=" + DEFAULT_BONE_COMPOSITION_PERCENTAJE);

        // Get all the userBodyInfoList where boneCompositionPercentaje equals to UPDATED_BONE_COMPOSITION_PERCENTAJE
        defaultUserBodyInfoShouldNotBeFound("boneCompositionPercentaje.equals=" + UPDATED_BONE_COMPOSITION_PERCENTAJE);
    }

    @Test
    @Transactional
    void getAllUserBodyInfosByBoneCompositionPercentajeIsInShouldWork() throws Exception {
        // Initialize the database
        userBodyInfoRepository.saveAndFlush(userBodyInfo);

        // Get all the userBodyInfoList where boneCompositionPercentaje in DEFAULT_BONE_COMPOSITION_PERCENTAJE or UPDATED_BONE_COMPOSITION_PERCENTAJE
        defaultUserBodyInfoShouldBeFound(
            "boneCompositionPercentaje.in=" + DEFAULT_BONE_COMPOSITION_PERCENTAJE + "," + UPDATED_BONE_COMPOSITION_PERCENTAJE
        );

        // Get all the userBodyInfoList where boneCompositionPercentaje equals to UPDATED_BONE_COMPOSITION_PERCENTAJE
        defaultUserBodyInfoShouldNotBeFound("boneCompositionPercentaje.in=" + UPDATED_BONE_COMPOSITION_PERCENTAJE);
    }

    @Test
    @Transactional
    void getAllUserBodyInfosByBoneCompositionPercentajeIsNullOrNotNull() throws Exception {
        // Initialize the database
        userBodyInfoRepository.saveAndFlush(userBodyInfo);

        // Get all the userBodyInfoList where boneCompositionPercentaje is not null
        defaultUserBodyInfoShouldBeFound("boneCompositionPercentaje.specified=true");

        // Get all the userBodyInfoList where boneCompositionPercentaje is null
        defaultUserBodyInfoShouldNotBeFound("boneCompositionPercentaje.specified=false");
    }

    @Test
    @Transactional
    void getAllUserBodyInfosByBoneCompositionPercentajeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        userBodyInfoRepository.saveAndFlush(userBodyInfo);

        // Get all the userBodyInfoList where boneCompositionPercentaje is greater than or equal to DEFAULT_BONE_COMPOSITION_PERCENTAJE
        defaultUserBodyInfoShouldBeFound("boneCompositionPercentaje.greaterThanOrEqual=" + DEFAULT_BONE_COMPOSITION_PERCENTAJE);

        // Get all the userBodyInfoList where boneCompositionPercentaje is greater than or equal to UPDATED_BONE_COMPOSITION_PERCENTAJE
        defaultUserBodyInfoShouldNotBeFound("boneCompositionPercentaje.greaterThanOrEqual=" + UPDATED_BONE_COMPOSITION_PERCENTAJE);
    }

    @Test
    @Transactional
    void getAllUserBodyInfosByBoneCompositionPercentajeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        userBodyInfoRepository.saveAndFlush(userBodyInfo);

        // Get all the userBodyInfoList where boneCompositionPercentaje is less than or equal to DEFAULT_BONE_COMPOSITION_PERCENTAJE
        defaultUserBodyInfoShouldBeFound("boneCompositionPercentaje.lessThanOrEqual=" + DEFAULT_BONE_COMPOSITION_PERCENTAJE);

        // Get all the userBodyInfoList where boneCompositionPercentaje is less than or equal to SMALLER_BONE_COMPOSITION_PERCENTAJE
        defaultUserBodyInfoShouldNotBeFound("boneCompositionPercentaje.lessThanOrEqual=" + SMALLER_BONE_COMPOSITION_PERCENTAJE);
    }

    @Test
    @Transactional
    void getAllUserBodyInfosByBoneCompositionPercentajeIsLessThanSomething() throws Exception {
        // Initialize the database
        userBodyInfoRepository.saveAndFlush(userBodyInfo);

        // Get all the userBodyInfoList where boneCompositionPercentaje is less than DEFAULT_BONE_COMPOSITION_PERCENTAJE
        defaultUserBodyInfoShouldNotBeFound("boneCompositionPercentaje.lessThan=" + DEFAULT_BONE_COMPOSITION_PERCENTAJE);

        // Get all the userBodyInfoList where boneCompositionPercentaje is less than UPDATED_BONE_COMPOSITION_PERCENTAJE
        defaultUserBodyInfoShouldBeFound("boneCompositionPercentaje.lessThan=" + UPDATED_BONE_COMPOSITION_PERCENTAJE);
    }

    @Test
    @Transactional
    void getAllUserBodyInfosByBoneCompositionPercentajeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        userBodyInfoRepository.saveAndFlush(userBodyInfo);

        // Get all the userBodyInfoList where boneCompositionPercentaje is greater than DEFAULT_BONE_COMPOSITION_PERCENTAJE
        defaultUserBodyInfoShouldNotBeFound("boneCompositionPercentaje.greaterThan=" + DEFAULT_BONE_COMPOSITION_PERCENTAJE);

        // Get all the userBodyInfoList where boneCompositionPercentaje is greater than SMALLER_BONE_COMPOSITION_PERCENTAJE
        defaultUserBodyInfoShouldBeFound("boneCompositionPercentaje.greaterThan=" + SMALLER_BONE_COMPOSITION_PERCENTAJE);
    }

    @Test
    @Transactional
    void getAllUserBodyInfosByMuscleCompositionPercentajeIsEqualToSomething() throws Exception {
        // Initialize the database
        userBodyInfoRepository.saveAndFlush(userBodyInfo);

        // Get all the userBodyInfoList where muscleCompositionPercentaje equals to DEFAULT_MUSCLE_COMPOSITION_PERCENTAJE
        defaultUserBodyInfoShouldBeFound("muscleCompositionPercentaje.equals=" + DEFAULT_MUSCLE_COMPOSITION_PERCENTAJE);

        // Get all the userBodyInfoList where muscleCompositionPercentaje equals to UPDATED_MUSCLE_COMPOSITION_PERCENTAJE
        defaultUserBodyInfoShouldNotBeFound("muscleCompositionPercentaje.equals=" + UPDATED_MUSCLE_COMPOSITION_PERCENTAJE);
    }

    @Test
    @Transactional
    void getAllUserBodyInfosByMuscleCompositionPercentajeIsInShouldWork() throws Exception {
        // Initialize the database
        userBodyInfoRepository.saveAndFlush(userBodyInfo);

        // Get all the userBodyInfoList where muscleCompositionPercentaje in DEFAULT_MUSCLE_COMPOSITION_PERCENTAJE or UPDATED_MUSCLE_COMPOSITION_PERCENTAJE
        defaultUserBodyInfoShouldBeFound(
            "muscleCompositionPercentaje.in=" + DEFAULT_MUSCLE_COMPOSITION_PERCENTAJE + "," + UPDATED_MUSCLE_COMPOSITION_PERCENTAJE
        );

        // Get all the userBodyInfoList where muscleCompositionPercentaje equals to UPDATED_MUSCLE_COMPOSITION_PERCENTAJE
        defaultUserBodyInfoShouldNotBeFound("muscleCompositionPercentaje.in=" + UPDATED_MUSCLE_COMPOSITION_PERCENTAJE);
    }

    @Test
    @Transactional
    void getAllUserBodyInfosByMuscleCompositionPercentajeIsNullOrNotNull() throws Exception {
        // Initialize the database
        userBodyInfoRepository.saveAndFlush(userBodyInfo);

        // Get all the userBodyInfoList where muscleCompositionPercentaje is not null
        defaultUserBodyInfoShouldBeFound("muscleCompositionPercentaje.specified=true");

        // Get all the userBodyInfoList where muscleCompositionPercentaje is null
        defaultUserBodyInfoShouldNotBeFound("muscleCompositionPercentaje.specified=false");
    }

    @Test
    @Transactional
    void getAllUserBodyInfosByMuscleCompositionPercentajeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        userBodyInfoRepository.saveAndFlush(userBodyInfo);

        // Get all the userBodyInfoList where muscleCompositionPercentaje is greater than or equal to DEFAULT_MUSCLE_COMPOSITION_PERCENTAJE
        defaultUserBodyInfoShouldBeFound("muscleCompositionPercentaje.greaterThanOrEqual=" + DEFAULT_MUSCLE_COMPOSITION_PERCENTAJE);

        // Get all the userBodyInfoList where muscleCompositionPercentaje is greater than or equal to UPDATED_MUSCLE_COMPOSITION_PERCENTAJE
        defaultUserBodyInfoShouldNotBeFound("muscleCompositionPercentaje.greaterThanOrEqual=" + UPDATED_MUSCLE_COMPOSITION_PERCENTAJE);
    }

    @Test
    @Transactional
    void getAllUserBodyInfosByMuscleCompositionPercentajeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        userBodyInfoRepository.saveAndFlush(userBodyInfo);

        // Get all the userBodyInfoList where muscleCompositionPercentaje is less than or equal to DEFAULT_MUSCLE_COMPOSITION_PERCENTAJE
        defaultUserBodyInfoShouldBeFound("muscleCompositionPercentaje.lessThanOrEqual=" + DEFAULT_MUSCLE_COMPOSITION_PERCENTAJE);

        // Get all the userBodyInfoList where muscleCompositionPercentaje is less than or equal to SMALLER_MUSCLE_COMPOSITION_PERCENTAJE
        defaultUserBodyInfoShouldNotBeFound("muscleCompositionPercentaje.lessThanOrEqual=" + SMALLER_MUSCLE_COMPOSITION_PERCENTAJE);
    }

    @Test
    @Transactional
    void getAllUserBodyInfosByMuscleCompositionPercentajeIsLessThanSomething() throws Exception {
        // Initialize the database
        userBodyInfoRepository.saveAndFlush(userBodyInfo);

        // Get all the userBodyInfoList where muscleCompositionPercentaje is less than DEFAULT_MUSCLE_COMPOSITION_PERCENTAJE
        defaultUserBodyInfoShouldNotBeFound("muscleCompositionPercentaje.lessThan=" + DEFAULT_MUSCLE_COMPOSITION_PERCENTAJE);

        // Get all the userBodyInfoList where muscleCompositionPercentaje is less than UPDATED_MUSCLE_COMPOSITION_PERCENTAJE
        defaultUserBodyInfoShouldBeFound("muscleCompositionPercentaje.lessThan=" + UPDATED_MUSCLE_COMPOSITION_PERCENTAJE);
    }

    @Test
    @Transactional
    void getAllUserBodyInfosByMuscleCompositionPercentajeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        userBodyInfoRepository.saveAndFlush(userBodyInfo);

        // Get all the userBodyInfoList where muscleCompositionPercentaje is greater than DEFAULT_MUSCLE_COMPOSITION_PERCENTAJE
        defaultUserBodyInfoShouldNotBeFound("muscleCompositionPercentaje.greaterThan=" + DEFAULT_MUSCLE_COMPOSITION_PERCENTAJE);

        // Get all the userBodyInfoList where muscleCompositionPercentaje is greater than SMALLER_MUSCLE_COMPOSITION_PERCENTAJE
        defaultUserBodyInfoShouldBeFound("muscleCompositionPercentaje.greaterThan=" + SMALLER_MUSCLE_COMPOSITION_PERCENTAJE);
    }

    @Test
    @Transactional
    void getAllUserBodyInfosBySmokerIsEqualToSomething() throws Exception {
        // Initialize the database
        userBodyInfoRepository.saveAndFlush(userBodyInfo);

        // Get all the userBodyInfoList where smoker equals to DEFAULT_SMOKER
        defaultUserBodyInfoShouldBeFound("smoker.equals=" + DEFAULT_SMOKER);

        // Get all the userBodyInfoList where smoker equals to UPDATED_SMOKER
        defaultUserBodyInfoShouldNotBeFound("smoker.equals=" + UPDATED_SMOKER);
    }

    @Test
    @Transactional
    void getAllUserBodyInfosBySmokerIsInShouldWork() throws Exception {
        // Initialize the database
        userBodyInfoRepository.saveAndFlush(userBodyInfo);

        // Get all the userBodyInfoList where smoker in DEFAULT_SMOKER or UPDATED_SMOKER
        defaultUserBodyInfoShouldBeFound("smoker.in=" + DEFAULT_SMOKER + "," + UPDATED_SMOKER);

        // Get all the userBodyInfoList where smoker equals to UPDATED_SMOKER
        defaultUserBodyInfoShouldNotBeFound("smoker.in=" + UPDATED_SMOKER);
    }

    @Test
    @Transactional
    void getAllUserBodyInfosBySmokerIsNullOrNotNull() throws Exception {
        // Initialize the database
        userBodyInfoRepository.saveAndFlush(userBodyInfo);

        // Get all the userBodyInfoList where smoker is not null
        defaultUserBodyInfoShouldBeFound("smoker.specified=true");

        // Get all the userBodyInfoList where smoker is null
        defaultUserBodyInfoShouldNotBeFound("smoker.specified=false");
    }

    @Test
    @Transactional
    void getAllUserBodyInfosByWaightKgIsEqualToSomething() throws Exception {
        // Initialize the database
        userBodyInfoRepository.saveAndFlush(userBodyInfo);

        // Get all the userBodyInfoList where waightKg equals to DEFAULT_WAIGHT_KG
        defaultUserBodyInfoShouldBeFound("waightKg.equals=" + DEFAULT_WAIGHT_KG);

        // Get all the userBodyInfoList where waightKg equals to UPDATED_WAIGHT_KG
        defaultUserBodyInfoShouldNotBeFound("waightKg.equals=" + UPDATED_WAIGHT_KG);
    }

    @Test
    @Transactional
    void getAllUserBodyInfosByWaightKgIsInShouldWork() throws Exception {
        // Initialize the database
        userBodyInfoRepository.saveAndFlush(userBodyInfo);

        // Get all the userBodyInfoList where waightKg in DEFAULT_WAIGHT_KG or UPDATED_WAIGHT_KG
        defaultUserBodyInfoShouldBeFound("waightKg.in=" + DEFAULT_WAIGHT_KG + "," + UPDATED_WAIGHT_KG);

        // Get all the userBodyInfoList where waightKg equals to UPDATED_WAIGHT_KG
        defaultUserBodyInfoShouldNotBeFound("waightKg.in=" + UPDATED_WAIGHT_KG);
    }

    @Test
    @Transactional
    void getAllUserBodyInfosByWaightKgIsNullOrNotNull() throws Exception {
        // Initialize the database
        userBodyInfoRepository.saveAndFlush(userBodyInfo);

        // Get all the userBodyInfoList where waightKg is not null
        defaultUserBodyInfoShouldBeFound("waightKg.specified=true");

        // Get all the userBodyInfoList where waightKg is null
        defaultUserBodyInfoShouldNotBeFound("waightKg.specified=false");
    }

    @Test
    @Transactional
    void getAllUserBodyInfosByWaightKgIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        userBodyInfoRepository.saveAndFlush(userBodyInfo);

        // Get all the userBodyInfoList where waightKg is greater than or equal to DEFAULT_WAIGHT_KG
        defaultUserBodyInfoShouldBeFound("waightKg.greaterThanOrEqual=" + DEFAULT_WAIGHT_KG);

        // Get all the userBodyInfoList where waightKg is greater than or equal to UPDATED_WAIGHT_KG
        defaultUserBodyInfoShouldNotBeFound("waightKg.greaterThanOrEqual=" + UPDATED_WAIGHT_KG);
    }

    @Test
    @Transactional
    void getAllUserBodyInfosByWaightKgIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        userBodyInfoRepository.saveAndFlush(userBodyInfo);

        // Get all the userBodyInfoList where waightKg is less than or equal to DEFAULT_WAIGHT_KG
        defaultUserBodyInfoShouldBeFound("waightKg.lessThanOrEqual=" + DEFAULT_WAIGHT_KG);

        // Get all the userBodyInfoList where waightKg is less than or equal to SMALLER_WAIGHT_KG
        defaultUserBodyInfoShouldNotBeFound("waightKg.lessThanOrEqual=" + SMALLER_WAIGHT_KG);
    }

    @Test
    @Transactional
    void getAllUserBodyInfosByWaightKgIsLessThanSomething() throws Exception {
        // Initialize the database
        userBodyInfoRepository.saveAndFlush(userBodyInfo);

        // Get all the userBodyInfoList where waightKg is less than DEFAULT_WAIGHT_KG
        defaultUserBodyInfoShouldNotBeFound("waightKg.lessThan=" + DEFAULT_WAIGHT_KG);

        // Get all the userBodyInfoList where waightKg is less than UPDATED_WAIGHT_KG
        defaultUserBodyInfoShouldBeFound("waightKg.lessThan=" + UPDATED_WAIGHT_KG);
    }

    @Test
    @Transactional
    void getAllUserBodyInfosByWaightKgIsGreaterThanSomething() throws Exception {
        // Initialize the database
        userBodyInfoRepository.saveAndFlush(userBodyInfo);

        // Get all the userBodyInfoList where waightKg is greater than DEFAULT_WAIGHT_KG
        defaultUserBodyInfoShouldNotBeFound("waightKg.greaterThan=" + DEFAULT_WAIGHT_KG);

        // Get all the userBodyInfoList where waightKg is greater than SMALLER_WAIGHT_KG
        defaultUserBodyInfoShouldBeFound("waightKg.greaterThan=" + SMALLER_WAIGHT_KG);
    }

    @Test
    @Transactional
    void getAllUserBodyInfosByHeightCmIsEqualToSomething() throws Exception {
        // Initialize the database
        userBodyInfoRepository.saveAndFlush(userBodyInfo);

        // Get all the userBodyInfoList where heightCm equals to DEFAULT_HEIGHT_CM
        defaultUserBodyInfoShouldBeFound("heightCm.equals=" + DEFAULT_HEIGHT_CM);

        // Get all the userBodyInfoList where heightCm equals to UPDATED_HEIGHT_CM
        defaultUserBodyInfoShouldNotBeFound("heightCm.equals=" + UPDATED_HEIGHT_CM);
    }

    @Test
    @Transactional
    void getAllUserBodyInfosByHeightCmIsInShouldWork() throws Exception {
        // Initialize the database
        userBodyInfoRepository.saveAndFlush(userBodyInfo);

        // Get all the userBodyInfoList where heightCm in DEFAULT_HEIGHT_CM or UPDATED_HEIGHT_CM
        defaultUserBodyInfoShouldBeFound("heightCm.in=" + DEFAULT_HEIGHT_CM + "," + UPDATED_HEIGHT_CM);

        // Get all the userBodyInfoList where heightCm equals to UPDATED_HEIGHT_CM
        defaultUserBodyInfoShouldNotBeFound("heightCm.in=" + UPDATED_HEIGHT_CM);
    }

    @Test
    @Transactional
    void getAllUserBodyInfosByHeightCmIsNullOrNotNull() throws Exception {
        // Initialize the database
        userBodyInfoRepository.saveAndFlush(userBodyInfo);

        // Get all the userBodyInfoList where heightCm is not null
        defaultUserBodyInfoShouldBeFound("heightCm.specified=true");

        // Get all the userBodyInfoList where heightCm is null
        defaultUserBodyInfoShouldNotBeFound("heightCm.specified=false");
    }

    @Test
    @Transactional
    void getAllUserBodyInfosByHeightCmIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        userBodyInfoRepository.saveAndFlush(userBodyInfo);

        // Get all the userBodyInfoList where heightCm is greater than or equal to DEFAULT_HEIGHT_CM
        defaultUserBodyInfoShouldBeFound("heightCm.greaterThanOrEqual=" + DEFAULT_HEIGHT_CM);

        // Get all the userBodyInfoList where heightCm is greater than or equal to UPDATED_HEIGHT_CM
        defaultUserBodyInfoShouldNotBeFound("heightCm.greaterThanOrEqual=" + UPDATED_HEIGHT_CM);
    }

    @Test
    @Transactional
    void getAllUserBodyInfosByHeightCmIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        userBodyInfoRepository.saveAndFlush(userBodyInfo);

        // Get all the userBodyInfoList where heightCm is less than or equal to DEFAULT_HEIGHT_CM
        defaultUserBodyInfoShouldBeFound("heightCm.lessThanOrEqual=" + DEFAULT_HEIGHT_CM);

        // Get all the userBodyInfoList where heightCm is less than or equal to SMALLER_HEIGHT_CM
        defaultUserBodyInfoShouldNotBeFound("heightCm.lessThanOrEqual=" + SMALLER_HEIGHT_CM);
    }

    @Test
    @Transactional
    void getAllUserBodyInfosByHeightCmIsLessThanSomething() throws Exception {
        // Initialize the database
        userBodyInfoRepository.saveAndFlush(userBodyInfo);

        // Get all the userBodyInfoList where heightCm is less than DEFAULT_HEIGHT_CM
        defaultUserBodyInfoShouldNotBeFound("heightCm.lessThan=" + DEFAULT_HEIGHT_CM);

        // Get all the userBodyInfoList where heightCm is less than UPDATED_HEIGHT_CM
        defaultUserBodyInfoShouldBeFound("heightCm.lessThan=" + UPDATED_HEIGHT_CM);
    }

    @Test
    @Transactional
    void getAllUserBodyInfosByHeightCmIsGreaterThanSomething() throws Exception {
        // Initialize the database
        userBodyInfoRepository.saveAndFlush(userBodyInfo);

        // Get all the userBodyInfoList where heightCm is greater than DEFAULT_HEIGHT_CM
        defaultUserBodyInfoShouldNotBeFound("heightCm.greaterThan=" + DEFAULT_HEIGHT_CM);

        // Get all the userBodyInfoList where heightCm is greater than SMALLER_HEIGHT_CM
        defaultUserBodyInfoShouldBeFound("heightCm.greaterThan=" + SMALLER_HEIGHT_CM);
    }

    @Test
    @Transactional
    void getAllUserBodyInfosByBodyHealthScoreIsEqualToSomething() throws Exception {
        // Initialize the database
        userBodyInfoRepository.saveAndFlush(userBodyInfo);

        // Get all the userBodyInfoList where bodyHealthScore equals to DEFAULT_BODY_HEALTH_SCORE
        defaultUserBodyInfoShouldBeFound("bodyHealthScore.equals=" + DEFAULT_BODY_HEALTH_SCORE);

        // Get all the userBodyInfoList where bodyHealthScore equals to UPDATED_BODY_HEALTH_SCORE
        defaultUserBodyInfoShouldNotBeFound("bodyHealthScore.equals=" + UPDATED_BODY_HEALTH_SCORE);
    }

    @Test
    @Transactional
    void getAllUserBodyInfosByBodyHealthScoreIsInShouldWork() throws Exception {
        // Initialize the database
        userBodyInfoRepository.saveAndFlush(userBodyInfo);

        // Get all the userBodyInfoList where bodyHealthScore in DEFAULT_BODY_HEALTH_SCORE or UPDATED_BODY_HEALTH_SCORE
        defaultUserBodyInfoShouldBeFound("bodyHealthScore.in=" + DEFAULT_BODY_HEALTH_SCORE + "," + UPDATED_BODY_HEALTH_SCORE);

        // Get all the userBodyInfoList where bodyHealthScore equals to UPDATED_BODY_HEALTH_SCORE
        defaultUserBodyInfoShouldNotBeFound("bodyHealthScore.in=" + UPDATED_BODY_HEALTH_SCORE);
    }

    @Test
    @Transactional
    void getAllUserBodyInfosByBodyHealthScoreIsNullOrNotNull() throws Exception {
        // Initialize the database
        userBodyInfoRepository.saveAndFlush(userBodyInfo);

        // Get all the userBodyInfoList where bodyHealthScore is not null
        defaultUserBodyInfoShouldBeFound("bodyHealthScore.specified=true");

        // Get all the userBodyInfoList where bodyHealthScore is null
        defaultUserBodyInfoShouldNotBeFound("bodyHealthScore.specified=false");
    }

    @Test
    @Transactional
    void getAllUserBodyInfosByBodyHealthScoreIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        userBodyInfoRepository.saveAndFlush(userBodyInfo);

        // Get all the userBodyInfoList where bodyHealthScore is greater than or equal to DEFAULT_BODY_HEALTH_SCORE
        defaultUserBodyInfoShouldBeFound("bodyHealthScore.greaterThanOrEqual=" + DEFAULT_BODY_HEALTH_SCORE);

        // Get all the userBodyInfoList where bodyHealthScore is greater than or equal to UPDATED_BODY_HEALTH_SCORE
        defaultUserBodyInfoShouldNotBeFound("bodyHealthScore.greaterThanOrEqual=" + UPDATED_BODY_HEALTH_SCORE);
    }

    @Test
    @Transactional
    void getAllUserBodyInfosByBodyHealthScoreIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        userBodyInfoRepository.saveAndFlush(userBodyInfo);

        // Get all the userBodyInfoList where bodyHealthScore is less than or equal to DEFAULT_BODY_HEALTH_SCORE
        defaultUserBodyInfoShouldBeFound("bodyHealthScore.lessThanOrEqual=" + DEFAULT_BODY_HEALTH_SCORE);

        // Get all the userBodyInfoList where bodyHealthScore is less than or equal to SMALLER_BODY_HEALTH_SCORE
        defaultUserBodyInfoShouldNotBeFound("bodyHealthScore.lessThanOrEqual=" + SMALLER_BODY_HEALTH_SCORE);
    }

    @Test
    @Transactional
    void getAllUserBodyInfosByBodyHealthScoreIsLessThanSomething() throws Exception {
        // Initialize the database
        userBodyInfoRepository.saveAndFlush(userBodyInfo);

        // Get all the userBodyInfoList where bodyHealthScore is less than DEFAULT_BODY_HEALTH_SCORE
        defaultUserBodyInfoShouldNotBeFound("bodyHealthScore.lessThan=" + DEFAULT_BODY_HEALTH_SCORE);

        // Get all the userBodyInfoList where bodyHealthScore is less than UPDATED_BODY_HEALTH_SCORE
        defaultUserBodyInfoShouldBeFound("bodyHealthScore.lessThan=" + UPDATED_BODY_HEALTH_SCORE);
    }

    @Test
    @Transactional
    void getAllUserBodyInfosByBodyHealthScoreIsGreaterThanSomething() throws Exception {
        // Initialize the database
        userBodyInfoRepository.saveAndFlush(userBodyInfo);

        // Get all the userBodyInfoList where bodyHealthScore is greater than DEFAULT_BODY_HEALTH_SCORE
        defaultUserBodyInfoShouldNotBeFound("bodyHealthScore.greaterThan=" + DEFAULT_BODY_HEALTH_SCORE);

        // Get all the userBodyInfoList where bodyHealthScore is greater than SMALLER_BODY_HEALTH_SCORE
        defaultUserBodyInfoShouldBeFound("bodyHealthScore.greaterThan=" + SMALLER_BODY_HEALTH_SCORE);
    }

    @Test
    @Transactional
    void getAllUserBodyInfosByCardiovascularRiskIsEqualToSomething() throws Exception {
        // Initialize the database
        userBodyInfoRepository.saveAndFlush(userBodyInfo);

        // Get all the userBodyInfoList where cardiovascularRisk equals to DEFAULT_CARDIOVASCULAR_RISK
        defaultUserBodyInfoShouldBeFound("cardiovascularRisk.equals=" + DEFAULT_CARDIOVASCULAR_RISK);

        // Get all the userBodyInfoList where cardiovascularRisk equals to UPDATED_CARDIOVASCULAR_RISK
        defaultUserBodyInfoShouldNotBeFound("cardiovascularRisk.equals=" + UPDATED_CARDIOVASCULAR_RISK);
    }

    @Test
    @Transactional
    void getAllUserBodyInfosByCardiovascularRiskIsInShouldWork() throws Exception {
        // Initialize the database
        userBodyInfoRepository.saveAndFlush(userBodyInfo);

        // Get all the userBodyInfoList where cardiovascularRisk in DEFAULT_CARDIOVASCULAR_RISK or UPDATED_CARDIOVASCULAR_RISK
        defaultUserBodyInfoShouldBeFound("cardiovascularRisk.in=" + DEFAULT_CARDIOVASCULAR_RISK + "," + UPDATED_CARDIOVASCULAR_RISK);

        // Get all the userBodyInfoList where cardiovascularRisk equals to UPDATED_CARDIOVASCULAR_RISK
        defaultUserBodyInfoShouldNotBeFound("cardiovascularRisk.in=" + UPDATED_CARDIOVASCULAR_RISK);
    }

    @Test
    @Transactional
    void getAllUserBodyInfosByCardiovascularRiskIsNullOrNotNull() throws Exception {
        // Initialize the database
        userBodyInfoRepository.saveAndFlush(userBodyInfo);

        // Get all the userBodyInfoList where cardiovascularRisk is not null
        defaultUserBodyInfoShouldBeFound("cardiovascularRisk.specified=true");

        // Get all the userBodyInfoList where cardiovascularRisk is null
        defaultUserBodyInfoShouldNotBeFound("cardiovascularRisk.specified=false");
    }

    @Test
    @Transactional
    void getAllUserBodyInfosByCardiovascularRiskIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        userBodyInfoRepository.saveAndFlush(userBodyInfo);

        // Get all the userBodyInfoList where cardiovascularRisk is greater than or equal to DEFAULT_CARDIOVASCULAR_RISK
        defaultUserBodyInfoShouldBeFound("cardiovascularRisk.greaterThanOrEqual=" + DEFAULT_CARDIOVASCULAR_RISK);

        // Get all the userBodyInfoList where cardiovascularRisk is greater than or equal to UPDATED_CARDIOVASCULAR_RISK
        defaultUserBodyInfoShouldNotBeFound("cardiovascularRisk.greaterThanOrEqual=" + UPDATED_CARDIOVASCULAR_RISK);
    }

    @Test
    @Transactional
    void getAllUserBodyInfosByCardiovascularRiskIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        userBodyInfoRepository.saveAndFlush(userBodyInfo);

        // Get all the userBodyInfoList where cardiovascularRisk is less than or equal to DEFAULT_CARDIOVASCULAR_RISK
        defaultUserBodyInfoShouldBeFound("cardiovascularRisk.lessThanOrEqual=" + DEFAULT_CARDIOVASCULAR_RISK);

        // Get all the userBodyInfoList where cardiovascularRisk is less than or equal to SMALLER_CARDIOVASCULAR_RISK
        defaultUserBodyInfoShouldNotBeFound("cardiovascularRisk.lessThanOrEqual=" + SMALLER_CARDIOVASCULAR_RISK);
    }

    @Test
    @Transactional
    void getAllUserBodyInfosByCardiovascularRiskIsLessThanSomething() throws Exception {
        // Initialize the database
        userBodyInfoRepository.saveAndFlush(userBodyInfo);

        // Get all the userBodyInfoList where cardiovascularRisk is less than DEFAULT_CARDIOVASCULAR_RISK
        defaultUserBodyInfoShouldNotBeFound("cardiovascularRisk.lessThan=" + DEFAULT_CARDIOVASCULAR_RISK);

        // Get all the userBodyInfoList where cardiovascularRisk is less than UPDATED_CARDIOVASCULAR_RISK
        defaultUserBodyInfoShouldBeFound("cardiovascularRisk.lessThan=" + UPDATED_CARDIOVASCULAR_RISK);
    }

    @Test
    @Transactional
    void getAllUserBodyInfosByCardiovascularRiskIsGreaterThanSomething() throws Exception {
        // Initialize the database
        userBodyInfoRepository.saveAndFlush(userBodyInfo);

        // Get all the userBodyInfoList where cardiovascularRisk is greater than DEFAULT_CARDIOVASCULAR_RISK
        defaultUserBodyInfoShouldNotBeFound("cardiovascularRisk.greaterThan=" + DEFAULT_CARDIOVASCULAR_RISK);

        // Get all the userBodyInfoList where cardiovascularRisk is greater than SMALLER_CARDIOVASCULAR_RISK
        defaultUserBodyInfoShouldBeFound("cardiovascularRisk.greaterThan=" + SMALLER_CARDIOVASCULAR_RISK);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultUserBodyInfoShouldBeFound(String filter) throws Exception {
        restUserBodyInfoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userBodyInfo.getId().toString())))
            .andExpect(jsonPath("$.[*].usuarioId").value(hasItem(DEFAULT_USUARIO_ID)))
            .andExpect(jsonPath("$.[*].empresaId").value(hasItem(DEFAULT_EMPRESA_ID)))
            .andExpect(jsonPath("$.[*].waistCircumference").value(hasItem(DEFAULT_WAIST_CIRCUMFERENCE.doubleValue())))
            .andExpect(jsonPath("$.[*].hipCircumference").value(hasItem(DEFAULT_HIP_CIRCUMFERENCE.doubleValue())))
            .andExpect(jsonPath("$.[*].chestCircumference").value(hasItem(DEFAULT_CHEST_CIRCUMFERENCE.doubleValue())))
            .andExpect(jsonPath("$.[*].boneCompositionPercentaje").value(hasItem(DEFAULT_BONE_COMPOSITION_PERCENTAJE.doubleValue())))
            .andExpect(jsonPath("$.[*].muscleCompositionPercentaje").value(hasItem(DEFAULT_MUSCLE_COMPOSITION_PERCENTAJE.doubleValue())))
            .andExpect(jsonPath("$.[*].smoker").value(hasItem(DEFAULT_SMOKER.booleanValue())))
            .andExpect(jsonPath("$.[*].waightKg").value(hasItem(DEFAULT_WAIGHT_KG.doubleValue())))
            .andExpect(jsonPath("$.[*].heightCm").value(hasItem(DEFAULT_HEIGHT_CM.doubleValue())))
            .andExpect(jsonPath("$.[*].bodyHealthScore").value(hasItem(DEFAULT_BODY_HEALTH_SCORE.doubleValue())))
            .andExpect(jsonPath("$.[*].cardiovascularRisk").value(hasItem(DEFAULT_CARDIOVASCULAR_RISK)));

        // Check, that the count call also returns 1
        restUserBodyInfoMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultUserBodyInfoShouldNotBeFound(String filter) throws Exception {
        restUserBodyInfoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restUserBodyInfoMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingUserBodyInfo() throws Exception {
        // Get the userBodyInfo
        restUserBodyInfoMockMvc.perform(get(ENTITY_API_URL_ID, UUID.randomUUID().toString())).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingUserBodyInfo() throws Exception {
        // Initialize the database
        userBodyInfoRepository.saveAndFlush(userBodyInfo);

        int databaseSizeBeforeUpdate = userBodyInfoRepository.findAll().size();

        // Update the userBodyInfo
        UserBodyInfo updatedUserBodyInfo = userBodyInfoRepository.findById(userBodyInfo.getId()).get();
        // Disconnect from session so that the updates on updatedUserBodyInfo are not directly saved in db
        em.detach(updatedUserBodyInfo);
        updatedUserBodyInfo
            .usuarioId(UPDATED_USUARIO_ID)
            .empresaId(UPDATED_EMPRESA_ID)
            .waistCircumference(UPDATED_WAIST_CIRCUMFERENCE)
            .hipCircumference(UPDATED_HIP_CIRCUMFERENCE)
            .chestCircumference(UPDATED_CHEST_CIRCUMFERENCE)
            .boneCompositionPercentaje(UPDATED_BONE_COMPOSITION_PERCENTAJE)
            .muscleCompositionPercentaje(UPDATED_MUSCLE_COMPOSITION_PERCENTAJE)
            .smoker(UPDATED_SMOKER)
            .waightKg(UPDATED_WAIGHT_KG)
            .heightCm(UPDATED_HEIGHT_CM)
            .bodyHealthScore(UPDATED_BODY_HEALTH_SCORE)
            .cardiovascularRisk(UPDATED_CARDIOVASCULAR_RISK);

        restUserBodyInfoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedUserBodyInfo.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedUserBodyInfo))
            )
            .andExpect(status().isOk());

        // Validate the UserBodyInfo in the database
        List<UserBodyInfo> userBodyInfoList = userBodyInfoRepository.findAll();
        assertThat(userBodyInfoList).hasSize(databaseSizeBeforeUpdate);
        UserBodyInfo testUserBodyInfo = userBodyInfoList.get(userBodyInfoList.size() - 1);
        assertThat(testUserBodyInfo.getUsuarioId()).isEqualTo(UPDATED_USUARIO_ID);
        assertThat(testUserBodyInfo.getEmpresaId()).isEqualTo(UPDATED_EMPRESA_ID);
        assertThat(testUserBodyInfo.getWaistCircumference()).isEqualTo(UPDATED_WAIST_CIRCUMFERENCE);
        assertThat(testUserBodyInfo.getHipCircumference()).isEqualTo(UPDATED_HIP_CIRCUMFERENCE);
        assertThat(testUserBodyInfo.getChestCircumference()).isEqualTo(UPDATED_CHEST_CIRCUMFERENCE);
        assertThat(testUserBodyInfo.getBoneCompositionPercentaje()).isEqualTo(UPDATED_BONE_COMPOSITION_PERCENTAJE);
        assertThat(testUserBodyInfo.getMuscleCompositionPercentaje()).isEqualTo(UPDATED_MUSCLE_COMPOSITION_PERCENTAJE);
        assertThat(testUserBodyInfo.getSmoker()).isEqualTo(UPDATED_SMOKER);
        assertThat(testUserBodyInfo.getWaightKg()).isEqualTo(UPDATED_WAIGHT_KG);
        assertThat(testUserBodyInfo.getHeightCm()).isEqualTo(UPDATED_HEIGHT_CM);
        assertThat(testUserBodyInfo.getBodyHealthScore()).isEqualTo(UPDATED_BODY_HEALTH_SCORE);
        assertThat(testUserBodyInfo.getCardiovascularRisk()).isEqualTo(UPDATED_CARDIOVASCULAR_RISK);
    }

    @Test
    @Transactional
    void putNonExistingUserBodyInfo() throws Exception {
        int databaseSizeBeforeUpdate = userBodyInfoRepository.findAll().size();
        userBodyInfo.setId(UUID.randomUUID());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserBodyInfoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, userBodyInfo.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(userBodyInfo))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserBodyInfo in the database
        List<UserBodyInfo> userBodyInfoList = userBodyInfoRepository.findAll();
        assertThat(userBodyInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchUserBodyInfo() throws Exception {
        int databaseSizeBeforeUpdate = userBodyInfoRepository.findAll().size();
        userBodyInfo.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserBodyInfoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(userBodyInfo))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserBodyInfo in the database
        List<UserBodyInfo> userBodyInfoList = userBodyInfoRepository.findAll();
        assertThat(userBodyInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamUserBodyInfo() throws Exception {
        int databaseSizeBeforeUpdate = userBodyInfoRepository.findAll().size();
        userBodyInfo.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserBodyInfoMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(userBodyInfo)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the UserBodyInfo in the database
        List<UserBodyInfo> userBodyInfoList = userBodyInfoRepository.findAll();
        assertThat(userBodyInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateUserBodyInfoWithPatch() throws Exception {
        // Initialize the database
        userBodyInfoRepository.saveAndFlush(userBodyInfo);

        int databaseSizeBeforeUpdate = userBodyInfoRepository.findAll().size();

        // Update the userBodyInfo using partial update
        UserBodyInfo partialUpdatedUserBodyInfo = new UserBodyInfo();
        partialUpdatedUserBodyInfo.setId(userBodyInfo.getId());

        partialUpdatedUserBodyInfo
            .waistCircumference(UPDATED_WAIST_CIRCUMFERENCE)
            .chestCircumference(UPDATED_CHEST_CIRCUMFERENCE)
            .boneCompositionPercentaje(UPDATED_BONE_COMPOSITION_PERCENTAJE)
            .cardiovascularRisk(UPDATED_CARDIOVASCULAR_RISK);

        restUserBodyInfoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedUserBodyInfo.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedUserBodyInfo))
            )
            .andExpect(status().isOk());

        // Validate the UserBodyInfo in the database
        List<UserBodyInfo> userBodyInfoList = userBodyInfoRepository.findAll();
        assertThat(userBodyInfoList).hasSize(databaseSizeBeforeUpdate);
        UserBodyInfo testUserBodyInfo = userBodyInfoList.get(userBodyInfoList.size() - 1);
        assertThat(testUserBodyInfo.getUsuarioId()).isEqualTo(DEFAULT_USUARIO_ID);
        assertThat(testUserBodyInfo.getEmpresaId()).isEqualTo(DEFAULT_EMPRESA_ID);
        assertThat(testUserBodyInfo.getWaistCircumference()).isEqualTo(UPDATED_WAIST_CIRCUMFERENCE);
        assertThat(testUserBodyInfo.getHipCircumference()).isEqualTo(DEFAULT_HIP_CIRCUMFERENCE);
        assertThat(testUserBodyInfo.getChestCircumference()).isEqualTo(UPDATED_CHEST_CIRCUMFERENCE);
        assertThat(testUserBodyInfo.getBoneCompositionPercentaje()).isEqualTo(UPDATED_BONE_COMPOSITION_PERCENTAJE);
        assertThat(testUserBodyInfo.getMuscleCompositionPercentaje()).isEqualTo(DEFAULT_MUSCLE_COMPOSITION_PERCENTAJE);
        assertThat(testUserBodyInfo.getSmoker()).isEqualTo(DEFAULT_SMOKER);
        assertThat(testUserBodyInfo.getWaightKg()).isEqualTo(DEFAULT_WAIGHT_KG);
        assertThat(testUserBodyInfo.getHeightCm()).isEqualTo(DEFAULT_HEIGHT_CM);
        assertThat(testUserBodyInfo.getBodyHealthScore()).isEqualTo(DEFAULT_BODY_HEALTH_SCORE);
        assertThat(testUserBodyInfo.getCardiovascularRisk()).isEqualTo(UPDATED_CARDIOVASCULAR_RISK);
    }

    @Test
    @Transactional
    void fullUpdateUserBodyInfoWithPatch() throws Exception {
        // Initialize the database
        userBodyInfoRepository.saveAndFlush(userBodyInfo);

        int databaseSizeBeforeUpdate = userBodyInfoRepository.findAll().size();

        // Update the userBodyInfo using partial update
        UserBodyInfo partialUpdatedUserBodyInfo = new UserBodyInfo();
        partialUpdatedUserBodyInfo.setId(userBodyInfo.getId());

        partialUpdatedUserBodyInfo
            .usuarioId(UPDATED_USUARIO_ID)
            .empresaId(UPDATED_EMPRESA_ID)
            .waistCircumference(UPDATED_WAIST_CIRCUMFERENCE)
            .hipCircumference(UPDATED_HIP_CIRCUMFERENCE)
            .chestCircumference(UPDATED_CHEST_CIRCUMFERENCE)
            .boneCompositionPercentaje(UPDATED_BONE_COMPOSITION_PERCENTAJE)
            .muscleCompositionPercentaje(UPDATED_MUSCLE_COMPOSITION_PERCENTAJE)
            .smoker(UPDATED_SMOKER)
            .waightKg(UPDATED_WAIGHT_KG)
            .heightCm(UPDATED_HEIGHT_CM)
            .bodyHealthScore(UPDATED_BODY_HEALTH_SCORE)
            .cardiovascularRisk(UPDATED_CARDIOVASCULAR_RISK);

        restUserBodyInfoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedUserBodyInfo.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedUserBodyInfo))
            )
            .andExpect(status().isOk());

        // Validate the UserBodyInfo in the database
        List<UserBodyInfo> userBodyInfoList = userBodyInfoRepository.findAll();
        assertThat(userBodyInfoList).hasSize(databaseSizeBeforeUpdate);
        UserBodyInfo testUserBodyInfo = userBodyInfoList.get(userBodyInfoList.size() - 1);
        assertThat(testUserBodyInfo.getUsuarioId()).isEqualTo(UPDATED_USUARIO_ID);
        assertThat(testUserBodyInfo.getEmpresaId()).isEqualTo(UPDATED_EMPRESA_ID);
        assertThat(testUserBodyInfo.getWaistCircumference()).isEqualTo(UPDATED_WAIST_CIRCUMFERENCE);
        assertThat(testUserBodyInfo.getHipCircumference()).isEqualTo(UPDATED_HIP_CIRCUMFERENCE);
        assertThat(testUserBodyInfo.getChestCircumference()).isEqualTo(UPDATED_CHEST_CIRCUMFERENCE);
        assertThat(testUserBodyInfo.getBoneCompositionPercentaje()).isEqualTo(UPDATED_BONE_COMPOSITION_PERCENTAJE);
        assertThat(testUserBodyInfo.getMuscleCompositionPercentaje()).isEqualTo(UPDATED_MUSCLE_COMPOSITION_PERCENTAJE);
        assertThat(testUserBodyInfo.getSmoker()).isEqualTo(UPDATED_SMOKER);
        assertThat(testUserBodyInfo.getWaightKg()).isEqualTo(UPDATED_WAIGHT_KG);
        assertThat(testUserBodyInfo.getHeightCm()).isEqualTo(UPDATED_HEIGHT_CM);
        assertThat(testUserBodyInfo.getBodyHealthScore()).isEqualTo(UPDATED_BODY_HEALTH_SCORE);
        assertThat(testUserBodyInfo.getCardiovascularRisk()).isEqualTo(UPDATED_CARDIOVASCULAR_RISK);
    }

    @Test
    @Transactional
    void patchNonExistingUserBodyInfo() throws Exception {
        int databaseSizeBeforeUpdate = userBodyInfoRepository.findAll().size();
        userBodyInfo.setId(UUID.randomUUID());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserBodyInfoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, userBodyInfo.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(userBodyInfo))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserBodyInfo in the database
        List<UserBodyInfo> userBodyInfoList = userBodyInfoRepository.findAll();
        assertThat(userBodyInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchUserBodyInfo() throws Exception {
        int databaseSizeBeforeUpdate = userBodyInfoRepository.findAll().size();
        userBodyInfo.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserBodyInfoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(userBodyInfo))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserBodyInfo in the database
        List<UserBodyInfo> userBodyInfoList = userBodyInfoRepository.findAll();
        assertThat(userBodyInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamUserBodyInfo() throws Exception {
        int databaseSizeBeforeUpdate = userBodyInfoRepository.findAll().size();
        userBodyInfo.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserBodyInfoMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(userBodyInfo))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the UserBodyInfo in the database
        List<UserBodyInfo> userBodyInfoList = userBodyInfoRepository.findAll();
        assertThat(userBodyInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteUserBodyInfo() throws Exception {
        // Initialize the database
        userBodyInfoRepository.saveAndFlush(userBodyInfo);

        int databaseSizeBeforeDelete = userBodyInfoRepository.findAll().size();

        // Delete the userBodyInfo
        restUserBodyInfoMockMvc
            .perform(delete(ENTITY_API_URL_ID, userBodyInfo.getId().toString()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<UserBodyInfo> userBodyInfoList = userBodyInfoRepository.findAll();
        assertThat(userBodyInfoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
