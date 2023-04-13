import Actions, {
  reducer,
  INITIAL_STATE,
} from '../../../../../app/modules/entities/body-fat-percentage-summary/body-fat-percentage-summary.reducer';

test('attempt retrieving a single bodyFatPercentageSummary', () => {
  const state = reducer(INITIAL_STATE, Actions.bodyFatPercentageSummaryRequest({ id: 1 }));

  expect(state.fetchingOne).toBe(true);
  expect(state.bodyFatPercentageSummary).toEqual({ id: undefined });
});

test('attempt retrieving a list of bodyFatPercentageSummary', () => {
  const state = reducer(INITIAL_STATE, Actions.bodyFatPercentageSummaryAllRequest({ id: 1 }));

  expect(state.fetchingAll).toBe(true);
  expect(state.bodyFatPercentageSummaryList).toEqual([]);
});

test('attempt updating a bodyFatPercentageSummary', () => {
  const state = reducer(INITIAL_STATE, Actions.bodyFatPercentageSummaryUpdateRequest({ id: 1 }));

  expect(state.updating).toBe(true);
});
test('attempt to deleting a bodyFatPercentageSummary', () => {
  const state = reducer(INITIAL_STATE, Actions.bodyFatPercentageSummaryDeleteRequest({ id: 1 }));

  expect(state.deleting).toBe(true);
});

test('success retrieving a bodyFatPercentageSummary', () => {
  const state = reducer(INITIAL_STATE, Actions.bodyFatPercentageSummarySuccess({ id: 1 }));

  expect(state.fetchingOne).toBe(false);
  expect(state.errorOne).toBe(null);
  expect(state.bodyFatPercentageSummary).toEqual({ id: 1 });
});

test('success retrieving a list of bodyFatPercentageSummary', () => {
  const state = reducer(
    INITIAL_STATE,
    Actions.bodyFatPercentageSummaryAllSuccess([{ id: 1 }, { id: 2 }], {
      link: '</?page=1>; rel="last",</?page=0>; rel="first"',
      'x-total-count': 5,
    }),
  );

  expect(state.fetchingAll).toBe(false);
  expect(state.errorAll).toBe(null);
  expect(state.bodyFatPercentageSummaryList).toEqual([{ id: 1 }, { id: 2 }]);
  expect(state.links).toEqual({ first: 0, last: 1 });
  expect(state.totalItems).toEqual(5);
});

test('success updating a bodyFatPercentageSummary', () => {
  const state = reducer(INITIAL_STATE, Actions.bodyFatPercentageSummaryUpdateSuccess({ id: 1 }));

  expect(state.updating).toBe(false);
  expect(state.errorUpdating).toBe(null);
  expect(state.bodyFatPercentageSummary).toEqual({ id: 1 });
});
test('success deleting a bodyFatPercentageSummary', () => {
  const state = reducer(INITIAL_STATE, Actions.bodyFatPercentageSummaryDeleteSuccess());

  expect(state.deleting).toBe(false);
  expect(state.errorDeleting).toBe(null);
  expect(state.bodyFatPercentageSummary).toEqual({ id: undefined });
});

test('failure retrieving a bodyFatPercentageSummary', () => {
  const state = reducer(INITIAL_STATE, Actions.bodyFatPercentageSummaryFailure({ error: 'Not found' }));

  expect(state.fetchingOne).toBe(false);
  expect(state.errorOne).toEqual({ error: 'Not found' });
  expect(state.bodyFatPercentageSummary).toEqual({ id: undefined });
});

test('failure retrieving a list of bodyFatPercentageSummary', () => {
  const state = reducer(INITIAL_STATE, Actions.bodyFatPercentageSummaryAllFailure({ error: 'Not found' }));

  expect(state.fetchingAll).toBe(false);
  expect(state.errorAll).toEqual({ error: 'Not found' });
  expect(state.bodyFatPercentageSummaryList).toEqual([]);
});

test('failure updating a bodyFatPercentageSummary', () => {
  const state = reducer(INITIAL_STATE, Actions.bodyFatPercentageSummaryUpdateFailure({ error: 'Not found' }));

  expect(state.updating).toBe(false);
  expect(state.errorUpdating).toEqual({ error: 'Not found' });
  expect(state.bodyFatPercentageSummary).toEqual(INITIAL_STATE.bodyFatPercentageSummary);
});
test('failure deleting a bodyFatPercentageSummary', () => {
  const state = reducer(INITIAL_STATE, Actions.bodyFatPercentageSummaryDeleteFailure({ error: 'Not found' }));

  expect(state.deleting).toBe(false);
  expect(state.errorDeleting).toEqual({ error: 'Not found' });
  expect(state.bodyFatPercentageSummary).toEqual(INITIAL_STATE.bodyFatPercentageSummary);
});

test('resetting state for bodyFatPercentageSummary', () => {
  const state = reducer({ ...INITIAL_STATE, deleting: true }, Actions.bodyFatPercentageSummaryReset());
  expect(state).toEqual(INITIAL_STATE);
});
