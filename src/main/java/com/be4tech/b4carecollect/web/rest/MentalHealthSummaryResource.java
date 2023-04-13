package com.be4tech.b4carecollect.web.rest;

import com.be4tech.b4carecollect.domain.MentalHealthSummary;
import com.be4tech.b4carecollect.repository.MentalHealthSummaryRepository;
import com.be4tech.b4carecollect.service.MentalHealthSummaryQueryService;
import com.be4tech.b4carecollect.service.MentalHealthSummaryService;
import com.be4tech.b4carecollect.service.criteria.MentalHealthSummaryCriteria;
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
 * REST controller for managing {@link com.be4tech.b4carecollect.domain.MentalHealthSummary}.
 */
@RestController
@RequestMapping("/api")
public class MentalHealthSummaryResource {

    private final Logger log = LoggerFactory.getLogger(MentalHealthSummaryResource.class);

    private static final String ENTITY_NAME = "mentalHealthSummary";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MentalHealthSummaryService mentalHealthSummaryService;

    private final MentalHealthSummaryRepository mentalHealthSummaryRepository;

    private final MentalHealthSummaryQueryService mentalHealthSummaryQueryService;

    public MentalHealthSummaryResource(
        MentalHealthSummaryService mentalHealthSummaryService,
        MentalHealthSummaryRepository mentalHealthSummaryRepository,
        MentalHealthSummaryQueryService mentalHealthSummaryQueryService
    ) {
        this.mentalHealthSummaryService = mentalHealthSummaryService;
        this.mentalHealthSummaryRepository = mentalHealthSummaryRepository;
        this.mentalHealthSummaryQueryService = mentalHealthSummaryQueryService;
    }

    /**
     * {@code POST  /mental-health-summaries} : Create a new mentalHealthSummary.
     *
     * @param mentalHealthSummary the mentalHealthSummary to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new mentalHealthSummary, or with status {@code 400 (Bad Request)} if the mentalHealthSummary has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/mental-health-summaries")
    public ResponseEntity<MentalHealthSummary> createMentalHealthSummary(@RequestBody MentalHealthSummary mentalHealthSummary)
        throws URISyntaxException {
        log.debug("REST request to save MentalHealthSummary : {}", mentalHealthSummary);
        if (mentalHealthSummary.getId() != null) {
            throw new BadRequestAlertException("A new mentalHealthSummary cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MentalHealthSummary result = mentalHealthSummaryService.save(mentalHealthSummary);
        return ResponseEntity
            .created(new URI("/api/mental-health-summaries/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /mental-health-summaries/:id} : Updates an existing mentalHealthSummary.
     *
     * @param id the id of the mentalHealthSummary to save.
     * @param mentalHealthSummary the mentalHealthSummary to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mentalHealthSummary,
     * or with status {@code 400 (Bad Request)} if the mentalHealthSummary is not valid,
     * or with status {@code 500 (Internal Server Error)} if the mentalHealthSummary couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/mental-health-summaries/{id}")
    public ResponseEntity<MentalHealthSummary> updateMentalHealthSummary(
        @PathVariable(value = "id", required = false) final UUID id,
        @RequestBody MentalHealthSummary mentalHealthSummary
    ) throws URISyntaxException {
        log.debug("REST request to update MentalHealthSummary : {}, {}", id, mentalHealthSummary);
        if (mentalHealthSummary.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, mentalHealthSummary.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!mentalHealthSummaryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        MentalHealthSummary result = mentalHealthSummaryService.update(mentalHealthSummary);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, mentalHealthSummary.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /mental-health-summaries/:id} : Partial updates given fields of an existing mentalHealthSummary, field will ignore if it is null
     *
     * @param id the id of the mentalHealthSummary to save.
     * @param mentalHealthSummary the mentalHealthSummary to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mentalHealthSummary,
     * or with status {@code 400 (Bad Request)} if the mentalHealthSummary is not valid,
     * or with status {@code 404 (Not Found)} if the mentalHealthSummary is not found,
     * or with status {@code 500 (Internal Server Error)} if the mentalHealthSummary couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/mental-health-summaries/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<MentalHealthSummary> partialUpdateMentalHealthSummary(
        @PathVariable(value = "id", required = false) final UUID id,
        @RequestBody MentalHealthSummary mentalHealthSummary
    ) throws URISyntaxException {
        log.debug("REST request to partial update MentalHealthSummary partially : {}, {}", id, mentalHealthSummary);
        if (mentalHealthSummary.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, mentalHealthSummary.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!mentalHealthSummaryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<MentalHealthSummary> result = mentalHealthSummaryService.partialUpdate(mentalHealthSummary);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, mentalHealthSummary.getId().toString())
        );
    }

    /**
     * {@code GET  /mental-health-summaries} : get all the mentalHealthSummaries.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of mentalHealthSummaries in body.
     */
    @GetMapping("/mental-health-summaries")
    public ResponseEntity<List<MentalHealthSummary>> getAllMentalHealthSummaries(
        MentalHealthSummaryCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get MentalHealthSummaries by criteria: {}", criteria);
        Page<MentalHealthSummary> page = mentalHealthSummaryQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /mental-health-summaries/count} : count all the mentalHealthSummaries.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/mental-health-summaries/count")
    public ResponseEntity<Long> countMentalHealthSummaries(MentalHealthSummaryCriteria criteria) {
        log.debug("REST request to count MentalHealthSummaries by criteria: {}", criteria);
        return ResponseEntity.ok().body(mentalHealthSummaryQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /mental-health-summaries/:id} : get the "id" mentalHealthSummary.
     *
     * @param id the id of the mentalHealthSummary to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the mentalHealthSummary, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/mental-health-summaries/{id}")
    public ResponseEntity<MentalHealthSummary> getMentalHealthSummary(@PathVariable UUID id) {
        log.debug("REST request to get MentalHealthSummary : {}", id);
        Optional<MentalHealthSummary> mentalHealthSummary = mentalHealthSummaryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(mentalHealthSummary);
    }

    /**
     * {@code DELETE  /mental-health-summaries/:id} : delete the "id" mentalHealthSummary.
     *
     * @param id the id of the mentalHealthSummary to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/mental-health-summaries/{id}")
    public ResponseEntity<Void> deleteMentalHealthSummary(@PathVariable UUID id) {
        log.debug("REST request to delete MentalHealthSummary : {}", id);
        mentalHealthSummaryService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
