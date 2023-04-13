package com.be4tech.b4carecollect.web.rest;

import com.be4tech.b4carecollect.domain.OxygenSaturation;
import com.be4tech.b4carecollect.repository.OxygenSaturationRepository;
import com.be4tech.b4carecollect.service.OxygenSaturationQueryService;
import com.be4tech.b4carecollect.service.OxygenSaturationService;
import com.be4tech.b4carecollect.service.criteria.OxygenSaturationCriteria;
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
 * REST controller for managing {@link com.be4tech.b4carecollect.domain.OxygenSaturation}.
 */
@RestController
@RequestMapping("/api")
public class OxygenSaturationResource {

    private final Logger log = LoggerFactory.getLogger(OxygenSaturationResource.class);

    private static final String ENTITY_NAME = "oxygenSaturation";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OxygenSaturationService oxygenSaturationService;

    private final OxygenSaturationRepository oxygenSaturationRepository;

    private final OxygenSaturationQueryService oxygenSaturationQueryService;

    public OxygenSaturationResource(
        OxygenSaturationService oxygenSaturationService,
        OxygenSaturationRepository oxygenSaturationRepository,
        OxygenSaturationQueryService oxygenSaturationQueryService
    ) {
        this.oxygenSaturationService = oxygenSaturationService;
        this.oxygenSaturationRepository = oxygenSaturationRepository;
        this.oxygenSaturationQueryService = oxygenSaturationQueryService;
    }

    /**
     * {@code POST  /oxygen-saturations} : Create a new oxygenSaturation.
     *
     * @param oxygenSaturation the oxygenSaturation to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new oxygenSaturation, or with status {@code 400 (Bad Request)} if the oxygenSaturation has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/oxygen-saturations")
    public ResponseEntity<OxygenSaturation> createOxygenSaturation(@RequestBody OxygenSaturation oxygenSaturation)
        throws URISyntaxException {
        log.debug("REST request to save OxygenSaturation : {}", oxygenSaturation);
        if (oxygenSaturation.getId() != null) {
            throw new BadRequestAlertException("A new oxygenSaturation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OxygenSaturation result = oxygenSaturationService.save(oxygenSaturation);
        return ResponseEntity
            .created(new URI("/api/oxygen-saturations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /oxygen-saturations/:id} : Updates an existing oxygenSaturation.
     *
     * @param id the id of the oxygenSaturation to save.
     * @param oxygenSaturation the oxygenSaturation to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated oxygenSaturation,
     * or with status {@code 400 (Bad Request)} if the oxygenSaturation is not valid,
     * or with status {@code 500 (Internal Server Error)} if the oxygenSaturation couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/oxygen-saturations/{id}")
    public ResponseEntity<OxygenSaturation> updateOxygenSaturation(
        @PathVariable(value = "id", required = false) final UUID id,
        @RequestBody OxygenSaturation oxygenSaturation
    ) throws URISyntaxException {
        log.debug("REST request to update OxygenSaturation : {}, {}", id, oxygenSaturation);
        if (oxygenSaturation.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, oxygenSaturation.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!oxygenSaturationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        OxygenSaturation result = oxygenSaturationService.update(oxygenSaturation);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, oxygenSaturation.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /oxygen-saturations/:id} : Partial updates given fields of an existing oxygenSaturation, field will ignore if it is null
     *
     * @param id the id of the oxygenSaturation to save.
     * @param oxygenSaturation the oxygenSaturation to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated oxygenSaturation,
     * or with status {@code 400 (Bad Request)} if the oxygenSaturation is not valid,
     * or with status {@code 404 (Not Found)} if the oxygenSaturation is not found,
     * or with status {@code 500 (Internal Server Error)} if the oxygenSaturation couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/oxygen-saturations/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<OxygenSaturation> partialUpdateOxygenSaturation(
        @PathVariable(value = "id", required = false) final UUID id,
        @RequestBody OxygenSaturation oxygenSaturation
    ) throws URISyntaxException {
        log.debug("REST request to partial update OxygenSaturation partially : {}, {}", id, oxygenSaturation);
        if (oxygenSaturation.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, oxygenSaturation.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!oxygenSaturationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<OxygenSaturation> result = oxygenSaturationService.partialUpdate(oxygenSaturation);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, oxygenSaturation.getId().toString())
        );
    }

    /**
     * {@code GET  /oxygen-saturations} : get all the oxygenSaturations.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of oxygenSaturations in body.
     */
    @GetMapping("/oxygen-saturations")
    public ResponseEntity<List<OxygenSaturation>> getAllOxygenSaturations(
        OxygenSaturationCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get OxygenSaturations by criteria: {}", criteria);
        Page<OxygenSaturation> page = oxygenSaturationQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /oxygen-saturations/count} : count all the oxygenSaturations.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/oxygen-saturations/count")
    public ResponseEntity<Long> countOxygenSaturations(OxygenSaturationCriteria criteria) {
        log.debug("REST request to count OxygenSaturations by criteria: {}", criteria);
        return ResponseEntity.ok().body(oxygenSaturationQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /oxygen-saturations/:id} : get the "id" oxygenSaturation.
     *
     * @param id the id of the oxygenSaturation to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the oxygenSaturation, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/oxygen-saturations/{id}")
    public ResponseEntity<OxygenSaturation> getOxygenSaturation(@PathVariable UUID id) {
        log.debug("REST request to get OxygenSaturation : {}", id);
        Optional<OxygenSaturation> oxygenSaturation = oxygenSaturationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(oxygenSaturation);
    }

    /**
     * {@code DELETE  /oxygen-saturations/:id} : delete the "id" oxygenSaturation.
     *
     * @param id the id of the oxygenSaturation to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/oxygen-saturations/{id}")
    public ResponseEntity<Void> deleteOxygenSaturation(@PathVariable UUID id) {
        log.debug("REST request to delete OxygenSaturation : {}", id);
        oxygenSaturationService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
