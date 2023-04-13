import React, { useState, useEffect } from 'react';
import InfiniteScroll from 'react-infinite-scroll-component';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, TextFormat, getSortState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ASC, DESC, ITEMS_PER_PAGE, SORT } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IAlarmRiskIndexSummary } from 'app/shared/model/alarm-risk-index-summary.model';
import { getEntities, reset } from './alarm-risk-index-summary.reducer';

export const AlarmRiskIndexSummary = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getSortState(location, ITEMS_PER_PAGE, 'id'), location.search)
  );
  const [sorting, setSorting] = useState(false);

  const alarmRiskIndexSummaryList = useAppSelector(state => state.alarmRiskIndexSummary.entities);
  const loading = useAppSelector(state => state.alarmRiskIndexSummary.loading);
  const totalItems = useAppSelector(state => state.alarmRiskIndexSummary.totalItems);
  const links = useAppSelector(state => state.alarmRiskIndexSummary.links);
  const entity = useAppSelector(state => state.alarmRiskIndexSummary.entity);
  const updateSuccess = useAppSelector(state => state.alarmRiskIndexSummary.updateSuccess);

  const getAllEntities = () => {
    dispatch(
      getEntities({
        page: paginationState.activePage - 1,
        size: paginationState.itemsPerPage,
        sort: `${paginationState.sort},${paginationState.order}`,
      })
    );
  };

  const resetAll = () => {
    dispatch(reset());
    setPaginationState({
      ...paginationState,
      activePage: 1,
    });
    dispatch(getEntities({}));
  };

  useEffect(() => {
    resetAll();
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      resetAll();
    }
  }, [updateSuccess]);

  useEffect(() => {
    getAllEntities();
  }, [paginationState.activePage]);

  const handleLoadMore = () => {
    if ((window as any).pageYOffset > 0) {
      setPaginationState({
        ...paginationState,
        activePage: paginationState.activePage + 1,
      });
    }
  };

  useEffect(() => {
    if (sorting) {
      getAllEntities();
      setSorting(false);
    }
  }, [sorting]);

  const sort = p => () => {
    dispatch(reset());
    setPaginationState({
      ...paginationState,
      activePage: 1,
      order: paginationState.order === ASC ? DESC : ASC,
      sort: p,
    });
    setSorting(true);
  };

  const handleSyncList = () => {
    resetAll();
  };

  return (
    <div>
      <h2 id="alarm-risk-index-summary-heading" data-cy="AlarmRiskIndexSummaryHeading">
        <Translate contentKey="b4CarecollectApp.alarmRiskIndexSummary.home.title">Alarm Risk Index Summaries</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="b4CarecollectApp.alarmRiskIndexSummary.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link
            to="/alarm-risk-index-summary/new"
            className="btn btn-primary jh-create-entity"
            id="jh-create-entity"
            data-cy="entityCreateButton"
          >
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="b4CarecollectApp.alarmRiskIndexSummary.home.createLabel">Create new Alarm Risk Index Summary</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        <InfiniteScroll
          dataLength={alarmRiskIndexSummaryList ? alarmRiskIndexSummaryList.length : 0}
          next={handleLoadMore}
          hasMore={paginationState.activePage - 1 < links.next}
          loader={<div className="loader">Loading ...</div>}
        >
          {alarmRiskIndexSummaryList && alarmRiskIndexSummaryList.length > 0 ? (
            <Table responsive>
              <thead>
                <tr>
                  <th className="hand" onClick={sort('id')}>
                    <Translate contentKey="b4CarecollectApp.alarmRiskIndexSummary.id">Id</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('usuarioId')}>
                    <Translate contentKey="b4CarecollectApp.alarmRiskIndexSummary.usuarioId">Usuario Id</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('empresaId')}>
                    <Translate contentKey="b4CarecollectApp.alarmRiskIndexSummary.empresaId">Empresa Id</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('fieldAlarmRiskAverage')}>
                    <Translate contentKey="b4CarecollectApp.alarmRiskIndexSummary.fieldAlarmRiskAverage">
                      Field Alarm Risk Average
                    </Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('fieldAlarmRiskMax')}>
                    <Translate contentKey="b4CarecollectApp.alarmRiskIndexSummary.fieldAlarmRiskMax">Field Alarm Risk Max</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('fieldAlarmRiskMin')}>
                    <Translate contentKey="b4CarecollectApp.alarmRiskIndexSummary.fieldAlarmRiskMin">Field Alarm Risk Min</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('startTime')}>
                    <Translate contentKey="b4CarecollectApp.alarmRiskIndexSummary.startTime">Start Time</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('endTime')}>
                    <Translate contentKey="b4CarecollectApp.alarmRiskIndexSummary.endTime">End Time</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th />
                </tr>
              </thead>
              <tbody>
                {alarmRiskIndexSummaryList.map((alarmRiskIndexSummary, i) => (
                  <tr key={`entity-${i}`} data-cy="entityTable">
                    <td>
                      <Button tag={Link} to={`/alarm-risk-index-summary/${alarmRiskIndexSummary.id}`} color="link" size="sm">
                        {alarmRiskIndexSummary.id}
                      </Button>
                    </td>
                    <td>{alarmRiskIndexSummary.usuarioId}</td>
                    <td>{alarmRiskIndexSummary.empresaId}</td>
                    <td>{alarmRiskIndexSummary.fieldAlarmRiskAverage}</td>
                    <td>{alarmRiskIndexSummary.fieldAlarmRiskMax}</td>
                    <td>{alarmRiskIndexSummary.fieldAlarmRiskMin}</td>
                    <td>
                      {alarmRiskIndexSummary.startTime ? (
                        <TextFormat type="date" value={alarmRiskIndexSummary.startTime} format={APP_DATE_FORMAT} />
                      ) : null}
                    </td>
                    <td>
                      {alarmRiskIndexSummary.endTime ? (
                        <TextFormat type="date" value={alarmRiskIndexSummary.endTime} format={APP_DATE_FORMAT} />
                      ) : null}
                    </td>
                    <td className="text-end">
                      <div className="btn-group flex-btn-group-container">
                        <Button
                          tag={Link}
                          to={`/alarm-risk-index-summary/${alarmRiskIndexSummary.id}`}
                          color="info"
                          size="sm"
                          data-cy="entityDetailsButton"
                        >
                          <FontAwesomeIcon icon="eye" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.view">View</Translate>
                          </span>
                        </Button>
                        <Button
                          tag={Link}
                          to={`/alarm-risk-index-summary/${alarmRiskIndexSummary.id}/edit`}
                          color="primary"
                          size="sm"
                          data-cy="entityEditButton"
                        >
                          <FontAwesomeIcon icon="pencil-alt" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.edit">Edit</Translate>
                          </span>
                        </Button>
                        <Button
                          tag={Link}
                          to={`/alarm-risk-index-summary/${alarmRiskIndexSummary.id}/delete`}
                          color="danger"
                          size="sm"
                          data-cy="entityDeleteButton"
                        >
                          <FontAwesomeIcon icon="trash" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.delete">Delete</Translate>
                          </span>
                        </Button>
                      </div>
                    </td>
                  </tr>
                ))}
              </tbody>
            </Table>
          ) : (
            !loading && (
              <div className="alert alert-warning">
                <Translate contentKey="b4CarecollectApp.alarmRiskIndexSummary.home.notFound">No Alarm Risk Index Summaries found</Translate>
              </div>
            )
          )}
        </InfiniteScroll>
      </div>
    </div>
  );
};

export default AlarmRiskIndexSummary;
