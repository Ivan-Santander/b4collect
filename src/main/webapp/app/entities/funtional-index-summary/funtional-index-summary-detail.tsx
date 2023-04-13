import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './funtional-index-summary.reducer';

export const FuntionalIndexSummaryDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const funtionalIndexSummaryEntity = useAppSelector(state => state.funtionalIndexSummary.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="funtionalIndexSummaryDetailsHeading">
          <Translate contentKey="b4CarecollectApp.funtionalIndexSummary.detail.title">FuntionalIndexSummary</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="b4CarecollectApp.funtionalIndexSummary.id">Id</Translate>
            </span>
          </dt>
          <dd>{funtionalIndexSummaryEntity.id}</dd>
          <dt>
            <span id="usuarioId">
              <Translate contentKey="b4CarecollectApp.funtionalIndexSummary.usuarioId">Usuario Id</Translate>
            </span>
          </dt>
          <dd>{funtionalIndexSummaryEntity.usuarioId}</dd>
          <dt>
            <span id="empresaId">
              <Translate contentKey="b4CarecollectApp.funtionalIndexSummary.empresaId">Empresa Id</Translate>
            </span>
          </dt>
          <dd>{funtionalIndexSummaryEntity.empresaId}</dd>
          <dt>
            <span id="fieldFuntionalIndexAverage">
              <Translate contentKey="b4CarecollectApp.funtionalIndexSummary.fieldFuntionalIndexAverage">
                Field Funtional Index Average
              </Translate>
            </span>
          </dt>
          <dd>{funtionalIndexSummaryEntity.fieldFuntionalIndexAverage}</dd>
          <dt>
            <span id="fieldFuntionalIndexMax">
              <Translate contentKey="b4CarecollectApp.funtionalIndexSummary.fieldFuntionalIndexMax">Field Funtional Index Max</Translate>
            </span>
          </dt>
          <dd>{funtionalIndexSummaryEntity.fieldFuntionalIndexMax}</dd>
          <dt>
            <span id="fieldFuntionalIndexMin">
              <Translate contentKey="b4CarecollectApp.funtionalIndexSummary.fieldFuntionalIndexMin">Field Funtional Index Min</Translate>
            </span>
          </dt>
          <dd>{funtionalIndexSummaryEntity.fieldFuntionalIndexMin}</dd>
          <dt>
            <span id="startTime">
              <Translate contentKey="b4CarecollectApp.funtionalIndexSummary.startTime">Start Time</Translate>
            </span>
          </dt>
          <dd>
            {funtionalIndexSummaryEntity.startTime ? (
              <TextFormat value={funtionalIndexSummaryEntity.startTime} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="endTime">
              <Translate contentKey="b4CarecollectApp.funtionalIndexSummary.endTime">End Time</Translate>
            </span>
          </dt>
          <dd>
            {funtionalIndexSummaryEntity.endTime ? (
              <TextFormat value={funtionalIndexSummaryEntity.endTime} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
        </dl>
        <Button tag={Link} to="/funtional-index-summary" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/funtional-index-summary/${funtionalIndexSummaryEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default FuntionalIndexSummaryDetail;
