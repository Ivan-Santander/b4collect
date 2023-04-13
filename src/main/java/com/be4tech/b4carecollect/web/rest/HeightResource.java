package com.be4tech.b4carecollect.web.rest;

import com.be4tech.b4carecollect.domain.Height;
import com.be4tech.b4carecollect.repository.HeightRepository;
import com.be4tech.b4carecollect.service.HeightQueryService;
import com.be4tech.b4carecollect.service.HeightService;
import com.be4tech.b4carecollect.service.criteria.HeightCriteria;
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
 * REST controller for managing {@link com.be4tech.b4carecollect.domain.Height}.
 */
@RestController
@RequestMapping("/api")
public class HeightResource {

    private final Logger log = LoggerFactory.getLogger(HeightResource.class);

    private static final String ENTITY_NAME = "height";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final HeightService heightService;

    private final HeightRepository heightRepository;

    private final HeightQueryService heightQueryService;

    public HeightResource(HeightService heightService, HeightRepository heightRepository, HeightQueryService heightQueryService) {
        this.heightService = heightService;
        this.heightRepository = heightRepository;
        this.heightQueryService = heightQueryService;
    }

    /**
     * {@code POST  /heights} : Create a new height.
     *
     * @param height the height to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new height, or with status {@code 400 (Bad Request)} if the height has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/heights")
    public ResponseEntity<Height> createHeight(@RequestBody Height height) throws URISyntaxException {
        log.debug("REST request to save Height : {}", height);
        if (height.getId() != null) {
            throw new BadRequestAlertException("A new height cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Height result = heightService.save(height);
        return ResponseEntity
            .created(new URI("/api/heights/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /heights/:id} : Updates an existing height.
     *
     * @param id the id of the height to save.
     * @param height the height to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated height,
     * or with status {@code 400 (Bad Request)} if the height is not valid,
     * or with status {@code 500 (Internal Server Error)} if the height couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/heights/{id}")
    public ResponseEntity<Height> updateHeight(@PathVariable(value = "id", required = false) final UUID id, @RequestBody Height height)
        throws URISyntaxException {
        log.debug("REST request to update Height : {}, {}", id, height);
        if (height.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, height.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!heightRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Height result = heightService.update(height);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, height.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /heights/:id} : Partial updates given fields of an existing height, field will ignore if it is null
     *
     * @param id the id of the height to save.
     * @param height the height to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated height,
     * or with status {@code 400 (Bad Request)} if the height is not valid,
     * or with status {@code 404 (Not Found)} if the height is not found,
     * or with status {@code 500 (Internal Server Error)} if the height couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/heights/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Height> partialUpdateHeight(
        @PathVariable(value = "id", required = false) final UUID id,
        @RequestBody Height height
    ) throws URISyntaxException {
        log.debug("REST request to partial update Height partially : {}, {}", id, height);
        if (height.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, height.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!heightRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Height> result = heightService.partialUpdate(height);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, height.getId().toString())
        );
    }

    /**
     * {@code GET  /heights} : get all the heights.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of heights in body.
     */
    @GetMapping("/heights")
    public ResponseEntity<List<Height>> getAllHeights(
        HeightCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get Heights by criteria: {}", criteria);
        Page<Height> page = heightQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /heights/count} : count all the heights.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/heights/count")
    public ResponseEntity<Long> countHeights(HeightCriteria criteria) {
        log.debug("REST request to count Heights by criteria: {}", criteria);
        return ResponseEntity.ok().body(heightQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /heights/:id} : get the "id" height.
     *
     * @param id the id of the height to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the height, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/heights/{id}")
    public ResponseEntity<Height> getHeight(@PathVariable UUID id) {
        log.debug("REST request to get Height : {}", id);
        Optional<Height> height = heightService.findOne(id);
        return ResponseUtil.wrapOrNotFound(height);
    }

    /**
     * {@code DELETE  /heights/:id} : delete the "id" height.
     *
     * @param id the id of the height to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/heights/{id}")
    public ResponseEntity<Void> deleteHeight(@PathVariable UUID id) {
        log.debug("REST request to delete Height : {}", id);
        heightService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
