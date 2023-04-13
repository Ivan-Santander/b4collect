import { call, put } from 'redux-saga/effects';
import { callApi } from '../../../shared/sagas/call-api.saga';
import HeartMinutesActions from './heart-minutes.reducer';
import { convertDateTimeFromServer } from '../../../shared/util/date-transforms';

function* getHeartMinutes(api, action) {
  const { heartMinutesId } = action;
  // make the call to the api
  const apiCall = call(api.getHeartMinutes, heartMinutesId);
  const response = yield call(callApi, apiCall);

  // success?
  if (response.ok) {
    response.data = mapDateFields(response.data);
    yield put(HeartMinutesActions.heartMinutesSuccess(response.data));
  } else {
    yield put(HeartMinutesActions.heartMinutesFailure(response.data));
  }
}

function* getAllHeartMinutes(api, action) {
  const { options } = action;
  // make the call to the api
  const apiCall = call(api.getAllHeartMinutes, options);
  const response = yield call(callApi, apiCall);

  // success?
  if (response.ok) {
    yield put(HeartMinutesActions.heartMinutesAllSuccess(response.data, response.headers));
  } else {
    yield put(HeartMinutesActions.heartMinutesAllFailure(response.data));
  }
}

function* updateHeartMinutes(api, action) {
  const { heartMinutes } = action;
  // make the call to the api
  const idIsNotNull = !(heartMinutes.id === null || heartMinutes.id === undefined);
  const apiCall = call(idIsNotNull ? api.updateHeartMinutes : api.createHeartMinutes, heartMinutes);
  const response = yield call(callApi, apiCall);

  // success?
  if (response.ok) {
    response.data = mapDateFields(response.data);
    yield put(HeartMinutesActions.heartMinutesUpdateSuccess(response.data));
  } else {
    yield put(HeartMinutesActions.heartMinutesUpdateFailure(response.data));
  }
}

function* deleteHeartMinutes(api, action) {
  const { heartMinutesId } = action;
  // make the call to the api
  const apiCall = call(api.deleteHeartMinutes, heartMinutesId);
  const response = yield call(callApi, apiCall);

  // success?
  if (response.ok) {
    yield put(HeartMinutesActions.heartMinutesDeleteSuccess());
  } else {
    yield put(HeartMinutesActions.heartMinutesDeleteFailure(response.data));
  }
}
function mapDateFields(data) {
  data.startTime = convertDateTimeFromServer(data.startTime);
  data.endTime = convertDateTimeFromServer(data.endTime);
  return data;
}

export default {
  getAllHeartMinutes,
  getHeartMinutes,
  deleteHeartMinutes,
  updateHeartMinutes,
};
