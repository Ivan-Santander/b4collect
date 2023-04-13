import { createReducer, createActions } from 'reduxsauce';
import Immutable from 'seamless-immutable';
import { loadMoreDataWhenScrolled } from '../../../shared/util/pagination-utils';
import { parseHeaderForLinks } from '../../../shared/util/url-utils';

/* ------------- Types and Action Creators ------------- */

const { Types, Creators } = createActions({
  oxygenSaturationRequest: ['oxygenSaturationId'],
  oxygenSaturationAllRequest: ['options'],
  oxygenSaturationUpdateRequest: ['oxygenSaturation'],
  oxygenSaturationDeleteRequest: ['oxygenSaturationId'],

  oxygenSaturationSuccess: ['oxygenSaturation'],
  oxygenSaturationAllSuccess: ['oxygenSaturationList', 'headers'],
  oxygenSaturationUpdateSuccess: ['oxygenSaturation'],
  oxygenSaturationDeleteSuccess: [],

  oxygenSaturationFailure: ['error'],
  oxygenSaturationAllFailure: ['error'],
  oxygenSaturationUpdateFailure: ['error'],
  oxygenSaturationDeleteFailure: ['error'],

  oxygenSaturationReset: [],
});

export const OxygenSaturationTypes = Types;
export default Creators;

/* ------------- Initial State ------------- */

export const INITIAL_STATE = Immutable({
  fetchingOne: false,
  fetchingAll: false,
  updating: false,
  deleting: false,
  updateSuccess: false,
  oxygenSaturation: { id: undefined },
  oxygenSaturationList: [],
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
    oxygenSaturation: INITIAL_STATE.oxygenSaturation,
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
  const { oxygenSaturation } = action;
  return state.merge({
    fetchingOne: false,
    errorOne: null,
    oxygenSaturation,
  });
};
// successful api lookup for all entities
export const allSuccess = (state, action) => {
  const { oxygenSaturationList, headers } = action;
  const links = parseHeaderForLinks(headers.link);
  return state.merge({
    fetchingAll: false,
    errorAll: null,
    links,
    totalItems: parseInt(headers['x-total-count'], 10),
    oxygenSaturationList: loadMoreDataWhenScrolled(state.oxygenSaturationList, oxygenSaturationList, links),
  });
};
// successful api update
export const updateSuccess = (state, action) => {
  const { oxygenSaturation } = action;
  return state.merge({
    updateSuccess: true,
    updating: false,
    errorUpdating: null,
    oxygenSaturation,
  });
};
// successful api delete
export const deleteSuccess = (state) => {
  return state.merge({
    deleting: false,
    errorDeleting: null,
    oxygenSaturation: INITIAL_STATE.oxygenSaturation,
  });
};

// Something went wrong fetching a single entity.
export const failure = (state, action) => {
  const { error } = action;
  return state.merge({
    fetchingOne: false,
    errorOne: error,
    oxygenSaturation: INITIAL_STATE.oxygenSaturation,
  });
};
// Something went wrong fetching all entities.
export const allFailure = (state, action) => {
  const { error } = action;
  return state.merge({
    fetchingAll: false,
    errorAll: error,
    oxygenSaturationList: [],
  });
};
// Something went wrong updating.
export const updateFailure = (state, action) => {
  const { error } = action;
  return state.merge({
    updateSuccess: false,
    updating: false,
    errorUpdating: error,
    oxygenSaturation: state.oxygenSaturation,
  });
};
// Something went wrong deleting.
export const deleteFailure = (state, action) => {
  const { error } = action;
  return state.merge({
    deleting: false,
    errorDeleting: error,
    oxygenSaturation: state.oxygenSaturation,
  });
};

export const reset = (state) => INITIAL_STATE;

/* ------------- Hookup Reducers To Types ------------- */

export const reducer = createReducer(INITIAL_STATE, {
  [Types.OXYGEN_SATURATION_REQUEST]: request,
  [Types.OXYGEN_SATURATION_ALL_REQUEST]: allRequest,
  [Types.OXYGEN_SATURATION_UPDATE_REQUEST]: updateRequest,
  [Types.OXYGEN_SATURATION_DELETE_REQUEST]: deleteRequest,

  [Types.OXYGEN_SATURATION_SUCCESS]: success,
  [Types.OXYGEN_SATURATION_ALL_SUCCESS]: allSuccess,
  [Types.OXYGEN_SATURATION_UPDATE_SUCCESS]: updateSuccess,
  [Types.OXYGEN_SATURATION_DELETE_SUCCESS]: deleteSuccess,

  [Types.OXYGEN_SATURATION_FAILURE]: failure,
  [Types.OXYGEN_SATURATION_ALL_FAILURE]: allFailure,
  [Types.OXYGEN_SATURATION_UPDATE_FAILURE]: updateFailure,
  [Types.OXYGEN_SATURATION_DELETE_FAILURE]: deleteFailure,
  [Types.OXYGEN_SATURATION_RESET]: reset,
});
