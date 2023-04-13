import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { INutrition } from 'app/shared/model/nutrition.model';
import { getEntity, updateEntity, createEntity, reset } from './nutrition.reducer';

export const NutritionUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const nutritionEntity = useAppSelector(state => state.nutrition.entity);
  const loading = useAppSelector(state => state.nutrition.loading);
  const updating = useAppSelector(state => state.nutrition.updating);
  const updateSuccess = useAppSelector(state => state.nutrition.updateSuccess);

  const handleClose = () => {
    navigate('/nutrition');
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
      ...nutritionEntity,
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
          ...nutritionEntity,
          endTime: convertDateTimeFromServer(nutritionEntity.endTime),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="b4CarecollectApp.nutrition.home.createOrEditLabel" data-cy="NutritionCreateUpdateHeading">
            <Translate contentKey="b4CarecollectApp.nutrition.home.createOrEditLabel">Create or edit a Nutrition</Translate>
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
                  id="nutrition-id"
                  label={translate('b4CarecollectApp.nutrition.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('b4CarecollectApp.nutrition.usuarioId')}
                id="nutrition-usuarioId"
                name="usuarioId"
                data-cy="usuarioId"
                type="text"
              />
              <ValidatedField
                label={translate('b4CarecollectApp.nutrition.empresaId')}
                id="nutrition-empresaId"
                name="empresaId"
                data-cy="empresaId"
                type="text"
              />
              <ValidatedField
                label={translate('b4CarecollectApp.nutrition.mealType')}
                id="nutrition-mealType"
                name="mealType"
                data-cy="mealType"
                type="text"
              />
              <ValidatedField
                label={translate('b4CarecollectApp.nutrition.food')}
                id="nutrition-food"
                name="food"
                data-cy="food"
                type="textarea"
              />
              <ValidatedField
                label={translate('b4CarecollectApp.nutrition.nutrients')}
                id="nutrition-nutrients"
                name="nutrients"
                data-cy="nutrients"
                type="textarea"
              />
              <ValidatedField
                label={translate('b4CarecollectApp.nutrition.endTime')}
                id="nutrition-endTime"
                name="endTime"
                data-cy="endTime"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/nutrition" replace color="info">
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

export default NutritionUpdate;
