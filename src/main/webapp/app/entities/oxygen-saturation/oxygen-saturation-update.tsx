import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IOxygenSaturation } from 'app/shared/model/oxygen-saturation.model';
import { getEntity, updateEntity, createEntity, reset } from './oxygen-saturation.reducer';

export const OxygenSaturationUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const oxygenSaturationEntity = useAppSelector(state => state.oxygenSaturation.entity);
  const loading = useAppSelector(state => state.oxygenSaturation.loading);
  const updating = useAppSelector(state => state.oxygenSaturation.updating);
  const updateSuccess = useAppSelector(state => state.oxygenSaturation.updateSuccess);

  const handleClose = () => {
    navigate('/oxygen-saturation');
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
      ...oxygenSaturationEntity,
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
          ...oxygenSaturationEntity,
          endTime: convertDateTimeFromServer(oxygenSaturationEntity.endTime),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="b4CarecollectApp.oxygenSaturation.home.createOrEditLabel" data-cy="OxygenSaturationCreateUpdateHeading">
            <Translate contentKey="b4CarecollectApp.oxygenSaturation.home.createOrEditLabel">Create or edit a OxygenSaturation</Translate>
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
                  id="oxygen-saturation-id"
                  label={translate('b4CarecollectApp.oxygenSaturation.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('b4CarecollectApp.oxygenSaturation.usuarioId')}
                id="oxygen-saturation-usuarioId"
                name="usuarioId"
                data-cy="usuarioId"
                type="text"
              />
              <ValidatedField
                label={translate('b4CarecollectApp.oxygenSaturation.empresaId')}
                id="oxygen-saturation-empresaId"
                name="empresaId"
                data-cy="empresaId"
                type="text"
              />
              <ValidatedField
                label={translate('b4CarecollectApp.oxygenSaturation.fieldOxigenSaturation')}
                id="oxygen-saturation-fieldOxigenSaturation"
                name="fieldOxigenSaturation"
                data-cy="fieldOxigenSaturation"
                type="text"
              />
              <ValidatedField
                label={translate('b4CarecollectApp.oxygenSaturation.fieldSuplementalOxigenFlowRate')}
                id="oxygen-saturation-fieldSuplementalOxigenFlowRate"
                name="fieldSuplementalOxigenFlowRate"
                data-cy="fieldSuplementalOxigenFlowRate"
                type="text"
              />
              <ValidatedField
                label={translate('b4CarecollectApp.oxygenSaturation.fieldOxigenTherapyAdministrationMode')}
                id="oxygen-saturation-fieldOxigenTherapyAdministrationMode"
                name="fieldOxigenTherapyAdministrationMode"
                data-cy="fieldOxigenTherapyAdministrationMode"
                type="text"
              />
              <ValidatedField
                label={translate('b4CarecollectApp.oxygenSaturation.fieldOxigenSaturationMode')}
                id="oxygen-saturation-fieldOxigenSaturationMode"
                name="fieldOxigenSaturationMode"
                data-cy="fieldOxigenSaturationMode"
                type="text"
              />
              <ValidatedField
                label={translate('b4CarecollectApp.oxygenSaturation.fieldOxigenSaturationMeasurementMethod')}
                id="oxygen-saturation-fieldOxigenSaturationMeasurementMethod"
                name="fieldOxigenSaturationMeasurementMethod"
                data-cy="fieldOxigenSaturationMeasurementMethod"
                type="text"
              />
              <ValidatedField
                label={translate('b4CarecollectApp.oxygenSaturation.endTime')}
                id="oxygen-saturation-endTime"
                name="endTime"
                data-cy="endTime"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/oxygen-saturation" replace color="info">
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

export default OxygenSaturationUpdate;
