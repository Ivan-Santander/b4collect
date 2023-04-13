import Actions, { reducer, INITIAL_STATE } from '../../../../../app/modules/entities/calories-bmr-summary/calories-bmr-summary.reducer';

test('attempt retrieving a single caloriesBmrSummary', () => {
  const state = reducer(INITIAL_STATE, Actions.caloriesBmrSummaryRequest({ id: 1 }));

  expect(state.fetchingOne).toBe(true);
  expect(state.caloriesBmrSummary).toEqual({ id: undefined });
});

test('attempt retrieving a list of caloriesBmrSummary', () => {
  const state = reducer(INITIAL_STATE, Actions.caloriesBmrSummaryAllRequest({ id: 1 }));

  expect(state.fetchingAll).toBe(true);
  expect(state.caloriesBmrSummaryList).toEqual([]);
});

test('attempt updating a caloriesBmrSummary', () => {
  const state = reducer(INITIAL_STATE, Actions.caloriesBmrSummaryUpdateRequest({ id: 1 }));

  expect(state.updating).toBe(true);
});
test('attempt to deleting a caloriesBmrSummary', () => {
  const state = reducer(INITIAL_STATE, Actions.caloriesBmrSummaryDeleteRequest({ id: 1 }));

  expect(state.deleting).toBe(true);
});

test('success retrieving a caloriesBmrSummary', () => {
  const state = reducer(INITIAL_STATE, Actions.caloriesBmrSummarySuccess({ id: 1 }));

  expect(state.fetchingOne).toBe(false);
  expect(state.errorOne).toBe(null);
  expect(state.caloriesBmrSummary).toEqual({ id: 1 });
});

test('success retrieving a list of caloriesBmrSummary', () => {
  const state = reducer(
    INITIAL_STATE,
    Actions.caloriesBmrSummaryAllSuccess([{ id: 1 }, { id: 2 }], {
      link: '</?page=1>; rel="last",</?page=0>; rel="first"',
      'x-total-count': 5,
    }),
  );

  expect(state.fetchingAll).toBe(false);
  expect(state.errorAll).toBe(null);
  expect(state.caloriesBmrSummaryList).toEqual([{ id: 1 }, { id: 2 }]);
  expect(state.links).toEqual({ first: 0, last: 1 });
  expect(state.totalItems).toEqual(5);
});

test('success updating a caloriesBmrSummary', () => {
  const state = reducer(INITIAL_STATE, Actions.caloriesBmrSummaryUpdateSuccess({ id: 1 }));

  expect(state.updating).toBe(false);
  expect(state.errorUpdating).toBe(null);
  expect(state.caloriesBmrSummary).toEqual({ id: 1 });
});
test('success deleting a caloriesBmrSummary', () => {
  const state = reducer(INITIAL_STATE, Actions.caloriesBmrSummaryDeleteSuccess());

  expect(state.deleting).toBe(false);
  expect(state.errorDeleting).toBe(null);
  expect(state.caloriesBmrSummary).toEqual({ id: undefined });
});

test('failure retrieving a caloriesBmrSummary', () => {
  const state = reducer(INITIAL_STATE, Actions.caloriesBmrSummaryFailure({ error: 'Not found' }));

  expect(state.fetchingOne).toBe(false);
  expect(state.errorOne).toEqual({ error: 'Not found' });
  expect(state.caloriesBmrSummary).toEqual({ id: undefined });
});

test('failure retrieving a list of caloriesBmrSummary', () => {
  const state = reducer(INITIAL_STATE, Actions.caloriesBmrSummaryAllFailure({ error: 'Not found' }));

  expect(state.fetchingAll).toBe(false);
  expect(state.errorAll).toEqual({ error: 'Not found' });
  expect(state.caloriesBmrSummaryList).toEqual([]);
});

test('failure updating a caloriesBmrSummary', () => {
  const state = reducer(INITIAL_STATE, Actions.caloriesBmrSummaryUpdateFailure({ error: 'Not found' }));

  expect(state.updating).toBe(false);
  expect(state.errorUpdating).toEqual({ error: 'Not found' });
  expect(state.caloriesBmrSummary).toEqual(INITIAL_STATE.caloriesBmrSummary);
});
test('failure deleting a caloriesBmrSummary', () => {
  const state = reducer(INITIAL_STATE, Actions.caloriesBmrSummaryDeleteFailure({ error: 'Not found' }));

  expect(state.deleting).toBe(false);
  expect(state.errorDeleting).toEqual({ error: 'Not found' });
  expect(state.caloriesBmrSummary).toEqual(INITIAL_STATE.caloriesBmrSummary);
});

test('resetting state for caloriesBmrSummary', () => {
  const state = reducer({ ...INITIAL_STATE, deleting: true }, Actions.caloriesBmrSummaryReset());
  expect(state).toEqual(INITIAL_STATE);
});
