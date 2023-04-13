package com.be4tech.b4carecollect.service;

import com.be4tech.b4carecollect.domain.*; // for static metamodels
import com.be4tech.b4carecollect.domain.BodyFatPercentage;
import com.be4tech.b4carecollect.repository.BodyFatPercentageRepository;
import com.be4tech.b4carecollect.service.criteria.BodyFatPercentageCriteria;
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
 * Service for executing complex queries for {@link BodyFatPercentage} entities in the database.
 * The main input is a {@link BodyFatPercentageCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link BodyFatPercentage} or a {@link Page} of {@link BodyFatPercentage} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class BodyFatPercentageQueryService extends QueryService<BodyFatPercentage> {

    private final Logger log = LoggerFactory.getLogger(BodyFatPercentageQueryService.class);

    private final BodyFatPercentageRepository bodyFatPercentageRepository;

    public BodyFatPercentageQueryService(BodyFatPercentageRepository bodyFatPercentageRepository) {
        this.bodyFatPercentageRepository = bodyFatPercentageRepository;
    }

    /**
     * Return a {@link List} of {@link BodyFatPercentage} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<BodyFatPercentage> findByCriteria(BodyFatPercentageCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<BodyFatPercentage> specification = createSpecification(criteria);
        return bodyFatPercentageRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link BodyFatPercentage} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<BodyFatPercentage> findByCriteria(BodyFatPercentageCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<BodyFatPercentage> specification = createSpecification(criteria);
        return bodyFatPercentageRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(BodyFatPercentageCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<BodyFatPercentage> specification = createSpecification(criteria);
        return bodyFatPercentageRepository.count(specification);
    }

    /**
     * Function to convert {@link BodyFatPercentageCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<BodyFatPercentage> createSpecification(BodyFatPercentageCriteria criteria) {
        Specification<BodyFatPercentage> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), BodyFatPercentage_.id));
            }
            if (criteria.getUsuarioId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUsuarioId(), BodyFatPercentage_.usuarioId));
            }
            if (criteria.getEmpresaId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmpresaId(), BodyFatPercentage_.empresaId));
            }
            if (criteria.getFieldPorcentage() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getFieldPorcentage(), BodyFatPercentage_.fieldPorcentage));
            }
            if (criteria.getEndTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEndTime(), BodyFatPercentage_.endTime));
            }
        }
        return specification;
    }
}
