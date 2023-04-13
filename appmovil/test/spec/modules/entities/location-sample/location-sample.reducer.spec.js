import Actions, { reducer, INITIAL_STATE } from '../../../../../app/modules/entities/location-sample/location-sample.reducer';

test('attempt retrieving a single locationSample', () => {
  const state = reducer(INITIAL_STATE, Actions.locationSampleRequest({ id: 1 }));

  expect(state.fetchingOne).toBe(true);
  expect(state.locationSample).toEqual({ id: undefined });
});

test('attempt retrieving a list of locationSample', () => {
  const state = reducer(INITIAL_STATE, Actions.locationSampleAllRequest({ id: 1 }));

  expect(state.fetchingAll).toBe(true);
  expect(state.locationSampleList).toEqual([]);
});

test('attempt updating a locationSample', () => {
  const state = reducer(INITIAL_STATE, Actions.locationSampleUpdateRequest({ id: 1 }));

  expect(state.updating).toBe(true);
});
test('attempt to deleting a locationSample', () => {
  const state = reducer(INITIAL_STATE, Actions.locationSampleDeleteRequest({ id: 1 }));

  expect(state.deleting).toBe(true);
});

test('success retrieving a locationSample', () => {
  const state = reducer(INITIAL_STATE, Actions.locationSampleSuccess({ id: 1 }));

  expect(state.fetchingOne).toBe(false);
  expect(state.errorOne).toBe(null);
  expect(state.locationSample).toEqual({ id: 1 });
});

test('success retrieving a list of locationSample', () => {
  const state = reducer(
    INITIAL_STATE,
    Actions.locationSampleAllSuccess([{ id: 1 }, { id: 2 }], {
      link: '</?page=1>; rel="last",</?page=0>; rel="first"',
      'x-total-count': 5,
    }),
  );

  expect(state.fetchingAll).toBe(false);
  expect(state.errorAll).toBe(null);
  expect(state.locationSampleList).toEqual([{ id: 1 }, { id: 2 }]);
  expect(state.links).toEqual({ first: 0, last: 1 });
  expect(state.totalItems).toEqual(5);
});

test('success updating a locationSample', () => {
  const state = reducer(INITIAL_STATE, Actions.locationSampleUpdateSuccess({ id: 1 }));

  expect(state.updating).toBe(false);
  expect(state.errorUpdating).toBe(null);
  expect(state.locationSample).toEqual({ id: 1 });
});
test('success deleting a locationSample', () => {
  const state = reducer(INITIAL_STATE, Actions.locationSampleDeleteSuccess());

  expect(state.deleting).toBe(false);
  expect(state.errorDeleting).toBe(null);
  expect(state.locationSample).toEqual({ id: undefined });
});

test('failure retrieving a locationSample', () => {
  const state = reducer(INITIAL_STATE, Actions.locationSampleFailure({ error: 'Not found' }));

  expect(state.fetchingOne).toBe(false);
  expect(state.errorOne).toEqual({ error: 'Not found' });
  expect(state.locationSample).toEqual({ id: undefined });
});

test('failure retrieving a list of locationSample', () => {
  const state = reducer(INITIAL_STATE, Actions.locationSampleAllFailure({ error: 'Not found' }));

  expect(state.fetchingAll).toBe(false);
  expect(state.errorAll).toEqual({ error: 'Not found' });
  expect(state.locationSampleList).toEqual([]);
});

test('failure updating a locationSample', () => {
  const state = reducer(INITIAL_STATE, Actions.locationSampleUpdateFailure({ error: 'Not found' }));

  expect(state.updating).toBe(false);
  expect(state.errorUpdating).toEqual({ error: 'Not found' });
  expect(state.locationSample).toEqual(INITIAL_STATE.locationSample);
});
test('failure deleting a locationSample', () => {
  const state = reducer(INITIAL_STATE, Actions.locationSampleDeleteFailure({ error: 'Not found' }));

  expect(state.deleting).toBe(false);
  expect(state.errorDeleting).toEqual({ error: 'Not found' });
  expect(state.locationSample).toEqual(INITIAL_STATE.locationSample);
});

test('resetting state for locationSample', () => {
  const state = reducer({ ...INITIAL_STATE, deleting: true }, Actions.locationSampleReset());
  expect(state).toEqual(INITIAL_STATE);
});
