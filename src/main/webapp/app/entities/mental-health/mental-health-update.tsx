import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IMentalHealth } from 'app/shared/model/mental-health.model';
import { getEntity, updateEntity, createEntity, reset } from './mental-health.reducer';

export const MentalHealthUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const mentalHealthEntity = useAppSelector(state => state.mentalHealth.entity);
  const loading = useAppSelector(state => state.mentalHealth.loading);
  const updating = useAppSelector(state => state.mentalHealth.updating);
  const updateSuccess = useAppSelector(state => state.mentalHealth.updateSuccess);

  const handleClose = () => {
    navigate('/mental-health');
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
    values.startDate = convertDateTimeToServer(values.startDate);
    values.endDate = convertDateTimeToServer(values.endDate);

    const entity = {
      ...mentalHealthEntity,
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
          startDate: displayDefaultDateTime(),
          endDate: displayDefaultDateTime(),
        }
      : {
          ...mentalHealthEntity,
          startDate: convertDateTimeFromServer(mentalHealthEntity.startDate),
          endDate: convertDateTimeFromServer(mentalHealthEntity.endDate),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="b4CarecollectApp.mentalHealth.home.createOrEditLabel" data-cy="MentalHealthCreateUpdateHeading">
            <Translate contentKey="b4CarecollectApp.mentalHealth.home.createOrEditLabel">Create or edit a MentalHealth</Translate>
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
                  id="mental-health-id"
                  label={translate('b4CarecollectApp.mentalHealth.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('b4CarecollectApp.mentalHealth.usuarioId')}
                id="mental-health-usuarioId"
                name="usuarioId"
                data-cy="usuarioId"
                type="text"
              />
              <ValidatedField
                label={translate('b4CarecollectApp.mentalHealth.empresaId')}
                id="mental-health-empresaId"
                name="empresaId"
                data-cy="empresaId"
                type="text"
              />
              <ValidatedField
                label={translate('b4CarecollectApp.mentalHealth.emotionDescription')}
                id="mental-health-emotionDescription"
                name="emotionDescription"
                data-cy="emotionDescription"
                type="text"
              />
              <ValidatedField
                label={translate('b4CarecollectApp.mentalHealth.emotionValue')}
                id="mental-health-emotionValue"
                name="emotionValue"
                data-cy="emotionValue"
                type="text"
              />
              <ValidatedField
                label={translate('b4CarecollectApp.mentalHealth.startDate')}
                id="mental-health-startDate"
                name="startDate"
                data-cy="startDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('b4CarecollectApp.mentalHealth.endDate')}
                id="mental-health-endDate"
                name="endDate"
                data-cy="endDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('b4CarecollectApp.mentalHealth.mentalHealthScore')}
                id="mental-health-mentalHealthScore"
                name="mentalHealthScore"
                data-cy="mentalHealthScore"
                type="text"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/mental-health" replace color="info">
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

export default MentalHealthUpdate;
