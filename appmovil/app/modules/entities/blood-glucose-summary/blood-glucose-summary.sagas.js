import { call, put } from 'redux-saga/effects';
import { callApi } from '../../../shared/sagas/call-api.saga';
import BloodGlucoseSummaryActions from './blood-glucose-summary.reducer';
import { convertDateTimeFromServer } from '../../../shared/util/date-transforms';

function* getBloodGlucoseSummary(api, action) {
  const { bloodGlucoseSummaryId } = action;
  // make the call to the api
  const apiCall = call(api.getBloodGlucoseSummary, bloodGlucoseSummaryId);
  const response = yield call(callApi, apiCall);

  // success?
  if (response.ok) {
    response.data = mapDateFields(response.data);
    yield put(BloodGlucoseSummaryActions.bloodGlucoseSummarySuccess(response.data));
  } else {
    yield put(BloodGlucoseSummaryActions.bloodGlucoseSummaryFailure(response.data));
  }
}

function* getAllBloodGlucoseSummaries(api, action) {
  const { options } = action;
  // make the call to the api
  const apiCall = call(api.getAllBloodGlucoseSummaries, options);
  const response = yield call(callApi, apiCall);

  // success?
  if (response.ok) {
    yield put(BloodGlucoseSummaryActions.bloodGlucoseSummaryAllSuccess(response.data, response.headers));
  } else {
    yield put(BloodGlucoseSummaryActions.bloodGlucoseSummaryAllFailure(response.data));
  }
}

function* updateBloodGlucoseSummary(api, action) {
  const { bloodGlucoseSummary } = action;
  // make the call to the api
  const idIsNotNull = !(bloodGlucoseSummary.id === null || bloodGlucoseSummary.id === undefined);
  const apiCall = call(idIsNotNull ? api.updateBloodGlucoseSummary : api.createBloodGlucoseSummary, bloodGlucoseSummary);
  const response = yield call(callApi, apiCall);

  // success?
  if (response.ok) {
    response.data = mapDateFields(response.data);
    yield put(BloodGlucoseSummaryActions.bloodGlucoseSummaryUpdateSuccess(response.data));
  } else {
    yield put(BloodGlucoseSummaryActions.bloodGlucoseSummaryUpdateFailure(response.data));
  }
}

function* deleteBloodGlucoseSummary(api, action) {
  const { bloodGlucoseSummaryId } = action;
  // make the call to the api
  const apiCall = call(api.deleteBloodGlucoseSummary, bloodGlucoseSummaryId);
  const response = yield call(callApi, apiCall);

  // success?
  if (response.ok) {
    yield put(BloodGlucoseSummaryActions.bloodGlucoseSummaryDeleteSuccess());
  } else {
    yield put(BloodGlucoseSummaryActions.bloodGlucoseSummaryDeleteFailure(response.data));
  }
}
function mapDateFields(data) {
  data.startTime = convertDateTimeFromServer(data.startTime);
  data.endTime = convertDateTimeFromServer(data.endTime);
  return data;
}

export default {
  getAllBloodGlucoseSummaries,
  getBloodGlucoseSummary,
  deleteBloodGlucoseSummary,
  updateBloodGlucoseSummary,
};
