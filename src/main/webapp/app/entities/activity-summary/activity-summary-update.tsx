import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IActivitySummary } from 'app/shared/model/activity-summary.model';
import { getEntity, updateEntity, createEntity, reset } from './activity-summary.reducer';

export const ActivitySummaryUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const activitySummaryEntity = useAppSelector(state => state.activitySummary.entity);
  const loading = useAppSelector(state => state.activitySummary.loading);
  const updating = useAppSelector(state => state.activitySummary.updating);
  const updateSuccess = useAppSelector(state => state.activitySummary.updateSuccess);

  const handleClose = () => {
    navigate('/activity-summary');
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
      ...activitySummaryEntity,
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
          ...activitySummaryEntity,
          startTime: convertDateTimeFromServer(activitySummaryEntity.startTime),
          endTime: convertDateTimeFromServer(activitySummaryEntity.endTime),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="b4CarecollectApp.activitySummary.home.createOrEditLabel" data-cy="ActivitySummaryCreateUpdateHeading">
            <Translate contentKey="b4CarecollectApp.activitySummary.home.createOrEditLabel">Create or edit a ActivitySummary</Translate>
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
                  id="activity-summary-id"
                  label={translate('b4CarecollectApp.activitySummary.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('b4CarecollectApp.activitySummary.usuarioId')}
                id="activity-summary-usuarioId"
                name="usuarioId"
                data-cy="usuarioId"
                type="text"
              />
              <ValidatedField
                label={translate('b4CarecollectApp.activitySummary.empresaId')}
                id="activity-summary-empresaId"
                name="empresaId"
                data-cy="empresaId"
                type="text"
              />
              <ValidatedField
                label={translate('b4CarecollectApp.activitySummary.fieldActivity')}
                id="activity-summary-fieldActivity"
                name="fieldActivity"
                data-cy="fieldActivity"
                type="text"
              />
              <ValidatedField
                label={translate('b4CarecollectApp.activitySummary.fieldDuration')}
                id="activity-summary-fieldDuration"
                name="fieldDuration"
                data-cy="fieldDuration"
                type="text"
              />
              <ValidatedField
                label={translate('b4CarecollectApp.activitySummary.fieldNumSegments')}
                id="activity-summary-fieldNumSegments"
                name="fieldNumSegments"
                data-cy="fieldNumSegments"
                type="text"
              />
              <ValidatedField
                label={translate('b4CarecollectApp.activitySummary.startTime')}
                id="activity-summary-startTime"
                name="startTime"
                data-cy="startTime"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('b4CarecollectApp.activitySummary.endTime')}
                id="activity-summary-endTime"
                name="endTime"
                data-cy="endTime"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/activity-summary" replace color="info">
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

export default ActivitySummaryUpdate;
