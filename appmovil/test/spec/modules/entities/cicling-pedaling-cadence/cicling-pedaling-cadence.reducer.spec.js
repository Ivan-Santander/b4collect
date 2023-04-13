import Actions, {
  reducer,
  INITIAL_STATE,
} from '../../../../../app/modules/entities/cicling-pedaling-cadence/cicling-pedaling-cadence.reducer';

test('attempt retrieving a single ciclingPedalingCadence', () => {
  const state = reducer(INITIAL_STATE, Actions.ciclingPedalingCadenceRequest({ id: 1 }));

  expect(state.fetchingOne).toBe(true);
  expect(state.ciclingPedalingCadence).toEqual({ id: undefined });
});

test('attempt retrieving a list of ciclingPedalingCadence', () => {
  const state = reducer(INITIAL_STATE, Actions.ciclingPedalingCadenceAllRequest({ id: 1 }));

  expect(state.fetchingAll).toBe(true);
  expect(state.ciclingPedalingCadenceList).toEqual([]);
});

test('attempt updating a ciclingPedalingCadence', () => {
  const state = reducer(INITIAL_STATE, Actions.ciclingPedalingCadenceUpdateRequest({ id: 1 }));

  expect(state.updating).toBe(true);
});
test('attempt to deleting a ciclingPedalingCadence', () => {
  const state = reducer(INITIAL_STATE, Actions.ciclingPedalingCadenceDeleteRequest({ id: 1 }));

  expect(state.deleting).toBe(true);
});

test('success retrieving a ciclingPedalingCadence', () => {
  const state = reducer(INITIAL_STATE, Actions.ciclingPedalingCadenceSuccess({ id: 1 }));

  expect(state.fetchingOne).toBe(false);
  expect(state.errorOne).toBe(null);
  expect(state.ciclingPedalingCadence).toEqual({ id: 1 });
});

test('success retrieving a list of ciclingPedalingCadence', () => {
  const state = reducer(
    INITIAL_STATE,
    Actions.ciclingPedalingCadenceAllSuccess([{ id: 1 }, { id: 2 }], {
      link: '</?page=1>; rel="last",</?page=0>; rel="first"',
      'x-total-count': 5,
    }),
  );

  expect(state.fetchingAll).toBe(false);
  expect(state.errorAll).toBe(null);
  expect(state.ciclingPedalingCadenceList).toEqual([{ id: 1 }, { id: 2 }]);
  expect(state.links).toEqual({ first: 0, last: 1 });
  expect(state.totalItems).toEqual(5);
});

test('success updating a ciclingPedalingCadence', () => {
  const state = reducer(INITIAL_STATE, Actions.ciclingPedalingCadenceUpdateSuccess({ id: 1 }));

  expect(state.updating).toBe(false);
  expect(state.errorUpdating).toBe(null);
  expect(state.ciclingPedalingCadence).toEqual({ id: 1 });
});
test('success deleting a ciclingPedalingCadence', () => {
  const state = reducer(INITIAL_STATE, Actions.ciclingPedalingCadenceDeleteSuccess());

  expect(state.deleting).toBe(false);
  expect(state.errorDeleting).toBe(null);
  expect(state.ciclingPedalingCadence).toEqual({ id: undefined });
});

test('failure retrieving a ciclingPedalingCadence', () => {
  const state = reducer(INITIAL_STATE, Actions.ciclingPedalingCadenceFailure({ error: 'Not found' }));

  expect(state.fetchingOne).toBe(false);
  expect(state.errorOne).toEqual({ error: 'Not found' });
  expect(state.ciclingPedalingCadence).toEqual({ id: undefined });
});

test('failure retrieving a list of ciclingPedalingCadence', () => {
  const state = reducer(INITIAL_STATE, Actions.ciclingPedalingCadenceAllFailure({ error: 'Not found' }));

  expect(state.fetchingAll).toBe(false);
  expect(state.errorAll).toEqual({ error: 'Not found' });
  expect(state.ciclingPedalingCadenceList).toEqual([]);
});

test('failure updating a ciclingPedalingCadence', () => {
  const state = reducer(INITIAL_STATE, Actions.ciclingPedalingCadenceUpdateFailure({ error: 'Not found' }));

  expect(state.updating).toBe(false);
  expect(state.errorUpdating).toEqual({ error: 'Not found' });
  expect(state.ciclingPedalingCadence).toEqual(INITIAL_STATE.ciclingPedalingCadence);
});
test('failure deleting a ciclingPedalingCadence', () => {
  const state = reducer(INITIAL_STATE, Actions.ciclingPedalingCadenceDeleteFailure({ error: 'Not found' }));

  expect(state.deleting).toBe(false);
  expect(state.errorDeleting).toEqual({ error: 'Not found' });
  expect(state.ciclingPedalingCadence).toEqual(INITIAL_STATE.ciclingPedalingCadence);
});

test('resetting state for ciclingPedalingCadence', () => {
  const state = reducer({ ...INITIAL_STATE, deleting: true }, Actions.ciclingPedalingCadenceReset());
  expect(state).toEqual(INITIAL_STATE);
});
