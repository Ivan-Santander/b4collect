import { put } from 'redux-saga/effects';

import FixtureAPI from '../../../../../app/shared/services/fixture-api';
import StepCountCadenceSagas from '../../../../../app/modules/entities/step-count-cadence/step-count-cadence.sagas';
import StepCountCadenceActions from '../../../../../app/modules/entities/step-count-cadence/step-count-cadence.reducer';

const { getStepCountCadence, getAllStepCountCadences, updateStepCountCadence, deleteStepCountCadence } = StepCountCadenceSagas;
const stepper = (fn) => (mock) => fn.next(mock).value;

test('get success path', () => {
  const response = FixtureAPI.getStepCountCadence(1);
  const step = stepper(getStepCountCadence(FixtureAPI, { stepCountCadenceId: { id: 1 } }));
  // Step 1: Hit the api
  step();
  // Step 2: Successful return and data!
  expect(step(response)).toEqual(put(StepCountCadenceActions.stepCountCadenceSuccess(response.data)));
});

test('get failure path', () => {
  const response = { ok: false };
  const step = stepper(getStepCountCadence(FixtureAPI, { stepCountCadenceId: { id: 1 } }));
  // Step 1: Hit the api
  step();
  // Step 2: Failed response.
  expect(step(response)).toEqual(put(StepCountCadenceActions.stepCountCadenceFailure()));
});

test('getAll success path', () => {
  const response = FixtureAPI.getAllStepCountCadences();
  const step = stepper(getAllStepCountCadences(FixtureAPI, { options: { page: 0, sort: 'id,asc', size: 20 } }));
  // Step 1: Hit the api
  step();
  // Step 2: Successful return and data!
  expect(step(response)).toEqual(put(StepCountCadenceActions.stepCountCadenceAllSuccess([{ id: 1 }, { id: 2 }])));
});

test('getAll failure path', () => {
  const response = { ok: false };
  const step = stepper(getAllStepCountCadences(FixtureAPI, { options: { page: 0, sort: 'id,asc', size: 20 } }));
  // Step 1: Hit the api
  step();
  // Step 2: Failed response.
  expect(step(response)).toEqual(put(StepCountCadenceActions.stepCountCadenceAllFailure()));
});

test('update success path', () => {
  const response = FixtureAPI.updateStepCountCadence({ id: 1 });
  const step = stepper(updateStepCountCadence(FixtureAPI, { stepCountCadence: { id: 1 } }));
  // Step 1: Hit the api
  step();
  // Step 2: Successful return and data!
  expect(step(response)).toEqual(put(StepCountCadenceActions.stepCountCadenceUpdateSuccess(response.data)));
});

test('update failure path', () => {
  const response = { ok: false };
  const step = stepper(updateStepCountCadence(FixtureAPI, { stepCountCadence: { id: 1 } }));
  // Step 1: Hit the api
  step();
  // Step 2: Failed response.
  expect(step(response)).toEqual(put(StepCountCadenceActions.stepCountCadenceUpdateFailure()));
});

test('delete success path', () => {
  const response = FixtureAPI.deleteStepCountCadence({ id: 1 });
  const step = stepper(deleteStepCountCadence(FixtureAPI, { stepCountCadenceId: { id: 1 } }));
  // Step 1: Hit the api
  step();
  // Step 2: Successful return and data!
  expect(step(response)).toEqual(put(StepCountCadenceActions.stepCountCadenceDeleteSuccess(response.data)));
});

test('delete failure path', () => {
  const response = { ok: false };
  const step = stepper(deleteStepCountCadence(FixtureAPI, { stepCountCadenceId: { id: 1 } }));
  // Step 1: Hit the api
  step();
  // Step 2: Failed response.
  expect(step(response)).toEqual(put(StepCountCadenceActions.stepCountCadenceDeleteFailure()));
});
