package com.be4tech.b4carecollect.service;

import com.be4tech.b4carecollect.domain.*; // for static metamodels
import com.be4tech.b4carecollect.domain.BodyTemperature;
import com.be4tech.b4carecollect.repository.BodyTemperatureRepository;
import com.be4tech.b4carecollect.service.criteria.BodyTemperatureCriteria;
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
 * Service for executing complex queries for {@link BodyTemperature} entities in the database.
 * The main input is a {@link BodyTemperatureCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link BodyTemperature} or a {@link Page} of {@link BodyTemperature} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class BodyTemperatureQueryService extends QueryService<BodyTemperature> {

    private final Logger log = LoggerFactory.getLogger(BodyTemperatureQueryService.class);

    private final BodyTemperatureRepository bodyTemperatureRepository;

    public BodyTemperatureQueryService(BodyTemperatureRepository bodyTemperatureRepository) {
        this.bodyTemperatureRepository = bodyTemperatureRepository;
    }

    /**
     * Return a {@link List} of {@link BodyTemperature} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<BodyTemperature> findByCriteria(BodyTemperatureCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<BodyTemperature> specification = createSpecification(criteria);
        return bodyTemperatureRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link BodyTemperature} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<BodyTemperature> findByCriteria(BodyTemperatureCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<BodyTemperature> specification = createSpecification(criteria);
        return bodyTemperatureRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(BodyTemperatureCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<BodyTemperature> specification = createSpecification(criteria);
        return bodyTemperatureRepository.count(specification);
    }

    /**
     * Function to convert {@link BodyTemperatureCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<BodyTemperature> createSpecification(BodyTemperatureCriteria criteria) {
        Specification<BodyTemperature> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), BodyTemperature_.id));
            }
            if (criteria.getUsuarioId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUsuarioId(), BodyTemperature_.usuarioId));
            }
            if (criteria.getEmpresaId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmpresaId(), BodyTemperature_.empresaId));
            }
            if (criteria.getFieldBodyTemperature() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getFieldBodyTemperature(), BodyTemperature_.fieldBodyTemperature));
            }
            if (criteria.getFieldBodyTemperatureMeasureLocation() != null) {
                specification =
                    specification.and(
                        buildRangeSpecification(
                            criteria.getFieldBodyTemperatureMeasureLocation(),
                            BodyTemperature_.fieldBodyTemperatureMeasureLocation
                        )
                    );
            }
            if (criteria.getEndTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEndTime(), BodyTemperature_.endTime));
            }
        }
        return specification;
    }
}
