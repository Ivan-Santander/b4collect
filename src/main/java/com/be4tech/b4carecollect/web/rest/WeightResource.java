package com.be4tech.b4carecollect.web.rest;

import com.be4tech.b4carecollect.domain.Weight;
import com.be4tech.b4carecollect.repository.WeightRepository;
import com.be4tech.b4carecollect.service.WeightQueryService;
import com.be4tech.b4carecollect.service.WeightService;
import com.be4tech.b4carecollect.service.criteria.WeightCriteria;
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
 * REST controller for managing {@link com.be4tech.b4carecollect.domain.Weight}.
 */
@RestController
@RequestMapping("/api")
public class WeightResource {

    private final Logger log = LoggerFactory.getLogger(WeightResource.class);

    private static final String ENTITY_NAME = "weight";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final WeightService weightService;

    private final WeightRepository weightRepository;

    private final WeightQueryService weightQueryService;

    public WeightResource(WeightService weightService, WeightRepository weightRepository, WeightQueryService weightQueryService) {
        this.weightService = weightService;
        this.weightRepository = weightRepository;
        this.weightQueryService = weightQueryService;
    }

    /**
     * {@code POST  /weights} : Create a new weight.
     *
     * @param weight the weight to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new weight, or with status {@code 400 (Bad Request)} if the weight has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/weights")
    public ResponseEntity<Weight> createWeight(@RequestBody Weight weight) throws URISyntaxException {
        log.debug("REST request to save Weight : {}", weight);
        if (weight.getId() != null) {
            throw new BadRequestAlertException("A new weight cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Weight result = weightService.save(weight);
        return ResponseEntity
            .created(new URI("/api/weights/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /weights/:id} : Updates an existing weight.
     *
     * @param id the id of the weight to save.
     * @param weight the weight to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated weight,
     * or with status {@code 400 (Bad Request)} if the weight is not valid,
     * or with status {@code 500 (Internal Server Error)} if the weight couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/weights/{id}")
    public ResponseEntity<Weight> updateWeight(@PathVariable(value = "id", required = false) final UUID id, @RequestBody Weight weight)
        throws URISyntaxException {
        log.debug("REST request to update Weight : {}, {}", id, weight);
        if (weight.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, weight.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!weightRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Weight result = weightService.update(weight);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, weight.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /weights/:id} : Partial updates given fields of an existing weight, field will ignore if it is null
     *
     * @param id the id of the weight to save.
     * @param weight the weight to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated weight,
     * or with status {@code 400 (Bad Request)} if the weight is not valid,
     * or with status {@code 404 (Not Found)} if the weight is not found,
     * or with status {@code 500 (Internal Server Error)} if the weight couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/weights/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Weight> partialUpdateWeight(
        @PathVariable(value = "id", required = false) final UUID id,
        @RequestBody Weight weight
    ) throws URISyntaxException {
        log.debug("REST request to partial update Weight partially : {}, {}", id, weight);
        if (weight.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, weight.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!weightRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Weight> result = weightService.partialUpdate(weight);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, weight.getId().toString())
        );
    }

    /**
     * {@code GET  /weights} : get all the weights.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of weights in body.
     */
    @GetMapping("/weights")
    public ResponseEntity<List<Weight>> getAllWeights(
        WeightCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get Weights by criteria: {}", criteria);
        Page<Weight> page = weightQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /weights/count} : count all the weights.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/weights/count")
    public ResponseEntity<Long> countWeights(WeightCriteria criteria) {
        log.debug("REST request to count Weights by criteria: {}", criteria);
        return ResponseEntity.ok().body(weightQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /weights/:id} : get the "id" weight.
     *
     * @param id the id of the weight to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the weight, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/weights/{id}")
    public ResponseEntity<Weight> getWeight(@PathVariable UUID id) {
        log.debug("REST request to get Weight : {}", id);
        Optional<Weight> weight = weightService.findOne(id);
        return ResponseUtil.wrapOrNotFound(weight);
    }

    /**
     * {@code DELETE  /weights/:id} : delete the "id" weight.
     *
     * @param id the id of the weight to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/weights/{id}")
    public ResponseEntity<Void> deleteWeight(@PathVariable UUID id) {
        log.debug("REST request to delete Weight : {}", id);
        weightService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
