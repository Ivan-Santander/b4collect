import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './user-demographics.reducer';

export const UserDemographicsDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const userDemographicsEntity = useAppSelector(state => state.userDemographics.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="userDemographicsDetailsHeading">
          <Translate contentKey="b4CarecollectApp.userDemographics.detail.title">UserDemographics</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="b4CarecollectApp.userDemographics.id">Id</Translate>
            </span>
          </dt>
          <dd>{userDemographicsEntity.id}</dd>
          <dt>
            <span id="usuarioId">
              <Translate contentKey="b4CarecollectApp.userDemographics.usuarioId">Usuario Id</Translate>
            </span>
          </dt>
          <dd>{userDemographicsEntity.usuarioId}</dd>
          <dt>
            <span id="empresaId">
              <Translate contentKey="b4CarecollectApp.userDemographics.empresaId">Empresa Id</Translate>
            </span>
          </dt>
          <dd>{userDemographicsEntity.empresaId}</dd>
          <dt>
            <span id="gender">
              <Translate contentKey="b4CarecollectApp.userDemographics.gender">Gender</Translate>
            </span>
          </dt>
          <dd>{userDemographicsEntity.gender}</dd>
          <dt>
            <span id="dateOfBird">
              <Translate contentKey="b4CarecollectApp.userDemographics.dateOfBird">Date Of Bird</Translate>
            </span>
          </dt>
          <dd>
            {userDemographicsEntity.dateOfBird ? (
              <TextFormat value={userDemographicsEntity.dateOfBird} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="age">
              <Translate contentKey="b4CarecollectApp.userDemographics.age">Age</Translate>
            </span>
          </dt>
          <dd>{userDemographicsEntity.age}</dd>
          <dt>
            <span id="country">
              <Translate contentKey="b4CarecollectApp.userDemographics.country">Country</Translate>
            </span>
          </dt>
          <dd>{userDemographicsEntity.country}</dd>
          <dt>
            <span id="state">
              <Translate contentKey="b4CarecollectApp.userDemographics.state">State</Translate>
            </span>
          </dt>
          <dd>{userDemographicsEntity.state}</dd>
          <dt>
            <span id="city">
              <Translate contentKey="b4CarecollectApp.userDemographics.city">City</Translate>
            </span>
          </dt>
          <dd>{userDemographicsEntity.city}</dd>
          <dt>
            <span id="ethnicity">
              <Translate contentKey="b4CarecollectApp.userDemographics.ethnicity">Ethnicity</Translate>
            </span>
          </dt>
          <dd>{userDemographicsEntity.ethnicity}</dd>
          <dt>
            <span id="income">
              <Translate contentKey="b4CarecollectApp.userDemographics.income">Income</Translate>
            </span>
          </dt>
          <dd>{userDemographicsEntity.income}</dd>
          <dt>
            <span id="maritalStatus">
              <Translate contentKey="b4CarecollectApp.userDemographics.maritalStatus">Marital Status</Translate>
            </span>
          </dt>
          <dd>{userDemographicsEntity.maritalStatus}</dd>
          <dt>
            <span id="education">
              <Translate contentKey="b4CarecollectApp.userDemographics.education">Education</Translate>
            </span>
          </dt>
          <dd>{userDemographicsEntity.education}</dd>
          <dt>
            <span id="endTime">
              <Translate contentKey="b4CarecollectApp.userDemographics.endTime">End Time</Translate>
            </span>
          </dt>
          <dd>
            {userDemographicsEntity.endTime ? (
              <TextFormat value={userDemographicsEntity.endTime} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
        </dl>
        <Button tag={Link} to="/user-demographics" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/user-demographics/${userDemographicsEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default UserDemographicsDetail;
