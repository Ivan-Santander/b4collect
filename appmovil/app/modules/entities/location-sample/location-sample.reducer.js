import { createReducer, createActions } from 'reduxsauce';
import Immutable from 'seamless-immutable';
import { loadMoreDataWhenScrolled } from '../../../shared/util/pagination-utils';
import { parseHeaderForLinks } from '../../../shared/util/url-utils';

/* ------------- Types and Action Creators ------------- */

const { Types, Creators } = createActions({
  locationSampleRequest: ['locationSampleId'],
  locationSampleAllRequest: ['options'],
  locationSampleUpdateRequest: ['locationSample'],
  locationSampleDeleteRequest: ['locationSampleId'],

  locationSampleSuccess: ['locationSample'],
  locationSampleAllSuccess: ['locationSampleList', 'headers'],
  locationSampleUpdateSuccess: ['locationSample'],
  locationSampleDeleteSuccess: [],

  locationSampleFailure: ['error'],
  locationSampleAllFailure: ['error'],
  locationSampleUpdateFailure: ['error'],
  locationSampleDeleteFailure: ['error'],

  locationSampleReset: [],
});

export const LocationSampleTypes = Types;
export default Creators;

/* ------------- Initial State ------------- */

export const INITIAL_STATE = Immutable({
  fetchingOne: false,
  fetchingAll: false,
  updating: false,
  deleting: false,
  updateSuccess: false,
  locationSample: { id: undefined },
  locationSampleList: [],
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
    locationSample: INITIAL_STATE.locationSample,
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
  const { locationSample } = action;
  return state.merge({
    fetchingOne: false,
    errorOne: null,
    locationSample,
  });
};
// successful api lookup for all entities
export const allSuccess = (state, action) => {
  const { locationSampleList, headers } = action;
  const links = parseHeaderForLinks(headers.link);
  return state.merge({
    fetchingAll: false,
    errorAll: null,
    links,
    totalItems: parseInt(headers['x-total-count'], 10),
    locationSampleList: loadMoreDataWhenScrolled(state.locationSampleList, locationSampleList, links),
  });
};
// successful api update
export const updateSuccess = (state, action) => {
  const { locationSample } = action;
  return state.merge({
    updateSuccess: true,
    updating: false,
    errorUpdating: null,
    locationSample,
  });
};
// successful api delete
export const deleteSuccess = (state) => {
  return state.merge({
    deleting: false,
    errorDeleting: null,
    locationSample: INITIAL_STATE.locationSample,
  });
};

// Something went wrong fetching a single entity.
export const failure = (state, action) => {
  const { error } = action;
  return state.merge({
    fetchingOne: false,
    errorOne: error,
    locationSample: INITIAL_STATE.locationSample,
  });
};
// Something went wrong fetching all entities.
export const allFailure = (state, action) => {
  const { error } = action;
  return state.merge({
    fetchingAll: false,
    errorAll: error,
    locationSampleList: [],
  });
};
// Something went wrong updating.
export const updateFailure = (state, action) => {
  const { error } = action;
  return state.merge({
    updateSuccess: false,
    updating: false,
    errorUpdating: error,
    locationSample: state.locationSample,
  });
};
// Something went wrong deleting.
export const deleteFailure = (state, action) => {
  const { error } = action;
  return state.merge({
    deleting: false,
    errorDeleting: error,
    locationSample: state.locationSample,
  });
};

export const reset = (state) => INITIAL_STATE;

/* ------------- Hookup Reducers To Types ------------- */

export const reducer = createReducer(INITIAL_STATE, {
  [Types.LOCATION_SAMPLE_REQUEST]: request,
  [Types.LOCATION_SAMPLE_ALL_REQUEST]: allRequest,
  [Types.LOCATION_SAMPLE_UPDATE_REQUEST]: updateRequest,
  [Types.LOCATION_SAMPLE_DELETE_REQUEST]: deleteRequest,

  [Types.LOCATION_SAMPLE_SUCCESS]: success,
  [Types.LOCATION_SAMPLE_ALL_SUCCESS]: allSuccess,
  [Types.LOCATION_SAMPLE_UPDATE_SUCCESS]: updateSuccess,
  [Types.LOCATION_SAMPLE_DELETE_SUCCESS]: deleteSuccess,

  [Types.LOCATION_SAMPLE_FAILURE]: failure,
  [Types.LOCATION_SAMPLE_ALL_FAILURE]: allFailure,
  [Types.LOCATION_SAMPLE_UPDATE_FAILURE]: updateFailure,
  [Types.LOCATION_SAMPLE_DELETE_FAILURE]: deleteFailure,
  [Types.LOCATION_SAMPLE_RESET]: reset,
});
