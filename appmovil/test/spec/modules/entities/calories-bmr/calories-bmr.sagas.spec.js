import { put } from 'redux-saga/effects';

import FixtureAPI from '../../../../../app/shared/services/fixture-api';
import CaloriesBMRSagas from '../../../../../app/modules/entities/calories-bmr/calories-bmr.sagas';
import CaloriesBMRActions from '../../../../../app/modules/entities/calories-bmr/calories-bmr.reducer';

const { getCaloriesBMR, getAllCaloriesBMRS, updateCaloriesBMR, deleteCaloriesBMR } = CaloriesBMRSagas;
const stepper = (fn) => (mock) => fn.next(mock).value;

test('get success path', () => {
  const response = FixtureAPI.getCaloriesBMR(1);
  const step = stepper(getCaloriesBMR(FixtureAPI, { caloriesBMRId: { id: 1 } }));
  // Step 1: Hit the api
  step();
  // Step 2: Successful return and data!
  expect(step(response)).toEqual(put(CaloriesBMRActions.caloriesBMRSuccess(response.data)));
});

test('get failure path', () => {
  const response = { ok: false };
  const step = stepper(getCaloriesBMR(FixtureAPI, { caloriesBMRId: { id: 1 } }));
  // Step 1: Hit the api
  step();
  // Step 2: Failed response.
  expect(step(response)).toEqual(put(CaloriesBMRActions.caloriesBMRFailure()));
});

test('getAll success path', () => {
  const response = FixtureAPI.getAllCaloriesBMRS();
  const step = stepper(getAllCaloriesBMRS(FixtureAPI, { options: { page: 0, sort: 'id,asc', size: 20 } }));
  // Step 1: Hit the api
  step();
  // Step 2: Successful return and data!
  expect(step(response)).toEqual(put(CaloriesBMRActions.caloriesBMRAllSuccess([{ id: 1 }, { id: 2 }])));
});

test('getAll failure path', () => {
  const response = { ok: false };
  const step = stepper(getAllCaloriesBMRS(FixtureAPI, { options: { page: 0, sort: 'id,asc', size: 20 } }));
  // Step 1: Hit the api
  step();
  // Step 2: Failed response.
  expect(step(response)).toEqual(put(CaloriesBMRActions.caloriesBMRAllFailure()));
});

test('update success path', () => {
  const response = FixtureAPI.updateCaloriesBMR({ id: 1 });
  const step = stepper(updateCaloriesBMR(FixtureAPI, { caloriesBMR: { id: 1 } }));
  // Step 1: Hit the api
  step();
  // Step 2: Successful return and data!
  expect(step(response)).toEqual(put(CaloriesBMRActions.caloriesBMRUpdateSuccess(response.data)));
});

test('update failure path', () => {
  const response = { ok: false };
  const step = stepper(updateCaloriesBMR(FixtureAPI, { caloriesBMR: { id: 1 } }));
  // Step 1: Hit the api
  step();
  // Step 2: Failed response.
  expect(step(response)).toEqual(put(CaloriesBMRActions.caloriesBMRUpdateFailure()));
});

test('delete success path', () => {
  const response = FixtureAPI.deleteCaloriesBMR({ id: 1 });
  const step = stepper(deleteCaloriesBMR(FixtureAPI, { caloriesBMRId: { id: 1 } }));
  // Step 1: Hit the api
  step();
  // Step 2: Successful return and data!
  expect(step(response)).toEqual(put(CaloriesBMRActions.caloriesBMRDeleteSuccess(response.data)));
});

test('delete failure path', () => {
  const response = { ok: false };
  const step = stepper(deleteCaloriesBMR(FixtureAPI, { caloriesBMRId: { id: 1 } }));
  // Step 1: Hit the api
  step();
  // Step 2: Failed response.
  expect(step(response)).toEqual(put(CaloriesBMRActions.caloriesBMRDeleteFailure()));
});
