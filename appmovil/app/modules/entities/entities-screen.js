import React from 'react';
import { ScrollView, Text } from 'react-native';
// Styles
import RoundedButton from '../../shared/components/rounded-button/rounded-button';

import styles from './entities-screen.styles';

export default function EntitiesScreen({ navigation }) {
  return (
    <ScrollView style={styles.container} contentContainerStyle={styles.paddedScrollView} testID="entityScreenScrollList">
      <Text style={styles.centerText}>Consulte las medidas almacenadas en nuestro servicio</Text>
      <RoundedButton text="CaloriesBMR" onPress={() => navigation.navigate('CaloriesBMR')} testID="caloriesBMREntityScreenButton" />
      <RoundedButton
        text="CaloriesExpended"
        onPress={() => navigation.navigate('CaloriesExpended')}
        testID="caloriesExpendedEntityScreenButton"
      />
      <RoundedButton
        text="CiclingPedalingCadence"
        onPress={() => navigation.navigate('CiclingPedalingCadence')}
        testID="ciclingPedalingCadenceEntityScreenButton"
      />
      <RoundedButton text="HeartMinutes" onPress={() => navigation.navigate('HeartMinutes')} testID="heartMinutesEntityScreenButton" />
      <RoundedButton text="ActiveMinutes" onPress={() => navigation.navigate('ActiveMinutes')} testID="activeMinutesEntityScreenButton" />
      <RoundedButton text="PoweSample" onPress={() => navigation.navigate('PoweSample')} testID="poweSampleEntityScreenButton" />
      <RoundedButton
        text="StepCountCadence"
        onPress={() => navigation.navigate('StepCountCadence')}
        testID="stepCountCadenceEntityScreenButton"
      />
      <RoundedButton
        text="StepCountDelta"
        onPress={() => navigation.navigate('StepCountDelta')}
        testID="stepCountDeltaEntityScreenButton"
      />
      <RoundedButton
        text="ActivityExercise"
        onPress={() => navigation.navigate('ActivityExercise')}
        testID="activityExerciseEntityScreenButton"
      />
      <RoundedButton
        text="CyclingWheelRevolution"
        onPress={() => navigation.navigate('CyclingWheelRevolution')}
        testID="cyclingWheelRevolutionEntityScreenButton"
      />
      <RoundedButton text="DistanceDelta" onPress={() => navigation.navigate('DistanceDelta')} testID="distanceDeltaEntityScreenButton" />
      <RoundedButton
        text="LocationSample"
        onPress={() => navigation.navigate('LocationSample')}
        testID="locationSampleEntityScreenButton"
      />
      <RoundedButton text="Speed" onPress={() => navigation.navigate('Speed')} testID="speedEntityScreenButton" />
      <RoundedButton text="Nutrition" onPress={() => navigation.navigate('Nutrition')} testID="nutritionEntityScreenButton" />
      <RoundedButton text="BloodGlucose" onPress={() => navigation.navigate('BloodGlucose')} testID="bloodGlucoseEntityScreenButton" />
      <RoundedButton text="BloodPressure" onPress={() => navigation.navigate('BloodPressure')} testID="bloodPressureEntityScreenButton" />
      <RoundedButton
        text="BodyFatPercentage"
        onPress={() => navigation.navigate('BodyFatPercentage')}
        testID="bodyFatPercentageEntityScreenButton"
      />
      <RoundedButton
        text="BodyTemperature"
        onPress={() => navigation.navigate('BodyTemperature')}
        testID="bodyTemperatureEntityScreenButton"
      />
      <RoundedButton text="HeartRateBpm" onPress={() => navigation.navigate('HeartRateBpm')} testID="heartRateBpmEntityScreenButton" />
      <RoundedButton text="Height" onPress={() => navigation.navigate('Height')} testID="heightEntityScreenButton" />
      <RoundedButton
        text="OxygenSaturation"
        onPress={() => navigation.navigate('OxygenSaturation')}
        testID="oxygenSaturationEntityScreenButton"
      />
      <RoundedButton text="SleepSegment" onPress={() => navigation.navigate('SleepSegment')} testID="sleepSegmentEntityScreenButton" />
      <RoundedButton text="Weight" onPress={() => navigation.navigate('Weight')} testID="weightEntityScreenButton" />
      <RoundedButton
        text="ActivitySummary"
        onPress={() => navigation.navigate('ActivitySummary')}
        testID="activitySummaryEntityScreenButton"
      />
      <RoundedButton
        text="CaloriesBmrSummary"
        onPress={() => navigation.navigate('CaloriesBmrSummary')}
        testID="caloriesBmrSummaryEntityScreenButton"
      />
      <RoundedButton text="PowerSummary" onPress={() => navigation.navigate('PowerSummary')} testID="powerSummaryEntityScreenButton" />
      <RoundedButton
        text="BodyFatPercentageSummary"
        onPress={() => navigation.navigate('BodyFatPercentageSummary')}
        testID="bodyFatPercentageSummaryEntityScreenButton"
      />
      <RoundedButton
        text="HeartRateSummary"
        onPress={() => navigation.navigate('HeartRateSummary')}
        testID="heartRateSummaryEntityScreenButton"
      />
      <RoundedButton text="HeightSummary" onPress={() => navigation.navigate('HeightSummary')} testID="heightSummaryEntityScreenButton" />
      <RoundedButton text="WeightSummary" onPress={() => navigation.navigate('WeightSummary')} testID="weightSummaryEntityScreenButton" />
      <RoundedButton text="SpeedSummary" onPress={() => navigation.navigate('SpeedSummary')} testID="speedSummaryEntityScreenButton" />
      <RoundedButton
        text="NutritionSummary"
        onPress={() => navigation.navigate('NutritionSummary')}
        testID="nutritionSummaryEntityScreenButton"
      />
      <RoundedButton
        text="BloodGlucoseSummary"
        onPress={() => navigation.navigate('BloodGlucoseSummary')}
        testID="bloodGlucoseSummaryEntityScreenButton"
      />
      <RoundedButton
        text="BloodPressureSummary"
        onPress={() => navigation.navigate('BloodPressureSummary')}
        testID="bloodPressureSummaryEntityScreenButton"
      />
      <RoundedButton
        text="TemperatureSummary"
        onPress={() => navigation.navigate('TemperatureSummary')}
        testID="temperatureSummaryEntityScreenButton"
      />
      <RoundedButton
        text="OxygenSaturationSummary"
        onPress={() => navigation.navigate('OxygenSaturationSummary')}
        testID="oxygenSaturationSummaryEntityScreenButton"
      />
      {/* jhipster-react-native-entity-screen-needle */}
    </ScrollView>
  );
}
