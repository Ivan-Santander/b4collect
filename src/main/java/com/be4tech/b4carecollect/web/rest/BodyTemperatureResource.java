package com.be4tech.b4carecollect.web.rest;

import com.be4tech.b4carecollect.domain.BodyTemperature;
import com.be4tech.b4carecollect.repository.BodyTemperatureRepository;
import com.be4tech.b4carecollect.service.BodyTemperatureQueryService;
import com.be4tech.b4carecollect.service.BodyTemperatureService;
import com.be4tech.b4carecollect.service.criteria.BodyTemperatureCriteria;
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
 * REST controller for managing {@link com.be4tech.b4carecollect.domain.BodyTemperature}.
 */
@RestController
@RequestMapping("/api")
public class BodyTemperatureResource {

    private final Logger log = LoggerFactory.getLogger(BodyTemperatureResource.class);

    private static final String ENTITY_NAME = "bodyTemperature";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BodyTemperatureService bodyTemperatureService;

    private final BodyTemperatureRepository bodyTemperatureRepository;

    private final BodyTemperatureQueryService bodyTemperatureQueryService;

    public BodyTemperatureResource(
        BodyTemperatureService bodyTemperatureService,
        BodyTemperatureRepository bodyTemperatureRepository,
        BodyTemperatureQueryService bodyTemperatureQueryService
    ) {
        this.bodyTemperatureService = bodyTemperatureService;
        this.bodyTemperatureRepository = bodyTemperatureRepository;
        this.bodyTemperatureQueryService = bodyTemperatureQueryService;
    }

    /**
     * {@code POST  /body-temperatures} : Create a new bodyTemperature.
     *
     * @param bodyTemperature the bodyTemperature to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new bodyTemperature, or with status {@code 400 (Bad Request)} if the bodyTemperature has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/body-temperatures")
    public ResponseEntity<BodyTemperature> createBodyTemperature(@RequestBody BodyTemperature bodyTemperature) throws URISyntaxException {
        log.debug("REST request to save BodyTemperature : {}", bodyTemperature);
        if (bodyTemperature.getId() != null) {
            throw new BadRequestAlertException("A new bodyTemperature cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BodyTemperature result = bodyTemperatureService.save(bodyTemperature);
        return ResponseEntity
            .created(new URI("/api/body-temperatures/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /body-temperatures/:id} : Updates an existing bodyTemperature.
     *
     * @param id the id of the bodyTemperature to save.
     * @param bodyTemperature the bodyTemperature to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bodyTemperature,
     * or with status {@code 400 (Bad Request)} if the bodyTemperature is not valid,
     * or with status {@code 500 (Internal Server Error)} if the bodyTemperature couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/body-temperatures/{id}")
    public ResponseEntity<BodyTemperature> updateBodyTemperature(
        @PathVariable(value = "id", required = false) final UUID id,
        @RequestBody BodyTemperature bodyTemperature
    ) throws URISyntaxException {
        log.debug("REST request to update BodyTemperature : {}, {}", id, bodyTemperature);
        if (bodyTemperature.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, bodyTemperature.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bodyTemperatureRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        BodyTemperature result = bodyTemperatureService.update(bodyTemperature);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, bodyTemperature.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /body-temperatures/:id} : Partial updates given fields of an existing bodyTemperature, field will ignore if it is null
     *
     * @param id the id of the bodyTemperature to save.
     * @param bodyTemperature the bodyTemperature to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bodyTemperature,
     * or with status {@code 400 (Bad Request)} if the bodyTemperature is not valid,
     * or with status {@code 404 (Not Found)} if the bodyTemperature is not found,
     * or with status {@code 500 (Internal Server Error)} if the bodyTemperature couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/body-temperatures/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<BodyTemperature> partialUpdateBodyTemperature(
        @PathVariable(value = "id", required = false) final UUID id,
        @RequestBody BodyTemperature bodyTemperature
    ) throws URISyntaxException {
        log.debug("REST request to partial update BodyTemperature partially : {}, {}", id, bodyTemperature);
        if (bodyTemperature.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, bodyTemperature.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bodyTemperatureRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<BodyTemperature> result = bodyTemperatureService.partialUpdate(bodyTemperature);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, bodyTemperature.getId().toString())
        );
    }

    /**
     * {@code GET  /body-temperatures} : get all the bodyTemperatures.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of bodyTemperatures in body.
     */
    @GetMapping("/body-temperatures")
    public ResponseEntity<List<BodyTemperature>> getAllBodyTemperatures(
        BodyTemperatureCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get BodyTemperatures by criteria: {}", criteria);
        Page<BodyTemperature> page = bodyTemperatureQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /body-temperatures/count} : count all the bodyTemperatures.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/body-temperatures/count")
    public ResponseEntity<Long> countBodyTemperatures(BodyTemperatureCriteria criteria) {
        log.debug("REST request to count BodyTemperatures by criteria: {}", criteria);
        return ResponseEntity.ok().body(bodyTemperatureQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /body-temperatures/:id} : get the "id" bodyTemperature.
     *
     * @param id the id of the bodyTemperature to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the bodyTemperature, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/body-temperatures/{id}")
    public ResponseEntity<BodyTemperature> getBodyTemperature(@PathVariable UUID id) {
        log.debug("REST request to get BodyTemperature : {}", id);
        Optional<BodyTemperature> bodyTemperature = bodyTemperatureService.findOne(id);
        return ResponseUtil.wrapOrNotFound(bodyTemperature);
    }

    /**
     * {@code DELETE  /body-temperatures/:id} : delete the "id" bodyTemperature.
     *
     * @param id the id of the bodyTemperature to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/body-temperatures/{id}")
    public ResponseEntity<Void> deleteBodyTemperature(@PathVariable UUID id) {
        log.debug("REST request to delete BodyTemperature : {}", id);
        bodyTemperatureService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
