package com.be4tech.b4carecollect.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.be4tech.b4carecollect.IntegrationTest;
import com.be4tech.b4carecollect.domain.OxygenSaturationSummary;
import com.be4tech.b4carecollect.repository.OxygenSaturationSummaryRepository;
import com.be4tech.b4carecollect.service.criteria.OxygenSaturationSummaryCriteria;
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
 * Integration tests for the {@link OxygenSaturationSummaryResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class OxygenSaturationSummaryResourceIT {

    private static final String DEFAULT_USUARIO_ID = "AAAAAAAAAA";
    private static final String UPDATED_USUARIO_ID = "BBBBBBBBBB";

    private static final String DEFAULT_EMPRESA_ID = "AAAAAAAAAA";
    private static final String UPDATED_EMPRESA_ID = "BBBBBBBBBB";

    private static final Float DEFAULT_FIELD_OXIGEN_SATURATION_AVERAGE = 1F;
    private static final Float UPDATED_FIELD_OXIGEN_SATURATION_AVERAGE = 2F;
    private static final Float SMALLER_FIELD_OXIGEN_SATURATION_AVERAGE = 1F - 1F;

    private static final Float DEFAULT_FIELD_OXIGEN_SATURATION_MAX = 1F;
    private static final Float UPDATED_FIELD_OXIGEN_SATURATION_MAX = 2F;
    private static final Float SMALLER_FIELD_OXIGEN_SATURATION_MAX = 1F - 1F;

    private static final Float DEFAULT_FIELD_OXIGEN_SATURATION_MIN = 1F;
    private static final Float UPDATED_FIELD_OXIGEN_SATURATION_MIN = 2F;
    private static final Float SMALLER_FIELD_OXIGEN_SATURATION_MIN = 1F - 1F;

    private static final Float DEFAULT_FIELD_SUPLEMENTAL_OXIGEN_FLOW_RATE_AVERAGE = 1F;
    private static final Float UPDATED_FIELD_SUPLEMENTAL_OXIGEN_FLOW_RATE_AVERAGE = 2F;
    private static final Float SMALLER_FIELD_SUPLEMENTAL_OXIGEN_FLOW_RATE_AVERAGE = 1F - 1F;

    private static final Float DEFAULT_FIELD_SUPLEMENTAL_OXIGEN_FLOW_RATE_MAX = 1F;
    private static final Float UPDATED_FIELD_SUPLEMENTAL_OXIGEN_FLOW_RATE_MAX = 2F;
    private static final Float SMALLER_FIELD_SUPLEMENTAL_OXIGEN_FLOW_RATE_MAX = 1F - 1F;

    private static final Float DEFAULT_FIELD_SUPLEMENTAL_OXIGEN_FLOW_RATE_MIN = 1F;
    private static final Float UPDATED_FIELD_SUPLEMENTAL_OXIGEN_FLOW_RATE_MIN = 2F;
    private static final Float SMALLER_FIELD_SUPLEMENTAL_OXIGEN_FLOW_RATE_MIN = 1F - 1F;

    private static final Integer DEFAULT_FIELD_OXIGEN_THERAPY_ADMINISTRATION_MODE = 1;
    private static final Integer UPDATED_FIELD_OXIGEN_THERAPY_ADMINISTRATION_MODE = 2;
    private static final Integer SMALLER_FIELD_OXIGEN_THERAPY_ADMINISTRATION_MODE = 1 - 1;

    private static final Integer DEFAULT_FIELD_OXIGEN_SATURATION_MODE = 1;
    private static final Integer UPDATED_FIELD_OXIGEN_SATURATION_MODE = 2;
    private static final Integer SMALLER_FIELD_OXIGEN_SATURATION_MODE = 1 - 1;

    private static final Integer DEFAULT_FIELD_OXIGEN_SATURATION_MEASUREMENT_METHOD = 1;
    private static final Integer UPDATED_FIELD_OXIGEN_SATURATION_MEASUREMENT_METHOD = 2;
    private static final Integer SMALLER_FIELD_OXIGEN_SATURATION_MEASUREMENT_METHOD = 1 - 1;

    private static final Instant DEFAULT_END_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_END_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/oxygen-saturation-summaries";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private OxygenSaturationSummaryRepository oxygenSaturationSummaryRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOxygenSaturationSummaryMockMvc;

    private OxygenSaturationSummary oxygenSaturationSummary;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OxygenSaturationSummary createEntity(EntityManager em) {
        OxygenSaturationSummary oxygenSaturationSummary = new OxygenSaturationSummary()
            .usuarioId(DEFAULT_USUARIO_ID)
            .empresaId(DEFAULT_EMPRESA_ID)
            .fieldOxigenSaturationAverage(DEFAULT_FIELD_OXIGEN_SATURATION_AVERAGE)
            .fieldOxigenSaturationMax(DEFAULT_FIELD_OXIGEN_SATURATION_MAX)
            .fieldOxigenSaturationMin(DEFAULT_FIELD_OXIGEN_SATURATION_MIN)
            .fieldSuplementalOxigenFlowRateAverage(DEFAULT_FIELD_SUPLEMENTAL_OXIGEN_FLOW_RATE_AVERAGE)
            .fieldSuplementalOxigenFlowRateMax(DEFAULT_FIELD_SUPLEMENTAL_OXIGEN_FLOW_RATE_MAX)
            .fieldSuplementalOxigenFlowRateMin(DEFAULT_FIELD_SUPLEMENTAL_OXIGEN_FLOW_RATE_MIN)
            .fieldOxigenTherapyAdministrationMode(DEFAULT_FIELD_OXIGEN_THERAPY_ADMINISTRATION_MODE)
            .fieldOxigenSaturationMode(DEFAULT_FIELD_OXIGEN_SATURATION_MODE)
            .fieldOxigenSaturationMeasurementMethod(DEFAULT_FIELD_OXIGEN_SATURATION_MEASUREMENT_METHOD)
            .endTime(DEFAULT_END_TIME);
        return oxygenSaturationSummary;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OxygenSaturationSummary createUpdatedEntity(EntityManager em) {
        OxygenSaturationSummary oxygenSaturationSummary = new OxygenSaturationSummary()
            .usuarioId(UPDATED_USUARIO_ID)
            .empresaId(UPDATED_EMPRESA_ID)
            .fieldOxigenSaturationAverage(UPDATED_FIELD_OXIGEN_SATURATION_AVERAGE)
            .fieldOxigenSaturationMax(UPDATED_FIELD_OXIGEN_SATURATION_MAX)
            .fieldOxigenSaturationMin(UPDATED_FIELD_OXIGEN_SATURATION_MIN)
            .fieldSuplementalOxigenFlowRateAverage(UPDATED_FIELD_SUPLEMENTAL_OXIGEN_FLOW_RATE_AVERAGE)
            .fieldSuplementalOxigenFlowRateMax(UPDATED_FIELD_SUPLEMENTAL_OXIGEN_FLOW_RATE_MAX)
            .fieldSuplementalOxigenFlowRateMin(UPDATED_FIELD_SUPLEMENTAL_OXIGEN_FLOW_RATE_MIN)
            .fieldOxigenTherapyAdministrationMode(UPDATED_FIELD_OXIGEN_THERAPY_ADMINISTRATION_MODE)
            .fieldOxigenSaturationMode(UPDATED_FIELD_OXIGEN_SATURATION_MODE)
            .fieldOxigenSaturationMeasurementMethod(UPDATED_FIELD_OXIGEN_SATURATION_MEASUREMENT_METHOD)
            .endTime(UPDATED_END_TIME);
        return oxygenSaturationSummary;
    }

    @BeforeEach
    public void initTest() {
        oxygenSaturationSummary = createEntity(em);
    }

    @Test
    @Transactional
    void createOxygenSaturationSummary() throws Exception {
        int databaseSizeBeforeCreate = oxygenSaturationSummaryRepository.findAll().size();
        // Create the OxygenSaturationSummary
        restOxygenSaturationSummaryMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(oxygenSaturationSummary))
            )
            .andExpect(status().isCreated());

        // Validate the OxygenSaturationSummary in the database
        List<OxygenSaturationSummary> oxygenSaturationSummaryList = oxygenSaturationSummaryRepository.findAll();
        assertThat(oxygenSaturationSummaryList).hasSize(databaseSizeBeforeCreate + 1);
        OxygenSaturationSummary testOxygenSaturationSummary = oxygenSaturationSummaryList.get(oxygenSaturationSummaryList.size() - 1);
        assertThat(testOxygenSaturationSummary.getUsuarioId()).isEqualTo(DEFAULT_USUARIO_ID);
        assertThat(testOxygenSaturationSummary.getEmpresaId()).isEqualTo(DEFAULT_EMPRESA_ID);
        assertThat(testOxygenSaturationSummary.getFieldOxigenSaturationAverage()).isEqualTo(DEFAULT_FIELD_OXIGEN_SATURATION_AVERAGE);
        assertThat(testOxygenSaturationSummary.getFieldOxigenSaturationMax()).isEqualTo(DEFAULT_FIELD_OXIGEN_SATURATION_MAX);
        assertThat(testOxygenSaturationSummary.getFieldOxigenSaturationMin()).isEqualTo(DEFAULT_FIELD_OXIGEN_SATURATION_MIN);
        assertThat(testOxygenSaturationSummary.getFieldSuplementalOxigenFlowRateAverage())
            .isEqualTo(DEFAULT_FIELD_SUPLEMENTAL_OXIGEN_FLOW_RATE_AVERAGE);
        assertThat(testOxygenSaturationSummary.getFieldSuplementalOxigenFlowRateMax())
            .isEqualTo(DEFAULT_FIELD_SUPLEMENTAL_OXIGEN_FLOW_RATE_MAX);
        assertThat(testOxygenSaturationSummary.getFieldSuplementalOxigenFlowRateMin())
            .isEqualTo(DEFAULT_FIELD_SUPLEMENTAL_OXIGEN_FLOW_RATE_MIN);
        assertThat(testOxygenSaturationSummary.getFieldOxigenTherapyAdministrationMode())
            .isEqualTo(DEFAULT_FIELD_OXIGEN_THERAPY_ADMINISTRATION_MODE);
        assertThat(testOxygenSaturationSummary.getFieldOxigenSaturationMode()).isEqualTo(DEFAULT_FIELD_OXIGEN_SATURATION_MODE);
        assertThat(testOxygenSaturationSummary.getFieldOxigenSaturationMeasurementMethod())
            .isEqualTo(DEFAULT_FIELD_OXIGEN_SATURATION_MEASUREMENT_METHOD);
        assertThat(testOxygenSaturationSummary.getEndTime()).isEqualTo(DEFAULT_END_TIME);
    }

    @Test
    @Transactional
    void createOxygenSaturationSummaryWithExistingId() throws Exception {
        // Create the OxygenSaturationSummary with an existing ID
        oxygenSaturationSummaryRepository.saveAndFlush(oxygenSaturationSummary);

        int databaseSizeBeforeCreate = oxygenSaturationSummaryRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restOxygenSaturationSummaryMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(oxygenSaturationSummary))
            )
            .andExpect(status().isBadRequest());

        // Validate the OxygenSaturationSummary in the database
        List<OxygenSaturationSummary> oxygenSaturationSummaryList = oxygenSaturationSummaryRepository.findAll();
        assertThat(oxygenSaturationSummaryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllOxygenSaturationSummaries() throws Exception {
        // Initialize the database
        oxygenSaturationSummaryRepository.saveAndFlush(oxygenSaturationSummary);

        // Get all the oxygenSaturationSummaryList
        restOxygenSaturationSummaryMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(oxygenSaturationSummary.getId().toString())))
            .andExpect(jsonPath("$.[*].usuarioId").value(hasItem(DEFAULT_USUARIO_ID)))
            .andExpect(jsonPath("$.[*].empresaId").value(hasItem(DEFAULT_EMPRESA_ID)))
            .andExpect(jsonPath("$.[*].fieldOxigenSaturationAverage").value(hasItem(DEFAULT_FIELD_OXIGEN_SATURATION_AVERAGE.doubleValue())))
            .andExpect(jsonPath("$.[*].fieldOxigenSaturationMax").value(hasItem(DEFAULT_FIELD_OXIGEN_SATURATION_MAX.doubleValue())))
            .andExpect(jsonPath("$.[*].fieldOxigenSaturationMin").value(hasItem(DEFAULT_FIELD_OXIGEN_SATURATION_MIN.doubleValue())))
            .andExpect(
                jsonPath("$.[*].fieldSuplementalOxigenFlowRateAverage")
                    .value(hasItem(DEFAULT_FIELD_SUPLEMENTAL_OXIGEN_FLOW_RATE_AVERAGE.doubleValue()))
            )
            .andExpect(
                jsonPath("$.[*].fieldSuplementalOxigenFlowRateMax")
                    .value(hasItem(DEFAULT_FIELD_SUPLEMENTAL_OXIGEN_FLOW_RATE_MAX.doubleValue()))
            )
            .andExpect(
                jsonPath("$.[*].fieldSuplementalOxigenFlowRateMin")
                    .value(hasItem(DEFAULT_FIELD_SUPLEMENTAL_OXIGEN_FLOW_RATE_MIN.doubleValue()))
            )
            .andExpect(
                jsonPath("$.[*].fieldOxigenTherapyAdministrationMode").value(hasItem(DEFAULT_FIELD_OXIGEN_THERAPY_ADMINISTRATION_MODE))
            )
            .andExpect(jsonPath("$.[*].fieldOxigenSaturationMode").value(hasItem(DEFAULT_FIELD_OXIGEN_SATURATION_MODE)))
            .andExpect(
                jsonPath("$.[*].fieldOxigenSaturationMeasurementMethod").value(hasItem(DEFAULT_FIELD_OXIGEN_SATURATION_MEASUREMENT_METHOD))
            )
            .andExpect(jsonPath("$.[*].endTime").value(hasItem(DEFAULT_END_TIME.toString())));
    }

    @Test
    @Transactional
    void getOxygenSaturationSummary() throws Exception {
        // Initialize the database
        oxygenSaturationSummaryRepository.saveAndFlush(oxygenSaturationSummary);

        // Get the oxygenSaturationSummary
        restOxygenSaturationSummaryMockMvc
            .perform(get(ENTITY_API_URL_ID, oxygenSaturationSummary.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(oxygenSaturationSummary.getId().toString()))
            .andExpect(jsonPath("$.usuarioId").value(DEFAULT_USUARIO_ID))
            .andExpect(jsonPath("$.empresaId").value(DEFAULT_EMPRESA_ID))
            .andExpect(jsonPath("$.fieldOxigenSaturationAverage").value(DEFAULT_FIELD_OXIGEN_SATURATION_AVERAGE.doubleValue()))
            .andExpect(jsonPath("$.fieldOxigenSaturationMax").value(DEFAULT_FIELD_OXIGEN_SATURATION_MAX.doubleValue()))
            .andExpect(jsonPath("$.fieldOxigenSaturationMin").value(DEFAULT_FIELD_OXIGEN_SATURATION_MIN.doubleValue()))
            .andExpect(
                jsonPath("$.fieldSuplementalOxigenFlowRateAverage").value(DEFAULT_FIELD_SUPLEMENTAL_OXIGEN_FLOW_RATE_AVERAGE.doubleValue())
            )
            .andExpect(jsonPath("$.fieldSuplementalOxigenFlowRateMax").value(DEFAULT_FIELD_SUPLEMENTAL_OXIGEN_FLOW_RATE_MAX.doubleValue()))
            .andExpect(jsonPath("$.fieldSuplementalOxigenFlowRateMin").value(DEFAULT_FIELD_SUPLEMENTAL_OXIGEN_FLOW_RATE_MIN.doubleValue()))
            .andExpect(jsonPath("$.fieldOxigenTherapyAdministrationMode").value(DEFAULT_FIELD_OXIGEN_THERAPY_ADMINISTRATION_MODE))
            .andExpect(jsonPath("$.fieldOxigenSaturationMode").value(DEFAULT_FIELD_OXIGEN_SATURATION_MODE))
            .andExpect(jsonPath("$.fieldOxigenSaturationMeasurementMethod").value(DEFAULT_FIELD_OXIGEN_SATURATION_MEASUREMENT_METHOD))
            .andExpect(jsonPath("$.endTime").value(DEFAULT_END_TIME.toString()));
    }

    @Test
    @Transactional
    void getOxygenSaturationSummariesByIdFiltering() throws Exception {
        // Initialize the database
        oxygenSaturationSummaryRepository.saveAndFlush(oxygenSaturationSummary);

        UUID id = oxygenSaturationSummary.getId();

        defaultOxygenSaturationSummaryShouldBeFound("id.equals=" + id);
        defaultOxygenSaturationSummaryShouldNotBeFound("id.notEquals=" + id);
    }

    @Test
    @Transactional
    void getAllOxygenSaturationSummariesByUsuarioIdIsEqualToSomething() throws Exception {
        // Initialize the database
        oxygenSaturationSummaryRepository.saveAndFlush(oxygenSaturationSummary);

        // Get all the oxygenSaturationSummaryList where usuarioId equals to DEFAULT_USUARIO_ID
        defaultOxygenSaturationSummaryShouldBeFound("usuarioId.equals=" + DEFAULT_USUARIO_ID);

        // Get all the oxygenSaturationSummaryList where usuarioId equals to UPDATED_USUARIO_ID
        defaultOxygenSaturationSummaryShouldNotBeFound("usuarioId.equals=" + UPDATED_USUARIO_ID);
    }

    @Test
    @Transactional
    void getAllOxygenSaturationSummariesByUsuarioIdIsInShouldWork() throws Exception {
        // Initialize the database
        oxygenSaturationSummaryRepository.saveAndFlush(oxygenSaturationSummary);

        // Get all the oxygenSaturationSummaryList where usuarioId in DEFAULT_USUARIO_ID or UPDATED_USUARIO_ID
        defaultOxygenSaturationSummaryShouldBeFound("usuarioId.in=" + DEFAULT_USUARIO_ID + "," + UPDATED_USUARIO_ID);

        // Get all the oxygenSaturationSummaryList where usuarioId equals to UPDATED_USUARIO_ID
        defaultOxygenSaturationSummaryShouldNotBeFound("usuarioId.in=" + UPDATED_USUARIO_ID);
    }

    @Test
    @Transactional
    void getAllOxygenSaturationSummariesByUsuarioIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        oxygenSaturationSummaryRepository.saveAndFlush(oxygenSaturationSummary);

        // Get all the oxygenSaturationSummaryList where usuarioId is not null
        defaultOxygenSaturationSummaryShouldBeFound("usuarioId.specified=true");

        // Get all the oxygenSaturationSummaryList where usuarioId is null
        defaultOxygenSaturationSummaryShouldNotBeFound("usuarioId.specified=false");
    }

    @Test
    @Transactional
    void getAllOxygenSaturationSummariesByUsuarioIdContainsSomething() throws Exception {
        // Initialize the database
        oxygenSaturationSummaryRepository.saveAndFlush(oxygenSaturationSummary);

        // Get all the oxygenSaturationSummaryList where usuarioId contains DEFAULT_USUARIO_ID
        defaultOxygenSaturationSummaryShouldBeFound("usuarioId.contains=" + DEFAULT_USUARIO_ID);

        // Get all the oxygenSaturationSummaryList where usuarioId contains UPDATED_USUARIO_ID
        defaultOxygenSaturationSummaryShouldNotBeFound("usuarioId.contains=" + UPDATED_USUARIO_ID);
    }

    @Test
    @Transactional
    void getAllOxygenSaturationSummariesByUsuarioIdNotContainsSomething() throws Exception {
        // Initialize the database
        oxygenSaturationSummaryRepository.saveAndFlush(oxygenSaturationSummary);

        // Get all the oxygenSaturationSummaryList where usuarioId does not contain DEFAULT_USUARIO_ID
        defaultOxygenSaturationSummaryShouldNotBeFound("usuarioId.doesNotContain=" + DEFAULT_USUARIO_ID);

        // Get all the oxygenSaturationSummaryList where usuarioId does not contain UPDATED_USUARIO_ID
        defaultOxygenSaturationSummaryShouldBeFound("usuarioId.doesNotContain=" + UPDATED_USUARIO_ID);
    }

    @Test
    @Transactional
    void getAllOxygenSaturationSummariesByEmpresaIdIsEqualToSomething() throws Exception {
        // Initialize the database
        oxygenSaturationSummaryRepository.saveAndFlush(oxygenSaturationSummary);

        // Get all the oxygenSaturationSummaryList where empresaId equals to DEFAULT_EMPRESA_ID
        defaultOxygenSaturationSummaryShouldBeFound("empresaId.equals=" + DEFAULT_EMPRESA_ID);

        // Get all the oxygenSaturationSummaryList where empresaId equals to UPDATED_EMPRESA_ID
        defaultOxygenSaturationSummaryShouldNotBeFound("empresaId.equals=" + UPDATED_EMPRESA_ID);
    }

    @Test
    @Transactional
    void getAllOxygenSaturationSummariesByEmpresaIdIsInShouldWork() throws Exception {
        // Initialize the database
        oxygenSaturationSummaryRepository.saveAndFlush(oxygenSaturationSummary);

        // Get all the oxygenSaturationSummaryList where empresaId in DEFAULT_EMPRESA_ID or UPDATED_EMPRESA_ID
        defaultOxygenSaturationSummaryShouldBeFound("empresaId.in=" + DEFAULT_EMPRESA_ID + "," + UPDATED_EMPRESA_ID);

        // Get all the oxygenSaturationSummaryList where empresaId equals to UPDATED_EMPRESA_ID
        defaultOxygenSaturationSummaryShouldNotBeFound("empresaId.in=" + UPDATED_EMPRESA_ID);
    }

    @Test
    @Transactional
    void getAllOxygenSaturationSummariesByEmpresaIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        oxygenSaturationSummaryRepository.saveAndFlush(oxygenSaturationSummary);

        // Get all the oxygenSaturationSummaryList where empresaId is not null
        defaultOxygenSaturationSummaryShouldBeFound("empresaId.specified=true");

        // Get all the oxygenSaturationSummaryList where empresaId is null
        defaultOxygenSaturationSummaryShouldNotBeFound("empresaId.specified=false");
    }

    @Test
    @Transactional
    void getAllOxygenSaturationSummariesByEmpresaIdContainsSomething() throws Exception {
        // Initialize the database
        oxygenSaturationSummaryRepository.saveAndFlush(oxygenSaturationSummary);

        // Get all the oxygenSaturationSummaryList where empresaId contains DEFAULT_EMPRESA_ID
        defaultOxygenSaturationSummaryShouldBeFound("empresaId.contains=" + DEFAULT_EMPRESA_ID);

        // Get all the oxygenSaturationSummaryList where empresaId contains UPDATED_EMPRESA_ID
        defaultOxygenSaturationSummaryShouldNotBeFound("empresaId.contains=" + UPDATED_EMPRESA_ID);
    }

    @Test
    @Transactional
    void getAllOxygenSaturationSummariesByEmpresaIdNotContainsSomething() throws Exception {
        // Initialize the database
        oxygenSaturationSummaryRepository.saveAndFlush(oxygenSaturationSummary);

        // Get all the oxygenSaturationSummaryList where empresaId does not contain DEFAULT_EMPRESA_ID
        defaultOxygenSaturationSummaryShouldNotBeFound("empresaId.doesNotContain=" + DEFAULT_EMPRESA_ID);

        // Get all the oxygenSaturationSummaryList where empresaId does not contain UPDATED_EMPRESA_ID
        defaultOxygenSaturationSummaryShouldBeFound("empresaId.doesNotContain=" + UPDATED_EMPRESA_ID);
    }

    @Test
    @Transactional
    void getAllOxygenSaturationSummariesByFieldOxigenSaturationAverageIsEqualToSomething() throws Exception {
        // Initialize the database
        oxygenSaturationSummaryRepository.saveAndFlush(oxygenSaturationSummary);

        // Get all the oxygenSaturationSummaryList where fieldOxigenSaturationAverage equals to DEFAULT_FIELD_OXIGEN_SATURATION_AVERAGE
        defaultOxygenSaturationSummaryShouldBeFound("fieldOxigenSaturationAverage.equals=" + DEFAULT_FIELD_OXIGEN_SATURATION_AVERAGE);

        // Get all the oxygenSaturationSummaryList where fieldOxigenSaturationAverage equals to UPDATED_FIELD_OXIGEN_SATURATION_AVERAGE
        defaultOxygenSaturationSummaryShouldNotBeFound("fieldOxigenSaturationAverage.equals=" + UPDATED_FIELD_OXIGEN_SATURATION_AVERAGE);
    }

    @Test
    @Transactional
    void getAllOxygenSaturationSummariesByFieldOxigenSaturationAverageIsInShouldWork() throws Exception {
        // Initialize the database
        oxygenSaturationSummaryRepository.saveAndFlush(oxygenSaturationSummary);

        // Get all the oxygenSaturationSummaryList where fieldOxigenSaturationAverage in DEFAULT_FIELD_OXIGEN_SATURATION_AVERAGE or UPDATED_FIELD_OXIGEN_SATURATION_AVERAGE
        defaultOxygenSaturationSummaryShouldBeFound(
            "fieldOxigenSaturationAverage.in=" + DEFAULT_FIELD_OXIGEN_SATURATION_AVERAGE + "," + UPDATED_FIELD_OXIGEN_SATURATION_AVERAGE
        );

        // Get all the oxygenSaturationSummaryList where fieldOxigenSaturationAverage equals to UPDATED_FIELD_OXIGEN_SATURATION_AVERAGE
        defaultOxygenSaturationSummaryShouldNotBeFound("fieldOxigenSaturationAverage.in=" + UPDATED_FIELD_OXIGEN_SATURATION_AVERAGE);
    }

    @Test
    @Transactional
    void getAllOxygenSaturationSummariesByFieldOxigenSaturationAverageIsNullOrNotNull() throws Exception {
        // Initialize the database
        oxygenSaturationSummaryRepository.saveAndFlush(oxygenSaturationSummary);

        // Get all the oxygenSaturationSummaryList where fieldOxigenSaturationAverage is not null
        defaultOxygenSaturationSummaryShouldBeFound("fieldOxigenSaturationAverage.specified=true");

        // Get all the oxygenSaturationSummaryList where fieldOxigenSaturationAverage is null
        defaultOxygenSaturationSummaryShouldNotBeFound("fieldOxigenSaturationAverage.specified=false");
    }

    @Test
    @Transactional
    void getAllOxygenSaturationSummariesByFieldOxigenSaturationAverageIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        oxygenSaturationSummaryRepository.saveAndFlush(oxygenSaturationSummary);

        // Get all the oxygenSaturationSummaryList where fieldOxigenSaturationAverage is greater than or equal to DEFAULT_FIELD_OXIGEN_SATURATION_AVERAGE
        defaultOxygenSaturationSummaryShouldBeFound(
            "fieldOxigenSaturationAverage.greaterThanOrEqual=" + DEFAULT_FIELD_OXIGEN_SATURATION_AVERAGE
        );

        // Get all the oxygenSaturationSummaryList where fieldOxigenSaturationAverage is greater than or equal to UPDATED_FIELD_OXIGEN_SATURATION_AVERAGE
        defaultOxygenSaturationSummaryShouldNotBeFound(
            "fieldOxigenSaturationAverage.greaterThanOrEqual=" + UPDATED_FIELD_OXIGEN_SATURATION_AVERAGE
        );
    }

    @Test
    @Transactional
    void getAllOxygenSaturationSummariesByFieldOxigenSaturationAverageIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        oxygenSaturationSummaryRepository.saveAndFlush(oxygenSaturationSummary);

        // Get all the oxygenSaturationSummaryList where fieldOxigenSaturationAverage is less than or equal to DEFAULT_FIELD_OXIGEN_SATURATION_AVERAGE
        defaultOxygenSaturationSummaryShouldBeFound(
            "fieldOxigenSaturationAverage.lessThanOrEqual=" + DEFAULT_FIELD_OXIGEN_SATURATION_AVERAGE
        );

        // Get all the oxygenSaturationSummaryList where fieldOxigenSaturationAverage is less than or equal to SMALLER_FIELD_OXIGEN_SATURATION_AVERAGE
        defaultOxygenSaturationSummaryShouldNotBeFound(
            "fieldOxigenSaturationAverage.lessThanOrEqual=" + SMALLER_FIELD_OXIGEN_SATURATION_AVERAGE
        );
    }

    @Test
    @Transactional
    void getAllOxygenSaturationSummariesByFieldOxigenSaturationAverageIsLessThanSomething() throws Exception {
        // Initialize the database
        oxygenSaturationSummaryRepository.saveAndFlush(oxygenSaturationSummary);

        // Get all the oxygenSaturationSummaryList where fieldOxigenSaturationAverage is less than DEFAULT_FIELD_OXIGEN_SATURATION_AVERAGE
        defaultOxygenSaturationSummaryShouldNotBeFound("fieldOxigenSaturationAverage.lessThan=" + DEFAULT_FIELD_OXIGEN_SATURATION_AVERAGE);

        // Get all the oxygenSaturationSummaryList where fieldOxigenSaturationAverage is less than UPDATED_FIELD_OXIGEN_SATURATION_AVERAGE
        defaultOxygenSaturationSummaryShouldBeFound("fieldOxigenSaturationAverage.lessThan=" + UPDATED_FIELD_OXIGEN_SATURATION_AVERAGE);
    }

    @Test
    @Transactional
    void getAllOxygenSaturationSummariesByFieldOxigenSaturationAverageIsGreaterThanSomething() throws Exception {
        // Initialize the database
        oxygenSaturationSummaryRepository.saveAndFlush(oxygenSaturationSummary);

        // Get all the oxygenSaturationSummaryList where fieldOxigenSaturationAverage is greater than DEFAULT_FIELD_OXIGEN_SATURATION_AVERAGE
        defaultOxygenSaturationSummaryShouldNotBeFound(
            "fieldOxigenSaturationAverage.greaterThan=" + DEFAULT_FIELD_OXIGEN_SATURATION_AVERAGE
        );

        // Get all the oxygenSaturationSummaryList where fieldOxigenSaturationAverage is greater than SMALLER_FIELD_OXIGEN_SATURATION_AVERAGE
        defaultOxygenSaturationSummaryShouldBeFound("fieldOxigenSaturationAverage.greaterThan=" + SMALLER_FIELD_OXIGEN_SATURATION_AVERAGE);
    }

    @Test
    @Transactional
    void getAllOxygenSaturationSummariesByFieldOxigenSaturationMaxIsEqualToSomething() throws Exception {
        // Initialize the database
        oxygenSaturationSummaryRepository.saveAndFlush(oxygenSaturationSummary);

        // Get all the oxygenSaturationSummaryList where fieldOxigenSaturationMax equals to DEFAULT_FIELD_OXIGEN_SATURATION_MAX
        defaultOxygenSaturationSummaryShouldBeFound("fieldOxigenSaturationMax.equals=" + DEFAULT_FIELD_OXIGEN_SATURATION_MAX);

        // Get all the oxygenSaturationSummaryList where fieldOxigenSaturationMax equals to UPDATED_FIELD_OXIGEN_SATURATION_MAX
        defaultOxygenSaturationSummaryShouldNotBeFound("fieldOxigenSaturationMax.equals=" + UPDATED_FIELD_OXIGEN_SATURATION_MAX);
    }

    @Test
    @Transactional
    void getAllOxygenSaturationSummariesByFieldOxigenSaturationMaxIsInShouldWork() throws Exception {
        // Initialize the database
        oxygenSaturationSummaryRepository.saveAndFlush(oxygenSaturationSummary);

        // Get all the oxygenSaturationSummaryList where fieldOxigenSaturationMax in DEFAULT_FIELD_OXIGEN_SATURATION_MAX or UPDATED_FIELD_OXIGEN_SATURATION_MAX
        defaultOxygenSaturationSummaryShouldBeFound(
            "fieldOxigenSaturationMax.in=" + DEFAULT_FIELD_OXIGEN_SATURATION_MAX + "," + UPDATED_FIELD_OXIGEN_SATURATION_MAX
        );

        // Get all the oxygenSaturationSummaryList where fieldOxigenSaturationMax equals to UPDATED_FIELD_OXIGEN_SATURATION_MAX
        defaultOxygenSaturationSummaryShouldNotBeFound("fieldOxigenSaturationMax.in=" + UPDATED_FIELD_OXIGEN_SATURATION_MAX);
    }

    @Test
    @Transactional
    void getAllOxygenSaturationSummariesByFieldOxigenSaturationMaxIsNullOrNotNull() throws Exception {
        // Initialize the database
        oxygenSaturationSummaryRepository.saveAndFlush(oxygenSaturationSummary);

        // Get all the oxygenSaturationSummaryList where fieldOxigenSaturationMax is not null
        defaultOxygenSaturationSummaryShouldBeFound("fieldOxigenSaturationMax.specified=true");

        // Get all the oxygenSaturationSummaryList where fieldOxigenSaturationMax is null
        defaultOxygenSaturationSummaryShouldNotBeFound("fieldOxigenSaturationMax.specified=false");
    }

    @Test
    @Transactional
    void getAllOxygenSaturationSummariesByFieldOxigenSaturationMaxIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        oxygenSaturationSummaryRepository.saveAndFlush(oxygenSaturationSummary);

        // Get all the oxygenSaturationSummaryList where fieldOxigenSaturationMax is greater than or equal to DEFAULT_FIELD_OXIGEN_SATURATION_MAX
        defaultOxygenSaturationSummaryShouldBeFound("fieldOxigenSaturationMax.greaterThanOrEqual=" + DEFAULT_FIELD_OXIGEN_SATURATION_MAX);

        // Get all the oxygenSaturationSummaryList where fieldOxigenSaturationMax is greater than or equal to UPDATED_FIELD_OXIGEN_SATURATION_MAX
        defaultOxygenSaturationSummaryShouldNotBeFound(
            "fieldOxigenSaturationMax.greaterThanOrEqual=" + UPDATED_FIELD_OXIGEN_SATURATION_MAX
        );
    }

    @Test
    @Transactional
    void getAllOxygenSaturationSummariesByFieldOxigenSaturationMaxIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        oxygenSaturationSummaryRepository.saveAndFlush(oxygenSaturationSummary);

        // Get all the oxygenSaturationSummaryList where fieldOxigenSaturationMax is less than or equal to DEFAULT_FIELD_OXIGEN_SATURATION_MAX
        defaultOxygenSaturationSummaryShouldBeFound("fieldOxigenSaturationMax.lessThanOrEqual=" + DEFAULT_FIELD_OXIGEN_SATURATION_MAX);

        // Get all the oxygenSaturationSummaryList where fieldOxigenSaturationMax is less than or equal to SMALLER_FIELD_OXIGEN_SATURATION_MAX
        defaultOxygenSaturationSummaryShouldNotBeFound("fieldOxigenSaturationMax.lessThanOrEqual=" + SMALLER_FIELD_OXIGEN_SATURATION_MAX);
    }

    @Test
    @Transactional
    void getAllOxygenSaturationSummariesByFieldOxigenSaturationMaxIsLessThanSomething() throws Exception {
        // Initialize the database
        oxygenSaturationSummaryRepository.saveAndFlush(oxygenSaturationSummary);

        // Get all the oxygenSaturationSummaryList where fieldOxigenSaturationMax is less than DEFAULT_FIELD_OXIGEN_SATURATION_MAX
        defaultOxygenSaturationSummaryShouldNotBeFound("fieldOxigenSaturationMax.lessThan=" + DEFAULT_FIELD_OXIGEN_SATURATION_MAX);

        // Get all the oxygenSaturationSummaryList where fieldOxigenSaturationMax is less than UPDATED_FIELD_OXIGEN_SATURATION_MAX
        defaultOxygenSaturationSummaryShouldBeFound("fieldOxigenSaturationMax.lessThan=" + UPDATED_FIELD_OXIGEN_SATURATION_MAX);
    }

    @Test
    @Transactional
    void getAllOxygenSaturationSummariesByFieldOxigenSaturationMaxIsGreaterThanSomething() throws Exception {
        // Initialize the database
        oxygenSaturationSummaryRepository.saveAndFlush(oxygenSaturationSummary);

        // Get all the oxygenSaturationSummaryList where fieldOxigenSaturationMax is greater than DEFAULT_FIELD_OXIGEN_SATURATION_MAX
        defaultOxygenSaturationSummaryShouldNotBeFound("fieldOxigenSaturationMax.greaterThan=" + DEFAULT_FIELD_OXIGEN_SATURATION_MAX);

        // Get all the oxygenSaturationSummaryList where fieldOxigenSaturationMax is greater than SMALLER_FIELD_OXIGEN_SATURATION_MAX
        defaultOxygenSaturationSummaryShouldBeFound("fieldOxigenSaturationMax.greaterThan=" + SMALLER_FIELD_OXIGEN_SATURATION_MAX);
    }

    @Test
    @Transactional
    void getAllOxygenSaturationSummariesByFieldOxigenSaturationMinIsEqualToSomething() throws Exception {
        // Initialize the database
        oxygenSaturationSummaryRepository.saveAndFlush(oxygenSaturationSummary);

        // Get all the oxygenSaturationSummaryList where fieldOxigenSaturationMin equals to DEFAULT_FIELD_OXIGEN_SATURATION_MIN
        defaultOxygenSaturationSummaryShouldBeFound("fieldOxigenSaturationMin.equals=" + DEFAULT_FIELD_OXIGEN_SATURATION_MIN);

        // Get all the oxygenSaturationSummaryList where fieldOxigenSaturationMin equals to UPDATED_FIELD_OXIGEN_SATURATION_MIN
        defaultOxygenSaturationSummaryShouldNotBeFound("fieldOxigenSaturationMin.equals=" + UPDATED_FIELD_OXIGEN_SATURATION_MIN);
    }

    @Test
    @Transactional
    void getAllOxygenSaturationSummariesByFieldOxigenSaturationMinIsInShouldWork() throws Exception {
        // Initialize the database
        oxygenSaturationSummaryRepository.saveAndFlush(oxygenSaturationSummary);

        // Get all the oxygenSaturationSummaryList where fieldOxigenSaturationMin in DEFAULT_FIELD_OXIGEN_SATURATION_MIN or UPDATED_FIELD_OXIGEN_SATURATION_MIN
        defaultOxygenSaturationSummaryShouldBeFound(
            "fieldOxigenSaturationMin.in=" + DEFAULT_FIELD_OXIGEN_SATURATION_MIN + "," + UPDATED_FIELD_OXIGEN_SATURATION_MIN
        );

        // Get all the oxygenSaturationSummaryList where fieldOxigenSaturationMin equals to UPDATED_FIELD_OXIGEN_SATURATION_MIN
        defaultOxygenSaturationSummaryShouldNotBeFound("fieldOxigenSaturationMin.in=" + UPDATED_FIELD_OXIGEN_SATURATION_MIN);
    }

    @Test
    @Transactional
    void getAllOxygenSaturationSummariesByFieldOxigenSaturationMinIsNullOrNotNull() throws Exception {
        // Initialize the database
        oxygenSaturationSummaryRepository.saveAndFlush(oxygenSaturationSummary);

        // Get all the oxygenSaturationSummaryList where fieldOxigenSaturationMin is not null
        defaultOxygenSaturationSummaryShouldBeFound("fieldOxigenSaturationMin.specified=true");

        // Get all the oxygenSaturationSummaryList where fieldOxigenSaturationMin is null
        defaultOxygenSaturationSummaryShouldNotBeFound("fieldOxigenSaturationMin.specified=false");
    }

    @Test
    @Transactional
    void getAllOxygenSaturationSummariesByFieldOxigenSaturationMinIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        oxygenSaturationSummaryRepository.saveAndFlush(oxygenSaturationSummary);

        // Get all the oxygenSaturationSummaryList where fieldOxigenSaturationMin is greater than or equal to DEFAULT_FIELD_OXIGEN_SATURATION_MIN
        defaultOxygenSaturationSummaryShouldBeFound("fieldOxigenSaturationMin.greaterThanOrEqual=" + DEFAULT_FIELD_OXIGEN_SATURATION_MIN);

        // Get all the oxygenSaturationSummaryList where fieldOxigenSaturationMin is greater than or equal to UPDATED_FIELD_OXIGEN_SATURATION_MIN
        defaultOxygenSaturationSummaryShouldNotBeFound(
            "fieldOxigenSaturationMin.greaterThanOrEqual=" + UPDATED_FIELD_OXIGEN_SATURATION_MIN
        );
    }

    @Test
    @Transactional
    void getAllOxygenSaturationSummariesByFieldOxigenSaturationMinIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        oxygenSaturationSummaryRepository.saveAndFlush(oxygenSaturationSummary);

        // Get all the oxygenSaturationSummaryList where fieldOxigenSaturationMin is less than or equal to DEFAULT_FIELD_OXIGEN_SATURATION_MIN
        defaultOxygenSaturationSummaryShouldBeFound("fieldOxigenSaturationMin.lessThanOrEqual=" + DEFAULT_FIELD_OXIGEN_SATURATION_MIN);

        // Get all the oxygenSaturationSummaryList where fieldOxigenSaturationMin is less than or equal to SMALLER_FIELD_OXIGEN_SATURATION_MIN
        defaultOxygenSaturationSummaryShouldNotBeFound("fieldOxigenSaturationMin.lessThanOrEqual=" + SMALLER_FIELD_OXIGEN_SATURATION_MIN);
    }

    @Test
    @Transactional
    void getAllOxygenSaturationSummariesByFieldOxigenSaturationMinIsLessThanSomething() throws Exception {
        // Initialize the database
        oxygenSaturationSummaryRepository.saveAndFlush(oxygenSaturationSummary);

        // Get all the oxygenSaturationSummaryList where fieldOxigenSaturationMin is less than DEFAULT_FIELD_OXIGEN_SATURATION_MIN
        defaultOxygenSaturationSummaryShouldNotBeFound("fieldOxigenSaturationMin.lessThan=" + DEFAULT_FIELD_OXIGEN_SATURATION_MIN);

        // Get all the oxygenSaturationSummaryList where fieldOxigenSaturationMin is less than UPDATED_FIELD_OXIGEN_SATURATION_MIN
        defaultOxygenSaturationSummaryShouldBeFound("fieldOxigenSaturationMin.lessThan=" + UPDATED_FIELD_OXIGEN_SATURATION_MIN);
    }

    @Test
    @Transactional
    void getAllOxygenSaturationSummariesByFieldOxigenSaturationMinIsGreaterThanSomething() throws Exception {
        // Initialize the database
        oxygenSaturationSummaryRepository.saveAndFlush(oxygenSaturationSummary);

        // Get all the oxygenSaturationSummaryList where fieldOxigenSaturationMin is greater than DEFAULT_FIELD_OXIGEN_SATURATION_MIN
        defaultOxygenSaturationSummaryShouldNotBeFound("fieldOxigenSaturationMin.greaterThan=" + DEFAULT_FIELD_OXIGEN_SATURATION_MIN);

        // Get all the oxygenSaturationSummaryList where fieldOxigenSaturationMin is greater than SMALLER_FIELD_OXIGEN_SATURATION_MIN
        defaultOxygenSaturationSummaryShouldBeFound("fieldOxigenSaturationMin.greaterThan=" + SMALLER_FIELD_OXIGEN_SATURATION_MIN);
    }

    @Test
    @Transactional
    void getAllOxygenSaturationSummariesByFieldSuplementalOxigenFlowRateAverageIsEqualToSomething() throws Exception {
        // Initialize the database
        oxygenSaturationSummaryRepository.saveAndFlush(oxygenSaturationSummary);

        // Get all the oxygenSaturationSummaryList where fieldSuplementalOxigenFlowRateAverage equals to DEFAULT_FIELD_SUPLEMENTAL_OXIGEN_FLOW_RATE_AVERAGE
        defaultOxygenSaturationSummaryShouldBeFound(
            "fieldSuplementalOxigenFlowRateAverage.equals=" + DEFAULT_FIELD_SUPLEMENTAL_OXIGEN_FLOW_RATE_AVERAGE
        );

        // Get all the oxygenSaturationSummaryList where fieldSuplementalOxigenFlowRateAverage equals to UPDATED_FIELD_SUPLEMENTAL_OXIGEN_FLOW_RATE_AVERAGE
        defaultOxygenSaturationSummaryShouldNotBeFound(
            "fieldSuplementalOxigenFlowRateAverage.equals=" + UPDATED_FIELD_SUPLEMENTAL_OXIGEN_FLOW_RATE_AVERAGE
        );
    }

    @Test
    @Transactional
    void getAllOxygenSaturationSummariesByFieldSuplementalOxigenFlowRateAverageIsInShouldWork() throws Exception {
        // Initialize the database
        oxygenSaturationSummaryRepository.saveAndFlush(oxygenSaturationSummary);

        // Get all the oxygenSaturationSummaryList where fieldSuplementalOxigenFlowRateAverage in DEFAULT_FIELD_SUPLEMENTAL_OXIGEN_FLOW_RATE_AVERAGE or UPDATED_FIELD_SUPLEMENTAL_OXIGEN_FLOW_RATE_AVERAGE
        defaultOxygenSaturationSummaryShouldBeFound(
            "fieldSuplementalOxigenFlowRateAverage.in=" +
            DEFAULT_FIELD_SUPLEMENTAL_OXIGEN_FLOW_RATE_AVERAGE +
            "," +
            UPDATED_FIELD_SUPLEMENTAL_OXIGEN_FLOW_RATE_AVERAGE
        );

        // Get all the oxygenSaturationSummaryList where fieldSuplementalOxigenFlowRateAverage equals to UPDATED_FIELD_SUPLEMENTAL_OXIGEN_FLOW_RATE_AVERAGE
        defaultOxygenSaturationSummaryShouldNotBeFound(
            "fieldSuplementalOxigenFlowRateAverage.in=" + UPDATED_FIELD_SUPLEMENTAL_OXIGEN_FLOW_RATE_AVERAGE
        );
    }

    @Test
    @Transactional
    void getAllOxygenSaturationSummariesByFieldSuplementalOxigenFlowRateAverageIsNullOrNotNull() throws Exception {
        // Initialize the database
        oxygenSaturationSummaryRepository.saveAndFlush(oxygenSaturationSummary);

        // Get all the oxygenSaturationSummaryList where fieldSuplementalOxigenFlowRateAverage is not null
        defaultOxygenSaturationSummaryShouldBeFound("fieldSuplementalOxigenFlowRateAverage.specified=true");

        // Get all the oxygenSaturationSummaryList where fieldSuplementalOxigenFlowRateAverage is null
        defaultOxygenSaturationSummaryShouldNotBeFound("fieldSuplementalOxigenFlowRateAverage.specified=false");
    }

    @Test
    @Transactional
    void getAllOxygenSaturationSummariesByFieldSuplementalOxigenFlowRateAverageIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        oxygenSaturationSummaryRepository.saveAndFlush(oxygenSaturationSummary);

        // Get all the oxygenSaturationSummaryList where fieldSuplementalOxigenFlowRateAverage is greater than or equal to DEFAULT_FIELD_SUPLEMENTAL_OXIGEN_FLOW_RATE_AVERAGE
        defaultOxygenSaturationSummaryShouldBeFound(
            "fieldSuplementalOxigenFlowRateAverage.greaterThanOrEqual=" + DEFAULT_FIELD_SUPLEMENTAL_OXIGEN_FLOW_RATE_AVERAGE
        );

        // Get all the oxygenSaturationSummaryList where fieldSuplementalOxigenFlowRateAverage is greater than or equal to UPDATED_FIELD_SUPLEMENTAL_OXIGEN_FLOW_RATE_AVERAGE
        defaultOxygenSaturationSummaryShouldNotBeFound(
            "fieldSuplementalOxigenFlowRateAverage.greaterThanOrEqual=" + UPDATED_FIELD_SUPLEMENTAL_OXIGEN_FLOW_RATE_AVERAGE
        );
    }

    @Test
    @Transactional
    void getAllOxygenSaturationSummariesByFieldSuplementalOxigenFlowRateAverageIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        oxygenSaturationSummaryRepository.saveAndFlush(oxygenSaturationSummary);

        // Get all the oxygenSaturationSummaryList where fieldSuplementalOxigenFlowRateAverage is less than or equal to DEFAULT_FIELD_SUPLEMENTAL_OXIGEN_FLOW_RATE_AVERAGE
        defaultOxygenSaturationSummaryShouldBeFound(
            "fieldSuplementalOxigenFlowRateAverage.lessThanOrEqual=" + DEFAULT_FIELD_SUPLEMENTAL_OXIGEN_FLOW_RATE_AVERAGE
        );

        // Get all the oxygenSaturationSummaryList where fieldSuplementalOxigenFlowRateAverage is less than or equal to SMALLER_FIELD_SUPLEMENTAL_OXIGEN_FLOW_RATE_AVERAGE
        defaultOxygenSaturationSummaryShouldNotBeFound(
            "fieldSuplementalOxigenFlowRateAverage.lessThanOrEqual=" + SMALLER_FIELD_SUPLEMENTAL_OXIGEN_FLOW_RATE_AVERAGE
        );
    }

    @Test
    @Transactional
    void getAllOxygenSaturationSummariesByFieldSuplementalOxigenFlowRateAverageIsLessThanSomething() throws Exception {
        // Initialize the database
        oxygenSaturationSummaryRepository.saveAndFlush(oxygenSaturationSummary);

        // Get all the oxygenSaturationSummaryList where fieldSuplementalOxigenFlowRateAverage is less than DEFAULT_FIELD_SUPLEMENTAL_OXIGEN_FLOW_RATE_AVERAGE
        defaultOxygenSaturationSummaryShouldNotBeFound(
            "fieldSuplementalOxigenFlowRateAverage.lessThan=" + DEFAULT_FIELD_SUPLEMENTAL_OXIGEN_FLOW_RATE_AVERAGE
        );

        // Get all the oxygenSaturationSummaryList where fieldSuplementalOxigenFlowRateAverage is less than UPDATED_FIELD_SUPLEMENTAL_OXIGEN_FLOW_RATE_AVERAGE
        defaultOxygenSaturationSummaryShouldBeFound(
            "fieldSuplementalOxigenFlowRateAverage.lessThan=" + UPDATED_FIELD_SUPLEMENTAL_OXIGEN_FLOW_RATE_AVERAGE
        );
    }

    @Test
    @Transactional
    void getAllOxygenSaturationSummariesByFieldSuplementalOxigenFlowRateAverageIsGreaterThanSomething() throws Exception {
        // Initialize the database
        oxygenSaturationSummaryRepository.saveAndFlush(oxygenSaturationSummary);

        // Get all the oxygenSaturationSummaryList where fieldSuplementalOxigenFlowRateAverage is greater than DEFAULT_FIELD_SUPLEMENTAL_OXIGEN_FLOW_RATE_AVERAGE
        defaultOxygenSaturationSummaryShouldNotBeFound(
            "fieldSuplementalOxigenFlowRateAverage.greaterThan=" + DEFAULT_FIELD_SUPLEMENTAL_OXIGEN_FLOW_RATE_AVERAGE
        );

        // Get all the oxygenSaturationSummaryList where fieldSuplementalOxigenFlowRateAverage is greater than SMALLER_FIELD_SUPLEMENTAL_OXIGEN_FLOW_RATE_AVERAGE
        defaultOxygenSaturationSummaryShouldBeFound(
            "fieldSuplementalOxigenFlowRateAverage.greaterThan=" + SMALLER_FIELD_SUPLEMENTAL_OXIGEN_FLOW_RATE_AVERAGE
        );
    }

    @Test
    @Transactional
    void getAllOxygenSaturationSummariesByFieldSuplementalOxigenFlowRateMaxIsEqualToSomething() throws Exception {
        // Initialize the database
        oxygenSaturationSummaryRepository.saveAndFlush(oxygenSaturationSummary);

        // Get all the oxygenSaturationSummaryList where fieldSuplementalOxigenFlowRateMax equals to DEFAULT_FIELD_SUPLEMENTAL_OXIGEN_FLOW_RATE_MAX
        defaultOxygenSaturationSummaryShouldBeFound(
            "fieldSuplementalOxigenFlowRateMax.equals=" + DEFAULT_FIELD_SUPLEMENTAL_OXIGEN_FLOW_RATE_MAX
        );

        // Get all the oxygenSaturationSummaryList where fieldSuplementalOxigenFlowRateMax equals to UPDATED_FIELD_SUPLEMENTAL_OXIGEN_FLOW_RATE_MAX
        defaultOxygenSaturationSummaryShouldNotBeFound(
            "fieldSuplementalOxigenFlowRateMax.equals=" + UPDATED_FIELD_SUPLEMENTAL_OXIGEN_FLOW_RATE_MAX
        );
    }

    @Test
    @Transactional
    void getAllOxygenSaturationSummariesByFieldSuplementalOxigenFlowRateMaxIsInShouldWork() throws Exception {
        // Initialize the database
        oxygenSaturationSummaryRepository.saveAndFlush(oxygenSaturationSummary);

        // Get all the oxygenSaturationSummaryList where fieldSuplementalOxigenFlowRateMax in DEFAULT_FIELD_SUPLEMENTAL_OXIGEN_FLOW_RATE_MAX or UPDATED_FIELD_SUPLEMENTAL_OXIGEN_FLOW_RATE_MAX
        defaultOxygenSaturationSummaryShouldBeFound(
            "fieldSuplementalOxigenFlowRateMax.in=" +
            DEFAULT_FIELD_SUPLEMENTAL_OXIGEN_FLOW_RATE_MAX +
            "," +
            UPDATED_FIELD_SUPLEMENTAL_OXIGEN_FLOW_RATE_MAX
        );

        // Get all the oxygenSaturationSummaryList where fieldSuplementalOxigenFlowRateMax equals to UPDATED_FIELD_SUPLEMENTAL_OXIGEN_FLOW_RATE_MAX
        defaultOxygenSaturationSummaryShouldNotBeFound(
            "fieldSuplementalOxigenFlowRateMax.in=" + UPDATED_FIELD_SUPLEMENTAL_OXIGEN_FLOW_RATE_MAX
        );
    }

    @Test
    @Transactional
    void getAllOxygenSaturationSummariesByFieldSuplementalOxigenFlowRateMaxIsNullOrNotNull() throws Exception {
        // Initialize the database
        oxygenSaturationSummaryRepository.saveAndFlush(oxygenSaturationSummary);

        // Get all the oxygenSaturationSummaryList where fieldSuplementalOxigenFlowRateMax is not null
        defaultOxygenSaturationSummaryShouldBeFound("fieldSuplementalOxigenFlowRateMax.specified=true");

        // Get all the oxygenSaturationSummaryList where fieldSuplementalOxigenFlowRateMax is null
        defaultOxygenSaturationSummaryShouldNotBeFound("fieldSuplementalOxigenFlowRateMax.specified=false");
    }

    @Test
    @Transactional
    void getAllOxygenSaturationSummariesByFieldSuplementalOxigenFlowRateMaxIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        oxygenSaturationSummaryRepository.saveAndFlush(oxygenSaturationSummary);

        // Get all the oxygenSaturationSummaryList where fieldSuplementalOxigenFlowRateMax is greater than or equal to DEFAULT_FIELD_SUPLEMENTAL_OXIGEN_FLOW_RATE_MAX
        defaultOxygenSaturationSummaryShouldBeFound(
            "fieldSuplementalOxigenFlowRateMax.greaterThanOrEqual=" + DEFAULT_FIELD_SUPLEMENTAL_OXIGEN_FLOW_RATE_MAX
        );

        // Get all the oxygenSaturationSummaryList where fieldSuplementalOxigenFlowRateMax is greater than or equal to UPDATED_FIELD_SUPLEMENTAL_OXIGEN_FLOW_RATE_MAX
        defaultOxygenSaturationSummaryShouldNotBeFound(
            "fieldSuplementalOxigenFlowRateMax.greaterThanOrEqual=" + UPDATED_FIELD_SUPLEMENTAL_OXIGEN_FLOW_RATE_MAX
        );
    }

    @Test
    @Transactional
    void getAllOxygenSaturationSummariesByFieldSuplementalOxigenFlowRateMaxIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        oxygenSaturationSummaryRepository.saveAndFlush(oxygenSaturationSummary);

        // Get all the oxygenSaturationSummaryList where fieldSuplementalOxigenFlowRateMax is less than or equal to DEFAULT_FIELD_SUPLEMENTAL_OXIGEN_FLOW_RATE_MAX
        defaultOxygenSaturationSummaryShouldBeFound(
            "fieldSuplementalOxigenFlowRateMax.lessThanOrEqual=" + DEFAULT_FIELD_SUPLEMENTAL_OXIGEN_FLOW_RATE_MAX
        );

        // Get all the oxygenSaturationSummaryList where fieldSuplementalOxigenFlowRateMax is less than or equal to SMALLER_FIELD_SUPLEMENTAL_OXIGEN_FLOW_RATE_MAX
        defaultOxygenSaturationSummaryShouldNotBeFound(
            "fieldSuplementalOxigenFlowRateMax.lessThanOrEqual=" + SMALLER_FIELD_SUPLEMENTAL_OXIGEN_FLOW_RATE_MAX
        );
    }

    @Test
    @Transactional
    void getAllOxygenSaturationSummariesByFieldSuplementalOxigenFlowRateMaxIsLessThanSomething() throws Exception {
        // Initialize the database
        oxygenSaturationSummaryRepository.saveAndFlush(oxygenSaturationSummary);

        // Get all the oxygenSaturationSummaryList where fieldSuplementalOxigenFlowRateMax is less than DEFAULT_FIELD_SUPLEMENTAL_OXIGEN_FLOW_RATE_MAX
        defaultOxygenSaturationSummaryShouldNotBeFound(
            "fieldSuplementalOxigenFlowRateMax.lessThan=" + DEFAULT_FIELD_SUPLEMENTAL_OXIGEN_FLOW_RATE_MAX
        );

        // Get all the oxygenSaturationSummaryList where fieldSuplementalOxigenFlowRateMax is less than UPDATED_FIELD_SUPLEMENTAL_OXIGEN_FLOW_RATE_MAX
        defaultOxygenSaturationSummaryShouldBeFound(
            "fieldSuplementalOxigenFlowRateMax.lessThan=" + UPDATED_FIELD_SUPLEMENTAL_OXIGEN_FLOW_RATE_MAX
        );
    }

    @Test
    @Transactional
    void getAllOxygenSaturationSummariesByFieldSuplementalOxigenFlowRateMaxIsGreaterThanSomething() throws Exception {
        // Initialize the database
        oxygenSaturationSummaryRepository.saveAndFlush(oxygenSaturationSummary);

        // Get all the oxygenSaturationSummaryList where fieldSuplementalOxigenFlowRateMax is greater than DEFAULT_FIELD_SUPLEMENTAL_OXIGEN_FLOW_RATE_MAX
        defaultOxygenSaturationSummaryShouldNotBeFound(
            "fieldSuplementalOxigenFlowRateMax.greaterThan=" + DEFAULT_FIELD_SUPLEMENTAL_OXIGEN_FLOW_RATE_MAX
        );

        // Get all the oxygenSaturationSummaryList where fieldSuplementalOxigenFlowRateMax is greater than SMALLER_FIELD_SUPLEMENTAL_OXIGEN_FLOW_RATE_MAX
        defaultOxygenSaturationSummaryShouldBeFound(
            "fieldSuplementalOxigenFlowRateMax.greaterThan=" + SMALLER_FIELD_SUPLEMENTAL_OXIGEN_FLOW_RATE_MAX
        );
    }

    @Test
    @Transactional
    void getAllOxygenSaturationSummariesByFieldSuplementalOxigenFlowRateMinIsEqualToSomething() throws Exception {
        // Initialize the database
        oxygenSaturationSummaryRepository.saveAndFlush(oxygenSaturationSummary);

        // Get all the oxygenSaturationSummaryList where fieldSuplementalOxigenFlowRateMin equals to DEFAULT_FIELD_SUPLEMENTAL_OXIGEN_FLOW_RATE_MIN
        defaultOxygenSaturationSummaryShouldBeFound(
            "fieldSuplementalOxigenFlowRateMin.equals=" + DEFAULT_FIELD_SUPLEMENTAL_OXIGEN_FLOW_RATE_MIN
        );

        // Get all the oxygenSaturationSummaryList where fieldSuplementalOxigenFlowRateMin equals to UPDATED_FIELD_SUPLEMENTAL_OXIGEN_FLOW_RATE_MIN
        defaultOxygenSaturationSummaryShouldNotBeFound(
            "fieldSuplementalOxigenFlowRateMin.equals=" + UPDATED_FIELD_SUPLEMENTAL_OXIGEN_FLOW_RATE_MIN
        );
    }

    @Test
    @Transactional
    void getAllOxygenSaturationSummariesByFieldSuplementalOxigenFlowRateMinIsInShouldWork() throws Exception {
        // Initialize the database
        oxygenSaturationSummaryRepository.saveAndFlush(oxygenSaturationSummary);

        // Get all the oxygenSaturationSummaryList where fieldSuplementalOxigenFlowRateMin in DEFAULT_FIELD_SUPLEMENTAL_OXIGEN_FLOW_RATE_MIN or UPDATED_FIELD_SUPLEMENTAL_OXIGEN_FLOW_RATE_MIN
        defaultOxygenSaturationSummaryShouldBeFound(
            "fieldSuplementalOxigenFlowRateMin.in=" +
            DEFAULT_FIELD_SUPLEMENTAL_OXIGEN_FLOW_RATE_MIN +
            "," +
            UPDATED_FIELD_SUPLEMENTAL_OXIGEN_FLOW_RATE_MIN
        );

        // Get all the oxygenSaturationSummaryList where fieldSuplementalOxigenFlowRateMin equals to UPDATED_FIELD_SUPLEMENTAL_OXIGEN_FLOW_RATE_MIN
        defaultOxygenSaturationSummaryShouldNotBeFound(
            "fieldSuplementalOxigenFlowRateMin.in=" + UPDATED_FIELD_SUPLEMENTAL_OXIGEN_FLOW_RATE_MIN
        );
    }

    @Test
    @Transactional
    void getAllOxygenSaturationSummariesByFieldSuplementalOxigenFlowRateMinIsNullOrNotNull() throws Exception {
        // Initialize the database
        oxygenSaturationSummaryRepository.saveAndFlush(oxygenSaturationSummary);

        // Get all the oxygenSaturationSummaryList where fieldSuplementalOxigenFlowRateMin is not null
        defaultOxygenSaturationSummaryShouldBeFound("fieldSuplementalOxigenFlowRateMin.specified=true");

        // Get all the oxygenSaturationSummaryList where fieldSuplementalOxigenFlowRateMin is null
        defaultOxygenSaturationSummaryShouldNotBeFound("fieldSuplementalOxigenFlowRateMin.specified=false");
    }

    @Test
    @Transactional
    void getAllOxygenSaturationSummariesByFieldSuplementalOxigenFlowRateMinIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        oxygenSaturationSummaryRepository.saveAndFlush(oxygenSaturationSummary);

        // Get all the oxygenSaturationSummaryList where fieldSuplementalOxigenFlowRateMin is greater than or equal to DEFAULT_FIELD_SUPLEMENTAL_OXIGEN_FLOW_RATE_MIN
        defaultOxygenSaturationSummaryShouldBeFound(
            "fieldSuplementalOxigenFlowRateMin.greaterThanOrEqual=" + DEFAULT_FIELD_SUPLEMENTAL_OXIGEN_FLOW_RATE_MIN
        );

        // Get all the oxygenSaturationSummaryList where fieldSuplementalOxigenFlowRateMin is greater than or equal to UPDATED_FIELD_SUPLEMENTAL_OXIGEN_FLOW_RATE_MIN
        defaultOxygenSaturationSummaryShouldNotBeFound(
            "fieldSuplementalOxigenFlowRateMin.greaterThanOrEqual=" + UPDATED_FIELD_SUPLEMENTAL_OXIGEN_FLOW_RATE_MIN
        );
    }

    @Test
    @Transactional
    void getAllOxygenSaturationSummariesByFieldSuplementalOxigenFlowRateMinIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        oxygenSaturationSummaryRepository.saveAndFlush(oxygenSaturationSummary);

        // Get all the oxygenSaturationSummaryList where fieldSuplementalOxigenFlowRateMin is less than or equal to DEFAULT_FIELD_SUPLEMENTAL_OXIGEN_FLOW_RATE_MIN
        defaultOxygenSaturationSummaryShouldBeFound(
            "fieldSuplementalOxigenFlowRateMin.lessThanOrEqual=" + DEFAULT_FIELD_SUPLEMENTAL_OXIGEN_FLOW_RATE_MIN
        );

        // Get all the oxygenSaturationSummaryList where fieldSuplementalOxigenFlowRateMin is less than or equal to SMALLER_FIELD_SUPLEMENTAL_OXIGEN_FLOW_RATE_MIN
        defaultOxygenSaturationSummaryShouldNotBeFound(
            "fieldSuplementalOxigenFlowRateMin.lessThanOrEqual=" + SMALLER_FIELD_SUPLEMENTAL_OXIGEN_FLOW_RATE_MIN
        );
    }

    @Test
    @Transactional
    void getAllOxygenSaturationSummariesByFieldSuplementalOxigenFlowRateMinIsLessThanSomething() throws Exception {
        // Initialize the database
        oxygenSaturationSummaryRepository.saveAndFlush(oxygenSaturationSummary);

        // Get all the oxygenSaturationSummaryList where fieldSuplementalOxigenFlowRateMin is less than DEFAULT_FIELD_SUPLEMENTAL_OXIGEN_FLOW_RATE_MIN
        defaultOxygenSaturationSummaryShouldNotBeFound(
            "fieldSuplementalOxigenFlowRateMin.lessThan=" + DEFAULT_FIELD_SUPLEMENTAL_OXIGEN_FLOW_RATE_MIN
        );

        // Get all the oxygenSaturationSummaryList where fieldSuplementalOxigenFlowRateMin is less than UPDATED_FIELD_SUPLEMENTAL_OXIGEN_FLOW_RATE_MIN
        defaultOxygenSaturationSummaryShouldBeFound(
            "fieldSuplementalOxigenFlowRateMin.lessThan=" + UPDATED_FIELD_SUPLEMENTAL_OXIGEN_FLOW_RATE_MIN
        );
    }

    @Test
    @Transactional
    void getAllOxygenSaturationSummariesByFieldSuplementalOxigenFlowRateMinIsGreaterThanSomething() throws Exception {
        // Initialize the database
        oxygenSaturationSummaryRepository.saveAndFlush(oxygenSaturationSummary);

        // Get all the oxygenSaturationSummaryList where fieldSuplementalOxigenFlowRateMin is greater than DEFAULT_FIELD_SUPLEMENTAL_OXIGEN_FLOW_RATE_MIN
        defaultOxygenSaturationSummaryShouldNotBeFound(
            "fieldSuplementalOxigenFlowRateMin.greaterThan=" + DEFAULT_FIELD_SUPLEMENTAL_OXIGEN_FLOW_RATE_MIN
        );

        // Get all the oxygenSaturationSummaryList where fieldSuplementalOxigenFlowRateMin is greater than SMALLER_FIELD_SUPLEMENTAL_OXIGEN_FLOW_RATE_MIN
        defaultOxygenSaturationSummaryShouldBeFound(
            "fieldSuplementalOxigenFlowRateMin.greaterThan=" + SMALLER_FIELD_SUPLEMENTAL_OXIGEN_FLOW_RATE_MIN
        );
    }

    @Test
    @Transactional
    void getAllOxygenSaturationSummariesByFieldOxigenTherapyAdministrationModeIsEqualToSomething() throws Exception {
        // Initialize the database
        oxygenSaturationSummaryRepository.saveAndFlush(oxygenSaturationSummary);

        // Get all the oxygenSaturationSummaryList where fieldOxigenTherapyAdministrationMode equals to DEFAULT_FIELD_OXIGEN_THERAPY_ADMINISTRATION_MODE
        defaultOxygenSaturationSummaryShouldBeFound(
            "fieldOxigenTherapyAdministrationMode.equals=" + DEFAULT_FIELD_OXIGEN_THERAPY_ADMINISTRATION_MODE
        );

        // Get all the oxygenSaturationSummaryList where fieldOxigenTherapyAdministrationMode equals to UPDATED_FIELD_OXIGEN_THERAPY_ADMINISTRATION_MODE
        defaultOxygenSaturationSummaryShouldNotBeFound(
            "fieldOxigenTherapyAdministrationMode.equals=" + UPDATED_FIELD_OXIGEN_THERAPY_ADMINISTRATION_MODE
        );
    }

    @Test
    @Transactional
    void getAllOxygenSaturationSummariesByFieldOxigenTherapyAdministrationModeIsInShouldWork() throws Exception {
        // Initialize the database
        oxygenSaturationSummaryRepository.saveAndFlush(oxygenSaturationSummary);

        // Get all the oxygenSaturationSummaryList where fieldOxigenTherapyAdministrationMode in DEFAULT_FIELD_OXIGEN_THERAPY_ADMINISTRATION_MODE or UPDATED_FIELD_OXIGEN_THERAPY_ADMINISTRATION_MODE
        defaultOxygenSaturationSummaryShouldBeFound(
            "fieldOxigenTherapyAdministrationMode.in=" +
            DEFAULT_FIELD_OXIGEN_THERAPY_ADMINISTRATION_MODE +
            "," +
            UPDATED_FIELD_OXIGEN_THERAPY_ADMINISTRATION_MODE
        );

        // Get all the oxygenSaturationSummaryList where fieldOxigenTherapyAdministrationMode equals to UPDATED_FIELD_OXIGEN_THERAPY_ADMINISTRATION_MODE
        defaultOxygenSaturationSummaryShouldNotBeFound(
            "fieldOxigenTherapyAdministrationMode.in=" + UPDATED_FIELD_OXIGEN_THERAPY_ADMINISTRATION_MODE
        );
    }

    @Test
    @Transactional
    void getAllOxygenSaturationSummariesByFieldOxigenTherapyAdministrationModeIsNullOrNotNull() throws Exception {
        // Initialize the database
        oxygenSaturationSummaryRepository.saveAndFlush(oxygenSaturationSummary);

        // Get all the oxygenSaturationSummaryList where fieldOxigenTherapyAdministrationMode is not null
        defaultOxygenSaturationSummaryShouldBeFound("fieldOxigenTherapyAdministrationMode.specified=true");

        // Get all the oxygenSaturationSummaryList where fieldOxigenTherapyAdministrationMode is null
        defaultOxygenSaturationSummaryShouldNotBeFound("fieldOxigenTherapyAdministrationMode.specified=false");
    }

    @Test
    @Transactional
    void getAllOxygenSaturationSummariesByFieldOxigenTherapyAdministrationModeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        oxygenSaturationSummaryRepository.saveAndFlush(oxygenSaturationSummary);

        // Get all the oxygenSaturationSummaryList where fieldOxigenTherapyAdministrationMode is greater than or equal to DEFAULT_FIELD_OXIGEN_THERAPY_ADMINISTRATION_MODE
        defaultOxygenSaturationSummaryShouldBeFound(
            "fieldOxigenTherapyAdministrationMode.greaterThanOrEqual=" + DEFAULT_FIELD_OXIGEN_THERAPY_ADMINISTRATION_MODE
        );

        // Get all the oxygenSaturationSummaryList where fieldOxigenTherapyAdministrationMode is greater than or equal to UPDATED_FIELD_OXIGEN_THERAPY_ADMINISTRATION_MODE
        defaultOxygenSaturationSummaryShouldNotBeFound(
            "fieldOxigenTherapyAdministrationMode.greaterThanOrEqual=" + UPDATED_FIELD_OXIGEN_THERAPY_ADMINISTRATION_MODE
        );
    }

    @Test
    @Transactional
    void getAllOxygenSaturationSummariesByFieldOxigenTherapyAdministrationModeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        oxygenSaturationSummaryRepository.saveAndFlush(oxygenSaturationSummary);

        // Get all the oxygenSaturationSummaryList where fieldOxigenTherapyAdministrationMode is less than or equal to DEFAULT_FIELD_OXIGEN_THERAPY_ADMINISTRATION_MODE
        defaultOxygenSaturationSummaryShouldBeFound(
            "fieldOxigenTherapyAdministrationMode.lessThanOrEqual=" + DEFAULT_FIELD_OXIGEN_THERAPY_ADMINISTRATION_MODE
        );

        // Get all the oxygenSaturationSummaryList where fieldOxigenTherapyAdministrationMode is less than or equal to SMALLER_FIELD_OXIGEN_THERAPY_ADMINISTRATION_MODE
        defaultOxygenSaturationSummaryShouldNotBeFound(
            "fieldOxigenTherapyAdministrationMode.lessThanOrEqual=" + SMALLER_FIELD_OXIGEN_THERAPY_ADMINISTRATION_MODE
        );
    }

    @Test
    @Transactional
    void getAllOxygenSaturationSummariesByFieldOxigenTherapyAdministrationModeIsLessThanSomething() throws Exception {
        // Initialize the database
        oxygenSaturationSummaryRepository.saveAndFlush(oxygenSaturationSummary);

        // Get all the oxygenSaturationSummaryList where fieldOxigenTherapyAdministrationMode is less than DEFAULT_FIELD_OXIGEN_THERAPY_ADMINISTRATION_MODE
        defaultOxygenSaturationSummaryShouldNotBeFound(
            "fieldOxigenTherapyAdministrationMode.lessThan=" + DEFAULT_FIELD_OXIGEN_THERAPY_ADMINISTRATION_MODE
        );

        // Get all the oxygenSaturationSummaryList where fieldOxigenTherapyAdministrationMode is less than UPDATED_FIELD_OXIGEN_THERAPY_ADMINISTRATION_MODE
        defaultOxygenSaturationSummaryShouldBeFound(
            "fieldOxigenTherapyAdministrationMode.lessThan=" + UPDATED_FIELD_OXIGEN_THERAPY_ADMINISTRATION_MODE
        );
    }

    @Test
    @Transactional
    void getAllOxygenSaturationSummariesByFieldOxigenTherapyAdministrationModeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        oxygenSaturationSummaryRepository.saveAndFlush(oxygenSaturationSummary);

        // Get all the oxygenSaturationSummaryList where fieldOxigenTherapyAdministrationMode is greater than DEFAULT_FIELD_OXIGEN_THERAPY_ADMINISTRATION_MODE
        defaultOxygenSaturationSummaryShouldNotBeFound(
            "fieldOxigenTherapyAdministrationMode.greaterThan=" + DEFAULT_FIELD_OXIGEN_THERAPY_ADMINISTRATION_MODE
        );

        // Get all the oxygenSaturationSummaryList where fieldOxigenTherapyAdministrationMode is greater than SMALLER_FIELD_OXIGEN_THERAPY_ADMINISTRATION_MODE
        defaultOxygenSaturationSummaryShouldBeFound(
            "fieldOxigenTherapyAdministrationMode.greaterThan=" + SMALLER_FIELD_OXIGEN_THERAPY_ADMINISTRATION_MODE
        );
    }

    @Test
    @Transactional
    void getAllOxygenSaturationSummariesByFieldOxigenSaturationModeIsEqualToSomething() throws Exception {
        // Initialize the database
        oxygenSaturationSummaryRepository.saveAndFlush(oxygenSaturationSummary);

        // Get all the oxygenSaturationSummaryList where fieldOxigenSaturationMode equals to DEFAULT_FIELD_OXIGEN_SATURATION_MODE
        defaultOxygenSaturationSummaryShouldBeFound("fieldOxigenSaturationMode.equals=" + DEFAULT_FIELD_OXIGEN_SATURATION_MODE);

        // Get all the oxygenSaturationSummaryList where fieldOxigenSaturationMode equals to UPDATED_FIELD_OXIGEN_SATURATION_MODE
        defaultOxygenSaturationSummaryShouldNotBeFound("fieldOxigenSaturationMode.equals=" + UPDATED_FIELD_OXIGEN_SATURATION_MODE);
    }

    @Test
    @Transactional
    void getAllOxygenSaturationSummariesByFieldOxigenSaturationModeIsInShouldWork() throws Exception {
        // Initialize the database
        oxygenSaturationSummaryRepository.saveAndFlush(oxygenSaturationSummary);

        // Get all the oxygenSaturationSummaryList where fieldOxigenSaturationMode in DEFAULT_FIELD_OXIGEN_SATURATION_MODE or UPDATED_FIELD_OXIGEN_SATURATION_MODE
        defaultOxygenSaturationSummaryShouldBeFound(
            "fieldOxigenSaturationMode.in=" + DEFAULT_FIELD_OXIGEN_SATURATION_MODE + "," + UPDATED_FIELD_OXIGEN_SATURATION_MODE
        );

        // Get all the oxygenSaturationSummaryList where fieldOxigenSaturationMode equals to UPDATED_FIELD_OXIGEN_SATURATION_MODE
        defaultOxygenSaturationSummaryShouldNotBeFound("fieldOxigenSaturationMode.in=" + UPDATED_FIELD_OXIGEN_SATURATION_MODE);
    }

    @Test
    @Transactional
    void getAllOxygenSaturationSummariesByFieldOxigenSaturationModeIsNullOrNotNull() throws Exception {
        // Initialize the database
        oxygenSaturationSummaryRepository.saveAndFlush(oxygenSaturationSummary);

        // Get all the oxygenSaturationSummaryList where fieldOxigenSaturationMode is not null
        defaultOxygenSaturationSummaryShouldBeFound("fieldOxigenSaturationMode.specified=true");

        // Get all the oxygenSaturationSummaryList where fieldOxigenSaturationMode is null
        defaultOxygenSaturationSummaryShouldNotBeFound("fieldOxigenSaturationMode.specified=false");
    }

    @Test
    @Transactional
    void getAllOxygenSaturationSummariesByFieldOxigenSaturationModeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        oxygenSaturationSummaryRepository.saveAndFlush(oxygenSaturationSummary);

        // Get all the oxygenSaturationSummaryList where fieldOxigenSaturationMode is greater than or equal to DEFAULT_FIELD_OXIGEN_SATURATION_MODE
        defaultOxygenSaturationSummaryShouldBeFound("fieldOxigenSaturationMode.greaterThanOrEqual=" + DEFAULT_FIELD_OXIGEN_SATURATION_MODE);

        // Get all the oxygenSaturationSummaryList where fieldOxigenSaturationMode is greater than or equal to UPDATED_FIELD_OXIGEN_SATURATION_MODE
        defaultOxygenSaturationSummaryShouldNotBeFound(
            "fieldOxigenSaturationMode.greaterThanOrEqual=" + UPDATED_FIELD_OXIGEN_SATURATION_MODE
        );
    }

    @Test
    @Transactional
    void getAllOxygenSaturationSummariesByFieldOxigenSaturationModeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        oxygenSaturationSummaryRepository.saveAndFlush(oxygenSaturationSummary);

        // Get all the oxygenSaturationSummaryList where fieldOxigenSaturationMode is less than or equal to DEFAULT_FIELD_OXIGEN_SATURATION_MODE
        defaultOxygenSaturationSummaryShouldBeFound("fieldOxigenSaturationMode.lessThanOrEqual=" + DEFAULT_FIELD_OXIGEN_SATURATION_MODE);

        // Get all the oxygenSaturationSummaryList where fieldOxigenSaturationMode is less than or equal to SMALLER_FIELD_OXIGEN_SATURATION_MODE
        defaultOxygenSaturationSummaryShouldNotBeFound("fieldOxigenSaturationMode.lessThanOrEqual=" + SMALLER_FIELD_OXIGEN_SATURATION_MODE);
    }

    @Test
    @Transactional
    void getAllOxygenSaturationSummariesByFieldOxigenSaturationModeIsLessThanSomething() throws Exception {
        // Initialize the database
        oxygenSaturationSummaryRepository.saveAndFlush(oxygenSaturationSummary);

        // Get all the oxygenSaturationSummaryList where fieldOxigenSaturationMode is less than DEFAULT_FIELD_OXIGEN_SATURATION_MODE
        defaultOxygenSaturationSummaryShouldNotBeFound("fieldOxigenSaturationMode.lessThan=" + DEFAULT_FIELD_OXIGEN_SATURATION_MODE);

        // Get all the oxygenSaturationSummaryList where fieldOxigenSaturationMode is less than UPDATED_FIELD_OXIGEN_SATURATION_MODE
        defaultOxygenSaturationSummaryShouldBeFound("fieldOxigenSaturationMode.lessThan=" + UPDATED_FIELD_OXIGEN_SATURATION_MODE);
    }

    @Test
    @Transactional
    void getAllOxygenSaturationSummariesByFieldOxigenSaturationModeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        oxygenSaturationSummaryRepository.saveAndFlush(oxygenSaturationSummary);

        // Get all the oxygenSaturationSummaryList where fieldOxigenSaturationMode is greater than DEFAULT_FIELD_OXIGEN_SATURATION_MODE
        defaultOxygenSaturationSummaryShouldNotBeFound("fieldOxigenSaturationMode.greaterThan=" + DEFAULT_FIELD_OXIGEN_SATURATION_MODE);

        // Get all the oxygenSaturationSummaryList where fieldOxigenSaturationMode is greater than SMALLER_FIELD_OXIGEN_SATURATION_MODE
        defaultOxygenSaturationSummaryShouldBeFound("fieldOxigenSaturationMode.greaterThan=" + SMALLER_FIELD_OXIGEN_SATURATION_MODE);
    }

    @Test
    @Transactional
    void getAllOxygenSaturationSummariesByFieldOxigenSaturationMeasurementMethodIsEqualToSomething() throws Exception {
        // Initialize the database
        oxygenSaturationSummaryRepository.saveAndFlush(oxygenSaturationSummary);

        // Get all the oxygenSaturationSummaryList where fieldOxigenSaturationMeasurementMethod equals to DEFAULT_FIELD_OXIGEN_SATURATION_MEASUREMENT_METHOD
        defaultOxygenSaturationSummaryShouldBeFound(
            "fieldOxigenSaturationMeasurementMethod.equals=" + DEFAULT_FIELD_OXIGEN_SATURATION_MEASUREMENT_METHOD
        );

        // Get all the oxygenSaturationSummaryList where fieldOxigenSaturationMeasurementMethod equals to UPDATED_FIELD_OXIGEN_SATURATION_MEASUREMENT_METHOD
        defaultOxygenSaturationSummaryShouldNotBeFound(
            "fieldOxigenSaturationMeasurementMethod.equals=" + UPDATED_FIELD_OXIGEN_SATURATION_MEASUREMENT_METHOD
        );
    }

    @Test
    @Transactional
    void getAllOxygenSaturationSummariesByFieldOxigenSaturationMeasurementMethodIsInShouldWork() throws Exception {
        // Initialize the database
        oxygenSaturationSummaryRepository.saveAndFlush(oxygenSaturationSummary);

        // Get all the oxygenSaturationSummaryList where fieldOxigenSaturationMeasurementMethod in DEFAULT_FIELD_OXIGEN_SATURATION_MEASUREMENT_METHOD or UPDATED_FIELD_OXIGEN_SATURATION_MEASUREMENT_METHOD
        defaultOxygenSaturationSummaryShouldBeFound(
            "fieldOxigenSaturationMeasurementMethod.in=" +
            DEFAULT_FIELD_OXIGEN_SATURATION_MEASUREMENT_METHOD +
            "," +
            UPDATED_FIELD_OXIGEN_SATURATION_MEASUREMENT_METHOD
        );

        // Get all the oxygenSaturationSummaryList where fieldOxigenSaturationMeasurementMethod equals to UPDATED_FIELD_OXIGEN_SATURATION_MEASUREMENT_METHOD
        defaultOxygenSaturationSummaryShouldNotBeFound(
            "fieldOxigenSaturationMeasurementMethod.in=" + UPDATED_FIELD_OXIGEN_SATURATION_MEASUREMENT_METHOD
        );
    }

    @Test
    @Transactional
    void getAllOxygenSaturationSummariesByFieldOxigenSaturationMeasurementMethodIsNullOrNotNull() throws Exception {
        // Initialize the database
        oxygenSaturationSummaryRepository.saveAndFlush(oxygenSaturationSummary);

        // Get all the oxygenSaturationSummaryList where fieldOxigenSaturationMeasurementMethod is not null
        defaultOxygenSaturationSummaryShouldBeFound("fieldOxigenSaturationMeasurementMethod.specified=true");

        // Get all the oxygenSaturationSummaryList where fieldOxigenSaturationMeasurementMethod is null
        defaultOxygenSaturationSummaryShouldNotBeFound("fieldOxigenSaturationMeasurementMethod.specified=false");
    }

    @Test
    @Transactional
    void getAllOxygenSaturationSummariesByFieldOxigenSaturationMeasurementMethodIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        oxygenSaturationSummaryRepository.saveAndFlush(oxygenSaturationSummary);

        // Get all the oxygenSaturationSummaryList where fieldOxigenSaturationMeasurementMethod is greater than or equal to DEFAULT_FIELD_OXIGEN_SATURATION_MEASUREMENT_METHOD
        defaultOxygenSaturationSummaryShouldBeFound(
            "fieldOxigenSaturationMeasurementMethod.greaterThanOrEqual=" + DEFAULT_FIELD_OXIGEN_SATURATION_MEASUREMENT_METHOD
        );

        // Get all the oxygenSaturationSummaryList where fieldOxigenSaturationMeasurementMethod is greater than or equal to UPDATED_FIELD_OXIGEN_SATURATION_MEASUREMENT_METHOD
        defaultOxygenSaturationSummaryShouldNotBeFound(
            "fieldOxigenSaturationMeasurementMethod.greaterThanOrEqual=" + UPDATED_FIELD_OXIGEN_SATURATION_MEASUREMENT_METHOD
        );
    }

    @Test
    @Transactional
    void getAllOxygenSaturationSummariesByFieldOxigenSaturationMeasurementMethodIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        oxygenSaturationSummaryRepository.saveAndFlush(oxygenSaturationSummary);

        // Get all the oxygenSaturationSummaryList where fieldOxigenSaturationMeasurementMethod is less than or equal to DEFAULT_FIELD_OXIGEN_SATURATION_MEASUREMENT_METHOD
        defaultOxygenSaturationSummaryShouldBeFound(
            "fieldOxigenSaturationMeasurementMethod.lessThanOrEqual=" + DEFAULT_FIELD_OXIGEN_SATURATION_MEASUREMENT_METHOD
        );

        // Get all the oxygenSaturationSummaryList where fieldOxigenSaturationMeasurementMethod is less than or equal to SMALLER_FIELD_OXIGEN_SATURATION_MEASUREMENT_METHOD
        defaultOxygenSaturationSummaryShouldNotBeFound(
            "fieldOxigenSaturationMeasurementMethod.lessThanOrEqual=" + SMALLER_FIELD_OXIGEN_SATURATION_MEASUREMENT_METHOD
        );
    }

    @Test
    @Transactional
    void getAllOxygenSaturationSummariesByFieldOxigenSaturationMeasurementMethodIsLessThanSomething() throws Exception {
        // Initialize the database
        oxygenSaturationSummaryRepository.saveAndFlush(oxygenSaturationSummary);

        // Get all the oxygenSaturationSummaryList where fieldOxigenSaturationMeasurementMethod is less than DEFAULT_FIELD_OXIGEN_SATURATION_MEASUREMENT_METHOD
        defaultOxygenSaturationSummaryShouldNotBeFound(
            "fieldOxigenSaturationMeasurementMethod.lessThan=" + DEFAULT_FIELD_OXIGEN_SATURATION_MEASUREMENT_METHOD
        );

        // Get all the oxygenSaturationSummaryList where fieldOxigenSaturationMeasurementMethod is less than UPDATED_FIELD_OXIGEN_SATURATION_MEASUREMENT_METHOD
        defaultOxygenSaturationSummaryShouldBeFound(
            "fieldOxigenSaturationMeasurementMethod.lessThan=" + UPDATED_FIELD_OXIGEN_SATURATION_MEASUREMENT_METHOD
        );
    }

    @Test
    @Transactional
    void getAllOxygenSaturationSummariesByFieldOxigenSaturationMeasurementMethodIsGreaterThanSomething() throws Exception {
        // Initialize the database
        oxygenSaturationSummaryRepository.saveAndFlush(oxygenSaturationSummary);

        // Get all the oxygenSaturationSummaryList where fieldOxigenSaturationMeasurementMethod is greater than DEFAULT_FIELD_OXIGEN_SATURATION_MEASUREMENT_METHOD
        defaultOxygenSaturationSummaryShouldNotBeFound(
            "fieldOxigenSaturationMeasurementMethod.greaterThan=" + DEFAULT_FIELD_OXIGEN_SATURATION_MEASUREMENT_METHOD
        );

        // Get all the oxygenSaturationSummaryList where fieldOxigenSaturationMeasurementMethod is greater than SMALLER_FIELD_OXIGEN_SATURATION_MEASUREMENT_METHOD
        defaultOxygenSaturationSummaryShouldBeFound(
            "fieldOxigenSaturationMeasurementMethod.greaterThan=" + SMALLER_FIELD_OXIGEN_SATURATION_MEASUREMENT_METHOD
        );
    }

    @Test
    @Transactional
    void getAllOxygenSaturationSummariesByEndTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        oxygenSaturationSummaryRepository.saveAndFlush(oxygenSaturationSummary);

        // Get all the oxygenSaturationSummaryList where endTime equals to DEFAULT_END_TIME
        defaultOxygenSaturationSummaryShouldBeFound("endTime.equals=" + DEFAULT_END_TIME);

        // Get all the oxygenSaturationSummaryList where endTime equals to UPDATED_END_TIME
        defaultOxygenSaturationSummaryShouldNotBeFound("endTime.equals=" + UPDATED_END_TIME);
    }

    @Test
    @Transactional
    void getAllOxygenSaturationSummariesByEndTimeIsInShouldWork() throws Exception {
        // Initialize the database
        oxygenSaturationSummaryRepository.saveAndFlush(oxygenSaturationSummary);

        // Get all the oxygenSaturationSummaryList where endTime in DEFAULT_END_TIME or UPDATED_END_TIME
        defaultOxygenSaturationSummaryShouldBeFound("endTime.in=" + DEFAULT_END_TIME + "," + UPDATED_END_TIME);

        // Get all the oxygenSaturationSummaryList where endTime equals to UPDATED_END_TIME
        defaultOxygenSaturationSummaryShouldNotBeFound("endTime.in=" + UPDATED_END_TIME);
    }

    @Test
    @Transactional
    void getAllOxygenSaturationSummariesByEndTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        oxygenSaturationSummaryRepository.saveAndFlush(oxygenSaturationSummary);

        // Get all the oxygenSaturationSummaryList where endTime is not null
        defaultOxygenSaturationSummaryShouldBeFound("endTime.specified=true");

        // Get all the oxygenSaturationSummaryList where endTime is null
        defaultOxygenSaturationSummaryShouldNotBeFound("endTime.specified=false");
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultOxygenSaturationSummaryShouldBeFound(String filter) throws Exception {
        restOxygenSaturationSummaryMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(oxygenSaturationSummary.getId().toString())))
            .andExpect(jsonPath("$.[*].usuarioId").value(hasItem(DEFAULT_USUARIO_ID)))
            .andExpect(jsonPath("$.[*].empresaId").value(hasItem(DEFAULT_EMPRESA_ID)))
            .andExpect(jsonPath("$.[*].fieldOxigenSaturationAverage").value(hasItem(DEFAULT_FIELD_OXIGEN_SATURATION_AVERAGE.doubleValue())))
            .andExpect(jsonPath("$.[*].fieldOxigenSaturationMax").value(hasItem(DEFAULT_FIELD_OXIGEN_SATURATION_MAX.doubleValue())))
            .andExpect(jsonPath("$.[*].fieldOxigenSaturationMin").value(hasItem(DEFAULT_FIELD_OXIGEN_SATURATION_MIN.doubleValue())))
            .andExpect(
                jsonPath("$.[*].fieldSuplementalOxigenFlowRateAverage")
                    .value(hasItem(DEFAULT_FIELD_SUPLEMENTAL_OXIGEN_FLOW_RATE_AVERAGE.doubleValue()))
            )
            .andExpect(
                jsonPath("$.[*].fieldSuplementalOxigenFlowRateMax")
                    .value(hasItem(DEFAULT_FIELD_SUPLEMENTAL_OXIGEN_FLOW_RATE_MAX.doubleValue()))
            )
            .andExpect(
                jsonPath("$.[*].fieldSuplementalOxigenFlowRateMin")
                    .value(hasItem(DEFAULT_FIELD_SUPLEMENTAL_OXIGEN_FLOW_RATE_MIN.doubleValue()))
            )
            .andExpect(
                jsonPath("$.[*].fieldOxigenTherapyAdministrationMode").value(hasItem(DEFAULT_FIELD_OXIGEN_THERAPY_ADMINISTRATION_MODE))
            )
            .andExpect(jsonPath("$.[*].fieldOxigenSaturationMode").value(hasItem(DEFAULT_FIELD_OXIGEN_SATURATION_MODE)))
            .andExpect(
                jsonPath("$.[*].fieldOxigenSaturationMeasurementMethod").value(hasItem(DEFAULT_FIELD_OXIGEN_SATURATION_MEASUREMENT_METHOD))
            )
            .andExpect(jsonPath("$.[*].endTime").value(hasItem(DEFAULT_END_TIME.toString())));

        // Check, that the count call also returns 1
        restOxygenSaturationSummaryMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultOxygenSaturationSummaryShouldNotBeFound(String filter) throws Exception {
        restOxygenSaturationSummaryMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restOxygenSaturationSummaryMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingOxygenSaturationSummary() throws Exception {
        // Get the oxygenSaturationSummary
        restOxygenSaturationSummaryMockMvc.perform(get(ENTITY_API_URL_ID, UUID.randomUUID().toString())).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingOxygenSaturationSummary() throws Exception {
        // Initialize the database
        oxygenSaturationSummaryRepository.saveAndFlush(oxygenSaturationSummary);

        int databaseSizeBeforeUpdate = oxygenSaturationSummaryRepository.findAll().size();

        // Update the oxygenSaturationSummary
        OxygenSaturationSummary updatedOxygenSaturationSummary = oxygenSaturationSummaryRepository
            .findById(oxygenSaturationSummary.getId())
            .get();
        // Disconnect from session so that the updates on updatedOxygenSaturationSummary are not directly saved in db
        em.detach(updatedOxygenSaturationSummary);
        updatedOxygenSaturationSummary
            .usuarioId(UPDATED_USUARIO_ID)
            .empresaId(UPDATED_EMPRESA_ID)
            .fieldOxigenSaturationAverage(UPDATED_FIELD_OXIGEN_SATURATION_AVERAGE)
            .fieldOxigenSaturationMax(UPDATED_FIELD_OXIGEN_SATURATION_MAX)
            .fieldOxigenSaturationMin(UPDATED_FIELD_OXIGEN_SATURATION_MIN)
            .fieldSuplementalOxigenFlowRateAverage(UPDATED_FIELD_SUPLEMENTAL_OXIGEN_FLOW_RATE_AVERAGE)
            .fieldSuplementalOxigenFlowRateMax(UPDATED_FIELD_SUPLEMENTAL_OXIGEN_FLOW_RATE_MAX)
            .fieldSuplementalOxigenFlowRateMin(UPDATED_FIELD_SUPLEMENTAL_OXIGEN_FLOW_RATE_MIN)
            .fieldOxigenTherapyAdministrationMode(UPDATED_FIELD_OXIGEN_THERAPY_ADMINISTRATION_MODE)
            .fieldOxigenSaturationMode(UPDATED_FIELD_OXIGEN_SATURATION_MODE)
            .fieldOxigenSaturationMeasurementMethod(UPDATED_FIELD_OXIGEN_SATURATION_MEASUREMENT_METHOD)
            .endTime(UPDATED_END_TIME);

        restOxygenSaturationSummaryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedOxygenSaturationSummary.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedOxygenSaturationSummary))
            )
            .andExpect(status().isOk());

        // Validate the OxygenSaturationSummary in the database
        List<OxygenSaturationSummary> oxygenSaturationSummaryList = oxygenSaturationSummaryRepository.findAll();
        assertThat(oxygenSaturationSummaryList).hasSize(databaseSizeBeforeUpdate);
        OxygenSaturationSummary testOxygenSaturationSummary = oxygenSaturationSummaryList.get(oxygenSaturationSummaryList.size() - 1);
        assertThat(testOxygenSaturationSummary.getUsuarioId()).isEqualTo(UPDATED_USUARIO_ID);
        assertThat(testOxygenSaturationSummary.getEmpresaId()).isEqualTo(UPDATED_EMPRESA_ID);
        assertThat(testOxygenSaturationSummary.getFieldOxigenSaturationAverage()).isEqualTo(UPDATED_FIELD_OXIGEN_SATURATION_AVERAGE);
        assertThat(testOxygenSaturationSummary.getFieldOxigenSaturationMax()).isEqualTo(UPDATED_FIELD_OXIGEN_SATURATION_MAX);
        assertThat(testOxygenSaturationSummary.getFieldOxigenSaturationMin()).isEqualTo(UPDATED_FIELD_OXIGEN_SATURATION_MIN);
        assertThat(testOxygenSaturationSummary.getFieldSuplementalOxigenFlowRateAverage())
            .isEqualTo(UPDATED_FIELD_SUPLEMENTAL_OXIGEN_FLOW_RATE_AVERAGE);
        assertThat(testOxygenSaturationSummary.getFieldSuplementalOxigenFlowRateMax())
            .isEqualTo(UPDATED_FIELD_SUPLEMENTAL_OXIGEN_FLOW_RATE_MAX);
        assertThat(testOxygenSaturationSummary.getFieldSuplementalOxigenFlowRateMin())
            .isEqualTo(UPDATED_FIELD_SUPLEMENTAL_OXIGEN_FLOW_RATE_MIN);
        assertThat(testOxygenSaturationSummary.getFieldOxigenTherapyAdministrationMode())
            .isEqualTo(UPDATED_FIELD_OXIGEN_THERAPY_ADMINISTRATION_MODE);
        assertThat(testOxygenSaturationSummary.getFieldOxigenSaturationMode()).isEqualTo(UPDATED_FIELD_OXIGEN_SATURATION_MODE);
        assertThat(testOxygenSaturationSummary.getFieldOxigenSaturationMeasurementMethod())
            .isEqualTo(UPDATED_FIELD_OXIGEN_SATURATION_MEASUREMENT_METHOD);
        assertThat(testOxygenSaturationSummary.getEndTime()).isEqualTo(UPDATED_END_TIME);
    }

    @Test
    @Transactional
    void putNonExistingOxygenSaturationSummary() throws Exception {
        int databaseSizeBeforeUpdate = oxygenSaturationSummaryRepository.findAll().size();
        oxygenSaturationSummary.setId(UUID.randomUUID());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOxygenSaturationSummaryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, oxygenSaturationSummary.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(oxygenSaturationSummary))
            )
            .andExpect(status().isBadRequest());

        // Validate the OxygenSaturationSummary in the database
        List<OxygenSaturationSummary> oxygenSaturationSummaryList = oxygenSaturationSummaryRepository.findAll();
        assertThat(oxygenSaturationSummaryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchOxygenSaturationSummary() throws Exception {
        int databaseSizeBeforeUpdate = oxygenSaturationSummaryRepository.findAll().size();
        oxygenSaturationSummary.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOxygenSaturationSummaryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(oxygenSaturationSummary))
            )
            .andExpect(status().isBadRequest());

        // Validate the OxygenSaturationSummary in the database
        List<OxygenSaturationSummary> oxygenSaturationSummaryList = oxygenSaturationSummaryRepository.findAll();
        assertThat(oxygenSaturationSummaryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamOxygenSaturationSummary() throws Exception {
        int databaseSizeBeforeUpdate = oxygenSaturationSummaryRepository.findAll().size();
        oxygenSaturationSummary.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOxygenSaturationSummaryMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(oxygenSaturationSummary))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the OxygenSaturationSummary in the database
        List<OxygenSaturationSummary> oxygenSaturationSummaryList = oxygenSaturationSummaryRepository.findAll();
        assertThat(oxygenSaturationSummaryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateOxygenSaturationSummaryWithPatch() throws Exception {
        // Initialize the database
        oxygenSaturationSummaryRepository.saveAndFlush(oxygenSaturationSummary);

        int databaseSizeBeforeUpdate = oxygenSaturationSummaryRepository.findAll().size();

        // Update the oxygenSaturationSummary using partial update
        OxygenSaturationSummary partialUpdatedOxygenSaturationSummary = new OxygenSaturationSummary();
        partialUpdatedOxygenSaturationSummary.setId(oxygenSaturationSummary.getId());

        partialUpdatedOxygenSaturationSummary
            .usuarioId(UPDATED_USUARIO_ID)
            .fieldOxigenSaturationAverage(UPDATED_FIELD_OXIGEN_SATURATION_AVERAGE)
            .fieldOxigenSaturationMax(UPDATED_FIELD_OXIGEN_SATURATION_MAX)
            .fieldSuplementalOxigenFlowRateMax(UPDATED_FIELD_SUPLEMENTAL_OXIGEN_FLOW_RATE_MAX)
            .fieldOxigenSaturationMeasurementMethod(UPDATED_FIELD_OXIGEN_SATURATION_MEASUREMENT_METHOD);

        restOxygenSaturationSummaryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOxygenSaturationSummary.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOxygenSaturationSummary))
            )
            .andExpect(status().isOk());

        // Validate the OxygenSaturationSummary in the database
        List<OxygenSaturationSummary> oxygenSaturationSummaryList = oxygenSaturationSummaryRepository.findAll();
        assertThat(oxygenSaturationSummaryList).hasSize(databaseSizeBeforeUpdate);
        OxygenSaturationSummary testOxygenSaturationSummary = oxygenSaturationSummaryList.get(oxygenSaturationSummaryList.size() - 1);
        assertThat(testOxygenSaturationSummary.getUsuarioId()).isEqualTo(UPDATED_USUARIO_ID);
        assertThat(testOxygenSaturationSummary.getEmpresaId()).isEqualTo(DEFAULT_EMPRESA_ID);
        assertThat(testOxygenSaturationSummary.getFieldOxigenSaturationAverage()).isEqualTo(UPDATED_FIELD_OXIGEN_SATURATION_AVERAGE);
        assertThat(testOxygenSaturationSummary.getFieldOxigenSaturationMax()).isEqualTo(UPDATED_FIELD_OXIGEN_SATURATION_MAX);
        assertThat(testOxygenSaturationSummary.getFieldOxigenSaturationMin()).isEqualTo(DEFAULT_FIELD_OXIGEN_SATURATION_MIN);
        assertThat(testOxygenSaturationSummary.getFieldSuplementalOxigenFlowRateAverage())
            .isEqualTo(DEFAULT_FIELD_SUPLEMENTAL_OXIGEN_FLOW_RATE_AVERAGE);
        assertThat(testOxygenSaturationSummary.getFieldSuplementalOxigenFlowRateMax())
            .isEqualTo(UPDATED_FIELD_SUPLEMENTAL_OXIGEN_FLOW_RATE_MAX);
        assertThat(testOxygenSaturationSummary.getFieldSuplementalOxigenFlowRateMin())
            .isEqualTo(DEFAULT_FIELD_SUPLEMENTAL_OXIGEN_FLOW_RATE_MIN);
        assertThat(testOxygenSaturationSummary.getFieldOxigenTherapyAdministrationMode())
            .isEqualTo(DEFAULT_FIELD_OXIGEN_THERAPY_ADMINISTRATION_MODE);
        assertThat(testOxygenSaturationSummary.getFieldOxigenSaturationMode()).isEqualTo(DEFAULT_FIELD_OXIGEN_SATURATION_MODE);
        assertThat(testOxygenSaturationSummary.getFieldOxigenSaturationMeasurementMethod())
            .isEqualTo(UPDATED_FIELD_OXIGEN_SATURATION_MEASUREMENT_METHOD);
        assertThat(testOxygenSaturationSummary.getEndTime()).isEqualTo(DEFAULT_END_TIME);
    }

    @Test
    @Transactional
    void fullUpdateOxygenSaturationSummaryWithPatch() throws Exception {
        // Initialize the database
        oxygenSaturationSummaryRepository.saveAndFlush(oxygenSaturationSummary);

        int databaseSizeBeforeUpdate = oxygenSaturationSummaryRepository.findAll().size();

        // Update the oxygenSaturationSummary using partial update
        OxygenSaturationSummary partialUpdatedOxygenSaturationSummary = new OxygenSaturationSummary();
        partialUpdatedOxygenSaturationSummary.setId(oxygenSaturationSummary.getId());

        partialUpdatedOxygenSaturationSummary
            .usuarioId(UPDATED_USUARIO_ID)
            .empresaId(UPDATED_EMPRESA_ID)
            .fieldOxigenSaturationAverage(UPDATED_FIELD_OXIGEN_SATURATION_AVERAGE)
            .fieldOxigenSaturationMax(UPDATED_FIELD_OXIGEN_SATURATION_MAX)
            .fieldOxigenSaturationMin(UPDATED_FIELD_OXIGEN_SATURATION_MIN)
            .fieldSuplementalOxigenFlowRateAverage(UPDATED_FIELD_SUPLEMENTAL_OXIGEN_FLOW_RATE_AVERAGE)
            .fieldSuplementalOxigenFlowRateMax(UPDATED_FIELD_SUPLEMENTAL_OXIGEN_FLOW_RATE_MAX)
            .fieldSuplementalOxigenFlowRateMin(UPDATED_FIELD_SUPLEMENTAL_OXIGEN_FLOW_RATE_MIN)
            .fieldOxigenTherapyAdministrationMode(UPDATED_FIELD_OXIGEN_THERAPY_ADMINISTRATION_MODE)
            .fieldOxigenSaturationMode(UPDATED_FIELD_OXIGEN_SATURATION_MODE)
            .fieldOxigenSaturationMeasurementMethod(UPDATED_FIELD_OXIGEN_SATURATION_MEASUREMENT_METHOD)
            .endTime(UPDATED_END_TIME);

        restOxygenSaturationSummaryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOxygenSaturationSummary.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOxygenSaturationSummary))
            )
            .andExpect(status().isOk());

        // Validate the OxygenSaturationSummary in the database
        List<OxygenSaturationSummary> oxygenSaturationSummaryList = oxygenSaturationSummaryRepository.findAll();
        assertThat(oxygenSaturationSummaryList).hasSize(databaseSizeBeforeUpdate);
        OxygenSaturationSummary testOxygenSaturationSummary = oxygenSaturationSummaryList.get(oxygenSaturationSummaryList.size() - 1);
        assertThat(testOxygenSaturationSummary.getUsuarioId()).isEqualTo(UPDATED_USUARIO_ID);
        assertThat(testOxygenSaturationSummary.getEmpresaId()).isEqualTo(UPDATED_EMPRESA_ID);
        assertThat(testOxygenSaturationSummary.getFieldOxigenSaturationAverage()).isEqualTo(UPDATED_FIELD_OXIGEN_SATURATION_AVERAGE);
        assertThat(testOxygenSaturationSummary.getFieldOxigenSaturationMax()).isEqualTo(UPDATED_FIELD_OXIGEN_SATURATION_MAX);
        assertThat(testOxygenSaturationSummary.getFieldOxigenSaturationMin()).isEqualTo(UPDATED_FIELD_OXIGEN_SATURATION_MIN);
        assertThat(testOxygenSaturationSummary.getFieldSuplementalOxigenFlowRateAverage())
            .isEqualTo(UPDATED_FIELD_SUPLEMENTAL_OXIGEN_FLOW_RATE_AVERAGE);
        assertThat(testOxygenSaturationSummary.getFieldSuplementalOxigenFlowRateMax())
            .isEqualTo(UPDATED_FIELD_SUPLEMENTAL_OXIGEN_FLOW_RATE_MAX);
        assertThat(testOxygenSaturationSummary.getFieldSuplementalOxigenFlowRateMin())
            .isEqualTo(UPDATED_FIELD_SUPLEMENTAL_OXIGEN_FLOW_RATE_MIN);
        assertThat(testOxygenSaturationSummary.getFieldOxigenTherapyAdministrationMode())
            .isEqualTo(UPDATED_FIELD_OXIGEN_THERAPY_ADMINISTRATION_MODE);
        assertThat(testOxygenSaturationSummary.getFieldOxigenSaturationMode()).isEqualTo(UPDATED_FIELD_OXIGEN_SATURATION_MODE);
        assertThat(testOxygenSaturationSummary.getFieldOxigenSaturationMeasurementMethod())
            .isEqualTo(UPDATED_FIELD_OXIGEN_SATURATION_MEASUREMENT_METHOD);
        assertThat(testOxygenSaturationSummary.getEndTime()).isEqualTo(UPDATED_END_TIME);
    }

    @Test
    @Transactional
    void patchNonExistingOxygenSaturationSummary() throws Exception {
        int databaseSizeBeforeUpdate = oxygenSaturationSummaryRepository.findAll().size();
        oxygenSaturationSummary.setId(UUID.randomUUID());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOxygenSaturationSummaryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, oxygenSaturationSummary.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(oxygenSaturationSummary))
            )
            .andExpect(status().isBadRequest());

        // Validate the OxygenSaturationSummary in the database
        List<OxygenSaturationSummary> oxygenSaturationSummaryList = oxygenSaturationSummaryRepository.findAll();
        assertThat(oxygenSaturationSummaryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchOxygenSaturationSummary() throws Exception {
        int databaseSizeBeforeUpdate = oxygenSaturationSummaryRepository.findAll().size();
        oxygenSaturationSummary.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOxygenSaturationSummaryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(oxygenSaturationSummary))
            )
            .andExpect(status().isBadRequest());

        // Validate the OxygenSaturationSummary in the database
        List<OxygenSaturationSummary> oxygenSaturationSummaryList = oxygenSaturationSummaryRepository.findAll();
        assertThat(oxygenSaturationSummaryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamOxygenSaturationSummary() throws Exception {
        int databaseSizeBeforeUpdate = oxygenSaturationSummaryRepository.findAll().size();
        oxygenSaturationSummary.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOxygenSaturationSummaryMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(oxygenSaturationSummary))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the OxygenSaturationSummary in the database
        List<OxygenSaturationSummary> oxygenSaturationSummaryList = oxygenSaturationSummaryRepository.findAll();
        assertThat(oxygenSaturationSummaryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteOxygenSaturationSummary() throws Exception {
        // Initialize the database
        oxygenSaturationSummaryRepository.saveAndFlush(oxygenSaturationSummary);

        int databaseSizeBeforeDelete = oxygenSaturationSummaryRepository.findAll().size();

        // Delete the oxygenSaturationSummary
        restOxygenSaturationSummaryMockMvc
            .perform(delete(ENTITY_API_URL_ID, oxygenSaturationSummary.getId().toString()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<OxygenSaturationSummary> oxygenSaturationSummaryList = oxygenSaturationSummaryRepository.findAll();
        assertThat(oxygenSaturationSummaryList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
