import { put } from 'redux-saga/effects';

import FixtureAPI from '../../../../../app/shared/services/fixture-api';
import CaloriesExpendedSagas from '../../../../../app/modules/entities/calories-expended/calories-expended.sagas';
import CaloriesExpendedActions from '../../../../../app/modules/entities/calories-expended/calories-expended.reducer';

const { getCaloriesExpended, getAllCaloriesExpendeds, updateCaloriesExpended, deleteCaloriesExpended } = CaloriesExpendedSagas;
const stepper = (fn) => (mock) => fn.next(mock).value;

test('get success path', () => {
  const response = FixtureAPI.getCaloriesExpended(1);
  const step = stepper(getCaloriesExpended(FixtureAPI, { caloriesExpendedId: { id: 1 } }));
  // Step 1: Hit the api
  step();
  // Step 2: Successful return and data!
  expect(step(response)).toEqual(put(CaloriesExpendedActions.caloriesExpendedSuccess(response.data)));
});

test('get failure path', () => {
  const response = { ok: false };
  const step = stepper(getCaloriesExpended(FixtureAPI, { caloriesExpendedId: { id: 1 } }));
  // Step 1: Hit the api
  step();
  // Step 2: Failed response.
  expect(step(response)).toEqual(put(CaloriesExpendedActions.caloriesExpendedFailure()));
});

test('getAll success path', () => {
  const response = FixtureAPI.getAllCaloriesExpendeds();
  const step = stepper(getAllCaloriesExpendeds(FixtureAPI, { options: { page: 0, sort: 'id,asc', size: 20 } }));
  // Step 1: Hit the api
  step();
  // Step 2: Successful return and data!
  expect(step(response)).toEqual(put(CaloriesExpendedActions.caloriesExpendedAllSuccess([{ id: 1 }, { id: 2 }])));
});

test('getAll failure path', () => {
  const response = { ok: false };
  const step = stepper(getAllCaloriesExpendeds(FixtureAPI, { options: { page: 0, sort: 'id,asc', size: 20 } }));
  // Step 1: Hit the api
  step();
  // Step 2: Failed response.
  expect(step(response)).toEqual(put(CaloriesExpendedActions.caloriesExpendedAllFailure()));
});

test('update success path', () => {
  const response = FixtureAPI.updateCaloriesExpended({ id: 1 });
  const step = stepper(updateCaloriesExpended(FixtureAPI, { caloriesExpended: { id: 1 } }));
  // Step 1: Hit the api
  step();
  // Step 2: Successful return and data!
  expect(step(response)).toEqual(put(CaloriesExpendedActions.caloriesExpendedUpdateSuccess(response.data)));
});

test('update failure path', () => {
  const response = { ok: false };
  const step = stepper(updateCaloriesExpended(FixtureAPI, { caloriesExpended: { id: 1 } }));
  // Step 1: Hit the api
  step();
  // Step 2: Failed response.
  expect(step(response)).toEqual(put(CaloriesExpendedActions.caloriesExpendedUpdateFailure()));
});

test('delete success path', () => {
  const response = FixtureAPI.deleteCaloriesExpended({ id: 1 });
  const step = stepper(deleteCaloriesExpended(FixtureAPI, { caloriesExpendedId: { id: 1 } }));
  // Step 1: Hit the api
  step();
  // Step 2: Successful return and data!
  expect(step(response)).toEqual(put(CaloriesExpendedActions.caloriesExpendedDeleteSuccess(response.data)));
});

test('delete failure path', () => {
  const response = { ok: false };
  const step = stepper(deleteCaloriesExpended(FixtureAPI, { caloriesExpendedId: { id: 1 } }));
  // Step 1: Hit the api
  step();
  // Step 2: Failed response.
  expect(step(response)).toEqual(put(CaloriesExpendedActions.caloriesExpendedDeleteFailure()));
});
