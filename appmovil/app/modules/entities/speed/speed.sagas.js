import { call, put } from 'redux-saga/effects';
import { callApi } from '../../../shared/sagas/call-api.saga';
import SpeedActions from './speed.reducer';
import { convertDateTimeFromServer } from '../../../shared/util/date-transforms';

function* getSpeed(api, action) {
  const { speedId } = action;
  // make the call to the api
  const apiCall = call(api.getSpeed, speedId);
  const response = yield call(callApi, apiCall);

  // success?
  if (response.ok) {
    response.data = mapDateFields(response.data);
    yield put(SpeedActions.speedSuccess(response.data));
  } else {
    yield put(SpeedActions.speedFailure(response.data));
  }
}

function* getAllSpeeds(api, action) {
  const { options } = action;
  // make the call to the api
  const apiCall = call(api.getAllSpeeds, options);
  const response = yield call(callApi, apiCall);

  // success?
  if (response.ok) {
    yield put(SpeedActions.speedAllSuccess(response.data, response.headers));
  } else {
    yield put(SpeedActions.speedAllFailure(response.data));
  }
}

function* updateSpeed(api, action) {
  const { speed } = action;
  // make the call to the api
  const idIsNotNull = !(speed.id === null || speed.id === undefined);
  const apiCall = call(idIsNotNull ? api.updateSpeed : api.createSpeed, speed);
  const response = yield call(callApi, apiCall);

  // success?
  if (response.ok) {
    response.data = mapDateFields(response.data);
    yield put(SpeedActions.speedUpdateSuccess(response.data));
  } else {
    yield put(SpeedActions.speedUpdateFailure(response.data));
  }
}

function* deleteSpeed(api, action) {
  const { speedId } = action;
  // make the call to the api
  const apiCall = call(api.deleteSpeed, speedId);
  const response = yield call(callApi, apiCall);

  // success?
  if (response.ok) {
    yield put(SpeedActions.speedDeleteSuccess());
  } else {
    yield put(SpeedActions.speedDeleteFailure(response.data));
  }
}
function mapDateFields(data) {
  data.endTime = convertDateTimeFromServer(data.endTime);
  return data;
}

export default {
  getAllSpeeds,
  getSpeed,
  deleteSpeed,
  updateSpeed,
};
