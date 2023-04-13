import { put } from 'redux-saga/effects';

import FixtureAPI from '../../../../../app/shared/services/fixture-api';
import CiclingPedalingCadenceSagas from '../../../../../app/modules/entities/cicling-pedaling-cadence/cicling-pedaling-cadence.sagas';
import CiclingPedalingCadenceActions from '../../../../../app/modules/entities/cicling-pedaling-cadence/cicling-pedaling-cadence.reducer';

const { getCiclingPedalingCadence, getAllCiclingPedalingCadences, updateCiclingPedalingCadence, deleteCiclingPedalingCadence } =
  CiclingPedalingCadenceSagas;
const stepper = (fn) => (mock) => fn.next(mock).value;

test('get success path', () => {
  const response = FixtureAPI.getCiclingPedalingCadence(1);
  const step = stepper(getCiclingPedalingCadence(FixtureAPI, { ciclingPedalingCadenceId: { id: 1 } }));
  // Step 1: Hit the api
  step();
  // Step 2: Successful return and data!
  expect(step(response)).toEqual(put(CiclingPedalingCadenceActions.ciclingPedalingCadenceSuccess(response.data)));
});

test('get failure path', () => {
  const response = { ok: false };
  const step = stepper(getCiclingPedalingCadence(FixtureAPI, { ciclingPedalingCadenceId: { id: 1 } }));
  // Step 1: Hit the api
  step();
  // Step 2: Failed response.
  expect(step(response)).toEqual(put(CiclingPedalingCadenceActions.ciclingPedalingCadenceFailure()));
});

test('getAll success path', () => {
  const response = FixtureAPI.getAllCiclingPedalingCadences();
  const step = stepper(getAllCiclingPedalingCadences(FixtureAPI, { options: { page: 0, sort: 'id,asc', size: 20 } }));
  // Step 1: Hit the api
  step();
  // Step 2: Successful return and data!
  expect(step(response)).toEqual(put(CiclingPedalingCadenceActions.ciclingPedalingCadenceAllSuccess([{ id: 1 }, { id: 2 }])));
});

test('getAll failure path', () => {
  const response = { ok: false };
  const step = stepper(getAllCiclingPedalingCadences(FixtureAPI, { options: { page: 0, sort: 'id,asc', size: 20 } }));
  // Step 1: Hit the api
  step();
  // Step 2: Failed response.
  expect(step(response)).toEqual(put(CiclingPedalingCadenceActions.ciclingPedalingCadenceAllFailure()));
});

test('update success path', () => {
  const response = FixtureAPI.updateCiclingPedalingCadence({ id: 1 });
  const step = stepper(updateCiclingPedalingCadence(FixtureAPI, { ciclingPedalingCadence: { id: 1 } }));
  // Step 1: Hit the api
  step();
  // Step 2: Successful return and data!
  expect(step(response)).toEqual(put(CiclingPedalingCadenceActions.ciclingPedalingCadenceUpdateSuccess(response.data)));
});

test('update failure path', () => {
  const response = { ok: false };
  const step = stepper(updateCiclingPedalingCadence(FixtureAPI, { ciclingPedalingCadence: { id: 1 } }));
  // Step 1: Hit the api
  step();
  // Step 2: Failed response.
  expect(step(response)).toEqual(put(CiclingPedalingCadenceActions.ciclingPedalingCadenceUpdateFailure()));
});

test('delete success path', () => {
  const response = FixtureAPI.deleteCiclingPedalingCadence({ id: 1 });
  const step = stepper(deleteCiclingPedalingCadence(FixtureAPI, { ciclingPedalingCadenceId: { id: 1 } }));
  // Step 1: Hit the api
  step();
  // Step 2: Successful return and data!
  expect(step(response)).toEqual(put(CiclingPedalingCadenceActions.ciclingPedalingCadenceDeleteSuccess(response.data)));
});

test('delete failure path', () => {
  const response = { ok: false };
  const step = stepper(deleteCiclingPedalingCadence(FixtureAPI, { ciclingPedalingCadenceId: { id: 1 } }));
  // Step 1: Hit the api
  step();
  // Step 2: Failed response.
  expect(step(response)).toEqual(put(CiclingPedalingCadenceActions.ciclingPedalingCadenceDeleteFailure()));
});
