import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IPoweSample } from 'app/shared/model/powe-sample.model';
import { getEntity, updateEntity, createEntity, reset } from './powe-sample.reducer';

export const PoweSampleUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const poweSampleEntity = useAppSelector(state => state.poweSample.entity);
  const loading = useAppSelector(state => state.poweSample.loading);
  const updating = useAppSelector(state => state.poweSample.updating);
  const updateSuccess = useAppSelector(state => state.poweSample.updateSuccess);

  const handleClose = () => {
    navigate('/powe-sample');
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
      ...poweSampleEntity,
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
          ...poweSampleEntity,
          startTime: convertDateTimeFromServer(poweSampleEntity.startTime),
          endTime: convertDateTimeFromServer(poweSampleEntity.endTime),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="b4CarecollectApp.poweSample.home.createOrEditLabel" data-cy="PoweSampleCreateUpdateHeading">
            <Translate contentKey="b4CarecollectApp.poweSample.home.createOrEditLabel">Create or edit a PoweSample</Translate>
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
                  id="powe-sample-id"
                  label={translate('b4CarecollectApp.poweSample.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('b4CarecollectApp.poweSample.usuarioId')}
                id="powe-sample-usuarioId"
                name="usuarioId"
                data-cy="usuarioId"
                type="text"
              />
              <ValidatedField
                label={translate('b4CarecollectApp.poweSample.empresaId')}
                id="powe-sample-empresaId"
                name="empresaId"
                data-cy="empresaId"
                type="text"
              />
              <ValidatedField
                label={translate('b4CarecollectApp.poweSample.vatios')}
                id="powe-sample-vatios"
                name="vatios"
                data-cy="vatios"
                type="text"
              />
              <ValidatedField
                label={translate('b4CarecollectApp.poweSample.startTime')}
                id="powe-sample-startTime"
                name="startTime"
                data-cy="startTime"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('b4CarecollectApp.poweSample.endTime')}
                id="powe-sample-endTime"
                name="endTime"
                data-cy="endTime"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/powe-sample" replace color="info">
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

export default PoweSampleUpdate;
