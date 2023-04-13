import { call, put } from 'redux-saga/effects';
import { callApi } from '../../../shared/sagas/call-api.saga';
import SpeedSummaryActions from './speed-summary.reducer';
import { convertDateTimeFromServer } from '../../../shared/util/date-transforms';

function* getSpeedSummary(api, action) {
  const { speedSummaryId } = action;
  // make the call to the api
  const apiCall = call(api.getSpeedSummary, speedSummaryId);
  const response = yield call(callApi, apiCall);

  // success?
  if (response.ok) {
    response.data = mapDateFields(response.data);
    yield put(SpeedSummaryActions.speedSummarySuccess(response.data));
  } else {
    yield put(SpeedSummaryActions.speedSummaryFailure(response.data));
  }
}

function* getAllSpeedSummaries(api, action) {
  const { options } = action;
  // make the call to the api
  const apiCall = call(api.getAllSpeedSummaries, options);
  const response = yield call(callApi, apiCall);

  // success?
  if (response.ok) {
    yield put(SpeedSummaryActions.speedSummaryAllSuccess(response.data, response.headers));
  } else {
    yield put(SpeedSummaryActions.speedSummaryAllFailure(response.data));
  }
}

function* updateSpeedSummary(api, action) {
  const { speedSummary } = action;
  // make the call to the api
  const idIsNotNull = !(speedSummary.id === null || speedSummary.id === undefined);
  const apiCall = call(idIsNotNull ? api.updateSpeedSummary : api.createSpeedSummary, speedSummary);
  const response = yield call(callApi, apiCall);

  // success?
  if (response.ok) {
    response.data = mapDateFields(response.data);
    yield put(SpeedSummaryActions.speedSummaryUpdateSuccess(response.data));
  } else {
    yield put(SpeedSummaryActions.speedSummaryUpdateFailure(response.data));
  }
}

function* deleteSpeedSummary(api, action) {
  const { speedSummaryId } = action;
  // make the call to the api
  const apiCall = call(api.deleteSpeedSummary, speedSummaryId);
  const response = yield call(callApi, apiCall);

  // success?
  if (response.ok) {
    yield put(SpeedSummaryActions.speedSummaryDeleteSuccess());
  } else {
    yield put(SpeedSummaryActions.speedSummaryDeleteFailure(response.data));
  }
}
function mapDateFields(data) {
  data.startTime = convertDateTimeFromServer(data.startTime);
  data.endTime = convertDateTimeFromServer(data.endTime);
  return data;
}

export default {
  getAllSpeedSummaries,
  getSpeedSummary,
  deleteSpeedSummary,
  updateSpeedSummary,
};
