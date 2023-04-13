import { call, put } from 'redux-saga/effects';
import { callApi } from '../../../shared/sagas/call-api.saga';
import NutritionActions from './nutrition.reducer';
import { convertDateTimeFromServer } from '../../../shared/util/date-transforms';

function* getNutrition(api, action) {
  const { nutritionId } = action;
  // make the call to the api
  const apiCall = call(api.getNutrition, nutritionId);
  const response = yield call(callApi, apiCall);

  // success?
  if (response.ok) {
    response.data = mapDateFields(response.data);
    yield put(NutritionActions.nutritionSuccess(response.data));
  } else {
    yield put(NutritionActions.nutritionFailure(response.data));
  }
}

function* getAllNutritions(api, action) {
  const { options } = action;
  // make the call to the api
  const apiCall = call(api.getAllNutritions, options);
  const response = yield call(callApi, apiCall);

  // success?
  if (response.ok) {
    yield put(NutritionActions.nutritionAllSuccess(response.data, response.headers));
  } else {
    yield put(NutritionActions.nutritionAllFailure(response.data));
  }
}

function* updateNutrition(api, action) {
  const { nutrition } = action;
  // make the call to the api
  const idIsNotNull = !(nutrition.id === null || nutrition.id === undefined);
  const apiCall = call(idIsNotNull ? api.updateNutrition : api.createNutrition, nutrition);
  const response = yield call(callApi, apiCall);

  // success?
  if (response.ok) {
    response.data = mapDateFields(response.data);
    yield put(NutritionActions.nutritionUpdateSuccess(response.data));
  } else {
    yield put(NutritionActions.nutritionUpdateFailure(response.data));
  }
}

function* deleteNutrition(api, action) {
  const { nutritionId } = action;
  // make the call to the api
  const apiCall = call(api.deleteNutrition, nutritionId);
  const response = yield call(callApi, apiCall);

  // success?
  if (response.ok) {
    yield put(NutritionActions.nutritionDeleteSuccess());
  } else {
    yield put(NutritionActions.nutritionDeleteFailure(response.data));
  }
}
function mapDateFields(data) {
  data.endTime = convertDateTimeFromServer(data.endTime);
  return data;
}

export default {
  getAllNutritions,
  getNutrition,
  deleteNutrition,
  updateNutrition,
};
