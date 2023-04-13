package com.be4tech.b4carecollect.service;

import com.be4tech.b4carecollect.domain.*; // for static metamodels
import com.be4tech.b4carecollect.domain.MentalHealth;
import com.be4tech.b4carecollect.repository.MentalHealthRepository;
import com.be4tech.b4carecollect.service.criteria.MentalHealthCriteria;
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
 * Service for executing complex queries for {@link MentalHealth} entities in the database.
 * The main input is a {@link MentalHealthCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link MentalHealth} or a {@link Page} of {@link MentalHealth} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class MentalHealthQueryService extends QueryService<MentalHealth> {

    private final Logger log = LoggerFactory.getLogger(MentalHealthQueryService.class);

    private final MentalHealthRepository mentalHealthRepository;

    public MentalHealthQueryService(MentalHealthRepository mentalHealthRepository) {
        this.mentalHealthRepository = mentalHealthRepository;
    }

    /**
     * Return a {@link List} of {@link MentalHealth} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<MentalHealth> findByCriteria(MentalHealthCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<MentalHealth> specification = createSpecification(criteria);
        return mentalHealthRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link MentalHealth} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<MentalHealth> findByCriteria(MentalHealthCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<MentalHealth> specification = createSpecification(criteria);
        return mentalHealthRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(MentalHealthCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<MentalHealth> specification = createSpecification(criteria);
        return mentalHealthRepository.count(specification);
    }

    /**
     * Function to convert {@link MentalHealthCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<MentalHealth> createSpecification(MentalHealthCriteria criteria) {
        Specification<MentalHealth> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), MentalHealth_.id));
            }
            if (criteria.getUsuarioId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUsuarioId(), MentalHealth_.usuarioId));
            }
            if (criteria.getEmpresaId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmpresaId(), MentalHealth_.empresaId));
            }
            if (criteria.getEmotionDescription() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getEmotionDescription(), MentalHealth_.emotionDescription));
            }
            if (criteria.getEmotionValue() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEmotionValue(), MentalHealth_.emotionValue));
            }
            if (criteria.getStartDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getStartDate(), MentalHealth_.startDate));
            }
            if (criteria.getEndDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEndDate(), MentalHealth_.endDate));
            }
            if (criteria.getMentalHealthScore() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getMentalHealthScore(), MentalHealth_.mentalHealthScore));
            }
        }
        return specification;
    }
}
