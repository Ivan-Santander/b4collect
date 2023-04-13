import { put } from 'redux-saga/effects';

import FixtureAPI from '../../../../../app/shared/services/fixture-api';
import BloodGlucoseSummarySagas from '../../../../../app/modules/entities/blood-glucose-summary/blood-glucose-summary.sagas';
import BloodGlucoseSummaryActions from '../../../../../app/modules/entities/blood-glucose-summary/blood-glucose-summary.reducer';

const { getBloodGlucoseSummary, getAllBloodGlucoseSummaries, updateBloodGlucoseSummary, deleteBloodGlucoseSummary } =
  BloodGlucoseSummarySagas;
const stepper = (fn) => (mock) => fn.next(mock).value;

test('get success path', () => {
  const response = FixtureAPI.getBloodGlucoseSummary(1);
  const step = stepper(getBloodGlucoseSummary(FixtureAPI, { bloodGlucoseSummaryId: { id: 1 } }));
  // Step 1: Hit the api
  step();
  // Step 2: Successful return and data!
  expect(step(response)).toEqual(put(BloodGlucoseSummaryActions.bloodGlucoseSummarySuccess(response.data)));
});

test('get failure path', () => {
  const response = { ok: false };
  const step = stepper(getBloodGlucoseSummary(FixtureAPI, { bloodGlucoseSummaryId: { id: 1 } }));
  // Step 1: Hit the api
  step();
  // Step 2: Failed response.
  expect(step(response)).toEqual(put(BloodGlucoseSummaryActions.bloodGlucoseSummaryFailure()));
});

test('getAll success path', () => {
  const response = FixtureAPI.getAllBloodGlucoseSummaries();
  const step = stepper(getAllBloodGlucoseSummaries(FixtureAPI, { options: { page: 0, sort: 'id,asc', size: 20 } }));
  // Step 1: Hit the api
  step();
  // Step 2: Successful return and data!
  expect(step(response)).toEqual(put(BloodGlucoseSummaryActions.bloodGlucoseSummaryAllSuccess([{ id: 1 }, { id: 2 }])));
});

test('getAll failure path', () => {
  const response = { ok: false };
  const step = stepper(getAllBloodGlucoseSummaries(FixtureAPI, { options: { page: 0, sort: 'id,asc', size: 20 } }));
  // Step 1: Hit the api
  step();
  // Step 2: Failed response.
  expect(step(response)).toEqual(put(BloodGlucoseSummaryActions.bloodGlucoseSummaryAllFailure()));
});

test('update success path', () => {
  const response = FixtureAPI.updateBloodGlucoseSummary({ id: 1 });
  const step = stepper(updateBloodGlucoseSummary(FixtureAPI, { bloodGlucoseSummary: { id: 1 } }));
  // Step 1: Hit the api
  step();
  // Step 2: Successful return and data!
  expect(step(response)).toEqual(put(BloodGlucoseSummaryActions.bloodGlucoseSummaryUpdateSuccess(response.data)));
});

test('update failure path', () => {
  const response = { ok: false };
  const step = stepper(updateBloodGlucoseSummary(FixtureAPI, { bloodGlucoseSummary: { id: 1 } }));
  // Step 1: Hit the api
  step();
  // Step 2: Failed response.
  expect(step(response)).toEqual(put(BloodGlucoseSummaryActions.bloodGlucoseSummaryUpdateFailure()));
});

test('delete success path', () => {
  const response = FixtureAPI.deleteBloodGlucoseSummary({ id: 1 });
  const step = stepper(deleteBloodGlucoseSummary(FixtureAPI, { bloodGlucoseSummaryId: { id: 1 } }));
  // Step 1: Hit the api
  step();
  // Step 2: Successful return and data!
  expect(step(response)).toEqual(put(BloodGlucoseSummaryActions.bloodGlucoseSummaryDeleteSuccess(response.data)));
});

test('delete failure path', () => {
  const response = { ok: false };
  const step = stepper(deleteBloodGlucoseSummary(FixtureAPI, { bloodGlucoseSummaryId: { id: 1 } }));
  // Step 1: Hit the api
  step();
  // Step 2: Failed response.
  expect(step(response)).toEqual(put(BloodGlucoseSummaryActions.bloodGlucoseSummaryDeleteFailure()));
});
