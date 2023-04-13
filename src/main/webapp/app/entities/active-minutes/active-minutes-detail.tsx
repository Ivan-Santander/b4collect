import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './active-minutes.reducer';

export const ActiveMinutesDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const activeMinutesEntity = useAppSelector(state => state.activeMinutes.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="activeMinutesDetailsHeading">
          <Translate contentKey="b4CarecollectApp.activeMinutes.detail.title">ActiveMinutes</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="b4CarecollectApp.activeMinutes.id">Id</Translate>
            </span>
          </dt>
          <dd>{activeMinutesEntity.id}</dd>
          <dt>
            <span id="usuarioId">
              <Translate contentKey="b4CarecollectApp.activeMinutes.usuarioId">Usuario Id</Translate>
            </span>
          </dt>
          <dd>{activeMinutesEntity.usuarioId}</dd>
          <dt>
            <span id="empresaId">
              <Translate contentKey="b4CarecollectApp.activeMinutes.empresaId">Empresa Id</Translate>
            </span>
          </dt>
          <dd>{activeMinutesEntity.empresaId}</dd>
          <dt>
            <span id="calorias">
              <Translate contentKey="b4CarecollectApp.activeMinutes.calorias">Calorias</Translate>
            </span>
          </dt>
          <dd>{activeMinutesEntity.calorias}</dd>
          <dt>
            <span id="startTime">
              <Translate contentKey="b4CarecollectApp.activeMinutes.startTime">Start Time</Translate>
            </span>
          </dt>
          <dd>
            {activeMinutesEntity.startTime ? (
              <TextFormat value={activeMinutesEntity.startTime} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="endTime">
              <Translate contentKey="b4CarecollectApp.activeMinutes.endTime">End Time</Translate>
            </span>
          </dt>
          <dd>
            {activeMinutesEntity.endTime ? <TextFormat value={activeMinutesEntity.endTime} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
        </dl>
        <Button tag={Link} to="/active-minutes" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/active-minutes/${activeMinutesEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default ActiveMinutesDetail;
