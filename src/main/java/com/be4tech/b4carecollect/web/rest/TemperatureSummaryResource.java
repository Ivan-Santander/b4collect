package com.be4tech.b4carecollect.web.rest;

import com.be4tech.b4carecollect.domain.TemperatureSummary;
import com.be4tech.b4carecollect.repository.TemperatureSummaryRepository;
import com.be4tech.b4carecollect.service.TemperatureSummaryQueryService;
import com.be4tech.b4carecollect.service.TemperatureSummaryService;
import com.be4tech.b4carecollect.service.criteria.TemperatureSummaryCriteria;
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
 * REST controller for managing {@link com.be4tech.b4carecollect.domain.TemperatureSummary}.
 */
@RestController
@RequestMapping("/api")
public class TemperatureSummaryResource {

    private final Logger log = LoggerFactory.getLogger(TemperatureSummaryResource.class);

    private static final String ENTITY_NAME = "temperatureSummary";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TemperatureSummaryService temperatureSummaryService;

    private final TemperatureSummaryRepository temperatureSummaryRepository;

    private final TemperatureSummaryQueryService temperatureSummaryQueryService;

    public TemperatureSummaryResource(
        TemperatureSummaryService temperatureSummaryService,
        TemperatureSummaryRepository temperatureSummaryRepository,
        TemperatureSummaryQueryService temperatureSummaryQueryService
    ) {
        this.temperatureSummaryService = temperatureSummaryService;
        this.temperatureSummaryRepository = temperatureSummaryRepository;
        this.temperatureSummaryQueryService = temperatureSummaryQueryService;
    }

    /**
     * {@code POST  /temperature-summaries} : Create a new temperatureSummary.
     *
     * @param temperatureSummary the temperatureSummary to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new temperatureSummary, or with status {@code 400 (Bad Request)} if the temperatureSummary has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/temperature-summaries")
    public ResponseEntity<TemperatureSummary> createTemperatureSummary(@RequestBody TemperatureSummary temperatureSummary)
        throws URISyntaxException {
        log.debug("REST request to save TemperatureSummary : {}", temperatureSummary);
        if (temperatureSummary.getId() != null) {
            throw new BadRequestAlertException("A new temperatureSummary cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TemperatureSummary result = temperatureSummaryService.save(temperatureSummary);
        return ResponseEntity
            .created(new URI("/api/temperature-summaries/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /temperature-summaries/:id} : Updates an existing temperatureSummary.
     *
     * @param id the id of the temperatureSummary to save.
     * @param temperatureSummary the temperatureSummary to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated temperatureSummary,
     * or with status {@code 400 (Bad Request)} if the temperatureSummary is not valid,
     * or with status {@code 500 (Internal Server Error)} if the temperatureSummary couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/temperature-summaries/{id}")
    public ResponseEntity<TemperatureSummary> updateTemperatureSummary(
        @PathVariable(value = "id", required = false) final UUID id,
        @RequestBody TemperatureSummary temperatureSummary
    ) throws URISyntaxException {
        log.debug("REST request to update TemperatureSummary : {}, {}", id, temperatureSummary);
        if (temperatureSummary.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, temperatureSummary.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!temperatureSummaryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        TemperatureSummary result = temperatureSummaryService.update(temperatureSummary);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, temperatureSummary.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /temperature-summaries/:id} : Partial updates given fields of an existing temperatureSummary, field will ignore if it is null
     *
     * @param id the id of the temperatureSummary to save.
     * @param temperatureSummary the temperatureSummary to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated temperatureSummary,
     * or with status {@code 400 (Bad Request)} if the temperatureSummary is not valid,
     * or with status {@code 404 (Not Found)} if the temperatureSummary is not found,
     * or with status {@code 500 (Internal Server Error)} if the temperatureSummary couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/temperature-summaries/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<TemperatureSummary> partialUpdateTemperatureSummary(
        @PathVariable(value = "id", required = false) final UUID id,
        @RequestBody TemperatureSummary temperatureSummary
    ) throws URISyntaxException {
        log.debug("REST request to partial update TemperatureSummary partially : {}, {}", id, temperatureSummary);
        if (temperatureSummary.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, temperatureSummary.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!temperatureSummaryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TemperatureSummary> result = temperatureSummaryService.partialUpdate(temperatureSummary);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, temperatureSummary.getId().toString())
        );
    }

    /**
     * {@code GET  /temperature-summaries} : get all the temperatureSummaries.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of temperatureSummaries in body.
     */
    @GetMapping("/temperature-summaries")
    public ResponseEntity<List<TemperatureSummary>> getAllTemperatureSummaries(
        TemperatureSummaryCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get TemperatureSummaries by criteria: {}", criteria);
        Page<TemperatureSummary> page = temperatureSummaryQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /temperature-summaries/count} : count all the temperatureSummaries.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/temperature-summaries/count")
    public ResponseEntity<Long> countTemperatureSummaries(TemperatureSummaryCriteria criteria) {
        log.debug("REST request to count TemperatureSummaries by criteria: {}", criteria);
        return ResponseEntity.ok().body(temperatureSummaryQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /temperature-summaries/:id} : get the "id" temperatureSummary.
     *
     * @param id the id of the temperatureSummary to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the temperatureSummary, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/temperature-summaries/{id}")
    public ResponseEntity<TemperatureSummary> getTemperatureSummary(@PathVariable UUID id) {
        log.debug("REST request to get TemperatureSummary : {}", id);
        Optional<TemperatureSummary> temperatureSummary = temperatureSummaryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(temperatureSummary);
    }

    /**
     * {@code DELETE  /temperature-summaries/:id} : delete the "id" temperatureSummary.
     *
     * @param id the id of the temperatureSummary to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/temperature-summaries/{id}")
    public ResponseEntity<Void> deleteTemperatureSummary(@PathVariable UUID id) {
        log.debug("REST request to delete TemperatureSummary : {}", id);
        temperatureSummaryService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
