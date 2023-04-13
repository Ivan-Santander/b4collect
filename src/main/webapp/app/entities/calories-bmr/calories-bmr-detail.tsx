import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './calories-bmr.reducer';

export const CaloriesBMRDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const caloriesBMREntity = useAppSelector(state => state.caloriesBMR.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="caloriesBMRDetailsHeading">
          <Translate contentKey="b4CarecollectApp.caloriesBMR.detail.title">CaloriesBMR</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="b4CarecollectApp.caloriesBMR.id">Id</Translate>
            </span>
          </dt>
          <dd>{caloriesBMREntity.id}</dd>
          <dt>
            <span id="usuarioId">
              <Translate contentKey="b4CarecollectApp.caloriesBMR.usuarioId">Usuario Id</Translate>
            </span>
          </dt>
          <dd>{caloriesBMREntity.usuarioId}</dd>
          <dt>
            <span id="empresaId">
              <Translate contentKey="b4CarecollectApp.caloriesBMR.empresaId">Empresa Id</Translate>
            </span>
          </dt>
          <dd>{caloriesBMREntity.empresaId}</dd>
          <dt>
            <span id="calorias">
              <Translate contentKey="b4CarecollectApp.caloriesBMR.calorias">Calorias</Translate>
            </span>
          </dt>
          <dd>{caloriesBMREntity.calorias}</dd>
          <dt>
            <span id="startTime">
              <Translate contentKey="b4CarecollectApp.caloriesBMR.startTime">Start Time</Translate>
            </span>
          </dt>
          <dd>
            {caloriesBMREntity.startTime ? <TextFormat value={caloriesBMREntity.startTime} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="endTime">
              <Translate contentKey="b4CarecollectApp.caloriesBMR.endTime">End Time</Translate>
            </span>
          </dt>
          <dd>
            {caloriesBMREntity.endTime ? <TextFormat value={caloriesBMREntity.endTime} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
        </dl>
        <Button tag={Link} to="/calories-bmr" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/calories-bmr/${caloriesBMREntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default CaloriesBMRDetail;
