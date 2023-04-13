import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './power-summary.reducer';

export const PowerSummaryDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const powerSummaryEntity = useAppSelector(state => state.powerSummary.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="powerSummaryDetailsHeading">
          <Translate contentKey="b4CarecollectApp.powerSummary.detail.title">PowerSummary</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="b4CarecollectApp.powerSummary.id">Id</Translate>
            </span>
          </dt>
          <dd>{powerSummaryEntity.id}</dd>
          <dt>
            <span id="usuarioId">
              <Translate contentKey="b4CarecollectApp.powerSummary.usuarioId">Usuario Id</Translate>
            </span>
          </dt>
          <dd>{powerSummaryEntity.usuarioId}</dd>
          <dt>
            <span id="empresaId">
              <Translate contentKey="b4CarecollectApp.powerSummary.empresaId">Empresa Id</Translate>
            </span>
          </dt>
          <dd>{powerSummaryEntity.empresaId}</dd>
          <dt>
            <span id="fieldAverage">
              <Translate contentKey="b4CarecollectApp.powerSummary.fieldAverage">Field Average</Translate>
            </span>
          </dt>
          <dd>{powerSummaryEntity.fieldAverage}</dd>
          <dt>
            <span id="fieldMax">
              <Translate contentKey="b4CarecollectApp.powerSummary.fieldMax">Field Max</Translate>
            </span>
          </dt>
          <dd>{powerSummaryEntity.fieldMax}</dd>
          <dt>
            <span id="fieldMin">
              <Translate contentKey="b4CarecollectApp.powerSummary.fieldMin">Field Min</Translate>
            </span>
          </dt>
          <dd>{powerSummaryEntity.fieldMin}</dd>
          <dt>
            <span id="startTime">
              <Translate contentKey="b4CarecollectApp.powerSummary.startTime">Start Time</Translate>
            </span>
          </dt>
          <dd>
            {powerSummaryEntity.startTime ? <TextFormat value={powerSummaryEntity.startTime} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="endTime">
              <Translate contentKey="b4CarecollectApp.powerSummary.endTime">End Time</Translate>
            </span>
          </dt>
          <dd>
            {powerSummaryEntity.endTime ? <TextFormat value={powerSummaryEntity.endTime} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
        </dl>
        <Button tag={Link} to="/power-summary" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/power-summary/${powerSummaryEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default PowerSummaryDetail;
