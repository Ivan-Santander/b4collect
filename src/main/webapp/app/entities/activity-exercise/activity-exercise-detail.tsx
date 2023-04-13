import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './activity-exercise.reducer';

export const ActivityExerciseDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const activityExerciseEntity = useAppSelector(state => state.activityExercise.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="activityExerciseDetailsHeading">
          <Translate contentKey="b4CarecollectApp.activityExercise.detail.title">ActivityExercise</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="b4CarecollectApp.activityExercise.id">Id</Translate>
            </span>
          </dt>
          <dd>{activityExerciseEntity.id}</dd>
          <dt>
            <span id="usuarioId">
              <Translate contentKey="b4CarecollectApp.activityExercise.usuarioId">Usuario Id</Translate>
            </span>
          </dt>
          <dd>{activityExerciseEntity.usuarioId}</dd>
          <dt>
            <span id="empresaId">
              <Translate contentKey="b4CarecollectApp.activityExercise.empresaId">Empresa Id</Translate>
            </span>
          </dt>
          <dd>{activityExerciseEntity.empresaId}</dd>
          <dt>
            <span id="exercise">
              <Translate contentKey="b4CarecollectApp.activityExercise.exercise">Exercise</Translate>
            </span>
          </dt>
          <dd>{activityExerciseEntity.exercise}</dd>
          <dt>
            <span id="repetitions">
              <Translate contentKey="b4CarecollectApp.activityExercise.repetitions">Repetitions</Translate>
            </span>
          </dt>
          <dd>{activityExerciseEntity.repetitions}</dd>
          <dt>
            <span id="typeResistence">
              <Translate contentKey="b4CarecollectApp.activityExercise.typeResistence">Type Resistence</Translate>
            </span>
          </dt>
          <dd>{activityExerciseEntity.typeResistence}</dd>
          <dt>
            <span id="resistenceKg">
              <Translate contentKey="b4CarecollectApp.activityExercise.resistenceKg">Resistence Kg</Translate>
            </span>
          </dt>
          <dd>{activityExerciseEntity.resistenceKg}</dd>
          <dt>
            <span id="duration">
              <Translate contentKey="b4CarecollectApp.activityExercise.duration">Duration</Translate>
            </span>
          </dt>
          <dd>{activityExerciseEntity.duration}</dd>
          <dt>
            <span id="startTime">
              <Translate contentKey="b4CarecollectApp.activityExercise.startTime">Start Time</Translate>
            </span>
          </dt>
          <dd>
            {activityExerciseEntity.startTime ? (
              <TextFormat value={activityExerciseEntity.startTime} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="endTime">
              <Translate contentKey="b4CarecollectApp.activityExercise.endTime">End Time</Translate>
            </span>
          </dt>
          <dd>
            {activityExerciseEntity.endTime ? (
              <TextFormat value={activityExerciseEntity.endTime} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
        </dl>
        <Button tag={Link} to="/activity-exercise" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/activity-exercise/${activityExerciseEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default ActivityExerciseDetail;
