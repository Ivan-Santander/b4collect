import React from 'react';
import { Translate } from 'react-jhipster';

import MenuItem from 'app/shared/layout/menus/menu-item';

const EntitiesMenu = () => {
  return (
    <>
      {/* prettier-ignore */}
      <MenuItem icon="asterisk" to="/calories-bmr">
        <Translate contentKey="global.menu.entities.caloriesBmr" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/calories-expended">
        <Translate contentKey="global.menu.entities.caloriesExpended" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/cicling-pedaling-cadence">
        <Translate contentKey="global.menu.entities.ciclingPedalingCadence" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/heart-minutes">
        <Translate contentKey="global.menu.entities.heartMinutes" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/active-minutes">
        <Translate contentKey="global.menu.entities.activeMinutes" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/powe-sample">
        <Translate contentKey="global.menu.entities.poweSample" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/step-count-cadence">
        <Translate contentKey="global.menu.entities.stepCountCadence" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/step-count-delta">
        <Translate contentKey="global.menu.entities.stepCountDelta" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/activity-exercise">
        <Translate contentKey="global.menu.entities.activityExercise" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/cycling-wheel-revolution">
        <Translate contentKey="global.menu.entities.cyclingWheelRevolution" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/distance-delta">
        <Translate contentKey="global.menu.entities.distanceDelta" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/location-sample">
        <Translate contentKey="global.menu.entities.locationSample" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/speed">
        <Translate contentKey="global.menu.entities.speed" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/nutrition">
        <Translate contentKey="global.menu.entities.nutrition" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/blood-glucose">
        <Translate contentKey="global.menu.entities.bloodGlucose" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/blood-pressure">
        <Translate contentKey="global.menu.entities.bloodPressure" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/body-fat-percentage">
        <Translate contentKey="global.menu.entities.bodyFatPercentage" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/body-temperature">
        <Translate contentKey="global.menu.entities.bodyTemperature" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/heart-rate-bpm">
        <Translate contentKey="global.menu.entities.heartRateBpm" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/height">
        <Translate contentKey="global.menu.entities.height" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/oxygen-saturation">
        <Translate contentKey="global.menu.entities.oxygenSaturation" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/sleep-segment">
        <Translate contentKey="global.menu.entities.sleepSegment" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/weight">
        <Translate contentKey="global.menu.entities.weight" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/activity-summary">
        <Translate contentKey="global.menu.entities.activitySummary" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/calories-bmr-summary">
        <Translate contentKey="global.menu.entities.caloriesBmrSummary" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/power-summary">
        <Translate contentKey="global.menu.entities.powerSummary" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/body-fat-percentage-summary">
        <Translate contentKey="global.menu.entities.bodyFatPercentageSummary" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/heart-rate-summary">
        <Translate contentKey="global.menu.entities.heartRateSummary" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/height-summary">
        <Translate contentKey="global.menu.entities.heightSummary" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/weight-summary">
        <Translate contentKey="global.menu.entities.weightSummary" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/speed-summary">
        <Translate contentKey="global.menu.entities.speedSummary" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/nutrition-summary">
        <Translate contentKey="global.menu.entities.nutritionSummary" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/blood-glucose-summary">
        <Translate contentKey="global.menu.entities.bloodGlucoseSummary" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/blood-pressure-summary">
        <Translate contentKey="global.menu.entities.bloodPressureSummary" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/temperature-summary">
        <Translate contentKey="global.menu.entities.temperatureSummary" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/oxygen-saturation-summary">
        <Translate contentKey="global.menu.entities.oxygenSaturationSummary" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/funtional-index">
        <Translate contentKey="global.menu.entities.funtionalIndex" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/user-demographics">
        <Translate contentKey="global.menu.entities.userDemographics" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/user-medical-info">
        <Translate contentKey="global.menu.entities.userMedicalInfo" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/user-body-info">
        <Translate contentKey="global.menu.entities.userBodyInfo" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/mental-health">
        <Translate contentKey="global.menu.entities.mentalHealth" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/alarm-risk-index-summary">
        <Translate contentKey="global.menu.entities.alarmRiskIndexSummary" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/sleep-scores">
        <Translate contentKey="global.menu.entities.sleepScores" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/funtional-index-summary">
        <Translate contentKey="global.menu.entities.funtionalIndexSummary" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/mental-health-summary">
        <Translate contentKey="global.menu.entities.mentalHealthSummary" />
      </MenuItem>
      {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
    </>
  );
};

export default EntitiesMenu;
