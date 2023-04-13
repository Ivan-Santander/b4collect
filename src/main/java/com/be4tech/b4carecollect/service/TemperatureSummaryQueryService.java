package com.be4tech.b4carecollect.service;

import com.be4tech.b4carecollect.domain.*; // for static metamodels
import com.be4tech.b4carecollect.domain.TemperatureSummary;
import com.be4tech.b4carecollect.repository.TemperatureSummaryRepository;
import com.be4tech.b4carecollect.service.criteria.TemperatureSummaryCriteria;
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
 * Service for executing complex queries for {@link TemperatureSummary} entities in the database.
 * The main input is a {@link TemperatureSummaryCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link TemperatureSummary} or a {@link Page} of {@link TemperatureSummary} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class TemperatureSummaryQueryService extends QueryService<TemperatureSummary> {

    private final Logger log = LoggerFactory.getLogger(TemperatureSummaryQueryService.class);

    private final TemperatureSummaryRepository temperatureSummaryRepository;

    public TemperatureSummaryQueryService(TemperatureSummaryRepository temperatureSummaryRepository) {
        this.temperatureSummaryRepository = temperatureSummaryRepository;
    }

    /**
     * Return a {@link List} of {@link TemperatureSummary} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<TemperatureSummary> findByCriteria(TemperatureSummaryCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<TemperatureSummary> specification = createSpecification(criteria);
        return temperatureSummaryRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link TemperatureSummary} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<TemperatureSummary> findByCriteria(TemperatureSummaryCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<TemperatureSummary> specification = createSpecification(criteria);
        return temperatureSummaryRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(TemperatureSummaryCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<TemperatureSummary> specification = createSpecification(criteria);
        return temperatureSummaryRepository.count(specification);
    }

    /**
     * Function to convert {@link TemperatureSummaryCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<TemperatureSummary> createSpecification(TemperatureSummaryCriteria criteria) {
        Specification<TemperatureSummary> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), TemperatureSummary_.id));
            }
            if (criteria.getUsuarioId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUsuarioId(), TemperatureSummary_.usuarioId));
            }
            if (criteria.getEmpresaId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmpresaId(), TemperatureSummary_.empresaId));
            }
            if (criteria.getFieldAverage() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFieldAverage(), TemperatureSummary_.fieldAverage));
            }
            if (criteria.getFieldMax() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFieldMax(), TemperatureSummary_.fieldMax));
            }
            if (criteria.getFieldMin() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFieldMin(), TemperatureSummary_.fieldMin));
            }
            if (criteria.getMeasurementLocation() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getMeasurementLocation(), TemperatureSummary_.measurementLocation));
            }
            if (criteria.getStartTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getStartTime(), TemperatureSummary_.startTime));
            }
            if (criteria.getEndTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEndTime(), TemperatureSummary_.endTime));
            }
        }
        return specification;
    }
}
