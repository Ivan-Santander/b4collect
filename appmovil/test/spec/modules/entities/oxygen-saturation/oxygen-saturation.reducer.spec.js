import Actions, { reducer, INITIAL_STATE } from '../../../../../app/modules/entities/oxygen-saturation/oxygen-saturation.reducer';

test('attempt retrieving a single oxygenSaturation', () => {
  const state = reducer(INITIAL_STATE, Actions.oxygenSaturationRequest({ id: 1 }));

  expect(state.fetchingOne).toBe(true);
  expect(state.oxygenSaturation).toEqual({ id: undefined });
});

test('attempt retrieving a list of oxygenSaturation', () => {
  const state = reducer(INITIAL_STATE, Actions.oxygenSaturationAllRequest({ id: 1 }));

  expect(state.fetchingAll).toBe(true);
  expect(state.oxygenSaturationList).toEqual([]);
});

test('attempt updating a oxygenSaturation', () => {
  const state = reducer(INITIAL_STATE, Actions.oxygenSaturationUpdateRequest({ id: 1 }));

  expect(state.updating).toBe(true);
});
test('attempt to deleting a oxygenSaturation', () => {
  const state = reducer(INITIAL_STATE, Actions.oxygenSaturationDeleteRequest({ id: 1 }));

  expect(state.deleting).toBe(true);
});

test('success retrieving a oxygenSaturation', () => {
  const state = reducer(INITIAL_STATE, Actions.oxygenSaturationSuccess({ id: 1 }));

  expect(state.fetchingOne).toBe(false);
  expect(state.errorOne).toBe(null);
  expect(state.oxygenSaturation).toEqual({ id: 1 });
});

test('success retrieving a list of oxygenSaturation', () => {
  const state = reducer(
    INITIAL_STATE,
    Actions.oxygenSaturationAllSuccess([{ id: 1 }, { id: 2 }], {
      link: '</?page=1>; rel="last",</?page=0>; rel="first"',
      'x-total-count': 5,
    }),
  );

  expect(state.fetchingAll).toBe(false);
  expect(state.errorAll).toBe(null);
  expect(state.oxygenSaturationList).toEqual([{ id: 1 }, { id: 2 }]);
  expect(state.links).toEqual({ first: 0, last: 1 });
  expect(state.totalItems).toEqual(5);
});

test('success updating a oxygenSaturation', () => {
  const state = reducer(INITIAL_STATE, Actions.oxygenSaturationUpdateSuccess({ id: 1 }));

  expect(state.updating).toBe(false);
  expect(state.errorUpdating).toBe(null);
  expect(state.oxygenSaturation).toEqual({ id: 1 });
});
test('success deleting a oxygenSaturation', () => {
  const state = reducer(INITIAL_STATE, Actions.oxygenSaturationDeleteSuccess());

  expect(state.deleting).toBe(false);
  expect(state.errorDeleting).toBe(null);
  expect(state.oxygenSaturation).toEqual({ id: undefined });
});

test('failure retrieving a oxygenSaturation', () => {
  const state = reducer(INITIAL_STATE, Actions.oxygenSaturationFailure({ error: 'Not found' }));

  expect(state.fetchingOne).toBe(false);
  expect(state.errorOne).toEqual({ error: 'Not found' });
  expect(state.oxygenSaturation).toEqual({ id: undefined });
});

test('failure retrieving a list of oxygenSaturation', () => {
  const state = reducer(INITIAL_STATE, Actions.oxygenSaturationAllFailure({ error: 'Not found' }));

  expect(state.fetchingAll).toBe(false);
  expect(state.errorAll).toEqual({ error: 'Not found' });
  expect(state.oxygenSaturationList).toEqual([]);
});

test('failure updating a oxygenSaturation', () => {
  const state = reducer(INITIAL_STATE, Actions.oxygenSaturationUpdateFailure({ error: 'Not found' }));

  expect(state.updating).toBe(false);
  expect(state.errorUpdating).toEqual({ error: 'Not found' });
  expect(state.oxygenSaturation).toEqual(INITIAL_STATE.oxygenSaturation);
});
test('failure deleting a oxygenSaturation', () => {
  const state = reducer(INITIAL_STATE, Actions.oxygenSaturationDeleteFailure({ error: 'Not found' }));

  expect(state.deleting).toBe(false);
  expect(state.errorDeleting).toEqual({ error: 'Not found' });
  expect(state.oxygenSaturation).toEqual(INITIAL_STATE.oxygenSaturation);
});

test('resetting state for oxygenSaturation', () => {
  const state = reducer({ ...INITIAL_STATE, deleting: true }, Actions.oxygenSaturationReset());
  expect(state).toEqual(INITIAL_STATE);
});
