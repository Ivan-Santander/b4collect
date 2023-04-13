import { createReducer, createActions } from 'reduxsauce';
import Immutable from 'seamless-immutable';
import { loadMoreDataWhenScrolled } from '../../../shared/util/pagination-utils';
import { parseHeaderForLinks } from '../../../shared/util/url-utils';

/* ------------- Types and Action Creators ------------- */

const { Types, Creators } = createActions({
  speedSummaryRequest: ['speedSummaryId'],
  speedSummaryAllRequest: ['options'],
  speedSummaryUpdateRequest: ['speedSummary'],
  speedSummaryDeleteRequest: ['speedSummaryId'],

  speedSummarySuccess: ['speedSummary'],
  speedSummaryAllSuccess: ['speedSummaryList', 'headers'],
  speedSummaryUpdateSuccess: ['speedSummary'],
  speedSummaryDeleteSuccess: [],

  speedSummaryFailure: ['error'],
  speedSummaryAllFailure: ['error'],
  speedSummaryUpdateFailure: ['error'],
  speedSummaryDeleteFailure: ['error'],

  speedSummaryReset: [],
});

export const SpeedSummaryTypes = Types;
export default Creators;

/* ------------- Initial State ------------- */

export const INITIAL_STATE = Immutable({
  fetchingOne: false,
  fetchingAll: false,
  updating: false,
  deleting: false,
  updateSuccess: false,
  speedSummary: { id: undefined },
  speedSummaryList: [],
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
    speedSummary: INITIAL_STATE.speedSummary,
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
  const { speedSummary } = action;
  return state.merge({
    fetchingOne: false,
    errorOne: null,
    speedSummary,
  });
};
// successful api lookup for all entities
export const allSuccess = (state, action) => {
  const { speedSummaryList, headers } = action;
  const links = parseHeaderForLinks(headers.link);
  return state.merge({
    fetchingAll: false,
    errorAll: null,
    links,
    totalItems: parseInt(headers['x-total-count'], 10),
    speedSummaryList: loadMoreDataWhenScrolled(state.speedSummaryList, speedSummaryList, links),
  });
};
// successful api update
export const updateSuccess = (state, action) => {
  const { speedSummary } = action;
  return state.merge({
    updateSuccess: true,
    updating: false,
    errorUpdating: null,
    speedSummary,
  });
};
// successful api delete
export const deleteSuccess = (state) => {
  return state.merge({
    deleting: false,
    errorDeleting: null,
    speedSummary: INITIAL_STATE.speedSummary,
  });
};

// Something went wrong fetching a single entity.
export const failure = (state, action) => {
  const { error } = action;
  return state.merge({
    fetchingOne: false,
    errorOne: error,
    speedSummary: INITIAL_STATE.speedSummary,
  });
};
// Something went wrong fetching all entities.
export const allFailure = (state, action) => {
  const { error } = action;
  return state.merge({
    fetchingAll: false,
    errorAll: error,
    speedSummaryList: [],
  });
};
// Something went wrong updating.
export const updateFailure = (state, action) => {
  const { error } = action;
  return state.merge({
    updateSuccess: false,
    updating: false,
    errorUpdating: error,
    speedSummary: state.speedSummary,
  });
};
// Something went wrong deleting.
export const deleteFailure = (state, action) => {
  const { error } = action;
  return state.merge({
    deleting: false,
    errorDeleting: error,
    speedSummary: state.speedSummary,
  });
};

export const reset = (state) => INITIAL_STATE;

/* ------------- Hookup Reducers To Types ------------- */

export const reducer = createReducer(INITIAL_STATE, {
  [Types.SPEED_SUMMARY_REQUEST]: request,
  [Types.SPEED_SUMMARY_ALL_REQUEST]: allRequest,
  [Types.SPEED_SUMMARY_UPDATE_REQUEST]: updateRequest,
  [Types.SPEED_SUMMARY_DELETE_REQUEST]: deleteRequest,

  [Types.SPEED_SUMMARY_SUCCESS]: success,
  [Types.SPEED_SUMMARY_ALL_SUCCESS]: allSuccess,
  [Types.SPEED_SUMMARY_UPDATE_SUCCESS]: updateSuccess,
  [Types.SPEED_SUMMARY_DELETE_SUCCESS]: deleteSuccess,

  [Types.SPEED_SUMMARY_FAILURE]: failure,
  [Types.SPEED_SUMMARY_ALL_FAILURE]: allFailure,
  [Types.SPEED_SUMMARY_UPDATE_FAILURE]: updateFailure,
  [Types.SPEED_SUMMARY_DELETE_FAILURE]: deleteFailure,
  [Types.SPEED_SUMMARY_RESET]: reset,
});
