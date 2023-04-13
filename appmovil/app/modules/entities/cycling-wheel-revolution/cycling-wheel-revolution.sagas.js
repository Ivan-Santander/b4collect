import { call, put } from 'redux-saga/effects';
import { callApi } from '../../../shared/sagas/call-api.saga';
import CyclingWheelRevolutionActions from './cycling-wheel-revolution.reducer';
import { convertDateTimeFromServer } from '../../../shared/util/date-transforms';

function* getCyclingWheelRevolution(api, action) {
  const { cyclingWheelRevolutionId } = action;
  // make the call to the api
  const apiCall = call(api.getCyclingWheelRevolution, cyclingWheelRevolutionId);
  const response = yield call(callApi, apiCall);

  // success?
  if (response.ok) {
    response.data = mapDateFields(response.data);
    yield put(CyclingWheelRevolutionActions.cyclingWheelRevolutionSuccess(response.data));
  } else {
    yield put(CyclingWheelRevolutionActions.cyclingWheelRevolutionFailure(response.data));
  }
}

function* getAllCyclingWheelRevolutions(api, action) {
  const { options } = action;
  // make the call to the api
  const apiCall = call(api.getAllCyclingWheelRevolutions, options);
  const response = yield call(callApi, apiCall);

  // success?
  if (response.ok) {
    yield put(CyclingWheelRevolutionActions.cyclingWheelRevolutionAllSuccess(response.data, response.headers));
  } else {
    yield put(CyclingWheelRevolutionActions.cyclingWheelRevolutionAllFailure(response.data));
  }
}

function* updateCyclingWheelRevolution(api, action) {
  const { cyclingWheelRevolution } = action;
  // make the call to the api
  const idIsNotNull = !(cyclingWheelRevolution.id === null || cyclingWheelRevolution.id === undefined);
  const apiCall = call(idIsNotNull ? api.updateCyclingWheelRevolution : api.createCyclingWheelRevolution, cyclingWheelRevolution);
  const response = yield call(callApi, apiCall);

  // success?
  if (response.ok) {
    response.data = mapDateFields(response.data);
    yield put(CyclingWheelRevolutionActions.cyclingWheelRevolutionUpdateSuccess(response.data));
  } else {
    yield put(CyclingWheelRevolutionActions.cyclingWheelRevolutionUpdateFailure(response.data));
  }
}

function* deleteCyclingWheelRevolution(api, action) {
  const { cyclingWheelRevolutionId } = action;
  // make the call to the api
  const apiCall = call(api.deleteCyclingWheelRevolution, cyclingWheelRevolutionId);
  const response = yield call(callApi, apiCall);

  // success?
  if (response.ok) {
    yield put(CyclingWheelRevolutionActions.cyclingWheelRevolutionDeleteSuccess());
  } else {
    yield put(CyclingWheelRevolutionActions.cyclingWheelRevolutionDeleteFailure(response.data));
  }
}
function mapDateFields(data) {
  data.startTime = convertDateTimeFromServer(data.startTime);
  data.endTime = convertDateTimeFromServer(data.endTime);
  return data;
}

export default {
  getAllCyclingWheelRevolutions,
  getCyclingWheelRevolution,
  deleteCyclingWheelRevolution,
  updateCyclingWheelRevolution,
};
