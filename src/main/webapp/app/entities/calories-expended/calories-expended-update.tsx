import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ICaloriesExpended } from 'app/shared/model/calories-expended.model';
import { getEntity, updateEntity, createEntity, reset } from './calories-expended.reducer';

export const CaloriesExpendedUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const caloriesExpendedEntity = useAppSelector(state => state.caloriesExpended.entity);
  const loading = useAppSelector(state => state.caloriesExpended.loading);
  const updating = useAppSelector(state => state.caloriesExpended.updating);
  const updateSuccess = useAppSelector(state => state.caloriesExpended.updateSuccess);

  const handleClose = () => {
    navigate('/calories-expended');
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
      ...caloriesExpendedEntity,
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
          ...caloriesExpendedEntity,
          startTime: convertDateTimeFromServer(caloriesExpendedEntity.startTime),
          endTime: convertDateTimeFromServer(caloriesExpendedEntity.endTime),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="b4CarecollectApp.caloriesExpended.home.createOrEditLabel" data-cy="CaloriesExpendedCreateUpdateHeading">
            <Translate contentKey="b4CarecollectApp.caloriesExpended.home.createOrEditLabel">Create or edit a CaloriesExpended</Translate>
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
                  id="calories-expended-id"
                  label={translate('b4CarecollectApp.caloriesExpended.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('b4CarecollectApp.caloriesExpended.usuarioId')}
                id="calories-expended-usuarioId"
                name="usuarioId"
                data-cy="usuarioId"
                type="text"
              />
              <ValidatedField
                label={translate('b4CarecollectApp.caloriesExpended.empresaId')}
                id="calories-expended-empresaId"
                name="empresaId"
                data-cy="empresaId"
                type="text"
              />
              <ValidatedField
                label={translate('b4CarecollectApp.caloriesExpended.calorias')}
                id="calories-expended-calorias"
                name="calorias"
                data-cy="calorias"
                type="text"
              />
              <ValidatedField
                label={translate('b4CarecollectApp.caloriesExpended.startTime')}
                id="calories-expended-startTime"
                name="startTime"
                data-cy="startTime"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('b4CarecollectApp.caloriesExpended.endTime')}
                id="calories-expended-endTime"
                name="endTime"
                data-cy="endTime"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/calories-expended" replace color="info">
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

export default CaloriesExpendedUpdate;
