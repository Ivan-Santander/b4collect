import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IUserDemographics } from 'app/shared/model/user-demographics.model';
import { getEntity, updateEntity, createEntity, reset } from './user-demographics.reducer';

export const UserDemographicsUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const userDemographicsEntity = useAppSelector(state => state.userDemographics.entity);
  const loading = useAppSelector(state => state.userDemographics.loading);
  const updating = useAppSelector(state => state.userDemographics.updating);
  const updateSuccess = useAppSelector(state => state.userDemographics.updateSuccess);

  const handleClose = () => {
    navigate('/user-demographics');
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
      ...userDemographicsEntity,
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
          ...userDemographicsEntity,
          endTime: convertDateTimeFromServer(userDemographicsEntity.endTime),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="b4CarecollectApp.userDemographics.home.createOrEditLabel" data-cy="UserDemographicsCreateUpdateHeading">
            <Translate contentKey="b4CarecollectApp.userDemographics.home.createOrEditLabel">Create or edit a UserDemographics</Translate>
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
                  id="user-demographics-id"
                  label={translate('b4CarecollectApp.userDemographics.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('b4CarecollectApp.userDemographics.usuarioId')}
                id="user-demographics-usuarioId"
                name="usuarioId"
                data-cy="usuarioId"
                type="text"
              />
              <ValidatedField
                label={translate('b4CarecollectApp.userDemographics.empresaId')}
                id="user-demographics-empresaId"
                name="empresaId"
                data-cy="empresaId"
                type="text"
              />
              <ValidatedField
                label={translate('b4CarecollectApp.userDemographics.gender')}
                id="user-demographics-gender"
                name="gender"
                data-cy="gender"
                type="text"
              />
              <ValidatedField
                label={translate('b4CarecollectApp.userDemographics.dateOfBird')}
                id="user-demographics-dateOfBird"
                name="dateOfBird"
                data-cy="dateOfBird"
                type="date"
              />
              <ValidatedField
                label={translate('b4CarecollectApp.userDemographics.age')}
                id="user-demographics-age"
                name="age"
                data-cy="age"
                type="text"
              />
              <ValidatedField
                label={translate('b4CarecollectApp.userDemographics.country')}
                id="user-demographics-country"
                name="country"
                data-cy="country"
                type="text"
              />
              <ValidatedField
                label={translate('b4CarecollectApp.userDemographics.state')}
                id="user-demographics-state"
                name="state"
                data-cy="state"
                type="text"
              />
              <ValidatedField
                label={translate('b4CarecollectApp.userDemographics.city')}
                id="user-demographics-city"
                name="city"
                data-cy="city"
                type="text"
              />
              <ValidatedField
                label={translate('b4CarecollectApp.userDemographics.ethnicity')}
                id="user-demographics-ethnicity"
                name="ethnicity"
                data-cy="ethnicity"
                type="text"
              />
              <ValidatedField
                label={translate('b4CarecollectApp.userDemographics.income')}
                id="user-demographics-income"
                name="income"
                data-cy="income"
                type="text"
              />
              <ValidatedField
                label={translate('b4CarecollectApp.userDemographics.maritalStatus')}
                id="user-demographics-maritalStatus"
                name="maritalStatus"
                data-cy="maritalStatus"
                type="text"
              />
              <ValidatedField
                label={translate('b4CarecollectApp.userDemographics.education')}
                id="user-demographics-education"
                name="education"
                data-cy="education"
                type="text"
              />
              <ValidatedField
                label={translate('b4CarecollectApp.userDemographics.endTime')}
                id="user-demographics-endTime"
                name="endTime"
                data-cy="endTime"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/user-demographics" replace color="info">
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

export default UserDemographicsUpdate;
