import { createReducer, createActions } from 'reduxsauce';
import Immutable from 'seamless-immutable';
import { loadMoreDataWhenScrolled } from '../../../shared/util/pagination-utils';
import { parseHeaderForLinks } from '../../../shared/util/url-utils';

/* ------------- Types and Action Creators ------------- */

const { Types, Creators } = createActions({
  caloriesBMRRequest: ['caloriesBMRId'],
  caloriesBMRAllRequest: ['options'],
  caloriesBMRUpdateRequest: ['caloriesBMR'],
  caloriesBMRDeleteRequest: ['caloriesBMRId'],

  caloriesBMRSuccess: ['caloriesBMR'],
  caloriesBMRAllSuccess: ['caloriesBMRList', 'headers'],
  caloriesBMRUpdateSuccess: ['caloriesBMR'],
  caloriesBMRDeleteSuccess: [],

  caloriesBMRFailure: ['error'],
  caloriesBMRAllFailure: ['error'],
  caloriesBMRUpdateFailure: ['error'],
  caloriesBMRDeleteFailure: ['error'],

  caloriesBMRReset: [],
});

export const CaloriesBMRTypes = Types;
export default Creators;

/* ------------- Initial State ------------- */

export const INITIAL_STATE = Immutable({
  fetchingOne: false,
  fetchingAll: false,
  updating: false,
  deleting: false,
  updateSuccess: false,
  caloriesBMR: { id: undefined },
  caloriesBMRList: [],
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
    caloriesBMR: INITIAL_STATE.caloriesBMR,
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
  const { caloriesBMR } = action;
  return state.merge({
    fetchingOne: false,
    errorOne: null,
    caloriesBMR,
  });
};
// successful api lookup for all entities
export const allSuccess = (state, action) => {
  const { caloriesBMRList, headers } = action;
  const links = parseHeaderForLinks(headers.link);
  return state.merge({
    fetchingAll: false,
    errorAll: null,
    links,
    totalItems: parseInt(headers['x-total-count'], 10),
    caloriesBMRList: loadMoreDataWhenScrolled(state.caloriesBMRList, caloriesBMRList, links),
  });
};
// successful api update
export const updateSuccess = (state, action) => {
  const { caloriesBMR } = action;
  return state.merge({
    updateSuccess: true,
    updating: false,
    errorUpdating: null,
    caloriesBMR,
  });
};
// successful api delete
export const deleteSuccess = (state) => {
  return state.merge({
    deleting: false,
    errorDeleting: null,
    caloriesBMR: INITIAL_STATE.caloriesBMR,
  });
};

// Something went wrong fetching a single entity.
export const failure = (state, action) => {
  const { error } = action;
  return state.merge({
    fetchingOne: false,
    errorOne: error,
    caloriesBMR: INITIAL_STATE.caloriesBMR,
  });
};
// Something went wrong fetching all entities.
export const allFailure = (state, action) => {
  const { error } = action;
  return state.merge({
    fetchingAll: false,
    errorAll: error,
    caloriesBMRList: [],
  });
};
// Something went wrong updating.
export const updateFailure = (state, action) => {
  const { error } = action;
  return state.merge({
    updateSuccess: false,
    updating: false,
    errorUpdating: error,
    caloriesBMR: state.caloriesBMR,
  });
};
// Something went wrong deleting.
export const deleteFailure = (state, action) => {
  const { error } = action;
  return state.merge({
    deleting: false,
    errorDeleting: error,
    caloriesBMR: state.caloriesBMR,
  });
};

export const reset = (state) => INITIAL_STATE;

/* ------------- Hookup Reducers To Types ------------- */

export const reducer = createReducer(INITIAL_STATE, {
  [Types.CALORIES_BMR_REQUEST]: request,
  [Types.CALORIES_BMR_ALL_REQUEST]: allRequest,
  [Types.CALORIES_BMR_UPDATE_REQUEST]: updateRequest,
  [Types.CALORIES_BMR_DELETE_REQUEST]: deleteRequest,

  [Types.CALORIES_BMR_SUCCESS]: success,
  [Types.CALORIES_BMR_ALL_SUCCESS]: allSuccess,
  [Types.CALORIES_BMR_UPDATE_SUCCESS]: updateSuccess,
  [Types.CALORIES_BMR_DELETE_SUCCESS]: deleteSuccess,

  [Types.CALORIES_BMR_FAILURE]: failure,
  [Types.CALORIES_BMR_ALL_FAILURE]: allFailure,
  [Types.CALORIES_BMR_UPDATE_FAILURE]: updateFailure,
  [Types.CALORIES_BMR_DELETE_FAILURE]: deleteFailure,
  [Types.CALORIES_BMR_RESET]: reset,
});
