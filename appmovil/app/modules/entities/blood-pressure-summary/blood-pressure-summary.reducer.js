import { createReducer, createActions } from 'reduxsauce';
import Immutable from 'seamless-immutable';
import { loadMoreDataWhenScrolled } from '../../../shared/util/pagination-utils';
import { parseHeaderForLinks } from '../../../shared/util/url-utils';

/* ------------- Types and Action Creators ------------- */

const { Types, Creators } = createActions({
  bloodPressureSummaryRequest: ['bloodPressureSummaryId'],
  bloodPressureSummaryAllRequest: ['options'],
  bloodPressureSummaryUpdateRequest: ['bloodPressureSummary'],
  bloodPressureSummaryDeleteRequest: ['bloodPressureSummaryId'],

  bloodPressureSummarySuccess: ['bloodPressureSummary'],
  bloodPressureSummaryAllSuccess: ['bloodPressureSummaryList', 'headers'],
  bloodPressureSummaryUpdateSuccess: ['bloodPressureSummary'],
  bloodPressureSummaryDeleteSuccess: [],

  bloodPressureSummaryFailure: ['error'],
  bloodPressureSummaryAllFailure: ['error'],
  bloodPressureSummaryUpdateFailure: ['error'],
  bloodPressureSummaryDeleteFailure: ['error'],

  bloodPressureSummaryReset: [],
});

export const BloodPressureSummaryTypes = Types;
export default Creators;

/* ------------- Initial State ------------- */

export const INITIAL_STATE = Immutable({
  fetchingOne: false,
  fetchingAll: false,
  updating: false,
  deleting: false,
  updateSuccess: false,
  bloodPressureSummary: { id: undefined },
  bloodPressureSummaryList: [],
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
    bloodPressureSummary: INITIAL_STATE.bloodPressureSummary,
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
  const { bloodPressureSummary } = action;
  return state.merge({
    fetchingOne: false,
    errorOne: null,
    bloodPressureSummary,
  });
};
// successful api lookup for all entities
export const allSuccess = (state, action) => {
  const { bloodPressureSummaryList, headers } = action;
  const links = parseHeaderForLinks(headers.link);
  return state.merge({
    fetchingAll: false,
    errorAll: null,
    links,
    totalItems: parseInt(headers['x-total-count'], 10),
    bloodPressureSummaryList: loadMoreDataWhenScrolled(state.bloodPressureSummaryList, bloodPressureSummaryList, links),
  });
};
// successful api update
export const updateSuccess = (state, action) => {
  const { bloodPressureSummary } = action;
  return state.merge({
    updateSuccess: true,
    updating: false,
    errorUpdating: null,
    bloodPressureSummary,
  });
};
// successful api delete
export const deleteSuccess = (state) => {
  return state.merge({
    deleting: false,
    errorDeleting: null,
    bloodPressureSummary: INITIAL_STATE.bloodPressureSummary,
  });
};

// Something went wrong fetching a single entity.
export const failure = (state, action) => {
  const { error } = action;
  return state.merge({
    fetchingOne: false,
    errorOne: error,
    bloodPressureSummary: INITIAL_STATE.bloodPressureSummary,
  });
};
// Something went wrong fetching all entities.
export const allFailure = (state, action) => {
  const { error } = action;
  return state.merge({
    fetchingAll: false,
    errorAll: error,
    bloodPressureSummaryList: [],
  });
};
// Something went wrong updating.
export const updateFailure = (state, action) => {
  const { error } = action;
  return state.merge({
    updateSuccess: false,
    updating: false,
    errorUpdating: error,
    bloodPressureSummary: state.bloodPressureSummary,
  });
};
// Something went wrong deleting.
export const deleteFailure = (state, action) => {
  const { error } = action;
  return state.merge({
    deleting: false,
    errorDeleting: error,
    bloodPressureSummary: state.bloodPressureSummary,
  });
};

export const reset = (state) => INITIAL_STATE;

/* ------------- Hookup Reducers To Types ------------- */

export const reducer = createReducer(INITIAL_STATE, {
  [Types.BLOOD_PRESSURE_SUMMARY_REQUEST]: request,
  [Types.BLOOD_PRESSURE_SUMMARY_ALL_REQUEST]: allRequest,
  [Types.BLOOD_PRESSURE_SUMMARY_UPDATE_REQUEST]: updateRequest,
  [Types.BLOOD_PRESSURE_SUMMARY_DELETE_REQUEST]: deleteRequest,

  [Types.BLOOD_PRESSURE_SUMMARY_SUCCESS]: success,
  [Types.BLOOD_PRESSURE_SUMMARY_ALL_SUCCESS]: allSuccess,
  [Types.BLOOD_PRESSURE_SUMMARY_UPDATE_SUCCESS]: updateSuccess,
  [Types.BLOOD_PRESSURE_SUMMARY_DELETE_SUCCESS]: deleteSuccess,

  [Types.BLOOD_PRESSURE_SUMMARY_FAILURE]: failure,
  [Types.BLOOD_PRESSURE_SUMMARY_ALL_FAILURE]: allFailure,
  [Types.BLOOD_PRESSURE_SUMMARY_UPDATE_FAILURE]: updateFailure,
  [Types.BLOOD_PRESSURE_SUMMARY_DELETE_FAILURE]: deleteFailure,
  [Types.BLOOD_PRESSURE_SUMMARY_RESET]: reset,
});
