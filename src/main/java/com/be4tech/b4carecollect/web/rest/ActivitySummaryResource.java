package com.be4tech.b4carecollect.web.rest;

import com.be4tech.b4carecollect.domain.ActivitySummary;
import com.be4tech.b4carecollect.repository.ActivitySummaryRepository;
import com.be4tech.b4carecollect.service.ActivitySummaryQueryService;
import com.be4tech.b4carecollect.service.ActivitySummaryService;
import com.be4tech.b4carecollect.service.criteria.ActivitySummaryCriteria;
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
 * REST controller for managing {@link com.be4tech.b4carecollect.domain.ActivitySummary}.
 */
@RestController
@RequestMapping("/api")
public class ActivitySummaryResource {

    private final Logger log = LoggerFactory.getLogger(ActivitySummaryResource.class);

    private static final String ENTITY_NAME = "activitySummary";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ActivitySummaryService activitySummaryService;

    private final ActivitySummaryRepository activitySummaryRepository;

    private final ActivitySummaryQueryService activitySummaryQueryService;

    public ActivitySummaryResource(
        ActivitySummaryService activitySummaryService,
        ActivitySummaryRepository activitySummaryRepository,
        ActivitySummaryQueryService activitySummaryQueryService
    ) {
        this.activitySummaryService = activitySummaryService;
        this.activitySummaryRepository = activitySummaryRepository;
        this.activitySummaryQueryService = activitySummaryQueryService;
    }

    /**
     * {@code POST  /activity-summaries} : Create a new activitySummary.
     *
     * @param activitySummary the activitySummary to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new activitySummary, or with status {@code 400 (Bad Request)} if the activitySummary has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/activity-summaries")
    public ResponseEntity<ActivitySummary> createActivitySummary(@RequestBody ActivitySummary activitySummary) throws URISyntaxException {
        log.debug("REST request to save ActivitySummary : {}", activitySummary);
        if (activitySummary.getId() != null) {
            throw new BadRequestAlertException("A new activitySummary cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ActivitySummary result = activitySummaryService.save(activitySummary);
        return ResponseEntity
            .created(new URI("/api/activity-summaries/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /activity-summaries/:id} : Updates an existing activitySummary.
     *
     * @param id the id of the activitySummary to save.
     * @param activitySummary the activitySummary to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated activitySummary,
     * or with status {@code 400 (Bad Request)} if the activitySummary is not valid,
     * or with status {@code 500 (Internal Server Error)} if the activitySummary couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/activity-summaries/{id}")
    public ResponseEntity<ActivitySummary> updateActivitySummary(
        @PathVariable(value = "id", required = false) final UUID id,
        @RequestBody ActivitySummary activitySummary
    ) throws URISyntaxException {
        log.debug("REST request to update ActivitySummary : {}, {}", id, activitySummary);
        if (activitySummary.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, activitySummary.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!activitySummaryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ActivitySummary result = activitySummaryService.update(activitySummary);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, activitySummary.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /activity-summaries/:id} : Partial updates given fields of an existing activitySummary, field will ignore if it is null
     *
     * @param id the id of the activitySummary to save.
     * @param activitySummary the activitySummary to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated activitySummary,
     * or with status {@code 400 (Bad Request)} if the activitySummary is not valid,
     * or with status {@code 404 (Not Found)} if the activitySummary is not found,
     * or with status {@code 500 (Internal Server Error)} if the activitySummary couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/activity-summaries/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ActivitySummary> partialUpdateActivitySummary(
        @PathVariable(value = "id", required = false) final UUID id,
        @RequestBody ActivitySummary activitySummary
    ) throws URISyntaxException {
        log.debug("REST request to partial update ActivitySummary partially : {}, {}", id, activitySummary);
        if (activitySummary.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, activitySummary.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!activitySummaryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ActivitySummary> result = activitySummaryService.partialUpdate(activitySummary);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, activitySummary.getId().toString())
        );
    }

    /**
     * {@code GET  /activity-summaries} : get all the activitySummaries.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of activitySummaries in body.
     */
    @GetMapping("/activity-summaries")
    public ResponseEntity<List<ActivitySummary>> getAllActivitySummaries(
        ActivitySummaryCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get ActivitySummaries by criteria: {}", criteria);
        Page<ActivitySummary> page = activitySummaryQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /activity-summaries/count} : count all the activitySummaries.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/activity-summaries/count")
    public ResponseEntity<Long> countActivitySummaries(ActivitySummaryCriteria criteria) {
        log.debug("REST request to count ActivitySummaries by criteria: {}", criteria);
        return ResponseEntity.ok().body(activitySummaryQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /activity-summaries/:id} : get the "id" activitySummary.
     *
     * @param id the id of the activitySummary to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the activitySummary, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/activity-summaries/{id}")
    public ResponseEntity<ActivitySummary> getActivitySummary(@PathVariable UUID id) {
        log.debug("REST request to get ActivitySummary : {}", id);
        Optional<ActivitySummary> activitySummary = activitySummaryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(activitySummary);
    }

    /**
     * {@code DELETE  /activity-summaries/:id} : delete the "id" activitySummary.
     *
     * @param id the id of the activitySummary to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/activity-summaries/{id}")
    public ResponseEntity<Void> deleteActivitySummary(@PathVariable UUID id) {
        log.debug("REST request to delete ActivitySummary : {}", id);
        activitySummaryService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
