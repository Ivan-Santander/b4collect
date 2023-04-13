package com.be4tech.b4carecollect.web.rest;

import com.be4tech.b4carecollect.domain.CiclingPedalingCadence;
import com.be4tech.b4carecollect.repository.CiclingPedalingCadenceRepository;
import com.be4tech.b4carecollect.service.CiclingPedalingCadenceQueryService;
import com.be4tech.b4carecollect.service.CiclingPedalingCadenceService;
import com.be4tech.b4carecollect.service.criteria.CiclingPedalingCadenceCriteria;
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
 * REST controller for managing {@link com.be4tech.b4carecollect.domain.CiclingPedalingCadence}.
 */
@RestController
@RequestMapping("/api")
public class CiclingPedalingCadenceResource {

    private final Logger log = LoggerFactory.getLogger(CiclingPedalingCadenceResource.class);

    private static final String ENTITY_NAME = "ciclingPedalingCadence";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CiclingPedalingCadenceService ciclingPedalingCadenceService;

    private final CiclingPedalingCadenceRepository ciclingPedalingCadenceRepository;

    private final CiclingPedalingCadenceQueryService ciclingPedalingCadenceQueryService;

    public CiclingPedalingCadenceResource(
        CiclingPedalingCadenceService ciclingPedalingCadenceService,
        CiclingPedalingCadenceRepository ciclingPedalingCadenceRepository,
        CiclingPedalingCadenceQueryService ciclingPedalingCadenceQueryService
    ) {
        this.ciclingPedalingCadenceService = ciclingPedalingCadenceService;
        this.ciclingPedalingCadenceRepository = ciclingPedalingCadenceRepository;
        this.ciclingPedalingCadenceQueryService = ciclingPedalingCadenceQueryService;
    }

    /**
     * {@code POST  /cicling-pedaling-cadences} : Create a new ciclingPedalingCadence.
     *
     * @param ciclingPedalingCadence the ciclingPedalingCadence to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new ciclingPedalingCadence, or with status {@code 400 (Bad Request)} if the ciclingPedalingCadence has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/cicling-pedaling-cadences")
    public ResponseEntity<CiclingPedalingCadence> createCiclingPedalingCadence(@RequestBody CiclingPedalingCadence ciclingPedalingCadence)
        throws URISyntaxException {
        log.debug("REST request to save CiclingPedalingCadence : {}", ciclingPedalingCadence);
        if (ciclingPedalingCadence.getId() != null) {
            throw new BadRequestAlertException("A new ciclingPedalingCadence cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CiclingPedalingCadence result = ciclingPedalingCadenceService.save(ciclingPedalingCadence);
        return ResponseEntity
            .created(new URI("/api/cicling-pedaling-cadences/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /cicling-pedaling-cadences/:id} : Updates an existing ciclingPedalingCadence.
     *
     * @param id the id of the ciclingPedalingCadence to save.
     * @param ciclingPedalingCadence the ciclingPedalingCadence to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ciclingPedalingCadence,
     * or with status {@code 400 (Bad Request)} if the ciclingPedalingCadence is not valid,
     * or with status {@code 500 (Internal Server Error)} if the ciclingPedalingCadence couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/cicling-pedaling-cadences/{id}")
    public ResponseEntity<CiclingPedalingCadence> updateCiclingPedalingCadence(
        @PathVariable(value = "id", required = false) final UUID id,
        @RequestBody CiclingPedalingCadence ciclingPedalingCadence
    ) throws URISyntaxException {
        log.debug("REST request to update CiclingPedalingCadence : {}, {}", id, ciclingPedalingCadence);
        if (ciclingPedalingCadence.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ciclingPedalingCadence.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ciclingPedalingCadenceRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CiclingPedalingCadence result = ciclingPedalingCadenceService.update(ciclingPedalingCadence);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, ciclingPedalingCadence.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /cicling-pedaling-cadences/:id} : Partial updates given fields of an existing ciclingPedalingCadence, field will ignore if it is null
     *
     * @param id the id of the ciclingPedalingCadence to save.
     * @param ciclingPedalingCadence the ciclingPedalingCadence to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ciclingPedalingCadence,
     * or with status {@code 400 (Bad Request)} if the ciclingPedalingCadence is not valid,
     * or with status {@code 404 (Not Found)} if the ciclingPedalingCadence is not found,
     * or with status {@code 500 (Internal Server Error)} if the ciclingPedalingCadence couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/cicling-pedaling-cadences/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CiclingPedalingCadence> partialUpdateCiclingPedalingCadence(
        @PathVariable(value = "id", required = false) final UUID id,
        @RequestBody CiclingPedalingCadence ciclingPedalingCadence
    ) throws URISyntaxException {
        log.debug("REST request to partial update CiclingPedalingCadence partially : {}, {}", id, ciclingPedalingCadence);
        if (ciclingPedalingCadence.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ciclingPedalingCadence.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ciclingPedalingCadenceRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CiclingPedalingCadence> result = ciclingPedalingCadenceService.partialUpdate(ciclingPedalingCadence);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, ciclingPedalingCadence.getId().toString())
        );
    }

    /**
     * {@code GET  /cicling-pedaling-cadences} : get all the ciclingPedalingCadences.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of ciclingPedalingCadences in body.
     */
    @GetMapping("/cicling-pedaling-cadences")
    public ResponseEntity<List<CiclingPedalingCadence>> getAllCiclingPedalingCadences(
        CiclingPedalingCadenceCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get CiclingPedalingCadences by criteria: {}", criteria);
        Page<CiclingPedalingCadence> page = ciclingPedalingCadenceQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /cicling-pedaling-cadences/count} : count all the ciclingPedalingCadences.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/cicling-pedaling-cadences/count")
    public ResponseEntity<Long> countCiclingPedalingCadences(CiclingPedalingCadenceCriteria criteria) {
        log.debug("REST request to count CiclingPedalingCadences by criteria: {}", criteria);
        return ResponseEntity.ok().body(ciclingPedalingCadenceQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /cicling-pedaling-cadences/:id} : get the "id" ciclingPedalingCadence.
     *
     * @param id the id of the ciclingPedalingCadence to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the ciclingPedalingCadence, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/cicling-pedaling-cadences/{id}")
    public ResponseEntity<CiclingPedalingCadence> getCiclingPedalingCadence(@PathVariable UUID id) {
        log.debug("REST request to get CiclingPedalingCadence : {}", id);
        Optional<CiclingPedalingCadence> ciclingPedalingCadence = ciclingPedalingCadenceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(ciclingPedalingCadence);
    }

    /**
     * {@code DELETE  /cicling-pedaling-cadences/:id} : delete the "id" ciclingPedalingCadence.
     *
     * @param id the id of the ciclingPedalingCadence to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/cicling-pedaling-cadences/{id}")
    public ResponseEntity<Void> deleteCiclingPedalingCadence(@PathVariable UUID id) {
        log.debug("REST request to delete CiclingPedalingCadence : {}", id);
        ciclingPedalingCadenceService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
