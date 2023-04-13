import Actions, { reducer, INITIAL_STATE } from '../../../../../app/modules/entities/step-count-cadence/step-count-cadence.reducer';

test('attempt retrieving a single stepCountCadence', () => {
  const state = reducer(INITIAL_STATE, Actions.stepCountCadenceRequest({ id: 1 }));

  expect(state.fetchingOne).toBe(true);
  expect(state.stepCountCadence).toEqual({ id: undefined });
});

test('attempt retrieving a list of stepCountCadence', () => {
  const state = reducer(INITIAL_STATE, Actions.stepCountCadenceAllRequest({ id: 1 }));

  expect(state.fetchingAll).toBe(true);
  expect(state.stepCountCadenceList).toEqual([]);
});

test('attempt updating a stepCountCadence', () => {
  const state = reducer(INITIAL_STATE, Actions.stepCountCadenceUpdateRequest({ id: 1 }));

  expect(state.updating).toBe(true);
});
test('attempt to deleting a stepCountCadence', () => {
  const state = reducer(INITIAL_STATE, Actions.stepCountCadenceDeleteRequest({ id: 1 }));

  expect(state.deleting).toBe(true);
});

test('success retrieving a stepCountCadence', () => {
  const state = reducer(INITIAL_STATE, Actions.stepCountCadenceSuccess({ id: 1 }));

  expect(state.fetchingOne).toBe(false);
  expect(state.errorOne).toBe(null);
  expect(state.stepCountCadence).toEqual({ id: 1 });
});

test('success retrieving a list of stepCountCadence', () => {
  const state = reducer(
    INITIAL_STATE,
    Actions.stepCountCadenceAllSuccess([{ id: 1 }, { id: 2 }], {
      link: '</?page=1>; rel="last",</?page=0>; rel="first"',
      'x-total-count': 5,
    }),
  );

  expect(state.fetchingAll).toBe(false);
  expect(state.errorAll).toBe(null);
  expect(state.stepCountCadenceList).toEqual([{ id: 1 }, { id: 2 }]);
  expect(state.links).toEqual({ first: 0, last: 1 });
  expect(state.totalItems).toEqual(5);
});

test('success updating a stepCountCadence', () => {
  const state = reducer(INITIAL_STATE, Actions.stepCountCadenceUpdateSuccess({ id: 1 }));

  expect(state.updating).toBe(false);
  expect(state.errorUpdating).toBe(null);
  expect(state.stepCountCadence).toEqual({ id: 1 });
});
test('success deleting a stepCountCadence', () => {
  const state = reducer(INITIAL_STATE, Actions.stepCountCadenceDeleteSuccess());

  expect(state.deleting).toBe(false);
  expect(state.errorDeleting).toBe(null);
  expect(state.stepCountCadence).toEqual({ id: undefined });
});

test('failure retrieving a stepCountCadence', () => {
  const state = reducer(INITIAL_STATE, Actions.stepCountCadenceFailure({ error: 'Not found' }));

  expect(state.fetchingOne).toBe(false);
  expect(state.errorOne).toEqual({ error: 'Not found' });
  expect(state.stepCountCadence).toEqual({ id: undefined });
});

test('failure retrieving a list of stepCountCadence', () => {
  const state = reducer(INITIAL_STATE, Actions.stepCountCadenceAllFailure({ error: 'Not found' }));

  expect(state.fetchingAll).toBe(false);
  expect(state.errorAll).toEqual({ error: 'Not found' });
  expect(state.stepCountCadenceList).toEqual([]);
});

test('failure updating a stepCountCadence', () => {
  const state = reducer(INITIAL_STATE, Actions.stepCountCadenceUpdateFailure({ error: 'Not found' }));

  expect(state.updating).toBe(false);
  expect(state.errorUpdating).toEqual({ error: 'Not found' });
  expect(state.stepCountCadence).toEqual(INITIAL_STATE.stepCountCadence);
});
test('failure deleting a stepCountCadence', () => {
  const state = reducer(INITIAL_STATE, Actions.stepCountCadenceDeleteFailure({ error: 'Not found' }));

  expect(state.deleting).toBe(false);
  expect(state.errorDeleting).toEqual({ error: 'Not found' });
  expect(state.stepCountCadence).toEqual(INITIAL_STATE.stepCountCadence);
});

test('resetting state for stepCountCadence', () => {
  const state = reducer({ ...INITIAL_STATE, deleting: true }, Actions.stepCountCadenceReset());
  expect(state).toEqual(INITIAL_STATE);
});
