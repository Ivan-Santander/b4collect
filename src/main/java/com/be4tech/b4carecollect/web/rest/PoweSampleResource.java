package com.be4tech.b4carecollect.web.rest;

import com.be4tech.b4carecollect.domain.PoweSample;
import com.be4tech.b4carecollect.repository.PoweSampleRepository;
import com.be4tech.b4carecollect.service.PoweSampleQueryService;
import com.be4tech.b4carecollect.service.PoweSampleService;
import com.be4tech.b4carecollect.service.criteria.PoweSampleCriteria;
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
 * REST controller for managing {@link com.be4tech.b4carecollect.domain.PoweSample}.
 */
@RestController
@RequestMapping("/api")
public class PoweSampleResource {

    private final Logger log = LoggerFactory.getLogger(PoweSampleResource.class);

    private static final String ENTITY_NAME = "poweSample";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PoweSampleService poweSampleService;

    private final PoweSampleRepository poweSampleRepository;

    private final PoweSampleQueryService poweSampleQueryService;

    public PoweSampleResource(
        PoweSampleService poweSampleService,
        PoweSampleRepository poweSampleRepository,
        PoweSampleQueryService poweSampleQueryService
    ) {
        this.poweSampleService = poweSampleService;
        this.poweSampleRepository = poweSampleRepository;
        this.poweSampleQueryService = poweSampleQueryService;
    }

    /**
     * {@code POST  /powe-samples} : Create a new poweSample.
     *
     * @param poweSample the poweSample to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new poweSample, or with status {@code 400 (Bad Request)} if the poweSample has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/powe-samples")
    public ResponseEntity<PoweSample> createPoweSample(@RequestBody PoweSample poweSample) throws URISyntaxException {
        log.debug("REST request to save PoweSample : {}", poweSample);
        if (poweSample.getId() != null) {
            throw new BadRequestAlertException("A new poweSample cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PoweSample result = poweSampleService.save(poweSample);
        return ResponseEntity
            .created(new URI("/api/powe-samples/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /powe-samples/:id} : Updates an existing poweSample.
     *
     * @param id the id of the poweSample to save.
     * @param poweSample the poweSample to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated poweSample,
     * or with status {@code 400 (Bad Request)} if the poweSample is not valid,
     * or with status {@code 500 (Internal Server Error)} if the poweSample couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/powe-samples/{id}")
    public ResponseEntity<PoweSample> updatePoweSample(
        @PathVariable(value = "id", required = false) final UUID id,
        @RequestBody PoweSample poweSample
    ) throws URISyntaxException {
        log.debug("REST request to update PoweSample : {}, {}", id, poweSample);
        if (poweSample.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, poweSample.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!poweSampleRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PoweSample result = poweSampleService.update(poweSample);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, poweSample.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /powe-samples/:id} : Partial updates given fields of an existing poweSample, field will ignore if it is null
     *
     * @param id the id of the poweSample to save.
     * @param poweSample the poweSample to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated poweSample,
     * or with status {@code 400 (Bad Request)} if the poweSample is not valid,
     * or with status {@code 404 (Not Found)} if the poweSample is not found,
     * or with status {@code 500 (Internal Server Error)} if the poweSample couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/powe-samples/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PoweSample> partialUpdatePoweSample(
        @PathVariable(value = "id", required = false) final UUID id,
        @RequestBody PoweSample poweSample
    ) throws URISyntaxException {
        log.debug("REST request to partial update PoweSample partially : {}, {}", id, poweSample);
        if (poweSample.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, poweSample.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!poweSampleRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PoweSample> result = poweSampleService.partialUpdate(poweSample);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, poweSample.getId().toString())
        );
    }

    /**
     * {@code GET  /powe-samples} : get all the poweSamples.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of poweSamples in body.
     */
    @GetMapping("/powe-samples")
    public ResponseEntity<List<PoweSample>> getAllPoweSamples(
        PoweSampleCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get PoweSamples by criteria: {}", criteria);
        Page<PoweSample> page = poweSampleQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /powe-samples/count} : count all the poweSamples.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/powe-samples/count")
    public ResponseEntity<Long> countPoweSamples(PoweSampleCriteria criteria) {
        log.debug("REST request to count PoweSamples by criteria: {}", criteria);
        return ResponseEntity.ok().body(poweSampleQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /powe-samples/:id} : get the "id" poweSample.
     *
     * @param id the id of the poweSample to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the poweSample, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/powe-samples/{id}")
    public ResponseEntity<PoweSample> getPoweSample(@PathVariable UUID id) {
        log.debug("REST request to get PoweSample : {}", id);
        Optional<PoweSample> poweSample = poweSampleService.findOne(id);
        return ResponseUtil.wrapOrNotFound(poweSample);
    }

    /**
     * {@code DELETE  /powe-samples/:id} : delete the "id" poweSample.
     *
     * @param id the id of the poweSample to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/powe-samples/{id}")
    public ResponseEntity<Void> deletePoweSample(@PathVariable UUID id) {
        log.debug("REST request to delete PoweSample : {}", id);
        poweSampleService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
