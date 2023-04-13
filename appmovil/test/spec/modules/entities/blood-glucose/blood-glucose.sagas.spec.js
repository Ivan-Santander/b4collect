import { put } from 'redux-saga/effects';

import FixtureAPI from '../../../../../app/shared/services/fixture-api';
import BloodGlucoseSagas from '../../../../../app/modules/entities/blood-glucose/blood-glucose.sagas';
import BloodGlucoseActions from '../../../../../app/modules/entities/blood-glucose/blood-glucose.reducer';

const { getBloodGlucose, getAllBloodGlucoses, updateBloodGlucose, deleteBloodGlucose } = BloodGlucoseSagas;
const stepper = (fn) => (mock) => fn.next(mock).value;

test('get success path', () => {
  const response = FixtureAPI.getBloodGlucose(1);
  const step = stepper(getBloodGlucose(FixtureAPI, { bloodGlucoseId: { id: 1 } }));
  // Step 1: Hit the api
  step();
  // Step 2: Successful return and data!
  expect(step(response)).toEqual(put(BloodGlucoseActions.bloodGlucoseSuccess(response.data)));
});

test('get failure path', () => {
  const response = { ok: false };
  const step = stepper(getBloodGlucose(FixtureAPI, { bloodGlucoseId: { id: 1 } }));
  // Step 1: Hit the api
  step();
  // Step 2: Failed response.
  expect(step(response)).toEqual(put(BloodGlucoseActions.bloodGlucoseFailure()));
});

test('getAll success path', () => {
  const response = FixtureAPI.getAllBloodGlucoses();
  const step = stepper(getAllBloodGlucoses(FixtureAPI, { options: { page: 0, sort: 'id,asc', size: 20 } }));
  // Step 1: Hit the api
  step();
  // Step 2: Successful return and data!
  expect(step(response)).toEqual(put(BloodGlucoseActions.bloodGlucoseAllSuccess([{ id: 1 }, { id: 2 }])));
});

test('getAll failure path', () => {
  const response = { ok: false };
  const step = stepper(getAllBloodGlucoses(FixtureAPI, { options: { page: 0, sort: 'id,asc', size: 20 } }));
  // Step 1: Hit the api
  step();
  // Step 2: Failed response.
  expect(step(response)).toEqual(put(BloodGlucoseActions.bloodGlucoseAllFailure()));
});

test('update success path', () => {
  const response = FixtureAPI.updateBloodGlucose({ id: 1 });
  const step = stepper(updateBloodGlucose(FixtureAPI, { bloodGlucose: { id: 1 } }));
  // Step 1: Hit the api
  step();
  // Step 2: Successful return and data!
  expect(step(response)).toEqual(put(BloodGlucoseActions.bloodGlucoseUpdateSuccess(response.data)));
});

test('update failure path', () => {
  const response = { ok: false };
  const step = stepper(updateBloodGlucose(FixtureAPI, { bloodGlucose: { id: 1 } }));
  // Step 1: Hit the api
  step();
  // Step 2: Failed response.
  expect(step(response)).toEqual(put(BloodGlucoseActions.bloodGlucoseUpdateFailure()));
});

test('delete success path', () => {
  const response = FixtureAPI.deleteBloodGlucose({ id: 1 });
  const step = stepper(deleteBloodGlucose(FixtureAPI, { bloodGlucoseId: { id: 1 } }));
  // Step 1: Hit the api
  step();
  // Step 2: Successful return and data!
  expect(step(response)).toEqual(put(BloodGlucoseActions.bloodGlucoseDeleteSuccess(response.data)));
});

test('delete failure path', () => {
  const response = { ok: false };
  const step = stepper(deleteBloodGlucose(FixtureAPI, { bloodGlucoseId: { id: 1 } }));
  // Step 1: Hit the api
  step();
  // Step 2: Failed response.
  expect(step(response)).toEqual(put(BloodGlucoseActions.bloodGlucoseDeleteFailure()));
});
