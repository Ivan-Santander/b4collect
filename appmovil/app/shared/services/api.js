// a library to wrap and simplify api calls
import apisauce from 'apisauce';

import AppConfig from '../../config/app-config';

// our "constructor"
const create = (baseURL = AppConfig.apiUrl) => {
  // ------
  // STEP 1
  // ------
  //
  // Create and configure an apisauce-based api object.
  //
  const api = apisauce.create({
    // base URL is read from the "constructor"
    baseURL,
    // here are some default headers
    headers: {
      'Cache-Control': 'no-cache',
    },
    // 10 second timeout...
    timeout: 10000,
  });

  // ------
  // STEP 2
  // ------
  //
  // Define some functions that call the api.  The goal is to provide
  // a thin wrapper of the api layer providing nicer feeling functions
  // rather than "get", "post" and friends.
  //
  // I generally don't like wrapping the output at this level because
  // sometimes specific actions need to be take on `403` or `401`, etc.
  //
  // Since we can't hide from that, we embrace it by getting out of the
  // way at this level.
  //
  const setAuthToken = (userAuth) => api.setHeader('Authorization', 'Bearer ' + userAuth);
  const removeAuthToken = () => api.deleteHeader('Authorization');
  const login = (userAuth) => api.post('api/authenticate', userAuth);
  const register = (user) => api.post('api/register', user);
  const forgotPassword = (data) =>
    api.post('api/account/reset-password/init', data, {
      headers: { 'Content-Type': 'text/plain', Accept: 'application/json, text/plain, */*' },
    });

  const getAccount = () => api.get('api/account');
  const updateAccount = (account) => api.post('api/account', account);
  const changePassword = (currentPassword, newPassword) =>
    api.post(
      'api/account/change-password',
      { currentPassword, newPassword },
      { headers: { 'Content-Type': 'application/json', Accept: 'application/json, text/plain, */*' } },
    );

  const getUser = (userId) => api.get('api/users/' + userId);
  const getAllUsers = (options) => api.get('api/users', options);
  const createUser = (user) => api.post('api/users', user);
  const updateUser = (user) => api.put('api/users', user);
  const deleteUser = (userId) => api.delete('api/users/' + userId);

  const getCaloriesBMR = (caloriesBMRId) => api.get('api/calories-bmrs/' + caloriesBMRId);
  const getAllCaloriesBMRS = (options) => api.get('api/calories-bmrs', options);
  const createCaloriesBMR = (caloriesBMR) => api.post('api/calories-bmrs', caloriesBMR);
  const updateCaloriesBMR = (caloriesBMR) => api.put(`api/calories-bmrs/${caloriesBMR.id}`, caloriesBMR);
  const deleteCaloriesBMR = (caloriesBMRId) => api.delete('api/calories-bmrs/' + caloriesBMRId);

  const getCaloriesExpended = (caloriesExpendedId) => api.get('api/calories-expendeds/' + caloriesExpendedId);
  const getAllCaloriesExpendeds = (options) => api.get('api/calories-expendeds', options);
  const createCaloriesExpended = (caloriesExpended) => api.post('api/calories-expendeds', caloriesExpended);
  const updateCaloriesExpended = (caloriesExpended) => api.put(`api/calories-expendeds/${caloriesExpended.id}`, caloriesExpended);
  const deleteCaloriesExpended = (caloriesExpendedId) => api.delete('api/calories-expendeds/' + caloriesExpendedId);

  const getCiclingPedalingCadence = (ciclingPedalingCadenceId) => api.get('api/cicling-pedaling-cadences/' + ciclingPedalingCadenceId);
  const getAllCiclingPedalingCadences = (options) => api.get('api/cicling-pedaling-cadences', options);
  const createCiclingPedalingCadence = (ciclingPedalingCadence) => api.post('api/cicling-pedaling-cadences', ciclingPedalingCadence);
  const updateCiclingPedalingCadence = (ciclingPedalingCadence) =>
    api.put(`api/cicling-pedaling-cadences/${ciclingPedalingCadence.id}`, ciclingPedalingCadence);
  const deleteCiclingPedalingCadence = (ciclingPedalingCadenceId) =>
    api.delete('api/cicling-pedaling-cadences/' + ciclingPedalingCadenceId);

  const getHeartMinutes = (heartMinutesId) => api.get('api/heart-minutes/' + heartMinutesId);
  const getAllHeartMinutes = (options) => api.get('api/heart-minutes', options);
  const createHeartMinutes = (heartMinutes) => api.post('api/heart-minutes', heartMinutes);
  const updateHeartMinutes = (heartMinutes) => api.put(`api/heart-minutes/${heartMinutes.id}`, heartMinutes);
  const deleteHeartMinutes = (heartMinutesId) => api.delete('api/heart-minutes/' + heartMinutesId);

  const getActiveMinutes = (activeMinutesId) => api.get('api/active-minutes/' + activeMinutesId);
  const getAllActiveMinutes = (options) => api.get('api/active-minutes', options);
  const createActiveMinutes = (activeMinutes) => api.post('api/active-minutes', activeMinutes);
  const updateActiveMinutes = (activeMinutes) => api.put(`api/active-minutes/${activeMinutes.id}`, activeMinutes);
  const deleteActiveMinutes = (activeMinutesId) => api.delete('api/active-minutes/' + activeMinutesId);

  const getPoweSample = (poweSampleId) => api.get('api/powe-samples/' + poweSampleId);
  const getAllPoweSamples = (options) => api.get('api/powe-samples', options);
  const createPoweSample = (poweSample) => api.post('api/powe-samples', poweSample);
  const updatePoweSample = (poweSample) => api.put(`api/powe-samples/${poweSample.id}`, poweSample);
  const deletePoweSample = (poweSampleId) => api.delete('api/powe-samples/' + poweSampleId);

  const getStepCountCadence = (stepCountCadenceId) => api.get('api/step-count-cadences/' + stepCountCadenceId);
  const getAllStepCountCadences = (options) => api.get('api/step-count-cadences', options);
  const createStepCountCadence = (stepCountCadence) => api.post('api/step-count-cadences', stepCountCadence);
  const updateStepCountCadence = (stepCountCadence) => api.put(`api/step-count-cadences/${stepCountCadence.id}`, stepCountCadence);
  const deleteStepCountCadence = (stepCountCadenceId) => api.delete('api/step-count-cadences/' + stepCountCadenceId);

  const getStepCountDelta = (stepCountDeltaId) => api.get('api/step-count-deltas/' + stepCountDeltaId);
  const getAllStepCountDeltas = (options) => api.get('api/step-count-deltas', options);
  const createStepCountDelta = (stepCountDelta) => api.post('api/step-count-deltas', stepCountDelta);
  const updateStepCountDelta = (stepCountDelta) => api.put(`api/step-count-deltas/${stepCountDelta.id}`, stepCountDelta);
  const deleteStepCountDelta = (stepCountDeltaId) => api.delete('api/step-count-deltas/' + stepCountDeltaId);

  const getActivityExercise = (activityExerciseId) => api.get('api/activity-exercises/' + activityExerciseId);
  const getAllActivityExercises = (options) => api.get('api/activity-exercises', options);
  const createActivityExercise = (activityExercise) => api.post('api/activity-exercises', activityExercise);
  const updateActivityExercise = (activityExercise) => api.put(`api/activity-exercises/${activityExercise.id}`, activityExercise);
  const deleteActivityExercise = (activityExerciseId) => api.delete('api/activity-exercises/' + activityExerciseId);

  const getCyclingWheelRevolution = (cyclingWheelRevolutionId) => api.get('api/cycling-wheel-revolutions/' + cyclingWheelRevolutionId);
  const getAllCyclingWheelRevolutions = (options) => api.get('api/cycling-wheel-revolutions', options);
  const createCyclingWheelRevolution = (cyclingWheelRevolution) => api.post('api/cycling-wheel-revolutions', cyclingWheelRevolution);
  const updateCyclingWheelRevolution = (cyclingWheelRevolution) =>
    api.put(`api/cycling-wheel-revolutions/${cyclingWheelRevolution.id}`, cyclingWheelRevolution);
  const deleteCyclingWheelRevolution = (cyclingWheelRevolutionId) =>
    api.delete('api/cycling-wheel-revolutions/' + cyclingWheelRevolutionId);

  const getDistanceDelta = (distanceDeltaId) => api.get('api/distance-deltas/' + distanceDeltaId);
  const getAllDistanceDeltas = (options) => api.get('api/distance-deltas', options);
  const createDistanceDelta = (distanceDelta) => api.post('api/distance-deltas', distanceDelta);
  const updateDistanceDelta = (distanceDelta) => api.put(`api/distance-deltas/${distanceDelta.id}`, distanceDelta);
  const deleteDistanceDelta = (distanceDeltaId) => api.delete('api/distance-deltas/' + distanceDeltaId);

  const getLocationSample = (locationSampleId) => api.get('api/location-samples/' + locationSampleId);
  const getAllLocationSamples = (options) => api.get('api/location-samples', options);
  const createLocationSample = (locationSample) => api.post('api/location-samples', locationSample);
  const updateLocationSample = (locationSample) => api.put(`api/location-samples/${locationSample.id}`, locationSample);
  const deleteLocationSample = (locationSampleId) => api.delete('api/location-samples/' + locationSampleId);

  const getSpeed = (speedId) => api.get('api/speeds/' + speedId);
  const getAllSpeeds = (options) => api.get('api/speeds', options);
  const createSpeed = (speed) => api.post('api/speeds', speed);
  const updateSpeed = (speed) => api.put(`api/speeds/${speed.id}`, speed);
  const deleteSpeed = (speedId) => api.delete('api/speeds/' + speedId);

  const getNutrition = (nutritionId) => api.get('api/nutritions/' + nutritionId);
  const getAllNutritions = (options) => api.get('api/nutritions', options);
  const createNutrition = (nutrition) => api.post('api/nutritions', nutrition);
  const updateNutrition = (nutrition) => api.put(`api/nutritions/${nutrition.id}`, nutrition);
  const deleteNutrition = (nutritionId) => api.delete('api/nutritions/' + nutritionId);

  const getBloodGlucose = (bloodGlucoseId) => api.get('api/blood-glucoses/' + bloodGlucoseId);
  const getAllBloodGlucoses = (options) => api.get('api/blood-glucoses', options);
  const createBloodGlucose = (bloodGlucose) => api.post('api/blood-glucoses', bloodGlucose);
  const updateBloodGlucose = (bloodGlucose) => api.put(`api/blood-glucoses/${bloodGlucose.id}`, bloodGlucose);
  const deleteBloodGlucose = (bloodGlucoseId) => api.delete('api/blood-glucoses/' + bloodGlucoseId);

  const getBloodPressure = (bloodPressureId) => api.get('api/blood-pressures/' + bloodPressureId);
  const getAllBloodPressures = (options) => api.get('api/blood-pressures', options);
  const createBloodPressure = (bloodPressure) => api.post('api/blood-pressures', bloodPressure);
  const updateBloodPressure = (bloodPressure) => api.put(`api/blood-pressures/${bloodPressure.id}`, bloodPressure);
  const deleteBloodPressure = (bloodPressureId) => api.delete('api/blood-pressures/' + bloodPressureId);

  const getBodyFatPercentage = (bodyFatPercentageId) => api.get('api/body-fat-percentages/' + bodyFatPercentageId);
  const getAllBodyFatPercentages = (options) => api.get('api/body-fat-percentages', options);
  const createBodyFatPercentage = (bodyFatPercentage) => api.post('api/body-fat-percentages', bodyFatPercentage);
  const updateBodyFatPercentage = (bodyFatPercentage) => api.put(`api/body-fat-percentages/${bodyFatPercentage.id}`, bodyFatPercentage);
  const deleteBodyFatPercentage = (bodyFatPercentageId) => api.delete('api/body-fat-percentages/' + bodyFatPercentageId);

  const getBodyTemperature = (bodyTemperatureId) => api.get('api/body-temperatures/' + bodyTemperatureId);
  const getAllBodyTemperatures = (options) => api.get('api/body-temperatures', options);
  const createBodyTemperature = (bodyTemperature) => api.post('api/body-temperatures', bodyTemperature);
  const updateBodyTemperature = (bodyTemperature) => api.put(`api/body-temperatures/${bodyTemperature.id}`, bodyTemperature);
  const deleteBodyTemperature = (bodyTemperatureId) => api.delete('api/body-temperatures/' + bodyTemperatureId);

  const getHeartRateBpm = (heartRateBpmId) => api.get('api/heart-rate-bpms/' + heartRateBpmId);
  const getAllHeartRateBpms = (options) => api.get('api/heart-rate-bpms', options);
  const createHeartRateBpm = (heartRateBpm) => api.post('api/heart-rate-bpms', heartRateBpm);
  const updateHeartRateBpm = (heartRateBpm) => api.put(`api/heart-rate-bpms/${heartRateBpm.id}`, heartRateBpm);
  const deleteHeartRateBpm = (heartRateBpmId) => api.delete('api/heart-rate-bpms/' + heartRateBpmId);

  const getHeight = (heightId) => api.get('api/heights/' + heightId);
  const getAllHeights = (options) => api.get('api/heights', options);
  const createHeight = (height) => api.post('api/heights', height);
  const updateHeight = (height) => api.put(`api/heights/${height.id}`, height);
  const deleteHeight = (heightId) => api.delete('api/heights/' + heightId);

  const getOxygenSaturation = (oxygenSaturationId) => api.get('api/oxygen-saturations/' + oxygenSaturationId);
  const getAllOxygenSaturations = (options) => api.get('api/oxygen-saturations', options);
  const createOxygenSaturation = (oxygenSaturation) => api.post('api/oxygen-saturations', oxygenSaturation);
  const updateOxygenSaturation = (oxygenSaturation) => api.put(`api/oxygen-saturations/${oxygenSaturation.id}`, oxygenSaturation);
  const deleteOxygenSaturation = (oxygenSaturationId) => api.delete('api/oxygen-saturations/' + oxygenSaturationId);

  const getSleepSegment = (sleepSegmentId) => api.get('api/sleep-segments/' + sleepSegmentId);
  const getAllSleepSegments = (options) => api.get('api/sleep-segments', options);
  const createSleepSegment = (sleepSegment) => api.post('api/sleep-segments', sleepSegment);
  const updateSleepSegment = (sleepSegment) => api.put(`api/sleep-segments/${sleepSegment.id}`, sleepSegment);
  const deleteSleepSegment = (sleepSegmentId) => api.delete('api/sleep-segments/' + sleepSegmentId);

  const getWeight = (weightId) => api.get('api/weights/' + weightId);
  const getAllWeights = (options) => api.get('api/weights', options);
  const createWeight = (weight) => api.post('api/weights', weight);
  const updateWeight = (weight) => api.put(`api/weights/${weight.id}`, weight);
  const deleteWeight = (weightId) => api.delete('api/weights/' + weightId);

  const getActivitySummary = (activitySummaryId) => api.get('api/activity-summaries/' + activitySummaryId);
  const getAllActivitySummaries = (options) => api.get('api/activity-summaries', options);
  const createActivitySummary = (activitySummary) => api.post('api/activity-summaries', activitySummary);
  const updateActivitySummary = (activitySummary) => api.put(`api/activity-summaries/${activitySummary.id}`, activitySummary);
  const deleteActivitySummary = (activitySummaryId) => api.delete('api/activity-summaries/' + activitySummaryId);

  const getCaloriesBmrSummary = (caloriesBmrSummaryId) => api.get('api/calories-bmr-summaries/' + caloriesBmrSummaryId);
  const getAllCaloriesBmrSummaries = (options) => api.get('api/calories-bmr-summaries', options);
  const createCaloriesBmrSummary = (caloriesBmrSummary) => api.post('api/calories-bmr-summaries', caloriesBmrSummary);
  const updateCaloriesBmrSummary = (caloriesBmrSummary) =>
    api.put(`api/calories-bmr-summaries/${caloriesBmrSummary.id}`, caloriesBmrSummary);
  const deleteCaloriesBmrSummary = (caloriesBmrSummaryId) => api.delete('api/calories-bmr-summaries/' + caloriesBmrSummaryId);

  const getPowerSummary = (powerSummaryId) => api.get('api/power-summaries/' + powerSummaryId);
  const getAllPowerSummaries = (options) => api.get('api/power-summaries', options);
  const createPowerSummary = (powerSummary) => api.post('api/power-summaries', powerSummary);
  const updatePowerSummary = (powerSummary) => api.put(`api/power-summaries/${powerSummary.id}`, powerSummary);
  const deletePowerSummary = (powerSummaryId) => api.delete('api/power-summaries/' + powerSummaryId);

  const getBodyFatPercentageSummary = (bodyFatPercentageSummaryId) =>
    api.get('api/body-fat-percentage-summaries/' + bodyFatPercentageSummaryId);
  const getAllBodyFatPercentageSummaries = (options) => api.get('api/body-fat-percentage-summaries', options);
  const createBodyFatPercentageSummary = (bodyFatPercentageSummary) =>
    api.post('api/body-fat-percentage-summaries', bodyFatPercentageSummary);
  const updateBodyFatPercentageSummary = (bodyFatPercentageSummary) =>
    api.put(`api/body-fat-percentage-summaries/${bodyFatPercentageSummary.id}`, bodyFatPercentageSummary);
  const deleteBodyFatPercentageSummary = (bodyFatPercentageSummaryId) =>
    api.delete('api/body-fat-percentage-summaries/' + bodyFatPercentageSummaryId);

  const getHeartRateSummary = (heartRateSummaryId) => api.get('api/heart-rate-summaries/' + heartRateSummaryId);
  const getAllHeartRateSummaries = (options) => api.get('api/heart-rate-summaries', options);
  const createHeartRateSummary = (heartRateSummary) => api.post('api/heart-rate-summaries', heartRateSummary);
  const updateHeartRateSummary = (heartRateSummary) => api.put(`api/heart-rate-summaries/${heartRateSummary.id}`, heartRateSummary);
  const deleteHeartRateSummary = (heartRateSummaryId) => api.delete('api/heart-rate-summaries/' + heartRateSummaryId);

  const getHeightSummary = (heightSummaryId) => api.get('api/height-summaries/' + heightSummaryId);
  const getAllHeightSummaries = (options) => api.get('api/height-summaries', options);
  const createHeightSummary = (heightSummary) => api.post('api/height-summaries', heightSummary);
  const updateHeightSummary = (heightSummary) => api.put(`api/height-summaries/${heightSummary.id}`, heightSummary);
  const deleteHeightSummary = (heightSummaryId) => api.delete('api/height-summaries/' + heightSummaryId);

  const getWeightSummary = (weightSummaryId) => api.get('api/weight-summaries/' + weightSummaryId);
  const getAllWeightSummaries = (options) => api.get('api/weight-summaries', options);
  const createWeightSummary = (weightSummary) => api.post('api/weight-summaries', weightSummary);
  const updateWeightSummary = (weightSummary) => api.put(`api/weight-summaries/${weightSummary.id}`, weightSummary);
  const deleteWeightSummary = (weightSummaryId) => api.delete('api/weight-summaries/' + weightSummaryId);

  const getSpeedSummary = (speedSummaryId) => api.get('api/speed-summaries/' + speedSummaryId);
  const getAllSpeedSummaries = (options) => api.get('api/speed-summaries', options);
  const createSpeedSummary = (speedSummary) => api.post('api/speed-summaries', speedSummary);
  const updateSpeedSummary = (speedSummary) => api.put(`api/speed-summaries/${speedSummary.id}`, speedSummary);
  const deleteSpeedSummary = (speedSummaryId) => api.delete('api/speed-summaries/' + speedSummaryId);

  const getNutritionSummary = (nutritionSummaryId) => api.get('api/nutrition-summaries/' + nutritionSummaryId);
  const getAllNutritionSummaries = (options) => api.get('api/nutrition-summaries', options);
  const createNutritionSummary = (nutritionSummary) => api.post('api/nutrition-summaries', nutritionSummary);
  const updateNutritionSummary = (nutritionSummary) => api.put(`api/nutrition-summaries/${nutritionSummary.id}`, nutritionSummary);
  const deleteNutritionSummary = (nutritionSummaryId) => api.delete('api/nutrition-summaries/' + nutritionSummaryId);

  const getBloodGlucoseSummary = (bloodGlucoseSummaryId) => api.get('api/blood-glucose-summaries/' + bloodGlucoseSummaryId);
  const getAllBloodGlucoseSummaries = (options) => api.get('api/blood-glucose-summaries', options);
  const createBloodGlucoseSummary = (bloodGlucoseSummary) => api.post('api/blood-glucose-summaries', bloodGlucoseSummary);
  const updateBloodGlucoseSummary = (bloodGlucoseSummary) =>
    api.put(`api/blood-glucose-summaries/${bloodGlucoseSummary.id}`, bloodGlucoseSummary);
  const deleteBloodGlucoseSummary = (bloodGlucoseSummaryId) => api.delete('api/blood-glucose-summaries/' + bloodGlucoseSummaryId);

  const getBloodPressureSummary = (bloodPressureSummaryId) => api.get('api/blood-pressure-summaries/' + bloodPressureSummaryId);
  const getAllBloodPressureSummaries = (options) => api.get('api/blood-pressure-summaries', options);
  const createBloodPressureSummary = (bloodPressureSummary) => api.post('api/blood-pressure-summaries', bloodPressureSummary);
  const updateBloodPressureSummary = (bloodPressureSummary) =>
    api.put(`api/blood-pressure-summaries/${bloodPressureSummary.id}`, bloodPressureSummary);
  const deleteBloodPressureSummary = (bloodPressureSummaryId) => api.delete('api/blood-pressure-summaries/' + bloodPressureSummaryId);

  const getTemperatureSummary = (temperatureSummaryId) => api.get('api/temperature-summaries/' + temperatureSummaryId);
  const getAllTemperatureSummaries = (options) => api.get('api/temperature-summaries', options);
  const createTemperatureSummary = (temperatureSummary) => api.post('api/temperature-summaries', temperatureSummary);
  const updateTemperatureSummary = (temperatureSummary) =>
    api.put(`api/temperature-summaries/${temperatureSummary.id}`, temperatureSummary);
  const deleteTemperatureSummary = (temperatureSummaryId) => api.delete('api/temperature-summaries/' + temperatureSummaryId);

  const getOxygenSaturationSummary = (oxygenSaturationSummaryId) => api.get('api/oxygen-saturation-summaries/' + oxygenSaturationSummaryId);
  const getAllOxygenSaturationSummaries = (options) => api.get('api/oxygen-saturation-summaries', options);
  const createOxygenSaturationSummary = (oxygenSaturationSummary) => api.post('api/oxygen-saturation-summaries', oxygenSaturationSummary);
  const updateOxygenSaturationSummary = (oxygenSaturationSummary) =>
    api.put(`api/oxygen-saturation-summaries/${oxygenSaturationSummary.id}`, oxygenSaturationSummary);
  const deleteOxygenSaturationSummary = (oxygenSaturationSummaryId) =>
    api.delete('api/oxygen-saturation-summaries/' + oxygenSaturationSummaryId);
  // jhipster-react-native-api-method-needle

  // ------
  // STEP 3
  // ------
  //
  // Return back a collection of functions that we would consider our
  // interface.  Most of the time it'll be just the list of all the
  // methods in step 2.
  //
  // Notice we're not returning back the `api` created in step 1?  That's
  // because it is scoped privately.  This is one way to create truly
  // private scoped goodies in JavaScript.
  //
  return {
    // a list of the API functions from step 2
    createUser,
    updateUser,
    getAllUsers,
    getUser,
    deleteUser,

    createCaloriesBMR,
    updateCaloriesBMR,
    getAllCaloriesBMRS,
    getCaloriesBMR,
    deleteCaloriesBMR,

    createCaloriesExpended,
    updateCaloriesExpended,
    getAllCaloriesExpendeds,
    getCaloriesExpended,
    deleteCaloriesExpended,

    createCiclingPedalingCadence,
    updateCiclingPedalingCadence,
    getAllCiclingPedalingCadences,
    getCiclingPedalingCadence,
    deleteCiclingPedalingCadence,

    createHeartMinutes,
    updateHeartMinutes,
    getAllHeartMinutes,
    getHeartMinutes,
    deleteHeartMinutes,

    createActiveMinutes,
    updateActiveMinutes,
    getAllActiveMinutes,
    getActiveMinutes,
    deleteActiveMinutes,

    createPoweSample,
    updatePoweSample,
    getAllPoweSamples,
    getPoweSample,
    deletePoweSample,

    createStepCountCadence,
    updateStepCountCadence,
    getAllStepCountCadences,
    getStepCountCadence,
    deleteStepCountCadence,

    createStepCountDelta,
    updateStepCountDelta,
    getAllStepCountDeltas,
    getStepCountDelta,
    deleteStepCountDelta,

    createActivityExercise,
    updateActivityExercise,
    getAllActivityExercises,
    getActivityExercise,
    deleteActivityExercise,

    createCyclingWheelRevolution,
    updateCyclingWheelRevolution,
    getAllCyclingWheelRevolutions,
    getCyclingWheelRevolution,
    deleteCyclingWheelRevolution,

    createDistanceDelta,
    updateDistanceDelta,
    getAllDistanceDeltas,
    getDistanceDelta,
    deleteDistanceDelta,

    createLocationSample,
    updateLocationSample,
    getAllLocationSamples,
    getLocationSample,
    deleteLocationSample,

    createSpeed,
    updateSpeed,
    getAllSpeeds,
    getSpeed,
    deleteSpeed,

    createNutrition,
    updateNutrition,
    getAllNutritions,
    getNutrition,
    deleteNutrition,

    createBloodGlucose,
    updateBloodGlucose,
    getAllBloodGlucoses,
    getBloodGlucose,
    deleteBloodGlucose,

    createBloodPressure,
    updateBloodPressure,
    getAllBloodPressures,
    getBloodPressure,
    deleteBloodPressure,

    createBodyFatPercentage,
    updateBodyFatPercentage,
    getAllBodyFatPercentages,
    getBodyFatPercentage,
    deleteBodyFatPercentage,

    createBodyTemperature,
    updateBodyTemperature,
    getAllBodyTemperatures,
    getBodyTemperature,
    deleteBodyTemperature,

    createHeartRateBpm,
    updateHeartRateBpm,
    getAllHeartRateBpms,
    getHeartRateBpm,
    deleteHeartRateBpm,

    createHeight,
    updateHeight,
    getAllHeights,
    getHeight,
    deleteHeight,

    createOxygenSaturation,
    updateOxygenSaturation,
    getAllOxygenSaturations,
    getOxygenSaturation,
    deleteOxygenSaturation,

    createSleepSegment,
    updateSleepSegment,
    getAllSleepSegments,
    getSleepSegment,
    deleteSleepSegment,

    createWeight,
    updateWeight,
    getAllWeights,
    getWeight,
    deleteWeight,

    createActivitySummary,
    updateActivitySummary,
    getAllActivitySummaries,
    getActivitySummary,
    deleteActivitySummary,

    createCaloriesBmrSummary,
    updateCaloriesBmrSummary,
    getAllCaloriesBmrSummaries,
    getCaloriesBmrSummary,
    deleteCaloriesBmrSummary,

    createPowerSummary,
    updatePowerSummary,
    getAllPowerSummaries,
    getPowerSummary,
    deletePowerSummary,

    createBodyFatPercentageSummary,
    updateBodyFatPercentageSummary,
    getAllBodyFatPercentageSummaries,
    getBodyFatPercentageSummary,
    deleteBodyFatPercentageSummary,

    createHeartRateSummary,
    updateHeartRateSummary,
    getAllHeartRateSummaries,
    getHeartRateSummary,
    deleteHeartRateSummary,

    createHeightSummary,
    updateHeightSummary,
    getAllHeightSummaries,
    getHeightSummary,
    deleteHeightSummary,

    createWeightSummary,
    updateWeightSummary,
    getAllWeightSummaries,
    getWeightSummary,
    deleteWeightSummary,

    createSpeedSummary,
    updateSpeedSummary,
    getAllSpeedSummaries,
    getSpeedSummary,
    deleteSpeedSummary,

    createNutritionSummary,
    updateNutritionSummary,
    getAllNutritionSummaries,
    getNutritionSummary,
    deleteNutritionSummary,

    createBloodGlucoseSummary,
    updateBloodGlucoseSummary,
    getAllBloodGlucoseSummaries,
    getBloodGlucoseSummary,
    deleteBloodGlucoseSummary,

    createBloodPressureSummary,
    updateBloodPressureSummary,
    getAllBloodPressureSummaries,
    getBloodPressureSummary,
    deleteBloodPressureSummary,

    createTemperatureSummary,
    updateTemperatureSummary,
    getAllTemperatureSummaries,
    getTemperatureSummary,
    deleteTemperatureSummary,

    createOxygenSaturationSummary,
    updateOxygenSaturationSummary,
    getAllOxygenSaturationSummaries,
    getOxygenSaturationSummary,
    deleteOxygenSaturationSummary,
    // jhipster-react-native-api-export-needle
    setAuthToken,
    removeAuthToken,
    login,
    register,
    forgotPassword,
    getAccount,
    updateAccount,
    changePassword,
  };
};

// let's return back our create method as the default.
export default {
  create,
};
