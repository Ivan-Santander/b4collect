import { put } from 'redux-saga/effects';

import FixtureAPI from '../../../../../app/shared/services/fixture-api';
import BloodPressureSummarySagas from '../../../../../app/modules/entities/blood-pressure-summary/blood-pressure-summary.sagas';
import BloodPressureSummaryActions from '../../../../../app/modules/entities/blood-pressure-summary/blood-pressure-summary.reducer';

const { getBloodPressureSummary, getAllBloodPressureSummaries, updateBloodPressureSummary, deleteBloodPressureSummary } =
  BloodPressureSummarySagas;
const stepper = (fn) => (mock) => fn.next(mock).value;

test('get success path', () => {
  const response = FixtureAPI.getBloodPressureSummary(1);
  const step = stepper(getBloodPressureSummary(FixtureAPI, { bloodPressureSummaryId: { id: 1 } }));
  // Step 1: Hit the api
  step();
  // Step 2: Successful return and data!
  expect(step(response)).toEqual(put(BloodPressureSummaryActions.bloodPressureSummarySuccess(response.data)));
});

test('get failure path', () => {
  const response = { ok: false };
  const step = stepper(getBloodPressureSummary(FixtureAPI, { bloodPressureSummaryId: { id: 1 } }));
  // Step 1: Hit the api
  step();
  // Step 2: Failed response.
  expect(step(response)).toEqual(put(BloodPressureSummaryActions.bloodPressureSummaryFailure()));
});

test('getAll success path', () => {
  const response = FixtureAPI.getAllBloodPressureSummaries();
  const step = stepper(getAllBloodPressureSummaries(FixtureAPI, { options: { page: 0, sort: 'id,asc', size: 20 } }));
  // Step 1: Hit the api
  step();
  // Step 2: Successful return and data!
  expect(step(response)).toEqual(put(BloodPressureSummaryActions.bloodPressureSummaryAllSuccess([{ id: 1 }, { id: 2 }])));
});

test('getAll failure path', () => {
  const response = { ok: false };
  const step = stepper(getAllBloodPressureSummaries(FixtureAPI, { options: { page: 0, sort: 'id,asc', size: 20 } }));
  // Step 1: Hit the api
  step();
  // Step 2: Failed response.
  expect(step(response)).toEqual(put(BloodPressureSummaryActions.bloodPressureSummaryAllFailure()));
});

test('update success path', () => {
  const response = FixtureAPI.updateBloodPressureSummary({ id: 1 });
  const step = stepper(updateBloodPressureSummary(FixtureAPI, { bloodPressureSummary: { id: 1 } }));
  // Step 1: Hit the api
  step();
  // Step 2: Successful return and data!
  expect(step(response)).toEqual(put(BloodPressureSummaryActions.bloodPressureSummaryUpdateSuccess(response.data)));
});

test('update failure path', () => {
  const response = { ok: false };
  const step = stepper(updateBloodPressureSummary(FixtureAPI, { bloodPressureSummary: { id: 1 } }));
  // Step 1: Hit the api
  step();
  // Step 2: Failed response.
  expect(step(response)).toEqual(put(BloodPressureSummaryActions.bloodPressureSummaryUpdateFailure()));
});

test('delete success path', () => {
  const response = FixtureAPI.deleteBloodPressureSummary({ id: 1 });
  const step = stepper(deleteBloodPressureSummary(FixtureAPI, { bloodPressureSummaryId: { id: 1 } }));
  // Step 1: Hit the api
  step();
  // Step 2: Successful return and data!
  expect(step(response)).toEqual(put(BloodPressureSummaryActions.bloodPressureSummaryDeleteSuccess(response.data)));
});

test('delete failure path', () => {
  const response = { ok: false };
  const step = stepper(deleteBloodPressureSummary(FixtureAPI, { bloodPressureSummaryId: { id: 1 } }));
  // Step 1: Hit the api
  step();
  // Step 2: Failed response.
  expect(step(response)).toEqual(put(BloodPressureSummaryActions.bloodPressureSummaryDeleteFailure()));
});
