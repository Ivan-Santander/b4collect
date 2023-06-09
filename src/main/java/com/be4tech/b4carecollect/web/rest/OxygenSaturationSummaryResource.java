package com.be4tech.b4carecollect.web.rest;

import com.be4tech.b4carecollect.domain.OxygenSaturationSummary;
import com.be4tech.b4carecollect.repository.OxygenSaturationSummaryRepository;
import com.be4tech.b4carecollect.service.OxygenSaturationSummaryQueryService;
import com.be4tech.b4carecollect.service.OxygenSaturationSummaryService;
import com.be4tech.b4carecollect.service.criteria.OxygenSaturationSummaryCriteria;
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
 * REST controller for managing {@link com.be4tech.b4carecollect.domain.OxygenSaturationSummary}.
 */
@RestController
@RequestMapping("/api")
public class OxygenSaturationSummaryResource {

    private final Logger log = LoggerFactory.getLogger(OxygenSaturationSummaryResource.class);

    private static final String ENTITY_NAME = "oxygenSaturationSummary";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OxygenSaturationSummaryService oxygenSaturationSummaryService;

    private final OxygenSaturationSummaryRepository oxygenSaturationSummaryRepository;

    private final OxygenSaturationSummaryQueryService oxygenSaturationSummaryQueryService;

    public OxygenSaturationSummaryResource(
        OxygenSaturationSummaryService oxygenSaturationSummaryService,
        OxygenSaturationSummaryRepository oxygenSaturationSummaryRepository,
        OxygenSaturationSummaryQueryService oxygenSaturationSummaryQueryService
    ) {
        this.oxygenSaturationSummaryService = oxygenSaturationSummaryService;
        this.oxygenSaturationSummaryRepository = oxygenSaturationSummaryRepository;
        this.oxygenSaturationSummaryQueryService = oxygenSaturationSummaryQueryService;
    }

    /**
     * {@code POST  /oxygen-saturation-summaries} : Create a new oxygenSaturationSummary.
     *
     * @param oxygenSaturationSummary the oxygenSaturationSummary to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new oxygenSaturationSummary, or with status {@code 400 (Bad Request)} if the oxygenSaturationSummary has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/oxygen-saturation-summaries")
    public ResponseEntity<OxygenSaturationSummary> createOxygenSaturationSummary(
        @RequestBody OxygenSaturationSummary oxygenSaturationSummary
    ) throws URISyntaxException {
        log.debug("REST request to save OxygenSaturationSummary : {}", oxygenSaturationSummary);
        if (oxygenSaturationSummary.getId() != null) {
            throw new BadRequestAlertException("A new oxygenSaturationSummary cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OxygenSaturationSummary result = oxygenSaturationSummaryService.save(oxygenSaturationSummary);
        return ResponseEntity
            .created(new URI("/api/oxygen-saturation-summaries/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /oxygen-saturation-summaries/:id} : Updates an existing oxygenSaturationSummary.
     *
     * @param id the id of the oxygenSaturationSummary to save.
     * @param oxygenSaturationSummary the oxygenSaturationSummary to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated oxygenSaturationSummary,
     * or with status {@code 400 (Bad Request)} if the oxygenSaturationSummary is not valid,
     * or with status {@code 500 (Internal Server Error)} if the oxygenSaturationSummary couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/oxygen-saturation-summaries/{id}")
    public ResponseEntity<OxygenSaturationSummary> updateOxygenSaturationSummary(
        @PathVariable(value = "id", required = false) final UUID id,
        @RequestBody OxygenSaturationSummary oxygenSaturationSummary
    ) throws URISyntaxException {
        log.debug("REST request to update OxygenSaturationSummary : {}, {}", id, oxygenSaturationSummary);
        if (oxygenSaturationSummary.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, oxygenSaturationSummary.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!oxygenSaturationSummaryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        OxygenSaturationSummary result = oxygenSaturationSummaryService.update(oxygenSaturationSummary);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, oxygenSaturationSummary.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /oxygen-saturation-summaries/:id} : Partial updates given fields of an existing oxygenSaturationSummary, field will ignore if it is null
     *
     * @param id the id of the oxygenSaturationSummary to save.
     * @param oxygenSaturationSummary the oxygenSaturationSummary to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated oxygenSaturationSummary,
     * or with status {@code 400 (Bad Request)} if the oxygenSaturationSummary is not valid,
     * or with status {@code 404 (Not Found)} if the oxygenSaturationSummary is not found,
     * or with status {@code 500 (Internal Server Error)} if the oxygenSaturationSummary couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/oxygen-saturation-summaries/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<OxygenSaturationSummary> partialUpdateOxygenSaturationSummary(
        @PathVariable(value = "id", required = false) final UUID id,
        @RequestBody OxygenSaturationSummary oxygenSaturationSummary
    ) throws URISyntaxException {
        log.debug("REST request to partial update OxygenSaturationSummary partially : {}, {}", id, oxygenSaturationSummary);
        if (oxygenSaturationSummary.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, oxygenSaturationSummary.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!oxygenSaturationSummaryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<OxygenSaturationSummary> result = oxygenSaturationSummaryService.partialUpdate(oxygenSaturationSummary);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, oxygenSaturationSummary.getId().toString())
        );
    }

    /**
     * {@code GET  /oxygen-saturation-summaries} : get all the oxygenSaturationSummaries.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of oxygenSaturationSummaries in body.
     */
    @GetMapping("/oxygen-saturation-summaries")
    public ResponseEntity<List<OxygenSaturationSummary>> getAllOxygenSaturationSummaries(
        OxygenSaturationSummaryCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get OxygenSaturationSummaries by criteria: {}", criteria);
        Page<OxygenSaturationSummary> page = oxygenSaturationSummaryQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /oxygen-saturation-summaries/count} : count all the oxygenSaturationSummaries.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/oxygen-saturation-summaries/count")
    public ResponseEntity<Long> countOxygenSaturationSummaries(OxygenSaturationSummaryCriteria criteria) {
        log.debug("REST request to count OxygenSaturationSummaries by criteria: {}", criteria);
        return ResponseEntity.ok().body(oxygenSaturationSummaryQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /oxygen-saturation-summaries/:id} : get the "id" oxygenSaturationSummary.
     *
     * @param id the id of the oxygenSaturationSummary to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the oxygenSaturationSummary, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/oxygen-saturation-summaries/{id}")
    public ResponseEntity<OxygenSaturationSummary> getOxygenSaturationSummary(@PathVariable UUID id) {
        log.debug("REST request to get OxygenSaturationSummary : {}", id);
        Optional<OxygenSaturationSummary> oxygenSaturationSummary = oxygenSaturationSummaryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(oxygenSaturationSummary);
    }

    /**
     * {@code DELETE  /oxygen-saturation-summaries/:id} : delete the "id" oxygenSaturationSummary.
     *
     * @param id the id of the oxygenSaturationSummary to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/oxygen-saturation-summaries/{id}")
    public ResponseEntity<Void> deleteOxygenSaturationSummary(@PathVariable UUID id) {
        log.debug("REST request to delete OxygenSaturationSummary : {}", id);
        oxygenSaturationSummaryService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
