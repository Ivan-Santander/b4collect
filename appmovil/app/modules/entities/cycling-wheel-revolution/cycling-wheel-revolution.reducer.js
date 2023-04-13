import { createReducer, createActions } from 'reduxsauce';
import Immutable from 'seamless-immutable';
import { loadMoreDataWhenScrolled } from '../../../shared/util/pagination-utils';
import { parseHeaderForLinks } from '../../../shared/util/url-utils';

/* ------------- Types and Action Creators ------------- */

const { Types, Creators } = createActions({
  cyclingWheelRevolutionRequest: ['cyclingWheelRevolutionId'],
  cyclingWheelRevolutionAllRequest: ['options'],
  cyclingWheelRevolutionUpdateRequest: ['cyclingWheelRevolution'],
  cyclingWheelRevolutionDeleteRequest: ['cyclingWheelRevolutionId'],

  cyclingWheelRevolutionSuccess: ['cyclingWheelRevolution'],
  cyclingWheelRevolutionAllSuccess: ['cyclingWheelRevolutionList', 'headers'],
  cyclingWheelRevolutionUpdateSuccess: ['cyclingWheelRevolution'],
  cyclingWheelRevolutionDeleteSuccess: [],

  cyclingWheelRevolutionFailure: ['error'],
  cyclingWheelRevolutionAllFailure: ['error'],
  cyclingWheelRevolutionUpdateFailure: ['error'],
  cyclingWheelRevolutionDeleteFailure: ['error'],

  cyclingWheelRevolutionReset: [],
});

export const CyclingWheelRevolutionTypes = Types;
export default Creators;

/* ------------- Initial State ------------- */

export const INITIAL_STATE = Immutable({
  fetchingOne: false,
  fetchingAll: false,
  updating: false,
  deleting: false,
  updateSuccess: false,
  cyclingWheelRevolution: { id: undefined },
  cyclingWheelRevolutionList: [],
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
    cyclingWheelRevolution: INITIAL_STATE.cyclingWheelRevolution,
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
  const { cyclingWheelRevolution } = action;
  return state.merge({
    fetchingOne: false,
    errorOne: null,
    cyclingWheelRevolution,
  });
};
// successful api lookup for all entities
export const allSuccess = (state, action) => {
  const { cyclingWheelRevolutionList, headers } = action;
  const links = parseHeaderForLinks(headers.link);
  return state.merge({
    fetchingAll: false,
    errorAll: null,
    links,
    totalItems: parseInt(headers['x-total-count'], 10),
    cyclingWheelRevolutionList: loadMoreDataWhenScrolled(state.cyclingWheelRevolutionList, cyclingWheelRevolutionList, links),
  });
};
// successful api update
export const updateSuccess = (state, action) => {
  const { cyclingWheelRevolution } = action;
  return state.merge({
    updateSuccess: true,
    updating: false,
    errorUpdating: null,
    cyclingWheelRevolution,
  });
};
// successful api delete
export const deleteSuccess = (state) => {
  return state.merge({
    deleting: false,
    errorDeleting: null,
    cyclingWheelRevolution: INITIAL_STATE.cyclingWheelRevolution,
  });
};

// Something went wrong fetching a single entity.
export const failure = (state, action) => {
  const { error } = action;
  return state.merge({
    fetchingOne: false,
    errorOne: error,
    cyclingWheelRevolution: INITIAL_STATE.cyclingWheelRevolution,
  });
};
// Something went wrong fetching all entities.
export const allFailure = (state, action) => {
  const { error } = action;
  return state.merge({
    fetchingAll: false,
    errorAll: error,
    cyclingWheelRevolutionList: [],
  });
};
// Something went wrong updating.
export const updateFailure = (state, action) => {
  const { error } = action;
  return state.merge({
    updateSuccess: false,
    updating: false,
    errorUpdating: error,
    cyclingWheelRevolution: state.cyclingWheelRevolution,
  });
};
// Something went wrong deleting.
export const deleteFailure = (state, action) => {
  const { error } = action;
  return state.merge({
    deleting: false,
    errorDeleting: error,
    cyclingWheelRevolution: state.cyclingWheelRevolution,
  });
};

export const reset = (state) => INITIAL_STATE;

/* ------------- Hookup Reducers To Types ------------- */

export const reducer = createReducer(INITIAL_STATE, {
  [Types.CYCLING_WHEEL_REVOLUTION_REQUEST]: request,
  [Types.CYCLING_WHEEL_REVOLUTION_ALL_REQUEST]: allRequest,
  [Types.CYCLING_WHEEL_REVOLUTION_UPDATE_REQUEST]: updateRequest,
  [Types.CYCLING_WHEEL_REVOLUTION_DELETE_REQUEST]: deleteRequest,

  [Types.CYCLING_WHEEL_REVOLUTION_SUCCESS]: success,
  [Types.CYCLING_WHEEL_REVOLUTION_ALL_SUCCESS]: allSuccess,
  [Types.CYCLING_WHEEL_REVOLUTION_UPDATE_SUCCESS]: updateSuccess,
  [Types.CYCLING_WHEEL_REVOLUTION_DELETE_SUCCESS]: deleteSuccess,

  [Types.CYCLING_WHEEL_REVOLUTION_FAILURE]: failure,
  [Types.CYCLING_WHEEL_REVOLUTION_ALL_FAILURE]: allFailure,
  [Types.CYCLING_WHEEL_REVOLUTION_UPDATE_FAILURE]: updateFailure,
  [Types.CYCLING_WHEEL_REVOLUTION_DELETE_FAILURE]: deleteFailure,
  [Types.CYCLING_WHEEL_REVOLUTION_RESET]: reset,
});
