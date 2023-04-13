import Actions, { reducer, INITIAL_STATE } from '../../../../../app/modules/entities/active-minutes/active-minutes.reducer';

test('attempt retrieving a single activeMinutes', () => {
  const state = reducer(INITIAL_STATE, Actions.activeMinutesRequest({ id: 1 }));

  expect(state.fetchingOne).toBe(true);
  expect(state.activeMinutes).toEqual({ id: undefined });
});

test('attempt retrieving a list of activeMinutes', () => {
  const state = reducer(INITIAL_STATE, Actions.activeMinutesAllRequest({ id: 1 }));

  expect(state.fetchingAll).toBe(true);
  expect(state.activeMinutesList).toEqual([]);
});

test('attempt updating a activeMinutes', () => {
  const state = reducer(INITIAL_STATE, Actions.activeMinutesUpdateRequest({ id: 1 }));

  expect(state.updating).toBe(true);
});
test('attempt to deleting a activeMinutes', () => {
  const state = reducer(INITIAL_STATE, Actions.activeMinutesDeleteRequest({ id: 1 }));

  expect(state.deleting).toBe(true);
});

test('success retrieving a activeMinutes', () => {
  const state = reducer(INITIAL_STATE, Actions.activeMinutesSuccess({ id: 1 }));

  expect(state.fetchingOne).toBe(false);
  expect(state.errorOne).toBe(null);
  expect(state.activeMinutes).toEqual({ id: 1 });
});

test('success retrieving a list of activeMinutes', () => {
  const state = reducer(
    INITIAL_STATE,
    Actions.activeMinutesAllSuccess([{ id: 1 }, { id: 2 }], { link: '</?page=1>; rel="last",</?page=0>; rel="first"', 'x-total-count': 5 }),
  );

  expect(state.fetchingAll).toBe(false);
  expect(state.errorAll).toBe(null);
  expect(state.activeMinutesList).toEqual([{ id: 1 }, { id: 2 }]);
  expect(state.links).toEqual({ first: 0, last: 1 });
  expect(state.totalItems).toEqual(5);
});

test('success updating a activeMinutes', () => {
  const state = reducer(INITIAL_STATE, Actions.activeMinutesUpdateSuccess({ id: 1 }));

  expect(state.updating).toBe(false);
  expect(state.errorUpdating).toBe(null);
  expect(state.activeMinutes).toEqual({ id: 1 });
});
test('success deleting a activeMinutes', () => {
  const state = reducer(INITIAL_STATE, Actions.activeMinutesDeleteSuccess());

  expect(state.deleting).toBe(false);
  expect(state.errorDeleting).toBe(null);
  expect(state.activeMinutes).toEqual({ id: undefined });
});

test('failure retrieving a activeMinutes', () => {
  const state = reducer(INITIAL_STATE, Actions.activeMinutesFailure({ error: 'Not found' }));

  expect(state.fetchingOne).toBe(false);
  expect(state.errorOne).toEqual({ error: 'Not found' });
  expect(state.activeMinutes).toEqual({ id: undefined });
});

test('failure retrieving a list of activeMinutes', () => {
  const state = reducer(INITIAL_STATE, Actions.activeMinutesAllFailure({ error: 'Not found' }));

  expect(state.fetchingAll).toBe(false);
  expect(state.errorAll).toEqual({ error: 'Not found' });
  expect(state.activeMinutesList).toEqual([]);
});

test('failure updating a activeMinutes', () => {
  const state = reducer(INITIAL_STATE, Actions.activeMinutesUpdateFailure({ error: 'Not found' }));

  expect(state.updating).toBe(false);
  expect(state.errorUpdating).toEqual({ error: 'Not found' });
  expect(state.activeMinutes).toEqual(INITIAL_STATE.activeMinutes);
});
test('failure deleting a activeMinutes', () => {
  const state = reducer(INITIAL_STATE, Actions.activeMinutesDeleteFailure({ error: 'Not found' }));

  expect(state.deleting).toBe(false);
  expect(state.errorDeleting).toEqual({ error: 'Not found' });
  expect(state.activeMinutes).toEqual(INITIAL_STATE.activeMinutes);
});

test('resetting state for activeMinutes', () => {
  const state = reducer({ ...INITIAL_STATE, deleting: true }, Actions.activeMinutesReset());
  expect(state).toEqual(INITIAL_STATE);
});
