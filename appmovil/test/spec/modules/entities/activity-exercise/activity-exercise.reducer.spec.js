import Actions, { reducer, INITIAL_STATE } from '../../../../../app/modules/entities/activity-exercise/activity-exercise.reducer';

test('attempt retrieving a single activityExercise', () => {
  const state = reducer(INITIAL_STATE, Actions.activityExerciseRequest({ id: 1 }));

  expect(state.fetchingOne).toBe(true);
  expect(state.activityExercise).toEqual({ id: undefined });
});

test('attempt retrieving a list of activityExercise', () => {
  const state = reducer(INITIAL_STATE, Actions.activityExerciseAllRequest({ id: 1 }));

  expect(state.fetchingAll).toBe(true);
  expect(state.activityExerciseList).toEqual([]);
});

test('attempt updating a activityExercise', () => {
  const state = reducer(INITIAL_STATE, Actions.activityExerciseUpdateRequest({ id: 1 }));

  expect(state.updating).toBe(true);
});
test('attempt to deleting a activityExercise', () => {
  const state = reducer(INITIAL_STATE, Actions.activityExerciseDeleteRequest({ id: 1 }));

  expect(state.deleting).toBe(true);
});

test('success retrieving a activityExercise', () => {
  const state = reducer(INITIAL_STATE, Actions.activityExerciseSuccess({ id: 1 }));

  expect(state.fetchingOne).toBe(false);
  expect(state.errorOne).toBe(null);
  expect(state.activityExercise).toEqual({ id: 1 });
});

test('success retrieving a list of activityExercise', () => {
  const state = reducer(
    INITIAL_STATE,
    Actions.activityExerciseAllSuccess([{ id: 1 }, { id: 2 }], {
      link: '</?page=1>; rel="last",</?page=0>; rel="first"',
      'x-total-count': 5,
    }),
  );

  expect(state.fetchingAll).toBe(false);
  expect(state.errorAll).toBe(null);
  expect(state.activityExerciseList).toEqual([{ id: 1 }, { id: 2 }]);
  expect(state.links).toEqual({ first: 0, last: 1 });
  expect(state.totalItems).toEqual(5);
});

test('success updating a activityExercise', () => {
  const state = reducer(INITIAL_STATE, Actions.activityExerciseUpdateSuccess({ id: 1 }));

  expect(state.updating).toBe(false);
  expect(state.errorUpdating).toBe(null);
  expect(state.activityExercise).toEqual({ id: 1 });
});
test('success deleting a activityExercise', () => {
  const state = reducer(INITIAL_STATE, Actions.activityExerciseDeleteSuccess());

  expect(state.deleting).toBe(false);
  expect(state.errorDeleting).toBe(null);
  expect(state.activityExercise).toEqual({ id: undefined });
});

test('failure retrieving a activityExercise', () => {
  const state = reducer(INITIAL_STATE, Actions.activityExerciseFailure({ error: 'Not found' }));

  expect(state.fetchingOne).toBe(false);
  expect(state.errorOne).toEqual({ error: 'Not found' });
  expect(state.activityExercise).toEqual({ id: undefined });
});

test('failure retrieving a list of activityExercise', () => {
  const state = reducer(INITIAL_STATE, Actions.activityExerciseAllFailure({ error: 'Not found' }));

  expect(state.fetchingAll).toBe(false);
  expect(state.errorAll).toEqual({ error: 'Not found' });
  expect(state.activityExerciseList).toEqual([]);
});

test('failure updating a activityExercise', () => {
  const state = reducer(INITIAL_STATE, Actions.activityExerciseUpdateFailure({ error: 'Not found' }));

  expect(state.updating).toBe(false);
  expect(state.errorUpdating).toEqual({ error: 'Not found' });
  expect(state.activityExercise).toEqual(INITIAL_STATE.activityExercise);
});
test('failure deleting a activityExercise', () => {
  const state = reducer(INITIAL_STATE, Actions.activityExerciseDeleteFailure({ error: 'Not found' }));

  expect(state.deleting).toBe(false);
  expect(state.errorDeleting).toEqual({ error: 'Not found' });
  expect(state.activityExercise).toEqual(INITIAL_STATE.activityExercise);
});

test('resetting state for activityExercise', () => {
  const state = reducer({ ...INITIAL_STATE, deleting: true }, Actions.activityExerciseReset());
  expect(state).toEqual(INITIAL_STATE);
});
