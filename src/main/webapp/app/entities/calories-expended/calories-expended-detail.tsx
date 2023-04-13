import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './calories-expended.reducer';

export const CaloriesExpendedDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const caloriesExpendedEntity = useAppSelector(state => state.caloriesExpended.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="caloriesExpendedDetailsHeading">
          <Translate contentKey="b4CarecollectApp.caloriesExpended.detail.title">CaloriesExpended</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="b4CarecollectApp.caloriesExpended.id">Id</Translate>
            </span>
          </dt>
          <dd>{caloriesExpendedEntity.id}</dd>
          <dt>
            <span id="usuarioId">
              <Translate contentKey="b4CarecollectApp.caloriesExpended.usuarioId">Usuario Id</Translate>
            </span>
          </dt>
          <dd>{caloriesExpendedEntity.usuarioId}</dd>
          <dt>
            <span id="empresaId">
              <Translate contentKey="b4CarecollectApp.caloriesExpended.empresaId">Empresa Id</Translate>
            </span>
          </dt>
          <dd>{caloriesExpendedEntity.empresaId}</dd>
          <dt>
            <span id="calorias">
              <Translate contentKey="b4CarecollectApp.caloriesExpended.calorias">Calorias</Translate>
            </span>
          </dt>
          <dd>{caloriesExpendedEntity.calorias}</dd>
          <dt>
            <span id="startTime">
              <Translate contentKey="b4CarecollectApp.caloriesExpended.startTime">Start Time</Translate>
            </span>
          </dt>
          <dd>
            {caloriesExpendedEntity.startTime ? (
              <TextFormat value={caloriesExpendedEntity.startTime} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="endTime">
              <Translate contentKey="b4CarecollectApp.caloriesExpended.endTime">End Time</Translate>
            </span>
          </dt>
          <dd>
            {caloriesExpendedEntity.endTime ? (
              <TextFormat value={caloriesExpendedEntity.endTime} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
        </dl>
        <Button tag={Link} to="/calories-expended" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/calories-expended/${caloriesExpendedEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default CaloriesExpendedDetail;
