package com.be4tech.b4carecollect.service;

import com.be4tech.b4carecollect.domain.*; // for static metamodels
import com.be4tech.b4carecollect.domain.Speed;
import com.be4tech.b4carecollect.repository.SpeedRepository;
import com.be4tech.b4carecollect.service.criteria.SpeedCriteria;
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
 * Service for executing complex queries for {@link Speed} entities in the database.
 * The main input is a {@link SpeedCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Speed} or a {@link Page} of {@link Speed} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class SpeedQueryService extends QueryService<Speed> {

    private final Logger log = LoggerFactory.getLogger(SpeedQueryService.class);

    private final SpeedRepository speedRepository;

    public SpeedQueryService(SpeedRepository speedRepository) {
        this.speedRepository = speedRepository;
    }

    /**
     * Return a {@link List} of {@link Speed} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Speed> findByCriteria(SpeedCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Speed> specification = createSpecification(criteria);
        return speedRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Speed} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Speed> findByCriteria(SpeedCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Speed> specification = createSpecification(criteria);
        return speedRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(SpeedCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Speed> specification = createSpecification(criteria);
        return speedRepository.count(specification);
    }

    /**
     * Function to convert {@link SpeedCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Speed> createSpecification(SpeedCriteria criteria) {
        Specification<Speed> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Speed_.id));
            }
            if (criteria.getUsuarioId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUsuarioId(), Speed_.usuarioId));
            }
            if (criteria.getEmpresaId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmpresaId(), Speed_.empresaId));
            }
            if (criteria.getSpeed() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSpeed(), Speed_.speed));
            }
            if (criteria.getEndTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEndTime(), Speed_.endTime));
            }
        }
        return specification;
    }
}
