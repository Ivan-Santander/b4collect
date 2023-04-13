import { put } from 'redux-saga/effects';

import FixtureAPI from '../../../../../app/shared/services/fixture-api';
import HeartMinutesSagas from '../../../../../app/modules/entities/heart-minutes/heart-minutes.sagas';
import HeartMinutesActions from '../../../../../app/modules/entities/heart-minutes/heart-minutes.reducer';

const { getHeartMinutes, getAllHeartMinutes, updateHeartMinutes, deleteHeartMinutes } = HeartMinutesSagas;
const stepper = (fn) => (mock) => fn.next(mock).value;

test('get success path', () => {
  const response = FixtureAPI.getHeartMinutes(1);
  const step = stepper(getHeartMinutes(FixtureAPI, { heartMinutesId: { id: 1 } }));
  // Step 1: Hit the api
  step();
  // Step 2: Successful return and data!
  expect(step(response)).toEqual(put(HeartMinutesActions.heartMinutesSuccess(response.data)));
});

test('get failure path', () => {
  const response = { ok: false };
  const step = stepper(getHeartMinutes(FixtureAPI, { heartMinutesId: { id: 1 } }));
  // Step 1: Hit the api
  step();
  // Step 2: Failed response.
  expect(step(response)).toEqual(put(HeartMinutesActions.heartMinutesFailure()));
});

test('getAll success path', () => {
  const response = FixtureAPI.getAllHeartMinutes();
  const step = stepper(getAllHeartMinutes(FixtureAPI, { options: { page: 0, sort: 'id,asc', size: 20 } }));
  // Step 1: Hit the api
  step();
  // Step 2: Successful return and data!
  expect(step(response)).toEqual(put(HeartMinutesActions.heartMinutesAllSuccess([{ id: 1 }, { id: 2 }])));
});

test('getAll failure path', () => {
  const response = { ok: false };
  const step = stepper(getAllHeartMinutes(FixtureAPI, { options: { page: 0, sort: 'id,asc', size: 20 } }));
  // Step 1: Hit the api
  step();
  // Step 2: Failed response.
  expect(step(response)).toEqual(put(HeartMinutesActions.heartMinutesAllFailure()));
});

test('update success path', () => {
  const response = FixtureAPI.updateHeartMinutes({ id: 1 });
  const step = stepper(updateHeartMinutes(FixtureAPI, { heartMinutes: { id: 1 } }));
  // Step 1: Hit the api
  step();
  // Step 2: Successful return and data!
  expect(step(response)).toEqual(put(HeartMinutesActions.heartMinutesUpdateSuccess(response.data)));
});

test('update failure path', () => {
  const response = { ok: false };
  const step = stepper(updateHeartMinutes(FixtureAPI, { heartMinutes: { id: 1 } }));
  // Step 1: Hit the api
  step();
  // Step 2: Failed response.
  expect(step(response)).toEqual(put(HeartMinutesActions.heartMinutesUpdateFailure()));
});

test('delete success path', () => {
  const response = FixtureAPI.deleteHeartMinutes({ id: 1 });
  const step = stepper(deleteHeartMinutes(FixtureAPI, { heartMinutesId: { id: 1 } }));
  // Step 1: Hit the api
  step();
  // Step 2: Successful return and data!
  expect(step(response)).toEqual(put(HeartMinutesActions.heartMinutesDeleteSuccess(response.data)));
});

test('delete failure path', () => {
  const response = { ok: false };
  const step = stepper(deleteHeartMinutes(FixtureAPI, { heartMinutesId: { id: 1 } }));
  // Step 1: Hit the api
  step();
  // Step 2: Failed response.
  expect(step(response)).toEqual(put(HeartMinutesActions.heartMinutesDeleteFailure()));
});
