import * as React from 'react';
import { createStackNavigator, HeaderBackButton } from '@react-navigation/stack';
import { Ionicons } from '@expo/vector-icons';

import { DrawerButton } from './drawer/drawer-button';
import { navigate, goBackOrIfParamsOrDefault } from './nav-ref';

// import screens
import EntitiesScreen from '../modules/entities/entities-screen';
import CaloriesBMRScreen from '../modules/entities/calories-bmr/calories-bmr-screen';
import CaloriesBMRDetailScreen from '../modules/entities/calories-bmr/calories-bmr-detail-screen';
import CaloriesBMREditScreen from '../modules/entities/calories-bmr/calories-bmr-edit-screen';
import CaloriesExpendedScreen from '../modules/entities/calories-expended/calories-expended-screen';
import CaloriesExpendedDetailScreen from '../modules/entities/calories-expended/calories-expended-detail-screen';
import CaloriesExpendedEditScreen from '../modules/entities/calories-expended/calories-expended-edit-screen';
import CiclingPedalingCadenceScreen from '../modules/entities/cicling-pedaling-cadence/cicling-pedaling-cadence-screen';
import CiclingPedalingCadenceDetailScreen from '../modules/entities/cicling-pedaling-cadence/cicling-pedaling-cadence-detail-screen';
import CiclingPedalingCadenceEditScreen from '../modules/entities/cicling-pedaling-cadence/cicling-pedaling-cadence-edit-screen';
import HeartMinutesScreen from '../modules/entities/heart-minutes/heart-minutes-screen';
import HeartMinutesDetailScreen from '../modules/entities/heart-minutes/heart-minutes-detail-screen';
import HeartMinutesEditScreen from '../modules/entities/heart-minutes/heart-minutes-edit-screen';
import ActiveMinutesScreen from '../modules/entities/active-minutes/active-minutes-screen';
import ActiveMinutesDetailScreen from '../modules/entities/active-minutes/active-minutes-detail-screen';
import ActiveMinutesEditScreen from '../modules/entities/active-minutes/active-minutes-edit-screen';
import PoweSampleScreen from '../modules/entities/powe-sample/powe-sample-screen';
import PoweSampleDetailScreen from '../modules/entities/powe-sample/powe-sample-detail-screen';
import PoweSampleEditScreen from '../modules/entities/powe-sample/powe-sample-edit-screen';
import StepCountCadenceScreen from '../modules/entities/step-count-cadence/step-count-cadence-screen';
import StepCountCadenceDetailScreen from '../modules/entities/step-count-cadence/step-count-cadence-detail-screen';
import StepCountCadenceEditScreen from '../modules/entities/step-count-cadence/step-count-cadence-edit-screen';
import StepCountDeltaScreen from '../modules/entities/step-count-delta/step-count-delta-screen';
import StepCountDeltaDetailScreen from '../modules/entities/step-count-delta/step-count-delta-detail-screen';
import StepCountDeltaEditScreen from '../modules/entities/step-count-delta/step-count-delta-edit-screen';
import ActivityExerciseScreen from '../modules/entities/activity-exercise/activity-exercise-screen';
import ActivityExerciseDetailScreen from '../modules/entities/activity-exercise/activity-exercise-detail-screen';
import ActivityExerciseEditScreen from '../modules/entities/activity-exercise/activity-exercise-edit-screen';
import CyclingWheelRevolutionScreen from '../modules/entities/cycling-wheel-revolution/cycling-wheel-revolution-screen';
import CyclingWheelRevolutionDetailScreen from '../modules/entities/cycling-wheel-revolution/cycling-wheel-revolution-detail-screen';
import CyclingWheelRevolutionEditScreen from '../modules/entities/cycling-wheel-revolution/cycling-wheel-revolution-edit-screen';
import DistanceDeltaScreen from '../modules/entities/distance-delta/distance-delta-screen';
import DistanceDeltaDetailScreen from '../modules/entities/distance-delta/distance-delta-detail-screen';
import DistanceDeltaEditScreen from '../modules/entities/distance-delta/distance-delta-edit-screen';
import LocationSampleScreen from '../modules/entities/location-sample/location-sample-screen';
import LocationSampleDetailScreen from '../modules/entities/location-sample/location-sample-detail-screen';
import LocationSampleEditScreen from '../modules/entities/location-sample/location-sample-edit-screen';
import SpeedScreen from '../modules/entities/speed/speed-screen';
import SpeedDetailScreen from '../modules/entities/speed/speed-detail-screen';
import SpeedEditScreen from '../modules/entities/speed/speed-edit-screen';
import NutritionScreen from '../modules/entities/nutrition/nutrition-screen';
import NutritionDetailScreen from '../modules/entities/nutrition/nutrition-detail-screen';
import NutritionEditScreen from '../modules/entities/nutrition/nutrition-edit-screen';
import BloodGlucoseScreen from '../modules/entities/blood-glucose/blood-glucose-screen';
import BloodGlucoseDetailScreen from '../modules/entities/blood-glucose/blood-glucose-detail-screen';
import BloodGlucoseEditScreen from '../modules/entities/blood-glucose/blood-glucose-edit-screen';
import BloodPressureScreen from '../modules/entities/blood-pressure/blood-pressure-screen';
import BloodPressureDetailScreen from '../modules/entities/blood-pressure/blood-pressure-detail-screen';
import BloodPressureEditScreen from '../modules/entities/blood-pressure/blood-pressure-edit-screen';
import BodyFatPercentageScreen from '../modules/entities/body-fat-percentage/body-fat-percentage-screen';
import BodyFatPercentageDetailScreen from '../modules/entities/body-fat-percentage/body-fat-percentage-detail-screen';
import BodyFatPercentageEditScreen from '../modules/entities/body-fat-percentage/body-fat-percentage-edit-screen';
import BodyTemperatureScreen from '../modules/entities/body-temperature/body-temperature-screen';
import BodyTemperatureDetailScreen from '../modules/entities/body-temperature/body-temperature-detail-screen';
import BodyTemperatureEditScreen from '../modules/entities/body-temperature/body-temperature-edit-screen';
import HeartRateBpmScreen from '../modules/entities/heart-rate-bpm/heart-rate-bpm-screen';
import HeartRateBpmDetailScreen from '../modules/entities/heart-rate-bpm/heart-rate-bpm-detail-screen';
import HeartRateBpmEditScreen from '../modules/entities/heart-rate-bpm/heart-rate-bpm-edit-screen';
import HeightScreen from '../modules/entities/height/height-screen';
import HeightDetailScreen from '../modules/entities/height/height-detail-screen';
import HeightEditScreen from '../modules/entities/height/height-edit-screen';
import OxygenSaturationScreen from '../modules/entities/oxygen-saturation/oxygen-saturation-screen';
import OxygenSaturationDetailScreen from '../modules/entities/oxygen-saturation/oxygen-saturation-detail-screen';
import OxygenSaturationEditScreen from '../modules/entities/oxygen-saturation/oxygen-saturation-edit-screen';
import SleepSegmentScreen from '../modules/entities/sleep-segment/sleep-segment-screen';
import SleepSegmentDetailScreen from '../modules/entities/sleep-segment/sleep-segment-detail-screen';
import SleepSegmentEditScreen from '../modules/entities/sleep-segment/sleep-segment-edit-screen';
import WeightScreen from '../modules/entities/weight/weight-screen';
import WeightDetailScreen from '../modules/entities/weight/weight-detail-screen';
import WeightEditScreen from '../modules/entities/weight/weight-edit-screen';
import ActivitySummaryScreen from '../modules/entities/activity-summary/activity-summary-screen';
import ActivitySummaryDetailScreen from '../modules/entities/activity-summary/activity-summary-detail-screen';
import ActivitySummaryEditScreen from '../modules/entities/activity-summary/activity-summary-edit-screen';
import CaloriesBmrSummaryScreen from '../modules/entities/calories-bmr-summary/calories-bmr-summary-screen';
import CaloriesBmrSummaryDetailScreen from '../modules/entities/calories-bmr-summary/calories-bmr-summary-detail-screen';
import CaloriesBmrSummaryEditScreen from '../modules/entities/calories-bmr-summary/calories-bmr-summary-edit-screen';
import PowerSummaryScreen from '../modules/entities/power-summary/power-summary-screen';
import PowerSummaryDetailScreen from '../modules/entities/power-summary/power-summary-detail-screen';
import PowerSummaryEditScreen from '../modules/entities/power-summary/power-summary-edit-screen';
import BodyFatPercentageSummaryScreen from '../modules/entities/body-fat-percentage-summary/body-fat-percentage-summary-screen';
import BodyFatPercentageSummaryDetailScreen from '../modules/entities/body-fat-percentage-summary/body-fat-percentage-summary-detail-screen';
import BodyFatPercentageSummaryEditScreen from '../modules/entities/body-fat-percentage-summary/body-fat-percentage-summary-edit-screen';
import HeartRateSummaryScreen from '../modules/entities/heart-rate-summary/heart-rate-summary-screen';
import HeartRateSummaryDetailScreen from '../modules/entities/heart-rate-summary/heart-rate-summary-detail-screen';
import HeartRateSummaryEditScreen from '../modules/entities/heart-rate-summary/heart-rate-summary-edit-screen';
import HeightSummaryScreen from '../modules/entities/height-summary/height-summary-screen';
import HeightSummaryDetailScreen from '../modules/entities/height-summary/height-summary-detail-screen';
import HeightSummaryEditScreen from '../modules/entities/height-summary/height-summary-edit-screen';
import WeightSummaryScreen from '../modules/entities/weight-summary/weight-summary-screen';
import WeightSummaryDetailScreen from '../modules/entities/weight-summary/weight-summary-detail-screen';
import WeightSummaryEditScreen from '../modules/entities/weight-summary/weight-summary-edit-screen';
import SpeedSummaryScreen from '../modules/entities/speed-summary/speed-summary-screen';
import SpeedSummaryDetailScreen from '../modules/entities/speed-summary/speed-summary-detail-screen';
import SpeedSummaryEditScreen from '../modules/entities/speed-summary/speed-summary-edit-screen';
import NutritionSummaryScreen from '../modules/entities/nutrition-summary/nutrition-summary-screen';
import NutritionSummaryDetailScreen from '../modules/entities/nutrition-summary/nutrition-summary-detail-screen';
import NutritionSummaryEditScreen from '../modules/entities/nutrition-summary/nutrition-summary-edit-screen';
import BloodGlucoseSummaryScreen from '../modules/entities/blood-glucose-summary/blood-glucose-summary-screen';
import BloodGlucoseSummaryDetailScreen from '../modules/entities/blood-glucose-summary/blood-glucose-summary-detail-screen';
import BloodGlucoseSummaryEditScreen from '../modules/entities/blood-glucose-summary/blood-glucose-summary-edit-screen';
import BloodPressureSummaryScreen from '../modules/entities/blood-pressure-summary/blood-pressure-summary-screen';
import BloodPressureSummaryDetailScreen from '../modules/entities/blood-pressure-summary/blood-pressure-summary-detail-screen';
import BloodPressureSummaryEditScreen from '../modules/entities/blood-pressure-summary/blood-pressure-summary-edit-screen';
import TemperatureSummaryScreen from '../modules/entities/temperature-summary/temperature-summary-screen';
import TemperatureSummaryDetailScreen from '../modules/entities/temperature-summary/temperature-summary-detail-screen';
import TemperatureSummaryEditScreen from '../modules/entities/temperature-summary/temperature-summary-edit-screen';
import OxygenSaturationSummaryScreen from '../modules/entities/oxygen-saturation-summary/oxygen-saturation-summary-screen';
import OxygenSaturationSummaryDetailScreen from '../modules/entities/oxygen-saturation-summary/oxygen-saturation-summary-detail-screen';
import OxygenSaturationSummaryEditScreen from '../modules/entities/oxygen-saturation-summary/oxygen-saturation-summary-edit-screen';
// jhipster-react-native-navigation-import-needle

export const entityScreens = [
  {
    name: 'Medidas',
    route: '',
    component: EntitiesScreen,
    options: {
      headerLeft: DrawerButton,
    },
  },
  {
    name: 'CaloriesBMR',
    route: 'calories-bmr',
    component: CaloriesBMRScreen,
    options: {
      title: 'CaloriesBMRS',
      headerLeft: () => <HeaderBackButton onPress={() => navigate('Entities')} />,
      headerRight: () => (
        <HeaderBackButton
          label=" New "
          onPress={() => navigate('CaloriesBMREdit', { id: undefined })}
          backImage={(props) => <Ionicons name="md-add-circle-outline" size={32} color={props.tintColor} />}
        />
      ),
    },
  },
  {
    name: 'CaloriesBMRDetail',
    route: 'calories-bmr/detail',
    component: CaloriesBMRDetailScreen,
    options: { title: 'View CaloriesBMR', headerLeft: () => <HeaderBackButton onPress={() => navigate('CaloriesBMR')} /> },
  },
  {
    name: 'CaloriesBMREdit',
    route: 'calories-bmr/edit',
    component: CaloriesBMREditScreen,
    options: {
      title: 'Edit CaloriesBMR',
      headerLeft: () => <HeaderBackButton onPress={() => goBackOrIfParamsOrDefault('CaloriesBMRDetail', 'CaloriesBMR')} />,
    },
  },
  {
    name: 'CaloriesExpended',
    route: 'calories-expended',
    component: CaloriesExpendedScreen,
    options: {
      title: 'CaloriesExpendeds',
      headerLeft: () => <HeaderBackButton onPress={() => navigate('Entities')} />,
      headerRight: () => (
        <HeaderBackButton
          label=" New "
          onPress={() => navigate('CaloriesExpendedEdit', { id: undefined })}
          backImage={(props) => <Ionicons name="md-add-circle-outline" size={32} color={props.tintColor} />}
        />
      ),
    },
  },
  {
    name: 'CaloriesExpendedDetail',
    route: 'calories-expended/detail',
    component: CaloriesExpendedDetailScreen,
    options: { title: 'View CaloriesExpended', headerLeft: () => <HeaderBackButton onPress={() => navigate('CaloriesExpended')} /> },
  },
  {
    name: 'CaloriesExpendedEdit',
    route: 'calories-expended/edit',
    component: CaloriesExpendedEditScreen,
    options: {
      title: 'Edit CaloriesExpended',
      headerLeft: () => <HeaderBackButton onPress={() => goBackOrIfParamsOrDefault('CaloriesExpendedDetail', 'CaloriesExpended')} />,
    },
  },
  {
    name: 'CiclingPedalingCadence',
    route: 'cicling-pedaling-cadence',
    component: CiclingPedalingCadenceScreen,
    options: {
      title: 'CiclingPedalingCadences',
      headerLeft: () => <HeaderBackButton onPress={() => navigate('Entities')} />,
      headerRight: () => (
        <HeaderBackButton
          label=" New "
          onPress={() => navigate('CiclingPedalingCadenceEdit', { id: undefined })}
          backImage={(props) => <Ionicons name="md-add-circle-outline" size={32} color={props.tintColor} />}
        />
      ),
    },
  },
  {
    name: 'CiclingPedalingCadenceDetail',
    route: 'cicling-pedaling-cadence/detail',
    component: CiclingPedalingCadenceDetailScreen,
    options: {
      title: 'View CiclingPedalingCadence',
      headerLeft: () => <HeaderBackButton onPress={() => navigate('CiclingPedalingCadence')} />,
    },
  },
  {
    name: 'CiclingPedalingCadenceEdit',
    route: 'cicling-pedaling-cadence/edit',
    component: CiclingPedalingCadenceEditScreen,
    options: {
      title: 'Edit CiclingPedalingCadence',
      headerLeft: () => (
        <HeaderBackButton onPress={() => goBackOrIfParamsOrDefault('CiclingPedalingCadenceDetail', 'CiclingPedalingCadence')} />
      ),
    },
  },
  {
    name: 'HeartMinutes',
    route: 'heart-minutes',
    component: HeartMinutesScreen,
    options: {
      title: 'HeartMinutes',
      headerLeft: () => <HeaderBackButton onPress={() => navigate('Entities')} />,
      headerRight: () => (
        <HeaderBackButton
          label=" New "
          onPress={() => navigate('HeartMinutesEdit', { id: undefined })}
          backImage={(props) => <Ionicons name="md-add-circle-outline" size={32} color={props.tintColor} />}
        />
      ),
    },
  },
  {
    name: 'HeartMinutesDetail',
    route: 'heart-minutes/detail',
    component: HeartMinutesDetailScreen,
    options: { title: 'View HeartMinutes', headerLeft: () => <HeaderBackButton onPress={() => navigate('HeartMinutes')} /> },
  },
  {
    name: 'HeartMinutesEdit',
    route: 'heart-minutes/edit',
    component: HeartMinutesEditScreen,
    options: {
      title: 'Edit HeartMinutes',
      headerLeft: () => <HeaderBackButton onPress={() => goBackOrIfParamsOrDefault('HeartMinutesDetail', 'HeartMinutes')} />,
    },
  },
  {
    name: 'ActiveMinutes',
    route: 'active-minutes',
    component: ActiveMinutesScreen,
    options: {
      title: 'ActiveMinutes',
      headerLeft: () => <HeaderBackButton onPress={() => navigate('Entities')} />,
      headerRight: () => (
        <HeaderBackButton
          label=" New "
          onPress={() => navigate('ActiveMinutesEdit', { id: undefined })}
          backImage={(props) => <Ionicons name="md-add-circle-outline" size={32} color={props.tintColor} />}
        />
      ),
    },
  },
  {
    name: 'ActiveMinutesDetail',
    route: 'active-minutes/detail',
    component: ActiveMinutesDetailScreen,
    options: { title: 'View ActiveMinutes', headerLeft: () => <HeaderBackButton onPress={() => navigate('ActiveMinutes')} /> },
  },
  {
    name: 'ActiveMinutesEdit',
    route: 'active-minutes/edit',
    component: ActiveMinutesEditScreen,
    options: {
      title: 'Edit ActiveMinutes',
      headerLeft: () => <HeaderBackButton onPress={() => goBackOrIfParamsOrDefault('ActiveMinutesDetail', 'ActiveMinutes')} />,
    },
  },
  {
    name: 'PoweSample',
    route: 'powe-sample',
    component: PoweSampleScreen,
    options: {
      title: 'PoweSamples',
      headerLeft: () => <HeaderBackButton onPress={() => navigate('Entities')} />,
      headerRight: () => (
        <HeaderBackButton
          label=" New "
          onPress={() => navigate('PoweSampleEdit', { id: undefined })}
          backImage={(props) => <Ionicons name="md-add-circle-outline" size={32} color={props.tintColor} />}
        />
      ),
    },
  },
  {
    name: 'PoweSampleDetail',
    route: 'powe-sample/detail',
    component: PoweSampleDetailScreen,
    options: { title: 'View PoweSample', headerLeft: () => <HeaderBackButton onPress={() => navigate('PoweSample')} /> },
  },
  {
    name: 'PoweSampleEdit',
    route: 'powe-sample/edit',
    component: PoweSampleEditScreen,
    options: {
      title: 'Edit PoweSample',
      headerLeft: () => <HeaderBackButton onPress={() => goBackOrIfParamsOrDefault('PoweSampleDetail', 'PoweSample')} />,
    },
  },
  {
    name: 'StepCountCadence',
    route: 'step-count-cadence',
    component: StepCountCadenceScreen,
    options: {
      title: 'StepCountCadences',
      headerLeft: () => <HeaderBackButton onPress={() => navigate('Entities')} />,
      headerRight: () => (
        <HeaderBackButton
          label=" New "
          onPress={() => navigate('StepCountCadenceEdit', { id: undefined })}
          backImage={(props) => <Ionicons name="md-add-circle-outline" size={32} color={props.tintColor} />}
        />
      ),
    },
  },
  {
    name: 'StepCountCadenceDetail',
    route: 'step-count-cadence/detail',
    component: StepCountCadenceDetailScreen,
    options: { title: 'View StepCountCadence', headerLeft: () => <HeaderBackButton onPress={() => navigate('StepCountCadence')} /> },
  },
  {
    name: 'StepCountCadenceEdit',
    route: 'step-count-cadence/edit',
    component: StepCountCadenceEditScreen,
    options: {
      title: 'Edit StepCountCadence',
      headerLeft: () => <HeaderBackButton onPress={() => goBackOrIfParamsOrDefault('StepCountCadenceDetail', 'StepCountCadence')} />,
    },
  },
  {
    name: 'StepCountDelta',
    route: 'step-count-delta',
    component: StepCountDeltaScreen,
    options: {
      title: 'StepCountDeltas',
      headerLeft: () => <HeaderBackButton onPress={() => navigate('Entities')} />,
      headerRight: () => (
        <HeaderBackButton
          label=" New "
          onPress={() => navigate('StepCountDeltaEdit', { id: undefined })}
          backImage={(props) => <Ionicons name="md-add-circle-outline" size={32} color={props.tintColor} />}
        />
      ),
    },
  },
  {
    name: 'StepCountDeltaDetail',
    route: 'step-count-delta/detail',
    component: StepCountDeltaDetailScreen,
    options: { title: 'View StepCountDelta', headerLeft: () => <HeaderBackButton onPress={() => navigate('StepCountDelta')} /> },
  },
  {
    name: 'StepCountDeltaEdit',
    route: 'step-count-delta/edit',
    component: StepCountDeltaEditScreen,
    options: {
      title: 'Edit StepCountDelta',
      headerLeft: () => <HeaderBackButton onPress={() => goBackOrIfParamsOrDefault('StepCountDeltaDetail', 'StepCountDelta')} />,
    },
  },
  {
    name: 'ActivityExercise',
    route: 'activity-exercise',
    component: ActivityExerciseScreen,
    options: {
      title: 'ActivityExercises',
      headerLeft: () => <HeaderBackButton onPress={() => navigate('Entities')} />,
      headerRight: () => (
        <HeaderBackButton
          label=" New "
          onPress={() => navigate('ActivityExerciseEdit', { id: undefined })}
          backImage={(props) => <Ionicons name="md-add-circle-outline" size={32} color={props.tintColor} />}
        />
      ),
    },
  },
  {
    name: 'ActivityExerciseDetail',
    route: 'activity-exercise/detail',
    component: ActivityExerciseDetailScreen,
    options: { title: 'View ActivityExercise', headerLeft: () => <HeaderBackButton onPress={() => navigate('ActivityExercise')} /> },
  },
  {
    name: 'ActivityExerciseEdit',
    route: 'activity-exercise/edit',
    component: ActivityExerciseEditScreen,
    options: {
      title: 'Edit ActivityExercise',
      headerLeft: () => <HeaderBackButton onPress={() => goBackOrIfParamsOrDefault('ActivityExerciseDetail', 'ActivityExercise')} />,
    },
  },
  {
    name: 'CyclingWheelRevolution',
    route: 'cycling-wheel-revolution',
    component: CyclingWheelRevolutionScreen,
    options: {
      title: 'CyclingWheelRevolutions',
      headerLeft: () => <HeaderBackButton onPress={() => navigate('Entities')} />,
      headerRight: () => (
        <HeaderBackButton
          label=" New "
          onPress={() => navigate('CyclingWheelRevolutionEdit', { id: undefined })}
          backImage={(props) => <Ionicons name="md-add-circle-outline" size={32} color={props.tintColor} />}
        />
      ),
    },
  },
  {
    name: 'CyclingWheelRevolutionDetail',
    route: 'cycling-wheel-revolution/detail',
    component: CyclingWheelRevolutionDetailScreen,
    options: {
      title: 'View CyclingWheelRevolution',
      headerLeft: () => <HeaderBackButton onPress={() => navigate('CyclingWheelRevolution')} />,
    },
  },
  {
    name: 'CyclingWheelRevolutionEdit',
    route: 'cycling-wheel-revolution/edit',
    component: CyclingWheelRevolutionEditScreen,
    options: {
      title: 'Edit CyclingWheelRevolution',
      headerLeft: () => (
        <HeaderBackButton onPress={() => goBackOrIfParamsOrDefault('CyclingWheelRevolutionDetail', 'CyclingWheelRevolution')} />
      ),
    },
  },
  {
    name: 'DistanceDelta',
    route: 'distance-delta',
    component: DistanceDeltaScreen,
    options: {
      title: 'DistanceDeltas',
      headerLeft: () => <HeaderBackButton onPress={() => navigate('Entities')} />,
      headerRight: () => (
        <HeaderBackButton
          label=" New "
          onPress={() => navigate('DistanceDeltaEdit', { id: undefined })}
          backImage={(props) => <Ionicons name="md-add-circle-outline" size={32} color={props.tintColor} />}
        />
      ),
    },
  },
  {
    name: 'DistanceDeltaDetail',
    route: 'distance-delta/detail',
    component: DistanceDeltaDetailScreen,
    options: { title: 'View DistanceDelta', headerLeft: () => <HeaderBackButton onPress={() => navigate('DistanceDelta')} /> },
  },
  {
    name: 'DistanceDeltaEdit',
    route: 'distance-delta/edit',
    component: DistanceDeltaEditScreen,
    options: {
      title: 'Edit DistanceDelta',
      headerLeft: () => <HeaderBackButton onPress={() => goBackOrIfParamsOrDefault('DistanceDeltaDetail', 'DistanceDelta')} />,
    },
  },
  {
    name: 'LocationSample',
    route: 'location-sample',
    component: LocationSampleScreen,
    options: {
      title: 'LocationSamples',
      headerLeft: () => <HeaderBackButton onPress={() => navigate('Entities')} />,
      headerRight: () => (
        <HeaderBackButton
          label=" New "
          onPress={() => navigate('LocationSampleEdit', { id: undefined })}
          backImage={(props) => <Ionicons name="md-add-circle-outline" size={32} color={props.tintColor} />}
        />
      ),
    },
  },
  {
    name: 'LocationSampleDetail',
    route: 'location-sample/detail',
    component: LocationSampleDetailScreen,
    options: { title: 'View LocationSample', headerLeft: () => <HeaderBackButton onPress={() => navigate('LocationSample')} /> },
  },
  {
    name: 'LocationSampleEdit',
    route: 'location-sample/edit',
    component: LocationSampleEditScreen,
    options: {
      title: 'Edit LocationSample',
      headerLeft: () => <HeaderBackButton onPress={() => goBackOrIfParamsOrDefault('LocationSampleDetail', 'LocationSample')} />,
    },
  },
  {
    name: 'Speed',
    route: 'speed',
    component: SpeedScreen,
    options: {
      title: 'Speeds',
      headerLeft: () => <HeaderBackButton onPress={() => navigate('Entities')} />,
      headerRight: () => (
        <HeaderBackButton
          label=" New "
          onPress={() => navigate('SpeedEdit', { id: undefined })}
          backImage={(props) => <Ionicons name="md-add-circle-outline" size={32} color={props.tintColor} />}
        />
      ),
    },
  },
  {
    name: 'SpeedDetail',
    route: 'speed/detail',
    component: SpeedDetailScreen,
    options: { title: 'View Speed', headerLeft: () => <HeaderBackButton onPress={() => navigate('Speed')} /> },
  },
  {
    name: 'SpeedEdit',
    route: 'speed/edit',
    component: SpeedEditScreen,
    options: {
      title: 'Edit Speed',
      headerLeft: () => <HeaderBackButton onPress={() => goBackOrIfParamsOrDefault('SpeedDetail', 'Speed')} />,
    },
  },
  {
    name: 'Nutrition',
    route: 'nutrition',
    component: NutritionScreen,
    options: {
      title: 'Nutritions',
      headerLeft: () => <HeaderBackButton onPress={() => navigate('Entities')} />,
      headerRight: () => (
        <HeaderBackButton
          label=" New "
          onPress={() => navigate('NutritionEdit', { id: undefined })}
          backImage={(props) => <Ionicons name="md-add-circle-outline" size={32} color={props.tintColor} />}
        />
      ),
    },
  },
  {
    name: 'NutritionDetail',
    route: 'nutrition/detail',
    component: NutritionDetailScreen,
    options: { title: 'View Nutrition', headerLeft: () => <HeaderBackButton onPress={() => navigate('Nutrition')} /> },
  },
  {
    name: 'NutritionEdit',
    route: 'nutrition/edit',
    component: NutritionEditScreen,
    options: {
      title: 'Edit Nutrition',
      headerLeft: () => <HeaderBackButton onPress={() => goBackOrIfParamsOrDefault('NutritionDetail', 'Nutrition')} />,
    },
  },
  {
    name: 'BloodGlucose',
    route: 'blood-glucose',
    component: BloodGlucoseScreen,
    options: {
      title: 'BloodGlucoses',
      headerLeft: () => <HeaderBackButton onPress={() => navigate('Entities')} />,
      headerRight: () => (
        <HeaderBackButton
          label=" New "
          onPress={() => navigate('BloodGlucoseEdit', { id: undefined })}
          backImage={(props) => <Ionicons name="md-add-circle-outline" size={32} color={props.tintColor} />}
        />
      ),
    },
  },
  {
    name: 'BloodGlucoseDetail',
    route: 'blood-glucose/detail',
    component: BloodGlucoseDetailScreen,
    options: { title: 'View BloodGlucose', headerLeft: () => <HeaderBackButton onPress={() => navigate('BloodGlucose')} /> },
  },
  {
    name: 'BloodGlucoseEdit',
    route: 'blood-glucose/edit',
    component: BloodGlucoseEditScreen,
    options: {
      title: 'Edit BloodGlucose',
      headerLeft: () => <HeaderBackButton onPress={() => goBackOrIfParamsOrDefault('BloodGlucoseDetail', 'BloodGlucose')} />,
    },
  },
  {
    name: 'BloodPressure',
    route: 'blood-pressure',
    component: BloodPressureScreen,
    options: {
      title: 'BloodPressures',
      headerLeft: () => <HeaderBackButton onPress={() => navigate('Entities')} />,
      headerRight: () => (
        <HeaderBackButton
          label=" New "
          onPress={() => navigate('BloodPressureEdit', { id: undefined })}
          backImage={(props) => <Ionicons name="md-add-circle-outline" size={32} color={props.tintColor} />}
        />
      ),
    },
  },
  {
    name: 'BloodPressureDetail',
    route: 'blood-pressure/detail',
    component: BloodPressureDetailScreen,
    options: { title: 'View BloodPressure', headerLeft: () => <HeaderBackButton onPress={() => navigate('BloodPressure')} /> },
  },
  {
    name: 'BloodPressureEdit',
    route: 'blood-pressure/edit',
    component: BloodPressureEditScreen,
    options: {
      title: 'Edit BloodPressure',
      headerLeft: () => <HeaderBackButton onPress={() => goBackOrIfParamsOrDefault('BloodPressureDetail', 'BloodPressure')} />,
    },
  },
  {
    name: 'BodyFatPercentage',
    route: 'body-fat-percentage',
    component: BodyFatPercentageScreen,
    options: {
      title: 'BodyFatPercentages',
      headerLeft: () => <HeaderBackButton onPress={() => navigate('Entities')} />,
      headerRight: () => (
        <HeaderBackButton
          label=" New "
          onPress={() => navigate('BodyFatPercentageEdit', { id: undefined })}
          backImage={(props) => <Ionicons name="md-add-circle-outline" size={32} color={props.tintColor} />}
        />
      ),
    },
  },
  {
    name: 'BodyFatPercentageDetail',
    route: 'body-fat-percentage/detail',
    component: BodyFatPercentageDetailScreen,
    options: { title: 'View BodyFatPercentage', headerLeft: () => <HeaderBackButton onPress={() => navigate('BodyFatPercentage')} /> },
  },
  {
    name: 'BodyFatPercentageEdit',
    route: 'body-fat-percentage/edit',
    component: BodyFatPercentageEditScreen,
    options: {
      title: 'Edit BodyFatPercentage',
      headerLeft: () => <HeaderBackButton onPress={() => goBackOrIfParamsOrDefault('BodyFatPercentageDetail', 'BodyFatPercentage')} />,
    },
  },
  {
    name: 'BodyTemperature',
    route: 'body-temperature',
    component: BodyTemperatureScreen,
    options: {
      title: 'BodyTemperatures',
      headerLeft: () => <HeaderBackButton onPress={() => navigate('Entities')} />,
      headerRight: () => (
        <HeaderBackButton
          label=" New "
          onPress={() => navigate('BodyTemperatureEdit', { id: undefined })}
          backImage={(props) => <Ionicons name="md-add-circle-outline" size={32} color={props.tintColor} />}
        />
      ),
    },
  },
  {
    name: 'BodyTemperatureDetail',
    route: 'body-temperature/detail',
    component: BodyTemperatureDetailScreen,
    options: { title: 'View BodyTemperature', headerLeft: () => <HeaderBackButton onPress={() => navigate('BodyTemperature')} /> },
  },
  {
    name: 'BodyTemperatureEdit',
    route: 'body-temperature/edit',
    component: BodyTemperatureEditScreen,
    options: {
      title: 'Edit BodyTemperature',
      headerLeft: () => <HeaderBackButton onPress={() => goBackOrIfParamsOrDefault('BodyTemperatureDetail', 'BodyTemperature')} />,
    },
  },
  {
    name: 'HeartRateBpm',
    route: 'heart-rate-bpm',
    component: HeartRateBpmScreen,
    options: {
      title: 'HeartRateBpms',
      headerLeft: () => <HeaderBackButton onPress={() => navigate('Entities')} />,
      headerRight: () => (
        <HeaderBackButton
          label=" New "
          onPress={() => navigate('HeartRateBpmEdit', { id: undefined })}
          backImage={(props) => <Ionicons name="md-add-circle-outline" size={32} color={props.tintColor} />}
        />
      ),
    },
  },
  {
    name: 'HeartRateBpmDetail',
    route: 'heart-rate-bpm/detail',
    component: HeartRateBpmDetailScreen,
    options: { title: 'View HeartRateBpm', headerLeft: () => <HeaderBackButton onPress={() => navigate('HeartRateBpm')} /> },
  },
  {
    name: 'HeartRateBpmEdit',
    route: 'heart-rate-bpm/edit',
    component: HeartRateBpmEditScreen,
    options: {
      title: 'Edit HeartRateBpm',
      headerLeft: () => <HeaderBackButton onPress={() => goBackOrIfParamsOrDefault('HeartRateBpmDetail', 'HeartRateBpm')} />,
    },
  },
  {
    name: 'Height',
    route: 'height',
    component: HeightScreen,
    options: {
      title: 'Heights',
      headerLeft: () => <HeaderBackButton onPress={() => navigate('Entities')} />,
      headerRight: () => (
        <HeaderBackButton
          label=" New "
          onPress={() => navigate('HeightEdit', { id: undefined })}
          backImage={(props) => <Ionicons name="md-add-circle-outline" size={32} color={props.tintColor} />}
        />
      ),
    },
  },
  {
    name: 'HeightDetail',
    route: 'height/detail',
    component: HeightDetailScreen,
    options: { title: 'View Height', headerLeft: () => <HeaderBackButton onPress={() => navigate('Height')} /> },
  },
  {
    name: 'HeightEdit',
    route: 'height/edit',
    component: HeightEditScreen,
    options: {
      title: 'Edit Height',
      headerLeft: () => <HeaderBackButton onPress={() => goBackOrIfParamsOrDefault('HeightDetail', 'Height')} />,
    },
  },
  {
    name: 'OxygenSaturation',
    route: 'oxygen-saturation',
    component: OxygenSaturationScreen,
    options: {
      title: 'OxygenSaturations',
      headerLeft: () => <HeaderBackButton onPress={() => navigate('Entities')} />,
      headerRight: () => (
        <HeaderBackButton
          label=" New "
          onPress={() => navigate('OxygenSaturationEdit', { id: undefined })}
          backImage={(props) => <Ionicons name="md-add-circle-outline" size={32} color={props.tintColor} />}
        />
      ),
    },
  },
  {
    name: 'OxygenSaturationDetail',
    route: 'oxygen-saturation/detail',
    component: OxygenSaturationDetailScreen,
    options: { title: 'View OxygenSaturation', headerLeft: () => <HeaderBackButton onPress={() => navigate('OxygenSaturation')} /> },
  },
  {
    name: 'OxygenSaturationEdit',
    route: 'oxygen-saturation/edit',
    component: OxygenSaturationEditScreen,
    options: {
      title: 'Edit OxygenSaturation',
      headerLeft: () => <HeaderBackButton onPress={() => goBackOrIfParamsOrDefault('OxygenSaturationDetail', 'OxygenSaturation')} />,
    },
  },
  {
    name: 'SleepSegment',
    route: 'sleep-segment',
    component: SleepSegmentScreen,
    options: {
      title: 'SleepSegments',
      headerLeft: () => <HeaderBackButton onPress={() => navigate('Entities')} />,
      headerRight: () => (
        <HeaderBackButton
          label=" New "
          onPress={() => navigate('SleepSegmentEdit', { id: undefined })}
          backImage={(props) => <Ionicons name="md-add-circle-outline" size={32} color={props.tintColor} />}
        />
      ),
    },
  },
  {
    name: 'SleepSegmentDetail',
    route: 'sleep-segment/detail',
    component: SleepSegmentDetailScreen,
    options: { title: 'View SleepSegment', headerLeft: () => <HeaderBackButton onPress={() => navigate('SleepSegment')} /> },
  },
  {
    name: 'SleepSegmentEdit',
    route: 'sleep-segment/edit',
    component: SleepSegmentEditScreen,
    options: {
      title: 'Edit SleepSegment',
      headerLeft: () => <HeaderBackButton onPress={() => goBackOrIfParamsOrDefault('SleepSegmentDetail', 'SleepSegment')} />,
    },
  },
  {
    name: 'Weight',
    route: 'weight',
    component: WeightScreen,
    options: {
      title: 'Weights',
      headerLeft: () => <HeaderBackButton onPress={() => navigate('Entities')} />,
      headerRight: () => (
        <HeaderBackButton
          label=" New "
          onPress={() => navigate('WeightEdit', { id: undefined })}
          backImage={(props) => <Ionicons name="md-add-circle-outline" size={32} color={props.tintColor} />}
        />
      ),
    },
  },
  {
    name: 'WeightDetail',
    route: 'weight/detail',
    component: WeightDetailScreen,
    options: { title: 'View Weight', headerLeft: () => <HeaderBackButton onPress={() => navigate('Weight')} /> },
  },
  {
    name: 'WeightEdit',
    route: 'weight/edit',
    component: WeightEditScreen,
    options: {
      title: 'Edit Weight',
      headerLeft: () => <HeaderBackButton onPress={() => goBackOrIfParamsOrDefault('WeightDetail', 'Weight')} />,
    },
  },
  {
    name: 'ActivitySummary',
    route: 'activity-summary',
    component: ActivitySummaryScreen,
    options: {
      title: 'ActivitySummaries',
      headerLeft: () => <HeaderBackButton onPress={() => navigate('Entities')} />,
      headerRight: () => (
        <HeaderBackButton
          label=" New "
          onPress={() => navigate('ActivitySummaryEdit', { id: undefined })}
          backImage={(props) => <Ionicons name="md-add-circle-outline" size={32} color={props.tintColor} />}
        />
      ),
    },
  },
  {
    name: 'ActivitySummaryDetail',
    route: 'activity-summary/detail',
    component: ActivitySummaryDetailScreen,
    options: { title: 'View ActivitySummary', headerLeft: () => <HeaderBackButton onPress={() => navigate('ActivitySummary')} /> },
  },
  {
    name: 'ActivitySummaryEdit',
    route: 'activity-summary/edit',
    component: ActivitySummaryEditScreen,
    options: {
      title: 'Edit ActivitySummary',
      headerLeft: () => <HeaderBackButton onPress={() => goBackOrIfParamsOrDefault('ActivitySummaryDetail', 'ActivitySummary')} />,
    },
  },
  {
    name: 'CaloriesBmrSummary',
    route: 'calories-bmr-summary',
    component: CaloriesBmrSummaryScreen,
    options: {
      title: 'CaloriesBmrSummaries',
      headerLeft: () => <HeaderBackButton onPress={() => navigate('Entities')} />,
      headerRight: () => (
        <HeaderBackButton
          label=" New "
          onPress={() => navigate('CaloriesBmrSummaryEdit', { id: undefined })}
          backImage={(props) => <Ionicons name="md-add-circle-outline" size={32} color={props.tintColor} />}
        />
      ),
    },
  },
  {
    name: 'CaloriesBmrSummaryDetail',
    route: 'calories-bmr-summary/detail',
    component: CaloriesBmrSummaryDetailScreen,
    options: { title: 'View CaloriesBmrSummary', headerLeft: () => <HeaderBackButton onPress={() => navigate('CaloriesBmrSummary')} /> },
  },
  {
    name: 'CaloriesBmrSummaryEdit',
    route: 'calories-bmr-summary/edit',
    component: CaloriesBmrSummaryEditScreen,
    options: {
      title: 'Edit CaloriesBmrSummary',
      headerLeft: () => <HeaderBackButton onPress={() => goBackOrIfParamsOrDefault('CaloriesBmrSummaryDetail', 'CaloriesBmrSummary')} />,
    },
  },
  {
    name: 'PowerSummary',
    route: 'power-summary',
    component: PowerSummaryScreen,
    options: {
      title: 'PowerSummaries',
      headerLeft: () => <HeaderBackButton onPress={() => navigate('Entities')} />,
      headerRight: () => (
        <HeaderBackButton
          label=" New "
          onPress={() => navigate('PowerSummaryEdit', { id: undefined })}
          backImage={(props) => <Ionicons name="md-add-circle-outline" size={32} color={props.tintColor} />}
        />
      ),
    },
  },
  {
    name: 'PowerSummaryDetail',
    route: 'power-summary/detail',
    component: PowerSummaryDetailScreen,
    options: { title: 'View PowerSummary', headerLeft: () => <HeaderBackButton onPress={() => navigate('PowerSummary')} /> },
  },
  {
    name: 'PowerSummaryEdit',
    route: 'power-summary/edit',
    component: PowerSummaryEditScreen,
    options: {
      title: 'Edit PowerSummary',
      headerLeft: () => <HeaderBackButton onPress={() => goBackOrIfParamsOrDefault('PowerSummaryDetail', 'PowerSummary')} />,
    },
  },
  {
    name: 'BodyFatPercentageSummary',
    route: 'body-fat-percentage-summary',
    component: BodyFatPercentageSummaryScreen,
    options: {
      title: 'BodyFatPercentageSummaries',
      headerLeft: () => <HeaderBackButton onPress={() => navigate('Entities')} />,
      headerRight: () => (
        <HeaderBackButton
          label=" New "
          onPress={() => navigate('BodyFatPercentageSummaryEdit', { id: undefined })}
          backImage={(props) => <Ionicons name="md-add-circle-outline" size={32} color={props.tintColor} />}
        />
      ),
    },
  },
  {
    name: 'BodyFatPercentageSummaryDetail',
    route: 'body-fat-percentage-summary/detail',
    component: BodyFatPercentageSummaryDetailScreen,
    options: {
      title: 'View BodyFatPercentageSummary',
      headerLeft: () => <HeaderBackButton onPress={() => navigate('BodyFatPercentageSummary')} />,
    },
  },
  {
    name: 'BodyFatPercentageSummaryEdit',
    route: 'body-fat-percentage-summary/edit',
    component: BodyFatPercentageSummaryEditScreen,
    options: {
      title: 'Edit BodyFatPercentageSummary',
      headerLeft: () => (
        <HeaderBackButton onPress={() => goBackOrIfParamsOrDefault('BodyFatPercentageSummaryDetail', 'BodyFatPercentageSummary')} />
      ),
    },
  },
  {
    name: 'HeartRateSummary',
    route: 'heart-rate-summary',
    component: HeartRateSummaryScreen,
    options: {
      title: 'HeartRateSummaries',
      headerLeft: () => <HeaderBackButton onPress={() => navigate('Entities')} />,
      headerRight: () => (
        <HeaderBackButton
          label=" New "
          onPress={() => navigate('HeartRateSummaryEdit', { id: undefined })}
          backImage={(props) => <Ionicons name="md-add-circle-outline" size={32} color={props.tintColor} />}
        />
      ),
    },
  },
  {
    name: 'HeartRateSummaryDetail',
    route: 'heart-rate-summary/detail',
    component: HeartRateSummaryDetailScreen,
    options: { title: 'View HeartRateSummary', headerLeft: () => <HeaderBackButton onPress={() => navigate('HeartRateSummary')} /> },
  },
  {
    name: 'HeartRateSummaryEdit',
    route: 'heart-rate-summary/edit',
    component: HeartRateSummaryEditScreen,
    options: {
      title: 'Edit HeartRateSummary',
      headerLeft: () => <HeaderBackButton onPress={() => goBackOrIfParamsOrDefault('HeartRateSummaryDetail', 'HeartRateSummary')} />,
    },
  },
  {
    name: 'HeightSummary',
    route: 'height-summary',
    component: HeightSummaryScreen,
    options: {
      title: 'HeightSummaries',
      headerLeft: () => <HeaderBackButton onPress={() => navigate('Entities')} />,
      headerRight: () => (
        <HeaderBackButton
          label=" New "
          onPress={() => navigate('HeightSummaryEdit', { id: undefined })}
          backImage={(props) => <Ionicons name="md-add-circle-outline" size={32} color={props.tintColor} />}
        />
      ),
    },
  },
  {
    name: 'HeightSummaryDetail',
    route: 'height-summary/detail',
    component: HeightSummaryDetailScreen,
    options: { title: 'View HeightSummary', headerLeft: () => <HeaderBackButton onPress={() => navigate('HeightSummary')} /> },
  },
  {
    name: 'HeightSummaryEdit',
    route: 'height-summary/edit',
    component: HeightSummaryEditScreen,
    options: {
      title: 'Edit HeightSummary',
      headerLeft: () => <HeaderBackButton onPress={() => goBackOrIfParamsOrDefault('HeightSummaryDetail', 'HeightSummary')} />,
    },
  },
  {
    name: 'WeightSummary',
    route: 'weight-summary',
    component: WeightSummaryScreen,
    options: {
      title: 'WeightSummaries',
      headerLeft: () => <HeaderBackButton onPress={() => navigate('Entities')} />,
      headerRight: () => (
        <HeaderBackButton
          label=" New "
          onPress={() => navigate('WeightSummaryEdit', { id: undefined })}
          backImage={(props) => <Ionicons name="md-add-circle-outline" size={32} color={props.tintColor} />}
        />
      ),
    },
  },
  {
    name: 'WeightSummaryDetail',
    route: 'weight-summary/detail',
    component: WeightSummaryDetailScreen,
    options: { title: 'View WeightSummary', headerLeft: () => <HeaderBackButton onPress={() => navigate('WeightSummary')} /> },
  },
  {
    name: 'WeightSummaryEdit',
    route: 'weight-summary/edit',
    component: WeightSummaryEditScreen,
    options: {
      title: 'Edit WeightSummary',
      headerLeft: () => <HeaderBackButton onPress={() => goBackOrIfParamsOrDefault('WeightSummaryDetail', 'WeightSummary')} />,
    },
  },
  {
    name: 'SpeedSummary',
    route: 'speed-summary',
    component: SpeedSummaryScreen,
    options: {
      title: 'SpeedSummaries',
      headerLeft: () => <HeaderBackButton onPress={() => navigate('Entities')} />,
      headerRight: () => (
        <HeaderBackButton
          label=" New "
          onPress={() => navigate('SpeedSummaryEdit', { id: undefined })}
          backImage={(props) => <Ionicons name="md-add-circle-outline" size={32} color={props.tintColor} />}
        />
      ),
    },
  },
  {
    name: 'SpeedSummaryDetail',
    route: 'speed-summary/detail',
    component: SpeedSummaryDetailScreen,
    options: { title: 'View SpeedSummary', headerLeft: () => <HeaderBackButton onPress={() => navigate('SpeedSummary')} /> },
  },
  {
    name: 'SpeedSummaryEdit',
    route: 'speed-summary/edit',
    component: SpeedSummaryEditScreen,
    options: {
      title: 'Edit SpeedSummary',
      headerLeft: () => <HeaderBackButton onPress={() => goBackOrIfParamsOrDefault('SpeedSummaryDetail', 'SpeedSummary')} />,
    },
  },
  {
    name: 'NutritionSummary',
    route: 'nutrition-summary',
    component: NutritionSummaryScreen,
    options: {
      title: 'NutritionSummaries',
      headerLeft: () => <HeaderBackButton onPress={() => navigate('Entities')} />,
      headerRight: () => (
        <HeaderBackButton
          label=" New "
          onPress={() => navigate('NutritionSummaryEdit', { id: undefined })}
          backImage={(props) => <Ionicons name="md-add-circle-outline" size={32} color={props.tintColor} />}
        />
      ),
    },
  },
  {
    name: 'NutritionSummaryDetail',
    route: 'nutrition-summary/detail',
    component: NutritionSummaryDetailScreen,
    options: { title: 'View NutritionSummary', headerLeft: () => <HeaderBackButton onPress={() => navigate('NutritionSummary')} /> },
  },
  {
    name: 'NutritionSummaryEdit',
    route: 'nutrition-summary/edit',
    component: NutritionSummaryEditScreen,
    options: {
      title: 'Edit NutritionSummary',
      headerLeft: () => <HeaderBackButton onPress={() => goBackOrIfParamsOrDefault('NutritionSummaryDetail', 'NutritionSummary')} />,
    },
  },
  {
    name: 'BloodGlucoseSummary',
    route: 'blood-glucose-summary',
    component: BloodGlucoseSummaryScreen,
    options: {
      title: 'BloodGlucoseSummaries',
      headerLeft: () => <HeaderBackButton onPress={() => navigate('Entities')} />,
      headerRight: () => (
        <HeaderBackButton
          label=" New "
          onPress={() => navigate('BloodGlucoseSummaryEdit', { id: undefined })}
          backImage={(props) => <Ionicons name="md-add-circle-outline" size={32} color={props.tintColor} />}
        />
      ),
    },
  },
  {
    name: 'BloodGlucoseSummaryDetail',
    route: 'blood-glucose-summary/detail',
    component: BloodGlucoseSummaryDetailScreen,
    options: { title: 'View BloodGlucoseSummary', headerLeft: () => <HeaderBackButton onPress={() => navigate('BloodGlucoseSummary')} /> },
  },
  {
    name: 'BloodGlucoseSummaryEdit',
    route: 'blood-glucose-summary/edit',
    component: BloodGlucoseSummaryEditScreen,
    options: {
      title: 'Edit BloodGlucoseSummary',
      headerLeft: () => <HeaderBackButton onPress={() => goBackOrIfParamsOrDefault('BloodGlucoseSummaryDetail', 'BloodGlucoseSummary')} />,
    },
  },
  {
    name: 'BloodPressureSummary',
    route: 'blood-pressure-summary',
    component: BloodPressureSummaryScreen,
    options: {
      title: 'BloodPressureSummaries',
      headerLeft: () => <HeaderBackButton onPress={() => navigate('Entities')} />,
      headerRight: () => (
        <HeaderBackButton
          label=" New "
          onPress={() => navigate('BloodPressureSummaryEdit', { id: undefined })}
          backImage={(props) => <Ionicons name="md-add-circle-outline" size={32} color={props.tintColor} />}
        />
      ),
    },
  },
  {
    name: 'BloodPressureSummaryDetail',
    route: 'blood-pressure-summary/detail',
    component: BloodPressureSummaryDetailScreen,
    options: {
      title: 'View BloodPressureSummary',
      headerLeft: () => <HeaderBackButton onPress={() => navigate('BloodPressureSummary')} />,
    },
  },
  {
    name: 'BloodPressureSummaryEdit',
    route: 'blood-pressure-summary/edit',
    component: BloodPressureSummaryEditScreen,
    options: {
      title: 'Edit BloodPressureSummary',
      headerLeft: () => (
        <HeaderBackButton onPress={() => goBackOrIfParamsOrDefault('BloodPressureSummaryDetail', 'BloodPressureSummary')} />
      ),
    },
  },
  {
    name: 'TemperatureSummary',
    route: 'temperature-summary',
    component: TemperatureSummaryScreen,
    options: {
      title: 'TemperatureSummaries',
      headerLeft: () => <HeaderBackButton onPress={() => navigate('Entities')} />,
      headerRight: () => (
        <HeaderBackButton
          label=" New "
          onPress={() => navigate('TemperatureSummaryEdit', { id: undefined })}
          backImage={(props) => <Ionicons name="md-add-circle-outline" size={32} color={props.tintColor} />}
        />
      ),
    },
  },
  {
    name: 'TemperatureSummaryDetail',
    route: 'temperature-summary/detail',
    component: TemperatureSummaryDetailScreen,
    options: { title: 'View TemperatureSummary', headerLeft: () => <HeaderBackButton onPress={() => navigate('TemperatureSummary')} /> },
  },
  {
    name: 'TemperatureSummaryEdit',
    route: 'temperature-summary/edit',
    component: TemperatureSummaryEditScreen,
    options: {
      title: 'Edit TemperatureSummary',
      headerLeft: () => <HeaderBackButton onPress={() => goBackOrIfParamsOrDefault('TemperatureSummaryDetail', 'TemperatureSummary')} />,
    },
  },
  {
    name: 'OxygenSaturationSummary',
    route: 'oxygen-saturation-summary',
    component: OxygenSaturationSummaryScreen,
    options: {
      title: 'OxygenSaturationSummaries',
      headerLeft: () => <HeaderBackButton onPress={() => navigate('Entities')} />,
      headerRight: () => (
        <HeaderBackButton
          label=" New "
          onPress={() => navigate('OxygenSaturationSummaryEdit', { id: undefined })}
          backImage={(props) => <Ionicons name="md-add-circle-outline" size={32} color={props.tintColor} />}
        />
      ),
    },
  },
  {
    name: 'OxygenSaturationSummaryDetail',
    route: 'oxygen-saturation-summary/detail',
    component: OxygenSaturationSummaryDetailScreen,
    options: {
      title: 'View OxygenSaturationSummary',
      headerLeft: () => <HeaderBackButton onPress={() => navigate('OxygenSaturationSummary')} />,
    },
  },
  {
    name: 'OxygenSaturationSummaryEdit',
    route: 'oxygen-saturation-summary/edit',
    component: OxygenSaturationSummaryEditScreen,
    options: {
      title: 'Edit OxygenSaturationSummary',
      headerLeft: () => (
        <HeaderBackButton onPress={() => goBackOrIfParamsOrDefault('OxygenSaturationSummaryDetail', 'OxygenSaturationSummary')} />
      ),
    },
  },
  // jhipster-react-native-navigation-declaration-needle
];

export const getEntityRoutes = () => {
  const routes = {};
  entityScreens.forEach((screen) => {
    routes[screen.name] = screen.route;
  });
  return routes;
};

const EntityStack = createStackNavigator();

export default function EntityStackScreen() {
  return (
    <EntityStack.Navigator>
      {entityScreens.map((screen, index) => {
        return <EntityStack.Screen name={screen.name} component={screen.component} key={index} options={screen.options} />;
      })}
    </EntityStack.Navigator>
  );
}
