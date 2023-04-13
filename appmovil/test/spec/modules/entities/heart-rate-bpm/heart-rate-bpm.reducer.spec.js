import Actions, { reducer, INITIAL_STATE } from '../../../../../app/modules/entities/heart-rate-bpm/heart-rate-bpm.reducer';

test('attempt retrieving a single heartRateBpm', () => {
  const state = reducer(INITIAL_STATE, Actions.heartRateBpmRequest({ id: 1 }));

  expect(state.fetchingOne).toBe(true);
  expect(state.heartRateBpm).toEqual({ id: undefined });
});

test('attempt retrieving a list of heartRateBpm', () => {
  const state = reducer(INITIAL_STATE, Actions.heartRateBpmAllRequest({ id: 1 }));

  expect(state.fetchingAll).toBe(true);
  expect(state.heartRateBpmList).toEqual([]);
});

test('attempt updating a heartRateBpm', () => {
  const state = reducer(INITIAL_STATE, Actions.heartRateBpmUpdateRequest({ id: 1 }));

  expect(state.updating).toBe(true);
});
test('attempt to deleting a heartRateBpm', () => {
  const state = reducer(INITIAL_STATE, Actions.heartRateBpmDeleteRequest({ id: 1 }));

  expect(state.deleting).toBe(true);
});

test('success retrieving a heartRateBpm', () => {
  const state = reducer(INITIAL_STATE, Actions.heartRateBpmSuccess({ id: 1 }));

  expect(state.fetchingOne).toBe(false);
  expect(state.errorOne).toBe(null);
  expect(state.heartRateBpm).toEqual({ id: 1 });
});

test('success retrieving a list of heartRateBpm', () => {
  const state = reducer(
    INITIAL_STATE,
    Actions.heartRateBpmAllSuccess([{ id: 1 }, { id: 2 }], { link: '</?page=1>; rel="last",</?page=0>; rel="first"', 'x-total-count': 5 }),
  );

  expect(state.fetchingAll).toBe(false);
  expect(state.errorAll).toBe(null);
  expect(state.heartRateBpmList).toEqual([{ id: 1 }, { id: 2 }]);
  expect(state.links).toEqual({ first: 0, last: 1 });
  expect(state.totalItems).toEqual(5);
});

test('success updating a heartRateBpm', () => {
  const state = reducer(INITIAL_STATE, Actions.heartRateBpmUpdateSuccess({ id: 1 }));

  expect(state.updating).toBe(false);
  expect(state.errorUpdating).toBe(null);
  expect(state.heartRateBpm).toEqual({ id: 1 });
});
test('success deleting a heartRateBpm', () => {
  const state = reducer(INITIAL_STATE, Actions.heartRateBpmDeleteSuccess());

  expect(state.deleting).toBe(false);
  expect(state.errorDeleting).toBe(null);
  expect(state.heartRateBpm).toEqual({ id: undefined });
});

test('failure retrieving a heartRateBpm', () => {
  const state = reducer(INITIAL_STATE, Actions.heartRateBpmFailure({ error: 'Not found' }));

  expect(state.fetchingOne).toBe(false);
  expect(state.errorOne).toEqual({ error: 'Not found' });
  expect(state.heartRateBpm).toEqual({ id: undefined });
});

test('failure retrieving a list of heartRateBpm', () => {
  const state = reducer(INITIAL_STATE, Actions.heartRateBpmAllFailure({ error: 'Not found' }));

  expect(state.fetchingAll).toBe(false);
  expect(state.errorAll).toEqual({ error: 'Not found' });
  expect(state.heartRateBpmList).toEqual([]);
});

test('failure updating a heartRateBpm', () => {
  const state = reducer(INITIAL_STATE, Actions.heartRateBpmUpdateFailure({ error: 'Not found' }));

  expect(state.updating).toBe(false);
  expect(state.errorUpdating).toEqual({ error: 'Not found' });
  expect(state.heartRateBpm).toEqual(INITIAL_STATE.heartRateBpm);
});
test('failure deleting a heartRateBpm', () => {
  const state = reducer(INITIAL_STATE, Actions.heartRateBpmDeleteFailure({ error: 'Not found' }));

  expect(state.deleting).toBe(false);
  expect(state.errorDeleting).toEqual({ error: 'Not found' });
  expect(state.heartRateBpm).toEqual(INITIAL_STATE.heartRateBpm);
});

test('resetting state for heartRateBpm', () => {
  const state = reducer({ ...INITIAL_STATE, deleting: true }, Actions.heartRateBpmReset());
  expect(state).toEqual(INITIAL_STATE);
});
