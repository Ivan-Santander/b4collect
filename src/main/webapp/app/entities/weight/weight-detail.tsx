import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './weight.reducer';

export const WeightDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const weightEntity = useAppSelector(state => state.weight.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="weightDetailsHeading">
          <Translate contentKey="b4CarecollectApp.weight.detail.title">Weight</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="b4CarecollectApp.weight.id">Id</Translate>
            </span>
          </dt>
          <dd>{weightEntity.id}</dd>
          <dt>
            <span id="usuarioId">
              <Translate contentKey="b4CarecollectApp.weight.usuarioId">Usuario Id</Translate>
            </span>
          </dt>
          <dd>{weightEntity.usuarioId}</dd>
          <dt>
            <span id="empresaId">
              <Translate contentKey="b4CarecollectApp.weight.empresaId">Empresa Id</Translate>
            </span>
          </dt>
          <dd>{weightEntity.empresaId}</dd>
          <dt>
            <span id="fieldWeight">
              <Translate contentKey="b4CarecollectApp.weight.fieldWeight">Field Weight</Translate>
            </span>
          </dt>
          <dd>{weightEntity.fieldWeight}</dd>
          <dt>
            <span id="endTime">
              <Translate contentKey="b4CarecollectApp.weight.endTime">End Time</Translate>
            </span>
          </dt>
          <dd>{weightEntity.endTime ? <TextFormat value={weightEntity.endTime} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
        </dl>
        <Button tag={Link} to="/weight" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/weight/${weightEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default WeightDetail;
