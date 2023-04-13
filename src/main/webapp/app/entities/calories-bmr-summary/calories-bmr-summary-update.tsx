import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ICaloriesBmrSummary } from 'app/shared/model/calories-bmr-summary.model';
import { getEntity, updateEntity, createEntity, reset } from './calories-bmr-summary.reducer';

export const CaloriesBmrSummaryUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const caloriesBmrSummaryEntity = useAppSelector(state => state.caloriesBmrSummary.entity);
  const loading = useAppSelector(state => state.caloriesBmrSummary.loading);
  const updating = useAppSelector(state => state.caloriesBmrSummary.updating);
  const updateSuccess = useAppSelector(state => state.caloriesBmrSummary.updateSuccess);

  const handleClose = () => {
    navigate('/calories-bmr-summary');
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
      ...caloriesBmrSummaryEntity,
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
          ...caloriesBmrSummaryEntity,
          startTime: convertDateTimeFromServer(caloriesBmrSummaryEntity.startTime),
          endTime: convertDateTimeFromServer(caloriesBmrSummaryEntity.endTime),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="b4CarecollectApp.caloriesBmrSummary.home.createOrEditLabel" data-cy="CaloriesBmrSummaryCreateUpdateHeading">
            <Translate contentKey="b4CarecollectApp.caloriesBmrSummary.home.createOrEditLabel">
              Create or edit a CaloriesBmrSummary
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
                  id="calories-bmr-summary-id"
                  label={translate('b4CarecollectApp.caloriesBmrSummary.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('b4CarecollectApp.caloriesBmrSummary.usuarioId')}
                id="calories-bmr-summary-usuarioId"
                name="usuarioId"
                data-cy="usuarioId"
                type="text"
              />
              <ValidatedField
                label={translate('b4CarecollectApp.caloriesBmrSummary.empresaId')}
                id="calories-bmr-summary-empresaId"
                name="empresaId"
                data-cy="empresaId"
                type="text"
              />
              <ValidatedField
                label={translate('b4CarecollectApp.caloriesBmrSummary.fieldAverage')}
                id="calories-bmr-summary-fieldAverage"
                name="fieldAverage"
                data-cy="fieldAverage"
                type="text"
              />
              <ValidatedField
                label={translate('b4CarecollectApp.caloriesBmrSummary.fieldMax')}
                id="calories-bmr-summary-fieldMax"
                name="fieldMax"
                data-cy="fieldMax"
                type="text"
              />
              <ValidatedField
                label={translate('b4CarecollectApp.caloriesBmrSummary.fieldMin')}
                id="calories-bmr-summary-fieldMin"
                name="fieldMin"
                data-cy="fieldMin"
                type="text"
              />
              <ValidatedField
                label={translate('b4CarecollectApp.caloriesBmrSummary.startTime')}
                id="calories-bmr-summary-startTime"
                name="startTime"
                data-cy="startTime"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('b4CarecollectApp.caloriesBmrSummary.endTime')}
                id="calories-bmr-summary-endTime"
                name="endTime"
                data-cy="endTime"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/calories-bmr-summary" replace color="info">
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

export default CaloriesBmrSummaryUpdate;
