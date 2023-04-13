import { put } from 'redux-saga/effects';

import FixtureAPI from '../../../../../app/shared/services/fixture-api';
import CaloriesBmrSummarySagas from '../../../../../app/modules/entities/calories-bmr-summary/calories-bmr-summary.sagas';
import CaloriesBmrSummaryActions from '../../../../../app/modules/entities/calories-bmr-summary/calories-bmr-summary.reducer';

const { getCaloriesBmrSummary, getAllCaloriesBmrSummaries, updateCaloriesBmrSummary, deleteCaloriesBmrSummary } = CaloriesBmrSummarySagas;
const stepper = (fn) => (mock) => fn.next(mock).value;

test('get success path', () => {
  const response = FixtureAPI.getCaloriesBmrSummary(1);
  const step = stepper(getCaloriesBmrSummary(FixtureAPI, { caloriesBmrSummaryId: { id: 1 } }));
  // Step 1: Hit the api
  step();
  // Step 2: Successful return and data!
  expect(step(response)).toEqual(put(CaloriesBmrSummaryActions.caloriesBmrSummarySuccess(response.data)));
});

test('get failure path', () => {
  const response = { ok: false };
  const step = stepper(getCaloriesBmrSummary(FixtureAPI, { caloriesBmrSummaryId: { id: 1 } }));
  // Step 1: Hit the api
  step();
  // Step 2: Failed response.
  expect(step(response)).toEqual(put(CaloriesBmrSummaryActions.caloriesBmrSummaryFailure()));
});

test('getAll success path', () => {
  const response = FixtureAPI.getAllCaloriesBmrSummaries();
  const step = stepper(getAllCaloriesBmrSummaries(FixtureAPI, { options: { page: 0, sort: 'id,asc', size: 20 } }));
  // Step 1: Hit the api
  step();
  // Step 2: Successful return and data!
  expect(step(response)).toEqual(put(CaloriesBmrSummaryActions.caloriesBmrSummaryAllSuccess([{ id: 1 }, { id: 2 }])));
});

test('getAll failure path', () => {
  const response = { ok: false };
  const step = stepper(getAllCaloriesBmrSummaries(FixtureAPI, { options: { page: 0, sort: 'id,asc', size: 20 } }));
  // Step 1: Hit the api
  step();
  // Step 2: Failed response.
  expect(step(response)).toEqual(put(CaloriesBmrSummaryActions.caloriesBmrSummaryAllFailure()));
});

test('update success path', () => {
  const response = FixtureAPI.updateCaloriesBmrSummary({ id: 1 });
  const step = stepper(updateCaloriesBmrSummary(FixtureAPI, { caloriesBmrSummary: { id: 1 } }));
  // Step 1: Hit the api
  step();
  // Step 2: Successful return and data!
  expect(step(response)).toEqual(put(CaloriesBmrSummaryActions.caloriesBmrSummaryUpdateSuccess(response.data)));
});

test('update failure path', () => {
  const response = { ok: false };
  const step = stepper(updateCaloriesBmrSummary(FixtureAPI, { caloriesBmrSummary: { id: 1 } }));
  // Step 1: Hit the api
  step();
  // Step 2: Failed response.
  expect(step(response)).toEqual(put(CaloriesBmrSummaryActions.caloriesBmrSummaryUpdateFailure()));
});

test('delete success path', () => {
  const response = FixtureAPI.deleteCaloriesBmrSummary({ id: 1 });
  const step = stepper(deleteCaloriesBmrSummary(FixtureAPI, { caloriesBmrSummaryId: { id: 1 } }));
  // Step 1: Hit the api
  step();
  // Step 2: Successful return and data!
  expect(step(response)).toEqual(put(CaloriesBmrSummaryActions.caloriesBmrSummaryDeleteSuccess(response.data)));
});

test('delete failure path', () => {
  const response = { ok: false };
  const step = stepper(deleteCaloriesBmrSummary(FixtureAPI, { caloriesBmrSummaryId: { id: 1 } }));
  // Step 1: Hit the api
  step();
  // Step 2: Failed response.
  expect(step(response)).toEqual(put(CaloriesBmrSummaryActions.caloriesBmrSummaryDeleteFailure()));
});
