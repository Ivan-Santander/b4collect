import Actions, { reducer, INITIAL_STATE } from '../../../../../app/modules/entities/nutrition/nutrition.reducer';

test('attempt retrieving a single nutrition', () => {
  const state = reducer(INITIAL_STATE, Actions.nutritionRequest({ id: 1 }));

  expect(state.fetchingOne).toBe(true);
  expect(state.nutrition).toEqual({ id: undefined });
});

test('attempt retrieving a list of nutrition', () => {
  const state = reducer(INITIAL_STATE, Actions.nutritionAllRequest({ id: 1 }));

  expect(state.fetchingAll).toBe(true);
  expect(state.nutritionList).toEqual([]);
});

test('attempt updating a nutrition', () => {
  const state = reducer(INITIAL_STATE, Actions.nutritionUpdateRequest({ id: 1 }));

  expect(state.updating).toBe(true);
});
test('attempt to deleting a nutrition', () => {
  const state = reducer(INITIAL_STATE, Actions.nutritionDeleteRequest({ id: 1 }));

  expect(state.deleting).toBe(true);
});

test('success retrieving a nutrition', () => {
  const state = reducer(INITIAL_STATE, Actions.nutritionSuccess({ id: 1 }));

  expect(state.fetchingOne).toBe(false);
  expect(state.errorOne).toBe(null);
  expect(state.nutrition).toEqual({ id: 1 });
});

test('success retrieving a list of nutrition', () => {
  const state = reducer(
    INITIAL_STATE,
    Actions.nutritionAllSuccess([{ id: 1 }, { id: 2 }], { link: '</?page=1>; rel="last",</?page=0>; rel="first"', 'x-total-count': 5 }),
  );

  expect(state.fetchingAll).toBe(false);
  expect(state.errorAll).toBe(null);
  expect(state.nutritionList).toEqual([{ id: 1 }, { id: 2 }]);
  expect(state.links).toEqual({ first: 0, last: 1 });
  expect(state.totalItems).toEqual(5);
});

test('success updating a nutrition', () => {
  const state = reducer(INITIAL_STATE, Actions.nutritionUpdateSuccess({ id: 1 }));

  expect(state.updating).toBe(false);
  expect(state.errorUpdating).toBe(null);
  expect(state.nutrition).toEqual({ id: 1 });
});
test('success deleting a nutrition', () => {
  const state = reducer(INITIAL_STATE, Actions.nutritionDeleteSuccess());

  expect(state.deleting).toBe(false);
  expect(state.errorDeleting).toBe(null);
  expect(state.nutrition).toEqual({ id: undefined });
});

test('failure retrieving a nutrition', () => {
  const state = reducer(INITIAL_STATE, Actions.nutritionFailure({ error: 'Not found' }));

  expect(state.fetchingOne).toBe(false);
  expect(state.errorOne).toEqual({ error: 'Not found' });
  expect(state.nutrition).toEqual({ id: undefined });
});

test('failure retrieving a list of nutrition', () => {
  const state = reducer(INITIAL_STATE, Actions.nutritionAllFailure({ error: 'Not found' }));

  expect(state.fetchingAll).toBe(false);
  expect(state.errorAll).toEqual({ error: 'Not found' });
  expect(state.nutritionList).toEqual([]);
});

test('failure updating a nutrition', () => {
  const state = reducer(INITIAL_STATE, Actions.nutritionUpdateFailure({ error: 'Not found' }));

  expect(state.updating).toBe(false);
  expect(state.errorUpdating).toEqual({ error: 'Not found' });
  expect(state.nutrition).toEqual(INITIAL_STATE.nutrition);
});
test('failure deleting a nutrition', () => {
  const state = reducer(INITIAL_STATE, Actions.nutritionDeleteFailure({ error: 'Not found' }));

  expect(state.deleting).toBe(false);
  expect(state.errorDeleting).toEqual({ error: 'Not found' });
  expect(state.nutrition).toEqual(INITIAL_STATE.nutrition);
});

test('resetting state for nutrition', () => {
  const state = reducer({ ...INITIAL_STATE, deleting: true }, Actions.nutritionReset());
  expect(state).toEqual(INITIAL_STATE);
});
