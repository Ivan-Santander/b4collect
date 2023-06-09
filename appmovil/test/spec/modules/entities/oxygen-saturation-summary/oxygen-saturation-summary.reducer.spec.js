import Actions, {
  reducer,
  INITIAL_STATE,
} from '../../../../../app/modules/entities/oxygen-saturation-summary/oxygen-saturation-summary.reducer';

test('attempt retrieving a single oxygenSaturationSummary', () => {
  const state = reducer(INITIAL_STATE, Actions.oxygenSaturationSummaryRequest({ id: 1 }));

  expect(state.fetchingOne).toBe(true);
  expect(state.oxygenSaturationSummary).toEqual({ id: undefined });
});

test('attempt retrieving a list of oxygenSaturationSummary', () => {
  const state = reducer(INITIAL_STATE, Actions.oxygenSaturationSummaryAllRequest({ id: 1 }));

  expect(state.fetchingAll).toBe(true);
  expect(state.oxygenSaturationSummaryList).toEqual([]);
});

test('attempt updating a oxygenSaturationSummary', () => {
  const state = reducer(INITIAL_STATE, Actions.oxygenSaturationSummaryUpdateRequest({ id: 1 }));

  expect(state.updating).toBe(true);
});
test('attempt to deleting a oxygenSaturationSummary', () => {
  const state = reducer(INITIAL_STATE, Actions.oxygenSaturationSummaryDeleteRequest({ id: 1 }));

  expect(state.deleting).toBe(true);
});

test('success retrieving a oxygenSaturationSummary', () => {
  const state = reducer(INITIAL_STATE, Actions.oxygenSaturationSummarySuccess({ id: 1 }));

  expect(state.fetchingOne).toBe(false);
  expect(state.errorOne).toBe(null);
  expect(state.oxygenSaturationSummary).toEqual({ id: 1 });
});

test('success retrieving a list of oxygenSaturationSummary', () => {
  const state = reducer(
    INITIAL_STATE,
    Actions.oxygenSaturationSummaryAllSuccess([{ id: 1 }, { id: 2 }], {
      link: '</?page=1>; rel="last",</?page=0>; rel="first"',
      'x-total-count': 5,
    }),
  );

  expect(state.fetchingAll).toBe(false);
  expect(state.errorAll).toBe(null);
  expect(state.oxygenSaturationSummaryList).toEqual([{ id: 1 }, { id: 2 }]);
  expect(state.links).toEqual({ first: 0, last: 1 });
  expect(state.totalItems).toEqual(5);
});

test('success updating a oxygenSaturationSummary', () => {
  const state = reducer(INITIAL_STATE, Actions.oxygenSaturationSummaryUpdateSuccess({ id: 1 }));

  expect(state.updating).toBe(false);
  expect(state.errorUpdating).toBe(null);
  expect(state.oxygenSaturationSummary).toEqual({ id: 1 });
});
test('success deleting a oxygenSaturationSummary', () => {
  const state = reducer(INITIAL_STATE, Actions.oxygenSaturationSummaryDeleteSuccess());

  expect(state.deleting).toBe(false);
  expect(state.errorDeleting).toBe(null);
  expect(state.oxygenSaturationSummary).toEqual({ id: undefined });
});

test('failure retrieving a oxygenSaturationSummary', () => {
  const state = reducer(INITIAL_STATE, Actions.oxygenSaturationSummaryFailure({ error: 'Not found' }));

  expect(state.fetchingOne).toBe(false);
  expect(state.errorOne).toEqual({ error: 'Not found' });
  expect(state.oxygenSaturationSummary).toEqual({ id: undefined });
});

test('failure retrieving a list of oxygenSaturationSummary', () => {
  const state = reducer(INITIAL_STATE, Actions.oxygenSaturationSummaryAllFailure({ error: 'Not found' }));

  expect(state.fetchingAll).toBe(false);
  expect(state.errorAll).toEqual({ error: 'Not found' });
  expect(state.oxygenSaturationSummaryList).toEqual([]);
});

test('failure updating a oxygenSaturationSummary', () => {
  const state = reducer(INITIAL_STATE, Actions.oxygenSaturationSummaryUpdateFailure({ error: 'Not found' }));

  expect(state.updating).toBe(false);
  expect(state.errorUpdating).toEqual({ error: 'Not found' });
  expect(state.oxygenSaturationSummary).toEqual(INITIAL_STATE.oxygenSaturationSummary);
});
test('failure deleting a oxygenSaturationSummary', () => {
  const state = reducer(INITIAL_STATE, Actions.oxygenSaturationSummaryDeleteFailure({ error: 'Not found' }));

  expect(state.deleting).toBe(false);
  expect(state.errorDeleting).toEqual({ error: 'Not found' });
  expect(state.oxygenSaturationSummary).toEqual(INITIAL_STATE.oxygenSaturationSummary);
});

test('resetting state for oxygenSaturationSummary', () => {
  const state = reducer({ ...INITIAL_STATE, deleting: true }, Actions.oxygenSaturationSummaryReset());
  expect(state).toEqual(INITIAL_STATE);
});
