import { call, put } from 'redux-saga/effects';
import { callApi } from '../../../shared/sagas/call-api.saga';
import SleepSegmentActions from './sleep-segment.reducer';
import { convertDateTimeFromServer } from '../../../shared/util/date-transforms';

function* getSleepSegment(api, action) {
  const { sleepSegmentId } = action;
  // make the call to the api
  const apiCall = call(api.getSleepSegment, sleepSegmentId);
  const response = yield call(callApi, apiCall);

  // success?
  if (response.ok) {
    response.data = mapDateFields(response.data);
    yield put(SleepSegmentActions.sleepSegmentSuccess(response.data));
  } else {
    yield put(SleepSegmentActions.sleepSegmentFailure(response.data));
  }
}

function* getAllSleepSegments(api, action) {
  const { options } = action;
  // make the call to the api
  const apiCall = call(api.getAllSleepSegments, options);
  const response = yield call(callApi, apiCall);

  // success?
  if (response.ok) {
    yield put(SleepSegmentActions.sleepSegmentAllSuccess(response.data, response.headers));
  } else {
    yield put(SleepSegmentActions.sleepSegmentAllFailure(response.data));
  }
}

function* updateSleepSegment(api, action) {
  const { sleepSegment } = action;
  // make the call to the api
  const idIsNotNull = !(sleepSegment.id === null || sleepSegment.id === undefined);
  const apiCall = call(idIsNotNull ? api.updateSleepSegment : api.createSleepSegment, sleepSegment);
  const response = yield call(callApi, apiCall);

  // success?
  if (response.ok) {
    response.data = mapDateFields(response.data);
    yield put(SleepSegmentActions.sleepSegmentUpdateSuccess(response.data));
  } else {
    yield put(SleepSegmentActions.sleepSegmentUpdateFailure(response.data));
  }
}

function* deleteSleepSegment(api, action) {
  const { sleepSegmentId } = action;
  // make the call to the api
  const apiCall = call(api.deleteSleepSegment, sleepSegmentId);
  const response = yield call(callApi, apiCall);

  // success?
  if (response.ok) {
    yield put(SleepSegmentActions.sleepSegmentDeleteSuccess());
  } else {
    yield put(SleepSegmentActions.sleepSegmentDeleteFailure(response.data));
  }
}
function mapDateFields(data) {
  data.startTime = convertDateTimeFromServer(data.startTime);
  data.endTime = convertDateTimeFromServer(data.endTime);
  return data;
}

export default {
  getAllSleepSegments,
  getSleepSegment,
  deleteSleepSegment,
  updateSleepSegment,
};
