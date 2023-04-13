import { createReducer, createActions } from 'reduxsauce';
import Immutable from 'seamless-immutable';
import { loadMoreDataWhenScrolled } from '../../../shared/util/pagination-utils';
import { parseHeaderForLinks } from '../../../shared/util/url-utils';

/* ------------- Types and Action Creators ------------- */

const { Types, Creators } = createActions({
  weightSummaryRequest: ['weightSummaryId'],
  weightSummaryAllRequest: ['options'],
  weightSummaryUpdateRequest: ['weightSummary'],
  weightSummaryDeleteRequest: ['weightSummaryId'],

  weightSummarySuccess: ['weightSummary'],
  weightSummaryAllSuccess: ['weightSummaryList', 'headers'],
  weightSummaryUpdateSuccess: ['weightSummary'],
  weightSummaryDeleteSuccess: [],

  weightSummaryFailure: ['error'],
  weightSummaryAllFailure: ['error'],
  weightSummaryUpdateFailure: ['error'],
  weightSummaryDeleteFailure: ['error'],

  weightSummaryReset: [],
});

export const WeightSummaryTypes = Types;
export default Creators;

/* ------------- Initial State ------------- */

export const INITIAL_STATE = Immutable({
  fetchingOne: false,
  fetchingAll: false,
  updating: false,
  deleting: false,
  updateSuccess: false,
  weightSummary: { id: undefined },
  weightSummaryList: [],
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
    weightSummary: INITIAL_STATE.weightSummary,
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
  const { weightSummary } = action;
  return state.merge({
    fetchingOne: false,
    errorOne: null,
    weightSummary,
  });
};
// successful api lookup for all entities
export const allSuccess = (state, action) => {
  const { weightSummaryList, headers } = action;
  const links = parseHeaderForLinks(headers.link);
  return state.merge({
    fetchingAll: false,
    errorAll: null,
    links,
    totalItems: parseInt(headers['x-total-count'], 10),
    weightSummaryList: loadMoreDataWhenScrolled(state.weightSummaryList, weightSummaryList, links),
  });
};
// successful api update
export const updateSuccess = (state, action) => {
  const { weightSummary } = action;
  return state.merge({
    updateSuccess: true,
    updating: false,
    errorUpdating: null,
    weightSummary,
  });
};
// successful api delete
export const deleteSuccess = (state) => {
  return state.merge({
    deleting: false,
    errorDeleting: null,
    weightSummary: INITIAL_STATE.weightSummary,
  });
};

// Something went wrong fetching a single entity.
export const failure = (state, action) => {
  const { error } = action;
  return state.merge({
    fetchingOne: false,
    errorOne: error,
    weightSummary: INITIAL_STATE.weightSummary,
  });
};
// Something went wrong fetching all entities.
export const allFailure = (state, action) => {
  const { error } = action;
  return state.merge({
    fetchingAll: false,
    errorAll: error,
    weightSummaryList: [],
  });
};
// Something went wrong updating.
export const updateFailure = (state, action) => {
  const { error } = action;
  return state.merge({
    updateSuccess: false,
    updating: false,
    errorUpdating: error,
    weightSummary: state.weightSummary,
  });
};
// Something went wrong deleting.
export const deleteFailure = (state, action) => {
  const { error } = action;
  return state.merge({
    deleting: false,
    errorDeleting: error,
    weightSummary: state.weightSummary,
  });
};

export const reset = (state) => INITIAL_STATE;

/* ------------- Hookup Reducers To Types ------------- */

export const reducer = createReducer(INITIAL_STATE, {
  [Types.WEIGHT_SUMMARY_REQUEST]: request,
  [Types.WEIGHT_SUMMARY_ALL_REQUEST]: allRequest,
  [Types.WEIGHT_SUMMARY_UPDATE_REQUEST]: updateRequest,
  [Types.WEIGHT_SUMMARY_DELETE_REQUEST]: deleteRequest,

  [Types.WEIGHT_SUMMARY_SUCCESS]: success,
  [Types.WEIGHT_SUMMARY_ALL_SUCCESS]: allSuccess,
  [Types.WEIGHT_SUMMARY_UPDATE_SUCCESS]: updateSuccess,
  [Types.WEIGHT_SUMMARY_DELETE_SUCCESS]: deleteSuccess,

  [Types.WEIGHT_SUMMARY_FAILURE]: failure,
  [Types.WEIGHT_SUMMARY_ALL_FAILURE]: allFailure,
  [Types.WEIGHT_SUMMARY_UPDATE_FAILURE]: updateFailure,
  [Types.WEIGHT_SUMMARY_DELETE_FAILURE]: deleteFailure,
  [Types.WEIGHT_SUMMARY_RESET]: reset,
});
