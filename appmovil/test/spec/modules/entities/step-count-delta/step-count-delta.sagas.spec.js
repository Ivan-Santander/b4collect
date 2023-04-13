import { put } from 'redux-saga/effects';

import FixtureAPI from '../../../../../app/shared/services/fixture-api';
import StepCountDeltaSagas from '../../../../../app/modules/entities/step-count-delta/step-count-delta.sagas';
import StepCountDeltaActions from '../../../../../app/modules/entities/step-count-delta/step-count-delta.reducer';

const { getStepCountDelta, getAllStepCountDeltas, updateStepCountDelta, deleteStepCountDelta } = StepCountDeltaSagas;
const stepper = (fn) => (mock) => fn.next(mock).value;

test('get success path', () => {
  const response = FixtureAPI.getStepCountDelta(1);
  const step = stepper(getStepCountDelta(FixtureAPI, { stepCountDeltaId: { id: 1 } }));
  // Step 1: Hit the api
  step();
  // Step 2: Successful return and data!
  expect(step(response)).toEqual(put(StepCountDeltaActions.stepCountDeltaSuccess(response.data)));
});

test('get failure path', () => {
  const response = { ok: false };
  const step = stepper(getStepCountDelta(FixtureAPI, { stepCountDeltaId: { id: 1 } }));
  // Step 1: Hit the api
  step();
  // Step 2: Failed response.
  expect(step(response)).toEqual(put(StepCountDeltaActions.stepCountDeltaFailure()));
});

test('getAll success path', () => {
  const response = FixtureAPI.getAllStepCountDeltas();
  const step = stepper(getAllStepCountDeltas(FixtureAPI, { options: { page: 0, sort: 'id,asc', size: 20 } }));
  // Step 1: Hit the api
  step();
  // Step 2: Successful return and data!
  expect(step(response)).toEqual(put(StepCountDeltaActions.stepCountDeltaAllSuccess([{ id: 1 }, { id: 2 }])));
});

test('getAll failure path', () => {
  const response = { ok: false };
  const step = stepper(getAllStepCountDeltas(FixtureAPI, { options: { page: 0, sort: 'id,asc', size: 20 } }));
  // Step 1: Hit the api
  step();
  // Step 2: Failed response.
  expect(step(response)).toEqual(put(StepCountDeltaActions.stepCountDeltaAllFailure()));
});

test('update success path', () => {
  const response = FixtureAPI.updateStepCountDelta({ id: 1 });
  const step = stepper(updateStepCountDelta(FixtureAPI, { stepCountDelta: { id: 1 } }));
  // Step 1: Hit the api
  step();
  // Step 2: Successful return and data!
  expect(step(response)).toEqual(put(StepCountDeltaActions.stepCountDeltaUpdateSuccess(response.data)));
});

test('update failure path', () => {
  const response = { ok: false };
  const step = stepper(updateStepCountDelta(FixtureAPI, { stepCountDelta: { id: 1 } }));
  // Step 1: Hit the api
  step();
  // Step 2: Failed response.
  expect(step(response)).toEqual(put(StepCountDeltaActions.stepCountDeltaUpdateFailure()));
});

test('delete success path', () => {
  const response = FixtureAPI.deleteStepCountDelta({ id: 1 });
  const step = stepper(deleteStepCountDelta(FixtureAPI, { stepCountDeltaId: { id: 1 } }));
  // Step 1: Hit the api
  step();
  // Step 2: Successful return and data!
  expect(step(response)).toEqual(put(StepCountDeltaActions.stepCountDeltaDeleteSuccess(response.data)));
});

test('delete failure path', () => {
  const response = { ok: false };
  const step = stepper(deleteStepCountDelta(FixtureAPI, { stepCountDeltaId: { id: 1 } }));
  // Step 1: Hit the api
  step();
  // Step 2: Failed response.
  expect(step(response)).toEqual(put(StepCountDeltaActions.stepCountDeltaDeleteFailure()));
});
