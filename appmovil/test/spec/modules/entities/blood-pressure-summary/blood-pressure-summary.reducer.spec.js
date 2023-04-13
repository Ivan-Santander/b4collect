import Actions, { reducer, INITIAL_STATE } from '../../../../../app/modules/entities/blood-pressure-summary/blood-pressure-summary.reducer';

test('attempt retrieving a single bloodPressureSummary', () => {
  const state = reducer(INITIAL_STATE, Actions.bloodPressureSummaryRequest({ id: 1 }));

  expect(state.fetchingOne).toBe(true);
  expect(state.bloodPressureSummary).toEqual({ id: undefined });
});

test('attempt retrieving a list of bloodPressureSummary', () => {
  const state = reducer(INITIAL_STATE, Actions.bloodPressureSummaryAllRequest({ id: 1 }));

  expect(state.fetchingAll).toBe(true);
  expect(state.bloodPressureSummaryList).toEqual([]);
});

test('attempt updating a bloodPressureSummary', () => {
  const state = reducer(INITIAL_STATE, Actions.bloodPressureSummaryUpdateRequest({ id: 1 }));

  expect(state.updating).toBe(true);
});
test('attempt to deleting a bloodPressureSummary', () => {
  const state = reducer(INITIAL_STATE, Actions.bloodPressureSummaryDeleteRequest({ id: 1 }));

  expect(state.deleting).toBe(true);
});

test('success retrieving a bloodPressureSummary', () => {
  const state = reducer(INITIAL_STATE, Actions.bloodPressureSummarySuccess({ id: 1 }));

  expect(state.fetchingOne).toBe(false);
  expect(state.errorOne).toBe(null);
  expect(state.bloodPressureSummary).toEqual({ id: 1 });
});

test('success retrieving a list of bloodPressureSummary', () => {
  const state = reducer(
    INITIAL_STATE,
    Actions.bloodPressureSummaryAllSuccess([{ id: 1 }, { id: 2 }], {
      link: '</?page=1>; rel="last",</?page=0>; rel="first"',
      'x-total-count': 5,
    }),
  );

  expect(state.fetchingAll).toBe(false);
  expect(state.errorAll).toBe(null);
  expect(state.bloodPressureSummaryList).toEqual([{ id: 1 }, { id: 2 }]);
  expect(state.links).toEqual({ first: 0, last: 1 });
  expect(state.totalItems).toEqual(5);
});

test('success updating a bloodPressureSummary', () => {
  const state = reducer(INITIAL_STATE, Actions.bloodPressureSummaryUpdateSuccess({ id: 1 }));

  expect(state.updating).toBe(false);
  expect(state.errorUpdating).toBe(null);
  expect(state.bloodPressureSummary).toEqual({ id: 1 });
});
test('success deleting a bloodPressureSummary', () => {
  const state = reducer(INITIAL_STATE, Actions.bloodPressureSummaryDeleteSuccess());

  expect(state.deleting).toBe(false);
  expect(state.errorDeleting).toBe(null);
  expect(state.bloodPressureSummary).toEqual({ id: undefined });
});

test('failure retrieving a bloodPressureSummary', () => {
  const state = reducer(INITIAL_STATE, Actions.bloodPressureSummaryFailure({ error: 'Not found' }));

  expect(state.fetchingOne).toBe(false);
  expect(state.errorOne).toEqual({ error: 'Not found' });
  expect(state.bloodPressureSummary).toEqual({ id: undefined });
});

test('failure retrieving a list of bloodPressureSummary', () => {
  const state = reducer(INITIAL_STATE, Actions.bloodPressureSummaryAllFailure({ error: 'Not found' }));

  expect(state.fetchingAll).toBe(false);
  expect(state.errorAll).toEqual({ error: 'Not found' });
  expect(state.bloodPressureSummaryList).toEqual([]);
});

test('failure updating a bloodPressureSummary', () => {
  const state = reducer(INITIAL_STATE, Actions.bloodPressureSummaryUpdateFailure({ error: 'Not found' }));

  expect(state.updating).toBe(false);
  expect(state.errorUpdating).toEqual({ error: 'Not found' });
  expect(state.bloodPressureSummary).toEqual(INITIAL_STATE.bloodPressureSummary);
});
test('failure deleting a bloodPressureSummary', () => {
  const state = reducer(INITIAL_STATE, Actions.bloodPressureSummaryDeleteFailure({ error: 'Not found' }));

  expect(state.deleting).toBe(false);
  expect(state.errorDeleting).toEqual({ error: 'Not found' });
  expect(state.bloodPressureSummary).toEqual(INITIAL_STATE.bloodPressureSummary);
});

test('resetting state for bloodPressureSummary', () => {
  const state = reducer({ ...INITIAL_STATE, deleting: true }, Actions.bloodPressureSummaryReset());
  expect(state).toEqual(INITIAL_STATE);
});
