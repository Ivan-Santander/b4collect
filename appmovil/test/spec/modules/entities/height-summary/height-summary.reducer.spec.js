import Actions, { reducer, INITIAL_STATE } from '../../../../../app/modules/entities/height-summary/height-summary.reducer';

test('attempt retrieving a single heightSummary', () => {
  const state = reducer(INITIAL_STATE, Actions.heightSummaryRequest({ id: 1 }));

  expect(state.fetchingOne).toBe(true);
  expect(state.heightSummary).toEqual({ id: undefined });
});

test('attempt retrieving a list of heightSummary', () => {
  const state = reducer(INITIAL_STATE, Actions.heightSummaryAllRequest({ id: 1 }));

  expect(state.fetchingAll).toBe(true);
  expect(state.heightSummaryList).toEqual([]);
});

test('attempt updating a heightSummary', () => {
  const state = reducer(INITIAL_STATE, Actions.heightSummaryUpdateRequest({ id: 1 }));

  expect(state.updating).toBe(true);
});
test('attempt to deleting a heightSummary', () => {
  const state = reducer(INITIAL_STATE, Actions.heightSummaryDeleteRequest({ id: 1 }));

  expect(state.deleting).toBe(true);
});

test('success retrieving a heightSummary', () => {
  const state = reducer(INITIAL_STATE, Actions.heightSummarySuccess({ id: 1 }));

  expect(state.fetchingOne).toBe(false);
  expect(state.errorOne).toBe(null);
  expect(state.heightSummary).toEqual({ id: 1 });
});

test('success retrieving a list of heightSummary', () => {
  const state = reducer(
    INITIAL_STATE,
    Actions.heightSummaryAllSuccess([{ id: 1 }, { id: 2 }], { link: '</?page=1>; rel="last",</?page=0>; rel="first"', 'x-total-count': 5 }),
  );

  expect(state.fetchingAll).toBe(false);
  expect(state.errorAll).toBe(null);
  expect(state.heightSummaryList).toEqual([{ id: 1 }, { id: 2 }]);
  expect(state.links).toEqual({ first: 0, last: 1 });
  expect(state.totalItems).toEqual(5);
});

test('success updating a heightSummary', () => {
  const state = reducer(INITIAL_STATE, Actions.heightSummaryUpdateSuccess({ id: 1 }));

  expect(state.updating).toBe(false);
  expect(state.errorUpdating).toBe(null);
  expect(state.heightSummary).toEqual({ id: 1 });
});
test('success deleting a heightSummary', () => {
  const state = reducer(INITIAL_STATE, Actions.heightSummaryDeleteSuccess());

  expect(state.deleting).toBe(false);
  expect(state.errorDeleting).toBe(null);
  expect(state.heightSummary).toEqual({ id: undefined });
});

test('failure retrieving a heightSummary', () => {
  const state = reducer(INITIAL_STATE, Actions.heightSummaryFailure({ error: 'Not found' }));

  expect(state.fetchingOne).toBe(false);
  expect(state.errorOne).toEqual({ error: 'Not found' });
  expect(state.heightSummary).toEqual({ id: undefined });
});

test('failure retrieving a list of heightSummary', () => {
  const state = reducer(INITIAL_STATE, Actions.heightSummaryAllFailure({ error: 'Not found' }));

  expect(state.fetchingAll).toBe(false);
  expect(state.errorAll).toEqual({ error: 'Not found' });
  expect(state.heightSummaryList).toEqual([]);
});

test('failure updating a heightSummary', () => {
  const state = reducer(INITIAL_STATE, Actions.heightSummaryUpdateFailure({ error: 'Not found' }));

  expect(state.updating).toBe(false);
  expect(state.errorUpdating).toEqual({ error: 'Not found' });
  expect(state.heightSummary).toEqual(INITIAL_STATE.heightSummary);
});
test('failure deleting a heightSummary', () => {
  const state = reducer(INITIAL_STATE, Actions.heightSummaryDeleteFailure({ error: 'Not found' }));

  expect(state.deleting).toBe(false);
  expect(state.errorDeleting).toEqual({ error: 'Not found' });
  expect(state.heightSummary).toEqual(INITIAL_STATE.heightSummary);
});

test('resetting state for heightSummary', () => {
  const state = reducer({ ...INITIAL_STATE, deleting: true }, Actions.heightSummaryReset());
  expect(state).toEqual(INITIAL_STATE);
});
