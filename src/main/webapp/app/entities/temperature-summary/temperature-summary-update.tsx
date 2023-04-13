import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ITemperatureSummary } from 'app/shared/model/temperature-summary.model';
import { getEntity, updateEntity, createEntity, reset } from './temperature-summary.reducer';

export const TemperatureSummaryUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const temperatureSummaryEntity = useAppSelector(state => state.temperatureSummary.entity);
  const loading = useAppSelector(state => state.temperatureSummary.loading);
  const updating = useAppSelector(state => state.temperatureSummary.updating);
  const updateSuccess = useAppSelector(state => state.temperatureSummary.updateSuccess);

  const handleClose = () => {
    navigate('/temperature-summary');
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
      ...temperatureSummaryEntity,
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
          ...temperatureSummaryEntity,
          startTime: convertDateTimeFromServer(temperatureSummaryEntity.startTime),
          endTime: convertDateTimeFromServer(temperatureSummaryEntity.endTime),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="b4CarecollectApp.temperatureSummary.home.createOrEditLabel" data-cy="TemperatureSummaryCreateUpdateHeading">
            <Translate contentKey="b4CarecollectApp.temperatureSummary.home.createOrEditLabel">
              Create or edit a TemperatureSummary
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
                  id="temperature-summary-id"
                  label={translate('b4CarecollectApp.temperatureSummary.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('b4CarecollectApp.temperatureSummary.usuarioId')}
                id="temperature-summary-usuarioId"
                name="usuarioId"
                data-cy="usuarioId"
                type="text"
              />
              <ValidatedField
                label={translate('b4CarecollectApp.temperatureSummary.empresaId')}
                id="temperature-summary-empresaId"
                name="empresaId"
                data-cy="empresaId"
                type="text"
              />
              <ValidatedField
                label={translate('b4CarecollectApp.temperatureSummary.fieldAverage')}
                id="temperature-summary-fieldAverage"
                name="fieldAverage"
                data-cy="fieldAverage"
                type="text"
              />
              <ValidatedField
                label={translate('b4CarecollectApp.temperatureSummary.fieldMax')}
                id="temperature-summary-fieldMax"
                name="fieldMax"
                data-cy="fieldMax"
                type="text"
              />
              <ValidatedField
                label={translate('b4CarecollectApp.temperatureSummary.fieldMin')}
                id="temperature-summary-fieldMin"
                name="fieldMin"
                data-cy="fieldMin"
                type="text"
              />
              <ValidatedField
                label={translate('b4CarecollectApp.temperatureSummary.measurementLocation')}
                id="temperature-summary-measurementLocation"
                name="measurementLocation"
                data-cy="measurementLocation"
                type="text"
              />
              <ValidatedField
                label={translate('b4CarecollectApp.temperatureSummary.startTime')}
                id="temperature-summary-startTime"
                name="startTime"
                data-cy="startTime"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('b4CarecollectApp.temperatureSummary.endTime')}
                id="temperature-summary-endTime"
                name="endTime"
                data-cy="endTime"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/temperature-summary" replace color="info">
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

export default TemperatureSummaryUpdate;
