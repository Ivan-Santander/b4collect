import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './temperature-summary.reducer';

export const TemperatureSummaryDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const temperatureSummaryEntity = useAppSelector(state => state.temperatureSummary.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="temperatureSummaryDetailsHeading">
          <Translate contentKey="b4CarecollectApp.temperatureSummary.detail.title">TemperatureSummary</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="b4CarecollectApp.temperatureSummary.id">Id</Translate>
            </span>
          </dt>
          <dd>{temperatureSummaryEntity.id}</dd>
          <dt>
            <span id="usuarioId">
              <Translate contentKey="b4CarecollectApp.temperatureSummary.usuarioId">Usuario Id</Translate>
            </span>
          </dt>
          <dd>{temperatureSummaryEntity.usuarioId}</dd>
          <dt>
            <span id="empresaId">
              <Translate contentKey="b4CarecollectApp.temperatureSummary.empresaId">Empresa Id</Translate>
            </span>
          </dt>
          <dd>{temperatureSummaryEntity.empresaId}</dd>
          <dt>
            <span id="fieldAverage">
              <Translate contentKey="b4CarecollectApp.temperatureSummary.fieldAverage">Field Average</Translate>
            </span>
          </dt>
          <dd>{temperatureSummaryEntity.fieldAverage}</dd>
          <dt>
            <span id="fieldMax">
              <Translate contentKey="b4CarecollectApp.temperatureSummary.fieldMax">Field Max</Translate>
            </span>
          </dt>
          <dd>{temperatureSummaryEntity.fieldMax}</dd>
          <dt>
            <span id="fieldMin">
              <Translate contentKey="b4CarecollectApp.temperatureSummary.fieldMin">Field Min</Translate>
            </span>
          </dt>
          <dd>{temperatureSummaryEntity.fieldMin}</dd>
          <dt>
            <span id="measurementLocation">
              <Translate contentKey="b4CarecollectApp.temperatureSummary.measurementLocation">Measurement Location</Translate>
            </span>
          </dt>
          <dd>{temperatureSummaryEntity.measurementLocation}</dd>
          <dt>
            <span id="startTime">
              <Translate contentKey="b4CarecollectApp.temperatureSummary.startTime">Start Time</Translate>
            </span>
          </dt>
          <dd>
            {temperatureSummaryEntity.startTime ? (
              <TextFormat value={temperatureSummaryEntity.startTime} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="endTime">
              <Translate contentKey="b4CarecollectApp.temperatureSummary.endTime">End Time</Translate>
            </span>
          </dt>
          <dd>
            {temperatureSummaryEntity.endTime ? (
              <TextFormat value={temperatureSummaryEntity.endTime} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
        </dl>
        <Button tag={Link} to="/temperature-summary" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/temperature-summary/${temperatureSummaryEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default TemperatureSummaryDetail;
