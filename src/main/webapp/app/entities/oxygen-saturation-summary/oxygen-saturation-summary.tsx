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

import { IOxygenSaturationSummary } from 'app/shared/model/oxygen-saturation-summary.model';
import { getEntities, reset } from './oxygen-saturation-summary.reducer';

export const OxygenSaturationSummary = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getSortState(location, ITEMS_PER_PAGE, 'id'), location.search)
  );
  const [sorting, setSorting] = useState(false);

  const oxygenSaturationSummaryList = useAppSelector(state => state.oxygenSaturationSummary.entities);
  const loading = useAppSelector(state => state.oxygenSaturationSummary.loading);
  const totalItems = useAppSelector(state => state.oxygenSaturationSummary.totalItems);
  const links = useAppSelector(state => state.oxygenSaturationSummary.links);
  const entity = useAppSelector(state => state.oxygenSaturationSummary.entity);
  const updateSuccess = useAppSelector(state => state.oxygenSaturationSummary.updateSuccess);

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
      <h2 id="oxygen-saturation-summary-heading" data-cy="OxygenSaturationSummaryHeading">
        <Translate contentKey="b4CarecollectApp.oxygenSaturationSummary.home.title">Oxygen Saturation Summaries</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="b4CarecollectApp.oxygenSaturationSummary.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link
            to="/oxygen-saturation-summary/new"
            className="btn btn-primary jh-create-entity"
            id="jh-create-entity"
            data-cy="entityCreateButton"
          >
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="b4CarecollectApp.oxygenSaturationSummary.home.createLabel">
              Create new Oxygen Saturation Summary
            </Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        <InfiniteScroll
          dataLength={oxygenSaturationSummaryList ? oxygenSaturationSummaryList.length : 0}
          next={handleLoadMore}
          hasMore={paginationState.activePage - 1 < links.next}
          loader={<div className="loader">Loading ...</div>}
        >
          {oxygenSaturationSummaryList && oxygenSaturationSummaryList.length > 0 ? (
            <Table responsive>
              <thead>
                <tr>
                  <th className="hand" onClick={sort('id')}>
                    <Translate contentKey="b4CarecollectApp.oxygenSaturationSummary.id">Id</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('usuarioId')}>
                    <Translate contentKey="b4CarecollectApp.oxygenSaturationSummary.usuarioId">Usuario Id</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('empresaId')}>
                    <Translate contentKey="b4CarecollectApp.oxygenSaturationSummary.empresaId">Empresa Id</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('fieldOxigenSaturationAverage')}>
                    <Translate contentKey="b4CarecollectApp.oxygenSaturationSummary.fieldOxigenSaturationAverage">
                      Field Oxigen Saturation Average
                    </Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('fieldOxigenSaturationMax')}>
                    <Translate contentKey="b4CarecollectApp.oxygenSaturationSummary.fieldOxigenSaturationMax">
                      Field Oxigen Saturation Max
                    </Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('fieldOxigenSaturationMin')}>
                    <Translate contentKey="b4CarecollectApp.oxygenSaturationSummary.fieldOxigenSaturationMin">
                      Field Oxigen Saturation Min
                    </Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('fieldSuplementalOxigenFlowRateAverage')}>
                    <Translate contentKey="b4CarecollectApp.oxygenSaturationSummary.fieldSuplementalOxigenFlowRateAverage">
                      Field Suplemental Oxigen Flow Rate Average
                    </Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('fieldSuplementalOxigenFlowRateMax')}>
                    <Translate contentKey="b4CarecollectApp.oxygenSaturationSummary.fieldSuplementalOxigenFlowRateMax">
                      Field Suplemental Oxigen Flow Rate Max
                    </Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('fieldSuplementalOxigenFlowRateMin')}>
                    <Translate contentKey="b4CarecollectApp.oxygenSaturationSummary.fieldSuplementalOxigenFlowRateMin">
                      Field Suplemental Oxigen Flow Rate Min
                    </Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('fieldOxigenTherapyAdministrationMode')}>
                    <Translate contentKey="b4CarecollectApp.oxygenSaturationSummary.fieldOxigenTherapyAdministrationMode">
                      Field Oxigen Therapy Administration Mode
                    </Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('fieldOxigenSaturationMode')}>
                    <Translate contentKey="b4CarecollectApp.oxygenSaturationSummary.fieldOxigenSaturationMode">
                      Field Oxigen Saturation Mode
                    </Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('fieldOxigenSaturationMeasurementMethod')}>
                    <Translate contentKey="b4CarecollectApp.oxygenSaturationSummary.fieldOxigenSaturationMeasurementMethod">
                      Field Oxigen Saturation Measurement Method
                    </Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('endTime')}>
                    <Translate contentKey="b4CarecollectApp.oxygenSaturationSummary.endTime">End Time</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th />
                </tr>
              </thead>
              <tbody>
                {oxygenSaturationSummaryList.map((oxygenSaturationSummary, i) => (
                  <tr key={`entity-${i}`} data-cy="entityTable">
                    <td>
                      <Button tag={Link} to={`/oxygen-saturation-summary/${oxygenSaturationSummary.id}`} color="link" size="sm">
                        {oxygenSaturationSummary.id}
                      </Button>
                    </td>
                    <td>{oxygenSaturationSummary.usuarioId}</td>
                    <td>{oxygenSaturationSummary.empresaId}</td>
                    <td>{oxygenSaturationSummary.fieldOxigenSaturationAverage}</td>
                    <td>{oxygenSaturationSummary.fieldOxigenSaturationMax}</td>
                    <td>{oxygenSaturationSummary.fieldOxigenSaturationMin}</td>
                    <td>{oxygenSaturationSummary.fieldSuplementalOxigenFlowRateAverage}</td>
                    <td>{oxygenSaturationSummary.fieldSuplementalOxigenFlowRateMax}</td>
                    <td>{oxygenSaturationSummary.fieldSuplementalOxigenFlowRateMin}</td>
                    <td>{oxygenSaturationSummary.fieldOxigenTherapyAdministrationMode}</td>
                    <td>{oxygenSaturationSummary.fieldOxigenSaturationMode}</td>
                    <td>{oxygenSaturationSummary.fieldOxigenSaturationMeasurementMethod}</td>
                    <td>
                      {oxygenSaturationSummary.endTime ? (
                        <TextFormat type="date" value={oxygenSaturationSummary.endTime} format={APP_DATE_FORMAT} />
                      ) : null}
                    </td>
                    <td className="text-end">
                      <div className="btn-group flex-btn-group-container">
                        <Button
                          tag={Link}
                          to={`/oxygen-saturation-summary/${oxygenSaturationSummary.id}`}
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
                          to={`/oxygen-saturation-summary/${oxygenSaturationSummary.id}/edit`}
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
                          to={`/oxygen-saturation-summary/${oxygenSaturationSummary.id}/delete`}
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
                <Translate contentKey="b4CarecollectApp.oxygenSaturationSummary.home.notFound">
                  No Oxygen Saturation Summaries found
                </Translate>
              </div>
            )
          )}
        </InfiniteScroll>
      </div>
    </div>
  );
};

export default OxygenSaturationSummary;
