import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ISleepScores } from 'app/shared/model/sleep-scores.model';
import { getEntity, updateEntity, createEntity, reset } from './sleep-scores.reducer';

export const SleepScoresUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const sleepScoresEntity = useAppSelector(state => state.sleepScores.entity);
  const loading = useAppSelector(state => state.sleepScores.loading);
  const updating = useAppSelector(state => state.sleepScores.updating);
  const updateSuccess = useAppSelector(state => state.sleepScores.updateSuccess);

  const handleClose = () => {
    navigate('/sleep-scores');
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
    const entity = {
      ...sleepScoresEntity,
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
      ? {}
      : {
          ...sleepScoresEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="b4CarecollectApp.sleepScores.home.createOrEditLabel" data-cy="SleepScoresCreateUpdateHeading">
            <Translate contentKey="b4CarecollectApp.sleepScores.home.createOrEditLabel">Create or edit a SleepScores</Translate>
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
                  id="sleep-scores-id"
                  label={translate('b4CarecollectApp.sleepScores.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('b4CarecollectApp.sleepScores.usuarioId')}
                id="sleep-scores-usuarioId"
                name="usuarioId"
                data-cy="usuarioId"
                type="text"
              />
              <ValidatedField
                label={translate('b4CarecollectApp.sleepScores.empresaId')}
                id="sleep-scores-empresaId"
                name="empresaId"
                data-cy="empresaId"
                type="text"
              />
              <ValidatedField
                label={translate('b4CarecollectApp.sleepScores.sleepQualityRatingScore')}
                id="sleep-scores-sleepQualityRatingScore"
                name="sleepQualityRatingScore"
                data-cy="sleepQualityRatingScore"
                type="text"
              />
              <ValidatedField
                label={translate('b4CarecollectApp.sleepScores.sleepEfficiencyScore')}
                id="sleep-scores-sleepEfficiencyScore"
                name="sleepEfficiencyScore"
                data-cy="sleepEfficiencyScore"
                type="text"
              />
              <ValidatedField
                label={translate('b4CarecollectApp.sleepScores.sleepGooalSecondsScore')}
                id="sleep-scores-sleepGooalSecondsScore"
                name="sleepGooalSecondsScore"
                data-cy="sleepGooalSecondsScore"
                type="text"
              />
              <ValidatedField
                label={translate('b4CarecollectApp.sleepScores.sleepContinuityScore')}
                id="sleep-scores-sleepContinuityScore"
                name="sleepContinuityScore"
                data-cy="sleepContinuityScore"
                type="text"
              />
              <ValidatedField
                label={translate('b4CarecollectApp.sleepScores.sleepContinuityRating')}
                id="sleep-scores-sleepContinuityRating"
                name="sleepContinuityRating"
                data-cy="sleepContinuityRating"
                type="text"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/sleep-scores" replace color="info">
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

export default SleepScoresUpdate;
