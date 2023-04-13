import { put } from 'redux-saga/effects';

import FixtureAPI from '../../../../../app/shared/services/fixture-api';
import CyclingWheelRevolutionSagas from '../../../../../app/modules/entities/cycling-wheel-revolution/cycling-wheel-revolution.sagas';
import CyclingWheelRevolutionActions from '../../../../../app/modules/entities/cycling-wheel-revolution/cycling-wheel-revolution.reducer';

const { getCyclingWheelRevolution, getAllCyclingWheelRevolutions, updateCyclingWheelRevolution, deleteCyclingWheelRevolution } =
  CyclingWheelRevolutionSagas;
const stepper = (fn) => (mock) => fn.next(mock).value;

test('get success path', () => {
  const response = FixtureAPI.getCyclingWheelRevolution(1);
  const step = stepper(getCyclingWheelRevolution(FixtureAPI, { cyclingWheelRevolutionId: { id: 1 } }));
  // Step 1: Hit the api
  step();
  // Step 2: Successful return and data!
  expect(step(response)).toEqual(put(CyclingWheelRevolutionActions.cyclingWheelRevolutionSuccess(response.data)));
});

test('get failure path', () => {
  const response = { ok: false };
  const step = stepper(getCyclingWheelRevolution(FixtureAPI, { cyclingWheelRevolutionId: { id: 1 } }));
  // Step 1: Hit the api
  step();
  // Step 2: Failed response.
  expect(step(response)).toEqual(put(CyclingWheelRevolutionActions.cyclingWheelRevolutionFailure()));
});

test('getAll success path', () => {
  const response = FixtureAPI.getAllCyclingWheelRevolutions();
  const step = stepper(getAllCyclingWheelRevolutions(FixtureAPI, { options: { page: 0, sort: 'id,asc', size: 20 } }));
  // Step 1: Hit the api
  step();
  // Step 2: Successful return and data!
  expect(step(response)).toEqual(put(CyclingWheelRevolutionActions.cyclingWheelRevolutionAllSuccess([{ id: 1 }, { id: 2 }])));
});

test('getAll failure path', () => {
  const response = { ok: false };
  const step = stepper(getAllCyclingWheelRevolutions(FixtureAPI, { options: { page: 0, sort: 'id,asc', size: 20 } }));
  // Step 1: Hit the api
  step();
  // Step 2: Failed response.
  expect(step(response)).toEqual(put(CyclingWheelRevolutionActions.cyclingWheelRevolutionAllFailure()));
});

test('update success path', () => {
  const response = FixtureAPI.updateCyclingWheelRevolution({ id: 1 });
  const step = stepper(updateCyclingWheelRevolution(FixtureAPI, { cyclingWheelRevolution: { id: 1 } }));
  // Step 1: Hit the api
  step();
  // Step 2: Successful return and data!
  expect(step(response)).toEqual(put(CyclingWheelRevolutionActions.cyclingWheelRevolutionUpdateSuccess(response.data)));
});

test('update failure path', () => {
  const response = { ok: false };
  const step = stepper(updateCyclingWheelRevolution(FixtureAPI, { cyclingWheelRevolution: { id: 1 } }));
  // Step 1: Hit the api
  step();
  // Step 2: Failed response.
  expect(step(response)).toEqual(put(CyclingWheelRevolutionActions.cyclingWheelRevolutionUpdateFailure()));
});

test('delete success path', () => {
  const response = FixtureAPI.deleteCyclingWheelRevolution({ id: 1 });
  const step = stepper(deleteCyclingWheelRevolution(FixtureAPI, { cyclingWheelRevolutionId: { id: 1 } }));
  // Step 1: Hit the api
  step();
  // Step 2: Successful return and data!
  expect(step(response)).toEqual(put(CyclingWheelRevolutionActions.cyclingWheelRevolutionDeleteSuccess(response.data)));
});

test('delete failure path', () => {
  const response = { ok: false };
  const step = stepper(deleteCyclingWheelRevolution(FixtureAPI, { cyclingWheelRevolutionId: { id: 1 } }));
  // Step 1: Hit the api
  step();
  // Step 2: Failed response.
  expect(step(response)).toEqual(put(CyclingWheelRevolutionActions.cyclingWheelRevolutionDeleteFailure()));
});
