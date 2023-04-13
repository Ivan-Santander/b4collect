package com.be4tech.b4carecollect.web.rest;

import com.be4tech.b4carecollect.domain.FuntionalIndex;
import com.be4tech.b4carecollect.repository.FuntionalIndexRepository;
import com.be4tech.b4carecollect.service.FuntionalIndexQueryService;
import com.be4tech.b4carecollect.service.FuntionalIndexService;
import com.be4tech.b4carecollect.service.criteria.FuntionalIndexCriteria;
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
 * REST controller for managing {@link com.be4tech.b4carecollect.domain.FuntionalIndex}.
 */
@RestController
@RequestMapping("/api")
public class FuntionalIndexResource {

    private final Logger log = LoggerFactory.getLogger(FuntionalIndexResource.class);

    private static final String ENTITY_NAME = "funtionalIndex";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FuntionalIndexService funtionalIndexService;

    private final FuntionalIndexRepository funtionalIndexRepository;

    private final FuntionalIndexQueryService funtionalIndexQueryService;

    public FuntionalIndexResource(
        FuntionalIndexService funtionalIndexService,
        FuntionalIndexRepository funtionalIndexRepository,
        FuntionalIndexQueryService funtionalIndexQueryService
    ) {
        this.funtionalIndexService = funtionalIndexService;
        this.funtionalIndexRepository = funtionalIndexRepository;
        this.funtionalIndexQueryService = funtionalIndexQueryService;
    }

    /**
     * {@code POST  /funtional-indices} : Create a new funtionalIndex.
     *
     * @param funtionalIndex the funtionalIndex to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new funtionalIndex, or with status {@code 400 (Bad Request)} if the funtionalIndex has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/funtional-indices")
    public ResponseEntity<FuntionalIndex> createFuntionalIndex(@RequestBody FuntionalIndex funtionalIndex) throws URISyntaxException {
        log.debug("REST request to save FuntionalIndex : {}", funtionalIndex);
        if (funtionalIndex.getId() != null) {
            throw new BadRequestAlertException("A new funtionalIndex cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FuntionalIndex result = funtionalIndexService.save(funtionalIndex);
        return ResponseEntity
            .created(new URI("/api/funtional-indices/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /funtional-indices/:id} : Updates an existing funtionalIndex.
     *
     * @param id the id of the funtionalIndex to save.
     * @param funtionalIndex the funtionalIndex to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated funtionalIndex,
     * or with status {@code 400 (Bad Request)} if the funtionalIndex is not valid,
     * or with status {@code 500 (Internal Server Error)} if the funtionalIndex couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/funtional-indices/{id}")
    public ResponseEntity<FuntionalIndex> updateFuntionalIndex(
        @PathVariable(value = "id", required = false) final UUID id,
        @RequestBody FuntionalIndex funtionalIndex
    ) throws URISyntaxException {
        log.debug("REST request to update FuntionalIndex : {}, {}", id, funtionalIndex);
        if (funtionalIndex.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, funtionalIndex.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!funtionalIndexRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        FuntionalIndex result = funtionalIndexService.update(funtionalIndex);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, funtionalIndex.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /funtional-indices/:id} : Partial updates given fields of an existing funtionalIndex, field will ignore if it is null
     *
     * @param id the id of the funtionalIndex to save.
     * @param funtionalIndex the funtionalIndex to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated funtionalIndex,
     * or with status {@code 400 (Bad Request)} if the funtionalIndex is not valid,
     * or with status {@code 404 (Not Found)} if the funtionalIndex is not found,
     * or with status {@code 500 (Internal Server Error)} if the funtionalIndex couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/funtional-indices/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<FuntionalIndex> partialUpdateFuntionalIndex(
        @PathVariable(value = "id", required = false) final UUID id,
        @RequestBody FuntionalIndex funtionalIndex
    ) throws URISyntaxException {
        log.debug("REST request to partial update FuntionalIndex partially : {}, {}", id, funtionalIndex);
        if (funtionalIndex.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, funtionalIndex.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!funtionalIndexRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<FuntionalIndex> result = funtionalIndexService.partialUpdate(funtionalIndex);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, funtionalIndex.getId().toString())
        );
    }

    /**
     * {@code GET  /funtional-indices} : get all the funtionalIndices.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of funtionalIndices in body.
     */
    @GetMapping("/funtional-indices")
    public ResponseEntity<List<FuntionalIndex>> getAllFuntionalIndices(
        FuntionalIndexCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get FuntionalIndices by criteria: {}", criteria);
        Page<FuntionalIndex> page = funtionalIndexQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /funtional-indices/count} : count all the funtionalIndices.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/funtional-indices/count")
    public ResponseEntity<Long> countFuntionalIndices(FuntionalIndexCriteria criteria) {
        log.debug("REST request to count FuntionalIndices by criteria: {}", criteria);
        return ResponseEntity.ok().body(funtionalIndexQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /funtional-indices/:id} : get the "id" funtionalIndex.
     *
     * @param id the id of the funtionalIndex to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the funtionalIndex, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/funtional-indices/{id}")
    public ResponseEntity<FuntionalIndex> getFuntionalIndex(@PathVariable UUID id) {
        log.debug("REST request to get FuntionalIndex : {}", id);
        Optional<FuntionalIndex> funtionalIndex = funtionalIndexService.findOne(id);
        return ResponseUtil.wrapOrNotFound(funtionalIndex);
    }

    /**
     * {@code DELETE  /funtional-indices/:id} : delete the "id" funtionalIndex.
     *
     * @param id the id of the funtionalIndex to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/funtional-indices/{id}")
    public ResponseEntity<Void> deleteFuntionalIndex(@PathVariable UUID id) {
        log.debug("REST request to delete FuntionalIndex : {}", id);
        funtionalIndexService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
