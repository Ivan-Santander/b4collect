import Actions, { reducer, INITIAL_STATE } from '../../../../../app/modules/entities/nutrition-summary/nutrition-summary.reducer';

test('attempt retrieving a single nutritionSummary', () => {
  const state = reducer(INITIAL_STATE, Actions.nutritionSummaryRequest({ id: 1 }));

  expect(state.fetchingOne).toBe(true);
  expect(state.nutritionSummary).toEqual({ id: undefined });
});

test('attempt retrieving a list of nutritionSummary', () => {
  const state = reducer(INITIAL_STATE, Actions.nutritionSummaryAllRequest({ id: 1 }));

  expect(state.fetchingAll).toBe(true);
  expect(state.nutritionSummaryList).toEqual([]);
});

test('attempt updating a nutritionSummary', () => {
  const state = reducer(INITIAL_STATE, Actions.nutritionSummaryUpdateRequest({ id: 1 }));

  expect(state.updating).toBe(true);
});
test('attempt to deleting a nutritionSummary', () => {
  const state = reducer(INITIAL_STATE, Actions.nutritionSummaryDeleteRequest({ id: 1 }));

  expect(state.deleting).toBe(true);
});

test('success retrieving a nutritionSummary', () => {
  const state = reducer(INITIAL_STATE, Actions.nutritionSummarySuccess({ id: 1 }));

  expect(state.fetchingOne).toBe(false);
  expect(state.errorOne).toBe(null);
  expect(state.nutritionSummary).toEqual({ id: 1 });
});

test('success retrieving a list of nutritionSummary', () => {
  const state = reducer(
    INITIAL_STATE,
    Actions.nutritionSummaryAllSuccess([{ id: 1 }, { id: 2 }], {
      link: '</?page=1>; rel="last",</?page=0>; rel="first"',
      'x-total-count': 5,
    }),
  );

  expect(state.fetchingAll).toBe(false);
  expect(state.errorAll).toBe(null);
  expect(state.nutritionSummaryList).toEqual([{ id: 1 }, { id: 2 }]);
  expect(state.links).toEqual({ first: 0, last: 1 });
  expect(state.totalItems).toEqual(5);
});

test('success updating a nutritionSummary', () => {
  const state = reducer(INITIAL_STATE, Actions.nutritionSummaryUpdateSuccess({ id: 1 }));

  expect(state.updating).toBe(false);
  expect(state.errorUpdating).toBe(null);
  expect(state.nutritionSummary).toEqual({ id: 1 });
});
test('success deleting a nutritionSummary', () => {
  const state = reducer(INITIAL_STATE, Actions.nutritionSummaryDeleteSuccess());

  expect(state.deleting).toBe(false);
  expect(state.errorDeleting).toBe(null);
  expect(state.nutritionSummary).toEqual({ id: undefined });
});

test('failure retrieving a nutritionSummary', () => {
  const state = reducer(INITIAL_STATE, Actions.nutritionSummaryFailure({ error: 'Not found' }));

  expect(state.fetchingOne).toBe(false);
  expect(state.errorOne).toEqual({ error: 'Not found' });
  expect(state.nutritionSummary).toEqual({ id: undefined });
});

test('failure retrieving a list of nutritionSummary', () => {
  const state = reducer(INITIAL_STATE, Actions.nutritionSummaryAllFailure({ error: 'Not found' }));

  expect(state.fetchingAll).toBe(false);
  expect(state.errorAll).toEqual({ error: 'Not found' });
  expect(state.nutritionSummaryList).toEqual([]);
});

test('failure updating a nutritionSummary', () => {
  const state = reducer(INITIAL_STATE, Actions.nutritionSummaryUpdateFailure({ error: 'Not found' }));

  expect(state.updating).toBe(false);
  expect(state.errorUpdating).toEqual({ error: 'Not found' });
  expect(state.nutritionSummary).toEqual(INITIAL_STATE.nutritionSummary);
});
test('failure deleting a nutritionSummary', () => {
  const state = reducer(INITIAL_STATE, Actions.nutritionSummaryDeleteFailure({ error: 'Not found' }));

  expect(state.deleting).toBe(false);
  expect(state.errorDeleting).toEqual({ error: 'Not found' });
  expect(state.nutritionSummary).toEqual(INITIAL_STATE.nutritionSummary);
});

test('resetting state for nutritionSummary', () => {
  const state = reducer({ ...INITIAL_STATE, deleting: true }, Actions.nutritionSummaryReset());
  expect(state).toEqual(INITIAL_STATE);
});
