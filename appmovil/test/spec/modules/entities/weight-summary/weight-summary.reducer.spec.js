import Actions, { reducer, INITIAL_STATE } from '../../../../../app/modules/entities/weight-summary/weight-summary.reducer';

test('attempt retrieving a single weightSummary', () => {
  const state = reducer(INITIAL_STATE, Actions.weightSummaryRequest({ id: 1 }));

  expect(state.fetchingOne).toBe(true);
  expect(state.weightSummary).toEqual({ id: undefined });
});

test('attempt retrieving a list of weightSummary', () => {
  const state = reducer(INITIAL_STATE, Actions.weightSummaryAllRequest({ id: 1 }));

  expect(state.fetchingAll).toBe(true);
  expect(state.weightSummaryList).toEqual([]);
});

test('attempt updating a weightSummary', () => {
  const state = reducer(INITIAL_STATE, Actions.weightSummaryUpdateRequest({ id: 1 }));

  expect(state.updating).toBe(true);
});
test('attempt to deleting a weightSummary', () => {
  const state = reducer(INITIAL_STATE, Actions.weightSummaryDeleteRequest({ id: 1 }));

  expect(state.deleting).toBe(true);
});

test('success retrieving a weightSummary', () => {
  const state = reducer(INITIAL_STATE, Actions.weightSummarySuccess({ id: 1 }));

  expect(state.fetchingOne).toBe(false);
  expect(state.errorOne).toBe(null);
  expect(state.weightSummary).toEqual({ id: 1 });
});

test('success retrieving a list of weightSummary', () => {
  const state = reducer(
    INITIAL_STATE,
    Actions.weightSummaryAllSuccess([{ id: 1 }, { id: 2 }], { link: '</?page=1>; rel="last",</?page=0>; rel="first"', 'x-total-count': 5 }),
  );

  expect(state.fetchingAll).toBe(false);
  expect(state.errorAll).toBe(null);
  expect(state.weightSummaryList).toEqual([{ id: 1 }, { id: 2 }]);
  expect(state.links).toEqual({ first: 0, last: 1 });
  expect(state.totalItems).toEqual(5);
});

test('success updating a weightSummary', () => {
  const state = reducer(INITIAL_STATE, Actions.weightSummaryUpdateSuccess({ id: 1 }));

  expect(state.updating).toBe(false);
  expect(state.errorUpdating).toBe(null);
  expect(state.weightSummary).toEqual({ id: 1 });
});
test('success deleting a weightSummary', () => {
  const state = reducer(INITIAL_STATE, Actions.weightSummaryDeleteSuccess());

  expect(state.deleting).toBe(false);
  expect(state.errorDeleting).toBe(null);
  expect(state.weightSummary).toEqual({ id: undefined });
});

test('failure retrieving a weightSummary', () => {
  const state = reducer(INITIAL_STATE, Actions.weightSummaryFailure({ error: 'Not found' }));

  expect(state.fetchingOne).toBe(false);
  expect(state.errorOne).toEqual({ error: 'Not found' });
  expect(state.weightSummary).toEqual({ id: undefined });
});

test('failure retrieving a list of weightSummary', () => {
  const state = reducer(INITIAL_STATE, Actions.weightSummaryAllFailure({ error: 'Not found' }));

  expect(state.fetchingAll).toBe(false);
  expect(state.errorAll).toEqual({ error: 'Not found' });
  expect(state.weightSummaryList).toEqual([]);
});

test('failure updating a weightSummary', () => {
  const state = reducer(INITIAL_STATE, Actions.weightSummaryUpdateFailure({ error: 'Not found' }));

  expect(state.updating).toBe(false);
  expect(state.errorUpdating).toEqual({ error: 'Not found' });
  expect(state.weightSummary).toEqual(INITIAL_STATE.weightSummary);
});
test('failure deleting a weightSummary', () => {
  const state = reducer(INITIAL_STATE, Actions.weightSummaryDeleteFailure({ error: 'Not found' }));

  expect(state.deleting).toBe(false);
  expect(state.errorDeleting).toEqual({ error: 'Not found' });
  expect(state.weightSummary).toEqual(INITIAL_STATE.weightSummary);
});

test('resetting state for weightSummary', () => {
  const state = reducer({ ...INITIAL_STATE, deleting: true }, Actions.weightSummaryReset());
  expect(state).toEqual(INITIAL_STATE);
});
