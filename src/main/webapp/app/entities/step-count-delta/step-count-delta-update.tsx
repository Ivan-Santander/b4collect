import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IStepCountDelta } from 'app/shared/model/step-count-delta.model';
import { getEntity, updateEntity, createEntity, reset } from './step-count-delta.reducer';

export const StepCountDeltaUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const stepCountDeltaEntity = useAppSelector(state => state.stepCountDelta.entity);
  const loading = useAppSelector(state => state.stepCountDelta.loading);
  const updating = useAppSelector(state => state.stepCountDelta.updating);
  const updateSuccess = useAppSelector(state => state.stepCountDelta.updateSuccess);

  const handleClose = () => {
    navigate('/step-count-delta');
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
      ...stepCountDeltaEntity,
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
          ...stepCountDeltaEntity,
          startTime: convertDateTimeFromServer(stepCountDeltaEntity.startTime),
          endTime: convertDateTimeFromServer(stepCountDeltaEntity.endTime),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="b4CarecollectApp.stepCountDelta.home.createOrEditLabel" data-cy="StepCountDeltaCreateUpdateHeading">
            <Translate contentKey="b4CarecollectApp.stepCountDelta.home.createOrEditLabel">Create or edit a StepCountDelta</Translate>
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
                  id="step-count-delta-id"
                  label={translate('b4CarecollectApp.stepCountDelta.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('b4CarecollectApp.stepCountDelta.usuarioId')}
                id="step-count-delta-usuarioId"
                name="usuarioId"
                data-cy="usuarioId"
                type="text"
              />
              <ValidatedField
                label={translate('b4CarecollectApp.stepCountDelta.empresaId')}
                id="step-count-delta-empresaId"
                name="empresaId"
                data-cy="empresaId"
                type="text"
              />
              <ValidatedField
                label={translate('b4CarecollectApp.stepCountDelta.steps')}
                id="step-count-delta-steps"
                name="steps"
                data-cy="steps"
                type="text"
              />
              <ValidatedField
                label={translate('b4CarecollectApp.stepCountDelta.startTime')}
                id="step-count-delta-startTime"
                name="startTime"
                data-cy="startTime"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('b4CarecollectApp.stepCountDelta.endTime')}
                id="step-count-delta-endTime"
                name="endTime"
                data-cy="endTime"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/step-count-delta" replace color="info">
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

export default StepCountDeltaUpdate;
