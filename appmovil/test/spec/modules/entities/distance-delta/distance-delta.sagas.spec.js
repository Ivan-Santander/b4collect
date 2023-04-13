import { put } from 'redux-saga/effects';

import FixtureAPI from '../../../../../app/shared/services/fixture-api';
import DistanceDeltaSagas from '../../../../../app/modules/entities/distance-delta/distance-delta.sagas';
import DistanceDeltaActions from '../../../../../app/modules/entities/distance-delta/distance-delta.reducer';

const { getDistanceDelta, getAllDistanceDeltas, updateDistanceDelta, deleteDistanceDelta } = DistanceDeltaSagas;
const stepper = (fn) => (mock) => fn.next(mock).value;

test('get success path', () => {
  const response = FixtureAPI.getDistanceDelta(1);
  const step = stepper(getDistanceDelta(FixtureAPI, { distanceDeltaId: { id: 1 } }));
  // Step 1: Hit the api
  step();
  // Step 2: Successful return and data!
  expect(step(response)).toEqual(put(DistanceDeltaActions.distanceDeltaSuccess(response.data)));
});

test('get failure path', () => {
  const response = { ok: false };
  const step = stepper(getDistanceDelta(FixtureAPI, { distanceDeltaId: { id: 1 } }));
  // Step 1: Hit the api
  step();
  // Step 2: Failed response.
  expect(step(response)).toEqual(put(DistanceDeltaActions.distanceDeltaFailure()));
});

test('getAll success path', () => {
  const response = FixtureAPI.getAllDistanceDeltas();
  const step = stepper(getAllDistanceDeltas(FixtureAPI, { options: { page: 0, sort: 'id,asc', size: 20 } }));
  // Step 1: Hit the api
  step();
  // Step 2: Successful return and data!
  expect(step(response)).toEqual(put(DistanceDeltaActions.distanceDeltaAllSuccess([{ id: 1 }, { id: 2 }])));
});

test('getAll failure path', () => {
  const response = { ok: false };
  const step = stepper(getAllDistanceDeltas(FixtureAPI, { options: { page: 0, sort: 'id,asc', size: 20 } }));
  // Step 1: Hit the api
  step();
  // Step 2: Failed response.
  expect(step(response)).toEqual(put(DistanceDeltaActions.distanceDeltaAllFailure()));
});

test('update success path', () => {
  const response = FixtureAPI.updateDistanceDelta({ id: 1 });
  const step = stepper(updateDistanceDelta(FixtureAPI, { distanceDelta: { id: 1 } }));
  // Step 1: Hit the api
  step();
  // Step 2: Successful return and data!
  expect(step(response)).toEqual(put(DistanceDeltaActions.distanceDeltaUpdateSuccess(response.data)));
});

test('update failure path', () => {
  const response = { ok: false };
  const step = stepper(updateDistanceDelta(FixtureAPI, { distanceDelta: { id: 1 } }));
  // Step 1: Hit the api
  step();
  // Step 2: Failed response.
  expect(step(response)).toEqual(put(DistanceDeltaActions.distanceDeltaUpdateFailure()));
});

test('delete success path', () => {
  const response = FixtureAPI.deleteDistanceDelta({ id: 1 });
  const step = stepper(deleteDistanceDelta(FixtureAPI, { distanceDeltaId: { id: 1 } }));
  // Step 1: Hit the api
  step();
  // Step 2: Successful return and data!
  expect(step(response)).toEqual(put(DistanceDeltaActions.distanceDeltaDeleteSuccess(response.data)));
});

test('delete failure path', () => {
  const response = { ok: false };
  const step = stepper(deleteDistanceDelta(FixtureAPI, { distanceDeltaId: { id: 1 } }));
  // Step 1: Hit the api
  step();
  // Step 2: Failed response.
  expect(step(response)).toEqual(put(DistanceDeltaActions.distanceDeltaDeleteFailure()));
});
