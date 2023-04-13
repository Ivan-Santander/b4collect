import { call, put } from 'redux-saga/effects';
import { callApi } from '../../../shared/sagas/call-api.saga';
import BloodGlucoseActions from './blood-glucose.reducer';
import { convertDateTimeFromServer } from '../../../shared/util/date-transforms';

function* getBloodGlucose(api, action) {
  const { bloodGlucoseId } = action;
  // make the call to the api
  const apiCall = call(api.getBloodGlucose, bloodGlucoseId);
  const response = yield call(callApi, apiCall);

  // success?
  if (response.ok) {
    response.data = mapDateFields(response.data);
    yield put(BloodGlucoseActions.bloodGlucoseSuccess(response.data));
  } else {
    yield put(BloodGlucoseActions.bloodGlucoseFailure(response.data));
  }
}

function* getAllBloodGlucoses(api, action) {
  const { options } = action;
  // make the call to the api
  const apiCall = call(api.getAllBloodGlucoses, options);
  const response = yield call(callApi, apiCall);

  // success?
  if (response.ok) {
    yield put(BloodGlucoseActions.bloodGlucoseAllSuccess(response.data, response.headers));
  } else {
    yield put(BloodGlucoseActions.bloodGlucoseAllFailure(response.data));
  }
}

function* updateBloodGlucose(api, action) {
  const { bloodGlucose } = action;
  // make the call to the api
  const idIsNotNull = !(bloodGlucose.id === null || bloodGlucose.id === undefined);
  const apiCall = call(idIsNotNull ? api.updateBloodGlucose : api.createBloodGlucose, bloodGlucose);
  const response = yield call(callApi, apiCall);

  // success?
  if (response.ok) {
    response.data = mapDateFields(response.data);
    yield put(BloodGlucoseActions.bloodGlucoseUpdateSuccess(response.data));
  } else {
    yield put(BloodGlucoseActions.bloodGlucoseUpdateFailure(response.data));
  }
}

function* deleteBloodGlucose(api, action) {
  const { bloodGlucoseId } = action;
  // make the call to the api
  const apiCall = call(api.deleteBloodGlucose, bloodGlucoseId);
  const response = yield call(callApi, apiCall);

  // success?
  if (response.ok) {
    yield put(BloodGlucoseActions.bloodGlucoseDeleteSuccess());
  } else {
    yield put(BloodGlucoseActions.bloodGlucoseDeleteFailure(response.data));
  }
}
function mapDateFields(data) {
  data.endTime = convertDateTimeFromServer(data.endTime);
  return data;
}

export default {
  getAllBloodGlucoses,
  getBloodGlucose,
  deleteBloodGlucose,
  updateBloodGlucose,
};
