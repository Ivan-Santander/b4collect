package com.be4tech.b4carecollect.service;

import com.be4tech.b4carecollect.domain.*; // for static metamodels
import com.be4tech.b4carecollect.domain.UserDemographics;
import com.be4tech.b4carecollect.repository.UserDemographicsRepository;
import com.be4tech.b4carecollect.service.criteria.UserDemographicsCriteria;
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
 * Service for executing complex queries for {@link UserDemographics} entities in the database.
 * The main input is a {@link UserDemographicsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link UserDemographics} or a {@link Page} of {@link UserDemographics} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class UserDemographicsQueryService extends QueryService<UserDemographics> {

    private final Logger log = LoggerFactory.getLogger(UserDemographicsQueryService.class);

    private final UserDemographicsRepository userDemographicsRepository;

    public UserDemographicsQueryService(UserDemographicsRepository userDemographicsRepository) {
        this.userDemographicsRepository = userDemographicsRepository;
    }

    /**
     * Return a {@link List} of {@link UserDemographics} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<UserDemographics> findByCriteria(UserDemographicsCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<UserDemographics> specification = createSpecification(criteria);
        return userDemographicsRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link UserDemographics} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<UserDemographics> findByCriteria(UserDemographicsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<UserDemographics> specification = createSpecification(criteria);
        return userDemographicsRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(UserDemographicsCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<UserDemographics> specification = createSpecification(criteria);
        return userDemographicsRepository.count(specification);
    }

    /**
     * Function to convert {@link UserDemographicsCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<UserDemographics> createSpecification(UserDemographicsCriteria criteria) {
        Specification<UserDemographics> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), UserDemographics_.id));
            }
            if (criteria.getUsuarioId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUsuarioId(), UserDemographics_.usuarioId));
            }
            if (criteria.getEmpresaId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmpresaId(), UserDemographics_.empresaId));
            }
            if (criteria.getGender() != null) {
                specification = specification.and(buildStringSpecification(criteria.getGender(), UserDemographics_.gender));
            }
            if (criteria.getDateOfBird() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDateOfBird(), UserDemographics_.dateOfBird));
            }
            if (criteria.getAge() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAge(), UserDemographics_.age));
            }
            if (criteria.getCountry() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCountry(), UserDemographics_.country));
            }
            if (criteria.getState() != null) {
                specification = specification.and(buildStringSpecification(criteria.getState(), UserDemographics_.state));
            }
            if (criteria.getCity() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCity(), UserDemographics_.city));
            }
            if (criteria.getEthnicity() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEthnicity(), UserDemographics_.ethnicity));
            }
            if (criteria.getIncome() != null) {
                specification = specification.and(buildStringSpecification(criteria.getIncome(), UserDemographics_.income));
            }
            if (criteria.getMaritalStatus() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMaritalStatus(), UserDemographics_.maritalStatus));
            }
            if (criteria.getEducation() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEducation(), UserDemographics_.education));
            }
            if (criteria.getEndTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEndTime(), UserDemographics_.endTime));
            }
        }
        return specification;
    }
}
