package com.be4tech.b4carecollect.web.rest;

import com.be4tech.b4carecollect.domain.PowerSummary;
import com.be4tech.b4carecollect.repository.PowerSummaryRepository;
import com.be4tech.b4carecollect.service.PowerSummaryQueryService;
import com.be4tech.b4carecollect.service.PowerSummaryService;
import com.be4tech.b4carecollect.service.criteria.PowerSummaryCriteria;
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
 * REST controller for managing {@link com.be4tech.b4carecollect.domain.PowerSummary}.
 */
@RestController
@RequestMapping("/api")
public class PowerSummaryResource {

    private final Logger log = LoggerFactory.getLogger(PowerSummaryResource.class);

    private static final String ENTITY_NAME = "powerSummary";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PowerSummaryService powerSummaryService;

    private final PowerSummaryRepository powerSummaryRepository;

    private final PowerSummaryQueryService powerSummaryQueryService;

    public PowerSummaryResource(
        PowerSummaryService powerSummaryService,
        PowerSummaryRepository powerSummaryRepository,
        PowerSummaryQueryService powerSummaryQueryService
    ) {
        this.powerSummaryService = powerSummaryService;
        this.powerSummaryRepository = powerSummaryRepository;
        this.powerSummaryQueryService = powerSummaryQueryService;
    }

    /**
     * {@code POST  /power-summaries} : Create a new powerSummary.
     *
     * @param powerSummary the powerSummary to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new powerSummary, or with status {@code 400 (Bad Request)} if the powerSummary has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/power-summaries")
    public ResponseEntity<PowerSummary> createPowerSummary(@RequestBody PowerSummary powerSummary) throws URISyntaxException {
        log.debug("REST request to save PowerSummary : {}", powerSummary);
        if (powerSummary.getId() != null) {
            throw new BadRequestAlertException("A new powerSummary cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PowerSummary result = powerSummaryService.save(powerSummary);
        return ResponseEntity
            .created(new URI("/api/power-summaries/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /power-summaries/:id} : Updates an existing powerSummary.
     *
     * @param id the id of the powerSummary to save.
     * @param powerSummary the powerSummary to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated powerSummary,
     * or with status {@code 400 (Bad Request)} if the powerSummary is not valid,
     * or with status {@code 500 (Internal Server Error)} if the powerSummary couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/power-summaries/{id}")
    public ResponseEntity<PowerSummary> updatePowerSummary(
        @PathVariable(value = "id", required = false) final UUID id,
        @RequestBody PowerSummary powerSummary
    ) throws URISyntaxException {
        log.debug("REST request to update PowerSummary : {}, {}", id, powerSummary);
        if (powerSummary.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, powerSummary.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!powerSummaryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PowerSummary result = powerSummaryService.update(powerSummary);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, powerSummary.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /power-summaries/:id} : Partial updates given fields of an existing powerSummary, field will ignore if it is null
     *
     * @param id the id of the powerSummary to save.
     * @param powerSummary the powerSummary to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated powerSummary,
     * or with status {@code 400 (Bad Request)} if the powerSummary is not valid,
     * or with status {@code 404 (Not Found)} if the powerSummary is not found,
     * or with status {@code 500 (Internal Server Error)} if the powerSummary couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/power-summaries/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PowerSummary> partialUpdatePowerSummary(
        @PathVariable(value = "id", required = false) final UUID id,
        @RequestBody PowerSummary powerSummary
    ) throws URISyntaxException {
        log.debug("REST request to partial update PowerSummary partially : {}, {}", id, powerSummary);
        if (powerSummary.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, powerSummary.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!powerSummaryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PowerSummary> result = powerSummaryService.partialUpdate(powerSummary);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, powerSummary.getId().toString())
        );
    }

    /**
     * {@code GET  /power-summaries} : get all the powerSummaries.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of powerSummaries in body.
     */
    @GetMapping("/power-summaries")
    public ResponseEntity<List<PowerSummary>> getAllPowerSummaries(
        PowerSummaryCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get PowerSummaries by criteria: {}", criteria);
        Page<PowerSummary> page = powerSummaryQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /power-summaries/count} : count all the powerSummaries.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/power-summaries/count")
    public ResponseEntity<Long> countPowerSummaries(PowerSummaryCriteria criteria) {
        log.debug("REST request to count PowerSummaries by criteria: {}", criteria);
        return ResponseEntity.ok().body(powerSummaryQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /power-summaries/:id} : get the "id" powerSummary.
     *
     * @param id the id of the powerSummary to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the powerSummary, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/power-summaries/{id}")
    public ResponseEntity<PowerSummary> getPowerSummary(@PathVariable UUID id) {
        log.debug("REST request to get PowerSummary : {}", id);
        Optional<PowerSummary> powerSummary = powerSummaryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(powerSummary);
    }

    /**
     * {@code DELETE  /power-summaries/:id} : delete the "id" powerSummary.
     *
     * @param id the id of the powerSummary to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/power-summaries/{id}")
    public ResponseEntity<Void> deletePowerSummary(@PathVariable UUID id) {
        log.debug("REST request to delete PowerSummary : {}", id);
        powerSummaryService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
