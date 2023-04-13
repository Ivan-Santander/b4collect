import Actions, { reducer, INITIAL_STATE } from '../../../../../app/modules/entities/body-temperature/body-temperature.reducer';

test('attempt retrieving a single bodyTemperature', () => {
  const state = reducer(INITIAL_STATE, Actions.bodyTemperatureRequest({ id: 1 }));

  expect(state.fetchingOne).toBe(true);
  expect(state.bodyTemperature).toEqual({ id: undefined });
});

test('attempt retrieving a list of bodyTemperature', () => {
  const state = reducer(INITIAL_STATE, Actions.bodyTemperatureAllRequest({ id: 1 }));

  expect(state.fetchingAll).toBe(true);
  expect(state.bodyTemperatureList).toEqual([]);
});

test('attempt updating a bodyTemperature', () => {
  const state = reducer(INITIAL_STATE, Actions.bodyTemperatureUpdateRequest({ id: 1 }));

  expect(state.updating).toBe(true);
});
test('attempt to deleting a bodyTemperature', () => {
  const state = reducer(INITIAL_STATE, Actions.bodyTemperatureDeleteRequest({ id: 1 }));

  expect(state.deleting).toBe(true);
});

test('success retrieving a bodyTemperature', () => {
  const state = reducer(INITIAL_STATE, Actions.bodyTemperatureSuccess({ id: 1 }));

  expect(state.fetchingOne).toBe(false);
  expect(state.errorOne).toBe(null);
  expect(state.bodyTemperature).toEqual({ id: 1 });
});

test('success retrieving a list of bodyTemperature', () => {
  const state = reducer(
    INITIAL_STATE,
    Actions.bodyTemperatureAllSuccess([{ id: 1 }, { id: 2 }], {
      link: '</?page=1>; rel="last",</?page=0>; rel="first"',
      'x-total-count': 5,
    }),
  );

  expect(state.fetchingAll).toBe(false);
  expect(state.errorAll).toBe(null);
  expect(state.bodyTemperatureList).toEqual([{ id: 1 }, { id: 2 }]);
  expect(state.links).toEqual({ first: 0, last: 1 });
  expect(state.totalItems).toEqual(5);
});

test('success updating a bodyTemperature', () => {
  const state = reducer(INITIAL_STATE, Actions.bodyTemperatureUpdateSuccess({ id: 1 }));

  expect(state.updating).toBe(false);
  expect(state.errorUpdating).toBe(null);
  expect(state.bodyTemperature).toEqual({ id: 1 });
});
test('success deleting a bodyTemperature', () => {
  const state = reducer(INITIAL_STATE, Actions.bodyTemperatureDeleteSuccess());

  expect(state.deleting).toBe(false);
  expect(state.errorDeleting).toBe(null);
  expect(state.bodyTemperature).toEqual({ id: undefined });
});

test('failure retrieving a bodyTemperature', () => {
  const state = reducer(INITIAL_STATE, Actions.bodyTemperatureFailure({ error: 'Not found' }));

  expect(state.fetchingOne).toBe(false);
  expect(state.errorOne).toEqual({ error: 'Not found' });
  expect(state.bodyTemperature).toEqual({ id: undefined });
});

test('failure retrieving a list of bodyTemperature', () => {
  const state = reducer(INITIAL_STATE, Actions.bodyTemperatureAllFailure({ error: 'Not found' }));

  expect(state.fetchingAll).toBe(false);
  expect(state.errorAll).toEqual({ error: 'Not found' });
  expect(state.bodyTemperatureList).toEqual([]);
});

test('failure updating a bodyTemperature', () => {
  const state = reducer(INITIAL_STATE, Actions.bodyTemperatureUpdateFailure({ error: 'Not found' }));

  expect(state.updating).toBe(false);
  expect(state.errorUpdating).toEqual({ error: 'Not found' });
  expect(state.bodyTemperature).toEqual(INITIAL_STATE.bodyTemperature);
});
test('failure deleting a bodyTemperature', () => {
  const state = reducer(INITIAL_STATE, Actions.bodyTemperatureDeleteFailure({ error: 'Not found' }));

  expect(state.deleting).toBe(false);
  expect(state.errorDeleting).toEqual({ error: 'Not found' });
  expect(state.bodyTemperature).toEqual(INITIAL_STATE.bodyTemperature);
});

test('resetting state for bodyTemperature', () => {
  const state = reducer({ ...INITIAL_STATE, deleting: true }, Actions.bodyTemperatureReset());
  expect(state).toEqual(INITIAL_STATE);
});
