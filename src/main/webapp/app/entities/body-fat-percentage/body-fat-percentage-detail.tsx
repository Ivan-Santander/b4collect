import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './body-fat-percentage.reducer';

export const BodyFatPercentageDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const bodyFatPercentageEntity = useAppSelector(state => state.bodyFatPercentage.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="bodyFatPercentageDetailsHeading">
          <Translate contentKey="b4CarecollectApp.bodyFatPercentage.detail.title">BodyFatPercentage</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="b4CarecollectApp.bodyFatPercentage.id">Id</Translate>
            </span>
          </dt>
          <dd>{bodyFatPercentageEntity.id}</dd>
          <dt>
            <span id="usuarioId">
              <Translate contentKey="b4CarecollectApp.bodyFatPercentage.usuarioId">Usuario Id</Translate>
            </span>
          </dt>
          <dd>{bodyFatPercentageEntity.usuarioId}</dd>
          <dt>
            <span id="empresaId">
              <Translate contentKey="b4CarecollectApp.bodyFatPercentage.empresaId">Empresa Id</Translate>
            </span>
          </dt>
          <dd>{bodyFatPercentageEntity.empresaId}</dd>
          <dt>
            <span id="fieldPorcentage">
              <Translate contentKey="b4CarecollectApp.bodyFatPercentage.fieldPorcentage">Field Porcentage</Translate>
            </span>
          </dt>
          <dd>{bodyFatPercentageEntity.fieldPorcentage}</dd>
          <dt>
            <span id="endTime">
              <Translate contentKey="b4CarecollectApp.bodyFatPercentage.endTime">End Time</Translate>
            </span>
          </dt>
          <dd>
            {bodyFatPercentageEntity.endTime ? (
              <TextFormat value={bodyFatPercentageEntity.endTime} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
        </dl>
        <Button tag={Link} to="/body-fat-percentage" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/body-fat-percentage/${bodyFatPercentageEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default BodyFatPercentageDetail;
