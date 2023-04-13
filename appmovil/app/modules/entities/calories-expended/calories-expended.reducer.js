import { createReducer, createActions } from 'reduxsauce';
import Immutable from 'seamless-immutable';
import { loadMoreDataWhenScrolled } from '../../../shared/util/pagination-utils';
import { parseHeaderForLinks } from '../../../shared/util/url-utils';

/* ------------- Types and Action Creators ------------- */

const { Types, Creators } = createActions({
  caloriesExpendedRequest: ['caloriesExpendedId'],
  caloriesExpendedAllRequest: ['options'],
  caloriesExpendedUpdateRequest: ['caloriesExpended'],
  caloriesExpendedDeleteRequest: ['caloriesExpendedId'],

  caloriesExpendedSuccess: ['caloriesExpended'],
  caloriesExpendedAllSuccess: ['caloriesExpendedList', 'headers'],
  caloriesExpendedUpdateSuccess: ['caloriesExpended'],
  caloriesExpendedDeleteSuccess: [],

  caloriesExpendedFailure: ['error'],
  caloriesExpendedAllFailure: ['error'],
  caloriesExpendedUpdateFailure: ['error'],
  caloriesExpendedDeleteFailure: ['error'],

  caloriesExpendedReset: [],
});

export const CaloriesExpendedTypes = Types;
export default Creators;

/* ------------- Initial State ------------- */

export const INITIAL_STATE = Immutable({
  fetchingOne: false,
  fetchingAll: false,
  updating: false,
  deleting: false,
  updateSuccess: false,
  caloriesExpended: { id: undefined },
  caloriesExpendedList: [],
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
    caloriesExpended: INITIAL_STATE.caloriesExpended,
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
  const { caloriesExpended } = action;
  return state.merge({
    fetchingOne: false,
    errorOne: null,
    caloriesExpended,
  });
};
// successful api lookup for all entities
export const allSuccess = (state, action) => {
  const { caloriesExpendedList, headers } = action;
  const links = parseHeaderForLinks(headers.link);
  return state.merge({
    fetchingAll: false,
    errorAll: null,
    links,
    totalItems: parseInt(headers['x-total-count'], 10),
    caloriesExpendedList: loadMoreDataWhenScrolled(state.caloriesExpendedList, caloriesExpendedList, links),
  });
};
// successful api update
export const updateSuccess = (state, action) => {
  const { caloriesExpended } = action;
  return state.merge({
    updateSuccess: true,
    updating: false,
    errorUpdating: null,
    caloriesExpended,
  });
};
// successful api delete
export const deleteSuccess = (state) => {
  return state.merge({
    deleting: false,
    errorDeleting: null,
    caloriesExpended: INITIAL_STATE.caloriesExpended,
  });
};

// Something went wrong fetching a single entity.
export const failure = (state, action) => {
  const { error } = action;
  return state.merge({
    fetchingOne: false,
    errorOne: error,
    caloriesExpended: INITIAL_STATE.caloriesExpended,
  });
};
// Something went wrong fetching all entities.
export const allFailure = (state, action) => {
  const { error } = action;
  return state.merge({
    fetchingAll: false,
    errorAll: error,
    caloriesExpendedList: [],
  });
};
// Something went wrong updating.
export const updateFailure = (state, action) => {
  const { error } = action;
  return state.merge({
    updateSuccess: false,
    updating: false,
    errorUpdating: error,
    caloriesExpended: state.caloriesExpended,
  });
};
// Something went wrong deleting.
export const deleteFailure = (state, action) => {
  const { error } = action;
  return state.merge({
    deleting: false,
    errorDeleting: error,
    caloriesExpended: state.caloriesExpended,
  });
};

export const reset = (state) => INITIAL_STATE;

/* ------------- Hookup Reducers To Types ------------- */

export const reducer = createReducer(INITIAL_STATE, {
  [Types.CALORIES_EXPENDED_REQUEST]: request,
  [Types.CALORIES_EXPENDED_ALL_REQUEST]: allRequest,
  [Types.CALORIES_EXPENDED_UPDATE_REQUEST]: updateRequest,
  [Types.CALORIES_EXPENDED_DELETE_REQUEST]: deleteRequest,

  [Types.CALORIES_EXPENDED_SUCCESS]: success,
  [Types.CALORIES_EXPENDED_ALL_SUCCESS]: allSuccess,
  [Types.CALORIES_EXPENDED_UPDATE_SUCCESS]: updateSuccess,
  [Types.CALORIES_EXPENDED_DELETE_SUCCESS]: deleteSuccess,

  [Types.CALORIES_EXPENDED_FAILURE]: failure,
  [Types.CALORIES_EXPENDED_ALL_FAILURE]: allFailure,
  [Types.CALORIES_EXPENDED_UPDATE_FAILURE]: updateFailure,
  [Types.CALORIES_EXPENDED_DELETE_FAILURE]: deleteFailure,
  [Types.CALORIES_EXPENDED_RESET]: reset,
});
