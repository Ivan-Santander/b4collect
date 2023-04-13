import { call, put } from 'redux-saga/effects';
import { callApi } from '../../../shared/sagas/call-api.saga';
import CiclingPedalingCadenceActions from './cicling-pedaling-cadence.reducer';
import { convertDateTimeFromServer } from '../../../shared/util/date-transforms';

function* getCiclingPedalingCadence(api, action) {
  const { ciclingPedalingCadenceId } = action;
  // make the call to the api
  const apiCall = call(api.getCiclingPedalingCadence, ciclingPedalingCadenceId);
  const response = yield call(callApi, apiCall);

  // success?
  if (response.ok) {
    response.data = mapDateFields(response.data);
    yield put(CiclingPedalingCadenceActions.ciclingPedalingCadenceSuccess(response.data));
  } else {
    yield put(CiclingPedalingCadenceActions.ciclingPedalingCadenceFailure(response.data));
  }
}

function* getAllCiclingPedalingCadences(api, action) {
  const { options } = action;
  // make the call to the api
  const apiCall = call(api.getAllCiclingPedalingCadences, options);
  const response = yield call(callApi, apiCall);

  // success?
  if (response.ok) {
    yield put(CiclingPedalingCadenceActions.ciclingPedalingCadenceAllSuccess(response.data, response.headers));
  } else {
    yield put(CiclingPedalingCadenceActions.ciclingPedalingCadenceAllFailure(response.data));
  }
}

function* updateCiclingPedalingCadence(api, action) {
  const { ciclingPedalingCadence } = action;
  // make the call to the api
  const idIsNotNull = !(ciclingPedalingCadence.id === null || ciclingPedalingCadence.id === undefined);
  const apiCall = call(idIsNotNull ? api.updateCiclingPedalingCadence : api.createCiclingPedalingCadence, ciclingPedalingCadence);
  const response = yield call(callApi, apiCall);

  // success?
  if (response.ok) {
    response.data = mapDateFields(response.data);
    yield put(CiclingPedalingCadenceActions.ciclingPedalingCadenceUpdateSuccess(response.data));
  } else {
    yield put(CiclingPedalingCadenceActions.ciclingPedalingCadenceUpdateFailure(response.data));
  }
}

function* deleteCiclingPedalingCadence(api, action) {
  const { ciclingPedalingCadenceId } = action;
  // make the call to the api
  const apiCall = call(api.deleteCiclingPedalingCadence, ciclingPedalingCadenceId);
  const response = yield call(callApi, apiCall);

  // success?
  if (response.ok) {
    yield put(CiclingPedalingCadenceActions.ciclingPedalingCadenceDeleteSuccess());
  } else {
    yield put(CiclingPedalingCadenceActions.ciclingPedalingCadenceDeleteFailure(response.data));
  }
}
function mapDateFields(data) {
  data.startTime = convertDateTimeFromServer(data.startTime);
  data.endTime = convertDateTimeFromServer(data.endTime);
  return data;
}

export default {
  getAllCiclingPedalingCadences,
  getCiclingPedalingCadence,
  deleteCiclingPedalingCadence,
  updateCiclingPedalingCadence,
};
