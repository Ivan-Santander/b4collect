import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IFuntionalIndexSummary } from 'app/shared/model/funtional-index-summary.model';
import { getEntity, updateEntity, createEntity, reset } from './funtional-index-summary.reducer';

export const FuntionalIndexSummaryUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const funtionalIndexSummaryEntity = useAppSelector(state => state.funtionalIndexSummary.entity);
  const loading = useAppSelector(state => state.funtionalIndexSummary.loading);
  const updating = useAppSelector(state => state.funtionalIndexSummary.updating);
  const updateSuccess = useAppSelector(state => state.funtionalIndexSummary.updateSuccess);

  const handleClose = () => {
    navigate('/funtional-index-summary');
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
      ...funtionalIndexSummaryEntity,
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
          ...funtionalIndexSummaryEntity,
          startTime: convertDateTimeFromServer(funtionalIndexSummaryEntity.startTime),
          endTime: convertDateTimeFromServer(funtionalIndexSummaryEntity.endTime),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="b4CarecollectApp.funtionalIndexSummary.home.createOrEditLabel" data-cy="FuntionalIndexSummaryCreateUpdateHeading">
            <Translate contentKey="b4CarecollectApp.funtionalIndexSummary.home.createOrEditLabel">
              Create or edit a FuntionalIndexSummary
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
                  id="funtional-index-summary-id"
                  label={translate('b4CarecollectApp.funtionalIndexSummary.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('b4CarecollectApp.funtionalIndexSummary.usuarioId')}
                id="funtional-index-summary-usuarioId"
                name="usuarioId"
                data-cy="usuarioId"
                type="text"
              />
              <ValidatedField
                label={translate('b4CarecollectApp.funtionalIndexSummary.empresaId')}
                id="funtional-index-summary-empresaId"
                name="empresaId"
                data-cy="empresaId"
                type="text"
              />
              <ValidatedField
                label={translate('b4CarecollectApp.funtionalIndexSummary.fieldFuntionalIndexAverage')}
                id="funtional-index-summary-fieldFuntionalIndexAverage"
                name="fieldFuntionalIndexAverage"
                data-cy="fieldFuntionalIndexAverage"
                type="text"
              />
              <ValidatedField
                label={translate('b4CarecollectApp.funtionalIndexSummary.fieldFuntionalIndexMax')}
                id="funtional-index-summary-fieldFuntionalIndexMax"
                name="fieldFuntionalIndexMax"
                data-cy="fieldFuntionalIndexMax"
                type="text"
              />
              <ValidatedField
                label={translate('b4CarecollectApp.funtionalIndexSummary.fieldFuntionalIndexMin')}
                id="funtional-index-summary-fieldFuntionalIndexMin"
                name="fieldFuntionalIndexMin"
                data-cy="fieldFuntionalIndexMin"
                type="text"
              />
              <ValidatedField
                label={translate('b4CarecollectApp.funtionalIndexSummary.startTime')}
                id="funtional-index-summary-startTime"
                name="startTime"
                data-cy="startTime"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('b4CarecollectApp.funtionalIndexSummary.endTime')}
                id="funtional-index-summary-endTime"
                name="endTime"
                data-cy="endTime"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/funtional-index-summary" replace color="info">
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

export default FuntionalIndexSummaryUpdate;
