import { put } from 'redux-saga/effects';

import FixtureAPI from '../../../../../app/shared/services/fixture-api';
import PoweSampleSagas from '../../../../../app/modules/entities/powe-sample/powe-sample.sagas';
import PoweSampleActions from '../../../../../app/modules/entities/powe-sample/powe-sample.reducer';

const { getPoweSample, getAllPoweSamples, updatePoweSample, deletePoweSample } = PoweSampleSagas;
const stepper = (fn) => (mock) => fn.next(mock).value;

test('get success path', () => {
  const response = FixtureAPI.getPoweSample(1);
  const step = stepper(getPoweSample(FixtureAPI, { poweSampleId: { id: 1 } }));
  // Step 1: Hit the api
  step();
  // Step 2: Successful return and data!
  expect(step(response)).toEqual(put(PoweSampleActions.poweSampleSuccess(response.data)));
});

test('get failure path', () => {
  const response = { ok: false };
  const step = stepper(getPoweSample(FixtureAPI, { poweSampleId: { id: 1 } }));
  // Step 1: Hit the api
  step();
  // Step 2: Failed response.
  expect(step(response)).toEqual(put(PoweSampleActions.poweSampleFailure()));
});

test('getAll success path', () => {
  const response = FixtureAPI.getAllPoweSamples();
  const step = stepper(getAllPoweSamples(FixtureAPI, { options: { page: 0, sort: 'id,asc', size: 20 } }));
  // Step 1: Hit the api
  step();
  // Step 2: Successful return and data!
  expect(step(response)).toEqual(put(PoweSampleActions.poweSampleAllSuccess([{ id: 1 }, { id: 2 }])));
});

test('getAll failure path', () => {
  const response = { ok: false };
  const step = stepper(getAllPoweSamples(FixtureAPI, { options: { page: 0, sort: 'id,asc', size: 20 } }));
  // Step 1: Hit the api
  step();
  // Step 2: Failed response.
  expect(step(response)).toEqual(put(PoweSampleActions.poweSampleAllFailure()));
});

test('update success path', () => {
  const response = FixtureAPI.updatePoweSample({ id: 1 });
  const step = stepper(updatePoweSample(FixtureAPI, { poweSample: { id: 1 } }));
  // Step 1: Hit the api
  step();
  // Step 2: Successful return and data!
  expect(step(response)).toEqual(put(PoweSampleActions.poweSampleUpdateSuccess(response.data)));
});

test('update failure path', () => {
  const response = { ok: false };
  const step = stepper(updatePoweSample(FixtureAPI, { poweSample: { id: 1 } }));
  // Step 1: Hit the api
  step();
  // Step 2: Failed response.
  expect(step(response)).toEqual(put(PoweSampleActions.poweSampleUpdateFailure()));
});

test('delete success path', () => {
  const response = FixtureAPI.deletePoweSample({ id: 1 });
  const step = stepper(deletePoweSample(FixtureAPI, { poweSampleId: { id: 1 } }));
  // Step 1: Hit the api
  step();
  // Step 2: Successful return and data!
  expect(step(response)).toEqual(put(PoweSampleActions.poweSampleDeleteSuccess(response.data)));
});

test('delete failure path', () => {
  const response = { ok: false };
  const step = stepper(deletePoweSample(FixtureAPI, { poweSampleId: { id: 1 } }));
  // Step 1: Hit the api
  step();
  // Step 2: Failed response.
  expect(step(response)).toEqual(put(PoweSampleActions.poweSampleDeleteFailure()));
});
