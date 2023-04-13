import { createReducer, createActions } from 'reduxsauce';
import Immutable from 'seamless-immutable';
import { loadMoreDataWhenScrolled } from '../../../shared/util/pagination-utils';
import { parseHeaderForLinks } from '../../../shared/util/url-utils';

/* ------------- Types and Action Creators ------------- */

const { Types, Creators } = createActions({
  nutritionRequest: ['nutritionId'],
  nutritionAllRequest: ['options'],
  nutritionUpdateRequest: ['nutrition'],
  nutritionDeleteRequest: ['nutritionId'],

  nutritionSuccess: ['nutrition'],
  nutritionAllSuccess: ['nutritionList', 'headers'],
  nutritionUpdateSuccess: ['nutrition'],
  nutritionDeleteSuccess: [],

  nutritionFailure: ['error'],
  nutritionAllFailure: ['error'],
  nutritionUpdateFailure: ['error'],
  nutritionDeleteFailure: ['error'],

  nutritionReset: [],
});

export const NutritionTypes = Types;
export default Creators;

/* ------------- Initial State ------------- */

export const INITIAL_STATE = Immutable({
  fetchingOne: false,
  fetchingAll: false,
  updating: false,
  deleting: false,
  updateSuccess: false,
  nutrition: { id: undefined },
  nutritionList: [],
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
    nutrition: INITIAL_STATE.nutrition,
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
  const { nutrition } = action;
  return state.merge({
    fetchingOne: false,
    errorOne: null,
    nutrition,
  });
};
// successful api lookup for all entities
export const allSuccess = (state, action) => {
  const { nutritionList, headers } = action;
  const links = parseHeaderForLinks(headers.link);
  return state.merge({
    fetchingAll: false,
    errorAll: null,
    links,
    totalItems: parseInt(headers['x-total-count'], 10),
    nutritionList: loadMoreDataWhenScrolled(state.nutritionList, nutritionList, links),
  });
};
// successful api update
export const updateSuccess = (state, action) => {
  const { nutrition } = action;
  return state.merge({
    updateSuccess: true,
    updating: false,
    errorUpdating: null,
    nutrition,
  });
};
// successful api delete
export const deleteSuccess = (state) => {
  return state.merge({
    deleting: false,
    errorDeleting: null,
    nutrition: INITIAL_STATE.nutrition,
  });
};

// Something went wrong fetching a single entity.
export const failure = (state, action) => {
  const { error } = action;
  return state.merge({
    fetchingOne: false,
    errorOne: error,
    nutrition: INITIAL_STATE.nutrition,
  });
};
// Something went wrong fetching all entities.
export const allFailure = (state, action) => {
  const { error } = action;
  return state.merge({
    fetchingAll: false,
    errorAll: error,
    nutritionList: [],
  });
};
// Something went wrong updating.
export const updateFailure = (state, action) => {
  const { error } = action;
  return state.merge({
    updateSuccess: false,
    updating: false,
    errorUpdating: error,
    nutrition: state.nutrition,
  });
};
// Something went wrong deleting.
export const deleteFailure = (state, action) => {
  const { error } = action;
  return state.merge({
    deleting: false,
    errorDeleting: error,
    nutrition: state.nutrition,
  });
};

export const reset = (state) => INITIAL_STATE;

/* ------------- Hookup Reducers To Types ------------- */

export const reducer = createReducer(INITIAL_STATE, {
  [Types.NUTRITION_REQUEST]: request,
  [Types.NUTRITION_ALL_REQUEST]: allRequest,
  [Types.NUTRITION_UPDATE_REQUEST]: updateRequest,
  [Types.NUTRITION_DELETE_REQUEST]: deleteRequest,

  [Types.NUTRITION_SUCCESS]: success,
  [Types.NUTRITION_ALL_SUCCESS]: allSuccess,
  [Types.NUTRITION_UPDATE_SUCCESS]: updateSuccess,
  [Types.NUTRITION_DELETE_SUCCESS]: deleteSuccess,

  [Types.NUTRITION_FAILURE]: failure,
  [Types.NUTRITION_ALL_FAILURE]: allFailure,
  [Types.NUTRITION_UPDATE_FAILURE]: updateFailure,
  [Types.NUTRITION_DELETE_FAILURE]: deleteFailure,
  [Types.NUTRITION_RESET]: reset,
});
