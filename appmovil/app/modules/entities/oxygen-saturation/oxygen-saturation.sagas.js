import { call, put } from 'redux-saga/effects';
import { callApi } from '../../../shared/sagas/call-api.saga';
import OxygenSaturationActions from './oxygen-saturation.reducer';
import { convertDateTimeFromServer } from '../../../shared/util/date-transforms';

function* getOxygenSaturation(api, action) {
  const { oxygenSaturationId } = action;
  // make the call to the api
  const apiCall = call(api.getOxygenSaturation, oxygenSaturationId);
  const response = yield call(callApi, apiCall);

  // success?
  if (response.ok) {
    response.data = mapDateFields(response.data);
    yield put(OxygenSaturationActions.oxygenSaturationSuccess(response.data));
  } else {
    yield put(OxygenSaturationActions.oxygenSaturationFailure(response.data));
  }
}

function* getAllOxygenSaturations(api, action) {
  const { options } = action;
  // make the call to the api
  const apiCall = call(api.getAllOxygenSaturations, options);
  const response = yield call(callApi, apiCall);

  // success?
  if (response.ok) {
    yield put(OxygenSaturationActions.oxygenSaturationAllSuccess(response.data, response.headers));
  } else {
    yield put(OxygenSaturationActions.oxygenSaturationAllFailure(response.data));
  }
}

function* updateOxygenSaturation(api, action) {
  const { oxygenSaturation } = action;
  // make the call to the api
  const idIsNotNull = !(oxygenSaturation.id === null || oxygenSaturation.id === undefined);
  const apiCall = call(idIsNotNull ? api.updateOxygenSaturation : api.createOxygenSaturation, oxygenSaturation);
  const response = yield call(callApi, apiCall);

  // success?
  if (response.ok) {
    response.data = mapDateFields(response.data);
    yield put(OxygenSaturationActions.oxygenSaturationUpdateSuccess(response.data));
  } else {
    yield put(OxygenSaturationActions.oxygenSaturationUpdateFailure(response.data));
  }
}

function* deleteOxygenSaturation(api, action) {
  const { oxygenSaturationId } = action;
  // make the call to the api
  const apiCall = call(api.deleteOxygenSaturation, oxygenSaturationId);
  const response = yield call(callApi, apiCall);

  // success?
  if (response.ok) {
    yield put(OxygenSaturationActions.oxygenSaturationDeleteSuccess());
  } else {
    yield put(OxygenSaturationActions.oxygenSaturationDeleteFailure(response.data));
  }
}
function mapDateFields(data) {
  data.endTime = convertDateTimeFromServer(data.endTime);
  return data;
}

export default {
  getAllOxygenSaturations,
  getOxygenSaturation,
  deleteOxygenSaturation,
  updateOxygenSaturation,
};
