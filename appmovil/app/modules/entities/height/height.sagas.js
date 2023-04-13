import { call, put } from 'redux-saga/effects';
import { callApi } from '../../../shared/sagas/call-api.saga';
import HeightActions from './height.reducer';
import { convertDateTimeFromServer } from '../../../shared/util/date-transforms';

function* getHeight(api, action) {
  const { heightId } = action;
  // make the call to the api
  const apiCall = call(api.getHeight, heightId);
  const response = yield call(callApi, apiCall);

  // success?
  if (response.ok) {
    response.data = mapDateFields(response.data);
    yield put(HeightActions.heightSuccess(response.data));
  } else {
    yield put(HeightActions.heightFailure(response.data));
  }
}

function* getAllHeights(api, action) {
  const { options } = action;
  // make the call to the api
  const apiCall = call(api.getAllHeights, options);
  const response = yield call(callApi, apiCall);

  // success?
  if (response.ok) {
    yield put(HeightActions.heightAllSuccess(response.data, response.headers));
  } else {
    yield put(HeightActions.heightAllFailure(response.data));
  }
}

function* updateHeight(api, action) {
  const { height } = action;
  // make the call to the api
  const idIsNotNull = !(height.id === null || height.id === undefined);
  const apiCall = call(idIsNotNull ? api.updateHeight : api.createHeight, height);
  const response = yield call(callApi, apiCall);

  // success?
  if (response.ok) {
    response.data = mapDateFields(response.data);
    yield put(HeightActions.heightUpdateSuccess(response.data));
  } else {
    yield put(HeightActions.heightUpdateFailure(response.data));
  }
}

function* deleteHeight(api, action) {
  const { heightId } = action;
  // make the call to the api
  const apiCall = call(api.deleteHeight, heightId);
  const response = yield call(callApi, apiCall);

  // success?
  if (response.ok) {
    yield put(HeightActions.heightDeleteSuccess());
  } else {
    yield put(HeightActions.heightDeleteFailure(response.data));
  }
}
function mapDateFields(data) {
  data.endTime = convertDateTimeFromServer(data.endTime);
  return data;
}

export default {
  getAllHeights,
  getHeight,
  deleteHeight,
  updateHeight,
};
