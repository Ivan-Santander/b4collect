import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './calories-bmr-summary.reducer';

export const CaloriesBmrSummaryDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const caloriesBmrSummaryEntity = useAppSelector(state => state.caloriesBmrSummary.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="caloriesBmrSummaryDetailsHeading">
          <Translate contentKey="b4CarecollectApp.caloriesBmrSummary.detail.title">CaloriesBmrSummary</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="b4CarecollectApp.caloriesBmrSummary.id">Id</Translate>
            </span>
          </dt>
          <dd>{caloriesBmrSummaryEntity.id}</dd>
          <dt>
            <span id="usuarioId">
              <Translate contentKey="b4CarecollectApp.caloriesBmrSummary.usuarioId">Usuario Id</Translate>
            </span>
          </dt>
          <dd>{caloriesBmrSummaryEntity.usuarioId}</dd>
          <dt>
            <span id="empresaId">
              <Translate contentKey="b4CarecollectApp.caloriesBmrSummary.empresaId">Empresa Id</Translate>
            </span>
          </dt>
          <dd>{caloriesBmrSummaryEntity.empresaId}</dd>
          <dt>
            <span id="fieldAverage">
              <Translate contentKey="b4CarecollectApp.caloriesBmrSummary.fieldAverage">Field Average</Translate>
            </span>
          </dt>
          <dd>{caloriesBmrSummaryEntity.fieldAverage}</dd>
          <dt>
            <span id="fieldMax">
              <Translate contentKey="b4CarecollectApp.caloriesBmrSummary.fieldMax">Field Max</Translate>
            </span>
          </dt>
          <dd>{caloriesBmrSummaryEntity.fieldMax}</dd>
          <dt>
            <span id="fieldMin">
              <Translate contentKey="b4CarecollectApp.caloriesBmrSummary.fieldMin">Field Min</Translate>
            </span>
          </dt>
          <dd>{caloriesBmrSummaryEntity.fieldMin}</dd>
          <dt>
            <span id="startTime">
              <Translate contentKey="b4CarecollectApp.caloriesBmrSummary.startTime">Start Time</Translate>
            </span>
          </dt>
          <dd>
            {caloriesBmrSummaryEntity.startTime ? (
              <TextFormat value={caloriesBmrSummaryEntity.startTime} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="endTime">
              <Translate contentKey="b4CarecollectApp.caloriesBmrSummary.endTime">End Time</Translate>
            </span>
          </dt>
          <dd>
            {caloriesBmrSummaryEntity.endTime ? (
              <TextFormat value={caloriesBmrSummaryEntity.endTime} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
        </dl>
        <Button tag={Link} to="/calories-bmr-summary" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/calories-bmr-summary/${caloriesBmrSummaryEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default CaloriesBmrSummaryDetail;
