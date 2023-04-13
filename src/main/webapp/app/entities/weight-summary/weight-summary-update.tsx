import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IWeightSummary } from 'app/shared/model/weight-summary.model';
import { getEntity, updateEntity, createEntity, reset } from './weight-summary.reducer';

export const WeightSummaryUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const weightSummaryEntity = useAppSelector(state => state.weightSummary.entity);
  const loading = useAppSelector(state => state.weightSummary.loading);
  const updating = useAppSelector(state => state.weightSummary.updating);
  const updateSuccess = useAppSelector(state => state.weightSummary.updateSuccess);

  const handleClose = () => {
    navigate('/weight-summary');
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
      ...weightSummaryEntity,
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
          ...weightSummaryEntity,
          startTime: convertDateTimeFromServer(weightSummaryEntity.startTime),
          endTime: convertDateTimeFromServer(weightSummaryEntity.endTime),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="b4CarecollectApp.weightSummary.home.createOrEditLabel" data-cy="WeightSummaryCreateUpdateHeading">
            <Translate contentKey="b4CarecollectApp.weightSummary.home.createOrEditLabel">Create or edit a WeightSummary</Translate>
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
                  id="weight-summary-id"
                  label={translate('b4CarecollectApp.weightSummary.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('b4CarecollectApp.weightSummary.usuarioId')}
                id="weight-summary-usuarioId"
                name="usuarioId"
                data-cy="usuarioId"
                type="text"
              />
              <ValidatedField
                label={translate('b4CarecollectApp.weightSummary.empresaId')}
                id="weight-summary-empresaId"
                name="empresaId"
                data-cy="empresaId"
                type="text"
              />
              <ValidatedField
                label={translate('b4CarecollectApp.weightSummary.fieldAverage')}
                id="weight-summary-fieldAverage"
                name="fieldAverage"
                data-cy="fieldAverage"
                type="text"
              />
              <ValidatedField
                label={translate('b4CarecollectApp.weightSummary.fieldMax')}
                id="weight-summary-fieldMax"
                name="fieldMax"
                data-cy="fieldMax"
                type="text"
              />
              <ValidatedField
                label={translate('b4CarecollectApp.weightSummary.fieldMin')}
                id="weight-summary-fieldMin"
                name="fieldMin"
                data-cy="fieldMin"
                type="text"
              />
              <ValidatedField
                label={translate('b4CarecollectApp.weightSummary.startTime')}
                id="weight-summary-startTime"
                name="startTime"
                data-cy="startTime"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('b4CarecollectApp.weightSummary.endTime')}
                id="weight-summary-endTime"
                name="endTime"
                data-cy="endTime"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/weight-summary" replace color="info">
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

export default WeightSummaryUpdate;
