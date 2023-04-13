package com.be4tech.b4carecollect.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.be4tech.b4carecollect.IntegrationTest;
import com.be4tech.b4carecollect.domain.OxygenSaturation;
import com.be4tech.b4carecollect.repository.OxygenSaturationRepository;
import com.be4tech.b4carecollect.service.criteria.OxygenSaturationCriteria;
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
 * Integration tests for the {@link OxygenSaturationResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class OxygenSaturationResourceIT {

    private static final String DEFAULT_USUARIO_ID = "AAAAAAAAAA";
    private static final String UPDATED_USUARIO_ID = "BBBBBBBBBB";

    private static final String DEFAULT_EMPRESA_ID = "AAAAAAAAAA";
    private static final String UPDATED_EMPRESA_ID = "BBBBBBBBBB";

    private static final Float DEFAULT_FIELD_OXIGEN_SATURATION = 1F;
    private static final Float UPDATED_FIELD_OXIGEN_SATURATION = 2F;
    private static final Float SMALLER_FIELD_OXIGEN_SATURATION = 1F - 1F;

    private static final Float DEFAULT_FIELD_SUPLEMENTAL_OXIGEN_FLOW_RATE = 1F;
    private static final Float UPDATED_FIELD_SUPLEMENTAL_OXIGEN_FLOW_RATE = 2F;
    private static final Float SMALLER_FIELD_SUPLEMENTAL_OXIGEN_FLOW_RATE = 1F - 1F;

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

    private static final String ENTITY_API_URL = "/api/oxygen-saturations";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private OxygenSaturationRepository oxygenSaturationRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOxygenSaturationMockMvc;

    private OxygenSaturation oxygenSaturation;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OxygenSaturation createEntity(EntityManager em) {
        OxygenSaturation oxygenSaturation = new OxygenSaturation()
            .usuarioId(DEFAULT_USUARIO_ID)
            .empresaId(DEFAULT_EMPRESA_ID)
            .fieldOxigenSaturation(DEFAULT_FIELD_OXIGEN_SATURATION)
            .fieldSuplementalOxigenFlowRate(DEFAULT_FIELD_SUPLEMENTAL_OXIGEN_FLOW_RATE)
            .fieldOxigenTherapyAdministrationMode(DEFAULT_FIELD_OXIGEN_THERAPY_ADMINISTRATION_MODE)
            .fieldOxigenSaturationMode(DEFAULT_FIELD_OXIGEN_SATURATION_MODE)
            .fieldOxigenSaturationMeasurementMethod(DEFAULT_FIELD_OXIGEN_SATURATION_MEASUREMENT_METHOD)
            .endTime(DEFAULT_END_TIME);
        return oxygenSaturation;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OxygenSaturation createUpdatedEntity(EntityManager em) {
        OxygenSaturation oxygenSaturation = new OxygenSaturation()
            .usuarioId(UPDATED_USUARIO_ID)
            .empresaId(UPDATED_EMPRESA_ID)
            .fieldOxigenSaturation(UPDATED_FIELD_OXIGEN_SATURATION)
            .fieldSuplementalOxigenFlowRate(UPDATED_FIELD_SUPLEMENTAL_OXIGEN_FLOW_RATE)
            .fieldOxigenTherapyAdministrationMode(UPDATED_FIELD_OXIGEN_THERAPY_ADMINISTRATION_MODE)
            .fieldOxigenSaturationMode(UPDATED_FIELD_OXIGEN_SATURATION_MODE)
            .fieldOxigenSaturationMeasurementMethod(UPDATED_FIELD_OXIGEN_SATURATION_MEASUREMENT_METHOD)
            .endTime(UPDATED_END_TIME);
        return oxygenSaturation;
    }

    @BeforeEach
    public void initTest() {
        oxygenSaturation = createEntity(em);
    }

    @Test
    @Transactional
    void createOxygenSaturation() throws Exception {
        int databaseSizeBeforeCreate = oxygenSaturationRepository.findAll().size();
        // Create the OxygenSaturation
        restOxygenSaturationMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(oxygenSaturation))
            )
            .andExpect(status().isCreated());

        // Validate the OxygenSaturation in the database
        List<OxygenSaturation> oxygenSaturationList = oxygenSaturationRepository.findAll();
        assertThat(oxygenSaturationList).hasSize(databaseSizeBeforeCreate + 1);
        OxygenSaturation testOxygenSaturation = oxygenSaturationList.get(oxygenSaturationList.size() - 1);
        assertThat(testOxygenSaturation.getUsuarioId()).isEqualTo(DEFAULT_USUARIO_ID);
        assertThat(testOxygenSaturation.getEmpresaId()).isEqualTo(DEFAULT_EMPRESA_ID);
        assertThat(testOxygenSaturation.getFieldOxigenSaturation()).isEqualTo(DEFAULT_FIELD_OXIGEN_SATURATION);
        assertThat(testOxygenSaturation.getFieldSuplementalOxigenFlowRate()).isEqualTo(DEFAULT_FIELD_SUPLEMENTAL_OXIGEN_FLOW_RATE);
        assertThat(testOxygenSaturation.getFieldOxigenTherapyAdministrationMode())
            .isEqualTo(DEFAULT_FIELD_OXIGEN_THERAPY_ADMINISTRATION_MODE);
        assertThat(testOxygenSaturation.getFieldOxigenSaturationMode()).isEqualTo(DEFAULT_FIELD_OXIGEN_SATURATION_MODE);
        assertThat(testOxygenSaturation.getFieldOxigenSaturationMeasurementMethod())
            .isEqualTo(DEFAULT_FIELD_OXIGEN_SATURATION_MEASUREMENT_METHOD);
        assertThat(testOxygenSaturation.getEndTime()).isEqualTo(DEFAULT_END_TIME);
    }

    @Test
    @Transactional
    void createOxygenSaturationWithExistingId() throws Exception {
        // Create the OxygenSaturation with an existing ID
        oxygenSaturationRepository.saveAndFlush(oxygenSaturation);

        int databaseSizeBeforeCreate = oxygenSaturationRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restOxygenSaturationMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(oxygenSaturation))
            )
            .andExpect(status().isBadRequest());

        // Validate the OxygenSaturation in the database
        List<OxygenSaturation> oxygenSaturationList = oxygenSaturationRepository.findAll();
        assertThat(oxygenSaturationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllOxygenSaturations() throws Exception {
        // Initialize the database
        oxygenSaturationRepository.saveAndFlush(oxygenSaturation);

        // Get all the oxygenSaturationList
        restOxygenSaturationMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(oxygenSaturation.getId().toString())))
            .andExpect(jsonPath("$.[*].usuarioId").value(hasItem(DEFAULT_USUARIO_ID)))
            .andExpect(jsonPath("$.[*].empresaId").value(hasItem(DEFAULT_EMPRESA_ID)))
            .andExpect(jsonPath("$.[*].fieldOxigenSaturation").value(hasItem(DEFAULT_FIELD_OXIGEN_SATURATION.doubleValue())))
            .andExpect(
                jsonPath("$.[*].fieldSuplementalOxigenFlowRate").value(hasItem(DEFAULT_FIELD_SUPLEMENTAL_OXIGEN_FLOW_RATE.doubleValue()))
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
    void getOxygenSaturation() throws Exception {
        // Initialize the database
        oxygenSaturationRepository.saveAndFlush(oxygenSaturation);

        // Get the oxygenSaturation
        restOxygenSaturationMockMvc
            .perform(get(ENTITY_API_URL_ID, oxygenSaturation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(oxygenSaturation.getId().toString()))
            .andExpect(jsonPath("$.usuarioId").value(DEFAULT_USUARIO_ID))
            .andExpect(jsonPath("$.empresaId").value(DEFAULT_EMPRESA_ID))
            .andExpect(jsonPath("$.fieldOxigenSaturation").value(DEFAULT_FIELD_OXIGEN_SATURATION.doubleValue()))
            .andExpect(jsonPath("$.fieldSuplementalOxigenFlowRate").value(DEFAULT_FIELD_SUPLEMENTAL_OXIGEN_FLOW_RATE.doubleValue()))
            .andExpect(jsonPath("$.fieldOxigenTherapyAdministrationMode").value(DEFAULT_FIELD_OXIGEN_THERAPY_ADMINISTRATION_MODE))
            .andExpect(jsonPath("$.fieldOxigenSaturationMode").value(DEFAULT_FIELD_OXIGEN_SATURATION_MODE))
            .andExpect(jsonPath("$.fieldOxigenSaturationMeasurementMethod").value(DEFAULT_FIELD_OXIGEN_SATURATION_MEASUREMENT_METHOD))
            .andExpect(jsonPath("$.endTime").value(DEFAULT_END_TIME.toString()));
    }

    @Test
    @Transactional
    void getOxygenSaturationsByIdFiltering() throws Exception {
        // Initialize the database
        oxygenSaturationRepository.saveAndFlush(oxygenSaturation);

        UUID id = oxygenSaturation.getId();

        defaultOxygenSaturationShouldBeFound("id.equals=" + id);
        defaultOxygenSaturationShouldNotBeFound("id.notEquals=" + id);
    }

    @Test
    @Transactional
    void getAllOxygenSaturationsByUsuarioIdIsEqualToSomething() throws Exception {
        // Initialize the database
        oxygenSaturationRepository.saveAndFlush(oxygenSaturation);

        // Get all the oxygenSaturationList where usuarioId equals to DEFAULT_USUARIO_ID
        defaultOxygenSaturationShouldBeFound("usuarioId.equals=" + DEFAULT_USUARIO_ID);

        // Get all the oxygenSaturationList where usuarioId equals to UPDATED_USUARIO_ID
        defaultOxygenSaturationShouldNotBeFound("usuarioId.equals=" + UPDATED_USUARIO_ID);
    }

    @Test
    @Transactional
    void getAllOxygenSaturationsByUsuarioIdIsInShouldWork() throws Exception {
        // Initialize the database
        oxygenSaturationRepository.saveAndFlush(oxygenSaturation);

        // Get all the oxygenSaturationList where usuarioId in DEFAULT_USUARIO_ID or UPDATED_USUARIO_ID
        defaultOxygenSaturationShouldBeFound("usuarioId.in=" + DEFAULT_USUARIO_ID + "," + UPDATED_USUARIO_ID);

        // Get all the oxygenSaturationList where usuarioId equals to UPDATED_USUARIO_ID
        defaultOxygenSaturationShouldNotBeFound("usuarioId.in=" + UPDATED_USUARIO_ID);
    }

    @Test
    @Transactional
    void getAllOxygenSaturationsByUsuarioIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        oxygenSaturationRepository.saveAndFlush(oxygenSaturation);

        // Get all the oxygenSaturationList where usuarioId is not null
        defaultOxygenSaturationShouldBeFound("usuarioId.specified=true");

        // Get all the oxygenSaturationList where usuarioId is null
        defaultOxygenSaturationShouldNotBeFound("usuarioId.specified=false");
    }

    @Test
    @Transactional
    void getAllOxygenSaturationsByUsuarioIdContainsSomething() throws Exception {
        // Initialize the database
        oxygenSaturationRepository.saveAndFlush(oxygenSaturation);

        // Get all the oxygenSaturationList where usuarioId contains DEFAULT_USUARIO_ID
        defaultOxygenSaturationShouldBeFound("usuarioId.contains=" + DEFAULT_USUARIO_ID);

        // Get all the oxygenSaturationList where usuarioId contains UPDATED_USUARIO_ID
        defaultOxygenSaturationShouldNotBeFound("usuarioId.contains=" + UPDATED_USUARIO_ID);
    }

    @Test
    @Transactional
    void getAllOxygenSaturationsByUsuarioIdNotContainsSomething() throws Exception {
        // Initialize the database
        oxygenSaturationRepository.saveAndFlush(oxygenSaturation);

        // Get all the oxygenSaturationList where usuarioId does not contain DEFAULT_USUARIO_ID
        defaultOxygenSaturationShouldNotBeFound("usuarioId.doesNotContain=" + DEFAULT_USUARIO_ID);

        // Get all the oxygenSaturationList where usuarioId does not contain UPDATED_USUARIO_ID
        defaultOxygenSaturationShouldBeFound("usuarioId.doesNotContain=" + UPDATED_USUARIO_ID);
    }

    @Test
    @Transactional
    void getAllOxygenSaturationsByEmpresaIdIsEqualToSomething() throws Exception {
        // Initialize the database
        oxygenSaturationRepository.saveAndFlush(oxygenSaturation);

        // Get all the oxygenSaturationList where empresaId equals to DEFAULT_EMPRESA_ID
        defaultOxygenSaturationShouldBeFound("empresaId.equals=" + DEFAULT_EMPRESA_ID);

        // Get all the oxygenSaturationList where empresaId equals to UPDATED_EMPRESA_ID
        defaultOxygenSaturationShouldNotBeFound("empresaId.equals=" + UPDATED_EMPRESA_ID);
    }

    @Test
    @Transactional
    void getAllOxygenSaturationsByEmpresaIdIsInShouldWork() throws Exception {
        // Initialize the database
        oxygenSaturationRepository.saveAndFlush(oxygenSaturation);

        // Get all the oxygenSaturationList where empresaId in DEFAULT_EMPRESA_ID or UPDATED_EMPRESA_ID
        defaultOxygenSaturationShouldBeFound("empresaId.in=" + DEFAULT_EMPRESA_ID + "," + UPDATED_EMPRESA_ID);

        // Get all the oxygenSaturationList where empresaId equals to UPDATED_EMPRESA_ID
        defaultOxygenSaturationShouldNotBeFound("empresaId.in=" + UPDATED_EMPRESA_ID);
    }

    @Test
    @Transactional
    void getAllOxygenSaturationsByEmpresaIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        oxygenSaturationRepository.saveAndFlush(oxygenSaturation);

        // Get all the oxygenSaturationList where empresaId is not null
        defaultOxygenSaturationShouldBeFound("empresaId.specified=true");

        // Get all the oxygenSaturationList where empresaId is null
        defaultOxygenSaturationShouldNotBeFound("empresaId.specified=false");
    }

    @Test
    @Transactional
    void getAllOxygenSaturationsByEmpresaIdContainsSomething() throws Exception {
        // Initialize the database
        oxygenSaturationRepository.saveAndFlush(oxygenSaturation);

        // Get all the oxygenSaturationList where empresaId contains DEFAULT_EMPRESA_ID
        defaultOxygenSaturationShouldBeFound("empresaId.contains=" + DEFAULT_EMPRESA_ID);

        // Get all the oxygenSaturationList where empresaId contains UPDATED_EMPRESA_ID
        defaultOxygenSaturationShouldNotBeFound("empresaId.contains=" + UPDATED_EMPRESA_ID);
    }

    @Test
    @Transactional
    void getAllOxygenSaturationsByEmpresaIdNotContainsSomething() throws Exception {
        // Initialize the database
        oxygenSaturationRepository.saveAndFlush(oxygenSaturation);

        // Get all the oxygenSaturationList where empresaId does not contain DEFAULT_EMPRESA_ID
        defaultOxygenSaturationShouldNotBeFound("empresaId.doesNotContain=" + DEFAULT_EMPRESA_ID);

        // Get all the oxygenSaturationList where empresaId does not contain UPDATED_EMPRESA_ID
        defaultOxygenSaturationShouldBeFound("empresaId.doesNotContain=" + UPDATED_EMPRESA_ID);
    }

    @Test
    @Transactional
    void getAllOxygenSaturationsByFieldOxigenSaturationIsEqualToSomething() throws Exception {
        // Initialize the database
        oxygenSaturationRepository.saveAndFlush(oxygenSaturation);

        // Get all the oxygenSaturationList where fieldOxigenSaturation equals to DEFAULT_FIELD_OXIGEN_SATURATION
        defaultOxygenSaturationShouldBeFound("fieldOxigenSaturation.equals=" + DEFAULT_FIELD_OXIGEN_SATURATION);

        // Get all the oxygenSaturationList where fieldOxigenSaturation equals to UPDATED_FIELD_OXIGEN_SATURATION
        defaultOxygenSaturationShouldNotBeFound("fieldOxigenSaturation.equals=" + UPDATED_FIELD_OXIGEN_SATURATION);
    }

    @Test
    @Transactional
    void getAllOxygenSaturationsByFieldOxigenSaturationIsInShouldWork() throws Exception {
        // Initialize the database
        oxygenSaturationRepository.saveAndFlush(oxygenSaturation);

        // Get all the oxygenSaturationList where fieldOxigenSaturation in DEFAULT_FIELD_OXIGEN_SATURATION or UPDATED_FIELD_OXIGEN_SATURATION
        defaultOxygenSaturationShouldBeFound(
            "fieldOxigenSaturation.in=" + DEFAULT_FIELD_OXIGEN_SATURATION + "," + UPDATED_FIELD_OXIGEN_SATURATION
        );

        // Get all the oxygenSaturationList where fieldOxigenSaturation equals to UPDATED_FIELD_OXIGEN_SATURATION
        defaultOxygenSaturationShouldNotBeFound("fieldOxigenSaturation.in=" + UPDATED_FIELD_OXIGEN_SATURATION);
    }

    @Test
    @Transactional
    void getAllOxygenSaturationsByFieldOxigenSaturationIsNullOrNotNull() throws Exception {
        // Initialize the database
        oxygenSaturationRepository.saveAndFlush(oxygenSaturation);

        // Get all the oxygenSaturationList where fieldOxigenSaturation is not null
        defaultOxygenSaturationShouldBeFound("fieldOxigenSaturation.specified=true");

        // Get all the oxygenSaturationList where fieldOxigenSaturation is null
        defaultOxygenSaturationShouldNotBeFound("fieldOxigenSaturation.specified=false");
    }

    @Test
    @Transactional
    void getAllOxygenSaturationsByFieldOxigenSaturationIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        oxygenSaturationRepository.saveAndFlush(oxygenSaturation);

        // Get all the oxygenSaturationList where fieldOxigenSaturation is greater than or equal to DEFAULT_FIELD_OXIGEN_SATURATION
        defaultOxygenSaturationShouldBeFound("fieldOxigenSaturation.greaterThanOrEqual=" + DEFAULT_FIELD_OXIGEN_SATURATION);

        // Get all the oxygenSaturationList where fieldOxigenSaturation is greater than or equal to UPDATED_FIELD_OXIGEN_SATURATION
        defaultOxygenSaturationShouldNotBeFound("fieldOxigenSaturation.greaterThanOrEqual=" + UPDATED_FIELD_OXIGEN_SATURATION);
    }

    @Test
    @Transactional
    void getAllOxygenSaturationsByFieldOxigenSaturationIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        oxygenSaturationRepository.saveAndFlush(oxygenSaturation);

        // Get all the oxygenSaturationList where fieldOxigenSaturation is less than or equal to DEFAULT_FIELD_OXIGEN_SATURATION
        defaultOxygenSaturationShouldBeFound("fieldOxigenSaturation.lessThanOrEqual=" + DEFAULT_FIELD_OXIGEN_SATURATION);

        // Get all the oxygenSaturationList where fieldOxigenSaturation is less than or equal to SMALLER_FIELD_OXIGEN_SATURATION
        defaultOxygenSaturationShouldNotBeFound("fieldOxigenSaturation.lessThanOrEqual=" + SMALLER_FIELD_OXIGEN_SATURATION);
    }

    @Test
    @Transactional
    void getAllOxygenSaturationsByFieldOxigenSaturationIsLessThanSomething() throws Exception {
        // Initialize the database
        oxygenSaturationRepository.saveAndFlush(oxygenSaturation);

        // Get all the oxygenSaturationList where fieldOxigenSaturation is less than DEFAULT_FIELD_OXIGEN_SATURATION
        defaultOxygenSaturationShouldNotBeFound("fieldOxigenSaturation.lessThan=" + DEFAULT_FIELD_OXIGEN_SATURATION);

        // Get all the oxygenSaturationList where fieldOxigenSaturation is less than UPDATED_FIELD_OXIGEN_SATURATION
        defaultOxygenSaturationShouldBeFound("fieldOxigenSaturation.lessThan=" + UPDATED_FIELD_OXIGEN_SATURATION);
    }

    @Test
    @Transactional
    void getAllOxygenSaturationsByFieldOxigenSaturationIsGreaterThanSomething() throws Exception {
        // Initialize the database
        oxygenSaturationRepository.saveAndFlush(oxygenSaturation);

        // Get all the oxygenSaturationList where fieldOxigenSaturation is greater than DEFAULT_FIELD_OXIGEN_SATURATION
        defaultOxygenSaturationShouldNotBeFound("fieldOxigenSaturation.greaterThan=" + DEFAULT_FIELD_OXIGEN_SATURATION);

        // Get all the oxygenSaturationList where fieldOxigenSaturation is greater than SMALLER_FIELD_OXIGEN_SATURATION
        defaultOxygenSaturationShouldBeFound("fieldOxigenSaturation.greaterThan=" + SMALLER_FIELD_OXIGEN_SATURATION);
    }

    @Test
    @Transactional
    void getAllOxygenSaturationsByFieldSuplementalOxigenFlowRateIsEqualToSomething() throws Exception {
        // Initialize the database
        oxygenSaturationRepository.saveAndFlush(oxygenSaturation);

        // Get all the oxygenSaturationList where fieldSuplementalOxigenFlowRate equals to DEFAULT_FIELD_SUPLEMENTAL_OXIGEN_FLOW_RATE
        defaultOxygenSaturationShouldBeFound("fieldSuplementalOxigenFlowRate.equals=" + DEFAULT_FIELD_SUPLEMENTAL_OXIGEN_FLOW_RATE);

        // Get all the oxygenSaturationList where fieldSuplementalOxigenFlowRate equals to UPDATED_FIELD_SUPLEMENTAL_OXIGEN_FLOW_RATE
        defaultOxygenSaturationShouldNotBeFound("fieldSuplementalOxigenFlowRate.equals=" + UPDATED_FIELD_SUPLEMENTAL_OXIGEN_FLOW_RATE);
    }

    @Test
    @Transactional
    void getAllOxygenSaturationsByFieldSuplementalOxigenFlowRateIsInShouldWork() throws Exception {
        // Initialize the database
        oxygenSaturationRepository.saveAndFlush(oxygenSaturation);

        // Get all the oxygenSaturationList where fieldSuplementalOxigenFlowRate in DEFAULT_FIELD_SUPLEMENTAL_OXIGEN_FLOW_RATE or UPDATED_FIELD_SUPLEMENTAL_OXIGEN_FLOW_RATE
        defaultOxygenSaturationShouldBeFound(
            "fieldSuplementalOxigenFlowRate.in=" +
            DEFAULT_FIELD_SUPLEMENTAL_OXIGEN_FLOW_RATE +
            "," +
            UPDATED_FIELD_SUPLEMENTAL_OXIGEN_FLOW_RATE
        );

        // Get all the oxygenSaturationList where fieldSuplementalOxigenFlowRate equals to UPDATED_FIELD_SUPLEMENTAL_OXIGEN_FLOW_RATE
        defaultOxygenSaturationShouldNotBeFound("fieldSuplementalOxigenFlowRate.in=" + UPDATED_FIELD_SUPLEMENTAL_OXIGEN_FLOW_RATE);
    }

    @Test
    @Transactional
    void getAllOxygenSaturationsByFieldSuplementalOxigenFlowRateIsNullOrNotNull() throws Exception {
        // Initialize the database
        oxygenSaturationRepository.saveAndFlush(oxygenSaturation);

        // Get all the oxygenSaturationList where fieldSuplementalOxigenFlowRate is not null
        defaultOxygenSaturationShouldBeFound("fieldSuplementalOxigenFlowRate.specified=true");

        // Get all the oxygenSaturationList where fieldSuplementalOxigenFlowRate is null
        defaultOxygenSaturationShouldNotBeFound("fieldSuplementalOxigenFlowRate.specified=false");
    }

    @Test
    @Transactional
    void getAllOxygenSaturationsByFieldSuplementalOxigenFlowRateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        oxygenSaturationRepository.saveAndFlush(oxygenSaturation);

        // Get all the oxygenSaturationList where fieldSuplementalOxigenFlowRate is greater than or equal to DEFAULT_FIELD_SUPLEMENTAL_OXIGEN_FLOW_RATE
        defaultOxygenSaturationShouldBeFound(
            "fieldSuplementalOxigenFlowRate.greaterThanOrEqual=" + DEFAULT_FIELD_SUPLEMENTAL_OXIGEN_FLOW_RATE
        );

        // Get all the oxygenSaturationList where fieldSuplementalOxigenFlowRate is greater than or equal to UPDATED_FIELD_SUPLEMENTAL_OXIGEN_FLOW_RATE
        defaultOxygenSaturationShouldNotBeFound(
            "fieldSuplementalOxigenFlowRate.greaterThanOrEqual=" + UPDATED_FIELD_SUPLEMENTAL_OXIGEN_FLOW_RATE
        );
    }

    @Test
    @Transactional
    void getAllOxygenSaturationsByFieldSuplementalOxigenFlowRateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        oxygenSaturationRepository.saveAndFlush(oxygenSaturation);

        // Get all the oxygenSaturationList where fieldSuplementalOxigenFlowRate is less than or equal to DEFAULT_FIELD_SUPLEMENTAL_OXIGEN_FLOW_RATE
        defaultOxygenSaturationShouldBeFound(
            "fieldSuplementalOxigenFlowRate.lessThanOrEqual=" + DEFAULT_FIELD_SUPLEMENTAL_OXIGEN_FLOW_RATE
        );

        // Get all the oxygenSaturationList where fieldSuplementalOxigenFlowRate is less than or equal to SMALLER_FIELD_SUPLEMENTAL_OXIGEN_FLOW_RATE
        defaultOxygenSaturationShouldNotBeFound(
            "fieldSuplementalOxigenFlowRate.lessThanOrEqual=" + SMALLER_FIELD_SUPLEMENTAL_OXIGEN_FLOW_RATE
        );
    }

    @Test
    @Transactional
    void getAllOxygenSaturationsByFieldSuplementalOxigenFlowRateIsLessThanSomething() throws Exception {
        // Initialize the database
        oxygenSaturationRepository.saveAndFlush(oxygenSaturation);

        // Get all the oxygenSaturationList where fieldSuplementalOxigenFlowRate is less than DEFAULT_FIELD_SUPLEMENTAL_OXIGEN_FLOW_RATE
        defaultOxygenSaturationShouldNotBeFound("fieldSuplementalOxigenFlowRate.lessThan=" + DEFAULT_FIELD_SUPLEMENTAL_OXIGEN_FLOW_RATE);

        // Get all the oxygenSaturationList where fieldSuplementalOxigenFlowRate is less than UPDATED_FIELD_SUPLEMENTAL_OXIGEN_FLOW_RATE
        defaultOxygenSaturationShouldBeFound("fieldSuplementalOxigenFlowRate.lessThan=" + UPDATED_FIELD_SUPLEMENTAL_OXIGEN_FLOW_RATE);
    }

    @Test
    @Transactional
    void getAllOxygenSaturationsByFieldSuplementalOxigenFlowRateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        oxygenSaturationRepository.saveAndFlush(oxygenSaturation);

        // Get all the oxygenSaturationList where fieldSuplementalOxigenFlowRate is greater than DEFAULT_FIELD_SUPLEMENTAL_OXIGEN_FLOW_RATE
        defaultOxygenSaturationShouldNotBeFound("fieldSuplementalOxigenFlowRate.greaterThan=" + DEFAULT_FIELD_SUPLEMENTAL_OXIGEN_FLOW_RATE);

        // Get all the oxygenSaturationList where fieldSuplementalOxigenFlowRate is greater than SMALLER_FIELD_SUPLEMENTAL_OXIGEN_FLOW_RATE
        defaultOxygenSaturationShouldBeFound("fieldSuplementalOxigenFlowRate.greaterThan=" + SMALLER_FIELD_SUPLEMENTAL_OXIGEN_FLOW_RATE);
    }

    @Test
    @Transactional
    void getAllOxygenSaturationsByFieldOxigenTherapyAdministrationModeIsEqualToSomething() throws Exception {
        // Initialize the database
        oxygenSaturationRepository.saveAndFlush(oxygenSaturation);

        // Get all the oxygenSaturationList where fieldOxigenTherapyAdministrationMode equals to DEFAULT_FIELD_OXIGEN_THERAPY_ADMINISTRATION_MODE
        defaultOxygenSaturationShouldBeFound(
            "fieldOxigenTherapyAdministrationMode.equals=" + DEFAULT_FIELD_OXIGEN_THERAPY_ADMINISTRATION_MODE
        );

        // Get all the oxygenSaturationList where fieldOxigenTherapyAdministrationMode equals to UPDATED_FIELD_OXIGEN_THERAPY_ADMINISTRATION_MODE
        defaultOxygenSaturationShouldNotBeFound(
            "fieldOxigenTherapyAdministrationMode.equals=" + UPDATED_FIELD_OXIGEN_THERAPY_ADMINISTRATION_MODE
        );
    }

    @Test
    @Transactional
    void getAllOxygenSaturationsByFieldOxigenTherapyAdministrationModeIsInShouldWork() throws Exception {
        // Initialize the database
        oxygenSaturationRepository.saveAndFlush(oxygenSaturation);

        // Get all the oxygenSaturationList where fieldOxigenTherapyAdministrationMode in DEFAULT_FIELD_OXIGEN_THERAPY_ADMINISTRATION_MODE or UPDATED_FIELD_OXIGEN_THERAPY_ADMINISTRATION_MODE
        defaultOxygenSaturationShouldBeFound(
            "fieldOxigenTherapyAdministrationMode.in=" +
            DEFAULT_FIELD_OXIGEN_THERAPY_ADMINISTRATION_MODE +
            "," +
            UPDATED_FIELD_OXIGEN_THERAPY_ADMINISTRATION_MODE
        );

        // Get all the oxygenSaturationList where fieldOxigenTherapyAdministrationMode equals to UPDATED_FIELD_OXIGEN_THERAPY_ADMINISTRATION_MODE
        defaultOxygenSaturationShouldNotBeFound(
            "fieldOxigenTherapyAdministrationMode.in=" + UPDATED_FIELD_OXIGEN_THERAPY_ADMINISTRATION_MODE
        );
    }

    @Test
    @Transactional
    void getAllOxygenSaturationsByFieldOxigenTherapyAdministrationModeIsNullOrNotNull() throws Exception {
        // Initialize the database
        oxygenSaturationRepository.saveAndFlush(oxygenSaturation);

        // Get all the oxygenSaturationList where fieldOxigenTherapyAdministrationMode is not null
        defaultOxygenSaturationShouldBeFound("fieldOxigenTherapyAdministrationMode.specified=true");

        // Get all the oxygenSaturationList where fieldOxigenTherapyAdministrationMode is null
        defaultOxygenSaturationShouldNotBeFound("fieldOxigenTherapyAdministrationMode.specified=false");
    }

    @Test
    @Transactional
    void getAllOxygenSaturationsByFieldOxigenTherapyAdministrationModeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        oxygenSaturationRepository.saveAndFlush(oxygenSaturation);

        // Get all the oxygenSaturationList where fieldOxigenTherapyAdministrationMode is greater than or equal to DEFAULT_FIELD_OXIGEN_THERAPY_ADMINISTRATION_MODE
        defaultOxygenSaturationShouldBeFound(
            "fieldOxigenTherapyAdministrationMode.greaterThanOrEqual=" + DEFAULT_FIELD_OXIGEN_THERAPY_ADMINISTRATION_MODE
        );

        // Get all the oxygenSaturationList where fieldOxigenTherapyAdministrationMode is greater than or equal to UPDATED_FIELD_OXIGEN_THERAPY_ADMINISTRATION_MODE
        defaultOxygenSaturationShouldNotBeFound(
            "fieldOxigenTherapyAdministrationMode.greaterThanOrEqual=" + UPDATED_FIELD_OXIGEN_THERAPY_ADMINISTRATION_MODE
        );
    }

    @Test
    @Transactional
    void getAllOxygenSaturationsByFieldOxigenTherapyAdministrationModeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        oxygenSaturationRepository.saveAndFlush(oxygenSaturation);

        // Get all the oxygenSaturationList where fieldOxigenTherapyAdministrationMode is less than or equal to DEFAULT_FIELD_OXIGEN_THERAPY_ADMINISTRATION_MODE
        defaultOxygenSaturationShouldBeFound(
            "fieldOxigenTherapyAdministrationMode.lessThanOrEqual=" + DEFAULT_FIELD_OXIGEN_THERAPY_ADMINISTRATION_MODE
        );

        // Get all the oxygenSaturationList where fieldOxigenTherapyAdministrationMode is less than or equal to SMALLER_FIELD_OXIGEN_THERAPY_ADMINISTRATION_MODE
        defaultOxygenSaturationShouldNotBeFound(
            "fieldOxigenTherapyAdministrationMode.lessThanOrEqual=" + SMALLER_FIELD_OXIGEN_THERAPY_ADMINISTRATION_MODE
        );
    }

    @Test
    @Transactional
    void getAllOxygenSaturationsByFieldOxigenTherapyAdministrationModeIsLessThanSomething() throws Exception {
        // Initialize the database
        oxygenSaturationRepository.saveAndFlush(oxygenSaturation);

        // Get all the oxygenSaturationList where fieldOxigenTherapyAdministrationMode is less than DEFAULT_FIELD_OXIGEN_THERAPY_ADMINISTRATION_MODE
        defaultOxygenSaturationShouldNotBeFound(
            "fieldOxigenTherapyAdministrationMode.lessThan=" + DEFAULT_FIELD_OXIGEN_THERAPY_ADMINISTRATION_MODE
        );

        // Get all the oxygenSaturationList where fieldOxigenTherapyAdministrationMode is less than UPDATED_FIELD_OXIGEN_THERAPY_ADMINISTRATION_MODE
        defaultOxygenSaturationShouldBeFound(
            "fieldOxigenTherapyAdministrationMode.lessThan=" + UPDATED_FIELD_OXIGEN_THERAPY_ADMINISTRATION_MODE
        );
    }

    @Test
    @Transactional
    void getAllOxygenSaturationsByFieldOxigenTherapyAdministrationModeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        oxygenSaturationRepository.saveAndFlush(oxygenSaturation);

        // Get all the oxygenSaturationList where fieldOxigenTherapyAdministrationMode is greater than DEFAULT_FIELD_OXIGEN_THERAPY_ADMINISTRATION_MODE
        defaultOxygenSaturationShouldNotBeFound(
            "fieldOxigenTherapyAdministrationMode.greaterThan=" + DEFAULT_FIELD_OXIGEN_THERAPY_ADMINISTRATION_MODE
        );

        // Get all the oxygenSaturationList where fieldOxigenTherapyAdministrationMode is greater than SMALLER_FIELD_OXIGEN_THERAPY_ADMINISTRATION_MODE
        defaultOxygenSaturationShouldBeFound(
            "fieldOxigenTherapyAdministrationMode.greaterThan=" + SMALLER_FIELD_OXIGEN_THERAPY_ADMINISTRATION_MODE
        );
    }

    @Test
    @Transactional
    void getAllOxygenSaturationsByFieldOxigenSaturationModeIsEqualToSomething() throws Exception {
        // Initialize the database
        oxygenSaturationRepository.saveAndFlush(oxygenSaturation);

        // Get all the oxygenSaturationList where fieldOxigenSaturationMode equals to DEFAULT_FIELD_OXIGEN_SATURATION_MODE
        defaultOxygenSaturationShouldBeFound("fieldOxigenSaturationMode.equals=" + DEFAULT_FIELD_OXIGEN_SATURATION_MODE);

        // Get all the oxygenSaturationList where fieldOxigenSaturationMode equals to UPDATED_FIELD_OXIGEN_SATURATION_MODE
        defaultOxygenSaturationShouldNotBeFound("fieldOxigenSaturationMode.equals=" + UPDATED_FIELD_OXIGEN_SATURATION_MODE);
    }

    @Test
    @Transactional
    void getAllOxygenSaturationsByFieldOxigenSaturationModeIsInShouldWork() throws Exception {
        // Initialize the database
        oxygenSaturationRepository.saveAndFlush(oxygenSaturation);

        // Get all the oxygenSaturationList where fieldOxigenSaturationMode in DEFAULT_FIELD_OXIGEN_SATURATION_MODE or UPDATED_FIELD_OXIGEN_SATURATION_MODE
        defaultOxygenSaturationShouldBeFound(
            "fieldOxigenSaturationMode.in=" + DEFAULT_FIELD_OXIGEN_SATURATION_MODE + "," + UPDATED_FIELD_OXIGEN_SATURATION_MODE
        );

        // Get all the oxygenSaturationList where fieldOxigenSaturationMode equals to UPDATED_FIELD_OXIGEN_SATURATION_MODE
        defaultOxygenSaturationShouldNotBeFound("fieldOxigenSaturationMode.in=" + UPDATED_FIELD_OXIGEN_SATURATION_MODE);
    }

    @Test
    @Transactional
    void getAllOxygenSaturationsByFieldOxigenSaturationModeIsNullOrNotNull() throws Exception {
        // Initialize the database
        oxygenSaturationRepository.saveAndFlush(oxygenSaturation);

        // Get all the oxygenSaturationList where fieldOxigenSaturationMode is not null
        defaultOxygenSaturationShouldBeFound("fieldOxigenSaturationMode.specified=true");

        // Get all the oxygenSaturationList where fieldOxigenSaturationMode is null
        defaultOxygenSaturationShouldNotBeFound("fieldOxigenSaturationMode.specified=false");
    }

    @Test
    @Transactional
    void getAllOxygenSaturationsByFieldOxigenSaturationModeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        oxygenSaturationRepository.saveAndFlush(oxygenSaturation);

        // Get all the oxygenSaturationList where fieldOxigenSaturationMode is greater than or equal to DEFAULT_FIELD_OXIGEN_SATURATION_MODE
        defaultOxygenSaturationShouldBeFound("fieldOxigenSaturationMode.greaterThanOrEqual=" + DEFAULT_FIELD_OXIGEN_SATURATION_MODE);

        // Get all the oxygenSaturationList where fieldOxigenSaturationMode is greater than or equal to UPDATED_FIELD_OXIGEN_SATURATION_MODE
        defaultOxygenSaturationShouldNotBeFound("fieldOxigenSaturationMode.greaterThanOrEqual=" + UPDATED_FIELD_OXIGEN_SATURATION_MODE);
    }

    @Test
    @Transactional
    void getAllOxygenSaturationsByFieldOxigenSaturationModeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        oxygenSaturationRepository.saveAndFlush(oxygenSaturation);

        // Get all the oxygenSaturationList where fieldOxigenSaturationMode is less than or equal to DEFAULT_FIELD_OXIGEN_SATURATION_MODE
        defaultOxygenSaturationShouldBeFound("fieldOxigenSaturationMode.lessThanOrEqual=" + DEFAULT_FIELD_OXIGEN_SATURATION_MODE);

        // Get all the oxygenSaturationList where fieldOxigenSaturationMode is less than or equal to SMALLER_FIELD_OXIGEN_SATURATION_MODE
        defaultOxygenSaturationShouldNotBeFound("fieldOxigenSaturationMode.lessThanOrEqual=" + SMALLER_FIELD_OXIGEN_SATURATION_MODE);
    }

    @Test
    @Transactional
    void getAllOxygenSaturationsByFieldOxigenSaturationModeIsLessThanSomething() throws Exception {
        // Initialize the database
        oxygenSaturationRepository.saveAndFlush(oxygenSaturation);

        // Get all the oxygenSaturationList where fieldOxigenSaturationMode is less than DEFAULT_FIELD_OXIGEN_SATURATION_MODE
        defaultOxygenSaturationShouldNotBeFound("fieldOxigenSaturationMode.lessThan=" + DEFAULT_FIELD_OXIGEN_SATURATION_MODE);

        // Get all the oxygenSaturationList where fieldOxigenSaturationMode is less than UPDATED_FIELD_OXIGEN_SATURATION_MODE
        defaultOxygenSaturationShouldBeFound("fieldOxigenSaturationMode.lessThan=" + UPDATED_FIELD_OXIGEN_SATURATION_MODE);
    }

    @Test
    @Transactional
    void getAllOxygenSaturationsByFieldOxigenSaturationModeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        oxygenSaturationRepository.saveAndFlush(oxygenSaturation);

        // Get all the oxygenSaturationList where fieldOxigenSaturationMode is greater than DEFAULT_FIELD_OXIGEN_SATURATION_MODE
        defaultOxygenSaturationShouldNotBeFound("fieldOxigenSaturationMode.greaterThan=" + DEFAULT_FIELD_OXIGEN_SATURATION_MODE);

        // Get all the oxygenSaturationList where fieldOxigenSaturationMode is greater than SMALLER_FIELD_OXIGEN_SATURATION_MODE
        defaultOxygenSaturationShouldBeFound("fieldOxigenSaturationMode.greaterThan=" + SMALLER_FIELD_OXIGEN_SATURATION_MODE);
    }

    @Test
    @Transactional
    void getAllOxygenSaturationsByFieldOxigenSaturationMeasurementMethodIsEqualToSomething() throws Exception {
        // Initialize the database
        oxygenSaturationRepository.saveAndFlush(oxygenSaturation);

        // Get all the oxygenSaturationList where fieldOxigenSaturationMeasurementMethod equals to DEFAULT_FIELD_OXIGEN_SATURATION_MEASUREMENT_METHOD
        defaultOxygenSaturationShouldBeFound(
            "fieldOxigenSaturationMeasurementMethod.equals=" + DEFAULT_FIELD_OXIGEN_SATURATION_MEASUREMENT_METHOD
        );

        // Get all the oxygenSaturationList where fieldOxigenSaturationMeasurementMethod equals to UPDATED_FIELD_OXIGEN_SATURATION_MEASUREMENT_METHOD
        defaultOxygenSaturationShouldNotBeFound(
            "fieldOxigenSaturationMeasurementMethod.equals=" + UPDATED_FIELD_OXIGEN_SATURATION_MEASUREMENT_METHOD
        );
    }

    @Test
    @Transactional
    void getAllOxygenSaturationsByFieldOxigenSaturationMeasurementMethodIsInShouldWork() throws Exception {
        // Initialize the database
        oxygenSaturationRepository.saveAndFlush(oxygenSaturation);

        // Get all the oxygenSaturationList where fieldOxigenSaturationMeasurementMethod in DEFAULT_FIELD_OXIGEN_SATURATION_MEASUREMENT_METHOD or UPDATED_FIELD_OXIGEN_SATURATION_MEASUREMENT_METHOD
        defaultOxygenSaturationShouldBeFound(
            "fieldOxigenSaturationMeasurementMethod.in=" +
            DEFAULT_FIELD_OXIGEN_SATURATION_MEASUREMENT_METHOD +
            "," +
            UPDATED_FIELD_OXIGEN_SATURATION_MEASUREMENT_METHOD
        );

        // Get all the oxygenSaturationList where fieldOxigenSaturationMeasurementMethod equals to UPDATED_FIELD_OXIGEN_SATURATION_MEASUREMENT_METHOD
        defaultOxygenSaturationShouldNotBeFound(
            "fieldOxigenSaturationMeasurementMethod.in=" + UPDATED_FIELD_OXIGEN_SATURATION_MEASUREMENT_METHOD
        );
    }

    @Test
    @Transactional
    void getAllOxygenSaturationsByFieldOxigenSaturationMeasurementMethodIsNullOrNotNull() throws Exception {
        // Initialize the database
        oxygenSaturationRepository.saveAndFlush(oxygenSaturation);

        // Get all the oxygenSaturationList where fieldOxigenSaturationMeasurementMethod is not null
        defaultOxygenSaturationShouldBeFound("fieldOxigenSaturationMeasurementMethod.specified=true");

        // Get all the oxygenSaturationList where fieldOxigenSaturationMeasurementMethod is null
        defaultOxygenSaturationShouldNotBeFound("fieldOxigenSaturationMeasurementMethod.specified=false");
    }

    @Test
    @Transactional
    void getAllOxygenSaturationsByFieldOxigenSaturationMeasurementMethodIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        oxygenSaturationRepository.saveAndFlush(oxygenSaturation);

        // Get all the oxygenSaturationList where fieldOxigenSaturationMeasurementMethod is greater than or equal to DEFAULT_FIELD_OXIGEN_SATURATION_MEASUREMENT_METHOD
        defaultOxygenSaturationShouldBeFound(
            "fieldOxigenSaturationMeasurementMethod.greaterThanOrEqual=" + DEFAULT_FIELD_OXIGEN_SATURATION_MEASUREMENT_METHOD
        );

        // Get all the oxygenSaturationList where fieldOxigenSaturationMeasurementMethod is greater than or equal to UPDATED_FIELD_OXIGEN_SATURATION_MEASUREMENT_METHOD
        defaultOxygenSaturationShouldNotBeFound(
            "fieldOxigenSaturationMeasurementMethod.greaterThanOrEqual=" + UPDATED_FIELD_OXIGEN_SATURATION_MEASUREMENT_METHOD
        );
    }

    @Test
    @Transactional
    void getAllOxygenSaturationsByFieldOxigenSaturationMeasurementMethodIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        oxygenSaturationRepository.saveAndFlush(oxygenSaturation);

        // Get all the oxygenSaturationList where fieldOxigenSaturationMeasurementMethod is less than or equal to DEFAULT_FIELD_OXIGEN_SATURATION_MEASUREMENT_METHOD
        defaultOxygenSaturationShouldBeFound(
            "fieldOxigenSaturationMeasurementMethod.lessThanOrEqual=" + DEFAULT_FIELD_OXIGEN_SATURATION_MEASUREMENT_METHOD
        );

        // Get all the oxygenSaturationList where fieldOxigenSaturationMeasurementMethod is less than or equal to SMALLER_FIELD_OXIGEN_SATURATION_MEASUREMENT_METHOD
        defaultOxygenSaturationShouldNotBeFound(
            "fieldOxigenSaturationMeasurementMethod.lessThanOrEqual=" + SMALLER_FIELD_OXIGEN_SATURATION_MEASUREMENT_METHOD
        );
    }

    @Test
    @Transactional
    void getAllOxygenSaturationsByFieldOxigenSaturationMeasurementMethodIsLessThanSomething() throws Exception {
        // Initialize the database
        oxygenSaturationRepository.saveAndFlush(oxygenSaturation);

        // Get all the oxygenSaturationList where fieldOxigenSaturationMeasurementMethod is less than DEFAULT_FIELD_OXIGEN_SATURATION_MEASUREMENT_METHOD
        defaultOxygenSaturationShouldNotBeFound(
            "fieldOxigenSaturationMeasurementMethod.lessThan=" + DEFAULT_FIELD_OXIGEN_SATURATION_MEASUREMENT_METHOD
        );

        // Get all the oxygenSaturationList where fieldOxigenSaturationMeasurementMethod is less than UPDATED_FIELD_OXIGEN_SATURATION_MEASUREMENT_METHOD
        defaultOxygenSaturationShouldBeFound(
            "fieldOxigenSaturationMeasurementMethod.lessThan=" + UPDATED_FIELD_OXIGEN_SATURATION_MEASUREMENT_METHOD
        );
    }

    @Test
    @Transactional
    void getAllOxygenSaturationsByFieldOxigenSaturationMeasurementMethodIsGreaterThanSomething() throws Exception {
        // Initialize the database
        oxygenSaturationRepository.saveAndFlush(oxygenSaturation);

        // Get all the oxygenSaturationList where fieldOxigenSaturationMeasurementMethod is greater than DEFAULT_FIELD_OXIGEN_SATURATION_MEASUREMENT_METHOD
        defaultOxygenSaturationShouldNotBeFound(
            "fieldOxigenSaturationMeasurementMethod.greaterThan=" + DEFAULT_FIELD_OXIGEN_SATURATION_MEASUREMENT_METHOD
        );

        // Get all the oxygenSaturationList where fieldOxigenSaturationMeasurementMethod is greater than SMALLER_FIELD_OXIGEN_SATURATION_MEASUREMENT_METHOD
        defaultOxygenSaturationShouldBeFound(
            "fieldOxigenSaturationMeasurementMethod.greaterThan=" + SMALLER_FIELD_OXIGEN_SATURATION_MEASUREMENT_METHOD
        );
    }

    @Test
    @Transactional
    void getAllOxygenSaturationsByEndTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        oxygenSaturationRepository.saveAndFlush(oxygenSaturation);

        // Get all the oxygenSaturationList where endTime equals to DEFAULT_END_TIME
        defaultOxygenSaturationShouldBeFound("endTime.equals=" + DEFAULT_END_TIME);

        // Get all the oxygenSaturationList where endTime equals to UPDATED_END_TIME
        defaultOxygenSaturationShouldNotBeFound("endTime.equals=" + UPDATED_END_TIME);
    }

    @Test
    @Transactional
    void getAllOxygenSaturationsByEndTimeIsInShouldWork() throws Exception {
        // Initialize the database
        oxygenSaturationRepository.saveAndFlush(oxygenSaturation);

        // Get all the oxygenSaturationList where endTime in DEFAULT_END_TIME or UPDATED_END_TIME
        defaultOxygenSaturationShouldBeFound("endTime.in=" + DEFAULT_END_TIME + "," + UPDATED_END_TIME);

        // Get all the oxygenSaturationList where endTime equals to UPDATED_END_TIME
        defaultOxygenSaturationShouldNotBeFound("endTime.in=" + UPDATED_END_TIME);
    }

    @Test
    @Transactional
    void getAllOxygenSaturationsByEndTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        oxygenSaturationRepository.saveAndFlush(oxygenSaturation);

        // Get all the oxygenSaturationList where endTime is not null
        defaultOxygenSaturationShouldBeFound("endTime.specified=true");

        // Get all the oxygenSaturationList where endTime is null
        defaultOxygenSaturationShouldNotBeFound("endTime.specified=false");
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultOxygenSaturationShouldBeFound(String filter) throws Exception {
        restOxygenSaturationMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(oxygenSaturation.getId().toString())))
            .andExpect(jsonPath("$.[*].usuarioId").value(hasItem(DEFAULT_USUARIO_ID)))
            .andExpect(jsonPath("$.[*].empresaId").value(hasItem(DEFAULT_EMPRESA_ID)))
            .andExpect(jsonPath("$.[*].fieldOxigenSaturation").value(hasItem(DEFAULT_FIELD_OXIGEN_SATURATION.doubleValue())))
            .andExpect(
                jsonPath("$.[*].fieldSuplementalOxigenFlowRate").value(hasItem(DEFAULT_FIELD_SUPLEMENTAL_OXIGEN_FLOW_RATE.doubleValue()))
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
        restOxygenSaturationMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultOxygenSaturationShouldNotBeFound(String filter) throws Exception {
        restOxygenSaturationMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restOxygenSaturationMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingOxygenSaturation() throws Exception {
        // Get the oxygenSaturation
        restOxygenSaturationMockMvc.perform(get(ENTITY_API_URL_ID, UUID.randomUUID().toString())).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingOxygenSaturation() throws Exception {
        // Initialize the database
        oxygenSaturationRepository.saveAndFlush(oxygenSaturation);

        int databaseSizeBeforeUpdate = oxygenSaturationRepository.findAll().size();

        // Update the oxygenSaturation
        OxygenSaturation updatedOxygenSaturation = oxygenSaturationRepository.findById(oxygenSaturation.getId()).get();
        // Disconnect from session so that the updates on updatedOxygenSaturation are not directly saved in db
        em.detach(updatedOxygenSaturation);
        updatedOxygenSaturation
            .usuarioId(UPDATED_USUARIO_ID)
            .empresaId(UPDATED_EMPRESA_ID)
            .fieldOxigenSaturation(UPDATED_FIELD_OXIGEN_SATURATION)
            .fieldSuplementalOxigenFlowRate(UPDATED_FIELD_SUPLEMENTAL_OXIGEN_FLOW_RATE)
            .fieldOxigenTherapyAdministrationMode(UPDATED_FIELD_OXIGEN_THERAPY_ADMINISTRATION_MODE)
            .fieldOxigenSaturationMode(UPDATED_FIELD_OXIGEN_SATURATION_MODE)
            .fieldOxigenSaturationMeasurementMethod(UPDATED_FIELD_OXIGEN_SATURATION_MEASUREMENT_METHOD)
            .endTime(UPDATED_END_TIME);

        restOxygenSaturationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedOxygenSaturation.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedOxygenSaturation))
            )
            .andExpect(status().isOk());

        // Validate the OxygenSaturation in the database
        List<OxygenSaturation> oxygenSaturationList = oxygenSaturationRepository.findAll();
        assertThat(oxygenSaturationList).hasSize(databaseSizeBeforeUpdate);
        OxygenSaturation testOxygenSaturation = oxygenSaturationList.get(oxygenSaturationList.size() - 1);
        assertThat(testOxygenSaturation.getUsuarioId()).isEqualTo(UPDATED_USUARIO_ID);
        assertThat(testOxygenSaturation.getEmpresaId()).isEqualTo(UPDATED_EMPRESA_ID);
        assertThat(testOxygenSaturation.getFieldOxigenSaturation()).isEqualTo(UPDATED_FIELD_OXIGEN_SATURATION);
        assertThat(testOxygenSaturation.getFieldSuplementalOxigenFlowRate()).isEqualTo(UPDATED_FIELD_SUPLEMENTAL_OXIGEN_FLOW_RATE);
        assertThat(testOxygenSaturation.getFieldOxigenTherapyAdministrationMode())
            .isEqualTo(UPDATED_FIELD_OXIGEN_THERAPY_ADMINISTRATION_MODE);
        assertThat(testOxygenSaturation.getFieldOxigenSaturationMode()).isEqualTo(UPDATED_FIELD_OXIGEN_SATURATION_MODE);
        assertThat(testOxygenSaturation.getFieldOxigenSaturationMeasurementMethod())
            .isEqualTo(UPDATED_FIELD_OXIGEN_SATURATION_MEASUREMENT_METHOD);
        assertThat(testOxygenSaturation.getEndTime()).isEqualTo(UPDATED_END_TIME);
    }

    @Test
    @Transactional
    void putNonExistingOxygenSaturation() throws Exception {
        int databaseSizeBeforeUpdate = oxygenSaturationRepository.findAll().size();
        oxygenSaturation.setId(UUID.randomUUID());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOxygenSaturationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, oxygenSaturation.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(oxygenSaturation))
            )
            .andExpect(status().isBadRequest());

        // Validate the OxygenSaturation in the database
        List<OxygenSaturation> oxygenSaturationList = oxygenSaturationRepository.findAll();
        assertThat(oxygenSaturationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchOxygenSaturation() throws Exception {
        int databaseSizeBeforeUpdate = oxygenSaturationRepository.findAll().size();
        oxygenSaturation.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOxygenSaturationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(oxygenSaturation))
            )
            .andExpect(status().isBadRequest());

        // Validate the OxygenSaturation in the database
        List<OxygenSaturation> oxygenSaturationList = oxygenSaturationRepository.findAll();
        assertThat(oxygenSaturationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamOxygenSaturation() throws Exception {
        int databaseSizeBeforeUpdate = oxygenSaturationRepository.findAll().size();
        oxygenSaturation.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOxygenSaturationMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(oxygenSaturation))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the OxygenSaturation in the database
        List<OxygenSaturation> oxygenSaturationList = oxygenSaturationRepository.findAll();
        assertThat(oxygenSaturationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateOxygenSaturationWithPatch() throws Exception {
        // Initialize the database
        oxygenSaturationRepository.saveAndFlush(oxygenSaturation);

        int databaseSizeBeforeUpdate = oxygenSaturationRepository.findAll().size();

        // Update the oxygenSaturation using partial update
        OxygenSaturation partialUpdatedOxygenSaturation = new OxygenSaturation();
        partialUpdatedOxygenSaturation.setId(oxygenSaturation.getId());

        partialUpdatedOxygenSaturation
            .empresaId(UPDATED_EMPRESA_ID)
            .fieldOxigenSaturation(UPDATED_FIELD_OXIGEN_SATURATION)
            .fieldOxigenSaturationMode(UPDATED_FIELD_OXIGEN_SATURATION_MODE);

        restOxygenSaturationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOxygenSaturation.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOxygenSaturation))
            )
            .andExpect(status().isOk());

        // Validate the OxygenSaturation in the database
        List<OxygenSaturation> oxygenSaturationList = oxygenSaturationRepository.findAll();
        assertThat(oxygenSaturationList).hasSize(databaseSizeBeforeUpdate);
        OxygenSaturation testOxygenSaturation = oxygenSaturationList.get(oxygenSaturationList.size() - 1);
        assertThat(testOxygenSaturation.getUsuarioId()).isEqualTo(DEFAULT_USUARIO_ID);
        assertThat(testOxygenSaturation.getEmpresaId()).isEqualTo(UPDATED_EMPRESA_ID);
        assertThat(testOxygenSaturation.getFieldOxigenSaturation()).isEqualTo(UPDATED_FIELD_OXIGEN_SATURATION);
        assertThat(testOxygenSaturation.getFieldSuplementalOxigenFlowRate()).isEqualTo(DEFAULT_FIELD_SUPLEMENTAL_OXIGEN_FLOW_RATE);
        assertThat(testOxygenSaturation.getFieldOxigenTherapyAdministrationMode())
            .isEqualTo(DEFAULT_FIELD_OXIGEN_THERAPY_ADMINISTRATION_MODE);
        assertThat(testOxygenSaturation.getFieldOxigenSaturationMode()).isEqualTo(UPDATED_FIELD_OXIGEN_SATURATION_MODE);
        assertThat(testOxygenSaturation.getFieldOxigenSaturationMeasurementMethod())
            .isEqualTo(DEFAULT_FIELD_OXIGEN_SATURATION_MEASUREMENT_METHOD);
        assertThat(testOxygenSaturation.getEndTime()).isEqualTo(DEFAULT_END_TIME);
    }

    @Test
    @Transactional
    void fullUpdateOxygenSaturationWithPatch() throws Exception {
        // Initialize the database
        oxygenSaturationRepository.saveAndFlush(oxygenSaturation);

        int databaseSizeBeforeUpdate = oxygenSaturationRepository.findAll().size();

        // Update the oxygenSaturation using partial update
        OxygenSaturation partialUpdatedOxygenSaturation = new OxygenSaturation();
        partialUpdatedOxygenSaturation.setId(oxygenSaturation.getId());

        partialUpdatedOxygenSaturation
            .usuarioId(UPDATED_USUARIO_ID)
            .empresaId(UPDATED_EMPRESA_ID)
            .fieldOxigenSaturation(UPDATED_FIELD_OXIGEN_SATURATION)
            .fieldSuplementalOxigenFlowRate(UPDATED_FIELD_SUPLEMENTAL_OXIGEN_FLOW_RATE)
            .fieldOxigenTherapyAdministrationMode(UPDATED_FIELD_OXIGEN_THERAPY_ADMINISTRATION_MODE)
            .fieldOxigenSaturationMode(UPDATED_FIELD_OXIGEN_SATURATION_MODE)
            .fieldOxigenSaturationMeasurementMethod(UPDATED_FIELD_OXIGEN_SATURATION_MEASUREMENT_METHOD)
            .endTime(UPDATED_END_TIME);

        restOxygenSaturationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOxygenSaturation.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOxygenSaturation))
            )
            .andExpect(status().isOk());

        // Validate the OxygenSaturation in the database
        List<OxygenSaturation> oxygenSaturationList = oxygenSaturationRepository.findAll();
        assertThat(oxygenSaturationList).hasSize(databaseSizeBeforeUpdate);
        OxygenSaturation testOxygenSaturation = oxygenSaturationList.get(oxygenSaturationList.size() - 1);
        assertThat(testOxygenSaturation.getUsuarioId()).isEqualTo(UPDATED_USUARIO_ID);
        assertThat(testOxygenSaturation.getEmpresaId()).isEqualTo(UPDATED_EMPRESA_ID);
        assertThat(testOxygenSaturation.getFieldOxigenSaturation()).isEqualTo(UPDATED_FIELD_OXIGEN_SATURATION);
        assertThat(testOxygenSaturation.getFieldSuplementalOxigenFlowRate()).isEqualTo(UPDATED_FIELD_SUPLEMENTAL_OXIGEN_FLOW_RATE);
        assertThat(testOxygenSaturation.getFieldOxigenTherapyAdministrationMode())
            .isEqualTo(UPDATED_FIELD_OXIGEN_THERAPY_ADMINISTRATION_MODE);
        assertThat(testOxygenSaturation.getFieldOxigenSaturationMode()).isEqualTo(UPDATED_FIELD_OXIGEN_SATURATION_MODE);
        assertThat(testOxygenSaturation.getFieldOxigenSaturationMeasurementMethod())
            .isEqualTo(UPDATED_FIELD_OXIGEN_SATURATION_MEASUREMENT_METHOD);
        assertThat(testOxygenSaturation.getEndTime()).isEqualTo(UPDATED_END_TIME);
    }

    @Test
    @Transactional
    void patchNonExistingOxygenSaturation() throws Exception {
        int databaseSizeBeforeUpdate = oxygenSaturationRepository.findAll().size();
        oxygenSaturation.setId(UUID.randomUUID());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOxygenSaturationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, oxygenSaturation.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(oxygenSaturation))
            )
            .andExpect(status().isBadRequest());

        // Validate the OxygenSaturation in the database
        List<OxygenSaturation> oxygenSaturationList = oxygenSaturationRepository.findAll();
        assertThat(oxygenSaturationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchOxygenSaturation() throws Exception {
        int databaseSizeBeforeUpdate = oxygenSaturationRepository.findAll().size();
        oxygenSaturation.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOxygenSaturationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(oxygenSaturation))
            )
            .andExpect(status().isBadRequest());

        // Validate the OxygenSaturation in the database
        List<OxygenSaturation> oxygenSaturationList = oxygenSaturationRepository.findAll();
        assertThat(oxygenSaturationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamOxygenSaturation() throws Exception {
        int databaseSizeBeforeUpdate = oxygenSaturationRepository.findAll().size();
        oxygenSaturation.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOxygenSaturationMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(oxygenSaturation))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the OxygenSaturation in the database
        List<OxygenSaturation> oxygenSaturationList = oxygenSaturationRepository.findAll();
        assertThat(oxygenSaturationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteOxygenSaturation() throws Exception {
        // Initialize the database
        oxygenSaturationRepository.saveAndFlush(oxygenSaturation);

        int databaseSizeBeforeDelete = oxygenSaturationRepository.findAll().size();

        // Delete the oxygenSaturation
        restOxygenSaturationMockMvc
            .perform(delete(ENTITY_API_URL_ID, oxygenSaturation.getId().toString()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<OxygenSaturation> oxygenSaturationList = oxygenSaturationRepository.findAll();
        assertThat(oxygenSaturationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
