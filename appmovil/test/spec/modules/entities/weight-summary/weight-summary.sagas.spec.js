import { put } from 'redux-saga/effects';

import FixtureAPI from '../../../../../app/shared/services/fixture-api';
import WeightSummarySagas from '../../../../../app/modules/entities/weight-summary/weight-summary.sagas';
import WeightSummaryActions from '../../../../../app/modules/entities/weight-summary/weight-summary.reducer';

const { getWeightSummary, getAllWeightSummaries, updateWeightSummary, deleteWeightSummary } = WeightSummarySagas;
const stepper = (fn) => (mock) => fn.next(mock).value;

test('get success path', () => {
  const response = FixtureAPI.getWeightSummary(1);
  const step = stepper(getWeightSummary(FixtureAPI, { weightSummaryId: { id: 1 } }));
  // Step 1: Hit the api
  step();
  // Step 2: Successful return and data!
  expect(step(response)).toEqual(put(WeightSummaryActions.weightSummarySuccess(response.data)));
});

test('get failure path', () => {
  const response = { ok: false };
  const step = stepper(getWeightSummary(FixtureAPI, { weightSummaryId: { id: 1 } }));
  // Step 1: Hit the api
  step();
  // Step 2: Failed response.
  expect(step(response)).toEqual(put(WeightSummaryActions.weightSummaryFailure()));
});

test('getAll success path', () => {
  const response = FixtureAPI.getAllWeightSummaries();
  const step = stepper(getAllWeightSummaries(FixtureAPI, { options: { page: 0, sort: 'id,asc', size: 20 } }));
  // Step 1: Hit the api
  step();
  // Step 2: Successful return and data!
  expect(step(response)).toEqual(put(WeightSummaryActions.weightSummaryAllSuccess([{ id: 1 }, { id: 2 }])));
});

test('getAll failure path', () => {
  const response = { ok: false };
  const step = stepper(getAllWeightSummaries(FixtureAPI, { options: { page: 0, sort: 'id,asc', size: 20 } }));
  // Step 1: Hit the api
  step();
  // Step 2: Failed response.
  expect(step(response)).toEqual(put(WeightSummaryActions.weightSummaryAllFailure()));
});

test('update success path', () => {
  const response = FixtureAPI.updateWeightSummary({ id: 1 });
  const step = stepper(updateWeightSummary(FixtureAPI, { weightSummary: { id: 1 } }));
  // Step 1: Hit the api
  step();
  // Step 2: Successful return and data!
  expect(step(response)).toEqual(put(WeightSummaryActions.weightSummaryUpdateSuccess(response.data)));
});

test('update failure path', () => {
  const response = { ok: false };
  const step = stepper(updateWeightSummary(FixtureAPI, { weightSummary: { id: 1 } }));
  // Step 1: Hit the api
  step();
  // Step 2: Failed response.
  expect(step(response)).toEqual(put(WeightSummaryActions.weightSummaryUpdateFailure()));
});

test('delete success path', () => {
  const response = FixtureAPI.deleteWeightSummary({ id: 1 });
  const step = stepper(deleteWeightSummary(FixtureAPI, { weightSummaryId: { id: 1 } }));
  // Step 1: Hit the api
  step();
  // Step 2: Successful return and data!
  expect(step(response)).toEqual(put(WeightSummaryActions.weightSummaryDeleteSuccess(response.data)));
});

test('delete failure path', () => {
  const response = { ok: false };
  const step = stepper(deleteWeightSummary(FixtureAPI, { weightSummaryId: { id: 1 } }));
  // Step 1: Hit the api
  step();
  // Step 2: Failed response.
  expect(step(response)).toEqual(put(WeightSummaryActions.weightSummaryDeleteFailure()));
});
