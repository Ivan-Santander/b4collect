import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './weight-summary.reducer';

export const WeightSummaryDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const weightSummaryEntity = useAppSelector(state => state.weightSummary.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="weightSummaryDetailsHeading">
          <Translate contentKey="b4CarecollectApp.weightSummary.detail.title">WeightSummary</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="b4CarecollectApp.weightSummary.id">Id</Translate>
            </span>
          </dt>
          <dd>{weightSummaryEntity.id}</dd>
          <dt>
            <span id="usuarioId">
              <Translate contentKey="b4CarecollectApp.weightSummary.usuarioId">Usuario Id</Translate>
            </span>
          </dt>
          <dd>{weightSummaryEntity.usuarioId}</dd>
          <dt>
            <span id="empresaId">
              <Translate contentKey="b4CarecollectApp.weightSummary.empresaId">Empresa Id</Translate>
            </span>
          </dt>
          <dd>{weightSummaryEntity.empresaId}</dd>
          <dt>
            <span id="fieldAverage">
              <Translate contentKey="b4CarecollectApp.weightSummary.fieldAverage">Field Average</Translate>
            </span>
          </dt>
          <dd>{weightSummaryEntity.fieldAverage}</dd>
          <dt>
            <span id="fieldMax">
              <Translate contentKey="b4CarecollectApp.weightSummary.fieldMax">Field Max</Translate>
            </span>
          </dt>
          <dd>{weightSummaryEntity.fieldMax}</dd>
          <dt>
            <span id="fieldMin">
              <Translate contentKey="b4CarecollectApp.weightSummary.fieldMin">Field Min</Translate>
            </span>
          </dt>
          <dd>{weightSummaryEntity.fieldMin}</dd>
          <dt>
            <span id="startTime">
              <Translate contentKey="b4CarecollectApp.weightSummary.startTime">Start Time</Translate>
            </span>
          </dt>
          <dd>
            {weightSummaryEntity.startTime ? (
              <TextFormat value={weightSummaryEntity.startTime} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="endTime">
              <Translate contentKey="b4CarecollectApp.weightSummary.endTime">End Time</Translate>
            </span>
          </dt>
          <dd>
            {weightSummaryEntity.endTime ? <TextFormat value={weightSummaryEntity.endTime} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
        </dl>
        <Button tag={Link} to="/weight-summary" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/weight-summary/${weightSummaryEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default WeightSummaryDetail;
