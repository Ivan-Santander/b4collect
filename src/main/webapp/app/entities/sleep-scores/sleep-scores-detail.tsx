import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './sleep-scores.reducer';

export const SleepScoresDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const sleepScoresEntity = useAppSelector(state => state.sleepScores.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="sleepScoresDetailsHeading">
          <Translate contentKey="b4CarecollectApp.sleepScores.detail.title">SleepScores</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="b4CarecollectApp.sleepScores.id">Id</Translate>
            </span>
          </dt>
          <dd>{sleepScoresEntity.id}</dd>
          <dt>
            <span id="usuarioId">
              <Translate contentKey="b4CarecollectApp.sleepScores.usuarioId">Usuario Id</Translate>
            </span>
          </dt>
          <dd>{sleepScoresEntity.usuarioId}</dd>
          <dt>
            <span id="empresaId">
              <Translate contentKey="b4CarecollectApp.sleepScores.empresaId">Empresa Id</Translate>
            </span>
          </dt>
          <dd>{sleepScoresEntity.empresaId}</dd>
          <dt>
            <span id="sleepQualityRatingScore">
              <Translate contentKey="b4CarecollectApp.sleepScores.sleepQualityRatingScore">Sleep Quality Rating Score</Translate>
            </span>
          </dt>
          <dd>{sleepScoresEntity.sleepQualityRatingScore}</dd>
          <dt>
            <span id="sleepEfficiencyScore">
              <Translate contentKey="b4CarecollectApp.sleepScores.sleepEfficiencyScore">Sleep Efficiency Score</Translate>
            </span>
          </dt>
          <dd>{sleepScoresEntity.sleepEfficiencyScore}</dd>
          <dt>
            <span id="sleepGooalSecondsScore">
              <Translate contentKey="b4CarecollectApp.sleepScores.sleepGooalSecondsScore">Sleep Gooal Seconds Score</Translate>
            </span>
          </dt>
          <dd>{sleepScoresEntity.sleepGooalSecondsScore}</dd>
          <dt>
            <span id="sleepContinuityScore">
              <Translate contentKey="b4CarecollectApp.sleepScores.sleepContinuityScore">Sleep Continuity Score</Translate>
            </span>
          </dt>
          <dd>{sleepScoresEntity.sleepContinuityScore}</dd>
          <dt>
            <span id="sleepContinuityRating">
              <Translate contentKey="b4CarecollectApp.sleepScores.sleepContinuityRating">Sleep Continuity Rating</Translate>
            </span>
          </dt>
          <dd>{sleepScoresEntity.sleepContinuityRating}</dd>
        </dl>
        <Button tag={Link} to="/sleep-scores" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/sleep-scores/${sleepScoresEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default SleepScoresDetail;
