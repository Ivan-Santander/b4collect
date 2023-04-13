package com.be4tech.b4carecollect.web.rest;

import com.be4tech.b4carecollect.domain.MentalHealth;
import com.be4tech.b4carecollect.repository.MentalHealthRepository;
import com.be4tech.b4carecollect.service.MentalHealthQueryService;
import com.be4tech.b4carecollect.service.MentalHealthService;
import com.be4tech.b4carecollect.service.criteria.MentalHealthCriteria;
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
 * REST controller for managing {@link com.be4tech.b4carecollect.domain.MentalHealth}.
 */
@RestController
@RequestMapping("/api")
public class MentalHealthResource {

    private final Logger log = LoggerFactory.getLogger(MentalHealthResource.class);

    private static final String ENTITY_NAME = "mentalHealth";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MentalHealthService mentalHealthService;

    private final MentalHealthRepository mentalHealthRepository;

    private final MentalHealthQueryService mentalHealthQueryService;

    public MentalHealthResource(
        MentalHealthService mentalHealthService,
        MentalHealthRepository mentalHealthRepository,
        MentalHealthQueryService mentalHealthQueryService
    ) {
        this.mentalHealthService = mentalHealthService;
        this.mentalHealthRepository = mentalHealthRepository;
        this.mentalHealthQueryService = mentalHealthQueryService;
    }

    /**
     * {@code POST  /mental-healths} : Create a new mentalHealth.
     *
     * @param mentalHealth the mentalHealth to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new mentalHealth, or with status {@code 400 (Bad Request)} if the mentalHealth has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/mental-healths")
    public ResponseEntity<MentalHealth> createMentalHealth(@RequestBody MentalHealth mentalHealth) throws URISyntaxException {
        log.debug("REST request to save MentalHealth : {}", mentalHealth);
        if (mentalHealth.getId() != null) {
            throw new BadRequestAlertException("A new mentalHealth cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MentalHealth result = mentalHealthService.save(mentalHealth);
        return ResponseEntity
            .created(new URI("/api/mental-healths/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /mental-healths/:id} : Updates an existing mentalHealth.
     *
     * @param id the id of the mentalHealth to save.
     * @param mentalHealth the mentalHealth to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mentalHealth,
     * or with status {@code 400 (Bad Request)} if the mentalHealth is not valid,
     * or with status {@code 500 (Internal Server Error)} if the mentalHealth couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/mental-healths/{id}")
    public ResponseEntity<MentalHealth> updateMentalHealth(
        @PathVariable(value = "id", required = false) final UUID id,
        @RequestBody MentalHealth mentalHealth
    ) throws URISyntaxException {
        log.debug("REST request to update MentalHealth : {}, {}", id, mentalHealth);
        if (mentalHealth.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, mentalHealth.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!mentalHealthRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        MentalHealth result = mentalHealthService.update(mentalHealth);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, mentalHealth.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /mental-healths/:id} : Partial updates given fields of an existing mentalHealth, field will ignore if it is null
     *
     * @param id the id of the mentalHealth to save.
     * @param mentalHealth the mentalHealth to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mentalHealth,
     * or with status {@code 400 (Bad Request)} if the mentalHealth is not valid,
     * or with status {@code 404 (Not Found)} if the mentalHealth is not found,
     * or with status {@code 500 (Internal Server Error)} if the mentalHealth couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/mental-healths/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<MentalHealth> partialUpdateMentalHealth(
        @PathVariable(value = "id", required = false) final UUID id,
        @RequestBody MentalHealth mentalHealth
    ) throws URISyntaxException {
        log.debug("REST request to partial update MentalHealth partially : {}, {}", id, mentalHealth);
        if (mentalHealth.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, mentalHealth.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!mentalHealthRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<MentalHealth> result = mentalHealthService.partialUpdate(mentalHealth);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, mentalHealth.getId().toString())
        );
    }

    /**
     * {@code GET  /mental-healths} : get all the mentalHealths.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of mentalHealths in body.
     */
    @GetMapping("/mental-healths")
    public ResponseEntity<List<MentalHealth>> getAllMentalHealths(
        MentalHealthCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get MentalHealths by criteria: {}", criteria);
        Page<MentalHealth> page = mentalHealthQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /mental-healths/count} : count all the mentalHealths.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/mental-healths/count")
    public ResponseEntity<Long> countMentalHealths(MentalHealthCriteria criteria) {
        log.debug("REST request to count MentalHealths by criteria: {}", criteria);
        return ResponseEntity.ok().body(mentalHealthQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /mental-healths/:id} : get the "id" mentalHealth.
     *
     * @param id the id of the mentalHealth to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the mentalHealth, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/mental-healths/{id}")
    public ResponseEntity<MentalHealth> getMentalHealth(@PathVariable UUID id) {
        log.debug("REST request to get MentalHealth : {}", id);
        Optional<MentalHealth> mentalHealth = mentalHealthService.findOne(id);
        return ResponseUtil.wrapOrNotFound(mentalHealth);
    }

    /**
     * {@code DELETE  /mental-healths/:id} : delete the "id" mentalHealth.
     *
     * @param id the id of the mentalHealth to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/mental-healths/{id}")
    public ResponseEntity<Void> deleteMentalHealth(@PathVariable UUID id) {
        log.debug("REST request to delete MentalHealth : {}", id);
        mentalHealthService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
