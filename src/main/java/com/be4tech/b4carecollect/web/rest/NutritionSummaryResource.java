package com.be4tech.b4carecollect.web.rest;

import com.be4tech.b4carecollect.domain.NutritionSummary;
import com.be4tech.b4carecollect.repository.NutritionSummaryRepository;
import com.be4tech.b4carecollect.service.NutritionSummaryQueryService;
import com.be4tech.b4carecollect.service.NutritionSummaryService;
import com.be4tech.b4carecollect.service.criteria.NutritionSummaryCriteria;
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
 * REST controller for managing {@link com.be4tech.b4carecollect.domain.NutritionSummary}.
 */
@RestController
@RequestMapping("/api")
public class NutritionSummaryResource {

    private final Logger log = LoggerFactory.getLogger(NutritionSummaryResource.class);

    private static final String ENTITY_NAME = "nutritionSummary";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final NutritionSummaryService nutritionSummaryService;

    private final NutritionSummaryRepository nutritionSummaryRepository;

    private final NutritionSummaryQueryService nutritionSummaryQueryService;

    public NutritionSummaryResource(
        NutritionSummaryService nutritionSummaryService,
        NutritionSummaryRepository nutritionSummaryRepository,
        NutritionSummaryQueryService nutritionSummaryQueryService
    ) {
        this.nutritionSummaryService = nutritionSummaryService;
        this.nutritionSummaryRepository = nutritionSummaryRepository;
        this.nutritionSummaryQueryService = nutritionSummaryQueryService;
    }

    /**
     * {@code POST  /nutrition-summaries} : Create a new nutritionSummary.
     *
     * @param nutritionSummary the nutritionSummary to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new nutritionSummary, or with status {@code 400 (Bad Request)} if the nutritionSummary has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/nutrition-summaries")
    public ResponseEntity<NutritionSummary> createNutritionSummary(@RequestBody NutritionSummary nutritionSummary)
        throws URISyntaxException {
        log.debug("REST request to save NutritionSummary : {}", nutritionSummary);
        if (nutritionSummary.getId() != null) {
            throw new BadRequestAlertException("A new nutritionSummary cannot already have an ID", ENTITY_NAME, "idexists");
        }
        NutritionSummary result = nutritionSummaryService.save(nutritionSummary);
        return ResponseEntity
            .created(new URI("/api/nutrition-summaries/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /nutrition-summaries/:id} : Updates an existing nutritionSummary.
     *
     * @param id the id of the nutritionSummary to save.
     * @param nutritionSummary the nutritionSummary to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated nutritionSummary,
     * or with status {@code 400 (Bad Request)} if the nutritionSummary is not valid,
     * or with status {@code 500 (Internal Server Error)} if the nutritionSummary couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/nutrition-summaries/{id}")
    public ResponseEntity<NutritionSummary> updateNutritionSummary(
        @PathVariable(value = "id", required = false) final UUID id,
        @RequestBody NutritionSummary nutritionSummary
    ) throws URISyntaxException {
        log.debug("REST request to update NutritionSummary : {}, {}", id, nutritionSummary);
        if (nutritionSummary.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, nutritionSummary.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!nutritionSummaryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        NutritionSummary result = nutritionSummaryService.update(nutritionSummary);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, nutritionSummary.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /nutrition-summaries/:id} : Partial updates given fields of an existing nutritionSummary, field will ignore if it is null
     *
     * @param id the id of the nutritionSummary to save.
     * @param nutritionSummary the nutritionSummary to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated nutritionSummary,
     * or with status {@code 400 (Bad Request)} if the nutritionSummary is not valid,
     * or with status {@code 404 (Not Found)} if the nutritionSummary is not found,
     * or with status {@code 500 (Internal Server Error)} if the nutritionSummary couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/nutrition-summaries/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<NutritionSummary> partialUpdateNutritionSummary(
        @PathVariable(value = "id", required = false) final UUID id,
        @RequestBody NutritionSummary nutritionSummary
    ) throws URISyntaxException {
        log.debug("REST request to partial update NutritionSummary partially : {}, {}", id, nutritionSummary);
        if (nutritionSummary.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, nutritionSummary.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!nutritionSummaryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<NutritionSummary> result = nutritionSummaryService.partialUpdate(nutritionSummary);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, nutritionSummary.getId().toString())
        );
    }

    /**
     * {@code GET  /nutrition-summaries} : get all the nutritionSummaries.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of nutritionSummaries in body.
     */
    @GetMapping("/nutrition-summaries")
    public ResponseEntity<List<NutritionSummary>> getAllNutritionSummaries(
        NutritionSummaryCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get NutritionSummaries by criteria: {}", criteria);
        Page<NutritionSummary> page = nutritionSummaryQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /nutrition-summaries/count} : count all the nutritionSummaries.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/nutrition-summaries/count")
    public ResponseEntity<Long> countNutritionSummaries(NutritionSummaryCriteria criteria) {
        log.debug("REST request to count NutritionSummaries by criteria: {}", criteria);
        return ResponseEntity.ok().body(nutritionSummaryQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /nutrition-summaries/:id} : get the "id" nutritionSummary.
     *
     * @param id the id of the nutritionSummary to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the nutritionSummary, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/nutrition-summaries/{id}")
    public ResponseEntity<NutritionSummary> getNutritionSummary(@PathVariable UUID id) {
        log.debug("REST request to get NutritionSummary : {}", id);
        Optional<NutritionSummary> nutritionSummary = nutritionSummaryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(nutritionSummary);
    }

    /**
     * {@code DELETE  /nutrition-summaries/:id} : delete the "id" nutritionSummary.
     *
     * @param id the id of the nutritionSummary to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/nutrition-summaries/{id}")
    public ResponseEntity<Void> deleteNutritionSummary(@PathVariable UUID id) {
        log.debug("REST request to delete NutritionSummary : {}", id);
        nutritionSummaryService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
