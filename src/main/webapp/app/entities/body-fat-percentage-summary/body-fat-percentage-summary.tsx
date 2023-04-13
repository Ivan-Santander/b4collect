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

import { IBodyFatPercentageSummary } from 'app/shared/model/body-fat-percentage-summary.model';
import { getEntities, reset } from './body-fat-percentage-summary.reducer';

export const BodyFatPercentageSummary = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getSortState(location, ITEMS_PER_PAGE, 'id'), location.search)
  );
  const [sorting, setSorting] = useState(false);

  const bodyFatPercentageSummaryList = useAppSelector(state => state.bodyFatPercentageSummary.entities);
  const loading = useAppSelector(state => state.bodyFatPercentageSummary.loading);
  const totalItems = useAppSelector(state => state.bodyFatPercentageSummary.totalItems);
  const links = useAppSelector(state => state.bodyFatPercentageSummary.links);
  const entity = useAppSelector(state => state.bodyFatPercentageSummary.entity);
  const updateSuccess = useAppSelector(state => state.bodyFatPercentageSummary.updateSuccess);

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
      <h2 id="body-fat-percentage-summary-heading" data-cy="BodyFatPercentageSummaryHeading">
        <Translate contentKey="b4CarecollectApp.bodyFatPercentageSummary.home.title">Body Fat Percentage Summaries</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="b4CarecollectApp.bodyFatPercentageSummary.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link
            to="/body-fat-percentage-summary/new"
            className="btn btn-primary jh-create-entity"
            id="jh-create-entity"
            data-cy="entityCreateButton"
          >
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="b4CarecollectApp.bodyFatPercentageSummary.home.createLabel">
              Create new Body Fat Percentage Summary
            </Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        <InfiniteScroll
          dataLength={bodyFatPercentageSummaryList ? bodyFatPercentageSummaryList.length : 0}
          next={handleLoadMore}
          hasMore={paginationState.activePage - 1 < links.next}
          loader={<div className="loader">Loading ...</div>}
        >
          {bodyFatPercentageSummaryList && bodyFatPercentageSummaryList.length > 0 ? (
            <Table responsive>
              <thead>
                <tr>
                  <th className="hand" onClick={sort('id')}>
                    <Translate contentKey="b4CarecollectApp.bodyFatPercentageSummary.id">Id</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('usuarioId')}>
                    <Translate contentKey="b4CarecollectApp.bodyFatPercentageSummary.usuarioId">Usuario Id</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('empresaId')}>
                    <Translate contentKey="b4CarecollectApp.bodyFatPercentageSummary.empresaId">Empresa Id</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('fieldAverage')}>
                    <Translate contentKey="b4CarecollectApp.bodyFatPercentageSummary.fieldAverage">Field Average</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('fieldMax')}>
                    <Translate contentKey="b4CarecollectApp.bodyFatPercentageSummary.fieldMax">Field Max</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('fieldMin')}>
                    <Translate contentKey="b4CarecollectApp.bodyFatPercentageSummary.fieldMin">Field Min</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('startTime')}>
                    <Translate contentKey="b4CarecollectApp.bodyFatPercentageSummary.startTime">Start Time</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('endTime')}>
                    <Translate contentKey="b4CarecollectApp.bodyFatPercentageSummary.endTime">End Time</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th />
                </tr>
              </thead>
              <tbody>
                {bodyFatPercentageSummaryList.map((bodyFatPercentageSummary, i) => (
                  <tr key={`entity-${i}`} data-cy="entityTable">
                    <td>
                      <Button tag={Link} to={`/body-fat-percentage-summary/${bodyFatPercentageSummary.id}`} color="link" size="sm">
                        {bodyFatPercentageSummary.id}
                      </Button>
                    </td>
                    <td>{bodyFatPercentageSummary.usuarioId}</td>
                    <td>{bodyFatPercentageSummary.empresaId}</td>
                    <td>{bodyFatPercentageSummary.fieldAverage}</td>
                    <td>{bodyFatPercentageSummary.fieldMax}</td>
                    <td>{bodyFatPercentageSummary.fieldMin}</td>
                    <td>
                      {bodyFatPercentageSummary.startTime ? (
                        <TextFormat type="date" value={bodyFatPercentageSummary.startTime} format={APP_DATE_FORMAT} />
                      ) : null}
                    </td>
                    <td>
                      {bodyFatPercentageSummary.endTime ? (
                        <TextFormat type="date" value={bodyFatPercentageSummary.endTime} format={APP_DATE_FORMAT} />
                      ) : null}
                    </td>
                    <td className="text-end">
                      <div className="btn-group flex-btn-group-container">
                        <Button
                          tag={Link}
                          to={`/body-fat-percentage-summary/${bodyFatPercentageSummary.id}`}
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
                          to={`/body-fat-percentage-summary/${bodyFatPercentageSummary.id}/edit`}
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
                          to={`/body-fat-percentage-summary/${bodyFatPercentageSummary.id}/delete`}
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
                <Translate contentKey="b4CarecollectApp.bodyFatPercentageSummary.home.notFound">
                  No Body Fat Percentage Summaries found
                </Translate>
              </div>
            )
          )}
        </InfiniteScroll>
      </div>
    </div>
  );
};

export default BodyFatPercentageSummary;
