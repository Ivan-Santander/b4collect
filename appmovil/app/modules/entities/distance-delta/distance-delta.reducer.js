import { createReducer, createActions } from 'reduxsauce';
import Immutable from 'seamless-immutable';
import { loadMoreDataWhenScrolled } from '../../../shared/util/pagination-utils';
import { parseHeaderForLinks } from '../../../shared/util/url-utils';

/* ------------- Types and Action Creators ------------- */

const { Types, Creators } = createActions({
  distanceDeltaRequest: ['distanceDeltaId'],
  distanceDeltaAllRequest: ['options'],
  distanceDeltaUpdateRequest: ['distanceDelta'],
  distanceDeltaDeleteRequest: ['distanceDeltaId'],

  distanceDeltaSuccess: ['distanceDelta'],
  distanceDeltaAllSuccess: ['distanceDeltaList', 'headers'],
  distanceDeltaUpdateSuccess: ['distanceDelta'],
  distanceDeltaDeleteSuccess: [],

  distanceDeltaFailure: ['error'],
  distanceDeltaAllFailure: ['error'],
  distanceDeltaUpdateFailure: ['error'],
  distanceDeltaDeleteFailure: ['error'],

  distanceDeltaReset: [],
});

export const DistanceDeltaTypes = Types;
export default Creators;

/* ------------- Initial State ------------- */

export const INITIAL_STATE = Immutable({
  fetchingOne: false,
  fetchingAll: false,
  updating: false,
  deleting: false,
  updateSuccess: false,
  distanceDelta: { id: undefined },
  distanceDeltaList: [],
  errorOne: null,
  errorAll: null,
  errorUpdating: null,
  errorDeleting: null,
  links: { next: 0 },
  totalItems: 0,
});

/* ------------- Reducers ------------- */

// request the data from an api
export const request = (state) =>
  state.merge({
    fetchingOne: true,
    errorOne: false,
    distanceDelta: INITIAL_STATE.distanceDelta,
  });

// request the data from an api
export const allRequest = (state) =>
  state.merge({
    fetchingAll: true,
    errorAll: false,
  });

// request to update from an api
export const updateRequest = (state) =>
  state.merge({
    updateSuccess: false,
    updating: true,
  });
// request to delete from an api
export const deleteRequest = (state) =>
  state.merge({
    deleting: true,
  });

// successful api lookup for single entity
export const success = (state, action) => {
  const { distanceDelta } = action;
  return state.merge({
    fetchingOne: false,
    errorOne: null,
    distanceDelta,
  });
};
// successful api lookup for all entities
export const allSuccess = (state, action) => {
  const { distanceDeltaList, headers } = action;
  const links = parseHeaderForLinks(headers.link);
  return state.merge({
    fetchingAll: false,
    errorAll: null,
    links,
    totalItems: parseInt(headers['x-total-count'], 10),
    distanceDeltaList: loadMoreDataWhenScrolled(state.distanceDeltaList, distanceDeltaList, links),
  });
};
// successful api update
export const updateSuccess = (state, action) => {
  const { distanceDelta } = action;
  return state.merge({
    updateSuccess: true,
    updating: false,
    errorUpdating: null,
    distanceDelta,
  });
};
// successful api delete
export const deleteSuccess = (state) => {
  return state.merge({
    deleting: false,
    errorDeleting: null,
    distanceDelta: INITIAL_STATE.distanceDelta,
  });
};

// Something went wrong fetching a single entity.
export const failure = (state, action) => {
  const { error } = action;
  return state.merge({
    fetchingOne: false,
    errorOne: error,
    distanceDelta: INITIAL_STATE.distanceDelta,
  });
};
// Something went wrong fetching all entities.
export const allFailure = (state, action) => {
  const { error } = action;
  return state.merge({
    fetchingAll: false,
    errorAll: error,
    distanceDeltaList: [],
  });
};
// Something went wrong updating.
export const updateFailure = (state, action) => {
  const { error } = action;
  return state.merge({
    updateSuccess: false,
    updating: false,
    errorUpdating: error,
    distanceDelta: state.distanceDelta,
  });
};
// Something went wrong deleting.
export const deleteFailure = (state, action) => {
  const { error } = action;
  return state.merge({
    deleting: false,
    errorDeleting: error,
    distanceDelta: state.distanceDelta,
  });
};

export const reset = (state) => INITIAL_STATE;

/* ------------- Hookup Reducers To Types ------------- */

export const reducer = createReducer(INITIAL_STATE, {
  [Types.DISTANCE_DELTA_REQUEST]: request,
  [Types.DISTANCE_DELTA_ALL_REQUEST]: allRequest,
  [Types.DISTANCE_DELTA_UPDATE_REQUEST]: updateRequest,
  [Types.DISTANCE_DELTA_DELETE_REQUEST]: deleteRequest,

  [Types.DISTANCE_DELTA_SUCCESS]: success,
  [Types.DISTANCE_DELTA_ALL_SUCCESS]: allSuccess,
  [Types.DISTANCE_DELTA_UPDATE_SUCCESS]: updateSuccess,
  [Types.DISTANCE_DELTA_DELETE_SUCCESS]: deleteSuccess,

  [Types.DISTANCE_DELTA_FAILURE]: failure,
  [Types.DISTANCE_DELTA_ALL_FAILURE]: allFailure,
  [Types.DISTANCE_DELTA_UPDATE_FAILURE]: updateFailure,
  [Types.DISTANCE_DELTA_DELETE_FAILURE]: deleteFailure,
  [Types.DISTANCE_DELTA_RESET]: reset,
});
