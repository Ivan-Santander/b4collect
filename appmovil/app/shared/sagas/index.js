import { takeLatest, all } from 'redux-saga/effects';
import API from '../services/api';
import FixtureAPI from '../services/fixture-api';
import AppConfig from '../../config/app-config';

/* ------------- Types ------------- */

import { StartupTypes } from '../reducers/startup.reducer';
import { LoginTypes } from '../../modules/login/login.reducer';
import { AccountTypes } from '../../shared/reducers/account.reducer';
import { RegisterTypes } from '../../modules/account/register/register.reducer';
import { ForgotPasswordTypes } from '../../modules/account/password-reset/forgot-password.reducer';
import { ChangePasswordTypes } from '../../modules/account/password/change-password.reducer';
import { UserTypes } from '../../shared/reducers/user.reducer';
import { CaloriesBMRTypes } from '../../modules/entities/calories-bmr/calories-bmr.reducer';
import { CaloriesExpendedTypes } from '../../modules/entities/calories-expended/calories-expended.reducer';
import { CiclingPedalingCadenceTypes } from '../../modules/entities/cicling-pedaling-cadence/cicling-pedaling-cadence.reducer';
import { HeartMinutesTypes } from '../../modules/entities/heart-minutes/heart-minutes.reducer';
import { ActiveMinutesTypes } from '../../modules/entities/active-minutes/active-minutes.reducer';
import { PoweSampleTypes } from '../../modules/entities/powe-sample/powe-sample.reducer';
import { StepCountCadenceTypes } from '../../modules/entities/step-count-cadence/step-count-cadence.reducer';
import { StepCountDeltaTypes } from '../../modules/entities/step-count-delta/step-count-delta.reducer';
import { ActivityExerciseTypes } from '../../modules/entities/activity-exercise/activity-exercise.reducer';
import { CyclingWheelRevolutionTypes } from '../../modules/entities/cycling-wheel-revolution/cycling-wheel-revolution.reducer';
import { DistanceDeltaTypes } from '../../modules/entities/distance-delta/distance-delta.reducer';
import { LocationSampleTypes } from '../../modules/entities/location-sample/location-sample.reducer';
import { SpeedTypes } from '../../modules/entities/speed/speed.reducer';
import { NutritionTypes } from '../../modules/entities/nutrition/nutrition.reducer';
import { BloodGlucoseTypes } from '../../modules/entities/blood-glucose/blood-glucose.reducer';
import { BloodPressureTypes } from '../../modules/entities/blood-pressure/blood-pressure.reducer';
import { BodyFatPercentageTypes } from '../../modules/entities/body-fat-percentage/body-fat-percentage.reducer';
import { BodyTemperatureTypes } from '../../modules/entities/body-temperature/body-temperature.reducer';
import { HeartRateBpmTypes } from '../../modules/entities/heart-rate-bpm/heart-rate-bpm.reducer';
import { HeightTypes } from '../../modules/entities/height/height.reducer';
import { OxygenSaturationTypes } from '../../modules/entities/oxygen-saturation/oxygen-saturation.reducer';
import { SleepSegmentTypes } from '../../modules/entities/sleep-segment/sleep-segment.reducer';
import { WeightTypes } from '../../modules/entities/weight/weight.reducer';
import { ActivitySummaryTypes } from '../../modules/entities/activity-summary/activity-summary.reducer';
import { CaloriesBmrSummaryTypes } from '../../modules/entities/calories-bmr-summary/calories-bmr-summary.reducer';
import { PowerSummaryTypes } from '../../modules/entities/power-summary/power-summary.reducer';
import { BodyFatPercentageSummaryTypes } from '../../modules/entities/body-fat-percentage-summary/body-fat-percentage-summary.reducer';
import { HeartRateSummaryTypes } from '../../modules/entities/heart-rate-summary/heart-rate-summary.reducer';
import { HeightSummaryTypes } from '../../modules/entities/height-summary/height-summary.reducer';
import { WeightSummaryTypes } from '../../modules/entities/weight-summary/weight-summary.reducer';
import { SpeedSummaryTypes } from '../../modules/entities/speed-summary/speed-summary.reducer';
import { NutritionSummaryTypes } from '../../modules/entities/nutrition-summary/nutrition-summary.reducer';
import { BloodGlucoseSummaryTypes } from '../../modules/entities/blood-glucose-summary/blood-glucose-summary.reducer';
import { BloodPressureSummaryTypes } from '../../modules/entities/blood-pressure-summary/blood-pressure-summary.reducer';
import { TemperatureSummaryTypes } from '../../modules/entities/temperature-summary/temperature-summary.reducer';
import { OxygenSaturationSummaryTypes } from '../../modules/entities/oxygen-saturation-summary/oxygen-saturation-summary.reducer';
// jhipster-react-native-saga-redux-import-needle

/* ------------- Sagas ------------- */

import { startup } from './startup.saga';
import { login, logout, loginLoad } from '../../modules/login/login.sagas';
import { register } from '../../modules/account/register/register.sagas';
import { forgotPassword } from '../../modules/account/password-reset/forgot-password.sagas';
import { changePassword } from '../../modules/account/password/change-password.sagas';
import { getAccount, updateAccount } from '../../shared/sagas/account.sagas';
import UserSagas from '../../shared/sagas/user.sagas';
import CaloriesBMRSagas from '../../modules/entities/calories-bmr/calories-bmr.sagas';
import CaloriesExpendedSagas from '../../modules/entities/calories-expended/calories-expended.sagas';
import CiclingPedalingCadenceSagas from '../../modules/entities/cicling-pedaling-cadence/cicling-pedaling-cadence.sagas';
import HeartMinutesSagas from '../../modules/entities/heart-minutes/heart-minutes.sagas';
import ActiveMinutesSagas from '../../modules/entities/active-minutes/active-minutes.sagas';
import PoweSampleSagas from '../../modules/entities/powe-sample/powe-sample.sagas';
import StepCountCadenceSagas from '../../modules/entities/step-count-cadence/step-count-cadence.sagas';
import StepCountDeltaSagas from '../../modules/entities/step-count-delta/step-count-delta.sagas';
import ActivityExerciseSagas from '../../modules/entities/activity-exercise/activity-exercise.sagas';
import CyclingWheelRevolutionSagas from '../../modules/entities/cycling-wheel-revolution/cycling-wheel-revolution.sagas';
import DistanceDeltaSagas from '../../modules/entities/distance-delta/distance-delta.sagas';
import LocationSampleSagas from '../../modules/entities/location-sample/location-sample.sagas';
import SpeedSagas from '../../modules/entities/speed/speed.sagas';
import NutritionSagas from '../../modules/entities/nutrition/nutrition.sagas';
import BloodGlucoseSagas from '../../modules/entities/blood-glucose/blood-glucose.sagas';
import BloodPressureSagas from '../../modules/entities/blood-pressure/blood-pressure.sagas';
import BodyFatPercentageSagas from '../../modules/entities/body-fat-percentage/body-fat-percentage.sagas';
import BodyTemperatureSagas from '../../modules/entities/body-temperature/body-temperature.sagas';
import HeartRateBpmSagas from '../../modules/entities/heart-rate-bpm/heart-rate-bpm.sagas';
import HeightSagas from '../../modules/entities/height/height.sagas';
import OxygenSaturationSagas from '../../modules/entities/oxygen-saturation/oxygen-saturation.sagas';
import SleepSegmentSagas from '../../modules/entities/sleep-segment/sleep-segment.sagas';
import WeightSagas from '../../modules/entities/weight/weight.sagas';
import ActivitySummarySagas from '../../modules/entities/activity-summary/activity-summary.sagas';
import CaloriesBmrSummarySagas from '../../modules/entities/calories-bmr-summary/calories-bmr-summary.sagas';
import PowerSummarySagas from '../../modules/entities/power-summary/power-summary.sagas';
import BodyFatPercentageSummarySagas from '../../modules/entities/body-fat-percentage-summary/body-fat-percentage-summary.sagas';
import HeartRateSummarySagas from '../../modules/entities/heart-rate-summary/heart-rate-summary.sagas';
import HeightSummarySagas from '../../modules/entities/height-summary/height-summary.sagas';
import WeightSummarySagas from '../../modules/entities/weight-summary/weight-summary.sagas';
import SpeedSummarySagas from '../../modules/entities/speed-summary/speed-summary.sagas';
import NutritionSummarySagas from '../../modules/entities/nutrition-summary/nutrition-summary.sagas';
import BloodGlucoseSummarySagas from '../../modules/entities/blood-glucose-summary/blood-glucose-summary.sagas';
import BloodPressureSummarySagas from '../../modules/entities/blood-pressure-summary/blood-pressure-summary.sagas';
import TemperatureSummarySagas from '../../modules/entities/temperature-summary/temperature-summary.sagas';
import OxygenSaturationSummarySagas from '../../modules/entities/oxygen-saturation-summary/oxygen-saturation-summary.sagas';
// jhipster-react-native-saga-method-import-needle

/* ------------- API ------------- */

// The API we use is only used from Sagas, so we create it here and pass along
// to the sagas which need it.
const api = AppConfig.useFixtures ? FixtureAPI : API.create();

/* ------------- Connect Types To Sagas ------------- */

export default function* root() {
  yield all([
    // some sagas only receive an action
    takeLatest(StartupTypes.STARTUP, startup),

    // JHipster accounts
    takeLatest(LoginTypes.LOGIN_LOAD, loginLoad, api),
    takeLatest(LoginTypes.LOGIN_REQUEST, login, api),
    takeLatest(LoginTypes.LOGOUT_REQUEST, logout, api),

    takeLatest(CaloriesBMRTypes.CALORIES_BMR_REQUEST, CaloriesBMRSagas.getCaloriesBMR, api),
    takeLatest(CaloriesBMRTypes.CALORIES_BMR_ALL_REQUEST, CaloriesBMRSagas.getAllCaloriesBMRS, api),
    takeLatest(CaloriesBMRTypes.CALORIES_BMR_UPDATE_REQUEST, CaloriesBMRSagas.updateCaloriesBMR, api),
    takeLatest(CaloriesBMRTypes.CALORIES_BMR_DELETE_REQUEST, CaloriesBMRSagas.deleteCaloriesBMR, api),

    takeLatest(CaloriesExpendedTypes.CALORIES_EXPENDED_REQUEST, CaloriesExpendedSagas.getCaloriesExpended, api),
    takeLatest(CaloriesExpendedTypes.CALORIES_EXPENDED_ALL_REQUEST, CaloriesExpendedSagas.getAllCaloriesExpendeds, api),
    takeLatest(CaloriesExpendedTypes.CALORIES_EXPENDED_UPDATE_REQUEST, CaloriesExpendedSagas.updateCaloriesExpended, api),
    takeLatest(CaloriesExpendedTypes.CALORIES_EXPENDED_DELETE_REQUEST, CaloriesExpendedSagas.deleteCaloriesExpended, api),

    takeLatest(CiclingPedalingCadenceTypes.CICLING_PEDALING_CADENCE_REQUEST, CiclingPedalingCadenceSagas.getCiclingPedalingCadence, api),
    takeLatest(
      CiclingPedalingCadenceTypes.CICLING_PEDALING_CADENCE_ALL_REQUEST,
      CiclingPedalingCadenceSagas.getAllCiclingPedalingCadences,
      api,
    ),
    takeLatest(
      CiclingPedalingCadenceTypes.CICLING_PEDALING_CADENCE_UPDATE_REQUEST,
      CiclingPedalingCadenceSagas.updateCiclingPedalingCadence,
      api,
    ),
    takeLatest(
      CiclingPedalingCadenceTypes.CICLING_PEDALING_CADENCE_DELETE_REQUEST,
      CiclingPedalingCadenceSagas.deleteCiclingPedalingCadence,
      api,
    ),

    takeLatest(HeartMinutesTypes.HEART_MINUTES_REQUEST, HeartMinutesSagas.getHeartMinutes, api),
    takeLatest(HeartMinutesTypes.HEART_MINUTES_ALL_REQUEST, HeartMinutesSagas.getAllHeartMinutes, api),
    takeLatest(HeartMinutesTypes.HEART_MINUTES_UPDATE_REQUEST, HeartMinutesSagas.updateHeartMinutes, api),
    takeLatest(HeartMinutesTypes.HEART_MINUTES_DELETE_REQUEST, HeartMinutesSagas.deleteHeartMinutes, api),

    takeLatest(ActiveMinutesTypes.ACTIVE_MINUTES_REQUEST, ActiveMinutesSagas.getActiveMinutes, api),
    takeLatest(ActiveMinutesTypes.ACTIVE_MINUTES_ALL_REQUEST, ActiveMinutesSagas.getAllActiveMinutes, api),
    takeLatest(ActiveMinutesTypes.ACTIVE_MINUTES_UPDATE_REQUEST, ActiveMinutesSagas.updateActiveMinutes, api),
    takeLatest(ActiveMinutesTypes.ACTIVE_MINUTES_DELETE_REQUEST, ActiveMinutesSagas.deleteActiveMinutes, api),

    takeLatest(PoweSampleTypes.POWE_SAMPLE_REQUEST, PoweSampleSagas.getPoweSample, api),
    takeLatest(PoweSampleTypes.POWE_SAMPLE_ALL_REQUEST, PoweSampleSagas.getAllPoweSamples, api),
    takeLatest(PoweSampleTypes.POWE_SAMPLE_UPDATE_REQUEST, PoweSampleSagas.updatePoweSample, api),
    takeLatest(PoweSampleTypes.POWE_SAMPLE_DELETE_REQUEST, PoweSampleSagas.deletePoweSample, api),

    takeLatest(StepCountCadenceTypes.STEP_COUNT_CADENCE_REQUEST, StepCountCadenceSagas.getStepCountCadence, api),
    takeLatest(StepCountCadenceTypes.STEP_COUNT_CADENCE_ALL_REQUEST, StepCountCadenceSagas.getAllStepCountCadences, api),
    takeLatest(StepCountCadenceTypes.STEP_COUNT_CADENCE_UPDATE_REQUEST, StepCountCadenceSagas.updateStepCountCadence, api),
    takeLatest(StepCountCadenceTypes.STEP_COUNT_CADENCE_DELETE_REQUEST, StepCountCadenceSagas.deleteStepCountCadence, api),

    takeLatest(StepCountDeltaTypes.STEP_COUNT_DELTA_REQUEST, StepCountDeltaSagas.getStepCountDelta, api),
    takeLatest(StepCountDeltaTypes.STEP_COUNT_DELTA_ALL_REQUEST, StepCountDeltaSagas.getAllStepCountDeltas, api),
    takeLatest(StepCountDeltaTypes.STEP_COUNT_DELTA_UPDATE_REQUEST, StepCountDeltaSagas.updateStepCountDelta, api),
    takeLatest(StepCountDeltaTypes.STEP_COUNT_DELTA_DELETE_REQUEST, StepCountDeltaSagas.deleteStepCountDelta, api),

    takeLatest(ActivityExerciseTypes.ACTIVITY_EXERCISE_REQUEST, ActivityExerciseSagas.getActivityExercise, api),
    takeLatest(ActivityExerciseTypes.ACTIVITY_EXERCISE_ALL_REQUEST, ActivityExerciseSagas.getAllActivityExercises, api),
    takeLatest(ActivityExerciseTypes.ACTIVITY_EXERCISE_UPDATE_REQUEST, ActivityExerciseSagas.updateActivityExercise, api),
    takeLatest(ActivityExerciseTypes.ACTIVITY_EXERCISE_DELETE_REQUEST, ActivityExerciseSagas.deleteActivityExercise, api),

    takeLatest(CyclingWheelRevolutionTypes.CYCLING_WHEEL_REVOLUTION_REQUEST, CyclingWheelRevolutionSagas.getCyclingWheelRevolution, api),
    takeLatest(
      CyclingWheelRevolutionTypes.CYCLING_WHEEL_REVOLUTION_ALL_REQUEST,
      CyclingWheelRevolutionSagas.getAllCyclingWheelRevolutions,
      api,
    ),
    takeLatest(
      CyclingWheelRevolutionTypes.CYCLING_WHEEL_REVOLUTION_UPDATE_REQUEST,
      CyclingWheelRevolutionSagas.updateCyclingWheelRevolution,
      api,
    ),
    takeLatest(
      CyclingWheelRevolutionTypes.CYCLING_WHEEL_REVOLUTION_DELETE_REQUEST,
      CyclingWheelRevolutionSagas.deleteCyclingWheelRevolution,
      api,
    ),

    takeLatest(DistanceDeltaTypes.DISTANCE_DELTA_REQUEST, DistanceDeltaSagas.getDistanceDelta, api),
    takeLatest(DistanceDeltaTypes.DISTANCE_DELTA_ALL_REQUEST, DistanceDeltaSagas.getAllDistanceDeltas, api),
    takeLatest(DistanceDeltaTypes.DISTANCE_DELTA_UPDATE_REQUEST, DistanceDeltaSagas.updateDistanceDelta, api),
    takeLatest(DistanceDeltaTypes.DISTANCE_DELTA_DELETE_REQUEST, DistanceDeltaSagas.deleteDistanceDelta, api),

    takeLatest(LocationSampleTypes.LOCATION_SAMPLE_REQUEST, LocationSampleSagas.getLocationSample, api),
    takeLatest(LocationSampleTypes.LOCATION_SAMPLE_ALL_REQUEST, LocationSampleSagas.getAllLocationSamples, api),
    takeLatest(LocationSampleTypes.LOCATION_SAMPLE_UPDATE_REQUEST, LocationSampleSagas.updateLocationSample, api),
    takeLatest(LocationSampleTypes.LOCATION_SAMPLE_DELETE_REQUEST, LocationSampleSagas.deleteLocationSample, api),

    takeLatest(SpeedTypes.SPEED_REQUEST, SpeedSagas.getSpeed, api),
    takeLatest(SpeedTypes.SPEED_ALL_REQUEST, SpeedSagas.getAllSpeeds, api),
    takeLatest(SpeedTypes.SPEED_UPDATE_REQUEST, SpeedSagas.updateSpeed, api),
    takeLatest(SpeedTypes.SPEED_DELETE_REQUEST, SpeedSagas.deleteSpeed, api),

    takeLatest(NutritionTypes.NUTRITION_REQUEST, NutritionSagas.getNutrition, api),
    takeLatest(NutritionTypes.NUTRITION_ALL_REQUEST, NutritionSagas.getAllNutritions, api),
    takeLatest(NutritionTypes.NUTRITION_UPDATE_REQUEST, NutritionSagas.updateNutrition, api),
    takeLatest(NutritionTypes.NUTRITION_DELETE_REQUEST, NutritionSagas.deleteNutrition, api),

    takeLatest(BloodGlucoseTypes.BLOOD_GLUCOSE_REQUEST, BloodGlucoseSagas.getBloodGlucose, api),
    takeLatest(BloodGlucoseTypes.BLOOD_GLUCOSE_ALL_REQUEST, BloodGlucoseSagas.getAllBloodGlucoses, api),
    takeLatest(BloodGlucoseTypes.BLOOD_GLUCOSE_UPDATE_REQUEST, BloodGlucoseSagas.updateBloodGlucose, api),
    takeLatest(BloodGlucoseTypes.BLOOD_GLUCOSE_DELETE_REQUEST, BloodGlucoseSagas.deleteBloodGlucose, api),

    takeLatest(BloodPressureTypes.BLOOD_PRESSURE_REQUEST, BloodPressureSagas.getBloodPressure, api),
    takeLatest(BloodPressureTypes.BLOOD_PRESSURE_ALL_REQUEST, BloodPressureSagas.getAllBloodPressures, api),
    takeLatest(BloodPressureTypes.BLOOD_PRESSURE_UPDATE_REQUEST, BloodPressureSagas.updateBloodPressure, api),
    takeLatest(BloodPressureTypes.BLOOD_PRESSURE_DELETE_REQUEST, BloodPressureSagas.deleteBloodPressure, api),

    takeLatest(BodyFatPercentageTypes.BODY_FAT_PERCENTAGE_REQUEST, BodyFatPercentageSagas.getBodyFatPercentage, api),
    takeLatest(BodyFatPercentageTypes.BODY_FAT_PERCENTAGE_ALL_REQUEST, BodyFatPercentageSagas.getAllBodyFatPercentages, api),
    takeLatest(BodyFatPercentageTypes.BODY_FAT_PERCENTAGE_UPDATE_REQUEST, BodyFatPercentageSagas.updateBodyFatPercentage, api),
    takeLatest(BodyFatPercentageTypes.BODY_FAT_PERCENTAGE_DELETE_REQUEST, BodyFatPercentageSagas.deleteBodyFatPercentage, api),

    takeLatest(BodyTemperatureTypes.BODY_TEMPERATURE_REQUEST, BodyTemperatureSagas.getBodyTemperature, api),
    takeLatest(BodyTemperatureTypes.BODY_TEMPERATURE_ALL_REQUEST, BodyTemperatureSagas.getAllBodyTemperatures, api),
    takeLatest(BodyTemperatureTypes.BODY_TEMPERATURE_UPDATE_REQUEST, BodyTemperatureSagas.updateBodyTemperature, api),
    takeLatest(BodyTemperatureTypes.BODY_TEMPERATURE_DELETE_REQUEST, BodyTemperatureSagas.deleteBodyTemperature, api),

    takeLatest(HeartRateBpmTypes.HEART_RATE_BPM_REQUEST, HeartRateBpmSagas.getHeartRateBpm, api),
    takeLatest(HeartRateBpmTypes.HEART_RATE_BPM_ALL_REQUEST, HeartRateBpmSagas.getAllHeartRateBpms, api),
    takeLatest(HeartRateBpmTypes.HEART_RATE_BPM_UPDATE_REQUEST, HeartRateBpmSagas.updateHeartRateBpm, api),
    takeLatest(HeartRateBpmTypes.HEART_RATE_BPM_DELETE_REQUEST, HeartRateBpmSagas.deleteHeartRateBpm, api),

    takeLatest(HeightTypes.HEIGHT_REQUEST, HeightSagas.getHeight, api),
    takeLatest(HeightTypes.HEIGHT_ALL_REQUEST, HeightSagas.getAllHeights, api),
    takeLatest(HeightTypes.HEIGHT_UPDATE_REQUEST, HeightSagas.updateHeight, api),
    takeLatest(HeightTypes.HEIGHT_DELETE_REQUEST, HeightSagas.deleteHeight, api),

    takeLatest(OxygenSaturationTypes.OXYGEN_SATURATION_REQUEST, OxygenSaturationSagas.getOxygenSaturation, api),
    takeLatest(OxygenSaturationTypes.OXYGEN_SATURATION_ALL_REQUEST, OxygenSaturationSagas.getAllOxygenSaturations, api),
    takeLatest(OxygenSaturationTypes.OXYGEN_SATURATION_UPDATE_REQUEST, OxygenSaturationSagas.updateOxygenSaturation, api),
    takeLatest(OxygenSaturationTypes.OXYGEN_SATURATION_DELETE_REQUEST, OxygenSaturationSagas.deleteOxygenSaturation, api),

    takeLatest(SleepSegmentTypes.SLEEP_SEGMENT_REQUEST, SleepSegmentSagas.getSleepSegment, api),
    takeLatest(SleepSegmentTypes.SLEEP_SEGMENT_ALL_REQUEST, SleepSegmentSagas.getAllSleepSegments, api),
    takeLatest(SleepSegmentTypes.SLEEP_SEGMENT_UPDATE_REQUEST, SleepSegmentSagas.updateSleepSegment, api),
    takeLatest(SleepSegmentTypes.SLEEP_SEGMENT_DELETE_REQUEST, SleepSegmentSagas.deleteSleepSegment, api),

    takeLatest(WeightTypes.WEIGHT_REQUEST, WeightSagas.getWeight, api),
    takeLatest(WeightTypes.WEIGHT_ALL_REQUEST, WeightSagas.getAllWeights, api),
    takeLatest(WeightTypes.WEIGHT_UPDATE_REQUEST, WeightSagas.updateWeight, api),
    takeLatest(WeightTypes.WEIGHT_DELETE_REQUEST, WeightSagas.deleteWeight, api),

    takeLatest(ActivitySummaryTypes.ACTIVITY_SUMMARY_REQUEST, ActivitySummarySagas.getActivitySummary, api),
    takeLatest(ActivitySummaryTypes.ACTIVITY_SUMMARY_ALL_REQUEST, ActivitySummarySagas.getAllActivitySummaries, api),
    takeLatest(ActivitySummaryTypes.ACTIVITY_SUMMARY_UPDATE_REQUEST, ActivitySummarySagas.updateActivitySummary, api),
    takeLatest(ActivitySummaryTypes.ACTIVITY_SUMMARY_DELETE_REQUEST, ActivitySummarySagas.deleteActivitySummary, api),

    takeLatest(CaloriesBmrSummaryTypes.CALORIES_BMR_SUMMARY_REQUEST, CaloriesBmrSummarySagas.getCaloriesBmrSummary, api),
    takeLatest(CaloriesBmrSummaryTypes.CALORIES_BMR_SUMMARY_ALL_REQUEST, CaloriesBmrSummarySagas.getAllCaloriesBmrSummaries, api),
    takeLatest(CaloriesBmrSummaryTypes.CALORIES_BMR_SUMMARY_UPDATE_REQUEST, CaloriesBmrSummarySagas.updateCaloriesBmrSummary, api),
    takeLatest(CaloriesBmrSummaryTypes.CALORIES_BMR_SUMMARY_DELETE_REQUEST, CaloriesBmrSummarySagas.deleteCaloriesBmrSummary, api),

    takeLatest(PowerSummaryTypes.POWER_SUMMARY_REQUEST, PowerSummarySagas.getPowerSummary, api),
    takeLatest(PowerSummaryTypes.POWER_SUMMARY_ALL_REQUEST, PowerSummarySagas.getAllPowerSummaries, api),
    takeLatest(PowerSummaryTypes.POWER_SUMMARY_UPDATE_REQUEST, PowerSummarySagas.updatePowerSummary, api),
    takeLatest(PowerSummaryTypes.POWER_SUMMARY_DELETE_REQUEST, PowerSummarySagas.deletePowerSummary, api),

    takeLatest(
      BodyFatPercentageSummaryTypes.BODY_FAT_PERCENTAGE_SUMMARY_REQUEST,
      BodyFatPercentageSummarySagas.getBodyFatPercentageSummary,
      api,
    ),
    takeLatest(
      BodyFatPercentageSummaryTypes.BODY_FAT_PERCENTAGE_SUMMARY_ALL_REQUEST,
      BodyFatPercentageSummarySagas.getAllBodyFatPercentageSummaries,
      api,
    ),
    takeLatest(
      BodyFatPercentageSummaryTypes.BODY_FAT_PERCENTAGE_SUMMARY_UPDATE_REQUEST,
      BodyFatPercentageSummarySagas.updateBodyFatPercentageSummary,
      api,
    ),
    takeLatest(
      BodyFatPercentageSummaryTypes.BODY_FAT_PERCENTAGE_SUMMARY_DELETE_REQUEST,
      BodyFatPercentageSummarySagas.deleteBodyFatPercentageSummary,
      api,
    ),

    takeLatest(HeartRateSummaryTypes.HEART_RATE_SUMMARY_REQUEST, HeartRateSummarySagas.getHeartRateSummary, api),
    takeLatest(HeartRateSummaryTypes.HEART_RATE_SUMMARY_ALL_REQUEST, HeartRateSummarySagas.getAllHeartRateSummaries, api),
    takeLatest(HeartRateSummaryTypes.HEART_RATE_SUMMARY_UPDATE_REQUEST, HeartRateSummarySagas.updateHeartRateSummary, api),
    takeLatest(HeartRateSummaryTypes.HEART_RATE_SUMMARY_DELETE_REQUEST, HeartRateSummarySagas.deleteHeartRateSummary, api),

    takeLatest(HeightSummaryTypes.HEIGHT_SUMMARY_REQUEST, HeightSummarySagas.getHeightSummary, api),
    takeLatest(HeightSummaryTypes.HEIGHT_SUMMARY_ALL_REQUEST, HeightSummarySagas.getAllHeightSummaries, api),
    takeLatest(HeightSummaryTypes.HEIGHT_SUMMARY_UPDATE_REQUEST, HeightSummarySagas.updateHeightSummary, api),
    takeLatest(HeightSummaryTypes.HEIGHT_SUMMARY_DELETE_REQUEST, HeightSummarySagas.deleteHeightSummary, api),

    takeLatest(WeightSummaryTypes.WEIGHT_SUMMARY_REQUEST, WeightSummarySagas.getWeightSummary, api),
    takeLatest(WeightSummaryTypes.WEIGHT_SUMMARY_ALL_REQUEST, WeightSummarySagas.getAllWeightSummaries, api),
    takeLatest(WeightSummaryTypes.WEIGHT_SUMMARY_UPDATE_REQUEST, WeightSummarySagas.updateWeightSummary, api),
    takeLatest(WeightSummaryTypes.WEIGHT_SUMMARY_DELETE_REQUEST, WeightSummarySagas.deleteWeightSummary, api),

    takeLatest(SpeedSummaryTypes.SPEED_SUMMARY_REQUEST, SpeedSummarySagas.getSpeedSummary, api),
    takeLatest(SpeedSummaryTypes.SPEED_SUMMARY_ALL_REQUEST, SpeedSummarySagas.getAllSpeedSummaries, api),
    takeLatest(SpeedSummaryTypes.SPEED_SUMMARY_UPDATE_REQUEST, SpeedSummarySagas.updateSpeedSummary, api),
    takeLatest(SpeedSummaryTypes.SPEED_SUMMARY_DELETE_REQUEST, SpeedSummarySagas.deleteSpeedSummary, api),

    takeLatest(NutritionSummaryTypes.NUTRITION_SUMMARY_REQUEST, NutritionSummarySagas.getNutritionSummary, api),
    takeLatest(NutritionSummaryTypes.NUTRITION_SUMMARY_ALL_REQUEST, NutritionSummarySagas.getAllNutritionSummaries, api),
    takeLatest(NutritionSummaryTypes.NUTRITION_SUMMARY_UPDATE_REQUEST, NutritionSummarySagas.updateNutritionSummary, api),
    takeLatest(NutritionSummaryTypes.NUTRITION_SUMMARY_DELETE_REQUEST, NutritionSummarySagas.deleteNutritionSummary, api),

    takeLatest(BloodGlucoseSummaryTypes.BLOOD_GLUCOSE_SUMMARY_REQUEST, BloodGlucoseSummarySagas.getBloodGlucoseSummary, api),
    takeLatest(BloodGlucoseSummaryTypes.BLOOD_GLUCOSE_SUMMARY_ALL_REQUEST, BloodGlucoseSummarySagas.getAllBloodGlucoseSummaries, api),
    takeLatest(BloodGlucoseSummaryTypes.BLOOD_GLUCOSE_SUMMARY_UPDATE_REQUEST, BloodGlucoseSummarySagas.updateBloodGlucoseSummary, api),
    takeLatest(BloodGlucoseSummaryTypes.BLOOD_GLUCOSE_SUMMARY_DELETE_REQUEST, BloodGlucoseSummarySagas.deleteBloodGlucoseSummary, api),

    takeLatest(BloodPressureSummaryTypes.BLOOD_PRESSURE_SUMMARY_REQUEST, BloodPressureSummarySagas.getBloodPressureSummary, api),
    takeLatest(BloodPressureSummaryTypes.BLOOD_PRESSURE_SUMMARY_ALL_REQUEST, BloodPressureSummarySagas.getAllBloodPressureSummaries, api),
    takeLatest(BloodPressureSummaryTypes.BLOOD_PRESSURE_SUMMARY_UPDATE_REQUEST, BloodPressureSummarySagas.updateBloodPressureSummary, api),
    takeLatest(BloodPressureSummaryTypes.BLOOD_PRESSURE_SUMMARY_DELETE_REQUEST, BloodPressureSummarySagas.deleteBloodPressureSummary, api),

    takeLatest(TemperatureSummaryTypes.TEMPERATURE_SUMMARY_REQUEST, TemperatureSummarySagas.getTemperatureSummary, api),
    takeLatest(TemperatureSummaryTypes.TEMPERATURE_SUMMARY_ALL_REQUEST, TemperatureSummarySagas.getAllTemperatureSummaries, api),
    takeLatest(TemperatureSummaryTypes.TEMPERATURE_SUMMARY_UPDATE_REQUEST, TemperatureSummarySagas.updateTemperatureSummary, api),
    takeLatest(TemperatureSummaryTypes.TEMPERATURE_SUMMARY_DELETE_REQUEST, TemperatureSummarySagas.deleteTemperatureSummary, api),

    takeLatest(
      OxygenSaturationSummaryTypes.OXYGEN_SATURATION_SUMMARY_REQUEST,
      OxygenSaturationSummarySagas.getOxygenSaturationSummary,
      api,
    ),
    takeLatest(
      OxygenSaturationSummaryTypes.OXYGEN_SATURATION_SUMMARY_ALL_REQUEST,
      OxygenSaturationSummarySagas.getAllOxygenSaturationSummaries,
      api,
    ),
    takeLatest(
      OxygenSaturationSummaryTypes.OXYGEN_SATURATION_SUMMARY_UPDATE_REQUEST,
      OxygenSaturationSummarySagas.updateOxygenSaturationSummary,
      api,
    ),
    takeLatest(
      OxygenSaturationSummaryTypes.OXYGEN_SATURATION_SUMMARY_DELETE_REQUEST,
      OxygenSaturationSummarySagas.deleteOxygenSaturationSummary,
      api,
    ),
    // jhipster-react-native-saga-redux-connect-needle

    takeLatest(RegisterTypes.REGISTER_REQUEST, register, api),
    takeLatest(ForgotPasswordTypes.FORGOT_PASSWORD_REQUEST, forgotPassword, api),
    takeLatest(ChangePasswordTypes.CHANGE_PASSWORD_REQUEST, changePassword, api),
    takeLatest(UserTypes.USER_REQUEST, UserSagas.getUser, api),
    takeLatest(UserTypes.USER_UPDATE_REQUEST, UserSagas.updateUser, api),
    takeLatest(UserTypes.USER_DELETE_REQUEST, UserSagas.deleteUser, api),
    takeLatest(UserTypes.USER_ALL_REQUEST, UserSagas.getAllUsers, api),

    takeLatest(AccountTypes.ACCOUNT_REQUEST, getAccount, api),
    takeLatest(AccountTypes.ACCOUNT_UPDATE_REQUEST, updateAccount, api),
  ]);
}
