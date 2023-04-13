import { createReducer, createActions } from 'reduxsauce';
import Immutable from 'seamless-immutable';
import { loadMoreDataWhenScrolled } from '../../../shared/util/pagination-utils';
import { parseHeaderForLinks } from '../../../shared/util/url-utils';

/* ------------- Types and Action Creators ------------- */

const { Types, Creators } = createActions({
  powerSummaryRequest: ['powerSummaryId'],
  powerSummaryAllRequest: ['options'],
  powerSummaryUpdateRequest: ['powerSummary'],
  powerSummaryDeleteRequest: ['powerSummaryId'],

  powerSummarySuccess: ['powerSummary'],
  powerSummaryAllSuccess: ['powerSummaryList', 'headers'],
  powerSummaryUpdateSuccess: ['powerSummary'],
  powerSummaryDeleteSuccess: [],

  powerSummaryFailure: ['error'],
  powerSummaryAllFailure: ['error'],
  powerSummaryUpdateFailure: ['error'],
  powerSummaryDeleteFailure: ['error'],

  powerSummaryReset: [],
});

export const PowerSummaryTypes = Types;
export default Creators;

/* ------------- Initial State ------------- */

export const INITIAL_STATE = Immutable({
  fetchingOne: false,
  fetchingAll: false,
  updating: false,
  deleting: false,
  updateSuccess: false,
  powerSummary: { id: undefined },
  powerSummaryList: [],
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
    powerSummary: INITIAL_STATE.powerSummary,
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
  const { powerSummary } = action;
  return state.merge({
    fetchingOne: false,
    errorOne: null,
    powerSummary,
  });
};
// successful api lookup for all entities
export const allSuccess = (state, action) => {
  const { powerSummaryList, headers } = action;
  const links = parseHeaderForLinks(headers.link);
  return state.merge({
    fetchingAll: false,
    errorAll: null,
    links,
    totalItems: parseInt(headers['x-total-count'], 10),
    powerSummaryList: loadMoreDataWhenScrolled(state.powerSummaryList, powerSummaryList, links),
  });
};
// successful api update
export const updateSuccess = (state, action) => {
  const { powerSummary } = action;
  return state.merge({
    updateSuccess: true,
    updating: false,
    errorUpdating: null,
    powerSummary,
  });
};
// successful api delete
export const deleteSuccess = (state) => {
  return state.merge({
    deleting: false,
    errorDeleting: null,
    powerSummary: INITIAL_STATE.powerSummary,
  });
};

// Something went wrong fetching a single entity.
export const failure = (state, action) => {
  const { error } = action;
  return state.merge({
    fetchingOne: false,
    errorOne: error,
    powerSummary: INITIAL_STATE.powerSummary,
  });
};
// Something went wrong fetching all entities.
export const allFailure = (state, action) => {
  const { error } = action;
  return state.merge({
    fetchingAll: false,
    errorAll: error,
    powerSummaryList: [],
  });
};
// Something went wrong updating.
export const updateFailure = (state, action) => {
  const { error } = action;
  return state.merge({
    updateSuccess: false,
    updating: false,
    errorUpdating: error,
    powerSummary: state.powerSummary,
  });
};
// Something went wrong deleting.
export const deleteFailure = (state, action) => {
  const { error } = action;
  return state.merge({
    deleting: false,
    errorDeleting: error,
    powerSummary: state.powerSummary,
  });
};

export const reset = (state) => INITIAL_STATE;

/* ------------- Hookup Reducers To Types ------------- */

export const reducer = createReducer(INITIAL_STATE, {
  [Types.POWER_SUMMARY_REQUEST]: request,
  [Types.POWER_SUMMARY_ALL_REQUEST]: allRequest,
  [Types.POWER_SUMMARY_UPDATE_REQUEST]: updateRequest,
  [Types.POWER_SUMMARY_DELETE_REQUEST]: deleteRequest,

  [Types.POWER_SUMMARY_SUCCESS]: success,
  [Types.POWER_SUMMARY_ALL_SUCCESS]: allSuccess,
  [Types.POWER_SUMMARY_UPDATE_SUCCESS]: updateSuccess,
  [Types.POWER_SUMMARY_DELETE_SUCCESS]: deleteSuccess,

  [Types.POWER_SUMMARY_FAILURE]: failure,
  [Types.POWER_SUMMARY_ALL_FAILURE]: allFailure,
  [Types.POWER_SUMMARY_UPDATE_FAILURE]: updateFailure,
  [Types.POWER_SUMMARY_DELETE_FAILURE]: deleteFailure,
  [Types.POWER_SUMMARY_RESET]: reset,
});
