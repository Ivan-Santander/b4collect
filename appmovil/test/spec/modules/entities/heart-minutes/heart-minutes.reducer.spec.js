import Actions, { reducer, INITIAL_STATE } from '../../../../../app/modules/entities/heart-minutes/heart-minutes.reducer';

test('attempt retrieving a single heartMinutes', () => {
  const state = reducer(INITIAL_STATE, Actions.heartMinutesRequest({ id: 1 }));

  expect(state.fetchingOne).toBe(true);
  expect(state.heartMinutes).toEqual({ id: undefined });
});

test('attempt retrieving a list of heartMinutes', () => {
  const state = reducer(INITIAL_STATE, Actions.heartMinutesAllRequest({ id: 1 }));

  expect(state.fetchingAll).toBe(true);
  expect(state.heartMinutesList).toEqual([]);
});

test('attempt updating a heartMinutes', () => {
  const state = reducer(INITIAL_STATE, Actions.heartMinutesUpdateRequest({ id: 1 }));

  expect(state.updating).toBe(true);
});
test('attempt to deleting a heartMinutes', () => {
  const state = reducer(INITIAL_STATE, Actions.heartMinutesDeleteRequest({ id: 1 }));

  expect(state.deleting).toBe(true);
});

test('success retrieving a heartMinutes', () => {
  const state = reducer(INITIAL_STATE, Actions.heartMinutesSuccess({ id: 1 }));

  expect(state.fetchingOne).toBe(false);
  expect(state.errorOne).toBe(null);
  expect(state.heartMinutes).toEqual({ id: 1 });
});

test('success retrieving a list of heartMinutes', () => {
  const state = reducer(
    INITIAL_STATE,
    Actions.heartMinutesAllSuccess([{ id: 1 }, { id: 2 }], { link: '</?page=1>; rel="last",</?page=0>; rel="first"', 'x-total-count': 5 }),
  );

  expect(state.fetchingAll).toBe(false);
  expect(state.errorAll).toBe(null);
  expect(state.heartMinutesList).toEqual([{ id: 1 }, { id: 2 }]);
  expect(state.links).toEqual({ first: 0, last: 1 });
  expect(state.totalItems).toEqual(5);
});

test('success updating a heartMinutes', () => {
  const state = reducer(INITIAL_STATE, Actions.heartMinutesUpdateSuccess({ id: 1 }));

  expect(state.updating).toBe(false);
  expect(state.errorUpdating).toBe(null);
  expect(state.heartMinutes).toEqual({ id: 1 });
});
test('success deleting a heartMinutes', () => {
  const state = reducer(INITIAL_STATE, Actions.heartMinutesDeleteSuccess());

  expect(state.deleting).toBe(false);
  expect(state.errorDeleting).toBe(null);
  expect(state.heartMinutes).toEqual({ id: undefined });
});

test('failure retrieving a heartMinutes', () => {
  const state = reducer(INITIAL_STATE, Actions.heartMinutesFailure({ error: 'Not found' }));

  expect(state.fetchingOne).toBe(false);
  expect(state.errorOne).toEqual({ error: 'Not found' });
  expect(state.heartMinutes).toEqual({ id: undefined });
});

test('failure retrieving a list of heartMinutes', () => {
  const state = reducer(INITIAL_STATE, Actions.heartMinutesAllFailure({ error: 'Not found' }));

  expect(state.fetchingAll).toBe(false);
  expect(state.errorAll).toEqual({ error: 'Not found' });
  expect(state.heartMinutesList).toEqual([]);
});

test('failure updating a heartMinutes', () => {
  const state = reducer(INITIAL_STATE, Actions.heartMinutesUpdateFailure({ error: 'Not found' }));

  expect(state.updating).toBe(false);
  expect(state.errorUpdating).toEqual({ error: 'Not found' });
  expect(state.heartMinutes).toEqual(INITIAL_STATE.heartMinutes);
});
test('failure deleting a heartMinutes', () => {
  const state = reducer(INITIAL_STATE, Actions.heartMinutesDeleteFailure({ error: 'Not found' }));

  expect(state.deleting).toBe(false);
  expect(state.errorDeleting).toEqual({ error: 'Not found' });
  expect(state.heartMinutes).toEqual(INITIAL_STATE.heartMinutes);
});

test('resetting state for heartMinutes', () => {
  const state = reducer({ ...INITIAL_STATE, deleting: true }, Actions.heartMinutesReset());
  expect(state).toEqual(INITIAL_STATE);
});
