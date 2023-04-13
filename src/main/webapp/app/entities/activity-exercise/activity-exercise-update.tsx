import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IActivityExercise } from 'app/shared/model/activity-exercise.model';
import { getEntity, updateEntity, createEntity, reset } from './activity-exercise.reducer';

export const ActivityExerciseUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const activityExerciseEntity = useAppSelector(state => state.activityExercise.entity);
  const loading = useAppSelector(state => state.activityExercise.loading);
  const updating = useAppSelector(state => state.activityExercise.updating);
  const updateSuccess = useAppSelector(state => state.activityExercise.updateSuccess);

  const handleClose = () => {
    navigate('/activity-exercise');
  };

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(id));
    }
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    values.startTime = convertDateTimeToServer(values.startTime);
    values.endTime = convertDateTimeToServer(values.endTime);

    const entity = {
      ...activityExerciseEntity,
      ...values,
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {
          startTime: displayDefaultDateTime(),
          endTime: displayDefaultDateTime(),
        }
      : {
          ...activityExerciseEntity,
          startTime: convertDateTimeFromServer(activityExerciseEntity.startTime),
          endTime: convertDateTimeFromServer(activityExerciseEntity.endTime),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="b4CarecollectApp.activityExercise.home.createOrEditLabel" data-cy="ActivityExerciseCreateUpdateHeading">
            <Translate contentKey="b4CarecollectApp.activityExercise.home.createOrEditLabel">Create or edit a ActivityExercise</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="activity-exercise-id"
                  label={translate('b4CarecollectApp.activityExercise.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('b4CarecollectApp.activityExercise.usuarioId')}
                id="activity-exercise-usuarioId"
                name="usuarioId"
                data-cy="usuarioId"
                type="text"
              />
              <ValidatedField
                label={translate('b4CarecollectApp.activityExercise.empresaId')}
                id="activity-exercise-empresaId"
                name="empresaId"
                data-cy="empresaId"
                type="text"
              />
              <ValidatedField
                label={translate('b4CarecollectApp.activityExercise.exercise')}
                id="activity-exercise-exercise"
                name="exercise"
                data-cy="exercise"
                type="text"
              />
              <ValidatedField
                label={translate('b4CarecollectApp.activityExercise.repetitions')}
                id="activity-exercise-repetitions"
                name="repetitions"
                data-cy="repetitions"
                type="text"
              />
              <ValidatedField
                label={translate('b4CarecollectApp.activityExercise.typeResistence')}
                id="activity-exercise-typeResistence"
                name="typeResistence"
                data-cy="typeResistence"
                type="text"
              />
              <ValidatedField
                label={translate('b4CarecollectApp.activityExercise.resistenceKg')}
                id="activity-exercise-resistenceKg"
                name="resistenceKg"
                data-cy="resistenceKg"
                type="text"
              />
              <ValidatedField
                label={translate('b4CarecollectApp.activityExercise.duration')}
                id="activity-exercise-duration"
                name="duration"
                data-cy="duration"
                type="text"
              />
              <ValidatedField
                label={translate('b4CarecollectApp.activityExercise.startTime')}
                id="activity-exercise-startTime"
                name="startTime"
                data-cy="startTime"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('b4CarecollectApp.activityExercise.endTime')}
                id="activity-exercise-endTime"
                name="endTime"
                data-cy="endTime"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/activity-exercise" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default ActivityExerciseUpdate;
