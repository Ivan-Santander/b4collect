import { createReducer, createActions } from 'reduxsauce';
import Immutable from 'seamless-immutable';
import { loadMoreDataWhenScrolled } from '../../../shared/util/pagination-utils';
import { parseHeaderForLinks } from '../../../shared/util/url-utils';

/* ------------- Types and Action Creators ------------- */

const { Types, Creators } = createActions({
  activeMinutesRequest: ['activeMinutesId'],
  activeMinutesAllRequest: ['options'],
  activeMinutesUpdateRequest: ['activeMinutes'],
  activeMinutesDeleteRequest: ['activeMinutesId'],

  activeMinutesSuccess: ['activeMinutes'],
  activeMinutesAllSuccess: ['activeMinutesList', 'headers'],
  activeMinutesUpdateSuccess: ['activeMinutes'],
  activeMinutesDeleteSuccess: [],

  activeMinutesFailure: ['error'],
  activeMinutesAllFailure: ['error'],
  activeMinutesUpdateFailure: ['error'],
  activeMinutesDeleteFailure: ['error'],

  activeMinutesReset: [],
});

export const ActiveMinutesTypes = Types;
export default Creators;

/* ------------- Initial State ------------- */

export const INITIAL_STATE = Immutable({
  fetchingOne: false,
  fetchingAll: false,
  updating: false,
  deleting: false,
  updateSuccess: false,
  activeMinutes: { id: undefined },
  activeMinutesList: [],
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
    activeMinutes: INITIAL_STATE.activeMinutes,
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
  const { activeMinutes } = action;
  return state.merge({
    fetchingOne: false,
    errorOne: null,
    activeMinutes,
  });
};
// successful api lookup for all entities
export const allSuccess = (state, action) => {
  const { activeMinutesList, headers } = action;
  const links = parseHeaderForLinks(headers.link);
  return state.merge({
    fetchingAll: false,
    errorAll: null,
    links,
    totalItems: parseInt(headers['x-total-count'], 10),
    activeMinutesList: loadMoreDataWhenScrolled(state.activeMinutesList, activeMinutesList, links),
  });
};
// successful api update
export const updateSuccess = (state, action) => {
  const { activeMinutes } = action;
  return state.merge({
    updateSuccess: true,
    updating: false,
    errorUpdating: null,
    activeMinutes,
  });
};
// successful api delete
export const deleteSuccess = (state) => {
  return state.merge({
    deleting: false,
    errorDeleting: null,
    activeMinutes: INITIAL_STATE.activeMinutes,
  });
};

// Something went wrong fetching a single entity.
export const failure = (state, action) => {
  const { error } = action;
  return state.merge({
    fetchingOne: false,
    errorOne: error,
    activeMinutes: INITIAL_STATE.activeMinutes,
  });
};
// Something went wrong fetching all entities.
export const allFailure = (state, action) => {
  const { error } = action;
  return state.merge({
    fetchingAll: false,
    errorAll: error,
    activeMinutesList: [],
  });
};
// Something went wrong updating.
export const updateFailure = (state, action) => {
  const { error } = action;
  return state.merge({
    updateSuccess: false,
    updating: false,
    errorUpdating: error,
    activeMinutes: state.activeMinutes,
  });
};
// Something went wrong deleting.
export const deleteFailure = (state, action) => {
  const { error } = action;
  return state.merge({
    deleting: false,
    errorDeleting: error,
    activeMinutes: state.activeMinutes,
  });
};

export const reset = (state) => INITIAL_STATE;

/* ------------- Hookup Reducers To Types ------------- */

export const reducer = createReducer(INITIAL_STATE, {
  [Types.ACTIVE_MINUTES_REQUEST]: request,
  [Types.ACTIVE_MINUTES_ALL_REQUEST]: allRequest,
  [Types.ACTIVE_MINUTES_UPDATE_REQUEST]: updateRequest,
  [Types.ACTIVE_MINUTES_DELETE_REQUEST]: deleteRequest,

  [Types.ACTIVE_MINUTES_SUCCESS]: success,
  [Types.ACTIVE_MINUTES_ALL_SUCCESS]: allSuccess,
  [Types.ACTIVE_MINUTES_UPDATE_SUCCESS]: updateSuccess,
  [Types.ACTIVE_MINUTES_DELETE_SUCCESS]: deleteSuccess,

  [Types.ACTIVE_MINUTES_FAILURE]: failure,
  [Types.ACTIVE_MINUTES_ALL_FAILURE]: allFailure,
  [Types.ACTIVE_MINUTES_UPDATE_FAILURE]: updateFailure,
  [Types.ACTIVE_MINUTES_DELETE_FAILURE]: deleteFailure,
  [Types.ACTIVE_MINUTES_RESET]: reset,
});
