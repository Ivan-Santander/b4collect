package com.be4tech.b4carecollect.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.be4tech.b4carecollect.IntegrationTest;
import com.be4tech.b4carecollect.domain.FuntionalIndexSummary;
import com.be4tech.b4carecollect.repository.FuntionalIndexSummaryRepository;
import com.be4tech.b4carecollect.service.criteria.FuntionalIndexSummaryCriteria;
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
 * Integration tests for the {@link FuntionalIndexSummaryResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class FuntionalIndexSummaryResourceIT {

    private static final String DEFAULT_USUARIO_ID = "AAAAAAAAAA";
    private static final String UPDATED_USUARIO_ID = "BBBBBBBBBB";

    private static final String DEFAULT_EMPRESA_ID = "AAAAAAAAAA";
    private static final String UPDATED_EMPRESA_ID = "BBBBBBBBBB";

    private static final Float DEFAULT_FIELD_FUNTIONAL_INDEX_AVERAGE = 1F;
    private static final Float UPDATED_FIELD_FUNTIONAL_INDEX_AVERAGE = 2F;
    private static final Float SMALLER_FIELD_FUNTIONAL_INDEX_AVERAGE = 1F - 1F;

    private static final Float DEFAULT_FIELD_FUNTIONAL_INDEX_MAX = 1F;
    private static final Float UPDATED_FIELD_FUNTIONAL_INDEX_MAX = 2F;
    private static final Float SMALLER_FIELD_FUNTIONAL_INDEX_MAX = 1F - 1F;

    private static final Float DEFAULT_FIELD_FUNTIONAL_INDEX_MIN = 1F;
    private static final Float UPDATED_FIELD_FUNTIONAL_INDEX_MIN = 2F;
    private static final Float SMALLER_FIELD_FUNTIONAL_INDEX_MIN = 1F - 1F;

    private static final Instant DEFAULT_START_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_START_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_END_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_END_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/funtional-index-summaries";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private FuntionalIndexSummaryRepository funtionalIndexSummaryRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFuntionalIndexSummaryMockMvc;

    private FuntionalIndexSummary funtionalIndexSummary;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FuntionalIndexSummary createEntity(EntityManager em) {
        FuntionalIndexSummary funtionalIndexSummary = new FuntionalIndexSummary()
            .usuarioId(DEFAULT_USUARIO_ID)
            .empresaId(DEFAULT_EMPRESA_ID)
            .fieldFuntionalIndexAverage(DEFAULT_FIELD_FUNTIONAL_INDEX_AVERAGE)
            .fieldFuntionalIndexMax(DEFAULT_FIELD_FUNTIONAL_INDEX_MAX)
            .fieldFuntionalIndexMin(DEFAULT_FIELD_FUNTIONAL_INDEX_MIN)
            .startTime(DEFAULT_START_TIME)
            .endTime(DEFAULT_END_TIME);
        return funtionalIndexSummary;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FuntionalIndexSummary createUpdatedEntity(EntityManager em) {
        FuntionalIndexSummary funtionalIndexSummary = new FuntionalIndexSummary()
            .usuarioId(UPDATED_USUARIO_ID)
            .empresaId(UPDATED_EMPRESA_ID)
            .fieldFuntionalIndexAverage(UPDATED_FIELD_FUNTIONAL_INDEX_AVERAGE)
            .fieldFuntionalIndexMax(UPDATED_FIELD_FUNTIONAL_INDEX_MAX)
            .fieldFuntionalIndexMin(UPDATED_FIELD_FUNTIONAL_INDEX_MIN)
            .startTime(UPDATED_START_TIME)
            .endTime(UPDATED_END_TIME);
        return funtionalIndexSummary;
    }

    @BeforeEach
    public void initTest() {
        funtionalIndexSummary = createEntity(em);
    }

    @Test
    @Transactional
    void createFuntionalIndexSummary() throws Exception {
        int databaseSizeBeforeCreate = funtionalIndexSummaryRepository.findAll().size();
        // Create the FuntionalIndexSummary
        restFuntionalIndexSummaryMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(funtionalIndexSummary))
            )
            .andExpect(status().isCreated());

        // Validate the FuntionalIndexSummary in the database
        List<FuntionalIndexSummary> funtionalIndexSummaryList = funtionalIndexSummaryRepository.findAll();
        assertThat(funtionalIndexSummaryList).hasSize(databaseSizeBeforeCreate + 1);
        FuntionalIndexSummary testFuntionalIndexSummary = funtionalIndexSummaryList.get(funtionalIndexSummaryList.size() - 1);
        assertThat(testFuntionalIndexSummary.getUsuarioId()).isEqualTo(DEFAULT_USUARIO_ID);
        assertThat(testFuntionalIndexSummary.getEmpresaId()).isEqualTo(DEFAULT_EMPRESA_ID);
        assertThat(testFuntionalIndexSummary.getFieldFuntionalIndexAverage()).isEqualTo(DEFAULT_FIELD_FUNTIONAL_INDEX_AVERAGE);
        assertThat(testFuntionalIndexSummary.getFieldFuntionalIndexMax()).isEqualTo(DEFAULT_FIELD_FUNTIONAL_INDEX_MAX);
        assertThat(testFuntionalIndexSummary.getFieldFuntionalIndexMin()).isEqualTo(DEFAULT_FIELD_FUNTIONAL_INDEX_MIN);
        assertThat(testFuntionalIndexSummary.getStartTime()).isEqualTo(DEFAULT_START_TIME);
        assertThat(testFuntionalIndexSummary.getEndTime()).isEqualTo(DEFAULT_END_TIME);
    }

    @Test
    @Transactional
    void createFuntionalIndexSummaryWithExistingId() throws Exception {
        // Create the FuntionalIndexSummary with an existing ID
        funtionalIndexSummaryRepository.saveAndFlush(funtionalIndexSummary);

        int databaseSizeBeforeCreate = funtionalIndexSummaryRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFuntionalIndexSummaryMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(funtionalIndexSummary))
            )
            .andExpect(status().isBadRequest());

        // Validate the FuntionalIndexSummary in the database
        List<FuntionalIndexSummary> funtionalIndexSummaryList = funtionalIndexSummaryRepository.findAll();
        assertThat(funtionalIndexSummaryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllFuntionalIndexSummaries() throws Exception {
        // Initialize the database
        funtionalIndexSummaryRepository.saveAndFlush(funtionalIndexSummary);

        // Get all the funtionalIndexSummaryList
        restFuntionalIndexSummaryMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(funtionalIndexSummary.getId().toString())))
            .andExpect(jsonPath("$.[*].usuarioId").value(hasItem(DEFAULT_USUARIO_ID)))
            .andExpect(jsonPath("$.[*].empresaId").value(hasItem(DEFAULT_EMPRESA_ID)))
            .andExpect(jsonPath("$.[*].fieldFuntionalIndexAverage").value(hasItem(DEFAULT_FIELD_FUNTIONAL_INDEX_AVERAGE.doubleValue())))
            .andExpect(jsonPath("$.[*].fieldFuntionalIndexMax").value(hasItem(DEFAULT_FIELD_FUNTIONAL_INDEX_MAX.doubleValue())))
            .andExpect(jsonPath("$.[*].fieldFuntionalIndexMin").value(hasItem(DEFAULT_FIELD_FUNTIONAL_INDEX_MIN.doubleValue())))
            .andExpect(jsonPath("$.[*].startTime").value(hasItem(DEFAULT_START_TIME.toString())))
            .andExpect(jsonPath("$.[*].endTime").value(hasItem(DEFAULT_END_TIME.toString())));
    }

    @Test
    @Transactional
    void getFuntionalIndexSummary() throws Exception {
        // Initialize the database
        funtionalIndexSummaryRepository.saveAndFlush(funtionalIndexSummary);

        // Get the funtionalIndexSummary
        restFuntionalIndexSummaryMockMvc
            .perform(get(ENTITY_API_URL_ID, funtionalIndexSummary.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(funtionalIndexSummary.getId().toString()))
            .andExpect(jsonPath("$.usuarioId").value(DEFAULT_USUARIO_ID))
            .andExpect(jsonPath("$.empresaId").value(DEFAULT_EMPRESA_ID))
            .andExpect(jsonPath("$.fieldFuntionalIndexAverage").value(DEFAULT_FIELD_FUNTIONAL_INDEX_AVERAGE.doubleValue()))
            .andExpect(jsonPath("$.fieldFuntionalIndexMax").value(DEFAULT_FIELD_FUNTIONAL_INDEX_MAX.doubleValue()))
            .andExpect(jsonPath("$.fieldFuntionalIndexMin").value(DEFAULT_FIELD_FUNTIONAL_INDEX_MIN.doubleValue()))
            .andExpect(jsonPath("$.startTime").value(DEFAULT_START_TIME.toString()))
            .andExpect(jsonPath("$.endTime").value(DEFAULT_END_TIME.toString()));
    }

    @Test
    @Transactional
    void getFuntionalIndexSummariesByIdFiltering() throws Exception {
        // Initialize the database
        funtionalIndexSummaryRepository.saveAndFlush(funtionalIndexSummary);

        UUID id = funtionalIndexSummary.getId();

        defaultFuntionalIndexSummaryShouldBeFound("id.equals=" + id);
        defaultFuntionalIndexSummaryShouldNotBeFound("id.notEquals=" + id);
    }

    @Test
    @Transactional
    void getAllFuntionalIndexSummariesByUsuarioIdIsEqualToSomething() throws Exception {
        // Initialize the database
        funtionalIndexSummaryRepository.saveAndFlush(funtionalIndexSummary);

        // Get all the funtionalIndexSummaryList where usuarioId equals to DEFAULT_USUARIO_ID
        defaultFuntionalIndexSummaryShouldBeFound("usuarioId.equals=" + DEFAULT_USUARIO_ID);

        // Get all the funtionalIndexSummaryList where usuarioId equals to UPDATED_USUARIO_ID
        defaultFuntionalIndexSummaryShouldNotBeFound("usuarioId.equals=" + UPDATED_USUARIO_ID);
    }

    @Test
    @Transactional
    void getAllFuntionalIndexSummariesByUsuarioIdIsInShouldWork() throws Exception {
        // Initialize the database
        funtionalIndexSummaryRepository.saveAndFlush(funtionalIndexSummary);

        // Get all the funtionalIndexSummaryList where usuarioId in DEFAULT_USUARIO_ID or UPDATED_USUARIO_ID
        defaultFuntionalIndexSummaryShouldBeFound("usuarioId.in=" + DEFAULT_USUARIO_ID + "," + UPDATED_USUARIO_ID);

        // Get all the funtionalIndexSummaryList where usuarioId equals to UPDATED_USUARIO_ID
        defaultFuntionalIndexSummaryShouldNotBeFound("usuarioId.in=" + UPDATED_USUARIO_ID);
    }

    @Test
    @Transactional
    void getAllFuntionalIndexSummariesByUsuarioIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        funtionalIndexSummaryRepository.saveAndFlush(funtionalIndexSummary);

        // Get all the funtionalIndexSummaryList where usuarioId is not null
        defaultFuntionalIndexSummaryShouldBeFound("usuarioId.specified=true");

        // Get all the funtionalIndexSummaryList where usuarioId is null
        defaultFuntionalIndexSummaryShouldNotBeFound("usuarioId.specified=false");
    }

    @Test
    @Transactional
    void getAllFuntionalIndexSummariesByUsuarioIdContainsSomething() throws Exception {
        // Initialize the database
        funtionalIndexSummaryRepository.saveAndFlush(funtionalIndexSummary);

        // Get all the funtionalIndexSummaryList where usuarioId contains DEFAULT_USUARIO_ID
        defaultFuntionalIndexSummaryShouldBeFound("usuarioId.contains=" + DEFAULT_USUARIO_ID);

        // Get all the funtionalIndexSummaryList where usuarioId contains UPDATED_USUARIO_ID
        defaultFuntionalIndexSummaryShouldNotBeFound("usuarioId.contains=" + UPDATED_USUARIO_ID);
    }

    @Test
    @Transactional
    void getAllFuntionalIndexSummariesByUsuarioIdNotContainsSomething() throws Exception {
        // Initialize the database
        funtionalIndexSummaryRepository.saveAndFlush(funtionalIndexSummary);

        // Get all the funtionalIndexSummaryList where usuarioId does not contain DEFAULT_USUARIO_ID
        defaultFuntionalIndexSummaryShouldNotBeFound("usuarioId.doesNotContain=" + DEFAULT_USUARIO_ID);

        // Get all the funtionalIndexSummaryList where usuarioId does not contain UPDATED_USUARIO_ID
        defaultFuntionalIndexSummaryShouldBeFound("usuarioId.doesNotContain=" + UPDATED_USUARIO_ID);
    }

    @Test
    @Transactional
    void getAllFuntionalIndexSummariesByEmpresaIdIsEqualToSomething() throws Exception {
        // Initialize the database
        funtionalIndexSummaryRepository.saveAndFlush(funtionalIndexSummary);

        // Get all the funtionalIndexSummaryList where empresaId equals to DEFAULT_EMPRESA_ID
        defaultFuntionalIndexSummaryShouldBeFound("empresaId.equals=" + DEFAULT_EMPRESA_ID);

        // Get all the funtionalIndexSummaryList where empresaId equals to UPDATED_EMPRESA_ID
        defaultFuntionalIndexSummaryShouldNotBeFound("empresaId.equals=" + UPDATED_EMPRESA_ID);
    }

    @Test
    @Transactional
    void getAllFuntionalIndexSummariesByEmpresaIdIsInShouldWork() throws Exception {
        // Initialize the database
        funtionalIndexSummaryRepository.saveAndFlush(funtionalIndexSummary);

        // Get all the funtionalIndexSummaryList where empresaId in DEFAULT_EMPRESA_ID or UPDATED_EMPRESA_ID
        defaultFuntionalIndexSummaryShouldBeFound("empresaId.in=" + DEFAULT_EMPRESA_ID + "," + UPDATED_EMPRESA_ID);

        // Get all the funtionalIndexSummaryList where empresaId equals to UPDATED_EMPRESA_ID
        defaultFuntionalIndexSummaryShouldNotBeFound("empresaId.in=" + UPDATED_EMPRESA_ID);
    }

    @Test
    @Transactional
    void getAllFuntionalIndexSummariesByEmpresaIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        funtionalIndexSummaryRepository.saveAndFlush(funtionalIndexSummary);

        // Get all the funtionalIndexSummaryList where empresaId is not null
        defaultFuntionalIndexSummaryShouldBeFound("empresaId.specified=true");

        // Get all the funtionalIndexSummaryList where empresaId is null
        defaultFuntionalIndexSummaryShouldNotBeFound("empresaId.specified=false");
    }

    @Test
    @Transactional
    void getAllFuntionalIndexSummariesByEmpresaIdContainsSomething() throws Exception {
        // Initialize the database
        funtionalIndexSummaryRepository.saveAndFlush(funtionalIndexSummary);

        // Get all the funtionalIndexSummaryList where empresaId contains DEFAULT_EMPRESA_ID
        defaultFuntionalIndexSummaryShouldBeFound("empresaId.contains=" + DEFAULT_EMPRESA_ID);

        // Get all the funtionalIndexSummaryList where empresaId contains UPDATED_EMPRESA_ID
        defaultFuntionalIndexSummaryShouldNotBeFound("empresaId.contains=" + UPDATED_EMPRESA_ID);
    }

    @Test
    @Transactional
    void getAllFuntionalIndexSummariesByEmpresaIdNotContainsSomething() throws Exception {
        // Initialize the database
        funtionalIndexSummaryRepository.saveAndFlush(funtionalIndexSummary);

        // Get all the funtionalIndexSummaryList where empresaId does not contain DEFAULT_EMPRESA_ID
        defaultFuntionalIndexSummaryShouldNotBeFound("empresaId.doesNotContain=" + DEFAULT_EMPRESA_ID);

        // Get all the funtionalIndexSummaryList where empresaId does not contain UPDATED_EMPRESA_ID
        defaultFuntionalIndexSummaryShouldBeFound("empresaId.doesNotContain=" + UPDATED_EMPRESA_ID);
    }

    @Test
    @Transactional
    void getAllFuntionalIndexSummariesByFieldFuntionalIndexAverageIsEqualToSomething() throws Exception {
        // Initialize the database
        funtionalIndexSummaryRepository.saveAndFlush(funtionalIndexSummary);

        // Get all the funtionalIndexSummaryList where fieldFuntionalIndexAverage equals to DEFAULT_FIELD_FUNTIONAL_INDEX_AVERAGE
        defaultFuntionalIndexSummaryShouldBeFound("fieldFuntionalIndexAverage.equals=" + DEFAULT_FIELD_FUNTIONAL_INDEX_AVERAGE);

        // Get all the funtionalIndexSummaryList where fieldFuntionalIndexAverage equals to UPDATED_FIELD_FUNTIONAL_INDEX_AVERAGE
        defaultFuntionalIndexSummaryShouldNotBeFound("fieldFuntionalIndexAverage.equals=" + UPDATED_FIELD_FUNTIONAL_INDEX_AVERAGE);
    }

    @Test
    @Transactional
    void getAllFuntionalIndexSummariesByFieldFuntionalIndexAverageIsInShouldWork() throws Exception {
        // Initialize the database
        funtionalIndexSummaryRepository.saveAndFlush(funtionalIndexSummary);

        // Get all the funtionalIndexSummaryList where fieldFuntionalIndexAverage in DEFAULT_FIELD_FUNTIONAL_INDEX_AVERAGE or UPDATED_FIELD_FUNTIONAL_INDEX_AVERAGE
        defaultFuntionalIndexSummaryShouldBeFound(
            "fieldFuntionalIndexAverage.in=" + DEFAULT_FIELD_FUNTIONAL_INDEX_AVERAGE + "," + UPDATED_FIELD_FUNTIONAL_INDEX_AVERAGE
        );

        // Get all the funtionalIndexSummaryList where fieldFuntionalIndexAverage equals to UPDATED_FIELD_FUNTIONAL_INDEX_AVERAGE
        defaultFuntionalIndexSummaryShouldNotBeFound("fieldFuntionalIndexAverage.in=" + UPDATED_FIELD_FUNTIONAL_INDEX_AVERAGE);
    }

    @Test
    @Transactional
    void getAllFuntionalIndexSummariesByFieldFuntionalIndexAverageIsNullOrNotNull() throws Exception {
        // Initialize the database
        funtionalIndexSummaryRepository.saveAndFlush(funtionalIndexSummary);

        // Get all the funtionalIndexSummaryList where fieldFuntionalIndexAverage is not null
        defaultFuntionalIndexSummaryShouldBeFound("fieldFuntionalIndexAverage.specified=true");

        // Get all the funtionalIndexSummaryList where fieldFuntionalIndexAverage is null
        defaultFuntionalIndexSummaryShouldNotBeFound("fieldFuntionalIndexAverage.specified=false");
    }

    @Test
    @Transactional
    void getAllFuntionalIndexSummariesByFieldFuntionalIndexAverageIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        funtionalIndexSummaryRepository.saveAndFlush(funtionalIndexSummary);

        // Get all the funtionalIndexSummaryList where fieldFuntionalIndexAverage is greater than or equal to DEFAULT_FIELD_FUNTIONAL_INDEX_AVERAGE
        defaultFuntionalIndexSummaryShouldBeFound("fieldFuntionalIndexAverage.greaterThanOrEqual=" + DEFAULT_FIELD_FUNTIONAL_INDEX_AVERAGE);

        // Get all the funtionalIndexSummaryList where fieldFuntionalIndexAverage is greater than or equal to UPDATED_FIELD_FUNTIONAL_INDEX_AVERAGE
        defaultFuntionalIndexSummaryShouldNotBeFound(
            "fieldFuntionalIndexAverage.greaterThanOrEqual=" + UPDATED_FIELD_FUNTIONAL_INDEX_AVERAGE
        );
    }

    @Test
    @Transactional
    void getAllFuntionalIndexSummariesByFieldFuntionalIndexAverageIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        funtionalIndexSummaryRepository.saveAndFlush(funtionalIndexSummary);

        // Get all the funtionalIndexSummaryList where fieldFuntionalIndexAverage is less than or equal to DEFAULT_FIELD_FUNTIONAL_INDEX_AVERAGE
        defaultFuntionalIndexSummaryShouldBeFound("fieldFuntionalIndexAverage.lessThanOrEqual=" + DEFAULT_FIELD_FUNTIONAL_INDEX_AVERAGE);

        // Get all the funtionalIndexSummaryList where fieldFuntionalIndexAverage is less than or equal to SMALLER_FIELD_FUNTIONAL_INDEX_AVERAGE
        defaultFuntionalIndexSummaryShouldNotBeFound("fieldFuntionalIndexAverage.lessThanOrEqual=" + SMALLER_FIELD_FUNTIONAL_INDEX_AVERAGE);
    }

    @Test
    @Transactional
    void getAllFuntionalIndexSummariesByFieldFuntionalIndexAverageIsLessThanSomething() throws Exception {
        // Initialize the database
        funtionalIndexSummaryRepository.saveAndFlush(funtionalIndexSummary);

        // Get all the funtionalIndexSummaryList where fieldFuntionalIndexAverage is less than DEFAULT_FIELD_FUNTIONAL_INDEX_AVERAGE
        defaultFuntionalIndexSummaryShouldNotBeFound("fieldFuntionalIndexAverage.lessThan=" + DEFAULT_FIELD_FUNTIONAL_INDEX_AVERAGE);

        // Get all the funtionalIndexSummaryList where fieldFuntionalIndexAverage is less than UPDATED_FIELD_FUNTIONAL_INDEX_AVERAGE
        defaultFuntionalIndexSummaryShouldBeFound("fieldFuntionalIndexAverage.lessThan=" + UPDATED_FIELD_FUNTIONAL_INDEX_AVERAGE);
    }

    @Test
    @Transactional
    void getAllFuntionalIndexSummariesByFieldFuntionalIndexAverageIsGreaterThanSomething() throws Exception {
        // Initialize the database
        funtionalIndexSummaryRepository.saveAndFlush(funtionalIndexSummary);

        // Get all the funtionalIndexSummaryList where fieldFuntionalIndexAverage is greater than DEFAULT_FIELD_FUNTIONAL_INDEX_AVERAGE
        defaultFuntionalIndexSummaryShouldNotBeFound("fieldFuntionalIndexAverage.greaterThan=" + DEFAULT_FIELD_FUNTIONAL_INDEX_AVERAGE);

        // Get all the funtionalIndexSummaryList where fieldFuntionalIndexAverage is greater than SMALLER_FIELD_FUNTIONAL_INDEX_AVERAGE
        defaultFuntionalIndexSummaryShouldBeFound("fieldFuntionalIndexAverage.greaterThan=" + SMALLER_FIELD_FUNTIONAL_INDEX_AVERAGE);
    }

    @Test
    @Transactional
    void getAllFuntionalIndexSummariesByFieldFuntionalIndexMaxIsEqualToSomething() throws Exception {
        // Initialize the database
        funtionalIndexSummaryRepository.saveAndFlush(funtionalIndexSummary);

        // Get all the funtionalIndexSummaryList where fieldFuntionalIndexMax equals to DEFAULT_FIELD_FUNTIONAL_INDEX_MAX
        defaultFuntionalIndexSummaryShouldBeFound("fieldFuntionalIndexMax.equals=" + DEFAULT_FIELD_FUNTIONAL_INDEX_MAX);

        // Get all the funtionalIndexSummaryList where fieldFuntionalIndexMax equals to UPDATED_FIELD_FUNTIONAL_INDEX_MAX
        defaultFuntionalIndexSummaryShouldNotBeFound("fieldFuntionalIndexMax.equals=" + UPDATED_FIELD_FUNTIONAL_INDEX_MAX);
    }

    @Test
    @Transactional
    void getAllFuntionalIndexSummariesByFieldFuntionalIndexMaxIsInShouldWork() throws Exception {
        // Initialize the database
        funtionalIndexSummaryRepository.saveAndFlush(funtionalIndexSummary);

        // Get all the funtionalIndexSummaryList where fieldFuntionalIndexMax in DEFAULT_FIELD_FUNTIONAL_INDEX_MAX or UPDATED_FIELD_FUNTIONAL_INDEX_MAX
        defaultFuntionalIndexSummaryShouldBeFound(
            "fieldFuntionalIndexMax.in=" + DEFAULT_FIELD_FUNTIONAL_INDEX_MAX + "," + UPDATED_FIELD_FUNTIONAL_INDEX_MAX
        );

        // Get all the funtionalIndexSummaryList where fieldFuntionalIndexMax equals to UPDATED_FIELD_FUNTIONAL_INDEX_MAX
        defaultFuntionalIndexSummaryShouldNotBeFound("fieldFuntionalIndexMax.in=" + UPDATED_FIELD_FUNTIONAL_INDEX_MAX);
    }

    @Test
    @Transactional
    void getAllFuntionalIndexSummariesByFieldFuntionalIndexMaxIsNullOrNotNull() throws Exception {
        // Initialize the database
        funtionalIndexSummaryRepository.saveAndFlush(funtionalIndexSummary);

        // Get all the funtionalIndexSummaryList where fieldFuntionalIndexMax is not null
        defaultFuntionalIndexSummaryShouldBeFound("fieldFuntionalIndexMax.specified=true");

        // Get all the funtionalIndexSummaryList where fieldFuntionalIndexMax is null
        defaultFuntionalIndexSummaryShouldNotBeFound("fieldFuntionalIndexMax.specified=false");
    }

    @Test
    @Transactional
    void getAllFuntionalIndexSummariesByFieldFuntionalIndexMaxIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        funtionalIndexSummaryRepository.saveAndFlush(funtionalIndexSummary);

        // Get all the funtionalIndexSummaryList where fieldFuntionalIndexMax is greater than or equal to DEFAULT_FIELD_FUNTIONAL_INDEX_MAX
        defaultFuntionalIndexSummaryShouldBeFound("fieldFuntionalIndexMax.greaterThanOrEqual=" + DEFAULT_FIELD_FUNTIONAL_INDEX_MAX);

        // Get all the funtionalIndexSummaryList where fieldFuntionalIndexMax is greater than or equal to UPDATED_FIELD_FUNTIONAL_INDEX_MAX
        defaultFuntionalIndexSummaryShouldNotBeFound("fieldFuntionalIndexMax.greaterThanOrEqual=" + UPDATED_FIELD_FUNTIONAL_INDEX_MAX);
    }

    @Test
    @Transactional
    void getAllFuntionalIndexSummariesByFieldFuntionalIndexMaxIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        funtionalIndexSummaryRepository.saveAndFlush(funtionalIndexSummary);

        // Get all the funtionalIndexSummaryList where fieldFuntionalIndexMax is less than or equal to DEFAULT_FIELD_FUNTIONAL_INDEX_MAX
        defaultFuntionalIndexSummaryShouldBeFound("fieldFuntionalIndexMax.lessThanOrEqual=" + DEFAULT_FIELD_FUNTIONAL_INDEX_MAX);

        // Get all the funtionalIndexSummaryList where fieldFuntionalIndexMax is less than or equal to SMALLER_FIELD_FUNTIONAL_INDEX_MAX
        defaultFuntionalIndexSummaryShouldNotBeFound("fieldFuntionalIndexMax.lessThanOrEqual=" + SMALLER_FIELD_FUNTIONAL_INDEX_MAX);
    }

    @Test
    @Transactional
    void getAllFuntionalIndexSummariesByFieldFuntionalIndexMaxIsLessThanSomething() throws Exception {
        // Initialize the database
        funtionalIndexSummaryRepository.saveAndFlush(funtionalIndexSummary);

        // Get all the funtionalIndexSummaryList where fieldFuntionalIndexMax is less than DEFAULT_FIELD_FUNTIONAL_INDEX_MAX
        defaultFuntionalIndexSummaryShouldNotBeFound("fieldFuntionalIndexMax.lessThan=" + DEFAULT_FIELD_FUNTIONAL_INDEX_MAX);

        // Get all the funtionalIndexSummaryList where fieldFuntionalIndexMax is less than UPDATED_FIELD_FUNTIONAL_INDEX_MAX
        defaultFuntionalIndexSummaryShouldBeFound("fieldFuntionalIndexMax.lessThan=" + UPDATED_FIELD_FUNTIONAL_INDEX_MAX);
    }

    @Test
    @Transactional
    void getAllFuntionalIndexSummariesByFieldFuntionalIndexMaxIsGreaterThanSomething() throws Exception {
        // Initialize the database
        funtionalIndexSummaryRepository.saveAndFlush(funtionalIndexSummary);

        // Get all the funtionalIndexSummaryList where fieldFuntionalIndexMax is greater than DEFAULT_FIELD_FUNTIONAL_INDEX_MAX
        defaultFuntionalIndexSummaryShouldNotBeFound("fieldFuntionalIndexMax.greaterThan=" + DEFAULT_FIELD_FUNTIONAL_INDEX_MAX);

        // Get all the funtionalIndexSummaryList where fieldFuntionalIndexMax is greater than SMALLER_FIELD_FUNTIONAL_INDEX_MAX
        defaultFuntionalIndexSummaryShouldBeFound("fieldFuntionalIndexMax.greaterThan=" + SMALLER_FIELD_FUNTIONAL_INDEX_MAX);
    }

    @Test
    @Transactional
    void getAllFuntionalIndexSummariesByFieldFuntionalIndexMinIsEqualToSomething() throws Exception {
        // Initialize the database
        funtionalIndexSummaryRepository.saveAndFlush(funtionalIndexSummary);

        // Get all the funtionalIndexSummaryList where fieldFuntionalIndexMin equals to DEFAULT_FIELD_FUNTIONAL_INDEX_MIN
        defaultFuntionalIndexSummaryShouldBeFound("fieldFuntionalIndexMin.equals=" + DEFAULT_FIELD_FUNTIONAL_INDEX_MIN);

        // Get all the funtionalIndexSummaryList where fieldFuntionalIndexMin equals to UPDATED_FIELD_FUNTIONAL_INDEX_MIN
        defaultFuntionalIndexSummaryShouldNotBeFound("fieldFuntionalIndexMin.equals=" + UPDATED_FIELD_FUNTIONAL_INDEX_MIN);
    }

    @Test
    @Transactional
    void getAllFuntionalIndexSummariesByFieldFuntionalIndexMinIsInShouldWork() throws Exception {
        // Initialize the database
        funtionalIndexSummaryRepository.saveAndFlush(funtionalIndexSummary);

        // Get all the funtionalIndexSummaryList where fieldFuntionalIndexMin in DEFAULT_FIELD_FUNTIONAL_INDEX_MIN or UPDATED_FIELD_FUNTIONAL_INDEX_MIN
        defaultFuntionalIndexSummaryShouldBeFound(
            "fieldFuntionalIndexMin.in=" + DEFAULT_FIELD_FUNTIONAL_INDEX_MIN + "," + UPDATED_FIELD_FUNTIONAL_INDEX_MIN
        );

        // Get all the funtionalIndexSummaryList where fieldFuntionalIndexMin equals to UPDATED_FIELD_FUNTIONAL_INDEX_MIN
        defaultFuntionalIndexSummaryShouldNotBeFound("fieldFuntionalIndexMin.in=" + UPDATED_FIELD_FUNTIONAL_INDEX_MIN);
    }

    @Test
    @Transactional
    void getAllFuntionalIndexSummariesByFieldFuntionalIndexMinIsNullOrNotNull() throws Exception {
        // Initialize the database
        funtionalIndexSummaryRepository.saveAndFlush(funtionalIndexSummary);

        // Get all the funtionalIndexSummaryList where fieldFuntionalIndexMin is not null
        defaultFuntionalIndexSummaryShouldBeFound("fieldFuntionalIndexMin.specified=true");

        // Get all the funtionalIndexSummaryList where fieldFuntionalIndexMin is null
        defaultFuntionalIndexSummaryShouldNotBeFound("fieldFuntionalIndexMin.specified=false");
    }

    @Test
    @Transactional
    void getAllFuntionalIndexSummariesByFieldFuntionalIndexMinIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        funtionalIndexSummaryRepository.saveAndFlush(funtionalIndexSummary);

        // Get all the funtionalIndexSummaryList where fieldFuntionalIndexMin is greater than or equal to DEFAULT_FIELD_FUNTIONAL_INDEX_MIN
        defaultFuntionalIndexSummaryShouldBeFound("fieldFuntionalIndexMin.greaterThanOrEqual=" + DEFAULT_FIELD_FUNTIONAL_INDEX_MIN);

        // Get all the funtionalIndexSummaryList where fieldFuntionalIndexMin is greater than or equal to UPDATED_FIELD_FUNTIONAL_INDEX_MIN
        defaultFuntionalIndexSummaryShouldNotBeFound("fieldFuntionalIndexMin.greaterThanOrEqual=" + UPDATED_FIELD_FUNTIONAL_INDEX_MIN);
    }

    @Test
    @Transactional
    void getAllFuntionalIndexSummariesByFieldFuntionalIndexMinIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        funtionalIndexSummaryRepository.saveAndFlush(funtionalIndexSummary);

        // Get all the funtionalIndexSummaryList where fieldFuntionalIndexMin is less than or equal to DEFAULT_FIELD_FUNTIONAL_INDEX_MIN
        defaultFuntionalIndexSummaryShouldBeFound("fieldFuntionalIndexMin.lessThanOrEqual=" + DEFAULT_FIELD_FUNTIONAL_INDEX_MIN);

        // Get all the funtionalIndexSummaryList where fieldFuntionalIndexMin is less than or equal to SMALLER_FIELD_FUNTIONAL_INDEX_MIN
        defaultFuntionalIndexSummaryShouldNotBeFound("fieldFuntionalIndexMin.lessThanOrEqual=" + SMALLER_FIELD_FUNTIONAL_INDEX_MIN);
    }

    @Test
    @Transactional
    void getAllFuntionalIndexSummariesByFieldFuntionalIndexMinIsLessThanSomething() throws Exception {
        // Initialize the database
        funtionalIndexSummaryRepository.saveAndFlush(funtionalIndexSummary);

        // Get all the funtionalIndexSummaryList where fieldFuntionalIndexMin is less than DEFAULT_FIELD_FUNTIONAL_INDEX_MIN
        defaultFuntionalIndexSummaryShouldNotBeFound("fieldFuntionalIndexMin.lessThan=" + DEFAULT_FIELD_FUNTIONAL_INDEX_MIN);

        // Get all the funtionalIndexSummaryList where fieldFuntionalIndexMin is less than UPDATED_FIELD_FUNTIONAL_INDEX_MIN
        defaultFuntionalIndexSummaryShouldBeFound("fieldFuntionalIndexMin.lessThan=" + UPDATED_FIELD_FUNTIONAL_INDEX_MIN);
    }

    @Test
    @Transactional
    void getAllFuntionalIndexSummariesByFieldFuntionalIndexMinIsGreaterThanSomething() throws Exception {
        // Initialize the database
        funtionalIndexSummaryRepository.saveAndFlush(funtionalIndexSummary);

        // Get all the funtionalIndexSummaryList where fieldFuntionalIndexMin is greater than DEFAULT_FIELD_FUNTIONAL_INDEX_MIN
        defaultFuntionalIndexSummaryShouldNotBeFound("fieldFuntionalIndexMin.greaterThan=" + DEFAULT_FIELD_FUNTIONAL_INDEX_MIN);

        // Get all the funtionalIndexSummaryList where fieldFuntionalIndexMin is greater than SMALLER_FIELD_FUNTIONAL_INDEX_MIN
        defaultFuntionalIndexSummaryShouldBeFound("fieldFuntionalIndexMin.greaterThan=" + SMALLER_FIELD_FUNTIONAL_INDEX_MIN);
    }

    @Test
    @Transactional
    void getAllFuntionalIndexSummariesByStartTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        funtionalIndexSummaryRepository.saveAndFlush(funtionalIndexSummary);

        // Get all the funtionalIndexSummaryList where startTime equals to DEFAULT_START_TIME
        defaultFuntionalIndexSummaryShouldBeFound("startTime.equals=" + DEFAULT_START_TIME);

        // Get all the funtionalIndexSummaryList where startTime equals to UPDATED_START_TIME
        defaultFuntionalIndexSummaryShouldNotBeFound("startTime.equals=" + UPDATED_START_TIME);
    }

    @Test
    @Transactional
    void getAllFuntionalIndexSummariesByStartTimeIsInShouldWork() throws Exception {
        // Initialize the database
        funtionalIndexSummaryRepository.saveAndFlush(funtionalIndexSummary);

        // Get all the funtionalIndexSummaryList where startTime in DEFAULT_START_TIME or UPDATED_START_TIME
        defaultFuntionalIndexSummaryShouldBeFound("startTime.in=" + DEFAULT_START_TIME + "," + UPDATED_START_TIME);

        // Get all the funtionalIndexSummaryList where startTime equals to UPDATED_START_TIME
        defaultFuntionalIndexSummaryShouldNotBeFound("startTime.in=" + UPDATED_START_TIME);
    }

    @Test
    @Transactional
    void getAllFuntionalIndexSummariesByStartTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        funtionalIndexSummaryRepository.saveAndFlush(funtionalIndexSummary);

        // Get all the funtionalIndexSummaryList where startTime is not null
        defaultFuntionalIndexSummaryShouldBeFound("startTime.specified=true");

        // Get all the funtionalIndexSummaryList where startTime is null
        defaultFuntionalIndexSummaryShouldNotBeFound("startTime.specified=false");
    }

    @Test
    @Transactional
    void getAllFuntionalIndexSummariesByEndTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        funtionalIndexSummaryRepository.saveAndFlush(funtionalIndexSummary);

        // Get all the funtionalIndexSummaryList where endTime equals to DEFAULT_END_TIME
        defaultFuntionalIndexSummaryShouldBeFound("endTime.equals=" + DEFAULT_END_TIME);

        // Get all the funtionalIndexSummaryList where endTime equals to UPDATED_END_TIME
        defaultFuntionalIndexSummaryShouldNotBeFound("endTime.equals=" + UPDATED_END_TIME);
    }

    @Test
    @Transactional
    void getAllFuntionalIndexSummariesByEndTimeIsInShouldWork() throws Exception {
        // Initialize the database
        funtionalIndexSummaryRepository.saveAndFlush(funtionalIndexSummary);

        // Get all the funtionalIndexSummaryList where endTime in DEFAULT_END_TIME or UPDATED_END_TIME
        defaultFuntionalIndexSummaryShouldBeFound("endTime.in=" + DEFAULT_END_TIME + "," + UPDATED_END_TIME);

        // Get all the funtionalIndexSummaryList where endTime equals to UPDATED_END_TIME
        defaultFuntionalIndexSummaryShouldNotBeFound("endTime.in=" + UPDATED_END_TIME);
    }

    @Test
    @Transactional
    void getAllFuntionalIndexSummariesByEndTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        funtionalIndexSummaryRepository.saveAndFlush(funtionalIndexSummary);

        // Get all the funtionalIndexSummaryList where endTime is not null
        defaultFuntionalIndexSummaryShouldBeFound("endTime.specified=true");

        // Get all the funtionalIndexSummaryList where endTime is null
        defaultFuntionalIndexSummaryShouldNotBeFound("endTime.specified=false");
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultFuntionalIndexSummaryShouldBeFound(String filter) throws Exception {
        restFuntionalIndexSummaryMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(funtionalIndexSummary.getId().toString())))
            .andExpect(jsonPath("$.[*].usuarioId").value(hasItem(DEFAULT_USUARIO_ID)))
            .andExpect(jsonPath("$.[*].empresaId").value(hasItem(DEFAULT_EMPRESA_ID)))
            .andExpect(jsonPath("$.[*].fieldFuntionalIndexAverage").value(hasItem(DEFAULT_FIELD_FUNTIONAL_INDEX_AVERAGE.doubleValue())))
            .andExpect(jsonPath("$.[*].fieldFuntionalIndexMax").value(hasItem(DEFAULT_FIELD_FUNTIONAL_INDEX_MAX.doubleValue())))
            .andExpect(jsonPath("$.[*].fieldFuntionalIndexMin").value(hasItem(DEFAULT_FIELD_FUNTIONAL_INDEX_MIN.doubleValue())))
            .andExpect(jsonPath("$.[*].startTime").value(hasItem(DEFAULT_START_TIME.toString())))
            .andExpect(jsonPath("$.[*].endTime").value(hasItem(DEFAULT_END_TIME.toString())));

        // Check, that the count call also returns 1
        restFuntionalIndexSummaryMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultFuntionalIndexSummaryShouldNotBeFound(String filter) throws Exception {
        restFuntionalIndexSummaryMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restFuntionalIndexSummaryMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingFuntionalIndexSummary() throws Exception {
        // Get the funtionalIndexSummary
        restFuntionalIndexSummaryMockMvc.perform(get(ENTITY_API_URL_ID, UUID.randomUUID().toString())).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingFuntionalIndexSummary() throws Exception {
        // Initialize the database
        funtionalIndexSummaryRepository.saveAndFlush(funtionalIndexSummary);

        int databaseSizeBeforeUpdate = funtionalIndexSummaryRepository.findAll().size();

        // Update the funtionalIndexSummary
        FuntionalIndexSummary updatedFuntionalIndexSummary = funtionalIndexSummaryRepository.findById(funtionalIndexSummary.getId()).get();
        // Disconnect from session so that the updates on updatedFuntionalIndexSummary are not directly saved in db
        em.detach(updatedFuntionalIndexSummary);
        updatedFuntionalIndexSummary
            .usuarioId(UPDATED_USUARIO_ID)
            .empresaId(UPDATED_EMPRESA_ID)
            .fieldFuntionalIndexAverage(UPDATED_FIELD_FUNTIONAL_INDEX_AVERAGE)
            .fieldFuntionalIndexMax(UPDATED_FIELD_FUNTIONAL_INDEX_MAX)
            .fieldFuntionalIndexMin(UPDATED_FIELD_FUNTIONAL_INDEX_MIN)
            .startTime(UPDATED_START_TIME)
            .endTime(UPDATED_END_TIME);

        restFuntionalIndexSummaryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedFuntionalIndexSummary.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedFuntionalIndexSummary))
            )
            .andExpect(status().isOk());

        // Validate the FuntionalIndexSummary in the database
        List<FuntionalIndexSummary> funtionalIndexSummaryList = funtionalIndexSummaryRepository.findAll();
        assertThat(funtionalIndexSummaryList).hasSize(databaseSizeBeforeUpdate);
        FuntionalIndexSummary testFuntionalIndexSummary = funtionalIndexSummaryList.get(funtionalIndexSummaryList.size() - 1);
        assertThat(testFuntionalIndexSummary.getUsuarioId()).isEqualTo(UPDATED_USUARIO_ID);
        assertThat(testFuntionalIndexSummary.getEmpresaId()).isEqualTo(UPDATED_EMPRESA_ID);
        assertThat(testFuntionalIndexSummary.getFieldFuntionalIndexAverage()).isEqualTo(UPDATED_FIELD_FUNTIONAL_INDEX_AVERAGE);
        assertThat(testFuntionalIndexSummary.getFieldFuntionalIndexMax()).isEqualTo(UPDATED_FIELD_FUNTIONAL_INDEX_MAX);
        assertThat(testFuntionalIndexSummary.getFieldFuntionalIndexMin()).isEqualTo(UPDATED_FIELD_FUNTIONAL_INDEX_MIN);
        assertThat(testFuntionalIndexSummary.getStartTime()).isEqualTo(UPDATED_START_TIME);
        assertThat(testFuntionalIndexSummary.getEndTime()).isEqualTo(UPDATED_END_TIME);
    }

    @Test
    @Transactional
    void putNonExistingFuntionalIndexSummary() throws Exception {
        int databaseSizeBeforeUpdate = funtionalIndexSummaryRepository.findAll().size();
        funtionalIndexSummary.setId(UUID.randomUUID());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFuntionalIndexSummaryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, funtionalIndexSummary.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(funtionalIndexSummary))
            )
            .andExpect(status().isBadRequest());

        // Validate the FuntionalIndexSummary in the database
        List<FuntionalIndexSummary> funtionalIndexSummaryList = funtionalIndexSummaryRepository.findAll();
        assertThat(funtionalIndexSummaryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchFuntionalIndexSummary() throws Exception {
        int databaseSizeBeforeUpdate = funtionalIndexSummaryRepository.findAll().size();
        funtionalIndexSummary.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFuntionalIndexSummaryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(funtionalIndexSummary))
            )
            .andExpect(status().isBadRequest());

        // Validate the FuntionalIndexSummary in the database
        List<FuntionalIndexSummary> funtionalIndexSummaryList = funtionalIndexSummaryRepository.findAll();
        assertThat(funtionalIndexSummaryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamFuntionalIndexSummary() throws Exception {
        int databaseSizeBeforeUpdate = funtionalIndexSummaryRepository.findAll().size();
        funtionalIndexSummary.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFuntionalIndexSummaryMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(funtionalIndexSummary))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the FuntionalIndexSummary in the database
        List<FuntionalIndexSummary> funtionalIndexSummaryList = funtionalIndexSummaryRepository.findAll();
        assertThat(funtionalIndexSummaryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateFuntionalIndexSummaryWithPatch() throws Exception {
        // Initialize the database
        funtionalIndexSummaryRepository.saveAndFlush(funtionalIndexSummary);

        int databaseSizeBeforeUpdate = funtionalIndexSummaryRepository.findAll().size();

        // Update the funtionalIndexSummary using partial update
        FuntionalIndexSummary partialUpdatedFuntionalIndexSummary = new FuntionalIndexSummary();
        partialUpdatedFuntionalIndexSummary.setId(funtionalIndexSummary.getId());

        partialUpdatedFuntionalIndexSummary
            .usuarioId(UPDATED_USUARIO_ID)
            .empresaId(UPDATED_EMPRESA_ID)
            .fieldFuntionalIndexMax(UPDATED_FIELD_FUNTIONAL_INDEX_MAX)
            .fieldFuntionalIndexMin(UPDATED_FIELD_FUNTIONAL_INDEX_MIN)
            .startTime(UPDATED_START_TIME)
            .endTime(UPDATED_END_TIME);

        restFuntionalIndexSummaryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFuntionalIndexSummary.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFuntionalIndexSummary))
            )
            .andExpect(status().isOk());

        // Validate the FuntionalIndexSummary in the database
        List<FuntionalIndexSummary> funtionalIndexSummaryList = funtionalIndexSummaryRepository.findAll();
        assertThat(funtionalIndexSummaryList).hasSize(databaseSizeBeforeUpdate);
        FuntionalIndexSummary testFuntionalIndexSummary = funtionalIndexSummaryList.get(funtionalIndexSummaryList.size() - 1);
        assertThat(testFuntionalIndexSummary.getUsuarioId()).isEqualTo(UPDATED_USUARIO_ID);
        assertThat(testFuntionalIndexSummary.getEmpresaId()).isEqualTo(UPDATED_EMPRESA_ID);
        assertThat(testFuntionalIndexSummary.getFieldFuntionalIndexAverage()).isEqualTo(DEFAULT_FIELD_FUNTIONAL_INDEX_AVERAGE);
        assertThat(testFuntionalIndexSummary.getFieldFuntionalIndexMax()).isEqualTo(UPDATED_FIELD_FUNTIONAL_INDEX_MAX);
        assertThat(testFuntionalIndexSummary.getFieldFuntionalIndexMin()).isEqualTo(UPDATED_FIELD_FUNTIONAL_INDEX_MIN);
        assertThat(testFuntionalIndexSummary.getStartTime()).isEqualTo(UPDATED_START_TIME);
        assertThat(testFuntionalIndexSummary.getEndTime()).isEqualTo(UPDATED_END_TIME);
    }

    @Test
    @Transactional
    void fullUpdateFuntionalIndexSummaryWithPatch() throws Exception {
        // Initialize the database
        funtionalIndexSummaryRepository.saveAndFlush(funtionalIndexSummary);

        int databaseSizeBeforeUpdate = funtionalIndexSummaryRepository.findAll().size();

        // Update the funtionalIndexSummary using partial update
        FuntionalIndexSummary partialUpdatedFuntionalIndexSummary = new FuntionalIndexSummary();
        partialUpdatedFuntionalIndexSummary.setId(funtionalIndexSummary.getId());

        partialUpdatedFuntionalIndexSummary
            .usuarioId(UPDATED_USUARIO_ID)
            .empresaId(UPDATED_EMPRESA_ID)
            .fieldFuntionalIndexAverage(UPDATED_FIELD_FUNTIONAL_INDEX_AVERAGE)
            .fieldFuntionalIndexMax(UPDATED_FIELD_FUNTIONAL_INDEX_MAX)
            .fieldFuntionalIndexMin(UPDATED_FIELD_FUNTIONAL_INDEX_MIN)
            .startTime(UPDATED_START_TIME)
            .endTime(UPDATED_END_TIME);

        restFuntionalIndexSummaryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFuntionalIndexSummary.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFuntionalIndexSummary))
            )
            .andExpect(status().isOk());

        // Validate the FuntionalIndexSummary in the database
        List<FuntionalIndexSummary> funtionalIndexSummaryList = funtionalIndexSummaryRepository.findAll();
        assertThat(funtionalIndexSummaryList).hasSize(databaseSizeBeforeUpdate);
        FuntionalIndexSummary testFuntionalIndexSummary = funtionalIndexSummaryList.get(funtionalIndexSummaryList.size() - 1);
        assertThat(testFuntionalIndexSummary.getUsuarioId()).isEqualTo(UPDATED_USUARIO_ID);
        assertThat(testFuntionalIndexSummary.getEmpresaId()).isEqualTo(UPDATED_EMPRESA_ID);
        assertThat(testFuntionalIndexSummary.getFieldFuntionalIndexAverage()).isEqualTo(UPDATED_FIELD_FUNTIONAL_INDEX_AVERAGE);
        assertThat(testFuntionalIndexSummary.getFieldFuntionalIndexMax()).isEqualTo(UPDATED_FIELD_FUNTIONAL_INDEX_MAX);
        assertThat(testFuntionalIndexSummary.getFieldFuntionalIndexMin()).isEqualTo(UPDATED_FIELD_FUNTIONAL_INDEX_MIN);
        assertThat(testFuntionalIndexSummary.getStartTime()).isEqualTo(UPDATED_START_TIME);
        assertThat(testFuntionalIndexSummary.getEndTime()).isEqualTo(UPDATED_END_TIME);
    }

    @Test
    @Transactional
    void patchNonExistingFuntionalIndexSummary() throws Exception {
        int databaseSizeBeforeUpdate = funtionalIndexSummaryRepository.findAll().size();
        funtionalIndexSummary.setId(UUID.randomUUID());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFuntionalIndexSummaryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, funtionalIndexSummary.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(funtionalIndexSummary))
            )
            .andExpect(status().isBadRequest());

        // Validate the FuntionalIndexSummary in the database
        List<FuntionalIndexSummary> funtionalIndexSummaryList = funtionalIndexSummaryRepository.findAll();
        assertThat(funtionalIndexSummaryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchFuntionalIndexSummary() throws Exception {
        int databaseSizeBeforeUpdate = funtionalIndexSummaryRepository.findAll().size();
        funtionalIndexSummary.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFuntionalIndexSummaryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(funtionalIndexSummary))
            )
            .andExpect(status().isBadRequest());

        // Validate the FuntionalIndexSummary in the database
        List<FuntionalIndexSummary> funtionalIndexSummaryList = funtionalIndexSummaryRepository.findAll();
        assertThat(funtionalIndexSummaryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamFuntionalIndexSummary() throws Exception {
        int databaseSizeBeforeUpdate = funtionalIndexSummaryRepository.findAll().size();
        funtionalIndexSummary.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFuntionalIndexSummaryMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(funtionalIndexSummary))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the FuntionalIndexSummary in the database
        List<FuntionalIndexSummary> funtionalIndexSummaryList = funtionalIndexSummaryRepository.findAll();
        assertThat(funtionalIndexSummaryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteFuntionalIndexSummary() throws Exception {
        // Initialize the database
        funtionalIndexSummaryRepository.saveAndFlush(funtionalIndexSummary);

        int databaseSizeBeforeDelete = funtionalIndexSummaryRepository.findAll().size();

        // Delete the funtionalIndexSummary
        restFuntionalIndexSummaryMockMvc
            .perform(delete(ENTITY_API_URL_ID, funtionalIndexSummary.getId().toString()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<FuntionalIndexSummary> funtionalIndexSummaryList = funtionalIndexSummaryRepository.findAll();
        assertThat(funtionalIndexSummaryList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
