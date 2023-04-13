import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './powe-sample.reducer';

export const PoweSampleDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const poweSampleEntity = useAppSelector(state => state.poweSample.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="poweSampleDetailsHeading">
          <Translate contentKey="b4CarecollectApp.poweSample.detail.title">PoweSample</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="b4CarecollectApp.poweSample.id">Id</Translate>
            </span>
          </dt>
          <dd>{poweSampleEntity.id}</dd>
          <dt>
            <span id="usuarioId">
              <Translate contentKey="b4CarecollectApp.poweSample.usuarioId">Usuario Id</Translate>
            </span>
          </dt>
          <dd>{poweSampleEntity.usuarioId}</dd>
          <dt>
            <span id="empresaId">
              <Translate contentKey="b4CarecollectApp.poweSample.empresaId">Empresa Id</Translate>
            </span>
          </dt>
          <dd>{poweSampleEntity.empresaId}</dd>
          <dt>
            <span id="vatios">
              <Translate contentKey="b4CarecollectApp.poweSample.vatios">Vatios</Translate>
            </span>
          </dt>
          <dd>{poweSampleEntity.vatios}</dd>
          <dt>
            <span id="startTime">
              <Translate contentKey="b4CarecollectApp.poweSample.startTime">Start Time</Translate>
            </span>
          </dt>
          <dd>
            {poweSampleEntity.startTime ? <TextFormat value={poweSampleEntity.startTime} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="endTime">
              <Translate contentKey="b4CarecollectApp.poweSample.endTime">End Time</Translate>
            </span>
          </dt>
          <dd>{poweSampleEntity.endTime ? <TextFormat value={poweSampleEntity.endTime} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
        </dl>
        <Button tag={Link} to="/powe-sample" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/powe-sample/${poweSampleEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default PoweSampleDetail;
