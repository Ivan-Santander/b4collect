import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IAlarmRiskIndexSummary } from 'app/shared/model/alarm-risk-index-summary.model';
import { getEntity, updateEntity, createEntity, reset } from './alarm-risk-index-summary.reducer';

export const AlarmRiskIndexSummaryUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const alarmRiskIndexSummaryEntity = useAppSelector(state => state.alarmRiskIndexSummary.entity);
  const loading = useAppSelector(state => state.alarmRiskIndexSummary.loading);
  const updating = useAppSelector(state => state.alarmRiskIndexSummary.updating);
  const updateSuccess = useAppSelector(state => state.alarmRiskIndexSummary.updateSuccess);

  const handleClose = () => {
    navigate('/alarm-risk-index-summary');
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
      ...alarmRiskIndexSummaryEntity,
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
          ...alarmRiskIndexSummaryEntity,
          startTime: convertDateTimeFromServer(alarmRiskIndexSummaryEntity.startTime),
          endTime: convertDateTimeFromServer(alarmRiskIndexSummaryEntity.endTime),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="b4CarecollectApp.alarmRiskIndexSummary.home.createOrEditLabel" data-cy="AlarmRiskIndexSummaryCreateUpdateHeading">
            <Translate contentKey="b4CarecollectApp.alarmRiskIndexSummary.home.createOrEditLabel">
              Create or edit a AlarmRiskIndexSummary
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
                  id="alarm-risk-index-summary-id"
                  label={translate('b4CarecollectApp.alarmRiskIndexSummary.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('b4CarecollectApp.alarmRiskIndexSummary.usuarioId')}
                id="alarm-risk-index-summary-usuarioId"
                name="usuarioId"
                data-cy="usuarioId"
                type="text"
              />
              <ValidatedField
                label={translate('b4CarecollectApp.alarmRiskIndexSummary.empresaId')}
                id="alarm-risk-index-summary-empresaId"
                name="empresaId"
                data-cy="empresaId"
                type="text"
              />
              <ValidatedField
                label={translate('b4CarecollectApp.alarmRiskIndexSummary.fieldAlarmRiskAverage')}
                id="alarm-risk-index-summary-fieldAlarmRiskAverage"
                name="fieldAlarmRiskAverage"
                data-cy="fieldAlarmRiskAverage"
                type="text"
              />
              <ValidatedField
                label={translate('b4CarecollectApp.alarmRiskIndexSummary.fieldAlarmRiskMax')}
                id="alarm-risk-index-summary-fieldAlarmRiskMax"
                name="fieldAlarmRiskMax"
                data-cy="fieldAlarmRiskMax"
                type="text"
              />
              <ValidatedField
                label={translate('b4CarecollectApp.alarmRiskIndexSummary.fieldAlarmRiskMin')}
                id="alarm-risk-index-summary-fieldAlarmRiskMin"
                name="fieldAlarmRiskMin"
                data-cy="fieldAlarmRiskMin"
                type="text"
              />
              <ValidatedField
                label={translate('b4CarecollectApp.alarmRiskIndexSummary.startTime')}
                id="alarm-risk-index-summary-startTime"
                name="startTime"
                data-cy="startTime"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('b4CarecollectApp.alarmRiskIndexSummary.endTime')}
                id="alarm-risk-index-summary-endTime"
                name="endTime"
                data-cy="endTime"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/alarm-risk-index-summary" replace color="info">
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

export default AlarmRiskIndexSummaryUpdate;
