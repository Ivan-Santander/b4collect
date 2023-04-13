import { call, put } from 'redux-saga/effects';
import { callApi } from '../../../shared/sagas/call-api.saga';
import HeightSummaryActions from './height-summary.reducer';
import { convertDateTimeFromServer } from '../../../shared/util/date-transforms';

function* getHeightSummary(api, action) {
  const { heightSummaryId } = action;
  // make the call to the api
  const apiCall = call(api.getHeightSummary, heightSummaryId);
  const response = yield call(callApi, apiCall);

  // success?
  if (response.ok) {
    response.data = mapDateFields(response.data);
    yield put(HeightSummaryActions.heightSummarySuccess(response.data));
  } else {
    yield put(HeightSummaryActions.heightSummaryFailure(response.data));
  }
}

function* getAllHeightSummaries(api, action) {
  const { options } = action;
  // make the call to the api
  const apiCall = call(api.getAllHeightSummaries, options);
  const response = yield call(callApi, apiCall);

  // success?
  if (response.ok) {
    yield put(HeightSummaryActions.heightSummaryAllSuccess(response.data, response.headers));
  } else {
    yield put(HeightSummaryActions.heightSummaryAllFailure(response.data));
  }
}

function* updateHeightSummary(api, action) {
  const { heightSummary } = action;
  // make the call to the api
  const idIsNotNull = !(heightSummary.id === null || heightSummary.id === undefined);
  const apiCall = call(idIsNotNull ? api.updateHeightSummary : api.createHeightSummary, heightSummary);
  const response = yield call(callApi, apiCall);

  // success?
  if (response.ok) {
    response.data = mapDateFields(response.data);
    yield put(HeightSummaryActions.heightSummaryUpdateSuccess(response.data));
  } else {
    yield put(HeightSummaryActions.heightSummaryUpdateFailure(response.data));
  }
}

function* deleteHeightSummary(api, action) {
  const { heightSummaryId } = action;
  // make the call to the api
  const apiCall = call(api.deleteHeightSummary, heightSummaryId);
  const response = yield call(callApi, apiCall);

  // success?
  if (response.ok) {
    yield put(HeightSummaryActions.heightSummaryDeleteSuccess());
  } else {
    yield put(HeightSummaryActions.heightSummaryDeleteFailure(response.data));
  }
}
function mapDateFields(data) {
  data.startTime = convertDateTimeFromServer(data.startTime);
  data.endTime = convertDateTimeFromServer(data.endTime);
  return data;
}

export default {
  getAllHeightSummaries,
  getHeightSummary,
  deleteHeightSummary,
  updateHeightSummary,
};
