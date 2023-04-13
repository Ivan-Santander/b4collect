import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './mental-health.reducer';

export const MentalHealthDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const mentalHealthEntity = useAppSelector(state => state.mentalHealth.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="mentalHealthDetailsHeading">
          <Translate contentKey="b4CarecollectApp.mentalHealth.detail.title">MentalHealth</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="b4CarecollectApp.mentalHealth.id">Id</Translate>
            </span>
          </dt>
          <dd>{mentalHealthEntity.id}</dd>
          <dt>
            <span id="usuarioId">
              <Translate contentKey="b4CarecollectApp.mentalHealth.usuarioId">Usuario Id</Translate>
            </span>
          </dt>
          <dd>{mentalHealthEntity.usuarioId}</dd>
          <dt>
            <span id="empresaId">
              <Translate contentKey="b4CarecollectApp.mentalHealth.empresaId">Empresa Id</Translate>
            </span>
          </dt>
          <dd>{mentalHealthEntity.empresaId}</dd>
          <dt>
            <span id="emotionDescription">
              <Translate contentKey="b4CarecollectApp.mentalHealth.emotionDescription">Emotion Description</Translate>
            </span>
          </dt>
          <dd>{mentalHealthEntity.emotionDescription}</dd>
          <dt>
            <span id="emotionValue">
              <Translate contentKey="b4CarecollectApp.mentalHealth.emotionValue">Emotion Value</Translate>
            </span>
          </dt>
          <dd>{mentalHealthEntity.emotionValue}</dd>
          <dt>
            <span id="startDate">
              <Translate contentKey="b4CarecollectApp.mentalHealth.startDate">Start Date</Translate>
            </span>
          </dt>
          <dd>
            {mentalHealthEntity.startDate ? <TextFormat value={mentalHealthEntity.startDate} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="endDate">
              <Translate contentKey="b4CarecollectApp.mentalHealth.endDate">End Date</Translate>
            </span>
          </dt>
          <dd>
            {mentalHealthEntity.endDate ? <TextFormat value={mentalHealthEntity.endDate} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="mentalHealthScore">
              <Translate contentKey="b4CarecollectApp.mentalHealth.mentalHealthScore">Mental Health Score</Translate>
            </span>
          </dt>
          <dd>{mentalHealthEntity.mentalHealthScore}</dd>
        </dl>
        <Button tag={Link} to="/mental-health" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/mental-health/${mentalHealthEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default MentalHealthDetail;
