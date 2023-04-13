import Actions, { reducer, INITIAL_STATE } from '../../../../../app/modules/entities/power-summary/power-summary.reducer';

test('attempt retrieving a single powerSummary', () => {
  const state = reducer(INITIAL_STATE, Actions.powerSummaryRequest({ id: 1 }));

  expect(state.fetchingOne).toBe(true);
  expect(state.powerSummary).toEqual({ id: undefined });
});

test('attempt retrieving a list of powerSummary', () => {
  const state = reducer(INITIAL_STATE, Actions.powerSummaryAllRequest({ id: 1 }));

  expect(state.fetchingAll).toBe(true);
  expect(state.powerSummaryList).toEqual([]);
});

test('attempt updating a powerSummary', () => {
  const state = reducer(INITIAL_STATE, Actions.powerSummaryUpdateRequest({ id: 1 }));

  expect(state.updating).toBe(true);
});
test('attempt to deleting a powerSummary', () => {
  const state = reducer(INITIAL_STATE, Actions.powerSummaryDeleteRequest({ id: 1 }));

  expect(state.deleting).toBe(true);
});

test('success retrieving a powerSummary', () => {
  const state = reducer(INITIAL_STATE, Actions.powerSummarySuccess({ id: 1 }));

  expect(state.fetchingOne).toBe(false);
  expect(state.errorOne).toBe(null);
  expect(state.powerSummary).toEqual({ id: 1 });
});

test('success retrieving a list of powerSummary', () => {
  const state = reducer(
    INITIAL_STATE,
    Actions.powerSummaryAllSuccess([{ id: 1 }, { id: 2 }], { link: '</?page=1>; rel="last",</?page=0>; rel="first"', 'x-total-count': 5 }),
  );

  expect(state.fetchingAll).toBe(false);
  expect(state.errorAll).toBe(null);
  expect(state.powerSummaryList).toEqual([{ id: 1 }, { id: 2 }]);
  expect(state.links).toEqual({ first: 0, last: 1 });
  expect(state.totalItems).toEqual(5);
});

test('success updating a powerSummary', () => {
  const state = reducer(INITIAL_STATE, Actions.powerSummaryUpdateSuccess({ id: 1 }));

  expect(state.updating).toBe(false);
  expect(state.errorUpdating).toBe(null);
  expect(state.powerSummary).toEqual({ id: 1 });
});
test('success deleting a powerSummary', () => {
  const state = reducer(INITIAL_STATE, Actions.powerSummaryDeleteSuccess());

  expect(state.deleting).toBe(false);
  expect(state.errorDeleting).toBe(null);
  expect(state.powerSummary).toEqual({ id: undefined });
});

test('failure retrieving a powerSummary', () => {
  const state = reducer(INITIAL_STATE, Actions.powerSummaryFailure({ error: 'Not found' }));

  expect(state.fetchingOne).toBe(false);
  expect(state.errorOne).toEqual({ error: 'Not found' });
  expect(state.powerSummary).toEqual({ id: undefined });
});

test('failure retrieving a list of powerSummary', () => {
  const state = reducer(INITIAL_STATE, Actions.powerSummaryAllFailure({ error: 'Not found' }));

  expect(state.fetchingAll).toBe(false);
  expect(state.errorAll).toEqual({ error: 'Not found' });
  expect(state.powerSummaryList).toEqual([]);
});

test('failure updating a powerSummary', () => {
  const state = reducer(INITIAL_STATE, Actions.powerSummaryUpdateFailure({ error: 'Not found' }));

  expect(state.updating).toBe(false);
  expect(state.errorUpdating).toEqual({ error: 'Not found' });
  expect(state.powerSummary).toEqual(INITIAL_STATE.powerSummary);
});
test('failure deleting a powerSummary', () => {
  const state = reducer(INITIAL_STATE, Actions.powerSummaryDeleteFailure({ error: 'Not found' }));

  expect(state.deleting).toBe(false);
  expect(state.errorDeleting).toEqual({ error: 'Not found' });
  expect(state.powerSummary).toEqual(INITIAL_STATE.powerSummary);
});

test('resetting state for powerSummary', () => {
  const state = reducer({ ...INITIAL_STATE, deleting: true }, Actions.powerSummaryReset());
  expect(state).toEqual(INITIAL_STATE);
});
