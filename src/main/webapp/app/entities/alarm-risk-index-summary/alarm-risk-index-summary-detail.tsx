import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './alarm-risk-index-summary.reducer';

export const AlarmRiskIndexSummaryDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const alarmRiskIndexSummaryEntity = useAppSelector(state => state.alarmRiskIndexSummary.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="alarmRiskIndexSummaryDetailsHeading">
          <Translate contentKey="b4CarecollectApp.alarmRiskIndexSummary.detail.title">AlarmRiskIndexSummary</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="b4CarecollectApp.alarmRiskIndexSummary.id">Id</Translate>
            </span>
          </dt>
          <dd>{alarmRiskIndexSummaryEntity.id}</dd>
          <dt>
            <span id="usuarioId">
              <Translate contentKey="b4CarecollectApp.alarmRiskIndexSummary.usuarioId">Usuario Id</Translate>
            </span>
          </dt>
          <dd>{alarmRiskIndexSummaryEntity.usuarioId}</dd>
          <dt>
            <span id="empresaId">
              <Translate contentKey="b4CarecollectApp.alarmRiskIndexSummary.empresaId">Empresa Id</Translate>
            </span>
          </dt>
          <dd>{alarmRiskIndexSummaryEntity.empresaId}</dd>
          <dt>
            <span id="fieldAlarmRiskAverage">
              <Translate contentKey="b4CarecollectApp.alarmRiskIndexSummary.fieldAlarmRiskAverage">Field Alarm Risk Average</Translate>
            </span>
          </dt>
          <dd>{alarmRiskIndexSummaryEntity.fieldAlarmRiskAverage}</dd>
          <dt>
            <span id="fieldAlarmRiskMax">
              <Translate contentKey="b4CarecollectApp.alarmRiskIndexSummary.fieldAlarmRiskMax">Field Alarm Risk Max</Translate>
            </span>
          </dt>
          <dd>{alarmRiskIndexSummaryEntity.fieldAlarmRiskMax}</dd>
          <dt>
            <span id="fieldAlarmRiskMin">
              <Translate contentKey="b4CarecollectApp.alarmRiskIndexSummary.fieldAlarmRiskMin">Field Alarm Risk Min</Translate>
            </span>
          </dt>
          <dd>{alarmRiskIndexSummaryEntity.fieldAlarmRiskMin}</dd>
          <dt>
            <span id="startTime">
              <Translate contentKey="b4CarecollectApp.alarmRiskIndexSummary.startTime">Start Time</Translate>
            </span>
          </dt>
          <dd>
            {alarmRiskIndexSummaryEntity.startTime ? (
              <TextFormat value={alarmRiskIndexSummaryEntity.startTime} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="endTime">
              <Translate contentKey="b4CarecollectApp.alarmRiskIndexSummary.endTime">End Time</Translate>
            </span>
          </dt>
          <dd>
            {alarmRiskIndexSummaryEntity.endTime ? (
              <TextFormat value={alarmRiskIndexSummaryEntity.endTime} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
        </dl>
        <Button tag={Link} to="/alarm-risk-index-summary" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/alarm-risk-index-summary/${alarmRiskIndexSummaryEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default AlarmRiskIndexSummaryDetail;
