package com.be4tech.b4carecollect.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.be4tech.b4carecollect.IntegrationTest;
import com.be4tech.b4carecollect.domain.TemperatureSummary;
import com.be4tech.b4carecollect.repository.TemperatureSummaryRepository;
import com.be4tech.b4carecollect.service.criteria.TemperatureSummaryCriteria;
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
 * Integration tests for the {@link TemperatureSummaryResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TemperatureSummaryResourceIT {

    private static final String DEFAULT_USUARIO_ID = "AAAAAAAAAA";
    private static final String UPDATED_USUARIO_ID = "BBBBBBBBBB";

    private static final String DEFAULT_EMPRESA_ID = "AAAAAAAAAA";
    private static final String UPDATED_EMPRESA_ID = "BBBBBBBBBB";

    private static final Float DEFAULT_FIELD_AVERAGE = 1F;
    private static final Float UPDATED_FIELD_AVERAGE = 2F;
    private static final Float SMALLER_FIELD_AVERAGE = 1F - 1F;

    private static final Float DEFAULT_FIELD_MAX = 1F;
    private static final Float UPDATED_FIELD_MAX = 2F;
    private static final Float SMALLER_FIELD_MAX = 1F - 1F;

    private static final Float DEFAULT_FIELD_MIN = 1F;
    private static final Float UPDATED_FIELD_MIN = 2F;
    private static final Float SMALLER_FIELD_MIN = 1F - 1F;

    private static final Integer DEFAULT_MEASUREMENT_LOCATION = 1;
    private static final Integer UPDATED_MEASUREMENT_LOCATION = 2;
    private static final Integer SMALLER_MEASUREMENT_LOCATION = 1 - 1;

    private static final Instant DEFAULT_START_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_START_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_END_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_END_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/temperature-summaries";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private TemperatureSummaryRepository temperatureSummaryRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTemperatureSummaryMockMvc;

    private TemperatureSummary temperatureSummary;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TemperatureSummary createEntity(EntityManager em) {
        TemperatureSummary temperatureSummary = new TemperatureSummary()
            .usuarioId(DEFAULT_USUARIO_ID)
            .empresaId(DEFAULT_EMPRESA_ID)
            .fieldAverage(DEFAULT_FIELD_AVERAGE)
            .fieldMax(DEFAULT_FIELD_MAX)
            .fieldMin(DEFAULT_FIELD_MIN)
            .measurementLocation(DEFAULT_MEASUREMENT_LOCATION)
            .startTime(DEFAULT_START_TIME)
            .endTime(DEFAULT_END_TIME);
        return temperatureSummary;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TemperatureSummary createUpdatedEntity(EntityManager em) {
        TemperatureSummary temperatureSummary = new TemperatureSummary()
            .usuarioId(UPDATED_USUARIO_ID)
            .empresaId(UPDATED_EMPRESA_ID)
            .fieldAverage(UPDATED_FIELD_AVERAGE)
            .fieldMax(UPDATED_FIELD_MAX)
            .fieldMin(UPDATED_FIELD_MIN)
            .measurementLocation(UPDATED_MEASUREMENT_LOCATION)
            .startTime(UPDATED_START_TIME)
            .endTime(UPDATED_END_TIME);
        return temperatureSummary;
    }

    @BeforeEach
    public void initTest() {
        temperatureSummary = createEntity(em);
    }

    @Test
    @Transactional
    void createTemperatureSummary() throws Exception {
        int databaseSizeBeforeCreate = temperatureSummaryRepository.findAll().size();
        // Create the TemperatureSummary
        restTemperatureSummaryMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(temperatureSummary))
            )
            .andExpect(status().isCreated());

        // Validate the TemperatureSummary in the database
        List<TemperatureSummary> temperatureSummaryList = temperatureSummaryRepository.findAll();
        assertThat(temperatureSummaryList).hasSize(databaseSizeBeforeCreate + 1);
        TemperatureSummary testTemperatureSummary = temperatureSummaryList.get(temperatureSummaryList.size() - 1);
        assertThat(testTemperatureSummary.getUsuarioId()).isEqualTo(DEFAULT_USUARIO_ID);
        assertThat(testTemperatureSummary.getEmpresaId()).isEqualTo(DEFAULT_EMPRESA_ID);
        assertThat(testTemperatureSummary.getFieldAverage()).isEqualTo(DEFAULT_FIELD_AVERAGE);
        assertThat(testTemperatureSummary.getFieldMax()).isEqualTo(DEFAULT_FIELD_MAX);
        assertThat(testTemperatureSummary.getFieldMin()).isEqualTo(DEFAULT_FIELD_MIN);
        assertThat(testTemperatureSummary.getMeasurementLocation()).isEqualTo(DEFAULT_MEASUREMENT_LOCATION);
        assertThat(testTemperatureSummary.getStartTime()).isEqualTo(DEFAULT_START_TIME);
        assertThat(testTemperatureSummary.getEndTime()).isEqualTo(DEFAULT_END_TIME);
    }

    @Test
    @Transactional
    void createTemperatureSummaryWithExistingId() throws Exception {
        // Create the TemperatureSummary with an existing ID
        temperatureSummaryRepository.saveAndFlush(temperatureSummary);

        int databaseSizeBeforeCreate = temperatureSummaryRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTemperatureSummaryMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(temperatureSummary))
            )
            .andExpect(status().isBadRequest());

        // Validate the TemperatureSummary in the database
        List<TemperatureSummary> temperatureSummaryList = temperatureSummaryRepository.findAll();
        assertThat(temperatureSummaryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllTemperatureSummaries() throws Exception {
        // Initialize the database
        temperatureSummaryRepository.saveAndFlush(temperatureSummary);

        // Get all the temperatureSummaryList
        restTemperatureSummaryMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(temperatureSummary.getId().toString())))
            .andExpect(jsonPath("$.[*].usuarioId").value(hasItem(DEFAULT_USUARIO_ID)))
            .andExpect(jsonPath("$.[*].empresaId").value(hasItem(DEFAULT_EMPRESA_ID)))
            .andExpect(jsonPath("$.[*].fieldAverage").value(hasItem(DEFAULT_FIELD_AVERAGE.doubleValue())))
            .andExpect(jsonPath("$.[*].fieldMax").value(hasItem(DEFAULT_FIELD_MAX.doubleValue())))
            .andExpect(jsonPath("$.[*].fieldMin").value(hasItem(DEFAULT_FIELD_MIN.doubleValue())))
            .andExpect(jsonPath("$.[*].measurementLocation").value(hasItem(DEFAULT_MEASUREMENT_LOCATION)))
            .andExpect(jsonPath("$.[*].startTime").value(hasItem(DEFAULT_START_TIME.toString())))
            .andExpect(jsonPath("$.[*].endTime").value(hasItem(DEFAULT_END_TIME.toString())));
    }

    @Test
    @Transactional
    void getTemperatureSummary() throws Exception {
        // Initialize the database
        temperatureSummaryRepository.saveAndFlush(temperatureSummary);

        // Get the temperatureSummary
        restTemperatureSummaryMockMvc
            .perform(get(ENTITY_API_URL_ID, temperatureSummary.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(temperatureSummary.getId().toString()))
            .andExpect(jsonPath("$.usuarioId").value(DEFAULT_USUARIO_ID))
            .andExpect(jsonPath("$.empresaId").value(DEFAULT_EMPRESA_ID))
            .andExpect(jsonPath("$.fieldAverage").value(DEFAULT_FIELD_AVERAGE.doubleValue()))
            .andExpect(jsonPath("$.fieldMax").value(DEFAULT_FIELD_MAX.doubleValue()))
            .andExpect(jsonPath("$.fieldMin").value(DEFAULT_FIELD_MIN.doubleValue()))
            .andExpect(jsonPath("$.measurementLocation").value(DEFAULT_MEASUREMENT_LOCATION))
            .andExpect(jsonPath("$.startTime").value(DEFAULT_START_TIME.toString()))
            .andExpect(jsonPath("$.endTime").value(DEFAULT_END_TIME.toString()));
    }

    @Test
    @Transactional
    void getTemperatureSummariesByIdFiltering() throws Exception {
        // Initialize the database
        temperatureSummaryRepository.saveAndFlush(temperatureSummary);

        UUID id = temperatureSummary.getId();

        defaultTemperatureSummaryShouldBeFound("id.equals=" + id);
        defaultTemperatureSummaryShouldNotBeFound("id.notEquals=" + id);
    }

    @Test
    @Transactional
    void getAllTemperatureSummariesByUsuarioIdIsEqualToSomething() throws Exception {
        // Initialize the database
        temperatureSummaryRepository.saveAndFlush(temperatureSummary);

        // Get all the temperatureSummaryList where usuarioId equals to DEFAULT_USUARIO_ID
        defaultTemperatureSummaryShouldBeFound("usuarioId.equals=" + DEFAULT_USUARIO_ID);

        // Get all the temperatureSummaryList where usuarioId equals to UPDATED_USUARIO_ID
        defaultTemperatureSummaryShouldNotBeFound("usuarioId.equals=" + UPDATED_USUARIO_ID);
    }

    @Test
    @Transactional
    void getAllTemperatureSummariesByUsuarioIdIsInShouldWork() throws Exception {
        // Initialize the database
        temperatureSummaryRepository.saveAndFlush(temperatureSummary);

        // Get all the temperatureSummaryList where usuarioId in DEFAULT_USUARIO_ID or UPDATED_USUARIO_ID
        defaultTemperatureSummaryShouldBeFound("usuarioId.in=" + DEFAULT_USUARIO_ID + "," + UPDATED_USUARIO_ID);

        // Get all the temperatureSummaryList where usuarioId equals to UPDATED_USUARIO_ID
        defaultTemperatureSummaryShouldNotBeFound("usuarioId.in=" + UPDATED_USUARIO_ID);
    }

    @Test
    @Transactional
    void getAllTemperatureSummariesByUsuarioIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        temperatureSummaryRepository.saveAndFlush(temperatureSummary);

        // Get all the temperatureSummaryList where usuarioId is not null
        defaultTemperatureSummaryShouldBeFound("usuarioId.specified=true");

        // Get all the temperatureSummaryList where usuarioId is null
        defaultTemperatureSummaryShouldNotBeFound("usuarioId.specified=false");
    }

    @Test
    @Transactional
    void getAllTemperatureSummariesByUsuarioIdContainsSomething() throws Exception {
        // Initialize the database
        temperatureSummaryRepository.saveAndFlush(temperatureSummary);

        // Get all the temperatureSummaryList where usuarioId contains DEFAULT_USUARIO_ID
        defaultTemperatureSummaryShouldBeFound("usuarioId.contains=" + DEFAULT_USUARIO_ID);

        // Get all the temperatureSummaryList where usuarioId contains UPDATED_USUARIO_ID
        defaultTemperatureSummaryShouldNotBeFound("usuarioId.contains=" + UPDATED_USUARIO_ID);
    }

    @Test
    @Transactional
    void getAllTemperatureSummariesByUsuarioIdNotContainsSomething() throws Exception {
        // Initialize the database
        temperatureSummaryRepository.saveAndFlush(temperatureSummary);

        // Get all the temperatureSummaryList where usuarioId does not contain DEFAULT_USUARIO_ID
        defaultTemperatureSummaryShouldNotBeFound("usuarioId.doesNotContain=" + DEFAULT_USUARIO_ID);

        // Get all the temperatureSummaryList where usuarioId does not contain UPDATED_USUARIO_ID
        defaultTemperatureSummaryShouldBeFound("usuarioId.doesNotContain=" + UPDATED_USUARIO_ID);
    }

    @Test
    @Transactional
    void getAllTemperatureSummariesByEmpresaIdIsEqualToSomething() throws Exception {
        // Initialize the database
        temperatureSummaryRepository.saveAndFlush(temperatureSummary);

        // Get all the temperatureSummaryList where empresaId equals to DEFAULT_EMPRESA_ID
        defaultTemperatureSummaryShouldBeFound("empresaId.equals=" + DEFAULT_EMPRESA_ID);

        // Get all the temperatureSummaryList where empresaId equals to UPDATED_EMPRESA_ID
        defaultTemperatureSummaryShouldNotBeFound("empresaId.equals=" + UPDATED_EMPRESA_ID);
    }

    @Test
    @Transactional
    void getAllTemperatureSummariesByEmpresaIdIsInShouldWork() throws Exception {
        // Initialize the database
        temperatureSummaryRepository.saveAndFlush(temperatureSummary);

        // Get all the temperatureSummaryList where empresaId in DEFAULT_EMPRESA_ID or UPDATED_EMPRESA_ID
        defaultTemperatureSummaryShouldBeFound("empresaId.in=" + DEFAULT_EMPRESA_ID + "," + UPDATED_EMPRESA_ID);

        // Get all the temperatureSummaryList where empresaId equals to UPDATED_EMPRESA_ID
        defaultTemperatureSummaryShouldNotBeFound("empresaId.in=" + UPDATED_EMPRESA_ID);
    }

    @Test
    @Transactional
    void getAllTemperatureSummariesByEmpresaIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        temperatureSummaryRepository.saveAndFlush(temperatureSummary);

        // Get all the temperatureSummaryList where empresaId is not null
        defaultTemperatureSummaryShouldBeFound("empresaId.specified=true");

        // Get all the temperatureSummaryList where empresaId is null
        defaultTemperatureSummaryShouldNotBeFound("empresaId.specified=false");
    }

    @Test
    @Transactional
    void getAllTemperatureSummariesByEmpresaIdContainsSomething() throws Exception {
        // Initialize the database
        temperatureSummaryRepository.saveAndFlush(temperatureSummary);

        // Get all the temperatureSummaryList where empresaId contains DEFAULT_EMPRESA_ID
        defaultTemperatureSummaryShouldBeFound("empresaId.contains=" + DEFAULT_EMPRESA_ID);

        // Get all the temperatureSummaryList where empresaId contains UPDATED_EMPRESA_ID
        defaultTemperatureSummaryShouldNotBeFound("empresaId.contains=" + UPDATED_EMPRESA_ID);
    }

    @Test
    @Transactional
    void getAllTemperatureSummariesByEmpresaIdNotContainsSomething() throws Exception {
        // Initialize the database
        temperatureSummaryRepository.saveAndFlush(temperatureSummary);

        // Get all the temperatureSummaryList where empresaId does not contain DEFAULT_EMPRESA_ID
        defaultTemperatureSummaryShouldNotBeFound("empresaId.doesNotContain=" + DEFAULT_EMPRESA_ID);

        // Get all the temperatureSummaryList where empresaId does not contain UPDATED_EMPRESA_ID
        defaultTemperatureSummaryShouldBeFound("empresaId.doesNotContain=" + UPDATED_EMPRESA_ID);
    }

    @Test
    @Transactional
    void getAllTemperatureSummariesByFieldAverageIsEqualToSomething() throws Exception {
        // Initialize the database
        temperatureSummaryRepository.saveAndFlush(temperatureSummary);

        // Get all the temperatureSummaryList where fieldAverage equals to DEFAULT_FIELD_AVERAGE
        defaultTemperatureSummaryShouldBeFound("fieldAverage.equals=" + DEFAULT_FIELD_AVERAGE);

        // Get all the temperatureSummaryList where fieldAverage equals to UPDATED_FIELD_AVERAGE
        defaultTemperatureSummaryShouldNotBeFound("fieldAverage.equals=" + UPDATED_FIELD_AVERAGE);
    }

    @Test
    @Transactional
    void getAllTemperatureSummariesByFieldAverageIsInShouldWork() throws Exception {
        // Initialize the database
        temperatureSummaryRepository.saveAndFlush(temperatureSummary);

        // Get all the temperatureSummaryList where fieldAverage in DEFAULT_FIELD_AVERAGE or UPDATED_FIELD_AVERAGE
        defaultTemperatureSummaryShouldBeFound("fieldAverage.in=" + DEFAULT_FIELD_AVERAGE + "," + UPDATED_FIELD_AVERAGE);

        // Get all the temperatureSummaryList where fieldAverage equals to UPDATED_FIELD_AVERAGE
        defaultTemperatureSummaryShouldNotBeFound("fieldAverage.in=" + UPDATED_FIELD_AVERAGE);
    }

    @Test
    @Transactional
    void getAllTemperatureSummariesByFieldAverageIsNullOrNotNull() throws Exception {
        // Initialize the database
        temperatureSummaryRepository.saveAndFlush(temperatureSummary);

        // Get all the temperatureSummaryList where fieldAverage is not null
        defaultTemperatureSummaryShouldBeFound("fieldAverage.specified=true");

        // Get all the temperatureSummaryList where fieldAverage is null
        defaultTemperatureSummaryShouldNotBeFound("fieldAverage.specified=false");
    }

    @Test
    @Transactional
    void getAllTemperatureSummariesByFieldAverageIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        temperatureSummaryRepository.saveAndFlush(temperatureSummary);

        // Get all the temperatureSummaryList where fieldAverage is greater than or equal to DEFAULT_FIELD_AVERAGE
        defaultTemperatureSummaryShouldBeFound("fieldAverage.greaterThanOrEqual=" + DEFAULT_FIELD_AVERAGE);

        // Get all the temperatureSummaryList where fieldAverage is greater than or equal to UPDATED_FIELD_AVERAGE
        defaultTemperatureSummaryShouldNotBeFound("fieldAverage.greaterThanOrEqual=" + UPDATED_FIELD_AVERAGE);
    }

    @Test
    @Transactional
    void getAllTemperatureSummariesByFieldAverageIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        temperatureSummaryRepository.saveAndFlush(temperatureSummary);

        // Get all the temperatureSummaryList where fieldAverage is less than or equal to DEFAULT_FIELD_AVERAGE
        defaultTemperatureSummaryShouldBeFound("fieldAverage.lessThanOrEqual=" + DEFAULT_FIELD_AVERAGE);

        // Get all the temperatureSummaryList where fieldAverage is less than or equal to SMALLER_FIELD_AVERAGE
        defaultTemperatureSummaryShouldNotBeFound("fieldAverage.lessThanOrEqual=" + SMALLER_FIELD_AVERAGE);
    }

    @Test
    @Transactional
    void getAllTemperatureSummariesByFieldAverageIsLessThanSomething() throws Exception {
        // Initialize the database
        temperatureSummaryRepository.saveAndFlush(temperatureSummary);

        // Get all the temperatureSummaryList where fieldAverage is less than DEFAULT_FIELD_AVERAGE
        defaultTemperatureSummaryShouldNotBeFound("fieldAverage.lessThan=" + DEFAULT_FIELD_AVERAGE);

        // Get all the temperatureSummaryList where fieldAverage is less than UPDATED_FIELD_AVERAGE
        defaultTemperatureSummaryShouldBeFound("fieldAverage.lessThan=" + UPDATED_FIELD_AVERAGE);
    }

    @Test
    @Transactional
    void getAllTemperatureSummariesByFieldAverageIsGreaterThanSomething() throws Exception {
        // Initialize the database
        temperatureSummaryRepository.saveAndFlush(temperatureSummary);

        // Get all the temperatureSummaryList where fieldAverage is greater than DEFAULT_FIELD_AVERAGE
        defaultTemperatureSummaryShouldNotBeFound("fieldAverage.greaterThan=" + DEFAULT_FIELD_AVERAGE);

        // Get all the temperatureSummaryList where fieldAverage is greater than SMALLER_FIELD_AVERAGE
        defaultTemperatureSummaryShouldBeFound("fieldAverage.greaterThan=" + SMALLER_FIELD_AVERAGE);
    }

    @Test
    @Transactional
    void getAllTemperatureSummariesByFieldMaxIsEqualToSomething() throws Exception {
        // Initialize the database
        temperatureSummaryRepository.saveAndFlush(temperatureSummary);

        // Get all the temperatureSummaryList where fieldMax equals to DEFAULT_FIELD_MAX
        defaultTemperatureSummaryShouldBeFound("fieldMax.equals=" + DEFAULT_FIELD_MAX);

        // Get all the temperatureSummaryList where fieldMax equals to UPDATED_FIELD_MAX
        defaultTemperatureSummaryShouldNotBeFound("fieldMax.equals=" + UPDATED_FIELD_MAX);
    }

    @Test
    @Transactional
    void getAllTemperatureSummariesByFieldMaxIsInShouldWork() throws Exception {
        // Initialize the database
        temperatureSummaryRepository.saveAndFlush(temperatureSummary);

        // Get all the temperatureSummaryList where fieldMax in DEFAULT_FIELD_MAX or UPDATED_FIELD_MAX
        defaultTemperatureSummaryShouldBeFound("fieldMax.in=" + DEFAULT_FIELD_MAX + "," + UPDATED_FIELD_MAX);

        // Get all the temperatureSummaryList where fieldMax equals to UPDATED_FIELD_MAX
        defaultTemperatureSummaryShouldNotBeFound("fieldMax.in=" + UPDATED_FIELD_MAX);
    }

    @Test
    @Transactional
    void getAllTemperatureSummariesByFieldMaxIsNullOrNotNull() throws Exception {
        // Initialize the database
        temperatureSummaryRepository.saveAndFlush(temperatureSummary);

        // Get all the temperatureSummaryList where fieldMax is not null
        defaultTemperatureSummaryShouldBeFound("fieldMax.specified=true");

        // Get all the temperatureSummaryList where fieldMax is null
        defaultTemperatureSummaryShouldNotBeFound("fieldMax.specified=false");
    }

    @Test
    @Transactional
    void getAllTemperatureSummariesByFieldMaxIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        temperatureSummaryRepository.saveAndFlush(temperatureSummary);

        // Get all the temperatureSummaryList where fieldMax is greater than or equal to DEFAULT_FIELD_MAX
        defaultTemperatureSummaryShouldBeFound("fieldMax.greaterThanOrEqual=" + DEFAULT_FIELD_MAX);

        // Get all the temperatureSummaryList where fieldMax is greater than or equal to UPDATED_FIELD_MAX
        defaultTemperatureSummaryShouldNotBeFound("fieldMax.greaterThanOrEqual=" + UPDATED_FIELD_MAX);
    }

    @Test
    @Transactional
    void getAllTemperatureSummariesByFieldMaxIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        temperatureSummaryRepository.saveAndFlush(temperatureSummary);

        // Get all the temperatureSummaryList where fieldMax is less than or equal to DEFAULT_FIELD_MAX
        defaultTemperatureSummaryShouldBeFound("fieldMax.lessThanOrEqual=" + DEFAULT_FIELD_MAX);

        // Get all the temperatureSummaryList where fieldMax is less than or equal to SMALLER_FIELD_MAX
        defaultTemperatureSummaryShouldNotBeFound("fieldMax.lessThanOrEqual=" + SMALLER_FIELD_MAX);
    }

    @Test
    @Transactional
    void getAllTemperatureSummariesByFieldMaxIsLessThanSomething() throws Exception {
        // Initialize the database
        temperatureSummaryRepository.saveAndFlush(temperatureSummary);

        // Get all the temperatureSummaryList where fieldMax is less than DEFAULT_FIELD_MAX
        defaultTemperatureSummaryShouldNotBeFound("fieldMax.lessThan=" + DEFAULT_FIELD_MAX);

        // Get all the temperatureSummaryList where fieldMax is less than UPDATED_FIELD_MAX
        defaultTemperatureSummaryShouldBeFound("fieldMax.lessThan=" + UPDATED_FIELD_MAX);
    }

    @Test
    @Transactional
    void getAllTemperatureSummariesByFieldMaxIsGreaterThanSomething() throws Exception {
        // Initialize the database
        temperatureSummaryRepository.saveAndFlush(temperatureSummary);

        // Get all the temperatureSummaryList where fieldMax is greater than DEFAULT_FIELD_MAX
        defaultTemperatureSummaryShouldNotBeFound("fieldMax.greaterThan=" + DEFAULT_FIELD_MAX);

        // Get all the temperatureSummaryList where fieldMax is greater than SMALLER_FIELD_MAX
        defaultTemperatureSummaryShouldBeFound("fieldMax.greaterThan=" + SMALLER_FIELD_MAX);
    }

    @Test
    @Transactional
    void getAllTemperatureSummariesByFieldMinIsEqualToSomething() throws Exception {
        // Initialize the database
        temperatureSummaryRepository.saveAndFlush(temperatureSummary);

        // Get all the temperatureSummaryList where fieldMin equals to DEFAULT_FIELD_MIN
        defaultTemperatureSummaryShouldBeFound("fieldMin.equals=" + DEFAULT_FIELD_MIN);

        // Get all the temperatureSummaryList where fieldMin equals to UPDATED_FIELD_MIN
        defaultTemperatureSummaryShouldNotBeFound("fieldMin.equals=" + UPDATED_FIELD_MIN);
    }

    @Test
    @Transactional
    void getAllTemperatureSummariesByFieldMinIsInShouldWork() throws Exception {
        // Initialize the database
        temperatureSummaryRepository.saveAndFlush(temperatureSummary);

        // Get all the temperatureSummaryList where fieldMin in DEFAULT_FIELD_MIN or UPDATED_FIELD_MIN
        defaultTemperatureSummaryShouldBeFound("fieldMin.in=" + DEFAULT_FIELD_MIN + "," + UPDATED_FIELD_MIN);

        // Get all the temperatureSummaryList where fieldMin equals to UPDATED_FIELD_MIN
        defaultTemperatureSummaryShouldNotBeFound("fieldMin.in=" + UPDATED_FIELD_MIN);
    }

    @Test
    @Transactional
    void getAllTemperatureSummariesByFieldMinIsNullOrNotNull() throws Exception {
        // Initialize the database
        temperatureSummaryRepository.saveAndFlush(temperatureSummary);

        // Get all the temperatureSummaryList where fieldMin is not null
        defaultTemperatureSummaryShouldBeFound("fieldMin.specified=true");

        // Get all the temperatureSummaryList where fieldMin is null
        defaultTemperatureSummaryShouldNotBeFound("fieldMin.specified=false");
    }

    @Test
    @Transactional
    void getAllTemperatureSummariesByFieldMinIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        temperatureSummaryRepository.saveAndFlush(temperatureSummary);

        // Get all the temperatureSummaryList where fieldMin is greater than or equal to DEFAULT_FIELD_MIN
        defaultTemperatureSummaryShouldBeFound("fieldMin.greaterThanOrEqual=" + DEFAULT_FIELD_MIN);

        // Get all the temperatureSummaryList where fieldMin is greater than or equal to UPDATED_FIELD_MIN
        defaultTemperatureSummaryShouldNotBeFound("fieldMin.greaterThanOrEqual=" + UPDATED_FIELD_MIN);
    }

    @Test
    @Transactional
    void getAllTemperatureSummariesByFieldMinIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        temperatureSummaryRepository.saveAndFlush(temperatureSummary);

        // Get all the temperatureSummaryList where fieldMin is less than or equal to DEFAULT_FIELD_MIN
        defaultTemperatureSummaryShouldBeFound("fieldMin.lessThanOrEqual=" + DEFAULT_FIELD_MIN);

        // Get all the temperatureSummaryList where fieldMin is less than or equal to SMALLER_FIELD_MIN
        defaultTemperatureSummaryShouldNotBeFound("fieldMin.lessThanOrEqual=" + SMALLER_FIELD_MIN);
    }

    @Test
    @Transactional
    void getAllTemperatureSummariesByFieldMinIsLessThanSomething() throws Exception {
        // Initialize the database
        temperatureSummaryRepository.saveAndFlush(temperatureSummary);

        // Get all the temperatureSummaryList where fieldMin is less than DEFAULT_FIELD_MIN
        defaultTemperatureSummaryShouldNotBeFound("fieldMin.lessThan=" + DEFAULT_FIELD_MIN);

        // Get all the temperatureSummaryList where fieldMin is less than UPDATED_FIELD_MIN
        defaultTemperatureSummaryShouldBeFound("fieldMin.lessThan=" + UPDATED_FIELD_MIN);
    }

    @Test
    @Transactional
    void getAllTemperatureSummariesByFieldMinIsGreaterThanSomething() throws Exception {
        // Initialize the database
        temperatureSummaryRepository.saveAndFlush(temperatureSummary);

        // Get all the temperatureSummaryList where fieldMin is greater than DEFAULT_FIELD_MIN
        defaultTemperatureSummaryShouldNotBeFound("fieldMin.greaterThan=" + DEFAULT_FIELD_MIN);

        // Get all the temperatureSummaryList where fieldMin is greater than SMALLER_FIELD_MIN
        defaultTemperatureSummaryShouldBeFound("fieldMin.greaterThan=" + SMALLER_FIELD_MIN);
    }

    @Test
    @Transactional
    void getAllTemperatureSummariesByMeasurementLocationIsEqualToSomething() throws Exception {
        // Initialize the database
        temperatureSummaryRepository.saveAndFlush(temperatureSummary);

        // Get all the temperatureSummaryList where measurementLocation equals to DEFAULT_MEASUREMENT_LOCATION
        defaultTemperatureSummaryShouldBeFound("measurementLocation.equals=" + DEFAULT_MEASUREMENT_LOCATION);

        // Get all the temperatureSummaryList where measurementLocation equals to UPDATED_MEASUREMENT_LOCATION
        defaultTemperatureSummaryShouldNotBeFound("measurementLocation.equals=" + UPDATED_MEASUREMENT_LOCATION);
    }

    @Test
    @Transactional
    void getAllTemperatureSummariesByMeasurementLocationIsInShouldWork() throws Exception {
        // Initialize the database
        temperatureSummaryRepository.saveAndFlush(temperatureSummary);

        // Get all the temperatureSummaryList where measurementLocation in DEFAULT_MEASUREMENT_LOCATION or UPDATED_MEASUREMENT_LOCATION
        defaultTemperatureSummaryShouldBeFound(
            "measurementLocation.in=" + DEFAULT_MEASUREMENT_LOCATION + "," + UPDATED_MEASUREMENT_LOCATION
        );

        // Get all the temperatureSummaryList where measurementLocation equals to UPDATED_MEASUREMENT_LOCATION
        defaultTemperatureSummaryShouldNotBeFound("measurementLocation.in=" + UPDATED_MEASUREMENT_LOCATION);
    }

    @Test
    @Transactional
    void getAllTemperatureSummariesByMeasurementLocationIsNullOrNotNull() throws Exception {
        // Initialize the database
        temperatureSummaryRepository.saveAndFlush(temperatureSummary);

        // Get all the temperatureSummaryList where measurementLocation is not null
        defaultTemperatureSummaryShouldBeFound("measurementLocation.specified=true");

        // Get all the temperatureSummaryList where measurementLocation is null
        defaultTemperatureSummaryShouldNotBeFound("measurementLocation.specified=false");
    }

    @Test
    @Transactional
    void getAllTemperatureSummariesByMeasurementLocationIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        temperatureSummaryRepository.saveAndFlush(temperatureSummary);

        // Get all the temperatureSummaryList where measurementLocation is greater than or equal to DEFAULT_MEASUREMENT_LOCATION
        defaultTemperatureSummaryShouldBeFound("measurementLocation.greaterThanOrEqual=" + DEFAULT_MEASUREMENT_LOCATION);

        // Get all the temperatureSummaryList where measurementLocation is greater than or equal to UPDATED_MEASUREMENT_LOCATION
        defaultTemperatureSummaryShouldNotBeFound("measurementLocation.greaterThanOrEqual=" + UPDATED_MEASUREMENT_LOCATION);
    }

    @Test
    @Transactional
    void getAllTemperatureSummariesByMeasurementLocationIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        temperatureSummaryRepository.saveAndFlush(temperatureSummary);

        // Get all the temperatureSummaryList where measurementLocation is less than or equal to DEFAULT_MEASUREMENT_LOCATION
        defaultTemperatureSummaryShouldBeFound("measurementLocation.lessThanOrEqual=" + DEFAULT_MEASUREMENT_LOCATION);

        // Get all the temperatureSummaryList where measurementLocation is less than or equal to SMALLER_MEASUREMENT_LOCATION
        defaultTemperatureSummaryShouldNotBeFound("measurementLocation.lessThanOrEqual=" + SMALLER_MEASUREMENT_LOCATION);
    }

    @Test
    @Transactional
    void getAllTemperatureSummariesByMeasurementLocationIsLessThanSomething() throws Exception {
        // Initialize the database
        temperatureSummaryRepository.saveAndFlush(temperatureSummary);

        // Get all the temperatureSummaryList where measurementLocation is less than DEFAULT_MEASUREMENT_LOCATION
        defaultTemperatureSummaryShouldNotBeFound("measurementLocation.lessThan=" + DEFAULT_MEASUREMENT_LOCATION);

        // Get all the temperatureSummaryList where measurementLocation is less than UPDATED_MEASUREMENT_LOCATION
        defaultTemperatureSummaryShouldBeFound("measurementLocation.lessThan=" + UPDATED_MEASUREMENT_LOCATION);
    }

    @Test
    @Transactional
    void getAllTemperatureSummariesByMeasurementLocationIsGreaterThanSomething() throws Exception {
        // Initialize the database
        temperatureSummaryRepository.saveAndFlush(temperatureSummary);

        // Get all the temperatureSummaryList where measurementLocation is greater than DEFAULT_MEASUREMENT_LOCATION
        defaultTemperatureSummaryShouldNotBeFound("measurementLocation.greaterThan=" + DEFAULT_MEASUREMENT_LOCATION);

        // Get all the temperatureSummaryList where measurementLocation is greater than SMALLER_MEASUREMENT_LOCATION
        defaultTemperatureSummaryShouldBeFound("measurementLocation.greaterThan=" + SMALLER_MEASUREMENT_LOCATION);
    }

    @Test
    @Transactional
    void getAllTemperatureSummariesByStartTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        temperatureSummaryRepository.saveAndFlush(temperatureSummary);

        // Get all the temperatureSummaryList where startTime equals to DEFAULT_START_TIME
        defaultTemperatureSummaryShouldBeFound("startTime.equals=" + DEFAULT_START_TIME);

        // Get all the temperatureSummaryList where startTime equals to UPDATED_START_TIME
        defaultTemperatureSummaryShouldNotBeFound("startTime.equals=" + UPDATED_START_TIME);
    }

    @Test
    @Transactional
    void getAllTemperatureSummariesByStartTimeIsInShouldWork() throws Exception {
        // Initialize the database
        temperatureSummaryRepository.saveAndFlush(temperatureSummary);

        // Get all the temperatureSummaryList where startTime in DEFAULT_START_TIME or UPDATED_START_TIME
        defaultTemperatureSummaryShouldBeFound("startTime.in=" + DEFAULT_START_TIME + "," + UPDATED_START_TIME);

        // Get all the temperatureSummaryList where startTime equals to UPDATED_START_TIME
        defaultTemperatureSummaryShouldNotBeFound("startTime.in=" + UPDATED_START_TIME);
    }

    @Test
    @Transactional
    void getAllTemperatureSummariesByStartTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        temperatureSummaryRepository.saveAndFlush(temperatureSummary);

        // Get all the temperatureSummaryList where startTime is not null
        defaultTemperatureSummaryShouldBeFound("startTime.specified=true");

        // Get all the temperatureSummaryList where startTime is null
        defaultTemperatureSummaryShouldNotBeFound("startTime.specified=false");
    }

    @Test
    @Transactional
    void getAllTemperatureSummariesByEndTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        temperatureSummaryRepository.saveAndFlush(temperatureSummary);

        // Get all the temperatureSummaryList where endTime equals to DEFAULT_END_TIME
        defaultTemperatureSummaryShouldBeFound("endTime.equals=" + DEFAULT_END_TIME);

        // Get all the temperatureSummaryList where endTime equals to UPDATED_END_TIME
        defaultTemperatureSummaryShouldNotBeFound("endTime.equals=" + UPDATED_END_TIME);
    }

    @Test
    @Transactional
    void getAllTemperatureSummariesByEndTimeIsInShouldWork() throws Exception {
        // Initialize the database
        temperatureSummaryRepository.saveAndFlush(temperatureSummary);

        // Get all the temperatureSummaryList where endTime in DEFAULT_END_TIME or UPDATED_END_TIME
        defaultTemperatureSummaryShouldBeFound("endTime.in=" + DEFAULT_END_TIME + "," + UPDATED_END_TIME);

        // Get all the temperatureSummaryList where endTime equals to UPDATED_END_TIME
        defaultTemperatureSummaryShouldNotBeFound("endTime.in=" + UPDATED_END_TIME);
    }

    @Test
    @Transactional
    void getAllTemperatureSummariesByEndTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        temperatureSummaryRepository.saveAndFlush(temperatureSummary);

        // Get all the temperatureSummaryList where endTime is not null
        defaultTemperatureSummaryShouldBeFound("endTime.specified=true");

        // Get all the temperatureSummaryList where endTime is null
        defaultTemperatureSummaryShouldNotBeFound("endTime.specified=false");
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultTemperatureSummaryShouldBeFound(String filter) throws Exception {
        restTemperatureSummaryMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(temperatureSummary.getId().toString())))
            .andExpect(jsonPath("$.[*].usuarioId").value(hasItem(DEFAULT_USUARIO_ID)))
            .andExpect(jsonPath("$.[*].empresaId").value(hasItem(DEFAULT_EMPRESA_ID)))
            .andExpect(jsonPath("$.[*].fieldAverage").value(hasItem(DEFAULT_FIELD_AVERAGE.doubleValue())))
            .andExpect(jsonPath("$.[*].fieldMax").value(hasItem(DEFAULT_FIELD_MAX.doubleValue())))
            .andExpect(jsonPath("$.[*].fieldMin").value(hasItem(DEFAULT_FIELD_MIN.doubleValue())))
            .andExpect(jsonPath("$.[*].measurementLocation").value(hasItem(DEFAULT_MEASUREMENT_LOCATION)))
            .andExpect(jsonPath("$.[*].startTime").value(hasItem(DEFAULT_START_TIME.toString())))
            .andExpect(jsonPath("$.[*].endTime").value(hasItem(DEFAULT_END_TIME.toString())));

        // Check, that the count call also returns 1
        restTemperatureSummaryMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultTemperatureSummaryShouldNotBeFound(String filter) throws Exception {
        restTemperatureSummaryMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restTemperatureSummaryMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingTemperatureSummary() throws Exception {
        // Get the temperatureSummary
        restTemperatureSummaryMockMvc.perform(get(ENTITY_API_URL_ID, UUID.randomUUID().toString())).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingTemperatureSummary() throws Exception {
        // Initialize the database
        temperatureSummaryRepository.saveAndFlush(temperatureSummary);

        int databaseSizeBeforeUpdate = temperatureSummaryRepository.findAll().size();

        // Update the temperatureSummary
        TemperatureSummary updatedTemperatureSummary = temperatureSummaryRepository.findById(temperatureSummary.getId()).get();
        // Disconnect from session so that the updates on updatedTemperatureSummary are not directly saved in db
        em.detach(updatedTemperatureSummary);
        updatedTemperatureSummary
            .usuarioId(UPDATED_USUARIO_ID)
            .empresaId(UPDATED_EMPRESA_ID)
            .fieldAverage(UPDATED_FIELD_AVERAGE)
            .fieldMax(UPDATED_FIELD_MAX)
            .fieldMin(UPDATED_FIELD_MIN)
            .measurementLocation(UPDATED_MEASUREMENT_LOCATION)
            .startTime(UPDATED_START_TIME)
            .endTime(UPDATED_END_TIME);

        restTemperatureSummaryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedTemperatureSummary.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedTemperatureSummary))
            )
            .andExpect(status().isOk());

        // Validate the TemperatureSummary in the database
        List<TemperatureSummary> temperatureSummaryList = temperatureSummaryRepository.findAll();
        assertThat(temperatureSummaryList).hasSize(databaseSizeBeforeUpdate);
        TemperatureSummary testTemperatureSummary = temperatureSummaryList.get(temperatureSummaryList.size() - 1);
        assertThat(testTemperatureSummary.getUsuarioId()).isEqualTo(UPDATED_USUARIO_ID);
        assertThat(testTemperatureSummary.getEmpresaId()).isEqualTo(UPDATED_EMPRESA_ID);
        assertThat(testTemperatureSummary.getFieldAverage()).isEqualTo(UPDATED_FIELD_AVERAGE);
        assertThat(testTemperatureSummary.getFieldMax()).isEqualTo(UPDATED_FIELD_MAX);
        assertThat(testTemperatureSummary.getFieldMin()).isEqualTo(UPDATED_FIELD_MIN);
        assertThat(testTemperatureSummary.getMeasurementLocation()).isEqualTo(UPDATED_MEASUREMENT_LOCATION);
        assertThat(testTemperatureSummary.getStartTime()).isEqualTo(UPDATED_START_TIME);
        assertThat(testTemperatureSummary.getEndTime()).isEqualTo(UPDATED_END_TIME);
    }

    @Test
    @Transactional
    void putNonExistingTemperatureSummary() throws Exception {
        int databaseSizeBeforeUpdate = temperatureSummaryRepository.findAll().size();
        temperatureSummary.setId(UUID.randomUUID());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTemperatureSummaryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, temperatureSummary.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(temperatureSummary))
            )
            .andExpect(status().isBadRequest());

        // Validate the TemperatureSummary in the database
        List<TemperatureSummary> temperatureSummaryList = temperatureSummaryRepository.findAll();
        assertThat(temperatureSummaryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTemperatureSummary() throws Exception {
        int databaseSizeBeforeUpdate = temperatureSummaryRepository.findAll().size();
        temperatureSummary.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTemperatureSummaryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(temperatureSummary))
            )
            .andExpect(status().isBadRequest());

        // Validate the TemperatureSummary in the database
        List<TemperatureSummary> temperatureSummaryList = temperatureSummaryRepository.findAll();
        assertThat(temperatureSummaryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTemperatureSummary() throws Exception {
        int databaseSizeBeforeUpdate = temperatureSummaryRepository.findAll().size();
        temperatureSummary.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTemperatureSummaryMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(temperatureSummary))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TemperatureSummary in the database
        List<TemperatureSummary> temperatureSummaryList = temperatureSummaryRepository.findAll();
        assertThat(temperatureSummaryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTemperatureSummaryWithPatch() throws Exception {
        // Initialize the database
        temperatureSummaryRepository.saveAndFlush(temperatureSummary);

        int databaseSizeBeforeUpdate = temperatureSummaryRepository.findAll().size();

        // Update the temperatureSummary using partial update
        TemperatureSummary partialUpdatedTemperatureSummary = new TemperatureSummary();
        partialUpdatedTemperatureSummary.setId(temperatureSummary.getId());

        partialUpdatedTemperatureSummary.usuarioId(UPDATED_USUARIO_ID).fieldMax(UPDATED_FIELD_MAX);

        restTemperatureSummaryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTemperatureSummary.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTemperatureSummary))
            )
            .andExpect(status().isOk());

        // Validate the TemperatureSummary in the database
        List<TemperatureSummary> temperatureSummaryList = temperatureSummaryRepository.findAll();
        assertThat(temperatureSummaryList).hasSize(databaseSizeBeforeUpdate);
        TemperatureSummary testTemperatureSummary = temperatureSummaryList.get(temperatureSummaryList.size() - 1);
        assertThat(testTemperatureSummary.getUsuarioId()).isEqualTo(UPDATED_USUARIO_ID);
        assertThat(testTemperatureSummary.getEmpresaId()).isEqualTo(DEFAULT_EMPRESA_ID);
        assertThat(testTemperatureSummary.getFieldAverage()).isEqualTo(DEFAULT_FIELD_AVERAGE);
        assertThat(testTemperatureSummary.getFieldMax()).isEqualTo(UPDATED_FIELD_MAX);
        assertThat(testTemperatureSummary.getFieldMin()).isEqualTo(DEFAULT_FIELD_MIN);
        assertThat(testTemperatureSummary.getMeasurementLocation()).isEqualTo(DEFAULT_MEASUREMENT_LOCATION);
        assertThat(testTemperatureSummary.getStartTime()).isEqualTo(DEFAULT_START_TIME);
        assertThat(testTemperatureSummary.getEndTime()).isEqualTo(DEFAULT_END_TIME);
    }

    @Test
    @Transactional
    void fullUpdateTemperatureSummaryWithPatch() throws Exception {
        // Initialize the database
        temperatureSummaryRepository.saveAndFlush(temperatureSummary);

        int databaseSizeBeforeUpdate = temperatureSummaryRepository.findAll().size();

        // Update the temperatureSummary using partial update
        TemperatureSummary partialUpdatedTemperatureSummary = new TemperatureSummary();
        partialUpdatedTemperatureSummary.setId(temperatureSummary.getId());

        partialUpdatedTemperatureSummary
            .usuarioId(UPDATED_USUARIO_ID)
            .empresaId(UPDATED_EMPRESA_ID)
            .fieldAverage(UPDATED_FIELD_AVERAGE)
            .fieldMax(UPDATED_FIELD_MAX)
            .fieldMin(UPDATED_FIELD_MIN)
            .measurementLocation(UPDATED_MEASUREMENT_LOCATION)
            .startTime(UPDATED_START_TIME)
            .endTime(UPDATED_END_TIME);

        restTemperatureSummaryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTemperatureSummary.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTemperatureSummary))
            )
            .andExpect(status().isOk());

        // Validate the TemperatureSummary in the database
        List<TemperatureSummary> temperatureSummaryList = temperatureSummaryRepository.findAll();
        assertThat(temperatureSummaryList).hasSize(databaseSizeBeforeUpdate);
        TemperatureSummary testTemperatureSummary = temperatureSummaryList.get(temperatureSummaryList.size() - 1);
        assertThat(testTemperatureSummary.getUsuarioId()).isEqualTo(UPDATED_USUARIO_ID);
        assertThat(testTemperatureSummary.getEmpresaId()).isEqualTo(UPDATED_EMPRESA_ID);
        assertThat(testTemperatureSummary.getFieldAverage()).isEqualTo(UPDATED_FIELD_AVERAGE);
        assertThat(testTemperatureSummary.getFieldMax()).isEqualTo(UPDATED_FIELD_MAX);
        assertThat(testTemperatureSummary.getFieldMin()).isEqualTo(UPDATED_FIELD_MIN);
        assertThat(testTemperatureSummary.getMeasurementLocation()).isEqualTo(UPDATED_MEASUREMENT_LOCATION);
        assertThat(testTemperatureSummary.getStartTime()).isEqualTo(UPDATED_START_TIME);
        assertThat(testTemperatureSummary.getEndTime()).isEqualTo(UPDATED_END_TIME);
    }

    @Test
    @Transactional
    void patchNonExistingTemperatureSummary() throws Exception {
        int databaseSizeBeforeUpdate = temperatureSummaryRepository.findAll().size();
        temperatureSummary.setId(UUID.randomUUID());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTemperatureSummaryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, temperatureSummary.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(temperatureSummary))
            )
            .andExpect(status().isBadRequest());

        // Validate the TemperatureSummary in the database
        List<TemperatureSummary> temperatureSummaryList = temperatureSummaryRepository.findAll();
        assertThat(temperatureSummaryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTemperatureSummary() throws Exception {
        int databaseSizeBeforeUpdate = temperatureSummaryRepository.findAll().size();
        temperatureSummary.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTemperatureSummaryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(temperatureSummary))
            )
            .andExpect(status().isBadRequest());

        // Validate the TemperatureSummary in the database
        List<TemperatureSummary> temperatureSummaryList = temperatureSummaryRepository.findAll();
        assertThat(temperatureSummaryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTemperatureSummary() throws Exception {
        int databaseSizeBeforeUpdate = temperatureSummaryRepository.findAll().size();
        temperatureSummary.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTemperatureSummaryMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(temperatureSummary))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TemperatureSummary in the database
        List<TemperatureSummary> temperatureSummaryList = temperatureSummaryRepository.findAll();
        assertThat(temperatureSummaryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTemperatureSummary() throws Exception {
        // Initialize the database
        temperatureSummaryRepository.saveAndFlush(temperatureSummary);

        int databaseSizeBeforeDelete = temperatureSummaryRepository.findAll().size();

        // Delete the temperatureSummary
        restTemperatureSummaryMockMvc
            .perform(delete(ENTITY_API_URL_ID, temperatureSummary.getId().toString()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TemperatureSummary> temperatureSummaryList = temperatureSummaryRepository.findAll();
        assertThat(temperatureSummaryList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
