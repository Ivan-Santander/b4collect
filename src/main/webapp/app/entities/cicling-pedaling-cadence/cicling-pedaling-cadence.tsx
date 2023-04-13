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

import { ICiclingPedalingCadence } from 'app/shared/model/cicling-pedaling-cadence.model';
import { getEntities, reset } from './cicling-pedaling-cadence.reducer';

export const CiclingPedalingCadence = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getSortState(location, ITEMS_PER_PAGE, 'id'), location.search)
  );
  const [sorting, setSorting] = useState(false);

  const ciclingPedalingCadenceList = useAppSelector(state => state.ciclingPedalingCadence.entities);
  const loading = useAppSelector(state => state.ciclingPedalingCadence.loading);
  const totalItems = useAppSelector(state => state.ciclingPedalingCadence.totalItems);
  const links = useAppSelector(state => state.ciclingPedalingCadence.links);
  const entity = useAppSelector(state => state.ciclingPedalingCadence.entity);
  const updateSuccess = useAppSelector(state => state.ciclingPedalingCadence.updateSuccess);

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
      <h2 id="cicling-pedaling-cadence-heading" data-cy="CiclingPedalingCadenceHeading">
        <Translate contentKey="b4CarecollectApp.ciclingPedalingCadence.home.title">Cicling Pedaling Cadences</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="b4CarecollectApp.ciclingPedalingCadence.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link
            to="/cicling-pedaling-cadence/new"
            className="btn btn-primary jh-create-entity"
            id="jh-create-entity"
            data-cy="entityCreateButton"
          >
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="b4CarecollectApp.ciclingPedalingCadence.home.createLabel">Create new Cicling Pedaling Cadence</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        <InfiniteScroll
          dataLength={ciclingPedalingCadenceList ? ciclingPedalingCadenceList.length : 0}
          next={handleLoadMore}
          hasMore={paginationState.activePage - 1 < links.next}
          loader={<div className="loader">Loading ...</div>}
        >
          {ciclingPedalingCadenceList && ciclingPedalingCadenceList.length > 0 ? (
            <Table responsive>
              <thead>
                <tr>
                  <th className="hand" onClick={sort('id')}>
                    <Translate contentKey="b4CarecollectApp.ciclingPedalingCadence.id">Id</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('usuarioId')}>
                    <Translate contentKey="b4CarecollectApp.ciclingPedalingCadence.usuarioId">Usuario Id</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('empresaId')}>
                    <Translate contentKey="b4CarecollectApp.ciclingPedalingCadence.empresaId">Empresa Id</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('rpm')}>
                    <Translate contentKey="b4CarecollectApp.ciclingPedalingCadence.rpm">Rpm</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('startTime')}>
                    <Translate contentKey="b4CarecollectApp.ciclingPedalingCadence.startTime">Start Time</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('endTime')}>
                    <Translate contentKey="b4CarecollectApp.ciclingPedalingCadence.endTime">End Time</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th />
                </tr>
              </thead>
              <tbody>
                {ciclingPedalingCadenceList.map((ciclingPedalingCadence, i) => (
                  <tr key={`entity-${i}`} data-cy="entityTable">
                    <td>
                      <Button tag={Link} to={`/cicling-pedaling-cadence/${ciclingPedalingCadence.id}`} color="link" size="sm">
                        {ciclingPedalingCadence.id}
                      </Button>
                    </td>
                    <td>{ciclingPedalingCadence.usuarioId}</td>
                    <td>{ciclingPedalingCadence.empresaId}</td>
                    <td>{ciclingPedalingCadence.rpm}</td>
                    <td>
                      {ciclingPedalingCadence.startTime ? (
                        <TextFormat type="date" value={ciclingPedalingCadence.startTime} format={APP_DATE_FORMAT} />
                      ) : null}
                    </td>
                    <td>
                      {ciclingPedalingCadence.endTime ? (
                        <TextFormat type="date" value={ciclingPedalingCadence.endTime} format={APP_DATE_FORMAT} />
                      ) : null}
                    </td>
                    <td className="text-end">
                      <div className="btn-group flex-btn-group-container">
                        <Button
                          tag={Link}
                          to={`/cicling-pedaling-cadence/${ciclingPedalingCadence.id}`}
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
                          to={`/cicling-pedaling-cadence/${ciclingPedalingCadence.id}/edit`}
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
                          to={`/cicling-pedaling-cadence/${ciclingPedalingCadence.id}/delete`}
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
                <Translate contentKey="b4CarecollectApp.ciclingPedalingCadence.home.notFound">No Cicling Pedaling Cadences found</Translate>
              </div>
            )
          )}
        </InfiniteScroll>
      </div>
    </div>
  );
};

export default CiclingPedalingCadence;
