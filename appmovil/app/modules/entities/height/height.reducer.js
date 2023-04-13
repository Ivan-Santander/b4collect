import { createReducer, createActions } from 'reduxsauce';
import Immutable from 'seamless-immutable';
import { loadMoreDataWhenScrolled } from '../../../shared/util/pagination-utils';
import { parseHeaderForLinks } from '../../../shared/util/url-utils';

/* ------------- Types and Action Creators ------------- */

const { Types, Creators } = createActions({
  heightRequest: ['heightId'],
  heightAllRequest: ['options'],
  heightUpdateRequest: ['height'],
  heightDeleteRequest: ['heightId'],

  heightSuccess: ['height'],
  heightAllSuccess: ['heightList', 'headers'],
  heightUpdateSuccess: ['height'],
  heightDeleteSuccess: [],

  heightFailure: ['error'],
  heightAllFailure: ['error'],
  heightUpdateFailure: ['error'],
  heightDeleteFailure: ['error'],

  heightReset: [],
});

export const HeightTypes = Types;
export default Creators;

/* ------------- Initial State ------------- */

export const INITIAL_STATE = Immutable({
  fetchingOne: false,
  fetchingAll: false,
  updating: false,
  deleting: false,
  updateSuccess: false,
  height: { id: undefined },
  heightList: [],
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
    height: INITIAL_STATE.height,
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
  const { height } = action;
  return state.merge({
    fetchingOne: false,
    errorOne: null,
    height,
  });
};
// successful api lookup for all entities
export const allSuccess = (state, action) => {
  const { heightList, headers } = action;
  const links = parseHeaderForLinks(headers.link);
  return state.merge({
    fetchingAll: false,
    errorAll: null,
    links,
    totalItems: parseInt(headers['x-total-count'], 10),
    heightList: loadMoreDataWhenScrolled(state.heightList, heightList, links),
  });
};
// successful api update
export const updateSuccess = (state, action) => {
  const { height } = action;
  return state.merge({
    updateSuccess: true,
    updating: false,
    errorUpdating: null,
    height,
  });
};
// successful api delete
export const deleteSuccess = (state) => {
  return state.merge({
    deleting: false,
    errorDeleting: null,
    height: INITIAL_STATE.height,
  });
};

// Something went wrong fetching a single entity.
export const failure = (state, action) => {
  const { error } = action;
  return state.merge({
    fetchingOne: false,
    errorOne: error,
    height: INITIAL_STATE.height,
  });
};
// Something went wrong fetching all entities.
export const allFailure = (state, action) => {
  const { error } = action;
  return state.merge({
    fetchingAll: false,
    errorAll: error,
    heightList: [],
  });
};
// Something went wrong updating.
export const updateFailure = (state, action) => {
  const { error } = action;
  return state.merge({
    updateSuccess: false,
    updating: false,
    errorUpdating: error,
    height: state.height,
  });
};
// Something went wrong deleting.
export const deleteFailure = (state, action) => {
  const { error } = action;
  return state.merge({
    deleting: false,
    errorDeleting: error,
    height: state.height,
  });
};

export const reset = (state) => INITIAL_STATE;

/* ------------- Hookup Reducers To Types ------------- */

export const reducer = createReducer(INITIAL_STATE, {
  [Types.HEIGHT_REQUEST]: request,
  [Types.HEIGHT_ALL_REQUEST]: allRequest,
  [Types.HEIGHT_UPDATE_REQUEST]: updateRequest,
  [Types.HEIGHT_DELETE_REQUEST]: deleteRequest,

  [Types.HEIGHT_SUCCESS]: success,
  [Types.HEIGHT_ALL_SUCCESS]: allSuccess,
  [Types.HEIGHT_UPDATE_SUCCESS]: updateSuccess,
  [Types.HEIGHT_DELETE_SUCCESS]: deleteSuccess,

  [Types.HEIGHT_FAILURE]: failure,
  [Types.HEIGHT_ALL_FAILURE]: allFailure,
  [Types.HEIGHT_UPDATE_FAILURE]: updateFailure,
  [Types.HEIGHT_DELETE_FAILURE]: deleteFailure,
  [Types.HEIGHT_RESET]: reset,
});
