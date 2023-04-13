import Actions, { reducer, INITIAL_STATE } from '../../../../../app/modules/entities/sleep-segment/sleep-segment.reducer';

test('attempt retrieving a single sleepSegment', () => {
  const state = reducer(INITIAL_STATE, Actions.sleepSegmentRequest({ id: 1 }));

  expect(state.fetchingOne).toBe(true);
  expect(state.sleepSegment).toEqual({ id: undefined });
});

test('attempt retrieving a list of sleepSegment', () => {
  const state = reducer(INITIAL_STATE, Actions.sleepSegmentAllRequest({ id: 1 }));

  expect(state.fetchingAll).toBe(true);
  expect(state.sleepSegmentList).toEqual([]);
});

test('attempt updating a sleepSegment', () => {
  const state = reducer(INITIAL_STATE, Actions.sleepSegmentUpdateRequest({ id: 1 }));

  expect(state.updating).toBe(true);
});
test('attempt to deleting a sleepSegment', () => {
  const state = reducer(INITIAL_STATE, Actions.sleepSegmentDeleteRequest({ id: 1 }));

  expect(state.deleting).toBe(true);
});

test('success retrieving a sleepSegment', () => {
  const state = reducer(INITIAL_STATE, Actions.sleepSegmentSuccess({ id: 1 }));

  expect(state.fetchingOne).toBe(false);
  expect(state.errorOne).toBe(null);
  expect(state.sleepSegment).toEqual({ id: 1 });
});

test('success retrieving a list of sleepSegment', () => {
  const state = reducer(
    INITIAL_STATE,
    Actions.sleepSegmentAllSuccess([{ id: 1 }, { id: 2 }], { link: '</?page=1>; rel="last",</?page=0>; rel="first"', 'x-total-count': 5 }),
  );

  expect(state.fetchingAll).toBe(false);
  expect(state.errorAll).toBe(null);
  expect(state.sleepSegmentList).toEqual([{ id: 1 }, { id: 2 }]);
  expect(state.links).toEqual({ first: 0, last: 1 });
  expect(state.totalItems).toEqual(5);
});

test('success updating a sleepSegment', () => {
  const state = reducer(INITIAL_STATE, Actions.sleepSegmentUpdateSuccess({ id: 1 }));

  expect(state.updating).toBe(false);
  expect(state.errorUpdating).toBe(null);
  expect(state.sleepSegment).toEqual({ id: 1 });
});
test('success deleting a sleepSegment', () => {
  const state = reducer(INITIAL_STATE, Actions.sleepSegmentDeleteSuccess());

  expect(state.deleting).toBe(false);
  expect(state.errorDeleting).toBe(null);
  expect(state.sleepSegment).toEqual({ id: undefined });
});

test('failure retrieving a sleepSegment', () => {
  const state = reducer(INITIAL_STATE, Actions.sleepSegmentFailure({ error: 'Not found' }));

  expect(state.fetchingOne).toBe(false);
  expect(state.errorOne).toEqual({ error: 'Not found' });
  expect(state.sleepSegment).toEqual({ id: undefined });
});

test('failure retrieving a list of sleepSegment', () => {
  const state = reducer(INITIAL_STATE, Actions.sleepSegmentAllFailure({ error: 'Not found' }));

  expect(state.fetchingAll).toBe(false);
  expect(state.errorAll).toEqual({ error: 'Not found' });
  expect(state.sleepSegmentList).toEqual([]);
});

test('failure updating a sleepSegment', () => {
  const state = reducer(INITIAL_STATE, Actions.sleepSegmentUpdateFailure({ error: 'Not found' }));

  expect(state.updating).toBe(false);
  expect(state.errorUpdating).toEqual({ error: 'Not found' });
  expect(state.sleepSegment).toEqual(INITIAL_STATE.sleepSegment);
});
test('failure deleting a sleepSegment', () => {
  const state = reducer(INITIAL_STATE, Actions.sleepSegmentDeleteFailure({ error: 'Not found' }));

  expect(state.deleting).toBe(false);
  expect(state.errorDeleting).toEqual({ error: 'Not found' });
  expect(state.sleepSegment).toEqual(INITIAL_STATE.sleepSegment);
});

test('resetting state for sleepSegment', () => {
  const state = reducer({ ...INITIAL_STATE, deleting: true }, Actions.sleepSegmentReset());
  expect(state).toEqual(INITIAL_STATE);
});
