import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './heart-rate-bpm.reducer';

export const HeartRateBpmDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const heartRateBpmEntity = useAppSelector(state => state.heartRateBpm.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="heartRateBpmDetailsHeading">
          <Translate contentKey="b4CarecollectApp.heartRateBpm.detail.title">HeartRateBpm</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="b4CarecollectApp.heartRateBpm.id">Id</Translate>
            </span>
          </dt>
          <dd>{heartRateBpmEntity.id}</dd>
          <dt>
            <span id="usuarioId">
              <Translate contentKey="b4CarecollectApp.heartRateBpm.usuarioId">Usuario Id</Translate>
            </span>
          </dt>
          <dd>{heartRateBpmEntity.usuarioId}</dd>
          <dt>
            <span id="empresaId">
              <Translate contentKey="b4CarecollectApp.heartRateBpm.empresaId">Empresa Id</Translate>
            </span>
          </dt>
          <dd>{heartRateBpmEntity.empresaId}</dd>
          <dt>
            <span id="ppm">
              <Translate contentKey="b4CarecollectApp.heartRateBpm.ppm">Ppm</Translate>
            </span>
          </dt>
          <dd>{heartRateBpmEntity.ppm}</dd>
          <dt>
            <span id="endTime">
              <Translate contentKey="b4CarecollectApp.heartRateBpm.endTime">End Time</Translate>
            </span>
          </dt>
          <dd>
            {heartRateBpmEntity.endTime ? <TextFormat value={heartRateBpmEntity.endTime} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
        </dl>
        <Button tag={Link} to="/heart-rate-bpm" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/heart-rate-bpm/${heartRateBpmEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default HeartRateBpmDetail;
