package com.be4tech.b4carecollect.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.be4tech.b4carecollect.IntegrationTest;
import com.be4tech.b4carecollect.domain.Speed;
import com.be4tech.b4carecollect.repository.SpeedRepository;
import com.be4tech.b4carecollect.service.criteria.SpeedCriteria;
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
 * Integration tests for the {@link SpeedResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SpeedResourceIT {

    private static final String DEFAULT_USUARIO_ID = "AAAAAAAAAA";
    private static final String UPDATED_USUARIO_ID = "BBBBBBBBBB";

    private static final String DEFAULT_EMPRESA_ID = "AAAAAAAAAA";
    private static final String UPDATED_EMPRESA_ID = "BBBBBBBBBB";

    private static final Float DEFAULT_SPEED = 1F;
    private static final Float UPDATED_SPEED = 2F;
    private static final Float SMALLER_SPEED = 1F - 1F;

    private static final Instant DEFAULT_END_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_END_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/speeds";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private SpeedRepository speedRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSpeedMockMvc;

    private Speed speed;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Speed createEntity(EntityManager em) {
        Speed speed = new Speed()
            .usuarioId(DEFAULT_USUARIO_ID)
            .empresaId(DEFAULT_EMPRESA_ID)
            .speed(DEFAULT_SPEED)
            .endTime(DEFAULT_END_TIME);
        return speed;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Speed createUpdatedEntity(EntityManager em) {
        Speed speed = new Speed()
            .usuarioId(UPDATED_USUARIO_ID)
            .empresaId(UPDATED_EMPRESA_ID)
            .speed(UPDATED_SPEED)
            .endTime(UPDATED_END_TIME);
        return speed;
    }

    @BeforeEach
    public void initTest() {
        speed = createEntity(em);
    }

    @Test
    @Transactional
    void createSpeed() throws Exception {
        int databaseSizeBeforeCreate = speedRepository.findAll().size();
        // Create the Speed
        restSpeedMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(speed)))
            .andExpect(status().isCreated());

        // Validate the Speed in the database
        List<Speed> speedList = speedRepository.findAll();
        assertThat(speedList).hasSize(databaseSizeBeforeCreate + 1);
        Speed testSpeed = speedList.get(speedList.size() - 1);
        assertThat(testSpeed.getUsuarioId()).isEqualTo(DEFAULT_USUARIO_ID);
        assertThat(testSpeed.getEmpresaId()).isEqualTo(DEFAULT_EMPRESA_ID);
        assertThat(testSpeed.getSpeed()).isEqualTo(DEFAULT_SPEED);
        assertThat(testSpeed.getEndTime()).isEqualTo(DEFAULT_END_TIME);
    }

    @Test
    @Transactional
    void createSpeedWithExistingId() throws Exception {
        // Create the Speed with an existing ID
        speedRepository.saveAndFlush(speed);

        int databaseSizeBeforeCreate = speedRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSpeedMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(speed)))
            .andExpect(status().isBadRequest());

        // Validate the Speed in the database
        List<Speed> speedList = speedRepository.findAll();
        assertThat(speedList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllSpeeds() throws Exception {
        // Initialize the database
        speedRepository.saveAndFlush(speed);

        // Get all the speedList
        restSpeedMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(speed.getId().toString())))
            .andExpect(jsonPath("$.[*].usuarioId").value(hasItem(DEFAULT_USUARIO_ID)))
            .andExpect(jsonPath("$.[*].empresaId").value(hasItem(DEFAULT_EMPRESA_ID)))
            .andExpect(jsonPath("$.[*].speed").value(hasItem(DEFAULT_SPEED.doubleValue())))
            .andExpect(jsonPath("$.[*].endTime").value(hasItem(DEFAULT_END_TIME.toString())));
    }

    @Test
    @Transactional
    void getSpeed() throws Exception {
        // Initialize the database
        speedRepository.saveAndFlush(speed);

        // Get the speed
        restSpeedMockMvc
            .perform(get(ENTITY_API_URL_ID, speed.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(speed.getId().toString()))
            .andExpect(jsonPath("$.usuarioId").value(DEFAULT_USUARIO_ID))
            .andExpect(jsonPath("$.empresaId").value(DEFAULT_EMPRESA_ID))
            .andExpect(jsonPath("$.speed").value(DEFAULT_SPEED.doubleValue()))
            .andExpect(jsonPath("$.endTime").value(DEFAULT_END_TIME.toString()));
    }

    @Test
    @Transactional
    void getSpeedsByIdFiltering() throws Exception {
        // Initialize the database
        speedRepository.saveAndFlush(speed);

        UUID id = speed.getId();

        defaultSpeedShouldBeFound("id.equals=" + id);
        defaultSpeedShouldNotBeFound("id.notEquals=" + id);
    }

    @Test
    @Transactional
    void getAllSpeedsByUsuarioIdIsEqualToSomething() throws Exception {
        // Initialize the database
        speedRepository.saveAndFlush(speed);

        // Get all the speedList where usuarioId equals to DEFAULT_USUARIO_ID
        defaultSpeedShouldBeFound("usuarioId.equals=" + DEFAULT_USUARIO_ID);

        // Get all the speedList where usuarioId equals to UPDATED_USUARIO_ID
        defaultSpeedShouldNotBeFound("usuarioId.equals=" + UPDATED_USUARIO_ID);
    }

    @Test
    @Transactional
    void getAllSpeedsByUsuarioIdIsInShouldWork() throws Exception {
        // Initialize the database
        speedRepository.saveAndFlush(speed);

        // Get all the speedList where usuarioId in DEFAULT_USUARIO_ID or UPDATED_USUARIO_ID
        defaultSpeedShouldBeFound("usuarioId.in=" + DEFAULT_USUARIO_ID + "," + UPDATED_USUARIO_ID);

        // Get all the speedList where usuarioId equals to UPDATED_USUARIO_ID
        defaultSpeedShouldNotBeFound("usuarioId.in=" + UPDATED_USUARIO_ID);
    }

    @Test
    @Transactional
    void getAllSpeedsByUsuarioIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        speedRepository.saveAndFlush(speed);

        // Get all the speedList where usuarioId is not null
        defaultSpeedShouldBeFound("usuarioId.specified=true");

        // Get all the speedList where usuarioId is null
        defaultSpeedShouldNotBeFound("usuarioId.specified=false");
    }

    @Test
    @Transactional
    void getAllSpeedsByUsuarioIdContainsSomething() throws Exception {
        // Initialize the database
        speedRepository.saveAndFlush(speed);

        // Get all the speedList where usuarioId contains DEFAULT_USUARIO_ID
        defaultSpeedShouldBeFound("usuarioId.contains=" + DEFAULT_USUARIO_ID);

        // Get all the speedList where usuarioId contains UPDATED_USUARIO_ID
        defaultSpeedShouldNotBeFound("usuarioId.contains=" + UPDATED_USUARIO_ID);
    }

    @Test
    @Transactional
    void getAllSpeedsByUsuarioIdNotContainsSomething() throws Exception {
        // Initialize the database
        speedRepository.saveAndFlush(speed);

        // Get all the speedList where usuarioId does not contain DEFAULT_USUARIO_ID
        defaultSpeedShouldNotBeFound("usuarioId.doesNotContain=" + DEFAULT_USUARIO_ID);

        // Get all the speedList where usuarioId does not contain UPDATED_USUARIO_ID
        defaultSpeedShouldBeFound("usuarioId.doesNotContain=" + UPDATED_USUARIO_ID);
    }

    @Test
    @Transactional
    void getAllSpeedsByEmpresaIdIsEqualToSomething() throws Exception {
        // Initialize the database
        speedRepository.saveAndFlush(speed);

        // Get all the speedList where empresaId equals to DEFAULT_EMPRESA_ID
        defaultSpeedShouldBeFound("empresaId.equals=" + DEFAULT_EMPRESA_ID);

        // Get all the speedList where empresaId equals to UPDATED_EMPRESA_ID
        defaultSpeedShouldNotBeFound("empresaId.equals=" + UPDATED_EMPRESA_ID);
    }

    @Test
    @Transactional
    void getAllSpeedsByEmpresaIdIsInShouldWork() throws Exception {
        // Initialize the database
        speedRepository.saveAndFlush(speed);

        // Get all the speedList where empresaId in DEFAULT_EMPRESA_ID or UPDATED_EMPRESA_ID
        defaultSpeedShouldBeFound("empresaId.in=" + DEFAULT_EMPRESA_ID + "," + UPDATED_EMPRESA_ID);

        // Get all the speedList where empresaId equals to UPDATED_EMPRESA_ID
        defaultSpeedShouldNotBeFound("empresaId.in=" + UPDATED_EMPRESA_ID);
    }

    @Test
    @Transactional
    void getAllSpeedsByEmpresaIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        speedRepository.saveAndFlush(speed);

        // Get all the speedList where empresaId is not null
        defaultSpeedShouldBeFound("empresaId.specified=true");

        // Get all the speedList where empresaId is null
        defaultSpeedShouldNotBeFound("empresaId.specified=false");
    }

    @Test
    @Transactional
    void getAllSpeedsByEmpresaIdContainsSomething() throws Exception {
        // Initialize the database
        speedRepository.saveAndFlush(speed);

        // Get all the speedList where empresaId contains DEFAULT_EMPRESA_ID
        defaultSpeedShouldBeFound("empresaId.contains=" + DEFAULT_EMPRESA_ID);

        // Get all the speedList where empresaId contains UPDATED_EMPRESA_ID
        defaultSpeedShouldNotBeFound("empresaId.contains=" + UPDATED_EMPRESA_ID);
    }

    @Test
    @Transactional
    void getAllSpeedsByEmpresaIdNotContainsSomething() throws Exception {
        // Initialize the database
        speedRepository.saveAndFlush(speed);

        // Get all the speedList where empresaId does not contain DEFAULT_EMPRESA_ID
        defaultSpeedShouldNotBeFound("empresaId.doesNotContain=" + DEFAULT_EMPRESA_ID);

        // Get all the speedList where empresaId does not contain UPDATED_EMPRESA_ID
        defaultSpeedShouldBeFound("empresaId.doesNotContain=" + UPDATED_EMPRESA_ID);
    }

    @Test
    @Transactional
    void getAllSpeedsBySpeedIsEqualToSomething() throws Exception {
        // Initialize the database
        speedRepository.saveAndFlush(speed);

        // Get all the speedList where speed equals to DEFAULT_SPEED
        defaultSpeedShouldBeFound("speed.equals=" + DEFAULT_SPEED);

        // Get all the speedList where speed equals to UPDATED_SPEED
        defaultSpeedShouldNotBeFound("speed.equals=" + UPDATED_SPEED);
    }

    @Test
    @Transactional
    void getAllSpeedsBySpeedIsInShouldWork() throws Exception {
        // Initialize the database
        speedRepository.saveAndFlush(speed);

        // Get all the speedList where speed in DEFAULT_SPEED or UPDATED_SPEED
        defaultSpeedShouldBeFound("speed.in=" + DEFAULT_SPEED + "," + UPDATED_SPEED);

        // Get all the speedList where speed equals to UPDATED_SPEED
        defaultSpeedShouldNotBeFound("speed.in=" + UPDATED_SPEED);
    }

    @Test
    @Transactional
    void getAllSpeedsBySpeedIsNullOrNotNull() throws Exception {
        // Initialize the database
        speedRepository.saveAndFlush(speed);

        // Get all the speedList where speed is not null
        defaultSpeedShouldBeFound("speed.specified=true");

        // Get all the speedList where speed is null
        defaultSpeedShouldNotBeFound("speed.specified=false");
    }

    @Test
    @Transactional
    void getAllSpeedsBySpeedIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        speedRepository.saveAndFlush(speed);

        // Get all the speedList where speed is greater than or equal to DEFAULT_SPEED
        defaultSpeedShouldBeFound("speed.greaterThanOrEqual=" + DEFAULT_SPEED);

        // Get all the speedList where speed is greater than or equal to UPDATED_SPEED
        defaultSpeedShouldNotBeFound("speed.greaterThanOrEqual=" + UPDATED_SPEED);
    }

    @Test
    @Transactional
    void getAllSpeedsBySpeedIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        speedRepository.saveAndFlush(speed);

        // Get all the speedList where speed is less than or equal to DEFAULT_SPEED
        defaultSpeedShouldBeFound("speed.lessThanOrEqual=" + DEFAULT_SPEED);

        // Get all the speedList where speed is less than or equal to SMALLER_SPEED
        defaultSpeedShouldNotBeFound("speed.lessThanOrEqual=" + SMALLER_SPEED);
    }

    @Test
    @Transactional
    void getAllSpeedsBySpeedIsLessThanSomething() throws Exception {
        // Initialize the database
        speedRepository.saveAndFlush(speed);

        // Get all the speedList where speed is less than DEFAULT_SPEED
        defaultSpeedShouldNotBeFound("speed.lessThan=" + DEFAULT_SPEED);

        // Get all the speedList where speed is less than UPDATED_SPEED
        defaultSpeedShouldBeFound("speed.lessThan=" + UPDATED_SPEED);
    }

    @Test
    @Transactional
    void getAllSpeedsBySpeedIsGreaterThanSomething() throws Exception {
        // Initialize the database
        speedRepository.saveAndFlush(speed);

        // Get all the speedList where speed is greater than DEFAULT_SPEED
        defaultSpeedShouldNotBeFound("speed.greaterThan=" + DEFAULT_SPEED);

        // Get all the speedList where speed is greater than SMALLER_SPEED
        defaultSpeedShouldBeFound("speed.greaterThan=" + SMALLER_SPEED);
    }

    @Test
    @Transactional
    void getAllSpeedsByEndTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        speedRepository.saveAndFlush(speed);

        // Get all the speedList where endTime equals to DEFAULT_END_TIME
        defaultSpeedShouldBeFound("endTime.equals=" + DEFAULT_END_TIME);

        // Get all the speedList where endTime equals to UPDATED_END_TIME
        defaultSpeedShouldNotBeFound("endTime.equals=" + UPDATED_END_TIME);
    }

    @Test
    @Transactional
    void getAllSpeedsByEndTimeIsInShouldWork() throws Exception {
        // Initialize the database
        speedRepository.saveAndFlush(speed);

        // Get all the speedList where endTime in DEFAULT_END_TIME or UPDATED_END_TIME
        defaultSpeedShouldBeFound("endTime.in=" + DEFAULT_END_TIME + "," + UPDATED_END_TIME);

        // Get all the speedList where endTime equals to UPDATED_END_TIME
        defaultSpeedShouldNotBeFound("endTime.in=" + UPDATED_END_TIME);
    }

    @Test
    @Transactional
    void getAllSpeedsByEndTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        speedRepository.saveAndFlush(speed);

        // Get all the speedList where endTime is not null
        defaultSpeedShouldBeFound("endTime.specified=true");

        // Get all the speedList where endTime is null
        defaultSpeedShouldNotBeFound("endTime.specified=false");
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultSpeedShouldBeFound(String filter) throws Exception {
        restSpeedMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(speed.getId().toString())))
            .andExpect(jsonPath("$.[*].usuarioId").value(hasItem(DEFAULT_USUARIO_ID)))
            .andExpect(jsonPath("$.[*].empresaId").value(hasItem(DEFAULT_EMPRESA_ID)))
            .andExpect(jsonPath("$.[*].speed").value(hasItem(DEFAULT_SPEED.doubleValue())))
            .andExpect(jsonPath("$.[*].endTime").value(hasItem(DEFAULT_END_TIME.toString())));

        // Check, that the count call also returns 1
        restSpeedMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultSpeedShouldNotBeFound(String filter) throws Exception {
        restSpeedMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restSpeedMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingSpeed() throws Exception {
        // Get the speed
        restSpeedMockMvc.perform(get(ENTITY_API_URL_ID, UUID.randomUUID().toString())).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingSpeed() throws Exception {
        // Initialize the database
        speedRepository.saveAndFlush(speed);

        int databaseSizeBeforeUpdate = speedRepository.findAll().size();

        // Update the speed
        Speed updatedSpeed = speedRepository.findById(speed.getId()).get();
        // Disconnect from session so that the updates on updatedSpeed are not directly saved in db
        em.detach(updatedSpeed);
        updatedSpeed.usuarioId(UPDATED_USUARIO_ID).empresaId(UPDATED_EMPRESA_ID).speed(UPDATED_SPEED).endTime(UPDATED_END_TIME);

        restSpeedMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedSpeed.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedSpeed))
            )
            .andExpect(status().isOk());

        // Validate the Speed in the database
        List<Speed> speedList = speedRepository.findAll();
        assertThat(speedList).hasSize(databaseSizeBeforeUpdate);
        Speed testSpeed = speedList.get(speedList.size() - 1);
        assertThat(testSpeed.getUsuarioId()).isEqualTo(UPDATED_USUARIO_ID);
        assertThat(testSpeed.getEmpresaId()).isEqualTo(UPDATED_EMPRESA_ID);
        assertThat(testSpeed.getSpeed()).isEqualTo(UPDATED_SPEED);
        assertThat(testSpeed.getEndTime()).isEqualTo(UPDATED_END_TIME);
    }

    @Test
    @Transactional
    void putNonExistingSpeed() throws Exception {
        int databaseSizeBeforeUpdate = speedRepository.findAll().size();
        speed.setId(UUID.randomUUID());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSpeedMockMvc
            .perform(
                put(ENTITY_API_URL_ID, speed.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(speed))
            )
            .andExpect(status().isBadRequest());

        // Validate the Speed in the database
        List<Speed> speedList = speedRepository.findAll();
        assertThat(speedList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSpeed() throws Exception {
        int databaseSizeBeforeUpdate = speedRepository.findAll().size();
        speed.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSpeedMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(speed))
            )
            .andExpect(status().isBadRequest());

        // Validate the Speed in the database
        List<Speed> speedList = speedRepository.findAll();
        assertThat(speedList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSpeed() throws Exception {
        int databaseSizeBeforeUpdate = speedRepository.findAll().size();
        speed.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSpeedMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(speed)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Speed in the database
        List<Speed> speedList = speedRepository.findAll();
        assertThat(speedList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSpeedWithPatch() throws Exception {
        // Initialize the database
        speedRepository.saveAndFlush(speed);

        int databaseSizeBeforeUpdate = speedRepository.findAll().size();

        // Update the speed using partial update
        Speed partialUpdatedSpeed = new Speed();
        partialUpdatedSpeed.setId(speed.getId());

        partialUpdatedSpeed.endTime(UPDATED_END_TIME);

        restSpeedMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSpeed.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSpeed))
            )
            .andExpect(status().isOk());

        // Validate the Speed in the database
        List<Speed> speedList = speedRepository.findAll();
        assertThat(speedList).hasSize(databaseSizeBeforeUpdate);
        Speed testSpeed = speedList.get(speedList.size() - 1);
        assertThat(testSpeed.getUsuarioId()).isEqualTo(DEFAULT_USUARIO_ID);
        assertThat(testSpeed.getEmpresaId()).isEqualTo(DEFAULT_EMPRESA_ID);
        assertThat(testSpeed.getSpeed()).isEqualTo(DEFAULT_SPEED);
        assertThat(testSpeed.getEndTime()).isEqualTo(UPDATED_END_TIME);
    }

    @Test
    @Transactional
    void fullUpdateSpeedWithPatch() throws Exception {
        // Initialize the database
        speedRepository.saveAndFlush(speed);

        int databaseSizeBeforeUpdate = speedRepository.findAll().size();

        // Update the speed using partial update
        Speed partialUpdatedSpeed = new Speed();
        partialUpdatedSpeed.setId(speed.getId());

        partialUpdatedSpeed.usuarioId(UPDATED_USUARIO_ID).empresaId(UPDATED_EMPRESA_ID).speed(UPDATED_SPEED).endTime(UPDATED_END_TIME);

        restSpeedMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSpeed.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSpeed))
            )
            .andExpect(status().isOk());

        // Validate the Speed in the database
        List<Speed> speedList = speedRepository.findAll();
        assertThat(speedList).hasSize(databaseSizeBeforeUpdate);
        Speed testSpeed = speedList.get(speedList.size() - 1);
        assertThat(testSpeed.getUsuarioId()).isEqualTo(UPDATED_USUARIO_ID);
        assertThat(testSpeed.getEmpresaId()).isEqualTo(UPDATED_EMPRESA_ID);
        assertThat(testSpeed.getSpeed()).isEqualTo(UPDATED_SPEED);
        assertThat(testSpeed.getEndTime()).isEqualTo(UPDATED_END_TIME);
    }

    @Test
    @Transactional
    void patchNonExistingSpeed() throws Exception {
        int databaseSizeBeforeUpdate = speedRepository.findAll().size();
        speed.setId(UUID.randomUUID());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSpeedMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, speed.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(speed))
            )
            .andExpect(status().isBadRequest());

        // Validate the Speed in the database
        List<Speed> speedList = speedRepository.findAll();
        assertThat(speedList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSpeed() throws Exception {
        int databaseSizeBeforeUpdate = speedRepository.findAll().size();
        speed.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSpeedMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(speed))
            )
            .andExpect(status().isBadRequest());

        // Validate the Speed in the database
        List<Speed> speedList = speedRepository.findAll();
        assertThat(speedList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSpeed() throws Exception {
        int databaseSizeBeforeUpdate = speedRepository.findAll().size();
        speed.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSpeedMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(speed)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Speed in the database
        List<Speed> speedList = speedRepository.findAll();
        assertThat(speedList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSpeed() throws Exception {
        // Initialize the database
        speedRepository.saveAndFlush(speed);

        int databaseSizeBeforeDelete = speedRepository.findAll().size();

        // Delete the speed
        restSpeedMockMvc
            .perform(delete(ENTITY_API_URL_ID, speed.getId().toString()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Speed> speedList = speedRepository.findAll();
        assertThat(speedList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
