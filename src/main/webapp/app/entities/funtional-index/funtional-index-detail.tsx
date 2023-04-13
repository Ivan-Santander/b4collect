import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './funtional-index.reducer';

export const FuntionalIndexDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const funtionalIndexEntity = useAppSelector(state => state.funtionalIndex.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="funtionalIndexDetailsHeading">
          <Translate contentKey="b4CarecollectApp.funtionalIndex.detail.title">FuntionalIndex</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="b4CarecollectApp.funtionalIndex.id">Id</Translate>
            </span>
          </dt>
          <dd>{funtionalIndexEntity.id}</dd>
          <dt>
            <span id="usuarioId">
              <Translate contentKey="b4CarecollectApp.funtionalIndex.usuarioId">Usuario Id</Translate>
            </span>
          </dt>
          <dd>{funtionalIndexEntity.usuarioId}</dd>
          <dt>
            <span id="empresaId">
              <Translate contentKey="b4CarecollectApp.funtionalIndex.empresaId">Empresa Id</Translate>
            </span>
          </dt>
          <dd>{funtionalIndexEntity.empresaId}</dd>
          <dt>
            <span id="bodyHealthScore">
              <Translate contentKey="b4CarecollectApp.funtionalIndex.bodyHealthScore">Body Health Score</Translate>
            </span>
          </dt>
          <dd>{funtionalIndexEntity.bodyHealthScore}</dd>
          <dt>
            <span id="mentalHealthScore">
              <Translate contentKey="b4CarecollectApp.funtionalIndex.mentalHealthScore">Mental Health Score</Translate>
            </span>
          </dt>
          <dd>{funtionalIndexEntity.mentalHealthScore}</dd>
          <dt>
            <span id="sleepHealthScore">
              <Translate contentKey="b4CarecollectApp.funtionalIndex.sleepHealthScore">Sleep Health Score</Translate>
            </span>
          </dt>
          <dd>{funtionalIndexEntity.sleepHealthScore}</dd>
          <dt>
            <span id="funtionalIndex">
              <Translate contentKey="b4CarecollectApp.funtionalIndex.funtionalIndex">Funtional Index</Translate>
            </span>
          </dt>
          <dd>{funtionalIndexEntity.funtionalIndex}</dd>
          <dt>
            <span id="alarmRiskScore">
              <Translate contentKey="b4CarecollectApp.funtionalIndex.alarmRiskScore">Alarm Risk Score</Translate>
            </span>
          </dt>
          <dd>{funtionalIndexEntity.alarmRiskScore}</dd>
          <dt>
            <span id="startTime">
              <Translate contentKey="b4CarecollectApp.funtionalIndex.startTime">Start Time</Translate>
            </span>
          </dt>
          <dd>
            {funtionalIndexEntity.startTime ? (
              <TextFormat value={funtionalIndexEntity.startTime} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
        </dl>
        <Button tag={Link} to="/funtional-index" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/funtional-index/${funtionalIndexEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default FuntionalIndexDetail;
