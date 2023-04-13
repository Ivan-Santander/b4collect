import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ICaloriesBMR } from 'app/shared/model/calories-bmr.model';
import { getEntity, updateEntity, createEntity, reset } from './calories-bmr.reducer';

export const CaloriesBMRUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const caloriesBMREntity = useAppSelector(state => state.caloriesBMR.entity);
  const loading = useAppSelector(state => state.caloriesBMR.loading);
  const updating = useAppSelector(state => state.caloriesBMR.updating);
  const updateSuccess = useAppSelector(state => state.caloriesBMR.updateSuccess);

  const handleClose = () => {
    navigate('/calories-bmr');
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
      ...caloriesBMREntity,
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
          ...caloriesBMREntity,
          startTime: convertDateTimeFromServer(caloriesBMREntity.startTime),
          endTime: convertDateTimeFromServer(caloriesBMREntity.endTime),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="b4CarecollectApp.caloriesBMR.home.createOrEditLabel" data-cy="CaloriesBMRCreateUpdateHeading">
            <Translate contentKey="b4CarecollectApp.caloriesBMR.home.createOrEditLabel">Create or edit a CaloriesBMR</Translate>
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
                  id="calories-bmr-id"
                  label={translate('b4CarecollectApp.caloriesBMR.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('b4CarecollectApp.caloriesBMR.usuarioId')}
                id="calories-bmr-usuarioId"
                name="usuarioId"
                data-cy="usuarioId"
                type="text"
              />
              <ValidatedField
                label={translate('b4CarecollectApp.caloriesBMR.empresaId')}
                id="calories-bmr-empresaId"
                name="empresaId"
                data-cy="empresaId"
                type="text"
              />
              <ValidatedField
                label={translate('b4CarecollectApp.caloriesBMR.calorias')}
                id="calories-bmr-calorias"
                name="calorias"
                data-cy="calorias"
                type="text"
              />
              <ValidatedField
                label={translate('b4CarecollectApp.caloriesBMR.startTime')}
                id="calories-bmr-startTime"
                name="startTime"
                data-cy="startTime"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('b4CarecollectApp.caloriesBMR.endTime')}
                id="calories-bmr-endTime"
                name="endTime"
                data-cy="endTime"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/calories-bmr" replace color="info">
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

export default CaloriesBMRUpdate;
