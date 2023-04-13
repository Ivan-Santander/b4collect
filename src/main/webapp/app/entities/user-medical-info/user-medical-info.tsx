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

import { IUserMedicalInfo } from 'app/shared/model/user-medical-info.model';
import { getEntities, reset } from './user-medical-info.reducer';

export const UserMedicalInfo = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getSortState(location, ITEMS_PER_PAGE, 'id'), location.search)
  );
  const [sorting, setSorting] = useState(false);

  const userMedicalInfoList = useAppSelector(state => state.userMedicalInfo.entities);
  const loading = useAppSelector(state => state.userMedicalInfo.loading);
  const totalItems = useAppSelector(state => state.userMedicalInfo.totalItems);
  const links = useAppSelector(state => state.userMedicalInfo.links);
  const entity = useAppSelector(state => state.userMedicalInfo.entity);
  const updateSuccess = useAppSelector(state => state.userMedicalInfo.updateSuccess);

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
      <h2 id="user-medical-info-heading" data-cy="UserMedicalInfoHeading">
        <Translate contentKey="b4CarecollectApp.userMedicalInfo.home.title">User Medical Infos</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="b4CarecollectApp.userMedicalInfo.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/user-medical-info/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="b4CarecollectApp.userMedicalInfo.home.createLabel">Create new User Medical Info</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        <InfiniteScroll
          dataLength={userMedicalInfoList ? userMedicalInfoList.length : 0}
          next={handleLoadMore}
          hasMore={paginationState.activePage - 1 < links.next}
          loader={<div className="loader">Loading ...</div>}
        >
          {userMedicalInfoList && userMedicalInfoList.length > 0 ? (
            <Table responsive>
              <thead>
                <tr>
                  <th className="hand" onClick={sort('id')}>
                    <Translate contentKey="b4CarecollectApp.userMedicalInfo.id">Id</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('usuarioId')}>
                    <Translate contentKey="b4CarecollectApp.userMedicalInfo.usuarioId">Usuario Id</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('empresaId')}>
                    <Translate contentKey="b4CarecollectApp.userMedicalInfo.empresaId">Empresa Id</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('hypertension')}>
                    <Translate contentKey="b4CarecollectApp.userMedicalInfo.hypertension">Hypertension</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('highGlucose')}>
                    <Translate contentKey="b4CarecollectApp.userMedicalInfo.highGlucose">High Glucose</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('hiabetes')}>
                    <Translate contentKey="b4CarecollectApp.userMedicalInfo.hiabetes">Hiabetes</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('totalCholesterol')}>
                    <Translate contentKey="b4CarecollectApp.userMedicalInfo.totalCholesterol">Total Cholesterol</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('hdlCholesterol')}>
                    <Translate contentKey="b4CarecollectApp.userMedicalInfo.hdlCholesterol">Hdl Cholesterol</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('medicalMainCondition')}>
                    <Translate contentKey="b4CarecollectApp.userMedicalInfo.medicalMainCondition">Medical Main Condition</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('medicalSecundaryCondition')}>
                    <Translate contentKey="b4CarecollectApp.userMedicalInfo.medicalSecundaryCondition">
                      Medical Secundary Condition
                    </Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('medicalMainMedication')}>
                    <Translate contentKey="b4CarecollectApp.userMedicalInfo.medicalMainMedication">Medical Main Medication</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('medicalSecundaryMedication')}>
                    <Translate contentKey="b4CarecollectApp.userMedicalInfo.medicalSecundaryMedication">
                      Medical Secundary Medication
                    </Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('medicalScore')}>
                    <Translate contentKey="b4CarecollectApp.userMedicalInfo.medicalScore">Medical Score</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('endTime')}>
                    <Translate contentKey="b4CarecollectApp.userMedicalInfo.endTime">End Time</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th />
                </tr>
              </thead>
              <tbody>
                {userMedicalInfoList.map((userMedicalInfo, i) => (
                  <tr key={`entity-${i}`} data-cy="entityTable">
                    <td>
                      <Button tag={Link} to={`/user-medical-info/${userMedicalInfo.id}`} color="link" size="sm">
                        {userMedicalInfo.id}
                      </Button>
                    </td>
                    <td>{userMedicalInfo.usuarioId}</td>
                    <td>{userMedicalInfo.empresaId}</td>
                    <td>{userMedicalInfo.hypertension ? 'true' : 'false'}</td>
                    <td>{userMedicalInfo.highGlucose ? 'true' : 'false'}</td>
                    <td>{userMedicalInfo.hiabetes ? 'true' : 'false'}</td>
                    <td>{userMedicalInfo.totalCholesterol}</td>
                    <td>{userMedicalInfo.hdlCholesterol}</td>
                    <td>{userMedicalInfo.medicalMainCondition}</td>
                    <td>{userMedicalInfo.medicalSecundaryCondition}</td>
                    <td>{userMedicalInfo.medicalMainMedication}</td>
                    <td>{userMedicalInfo.medicalSecundaryMedication}</td>
                    <td>{userMedicalInfo.medicalScore}</td>
                    <td>
                      {userMedicalInfo.endTime ? <TextFormat type="date" value={userMedicalInfo.endTime} format={APP_DATE_FORMAT} /> : null}
                    </td>
                    <td className="text-end">
                      <div className="btn-group flex-btn-group-container">
                        <Button
                          tag={Link}
                          to={`/user-medical-info/${userMedicalInfo.id}`}
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
                          to={`/user-medical-info/${userMedicalInfo.id}/edit`}
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
                          to={`/user-medical-info/${userMedicalInfo.id}/delete`}
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
                <Translate contentKey="b4CarecollectApp.userMedicalInfo.home.notFound">No User Medical Infos found</Translate>
              </div>
            )
          )}
        </InfiniteScroll>
      </div>
    </div>
  );
};

export default UserMedicalInfo;
