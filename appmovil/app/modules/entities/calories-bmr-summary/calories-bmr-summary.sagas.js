import { call, put } from 'redux-saga/effects';
import { callApi } from '../../../shared/sagas/call-api.saga';
import CaloriesBmrSummaryActions from './calories-bmr-summary.reducer';
import { convertDateTimeFromServer } from '../../../shared/util/date-transforms';

function* getCaloriesBmrSummary(api, action) {
  const { caloriesBmrSummaryId } = action;
  // make the call to the api
  const apiCall = call(api.getCaloriesBmrSummary, caloriesBmrSummaryId);
  const response = yield call(callApi, apiCall);

  // success?
  if (response.ok) {
    response.data = mapDateFields(response.data);
    yield put(CaloriesBmrSummaryActions.caloriesBmrSummarySuccess(response.data));
  } else {
    yield put(CaloriesBmrSummaryActions.caloriesBmrSummaryFailure(response.data));
  }
}

function* getAllCaloriesBmrSummaries(api, action) {
  const { options } = action;
  // make the call to the api
  const apiCall = call(api.getAllCaloriesBmrSummaries, options);
  const response = yield call(callApi, apiCall);

  // success?
  if (response.ok) {
    yield put(CaloriesBmrSummaryActions.caloriesBmrSummaryAllSuccess(response.data, response.headers));
  } else {
    yield put(CaloriesBmrSummaryActions.caloriesBmrSummaryAllFailure(response.data));
  }
}

function* updateCaloriesBmrSummary(api, action) {
  const { caloriesBmrSummary } = action;
  // make the call to the api
  const idIsNotNull = !(caloriesBmrSummary.id === null || caloriesBmrSummary.id === undefined);
  const apiCall = call(idIsNotNull ? api.updateCaloriesBmrSummary : api.createCaloriesBmrSummary, caloriesBmrSummary);
  const response = yield call(callApi, apiCall);

  // success?
  if (response.ok) {
    response.data = mapDateFields(response.data);
    yield put(CaloriesBmrSummaryActions.caloriesBmrSummaryUpdateSuccess(response.data));
  } else {
    yield put(CaloriesBmrSummaryActions.caloriesBmrSummaryUpdateFailure(response.data));
  }
}

function* deleteCaloriesBmrSummary(api, action) {
  const { caloriesBmrSummaryId } = action;
  // make the call to the api
  const apiCall = call(api.deleteCaloriesBmrSummary, caloriesBmrSummaryId);
  const response = yield call(callApi, apiCall);

  // success?
  if (response.ok) {
    yield put(CaloriesBmrSummaryActions.caloriesBmrSummaryDeleteSuccess());
  } else {
    yield put(CaloriesBmrSummaryActions.caloriesBmrSummaryDeleteFailure(response.data));
  }
}
function mapDateFields(data) {
  data.startTime = convertDateTimeFromServer(data.startTime);
  data.endTime = convertDateTimeFromServer(data.endTime);
  return data;
}

export default {
  getAllCaloriesBmrSummaries,
  getCaloriesBmrSummary,
  deleteCaloriesBmrSummary,
  updateCaloriesBmrSummary,
};
