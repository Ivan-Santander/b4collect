import { call, put } from 'redux-saga/effects';
import { callApi } from '../../../shared/sagas/call-api.saga';
import DistanceDeltaActions from './distance-delta.reducer';
import { convertDateTimeFromServer } from '../../../shared/util/date-transforms';

function* getDistanceDelta(api, action) {
  const { distanceDeltaId } = action;
  // make the call to the api
  const apiCall = call(api.getDistanceDelta, distanceDeltaId);
  const response = yield call(callApi, apiCall);

  // success?
  if (response.ok) {
    response.data = mapDateFields(response.data);
    yield put(DistanceDeltaActions.distanceDeltaSuccess(response.data));
  } else {
    yield put(DistanceDeltaActions.distanceDeltaFailure(response.data));
  }
}

function* getAllDistanceDeltas(api, action) {
  const { options } = action;
  // make the call to the api
  const apiCall = call(api.getAllDistanceDeltas, options);
  const response = yield call(callApi, apiCall);

  // success?
  if (response.ok) {
    yield put(DistanceDeltaActions.distanceDeltaAllSuccess(response.data, response.headers));
  } else {
    yield put(DistanceDeltaActions.distanceDeltaAllFailure(response.data));
  }
}

function* updateDistanceDelta(api, action) {
  const { distanceDelta } = action;
  // make the call to the api
  const idIsNotNull = !(distanceDelta.id === null || distanceDelta.id === undefined);
  const apiCall = call(idIsNotNull ? api.updateDistanceDelta : api.createDistanceDelta, distanceDelta);
  const response = yield call(callApi, apiCall);

  // success?
  if (response.ok) {
    response.data = mapDateFields(response.data);
    yield put(DistanceDeltaActions.distanceDeltaUpdateSuccess(response.data));
  } else {
    yield put(DistanceDeltaActions.distanceDeltaUpdateFailure(response.data));
  }
}

function* deleteDistanceDelta(api, action) {
  const { distanceDeltaId } = action;
  // make the call to the api
  const apiCall = call(api.deleteDistanceDelta, distanceDeltaId);
  const response = yield call(callApi, apiCall);

  // success?
  if (response.ok) {
    yield put(DistanceDeltaActions.distanceDeltaDeleteSuccess());
  } else {
    yield put(DistanceDeltaActions.distanceDeltaDeleteFailure(response.data));
  }
}
function mapDateFields(data) {
  data.startTime = convertDateTimeFromServer(data.startTime);
  data.endTime = convertDateTimeFromServer(data.endTime);
  return data;
}

export default {
  getAllDistanceDeltas,
  getDistanceDelta,
  deleteDistanceDelta,
  updateDistanceDelta,
};
