package com.be4tech.b4carecollect.web.rest;

import com.be4tech.b4carecollect.domain.CyclingWheelRevolution;
import com.be4tech.b4carecollect.repository.CyclingWheelRevolutionRepository;
import com.be4tech.b4carecollect.service.CyclingWheelRevolutionQueryService;
import com.be4tech.b4carecollect.service.CyclingWheelRevolutionService;
import com.be4tech.b4carecollect.service.criteria.CyclingWheelRevolutionCriteria;
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
 * REST controller for managing {@link com.be4tech.b4carecollect.domain.CyclingWheelRevolution}.
 */
@RestController
@RequestMapping("/api")
public class CyclingWheelRevolutionResource {

    private final Logger log = LoggerFactory.getLogger(CyclingWheelRevolutionResource.class);

    private static final String ENTITY_NAME = "cyclingWheelRevolution";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CyclingWheelRevolutionService cyclingWheelRevolutionService;

    private final CyclingWheelRevolutionRepository cyclingWheelRevolutionRepository;

    private final CyclingWheelRevolutionQueryService cyclingWheelRevolutionQueryService;

    public CyclingWheelRevolutionResource(
        CyclingWheelRevolutionService cyclingWheelRevolutionService,
        CyclingWheelRevolutionRepository cyclingWheelRevolutionRepository,
        CyclingWheelRevolutionQueryService cyclingWheelRevolutionQueryService
    ) {
        this.cyclingWheelRevolutionService = cyclingWheelRevolutionService;
        this.cyclingWheelRevolutionRepository = cyclingWheelRevolutionRepository;
        this.cyclingWheelRevolutionQueryService = cyclingWheelRevolutionQueryService;
    }

    /**
     * {@code POST  /cycling-wheel-revolutions} : Create a new cyclingWheelRevolution.
     *
     * @param cyclingWheelRevolution the cyclingWheelRevolution to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cyclingWheelRevolution, or with status {@code 400 (Bad Request)} if the cyclingWheelRevolution has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/cycling-wheel-revolutions")
    public ResponseEntity<CyclingWheelRevolution> createCyclingWheelRevolution(@RequestBody CyclingWheelRevolution cyclingWheelRevolution)
        throws URISyntaxException {
        log.debug("REST request to save CyclingWheelRevolution : {}", cyclingWheelRevolution);
        if (cyclingWheelRevolution.getId() != null) {
            throw new BadRequestAlertException("A new cyclingWheelRevolution cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CyclingWheelRevolution result = cyclingWheelRevolutionService.save(cyclingWheelRevolution);
        return ResponseEntity
            .created(new URI("/api/cycling-wheel-revolutions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /cycling-wheel-revolutions/:id} : Updates an existing cyclingWheelRevolution.
     *
     * @param id the id of the cyclingWheelRevolution to save.
     * @param cyclingWheelRevolution the cyclingWheelRevolution to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cyclingWheelRevolution,
     * or with status {@code 400 (Bad Request)} if the cyclingWheelRevolution is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cyclingWheelRevolution couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/cycling-wheel-revolutions/{id}")
    public ResponseEntity<CyclingWheelRevolution> updateCyclingWheelRevolution(
        @PathVariable(value = "id", required = false) final UUID id,
        @RequestBody CyclingWheelRevolution cyclingWheelRevolution
    ) throws URISyntaxException {
        log.debug("REST request to update CyclingWheelRevolution : {}, {}", id, cyclingWheelRevolution);
        if (cyclingWheelRevolution.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, cyclingWheelRevolution.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!cyclingWheelRevolutionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CyclingWheelRevolution result = cyclingWheelRevolutionService.update(cyclingWheelRevolution);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cyclingWheelRevolution.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /cycling-wheel-revolutions/:id} : Partial updates given fields of an existing cyclingWheelRevolution, field will ignore if it is null
     *
     * @param id the id of the cyclingWheelRevolution to save.
     * @param cyclingWheelRevolution the cyclingWheelRevolution to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cyclingWheelRevolution,
     * or with status {@code 400 (Bad Request)} if the cyclingWheelRevolution is not valid,
     * or with status {@code 404 (Not Found)} if the cyclingWheelRevolution is not found,
     * or with status {@code 500 (Internal Server Error)} if the cyclingWheelRevolution couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/cycling-wheel-revolutions/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CyclingWheelRevolution> partialUpdateCyclingWheelRevolution(
        @PathVariable(value = "id", required = false) final UUID id,
        @RequestBody CyclingWheelRevolution cyclingWheelRevolution
    ) throws URISyntaxException {
        log.debug("REST request to partial update CyclingWheelRevolution partially : {}, {}", id, cyclingWheelRevolution);
        if (cyclingWheelRevolution.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, cyclingWheelRevolution.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!cyclingWheelRevolutionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CyclingWheelRevolution> result = cyclingWheelRevolutionService.partialUpdate(cyclingWheelRevolution);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cyclingWheelRevolution.getId().toString())
        );
    }

    /**
     * {@code GET  /cycling-wheel-revolutions} : get all the cyclingWheelRevolutions.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cyclingWheelRevolutions in body.
     */
    @GetMapping("/cycling-wheel-revolutions")
    public ResponseEntity<List<CyclingWheelRevolution>> getAllCyclingWheelRevolutions(
        CyclingWheelRevolutionCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get CyclingWheelRevolutions by criteria: {}", criteria);
        Page<CyclingWheelRevolution> page = cyclingWheelRevolutionQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /cycling-wheel-revolutions/count} : count all the cyclingWheelRevolutions.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/cycling-wheel-revolutions/count")
    public ResponseEntity<Long> countCyclingWheelRevolutions(CyclingWheelRevolutionCriteria criteria) {
        log.debug("REST request to count CyclingWheelRevolutions by criteria: {}", criteria);
        return ResponseEntity.ok().body(cyclingWheelRevolutionQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /cycling-wheel-revolutions/:id} : get the "id" cyclingWheelRevolution.
     *
     * @param id the id of the cyclingWheelRevolution to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cyclingWheelRevolution, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/cycling-wheel-revolutions/{id}")
    public ResponseEntity<CyclingWheelRevolution> getCyclingWheelRevolution(@PathVariable UUID id) {
        log.debug("REST request to get CyclingWheelRevolution : {}", id);
        Optional<CyclingWheelRevolution> cyclingWheelRevolution = cyclingWheelRevolutionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(cyclingWheelRevolution);
    }

    /**
     * {@code DELETE  /cycling-wheel-revolutions/:id} : delete the "id" cyclingWheelRevolution.
     *
     * @param id the id of the cyclingWheelRevolution to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/cycling-wheel-revolutions/{id}")
    public ResponseEntity<Void> deleteCyclingWheelRevolution(@PathVariable UUID id) {
        log.debug("REST request to delete CyclingWheelRevolution : {}", id);
        cyclingWheelRevolutionService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
