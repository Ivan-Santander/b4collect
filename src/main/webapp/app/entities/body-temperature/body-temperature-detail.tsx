import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './body-temperature.reducer';

export const BodyTemperatureDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const bodyTemperatureEntity = useAppSelector(state => state.bodyTemperature.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="bodyTemperatureDetailsHeading">
          <Translate contentKey="b4CarecollectApp.bodyTemperature.detail.title">BodyTemperature</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="b4CarecollectApp.bodyTemperature.id">Id</Translate>
            </span>
          </dt>
          <dd>{bodyTemperatureEntity.id}</dd>
          <dt>
            <span id="usuarioId">
              <Translate contentKey="b4CarecollectApp.bodyTemperature.usuarioId">Usuario Id</Translate>
            </span>
          </dt>
          <dd>{bodyTemperatureEntity.usuarioId}</dd>
          <dt>
            <span id="empresaId">
              <Translate contentKey="b4CarecollectApp.bodyTemperature.empresaId">Empresa Id</Translate>
            </span>
          </dt>
          <dd>{bodyTemperatureEntity.empresaId}</dd>
          <dt>
            <span id="fieldBodyTemperature">
              <Translate contentKey="b4CarecollectApp.bodyTemperature.fieldBodyTemperature">Field Body Temperature</Translate>
            </span>
          </dt>
          <dd>{bodyTemperatureEntity.fieldBodyTemperature}</dd>
          <dt>
            <span id="fieldBodyTemperatureMeasureLocation">
              <Translate contentKey="b4CarecollectApp.bodyTemperature.fieldBodyTemperatureMeasureLocation">
                Field Body Temperature Measure Location
              </Translate>
            </span>
          </dt>
          <dd>{bodyTemperatureEntity.fieldBodyTemperatureMeasureLocation}</dd>
          <dt>
            <span id="endTime">
              <Translate contentKey="b4CarecollectApp.bodyTemperature.endTime">End Time</Translate>
            </span>
          </dt>
          <dd>
            {bodyTemperatureEntity.endTime ? (
              <TextFormat value={bodyTemperatureEntity.endTime} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
        </dl>
        <Button tag={Link} to="/body-temperature" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/body-temperature/${bodyTemperatureEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default BodyTemperatureDetail;
