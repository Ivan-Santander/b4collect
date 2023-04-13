import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IMentalHealthSummary } from 'app/shared/model/mental-health-summary.model';
import { getEntity, updateEntity, createEntity, reset } from './mental-health-summary.reducer';

export const MentalHealthSummaryUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const mentalHealthSummaryEntity = useAppSelector(state => state.mentalHealthSummary.entity);
  const loading = useAppSelector(state => state.mentalHealthSummary.loading);
  const updating = useAppSelector(state => state.mentalHealthSummary.updating);
  const updateSuccess = useAppSelector(state => state.mentalHealthSummary.updateSuccess);

  const handleClose = () => {
    navigate('/mental-health-summary');
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
      ...mentalHealthSummaryEntity,
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
          ...mentalHealthSummaryEntity,
          startTime: convertDateTimeFromServer(mentalHealthSummaryEntity.startTime),
          endTime: convertDateTimeFromServer(mentalHealthSummaryEntity.endTime),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="b4CarecollectApp.mentalHealthSummary.home.createOrEditLabel" data-cy="MentalHealthSummaryCreateUpdateHeading">
            <Translate contentKey="b4CarecollectApp.mentalHealthSummary.home.createOrEditLabel">
              Create or edit a MentalHealthSummary
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
                  id="mental-health-summary-id"
                  label={translate('b4CarecollectApp.mentalHealthSummary.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('b4CarecollectApp.mentalHealthSummary.usuarioId')}
                id="mental-health-summary-usuarioId"
                name="usuarioId"
                data-cy="usuarioId"
                type="text"
              />
              <ValidatedField
                label={translate('b4CarecollectApp.mentalHealthSummary.empresaId')}
                id="mental-health-summary-empresaId"
                name="empresaId"
                data-cy="empresaId"
                type="text"
              />
              <ValidatedField
                label={translate('b4CarecollectApp.mentalHealthSummary.emotionDescripMain')}
                id="mental-health-summary-emotionDescripMain"
                name="emotionDescripMain"
                data-cy="emotionDescripMain"
                type="text"
              />
              <ValidatedField
                label={translate('b4CarecollectApp.mentalHealthSummary.emotionDescripSecond')}
                id="mental-health-summary-emotionDescripSecond"
                name="emotionDescripSecond"
                data-cy="emotionDescripSecond"
                type="text"
              />
              <ValidatedField
                label={translate('b4CarecollectApp.mentalHealthSummary.fieldMentalHealthAverage')}
                id="mental-health-summary-fieldMentalHealthAverage"
                name="fieldMentalHealthAverage"
                data-cy="fieldMentalHealthAverage"
                type="text"
              />
              <ValidatedField
                label={translate('b4CarecollectApp.mentalHealthSummary.fieldMentalHealthMax')}
                id="mental-health-summary-fieldMentalHealthMax"
                name="fieldMentalHealthMax"
                data-cy="fieldMentalHealthMax"
                type="text"
              />
              <ValidatedField
                label={translate('b4CarecollectApp.mentalHealthSummary.fieldMentalHealthMin')}
                id="mental-health-summary-fieldMentalHealthMin"
                name="fieldMentalHealthMin"
                data-cy="fieldMentalHealthMin"
                type="text"
              />
              <ValidatedField
                label={translate('b4CarecollectApp.mentalHealthSummary.scoreMentalRisk')}
                id="mental-health-summary-scoreMentalRisk"
                name="scoreMentalRisk"
                data-cy="scoreMentalRisk"
                type="text"
              />
              <ValidatedField
                label={translate('b4CarecollectApp.mentalHealthSummary.startTime')}
                id="mental-health-summary-startTime"
                name="startTime"
                data-cy="startTime"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('b4CarecollectApp.mentalHealthSummary.endTime')}
                id="mental-health-summary-endTime"
                name="endTime"
                data-cy="endTime"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/mental-health-summary" replace color="info">
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

export default MentalHealthSummaryUpdate;
