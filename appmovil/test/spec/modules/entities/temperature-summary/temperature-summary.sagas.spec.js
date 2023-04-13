import { put } from 'redux-saga/effects';

import FixtureAPI from '../../../../../app/shared/services/fixture-api';
import TemperatureSummarySagas from '../../../../../app/modules/entities/temperature-summary/temperature-summary.sagas';
import TemperatureSummaryActions from '../../../../../app/modules/entities/temperature-summary/temperature-summary.reducer';

const { getTemperatureSummary, getAllTemperatureSummaries, updateTemperatureSummary, deleteTemperatureSummary } = TemperatureSummarySagas;
const stepper = (fn) => (mock) => fn.next(mock).value;

test('get success path', () => {
  const response = FixtureAPI.getTemperatureSummary(1);
  const step = stepper(getTemperatureSummary(FixtureAPI, { temperatureSummaryId: { id: 1 } }));
  // Step 1: Hit the api
  step();
  // Step 2: Successful return and data!
  expect(step(response)).toEqual(put(TemperatureSummaryActions.temperatureSummarySuccess(response.data)));
});

test('get failure path', () => {
  const response = { ok: false };
  const step = stepper(getTemperatureSummary(FixtureAPI, { temperatureSummaryId: { id: 1 } }));
  // Step 1: Hit the api
  step();
  // Step 2: Failed response.
  expect(step(response)).toEqual(put(TemperatureSummaryActions.temperatureSummaryFailure()));
});

test('getAll success path', () => {
  const response = FixtureAPI.getAllTemperatureSummaries();
  const step = stepper(getAllTemperatureSummaries(FixtureAPI, { options: { page: 0, sort: 'id,asc', size: 20 } }));
  // Step 1: Hit the api
  step();
  // Step 2: Successful return and data!
  expect(step(response)).toEqual(put(TemperatureSummaryActions.temperatureSummaryAllSuccess([{ id: 1 }, { id: 2 }])));
});

test('getAll failure path', () => {
  const response = { ok: false };
  const step = stepper(getAllTemperatureSummaries(FixtureAPI, { options: { page: 0, sort: 'id,asc', size: 20 } }));
  // Step 1: Hit the api
  step();
  // Step 2: Failed response.
  expect(step(response)).toEqual(put(TemperatureSummaryActions.temperatureSummaryAllFailure()));
});

test('update success path', () => {
  const response = FixtureAPI.updateTemperatureSummary({ id: 1 });
  const step = stepper(updateTemperatureSummary(FixtureAPI, { temperatureSummary: { id: 1 } }));
  // Step 1: Hit the api
  step();
  // Step 2: Successful return and data!
  expect(step(response)).toEqual(put(TemperatureSummaryActions.temperatureSummaryUpdateSuccess(response.data)));
});

test('update failure path', () => {
  const response = { ok: false };
  const step = stepper(updateTemperatureSummary(FixtureAPI, { temperatureSummary: { id: 1 } }));
  // Step 1: Hit the api
  step();
  // Step 2: Failed response.
  expect(step(response)).toEqual(put(TemperatureSummaryActions.temperatureSummaryUpdateFailure()));
});

test('delete success path', () => {
  const response = FixtureAPI.deleteTemperatureSummary({ id: 1 });
  const step = stepper(deleteTemperatureSummary(FixtureAPI, { temperatureSummaryId: { id: 1 } }));
  // Step 1: Hit the api
  step();
  // Step 2: Successful return and data!
  expect(step(response)).toEqual(put(TemperatureSummaryActions.temperatureSummaryDeleteSuccess(response.data)));
});

test('delete failure path', () => {
  const response = { ok: false };
  const step = stepper(deleteTemperatureSummary(FixtureAPI, { temperatureSummaryId: { id: 1 } }));
  // Step 1: Hit the api
  step();
  // Step 2: Failed response.
  expect(step(response)).toEqual(put(TemperatureSummaryActions.temperatureSummaryDeleteFailure()));
});
