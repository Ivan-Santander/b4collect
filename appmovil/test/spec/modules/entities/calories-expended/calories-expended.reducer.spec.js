import Actions, { reducer, INITIAL_STATE } from '../../../../../app/modules/entities/calories-expended/calories-expended.reducer';

test('attempt retrieving a single caloriesExpended', () => {
  const state = reducer(INITIAL_STATE, Actions.caloriesExpendedRequest({ id: 1 }));

  expect(state.fetchingOne).toBe(true);
  expect(state.caloriesExpended).toEqual({ id: undefined });
});

test('attempt retrieving a list of caloriesExpended', () => {
  const state = reducer(INITIAL_STATE, Actions.caloriesExpendedAllRequest({ id: 1 }));

  expect(state.fetchingAll).toBe(true);
  expect(state.caloriesExpendedList).toEqual([]);
});

test('attempt updating a caloriesExpended', () => {
  const state = reducer(INITIAL_STATE, Actions.caloriesExpendedUpdateRequest({ id: 1 }));

  expect(state.updating).toBe(true);
});
test('attempt to deleting a caloriesExpended', () => {
  const state = reducer(INITIAL_STATE, Actions.caloriesExpendedDeleteRequest({ id: 1 }));

  expect(state.deleting).toBe(true);
});

test('success retrieving a caloriesExpended', () => {
  const state = reducer(INITIAL_STATE, Actions.caloriesExpendedSuccess({ id: 1 }));

  expect(state.fetchingOne).toBe(false);
  expect(state.errorOne).toBe(null);
  expect(state.caloriesExpended).toEqual({ id: 1 });
});

test('success retrieving a list of caloriesExpended', () => {
  const state = reducer(
    INITIAL_STATE,
    Actions.caloriesExpendedAllSuccess([{ id: 1 }, { id: 2 }], {
      link: '</?page=1>; rel="last",</?page=0>; rel="first"',
      'x-total-count': 5,
    }),
  );

  expect(state.fetchingAll).toBe(false);
  expect(state.errorAll).toBe(null);
  expect(state.caloriesExpendedList).toEqual([{ id: 1 }, { id: 2 }]);
  expect(state.links).toEqual({ first: 0, last: 1 });
  expect(state.totalItems).toEqual(5);
});

test('success updating a caloriesExpended', () => {
  const state = reducer(INITIAL_STATE, Actions.caloriesExpendedUpdateSuccess({ id: 1 }));

  expect(state.updating).toBe(false);
  expect(state.errorUpdating).toBe(null);
  expect(state.caloriesExpended).toEqual({ id: 1 });
});
test('success deleting a caloriesExpended', () => {
  const state = reducer(INITIAL_STATE, Actions.caloriesExpendedDeleteSuccess());

  expect(state.deleting).toBe(false);
  expect(state.errorDeleting).toBe(null);
  expect(state.caloriesExpended).toEqual({ id: undefined });
});

test('failure retrieving a caloriesExpended', () => {
  const state = reducer(INITIAL_STATE, Actions.caloriesExpendedFailure({ error: 'Not found' }));

  expect(state.fetchingOne).toBe(false);
  expect(state.errorOne).toEqual({ error: 'Not found' });
  expect(state.caloriesExpended).toEqual({ id: undefined });
});

test('failure retrieving a list of caloriesExpended', () => {
  const state = reducer(INITIAL_STATE, Actions.caloriesExpendedAllFailure({ error: 'Not found' }));

  expect(state.fetchingAll).toBe(false);
  expect(state.errorAll).toEqual({ error: 'Not found' });
  expect(state.caloriesExpendedList).toEqual([]);
});

test('failure updating a caloriesExpended', () => {
  const state = reducer(INITIAL_STATE, Actions.caloriesExpendedUpdateFailure({ error: 'Not found' }));

  expect(state.updating).toBe(false);
  expect(state.errorUpdating).toEqual({ error: 'Not found' });
  expect(state.caloriesExpended).toEqual(INITIAL_STATE.caloriesExpended);
});
test('failure deleting a caloriesExpended', () => {
  const state = reducer(INITIAL_STATE, Actions.caloriesExpendedDeleteFailure({ error: 'Not found' }));

  expect(state.deleting).toBe(false);
  expect(state.errorDeleting).toEqual({ error: 'Not found' });
  expect(state.caloriesExpended).toEqual(INITIAL_STATE.caloriesExpended);
});

test('resetting state for caloriesExpended', () => {
  const state = reducer({ ...INITIAL_STATE, deleting: true }, Actions.caloriesExpendedReset());
  expect(state).toEqual(INITIAL_STATE);
});
