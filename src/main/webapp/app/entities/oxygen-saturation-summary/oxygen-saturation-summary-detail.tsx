import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './oxygen-saturation-summary.reducer';

export const OxygenSaturationSummaryDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const oxygenSaturationSummaryEntity = useAppSelector(state => state.oxygenSaturationSummary.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="oxygenSaturationSummaryDetailsHeading">
          <Translate contentKey="b4CarecollectApp.oxygenSaturationSummary.detail.title">OxygenSaturationSummary</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="b4CarecollectApp.oxygenSaturationSummary.id">Id</Translate>
            </span>
          </dt>
          <dd>{oxygenSaturationSummaryEntity.id}</dd>
          <dt>
            <span id="usuarioId">
              <Translate contentKey="b4CarecollectApp.oxygenSaturationSummary.usuarioId">Usuario Id</Translate>
            </span>
          </dt>
          <dd>{oxygenSaturationSummaryEntity.usuarioId}</dd>
          <dt>
            <span id="empresaId">
              <Translate contentKey="b4CarecollectApp.oxygenSaturationSummary.empresaId">Empresa Id</Translate>
            </span>
          </dt>
          <dd>{oxygenSaturationSummaryEntity.empresaId}</dd>
          <dt>
            <span id="fieldOxigenSaturationAverage">
              <Translate contentKey="b4CarecollectApp.oxygenSaturationSummary.fieldOxigenSaturationAverage">
                Field Oxigen Saturation Average
              </Translate>
            </span>
          </dt>
          <dd>{oxygenSaturationSummaryEntity.fieldOxigenSaturationAverage}</dd>
          <dt>
            <span id="fieldOxigenSaturationMax">
              <Translate contentKey="b4CarecollectApp.oxygenSaturationSummary.fieldOxigenSaturationMax">
                Field Oxigen Saturation Max
              </Translate>
            </span>
          </dt>
          <dd>{oxygenSaturationSummaryEntity.fieldOxigenSaturationMax}</dd>
          <dt>
            <span id="fieldOxigenSaturationMin">
              <Translate contentKey="b4CarecollectApp.oxygenSaturationSummary.fieldOxigenSaturationMin">
                Field Oxigen Saturation Min
              </Translate>
            </span>
          </dt>
          <dd>{oxygenSaturationSummaryEntity.fieldOxigenSaturationMin}</dd>
          <dt>
            <span id="fieldSuplementalOxigenFlowRateAverage">
              <Translate contentKey="b4CarecollectApp.oxygenSaturationSummary.fieldSuplementalOxigenFlowRateAverage">
                Field Suplemental Oxigen Flow Rate Average
              </Translate>
            </span>
          </dt>
          <dd>{oxygenSaturationSummaryEntity.fieldSuplementalOxigenFlowRateAverage}</dd>
          <dt>
            <span id="fieldSuplementalOxigenFlowRateMax">
              <Translate contentKey="b4CarecollectApp.oxygenSaturationSummary.fieldSuplementalOxigenFlowRateMax">
                Field Suplemental Oxigen Flow Rate Max
              </Translate>
            </span>
          </dt>
          <dd>{oxygenSaturationSummaryEntity.fieldSuplementalOxigenFlowRateMax}</dd>
          <dt>
            <span id="fieldSuplementalOxigenFlowRateMin">
              <Translate contentKey="b4CarecollectApp.oxygenSaturationSummary.fieldSuplementalOxigenFlowRateMin">
                Field Suplemental Oxigen Flow Rate Min
              </Translate>
            </span>
          </dt>
          <dd>{oxygenSaturationSummaryEntity.fieldSuplementalOxigenFlowRateMin}</dd>
          <dt>
            <span id="fieldOxigenTherapyAdministrationMode">
              <Translate contentKey="b4CarecollectApp.oxygenSaturationSummary.fieldOxigenTherapyAdministrationMode">
                Field Oxigen Therapy Administration Mode
              </Translate>
            </span>
          </dt>
          <dd>{oxygenSaturationSummaryEntity.fieldOxigenTherapyAdministrationMode}</dd>
          <dt>
            <span id="fieldOxigenSaturationMode">
              <Translate contentKey="b4CarecollectApp.oxygenSaturationSummary.fieldOxigenSaturationMode">
                Field Oxigen Saturation Mode
              </Translate>
            </span>
          </dt>
          <dd>{oxygenSaturationSummaryEntity.fieldOxigenSaturationMode}</dd>
          <dt>
            <span id="fieldOxigenSaturationMeasurementMethod">
              <Translate contentKey="b4CarecollectApp.oxygenSaturationSummary.fieldOxigenSaturationMeasurementMethod">
                Field Oxigen Saturation Measurement Method
              </Translate>
            </span>
          </dt>
          <dd>{oxygenSaturationSummaryEntity.fieldOxigenSaturationMeasurementMethod}</dd>
          <dt>
            <span id="endTime">
              <Translate contentKey="b4CarecollectApp.oxygenSaturationSummary.endTime">End Time</Translate>
            </span>
          </dt>
          <dd>
            {oxygenSaturationSummaryEntity.endTime ? (
              <TextFormat value={oxygenSaturationSummaryEntity.endTime} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
        </dl>
        <Button tag={Link} to="/oxygen-saturation-summary" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/oxygen-saturation-summary/${oxygenSaturationSummaryEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default OxygenSaturationSummaryDetail;
