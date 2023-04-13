import { put } from 'redux-saga/effects';

import FixtureAPI from '../../../../../app/shared/services/fixture-api';
import BodyTemperatureSagas from '../../../../../app/modules/entities/body-temperature/body-temperature.sagas';
import BodyTemperatureActions from '../../../../../app/modules/entities/body-temperature/body-temperature.reducer';

const { getBodyTemperature, getAllBodyTemperatures, updateBodyTemperature, deleteBodyTemperature } = BodyTemperatureSagas;
const stepper = (fn) => (mock) => fn.next(mock).value;

test('get success path', () => {
  const response = FixtureAPI.getBodyTemperature(1);
  const step = stepper(getBodyTemperature(FixtureAPI, { bodyTemperatureId: { id: 1 } }));
  // Step 1: Hit the api
  step();
  // Step 2: Successful return and data!
  expect(step(response)).toEqual(put(BodyTemperatureActions.bodyTemperatureSuccess(response.data)));
});

test('get failure path', () => {
  const response = { ok: false };
  const step = stepper(getBodyTemperature(FixtureAPI, { bodyTemperatureId: { id: 1 } }));
  // Step 1: Hit the api
  step();
  // Step 2: Failed response.
  expect(step(response)).toEqual(put(BodyTemperatureActions.bodyTemperatureFailure()));
});

test('getAll success path', () => {
  const response = FixtureAPI.getAllBodyTemperatures();
  const step = stepper(getAllBodyTemperatures(FixtureAPI, { options: { page: 0, sort: 'id,asc', size: 20 } }));
  // Step 1: Hit the api
  step();
  // Step 2: Successful return and data!
  expect(step(response)).toEqual(put(BodyTemperatureActions.bodyTemperatureAllSuccess([{ id: 1 }, { id: 2 }])));
});

test('getAll failure path', () => {
  const response = { ok: false };
  const step = stepper(getAllBodyTemperatures(FixtureAPI, { options: { page: 0, sort: 'id,asc', size: 20 } }));
  // Step 1: Hit the api
  step();
  // Step 2: Failed response.
  expect(step(response)).toEqual(put(BodyTemperatureActions.bodyTemperatureAllFailure()));
});

test('update success path', () => {
  const response = FixtureAPI.updateBodyTemperature({ id: 1 });
  const step = stepper(updateBodyTemperature(FixtureAPI, { bodyTemperature: { id: 1 } }));
  // Step 1: Hit the api
  step();
  // Step 2: Successful return and data!
  expect(step(response)).toEqual(put(BodyTemperatureActions.bodyTemperatureUpdateSuccess(response.data)));
});

test('update failure path', () => {
  const response = { ok: false };
  const step = stepper(updateBodyTemperature(FixtureAPI, { bodyTemperature: { id: 1 } }));
  // Step 1: Hit the api
  step();
  // Step 2: Failed response.
  expect(step(response)).toEqual(put(BodyTemperatureActions.bodyTemperatureUpdateFailure()));
});

test('delete success path', () => {
  const response = FixtureAPI.deleteBodyTemperature({ id: 1 });
  const step = stepper(deleteBodyTemperature(FixtureAPI, { bodyTemperatureId: { id: 1 } }));
  // Step 1: Hit the api
  step();
  // Step 2: Successful return and data!
  expect(step(response)).toEqual(put(BodyTemperatureActions.bodyTemperatureDeleteSuccess(response.data)));
});

test('delete failure path', () => {
  const response = { ok: false };
  const step = stepper(deleteBodyTemperature(FixtureAPI, { bodyTemperatureId: { id: 1 } }));
  // Step 1: Hit the api
  step();
  // Step 2: Failed response.
  expect(step(response)).toEqual(put(BodyTemperatureActions.bodyTemperatureDeleteFailure()));
});
