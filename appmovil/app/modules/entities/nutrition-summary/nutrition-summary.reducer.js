import { createReducer, createActions } from 'reduxsauce';
import Immutable from 'seamless-immutable';
import { loadMoreDataWhenScrolled } from '../../../shared/util/pagination-utils';
import { parseHeaderForLinks } from '../../../shared/util/url-utils';

/* ------------- Types and Action Creators ------------- */

const { Types, Creators } = createActions({
  nutritionSummaryRequest: ['nutritionSummaryId'],
  nutritionSummaryAllRequest: ['options'],
  nutritionSummaryUpdateRequest: ['nutritionSummary'],
  nutritionSummaryDeleteRequest: ['nutritionSummaryId'],

  nutritionSummarySuccess: ['nutritionSummary'],
  nutritionSummaryAllSuccess: ['nutritionSummaryList', 'headers'],
  nutritionSummaryUpdateSuccess: ['nutritionSummary'],
  nutritionSummaryDeleteSuccess: [],

  nutritionSummaryFailure: ['error'],
  nutritionSummaryAllFailure: ['error'],
  nutritionSummaryUpdateFailure: ['error'],
  nutritionSummaryDeleteFailure: ['error'],

  nutritionSummaryReset: [],
});

export const NutritionSummaryTypes = Types;
export default Creators;

/* ------------- Initial State ------------- */

export const INITIAL_STATE = Immutable({
  fetchingOne: false,
  fetchingAll: false,
  updating: false,
  deleting: false,
  updateSuccess: false,
  nutritionSummary: { id: undefined },
  nutritionSummaryList: [],
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
    nutritionSummary: INITIAL_STATE.nutritionSummary,
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
  const { nutritionSummary } = action;
  return state.merge({
    fetchingOne: false,
    errorOne: null,
    nutritionSummary,
  });
};
// successful api lookup for all entities
export const allSuccess = (state, action) => {
  const { nutritionSummaryList, headers } = action;
  const links = parseHeaderForLinks(headers.link);
  return state.merge({
    fetchingAll: false,
    errorAll: null,
    links,
    totalItems: parseInt(headers['x-total-count'], 10),
    nutritionSummaryList: loadMoreDataWhenScrolled(state.nutritionSummaryList, nutritionSummaryList, links),
  });
};
// successful api update
export const updateSuccess = (state, action) => {
  const { nutritionSummary } = action;
  return state.merge({
    updateSuccess: true,
    updating: false,
    errorUpdating: null,
    nutritionSummary,
  });
};
// successful api delete
export const deleteSuccess = (state) => {
  return state.merge({
    deleting: false,
    errorDeleting: null,
    nutritionSummary: INITIAL_STATE.nutritionSummary,
  });
};

// Something went wrong fetching a single entity.
export const failure = (state, action) => {
  const { error } = action;
  return state.merge({
    fetchingOne: false,
    errorOne: error,
    nutritionSummary: INITIAL_STATE.nutritionSummary,
  });
};
// Something went wrong fetching all entities.
export const allFailure = (state, action) => {
  const { error } = action;
  return state.merge({
    fetchingAll: false,
    errorAll: error,
    nutritionSummaryList: [],
  });
};
// Something went wrong updating.
export const updateFailure = (state, action) => {
  const { error } = action;
  return state.merge({
    updateSuccess: false,
    updating: false,
    errorUpdating: error,
    nutritionSummary: state.nutritionSummary,
  });
};
// Something went wrong deleting.
export const deleteFailure = (state, action) => {
  const { error } = action;
  return state.merge({
    deleting: false,
    errorDeleting: error,
    nutritionSummary: state.nutritionSummary,
  });
};

export const reset = (state) => INITIAL_STATE;

/* ------------- Hookup Reducers To Types ------------- */

export const reducer = createReducer(INITIAL_STATE, {
  [Types.NUTRITION_SUMMARY_REQUEST]: request,
  [Types.NUTRITION_SUMMARY_ALL_REQUEST]: allRequest,
  [Types.NUTRITION_SUMMARY_UPDATE_REQUEST]: updateRequest,
  [Types.NUTRITION_SUMMARY_DELETE_REQUEST]: deleteRequest,

  [Types.NUTRITION_SUMMARY_SUCCESS]: success,
  [Types.NUTRITION_SUMMARY_ALL_SUCCESS]: allSuccess,
  [Types.NUTRITION_SUMMARY_UPDATE_SUCCESS]: updateSuccess,
  [Types.NUTRITION_SUMMARY_DELETE_SUCCESS]: deleteSuccess,

  [Types.NUTRITION_SUMMARY_FAILURE]: failure,
  [Types.NUTRITION_SUMMARY_ALL_FAILURE]: allFailure,
  [Types.NUTRITION_SUMMARY_UPDATE_FAILURE]: updateFailure,
  [Types.NUTRITION_SUMMARY_DELETE_FAILURE]: deleteFailure,
  [Types.NUTRITION_SUMMARY_RESET]: reset,
});
