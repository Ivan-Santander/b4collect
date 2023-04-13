import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './speed.reducer';

export const SpeedDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const speedEntity = useAppSelector(state => state.speed.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="speedDetailsHeading">
          <Translate contentKey="b4CarecollectApp.speed.detail.title">Speed</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="b4CarecollectApp.speed.id">Id</Translate>
            </span>
          </dt>
          <dd>{speedEntity.id}</dd>
          <dt>
            <span id="usuarioId">
              <Translate contentKey="b4CarecollectApp.speed.usuarioId">Usuario Id</Translate>
            </span>
          </dt>
          <dd>{speedEntity.usuarioId}</dd>
          <dt>
            <span id="empresaId">
              <Translate contentKey="b4CarecollectApp.speed.empresaId">Empresa Id</Translate>
            </span>
          </dt>
          <dd>{speedEntity.empresaId}</dd>
          <dt>
            <span id="speed">
              <Translate contentKey="b4CarecollectApp.speed.speed">Speed</Translate>
            </span>
          </dt>
          <dd>{speedEntity.speed}</dd>
          <dt>
            <span id="endTime">
              <Translate contentKey="b4CarecollectApp.speed.endTime">End Time</Translate>
            </span>
          </dt>
          <dd>{speedEntity.endTime ? <TextFormat value={speedEntity.endTime} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
        </dl>
        <Button tag={Link} to="/speed" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/speed/${speedEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default SpeedDetail;
