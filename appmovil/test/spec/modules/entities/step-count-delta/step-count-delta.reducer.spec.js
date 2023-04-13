import Actions, { reducer, INITIAL_STATE } from '../../../../../app/modules/entities/step-count-delta/step-count-delta.reducer';

test('attempt retrieving a single stepCountDelta', () => {
  const state = reducer(INITIAL_STATE, Actions.stepCountDeltaRequest({ id: 1 }));

  expect(state.fetchingOne).toBe(true);
  expect(state.stepCountDelta).toEqual({ id: undefined });
});

test('attempt retrieving a list of stepCountDelta', () => {
  const state = reducer(INITIAL_STATE, Actions.stepCountDeltaAllRequest({ id: 1 }));

  expect(state.fetchingAll).toBe(true);
  expect(state.stepCountDeltaList).toEqual([]);
});

test('attempt updating a stepCountDelta', () => {
  const state = reducer(INITIAL_STATE, Actions.stepCountDeltaUpdateRequest({ id: 1 }));

  expect(state.updating).toBe(true);
});
test('attempt to deleting a stepCountDelta', () => {
  const state = reducer(INITIAL_STATE, Actions.stepCountDeltaDeleteRequest({ id: 1 }));

  expect(state.deleting).toBe(true);
});

test('success retrieving a stepCountDelta', () => {
  const state = reducer(INITIAL_STATE, Actions.stepCountDeltaSuccess({ id: 1 }));

  expect(state.fetchingOne).toBe(false);
  expect(state.errorOne).toBe(null);
  expect(state.stepCountDelta).toEqual({ id: 1 });
});

test('success retrieving a list of stepCountDelta', () => {
  const state = reducer(
    INITIAL_STATE,
    Actions.stepCountDeltaAllSuccess([{ id: 1 }, { id: 2 }], {
      link: '</?page=1>; rel="last",</?page=0>; rel="first"',
      'x-total-count': 5,
    }),
  );

  expect(state.fetchingAll).toBe(false);
  expect(state.errorAll).toBe(null);
  expect(state.stepCountDeltaList).toEqual([{ id: 1 }, { id: 2 }]);
  expect(state.links).toEqual({ first: 0, last: 1 });
  expect(state.totalItems).toEqual(5);
});

test('success updating a stepCountDelta', () => {
  const state = reducer(INITIAL_STATE, Actions.stepCountDeltaUpdateSuccess({ id: 1 }));

  expect(state.updating).toBe(false);
  expect(state.errorUpdating).toBe(null);
  expect(state.stepCountDelta).toEqual({ id: 1 });
});
test('success deleting a stepCountDelta', () => {
  const state = reducer(INITIAL_STATE, Actions.stepCountDeltaDeleteSuccess());

  expect(state.deleting).toBe(false);
  expect(state.errorDeleting).toBe(null);
  expect(state.stepCountDelta).toEqual({ id: undefined });
});

test('failure retrieving a stepCountDelta', () => {
  const state = reducer(INITIAL_STATE, Actions.stepCountDeltaFailure({ error: 'Not found' }));

  expect(state.fetchingOne).toBe(false);
  expect(state.errorOne).toEqual({ error: 'Not found' });
  expect(state.stepCountDelta).toEqual({ id: undefined });
});

test('failure retrieving a list of stepCountDelta', () => {
  const state = reducer(INITIAL_STATE, Actions.stepCountDeltaAllFailure({ error: 'Not found' }));

  expect(state.fetchingAll).toBe(false);
  expect(state.errorAll).toEqual({ error: 'Not found' });
  expect(state.stepCountDeltaList).toEqual([]);
});

test('failure updating a stepCountDelta', () => {
  const state = reducer(INITIAL_STATE, Actions.stepCountDeltaUpdateFailure({ error: 'Not found' }));

  expect(state.updating).toBe(false);
  expect(state.errorUpdating).toEqual({ error: 'Not found' });
  expect(state.stepCountDelta).toEqual(INITIAL_STATE.stepCountDelta);
});
test('failure deleting a stepCountDelta', () => {
  const state = reducer(INITIAL_STATE, Actions.stepCountDeltaDeleteFailure({ error: 'Not found' }));

  expect(state.deleting).toBe(false);
  expect(state.errorDeleting).toEqual({ error: 'Not found' });
  expect(state.stepCountDelta).toEqual(INITIAL_STATE.stepCountDelta);
});

test('resetting state for stepCountDelta', () => {
  const state = reducer({ ...INITIAL_STATE, deleting: true }, Actions.stepCountDeltaReset());
  expect(state).toEqual(INITIAL_STATE);
});
