import { put } from 'redux-saga/effects';

import FixtureAPI from '../../../../../app/shared/services/fixture-api';
import LocationSampleSagas from '../../../../../app/modules/entities/location-sample/location-sample.sagas';
import LocationSampleActions from '../../../../../app/modules/entities/location-sample/location-sample.reducer';

const { getLocationSample, getAllLocationSamples, updateLocationSample, deleteLocationSample } = LocationSampleSagas;
const stepper = (fn) => (mock) => fn.next(mock).value;

test('get success path', () => {
  const response = FixtureAPI.getLocationSample(1);
  const step = stepper(getLocationSample(FixtureAPI, { locationSampleId: { id: 1 } }));
  // Step 1: Hit the api
  step();
  // Step 2: Successful return and data!
  expect(step(response)).toEqual(put(LocationSampleActions.locationSampleSuccess(response.data)));
});

test('get failure path', () => {
  const response = { ok: false };
  const step = stepper(getLocationSample(FixtureAPI, { locationSampleId: { id: 1 } }));
  // Step 1: Hit the api
  step();
  // Step 2: Failed response.
  expect(step(response)).toEqual(put(LocationSampleActions.locationSampleFailure()));
});

test('getAll success path', () => {
  const response = FixtureAPI.getAllLocationSamples();
  const step = stepper(getAllLocationSamples(FixtureAPI, { options: { page: 0, sort: 'id,asc', size: 20 } }));
  // Step 1: Hit the api
  step();
  // Step 2: Successful return and data!
  expect(step(response)).toEqual(put(LocationSampleActions.locationSampleAllSuccess([{ id: 1 }, { id: 2 }])));
});

test('getAll failure path', () => {
  const response = { ok: false };
  const step = stepper(getAllLocationSamples(FixtureAPI, { options: { page: 0, sort: 'id,asc', size: 20 } }));
  // Step 1: Hit the api
  step();
  // Step 2: Failed response.
  expect(step(response)).toEqual(put(LocationSampleActions.locationSampleAllFailure()));
});

test('update success path', () => {
  const response = FixtureAPI.updateLocationSample({ id: 1 });
  const step = stepper(updateLocationSample(FixtureAPI, { locationSample: { id: 1 } }));
  // Step 1: Hit the api
  step();
  // Step 2: Successful return and data!
  expect(step(response)).toEqual(put(LocationSampleActions.locationSampleUpdateSuccess(response.data)));
});

test('update failure path', () => {
  const response = { ok: false };
  const step = stepper(updateLocationSample(FixtureAPI, { locationSample: { id: 1 } }));
  // Step 1: Hit the api
  step();
  // Step 2: Failed response.
  expect(step(response)).toEqual(put(LocationSampleActions.locationSampleUpdateFailure()));
});

test('delete success path', () => {
  const response = FixtureAPI.deleteLocationSample({ id: 1 });
  const step = stepper(deleteLocationSample(FixtureAPI, { locationSampleId: { id: 1 } }));
  // Step 1: Hit the api
  step();
  // Step 2: Successful return and data!
  expect(step(response)).toEqual(put(LocationSampleActions.locationSampleDeleteSuccess(response.data)));
});

test('delete failure path', () => {
  const response = { ok: false };
  const step = stepper(deleteLocationSample(FixtureAPI, { locationSampleId: { id: 1 } }));
  // Step 1: Hit the api
  step();
  // Step 2: Failed response.
  expect(step(response)).toEqual(put(LocationSampleActions.locationSampleDeleteFailure()));
});
