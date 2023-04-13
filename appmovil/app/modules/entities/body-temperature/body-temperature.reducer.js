import { createReducer, createActions } from 'reduxsauce';
import Immutable from 'seamless-immutable';
import { loadMoreDataWhenScrolled } from '../../../shared/util/pagination-utils';
import { parseHeaderForLinks } from '../../../shared/util/url-utils';

/* ------------- Types and Action Creators ------------- */

const { Types, Creators } = createActions({
  bodyTemperatureRequest: ['bodyTemperatureId'],
  bodyTemperatureAllRequest: ['options'],
  bodyTemperatureUpdateRequest: ['bodyTemperature'],
  bodyTemperatureDeleteRequest: ['bodyTemperatureId'],

  bodyTemperatureSuccess: ['bodyTemperature'],
  bodyTemperatureAllSuccess: ['bodyTemperatureList', 'headers'],
  bodyTemperatureUpdateSuccess: ['bodyTemperature'],
  bodyTemperatureDeleteSuccess: [],

  bodyTemperatureFailure: ['error'],
  bodyTemperatureAllFailure: ['error'],
  bodyTemperatureUpdateFailure: ['error'],
  bodyTemperatureDeleteFailure: ['error'],

  bodyTemperatureReset: [],
});

export const BodyTemperatureTypes = Types;
export default Creators;

/* ------------- Initial State ------------- */

export const INITIAL_STATE = Immutable({
  fetchingOne: false,
  fetchingAll: false,
  updating: false,
  deleting: false,
  updateSuccess: false,
  bodyTemperature: { id: undefined },
  bodyTemperatureList: [],
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
    bodyTemperature: INITIAL_STATE.bodyTemperature,
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
  const { bodyTemperature } = action;
  return state.merge({
    fetchingOne: false,
    errorOne: null,
    bodyTemperature,
  });
};
// successful api lookup for all entities
export const allSuccess = (state, action) => {
  const { bodyTemperatureList, headers } = action;
  const links = parseHeaderForLinks(headers.link);
  return state.merge({
    fetchingAll: false,
    errorAll: null,
    links,
    totalItems: parseInt(headers['x-total-count'], 10),
    bodyTemperatureList: loadMoreDataWhenScrolled(state.bodyTemperatureList, bodyTemperatureList, links),
  });
};
// successful api update
export const updateSuccess = (state, action) => {
  const { bodyTemperature } = action;
  return state.merge({
    updateSuccess: true,
    updating: false,
    errorUpdating: null,
    bodyTemperature,
  });
};
// successful api delete
export const deleteSuccess = (state) => {
  return state.merge({
    deleting: false,
    errorDeleting: null,
    bodyTemperature: INITIAL_STATE.bodyTemperature,
  });
};

// Something went wrong fetching a single entity.
export const failure = (state, action) => {
  const { error } = action;
  return state.merge({
    fetchingOne: false,
    errorOne: error,
    bodyTemperature: INITIAL_STATE.bodyTemperature,
  });
};
// Something went wrong fetching all entities.
export const allFailure = (state, action) => {
  const { error } = action;
  return state.merge({
    fetchingAll: false,
    errorAll: error,
    bodyTemperatureList: [],
  });
};
// Something went wrong updating.
export const updateFailure = (state, action) => {
  const { error } = action;
  return state.merge({
    updateSuccess: false,
    updating: false,
    errorUpdating: error,
    bodyTemperature: state.bodyTemperature,
  });
};
// Something went wrong deleting.
export const deleteFailure = (state, action) => {
  const { error } = action;
  return state.merge({
    deleting: false,
    errorDeleting: error,
    bodyTemperature: state.bodyTemperature,
  });
};

export const reset = (state) => INITIAL_STATE;

/* ------------- Hookup Reducers To Types ------------- */

export const reducer = createReducer(INITIAL_STATE, {
  [Types.BODY_TEMPERATURE_REQUEST]: request,
  [Types.BODY_TEMPERATURE_ALL_REQUEST]: allRequest,
  [Types.BODY_TEMPERATURE_UPDATE_REQUEST]: updateRequest,
  [Types.BODY_TEMPERATURE_DELETE_REQUEST]: deleteRequest,

  [Types.BODY_TEMPERATURE_SUCCESS]: success,
  [Types.BODY_TEMPERATURE_ALL_SUCCESS]: allSuccess,
  [Types.BODY_TEMPERATURE_UPDATE_SUCCESS]: updateSuccess,
  [Types.BODY_TEMPERATURE_DELETE_SUCCESS]: deleteSuccess,

  [Types.BODY_TEMPERATURE_FAILURE]: failure,
  [Types.BODY_TEMPERATURE_ALL_FAILURE]: allFailure,
  [Types.BODY_TEMPERATURE_UPDATE_FAILURE]: updateFailure,
  [Types.BODY_TEMPERATURE_DELETE_FAILURE]: deleteFailure,
  [Types.BODY_TEMPERATURE_RESET]: reset,
});
