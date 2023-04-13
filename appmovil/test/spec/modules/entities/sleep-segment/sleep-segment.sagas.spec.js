import { put } from 'redux-saga/effects';

import FixtureAPI from '../../../../../app/shared/services/fixture-api';
import SleepSegmentSagas from '../../../../../app/modules/entities/sleep-segment/sleep-segment.sagas';
import SleepSegmentActions from '../../../../../app/modules/entities/sleep-segment/sleep-segment.reducer';

const { getSleepSegment, getAllSleepSegments, updateSleepSegment, deleteSleepSegment } = SleepSegmentSagas;
const stepper = (fn) => (mock) => fn.next(mock).value;

test('get success path', () => {
  const response = FixtureAPI.getSleepSegment(1);
  const step = stepper(getSleepSegment(FixtureAPI, { sleepSegmentId: { id: 1 } }));
  // Step 1: Hit the api
  step();
  // Step 2: Successful return and data!
  expect(step(response)).toEqual(put(SleepSegmentActions.sleepSegmentSuccess(response.data)));
});

test('get failure path', () => {
  const response = { ok: false };
  const step = stepper(getSleepSegment(FixtureAPI, { sleepSegmentId: { id: 1 } }));
  // Step 1: Hit the api
  step();
  // Step 2: Failed response.
  expect(step(response)).toEqual(put(SleepSegmentActions.sleepSegmentFailure()));
});

test('getAll success path', () => {
  const response = FixtureAPI.getAllSleepSegments();
  const step = stepper(getAllSleepSegments(FixtureAPI, { options: { page: 0, sort: 'id,asc', size: 20 } }));
  // Step 1: Hit the api
  step();
  // Step 2: Successful return and data!
  expect(step(response)).toEqual(put(SleepSegmentActions.sleepSegmentAllSuccess([{ id: 1 }, { id: 2 }])));
});

test('getAll failure path', () => {
  const response = { ok: false };
  const step = stepper(getAllSleepSegments(FixtureAPI, { options: { page: 0, sort: 'id,asc', size: 20 } }));
  // Step 1: Hit the api
  step();
  // Step 2: Failed response.
  expect(step(response)).toEqual(put(SleepSegmentActions.sleepSegmentAllFailure()));
});

test('update success path', () => {
  const response = FixtureAPI.updateSleepSegment({ id: 1 });
  const step = stepper(updateSleepSegment(FixtureAPI, { sleepSegment: { id: 1 } }));
  // Step 1: Hit the api
  step();
  // Step 2: Successful return and data!
  expect(step(response)).toEqual(put(SleepSegmentActions.sleepSegmentUpdateSuccess(response.data)));
});

test('update failure path', () => {
  const response = { ok: false };
  const step = stepper(updateSleepSegment(FixtureAPI, { sleepSegment: { id: 1 } }));
  // Step 1: Hit the api
  step();
  // Step 2: Failed response.
  expect(step(response)).toEqual(put(SleepSegmentActions.sleepSegmentUpdateFailure()));
});

test('delete success path', () => {
  const response = FixtureAPI.deleteSleepSegment({ id: 1 });
  const step = stepper(deleteSleepSegment(FixtureAPI, { sleepSegmentId: { id: 1 } }));
  // Step 1: Hit the api
  step();
  // Step 2: Successful return and data!
  expect(step(response)).toEqual(put(SleepSegmentActions.sleepSegmentDeleteSuccess(response.data)));
});

test('delete failure path', () => {
  const response = { ok: false };
  const step = stepper(deleteSleepSegment(FixtureAPI, { sleepSegmentId: { id: 1 } }));
  // Step 1: Hit the api
  step();
  // Step 2: Failed response.
  expect(step(response)).toEqual(put(SleepSegmentActions.sleepSegmentDeleteFailure()));
});
