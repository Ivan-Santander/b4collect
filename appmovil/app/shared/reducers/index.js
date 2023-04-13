import { combineReducers } from 'redux';
import { persistReducer } from 'redux-persist';

import configureStore from './create-store';
import rootSaga from '../sagas';
import ReduxPersist from '../../config/redux-persist';

/* ------------- Assemble The Reducers ------------- */
export const reducers = combineReducers({
  appState: require('./app-state.reducer').reducer,
  users: require('./user.reducer').reducer,
  caloriesBMRS: require('../../modules/entities/calories-bmr/calories-bmr.reducer').reducer,
  caloriesExpendeds: require('../../modules/entities/calories-expended/calories-expended.reducer').reducer,
  ciclingPedalingCadences: require('../../modules/entities/cicling-pedaling-cadence/cicling-pedaling-cadence.reducer').reducer,
  heartMinutes: require('../../modules/entities/heart-minutes/heart-minutes.reducer').reducer,
  activeMinutes: require('../../modules/entities/active-minutes/active-minutes.reducer').reducer,
  poweSamples: require('../../modules/entities/powe-sample/powe-sample.reducer').reducer,
  stepCountCadences: require('../../modules/entities/step-count-cadence/step-count-cadence.reducer').reducer,
  stepCountDeltas: require('../../modules/entities/step-count-delta/step-count-delta.reducer').reducer,
  activityExercises: require('../../modules/entities/activity-exercise/activity-exercise.reducer').reducer,
  cyclingWheelRevolutions: require('../../modules/entities/cycling-wheel-revolution/cycling-wheel-revolution.reducer').reducer,
  distanceDeltas: require('../../modules/entities/distance-delta/distance-delta.reducer').reducer,
  locationSamples: require('../../modules/entities/location-sample/location-sample.reducer').reducer,
  speeds: require('../../modules/entities/speed/speed.reducer').reducer,
  nutritions: require('../../modules/entities/nutrition/nutrition.reducer').reducer,
  bloodGlucoses: require('../../modules/entities/blood-glucose/blood-glucose.reducer').reducer,
  bloodPressures: require('../../modules/entities/blood-pressure/blood-pressure.reducer').reducer,
  bodyFatPercentages: require('../../modules/entities/body-fat-percentage/body-fat-percentage.reducer').reducer,
  bodyTemperatures: require('../../modules/entities/body-temperature/body-temperature.reducer').reducer,
  heartRateBpms: require('../../modules/entities/heart-rate-bpm/heart-rate-bpm.reducer').reducer,
  heights: require('../../modules/entities/height/height.reducer').reducer,
  oxygenSaturations: require('../../modules/entities/oxygen-saturation/oxygen-saturation.reducer').reducer,
  sleepSegments: require('../../modules/entities/sleep-segment/sleep-segment.reducer').reducer,
  weights: require('../../modules/entities/weight/weight.reducer').reducer,
  activitySummaries: require('../../modules/entities/activity-summary/activity-summary.reducer').reducer,
  caloriesBmrSummaries: require('../../modules/entities/calories-bmr-summary/calories-bmr-summary.reducer').reducer,
  powerSummaries: require('../../modules/entities/power-summary/power-summary.reducer').reducer,
  bodyFatPercentageSummaries: require('../../modules/entities/body-fat-percentage-summary/body-fat-percentage-summary.reducer').reducer,
  heartRateSummaries: require('../../modules/entities/heart-rate-summary/heart-rate-summary.reducer').reducer,
  heightSummaries: require('../../modules/entities/height-summary/height-summary.reducer').reducer,
  weightSummaries: require('../../modules/entities/weight-summary/weight-summary.reducer').reducer,
  speedSummaries: require('../../modules/entities/speed-summary/speed-summary.reducer').reducer,
  nutritionSummaries: require('../../modules/entities/nutrition-summary/nutrition-summary.reducer').reducer,
  bloodGlucoseSummaries: require('../../modules/entities/blood-glucose-summary/blood-glucose-summary.reducer').reducer,
  bloodPressureSummaries: require('../../modules/entities/blood-pressure-summary/blood-pressure-summary.reducer').reducer,
  temperatureSummaries: require('../../modules/entities/temperature-summary/temperature-summary.reducer').reducer,
  oxygenSaturationSummaries: require('../../modules/entities/oxygen-saturation-summary/oxygen-saturation-summary.reducer').reducer,
  // jhipster-react-native-redux-store-import-needle
  account: require('./account.reducer').reducer,
  login: require('../../modules/login/login.reducer').reducer,
  register: require('../../modules/account/register/register.reducer').reducer,
  changePassword: require('../../modules/account/password/change-password.reducer').reducer,
  forgotPassword: require('../../modules/account/password-reset/forgot-password.reducer').reducer,
});

export default () => {
  let finalReducers = reducers;
  // If rehydration is on use persistReducer otherwise default combineReducers
  if (ReduxPersist.active) {
    const persistConfig = ReduxPersist.storeConfig;
    finalReducers = persistReducer(persistConfig, reducers);
  }

  let { store, sagasManager, sagaMiddleware } = configureStore(finalReducers, rootSaga);

  if (module.hot) {
    module.hot.accept(() => {
      const nextRootReducer = require('./index').reducers;
      store.replaceReducer(nextRootReducer);

      const newYieldedSagas = require('../sagas').default;
      sagasManager.cancel();
      sagasManager.done.then(() => {
        sagasManager = sagaMiddleware.run(newYieldedSagas);
      });
    });
  }

  return store;
};
