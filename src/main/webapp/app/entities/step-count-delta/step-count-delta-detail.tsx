import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './step-count-delta.reducer';

export const StepCountDeltaDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const stepCountDeltaEntity = useAppSelector(state => state.stepCountDelta.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="stepCountDeltaDetailsHeading">
          <Translate contentKey="b4CarecollectApp.stepCountDelta.detail.title">StepCountDelta</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="b4CarecollectApp.stepCountDelta.id">Id</Translate>
            </span>
          </dt>
          <dd>{stepCountDeltaEntity.id}</dd>
          <dt>
            <span id="usuarioId">
              <Translate contentKey="b4CarecollectApp.stepCountDelta.usuarioId">Usuario Id</Translate>
            </span>
          </dt>
          <dd>{stepCountDeltaEntity.usuarioId}</dd>
          <dt>
            <span id="empresaId">
              <Translate contentKey="b4CarecollectApp.stepCountDelta.empresaId">Empresa Id</Translate>
            </span>
          </dt>
          <dd>{stepCountDeltaEntity.empresaId}</dd>
          <dt>
            <span id="steps">
              <Translate contentKey="b4CarecollectApp.stepCountDelta.steps">Steps</Translate>
            </span>
          </dt>
          <dd>{stepCountDeltaEntity.steps}</dd>
          <dt>
            <span id="startTime">
              <Translate contentKey="b4CarecollectApp.stepCountDelta.startTime">Start Time</Translate>
            </span>
          </dt>
          <dd>
            {stepCountDeltaEntity.startTime ? (
              <TextFormat value={stepCountDeltaEntity.startTime} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="endTime">
              <Translate contentKey="b4CarecollectApp.stepCountDelta.endTime">End Time</Translate>
            </span>
          </dt>
          <dd>
            {stepCountDeltaEntity.endTime ? <TextFormat value={stepCountDeltaEntity.endTime} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
        </dl>
        <Button tag={Link} to="/step-count-delta" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/step-count-delta/${stepCountDeltaEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default StepCountDeltaDetail;
