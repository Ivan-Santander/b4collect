import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './body-fat-percentage-summary.reducer';

export const BodyFatPercentageSummaryDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const bodyFatPercentageSummaryEntity = useAppSelector(state => state.bodyFatPercentageSummary.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="bodyFatPercentageSummaryDetailsHeading">
          <Translate contentKey="b4CarecollectApp.bodyFatPercentageSummary.detail.title">BodyFatPercentageSummary</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="b4CarecollectApp.bodyFatPercentageSummary.id">Id</Translate>
            </span>
          </dt>
          <dd>{bodyFatPercentageSummaryEntity.id}</dd>
          <dt>
            <span id="usuarioId">
              <Translate contentKey="b4CarecollectApp.bodyFatPercentageSummary.usuarioId">Usuario Id</Translate>
            </span>
          </dt>
          <dd>{bodyFatPercentageSummaryEntity.usuarioId}</dd>
          <dt>
            <span id="empresaId">
              <Translate contentKey="b4CarecollectApp.bodyFatPercentageSummary.empresaId">Empresa Id</Translate>
            </span>
          </dt>
          <dd>{bodyFatPercentageSummaryEntity.empresaId}</dd>
          <dt>
            <span id="fieldAverage">
              <Translate contentKey="b4CarecollectApp.bodyFatPercentageSummary.fieldAverage">Field Average</Translate>
            </span>
          </dt>
          <dd>{bodyFatPercentageSummaryEntity.fieldAverage}</dd>
          <dt>
            <span id="fieldMax">
              <Translate contentKey="b4CarecollectApp.bodyFatPercentageSummary.fieldMax">Field Max</Translate>
            </span>
          </dt>
          <dd>{bodyFatPercentageSummaryEntity.fieldMax}</dd>
          <dt>
            <span id="fieldMin">
              <Translate contentKey="b4CarecollectApp.bodyFatPercentageSummary.fieldMin">Field Min</Translate>
            </span>
          </dt>
          <dd>{bodyFatPercentageSummaryEntity.fieldMin}</dd>
          <dt>
            <span id="startTime">
              <Translate contentKey="b4CarecollectApp.bodyFatPercentageSummary.startTime">Start Time</Translate>
            </span>
          </dt>
          <dd>
            {bodyFatPercentageSummaryEntity.startTime ? (
              <TextFormat value={bodyFatPercentageSummaryEntity.startTime} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="endTime">
              <Translate contentKey="b4CarecollectApp.bodyFatPercentageSummary.endTime">End Time</Translate>
            </span>
          </dt>
          <dd>
            {bodyFatPercentageSummaryEntity.endTime ? (
              <TextFormat value={bodyFatPercentageSummaryEntity.endTime} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
        </dl>
        <Button tag={Link} to="/body-fat-percentage-summary" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/body-fat-percentage-summary/${bodyFatPercentageSummaryEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default BodyFatPercentageSummaryDetail;
