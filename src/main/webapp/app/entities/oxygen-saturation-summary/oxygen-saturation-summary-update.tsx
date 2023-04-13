import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IOxygenSaturationSummary } from 'app/shared/model/oxygen-saturation-summary.model';
import { getEntity, updateEntity, createEntity, reset } from './oxygen-saturation-summary.reducer';

export const OxygenSaturationSummaryUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const oxygenSaturationSummaryEntity = useAppSelector(state => state.oxygenSaturationSummary.entity);
  const loading = useAppSelector(state => state.oxygenSaturationSummary.loading);
  const updating = useAppSelector(state => state.oxygenSaturationSummary.updating);
  const updateSuccess = useAppSelector(state => state.oxygenSaturationSummary.updateSuccess);

  const handleClose = () => {
    navigate('/oxygen-saturation-summary');
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
      ...oxygenSaturationSummaryEntity,
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
          ...oxygenSaturationSummaryEntity,
          endTime: convertDateTimeFromServer(oxygenSaturationSummaryEntity.endTime),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="b4CarecollectApp.oxygenSaturationSummary.home.createOrEditLabel" data-cy="OxygenSaturationSummaryCreateUpdateHeading">
            <Translate contentKey="b4CarecollectApp.oxygenSaturationSummary.home.createOrEditLabel">
              Create or edit a OxygenSaturationSummary
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
                  id="oxygen-saturation-summary-id"
                  label={translate('b4CarecollectApp.oxygenSaturationSummary.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('b4CarecollectApp.oxygenSaturationSummary.usuarioId')}
                id="oxygen-saturation-summary-usuarioId"
                name="usuarioId"
                data-cy="usuarioId"
                type="text"
              />
              <ValidatedField
                label={translate('b4CarecollectApp.oxygenSaturationSummary.empresaId')}
                id="oxygen-saturation-summary-empresaId"
                name="empresaId"
                data-cy="empresaId"
                type="text"
              />
              <ValidatedField
                label={translate('b4CarecollectApp.oxygenSaturationSummary.fieldOxigenSaturationAverage')}
                id="oxygen-saturation-summary-fieldOxigenSaturationAverage"
                name="fieldOxigenSaturationAverage"
                data-cy="fieldOxigenSaturationAverage"
                type="text"
              />
              <ValidatedField
                label={translate('b4CarecollectApp.oxygenSaturationSummary.fieldOxigenSaturationMax')}
                id="oxygen-saturation-summary-fieldOxigenSaturationMax"
                name="fieldOxigenSaturationMax"
                data-cy="fieldOxigenSaturationMax"
                type="text"
              />
              <ValidatedField
                label={translate('b4CarecollectApp.oxygenSaturationSummary.fieldOxigenSaturationMin')}
                id="oxygen-saturation-summary-fieldOxigenSaturationMin"
                name="fieldOxigenSaturationMin"
                data-cy="fieldOxigenSaturationMin"
                type="text"
              />
              <ValidatedField
                label={translate('b4CarecollectApp.oxygenSaturationSummary.fieldSuplementalOxigenFlowRateAverage')}
                id="oxygen-saturation-summary-fieldSuplementalOxigenFlowRateAverage"
                name="fieldSuplementalOxigenFlowRateAverage"
                data-cy="fieldSuplementalOxigenFlowRateAverage"
                type="text"
              />
              <ValidatedField
                label={translate('b4CarecollectApp.oxygenSaturationSummary.fieldSuplementalOxigenFlowRateMax')}
                id="oxygen-saturation-summary-fieldSuplementalOxigenFlowRateMax"
                name="fieldSuplementalOxigenFlowRateMax"
                data-cy="fieldSuplementalOxigenFlowRateMax"
                type="text"
              />
              <ValidatedField
                label={translate('b4CarecollectApp.oxygenSaturationSummary.fieldSuplementalOxigenFlowRateMin')}
                id="oxygen-saturation-summary-fieldSuplementalOxigenFlowRateMin"
                name="fieldSuplementalOxigenFlowRateMin"
                data-cy="fieldSuplementalOxigenFlowRateMin"
                type="text"
              />
              <ValidatedField
                label={translate('b4CarecollectApp.oxygenSaturationSummary.fieldOxigenTherapyAdministrationMode')}
                id="oxygen-saturation-summary-fieldOxigenTherapyAdministrationMode"
                name="fieldOxigenTherapyAdministrationMode"
                data-cy="fieldOxigenTherapyAdministrationMode"
                type="text"
              />
              <ValidatedField
                label={translate('b4CarecollectApp.oxygenSaturationSummary.fieldOxigenSaturationMode')}
                id="oxygen-saturation-summary-fieldOxigenSaturationMode"
                name="fieldOxigenSaturationMode"
                data-cy="fieldOxigenSaturationMode"
                type="text"
              />
              <ValidatedField
                label={translate('b4CarecollectApp.oxygenSaturationSummary.fieldOxigenSaturationMeasurementMethod')}
                id="oxygen-saturation-summary-fieldOxigenSaturationMeasurementMethod"
                name="fieldOxigenSaturationMeasurementMethod"
                data-cy="fieldOxigenSaturationMeasurementMethod"
                type="text"
              />
              <ValidatedField
                label={translate('b4CarecollectApp.oxygenSaturationSummary.endTime')}
                id="oxygen-saturation-summary-endTime"
                name="endTime"
                data-cy="endTime"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/oxygen-saturation-summary" replace color="info">
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

export default OxygenSaturationSummaryUpdate;
