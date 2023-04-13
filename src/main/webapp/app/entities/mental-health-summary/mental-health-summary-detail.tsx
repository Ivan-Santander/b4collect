import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './mental-health-summary.reducer';

export const MentalHealthSummaryDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const mentalHealthSummaryEntity = useAppSelector(state => state.mentalHealthSummary.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="mentalHealthSummaryDetailsHeading">
          <Translate contentKey="b4CarecollectApp.mentalHealthSummary.detail.title">MentalHealthSummary</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="b4CarecollectApp.mentalHealthSummary.id">Id</Translate>
            </span>
          </dt>
          <dd>{mentalHealthSummaryEntity.id}</dd>
          <dt>
            <span id="usuarioId">
              <Translate contentKey="b4CarecollectApp.mentalHealthSummary.usuarioId">Usuario Id</Translate>
            </span>
          </dt>
          <dd>{mentalHealthSummaryEntity.usuarioId}</dd>
          <dt>
            <span id="empresaId">
              <Translate contentKey="b4CarecollectApp.mentalHealthSummary.empresaId">Empresa Id</Translate>
            </span>
          </dt>
          <dd>{mentalHealthSummaryEntity.empresaId}</dd>
          <dt>
            <span id="emotionDescripMain">
              <Translate contentKey="b4CarecollectApp.mentalHealthSummary.emotionDescripMain">Emotion Descrip Main</Translate>
            </span>
          </dt>
          <dd>{mentalHealthSummaryEntity.emotionDescripMain}</dd>
          <dt>
            <span id="emotionDescripSecond">
              <Translate contentKey="b4CarecollectApp.mentalHealthSummary.emotionDescripSecond">Emotion Descrip Second</Translate>
            </span>
          </dt>
          <dd>{mentalHealthSummaryEntity.emotionDescripSecond}</dd>
          <dt>
            <span id="fieldMentalHealthAverage">
              <Translate contentKey="b4CarecollectApp.mentalHealthSummary.fieldMentalHealthAverage">Field Mental Health Average</Translate>
            </span>
          </dt>
          <dd>{mentalHealthSummaryEntity.fieldMentalHealthAverage}</dd>
          <dt>
            <span id="fieldMentalHealthMax">
              <Translate contentKey="b4CarecollectApp.mentalHealthSummary.fieldMentalHealthMax">Field Mental Health Max</Translate>
            </span>
          </dt>
          <dd>{mentalHealthSummaryEntity.fieldMentalHealthMax}</dd>
          <dt>
            <span id="fieldMentalHealthMin">
              <Translate contentKey="b4CarecollectApp.mentalHealthSummary.fieldMentalHealthMin">Field Mental Health Min</Translate>
            </span>
          </dt>
          <dd>{mentalHealthSummaryEntity.fieldMentalHealthMin}</dd>
          <dt>
            <span id="scoreMentalRisk">
              <Translate contentKey="b4CarecollectApp.mentalHealthSummary.scoreMentalRisk">Score Mental Risk</Translate>
            </span>
          </dt>
          <dd>{mentalHealthSummaryEntity.scoreMentalRisk}</dd>
          <dt>
            <span id="startTime">
              <Translate contentKey="b4CarecollectApp.mentalHealthSummary.startTime">Start Time</Translate>
            </span>
          </dt>
          <dd>
            {mentalHealthSummaryEntity.startTime ? (
              <TextFormat value={mentalHealthSummaryEntity.startTime} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="endTime">
              <Translate contentKey="b4CarecollectApp.mentalHealthSummary.endTime">End Time</Translate>
            </span>
          </dt>
          <dd>
            {mentalHealthSummaryEntity.endTime ? (
              <TextFormat value={mentalHealthSummaryEntity.endTime} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
        </dl>
        <Button tag={Link} to="/mental-health-summary" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/mental-health-summary/${mentalHealthSummaryEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default MentalHealthSummaryDetail;
