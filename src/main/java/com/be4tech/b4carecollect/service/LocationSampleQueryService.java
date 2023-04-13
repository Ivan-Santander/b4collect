package com.be4tech.b4carecollect.service;

import com.be4tech.b4carecollect.domain.*; // for static metamodels
import com.be4tech.b4carecollect.domain.LocationSample;
import com.be4tech.b4carecollect.repository.LocationSampleRepository;
import com.be4tech.b4carecollect.service.criteria.LocationSampleCriteria;
import java.util.List;
import javax.persistence.criteria.JoinType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link LocationSample} entities in the database.
 * The main input is a {@link LocationSampleCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link LocationSample} or a {@link Page} of {@link LocationSample} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class LocationSampleQueryService extends QueryService<LocationSample> {

    private final Logger log = LoggerFactory.getLogger(LocationSampleQueryService.class);

    private final LocationSampleRepository locationSampleRepository;

    public LocationSampleQueryService(LocationSampleRepository locationSampleRepository) {
        this.locationSampleRepository = locationSampleRepository;
    }

    /**
     * Return a {@link List} of {@link LocationSample} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<LocationSample> findByCriteria(LocationSampleCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<LocationSample> specification = createSpecification(criteria);
        return locationSampleRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link LocationSample} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<LocationSample> findByCriteria(LocationSampleCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<LocationSample> specification = createSpecification(criteria);
        return locationSampleRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(LocationSampleCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<LocationSample> specification = createSpecification(criteria);
        return locationSampleRepository.count(specification);
    }

    /**
     * Function to convert {@link LocationSampleCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<LocationSample> createSpecification(LocationSampleCriteria criteria) {
        Specification<LocationSample> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), LocationSample_.id));
            }
            if (criteria.getUsuarioId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUsuarioId(), LocationSample_.usuarioId));
            }
            if (criteria.getEmpresaId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmpresaId(), LocationSample_.empresaId));
            }
            if (criteria.getLatitudMin() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLatitudMin(), LocationSample_.latitudMin));
            }
            if (criteria.getLongitudMin() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLongitudMin(), LocationSample_.longitudMin));
            }
            if (criteria.getLatitudMax() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLatitudMax(), LocationSample_.latitudMax));
            }
            if (criteria.getLongitudMax() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLongitudMax(), LocationSample_.longitudMax));
            }
            if (criteria.getAccuracy() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAccuracy(), LocationSample_.accuracy));
            }
            if (criteria.getAltitud() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAltitud(), LocationSample_.altitud));
            }
            if (criteria.getStartTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getStartTime(), LocationSample_.startTime));
            }
            if (criteria.getEndTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEndTime(), LocationSample_.endTime));
            }
        }
        return specification;
    }
}
