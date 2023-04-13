import { createReducer, createActions } from 'reduxsauce';
import Immutable from 'seamless-immutable';
import { loadMoreDataWhenScrolled } from '../../../shared/util/pagination-utils';
import { parseHeaderForLinks } from '../../../shared/util/url-utils';

/* ------------- Types and Action Creators ------------- */

const { Types, Creators } = createActions({
  bodyFatPercentageSummaryRequest: ['bodyFatPercentageSummaryId'],
  bodyFatPercentageSummaryAllRequest: ['options'],
  bodyFatPercentageSummaryUpdateRequest: ['bodyFatPercentageSummary'],
  bodyFatPercentageSummaryDeleteRequest: ['bodyFatPercentageSummaryId'],

  bodyFatPercentageSummarySuccess: ['bodyFatPercentageSummary'],
  bodyFatPercentageSummaryAllSuccess: ['bodyFatPercentageSummaryList', 'headers'],
  bodyFatPercentageSummaryUpdateSuccess: ['bodyFatPercentageSummary'],
  bodyFatPercentageSummaryDeleteSuccess: [],

  bodyFatPercentageSummaryFailure: ['error'],
  bodyFatPercentageSummaryAllFailure: ['error'],
  bodyFatPercentageSummaryUpdateFailure: ['error'],
  bodyFatPercentageSummaryDeleteFailure: ['error'],

  bodyFatPercentageSummaryReset: [],
});

export const BodyFatPercentageSummaryTypes = Types;
export default Creators;

/* ------------- Initial State ------------- */

export const INITIAL_STATE = Immutable({
  fetchingOne: false,
  fetchingAll: false,
  updating: false,
  deleting: false,
  updateSuccess: false,
  bodyFatPercentageSummary: { id: undefined },
  bodyFatPercentageSummaryList: [],
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
    bodyFatPercentageSummary: INITIAL_STATE.bodyFatPercentageSummary,
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
  const { bodyFatPercentageSummary } = action;
  return state.merge({
    fetchingOne: false,
    errorOne: null,
    bodyFatPercentageSummary,
  });
};
// successful api lookup for all entities
export const allSuccess = (state, action) => {
  const { bodyFatPercentageSummaryList, headers } = action;
  const links = parseHeaderForLinks(headers.link);
  return state.merge({
    fetchingAll: false,
    errorAll: null,
    links,
    totalItems: parseInt(headers['x-total-count'], 10),
    bodyFatPercentageSummaryList: loadMoreDataWhenScrolled(state.bodyFatPercentageSummaryList, bodyFatPercentageSummaryList, links),
  });
};
// successful api update
export const updateSuccess = (state, action) => {
  const { bodyFatPercentageSummary } = action;
  return state.merge({
    updateSuccess: true,
    updating: false,
    errorUpdating: null,
    bodyFatPercentageSummary,
  });
};
// successful api delete
export const deleteSuccess = (state) => {
  return state.merge({
    deleting: false,
    errorDeleting: null,
    bodyFatPercentageSummary: INITIAL_STATE.bodyFatPercentageSummary,
  });
};

// Something went wrong fetching a single entity.
export const failure = (state, action) => {
  const { error } = action;
  return state.merge({
    fetchingOne: false,
    errorOne: error,
    bodyFatPercentageSummary: INITIAL_STATE.bodyFatPercentageSummary,
  });
};
// Something went wrong fetching all entities.
export const allFailure = (state, action) => {
  const { error } = action;
  return state.merge({
    fetchingAll: false,
    errorAll: error,
    bodyFatPercentageSummaryList: [],
  });
};
// Something went wrong updating.
export const updateFailure = (state, action) => {
  const { error } = action;
  return state.merge({
    updateSuccess: false,
    updating: false,
    errorUpdating: error,
    bodyFatPercentageSummary: state.bodyFatPercentageSummary,
  });
};
// Something went wrong deleting.
export const deleteFailure = (state, action) => {
  const { error } = action;
  return state.merge({
    deleting: false,
    errorDeleting: error,
    bodyFatPercentageSummary: state.bodyFatPercentageSummary,
  });
};

export const reset = (state) => INITIAL_STATE;

/* ------------- Hookup Reducers To Types ------------- */

export const reducer = createReducer(INITIAL_STATE, {
  [Types.BODY_FAT_PERCENTAGE_SUMMARY_REQUEST]: request,
  [Types.BODY_FAT_PERCENTAGE_SUMMARY_ALL_REQUEST]: allRequest,
  [Types.BODY_FAT_PERCENTAGE_SUMMARY_UPDATE_REQUEST]: updateRequest,
  [Types.BODY_FAT_PERCENTAGE_SUMMARY_DELETE_REQUEST]: deleteRequest,

  [Types.BODY_FAT_PERCENTAGE_SUMMARY_SUCCESS]: success,
  [Types.BODY_FAT_PERCENTAGE_SUMMARY_ALL_SUCCESS]: allSuccess,
  [Types.BODY_FAT_PERCENTAGE_SUMMARY_UPDATE_SUCCESS]: updateSuccess,
  [Types.BODY_FAT_PERCENTAGE_SUMMARY_DELETE_SUCCESS]: deleteSuccess,

  [Types.BODY_FAT_PERCENTAGE_SUMMARY_FAILURE]: failure,
  [Types.BODY_FAT_PERCENTAGE_SUMMARY_ALL_FAILURE]: allFailure,
  [Types.BODY_FAT_PERCENTAGE_SUMMARY_UPDATE_FAILURE]: updateFailure,
  [Types.BODY_FAT_PERCENTAGE_SUMMARY_DELETE_FAILURE]: deleteFailure,
  [Types.BODY_FAT_PERCENTAGE_SUMMARY_RESET]: reset,
});
