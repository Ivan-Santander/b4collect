import Actions, { reducer, INITIAL_STATE } from '../../../../../app/modules/entities/height/height.reducer';

test('attempt retrieving a single height', () => {
  const state = reducer(INITIAL_STATE, Actions.heightRequest({ id: 1 }));

  expect(state.fetchingOne).toBe(true);
  expect(state.height).toEqual({ id: undefined });
});

test('attempt retrieving a list of height', () => {
  const state = reducer(INITIAL_STATE, Actions.heightAllRequest({ id: 1 }));

  expect(state.fetchingAll).toBe(true);
  expect(state.heightList).toEqual([]);
});

test('attempt updating a height', () => {
  const state = reducer(INITIAL_STATE, Actions.heightUpdateRequest({ id: 1 }));

  expect(state.updating).toBe(true);
});
test('attempt to deleting a height', () => {
  const state = reducer(INITIAL_STATE, Actions.heightDeleteRequest({ id: 1 }));

  expect(state.deleting).toBe(true);
});

test('success retrieving a height', () => {
  const state = reducer(INITIAL_STATE, Actions.heightSuccess({ id: 1 }));

  expect(state.fetchingOne).toBe(false);
  expect(state.errorOne).toBe(null);
  expect(state.height).toEqual({ id: 1 });
});

test('success retrieving a list of height', () => {
  const state = reducer(
    INITIAL_STATE,
    Actions.heightAllSuccess([{ id: 1 }, { id: 2 }], { link: '</?page=1>; rel="last",</?page=0>; rel="first"', 'x-total-count': 5 }),
  );

  expect(state.fetchingAll).toBe(false);
  expect(state.errorAll).toBe(null);
  expect(state.heightList).toEqual([{ id: 1 }, { id: 2 }]);
  expect(state.links).toEqual({ first: 0, last: 1 });
  expect(state.totalItems).toEqual(5);
});

test('success updating a height', () => {
  const state = reducer(INITIAL_STATE, Actions.heightUpdateSuccess({ id: 1 }));

  expect(state.updating).toBe(false);
  expect(state.errorUpdating).toBe(null);
  expect(state.height).toEqual({ id: 1 });
});
test('success deleting a height', () => {
  const state = reducer(INITIAL_STATE, Actions.heightDeleteSuccess());

  expect(state.deleting).toBe(false);
  expect(state.errorDeleting).toBe(null);
  expect(state.height).toEqual({ id: undefined });
});

test('failure retrieving a height', () => {
  const state = reducer(INITIAL_STATE, Actions.heightFailure({ error: 'Not found' }));

  expect(state.fetchingOne).toBe(false);
  expect(state.errorOne).toEqual({ error: 'Not found' });
  expect(state.height).toEqual({ id: undefined });
});

test('failure retrieving a list of height', () => {
  const state = reducer(INITIAL_STATE, Actions.heightAllFailure({ error: 'Not found' }));

  expect(state.fetchingAll).toBe(false);
  expect(state.errorAll).toEqual({ error: 'Not found' });
  expect(state.heightList).toEqual([]);
});

test('failure updating a height', () => {
  const state = reducer(INITIAL_STATE, Actions.heightUpdateFailure({ error: 'Not found' }));

  expect(state.updating).toBe(false);
  expect(state.errorUpdating).toEqual({ error: 'Not found' });
  expect(state.height).toEqual(INITIAL_STATE.height);
});
test('failure deleting a height', () => {
  const state = reducer(INITIAL_STATE, Actions.heightDeleteFailure({ error: 'Not found' }));

  expect(state.deleting).toBe(false);
  expect(state.errorDeleting).toEqual({ error: 'Not found' });
  expect(state.height).toEqual(INITIAL_STATE.height);
});

test('resetting state for height', () => {
  const state = reducer({ ...INITIAL_STATE, deleting: true }, Actions.heightReset());
  expect(state).toEqual(INITIAL_STATE);
});
