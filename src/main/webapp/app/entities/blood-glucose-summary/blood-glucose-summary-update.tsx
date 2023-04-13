import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IBloodGlucoseSummary } from 'app/shared/model/blood-glucose-summary.model';
import { getEntity, updateEntity, createEntity, reset } from './blood-glucose-summary.reducer';

export const BloodGlucoseSummaryUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const bloodGlucoseSummaryEntity = useAppSelector(state => state.bloodGlucoseSummary.entity);
  const loading = useAppSelector(state => state.bloodGlucoseSummary.loading);
  const updating = useAppSelector(state => state.bloodGlucoseSummary.updating);
  const updateSuccess = useAppSelector(state => state.bloodGlucoseSummary.updateSuccess);

  const handleClose = () => {
    navigate('/blood-glucose-summary');
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
      ...bloodGlucoseSummaryEntity,
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
          ...bloodGlucoseSummaryEntity,
          startTime: convertDateTimeFromServer(bloodGlucoseSummaryEntity.startTime),
          endTime: convertDateTimeFromServer(bloodGlucoseSummaryEntity.endTime),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="b4CarecollectApp.bloodGlucoseSummary.home.createOrEditLabel" data-cy="BloodGlucoseSummaryCreateUpdateHeading">
            <Translate contentKey="b4CarecollectApp.bloodGlucoseSummary.home.createOrEditLabel">
              Create or edit a BloodGlucoseSummary
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
                  id="blood-glucose-summary-id"
                  label={translate('b4CarecollectApp.bloodGlucoseSummary.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('b4CarecollectApp.bloodGlucoseSummary.usuarioId')}
                id="blood-glucose-summary-usuarioId"
                name="usuarioId"
                data-cy="usuarioId"
                type="text"
              />
              <ValidatedField
                label={translate('b4CarecollectApp.bloodGlucoseSummary.empresaId')}
                id="blood-glucose-summary-empresaId"
                name="empresaId"
                data-cy="empresaId"
                type="text"
              />
              <ValidatedField
                label={translate('b4CarecollectApp.bloodGlucoseSummary.fieldAverage')}
                id="blood-glucose-summary-fieldAverage"
                name="fieldAverage"
                data-cy="fieldAverage"
                type="text"
              />
              <ValidatedField
                label={translate('b4CarecollectApp.bloodGlucoseSummary.fieldMax')}
                id="blood-glucose-summary-fieldMax"
                name="fieldMax"
                data-cy="fieldMax"
                type="text"
              />
              <ValidatedField
                label={translate('b4CarecollectApp.bloodGlucoseSummary.fieldMin')}
                id="blood-glucose-summary-fieldMin"
                name="fieldMin"
                data-cy="fieldMin"
                type="text"
              />
              <ValidatedField
                label={translate('b4CarecollectApp.bloodGlucoseSummary.intervalFood')}
                id="blood-glucose-summary-intervalFood"
                name="intervalFood"
                data-cy="intervalFood"
                type="text"
              />
              <ValidatedField
                label={translate('b4CarecollectApp.bloodGlucoseSummary.mealType')}
                id="blood-glucose-summary-mealType"
                name="mealType"
                data-cy="mealType"
                type="textarea"
              />
              <ValidatedField
                label={translate('b4CarecollectApp.bloodGlucoseSummary.relationTemporalSleep')}
                id="blood-glucose-summary-relationTemporalSleep"
                name="relationTemporalSleep"
                data-cy="relationTemporalSleep"
                type="text"
              />
              <ValidatedField
                label={translate('b4CarecollectApp.bloodGlucoseSummary.sampleSource')}
                id="blood-glucose-summary-sampleSource"
                name="sampleSource"
                data-cy="sampleSource"
                type="text"
              />
              <ValidatedField
                label={translate('b4CarecollectApp.bloodGlucoseSummary.startTime')}
                id="blood-glucose-summary-startTime"
                name="startTime"
                data-cy="startTime"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('b4CarecollectApp.bloodGlucoseSummary.endTime')}
                id="blood-glucose-summary-endTime"
                name="endTime"
                data-cy="endTime"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/blood-glucose-summary" replace color="info">
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

export default BloodGlucoseSummaryUpdate;
