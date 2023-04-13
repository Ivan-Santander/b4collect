import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IBloodPressure } from 'app/shared/model/blood-pressure.model';
import { getEntity, updateEntity, createEntity, reset } from './blood-pressure.reducer';

export const BloodPressureUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const bloodPressureEntity = useAppSelector(state => state.bloodPressure.entity);
  const loading = useAppSelector(state => state.bloodPressure.loading);
  const updating = useAppSelector(state => state.bloodPressure.updating);
  const updateSuccess = useAppSelector(state => state.bloodPressure.updateSuccess);

  const handleClose = () => {
    navigate('/blood-pressure');
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
    values.endTime = convertDateTimeToServer(values.endTime);

    const entity = {
      ...bloodPressureEntity,
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
          endTime: displayDefaultDateTime(),
        }
      : {
          ...bloodPressureEntity,
          endTime: convertDateTimeFromServer(bloodPressureEntity.endTime),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="b4CarecollectApp.bloodPressure.home.createOrEditLabel" data-cy="BloodPressureCreateUpdateHeading">
            <Translate contentKey="b4CarecollectApp.bloodPressure.home.createOrEditLabel">Create or edit a BloodPressure</Translate>
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
                  id="blood-pressure-id"
                  label={translate('b4CarecollectApp.bloodPressure.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('b4CarecollectApp.bloodPressure.usuarioId')}
                id="blood-pressure-usuarioId"
                name="usuarioId"
                data-cy="usuarioId"
                type="text"
              />
              <ValidatedField
                label={translate('b4CarecollectApp.bloodPressure.empresaId')}
                id="blood-pressure-empresaId"
                name="empresaId"
                data-cy="empresaId"
                type="text"
              />
              <ValidatedField
                label={translate('b4CarecollectApp.bloodPressure.fieldBloodPressureSystolic')}
                id="blood-pressure-fieldBloodPressureSystolic"
                name="fieldBloodPressureSystolic"
                data-cy="fieldBloodPressureSystolic"
                type="text"
              />
              <ValidatedField
                label={translate('b4CarecollectApp.bloodPressure.fieldBloodPressureDiastolic')}
                id="blood-pressure-fieldBloodPressureDiastolic"
                name="fieldBloodPressureDiastolic"
                data-cy="fieldBloodPressureDiastolic"
                type="text"
              />
              <ValidatedField
                label={translate('b4CarecollectApp.bloodPressure.fieldBodyPosition')}
                id="blood-pressure-fieldBodyPosition"
                name="fieldBodyPosition"
                data-cy="fieldBodyPosition"
                type="text"
              />
              <ValidatedField
                label={translate('b4CarecollectApp.bloodPressure.fieldBloodPressureMeasureLocation')}
                id="blood-pressure-fieldBloodPressureMeasureLocation"
                name="fieldBloodPressureMeasureLocation"
                data-cy="fieldBloodPressureMeasureLocation"
                type="text"
              />
              <ValidatedField
                label={translate('b4CarecollectApp.bloodPressure.endTime')}
                id="blood-pressure-endTime"
                name="endTime"
                data-cy="endTime"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/blood-pressure" replace color="info">
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

export default BloodPressureUpdate;
