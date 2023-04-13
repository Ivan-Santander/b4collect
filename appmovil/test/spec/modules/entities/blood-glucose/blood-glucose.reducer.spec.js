import Actions, { reducer, INITIAL_STATE } from '../../../../../app/modules/entities/blood-glucose/blood-glucose.reducer';

test('attempt retrieving a single bloodGlucose', () => {
  const state = reducer(INITIAL_STATE, Actions.bloodGlucoseRequest({ id: 1 }));

  expect(state.fetchingOne).toBe(true);
  expect(state.bloodGlucose).toEqual({ id: undefined });
});

test('attempt retrieving a list of bloodGlucose', () => {
  const state = reducer(INITIAL_STATE, Actions.bloodGlucoseAllRequest({ id: 1 }));

  expect(state.fetchingAll).toBe(true);
  expect(state.bloodGlucoseList).toEqual([]);
});

test('attempt updating a bloodGlucose', () => {
  const state = reducer(INITIAL_STATE, Actions.bloodGlucoseUpdateRequest({ id: 1 }));

  expect(state.updating).toBe(true);
});
test('attempt to deleting a bloodGlucose', () => {
  const state = reducer(INITIAL_STATE, Actions.bloodGlucoseDeleteRequest({ id: 1 }));

  expect(state.deleting).toBe(true);
});

test('success retrieving a bloodGlucose', () => {
  const state = reducer(INITIAL_STATE, Actions.bloodGlucoseSuccess({ id: 1 }));

  expect(state.fetchingOne).toBe(false);
  expect(state.errorOne).toBe(null);
  expect(state.bloodGlucose).toEqual({ id: 1 });
});

test('success retrieving a list of bloodGlucose', () => {
  const state = reducer(
    INITIAL_STATE,
    Actions.bloodGlucoseAllSuccess([{ id: 1 }, { id: 2 }], { link: '</?page=1>; rel="last",</?page=0>; rel="first"', 'x-total-count': 5 }),
  );

  expect(state.fetchingAll).toBe(false);
  expect(state.errorAll).toBe(null);
  expect(state.bloodGlucoseList).toEqual([{ id: 1 }, { id: 2 }]);
  expect(state.links).toEqual({ first: 0, last: 1 });
  expect(state.totalItems).toEqual(5);
});

test('success updating a bloodGlucose', () => {
  const state = reducer(INITIAL_STATE, Actions.bloodGlucoseUpdateSuccess({ id: 1 }));

  expect(state.updating).toBe(false);
  expect(state.errorUpdating).toBe(null);
  expect(state.bloodGlucose).toEqual({ id: 1 });
});
test('success deleting a bloodGlucose', () => {
  const state = reducer(INITIAL_STATE, Actions.bloodGlucoseDeleteSuccess());

  expect(state.deleting).toBe(false);
  expect(state.errorDeleting).toBe(null);
  expect(state.bloodGlucose).toEqual({ id: undefined });
});

test('failure retrieving a bloodGlucose', () => {
  const state = reducer(INITIAL_STATE, Actions.bloodGlucoseFailure({ error: 'Not found' }));

  expect(state.fetchingOne).toBe(false);
  expect(state.errorOne).toEqual({ error: 'Not found' });
  expect(state.bloodGlucose).toEqual({ id: undefined });
});

test('failure retrieving a list of bloodGlucose', () => {
  const state = reducer(INITIAL_STATE, Actions.bloodGlucoseAllFailure({ error: 'Not found' }));

  expect(state.fetchingAll).toBe(false);
  expect(state.errorAll).toEqual({ error: 'Not found' });
  expect(state.bloodGlucoseList).toEqual([]);
});

test('failure updating a bloodGlucose', () => {
  const state = reducer(INITIAL_STATE, Actions.bloodGlucoseUpdateFailure({ error: 'Not found' }));

  expect(state.updating).toBe(false);
  expect(state.errorUpdating).toEqual({ error: 'Not found' });
  expect(state.bloodGlucose).toEqual(INITIAL_STATE.bloodGlucose);
});
test('failure deleting a bloodGlucose', () => {
  const state = reducer(INITIAL_STATE, Actions.bloodGlucoseDeleteFailure({ error: 'Not found' }));

  expect(state.deleting).toBe(false);
  expect(state.errorDeleting).toEqual({ error: 'Not found' });
  expect(state.bloodGlucose).toEqual(INITIAL_STATE.bloodGlucose);
});

test('resetting state for bloodGlucose', () => {
  const state = reducer({ ...INITIAL_STATE, deleting: true }, Actions.bloodGlucoseReset());
  expect(state).toEqual(INITIAL_STATE);
});
