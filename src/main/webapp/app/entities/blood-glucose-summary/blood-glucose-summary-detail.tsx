import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, byteSize, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './blood-glucose-summary.reducer';

export const BloodGlucoseSummaryDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const bloodGlucoseSummaryEntity = useAppSelector(state => state.bloodGlucoseSummary.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="bloodGlucoseSummaryDetailsHeading">
          <Translate contentKey="b4CarecollectApp.bloodGlucoseSummary.detail.title">BloodGlucoseSummary</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="b4CarecollectApp.bloodGlucoseSummary.id">Id</Translate>
            </span>
          </dt>
          <dd>{bloodGlucoseSummaryEntity.id}</dd>
          <dt>
            <span id="usuarioId">
              <Translate contentKey="b4CarecollectApp.bloodGlucoseSummary.usuarioId">Usuario Id</Translate>
            </span>
          </dt>
          <dd>{bloodGlucoseSummaryEntity.usuarioId}</dd>
          <dt>
            <span id="empresaId">
              <Translate contentKey="b4CarecollectApp.bloodGlucoseSummary.empresaId">Empresa Id</Translate>
            </span>
          </dt>
          <dd>{bloodGlucoseSummaryEntity.empresaId}</dd>
          <dt>
            <span id="fieldAverage">
              <Translate contentKey="b4CarecollectApp.bloodGlucoseSummary.fieldAverage">Field Average</Translate>
            </span>
          </dt>
          <dd>{bloodGlucoseSummaryEntity.fieldAverage}</dd>
          <dt>
            <span id="fieldMax">
              <Translate contentKey="b4CarecollectApp.bloodGlucoseSummary.fieldMax">Field Max</Translate>
            </span>
          </dt>
          <dd>{bloodGlucoseSummaryEntity.fieldMax}</dd>
          <dt>
            <span id="fieldMin">
              <Translate contentKey="b4CarecollectApp.bloodGlucoseSummary.fieldMin">Field Min</Translate>
            </span>
          </dt>
          <dd>{bloodGlucoseSummaryEntity.fieldMin}</dd>
          <dt>
            <span id="intervalFood">
              <Translate contentKey="b4CarecollectApp.bloodGlucoseSummary.intervalFood">Interval Food</Translate>
            </span>
          </dt>
          <dd>{bloodGlucoseSummaryEntity.intervalFood}</dd>
          <dt>
            <span id="mealType">
              <Translate contentKey="b4CarecollectApp.bloodGlucoseSummary.mealType">Meal Type</Translate>
            </span>
          </dt>
          <dd>{bloodGlucoseSummaryEntity.mealType}</dd>
          <dt>
            <span id="relationTemporalSleep">
              <Translate contentKey="b4CarecollectApp.bloodGlucoseSummary.relationTemporalSleep">Relation Temporal Sleep</Translate>
            </span>
          </dt>
          <dd>{bloodGlucoseSummaryEntity.relationTemporalSleep}</dd>
          <dt>
            <span id="sampleSource">
              <Translate contentKey="b4CarecollectApp.bloodGlucoseSummary.sampleSource">Sample Source</Translate>
            </span>
          </dt>
          <dd>{bloodGlucoseSummaryEntity.sampleSource}</dd>
          <dt>
            <span id="startTime">
              <Translate contentKey="b4CarecollectApp.bloodGlucoseSummary.startTime">Start Time</Translate>
            </span>
          </dt>
          <dd>
            {bloodGlucoseSummaryEntity.startTime ? (
              <TextFormat value={bloodGlucoseSummaryEntity.startTime} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="endTime">
              <Translate contentKey="b4CarecollectApp.bloodGlucoseSummary.endTime">End Time</Translate>
            </span>
          </dt>
          <dd>
            {bloodGlucoseSummaryEntity.endTime ? (
              <TextFormat value={bloodGlucoseSummaryEntity.endTime} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
        </dl>
        <Button tag={Link} to="/blood-glucose-summary" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/blood-glucose-summary/${bloodGlucoseSummaryEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default BloodGlucoseSummaryDetail;
