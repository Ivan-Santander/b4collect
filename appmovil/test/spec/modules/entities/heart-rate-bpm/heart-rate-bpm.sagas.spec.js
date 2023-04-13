import { put } from 'redux-saga/effects';

import FixtureAPI from '../../../../../app/shared/services/fixture-api';
import HeartRateBpmSagas from '../../../../../app/modules/entities/heart-rate-bpm/heart-rate-bpm.sagas';
import HeartRateBpmActions from '../../../../../app/modules/entities/heart-rate-bpm/heart-rate-bpm.reducer';

const { getHeartRateBpm, getAllHeartRateBpms, updateHeartRateBpm, deleteHeartRateBpm } = HeartRateBpmSagas;
const stepper = (fn) => (mock) => fn.next(mock).value;

test('get success path', () => {
  const response = FixtureAPI.getHeartRateBpm(1);
  const step = stepper(getHeartRateBpm(FixtureAPI, { heartRateBpmId: { id: 1 } }));
  // Step 1: Hit the api
  step();
  // Step 2: Successful return and data!
  expect(step(response)).toEqual(put(HeartRateBpmActions.heartRateBpmSuccess(response.data)));
});

test('get failure path', () => {
  const response = { ok: false };
  const step = stepper(getHeartRateBpm(FixtureAPI, { heartRateBpmId: { id: 1 } }));
  // Step 1: Hit the api
  step();
  // Step 2: Failed response.
  expect(step(response)).toEqual(put(HeartRateBpmActions.heartRateBpmFailure()));
});

test('getAll success path', () => {
  const response = FixtureAPI.getAllHeartRateBpms();
  const step = stepper(getAllHeartRateBpms(FixtureAPI, { options: { page: 0, sort: 'id,asc', size: 20 } }));
  // Step 1: Hit the api
  step();
  // Step 2: Successful return and data!
  expect(step(response)).toEqual(put(HeartRateBpmActions.heartRateBpmAllSuccess([{ id: 1 }, { id: 2 }])));
});

test('getAll failure path', () => {
  const response = { ok: false };
  const step = stepper(getAllHeartRateBpms(FixtureAPI, { options: { page: 0, sort: 'id,asc', size: 20 } }));
  // Step 1: Hit the api
  step();
  // Step 2: Failed response.
  expect(step(response)).toEqual(put(HeartRateBpmActions.heartRateBpmAllFailure()));
});

test('update success path', () => {
  const response = FixtureAPI.updateHeartRateBpm({ id: 1 });
  const step = stepper(updateHeartRateBpm(FixtureAPI, { heartRateBpm: { id: 1 } }));
  // Step 1: Hit the api
  step();
  // Step 2: Successful return and data!
  expect(step(response)).toEqual(put(HeartRateBpmActions.heartRateBpmUpdateSuccess(response.data)));
});

test('update failure path', () => {
  const response = { ok: false };
  const step = stepper(updateHeartRateBpm(FixtureAPI, { heartRateBpm: { id: 1 } }));
  // Step 1: Hit the api
  step();
  // Step 2: Failed response.
  expect(step(response)).toEqual(put(HeartRateBpmActions.heartRateBpmUpdateFailure()));
});

test('delete success path', () => {
  const response = FixtureAPI.deleteHeartRateBpm({ id: 1 });
  const step = stepper(deleteHeartRateBpm(FixtureAPI, { heartRateBpmId: { id: 1 } }));
  // Step 1: Hit the api
  step();
  // Step 2: Successful return and data!
  expect(step(response)).toEqual(put(HeartRateBpmActions.heartRateBpmDeleteSuccess(response.data)));
});

test('delete failure path', () => {
  const response = { ok: false };
  const step = stepper(deleteHeartRateBpm(FixtureAPI, { heartRateBpmId: { id: 1 } }));
  // Step 1: Hit the api
  step();
  // Step 2: Failed response.
  expect(step(response)).toEqual(put(HeartRateBpmActions.heartRateBpmDeleteFailure()));
});
