import { put } from 'redux-saga/effects';

import FixtureAPI from '../../../../../app/shared/services/fixture-api';
import HeartRateSummarySagas from '../../../../../app/modules/entities/heart-rate-summary/heart-rate-summary.sagas';
import HeartRateSummaryActions from '../../../../../app/modules/entities/heart-rate-summary/heart-rate-summary.reducer';

const { getHeartRateSummary, getAllHeartRateSummaries, updateHeartRateSummary, deleteHeartRateSummary } = HeartRateSummarySagas;
const stepper = (fn) => (mock) => fn.next(mock).value;

test('get success path', () => {
  const response = FixtureAPI.getHeartRateSummary(1);
  const step = stepper(getHeartRateSummary(FixtureAPI, { heartRateSummaryId: { id: 1 } }));
  // Step 1: Hit the api
  step();
  // Step 2: Successful return and data!
  expect(step(response)).toEqual(put(HeartRateSummaryActions.heartRateSummarySuccess(response.data)));
});

test('get failure path', () => {
  const response = { ok: false };
  const step = stepper(getHeartRateSummary(FixtureAPI, { heartRateSummaryId: { id: 1 } }));
  // Step 1: Hit the api
  step();
  // Step 2: Failed response.
  expect(step(response)).toEqual(put(HeartRateSummaryActions.heartRateSummaryFailure()));
});

test('getAll success path', () => {
  const response = FixtureAPI.getAllHeartRateSummaries();
  const step = stepper(getAllHeartRateSummaries(FixtureAPI, { options: { page: 0, sort: 'id,asc', size: 20 } }));
  // Step 1: Hit the api
  step();
  // Step 2: Successful return and data!
  expect(step(response)).toEqual(put(HeartRateSummaryActions.heartRateSummaryAllSuccess([{ id: 1 }, { id: 2 }])));
});

test('getAll failure path', () => {
  const response = { ok: false };
  const step = stepper(getAllHeartRateSummaries(FixtureAPI, { options: { page: 0, sort: 'id,asc', size: 20 } }));
  // Step 1: Hit the api
  step();
  // Step 2: Failed response.
  expect(step(response)).toEqual(put(HeartRateSummaryActions.heartRateSummaryAllFailure()));
});

test('update success path', () => {
  const response = FixtureAPI.updateHeartRateSummary({ id: 1 });
  const step = stepper(updateHeartRateSummary(FixtureAPI, { heartRateSummary: { id: 1 } }));
  // Step 1: Hit the api
  step();
  // Step 2: Successful return and data!
  expect(step(response)).toEqual(put(HeartRateSummaryActions.heartRateSummaryUpdateSuccess(response.data)));
});

test('update failure path', () => {
  const response = { ok: false };
  const step = stepper(updateHeartRateSummary(FixtureAPI, { heartRateSummary: { id: 1 } }));
  // Step 1: Hit the api
  step();
  // Step 2: Failed response.
  expect(step(response)).toEqual(put(HeartRateSummaryActions.heartRateSummaryUpdateFailure()));
});

test('delete success path', () => {
  const response = FixtureAPI.deleteHeartRateSummary({ id: 1 });
  const step = stepper(deleteHeartRateSummary(FixtureAPI, { heartRateSummaryId: { id: 1 } }));
  // Step 1: Hit the api
  step();
  // Step 2: Successful return and data!
  expect(step(response)).toEqual(put(HeartRateSummaryActions.heartRateSummaryDeleteSuccess(response.data)));
});

test('delete failure path', () => {
  const response = { ok: false };
  const step = stepper(deleteHeartRateSummary(FixtureAPI, { heartRateSummaryId: { id: 1 } }));
  // Step 1: Hit the api
  step();
  // Step 2: Failed response.
  expect(step(response)).toEqual(put(HeartRateSummaryActions.heartRateSummaryDeleteFailure()));
});
