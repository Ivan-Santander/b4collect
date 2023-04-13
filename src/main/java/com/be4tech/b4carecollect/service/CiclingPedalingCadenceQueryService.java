package com.be4tech.b4carecollect.service;

import com.be4tech.b4carecollect.domain.*; // for static metamodels
import com.be4tech.b4carecollect.domain.CiclingPedalingCadence;
import com.be4tech.b4carecollect.repository.CiclingPedalingCadenceRepository;
import com.be4tech.b4carecollect.service.criteria.CiclingPedalingCadenceCriteria;
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
 * Service for executing complex queries for {@link CiclingPedalingCadence} entities in the database.
 * The main input is a {@link CiclingPedalingCadenceCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CiclingPedalingCadence} or a {@link Page} of {@link CiclingPedalingCadence} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CiclingPedalingCadenceQueryService extends QueryService<CiclingPedalingCadence> {

    private final Logger log = LoggerFactory.getLogger(CiclingPedalingCadenceQueryService.class);

    private final CiclingPedalingCadenceRepository ciclingPedalingCadenceRepository;

    public CiclingPedalingCadenceQueryService(CiclingPedalingCadenceRepository ciclingPedalingCadenceRepository) {
        this.ciclingPedalingCadenceRepository = ciclingPedalingCadenceRepository;
    }

    /**
     * Return a {@link List} of {@link CiclingPedalingCadence} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CiclingPedalingCadence> findByCriteria(CiclingPedalingCadenceCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CiclingPedalingCadence> specification = createSpecification(criteria);
        return ciclingPedalingCadenceRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link CiclingPedalingCadence} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CiclingPedalingCadence> findByCriteria(CiclingPedalingCadenceCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CiclingPedalingCadence> specification = createSpecification(criteria);
        return ciclingPedalingCadenceRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CiclingPedalingCadenceCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CiclingPedalingCadence> specification = createSpecification(criteria);
        return ciclingPedalingCadenceRepository.count(specification);
    }

    /**
     * Function to convert {@link CiclingPedalingCadenceCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<CiclingPedalingCadence> createSpecification(CiclingPedalingCadenceCriteria criteria) {
        Specification<CiclingPedalingCadence> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), CiclingPedalingCadence_.id));
            }
            if (criteria.getUsuarioId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUsuarioId(), CiclingPedalingCadence_.usuarioId));
            }
            if (criteria.getEmpresaId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmpresaId(), CiclingPedalingCadence_.empresaId));
            }
            if (criteria.getRpm() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getRpm(), CiclingPedalingCadence_.rpm));
            }
            if (criteria.getStartTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getStartTime(), CiclingPedalingCadence_.startTime));
            }
            if (criteria.getEndTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEndTime(), CiclingPedalingCadence_.endTime));
            }
        }
        return specification;
    }
}
