import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, byteSize, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './nutrition-summary.reducer';

export const NutritionSummaryDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const nutritionSummaryEntity = useAppSelector(state => state.nutritionSummary.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="nutritionSummaryDetailsHeading">
          <Translate contentKey="b4CarecollectApp.nutritionSummary.detail.title">NutritionSummary</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="b4CarecollectApp.nutritionSummary.id">Id</Translate>
            </span>
          </dt>
          <dd>{nutritionSummaryEntity.id}</dd>
          <dt>
            <span id="usuarioId">
              <Translate contentKey="b4CarecollectApp.nutritionSummary.usuarioId">Usuario Id</Translate>
            </span>
          </dt>
          <dd>{nutritionSummaryEntity.usuarioId}</dd>
          <dt>
            <span id="empresaId">
              <Translate contentKey="b4CarecollectApp.nutritionSummary.empresaId">Empresa Id</Translate>
            </span>
          </dt>
          <dd>{nutritionSummaryEntity.empresaId}</dd>
          <dt>
            <span id="mealType">
              <Translate contentKey="b4CarecollectApp.nutritionSummary.mealType">Meal Type</Translate>
            </span>
          </dt>
          <dd>{nutritionSummaryEntity.mealType}</dd>
          <dt>
            <span id="nutrient">
              <Translate contentKey="b4CarecollectApp.nutritionSummary.nutrient">Nutrient</Translate>
            </span>
          </dt>
          <dd>{nutritionSummaryEntity.nutrient}</dd>
          <dt>
            <span id="startTime">
              <Translate contentKey="b4CarecollectApp.nutritionSummary.startTime">Start Time</Translate>
            </span>
          </dt>
          <dd>
            {nutritionSummaryEntity.startTime ? (
              <TextFormat value={nutritionSummaryEntity.startTime} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="endTime">
              <Translate contentKey="b4CarecollectApp.nutritionSummary.endTime">End Time</Translate>
            </span>
          </dt>
          <dd>
            {nutritionSummaryEntity.endTime ? (
              <TextFormat value={nutritionSummaryEntity.endTime} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
        </dl>
        <Button tag={Link} to="/nutrition-summary" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/nutrition-summary/${nutritionSummaryEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default NutritionSummaryDetail;
