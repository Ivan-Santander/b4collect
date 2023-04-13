import Actions, { reducer, INITIAL_STATE } from '../../../../../app/modules/entities/powe-sample/powe-sample.reducer';

test('attempt retrieving a single poweSample', () => {
  const state = reducer(INITIAL_STATE, Actions.poweSampleRequest({ id: 1 }));

  expect(state.fetchingOne).toBe(true);
  expect(state.poweSample).toEqual({ id: undefined });
});

test('attempt retrieving a list of poweSample', () => {
  const state = reducer(INITIAL_STATE, Actions.poweSampleAllRequest({ id: 1 }));

  expect(state.fetchingAll).toBe(true);
  expect(state.poweSampleList).toEqual([]);
});

test('attempt updating a poweSample', () => {
  const state = reducer(INITIAL_STATE, Actions.poweSampleUpdateRequest({ id: 1 }));

  expect(state.updating).toBe(true);
});
test('attempt to deleting a poweSample', () => {
  const state = reducer(INITIAL_STATE, Actions.poweSampleDeleteRequest({ id: 1 }));

  expect(state.deleting).toBe(true);
});

test('success retrieving a poweSample', () => {
  const state = reducer(INITIAL_STATE, Actions.poweSampleSuccess({ id: 1 }));

  expect(state.fetchingOne).toBe(false);
  expect(state.errorOne).toBe(null);
  expect(state.poweSample).toEqual({ id: 1 });
});

test('success retrieving a list of poweSample', () => {
  const state = reducer(
    INITIAL_STATE,
    Actions.poweSampleAllSuccess([{ id: 1 }, { id: 2 }], { link: '</?page=1>; rel="last",</?page=0>; rel="first"', 'x-total-count': 5 }),
  );

  expect(state.fetchingAll).toBe(false);
  expect(state.errorAll).toBe(null);
  expect(state.poweSampleList).toEqual([{ id: 1 }, { id: 2 }]);
  expect(state.links).toEqual({ first: 0, last: 1 });
  expect(state.totalItems).toEqual(5);
});

test('success updating a poweSample', () => {
  const state = reducer(INITIAL_STATE, Actions.poweSampleUpdateSuccess({ id: 1 }));

  expect(state.updating).toBe(false);
  expect(state.errorUpdating).toBe(null);
  expect(state.poweSample).toEqual({ id: 1 });
});
test('success deleting a poweSample', () => {
  const state = reducer(INITIAL_STATE, Actions.poweSampleDeleteSuccess());

  expect(state.deleting).toBe(false);
  expect(state.errorDeleting).toBe(null);
  expect(state.poweSample).toEqual({ id: undefined });
});

test('failure retrieving a poweSample', () => {
  const state = reducer(INITIAL_STATE, Actions.poweSampleFailure({ error: 'Not found' }));

  expect(state.fetchingOne).toBe(false);
  expect(state.errorOne).toEqual({ error: 'Not found' });
  expect(state.poweSample).toEqual({ id: undefined });
});

test('failure retrieving a list of poweSample', () => {
  const state = reducer(INITIAL_STATE, Actions.poweSampleAllFailure({ error: 'Not found' }));

  expect(state.fetchingAll).toBe(false);
  expect(state.errorAll).toEqual({ error: 'Not found' });
  expect(state.poweSampleList).toEqual([]);
});

test('failure updating a poweSample', () => {
  const state = reducer(INITIAL_STATE, Actions.poweSampleUpdateFailure({ error: 'Not found' }));

  expect(state.updating).toBe(false);
  expect(state.errorUpdating).toEqual({ error: 'Not found' });
  expect(state.poweSample).toEqual(INITIAL_STATE.poweSample);
});
test('failure deleting a poweSample', () => {
  const state = reducer(INITIAL_STATE, Actions.poweSampleDeleteFailure({ error: 'Not found' }));

  expect(state.deleting).toBe(false);
  expect(state.errorDeleting).toEqual({ error: 'Not found' });
  expect(state.poweSample).toEqual(INITIAL_STATE.poweSample);
});

test('resetting state for poweSample', () => {
  const state = reducer({ ...INITIAL_STATE, deleting: true }, Actions.poweSampleReset());
  expect(state).toEqual(INITIAL_STATE);
});
