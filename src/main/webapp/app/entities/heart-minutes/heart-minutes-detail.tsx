import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './heart-minutes.reducer';

export const HeartMinutesDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const heartMinutesEntity = useAppSelector(state => state.heartMinutes.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="heartMinutesDetailsHeading">
          <Translate contentKey="b4CarecollectApp.heartMinutes.detail.title">HeartMinutes</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="b4CarecollectApp.heartMinutes.id">Id</Translate>
            </span>
          </dt>
          <dd>{heartMinutesEntity.id}</dd>
          <dt>
            <span id="usuarioId">
              <Translate contentKey="b4CarecollectApp.heartMinutes.usuarioId">Usuario Id</Translate>
            </span>
          </dt>
          <dd>{heartMinutesEntity.usuarioId}</dd>
          <dt>
            <span id="empresaId">
              <Translate contentKey="b4CarecollectApp.heartMinutes.empresaId">Empresa Id</Translate>
            </span>
          </dt>
          <dd>{heartMinutesEntity.empresaId}</dd>
          <dt>
            <span id="severity">
              <Translate contentKey="b4CarecollectApp.heartMinutes.severity">Severity</Translate>
            </span>
          </dt>
          <dd>{heartMinutesEntity.severity}</dd>
          <dt>
            <span id="startTime">
              <Translate contentKey="b4CarecollectApp.heartMinutes.startTime">Start Time</Translate>
            </span>
          </dt>
          <dd>
            {heartMinutesEntity.startTime ? <TextFormat value={heartMinutesEntity.startTime} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="endTime">
              <Translate contentKey="b4CarecollectApp.heartMinutes.endTime">End Time</Translate>
            </span>
          </dt>
          <dd>
            {heartMinutesEntity.endTime ? <TextFormat value={heartMinutesEntity.endTime} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
        </dl>
        <Button tag={Link} to="/heart-minutes" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/heart-minutes/${heartMinutesEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default HeartMinutesDetail;
