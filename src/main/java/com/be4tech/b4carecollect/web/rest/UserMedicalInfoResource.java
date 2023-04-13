package com.be4tech.b4carecollect.web.rest;

import com.be4tech.b4carecollect.domain.UserMedicalInfo;
import com.be4tech.b4carecollect.repository.UserMedicalInfoRepository;
import com.be4tech.b4carecollect.service.UserMedicalInfoQueryService;
import com.be4tech.b4carecollect.service.UserMedicalInfoService;
import com.be4tech.b4carecollect.service.criteria.UserMedicalInfoCriteria;
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
 * REST controller for managing {@link com.be4tech.b4carecollect.domain.UserMedicalInfo}.
 */
@RestController
@RequestMapping("/api")
public class UserMedicalInfoResource {

    private final Logger log = LoggerFactory.getLogger(UserMedicalInfoResource.class);

    private static final String ENTITY_NAME = "userMedicalInfo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UserMedicalInfoService userMedicalInfoService;

    private final UserMedicalInfoRepository userMedicalInfoRepository;

    private final UserMedicalInfoQueryService userMedicalInfoQueryService;

    public UserMedicalInfoResource(
        UserMedicalInfoService userMedicalInfoService,
        UserMedicalInfoRepository userMedicalInfoRepository,
        UserMedicalInfoQueryService userMedicalInfoQueryService
    ) {
        this.userMedicalInfoService = userMedicalInfoService;
        this.userMedicalInfoRepository = userMedicalInfoRepository;
        this.userMedicalInfoQueryService = userMedicalInfoQueryService;
    }

    /**
     * {@code POST  /user-medical-infos} : Create a new userMedicalInfo.
     *
     * @param userMedicalInfo the userMedicalInfo to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new userMedicalInfo, or with status {@code 400 (Bad Request)} if the userMedicalInfo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/user-medical-infos")
    public ResponseEntity<UserMedicalInfo> createUserMedicalInfo(@RequestBody UserMedicalInfo userMedicalInfo) throws URISyntaxException {
        log.debug("REST request to save UserMedicalInfo : {}", userMedicalInfo);
        if (userMedicalInfo.getId() != null) {
            throw new BadRequestAlertException("A new userMedicalInfo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UserMedicalInfo result = userMedicalInfoService.save(userMedicalInfo);
        return ResponseEntity
            .created(new URI("/api/user-medical-infos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /user-medical-infos/:id} : Updates an existing userMedicalInfo.
     *
     * @param id the id of the userMedicalInfo to save.
     * @param userMedicalInfo the userMedicalInfo to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated userMedicalInfo,
     * or with status {@code 400 (Bad Request)} if the userMedicalInfo is not valid,
     * or with status {@code 500 (Internal Server Error)} if the userMedicalInfo couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/user-medical-infos/{id}")
    public ResponseEntity<UserMedicalInfo> updateUserMedicalInfo(
        @PathVariable(value = "id", required = false) final UUID id,
        @RequestBody UserMedicalInfo userMedicalInfo
    ) throws URISyntaxException {
        log.debug("REST request to update UserMedicalInfo : {}, {}", id, userMedicalInfo);
        if (userMedicalInfo.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, userMedicalInfo.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!userMedicalInfoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        UserMedicalInfo result = userMedicalInfoService.update(userMedicalInfo);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, userMedicalInfo.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /user-medical-infos/:id} : Partial updates given fields of an existing userMedicalInfo, field will ignore if it is null
     *
     * @param id the id of the userMedicalInfo to save.
     * @param userMedicalInfo the userMedicalInfo to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated userMedicalInfo,
     * or with status {@code 400 (Bad Request)} if the userMedicalInfo is not valid,
     * or with status {@code 404 (Not Found)} if the userMedicalInfo is not found,
     * or with status {@code 500 (Internal Server Error)} if the userMedicalInfo couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/user-medical-infos/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<UserMedicalInfo> partialUpdateUserMedicalInfo(
        @PathVariable(value = "id", required = false) final UUID id,
        @RequestBody UserMedicalInfo userMedicalInfo
    ) throws URISyntaxException {
        log.debug("REST request to partial update UserMedicalInfo partially : {}, {}", id, userMedicalInfo);
        if (userMedicalInfo.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, userMedicalInfo.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!userMedicalInfoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<UserMedicalInfo> result = userMedicalInfoService.partialUpdate(userMedicalInfo);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, userMedicalInfo.getId().toString())
        );
    }

    /**
     * {@code GET  /user-medical-infos} : get all the userMedicalInfos.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of userMedicalInfos in body.
     */
    @GetMapping("/user-medical-infos")
    public ResponseEntity<List<UserMedicalInfo>> getAllUserMedicalInfos(
        UserMedicalInfoCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get UserMedicalInfos by criteria: {}", criteria);
        Page<UserMedicalInfo> page = userMedicalInfoQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /user-medical-infos/count} : count all the userMedicalInfos.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/user-medical-infos/count")
    public ResponseEntity<Long> countUserMedicalInfos(UserMedicalInfoCriteria criteria) {
        log.debug("REST request to count UserMedicalInfos by criteria: {}", criteria);
        return ResponseEntity.ok().body(userMedicalInfoQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /user-medical-infos/:id} : get the "id" userMedicalInfo.
     *
     * @param id the id of the userMedicalInfo to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the userMedicalInfo, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/user-medical-infos/{id}")
    public ResponseEntity<UserMedicalInfo> getUserMedicalInfo(@PathVariable UUID id) {
        log.debug("REST request to get UserMedicalInfo : {}", id);
        Optional<UserMedicalInfo> userMedicalInfo = userMedicalInfoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(userMedicalInfo);
    }

    /**
     * {@code DELETE  /user-medical-infos/:id} : delete the "id" userMedicalInfo.
     *
     * @param id the id of the userMedicalInfo to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/user-medical-infos/{id}")
    public ResponseEntity<Void> deleteUserMedicalInfo(@PathVariable UUID id) {
        log.debug("REST request to delete UserMedicalInfo : {}", id);
        userMedicalInfoService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
