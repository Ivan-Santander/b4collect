import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './heart-rate-summary.reducer';

export const HeartRateSummaryDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const heartRateSummaryEntity = useAppSelector(state => state.heartRateSummary.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="heartRateSummaryDetailsHeading">
          <Translate contentKey="b4CarecollectApp.heartRateSummary.detail.title">HeartRateSummary</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="b4CarecollectApp.heartRateSummary.id">Id</Translate>
            </span>
          </dt>
          <dd>{heartRateSummaryEntity.id}</dd>
          <dt>
            <span id="usuarioId">
              <Translate contentKey="b4CarecollectApp.heartRateSummary.usuarioId">Usuario Id</Translate>
            </span>
          </dt>
          <dd>{heartRateSummaryEntity.usuarioId}</dd>
          <dt>
            <span id="empresaId">
              <Translate contentKey="b4CarecollectApp.heartRateSummary.empresaId">Empresa Id</Translate>
            </span>
          </dt>
          <dd>{heartRateSummaryEntity.empresaId}</dd>
          <dt>
            <span id="fieldAverage">
              <Translate contentKey="b4CarecollectApp.heartRateSummary.fieldAverage">Field Average</Translate>
            </span>
          </dt>
          <dd>{heartRateSummaryEntity.fieldAverage}</dd>
          <dt>
            <span id="fieldMax">
              <Translate contentKey="b4CarecollectApp.heartRateSummary.fieldMax">Field Max</Translate>
            </span>
          </dt>
          <dd>{heartRateSummaryEntity.fieldMax}</dd>
          <dt>
            <span id="fieldMin">
              <Translate contentKey="b4CarecollectApp.heartRateSummary.fieldMin">Field Min</Translate>
            </span>
          </dt>
          <dd>{heartRateSummaryEntity.fieldMin}</dd>
          <dt>
            <span id="startTime">
              <Translate contentKey="b4CarecollectApp.heartRateSummary.startTime">Start Time</Translate>
            </span>
          </dt>
          <dd>
            {heartRateSummaryEntity.startTime ? (
              <TextFormat value={heartRateSummaryEntity.startTime} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="endTime">
              <Translate contentKey="b4CarecollectApp.heartRateSummary.endTime">End Time</Translate>
            </span>
          </dt>
          <dd>
            {heartRateSummaryEntity.endTime ? (
              <TextFormat value={heartRateSummaryEntity.endTime} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
        </dl>
        <Button tag={Link} to="/heart-rate-summary" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/heart-rate-summary/${heartRateSummaryEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default HeartRateSummaryDetail;
