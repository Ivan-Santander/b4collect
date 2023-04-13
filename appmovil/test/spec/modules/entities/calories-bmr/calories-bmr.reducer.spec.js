import Actions, { reducer, INITIAL_STATE } from '../../../../../app/modules/entities/calories-bmr/calories-bmr.reducer';

test('attempt retrieving a single caloriesBMR', () => {
  const state = reducer(INITIAL_STATE, Actions.caloriesBMRRequest({ id: 1 }));

  expect(state.fetchingOne).toBe(true);
  expect(state.caloriesBMR).toEqual({ id: undefined });
});

test('attempt retrieving a list of caloriesBMR', () => {
  const state = reducer(INITIAL_STATE, Actions.caloriesBMRAllRequest({ id: 1 }));

  expect(state.fetchingAll).toBe(true);
  expect(state.caloriesBMRList).toEqual([]);
});

test('attempt updating a caloriesBMR', () => {
  const state = reducer(INITIAL_STATE, Actions.caloriesBMRUpdateRequest({ id: 1 }));

  expect(state.updating).toBe(true);
});
test('attempt to deleting a caloriesBMR', () => {
  const state = reducer(INITIAL_STATE, Actions.caloriesBMRDeleteRequest({ id: 1 }));

  expect(state.deleting).toBe(true);
});

test('success retrieving a caloriesBMR', () => {
  const state = reducer(INITIAL_STATE, Actions.caloriesBMRSuccess({ id: 1 }));

  expect(state.fetchingOne).toBe(false);
  expect(state.errorOne).toBe(null);
  expect(state.caloriesBMR).toEqual({ id: 1 });
});

test('success retrieving a list of caloriesBMR', () => {
  const state = reducer(
    INITIAL_STATE,
    Actions.caloriesBMRAllSuccess([{ id: 1 }, { id: 2 }], { link: '</?page=1>; rel="last",</?page=0>; rel="first"', 'x-total-count': 5 }),
  );

  expect(state.fetchingAll).toBe(false);
  expect(state.errorAll).toBe(null);
  expect(state.caloriesBMRList).toEqual([{ id: 1 }, { id: 2 }]);
  expect(state.links).toEqual({ first: 0, last: 1 });
  expect(state.totalItems).toEqual(5);
});

test('success updating a caloriesBMR', () => {
  const state = reducer(INITIAL_STATE, Actions.caloriesBMRUpdateSuccess({ id: 1 }));

  expect(state.updating).toBe(false);
  expect(state.errorUpdating).toBe(null);
  expect(state.caloriesBMR).toEqual({ id: 1 });
});
test('success deleting a caloriesBMR', () => {
  const state = reducer(INITIAL_STATE, Actions.caloriesBMRDeleteSuccess());

  expect(state.deleting).toBe(false);
  expect(state.errorDeleting).toBe(null);
  expect(state.caloriesBMR).toEqual({ id: undefined });
});

test('failure retrieving a caloriesBMR', () => {
  const state = reducer(INITIAL_STATE, Actions.caloriesBMRFailure({ error: 'Not found' }));

  expect(state.fetchingOne).toBe(false);
  expect(state.errorOne).toEqual({ error: 'Not found' });
  expect(state.caloriesBMR).toEqual({ id: undefined });
});

test('failure retrieving a list of caloriesBMR', () => {
  const state = reducer(INITIAL_STATE, Actions.caloriesBMRAllFailure({ error: 'Not found' }));

  expect(state.fetchingAll).toBe(false);
  expect(state.errorAll).toEqual({ error: 'Not found' });
  expect(state.caloriesBMRList).toEqual([]);
});

test('failure updating a caloriesBMR', () => {
  const state = reducer(INITIAL_STATE, Actions.caloriesBMRUpdateFailure({ error: 'Not found' }));

  expect(state.updating).toBe(false);
  expect(state.errorUpdating).toEqual({ error: 'Not found' });
  expect(state.caloriesBMR).toEqual(INITIAL_STATE.caloriesBMR);
});
test('failure deleting a caloriesBMR', () => {
  const state = reducer(INITIAL_STATE, Actions.caloriesBMRDeleteFailure({ error: 'Not found' }));

  expect(state.deleting).toBe(false);
  expect(state.errorDeleting).toEqual({ error: 'Not found' });
  expect(state.caloriesBMR).toEqual(INITIAL_STATE.caloriesBMR);
});

test('resetting state for caloriesBMR', () => {
  const state = reducer({ ...INITIAL_STATE, deleting: true }, Actions.caloriesBMRReset());
  expect(state).toEqual(INITIAL_STATE);
});
