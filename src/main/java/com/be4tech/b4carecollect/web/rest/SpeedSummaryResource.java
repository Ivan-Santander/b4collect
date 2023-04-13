package com.be4tech.b4carecollect.web.rest;

import com.be4tech.b4carecollect.domain.SpeedSummary;
import com.be4tech.b4carecollect.repository.SpeedSummaryRepository;
import com.be4tech.b4carecollect.service.SpeedSummaryQueryService;
import com.be4tech.b4carecollect.service.SpeedSummaryService;
import com.be4tech.b4carecollect.service.criteria.SpeedSummaryCriteria;
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
 * REST controller for managing {@link com.be4tech.b4carecollect.domain.SpeedSummary}.
 */
@RestController
@RequestMapping("/api")
public class SpeedSummaryResource {

    private final Logger log = LoggerFactory.getLogger(SpeedSummaryResource.class);

    private static final String ENTITY_NAME = "speedSummary";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SpeedSummaryService speedSummaryService;

    private final SpeedSummaryRepository speedSummaryRepository;

    private final SpeedSummaryQueryService speedSummaryQueryService;

    public SpeedSummaryResource(
        SpeedSummaryService speedSummaryService,
        SpeedSummaryRepository speedSummaryRepository,
        SpeedSummaryQueryService speedSummaryQueryService
    ) {
        this.speedSummaryService = speedSummaryService;
        this.speedSummaryRepository = speedSummaryRepository;
        this.speedSummaryQueryService = speedSummaryQueryService;
    }

    /**
     * {@code POST  /speed-summaries} : Create a new speedSummary.
     *
     * @param speedSummary the speedSummary to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new speedSummary, or with status {@code 400 (Bad Request)} if the speedSummary has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/speed-summaries")
    public ResponseEntity<SpeedSummary> createSpeedSummary(@RequestBody SpeedSummary speedSummary) throws URISyntaxException {
        log.debug("REST request to save SpeedSummary : {}", speedSummary);
        if (speedSummary.getId() != null) {
            throw new BadRequestAlertException("A new speedSummary cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SpeedSummary result = speedSummaryService.save(speedSummary);
        return ResponseEntity
            .created(new URI("/api/speed-summaries/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /speed-summaries/:id} : Updates an existing speedSummary.
     *
     * @param id the id of the speedSummary to save.
     * @param speedSummary the speedSummary to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated speedSummary,
     * or with status {@code 400 (Bad Request)} if the speedSummary is not valid,
     * or with status {@code 500 (Internal Server Error)} if the speedSummary couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/speed-summaries/{id}")
    public ResponseEntity<SpeedSummary> updateSpeedSummary(
        @PathVariable(value = "id", required = false) final UUID id,
        @RequestBody SpeedSummary speedSummary
    ) throws URISyntaxException {
        log.debug("REST request to update SpeedSummary : {}, {}", id, speedSummary);
        if (speedSummary.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, speedSummary.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!speedSummaryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        SpeedSummary result = speedSummaryService.update(speedSummary);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, speedSummary.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /speed-summaries/:id} : Partial updates given fields of an existing speedSummary, field will ignore if it is null
     *
     * @param id the id of the speedSummary to save.
     * @param speedSummary the speedSummary to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated speedSummary,
     * or with status {@code 400 (Bad Request)} if the speedSummary is not valid,
     * or with status {@code 404 (Not Found)} if the speedSummary is not found,
     * or with status {@code 500 (Internal Server Error)} if the speedSummary couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/speed-summaries/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<SpeedSummary> partialUpdateSpeedSummary(
        @PathVariable(value = "id", required = false) final UUID id,
        @RequestBody SpeedSummary speedSummary
    ) throws URISyntaxException {
        log.debug("REST request to partial update SpeedSummary partially : {}, {}", id, speedSummary);
        if (speedSummary.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, speedSummary.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!speedSummaryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<SpeedSummary> result = speedSummaryService.partialUpdate(speedSummary);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, speedSummary.getId().toString())
        );
    }

    /**
     * {@code GET  /speed-summaries} : get all the speedSummaries.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of speedSummaries in body.
     */
    @GetMapping("/speed-summaries")
    public ResponseEntity<List<SpeedSummary>> getAllSpeedSummaries(
        SpeedSummaryCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get SpeedSummaries by criteria: {}", criteria);
        Page<SpeedSummary> page = speedSummaryQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /speed-summaries/count} : count all the speedSummaries.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/speed-summaries/count")
    public ResponseEntity<Long> countSpeedSummaries(SpeedSummaryCriteria criteria) {
        log.debug("REST request to count SpeedSummaries by criteria: {}", criteria);
        return ResponseEntity.ok().body(speedSummaryQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /speed-summaries/:id} : get the "id" speedSummary.
     *
     * @param id the id of the speedSummary to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the speedSummary, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/speed-summaries/{id}")
    public ResponseEntity<SpeedSummary> getSpeedSummary(@PathVariable UUID id) {
        log.debug("REST request to get SpeedSummary : {}", id);
        Optional<SpeedSummary> speedSummary = speedSummaryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(speedSummary);
    }

    /**
     * {@code DELETE  /speed-summaries/:id} : delete the "id" speedSummary.
     *
     * @param id the id of the speedSummary to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/speed-summaries/{id}")
    public ResponseEntity<Void> deleteSpeedSummary(@PathVariable UUID id) {
        log.debug("REST request to delete SpeedSummary : {}", id);
        speedSummaryService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
