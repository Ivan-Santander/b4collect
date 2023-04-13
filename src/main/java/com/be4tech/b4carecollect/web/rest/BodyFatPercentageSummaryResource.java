package com.be4tech.b4carecollect.web.rest;

import com.be4tech.b4carecollect.domain.BodyFatPercentageSummary;
import com.be4tech.b4carecollect.repository.BodyFatPercentageSummaryRepository;
import com.be4tech.b4carecollect.service.BodyFatPercentageSummaryQueryService;
import com.be4tech.b4carecollect.service.BodyFatPercentageSummaryService;
import com.be4tech.b4carecollect.service.criteria.BodyFatPercentageSummaryCriteria;
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
 * REST controller for managing {@link com.be4tech.b4carecollect.domain.BodyFatPercentageSummary}.
 */
@RestController
@RequestMapping("/api")
public class BodyFatPercentageSummaryResource {

    private final Logger log = LoggerFactory.getLogger(BodyFatPercentageSummaryResource.class);

    private static final String ENTITY_NAME = "bodyFatPercentageSummary";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BodyFatPercentageSummaryService bodyFatPercentageSummaryService;

    private final BodyFatPercentageSummaryRepository bodyFatPercentageSummaryRepository;

    private final BodyFatPercentageSummaryQueryService bodyFatPercentageSummaryQueryService;

    public BodyFatPercentageSummaryResource(
        BodyFatPercentageSummaryService bodyFatPercentageSummaryService,
        BodyFatPercentageSummaryRepository bodyFatPercentageSummaryRepository,
        BodyFatPercentageSummaryQueryService bodyFatPercentageSummaryQueryService
    ) {
        this.bodyFatPercentageSummaryService = bodyFatPercentageSummaryService;
        this.bodyFatPercentageSummaryRepository = bodyFatPercentageSummaryRepository;
        this.bodyFatPercentageSummaryQueryService = bodyFatPercentageSummaryQueryService;
    }

    /**
     * {@code POST  /body-fat-percentage-summaries} : Create a new bodyFatPercentageSummary.
     *
     * @param bodyFatPercentageSummary the bodyFatPercentageSummary to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new bodyFatPercentageSummary, or with status {@code 400 (Bad Request)} if the bodyFatPercentageSummary has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/body-fat-percentage-summaries")
    public ResponseEntity<BodyFatPercentageSummary> createBodyFatPercentageSummary(
        @RequestBody BodyFatPercentageSummary bodyFatPercentageSummary
    ) throws URISyntaxException {
        log.debug("REST request to save BodyFatPercentageSummary : {}", bodyFatPercentageSummary);
        if (bodyFatPercentageSummary.getId() != null) {
            throw new BadRequestAlertException("A new bodyFatPercentageSummary cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BodyFatPercentageSummary result = bodyFatPercentageSummaryService.save(bodyFatPercentageSummary);
        return ResponseEntity
            .created(new URI("/api/body-fat-percentage-summaries/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /body-fat-percentage-summaries/:id} : Updates an existing bodyFatPercentageSummary.
     *
     * @param id the id of the bodyFatPercentageSummary to save.
     * @param bodyFatPercentageSummary the bodyFatPercentageSummary to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bodyFatPercentageSummary,
     * or with status {@code 400 (Bad Request)} if the bodyFatPercentageSummary is not valid,
     * or with status {@code 500 (Internal Server Error)} if the bodyFatPercentageSummary couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/body-fat-percentage-summaries/{id}")
    public ResponseEntity<BodyFatPercentageSummary> updateBodyFatPercentageSummary(
        @PathVariable(value = "id", required = false) final UUID id,
        @RequestBody BodyFatPercentageSummary bodyFatPercentageSummary
    ) throws URISyntaxException {
        log.debug("REST request to update BodyFatPercentageSummary : {}, {}", id, bodyFatPercentageSummary);
        if (bodyFatPercentageSummary.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, bodyFatPercentageSummary.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bodyFatPercentageSummaryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        BodyFatPercentageSummary result = bodyFatPercentageSummaryService.update(bodyFatPercentageSummary);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, bodyFatPercentageSummary.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /body-fat-percentage-summaries/:id} : Partial updates given fields of an existing bodyFatPercentageSummary, field will ignore if it is null
     *
     * @param id the id of the bodyFatPercentageSummary to save.
     * @param bodyFatPercentageSummary the bodyFatPercentageSummary to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bodyFatPercentageSummary,
     * or with status {@code 400 (Bad Request)} if the bodyFatPercentageSummary is not valid,
     * or with status {@code 404 (Not Found)} if the bodyFatPercentageSummary is not found,
     * or with status {@code 500 (Internal Server Error)} if the bodyFatPercentageSummary couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/body-fat-percentage-summaries/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<BodyFatPercentageSummary> partialUpdateBodyFatPercentageSummary(
        @PathVariable(value = "id", required = false) final UUID id,
        @RequestBody BodyFatPercentageSummary bodyFatPercentageSummary
    ) throws URISyntaxException {
        log.debug("REST request to partial update BodyFatPercentageSummary partially : {}, {}", id, bodyFatPercentageSummary);
        if (bodyFatPercentageSummary.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, bodyFatPercentageSummary.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bodyFatPercentageSummaryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<BodyFatPercentageSummary> result = bodyFatPercentageSummaryService.partialUpdate(bodyFatPercentageSummary);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, bodyFatPercentageSummary.getId().toString())
        );
    }

    /**
     * {@code GET  /body-fat-percentage-summaries} : get all the bodyFatPercentageSummaries.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of bodyFatPercentageSummaries in body.
     */
    @GetMapping("/body-fat-percentage-summaries")
    public ResponseEntity<List<BodyFatPercentageSummary>> getAllBodyFatPercentageSummaries(
        BodyFatPercentageSummaryCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get BodyFatPercentageSummaries by criteria: {}", criteria);
        Page<BodyFatPercentageSummary> page = bodyFatPercentageSummaryQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /body-fat-percentage-summaries/count} : count all the bodyFatPercentageSummaries.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/body-fat-percentage-summaries/count")
    public ResponseEntity<Long> countBodyFatPercentageSummaries(BodyFatPercentageSummaryCriteria criteria) {
        log.debug("REST request to count BodyFatPercentageSummaries by criteria: {}", criteria);
        return ResponseEntity.ok().body(bodyFatPercentageSummaryQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /body-fat-percentage-summaries/:id} : get the "id" bodyFatPercentageSummary.
     *
     * @param id the id of the bodyFatPercentageSummary to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the bodyFatPercentageSummary, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/body-fat-percentage-summaries/{id}")
    public ResponseEntity<BodyFatPercentageSummary> getBodyFatPercentageSummary(@PathVariable UUID id) {
        log.debug("REST request to get BodyFatPercentageSummary : {}", id);
        Optional<BodyFatPercentageSummary> bodyFatPercentageSummary = bodyFatPercentageSummaryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(bodyFatPercentageSummary);
    }

    /**
     * {@code DELETE  /body-fat-percentage-summaries/:id} : delete the "id" bodyFatPercentageSummary.
     *
     * @param id the id of the bodyFatPercentageSummary to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/body-fat-percentage-summaries/{id}")
    public ResponseEntity<Void> deleteBodyFatPercentageSummary(@PathVariable UUID id) {
        log.debug("REST request to delete BodyFatPercentageSummary : {}", id);
        bodyFatPercentageSummaryService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
