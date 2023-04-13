import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './user-medical-info.reducer';

export const UserMedicalInfoDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const userMedicalInfoEntity = useAppSelector(state => state.userMedicalInfo.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="userMedicalInfoDetailsHeading">
          <Translate contentKey="b4CarecollectApp.userMedicalInfo.detail.title">UserMedicalInfo</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="b4CarecollectApp.userMedicalInfo.id">Id</Translate>
            </span>
          </dt>
          <dd>{userMedicalInfoEntity.id}</dd>
          <dt>
            <span id="usuarioId">
              <Translate contentKey="b4CarecollectApp.userMedicalInfo.usuarioId">Usuario Id</Translate>
            </span>
          </dt>
          <dd>{userMedicalInfoEntity.usuarioId}</dd>
          <dt>
            <span id="empresaId">
              <Translate contentKey="b4CarecollectApp.userMedicalInfo.empresaId">Empresa Id</Translate>
            </span>
          </dt>
          <dd>{userMedicalInfoEntity.empresaId}</dd>
          <dt>
            <span id="hypertension">
              <Translate contentKey="b4CarecollectApp.userMedicalInfo.hypertension">Hypertension</Translate>
            </span>
          </dt>
          <dd>{userMedicalInfoEntity.hypertension ? 'true' : 'false'}</dd>
          <dt>
            <span id="highGlucose">
              <Translate contentKey="b4CarecollectApp.userMedicalInfo.highGlucose">High Glucose</Translate>
            </span>
          </dt>
          <dd>{userMedicalInfoEntity.highGlucose ? 'true' : 'false'}</dd>
          <dt>
            <span id="hiabetes">
              <Translate contentKey="b4CarecollectApp.userMedicalInfo.hiabetes">Hiabetes</Translate>
            </span>
          </dt>
          <dd>{userMedicalInfoEntity.hiabetes ? 'true' : 'false'}</dd>
          <dt>
            <span id="totalCholesterol">
              <Translate contentKey="b4CarecollectApp.userMedicalInfo.totalCholesterol">Total Cholesterol</Translate>
            </span>
          </dt>
          <dd>{userMedicalInfoEntity.totalCholesterol}</dd>
          <dt>
            <span id="hdlCholesterol">
              <Translate contentKey="b4CarecollectApp.userMedicalInfo.hdlCholesterol">Hdl Cholesterol</Translate>
            </span>
          </dt>
          <dd>{userMedicalInfoEntity.hdlCholesterol}</dd>
          <dt>
            <span id="medicalMainCondition">
              <Translate contentKey="b4CarecollectApp.userMedicalInfo.medicalMainCondition">Medical Main Condition</Translate>
            </span>
          </dt>
          <dd>{userMedicalInfoEntity.medicalMainCondition}</dd>
          <dt>
            <span id="medicalSecundaryCondition">
              <Translate contentKey="b4CarecollectApp.userMedicalInfo.medicalSecundaryCondition">Medical Secundary Condition</Translate>
            </span>
          </dt>
          <dd>{userMedicalInfoEntity.medicalSecundaryCondition}</dd>
          <dt>
            <span id="medicalMainMedication">
              <Translate contentKey="b4CarecollectApp.userMedicalInfo.medicalMainMedication">Medical Main Medication</Translate>
            </span>
          </dt>
          <dd>{userMedicalInfoEntity.medicalMainMedication}</dd>
          <dt>
            <span id="medicalSecundaryMedication">
              <Translate contentKey="b4CarecollectApp.userMedicalInfo.medicalSecundaryMedication">Medical Secundary Medication</Translate>
            </span>
          </dt>
          <dd>{userMedicalInfoEntity.medicalSecundaryMedication}</dd>
          <dt>
            <span id="medicalScore">
              <Translate contentKey="b4CarecollectApp.userMedicalInfo.medicalScore">Medical Score</Translate>
            </span>
          </dt>
          <dd>{userMedicalInfoEntity.medicalScore}</dd>
          <dt>
            <span id="endTime">
              <Translate contentKey="b4CarecollectApp.userMedicalInfo.endTime">End Time</Translate>
            </span>
          </dt>
          <dd>
            {userMedicalInfoEntity.endTime ? (
              <TextFormat value={userMedicalInfoEntity.endTime} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
        </dl>
        <Button tag={Link} to="/user-medical-info" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/user-medical-info/${userMedicalInfoEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default UserMedicalInfoDetail;
