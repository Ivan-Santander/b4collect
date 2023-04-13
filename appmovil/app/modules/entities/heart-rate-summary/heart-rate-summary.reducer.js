import { createReducer, createActions } from 'reduxsauce';
import Immutable from 'seamless-immutable';
import { loadMoreDataWhenScrolled } from '../../../shared/util/pagination-utils';
import { parseHeaderForLinks } from '../../../shared/util/url-utils';

/* ------------- Types and Action Creators ------------- */

const { Types, Creators } = createActions({
  heartRateSummaryRequest: ['heartRateSummaryId'],
  heartRateSummaryAllRequest: ['options'],
  heartRateSummaryUpdateRequest: ['heartRateSummary'],
  heartRateSummaryDeleteRequest: ['heartRateSummaryId'],

  heartRateSummarySuccess: ['heartRateSummary'],
  heartRateSummaryAllSuccess: ['heartRateSummaryList', 'headers'],
  heartRateSummaryUpdateSuccess: ['heartRateSummary'],
  heartRateSummaryDeleteSuccess: [],

  heartRateSummaryFailure: ['error'],
  heartRateSummaryAllFailure: ['error'],
  heartRateSummaryUpdateFailure: ['error'],
  heartRateSummaryDeleteFailure: ['error'],

  heartRateSummaryReset: [],
});

export const HeartRateSummaryTypes = Types;
export default Creators;

/* ------------- Initial State ------------- */

export const INITIAL_STATE = Immutable({
  fetchingOne: false,
  fetchingAll: false,
  updating: false,
  deleting: false,
  updateSuccess: false,
  heartRateSummary: { id: undefined },
  heartRateSummaryList: [],
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
    heartRateSummary: INITIAL_STATE.heartRateSummary,
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
  const { heartRateSummary } = action;
  return state.merge({
    fetchingOne: false,
    errorOne: null,
    heartRateSummary,
  });
};
// successful api lookup for all entities
export const allSuccess = (state, action) => {
  const { heartRateSummaryList, headers } = action;
  const links = parseHeaderForLinks(headers.link);
  return state.merge({
    fetchingAll: false,
    errorAll: null,
    links,
    totalItems: parseInt(headers['x-total-count'], 10),
    heartRateSummaryList: loadMoreDataWhenScrolled(state.heartRateSummaryList, heartRateSummaryList, links),
  });
};
// successful api update
export const updateSuccess = (state, action) => {
  const { heartRateSummary } = action;
  return state.merge({
    updateSuccess: true,
    updating: false,
    errorUpdating: null,
    heartRateSummary,
  });
};
// successful api delete
export const deleteSuccess = (state) => {
  return state.merge({
    deleting: false,
    errorDeleting: null,
    heartRateSummary: INITIAL_STATE.heartRateSummary,
  });
};

// Something went wrong fetching a single entity.
export const failure = (state, action) => {
  const { error } = action;
  return state.merge({
    fetchingOne: false,
    errorOne: error,
    heartRateSummary: INITIAL_STATE.heartRateSummary,
  });
};
// Something went wrong fetching all entities.
export const allFailure = (state, action) => {
  const { error } = action;
  return state.merge({
    fetchingAll: false,
    errorAll: error,
    heartRateSummaryList: [],
  });
};
// Something went wrong updating.
export const updateFailure = (state, action) => {
  const { error } = action;
  return state.merge({
    updateSuccess: false,
    updating: false,
    errorUpdating: error,
    heartRateSummary: state.heartRateSummary,
  });
};
// Something went wrong deleting.
export const deleteFailure = (state, action) => {
  const { error } = action;
  return state.merge({
    deleting: false,
    errorDeleting: error,
    heartRateSummary: state.heartRateSummary,
  });
};

export const reset = (state) => INITIAL_STATE;

/* ------------- Hookup Reducers To Types ------------- */

export const reducer = createReducer(INITIAL_STATE, {
  [Types.HEART_RATE_SUMMARY_REQUEST]: request,
  [Types.HEART_RATE_SUMMARY_ALL_REQUEST]: allRequest,
  [Types.HEART_RATE_SUMMARY_UPDATE_REQUEST]: updateRequest,
  [Types.HEART_RATE_SUMMARY_DELETE_REQUEST]: deleteRequest,

  [Types.HEART_RATE_SUMMARY_SUCCESS]: success,
  [Types.HEART_RATE_SUMMARY_ALL_SUCCESS]: allSuccess,
  [Types.HEART_RATE_SUMMARY_UPDATE_SUCCESS]: updateSuccess,
  [Types.HEART_RATE_SUMMARY_DELETE_SUCCESS]: deleteSuccess,

  [Types.HEART_RATE_SUMMARY_FAILURE]: failure,
  [Types.HEART_RATE_SUMMARY_ALL_FAILURE]: allFailure,
  [Types.HEART_RATE_SUMMARY_UPDATE_FAILURE]: updateFailure,
  [Types.HEART_RATE_SUMMARY_DELETE_FAILURE]: deleteFailure,
  [Types.HEART_RATE_SUMMARY_RESET]: reset,
});
