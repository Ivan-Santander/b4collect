import { createReducer, createActions } from 'reduxsauce';
import Immutable from 'seamless-immutable';
import { loadMoreDataWhenScrolled } from '../../../shared/util/pagination-utils';
import { parseHeaderForLinks } from '../../../shared/util/url-utils';

/* ------------- Types and Action Creators ------------- */

const { Types, Creators } = createActions({
  heartMinutesRequest: ['heartMinutesId'],
  heartMinutesAllRequest: ['options'],
  heartMinutesUpdateRequest: ['heartMinutes'],
  heartMinutesDeleteRequest: ['heartMinutesId'],

  heartMinutesSuccess: ['heartMinutes'],
  heartMinutesAllSuccess: ['heartMinutesList', 'headers'],
  heartMinutesUpdateSuccess: ['heartMinutes'],
  heartMinutesDeleteSuccess: [],

  heartMinutesFailure: ['error'],
  heartMinutesAllFailure: ['error'],
  heartMinutesUpdateFailure: ['error'],
  heartMinutesDeleteFailure: ['error'],

  heartMinutesReset: [],
});

export const HeartMinutesTypes = Types;
export default Creators;

/* ------------- Initial State ------------- */

export const INITIAL_STATE = Immutable({
  fetchingOne: false,
  fetchingAll: false,
  updating: false,
  deleting: false,
  updateSuccess: false,
  heartMinutes: { id: undefined },
  heartMinutesList: [],
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
    heartMinutes: INITIAL_STATE.heartMinutes,
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
  const { heartMinutes } = action;
  return state.merge({
    fetchingOne: false,
    errorOne: null,
    heartMinutes,
  });
};
// successful api lookup for all entities
export const allSuccess = (state, action) => {
  const { heartMinutesList, headers } = action;
  const links = parseHeaderForLinks(headers.link);
  return state.merge({
    fetchingAll: false,
    errorAll: null,
    links,
    totalItems: parseInt(headers['x-total-count'], 10),
    heartMinutesList: loadMoreDataWhenScrolled(state.heartMinutesList, heartMinutesList, links),
  });
};
// successful api update
export const updateSuccess = (state, action) => {
  const { heartMinutes } = action;
  return state.merge({
    updateSuccess: true,
    updating: false,
    errorUpdating: null,
    heartMinutes,
  });
};
// successful api delete
export const deleteSuccess = (state) => {
  return state.merge({
    deleting: false,
    errorDeleting: null,
    heartMinutes: INITIAL_STATE.heartMinutes,
  });
};

// Something went wrong fetching a single entity.
export const failure = (state, action) => {
  const { error } = action;
  return state.merge({
    fetchingOne: false,
    errorOne: error,
    heartMinutes: INITIAL_STATE.heartMinutes,
  });
};
// Something went wrong fetching all entities.
export const allFailure = (state, action) => {
  const { error } = action;
  return state.merge({
    fetchingAll: false,
    errorAll: error,
    heartMinutesList: [],
  });
};
// Something went wrong updating.
export const updateFailure = (state, action) => {
  const { error } = action;
  return state.merge({
    updateSuccess: false,
    updating: false,
    errorUpdating: error,
    heartMinutes: state.heartMinutes,
  });
};
// Something went wrong deleting.
export const deleteFailure = (state, action) => {
  const { error } = action;
  return state.merge({
    deleting: false,
    errorDeleting: error,
    heartMinutes: state.heartMinutes,
  });
};

export const reset = (state) => INITIAL_STATE;

/* ------------- Hookup Reducers To Types ------------- */

export const reducer = createReducer(INITIAL_STATE, {
  [Types.HEART_MINUTES_REQUEST]: request,
  [Types.HEART_MINUTES_ALL_REQUEST]: allRequest,
  [Types.HEART_MINUTES_UPDATE_REQUEST]: updateRequest,
  [Types.HEART_MINUTES_DELETE_REQUEST]: deleteRequest,

  [Types.HEART_MINUTES_SUCCESS]: success,
  [Types.HEART_MINUTES_ALL_SUCCESS]: allSuccess,
  [Types.HEART_MINUTES_UPDATE_SUCCESS]: updateSuccess,
  [Types.HEART_MINUTES_DELETE_SUCCESS]: deleteSuccess,

  [Types.HEART_MINUTES_FAILURE]: failure,
  [Types.HEART_MINUTES_ALL_FAILURE]: allFailure,
  [Types.HEART_MINUTES_UPDATE_FAILURE]: updateFailure,
  [Types.HEART_MINUTES_DELETE_FAILURE]: deleteFailure,
  [Types.HEART_MINUTES_RESET]: reset,
});
