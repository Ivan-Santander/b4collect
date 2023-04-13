import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './distance-delta.reducer';

export const DistanceDeltaDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const distanceDeltaEntity = useAppSelector(state => state.distanceDelta.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="distanceDeltaDetailsHeading">
          <Translate contentKey="b4CarecollectApp.distanceDelta.detail.title">DistanceDelta</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="b4CarecollectApp.distanceDelta.id">Id</Translate>
            </span>
          </dt>
          <dd>{distanceDeltaEntity.id}</dd>
          <dt>
            <span id="usuarioId">
              <Translate contentKey="b4CarecollectApp.distanceDelta.usuarioId">Usuario Id</Translate>
            </span>
          </dt>
          <dd>{distanceDeltaEntity.usuarioId}</dd>
          <dt>
            <span id="empresaId">
              <Translate contentKey="b4CarecollectApp.distanceDelta.empresaId">Empresa Id</Translate>
            </span>
          </dt>
          <dd>{distanceDeltaEntity.empresaId}</dd>
          <dt>
            <span id="distance">
              <Translate contentKey="b4CarecollectApp.distanceDelta.distance">Distance</Translate>
            </span>
          </dt>
          <dd>{distanceDeltaEntity.distance}</dd>
          <dt>
            <span id="startTime">
              <Translate contentKey="b4CarecollectApp.distanceDelta.startTime">Start Time</Translate>
            </span>
          </dt>
          <dd>
            {distanceDeltaEntity.startTime ? (
              <TextFormat value={distanceDeltaEntity.startTime} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="endTime">
              <Translate contentKey="b4CarecollectApp.distanceDelta.endTime">End Time</Translate>
            </span>
          </dt>
          <dd>
            {distanceDeltaEntity.endTime ? <TextFormat value={distanceDeltaEntity.endTime} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
        </dl>
        <Button tag={Link} to="/distance-delta" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/distance-delta/${distanceDeltaEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default DistanceDeltaDetail;
