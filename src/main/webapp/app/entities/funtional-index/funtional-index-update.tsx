import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IFuntionalIndex } from 'app/shared/model/funtional-index.model';
import { getEntity, updateEntity, createEntity, reset } from './funtional-index.reducer';

export const FuntionalIndexUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const funtionalIndexEntity = useAppSelector(state => state.funtionalIndex.entity);
  const loading = useAppSelector(state => state.funtionalIndex.loading);
  const updating = useAppSelector(state => state.funtionalIndex.updating);
  const updateSuccess = useAppSelector(state => state.funtionalIndex.updateSuccess);

  const handleClose = () => {
    navigate('/funtional-index');
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

    const entity = {
      ...funtionalIndexEntity,
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
        }
      : {
          ...funtionalIndexEntity,
          startTime: convertDateTimeFromServer(funtionalIndexEntity.startTime),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="b4CarecollectApp.funtionalIndex.home.createOrEditLabel" data-cy="FuntionalIndexCreateUpdateHeading">
            <Translate contentKey="b4CarecollectApp.funtionalIndex.home.createOrEditLabel">Create or edit a FuntionalIndex</Translate>
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
                  id="funtional-index-id"
                  label={translate('b4CarecollectApp.funtionalIndex.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('b4CarecollectApp.funtionalIndex.usuarioId')}
                id="funtional-index-usuarioId"
                name="usuarioId"
                data-cy="usuarioId"
                type="text"
              />
              <ValidatedField
                label={translate('b4CarecollectApp.funtionalIndex.empresaId')}
                id="funtional-index-empresaId"
                name="empresaId"
                data-cy="empresaId"
                type="text"
              />
              <ValidatedField
                label={translate('b4CarecollectApp.funtionalIndex.bodyHealthScore')}
                id="funtional-index-bodyHealthScore"
                name="bodyHealthScore"
                data-cy="bodyHealthScore"
                type="text"
              />
              <ValidatedField
                label={translate('b4CarecollectApp.funtionalIndex.mentalHealthScore')}
                id="funtional-index-mentalHealthScore"
                name="mentalHealthScore"
                data-cy="mentalHealthScore"
                type="text"
              />
              <ValidatedField
                label={translate('b4CarecollectApp.funtionalIndex.sleepHealthScore')}
                id="funtional-index-sleepHealthScore"
                name="sleepHealthScore"
                data-cy="sleepHealthScore"
                type="text"
              />
              <ValidatedField
                label={translate('b4CarecollectApp.funtionalIndex.funtionalIndex')}
                id="funtional-index-funtionalIndex"
                name="funtionalIndex"
                data-cy="funtionalIndex"
                type="text"
              />
              <ValidatedField
                label={translate('b4CarecollectApp.funtionalIndex.alarmRiskScore')}
                id="funtional-index-alarmRiskScore"
                name="alarmRiskScore"
                data-cy="alarmRiskScore"
                type="text"
              />
              <ValidatedField
                label={translate('b4CarecollectApp.funtionalIndex.startTime')}
                id="funtional-index-startTime"
                name="startTime"
                data-cy="startTime"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/funtional-index" replace color="info">
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

export default FuntionalIndexUpdate;
