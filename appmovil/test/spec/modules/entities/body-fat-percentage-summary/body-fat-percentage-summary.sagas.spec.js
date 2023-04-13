import { put } from 'redux-saga/effects';

import FixtureAPI from '../../../../../app/shared/services/fixture-api';
import BodyFatPercentageSummarySagas from '../../../../../app/modules/entities/body-fat-percentage-summary/body-fat-percentage-summary.sagas';
import BodyFatPercentageSummaryActions from '../../../../../app/modules/entities/body-fat-percentage-summary/body-fat-percentage-summary.reducer';

const { getBodyFatPercentageSummary, getAllBodyFatPercentageSummaries, updateBodyFatPercentageSummary, deleteBodyFatPercentageSummary } =
  BodyFatPercentageSummarySagas;
const stepper = (fn) => (mock) => fn.next(mock).value;

test('get success path', () => {
  const response = FixtureAPI.getBodyFatPercentageSummary(1);
  const step = stepper(getBodyFatPercentageSummary(FixtureAPI, { bodyFatPercentageSummaryId: { id: 1 } }));
  // Step 1: Hit the api
  step();
  // Step 2: Successful return and data!
  expect(step(response)).toEqual(put(BodyFatPercentageSummaryActions.bodyFatPercentageSummarySuccess(response.data)));
});

test('get failure path', () => {
  const response = { ok: false };
  const step = stepper(getBodyFatPercentageSummary(FixtureAPI, { bodyFatPercentageSummaryId: { id: 1 } }));
  // Step 1: Hit the api
  step();
  // Step 2: Failed response.
  expect(step(response)).toEqual(put(BodyFatPercentageSummaryActions.bodyFatPercentageSummaryFailure()));
});

test('getAll success path', () => {
  const response = FixtureAPI.getAllBodyFatPercentageSummaries();
  const step = stepper(getAllBodyFatPercentageSummaries(FixtureAPI, { options: { page: 0, sort: 'id,asc', size: 20 } }));
  // Step 1: Hit the api
  step();
  // Step 2: Successful return and data!
  expect(step(response)).toEqual(put(BodyFatPercentageSummaryActions.bodyFatPercentageSummaryAllSuccess([{ id: 1 }, { id: 2 }])));
});

test('getAll failure path', () => {
  const response = { ok: false };
  const step = stepper(getAllBodyFatPercentageSummaries(FixtureAPI, { options: { page: 0, sort: 'id,asc', size: 20 } }));
  // Step 1: Hit the api
  step();
  // Step 2: Failed response.
  expect(step(response)).toEqual(put(BodyFatPercentageSummaryActions.bodyFatPercentageSummaryAllFailure()));
});

test('update success path', () => {
  const response = FixtureAPI.updateBodyFatPercentageSummary({ id: 1 });
  const step = stepper(updateBodyFatPercentageSummary(FixtureAPI, { bodyFatPercentageSummary: { id: 1 } }));
  // Step 1: Hit the api
  step();
  // Step 2: Successful return and data!
  expect(step(response)).toEqual(put(BodyFatPercentageSummaryActions.bodyFatPercentageSummaryUpdateSuccess(response.data)));
});

test('update failure path', () => {
  const response = { ok: false };
  const step = stepper(updateBodyFatPercentageSummary(FixtureAPI, { bodyFatPercentageSummary: { id: 1 } }));
  // Step 1: Hit the api
  step();
  // Step 2: Failed response.
  expect(step(response)).toEqual(put(BodyFatPercentageSummaryActions.bodyFatPercentageSummaryUpdateFailure()));
});

test('delete success path', () => {
  const response = FixtureAPI.deleteBodyFatPercentageSummary({ id: 1 });
  const step = stepper(deleteBodyFatPercentageSummary(FixtureAPI, { bodyFatPercentageSummaryId: { id: 1 } }));
  // Step 1: Hit the api
  step();
  // Step 2: Successful return and data!
  expect(step(response)).toEqual(put(BodyFatPercentageSummaryActions.bodyFatPercentageSummaryDeleteSuccess(response.data)));
});

test('delete failure path', () => {
  const response = { ok: false };
  const step = stepper(deleteBodyFatPercentageSummary(FixtureAPI, { bodyFatPercentageSummaryId: { id: 1 } }));
  // Step 1: Hit the api
  step();
  // Step 2: Failed response.
  expect(step(response)).toEqual(put(BodyFatPercentageSummaryActions.bodyFatPercentageSummaryDeleteFailure()));
});
