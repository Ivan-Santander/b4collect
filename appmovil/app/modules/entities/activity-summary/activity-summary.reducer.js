import { createReducer, createActions } from 'reduxsauce';
import Immutable from 'seamless-immutable';
import { loadMoreDataWhenScrolled } from '../../../shared/util/pagination-utils';
import { parseHeaderForLinks } from '../../../shared/util/url-utils';

/* ------------- Types and Action Creators ------------- */

const { Types, Creators } = createActions({
  activitySummaryRequest: ['activitySummaryId'],
  activitySummaryAllRequest: ['options'],
  activitySummaryUpdateRequest: ['activitySummary'],
  activitySummaryDeleteRequest: ['activitySummaryId'],

  activitySummarySuccess: ['activitySummary'],
  activitySummaryAllSuccess: ['activitySummaryList', 'headers'],
  activitySummaryUpdateSuccess: ['activitySummary'],
  activitySummaryDeleteSuccess: [],

  activitySummaryFailure: ['error'],
  activitySummaryAllFailure: ['error'],
  activitySummaryUpdateFailure: ['error'],
  activitySummaryDeleteFailure: ['error'],

  activitySummaryReset: [],
});

export const ActivitySummaryTypes = Types;
export default Creators;

/* ------------- Initial State ------------- */

export const INITIAL_STATE = Immutable({
  fetchingOne: false,
  fetchingAll: false,
  updating: false,
  deleting: false,
  updateSuccess: false,
  activitySummary: { id: undefined },
  activitySummaryList: [],
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
    activitySummary: INITIAL_STATE.activitySummary,
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
  const { activitySummary } = action;
  return state.merge({
    fetchingOne: false,
    errorOne: null,
    activitySummary,
  });
};
// successful api lookup for all entities
export const allSuccess = (state, action) => {
  const { activitySummaryList, headers } = action;
  const links = parseHeaderForLinks(headers.link);
  return state.merge({
    fetchingAll: false,
    errorAll: null,
    links,
    totalItems: parseInt(headers['x-total-count'], 10),
    activitySummaryList: loadMoreDataWhenScrolled(state.activitySummaryList, activitySummaryList, links),
  });
};
// successful api update
export const updateSuccess = (state, action) => {
  const { activitySummary } = action;
  return state.merge({
    updateSuccess: true,
    updating: false,
    errorUpdating: null,
    activitySummary,
  });
};
// successful api delete
export const deleteSuccess = (state) => {
  return state.merge({
    deleting: false,
    errorDeleting: null,
    activitySummary: INITIAL_STATE.activitySummary,
  });
};

// Something went wrong fetching a single entity.
export const failure = (state, action) => {
  const { error } = action;
  return state.merge({
    fetchingOne: false,
    errorOne: error,
    activitySummary: INITIAL_STATE.activitySummary,
  });
};
// Something went wrong fetching all entities.
export const allFailure = (state, action) => {
  const { error } = action;
  return state.merge({
    fetchingAll: false,
    errorAll: error,
    activitySummaryList: [],
  });
};
// Something went wrong updating.
export const updateFailure = (state, action) => {
  const { error } = action;
  return state.merge({
    updateSuccess: false,
    updating: false,
    errorUpdating: error,
    activitySummary: state.activitySummary,
  });
};
// Something went wrong deleting.
export const deleteFailure = (state, action) => {
  const { error } = action;
  return state.merge({
    deleting: false,
    errorDeleting: error,
    activitySummary: state.activitySummary,
  });
};

export const reset = (state) => INITIAL_STATE;

/* ------------- Hookup Reducers To Types ------------- */

export const reducer = createReducer(INITIAL_STATE, {
  [Types.ACTIVITY_SUMMARY_REQUEST]: request,
  [Types.ACTIVITY_SUMMARY_ALL_REQUEST]: allRequest,
  [Types.ACTIVITY_SUMMARY_UPDATE_REQUEST]: updateRequest,
  [Types.ACTIVITY_SUMMARY_DELETE_REQUEST]: deleteRequest,

  [Types.ACTIVITY_SUMMARY_SUCCESS]: success,
  [Types.ACTIVITY_SUMMARY_ALL_SUCCESS]: allSuccess,
  [Types.ACTIVITY_SUMMARY_UPDATE_SUCCESS]: updateSuccess,
  [Types.ACTIVITY_SUMMARY_DELETE_SUCCESS]: deleteSuccess,

  [Types.ACTIVITY_SUMMARY_FAILURE]: failure,
  [Types.ACTIVITY_SUMMARY_ALL_FAILURE]: allFailure,
  [Types.ACTIVITY_SUMMARY_UPDATE_FAILURE]: updateFailure,
  [Types.ACTIVITY_SUMMARY_DELETE_FAILURE]: deleteFailure,
  [Types.ACTIVITY_SUMMARY_RESET]: reset,
});
