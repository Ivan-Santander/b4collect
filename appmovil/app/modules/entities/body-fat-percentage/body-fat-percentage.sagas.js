import { call, put } from 'redux-saga/effects';
import { callApi } from '../../../shared/sagas/call-api.saga';
import BodyFatPercentageActions from './body-fat-percentage.reducer';
import { convertDateTimeFromServer } from '../../../shared/util/date-transforms';

function* getBodyFatPercentage(api, action) {
  const { bodyFatPercentageId } = action;
  // make the call to the api
  const apiCall = call(api.getBodyFatPercentage, bodyFatPercentageId);
  const response = yield call(callApi, apiCall);

  // success?
  if (response.ok) {
    response.data = mapDateFields(response.data);
    yield put(BodyFatPercentageActions.bodyFatPercentageSuccess(response.data));
  } else {
    yield put(BodyFatPercentageActions.bodyFatPercentageFailure(response.data));
  }
}

function* getAllBodyFatPercentages(api, action) {
  const { options } = action;
  // make the call to the api
  const apiCall = call(api.getAllBodyFatPercentages, options);
  const response = yield call(callApi, apiCall);

  // success?
  if (response.ok) {
    yield put(BodyFatPercentageActions.bodyFatPercentageAllSuccess(response.data, response.headers));
  } else {
    yield put(BodyFatPercentageActions.bodyFatPercentageAllFailure(response.data));
  }
}

function* updateBodyFatPercentage(api, action) {
  const { bodyFatPercentage } = action;
  // make the call to the api
  const idIsNotNull = !(bodyFatPercentage.id === null || bodyFatPercentage.id === undefined);
  const apiCall = call(idIsNotNull ? api.updateBodyFatPercentage : api.createBodyFatPercentage, bodyFatPercentage);
  const response = yield call(callApi, apiCall);

  // success?
  if (response.ok) {
    response.data = mapDateFields(response.data);
    yield put(BodyFatPercentageActions.bodyFatPercentageUpdateSuccess(response.data));
  } else {
    yield put(BodyFatPercentageActions.bodyFatPercentageUpdateFailure(response.data));
  }
}

function* deleteBodyFatPercentage(api, action) {
  const { bodyFatPercentageId } = action;
  // make the call to the api
  const apiCall = call(api.deleteBodyFatPercentage, bodyFatPercentageId);
  const response = yield call(callApi, apiCall);

  // success?
  if (response.ok) {
    yield put(BodyFatPercentageActions.bodyFatPercentageDeleteSuccess());
  } else {
    yield put(BodyFatPercentageActions.bodyFatPercentageDeleteFailure(response.data));
  }
}
function mapDateFields(data) {
  data.endTime = convertDateTimeFromServer(data.endTime);
  return data;
}

export default {
  getAllBodyFatPercentages,
  getBodyFatPercentage,
  deleteBodyFatPercentage,
  updateBodyFatPercentage,
};
