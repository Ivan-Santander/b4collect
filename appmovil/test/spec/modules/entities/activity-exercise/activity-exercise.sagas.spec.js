import { put } from 'redux-saga/effects';

import FixtureAPI from '../../../../../app/shared/services/fixture-api';
import ActivityExerciseSagas from '../../../../../app/modules/entities/activity-exercise/activity-exercise.sagas';
import ActivityExerciseActions from '../../../../../app/modules/entities/activity-exercise/activity-exercise.reducer';

const { getActivityExercise, getAllActivityExercises, updateActivityExercise, deleteActivityExercise } = ActivityExerciseSagas;
const stepper = (fn) => (mock) => fn.next(mock).value;

test('get success path', () => {
  const response = FixtureAPI.getActivityExercise(1);
  const step = stepper(getActivityExercise(FixtureAPI, { activityExerciseId: { id: 1 } }));
  // Step 1: Hit the api
  step();
  // Step 2: Successful return and data!
  expect(step(response)).toEqual(put(ActivityExerciseActions.activityExerciseSuccess(response.data)));
});

test('get failure path', () => {
  const response = { ok: false };
  const step = stepper(getActivityExercise(FixtureAPI, { activityExerciseId: { id: 1 } }));
  // Step 1: Hit the api
  step();
  // Step 2: Failed response.
  expect(step(response)).toEqual(put(ActivityExerciseActions.activityExerciseFailure()));
});

test('getAll success path', () => {
  const response = FixtureAPI.getAllActivityExercises();
  const step = stepper(getAllActivityExercises(FixtureAPI, { options: { page: 0, sort: 'id,asc', size: 20 } }));
  // Step 1: Hit the api
  step();
  // Step 2: Successful return and data!
  expect(step(response)).toEqual(put(ActivityExerciseActions.activityExerciseAllSuccess([{ id: 1 }, { id: 2 }])));
});

test('getAll failure path', () => {
  const response = { ok: false };
  const step = stepper(getAllActivityExercises(FixtureAPI, { options: { page: 0, sort: 'id,asc', size: 20 } }));
  // Step 1: Hit the api
  step();
  // Step 2: Failed response.
  expect(step(response)).toEqual(put(ActivityExerciseActions.activityExerciseAllFailure()));
});

test('update success path', () => {
  const response = FixtureAPI.updateActivityExercise({ id: 1 });
  const step = stepper(updateActivityExercise(FixtureAPI, { activityExercise: { id: 1 } }));
  // Step 1: Hit the api
  step();
  // Step 2: Successful return and data!
  expect(step(response)).toEqual(put(ActivityExerciseActions.activityExerciseUpdateSuccess(response.data)));
});

test('update failure path', () => {
  const response = { ok: false };
  const step = stepper(updateActivityExercise(FixtureAPI, { activityExercise: { id: 1 } }));
  // Step 1: Hit the api
  step();
  // Step 2: Failed response.
  expect(step(response)).toEqual(put(ActivityExerciseActions.activityExerciseUpdateFailure()));
});

test('delete success path', () => {
  const response = FixtureAPI.deleteActivityExercise({ id: 1 });
  const step = stepper(deleteActivityExercise(FixtureAPI, { activityExerciseId: { id: 1 } }));
  // Step 1: Hit the api
  step();
  // Step 2: Successful return and data!
  expect(step(response)).toEqual(put(ActivityExerciseActions.activityExerciseDeleteSuccess(response.data)));
});

test('delete failure path', () => {
  const response = { ok: false };
  const step = stepper(deleteActivityExercise(FixtureAPI, { activityExerciseId: { id: 1 } }));
  // Step 1: Hit the api
  step();
  // Step 2: Failed response.
  expect(step(response)).toEqual(put(ActivityExerciseActions.activityExerciseDeleteFailure()));
});
