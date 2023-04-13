import { call, put } from 'redux-saga/effects';
import { callApi } from '../../../shared/sagas/call-api.saga';
import HeartRateBpmActions from './heart-rate-bpm.reducer';
import { convertDateTimeFromServer } from '../../../shared/util/date-transforms';

function* getHeartRateBpm(api, action) {
  const { heartRateBpmId } = action;
  // make the call to the api
  const apiCall = call(api.getHeartRateBpm, heartRateBpmId);
  const response = yield call(callApi, apiCall);

  // success?
  if (response.ok) {
    response.data = mapDateFields(response.data);
    yield put(HeartRateBpmActions.heartRateBpmSuccess(response.data));
  } else {
    yield put(HeartRateBpmActions.heartRateBpmFailure(response.data));
  }
}

function* getAllHeartRateBpms(api, action) {
  const { options } = action;
  // make the call to the api
  const apiCall = call(api.getAllHeartRateBpms, options);
  const response = yield call(callApi, apiCall);

  // success?
  if (response.ok) {
    yield put(HeartRateBpmActions.heartRateBpmAllSuccess(response.data, response.headers));
  } else {
    yield put(HeartRateBpmActions.heartRateBpmAllFailure(response.data));
  }
}

function* updateHeartRateBpm(api, action) {
  const { heartRateBpm } = action;
  // make the call to the api
  const idIsNotNull = !(heartRateBpm.id === null || heartRateBpm.id === undefined);
  const apiCall = call(idIsNotNull ? api.updateHeartRateBpm : api.createHeartRateBpm, heartRateBpm);
  const response = yield call(callApi, apiCall);

  // success?
  if (response.ok) {
    response.data = mapDateFields(response.data);
    yield put(HeartRateBpmActions.heartRateBpmUpdateSuccess(response.data));
  } else {
    yield put(HeartRateBpmActions.heartRateBpmUpdateFailure(response.data));
  }
}

function* deleteHeartRateBpm(api, action) {
  const { heartRateBpmId } = action;
  // make the call to the api
  const apiCall = call(api.deleteHeartRateBpm, heartRateBpmId);
  const response = yield call(callApi, apiCall);

  // success?
  if (response.ok) {
    yield put(HeartRateBpmActions.heartRateBpmDeleteSuccess());
  } else {
    yield put(HeartRateBpmActions.heartRateBpmDeleteFailure(response.data));
  }
}
function mapDateFields(data) {
  data.endTime = convertDateTimeFromServer(data.endTime);
  return data;
}

export default {
  getAllHeartRateBpms,
  getHeartRateBpm,
  deleteHeartRateBpm,
  updateHeartRateBpm,
};
