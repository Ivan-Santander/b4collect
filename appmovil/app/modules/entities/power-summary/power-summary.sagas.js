import { call, put } from 'redux-saga/effects';
import { callApi } from '../../../shared/sagas/call-api.saga';
import PowerSummaryActions from './power-summary.reducer';
import { convertDateTimeFromServer } from '../../../shared/util/date-transforms';

function* getPowerSummary(api, action) {
  const { powerSummaryId } = action;
  // make the call to the api
  const apiCall = call(api.getPowerSummary, powerSummaryId);
  const response = yield call(callApi, apiCall);

  // success?
  if (response.ok) {
    response.data = mapDateFields(response.data);
    yield put(PowerSummaryActions.powerSummarySuccess(response.data));
  } else {
    yield put(PowerSummaryActions.powerSummaryFailure(response.data));
  }
}

function* getAllPowerSummaries(api, action) {
  const { options } = action;
  // make the call to the api
  const apiCall = call(api.getAllPowerSummaries, options);
  const response = yield call(callApi, apiCall);

  // success?
  if (response.ok) {
    yield put(PowerSummaryActions.powerSummaryAllSuccess(response.data, response.headers));
  } else {
    yield put(PowerSummaryActions.powerSummaryAllFailure(response.data));
  }
}

function* updatePowerSummary(api, action) {
  const { powerSummary } = action;
  // make the call to the api
  const idIsNotNull = !(powerSummary.id === null || powerSummary.id === undefined);
  const apiCall = call(idIsNotNull ? api.updatePowerSummary : api.createPowerSummary, powerSummary);
  const response = yield call(callApi, apiCall);

  // success?
  if (response.ok) {
    response.data = mapDateFields(response.data);
    yield put(PowerSummaryActions.powerSummaryUpdateSuccess(response.data));
  } else {
    yield put(PowerSummaryActions.powerSummaryUpdateFailure(response.data));
  }
}

function* deletePowerSummary(api, action) {
  const { powerSummaryId } = action;
  // make the call to the api
  const apiCall = call(api.deletePowerSummary, powerSummaryId);
  const response = yield call(callApi, apiCall);

  // success?
  if (response.ok) {
    yield put(PowerSummaryActions.powerSummaryDeleteSuccess());
  } else {
    yield put(PowerSummaryActions.powerSummaryDeleteFailure(response.data));
  }
}
function mapDateFields(data) {
  data.startTime = convertDateTimeFromServer(data.startTime);
  data.endTime = convertDateTimeFromServer(data.endTime);
  return data;
}

export default {
  getAllPowerSummaries,
  getPowerSummary,
  deletePowerSummary,
  updatePowerSummary,
};
