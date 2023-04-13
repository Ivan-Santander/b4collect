import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './blood-pressure.reducer';

export const BloodPressureDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const bloodPressureEntity = useAppSelector(state => state.bloodPressure.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="bloodPressureDetailsHeading">
          <Translate contentKey="b4CarecollectApp.bloodPressure.detail.title">BloodPressure</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="b4CarecollectApp.bloodPressure.id">Id</Translate>
            </span>
          </dt>
          <dd>{bloodPressureEntity.id}</dd>
          <dt>
            <span id="usuarioId">
              <Translate contentKey="b4CarecollectApp.bloodPressure.usuarioId">Usuario Id</Translate>
            </span>
          </dt>
          <dd>{bloodPressureEntity.usuarioId}</dd>
          <dt>
            <span id="empresaId">
              <Translate contentKey="b4CarecollectApp.bloodPressure.empresaId">Empresa Id</Translate>
            </span>
          </dt>
          <dd>{bloodPressureEntity.empresaId}</dd>
          <dt>
            <span id="fieldBloodPressureSystolic">
              <Translate contentKey="b4CarecollectApp.bloodPressure.fieldBloodPressureSystolic">Field Blood Pressure Systolic</Translate>
            </span>
          </dt>
          <dd>{bloodPressureEntity.fieldBloodPressureSystolic}</dd>
          <dt>
            <span id="fieldBloodPressureDiastolic">
              <Translate contentKey="b4CarecollectApp.bloodPressure.fieldBloodPressureDiastolic">Field Blood Pressure Diastolic</Translate>
            </span>
          </dt>
          <dd>{bloodPressureEntity.fieldBloodPressureDiastolic}</dd>
          <dt>
            <span id="fieldBodyPosition">
              <Translate contentKey="b4CarecollectApp.bloodPressure.fieldBodyPosition">Field Body Position</Translate>
            </span>
          </dt>
          <dd>{bloodPressureEntity.fieldBodyPosition}</dd>
          <dt>
            <span id="fieldBloodPressureMeasureLocation">
              <Translate contentKey="b4CarecollectApp.bloodPressure.fieldBloodPressureMeasureLocation">
                Field Blood Pressure Measure Location
              </Translate>
            </span>
          </dt>
          <dd>{bloodPressureEntity.fieldBloodPressureMeasureLocation}</dd>
          <dt>
            <span id="endTime">
              <Translate contentKey="b4CarecollectApp.bloodPressure.endTime">End Time</Translate>
            </span>
          </dt>
          <dd>
            {bloodPressureEntity.endTime ? <TextFormat value={bloodPressureEntity.endTime} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
        </dl>
        <Button tag={Link} to="/blood-pressure" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/blood-pressure/${bloodPressureEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default BloodPressureDetail;
