package com.be4tech.b4carecollect.web.rest;

import com.be4tech.b4carecollect.domain.CaloriesBMR;
import com.be4tech.b4carecollect.repository.CaloriesBMRRepository;
import com.be4tech.b4carecollect.service.CaloriesBMRQueryService;
import com.be4tech.b4carecollect.service.CaloriesBMRService;
import com.be4tech.b4carecollect.service.criteria.CaloriesBMRCriteria;
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
 * REST controller for managing {@link com.be4tech.b4carecollect.domain.CaloriesBMR}.
 */
@RestController
@RequestMapping("/api")
public class CaloriesBMRResource {

    private final Logger log = LoggerFactory.getLogger(CaloriesBMRResource.class);

    private static final String ENTITY_NAME = "caloriesBMR";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CaloriesBMRService caloriesBMRService;

    private final CaloriesBMRRepository caloriesBMRRepository;

    private final CaloriesBMRQueryService caloriesBMRQueryService;

    public CaloriesBMRResource(
        CaloriesBMRService caloriesBMRService,
        CaloriesBMRRepository caloriesBMRRepository,
        CaloriesBMRQueryService caloriesBMRQueryService
    ) {
        this.caloriesBMRService = caloriesBMRService;
        this.caloriesBMRRepository = caloriesBMRRepository;
        this.caloriesBMRQueryService = caloriesBMRQueryService;
    }

    /**
     * {@code POST  /calories-bmrs} : Create a new caloriesBMR.
     *
     * @param caloriesBMR the caloriesBMR to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new caloriesBMR, or with status {@code 400 (Bad Request)} if the caloriesBMR has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/calories-bmrs")
    public ResponseEntity<CaloriesBMR> createCaloriesBMR(@RequestBody CaloriesBMR caloriesBMR) throws URISyntaxException {
        log.debug("REST request to save CaloriesBMR : {}", caloriesBMR);
        if (caloriesBMR.getId() != null) {
            throw new BadRequestAlertException("A new caloriesBMR cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CaloriesBMR result = caloriesBMRService.save(caloriesBMR);
        return ResponseEntity
            .created(new URI("/api/calories-bmrs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /calories-bmrs/:id} : Updates an existing caloriesBMR.
     *
     * @param id the id of the caloriesBMR to save.
     * @param caloriesBMR the caloriesBMR to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated caloriesBMR,
     * or with status {@code 400 (Bad Request)} if the caloriesBMR is not valid,
     * or with status {@code 500 (Internal Server Error)} if the caloriesBMR couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/calories-bmrs/{id}")
    public ResponseEntity<CaloriesBMR> updateCaloriesBMR(
        @PathVariable(value = "id", required = false) final UUID id,
        @RequestBody CaloriesBMR caloriesBMR
    ) throws URISyntaxException {
        log.debug("REST request to update CaloriesBMR : {}, {}", id, caloriesBMR);
        if (caloriesBMR.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, caloriesBMR.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!caloriesBMRRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CaloriesBMR result = caloriesBMRService.update(caloriesBMR);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, caloriesBMR.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /calories-bmrs/:id} : Partial updates given fields of an existing caloriesBMR, field will ignore if it is null
     *
     * @param id the id of the caloriesBMR to save.
     * @param caloriesBMR the caloriesBMR to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated caloriesBMR,
     * or with status {@code 400 (Bad Request)} if the caloriesBMR is not valid,
     * or with status {@code 404 (Not Found)} if the caloriesBMR is not found,
     * or with status {@code 500 (Internal Server Error)} if the caloriesBMR couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/calories-bmrs/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CaloriesBMR> partialUpdateCaloriesBMR(
        @PathVariable(value = "id", required = false) final UUID id,
        @RequestBody CaloriesBMR caloriesBMR
    ) throws URISyntaxException {
        log.debug("REST request to partial update CaloriesBMR partially : {}, {}", id, caloriesBMR);
        if (caloriesBMR.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, caloriesBMR.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!caloriesBMRRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CaloriesBMR> result = caloriesBMRService.partialUpdate(caloriesBMR);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, caloriesBMR.getId().toString())
        );
    }

    /**
     * {@code GET  /calories-bmrs} : get all the caloriesBMRS.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of caloriesBMRS in body.
     */
    @GetMapping("/calories-bmrs")
    public ResponseEntity<List<CaloriesBMR>> getAllCaloriesBMRS(
        CaloriesBMRCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get CaloriesBMRS by criteria: {}", criteria);
        Page<CaloriesBMR> page = caloriesBMRQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /calories-bmrs/count} : count all the caloriesBMRS.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/calories-bmrs/count")
    public ResponseEntity<Long> countCaloriesBMRS(CaloriesBMRCriteria criteria) {
        log.debug("REST request to count CaloriesBMRS by criteria: {}", criteria);
        return ResponseEntity.ok().body(caloriesBMRQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /calories-bmrs/:id} : get the "id" caloriesBMR.
     *
     * @param id the id of the caloriesBMR to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the caloriesBMR, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/calories-bmrs/{id}")
    public ResponseEntity<CaloriesBMR> getCaloriesBMR(@PathVariable UUID id) {
        log.debug("REST request to get CaloriesBMR : {}", id);
        Optional<CaloriesBMR> caloriesBMR = caloriesBMRService.findOne(id);
        return ResponseUtil.wrapOrNotFound(caloriesBMR);
    }

    /**
     * {@code DELETE  /calories-bmrs/:id} : delete the "id" caloriesBMR.
     *
     * @param id the id of the caloriesBMR to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/calories-bmrs/{id}")
    public ResponseEntity<Void> deleteCaloriesBMR(@PathVariable UUID id) {
        log.debug("REST request to delete CaloriesBMR : {}", id);
        caloriesBMRService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
