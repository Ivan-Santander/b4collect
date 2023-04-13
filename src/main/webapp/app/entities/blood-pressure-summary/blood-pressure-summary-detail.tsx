import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './blood-pressure-summary.reducer';

export const BloodPressureSummaryDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const bloodPressureSummaryEntity = useAppSelector(state => state.bloodPressureSummary.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="bloodPressureSummaryDetailsHeading">
          <Translate contentKey="b4CarecollectApp.bloodPressureSummary.detail.title">BloodPressureSummary</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="b4CarecollectApp.bloodPressureSummary.id">Id</Translate>
            </span>
          </dt>
          <dd>{bloodPressureSummaryEntity.id}</dd>
          <dt>
            <span id="usuarioId">
              <Translate contentKey="b4CarecollectApp.bloodPressureSummary.usuarioId">Usuario Id</Translate>
            </span>
          </dt>
          <dd>{bloodPressureSummaryEntity.usuarioId}</dd>
          <dt>
            <span id="empresaId">
              <Translate contentKey="b4CarecollectApp.bloodPressureSummary.empresaId">Empresa Id</Translate>
            </span>
          </dt>
          <dd>{bloodPressureSummaryEntity.empresaId}</dd>
          <dt>
            <span id="fieldSistolicAverage">
              <Translate contentKey="b4CarecollectApp.bloodPressureSummary.fieldSistolicAverage">Field Sistolic Average</Translate>
            </span>
          </dt>
          <dd>{bloodPressureSummaryEntity.fieldSistolicAverage}</dd>
          <dt>
            <span id="fieldSistolicMax">
              <Translate contentKey="b4CarecollectApp.bloodPressureSummary.fieldSistolicMax">Field Sistolic Max</Translate>
            </span>
          </dt>
          <dd>{bloodPressureSummaryEntity.fieldSistolicMax}</dd>
          <dt>
            <span id="fieldSistolicMin">
              <Translate contentKey="b4CarecollectApp.bloodPressureSummary.fieldSistolicMin">Field Sistolic Min</Translate>
            </span>
          </dt>
          <dd>{bloodPressureSummaryEntity.fieldSistolicMin}</dd>
          <dt>
            <span id="fieldDiasatolicAverage">
              <Translate contentKey="b4CarecollectApp.bloodPressureSummary.fieldDiasatolicAverage">Field Diasatolic Average</Translate>
            </span>
          </dt>
          <dd>{bloodPressureSummaryEntity.fieldDiasatolicAverage}</dd>
          <dt>
            <span id="fieldDiastolicMax">
              <Translate contentKey="b4CarecollectApp.bloodPressureSummary.fieldDiastolicMax">Field Diastolic Max</Translate>
            </span>
          </dt>
          <dd>{bloodPressureSummaryEntity.fieldDiastolicMax}</dd>
          <dt>
            <span id="fieldDiastolicMin">
              <Translate contentKey="b4CarecollectApp.bloodPressureSummary.fieldDiastolicMin">Field Diastolic Min</Translate>
            </span>
          </dt>
          <dd>{bloodPressureSummaryEntity.fieldDiastolicMin}</dd>
          <dt>
            <span id="bodyPosition">
              <Translate contentKey="b4CarecollectApp.bloodPressureSummary.bodyPosition">Body Position</Translate>
            </span>
          </dt>
          <dd>{bloodPressureSummaryEntity.bodyPosition}</dd>
          <dt>
            <span id="measurementLocation">
              <Translate contentKey="b4CarecollectApp.bloodPressureSummary.measurementLocation">Measurement Location</Translate>
            </span>
          </dt>
          <dd>{bloodPressureSummaryEntity.measurementLocation}</dd>
          <dt>
            <span id="startTime">
              <Translate contentKey="b4CarecollectApp.bloodPressureSummary.startTime">Start Time</Translate>
            </span>
          </dt>
          <dd>
            {bloodPressureSummaryEntity.startTime ? (
              <TextFormat value={bloodPressureSummaryEntity.startTime} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="endTime">
              <Translate contentKey="b4CarecollectApp.bloodPressureSummary.endTime">End Time</Translate>
            </span>
          </dt>
          <dd>
            {bloodPressureSummaryEntity.endTime ? (
              <TextFormat value={bloodPressureSummaryEntity.endTime} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
        </dl>
        <Button tag={Link} to="/blood-pressure-summary" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/blood-pressure-summary/${bloodPressureSummaryEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default BloodPressureSummaryDetail;
