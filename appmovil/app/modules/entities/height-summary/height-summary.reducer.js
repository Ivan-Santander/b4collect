import { createReducer, createActions } from 'reduxsauce';
import Immutable from 'seamless-immutable';
import { loadMoreDataWhenScrolled } from '../../../shared/util/pagination-utils';
import { parseHeaderForLinks } from '../../../shared/util/url-utils';

/* ------------- Types and Action Creators ------------- */

const { Types, Creators } = createActions({
  heightSummaryRequest: ['heightSummaryId'],
  heightSummaryAllRequest: ['options'],
  heightSummaryUpdateRequest: ['heightSummary'],
  heightSummaryDeleteRequest: ['heightSummaryId'],

  heightSummarySuccess: ['heightSummary'],
  heightSummaryAllSuccess: ['heightSummaryList', 'headers'],
  heightSummaryUpdateSuccess: ['heightSummary'],
  heightSummaryDeleteSuccess: [],

  heightSummaryFailure: ['error'],
  heightSummaryAllFailure: ['error'],
  heightSummaryUpdateFailure: ['error'],
  heightSummaryDeleteFailure: ['error'],

  heightSummaryReset: [],
});

export const HeightSummaryTypes = Types;
export default Creators;

/* ------------- Initial State ------------- */

export const INITIAL_STATE = Immutable({
  fetchingOne: false,
  fetchingAll: false,
  updating: false,
  deleting: false,
  updateSuccess: false,
  heightSummary: { id: undefined },
  heightSummaryList: [],
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
    heightSummary: INITIAL_STATE.heightSummary,
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
  const { heightSummary } = action;
  return state.merge({
    fetchingOne: false,
    errorOne: null,
    heightSummary,
  });
};
// successful api lookup for all entities
export const allSuccess = (state, action) => {
  const { heightSummaryList, headers } = action;
  const links = parseHeaderForLinks(headers.link);
  return state.merge({
    fetchingAll: false,
    errorAll: null,
    links,
    totalItems: parseInt(headers['x-total-count'], 10),
    heightSummaryList: loadMoreDataWhenScrolled(state.heightSummaryList, heightSummaryList, links),
  });
};
// successful api update
export const updateSuccess = (state, action) => {
  const { heightSummary } = action;
  return state.merge({
    updateSuccess: true,
    updating: false,
    errorUpdating: null,
    heightSummary,
  });
};
// successful api delete
export const deleteSuccess = (state) => {
  return state.merge({
    deleting: false,
    errorDeleting: null,
    heightSummary: INITIAL_STATE.heightSummary,
  });
};

// Something went wrong fetching a single entity.
export const failure = (state, action) => {
  const { error } = action;
  return state.merge({
    fetchingOne: false,
    errorOne: error,
    heightSummary: INITIAL_STATE.heightSummary,
  });
};
// Something went wrong fetching all entities.
export const allFailure = (state, action) => {
  const { error } = action;
  return state.merge({
    fetchingAll: false,
    errorAll: error,
    heightSummaryList: [],
  });
};
// Something went wrong updating.
export const updateFailure = (state, action) => {
  const { error } = action;
  return state.merge({
    updateSuccess: false,
    updating: false,
    errorUpdating: error,
    heightSummary: state.heightSummary,
  });
};
// Something went wrong deleting.
export const deleteFailure = (state, action) => {
  const { error } = action;
  return state.merge({
    deleting: false,
    errorDeleting: error,
    heightSummary: state.heightSummary,
  });
};

export const reset = (state) => INITIAL_STATE;

/* ------------- Hookup Reducers To Types ------------- */

export const reducer = createReducer(INITIAL_STATE, {
  [Types.HEIGHT_SUMMARY_REQUEST]: request,
  [Types.HEIGHT_SUMMARY_ALL_REQUEST]: allRequest,
  [Types.HEIGHT_SUMMARY_UPDATE_REQUEST]: updateRequest,
  [Types.HEIGHT_SUMMARY_DELETE_REQUEST]: deleteRequest,

  [Types.HEIGHT_SUMMARY_SUCCESS]: success,
  [Types.HEIGHT_SUMMARY_ALL_SUCCESS]: allSuccess,
  [Types.HEIGHT_SUMMARY_UPDATE_SUCCESS]: updateSuccess,
  [Types.HEIGHT_SUMMARY_DELETE_SUCCESS]: deleteSuccess,

  [Types.HEIGHT_SUMMARY_FAILURE]: failure,
  [Types.HEIGHT_SUMMARY_ALL_FAILURE]: allFailure,
  [Types.HEIGHT_SUMMARY_UPDATE_FAILURE]: updateFailure,
  [Types.HEIGHT_SUMMARY_DELETE_FAILURE]: deleteFailure,
  [Types.HEIGHT_SUMMARY_RESET]: reset,
});
