package com.be4tech.b4carecollect.web.rest;

import com.be4tech.b4carecollect.domain.UserDemographics;
import com.be4tech.b4carecollect.repository.UserDemographicsRepository;
import com.be4tech.b4carecollect.service.UserDemographicsQueryService;
import com.be4tech.b4carecollect.service.UserDemographicsService;
import com.be4tech.b4carecollect.service.criteria.UserDemographicsCriteria;
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
 * REST controller for managing {@link com.be4tech.b4carecollect.domain.UserDemographics}.
 */
@RestController
@RequestMapping("/api")
public class UserDemographicsResource {

    private final Logger log = LoggerFactory.getLogger(UserDemographicsResource.class);

    private static final String ENTITY_NAME = "userDemographics";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UserDemographicsService userDemographicsService;

    private final UserDemographicsRepository userDemographicsRepository;

    private final UserDemographicsQueryService userDemographicsQueryService;

    public UserDemographicsResource(
        UserDemographicsService userDemographicsService,
        UserDemographicsRepository userDemographicsRepository,
        UserDemographicsQueryService userDemographicsQueryService
    ) {
        this.userDemographicsService = userDemographicsService;
        this.userDemographicsRepository = userDemographicsRepository;
        this.userDemographicsQueryService = userDemographicsQueryService;
    }

    /**
     * {@code POST  /user-demographics} : Create a new userDemographics.
     *
     * @param userDemographics the userDemographics to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new userDemographics, or with status {@code 400 (Bad Request)} if the userDemographics has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/user-demographics")
    public ResponseEntity<UserDemographics> createUserDemographics(@RequestBody UserDemographics userDemographics)
        throws URISyntaxException {
        log.debug("REST request to save UserDemographics : {}", userDemographics);
        if (userDemographics.getId() != null) {
            throw new BadRequestAlertException("A new userDemographics cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UserDemographics result = userDemographicsService.save(userDemographics);
        return ResponseEntity
            .created(new URI("/api/user-demographics/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /user-demographics/:id} : Updates an existing userDemographics.
     *
     * @param id the id of the userDemographics to save.
     * @param userDemographics the userDemographics to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated userDemographics,
     * or with status {@code 400 (Bad Request)} if the userDemographics is not valid,
     * or with status {@code 500 (Internal Server Error)} if the userDemographics couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/user-demographics/{id}")
    public ResponseEntity<UserDemographics> updateUserDemographics(
        @PathVariable(value = "id", required = false) final UUID id,
        @RequestBody UserDemographics userDemographics
    ) throws URISyntaxException {
        log.debug("REST request to update UserDemographics : {}, {}", id, userDemographics);
        if (userDemographics.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, userDemographics.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!userDemographicsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        UserDemographics result = userDemographicsService.update(userDemographics);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, userDemographics.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /user-demographics/:id} : Partial updates given fields of an existing userDemographics, field will ignore if it is null
     *
     * @param id the id of the userDemographics to save.
     * @param userDemographics the userDemographics to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated userDemographics,
     * or with status {@code 400 (Bad Request)} if the userDemographics is not valid,
     * or with status {@code 404 (Not Found)} if the userDemographics is not found,
     * or with status {@code 500 (Internal Server Error)} if the userDemographics couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/user-demographics/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<UserDemographics> partialUpdateUserDemographics(
        @PathVariable(value = "id", required = false) final UUID id,
        @RequestBody UserDemographics userDemographics
    ) throws URISyntaxException {
        log.debug("REST request to partial update UserDemographics partially : {}, {}", id, userDemographics);
        if (userDemographics.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, userDemographics.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!userDemographicsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<UserDemographics> result = userDemographicsService.partialUpdate(userDemographics);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, userDemographics.getId().toString())
        );
    }

    /**
     * {@code GET  /user-demographics} : get all the userDemographics.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of userDemographics in body.
     */
    @GetMapping("/user-demographics")
    public ResponseEntity<List<UserDemographics>> getAllUserDemographics(
        UserDemographicsCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get UserDemographics by criteria: {}", criteria);
        Page<UserDemographics> page = userDemographicsQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /user-demographics/count} : count all the userDemographics.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/user-demographics/count")
    public ResponseEntity<Long> countUserDemographics(UserDemographicsCriteria criteria) {
        log.debug("REST request to count UserDemographics by criteria: {}", criteria);
        return ResponseEntity.ok().body(userDemographicsQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /user-demographics/:id} : get the "id" userDemographics.
     *
     * @param id the id of the userDemographics to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the userDemographics, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/user-demographics/{id}")
    public ResponseEntity<UserDemographics> getUserDemographics(@PathVariable UUID id) {
        log.debug("REST request to get UserDemographics : {}", id);
        Optional<UserDemographics> userDemographics = userDemographicsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(userDemographics);
    }

    /**
     * {@code DELETE  /user-demographics/:id} : delete the "id" userDemographics.
     *
     * @param id the id of the userDemographics to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/user-demographics/{id}")
    public ResponseEntity<Void> deleteUserDemographics(@PathVariable UUID id) {
        log.debug("REST request to delete UserDemographics : {}", id);
        userDemographicsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
