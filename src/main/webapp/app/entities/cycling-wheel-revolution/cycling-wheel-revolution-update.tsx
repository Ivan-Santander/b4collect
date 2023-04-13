import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ICyclingWheelRevolution } from 'app/shared/model/cycling-wheel-revolution.model';
import { getEntity, updateEntity, createEntity, reset } from './cycling-wheel-revolution.reducer';

export const CyclingWheelRevolutionUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const cyclingWheelRevolutionEntity = useAppSelector(state => state.cyclingWheelRevolution.entity);
  const loading = useAppSelector(state => state.cyclingWheelRevolution.loading);
  const updating = useAppSelector(state => state.cyclingWheelRevolution.updating);
  const updateSuccess = useAppSelector(state => state.cyclingWheelRevolution.updateSuccess);

  const handleClose = () => {
    navigate('/cycling-wheel-revolution');
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
      ...cyclingWheelRevolutionEntity,
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
          ...cyclingWheelRevolutionEntity,
          startTime: convertDateTimeFromServer(cyclingWheelRevolutionEntity.startTime),
          endTime: convertDateTimeFromServer(cyclingWheelRevolutionEntity.endTime),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="b4CarecollectApp.cyclingWheelRevolution.home.createOrEditLabel" data-cy="CyclingWheelRevolutionCreateUpdateHeading">
            <Translate contentKey="b4CarecollectApp.cyclingWheelRevolution.home.createOrEditLabel">
              Create or edit a CyclingWheelRevolution
            </Translate>
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
                  id="cycling-wheel-revolution-id"
                  label={translate('b4CarecollectApp.cyclingWheelRevolution.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('b4CarecollectApp.cyclingWheelRevolution.usuarioId')}
                id="cycling-wheel-revolution-usuarioId"
                name="usuarioId"
                data-cy="usuarioId"
                type="text"
              />
              <ValidatedField
                label={translate('b4CarecollectApp.cyclingWheelRevolution.empresaId')}
                id="cycling-wheel-revolution-empresaId"
                name="empresaId"
                data-cy="empresaId"
                type="text"
              />
              <ValidatedField
                label={translate('b4CarecollectApp.cyclingWheelRevolution.revolutions')}
                id="cycling-wheel-revolution-revolutions"
                name="revolutions"
                data-cy="revolutions"
                type="text"
              />
              <ValidatedField
                label={translate('b4CarecollectApp.cyclingWheelRevolution.startTime')}
                id="cycling-wheel-revolution-startTime"
                name="startTime"
                data-cy="startTime"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('b4CarecollectApp.cyclingWheelRevolution.endTime')}
                id="cycling-wheel-revolution-endTime"
                name="endTime"
                data-cy="endTime"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/cycling-wheel-revolution" replace color="info">
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

export default CyclingWheelRevolutionUpdate;
