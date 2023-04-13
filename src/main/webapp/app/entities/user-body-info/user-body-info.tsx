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

import { IUserBodyInfo } from 'app/shared/model/user-body-info.model';
import { getEntities, reset } from './user-body-info.reducer';

export const UserBodyInfo = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getSortState(location, ITEMS_PER_PAGE, 'id'), location.search)
  );
  const [sorting, setSorting] = useState(false);

  const userBodyInfoList = useAppSelector(state => state.userBodyInfo.entities);
  const loading = useAppSelector(state => state.userBodyInfo.loading);
  const totalItems = useAppSelector(state => state.userBodyInfo.totalItems);
  const links = useAppSelector(state => state.userBodyInfo.links);
  const entity = useAppSelector(state => state.userBodyInfo.entity);
  const updateSuccess = useAppSelector(state => state.userBodyInfo.updateSuccess);

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
      <h2 id="user-body-info-heading" data-cy="UserBodyInfoHeading">
        <Translate contentKey="b4CarecollectApp.userBodyInfo.home.title">User Body Infos</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="b4CarecollectApp.userBodyInfo.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/user-body-info/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="b4CarecollectApp.userBodyInfo.home.createLabel">Create new User Body Info</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        <InfiniteScroll
          dataLength={userBodyInfoList ? userBodyInfoList.length : 0}
          next={handleLoadMore}
          hasMore={paginationState.activePage - 1 < links.next}
          loader={<div className="loader">Loading ...</div>}
        >
          {userBodyInfoList && userBodyInfoList.length > 0 ? (
            <Table responsive>
              <thead>
                <tr>
                  <th className="hand" onClick={sort('id')}>
                    <Translate contentKey="b4CarecollectApp.userBodyInfo.id">Id</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('usuarioId')}>
                    <Translate contentKey="b4CarecollectApp.userBodyInfo.usuarioId">Usuario Id</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('empresaId')}>
                    <Translate contentKey="b4CarecollectApp.userBodyInfo.empresaId">Empresa Id</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('waistCircumference')}>
                    <Translate contentKey="b4CarecollectApp.userBodyInfo.waistCircumference">Waist Circumference</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('hipCircumference')}>
                    <Translate contentKey="b4CarecollectApp.userBodyInfo.hipCircumference">Hip Circumference</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('chestCircumference')}>
                    <Translate contentKey="b4CarecollectApp.userBodyInfo.chestCircumference">Chest Circumference</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('boneCompositionPercentaje')}>
                    <Translate contentKey="b4CarecollectApp.userBodyInfo.boneCompositionPercentaje">Bone Composition Percentaje</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('muscleCompositionPercentaje')}>
                    <Translate contentKey="b4CarecollectApp.userBodyInfo.muscleCompositionPercentaje">
                      Muscle Composition Percentaje
                    </Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('smoker')}>
                    <Translate contentKey="b4CarecollectApp.userBodyInfo.smoker">Smoker</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('waightKg')}>
                    <Translate contentKey="b4CarecollectApp.userBodyInfo.waightKg">Waight Kg</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('heightCm')}>
                    <Translate contentKey="b4CarecollectApp.userBodyInfo.heightCm">Height Cm</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('bodyHealthScore')}>
                    <Translate contentKey="b4CarecollectApp.userBodyInfo.bodyHealthScore">Body Health Score</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('cardiovascularRisk')}>
                    <Translate contentKey="b4CarecollectApp.userBodyInfo.cardiovascularRisk">Cardiovascular Risk</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th />
                </tr>
              </thead>
              <tbody>
                {userBodyInfoList.map((userBodyInfo, i) => (
                  <tr key={`entity-${i}`} data-cy="entityTable">
                    <td>
                      <Button tag={Link} to={`/user-body-info/${userBodyInfo.id}`} color="link" size="sm">
                        {userBodyInfo.id}
                      </Button>
                    </td>
                    <td>{userBodyInfo.usuarioId}</td>
                    <td>{userBodyInfo.empresaId}</td>
                    <td>{userBodyInfo.waistCircumference}</td>
                    <td>{userBodyInfo.hipCircumference}</td>
                    <td>{userBodyInfo.chestCircumference}</td>
                    <td>{userBodyInfo.boneCompositionPercentaje}</td>
                    <td>{userBodyInfo.muscleCompositionPercentaje}</td>
                    <td>{userBodyInfo.smoker ? 'true' : 'false'}</td>
                    <td>{userBodyInfo.waightKg}</td>
                    <td>{userBodyInfo.heightCm}</td>
                    <td>{userBodyInfo.bodyHealthScore}</td>
                    <td>{userBodyInfo.cardiovascularRisk}</td>
                    <td className="text-end">
                      <div className="btn-group flex-btn-group-container">
                        <Button tag={Link} to={`/user-body-info/${userBodyInfo.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                          <FontAwesomeIcon icon="eye" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.view">View</Translate>
                          </span>
                        </Button>
                        <Button
                          tag={Link}
                          to={`/user-body-info/${userBodyInfo.id}/edit`}
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
                          to={`/user-body-info/${userBodyInfo.id}/delete`}
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
                <Translate contentKey="b4CarecollectApp.userBodyInfo.home.notFound">No User Body Infos found</Translate>
              </div>
            )
          )}
        </InfiniteScroll>
      </div>
    </div>
  );
};

export default UserBodyInfo;
