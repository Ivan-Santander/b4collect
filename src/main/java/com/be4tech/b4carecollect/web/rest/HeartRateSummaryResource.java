package com.be4tech.b4carecollect.web.rest;

import com.be4tech.b4carecollect.domain.HeartRateSummary;
import com.be4tech.b4carecollect.repository.HeartRateSummaryRepository;
import com.be4tech.b4carecollect.service.HeartRateSummaryQueryService;
import com.be4tech.b4carecollect.service.HeartRateSummaryService;
import com.be4tech.b4carecollect.service.criteria.HeartRateSummaryCriteria;
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
 * REST controller for managing {@link com.be4tech.b4carecollect.domain.HeartRateSummary}.
 */
@RestController
@RequestMapping("/api")
public class HeartRateSummaryResource {

    private final Logger log = LoggerFactory.getLogger(HeartRateSummaryResource.class);

    private static final String ENTITY_NAME = "heartRateSummary";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final HeartRateSummaryService heartRateSummaryService;

    private final HeartRateSummaryRepository heartRateSummaryRepository;

    private final HeartRateSummaryQueryService heartRateSummaryQueryService;

    public HeartRateSummaryResource(
        HeartRateSummaryService heartRateSummaryService,
        HeartRateSummaryRepository heartRateSummaryRepository,
        HeartRateSummaryQueryService heartRateSummaryQueryService
    ) {
        this.heartRateSummaryService = heartRateSummaryService;
        this.heartRateSummaryRepository = heartRateSummaryRepository;
        this.heartRateSummaryQueryService = heartRateSummaryQueryService;
    }

    /**
     * {@code POST  /heart-rate-summaries} : Create a new heartRateSummary.
     *
     * @param heartRateSummary the heartRateSummary to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new heartRateSummary, or with status {@code 400 (Bad Request)} if the heartRateSummary has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/heart-rate-summaries")
    public ResponseEntity<HeartRateSummary> createHeartRateSummary(@RequestBody HeartRateSummary heartRateSummary)
        throws URISyntaxException {
        log.debug("REST request to save HeartRateSummary : {}", heartRateSummary);
        if (heartRateSummary.getId() != null) {
            throw new BadRequestAlertException("A new heartRateSummary cannot already have an ID", ENTITY_NAME, "idexists");
        }
        HeartRateSummary result = heartRateSummaryService.save(heartRateSummary);
        return ResponseEntity
            .created(new URI("/api/heart-rate-summaries/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /heart-rate-summaries/:id} : Updates an existing heartRateSummary.
     *
     * @param id the id of the heartRateSummary to save.
     * @param heartRateSummary the heartRateSummary to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated heartRateSummary,
     * or with status {@code 400 (Bad Request)} if the heartRateSummary is not valid,
     * or with status {@code 500 (Internal Server Error)} if the heartRateSummary couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/heart-rate-summaries/{id}")
    public ResponseEntity<HeartRateSummary> updateHeartRateSummary(
        @PathVariable(value = "id", required = false) final UUID id,
        @RequestBody HeartRateSummary heartRateSummary
    ) throws URISyntaxException {
        log.debug("REST request to update HeartRateSummary : {}, {}", id, heartRateSummary);
        if (heartRateSummary.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, heartRateSummary.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!heartRateSummaryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        HeartRateSummary result = heartRateSummaryService.update(heartRateSummary);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, heartRateSummary.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /heart-rate-summaries/:id} : Partial updates given fields of an existing heartRateSummary, field will ignore if it is null
     *
     * @param id the id of the heartRateSummary to save.
     * @param heartRateSummary the heartRateSummary to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated heartRateSummary,
     * or with status {@code 400 (Bad Request)} if the heartRateSummary is not valid,
     * or with status {@code 404 (Not Found)} if the heartRateSummary is not found,
     * or with status {@code 500 (Internal Server Error)} if the heartRateSummary couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/heart-rate-summaries/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<HeartRateSummary> partialUpdateHeartRateSummary(
        @PathVariable(value = "id", required = false) final UUID id,
        @RequestBody HeartRateSummary heartRateSummary
    ) throws URISyntaxException {
        log.debug("REST request to partial update HeartRateSummary partially : {}, {}", id, heartRateSummary);
        if (heartRateSummary.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, heartRateSummary.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!heartRateSummaryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<HeartRateSummary> result = heartRateSummaryService.partialUpdate(heartRateSummary);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, heartRateSummary.getId().toString())
        );
    }

    /**
     * {@code GET  /heart-rate-summaries} : get all the heartRateSummaries.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of heartRateSummaries in body.
     */
    @GetMapping("/heart-rate-summaries")
    public ResponseEntity<List<HeartRateSummary>> getAllHeartRateSummaries(
        HeartRateSummaryCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get HeartRateSummaries by criteria: {}", criteria);
        Page<HeartRateSummary> page = heartRateSummaryQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /heart-rate-summaries/count} : count all the heartRateSummaries.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/heart-rate-summaries/count")
    public ResponseEntity<Long> countHeartRateSummaries(HeartRateSummaryCriteria criteria) {
        log.debug("REST request to count HeartRateSummaries by criteria: {}", criteria);
        return ResponseEntity.ok().body(heartRateSummaryQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /heart-rate-summaries/:id} : get the "id" heartRateSummary.
     *
     * @param id the id of the heartRateSummary to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the heartRateSummary, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/heart-rate-summaries/{id}")
    public ResponseEntity<HeartRateSummary> getHeartRateSummary(@PathVariable UUID id) {
        log.debug("REST request to get HeartRateSummary : {}", id);
        Optional<HeartRateSummary> heartRateSummary = heartRateSummaryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(heartRateSummary);
    }

    /**
     * {@code DELETE  /heart-rate-summaries/:id} : delete the "id" heartRateSummary.
     *
     * @param id the id of the heartRateSummary to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/heart-rate-summaries/{id}")
    public ResponseEntity<Void> deleteHeartRateSummary(@PathVariable UUID id) {
        log.debug("REST request to delete HeartRateSummary : {}", id);
        heartRateSummaryService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
