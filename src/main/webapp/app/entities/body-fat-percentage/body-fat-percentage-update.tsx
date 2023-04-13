import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IBodyFatPercentage } from 'app/shared/model/body-fat-percentage.model';
import { getEntity, updateEntity, createEntity, reset } from './body-fat-percentage.reducer';

export const BodyFatPercentageUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const bodyFatPercentageEntity = useAppSelector(state => state.bodyFatPercentage.entity);
  const loading = useAppSelector(state => state.bodyFatPercentage.loading);
  const updating = useAppSelector(state => state.bodyFatPercentage.updating);
  const updateSuccess = useAppSelector(state => state.bodyFatPercentage.updateSuccess);

  const handleClose = () => {
    navigate('/body-fat-percentage');
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
      ...bodyFatPercentageEntity,
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
          ...bodyFatPercentageEntity,
          endTime: convertDateTimeFromServer(bodyFatPercentageEntity.endTime),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="b4CarecollectApp.bodyFatPercentage.home.createOrEditLabel" data-cy="BodyFatPercentageCreateUpdateHeading">
            <Translate contentKey="b4CarecollectApp.bodyFatPercentage.home.createOrEditLabel">Create or edit a BodyFatPercentage</Translate>
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
                  id="body-fat-percentage-id"
                  label={translate('b4CarecollectApp.bodyFatPercentage.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('b4CarecollectApp.bodyFatPercentage.usuarioId')}
                id="body-fat-percentage-usuarioId"
                name="usuarioId"
                data-cy="usuarioId"
                type="text"
              />
              <ValidatedField
                label={translate('b4CarecollectApp.bodyFatPercentage.empresaId')}
                id="body-fat-percentage-empresaId"
                name="empresaId"
                data-cy="empresaId"
                type="text"
              />
              <ValidatedField
                label={translate('b4CarecollectApp.bodyFatPercentage.fieldPorcentage')}
                id="body-fat-percentage-fieldPorcentage"
                name="fieldPorcentage"
                data-cy="fieldPorcentage"
                type="text"
              />
              <ValidatedField
                label={translate('b4CarecollectApp.bodyFatPercentage.endTime')}
                id="body-fat-percentage-endTime"
                name="endTime"
                data-cy="endTime"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/body-fat-percentage" replace color="info">
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

export default BodyFatPercentageUpdate;
