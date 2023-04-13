package com.be4tech.b4carecollect.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.be4tech.b4carecollect.IntegrationTest;
import com.be4tech.b4carecollect.domain.ActivitySummary;
import com.be4tech.b4carecollect.repository.ActivitySummaryRepository;
import com.be4tech.b4carecollect.service.criteria.ActivitySummaryCriteria;
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
 * Integration tests for the {@link ActivitySummaryResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ActivitySummaryResourceIT {

    private static final String DEFAULT_USUARIO_ID = "AAAAAAAAAA";
    private static final String UPDATED_USUARIO_ID = "BBBBBBBBBB";

    private static final String DEFAULT_EMPRESA_ID = "AAAAAAAAAA";
    private static final String UPDATED_EMPRESA_ID = "BBBBBBBBBB";

    private static final Integer DEFAULT_FIELD_ACTIVITY = 1;
    private static final Integer UPDATED_FIELD_ACTIVITY = 2;
    private static final Integer SMALLER_FIELD_ACTIVITY = 1 - 1;

    private static final Integer DEFAULT_FIELD_DURATION = 1;
    private static final Integer UPDATED_FIELD_DURATION = 2;
    private static final Integer SMALLER_FIELD_DURATION = 1 - 1;

    private static final Integer DEFAULT_FIELD_NUM_SEGMENTS = 1;
    private static final Integer UPDATED_FIELD_NUM_SEGMENTS = 2;
    private static final Integer SMALLER_FIELD_NUM_SEGMENTS = 1 - 1;

    private static final Instant DEFAULT_START_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_START_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_END_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_END_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/activity-summaries";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ActivitySummaryRepository activitySummaryRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restActivitySummaryMockMvc;

    private ActivitySummary activitySummary;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ActivitySummary createEntity(EntityManager em) {
        ActivitySummary activitySummary = new ActivitySummary()
            .usuarioId(DEFAULT_USUARIO_ID)
            .empresaId(DEFAULT_EMPRESA_ID)
            .fieldActivity(DEFAULT_FIELD_ACTIVITY)
            .fieldDuration(DEFAULT_FIELD_DURATION)
            .fieldNumSegments(DEFAULT_FIELD_NUM_SEGMENTS)
            .startTime(DEFAULT_START_TIME)
            .endTime(DEFAULT_END_TIME);
        return activitySummary;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ActivitySummary createUpdatedEntity(EntityManager em) {
        ActivitySummary activitySummary = new ActivitySummary()
            .usuarioId(UPDATED_USUARIO_ID)
            .empresaId(UPDATED_EMPRESA_ID)
            .fieldActivity(UPDATED_FIELD_ACTIVITY)
            .fieldDuration(UPDATED_FIELD_DURATION)
            .fieldNumSegments(UPDATED_FIELD_NUM_SEGMENTS)
            .startTime(UPDATED_START_TIME)
            .endTime(UPDATED_END_TIME);
        return activitySummary;
    }

    @BeforeEach
    public void initTest() {
        activitySummary = createEntity(em);
    }

    @Test
    @Transactional
    void createActivitySummary() throws Exception {
        int databaseSizeBeforeCreate = activitySummaryRepository.findAll().size();
        // Create the ActivitySummary
        restActivitySummaryMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(activitySummary))
            )
            .andExpect(status().isCreated());

        // Validate the ActivitySummary in the database
        List<ActivitySummary> activitySummaryList = activitySummaryRepository.findAll();
        assertThat(activitySummaryList).hasSize(databaseSizeBeforeCreate + 1);
        ActivitySummary testActivitySummary = activitySummaryList.get(activitySummaryList.size() - 1);
        assertThat(testActivitySummary.getUsuarioId()).isEqualTo(DEFAULT_USUARIO_ID);
        assertThat(testActivitySummary.getEmpresaId()).isEqualTo(DEFAULT_EMPRESA_ID);
        assertThat(testActivitySummary.getFieldActivity()).isEqualTo(DEFAULT_FIELD_ACTIVITY);
        assertThat(testActivitySummary.getFieldDuration()).isEqualTo(DEFAULT_FIELD_DURATION);
        assertThat(testActivitySummary.getFieldNumSegments()).isEqualTo(DEFAULT_FIELD_NUM_SEGMENTS);
        assertThat(testActivitySummary.getStartTime()).isEqualTo(DEFAULT_START_TIME);
        assertThat(testActivitySummary.getEndTime()).isEqualTo(DEFAULT_END_TIME);
    }

    @Test
    @Transactional
    void createActivitySummaryWithExistingId() throws Exception {
        // Create the ActivitySummary with an existing ID
        activitySummaryRepository.saveAndFlush(activitySummary);

        int databaseSizeBeforeCreate = activitySummaryRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restActivitySummaryMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(activitySummary))
            )
            .andExpect(status().isBadRequest());

        // Validate the ActivitySummary in the database
        List<ActivitySummary> activitySummaryList = activitySummaryRepository.findAll();
        assertThat(activitySummaryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllActivitySummaries() throws Exception {
        // Initialize the database
        activitySummaryRepository.saveAndFlush(activitySummary);

        // Get all the activitySummaryList
        restActivitySummaryMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(activitySummary.getId().toString())))
            .andExpect(jsonPath("$.[*].usuarioId").value(hasItem(DEFAULT_USUARIO_ID)))
            .andExpect(jsonPath("$.[*].empresaId").value(hasItem(DEFAULT_EMPRESA_ID)))
            .andExpect(jsonPath("$.[*].fieldActivity").value(hasItem(DEFAULT_FIELD_ACTIVITY)))
            .andExpect(jsonPath("$.[*].fieldDuration").value(hasItem(DEFAULT_FIELD_DURATION)))
            .andExpect(jsonPath("$.[*].fieldNumSegments").value(hasItem(DEFAULT_FIELD_NUM_SEGMENTS)))
            .andExpect(jsonPath("$.[*].startTime").value(hasItem(DEFAULT_START_TIME.toString())))
            .andExpect(jsonPath("$.[*].endTime").value(hasItem(DEFAULT_END_TIME.toString())));
    }

    @Test
    @Transactional
    void getActivitySummary() throws Exception {
        // Initialize the database
        activitySummaryRepository.saveAndFlush(activitySummary);

        // Get the activitySummary
        restActivitySummaryMockMvc
            .perform(get(ENTITY_API_URL_ID, activitySummary.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(activitySummary.getId().toString()))
            .andExpect(jsonPath("$.usuarioId").value(DEFAULT_USUARIO_ID))
            .andExpect(jsonPath("$.empresaId").value(DEFAULT_EMPRESA_ID))
            .andExpect(jsonPath("$.fieldActivity").value(DEFAULT_FIELD_ACTIVITY))
            .andExpect(jsonPath("$.fieldDuration").value(DEFAULT_FIELD_DURATION))
            .andExpect(jsonPath("$.fieldNumSegments").value(DEFAULT_FIELD_NUM_SEGMENTS))
            .andExpect(jsonPath("$.startTime").value(DEFAULT_START_TIME.toString()))
            .andExpect(jsonPath("$.endTime").value(DEFAULT_END_TIME.toString()));
    }

    @Test
    @Transactional
    void getActivitySummariesByIdFiltering() throws Exception {
        // Initialize the database
        activitySummaryRepository.saveAndFlush(activitySummary);

        UUID id = activitySummary.getId();

        defaultActivitySummaryShouldBeFound("id.equals=" + id);
        defaultActivitySummaryShouldNotBeFound("id.notEquals=" + id);
    }

    @Test
    @Transactional
    void getAllActivitySummariesByUsuarioIdIsEqualToSomething() throws Exception {
        // Initialize the database
        activitySummaryRepository.saveAndFlush(activitySummary);

        // Get all the activitySummaryList where usuarioId equals to DEFAULT_USUARIO_ID
        defaultActivitySummaryShouldBeFound("usuarioId.equals=" + DEFAULT_USUARIO_ID);

        // Get all the activitySummaryList where usuarioId equals to UPDATED_USUARIO_ID
        defaultActivitySummaryShouldNotBeFound("usuarioId.equals=" + UPDATED_USUARIO_ID);
    }

    @Test
    @Transactional
    void getAllActivitySummariesByUsuarioIdIsInShouldWork() throws Exception {
        // Initialize the database
        activitySummaryRepository.saveAndFlush(activitySummary);

        // Get all the activitySummaryList where usuarioId in DEFAULT_USUARIO_ID or UPDATED_USUARIO_ID
        defaultActivitySummaryShouldBeFound("usuarioId.in=" + DEFAULT_USUARIO_ID + "," + UPDATED_USUARIO_ID);

        // Get all the activitySummaryList where usuarioId equals to UPDATED_USUARIO_ID
        defaultActivitySummaryShouldNotBeFound("usuarioId.in=" + UPDATED_USUARIO_ID);
    }

    @Test
    @Transactional
    void getAllActivitySummariesByUsuarioIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        activitySummaryRepository.saveAndFlush(activitySummary);

        // Get all the activitySummaryList where usuarioId is not null
        defaultActivitySummaryShouldBeFound("usuarioId.specified=true");

        // Get all the activitySummaryList where usuarioId is null
        defaultActivitySummaryShouldNotBeFound("usuarioId.specified=false");
    }

    @Test
    @Transactional
    void getAllActivitySummariesByUsuarioIdContainsSomething() throws Exception {
        // Initialize the database
        activitySummaryRepository.saveAndFlush(activitySummary);

        // Get all the activitySummaryList where usuarioId contains DEFAULT_USUARIO_ID
        defaultActivitySummaryShouldBeFound("usuarioId.contains=" + DEFAULT_USUARIO_ID);

        // Get all the activitySummaryList where usuarioId contains UPDATED_USUARIO_ID
        defaultActivitySummaryShouldNotBeFound("usuarioId.contains=" + UPDATED_USUARIO_ID);
    }

    @Test
    @Transactional
    void getAllActivitySummariesByUsuarioIdNotContainsSomething() throws Exception {
        // Initialize the database
        activitySummaryRepository.saveAndFlush(activitySummary);

        // Get all the activitySummaryList where usuarioId does not contain DEFAULT_USUARIO_ID
        defaultActivitySummaryShouldNotBeFound("usuarioId.doesNotContain=" + DEFAULT_USUARIO_ID);

        // Get all the activitySummaryList where usuarioId does not contain UPDATED_USUARIO_ID
        defaultActivitySummaryShouldBeFound("usuarioId.doesNotContain=" + UPDATED_USUARIO_ID);
    }

    @Test
    @Transactional
    void getAllActivitySummariesByEmpresaIdIsEqualToSomething() throws Exception {
        // Initialize the database
        activitySummaryRepository.saveAndFlush(activitySummary);

        // Get all the activitySummaryList where empresaId equals to DEFAULT_EMPRESA_ID
        defaultActivitySummaryShouldBeFound("empresaId.equals=" + DEFAULT_EMPRESA_ID);

        // Get all the activitySummaryList where empresaId equals to UPDATED_EMPRESA_ID
        defaultActivitySummaryShouldNotBeFound("empresaId.equals=" + UPDATED_EMPRESA_ID);
    }

    @Test
    @Transactional
    void getAllActivitySummariesByEmpresaIdIsInShouldWork() throws Exception {
        // Initialize the database
        activitySummaryRepository.saveAndFlush(activitySummary);

        // Get all the activitySummaryList where empresaId in DEFAULT_EMPRESA_ID or UPDATED_EMPRESA_ID
        defaultActivitySummaryShouldBeFound("empresaId.in=" + DEFAULT_EMPRESA_ID + "," + UPDATED_EMPRESA_ID);

        // Get all the activitySummaryList where empresaId equals to UPDATED_EMPRESA_ID
        defaultActivitySummaryShouldNotBeFound("empresaId.in=" + UPDATED_EMPRESA_ID);
    }

    @Test
    @Transactional
    void getAllActivitySummariesByEmpresaIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        activitySummaryRepository.saveAndFlush(activitySummary);

        // Get all the activitySummaryList where empresaId is not null
        defaultActivitySummaryShouldBeFound("empresaId.specified=true");

        // Get all the activitySummaryList where empresaId is null
        defaultActivitySummaryShouldNotBeFound("empresaId.specified=false");
    }

    @Test
    @Transactional
    void getAllActivitySummariesByEmpresaIdContainsSomething() throws Exception {
        // Initialize the database
        activitySummaryRepository.saveAndFlush(activitySummary);

        // Get all the activitySummaryList where empresaId contains DEFAULT_EMPRESA_ID
        defaultActivitySummaryShouldBeFound("empresaId.contains=" + DEFAULT_EMPRESA_ID);

        // Get all the activitySummaryList where empresaId contains UPDATED_EMPRESA_ID
        defaultActivitySummaryShouldNotBeFound("empresaId.contains=" + UPDATED_EMPRESA_ID);
    }

    @Test
    @Transactional
    void getAllActivitySummariesByEmpresaIdNotContainsSomething() throws Exception {
        // Initialize the database
        activitySummaryRepository.saveAndFlush(activitySummary);

        // Get all the activitySummaryList where empresaId does not contain DEFAULT_EMPRESA_ID
        defaultActivitySummaryShouldNotBeFound("empresaId.doesNotContain=" + DEFAULT_EMPRESA_ID);

        // Get all the activitySummaryList where empresaId does not contain UPDATED_EMPRESA_ID
        defaultActivitySummaryShouldBeFound("empresaId.doesNotContain=" + UPDATED_EMPRESA_ID);
    }

    @Test
    @Transactional
    void getAllActivitySummariesByFieldActivityIsEqualToSomething() throws Exception {
        // Initialize the database
        activitySummaryRepository.saveAndFlush(activitySummary);

        // Get all the activitySummaryList where fieldActivity equals to DEFAULT_FIELD_ACTIVITY
        defaultActivitySummaryShouldBeFound("fieldActivity.equals=" + DEFAULT_FIELD_ACTIVITY);

        // Get all the activitySummaryList where fieldActivity equals to UPDATED_FIELD_ACTIVITY
        defaultActivitySummaryShouldNotBeFound("fieldActivity.equals=" + UPDATED_FIELD_ACTIVITY);
    }

    @Test
    @Transactional
    void getAllActivitySummariesByFieldActivityIsInShouldWork() throws Exception {
        // Initialize the database
        activitySummaryRepository.saveAndFlush(activitySummary);

        // Get all the activitySummaryList where fieldActivity in DEFAULT_FIELD_ACTIVITY or UPDATED_FIELD_ACTIVITY
        defaultActivitySummaryShouldBeFound("fieldActivity.in=" + DEFAULT_FIELD_ACTIVITY + "," + UPDATED_FIELD_ACTIVITY);

        // Get all the activitySummaryList where fieldActivity equals to UPDATED_FIELD_ACTIVITY
        defaultActivitySummaryShouldNotBeFound("fieldActivity.in=" + UPDATED_FIELD_ACTIVITY);
    }

    @Test
    @Transactional
    void getAllActivitySummariesByFieldActivityIsNullOrNotNull() throws Exception {
        // Initialize the database
        activitySummaryRepository.saveAndFlush(activitySummary);

        // Get all the activitySummaryList where fieldActivity is not null
        defaultActivitySummaryShouldBeFound("fieldActivity.specified=true");

        // Get all the activitySummaryList where fieldActivity is null
        defaultActivitySummaryShouldNotBeFound("fieldActivity.specified=false");
    }

    @Test
    @Transactional
    void getAllActivitySummariesByFieldActivityIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        activitySummaryRepository.saveAndFlush(activitySummary);

        // Get all the activitySummaryList where fieldActivity is greater than or equal to DEFAULT_FIELD_ACTIVITY
        defaultActivitySummaryShouldBeFound("fieldActivity.greaterThanOrEqual=" + DEFAULT_FIELD_ACTIVITY);

        // Get all the activitySummaryList where fieldActivity is greater than or equal to UPDATED_FIELD_ACTIVITY
        defaultActivitySummaryShouldNotBeFound("fieldActivity.greaterThanOrEqual=" + UPDATED_FIELD_ACTIVITY);
    }

    @Test
    @Transactional
    void getAllActivitySummariesByFieldActivityIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        activitySummaryRepository.saveAndFlush(activitySummary);

        // Get all the activitySummaryList where fieldActivity is less than or equal to DEFAULT_FIELD_ACTIVITY
        defaultActivitySummaryShouldBeFound("fieldActivity.lessThanOrEqual=" + DEFAULT_FIELD_ACTIVITY);

        // Get all the activitySummaryList where fieldActivity is less than or equal to SMALLER_FIELD_ACTIVITY
        defaultActivitySummaryShouldNotBeFound("fieldActivity.lessThanOrEqual=" + SMALLER_FIELD_ACTIVITY);
    }

    @Test
    @Transactional
    void getAllActivitySummariesByFieldActivityIsLessThanSomething() throws Exception {
        // Initialize the database
        activitySummaryRepository.saveAndFlush(activitySummary);

        // Get all the activitySummaryList where fieldActivity is less than DEFAULT_FIELD_ACTIVITY
        defaultActivitySummaryShouldNotBeFound("fieldActivity.lessThan=" + DEFAULT_FIELD_ACTIVITY);

        // Get all the activitySummaryList where fieldActivity is less than UPDATED_FIELD_ACTIVITY
        defaultActivitySummaryShouldBeFound("fieldActivity.lessThan=" + UPDATED_FIELD_ACTIVITY);
    }

    @Test
    @Transactional
    void getAllActivitySummariesByFieldActivityIsGreaterThanSomething() throws Exception {
        // Initialize the database
        activitySummaryRepository.saveAndFlush(activitySummary);

        // Get all the activitySummaryList where fieldActivity is greater than DEFAULT_FIELD_ACTIVITY
        defaultActivitySummaryShouldNotBeFound("fieldActivity.greaterThan=" + DEFAULT_FIELD_ACTIVITY);

        // Get all the activitySummaryList where fieldActivity is greater than SMALLER_FIELD_ACTIVITY
        defaultActivitySummaryShouldBeFound("fieldActivity.greaterThan=" + SMALLER_FIELD_ACTIVITY);
    }

    @Test
    @Transactional
    void getAllActivitySummariesByFieldDurationIsEqualToSomething() throws Exception {
        // Initialize the database
        activitySummaryRepository.saveAndFlush(activitySummary);

        // Get all the activitySummaryList where fieldDuration equals to DEFAULT_FIELD_DURATION
        defaultActivitySummaryShouldBeFound("fieldDuration.equals=" + DEFAULT_FIELD_DURATION);

        // Get all the activitySummaryList where fieldDuration equals to UPDATED_FIELD_DURATION
        defaultActivitySummaryShouldNotBeFound("fieldDuration.equals=" + UPDATED_FIELD_DURATION);
    }

    @Test
    @Transactional
    void getAllActivitySummariesByFieldDurationIsInShouldWork() throws Exception {
        // Initialize the database
        activitySummaryRepository.saveAndFlush(activitySummary);

        // Get all the activitySummaryList where fieldDuration in DEFAULT_FIELD_DURATION or UPDATED_FIELD_DURATION
        defaultActivitySummaryShouldBeFound("fieldDuration.in=" + DEFAULT_FIELD_DURATION + "," + UPDATED_FIELD_DURATION);

        // Get all the activitySummaryList where fieldDuration equals to UPDATED_FIELD_DURATION
        defaultActivitySummaryShouldNotBeFound("fieldDuration.in=" + UPDATED_FIELD_DURATION);
    }

    @Test
    @Transactional
    void getAllActivitySummariesByFieldDurationIsNullOrNotNull() throws Exception {
        // Initialize the database
        activitySummaryRepository.saveAndFlush(activitySummary);

        // Get all the activitySummaryList where fieldDuration is not null
        defaultActivitySummaryShouldBeFound("fieldDuration.specified=true");

        // Get all the activitySummaryList where fieldDuration is null
        defaultActivitySummaryShouldNotBeFound("fieldDuration.specified=false");
    }

    @Test
    @Transactional
    void getAllActivitySummariesByFieldDurationIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        activitySummaryRepository.saveAndFlush(activitySummary);

        // Get all the activitySummaryList where fieldDuration is greater than or equal to DEFAULT_FIELD_DURATION
        defaultActivitySummaryShouldBeFound("fieldDuration.greaterThanOrEqual=" + DEFAULT_FIELD_DURATION);

        // Get all the activitySummaryList where fieldDuration is greater than or equal to UPDATED_FIELD_DURATION
        defaultActivitySummaryShouldNotBeFound("fieldDuration.greaterThanOrEqual=" + UPDATED_FIELD_DURATION);
    }

    @Test
    @Transactional
    void getAllActivitySummariesByFieldDurationIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        activitySummaryRepository.saveAndFlush(activitySummary);

        // Get all the activitySummaryList where fieldDuration is less than or equal to DEFAULT_FIELD_DURATION
        defaultActivitySummaryShouldBeFound("fieldDuration.lessThanOrEqual=" + DEFAULT_FIELD_DURATION);

        // Get all the activitySummaryList where fieldDuration is less than or equal to SMALLER_FIELD_DURATION
        defaultActivitySummaryShouldNotBeFound("fieldDuration.lessThanOrEqual=" + SMALLER_FIELD_DURATION);
    }

    @Test
    @Transactional
    void getAllActivitySummariesByFieldDurationIsLessThanSomething() throws Exception {
        // Initialize the database
        activitySummaryRepository.saveAndFlush(activitySummary);

        // Get all the activitySummaryList where fieldDuration is less than DEFAULT_FIELD_DURATION
        defaultActivitySummaryShouldNotBeFound("fieldDuration.lessThan=" + DEFAULT_FIELD_DURATION);

        // Get all the activitySummaryList where fieldDuration is less than UPDATED_FIELD_DURATION
        defaultActivitySummaryShouldBeFound("fieldDuration.lessThan=" + UPDATED_FIELD_DURATION);
    }

    @Test
    @Transactional
    void getAllActivitySummariesByFieldDurationIsGreaterThanSomething() throws Exception {
        // Initialize the database
        activitySummaryRepository.saveAndFlush(activitySummary);

        // Get all the activitySummaryList where fieldDuration is greater than DEFAULT_FIELD_DURATION
        defaultActivitySummaryShouldNotBeFound("fieldDuration.greaterThan=" + DEFAULT_FIELD_DURATION);

        // Get all the activitySummaryList where fieldDuration is greater than SMALLER_FIELD_DURATION
        defaultActivitySummaryShouldBeFound("fieldDuration.greaterThan=" + SMALLER_FIELD_DURATION);
    }

    @Test
    @Transactional
    void getAllActivitySummariesByFieldNumSegmentsIsEqualToSomething() throws Exception {
        // Initialize the database
        activitySummaryRepository.saveAndFlush(activitySummary);

        // Get all the activitySummaryList where fieldNumSegments equals to DEFAULT_FIELD_NUM_SEGMENTS
        defaultActivitySummaryShouldBeFound("fieldNumSegments.equals=" + DEFAULT_FIELD_NUM_SEGMENTS);

        // Get all the activitySummaryList where fieldNumSegments equals to UPDATED_FIELD_NUM_SEGMENTS
        defaultActivitySummaryShouldNotBeFound("fieldNumSegments.equals=" + UPDATED_FIELD_NUM_SEGMENTS);
    }

    @Test
    @Transactional
    void getAllActivitySummariesByFieldNumSegmentsIsInShouldWork() throws Exception {
        // Initialize the database
        activitySummaryRepository.saveAndFlush(activitySummary);

        // Get all the activitySummaryList where fieldNumSegments in DEFAULT_FIELD_NUM_SEGMENTS or UPDATED_FIELD_NUM_SEGMENTS
        defaultActivitySummaryShouldBeFound("fieldNumSegments.in=" + DEFAULT_FIELD_NUM_SEGMENTS + "," + UPDATED_FIELD_NUM_SEGMENTS);

        // Get all the activitySummaryList where fieldNumSegments equals to UPDATED_FIELD_NUM_SEGMENTS
        defaultActivitySummaryShouldNotBeFound("fieldNumSegments.in=" + UPDATED_FIELD_NUM_SEGMENTS);
    }

    @Test
    @Transactional
    void getAllActivitySummariesByFieldNumSegmentsIsNullOrNotNull() throws Exception {
        // Initialize the database
        activitySummaryRepository.saveAndFlush(activitySummary);

        // Get all the activitySummaryList where fieldNumSegments is not null
        defaultActivitySummaryShouldBeFound("fieldNumSegments.specified=true");

        // Get all the activitySummaryList where fieldNumSegments is null
        defaultActivitySummaryShouldNotBeFound("fieldNumSegments.specified=false");
    }

    @Test
    @Transactional
    void getAllActivitySummariesByFieldNumSegmentsIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        activitySummaryRepository.saveAndFlush(activitySummary);

        // Get all the activitySummaryList where fieldNumSegments is greater than or equal to DEFAULT_FIELD_NUM_SEGMENTS
        defaultActivitySummaryShouldBeFound("fieldNumSegments.greaterThanOrEqual=" + DEFAULT_FIELD_NUM_SEGMENTS);

        // Get all the activitySummaryList where fieldNumSegments is greater than or equal to UPDATED_FIELD_NUM_SEGMENTS
        defaultActivitySummaryShouldNotBeFound("fieldNumSegments.greaterThanOrEqual=" + UPDATED_FIELD_NUM_SEGMENTS);
    }

    @Test
    @Transactional
    void getAllActivitySummariesByFieldNumSegmentsIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        activitySummaryRepository.saveAndFlush(activitySummary);

        // Get all the activitySummaryList where fieldNumSegments is less than or equal to DEFAULT_FIELD_NUM_SEGMENTS
        defaultActivitySummaryShouldBeFound("fieldNumSegments.lessThanOrEqual=" + DEFAULT_FIELD_NUM_SEGMENTS);

        // Get all the activitySummaryList where fieldNumSegments is less than or equal to SMALLER_FIELD_NUM_SEGMENTS
        defaultActivitySummaryShouldNotBeFound("fieldNumSegments.lessThanOrEqual=" + SMALLER_FIELD_NUM_SEGMENTS);
    }

    @Test
    @Transactional
    void getAllActivitySummariesByFieldNumSegmentsIsLessThanSomething() throws Exception {
        // Initialize the database
        activitySummaryRepository.saveAndFlush(activitySummary);

        // Get all the activitySummaryList where fieldNumSegments is less than DEFAULT_FIELD_NUM_SEGMENTS
        defaultActivitySummaryShouldNotBeFound("fieldNumSegments.lessThan=" + DEFAULT_FIELD_NUM_SEGMENTS);

        // Get all the activitySummaryList where fieldNumSegments is less than UPDATED_FIELD_NUM_SEGMENTS
        defaultActivitySummaryShouldBeFound("fieldNumSegments.lessThan=" + UPDATED_FIELD_NUM_SEGMENTS);
    }

    @Test
    @Transactional
    void getAllActivitySummariesByFieldNumSegmentsIsGreaterThanSomething() throws Exception {
        // Initialize the database
        activitySummaryRepository.saveAndFlush(activitySummary);

        // Get all the activitySummaryList where fieldNumSegments is greater than DEFAULT_FIELD_NUM_SEGMENTS
        defaultActivitySummaryShouldNotBeFound("fieldNumSegments.greaterThan=" + DEFAULT_FIELD_NUM_SEGMENTS);

        // Get all the activitySummaryList where fieldNumSegments is greater than SMALLER_FIELD_NUM_SEGMENTS
        defaultActivitySummaryShouldBeFound("fieldNumSegments.greaterThan=" + SMALLER_FIELD_NUM_SEGMENTS);
    }

    @Test
    @Transactional
    void getAllActivitySummariesByStartTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        activitySummaryRepository.saveAndFlush(activitySummary);

        // Get all the activitySummaryList where startTime equals to DEFAULT_START_TIME
        defaultActivitySummaryShouldBeFound("startTime.equals=" + DEFAULT_START_TIME);

        // Get all the activitySummaryList where startTime equals to UPDATED_START_TIME
        defaultActivitySummaryShouldNotBeFound("startTime.equals=" + UPDATED_START_TIME);
    }

    @Test
    @Transactional
    void getAllActivitySummariesByStartTimeIsInShouldWork() throws Exception {
        // Initialize the database
        activitySummaryRepository.saveAndFlush(activitySummary);

        // Get all the activitySummaryList where startTime in DEFAULT_START_TIME or UPDATED_START_TIME
        defaultActivitySummaryShouldBeFound("startTime.in=" + DEFAULT_START_TIME + "," + UPDATED_START_TIME);

        // Get all the activitySummaryList where startTime equals to UPDATED_START_TIME
        defaultActivitySummaryShouldNotBeFound("startTime.in=" + UPDATED_START_TIME);
    }

    @Test
    @Transactional
    void getAllActivitySummariesByStartTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        activitySummaryRepository.saveAndFlush(activitySummary);

        // Get all the activitySummaryList where startTime is not null
        defaultActivitySummaryShouldBeFound("startTime.specified=true");

        // Get all the activitySummaryList where startTime is null
        defaultActivitySummaryShouldNotBeFound("startTime.specified=false");
    }

    @Test
    @Transactional
    void getAllActivitySummariesByEndTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        activitySummaryRepository.saveAndFlush(activitySummary);

        // Get all the activitySummaryList where endTime equals to DEFAULT_END_TIME
        defaultActivitySummaryShouldBeFound("endTime.equals=" + DEFAULT_END_TIME);

        // Get all the activitySummaryList where endTime equals to UPDATED_END_TIME
        defaultActivitySummaryShouldNotBeFound("endTime.equals=" + UPDATED_END_TIME);
    }

    @Test
    @Transactional
    void getAllActivitySummariesByEndTimeIsInShouldWork() throws Exception {
        // Initialize the database
        activitySummaryRepository.saveAndFlush(activitySummary);

        // Get all the activitySummaryList where endTime in DEFAULT_END_TIME or UPDATED_END_TIME
        defaultActivitySummaryShouldBeFound("endTime.in=" + DEFAULT_END_TIME + "," + UPDATED_END_TIME);

        // Get all the activitySummaryList where endTime equals to UPDATED_END_TIME
        defaultActivitySummaryShouldNotBeFound("endTime.in=" + UPDATED_END_TIME);
    }

    @Test
    @Transactional
    void getAllActivitySummariesByEndTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        activitySummaryRepository.saveAndFlush(activitySummary);

        // Get all the activitySummaryList where endTime is not null
        defaultActivitySummaryShouldBeFound("endTime.specified=true");

        // Get all the activitySummaryList where endTime is null
        defaultActivitySummaryShouldNotBeFound("endTime.specified=false");
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultActivitySummaryShouldBeFound(String filter) throws Exception {
        restActivitySummaryMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(activitySummary.getId().toString())))
            .andExpect(jsonPath("$.[*].usuarioId").value(hasItem(DEFAULT_USUARIO_ID)))
            .andExpect(jsonPath("$.[*].empresaId").value(hasItem(DEFAULT_EMPRESA_ID)))
            .andExpect(jsonPath("$.[*].fieldActivity").value(hasItem(DEFAULT_FIELD_ACTIVITY)))
            .andExpect(jsonPath("$.[*].fieldDuration").value(hasItem(DEFAULT_FIELD_DURATION)))
            .andExpect(jsonPath("$.[*].fieldNumSegments").value(hasItem(DEFAULT_FIELD_NUM_SEGMENTS)))
            .andExpect(jsonPath("$.[*].startTime").value(hasItem(DEFAULT_START_TIME.toString())))
            .andExpect(jsonPath("$.[*].endTime").value(hasItem(DEFAULT_END_TIME.toString())));

        // Check, that the count call also returns 1
        restActivitySummaryMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultActivitySummaryShouldNotBeFound(String filter) throws Exception {
        restActivitySummaryMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restActivitySummaryMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingActivitySummary() throws Exception {
        // Get the activitySummary
        restActivitySummaryMockMvc.perform(get(ENTITY_API_URL_ID, UUID.randomUUID().toString())).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingActivitySummary() throws Exception {
        // Initialize the database
        activitySummaryRepository.saveAndFlush(activitySummary);

        int databaseSizeBeforeUpdate = activitySummaryRepository.findAll().size();

        // Update the activitySummary
        ActivitySummary updatedActivitySummary = activitySummaryRepository.findById(activitySummary.getId()).get();
        // Disconnect from session so that the updates on updatedActivitySummary are not directly saved in db
        em.detach(updatedActivitySummary);
        updatedActivitySummary
            .usuarioId(UPDATED_USUARIO_ID)
            .empresaId(UPDATED_EMPRESA_ID)
            .fieldActivity(UPDATED_FIELD_ACTIVITY)
            .fieldDuration(UPDATED_FIELD_DURATION)
            .fieldNumSegments(UPDATED_FIELD_NUM_SEGMENTS)
            .startTime(UPDATED_START_TIME)
            .endTime(UPDATED_END_TIME);

        restActivitySummaryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedActivitySummary.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedActivitySummary))
            )
            .andExpect(status().isOk());

        // Validate the ActivitySummary in the database
        List<ActivitySummary> activitySummaryList = activitySummaryRepository.findAll();
        assertThat(activitySummaryList).hasSize(databaseSizeBeforeUpdate);
        ActivitySummary testActivitySummary = activitySummaryList.get(activitySummaryList.size() - 1);
        assertThat(testActivitySummary.getUsuarioId()).isEqualTo(UPDATED_USUARIO_ID);
        assertThat(testActivitySummary.getEmpresaId()).isEqualTo(UPDATED_EMPRESA_ID);
        assertThat(testActivitySummary.getFieldActivity()).isEqualTo(UPDATED_FIELD_ACTIVITY);
        assertThat(testActivitySummary.getFieldDuration()).isEqualTo(UPDATED_FIELD_DURATION);
        assertThat(testActivitySummary.getFieldNumSegments()).isEqualTo(UPDATED_FIELD_NUM_SEGMENTS);
        assertThat(testActivitySummary.getStartTime()).isEqualTo(UPDATED_START_TIME);
        assertThat(testActivitySummary.getEndTime()).isEqualTo(UPDATED_END_TIME);
    }

    @Test
    @Transactional
    void putNonExistingActivitySummary() throws Exception {
        int databaseSizeBeforeUpdate = activitySummaryRepository.findAll().size();
        activitySummary.setId(UUID.randomUUID());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restActivitySummaryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, activitySummary.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(activitySummary))
            )
            .andExpect(status().isBadRequest());

        // Validate the ActivitySummary in the database
        List<ActivitySummary> activitySummaryList = activitySummaryRepository.findAll();
        assertThat(activitySummaryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchActivitySummary() throws Exception {
        int databaseSizeBeforeUpdate = activitySummaryRepository.findAll().size();
        activitySummary.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restActivitySummaryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(activitySummary))
            )
            .andExpect(status().isBadRequest());

        // Validate the ActivitySummary in the database
        List<ActivitySummary> activitySummaryList = activitySummaryRepository.findAll();
        assertThat(activitySummaryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamActivitySummary() throws Exception {
        int databaseSizeBeforeUpdate = activitySummaryRepository.findAll().size();
        activitySummary.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restActivitySummaryMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(activitySummary))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ActivitySummary in the database
        List<ActivitySummary> activitySummaryList = activitySummaryRepository.findAll();
        assertThat(activitySummaryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateActivitySummaryWithPatch() throws Exception {
        // Initialize the database
        activitySummaryRepository.saveAndFlush(activitySummary);

        int databaseSizeBeforeUpdate = activitySummaryRepository.findAll().size();

        // Update the activitySummary using partial update
        ActivitySummary partialUpdatedActivitySummary = new ActivitySummary();
        partialUpdatedActivitySummary.setId(activitySummary.getId());

        partialUpdatedActivitySummary.usuarioId(UPDATED_USUARIO_ID).startTime(UPDATED_START_TIME);

        restActivitySummaryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedActivitySummary.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedActivitySummary))
            )
            .andExpect(status().isOk());

        // Validate the ActivitySummary in the database
        List<ActivitySummary> activitySummaryList = activitySummaryRepository.findAll();
        assertThat(activitySummaryList).hasSize(databaseSizeBeforeUpdate);
        ActivitySummary testActivitySummary = activitySummaryList.get(activitySummaryList.size() - 1);
        assertThat(testActivitySummary.getUsuarioId()).isEqualTo(UPDATED_USUARIO_ID);
        assertThat(testActivitySummary.getEmpresaId()).isEqualTo(DEFAULT_EMPRESA_ID);
        assertThat(testActivitySummary.getFieldActivity()).isEqualTo(DEFAULT_FIELD_ACTIVITY);
        assertThat(testActivitySummary.getFieldDuration()).isEqualTo(DEFAULT_FIELD_DURATION);
        assertThat(testActivitySummary.getFieldNumSegments()).isEqualTo(DEFAULT_FIELD_NUM_SEGMENTS);
        assertThat(testActivitySummary.getStartTime()).isEqualTo(UPDATED_START_TIME);
        assertThat(testActivitySummary.getEndTime()).isEqualTo(DEFAULT_END_TIME);
    }

    @Test
    @Transactional
    void fullUpdateActivitySummaryWithPatch() throws Exception {
        // Initialize the database
        activitySummaryRepository.saveAndFlush(activitySummary);

        int databaseSizeBeforeUpdate = activitySummaryRepository.findAll().size();

        // Update the activitySummary using partial update
        ActivitySummary partialUpdatedActivitySummary = new ActivitySummary();
        partialUpdatedActivitySummary.setId(activitySummary.getId());

        partialUpdatedActivitySummary
            .usuarioId(UPDATED_USUARIO_ID)
            .empresaId(UPDATED_EMPRESA_ID)
            .fieldActivity(UPDATED_FIELD_ACTIVITY)
            .fieldDuration(UPDATED_FIELD_DURATION)
            .fieldNumSegments(UPDATED_FIELD_NUM_SEGMENTS)
            .startTime(UPDATED_START_TIME)
            .endTime(UPDATED_END_TIME);

        restActivitySummaryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedActivitySummary.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedActivitySummary))
            )
            .andExpect(status().isOk());

        // Validate the ActivitySummary in the database
        List<ActivitySummary> activitySummaryList = activitySummaryRepository.findAll();
        assertThat(activitySummaryList).hasSize(databaseSizeBeforeUpdate);
        ActivitySummary testActivitySummary = activitySummaryList.get(activitySummaryList.size() - 1);
        assertThat(testActivitySummary.getUsuarioId()).isEqualTo(UPDATED_USUARIO_ID);
        assertThat(testActivitySummary.getEmpresaId()).isEqualTo(UPDATED_EMPRESA_ID);
        assertThat(testActivitySummary.getFieldActivity()).isEqualTo(UPDATED_FIELD_ACTIVITY);
        assertThat(testActivitySummary.getFieldDuration()).isEqualTo(UPDATED_FIELD_DURATION);
        assertThat(testActivitySummary.getFieldNumSegments()).isEqualTo(UPDATED_FIELD_NUM_SEGMENTS);
        assertThat(testActivitySummary.getStartTime()).isEqualTo(UPDATED_START_TIME);
        assertThat(testActivitySummary.getEndTime()).isEqualTo(UPDATED_END_TIME);
    }

    @Test
    @Transactional
    void patchNonExistingActivitySummary() throws Exception {
        int databaseSizeBeforeUpdate = activitySummaryRepository.findAll().size();
        activitySummary.setId(UUID.randomUUID());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restActivitySummaryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, activitySummary.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(activitySummary))
            )
            .andExpect(status().isBadRequest());

        // Validate the ActivitySummary in the database
        List<ActivitySummary> activitySummaryList = activitySummaryRepository.findAll();
        assertThat(activitySummaryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchActivitySummary() throws Exception {
        int databaseSizeBeforeUpdate = activitySummaryRepository.findAll().size();
        activitySummary.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restActivitySummaryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(activitySummary))
            )
            .andExpect(status().isBadRequest());

        // Validate the ActivitySummary in the database
        List<ActivitySummary> activitySummaryList = activitySummaryRepository.findAll();
        assertThat(activitySummaryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamActivitySummary() throws Exception {
        int databaseSizeBeforeUpdate = activitySummaryRepository.findAll().size();
        activitySummary.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restActivitySummaryMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(activitySummary))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ActivitySummary in the database
        List<ActivitySummary> activitySummaryList = activitySummaryRepository.findAll();
        assertThat(activitySummaryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteActivitySummary() throws Exception {
        // Initialize the database
        activitySummaryRepository.saveAndFlush(activitySummary);

        int databaseSizeBeforeDelete = activitySummaryRepository.findAll().size();

        // Delete the activitySummary
        restActivitySummaryMockMvc
            .perform(delete(ENTITY_API_URL_ID, activitySummary.getId().toString()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ActivitySummary> activitySummaryList = activitySummaryRepository.findAll();
        assertThat(activitySummaryList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
