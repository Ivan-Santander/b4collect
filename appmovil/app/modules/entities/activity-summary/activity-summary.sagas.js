import { call, put } from 'redux-saga/effects';
import { callApi } from '../../../shared/sagas/call-api.saga';
import ActivitySummaryActions from './activity-summary.reducer';
import { convertDateTimeFromServer } from '../../../shared/util/date-transforms';

function* getActivitySummary(api, action) {
  const { activitySummaryId } = action;
  // make the call to the api
  const apiCall = call(api.getActivitySummary, activitySummaryId);
  const response = yield call(callApi, apiCall);

  // success?
  if (response.ok) {
    response.data = mapDateFields(response.data);
    yield put(ActivitySummaryActions.activitySummarySuccess(response.data));
  } else {
    yield put(ActivitySummaryActions.activitySummaryFailure(response.data));
  }
}

function* getAllActivitySummaries(api, action) {
  const { options } = action;
  // make the call to the api
  const apiCall = call(api.getAllActivitySummaries, options);
  const response = yield call(callApi, apiCall);

  // success?
  if (response.ok) {
    yield put(ActivitySummaryActions.activitySummaryAllSuccess(response.data, response.headers));
  } else {
    yield put(ActivitySummaryActions.activitySummaryAllFailure(response.data));
  }
}

function* updateActivitySummary(api, action) {
  const { activitySummary } = action;
  // make the call to the api
  const idIsNotNull = !(activitySummary.id === null || activitySummary.id === undefined);
  const apiCall = call(idIsNotNull ? api.updateActivitySummary : api.createActivitySummary, activitySummary);
  const response = yield call(callApi, apiCall);

  // success?
  if (response.ok) {
    response.data = mapDateFields(response.data);
    yield put(ActivitySummaryActions.activitySummaryUpdateSuccess(response.data));
  } else {
    yield put(ActivitySummaryActions.activitySummaryUpdateFailure(response.data));
  }
}

function* deleteActivitySummary(api, action) {
  const { activitySummaryId } = action;
  // make the call to the api
  const apiCall = call(api.deleteActivitySummary, activitySummaryId);
  const response = yield call(callApi, apiCall);

  // success?
  if (response.ok) {
    yield put(ActivitySummaryActions.activitySummaryDeleteSuccess());
  } else {
    yield put(ActivitySummaryActions.activitySummaryDeleteFailure(response.data));
  }
}
function mapDateFields(data) {
  data.startTime = convertDateTimeFromServer(data.startTime);
  data.endTime = convertDateTimeFromServer(data.endTime);
  return data;
}

export default {
  getAllActivitySummaries,
  getActivitySummary,
  deleteActivitySummary,
  updateActivitySummary,
};
