package com.be4tech.b4carecollect.web.rest;

import com.be4tech.b4carecollect.domain.StepCountDelta;
import com.be4tech.b4carecollect.repository.StepCountDeltaRepository;
import com.be4tech.b4carecollect.service.StepCountDeltaQueryService;
import com.be4tech.b4carecollect.service.StepCountDeltaService;
import com.be4tech.b4carecollect.service.criteria.StepCountDeltaCriteria;
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
 * REST controller for managing {@link com.be4tech.b4carecollect.domain.StepCountDelta}.
 */
@RestController
@RequestMapping("/api")
public class StepCountDeltaResource {

    private final Logger log = LoggerFactory.getLogger(StepCountDeltaResource.class);

    private static final String ENTITY_NAME = "stepCountDelta";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final StepCountDeltaService stepCountDeltaService;

    private final StepCountDeltaRepository stepCountDeltaRepository;

    private final StepCountDeltaQueryService stepCountDeltaQueryService;

    public StepCountDeltaResource(
        StepCountDeltaService stepCountDeltaService,
        StepCountDeltaRepository stepCountDeltaRepository,
        StepCountDeltaQueryService stepCountDeltaQueryService
    ) {
        this.stepCountDeltaService = stepCountDeltaService;
        this.stepCountDeltaRepository = stepCountDeltaRepository;
        this.stepCountDeltaQueryService = stepCountDeltaQueryService;
    }

    /**
     * {@code POST  /step-count-deltas} : Create a new stepCountDelta.
     *
     * @param stepCountDelta the stepCountDelta to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new stepCountDelta, or with status {@code 400 (Bad Request)} if the stepCountDelta has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/step-count-deltas")
    public ResponseEntity<StepCountDelta> createStepCountDelta(@RequestBody StepCountDelta stepCountDelta) throws URISyntaxException {
        log.debug("REST request to save StepCountDelta : {}", stepCountDelta);
        if (stepCountDelta.getId() != null) {
            throw new BadRequestAlertException("A new stepCountDelta cannot already have an ID", ENTITY_NAME, "idexists");
        }
        StepCountDelta result = stepCountDeltaService.save(stepCountDelta);
        return ResponseEntity
            .created(new URI("/api/step-count-deltas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /step-count-deltas/:id} : Updates an existing stepCountDelta.
     *
     * @param id the id of the stepCountDelta to save.
     * @param stepCountDelta the stepCountDelta to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated stepCountDelta,
     * or with status {@code 400 (Bad Request)} if the stepCountDelta is not valid,
     * or with status {@code 500 (Internal Server Error)} if the stepCountDelta couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/step-count-deltas/{id}")
    public ResponseEntity<StepCountDelta> updateStepCountDelta(
        @PathVariable(value = "id", required = false) final UUID id,
        @RequestBody StepCountDelta stepCountDelta
    ) throws URISyntaxException {
        log.debug("REST request to update StepCountDelta : {}, {}", id, stepCountDelta);
        if (stepCountDelta.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, stepCountDelta.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!stepCountDeltaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        StepCountDelta result = stepCountDeltaService.update(stepCountDelta);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, stepCountDelta.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /step-count-deltas/:id} : Partial updates given fields of an existing stepCountDelta, field will ignore if it is null
     *
     * @param id the id of the stepCountDelta to save.
     * @param stepCountDelta the stepCountDelta to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated stepCountDelta,
     * or with status {@code 400 (Bad Request)} if the stepCountDelta is not valid,
     * or with status {@code 404 (Not Found)} if the stepCountDelta is not found,
     * or with status {@code 500 (Internal Server Error)} if the stepCountDelta couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/step-count-deltas/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<StepCountDelta> partialUpdateStepCountDelta(
        @PathVariable(value = "id", required = false) final UUID id,
        @RequestBody StepCountDelta stepCountDelta
    ) throws URISyntaxException {
        log.debug("REST request to partial update StepCountDelta partially : {}, {}", id, stepCountDelta);
        if (stepCountDelta.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, stepCountDelta.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!stepCountDeltaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<StepCountDelta> result = stepCountDeltaService.partialUpdate(stepCountDelta);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, stepCountDelta.getId().toString())
        );
    }

    /**
     * {@code GET  /step-count-deltas} : get all the stepCountDeltas.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of stepCountDeltas in body.
     */
    @GetMapping("/step-count-deltas")
    public ResponseEntity<List<StepCountDelta>> getAllStepCountDeltas(
        StepCountDeltaCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get StepCountDeltas by criteria: {}", criteria);
        Page<StepCountDelta> page = stepCountDeltaQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /step-count-deltas/count} : count all the stepCountDeltas.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/step-count-deltas/count")
    public ResponseEntity<Long> countStepCountDeltas(StepCountDeltaCriteria criteria) {
        log.debug("REST request to count StepCountDeltas by criteria: {}", criteria);
        return ResponseEntity.ok().body(stepCountDeltaQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /step-count-deltas/:id} : get the "id" stepCountDelta.
     *
     * @param id the id of the stepCountDelta to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the stepCountDelta, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/step-count-deltas/{id}")
    public ResponseEntity<StepCountDelta> getStepCountDelta(@PathVariable UUID id) {
        log.debug("REST request to get StepCountDelta : {}", id);
        Optional<StepCountDelta> stepCountDelta = stepCountDeltaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(stepCountDelta);
    }

    /**
     * {@code DELETE  /step-count-deltas/:id} : delete the "id" stepCountDelta.
     *
     * @param id the id of the stepCountDelta to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/step-count-deltas/{id}")
    public ResponseEntity<Void> deleteStepCountDelta(@PathVariable UUID id) {
        log.debug("REST request to delete StepCountDelta : {}", id);
        stepCountDeltaService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
