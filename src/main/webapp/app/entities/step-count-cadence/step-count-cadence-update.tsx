import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IStepCountCadence } from 'app/shared/model/step-count-cadence.model';
import { getEntity, updateEntity, createEntity, reset } from './step-count-cadence.reducer';

export const StepCountCadenceUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const stepCountCadenceEntity = useAppSelector(state => state.stepCountCadence.entity);
  const loading = useAppSelector(state => state.stepCountCadence.loading);
  const updating = useAppSelector(state => state.stepCountCadence.updating);
  const updateSuccess = useAppSelector(state => state.stepCountCadence.updateSuccess);

  const handleClose = () => {
    navigate('/step-count-cadence');
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
      ...stepCountCadenceEntity,
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
          ...stepCountCadenceEntity,
          startTime: convertDateTimeFromServer(stepCountCadenceEntity.startTime),
          endTime: convertDateTimeFromServer(stepCountCadenceEntity.endTime),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="b4CarecollectApp.stepCountCadence.home.createOrEditLabel" data-cy="StepCountCadenceCreateUpdateHeading">
            <Translate contentKey="b4CarecollectApp.stepCountCadence.home.createOrEditLabel">Create or edit a StepCountCadence</Translate>
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
                  id="step-count-cadence-id"
                  label={translate('b4CarecollectApp.stepCountCadence.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('b4CarecollectApp.stepCountCadence.usuarioId')}
                id="step-count-cadence-usuarioId"
                name="usuarioId"
                data-cy="usuarioId"
                type="text"
              />
              <ValidatedField
                label={translate('b4CarecollectApp.stepCountCadence.empresaId')}
                id="step-count-cadence-empresaId"
                name="empresaId"
                data-cy="empresaId"
                type="text"
              />
              <ValidatedField
                label={translate('b4CarecollectApp.stepCountCadence.rpm')}
                id="step-count-cadence-rpm"
                name="rpm"
                data-cy="rpm"
                type="text"
              />
              <ValidatedField
                label={translate('b4CarecollectApp.stepCountCadence.startTime')}
                id="step-count-cadence-startTime"
                name="startTime"
                data-cy="startTime"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('b4CarecollectApp.stepCountCadence.endTime')}
                id="step-count-cadence-endTime"
                name="endTime"
                data-cy="endTime"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/step-count-cadence" replace color="info">
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

export default StepCountCadenceUpdate;
