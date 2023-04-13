package com.be4tech.b4carecollect.service;

import com.be4tech.b4carecollect.domain.*; // for static metamodels
import com.be4tech.b4carecollect.domain.Nutrition;
import com.be4tech.b4carecollect.repository.NutritionRepository;
import com.be4tech.b4carecollect.service.criteria.NutritionCriteria;
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
 * Service for executing complex queries for {@link Nutrition} entities in the database.
 * The main input is a {@link NutritionCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Nutrition} or a {@link Page} of {@link Nutrition} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class NutritionQueryService extends QueryService<Nutrition> {

    private final Logger log = LoggerFactory.getLogger(NutritionQueryService.class);

    private final NutritionRepository nutritionRepository;

    public NutritionQueryService(NutritionRepository nutritionRepository) {
        this.nutritionRepository = nutritionRepository;
    }

    /**
     * Return a {@link List} of {@link Nutrition} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Nutrition> findByCriteria(NutritionCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Nutrition> specification = createSpecification(criteria);
        return nutritionRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Nutrition} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Nutrition> findByCriteria(NutritionCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Nutrition> specification = createSpecification(criteria);
        return nutritionRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(NutritionCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Nutrition> specification = createSpecification(criteria);
        return nutritionRepository.count(specification);
    }

    /**
     * Function to convert {@link NutritionCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Nutrition> createSpecification(NutritionCriteria criteria) {
        Specification<Nutrition> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Nutrition_.id));
            }
            if (criteria.getUsuarioId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUsuarioId(), Nutrition_.usuarioId));
            }
            if (criteria.getEmpresaId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmpresaId(), Nutrition_.empresaId));
            }
            if (criteria.getMealType() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getMealType(), Nutrition_.mealType));
            }
            if (criteria.getEndTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEndTime(), Nutrition_.endTime));
            }
        }
        return specification;
    }
}
