import Actions, { reducer, INITIAL_STATE } from '../../../../../app/modules/entities/distance-delta/distance-delta.reducer';

test('attempt retrieving a single distanceDelta', () => {
  const state = reducer(INITIAL_STATE, Actions.distanceDeltaRequest({ id: 1 }));

  expect(state.fetchingOne).toBe(true);
  expect(state.distanceDelta).toEqual({ id: undefined });
});

test('attempt retrieving a list of distanceDelta', () => {
  const state = reducer(INITIAL_STATE, Actions.distanceDeltaAllRequest({ id: 1 }));

  expect(state.fetchingAll).toBe(true);
  expect(state.distanceDeltaList).toEqual([]);
});

test('attempt updating a distanceDelta', () => {
  const state = reducer(INITIAL_STATE, Actions.distanceDeltaUpdateRequest({ id: 1 }));

  expect(state.updating).toBe(true);
});
test('attempt to deleting a distanceDelta', () => {
  const state = reducer(INITIAL_STATE, Actions.distanceDeltaDeleteRequest({ id: 1 }));

  expect(state.deleting).toBe(true);
});

test('success retrieving a distanceDelta', () => {
  const state = reducer(INITIAL_STATE, Actions.distanceDeltaSuccess({ id: 1 }));

  expect(state.fetchingOne).toBe(false);
  expect(state.errorOne).toBe(null);
  expect(state.distanceDelta).toEqual({ id: 1 });
});

test('success retrieving a list of distanceDelta', () => {
  const state = reducer(
    INITIAL_STATE,
    Actions.distanceDeltaAllSuccess([{ id: 1 }, { id: 2 }], { link: '</?page=1>; rel="last",</?page=0>; rel="first"', 'x-total-count': 5 }),
  );

  expect(state.fetchingAll).toBe(false);
  expect(state.errorAll).toBe(null);
  expect(state.distanceDeltaList).toEqual([{ id: 1 }, { id: 2 }]);
  expect(state.links).toEqual({ first: 0, last: 1 });
  expect(state.totalItems).toEqual(5);
});

test('success updating a distanceDelta', () => {
  const state = reducer(INITIAL_STATE, Actions.distanceDeltaUpdateSuccess({ id: 1 }));

  expect(state.updating).toBe(false);
  expect(state.errorUpdating).toBe(null);
  expect(state.distanceDelta).toEqual({ id: 1 });
});
test('success deleting a distanceDelta', () => {
  const state = reducer(INITIAL_STATE, Actions.distanceDeltaDeleteSuccess());

  expect(state.deleting).toBe(false);
  expect(state.errorDeleting).toBe(null);
  expect(state.distanceDelta).toEqual({ id: undefined });
});

test('failure retrieving a distanceDelta', () => {
  const state = reducer(INITIAL_STATE, Actions.distanceDeltaFailure({ error: 'Not found' }));

  expect(state.fetchingOne).toBe(false);
  expect(state.errorOne).toEqual({ error: 'Not found' });
  expect(state.distanceDelta).toEqual({ id: undefined });
});

test('failure retrieving a list of distanceDelta', () => {
  const state = reducer(INITIAL_STATE, Actions.distanceDeltaAllFailure({ error: 'Not found' }));

  expect(state.fetchingAll).toBe(false);
  expect(state.errorAll).toEqual({ error: 'Not found' });
  expect(state.distanceDeltaList).toEqual([]);
});

test('failure updating a distanceDelta', () => {
  const state = reducer(INITIAL_STATE, Actions.distanceDeltaUpdateFailure({ error: 'Not found' }));

  expect(state.updating).toBe(false);
  expect(state.errorUpdating).toEqual({ error: 'Not found' });
  expect(state.distanceDelta).toEqual(INITIAL_STATE.distanceDelta);
});
test('failure deleting a distanceDelta', () => {
  const state = reducer(INITIAL_STATE, Actions.distanceDeltaDeleteFailure({ error: 'Not found' }));

  expect(state.deleting).toBe(false);
  expect(state.errorDeleting).toEqual({ error: 'Not found' });
  expect(state.distanceDelta).toEqual(INITIAL_STATE.distanceDelta);
});

test('resetting state for distanceDelta', () => {
  const state = reducer({ ...INITIAL_STATE, deleting: true }, Actions.distanceDeltaReset());
  expect(state).toEqual(INITIAL_STATE);
});
