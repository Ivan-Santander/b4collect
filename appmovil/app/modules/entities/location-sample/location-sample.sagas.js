import { call, put } from 'redux-saga/effects';
import { callApi } from '../../../shared/sagas/call-api.saga';
import LocationSampleActions from './location-sample.reducer';
import { convertDateTimeFromServer } from '../../../shared/util/date-transforms';

function* getLocationSample(api, action) {
  const { locationSampleId } = action;
  // make the call to the api
  const apiCall = call(api.getLocationSample, locationSampleId);
  const response = yield call(callApi, apiCall);

  // success?
  if (response.ok) {
    response.data = mapDateFields(response.data);
    yield put(LocationSampleActions.locationSampleSuccess(response.data));
  } else {
    yield put(LocationSampleActions.locationSampleFailure(response.data));
  }
}

function* getAllLocationSamples(api, action) {
  const { options } = action;
  // make the call to the api
  const apiCall = call(api.getAllLocationSamples, options);
  const response = yield call(callApi, apiCall);

  // success?
  if (response.ok) {
    yield put(LocationSampleActions.locationSampleAllSuccess(response.data, response.headers));
  } else {
    yield put(LocationSampleActions.locationSampleAllFailure(response.data));
  }
}

function* updateLocationSample(api, action) {
  const { locationSample } = action;
  // make the call to the api
  const idIsNotNull = !(locationSample.id === null || locationSample.id === undefined);
  const apiCall = call(idIsNotNull ? api.updateLocationSample : api.createLocationSample, locationSample);
  const response = yield call(callApi, apiCall);

  // success?
  if (response.ok) {
    response.data = mapDateFields(response.data);
    yield put(LocationSampleActions.locationSampleUpdateSuccess(response.data));
  } else {
    yield put(LocationSampleActions.locationSampleUpdateFailure(response.data));
  }
}

function* deleteLocationSample(api, action) {
  const { locationSampleId } = action;
  // make the call to the api
  const apiCall = call(api.deleteLocationSample, locationSampleId);
  const response = yield call(callApi, apiCall);

  // success?
  if (response.ok) {
    yield put(LocationSampleActions.locationSampleDeleteSuccess());
  } else {
    yield put(LocationSampleActions.locationSampleDeleteFailure(response.data));
  }
}
function mapDateFields(data) {
  data.startTime = convertDateTimeFromServer(data.startTime);
  data.endTime = convertDateTimeFromServer(data.endTime);
  return data;
}

export default {
  getAllLocationSamples,
  getLocationSample,
  deleteLocationSample,
  updateLocationSample,
};
