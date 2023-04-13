package com.be4tech.b4carecollect.web.rest;

import com.be4tech.b4carecollect.domain.ActiveMinutes;
import com.be4tech.b4carecollect.repository.ActiveMinutesRepository;
import com.be4tech.b4carecollect.service.ActiveMinutesQueryService;
import com.be4tech.b4carecollect.service.ActiveMinutesService;
import com.be4tech.b4carecollect.service.criteria.ActiveMinutesCriteria;
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
 * REST controller for managing {@link com.be4tech.b4carecollect.domain.ActiveMinutes}.
 */
@RestController
@RequestMapping("/api")
public class ActiveMinutesResource {

    private final Logger log = LoggerFactory.getLogger(ActiveMinutesResource.class);

    private static final String ENTITY_NAME = "activeMinutes";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ActiveMinutesService activeMinutesService;

    private final ActiveMinutesRepository activeMinutesRepository;

    private final ActiveMinutesQueryService activeMinutesQueryService;

    public ActiveMinutesResource(
        ActiveMinutesService activeMinutesService,
        ActiveMinutesRepository activeMinutesRepository,
        ActiveMinutesQueryService activeMinutesQueryService
    ) {
        this.activeMinutesService = activeMinutesService;
        this.activeMinutesRepository = activeMinutesRepository;
        this.activeMinutesQueryService = activeMinutesQueryService;
    }

    /**
     * {@code POST  /active-minutes} : Create a new activeMinutes.
     *
     * @param activeMinutes the activeMinutes to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new activeMinutes, or with status {@code 400 (Bad Request)} if the activeMinutes has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/active-minutes")
    public ResponseEntity<ActiveMinutes> createActiveMinutes(@RequestBody ActiveMinutes activeMinutes) throws URISyntaxException {
        log.debug("REST request to save ActiveMinutes : {}", activeMinutes);
        if (activeMinutes.getId() != null) {
            throw new BadRequestAlertException("A new activeMinutes cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ActiveMinutes result = activeMinutesService.save(activeMinutes);
        return ResponseEntity
            .created(new URI("/api/active-minutes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /active-minutes/:id} : Updates an existing activeMinutes.
     *
     * @param id the id of the activeMinutes to save.
     * @param activeMinutes the activeMinutes to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated activeMinutes,
     * or with status {@code 400 (Bad Request)} if the activeMinutes is not valid,
     * or with status {@code 500 (Internal Server Error)} if the activeMinutes couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/active-minutes/{id}")
    public ResponseEntity<ActiveMinutes> updateActiveMinutes(
        @PathVariable(value = "id", required = false) final UUID id,
        @RequestBody ActiveMinutes activeMinutes
    ) throws URISyntaxException {
        log.debug("REST request to update ActiveMinutes : {}, {}", id, activeMinutes);
        if (activeMinutes.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, activeMinutes.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!activeMinutesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ActiveMinutes result = activeMinutesService.update(activeMinutes);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, activeMinutes.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /active-minutes/:id} : Partial updates given fields of an existing activeMinutes, field will ignore if it is null
     *
     * @param id the id of the activeMinutes to save.
     * @param activeMinutes the activeMinutes to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated activeMinutes,
     * or with status {@code 400 (Bad Request)} if the activeMinutes is not valid,
     * or with status {@code 404 (Not Found)} if the activeMinutes is not found,
     * or with status {@code 500 (Internal Server Error)} if the activeMinutes couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/active-minutes/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ActiveMinutes> partialUpdateActiveMinutes(
        @PathVariable(value = "id", required = false) final UUID id,
        @RequestBody ActiveMinutes activeMinutes
    ) throws URISyntaxException {
        log.debug("REST request to partial update ActiveMinutes partially : {}, {}", id, activeMinutes);
        if (activeMinutes.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, activeMinutes.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!activeMinutesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ActiveMinutes> result = activeMinutesService.partialUpdate(activeMinutes);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, activeMinutes.getId().toString())
        );
    }

    /**
     * {@code GET  /active-minutes} : get all the activeMinutes.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of activeMinutes in body.
     */
    @GetMapping("/active-minutes")
    public ResponseEntity<List<ActiveMinutes>> getAllActiveMinutes(
        ActiveMinutesCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get ActiveMinutes by criteria: {}", criteria);
        Page<ActiveMinutes> page = activeMinutesQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /active-minutes/count} : count all the activeMinutes.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/active-minutes/count")
    public ResponseEntity<Long> countActiveMinutes(ActiveMinutesCriteria criteria) {
        log.debug("REST request to count ActiveMinutes by criteria: {}", criteria);
        return ResponseEntity.ok().body(activeMinutesQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /active-minutes/:id} : get the "id" activeMinutes.
     *
     * @param id the id of the activeMinutes to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the activeMinutes, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/active-minutes/{id}")
    public ResponseEntity<ActiveMinutes> getActiveMinutes(@PathVariable UUID id) {
        log.debug("REST request to get ActiveMinutes : {}", id);
        Optional<ActiveMinutes> activeMinutes = activeMinutesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(activeMinutes);
    }

    /**
     * {@code DELETE  /active-minutes/:id} : delete the "id" activeMinutes.
     *
     * @param id the id of the activeMinutes to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/active-minutes/{id}")
    public ResponseEntity<Void> deleteActiveMinutes(@PathVariable UUID id) {
        log.debug("REST request to delete ActiveMinutes : {}", id);
        activeMinutesService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
