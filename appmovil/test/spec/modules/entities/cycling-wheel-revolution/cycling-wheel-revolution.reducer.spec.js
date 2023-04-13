import Actions, {
  reducer,
  INITIAL_STATE,
} from '../../../../../app/modules/entities/cycling-wheel-revolution/cycling-wheel-revolution.reducer';

test('attempt retrieving a single cyclingWheelRevolution', () => {
  const state = reducer(INITIAL_STATE, Actions.cyclingWheelRevolutionRequest({ id: 1 }));

  expect(state.fetchingOne).toBe(true);
  expect(state.cyclingWheelRevolution).toEqual({ id: undefined });
});

test('attempt retrieving a list of cyclingWheelRevolution', () => {
  const state = reducer(INITIAL_STATE, Actions.cyclingWheelRevolutionAllRequest({ id: 1 }));

  expect(state.fetchingAll).toBe(true);
  expect(state.cyclingWheelRevolutionList).toEqual([]);
});

test('attempt updating a cyclingWheelRevolution', () => {
  const state = reducer(INITIAL_STATE, Actions.cyclingWheelRevolutionUpdateRequest({ id: 1 }));

  expect(state.updating).toBe(true);
});
test('attempt to deleting a cyclingWheelRevolution', () => {
  const state = reducer(INITIAL_STATE, Actions.cyclingWheelRevolutionDeleteRequest({ id: 1 }));

  expect(state.deleting).toBe(true);
});

test('success retrieving a cyclingWheelRevolution', () => {
  const state = reducer(INITIAL_STATE, Actions.cyclingWheelRevolutionSuccess({ id: 1 }));

  expect(state.fetchingOne).toBe(false);
  expect(state.errorOne).toBe(null);
  expect(state.cyclingWheelRevolution).toEqual({ id: 1 });
});

test('success retrieving a list of cyclingWheelRevolution', () => {
  const state = reducer(
    INITIAL_STATE,
    Actions.cyclingWheelRevolutionAllSuccess([{ id: 1 }, { id: 2 }], {
      link: '</?page=1>; rel="last",</?page=0>; rel="first"',
      'x-total-count': 5,
    }),
  );

  expect(state.fetchingAll).toBe(false);
  expect(state.errorAll).toBe(null);
  expect(state.cyclingWheelRevolutionList).toEqual([{ id: 1 }, { id: 2 }]);
  expect(state.links).toEqual({ first: 0, last: 1 });
  expect(state.totalItems).toEqual(5);
});

test('success updating a cyclingWheelRevolution', () => {
  const state = reducer(INITIAL_STATE, Actions.cyclingWheelRevolutionUpdateSuccess({ id: 1 }));

  expect(state.updating).toBe(false);
  expect(state.errorUpdating).toBe(null);
  expect(state.cyclingWheelRevolution).toEqual({ id: 1 });
});
test('success deleting a cyclingWheelRevolution', () => {
  const state = reducer(INITIAL_STATE, Actions.cyclingWheelRevolutionDeleteSuccess());

  expect(state.deleting).toBe(false);
  expect(state.errorDeleting).toBe(null);
  expect(state.cyclingWheelRevolution).toEqual({ id: undefined });
});

test('failure retrieving a cyclingWheelRevolution', () => {
  const state = reducer(INITIAL_STATE, Actions.cyclingWheelRevolutionFailure({ error: 'Not found' }));

  expect(state.fetchingOne).toBe(false);
  expect(state.errorOne).toEqual({ error: 'Not found' });
  expect(state.cyclingWheelRevolution).toEqual({ id: undefined });
});

test('failure retrieving a list of cyclingWheelRevolution', () => {
  const state = reducer(INITIAL_STATE, Actions.cyclingWheelRevolutionAllFailure({ error: 'Not found' }));

  expect(state.fetchingAll).toBe(false);
  expect(state.errorAll).toEqual({ error: 'Not found' });
  expect(state.cyclingWheelRevolutionList).toEqual([]);
});

test('failure updating a cyclingWheelRevolution', () => {
  const state = reducer(INITIAL_STATE, Actions.cyclingWheelRevolutionUpdateFailure({ error: 'Not found' }));

  expect(state.updating).toBe(false);
  expect(state.errorUpdating).toEqual({ error: 'Not found' });
  expect(state.cyclingWheelRevolution).toEqual(INITIAL_STATE.cyclingWheelRevolution);
});
test('failure deleting a cyclingWheelRevolution', () => {
  const state = reducer(INITIAL_STATE, Actions.cyclingWheelRevolutionDeleteFailure({ error: 'Not found' }));

  expect(state.deleting).toBe(false);
  expect(state.errorDeleting).toEqual({ error: 'Not found' });
  expect(state.cyclingWheelRevolution).toEqual(INITIAL_STATE.cyclingWheelRevolution);
});

test('resetting state for cyclingWheelRevolution', () => {
  const state = reducer({ ...INITIAL_STATE, deleting: true }, Actions.cyclingWheelRevolutionReset());
  expect(state).toEqual(INITIAL_STATE);
});
