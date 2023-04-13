import { createReducer, createActions } from 'reduxsauce';
import Immutable from 'seamless-immutable';
import { loadMoreDataWhenScrolled } from '../../../shared/util/pagination-utils';
import { parseHeaderForLinks } from '../../../shared/util/url-utils';

/* ------------- Types and Action Creators ------------- */

const { Types, Creators } = createActions({
  stepCountDeltaRequest: ['stepCountDeltaId'],
  stepCountDeltaAllRequest: ['options'],
  stepCountDeltaUpdateRequest: ['stepCountDelta'],
  stepCountDeltaDeleteRequest: ['stepCountDeltaId'],

  stepCountDeltaSuccess: ['stepCountDelta'],
  stepCountDeltaAllSuccess: ['stepCountDeltaList', 'headers'],
  stepCountDeltaUpdateSuccess: ['stepCountDelta'],
  stepCountDeltaDeleteSuccess: [],

  stepCountDeltaFailure: ['error'],
  stepCountDeltaAllFailure: ['error'],
  stepCountDeltaUpdateFailure: ['error'],
  stepCountDeltaDeleteFailure: ['error'],

  stepCountDeltaReset: [],
});

export const StepCountDeltaTypes = Types;
export default Creators;

/* ------------- Initial State ------------- */

export const INITIAL_STATE = Immutable({
  fetchingOne: false,
  fetchingAll: false,
  updating: false,
  deleting: false,
  updateSuccess: false,
  stepCountDelta: { id: undefined },
  stepCountDeltaList: [],
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
    stepCountDelta: INITIAL_STATE.stepCountDelta,
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
  const { stepCountDelta } = action;
  return state.merge({
    fetchingOne: false,
    errorOne: null,
    stepCountDelta,
  });
};
// successful api lookup for all entities
export const allSuccess = (state, action) => {
  const { stepCountDeltaList, headers } = action;
  const links = parseHeaderForLinks(headers.link);
  return state.merge({
    fetchingAll: false,
    errorAll: null,
    links,
    totalItems: parseInt(headers['x-total-count'], 10),
    stepCountDeltaList: loadMoreDataWhenScrolled(state.stepCountDeltaList, stepCountDeltaList, links),
  });
};
// successful api update
export const updateSuccess = (state, action) => {
  const { stepCountDelta } = action;
  return state.merge({
    updateSuccess: true,
    updating: false,
    errorUpdating: null,
    stepCountDelta,
  });
};
// successful api delete
export const deleteSuccess = (state) => {
  return state.merge({
    deleting: false,
    errorDeleting: null,
    stepCountDelta: INITIAL_STATE.stepCountDelta,
  });
};

// Something went wrong fetching a single entity.
export const failure = (state, action) => {
  const { error } = action;
  return state.merge({
    fetchingOne: false,
    errorOne: error,
    stepCountDelta: INITIAL_STATE.stepCountDelta,
  });
};
// Something went wrong fetching all entities.
export const allFailure = (state, action) => {
  const { error } = action;
  return state.merge({
    fetchingAll: false,
    errorAll: error,
    stepCountDeltaList: [],
  });
};
// Something went wrong updating.
export const updateFailure = (state, action) => {
  const { error } = action;
  return state.merge({
    updateSuccess: false,
    updating: false,
    errorUpdating: error,
    stepCountDelta: state.stepCountDelta,
  });
};
// Something went wrong deleting.
export const deleteFailure = (state, action) => {
  const { error } = action;
  return state.merge({
    deleting: false,
    errorDeleting: error,
    stepCountDelta: state.stepCountDelta,
  });
};

export const reset = (state) => INITIAL_STATE;

/* ------------- Hookup Reducers To Types ------------- */

export const reducer = createReducer(INITIAL_STATE, {
  [Types.STEP_COUNT_DELTA_REQUEST]: request,
  [Types.STEP_COUNT_DELTA_ALL_REQUEST]: allRequest,
  [Types.STEP_COUNT_DELTA_UPDATE_REQUEST]: updateRequest,
  [Types.STEP_COUNT_DELTA_DELETE_REQUEST]: deleteRequest,

  [Types.STEP_COUNT_DELTA_SUCCESS]: success,
  [Types.STEP_COUNT_DELTA_ALL_SUCCESS]: allSuccess,
  [Types.STEP_COUNT_DELTA_UPDATE_SUCCESS]: updateSuccess,
  [Types.STEP_COUNT_DELTA_DELETE_SUCCESS]: deleteSuccess,

  [Types.STEP_COUNT_DELTA_FAILURE]: failure,
  [Types.STEP_COUNT_DELTA_ALL_FAILURE]: allFailure,
  [Types.STEP_COUNT_DELTA_UPDATE_FAILURE]: updateFailure,
  [Types.STEP_COUNT_DELTA_DELETE_FAILURE]: deleteFailure,
  [Types.STEP_COUNT_DELTA_RESET]: reset,
});
