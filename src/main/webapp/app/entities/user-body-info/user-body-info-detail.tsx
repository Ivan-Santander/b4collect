import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './user-body-info.reducer';

export const UserBodyInfoDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const userBodyInfoEntity = useAppSelector(state => state.userBodyInfo.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="userBodyInfoDetailsHeading">
          <Translate contentKey="b4CarecollectApp.userBodyInfo.detail.title">UserBodyInfo</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="b4CarecollectApp.userBodyInfo.id">Id</Translate>
            </span>
          </dt>
          <dd>{userBodyInfoEntity.id}</dd>
          <dt>
            <span id="usuarioId">
              <Translate contentKey="b4CarecollectApp.userBodyInfo.usuarioId">Usuario Id</Translate>
            </span>
          </dt>
          <dd>{userBodyInfoEntity.usuarioId}</dd>
          <dt>
            <span id="empresaId">
              <Translate contentKey="b4CarecollectApp.userBodyInfo.empresaId">Empresa Id</Translate>
            </span>
          </dt>
          <dd>{userBodyInfoEntity.empresaId}</dd>
          <dt>
            <span id="waistCircumference">
              <Translate contentKey="b4CarecollectApp.userBodyInfo.waistCircumference">Waist Circumference</Translate>
            </span>
          </dt>
          <dd>{userBodyInfoEntity.waistCircumference}</dd>
          <dt>
            <span id="hipCircumference">
              <Translate contentKey="b4CarecollectApp.userBodyInfo.hipCircumference">Hip Circumference</Translate>
            </span>
          </dt>
          <dd>{userBodyInfoEntity.hipCircumference}</dd>
          <dt>
            <span id="chestCircumference">
              <Translate contentKey="b4CarecollectApp.userBodyInfo.chestCircumference">Chest Circumference</Translate>
            </span>
          </dt>
          <dd>{userBodyInfoEntity.chestCircumference}</dd>
          <dt>
            <span id="boneCompositionPercentaje">
              <Translate contentKey="b4CarecollectApp.userBodyInfo.boneCompositionPercentaje">Bone Composition Percentaje</Translate>
            </span>
          </dt>
          <dd>{userBodyInfoEntity.boneCompositionPercentaje}</dd>
          <dt>
            <span id="muscleCompositionPercentaje">
              <Translate contentKey="b4CarecollectApp.userBodyInfo.muscleCompositionPercentaje">Muscle Composition Percentaje</Translate>
            </span>
          </dt>
          <dd>{userBodyInfoEntity.muscleCompositionPercentaje}</dd>
          <dt>
            <span id="smoker">
              <Translate contentKey="b4CarecollectApp.userBodyInfo.smoker">Smoker</Translate>
            </span>
          </dt>
          <dd>{userBodyInfoEntity.smoker ? 'true' : 'false'}</dd>
          <dt>
            <span id="waightKg">
              <Translate contentKey="b4CarecollectApp.userBodyInfo.waightKg">Waight Kg</Translate>
            </span>
          </dt>
          <dd>{userBodyInfoEntity.waightKg}</dd>
          <dt>
            <span id="heightCm">
              <Translate contentKey="b4CarecollectApp.userBodyInfo.heightCm">Height Cm</Translate>
            </span>
          </dt>
          <dd>{userBodyInfoEntity.heightCm}</dd>
          <dt>
            <span id="bodyHealthScore">
              <Translate contentKey="b4CarecollectApp.userBodyInfo.bodyHealthScore">Body Health Score</Translate>
            </span>
          </dt>
          <dd>{userBodyInfoEntity.bodyHealthScore}</dd>
          <dt>
            <span id="cardiovascularRisk">
              <Translate contentKey="b4CarecollectApp.userBodyInfo.cardiovascularRisk">Cardiovascular Risk</Translate>
            </span>
          </dt>
          <dd>{userBodyInfoEntity.cardiovascularRisk}</dd>
        </dl>
        <Button tag={Link} to="/user-body-info" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/user-body-info/${userBodyInfoEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default UserBodyInfoDetail;
