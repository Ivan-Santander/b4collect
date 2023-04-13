package com.be4tech.b4carecollect.web.rest;

import com.be4tech.b4carecollect.domain.UserBodyInfo;
import com.be4tech.b4carecollect.repository.UserBodyInfoRepository;
import com.be4tech.b4carecollect.service.UserBodyInfoQueryService;
import com.be4tech.b4carecollect.service.UserBodyInfoService;
import com.be4tech.b4carecollect.service.criteria.UserBodyInfoCriteria;
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
 * REST controller for managing {@link com.be4tech.b4carecollect.domain.UserBodyInfo}.
 */
@RestController
@RequestMapping("/api")
public class UserBodyInfoResource {

    private final Logger log = LoggerFactory.getLogger(UserBodyInfoResource.class);

    private static final String ENTITY_NAME = "userBodyInfo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UserBodyInfoService userBodyInfoService;

    private final UserBodyInfoRepository userBodyInfoRepository;

    private final UserBodyInfoQueryService userBodyInfoQueryService;

    public UserBodyInfoResource(
        UserBodyInfoService userBodyInfoService,
        UserBodyInfoRepository userBodyInfoRepository,
        UserBodyInfoQueryService userBodyInfoQueryService
    ) {
        this.userBodyInfoService = userBodyInfoService;
        this.userBodyInfoRepository = userBodyInfoRepository;
        this.userBodyInfoQueryService = userBodyInfoQueryService;
    }

    /**
     * {@code POST  /user-body-infos} : Create a new userBodyInfo.
     *
     * @param userBodyInfo the userBodyInfo to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new userBodyInfo, or with status {@code 400 (Bad Request)} if the userBodyInfo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/user-body-infos")
    public ResponseEntity<UserBodyInfo> createUserBodyInfo(@RequestBody UserBodyInfo userBodyInfo) throws URISyntaxException {
        log.debug("REST request to save UserBodyInfo : {}", userBodyInfo);
        if (userBodyInfo.getId() != null) {
            throw new BadRequestAlertException("A new userBodyInfo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UserBodyInfo result = userBodyInfoService.save(userBodyInfo);
        return ResponseEntity
            .created(new URI("/api/user-body-infos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /user-body-infos/:id} : Updates an existing userBodyInfo.
     *
     * @param id the id of the userBodyInfo to save.
     * @param userBodyInfo the userBodyInfo to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated userBodyInfo,
     * or with status {@code 400 (Bad Request)} if the userBodyInfo is not valid,
     * or with status {@code 500 (Internal Server Error)} if the userBodyInfo couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/user-body-infos/{id}")
    public ResponseEntity<UserBodyInfo> updateUserBodyInfo(
        @PathVariable(value = "id", required = false) final UUID id,
        @RequestBody UserBodyInfo userBodyInfo
    ) throws URISyntaxException {
        log.debug("REST request to update UserBodyInfo : {}, {}", id, userBodyInfo);
        if (userBodyInfo.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, userBodyInfo.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!userBodyInfoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        UserBodyInfo result = userBodyInfoService.update(userBodyInfo);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, userBodyInfo.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /user-body-infos/:id} : Partial updates given fields of an existing userBodyInfo, field will ignore if it is null
     *
     * @param id the id of the userBodyInfo to save.
     * @param userBodyInfo the userBodyInfo to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated userBodyInfo,
     * or with status {@code 400 (Bad Request)} if the userBodyInfo is not valid,
     * or with status {@code 404 (Not Found)} if the userBodyInfo is not found,
     * or with status {@code 500 (Internal Server Error)} if the userBodyInfo couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/user-body-infos/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<UserBodyInfo> partialUpdateUserBodyInfo(
        @PathVariable(value = "id", required = false) final UUID id,
        @RequestBody UserBodyInfo userBodyInfo
    ) throws URISyntaxException {
        log.debug("REST request to partial update UserBodyInfo partially : {}, {}", id, userBodyInfo);
        if (userBodyInfo.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, userBodyInfo.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!userBodyInfoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<UserBodyInfo> result = userBodyInfoService.partialUpdate(userBodyInfo);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, userBodyInfo.getId().toString())
        );
    }

    /**
     * {@code GET  /user-body-infos} : get all the userBodyInfos.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of userBodyInfos in body.
     */
    @GetMapping("/user-body-infos")
    public ResponseEntity<List<UserBodyInfo>> getAllUserBodyInfos(
        UserBodyInfoCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get UserBodyInfos by criteria: {}", criteria);
        Page<UserBodyInfo> page = userBodyInfoQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /user-body-infos/count} : count all the userBodyInfos.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/user-body-infos/count")
    public ResponseEntity<Long> countUserBodyInfos(UserBodyInfoCriteria criteria) {
        log.debug("REST request to count UserBodyInfos by criteria: {}", criteria);
        return ResponseEntity.ok().body(userBodyInfoQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /user-body-infos/:id} : get the "id" userBodyInfo.
     *
     * @param id the id of the userBodyInfo to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the userBodyInfo, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/user-body-infos/{id}")
    public ResponseEntity<UserBodyInfo> getUserBodyInfo(@PathVariable UUID id) {
        log.debug("REST request to get UserBodyInfo : {}", id);
        Optional<UserBodyInfo> userBodyInfo = userBodyInfoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(userBodyInfo);
    }

    /**
     * {@code DELETE  /user-body-infos/:id} : delete the "id" userBodyInfo.
     *
     * @param id the id of the userBodyInfo to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/user-body-infos/{id}")
    public ResponseEntity<Void> deleteUserBodyInfo(@PathVariable UUID id) {
        log.debug("REST request to delete UserBodyInfo : {}", id);
        userBodyInfoService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
