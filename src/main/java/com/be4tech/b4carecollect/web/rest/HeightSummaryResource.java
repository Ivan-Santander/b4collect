package com.be4tech.b4carecollect.web.rest;

import com.be4tech.b4carecollect.domain.HeightSummary;
import com.be4tech.b4carecollect.repository.HeightSummaryRepository;
import com.be4tech.b4carecollect.service.HeightSummaryQueryService;
import com.be4tech.b4carecollect.service.HeightSummaryService;
import com.be4tech.b4carecollect.service.criteria.HeightSummaryCriteria;
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
 * REST controller for managing {@link com.be4tech.b4carecollect.domain.HeightSummary}.
 */
@RestController
@RequestMapping("/api")
public class HeightSummaryResource {

    private final Logger log = LoggerFactory.getLogger(HeightSummaryResource.class);

    private static final String ENTITY_NAME = "heightSummary";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final HeightSummaryService heightSummaryService;

    private final HeightSummaryRepository heightSummaryRepository;

    private final HeightSummaryQueryService heightSummaryQueryService;

    public HeightSummaryResource(
        HeightSummaryService heightSummaryService,
        HeightSummaryRepository heightSummaryRepository,
        HeightSummaryQueryService heightSummaryQueryService
    ) {
        this.heightSummaryService = heightSummaryService;
        this.heightSummaryRepository = heightSummaryRepository;
        this.heightSummaryQueryService = heightSummaryQueryService;
    }

    /**
     * {@code POST  /height-summaries} : Create a new heightSummary.
     *
     * @param heightSummary the heightSummary to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new heightSummary, or with status {@code 400 (Bad Request)} if the heightSummary has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/height-summaries")
    public ResponseEntity<HeightSummary> createHeightSummary(@RequestBody HeightSummary heightSummary) throws URISyntaxException {
        log.debug("REST request to save HeightSummary : {}", heightSummary);
        if (heightSummary.getId() != null) {
            throw new BadRequestAlertException("A new heightSummary cannot already have an ID", ENTITY_NAME, "idexists");
        }
        HeightSummary result = heightSummaryService.save(heightSummary);
        return ResponseEntity
            .created(new URI("/api/height-summaries/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /height-summaries/:id} : Updates an existing heightSummary.
     *
     * @param id the id of the heightSummary to save.
     * @param heightSummary the heightSummary to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated heightSummary,
     * or with status {@code 400 (Bad Request)} if the heightSummary is not valid,
     * or with status {@code 500 (Internal Server Error)} if the heightSummary couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/height-summaries/{id}")
    public ResponseEntity<HeightSummary> updateHeightSummary(
        @PathVariable(value = "id", required = false) final UUID id,
        @RequestBody HeightSummary heightSummary
    ) throws URISyntaxException {
        log.debug("REST request to update HeightSummary : {}, {}", id, heightSummary);
        if (heightSummary.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, heightSummary.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!heightSummaryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        HeightSummary result = heightSummaryService.update(heightSummary);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, heightSummary.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /height-summaries/:id} : Partial updates given fields of an existing heightSummary, field will ignore if it is null
     *
     * @param id the id of the heightSummary to save.
     * @param heightSummary the heightSummary to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated heightSummary,
     * or with status {@code 400 (Bad Request)} if the heightSummary is not valid,
     * or with status {@code 404 (Not Found)} if the heightSummary is not found,
     * or with status {@code 500 (Internal Server Error)} if the heightSummary couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/height-summaries/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<HeightSummary> partialUpdateHeightSummary(
        @PathVariable(value = "id", required = false) final UUID id,
        @RequestBody HeightSummary heightSummary
    ) throws URISyntaxException {
        log.debug("REST request to partial update HeightSummary partially : {}, {}", id, heightSummary);
        if (heightSummary.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, heightSummary.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!heightSummaryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<HeightSummary> result = heightSummaryService.partialUpdate(heightSummary);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, heightSummary.getId().toString())
        );
    }

    /**
     * {@code GET  /height-summaries} : get all the heightSummaries.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of heightSummaries in body.
     */
    @GetMapping("/height-summaries")
    public ResponseEntity<List<HeightSummary>> getAllHeightSummaries(
        HeightSummaryCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get HeightSummaries by criteria: {}", criteria);
        Page<HeightSummary> page = heightSummaryQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /height-summaries/count} : count all the heightSummaries.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/height-summaries/count")
    public ResponseEntity<Long> countHeightSummaries(HeightSummaryCriteria criteria) {
        log.debug("REST request to count HeightSummaries by criteria: {}", criteria);
        return ResponseEntity.ok().body(heightSummaryQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /height-summaries/:id} : get the "id" heightSummary.
     *
     * @param id the id of the heightSummary to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the heightSummary, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/height-summaries/{id}")
    public ResponseEntity<HeightSummary> getHeightSummary(@PathVariable UUID id) {
        log.debug("REST request to get HeightSummary : {}", id);
        Optional<HeightSummary> heightSummary = heightSummaryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(heightSummary);
    }

    /**
     * {@code DELETE  /height-summaries/:id} : delete the "id" heightSummary.
     *
     * @param id the id of the heightSummary to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/height-summaries/{id}")
    public ResponseEntity<Void> deleteHeightSummary(@PathVariable UUID id) {
        log.debug("REST request to delete HeightSummary : {}", id);
        heightSummaryService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
