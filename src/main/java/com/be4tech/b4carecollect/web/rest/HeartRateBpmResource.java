package com.be4tech.b4carecollect.web.rest;

import com.be4tech.b4carecollect.domain.HeartRateBpm;
import com.be4tech.b4carecollect.repository.HeartRateBpmRepository;
import com.be4tech.b4carecollect.service.HeartRateBpmQueryService;
import com.be4tech.b4carecollect.service.HeartRateBpmService;
import com.be4tech.b4carecollect.service.criteria.HeartRateBpmCriteria;
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
 * REST controller for managing {@link com.be4tech.b4carecollect.domain.HeartRateBpm}.
 */
@RestController
@RequestMapping("/api")
public class HeartRateBpmResource {

    private final Logger log = LoggerFactory.getLogger(HeartRateBpmResource.class);

    private static final String ENTITY_NAME = "heartRateBpm";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final HeartRateBpmService heartRateBpmService;

    private final HeartRateBpmRepository heartRateBpmRepository;

    private final HeartRateBpmQueryService heartRateBpmQueryService;

    public HeartRateBpmResource(
        HeartRateBpmService heartRateBpmService,
        HeartRateBpmRepository heartRateBpmRepository,
        HeartRateBpmQueryService heartRateBpmQueryService
    ) {
        this.heartRateBpmService = heartRateBpmService;
        this.heartRateBpmRepository = heartRateBpmRepository;
        this.heartRateBpmQueryService = heartRateBpmQueryService;
    }

    /**
     * {@code POST  /heart-rate-bpms} : Create a new heartRateBpm.
     *
     * @param heartRateBpm the heartRateBpm to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new heartRateBpm, or with status {@code 400 (Bad Request)} if the heartRateBpm has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/heart-rate-bpms")
    public ResponseEntity<HeartRateBpm> createHeartRateBpm(@RequestBody HeartRateBpm heartRateBpm) throws URISyntaxException {
        log.debug("REST request to save HeartRateBpm : {}", heartRateBpm);
        if (heartRateBpm.getId() != null) {
            throw new BadRequestAlertException("A new heartRateBpm cannot already have an ID", ENTITY_NAME, "idexists");
        }
        HeartRateBpm result = heartRateBpmService.save(heartRateBpm);
        return ResponseEntity
            .created(new URI("/api/heart-rate-bpms/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /heart-rate-bpms/:id} : Updates an existing heartRateBpm.
     *
     * @param id the id of the heartRateBpm to save.
     * @param heartRateBpm the heartRateBpm to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated heartRateBpm,
     * or with status {@code 400 (Bad Request)} if the heartRateBpm is not valid,
     * or with status {@code 500 (Internal Server Error)} if the heartRateBpm couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/heart-rate-bpms/{id}")
    public ResponseEntity<HeartRateBpm> updateHeartRateBpm(
        @PathVariable(value = "id", required = false) final UUID id,
        @RequestBody HeartRateBpm heartRateBpm
    ) throws URISyntaxException {
        log.debug("REST request to update HeartRateBpm : {}, {}", id, heartRateBpm);
        if (heartRateBpm.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, heartRateBpm.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!heartRateBpmRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        HeartRateBpm result = heartRateBpmService.update(heartRateBpm);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, heartRateBpm.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /heart-rate-bpms/:id} : Partial updates given fields of an existing heartRateBpm, field will ignore if it is null
     *
     * @param id the id of the heartRateBpm to save.
     * @param heartRateBpm the heartRateBpm to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated heartRateBpm,
     * or with status {@code 400 (Bad Request)} if the heartRateBpm is not valid,
     * or with status {@code 404 (Not Found)} if the heartRateBpm is not found,
     * or with status {@code 500 (Internal Server Error)} if the heartRateBpm couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/heart-rate-bpms/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<HeartRateBpm> partialUpdateHeartRateBpm(
        @PathVariable(value = "id", required = false) final UUID id,
        @RequestBody HeartRateBpm heartRateBpm
    ) throws URISyntaxException {
        log.debug("REST request to partial update HeartRateBpm partially : {}, {}", id, heartRateBpm);
        if (heartRateBpm.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, heartRateBpm.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!heartRateBpmRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<HeartRateBpm> result = heartRateBpmService.partialUpdate(heartRateBpm);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, heartRateBpm.getId().toString())
        );
    }

    /**
     * {@code GET  /heart-rate-bpms} : get all the heartRateBpms.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of heartRateBpms in body.
     */
    @GetMapping("/heart-rate-bpms")
    public ResponseEntity<List<HeartRateBpm>> getAllHeartRateBpms(
        HeartRateBpmCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get HeartRateBpms by criteria: {}", criteria);
        Page<HeartRateBpm> page = heartRateBpmQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /heart-rate-bpms/count} : count all the heartRateBpms.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/heart-rate-bpms/count")
    public ResponseEntity<Long> countHeartRateBpms(HeartRateBpmCriteria criteria) {
        log.debug("REST request to count HeartRateBpms by criteria: {}", criteria);
        return ResponseEntity.ok().body(heartRateBpmQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /heart-rate-bpms/:id} : get the "id" heartRateBpm.
     *
     * @param id the id of the heartRateBpm to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the heartRateBpm, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/heart-rate-bpms/{id}")
    public ResponseEntity<HeartRateBpm> getHeartRateBpm(@PathVariable UUID id) {
        log.debug("REST request to get HeartRateBpm : {}", id);
        Optional<HeartRateBpm> heartRateBpm = heartRateBpmService.findOne(id);
        return ResponseUtil.wrapOrNotFound(heartRateBpm);
    }

    /**
     * {@code DELETE  /heart-rate-bpms/:id} : delete the "id" heartRateBpm.
     *
     * @param id the id of the heartRateBpm to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/heart-rate-bpms/{id}")
    public ResponseEntity<Void> deleteHeartRateBpm(@PathVariable UUID id) {
        log.debug("REST request to delete HeartRateBpm : {}", id);
        heartRateBpmService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
