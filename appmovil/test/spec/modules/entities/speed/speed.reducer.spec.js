import Actions, { reducer, INITIAL_STATE } from '../../../../../app/modules/entities/speed/speed.reducer';

test('attempt retrieving a single speed', () => {
  const state = reducer(INITIAL_STATE, Actions.speedRequest({ id: 1 }));

  expect(state.fetchingOne).toBe(true);
  expect(state.speed).toEqual({ id: undefined });
});

test('attempt retrieving a list of speed', () => {
  const state = reducer(INITIAL_STATE, Actions.speedAllRequest({ id: 1 }));

  expect(state.fetchingAll).toBe(true);
  expect(state.speedList).toEqual([]);
});

test('attempt updating a speed', () => {
  const state = reducer(INITIAL_STATE, Actions.speedUpdateRequest({ id: 1 }));

  expect(state.updating).toBe(true);
});
test('attempt to deleting a speed', () => {
  const state = reducer(INITIAL_STATE, Actions.speedDeleteRequest({ id: 1 }));

  expect(state.deleting).toBe(true);
});

test('success retrieving a speed', () => {
  const state = reducer(INITIAL_STATE, Actions.speedSuccess({ id: 1 }));

  expect(state.fetchingOne).toBe(false);
  expect(state.errorOne).toBe(null);
  expect(state.speed).toEqual({ id: 1 });
});

test('success retrieving a list of speed', () => {
  const state = reducer(
    INITIAL_STATE,
    Actions.speedAllSuccess([{ id: 1 }, { id: 2 }], { link: '</?page=1>; rel="last",</?page=0>; rel="first"', 'x-total-count': 5 }),
  );

  expect(state.fetchingAll).toBe(false);
  expect(state.errorAll).toBe(null);
  expect(state.speedList).toEqual([{ id: 1 }, { id: 2 }]);
  expect(state.links).toEqual({ first: 0, last: 1 });
  expect(state.totalItems).toEqual(5);
});

test('success updating a speed', () => {
  const state = reducer(INITIAL_STATE, Actions.speedUpdateSuccess({ id: 1 }));

  expect(state.updating).toBe(false);
  expect(state.errorUpdating).toBe(null);
  expect(state.speed).toEqual({ id: 1 });
});
test('success deleting a speed', () => {
  const state = reducer(INITIAL_STATE, Actions.speedDeleteSuccess());

  expect(state.deleting).toBe(false);
  expect(state.errorDeleting).toBe(null);
  expect(state.speed).toEqual({ id: undefined });
});

test('failure retrieving a speed', () => {
  const state = reducer(INITIAL_STATE, Actions.speedFailure({ error: 'Not found' }));

  expect(state.fetchingOne).toBe(false);
  expect(state.errorOne).toEqual({ error: 'Not found' });
  expect(state.speed).toEqual({ id: undefined });
});

test('failure retrieving a list of speed', () => {
  const state = reducer(INITIAL_STATE, Actions.speedAllFailure({ error: 'Not found' }));

  expect(state.fetchingAll).toBe(false);
  expect(state.errorAll).toEqual({ error: 'Not found' });
  expect(state.speedList).toEqual([]);
});

test('failure updating a speed', () => {
  const state = reducer(INITIAL_STATE, Actions.speedUpdateFailure({ error: 'Not found' }));

  expect(state.updating).toBe(false);
  expect(state.errorUpdating).toEqual({ error: 'Not found' });
  expect(state.speed).toEqual(INITIAL_STATE.speed);
});
test('failure deleting a speed', () => {
  const state = reducer(INITIAL_STATE, Actions.speedDeleteFailure({ error: 'Not found' }));

  expect(state.deleting).toBe(false);
  expect(state.errorDeleting).toEqual({ error: 'Not found' });
  expect(state.speed).toEqual(INITIAL_STATE.speed);
});

test('resetting state for speed', () => {
  const state = reducer({ ...INITIAL_STATE, deleting: true }, Actions.speedReset());
  expect(state).toEqual(INITIAL_STATE);
});
