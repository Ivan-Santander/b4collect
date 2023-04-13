import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './sleep-segment.reducer';

export const SleepSegmentDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const sleepSegmentEntity = useAppSelector(state => state.sleepSegment.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="sleepSegmentDetailsHeading">
          <Translate contentKey="b4CarecollectApp.sleepSegment.detail.title">SleepSegment</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="b4CarecollectApp.sleepSegment.id">Id</Translate>
            </span>
          </dt>
          <dd>{sleepSegmentEntity.id}</dd>
          <dt>
            <span id="usuarioId">
              <Translate contentKey="b4CarecollectApp.sleepSegment.usuarioId">Usuario Id</Translate>
            </span>
          </dt>
          <dd>{sleepSegmentEntity.usuarioId}</dd>
          <dt>
            <span id="empresaId">
              <Translate contentKey="b4CarecollectApp.sleepSegment.empresaId">Empresa Id</Translate>
            </span>
          </dt>
          <dd>{sleepSegmentEntity.empresaId}</dd>
          <dt>
            <span id="fieldSleepSegmentType">
              <Translate contentKey="b4CarecollectApp.sleepSegment.fieldSleepSegmentType">Field Sleep Segment Type</Translate>
            </span>
          </dt>
          <dd>{sleepSegmentEntity.fieldSleepSegmentType}</dd>
          <dt>
            <span id="fieldBloodGlucoseSpecimenSource">
              <Translate contentKey="b4CarecollectApp.sleepSegment.fieldBloodGlucoseSpecimenSource">
                Field Blood Glucose Specimen Source
              </Translate>
            </span>
          </dt>
          <dd>{sleepSegmentEntity.fieldBloodGlucoseSpecimenSource}</dd>
          <dt>
            <span id="startTime">
              <Translate contentKey="b4CarecollectApp.sleepSegment.startTime">Start Time</Translate>
            </span>
          </dt>
          <dd>
            {sleepSegmentEntity.startTime ? <TextFormat value={sleepSegmentEntity.startTime} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="endTime">
              <Translate contentKey="b4CarecollectApp.sleepSegment.endTime">End Time</Translate>
            </span>
          </dt>
          <dd>
            {sleepSegmentEntity.endTime ? <TextFormat value={sleepSegmentEntity.endTime} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
        </dl>
        <Button tag={Link} to="/sleep-segment" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/sleep-segment/${sleepSegmentEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default SleepSegmentDetail;
