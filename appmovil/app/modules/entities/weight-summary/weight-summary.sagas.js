import { call, put } from 'redux-saga/effects';
import { callApi } from '../../../shared/sagas/call-api.saga';
import WeightSummaryActions from './weight-summary.reducer';
import { convertDateTimeFromServer } from '../../../shared/util/date-transforms';

function* getWeightSummary(api, action) {
  const { weightSummaryId } = action;
  // make the call to the api
  const apiCall = call(api.getWeightSummary, weightSummaryId);
  const response = yield call(callApi, apiCall);

  // success?
  if (response.ok) {
    response.data = mapDateFields(response.data);
    yield put(WeightSummaryActions.weightSummarySuccess(response.data));
  } else {
    yield put(WeightSummaryActions.weightSummaryFailure(response.data));
  }
}

function* getAllWeightSummaries(api, action) {
  const { options } = action;
  // make the call to the api
  const apiCall = call(api.getAllWeightSummaries, options);
  const response = yield call(callApi, apiCall);

  // success?
  if (response.ok) {
    yield put(WeightSummaryActions.weightSummaryAllSuccess(response.data, response.headers));
  } else {
    yield put(WeightSummaryActions.weightSummaryAllFailure(response.data));
  }
}

function* updateWeightSummary(api, action) {
  const { weightSummary } = action;
  // make the call to the api
  const idIsNotNull = !(weightSummary.id === null || weightSummary.id === undefined);
  const apiCall = call(idIsNotNull ? api.updateWeightSummary : api.createWeightSummary, weightSummary);
  const response = yield call(callApi, apiCall);

  // success?
  if (response.ok) {
    response.data = mapDateFields(response.data);
    yield put(WeightSummaryActions.weightSummaryUpdateSuccess(response.data));
  } else {
    yield put(WeightSummaryActions.weightSummaryUpdateFailure(response.data));
  }
}

function* deleteWeightSummary(api, action) {
  const { weightSummaryId } = action;
  // make the call to the api
  const apiCall = call(api.deleteWeightSummary, weightSummaryId);
  const response = yield call(callApi, apiCall);

  // success?
  if (response.ok) {
    yield put(WeightSummaryActions.weightSummaryDeleteSuccess());
  } else {
    yield put(WeightSummaryActions.weightSummaryDeleteFailure(response.data));
  }
}
function mapDateFields(data) {
  data.startTime = convertDateTimeFromServer(data.startTime);
  data.endTime = convertDateTimeFromServer(data.endTime);
  return data;
}

export default {
  getAllWeightSummaries,
  getWeightSummary,
  deleteWeightSummary,
  updateWeightSummary,
};
