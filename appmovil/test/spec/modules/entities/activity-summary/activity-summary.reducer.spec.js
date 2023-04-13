import Actions, { reducer, INITIAL_STATE } from '../../../../../app/modules/entities/activity-summary/activity-summary.reducer';

test('attempt retrieving a single activitySummary', () => {
  const state = reducer(INITIAL_STATE, Actions.activitySummaryRequest({ id: 1 }));

  expect(state.fetchingOne).toBe(true);
  expect(state.activitySummary).toEqual({ id: undefined });
});

test('attempt retrieving a list of activitySummary', () => {
  const state = reducer(INITIAL_STATE, Actions.activitySummaryAllRequest({ id: 1 }));

  expect(state.fetchingAll).toBe(true);
  expect(state.activitySummaryList).toEqual([]);
});

test('attempt updating a activitySummary', () => {
  const state = reducer(INITIAL_STATE, Actions.activitySummaryUpdateRequest({ id: 1 }));

  expect(state.updating).toBe(true);
});
test('attempt to deleting a activitySummary', () => {
  const state = reducer(INITIAL_STATE, Actions.activitySummaryDeleteRequest({ id: 1 }));

  expect(state.deleting).toBe(true);
});

test('success retrieving a activitySummary', () => {
  const state = reducer(INITIAL_STATE, Actions.activitySummarySuccess({ id: 1 }));

  expect(state.fetchingOne).toBe(false);
  expect(state.errorOne).toBe(null);
  expect(state.activitySummary).toEqual({ id: 1 });
});

test('success retrieving a list of activitySummary', () => {
  const state = reducer(
    INITIAL_STATE,
    Actions.activitySummaryAllSuccess([{ id: 1 }, { id: 2 }], {
      link: '</?page=1>; rel="last",</?page=0>; rel="first"',
      'x-total-count': 5,
    }),
  );

  expect(state.fetchingAll).toBe(false);
  expect(state.errorAll).toBe(null);
  expect(state.activitySummaryList).toEqual([{ id: 1 }, { id: 2 }]);
  expect(state.links).toEqual({ first: 0, last: 1 });
  expect(state.totalItems).toEqual(5);
});

test('success updating a activitySummary', () => {
  const state = reducer(INITIAL_STATE, Actions.activitySummaryUpdateSuccess({ id: 1 }));

  expect(state.updating).toBe(false);
  expect(state.errorUpdating).toBe(null);
  expect(state.activitySummary).toEqual({ id: 1 });
});
test('success deleting a activitySummary', () => {
  const state = reducer(INITIAL_STATE, Actions.activitySummaryDeleteSuccess());

  expect(state.deleting).toBe(false);
  expect(state.errorDeleting).toBe(null);
  expect(state.activitySummary).toEqual({ id: undefined });
});

test('failure retrieving a activitySummary', () => {
  const state = reducer(INITIAL_STATE, Actions.activitySummaryFailure({ error: 'Not found' }));

  expect(state.fetchingOne).toBe(false);
  expect(state.errorOne).toEqual({ error: 'Not found' });
  expect(state.activitySummary).toEqual({ id: undefined });
});

test('failure retrieving a list of activitySummary', () => {
  const state = reducer(INITIAL_STATE, Actions.activitySummaryAllFailure({ error: 'Not found' }));

  expect(state.fetchingAll).toBe(false);
  expect(state.errorAll).toEqual({ error: 'Not found' });
  expect(state.activitySummaryList).toEqual([]);
});

test('failure updating a activitySummary', () => {
  const state = reducer(INITIAL_STATE, Actions.activitySummaryUpdateFailure({ error: 'Not found' }));

  expect(state.updating).toBe(false);
  expect(state.errorUpdating).toEqual({ error: 'Not found' });
  expect(state.activitySummary).toEqual(INITIAL_STATE.activitySummary);
});
test('failure deleting a activitySummary', () => {
  const state = reducer(INITIAL_STATE, Actions.activitySummaryDeleteFailure({ error: 'Not found' }));

  expect(state.deleting).toBe(false);
  expect(state.errorDeleting).toEqual({ error: 'Not found' });
  expect(state.activitySummary).toEqual(INITIAL_STATE.activitySummary);
});

test('resetting state for activitySummary', () => {
  const state = reducer({ ...INITIAL_STATE, deleting: true }, Actions.activitySummaryReset());
  expect(state).toEqual(INITIAL_STATE);
});
