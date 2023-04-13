import Actions, { reducer, INITIAL_STATE } from '../../../../../app/modules/entities/heart-rate-summary/heart-rate-summary.reducer';

test('attempt retrieving a single heartRateSummary', () => {
  const state = reducer(INITIAL_STATE, Actions.heartRateSummaryRequest({ id: 1 }));

  expect(state.fetchingOne).toBe(true);
  expect(state.heartRateSummary).toEqual({ id: undefined });
});

test('attempt retrieving a list of heartRateSummary', () => {
  const state = reducer(INITIAL_STATE, Actions.heartRateSummaryAllRequest({ id: 1 }));

  expect(state.fetchingAll).toBe(true);
  expect(state.heartRateSummaryList).toEqual([]);
});

test('attempt updating a heartRateSummary', () => {
  const state = reducer(INITIAL_STATE, Actions.heartRateSummaryUpdateRequest({ id: 1 }));

  expect(state.updating).toBe(true);
});
test('attempt to deleting a heartRateSummary', () => {
  const state = reducer(INITIAL_STATE, Actions.heartRateSummaryDeleteRequest({ id: 1 }));

  expect(state.deleting).toBe(true);
});

test('success retrieving a heartRateSummary', () => {
  const state = reducer(INITIAL_STATE, Actions.heartRateSummarySuccess({ id: 1 }));

  expect(state.fetchingOne).toBe(false);
  expect(state.errorOne).toBe(null);
  expect(state.heartRateSummary).toEqual({ id: 1 });
});

test('success retrieving a list of heartRateSummary', () => {
  const state = reducer(
    INITIAL_STATE,
    Actions.heartRateSummaryAllSuccess([{ id: 1 }, { id: 2 }], {
      link: '</?page=1>; rel="last",</?page=0>; rel="first"',
      'x-total-count': 5,
    }),
  );

  expect(state.fetchingAll).toBe(false);
  expect(state.errorAll).toBe(null);
  expect(state.heartRateSummaryList).toEqual([{ id: 1 }, { id: 2 }]);
  expect(state.links).toEqual({ first: 0, last: 1 });
  expect(state.totalItems).toEqual(5);
});

test('success updating a heartRateSummary', () => {
  const state = reducer(INITIAL_STATE, Actions.heartRateSummaryUpdateSuccess({ id: 1 }));

  expect(state.updating).toBe(false);
  expect(state.errorUpdating).toBe(null);
  expect(state.heartRateSummary).toEqual({ id: 1 });
});
test('success deleting a heartRateSummary', () => {
  const state = reducer(INITIAL_STATE, Actions.heartRateSummaryDeleteSuccess());

  expect(state.deleting).toBe(false);
  expect(state.errorDeleting).toBe(null);
  expect(state.heartRateSummary).toEqual({ id: undefined });
});

test('failure retrieving a heartRateSummary', () => {
  const state = reducer(INITIAL_STATE, Actions.heartRateSummaryFailure({ error: 'Not found' }));

  expect(state.fetchingOne).toBe(false);
  expect(state.errorOne).toEqual({ error: 'Not found' });
  expect(state.heartRateSummary).toEqual({ id: undefined });
});

test('failure retrieving a list of heartRateSummary', () => {
  const state = reducer(INITIAL_STATE, Actions.heartRateSummaryAllFailure({ error: 'Not found' }));

  expect(state.fetchingAll).toBe(false);
  expect(state.errorAll).toEqual({ error: 'Not found' });
  expect(state.heartRateSummaryList).toEqual([]);
});

test('failure updating a heartRateSummary', () => {
  const state = reducer(INITIAL_STATE, Actions.heartRateSummaryUpdateFailure({ error: 'Not found' }));

  expect(state.updating).toBe(false);
  expect(state.errorUpdating).toEqual({ error: 'Not found' });
  expect(state.heartRateSummary).toEqual(INITIAL_STATE.heartRateSummary);
});
test('failure deleting a heartRateSummary', () => {
  const state = reducer(INITIAL_STATE, Actions.heartRateSummaryDeleteFailure({ error: 'Not found' }));

  expect(state.deleting).toBe(false);
  expect(state.errorDeleting).toEqual({ error: 'Not found' });
  expect(state.heartRateSummary).toEqual(INITIAL_STATE.heartRateSummary);
});

test('resetting state for heartRateSummary', () => {
  const state = reducer({ ...INITIAL_STATE, deleting: true }, Actions.heartRateSummaryReset());
  expect(state).toEqual(INITIAL_STATE);
});
