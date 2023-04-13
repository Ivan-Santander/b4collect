import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './cicling-pedaling-cadence.reducer';

export const CiclingPedalingCadenceDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const ciclingPedalingCadenceEntity = useAppSelector(state => state.ciclingPedalingCadence.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="ciclingPedalingCadenceDetailsHeading">
          <Translate contentKey="b4CarecollectApp.ciclingPedalingCadence.detail.title">CiclingPedalingCadence</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="b4CarecollectApp.ciclingPedalingCadence.id">Id</Translate>
            </span>
          </dt>
          <dd>{ciclingPedalingCadenceEntity.id}</dd>
          <dt>
            <span id="usuarioId">
              <Translate contentKey="b4CarecollectApp.ciclingPedalingCadence.usuarioId">Usuario Id</Translate>
            </span>
          </dt>
          <dd>{ciclingPedalingCadenceEntity.usuarioId}</dd>
          <dt>
            <span id="empresaId">
              <Translate contentKey="b4CarecollectApp.ciclingPedalingCadence.empresaId">Empresa Id</Translate>
            </span>
          </dt>
          <dd>{ciclingPedalingCadenceEntity.empresaId}</dd>
          <dt>
            <span id="rpm">
              <Translate contentKey="b4CarecollectApp.ciclingPedalingCadence.rpm">Rpm</Translate>
            </span>
          </dt>
          <dd>{ciclingPedalingCadenceEntity.rpm}</dd>
          <dt>
            <span id="startTime">
              <Translate contentKey="b4CarecollectApp.ciclingPedalingCadence.startTime">Start Time</Translate>
            </span>
          </dt>
          <dd>
            {ciclingPedalingCadenceEntity.startTime ? (
              <TextFormat value={ciclingPedalingCadenceEntity.startTime} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="endTime">
              <Translate contentKey="b4CarecollectApp.ciclingPedalingCadence.endTime">End Time</Translate>
            </span>
          </dt>
          <dd>
            {ciclingPedalingCadenceEntity.endTime ? (
              <TextFormat value={ciclingPedalingCadenceEntity.endTime} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
        </dl>
        <Button tag={Link} to="/cicling-pedaling-cadence" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/cicling-pedaling-cadence/${ciclingPedalingCadenceEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default CiclingPedalingCadenceDetail;
