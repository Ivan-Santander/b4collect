package com.be4tech.b4carecollect.web.rest;

import com.be4tech.b4carecollect.domain.BloodPressureSummary;
import com.be4tech.b4carecollect.repository.BloodPressureSummaryRepository;
import com.be4tech.b4carecollect.service.BloodPressureSummaryQueryService;
import com.be4tech.b4carecollect.service.BloodPressureSummaryService;
import com.be4tech.b4carecollect.service.criteria.BloodPressureSummaryCriteria;
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
 * REST controller for managing {@link com.be4tech.b4carecollect.domain.BloodPressureSummary}.
 */
@RestController
@RequestMapping("/api")
public class BloodPressureSummaryResource {

    private final Logger log = LoggerFactory.getLogger(BloodPressureSummaryResource.class);

    private static final String ENTITY_NAME = "bloodPressureSummary";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BloodPressureSummaryService bloodPressureSummaryService;

    private final BloodPressureSummaryRepository bloodPressureSummaryRepository;

    private final BloodPressureSummaryQueryService bloodPressureSummaryQueryService;

    public BloodPressureSummaryResource(
        BloodPressureSummaryService bloodPressureSummaryService,
        BloodPressureSummaryRepository bloodPressureSummaryRepository,
        BloodPressureSummaryQueryService bloodPressureSummaryQueryService
    ) {
        this.bloodPressureSummaryService = bloodPressureSummaryService;
        this.bloodPressureSummaryRepository = bloodPressureSummaryRepository;
        this.bloodPressureSummaryQueryService = bloodPressureSummaryQueryService;
    }

    /**
     * {@code POST  /blood-pressure-summaries} : Create a new bloodPressureSummary.
     *
     * @param bloodPressureSummary the bloodPressureSummary to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new bloodPressureSummary, or with status {@code 400 (Bad Request)} if the bloodPressureSummary has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/blood-pressure-summaries")
    public ResponseEntity<BloodPressureSummary> createBloodPressureSummary(@RequestBody BloodPressureSummary bloodPressureSummary)
        throws URISyntaxException {
        log.debug("REST request to save BloodPressureSummary : {}", bloodPressureSummary);
        if (bloodPressureSummary.getId() != null) {
            throw new BadRequestAlertException("A new bloodPressureSummary cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BloodPressureSummary result = bloodPressureSummaryService.save(bloodPressureSummary);
        return ResponseEntity
            .created(new URI("/api/blood-pressure-summaries/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /blood-pressure-summaries/:id} : Updates an existing bloodPressureSummary.
     *
     * @param id the id of the bloodPressureSummary to save.
     * @param bloodPressureSummary the bloodPressureSummary to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bloodPressureSummary,
     * or with status {@code 400 (Bad Request)} if the bloodPressureSummary is not valid,
     * or with status {@code 500 (Internal Server Error)} if the bloodPressureSummary couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/blood-pressure-summaries/{id}")
    public ResponseEntity<BloodPressureSummary> updateBloodPressureSummary(
        @PathVariable(value = "id", required = false) final UUID id,
        @RequestBody BloodPressureSummary bloodPressureSummary
    ) throws URISyntaxException {
        log.debug("REST request to update BloodPressureSummary : {}, {}", id, bloodPressureSummary);
        if (bloodPressureSummary.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, bloodPressureSummary.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bloodPressureSummaryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        BloodPressureSummary result = bloodPressureSummaryService.update(bloodPressureSummary);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, bloodPressureSummary.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /blood-pressure-summaries/:id} : Partial updates given fields of an existing bloodPressureSummary, field will ignore if it is null
     *
     * @param id the id of the bloodPressureSummary to save.
     * @param bloodPressureSummary the bloodPressureSummary to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bloodPressureSummary,
     * or with status {@code 400 (Bad Request)} if the bloodPressureSummary is not valid,
     * or with status {@code 404 (Not Found)} if the bloodPressureSummary is not found,
     * or with status {@code 500 (Internal Server Error)} if the bloodPressureSummary couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/blood-pressure-summaries/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<BloodPressureSummary> partialUpdateBloodPressureSummary(
        @PathVariable(value = "id", required = false) final UUID id,
        @RequestBody BloodPressureSummary bloodPressureSummary
    ) throws URISyntaxException {
        log.debug("REST request to partial update BloodPressureSummary partially : {}, {}", id, bloodPressureSummary);
        if (bloodPressureSummary.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, bloodPressureSummary.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bloodPressureSummaryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<BloodPressureSummary> result = bloodPressureSummaryService.partialUpdate(bloodPressureSummary);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, bloodPressureSummary.getId().toString())
        );
    }

    /**
     * {@code GET  /blood-pressure-summaries} : get all the bloodPressureSummaries.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of bloodPressureSummaries in body.
     */
    @GetMapping("/blood-pressure-summaries")
    public ResponseEntity<List<BloodPressureSummary>> getAllBloodPressureSummaries(
        BloodPressureSummaryCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get BloodPressureSummaries by criteria: {}", criteria);
        Page<BloodPressureSummary> page = bloodPressureSummaryQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /blood-pressure-summaries/count} : count all the bloodPressureSummaries.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/blood-pressure-summaries/count")
    public ResponseEntity<Long> countBloodPressureSummaries(BloodPressureSummaryCriteria criteria) {
        log.debug("REST request to count BloodPressureSummaries by criteria: {}", criteria);
        return ResponseEntity.ok().body(bloodPressureSummaryQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /blood-pressure-summaries/:id} : get the "id" bloodPressureSummary.
     *
     * @param id the id of the bloodPressureSummary to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the bloodPressureSummary, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/blood-pressure-summaries/{id}")
    public ResponseEntity<BloodPressureSummary> getBloodPressureSummary(@PathVariable UUID id) {
        log.debug("REST request to get BloodPressureSummary : {}", id);
        Optional<BloodPressureSummary> bloodPressureSummary = bloodPressureSummaryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(bloodPressureSummary);
    }

    /**
     * {@code DELETE  /blood-pressure-summaries/:id} : delete the "id" bloodPressureSummary.
     *
     * @param id the id of the bloodPressureSummary to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/blood-pressure-summaries/{id}")
    public ResponseEntity<Void> deleteBloodPressureSummary(@PathVariable UUID id) {
        log.debug("REST request to delete BloodPressureSummary : {}", id);
        bloodPressureSummaryService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
