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

import { IOxygenSaturation } from 'app/shared/model/oxygen-saturation.model';
import { getEntities, reset } from './oxygen-saturation.reducer';

export const OxygenSaturation = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getSortState(location, ITEMS_PER_PAGE, 'id'), location.search)
  );
  const [sorting, setSorting] = useState(false);

  const oxygenSaturationList = useAppSelector(state => state.oxygenSaturation.entities);
  const loading = useAppSelector(state => state.oxygenSaturation.loading);
  const totalItems = useAppSelector(state => state.oxygenSaturation.totalItems);
  const links = useAppSelector(state => state.oxygenSaturation.links);
  const entity = useAppSelector(state => state.oxygenSaturation.entity);
  const updateSuccess = useAppSelector(state => state.oxygenSaturation.updateSuccess);

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
      <h2 id="oxygen-saturation-heading" data-cy="OxygenSaturationHeading">
        <Translate contentKey="b4CarecollectApp.oxygenSaturation.home.title">Oxygen Saturations</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="b4CarecollectApp.oxygenSaturation.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/oxygen-saturation/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="b4CarecollectApp.oxygenSaturation.home.createLabel">Create new Oxygen Saturation</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        <InfiniteScroll
          dataLength={oxygenSaturationList ? oxygenSaturationList.length : 0}
          next={handleLoadMore}
          hasMore={paginationState.activePage - 1 < links.next}
          loader={<div className="loader">Loading ...</div>}
        >
          {oxygenSaturationList && oxygenSaturationList.length > 0 ? (
            <Table responsive>
              <thead>
                <tr>
                  <th className="hand" onClick={sort('id')}>
                    <Translate contentKey="b4CarecollectApp.oxygenSaturation.id">Id</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('usuarioId')}>
                    <Translate contentKey="b4CarecollectApp.oxygenSaturation.usuarioId">Usuario Id</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('empresaId')}>
                    <Translate contentKey="b4CarecollectApp.oxygenSaturation.empresaId">Empresa Id</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('fieldOxigenSaturation')}>
                    <Translate contentKey="b4CarecollectApp.oxygenSaturation.fieldOxigenSaturation">Field Oxigen Saturation</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('fieldSuplementalOxigenFlowRate')}>
                    <Translate contentKey="b4CarecollectApp.oxygenSaturation.fieldSuplementalOxigenFlowRate">
                      Field Suplemental Oxigen Flow Rate
                    </Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('fieldOxigenTherapyAdministrationMode')}>
                    <Translate contentKey="b4CarecollectApp.oxygenSaturation.fieldOxigenTherapyAdministrationMode">
                      Field Oxigen Therapy Administration Mode
                    </Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('fieldOxigenSaturationMode')}>
                    <Translate contentKey="b4CarecollectApp.oxygenSaturation.fieldOxigenSaturationMode">
                      Field Oxigen Saturation Mode
                    </Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('fieldOxigenSaturationMeasurementMethod')}>
                    <Translate contentKey="b4CarecollectApp.oxygenSaturation.fieldOxigenSaturationMeasurementMethod">
                      Field Oxigen Saturation Measurement Method
                    </Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('endTime')}>
                    <Translate contentKey="b4CarecollectApp.oxygenSaturation.endTime">End Time</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th />
                </tr>
              </thead>
              <tbody>
                {oxygenSaturationList.map((oxygenSaturation, i) => (
                  <tr key={`entity-${i}`} data-cy="entityTable">
                    <td>
                      <Button tag={Link} to={`/oxygen-saturation/${oxygenSaturation.id}`} color="link" size="sm">
                        {oxygenSaturation.id}
                      </Button>
                    </td>
                    <td>{oxygenSaturation.usuarioId}</td>
                    <td>{oxygenSaturation.empresaId}</td>
                    <td>{oxygenSaturation.fieldOxigenSaturation}</td>
                    <td>{oxygenSaturation.fieldSuplementalOxigenFlowRate}</td>
                    <td>{oxygenSaturation.fieldOxigenTherapyAdministrationMode}</td>
                    <td>{oxygenSaturation.fieldOxigenSaturationMode}</td>
                    <td>{oxygenSaturation.fieldOxigenSaturationMeasurementMethod}</td>
                    <td>
                      {oxygenSaturation.endTime ? (
                        <TextFormat type="date" value={oxygenSaturation.endTime} format={APP_DATE_FORMAT} />
                      ) : null}
                    </td>
                    <td className="text-end">
                      <div className="btn-group flex-btn-group-container">
                        <Button
                          tag={Link}
                          to={`/oxygen-saturation/${oxygenSaturation.id}`}
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
                          to={`/oxygen-saturation/${oxygenSaturation.id}/edit`}
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
                          to={`/oxygen-saturation/${oxygenSaturation.id}/delete`}
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
                <Translate contentKey="b4CarecollectApp.oxygenSaturation.home.notFound">No Oxygen Saturations found</Translate>
              </div>
            )
          )}
        </InfiniteScroll>
      </div>
    </div>
  );
};

export default OxygenSaturation;
