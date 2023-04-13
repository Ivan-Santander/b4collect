package com.be4tech.b4carecollect.service;

import com.be4tech.b4carecollect.domain.*; // for static metamodels
import com.be4tech.b4carecollect.domain.UserMedicalInfo;
import com.be4tech.b4carecollect.repository.UserMedicalInfoRepository;
import com.be4tech.b4carecollect.service.criteria.UserMedicalInfoCriteria;
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
 * Service for executing complex queries for {@link UserMedicalInfo} entities in the database.
 * The main input is a {@link UserMedicalInfoCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link UserMedicalInfo} or a {@link Page} of {@link UserMedicalInfo} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class UserMedicalInfoQueryService extends QueryService<UserMedicalInfo> {

    private final Logger log = LoggerFactory.getLogger(UserMedicalInfoQueryService.class);

    private final UserMedicalInfoRepository userMedicalInfoRepository;

    public UserMedicalInfoQueryService(UserMedicalInfoRepository userMedicalInfoRepository) {
        this.userMedicalInfoRepository = userMedicalInfoRepository;
    }

    /**
     * Return a {@link List} of {@link UserMedicalInfo} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<UserMedicalInfo> findByCriteria(UserMedicalInfoCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<UserMedicalInfo> specification = createSpecification(criteria);
        return userMedicalInfoRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link UserMedicalInfo} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<UserMedicalInfo> findByCriteria(UserMedicalInfoCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<UserMedicalInfo> specification = createSpecification(criteria);
        return userMedicalInfoRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(UserMedicalInfoCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<UserMedicalInfo> specification = createSpecification(criteria);
        return userMedicalInfoRepository.count(specification);
    }

    /**
     * Function to convert {@link UserMedicalInfoCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<UserMedicalInfo> createSpecification(UserMedicalInfoCriteria criteria) {
        Specification<UserMedicalInfo> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), UserMedicalInfo_.id));
            }
            if (criteria.getUsuarioId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUsuarioId(), UserMedicalInfo_.usuarioId));
            }
            if (criteria.getEmpresaId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmpresaId(), UserMedicalInfo_.empresaId));
            }
            if (criteria.getHypertension() != null) {
                specification = specification.and(buildSpecification(criteria.getHypertension(), UserMedicalInfo_.hypertension));
            }
            if (criteria.getHighGlucose() != null) {
                specification = specification.and(buildSpecification(criteria.getHighGlucose(), UserMedicalInfo_.highGlucose));
            }
            if (criteria.getHiabetes() != null) {
                specification = specification.and(buildSpecification(criteria.getHiabetes(), UserMedicalInfo_.hiabetes));
            }
            if (criteria.getTotalCholesterol() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getTotalCholesterol(), UserMedicalInfo_.totalCholesterol));
            }
            if (criteria.getHdlCholesterol() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getHdlCholesterol(), UserMedicalInfo_.hdlCholesterol));
            }
            if (criteria.getMedicalMainCondition() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getMedicalMainCondition(), UserMedicalInfo_.medicalMainCondition));
            }
            if (criteria.getMedicalSecundaryCondition() != null) {
                specification =
                    specification.and(
                        buildStringSpecification(criteria.getMedicalSecundaryCondition(), UserMedicalInfo_.medicalSecundaryCondition)
                    );
            }
            if (criteria.getMedicalMainMedication() != null) {
                specification =
                    specification.and(
                        buildStringSpecification(criteria.getMedicalMainMedication(), UserMedicalInfo_.medicalMainMedication)
                    );
            }
            if (criteria.getMedicalSecundaryMedication() != null) {
                specification =
                    specification.and(
                        buildStringSpecification(criteria.getMedicalSecundaryMedication(), UserMedicalInfo_.medicalSecundaryMedication)
                    );
            }
            if (criteria.getMedicalScore() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getMedicalScore(), UserMedicalInfo_.medicalScore));
            }
            if (criteria.getEndTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEndTime(), UserMedicalInfo_.endTime));
            }
        }
        return specification;
    }
}
