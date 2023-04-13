package com.be4tech.b4carecollect.service;

import com.be4tech.b4carecollect.domain.*; // for static metamodels
import com.be4tech.b4carecollect.domain.FuntionalIndex;
import com.be4tech.b4carecollect.repository.FuntionalIndexRepository;
import com.be4tech.b4carecollect.service.criteria.FuntionalIndexCriteria;
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
 * Service for executing complex queries for {@link FuntionalIndex} entities in the database.
 * The main input is a {@link FuntionalIndexCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link FuntionalIndex} or a {@link Page} of {@link FuntionalIndex} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class FuntionalIndexQueryService extends QueryService<FuntionalIndex> {

    private final Logger log = LoggerFactory.getLogger(FuntionalIndexQueryService.class);

    private final FuntionalIndexRepository funtionalIndexRepository;

    public FuntionalIndexQueryService(FuntionalIndexRepository funtionalIndexRepository) {
        this.funtionalIndexRepository = funtionalIndexRepository;
    }

    /**
     * Return a {@link List} of {@link FuntionalIndex} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<FuntionalIndex> findByCriteria(FuntionalIndexCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<FuntionalIndex> specification = createSpecification(criteria);
        return funtionalIndexRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link FuntionalIndex} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<FuntionalIndex> findByCriteria(FuntionalIndexCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<FuntionalIndex> specification = createSpecification(criteria);
        return funtionalIndexRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(FuntionalIndexCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<FuntionalIndex> specification = createSpecification(criteria);
        return funtionalIndexRepository.count(specification);
    }

    /**
     * Function to convert {@link FuntionalIndexCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<FuntionalIndex> createSpecification(FuntionalIndexCriteria criteria) {
        Specification<FuntionalIndex> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), FuntionalIndex_.id));
            }
            if (criteria.getUsuarioId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUsuarioId(), FuntionalIndex_.usuarioId));
            }
            if (criteria.getEmpresaId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmpresaId(), FuntionalIndex_.empresaId));
            }
            if (criteria.getBodyHealthScore() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getBodyHealthScore(), FuntionalIndex_.bodyHealthScore));
            }
            if (criteria.getMentalHealthScore() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getMentalHealthScore(), FuntionalIndex_.mentalHealthScore));
            }
            if (criteria.getSleepHealthScore() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getSleepHealthScore(), FuntionalIndex_.sleepHealthScore));
            }
            if (criteria.getFuntionalIndex() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFuntionalIndex(), FuntionalIndex_.funtionalIndex));
            }
            if (criteria.getAlarmRiskScore() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAlarmRiskScore(), FuntionalIndex_.alarmRiskScore));
            }
            if (criteria.getStartTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getStartTime(), FuntionalIndex_.startTime));
            }
        }
        return specification;
    }
}
