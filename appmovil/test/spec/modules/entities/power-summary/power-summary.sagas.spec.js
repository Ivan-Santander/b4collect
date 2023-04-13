import { put } from 'redux-saga/effects';

import FixtureAPI from '../../../../../app/shared/services/fixture-api';
import PowerSummarySagas from '../../../../../app/modules/entities/power-summary/power-summary.sagas';
import PowerSummaryActions from '../../../../../app/modules/entities/power-summary/power-summary.reducer';

const { getPowerSummary, getAllPowerSummaries, updatePowerSummary, deletePowerSummary } = PowerSummarySagas;
const stepper = (fn) => (mock) => fn.next(mock).value;

test('get success path', () => {
  const response = FixtureAPI.getPowerSummary(1);
  const step = stepper(getPowerSummary(FixtureAPI, { powerSummaryId: { id: 1 } }));
  // Step 1: Hit the api
  step();
  // Step 2: Successful return and data!
  expect(step(response)).toEqual(put(PowerSummaryActions.powerSummarySuccess(response.data)));
});

test('get failure path', () => {
  const response = { ok: false };
  const step = stepper(getPowerSummary(FixtureAPI, { powerSummaryId: { id: 1 } }));
  // Step 1: Hit the api
  step();
  // Step 2: Failed response.
  expect(step(response)).toEqual(put(PowerSummaryActions.powerSummaryFailure()));
});

test('getAll success path', () => {
  const response = FixtureAPI.getAllPowerSummaries();
  const step = stepper(getAllPowerSummaries(FixtureAPI, { options: { page: 0, sort: 'id,asc', size: 20 } }));
  // Step 1: Hit the api
  step();
  // Step 2: Successful return and data!
  expect(step(response)).toEqual(put(PowerSummaryActions.powerSummaryAllSuccess([{ id: 1 }, { id: 2 }])));
});

test('getAll failure path', () => {
  const response = { ok: false };
  const step = stepper(getAllPowerSummaries(FixtureAPI, { options: { page: 0, sort: 'id,asc', size: 20 } }));
  // Step 1: Hit the api
  step();
  // Step 2: Failed response.
  expect(step(response)).toEqual(put(PowerSummaryActions.powerSummaryAllFailure()));
});

test('update success path', () => {
  const response = FixtureAPI.updatePowerSummary({ id: 1 });
  const step = stepper(updatePowerSummary(FixtureAPI, { powerSummary: { id: 1 } }));
  // Step 1: Hit the api
  step();
  // Step 2: Successful return and data!
  expect(step(response)).toEqual(put(PowerSummaryActions.powerSummaryUpdateSuccess(response.data)));
});

test('update failure path', () => {
  const response = { ok: false };
  const step = stepper(updatePowerSummary(FixtureAPI, { powerSummary: { id: 1 } }));
  // Step 1: Hit the api
  step();
  // Step 2: Failed response.
  expect(step(response)).toEqual(put(PowerSummaryActions.powerSummaryUpdateFailure()));
});

test('delete success path', () => {
  const response = FixtureAPI.deletePowerSummary({ id: 1 });
  const step = stepper(deletePowerSummary(FixtureAPI, { powerSummaryId: { id: 1 } }));
  // Step 1: Hit the api
  step();
  // Step 2: Successful return and data!
  expect(step(response)).toEqual(put(PowerSummaryActions.powerSummaryDeleteSuccess(response.data)));
});

test('delete failure path', () => {
  const response = { ok: false };
  const step = stepper(deletePowerSummary(FixtureAPI, { powerSummaryId: { id: 1 } }));
  // Step 1: Hit the api
  step();
  // Step 2: Failed response.
  expect(step(response)).toEqual(put(PowerSummaryActions.powerSummaryDeleteFailure()));
});
