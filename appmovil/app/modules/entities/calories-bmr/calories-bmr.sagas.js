import { call, put } from 'redux-saga/effects';
import { callApi } from '../../../shared/sagas/call-api.saga';
import CaloriesBMRActions from './calories-bmr.reducer';
import { convertDateTimeFromServer } from '../../../shared/util/date-transforms';

function* getCaloriesBMR(api, action) {
  const { caloriesBMRId } = action;
  // make the call to the api
  const apiCall = call(api.getCaloriesBMR, caloriesBMRId);
  const response = yield call(callApi, apiCall);

  // success?
  if (response.ok) {
    response.data = mapDateFields(response.data);
    yield put(CaloriesBMRActions.caloriesBMRSuccess(response.data));
  } else {
    yield put(CaloriesBMRActions.caloriesBMRFailure(response.data));
  }
}

function* getAllCaloriesBMRS(api, action) {
  const { options } = action;
  // make the call to the api
  const apiCall = call(api.getAllCaloriesBMRS, options);
  const response = yield call(callApi, apiCall);

  // success?
  if (response.ok) {
    yield put(CaloriesBMRActions.caloriesBMRAllSuccess(response.data, response.headers));
  } else {
    yield put(CaloriesBMRActions.caloriesBMRAllFailure(response.data));
  }
}

function* updateCaloriesBMR(api, action) {
  const { caloriesBMR } = action;
  // make the call to the api
  const idIsNotNull = !(caloriesBMR.id === null || caloriesBMR.id === undefined);
  const apiCall = call(idIsNotNull ? api.updateCaloriesBMR : api.createCaloriesBMR, caloriesBMR);
  const response = yield call(callApi, apiCall);

  // success?
  if (response.ok) {
    response.data = mapDateFields(response.data);
    yield put(CaloriesBMRActions.caloriesBMRUpdateSuccess(response.data));
  } else {
    yield put(CaloriesBMRActions.caloriesBMRUpdateFailure(response.data));
  }
}

function* deleteCaloriesBMR(api, action) {
  const { caloriesBMRId } = action;
  // make the call to the api
  const apiCall = call(api.deleteCaloriesBMR, caloriesBMRId);
  const response = yield call(callApi, apiCall);

  // success?
  if (response.ok) {
    yield put(CaloriesBMRActions.caloriesBMRDeleteSuccess());
  } else {
    yield put(CaloriesBMRActions.caloriesBMRDeleteFailure(response.data));
  }
}
function mapDateFields(data) {
  data.startTime = convertDateTimeFromServer(data.startTime);
  data.endTime = convertDateTimeFromServer(data.endTime);
  return data;
}

export default {
  getAllCaloriesBMRS,
  getCaloriesBMR,
  deleteCaloriesBMR,
  updateCaloriesBMR,
};
