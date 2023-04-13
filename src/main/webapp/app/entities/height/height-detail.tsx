import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './height.reducer';

export const HeightDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const heightEntity = useAppSelector(state => state.height.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="heightDetailsHeading">
          <Translate contentKey="b4CarecollectApp.height.detail.title">Height</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="b4CarecollectApp.height.id">Id</Translate>
            </span>
          </dt>
          <dd>{heightEntity.id}</dd>
          <dt>
            <span id="usuarioId">
              <Translate contentKey="b4CarecollectApp.height.usuarioId">Usuario Id</Translate>
            </span>
          </dt>
          <dd>{heightEntity.usuarioId}</dd>
          <dt>
            <span id="empresaId">
              <Translate contentKey="b4CarecollectApp.height.empresaId">Empresa Id</Translate>
            </span>
          </dt>
          <dd>{heightEntity.empresaId}</dd>
          <dt>
            <span id="fieldHeight">
              <Translate contentKey="b4CarecollectApp.height.fieldHeight">Field Height</Translate>
            </span>
          </dt>
          <dd>{heightEntity.fieldHeight}</dd>
          <dt>
            <span id="endTime">
              <Translate contentKey="b4CarecollectApp.height.endTime">End Time</Translate>
            </span>
          </dt>
          <dd>{heightEntity.endTime ? <TextFormat value={heightEntity.endTime} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
        </dl>
        <Button tag={Link} to="/height" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/height/${heightEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default HeightDetail;
