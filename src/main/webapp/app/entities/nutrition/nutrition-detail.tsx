import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, byteSize, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './nutrition.reducer';

export const NutritionDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const nutritionEntity = useAppSelector(state => state.nutrition.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="nutritionDetailsHeading">
          <Translate contentKey="b4CarecollectApp.nutrition.detail.title">Nutrition</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="b4CarecollectApp.nutrition.id">Id</Translate>
            </span>
          </dt>
          <dd>{nutritionEntity.id}</dd>
          <dt>
            <span id="usuarioId">
              <Translate contentKey="b4CarecollectApp.nutrition.usuarioId">Usuario Id</Translate>
            </span>
          </dt>
          <dd>{nutritionEntity.usuarioId}</dd>
          <dt>
            <span id="empresaId">
              <Translate contentKey="b4CarecollectApp.nutrition.empresaId">Empresa Id</Translate>
            </span>
          </dt>
          <dd>{nutritionEntity.empresaId}</dd>
          <dt>
            <span id="mealType">
              <Translate contentKey="b4CarecollectApp.nutrition.mealType">Meal Type</Translate>
            </span>
          </dt>
          <dd>{nutritionEntity.mealType}</dd>
          <dt>
            <span id="food">
              <Translate contentKey="b4CarecollectApp.nutrition.food">Food</Translate>
            </span>
          </dt>
          <dd>{nutritionEntity.food}</dd>
          <dt>
            <span id="nutrients">
              <Translate contentKey="b4CarecollectApp.nutrition.nutrients">Nutrients</Translate>
            </span>
          </dt>
          <dd>{nutritionEntity.nutrients}</dd>
          <dt>
            <span id="endTime">
              <Translate contentKey="b4CarecollectApp.nutrition.endTime">End Time</Translate>
            </span>
          </dt>
          <dd>{nutritionEntity.endTime ? <TextFormat value={nutritionEntity.endTime} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
        </dl>
        <Button tag={Link} to="/nutrition" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/nutrition/${nutritionEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default NutritionDetail;
