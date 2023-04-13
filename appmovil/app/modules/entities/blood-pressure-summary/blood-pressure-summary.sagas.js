import { call, put } from 'redux-saga/effects';
import { callApi } from '../../../shared/sagas/call-api.saga';
import BloodPressureSummaryActions from './blood-pressure-summary.reducer';
import { convertDateTimeFromServer } from '../../../shared/util/date-transforms';

function* getBloodPressureSummary(api, action) {
  const { bloodPressureSummaryId } = action;
  // make the call to the api
  const apiCall = call(api.getBloodPressureSummary, bloodPressureSummaryId);
  const response = yield call(callApi, apiCall);

  // success?
  if (response.ok) {
    response.data = mapDateFields(response.data);
    yield put(BloodPressureSummaryActions.bloodPressureSummarySuccess(response.data));
  } else {
    yield put(BloodPressureSummaryActions.bloodPressureSummaryFailure(response.data));
  }
}

function* getAllBloodPressureSummaries(api, action) {
  const { options } = action;
  // make the call to the api
  const apiCall = call(api.getAllBloodPressureSummaries, options);
  const response = yield call(callApi, apiCall);

  // success?
  if (response.ok) {
    yield put(BloodPressureSummaryActions.bloodPressureSummaryAllSuccess(response.data, response.headers));
  } else {
    yield put(BloodPressureSummaryActions.bloodPressureSummaryAllFailure(response.data));
  }
}

function* updateBloodPressureSummary(api, action) {
  const { bloodPressureSummary } = action;
  // make the call to the api
  const idIsNotNull = !(bloodPressureSummary.id === null || bloodPressureSummary.id === undefined);
  const apiCall = call(idIsNotNull ? api.updateBloodPressureSummary : api.createBloodPressureSummary, bloodPressureSummary);
  const response = yield call(callApi, apiCall);

  // success?
  if (response.ok) {
    response.data = mapDateFields(response.data);
    yield put(BloodPressureSummaryActions.bloodPressureSummaryUpdateSuccess(response.data));
  } else {
    yield put(BloodPressureSummaryActions.bloodPressureSummaryUpdateFailure(response.data));
  }
}

function* deleteBloodPressureSummary(api, action) {
  const { bloodPressureSummaryId } = action;
  // make the call to the api
  const apiCall = call(api.deleteBloodPressureSummary, bloodPressureSummaryId);
  const response = yield call(callApi, apiCall);

  // success?
  if (response.ok) {
    yield put(BloodPressureSummaryActions.bloodPressureSummaryDeleteSuccess());
  } else {
    yield put(BloodPressureSummaryActions.bloodPressureSummaryDeleteFailure(response.data));
  }
}
function mapDateFields(data) {
  data.startTime = convertDateTimeFromServer(data.startTime);
  data.endTime = convertDateTimeFromServer(data.endTime);
  return data;
}

export default {
  getAllBloodPressureSummaries,
  getBloodPressureSummary,
  deleteBloodPressureSummary,
  updateBloodPressureSummary,
};
