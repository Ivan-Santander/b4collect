package com.be4tech.b4carecollect.web.rest;

import com.be4tech.b4carecollect.domain.HeartMinutes;
import com.be4tech.b4carecollect.repository.HeartMinutesRepository;
import com.be4tech.b4carecollect.service.HeartMinutesQueryService;
import com.be4tech.b4carecollect.service.HeartMinutesService;
import com.be4tech.b4carecollect.service.criteria.HeartMinutesCriteria;
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
 * REST controller for managing {@link com.be4tech.b4carecollect.domain.HeartMinutes}.
 */
@RestController
@RequestMapping("/api")
public class HeartMinutesResource {

    private final Logger log = LoggerFactory.getLogger(HeartMinutesResource.class);

    private static final String ENTITY_NAME = "heartMinutes";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final HeartMinutesService heartMinutesService;

    private final HeartMinutesRepository heartMinutesRepository;

    private final HeartMinutesQueryService heartMinutesQueryService;

    public HeartMinutesResource(
        HeartMinutesService heartMinutesService,
        HeartMinutesRepository heartMinutesRepository,
        HeartMinutesQueryService heartMinutesQueryService
    ) {
        this.heartMinutesService = heartMinutesService;
        this.heartMinutesRepository = heartMinutesRepository;
        this.heartMinutesQueryService = heartMinutesQueryService;
    }

    /**
     * {@code POST  /heart-minutes} : Create a new heartMinutes.
     *
     * @param heartMinutes the heartMinutes to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new heartMinutes, or with status {@code 400 (Bad Request)} if the heartMinutes has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/heart-minutes")
    public ResponseEntity<HeartMinutes> createHeartMinutes(@RequestBody HeartMinutes heartMinutes) throws URISyntaxException {
        log.debug("REST request to save HeartMinutes : {}", heartMinutes);
        if (heartMinutes.getId() != null) {
            throw new BadRequestAlertException("A new heartMinutes cannot already have an ID", ENTITY_NAME, "idexists");
        }
        HeartMinutes result = heartMinutesService.save(heartMinutes);
        return ResponseEntity
            .created(new URI("/api/heart-minutes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /heart-minutes/:id} : Updates an existing heartMinutes.
     *
     * @param id the id of the heartMinutes to save.
     * @param heartMinutes the heartMinutes to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated heartMinutes,
     * or with status {@code 400 (Bad Request)} if the heartMinutes is not valid,
     * or with status {@code 500 (Internal Server Error)} if the heartMinutes couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/heart-minutes/{id}")
    public ResponseEntity<HeartMinutes> updateHeartMinutes(
        @PathVariable(value = "id", required = false) final UUID id,
        @RequestBody HeartMinutes heartMinutes
    ) throws URISyntaxException {
        log.debug("REST request to update HeartMinutes : {}, {}", id, heartMinutes);
        if (heartMinutes.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, heartMinutes.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!heartMinutesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        HeartMinutes result = heartMinutesService.update(heartMinutes);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, heartMinutes.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /heart-minutes/:id} : Partial updates given fields of an existing heartMinutes, field will ignore if it is null
     *
     * @param id the id of the heartMinutes to save.
     * @param heartMinutes the heartMinutes to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated heartMinutes,
     * or with status {@code 400 (Bad Request)} if the heartMinutes is not valid,
     * or with status {@code 404 (Not Found)} if the heartMinutes is not found,
     * or with status {@code 500 (Internal Server Error)} if the heartMinutes couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/heart-minutes/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<HeartMinutes> partialUpdateHeartMinutes(
        @PathVariable(value = "id", required = false) final UUID id,
        @RequestBody HeartMinutes heartMinutes
    ) throws URISyntaxException {
        log.debug("REST request to partial update HeartMinutes partially : {}, {}", id, heartMinutes);
        if (heartMinutes.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, heartMinutes.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!heartMinutesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<HeartMinutes> result = heartMinutesService.partialUpdate(heartMinutes);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, heartMinutes.getId().toString())
        );
    }

    /**
     * {@code GET  /heart-minutes} : get all the heartMinutes.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of heartMinutes in body.
     */
    @GetMapping("/heart-minutes")
    public ResponseEntity<List<HeartMinutes>> getAllHeartMinutes(
        HeartMinutesCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get HeartMinutes by criteria: {}", criteria);
        Page<HeartMinutes> page = heartMinutesQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /heart-minutes/count} : count all the heartMinutes.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/heart-minutes/count")
    public ResponseEntity<Long> countHeartMinutes(HeartMinutesCriteria criteria) {
        log.debug("REST request to count HeartMinutes by criteria: {}", criteria);
        return ResponseEntity.ok().body(heartMinutesQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /heart-minutes/:id} : get the "id" heartMinutes.
     *
     * @param id the id of the heartMinutes to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the heartMinutes, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/heart-minutes/{id}")
    public ResponseEntity<HeartMinutes> getHeartMinutes(@PathVariable UUID id) {
        log.debug("REST request to get HeartMinutes : {}", id);
        Optional<HeartMinutes> heartMinutes = heartMinutesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(heartMinutes);
    }

    /**
     * {@code DELETE  /heart-minutes/:id} : delete the "id" heartMinutes.
     *
     * @param id the id of the heartMinutes to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/heart-minutes/{id}")
    public ResponseEntity<Void> deleteHeartMinutes(@PathVariable UUID id) {
        log.debug("REST request to delete HeartMinutes : {}", id);
        heartMinutesService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
