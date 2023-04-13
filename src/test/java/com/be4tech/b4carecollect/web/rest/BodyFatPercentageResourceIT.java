package com.be4tech.b4carecollect.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.be4tech.b4carecollect.IntegrationTest;
import com.be4tech.b4carecollect.domain.BodyFatPercentage;
import com.be4tech.b4carecollect.repository.BodyFatPercentageRepository;
import com.be4tech.b4carecollect.service.criteria.BodyFatPercentageCriteria;
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
 * Integration tests for the {@link BodyFatPercentageResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class BodyFatPercentageResourceIT {

    private static final String DEFAULT_USUARIO_ID = "AAAAAAAAAA";
    private static final String UPDATED_USUARIO_ID = "BBBBBBBBBB";

    private static final String DEFAULT_EMPRESA_ID = "AAAAAAAAAA";
    private static final String UPDATED_EMPRESA_ID = "BBBBBBBBBB";

    private static final Float DEFAULT_FIELD_PORCENTAGE = 1F;
    private static final Float UPDATED_FIELD_PORCENTAGE = 2F;
    private static final Float SMALLER_FIELD_PORCENTAGE = 1F - 1F;

    private static final Instant DEFAULT_END_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_END_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/body-fat-percentages";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private BodyFatPercentageRepository bodyFatPercentageRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBodyFatPercentageMockMvc;

    private BodyFatPercentage bodyFatPercentage;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BodyFatPercentage createEntity(EntityManager em) {
        BodyFatPercentage bodyFatPercentage = new BodyFatPercentage()
            .usuarioId(DEFAULT_USUARIO_ID)
            .empresaId(DEFAULT_EMPRESA_ID)
            .fieldPorcentage(DEFAULT_FIELD_PORCENTAGE)
            .endTime(DEFAULT_END_TIME);
        return bodyFatPercentage;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BodyFatPercentage createUpdatedEntity(EntityManager em) {
        BodyFatPercentage bodyFatPercentage = new BodyFatPercentage()
            .usuarioId(UPDATED_USUARIO_ID)
            .empresaId(UPDATED_EMPRESA_ID)
            .fieldPorcentage(UPDATED_FIELD_PORCENTAGE)
            .endTime(UPDATED_END_TIME);
        return bodyFatPercentage;
    }

    @BeforeEach
    public void initTest() {
        bodyFatPercentage = createEntity(em);
    }

    @Test
    @Transactional
    void createBodyFatPercentage() throws Exception {
        int databaseSizeBeforeCreate = bodyFatPercentageRepository.findAll().size();
        // Create the BodyFatPercentage
        restBodyFatPercentageMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bodyFatPercentage))
            )
            .andExpect(status().isCreated());

        // Validate the BodyFatPercentage in the database
        List<BodyFatPercentage> bodyFatPercentageList = bodyFatPercentageRepository.findAll();
        assertThat(bodyFatPercentageList).hasSize(databaseSizeBeforeCreate + 1);
        BodyFatPercentage testBodyFatPercentage = bodyFatPercentageList.get(bodyFatPercentageList.size() - 1);
        assertThat(testBodyFatPercentage.getUsuarioId()).isEqualTo(DEFAULT_USUARIO_ID);
        assertThat(testBodyFatPercentage.getEmpresaId()).isEqualTo(DEFAULT_EMPRESA_ID);
        assertThat(testBodyFatPercentage.getFieldPorcentage()).isEqualTo(DEFAULT_FIELD_PORCENTAGE);
        assertThat(testBodyFatPercentage.getEndTime()).isEqualTo(DEFAULT_END_TIME);
    }

    @Test
    @Transactional
    void createBodyFatPercentageWithExistingId() throws Exception {
        // Create the BodyFatPercentage with an existing ID
        bodyFatPercentageRepository.saveAndFlush(bodyFatPercentage);

        int databaseSizeBeforeCreate = bodyFatPercentageRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBodyFatPercentageMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bodyFatPercentage))
            )
            .andExpect(status().isBadRequest());

        // Validate the BodyFatPercentage in the database
        List<BodyFatPercentage> bodyFatPercentageList = bodyFatPercentageRepository.findAll();
        assertThat(bodyFatPercentageList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllBodyFatPercentages() throws Exception {
        // Initialize the database
        bodyFatPercentageRepository.saveAndFlush(bodyFatPercentage);

        // Get all the bodyFatPercentageList
        restBodyFatPercentageMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bodyFatPercentage.getId().toString())))
            .andExpect(jsonPath("$.[*].usuarioId").value(hasItem(DEFAULT_USUARIO_ID)))
            .andExpect(jsonPath("$.[*].empresaId").value(hasItem(DEFAULT_EMPRESA_ID)))
            .andExpect(jsonPath("$.[*].fieldPorcentage").value(hasItem(DEFAULT_FIELD_PORCENTAGE.doubleValue())))
            .andExpect(jsonPath("$.[*].endTime").value(hasItem(DEFAULT_END_TIME.toString())));
    }

    @Test
    @Transactional
    void getBodyFatPercentage() throws Exception {
        // Initialize the database
        bodyFatPercentageRepository.saveAndFlush(bodyFatPercentage);

        // Get the bodyFatPercentage
        restBodyFatPercentageMockMvc
            .perform(get(ENTITY_API_URL_ID, bodyFatPercentage.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(bodyFatPercentage.getId().toString()))
            .andExpect(jsonPath("$.usuarioId").value(DEFAULT_USUARIO_ID))
            .andExpect(jsonPath("$.empresaId").value(DEFAULT_EMPRESA_ID))
            .andExpect(jsonPath("$.fieldPorcentage").value(DEFAULT_FIELD_PORCENTAGE.doubleValue()))
            .andExpect(jsonPath("$.endTime").value(DEFAULT_END_TIME.toString()));
    }

    @Test
    @Transactional
    void getBodyFatPercentagesByIdFiltering() throws Exception {
        // Initialize the database
        bodyFatPercentageRepository.saveAndFlush(bodyFatPercentage);

        UUID id = bodyFatPercentage.getId();

        defaultBodyFatPercentageShouldBeFound("id.equals=" + id);
        defaultBodyFatPercentageShouldNotBeFound("id.notEquals=" + id);
    }

    @Test
    @Transactional
    void getAllBodyFatPercentagesByUsuarioIdIsEqualToSomething() throws Exception {
        // Initialize the database
        bodyFatPercentageRepository.saveAndFlush(bodyFatPercentage);

        // Get all the bodyFatPercentageList where usuarioId equals to DEFAULT_USUARIO_ID
        defaultBodyFatPercentageShouldBeFound("usuarioId.equals=" + DEFAULT_USUARIO_ID);

        // Get all the bodyFatPercentageList where usuarioId equals to UPDATED_USUARIO_ID
        defaultBodyFatPercentageShouldNotBeFound("usuarioId.equals=" + UPDATED_USUARIO_ID);
    }

    @Test
    @Transactional
    void getAllBodyFatPercentagesByUsuarioIdIsInShouldWork() throws Exception {
        // Initialize the database
        bodyFatPercentageRepository.saveAndFlush(bodyFatPercentage);

        // Get all the bodyFatPercentageList where usuarioId in DEFAULT_USUARIO_ID or UPDATED_USUARIO_ID
        defaultBodyFatPercentageShouldBeFound("usuarioId.in=" + DEFAULT_USUARIO_ID + "," + UPDATED_USUARIO_ID);

        // Get all the bodyFatPercentageList where usuarioId equals to UPDATED_USUARIO_ID
        defaultBodyFatPercentageShouldNotBeFound("usuarioId.in=" + UPDATED_USUARIO_ID);
    }

    @Test
    @Transactional
    void getAllBodyFatPercentagesByUsuarioIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        bodyFatPercentageRepository.saveAndFlush(bodyFatPercentage);

        // Get all the bodyFatPercentageList where usuarioId is not null
        defaultBodyFatPercentageShouldBeFound("usuarioId.specified=true");

        // Get all the bodyFatPercentageList where usuarioId is null
        defaultBodyFatPercentageShouldNotBeFound("usuarioId.specified=false");
    }

    @Test
    @Transactional
    void getAllBodyFatPercentagesByUsuarioIdContainsSomething() throws Exception {
        // Initialize the database
        bodyFatPercentageRepository.saveAndFlush(bodyFatPercentage);

        // Get all the bodyFatPercentageList where usuarioId contains DEFAULT_USUARIO_ID
        defaultBodyFatPercentageShouldBeFound("usuarioId.contains=" + DEFAULT_USUARIO_ID);

        // Get all the bodyFatPercentageList where usuarioId contains UPDATED_USUARIO_ID
        defaultBodyFatPercentageShouldNotBeFound("usuarioId.contains=" + UPDATED_USUARIO_ID);
    }

    @Test
    @Transactional
    void getAllBodyFatPercentagesByUsuarioIdNotContainsSomething() throws Exception {
        // Initialize the database
        bodyFatPercentageRepository.saveAndFlush(bodyFatPercentage);

        // Get all the bodyFatPercentageList where usuarioId does not contain DEFAULT_USUARIO_ID
        defaultBodyFatPercentageShouldNotBeFound("usuarioId.doesNotContain=" + DEFAULT_USUARIO_ID);

        // Get all the bodyFatPercentageList where usuarioId does not contain UPDATED_USUARIO_ID
        defaultBodyFatPercentageShouldBeFound("usuarioId.doesNotContain=" + UPDATED_USUARIO_ID);
    }

    @Test
    @Transactional
    void getAllBodyFatPercentagesByEmpresaIdIsEqualToSomething() throws Exception {
        // Initialize the database
        bodyFatPercentageRepository.saveAndFlush(bodyFatPercentage);

        // Get all the bodyFatPercentageList where empresaId equals to DEFAULT_EMPRESA_ID
        defaultBodyFatPercentageShouldBeFound("empresaId.equals=" + DEFAULT_EMPRESA_ID);

        // Get all the bodyFatPercentageList where empresaId equals to UPDATED_EMPRESA_ID
        defaultBodyFatPercentageShouldNotBeFound("empresaId.equals=" + UPDATED_EMPRESA_ID);
    }

    @Test
    @Transactional
    void getAllBodyFatPercentagesByEmpresaIdIsInShouldWork() throws Exception {
        // Initialize the database
        bodyFatPercentageRepository.saveAndFlush(bodyFatPercentage);

        // Get all the bodyFatPercentageList where empresaId in DEFAULT_EMPRESA_ID or UPDATED_EMPRESA_ID
        defaultBodyFatPercentageShouldBeFound("empresaId.in=" + DEFAULT_EMPRESA_ID + "," + UPDATED_EMPRESA_ID);

        // Get all the bodyFatPercentageList where empresaId equals to UPDATED_EMPRESA_ID
        defaultBodyFatPercentageShouldNotBeFound("empresaId.in=" + UPDATED_EMPRESA_ID);
    }

    @Test
    @Transactional
    void getAllBodyFatPercentagesByEmpresaIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        bodyFatPercentageRepository.saveAndFlush(bodyFatPercentage);

        // Get all the bodyFatPercentageList where empresaId is not null
        defaultBodyFatPercentageShouldBeFound("empresaId.specified=true");

        // Get all the bodyFatPercentageList where empresaId is null
        defaultBodyFatPercentageShouldNotBeFound("empresaId.specified=false");
    }

    @Test
    @Transactional
    void getAllBodyFatPercentagesByEmpresaIdContainsSomething() throws Exception {
        // Initialize the database
        bodyFatPercentageRepository.saveAndFlush(bodyFatPercentage);

        // Get all the bodyFatPercentageList where empresaId contains DEFAULT_EMPRESA_ID
        defaultBodyFatPercentageShouldBeFound("empresaId.contains=" + DEFAULT_EMPRESA_ID);

        // Get all the bodyFatPercentageList where empresaId contains UPDATED_EMPRESA_ID
        defaultBodyFatPercentageShouldNotBeFound("empresaId.contains=" + UPDATED_EMPRESA_ID);
    }

    @Test
    @Transactional
    void getAllBodyFatPercentagesByEmpresaIdNotContainsSomething() throws Exception {
        // Initialize the database
        bodyFatPercentageRepository.saveAndFlush(bodyFatPercentage);

        // Get all the bodyFatPercentageList where empresaId does not contain DEFAULT_EMPRESA_ID
        defaultBodyFatPercentageShouldNotBeFound("empresaId.doesNotContain=" + DEFAULT_EMPRESA_ID);

        // Get all the bodyFatPercentageList where empresaId does not contain UPDATED_EMPRESA_ID
        defaultBodyFatPercentageShouldBeFound("empresaId.doesNotContain=" + UPDATED_EMPRESA_ID);
    }

    @Test
    @Transactional
    void getAllBodyFatPercentagesByFieldPorcentageIsEqualToSomething() throws Exception {
        // Initialize the database
        bodyFatPercentageRepository.saveAndFlush(bodyFatPercentage);

        // Get all the bodyFatPercentageList where fieldPorcentage equals to DEFAULT_FIELD_PORCENTAGE
        defaultBodyFatPercentageShouldBeFound("fieldPorcentage.equals=" + DEFAULT_FIELD_PORCENTAGE);

        // Get all the bodyFatPercentageList where fieldPorcentage equals to UPDATED_FIELD_PORCENTAGE
        defaultBodyFatPercentageShouldNotBeFound("fieldPorcentage.equals=" + UPDATED_FIELD_PORCENTAGE);
    }

    @Test
    @Transactional
    void getAllBodyFatPercentagesByFieldPorcentageIsInShouldWork() throws Exception {
        // Initialize the database
        bodyFatPercentageRepository.saveAndFlush(bodyFatPercentage);

        // Get all the bodyFatPercentageList where fieldPorcentage in DEFAULT_FIELD_PORCENTAGE or UPDATED_FIELD_PORCENTAGE
        defaultBodyFatPercentageShouldBeFound("fieldPorcentage.in=" + DEFAULT_FIELD_PORCENTAGE + "," + UPDATED_FIELD_PORCENTAGE);

        // Get all the bodyFatPercentageList where fieldPorcentage equals to UPDATED_FIELD_PORCENTAGE
        defaultBodyFatPercentageShouldNotBeFound("fieldPorcentage.in=" + UPDATED_FIELD_PORCENTAGE);
    }

    @Test
    @Transactional
    void getAllBodyFatPercentagesByFieldPorcentageIsNullOrNotNull() throws Exception {
        // Initialize the database
        bodyFatPercentageRepository.saveAndFlush(bodyFatPercentage);

        // Get all the bodyFatPercentageList where fieldPorcentage is not null
        defaultBodyFatPercentageShouldBeFound("fieldPorcentage.specified=true");

        // Get all the bodyFatPercentageList where fieldPorcentage is null
        defaultBodyFatPercentageShouldNotBeFound("fieldPorcentage.specified=false");
    }

    @Test
    @Transactional
    void getAllBodyFatPercentagesByFieldPorcentageIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        bodyFatPercentageRepository.saveAndFlush(bodyFatPercentage);

        // Get all the bodyFatPercentageList where fieldPorcentage is greater than or equal to DEFAULT_FIELD_PORCENTAGE
        defaultBodyFatPercentageShouldBeFound("fieldPorcentage.greaterThanOrEqual=" + DEFAULT_FIELD_PORCENTAGE);

        // Get all the bodyFatPercentageList where fieldPorcentage is greater than or equal to UPDATED_FIELD_PORCENTAGE
        defaultBodyFatPercentageShouldNotBeFound("fieldPorcentage.greaterThanOrEqual=" + UPDATED_FIELD_PORCENTAGE);
    }

    @Test
    @Transactional
    void getAllBodyFatPercentagesByFieldPorcentageIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        bodyFatPercentageRepository.saveAndFlush(bodyFatPercentage);

        // Get all the bodyFatPercentageList where fieldPorcentage is less than or equal to DEFAULT_FIELD_PORCENTAGE
        defaultBodyFatPercentageShouldBeFound("fieldPorcentage.lessThanOrEqual=" + DEFAULT_FIELD_PORCENTAGE);

        // Get all the bodyFatPercentageList where fieldPorcentage is less than or equal to SMALLER_FIELD_PORCENTAGE
        defaultBodyFatPercentageShouldNotBeFound("fieldPorcentage.lessThanOrEqual=" + SMALLER_FIELD_PORCENTAGE);
    }

    @Test
    @Transactional
    void getAllBodyFatPercentagesByFieldPorcentageIsLessThanSomething() throws Exception {
        // Initialize the database
        bodyFatPercentageRepository.saveAndFlush(bodyFatPercentage);

        // Get all the bodyFatPercentageList where fieldPorcentage is less than DEFAULT_FIELD_PORCENTAGE
        defaultBodyFatPercentageShouldNotBeFound("fieldPorcentage.lessThan=" + DEFAULT_FIELD_PORCENTAGE);

        // Get all the bodyFatPercentageList where fieldPorcentage is less than UPDATED_FIELD_PORCENTAGE
        defaultBodyFatPercentageShouldBeFound("fieldPorcentage.lessThan=" + UPDATED_FIELD_PORCENTAGE);
    }

    @Test
    @Transactional
    void getAllBodyFatPercentagesByFieldPorcentageIsGreaterThanSomething() throws Exception {
        // Initialize the database
        bodyFatPercentageRepository.saveAndFlush(bodyFatPercentage);

        // Get all the bodyFatPercentageList where fieldPorcentage is greater than DEFAULT_FIELD_PORCENTAGE
        defaultBodyFatPercentageShouldNotBeFound("fieldPorcentage.greaterThan=" + DEFAULT_FIELD_PORCENTAGE);

        // Get all the bodyFatPercentageList where fieldPorcentage is greater than SMALLER_FIELD_PORCENTAGE
        defaultBodyFatPercentageShouldBeFound("fieldPorcentage.greaterThan=" + SMALLER_FIELD_PORCENTAGE);
    }

    @Test
    @Transactional
    void getAllBodyFatPercentagesByEndTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        bodyFatPercentageRepository.saveAndFlush(bodyFatPercentage);

        // Get all the bodyFatPercentageList where endTime equals to DEFAULT_END_TIME
        defaultBodyFatPercentageShouldBeFound("endTime.equals=" + DEFAULT_END_TIME);

        // Get all the bodyFatPercentageList where endTime equals to UPDATED_END_TIME
        defaultBodyFatPercentageShouldNotBeFound("endTime.equals=" + UPDATED_END_TIME);
    }

    @Test
    @Transactional
    void getAllBodyFatPercentagesByEndTimeIsInShouldWork() throws Exception {
        // Initialize the database
        bodyFatPercentageRepository.saveAndFlush(bodyFatPercentage);

        // Get all the bodyFatPercentageList where endTime in DEFAULT_END_TIME or UPDATED_END_TIME
        defaultBodyFatPercentageShouldBeFound("endTime.in=" + DEFAULT_END_TIME + "," + UPDATED_END_TIME);

        // Get all the bodyFatPercentageList where endTime equals to UPDATED_END_TIME
        defaultBodyFatPercentageShouldNotBeFound("endTime.in=" + UPDATED_END_TIME);
    }

    @Test
    @Transactional
    void getAllBodyFatPercentagesByEndTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        bodyFatPercentageRepository.saveAndFlush(bodyFatPercentage);

        // Get all the bodyFatPercentageList where endTime is not null
        defaultBodyFatPercentageShouldBeFound("endTime.specified=true");

        // Get all the bodyFatPercentageList where endTime is null
        defaultBodyFatPercentageShouldNotBeFound("endTime.specified=false");
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultBodyFatPercentageShouldBeFound(String filter) throws Exception {
        restBodyFatPercentageMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bodyFatPercentage.getId().toString())))
            .andExpect(jsonPath("$.[*].usuarioId").value(hasItem(DEFAULT_USUARIO_ID)))
            .andExpect(jsonPath("$.[*].empresaId").value(hasItem(DEFAULT_EMPRESA_ID)))
            .andExpect(jsonPath("$.[*].fieldPorcentage").value(hasItem(DEFAULT_FIELD_PORCENTAGE.doubleValue())))
            .andExpect(jsonPath("$.[*].endTime").value(hasItem(DEFAULT_END_TIME.toString())));

        // Check, that the count call also returns 1
        restBodyFatPercentageMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultBodyFatPercentageShouldNotBeFound(String filter) throws Exception {
        restBodyFatPercentageMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restBodyFatPercentageMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingBodyFatPercentage() throws Exception {
        // Get the bodyFatPercentage
        restBodyFatPercentageMockMvc.perform(get(ENTITY_API_URL_ID, UUID.randomUUID().toString())).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingBodyFatPercentage() throws Exception {
        // Initialize the database
        bodyFatPercentageRepository.saveAndFlush(bodyFatPercentage);

        int databaseSizeBeforeUpdate = bodyFatPercentageRepository.findAll().size();

        // Update the bodyFatPercentage
        BodyFatPercentage updatedBodyFatPercentage = bodyFatPercentageRepository.findById(bodyFatPercentage.getId()).get();
        // Disconnect from session so that the updates on updatedBodyFatPercentage are not directly saved in db
        em.detach(updatedBodyFatPercentage);
        updatedBodyFatPercentage
            .usuarioId(UPDATED_USUARIO_ID)
            .empresaId(UPDATED_EMPRESA_ID)
            .fieldPorcentage(UPDATED_FIELD_PORCENTAGE)
            .endTime(UPDATED_END_TIME);

        restBodyFatPercentageMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedBodyFatPercentage.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedBodyFatPercentage))
            )
            .andExpect(status().isOk());

        // Validate the BodyFatPercentage in the database
        List<BodyFatPercentage> bodyFatPercentageList = bodyFatPercentageRepository.findAll();
        assertThat(bodyFatPercentageList).hasSize(databaseSizeBeforeUpdate);
        BodyFatPercentage testBodyFatPercentage = bodyFatPercentageList.get(bodyFatPercentageList.size() - 1);
        assertThat(testBodyFatPercentage.getUsuarioId()).isEqualTo(UPDATED_USUARIO_ID);
        assertThat(testBodyFatPercentage.getEmpresaId()).isEqualTo(UPDATED_EMPRESA_ID);
        assertThat(testBodyFatPercentage.getFieldPorcentage()).isEqualTo(UPDATED_FIELD_PORCENTAGE);
        assertThat(testBodyFatPercentage.getEndTime()).isEqualTo(UPDATED_END_TIME);
    }

    @Test
    @Transactional
    void putNonExistingBodyFatPercentage() throws Exception {
        int databaseSizeBeforeUpdate = bodyFatPercentageRepository.findAll().size();
        bodyFatPercentage.setId(UUID.randomUUID());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBodyFatPercentageMockMvc
            .perform(
                put(ENTITY_API_URL_ID, bodyFatPercentage.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bodyFatPercentage))
            )
            .andExpect(status().isBadRequest());

        // Validate the BodyFatPercentage in the database
        List<BodyFatPercentage> bodyFatPercentageList = bodyFatPercentageRepository.findAll();
        assertThat(bodyFatPercentageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchBodyFatPercentage() throws Exception {
        int databaseSizeBeforeUpdate = bodyFatPercentageRepository.findAll().size();
        bodyFatPercentage.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBodyFatPercentageMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bodyFatPercentage))
            )
            .andExpect(status().isBadRequest());

        // Validate the BodyFatPercentage in the database
        List<BodyFatPercentage> bodyFatPercentageList = bodyFatPercentageRepository.findAll();
        assertThat(bodyFatPercentageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamBodyFatPercentage() throws Exception {
        int databaseSizeBeforeUpdate = bodyFatPercentageRepository.findAll().size();
        bodyFatPercentage.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBodyFatPercentageMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bodyFatPercentage))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the BodyFatPercentage in the database
        List<BodyFatPercentage> bodyFatPercentageList = bodyFatPercentageRepository.findAll();
        assertThat(bodyFatPercentageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateBodyFatPercentageWithPatch() throws Exception {
        // Initialize the database
        bodyFatPercentageRepository.saveAndFlush(bodyFatPercentage);

        int databaseSizeBeforeUpdate = bodyFatPercentageRepository.findAll().size();

        // Update the bodyFatPercentage using partial update
        BodyFatPercentage partialUpdatedBodyFatPercentage = new BodyFatPercentage();
        partialUpdatedBodyFatPercentage.setId(bodyFatPercentage.getId());

        partialUpdatedBodyFatPercentage.usuarioId(UPDATED_USUARIO_ID).fieldPorcentage(UPDATED_FIELD_PORCENTAGE);

        restBodyFatPercentageMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBodyFatPercentage.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBodyFatPercentage))
            )
            .andExpect(status().isOk());

        // Validate the BodyFatPercentage in the database
        List<BodyFatPercentage> bodyFatPercentageList = bodyFatPercentageRepository.findAll();
        assertThat(bodyFatPercentageList).hasSize(databaseSizeBeforeUpdate);
        BodyFatPercentage testBodyFatPercentage = bodyFatPercentageList.get(bodyFatPercentageList.size() - 1);
        assertThat(testBodyFatPercentage.getUsuarioId()).isEqualTo(UPDATED_USUARIO_ID);
        assertThat(testBodyFatPercentage.getEmpresaId()).isEqualTo(DEFAULT_EMPRESA_ID);
        assertThat(testBodyFatPercentage.getFieldPorcentage()).isEqualTo(UPDATED_FIELD_PORCENTAGE);
        assertThat(testBodyFatPercentage.getEndTime()).isEqualTo(DEFAULT_END_TIME);
    }

    @Test
    @Transactional
    void fullUpdateBodyFatPercentageWithPatch() throws Exception {
        // Initialize the database
        bodyFatPercentageRepository.saveAndFlush(bodyFatPercentage);

        int databaseSizeBeforeUpdate = bodyFatPercentageRepository.findAll().size();

        // Update the bodyFatPercentage using partial update
        BodyFatPercentage partialUpdatedBodyFatPercentage = new BodyFatPercentage();
        partialUpdatedBodyFatPercentage.setId(bodyFatPercentage.getId());

        partialUpdatedBodyFatPercentage
            .usuarioId(UPDATED_USUARIO_ID)
            .empresaId(UPDATED_EMPRESA_ID)
            .fieldPorcentage(UPDATED_FIELD_PORCENTAGE)
            .endTime(UPDATED_END_TIME);

        restBodyFatPercentageMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBodyFatPercentage.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBodyFatPercentage))
            )
            .andExpect(status().isOk());

        // Validate the BodyFatPercentage in the database
        List<BodyFatPercentage> bodyFatPercentageList = bodyFatPercentageRepository.findAll();
        assertThat(bodyFatPercentageList).hasSize(databaseSizeBeforeUpdate);
        BodyFatPercentage testBodyFatPercentage = bodyFatPercentageList.get(bodyFatPercentageList.size() - 1);
        assertThat(testBodyFatPercentage.getUsuarioId()).isEqualTo(UPDATED_USUARIO_ID);
        assertThat(testBodyFatPercentage.getEmpresaId()).isEqualTo(UPDATED_EMPRESA_ID);
        assertThat(testBodyFatPercentage.getFieldPorcentage()).isEqualTo(UPDATED_FIELD_PORCENTAGE);
        assertThat(testBodyFatPercentage.getEndTime()).isEqualTo(UPDATED_END_TIME);
    }

    @Test
    @Transactional
    void patchNonExistingBodyFatPercentage() throws Exception {
        int databaseSizeBeforeUpdate = bodyFatPercentageRepository.findAll().size();
        bodyFatPercentage.setId(UUID.randomUUID());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBodyFatPercentageMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, bodyFatPercentage.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(bodyFatPercentage))
            )
            .andExpect(status().isBadRequest());

        // Validate the BodyFatPercentage in the database
        List<BodyFatPercentage> bodyFatPercentageList = bodyFatPercentageRepository.findAll();
        assertThat(bodyFatPercentageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchBodyFatPercentage() throws Exception {
        int databaseSizeBeforeUpdate = bodyFatPercentageRepository.findAll().size();
        bodyFatPercentage.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBodyFatPercentageMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(bodyFatPercentage))
            )
            .andExpect(status().isBadRequest());

        // Validate the BodyFatPercentage in the database
        List<BodyFatPercentage> bodyFatPercentageList = bodyFatPercentageRepository.findAll();
        assertThat(bodyFatPercentageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamBodyFatPercentage() throws Exception {
        int databaseSizeBeforeUpdate = bodyFatPercentageRepository.findAll().size();
        bodyFatPercentage.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBodyFatPercentageMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(bodyFatPercentage))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the BodyFatPercentage in the database
        List<BodyFatPercentage> bodyFatPercentageList = bodyFatPercentageRepository.findAll();
        assertThat(bodyFatPercentageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteBodyFatPercentage() throws Exception {
        // Initialize the database
        bodyFatPercentageRepository.saveAndFlush(bodyFatPercentage);

        int databaseSizeBeforeDelete = bodyFatPercentageRepository.findAll().size();

        // Delete the bodyFatPercentage
        restBodyFatPercentageMockMvc
            .perform(delete(ENTITY_API_URL_ID, bodyFatPercentage.getId().toString()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<BodyFatPercentage> bodyFatPercentageList = bodyFatPercentageRepository.findAll();
        assertThat(bodyFatPercentageList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
