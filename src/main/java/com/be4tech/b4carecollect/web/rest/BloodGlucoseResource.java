package com.be4tech.b4carecollect.web.rest;

import com.be4tech.b4carecollect.domain.BloodGlucose;
import com.be4tech.b4carecollect.repository.BloodGlucoseRepository;
import com.be4tech.b4carecollect.service.BloodGlucoseQueryService;
import com.be4tech.b4carecollect.service.BloodGlucoseService;
import com.be4tech.b4carecollect.service.criteria.BloodGlucoseCriteria;
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
 * REST controller for managing {@link com.be4tech.b4carecollect.domain.BloodGlucose}.
 */
@RestController
@RequestMapping("/api")
public class BloodGlucoseResource {

    private final Logger log = LoggerFactory.getLogger(BloodGlucoseResource.class);

    private static final String ENTITY_NAME = "bloodGlucose";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BloodGlucoseService bloodGlucoseService;

    private final BloodGlucoseRepository bloodGlucoseRepository;

    private final BloodGlucoseQueryService bloodGlucoseQueryService;

    public BloodGlucoseResource(
        BloodGlucoseService bloodGlucoseService,
        BloodGlucoseRepository bloodGlucoseRepository,
        BloodGlucoseQueryService bloodGlucoseQueryService
    ) {
        this.bloodGlucoseService = bloodGlucoseService;
        this.bloodGlucoseRepository = bloodGlucoseRepository;
        this.bloodGlucoseQueryService = bloodGlucoseQueryService;
    }

    /**
     * {@code POST  /blood-glucoses} : Create a new bloodGlucose.
     *
     * @param bloodGlucose the bloodGlucose to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new bloodGlucose, or with status {@code 400 (Bad Request)} if the bloodGlucose has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/blood-glucoses")
    public ResponseEntity<BloodGlucose> createBloodGlucose(@RequestBody BloodGlucose bloodGlucose) throws URISyntaxException {
        log.debug("REST request to save BloodGlucose : {}", bloodGlucose);
        if (bloodGlucose.getId() != null) {
            throw new BadRequestAlertException("A new bloodGlucose cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BloodGlucose result = bloodGlucoseService.save(bloodGlucose);
        return ResponseEntity
            .created(new URI("/api/blood-glucoses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /blood-glucoses/:id} : Updates an existing bloodGlucose.
     *
     * @param id the id of the bloodGlucose to save.
     * @param bloodGlucose the bloodGlucose to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bloodGlucose,
     * or with status {@code 400 (Bad Request)} if the bloodGlucose is not valid,
     * or with status {@code 500 (Internal Server Error)} if the bloodGlucose couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/blood-glucoses/{id}")
    public ResponseEntity<BloodGlucose> updateBloodGlucose(
        @PathVariable(value = "id", required = false) final UUID id,
        @RequestBody BloodGlucose bloodGlucose
    ) throws URISyntaxException {
        log.debug("REST request to update BloodGlucose : {}, {}", id, bloodGlucose);
        if (bloodGlucose.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, bloodGlucose.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bloodGlucoseRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        BloodGlucose result = bloodGlucoseService.update(bloodGlucose);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, bloodGlucose.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /blood-glucoses/:id} : Partial updates given fields of an existing bloodGlucose, field will ignore if it is null
     *
     * @param id the id of the bloodGlucose to save.
     * @param bloodGlucose the bloodGlucose to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bloodGlucose,
     * or with status {@code 400 (Bad Request)} if the bloodGlucose is not valid,
     * or with status {@code 404 (Not Found)} if the bloodGlucose is not found,
     * or with status {@code 500 (Internal Server Error)} if the bloodGlucose couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/blood-glucoses/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<BloodGlucose> partialUpdateBloodGlucose(
        @PathVariable(value = "id", required = false) final UUID id,
        @RequestBody BloodGlucose bloodGlucose
    ) throws URISyntaxException {
        log.debug("REST request to partial update BloodGlucose partially : {}, {}", id, bloodGlucose);
        if (bloodGlucose.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, bloodGlucose.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bloodGlucoseRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<BloodGlucose> result = bloodGlucoseService.partialUpdate(bloodGlucose);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, bloodGlucose.getId().toString())
        );
    }

    /**
     * {@code GET  /blood-glucoses} : get all the bloodGlucoses.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of bloodGlucoses in body.
     */
    @GetMapping("/blood-glucoses")
    public ResponseEntity<List<BloodGlucose>> getAllBloodGlucoses(
        BloodGlucoseCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get BloodGlucoses by criteria: {}", criteria);
        Page<BloodGlucose> page = bloodGlucoseQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /blood-glucoses/count} : count all the bloodGlucoses.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/blood-glucoses/count")
    public ResponseEntity<Long> countBloodGlucoses(BloodGlucoseCriteria criteria) {
        log.debug("REST request to count BloodGlucoses by criteria: {}", criteria);
        return ResponseEntity.ok().body(bloodGlucoseQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /blood-glucoses/:id} : get the "id" bloodGlucose.
     *
     * @param id the id of the bloodGlucose to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the bloodGlucose, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/blood-glucoses/{id}")
    public ResponseEntity<BloodGlucose> getBloodGlucose(@PathVariable UUID id) {
        log.debug("REST request to get BloodGlucose : {}", id);
        Optional<BloodGlucose> bloodGlucose = bloodGlucoseService.findOne(id);
        return ResponseUtil.wrapOrNotFound(bloodGlucose);
    }

    /**
     * {@code DELETE  /blood-glucoses/:id} : delete the "id" bloodGlucose.
     *
     * @param id the id of the bloodGlucose to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/blood-glucoses/{id}")
    public ResponseEntity<Void> deleteBloodGlucose(@PathVariable UUID id) {
        log.debug("REST request to delete BloodGlucose : {}", id);
        bloodGlucoseService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
