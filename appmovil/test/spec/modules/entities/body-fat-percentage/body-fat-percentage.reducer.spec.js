import Actions, { reducer, INITIAL_STATE } from '../../../../../app/modules/entities/body-fat-percentage/body-fat-percentage.reducer';

test('attempt retrieving a single bodyFatPercentage', () => {
  const state = reducer(INITIAL_STATE, Actions.bodyFatPercentageRequest({ id: 1 }));

  expect(state.fetchingOne).toBe(true);
  expect(state.bodyFatPercentage).toEqual({ id: undefined });
});

test('attempt retrieving a list of bodyFatPercentage', () => {
  const state = reducer(INITIAL_STATE, Actions.bodyFatPercentageAllRequest({ id: 1 }));

  expect(state.fetchingAll).toBe(true);
  expect(state.bodyFatPercentageList).toEqual([]);
});

test('attempt updating a bodyFatPercentage', () => {
  const state = reducer(INITIAL_STATE, Actions.bodyFatPercentageUpdateRequest({ id: 1 }));

  expect(state.updating).toBe(true);
});
test('attempt to deleting a bodyFatPercentage', () => {
  const state = reducer(INITIAL_STATE, Actions.bodyFatPercentageDeleteRequest({ id: 1 }));

  expect(state.deleting).toBe(true);
});

test('success retrieving a bodyFatPercentage', () => {
  const state = reducer(INITIAL_STATE, Actions.bodyFatPercentageSuccess({ id: 1 }));

  expect(state.fetchingOne).toBe(false);
  expect(state.errorOne).toBe(null);
  expect(state.bodyFatPercentage).toEqual({ id: 1 });
});

test('success retrieving a list of bodyFatPercentage', () => {
  const state = reducer(
    INITIAL_STATE,
    Actions.bodyFatPercentageAllSuccess([{ id: 1 }, { id: 2 }], {
      link: '</?page=1>; rel="last",</?page=0>; rel="first"',
      'x-total-count': 5,
    }),
  );

  expect(state.fetchingAll).toBe(false);
  expect(state.errorAll).toBe(null);
  expect(state.bodyFatPercentageList).toEqual([{ id: 1 }, { id: 2 }]);
  expect(state.links).toEqual({ first: 0, last: 1 });
  expect(state.totalItems).toEqual(5);
});

test('success updating a bodyFatPercentage', () => {
  const state = reducer(INITIAL_STATE, Actions.bodyFatPercentageUpdateSuccess({ id: 1 }));

  expect(state.updating).toBe(false);
  expect(state.errorUpdating).toBe(null);
  expect(state.bodyFatPercentage).toEqual({ id: 1 });
});
test('success deleting a bodyFatPercentage', () => {
  const state = reducer(INITIAL_STATE, Actions.bodyFatPercentageDeleteSuccess());

  expect(state.deleting).toBe(false);
  expect(state.errorDeleting).toBe(null);
  expect(state.bodyFatPercentage).toEqual({ id: undefined });
});

test('failure retrieving a bodyFatPercentage', () => {
  const state = reducer(INITIAL_STATE, Actions.bodyFatPercentageFailure({ error: 'Not found' }));

  expect(state.fetchingOne).toBe(false);
  expect(state.errorOne).toEqual({ error: 'Not found' });
  expect(state.bodyFatPercentage).toEqual({ id: undefined });
});

test('failure retrieving a list of bodyFatPercentage', () => {
  const state = reducer(INITIAL_STATE, Actions.bodyFatPercentageAllFailure({ error: 'Not found' }));

  expect(state.fetchingAll).toBe(false);
  expect(state.errorAll).toEqual({ error: 'Not found' });
  expect(state.bodyFatPercentageList).toEqual([]);
});

test('failure updating a bodyFatPercentage', () => {
  const state = reducer(INITIAL_STATE, Actions.bodyFatPercentageUpdateFailure({ error: 'Not found' }));

  expect(state.updating).toBe(false);
  expect(state.errorUpdating).toEqual({ error: 'Not found' });
  expect(state.bodyFatPercentage).toEqual(INITIAL_STATE.bodyFatPercentage);
});
test('failure deleting a bodyFatPercentage', () => {
  const state = reducer(INITIAL_STATE, Actions.bodyFatPercentageDeleteFailure({ error: 'Not found' }));

  expect(state.deleting).toBe(false);
  expect(state.errorDeleting).toEqual({ error: 'Not found' });
  expect(state.bodyFatPercentage).toEqual(INITIAL_STATE.bodyFatPercentage);
});

test('resetting state for bodyFatPercentage', () => {
  const state = reducer({ ...INITIAL_STATE, deleting: true }, Actions.bodyFatPercentageReset());
  expect(state).toEqual(INITIAL_STATE);
});
