import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './location-sample.reducer';

export const LocationSampleDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const locationSampleEntity = useAppSelector(state => state.locationSample.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="locationSampleDetailsHeading">
          <Translate contentKey="b4CarecollectApp.locationSample.detail.title">LocationSample</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="b4CarecollectApp.locationSample.id">Id</Translate>
            </span>
          </dt>
          <dd>{locationSampleEntity.id}</dd>
          <dt>
            <span id="usuarioId">
              <Translate contentKey="b4CarecollectApp.locationSample.usuarioId">Usuario Id</Translate>
            </span>
          </dt>
          <dd>{locationSampleEntity.usuarioId}</dd>
          <dt>
            <span id="empresaId">
              <Translate contentKey="b4CarecollectApp.locationSample.empresaId">Empresa Id</Translate>
            </span>
          </dt>
          <dd>{locationSampleEntity.empresaId}</dd>
          <dt>
            <span id="latitudMin">
              <Translate contentKey="b4CarecollectApp.locationSample.latitudMin">Latitud Min</Translate>
            </span>
          </dt>
          <dd>{locationSampleEntity.latitudMin}</dd>
          <dt>
            <span id="longitudMin">
              <Translate contentKey="b4CarecollectApp.locationSample.longitudMin">Longitud Min</Translate>
            </span>
          </dt>
          <dd>{locationSampleEntity.longitudMin}</dd>
          <dt>
            <span id="latitudMax">
              <Translate contentKey="b4CarecollectApp.locationSample.latitudMax">Latitud Max</Translate>
            </span>
          </dt>
          <dd>{locationSampleEntity.latitudMax}</dd>
          <dt>
            <span id="longitudMax">
              <Translate contentKey="b4CarecollectApp.locationSample.longitudMax">Longitud Max</Translate>
            </span>
          </dt>
          <dd>{locationSampleEntity.longitudMax}</dd>
          <dt>
            <span id="accuracy">
              <Translate contentKey="b4CarecollectApp.locationSample.accuracy">Accuracy</Translate>
            </span>
          </dt>
          <dd>{locationSampleEntity.accuracy}</dd>
          <dt>
            <span id="altitud">
              <Translate contentKey="b4CarecollectApp.locationSample.altitud">Altitud</Translate>
            </span>
          </dt>
          <dd>{locationSampleEntity.altitud}</dd>
          <dt>
            <span id="startTime">
              <Translate contentKey="b4CarecollectApp.locationSample.startTime">Start Time</Translate>
            </span>
          </dt>
          <dd>
            {locationSampleEntity.startTime ? (
              <TextFormat value={locationSampleEntity.startTime} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="endTime">
              <Translate contentKey="b4CarecollectApp.locationSample.endTime">End Time</Translate>
            </span>
          </dt>
          <dd>
            {locationSampleEntity.endTime ? <TextFormat value={locationSampleEntity.endTime} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
        </dl>
        <Button tag={Link} to="/location-sample" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/location-sample/${locationSampleEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default LocationSampleDetail;
