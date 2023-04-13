import { call, put } from 'redux-saga/effects';
import { callApi } from '../../../shared/sagas/call-api.saga';
import StepCountCadenceActions from './step-count-cadence.reducer';
import { convertDateTimeFromServer } from '../../../shared/util/date-transforms';

function* getStepCountCadence(api, action) {
  const { stepCountCadenceId } = action;
  // make the call to the api
  const apiCall = call(api.getStepCountCadence, stepCountCadenceId);
  const response = yield call(callApi, apiCall);

  // success?
  if (response.ok) {
    response.data = mapDateFields(response.data);
    yield put(StepCountCadenceActions.stepCountCadenceSuccess(response.data));
  } else {
    yield put(StepCountCadenceActions.stepCountCadenceFailure(response.data));
  }
}

function* getAllStepCountCadences(api, action) {
  const { options } = action;
  // make the call to the api
  const apiCall = call(api.getAllStepCountCadences, options);
  const response = yield call(callApi, apiCall);

  // success?
  if (response.ok) {
    yield put(StepCountCadenceActions.stepCountCadenceAllSuccess(response.data, response.headers));
  } else {
    yield put(StepCountCadenceActions.stepCountCadenceAllFailure(response.data));
  }
}

function* updateStepCountCadence(api, action) {
  const { stepCountCadence } = action;
  // make the call to the api
  const idIsNotNull = !(stepCountCadence.id === null || stepCountCadence.id === undefined);
  const apiCall = call(idIsNotNull ? api.updateStepCountCadence : api.createStepCountCadence, stepCountCadence);
  const response = yield call(callApi, apiCall);

  // success?
  if (response.ok) {
    response.data = mapDateFields(response.data);
    yield put(StepCountCadenceActions.stepCountCadenceUpdateSuccess(response.data));
  } else {
    yield put(StepCountCadenceActions.stepCountCadenceUpdateFailure(response.data));
  }
}

function* deleteStepCountCadence(api, action) {
  const { stepCountCadenceId } = action;
  // make the call to the api
  const apiCall = call(api.deleteStepCountCadence, stepCountCadenceId);
  const response = yield call(callApi, apiCall);

  // success?
  if (response.ok) {
    yield put(StepCountCadenceActions.stepCountCadenceDeleteSuccess());
  } else {
    yield put(StepCountCadenceActions.stepCountCadenceDeleteFailure(response.data));
  }
}
function mapDateFields(data) {
  data.startTime = convertDateTimeFromServer(data.startTime);
  data.endTime = convertDateTimeFromServer(data.endTime);
  return data;
}

export default {
  getAllStepCountCadences,
  getStepCountCadence,
  deleteStepCountCadence,
  updateStepCountCadence,
};
