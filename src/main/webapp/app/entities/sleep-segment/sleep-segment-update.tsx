import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ISleepSegment } from 'app/shared/model/sleep-segment.model';
import { getEntity, updateEntity, createEntity, reset } from './sleep-segment.reducer';

export const SleepSegmentUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const sleepSegmentEntity = useAppSelector(state => state.sleepSegment.entity);
  const loading = useAppSelector(state => state.sleepSegment.loading);
  const updating = useAppSelector(state => state.sleepSegment.updating);
  const updateSuccess = useAppSelector(state => state.sleepSegment.updateSuccess);

  const handleClose = () => {
    navigate('/sleep-segment');
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
      ...sleepSegmentEntity,
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
          ...sleepSegmentEntity,
          startTime: convertDateTimeFromServer(sleepSegmentEntity.startTime),
          endTime: convertDateTimeFromServer(sleepSegmentEntity.endTime),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="b4CarecollectApp.sleepSegment.home.createOrEditLabel" data-cy="SleepSegmentCreateUpdateHeading">
            <Translate contentKey="b4CarecollectApp.sleepSegment.home.createOrEditLabel">Create or edit a SleepSegment</Translate>
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
                  id="sleep-segment-id"
                  label={translate('b4CarecollectApp.sleepSegment.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('b4CarecollectApp.sleepSegment.usuarioId')}
                id="sleep-segment-usuarioId"
                name="usuarioId"
                data-cy="usuarioId"
                type="text"
              />
              <ValidatedField
                label={translate('b4CarecollectApp.sleepSegment.empresaId')}
                id="sleep-segment-empresaId"
                name="empresaId"
                data-cy="empresaId"
                type="text"
              />
              <ValidatedField
                label={translate('b4CarecollectApp.sleepSegment.fieldSleepSegmentType')}
                id="sleep-segment-fieldSleepSegmentType"
                name="fieldSleepSegmentType"
                data-cy="fieldSleepSegmentType"
                type="text"
              />
              <ValidatedField
                label={translate('b4CarecollectApp.sleepSegment.fieldBloodGlucoseSpecimenSource')}
                id="sleep-segment-fieldBloodGlucoseSpecimenSource"
                name="fieldBloodGlucoseSpecimenSource"
                data-cy="fieldBloodGlucoseSpecimenSource"
                type="text"
              />
              <ValidatedField
                label={translate('b4CarecollectApp.sleepSegment.startTime')}
                id="sleep-segment-startTime"
                name="startTime"
                data-cy="startTime"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('b4CarecollectApp.sleepSegment.endTime')}
                id="sleep-segment-endTime"
                name="endTime"
                data-cy="endTime"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/sleep-segment" replace color="info">
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

export default SleepSegmentUpdate;
