package com.be4tech.b4carecollect.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.be4tech.b4carecollect.IntegrationTest;
import com.be4tech.b4carecollect.domain.BloodPressure;
import com.be4tech.b4carecollect.repository.BloodPressureRepository;
import com.be4tech.b4carecollect.service.criteria.BloodPressureCriteria;
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
 * Integration tests for the {@link BloodPressureResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class BloodPressureResourceIT {

    private static final String DEFAULT_USUARIO_ID = "AAAAAAAAAA";
    private static final String UPDATED_USUARIO_ID = "BBBBBBBBBB";

    private static final String DEFAULT_EMPRESA_ID = "AAAAAAAAAA";
    private static final String UPDATED_EMPRESA_ID = "BBBBBBBBBB";

    private static final Float DEFAULT_FIELD_BLOOD_PRESSURE_SYSTOLIC = 1F;
    private static final Float UPDATED_FIELD_BLOOD_PRESSURE_SYSTOLIC = 2F;
    private static final Float SMALLER_FIELD_BLOOD_PRESSURE_SYSTOLIC = 1F - 1F;

    private static final Float DEFAULT_FIELD_BLOOD_PRESSURE_DIASTOLIC = 1F;
    private static final Float UPDATED_FIELD_BLOOD_PRESSURE_DIASTOLIC = 2F;
    private static final Float SMALLER_FIELD_BLOOD_PRESSURE_DIASTOLIC = 1F - 1F;

    private static final String DEFAULT_FIELD_BODY_POSITION = "AAAAAAAAAA";
    private static final String UPDATED_FIELD_BODY_POSITION = "BBBBBBBBBB";

    private static final Integer DEFAULT_FIELD_BLOOD_PRESSURE_MEASURE_LOCATION = 1;
    private static final Integer UPDATED_FIELD_BLOOD_PRESSURE_MEASURE_LOCATION = 2;
    private static final Integer SMALLER_FIELD_BLOOD_PRESSURE_MEASURE_LOCATION = 1 - 1;

    private static final Instant DEFAULT_END_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_END_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/blood-pressures";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private BloodPressureRepository bloodPressureRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBloodPressureMockMvc;

    private BloodPressure bloodPressure;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BloodPressure createEntity(EntityManager em) {
        BloodPressure bloodPressure = new BloodPressure()
            .usuarioId(DEFAULT_USUARIO_ID)
            .empresaId(DEFAULT_EMPRESA_ID)
            .fieldBloodPressureSystolic(DEFAULT_FIELD_BLOOD_PRESSURE_SYSTOLIC)
            .fieldBloodPressureDiastolic(DEFAULT_FIELD_BLOOD_PRESSURE_DIASTOLIC)
            .fieldBodyPosition(DEFAULT_FIELD_BODY_POSITION)
            .fieldBloodPressureMeasureLocation(DEFAULT_FIELD_BLOOD_PRESSURE_MEASURE_LOCATION)
            .endTime(DEFAULT_END_TIME);
        return bloodPressure;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BloodPressure createUpdatedEntity(EntityManager em) {
        BloodPressure bloodPressure = new BloodPressure()
            .usuarioId(UPDATED_USUARIO_ID)
            .empresaId(UPDATED_EMPRESA_ID)
            .fieldBloodPressureSystolic(UPDATED_FIELD_BLOOD_PRESSURE_SYSTOLIC)
            .fieldBloodPressureDiastolic(UPDATED_FIELD_BLOOD_PRESSURE_DIASTOLIC)
            .fieldBodyPosition(UPDATED_FIELD_BODY_POSITION)
            .fieldBloodPressureMeasureLocation(UPDATED_FIELD_BLOOD_PRESSURE_MEASURE_LOCATION)
            .endTime(UPDATED_END_TIME);
        return bloodPressure;
    }

    @BeforeEach
    public void initTest() {
        bloodPressure = createEntity(em);
    }

    @Test
    @Transactional
    void createBloodPressure() throws Exception {
        int databaseSizeBeforeCreate = bloodPressureRepository.findAll().size();
        // Create the BloodPressure
        restBloodPressureMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bloodPressure)))
            .andExpect(status().isCreated());

        // Validate the BloodPressure in the database
        List<BloodPressure> bloodPressureList = bloodPressureRepository.findAll();
        assertThat(bloodPressureList).hasSize(databaseSizeBeforeCreate + 1);
        BloodPressure testBloodPressure = bloodPressureList.get(bloodPressureList.size() - 1);
        assertThat(testBloodPressure.getUsuarioId()).isEqualTo(DEFAULT_USUARIO_ID);
        assertThat(testBloodPressure.getEmpresaId()).isEqualTo(DEFAULT_EMPRESA_ID);
        assertThat(testBloodPressure.getFieldBloodPressureSystolic()).isEqualTo(DEFAULT_FIELD_BLOOD_PRESSURE_SYSTOLIC);
        assertThat(testBloodPressure.getFieldBloodPressureDiastolic()).isEqualTo(DEFAULT_FIELD_BLOOD_PRESSURE_DIASTOLIC);
        assertThat(testBloodPressure.getFieldBodyPosition()).isEqualTo(DEFAULT_FIELD_BODY_POSITION);
        assertThat(testBloodPressure.getFieldBloodPressureMeasureLocation()).isEqualTo(DEFAULT_FIELD_BLOOD_PRESSURE_MEASURE_LOCATION);
        assertThat(testBloodPressure.getEndTime()).isEqualTo(DEFAULT_END_TIME);
    }

    @Test
    @Transactional
    void createBloodPressureWithExistingId() throws Exception {
        // Create the BloodPressure with an existing ID
        bloodPressureRepository.saveAndFlush(bloodPressure);

        int databaseSizeBeforeCreate = bloodPressureRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBloodPressureMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bloodPressure)))
            .andExpect(status().isBadRequest());

        // Validate the BloodPressure in the database
        List<BloodPressure> bloodPressureList = bloodPressureRepository.findAll();
        assertThat(bloodPressureList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllBloodPressures() throws Exception {
        // Initialize the database
        bloodPressureRepository.saveAndFlush(bloodPressure);

        // Get all the bloodPressureList
        restBloodPressureMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bloodPressure.getId().toString())))
            .andExpect(jsonPath("$.[*].usuarioId").value(hasItem(DEFAULT_USUARIO_ID)))
            .andExpect(jsonPath("$.[*].empresaId").value(hasItem(DEFAULT_EMPRESA_ID)))
            .andExpect(jsonPath("$.[*].fieldBloodPressureSystolic").value(hasItem(DEFAULT_FIELD_BLOOD_PRESSURE_SYSTOLIC.doubleValue())))
            .andExpect(jsonPath("$.[*].fieldBloodPressureDiastolic").value(hasItem(DEFAULT_FIELD_BLOOD_PRESSURE_DIASTOLIC.doubleValue())))
            .andExpect(jsonPath("$.[*].fieldBodyPosition").value(hasItem(DEFAULT_FIELD_BODY_POSITION)))
            .andExpect(jsonPath("$.[*].fieldBloodPressureMeasureLocation").value(hasItem(DEFAULT_FIELD_BLOOD_PRESSURE_MEASURE_LOCATION)))
            .andExpect(jsonPath("$.[*].endTime").value(hasItem(DEFAULT_END_TIME.toString())));
    }

    @Test
    @Transactional
    void getBloodPressure() throws Exception {
        // Initialize the database
        bloodPressureRepository.saveAndFlush(bloodPressure);

        // Get the bloodPressure
        restBloodPressureMockMvc
            .perform(get(ENTITY_API_URL_ID, bloodPressure.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(bloodPressure.getId().toString()))
            .andExpect(jsonPath("$.usuarioId").value(DEFAULT_USUARIO_ID))
            .andExpect(jsonPath("$.empresaId").value(DEFAULT_EMPRESA_ID))
            .andExpect(jsonPath("$.fieldBloodPressureSystolic").value(DEFAULT_FIELD_BLOOD_PRESSURE_SYSTOLIC.doubleValue()))
            .andExpect(jsonPath("$.fieldBloodPressureDiastolic").value(DEFAULT_FIELD_BLOOD_PRESSURE_DIASTOLIC.doubleValue()))
            .andExpect(jsonPath("$.fieldBodyPosition").value(DEFAULT_FIELD_BODY_POSITION))
            .andExpect(jsonPath("$.fieldBloodPressureMeasureLocation").value(DEFAULT_FIELD_BLOOD_PRESSURE_MEASURE_LOCATION))
            .andExpect(jsonPath("$.endTime").value(DEFAULT_END_TIME.toString()));
    }

    @Test
    @Transactional
    void getBloodPressuresByIdFiltering() throws Exception {
        // Initialize the database
        bloodPressureRepository.saveAndFlush(bloodPressure);

        UUID id = bloodPressure.getId();

        defaultBloodPressureShouldBeFound("id.equals=" + id);
        defaultBloodPressureShouldNotBeFound("id.notEquals=" + id);
    }

    @Test
    @Transactional
    void getAllBloodPressuresByUsuarioIdIsEqualToSomething() throws Exception {
        // Initialize the database
        bloodPressureRepository.saveAndFlush(bloodPressure);

        // Get all the bloodPressureList where usuarioId equals to DEFAULT_USUARIO_ID
        defaultBloodPressureShouldBeFound("usuarioId.equals=" + DEFAULT_USUARIO_ID);

        // Get all the bloodPressureList where usuarioId equals to UPDATED_USUARIO_ID
        defaultBloodPressureShouldNotBeFound("usuarioId.equals=" + UPDATED_USUARIO_ID);
    }

    @Test
    @Transactional
    void getAllBloodPressuresByUsuarioIdIsInShouldWork() throws Exception {
        // Initialize the database
        bloodPressureRepository.saveAndFlush(bloodPressure);

        // Get all the bloodPressureList where usuarioId in DEFAULT_USUARIO_ID or UPDATED_USUARIO_ID
        defaultBloodPressureShouldBeFound("usuarioId.in=" + DEFAULT_USUARIO_ID + "," + UPDATED_USUARIO_ID);

        // Get all the bloodPressureList where usuarioId equals to UPDATED_USUARIO_ID
        defaultBloodPressureShouldNotBeFound("usuarioId.in=" + UPDATED_USUARIO_ID);
    }

    @Test
    @Transactional
    void getAllBloodPressuresByUsuarioIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        bloodPressureRepository.saveAndFlush(bloodPressure);

        // Get all the bloodPressureList where usuarioId is not null
        defaultBloodPressureShouldBeFound("usuarioId.specified=true");

        // Get all the bloodPressureList where usuarioId is null
        defaultBloodPressureShouldNotBeFound("usuarioId.specified=false");
    }

    @Test
    @Transactional
    void getAllBloodPressuresByUsuarioIdContainsSomething() throws Exception {
        // Initialize the database
        bloodPressureRepository.saveAndFlush(bloodPressure);

        // Get all the bloodPressureList where usuarioId contains DEFAULT_USUARIO_ID
        defaultBloodPressureShouldBeFound("usuarioId.contains=" + DEFAULT_USUARIO_ID);

        // Get all the bloodPressureList where usuarioId contains UPDATED_USUARIO_ID
        defaultBloodPressureShouldNotBeFound("usuarioId.contains=" + UPDATED_USUARIO_ID);
    }

    @Test
    @Transactional
    void getAllBloodPressuresByUsuarioIdNotContainsSomething() throws Exception {
        // Initialize the database
        bloodPressureRepository.saveAndFlush(bloodPressure);

        // Get all the bloodPressureList where usuarioId does not contain DEFAULT_USUARIO_ID
        defaultBloodPressureShouldNotBeFound("usuarioId.doesNotContain=" + DEFAULT_USUARIO_ID);

        // Get all the bloodPressureList where usuarioId does not contain UPDATED_USUARIO_ID
        defaultBloodPressureShouldBeFound("usuarioId.doesNotContain=" + UPDATED_USUARIO_ID);
    }

    @Test
    @Transactional
    void getAllBloodPressuresByEmpresaIdIsEqualToSomething() throws Exception {
        // Initialize the database
        bloodPressureRepository.saveAndFlush(bloodPressure);

        // Get all the bloodPressureList where empresaId equals to DEFAULT_EMPRESA_ID
        defaultBloodPressureShouldBeFound("empresaId.equals=" + DEFAULT_EMPRESA_ID);

        // Get all the bloodPressureList where empresaId equals to UPDATED_EMPRESA_ID
        defaultBloodPressureShouldNotBeFound("empresaId.equals=" + UPDATED_EMPRESA_ID);
    }

    @Test
    @Transactional
    void getAllBloodPressuresByEmpresaIdIsInShouldWork() throws Exception {
        // Initialize the database
        bloodPressureRepository.saveAndFlush(bloodPressure);

        // Get all the bloodPressureList where empresaId in DEFAULT_EMPRESA_ID or UPDATED_EMPRESA_ID
        defaultBloodPressureShouldBeFound("empresaId.in=" + DEFAULT_EMPRESA_ID + "," + UPDATED_EMPRESA_ID);

        // Get all the bloodPressureList where empresaId equals to UPDATED_EMPRESA_ID
        defaultBloodPressureShouldNotBeFound("empresaId.in=" + UPDATED_EMPRESA_ID);
    }

    @Test
    @Transactional
    void getAllBloodPressuresByEmpresaIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        bloodPressureRepository.saveAndFlush(bloodPressure);

        // Get all the bloodPressureList where empresaId is not null
        defaultBloodPressureShouldBeFound("empresaId.specified=true");

        // Get all the bloodPressureList where empresaId is null
        defaultBloodPressureShouldNotBeFound("empresaId.specified=false");
    }

    @Test
    @Transactional
    void getAllBloodPressuresByEmpresaIdContainsSomething() throws Exception {
        // Initialize the database
        bloodPressureRepository.saveAndFlush(bloodPressure);

        // Get all the bloodPressureList where empresaId contains DEFAULT_EMPRESA_ID
        defaultBloodPressureShouldBeFound("empresaId.contains=" + DEFAULT_EMPRESA_ID);

        // Get all the bloodPressureList where empresaId contains UPDATED_EMPRESA_ID
        defaultBloodPressureShouldNotBeFound("empresaId.contains=" + UPDATED_EMPRESA_ID);
    }

    @Test
    @Transactional
    void getAllBloodPressuresByEmpresaIdNotContainsSomething() throws Exception {
        // Initialize the database
        bloodPressureRepository.saveAndFlush(bloodPressure);

        // Get all the bloodPressureList where empresaId does not contain DEFAULT_EMPRESA_ID
        defaultBloodPressureShouldNotBeFound("empresaId.doesNotContain=" + DEFAULT_EMPRESA_ID);

        // Get all the bloodPressureList where empresaId does not contain UPDATED_EMPRESA_ID
        defaultBloodPressureShouldBeFound("empresaId.doesNotContain=" + UPDATED_EMPRESA_ID);
    }

    @Test
    @Transactional
    void getAllBloodPressuresByFieldBloodPressureSystolicIsEqualToSomething() throws Exception {
        // Initialize the database
        bloodPressureRepository.saveAndFlush(bloodPressure);

        // Get all the bloodPressureList where fieldBloodPressureSystolic equals to DEFAULT_FIELD_BLOOD_PRESSURE_SYSTOLIC
        defaultBloodPressureShouldBeFound("fieldBloodPressureSystolic.equals=" + DEFAULT_FIELD_BLOOD_PRESSURE_SYSTOLIC);

        // Get all the bloodPressureList where fieldBloodPressureSystolic equals to UPDATED_FIELD_BLOOD_PRESSURE_SYSTOLIC
        defaultBloodPressureShouldNotBeFound("fieldBloodPressureSystolic.equals=" + UPDATED_FIELD_BLOOD_PRESSURE_SYSTOLIC);
    }

    @Test
    @Transactional
    void getAllBloodPressuresByFieldBloodPressureSystolicIsInShouldWork() throws Exception {
        // Initialize the database
        bloodPressureRepository.saveAndFlush(bloodPressure);

        // Get all the bloodPressureList where fieldBloodPressureSystolic in DEFAULT_FIELD_BLOOD_PRESSURE_SYSTOLIC or UPDATED_FIELD_BLOOD_PRESSURE_SYSTOLIC
        defaultBloodPressureShouldBeFound(
            "fieldBloodPressureSystolic.in=" + DEFAULT_FIELD_BLOOD_PRESSURE_SYSTOLIC + "," + UPDATED_FIELD_BLOOD_PRESSURE_SYSTOLIC
        );

        // Get all the bloodPressureList where fieldBloodPressureSystolic equals to UPDATED_FIELD_BLOOD_PRESSURE_SYSTOLIC
        defaultBloodPressureShouldNotBeFound("fieldBloodPressureSystolic.in=" + UPDATED_FIELD_BLOOD_PRESSURE_SYSTOLIC);
    }

    @Test
    @Transactional
    void getAllBloodPressuresByFieldBloodPressureSystolicIsNullOrNotNull() throws Exception {
        // Initialize the database
        bloodPressureRepository.saveAndFlush(bloodPressure);

        // Get all the bloodPressureList where fieldBloodPressureSystolic is not null
        defaultBloodPressureShouldBeFound("fieldBloodPressureSystolic.specified=true");

        // Get all the bloodPressureList where fieldBloodPressureSystolic is null
        defaultBloodPressureShouldNotBeFound("fieldBloodPressureSystolic.specified=false");
    }

    @Test
    @Transactional
    void getAllBloodPressuresByFieldBloodPressureSystolicIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        bloodPressureRepository.saveAndFlush(bloodPressure);

        // Get all the bloodPressureList where fieldBloodPressureSystolic is greater than or equal to DEFAULT_FIELD_BLOOD_PRESSURE_SYSTOLIC
        defaultBloodPressureShouldBeFound("fieldBloodPressureSystolic.greaterThanOrEqual=" + DEFAULT_FIELD_BLOOD_PRESSURE_SYSTOLIC);

        // Get all the bloodPressureList where fieldBloodPressureSystolic is greater than or equal to UPDATED_FIELD_BLOOD_PRESSURE_SYSTOLIC
        defaultBloodPressureShouldNotBeFound("fieldBloodPressureSystolic.greaterThanOrEqual=" + UPDATED_FIELD_BLOOD_PRESSURE_SYSTOLIC);
    }

    @Test
    @Transactional
    void getAllBloodPressuresByFieldBloodPressureSystolicIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        bloodPressureRepository.saveAndFlush(bloodPressure);

        // Get all the bloodPressureList where fieldBloodPressureSystolic is less than or equal to DEFAULT_FIELD_BLOOD_PRESSURE_SYSTOLIC
        defaultBloodPressureShouldBeFound("fieldBloodPressureSystolic.lessThanOrEqual=" + DEFAULT_FIELD_BLOOD_PRESSURE_SYSTOLIC);

        // Get all the bloodPressureList where fieldBloodPressureSystolic is less than or equal to SMALLER_FIELD_BLOOD_PRESSURE_SYSTOLIC
        defaultBloodPressureShouldNotBeFound("fieldBloodPressureSystolic.lessThanOrEqual=" + SMALLER_FIELD_BLOOD_PRESSURE_SYSTOLIC);
    }

    @Test
    @Transactional
    void getAllBloodPressuresByFieldBloodPressureSystolicIsLessThanSomething() throws Exception {
        // Initialize the database
        bloodPressureRepository.saveAndFlush(bloodPressure);

        // Get all the bloodPressureList where fieldBloodPressureSystolic is less than DEFAULT_FIELD_BLOOD_PRESSURE_SYSTOLIC
        defaultBloodPressureShouldNotBeFound("fieldBloodPressureSystolic.lessThan=" + DEFAULT_FIELD_BLOOD_PRESSURE_SYSTOLIC);

        // Get all the bloodPressureList where fieldBloodPressureSystolic is less than UPDATED_FIELD_BLOOD_PRESSURE_SYSTOLIC
        defaultBloodPressureShouldBeFound("fieldBloodPressureSystolic.lessThan=" + UPDATED_FIELD_BLOOD_PRESSURE_SYSTOLIC);
    }

    @Test
    @Transactional
    void getAllBloodPressuresByFieldBloodPressureSystolicIsGreaterThanSomething() throws Exception {
        // Initialize the database
        bloodPressureRepository.saveAndFlush(bloodPressure);

        // Get all the bloodPressureList where fieldBloodPressureSystolic is greater than DEFAULT_FIELD_BLOOD_PRESSURE_SYSTOLIC
        defaultBloodPressureShouldNotBeFound("fieldBloodPressureSystolic.greaterThan=" + DEFAULT_FIELD_BLOOD_PRESSURE_SYSTOLIC);

        // Get all the bloodPressureList where fieldBloodPressureSystolic is greater than SMALLER_FIELD_BLOOD_PRESSURE_SYSTOLIC
        defaultBloodPressureShouldBeFound("fieldBloodPressureSystolic.greaterThan=" + SMALLER_FIELD_BLOOD_PRESSURE_SYSTOLIC);
    }

    @Test
    @Transactional
    void getAllBloodPressuresByFieldBloodPressureDiastolicIsEqualToSomething() throws Exception {
        // Initialize the database
        bloodPressureRepository.saveAndFlush(bloodPressure);

        // Get all the bloodPressureList where fieldBloodPressureDiastolic equals to DEFAULT_FIELD_BLOOD_PRESSURE_DIASTOLIC
        defaultBloodPressureShouldBeFound("fieldBloodPressureDiastolic.equals=" + DEFAULT_FIELD_BLOOD_PRESSURE_DIASTOLIC);

        // Get all the bloodPressureList where fieldBloodPressureDiastolic equals to UPDATED_FIELD_BLOOD_PRESSURE_DIASTOLIC
        defaultBloodPressureShouldNotBeFound("fieldBloodPressureDiastolic.equals=" + UPDATED_FIELD_BLOOD_PRESSURE_DIASTOLIC);
    }

    @Test
    @Transactional
    void getAllBloodPressuresByFieldBloodPressureDiastolicIsInShouldWork() throws Exception {
        // Initialize the database
        bloodPressureRepository.saveAndFlush(bloodPressure);

        // Get all the bloodPressureList where fieldBloodPressureDiastolic in DEFAULT_FIELD_BLOOD_PRESSURE_DIASTOLIC or UPDATED_FIELD_BLOOD_PRESSURE_DIASTOLIC
        defaultBloodPressureShouldBeFound(
            "fieldBloodPressureDiastolic.in=" + DEFAULT_FIELD_BLOOD_PRESSURE_DIASTOLIC + "," + UPDATED_FIELD_BLOOD_PRESSURE_DIASTOLIC
        );

        // Get all the bloodPressureList where fieldBloodPressureDiastolic equals to UPDATED_FIELD_BLOOD_PRESSURE_DIASTOLIC
        defaultBloodPressureShouldNotBeFound("fieldBloodPressureDiastolic.in=" + UPDATED_FIELD_BLOOD_PRESSURE_DIASTOLIC);
    }

    @Test
    @Transactional
    void getAllBloodPressuresByFieldBloodPressureDiastolicIsNullOrNotNull() throws Exception {
        // Initialize the database
        bloodPressureRepository.saveAndFlush(bloodPressure);

        // Get all the bloodPressureList where fieldBloodPressureDiastolic is not null
        defaultBloodPressureShouldBeFound("fieldBloodPressureDiastolic.specified=true");

        // Get all the bloodPressureList where fieldBloodPressureDiastolic is null
        defaultBloodPressureShouldNotBeFound("fieldBloodPressureDiastolic.specified=false");
    }

    @Test
    @Transactional
    void getAllBloodPressuresByFieldBloodPressureDiastolicIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        bloodPressureRepository.saveAndFlush(bloodPressure);

        // Get all the bloodPressureList where fieldBloodPressureDiastolic is greater than or equal to DEFAULT_FIELD_BLOOD_PRESSURE_DIASTOLIC
        defaultBloodPressureShouldBeFound("fieldBloodPressureDiastolic.greaterThanOrEqual=" + DEFAULT_FIELD_BLOOD_PRESSURE_DIASTOLIC);

        // Get all the bloodPressureList where fieldBloodPressureDiastolic is greater than or equal to UPDATED_FIELD_BLOOD_PRESSURE_DIASTOLIC
        defaultBloodPressureShouldNotBeFound("fieldBloodPressureDiastolic.greaterThanOrEqual=" + UPDATED_FIELD_BLOOD_PRESSURE_DIASTOLIC);
    }

    @Test
    @Transactional
    void getAllBloodPressuresByFieldBloodPressureDiastolicIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        bloodPressureRepository.saveAndFlush(bloodPressure);

        // Get all the bloodPressureList where fieldBloodPressureDiastolic is less than or equal to DEFAULT_FIELD_BLOOD_PRESSURE_DIASTOLIC
        defaultBloodPressureShouldBeFound("fieldBloodPressureDiastolic.lessThanOrEqual=" + DEFAULT_FIELD_BLOOD_PRESSURE_DIASTOLIC);

        // Get all the bloodPressureList where fieldBloodPressureDiastolic is less than or equal to SMALLER_FIELD_BLOOD_PRESSURE_DIASTOLIC
        defaultBloodPressureShouldNotBeFound("fieldBloodPressureDiastolic.lessThanOrEqual=" + SMALLER_FIELD_BLOOD_PRESSURE_DIASTOLIC);
    }

    @Test
    @Transactional
    void getAllBloodPressuresByFieldBloodPressureDiastolicIsLessThanSomething() throws Exception {
        // Initialize the database
        bloodPressureRepository.saveAndFlush(bloodPressure);

        // Get all the bloodPressureList where fieldBloodPressureDiastolic is less than DEFAULT_FIELD_BLOOD_PRESSURE_DIASTOLIC
        defaultBloodPressureShouldNotBeFound("fieldBloodPressureDiastolic.lessThan=" + DEFAULT_FIELD_BLOOD_PRESSURE_DIASTOLIC);

        // Get all the bloodPressureList where fieldBloodPressureDiastolic is less than UPDATED_FIELD_BLOOD_PRESSURE_DIASTOLIC
        defaultBloodPressureShouldBeFound("fieldBloodPressureDiastolic.lessThan=" + UPDATED_FIELD_BLOOD_PRESSURE_DIASTOLIC);
    }

    @Test
    @Transactional
    void getAllBloodPressuresByFieldBloodPressureDiastolicIsGreaterThanSomething() throws Exception {
        // Initialize the database
        bloodPressureRepository.saveAndFlush(bloodPressure);

        // Get all the bloodPressureList where fieldBloodPressureDiastolic is greater than DEFAULT_FIELD_BLOOD_PRESSURE_DIASTOLIC
        defaultBloodPressureShouldNotBeFound("fieldBloodPressureDiastolic.greaterThan=" + DEFAULT_FIELD_BLOOD_PRESSURE_DIASTOLIC);

        // Get all the bloodPressureList where fieldBloodPressureDiastolic is greater than SMALLER_FIELD_BLOOD_PRESSURE_DIASTOLIC
        defaultBloodPressureShouldBeFound("fieldBloodPressureDiastolic.greaterThan=" + SMALLER_FIELD_BLOOD_PRESSURE_DIASTOLIC);
    }

    @Test
    @Transactional
    void getAllBloodPressuresByFieldBodyPositionIsEqualToSomething() throws Exception {
        // Initialize the database
        bloodPressureRepository.saveAndFlush(bloodPressure);

        // Get all the bloodPressureList where fieldBodyPosition equals to DEFAULT_FIELD_BODY_POSITION
        defaultBloodPressureShouldBeFound("fieldBodyPosition.equals=" + DEFAULT_FIELD_BODY_POSITION);

        // Get all the bloodPressureList where fieldBodyPosition equals to UPDATED_FIELD_BODY_POSITION
        defaultBloodPressureShouldNotBeFound("fieldBodyPosition.equals=" + UPDATED_FIELD_BODY_POSITION);
    }

    @Test
    @Transactional
    void getAllBloodPressuresByFieldBodyPositionIsInShouldWork() throws Exception {
        // Initialize the database
        bloodPressureRepository.saveAndFlush(bloodPressure);

        // Get all the bloodPressureList where fieldBodyPosition in DEFAULT_FIELD_BODY_POSITION or UPDATED_FIELD_BODY_POSITION
        defaultBloodPressureShouldBeFound("fieldBodyPosition.in=" + DEFAULT_FIELD_BODY_POSITION + "," + UPDATED_FIELD_BODY_POSITION);

        // Get all the bloodPressureList where fieldBodyPosition equals to UPDATED_FIELD_BODY_POSITION
        defaultBloodPressureShouldNotBeFound("fieldBodyPosition.in=" + UPDATED_FIELD_BODY_POSITION);
    }

    @Test
    @Transactional
    void getAllBloodPressuresByFieldBodyPositionIsNullOrNotNull() throws Exception {
        // Initialize the database
        bloodPressureRepository.saveAndFlush(bloodPressure);

        // Get all the bloodPressureList where fieldBodyPosition is not null
        defaultBloodPressureShouldBeFound("fieldBodyPosition.specified=true");

        // Get all the bloodPressureList where fieldBodyPosition is null
        defaultBloodPressureShouldNotBeFound("fieldBodyPosition.specified=false");
    }

    @Test
    @Transactional
    void getAllBloodPressuresByFieldBodyPositionContainsSomething() throws Exception {
        // Initialize the database
        bloodPressureRepository.saveAndFlush(bloodPressure);

        // Get all the bloodPressureList where fieldBodyPosition contains DEFAULT_FIELD_BODY_POSITION
        defaultBloodPressureShouldBeFound("fieldBodyPosition.contains=" + DEFAULT_FIELD_BODY_POSITION);

        // Get all the bloodPressureList where fieldBodyPosition contains UPDATED_FIELD_BODY_POSITION
        defaultBloodPressureShouldNotBeFound("fieldBodyPosition.contains=" + UPDATED_FIELD_BODY_POSITION);
    }

    @Test
    @Transactional
    void getAllBloodPressuresByFieldBodyPositionNotContainsSomething() throws Exception {
        // Initialize the database
        bloodPressureRepository.saveAndFlush(bloodPressure);

        // Get all the bloodPressureList where fieldBodyPosition does not contain DEFAULT_FIELD_BODY_POSITION
        defaultBloodPressureShouldNotBeFound("fieldBodyPosition.doesNotContain=" + DEFAULT_FIELD_BODY_POSITION);

        // Get all the bloodPressureList where fieldBodyPosition does not contain UPDATED_FIELD_BODY_POSITION
        defaultBloodPressureShouldBeFound("fieldBodyPosition.doesNotContain=" + UPDATED_FIELD_BODY_POSITION);
    }

    @Test
    @Transactional
    void getAllBloodPressuresByFieldBloodPressureMeasureLocationIsEqualToSomething() throws Exception {
        // Initialize the database
        bloodPressureRepository.saveAndFlush(bloodPressure);

        // Get all the bloodPressureList where fieldBloodPressureMeasureLocation equals to DEFAULT_FIELD_BLOOD_PRESSURE_MEASURE_LOCATION
        defaultBloodPressureShouldBeFound("fieldBloodPressureMeasureLocation.equals=" + DEFAULT_FIELD_BLOOD_PRESSURE_MEASURE_LOCATION);

        // Get all the bloodPressureList where fieldBloodPressureMeasureLocation equals to UPDATED_FIELD_BLOOD_PRESSURE_MEASURE_LOCATION
        defaultBloodPressureShouldNotBeFound("fieldBloodPressureMeasureLocation.equals=" + UPDATED_FIELD_BLOOD_PRESSURE_MEASURE_LOCATION);
    }

    @Test
    @Transactional
    void getAllBloodPressuresByFieldBloodPressureMeasureLocationIsInShouldWork() throws Exception {
        // Initialize the database
        bloodPressureRepository.saveAndFlush(bloodPressure);

        // Get all the bloodPressureList where fieldBloodPressureMeasureLocation in DEFAULT_FIELD_BLOOD_PRESSURE_MEASURE_LOCATION or UPDATED_FIELD_BLOOD_PRESSURE_MEASURE_LOCATION
        defaultBloodPressureShouldBeFound(
            "fieldBloodPressureMeasureLocation.in=" +
            DEFAULT_FIELD_BLOOD_PRESSURE_MEASURE_LOCATION +
            "," +
            UPDATED_FIELD_BLOOD_PRESSURE_MEASURE_LOCATION
        );

        // Get all the bloodPressureList where fieldBloodPressureMeasureLocation equals to UPDATED_FIELD_BLOOD_PRESSURE_MEASURE_LOCATION
        defaultBloodPressureShouldNotBeFound("fieldBloodPressureMeasureLocation.in=" + UPDATED_FIELD_BLOOD_PRESSURE_MEASURE_LOCATION);
    }

    @Test
    @Transactional
    void getAllBloodPressuresByFieldBloodPressureMeasureLocationIsNullOrNotNull() throws Exception {
        // Initialize the database
        bloodPressureRepository.saveAndFlush(bloodPressure);

        // Get all the bloodPressureList where fieldBloodPressureMeasureLocation is not null
        defaultBloodPressureShouldBeFound("fieldBloodPressureMeasureLocation.specified=true");

        // Get all the bloodPressureList where fieldBloodPressureMeasureLocation is null
        defaultBloodPressureShouldNotBeFound("fieldBloodPressureMeasureLocation.specified=false");
    }

    @Test
    @Transactional
    void getAllBloodPressuresByFieldBloodPressureMeasureLocationIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        bloodPressureRepository.saveAndFlush(bloodPressure);

        // Get all the bloodPressureList where fieldBloodPressureMeasureLocation is greater than or equal to DEFAULT_FIELD_BLOOD_PRESSURE_MEASURE_LOCATION
        defaultBloodPressureShouldBeFound(
            "fieldBloodPressureMeasureLocation.greaterThanOrEqual=" + DEFAULT_FIELD_BLOOD_PRESSURE_MEASURE_LOCATION
        );

        // Get all the bloodPressureList where fieldBloodPressureMeasureLocation is greater than or equal to UPDATED_FIELD_BLOOD_PRESSURE_MEASURE_LOCATION
        defaultBloodPressureShouldNotBeFound(
            "fieldBloodPressureMeasureLocation.greaterThanOrEqual=" + UPDATED_FIELD_BLOOD_PRESSURE_MEASURE_LOCATION
        );
    }

    @Test
    @Transactional
    void getAllBloodPressuresByFieldBloodPressureMeasureLocationIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        bloodPressureRepository.saveAndFlush(bloodPressure);

        // Get all the bloodPressureList where fieldBloodPressureMeasureLocation is less than or equal to DEFAULT_FIELD_BLOOD_PRESSURE_MEASURE_LOCATION
        defaultBloodPressureShouldBeFound(
            "fieldBloodPressureMeasureLocation.lessThanOrEqual=" + DEFAULT_FIELD_BLOOD_PRESSURE_MEASURE_LOCATION
        );

        // Get all the bloodPressureList where fieldBloodPressureMeasureLocation is less than or equal to SMALLER_FIELD_BLOOD_PRESSURE_MEASURE_LOCATION
        defaultBloodPressureShouldNotBeFound(
            "fieldBloodPressureMeasureLocation.lessThanOrEqual=" + SMALLER_FIELD_BLOOD_PRESSURE_MEASURE_LOCATION
        );
    }

    @Test
    @Transactional
    void getAllBloodPressuresByFieldBloodPressureMeasureLocationIsLessThanSomething() throws Exception {
        // Initialize the database
        bloodPressureRepository.saveAndFlush(bloodPressure);

        // Get all the bloodPressureList where fieldBloodPressureMeasureLocation is less than DEFAULT_FIELD_BLOOD_PRESSURE_MEASURE_LOCATION
        defaultBloodPressureShouldNotBeFound("fieldBloodPressureMeasureLocation.lessThan=" + DEFAULT_FIELD_BLOOD_PRESSURE_MEASURE_LOCATION);

        // Get all the bloodPressureList where fieldBloodPressureMeasureLocation is less than UPDATED_FIELD_BLOOD_PRESSURE_MEASURE_LOCATION
        defaultBloodPressureShouldBeFound("fieldBloodPressureMeasureLocation.lessThan=" + UPDATED_FIELD_BLOOD_PRESSURE_MEASURE_LOCATION);
    }

    @Test
    @Transactional
    void getAllBloodPressuresByFieldBloodPressureMeasureLocationIsGreaterThanSomething() throws Exception {
        // Initialize the database
        bloodPressureRepository.saveAndFlush(bloodPressure);

        // Get all the bloodPressureList where fieldBloodPressureMeasureLocation is greater than DEFAULT_FIELD_BLOOD_PRESSURE_MEASURE_LOCATION
        defaultBloodPressureShouldNotBeFound(
            "fieldBloodPressureMeasureLocation.greaterThan=" + DEFAULT_FIELD_BLOOD_PRESSURE_MEASURE_LOCATION
        );

        // Get all the bloodPressureList where fieldBloodPressureMeasureLocation is greater than SMALLER_FIELD_BLOOD_PRESSURE_MEASURE_LOCATION
        defaultBloodPressureShouldBeFound("fieldBloodPressureMeasureLocation.greaterThan=" + SMALLER_FIELD_BLOOD_PRESSURE_MEASURE_LOCATION);
    }

    @Test
    @Transactional
    void getAllBloodPressuresByEndTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        bloodPressureRepository.saveAndFlush(bloodPressure);

        // Get all the bloodPressureList where endTime equals to DEFAULT_END_TIME
        defaultBloodPressureShouldBeFound("endTime.equals=" + DEFAULT_END_TIME);

        // Get all the bloodPressureList where endTime equals to UPDATED_END_TIME
        defaultBloodPressureShouldNotBeFound("endTime.equals=" + UPDATED_END_TIME);
    }

    @Test
    @Transactional
    void getAllBloodPressuresByEndTimeIsInShouldWork() throws Exception {
        // Initialize the database
        bloodPressureRepository.saveAndFlush(bloodPressure);

        // Get all the bloodPressureList where endTime in DEFAULT_END_TIME or UPDATED_END_TIME
        defaultBloodPressureShouldBeFound("endTime.in=" + DEFAULT_END_TIME + "," + UPDATED_END_TIME);

        // Get all the bloodPressureList where endTime equals to UPDATED_END_TIME
        defaultBloodPressureShouldNotBeFound("endTime.in=" + UPDATED_END_TIME);
    }

    @Test
    @Transactional
    void getAllBloodPressuresByEndTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        bloodPressureRepository.saveAndFlush(bloodPressure);

        // Get all the bloodPressureList where endTime is not null
        defaultBloodPressureShouldBeFound("endTime.specified=true");

        // Get all the bloodPressureList where endTime is null
        defaultBloodPressureShouldNotBeFound("endTime.specified=false");
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultBloodPressureShouldBeFound(String filter) throws Exception {
        restBloodPressureMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bloodPressure.getId().toString())))
            .andExpect(jsonPath("$.[*].usuarioId").value(hasItem(DEFAULT_USUARIO_ID)))
            .andExpect(jsonPath("$.[*].empresaId").value(hasItem(DEFAULT_EMPRESA_ID)))
            .andExpect(jsonPath("$.[*].fieldBloodPressureSystolic").value(hasItem(DEFAULT_FIELD_BLOOD_PRESSURE_SYSTOLIC.doubleValue())))
            .andExpect(jsonPath("$.[*].fieldBloodPressureDiastolic").value(hasItem(DEFAULT_FIELD_BLOOD_PRESSURE_DIASTOLIC.doubleValue())))
            .andExpect(jsonPath("$.[*].fieldBodyPosition").value(hasItem(DEFAULT_FIELD_BODY_POSITION)))
            .andExpect(jsonPath("$.[*].fieldBloodPressureMeasureLocation").value(hasItem(DEFAULT_FIELD_BLOOD_PRESSURE_MEASURE_LOCATION)))
            .andExpect(jsonPath("$.[*].endTime").value(hasItem(DEFAULT_END_TIME.toString())));

        // Check, that the count call also returns 1
        restBloodPressureMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultBloodPressureShouldNotBeFound(String filter) throws Exception {
        restBloodPressureMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restBloodPressureMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingBloodPressure() throws Exception {
        // Get the bloodPressure
        restBloodPressureMockMvc.perform(get(ENTITY_API_URL_ID, UUID.randomUUID().toString())).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingBloodPressure() throws Exception {
        // Initialize the database
        bloodPressureRepository.saveAndFlush(bloodPressure);

        int databaseSizeBeforeUpdate = bloodPressureRepository.findAll().size();

        // Update the bloodPressure
        BloodPressure updatedBloodPressure = bloodPressureRepository.findById(bloodPressure.getId()).get();
        // Disconnect from session so that the updates on updatedBloodPressure are not directly saved in db
        em.detach(updatedBloodPressure);
        updatedBloodPressure
            .usuarioId(UPDATED_USUARIO_ID)
            .empresaId(UPDATED_EMPRESA_ID)
            .fieldBloodPressureSystolic(UPDATED_FIELD_BLOOD_PRESSURE_SYSTOLIC)
            .fieldBloodPressureDiastolic(UPDATED_FIELD_BLOOD_PRESSURE_DIASTOLIC)
            .fieldBodyPosition(UPDATED_FIELD_BODY_POSITION)
            .fieldBloodPressureMeasureLocation(UPDATED_FIELD_BLOOD_PRESSURE_MEASURE_LOCATION)
            .endTime(UPDATED_END_TIME);

        restBloodPressureMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedBloodPressure.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedBloodPressure))
            )
            .andExpect(status().isOk());

        // Validate the BloodPressure in the database
        List<BloodPressure> bloodPressureList = bloodPressureRepository.findAll();
        assertThat(bloodPressureList).hasSize(databaseSizeBeforeUpdate);
        BloodPressure testBloodPressure = bloodPressureList.get(bloodPressureList.size() - 1);
        assertThat(testBloodPressure.getUsuarioId()).isEqualTo(UPDATED_USUARIO_ID);
        assertThat(testBloodPressure.getEmpresaId()).isEqualTo(UPDATED_EMPRESA_ID);
        assertThat(testBloodPressure.getFieldBloodPressureSystolic()).isEqualTo(UPDATED_FIELD_BLOOD_PRESSURE_SYSTOLIC);
        assertThat(testBloodPressure.getFieldBloodPressureDiastolic()).isEqualTo(UPDATED_FIELD_BLOOD_PRESSURE_DIASTOLIC);
        assertThat(testBloodPressure.getFieldBodyPosition()).isEqualTo(UPDATED_FIELD_BODY_POSITION);
        assertThat(testBloodPressure.getFieldBloodPressureMeasureLocation()).isEqualTo(UPDATED_FIELD_BLOOD_PRESSURE_MEASURE_LOCATION);
        assertThat(testBloodPressure.getEndTime()).isEqualTo(UPDATED_END_TIME);
    }

    @Test
    @Transactional
    void putNonExistingBloodPressure() throws Exception {
        int databaseSizeBeforeUpdate = bloodPressureRepository.findAll().size();
        bloodPressure.setId(UUID.randomUUID());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBloodPressureMockMvc
            .perform(
                put(ENTITY_API_URL_ID, bloodPressure.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bloodPressure))
            )
            .andExpect(status().isBadRequest());

        // Validate the BloodPressure in the database
        List<BloodPressure> bloodPressureList = bloodPressureRepository.findAll();
        assertThat(bloodPressureList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchBloodPressure() throws Exception {
        int databaseSizeBeforeUpdate = bloodPressureRepository.findAll().size();
        bloodPressure.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBloodPressureMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bloodPressure))
            )
            .andExpect(status().isBadRequest());

        // Validate the BloodPressure in the database
        List<BloodPressure> bloodPressureList = bloodPressureRepository.findAll();
        assertThat(bloodPressureList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamBloodPressure() throws Exception {
        int databaseSizeBeforeUpdate = bloodPressureRepository.findAll().size();
        bloodPressure.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBloodPressureMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bloodPressure)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the BloodPressure in the database
        List<BloodPressure> bloodPressureList = bloodPressureRepository.findAll();
        assertThat(bloodPressureList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateBloodPressureWithPatch() throws Exception {
        // Initialize the database
        bloodPressureRepository.saveAndFlush(bloodPressure);

        int databaseSizeBeforeUpdate = bloodPressureRepository.findAll().size();

        // Update the bloodPressure using partial update
        BloodPressure partialUpdatedBloodPressure = new BloodPressure();
        partialUpdatedBloodPressure.setId(bloodPressure.getId());

        partialUpdatedBloodPressure
            .fieldBloodPressureSystolic(UPDATED_FIELD_BLOOD_PRESSURE_SYSTOLIC)
            .fieldBloodPressureDiastolic(UPDATED_FIELD_BLOOD_PRESSURE_DIASTOLIC)
            .endTime(UPDATED_END_TIME);

        restBloodPressureMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBloodPressure.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBloodPressure))
            )
            .andExpect(status().isOk());

        // Validate the BloodPressure in the database
        List<BloodPressure> bloodPressureList = bloodPressureRepository.findAll();
        assertThat(bloodPressureList).hasSize(databaseSizeBeforeUpdate);
        BloodPressure testBloodPressure = bloodPressureList.get(bloodPressureList.size() - 1);
        assertThat(testBloodPressure.getUsuarioId()).isEqualTo(DEFAULT_USUARIO_ID);
        assertThat(testBloodPressure.getEmpresaId()).isEqualTo(DEFAULT_EMPRESA_ID);
        assertThat(testBloodPressure.getFieldBloodPressureSystolic()).isEqualTo(UPDATED_FIELD_BLOOD_PRESSURE_SYSTOLIC);
        assertThat(testBloodPressure.getFieldBloodPressureDiastolic()).isEqualTo(UPDATED_FIELD_BLOOD_PRESSURE_DIASTOLIC);
        assertThat(testBloodPressure.getFieldBodyPosition()).isEqualTo(DEFAULT_FIELD_BODY_POSITION);
        assertThat(testBloodPressure.getFieldBloodPressureMeasureLocation()).isEqualTo(DEFAULT_FIELD_BLOOD_PRESSURE_MEASURE_LOCATION);
        assertThat(testBloodPressure.getEndTime()).isEqualTo(UPDATED_END_TIME);
    }

    @Test
    @Transactional
    void fullUpdateBloodPressureWithPatch() throws Exception {
        // Initialize the database
        bloodPressureRepository.saveAndFlush(bloodPressure);

        int databaseSizeBeforeUpdate = bloodPressureRepository.findAll().size();

        // Update the bloodPressure using partial update
        BloodPressure partialUpdatedBloodPressure = new BloodPressure();
        partialUpdatedBloodPressure.setId(bloodPressure.getId());

        partialUpdatedBloodPressure
            .usuarioId(UPDATED_USUARIO_ID)
            .empresaId(UPDATED_EMPRESA_ID)
            .fieldBloodPressureSystolic(UPDATED_FIELD_BLOOD_PRESSURE_SYSTOLIC)
            .fieldBloodPressureDiastolic(UPDATED_FIELD_BLOOD_PRESSURE_DIASTOLIC)
            .fieldBodyPosition(UPDATED_FIELD_BODY_POSITION)
            .fieldBloodPressureMeasureLocation(UPDATED_FIELD_BLOOD_PRESSURE_MEASURE_LOCATION)
            .endTime(UPDATED_END_TIME);

        restBloodPressureMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBloodPressure.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBloodPressure))
            )
            .andExpect(status().isOk());

        // Validate the BloodPressure in the database
        List<BloodPressure> bloodPressureList = bloodPressureRepository.findAll();
        assertThat(bloodPressureList).hasSize(databaseSizeBeforeUpdate);
        BloodPressure testBloodPressure = bloodPressureList.get(bloodPressureList.size() - 1);
        assertThat(testBloodPressure.getUsuarioId()).isEqualTo(UPDATED_USUARIO_ID);
        assertThat(testBloodPressure.getEmpresaId()).isEqualTo(UPDATED_EMPRESA_ID);
        assertThat(testBloodPressure.getFieldBloodPressureSystolic()).isEqualTo(UPDATED_FIELD_BLOOD_PRESSURE_SYSTOLIC);
        assertThat(testBloodPressure.getFieldBloodPressureDiastolic()).isEqualTo(UPDATED_FIELD_BLOOD_PRESSURE_DIASTOLIC);
        assertThat(testBloodPressure.getFieldBodyPosition()).isEqualTo(UPDATED_FIELD_BODY_POSITION);
        assertThat(testBloodPressure.getFieldBloodPressureMeasureLocation()).isEqualTo(UPDATED_FIELD_BLOOD_PRESSURE_MEASURE_LOCATION);
        assertThat(testBloodPressure.getEndTime()).isEqualTo(UPDATED_END_TIME);
    }

    @Test
    @Transactional
    void patchNonExistingBloodPressure() throws Exception {
        int databaseSizeBeforeUpdate = bloodPressureRepository.findAll().size();
        bloodPressure.setId(UUID.randomUUID());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBloodPressureMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, bloodPressure.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(bloodPressure))
            )
            .andExpect(status().isBadRequest());

        // Validate the BloodPressure in the database
        List<BloodPressure> bloodPressureList = bloodPressureRepository.findAll();
        assertThat(bloodPressureList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchBloodPressure() throws Exception {
        int databaseSizeBeforeUpdate = bloodPressureRepository.findAll().size();
        bloodPressure.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBloodPressureMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(bloodPressure))
            )
            .andExpect(status().isBadRequest());

        // Validate the BloodPressure in the database
        List<BloodPressure> bloodPressureList = bloodPressureRepository.findAll();
        assertThat(bloodPressureList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamBloodPressure() throws Exception {
        int databaseSizeBeforeUpdate = bloodPressureRepository.findAll().size();
        bloodPressure.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBloodPressureMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(bloodPressure))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the BloodPressure in the database
        List<BloodPressure> bloodPressureList = bloodPressureRepository.findAll();
        assertThat(bloodPressureList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteBloodPressure() throws Exception {
        // Initialize the database
        bloodPressureRepository.saveAndFlush(bloodPressure);

        int databaseSizeBeforeDelete = bloodPressureRepository.findAll().size();

        // Delete the bloodPressure
        restBloodPressureMockMvc
            .perform(delete(ENTITY_API_URL_ID, bloodPressure.getId().toString()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<BloodPressure> bloodPressureList = bloodPressureRepository.findAll();
        assertThat(bloodPressureList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
