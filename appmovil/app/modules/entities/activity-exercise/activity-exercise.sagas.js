import { call, put } from 'redux-saga/effects';
import { callApi } from '../../../shared/sagas/call-api.saga';
import ActivityExerciseActions from './activity-exercise.reducer';
import { convertDateTimeFromServer } from '../../../shared/util/date-transforms';

function* getActivityExercise(api, action) {
  const { activityExerciseId } = action;
  // make the call to the api
  const apiCall = call(api.getActivityExercise, activityExerciseId);
  const response = yield call(callApi, apiCall);

  // success?
  if (response.ok) {
    response.data = mapDateFields(response.data);
    yield put(ActivityExerciseActions.activityExerciseSuccess(response.data));
  } else {
    yield put(ActivityExerciseActions.activityExerciseFailure(response.data));
  }
}

function* getAllActivityExercises(api, action) {
  const { options } = action;
  // make the call to the api
  const apiCall = call(api.getAllActivityExercises, options);
  const response = yield call(callApi, apiCall);

  // success?
  if (response.ok) {
    yield put(ActivityExerciseActions.activityExerciseAllSuccess(response.data, response.headers));
  } else {
    yield put(ActivityExerciseActions.activityExerciseAllFailure(response.data));
  }
}

function* updateActivityExercise(api, action) {
  const { activityExercise } = action;
  // make the call to the api
  const idIsNotNull = !(activityExercise.id === null || activityExercise.id === undefined);
  const apiCall = call(idIsNotNull ? api.updateActivityExercise : api.createActivityExercise, activityExercise);
  const response = yield call(callApi, apiCall);

  // success?
  if (response.ok) {
    response.data = mapDateFields(response.data);
    yield put(ActivityExerciseActions.activityExerciseUpdateSuccess(response.data));
  } else {
    yield put(ActivityExerciseActions.activityExerciseUpdateFailure(response.data));
  }
}

function* deleteActivityExercise(api, action) {
  const { activityExerciseId } = action;
  // make the call to the api
  const apiCall = call(api.deleteActivityExercise, activityExerciseId);
  const response = yield call(callApi, apiCall);

  // success?
  if (response.ok) {
    yield put(ActivityExerciseActions.activityExerciseDeleteSuccess());
  } else {
    yield put(ActivityExerciseActions.activityExerciseDeleteFailure(response.data));
  }
}
function mapDateFields(data) {
  data.startTime = convertDateTimeFromServer(data.startTime);
  data.endTime = convertDateTimeFromServer(data.endTime);
  return data;
}

export default {
  getAllActivityExercises,
  getActivityExercise,
  deleteActivityExercise,
  updateActivityExercise,
};
