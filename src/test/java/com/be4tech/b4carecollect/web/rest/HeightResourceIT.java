package com.be4tech.b4carecollect.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.be4tech.b4carecollect.IntegrationTest;
import com.be4tech.b4carecollect.domain.Height;
import com.be4tech.b4carecollect.repository.HeightRepository;
import com.be4tech.b4carecollect.service.criteria.HeightCriteria;
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
 * Integration tests for the {@link HeightResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class HeightResourceIT {

    private static final String DEFAULT_USUARIO_ID = "AAAAAAAAAA";
    private static final String UPDATED_USUARIO_ID = "BBBBBBBBBB";

    private static final String DEFAULT_EMPRESA_ID = "AAAAAAAAAA";
    private static final String UPDATED_EMPRESA_ID = "BBBBBBBBBB";

    private static final Float DEFAULT_FIELD_HEIGHT = 1F;
    private static final Float UPDATED_FIELD_HEIGHT = 2F;
    private static final Float SMALLER_FIELD_HEIGHT = 1F - 1F;

    private static final Instant DEFAULT_END_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_END_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/heights";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private HeightRepository heightRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restHeightMockMvc;

    private Height height;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Height createEntity(EntityManager em) {
        Height height = new Height()
            .usuarioId(DEFAULT_USUARIO_ID)
            .empresaId(DEFAULT_EMPRESA_ID)
            .fieldHeight(DEFAULT_FIELD_HEIGHT)
            .endTime(DEFAULT_END_TIME);
        return height;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Height createUpdatedEntity(EntityManager em) {
        Height height = new Height()
            .usuarioId(UPDATED_USUARIO_ID)
            .empresaId(UPDATED_EMPRESA_ID)
            .fieldHeight(UPDATED_FIELD_HEIGHT)
            .endTime(UPDATED_END_TIME);
        return height;
    }

    @BeforeEach
    public void initTest() {
        height = createEntity(em);
    }

    @Test
    @Transactional
    void createHeight() throws Exception {
        int databaseSizeBeforeCreate = heightRepository.findAll().size();
        // Create the Height
        restHeightMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(height)))
            .andExpect(status().isCreated());

        // Validate the Height in the database
        List<Height> heightList = heightRepository.findAll();
        assertThat(heightList).hasSize(databaseSizeBeforeCreate + 1);
        Height testHeight = heightList.get(heightList.size() - 1);
        assertThat(testHeight.getUsuarioId()).isEqualTo(DEFAULT_USUARIO_ID);
        assertThat(testHeight.getEmpresaId()).isEqualTo(DEFAULT_EMPRESA_ID);
        assertThat(testHeight.getFieldHeight()).isEqualTo(DEFAULT_FIELD_HEIGHT);
        assertThat(testHeight.getEndTime()).isEqualTo(DEFAULT_END_TIME);
    }

    @Test
    @Transactional
    void createHeightWithExistingId() throws Exception {
        // Create the Height with an existing ID
        heightRepository.saveAndFlush(height);

        int databaseSizeBeforeCreate = heightRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restHeightMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(height)))
            .andExpect(status().isBadRequest());

        // Validate the Height in the database
        List<Height> heightList = heightRepository.findAll();
        assertThat(heightList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllHeights() throws Exception {
        // Initialize the database
        heightRepository.saveAndFlush(height);

        // Get all the heightList
        restHeightMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(height.getId().toString())))
            .andExpect(jsonPath("$.[*].usuarioId").value(hasItem(DEFAULT_USUARIO_ID)))
            .andExpect(jsonPath("$.[*].empresaId").value(hasItem(DEFAULT_EMPRESA_ID)))
            .andExpect(jsonPath("$.[*].fieldHeight").value(hasItem(DEFAULT_FIELD_HEIGHT.doubleValue())))
            .andExpect(jsonPath("$.[*].endTime").value(hasItem(DEFAULT_END_TIME.toString())));
    }

    @Test
    @Transactional
    void getHeight() throws Exception {
        // Initialize the database
        heightRepository.saveAndFlush(height);

        // Get the height
        restHeightMockMvc
            .perform(get(ENTITY_API_URL_ID, height.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(height.getId().toString()))
            .andExpect(jsonPath("$.usuarioId").value(DEFAULT_USUARIO_ID))
            .andExpect(jsonPath("$.empresaId").value(DEFAULT_EMPRESA_ID))
            .andExpect(jsonPath("$.fieldHeight").value(DEFAULT_FIELD_HEIGHT.doubleValue()))
            .andExpect(jsonPath("$.endTime").value(DEFAULT_END_TIME.toString()));
    }

    @Test
    @Transactional
    void getHeightsByIdFiltering() throws Exception {
        // Initialize the database
        heightRepository.saveAndFlush(height);

        UUID id = height.getId();

        defaultHeightShouldBeFound("id.equals=" + id);
        defaultHeightShouldNotBeFound("id.notEquals=" + id);
    }

    @Test
    @Transactional
    void getAllHeightsByUsuarioIdIsEqualToSomething() throws Exception {
        // Initialize the database
        heightRepository.saveAndFlush(height);

        // Get all the heightList where usuarioId equals to DEFAULT_USUARIO_ID
        defaultHeightShouldBeFound("usuarioId.equals=" + DEFAULT_USUARIO_ID);

        // Get all the heightList where usuarioId equals to UPDATED_USUARIO_ID
        defaultHeightShouldNotBeFound("usuarioId.equals=" + UPDATED_USUARIO_ID);
    }

    @Test
    @Transactional
    void getAllHeightsByUsuarioIdIsInShouldWork() throws Exception {
        // Initialize the database
        heightRepository.saveAndFlush(height);

        // Get all the heightList where usuarioId in DEFAULT_USUARIO_ID or UPDATED_USUARIO_ID
        defaultHeightShouldBeFound("usuarioId.in=" + DEFAULT_USUARIO_ID + "," + UPDATED_USUARIO_ID);

        // Get all the heightList where usuarioId equals to UPDATED_USUARIO_ID
        defaultHeightShouldNotBeFound("usuarioId.in=" + UPDATED_USUARIO_ID);
    }

    @Test
    @Transactional
    void getAllHeightsByUsuarioIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        heightRepository.saveAndFlush(height);

        // Get all the heightList where usuarioId is not null
        defaultHeightShouldBeFound("usuarioId.specified=true");

        // Get all the heightList where usuarioId is null
        defaultHeightShouldNotBeFound("usuarioId.specified=false");
    }

    @Test
    @Transactional
    void getAllHeightsByUsuarioIdContainsSomething() throws Exception {
        // Initialize the database
        heightRepository.saveAndFlush(height);

        // Get all the heightList where usuarioId contains DEFAULT_USUARIO_ID
        defaultHeightShouldBeFound("usuarioId.contains=" + DEFAULT_USUARIO_ID);

        // Get all the heightList where usuarioId contains UPDATED_USUARIO_ID
        defaultHeightShouldNotBeFound("usuarioId.contains=" + UPDATED_USUARIO_ID);
    }

    @Test
    @Transactional
    void getAllHeightsByUsuarioIdNotContainsSomething() throws Exception {
        // Initialize the database
        heightRepository.saveAndFlush(height);

        // Get all the heightList where usuarioId does not contain DEFAULT_USUARIO_ID
        defaultHeightShouldNotBeFound("usuarioId.doesNotContain=" + DEFAULT_USUARIO_ID);

        // Get all the heightList where usuarioId does not contain UPDATED_USUARIO_ID
        defaultHeightShouldBeFound("usuarioId.doesNotContain=" + UPDATED_USUARIO_ID);
    }

    @Test
    @Transactional
    void getAllHeightsByEmpresaIdIsEqualToSomething() throws Exception {
        // Initialize the database
        heightRepository.saveAndFlush(height);

        // Get all the heightList where empresaId equals to DEFAULT_EMPRESA_ID
        defaultHeightShouldBeFound("empresaId.equals=" + DEFAULT_EMPRESA_ID);

        // Get all the heightList where empresaId equals to UPDATED_EMPRESA_ID
        defaultHeightShouldNotBeFound("empresaId.equals=" + UPDATED_EMPRESA_ID);
    }

    @Test
    @Transactional
    void getAllHeightsByEmpresaIdIsInShouldWork() throws Exception {
        // Initialize the database
        heightRepository.saveAndFlush(height);

        // Get all the heightList where empresaId in DEFAULT_EMPRESA_ID or UPDATED_EMPRESA_ID
        defaultHeightShouldBeFound("empresaId.in=" + DEFAULT_EMPRESA_ID + "," + UPDATED_EMPRESA_ID);

        // Get all the heightList where empresaId equals to UPDATED_EMPRESA_ID
        defaultHeightShouldNotBeFound("empresaId.in=" + UPDATED_EMPRESA_ID);
    }

    @Test
    @Transactional
    void getAllHeightsByEmpresaIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        heightRepository.saveAndFlush(height);

        // Get all the heightList where empresaId is not null
        defaultHeightShouldBeFound("empresaId.specified=true");

        // Get all the heightList where empresaId is null
        defaultHeightShouldNotBeFound("empresaId.specified=false");
    }

    @Test
    @Transactional
    void getAllHeightsByEmpresaIdContainsSomething() throws Exception {
        // Initialize the database
        heightRepository.saveAndFlush(height);

        // Get all the heightList where empresaId contains DEFAULT_EMPRESA_ID
        defaultHeightShouldBeFound("empresaId.contains=" + DEFAULT_EMPRESA_ID);

        // Get all the heightList where empresaId contains UPDATED_EMPRESA_ID
        defaultHeightShouldNotBeFound("empresaId.contains=" + UPDATED_EMPRESA_ID);
    }

    @Test
    @Transactional
    void getAllHeightsByEmpresaIdNotContainsSomething() throws Exception {
        // Initialize the database
        heightRepository.saveAndFlush(height);

        // Get all the heightList where empresaId does not contain DEFAULT_EMPRESA_ID
        defaultHeightShouldNotBeFound("empresaId.doesNotContain=" + DEFAULT_EMPRESA_ID);

        // Get all the heightList where empresaId does not contain UPDATED_EMPRESA_ID
        defaultHeightShouldBeFound("empresaId.doesNotContain=" + UPDATED_EMPRESA_ID);
    }

    @Test
    @Transactional
    void getAllHeightsByFieldHeightIsEqualToSomething() throws Exception {
        // Initialize the database
        heightRepository.saveAndFlush(height);

        // Get all the heightList where fieldHeight equals to DEFAULT_FIELD_HEIGHT
        defaultHeightShouldBeFound("fieldHeight.equals=" + DEFAULT_FIELD_HEIGHT);

        // Get all the heightList where fieldHeight equals to UPDATED_FIELD_HEIGHT
        defaultHeightShouldNotBeFound("fieldHeight.equals=" + UPDATED_FIELD_HEIGHT);
    }

    @Test
    @Transactional
    void getAllHeightsByFieldHeightIsInShouldWork() throws Exception {
        // Initialize the database
        heightRepository.saveAndFlush(height);

        // Get all the heightList where fieldHeight in DEFAULT_FIELD_HEIGHT or UPDATED_FIELD_HEIGHT
        defaultHeightShouldBeFound("fieldHeight.in=" + DEFAULT_FIELD_HEIGHT + "," + UPDATED_FIELD_HEIGHT);

        // Get all the heightList where fieldHeight equals to UPDATED_FIELD_HEIGHT
        defaultHeightShouldNotBeFound("fieldHeight.in=" + UPDATED_FIELD_HEIGHT);
    }

    @Test
    @Transactional
    void getAllHeightsByFieldHeightIsNullOrNotNull() throws Exception {
        // Initialize the database
        heightRepository.saveAndFlush(height);

        // Get all the heightList where fieldHeight is not null
        defaultHeightShouldBeFound("fieldHeight.specified=true");

        // Get all the heightList where fieldHeight is null
        defaultHeightShouldNotBeFound("fieldHeight.specified=false");
    }

    @Test
    @Transactional
    void getAllHeightsByFieldHeightIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        heightRepository.saveAndFlush(height);

        // Get all the heightList where fieldHeight is greater than or equal to DEFAULT_FIELD_HEIGHT
        defaultHeightShouldBeFound("fieldHeight.greaterThanOrEqual=" + DEFAULT_FIELD_HEIGHT);

        // Get all the heightList where fieldHeight is greater than or equal to UPDATED_FIELD_HEIGHT
        defaultHeightShouldNotBeFound("fieldHeight.greaterThanOrEqual=" + UPDATED_FIELD_HEIGHT);
    }

    @Test
    @Transactional
    void getAllHeightsByFieldHeightIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        heightRepository.saveAndFlush(height);

        // Get all the heightList where fieldHeight is less than or equal to DEFAULT_FIELD_HEIGHT
        defaultHeightShouldBeFound("fieldHeight.lessThanOrEqual=" + DEFAULT_FIELD_HEIGHT);

        // Get all the heightList where fieldHeight is less than or equal to SMALLER_FIELD_HEIGHT
        defaultHeightShouldNotBeFound("fieldHeight.lessThanOrEqual=" + SMALLER_FIELD_HEIGHT);
    }

    @Test
    @Transactional
    void getAllHeightsByFieldHeightIsLessThanSomething() throws Exception {
        // Initialize the database
        heightRepository.saveAndFlush(height);

        // Get all the heightList where fieldHeight is less than DEFAULT_FIELD_HEIGHT
        defaultHeightShouldNotBeFound("fieldHeight.lessThan=" + DEFAULT_FIELD_HEIGHT);

        // Get all the heightList where fieldHeight is less than UPDATED_FIELD_HEIGHT
        defaultHeightShouldBeFound("fieldHeight.lessThan=" + UPDATED_FIELD_HEIGHT);
    }

    @Test
    @Transactional
    void getAllHeightsByFieldHeightIsGreaterThanSomething() throws Exception {
        // Initialize the database
        heightRepository.saveAndFlush(height);

        // Get all the heightList where fieldHeight is greater than DEFAULT_FIELD_HEIGHT
        defaultHeightShouldNotBeFound("fieldHeight.greaterThan=" + DEFAULT_FIELD_HEIGHT);

        // Get all the heightList where fieldHeight is greater than SMALLER_FIELD_HEIGHT
        defaultHeightShouldBeFound("fieldHeight.greaterThan=" + SMALLER_FIELD_HEIGHT);
    }

    @Test
    @Transactional
    void getAllHeightsByEndTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        heightRepository.saveAndFlush(height);

        // Get all the heightList where endTime equals to DEFAULT_END_TIME
        defaultHeightShouldBeFound("endTime.equals=" + DEFAULT_END_TIME);

        // Get all the heightList where endTime equals to UPDATED_END_TIME
        defaultHeightShouldNotBeFound("endTime.equals=" + UPDATED_END_TIME);
    }

    @Test
    @Transactional
    void getAllHeightsByEndTimeIsInShouldWork() throws Exception {
        // Initialize the database
        heightRepository.saveAndFlush(height);

        // Get all the heightList where endTime in DEFAULT_END_TIME or UPDATED_END_TIME
        defaultHeightShouldBeFound("endTime.in=" + DEFAULT_END_TIME + "," + UPDATED_END_TIME);

        // Get all the heightList where endTime equals to UPDATED_END_TIME
        defaultHeightShouldNotBeFound("endTime.in=" + UPDATED_END_TIME);
    }

    @Test
    @Transactional
    void getAllHeightsByEndTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        heightRepository.saveAndFlush(height);

        // Get all the heightList where endTime is not null
        defaultHeightShouldBeFound("endTime.specified=true");

        // Get all the heightList where endTime is null
        defaultHeightShouldNotBeFound("endTime.specified=false");
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultHeightShouldBeFound(String filter) throws Exception {
        restHeightMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(height.getId().toString())))
            .andExpect(jsonPath("$.[*].usuarioId").value(hasItem(DEFAULT_USUARIO_ID)))
            .andExpect(jsonPath("$.[*].empresaId").value(hasItem(DEFAULT_EMPRESA_ID)))
            .andExpect(jsonPath("$.[*].fieldHeight").value(hasItem(DEFAULT_FIELD_HEIGHT.doubleValue())))
            .andExpect(jsonPath("$.[*].endTime").value(hasItem(DEFAULT_END_TIME.toString())));

        // Check, that the count call also returns 1
        restHeightMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultHeightShouldNotBeFound(String filter) throws Exception {
        restHeightMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restHeightMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingHeight() throws Exception {
        // Get the height
        restHeightMockMvc.perform(get(ENTITY_API_URL_ID, UUID.randomUUID().toString())).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingHeight() throws Exception {
        // Initialize the database
        heightRepository.saveAndFlush(height);

        int databaseSizeBeforeUpdate = heightRepository.findAll().size();

        // Update the height
        Height updatedHeight = heightRepository.findById(height.getId()).get();
        // Disconnect from session so that the updates on updatedHeight are not directly saved in db
        em.detach(updatedHeight);
        updatedHeight
            .usuarioId(UPDATED_USUARIO_ID)
            .empresaId(UPDATED_EMPRESA_ID)
            .fieldHeight(UPDATED_FIELD_HEIGHT)
            .endTime(UPDATED_END_TIME);

        restHeightMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedHeight.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedHeight))
            )
            .andExpect(status().isOk());

        // Validate the Height in the database
        List<Height> heightList = heightRepository.findAll();
        assertThat(heightList).hasSize(databaseSizeBeforeUpdate);
        Height testHeight = heightList.get(heightList.size() - 1);
        assertThat(testHeight.getUsuarioId()).isEqualTo(UPDATED_USUARIO_ID);
        assertThat(testHeight.getEmpresaId()).isEqualTo(UPDATED_EMPRESA_ID);
        assertThat(testHeight.getFieldHeight()).isEqualTo(UPDATED_FIELD_HEIGHT);
        assertThat(testHeight.getEndTime()).isEqualTo(UPDATED_END_TIME);
    }

    @Test
    @Transactional
    void putNonExistingHeight() throws Exception {
        int databaseSizeBeforeUpdate = heightRepository.findAll().size();
        height.setId(UUID.randomUUID());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHeightMockMvc
            .perform(
                put(ENTITY_API_URL_ID, height.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(height))
            )
            .andExpect(status().isBadRequest());

        // Validate the Height in the database
        List<Height> heightList = heightRepository.findAll();
        assertThat(heightList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchHeight() throws Exception {
        int databaseSizeBeforeUpdate = heightRepository.findAll().size();
        height.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHeightMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(height))
            )
            .andExpect(status().isBadRequest());

        // Validate the Height in the database
        List<Height> heightList = heightRepository.findAll();
        assertThat(heightList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamHeight() throws Exception {
        int databaseSizeBeforeUpdate = heightRepository.findAll().size();
        height.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHeightMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(height)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Height in the database
        List<Height> heightList = heightRepository.findAll();
        assertThat(heightList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateHeightWithPatch() throws Exception {
        // Initialize the database
        heightRepository.saveAndFlush(height);

        int databaseSizeBeforeUpdate = heightRepository.findAll().size();

        // Update the height using partial update
        Height partialUpdatedHeight = new Height();
        partialUpdatedHeight.setId(height.getId());

        partialUpdatedHeight.endTime(UPDATED_END_TIME);

        restHeightMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedHeight.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedHeight))
            )
            .andExpect(status().isOk());

        // Validate the Height in the database
        List<Height> heightList = heightRepository.findAll();
        assertThat(heightList).hasSize(databaseSizeBeforeUpdate);
        Height testHeight = heightList.get(heightList.size() - 1);
        assertThat(testHeight.getUsuarioId()).isEqualTo(DEFAULT_USUARIO_ID);
        assertThat(testHeight.getEmpresaId()).isEqualTo(DEFAULT_EMPRESA_ID);
        assertThat(testHeight.getFieldHeight()).isEqualTo(DEFAULT_FIELD_HEIGHT);
        assertThat(testHeight.getEndTime()).isEqualTo(UPDATED_END_TIME);
    }

    @Test
    @Transactional
    void fullUpdateHeightWithPatch() throws Exception {
        // Initialize the database
        heightRepository.saveAndFlush(height);

        int databaseSizeBeforeUpdate = heightRepository.findAll().size();

        // Update the height using partial update
        Height partialUpdatedHeight = new Height();
        partialUpdatedHeight.setId(height.getId());

        partialUpdatedHeight
            .usuarioId(UPDATED_USUARIO_ID)
            .empresaId(UPDATED_EMPRESA_ID)
            .fieldHeight(UPDATED_FIELD_HEIGHT)
            .endTime(UPDATED_END_TIME);

        restHeightMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedHeight.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedHeight))
            )
            .andExpect(status().isOk());

        // Validate the Height in the database
        List<Height> heightList = heightRepository.findAll();
        assertThat(heightList).hasSize(databaseSizeBeforeUpdate);
        Height testHeight = heightList.get(heightList.size() - 1);
        assertThat(testHeight.getUsuarioId()).isEqualTo(UPDATED_USUARIO_ID);
        assertThat(testHeight.getEmpresaId()).isEqualTo(UPDATED_EMPRESA_ID);
        assertThat(testHeight.getFieldHeight()).isEqualTo(UPDATED_FIELD_HEIGHT);
        assertThat(testHeight.getEndTime()).isEqualTo(UPDATED_END_TIME);
    }

    @Test
    @Transactional
    void patchNonExistingHeight() throws Exception {
        int databaseSizeBeforeUpdate = heightRepository.findAll().size();
        height.setId(UUID.randomUUID());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHeightMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, height.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(height))
            )
            .andExpect(status().isBadRequest());

        // Validate the Height in the database
        List<Height> heightList = heightRepository.findAll();
        assertThat(heightList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchHeight() throws Exception {
        int databaseSizeBeforeUpdate = heightRepository.findAll().size();
        height.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHeightMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(height))
            )
            .andExpect(status().isBadRequest());

        // Validate the Height in the database
        List<Height> heightList = heightRepository.findAll();
        assertThat(heightList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamHeight() throws Exception {
        int databaseSizeBeforeUpdate = heightRepository.findAll().size();
        height.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHeightMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(height)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Height in the database
        List<Height> heightList = heightRepository.findAll();
        assertThat(heightList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteHeight() throws Exception {
        // Initialize the database
        heightRepository.saveAndFlush(height);

        int databaseSizeBeforeDelete = heightRepository.findAll().size();

        // Delete the height
        restHeightMockMvc
            .perform(delete(ENTITY_API_URL_ID, height.getId().toString()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Height> heightList = heightRepository.findAll();
        assertThat(heightList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
