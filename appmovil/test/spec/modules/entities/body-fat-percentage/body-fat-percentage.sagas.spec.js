import { put } from 'redux-saga/effects';

import FixtureAPI from '../../../../../app/shared/services/fixture-api';
import BodyFatPercentageSagas from '../../../../../app/modules/entities/body-fat-percentage/body-fat-percentage.sagas';
import BodyFatPercentageActions from '../../../../../app/modules/entities/body-fat-percentage/body-fat-percentage.reducer';

const { getBodyFatPercentage, getAllBodyFatPercentages, updateBodyFatPercentage, deleteBodyFatPercentage } = BodyFatPercentageSagas;
const stepper = (fn) => (mock) => fn.next(mock).value;

test('get success path', () => {
  const response = FixtureAPI.getBodyFatPercentage(1);
  const step = stepper(getBodyFatPercentage(FixtureAPI, { bodyFatPercentageId: { id: 1 } }));
  // Step 1: Hit the api
  step();
  // Step 2: Successful return and data!
  expect(step(response)).toEqual(put(BodyFatPercentageActions.bodyFatPercentageSuccess(response.data)));
});

test('get failure path', () => {
  const response = { ok: false };
  const step = stepper(getBodyFatPercentage(FixtureAPI, { bodyFatPercentageId: { id: 1 } }));
  // Step 1: Hit the api
  step();
  // Step 2: Failed response.
  expect(step(response)).toEqual(put(BodyFatPercentageActions.bodyFatPercentageFailure()));
});

test('getAll success path', () => {
  const response = FixtureAPI.getAllBodyFatPercentages();
  const step = stepper(getAllBodyFatPercentages(FixtureAPI, { options: { page: 0, sort: 'id,asc', size: 20 } }));
  // Step 1: Hit the api
  step();
  // Step 2: Successful return and data!
  expect(step(response)).toEqual(put(BodyFatPercentageActions.bodyFatPercentageAllSuccess([{ id: 1 }, { id: 2 }])));
});

test('getAll failure path', () => {
  const response = { ok: false };
  const step = stepper(getAllBodyFatPercentages(FixtureAPI, { options: { page: 0, sort: 'id,asc', size: 20 } }));
  // Step 1: Hit the api
  step();
  // Step 2: Failed response.
  expect(step(response)).toEqual(put(BodyFatPercentageActions.bodyFatPercentageAllFailure()));
});

test('update success path', () => {
  const response = FixtureAPI.updateBodyFatPercentage({ id: 1 });
  const step = stepper(updateBodyFatPercentage(FixtureAPI, { bodyFatPercentage: { id: 1 } }));
  // Step 1: Hit the api
  step();
  // Step 2: Successful return and data!
  expect(step(response)).toEqual(put(BodyFatPercentageActions.bodyFatPercentageUpdateSuccess(response.data)));
});

test('update failure path', () => {
  const response = { ok: false };
  const step = stepper(updateBodyFatPercentage(FixtureAPI, { bodyFatPercentage: { id: 1 } }));
  // Step 1: Hit the api
  step();
  // Step 2: Failed response.
  expect(step(response)).toEqual(put(BodyFatPercentageActions.bodyFatPercentageUpdateFailure()));
});

test('delete success path', () => {
  const response = FixtureAPI.deleteBodyFatPercentage({ id: 1 });
  const step = stepper(deleteBodyFatPercentage(FixtureAPI, { bodyFatPercentageId: { id: 1 } }));
  // Step 1: Hit the api
  step();
  // Step 2: Successful return and data!
  expect(step(response)).toEqual(put(BodyFatPercentageActions.bodyFatPercentageDeleteSuccess(response.data)));
});

test('delete failure path', () => {
  const response = { ok: false };
  const step = stepper(deleteBodyFatPercentage(FixtureAPI, { bodyFatPercentageId: { id: 1 } }));
  // Step 1: Hit the api
  step();
  // Step 2: Failed response.
  expect(step(response)).toEqual(put(BodyFatPercentageActions.bodyFatPercentageDeleteFailure()));
});
