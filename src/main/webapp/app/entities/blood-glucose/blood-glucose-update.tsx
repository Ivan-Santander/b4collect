import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IBloodGlucose } from 'app/shared/model/blood-glucose.model';
import { getEntity, updateEntity, createEntity, reset } from './blood-glucose.reducer';

export const BloodGlucoseUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const bloodGlucoseEntity = useAppSelector(state => state.bloodGlucose.entity);
  const loading = useAppSelector(state => state.bloodGlucose.loading);
  const updating = useAppSelector(state => state.bloodGlucose.updating);
  const updateSuccess = useAppSelector(state => state.bloodGlucose.updateSuccess);

  const handleClose = () => {
    navigate('/blood-glucose');
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
      ...bloodGlucoseEntity,
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
          ...bloodGlucoseEntity,
          endTime: convertDateTimeFromServer(bloodGlucoseEntity.endTime),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="b4CarecollectApp.bloodGlucose.home.createOrEditLabel" data-cy="BloodGlucoseCreateUpdateHeading">
            <Translate contentKey="b4CarecollectApp.bloodGlucose.home.createOrEditLabel">Create or edit a BloodGlucose</Translate>
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
                  id="blood-glucose-id"
                  label={translate('b4CarecollectApp.bloodGlucose.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('b4CarecollectApp.bloodGlucose.usuarioId')}
                id="blood-glucose-usuarioId"
                name="usuarioId"
                data-cy="usuarioId"
                type="text"
              />
              <ValidatedField
                label={translate('b4CarecollectApp.bloodGlucose.empresaId')}
                id="blood-glucose-empresaId"
                name="empresaId"
                data-cy="empresaId"
                type="text"
              />
              <ValidatedField
                label={translate('b4CarecollectApp.bloodGlucose.fieldBloodGlucoseLevel')}
                id="blood-glucose-fieldBloodGlucoseLevel"
                name="fieldBloodGlucoseLevel"
                data-cy="fieldBloodGlucoseLevel"
                type="text"
              />
              <ValidatedField
                label={translate('b4CarecollectApp.bloodGlucose.fieldTemporalRelationToMeal')}
                id="blood-glucose-fieldTemporalRelationToMeal"
                name="fieldTemporalRelationToMeal"
                data-cy="fieldTemporalRelationToMeal"
                type="text"
              />
              <ValidatedField
                label={translate('b4CarecollectApp.bloodGlucose.fieldMealType')}
                id="blood-glucose-fieldMealType"
                name="fieldMealType"
                data-cy="fieldMealType"
                type="text"
              />
              <ValidatedField
                label={translate('b4CarecollectApp.bloodGlucose.fieldTemporalRelationToSleep')}
                id="blood-glucose-fieldTemporalRelationToSleep"
                name="fieldTemporalRelationToSleep"
                data-cy="fieldTemporalRelationToSleep"
                type="text"
              />
              <ValidatedField
                label={translate('b4CarecollectApp.bloodGlucose.fieldBloodGlucoseSpecimenSource')}
                id="blood-glucose-fieldBloodGlucoseSpecimenSource"
                name="fieldBloodGlucoseSpecimenSource"
                data-cy="fieldBloodGlucoseSpecimenSource"
                type="text"
              />
              <ValidatedField
                label={translate('b4CarecollectApp.bloodGlucose.endTime')}
                id="blood-glucose-endTime"
                name="endTime"
                data-cy="endTime"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/blood-glucose" replace color="info">
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

export default BloodGlucoseUpdate;
