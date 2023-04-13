import Actions, { reducer, INITIAL_STATE } from '../../../../../app/modules/entities/temperature-summary/temperature-summary.reducer';

test('attempt retrieving a single temperatureSummary', () => {
  const state = reducer(INITIAL_STATE, Actions.temperatureSummaryRequest({ id: 1 }));

  expect(state.fetchingOne).toBe(true);
  expect(state.temperatureSummary).toEqual({ id: undefined });
});

test('attempt retrieving a list of temperatureSummary', () => {
  const state = reducer(INITIAL_STATE, Actions.temperatureSummaryAllRequest({ id: 1 }));

  expect(state.fetchingAll).toBe(true);
  expect(state.temperatureSummaryList).toEqual([]);
});

test('attempt updating a temperatureSummary', () => {
  const state = reducer(INITIAL_STATE, Actions.temperatureSummaryUpdateRequest({ id: 1 }));

  expect(state.updating).toBe(true);
});
test('attempt to deleting a temperatureSummary', () => {
  const state = reducer(INITIAL_STATE, Actions.temperatureSummaryDeleteRequest({ id: 1 }));

  expect(state.deleting).toBe(true);
});

test('success retrieving a temperatureSummary', () => {
  const state = reducer(INITIAL_STATE, Actions.temperatureSummarySuccess({ id: 1 }));

  expect(state.fetchingOne).toBe(false);
  expect(state.errorOne).toBe(null);
  expect(state.temperatureSummary).toEqual({ id: 1 });
});

test('success retrieving a list of temperatureSummary', () => {
  const state = reducer(
    INITIAL_STATE,
    Actions.temperatureSummaryAllSuccess([{ id: 1 }, { id: 2 }], {
      link: '</?page=1>; rel="last",</?page=0>; rel="first"',
      'x-total-count': 5,
    }),
  );

  expect(state.fetchingAll).toBe(false);
  expect(state.errorAll).toBe(null);
  expect(state.temperatureSummaryList).toEqual([{ id: 1 }, { id: 2 }]);
  expect(state.links).toEqual({ first: 0, last: 1 });
  expect(state.totalItems).toEqual(5);
});

test('success updating a temperatureSummary', () => {
  const state = reducer(INITIAL_STATE, Actions.temperatureSummaryUpdateSuccess({ id: 1 }));

  expect(state.updating).toBe(false);
  expect(state.errorUpdating).toBe(null);
  expect(state.temperatureSummary).toEqual({ id: 1 });
});
test('success deleting a temperatureSummary', () => {
  const state = reducer(INITIAL_STATE, Actions.temperatureSummaryDeleteSuccess());

  expect(state.deleting).toBe(false);
  expect(state.errorDeleting).toBe(null);
  expect(state.temperatureSummary).toEqual({ id: undefined });
});

test('failure retrieving a temperatureSummary', () => {
  const state = reducer(INITIAL_STATE, Actions.temperatureSummaryFailure({ error: 'Not found' }));

  expect(state.fetchingOne).toBe(false);
  expect(state.errorOne).toEqual({ error: 'Not found' });
  expect(state.temperatureSummary).toEqual({ id: undefined });
});

test('failure retrieving a list of temperatureSummary', () => {
  const state = reducer(INITIAL_STATE, Actions.temperatureSummaryAllFailure({ error: 'Not found' }));

  expect(state.fetchingAll).toBe(false);
  expect(state.errorAll).toEqual({ error: 'Not found' });
  expect(state.temperatureSummaryList).toEqual([]);
});

test('failure updating a temperatureSummary', () => {
  const state = reducer(INITIAL_STATE, Actions.temperatureSummaryUpdateFailure({ error: 'Not found' }));

  expect(state.updating).toBe(false);
  expect(state.errorUpdating).toEqual({ error: 'Not found' });
  expect(state.temperatureSummary).toEqual(INITIAL_STATE.temperatureSummary);
});
test('failure deleting a temperatureSummary', () => {
  const state = reducer(INITIAL_STATE, Actions.temperatureSummaryDeleteFailure({ error: 'Not found' }));

  expect(state.deleting).toBe(false);
  expect(state.errorDeleting).toEqual({ error: 'Not found' });
  expect(state.temperatureSummary).toEqual(INITIAL_STATE.temperatureSummary);
});

test('resetting state for temperatureSummary', () => {
  const state = reducer({ ...INITIAL_STATE, deleting: true }, Actions.temperatureSummaryReset());
  expect(state).toEqual(INITIAL_STATE);
});
