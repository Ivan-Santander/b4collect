package com.be4tech.b4carecollect.web.rest;

import com.be4tech.b4carecollect.domain.AlarmRiskIndexSummary;
import com.be4tech.b4carecollect.repository.AlarmRiskIndexSummaryRepository;
import com.be4tech.b4carecollect.service.AlarmRiskIndexSummaryQueryService;
import com.be4tech.b4carecollect.service.AlarmRiskIndexSummaryService;
import com.be4tech.b4carecollect.service.criteria.AlarmRiskIndexSummaryCriteria;
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
 * REST controller for managing {@link com.be4tech.b4carecollect.domain.AlarmRiskIndexSummary}.
 */
@RestController
@RequestMapping("/api")
public class AlarmRiskIndexSummaryResource {

    private final Logger log = LoggerFactory.getLogger(AlarmRiskIndexSummaryResource.class);

    private static final String ENTITY_NAME = "alarmRiskIndexSummary";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AlarmRiskIndexSummaryService alarmRiskIndexSummaryService;

    private final AlarmRiskIndexSummaryRepository alarmRiskIndexSummaryRepository;

    private final AlarmRiskIndexSummaryQueryService alarmRiskIndexSummaryQueryService;

    public AlarmRiskIndexSummaryResource(
        AlarmRiskIndexSummaryService alarmRiskIndexSummaryService,
        AlarmRiskIndexSummaryRepository alarmRiskIndexSummaryRepository,
        AlarmRiskIndexSummaryQueryService alarmRiskIndexSummaryQueryService
    ) {
        this.alarmRiskIndexSummaryService = alarmRiskIndexSummaryService;
        this.alarmRiskIndexSummaryRepository = alarmRiskIndexSummaryRepository;
        this.alarmRiskIndexSummaryQueryService = alarmRiskIndexSummaryQueryService;
    }

    /**
     * {@code POST  /alarm-risk-index-summaries} : Create a new alarmRiskIndexSummary.
     *
     * @param alarmRiskIndexSummary the alarmRiskIndexSummary to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new alarmRiskIndexSummary, or with status {@code 400 (Bad Request)} if the alarmRiskIndexSummary has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/alarm-risk-index-summaries")
    public ResponseEntity<AlarmRiskIndexSummary> createAlarmRiskIndexSummary(@RequestBody AlarmRiskIndexSummary alarmRiskIndexSummary)
        throws URISyntaxException {
        log.debug("REST request to save AlarmRiskIndexSummary : {}", alarmRiskIndexSummary);
        if (alarmRiskIndexSummary.getId() != null) {
            throw new BadRequestAlertException("A new alarmRiskIndexSummary cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AlarmRiskIndexSummary result = alarmRiskIndexSummaryService.save(alarmRiskIndexSummary);
        return ResponseEntity
            .created(new URI("/api/alarm-risk-index-summaries/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /alarm-risk-index-summaries/:id} : Updates an existing alarmRiskIndexSummary.
     *
     * @param id the id of the alarmRiskIndexSummary to save.
     * @param alarmRiskIndexSummary the alarmRiskIndexSummary to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated alarmRiskIndexSummary,
     * or with status {@code 400 (Bad Request)} if the alarmRiskIndexSummary is not valid,
     * or with status {@code 500 (Internal Server Error)} if the alarmRiskIndexSummary couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/alarm-risk-index-summaries/{id}")
    public ResponseEntity<AlarmRiskIndexSummary> updateAlarmRiskIndexSummary(
        @PathVariable(value = "id", required = false) final UUID id,
        @RequestBody AlarmRiskIndexSummary alarmRiskIndexSummary
    ) throws URISyntaxException {
        log.debug("REST request to update AlarmRiskIndexSummary : {}, {}", id, alarmRiskIndexSummary);
        if (alarmRiskIndexSummary.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, alarmRiskIndexSummary.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!alarmRiskIndexSummaryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        AlarmRiskIndexSummary result = alarmRiskIndexSummaryService.update(alarmRiskIndexSummary);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, alarmRiskIndexSummary.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /alarm-risk-index-summaries/:id} : Partial updates given fields of an existing alarmRiskIndexSummary, field will ignore if it is null
     *
     * @param id the id of the alarmRiskIndexSummary to save.
     * @param alarmRiskIndexSummary the alarmRiskIndexSummary to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated alarmRiskIndexSummary,
     * or with status {@code 400 (Bad Request)} if the alarmRiskIndexSummary is not valid,
     * or with status {@code 404 (Not Found)} if the alarmRiskIndexSummary is not found,
     * or with status {@code 500 (Internal Server Error)} if the alarmRiskIndexSummary couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/alarm-risk-index-summaries/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AlarmRiskIndexSummary> partialUpdateAlarmRiskIndexSummary(
        @PathVariable(value = "id", required = false) final UUID id,
        @RequestBody AlarmRiskIndexSummary alarmRiskIndexSummary
    ) throws URISyntaxException {
        log.debug("REST request to partial update AlarmRiskIndexSummary partially : {}, {}", id, alarmRiskIndexSummary);
        if (alarmRiskIndexSummary.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, alarmRiskIndexSummary.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!alarmRiskIndexSummaryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AlarmRiskIndexSummary> result = alarmRiskIndexSummaryService.partialUpdate(alarmRiskIndexSummary);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, alarmRiskIndexSummary.getId().toString())
        );
    }

    /**
     * {@code GET  /alarm-risk-index-summaries} : get all the alarmRiskIndexSummaries.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of alarmRiskIndexSummaries in body.
     */
    @GetMapping("/alarm-risk-index-summaries")
    public ResponseEntity<List<AlarmRiskIndexSummary>> getAllAlarmRiskIndexSummaries(
        AlarmRiskIndexSummaryCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get AlarmRiskIndexSummaries by criteria: {}", criteria);
        Page<AlarmRiskIndexSummary> page = alarmRiskIndexSummaryQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /alarm-risk-index-summaries/count} : count all the alarmRiskIndexSummaries.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/alarm-risk-index-summaries/count")
    public ResponseEntity<Long> countAlarmRiskIndexSummaries(AlarmRiskIndexSummaryCriteria criteria) {
        log.debug("REST request to count AlarmRiskIndexSummaries by criteria: {}", criteria);
        return ResponseEntity.ok().body(alarmRiskIndexSummaryQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /alarm-risk-index-summaries/:id} : get the "id" alarmRiskIndexSummary.
     *
     * @param id the id of the alarmRiskIndexSummary to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the alarmRiskIndexSummary, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/alarm-risk-index-summaries/{id}")
    public ResponseEntity<AlarmRiskIndexSummary> getAlarmRiskIndexSummary(@PathVariable UUID id) {
        log.debug("REST request to get AlarmRiskIndexSummary : {}", id);
        Optional<AlarmRiskIndexSummary> alarmRiskIndexSummary = alarmRiskIndexSummaryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(alarmRiskIndexSummary);
    }

    /**
     * {@code DELETE  /alarm-risk-index-summaries/:id} : delete the "id" alarmRiskIndexSummary.
     *
     * @param id the id of the alarmRiskIndexSummary to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/alarm-risk-index-summaries/{id}")
    public ResponseEntity<Void> deleteAlarmRiskIndexSummary(@PathVariable UUID id) {
        log.debug("REST request to delete AlarmRiskIndexSummary : {}", id);
        alarmRiskIndexSummaryService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
