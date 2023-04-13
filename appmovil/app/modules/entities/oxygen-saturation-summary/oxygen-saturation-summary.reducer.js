import { createReducer, createActions } from 'reduxsauce';
import Immutable from 'seamless-immutable';
import { loadMoreDataWhenScrolled } from '../../../shared/util/pagination-utils';
import { parseHeaderForLinks } from '../../../shared/util/url-utils';

/* ------------- Types and Action Creators ------------- */

const { Types, Creators } = createActions({
  oxygenSaturationSummaryRequest: ['oxygenSaturationSummaryId'],
  oxygenSaturationSummaryAllRequest: ['options'],
  oxygenSaturationSummaryUpdateRequest: ['oxygenSaturationSummary'],
  oxygenSaturationSummaryDeleteRequest: ['oxygenSaturationSummaryId'],

  oxygenSaturationSummarySuccess: ['oxygenSaturationSummary'],
  oxygenSaturationSummaryAllSuccess: ['oxygenSaturationSummaryList', 'headers'],
  oxygenSaturationSummaryUpdateSuccess: ['oxygenSaturationSummary'],
  oxygenSaturationSummaryDeleteSuccess: [],

  oxygenSaturationSummaryFailure: ['error'],
  oxygenSaturationSummaryAllFailure: ['error'],
  oxygenSaturationSummaryUpdateFailure: ['error'],
  oxygenSaturationSummaryDeleteFailure: ['error'],

  oxygenSaturationSummaryReset: [],
});

export const OxygenSaturationSummaryTypes = Types;
export default Creators;

/* ------------- Initial State ------------- */

export const INITIAL_STATE = Immutable({
  fetchingOne: false,
  fetchingAll: false,
  updating: false,
  deleting: false,
  updateSuccess: false,
  oxygenSaturationSummary: { id: undefined },
  oxygenSaturationSummaryList: [],
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
    oxygenSaturationSummary: INITIAL_STATE.oxygenSaturationSummary,
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
  const { oxygenSaturationSummary } = action;
  return state.merge({
    fetchingOne: false,
    errorOne: null,
    oxygenSaturationSummary,
  });
};
// successful api lookup for all entities
export const allSuccess = (state, action) => {
  const { oxygenSaturationSummaryList, headers } = action;
  const links = parseHeaderForLinks(headers.link);
  return state.merge({
    fetchingAll: false,
    errorAll: null,
    links,
    totalItems: parseInt(headers['x-total-count'], 10),
    oxygenSaturationSummaryList: loadMoreDataWhenScrolled(state.oxygenSaturationSummaryList, oxygenSaturationSummaryList, links),
  });
};
// successful api update
export const updateSuccess = (state, action) => {
  const { oxygenSaturationSummary } = action;
  return state.merge({
    updateSuccess: true,
    updating: false,
    errorUpdating: null,
    oxygenSaturationSummary,
  });
};
// successful api delete
export const deleteSuccess = (state) => {
  return state.merge({
    deleting: false,
    errorDeleting: null,
    oxygenSaturationSummary: INITIAL_STATE.oxygenSaturationSummary,
  });
};

// Something went wrong fetching a single entity.
export const failure = (state, action) => {
  const { error } = action;
  return state.merge({
    fetchingOne: false,
    errorOne: error,
    oxygenSaturationSummary: INITIAL_STATE.oxygenSaturationSummary,
  });
};
// Something went wrong fetching all entities.
export const allFailure = (state, action) => {
  const { error } = action;
  return state.merge({
    fetchingAll: false,
    errorAll: error,
    oxygenSaturationSummaryList: [],
  });
};
// Something went wrong updating.
export const updateFailure = (state, action) => {
  const { error } = action;
  return state.merge({
    updateSuccess: false,
    updating: false,
    errorUpdating: error,
    oxygenSaturationSummary: state.oxygenSaturationSummary,
  });
};
// Something went wrong deleting.
export const deleteFailure = (state, action) => {
  const { error } = action;
  return state.merge({
    deleting: false,
    errorDeleting: error,
    oxygenSaturationSummary: state.oxygenSaturationSummary,
  });
};

export const reset = (state) => INITIAL_STATE;

/* ------------- Hookup Reducers To Types ------------- */

export const reducer = createReducer(INITIAL_STATE, {
  [Types.OXYGEN_SATURATION_SUMMARY_REQUEST]: request,
  [Types.OXYGEN_SATURATION_SUMMARY_ALL_REQUEST]: allRequest,
  [Types.OXYGEN_SATURATION_SUMMARY_UPDATE_REQUEST]: updateRequest,
  [Types.OXYGEN_SATURATION_SUMMARY_DELETE_REQUEST]: deleteRequest,

  [Types.OXYGEN_SATURATION_SUMMARY_SUCCESS]: success,
  [Types.OXYGEN_SATURATION_SUMMARY_ALL_SUCCESS]: allSuccess,
  [Types.OXYGEN_SATURATION_SUMMARY_UPDATE_SUCCESS]: updateSuccess,
  [Types.OXYGEN_SATURATION_SUMMARY_DELETE_SUCCESS]: deleteSuccess,

  [Types.OXYGEN_SATURATION_SUMMARY_FAILURE]: failure,
  [Types.OXYGEN_SATURATION_SUMMARY_ALL_FAILURE]: allFailure,
  [Types.OXYGEN_SATURATION_SUMMARY_UPDATE_FAILURE]: updateFailure,
  [Types.OXYGEN_SATURATION_SUMMARY_DELETE_FAILURE]: deleteFailure,
  [Types.OXYGEN_SATURATION_SUMMARY_RESET]: reset,
});
