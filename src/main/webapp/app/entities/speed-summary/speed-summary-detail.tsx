import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './speed-summary.reducer';

export const SpeedSummaryDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const speedSummaryEntity = useAppSelector(state => state.speedSummary.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="speedSummaryDetailsHeading">
          <Translate contentKey="b4CarecollectApp.speedSummary.detail.title">SpeedSummary</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="b4CarecollectApp.speedSummary.id">Id</Translate>
            </span>
          </dt>
          <dd>{speedSummaryEntity.id}</dd>
          <dt>
            <span id="usuarioId">
              <Translate contentKey="b4CarecollectApp.speedSummary.usuarioId">Usuario Id</Translate>
            </span>
          </dt>
          <dd>{speedSummaryEntity.usuarioId}</dd>
          <dt>
            <span id="empresaId">
              <Translate contentKey="b4CarecollectApp.speedSummary.empresaId">Empresa Id</Translate>
            </span>
          </dt>
          <dd>{speedSummaryEntity.empresaId}</dd>
          <dt>
            <span id="fieldAverage">
              <Translate contentKey="b4CarecollectApp.speedSummary.fieldAverage">Field Average</Translate>
            </span>
          </dt>
          <dd>{speedSummaryEntity.fieldAverage}</dd>
          <dt>
            <span id="fieldMax">
              <Translate contentKey="b4CarecollectApp.speedSummary.fieldMax">Field Max</Translate>
            </span>
          </dt>
          <dd>{speedSummaryEntity.fieldMax}</dd>
          <dt>
            <span id="fieldMin">
              <Translate contentKey="b4CarecollectApp.speedSummary.fieldMin">Field Min</Translate>
            </span>
          </dt>
          <dd>{speedSummaryEntity.fieldMin}</dd>
          <dt>
            <span id="startTime">
              <Translate contentKey="b4CarecollectApp.speedSummary.startTime">Start Time</Translate>
            </span>
          </dt>
          <dd>
            {speedSummaryEntity.startTime ? <TextFormat value={speedSummaryEntity.startTime} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="endTime">
              <Translate contentKey="b4CarecollectApp.speedSummary.endTime">End Time</Translate>
            </span>
          </dt>
          <dd>
            {speedSummaryEntity.endTime ? <TextFormat value={speedSummaryEntity.endTime} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
        </dl>
        <Button tag={Link} to="/speed-summary" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/speed-summary/${speedSummaryEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default SpeedSummaryDetail;
