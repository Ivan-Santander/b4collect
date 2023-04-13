package com.be4tech.b4carecollect.web.rest;

import com.be4tech.b4carecollect.domain.DistanceDelta;
import com.be4tech.b4carecollect.repository.DistanceDeltaRepository;
import com.be4tech.b4carecollect.service.DistanceDeltaQueryService;
import com.be4tech.b4carecollect.service.DistanceDeltaService;
import com.be4tech.b4carecollect.service.criteria.DistanceDeltaCriteria;
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
 * REST controller for managing {@link com.be4tech.b4carecollect.domain.DistanceDelta}.
 */
@RestController
@RequestMapping("/api")
public class DistanceDeltaResource {

    private final Logger log = LoggerFactory.getLogger(DistanceDeltaResource.class);

    private static final String ENTITY_NAME = "distanceDelta";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DistanceDeltaService distanceDeltaService;

    private final DistanceDeltaRepository distanceDeltaRepository;

    private final DistanceDeltaQueryService distanceDeltaQueryService;

    public DistanceDeltaResource(
        DistanceDeltaService distanceDeltaService,
        DistanceDeltaRepository distanceDeltaRepository,
        DistanceDeltaQueryService distanceDeltaQueryService
    ) {
        this.distanceDeltaService = distanceDeltaService;
        this.distanceDeltaRepository = distanceDeltaRepository;
        this.distanceDeltaQueryService = distanceDeltaQueryService;
    }

    /**
     * {@code POST  /distance-deltas} : Create a new distanceDelta.
     *
     * @param distanceDelta the distanceDelta to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new distanceDelta, or with status {@code 400 (Bad Request)} if the distanceDelta has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/distance-deltas")
    public ResponseEntity<DistanceDelta> createDistanceDelta(@RequestBody DistanceDelta distanceDelta) throws URISyntaxException {
        log.debug("REST request to save DistanceDelta : {}", distanceDelta);
        if (distanceDelta.getId() != null) {
            throw new BadRequestAlertException("A new distanceDelta cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DistanceDelta result = distanceDeltaService.save(distanceDelta);
        return ResponseEntity
            .created(new URI("/api/distance-deltas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /distance-deltas/:id} : Updates an existing distanceDelta.
     *
     * @param id the id of the distanceDelta to save.
     * @param distanceDelta the distanceDelta to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated distanceDelta,
     * or with status {@code 400 (Bad Request)} if the distanceDelta is not valid,
     * or with status {@code 500 (Internal Server Error)} if the distanceDelta couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/distance-deltas/{id}")
    public ResponseEntity<DistanceDelta> updateDistanceDelta(
        @PathVariable(value = "id", required = false) final UUID id,
        @RequestBody DistanceDelta distanceDelta
    ) throws URISyntaxException {
        log.debug("REST request to update DistanceDelta : {}, {}", id, distanceDelta);
        if (distanceDelta.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, distanceDelta.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!distanceDeltaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        DistanceDelta result = distanceDeltaService.update(distanceDelta);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, distanceDelta.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /distance-deltas/:id} : Partial updates given fields of an existing distanceDelta, field will ignore if it is null
     *
     * @param id the id of the distanceDelta to save.
     * @param distanceDelta the distanceDelta to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated distanceDelta,
     * or with status {@code 400 (Bad Request)} if the distanceDelta is not valid,
     * or with status {@code 404 (Not Found)} if the distanceDelta is not found,
     * or with status {@code 500 (Internal Server Error)} if the distanceDelta couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/distance-deltas/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<DistanceDelta> partialUpdateDistanceDelta(
        @PathVariable(value = "id", required = false) final UUID id,
        @RequestBody DistanceDelta distanceDelta
    ) throws URISyntaxException {
        log.debug("REST request to partial update DistanceDelta partially : {}, {}", id, distanceDelta);
        if (distanceDelta.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, distanceDelta.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!distanceDeltaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<DistanceDelta> result = distanceDeltaService.partialUpdate(distanceDelta);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, distanceDelta.getId().toString())
        );
    }

    /**
     * {@code GET  /distance-deltas} : get all the distanceDeltas.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of distanceDeltas in body.
     */
    @GetMapping("/distance-deltas")
    public ResponseEntity<List<DistanceDelta>> getAllDistanceDeltas(
        DistanceDeltaCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get DistanceDeltas by criteria: {}", criteria);
        Page<DistanceDelta> page = distanceDeltaQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /distance-deltas/count} : count all the distanceDeltas.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/distance-deltas/count")
    public ResponseEntity<Long> countDistanceDeltas(DistanceDeltaCriteria criteria) {
        log.debug("REST request to count DistanceDeltas by criteria: {}", criteria);
        return ResponseEntity.ok().body(distanceDeltaQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /distance-deltas/:id} : get the "id" distanceDelta.
     *
     * @param id the id of the distanceDelta to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the distanceDelta, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/distance-deltas/{id}")
    public ResponseEntity<DistanceDelta> getDistanceDelta(@PathVariable UUID id) {
        log.debug("REST request to get DistanceDelta : {}", id);
        Optional<DistanceDelta> distanceDelta = distanceDeltaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(distanceDelta);
    }

    /**
     * {@code DELETE  /distance-deltas/:id} : delete the "id" distanceDelta.
     *
     * @param id the id of the distanceDelta to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/distance-deltas/{id}")
    public ResponseEntity<Void> deleteDistanceDelta(@PathVariable UUID id) {
        log.debug("REST request to delete DistanceDelta : {}", id);
        distanceDeltaService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
