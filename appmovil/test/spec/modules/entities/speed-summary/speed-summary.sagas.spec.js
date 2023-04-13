import { put } from 'redux-saga/effects';

import FixtureAPI from '../../../../../app/shared/services/fixture-api';
import SpeedSummarySagas from '../../../../../app/modules/entities/speed-summary/speed-summary.sagas';
import SpeedSummaryActions from '../../../../../app/modules/entities/speed-summary/speed-summary.reducer';

const { getSpeedSummary, getAllSpeedSummaries, updateSpeedSummary, deleteSpeedSummary } = SpeedSummarySagas;
const stepper = (fn) => (mock) => fn.next(mock).value;

test('get success path', () => {
  const response = FixtureAPI.getSpeedSummary(1);
  const step = stepper(getSpeedSummary(FixtureAPI, { speedSummaryId: { id: 1 } }));
  // Step 1: Hit the api
  step();
  // Step 2: Successful return and data!
  expect(step(response)).toEqual(put(SpeedSummaryActions.speedSummarySuccess(response.data)));
});

test('get failure path', () => {
  const response = { ok: false };
  const step = stepper(getSpeedSummary(FixtureAPI, { speedSummaryId: { id: 1 } }));
  // Step 1: Hit the api
  step();
  // Step 2: Failed response.
  expect(step(response)).toEqual(put(SpeedSummaryActions.speedSummaryFailure()));
});

test('getAll success path', () => {
  const response = FixtureAPI.getAllSpeedSummaries();
  const step = stepper(getAllSpeedSummaries(FixtureAPI, { options: { page: 0, sort: 'id,asc', size: 20 } }));
  // Step 1: Hit the api
  step();
  // Step 2: Successful return and data!
  expect(step(response)).toEqual(put(SpeedSummaryActions.speedSummaryAllSuccess([{ id: 1 }, { id: 2 }])));
});

test('getAll failure path', () => {
  const response = { ok: false };
  const step = stepper(getAllSpeedSummaries(FixtureAPI, { options: { page: 0, sort: 'id,asc', size: 20 } }));
  // Step 1: Hit the api
  step();
  // Step 2: Failed response.
  expect(step(response)).toEqual(put(SpeedSummaryActions.speedSummaryAllFailure()));
});

test('update success path', () => {
  const response = FixtureAPI.updateSpeedSummary({ id: 1 });
  const step = stepper(updateSpeedSummary(FixtureAPI, { speedSummary: { id: 1 } }));
  // Step 1: Hit the api
  step();
  // Step 2: Successful return and data!
  expect(step(response)).toEqual(put(SpeedSummaryActions.speedSummaryUpdateSuccess(response.data)));
});

test('update failure path', () => {
  const response = { ok: false };
  const step = stepper(updateSpeedSummary(FixtureAPI, { speedSummary: { id: 1 } }));
  // Step 1: Hit the api
  step();
  // Step 2: Failed response.
  expect(step(response)).toEqual(put(SpeedSummaryActions.speedSummaryUpdateFailure()));
});

test('delete success path', () => {
  const response = FixtureAPI.deleteSpeedSummary({ id: 1 });
  const step = stepper(deleteSpeedSummary(FixtureAPI, { speedSummaryId: { id: 1 } }));
  // Step 1: Hit the api
  step();
  // Step 2: Successful return and data!
  expect(step(response)).toEqual(put(SpeedSummaryActions.speedSummaryDeleteSuccess(response.data)));
});

test('delete failure path', () => {
  const response = { ok: false };
  const step = stepper(deleteSpeedSummary(FixtureAPI, { speedSummaryId: { id: 1 } }));
  // Step 1: Hit the api
  step();
  // Step 2: Failed response.
  expect(step(response)).toEqual(put(SpeedSummaryActions.speedSummaryDeleteFailure()));
});
