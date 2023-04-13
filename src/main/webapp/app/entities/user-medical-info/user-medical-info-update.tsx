import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IUserMedicalInfo } from 'app/shared/model/user-medical-info.model';
import { getEntity, updateEntity, createEntity, reset } from './user-medical-info.reducer';

export const UserMedicalInfoUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const userMedicalInfoEntity = useAppSelector(state => state.userMedicalInfo.entity);
  const loading = useAppSelector(state => state.userMedicalInfo.loading);
  const updating = useAppSelector(state => state.userMedicalInfo.updating);
  const updateSuccess = useAppSelector(state => state.userMedicalInfo.updateSuccess);

  const handleClose = () => {
    navigate('/user-medical-info');
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
      ...userMedicalInfoEntity,
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
          ...userMedicalInfoEntity,
          endTime: convertDateTimeFromServer(userMedicalInfoEntity.endTime),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="b4CarecollectApp.userMedicalInfo.home.createOrEditLabel" data-cy="UserMedicalInfoCreateUpdateHeading">
            <Translate contentKey="b4CarecollectApp.userMedicalInfo.home.createOrEditLabel">Create or edit a UserMedicalInfo</Translate>
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
                  id="user-medical-info-id"
                  label={translate('b4CarecollectApp.userMedicalInfo.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('b4CarecollectApp.userMedicalInfo.usuarioId')}
                id="user-medical-info-usuarioId"
                name="usuarioId"
                data-cy="usuarioId"
                type="text"
              />
              <ValidatedField
                label={translate('b4CarecollectApp.userMedicalInfo.empresaId')}
                id="user-medical-info-empresaId"
                name="empresaId"
                data-cy="empresaId"
                type="text"
              />
              <ValidatedField
                label={translate('b4CarecollectApp.userMedicalInfo.hypertension')}
                id="user-medical-info-hypertension"
                name="hypertension"
                data-cy="hypertension"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('b4CarecollectApp.userMedicalInfo.highGlucose')}
                id="user-medical-info-highGlucose"
                name="highGlucose"
                data-cy="highGlucose"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('b4CarecollectApp.userMedicalInfo.hiabetes')}
                id="user-medical-info-hiabetes"
                name="hiabetes"
                data-cy="hiabetes"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('b4CarecollectApp.userMedicalInfo.totalCholesterol')}
                id="user-medical-info-totalCholesterol"
                name="totalCholesterol"
                data-cy="totalCholesterol"
                type="text"
              />
              <ValidatedField
                label={translate('b4CarecollectApp.userMedicalInfo.hdlCholesterol')}
                id="user-medical-info-hdlCholesterol"
                name="hdlCholesterol"
                data-cy="hdlCholesterol"
                type="text"
              />
              <ValidatedField
                label={translate('b4CarecollectApp.userMedicalInfo.medicalMainCondition')}
                id="user-medical-info-medicalMainCondition"
                name="medicalMainCondition"
                data-cy="medicalMainCondition"
                type="text"
              />
              <ValidatedField
                label={translate('b4CarecollectApp.userMedicalInfo.medicalSecundaryCondition')}
                id="user-medical-info-medicalSecundaryCondition"
                name="medicalSecundaryCondition"
                data-cy="medicalSecundaryCondition"
                type="text"
              />
              <ValidatedField
                label={translate('b4CarecollectApp.userMedicalInfo.medicalMainMedication')}
                id="user-medical-info-medicalMainMedication"
                name="medicalMainMedication"
                data-cy="medicalMainMedication"
                type="text"
              />
              <ValidatedField
                label={translate('b4CarecollectApp.userMedicalInfo.medicalSecundaryMedication')}
                id="user-medical-info-medicalSecundaryMedication"
                name="medicalSecundaryMedication"
                data-cy="medicalSecundaryMedication"
                type="text"
              />
              <ValidatedField
                label={translate('b4CarecollectApp.userMedicalInfo.medicalScore')}
                id="user-medical-info-medicalScore"
                name="medicalScore"
                data-cy="medicalScore"
                type="text"
              />
              <ValidatedField
                label={translate('b4CarecollectApp.userMedicalInfo.endTime')}
                id="user-medical-info-endTime"
                name="endTime"
                data-cy="endTime"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/user-medical-info" replace color="info">
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

export default UserMedicalInfoUpdate;
