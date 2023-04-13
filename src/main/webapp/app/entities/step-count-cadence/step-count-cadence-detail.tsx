import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './step-count-cadence.reducer';

export const StepCountCadenceDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const stepCountCadenceEntity = useAppSelector(state => state.stepCountCadence.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="stepCountCadenceDetailsHeading">
          <Translate contentKey="b4CarecollectApp.stepCountCadence.detail.title">StepCountCadence</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="b4CarecollectApp.stepCountCadence.id">Id</Translate>
            </span>
          </dt>
          <dd>{stepCountCadenceEntity.id}</dd>
          <dt>
            <span id="usuarioId">
              <Translate contentKey="b4CarecollectApp.stepCountCadence.usuarioId">Usuario Id</Translate>
            </span>
          </dt>
          <dd>{stepCountCadenceEntity.usuarioId}</dd>
          <dt>
            <span id="empresaId">
              <Translate contentKey="b4CarecollectApp.stepCountCadence.empresaId">Empresa Id</Translate>
            </span>
          </dt>
          <dd>{stepCountCadenceEntity.empresaId}</dd>
          <dt>
            <span id="rpm">
              <Translate contentKey="b4CarecollectApp.stepCountCadence.rpm">Rpm</Translate>
            </span>
          </dt>
          <dd>{stepCountCadenceEntity.rpm}</dd>
          <dt>
            <span id="startTime">
              <Translate contentKey="b4CarecollectApp.stepCountCadence.startTime">Start Time</Translate>
            </span>
          </dt>
          <dd>
            {stepCountCadenceEntity.startTime ? (
              <TextFormat value={stepCountCadenceEntity.startTime} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="endTime">
              <Translate contentKey="b4CarecollectApp.stepCountCadence.endTime">End Time</Translate>
            </span>
          </dt>
          <dd>
            {stepCountCadenceEntity.endTime ? (
              <TextFormat value={stepCountCadenceEntity.endTime} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
        </dl>
        <Button tag={Link} to="/step-count-cadence" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/step-count-cadence/${stepCountCadenceEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default StepCountCadenceDetail;
