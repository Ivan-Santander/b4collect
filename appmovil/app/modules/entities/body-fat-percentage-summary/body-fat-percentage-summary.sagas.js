import { call, put } from 'redux-saga/effects';
import { callApi } from '../../../shared/sagas/call-api.saga';
import BodyFatPercentageSummaryActions from './body-fat-percentage-summary.reducer';
import { convertDateTimeFromServer } from '../../../shared/util/date-transforms';

function* getBodyFatPercentageSummary(api, action) {
  const { bodyFatPercentageSummaryId } = action;
  // make the call to the api
  const apiCall = call(api.getBodyFatPercentageSummary, bodyFatPercentageSummaryId);
  const response = yield call(callApi, apiCall);

  // success?
  if (response.ok) {
    response.data = mapDateFields(response.data);
    yield put(BodyFatPercentageSummaryActions.bodyFatPercentageSummarySuccess(response.data));
  } else {
    yield put(BodyFatPercentageSummaryActions.bodyFatPercentageSummaryFailure(response.data));
  }
}

function* getAllBodyFatPercentageSummaries(api, action) {
  const { options } = action;
  // make the call to the api
  const apiCall = call(api.getAllBodyFatPercentageSummaries, options);
  const response = yield call(callApi, apiCall);

  // success?
  if (response.ok) {
    yield put(BodyFatPercentageSummaryActions.bodyFatPercentageSummaryAllSuccess(response.data, response.headers));
  } else {
    yield put(BodyFatPercentageSummaryActions.bodyFatPercentageSummaryAllFailure(response.data));
  }
}

function* updateBodyFatPercentageSummary(api, action) {
  const { bodyFatPercentageSummary } = action;
  // make the call to the api
  const idIsNotNull = !(bodyFatPercentageSummary.id === null || bodyFatPercentageSummary.id === undefined);
  const apiCall = call(idIsNotNull ? api.updateBodyFatPercentageSummary : api.createBodyFatPercentageSummary, bodyFatPercentageSummary);
  const response = yield call(callApi, apiCall);

  // success?
  if (response.ok) {
    response.data = mapDateFields(response.data);
    yield put(BodyFatPercentageSummaryActions.bodyFatPercentageSummaryUpdateSuccess(response.data));
  } else {
    yield put(BodyFatPercentageSummaryActions.bodyFatPercentageSummaryUpdateFailure(response.data));
  }
}

function* deleteBodyFatPercentageSummary(api, action) {
  const { bodyFatPercentageSummaryId } = action;
  // make the call to the api
  const apiCall = call(api.deleteBodyFatPercentageSummary, bodyFatPercentageSummaryId);
  const response = yield call(callApi, apiCall);

  // success?
  if (response.ok) {
    yield put(BodyFatPercentageSummaryActions.bodyFatPercentageSummaryDeleteSuccess());
  } else {
    yield put(BodyFatPercentageSummaryActions.bodyFatPercentageSummaryDeleteFailure(response.data));
  }
}
function mapDateFields(data) {
  data.startTime = convertDateTimeFromServer(data.startTime);
  data.endTime = convertDateTimeFromServer(data.endTime);
  return data;
}

export default {
  getAllBodyFatPercentageSummaries,
  getBodyFatPercentageSummary,
  deleteBodyFatPercentageSummary,
  updateBodyFatPercentageSummary,
};
