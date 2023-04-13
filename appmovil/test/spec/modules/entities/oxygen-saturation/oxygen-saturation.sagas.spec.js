import { put } from 'redux-saga/effects';

import FixtureAPI from '../../../../../app/shared/services/fixture-api';
import OxygenSaturationSagas from '../../../../../app/modules/entities/oxygen-saturation/oxygen-saturation.sagas';
import OxygenSaturationActions from '../../../../../app/modules/entities/oxygen-saturation/oxygen-saturation.reducer';

const { getOxygenSaturation, getAllOxygenSaturations, updateOxygenSaturation, deleteOxygenSaturation } = OxygenSaturationSagas;
const stepper = (fn) => (mock) => fn.next(mock).value;

test('get success path', () => {
  const response = FixtureAPI.getOxygenSaturation(1);
  const step = stepper(getOxygenSaturation(FixtureAPI, { oxygenSaturationId: { id: 1 } }));
  // Step 1: Hit the api
  step();
  // Step 2: Successful return and data!
  expect(step(response)).toEqual(put(OxygenSaturationActions.oxygenSaturationSuccess(response.data)));
});

test('get failure path', () => {
  const response = { ok: false };
  const step = stepper(getOxygenSaturation(FixtureAPI, { oxygenSaturationId: { id: 1 } }));
  // Step 1: Hit the api
  step();
  // Step 2: Failed response.
  expect(step(response)).toEqual(put(OxygenSaturationActions.oxygenSaturationFailure()));
});

test('getAll success path', () => {
  const response = FixtureAPI.getAllOxygenSaturations();
  const step = stepper(getAllOxygenSaturations(FixtureAPI, { options: { page: 0, sort: 'id,asc', size: 20 } }));
  // Step 1: Hit the api
  step();
  // Step 2: Successful return and data!
  expect(step(response)).toEqual(put(OxygenSaturationActions.oxygenSaturationAllSuccess([{ id: 1 }, { id: 2 }])));
});

test('getAll failure path', () => {
  const response = { ok: false };
  const step = stepper(getAllOxygenSaturations(FixtureAPI, { options: { page: 0, sort: 'id,asc', size: 20 } }));
  // Step 1: Hit the api
  step();
  // Step 2: Failed response.
  expect(step(response)).toEqual(put(OxygenSaturationActions.oxygenSaturationAllFailure()));
});

test('update success path', () => {
  const response = FixtureAPI.updateOxygenSaturation({ id: 1 });
  const step = stepper(updateOxygenSaturation(FixtureAPI, { oxygenSaturation: { id: 1 } }));
  // Step 1: Hit the api
  step();
  // Step 2: Successful return and data!
  expect(step(response)).toEqual(put(OxygenSaturationActions.oxygenSaturationUpdateSuccess(response.data)));
});

test('update failure path', () => {
  const response = { ok: false };
  const step = stepper(updateOxygenSaturation(FixtureAPI, { oxygenSaturation: { id: 1 } }));
  // Step 1: Hit the api
  step();
  // Step 2: Failed response.
  expect(step(response)).toEqual(put(OxygenSaturationActions.oxygenSaturationUpdateFailure()));
});

test('delete success path', () => {
  const response = FixtureAPI.deleteOxygenSaturation({ id: 1 });
  const step = stepper(deleteOxygenSaturation(FixtureAPI, { oxygenSaturationId: { id: 1 } }));
  // Step 1: Hit the api
  step();
  // Step 2: Successful return and data!
  expect(step(response)).toEqual(put(OxygenSaturationActions.oxygenSaturationDeleteSuccess(response.data)));
});

test('delete failure path', () => {
  const response = { ok: false };
  const step = stepper(deleteOxygenSaturation(FixtureAPI, { oxygenSaturationId: { id: 1 } }));
  // Step 1: Hit the api
  step();
  // Step 2: Failed response.
  expect(step(response)).toEqual(put(OxygenSaturationActions.oxygenSaturationDeleteFailure()));
});
