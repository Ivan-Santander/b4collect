import { put } from 'redux-saga/effects';

import FixtureAPI from '../../../../../app/shared/services/fixture-api';
import NutritionSagas from '../../../../../app/modules/entities/nutrition/nutrition.sagas';
import NutritionActions from '../../../../../app/modules/entities/nutrition/nutrition.reducer';

const { getNutrition, getAllNutritions, updateNutrition, deleteNutrition } = NutritionSagas;
const stepper = (fn) => (mock) => fn.next(mock).value;

test('get success path', () => {
  const response = FixtureAPI.getNutrition(1);
  const step = stepper(getNutrition(FixtureAPI, { nutritionId: { id: 1 } }));
  // Step 1: Hit the api
  step();
  // Step 2: Successful return and data!
  expect(step(response)).toEqual(put(NutritionActions.nutritionSuccess(response.data)));
});

test('get failure path', () => {
  const response = { ok: false };
  const step = stepper(getNutrition(FixtureAPI, { nutritionId: { id: 1 } }));
  // Step 1: Hit the api
  step();
  // Step 2: Failed response.
  expect(step(response)).toEqual(put(NutritionActions.nutritionFailure()));
});

test('getAll success path', () => {
  const response = FixtureAPI.getAllNutritions();
  const step = stepper(getAllNutritions(FixtureAPI, { options: { page: 0, sort: 'id,asc', size: 20 } }));
  // Step 1: Hit the api
  step();
  // Step 2: Successful return and data!
  expect(step(response)).toEqual(put(NutritionActions.nutritionAllSuccess([{ id: 1 }, { id: 2 }])));
});

test('getAll failure path', () => {
  const response = { ok: false };
  const step = stepper(getAllNutritions(FixtureAPI, { options: { page: 0, sort: 'id,asc', size: 20 } }));
  // Step 1: Hit the api
  step();
  // Step 2: Failed response.
  expect(step(response)).toEqual(put(NutritionActions.nutritionAllFailure()));
});

test('update success path', () => {
  const response = FixtureAPI.updateNutrition({ id: 1 });
  const step = stepper(updateNutrition(FixtureAPI, { nutrition: { id: 1 } }));
  // Step 1: Hit the api
  step();
  // Step 2: Successful return and data!
  expect(step(response)).toEqual(put(NutritionActions.nutritionUpdateSuccess(response.data)));
});

test('update failure path', () => {
  const response = { ok: false };
  const step = stepper(updateNutrition(FixtureAPI, { nutrition: { id: 1 } }));
  // Step 1: Hit the api
  step();
  // Step 2: Failed response.
  expect(step(response)).toEqual(put(NutritionActions.nutritionUpdateFailure()));
});

test('delete success path', () => {
  const response = FixtureAPI.deleteNutrition({ id: 1 });
  const step = stepper(deleteNutrition(FixtureAPI, { nutritionId: { id: 1 } }));
  // Step 1: Hit the api
  step();
  // Step 2: Successful return and data!
  expect(step(response)).toEqual(put(NutritionActions.nutritionDeleteSuccess(response.data)));
});

test('delete failure path', () => {
  const response = { ok: false };
  const step = stepper(deleteNutrition(FixtureAPI, { nutritionId: { id: 1 } }));
  // Step 1: Hit the api
  step();
  // Step 2: Failed response.
  expect(step(response)).toEqual(put(NutritionActions.nutritionDeleteFailure()));
});
