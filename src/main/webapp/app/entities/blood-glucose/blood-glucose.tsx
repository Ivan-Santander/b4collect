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

import { IBloodGlucose } from 'app/shared/model/blood-glucose.model';
import { getEntities, reset } from './blood-glucose.reducer';

export const BloodGlucose = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getSortState(location, ITEMS_PER_PAGE, 'id'), location.search)
  );
  const [sorting, setSorting] = useState(false);

  const bloodGlucoseList = useAppSelector(state => state.bloodGlucose.entities);
  const loading = useAppSelector(state => state.bloodGlucose.loading);
  const totalItems = useAppSelector(state => state.bloodGlucose.totalItems);
  const links = useAppSelector(state => state.bloodGlucose.links);
  const entity = useAppSelector(state => state.bloodGlucose.entity);
  const updateSuccess = useAppSelector(state => state.bloodGlucose.updateSuccess);

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
      <h2 id="blood-glucose-heading" data-cy="BloodGlucoseHeading">
        <Translate contentKey="b4CarecollectApp.bloodGlucose.home.title">Blood Glucoses</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="b4CarecollectApp.bloodGlucose.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/blood-glucose/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="b4CarecollectApp.bloodGlucose.home.createLabel">Create new Blood Glucose</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        <InfiniteScroll
          dataLength={bloodGlucoseList ? bloodGlucoseList.length : 0}
          next={handleLoadMore}
          hasMore={paginationState.activePage - 1 < links.next}
          loader={<div className="loader">Loading ...</div>}
        >
          {bloodGlucoseList && bloodGlucoseList.length > 0 ? (
            <Table responsive>
              <thead>
                <tr>
                  <th className="hand" onClick={sort('id')}>
                    <Translate contentKey="b4CarecollectApp.bloodGlucose.id">Id</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('usuarioId')}>
                    <Translate contentKey="b4CarecollectApp.bloodGlucose.usuarioId">Usuario Id</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('empresaId')}>
                    <Translate contentKey="b4CarecollectApp.bloodGlucose.empresaId">Empresa Id</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('fieldBloodGlucoseLevel')}>
                    <Translate contentKey="b4CarecollectApp.bloodGlucose.fieldBloodGlucoseLevel">Field Blood Glucose Level</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('fieldTemporalRelationToMeal')}>
                    <Translate contentKey="b4CarecollectApp.bloodGlucose.fieldTemporalRelationToMeal">
                      Field Temporal Relation To Meal
                    </Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('fieldMealType')}>
                    <Translate contentKey="b4CarecollectApp.bloodGlucose.fieldMealType">Field Meal Type</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('fieldTemporalRelationToSleep')}>
                    <Translate contentKey="b4CarecollectApp.bloodGlucose.fieldTemporalRelationToSleep">
                      Field Temporal Relation To Sleep
                    </Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('fieldBloodGlucoseSpecimenSource')}>
                    <Translate contentKey="b4CarecollectApp.bloodGlucose.fieldBloodGlucoseSpecimenSource">
                      Field Blood Glucose Specimen Source
                    </Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('endTime')}>
                    <Translate contentKey="b4CarecollectApp.bloodGlucose.endTime">End Time</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th />
                </tr>
              </thead>
              <tbody>
                {bloodGlucoseList.map((bloodGlucose, i) => (
                  <tr key={`entity-${i}`} data-cy="entityTable">
                    <td>
                      <Button tag={Link} to={`/blood-glucose/${bloodGlucose.id}`} color="link" size="sm">
                        {bloodGlucose.id}
                      </Button>
                    </td>
                    <td>{bloodGlucose.usuarioId}</td>
                    <td>{bloodGlucose.empresaId}</td>
                    <td>{bloodGlucose.fieldBloodGlucoseLevel}</td>
                    <td>{bloodGlucose.fieldTemporalRelationToMeal}</td>
                    <td>{bloodGlucose.fieldMealType}</td>
                    <td>{bloodGlucose.fieldTemporalRelationToSleep}</td>
                    <td>{bloodGlucose.fieldBloodGlucoseSpecimenSource}</td>
                    <td>
                      {bloodGlucose.endTime ? <TextFormat type="date" value={bloodGlucose.endTime} format={APP_DATE_FORMAT} /> : null}
                    </td>
                    <td className="text-end">
                      <div className="btn-group flex-btn-group-container">
                        <Button tag={Link} to={`/blood-glucose/${bloodGlucose.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                          <FontAwesomeIcon icon="eye" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.view">View</Translate>
                          </span>
                        </Button>
                        <Button
                          tag={Link}
                          to={`/blood-glucose/${bloodGlucose.id}/edit`}
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
                          to={`/blood-glucose/${bloodGlucose.id}/delete`}
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
                <Translate contentKey="b4CarecollectApp.bloodGlucose.home.notFound">No Blood Glucoses found</Translate>
              </div>
            )
          )}
        </InfiniteScroll>
      </div>
    </div>
  );
};

export default BloodGlucose;
