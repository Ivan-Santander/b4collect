import { call, put } from 'redux-saga/effects';
import { callApi } from '../../../shared/sagas/call-api.saga';
import NutritionSummaryActions from './nutrition-summary.reducer';
import { convertDateTimeFromServer } from '../../../shared/util/date-transforms';

function* getNutritionSummary(api, action) {
  const { nutritionSummaryId } = action;
  // make the call to the api
  const apiCall = call(api.getNutritionSummary, nutritionSummaryId);
  const response = yield call(callApi, apiCall);

  // success?
  if (response.ok) {
    response.data = mapDateFields(response.data);
    yield put(NutritionSummaryActions.nutritionSummarySuccess(response.data));
  } else {
    yield put(NutritionSummaryActions.nutritionSummaryFailure(response.data));
  }
}

function* getAllNutritionSummaries(api, action) {
  const { options } = action;
  // make the call to the api
  const apiCall = call(api.getAllNutritionSummaries, options);
  const response = yield call(callApi, apiCall);

  // success?
  if (response.ok) {
    yield put(NutritionSummaryActions.nutritionSummaryAllSuccess(response.data, response.headers));
  } else {
    yield put(NutritionSummaryActions.nutritionSummaryAllFailure(response.data));
  }
}

function* updateNutritionSummary(api, action) {
  const { nutritionSummary } = action;
  // make the call to the api
  const idIsNotNull = !(nutritionSummary.id === null || nutritionSummary.id === undefined);
  const apiCall = call(idIsNotNull ? api.updateNutritionSummary : api.createNutritionSummary, nutritionSummary);
  const response = yield call(callApi, apiCall);

  // success?
  if (response.ok) {
    response.data = mapDateFields(response.data);
    yield put(NutritionSummaryActions.nutritionSummaryUpdateSuccess(response.data));
  } else {
    yield put(NutritionSummaryActions.nutritionSummaryUpdateFailure(response.data));
  }
}

function* deleteNutritionSummary(api, action) {
  const { nutritionSummaryId } = action;
  // make the call to the api
  const apiCall = call(api.deleteNutritionSummary, nutritionSummaryId);
  const response = yield call(callApi, apiCall);

  // success?
  if (response.ok) {
    yield put(NutritionSummaryActions.nutritionSummaryDeleteSuccess());
  } else {
    yield put(NutritionSummaryActions.nutritionSummaryDeleteFailure(response.data));
  }
}
function mapDateFields(data) {
  data.startTime = convertDateTimeFromServer(data.startTime);
  data.endTime = convertDateTimeFromServer(data.endTime);
  return data;
}

export default {
  getAllNutritionSummaries,
  getNutritionSummary,
  deleteNutritionSummary,
  updateNutritionSummary,
};
