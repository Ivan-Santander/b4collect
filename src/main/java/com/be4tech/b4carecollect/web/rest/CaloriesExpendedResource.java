package com.be4tech.b4carecollect.web.rest;

import com.be4tech.b4carecollect.domain.CaloriesExpended;
import com.be4tech.b4carecollect.repository.CaloriesExpendedRepository;
import com.be4tech.b4carecollect.service.CaloriesExpendedQueryService;
import com.be4tech.b4carecollect.service.CaloriesExpendedService;
import com.be4tech.b4carecollect.service.criteria.CaloriesExpendedCriteria;
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
 * REST controller for managing {@link com.be4tech.b4carecollect.domain.CaloriesExpended}.
 */
@RestController
@RequestMapping("/api")
public class CaloriesExpendedResource {

    private final Logger log = LoggerFactory.getLogger(CaloriesExpendedResource.class);

    private static final String ENTITY_NAME = "caloriesExpended";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CaloriesExpendedService caloriesExpendedService;

    private final CaloriesExpendedRepository caloriesExpendedRepository;

    private final CaloriesExpendedQueryService caloriesExpendedQueryService;

    public CaloriesExpendedResource(
        CaloriesExpendedService caloriesExpendedService,
        CaloriesExpendedRepository caloriesExpendedRepository,
        CaloriesExpendedQueryService caloriesExpendedQueryService
    ) {
        this.caloriesExpendedService = caloriesExpendedService;
        this.caloriesExpendedRepository = caloriesExpendedRepository;
        this.caloriesExpendedQueryService = caloriesExpendedQueryService;
    }

    /**
     * {@code POST  /calories-expendeds} : Create a new caloriesExpended.
     *
     * @param caloriesExpended the caloriesExpended to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new caloriesExpended, or with status {@code 400 (Bad Request)} if the caloriesExpended has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/calories-expendeds")
    public ResponseEntity<CaloriesExpended> createCaloriesExpended(@RequestBody CaloriesExpended caloriesExpended)
        throws URISyntaxException {
        log.debug("REST request to save CaloriesExpended : {}", caloriesExpended);
        if (caloriesExpended.getId() != null) {
            throw new BadRequestAlertException("A new caloriesExpended cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CaloriesExpended result = caloriesExpendedService.save(caloriesExpended);
        return ResponseEntity
            .created(new URI("/api/calories-expendeds/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /calories-expendeds/:id} : Updates an existing caloriesExpended.
     *
     * @param id the id of the caloriesExpended to save.
     * @param caloriesExpended the caloriesExpended to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated caloriesExpended,
     * or with status {@code 400 (Bad Request)} if the caloriesExpended is not valid,
     * or with status {@code 500 (Internal Server Error)} if the caloriesExpended couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/calories-expendeds/{id}")
    public ResponseEntity<CaloriesExpended> updateCaloriesExpended(
        @PathVariable(value = "id", required = false) final UUID id,
        @RequestBody CaloriesExpended caloriesExpended
    ) throws URISyntaxException {
        log.debug("REST request to update CaloriesExpended : {}, {}", id, caloriesExpended);
        if (caloriesExpended.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, caloriesExpended.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!caloriesExpendedRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CaloriesExpended result = caloriesExpendedService.update(caloriesExpended);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, caloriesExpended.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /calories-expendeds/:id} : Partial updates given fields of an existing caloriesExpended, field will ignore if it is null
     *
     * @param id the id of the caloriesExpended to save.
     * @param caloriesExpended the caloriesExpended to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated caloriesExpended,
     * or with status {@code 400 (Bad Request)} if the caloriesExpended is not valid,
     * or with status {@code 404 (Not Found)} if the caloriesExpended is not found,
     * or with status {@code 500 (Internal Server Error)} if the caloriesExpended couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/calories-expendeds/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CaloriesExpended> partialUpdateCaloriesExpended(
        @PathVariable(value = "id", required = false) final UUID id,
        @RequestBody CaloriesExpended caloriesExpended
    ) throws URISyntaxException {
        log.debug("REST request to partial update CaloriesExpended partially : {}, {}", id, caloriesExpended);
        if (caloriesExpended.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, caloriesExpended.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!caloriesExpendedRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CaloriesExpended> result = caloriesExpendedService.partialUpdate(caloriesExpended);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, caloriesExpended.getId().toString())
        );
    }

    /**
     * {@code GET  /calories-expendeds} : get all the caloriesExpendeds.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of caloriesExpendeds in body.
     */
    @GetMapping("/calories-expendeds")
    public ResponseEntity<List<CaloriesExpended>> getAllCaloriesExpendeds(
        CaloriesExpendedCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get CaloriesExpendeds by criteria: {}", criteria);
        Page<CaloriesExpended> page = caloriesExpendedQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /calories-expendeds/count} : count all the caloriesExpendeds.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/calories-expendeds/count")
    public ResponseEntity<Long> countCaloriesExpendeds(CaloriesExpendedCriteria criteria) {
        log.debug("REST request to count CaloriesExpendeds by criteria: {}", criteria);
        return ResponseEntity.ok().body(caloriesExpendedQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /calories-expendeds/:id} : get the "id" caloriesExpended.
     *
     * @param id the id of the caloriesExpended to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the caloriesExpended, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/calories-expendeds/{id}")
    public ResponseEntity<CaloriesExpended> getCaloriesExpended(@PathVariable UUID id) {
        log.debug("REST request to get CaloriesExpended : {}", id);
        Optional<CaloriesExpended> caloriesExpended = caloriesExpendedService.findOne(id);
        return ResponseUtil.wrapOrNotFound(caloriesExpended);
    }

    /**
     * {@code DELETE  /calories-expendeds/:id} : delete the "id" caloriesExpended.
     *
     * @param id the id of the caloriesExpended to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/calories-expendeds/{id}")
    public ResponseEntity<Void> deleteCaloriesExpended(@PathVariable UUID id) {
        log.debug("REST request to delete CaloriesExpended : {}", id);
        caloriesExpendedService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
