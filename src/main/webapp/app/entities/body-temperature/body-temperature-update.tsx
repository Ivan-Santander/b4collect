import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IBodyTemperature } from 'app/shared/model/body-temperature.model';
import { getEntity, updateEntity, createEntity, reset } from './body-temperature.reducer';

export const BodyTemperatureUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const bodyTemperatureEntity = useAppSelector(state => state.bodyTemperature.entity);
  const loading = useAppSelector(state => state.bodyTemperature.loading);
  const updating = useAppSelector(state => state.bodyTemperature.updating);
  const updateSuccess = useAppSelector(state => state.bodyTemperature.updateSuccess);

  const handleClose = () => {
    navigate('/body-temperature');
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
      ...bodyTemperatureEntity,
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
          ...bodyTemperatureEntity,
          endTime: convertDateTimeFromServer(bodyTemperatureEntity.endTime),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="b4CarecollectApp.bodyTemperature.home.createOrEditLabel" data-cy="BodyTemperatureCreateUpdateHeading">
            <Translate contentKey="b4CarecollectApp.bodyTemperature.home.createOrEditLabel">Create or edit a BodyTemperature</Translate>
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
                  id="body-temperature-id"
                  label={translate('b4CarecollectApp.bodyTemperature.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('b4CarecollectApp.bodyTemperature.usuarioId')}
                id="body-temperature-usuarioId"
                name="usuarioId"
                data-cy="usuarioId"
                type="text"
              />
              <ValidatedField
                label={translate('b4CarecollectApp.bodyTemperature.empresaId')}
                id="body-temperature-empresaId"
                name="empresaId"
                data-cy="empresaId"
                type="text"
              />
              <ValidatedField
                label={translate('b4CarecollectApp.bodyTemperature.fieldBodyTemperature')}
                id="body-temperature-fieldBodyTemperature"
                name="fieldBodyTemperature"
                data-cy="fieldBodyTemperature"
                type="text"
              />
              <ValidatedField
                label={translate('b4CarecollectApp.bodyTemperature.fieldBodyTemperatureMeasureLocation')}
                id="body-temperature-fieldBodyTemperatureMeasureLocation"
                name="fieldBodyTemperatureMeasureLocation"
                data-cy="fieldBodyTemperatureMeasureLocation"
                type="text"
              />
              <ValidatedField
                label={translate('b4CarecollectApp.bodyTemperature.endTime')}
                id="body-temperature-endTime"
                name="endTime"
                data-cy="endTime"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/body-temperature" replace color="info">
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

export default BodyTemperatureUpdate;
