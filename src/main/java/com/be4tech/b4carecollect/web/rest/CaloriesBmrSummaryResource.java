package com.be4tech.b4carecollect.web.rest;

import com.be4tech.b4carecollect.domain.CaloriesBmrSummary;
import com.be4tech.b4carecollect.repository.CaloriesBmrSummaryRepository;
import com.be4tech.b4carecollect.service.CaloriesBmrSummaryQueryService;
import com.be4tech.b4carecollect.service.CaloriesBmrSummaryService;
import com.be4tech.b4carecollect.service.criteria.CaloriesBmrSummaryCriteria;
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
 * REST controller for managing {@link com.be4tech.b4carecollect.domain.CaloriesBmrSummary}.
 */
@RestController
@RequestMapping("/api")
public class CaloriesBmrSummaryResource {

    private final Logger log = LoggerFactory.getLogger(CaloriesBmrSummaryResource.class);

    private static final String ENTITY_NAME = "caloriesBmrSummary";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CaloriesBmrSummaryService caloriesBmrSummaryService;

    private final CaloriesBmrSummaryRepository caloriesBmrSummaryRepository;

    private final CaloriesBmrSummaryQueryService caloriesBmrSummaryQueryService;

    public CaloriesBmrSummaryResource(
        CaloriesBmrSummaryService caloriesBmrSummaryService,
        CaloriesBmrSummaryRepository caloriesBmrSummaryRepository,
        CaloriesBmrSummaryQueryService caloriesBmrSummaryQueryService
    ) {
        this.caloriesBmrSummaryService = caloriesBmrSummaryService;
        this.caloriesBmrSummaryRepository = caloriesBmrSummaryRepository;
        this.caloriesBmrSummaryQueryService = caloriesBmrSummaryQueryService;
    }

    /**
     * {@code POST  /calories-bmr-summaries} : Create a new caloriesBmrSummary.
     *
     * @param caloriesBmrSummary the caloriesBmrSummary to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new caloriesBmrSummary, or with status {@code 400 (Bad Request)} if the caloriesBmrSummary has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/calories-bmr-summaries")
    public ResponseEntity<CaloriesBmrSummary> createCaloriesBmrSummary(@RequestBody CaloriesBmrSummary caloriesBmrSummary)
        throws URISyntaxException {
        log.debug("REST request to save CaloriesBmrSummary : {}", caloriesBmrSummary);
        if (caloriesBmrSummary.getId() != null) {
            throw new BadRequestAlertException("A new caloriesBmrSummary cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CaloriesBmrSummary result = caloriesBmrSummaryService.save(caloriesBmrSummary);
        return ResponseEntity
            .created(new URI("/api/calories-bmr-summaries/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /calories-bmr-summaries/:id} : Updates an existing caloriesBmrSummary.
     *
     * @param id the id of the caloriesBmrSummary to save.
     * @param caloriesBmrSummary the caloriesBmrSummary to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated caloriesBmrSummary,
     * or with status {@code 400 (Bad Request)} if the caloriesBmrSummary is not valid,
     * or with status {@code 500 (Internal Server Error)} if the caloriesBmrSummary couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/calories-bmr-summaries/{id}")
    public ResponseEntity<CaloriesBmrSummary> updateCaloriesBmrSummary(
        @PathVariable(value = "id", required = false) final UUID id,
        @RequestBody CaloriesBmrSummary caloriesBmrSummary
    ) throws URISyntaxException {
        log.debug("REST request to update CaloriesBmrSummary : {}, {}", id, caloriesBmrSummary);
        if (caloriesBmrSummary.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, caloriesBmrSummary.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!caloriesBmrSummaryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CaloriesBmrSummary result = caloriesBmrSummaryService.update(caloriesBmrSummary);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, caloriesBmrSummary.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /calories-bmr-summaries/:id} : Partial updates given fields of an existing caloriesBmrSummary, field will ignore if it is null
     *
     * @param id the id of the caloriesBmrSummary to save.
     * @param caloriesBmrSummary the caloriesBmrSummary to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated caloriesBmrSummary,
     * or with status {@code 400 (Bad Request)} if the caloriesBmrSummary is not valid,
     * or with status {@code 404 (Not Found)} if the caloriesBmrSummary is not found,
     * or with status {@code 500 (Internal Server Error)} if the caloriesBmrSummary couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/calories-bmr-summaries/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CaloriesBmrSummary> partialUpdateCaloriesBmrSummary(
        @PathVariable(value = "id", required = false) final UUID id,
        @RequestBody CaloriesBmrSummary caloriesBmrSummary
    ) throws URISyntaxException {
        log.debug("REST request to partial update CaloriesBmrSummary partially : {}, {}", id, caloriesBmrSummary);
        if (caloriesBmrSummary.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, caloriesBmrSummary.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!caloriesBmrSummaryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CaloriesBmrSummary> result = caloriesBmrSummaryService.partialUpdate(caloriesBmrSummary);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, caloriesBmrSummary.getId().toString())
        );
    }

    /**
     * {@code GET  /calories-bmr-summaries} : get all the caloriesBmrSummaries.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of caloriesBmrSummaries in body.
     */
    @GetMapping("/calories-bmr-summaries")
    public ResponseEntity<List<CaloriesBmrSummary>> getAllCaloriesBmrSummaries(
        CaloriesBmrSummaryCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get CaloriesBmrSummaries by criteria: {}", criteria);
        Page<CaloriesBmrSummary> page = caloriesBmrSummaryQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /calories-bmr-summaries/count} : count all the caloriesBmrSummaries.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/calories-bmr-summaries/count")
    public ResponseEntity<Long> countCaloriesBmrSummaries(CaloriesBmrSummaryCriteria criteria) {
        log.debug("REST request to count CaloriesBmrSummaries by criteria: {}", criteria);
        return ResponseEntity.ok().body(caloriesBmrSummaryQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /calories-bmr-summaries/:id} : get the "id" caloriesBmrSummary.
     *
     * @param id the id of the caloriesBmrSummary to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the caloriesBmrSummary, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/calories-bmr-summaries/{id}")
    public ResponseEntity<CaloriesBmrSummary> getCaloriesBmrSummary(@PathVariable UUID id) {
        log.debug("REST request to get CaloriesBmrSummary : {}", id);
        Optional<CaloriesBmrSummary> caloriesBmrSummary = caloriesBmrSummaryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(caloriesBmrSummary);
    }

    /**
     * {@code DELETE  /calories-bmr-summaries/:id} : delete the "id" caloriesBmrSummary.
     *
     * @param id the id of the caloriesBmrSummary to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/calories-bmr-summaries/{id}")
    public ResponseEntity<Void> deleteCaloriesBmrSummary(@PathVariable UUID id) {
        log.debug("REST request to delete CaloriesBmrSummary : {}", id);
        caloriesBmrSummaryService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
