import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import CaloriesBMR from './calories-bmr';
import CaloriesExpended from './calories-expended';
import CiclingPedalingCadence from './cicling-pedaling-cadence';
import HeartMinutes from './heart-minutes';
import ActiveMinutes from './active-minutes';
import PoweSample from './powe-sample';
import StepCountCadence from './step-count-cadence';
import StepCountDelta from './step-count-delta';
import ActivityExercise from './activity-exercise';
import CyclingWheelRevolution from './cycling-wheel-revolution';
import DistanceDelta from './distance-delta';
import LocationSample from './location-sample';
import Speed from './speed';
import Nutrition from './nutrition';
import BloodGlucose from './blood-glucose';
import BloodPressure from './blood-pressure';
import BodyFatPercentage from './body-fat-percentage';
import BodyTemperature from './body-temperature';
import HeartRateBpm from './heart-rate-bpm';
import Height from './height';
import OxygenSaturation from './oxygen-saturation';
import SleepSegment from './sleep-segment';
import Weight from './weight';
import ActivitySummary from './activity-summary';
import CaloriesBmrSummary from './calories-bmr-summary';
import PowerSummary from './power-summary';
import BodyFatPercentageSummary from './body-fat-percentage-summary';
import HeartRateSummary from './heart-rate-summary';
import HeightSummary from './height-summary';
import WeightSummary from './weight-summary';
import SpeedSummary from './speed-summary';
import NutritionSummary from './nutrition-summary';
import BloodGlucoseSummary from './blood-glucose-summary';
import BloodPressureSummary from './blood-pressure-summary';
import TemperatureSummary from './temperature-summary';
import OxygenSaturationSummary from './oxygen-saturation-summary';
import FuntionalIndex from './funtional-index';
import UserDemographics from './user-demographics';
import UserMedicalInfo from './user-medical-info';
import UserBodyInfo from './user-body-info';
import MentalHealth from './mental-health';
import AlarmRiskIndexSummary from './alarm-risk-index-summary';
import SleepScores from './sleep-scores';
import FuntionalIndexSummary from './funtional-index-summary';
import MentalHealthSummary from './mental-health-summary';
/* jhipster-needle-add-route-import - JHipster will add routes here */

export default () => {
  return (
    <div>
      <ErrorBoundaryRoutes>
        {/* prettier-ignore */}
        <Route path="calories-bmr/*" element={<CaloriesBMR />} />
        <Route path="calories-expended/*" element={<CaloriesExpended />} />
        <Route path="cicling-pedaling-cadence/*" element={<CiclingPedalingCadence />} />
        <Route path="heart-minutes/*" element={<HeartMinutes />} />
        <Route path="active-minutes/*" element={<ActiveMinutes />} />
        <Route path="powe-sample/*" element={<PoweSample />} />
        <Route path="step-count-cadence/*" element={<StepCountCadence />} />
        <Route path="step-count-delta/*" element={<StepCountDelta />} />
        <Route path="activity-exercise/*" element={<ActivityExercise />} />
        <Route path="cycling-wheel-revolution/*" element={<CyclingWheelRevolution />} />
        <Route path="distance-delta/*" element={<DistanceDelta />} />
        <Route path="location-sample/*" element={<LocationSample />} />
        <Route path="speed/*" element={<Speed />} />
        <Route path="nutrition/*" element={<Nutrition />} />
        <Route path="blood-glucose/*" element={<BloodGlucose />} />
        <Route path="blood-pressure/*" element={<BloodPressure />} />
        <Route path="body-fat-percentage/*" element={<BodyFatPercentage />} />
        <Route path="body-temperature/*" element={<BodyTemperature />} />
        <Route path="heart-rate-bpm/*" element={<HeartRateBpm />} />
        <Route path="height/*" element={<Height />} />
        <Route path="oxygen-saturation/*" element={<OxygenSaturation />} />
        <Route path="sleep-segment/*" element={<SleepSegment />} />
        <Route path="weight/*" element={<Weight />} />
        <Route path="activity-summary/*" element={<ActivitySummary />} />
        <Route path="calories-bmr-summary/*" element={<CaloriesBmrSummary />} />
        <Route path="power-summary/*" element={<PowerSummary />} />
        <Route path="body-fat-percentage-summary/*" element={<BodyFatPercentageSummary />} />
        <Route path="heart-rate-summary/*" element={<HeartRateSummary />} />
        <Route path="height-summary/*" element={<HeightSummary />} />
        <Route path="weight-summary/*" element={<WeightSummary />} />
        <Route path="speed-summary/*" element={<SpeedSummary />} />
        <Route path="nutrition-summary/*" element={<NutritionSummary />} />
        <Route path="blood-glucose-summary/*" element={<BloodGlucoseSummary />} />
        <Route path="blood-pressure-summary/*" element={<BloodPressureSummary />} />
        <Route path="temperature-summary/*" element={<TemperatureSummary />} />
        <Route path="oxygen-saturation-summary/*" element={<OxygenSaturationSummary />} />
        <Route path="funtional-index/*" element={<FuntionalIndex />} />
        <Route path="user-demographics/*" element={<UserDemographics />} />
        <Route path="user-medical-info/*" element={<UserMedicalInfo />} />
        <Route path="user-body-info/*" element={<UserBodyInfo />} />
        <Route path="mental-health/*" element={<MentalHealth />} />
        <Route path="alarm-risk-index-summary/*" element={<AlarmRiskIndexSummary />} />
        <Route path="sleep-scores/*" element={<SleepScores />} />
        <Route path="funtional-index-summary/*" element={<FuntionalIndexSummary />} />
        <Route path="mental-health-summary/*" element={<MentalHealthSummary />} />
        {/* jhipster-needle-add-route-path - JHipster will add routes here */}
      </ErrorBoundaryRoutes>
    </div>
  );
};
