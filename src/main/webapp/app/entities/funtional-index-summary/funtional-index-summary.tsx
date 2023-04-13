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

import { IFuntionalIndexSummary } from 'app/shared/model/funtional-index-summary.model';
import { getEntities, reset } from './funtional-index-summary.reducer';

export const FuntionalIndexSummary = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getSortState(location, ITEMS_PER_PAGE, 'id'), location.search)
  );
  const [sorting, setSorting] = useState(false);

  const funtionalIndexSummaryList = useAppSelector(state => state.funtionalIndexSummary.entities);
  const loading = useAppSelector(state => state.funtionalIndexSummary.loading);
  const totalItems = useAppSelector(state => state.funtionalIndexSummary.totalItems);
  const links = useAppSelector(state => state.funtionalIndexSummary.links);
  const entity = useAppSelector(state => state.funtionalIndexSummary.entity);
  const updateSuccess = useAppSelector(state => state.funtionalIndexSummary.updateSuccess);

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
      <h2 id="funtional-index-summary-heading" data-cy="FuntionalIndexSummaryHeading">
        <Translate contentKey="b4CarecollectApp.funtionalIndexSummary.home.title">Funtional Index Summaries</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="b4CarecollectApp.funtionalIndexSummary.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link
            to="/funtional-index-summary/new"
            className="btn btn-primary jh-create-entity"
            id="jh-create-entity"
            data-cy="entityCreateButton"
          >
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="b4CarecollectApp.funtionalIndexSummary.home.createLabel">Create new Funtional Index Summary</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        <InfiniteScroll
          dataLength={funtionalIndexSummaryList ? funtionalIndexSummaryList.length : 0}
          next={handleLoadMore}
          hasMore={paginationState.activePage - 1 < links.next}
          loader={<div className="loader">Loading ...</div>}
        >
          {funtionalIndexSummaryList && funtionalIndexSummaryList.length > 0 ? (
            <Table responsive>
              <thead>
                <tr>
                  <th className="hand" onClick={sort('id')}>
                    <Translate contentKey="b4CarecollectApp.funtionalIndexSummary.id">Id</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('usuarioId')}>
                    <Translate contentKey="b4CarecollectApp.funtionalIndexSummary.usuarioId">Usuario Id</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('empresaId')}>
                    <Translate contentKey="b4CarecollectApp.funtionalIndexSummary.empresaId">Empresa Id</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('fieldFuntionalIndexAverage')}>
                    <Translate contentKey="b4CarecollectApp.funtionalIndexSummary.fieldFuntionalIndexAverage">
                      Field Funtional Index Average
                    </Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('fieldFuntionalIndexMax')}>
                    <Translate contentKey="b4CarecollectApp.funtionalIndexSummary.fieldFuntionalIndexMax">
                      Field Funtional Index Max
                    </Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('fieldFuntionalIndexMin')}>
                    <Translate contentKey="b4CarecollectApp.funtionalIndexSummary.fieldFuntionalIndexMin">
                      Field Funtional Index Min
                    </Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('startTime')}>
                    <Translate contentKey="b4CarecollectApp.funtionalIndexSummary.startTime">Start Time</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('endTime')}>
                    <Translate contentKey="b4CarecollectApp.funtionalIndexSummary.endTime">End Time</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th />
                </tr>
              </thead>
              <tbody>
                {funtionalIndexSummaryList.map((funtionalIndexSummary, i) => (
                  <tr key={`entity-${i}`} data-cy="entityTable">
                    <td>
                      <Button tag={Link} to={`/funtional-index-summary/${funtionalIndexSummary.id}`} color="link" size="sm">
                        {funtionalIndexSummary.id}
                      </Button>
                    </td>
                    <td>{funtionalIndexSummary.usuarioId}</td>
                    <td>{funtionalIndexSummary.empresaId}</td>
                    <td>{funtionalIndexSummary.fieldFuntionalIndexAverage}</td>
                    <td>{funtionalIndexSummary.fieldFuntionalIndexMax}</td>
                    <td>{funtionalIndexSummary.fieldFuntionalIndexMin}</td>
                    <td>
                      {funtionalIndexSummary.startTime ? (
                        <TextFormat type="date" value={funtionalIndexSummary.startTime} format={APP_DATE_FORMAT} />
                      ) : null}
                    </td>
                    <td>
                      {funtionalIndexSummary.endTime ? (
                        <TextFormat type="date" value={funtionalIndexSummary.endTime} format={APP_DATE_FORMAT} />
                      ) : null}
                    </td>
                    <td className="text-end">
                      <div className="btn-group flex-btn-group-container">
                        <Button
                          tag={Link}
                          to={`/funtional-index-summary/${funtionalIndexSummary.id}`}
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
                          to={`/funtional-index-summary/${funtionalIndexSummary.id}/edit`}
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
                          to={`/funtional-index-summary/${funtionalIndexSummary.id}/delete`}
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
                <Translate contentKey="b4CarecollectApp.funtionalIndexSummary.home.notFound">No Funtional Index Summaries found</Translate>
              </div>
            )
          )}
        </InfiniteScroll>
      </div>
    </div>
  );
};

export default FuntionalIndexSummary;
