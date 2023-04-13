import { createReducer, createActions } from 'reduxsauce';
import Immutable from 'seamless-immutable';
import { loadMoreDataWhenScrolled } from '../../../shared/util/pagination-utils';
import { parseHeaderForLinks } from '../../../shared/util/url-utils';

/* ------------- Types and Action Creators ------------- */

const { Types, Creators } = createActions({
  activityExerciseRequest: ['activityExerciseId'],
  activityExerciseAllRequest: ['options'],
  activityExerciseUpdateRequest: ['activityExercise'],
  activityExerciseDeleteRequest: ['activityExerciseId'],

  activityExerciseSuccess: ['activityExercise'],
  activityExerciseAllSuccess: ['activityExerciseList', 'headers'],
  activityExerciseUpdateSuccess: ['activityExercise'],
  activityExerciseDeleteSuccess: [],

  activityExerciseFailure: ['error'],
  activityExerciseAllFailure: ['error'],
  activityExerciseUpdateFailure: ['error'],
  activityExerciseDeleteFailure: ['error'],

  activityExerciseReset: [],
});

export const ActivityExerciseTypes = Types;
export default Creators;

/* ------------- Initial State ------------- */

export const INITIAL_STATE = Immutable({
  fetchingOne: false,
  fetchingAll: false,
  updating: false,
  deleting: false,
  updateSuccess: false,
  activityExercise: { id: undefined },
  activityExerciseList: [],
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
    activityExercise: INITIAL_STATE.activityExercise,
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
  const { activityExercise } = action;
  return state.merge({
    fetchingOne: false,
    errorOne: null,
    activityExercise,
  });
};
// successful api lookup for all entities
export const allSuccess = (state, action) => {
  const { activityExerciseList, headers } = action;
  const links = parseHeaderForLinks(headers.link);
  return state.merge({
    fetchingAll: false,
    errorAll: null,
    links,
    totalItems: parseInt(headers['x-total-count'], 10),
    activityExerciseList: loadMoreDataWhenScrolled(state.activityExerciseList, activityExerciseList, links),
  });
};
// successful api update
export const updateSuccess = (state, action) => {
  const { activityExercise } = action;
  return state.merge({
    updateSuccess: true,
    updating: false,
    errorUpdating: null,
    activityExercise,
  });
};
// successful api delete
export const deleteSuccess = (state) => {
  return state.merge({
    deleting: false,
    errorDeleting: null,
    activityExercise: INITIAL_STATE.activityExercise,
  });
};

// Something went wrong fetching a single entity.
export const failure = (state, action) => {
  const { error } = action;
  return state.merge({
    fetchingOne: false,
    errorOne: error,
    activityExercise: INITIAL_STATE.activityExercise,
  });
};
// Something went wrong fetching all entities.
export const allFailure = (state, action) => {
  const { error } = action;
  return state.merge({
    fetchingAll: false,
    errorAll: error,
    activityExerciseList: [],
  });
};
// Something went wrong updating.
export const updateFailure = (state, action) => {
  const { error } = action;
  return state.merge({
    updateSuccess: false,
    updating: false,
    errorUpdating: error,
    activityExercise: state.activityExercise,
  });
};
// Something went wrong deleting.
export const deleteFailure = (state, action) => {
  const { error } = action;
  return state.merge({
    deleting: false,
    errorDeleting: error,
    activityExercise: state.activityExercise,
  });
};

export const reset = (state) => INITIAL_STATE;

/* ------------- Hookup Reducers To Types ------------- */

export const reducer = createReducer(INITIAL_STATE, {
  [Types.ACTIVITY_EXERCISE_REQUEST]: request,
  [Types.ACTIVITY_EXERCISE_ALL_REQUEST]: allRequest,
  [Types.ACTIVITY_EXERCISE_UPDATE_REQUEST]: updateRequest,
  [Types.ACTIVITY_EXERCISE_DELETE_REQUEST]: deleteRequest,

  [Types.ACTIVITY_EXERCISE_SUCCESS]: success,
  [Types.ACTIVITY_EXERCISE_ALL_SUCCESS]: allSuccess,
  [Types.ACTIVITY_EXERCISE_UPDATE_SUCCESS]: updateSuccess,
  [Types.ACTIVITY_EXERCISE_DELETE_SUCCESS]: deleteSuccess,

  [Types.ACTIVITY_EXERCISE_FAILURE]: failure,
  [Types.ACTIVITY_EXERCISE_ALL_FAILURE]: allFailure,
  [Types.ACTIVITY_EXERCISE_UPDATE_FAILURE]: updateFailure,
  [Types.ACTIVITY_EXERCISE_DELETE_FAILURE]: deleteFailure,
  [Types.ACTIVITY_EXERCISE_RESET]: reset,
});
