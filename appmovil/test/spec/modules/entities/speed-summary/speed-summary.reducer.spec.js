import Actions, { reducer, INITIAL_STATE } from '../../../../../app/modules/entities/speed-summary/speed-summary.reducer';

test('attempt retrieving a single speedSummary', () => {
  const state = reducer(INITIAL_STATE, Actions.speedSummaryRequest({ id: 1 }));

  expect(state.fetchingOne).toBe(true);
  expect(state.speedSummary).toEqual({ id: undefined });
});

test('attempt retrieving a list of speedSummary', () => {
  const state = reducer(INITIAL_STATE, Actions.speedSummaryAllRequest({ id: 1 }));

  expect(state.fetchingAll).toBe(true);
  expect(state.speedSummaryList).toEqual([]);
});

test('attempt updating a speedSummary', () => {
  const state = reducer(INITIAL_STATE, Actions.speedSummaryUpdateRequest({ id: 1 }));

  expect(state.updating).toBe(true);
});
test('attempt to deleting a speedSummary', () => {
  const state = reducer(INITIAL_STATE, Actions.speedSummaryDeleteRequest({ id: 1 }));

  expect(state.deleting).toBe(true);
});

test('success retrieving a speedSummary', () => {
  const state = reducer(INITIAL_STATE, Actions.speedSummarySuccess({ id: 1 }));

  expect(state.fetchingOne).toBe(false);
  expect(state.errorOne).toBe(null);
  expect(state.speedSummary).toEqual({ id: 1 });
});

test('success retrieving a list of speedSummary', () => {
  const state = reducer(
    INITIAL_STATE,
    Actions.speedSummaryAllSuccess([{ id: 1 }, { id: 2 }], { link: '</?page=1>; rel="last",</?page=0>; rel="first"', 'x-total-count': 5 }),
  );

  expect(state.fetchingAll).toBe(false);
  expect(state.errorAll).toBe(null);
  expect(state.speedSummaryList).toEqual([{ id: 1 }, { id: 2 }]);
  expect(state.links).toEqual({ first: 0, last: 1 });
  expect(state.totalItems).toEqual(5);
});

test('success updating a speedSummary', () => {
  const state = reducer(INITIAL_STATE, Actions.speedSummaryUpdateSuccess({ id: 1 }));

  expect(state.updating).toBe(false);
  expect(state.errorUpdating).toBe(null);
  expect(state.speedSummary).toEqual({ id: 1 });
});
test('success deleting a speedSummary', () => {
  const state = reducer(INITIAL_STATE, Actions.speedSummaryDeleteSuccess());

  expect(state.deleting).toBe(false);
  expect(state.errorDeleting).toBe(null);
  expect(state.speedSummary).toEqual({ id: undefined });
});

test('failure retrieving a speedSummary', () => {
  const state = reducer(INITIAL_STATE, Actions.speedSummaryFailure({ error: 'Not found' }));

  expect(state.fetchingOne).toBe(false);
  expect(state.errorOne).toEqual({ error: 'Not found' });
  expect(state.speedSummary).toEqual({ id: undefined });
});

test('failure retrieving a list of speedSummary', () => {
  const state = reducer(INITIAL_STATE, Actions.speedSummaryAllFailure({ error: 'Not found' }));

  expect(state.fetchingAll).toBe(false);
  expect(state.errorAll).toEqual({ error: 'Not found' });
  expect(state.speedSummaryList).toEqual([]);
});

test('failure updating a speedSummary', () => {
  const state = reducer(INITIAL_STATE, Actions.speedSummaryUpdateFailure({ error: 'Not found' }));

  expect(state.updating).toBe(false);
  expect(state.errorUpdating).toEqual({ error: 'Not found' });
  expect(state.speedSummary).toEqual(INITIAL_STATE.speedSummary);
});
test('failure deleting a speedSummary', () => {
  const state = reducer(INITIAL_STATE, Actions.speedSummaryDeleteFailure({ error: 'Not found' }));

  expect(state.deleting).toBe(false);
  expect(state.errorDeleting).toEqual({ error: 'Not found' });
  expect(state.speedSummary).toEqual(INITIAL_STATE.speedSummary);
});

test('resetting state for speedSummary', () => {
  const state = reducer({ ...INITIAL_STATE, deleting: true }, Actions.speedSummaryReset());
  expect(state).toEqual(INITIAL_STATE);
});
