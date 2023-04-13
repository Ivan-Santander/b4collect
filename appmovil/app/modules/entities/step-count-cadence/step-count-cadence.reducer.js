import { createReducer, createActions } from 'reduxsauce';
import Immutable from 'seamless-immutable';
import { loadMoreDataWhenScrolled } from '../../../shared/util/pagination-utils';
import { parseHeaderForLinks } from '../../../shared/util/url-utils';

/* ------------- Types and Action Creators ------------- */

const { Types, Creators } = createActions({
  stepCountCadenceRequest: ['stepCountCadenceId'],
  stepCountCadenceAllRequest: ['options'],
  stepCountCadenceUpdateRequest: ['stepCountCadence'],
  stepCountCadenceDeleteRequest: ['stepCountCadenceId'],

  stepCountCadenceSuccess: ['stepCountCadence'],
  stepCountCadenceAllSuccess: ['stepCountCadenceList', 'headers'],
  stepCountCadenceUpdateSuccess: ['stepCountCadence'],
  stepCountCadenceDeleteSuccess: [],

  stepCountCadenceFailure: ['error'],
  stepCountCadenceAllFailure: ['error'],
  stepCountCadenceUpdateFailure: ['error'],
  stepCountCadenceDeleteFailure: ['error'],

  stepCountCadenceReset: [],
});

export const StepCountCadenceTypes = Types;
export default Creators;

/* ------------- Initial State ------------- */

export const INITIAL_STATE = Immutable({
  fetchingOne: false,
  fetchingAll: false,
  updating: false,
  deleting: false,
  updateSuccess: false,
  stepCountCadence: { id: undefined },
  stepCountCadenceList: [],
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
    stepCountCadence: INITIAL_STATE.stepCountCadence,
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
  const { stepCountCadence } = action;
  return state.merge({
    fetchingOne: false,
    errorOne: null,
    stepCountCadence,
  });
};
// successful api lookup for all entities
export const allSuccess = (state, action) => {
  const { stepCountCadenceList, headers } = action;
  const links = parseHeaderForLinks(headers.link);
  return state.merge({
    fetchingAll: false,
    errorAll: null,
    links,
    totalItems: parseInt(headers['x-total-count'], 10),
    stepCountCadenceList: loadMoreDataWhenScrolled(state.stepCountCadenceList, stepCountCadenceList, links),
  });
};
// successful api update
export const updateSuccess = (state, action) => {
  const { stepCountCadence } = action;
  return state.merge({
    updateSuccess: true,
    updating: false,
    errorUpdating: null,
    stepCountCadence,
  });
};
// successful api delete
export const deleteSuccess = (state) => {
  return state.merge({
    deleting: false,
    errorDeleting: null,
    stepCountCadence: INITIAL_STATE.stepCountCadence,
  });
};

// Something went wrong fetching a single entity.
export const failure = (state, action) => {
  const { error } = action;
  return state.merge({
    fetchingOne: false,
    errorOne: error,
    stepCountCadence: INITIAL_STATE.stepCountCadence,
  });
};
// Something went wrong fetching all entities.
export const allFailure = (state, action) => {
  const { error } = action;
  return state.merge({
    fetchingAll: false,
    errorAll: error,
    stepCountCadenceList: [],
  });
};
// Something went wrong updating.
export const updateFailure = (state, action) => {
  const { error } = action;
  return state.merge({
    updateSuccess: false,
    updating: false,
    errorUpdating: error,
    stepCountCadence: state.stepCountCadence,
  });
};
// Something went wrong deleting.
export const deleteFailure = (state, action) => {
  const { error } = action;
  return state.merge({
    deleting: false,
    errorDeleting: error,
    stepCountCadence: state.stepCountCadence,
  });
};

export const reset = (state) => INITIAL_STATE;

/* ------------- Hookup Reducers To Types ------------- */

export const reducer = createReducer(INITIAL_STATE, {
  [Types.STEP_COUNT_CADENCE_REQUEST]: request,
  [Types.STEP_COUNT_CADENCE_ALL_REQUEST]: allRequest,
  [Types.STEP_COUNT_CADENCE_UPDATE_REQUEST]: updateRequest,
  [Types.STEP_COUNT_CADENCE_DELETE_REQUEST]: deleteRequest,

  [Types.STEP_COUNT_CADENCE_SUCCESS]: success,
  [Types.STEP_COUNT_CADENCE_ALL_SUCCESS]: allSuccess,
  [Types.STEP_COUNT_CADENCE_UPDATE_SUCCESS]: updateSuccess,
  [Types.STEP_COUNT_CADENCE_DELETE_SUCCESS]: deleteSuccess,

  [Types.STEP_COUNT_CADENCE_FAILURE]: failure,
  [Types.STEP_COUNT_CADENCE_ALL_FAILURE]: allFailure,
  [Types.STEP_COUNT_CADENCE_UPDATE_FAILURE]: updateFailure,
  [Types.STEP_COUNT_CADENCE_DELETE_FAILURE]: deleteFailure,
  [Types.STEP_COUNT_CADENCE_RESET]: reset,
});
