import { createReducer, createActions } from 'reduxsauce';
import Immutable from 'seamless-immutable';
import { loadMoreDataWhenScrolled } from '../../../shared/util/pagination-utils';
import { parseHeaderForLinks } from '../../../shared/util/url-utils';

/* ------------- Types and Action Creators ------------- */

const { Types, Creators } = createActions({
  sleepSegmentRequest: ['sleepSegmentId'],
  sleepSegmentAllRequest: ['options'],
  sleepSegmentUpdateRequest: ['sleepSegment'],
  sleepSegmentDeleteRequest: ['sleepSegmentId'],

  sleepSegmentSuccess: ['sleepSegment'],
  sleepSegmentAllSuccess: ['sleepSegmentList', 'headers'],
  sleepSegmentUpdateSuccess: ['sleepSegment'],
  sleepSegmentDeleteSuccess: [],

  sleepSegmentFailure: ['error'],
  sleepSegmentAllFailure: ['error'],
  sleepSegmentUpdateFailure: ['error'],
  sleepSegmentDeleteFailure: ['error'],

  sleepSegmentReset: [],
});

export const SleepSegmentTypes = Types;
export default Creators;

/* ------------- Initial State ------------- */

export const INITIAL_STATE = Immutable({
  fetchingOne: false,
  fetchingAll: false,
  updating: false,
  deleting: false,
  updateSuccess: false,
  sleepSegment: { id: undefined },
  sleepSegmentList: [],
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
    sleepSegment: INITIAL_STATE.sleepSegment,
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
  const { sleepSegment } = action;
  return state.merge({
    fetchingOne: false,
    errorOne: null,
    sleepSegment,
  });
};
// successful api lookup for all entities
export const allSuccess = (state, action) => {
  const { sleepSegmentList, headers } = action;
  const links = parseHeaderForLinks(headers.link);
  return state.merge({
    fetchingAll: false,
    errorAll: null,
    links,
    totalItems: parseInt(headers['x-total-count'], 10),
    sleepSegmentList: loadMoreDataWhenScrolled(state.sleepSegmentList, sleepSegmentList, links),
  });
};
// successful api update
export const updateSuccess = (state, action) => {
  const { sleepSegment } = action;
  return state.merge({
    updateSuccess: true,
    updating: false,
    errorUpdating: null,
    sleepSegment,
  });
};
// successful api delete
export const deleteSuccess = (state) => {
  return state.merge({
    deleting: false,
    errorDeleting: null,
    sleepSegment: INITIAL_STATE.sleepSegment,
  });
};

// Something went wrong fetching a single entity.
export const failure = (state, action) => {
  const { error } = action;
  return state.merge({
    fetchingOne: false,
    errorOne: error,
    sleepSegment: INITIAL_STATE.sleepSegment,
  });
};
// Something went wrong fetching all entities.
export const allFailure = (state, action) => {
  const { error } = action;
  return state.merge({
    fetchingAll: false,
    errorAll: error,
    sleepSegmentList: [],
  });
};
// Something went wrong updating.
export const updateFailure = (state, action) => {
  const { error } = action;
  return state.merge({
    updateSuccess: false,
    updating: false,
    errorUpdating: error,
    sleepSegment: state.sleepSegment,
  });
};
// Something went wrong deleting.
export const deleteFailure = (state, action) => {
  const { error } = action;
  return state.merge({
    deleting: false,
    errorDeleting: error,
    sleepSegment: state.sleepSegment,
  });
};

export const reset = (state) => INITIAL_STATE;

/* ------------- Hookup Reducers To Types ------------- */

export const reducer = createReducer(INITIAL_STATE, {
  [Types.SLEEP_SEGMENT_REQUEST]: request,
  [Types.SLEEP_SEGMENT_ALL_REQUEST]: allRequest,
  [Types.SLEEP_SEGMENT_UPDATE_REQUEST]: updateRequest,
  [Types.SLEEP_SEGMENT_DELETE_REQUEST]: deleteRequest,

  [Types.SLEEP_SEGMENT_SUCCESS]: success,
  [Types.SLEEP_SEGMENT_ALL_SUCCESS]: allSuccess,
  [Types.SLEEP_SEGMENT_UPDATE_SUCCESS]: updateSuccess,
  [Types.SLEEP_SEGMENT_DELETE_SUCCESS]: deleteSuccess,

  [Types.SLEEP_SEGMENT_FAILURE]: failure,
  [Types.SLEEP_SEGMENT_ALL_FAILURE]: allFailure,
  [Types.SLEEP_SEGMENT_UPDATE_FAILURE]: updateFailure,
  [Types.SLEEP_SEGMENT_DELETE_FAILURE]: deleteFailure,
  [Types.SLEEP_SEGMENT_RESET]: reset,
});
