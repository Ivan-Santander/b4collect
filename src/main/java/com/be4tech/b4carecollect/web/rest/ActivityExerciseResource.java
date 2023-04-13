package com.be4tech.b4carecollect.web.rest;

import com.be4tech.b4carecollect.domain.ActivityExercise;
import com.be4tech.b4carecollect.repository.ActivityExerciseRepository;
import com.be4tech.b4carecollect.service.ActivityExerciseQueryService;
import com.be4tech.b4carecollect.service.ActivityExerciseService;
import com.be4tech.b4carecollect.service.criteria.ActivityExerciseCriteria;
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
 * REST controller for managing {@link com.be4tech.b4carecollect.domain.ActivityExercise}.
 */
@RestController
@RequestMapping("/api")
public class ActivityExerciseResource {

    private final Logger log = LoggerFactory.getLogger(ActivityExerciseResource.class);

    private static final String ENTITY_NAME = "activityExercise";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ActivityExerciseService activityExerciseService;

    private final ActivityExerciseRepository activityExerciseRepository;

    private final ActivityExerciseQueryService activityExerciseQueryService;

    public ActivityExerciseResource(
        ActivityExerciseService activityExerciseService,
        ActivityExerciseRepository activityExerciseRepository,
        ActivityExerciseQueryService activityExerciseQueryService
    ) {
        this.activityExerciseService = activityExerciseService;
        this.activityExerciseRepository = activityExerciseRepository;
        this.activityExerciseQueryService = activityExerciseQueryService;
    }

    /**
     * {@code POST  /activity-exercises} : Create a new activityExercise.
     *
     * @param activityExercise the activityExercise to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new activityExercise, or with status {@code 400 (Bad Request)} if the activityExercise has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/activity-exercises")
    public ResponseEntity<ActivityExercise> createActivityExercise(@RequestBody ActivityExercise activityExercise)
        throws URISyntaxException {
        log.debug("REST request to save ActivityExercise : {}", activityExercise);
        if (activityExercise.getId() != null) {
            throw new BadRequestAlertException("A new activityExercise cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ActivityExercise result = activityExerciseService.save(activityExercise);
        return ResponseEntity
            .created(new URI("/api/activity-exercises/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /activity-exercises/:id} : Updates an existing activityExercise.
     *
     * @param id the id of the activityExercise to save.
     * @param activityExercise the activityExercise to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated activityExercise,
     * or with status {@code 400 (Bad Request)} if the activityExercise is not valid,
     * or with status {@code 500 (Internal Server Error)} if the activityExercise couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/activity-exercises/{id}")
    public ResponseEntity<ActivityExercise> updateActivityExercise(
        @PathVariable(value = "id", required = false) final UUID id,
        @RequestBody ActivityExercise activityExercise
    ) throws URISyntaxException {
        log.debug("REST request to update ActivityExercise : {}, {}", id, activityExercise);
        if (activityExercise.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, activityExercise.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!activityExerciseRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ActivityExercise result = activityExerciseService.update(activityExercise);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, activityExercise.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /activity-exercises/:id} : Partial updates given fields of an existing activityExercise, field will ignore if it is null
     *
     * @param id the id of the activityExercise to save.
     * @param activityExercise the activityExercise to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated activityExercise,
     * or with status {@code 400 (Bad Request)} if the activityExercise is not valid,
     * or with status {@code 404 (Not Found)} if the activityExercise is not found,
     * or with status {@code 500 (Internal Server Error)} if the activityExercise couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/activity-exercises/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ActivityExercise> partialUpdateActivityExercise(
        @PathVariable(value = "id", required = false) final UUID id,
        @RequestBody ActivityExercise activityExercise
    ) throws URISyntaxException {
        log.debug("REST request to partial update ActivityExercise partially : {}, {}", id, activityExercise);
        if (activityExercise.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, activityExercise.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!activityExerciseRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ActivityExercise> result = activityExerciseService.partialUpdate(activityExercise);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, activityExercise.getId().toString())
        );
    }

    /**
     * {@code GET  /activity-exercises} : get all the activityExercises.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of activityExercises in body.
     */
    @GetMapping("/activity-exercises")
    public ResponseEntity<List<ActivityExercise>> getAllActivityExercises(
        ActivityExerciseCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get ActivityExercises by criteria: {}", criteria);
        Page<ActivityExercise> page = activityExerciseQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /activity-exercises/count} : count all the activityExercises.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/activity-exercises/count")
    public ResponseEntity<Long> countActivityExercises(ActivityExerciseCriteria criteria) {
        log.debug("REST request to count ActivityExercises by criteria: {}", criteria);
        return ResponseEntity.ok().body(activityExerciseQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /activity-exercises/:id} : get the "id" activityExercise.
     *
     * @param id the id of the activityExercise to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the activityExercise, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/activity-exercises/{id}")
    public ResponseEntity<ActivityExercise> getActivityExercise(@PathVariable UUID id) {
        log.debug("REST request to get ActivityExercise : {}", id);
        Optional<ActivityExercise> activityExercise = activityExerciseService.findOne(id);
        return ResponseUtil.wrapOrNotFound(activityExercise);
    }

    /**
     * {@code DELETE  /activity-exercises/:id} : delete the "id" activityExercise.
     *
     * @param id the id of the activityExercise to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/activity-exercises/{id}")
    public ResponseEntity<Void> deleteActivityExercise(@PathVariable UUID id) {
        log.debug("REST request to delete ActivityExercise : {}", id);
        activityExerciseService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
