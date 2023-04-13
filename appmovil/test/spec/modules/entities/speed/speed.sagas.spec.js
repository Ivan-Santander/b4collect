import { put } from 'redux-saga/effects';

import FixtureAPI from '../../../../../app/shared/services/fixture-api';
import SpeedSagas from '../../../../../app/modules/entities/speed/speed.sagas';
import SpeedActions from '../../../../../app/modules/entities/speed/speed.reducer';

const { getSpeed, getAllSpeeds, updateSpeed, deleteSpeed } = SpeedSagas;
const stepper = (fn) => (mock) => fn.next(mock).value;

test('get success path', () => {
  const response = FixtureAPI.getSpeed(1);
  const step = stepper(getSpeed(FixtureAPI, { speedId: { id: 1 } }));
  // Step 1: Hit the api
  step();
  // Step 2: Successful return and data!
  expect(step(response)).toEqual(put(SpeedActions.speedSuccess(response.data)));
});

test('get failure path', () => {
  const response = { ok: false };
  const step = stepper(getSpeed(FixtureAPI, { speedId: { id: 1 } }));
  // Step 1: Hit the api
  step();
  // Step 2: Failed response.
  expect(step(response)).toEqual(put(SpeedActions.speedFailure()));
});

test('getAll success path', () => {
  const response = FixtureAPI.getAllSpeeds();
  const step = stepper(getAllSpeeds(FixtureAPI, { options: { page: 0, sort: 'id,asc', size: 20 } }));
  // Step 1: Hit the api
  step();
  // Step 2: Successful return and data!
  expect(step(response)).toEqual(put(SpeedActions.speedAllSuccess([{ id: 1 }, { id: 2 }])));
});

test('getAll failure path', () => {
  const response = { ok: false };
  const step = stepper(getAllSpeeds(FixtureAPI, { options: { page: 0, sort: 'id,asc', size: 20 } }));
  // Step 1: Hit the api
  step();
  // Step 2: Failed response.
  expect(step(response)).toEqual(put(SpeedActions.speedAllFailure()));
});

test('update success path', () => {
  const response = FixtureAPI.updateSpeed({ id: 1 });
  const step = stepper(updateSpeed(FixtureAPI, { speed: { id: 1 } }));
  // Step 1: Hit the api
  step();
  // Step 2: Successful return and data!
  expect(step(response)).toEqual(put(SpeedActions.speedUpdateSuccess(response.data)));
});

test('update failure path', () => {
  const response = { ok: false };
  const step = stepper(updateSpeed(FixtureAPI, { speed: { id: 1 } }));
  // Step 1: Hit the api
  step();
  // Step 2: Failed response.
  expect(step(response)).toEqual(put(SpeedActions.speedUpdateFailure()));
});

test('delete success path', () => {
  const response = FixtureAPI.deleteSpeed({ id: 1 });
  const step = stepper(deleteSpeed(FixtureAPI, { speedId: { id: 1 } }));
  // Step 1: Hit the api
  step();
  // Step 2: Successful return and data!
  expect(step(response)).toEqual(put(SpeedActions.speedDeleteSuccess(response.data)));
});

test('delete failure path', () => {
  const response = { ok: false };
  const step = stepper(deleteSpeed(FixtureAPI, { speedId: { id: 1 } }));
  // Step 1: Hit the api
  step();
  // Step 2: Failed response.
  expect(step(response)).toEqual(put(SpeedActions.speedDeleteFailure()));
});
