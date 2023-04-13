package com.be4tech.b4carecollect.service;

import com.be4tech.b4carecollect.domain.*; // for static metamodels
import com.be4tech.b4carecollect.domain.Height;
import com.be4tech.b4carecollect.repository.HeightRepository;
import com.be4tech.b4carecollect.service.criteria.HeightCriteria;
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
 * Service for executing complex queries for {@link Height} entities in the database.
 * The main input is a {@link HeightCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Height} or a {@link Page} of {@link Height} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class HeightQueryService extends QueryService<Height> {

    private final Logger log = LoggerFactory.getLogger(HeightQueryService.class);

    private final HeightRepository heightRepository;

    public HeightQueryService(HeightRepository heightRepository) {
        this.heightRepository = heightRepository;
    }

    /**
     * Return a {@link List} of {@link Height} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Height> findByCriteria(HeightCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Height> specification = createSpecification(criteria);
        return heightRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Height} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Height> findByCriteria(HeightCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Height> specification = createSpecification(criteria);
        return heightRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(HeightCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Height> specification = createSpecification(criteria);
        return heightRepository.count(specification);
    }

    /**
     * Function to convert {@link HeightCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Height> createSpecification(HeightCriteria criteria) {
        Specification<Height> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Height_.id));
            }
            if (criteria.getUsuarioId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUsuarioId(), Height_.usuarioId));
            }
            if (criteria.getEmpresaId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmpresaId(), Height_.empresaId));
            }
            if (criteria.getFieldHeight() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFieldHeight(), Height_.fieldHeight));
            }
            if (criteria.getEndTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEndTime(), Height_.endTime));
            }
        }
        return specification;
    }
}
