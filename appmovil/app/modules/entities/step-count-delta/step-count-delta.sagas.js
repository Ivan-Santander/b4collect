import { call, put } from 'redux-saga/effects';
import { callApi } from '../../../shared/sagas/call-api.saga';
import StepCountDeltaActions from './step-count-delta.reducer';
import { convertDateTimeFromServer } from '../../../shared/util/date-transforms';

function* getStepCountDelta(api, action) {
  const { stepCountDeltaId } = action;
  // make the call to the api
  const apiCall = call(api.getStepCountDelta, stepCountDeltaId);
  const response = yield call(callApi, apiCall);

  // success?
  if (response.ok) {
    response.data = mapDateFields(response.data);
    yield put(StepCountDeltaActions.stepCountDeltaSuccess(response.data));
  } else {
    yield put(StepCountDeltaActions.stepCountDeltaFailure(response.data));
  }
}

function* getAllStepCountDeltas(api, action) {
  const { options } = action;
  // make the call to the api
  const apiCall = call(api.getAllStepCountDeltas, options);
  const response = yield call(callApi, apiCall);

  // success?
  if (response.ok) {
    yield put(StepCountDeltaActions.stepCountDeltaAllSuccess(response.data, response.headers));
  } else {
    yield put(StepCountDeltaActions.stepCountDeltaAllFailure(response.data));
  }
}

function* updateStepCountDelta(api, action) {
  const { stepCountDelta } = action;
  // make the call to the api
  const idIsNotNull = !(stepCountDelta.id === null || stepCountDelta.id === undefined);
  const apiCall = call(idIsNotNull ? api.updateStepCountDelta : api.createStepCountDelta, stepCountDelta);
  const response = yield call(callApi, apiCall);

  // success?
  if (response.ok) {
    response.data = mapDateFields(response.data);
    yield put(StepCountDeltaActions.stepCountDeltaUpdateSuccess(response.data));
  } else {
    yield put(StepCountDeltaActions.stepCountDeltaUpdateFailure(response.data));
  }
}

function* deleteStepCountDelta(api, action) {
  const { stepCountDeltaId } = action;
  // make the call to the api
  const apiCall = call(api.deleteStepCountDelta, stepCountDeltaId);
  const response = yield call(callApi, apiCall);

  // success?
  if (response.ok) {
    yield put(StepCountDeltaActions.stepCountDeltaDeleteSuccess());
  } else {
    yield put(StepCountDeltaActions.stepCountDeltaDeleteFailure(response.data));
  }
}
function mapDateFields(data) {
  data.startTime = convertDateTimeFromServer(data.startTime);
  data.endTime = convertDateTimeFromServer(data.endTime);
  return data;
}

export default {
  getAllStepCountDeltas,
  getStepCountDelta,
  deleteStepCountDelta,
  updateStepCountDelta,
};
