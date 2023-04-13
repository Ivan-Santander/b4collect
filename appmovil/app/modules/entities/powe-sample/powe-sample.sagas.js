import { call, put } from 'redux-saga/effects';
import { callApi } from '../../../shared/sagas/call-api.saga';
import PoweSampleActions from './powe-sample.reducer';
import { convertDateTimeFromServer } from '../../../shared/util/date-transforms';

function* getPoweSample(api, action) {
  const { poweSampleId } = action;
  // make the call to the api
  const apiCall = call(api.getPoweSample, poweSampleId);
  const response = yield call(callApi, apiCall);

  // success?
  if (response.ok) {
    response.data = mapDateFields(response.data);
    yield put(PoweSampleActions.poweSampleSuccess(response.data));
  } else {
    yield put(PoweSampleActions.poweSampleFailure(response.data));
  }
}

function* getAllPoweSamples(api, action) {
  const { options } = action;
  // make the call to the api
  const apiCall = call(api.getAllPoweSamples, options);
  const response = yield call(callApi, apiCall);

  // success?
  if (response.ok) {
    yield put(PoweSampleActions.poweSampleAllSuccess(response.data, response.headers));
  } else {
    yield put(PoweSampleActions.poweSampleAllFailure(response.data));
  }
}

function* updatePoweSample(api, action) {
  const { poweSample } = action;
  // make the call to the api
  const idIsNotNull = !(poweSample.id === null || poweSample.id === undefined);
  const apiCall = call(idIsNotNull ? api.updatePoweSample : api.createPoweSample, poweSample);
  const response = yield call(callApi, apiCall);

  // success?
  if (response.ok) {
    response.data = mapDateFields(response.data);
    yield put(PoweSampleActions.poweSampleUpdateSuccess(response.data));
  } else {
    yield put(PoweSampleActions.poweSampleUpdateFailure(response.data));
  }
}

function* deletePoweSample(api, action) {
  const { poweSampleId } = action;
  // make the call to the api
  const apiCall = call(api.deletePoweSample, poweSampleId);
  const response = yield call(callApi, apiCall);

  // success?
  if (response.ok) {
    yield put(PoweSampleActions.poweSampleDeleteSuccess());
  } else {
    yield put(PoweSampleActions.poweSampleDeleteFailure(response.data));
  }
}
function mapDateFields(data) {
  data.startTime = convertDateTimeFromServer(data.startTime);
  data.endTime = convertDateTimeFromServer(data.endTime);
  return data;
}

export default {
  getAllPoweSamples,
  getPoweSample,
  deletePoweSample,
  updatePoweSample,
};
