package com.be4tech.b4carecollect.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.be4tech.b4carecollect.IntegrationTest;
import com.be4tech.b4carecollect.domain.SleepSegment;
import com.be4tech.b4carecollect.repository.SleepSegmentRepository;
import com.be4tech.b4carecollect.service.criteria.SleepSegmentCriteria;
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
 * Integration tests for the {@link SleepSegmentResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SleepSegmentResourceIT {

    private static final String DEFAULT_USUARIO_ID = "AAAAAAAAAA";
    private static final String UPDATED_USUARIO_ID = "BBBBBBBBBB";

    private static final String DEFAULT_EMPRESA_ID = "AAAAAAAAAA";
    private static final String UPDATED_EMPRESA_ID = "BBBBBBBBBB";

    private static final Integer DEFAULT_FIELD_SLEEP_SEGMENT_TYPE = 1;
    private static final Integer UPDATED_FIELD_SLEEP_SEGMENT_TYPE = 2;
    private static final Integer SMALLER_FIELD_SLEEP_SEGMENT_TYPE = 1 - 1;

    private static final Integer DEFAULT_FIELD_BLOOD_GLUCOSE_SPECIMEN_SOURCE = 1;
    private static final Integer UPDATED_FIELD_BLOOD_GLUCOSE_SPECIMEN_SOURCE = 2;
    private static final Integer SMALLER_FIELD_BLOOD_GLUCOSE_SPECIMEN_SOURCE = 1 - 1;

    private static final Instant DEFAULT_START_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_START_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_END_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_END_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/sleep-segments";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private SleepSegmentRepository sleepSegmentRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSleepSegmentMockMvc;

    private SleepSegment sleepSegment;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SleepSegment createEntity(EntityManager em) {
        SleepSegment sleepSegment = new SleepSegment()
            .usuarioId(DEFAULT_USUARIO_ID)
            .empresaId(DEFAULT_EMPRESA_ID)
            .fieldSleepSegmentType(DEFAULT_FIELD_SLEEP_SEGMENT_TYPE)
            .fieldBloodGlucoseSpecimenSource(DEFAULT_FIELD_BLOOD_GLUCOSE_SPECIMEN_SOURCE)
            .startTime(DEFAULT_START_TIME)
            .endTime(DEFAULT_END_TIME);
        return sleepSegment;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SleepSegment createUpdatedEntity(EntityManager em) {
        SleepSegment sleepSegment = new SleepSegment()
            .usuarioId(UPDATED_USUARIO_ID)
            .empresaId(UPDATED_EMPRESA_ID)
            .fieldSleepSegmentType(UPDATED_FIELD_SLEEP_SEGMENT_TYPE)
            .fieldBloodGlucoseSpecimenSource(UPDATED_FIELD_BLOOD_GLUCOSE_SPECIMEN_SOURCE)
            .startTime(UPDATED_START_TIME)
            .endTime(UPDATED_END_TIME);
        return sleepSegment;
    }

    @BeforeEach
    public void initTest() {
        sleepSegment = createEntity(em);
    }

    @Test
    @Transactional
    void createSleepSegment() throws Exception {
        int databaseSizeBeforeCreate = sleepSegmentRepository.findAll().size();
        // Create the SleepSegment
        restSleepSegmentMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(sleepSegment)))
            .andExpect(status().isCreated());

        // Validate the SleepSegment in the database
        List<SleepSegment> sleepSegmentList = sleepSegmentRepository.findAll();
        assertThat(sleepSegmentList).hasSize(databaseSizeBeforeCreate + 1);
        SleepSegment testSleepSegment = sleepSegmentList.get(sleepSegmentList.size() - 1);
        assertThat(testSleepSegment.getUsuarioId()).isEqualTo(DEFAULT_USUARIO_ID);
        assertThat(testSleepSegment.getEmpresaId()).isEqualTo(DEFAULT_EMPRESA_ID);
        assertThat(testSleepSegment.getFieldSleepSegmentType()).isEqualTo(DEFAULT_FIELD_SLEEP_SEGMENT_TYPE);
        assertThat(testSleepSegment.getFieldBloodGlucoseSpecimenSource()).isEqualTo(DEFAULT_FIELD_BLOOD_GLUCOSE_SPECIMEN_SOURCE);
        assertThat(testSleepSegment.getStartTime()).isEqualTo(DEFAULT_START_TIME);
        assertThat(testSleepSegment.getEndTime()).isEqualTo(DEFAULT_END_TIME);
    }

    @Test
    @Transactional
    void createSleepSegmentWithExistingId() throws Exception {
        // Create the SleepSegment with an existing ID
        sleepSegmentRepository.saveAndFlush(sleepSegment);

        int databaseSizeBeforeCreate = sleepSegmentRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSleepSegmentMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(sleepSegment)))
            .andExpect(status().isBadRequest());

        // Validate the SleepSegment in the database
        List<SleepSegment> sleepSegmentList = sleepSegmentRepository.findAll();
        assertThat(sleepSegmentList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllSleepSegments() throws Exception {
        // Initialize the database
        sleepSegmentRepository.saveAndFlush(sleepSegment);

        // Get all the sleepSegmentList
        restSleepSegmentMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sleepSegment.getId().toString())))
            .andExpect(jsonPath("$.[*].usuarioId").value(hasItem(DEFAULT_USUARIO_ID)))
            .andExpect(jsonPath("$.[*].empresaId").value(hasItem(DEFAULT_EMPRESA_ID)))
            .andExpect(jsonPath("$.[*].fieldSleepSegmentType").value(hasItem(DEFAULT_FIELD_SLEEP_SEGMENT_TYPE)))
            .andExpect(jsonPath("$.[*].fieldBloodGlucoseSpecimenSource").value(hasItem(DEFAULT_FIELD_BLOOD_GLUCOSE_SPECIMEN_SOURCE)))
            .andExpect(jsonPath("$.[*].startTime").value(hasItem(DEFAULT_START_TIME.toString())))
            .andExpect(jsonPath("$.[*].endTime").value(hasItem(DEFAULT_END_TIME.toString())));
    }

    @Test
    @Transactional
    void getSleepSegment() throws Exception {
        // Initialize the database
        sleepSegmentRepository.saveAndFlush(sleepSegment);

        // Get the sleepSegment
        restSleepSegmentMockMvc
            .perform(get(ENTITY_API_URL_ID, sleepSegment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(sleepSegment.getId().toString()))
            .andExpect(jsonPath("$.usuarioId").value(DEFAULT_USUARIO_ID))
            .andExpect(jsonPath("$.empresaId").value(DEFAULT_EMPRESA_ID))
            .andExpect(jsonPath("$.fieldSleepSegmentType").value(DEFAULT_FIELD_SLEEP_SEGMENT_TYPE))
            .andExpect(jsonPath("$.fieldBloodGlucoseSpecimenSource").value(DEFAULT_FIELD_BLOOD_GLUCOSE_SPECIMEN_SOURCE))
            .andExpect(jsonPath("$.startTime").value(DEFAULT_START_TIME.toString()))
            .andExpect(jsonPath("$.endTime").value(DEFAULT_END_TIME.toString()));
    }

    @Test
    @Transactional
    void getSleepSegmentsByIdFiltering() throws Exception {
        // Initialize the database
        sleepSegmentRepository.saveAndFlush(sleepSegment);

        UUID id = sleepSegment.getId();

        defaultSleepSegmentShouldBeFound("id.equals=" + id);
        defaultSleepSegmentShouldNotBeFound("id.notEquals=" + id);
    }

    @Test
    @Transactional
    void getAllSleepSegmentsByUsuarioIdIsEqualToSomething() throws Exception {
        // Initialize the database
        sleepSegmentRepository.saveAndFlush(sleepSegment);

        // Get all the sleepSegmentList where usuarioId equals to DEFAULT_USUARIO_ID
        defaultSleepSegmentShouldBeFound("usuarioId.equals=" + DEFAULT_USUARIO_ID);

        // Get all the sleepSegmentList where usuarioId equals to UPDATED_USUARIO_ID
        defaultSleepSegmentShouldNotBeFound("usuarioId.equals=" + UPDATED_USUARIO_ID);
    }

    @Test
    @Transactional
    void getAllSleepSegmentsByUsuarioIdIsInShouldWork() throws Exception {
        // Initialize the database
        sleepSegmentRepository.saveAndFlush(sleepSegment);

        // Get all the sleepSegmentList where usuarioId in DEFAULT_USUARIO_ID or UPDATED_USUARIO_ID
        defaultSleepSegmentShouldBeFound("usuarioId.in=" + DEFAULT_USUARIO_ID + "," + UPDATED_USUARIO_ID);

        // Get all the sleepSegmentList where usuarioId equals to UPDATED_USUARIO_ID
        defaultSleepSegmentShouldNotBeFound("usuarioId.in=" + UPDATED_USUARIO_ID);
    }

    @Test
    @Transactional
    void getAllSleepSegmentsByUsuarioIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        sleepSegmentRepository.saveAndFlush(sleepSegment);

        // Get all the sleepSegmentList where usuarioId is not null
        defaultSleepSegmentShouldBeFound("usuarioId.specified=true");

        // Get all the sleepSegmentList where usuarioId is null
        defaultSleepSegmentShouldNotBeFound("usuarioId.specified=false");
    }

    @Test
    @Transactional
    void getAllSleepSegmentsByUsuarioIdContainsSomething() throws Exception {
        // Initialize the database
        sleepSegmentRepository.saveAndFlush(sleepSegment);

        // Get all the sleepSegmentList where usuarioId contains DEFAULT_USUARIO_ID
        defaultSleepSegmentShouldBeFound("usuarioId.contains=" + DEFAULT_USUARIO_ID);

        // Get all the sleepSegmentList where usuarioId contains UPDATED_USUARIO_ID
        defaultSleepSegmentShouldNotBeFound("usuarioId.contains=" + UPDATED_USUARIO_ID);
    }

    @Test
    @Transactional
    void getAllSleepSegmentsByUsuarioIdNotContainsSomething() throws Exception {
        // Initialize the database
        sleepSegmentRepository.saveAndFlush(sleepSegment);

        // Get all the sleepSegmentList where usuarioId does not contain DEFAULT_USUARIO_ID
        defaultSleepSegmentShouldNotBeFound("usuarioId.doesNotContain=" + DEFAULT_USUARIO_ID);

        // Get all the sleepSegmentList where usuarioId does not contain UPDATED_USUARIO_ID
        defaultSleepSegmentShouldBeFound("usuarioId.doesNotContain=" + UPDATED_USUARIO_ID);
    }

    @Test
    @Transactional
    void getAllSleepSegmentsByEmpresaIdIsEqualToSomething() throws Exception {
        // Initialize the database
        sleepSegmentRepository.saveAndFlush(sleepSegment);

        // Get all the sleepSegmentList where empresaId equals to DEFAULT_EMPRESA_ID
        defaultSleepSegmentShouldBeFound("empresaId.equals=" + DEFAULT_EMPRESA_ID);

        // Get all the sleepSegmentList where empresaId equals to UPDATED_EMPRESA_ID
        defaultSleepSegmentShouldNotBeFound("empresaId.equals=" + UPDATED_EMPRESA_ID);
    }

    @Test
    @Transactional
    void getAllSleepSegmentsByEmpresaIdIsInShouldWork() throws Exception {
        // Initialize the database
        sleepSegmentRepository.saveAndFlush(sleepSegment);

        // Get all the sleepSegmentList where empresaId in DEFAULT_EMPRESA_ID or UPDATED_EMPRESA_ID
        defaultSleepSegmentShouldBeFound("empresaId.in=" + DEFAULT_EMPRESA_ID + "," + UPDATED_EMPRESA_ID);

        // Get all the sleepSegmentList where empresaId equals to UPDATED_EMPRESA_ID
        defaultSleepSegmentShouldNotBeFound("empresaId.in=" + UPDATED_EMPRESA_ID);
    }

    @Test
    @Transactional
    void getAllSleepSegmentsByEmpresaIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        sleepSegmentRepository.saveAndFlush(sleepSegment);

        // Get all the sleepSegmentList where empresaId is not null
        defaultSleepSegmentShouldBeFound("empresaId.specified=true");

        // Get all the sleepSegmentList where empresaId is null
        defaultSleepSegmentShouldNotBeFound("empresaId.specified=false");
    }

    @Test
    @Transactional
    void getAllSleepSegmentsByEmpresaIdContainsSomething() throws Exception {
        // Initialize the database
        sleepSegmentRepository.saveAndFlush(sleepSegment);

        // Get all the sleepSegmentList where empresaId contains DEFAULT_EMPRESA_ID
        defaultSleepSegmentShouldBeFound("empresaId.contains=" + DEFAULT_EMPRESA_ID);

        // Get all the sleepSegmentList where empresaId contains UPDATED_EMPRESA_ID
        defaultSleepSegmentShouldNotBeFound("empresaId.contains=" + UPDATED_EMPRESA_ID);
    }

    @Test
    @Transactional
    void getAllSleepSegmentsByEmpresaIdNotContainsSomething() throws Exception {
        // Initialize the database
        sleepSegmentRepository.saveAndFlush(sleepSegment);

        // Get all the sleepSegmentList where empresaId does not contain DEFAULT_EMPRESA_ID
        defaultSleepSegmentShouldNotBeFound("empresaId.doesNotContain=" + DEFAULT_EMPRESA_ID);

        // Get all the sleepSegmentList where empresaId does not contain UPDATED_EMPRESA_ID
        defaultSleepSegmentShouldBeFound("empresaId.doesNotContain=" + UPDATED_EMPRESA_ID);
    }

    @Test
    @Transactional
    void getAllSleepSegmentsByFieldSleepSegmentTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        sleepSegmentRepository.saveAndFlush(sleepSegment);

        // Get all the sleepSegmentList where fieldSleepSegmentType equals to DEFAULT_FIELD_SLEEP_SEGMENT_TYPE
        defaultSleepSegmentShouldBeFound("fieldSleepSegmentType.equals=" + DEFAULT_FIELD_SLEEP_SEGMENT_TYPE);

        // Get all the sleepSegmentList where fieldSleepSegmentType equals to UPDATED_FIELD_SLEEP_SEGMENT_TYPE
        defaultSleepSegmentShouldNotBeFound("fieldSleepSegmentType.equals=" + UPDATED_FIELD_SLEEP_SEGMENT_TYPE);
    }

    @Test
    @Transactional
    void getAllSleepSegmentsByFieldSleepSegmentTypeIsInShouldWork() throws Exception {
        // Initialize the database
        sleepSegmentRepository.saveAndFlush(sleepSegment);

        // Get all the sleepSegmentList where fieldSleepSegmentType in DEFAULT_FIELD_SLEEP_SEGMENT_TYPE or UPDATED_FIELD_SLEEP_SEGMENT_TYPE
        defaultSleepSegmentShouldBeFound(
            "fieldSleepSegmentType.in=" + DEFAULT_FIELD_SLEEP_SEGMENT_TYPE + "," + UPDATED_FIELD_SLEEP_SEGMENT_TYPE
        );

        // Get all the sleepSegmentList where fieldSleepSegmentType equals to UPDATED_FIELD_SLEEP_SEGMENT_TYPE
        defaultSleepSegmentShouldNotBeFound("fieldSleepSegmentType.in=" + UPDATED_FIELD_SLEEP_SEGMENT_TYPE);
    }

    @Test
    @Transactional
    void getAllSleepSegmentsByFieldSleepSegmentTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        sleepSegmentRepository.saveAndFlush(sleepSegment);

        // Get all the sleepSegmentList where fieldSleepSegmentType is not null
        defaultSleepSegmentShouldBeFound("fieldSleepSegmentType.specified=true");

        // Get all the sleepSegmentList where fieldSleepSegmentType is null
        defaultSleepSegmentShouldNotBeFound("fieldSleepSegmentType.specified=false");
    }

    @Test
    @Transactional
    void getAllSleepSegmentsByFieldSleepSegmentTypeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        sleepSegmentRepository.saveAndFlush(sleepSegment);

        // Get all the sleepSegmentList where fieldSleepSegmentType is greater than or equal to DEFAULT_FIELD_SLEEP_SEGMENT_TYPE
        defaultSleepSegmentShouldBeFound("fieldSleepSegmentType.greaterThanOrEqual=" + DEFAULT_FIELD_SLEEP_SEGMENT_TYPE);

        // Get all the sleepSegmentList where fieldSleepSegmentType is greater than or equal to UPDATED_FIELD_SLEEP_SEGMENT_TYPE
        defaultSleepSegmentShouldNotBeFound("fieldSleepSegmentType.greaterThanOrEqual=" + UPDATED_FIELD_SLEEP_SEGMENT_TYPE);
    }

    @Test
    @Transactional
    void getAllSleepSegmentsByFieldSleepSegmentTypeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        sleepSegmentRepository.saveAndFlush(sleepSegment);

        // Get all the sleepSegmentList where fieldSleepSegmentType is less than or equal to DEFAULT_FIELD_SLEEP_SEGMENT_TYPE
        defaultSleepSegmentShouldBeFound("fieldSleepSegmentType.lessThanOrEqual=" + DEFAULT_FIELD_SLEEP_SEGMENT_TYPE);

        // Get all the sleepSegmentList where fieldSleepSegmentType is less than or equal to SMALLER_FIELD_SLEEP_SEGMENT_TYPE
        defaultSleepSegmentShouldNotBeFound("fieldSleepSegmentType.lessThanOrEqual=" + SMALLER_FIELD_SLEEP_SEGMENT_TYPE);
    }

    @Test
    @Transactional
    void getAllSleepSegmentsByFieldSleepSegmentTypeIsLessThanSomething() throws Exception {
        // Initialize the database
        sleepSegmentRepository.saveAndFlush(sleepSegment);

        // Get all the sleepSegmentList where fieldSleepSegmentType is less than DEFAULT_FIELD_SLEEP_SEGMENT_TYPE
        defaultSleepSegmentShouldNotBeFound("fieldSleepSegmentType.lessThan=" + DEFAULT_FIELD_SLEEP_SEGMENT_TYPE);

        // Get all the sleepSegmentList where fieldSleepSegmentType is less than UPDATED_FIELD_SLEEP_SEGMENT_TYPE
        defaultSleepSegmentShouldBeFound("fieldSleepSegmentType.lessThan=" + UPDATED_FIELD_SLEEP_SEGMENT_TYPE);
    }

    @Test
    @Transactional
    void getAllSleepSegmentsByFieldSleepSegmentTypeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        sleepSegmentRepository.saveAndFlush(sleepSegment);

        // Get all the sleepSegmentList where fieldSleepSegmentType is greater than DEFAULT_FIELD_SLEEP_SEGMENT_TYPE
        defaultSleepSegmentShouldNotBeFound("fieldSleepSegmentType.greaterThan=" + DEFAULT_FIELD_SLEEP_SEGMENT_TYPE);

        // Get all the sleepSegmentList where fieldSleepSegmentType is greater than SMALLER_FIELD_SLEEP_SEGMENT_TYPE
        defaultSleepSegmentShouldBeFound("fieldSleepSegmentType.greaterThan=" + SMALLER_FIELD_SLEEP_SEGMENT_TYPE);
    }

    @Test
    @Transactional
    void getAllSleepSegmentsByFieldBloodGlucoseSpecimenSourceIsEqualToSomething() throws Exception {
        // Initialize the database
        sleepSegmentRepository.saveAndFlush(sleepSegment);

        // Get all the sleepSegmentList where fieldBloodGlucoseSpecimenSource equals to DEFAULT_FIELD_BLOOD_GLUCOSE_SPECIMEN_SOURCE
        defaultSleepSegmentShouldBeFound("fieldBloodGlucoseSpecimenSource.equals=" + DEFAULT_FIELD_BLOOD_GLUCOSE_SPECIMEN_SOURCE);

        // Get all the sleepSegmentList where fieldBloodGlucoseSpecimenSource equals to UPDATED_FIELD_BLOOD_GLUCOSE_SPECIMEN_SOURCE
        defaultSleepSegmentShouldNotBeFound("fieldBloodGlucoseSpecimenSource.equals=" + UPDATED_FIELD_BLOOD_GLUCOSE_SPECIMEN_SOURCE);
    }

    @Test
    @Transactional
    void getAllSleepSegmentsByFieldBloodGlucoseSpecimenSourceIsInShouldWork() throws Exception {
        // Initialize the database
        sleepSegmentRepository.saveAndFlush(sleepSegment);

        // Get all the sleepSegmentList where fieldBloodGlucoseSpecimenSource in DEFAULT_FIELD_BLOOD_GLUCOSE_SPECIMEN_SOURCE or UPDATED_FIELD_BLOOD_GLUCOSE_SPECIMEN_SOURCE
        defaultSleepSegmentShouldBeFound(
            "fieldBloodGlucoseSpecimenSource.in=" +
            DEFAULT_FIELD_BLOOD_GLUCOSE_SPECIMEN_SOURCE +
            "," +
            UPDATED_FIELD_BLOOD_GLUCOSE_SPECIMEN_SOURCE
        );

        // Get all the sleepSegmentList where fieldBloodGlucoseSpecimenSource equals to UPDATED_FIELD_BLOOD_GLUCOSE_SPECIMEN_SOURCE
        defaultSleepSegmentShouldNotBeFound("fieldBloodGlucoseSpecimenSource.in=" + UPDATED_FIELD_BLOOD_GLUCOSE_SPECIMEN_SOURCE);
    }

    @Test
    @Transactional
    void getAllSleepSegmentsByFieldBloodGlucoseSpecimenSourceIsNullOrNotNull() throws Exception {
        // Initialize the database
        sleepSegmentRepository.saveAndFlush(sleepSegment);

        // Get all the sleepSegmentList where fieldBloodGlucoseSpecimenSource is not null
        defaultSleepSegmentShouldBeFound("fieldBloodGlucoseSpecimenSource.specified=true");

        // Get all the sleepSegmentList where fieldBloodGlucoseSpecimenSource is null
        defaultSleepSegmentShouldNotBeFound("fieldBloodGlucoseSpecimenSource.specified=false");
    }

    @Test
    @Transactional
    void getAllSleepSegmentsByFieldBloodGlucoseSpecimenSourceIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        sleepSegmentRepository.saveAndFlush(sleepSegment);

        // Get all the sleepSegmentList where fieldBloodGlucoseSpecimenSource is greater than or equal to DEFAULT_FIELD_BLOOD_GLUCOSE_SPECIMEN_SOURCE
        defaultSleepSegmentShouldBeFound(
            "fieldBloodGlucoseSpecimenSource.greaterThanOrEqual=" + DEFAULT_FIELD_BLOOD_GLUCOSE_SPECIMEN_SOURCE
        );

        // Get all the sleepSegmentList where fieldBloodGlucoseSpecimenSource is greater than or equal to UPDATED_FIELD_BLOOD_GLUCOSE_SPECIMEN_SOURCE
        defaultSleepSegmentShouldNotBeFound(
            "fieldBloodGlucoseSpecimenSource.greaterThanOrEqual=" + UPDATED_FIELD_BLOOD_GLUCOSE_SPECIMEN_SOURCE
        );
    }

    @Test
    @Transactional
    void getAllSleepSegmentsByFieldBloodGlucoseSpecimenSourceIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        sleepSegmentRepository.saveAndFlush(sleepSegment);

        // Get all the sleepSegmentList where fieldBloodGlucoseSpecimenSource is less than or equal to DEFAULT_FIELD_BLOOD_GLUCOSE_SPECIMEN_SOURCE
        defaultSleepSegmentShouldBeFound("fieldBloodGlucoseSpecimenSource.lessThanOrEqual=" + DEFAULT_FIELD_BLOOD_GLUCOSE_SPECIMEN_SOURCE);

        // Get all the sleepSegmentList where fieldBloodGlucoseSpecimenSource is less than or equal to SMALLER_FIELD_BLOOD_GLUCOSE_SPECIMEN_SOURCE
        defaultSleepSegmentShouldNotBeFound(
            "fieldBloodGlucoseSpecimenSource.lessThanOrEqual=" + SMALLER_FIELD_BLOOD_GLUCOSE_SPECIMEN_SOURCE
        );
    }

    @Test
    @Transactional
    void getAllSleepSegmentsByFieldBloodGlucoseSpecimenSourceIsLessThanSomething() throws Exception {
        // Initialize the database
        sleepSegmentRepository.saveAndFlush(sleepSegment);

        // Get all the sleepSegmentList where fieldBloodGlucoseSpecimenSource is less than DEFAULT_FIELD_BLOOD_GLUCOSE_SPECIMEN_SOURCE
        defaultSleepSegmentShouldNotBeFound("fieldBloodGlucoseSpecimenSource.lessThan=" + DEFAULT_FIELD_BLOOD_GLUCOSE_SPECIMEN_SOURCE);

        // Get all the sleepSegmentList where fieldBloodGlucoseSpecimenSource is less than UPDATED_FIELD_BLOOD_GLUCOSE_SPECIMEN_SOURCE
        defaultSleepSegmentShouldBeFound("fieldBloodGlucoseSpecimenSource.lessThan=" + UPDATED_FIELD_BLOOD_GLUCOSE_SPECIMEN_SOURCE);
    }

    @Test
    @Transactional
    void getAllSleepSegmentsByFieldBloodGlucoseSpecimenSourceIsGreaterThanSomething() throws Exception {
        // Initialize the database
        sleepSegmentRepository.saveAndFlush(sleepSegment);

        // Get all the sleepSegmentList where fieldBloodGlucoseSpecimenSource is greater than DEFAULT_FIELD_BLOOD_GLUCOSE_SPECIMEN_SOURCE
        defaultSleepSegmentShouldNotBeFound("fieldBloodGlucoseSpecimenSource.greaterThan=" + DEFAULT_FIELD_BLOOD_GLUCOSE_SPECIMEN_SOURCE);

        // Get all the sleepSegmentList where fieldBloodGlucoseSpecimenSource is greater than SMALLER_FIELD_BLOOD_GLUCOSE_SPECIMEN_SOURCE
        defaultSleepSegmentShouldBeFound("fieldBloodGlucoseSpecimenSource.greaterThan=" + SMALLER_FIELD_BLOOD_GLUCOSE_SPECIMEN_SOURCE);
    }

    @Test
    @Transactional
    void getAllSleepSegmentsByStartTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        sleepSegmentRepository.saveAndFlush(sleepSegment);

        // Get all the sleepSegmentList where startTime equals to DEFAULT_START_TIME
        defaultSleepSegmentShouldBeFound("startTime.equals=" + DEFAULT_START_TIME);

        // Get all the sleepSegmentList where startTime equals to UPDATED_START_TIME
        defaultSleepSegmentShouldNotBeFound("startTime.equals=" + UPDATED_START_TIME);
    }

    @Test
    @Transactional
    void getAllSleepSegmentsByStartTimeIsInShouldWork() throws Exception {
        // Initialize the database
        sleepSegmentRepository.saveAndFlush(sleepSegment);

        // Get all the sleepSegmentList where startTime in DEFAULT_START_TIME or UPDATED_START_TIME
        defaultSleepSegmentShouldBeFound("startTime.in=" + DEFAULT_START_TIME + "," + UPDATED_START_TIME);

        // Get all the sleepSegmentList where startTime equals to UPDATED_START_TIME
        defaultSleepSegmentShouldNotBeFound("startTime.in=" + UPDATED_START_TIME);
    }

    @Test
    @Transactional
    void getAllSleepSegmentsByStartTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        sleepSegmentRepository.saveAndFlush(sleepSegment);

        // Get all the sleepSegmentList where startTime is not null
        defaultSleepSegmentShouldBeFound("startTime.specified=true");

        // Get all the sleepSegmentList where startTime is null
        defaultSleepSegmentShouldNotBeFound("startTime.specified=false");
    }

    @Test
    @Transactional
    void getAllSleepSegmentsByEndTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        sleepSegmentRepository.saveAndFlush(sleepSegment);

        // Get all the sleepSegmentList where endTime equals to DEFAULT_END_TIME
        defaultSleepSegmentShouldBeFound("endTime.equals=" + DEFAULT_END_TIME);

        // Get all the sleepSegmentList where endTime equals to UPDATED_END_TIME
        defaultSleepSegmentShouldNotBeFound("endTime.equals=" + UPDATED_END_TIME);
    }

    @Test
    @Transactional
    void getAllSleepSegmentsByEndTimeIsInShouldWork() throws Exception {
        // Initialize the database
        sleepSegmentRepository.saveAndFlush(sleepSegment);

        // Get all the sleepSegmentList where endTime in DEFAULT_END_TIME or UPDATED_END_TIME
        defaultSleepSegmentShouldBeFound("endTime.in=" + DEFAULT_END_TIME + "," + UPDATED_END_TIME);

        // Get all the sleepSegmentList where endTime equals to UPDATED_END_TIME
        defaultSleepSegmentShouldNotBeFound("endTime.in=" + UPDATED_END_TIME);
    }

    @Test
    @Transactional
    void getAllSleepSegmentsByEndTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        sleepSegmentRepository.saveAndFlush(sleepSegment);

        // Get all the sleepSegmentList where endTime is not null
        defaultSleepSegmentShouldBeFound("endTime.specified=true");

        // Get all the sleepSegmentList where endTime is null
        defaultSleepSegmentShouldNotBeFound("endTime.specified=false");
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultSleepSegmentShouldBeFound(String filter) throws Exception {
        restSleepSegmentMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sleepSegment.getId().toString())))
            .andExpect(jsonPath("$.[*].usuarioId").value(hasItem(DEFAULT_USUARIO_ID)))
            .andExpect(jsonPath("$.[*].empresaId").value(hasItem(DEFAULT_EMPRESA_ID)))
            .andExpect(jsonPath("$.[*].fieldSleepSegmentType").value(hasItem(DEFAULT_FIELD_SLEEP_SEGMENT_TYPE)))
            .andExpect(jsonPath("$.[*].fieldBloodGlucoseSpecimenSource").value(hasItem(DEFAULT_FIELD_BLOOD_GLUCOSE_SPECIMEN_SOURCE)))
            .andExpect(jsonPath("$.[*].startTime").value(hasItem(DEFAULT_START_TIME.toString())))
            .andExpect(jsonPath("$.[*].endTime").value(hasItem(DEFAULT_END_TIME.toString())));

        // Check, that the count call also returns 1
        restSleepSegmentMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultSleepSegmentShouldNotBeFound(String filter) throws Exception {
        restSleepSegmentMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restSleepSegmentMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingSleepSegment() throws Exception {
        // Get the sleepSegment
        restSleepSegmentMockMvc.perform(get(ENTITY_API_URL_ID, UUID.randomUUID().toString())).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingSleepSegment() throws Exception {
        // Initialize the database
        sleepSegmentRepository.saveAndFlush(sleepSegment);

        int databaseSizeBeforeUpdate = sleepSegmentRepository.findAll().size();

        // Update the sleepSegment
        SleepSegment updatedSleepSegment = sleepSegmentRepository.findById(sleepSegment.getId()).get();
        // Disconnect from session so that the updates on updatedSleepSegment are not directly saved in db
        em.detach(updatedSleepSegment);
        updatedSleepSegment
            .usuarioId(UPDATED_USUARIO_ID)
            .empresaId(UPDATED_EMPRESA_ID)
            .fieldSleepSegmentType(UPDATED_FIELD_SLEEP_SEGMENT_TYPE)
            .fieldBloodGlucoseSpecimenSource(UPDATED_FIELD_BLOOD_GLUCOSE_SPECIMEN_SOURCE)
            .startTime(UPDATED_START_TIME)
            .endTime(UPDATED_END_TIME);

        restSleepSegmentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedSleepSegment.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedSleepSegment))
            )
            .andExpect(status().isOk());

        // Validate the SleepSegment in the database
        List<SleepSegment> sleepSegmentList = sleepSegmentRepository.findAll();
        assertThat(sleepSegmentList).hasSize(databaseSizeBeforeUpdate);
        SleepSegment testSleepSegment = sleepSegmentList.get(sleepSegmentList.size() - 1);
        assertThat(testSleepSegment.getUsuarioId()).isEqualTo(UPDATED_USUARIO_ID);
        assertThat(testSleepSegment.getEmpresaId()).isEqualTo(UPDATED_EMPRESA_ID);
        assertThat(testSleepSegment.getFieldSleepSegmentType()).isEqualTo(UPDATED_FIELD_SLEEP_SEGMENT_TYPE);
        assertThat(testSleepSegment.getFieldBloodGlucoseSpecimenSource()).isEqualTo(UPDATED_FIELD_BLOOD_GLUCOSE_SPECIMEN_SOURCE);
        assertThat(testSleepSegment.getStartTime()).isEqualTo(UPDATED_START_TIME);
        assertThat(testSleepSegment.getEndTime()).isEqualTo(UPDATED_END_TIME);
    }

    @Test
    @Transactional
    void putNonExistingSleepSegment() throws Exception {
        int databaseSizeBeforeUpdate = sleepSegmentRepository.findAll().size();
        sleepSegment.setId(UUID.randomUUID());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSleepSegmentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, sleepSegment.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(sleepSegment))
            )
            .andExpect(status().isBadRequest());

        // Validate the SleepSegment in the database
        List<SleepSegment> sleepSegmentList = sleepSegmentRepository.findAll();
        assertThat(sleepSegmentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSleepSegment() throws Exception {
        int databaseSizeBeforeUpdate = sleepSegmentRepository.findAll().size();
        sleepSegment.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSleepSegmentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(sleepSegment))
            )
            .andExpect(status().isBadRequest());

        // Validate the SleepSegment in the database
        List<SleepSegment> sleepSegmentList = sleepSegmentRepository.findAll();
        assertThat(sleepSegmentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSleepSegment() throws Exception {
        int databaseSizeBeforeUpdate = sleepSegmentRepository.findAll().size();
        sleepSegment.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSleepSegmentMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(sleepSegment)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the SleepSegment in the database
        List<SleepSegment> sleepSegmentList = sleepSegmentRepository.findAll();
        assertThat(sleepSegmentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSleepSegmentWithPatch() throws Exception {
        // Initialize the database
        sleepSegmentRepository.saveAndFlush(sleepSegment);

        int databaseSizeBeforeUpdate = sleepSegmentRepository.findAll().size();

        // Update the sleepSegment using partial update
        SleepSegment partialUpdatedSleepSegment = new SleepSegment();
        partialUpdatedSleepSegment.setId(sleepSegment.getId());

        partialUpdatedSleepSegment
            .usuarioId(UPDATED_USUARIO_ID)
            .fieldSleepSegmentType(UPDATED_FIELD_SLEEP_SEGMENT_TYPE)
            .fieldBloodGlucoseSpecimenSource(UPDATED_FIELD_BLOOD_GLUCOSE_SPECIMEN_SOURCE)
            .startTime(UPDATED_START_TIME);

        restSleepSegmentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSleepSegment.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSleepSegment))
            )
            .andExpect(status().isOk());

        // Validate the SleepSegment in the database
        List<SleepSegment> sleepSegmentList = sleepSegmentRepository.findAll();
        assertThat(sleepSegmentList).hasSize(databaseSizeBeforeUpdate);
        SleepSegment testSleepSegment = sleepSegmentList.get(sleepSegmentList.size() - 1);
        assertThat(testSleepSegment.getUsuarioId()).isEqualTo(UPDATED_USUARIO_ID);
        assertThat(testSleepSegment.getEmpresaId()).isEqualTo(DEFAULT_EMPRESA_ID);
        assertThat(testSleepSegment.getFieldSleepSegmentType()).isEqualTo(UPDATED_FIELD_SLEEP_SEGMENT_TYPE);
        assertThat(testSleepSegment.getFieldBloodGlucoseSpecimenSource()).isEqualTo(UPDATED_FIELD_BLOOD_GLUCOSE_SPECIMEN_SOURCE);
        assertThat(testSleepSegment.getStartTime()).isEqualTo(UPDATED_START_TIME);
        assertThat(testSleepSegment.getEndTime()).isEqualTo(DEFAULT_END_TIME);
    }

    @Test
    @Transactional
    void fullUpdateSleepSegmentWithPatch() throws Exception {
        // Initialize the database
        sleepSegmentRepository.saveAndFlush(sleepSegment);

        int databaseSizeBeforeUpdate = sleepSegmentRepository.findAll().size();

        // Update the sleepSegment using partial update
        SleepSegment partialUpdatedSleepSegment = new SleepSegment();
        partialUpdatedSleepSegment.setId(sleepSegment.getId());

        partialUpdatedSleepSegment
            .usuarioId(UPDATED_USUARIO_ID)
            .empresaId(UPDATED_EMPRESA_ID)
            .fieldSleepSegmentType(UPDATED_FIELD_SLEEP_SEGMENT_TYPE)
            .fieldBloodGlucoseSpecimenSource(UPDATED_FIELD_BLOOD_GLUCOSE_SPECIMEN_SOURCE)
            .startTime(UPDATED_START_TIME)
            .endTime(UPDATED_END_TIME);

        restSleepSegmentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSleepSegment.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSleepSegment))
            )
            .andExpect(status().isOk());

        // Validate the SleepSegment in the database
        List<SleepSegment> sleepSegmentList = sleepSegmentRepository.findAll();
        assertThat(sleepSegmentList).hasSize(databaseSizeBeforeUpdate);
        SleepSegment testSleepSegment = sleepSegmentList.get(sleepSegmentList.size() - 1);
        assertThat(testSleepSegment.getUsuarioId()).isEqualTo(UPDATED_USUARIO_ID);
        assertThat(testSleepSegment.getEmpresaId()).isEqualTo(UPDATED_EMPRESA_ID);
        assertThat(testSleepSegment.getFieldSleepSegmentType()).isEqualTo(UPDATED_FIELD_SLEEP_SEGMENT_TYPE);
        assertThat(testSleepSegment.getFieldBloodGlucoseSpecimenSource()).isEqualTo(UPDATED_FIELD_BLOOD_GLUCOSE_SPECIMEN_SOURCE);
        assertThat(testSleepSegment.getStartTime()).isEqualTo(UPDATED_START_TIME);
        assertThat(testSleepSegment.getEndTime()).isEqualTo(UPDATED_END_TIME);
    }

    @Test
    @Transactional
    void patchNonExistingSleepSegment() throws Exception {
        int databaseSizeBeforeUpdate = sleepSegmentRepository.findAll().size();
        sleepSegment.setId(UUID.randomUUID());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSleepSegmentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, sleepSegment.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(sleepSegment))
            )
            .andExpect(status().isBadRequest());

        // Validate the SleepSegment in the database
        List<SleepSegment> sleepSegmentList = sleepSegmentRepository.findAll();
        assertThat(sleepSegmentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSleepSegment() throws Exception {
        int databaseSizeBeforeUpdate = sleepSegmentRepository.findAll().size();
        sleepSegment.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSleepSegmentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(sleepSegment))
            )
            .andExpect(status().isBadRequest());

        // Validate the SleepSegment in the database
        List<SleepSegment> sleepSegmentList = sleepSegmentRepository.findAll();
        assertThat(sleepSegmentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSleepSegment() throws Exception {
        int databaseSizeBeforeUpdate = sleepSegmentRepository.findAll().size();
        sleepSegment.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSleepSegmentMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(sleepSegment))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the SleepSegment in the database
        List<SleepSegment> sleepSegmentList = sleepSegmentRepository.findAll();
        assertThat(sleepSegmentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSleepSegment() throws Exception {
        // Initialize the database
        sleepSegmentRepository.saveAndFlush(sleepSegment);

        int databaseSizeBeforeDelete = sleepSegmentRepository.findAll().size();

        // Delete the sleepSegment
        restSleepSegmentMockMvc
            .perform(delete(ENTITY_API_URL_ID, sleepSegment.getId().toString()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SleepSegment> sleepSegmentList = sleepSegmentRepository.findAll();
        assertThat(sleepSegmentList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
