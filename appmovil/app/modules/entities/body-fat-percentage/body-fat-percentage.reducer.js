import { createReducer, createActions } from 'reduxsauce';
import Immutable from 'seamless-immutable';
import { loadMoreDataWhenScrolled } from '../../../shared/util/pagination-utils';
import { parseHeaderForLinks } from '../../../shared/util/url-utils';

/* ------------- Types and Action Creators ------------- */

const { Types, Creators } = createActions({
  bodyFatPercentageRequest: ['bodyFatPercentageId'],
  bodyFatPercentageAllRequest: ['options'],
  bodyFatPercentageUpdateRequest: ['bodyFatPercentage'],
  bodyFatPercentageDeleteRequest: ['bodyFatPercentageId'],

  bodyFatPercentageSuccess: ['bodyFatPercentage'],
  bodyFatPercentageAllSuccess: ['bodyFatPercentageList', 'headers'],
  bodyFatPercentageUpdateSuccess: ['bodyFatPercentage'],
  bodyFatPercentageDeleteSuccess: [],

  bodyFatPercentageFailure: ['error'],
  bodyFatPercentageAllFailure: ['error'],
  bodyFatPercentageUpdateFailure: ['error'],
  bodyFatPercentageDeleteFailure: ['error'],

  bodyFatPercentageReset: [],
});

export const BodyFatPercentageTypes = Types;
export default Creators;

/* ------------- Initial State ------------- */

export const INITIAL_STATE = Immutable({
  fetchingOne: false,
  fetchingAll: false,
  updating: false,
  deleting: false,
  updateSuccess: false,
  bodyFatPercentage: { id: undefined },
  bodyFatPercentageList: [],
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
    bodyFatPercentage: INITIAL_STATE.bodyFatPercentage,
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
  const { bodyFatPercentage } = action;
  return state.merge({
    fetchingOne: false,
    errorOne: null,
    bodyFatPercentage,
  });
};
// successful api lookup for all entities
export const allSuccess = (state, action) => {
  const { bodyFatPercentageList, headers } = action;
  const links = parseHeaderForLinks(headers.link);
  return state.merge({
    fetchingAll: false,
    errorAll: null,
    links,
    totalItems: parseInt(headers['x-total-count'], 10),
    bodyFatPercentageList: loadMoreDataWhenScrolled(state.bodyFatPercentageList, bodyFatPercentageList, links),
  });
};
// successful api update
export const updateSuccess = (state, action) => {
  const { bodyFatPercentage } = action;
  return state.merge({
    updateSuccess: true,
    updating: false,
    errorUpdating: null,
    bodyFatPercentage,
  });
};
// successful api delete
export const deleteSuccess = (state) => {
  return state.merge({
    deleting: false,
    errorDeleting: null,
    bodyFatPercentage: INITIAL_STATE.bodyFatPercentage,
  });
};

// Something went wrong fetching a single entity.
export const failure = (state, action) => {
  const { error } = action;
  return state.merge({
    fetchingOne: false,
    errorOne: error,
    bodyFatPercentage: INITIAL_STATE.bodyFatPercentage,
  });
};
// Something went wrong fetching all entities.
export const allFailure = (state, action) => {
  const { error } = action;
  return state.merge({
    fetchingAll: false,
    errorAll: error,
    bodyFatPercentageList: [],
  });
};
// Something went wrong updating.
export const updateFailure = (state, action) => {
  const { error } = action;
  return state.merge({
    updateSuccess: false,
    updating: false,
    errorUpdating: error,
    bodyFatPercentage: state.bodyFatPercentage,
  });
};
// Something went wrong deleting.
export const deleteFailure = (state, action) => {
  const { error } = action;
  return state.merge({
    deleting: false,
    errorDeleting: error,
    bodyFatPercentage: state.bodyFatPercentage,
  });
};

export const reset = (state) => INITIAL_STATE;

/* ------------- Hookup Reducers To Types ------------- */

export const reducer = createReducer(INITIAL_STATE, {
  [Types.BODY_FAT_PERCENTAGE_REQUEST]: request,
  [Types.BODY_FAT_PERCENTAGE_ALL_REQUEST]: allRequest,
  [Types.BODY_FAT_PERCENTAGE_UPDATE_REQUEST]: updateRequest,
  [Types.BODY_FAT_PERCENTAGE_DELETE_REQUEST]: deleteRequest,

  [Types.BODY_FAT_PERCENTAGE_SUCCESS]: success,
  [Types.BODY_FAT_PERCENTAGE_ALL_SUCCESS]: allSuccess,
  [Types.BODY_FAT_PERCENTAGE_UPDATE_SUCCESS]: updateSuccess,
  [Types.BODY_FAT_PERCENTAGE_DELETE_SUCCESS]: deleteSuccess,

  [Types.BODY_FAT_PERCENTAGE_FAILURE]: failure,
  [Types.BODY_FAT_PERCENTAGE_ALL_FAILURE]: allFailure,
  [Types.BODY_FAT_PERCENTAGE_UPDATE_FAILURE]: updateFailure,
  [Types.BODY_FAT_PERCENTAGE_DELETE_FAILURE]: deleteFailure,
  [Types.BODY_FAT_PERCENTAGE_RESET]: reset,
});
