import { put } from 'redux-saga/effects';

import FixtureAPI from '../../../../../app/shared/services/fixture-api';
import HeightSummarySagas from '../../../../../app/modules/entities/height-summary/height-summary.sagas';
import HeightSummaryActions from '../../../../../app/modules/entities/height-summary/height-summary.reducer';

const { getHeightSummary, getAllHeightSummaries, updateHeightSummary, deleteHeightSummary } = HeightSummarySagas;
const stepper = (fn) => (mock) => fn.next(mock).value;

test('get success path', () => {
  const response = FixtureAPI.getHeightSummary(1);
  const step = stepper(getHeightSummary(FixtureAPI, { heightSummaryId: { id: 1 } }));
  // Step 1: Hit the api
  step();
  // Step 2: Successful return and data!
  expect(step(response)).toEqual(put(HeightSummaryActions.heightSummarySuccess(response.data)));
});

test('get failure path', () => {
  const response = { ok: false };
  const step = stepper(getHeightSummary(FixtureAPI, { heightSummaryId: { id: 1 } }));
  // Step 1: Hit the api
  step();
  // Step 2: Failed response.
  expect(step(response)).toEqual(put(HeightSummaryActions.heightSummaryFailure()));
});

test('getAll success path', () => {
  const response = FixtureAPI.getAllHeightSummaries();
  const step = stepper(getAllHeightSummaries(FixtureAPI, { options: { page: 0, sort: 'id,asc', size: 20 } }));
  // Step 1: Hit the api
  step();
  // Step 2: Successful return and data!
  expect(step(response)).toEqual(put(HeightSummaryActions.heightSummaryAllSuccess([{ id: 1 }, { id: 2 }])));
});

test('getAll failure path', () => {
  const response = { ok: false };
  const step = stepper(getAllHeightSummaries(FixtureAPI, { options: { page: 0, sort: 'id,asc', size: 20 } }));
  // Step 1: Hit the api
  step();
  // Step 2: Failed response.
  expect(step(response)).toEqual(put(HeightSummaryActions.heightSummaryAllFailure()));
});

test('update success path', () => {
  const response = FixtureAPI.updateHeightSummary({ id: 1 });
  const step = stepper(updateHeightSummary(FixtureAPI, { heightSummary: { id: 1 } }));
  // Step 1: Hit the api
  step();
  // Step 2: Successful return and data!
  expect(step(response)).toEqual(put(HeightSummaryActions.heightSummaryUpdateSuccess(response.data)));
});

test('update failure path', () => {
  const response = { ok: false };
  const step = stepper(updateHeightSummary(FixtureAPI, { heightSummary: { id: 1 } }));
  // Step 1: Hit the api
  step();
  // Step 2: Failed response.
  expect(step(response)).toEqual(put(HeightSummaryActions.heightSummaryUpdateFailure()));
});

test('delete success path', () => {
  const response = FixtureAPI.deleteHeightSummary({ id: 1 });
  const step = stepper(deleteHeightSummary(FixtureAPI, { heightSummaryId: { id: 1 } }));
  // Step 1: Hit the api
  step();
  // Step 2: Successful return and data!
  expect(step(response)).toEqual(put(HeightSummaryActions.heightSummaryDeleteSuccess(response.data)));
});

test('delete failure path', () => {
  const response = { ok: false };
  const step = stepper(deleteHeightSummary(FixtureAPI, { heightSummaryId: { id: 1 } }));
  // Step 1: Hit the api
  step();
  // Step 2: Failed response.
  expect(step(response)).toEqual(put(HeightSummaryActions.heightSummaryDeleteFailure()));
});
