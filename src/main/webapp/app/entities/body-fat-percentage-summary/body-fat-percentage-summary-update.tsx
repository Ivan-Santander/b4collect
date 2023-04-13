import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IBodyFatPercentageSummary } from 'app/shared/model/body-fat-percentage-summary.model';
import { getEntity, updateEntity, createEntity, reset } from './body-fat-percentage-summary.reducer';

export const BodyFatPercentageSummaryUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const bodyFatPercentageSummaryEntity = useAppSelector(state => state.bodyFatPercentageSummary.entity);
  const loading = useAppSelector(state => state.bodyFatPercentageSummary.loading);
  const updating = useAppSelector(state => state.bodyFatPercentageSummary.updating);
  const updateSuccess = useAppSelector(state => state.bodyFatPercentageSummary.updateSuccess);

  const handleClose = () => {
    navigate('/body-fat-percentage-summary');
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
      ...bodyFatPercentageSummaryEntity,
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
          ...bodyFatPercentageSummaryEntity,
          startTime: convertDateTimeFromServer(bodyFatPercentageSummaryEntity.startTime),
          endTime: convertDateTimeFromServer(bodyFatPercentageSummaryEntity.endTime),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="b4CarecollectApp.bodyFatPercentageSummary.home.createOrEditLabel" data-cy="BodyFatPercentageSummaryCreateUpdateHeading">
            <Translate contentKey="b4CarecollectApp.bodyFatPercentageSummary.home.createOrEditLabel">
              Create or edit a BodyFatPercentageSummary
            </Translate>
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
                  id="body-fat-percentage-summary-id"
                  label={translate('b4CarecollectApp.bodyFatPercentageSummary.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('b4CarecollectApp.bodyFatPercentageSummary.usuarioId')}
                id="body-fat-percentage-summary-usuarioId"
                name="usuarioId"
                data-cy="usuarioId"
                type="text"
              />
              <ValidatedField
                label={translate('b4CarecollectApp.bodyFatPercentageSummary.empresaId')}
                id="body-fat-percentage-summary-empresaId"
                name="empresaId"
                data-cy="empresaId"
                type="text"
              />
              <ValidatedField
                label={translate('b4CarecollectApp.bodyFatPercentageSummary.fieldAverage')}
                id="body-fat-percentage-summary-fieldAverage"
                name="fieldAverage"
                data-cy="fieldAverage"
                type="text"
              />
              <ValidatedField
                label={translate('b4CarecollectApp.bodyFatPercentageSummary.fieldMax')}
                id="body-fat-percentage-summary-fieldMax"
                name="fieldMax"
                data-cy="fieldMax"
                type="text"
              />
              <ValidatedField
                label={translate('b4CarecollectApp.bodyFatPercentageSummary.fieldMin')}
                id="body-fat-percentage-summary-fieldMin"
                name="fieldMin"
                data-cy="fieldMin"
                type="text"
              />
              <ValidatedField
                label={translate('b4CarecollectApp.bodyFatPercentageSummary.startTime')}
                id="body-fat-percentage-summary-startTime"
                name="startTime"
                data-cy="startTime"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('b4CarecollectApp.bodyFatPercentageSummary.endTime')}
                id="body-fat-percentage-summary-endTime"
                name="endTime"
                data-cy="endTime"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/body-fat-percentage-summary" replace color="info">
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

export default BodyFatPercentageSummaryUpdate;
