import { createReducer, createActions } from 'reduxsauce';
import Immutable from 'seamless-immutable';
import { loadMoreDataWhenScrolled } from '../../../shared/util/pagination-utils';
import { parseHeaderForLinks } from '../../../shared/util/url-utils';

/* ------------- Types and Action Creators ------------- */

const { Types, Creators } = createActions({
  caloriesBmrSummaryRequest: ['caloriesBmrSummaryId'],
  caloriesBmrSummaryAllRequest: ['options'],
  caloriesBmrSummaryUpdateRequest: ['caloriesBmrSummary'],
  caloriesBmrSummaryDeleteRequest: ['caloriesBmrSummaryId'],

  caloriesBmrSummarySuccess: ['caloriesBmrSummary'],
  caloriesBmrSummaryAllSuccess: ['caloriesBmrSummaryList', 'headers'],
  caloriesBmrSummaryUpdateSuccess: ['caloriesBmrSummary'],
  caloriesBmrSummaryDeleteSuccess: [],

  caloriesBmrSummaryFailure: ['error'],
  caloriesBmrSummaryAllFailure: ['error'],
  caloriesBmrSummaryUpdateFailure: ['error'],
  caloriesBmrSummaryDeleteFailure: ['error'],

  caloriesBmrSummaryReset: [],
});

export const CaloriesBmrSummaryTypes = Types;
export default Creators;

/* ------------- Initial State ------------- */

export const INITIAL_STATE = Immutable({
  fetchingOne: false,
  fetchingAll: false,
  updating: false,
  deleting: false,
  updateSuccess: false,
  caloriesBmrSummary: { id: undefined },
  caloriesBmrSummaryList: [],
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
    caloriesBmrSummary: INITIAL_STATE.caloriesBmrSummary,
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
  const { caloriesBmrSummary } = action;
  return state.merge({
    fetchingOne: false,
    errorOne: null,
    caloriesBmrSummary,
  });
};
// successful api lookup for all entities
export const allSuccess = (state, action) => {
  const { caloriesBmrSummaryList, headers } = action;
  const links = parseHeaderForLinks(headers.link);
  return state.merge({
    fetchingAll: false,
    errorAll: null,
    links,
    totalItems: parseInt(headers['x-total-count'], 10),
    caloriesBmrSummaryList: loadMoreDataWhenScrolled(state.caloriesBmrSummaryList, caloriesBmrSummaryList, links),
  });
};
// successful api update
export const updateSuccess = (state, action) => {
  const { caloriesBmrSummary } = action;
  return state.merge({
    updateSuccess: true,
    updating: false,
    errorUpdating: null,
    caloriesBmrSummary,
  });
};
// successful api delete
export const deleteSuccess = (state) => {
  return state.merge({
    deleting: false,
    errorDeleting: null,
    caloriesBmrSummary: INITIAL_STATE.caloriesBmrSummary,
  });
};

// Something went wrong fetching a single entity.
export const failure = (state, action) => {
  const { error } = action;
  return state.merge({
    fetchingOne: false,
    errorOne: error,
    caloriesBmrSummary: INITIAL_STATE.caloriesBmrSummary,
  });
};
// Something went wrong fetching all entities.
export const allFailure = (state, action) => {
  const { error } = action;
  return state.merge({
    fetchingAll: false,
    errorAll: error,
    caloriesBmrSummaryList: [],
  });
};
// Something went wrong updating.
export const updateFailure = (state, action) => {
  const { error } = action;
  return state.merge({
    updateSuccess: false,
    updating: false,
    errorUpdating: error,
    caloriesBmrSummary: state.caloriesBmrSummary,
  });
};
// Something went wrong deleting.
export const deleteFailure = (state, action) => {
  const { error } = action;
  return state.merge({
    deleting: false,
    errorDeleting: error,
    caloriesBmrSummary: state.caloriesBmrSummary,
  });
};

export const reset = (state) => INITIAL_STATE;

/* ------------- Hookup Reducers To Types ------------- */

export const reducer = createReducer(INITIAL_STATE, {
  [Types.CALORIES_BMR_SUMMARY_REQUEST]: request,
  [Types.CALORIES_BMR_SUMMARY_ALL_REQUEST]: allRequest,
  [Types.CALORIES_BMR_SUMMARY_UPDATE_REQUEST]: updateRequest,
  [Types.CALORIES_BMR_SUMMARY_DELETE_REQUEST]: deleteRequest,

  [Types.CALORIES_BMR_SUMMARY_SUCCESS]: success,
  [Types.CALORIES_BMR_SUMMARY_ALL_SUCCESS]: allSuccess,
  [Types.CALORIES_BMR_SUMMARY_UPDATE_SUCCESS]: updateSuccess,
  [Types.CALORIES_BMR_SUMMARY_DELETE_SUCCESS]: deleteSuccess,

  [Types.CALORIES_BMR_SUMMARY_FAILURE]: failure,
  [Types.CALORIES_BMR_SUMMARY_ALL_FAILURE]: allFailure,
  [Types.CALORIES_BMR_SUMMARY_UPDATE_FAILURE]: updateFailure,
  [Types.CALORIES_BMR_SUMMARY_DELETE_FAILURE]: deleteFailure,
  [Types.CALORIES_BMR_SUMMARY_RESET]: reset,
});
