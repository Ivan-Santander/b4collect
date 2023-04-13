package com.be4tech.b4carecollect.web.rest;

import com.be4tech.b4carecollect.domain.StepCountCadence;
import com.be4tech.b4carecollect.repository.StepCountCadenceRepository;
import com.be4tech.b4carecollect.service.StepCountCadenceQueryService;
import com.be4tech.b4carecollect.service.StepCountCadenceService;
import com.be4tech.b4carecollect.service.criteria.StepCountCadenceCriteria;
import com.be4tech.b4carecollect.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.be4tech.b4carecollect.domain.StepCountCadence}.
 */
@RestController
@RequestMapping("/api")
public class StepCountCadenceResource {

    private final Logger log = LoggerFactory.getLogger(StepCountCadenceResource.class);

    private static final String ENTITY_NAME = "stepCountCadence";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final StepCountCadenceService stepCountCadenceService;

    private final StepCountCadenceRepository stepCountCadenceRepository;

    private final StepCountCadenceQueryService stepCountCadenceQueryService;

    public StepCountCadenceResource(
        StepCountCadenceService stepCountCadenceService,
        StepCountCadenceRepository stepCountCadenceRepository,
        StepCountCadenceQueryService stepCountCadenceQueryService
    ) {
        this.stepCountCadenceService = stepCountCadenceService;
        this.stepCountCadenceRepository = stepCountCadenceRepository;
        this.stepCountCadenceQueryService = stepCountCadenceQueryService;
    }

    /**
     * {@code POST  /step-count-cadences} : Create a new stepCountCadence.
     *
     * @param stepCountCadence the stepCountCadence to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new stepCountCadence, or with status {@code 400 (Bad Request)} if the stepCountCadence has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/step-count-cadences")
    public ResponseEntity<StepCountCadence> createStepCountCadence(@RequestBody StepCountCadence stepCountCadence)
        throws URISyntaxException {
        log.debug("REST request to save StepCountCadence : {}", stepCountCadence);
        if (stepCountCadence.getId() != null) {
            throw new BadRequestAlertException("A new stepCountCadence cannot already have an ID", ENTITY_NAME, "idexists");
        }
        StepCountCadence result = stepCountCadenceService.save(stepCountCadence);
        return ResponseEntity
            .created(new URI("/api/step-count-cadences/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /step-count-cadences/:id} : Updates an existing stepCountCadence.
     *
     * @param id the id of the stepCountCadence to save.
     * @param stepCountCadence the stepCountCadence to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated stepCountCadence,
     * or with status {@code 400 (Bad Request)} if the stepCountCadence is not valid,
     * or with status {@code 500 (Internal Server Error)} if the stepCountCadence couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/step-count-cadences/{id}")
    public ResponseEntity<StepCountCadence> updateStepCountCadence(
        @PathVariable(value = "id", required = false) final UUID id,
        @RequestBody StepCountCadence stepCountCadence
    ) throws URISyntaxException {
        log.debug("REST request to update StepCountCadence : {}, {}", id, stepCountCadence);
        if (stepCountCadence.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, stepCountCadence.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!stepCountCadenceRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        StepCountCadence result = stepCountCadenceService.update(stepCountCadence);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, stepCountCadence.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /step-count-cadences/:id} : Partial updates given fields of an existing stepCountCadence, field will ignore if it is null
     *
     * @param id the id of the stepCountCadence to save.
     * @param stepCountCadence the stepCountCadence to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated stepCountCadence,
     * or with status {@code 400 (Bad Request)} if the stepCountCadence is not valid,
     * or with status {@code 404 (Not Found)} if the stepCountCadence is not found,
     * or with status {@code 500 (Internal Server Error)} if the stepCountCadence couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/step-count-cadences/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<StepCountCadence> partialUpdateStepCountCadence(
        @PathVariable(value = "id", required = false) final UUID id,
        @RequestBody StepCountCadence stepCountCadence
    ) throws URISyntaxException {
        log.debug("REST request to partial update StepCountCadence partially : {}, {}", id, stepCountCadence);
        if (stepCountCadence.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, stepCountCadence.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!stepCountCadenceRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<StepCountCadence> result = stepCountCadenceService.partialUpdate(stepCountCadence);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, stepCountCadence.getId().toString())
        );
    }

    /**
     * {@code GET  /step-count-cadences} : get all the stepCountCadences.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of stepCountCadences in body.
     */
    @GetMapping("/step-count-cadences")
    public ResponseEntity<List<StepCountCadence>> getAllStepCountCadences(
        StepCountCadenceCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get StepCountCadences by criteria: {}", criteria);
        Page<StepCountCadence> page = stepCountCadenceQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /step-count-cadences/count} : count all the stepCountCadences.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/step-count-cadences/count")
    public ResponseEntity<Long> countStepCountCadences(StepCountCadenceCriteria criteria) {
        log.debug("REST request to count StepCountCadences by criteria: {}", criteria);
        return ResponseEntity.ok().body(stepCountCadenceQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /step-count-cadences/:id} : get the "id" stepCountCadence.
     *
     * @param id the id of the stepCountCadence to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the stepCountCadence, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/step-count-cadences/{id}")
    public ResponseEntity<StepCountCadence> getStepCountCadence(@PathVariable UUID id) {
        log.debug("REST request to get StepCountCadence : {}", id);
        Optional<StepCountCadence> stepCountCadence = stepCountCadenceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(stepCountCadence);
    }

    /**
     * {@code DELETE  /step-count-cadences/:id} : delete the "id" stepCountCadence.
     *
     * @param id the id of the stepCountCadence to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/step-count-cadences/{id}")
    public ResponseEntity<Void> deleteStepCountCadence(@PathVariable UUID id) {
        log.debug("REST request to delete StepCountCadence : {}", id);
        stepCountCadenceService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
