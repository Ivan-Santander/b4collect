import { call, put } from 'redux-saga/effects';
import { callApi } from '../../../shared/sagas/call-api.saga';
import BodyTemperatureActions from './body-temperature.reducer';
import { convertDateTimeFromServer } from '../../../shared/util/date-transforms';

function* getBodyTemperature(api, action) {
  const { bodyTemperatureId } = action;
  // make the call to the api
  const apiCall = call(api.getBodyTemperature, bodyTemperatureId);
  const response = yield call(callApi, apiCall);

  // success?
  if (response.ok) {
    response.data = mapDateFields(response.data);
    yield put(BodyTemperatureActions.bodyTemperatureSuccess(response.data));
  } else {
    yield put(BodyTemperatureActions.bodyTemperatureFailure(response.data));
  }
}

function* getAllBodyTemperatures(api, action) {
  const { options } = action;
  // make the call to the api
  const apiCall = call(api.getAllBodyTemperatures, options);
  const response = yield call(callApi, apiCall);

  // success?
  if (response.ok) {
    yield put(BodyTemperatureActions.bodyTemperatureAllSuccess(response.data, response.headers));
  } else {
    yield put(BodyTemperatureActions.bodyTemperatureAllFailure(response.data));
  }
}

function* updateBodyTemperature(api, action) {
  const { bodyTemperature } = action;
  // make the call to the api
  const idIsNotNull = !(bodyTemperature.id === null || bodyTemperature.id === undefined);
  const apiCall = call(idIsNotNull ? api.updateBodyTemperature : api.createBodyTemperature, bodyTemperature);
  const response = yield call(callApi, apiCall);

  // success?
  if (response.ok) {
    response.data = mapDateFields(response.data);
    yield put(BodyTemperatureActions.bodyTemperatureUpdateSuccess(response.data));
  } else {
    yield put(BodyTemperatureActions.bodyTemperatureUpdateFailure(response.data));
  }
}

function* deleteBodyTemperature(api, action) {
  const { bodyTemperatureId } = action;
  // make the call to the api
  const apiCall = call(api.deleteBodyTemperature, bodyTemperatureId);
  const response = yield call(callApi, apiCall);

  // success?
  if (response.ok) {
    yield put(BodyTemperatureActions.bodyTemperatureDeleteSuccess());
  } else {
    yield put(BodyTemperatureActions.bodyTemperatureDeleteFailure(response.data));
  }
}
function mapDateFields(data) {
  data.endTime = convertDateTimeFromServer(data.endTime);
  return data;
}

export default {
  getAllBodyTemperatures,
  getBodyTemperature,
  deleteBodyTemperature,
  updateBodyTemperature,
};
