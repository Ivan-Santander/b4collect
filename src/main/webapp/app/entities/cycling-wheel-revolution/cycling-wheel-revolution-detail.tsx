import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './cycling-wheel-revolution.reducer';

export const CyclingWheelRevolutionDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const cyclingWheelRevolutionEntity = useAppSelector(state => state.cyclingWheelRevolution.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="cyclingWheelRevolutionDetailsHeading">
          <Translate contentKey="b4CarecollectApp.cyclingWheelRevolution.detail.title">CyclingWheelRevolution</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="b4CarecollectApp.cyclingWheelRevolution.id">Id</Translate>
            </span>
          </dt>
          <dd>{cyclingWheelRevolutionEntity.id}</dd>
          <dt>
            <span id="usuarioId">
              <Translate contentKey="b4CarecollectApp.cyclingWheelRevolution.usuarioId">Usuario Id</Translate>
            </span>
          </dt>
          <dd>{cyclingWheelRevolutionEntity.usuarioId}</dd>
          <dt>
            <span id="empresaId">
              <Translate contentKey="b4CarecollectApp.cyclingWheelRevolution.empresaId">Empresa Id</Translate>
            </span>
          </dt>
          <dd>{cyclingWheelRevolutionEntity.empresaId}</dd>
          <dt>
            <span id="revolutions">
              <Translate contentKey="b4CarecollectApp.cyclingWheelRevolution.revolutions">Revolutions</Translate>
            </span>
          </dt>
          <dd>{cyclingWheelRevolutionEntity.revolutions}</dd>
          <dt>
            <span id="startTime">
              <Translate contentKey="b4CarecollectApp.cyclingWheelRevolution.startTime">Start Time</Translate>
            </span>
          </dt>
          <dd>
            {cyclingWheelRevolutionEntity.startTime ? (
              <TextFormat value={cyclingWheelRevolutionEntity.startTime} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="endTime">
              <Translate contentKey="b4CarecollectApp.cyclingWheelRevolution.endTime">End Time</Translate>
            </span>
          </dt>
          <dd>
            {cyclingWheelRevolutionEntity.endTime ? (
              <TextFormat value={cyclingWheelRevolutionEntity.endTime} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
        </dl>
        <Button tag={Link} to="/cycling-wheel-revolution" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/cycling-wheel-revolution/${cyclingWheelRevolutionEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default CyclingWheelRevolutionDetail;
