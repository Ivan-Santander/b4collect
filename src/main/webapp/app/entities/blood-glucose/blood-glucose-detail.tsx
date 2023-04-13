import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './blood-glucose.reducer';

export const BloodGlucoseDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const bloodGlucoseEntity = useAppSelector(state => state.bloodGlucose.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="bloodGlucoseDetailsHeading">
          <Translate contentKey="b4CarecollectApp.bloodGlucose.detail.title">BloodGlucose</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="b4CarecollectApp.bloodGlucose.id">Id</Translate>
            </span>
          </dt>
          <dd>{bloodGlucoseEntity.id}</dd>
          <dt>
            <span id="usuarioId">
              <Translate contentKey="b4CarecollectApp.bloodGlucose.usuarioId">Usuario Id</Translate>
            </span>
          </dt>
          <dd>{bloodGlucoseEntity.usuarioId}</dd>
          <dt>
            <span id="empresaId">
              <Translate contentKey="b4CarecollectApp.bloodGlucose.empresaId">Empresa Id</Translate>
            </span>
          </dt>
          <dd>{bloodGlucoseEntity.empresaId}</dd>
          <dt>
            <span id="fieldBloodGlucoseLevel">
              <Translate contentKey="b4CarecollectApp.bloodGlucose.fieldBloodGlucoseLevel">Field Blood Glucose Level</Translate>
            </span>
          </dt>
          <dd>{bloodGlucoseEntity.fieldBloodGlucoseLevel}</dd>
          <dt>
            <span id="fieldTemporalRelationToMeal">
              <Translate contentKey="b4CarecollectApp.bloodGlucose.fieldTemporalRelationToMeal">Field Temporal Relation To Meal</Translate>
            </span>
          </dt>
          <dd>{bloodGlucoseEntity.fieldTemporalRelationToMeal}</dd>
          <dt>
            <span id="fieldMealType">
              <Translate contentKey="b4CarecollectApp.bloodGlucose.fieldMealType">Field Meal Type</Translate>
            </span>
          </dt>
          <dd>{bloodGlucoseEntity.fieldMealType}</dd>
          <dt>
            <span id="fieldTemporalRelationToSleep">
              <Translate contentKey="b4CarecollectApp.bloodGlucose.fieldTemporalRelationToSleep">
                Field Temporal Relation To Sleep
              </Translate>
            </span>
          </dt>
          <dd>{bloodGlucoseEntity.fieldTemporalRelationToSleep}</dd>
          <dt>
            <span id="fieldBloodGlucoseSpecimenSource">
              <Translate contentKey="b4CarecollectApp.bloodGlucose.fieldBloodGlucoseSpecimenSource">
                Field Blood Glucose Specimen Source
              </Translate>
            </span>
          </dt>
          <dd>{bloodGlucoseEntity.fieldBloodGlucoseSpecimenSource}</dd>
          <dt>
            <span id="endTime">
              <Translate contentKey="b4CarecollectApp.bloodGlucose.endTime">End Time</Translate>
            </span>
          </dt>
          <dd>
            {bloodGlucoseEntity.endTime ? <TextFormat value={bloodGlucoseEntity.endTime} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
        </dl>
        <Button tag={Link} to="/blood-glucose" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/blood-glucose/${bloodGlucoseEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default BloodGlucoseDetail;
