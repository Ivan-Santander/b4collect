import { call, put } from 'redux-saga/effects';
import { callApi } from '../../../shared/sagas/call-api.saga';
import HeartRateSummaryActions from './heart-rate-summary.reducer';
import { convertDateTimeFromServer } from '../../../shared/util/date-transforms';

function* getHeartRateSummary(api, action) {
  const { heartRateSummaryId } = action;
  // make the call to the api
  const apiCall = call(api.getHeartRateSummary, heartRateSummaryId);
  const response = yield call(callApi, apiCall);

  // success?
  if (response.ok) {
    response.data = mapDateFields(response.data);
    yield put(HeartRateSummaryActions.heartRateSummarySuccess(response.data));
  } else {
    yield put(HeartRateSummaryActions.heartRateSummaryFailure(response.data));
  }
}

function* getAllHeartRateSummaries(api, action) {
  const { options } = action;
  // make the call to the api
  const apiCall = call(api.getAllHeartRateSummaries, options);
  const response = yield call(callApi, apiCall);

  // success?
  if (response.ok) {
    yield put(HeartRateSummaryActions.heartRateSummaryAllSuccess(response.data, response.headers));
  } else {
    yield put(HeartRateSummaryActions.heartRateSummaryAllFailure(response.data));
  }
}

function* updateHeartRateSummary(api, action) {
  const { heartRateSummary } = action;
  // make the call to the api
  const idIsNotNull = !(heartRateSummary.id === null || heartRateSummary.id === undefined);
  const apiCall = call(idIsNotNull ? api.updateHeartRateSummary : api.createHeartRateSummary, heartRateSummary);
  const response = yield call(callApi, apiCall);

  // success?
  if (response.ok) {
    response.data = mapDateFields(response.data);
    yield put(HeartRateSummaryActions.heartRateSummaryUpdateSuccess(response.data));
  } else {
    yield put(HeartRateSummaryActions.heartRateSummaryUpdateFailure(response.data));
  }
}

function* deleteHeartRateSummary(api, action) {
  const { heartRateSummaryId } = action;
  // make the call to the api
  const apiCall = call(api.deleteHeartRateSummary, heartRateSummaryId);
  const response = yield call(callApi, apiCall);

  // success?
  if (response.ok) {
    yield put(HeartRateSummaryActions.heartRateSummaryDeleteSuccess());
  } else {
    yield put(HeartRateSummaryActions.heartRateSummaryDeleteFailure(response.data));
  }
}
function mapDateFields(data) {
  data.startTime = convertDateTimeFromServer(data.startTime);
  data.endTime = convertDateTimeFromServer(data.endTime);
  return data;
}

export default {
  getAllHeartRateSummaries,
  getHeartRateSummary,
  deleteHeartRateSummary,
  updateHeartRateSummary,
};
