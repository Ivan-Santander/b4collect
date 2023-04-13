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

import { IBloodPressureSummary } from 'app/shared/model/blood-pressure-summary.model';
import { getEntities, reset } from './blood-pressure-summary.reducer';

export const BloodPressureSummary = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getSortState(location, ITEMS_PER_PAGE, 'id'), location.search)
  );
  const [sorting, setSorting] = useState(false);

  const bloodPressureSummaryList = useAppSelector(state => state.bloodPressureSummary.entities);
  const loading = useAppSelector(state => state.bloodPressureSummary.loading);
  const totalItems = useAppSelector(state => state.bloodPressureSummary.totalItems);
  const links = useAppSelector(state => state.bloodPressureSummary.links);
  const entity = useAppSelector(state => state.bloodPressureSummary.entity);
  const updateSuccess = useAppSelector(state => state.bloodPressureSummary.updateSuccess);

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
      <h2 id="blood-pressure-summary-heading" data-cy="BloodPressureSummaryHeading">
        <Translate contentKey="b4CarecollectApp.bloodPressureSummary.home.title">Blood Pressure Summaries</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="b4CarecollectApp.bloodPressureSummary.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link
            to="/blood-pressure-summary/new"
            className="btn btn-primary jh-create-entity"
            id="jh-create-entity"
            data-cy="entityCreateButton"
          >
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="b4CarecollectApp.bloodPressureSummary.home.createLabel">Create new Blood Pressure Summary</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        <InfiniteScroll
          dataLength={bloodPressureSummaryList ? bloodPressureSummaryList.length : 0}
          next={handleLoadMore}
          hasMore={paginationState.activePage - 1 < links.next}
          loader={<div className="loader">Loading ...</div>}
        >
          {bloodPressureSummaryList && bloodPressureSummaryList.length > 0 ? (
            <Table responsive>
              <thead>
                <tr>
                  <th className="hand" onClick={sort('id')}>
                    <Translate contentKey="b4CarecollectApp.bloodPressureSummary.id">Id</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('usuarioId')}>
                    <Translate contentKey="b4CarecollectApp.bloodPressureSummary.usuarioId">Usuario Id</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('empresaId')}>
                    <Translate contentKey="b4CarecollectApp.bloodPressureSummary.empresaId">Empresa Id</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('fieldSistolicAverage')}>
                    <Translate contentKey="b4CarecollectApp.bloodPressureSummary.fieldSistolicAverage">Field Sistolic Average</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('fieldSistolicMax')}>
                    <Translate contentKey="b4CarecollectApp.bloodPressureSummary.fieldSistolicMax">Field Sistolic Max</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('fieldSistolicMin')}>
                    <Translate contentKey="b4CarecollectApp.bloodPressureSummary.fieldSistolicMin">Field Sistolic Min</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('fieldDiasatolicAverage')}>
                    <Translate contentKey="b4CarecollectApp.bloodPressureSummary.fieldDiasatolicAverage">
                      Field Diasatolic Average
                    </Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('fieldDiastolicMax')}>
                    <Translate contentKey="b4CarecollectApp.bloodPressureSummary.fieldDiastolicMax">Field Diastolic Max</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('fieldDiastolicMin')}>
                    <Translate contentKey="b4CarecollectApp.bloodPressureSummary.fieldDiastolicMin">Field Diastolic Min</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('bodyPosition')}>
                    <Translate contentKey="b4CarecollectApp.bloodPressureSummary.bodyPosition">Body Position</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('measurementLocation')}>
                    <Translate contentKey="b4CarecollectApp.bloodPressureSummary.measurementLocation">Measurement Location</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('startTime')}>
                    <Translate contentKey="b4CarecollectApp.bloodPressureSummary.startTime">Start Time</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('endTime')}>
                    <Translate contentKey="b4CarecollectApp.bloodPressureSummary.endTime">End Time</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th />
                </tr>
              </thead>
              <tbody>
                {bloodPressureSummaryList.map((bloodPressureSummary, i) => (
                  <tr key={`entity-${i}`} data-cy="entityTable">
                    <td>
                      <Button tag={Link} to={`/blood-pressure-summary/${bloodPressureSummary.id}`} color="link" size="sm">
                        {bloodPressureSummary.id}
                      </Button>
                    </td>
                    <td>{bloodPressureSummary.usuarioId}</td>
                    <td>{bloodPressureSummary.empresaId}</td>
                    <td>{bloodPressureSummary.fieldSistolicAverage}</td>
                    <td>{bloodPressureSummary.fieldSistolicMax}</td>
                    <td>{bloodPressureSummary.fieldSistolicMin}</td>
                    <td>{bloodPressureSummary.fieldDiasatolicAverage}</td>
                    <td>{bloodPressureSummary.fieldDiastolicMax}</td>
                    <td>{bloodPressureSummary.fieldDiastolicMin}</td>
                    <td>{bloodPressureSummary.bodyPosition}</td>
                    <td>{bloodPressureSummary.measurementLocation}</td>
                    <td>
                      {bloodPressureSummary.startTime ? (
                        <TextFormat type="date" value={bloodPressureSummary.startTime} format={APP_DATE_FORMAT} />
                      ) : null}
                    </td>
                    <td>
                      {bloodPressureSummary.endTime ? (
                        <TextFormat type="date" value={bloodPressureSummary.endTime} format={APP_DATE_FORMAT} />
                      ) : null}
                    </td>
                    <td className="text-end">
                      <div className="btn-group flex-btn-group-container">
                        <Button
                          tag={Link}
                          to={`/blood-pressure-summary/${bloodPressureSummary.id}`}
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
                          to={`/blood-pressure-summary/${bloodPressureSummary.id}/edit`}
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
                          to={`/blood-pressure-summary/${bloodPressureSummary.id}/delete`}
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
                <Translate contentKey="b4CarecollectApp.bloodPressureSummary.home.notFound">No Blood Pressure Summaries found</Translate>
              </div>
            )
          )}
        </InfiniteScroll>
      </div>
    </div>
  );
};

export default BloodPressureSummary;
