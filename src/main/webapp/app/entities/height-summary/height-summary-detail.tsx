import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './height-summary.reducer';

export const HeightSummaryDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const heightSummaryEntity = useAppSelector(state => state.heightSummary.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="heightSummaryDetailsHeading">
          <Translate contentKey="b4CarecollectApp.heightSummary.detail.title">HeightSummary</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="b4CarecollectApp.heightSummary.id">Id</Translate>
            </span>
          </dt>
          <dd>{heightSummaryEntity.id}</dd>
          <dt>
            <span id="usuarioId">
              <Translate contentKey="b4CarecollectApp.heightSummary.usuarioId">Usuario Id</Translate>
            </span>
          </dt>
          <dd>{heightSummaryEntity.usuarioId}</dd>
          <dt>
            <span id="empresaId">
              <Translate contentKey="b4CarecollectApp.heightSummary.empresaId">Empresa Id</Translate>
            </span>
          </dt>
          <dd>{heightSummaryEntity.empresaId}</dd>
          <dt>
            <span id="fieldAverage">
              <Translate contentKey="b4CarecollectApp.heightSummary.fieldAverage">Field Average</Translate>
            </span>
          </dt>
          <dd>{heightSummaryEntity.fieldAverage}</dd>
          <dt>
            <span id="fieldMax">
              <Translate contentKey="b4CarecollectApp.heightSummary.fieldMax">Field Max</Translate>
            </span>
          </dt>
          <dd>{heightSummaryEntity.fieldMax}</dd>
          <dt>
            <span id="fieldMin">
              <Translate contentKey="b4CarecollectApp.heightSummary.fieldMin">Field Min</Translate>
            </span>
          </dt>
          <dd>{heightSummaryEntity.fieldMin}</dd>
          <dt>
            <span id="startTime">
              <Translate contentKey="b4CarecollectApp.heightSummary.startTime">Start Time</Translate>
            </span>
          </dt>
          <dd>
            {heightSummaryEntity.startTime ? (
              <TextFormat value={heightSummaryEntity.startTime} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="endTime">
              <Translate contentKey="b4CarecollectApp.heightSummary.endTime">End Time</Translate>
            </span>
          </dt>
          <dd>
            {heightSummaryEntity.endTime ? <TextFormat value={heightSummaryEntity.endTime} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
        </dl>
        <Button tag={Link} to="/height-summary" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/height-summary/${heightSummaryEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default HeightSummaryDetail;
