package com.be4tech.b4carecollect.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.be4tech.b4carecollect.IntegrationTest;
import com.be4tech.b4carecollect.domain.LocationSample;
import com.be4tech.b4carecollect.repository.LocationSampleRepository;
import com.be4tech.b4carecollect.service.criteria.LocationSampleCriteria;
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
 * Integration tests for the {@link LocationSampleResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class LocationSampleResourceIT {

    private static final String DEFAULT_USUARIO_ID = "AAAAAAAAAA";
    private static final String UPDATED_USUARIO_ID = "BBBBBBBBBB";

    private static final String DEFAULT_EMPRESA_ID = "AAAAAAAAAA";
    private static final String UPDATED_EMPRESA_ID = "BBBBBBBBBB";

    private static final Float DEFAULT_LATITUD_MIN = 1F;
    private static final Float UPDATED_LATITUD_MIN = 2F;
    private static final Float SMALLER_LATITUD_MIN = 1F - 1F;

    private static final Float DEFAULT_LONGITUD_MIN = 1F;
    private static final Float UPDATED_LONGITUD_MIN = 2F;
    private static final Float SMALLER_LONGITUD_MIN = 1F - 1F;

    private static final Float DEFAULT_LATITUD_MAX = 1F;
    private static final Float UPDATED_LATITUD_MAX = 2F;
    private static final Float SMALLER_LATITUD_MAX = 1F - 1F;

    private static final Float DEFAULT_LONGITUD_MAX = 1F;
    private static final Float UPDATED_LONGITUD_MAX = 2F;
    private static final Float SMALLER_LONGITUD_MAX = 1F - 1F;

    private static final Float DEFAULT_ACCURACY = 1F;
    private static final Float UPDATED_ACCURACY = 2F;
    private static final Float SMALLER_ACCURACY = 1F - 1F;

    private static final Float DEFAULT_ALTITUD = 1F;
    private static final Float UPDATED_ALTITUD = 2F;
    private static final Float SMALLER_ALTITUD = 1F - 1F;

    private static final Instant DEFAULT_START_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_START_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_END_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_END_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/location-samples";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private LocationSampleRepository locationSampleRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restLocationSampleMockMvc;

    private LocationSample locationSample;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LocationSample createEntity(EntityManager em) {
        LocationSample locationSample = new LocationSample()
            .usuarioId(DEFAULT_USUARIO_ID)
            .empresaId(DEFAULT_EMPRESA_ID)
            .latitudMin(DEFAULT_LATITUD_MIN)
            .longitudMin(DEFAULT_LONGITUD_MIN)
            .latitudMax(DEFAULT_LATITUD_MAX)
            .longitudMax(DEFAULT_LONGITUD_MAX)
            .accuracy(DEFAULT_ACCURACY)
            .altitud(DEFAULT_ALTITUD)
            .startTime(DEFAULT_START_TIME)
            .endTime(DEFAULT_END_TIME);
        return locationSample;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LocationSample createUpdatedEntity(EntityManager em) {
        LocationSample locationSample = new LocationSample()
            .usuarioId(UPDATED_USUARIO_ID)
            .empresaId(UPDATED_EMPRESA_ID)
            .latitudMin(UPDATED_LATITUD_MIN)
            .longitudMin(UPDATED_LONGITUD_MIN)
            .latitudMax(UPDATED_LATITUD_MAX)
            .longitudMax(UPDATED_LONGITUD_MAX)
            .accuracy(UPDATED_ACCURACY)
            .altitud(UPDATED_ALTITUD)
            .startTime(UPDATED_START_TIME)
            .endTime(UPDATED_END_TIME);
        return locationSample;
    }

    @BeforeEach
    public void initTest() {
        locationSample = createEntity(em);
    }

    @Test
    @Transactional
    void createLocationSample() throws Exception {
        int databaseSizeBeforeCreate = locationSampleRepository.findAll().size();
        // Create the LocationSample
        restLocationSampleMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(locationSample))
            )
            .andExpect(status().isCreated());

        // Validate the LocationSample in the database
        List<LocationSample> locationSampleList = locationSampleRepository.findAll();
        assertThat(locationSampleList).hasSize(databaseSizeBeforeCreate + 1);
        LocationSample testLocationSample = locationSampleList.get(locationSampleList.size() - 1);
        assertThat(testLocationSample.getUsuarioId()).isEqualTo(DEFAULT_USUARIO_ID);
        assertThat(testLocationSample.getEmpresaId()).isEqualTo(DEFAULT_EMPRESA_ID);
        assertThat(testLocationSample.getLatitudMin()).isEqualTo(DEFAULT_LATITUD_MIN);
        assertThat(testLocationSample.getLongitudMin()).isEqualTo(DEFAULT_LONGITUD_MIN);
        assertThat(testLocationSample.getLatitudMax()).isEqualTo(DEFAULT_LATITUD_MAX);
        assertThat(testLocationSample.getLongitudMax()).isEqualTo(DEFAULT_LONGITUD_MAX);
        assertThat(testLocationSample.getAccuracy()).isEqualTo(DEFAULT_ACCURACY);
        assertThat(testLocationSample.getAltitud()).isEqualTo(DEFAULT_ALTITUD);
        assertThat(testLocationSample.getStartTime()).isEqualTo(DEFAULT_START_TIME);
        assertThat(testLocationSample.getEndTime()).isEqualTo(DEFAULT_END_TIME);
    }

    @Test
    @Transactional
    void createLocationSampleWithExistingId() throws Exception {
        // Create the LocationSample with an existing ID
        locationSampleRepository.saveAndFlush(locationSample);

        int databaseSizeBeforeCreate = locationSampleRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restLocationSampleMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(locationSample))
            )
            .andExpect(status().isBadRequest());

        // Validate the LocationSample in the database
        List<LocationSample> locationSampleList = locationSampleRepository.findAll();
        assertThat(locationSampleList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllLocationSamples() throws Exception {
        // Initialize the database
        locationSampleRepository.saveAndFlush(locationSample);

        // Get all the locationSampleList
        restLocationSampleMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(locationSample.getId().toString())))
            .andExpect(jsonPath("$.[*].usuarioId").value(hasItem(DEFAULT_USUARIO_ID)))
            .andExpect(jsonPath("$.[*].empresaId").value(hasItem(DEFAULT_EMPRESA_ID)))
            .andExpect(jsonPath("$.[*].latitudMin").value(hasItem(DEFAULT_LATITUD_MIN.doubleValue())))
            .andExpect(jsonPath("$.[*].longitudMin").value(hasItem(DEFAULT_LONGITUD_MIN.doubleValue())))
            .andExpect(jsonPath("$.[*].latitudMax").value(hasItem(DEFAULT_LATITUD_MAX.doubleValue())))
            .andExpect(jsonPath("$.[*].longitudMax").value(hasItem(DEFAULT_LONGITUD_MAX.doubleValue())))
            .andExpect(jsonPath("$.[*].accuracy").value(hasItem(DEFAULT_ACCURACY.doubleValue())))
            .andExpect(jsonPath("$.[*].altitud").value(hasItem(DEFAULT_ALTITUD.doubleValue())))
            .andExpect(jsonPath("$.[*].startTime").value(hasItem(DEFAULT_START_TIME.toString())))
            .andExpect(jsonPath("$.[*].endTime").value(hasItem(DEFAULT_END_TIME.toString())));
    }

    @Test
    @Transactional
    void getLocationSample() throws Exception {
        // Initialize the database
        locationSampleRepository.saveAndFlush(locationSample);

        // Get the locationSample
        restLocationSampleMockMvc
            .perform(get(ENTITY_API_URL_ID, locationSample.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(locationSample.getId().toString()))
            .andExpect(jsonPath("$.usuarioId").value(DEFAULT_USUARIO_ID))
            .andExpect(jsonPath("$.empresaId").value(DEFAULT_EMPRESA_ID))
            .andExpect(jsonPath("$.latitudMin").value(DEFAULT_LATITUD_MIN.doubleValue()))
            .andExpect(jsonPath("$.longitudMin").value(DEFAULT_LONGITUD_MIN.doubleValue()))
            .andExpect(jsonPath("$.latitudMax").value(DEFAULT_LATITUD_MAX.doubleValue()))
            .andExpect(jsonPath("$.longitudMax").value(DEFAULT_LONGITUD_MAX.doubleValue()))
            .andExpect(jsonPath("$.accuracy").value(DEFAULT_ACCURACY.doubleValue()))
            .andExpect(jsonPath("$.altitud").value(DEFAULT_ALTITUD.doubleValue()))
            .andExpect(jsonPath("$.startTime").value(DEFAULT_START_TIME.toString()))
            .andExpect(jsonPath("$.endTime").value(DEFAULT_END_TIME.toString()));
    }

    @Test
    @Transactional
    void getLocationSamplesByIdFiltering() throws Exception {
        // Initialize the database
        locationSampleRepository.saveAndFlush(locationSample);

        UUID id = locationSample.getId();

        defaultLocationSampleShouldBeFound("id.equals=" + id);
        defaultLocationSampleShouldNotBeFound("id.notEquals=" + id);
    }

    @Test
    @Transactional
    void getAllLocationSamplesByUsuarioIdIsEqualToSomething() throws Exception {
        // Initialize the database
        locationSampleRepository.saveAndFlush(locationSample);

        // Get all the locationSampleList where usuarioId equals to DEFAULT_USUARIO_ID
        defaultLocationSampleShouldBeFound("usuarioId.equals=" + DEFAULT_USUARIO_ID);

        // Get all the locationSampleList where usuarioId equals to UPDATED_USUARIO_ID
        defaultLocationSampleShouldNotBeFound("usuarioId.equals=" + UPDATED_USUARIO_ID);
    }

    @Test
    @Transactional
    void getAllLocationSamplesByUsuarioIdIsInShouldWork() throws Exception {
        // Initialize the database
        locationSampleRepository.saveAndFlush(locationSample);

        // Get all the locationSampleList where usuarioId in DEFAULT_USUARIO_ID or UPDATED_USUARIO_ID
        defaultLocationSampleShouldBeFound("usuarioId.in=" + DEFAULT_USUARIO_ID + "," + UPDATED_USUARIO_ID);

        // Get all the locationSampleList where usuarioId equals to UPDATED_USUARIO_ID
        defaultLocationSampleShouldNotBeFound("usuarioId.in=" + UPDATED_USUARIO_ID);
    }

    @Test
    @Transactional
    void getAllLocationSamplesByUsuarioIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        locationSampleRepository.saveAndFlush(locationSample);

        // Get all the locationSampleList where usuarioId is not null
        defaultLocationSampleShouldBeFound("usuarioId.specified=true");

        // Get all the locationSampleList where usuarioId is null
        defaultLocationSampleShouldNotBeFound("usuarioId.specified=false");
    }

    @Test
    @Transactional
    void getAllLocationSamplesByUsuarioIdContainsSomething() throws Exception {
        // Initialize the database
        locationSampleRepository.saveAndFlush(locationSample);

        // Get all the locationSampleList where usuarioId contains DEFAULT_USUARIO_ID
        defaultLocationSampleShouldBeFound("usuarioId.contains=" + DEFAULT_USUARIO_ID);

        // Get all the locationSampleList where usuarioId contains UPDATED_USUARIO_ID
        defaultLocationSampleShouldNotBeFound("usuarioId.contains=" + UPDATED_USUARIO_ID);
    }

    @Test
    @Transactional
    void getAllLocationSamplesByUsuarioIdNotContainsSomething() throws Exception {
        // Initialize the database
        locationSampleRepository.saveAndFlush(locationSample);

        // Get all the locationSampleList where usuarioId does not contain DEFAULT_USUARIO_ID
        defaultLocationSampleShouldNotBeFound("usuarioId.doesNotContain=" + DEFAULT_USUARIO_ID);

        // Get all the locationSampleList where usuarioId does not contain UPDATED_USUARIO_ID
        defaultLocationSampleShouldBeFound("usuarioId.doesNotContain=" + UPDATED_USUARIO_ID);
    }

    @Test
    @Transactional
    void getAllLocationSamplesByEmpresaIdIsEqualToSomething() throws Exception {
        // Initialize the database
        locationSampleRepository.saveAndFlush(locationSample);

        // Get all the locationSampleList where empresaId equals to DEFAULT_EMPRESA_ID
        defaultLocationSampleShouldBeFound("empresaId.equals=" + DEFAULT_EMPRESA_ID);

        // Get all the locationSampleList where empresaId equals to UPDATED_EMPRESA_ID
        defaultLocationSampleShouldNotBeFound("empresaId.equals=" + UPDATED_EMPRESA_ID);
    }

    @Test
    @Transactional
    void getAllLocationSamplesByEmpresaIdIsInShouldWork() throws Exception {
        // Initialize the database
        locationSampleRepository.saveAndFlush(locationSample);

        // Get all the locationSampleList where empresaId in DEFAULT_EMPRESA_ID or UPDATED_EMPRESA_ID
        defaultLocationSampleShouldBeFound("empresaId.in=" + DEFAULT_EMPRESA_ID + "," + UPDATED_EMPRESA_ID);

        // Get all the locationSampleList where empresaId equals to UPDATED_EMPRESA_ID
        defaultLocationSampleShouldNotBeFound("empresaId.in=" + UPDATED_EMPRESA_ID);
    }

    @Test
    @Transactional
    void getAllLocationSamplesByEmpresaIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        locationSampleRepository.saveAndFlush(locationSample);

        // Get all the locationSampleList where empresaId is not null
        defaultLocationSampleShouldBeFound("empresaId.specified=true");

        // Get all the locationSampleList where empresaId is null
        defaultLocationSampleShouldNotBeFound("empresaId.specified=false");
    }

    @Test
    @Transactional
    void getAllLocationSamplesByEmpresaIdContainsSomething() throws Exception {
        // Initialize the database
        locationSampleRepository.saveAndFlush(locationSample);

        // Get all the locationSampleList where empresaId contains DEFAULT_EMPRESA_ID
        defaultLocationSampleShouldBeFound("empresaId.contains=" + DEFAULT_EMPRESA_ID);

        // Get all the locationSampleList where empresaId contains UPDATED_EMPRESA_ID
        defaultLocationSampleShouldNotBeFound("empresaId.contains=" + UPDATED_EMPRESA_ID);
    }

    @Test
    @Transactional
    void getAllLocationSamplesByEmpresaIdNotContainsSomething() throws Exception {
        // Initialize the database
        locationSampleRepository.saveAndFlush(locationSample);

        // Get all the locationSampleList where empresaId does not contain DEFAULT_EMPRESA_ID
        defaultLocationSampleShouldNotBeFound("empresaId.doesNotContain=" + DEFAULT_EMPRESA_ID);

        // Get all the locationSampleList where empresaId does not contain UPDATED_EMPRESA_ID
        defaultLocationSampleShouldBeFound("empresaId.doesNotContain=" + UPDATED_EMPRESA_ID);
    }

    @Test
    @Transactional
    void getAllLocationSamplesByLatitudMinIsEqualToSomething() throws Exception {
        // Initialize the database
        locationSampleRepository.saveAndFlush(locationSample);

        // Get all the locationSampleList where latitudMin equals to DEFAULT_LATITUD_MIN
        defaultLocationSampleShouldBeFound("latitudMin.equals=" + DEFAULT_LATITUD_MIN);

        // Get all the locationSampleList where latitudMin equals to UPDATED_LATITUD_MIN
        defaultLocationSampleShouldNotBeFound("latitudMin.equals=" + UPDATED_LATITUD_MIN);
    }

    @Test
    @Transactional
    void getAllLocationSamplesByLatitudMinIsInShouldWork() throws Exception {
        // Initialize the database
        locationSampleRepository.saveAndFlush(locationSample);

        // Get all the locationSampleList where latitudMin in DEFAULT_LATITUD_MIN or UPDATED_LATITUD_MIN
        defaultLocationSampleShouldBeFound("latitudMin.in=" + DEFAULT_LATITUD_MIN + "," + UPDATED_LATITUD_MIN);

        // Get all the locationSampleList where latitudMin equals to UPDATED_LATITUD_MIN
        defaultLocationSampleShouldNotBeFound("latitudMin.in=" + UPDATED_LATITUD_MIN);
    }

    @Test
    @Transactional
    void getAllLocationSamplesByLatitudMinIsNullOrNotNull() throws Exception {
        // Initialize the database
        locationSampleRepository.saveAndFlush(locationSample);

        // Get all the locationSampleList where latitudMin is not null
        defaultLocationSampleShouldBeFound("latitudMin.specified=true");

        // Get all the locationSampleList where latitudMin is null
        defaultLocationSampleShouldNotBeFound("latitudMin.specified=false");
    }

    @Test
    @Transactional
    void getAllLocationSamplesByLatitudMinIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        locationSampleRepository.saveAndFlush(locationSample);

        // Get all the locationSampleList where latitudMin is greater than or equal to DEFAULT_LATITUD_MIN
        defaultLocationSampleShouldBeFound("latitudMin.greaterThanOrEqual=" + DEFAULT_LATITUD_MIN);

        // Get all the locationSampleList where latitudMin is greater than or equal to UPDATED_LATITUD_MIN
        defaultLocationSampleShouldNotBeFound("latitudMin.greaterThanOrEqual=" + UPDATED_LATITUD_MIN);
    }

    @Test
    @Transactional
    void getAllLocationSamplesByLatitudMinIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        locationSampleRepository.saveAndFlush(locationSample);

        // Get all the locationSampleList where latitudMin is less than or equal to DEFAULT_LATITUD_MIN
        defaultLocationSampleShouldBeFound("latitudMin.lessThanOrEqual=" + DEFAULT_LATITUD_MIN);

        // Get all the locationSampleList where latitudMin is less than or equal to SMALLER_LATITUD_MIN
        defaultLocationSampleShouldNotBeFound("latitudMin.lessThanOrEqual=" + SMALLER_LATITUD_MIN);
    }

    @Test
    @Transactional
    void getAllLocationSamplesByLatitudMinIsLessThanSomething() throws Exception {
        // Initialize the database
        locationSampleRepository.saveAndFlush(locationSample);

        // Get all the locationSampleList where latitudMin is less than DEFAULT_LATITUD_MIN
        defaultLocationSampleShouldNotBeFound("latitudMin.lessThan=" + DEFAULT_LATITUD_MIN);

        // Get all the locationSampleList where latitudMin is less than UPDATED_LATITUD_MIN
        defaultLocationSampleShouldBeFound("latitudMin.lessThan=" + UPDATED_LATITUD_MIN);
    }

    @Test
    @Transactional
    void getAllLocationSamplesByLatitudMinIsGreaterThanSomething() throws Exception {
        // Initialize the database
        locationSampleRepository.saveAndFlush(locationSample);

        // Get all the locationSampleList where latitudMin is greater than DEFAULT_LATITUD_MIN
        defaultLocationSampleShouldNotBeFound("latitudMin.greaterThan=" + DEFAULT_LATITUD_MIN);

        // Get all the locationSampleList where latitudMin is greater than SMALLER_LATITUD_MIN
        defaultLocationSampleShouldBeFound("latitudMin.greaterThan=" + SMALLER_LATITUD_MIN);
    }

    @Test
    @Transactional
    void getAllLocationSamplesByLongitudMinIsEqualToSomething() throws Exception {
        // Initialize the database
        locationSampleRepository.saveAndFlush(locationSample);

        // Get all the locationSampleList where longitudMin equals to DEFAULT_LONGITUD_MIN
        defaultLocationSampleShouldBeFound("longitudMin.equals=" + DEFAULT_LONGITUD_MIN);

        // Get all the locationSampleList where longitudMin equals to UPDATED_LONGITUD_MIN
        defaultLocationSampleShouldNotBeFound("longitudMin.equals=" + UPDATED_LONGITUD_MIN);
    }

    @Test
    @Transactional
    void getAllLocationSamplesByLongitudMinIsInShouldWork() throws Exception {
        // Initialize the database
        locationSampleRepository.saveAndFlush(locationSample);

        // Get all the locationSampleList where longitudMin in DEFAULT_LONGITUD_MIN or UPDATED_LONGITUD_MIN
        defaultLocationSampleShouldBeFound("longitudMin.in=" + DEFAULT_LONGITUD_MIN + "," + UPDATED_LONGITUD_MIN);

        // Get all the locationSampleList where longitudMin equals to UPDATED_LONGITUD_MIN
        defaultLocationSampleShouldNotBeFound("longitudMin.in=" + UPDATED_LONGITUD_MIN);
    }

    @Test
    @Transactional
    void getAllLocationSamplesByLongitudMinIsNullOrNotNull() throws Exception {
        // Initialize the database
        locationSampleRepository.saveAndFlush(locationSample);

        // Get all the locationSampleList where longitudMin is not null
        defaultLocationSampleShouldBeFound("longitudMin.specified=true");

        // Get all the locationSampleList where longitudMin is null
        defaultLocationSampleShouldNotBeFound("longitudMin.specified=false");
    }

    @Test
    @Transactional
    void getAllLocationSamplesByLongitudMinIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        locationSampleRepository.saveAndFlush(locationSample);

        // Get all the locationSampleList where longitudMin is greater than or equal to DEFAULT_LONGITUD_MIN
        defaultLocationSampleShouldBeFound("longitudMin.greaterThanOrEqual=" + DEFAULT_LONGITUD_MIN);

        // Get all the locationSampleList where longitudMin is greater than or equal to UPDATED_LONGITUD_MIN
        defaultLocationSampleShouldNotBeFound("longitudMin.greaterThanOrEqual=" + UPDATED_LONGITUD_MIN);
    }

    @Test
    @Transactional
    void getAllLocationSamplesByLongitudMinIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        locationSampleRepository.saveAndFlush(locationSample);

        // Get all the locationSampleList where longitudMin is less than or equal to DEFAULT_LONGITUD_MIN
        defaultLocationSampleShouldBeFound("longitudMin.lessThanOrEqual=" + DEFAULT_LONGITUD_MIN);

        // Get all the locationSampleList where longitudMin is less than or equal to SMALLER_LONGITUD_MIN
        defaultLocationSampleShouldNotBeFound("longitudMin.lessThanOrEqual=" + SMALLER_LONGITUD_MIN);
    }

    @Test
    @Transactional
    void getAllLocationSamplesByLongitudMinIsLessThanSomething() throws Exception {
        // Initialize the database
        locationSampleRepository.saveAndFlush(locationSample);

        // Get all the locationSampleList where longitudMin is less than DEFAULT_LONGITUD_MIN
        defaultLocationSampleShouldNotBeFound("longitudMin.lessThan=" + DEFAULT_LONGITUD_MIN);

        // Get all the locationSampleList where longitudMin is less than UPDATED_LONGITUD_MIN
        defaultLocationSampleShouldBeFound("longitudMin.lessThan=" + UPDATED_LONGITUD_MIN);
    }

    @Test
    @Transactional
    void getAllLocationSamplesByLongitudMinIsGreaterThanSomething() throws Exception {
        // Initialize the database
        locationSampleRepository.saveAndFlush(locationSample);

        // Get all the locationSampleList where longitudMin is greater than DEFAULT_LONGITUD_MIN
        defaultLocationSampleShouldNotBeFound("longitudMin.greaterThan=" + DEFAULT_LONGITUD_MIN);

        // Get all the locationSampleList where longitudMin is greater than SMALLER_LONGITUD_MIN
        defaultLocationSampleShouldBeFound("longitudMin.greaterThan=" + SMALLER_LONGITUD_MIN);
    }

    @Test
    @Transactional
    void getAllLocationSamplesByLatitudMaxIsEqualToSomething() throws Exception {
        // Initialize the database
        locationSampleRepository.saveAndFlush(locationSample);

        // Get all the locationSampleList where latitudMax equals to DEFAULT_LATITUD_MAX
        defaultLocationSampleShouldBeFound("latitudMax.equals=" + DEFAULT_LATITUD_MAX);

        // Get all the locationSampleList where latitudMax equals to UPDATED_LATITUD_MAX
        defaultLocationSampleShouldNotBeFound("latitudMax.equals=" + UPDATED_LATITUD_MAX);
    }

    @Test
    @Transactional
    void getAllLocationSamplesByLatitudMaxIsInShouldWork() throws Exception {
        // Initialize the database
        locationSampleRepository.saveAndFlush(locationSample);

        // Get all the locationSampleList where latitudMax in DEFAULT_LATITUD_MAX or UPDATED_LATITUD_MAX
        defaultLocationSampleShouldBeFound("latitudMax.in=" + DEFAULT_LATITUD_MAX + "," + UPDATED_LATITUD_MAX);

        // Get all the locationSampleList where latitudMax equals to UPDATED_LATITUD_MAX
        defaultLocationSampleShouldNotBeFound("latitudMax.in=" + UPDATED_LATITUD_MAX);
    }

    @Test
    @Transactional
    void getAllLocationSamplesByLatitudMaxIsNullOrNotNull() throws Exception {
        // Initialize the database
        locationSampleRepository.saveAndFlush(locationSample);

        // Get all the locationSampleList where latitudMax is not null
        defaultLocationSampleShouldBeFound("latitudMax.specified=true");

        // Get all the locationSampleList where latitudMax is null
        defaultLocationSampleShouldNotBeFound("latitudMax.specified=false");
    }

    @Test
    @Transactional
    void getAllLocationSamplesByLatitudMaxIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        locationSampleRepository.saveAndFlush(locationSample);

        // Get all the locationSampleList where latitudMax is greater than or equal to DEFAULT_LATITUD_MAX
        defaultLocationSampleShouldBeFound("latitudMax.greaterThanOrEqual=" + DEFAULT_LATITUD_MAX);

        // Get all the locationSampleList where latitudMax is greater than or equal to UPDATED_LATITUD_MAX
        defaultLocationSampleShouldNotBeFound("latitudMax.greaterThanOrEqual=" + UPDATED_LATITUD_MAX);
    }

    @Test
    @Transactional
    void getAllLocationSamplesByLatitudMaxIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        locationSampleRepository.saveAndFlush(locationSample);

        // Get all the locationSampleList where latitudMax is less than or equal to DEFAULT_LATITUD_MAX
        defaultLocationSampleShouldBeFound("latitudMax.lessThanOrEqual=" + DEFAULT_LATITUD_MAX);

        // Get all the locationSampleList where latitudMax is less than or equal to SMALLER_LATITUD_MAX
        defaultLocationSampleShouldNotBeFound("latitudMax.lessThanOrEqual=" + SMALLER_LATITUD_MAX);
    }

    @Test
    @Transactional
    void getAllLocationSamplesByLatitudMaxIsLessThanSomething() throws Exception {
        // Initialize the database
        locationSampleRepository.saveAndFlush(locationSample);

        // Get all the locationSampleList where latitudMax is less than DEFAULT_LATITUD_MAX
        defaultLocationSampleShouldNotBeFound("latitudMax.lessThan=" + DEFAULT_LATITUD_MAX);

        // Get all the locationSampleList where latitudMax is less than UPDATED_LATITUD_MAX
        defaultLocationSampleShouldBeFound("latitudMax.lessThan=" + UPDATED_LATITUD_MAX);
    }

    @Test
    @Transactional
    void getAllLocationSamplesByLatitudMaxIsGreaterThanSomething() throws Exception {
        // Initialize the database
        locationSampleRepository.saveAndFlush(locationSample);

        // Get all the locationSampleList where latitudMax is greater than DEFAULT_LATITUD_MAX
        defaultLocationSampleShouldNotBeFound("latitudMax.greaterThan=" + DEFAULT_LATITUD_MAX);

        // Get all the locationSampleList where latitudMax is greater than SMALLER_LATITUD_MAX
        defaultLocationSampleShouldBeFound("latitudMax.greaterThan=" + SMALLER_LATITUD_MAX);
    }

    @Test
    @Transactional
    void getAllLocationSamplesByLongitudMaxIsEqualToSomething() throws Exception {
        // Initialize the database
        locationSampleRepository.saveAndFlush(locationSample);

        // Get all the locationSampleList where longitudMax equals to DEFAULT_LONGITUD_MAX
        defaultLocationSampleShouldBeFound("longitudMax.equals=" + DEFAULT_LONGITUD_MAX);

        // Get all the locationSampleList where longitudMax equals to UPDATED_LONGITUD_MAX
        defaultLocationSampleShouldNotBeFound("longitudMax.equals=" + UPDATED_LONGITUD_MAX);
    }

    @Test
    @Transactional
    void getAllLocationSamplesByLongitudMaxIsInShouldWork() throws Exception {
        // Initialize the database
        locationSampleRepository.saveAndFlush(locationSample);

        // Get all the locationSampleList where longitudMax in DEFAULT_LONGITUD_MAX or UPDATED_LONGITUD_MAX
        defaultLocationSampleShouldBeFound("longitudMax.in=" + DEFAULT_LONGITUD_MAX + "," + UPDATED_LONGITUD_MAX);

        // Get all the locationSampleList where longitudMax equals to UPDATED_LONGITUD_MAX
        defaultLocationSampleShouldNotBeFound("longitudMax.in=" + UPDATED_LONGITUD_MAX);
    }

    @Test
    @Transactional
    void getAllLocationSamplesByLongitudMaxIsNullOrNotNull() throws Exception {
        // Initialize the database
        locationSampleRepository.saveAndFlush(locationSample);

        // Get all the locationSampleList where longitudMax is not null
        defaultLocationSampleShouldBeFound("longitudMax.specified=true");

        // Get all the locationSampleList where longitudMax is null
        defaultLocationSampleShouldNotBeFound("longitudMax.specified=false");
    }

    @Test
    @Transactional
    void getAllLocationSamplesByLongitudMaxIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        locationSampleRepository.saveAndFlush(locationSample);

        // Get all the locationSampleList where longitudMax is greater than or equal to DEFAULT_LONGITUD_MAX
        defaultLocationSampleShouldBeFound("longitudMax.greaterThanOrEqual=" + DEFAULT_LONGITUD_MAX);

        // Get all the locationSampleList where longitudMax is greater than or equal to UPDATED_LONGITUD_MAX
        defaultLocationSampleShouldNotBeFound("longitudMax.greaterThanOrEqual=" + UPDATED_LONGITUD_MAX);
    }

    @Test
    @Transactional
    void getAllLocationSamplesByLongitudMaxIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        locationSampleRepository.saveAndFlush(locationSample);

        // Get all the locationSampleList where longitudMax is less than or equal to DEFAULT_LONGITUD_MAX
        defaultLocationSampleShouldBeFound("longitudMax.lessThanOrEqual=" + DEFAULT_LONGITUD_MAX);

        // Get all the locationSampleList where longitudMax is less than or equal to SMALLER_LONGITUD_MAX
        defaultLocationSampleShouldNotBeFound("longitudMax.lessThanOrEqual=" + SMALLER_LONGITUD_MAX);
    }

    @Test
    @Transactional
    void getAllLocationSamplesByLongitudMaxIsLessThanSomething() throws Exception {
        // Initialize the database
        locationSampleRepository.saveAndFlush(locationSample);

        // Get all the locationSampleList where longitudMax is less than DEFAULT_LONGITUD_MAX
        defaultLocationSampleShouldNotBeFound("longitudMax.lessThan=" + DEFAULT_LONGITUD_MAX);

        // Get all the locationSampleList where longitudMax is less than UPDATED_LONGITUD_MAX
        defaultLocationSampleShouldBeFound("longitudMax.lessThan=" + UPDATED_LONGITUD_MAX);
    }

    @Test
    @Transactional
    void getAllLocationSamplesByLongitudMaxIsGreaterThanSomething() throws Exception {
        // Initialize the database
        locationSampleRepository.saveAndFlush(locationSample);

        // Get all the locationSampleList where longitudMax is greater than DEFAULT_LONGITUD_MAX
        defaultLocationSampleShouldNotBeFound("longitudMax.greaterThan=" + DEFAULT_LONGITUD_MAX);

        // Get all the locationSampleList where longitudMax is greater than SMALLER_LONGITUD_MAX
        defaultLocationSampleShouldBeFound("longitudMax.greaterThan=" + SMALLER_LONGITUD_MAX);
    }

    @Test
    @Transactional
    void getAllLocationSamplesByAccuracyIsEqualToSomething() throws Exception {
        // Initialize the database
        locationSampleRepository.saveAndFlush(locationSample);

        // Get all the locationSampleList where accuracy equals to DEFAULT_ACCURACY
        defaultLocationSampleShouldBeFound("accuracy.equals=" + DEFAULT_ACCURACY);

        // Get all the locationSampleList where accuracy equals to UPDATED_ACCURACY
        defaultLocationSampleShouldNotBeFound("accuracy.equals=" + UPDATED_ACCURACY);
    }

    @Test
    @Transactional
    void getAllLocationSamplesByAccuracyIsInShouldWork() throws Exception {
        // Initialize the database
        locationSampleRepository.saveAndFlush(locationSample);

        // Get all the locationSampleList where accuracy in DEFAULT_ACCURACY or UPDATED_ACCURACY
        defaultLocationSampleShouldBeFound("accuracy.in=" + DEFAULT_ACCURACY + "," + UPDATED_ACCURACY);

        // Get all the locationSampleList where accuracy equals to UPDATED_ACCURACY
        defaultLocationSampleShouldNotBeFound("accuracy.in=" + UPDATED_ACCURACY);
    }

    @Test
    @Transactional
    void getAllLocationSamplesByAccuracyIsNullOrNotNull() throws Exception {
        // Initialize the database
        locationSampleRepository.saveAndFlush(locationSample);

        // Get all the locationSampleList where accuracy is not null
        defaultLocationSampleShouldBeFound("accuracy.specified=true");

        // Get all the locationSampleList where accuracy is null
        defaultLocationSampleShouldNotBeFound("accuracy.specified=false");
    }

    @Test
    @Transactional
    void getAllLocationSamplesByAccuracyIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        locationSampleRepository.saveAndFlush(locationSample);

        // Get all the locationSampleList where accuracy is greater than or equal to DEFAULT_ACCURACY
        defaultLocationSampleShouldBeFound("accuracy.greaterThanOrEqual=" + DEFAULT_ACCURACY);

        // Get all the locationSampleList where accuracy is greater than or equal to UPDATED_ACCURACY
        defaultLocationSampleShouldNotBeFound("accuracy.greaterThanOrEqual=" + UPDATED_ACCURACY);
    }

    @Test
    @Transactional
    void getAllLocationSamplesByAccuracyIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        locationSampleRepository.saveAndFlush(locationSample);

        // Get all the locationSampleList where accuracy is less than or equal to DEFAULT_ACCURACY
        defaultLocationSampleShouldBeFound("accuracy.lessThanOrEqual=" + DEFAULT_ACCURACY);

        // Get all the locationSampleList where accuracy is less than or equal to SMALLER_ACCURACY
        defaultLocationSampleShouldNotBeFound("accuracy.lessThanOrEqual=" + SMALLER_ACCURACY);
    }

    @Test
    @Transactional
    void getAllLocationSamplesByAccuracyIsLessThanSomething() throws Exception {
        // Initialize the database
        locationSampleRepository.saveAndFlush(locationSample);

        // Get all the locationSampleList where accuracy is less than DEFAULT_ACCURACY
        defaultLocationSampleShouldNotBeFound("accuracy.lessThan=" + DEFAULT_ACCURACY);

        // Get all the locationSampleList where accuracy is less than UPDATED_ACCURACY
        defaultLocationSampleShouldBeFound("accuracy.lessThan=" + UPDATED_ACCURACY);
    }

    @Test
    @Transactional
    void getAllLocationSamplesByAccuracyIsGreaterThanSomething() throws Exception {
        // Initialize the database
        locationSampleRepository.saveAndFlush(locationSample);

        // Get all the locationSampleList where accuracy is greater than DEFAULT_ACCURACY
        defaultLocationSampleShouldNotBeFound("accuracy.greaterThan=" + DEFAULT_ACCURACY);

        // Get all the locationSampleList where accuracy is greater than SMALLER_ACCURACY
        defaultLocationSampleShouldBeFound("accuracy.greaterThan=" + SMALLER_ACCURACY);
    }

    @Test
    @Transactional
    void getAllLocationSamplesByAltitudIsEqualToSomething() throws Exception {
        // Initialize the database
        locationSampleRepository.saveAndFlush(locationSample);

        // Get all the locationSampleList where altitud equals to DEFAULT_ALTITUD
        defaultLocationSampleShouldBeFound("altitud.equals=" + DEFAULT_ALTITUD);

        // Get all the locationSampleList where altitud equals to UPDATED_ALTITUD
        defaultLocationSampleShouldNotBeFound("altitud.equals=" + UPDATED_ALTITUD);
    }

    @Test
    @Transactional
    void getAllLocationSamplesByAltitudIsInShouldWork() throws Exception {
        // Initialize the database
        locationSampleRepository.saveAndFlush(locationSample);

        // Get all the locationSampleList where altitud in DEFAULT_ALTITUD or UPDATED_ALTITUD
        defaultLocationSampleShouldBeFound("altitud.in=" + DEFAULT_ALTITUD + "," + UPDATED_ALTITUD);

        // Get all the locationSampleList where altitud equals to UPDATED_ALTITUD
        defaultLocationSampleShouldNotBeFound("altitud.in=" + UPDATED_ALTITUD);
    }

    @Test
    @Transactional
    void getAllLocationSamplesByAltitudIsNullOrNotNull() throws Exception {
        // Initialize the database
        locationSampleRepository.saveAndFlush(locationSample);

        // Get all the locationSampleList where altitud is not null
        defaultLocationSampleShouldBeFound("altitud.specified=true");

        // Get all the locationSampleList where altitud is null
        defaultLocationSampleShouldNotBeFound("altitud.specified=false");
    }

    @Test
    @Transactional
    void getAllLocationSamplesByAltitudIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        locationSampleRepository.saveAndFlush(locationSample);

        // Get all the locationSampleList where altitud is greater than or equal to DEFAULT_ALTITUD
        defaultLocationSampleShouldBeFound("altitud.greaterThanOrEqual=" + DEFAULT_ALTITUD);

        // Get all the locationSampleList where altitud is greater than or equal to UPDATED_ALTITUD
        defaultLocationSampleShouldNotBeFound("altitud.greaterThanOrEqual=" + UPDATED_ALTITUD);
    }

    @Test
    @Transactional
    void getAllLocationSamplesByAltitudIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        locationSampleRepository.saveAndFlush(locationSample);

        // Get all the locationSampleList where altitud is less than or equal to DEFAULT_ALTITUD
        defaultLocationSampleShouldBeFound("altitud.lessThanOrEqual=" + DEFAULT_ALTITUD);

        // Get all the locationSampleList where altitud is less than or equal to SMALLER_ALTITUD
        defaultLocationSampleShouldNotBeFound("altitud.lessThanOrEqual=" + SMALLER_ALTITUD);
    }

    @Test
    @Transactional
    void getAllLocationSamplesByAltitudIsLessThanSomething() throws Exception {
        // Initialize the database
        locationSampleRepository.saveAndFlush(locationSample);

        // Get all the locationSampleList where altitud is less than DEFAULT_ALTITUD
        defaultLocationSampleShouldNotBeFound("altitud.lessThan=" + DEFAULT_ALTITUD);

        // Get all the locationSampleList where altitud is less than UPDATED_ALTITUD
        defaultLocationSampleShouldBeFound("altitud.lessThan=" + UPDATED_ALTITUD);
    }

    @Test
    @Transactional
    void getAllLocationSamplesByAltitudIsGreaterThanSomething() throws Exception {
        // Initialize the database
        locationSampleRepository.saveAndFlush(locationSample);

        // Get all the locationSampleList where altitud is greater than DEFAULT_ALTITUD
        defaultLocationSampleShouldNotBeFound("altitud.greaterThan=" + DEFAULT_ALTITUD);

        // Get all the locationSampleList where altitud is greater than SMALLER_ALTITUD
        defaultLocationSampleShouldBeFound("altitud.greaterThan=" + SMALLER_ALTITUD);
    }

    @Test
    @Transactional
    void getAllLocationSamplesByStartTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        locationSampleRepository.saveAndFlush(locationSample);

        // Get all the locationSampleList where startTime equals to DEFAULT_START_TIME
        defaultLocationSampleShouldBeFound("startTime.equals=" + DEFAULT_START_TIME);

        // Get all the locationSampleList where startTime equals to UPDATED_START_TIME
        defaultLocationSampleShouldNotBeFound("startTime.equals=" + UPDATED_START_TIME);
    }

    @Test
    @Transactional
    void getAllLocationSamplesByStartTimeIsInShouldWork() throws Exception {
        // Initialize the database
        locationSampleRepository.saveAndFlush(locationSample);

        // Get all the locationSampleList where startTime in DEFAULT_START_TIME or UPDATED_START_TIME
        defaultLocationSampleShouldBeFound("startTime.in=" + DEFAULT_START_TIME + "," + UPDATED_START_TIME);

        // Get all the locationSampleList where startTime equals to UPDATED_START_TIME
        defaultLocationSampleShouldNotBeFound("startTime.in=" + UPDATED_START_TIME);
    }

    @Test
    @Transactional
    void getAllLocationSamplesByStartTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        locationSampleRepository.saveAndFlush(locationSample);

        // Get all the locationSampleList where startTime is not null
        defaultLocationSampleShouldBeFound("startTime.specified=true");

        // Get all the locationSampleList where startTime is null
        defaultLocationSampleShouldNotBeFound("startTime.specified=false");
    }

    @Test
    @Transactional
    void getAllLocationSamplesByEndTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        locationSampleRepository.saveAndFlush(locationSample);

        // Get all the locationSampleList where endTime equals to DEFAULT_END_TIME
        defaultLocationSampleShouldBeFound("endTime.equals=" + DEFAULT_END_TIME);

        // Get all the locationSampleList where endTime equals to UPDATED_END_TIME
        defaultLocationSampleShouldNotBeFound("endTime.equals=" + UPDATED_END_TIME);
    }

    @Test
    @Transactional
    void getAllLocationSamplesByEndTimeIsInShouldWork() throws Exception {
        // Initialize the database
        locationSampleRepository.saveAndFlush(locationSample);

        // Get all the locationSampleList where endTime in DEFAULT_END_TIME or UPDATED_END_TIME
        defaultLocationSampleShouldBeFound("endTime.in=" + DEFAULT_END_TIME + "," + UPDATED_END_TIME);

        // Get all the locationSampleList where endTime equals to UPDATED_END_TIME
        defaultLocationSampleShouldNotBeFound("endTime.in=" + UPDATED_END_TIME);
    }

    @Test
    @Transactional
    void getAllLocationSamplesByEndTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        locationSampleRepository.saveAndFlush(locationSample);

        // Get all the locationSampleList where endTime is not null
        defaultLocationSampleShouldBeFound("endTime.specified=true");

        // Get all the locationSampleList where endTime is null
        defaultLocationSampleShouldNotBeFound("endTime.specified=false");
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultLocationSampleShouldBeFound(String filter) throws Exception {
        restLocationSampleMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(locationSample.getId().toString())))
            .andExpect(jsonPath("$.[*].usuarioId").value(hasItem(DEFAULT_USUARIO_ID)))
            .andExpect(jsonPath("$.[*].empresaId").value(hasItem(DEFAULT_EMPRESA_ID)))
            .andExpect(jsonPath("$.[*].latitudMin").value(hasItem(DEFAULT_LATITUD_MIN.doubleValue())))
            .andExpect(jsonPath("$.[*].longitudMin").value(hasItem(DEFAULT_LONGITUD_MIN.doubleValue())))
            .andExpect(jsonPath("$.[*].latitudMax").value(hasItem(DEFAULT_LATITUD_MAX.doubleValue())))
            .andExpect(jsonPath("$.[*].longitudMax").value(hasItem(DEFAULT_LONGITUD_MAX.doubleValue())))
            .andExpect(jsonPath("$.[*].accuracy").value(hasItem(DEFAULT_ACCURACY.doubleValue())))
            .andExpect(jsonPath("$.[*].altitud").value(hasItem(DEFAULT_ALTITUD.doubleValue())))
            .andExpect(jsonPath("$.[*].startTime").value(hasItem(DEFAULT_START_TIME.toString())))
            .andExpect(jsonPath("$.[*].endTime").value(hasItem(DEFAULT_END_TIME.toString())));

        // Check, that the count call also returns 1
        restLocationSampleMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultLocationSampleShouldNotBeFound(String filter) throws Exception {
        restLocationSampleMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restLocationSampleMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingLocationSample() throws Exception {
        // Get the locationSample
        restLocationSampleMockMvc.perform(get(ENTITY_API_URL_ID, UUID.randomUUID().toString())).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingLocationSample() throws Exception {
        // Initialize the database
        locationSampleRepository.saveAndFlush(locationSample);

        int databaseSizeBeforeUpdate = locationSampleRepository.findAll().size();

        // Update the locationSample
        LocationSample updatedLocationSample = locationSampleRepository.findById(locationSample.getId()).get();
        // Disconnect from session so that the updates on updatedLocationSample are not directly saved in db
        em.detach(updatedLocationSample);
        updatedLocationSample
            .usuarioId(UPDATED_USUARIO_ID)
            .empresaId(UPDATED_EMPRESA_ID)
            .latitudMin(UPDATED_LATITUD_MIN)
            .longitudMin(UPDATED_LONGITUD_MIN)
            .latitudMax(UPDATED_LATITUD_MAX)
            .longitudMax(UPDATED_LONGITUD_MAX)
            .accuracy(UPDATED_ACCURACY)
            .altitud(UPDATED_ALTITUD)
            .startTime(UPDATED_START_TIME)
            .endTime(UPDATED_END_TIME);

        restLocationSampleMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedLocationSample.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedLocationSample))
            )
            .andExpect(status().isOk());

        // Validate the LocationSample in the database
        List<LocationSample> locationSampleList = locationSampleRepository.findAll();
        assertThat(locationSampleList).hasSize(databaseSizeBeforeUpdate);
        LocationSample testLocationSample = locationSampleList.get(locationSampleList.size() - 1);
        assertThat(testLocationSample.getUsuarioId()).isEqualTo(UPDATED_USUARIO_ID);
        assertThat(testLocationSample.getEmpresaId()).isEqualTo(UPDATED_EMPRESA_ID);
        assertThat(testLocationSample.getLatitudMin()).isEqualTo(UPDATED_LATITUD_MIN);
        assertThat(testLocationSample.getLongitudMin()).isEqualTo(UPDATED_LONGITUD_MIN);
        assertThat(testLocationSample.getLatitudMax()).isEqualTo(UPDATED_LATITUD_MAX);
        assertThat(testLocationSample.getLongitudMax()).isEqualTo(UPDATED_LONGITUD_MAX);
        assertThat(testLocationSample.getAccuracy()).isEqualTo(UPDATED_ACCURACY);
        assertThat(testLocationSample.getAltitud()).isEqualTo(UPDATED_ALTITUD);
        assertThat(testLocationSample.getStartTime()).isEqualTo(UPDATED_START_TIME);
        assertThat(testLocationSample.getEndTime()).isEqualTo(UPDATED_END_TIME);
    }

    @Test
    @Transactional
    void putNonExistingLocationSample() throws Exception {
        int databaseSizeBeforeUpdate = locationSampleRepository.findAll().size();
        locationSample.setId(UUID.randomUUID());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLocationSampleMockMvc
            .perform(
                put(ENTITY_API_URL_ID, locationSample.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(locationSample))
            )
            .andExpect(status().isBadRequest());

        // Validate the LocationSample in the database
        List<LocationSample> locationSampleList = locationSampleRepository.findAll();
        assertThat(locationSampleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchLocationSample() throws Exception {
        int databaseSizeBeforeUpdate = locationSampleRepository.findAll().size();
        locationSample.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLocationSampleMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(locationSample))
            )
            .andExpect(status().isBadRequest());

        // Validate the LocationSample in the database
        List<LocationSample> locationSampleList = locationSampleRepository.findAll();
        assertThat(locationSampleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamLocationSample() throws Exception {
        int databaseSizeBeforeUpdate = locationSampleRepository.findAll().size();
        locationSample.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLocationSampleMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(locationSample)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the LocationSample in the database
        List<LocationSample> locationSampleList = locationSampleRepository.findAll();
        assertThat(locationSampleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateLocationSampleWithPatch() throws Exception {
        // Initialize the database
        locationSampleRepository.saveAndFlush(locationSample);

        int databaseSizeBeforeUpdate = locationSampleRepository.findAll().size();

        // Update the locationSample using partial update
        LocationSample partialUpdatedLocationSample = new LocationSample();
        partialUpdatedLocationSample.setId(locationSample.getId());

        partialUpdatedLocationSample
            .usuarioId(UPDATED_USUARIO_ID)
            .longitudMin(UPDATED_LONGITUD_MIN)
            .latitudMax(UPDATED_LATITUD_MAX)
            .altitud(UPDATED_ALTITUD)
            .startTime(UPDATED_START_TIME);

        restLocationSampleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLocationSample.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLocationSample))
            )
            .andExpect(status().isOk());

        // Validate the LocationSample in the database
        List<LocationSample> locationSampleList = locationSampleRepository.findAll();
        assertThat(locationSampleList).hasSize(databaseSizeBeforeUpdate);
        LocationSample testLocationSample = locationSampleList.get(locationSampleList.size() - 1);
        assertThat(testLocationSample.getUsuarioId()).isEqualTo(UPDATED_USUARIO_ID);
        assertThat(testLocationSample.getEmpresaId()).isEqualTo(DEFAULT_EMPRESA_ID);
        assertThat(testLocationSample.getLatitudMin()).isEqualTo(DEFAULT_LATITUD_MIN);
        assertThat(testLocationSample.getLongitudMin()).isEqualTo(UPDATED_LONGITUD_MIN);
        assertThat(testLocationSample.getLatitudMax()).isEqualTo(UPDATED_LATITUD_MAX);
        assertThat(testLocationSample.getLongitudMax()).isEqualTo(DEFAULT_LONGITUD_MAX);
        assertThat(testLocationSample.getAccuracy()).isEqualTo(DEFAULT_ACCURACY);
        assertThat(testLocationSample.getAltitud()).isEqualTo(UPDATED_ALTITUD);
        assertThat(testLocationSample.getStartTime()).isEqualTo(UPDATED_START_TIME);
        assertThat(testLocationSample.getEndTime()).isEqualTo(DEFAULT_END_TIME);
    }

    @Test
    @Transactional
    void fullUpdateLocationSampleWithPatch() throws Exception {
        // Initialize the database
        locationSampleRepository.saveAndFlush(locationSample);

        int databaseSizeBeforeUpdate = locationSampleRepository.findAll().size();

        // Update the locationSample using partial update
        LocationSample partialUpdatedLocationSample = new LocationSample();
        partialUpdatedLocationSample.setId(locationSample.getId());

        partialUpdatedLocationSample
            .usuarioId(UPDATED_USUARIO_ID)
            .empresaId(UPDATED_EMPRESA_ID)
            .latitudMin(UPDATED_LATITUD_MIN)
            .longitudMin(UPDATED_LONGITUD_MIN)
            .latitudMax(UPDATED_LATITUD_MAX)
            .longitudMax(UPDATED_LONGITUD_MAX)
            .accuracy(UPDATED_ACCURACY)
            .altitud(UPDATED_ALTITUD)
            .startTime(UPDATED_START_TIME)
            .endTime(UPDATED_END_TIME);

        restLocationSampleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLocationSample.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLocationSample))
            )
            .andExpect(status().isOk());

        // Validate the LocationSample in the database
        List<LocationSample> locationSampleList = locationSampleRepository.findAll();
        assertThat(locationSampleList).hasSize(databaseSizeBeforeUpdate);
        LocationSample testLocationSample = locationSampleList.get(locationSampleList.size() - 1);
        assertThat(testLocationSample.getUsuarioId()).isEqualTo(UPDATED_USUARIO_ID);
        assertThat(testLocationSample.getEmpresaId()).isEqualTo(UPDATED_EMPRESA_ID);
        assertThat(testLocationSample.getLatitudMin()).isEqualTo(UPDATED_LATITUD_MIN);
        assertThat(testLocationSample.getLongitudMin()).isEqualTo(UPDATED_LONGITUD_MIN);
        assertThat(testLocationSample.getLatitudMax()).isEqualTo(UPDATED_LATITUD_MAX);
        assertThat(testLocationSample.getLongitudMax()).isEqualTo(UPDATED_LONGITUD_MAX);
        assertThat(testLocationSample.getAccuracy()).isEqualTo(UPDATED_ACCURACY);
        assertThat(testLocationSample.getAltitud()).isEqualTo(UPDATED_ALTITUD);
        assertThat(testLocationSample.getStartTime()).isEqualTo(UPDATED_START_TIME);
        assertThat(testLocationSample.getEndTime()).isEqualTo(UPDATED_END_TIME);
    }

    @Test
    @Transactional
    void patchNonExistingLocationSample() throws Exception {
        int databaseSizeBeforeUpdate = locationSampleRepository.findAll().size();
        locationSample.setId(UUID.randomUUID());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLocationSampleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, locationSample.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(locationSample))
            )
            .andExpect(status().isBadRequest());

        // Validate the LocationSample in the database
        List<LocationSample> locationSampleList = locationSampleRepository.findAll();
        assertThat(locationSampleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchLocationSample() throws Exception {
        int databaseSizeBeforeUpdate = locationSampleRepository.findAll().size();
        locationSample.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLocationSampleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(locationSample))
            )
            .andExpect(status().isBadRequest());

        // Validate the LocationSample in the database
        List<LocationSample> locationSampleList = locationSampleRepository.findAll();
        assertThat(locationSampleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamLocationSample() throws Exception {
        int databaseSizeBeforeUpdate = locationSampleRepository.findAll().size();
        locationSample.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLocationSampleMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(locationSample))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the LocationSample in the database
        List<LocationSample> locationSampleList = locationSampleRepository.findAll();
        assertThat(locationSampleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteLocationSample() throws Exception {
        // Initialize the database
        locationSampleRepository.saveAndFlush(locationSample);

        int databaseSizeBeforeDelete = locationSampleRepository.findAll().size();

        // Delete the locationSample
        restLocationSampleMockMvc
            .perform(delete(ENTITY_API_URL_ID, locationSample.getId().toString()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<LocationSample> locationSampleList = locationSampleRepository.findAll();
        assertThat(locationSampleList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
