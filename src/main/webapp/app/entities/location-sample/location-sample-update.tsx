import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ILocationSample } from 'app/shared/model/location-sample.model';
import { getEntity, updateEntity, createEntity, reset } from './location-sample.reducer';

export const LocationSampleUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const locationSampleEntity = useAppSelector(state => state.locationSample.entity);
  const loading = useAppSelector(state => state.locationSample.loading);
  const updating = useAppSelector(state => state.locationSample.updating);
  const updateSuccess = useAppSelector(state => state.locationSample.updateSuccess);

  const handleClose = () => {
    navigate('/location-sample');
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
      ...locationSampleEntity,
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
          ...locationSampleEntity,
          startTime: convertDateTimeFromServer(locationSampleEntity.startTime),
          endTime: convertDateTimeFromServer(locationSampleEntity.endTime),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="b4CarecollectApp.locationSample.home.createOrEditLabel" data-cy="LocationSampleCreateUpdateHeading">
            <Translate contentKey="b4CarecollectApp.locationSample.home.createOrEditLabel">Create or edit a LocationSample</Translate>
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
                  id="location-sample-id"
                  label={translate('b4CarecollectApp.locationSample.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('b4CarecollectApp.locationSample.usuarioId')}
                id="location-sample-usuarioId"
                name="usuarioId"
                data-cy="usuarioId"
                type="text"
              />
              <ValidatedField
                label={translate('b4CarecollectApp.locationSample.empresaId')}
                id="location-sample-empresaId"
                name="empresaId"
                data-cy="empresaId"
                type="text"
              />
              <ValidatedField
                label={translate('b4CarecollectApp.locationSample.latitudMin')}
                id="location-sample-latitudMin"
                name="latitudMin"
                data-cy="latitudMin"
                type="text"
              />
              <ValidatedField
                label={translate('b4CarecollectApp.locationSample.longitudMin')}
                id="location-sample-longitudMin"
                name="longitudMin"
                data-cy="longitudMin"
                type="text"
              />
              <ValidatedField
                label={translate('b4CarecollectApp.locationSample.latitudMax')}
                id="location-sample-latitudMax"
                name="latitudMax"
                data-cy="latitudMax"
                type="text"
              />
              <ValidatedField
                label={translate('b4CarecollectApp.locationSample.longitudMax')}
                id="location-sample-longitudMax"
                name="longitudMax"
                data-cy="longitudMax"
                type="text"
              />
              <ValidatedField
                label={translate('b4CarecollectApp.locationSample.accuracy')}
                id="location-sample-accuracy"
                name="accuracy"
                data-cy="accuracy"
                type="text"
              />
              <ValidatedField
                label={translate('b4CarecollectApp.locationSample.altitud')}
                id="location-sample-altitud"
                name="altitud"
                data-cy="altitud"
                type="text"
              />
              <ValidatedField
                label={translate('b4CarecollectApp.locationSample.startTime')}
                id="location-sample-startTime"
                name="startTime"
                data-cy="startTime"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('b4CarecollectApp.locationSample.endTime')}
                id="location-sample-endTime"
                name="endTime"
                data-cy="endTime"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/location-sample" replace color="info">
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

export default LocationSampleUpdate;
