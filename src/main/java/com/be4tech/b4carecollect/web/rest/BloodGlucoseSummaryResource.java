package com.be4tech.b4carecollect.web.rest;

import com.be4tech.b4carecollect.domain.BloodGlucoseSummary;
import com.be4tech.b4carecollect.repository.BloodGlucoseSummaryRepository;
import com.be4tech.b4carecollect.service.BloodGlucoseSummaryQueryService;
import com.be4tech.b4carecollect.service.BloodGlucoseSummaryService;
import com.be4tech.b4carecollect.service.criteria.BloodGlucoseSummaryCriteria;
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
 * REST controller for managing {@link com.be4tech.b4carecollect.domain.BloodGlucoseSummary}.
 */
@RestController
@RequestMapping("/api")
public class BloodGlucoseSummaryResource {

    private final Logger log = LoggerFactory.getLogger(BloodGlucoseSummaryResource.class);

    private static final String ENTITY_NAME = "bloodGlucoseSummary";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BloodGlucoseSummaryService bloodGlucoseSummaryService;

    private final BloodGlucoseSummaryRepository bloodGlucoseSummaryRepository;

    private final BloodGlucoseSummaryQueryService bloodGlucoseSummaryQueryService;

    public BloodGlucoseSummaryResource(
        BloodGlucoseSummaryService bloodGlucoseSummaryService,
        BloodGlucoseSummaryRepository bloodGlucoseSummaryRepository,
        BloodGlucoseSummaryQueryService bloodGlucoseSummaryQueryService
    ) {
        this.bloodGlucoseSummaryService = bloodGlucoseSummaryService;
        this.bloodGlucoseSummaryRepository = bloodGlucoseSummaryRepository;
        this.bloodGlucoseSummaryQueryService = bloodGlucoseSummaryQueryService;
    }

    /**
     * {@code POST  /blood-glucose-summaries} : Create a new bloodGlucoseSummary.
     *
     * @param bloodGlucoseSummary the bloodGlucoseSummary to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new bloodGlucoseSummary, or with status {@code 400 (Bad Request)} if the bloodGlucoseSummary has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/blood-glucose-summaries")
    public ResponseEntity<BloodGlucoseSummary> createBloodGlucoseSummary(@RequestBody BloodGlucoseSummary bloodGlucoseSummary)
        throws URISyntaxException {
        log.debug("REST request to save BloodGlucoseSummary : {}", bloodGlucoseSummary);
        if (bloodGlucoseSummary.getId() != null) {
            throw new BadRequestAlertException("A new bloodGlucoseSummary cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BloodGlucoseSummary result = bloodGlucoseSummaryService.save(bloodGlucoseSummary);
        return ResponseEntity
            .created(new URI("/api/blood-glucose-summaries/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /blood-glucose-summaries/:id} : Updates an existing bloodGlucoseSummary.
     *
     * @param id the id of the bloodGlucoseSummary to save.
     * @param bloodGlucoseSummary the bloodGlucoseSummary to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bloodGlucoseSummary,
     * or with status {@code 400 (Bad Request)} if the bloodGlucoseSummary is not valid,
     * or with status {@code 500 (Internal Server Error)} if the bloodGlucoseSummary couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/blood-glucose-summaries/{id}")
    public ResponseEntity<BloodGlucoseSummary> updateBloodGlucoseSummary(
        @PathVariable(value = "id", required = false) final UUID id,
        @RequestBody BloodGlucoseSummary bloodGlucoseSummary
    ) throws URISyntaxException {
        log.debug("REST request to update BloodGlucoseSummary : {}, {}", id, bloodGlucoseSummary);
        if (bloodGlucoseSummary.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, bloodGlucoseSummary.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bloodGlucoseSummaryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        BloodGlucoseSummary result = bloodGlucoseSummaryService.update(bloodGlucoseSummary);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, bloodGlucoseSummary.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /blood-glucose-summaries/:id} : Partial updates given fields of an existing bloodGlucoseSummary, field will ignore if it is null
     *
     * @param id the id of the bloodGlucoseSummary to save.
     * @param bloodGlucoseSummary the bloodGlucoseSummary to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bloodGlucoseSummary,
     * or with status {@code 400 (Bad Request)} if the bloodGlucoseSummary is not valid,
     * or with status {@code 404 (Not Found)} if the bloodGlucoseSummary is not found,
     * or with status {@code 500 (Internal Server Error)} if the bloodGlucoseSummary couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/blood-glucose-summaries/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<BloodGlucoseSummary> partialUpdateBloodGlucoseSummary(
        @PathVariable(value = "id", required = false) final UUID id,
        @RequestBody BloodGlucoseSummary bloodGlucoseSummary
    ) throws URISyntaxException {
        log.debug("REST request to partial update BloodGlucoseSummary partially : {}, {}", id, bloodGlucoseSummary);
        if (bloodGlucoseSummary.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, bloodGlucoseSummary.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bloodGlucoseSummaryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<BloodGlucoseSummary> result = bloodGlucoseSummaryService.partialUpdate(bloodGlucoseSummary);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, bloodGlucoseSummary.getId().toString())
        );
    }

    /**
     * {@code GET  /blood-glucose-summaries} : get all the bloodGlucoseSummaries.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of bloodGlucoseSummaries in body.
     */
    @GetMapping("/blood-glucose-summaries")
    public ResponseEntity<List<BloodGlucoseSummary>> getAllBloodGlucoseSummaries(
        BloodGlucoseSummaryCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get BloodGlucoseSummaries by criteria: {}", criteria);
        Page<BloodGlucoseSummary> page = bloodGlucoseSummaryQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /blood-glucose-summaries/count} : count all the bloodGlucoseSummaries.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/blood-glucose-summaries/count")
    public ResponseEntity<Long> countBloodGlucoseSummaries(BloodGlucoseSummaryCriteria criteria) {
        log.debug("REST request to count BloodGlucoseSummaries by criteria: {}", criteria);
        return ResponseEntity.ok().body(bloodGlucoseSummaryQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /blood-glucose-summaries/:id} : get the "id" bloodGlucoseSummary.
     *
     * @param id the id of the bloodGlucoseSummary to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the bloodGlucoseSummary, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/blood-glucose-summaries/{id}")
    public ResponseEntity<BloodGlucoseSummary> getBloodGlucoseSummary(@PathVariable UUID id) {
        log.debug("REST request to get BloodGlucoseSummary : {}", id);
        Optional<BloodGlucoseSummary> bloodGlucoseSummary = bloodGlucoseSummaryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(bloodGlucoseSummary);
    }

    /**
     * {@code DELETE  /blood-glucose-summaries/:id} : delete the "id" bloodGlucoseSummary.
     *
     * @param id the id of the bloodGlucoseSummary to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/blood-glucose-summaries/{id}")
    public ResponseEntity<Void> deleteBloodGlucoseSummary(@PathVariable UUID id) {
        log.debug("REST request to delete BloodGlucoseSummary : {}", id);
        bloodGlucoseSummaryService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
