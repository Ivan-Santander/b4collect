import React, { useState, useEffect } from 'react';
import InfiniteScroll from 'react-infinite-scroll-component';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, getSortState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ASC, DESC, ITEMS_PER_PAGE, SORT } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ISleepScores } from 'app/shared/model/sleep-scores.model';
import { getEntities, reset } from './sleep-scores.reducer';

export const SleepScores = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getSortState(location, ITEMS_PER_PAGE, 'id'), location.search)
  );
  const [sorting, setSorting] = useState(false);

  const sleepScoresList = useAppSelector(state => state.sleepScores.entities);
  const loading = useAppSelector(state => state.sleepScores.loading);
  const totalItems = useAppSelector(state => state.sleepScores.totalItems);
  const links = useAppSelector(state => state.sleepScores.links);
  const entity = useAppSelector(state => state.sleepScores.entity);
  const updateSuccess = useAppSelector(state => state.sleepScores.updateSuccess);

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
      <h2 id="sleep-scores-heading" data-cy="SleepScoresHeading">
        <Translate contentKey="b4CarecollectApp.sleepScores.home.title">Sleep Scores</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="b4CarecollectApp.sleepScores.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/sleep-scores/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="b4CarecollectApp.sleepScores.home.createLabel">Create new Sleep Scores</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        <InfiniteScroll
          dataLength={sleepScoresList ? sleepScoresList.length : 0}
          next={handleLoadMore}
          hasMore={paginationState.activePage - 1 < links.next}
          loader={<div className="loader">Loading ...</div>}
        >
          {sleepScoresList && sleepScoresList.length > 0 ? (
            <Table responsive>
              <thead>
                <tr>
                  <th className="hand" onClick={sort('id')}>
                    <Translate contentKey="b4CarecollectApp.sleepScores.id">Id</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('usuarioId')}>
                    <Translate contentKey="b4CarecollectApp.sleepScores.usuarioId">Usuario Id</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('empresaId')}>
                    <Translate contentKey="b4CarecollectApp.sleepScores.empresaId">Empresa Id</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('sleepQualityRatingScore')}>
                    <Translate contentKey="b4CarecollectApp.sleepScores.sleepQualityRatingScore">Sleep Quality Rating Score</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('sleepEfficiencyScore')}>
                    <Translate contentKey="b4CarecollectApp.sleepScores.sleepEfficiencyScore">Sleep Efficiency Score</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('sleepGooalSecondsScore')}>
                    <Translate contentKey="b4CarecollectApp.sleepScores.sleepGooalSecondsScore">Sleep Gooal Seconds Score</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('sleepContinuityScore')}>
                    <Translate contentKey="b4CarecollectApp.sleepScores.sleepContinuityScore">Sleep Continuity Score</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('sleepContinuityRating')}>
                    <Translate contentKey="b4CarecollectApp.sleepScores.sleepContinuityRating">Sleep Continuity Rating</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th />
                </tr>
              </thead>
              <tbody>
                {sleepScoresList.map((sleepScores, i) => (
                  <tr key={`entity-${i}`} data-cy="entityTable">
                    <td>
                      <Button tag={Link} to={`/sleep-scores/${sleepScores.id}`} color="link" size="sm">
                        {sleepScores.id}
                      </Button>
                    </td>
                    <td>{sleepScores.usuarioId}</td>
                    <td>{sleepScores.empresaId}</td>
                    <td>{sleepScores.sleepQualityRatingScore}</td>
                    <td>{sleepScores.sleepEfficiencyScore}</td>
                    <td>{sleepScores.sleepGooalSecondsScore}</td>
                    <td>{sleepScores.sleepContinuityScore}</td>
                    <td>{sleepScores.sleepContinuityRating}</td>
                    <td className="text-end">
                      <div className="btn-group flex-btn-group-container">
                        <Button tag={Link} to={`/sleep-scores/${sleepScores.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                          <FontAwesomeIcon icon="eye" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.view">View</Translate>
                          </span>
                        </Button>
                        <Button tag={Link} to={`/sleep-scores/${sleepScores.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                          <FontAwesomeIcon icon="pencil-alt" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.edit">Edit</Translate>
                          </span>
                        </Button>
                        <Button
                          tag={Link}
                          to={`/sleep-scores/${sleepScores.id}/delete`}
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
                <Translate contentKey="b4CarecollectApp.sleepScores.home.notFound">No Sleep Scores found</Translate>
              </div>
            )
          )}
        </InfiniteScroll>
      </div>
    </div>
  );
};

export default SleepScores;
