package com.be4tech.b4carecollect.web.rest;

import com.be4tech.b4carecollect.domain.SleepScores;
import com.be4tech.b4carecollect.repository.SleepScoresRepository;
import com.be4tech.b4carecollect.service.SleepScoresQueryService;
import com.be4tech.b4carecollect.service.SleepScoresService;
import com.be4tech.b4carecollect.service.criteria.SleepScoresCriteria;
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
 * REST controller for managing {@link com.be4tech.b4carecollect.domain.SleepScores}.
 */
@RestController
@RequestMapping("/api")
public class SleepScoresResource {

    private final Logger log = LoggerFactory.getLogger(SleepScoresResource.class);

    private static final String ENTITY_NAME = "sleepScores";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SleepScoresService sleepScoresService;

    private final SleepScoresRepository sleepScoresRepository;

    private final SleepScoresQueryService sleepScoresQueryService;

    public SleepScoresResource(
        SleepScoresService sleepScoresService,
        SleepScoresRepository sleepScoresRepository,
        SleepScoresQueryService sleepScoresQueryService
    ) {
        this.sleepScoresService = sleepScoresService;
        this.sleepScoresRepository = sleepScoresRepository;
        this.sleepScoresQueryService = sleepScoresQueryService;
    }

    /**
     * {@code POST  /sleep-scores} : Create a new sleepScores.
     *
     * @param sleepScores the sleepScores to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new sleepScores, or with status {@code 400 (Bad Request)} if the sleepScores has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/sleep-scores")
    public ResponseEntity<SleepScores> createSleepScores(@RequestBody SleepScores sleepScores) throws URISyntaxException {
        log.debug("REST request to save SleepScores : {}", sleepScores);
        if (sleepScores.getId() != null) {
            throw new BadRequestAlertException("A new sleepScores cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SleepScores result = sleepScoresService.save(sleepScores);
        return ResponseEntity
            .created(new URI("/api/sleep-scores/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /sleep-scores/:id} : Updates an existing sleepScores.
     *
     * @param id the id of the sleepScores to save.
     * @param sleepScores the sleepScores to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sleepScores,
     * or with status {@code 400 (Bad Request)} if the sleepScores is not valid,
     * or with status {@code 500 (Internal Server Error)} if the sleepScores couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/sleep-scores/{id}")
    public ResponseEntity<SleepScores> updateSleepScores(
        @PathVariable(value = "id", required = false) final UUID id,
        @RequestBody SleepScores sleepScores
    ) throws URISyntaxException {
        log.debug("REST request to update SleepScores : {}, {}", id, sleepScores);
        if (sleepScores.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, sleepScores.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!sleepScoresRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        SleepScores result = sleepScoresService.update(sleepScores);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, sleepScores.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /sleep-scores/:id} : Partial updates given fields of an existing sleepScores, field will ignore if it is null
     *
     * @param id the id of the sleepScores to save.
     * @param sleepScores the sleepScores to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sleepScores,
     * or with status {@code 400 (Bad Request)} if the sleepScores is not valid,
     * or with status {@code 404 (Not Found)} if the sleepScores is not found,
     * or with status {@code 500 (Internal Server Error)} if the sleepScores couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/sleep-scores/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<SleepScores> partialUpdateSleepScores(
        @PathVariable(value = "id", required = false) final UUID id,
        @RequestBody SleepScores sleepScores
    ) throws URISyntaxException {
        log.debug("REST request to partial update SleepScores partially : {}, {}", id, sleepScores);
        if (sleepScores.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, sleepScores.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!sleepScoresRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<SleepScores> result = sleepScoresService.partialUpdate(sleepScores);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, sleepScores.getId().toString())
        );
    }

    /**
     * {@code GET  /sleep-scores} : get all the sleepScores.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of sleepScores in body.
     */
    @GetMapping("/sleep-scores")
    public ResponseEntity<List<SleepScores>> getAllSleepScores(
        SleepScoresCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get SleepScores by criteria: {}", criteria);
        Page<SleepScores> page = sleepScoresQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /sleep-scores/count} : count all the sleepScores.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/sleep-scores/count")
    public ResponseEntity<Long> countSleepScores(SleepScoresCriteria criteria) {
        log.debug("REST request to count SleepScores by criteria: {}", criteria);
        return ResponseEntity.ok().body(sleepScoresQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /sleep-scores/:id} : get the "id" sleepScores.
     *
     * @param id the id of the sleepScores to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the sleepScores, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/sleep-scores/{id}")
    public ResponseEntity<SleepScores> getSleepScores(@PathVariable UUID id) {
        log.debug("REST request to get SleepScores : {}", id);
        Optional<SleepScores> sleepScores = sleepScoresService.findOne(id);
        return ResponseUtil.wrapOrNotFound(sleepScores);
    }

    /**
     * {@code DELETE  /sleep-scores/:id} : delete the "id" sleepScores.
     *
     * @param id the id of the sleepScores to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/sleep-scores/{id}")
    public ResponseEntity<Void> deleteSleepScores(@PathVariable UUID id) {
        log.debug("REST request to delete SleepScores : {}", id);
        sleepScoresService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
