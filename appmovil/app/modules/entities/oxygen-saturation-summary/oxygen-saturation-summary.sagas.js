import { call, put } from 'redux-saga/effects';
import { callApi } from '../../../shared/sagas/call-api.saga';
import OxygenSaturationSummaryActions from './oxygen-saturation-summary.reducer';
import { convertDateTimeFromServer } from '../../../shared/util/date-transforms';

function* getOxygenSaturationSummary(api, action) {
  const { oxygenSaturationSummaryId } = action;
  // make the call to the api
  const apiCall = call(api.getOxygenSaturationSummary, oxygenSaturationSummaryId);
  const response = yield call(callApi, apiCall);

  // success?
  if (response.ok) {
    response.data = mapDateFields(response.data);
    yield put(OxygenSaturationSummaryActions.oxygenSaturationSummarySuccess(response.data));
  } else {
    yield put(OxygenSaturationSummaryActions.oxygenSaturationSummaryFailure(response.data));
  }
}

function* getAllOxygenSaturationSummaries(api, action) {
  const { options } = action;
  // make the call to the api
  const apiCall = call(api.getAllOxygenSaturationSummaries, options);
  const response = yield call(callApi, apiCall);

  // success?
  if (response.ok) {
    yield put(OxygenSaturationSummaryActions.oxygenSaturationSummaryAllSuccess(response.data, response.headers));
  } else {
    yield put(OxygenSaturationSummaryActions.oxygenSaturationSummaryAllFailure(response.data));
  }
}

function* updateOxygenSaturationSummary(api, action) {
  const { oxygenSaturationSummary } = action;
  // make the call to the api
  const idIsNotNull = !(oxygenSaturationSummary.id === null || oxygenSaturationSummary.id === undefined);
  const apiCall = call(idIsNotNull ? api.updateOxygenSaturationSummary : api.createOxygenSaturationSummary, oxygenSaturationSummary);
  const response = yield call(callApi, apiCall);

  // success?
  if (response.ok) {
    response.data = mapDateFields(response.data);
    yield put(OxygenSaturationSummaryActions.oxygenSaturationSummaryUpdateSuccess(response.data));
  } else {
    yield put(OxygenSaturationSummaryActions.oxygenSaturationSummaryUpdateFailure(response.data));
  }
}

function* deleteOxygenSaturationSummary(api, action) {
  const { oxygenSaturationSummaryId } = action;
  // make the call to the api
  const apiCall = call(api.deleteOxygenSaturationSummary, oxygenSaturationSummaryId);
  const response = yield call(callApi, apiCall);

  // success?
  if (response.ok) {
    yield put(OxygenSaturationSummaryActions.oxygenSaturationSummaryDeleteSuccess());
  } else {
    yield put(OxygenSaturationSummaryActions.oxygenSaturationSummaryDeleteFailure(response.data));
  }
}
function mapDateFields(data) {
  data.endTime = convertDateTimeFromServer(data.endTime);
  return data;
}

export default {
  getAllOxygenSaturationSummaries,
  getOxygenSaturationSummary,
  deleteOxygenSaturationSummary,
  updateOxygenSaturationSummary,
};
