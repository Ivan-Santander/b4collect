package com.be4tech.b4carecollect.web.rest;

import com.be4tech.b4carecollect.domain.FuntionalIndexSummary;
import com.be4tech.b4carecollect.repository.FuntionalIndexSummaryRepository;
import com.be4tech.b4carecollect.service.FuntionalIndexSummaryQueryService;
import com.be4tech.b4carecollect.service.FuntionalIndexSummaryService;
import com.be4tech.b4carecollect.service.criteria.FuntionalIndexSummaryCriteria;
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
 * REST controller for managing {@link com.be4tech.b4carecollect.domain.FuntionalIndexSummary}.
 */
@RestController
@RequestMapping("/api")
public class FuntionalIndexSummaryResource {

    private final Logger log = LoggerFactory.getLogger(FuntionalIndexSummaryResource.class);

    private static final String ENTITY_NAME = "funtionalIndexSummary";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FuntionalIndexSummaryService funtionalIndexSummaryService;

    private final FuntionalIndexSummaryRepository funtionalIndexSummaryRepository;

    private final FuntionalIndexSummaryQueryService funtionalIndexSummaryQueryService;

    public FuntionalIndexSummaryResource(
        FuntionalIndexSummaryService funtionalIndexSummaryService,
        FuntionalIndexSummaryRepository funtionalIndexSummaryRepository,
        FuntionalIndexSummaryQueryService funtionalIndexSummaryQueryService
    ) {
        this.funtionalIndexSummaryService = funtionalIndexSummaryService;
        this.funtionalIndexSummaryRepository = funtionalIndexSummaryRepository;
        this.funtionalIndexSummaryQueryService = funtionalIndexSummaryQueryService;
    }

    /**
     * {@code POST  /funtional-index-summaries} : Create a new funtionalIndexSummary.
     *
     * @param funtionalIndexSummary the funtionalIndexSummary to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new funtionalIndexSummary, or with status {@code 400 (Bad Request)} if the funtionalIndexSummary has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/funtional-index-summaries")
    public ResponseEntity<FuntionalIndexSummary> createFuntionalIndexSummary(@RequestBody FuntionalIndexSummary funtionalIndexSummary)
        throws URISyntaxException {
        log.debug("REST request to save FuntionalIndexSummary : {}", funtionalIndexSummary);
        if (funtionalIndexSummary.getId() != null) {
            throw new BadRequestAlertException("A new funtionalIndexSummary cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FuntionalIndexSummary result = funtionalIndexSummaryService.save(funtionalIndexSummary);
        return ResponseEntity
            .created(new URI("/api/funtional-index-summaries/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /funtional-index-summaries/:id} : Updates an existing funtionalIndexSummary.
     *
     * @param id the id of the funtionalIndexSummary to save.
     * @param funtionalIndexSummary the funtionalIndexSummary to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated funtionalIndexSummary,
     * or with status {@code 400 (Bad Request)} if the funtionalIndexSummary is not valid,
     * or with status {@code 500 (Internal Server Error)} if the funtionalIndexSummary couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/funtional-index-summaries/{id}")
    public ResponseEntity<FuntionalIndexSummary> updateFuntionalIndexSummary(
        @PathVariable(value = "id", required = false) final UUID id,
        @RequestBody FuntionalIndexSummary funtionalIndexSummary
    ) throws URISyntaxException {
        log.debug("REST request to update FuntionalIndexSummary : {}, {}", id, funtionalIndexSummary);
        if (funtionalIndexSummary.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, funtionalIndexSummary.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!funtionalIndexSummaryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        FuntionalIndexSummary result = funtionalIndexSummaryService.update(funtionalIndexSummary);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, funtionalIndexSummary.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /funtional-index-summaries/:id} : Partial updates given fields of an existing funtionalIndexSummary, field will ignore if it is null
     *
     * @param id the id of the funtionalIndexSummary to save.
     * @param funtionalIndexSummary the funtionalIndexSummary to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated funtionalIndexSummary,
     * or with status {@code 400 (Bad Request)} if the funtionalIndexSummary is not valid,
     * or with status {@code 404 (Not Found)} if the funtionalIndexSummary is not found,
     * or with status {@code 500 (Internal Server Error)} if the funtionalIndexSummary couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/funtional-index-summaries/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<FuntionalIndexSummary> partialUpdateFuntionalIndexSummary(
        @PathVariable(value = "id", required = false) final UUID id,
        @RequestBody FuntionalIndexSummary funtionalIndexSummary
    ) throws URISyntaxException {
        log.debug("REST request to partial update FuntionalIndexSummary partially : {}, {}", id, funtionalIndexSummary);
        if (funtionalIndexSummary.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, funtionalIndexSummary.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!funtionalIndexSummaryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<FuntionalIndexSummary> result = funtionalIndexSummaryService.partialUpdate(funtionalIndexSummary);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, funtionalIndexSummary.getId().toString())
        );
    }

    /**
     * {@code GET  /funtional-index-summaries} : get all the funtionalIndexSummaries.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of funtionalIndexSummaries in body.
     */
    @GetMapping("/funtional-index-summaries")
    public ResponseEntity<List<FuntionalIndexSummary>> getAllFuntionalIndexSummaries(
        FuntionalIndexSummaryCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get FuntionalIndexSummaries by criteria: {}", criteria);
        Page<FuntionalIndexSummary> page = funtionalIndexSummaryQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /funtional-index-summaries/count} : count all the funtionalIndexSummaries.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/funtional-index-summaries/count")
    public ResponseEntity<Long> countFuntionalIndexSummaries(FuntionalIndexSummaryCriteria criteria) {
        log.debug("REST request to count FuntionalIndexSummaries by criteria: {}", criteria);
        return ResponseEntity.ok().body(funtionalIndexSummaryQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /funtional-index-summaries/:id} : get the "id" funtionalIndexSummary.
     *
     * @param id the id of the funtionalIndexSummary to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the funtionalIndexSummary, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/funtional-index-summaries/{id}")
    public ResponseEntity<FuntionalIndexSummary> getFuntionalIndexSummary(@PathVariable UUID id) {
        log.debug("REST request to get FuntionalIndexSummary : {}", id);
        Optional<FuntionalIndexSummary> funtionalIndexSummary = funtionalIndexSummaryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(funtionalIndexSummary);
    }

    /**
     * {@code DELETE  /funtional-index-summaries/:id} : delete the "id" funtionalIndexSummary.
     *
     * @param id the id of the funtionalIndexSummary to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/funtional-index-summaries/{id}")
    public ResponseEntity<Void> deleteFuntionalIndexSummary(@PathVariable UUID id) {
        log.debug("REST request to delete FuntionalIndexSummary : {}", id);
        funtionalIndexSummaryService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
