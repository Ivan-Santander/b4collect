import { put } from 'redux-saga/effects';

import FixtureAPI from '../../../../../app/shared/services/fixture-api';
import ActiveMinutesSagas from '../../../../../app/modules/entities/active-minutes/active-minutes.sagas';
import ActiveMinutesActions from '../../../../../app/modules/entities/active-minutes/active-minutes.reducer';

const { getActiveMinutes, getAllActiveMinutes, updateActiveMinutes, deleteActiveMinutes } = ActiveMinutesSagas;
const stepper = (fn) => (mock) => fn.next(mock).value;

test('get success path', () => {
  const response = FixtureAPI.getActiveMinutes(1);
  const step = stepper(getActiveMinutes(FixtureAPI, { activeMinutesId: { id: 1 } }));
  // Step 1: Hit the api
  step();
  // Step 2: Successful return and data!
  expect(step(response)).toEqual(put(ActiveMinutesActions.activeMinutesSuccess(response.data)));
});

test('get failure path', () => {
  const response = { ok: false };
  const step = stepper(getActiveMinutes(FixtureAPI, { activeMinutesId: { id: 1 } }));
  // Step 1: Hit the api
  step();
  // Step 2: Failed response.
  expect(step(response)).toEqual(put(ActiveMinutesActions.activeMinutesFailure()));
});

test('getAll success path', () => {
  const response = FixtureAPI.getAllActiveMinutes();
  const step = stepper(getAllActiveMinutes(FixtureAPI, { options: { page: 0, sort: 'id,asc', size: 20 } }));
  // Step 1: Hit the api
  step();
  // Step 2: Successful return and data!
  expect(step(response)).toEqual(put(ActiveMinutesActions.activeMinutesAllSuccess([{ id: 1 }, { id: 2 }])));
});

test('getAll failure path', () => {
  const response = { ok: false };
  const step = stepper(getAllActiveMinutes(FixtureAPI, { options: { page: 0, sort: 'id,asc', size: 20 } }));
  // Step 1: Hit the api
  step();
  // Step 2: Failed response.
  expect(step(response)).toEqual(put(ActiveMinutesActions.activeMinutesAllFailure()));
});

test('update success path', () => {
  const response = FixtureAPI.updateActiveMinutes({ id: 1 });
  const step = stepper(updateActiveMinutes(FixtureAPI, { activeMinutes: { id: 1 } }));
  // Step 1: Hit the api
  step();
  // Step 2: Successful return and data!
  expect(step(response)).toEqual(put(ActiveMinutesActions.activeMinutesUpdateSuccess(response.data)));
});

test('update failure path', () => {
  const response = { ok: false };
  const step = stepper(updateActiveMinutes(FixtureAPI, { activeMinutes: { id: 1 } }));
  // Step 1: Hit the api
  step();
  // Step 2: Failed response.
  expect(step(response)).toEqual(put(ActiveMinutesActions.activeMinutesUpdateFailure()));
});

test('delete success path', () => {
  const response = FixtureAPI.deleteActiveMinutes({ id: 1 });
  const step = stepper(deleteActiveMinutes(FixtureAPI, { activeMinutesId: { id: 1 } }));
  // Step 1: Hit the api
  step();
  // Step 2: Successful return and data!
  expect(step(response)).toEqual(put(ActiveMinutesActions.activeMinutesDeleteSuccess(response.data)));
});

test('delete failure path', () => {
  const response = { ok: false };
  const step = stepper(deleteActiveMinutes(FixtureAPI, { activeMinutesId: { id: 1 } }));
  // Step 1: Hit the api
  step();
  // Step 2: Failed response.
  expect(step(response)).toEqual(put(ActiveMinutesActions.activeMinutesDeleteFailure()));
});
