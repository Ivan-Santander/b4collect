package com.be4tech.b4carecollect.service;

import com.be4tech.b4carecollect.domain.*; // for static metamodels
import com.be4tech.b4carecollect.domain.Weight;
import com.be4tech.b4carecollect.repository.WeightRepository;
import com.be4tech.b4carecollect.service.criteria.WeightCriteria;
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
 * Service for executing complex queries for {@link Weight} entities in the database.
 * The main input is a {@link WeightCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Weight} or a {@link Page} of {@link Weight} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class WeightQueryService extends QueryService<Weight> {

    private final Logger log = LoggerFactory.getLogger(WeightQueryService.class);

    private final WeightRepository weightRepository;

    public WeightQueryService(WeightRepository weightRepository) {
        this.weightRepository = weightRepository;
    }

    /**
     * Return a {@link List} of {@link Weight} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Weight> findByCriteria(WeightCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Weight> specification = createSpecification(criteria);
        return weightRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Weight} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Weight> findByCriteria(WeightCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Weight> specification = createSpecification(criteria);
        return weightRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(WeightCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Weight> specification = createSpecification(criteria);
        return weightRepository.count(specification);
    }

    /**
     * Function to convert {@link WeightCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Weight> createSpecification(WeightCriteria criteria) {
        Specification<Weight> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Weight_.id));
            }
            if (criteria.getUsuarioId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUsuarioId(), Weight_.usuarioId));
            }
            if (criteria.getEmpresaId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmpresaId(), Weight_.empresaId));
            }
            if (criteria.getFieldWeight() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFieldWeight(), Weight_.fieldWeight));
            }
            if (criteria.getEndTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEndTime(), Weight_.endTime));
            }
        }
        return specification;
    }
}
