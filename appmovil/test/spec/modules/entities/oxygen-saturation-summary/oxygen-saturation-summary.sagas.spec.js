import { put } from 'redux-saga/effects';

import FixtureAPI from '../../../../../app/shared/services/fixture-api';
import OxygenSaturationSummarySagas from '../../../../../app/modules/entities/oxygen-saturation-summary/oxygen-saturation-summary.sagas';
import OxygenSaturationSummaryActions from '../../../../../app/modules/entities/oxygen-saturation-summary/oxygen-saturation-summary.reducer';

const { getOxygenSaturationSummary, getAllOxygenSaturationSummaries, updateOxygenSaturationSummary, deleteOxygenSaturationSummary } =
  OxygenSaturationSummarySagas;
const stepper = (fn) => (mock) => fn.next(mock).value;

test('get success path', () => {
  const response = FixtureAPI.getOxygenSaturationSummary(1);
  const step = stepper(getOxygenSaturationSummary(FixtureAPI, { oxygenSaturationSummaryId: { id: 1 } }));
  // Step 1: Hit the api
  step();
  // Step 2: Successful return and data!
  expect(step(response)).toEqual(put(OxygenSaturationSummaryActions.oxygenSaturationSummarySuccess(response.data)));
});

test('get failure path', () => {
  const response = { ok: false };
  const step = stepper(getOxygenSaturationSummary(FixtureAPI, { oxygenSaturationSummaryId: { id: 1 } }));
  // Step 1: Hit the api
  step();
  // Step 2: Failed response.
  expect(step(response)).toEqual(put(OxygenSaturationSummaryActions.oxygenSaturationSummaryFailure()));
});

test('getAll success path', () => {
  const response = FixtureAPI.getAllOxygenSaturationSummaries();
  const step = stepper(getAllOxygenSaturationSummaries(FixtureAPI, { options: { page: 0, sort: 'id,asc', size: 20 } }));
  // Step 1: Hit the api
  step();
  // Step 2: Successful return and data!
  expect(step(response)).toEqual(put(OxygenSaturationSummaryActions.oxygenSaturationSummaryAllSuccess([{ id: 1 }, { id: 2 }])));
});

test('getAll failure path', () => {
  const response = { ok: false };
  const step = stepper(getAllOxygenSaturationSummaries(FixtureAPI, { options: { page: 0, sort: 'id,asc', size: 20 } }));
  // Step 1: Hit the api
  step();
  // Step 2: Failed response.
  expect(step(response)).toEqual(put(OxygenSaturationSummaryActions.oxygenSaturationSummaryAllFailure()));
});

test('update success path', () => {
  const response = FixtureAPI.updateOxygenSaturationSummary({ id: 1 });
  const step = stepper(updateOxygenSaturationSummary(FixtureAPI, { oxygenSaturationSummary: { id: 1 } }));
  // Step 1: Hit the api
  step();
  // Step 2: Successful return and data!
  expect(step(response)).toEqual(put(OxygenSaturationSummaryActions.oxygenSaturationSummaryUpdateSuccess(response.data)));
});

test('update failure path', () => {
  const response = { ok: false };
  const step = stepper(updateOxygenSaturationSummary(FixtureAPI, { oxygenSaturationSummary: { id: 1 } }));
  // Step 1: Hit the api
  step();
  // Step 2: Failed response.
  expect(step(response)).toEqual(put(OxygenSaturationSummaryActions.oxygenSaturationSummaryUpdateFailure()));
});

test('delete success path', () => {
  const response = FixtureAPI.deleteOxygenSaturationSummary({ id: 1 });
  const step = stepper(deleteOxygenSaturationSummary(FixtureAPI, { oxygenSaturationSummaryId: { id: 1 } }));
  // Step 1: Hit the api
  step();
  // Step 2: Successful return and data!
  expect(step(response)).toEqual(put(OxygenSaturationSummaryActions.oxygenSaturationSummaryDeleteSuccess(response.data)));
});

test('delete failure path', () => {
  const response = { ok: false };
  const step = stepper(deleteOxygenSaturationSummary(FixtureAPI, { oxygenSaturationSummaryId: { id: 1 } }));
  // Step 1: Hit the api
  step();
  // Step 2: Failed response.
  expect(step(response)).toEqual(put(OxygenSaturationSummaryActions.oxygenSaturationSummaryDeleteFailure()));
});
