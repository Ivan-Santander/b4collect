import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IBloodPressureSummary } from 'app/shared/model/blood-pressure-summary.model';
import { getEntity, updateEntity, createEntity, reset } from './blood-pressure-summary.reducer';

export const BloodPressureSummaryUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const bloodPressureSummaryEntity = useAppSelector(state => state.bloodPressureSummary.entity);
  const loading = useAppSelector(state => state.bloodPressureSummary.loading);
  const updating = useAppSelector(state => state.bloodPressureSummary.updating);
  const updateSuccess = useAppSelector(state => state.bloodPressureSummary.updateSuccess);

  const handleClose = () => {
    navigate('/blood-pressure-summary');
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
      ...bloodPressureSummaryEntity,
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
          ...bloodPressureSummaryEntity,
          startTime: convertDateTimeFromServer(bloodPressureSummaryEntity.startTime),
          endTime: convertDateTimeFromServer(bloodPressureSummaryEntity.endTime),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="b4CarecollectApp.bloodPressureSummary.home.createOrEditLabel" data-cy="BloodPressureSummaryCreateUpdateHeading">
            <Translate contentKey="b4CarecollectApp.bloodPressureSummary.home.createOrEditLabel">
              Create or edit a BloodPressureSummary
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
                  id="blood-pressure-summary-id"
                  label={translate('b4CarecollectApp.bloodPressureSummary.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('b4CarecollectApp.bloodPressureSummary.usuarioId')}
                id="blood-pressure-summary-usuarioId"
                name="usuarioId"
                data-cy="usuarioId"
                type="text"
              />
              <ValidatedField
                label={translate('b4CarecollectApp.bloodPressureSummary.empresaId')}
                id="blood-pressure-summary-empresaId"
                name="empresaId"
                data-cy="empresaId"
                type="text"
              />
              <ValidatedField
                label={translate('b4CarecollectApp.bloodPressureSummary.fieldSistolicAverage')}
                id="blood-pressure-summary-fieldSistolicAverage"
                name="fieldSistolicAverage"
                data-cy="fieldSistolicAverage"
                type="text"
              />
              <ValidatedField
                label={translate('b4CarecollectApp.bloodPressureSummary.fieldSistolicMax')}
                id="blood-pressure-summary-fieldSistolicMax"
                name="fieldSistolicMax"
                data-cy="fieldSistolicMax"
                type="text"
              />
              <ValidatedField
                label={translate('b4CarecollectApp.bloodPressureSummary.fieldSistolicMin')}
                id="blood-pressure-summary-fieldSistolicMin"
                name="fieldSistolicMin"
                data-cy="fieldSistolicMin"
                type="text"
              />
              <ValidatedField
                label={translate('b4CarecollectApp.bloodPressureSummary.fieldDiasatolicAverage')}
                id="blood-pressure-summary-fieldDiasatolicAverage"
                name="fieldDiasatolicAverage"
                data-cy="fieldDiasatolicAverage"
                type="text"
              />
              <ValidatedField
                label={translate('b4CarecollectApp.bloodPressureSummary.fieldDiastolicMax')}
                id="blood-pressure-summary-fieldDiastolicMax"
                name="fieldDiastolicMax"
                data-cy="fieldDiastolicMax"
                type="text"
              />
              <ValidatedField
                label={translate('b4CarecollectApp.bloodPressureSummary.fieldDiastolicMin')}
                id="blood-pressure-summary-fieldDiastolicMin"
                name="fieldDiastolicMin"
                data-cy="fieldDiastolicMin"
                type="text"
              />
              <ValidatedField
                label={translate('b4CarecollectApp.bloodPressureSummary.bodyPosition')}
                id="blood-pressure-summary-bodyPosition"
                name="bodyPosition"
                data-cy="bodyPosition"
                type="text"
              />
              <ValidatedField
                label={translate('b4CarecollectApp.bloodPressureSummary.measurementLocation')}
                id="blood-pressure-summary-measurementLocation"
                name="measurementLocation"
                data-cy="measurementLocation"
                type="text"
              />
              <ValidatedField
                label={translate('b4CarecollectApp.bloodPressureSummary.startTime')}
                id="blood-pressure-summary-startTime"
                name="startTime"
                data-cy="startTime"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('b4CarecollectApp.bloodPressureSummary.endTime')}
                id="blood-pressure-summary-endTime"
                name="endTime"
                data-cy="endTime"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/blood-pressure-summary" replace color="info">
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

export default BloodPressureSummaryUpdate;
