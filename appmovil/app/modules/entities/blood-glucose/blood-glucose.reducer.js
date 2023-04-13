import { createReducer, createActions } from 'reduxsauce';
import Immutable from 'seamless-immutable';
import { loadMoreDataWhenScrolled } from '../../../shared/util/pagination-utils';
import { parseHeaderForLinks } from '../../../shared/util/url-utils';

/* ------------- Types and Action Creators ------------- */

const { Types, Creators } = createActions({
  bloodGlucoseRequest: ['bloodGlucoseId'],
  bloodGlucoseAllRequest: ['options'],
  bloodGlucoseUpdateRequest: ['bloodGlucose'],
  bloodGlucoseDeleteRequest: ['bloodGlucoseId'],

  bloodGlucoseSuccess: ['bloodGlucose'],
  bloodGlucoseAllSuccess: ['bloodGlucoseList', 'headers'],
  bloodGlucoseUpdateSuccess: ['bloodGlucose'],
  bloodGlucoseDeleteSuccess: [],

  bloodGlucoseFailure: ['error'],
  bloodGlucoseAllFailure: ['error'],
  bloodGlucoseUpdateFailure: ['error'],
  bloodGlucoseDeleteFailure: ['error'],

  bloodGlucoseReset: [],
});

export const BloodGlucoseTypes = Types;
export default Creators;

/* ------------- Initial State ------------- */

export const INITIAL_STATE = Immutable({
  fetchingOne: false,
  fetchingAll: false,
  updating: false,
  deleting: false,
  updateSuccess: false,
  bloodGlucose: { id: undefined },
  bloodGlucoseList: [],
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
    bloodGlucose: INITIAL_STATE.bloodGlucose,
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
  const { bloodGlucose } = action;
  return state.merge({
    fetchingOne: false,
    errorOne: null,
    bloodGlucose,
  });
};
// successful api lookup for all entities
export const allSuccess = (state, action) => {
  const { bloodGlucoseList, headers } = action;
  const links = parseHeaderForLinks(headers.link);
  return state.merge({
    fetchingAll: false,
    errorAll: null,
    links,
    totalItems: parseInt(headers['x-total-count'], 10),
    bloodGlucoseList: loadMoreDataWhenScrolled(state.bloodGlucoseList, bloodGlucoseList, links),
  });
};
// successful api update
export const updateSuccess = (state, action) => {
  const { bloodGlucose } = action;
  return state.merge({
    updateSuccess: true,
    updating: false,
    errorUpdating: null,
    bloodGlucose,
  });
};
// successful api delete
export const deleteSuccess = (state) => {
  return state.merge({
    deleting: false,
    errorDeleting: null,
    bloodGlucose: INITIAL_STATE.bloodGlucose,
  });
};

// Something went wrong fetching a single entity.
export const failure = (state, action) => {
  const { error } = action;
  return state.merge({
    fetchingOne: false,
    errorOne: error,
    bloodGlucose: INITIAL_STATE.bloodGlucose,
  });
};
// Something went wrong fetching all entities.
export const allFailure = (state, action) => {
  const { error } = action;
  return state.merge({
    fetchingAll: false,
    errorAll: error,
    bloodGlucoseList: [],
  });
};
// Something went wrong updating.
export const updateFailure = (state, action) => {
  const { error } = action;
  return state.merge({
    updateSuccess: false,
    updating: false,
    errorUpdating: error,
    bloodGlucose: state.bloodGlucose,
  });
};
// Something went wrong deleting.
export const deleteFailure = (state, action) => {
  const { error } = action;
  return state.merge({
    deleting: false,
    errorDeleting: error,
    bloodGlucose: state.bloodGlucose,
  });
};

export const reset = (state) => INITIAL_STATE;

/* ------------- Hookup Reducers To Types ------------- */

export const reducer = createReducer(INITIAL_STATE, {
  [Types.BLOOD_GLUCOSE_REQUEST]: request,
  [Types.BLOOD_GLUCOSE_ALL_REQUEST]: allRequest,
  [Types.BLOOD_GLUCOSE_UPDATE_REQUEST]: updateRequest,
  [Types.BLOOD_GLUCOSE_DELETE_REQUEST]: deleteRequest,

  [Types.BLOOD_GLUCOSE_SUCCESS]: success,
  [Types.BLOOD_GLUCOSE_ALL_SUCCESS]: allSuccess,
  [Types.BLOOD_GLUCOSE_UPDATE_SUCCESS]: updateSuccess,
  [Types.BLOOD_GLUCOSE_DELETE_SUCCESS]: deleteSuccess,

  [Types.BLOOD_GLUCOSE_FAILURE]: failure,
  [Types.BLOOD_GLUCOSE_ALL_FAILURE]: allFailure,
  [Types.BLOOD_GLUCOSE_UPDATE_FAILURE]: updateFailure,
  [Types.BLOOD_GLUCOSE_DELETE_FAILURE]: deleteFailure,
  [Types.BLOOD_GLUCOSE_RESET]: reset,
});
