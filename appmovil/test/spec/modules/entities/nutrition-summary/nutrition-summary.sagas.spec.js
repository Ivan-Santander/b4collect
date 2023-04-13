import { put } from 'redux-saga/effects';

import FixtureAPI from '../../../../../app/shared/services/fixture-api';
import NutritionSummarySagas from '../../../../../app/modules/entities/nutrition-summary/nutrition-summary.sagas';
import NutritionSummaryActions from '../../../../../app/modules/entities/nutrition-summary/nutrition-summary.reducer';

const { getNutritionSummary, getAllNutritionSummaries, updateNutritionSummary, deleteNutritionSummary } = NutritionSummarySagas;
const stepper = (fn) => (mock) => fn.next(mock).value;

test('get success path', () => {
  const response = FixtureAPI.getNutritionSummary(1);
  const step = stepper(getNutritionSummary(FixtureAPI, { nutritionSummaryId: { id: 1 } }));
  // Step 1: Hit the api
  step();
  // Step 2: Successful return and data!
  expect(step(response)).toEqual(put(NutritionSummaryActions.nutritionSummarySuccess(response.data)));
});

test('get failure path', () => {
  const response = { ok: false };
  const step = stepper(getNutritionSummary(FixtureAPI, { nutritionSummaryId: { id: 1 } }));
  // Step 1: Hit the api
  step();
  // Step 2: Failed response.
  expect(step(response)).toEqual(put(NutritionSummaryActions.nutritionSummaryFailure()));
});

test('getAll success path', () => {
  const response = FixtureAPI.getAllNutritionSummaries();
  const step = stepper(getAllNutritionSummaries(FixtureAPI, { options: { page: 0, sort: 'id,asc', size: 20 } }));
  // Step 1: Hit the api
  step();
  // Step 2: Successful return and data!
  expect(step(response)).toEqual(put(NutritionSummaryActions.nutritionSummaryAllSuccess([{ id: 1 }, { id: 2 }])));
});

test('getAll failure path', () => {
  const response = { ok: false };
  const step = stepper(getAllNutritionSummaries(FixtureAPI, { options: { page: 0, sort: 'id,asc', size: 20 } }));
  // Step 1: Hit the api
  step();
  // Step 2: Failed response.
  expect(step(response)).toEqual(put(NutritionSummaryActions.nutritionSummaryAllFailure()));
});

test('update success path', () => {
  const response = FixtureAPI.updateNutritionSummary({ id: 1 });
  const step = stepper(updateNutritionSummary(FixtureAPI, { nutritionSummary: { id: 1 } }));
  // Step 1: Hit the api
  step();
  // Step 2: Successful return and data!
  expect(step(response)).toEqual(put(NutritionSummaryActions.nutritionSummaryUpdateSuccess(response.data)));
});

test('update failure path', () => {
  const response = { ok: false };
  const step = stepper(updateNutritionSummary(FixtureAPI, { nutritionSummary: { id: 1 } }));
  // Step 1: Hit the api
  step();
  // Step 2: Failed response.
  expect(step(response)).toEqual(put(NutritionSummaryActions.nutritionSummaryUpdateFailure()));
});

test('delete success path', () => {
  const response = FixtureAPI.deleteNutritionSummary({ id: 1 });
  const step = stepper(deleteNutritionSummary(FixtureAPI, { nutritionSummaryId: { id: 1 } }));
  // Step 1: Hit the api
  step();
  // Step 2: Successful return and data!
  expect(step(response)).toEqual(put(NutritionSummaryActions.nutritionSummaryDeleteSuccess(response.data)));
});

test('delete failure path', () => {
  const response = { ok: false };
  const step = stepper(deleteNutritionSummary(FixtureAPI, { nutritionSummaryId: { id: 1 } }));
  // Step 1: Hit the api
  step();
  // Step 2: Failed response.
  expect(step(response)).toEqual(put(NutritionSummaryActions.nutritionSummaryDeleteFailure()));
});
