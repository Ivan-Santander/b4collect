package com.be4tech.b4carecollect.web.rest;

import com.be4tech.b4carecollect.domain.Speed;
import com.be4tech.b4carecollect.repository.SpeedRepository;
import com.be4tech.b4carecollect.service.SpeedQueryService;
import com.be4tech.b4carecollect.service.SpeedService;
import com.be4tech.b4carecollect.service.criteria.SpeedCriteria;
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
 * REST controller for managing {@link com.be4tech.b4carecollect.domain.Speed}.
 */
@RestController
@RequestMapping("/api")
public class SpeedResource {

    private final Logger log = LoggerFactory.getLogger(SpeedResource.class);

    private static final String ENTITY_NAME = "speed";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SpeedService speedService;

    private final SpeedRepository speedRepository;

    private final SpeedQueryService speedQueryService;

    public SpeedResource(SpeedService speedService, SpeedRepository speedRepository, SpeedQueryService speedQueryService) {
        this.speedService = speedService;
        this.speedRepository = speedRepository;
        this.speedQueryService = speedQueryService;
    }

    /**
     * {@code POST  /speeds} : Create a new speed.
     *
     * @param speed the speed to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new speed, or with status {@code 400 (Bad Request)} if the speed has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/speeds")
    public ResponseEntity<Speed> createSpeed(@RequestBody Speed speed) throws URISyntaxException {
        log.debug("REST request to save Speed : {}", speed);
        if (speed.getId() != null) {
            throw new BadRequestAlertException("A new speed cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Speed result = speedService.save(speed);
        return ResponseEntity
            .created(new URI("/api/speeds/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /speeds/:id} : Updates an existing speed.
     *
     * @param id the id of the speed to save.
     * @param speed the speed to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated speed,
     * or with status {@code 400 (Bad Request)} if the speed is not valid,
     * or with status {@code 500 (Internal Server Error)} if the speed couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/speeds/{id}")
    public ResponseEntity<Speed> updateSpeed(@PathVariable(value = "id", required = false) final UUID id, @RequestBody Speed speed)
        throws URISyntaxException {
        log.debug("REST request to update Speed : {}, {}", id, speed);
        if (speed.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, speed.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!speedRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Speed result = speedService.update(speed);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, speed.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /speeds/:id} : Partial updates given fields of an existing speed, field will ignore if it is null
     *
     * @param id the id of the speed to save.
     * @param speed the speed to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated speed,
     * or with status {@code 400 (Bad Request)} if the speed is not valid,
     * or with status {@code 404 (Not Found)} if the speed is not found,
     * or with status {@code 500 (Internal Server Error)} if the speed couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/speeds/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Speed> partialUpdateSpeed(@PathVariable(value = "id", required = false) final UUID id, @RequestBody Speed speed)
        throws URISyntaxException {
        log.debug("REST request to partial update Speed partially : {}, {}", id, speed);
        if (speed.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, speed.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!speedRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Speed> result = speedService.partialUpdate(speed);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, speed.getId().toString())
        );
    }

    /**
     * {@code GET  /speeds} : get all the speeds.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of speeds in body.
     */
    @GetMapping("/speeds")
    public ResponseEntity<List<Speed>> getAllSpeeds(
        SpeedCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get Speeds by criteria: {}", criteria);
        Page<Speed> page = speedQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /speeds/count} : count all the speeds.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/speeds/count")
    public ResponseEntity<Long> countSpeeds(SpeedCriteria criteria) {
        log.debug("REST request to count Speeds by criteria: {}", criteria);
        return ResponseEntity.ok().body(speedQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /speeds/:id} : get the "id" speed.
     *
     * @param id the id of the speed to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the speed, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/speeds/{id}")
    public ResponseEntity<Speed> getSpeed(@PathVariable UUID id) {
        log.debug("REST request to get Speed : {}", id);
        Optional<Speed> speed = speedService.findOne(id);
        return ResponseUtil.wrapOrNotFound(speed);
    }

    /**
     * {@code DELETE  /speeds/:id} : delete the "id" speed.
     *
     * @param id the id of the speed to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/speeds/{id}")
    public ResponseEntity<Void> deleteSpeed(@PathVariable UUID id) {
        log.debug("REST request to delete Speed : {}", id);
        speedService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
