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

import { IUserDemographics } from 'app/shared/model/user-demographics.model';
import { getEntities, reset } from './user-demographics.reducer';

export const UserDemographics = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getSortState(location, ITEMS_PER_PAGE, 'id'), location.search)
  );
  const [sorting, setSorting] = useState(false);

  const userDemographicsList = useAppSelector(state => state.userDemographics.entities);
  const loading = useAppSelector(state => state.userDemographics.loading);
  const totalItems = useAppSelector(state => state.userDemographics.totalItems);
  const links = useAppSelector(state => state.userDemographics.links);
  const entity = useAppSelector(state => state.userDemographics.entity);
  const updateSuccess = useAppSelector(state => state.userDemographics.updateSuccess);

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
      <h2 id="user-demographics-heading" data-cy="UserDemographicsHeading">
        <Translate contentKey="b4CarecollectApp.userDemographics.home.title">User Demographics</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="b4CarecollectApp.userDemographics.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/user-demographics/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="b4CarecollectApp.userDemographics.home.createLabel">Create new User Demographics</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        <InfiniteScroll
          dataLength={userDemographicsList ? userDemographicsList.length : 0}
          next={handleLoadMore}
          hasMore={paginationState.activePage - 1 < links.next}
          loader={<div className="loader">Loading ...</div>}
        >
          {userDemographicsList && userDemographicsList.length > 0 ? (
            <Table responsive>
              <thead>
                <tr>
                  <th className="hand" onClick={sort('id')}>
                    <Translate contentKey="b4CarecollectApp.userDemographics.id">Id</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('usuarioId')}>
                    <Translate contentKey="b4CarecollectApp.userDemographics.usuarioId">Usuario Id</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('empresaId')}>
                    <Translate contentKey="b4CarecollectApp.userDemographics.empresaId">Empresa Id</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('gender')}>
                    <Translate contentKey="b4CarecollectApp.userDemographics.gender">Gender</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('dateOfBird')}>
                    <Translate contentKey="b4CarecollectApp.userDemographics.dateOfBird">Date Of Bird</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('age')}>
                    <Translate contentKey="b4CarecollectApp.userDemographics.age">Age</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('country')}>
                    <Translate contentKey="b4CarecollectApp.userDemographics.country">Country</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('state')}>
                    <Translate contentKey="b4CarecollectApp.userDemographics.state">State</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('city')}>
                    <Translate contentKey="b4CarecollectApp.userDemographics.city">City</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('ethnicity')}>
                    <Translate contentKey="b4CarecollectApp.userDemographics.ethnicity">Ethnicity</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('income')}>
                    <Translate contentKey="b4CarecollectApp.userDemographics.income">Income</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('maritalStatus')}>
                    <Translate contentKey="b4CarecollectApp.userDemographics.maritalStatus">Marital Status</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('education')}>
                    <Translate contentKey="b4CarecollectApp.userDemographics.education">Education</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('endTime')}>
                    <Translate contentKey="b4CarecollectApp.userDemographics.endTime">End Time</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th />
                </tr>
              </thead>
              <tbody>
                {userDemographicsList.map((userDemographics, i) => (
                  <tr key={`entity-${i}`} data-cy="entityTable">
                    <td>
                      <Button tag={Link} to={`/user-demographics/${userDemographics.id}`} color="link" size="sm">
                        {userDemographics.id}
                      </Button>
                    </td>
                    <td>{userDemographics.usuarioId}</td>
                    <td>{userDemographics.empresaId}</td>
                    <td>{userDemographics.gender}</td>
                    <td>
                      {userDemographics.dateOfBird ? (
                        <TextFormat type="date" value={userDemographics.dateOfBird} format={APP_LOCAL_DATE_FORMAT} />
                      ) : null}
                    </td>
                    <td>{userDemographics.age}</td>
                    <td>{userDemographics.country}</td>
                    <td>{userDemographics.state}</td>
                    <td>{userDemographics.city}</td>
                    <td>{userDemographics.ethnicity}</td>
                    <td>{userDemographics.income}</td>
                    <td>{userDemographics.maritalStatus}</td>
                    <td>{userDemographics.education}</td>
                    <td>
                      {userDemographics.endTime ? (
                        <TextFormat type="date" value={userDemographics.endTime} format={APP_DATE_FORMAT} />
                      ) : null}
                    </td>
                    <td className="text-end">
                      <div className="btn-group flex-btn-group-container">
                        <Button
                          tag={Link}
                          to={`/user-demographics/${userDemographics.id}`}
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
                          to={`/user-demographics/${userDemographics.id}/edit`}
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
                          to={`/user-demographics/${userDemographics.id}/delete`}
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
                <Translate contentKey="b4CarecollectApp.userDemographics.home.notFound">No User Demographics found</Translate>
              </div>
            )
          )}
        </InfiniteScroll>
      </div>
    </div>
  );
};

export default UserDemographics;
