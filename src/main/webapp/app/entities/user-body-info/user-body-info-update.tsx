import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IUserBodyInfo } from 'app/shared/model/user-body-info.model';
import { getEntity, updateEntity, createEntity, reset } from './user-body-info.reducer';

export const UserBodyInfoUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const userBodyInfoEntity = useAppSelector(state => state.userBodyInfo.entity);
  const loading = useAppSelector(state => state.userBodyInfo.loading);
  const updating = useAppSelector(state => state.userBodyInfo.updating);
  const updateSuccess = useAppSelector(state => state.userBodyInfo.updateSuccess);

  const handleClose = () => {
    navigate('/user-body-info');
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
    const entity = {
      ...userBodyInfoEntity,
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
      ? {}
      : {
          ...userBodyInfoEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="b4CarecollectApp.userBodyInfo.home.createOrEditLabel" data-cy="UserBodyInfoCreateUpdateHeading">
            <Translate contentKey="b4CarecollectApp.userBodyInfo.home.createOrEditLabel">Create or edit a UserBodyInfo</Translate>
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
                  id="user-body-info-id"
                  label={translate('b4CarecollectApp.userBodyInfo.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('b4CarecollectApp.userBodyInfo.usuarioId')}
                id="user-body-info-usuarioId"
                name="usuarioId"
                data-cy="usuarioId"
                type="text"
              />
              <ValidatedField
                label={translate('b4CarecollectApp.userBodyInfo.empresaId')}
                id="user-body-info-empresaId"
                name="empresaId"
                data-cy="empresaId"
                type="text"
              />
              <ValidatedField
                label={translate('b4CarecollectApp.userBodyInfo.waistCircumference')}
                id="user-body-info-waistCircumference"
                name="waistCircumference"
                data-cy="waistCircumference"
                type="text"
              />
              <ValidatedField
                label={translate('b4CarecollectApp.userBodyInfo.hipCircumference')}
                id="user-body-info-hipCircumference"
                name="hipCircumference"
                data-cy="hipCircumference"
                type="text"
              />
              <ValidatedField
                label={translate('b4CarecollectApp.userBodyInfo.chestCircumference')}
                id="user-body-info-chestCircumference"
                name="chestCircumference"
                data-cy="chestCircumference"
                type="text"
              />
              <ValidatedField
                label={translate('b4CarecollectApp.userBodyInfo.boneCompositionPercentaje')}
                id="user-body-info-boneCompositionPercentaje"
                name="boneCompositionPercentaje"
                data-cy="boneCompositionPercentaje"
                type="text"
              />
              <ValidatedField
                label={translate('b4CarecollectApp.userBodyInfo.muscleCompositionPercentaje')}
                id="user-body-info-muscleCompositionPercentaje"
                name="muscleCompositionPercentaje"
                data-cy="muscleCompositionPercentaje"
                type="text"
              />
              <ValidatedField
                label={translate('b4CarecollectApp.userBodyInfo.smoker')}
                id="user-body-info-smoker"
                name="smoker"
                data-cy="smoker"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('b4CarecollectApp.userBodyInfo.waightKg')}
                id="user-body-info-waightKg"
                name="waightKg"
                data-cy="waightKg"
                type="text"
              />
              <ValidatedField
                label={translate('b4CarecollectApp.userBodyInfo.heightCm')}
                id="user-body-info-heightCm"
                name="heightCm"
                data-cy="heightCm"
                type="text"
              />
              <ValidatedField
                label={translate('b4CarecollectApp.userBodyInfo.bodyHealthScore')}
                id="user-body-info-bodyHealthScore"
                name="bodyHealthScore"
                data-cy="bodyHealthScore"
                type="text"
              />
              <ValidatedField
                label={translate('b4CarecollectApp.userBodyInfo.cardiovascularRisk')}
                id="user-body-info-cardiovascularRisk"
                name="cardiovascularRisk"
                data-cy="cardiovascularRisk"
                type="text"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/user-body-info" replace color="info">
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

export default UserBodyInfoUpdate;
