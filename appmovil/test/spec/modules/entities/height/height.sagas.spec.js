import { put } from 'redux-saga/effects';

import FixtureAPI from '../../../../../app/shared/services/fixture-api';
import HeightSagas from '../../../../../app/modules/entities/height/height.sagas';
import HeightActions from '../../../../../app/modules/entities/height/height.reducer';

const { getHeight, getAllHeights, updateHeight, deleteHeight } = HeightSagas;
const stepper = (fn) => (mock) => fn.next(mock).value;

test('get success path', () => {
  const response = FixtureAPI.getHeight(1);
  const step = stepper(getHeight(FixtureAPI, { heightId: { id: 1 } }));
  // Step 1: Hit the api
  step();
  // Step 2: Successful return and data!
  expect(step(response)).toEqual(put(HeightActions.heightSuccess(response.data)));
});

test('get failure path', () => {
  const response = { ok: false };
  const step = stepper(getHeight(FixtureAPI, { heightId: { id: 1 } }));
  // Step 1: Hit the api
  step();
  // Step 2: Failed response.
  expect(step(response)).toEqual(put(HeightActions.heightFailure()));
});

test('getAll success path', () => {
  const response = FixtureAPI.getAllHeights();
  const step = stepper(getAllHeights(FixtureAPI, { options: { page: 0, sort: 'id,asc', size: 20 } }));
  // Step 1: Hit the api
  step();
  // Step 2: Successful return and data!
  expect(step(response)).toEqual(put(HeightActions.heightAllSuccess([{ id: 1 }, { id: 2 }])));
});

test('getAll failure path', () => {
  const response = { ok: false };
  const step = stepper(getAllHeights(FixtureAPI, { options: { page: 0, sort: 'id,asc', size: 20 } }));
  // Step 1: Hit the api
  step();
  // Step 2: Failed response.
  expect(step(response)).toEqual(put(HeightActions.heightAllFailure()));
});

test('update success path', () => {
  const response = FixtureAPI.updateHeight({ id: 1 });
  const step = stepper(updateHeight(FixtureAPI, { height: { id: 1 } }));
  // Step 1: Hit the api
  step();
  // Step 2: Successful return and data!
  expect(step(response)).toEqual(put(HeightActions.heightUpdateSuccess(response.data)));
});

test('update failure path', () => {
  const response = { ok: false };
  const step = stepper(updateHeight(FixtureAPI, { height: { id: 1 } }));
  // Step 1: Hit the api
  step();
  // Step 2: Failed response.
  expect(step(response)).toEqual(put(HeightActions.heightUpdateFailure()));
});

test('delete success path', () => {
  const response = FixtureAPI.deleteHeight({ id: 1 });
  const step = stepper(deleteHeight(FixtureAPI, { heightId: { id: 1 } }));
  // Step 1: Hit the api
  step();
  // Step 2: Successful return and data!
  expect(step(response)).toEqual(put(HeightActions.heightDeleteSuccess(response.data)));
});

test('delete failure path', () => {
  const response = { ok: false };
  const step = stepper(deleteHeight(FixtureAPI, { heightId: { id: 1 } }));
  // Step 1: Hit the api
  step();
  // Step 2: Failed response.
  expect(step(response)).toEqual(put(HeightActions.heightDeleteFailure()));
});
