import { call, put } from 'redux-saga/effects';
import { callApi } from '../../../shared/sagas/call-api.saga';
import TemperatureSummaryActions from './temperature-summary.reducer';
import { convertDateTimeFromServer } from '../../../shared/util/date-transforms';

function* getTemperatureSummary(api, action) {
  const { temperatureSummaryId } = action;
  // make the call to the api
  const apiCall = call(api.getTemperatureSummary, temperatureSummaryId);
  const response = yield call(callApi, apiCall);

  // success?
  if (response.ok) {
    response.data = mapDateFields(response.data);
    yield put(TemperatureSummaryActions.temperatureSummarySuccess(response.data));
  } else {
    yield put(TemperatureSummaryActions.temperatureSummaryFailure(response.data));
  }
}

function* getAllTemperatureSummaries(api, action) {
  const { options } = action;
  // make the call to the api
  const apiCall = call(api.getAllTemperatureSummaries, options);
  const response = yield call(callApi, apiCall);

  // success?
  if (response.ok) {
    yield put(TemperatureSummaryActions.temperatureSummaryAllSuccess(response.data, response.headers));
  } else {
    yield put(TemperatureSummaryActions.temperatureSummaryAllFailure(response.data));
  }
}

function* updateTemperatureSummary(api, action) {
  const { temperatureSummary } = action;
  // make the call to the api
  const idIsNotNull = !(temperatureSummary.id === null || temperatureSummary.id === undefined);
  const apiCall = call(idIsNotNull ? api.updateTemperatureSummary : api.createTemperatureSummary, temperatureSummary);
  const response = yield call(callApi, apiCall);

  // success?
  if (response.ok) {
    response.data = mapDateFields(response.data);
    yield put(TemperatureSummaryActions.temperatureSummaryUpdateSuccess(response.data));
  } else {
    yield put(TemperatureSummaryActions.temperatureSummaryUpdateFailure(response.data));
  }
}

function* deleteTemperatureSummary(api, action) {
  const { temperatureSummaryId } = action;
  // make the call to the api
  const apiCall = call(api.deleteTemperatureSummary, temperatureSummaryId);
  const response = yield call(callApi, apiCall);

  // success?
  if (response.ok) {
    yield put(TemperatureSummaryActions.temperatureSummaryDeleteSuccess());
  } else {
    yield put(TemperatureSummaryActions.temperatureSummaryDeleteFailure(response.data));
  }
}
function mapDateFields(data) {
  data.startTime = convertDateTimeFromServer(data.startTime);
  data.endTime = convertDateTimeFromServer(data.endTime);
  return data;
}

export default {
  getAllTemperatureSummaries,
  getTemperatureSummary,
  deleteTemperatureSummary,
  updateTemperatureSummary,
};
