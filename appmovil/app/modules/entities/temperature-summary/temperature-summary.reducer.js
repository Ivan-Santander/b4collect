import { createReducer, createActions } from 'reduxsauce';
import Immutable from 'seamless-immutable';
import { loadMoreDataWhenScrolled } from '../../../shared/util/pagination-utils';
import { parseHeaderForLinks } from '../../../shared/util/url-utils';

/* ------------- Types and Action Creators ------------- */

const { Types, Creators } = createActions({
  temperatureSummaryRequest: ['temperatureSummaryId'],
  temperatureSummaryAllRequest: ['options'],
  temperatureSummaryUpdateRequest: ['temperatureSummary'],
  temperatureSummaryDeleteRequest: ['temperatureSummaryId'],

  temperatureSummarySuccess: ['temperatureSummary'],
  temperatureSummaryAllSuccess: ['temperatureSummaryList', 'headers'],
  temperatureSummaryUpdateSuccess: ['temperatureSummary'],
  temperatureSummaryDeleteSuccess: [],

  temperatureSummaryFailure: ['error'],
  temperatureSummaryAllFailure: ['error'],
  temperatureSummaryUpdateFailure: ['error'],
  temperatureSummaryDeleteFailure: ['error'],

  temperatureSummaryReset: [],
});

export const TemperatureSummaryTypes = Types;
export default Creators;

/* ------------- Initial State ------------- */

export const INITIAL_STATE = Immutable({
  fetchingOne: false,
  fetchingAll: false,
  updating: false,
  deleting: false,
  updateSuccess: false,
  temperatureSummary: { id: undefined },
  temperatureSummaryList: [],
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
    temperatureSummary: INITIAL_STATE.temperatureSummary,
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
  const { temperatureSummary } = action;
  return state.merge({
    fetchingOne: false,
    errorOne: null,
    temperatureSummary,
  });
};
// successful api lookup for all entities
export const allSuccess = (state, action) => {
  const { temperatureSummaryList, headers } = action;
  const links = parseHeaderForLinks(headers.link);
  return state.merge({
    fetchingAll: false,
    errorAll: null,
    links,
    totalItems: parseInt(headers['x-total-count'], 10),
    temperatureSummaryList: loadMoreDataWhenScrolled(state.temperatureSummaryList, temperatureSummaryList, links),
  });
};
// successful api update
export const updateSuccess = (state, action) => {
  const { temperatureSummary } = action;
  return state.merge({
    updateSuccess: true,
    updating: false,
    errorUpdating: null,
    temperatureSummary,
  });
};
// successful api delete
export const deleteSuccess = (state) => {
  return state.merge({
    deleting: false,
    errorDeleting: null,
    temperatureSummary: INITIAL_STATE.temperatureSummary,
  });
};

// Something went wrong fetching a single entity.
export const failure = (state, action) => {
  const { error } = action;
  return state.merge({
    fetchingOne: false,
    errorOne: error,
    temperatureSummary: INITIAL_STATE.temperatureSummary,
  });
};
// Something went wrong fetching all entities.
export const allFailure = (state, action) => {
  const { error } = action;
  return state.merge({
    fetchingAll: false,
    errorAll: error,
    temperatureSummaryList: [],
  });
};
// Something went wrong updating.
export const updateFailure = (state, action) => {
  const { error } = action;
  return state.merge({
    updateSuccess: false,
    updating: false,
    errorUpdating: error,
    temperatureSummary: state.temperatureSummary,
  });
};
// Something went wrong deleting.
export const deleteFailure = (state, action) => {
  const { error } = action;
  return state.merge({
    deleting: false,
    errorDeleting: error,
    temperatureSummary: state.temperatureSummary,
  });
};

export const reset = (state) => INITIAL_STATE;

/* ------------- Hookup Reducers To Types ------------- */

export const reducer = createReducer(INITIAL_STATE, {
  [Types.TEMPERATURE_SUMMARY_REQUEST]: request,
  [Types.TEMPERATURE_SUMMARY_ALL_REQUEST]: allRequest,
  [Types.TEMPERATURE_SUMMARY_UPDATE_REQUEST]: updateRequest,
  [Types.TEMPERATURE_SUMMARY_DELETE_REQUEST]: deleteRequest,

  [Types.TEMPERATURE_SUMMARY_SUCCESS]: success,
  [Types.TEMPERATURE_SUMMARY_ALL_SUCCESS]: allSuccess,
  [Types.TEMPERATURE_SUMMARY_UPDATE_SUCCESS]: updateSuccess,
  [Types.TEMPERATURE_SUMMARY_DELETE_SUCCESS]: deleteSuccess,

  [Types.TEMPERATURE_SUMMARY_FAILURE]: failure,
  [Types.TEMPERATURE_SUMMARY_ALL_FAILURE]: allFailure,
  [Types.TEMPERATURE_SUMMARY_UPDATE_FAILURE]: updateFailure,
  [Types.TEMPERATURE_SUMMARY_DELETE_FAILURE]: deleteFailure,
  [Types.TEMPERATURE_SUMMARY_RESET]: reset,
});
