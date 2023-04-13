import { createReducer, createActions } from 'reduxsauce';
import Immutable from 'seamless-immutable';
import { loadMoreDataWhenScrolled } from '../../../shared/util/pagination-utils';
import { parseHeaderForLinks } from '../../../shared/util/url-utils';

/* ------------- Types and Action Creators ------------- */

const { Types, Creators } = createActions({
  speedRequest: ['speedId'],
  speedAllRequest: ['options'],
  speedUpdateRequest: ['speed'],
  speedDeleteRequest: ['speedId'],

  speedSuccess: ['speed'],
  speedAllSuccess: ['speedList', 'headers'],
  speedUpdateSuccess: ['speed'],
  speedDeleteSuccess: [],

  speedFailure: ['error'],
  speedAllFailure: ['error'],
  speedUpdateFailure: ['error'],
  speedDeleteFailure: ['error'],

  speedReset: [],
});

export const SpeedTypes = Types;
export default Creators;

/* ------------- Initial State ------------- */

export const INITIAL_STATE = Immutable({
  fetchingOne: false,
  fetchingAll: false,
  updating: false,
  deleting: false,
  updateSuccess: false,
  speed: { id: undefined },
  speedList: [],
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
    speed: INITIAL_STATE.speed,
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
  const { speed } = action;
  return state.merge({
    fetchingOne: false,
    errorOne: null,
    speed,
  });
};
// successful api lookup for all entities
export const allSuccess = (state, action) => {
  const { speedList, headers } = action;
  const links = parseHeaderForLinks(headers.link);
  return state.merge({
    fetchingAll: false,
    errorAll: null,
    links,
    totalItems: parseInt(headers['x-total-count'], 10),
    speedList: loadMoreDataWhenScrolled(state.speedList, speedList, links),
  });
};
// successful api update
export const updateSuccess = (state, action) => {
  const { speed } = action;
  return state.merge({
    updateSuccess: true,
    updating: false,
    errorUpdating: null,
    speed,
  });
};
// successful api delete
export const deleteSuccess = (state) => {
  return state.merge({
    deleting: false,
    errorDeleting: null,
    speed: INITIAL_STATE.speed,
  });
};

// Something went wrong fetching a single entity.
export const failure = (state, action) => {
  const { error } = action;
  return state.merge({
    fetchingOne: false,
    errorOne: error,
    speed: INITIAL_STATE.speed,
  });
};
// Something went wrong fetching all entities.
export const allFailure = (state, action) => {
  const { error } = action;
  return state.merge({
    fetchingAll: false,
    errorAll: error,
    speedList: [],
  });
};
// Something went wrong updating.
export const updateFailure = (state, action) => {
  const { error } = action;
  return state.merge({
    updateSuccess: false,
    updating: false,
    errorUpdating: error,
    speed: state.speed,
  });
};
// Something went wrong deleting.
export const deleteFailure = (state, action) => {
  const { error } = action;
  return state.merge({
    deleting: false,
    errorDeleting: error,
    speed: state.speed,
  });
};

export const reset = (state) => INITIAL_STATE;

/* ------------- Hookup Reducers To Types ------------- */

export const reducer = createReducer(INITIAL_STATE, {
  [Types.SPEED_REQUEST]: request,
  [Types.SPEED_ALL_REQUEST]: allRequest,
  [Types.SPEED_UPDATE_REQUEST]: updateRequest,
  [Types.SPEED_DELETE_REQUEST]: deleteRequest,

  [Types.SPEED_SUCCESS]: success,
  [Types.SPEED_ALL_SUCCESS]: allSuccess,
  [Types.SPEED_UPDATE_SUCCESS]: updateSuccess,
  [Types.SPEED_DELETE_SUCCESS]: deleteSuccess,

  [Types.SPEED_FAILURE]: failure,
  [Types.SPEED_ALL_FAILURE]: allFailure,
  [Types.SPEED_UPDATE_FAILURE]: updateFailure,
  [Types.SPEED_DELETE_FAILURE]: deleteFailure,
  [Types.SPEED_RESET]: reset,
});
