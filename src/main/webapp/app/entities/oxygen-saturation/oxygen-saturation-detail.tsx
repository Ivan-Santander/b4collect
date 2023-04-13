import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './oxygen-saturation.reducer';

export const OxygenSaturationDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const oxygenSaturationEntity = useAppSelector(state => state.oxygenSaturation.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="oxygenSaturationDetailsHeading">
          <Translate contentKey="b4CarecollectApp.oxygenSaturation.detail.title">OxygenSaturation</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="b4CarecollectApp.oxygenSaturation.id">Id</Translate>
            </span>
          </dt>
          <dd>{oxygenSaturationEntity.id}</dd>
          <dt>
            <span id="usuarioId">
              <Translate contentKey="b4CarecollectApp.oxygenSaturation.usuarioId">Usuario Id</Translate>
            </span>
          </dt>
          <dd>{oxygenSaturationEntity.usuarioId}</dd>
          <dt>
            <span id="empresaId">
              <Translate contentKey="b4CarecollectApp.oxygenSaturation.empresaId">Empresa Id</Translate>
            </span>
          </dt>
          <dd>{oxygenSaturationEntity.empresaId}</dd>
          <dt>
            <span id="fieldOxigenSaturation">
              <Translate contentKey="b4CarecollectApp.oxygenSaturation.fieldOxigenSaturation">Field Oxigen Saturation</Translate>
            </span>
          </dt>
          <dd>{oxygenSaturationEntity.fieldOxigenSaturation}</dd>
          <dt>
            <span id="fieldSuplementalOxigenFlowRate">
              <Translate contentKey="b4CarecollectApp.oxygenSaturation.fieldSuplementalOxigenFlowRate">
                Field Suplemental Oxigen Flow Rate
              </Translate>
            </span>
          </dt>
          <dd>{oxygenSaturationEntity.fieldSuplementalOxigenFlowRate}</dd>
          <dt>
            <span id="fieldOxigenTherapyAdministrationMode">
              <Translate contentKey="b4CarecollectApp.oxygenSaturation.fieldOxigenTherapyAdministrationMode">
                Field Oxigen Therapy Administration Mode
              </Translate>
            </span>
          </dt>
          <dd>{oxygenSaturationEntity.fieldOxigenTherapyAdministrationMode}</dd>
          <dt>
            <span id="fieldOxigenSaturationMode">
              <Translate contentKey="b4CarecollectApp.oxygenSaturation.fieldOxigenSaturationMode">Field Oxigen Saturation Mode</Translate>
            </span>
          </dt>
          <dd>{oxygenSaturationEntity.fieldOxigenSaturationMode}</dd>
          <dt>
            <span id="fieldOxigenSaturationMeasurementMethod">
              <Translate contentKey="b4CarecollectApp.oxygenSaturation.fieldOxigenSaturationMeasurementMethod">
                Field Oxigen Saturation Measurement Method
              </Translate>
            </span>
          </dt>
          <dd>{oxygenSaturationEntity.fieldOxigenSaturationMeasurementMethod}</dd>
          <dt>
            <span id="endTime">
              <Translate contentKey="b4CarecollectApp.oxygenSaturation.endTime">End Time</Translate>
            </span>
          </dt>
          <dd>
            {oxygenSaturationEntity.endTime ? (
              <TextFormat value={oxygenSaturationEntity.endTime} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
        </dl>
        <Button tag={Link} to="/oxygen-saturation" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/oxygen-saturation/${oxygenSaturationEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default OxygenSaturationDetail;
