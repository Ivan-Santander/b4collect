import { createReducer, createActions } from 'reduxsauce';
import Immutable from 'seamless-immutable';
import { loadMoreDataWhenScrolled } from '../../../shared/util/pagination-utils';
import { parseHeaderForLinks } from '../../../shared/util/url-utils';

/* ------------- Types and Action Creators ------------- */

const { Types, Creators } = createActions({
  poweSampleRequest: ['poweSampleId'],
  poweSampleAllRequest: ['options'],
  poweSampleUpdateRequest: ['poweSample'],
  poweSampleDeleteRequest: ['poweSampleId'],

  poweSampleSuccess: ['poweSample'],
  poweSampleAllSuccess: ['poweSampleList', 'headers'],
  poweSampleUpdateSuccess: ['poweSample'],
  poweSampleDeleteSuccess: [],

  poweSampleFailure: ['error'],
  poweSampleAllFailure: ['error'],
  poweSampleUpdateFailure: ['error'],
  poweSampleDeleteFailure: ['error'],

  poweSampleReset: [],
});

export const PoweSampleTypes = Types;
export default Creators;

/* ------------- Initial State ------------- */

export const INITIAL_STATE = Immutable({
  fetchingOne: false,
  fetchingAll: false,
  updating: false,
  deleting: false,
  updateSuccess: false,
  poweSample: { id: undefined },
  poweSampleList: [],
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
    poweSample: INITIAL_STATE.poweSample,
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
  const { poweSample } = action;
  return state.merge({
    fetchingOne: false,
    errorOne: null,
    poweSample,
  });
};
// successful api lookup for all entities
export const allSuccess = (state, action) => {
  const { poweSampleList, headers } = action;
  const links = parseHeaderForLinks(headers.link);
  return state.merge({
    fetchingAll: false,
    errorAll: null,
    links,
    totalItems: parseInt(headers['x-total-count'], 10),
    poweSampleList: loadMoreDataWhenScrolled(state.poweSampleList, poweSampleList, links),
  });
};
// successful api update
export const updateSuccess = (state, action) => {
  const { poweSample } = action;
  return state.merge({
    updateSuccess: true,
    updating: false,
    errorUpdating: null,
    poweSample,
  });
};
// successful api delete
export const deleteSuccess = (state) => {
  return state.merge({
    deleting: false,
    errorDeleting: null,
    poweSample: INITIAL_STATE.poweSample,
  });
};

// Something went wrong fetching a single entity.
export const failure = (state, action) => {
  const { error } = action;
  return state.merge({
    fetchingOne: false,
    errorOne: error,
    poweSample: INITIAL_STATE.poweSample,
  });
};
// Something went wrong fetching all entities.
export const allFailure = (state, action) => {
  const { error } = action;
  return state.merge({
    fetchingAll: false,
    errorAll: error,
    poweSampleList: [],
  });
};
// Something went wrong updating.
export const updateFailure = (state, action) => {
  const { error } = action;
  return state.merge({
    updateSuccess: false,
    updating: false,
    errorUpdating: error,
    poweSample: state.poweSample,
  });
};
// Something went wrong deleting.
export const deleteFailure = (state, action) => {
  const { error } = action;
  return state.merge({
    deleting: false,
    errorDeleting: error,
    poweSample: state.poweSample,
  });
};

export const reset = (state) => INITIAL_STATE;

/* ------------- Hookup Reducers To Types ------------- */

export const reducer = createReducer(INITIAL_STATE, {
  [Types.POWE_SAMPLE_REQUEST]: request,
  [Types.POWE_SAMPLE_ALL_REQUEST]: allRequest,
  [Types.POWE_SAMPLE_UPDATE_REQUEST]: updateRequest,
  [Types.POWE_SAMPLE_DELETE_REQUEST]: deleteRequest,

  [Types.POWE_SAMPLE_SUCCESS]: success,
  [Types.POWE_SAMPLE_ALL_SUCCESS]: allSuccess,
  [Types.POWE_SAMPLE_UPDATE_SUCCESS]: updateSuccess,
  [Types.POWE_SAMPLE_DELETE_SUCCESS]: deleteSuccess,

  [Types.POWE_SAMPLE_FAILURE]: failure,
  [Types.POWE_SAMPLE_ALL_FAILURE]: allFailure,
  [Types.POWE_SAMPLE_UPDATE_FAILURE]: updateFailure,
  [Types.POWE_SAMPLE_DELETE_FAILURE]: deleteFailure,
  [Types.POWE_SAMPLE_RESET]: reset,
});
