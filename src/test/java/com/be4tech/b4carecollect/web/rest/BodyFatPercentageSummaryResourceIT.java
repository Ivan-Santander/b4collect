package com.be4tech.b4carecollect.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.be4tech.b4carecollect.IntegrationTest;
import com.be4tech.b4carecollect.domain.BodyFatPercentageSummary;
import com.be4tech.b4carecollect.repository.BodyFatPercentageSummaryRepository;
import com.be4tech.b4carecollect.service.criteria.BodyFatPercentageSummaryCriteria;
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
 * Integration tests for the {@link BodyFatPercentageSummaryResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class BodyFatPercentageSummaryResourceIT {

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

    private static final Instant DEFAULT_START_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_START_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_END_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_END_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/body-fat-percentage-summaries";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private BodyFatPercentageSummaryRepository bodyFatPercentageSummaryRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBodyFatPercentageSummaryMockMvc;

    private BodyFatPercentageSummary bodyFatPercentageSummary;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BodyFatPercentageSummary createEntity(EntityManager em) {
        BodyFatPercentageSummary bodyFatPercentageSummary = new BodyFatPercentageSummary()
            .usuarioId(DEFAULT_USUARIO_ID)
            .empresaId(DEFAULT_EMPRESA_ID)
            .fieldAverage(DEFAULT_FIELD_AVERAGE)
            .fieldMax(DEFAULT_FIELD_MAX)
            .fieldMin(DEFAULT_FIELD_MIN)
            .startTime(DEFAULT_START_TIME)
            .endTime(DEFAULT_END_TIME);
        return bodyFatPercentageSummary;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BodyFatPercentageSummary createUpdatedEntity(EntityManager em) {
        BodyFatPercentageSummary bodyFatPercentageSummary = new BodyFatPercentageSummary()
            .usuarioId(UPDATED_USUARIO_ID)
            .empresaId(UPDATED_EMPRESA_ID)
            .fieldAverage(UPDATED_FIELD_AVERAGE)
            .fieldMax(UPDATED_FIELD_MAX)
            .fieldMin(UPDATED_FIELD_MIN)
            .startTime(UPDATED_START_TIME)
            .endTime(UPDATED_END_TIME);
        return bodyFatPercentageSummary;
    }

    @BeforeEach
    public void initTest() {
        bodyFatPercentageSummary = createEntity(em);
    }

    @Test
    @Transactional
    void createBodyFatPercentageSummary() throws Exception {
        int databaseSizeBeforeCreate = bodyFatPercentageSummaryRepository.findAll().size();
        // Create the BodyFatPercentageSummary
        restBodyFatPercentageSummaryMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bodyFatPercentageSummary))
            )
            .andExpect(status().isCreated());

        // Validate the BodyFatPercentageSummary in the database
        List<BodyFatPercentageSummary> bodyFatPercentageSummaryList = bodyFatPercentageSummaryRepository.findAll();
        assertThat(bodyFatPercentageSummaryList).hasSize(databaseSizeBeforeCreate + 1);
        BodyFatPercentageSummary testBodyFatPercentageSummary = bodyFatPercentageSummaryList.get(bodyFatPercentageSummaryList.size() - 1);
        assertThat(testBodyFatPercentageSummary.getUsuarioId()).isEqualTo(DEFAULT_USUARIO_ID);
        assertThat(testBodyFatPercentageSummary.getEmpresaId()).isEqualTo(DEFAULT_EMPRESA_ID);
        assertThat(testBodyFatPercentageSummary.getFieldAverage()).isEqualTo(DEFAULT_FIELD_AVERAGE);
        assertThat(testBodyFatPercentageSummary.getFieldMax()).isEqualTo(DEFAULT_FIELD_MAX);
        assertThat(testBodyFatPercentageSummary.getFieldMin()).isEqualTo(DEFAULT_FIELD_MIN);
        assertThat(testBodyFatPercentageSummary.getStartTime()).isEqualTo(DEFAULT_START_TIME);
        assertThat(testBodyFatPercentageSummary.getEndTime()).isEqualTo(DEFAULT_END_TIME);
    }

    @Test
    @Transactional
    void createBodyFatPercentageSummaryWithExistingId() throws Exception {
        // Create the BodyFatPercentageSummary with an existing ID
        bodyFatPercentageSummaryRepository.saveAndFlush(bodyFatPercentageSummary);

        int databaseSizeBeforeCreate = bodyFatPercentageSummaryRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBodyFatPercentageSummaryMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bodyFatPercentageSummary))
            )
            .andExpect(status().isBadRequest());

        // Validate the BodyFatPercentageSummary in the database
        List<BodyFatPercentageSummary> bodyFatPercentageSummaryList = bodyFatPercentageSummaryRepository.findAll();
        assertThat(bodyFatPercentageSummaryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllBodyFatPercentageSummaries() throws Exception {
        // Initialize the database
        bodyFatPercentageSummaryRepository.saveAndFlush(bodyFatPercentageSummary);

        // Get all the bodyFatPercentageSummaryList
        restBodyFatPercentageSummaryMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bodyFatPercentageSummary.getId().toString())))
            .andExpect(jsonPath("$.[*].usuarioId").value(hasItem(DEFAULT_USUARIO_ID)))
            .andExpect(jsonPath("$.[*].empresaId").value(hasItem(DEFAULT_EMPRESA_ID)))
            .andExpect(jsonPath("$.[*].fieldAverage").value(hasItem(DEFAULT_FIELD_AVERAGE.doubleValue())))
            .andExpect(jsonPath("$.[*].fieldMax").value(hasItem(DEFAULT_FIELD_MAX.doubleValue())))
            .andExpect(jsonPath("$.[*].fieldMin").value(hasItem(DEFAULT_FIELD_MIN.doubleValue())))
            .andExpect(jsonPath("$.[*].startTime").value(hasItem(DEFAULT_START_TIME.toString())))
            .andExpect(jsonPath("$.[*].endTime").value(hasItem(DEFAULT_END_TIME.toString())));
    }

    @Test
    @Transactional
    void getBodyFatPercentageSummary() throws Exception {
        // Initialize the database
        bodyFatPercentageSummaryRepository.saveAndFlush(bodyFatPercentageSummary);

        // Get the bodyFatPercentageSummary
        restBodyFatPercentageSummaryMockMvc
            .perform(get(ENTITY_API_URL_ID, bodyFatPercentageSummary.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(bodyFatPercentageSummary.getId().toString()))
            .andExpect(jsonPath("$.usuarioId").value(DEFAULT_USUARIO_ID))
            .andExpect(jsonPath("$.empresaId").value(DEFAULT_EMPRESA_ID))
            .andExpect(jsonPath("$.fieldAverage").value(DEFAULT_FIELD_AVERAGE.doubleValue()))
            .andExpect(jsonPath("$.fieldMax").value(DEFAULT_FIELD_MAX.doubleValue()))
            .andExpect(jsonPath("$.fieldMin").value(DEFAULT_FIELD_MIN.doubleValue()))
            .andExpect(jsonPath("$.startTime").value(DEFAULT_START_TIME.toString()))
            .andExpect(jsonPath("$.endTime").value(DEFAULT_END_TIME.toString()));
    }

    @Test
    @Transactional
    void getBodyFatPercentageSummariesByIdFiltering() throws Exception {
        // Initialize the database
        bodyFatPercentageSummaryRepository.saveAndFlush(bodyFatPercentageSummary);

        UUID id = bodyFatPercentageSummary.getId();

        defaultBodyFatPercentageSummaryShouldBeFound("id.equals=" + id);
        defaultBodyFatPercentageSummaryShouldNotBeFound("id.notEquals=" + id);
    }

    @Test
    @Transactional
    void getAllBodyFatPercentageSummariesByUsuarioIdIsEqualToSomething() throws Exception {
        // Initialize the database
        bodyFatPercentageSummaryRepository.saveAndFlush(bodyFatPercentageSummary);

        // Get all the bodyFatPercentageSummaryList where usuarioId equals to DEFAULT_USUARIO_ID
        defaultBodyFatPercentageSummaryShouldBeFound("usuarioId.equals=" + DEFAULT_USUARIO_ID);

        // Get all the bodyFatPercentageSummaryList where usuarioId equals to UPDATED_USUARIO_ID
        defaultBodyFatPercentageSummaryShouldNotBeFound("usuarioId.equals=" + UPDATED_USUARIO_ID);
    }

    @Test
    @Transactional
    void getAllBodyFatPercentageSummariesByUsuarioIdIsInShouldWork() throws Exception {
        // Initialize the database
        bodyFatPercentageSummaryRepository.saveAndFlush(bodyFatPercentageSummary);

        // Get all the bodyFatPercentageSummaryList where usuarioId in DEFAULT_USUARIO_ID or UPDATED_USUARIO_ID
        defaultBodyFatPercentageSummaryShouldBeFound("usuarioId.in=" + DEFAULT_USUARIO_ID + "," + UPDATED_USUARIO_ID);

        // Get all the bodyFatPercentageSummaryList where usuarioId equals to UPDATED_USUARIO_ID
        defaultBodyFatPercentageSummaryShouldNotBeFound("usuarioId.in=" + UPDATED_USUARIO_ID);
    }

    @Test
    @Transactional
    void getAllBodyFatPercentageSummariesByUsuarioIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        bodyFatPercentageSummaryRepository.saveAndFlush(bodyFatPercentageSummary);

        // Get all the bodyFatPercentageSummaryList where usuarioId is not null
        defaultBodyFatPercentageSummaryShouldBeFound("usuarioId.specified=true");

        // Get all the bodyFatPercentageSummaryList where usuarioId is null
        defaultBodyFatPercentageSummaryShouldNotBeFound("usuarioId.specified=false");
    }

    @Test
    @Transactional
    void getAllBodyFatPercentageSummariesByUsuarioIdContainsSomething() throws Exception {
        // Initialize the database
        bodyFatPercentageSummaryRepository.saveAndFlush(bodyFatPercentageSummary);

        // Get all the bodyFatPercentageSummaryList where usuarioId contains DEFAULT_USUARIO_ID
        defaultBodyFatPercentageSummaryShouldBeFound("usuarioId.contains=" + DEFAULT_USUARIO_ID);

        // Get all the bodyFatPercentageSummaryList where usuarioId contains UPDATED_USUARIO_ID
        defaultBodyFatPercentageSummaryShouldNotBeFound("usuarioId.contains=" + UPDATED_USUARIO_ID);
    }

    @Test
    @Transactional
    void getAllBodyFatPercentageSummariesByUsuarioIdNotContainsSomething() throws Exception {
        // Initialize the database
        bodyFatPercentageSummaryRepository.saveAndFlush(bodyFatPercentageSummary);

        // Get all the bodyFatPercentageSummaryList where usuarioId does not contain DEFAULT_USUARIO_ID
        defaultBodyFatPercentageSummaryShouldNotBeFound("usuarioId.doesNotContain=" + DEFAULT_USUARIO_ID);

        // Get all the bodyFatPercentageSummaryList where usuarioId does not contain UPDATED_USUARIO_ID
        defaultBodyFatPercentageSummaryShouldBeFound("usuarioId.doesNotContain=" + UPDATED_USUARIO_ID);
    }

    @Test
    @Transactional
    void getAllBodyFatPercentageSummariesByEmpresaIdIsEqualToSomething() throws Exception {
        // Initialize the database
        bodyFatPercentageSummaryRepository.saveAndFlush(bodyFatPercentageSummary);

        // Get all the bodyFatPercentageSummaryList where empresaId equals to DEFAULT_EMPRESA_ID
        defaultBodyFatPercentageSummaryShouldBeFound("empresaId.equals=" + DEFAULT_EMPRESA_ID);

        // Get all the bodyFatPercentageSummaryList where empresaId equals to UPDATED_EMPRESA_ID
        defaultBodyFatPercentageSummaryShouldNotBeFound("empresaId.equals=" + UPDATED_EMPRESA_ID);
    }

    @Test
    @Transactional
    void getAllBodyFatPercentageSummariesByEmpresaIdIsInShouldWork() throws Exception {
        // Initialize the database
        bodyFatPercentageSummaryRepository.saveAndFlush(bodyFatPercentageSummary);

        // Get all the bodyFatPercentageSummaryList where empresaId in DEFAULT_EMPRESA_ID or UPDATED_EMPRESA_ID
        defaultBodyFatPercentageSummaryShouldBeFound("empresaId.in=" + DEFAULT_EMPRESA_ID + "," + UPDATED_EMPRESA_ID);

        // Get all the bodyFatPercentageSummaryList where empresaId equals to UPDATED_EMPRESA_ID
        defaultBodyFatPercentageSummaryShouldNotBeFound("empresaId.in=" + UPDATED_EMPRESA_ID);
    }

    @Test
    @Transactional
    void getAllBodyFatPercentageSummariesByEmpresaIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        bodyFatPercentageSummaryRepository.saveAndFlush(bodyFatPercentageSummary);

        // Get all the bodyFatPercentageSummaryList where empresaId is not null
        defaultBodyFatPercentageSummaryShouldBeFound("empresaId.specified=true");

        // Get all the bodyFatPercentageSummaryList where empresaId is null
        defaultBodyFatPercentageSummaryShouldNotBeFound("empresaId.specified=false");
    }

    @Test
    @Transactional
    void getAllBodyFatPercentageSummariesByEmpresaIdContainsSomething() throws Exception {
        // Initialize the database
        bodyFatPercentageSummaryRepository.saveAndFlush(bodyFatPercentageSummary);

        // Get all the bodyFatPercentageSummaryList where empresaId contains DEFAULT_EMPRESA_ID
        defaultBodyFatPercentageSummaryShouldBeFound("empresaId.contains=" + DEFAULT_EMPRESA_ID);

        // Get all the bodyFatPercentageSummaryList where empresaId contains UPDATED_EMPRESA_ID
        defaultBodyFatPercentageSummaryShouldNotBeFound("empresaId.contains=" + UPDATED_EMPRESA_ID);
    }

    @Test
    @Transactional
    void getAllBodyFatPercentageSummariesByEmpresaIdNotContainsSomething() throws Exception {
        // Initialize the database
        bodyFatPercentageSummaryRepository.saveAndFlush(bodyFatPercentageSummary);

        // Get all the bodyFatPercentageSummaryList where empresaId does not contain DEFAULT_EMPRESA_ID
        defaultBodyFatPercentageSummaryShouldNotBeFound("empresaId.doesNotContain=" + DEFAULT_EMPRESA_ID);

        // Get all the bodyFatPercentageSummaryList where empresaId does not contain UPDATED_EMPRESA_ID
        defaultBodyFatPercentageSummaryShouldBeFound("empresaId.doesNotContain=" + UPDATED_EMPRESA_ID);
    }

    @Test
    @Transactional
    void getAllBodyFatPercentageSummariesByFieldAverageIsEqualToSomething() throws Exception {
        // Initialize the database
        bodyFatPercentageSummaryRepository.saveAndFlush(bodyFatPercentageSummary);

        // Get all the bodyFatPercentageSummaryList where fieldAverage equals to DEFAULT_FIELD_AVERAGE
        defaultBodyFatPercentageSummaryShouldBeFound("fieldAverage.equals=" + DEFAULT_FIELD_AVERAGE);

        // Get all the bodyFatPercentageSummaryList where fieldAverage equals to UPDATED_FIELD_AVERAGE
        defaultBodyFatPercentageSummaryShouldNotBeFound("fieldAverage.equals=" + UPDATED_FIELD_AVERAGE);
    }

    @Test
    @Transactional
    void getAllBodyFatPercentageSummariesByFieldAverageIsInShouldWork() throws Exception {
        // Initialize the database
        bodyFatPercentageSummaryRepository.saveAndFlush(bodyFatPercentageSummary);

        // Get all the bodyFatPercentageSummaryList where fieldAverage in DEFAULT_FIELD_AVERAGE or UPDATED_FIELD_AVERAGE
        defaultBodyFatPercentageSummaryShouldBeFound("fieldAverage.in=" + DEFAULT_FIELD_AVERAGE + "," + UPDATED_FIELD_AVERAGE);

        // Get all the bodyFatPercentageSummaryList where fieldAverage equals to UPDATED_FIELD_AVERAGE
        defaultBodyFatPercentageSummaryShouldNotBeFound("fieldAverage.in=" + UPDATED_FIELD_AVERAGE);
    }

    @Test
    @Transactional
    void getAllBodyFatPercentageSummariesByFieldAverageIsNullOrNotNull() throws Exception {
        // Initialize the database
        bodyFatPercentageSummaryRepository.saveAndFlush(bodyFatPercentageSummary);

        // Get all the bodyFatPercentageSummaryList where fieldAverage is not null
        defaultBodyFatPercentageSummaryShouldBeFound("fieldAverage.specified=true");

        // Get all the bodyFatPercentageSummaryList where fieldAverage is null
        defaultBodyFatPercentageSummaryShouldNotBeFound("fieldAverage.specified=false");
    }

    @Test
    @Transactional
    void getAllBodyFatPercentageSummariesByFieldAverageIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        bodyFatPercentageSummaryRepository.saveAndFlush(bodyFatPercentageSummary);

        // Get all the bodyFatPercentageSummaryList where fieldAverage is greater than or equal to DEFAULT_FIELD_AVERAGE
        defaultBodyFatPercentageSummaryShouldBeFound("fieldAverage.greaterThanOrEqual=" + DEFAULT_FIELD_AVERAGE);

        // Get all the bodyFatPercentageSummaryList where fieldAverage is greater than or equal to UPDATED_FIELD_AVERAGE
        defaultBodyFatPercentageSummaryShouldNotBeFound("fieldAverage.greaterThanOrEqual=" + UPDATED_FIELD_AVERAGE);
    }

    @Test
    @Transactional
    void getAllBodyFatPercentageSummariesByFieldAverageIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        bodyFatPercentageSummaryRepository.saveAndFlush(bodyFatPercentageSummary);

        // Get all the bodyFatPercentageSummaryList where fieldAverage is less than or equal to DEFAULT_FIELD_AVERAGE
        defaultBodyFatPercentageSummaryShouldBeFound("fieldAverage.lessThanOrEqual=" + DEFAULT_FIELD_AVERAGE);

        // Get all the bodyFatPercentageSummaryList where fieldAverage is less than or equal to SMALLER_FIELD_AVERAGE
        defaultBodyFatPercentageSummaryShouldNotBeFound("fieldAverage.lessThanOrEqual=" + SMALLER_FIELD_AVERAGE);
    }

    @Test
    @Transactional
    void getAllBodyFatPercentageSummariesByFieldAverageIsLessThanSomething() throws Exception {
        // Initialize the database
        bodyFatPercentageSummaryRepository.saveAndFlush(bodyFatPercentageSummary);

        // Get all the bodyFatPercentageSummaryList where fieldAverage is less than DEFAULT_FIELD_AVERAGE
        defaultBodyFatPercentageSummaryShouldNotBeFound("fieldAverage.lessThan=" + DEFAULT_FIELD_AVERAGE);

        // Get all the bodyFatPercentageSummaryList where fieldAverage is less than UPDATED_FIELD_AVERAGE
        defaultBodyFatPercentageSummaryShouldBeFound("fieldAverage.lessThan=" + UPDATED_FIELD_AVERAGE);
    }

    @Test
    @Transactional
    void getAllBodyFatPercentageSummariesByFieldAverageIsGreaterThanSomething() throws Exception {
        // Initialize the database
        bodyFatPercentageSummaryRepository.saveAndFlush(bodyFatPercentageSummary);

        // Get all the bodyFatPercentageSummaryList where fieldAverage is greater than DEFAULT_FIELD_AVERAGE
        defaultBodyFatPercentageSummaryShouldNotBeFound("fieldAverage.greaterThan=" + DEFAULT_FIELD_AVERAGE);

        // Get all the bodyFatPercentageSummaryList where fieldAverage is greater than SMALLER_FIELD_AVERAGE
        defaultBodyFatPercentageSummaryShouldBeFound("fieldAverage.greaterThan=" + SMALLER_FIELD_AVERAGE);
    }

    @Test
    @Transactional
    void getAllBodyFatPercentageSummariesByFieldMaxIsEqualToSomething() throws Exception {
        // Initialize the database
        bodyFatPercentageSummaryRepository.saveAndFlush(bodyFatPercentageSummary);

        // Get all the bodyFatPercentageSummaryList where fieldMax equals to DEFAULT_FIELD_MAX
        defaultBodyFatPercentageSummaryShouldBeFound("fieldMax.equals=" + DEFAULT_FIELD_MAX);

        // Get all the bodyFatPercentageSummaryList where fieldMax equals to UPDATED_FIELD_MAX
        defaultBodyFatPercentageSummaryShouldNotBeFound("fieldMax.equals=" + UPDATED_FIELD_MAX);
    }

    @Test
    @Transactional
    void getAllBodyFatPercentageSummariesByFieldMaxIsInShouldWork() throws Exception {
        // Initialize the database
        bodyFatPercentageSummaryRepository.saveAndFlush(bodyFatPercentageSummary);

        // Get all the bodyFatPercentageSummaryList where fieldMax in DEFAULT_FIELD_MAX or UPDATED_FIELD_MAX
        defaultBodyFatPercentageSummaryShouldBeFound("fieldMax.in=" + DEFAULT_FIELD_MAX + "," + UPDATED_FIELD_MAX);

        // Get all the bodyFatPercentageSummaryList where fieldMax equals to UPDATED_FIELD_MAX
        defaultBodyFatPercentageSummaryShouldNotBeFound("fieldMax.in=" + UPDATED_FIELD_MAX);
    }

    @Test
    @Transactional
    void getAllBodyFatPercentageSummariesByFieldMaxIsNullOrNotNull() throws Exception {
        // Initialize the database
        bodyFatPercentageSummaryRepository.saveAndFlush(bodyFatPercentageSummary);

        // Get all the bodyFatPercentageSummaryList where fieldMax is not null
        defaultBodyFatPercentageSummaryShouldBeFound("fieldMax.specified=true");

        // Get all the bodyFatPercentageSummaryList where fieldMax is null
        defaultBodyFatPercentageSummaryShouldNotBeFound("fieldMax.specified=false");
    }

    @Test
    @Transactional
    void getAllBodyFatPercentageSummariesByFieldMaxIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        bodyFatPercentageSummaryRepository.saveAndFlush(bodyFatPercentageSummary);

        // Get all the bodyFatPercentageSummaryList where fieldMax is greater than or equal to DEFAULT_FIELD_MAX
        defaultBodyFatPercentageSummaryShouldBeFound("fieldMax.greaterThanOrEqual=" + DEFAULT_FIELD_MAX);

        // Get all the bodyFatPercentageSummaryList where fieldMax is greater than or equal to UPDATED_FIELD_MAX
        defaultBodyFatPercentageSummaryShouldNotBeFound("fieldMax.greaterThanOrEqual=" + UPDATED_FIELD_MAX);
    }

    @Test
    @Transactional
    void getAllBodyFatPercentageSummariesByFieldMaxIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        bodyFatPercentageSummaryRepository.saveAndFlush(bodyFatPercentageSummary);

        // Get all the bodyFatPercentageSummaryList where fieldMax is less than or equal to DEFAULT_FIELD_MAX
        defaultBodyFatPercentageSummaryShouldBeFound("fieldMax.lessThanOrEqual=" + DEFAULT_FIELD_MAX);

        // Get all the bodyFatPercentageSummaryList where fieldMax is less than or equal to SMALLER_FIELD_MAX
        defaultBodyFatPercentageSummaryShouldNotBeFound("fieldMax.lessThanOrEqual=" + SMALLER_FIELD_MAX);
    }

    @Test
    @Transactional
    void getAllBodyFatPercentageSummariesByFieldMaxIsLessThanSomething() throws Exception {
        // Initialize the database
        bodyFatPercentageSummaryRepository.saveAndFlush(bodyFatPercentageSummary);

        // Get all the bodyFatPercentageSummaryList where fieldMax is less than DEFAULT_FIELD_MAX
        defaultBodyFatPercentageSummaryShouldNotBeFound("fieldMax.lessThan=" + DEFAULT_FIELD_MAX);

        // Get all the bodyFatPercentageSummaryList where fieldMax is less than UPDATED_FIELD_MAX
        defaultBodyFatPercentageSummaryShouldBeFound("fieldMax.lessThan=" + UPDATED_FIELD_MAX);
    }

    @Test
    @Transactional
    void getAllBodyFatPercentageSummariesByFieldMaxIsGreaterThanSomething() throws Exception {
        // Initialize the database
        bodyFatPercentageSummaryRepository.saveAndFlush(bodyFatPercentageSummary);

        // Get all the bodyFatPercentageSummaryList where fieldMax is greater than DEFAULT_FIELD_MAX
        defaultBodyFatPercentageSummaryShouldNotBeFound("fieldMax.greaterThan=" + DEFAULT_FIELD_MAX);

        // Get all the bodyFatPercentageSummaryList where fieldMax is greater than SMALLER_FIELD_MAX
        defaultBodyFatPercentageSummaryShouldBeFound("fieldMax.greaterThan=" + SMALLER_FIELD_MAX);
    }

    @Test
    @Transactional
    void getAllBodyFatPercentageSummariesByFieldMinIsEqualToSomething() throws Exception {
        // Initialize the database
        bodyFatPercentageSummaryRepository.saveAndFlush(bodyFatPercentageSummary);

        // Get all the bodyFatPercentageSummaryList where fieldMin equals to DEFAULT_FIELD_MIN
        defaultBodyFatPercentageSummaryShouldBeFound("fieldMin.equals=" + DEFAULT_FIELD_MIN);

        // Get all the bodyFatPercentageSummaryList where fieldMin equals to UPDATED_FIELD_MIN
        defaultBodyFatPercentageSummaryShouldNotBeFound("fieldMin.equals=" + UPDATED_FIELD_MIN);
    }

    @Test
    @Transactional
    void getAllBodyFatPercentageSummariesByFieldMinIsInShouldWork() throws Exception {
        // Initialize the database
        bodyFatPercentageSummaryRepository.saveAndFlush(bodyFatPercentageSummary);

        // Get all the bodyFatPercentageSummaryList where fieldMin in DEFAULT_FIELD_MIN or UPDATED_FIELD_MIN
        defaultBodyFatPercentageSummaryShouldBeFound("fieldMin.in=" + DEFAULT_FIELD_MIN + "," + UPDATED_FIELD_MIN);

        // Get all the bodyFatPercentageSummaryList where fieldMin equals to UPDATED_FIELD_MIN
        defaultBodyFatPercentageSummaryShouldNotBeFound("fieldMin.in=" + UPDATED_FIELD_MIN);
    }

    @Test
    @Transactional
    void getAllBodyFatPercentageSummariesByFieldMinIsNullOrNotNull() throws Exception {
        // Initialize the database
        bodyFatPercentageSummaryRepository.saveAndFlush(bodyFatPercentageSummary);

        // Get all the bodyFatPercentageSummaryList where fieldMin is not null
        defaultBodyFatPercentageSummaryShouldBeFound("fieldMin.specified=true");

        // Get all the bodyFatPercentageSummaryList where fieldMin is null
        defaultBodyFatPercentageSummaryShouldNotBeFound("fieldMin.specified=false");
    }

    @Test
    @Transactional
    void getAllBodyFatPercentageSummariesByFieldMinIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        bodyFatPercentageSummaryRepository.saveAndFlush(bodyFatPercentageSummary);

        // Get all the bodyFatPercentageSummaryList where fieldMin is greater than or equal to DEFAULT_FIELD_MIN
        defaultBodyFatPercentageSummaryShouldBeFound("fieldMin.greaterThanOrEqual=" + DEFAULT_FIELD_MIN);

        // Get all the bodyFatPercentageSummaryList where fieldMin is greater than or equal to UPDATED_FIELD_MIN
        defaultBodyFatPercentageSummaryShouldNotBeFound("fieldMin.greaterThanOrEqual=" + UPDATED_FIELD_MIN);
    }

    @Test
    @Transactional
    void getAllBodyFatPercentageSummariesByFieldMinIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        bodyFatPercentageSummaryRepository.saveAndFlush(bodyFatPercentageSummary);

        // Get all the bodyFatPercentageSummaryList where fieldMin is less than or equal to DEFAULT_FIELD_MIN
        defaultBodyFatPercentageSummaryShouldBeFound("fieldMin.lessThanOrEqual=" + DEFAULT_FIELD_MIN);

        // Get all the bodyFatPercentageSummaryList where fieldMin is less than or equal to SMALLER_FIELD_MIN
        defaultBodyFatPercentageSummaryShouldNotBeFound("fieldMin.lessThanOrEqual=" + SMALLER_FIELD_MIN);
    }

    @Test
    @Transactional
    void getAllBodyFatPercentageSummariesByFieldMinIsLessThanSomething() throws Exception {
        // Initialize the database
        bodyFatPercentageSummaryRepository.saveAndFlush(bodyFatPercentageSummary);

        // Get all the bodyFatPercentageSummaryList where fieldMin is less than DEFAULT_FIELD_MIN
        defaultBodyFatPercentageSummaryShouldNotBeFound("fieldMin.lessThan=" + DEFAULT_FIELD_MIN);

        // Get all the bodyFatPercentageSummaryList where fieldMin is less than UPDATED_FIELD_MIN
        defaultBodyFatPercentageSummaryShouldBeFound("fieldMin.lessThan=" + UPDATED_FIELD_MIN);
    }

    @Test
    @Transactional
    void getAllBodyFatPercentageSummariesByFieldMinIsGreaterThanSomething() throws Exception {
        // Initialize the database
        bodyFatPercentageSummaryRepository.saveAndFlush(bodyFatPercentageSummary);

        // Get all the bodyFatPercentageSummaryList where fieldMin is greater than DEFAULT_FIELD_MIN
        defaultBodyFatPercentageSummaryShouldNotBeFound("fieldMin.greaterThan=" + DEFAULT_FIELD_MIN);

        // Get all the bodyFatPercentageSummaryList where fieldMin is greater than SMALLER_FIELD_MIN
        defaultBodyFatPercentageSummaryShouldBeFound("fieldMin.greaterThan=" + SMALLER_FIELD_MIN);
    }

    @Test
    @Transactional
    void getAllBodyFatPercentageSummariesByStartTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        bodyFatPercentageSummaryRepository.saveAndFlush(bodyFatPercentageSummary);

        // Get all the bodyFatPercentageSummaryList where startTime equals to DEFAULT_START_TIME
        defaultBodyFatPercentageSummaryShouldBeFound("startTime.equals=" + DEFAULT_START_TIME);

        // Get all the bodyFatPercentageSummaryList where startTime equals to UPDATED_START_TIME
        defaultBodyFatPercentageSummaryShouldNotBeFound("startTime.equals=" + UPDATED_START_TIME);
    }

    @Test
    @Transactional
    void getAllBodyFatPercentageSummariesByStartTimeIsInShouldWork() throws Exception {
        // Initialize the database
        bodyFatPercentageSummaryRepository.saveAndFlush(bodyFatPercentageSummary);

        // Get all the bodyFatPercentageSummaryList where startTime in DEFAULT_START_TIME or UPDATED_START_TIME
        defaultBodyFatPercentageSummaryShouldBeFound("startTime.in=" + DEFAULT_START_TIME + "," + UPDATED_START_TIME);

        // Get all the bodyFatPercentageSummaryList where startTime equals to UPDATED_START_TIME
        defaultBodyFatPercentageSummaryShouldNotBeFound("startTime.in=" + UPDATED_START_TIME);
    }

    @Test
    @Transactional
    void getAllBodyFatPercentageSummariesByStartTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        bodyFatPercentageSummaryRepository.saveAndFlush(bodyFatPercentageSummary);

        // Get all the bodyFatPercentageSummaryList where startTime is not null
        defaultBodyFatPercentageSummaryShouldBeFound("startTime.specified=true");

        // Get all the bodyFatPercentageSummaryList where startTime is null
        defaultBodyFatPercentageSummaryShouldNotBeFound("startTime.specified=false");
    }

    @Test
    @Transactional
    void getAllBodyFatPercentageSummariesByEndTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        bodyFatPercentageSummaryRepository.saveAndFlush(bodyFatPercentageSummary);

        // Get all the bodyFatPercentageSummaryList where endTime equals to DEFAULT_END_TIME
        defaultBodyFatPercentageSummaryShouldBeFound("endTime.equals=" + DEFAULT_END_TIME);

        // Get all the bodyFatPercentageSummaryList where endTime equals to UPDATED_END_TIME
        defaultBodyFatPercentageSummaryShouldNotBeFound("endTime.equals=" + UPDATED_END_TIME);
    }

    @Test
    @Transactional
    void getAllBodyFatPercentageSummariesByEndTimeIsInShouldWork() throws Exception {
        // Initialize the database
        bodyFatPercentageSummaryRepository.saveAndFlush(bodyFatPercentageSummary);

        // Get all the bodyFatPercentageSummaryList where endTime in DEFAULT_END_TIME or UPDATED_END_TIME
        defaultBodyFatPercentageSummaryShouldBeFound("endTime.in=" + DEFAULT_END_TIME + "," + UPDATED_END_TIME);

        // Get all the bodyFatPercentageSummaryList where endTime equals to UPDATED_END_TIME
        defaultBodyFatPercentageSummaryShouldNotBeFound("endTime.in=" + UPDATED_END_TIME);
    }

    @Test
    @Transactional
    void getAllBodyFatPercentageSummariesByEndTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        bodyFatPercentageSummaryRepository.saveAndFlush(bodyFatPercentageSummary);

        // Get all the bodyFatPercentageSummaryList where endTime is not null
        defaultBodyFatPercentageSummaryShouldBeFound("endTime.specified=true");

        // Get all the bodyFatPercentageSummaryList where endTime is null
        defaultBodyFatPercentageSummaryShouldNotBeFound("endTime.specified=false");
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultBodyFatPercentageSummaryShouldBeFound(String filter) throws Exception {
        restBodyFatPercentageSummaryMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bodyFatPercentageSummary.getId().toString())))
            .andExpect(jsonPath("$.[*].usuarioId").value(hasItem(DEFAULT_USUARIO_ID)))
            .andExpect(jsonPath("$.[*].empresaId").value(hasItem(DEFAULT_EMPRESA_ID)))
            .andExpect(jsonPath("$.[*].fieldAverage").value(hasItem(DEFAULT_FIELD_AVERAGE.doubleValue())))
            .andExpect(jsonPath("$.[*].fieldMax").value(hasItem(DEFAULT_FIELD_MAX.doubleValue())))
            .andExpect(jsonPath("$.[*].fieldMin").value(hasItem(DEFAULT_FIELD_MIN.doubleValue())))
            .andExpect(jsonPath("$.[*].startTime").value(hasItem(DEFAULT_START_TIME.toString())))
            .andExpect(jsonPath("$.[*].endTime").value(hasItem(DEFAULT_END_TIME.toString())));

        // Check, that the count call also returns 1
        restBodyFatPercentageSummaryMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultBodyFatPercentageSummaryShouldNotBeFound(String filter) throws Exception {
        restBodyFatPercentageSummaryMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restBodyFatPercentageSummaryMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingBodyFatPercentageSummary() throws Exception {
        // Get the bodyFatPercentageSummary
        restBodyFatPercentageSummaryMockMvc.perform(get(ENTITY_API_URL_ID, UUID.randomUUID().toString())).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingBodyFatPercentageSummary() throws Exception {
        // Initialize the database
        bodyFatPercentageSummaryRepository.saveAndFlush(bodyFatPercentageSummary);

        int databaseSizeBeforeUpdate = bodyFatPercentageSummaryRepository.findAll().size();

        // Update the bodyFatPercentageSummary
        BodyFatPercentageSummary updatedBodyFatPercentageSummary = bodyFatPercentageSummaryRepository
            .findById(bodyFatPercentageSummary.getId())
            .get();
        // Disconnect from session so that the updates on updatedBodyFatPercentageSummary are not directly saved in db
        em.detach(updatedBodyFatPercentageSummary);
        updatedBodyFatPercentageSummary
            .usuarioId(UPDATED_USUARIO_ID)
            .empresaId(UPDATED_EMPRESA_ID)
            .fieldAverage(UPDATED_FIELD_AVERAGE)
            .fieldMax(UPDATED_FIELD_MAX)
            .fieldMin(UPDATED_FIELD_MIN)
            .startTime(UPDATED_START_TIME)
            .endTime(UPDATED_END_TIME);

        restBodyFatPercentageSummaryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedBodyFatPercentageSummary.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedBodyFatPercentageSummary))
            )
            .andExpect(status().isOk());

        // Validate the BodyFatPercentageSummary in the database
        List<BodyFatPercentageSummary> bodyFatPercentageSummaryList = bodyFatPercentageSummaryRepository.findAll();
        assertThat(bodyFatPercentageSummaryList).hasSize(databaseSizeBeforeUpdate);
        BodyFatPercentageSummary testBodyFatPercentageSummary = bodyFatPercentageSummaryList.get(bodyFatPercentageSummaryList.size() - 1);
        assertThat(testBodyFatPercentageSummary.getUsuarioId()).isEqualTo(UPDATED_USUARIO_ID);
        assertThat(testBodyFatPercentageSummary.getEmpresaId()).isEqualTo(UPDATED_EMPRESA_ID);
        assertThat(testBodyFatPercentageSummary.getFieldAverage()).isEqualTo(UPDATED_FIELD_AVERAGE);
        assertThat(testBodyFatPercentageSummary.getFieldMax()).isEqualTo(UPDATED_FIELD_MAX);
        assertThat(testBodyFatPercentageSummary.getFieldMin()).isEqualTo(UPDATED_FIELD_MIN);
        assertThat(testBodyFatPercentageSummary.getStartTime()).isEqualTo(UPDATED_START_TIME);
        assertThat(testBodyFatPercentageSummary.getEndTime()).isEqualTo(UPDATED_END_TIME);
    }

    @Test
    @Transactional
    void putNonExistingBodyFatPercentageSummary() throws Exception {
        int databaseSizeBeforeUpdate = bodyFatPercentageSummaryRepository.findAll().size();
        bodyFatPercentageSummary.setId(UUID.randomUUID());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBodyFatPercentageSummaryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, bodyFatPercentageSummary.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bodyFatPercentageSummary))
            )
            .andExpect(status().isBadRequest());

        // Validate the BodyFatPercentageSummary in the database
        List<BodyFatPercentageSummary> bodyFatPercentageSummaryList = bodyFatPercentageSummaryRepository.findAll();
        assertThat(bodyFatPercentageSummaryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchBodyFatPercentageSummary() throws Exception {
        int databaseSizeBeforeUpdate = bodyFatPercentageSummaryRepository.findAll().size();
        bodyFatPercentageSummary.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBodyFatPercentageSummaryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bodyFatPercentageSummary))
            )
            .andExpect(status().isBadRequest());

        // Validate the BodyFatPercentageSummary in the database
        List<BodyFatPercentageSummary> bodyFatPercentageSummaryList = bodyFatPercentageSummaryRepository.findAll();
        assertThat(bodyFatPercentageSummaryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamBodyFatPercentageSummary() throws Exception {
        int databaseSizeBeforeUpdate = bodyFatPercentageSummaryRepository.findAll().size();
        bodyFatPercentageSummary.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBodyFatPercentageSummaryMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bodyFatPercentageSummary))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the BodyFatPercentageSummary in the database
        List<BodyFatPercentageSummary> bodyFatPercentageSummaryList = bodyFatPercentageSummaryRepository.findAll();
        assertThat(bodyFatPercentageSummaryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateBodyFatPercentageSummaryWithPatch() throws Exception {
        // Initialize the database
        bodyFatPercentageSummaryRepository.saveAndFlush(bodyFatPercentageSummary);

        int databaseSizeBeforeUpdate = bodyFatPercentageSummaryRepository.findAll().size();

        // Update the bodyFatPercentageSummary using partial update
        BodyFatPercentageSummary partialUpdatedBodyFatPercentageSummary = new BodyFatPercentageSummary();
        partialUpdatedBodyFatPercentageSummary.setId(bodyFatPercentageSummary.getId());

        partialUpdatedBodyFatPercentageSummary
            .usuarioId(UPDATED_USUARIO_ID)
            .empresaId(UPDATED_EMPRESA_ID)
            .fieldAverage(UPDATED_FIELD_AVERAGE);

        restBodyFatPercentageSummaryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBodyFatPercentageSummary.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBodyFatPercentageSummary))
            )
            .andExpect(status().isOk());

        // Validate the BodyFatPercentageSummary in the database
        List<BodyFatPercentageSummary> bodyFatPercentageSummaryList = bodyFatPercentageSummaryRepository.findAll();
        assertThat(bodyFatPercentageSummaryList).hasSize(databaseSizeBeforeUpdate);
        BodyFatPercentageSummary testBodyFatPercentageSummary = bodyFatPercentageSummaryList.get(bodyFatPercentageSummaryList.size() - 1);
        assertThat(testBodyFatPercentageSummary.getUsuarioId()).isEqualTo(UPDATED_USUARIO_ID);
        assertThat(testBodyFatPercentageSummary.getEmpresaId()).isEqualTo(UPDATED_EMPRESA_ID);
        assertThat(testBodyFatPercentageSummary.getFieldAverage()).isEqualTo(UPDATED_FIELD_AVERAGE);
        assertThat(testBodyFatPercentageSummary.getFieldMax()).isEqualTo(DEFAULT_FIELD_MAX);
        assertThat(testBodyFatPercentageSummary.getFieldMin()).isEqualTo(DEFAULT_FIELD_MIN);
        assertThat(testBodyFatPercentageSummary.getStartTime()).isEqualTo(DEFAULT_START_TIME);
        assertThat(testBodyFatPercentageSummary.getEndTime()).isEqualTo(DEFAULT_END_TIME);
    }

    @Test
    @Transactional
    void fullUpdateBodyFatPercentageSummaryWithPatch() throws Exception {
        // Initialize the database
        bodyFatPercentageSummaryRepository.saveAndFlush(bodyFatPercentageSummary);

        int databaseSizeBeforeUpdate = bodyFatPercentageSummaryRepository.findAll().size();

        // Update the bodyFatPercentageSummary using partial update
        BodyFatPercentageSummary partialUpdatedBodyFatPercentageSummary = new BodyFatPercentageSummary();
        partialUpdatedBodyFatPercentageSummary.setId(bodyFatPercentageSummary.getId());

        partialUpdatedBodyFatPercentageSummary
            .usuarioId(UPDATED_USUARIO_ID)
            .empresaId(UPDATED_EMPRESA_ID)
            .fieldAverage(UPDATED_FIELD_AVERAGE)
            .fieldMax(UPDATED_FIELD_MAX)
            .fieldMin(UPDATED_FIELD_MIN)
            .startTime(UPDATED_START_TIME)
            .endTime(UPDATED_END_TIME);

        restBodyFatPercentageSummaryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBodyFatPercentageSummary.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBodyFatPercentageSummary))
            )
            .andExpect(status().isOk());

        // Validate the BodyFatPercentageSummary in the database
        List<BodyFatPercentageSummary> bodyFatPercentageSummaryList = bodyFatPercentageSummaryRepository.findAll();
        assertThat(bodyFatPercentageSummaryList).hasSize(databaseSizeBeforeUpdate);
        BodyFatPercentageSummary testBodyFatPercentageSummary = bodyFatPercentageSummaryList.get(bodyFatPercentageSummaryList.size() - 1);
        assertThat(testBodyFatPercentageSummary.getUsuarioId()).isEqualTo(UPDATED_USUARIO_ID);
        assertThat(testBodyFatPercentageSummary.getEmpresaId()).isEqualTo(UPDATED_EMPRESA_ID);
        assertThat(testBodyFatPercentageSummary.getFieldAverage()).isEqualTo(UPDATED_FIELD_AVERAGE);
        assertThat(testBodyFatPercentageSummary.getFieldMax()).isEqualTo(UPDATED_FIELD_MAX);
        assertThat(testBodyFatPercentageSummary.getFieldMin()).isEqualTo(UPDATED_FIELD_MIN);
        assertThat(testBodyFatPercentageSummary.getStartTime()).isEqualTo(UPDATED_START_TIME);
        assertThat(testBodyFatPercentageSummary.getEndTime()).isEqualTo(UPDATED_END_TIME);
    }

    @Test
    @Transactional
    void patchNonExistingBodyFatPercentageSummary() throws Exception {
        int databaseSizeBeforeUpdate = bodyFatPercentageSummaryRepository.findAll().size();
        bodyFatPercentageSummary.setId(UUID.randomUUID());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBodyFatPercentageSummaryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, bodyFatPercentageSummary.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(bodyFatPercentageSummary))
            )
            .andExpect(status().isBadRequest());

        // Validate the BodyFatPercentageSummary in the database
        List<BodyFatPercentageSummary> bodyFatPercentageSummaryList = bodyFatPercentageSummaryRepository.findAll();
        assertThat(bodyFatPercentageSummaryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchBodyFatPercentageSummary() throws Exception {
        int databaseSizeBeforeUpdate = bodyFatPercentageSummaryRepository.findAll().size();
        bodyFatPercentageSummary.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBodyFatPercentageSummaryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(bodyFatPercentageSummary))
            )
            .andExpect(status().isBadRequest());

        // Validate the BodyFatPercentageSummary in the database
        List<BodyFatPercentageSummary> bodyFatPercentageSummaryList = bodyFatPercentageSummaryRepository.findAll();
        assertThat(bodyFatPercentageSummaryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamBodyFatPercentageSummary() throws Exception {
        int databaseSizeBeforeUpdate = bodyFatPercentageSummaryRepository.findAll().size();
        bodyFatPercentageSummary.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBodyFatPercentageSummaryMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(bodyFatPercentageSummary))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the BodyFatPercentageSummary in the database
        List<BodyFatPercentageSummary> bodyFatPercentageSummaryList = bodyFatPercentageSummaryRepository.findAll();
        assertThat(bodyFatPercentageSummaryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteBodyFatPercentageSummary() throws Exception {
        // Initialize the database
        bodyFatPercentageSummaryRepository.saveAndFlush(bodyFatPercentageSummary);

        int databaseSizeBeforeDelete = bodyFatPercentageSummaryRepository.findAll().size();

        // Delete the bodyFatPercentageSummary
        restBodyFatPercentageSummaryMockMvc
            .perform(delete(ENTITY_API_URL_ID, bodyFatPercentageSummary.getId().toString()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<BodyFatPercentageSummary> bodyFatPercentageSummaryList = bodyFatPercentageSummaryRepository.findAll();
        assertThat(bodyFatPercentageSummaryList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
