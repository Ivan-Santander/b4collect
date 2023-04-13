import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './activity-summary.reducer';

export const ActivitySummaryDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const activitySummaryEntity = useAppSelector(state => state.activitySummary.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="activitySummaryDetailsHeading">
          <Translate contentKey="b4CarecollectApp.activitySummary.detail.title">ActivitySummary</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="b4CarecollectApp.activitySummary.id">Id</Translate>
            </span>
          </dt>
          <dd>{activitySummaryEntity.id}</dd>
          <dt>
            <span id="usuarioId">
              <Translate contentKey="b4CarecollectApp.activitySummary.usuarioId">Usuario Id</Translate>
            </span>
          </dt>
          <dd>{activitySummaryEntity.usuarioId}</dd>
          <dt>
            <span id="empresaId">
              <Translate contentKey="b4CarecollectApp.activitySummary.empresaId">Empresa Id</Translate>
            </span>
          </dt>
          <dd>{activitySummaryEntity.empresaId}</dd>
          <dt>
            <span id="fieldActivity">
              <Translate contentKey="b4CarecollectApp.activitySummary.fieldActivity">Field Activity</Translate>
            </span>
          </dt>
          <dd>{activitySummaryEntity.fieldActivity}</dd>
          <dt>
            <span id="fieldDuration">
              <Translate contentKey="b4CarecollectApp.activitySummary.fieldDuration">Field Duration</Translate>
            </span>
          </dt>
          <dd>{activitySummaryEntity.fieldDuration}</dd>
          <dt>
            <span id="fieldNumSegments">
              <Translate contentKey="b4CarecollectApp.activitySummary.fieldNumSegments">Field Num Segments</Translate>
            </span>
          </dt>
          <dd>{activitySummaryEntity.fieldNumSegments}</dd>
          <dt>
            <span id="startTime">
              <Translate contentKey="b4CarecollectApp.activitySummary.startTime">Start Time</Translate>
            </span>
          </dt>
          <dd>
            {activitySummaryEntity.startTime ? (
              <TextFormat value={activitySummaryEntity.startTime} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="endTime">
              <Translate contentKey="b4CarecollectApp.activitySummary.endTime">End Time</Translate>
            </span>
          </dt>
          <dd>
            {activitySummaryEntity.endTime ? (
              <TextFormat value={activitySummaryEntity.endTime} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
        </dl>
        <Button tag={Link} to="/activity-summary" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/activity-summary/${activitySummaryEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default ActivitySummaryDetail;
