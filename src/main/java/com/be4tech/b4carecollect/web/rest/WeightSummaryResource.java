package com.be4tech.b4carecollect.web.rest;

import com.be4tech.b4carecollect.domain.WeightSummary;
import com.be4tech.b4carecollect.repository.WeightSummaryRepository;
import com.be4tech.b4carecollect.service.WeightSummaryQueryService;
import com.be4tech.b4carecollect.service.WeightSummaryService;
import com.be4tech.b4carecollect.service.criteria.WeightSummaryCriteria;
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
 * REST controller for managing {@link com.be4tech.b4carecollect.domain.WeightSummary}.
 */
@RestController
@RequestMapping("/api")
public class WeightSummaryResource {

    private final Logger log = LoggerFactory.getLogger(WeightSummaryResource.class);

    private static final String ENTITY_NAME = "weightSummary";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final WeightSummaryService weightSummaryService;

    private final WeightSummaryRepository weightSummaryRepository;

    private final WeightSummaryQueryService weightSummaryQueryService;

    public WeightSummaryResource(
        WeightSummaryService weightSummaryService,
        WeightSummaryRepository weightSummaryRepository,
        WeightSummaryQueryService weightSummaryQueryService
    ) {
        this.weightSummaryService = weightSummaryService;
        this.weightSummaryRepository = weightSummaryRepository;
        this.weightSummaryQueryService = weightSummaryQueryService;
    }

    /**
     * {@code POST  /weight-summaries} : Create a new weightSummary.
     *
     * @param weightSummary the weightSummary to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new weightSummary, or with status {@code 400 (Bad Request)} if the weightSummary has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/weight-summaries")
    public ResponseEntity<WeightSummary> createWeightSummary(@RequestBody WeightSummary weightSummary) throws URISyntaxException {
        log.debug("REST request to save WeightSummary : {}", weightSummary);
        if (weightSummary.getId() != null) {
            throw new BadRequestAlertException("A new weightSummary cannot already have an ID", ENTITY_NAME, "idexists");
        }
        WeightSummary result = weightSummaryService.save(weightSummary);
        return ResponseEntity
            .created(new URI("/api/weight-summaries/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /weight-summaries/:id} : Updates an existing weightSummary.
     *
     * @param id the id of the weightSummary to save.
     * @param weightSummary the weightSummary to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated weightSummary,
     * or with status {@code 400 (Bad Request)} if the weightSummary is not valid,
     * or with status {@code 500 (Internal Server Error)} if the weightSummary couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/weight-summaries/{id}")
    public ResponseEntity<WeightSummary> updateWeightSummary(
        @PathVariable(value = "id", required = false) final UUID id,
        @RequestBody WeightSummary weightSummary
    ) throws URISyntaxException {
        log.debug("REST request to update WeightSummary : {}, {}", id, weightSummary);
        if (weightSummary.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, weightSummary.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!weightSummaryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        WeightSummary result = weightSummaryService.update(weightSummary);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, weightSummary.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /weight-summaries/:id} : Partial updates given fields of an existing weightSummary, field will ignore if it is null
     *
     * @param id the id of the weightSummary to save.
     * @param weightSummary the weightSummary to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated weightSummary,
     * or with status {@code 400 (Bad Request)} if the weightSummary is not valid,
     * or with status {@code 404 (Not Found)} if the weightSummary is not found,
     * or with status {@code 500 (Internal Server Error)} if the weightSummary couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/weight-summaries/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<WeightSummary> partialUpdateWeightSummary(
        @PathVariable(value = "id", required = false) final UUID id,
        @RequestBody WeightSummary weightSummary
    ) throws URISyntaxException {
        log.debug("REST request to partial update WeightSummary partially : {}, {}", id, weightSummary);
        if (weightSummary.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, weightSummary.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!weightSummaryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<WeightSummary> result = weightSummaryService.partialUpdate(weightSummary);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, weightSummary.getId().toString())
        );
    }

    /**
     * {@code GET  /weight-summaries} : get all the weightSummaries.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of weightSummaries in body.
     */
    @GetMapping("/weight-summaries")
    public ResponseEntity<List<WeightSummary>> getAllWeightSummaries(
        WeightSummaryCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get WeightSummaries by criteria: {}", criteria);
        Page<WeightSummary> page = weightSummaryQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /weight-summaries/count} : count all the weightSummaries.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/weight-summaries/count")
    public ResponseEntity<Long> countWeightSummaries(WeightSummaryCriteria criteria) {
        log.debug("REST request to count WeightSummaries by criteria: {}", criteria);
        return ResponseEntity.ok().body(weightSummaryQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /weight-summaries/:id} : get the "id" weightSummary.
     *
     * @param id the id of the weightSummary to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the weightSummary, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/weight-summaries/{id}")
    public ResponseEntity<WeightSummary> getWeightSummary(@PathVariable UUID id) {
        log.debug("REST request to get WeightSummary : {}", id);
        Optional<WeightSummary> weightSummary = weightSummaryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(weightSummary);
    }

    /**
     * {@code DELETE  /weight-summaries/:id} : delete the "id" weightSummary.
     *
     * @param id the id of the weightSummary to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/weight-summaries/{id}")
    public ResponseEntity<Void> deleteWeightSummary(@PathVariable UUID id) {
        log.debug("REST request to delete WeightSummary : {}", id);
        weightSummaryService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
