import React, { useState, useEffect } from 'react';
import InfiniteScroll from 'react-infinite-scroll-component';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { byteSize, Translate, TextFormat, getSortState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ASC, DESC, ITEMS_PER_PAGE, SORT } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IBloodGlucoseSummary } from 'app/shared/model/blood-glucose-summary.model';
import { getEntities, reset } from './blood-glucose-summary.reducer';

export const BloodGlucoseSummary = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getSortState(location, ITEMS_PER_PAGE, 'id'), location.search)
  );
  const [sorting, setSorting] = useState(false);

  const bloodGlucoseSummaryList = useAppSelector(state => state.bloodGlucoseSummary.entities);
  const loading = useAppSelector(state => state.bloodGlucoseSummary.loading);
  const totalItems = useAppSelector(state => state.bloodGlucoseSummary.totalItems);
  const links = useAppSelector(state => state.bloodGlucoseSummary.links);
  const entity = useAppSelector(state => state.bloodGlucoseSummary.entity);
  const updateSuccess = useAppSelector(state => state.bloodGlucoseSummary.updateSuccess);

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
      <h2 id="blood-glucose-summary-heading" data-cy="BloodGlucoseSummaryHeading">
        <Translate contentKey="b4CarecollectApp.bloodGlucoseSummary.home.title">Blood Glucose Summaries</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="b4CarecollectApp.bloodGlucoseSummary.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link
            to="/blood-glucose-summary/new"
            className="btn btn-primary jh-create-entity"
            id="jh-create-entity"
            data-cy="entityCreateButton"
          >
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="b4CarecollectApp.bloodGlucoseSummary.home.createLabel">Create new Blood Glucose Summary</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        <InfiniteScroll
          dataLength={bloodGlucoseSummaryList ? bloodGlucoseSummaryList.length : 0}
          next={handleLoadMore}
          hasMore={paginationState.activePage - 1 < links.next}
          loader={<div className="loader">Loading ...</div>}
        >
          {bloodGlucoseSummaryList && bloodGlucoseSummaryList.length > 0 ? (
            <Table responsive>
              <thead>
                <tr>
                  <th className="hand" onClick={sort('id')}>
                    <Translate contentKey="b4CarecollectApp.bloodGlucoseSummary.id">Id</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('usuarioId')}>
                    <Translate contentKey="b4CarecollectApp.bloodGlucoseSummary.usuarioId">Usuario Id</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('empresaId')}>
                    <Translate contentKey="b4CarecollectApp.bloodGlucoseSummary.empresaId">Empresa Id</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('fieldAverage')}>
                    <Translate contentKey="b4CarecollectApp.bloodGlucoseSummary.fieldAverage">Field Average</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('fieldMax')}>
                    <Translate contentKey="b4CarecollectApp.bloodGlucoseSummary.fieldMax">Field Max</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('fieldMin')}>
                    <Translate contentKey="b4CarecollectApp.bloodGlucoseSummary.fieldMin">Field Min</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('intervalFood')}>
                    <Translate contentKey="b4CarecollectApp.bloodGlucoseSummary.intervalFood">Interval Food</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('mealType')}>
                    <Translate contentKey="b4CarecollectApp.bloodGlucoseSummary.mealType">Meal Type</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('relationTemporalSleep')}>
                    <Translate contentKey="b4CarecollectApp.bloodGlucoseSummary.relationTemporalSleep">Relation Temporal Sleep</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('sampleSource')}>
                    <Translate contentKey="b4CarecollectApp.bloodGlucoseSummary.sampleSource">Sample Source</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('startTime')}>
                    <Translate contentKey="b4CarecollectApp.bloodGlucoseSummary.startTime">Start Time</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('endTime')}>
                    <Translate contentKey="b4CarecollectApp.bloodGlucoseSummary.endTime">End Time</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th />
                </tr>
              </thead>
              <tbody>
                {bloodGlucoseSummaryList.map((bloodGlucoseSummary, i) => (
                  <tr key={`entity-${i}`} data-cy="entityTable">
                    <td>
                      <Button tag={Link} to={`/blood-glucose-summary/${bloodGlucoseSummary.id}`} color="link" size="sm">
                        {bloodGlucoseSummary.id}
                      </Button>
                    </td>
                    <td>{bloodGlucoseSummary.usuarioId}</td>
                    <td>{bloodGlucoseSummary.empresaId}</td>
                    <td>{bloodGlucoseSummary.fieldAverage}</td>
                    <td>{bloodGlucoseSummary.fieldMax}</td>
                    <td>{bloodGlucoseSummary.fieldMin}</td>
                    <td>{bloodGlucoseSummary.intervalFood}</td>
                    <td>{bloodGlucoseSummary.mealType}</td>
                    <td>{bloodGlucoseSummary.relationTemporalSleep}</td>
                    <td>{bloodGlucoseSummary.sampleSource}</td>
                    <td>
                      {bloodGlucoseSummary.startTime ? (
                        <TextFormat type="date" value={bloodGlucoseSummary.startTime} format={APP_DATE_FORMAT} />
                      ) : null}
                    </td>
                    <td>
                      {bloodGlucoseSummary.endTime ? (
                        <TextFormat type="date" value={bloodGlucoseSummary.endTime} format={APP_DATE_FORMAT} />
                      ) : null}
                    </td>
                    <td className="text-end">
                      <div className="btn-group flex-btn-group-container">
                        <Button
                          tag={Link}
                          to={`/blood-glucose-summary/${bloodGlucoseSummary.id}`}
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
                          to={`/blood-glucose-summary/${bloodGlucoseSummary.id}/edit`}
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
                          to={`/blood-glucose-summary/${bloodGlucoseSummary.id}/delete`}
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
                <Translate contentKey="b4CarecollectApp.bloodGlucoseSummary.home.notFound">No Blood Glucose Summaries found</Translate>
              </div>
            )
          )}
        </InfiniteScroll>
      </div>
    </div>
  );
};

export default BloodGlucoseSummary;
