import Actions, { reducer, INITIAL_STATE } from '../../../../../app/modules/entities/blood-glucose-summary/blood-glucose-summary.reducer';

test('attempt retrieving a single bloodGlucoseSummary', () => {
  const state = reducer(INITIAL_STATE, Actions.bloodGlucoseSummaryRequest({ id: 1 }));

  expect(state.fetchingOne).toBe(true);
  expect(state.bloodGlucoseSummary).toEqual({ id: undefined });
});

test('attempt retrieving a list of bloodGlucoseSummary', () => {
  const state = reducer(INITIAL_STATE, Actions.bloodGlucoseSummaryAllRequest({ id: 1 }));

  expect(state.fetchingAll).toBe(true);
  expect(state.bloodGlucoseSummaryList).toEqual([]);
});

test('attempt updating a bloodGlucoseSummary', () => {
  const state = reducer(INITIAL_STATE, Actions.bloodGlucoseSummaryUpdateRequest({ id: 1 }));

  expect(state.updating).toBe(true);
});
test('attempt to deleting a bloodGlucoseSummary', () => {
  const state = reducer(INITIAL_STATE, Actions.bloodGlucoseSummaryDeleteRequest({ id: 1 }));

  expect(state.deleting).toBe(true);
});

test('success retrieving a bloodGlucoseSummary', () => {
  const state = reducer(INITIAL_STATE, Actions.bloodGlucoseSummarySuccess({ id: 1 }));

  expect(state.fetchingOne).toBe(false);
  expect(state.errorOne).toBe(null);
  expect(state.bloodGlucoseSummary).toEqual({ id: 1 });
});

test('success retrieving a list of bloodGlucoseSummary', () => {
  const state = reducer(
    INITIAL_STATE,
    Actions.bloodGlucoseSummaryAllSuccess([{ id: 1 }, { id: 2 }], {
      link: '</?page=1>; rel="last",</?page=0>; rel="first"',
      'x-total-count': 5,
    }),
  );

  expect(state.fetchingAll).toBe(false);
  expect(state.errorAll).toBe(null);
  expect(state.bloodGlucoseSummaryList).toEqual([{ id: 1 }, { id: 2 }]);
  expect(state.links).toEqual({ first: 0, last: 1 });
  expect(state.totalItems).toEqual(5);
});

test('success updating a bloodGlucoseSummary', () => {
  const state = reducer(INITIAL_STATE, Actions.bloodGlucoseSummaryUpdateSuccess({ id: 1 }));

  expect(state.updating).toBe(false);
  expect(state.errorUpdating).toBe(null);
  expect(state.bloodGlucoseSummary).toEqual({ id: 1 });
});
test('success deleting a bloodGlucoseSummary', () => {
  const state = reducer(INITIAL_STATE, Actions.bloodGlucoseSummaryDeleteSuccess());

  expect(state.deleting).toBe(false);
  expect(state.errorDeleting).toBe(null);
  expect(state.bloodGlucoseSummary).toEqual({ id: undefined });
});

test('failure retrieving a bloodGlucoseSummary', () => {
  const state = reducer(INITIAL_STATE, Actions.bloodGlucoseSummaryFailure({ error: 'Not found' }));

  expect(state.fetchingOne).toBe(false);
  expect(state.errorOne).toEqual({ error: 'Not found' });
  expect(state.bloodGlucoseSummary).toEqual({ id: undefined });
});

test('failure retrieving a list of bloodGlucoseSummary', () => {
  const state = reducer(INITIAL_STATE, Actions.bloodGlucoseSummaryAllFailure({ error: 'Not found' }));

  expect(state.fetchingAll).toBe(false);
  expect(state.errorAll).toEqual({ error: 'Not found' });
  expect(state.bloodGlucoseSummaryList).toEqual([]);
});

test('failure updating a bloodGlucoseSummary', () => {
  const state = reducer(INITIAL_STATE, Actions.bloodGlucoseSummaryUpdateFailure({ error: 'Not found' }));

  expect(state.updating).toBe(false);
  expect(state.errorUpdating).toEqual({ error: 'Not found' });
  expect(state.bloodGlucoseSummary).toEqual(INITIAL_STATE.bloodGlucoseSummary);
});
test('failure deleting a bloodGlucoseSummary', () => {
  const state = reducer(INITIAL_STATE, Actions.bloodGlucoseSummaryDeleteFailure({ error: 'Not found' }));

  expect(state.deleting).toBe(false);
  expect(state.errorDeleting).toEqual({ error: 'Not found' });
  expect(state.bloodGlucoseSummary).toEqual(INITIAL_STATE.bloodGlucoseSummary);
});

test('resetting state for bloodGlucoseSummary', () => {
  const state = reducer({ ...INITIAL_STATE, deleting: true }, Actions.bloodGlucoseSummaryReset());
  expect(state).toEqual(INITIAL_STATE);
});
