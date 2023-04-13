import { call, put } from 'redux-saga/effects';
import { callApi } from '../../../shared/sagas/call-api.saga';
import ActiveMinutesActions from './active-minutes.reducer';
import { convertDateTimeFromServer } from '../../../shared/util/date-transforms';

function* getActiveMinutes(api, action) {
  const { activeMinutesId } = action;
  // make the call to the api
  const apiCall = call(api.getActiveMinutes, activeMinutesId);
  const response = yield call(callApi, apiCall);

  // success?
  if (response.ok) {
    response.data = mapDateFields(response.data);
    yield put(ActiveMinutesActions.activeMinutesSuccess(response.data));
  } else {
    yield put(ActiveMinutesActions.activeMinutesFailure(response.data));
  }
}

function* getAllActiveMinutes(api, action) {
  const { options } = action;
  // make the call to the api
  const apiCall = call(api.getAllActiveMinutes, options);
  const response = yield call(callApi, apiCall);

  // success?
  if (response.ok) {
    yield put(ActiveMinutesActions.activeMinutesAllSuccess(response.data, response.headers));
  } else {
    yield put(ActiveMinutesActions.activeMinutesAllFailure(response.data));
  }
}

function* updateActiveMinutes(api, action) {
  const { activeMinutes } = action;
  // make the call to the api
  const idIsNotNull = !(activeMinutes.id === null || activeMinutes.id === undefined);
  const apiCall = call(idIsNotNull ? api.updateActiveMinutes : api.createActiveMinutes, activeMinutes);
  const response = yield call(callApi, apiCall);

  // success?
  if (response.ok) {
    response.data = mapDateFields(response.data);
    yield put(ActiveMinutesActions.activeMinutesUpdateSuccess(response.data));
  } else {
    yield put(ActiveMinutesActions.activeMinutesUpdateFailure(response.data));
  }
}

function* deleteActiveMinutes(api, action) {
  const { activeMinutesId } = action;
  // make the call to the api
  const apiCall = call(api.deleteActiveMinutes, activeMinutesId);
  const response = yield call(callApi, apiCall);

  // success?
  if (response.ok) {
    yield put(ActiveMinutesActions.activeMinutesDeleteSuccess());
  } else {
    yield put(ActiveMinutesActions.activeMinutesDeleteFailure(response.data));
  }
}
function mapDateFields(data) {
  data.startTime = convertDateTimeFromServer(data.startTime);
  data.endTime = convertDateTimeFromServer(data.endTime);
  return data;
}

export default {
  getAllActiveMinutes,
  getActiveMinutes,
  deleteActiveMinutes,
  updateActiveMinutes,
};
