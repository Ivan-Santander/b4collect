import { put } from 'redux-saga/effects';

import FixtureAPI from '../../../../../app/shared/services/fixture-api';
import ActivitySummarySagas from '../../../../../app/modules/entities/activity-summary/activity-summary.sagas';
import ActivitySummaryActions from '../../../../../app/modules/entities/activity-summary/activity-summary.reducer';

const { getActivitySummary, getAllActivitySummaries, updateActivitySummary, deleteActivitySummary } = ActivitySummarySagas;
const stepper = (fn) => (mock) => fn.next(mock).value;

test('get success path', () => {
  const response = FixtureAPI.getActivitySummary(1);
  const step = stepper(getActivitySummary(FixtureAPI, { activitySummaryId: { id: 1 } }));
  // Step 1: Hit the api
  step();
  // Step 2: Successful return and data!
  expect(step(response)).toEqual(put(ActivitySummaryActions.activitySummarySuccess(response.data)));
});

test('get failure path', () => {
  const response = { ok: false };
  const step = stepper(getActivitySummary(FixtureAPI, { activitySummaryId: { id: 1 } }));
  // Step 1: Hit the api
  step();
  // Step 2: Failed response.
  expect(step(response)).toEqual(put(ActivitySummaryActions.activitySummaryFailure()));
});

test('getAll success path', () => {
  const response = FixtureAPI.getAllActivitySummaries();
  const step = stepper(getAllActivitySummaries(FixtureAPI, { options: { page: 0, sort: 'id,asc', size: 20 } }));
  // Step 1: Hit the api
  step();
  // Step 2: Successful return and data!
  expect(step(response)).toEqual(put(ActivitySummaryActions.activitySummaryAllSuccess([{ id: 1 }, { id: 2 }])));
});

test('getAll failure path', () => {
  const response = { ok: false };
  const step = stepper(getAllActivitySummaries(FixtureAPI, { options: { page: 0, sort: 'id,asc', size: 20 } }));
  // Step 1: Hit the api
  step();
  // Step 2: Failed response.
  expect(step(response)).toEqual(put(ActivitySummaryActions.activitySummaryAllFailure()));
});

test('update success path', () => {
  const response = FixtureAPI.updateActivitySummary({ id: 1 });
  const step = stepper(updateActivitySummary(FixtureAPI, { activitySummary: { id: 1 } }));
  // Step 1: Hit the api
  step();
  // Step 2: Successful return and data!
  expect(step(response)).toEqual(put(ActivitySummaryActions.activitySummaryUpdateSuccess(response.data)));
});

test('update failure path', () => {
  const response = { ok: false };
  const step = stepper(updateActivitySummary(FixtureAPI, { activitySummary: { id: 1 } }));
  // Step 1: Hit the api
  step();
  // Step 2: Failed response.
  expect(step(response)).toEqual(put(ActivitySummaryActions.activitySummaryUpdateFailure()));
});

test('delete success path', () => {
  const response = FixtureAPI.deleteActivitySummary({ id: 1 });
  const step = stepper(deleteActivitySummary(FixtureAPI, { activitySummaryId: { id: 1 } }));
  // Step 1: Hit the api
  step();
  // Step 2: Successful return and data!
  expect(step(response)).toEqual(put(ActivitySummaryActions.activitySummaryDeleteSuccess(response.data)));
});

test('delete failure path', () => {
  const response = { ok: false };
  const step = stepper(deleteActivitySummary(FixtureAPI, { activitySummaryId: { id: 1 } }));
  // Step 1: Hit the api
  step();
  // Step 2: Failed response.
  expect(step(response)).toEqual(put(ActivitySummaryActions.activitySummaryDeleteFailure()));
});
