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

import { IMentalHealthSummary } from 'app/shared/model/mental-health-summary.model';
import { getEntities, reset } from './mental-health-summary.reducer';

export const MentalHealthSummary = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getSortState(location, ITEMS_PER_PAGE, 'id'), location.search)
  );
  const [sorting, setSorting] = useState(false);

  const mentalHealthSummaryList = useAppSelector(state => state.mentalHealthSummary.entities);
  const loading = useAppSelector(state => state.mentalHealthSummary.loading);
  const totalItems = useAppSelector(state => state.mentalHealthSummary.totalItems);
  const links = useAppSelector(state => state.mentalHealthSummary.links);
  const entity = useAppSelector(state => state.mentalHealthSummary.entity);
  const updateSuccess = useAppSelector(state => state.mentalHealthSummary.updateSuccess);

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
      <h2 id="mental-health-summary-heading" data-cy="MentalHealthSummaryHeading">
        <Translate contentKey="b4CarecollectApp.mentalHealthSummary.home.title">Mental Health Summaries</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="b4CarecollectApp.mentalHealthSummary.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link
            to="/mental-health-summary/new"
            className="btn btn-primary jh-create-entity"
            id="jh-create-entity"
            data-cy="entityCreateButton"
          >
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="b4CarecollectApp.mentalHealthSummary.home.createLabel">Create new Mental Health Summary</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        <InfiniteScroll
          dataLength={mentalHealthSummaryList ? mentalHealthSummaryList.length : 0}
          next={handleLoadMore}
          hasMore={paginationState.activePage - 1 < links.next}
          loader={<div className="loader">Loading ...</div>}
        >
          {mentalHealthSummaryList && mentalHealthSummaryList.length > 0 ? (
            <Table responsive>
              <thead>
                <tr>
                  <th className="hand" onClick={sort('id')}>
                    <Translate contentKey="b4CarecollectApp.mentalHealthSummary.id">Id</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('usuarioId')}>
                    <Translate contentKey="b4CarecollectApp.mentalHealthSummary.usuarioId">Usuario Id</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('empresaId')}>
                    <Translate contentKey="b4CarecollectApp.mentalHealthSummary.empresaId">Empresa Id</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('emotionDescripMain')}>
                    <Translate contentKey="b4CarecollectApp.mentalHealthSummary.emotionDescripMain">Emotion Descrip Main</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('emotionDescripSecond')}>
                    <Translate contentKey="b4CarecollectApp.mentalHealthSummary.emotionDescripSecond">Emotion Descrip Second</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('fieldMentalHealthAverage')}>
                    <Translate contentKey="b4CarecollectApp.mentalHealthSummary.fieldMentalHealthAverage">
                      Field Mental Health Average
                    </Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('fieldMentalHealthMax')}>
                    <Translate contentKey="b4CarecollectApp.mentalHealthSummary.fieldMentalHealthMax">Field Mental Health Max</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('fieldMentalHealthMin')}>
                    <Translate contentKey="b4CarecollectApp.mentalHealthSummary.fieldMentalHealthMin">Field Mental Health Min</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('scoreMentalRisk')}>
                    <Translate contentKey="b4CarecollectApp.mentalHealthSummary.scoreMentalRisk">Score Mental Risk</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('startTime')}>
                    <Translate contentKey="b4CarecollectApp.mentalHealthSummary.startTime">Start Time</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('endTime')}>
                    <Translate contentKey="b4CarecollectApp.mentalHealthSummary.endTime">End Time</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th />
                </tr>
              </thead>
              <tbody>
                {mentalHealthSummaryList.map((mentalHealthSummary, i) => (
                  <tr key={`entity-${i}`} data-cy="entityTable">
                    <td>
                      <Button tag={Link} to={`/mental-health-summary/${mentalHealthSummary.id}`} color="link" size="sm">
                        {mentalHealthSummary.id}
                      </Button>
                    </td>
                    <td>{mentalHealthSummary.usuarioId}</td>
                    <td>{mentalHealthSummary.empresaId}</td>
                    <td>{mentalHealthSummary.emotionDescripMain}</td>
                    <td>{mentalHealthSummary.emotionDescripSecond}</td>
                    <td>{mentalHealthSummary.fieldMentalHealthAverage}</td>
                    <td>{mentalHealthSummary.fieldMentalHealthMax}</td>
                    <td>{mentalHealthSummary.fieldMentalHealthMin}</td>
                    <td>{mentalHealthSummary.scoreMentalRisk}</td>
                    <td>
                      {mentalHealthSummary.startTime ? (
                        <TextFormat type="date" value={mentalHealthSummary.startTime} format={APP_DATE_FORMAT} />
                      ) : null}
                    </td>
                    <td>
                      {mentalHealthSummary.endTime ? (
                        <TextFormat type="date" value={mentalHealthSummary.endTime} format={APP_DATE_FORMAT} />
                      ) : null}
                    </td>
                    <td className="text-end">
                      <div className="btn-group flex-btn-group-container">
                        <Button
                          tag={Link}
                          to={`/mental-health-summary/${mentalHealthSummary.id}`}
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
                          to={`/mental-health-summary/${mentalHealthSummary.id}/edit`}
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
                          to={`/mental-health-summary/${mentalHealthSummary.id}/delete`}
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
                <Translate contentKey="b4CarecollectApp.mentalHealthSummary.home.notFound">No Mental Health Summaries found</Translate>
              </div>
            )
          )}
        </InfiniteScroll>
      </div>
    </div>
  );
};

export default MentalHealthSummary;
