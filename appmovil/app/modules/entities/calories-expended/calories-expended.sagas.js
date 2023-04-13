import { call, put } from 'redux-saga/effects';
import { callApi } from '../../../shared/sagas/call-api.saga';
import CaloriesExpendedActions from './calories-expended.reducer';
import { convertDateTimeFromServer } from '../../../shared/util/date-transforms';

function* getCaloriesExpended(api, action) {
  const { caloriesExpendedId } = action;
  // make the call to the api
  const apiCall = call(api.getCaloriesExpended, caloriesExpendedId);
  const response = yield call(callApi, apiCall);

  // success?
  if (response.ok) {
    response.data = mapDateFields(response.data);
    yield put(CaloriesExpendedActions.caloriesExpendedSuccess(response.data));
  } else {
    yield put(CaloriesExpendedActions.caloriesExpendedFailure(response.data));
  }
}

function* getAllCaloriesExpendeds(api, action) {
  const { options } = action;
  // make the call to the api
  const apiCall = call(api.getAllCaloriesExpendeds, options);
  const response = yield call(callApi, apiCall);

  // success?
  if (response.ok) {
    yield put(CaloriesExpendedActions.caloriesExpendedAllSuccess(response.data, response.headers));
  } else {
    yield put(CaloriesExpendedActions.caloriesExpendedAllFailure(response.data));
  }
}

function* updateCaloriesExpended(api, action) {
  const { caloriesExpended } = action;
  // make the call to the api
  const idIsNotNull = !(caloriesExpended.id === null || caloriesExpended.id === undefined);
  const apiCall = call(idIsNotNull ? api.updateCaloriesExpended : api.createCaloriesExpended, caloriesExpended);
  const response = yield call(callApi, apiCall);

  // success?
  if (response.ok) {
    response.data = mapDateFields(response.data);
    yield put(CaloriesExpendedActions.caloriesExpendedUpdateSuccess(response.data));
  } else {
    yield put(CaloriesExpendedActions.caloriesExpendedUpdateFailure(response.data));
  }
}

function* deleteCaloriesExpended(api, action) {
  const { caloriesExpendedId } = action;
  // make the call to the api
  const apiCall = call(api.deleteCaloriesExpended, caloriesExpendedId);
  const response = yield call(callApi, apiCall);

  // success?
  if (response.ok) {
    yield put(CaloriesExpendedActions.caloriesExpendedDeleteSuccess());
  } else {
    yield put(CaloriesExpendedActions.caloriesExpendedDeleteFailure(response.data));
  }
}
function mapDateFields(data) {
  data.startTime = convertDateTimeFromServer(data.startTime);
  data.endTime = convertDateTimeFromServer(data.endTime);
  return data;
}

export default {
  getAllCaloriesExpendeds,
  getCaloriesExpended,
  deleteCaloriesExpended,
  updateCaloriesExpended,
};
