package com.be4tech.b4carecollect.web.rest;

import com.be4tech.b4carecollect.domain.BodyFatPercentage;
import com.be4tech.b4carecollect.repository.BodyFatPercentageRepository;
import com.be4tech.b4carecollect.service.BodyFatPercentageQueryService;
import com.be4tech.b4carecollect.service.BodyFatPercentageService;
import com.be4tech.b4carecollect.service.criteria.BodyFatPercentageCriteria;
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
 * REST controller for managing {@link com.be4tech.b4carecollect.domain.BodyFatPercentage}.
 */
@RestController
@RequestMapping("/api")
public class BodyFatPercentageResource {

    private final Logger log = LoggerFactory.getLogger(BodyFatPercentageResource.class);

    private static final String ENTITY_NAME = "bodyFatPercentage";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BodyFatPercentageService bodyFatPercentageService;

    private final BodyFatPercentageRepository bodyFatPercentageRepository;

    private final BodyFatPercentageQueryService bodyFatPercentageQueryService;

    public BodyFatPercentageResource(
        BodyFatPercentageService bodyFatPercentageService,
        BodyFatPercentageRepository bodyFatPercentageRepository,
        BodyFatPercentageQueryService bodyFatPercentageQueryService
    ) {
        this.bodyFatPercentageService = bodyFatPercentageService;
        this.bodyFatPercentageRepository = bodyFatPercentageRepository;
        this.bodyFatPercentageQueryService = bodyFatPercentageQueryService;
    }

    /**
     * {@code POST  /body-fat-percentages} : Create a new bodyFatPercentage.
     *
     * @param bodyFatPercentage the bodyFatPercentage to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new bodyFatPercentage, or with status {@code 400 (Bad Request)} if the bodyFatPercentage has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/body-fat-percentages")
    public ResponseEntity<BodyFatPercentage> createBodyFatPercentage(@RequestBody BodyFatPercentage bodyFatPercentage)
        throws URISyntaxException {
        log.debug("REST request to save BodyFatPercentage : {}", bodyFatPercentage);
        if (bodyFatPercentage.getId() != null) {
            throw new BadRequestAlertException("A new bodyFatPercentage cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BodyFatPercentage result = bodyFatPercentageService.save(bodyFatPercentage);
        return ResponseEntity
            .created(new URI("/api/body-fat-percentages/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /body-fat-percentages/:id} : Updates an existing bodyFatPercentage.
     *
     * @param id the id of the bodyFatPercentage to save.
     * @param bodyFatPercentage the bodyFatPercentage to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bodyFatPercentage,
     * or with status {@code 400 (Bad Request)} if the bodyFatPercentage is not valid,
     * or with status {@code 500 (Internal Server Error)} if the bodyFatPercentage couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/body-fat-percentages/{id}")
    public ResponseEntity<BodyFatPercentage> updateBodyFatPercentage(
        @PathVariable(value = "id", required = false) final UUID id,
        @RequestBody BodyFatPercentage bodyFatPercentage
    ) throws URISyntaxException {
        log.debug("REST request to update BodyFatPercentage : {}, {}", id, bodyFatPercentage);
        if (bodyFatPercentage.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, bodyFatPercentage.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bodyFatPercentageRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        BodyFatPercentage result = bodyFatPercentageService.update(bodyFatPercentage);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, bodyFatPercentage.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /body-fat-percentages/:id} : Partial updates given fields of an existing bodyFatPercentage, field will ignore if it is null
     *
     * @param id the id of the bodyFatPercentage to save.
     * @param bodyFatPercentage the bodyFatPercentage to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bodyFatPercentage,
     * or with status {@code 400 (Bad Request)} if the bodyFatPercentage is not valid,
     * or with status {@code 404 (Not Found)} if the bodyFatPercentage is not found,
     * or with status {@code 500 (Internal Server Error)} if the bodyFatPercentage couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/body-fat-percentages/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<BodyFatPercentage> partialUpdateBodyFatPercentage(
        @PathVariable(value = "id", required = false) final UUID id,
        @RequestBody BodyFatPercentage bodyFatPercentage
    ) throws URISyntaxException {
        log.debug("REST request to partial update BodyFatPercentage partially : {}, {}", id, bodyFatPercentage);
        if (bodyFatPercentage.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, bodyFatPercentage.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bodyFatPercentageRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<BodyFatPercentage> result = bodyFatPercentageService.partialUpdate(bodyFatPercentage);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, bodyFatPercentage.getId().toString())
        );
    }

    /**
     * {@code GET  /body-fat-percentages} : get all the bodyFatPercentages.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of bodyFatPercentages in body.
     */
    @GetMapping("/body-fat-percentages")
    public ResponseEntity<List<BodyFatPercentage>> getAllBodyFatPercentages(
        BodyFatPercentageCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get BodyFatPercentages by criteria: {}", criteria);
        Page<BodyFatPercentage> page = bodyFatPercentageQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /body-fat-percentages/count} : count all the bodyFatPercentages.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/body-fat-percentages/count")
    public ResponseEntity<Long> countBodyFatPercentages(BodyFatPercentageCriteria criteria) {
        log.debug("REST request to count BodyFatPercentages by criteria: {}", criteria);
        return ResponseEntity.ok().body(bodyFatPercentageQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /body-fat-percentages/:id} : get the "id" bodyFatPercentage.
     *
     * @param id the id of the bodyFatPercentage to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the bodyFatPercentage, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/body-fat-percentages/{id}")
    public ResponseEntity<BodyFatPercentage> getBodyFatPercentage(@PathVariable UUID id) {
        log.debug("REST request to get BodyFatPercentage : {}", id);
        Optional<BodyFatPercentage> bodyFatPercentage = bodyFatPercentageService.findOne(id);
        return ResponseUtil.wrapOrNotFound(bodyFatPercentage);
    }

    /**
     * {@code DELETE  /body-fat-percentages/:id} : delete the "id" bodyFatPercentage.
     *
     * @param id the id of the bodyFatPercentage to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/body-fat-percentages/{id}")
    public ResponseEntity<Void> deleteBodyFatPercentage(@PathVariable UUID id) {
        log.debug("REST request to delete BodyFatPercentage : {}", id);
        bodyFatPercentageService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
