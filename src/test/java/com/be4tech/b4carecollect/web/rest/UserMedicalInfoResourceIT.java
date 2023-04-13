package com.be4tech.b4carecollect.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.be4tech.b4carecollect.IntegrationTest;
import com.be4tech.b4carecollect.domain.UserMedicalInfo;
import com.be4tech.b4carecollect.repository.UserMedicalInfoRepository;
import com.be4tech.b4carecollect.service.criteria.UserMedicalInfoCriteria;
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
 * Integration tests for the {@link UserMedicalInfoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class UserMedicalInfoResourceIT {

    private static final String DEFAULT_USUARIO_ID = "AAAAAAAAAA";
    private static final String UPDATED_USUARIO_ID = "BBBBBBBBBB";

    private static final String DEFAULT_EMPRESA_ID = "AAAAAAAAAA";
    private static final String UPDATED_EMPRESA_ID = "BBBBBBBBBB";

    private static final Boolean DEFAULT_HYPERTENSION = false;
    private static final Boolean UPDATED_HYPERTENSION = true;

    private static final Boolean DEFAULT_HIGH_GLUCOSE = false;
    private static final Boolean UPDATED_HIGH_GLUCOSE = true;

    private static final Boolean DEFAULT_HIABETES = false;
    private static final Boolean UPDATED_HIABETES = true;

    private static final Float DEFAULT_TOTAL_CHOLESTEROL = 1F;
    private static final Float UPDATED_TOTAL_CHOLESTEROL = 2F;
    private static final Float SMALLER_TOTAL_CHOLESTEROL = 1F - 1F;

    private static final Float DEFAULT_HDL_CHOLESTEROL = 1F;
    private static final Float UPDATED_HDL_CHOLESTEROL = 2F;
    private static final Float SMALLER_HDL_CHOLESTEROL = 1F - 1F;

    private static final String DEFAULT_MEDICAL_MAIN_CONDITION = "AAAAAAAAAA";
    private static final String UPDATED_MEDICAL_MAIN_CONDITION = "BBBBBBBBBB";

    private static final String DEFAULT_MEDICAL_SECUNDARY_CONDITION = "AAAAAAAAAA";
    private static final String UPDATED_MEDICAL_SECUNDARY_CONDITION = "BBBBBBBBBB";

    private static final String DEFAULT_MEDICAL_MAIN_MEDICATION = "AAAAAAAAAA";
    private static final String UPDATED_MEDICAL_MAIN_MEDICATION = "BBBBBBBBBB";

    private static final String DEFAULT_MEDICAL_SECUNDARY_MEDICATION = "AAAAAAAAAA";
    private static final String UPDATED_MEDICAL_SECUNDARY_MEDICATION = "BBBBBBBBBB";

    private static final Integer DEFAULT_MEDICAL_SCORE = 1;
    private static final Integer UPDATED_MEDICAL_SCORE = 2;
    private static final Integer SMALLER_MEDICAL_SCORE = 1 - 1;

    private static final Instant DEFAULT_END_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_END_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/user-medical-infos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private UserMedicalInfoRepository userMedicalInfoRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restUserMedicalInfoMockMvc;

    private UserMedicalInfo userMedicalInfo;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserMedicalInfo createEntity(EntityManager em) {
        UserMedicalInfo userMedicalInfo = new UserMedicalInfo()
            .usuarioId(DEFAULT_USUARIO_ID)
            .empresaId(DEFAULT_EMPRESA_ID)
            .hypertension(DEFAULT_HYPERTENSION)
            .highGlucose(DEFAULT_HIGH_GLUCOSE)
            .hiabetes(DEFAULT_HIABETES)
            .totalCholesterol(DEFAULT_TOTAL_CHOLESTEROL)
            .hdlCholesterol(DEFAULT_HDL_CHOLESTEROL)
            .medicalMainCondition(DEFAULT_MEDICAL_MAIN_CONDITION)
            .medicalSecundaryCondition(DEFAULT_MEDICAL_SECUNDARY_CONDITION)
            .medicalMainMedication(DEFAULT_MEDICAL_MAIN_MEDICATION)
            .medicalSecundaryMedication(DEFAULT_MEDICAL_SECUNDARY_MEDICATION)
            .medicalScore(DEFAULT_MEDICAL_SCORE)
            .endTime(DEFAULT_END_TIME);
        return userMedicalInfo;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserMedicalInfo createUpdatedEntity(EntityManager em) {
        UserMedicalInfo userMedicalInfo = new UserMedicalInfo()
            .usuarioId(UPDATED_USUARIO_ID)
            .empresaId(UPDATED_EMPRESA_ID)
            .hypertension(UPDATED_HYPERTENSION)
            .highGlucose(UPDATED_HIGH_GLUCOSE)
            .hiabetes(UPDATED_HIABETES)
            .totalCholesterol(UPDATED_TOTAL_CHOLESTEROL)
            .hdlCholesterol(UPDATED_HDL_CHOLESTEROL)
            .medicalMainCondition(UPDATED_MEDICAL_MAIN_CONDITION)
            .medicalSecundaryCondition(UPDATED_MEDICAL_SECUNDARY_CONDITION)
            .medicalMainMedication(UPDATED_MEDICAL_MAIN_MEDICATION)
            .medicalSecundaryMedication(UPDATED_MEDICAL_SECUNDARY_MEDICATION)
            .medicalScore(UPDATED_MEDICAL_SCORE)
            .endTime(UPDATED_END_TIME);
        return userMedicalInfo;
    }

    @BeforeEach
    public void initTest() {
        userMedicalInfo = createEntity(em);
    }

    @Test
    @Transactional
    void createUserMedicalInfo() throws Exception {
        int databaseSizeBeforeCreate = userMedicalInfoRepository.findAll().size();
        // Create the UserMedicalInfo
        restUserMedicalInfoMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(userMedicalInfo))
            )
            .andExpect(status().isCreated());

        // Validate the UserMedicalInfo in the database
        List<UserMedicalInfo> userMedicalInfoList = userMedicalInfoRepository.findAll();
        assertThat(userMedicalInfoList).hasSize(databaseSizeBeforeCreate + 1);
        UserMedicalInfo testUserMedicalInfo = userMedicalInfoList.get(userMedicalInfoList.size() - 1);
        assertThat(testUserMedicalInfo.getUsuarioId()).isEqualTo(DEFAULT_USUARIO_ID);
        assertThat(testUserMedicalInfo.getEmpresaId()).isEqualTo(DEFAULT_EMPRESA_ID);
        assertThat(testUserMedicalInfo.getHypertension()).isEqualTo(DEFAULT_HYPERTENSION);
        assertThat(testUserMedicalInfo.getHighGlucose()).isEqualTo(DEFAULT_HIGH_GLUCOSE);
        assertThat(testUserMedicalInfo.getHiabetes()).isEqualTo(DEFAULT_HIABETES);
        assertThat(testUserMedicalInfo.getTotalCholesterol()).isEqualTo(DEFAULT_TOTAL_CHOLESTEROL);
        assertThat(testUserMedicalInfo.getHdlCholesterol()).isEqualTo(DEFAULT_HDL_CHOLESTEROL);
        assertThat(testUserMedicalInfo.getMedicalMainCondition()).isEqualTo(DEFAULT_MEDICAL_MAIN_CONDITION);
        assertThat(testUserMedicalInfo.getMedicalSecundaryCondition()).isEqualTo(DEFAULT_MEDICAL_SECUNDARY_CONDITION);
        assertThat(testUserMedicalInfo.getMedicalMainMedication()).isEqualTo(DEFAULT_MEDICAL_MAIN_MEDICATION);
        assertThat(testUserMedicalInfo.getMedicalSecundaryMedication()).isEqualTo(DEFAULT_MEDICAL_SECUNDARY_MEDICATION);
        assertThat(testUserMedicalInfo.getMedicalScore()).isEqualTo(DEFAULT_MEDICAL_SCORE);
        assertThat(testUserMedicalInfo.getEndTime()).isEqualTo(DEFAULT_END_TIME);
    }

    @Test
    @Transactional
    void createUserMedicalInfoWithExistingId() throws Exception {
        // Create the UserMedicalInfo with an existing ID
        userMedicalInfoRepository.saveAndFlush(userMedicalInfo);

        int databaseSizeBeforeCreate = userMedicalInfoRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserMedicalInfoMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(userMedicalInfo))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserMedicalInfo in the database
        List<UserMedicalInfo> userMedicalInfoList = userMedicalInfoRepository.findAll();
        assertThat(userMedicalInfoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllUserMedicalInfos() throws Exception {
        // Initialize the database
        userMedicalInfoRepository.saveAndFlush(userMedicalInfo);

        // Get all the userMedicalInfoList
        restUserMedicalInfoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userMedicalInfo.getId().toString())))
            .andExpect(jsonPath("$.[*].usuarioId").value(hasItem(DEFAULT_USUARIO_ID)))
            .andExpect(jsonPath("$.[*].empresaId").value(hasItem(DEFAULT_EMPRESA_ID)))
            .andExpect(jsonPath("$.[*].hypertension").value(hasItem(DEFAULT_HYPERTENSION.booleanValue())))
            .andExpect(jsonPath("$.[*].highGlucose").value(hasItem(DEFAULT_HIGH_GLUCOSE.booleanValue())))
            .andExpect(jsonPath("$.[*].hiabetes").value(hasItem(DEFAULT_HIABETES.booleanValue())))
            .andExpect(jsonPath("$.[*].totalCholesterol").value(hasItem(DEFAULT_TOTAL_CHOLESTEROL.doubleValue())))
            .andExpect(jsonPath("$.[*].hdlCholesterol").value(hasItem(DEFAULT_HDL_CHOLESTEROL.doubleValue())))
            .andExpect(jsonPath("$.[*].medicalMainCondition").value(hasItem(DEFAULT_MEDICAL_MAIN_CONDITION)))
            .andExpect(jsonPath("$.[*].medicalSecundaryCondition").value(hasItem(DEFAULT_MEDICAL_SECUNDARY_CONDITION)))
            .andExpect(jsonPath("$.[*].medicalMainMedication").value(hasItem(DEFAULT_MEDICAL_MAIN_MEDICATION)))
            .andExpect(jsonPath("$.[*].medicalSecundaryMedication").value(hasItem(DEFAULT_MEDICAL_SECUNDARY_MEDICATION)))
            .andExpect(jsonPath("$.[*].medicalScore").value(hasItem(DEFAULT_MEDICAL_SCORE)))
            .andExpect(jsonPath("$.[*].endTime").value(hasItem(DEFAULT_END_TIME.toString())));
    }

    @Test
    @Transactional
    void getUserMedicalInfo() throws Exception {
        // Initialize the database
        userMedicalInfoRepository.saveAndFlush(userMedicalInfo);

        // Get the userMedicalInfo
        restUserMedicalInfoMockMvc
            .perform(get(ENTITY_API_URL_ID, userMedicalInfo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(userMedicalInfo.getId().toString()))
            .andExpect(jsonPath("$.usuarioId").value(DEFAULT_USUARIO_ID))
            .andExpect(jsonPath("$.empresaId").value(DEFAULT_EMPRESA_ID))
            .andExpect(jsonPath("$.hypertension").value(DEFAULT_HYPERTENSION.booleanValue()))
            .andExpect(jsonPath("$.highGlucose").value(DEFAULT_HIGH_GLUCOSE.booleanValue()))
            .andExpect(jsonPath("$.hiabetes").value(DEFAULT_HIABETES.booleanValue()))
            .andExpect(jsonPath("$.totalCholesterol").value(DEFAULT_TOTAL_CHOLESTEROL.doubleValue()))
            .andExpect(jsonPath("$.hdlCholesterol").value(DEFAULT_HDL_CHOLESTEROL.doubleValue()))
            .andExpect(jsonPath("$.medicalMainCondition").value(DEFAULT_MEDICAL_MAIN_CONDITION))
            .andExpect(jsonPath("$.medicalSecundaryCondition").value(DEFAULT_MEDICAL_SECUNDARY_CONDITION))
            .andExpect(jsonPath("$.medicalMainMedication").value(DEFAULT_MEDICAL_MAIN_MEDICATION))
            .andExpect(jsonPath("$.medicalSecundaryMedication").value(DEFAULT_MEDICAL_SECUNDARY_MEDICATION))
            .andExpect(jsonPath("$.medicalScore").value(DEFAULT_MEDICAL_SCORE))
            .andExpect(jsonPath("$.endTime").value(DEFAULT_END_TIME.toString()));
    }

    @Test
    @Transactional
    void getUserMedicalInfosByIdFiltering() throws Exception {
        // Initialize the database
        userMedicalInfoRepository.saveAndFlush(userMedicalInfo);

        UUID id = userMedicalInfo.getId();

        defaultUserMedicalInfoShouldBeFound("id.equals=" + id);
        defaultUserMedicalInfoShouldNotBeFound("id.notEquals=" + id);
    }

    @Test
    @Transactional
    void getAllUserMedicalInfosByUsuarioIdIsEqualToSomething() throws Exception {
        // Initialize the database
        userMedicalInfoRepository.saveAndFlush(userMedicalInfo);

        // Get all the userMedicalInfoList where usuarioId equals to DEFAULT_USUARIO_ID
        defaultUserMedicalInfoShouldBeFound("usuarioId.equals=" + DEFAULT_USUARIO_ID);

        // Get all the userMedicalInfoList where usuarioId equals to UPDATED_USUARIO_ID
        defaultUserMedicalInfoShouldNotBeFound("usuarioId.equals=" + UPDATED_USUARIO_ID);
    }

    @Test
    @Transactional
    void getAllUserMedicalInfosByUsuarioIdIsInShouldWork() throws Exception {
        // Initialize the database
        userMedicalInfoRepository.saveAndFlush(userMedicalInfo);

        // Get all the userMedicalInfoList where usuarioId in DEFAULT_USUARIO_ID or UPDATED_USUARIO_ID
        defaultUserMedicalInfoShouldBeFound("usuarioId.in=" + DEFAULT_USUARIO_ID + "," + UPDATED_USUARIO_ID);

        // Get all the userMedicalInfoList where usuarioId equals to UPDATED_USUARIO_ID
        defaultUserMedicalInfoShouldNotBeFound("usuarioId.in=" + UPDATED_USUARIO_ID);
    }

    @Test
    @Transactional
    void getAllUserMedicalInfosByUsuarioIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        userMedicalInfoRepository.saveAndFlush(userMedicalInfo);

        // Get all the userMedicalInfoList where usuarioId is not null
        defaultUserMedicalInfoShouldBeFound("usuarioId.specified=true");

        // Get all the userMedicalInfoList where usuarioId is null
        defaultUserMedicalInfoShouldNotBeFound("usuarioId.specified=false");
    }

    @Test
    @Transactional
    void getAllUserMedicalInfosByUsuarioIdContainsSomething() throws Exception {
        // Initialize the database
        userMedicalInfoRepository.saveAndFlush(userMedicalInfo);

        // Get all the userMedicalInfoList where usuarioId contains DEFAULT_USUARIO_ID
        defaultUserMedicalInfoShouldBeFound("usuarioId.contains=" + DEFAULT_USUARIO_ID);

        // Get all the userMedicalInfoList where usuarioId contains UPDATED_USUARIO_ID
        defaultUserMedicalInfoShouldNotBeFound("usuarioId.contains=" + UPDATED_USUARIO_ID);
    }

    @Test
    @Transactional
    void getAllUserMedicalInfosByUsuarioIdNotContainsSomething() throws Exception {
        // Initialize the database
        userMedicalInfoRepository.saveAndFlush(userMedicalInfo);

        // Get all the userMedicalInfoList where usuarioId does not contain DEFAULT_USUARIO_ID
        defaultUserMedicalInfoShouldNotBeFound("usuarioId.doesNotContain=" + DEFAULT_USUARIO_ID);

        // Get all the userMedicalInfoList where usuarioId does not contain UPDATED_USUARIO_ID
        defaultUserMedicalInfoShouldBeFound("usuarioId.doesNotContain=" + UPDATED_USUARIO_ID);
    }

    @Test
    @Transactional
    void getAllUserMedicalInfosByEmpresaIdIsEqualToSomething() throws Exception {
        // Initialize the database
        userMedicalInfoRepository.saveAndFlush(userMedicalInfo);

        // Get all the userMedicalInfoList where empresaId equals to DEFAULT_EMPRESA_ID
        defaultUserMedicalInfoShouldBeFound("empresaId.equals=" + DEFAULT_EMPRESA_ID);

        // Get all the userMedicalInfoList where empresaId equals to UPDATED_EMPRESA_ID
        defaultUserMedicalInfoShouldNotBeFound("empresaId.equals=" + UPDATED_EMPRESA_ID);
    }

    @Test
    @Transactional
    void getAllUserMedicalInfosByEmpresaIdIsInShouldWork() throws Exception {
        // Initialize the database
        userMedicalInfoRepository.saveAndFlush(userMedicalInfo);

        // Get all the userMedicalInfoList where empresaId in DEFAULT_EMPRESA_ID or UPDATED_EMPRESA_ID
        defaultUserMedicalInfoShouldBeFound("empresaId.in=" + DEFAULT_EMPRESA_ID + "," + UPDATED_EMPRESA_ID);

        // Get all the userMedicalInfoList where empresaId equals to UPDATED_EMPRESA_ID
        defaultUserMedicalInfoShouldNotBeFound("empresaId.in=" + UPDATED_EMPRESA_ID);
    }

    @Test
    @Transactional
    void getAllUserMedicalInfosByEmpresaIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        userMedicalInfoRepository.saveAndFlush(userMedicalInfo);

        // Get all the userMedicalInfoList where empresaId is not null
        defaultUserMedicalInfoShouldBeFound("empresaId.specified=true");

        // Get all the userMedicalInfoList where empresaId is null
        defaultUserMedicalInfoShouldNotBeFound("empresaId.specified=false");
    }

    @Test
    @Transactional
    void getAllUserMedicalInfosByEmpresaIdContainsSomething() throws Exception {
        // Initialize the database
        userMedicalInfoRepository.saveAndFlush(userMedicalInfo);

        // Get all the userMedicalInfoList where empresaId contains DEFAULT_EMPRESA_ID
        defaultUserMedicalInfoShouldBeFound("empresaId.contains=" + DEFAULT_EMPRESA_ID);

        // Get all the userMedicalInfoList where empresaId contains UPDATED_EMPRESA_ID
        defaultUserMedicalInfoShouldNotBeFound("empresaId.contains=" + UPDATED_EMPRESA_ID);
    }

    @Test
    @Transactional
    void getAllUserMedicalInfosByEmpresaIdNotContainsSomething() throws Exception {
        // Initialize the database
        userMedicalInfoRepository.saveAndFlush(userMedicalInfo);

        // Get all the userMedicalInfoList where empresaId does not contain DEFAULT_EMPRESA_ID
        defaultUserMedicalInfoShouldNotBeFound("empresaId.doesNotContain=" + DEFAULT_EMPRESA_ID);

        // Get all the userMedicalInfoList where empresaId does not contain UPDATED_EMPRESA_ID
        defaultUserMedicalInfoShouldBeFound("empresaId.doesNotContain=" + UPDATED_EMPRESA_ID);
    }

    @Test
    @Transactional
    void getAllUserMedicalInfosByHypertensionIsEqualToSomething() throws Exception {
        // Initialize the database
        userMedicalInfoRepository.saveAndFlush(userMedicalInfo);

        // Get all the userMedicalInfoList where hypertension equals to DEFAULT_HYPERTENSION
        defaultUserMedicalInfoShouldBeFound("hypertension.equals=" + DEFAULT_HYPERTENSION);

        // Get all the userMedicalInfoList where hypertension equals to UPDATED_HYPERTENSION
        defaultUserMedicalInfoShouldNotBeFound("hypertension.equals=" + UPDATED_HYPERTENSION);
    }

    @Test
    @Transactional
    void getAllUserMedicalInfosByHypertensionIsInShouldWork() throws Exception {
        // Initialize the database
        userMedicalInfoRepository.saveAndFlush(userMedicalInfo);

        // Get all the userMedicalInfoList where hypertension in DEFAULT_HYPERTENSION or UPDATED_HYPERTENSION
        defaultUserMedicalInfoShouldBeFound("hypertension.in=" + DEFAULT_HYPERTENSION + "," + UPDATED_HYPERTENSION);

        // Get all the userMedicalInfoList where hypertension equals to UPDATED_HYPERTENSION
        defaultUserMedicalInfoShouldNotBeFound("hypertension.in=" + UPDATED_HYPERTENSION);
    }

    @Test
    @Transactional
    void getAllUserMedicalInfosByHypertensionIsNullOrNotNull() throws Exception {
        // Initialize the database
        userMedicalInfoRepository.saveAndFlush(userMedicalInfo);

        // Get all the userMedicalInfoList where hypertension is not null
        defaultUserMedicalInfoShouldBeFound("hypertension.specified=true");

        // Get all the userMedicalInfoList where hypertension is null
        defaultUserMedicalInfoShouldNotBeFound("hypertension.specified=false");
    }

    @Test
    @Transactional
    void getAllUserMedicalInfosByHighGlucoseIsEqualToSomething() throws Exception {
        // Initialize the database
        userMedicalInfoRepository.saveAndFlush(userMedicalInfo);

        // Get all the userMedicalInfoList where highGlucose equals to DEFAULT_HIGH_GLUCOSE
        defaultUserMedicalInfoShouldBeFound("highGlucose.equals=" + DEFAULT_HIGH_GLUCOSE);

        // Get all the userMedicalInfoList where highGlucose equals to UPDATED_HIGH_GLUCOSE
        defaultUserMedicalInfoShouldNotBeFound("highGlucose.equals=" + UPDATED_HIGH_GLUCOSE);
    }

    @Test
    @Transactional
    void getAllUserMedicalInfosByHighGlucoseIsInShouldWork() throws Exception {
        // Initialize the database
        userMedicalInfoRepository.saveAndFlush(userMedicalInfo);

        // Get all the userMedicalInfoList where highGlucose in DEFAULT_HIGH_GLUCOSE or UPDATED_HIGH_GLUCOSE
        defaultUserMedicalInfoShouldBeFound("highGlucose.in=" + DEFAULT_HIGH_GLUCOSE + "," + UPDATED_HIGH_GLUCOSE);

        // Get all the userMedicalInfoList where highGlucose equals to UPDATED_HIGH_GLUCOSE
        defaultUserMedicalInfoShouldNotBeFound("highGlucose.in=" + UPDATED_HIGH_GLUCOSE);
    }

    @Test
    @Transactional
    void getAllUserMedicalInfosByHighGlucoseIsNullOrNotNull() throws Exception {
        // Initialize the database
        userMedicalInfoRepository.saveAndFlush(userMedicalInfo);

        // Get all the userMedicalInfoList where highGlucose is not null
        defaultUserMedicalInfoShouldBeFound("highGlucose.specified=true");

        // Get all the userMedicalInfoList where highGlucose is null
        defaultUserMedicalInfoShouldNotBeFound("highGlucose.specified=false");
    }

    @Test
    @Transactional
    void getAllUserMedicalInfosByHiabetesIsEqualToSomething() throws Exception {
        // Initialize the database
        userMedicalInfoRepository.saveAndFlush(userMedicalInfo);

        // Get all the userMedicalInfoList where hiabetes equals to DEFAULT_HIABETES
        defaultUserMedicalInfoShouldBeFound("hiabetes.equals=" + DEFAULT_HIABETES);

        // Get all the userMedicalInfoList where hiabetes equals to UPDATED_HIABETES
        defaultUserMedicalInfoShouldNotBeFound("hiabetes.equals=" + UPDATED_HIABETES);
    }

    @Test
    @Transactional
    void getAllUserMedicalInfosByHiabetesIsInShouldWork() throws Exception {
        // Initialize the database
        userMedicalInfoRepository.saveAndFlush(userMedicalInfo);

        // Get all the userMedicalInfoList where hiabetes in DEFAULT_HIABETES or UPDATED_HIABETES
        defaultUserMedicalInfoShouldBeFound("hiabetes.in=" + DEFAULT_HIABETES + "," + UPDATED_HIABETES);

        // Get all the userMedicalInfoList where hiabetes equals to UPDATED_HIABETES
        defaultUserMedicalInfoShouldNotBeFound("hiabetes.in=" + UPDATED_HIABETES);
    }

    @Test
    @Transactional
    void getAllUserMedicalInfosByHiabetesIsNullOrNotNull() throws Exception {
        // Initialize the database
        userMedicalInfoRepository.saveAndFlush(userMedicalInfo);

        // Get all the userMedicalInfoList where hiabetes is not null
        defaultUserMedicalInfoShouldBeFound("hiabetes.specified=true");

        // Get all the userMedicalInfoList where hiabetes is null
        defaultUserMedicalInfoShouldNotBeFound("hiabetes.specified=false");
    }

    @Test
    @Transactional
    void getAllUserMedicalInfosByTotalCholesterolIsEqualToSomething() throws Exception {
        // Initialize the database
        userMedicalInfoRepository.saveAndFlush(userMedicalInfo);

        // Get all the userMedicalInfoList where totalCholesterol equals to DEFAULT_TOTAL_CHOLESTEROL
        defaultUserMedicalInfoShouldBeFound("totalCholesterol.equals=" + DEFAULT_TOTAL_CHOLESTEROL);

        // Get all the userMedicalInfoList where totalCholesterol equals to UPDATED_TOTAL_CHOLESTEROL
        defaultUserMedicalInfoShouldNotBeFound("totalCholesterol.equals=" + UPDATED_TOTAL_CHOLESTEROL);
    }

    @Test
    @Transactional
    void getAllUserMedicalInfosByTotalCholesterolIsInShouldWork() throws Exception {
        // Initialize the database
        userMedicalInfoRepository.saveAndFlush(userMedicalInfo);

        // Get all the userMedicalInfoList where totalCholesterol in DEFAULT_TOTAL_CHOLESTEROL or UPDATED_TOTAL_CHOLESTEROL
        defaultUserMedicalInfoShouldBeFound("totalCholesterol.in=" + DEFAULT_TOTAL_CHOLESTEROL + "," + UPDATED_TOTAL_CHOLESTEROL);

        // Get all the userMedicalInfoList where totalCholesterol equals to UPDATED_TOTAL_CHOLESTEROL
        defaultUserMedicalInfoShouldNotBeFound("totalCholesterol.in=" + UPDATED_TOTAL_CHOLESTEROL);
    }

    @Test
    @Transactional
    void getAllUserMedicalInfosByTotalCholesterolIsNullOrNotNull() throws Exception {
        // Initialize the database
        userMedicalInfoRepository.saveAndFlush(userMedicalInfo);

        // Get all the userMedicalInfoList where totalCholesterol is not null
        defaultUserMedicalInfoShouldBeFound("totalCholesterol.specified=true");

        // Get all the userMedicalInfoList where totalCholesterol is null
        defaultUserMedicalInfoShouldNotBeFound("totalCholesterol.specified=false");
    }

    @Test
    @Transactional
    void getAllUserMedicalInfosByTotalCholesterolIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        userMedicalInfoRepository.saveAndFlush(userMedicalInfo);

        // Get all the userMedicalInfoList where totalCholesterol is greater than or equal to DEFAULT_TOTAL_CHOLESTEROL
        defaultUserMedicalInfoShouldBeFound("totalCholesterol.greaterThanOrEqual=" + DEFAULT_TOTAL_CHOLESTEROL);

        // Get all the userMedicalInfoList where totalCholesterol is greater than or equal to UPDATED_TOTAL_CHOLESTEROL
        defaultUserMedicalInfoShouldNotBeFound("totalCholesterol.greaterThanOrEqual=" + UPDATED_TOTAL_CHOLESTEROL);
    }

    @Test
    @Transactional
    void getAllUserMedicalInfosByTotalCholesterolIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        userMedicalInfoRepository.saveAndFlush(userMedicalInfo);

        // Get all the userMedicalInfoList where totalCholesterol is less than or equal to DEFAULT_TOTAL_CHOLESTEROL
        defaultUserMedicalInfoShouldBeFound("totalCholesterol.lessThanOrEqual=" + DEFAULT_TOTAL_CHOLESTEROL);

        // Get all the userMedicalInfoList where totalCholesterol is less than or equal to SMALLER_TOTAL_CHOLESTEROL
        defaultUserMedicalInfoShouldNotBeFound("totalCholesterol.lessThanOrEqual=" + SMALLER_TOTAL_CHOLESTEROL);
    }

    @Test
    @Transactional
    void getAllUserMedicalInfosByTotalCholesterolIsLessThanSomething() throws Exception {
        // Initialize the database
        userMedicalInfoRepository.saveAndFlush(userMedicalInfo);

        // Get all the userMedicalInfoList where totalCholesterol is less than DEFAULT_TOTAL_CHOLESTEROL
        defaultUserMedicalInfoShouldNotBeFound("totalCholesterol.lessThan=" + DEFAULT_TOTAL_CHOLESTEROL);

        // Get all the userMedicalInfoList where totalCholesterol is less than UPDATED_TOTAL_CHOLESTEROL
        defaultUserMedicalInfoShouldBeFound("totalCholesterol.lessThan=" + UPDATED_TOTAL_CHOLESTEROL);
    }

    @Test
    @Transactional
    void getAllUserMedicalInfosByTotalCholesterolIsGreaterThanSomething() throws Exception {
        // Initialize the database
        userMedicalInfoRepository.saveAndFlush(userMedicalInfo);

        // Get all the userMedicalInfoList where totalCholesterol is greater than DEFAULT_TOTAL_CHOLESTEROL
        defaultUserMedicalInfoShouldNotBeFound("totalCholesterol.greaterThan=" + DEFAULT_TOTAL_CHOLESTEROL);

        // Get all the userMedicalInfoList where totalCholesterol is greater than SMALLER_TOTAL_CHOLESTEROL
        defaultUserMedicalInfoShouldBeFound("totalCholesterol.greaterThan=" + SMALLER_TOTAL_CHOLESTEROL);
    }

    @Test
    @Transactional
    void getAllUserMedicalInfosByHdlCholesterolIsEqualToSomething() throws Exception {
        // Initialize the database
        userMedicalInfoRepository.saveAndFlush(userMedicalInfo);

        // Get all the userMedicalInfoList where hdlCholesterol equals to DEFAULT_HDL_CHOLESTEROL
        defaultUserMedicalInfoShouldBeFound("hdlCholesterol.equals=" + DEFAULT_HDL_CHOLESTEROL);

        // Get all the userMedicalInfoList where hdlCholesterol equals to UPDATED_HDL_CHOLESTEROL
        defaultUserMedicalInfoShouldNotBeFound("hdlCholesterol.equals=" + UPDATED_HDL_CHOLESTEROL);
    }

    @Test
    @Transactional
    void getAllUserMedicalInfosByHdlCholesterolIsInShouldWork() throws Exception {
        // Initialize the database
        userMedicalInfoRepository.saveAndFlush(userMedicalInfo);

        // Get all the userMedicalInfoList where hdlCholesterol in DEFAULT_HDL_CHOLESTEROL or UPDATED_HDL_CHOLESTEROL
        defaultUserMedicalInfoShouldBeFound("hdlCholesterol.in=" + DEFAULT_HDL_CHOLESTEROL + "," + UPDATED_HDL_CHOLESTEROL);

        // Get all the userMedicalInfoList where hdlCholesterol equals to UPDATED_HDL_CHOLESTEROL
        defaultUserMedicalInfoShouldNotBeFound("hdlCholesterol.in=" + UPDATED_HDL_CHOLESTEROL);
    }

    @Test
    @Transactional
    void getAllUserMedicalInfosByHdlCholesterolIsNullOrNotNull() throws Exception {
        // Initialize the database
        userMedicalInfoRepository.saveAndFlush(userMedicalInfo);

        // Get all the userMedicalInfoList where hdlCholesterol is not null
        defaultUserMedicalInfoShouldBeFound("hdlCholesterol.specified=true");

        // Get all the userMedicalInfoList where hdlCholesterol is null
        defaultUserMedicalInfoShouldNotBeFound("hdlCholesterol.specified=false");
    }

    @Test
    @Transactional
    void getAllUserMedicalInfosByHdlCholesterolIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        userMedicalInfoRepository.saveAndFlush(userMedicalInfo);

        // Get all the userMedicalInfoList where hdlCholesterol is greater than or equal to DEFAULT_HDL_CHOLESTEROL
        defaultUserMedicalInfoShouldBeFound("hdlCholesterol.greaterThanOrEqual=" + DEFAULT_HDL_CHOLESTEROL);

        // Get all the userMedicalInfoList where hdlCholesterol is greater than or equal to UPDATED_HDL_CHOLESTEROL
        defaultUserMedicalInfoShouldNotBeFound("hdlCholesterol.greaterThanOrEqual=" + UPDATED_HDL_CHOLESTEROL);
    }

    @Test
    @Transactional
    void getAllUserMedicalInfosByHdlCholesterolIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        userMedicalInfoRepository.saveAndFlush(userMedicalInfo);

        // Get all the userMedicalInfoList where hdlCholesterol is less than or equal to DEFAULT_HDL_CHOLESTEROL
        defaultUserMedicalInfoShouldBeFound("hdlCholesterol.lessThanOrEqual=" + DEFAULT_HDL_CHOLESTEROL);

        // Get all the userMedicalInfoList where hdlCholesterol is less than or equal to SMALLER_HDL_CHOLESTEROL
        defaultUserMedicalInfoShouldNotBeFound("hdlCholesterol.lessThanOrEqual=" + SMALLER_HDL_CHOLESTEROL);
    }

    @Test
    @Transactional
    void getAllUserMedicalInfosByHdlCholesterolIsLessThanSomething() throws Exception {
        // Initialize the database
        userMedicalInfoRepository.saveAndFlush(userMedicalInfo);

        // Get all the userMedicalInfoList where hdlCholesterol is less than DEFAULT_HDL_CHOLESTEROL
        defaultUserMedicalInfoShouldNotBeFound("hdlCholesterol.lessThan=" + DEFAULT_HDL_CHOLESTEROL);

        // Get all the userMedicalInfoList where hdlCholesterol is less than UPDATED_HDL_CHOLESTEROL
        defaultUserMedicalInfoShouldBeFound("hdlCholesterol.lessThan=" + UPDATED_HDL_CHOLESTEROL);
    }

    @Test
    @Transactional
    void getAllUserMedicalInfosByHdlCholesterolIsGreaterThanSomething() throws Exception {
        // Initialize the database
        userMedicalInfoRepository.saveAndFlush(userMedicalInfo);

        // Get all the userMedicalInfoList where hdlCholesterol is greater than DEFAULT_HDL_CHOLESTEROL
        defaultUserMedicalInfoShouldNotBeFound("hdlCholesterol.greaterThan=" + DEFAULT_HDL_CHOLESTEROL);

        // Get all the userMedicalInfoList where hdlCholesterol is greater than SMALLER_HDL_CHOLESTEROL
        defaultUserMedicalInfoShouldBeFound("hdlCholesterol.greaterThan=" + SMALLER_HDL_CHOLESTEROL);
    }

    @Test
    @Transactional
    void getAllUserMedicalInfosByMedicalMainConditionIsEqualToSomething() throws Exception {
        // Initialize the database
        userMedicalInfoRepository.saveAndFlush(userMedicalInfo);

        // Get all the userMedicalInfoList where medicalMainCondition equals to DEFAULT_MEDICAL_MAIN_CONDITION
        defaultUserMedicalInfoShouldBeFound("medicalMainCondition.equals=" + DEFAULT_MEDICAL_MAIN_CONDITION);

        // Get all the userMedicalInfoList where medicalMainCondition equals to UPDATED_MEDICAL_MAIN_CONDITION
        defaultUserMedicalInfoShouldNotBeFound("medicalMainCondition.equals=" + UPDATED_MEDICAL_MAIN_CONDITION);
    }

    @Test
    @Transactional
    void getAllUserMedicalInfosByMedicalMainConditionIsInShouldWork() throws Exception {
        // Initialize the database
        userMedicalInfoRepository.saveAndFlush(userMedicalInfo);

        // Get all the userMedicalInfoList where medicalMainCondition in DEFAULT_MEDICAL_MAIN_CONDITION or UPDATED_MEDICAL_MAIN_CONDITION
        defaultUserMedicalInfoShouldBeFound(
            "medicalMainCondition.in=" + DEFAULT_MEDICAL_MAIN_CONDITION + "," + UPDATED_MEDICAL_MAIN_CONDITION
        );

        // Get all the userMedicalInfoList where medicalMainCondition equals to UPDATED_MEDICAL_MAIN_CONDITION
        defaultUserMedicalInfoShouldNotBeFound("medicalMainCondition.in=" + UPDATED_MEDICAL_MAIN_CONDITION);
    }

    @Test
    @Transactional
    void getAllUserMedicalInfosByMedicalMainConditionIsNullOrNotNull() throws Exception {
        // Initialize the database
        userMedicalInfoRepository.saveAndFlush(userMedicalInfo);

        // Get all the userMedicalInfoList where medicalMainCondition is not null
        defaultUserMedicalInfoShouldBeFound("medicalMainCondition.specified=true");

        // Get all the userMedicalInfoList where medicalMainCondition is null
        defaultUserMedicalInfoShouldNotBeFound("medicalMainCondition.specified=false");
    }

    @Test
    @Transactional
    void getAllUserMedicalInfosByMedicalMainConditionContainsSomething() throws Exception {
        // Initialize the database
        userMedicalInfoRepository.saveAndFlush(userMedicalInfo);

        // Get all the userMedicalInfoList where medicalMainCondition contains DEFAULT_MEDICAL_MAIN_CONDITION
        defaultUserMedicalInfoShouldBeFound("medicalMainCondition.contains=" + DEFAULT_MEDICAL_MAIN_CONDITION);

        // Get all the userMedicalInfoList where medicalMainCondition contains UPDATED_MEDICAL_MAIN_CONDITION
        defaultUserMedicalInfoShouldNotBeFound("medicalMainCondition.contains=" + UPDATED_MEDICAL_MAIN_CONDITION);
    }

    @Test
    @Transactional
    void getAllUserMedicalInfosByMedicalMainConditionNotContainsSomething() throws Exception {
        // Initialize the database
        userMedicalInfoRepository.saveAndFlush(userMedicalInfo);

        // Get all the userMedicalInfoList where medicalMainCondition does not contain DEFAULT_MEDICAL_MAIN_CONDITION
        defaultUserMedicalInfoShouldNotBeFound("medicalMainCondition.doesNotContain=" + DEFAULT_MEDICAL_MAIN_CONDITION);

        // Get all the userMedicalInfoList where medicalMainCondition does not contain UPDATED_MEDICAL_MAIN_CONDITION
        defaultUserMedicalInfoShouldBeFound("medicalMainCondition.doesNotContain=" + UPDATED_MEDICAL_MAIN_CONDITION);
    }

    @Test
    @Transactional
    void getAllUserMedicalInfosByMedicalSecundaryConditionIsEqualToSomething() throws Exception {
        // Initialize the database
        userMedicalInfoRepository.saveAndFlush(userMedicalInfo);

        // Get all the userMedicalInfoList where medicalSecundaryCondition equals to DEFAULT_MEDICAL_SECUNDARY_CONDITION
        defaultUserMedicalInfoShouldBeFound("medicalSecundaryCondition.equals=" + DEFAULT_MEDICAL_SECUNDARY_CONDITION);

        // Get all the userMedicalInfoList where medicalSecundaryCondition equals to UPDATED_MEDICAL_SECUNDARY_CONDITION
        defaultUserMedicalInfoShouldNotBeFound("medicalSecundaryCondition.equals=" + UPDATED_MEDICAL_SECUNDARY_CONDITION);
    }

    @Test
    @Transactional
    void getAllUserMedicalInfosByMedicalSecundaryConditionIsInShouldWork() throws Exception {
        // Initialize the database
        userMedicalInfoRepository.saveAndFlush(userMedicalInfo);

        // Get all the userMedicalInfoList where medicalSecundaryCondition in DEFAULT_MEDICAL_SECUNDARY_CONDITION or UPDATED_MEDICAL_SECUNDARY_CONDITION
        defaultUserMedicalInfoShouldBeFound(
            "medicalSecundaryCondition.in=" + DEFAULT_MEDICAL_SECUNDARY_CONDITION + "," + UPDATED_MEDICAL_SECUNDARY_CONDITION
        );

        // Get all the userMedicalInfoList where medicalSecundaryCondition equals to UPDATED_MEDICAL_SECUNDARY_CONDITION
        defaultUserMedicalInfoShouldNotBeFound("medicalSecundaryCondition.in=" + UPDATED_MEDICAL_SECUNDARY_CONDITION);
    }

    @Test
    @Transactional
    void getAllUserMedicalInfosByMedicalSecundaryConditionIsNullOrNotNull() throws Exception {
        // Initialize the database
        userMedicalInfoRepository.saveAndFlush(userMedicalInfo);

        // Get all the userMedicalInfoList where medicalSecundaryCondition is not null
        defaultUserMedicalInfoShouldBeFound("medicalSecundaryCondition.specified=true");

        // Get all the userMedicalInfoList where medicalSecundaryCondition is null
        defaultUserMedicalInfoShouldNotBeFound("medicalSecundaryCondition.specified=false");
    }

    @Test
    @Transactional
    void getAllUserMedicalInfosByMedicalSecundaryConditionContainsSomething() throws Exception {
        // Initialize the database
        userMedicalInfoRepository.saveAndFlush(userMedicalInfo);

        // Get all the userMedicalInfoList where medicalSecundaryCondition contains DEFAULT_MEDICAL_SECUNDARY_CONDITION
        defaultUserMedicalInfoShouldBeFound("medicalSecundaryCondition.contains=" + DEFAULT_MEDICAL_SECUNDARY_CONDITION);

        // Get all the userMedicalInfoList where medicalSecundaryCondition contains UPDATED_MEDICAL_SECUNDARY_CONDITION
        defaultUserMedicalInfoShouldNotBeFound("medicalSecundaryCondition.contains=" + UPDATED_MEDICAL_SECUNDARY_CONDITION);
    }

    @Test
    @Transactional
    void getAllUserMedicalInfosByMedicalSecundaryConditionNotContainsSomething() throws Exception {
        // Initialize the database
        userMedicalInfoRepository.saveAndFlush(userMedicalInfo);

        // Get all the userMedicalInfoList where medicalSecundaryCondition does not contain DEFAULT_MEDICAL_SECUNDARY_CONDITION
        defaultUserMedicalInfoShouldNotBeFound("medicalSecundaryCondition.doesNotContain=" + DEFAULT_MEDICAL_SECUNDARY_CONDITION);

        // Get all the userMedicalInfoList where medicalSecundaryCondition does not contain UPDATED_MEDICAL_SECUNDARY_CONDITION
        defaultUserMedicalInfoShouldBeFound("medicalSecundaryCondition.doesNotContain=" + UPDATED_MEDICAL_SECUNDARY_CONDITION);
    }

    @Test
    @Transactional
    void getAllUserMedicalInfosByMedicalMainMedicationIsEqualToSomething() throws Exception {
        // Initialize the database
        userMedicalInfoRepository.saveAndFlush(userMedicalInfo);

        // Get all the userMedicalInfoList where medicalMainMedication equals to DEFAULT_MEDICAL_MAIN_MEDICATION
        defaultUserMedicalInfoShouldBeFound("medicalMainMedication.equals=" + DEFAULT_MEDICAL_MAIN_MEDICATION);

        // Get all the userMedicalInfoList where medicalMainMedication equals to UPDATED_MEDICAL_MAIN_MEDICATION
        defaultUserMedicalInfoShouldNotBeFound("medicalMainMedication.equals=" + UPDATED_MEDICAL_MAIN_MEDICATION);
    }

    @Test
    @Transactional
    void getAllUserMedicalInfosByMedicalMainMedicationIsInShouldWork() throws Exception {
        // Initialize the database
        userMedicalInfoRepository.saveAndFlush(userMedicalInfo);

        // Get all the userMedicalInfoList where medicalMainMedication in DEFAULT_MEDICAL_MAIN_MEDICATION or UPDATED_MEDICAL_MAIN_MEDICATION
        defaultUserMedicalInfoShouldBeFound(
            "medicalMainMedication.in=" + DEFAULT_MEDICAL_MAIN_MEDICATION + "," + UPDATED_MEDICAL_MAIN_MEDICATION
        );

        // Get all the userMedicalInfoList where medicalMainMedication equals to UPDATED_MEDICAL_MAIN_MEDICATION
        defaultUserMedicalInfoShouldNotBeFound("medicalMainMedication.in=" + UPDATED_MEDICAL_MAIN_MEDICATION);
    }

    @Test
    @Transactional
    void getAllUserMedicalInfosByMedicalMainMedicationIsNullOrNotNull() throws Exception {
        // Initialize the database
        userMedicalInfoRepository.saveAndFlush(userMedicalInfo);

        // Get all the userMedicalInfoList where medicalMainMedication is not null
        defaultUserMedicalInfoShouldBeFound("medicalMainMedication.specified=true");

        // Get all the userMedicalInfoList where medicalMainMedication is null
        defaultUserMedicalInfoShouldNotBeFound("medicalMainMedication.specified=false");
    }

    @Test
    @Transactional
    void getAllUserMedicalInfosByMedicalMainMedicationContainsSomething() throws Exception {
        // Initialize the database
        userMedicalInfoRepository.saveAndFlush(userMedicalInfo);

        // Get all the userMedicalInfoList where medicalMainMedication contains DEFAULT_MEDICAL_MAIN_MEDICATION
        defaultUserMedicalInfoShouldBeFound("medicalMainMedication.contains=" + DEFAULT_MEDICAL_MAIN_MEDICATION);

        // Get all the userMedicalInfoList where medicalMainMedication contains UPDATED_MEDICAL_MAIN_MEDICATION
        defaultUserMedicalInfoShouldNotBeFound("medicalMainMedication.contains=" + UPDATED_MEDICAL_MAIN_MEDICATION);
    }

    @Test
    @Transactional
    void getAllUserMedicalInfosByMedicalMainMedicationNotContainsSomething() throws Exception {
        // Initialize the database
        userMedicalInfoRepository.saveAndFlush(userMedicalInfo);

        // Get all the userMedicalInfoList where medicalMainMedication does not contain DEFAULT_MEDICAL_MAIN_MEDICATION
        defaultUserMedicalInfoShouldNotBeFound("medicalMainMedication.doesNotContain=" + DEFAULT_MEDICAL_MAIN_MEDICATION);

        // Get all the userMedicalInfoList where medicalMainMedication does not contain UPDATED_MEDICAL_MAIN_MEDICATION
        defaultUserMedicalInfoShouldBeFound("medicalMainMedication.doesNotContain=" + UPDATED_MEDICAL_MAIN_MEDICATION);
    }

    @Test
    @Transactional
    void getAllUserMedicalInfosByMedicalSecundaryMedicationIsEqualToSomething() throws Exception {
        // Initialize the database
        userMedicalInfoRepository.saveAndFlush(userMedicalInfo);

        // Get all the userMedicalInfoList where medicalSecundaryMedication equals to DEFAULT_MEDICAL_SECUNDARY_MEDICATION
        defaultUserMedicalInfoShouldBeFound("medicalSecundaryMedication.equals=" + DEFAULT_MEDICAL_SECUNDARY_MEDICATION);

        // Get all the userMedicalInfoList where medicalSecundaryMedication equals to UPDATED_MEDICAL_SECUNDARY_MEDICATION
        defaultUserMedicalInfoShouldNotBeFound("medicalSecundaryMedication.equals=" + UPDATED_MEDICAL_SECUNDARY_MEDICATION);
    }

    @Test
    @Transactional
    void getAllUserMedicalInfosByMedicalSecundaryMedicationIsInShouldWork() throws Exception {
        // Initialize the database
        userMedicalInfoRepository.saveAndFlush(userMedicalInfo);

        // Get all the userMedicalInfoList where medicalSecundaryMedication in DEFAULT_MEDICAL_SECUNDARY_MEDICATION or UPDATED_MEDICAL_SECUNDARY_MEDICATION
        defaultUserMedicalInfoShouldBeFound(
            "medicalSecundaryMedication.in=" + DEFAULT_MEDICAL_SECUNDARY_MEDICATION + "," + UPDATED_MEDICAL_SECUNDARY_MEDICATION
        );

        // Get all the userMedicalInfoList where medicalSecundaryMedication equals to UPDATED_MEDICAL_SECUNDARY_MEDICATION
        defaultUserMedicalInfoShouldNotBeFound("medicalSecundaryMedication.in=" + UPDATED_MEDICAL_SECUNDARY_MEDICATION);
    }

    @Test
    @Transactional
    void getAllUserMedicalInfosByMedicalSecundaryMedicationIsNullOrNotNull() throws Exception {
        // Initialize the database
        userMedicalInfoRepository.saveAndFlush(userMedicalInfo);

        // Get all the userMedicalInfoList where medicalSecundaryMedication is not null
        defaultUserMedicalInfoShouldBeFound("medicalSecundaryMedication.specified=true");

        // Get all the userMedicalInfoList where medicalSecundaryMedication is null
        defaultUserMedicalInfoShouldNotBeFound("medicalSecundaryMedication.specified=false");
    }

    @Test
    @Transactional
    void getAllUserMedicalInfosByMedicalSecundaryMedicationContainsSomething() throws Exception {
        // Initialize the database
        userMedicalInfoRepository.saveAndFlush(userMedicalInfo);

        // Get all the userMedicalInfoList where medicalSecundaryMedication contains DEFAULT_MEDICAL_SECUNDARY_MEDICATION
        defaultUserMedicalInfoShouldBeFound("medicalSecundaryMedication.contains=" + DEFAULT_MEDICAL_SECUNDARY_MEDICATION);

        // Get all the userMedicalInfoList where medicalSecundaryMedication contains UPDATED_MEDICAL_SECUNDARY_MEDICATION
        defaultUserMedicalInfoShouldNotBeFound("medicalSecundaryMedication.contains=" + UPDATED_MEDICAL_SECUNDARY_MEDICATION);
    }

    @Test
    @Transactional
    void getAllUserMedicalInfosByMedicalSecundaryMedicationNotContainsSomething() throws Exception {
        // Initialize the database
        userMedicalInfoRepository.saveAndFlush(userMedicalInfo);

        // Get all the userMedicalInfoList where medicalSecundaryMedication does not contain DEFAULT_MEDICAL_SECUNDARY_MEDICATION
        defaultUserMedicalInfoShouldNotBeFound("medicalSecundaryMedication.doesNotContain=" + DEFAULT_MEDICAL_SECUNDARY_MEDICATION);

        // Get all the userMedicalInfoList where medicalSecundaryMedication does not contain UPDATED_MEDICAL_SECUNDARY_MEDICATION
        defaultUserMedicalInfoShouldBeFound("medicalSecundaryMedication.doesNotContain=" + UPDATED_MEDICAL_SECUNDARY_MEDICATION);
    }

    @Test
    @Transactional
    void getAllUserMedicalInfosByMedicalScoreIsEqualToSomething() throws Exception {
        // Initialize the database
        userMedicalInfoRepository.saveAndFlush(userMedicalInfo);

        // Get all the userMedicalInfoList where medicalScore equals to DEFAULT_MEDICAL_SCORE
        defaultUserMedicalInfoShouldBeFound("medicalScore.equals=" + DEFAULT_MEDICAL_SCORE);

        // Get all the userMedicalInfoList where medicalScore equals to UPDATED_MEDICAL_SCORE
        defaultUserMedicalInfoShouldNotBeFound("medicalScore.equals=" + UPDATED_MEDICAL_SCORE);
    }

    @Test
    @Transactional
    void getAllUserMedicalInfosByMedicalScoreIsInShouldWork() throws Exception {
        // Initialize the database
        userMedicalInfoRepository.saveAndFlush(userMedicalInfo);

        // Get all the userMedicalInfoList where medicalScore in DEFAULT_MEDICAL_SCORE or UPDATED_MEDICAL_SCORE
        defaultUserMedicalInfoShouldBeFound("medicalScore.in=" + DEFAULT_MEDICAL_SCORE + "," + UPDATED_MEDICAL_SCORE);

        // Get all the userMedicalInfoList where medicalScore equals to UPDATED_MEDICAL_SCORE
        defaultUserMedicalInfoShouldNotBeFound("medicalScore.in=" + UPDATED_MEDICAL_SCORE);
    }

    @Test
    @Transactional
    void getAllUserMedicalInfosByMedicalScoreIsNullOrNotNull() throws Exception {
        // Initialize the database
        userMedicalInfoRepository.saveAndFlush(userMedicalInfo);

        // Get all the userMedicalInfoList where medicalScore is not null
        defaultUserMedicalInfoShouldBeFound("medicalScore.specified=true");

        // Get all the userMedicalInfoList where medicalScore is null
        defaultUserMedicalInfoShouldNotBeFound("medicalScore.specified=false");
    }

    @Test
    @Transactional
    void getAllUserMedicalInfosByMedicalScoreIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        userMedicalInfoRepository.saveAndFlush(userMedicalInfo);

        // Get all the userMedicalInfoList where medicalScore is greater than or equal to DEFAULT_MEDICAL_SCORE
        defaultUserMedicalInfoShouldBeFound("medicalScore.greaterThanOrEqual=" + DEFAULT_MEDICAL_SCORE);

        // Get all the userMedicalInfoList where medicalScore is greater than or equal to UPDATED_MEDICAL_SCORE
        defaultUserMedicalInfoShouldNotBeFound("medicalScore.greaterThanOrEqual=" + UPDATED_MEDICAL_SCORE);
    }

    @Test
    @Transactional
    void getAllUserMedicalInfosByMedicalScoreIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        userMedicalInfoRepository.saveAndFlush(userMedicalInfo);

        // Get all the userMedicalInfoList where medicalScore is less than or equal to DEFAULT_MEDICAL_SCORE
        defaultUserMedicalInfoShouldBeFound("medicalScore.lessThanOrEqual=" + DEFAULT_MEDICAL_SCORE);

        // Get all the userMedicalInfoList where medicalScore is less than or equal to SMALLER_MEDICAL_SCORE
        defaultUserMedicalInfoShouldNotBeFound("medicalScore.lessThanOrEqual=" + SMALLER_MEDICAL_SCORE);
    }

    @Test
    @Transactional
    void getAllUserMedicalInfosByMedicalScoreIsLessThanSomething() throws Exception {
        // Initialize the database
        userMedicalInfoRepository.saveAndFlush(userMedicalInfo);

        // Get all the userMedicalInfoList where medicalScore is less than DEFAULT_MEDICAL_SCORE
        defaultUserMedicalInfoShouldNotBeFound("medicalScore.lessThan=" + DEFAULT_MEDICAL_SCORE);

        // Get all the userMedicalInfoList where medicalScore is less than UPDATED_MEDICAL_SCORE
        defaultUserMedicalInfoShouldBeFound("medicalScore.lessThan=" + UPDATED_MEDICAL_SCORE);
    }

    @Test
    @Transactional
    void getAllUserMedicalInfosByMedicalScoreIsGreaterThanSomething() throws Exception {
        // Initialize the database
        userMedicalInfoRepository.saveAndFlush(userMedicalInfo);

        // Get all the userMedicalInfoList where medicalScore is greater than DEFAULT_MEDICAL_SCORE
        defaultUserMedicalInfoShouldNotBeFound("medicalScore.greaterThan=" + DEFAULT_MEDICAL_SCORE);

        // Get all the userMedicalInfoList where medicalScore is greater than SMALLER_MEDICAL_SCORE
        defaultUserMedicalInfoShouldBeFound("medicalScore.greaterThan=" + SMALLER_MEDICAL_SCORE);
    }

    @Test
    @Transactional
    void getAllUserMedicalInfosByEndTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        userMedicalInfoRepository.saveAndFlush(userMedicalInfo);

        // Get all the userMedicalInfoList where endTime equals to DEFAULT_END_TIME
        defaultUserMedicalInfoShouldBeFound("endTime.equals=" + DEFAULT_END_TIME);

        // Get all the userMedicalInfoList where endTime equals to UPDATED_END_TIME
        defaultUserMedicalInfoShouldNotBeFound("endTime.equals=" + UPDATED_END_TIME);
    }

    @Test
    @Transactional
    void getAllUserMedicalInfosByEndTimeIsInShouldWork() throws Exception {
        // Initialize the database
        userMedicalInfoRepository.saveAndFlush(userMedicalInfo);

        // Get all the userMedicalInfoList where endTime in DEFAULT_END_TIME or UPDATED_END_TIME
        defaultUserMedicalInfoShouldBeFound("endTime.in=" + DEFAULT_END_TIME + "," + UPDATED_END_TIME);

        // Get all the userMedicalInfoList where endTime equals to UPDATED_END_TIME
        defaultUserMedicalInfoShouldNotBeFound("endTime.in=" + UPDATED_END_TIME);
    }

    @Test
    @Transactional
    void getAllUserMedicalInfosByEndTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        userMedicalInfoRepository.saveAndFlush(userMedicalInfo);

        // Get all the userMedicalInfoList where endTime is not null
        defaultUserMedicalInfoShouldBeFound("endTime.specified=true");

        // Get all the userMedicalInfoList where endTime is null
        defaultUserMedicalInfoShouldNotBeFound("endTime.specified=false");
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultUserMedicalInfoShouldBeFound(String filter) throws Exception {
        restUserMedicalInfoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userMedicalInfo.getId().toString())))
            .andExpect(jsonPath("$.[*].usuarioId").value(hasItem(DEFAULT_USUARIO_ID)))
            .andExpect(jsonPath("$.[*].empresaId").value(hasItem(DEFAULT_EMPRESA_ID)))
            .andExpect(jsonPath("$.[*].hypertension").value(hasItem(DEFAULT_HYPERTENSION.booleanValue())))
            .andExpect(jsonPath("$.[*].highGlucose").value(hasItem(DEFAULT_HIGH_GLUCOSE.booleanValue())))
            .andExpect(jsonPath("$.[*].hiabetes").value(hasItem(DEFAULT_HIABETES.booleanValue())))
            .andExpect(jsonPath("$.[*].totalCholesterol").value(hasItem(DEFAULT_TOTAL_CHOLESTEROL.doubleValue())))
            .andExpect(jsonPath("$.[*].hdlCholesterol").value(hasItem(DEFAULT_HDL_CHOLESTEROL.doubleValue())))
            .andExpect(jsonPath("$.[*].medicalMainCondition").value(hasItem(DEFAULT_MEDICAL_MAIN_CONDITION)))
            .andExpect(jsonPath("$.[*].medicalSecundaryCondition").value(hasItem(DEFAULT_MEDICAL_SECUNDARY_CONDITION)))
            .andExpect(jsonPath("$.[*].medicalMainMedication").value(hasItem(DEFAULT_MEDICAL_MAIN_MEDICATION)))
            .andExpect(jsonPath("$.[*].medicalSecundaryMedication").value(hasItem(DEFAULT_MEDICAL_SECUNDARY_MEDICATION)))
            .andExpect(jsonPath("$.[*].medicalScore").value(hasItem(DEFAULT_MEDICAL_SCORE)))
            .andExpect(jsonPath("$.[*].endTime").value(hasItem(DEFAULT_END_TIME.toString())));

        // Check, that the count call also returns 1
        restUserMedicalInfoMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultUserMedicalInfoShouldNotBeFound(String filter) throws Exception {
        restUserMedicalInfoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restUserMedicalInfoMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingUserMedicalInfo() throws Exception {
        // Get the userMedicalInfo
        restUserMedicalInfoMockMvc.perform(get(ENTITY_API_URL_ID, UUID.randomUUID().toString())).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingUserMedicalInfo() throws Exception {
        // Initialize the database
        userMedicalInfoRepository.saveAndFlush(userMedicalInfo);

        int databaseSizeBeforeUpdate = userMedicalInfoRepository.findAll().size();

        // Update the userMedicalInfo
        UserMedicalInfo updatedUserMedicalInfo = userMedicalInfoRepository.findById(userMedicalInfo.getId()).get();
        // Disconnect from session so that the updates on updatedUserMedicalInfo are not directly saved in db
        em.detach(updatedUserMedicalInfo);
        updatedUserMedicalInfo
            .usuarioId(UPDATED_USUARIO_ID)
            .empresaId(UPDATED_EMPRESA_ID)
            .hypertension(UPDATED_HYPERTENSION)
            .highGlucose(UPDATED_HIGH_GLUCOSE)
            .hiabetes(UPDATED_HIABETES)
            .totalCholesterol(UPDATED_TOTAL_CHOLESTEROL)
            .hdlCholesterol(UPDATED_HDL_CHOLESTEROL)
            .medicalMainCondition(UPDATED_MEDICAL_MAIN_CONDITION)
            .medicalSecundaryCondition(UPDATED_MEDICAL_SECUNDARY_CONDITION)
            .medicalMainMedication(UPDATED_MEDICAL_MAIN_MEDICATION)
            .medicalSecundaryMedication(UPDATED_MEDICAL_SECUNDARY_MEDICATION)
            .medicalScore(UPDATED_MEDICAL_SCORE)
            .endTime(UPDATED_END_TIME);

        restUserMedicalInfoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedUserMedicalInfo.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedUserMedicalInfo))
            )
            .andExpect(status().isOk());

        // Validate the UserMedicalInfo in the database
        List<UserMedicalInfo> userMedicalInfoList = userMedicalInfoRepository.findAll();
        assertThat(userMedicalInfoList).hasSize(databaseSizeBeforeUpdate);
        UserMedicalInfo testUserMedicalInfo = userMedicalInfoList.get(userMedicalInfoList.size() - 1);
        assertThat(testUserMedicalInfo.getUsuarioId()).isEqualTo(UPDATED_USUARIO_ID);
        assertThat(testUserMedicalInfo.getEmpresaId()).isEqualTo(UPDATED_EMPRESA_ID);
        assertThat(testUserMedicalInfo.getHypertension()).isEqualTo(UPDATED_HYPERTENSION);
        assertThat(testUserMedicalInfo.getHighGlucose()).isEqualTo(UPDATED_HIGH_GLUCOSE);
        assertThat(testUserMedicalInfo.getHiabetes()).isEqualTo(UPDATED_HIABETES);
        assertThat(testUserMedicalInfo.getTotalCholesterol()).isEqualTo(UPDATED_TOTAL_CHOLESTEROL);
        assertThat(testUserMedicalInfo.getHdlCholesterol()).isEqualTo(UPDATED_HDL_CHOLESTEROL);
        assertThat(testUserMedicalInfo.getMedicalMainCondition()).isEqualTo(UPDATED_MEDICAL_MAIN_CONDITION);
        assertThat(testUserMedicalInfo.getMedicalSecundaryCondition()).isEqualTo(UPDATED_MEDICAL_SECUNDARY_CONDITION);
        assertThat(testUserMedicalInfo.getMedicalMainMedication()).isEqualTo(UPDATED_MEDICAL_MAIN_MEDICATION);
        assertThat(testUserMedicalInfo.getMedicalSecundaryMedication()).isEqualTo(UPDATED_MEDICAL_SECUNDARY_MEDICATION);
        assertThat(testUserMedicalInfo.getMedicalScore()).isEqualTo(UPDATED_MEDICAL_SCORE);
        assertThat(testUserMedicalInfo.getEndTime()).isEqualTo(UPDATED_END_TIME);
    }

    @Test
    @Transactional
    void putNonExistingUserMedicalInfo() throws Exception {
        int databaseSizeBeforeUpdate = userMedicalInfoRepository.findAll().size();
        userMedicalInfo.setId(UUID.randomUUID());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserMedicalInfoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, userMedicalInfo.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(userMedicalInfo))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserMedicalInfo in the database
        List<UserMedicalInfo> userMedicalInfoList = userMedicalInfoRepository.findAll();
        assertThat(userMedicalInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchUserMedicalInfo() throws Exception {
        int databaseSizeBeforeUpdate = userMedicalInfoRepository.findAll().size();
        userMedicalInfo.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserMedicalInfoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(userMedicalInfo))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserMedicalInfo in the database
        List<UserMedicalInfo> userMedicalInfoList = userMedicalInfoRepository.findAll();
        assertThat(userMedicalInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamUserMedicalInfo() throws Exception {
        int databaseSizeBeforeUpdate = userMedicalInfoRepository.findAll().size();
        userMedicalInfo.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserMedicalInfoMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(userMedicalInfo))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the UserMedicalInfo in the database
        List<UserMedicalInfo> userMedicalInfoList = userMedicalInfoRepository.findAll();
        assertThat(userMedicalInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateUserMedicalInfoWithPatch() throws Exception {
        // Initialize the database
        userMedicalInfoRepository.saveAndFlush(userMedicalInfo);

        int databaseSizeBeforeUpdate = userMedicalInfoRepository.findAll().size();

        // Update the userMedicalInfo using partial update
        UserMedicalInfo partialUpdatedUserMedicalInfo = new UserMedicalInfo();
        partialUpdatedUserMedicalInfo.setId(userMedicalInfo.getId());

        partialUpdatedUserMedicalInfo
            .usuarioId(UPDATED_USUARIO_ID)
            .empresaId(UPDATED_EMPRESA_ID)
            .highGlucose(UPDATED_HIGH_GLUCOSE)
            .totalCholesterol(UPDATED_TOTAL_CHOLESTEROL)
            .hdlCholesterol(UPDATED_HDL_CHOLESTEROL)
            .medicalSecundaryMedication(UPDATED_MEDICAL_SECUNDARY_MEDICATION)
            .medicalScore(UPDATED_MEDICAL_SCORE)
            .endTime(UPDATED_END_TIME);

        restUserMedicalInfoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedUserMedicalInfo.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedUserMedicalInfo))
            )
            .andExpect(status().isOk());

        // Validate the UserMedicalInfo in the database
        List<UserMedicalInfo> userMedicalInfoList = userMedicalInfoRepository.findAll();
        assertThat(userMedicalInfoList).hasSize(databaseSizeBeforeUpdate);
        UserMedicalInfo testUserMedicalInfo = userMedicalInfoList.get(userMedicalInfoList.size() - 1);
        assertThat(testUserMedicalInfo.getUsuarioId()).isEqualTo(UPDATED_USUARIO_ID);
        assertThat(testUserMedicalInfo.getEmpresaId()).isEqualTo(UPDATED_EMPRESA_ID);
        assertThat(testUserMedicalInfo.getHypertension()).isEqualTo(DEFAULT_HYPERTENSION);
        assertThat(testUserMedicalInfo.getHighGlucose()).isEqualTo(UPDATED_HIGH_GLUCOSE);
        assertThat(testUserMedicalInfo.getHiabetes()).isEqualTo(DEFAULT_HIABETES);
        assertThat(testUserMedicalInfo.getTotalCholesterol()).isEqualTo(UPDATED_TOTAL_CHOLESTEROL);
        assertThat(testUserMedicalInfo.getHdlCholesterol()).isEqualTo(UPDATED_HDL_CHOLESTEROL);
        assertThat(testUserMedicalInfo.getMedicalMainCondition()).isEqualTo(DEFAULT_MEDICAL_MAIN_CONDITION);
        assertThat(testUserMedicalInfo.getMedicalSecundaryCondition()).isEqualTo(DEFAULT_MEDICAL_SECUNDARY_CONDITION);
        assertThat(testUserMedicalInfo.getMedicalMainMedication()).isEqualTo(DEFAULT_MEDICAL_MAIN_MEDICATION);
        assertThat(testUserMedicalInfo.getMedicalSecundaryMedication()).isEqualTo(UPDATED_MEDICAL_SECUNDARY_MEDICATION);
        assertThat(testUserMedicalInfo.getMedicalScore()).isEqualTo(UPDATED_MEDICAL_SCORE);
        assertThat(testUserMedicalInfo.getEndTime()).isEqualTo(UPDATED_END_TIME);
    }

    @Test
    @Transactional
    void fullUpdateUserMedicalInfoWithPatch() throws Exception {
        // Initialize the database
        userMedicalInfoRepository.saveAndFlush(userMedicalInfo);

        int databaseSizeBeforeUpdate = userMedicalInfoRepository.findAll().size();

        // Update the userMedicalInfo using partial update
        UserMedicalInfo partialUpdatedUserMedicalInfo = new UserMedicalInfo();
        partialUpdatedUserMedicalInfo.setId(userMedicalInfo.getId());

        partialUpdatedUserMedicalInfo
            .usuarioId(UPDATED_USUARIO_ID)
            .empresaId(UPDATED_EMPRESA_ID)
            .hypertension(UPDATED_HYPERTENSION)
            .highGlucose(UPDATED_HIGH_GLUCOSE)
            .hiabetes(UPDATED_HIABETES)
            .totalCholesterol(UPDATED_TOTAL_CHOLESTEROL)
            .hdlCholesterol(UPDATED_HDL_CHOLESTEROL)
            .medicalMainCondition(UPDATED_MEDICAL_MAIN_CONDITION)
            .medicalSecundaryCondition(UPDATED_MEDICAL_SECUNDARY_CONDITION)
            .medicalMainMedication(UPDATED_MEDICAL_MAIN_MEDICATION)
            .medicalSecundaryMedication(UPDATED_MEDICAL_SECUNDARY_MEDICATION)
            .medicalScore(UPDATED_MEDICAL_SCORE)
            .endTime(UPDATED_END_TIME);

        restUserMedicalInfoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedUserMedicalInfo.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedUserMedicalInfo))
            )
            .andExpect(status().isOk());

        // Validate the UserMedicalInfo in the database
        List<UserMedicalInfo> userMedicalInfoList = userMedicalInfoRepository.findAll();
        assertThat(userMedicalInfoList).hasSize(databaseSizeBeforeUpdate);
        UserMedicalInfo testUserMedicalInfo = userMedicalInfoList.get(userMedicalInfoList.size() - 1);
        assertThat(testUserMedicalInfo.getUsuarioId()).isEqualTo(UPDATED_USUARIO_ID);
        assertThat(testUserMedicalInfo.getEmpresaId()).isEqualTo(UPDATED_EMPRESA_ID);
        assertThat(testUserMedicalInfo.getHypertension()).isEqualTo(UPDATED_HYPERTENSION);
        assertThat(testUserMedicalInfo.getHighGlucose()).isEqualTo(UPDATED_HIGH_GLUCOSE);
        assertThat(testUserMedicalInfo.getHiabetes()).isEqualTo(UPDATED_HIABETES);
        assertThat(testUserMedicalInfo.getTotalCholesterol()).isEqualTo(UPDATED_TOTAL_CHOLESTEROL);
        assertThat(testUserMedicalInfo.getHdlCholesterol()).isEqualTo(UPDATED_HDL_CHOLESTEROL);
        assertThat(testUserMedicalInfo.getMedicalMainCondition()).isEqualTo(UPDATED_MEDICAL_MAIN_CONDITION);
        assertThat(testUserMedicalInfo.getMedicalSecundaryCondition()).isEqualTo(UPDATED_MEDICAL_SECUNDARY_CONDITION);
        assertThat(testUserMedicalInfo.getMedicalMainMedication()).isEqualTo(UPDATED_MEDICAL_MAIN_MEDICATION);
        assertThat(testUserMedicalInfo.getMedicalSecundaryMedication()).isEqualTo(UPDATED_MEDICAL_SECUNDARY_MEDICATION);
        assertThat(testUserMedicalInfo.getMedicalScore()).isEqualTo(UPDATED_MEDICAL_SCORE);
        assertThat(testUserMedicalInfo.getEndTime()).isEqualTo(UPDATED_END_TIME);
    }

    @Test
    @Transactional
    void patchNonExistingUserMedicalInfo() throws Exception {
        int databaseSizeBeforeUpdate = userMedicalInfoRepository.findAll().size();
        userMedicalInfo.setId(UUID.randomUUID());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserMedicalInfoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, userMedicalInfo.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(userMedicalInfo))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserMedicalInfo in the database
        List<UserMedicalInfo> userMedicalInfoList = userMedicalInfoRepository.findAll();
        assertThat(userMedicalInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchUserMedicalInfo() throws Exception {
        int databaseSizeBeforeUpdate = userMedicalInfoRepository.findAll().size();
        userMedicalInfo.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserMedicalInfoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(userMedicalInfo))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserMedicalInfo in the database
        List<UserMedicalInfo> userMedicalInfoList = userMedicalInfoRepository.findAll();
        assertThat(userMedicalInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamUserMedicalInfo() throws Exception {
        int databaseSizeBeforeUpdate = userMedicalInfoRepository.findAll().size();
        userMedicalInfo.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserMedicalInfoMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(userMedicalInfo))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the UserMedicalInfo in the database
        List<UserMedicalInfo> userMedicalInfoList = userMedicalInfoRepository.findAll();
        assertThat(userMedicalInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteUserMedicalInfo() throws Exception {
        // Initialize the database
        userMedicalInfoRepository.saveAndFlush(userMedicalInfo);

        int databaseSizeBeforeDelete = userMedicalInfoRepository.findAll().size();

        // Delete the userMedicalInfo
        restUserMedicalInfoMockMvc
            .perform(delete(ENTITY_API_URL_ID, userMedicalInfo.getId().toString()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<UserMedicalInfo> userMedicalInfoList = userMedicalInfoRepository.findAll();
        assertThat(userMedicalInfoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
