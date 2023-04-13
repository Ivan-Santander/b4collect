package com.be4tech.b4carecollect.web.rest;

import com.be4tech.b4carecollect.domain.LocationSample;
import com.be4tech.b4carecollect.repository.LocationSampleRepository;
import com.be4tech.b4carecollect.service.LocationSampleQueryService;
import com.be4tech.b4carecollect.service.LocationSampleService;
import com.be4tech.b4carecollect.service.criteria.LocationSampleCriteria;
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
 * REST controller for managing {@link com.be4tech.b4carecollect.domain.LocationSample}.
 */
@RestController
@RequestMapping("/api")
public class LocationSampleResource {

    private final Logger log = LoggerFactory.getLogger(LocationSampleResource.class);

    private static final String ENTITY_NAME = "locationSample";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LocationSampleService locationSampleService;

    private final LocationSampleRepository locationSampleRepository;

    private final LocationSampleQueryService locationSampleQueryService;

    public LocationSampleResource(
        LocationSampleService locationSampleService,
        LocationSampleRepository locationSampleRepository,
        LocationSampleQueryService locationSampleQueryService
    ) {
        this.locationSampleService = locationSampleService;
        this.locationSampleRepository = locationSampleRepository;
        this.locationSampleQueryService = locationSampleQueryService;
    }

    /**
     * {@code POST  /location-samples} : Create a new locationSample.
     *
     * @param locationSample the locationSample to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new locationSample, or with status {@code 400 (Bad Request)} if the locationSample has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/location-samples")
    public ResponseEntity<LocationSample> createLocationSample(@RequestBody LocationSample locationSample) throws URISyntaxException {
        log.debug("REST request to save LocationSample : {}", locationSample);
        if (locationSample.getId() != null) {
            throw new BadRequestAlertException("A new locationSample cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LocationSample result = locationSampleService.save(locationSample);
        return ResponseEntity
            .created(new URI("/api/location-samples/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /location-samples/:id} : Updates an existing locationSample.
     *
     * @param id the id of the locationSample to save.
     * @param locationSample the locationSample to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated locationSample,
     * or with status {@code 400 (Bad Request)} if the locationSample is not valid,
     * or with status {@code 500 (Internal Server Error)} if the locationSample couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/location-samples/{id}")
    public ResponseEntity<LocationSample> updateLocationSample(
        @PathVariable(value = "id", required = false) final UUID id,
        @RequestBody LocationSample locationSample
    ) throws URISyntaxException {
        log.debug("REST request to update LocationSample : {}, {}", id, locationSample);
        if (locationSample.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, locationSample.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!locationSampleRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        LocationSample result = locationSampleService.update(locationSample);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, locationSample.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /location-samples/:id} : Partial updates given fields of an existing locationSample, field will ignore if it is null
     *
     * @param id the id of the locationSample to save.
     * @param locationSample the locationSample to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated locationSample,
     * or with status {@code 400 (Bad Request)} if the locationSample is not valid,
     * or with status {@code 404 (Not Found)} if the locationSample is not found,
     * or with status {@code 500 (Internal Server Error)} if the locationSample couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/location-samples/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<LocationSample> partialUpdateLocationSample(
        @PathVariable(value = "id", required = false) final UUID id,
        @RequestBody LocationSample locationSample
    ) throws URISyntaxException {
        log.debug("REST request to partial update LocationSample partially : {}, {}", id, locationSample);
        if (locationSample.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, locationSample.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!locationSampleRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<LocationSample> result = locationSampleService.partialUpdate(locationSample);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, locationSample.getId().toString())
        );
    }

    /**
     * {@code GET  /location-samples} : get all the locationSamples.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of locationSamples in body.
     */
    @GetMapping("/location-samples")
    public ResponseEntity<List<LocationSample>> getAllLocationSamples(
        LocationSampleCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get LocationSamples by criteria: {}", criteria);
        Page<LocationSample> page = locationSampleQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /location-samples/count} : count all the locationSamples.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/location-samples/count")
    public ResponseEntity<Long> countLocationSamples(LocationSampleCriteria criteria) {
        log.debug("REST request to count LocationSamples by criteria: {}", criteria);
        return ResponseEntity.ok().body(locationSampleQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /location-samples/:id} : get the "id" locationSample.
     *
     * @param id the id of the locationSample to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the locationSample, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/location-samples/{id}")
    public ResponseEntity<LocationSample> getLocationSample(@PathVariable UUID id) {
        log.debug("REST request to get LocationSample : {}", id);
        Optional<LocationSample> locationSample = locationSampleService.findOne(id);
        return ResponseUtil.wrapOrNotFound(locationSample);
    }

    /**
     * {@code DELETE  /location-samples/:id} : delete the "id" locationSample.
     *
     * @param id the id of the locationSample to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/location-samples/{id}")
    public ResponseEntity<Void> deleteLocationSample(@PathVariable UUID id) {
        log.debug("REST request to delete LocationSample : {}", id);
        locationSampleService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
