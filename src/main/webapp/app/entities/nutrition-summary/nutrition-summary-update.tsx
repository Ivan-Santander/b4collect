import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { INutritionSummary } from 'app/shared/model/nutrition-summary.model';
import { getEntity, updateEntity, createEntity, reset } from './nutrition-summary.reducer';

export const NutritionSummaryUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const nutritionSummaryEntity = useAppSelector(state => state.nutritionSummary.entity);
  const loading = useAppSelector(state => state.nutritionSummary.loading);
  const updating = useAppSelector(state => state.nutritionSummary.updating);
  const updateSuccess = useAppSelector(state => state.nutritionSummary.updateSuccess);

  const handleClose = () => {
    navigate('/nutrition-summary');
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
      ...nutritionSummaryEntity,
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
          ...nutritionSummaryEntity,
          startTime: convertDateTimeFromServer(nutritionSummaryEntity.startTime),
          endTime: convertDateTimeFromServer(nutritionSummaryEntity.endTime),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="b4CarecollectApp.nutritionSummary.home.createOrEditLabel" data-cy="NutritionSummaryCreateUpdateHeading">
            <Translate contentKey="b4CarecollectApp.nutritionSummary.home.createOrEditLabel">Create or edit a NutritionSummary</Translate>
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
                  id="nutrition-summary-id"
                  label={translate('b4CarecollectApp.nutritionSummary.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('b4CarecollectApp.nutritionSummary.usuarioId')}
                id="nutrition-summary-usuarioId"
                name="usuarioId"
                data-cy="usuarioId"
                type="text"
              />
              <ValidatedField
                label={translate('b4CarecollectApp.nutritionSummary.empresaId')}
                id="nutrition-summary-empresaId"
                name="empresaId"
                data-cy="empresaId"
                type="text"
              />
              <ValidatedField
                label={translate('b4CarecollectApp.nutritionSummary.mealType')}
                id="nutrition-summary-mealType"
                name="mealType"
                data-cy="mealType"
                type="textarea"
              />
              <ValidatedField
                label={translate('b4CarecollectApp.nutritionSummary.nutrient')}
                id="nutrition-summary-nutrient"
                name="nutrient"
                data-cy="nutrient"
                type="textarea"
              />
              <ValidatedField
                label={translate('b4CarecollectApp.nutritionSummary.startTime')}
                id="nutrition-summary-startTime"
                name="startTime"
                data-cy="startTime"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('b4CarecollectApp.nutritionSummary.endTime')}
                id="nutrition-summary-endTime"
                name="endTime"
                data-cy="endTime"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/nutrition-summary" replace color="info">
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

export default NutritionSummaryUpdate;
