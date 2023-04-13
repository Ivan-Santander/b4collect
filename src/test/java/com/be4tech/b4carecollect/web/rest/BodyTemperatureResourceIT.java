package com.be4tech.b4carecollect.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.be4tech.b4carecollect.IntegrationTest;
import com.be4tech.b4carecollect.domain.BodyTemperature;
import com.be4tech.b4carecollect.repository.BodyTemperatureRepository;
import com.be4tech.b4carecollect.service.criteria.BodyTemperatureCriteria;
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
 * Integration tests for the {@link BodyTemperatureResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class BodyTemperatureResourceIT {

    private static final String DEFAULT_USUARIO_ID = "AAAAAAAAAA";
    private static final String UPDATED_USUARIO_ID = "BBBBBBBBBB";

    private static final String DEFAULT_EMPRESA_ID = "AAAAAAAAAA";
    private static final String UPDATED_EMPRESA_ID = "BBBBBBBBBB";

    private static final Float DEFAULT_FIELD_BODY_TEMPERATURE = 1F;
    private static final Float UPDATED_FIELD_BODY_TEMPERATURE = 2F;
    private static final Float SMALLER_FIELD_BODY_TEMPERATURE = 1F - 1F;

    private static final Integer DEFAULT_FIELD_BODY_TEMPERATURE_MEASURE_LOCATION = 1;
    private static final Integer UPDATED_FIELD_BODY_TEMPERATURE_MEASURE_LOCATION = 2;
    private static final Integer SMALLER_FIELD_BODY_TEMPERATURE_MEASURE_LOCATION = 1 - 1;

    private static final Instant DEFAULT_END_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_END_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/body-temperatures";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private BodyTemperatureRepository bodyTemperatureRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBodyTemperatureMockMvc;

    private BodyTemperature bodyTemperature;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BodyTemperature createEntity(EntityManager em) {
        BodyTemperature bodyTemperature = new BodyTemperature()
            .usuarioId(DEFAULT_USUARIO_ID)
            .empresaId(DEFAULT_EMPRESA_ID)
            .fieldBodyTemperature(DEFAULT_FIELD_BODY_TEMPERATURE)
            .fieldBodyTemperatureMeasureLocation(DEFAULT_FIELD_BODY_TEMPERATURE_MEASURE_LOCATION)
            .endTime(DEFAULT_END_TIME);
        return bodyTemperature;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BodyTemperature createUpdatedEntity(EntityManager em) {
        BodyTemperature bodyTemperature = new BodyTemperature()
            .usuarioId(UPDATED_USUARIO_ID)
            .empresaId(UPDATED_EMPRESA_ID)
            .fieldBodyTemperature(UPDATED_FIELD_BODY_TEMPERATURE)
            .fieldBodyTemperatureMeasureLocation(UPDATED_FIELD_BODY_TEMPERATURE_MEASURE_LOCATION)
            .endTime(UPDATED_END_TIME);
        return bodyTemperature;
    }

    @BeforeEach
    public void initTest() {
        bodyTemperature = createEntity(em);
    }

    @Test
    @Transactional
    void createBodyTemperature() throws Exception {
        int databaseSizeBeforeCreate = bodyTemperatureRepository.findAll().size();
        // Create the BodyTemperature
        restBodyTemperatureMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bodyTemperature))
            )
            .andExpect(status().isCreated());

        // Validate the BodyTemperature in the database
        List<BodyTemperature> bodyTemperatureList = bodyTemperatureRepository.findAll();
        assertThat(bodyTemperatureList).hasSize(databaseSizeBeforeCreate + 1);
        BodyTemperature testBodyTemperature = bodyTemperatureList.get(bodyTemperatureList.size() - 1);
        assertThat(testBodyTemperature.getUsuarioId()).isEqualTo(DEFAULT_USUARIO_ID);
        assertThat(testBodyTemperature.getEmpresaId()).isEqualTo(DEFAULT_EMPRESA_ID);
        assertThat(testBodyTemperature.getFieldBodyTemperature()).isEqualTo(DEFAULT_FIELD_BODY_TEMPERATURE);
        assertThat(testBodyTemperature.getFieldBodyTemperatureMeasureLocation()).isEqualTo(DEFAULT_FIELD_BODY_TEMPERATURE_MEASURE_LOCATION);
        assertThat(testBodyTemperature.getEndTime()).isEqualTo(DEFAULT_END_TIME);
    }

    @Test
    @Transactional
    void createBodyTemperatureWithExistingId() throws Exception {
        // Create the BodyTemperature with an existing ID
        bodyTemperatureRepository.saveAndFlush(bodyTemperature);

        int databaseSizeBeforeCreate = bodyTemperatureRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBodyTemperatureMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bodyTemperature))
            )
            .andExpect(status().isBadRequest());

        // Validate the BodyTemperature in the database
        List<BodyTemperature> bodyTemperatureList = bodyTemperatureRepository.findAll();
        assertThat(bodyTemperatureList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllBodyTemperatures() throws Exception {
        // Initialize the database
        bodyTemperatureRepository.saveAndFlush(bodyTemperature);

        // Get all the bodyTemperatureList
        restBodyTemperatureMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bodyTemperature.getId().toString())))
            .andExpect(jsonPath("$.[*].usuarioId").value(hasItem(DEFAULT_USUARIO_ID)))
            .andExpect(jsonPath("$.[*].empresaId").value(hasItem(DEFAULT_EMPRESA_ID)))
            .andExpect(jsonPath("$.[*].fieldBodyTemperature").value(hasItem(DEFAULT_FIELD_BODY_TEMPERATURE.doubleValue())))
            .andExpect(
                jsonPath("$.[*].fieldBodyTemperatureMeasureLocation").value(hasItem(DEFAULT_FIELD_BODY_TEMPERATURE_MEASURE_LOCATION))
            )
            .andExpect(jsonPath("$.[*].endTime").value(hasItem(DEFAULT_END_TIME.toString())));
    }

    @Test
    @Transactional
    void getBodyTemperature() throws Exception {
        // Initialize the database
        bodyTemperatureRepository.saveAndFlush(bodyTemperature);

        // Get the bodyTemperature
        restBodyTemperatureMockMvc
            .perform(get(ENTITY_API_URL_ID, bodyTemperature.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(bodyTemperature.getId().toString()))
            .andExpect(jsonPath("$.usuarioId").value(DEFAULT_USUARIO_ID))
            .andExpect(jsonPath("$.empresaId").value(DEFAULT_EMPRESA_ID))
            .andExpect(jsonPath("$.fieldBodyTemperature").value(DEFAULT_FIELD_BODY_TEMPERATURE.doubleValue()))
            .andExpect(jsonPath("$.fieldBodyTemperatureMeasureLocation").value(DEFAULT_FIELD_BODY_TEMPERATURE_MEASURE_LOCATION))
            .andExpect(jsonPath("$.endTime").value(DEFAULT_END_TIME.toString()));
    }

    @Test
    @Transactional
    void getBodyTemperaturesByIdFiltering() throws Exception {
        // Initialize the database
        bodyTemperatureRepository.saveAndFlush(bodyTemperature);

        UUID id = bodyTemperature.getId();

        defaultBodyTemperatureShouldBeFound("id.equals=" + id);
        defaultBodyTemperatureShouldNotBeFound("id.notEquals=" + id);
    }

    @Test
    @Transactional
    void getAllBodyTemperaturesByUsuarioIdIsEqualToSomething() throws Exception {
        // Initialize the database
        bodyTemperatureRepository.saveAndFlush(bodyTemperature);

        // Get all the bodyTemperatureList where usuarioId equals to DEFAULT_USUARIO_ID
        defaultBodyTemperatureShouldBeFound("usuarioId.equals=" + DEFAULT_USUARIO_ID);

        // Get all the bodyTemperatureList where usuarioId equals to UPDATED_USUARIO_ID
        defaultBodyTemperatureShouldNotBeFound("usuarioId.equals=" + UPDATED_USUARIO_ID);
    }

    @Test
    @Transactional
    void getAllBodyTemperaturesByUsuarioIdIsInShouldWork() throws Exception {
        // Initialize the database
        bodyTemperatureRepository.saveAndFlush(bodyTemperature);

        // Get all the bodyTemperatureList where usuarioId in DEFAULT_USUARIO_ID or UPDATED_USUARIO_ID
        defaultBodyTemperatureShouldBeFound("usuarioId.in=" + DEFAULT_USUARIO_ID + "," + UPDATED_USUARIO_ID);

        // Get all the bodyTemperatureList where usuarioId equals to UPDATED_USUARIO_ID
        defaultBodyTemperatureShouldNotBeFound("usuarioId.in=" + UPDATED_USUARIO_ID);
    }

    @Test
    @Transactional
    void getAllBodyTemperaturesByUsuarioIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        bodyTemperatureRepository.saveAndFlush(bodyTemperature);

        // Get all the bodyTemperatureList where usuarioId is not null
        defaultBodyTemperatureShouldBeFound("usuarioId.specified=true");

        // Get all the bodyTemperatureList where usuarioId is null
        defaultBodyTemperatureShouldNotBeFound("usuarioId.specified=false");
    }

    @Test
    @Transactional
    void getAllBodyTemperaturesByUsuarioIdContainsSomething() throws Exception {
        // Initialize the database
        bodyTemperatureRepository.saveAndFlush(bodyTemperature);

        // Get all the bodyTemperatureList where usuarioId contains DEFAULT_USUARIO_ID
        defaultBodyTemperatureShouldBeFound("usuarioId.contains=" + DEFAULT_USUARIO_ID);

        // Get all the bodyTemperatureList where usuarioId contains UPDATED_USUARIO_ID
        defaultBodyTemperatureShouldNotBeFound("usuarioId.contains=" + UPDATED_USUARIO_ID);
    }

    @Test
    @Transactional
    void getAllBodyTemperaturesByUsuarioIdNotContainsSomething() throws Exception {
        // Initialize the database
        bodyTemperatureRepository.saveAndFlush(bodyTemperature);

        // Get all the bodyTemperatureList where usuarioId does not contain DEFAULT_USUARIO_ID
        defaultBodyTemperatureShouldNotBeFound("usuarioId.doesNotContain=" + DEFAULT_USUARIO_ID);

        // Get all the bodyTemperatureList where usuarioId does not contain UPDATED_USUARIO_ID
        defaultBodyTemperatureShouldBeFound("usuarioId.doesNotContain=" + UPDATED_USUARIO_ID);
    }

    @Test
    @Transactional
    void getAllBodyTemperaturesByEmpresaIdIsEqualToSomething() throws Exception {
        // Initialize the database
        bodyTemperatureRepository.saveAndFlush(bodyTemperature);

        // Get all the bodyTemperatureList where empresaId equals to DEFAULT_EMPRESA_ID
        defaultBodyTemperatureShouldBeFound("empresaId.equals=" + DEFAULT_EMPRESA_ID);

        // Get all the bodyTemperatureList where empresaId equals to UPDATED_EMPRESA_ID
        defaultBodyTemperatureShouldNotBeFound("empresaId.equals=" + UPDATED_EMPRESA_ID);
    }

    @Test
    @Transactional
    void getAllBodyTemperaturesByEmpresaIdIsInShouldWork() throws Exception {
        // Initialize the database
        bodyTemperatureRepository.saveAndFlush(bodyTemperature);

        // Get all the bodyTemperatureList where empresaId in DEFAULT_EMPRESA_ID or UPDATED_EMPRESA_ID
        defaultBodyTemperatureShouldBeFound("empresaId.in=" + DEFAULT_EMPRESA_ID + "," + UPDATED_EMPRESA_ID);

        // Get all the bodyTemperatureList where empresaId equals to UPDATED_EMPRESA_ID
        defaultBodyTemperatureShouldNotBeFound("empresaId.in=" + UPDATED_EMPRESA_ID);
    }

    @Test
    @Transactional
    void getAllBodyTemperaturesByEmpresaIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        bodyTemperatureRepository.saveAndFlush(bodyTemperature);

        // Get all the bodyTemperatureList where empresaId is not null
        defaultBodyTemperatureShouldBeFound("empresaId.specified=true");

        // Get all the bodyTemperatureList where empresaId is null
        defaultBodyTemperatureShouldNotBeFound("empresaId.specified=false");
    }

    @Test
    @Transactional
    void getAllBodyTemperaturesByEmpresaIdContainsSomething() throws Exception {
        // Initialize the database
        bodyTemperatureRepository.saveAndFlush(bodyTemperature);

        // Get all the bodyTemperatureList where empresaId contains DEFAULT_EMPRESA_ID
        defaultBodyTemperatureShouldBeFound("empresaId.contains=" + DEFAULT_EMPRESA_ID);

        // Get all the bodyTemperatureList where empresaId contains UPDATED_EMPRESA_ID
        defaultBodyTemperatureShouldNotBeFound("empresaId.contains=" + UPDATED_EMPRESA_ID);
    }

    @Test
    @Transactional
    void getAllBodyTemperaturesByEmpresaIdNotContainsSomething() throws Exception {
        // Initialize the database
        bodyTemperatureRepository.saveAndFlush(bodyTemperature);

        // Get all the bodyTemperatureList where empresaId does not contain DEFAULT_EMPRESA_ID
        defaultBodyTemperatureShouldNotBeFound("empresaId.doesNotContain=" + DEFAULT_EMPRESA_ID);

        // Get all the bodyTemperatureList where empresaId does not contain UPDATED_EMPRESA_ID
        defaultBodyTemperatureShouldBeFound("empresaId.doesNotContain=" + UPDATED_EMPRESA_ID);
    }

    @Test
    @Transactional
    void getAllBodyTemperaturesByFieldBodyTemperatureIsEqualToSomething() throws Exception {
        // Initialize the database
        bodyTemperatureRepository.saveAndFlush(bodyTemperature);

        // Get all the bodyTemperatureList where fieldBodyTemperature equals to DEFAULT_FIELD_BODY_TEMPERATURE
        defaultBodyTemperatureShouldBeFound("fieldBodyTemperature.equals=" + DEFAULT_FIELD_BODY_TEMPERATURE);

        // Get all the bodyTemperatureList where fieldBodyTemperature equals to UPDATED_FIELD_BODY_TEMPERATURE
        defaultBodyTemperatureShouldNotBeFound("fieldBodyTemperature.equals=" + UPDATED_FIELD_BODY_TEMPERATURE);
    }

    @Test
    @Transactional
    void getAllBodyTemperaturesByFieldBodyTemperatureIsInShouldWork() throws Exception {
        // Initialize the database
        bodyTemperatureRepository.saveAndFlush(bodyTemperature);

        // Get all the bodyTemperatureList where fieldBodyTemperature in DEFAULT_FIELD_BODY_TEMPERATURE or UPDATED_FIELD_BODY_TEMPERATURE
        defaultBodyTemperatureShouldBeFound(
            "fieldBodyTemperature.in=" + DEFAULT_FIELD_BODY_TEMPERATURE + "," + UPDATED_FIELD_BODY_TEMPERATURE
        );

        // Get all the bodyTemperatureList where fieldBodyTemperature equals to UPDATED_FIELD_BODY_TEMPERATURE
        defaultBodyTemperatureShouldNotBeFound("fieldBodyTemperature.in=" + UPDATED_FIELD_BODY_TEMPERATURE);
    }

    @Test
    @Transactional
    void getAllBodyTemperaturesByFieldBodyTemperatureIsNullOrNotNull() throws Exception {
        // Initialize the database
        bodyTemperatureRepository.saveAndFlush(bodyTemperature);

        // Get all the bodyTemperatureList where fieldBodyTemperature is not null
        defaultBodyTemperatureShouldBeFound("fieldBodyTemperature.specified=true");

        // Get all the bodyTemperatureList where fieldBodyTemperature is null
        defaultBodyTemperatureShouldNotBeFound("fieldBodyTemperature.specified=false");
    }

    @Test
    @Transactional
    void getAllBodyTemperaturesByFieldBodyTemperatureIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        bodyTemperatureRepository.saveAndFlush(bodyTemperature);

        // Get all the bodyTemperatureList where fieldBodyTemperature is greater than or equal to DEFAULT_FIELD_BODY_TEMPERATURE
        defaultBodyTemperatureShouldBeFound("fieldBodyTemperature.greaterThanOrEqual=" + DEFAULT_FIELD_BODY_TEMPERATURE);

        // Get all the bodyTemperatureList where fieldBodyTemperature is greater than or equal to UPDATED_FIELD_BODY_TEMPERATURE
        defaultBodyTemperatureShouldNotBeFound("fieldBodyTemperature.greaterThanOrEqual=" + UPDATED_FIELD_BODY_TEMPERATURE);
    }

    @Test
    @Transactional
    void getAllBodyTemperaturesByFieldBodyTemperatureIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        bodyTemperatureRepository.saveAndFlush(bodyTemperature);

        // Get all the bodyTemperatureList where fieldBodyTemperature is less than or equal to DEFAULT_FIELD_BODY_TEMPERATURE
        defaultBodyTemperatureShouldBeFound("fieldBodyTemperature.lessThanOrEqual=" + DEFAULT_FIELD_BODY_TEMPERATURE);

        // Get all the bodyTemperatureList where fieldBodyTemperature is less than or equal to SMALLER_FIELD_BODY_TEMPERATURE
        defaultBodyTemperatureShouldNotBeFound("fieldBodyTemperature.lessThanOrEqual=" + SMALLER_FIELD_BODY_TEMPERATURE);
    }

    @Test
    @Transactional
    void getAllBodyTemperaturesByFieldBodyTemperatureIsLessThanSomething() throws Exception {
        // Initialize the database
        bodyTemperatureRepository.saveAndFlush(bodyTemperature);

        // Get all the bodyTemperatureList where fieldBodyTemperature is less than DEFAULT_FIELD_BODY_TEMPERATURE
        defaultBodyTemperatureShouldNotBeFound("fieldBodyTemperature.lessThan=" + DEFAULT_FIELD_BODY_TEMPERATURE);

        // Get all the bodyTemperatureList where fieldBodyTemperature is less than UPDATED_FIELD_BODY_TEMPERATURE
        defaultBodyTemperatureShouldBeFound("fieldBodyTemperature.lessThan=" + UPDATED_FIELD_BODY_TEMPERATURE);
    }

    @Test
    @Transactional
    void getAllBodyTemperaturesByFieldBodyTemperatureIsGreaterThanSomething() throws Exception {
        // Initialize the database
        bodyTemperatureRepository.saveAndFlush(bodyTemperature);

        // Get all the bodyTemperatureList where fieldBodyTemperature is greater than DEFAULT_FIELD_BODY_TEMPERATURE
        defaultBodyTemperatureShouldNotBeFound("fieldBodyTemperature.greaterThan=" + DEFAULT_FIELD_BODY_TEMPERATURE);

        // Get all the bodyTemperatureList where fieldBodyTemperature is greater than SMALLER_FIELD_BODY_TEMPERATURE
        defaultBodyTemperatureShouldBeFound("fieldBodyTemperature.greaterThan=" + SMALLER_FIELD_BODY_TEMPERATURE);
    }

    @Test
    @Transactional
    void getAllBodyTemperaturesByFieldBodyTemperatureMeasureLocationIsEqualToSomething() throws Exception {
        // Initialize the database
        bodyTemperatureRepository.saveAndFlush(bodyTemperature);

        // Get all the bodyTemperatureList where fieldBodyTemperatureMeasureLocation equals to DEFAULT_FIELD_BODY_TEMPERATURE_MEASURE_LOCATION
        defaultBodyTemperatureShouldBeFound(
            "fieldBodyTemperatureMeasureLocation.equals=" + DEFAULT_FIELD_BODY_TEMPERATURE_MEASURE_LOCATION
        );

        // Get all the bodyTemperatureList where fieldBodyTemperatureMeasureLocation equals to UPDATED_FIELD_BODY_TEMPERATURE_MEASURE_LOCATION
        defaultBodyTemperatureShouldNotBeFound(
            "fieldBodyTemperatureMeasureLocation.equals=" + UPDATED_FIELD_BODY_TEMPERATURE_MEASURE_LOCATION
        );
    }

    @Test
    @Transactional
    void getAllBodyTemperaturesByFieldBodyTemperatureMeasureLocationIsInShouldWork() throws Exception {
        // Initialize the database
        bodyTemperatureRepository.saveAndFlush(bodyTemperature);

        // Get all the bodyTemperatureList where fieldBodyTemperatureMeasureLocation in DEFAULT_FIELD_BODY_TEMPERATURE_MEASURE_LOCATION or UPDATED_FIELD_BODY_TEMPERATURE_MEASURE_LOCATION
        defaultBodyTemperatureShouldBeFound(
            "fieldBodyTemperatureMeasureLocation.in=" +
            DEFAULT_FIELD_BODY_TEMPERATURE_MEASURE_LOCATION +
            "," +
            UPDATED_FIELD_BODY_TEMPERATURE_MEASURE_LOCATION
        );

        // Get all the bodyTemperatureList where fieldBodyTemperatureMeasureLocation equals to UPDATED_FIELD_BODY_TEMPERATURE_MEASURE_LOCATION
        defaultBodyTemperatureShouldNotBeFound("fieldBodyTemperatureMeasureLocation.in=" + UPDATED_FIELD_BODY_TEMPERATURE_MEASURE_LOCATION);
    }

    @Test
    @Transactional
    void getAllBodyTemperaturesByFieldBodyTemperatureMeasureLocationIsNullOrNotNull() throws Exception {
        // Initialize the database
        bodyTemperatureRepository.saveAndFlush(bodyTemperature);

        // Get all the bodyTemperatureList where fieldBodyTemperatureMeasureLocation is not null
        defaultBodyTemperatureShouldBeFound("fieldBodyTemperatureMeasureLocation.specified=true");

        // Get all the bodyTemperatureList where fieldBodyTemperatureMeasureLocation is null
        defaultBodyTemperatureShouldNotBeFound("fieldBodyTemperatureMeasureLocation.specified=false");
    }

    @Test
    @Transactional
    void getAllBodyTemperaturesByFieldBodyTemperatureMeasureLocationIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        bodyTemperatureRepository.saveAndFlush(bodyTemperature);

        // Get all the bodyTemperatureList where fieldBodyTemperatureMeasureLocation is greater than or equal to DEFAULT_FIELD_BODY_TEMPERATURE_MEASURE_LOCATION
        defaultBodyTemperatureShouldBeFound(
            "fieldBodyTemperatureMeasureLocation.greaterThanOrEqual=" + DEFAULT_FIELD_BODY_TEMPERATURE_MEASURE_LOCATION
        );

        // Get all the bodyTemperatureList where fieldBodyTemperatureMeasureLocation is greater than or equal to UPDATED_FIELD_BODY_TEMPERATURE_MEASURE_LOCATION
        defaultBodyTemperatureShouldNotBeFound(
            "fieldBodyTemperatureMeasureLocation.greaterThanOrEqual=" + UPDATED_FIELD_BODY_TEMPERATURE_MEASURE_LOCATION
        );
    }

    @Test
    @Transactional
    void getAllBodyTemperaturesByFieldBodyTemperatureMeasureLocationIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        bodyTemperatureRepository.saveAndFlush(bodyTemperature);

        // Get all the bodyTemperatureList where fieldBodyTemperatureMeasureLocation is less than or equal to DEFAULT_FIELD_BODY_TEMPERATURE_MEASURE_LOCATION
        defaultBodyTemperatureShouldBeFound(
            "fieldBodyTemperatureMeasureLocation.lessThanOrEqual=" + DEFAULT_FIELD_BODY_TEMPERATURE_MEASURE_LOCATION
        );

        // Get all the bodyTemperatureList where fieldBodyTemperatureMeasureLocation is less than or equal to SMALLER_FIELD_BODY_TEMPERATURE_MEASURE_LOCATION
        defaultBodyTemperatureShouldNotBeFound(
            "fieldBodyTemperatureMeasureLocation.lessThanOrEqual=" + SMALLER_FIELD_BODY_TEMPERATURE_MEASURE_LOCATION
        );
    }

    @Test
    @Transactional
    void getAllBodyTemperaturesByFieldBodyTemperatureMeasureLocationIsLessThanSomething() throws Exception {
        // Initialize the database
        bodyTemperatureRepository.saveAndFlush(bodyTemperature);

        // Get all the bodyTemperatureList where fieldBodyTemperatureMeasureLocation is less than DEFAULT_FIELD_BODY_TEMPERATURE_MEASURE_LOCATION
        defaultBodyTemperatureShouldNotBeFound(
            "fieldBodyTemperatureMeasureLocation.lessThan=" + DEFAULT_FIELD_BODY_TEMPERATURE_MEASURE_LOCATION
        );

        // Get all the bodyTemperatureList where fieldBodyTemperatureMeasureLocation is less than UPDATED_FIELD_BODY_TEMPERATURE_MEASURE_LOCATION
        defaultBodyTemperatureShouldBeFound(
            "fieldBodyTemperatureMeasureLocation.lessThan=" + UPDATED_FIELD_BODY_TEMPERATURE_MEASURE_LOCATION
        );
    }

    @Test
    @Transactional
    void getAllBodyTemperaturesByFieldBodyTemperatureMeasureLocationIsGreaterThanSomething() throws Exception {
        // Initialize the database
        bodyTemperatureRepository.saveAndFlush(bodyTemperature);

        // Get all the bodyTemperatureList where fieldBodyTemperatureMeasureLocation is greater than DEFAULT_FIELD_BODY_TEMPERATURE_MEASURE_LOCATION
        defaultBodyTemperatureShouldNotBeFound(
            "fieldBodyTemperatureMeasureLocation.greaterThan=" + DEFAULT_FIELD_BODY_TEMPERATURE_MEASURE_LOCATION
        );

        // Get all the bodyTemperatureList where fieldBodyTemperatureMeasureLocation is greater than SMALLER_FIELD_BODY_TEMPERATURE_MEASURE_LOCATION
        defaultBodyTemperatureShouldBeFound(
            "fieldBodyTemperatureMeasureLocation.greaterThan=" + SMALLER_FIELD_BODY_TEMPERATURE_MEASURE_LOCATION
        );
    }

    @Test
    @Transactional
    void getAllBodyTemperaturesByEndTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        bodyTemperatureRepository.saveAndFlush(bodyTemperature);

        // Get all the bodyTemperatureList where endTime equals to DEFAULT_END_TIME
        defaultBodyTemperatureShouldBeFound("endTime.equals=" + DEFAULT_END_TIME);

        // Get all the bodyTemperatureList where endTime equals to UPDATED_END_TIME
        defaultBodyTemperatureShouldNotBeFound("endTime.equals=" + UPDATED_END_TIME);
    }

    @Test
    @Transactional
    void getAllBodyTemperaturesByEndTimeIsInShouldWork() throws Exception {
        // Initialize the database
        bodyTemperatureRepository.saveAndFlush(bodyTemperature);

        // Get all the bodyTemperatureList where endTime in DEFAULT_END_TIME or UPDATED_END_TIME
        defaultBodyTemperatureShouldBeFound("endTime.in=" + DEFAULT_END_TIME + "," + UPDATED_END_TIME);

        // Get all the bodyTemperatureList where endTime equals to UPDATED_END_TIME
        defaultBodyTemperatureShouldNotBeFound("endTime.in=" + UPDATED_END_TIME);
    }

    @Test
    @Transactional
    void getAllBodyTemperaturesByEndTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        bodyTemperatureRepository.saveAndFlush(bodyTemperature);

        // Get all the bodyTemperatureList where endTime is not null
        defaultBodyTemperatureShouldBeFound("endTime.specified=true");

        // Get all the bodyTemperatureList where endTime is null
        defaultBodyTemperatureShouldNotBeFound("endTime.specified=false");
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultBodyTemperatureShouldBeFound(String filter) throws Exception {
        restBodyTemperatureMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bodyTemperature.getId().toString())))
            .andExpect(jsonPath("$.[*].usuarioId").value(hasItem(DEFAULT_USUARIO_ID)))
            .andExpect(jsonPath("$.[*].empresaId").value(hasItem(DEFAULT_EMPRESA_ID)))
            .andExpect(jsonPath("$.[*].fieldBodyTemperature").value(hasItem(DEFAULT_FIELD_BODY_TEMPERATURE.doubleValue())))
            .andExpect(
                jsonPath("$.[*].fieldBodyTemperatureMeasureLocation").value(hasItem(DEFAULT_FIELD_BODY_TEMPERATURE_MEASURE_LOCATION))
            )
            .andExpect(jsonPath("$.[*].endTime").value(hasItem(DEFAULT_END_TIME.toString())));

        // Check, that the count call also returns 1
        restBodyTemperatureMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultBodyTemperatureShouldNotBeFound(String filter) throws Exception {
        restBodyTemperatureMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restBodyTemperatureMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingBodyTemperature() throws Exception {
        // Get the bodyTemperature
        restBodyTemperatureMockMvc.perform(get(ENTITY_API_URL_ID, UUID.randomUUID().toString())).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingBodyTemperature() throws Exception {
        // Initialize the database
        bodyTemperatureRepository.saveAndFlush(bodyTemperature);

        int databaseSizeBeforeUpdate = bodyTemperatureRepository.findAll().size();

        // Update the bodyTemperature
        BodyTemperature updatedBodyTemperature = bodyTemperatureRepository.findById(bodyTemperature.getId()).get();
        // Disconnect from session so that the updates on updatedBodyTemperature are not directly saved in db
        em.detach(updatedBodyTemperature);
        updatedBodyTemperature
            .usuarioId(UPDATED_USUARIO_ID)
            .empresaId(UPDATED_EMPRESA_ID)
            .fieldBodyTemperature(UPDATED_FIELD_BODY_TEMPERATURE)
            .fieldBodyTemperatureMeasureLocation(UPDATED_FIELD_BODY_TEMPERATURE_MEASURE_LOCATION)
            .endTime(UPDATED_END_TIME);

        restBodyTemperatureMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedBodyTemperature.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedBodyTemperature))
            )
            .andExpect(status().isOk());

        // Validate the BodyTemperature in the database
        List<BodyTemperature> bodyTemperatureList = bodyTemperatureRepository.findAll();
        assertThat(bodyTemperatureList).hasSize(databaseSizeBeforeUpdate);
        BodyTemperature testBodyTemperature = bodyTemperatureList.get(bodyTemperatureList.size() - 1);
        assertThat(testBodyTemperature.getUsuarioId()).isEqualTo(UPDATED_USUARIO_ID);
        assertThat(testBodyTemperature.getEmpresaId()).isEqualTo(UPDATED_EMPRESA_ID);
        assertThat(testBodyTemperature.getFieldBodyTemperature()).isEqualTo(UPDATED_FIELD_BODY_TEMPERATURE);
        assertThat(testBodyTemperature.getFieldBodyTemperatureMeasureLocation()).isEqualTo(UPDATED_FIELD_BODY_TEMPERATURE_MEASURE_LOCATION);
        assertThat(testBodyTemperature.getEndTime()).isEqualTo(UPDATED_END_TIME);
    }

    @Test
    @Transactional
    void putNonExistingBodyTemperature() throws Exception {
        int databaseSizeBeforeUpdate = bodyTemperatureRepository.findAll().size();
        bodyTemperature.setId(UUID.randomUUID());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBodyTemperatureMockMvc
            .perform(
                put(ENTITY_API_URL_ID, bodyTemperature.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bodyTemperature))
            )
            .andExpect(status().isBadRequest());

        // Validate the BodyTemperature in the database
        List<BodyTemperature> bodyTemperatureList = bodyTemperatureRepository.findAll();
        assertThat(bodyTemperatureList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchBodyTemperature() throws Exception {
        int databaseSizeBeforeUpdate = bodyTemperatureRepository.findAll().size();
        bodyTemperature.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBodyTemperatureMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bodyTemperature))
            )
            .andExpect(status().isBadRequest());

        // Validate the BodyTemperature in the database
        List<BodyTemperature> bodyTemperatureList = bodyTemperatureRepository.findAll();
        assertThat(bodyTemperatureList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamBodyTemperature() throws Exception {
        int databaseSizeBeforeUpdate = bodyTemperatureRepository.findAll().size();
        bodyTemperature.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBodyTemperatureMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bodyTemperature))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the BodyTemperature in the database
        List<BodyTemperature> bodyTemperatureList = bodyTemperatureRepository.findAll();
        assertThat(bodyTemperatureList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateBodyTemperatureWithPatch() throws Exception {
        // Initialize the database
        bodyTemperatureRepository.saveAndFlush(bodyTemperature);

        int databaseSizeBeforeUpdate = bodyTemperatureRepository.findAll().size();

        // Update the bodyTemperature using partial update
        BodyTemperature partialUpdatedBodyTemperature = new BodyTemperature();
        partialUpdatedBodyTemperature.setId(bodyTemperature.getId());

        partialUpdatedBodyTemperature
            .usuarioId(UPDATED_USUARIO_ID)
            .fieldBodyTemperatureMeasureLocation(UPDATED_FIELD_BODY_TEMPERATURE_MEASURE_LOCATION)
            .endTime(UPDATED_END_TIME);

        restBodyTemperatureMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBodyTemperature.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBodyTemperature))
            )
            .andExpect(status().isOk());

        // Validate the BodyTemperature in the database
        List<BodyTemperature> bodyTemperatureList = bodyTemperatureRepository.findAll();
        assertThat(bodyTemperatureList).hasSize(databaseSizeBeforeUpdate);
        BodyTemperature testBodyTemperature = bodyTemperatureList.get(bodyTemperatureList.size() - 1);
        assertThat(testBodyTemperature.getUsuarioId()).isEqualTo(UPDATED_USUARIO_ID);
        assertThat(testBodyTemperature.getEmpresaId()).isEqualTo(DEFAULT_EMPRESA_ID);
        assertThat(testBodyTemperature.getFieldBodyTemperature()).isEqualTo(DEFAULT_FIELD_BODY_TEMPERATURE);
        assertThat(testBodyTemperature.getFieldBodyTemperatureMeasureLocation()).isEqualTo(UPDATED_FIELD_BODY_TEMPERATURE_MEASURE_LOCATION);
        assertThat(testBodyTemperature.getEndTime()).isEqualTo(UPDATED_END_TIME);
    }

    @Test
    @Transactional
    void fullUpdateBodyTemperatureWithPatch() throws Exception {
        // Initialize the database
        bodyTemperatureRepository.saveAndFlush(bodyTemperature);

        int databaseSizeBeforeUpdate = bodyTemperatureRepository.findAll().size();

        // Update the bodyTemperature using partial update
        BodyTemperature partialUpdatedBodyTemperature = new BodyTemperature();
        partialUpdatedBodyTemperature.setId(bodyTemperature.getId());

        partialUpdatedBodyTemperature
            .usuarioId(UPDATED_USUARIO_ID)
            .empresaId(UPDATED_EMPRESA_ID)
            .fieldBodyTemperature(UPDATED_FIELD_BODY_TEMPERATURE)
            .fieldBodyTemperatureMeasureLocation(UPDATED_FIELD_BODY_TEMPERATURE_MEASURE_LOCATION)
            .endTime(UPDATED_END_TIME);

        restBodyTemperatureMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBodyTemperature.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBodyTemperature))
            )
            .andExpect(status().isOk());

        // Validate the BodyTemperature in the database
        List<BodyTemperature> bodyTemperatureList = bodyTemperatureRepository.findAll();
        assertThat(bodyTemperatureList).hasSize(databaseSizeBeforeUpdate);
        BodyTemperature testBodyTemperature = bodyTemperatureList.get(bodyTemperatureList.size() - 1);
        assertThat(testBodyTemperature.getUsuarioId()).isEqualTo(UPDATED_USUARIO_ID);
        assertThat(testBodyTemperature.getEmpresaId()).isEqualTo(UPDATED_EMPRESA_ID);
        assertThat(testBodyTemperature.getFieldBodyTemperature()).isEqualTo(UPDATED_FIELD_BODY_TEMPERATURE);
        assertThat(testBodyTemperature.getFieldBodyTemperatureMeasureLocation()).isEqualTo(UPDATED_FIELD_BODY_TEMPERATURE_MEASURE_LOCATION);
        assertThat(testBodyTemperature.getEndTime()).isEqualTo(UPDATED_END_TIME);
    }

    @Test
    @Transactional
    void patchNonExistingBodyTemperature() throws Exception {
        int databaseSizeBeforeUpdate = bodyTemperatureRepository.findAll().size();
        bodyTemperature.setId(UUID.randomUUID());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBodyTemperatureMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, bodyTemperature.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(bodyTemperature))
            )
            .andExpect(status().isBadRequest());

        // Validate the BodyTemperature in the database
        List<BodyTemperature> bodyTemperatureList = bodyTemperatureRepository.findAll();
        assertThat(bodyTemperatureList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchBodyTemperature() throws Exception {
        int databaseSizeBeforeUpdate = bodyTemperatureRepository.findAll().size();
        bodyTemperature.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBodyTemperatureMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(bodyTemperature))
            )
            .andExpect(status().isBadRequest());

        // Validate the BodyTemperature in the database
        List<BodyTemperature> bodyTemperatureList = bodyTemperatureRepository.findAll();
        assertThat(bodyTemperatureList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamBodyTemperature() throws Exception {
        int databaseSizeBeforeUpdate = bodyTemperatureRepository.findAll().size();
        bodyTemperature.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBodyTemperatureMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(bodyTemperature))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the BodyTemperature in the database
        List<BodyTemperature> bodyTemperatureList = bodyTemperatureRepository.findAll();
        assertThat(bodyTemperatureList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteBodyTemperature() throws Exception {
        // Initialize the database
        bodyTemperatureRepository.saveAndFlush(bodyTemperature);

        int databaseSizeBeforeDelete = bodyTemperatureRepository.findAll().size();

        // Delete the bodyTemperature
        restBodyTemperatureMockMvc
            .perform(delete(ENTITY_API_URL_ID, bodyTemperature.getId().toString()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<BodyTemperature> bodyTemperatureList = bodyTemperatureRepository.findAll();
        assertThat(bodyTemperatureList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
