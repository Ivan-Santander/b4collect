import React, { createRef } from 'react';
import { ActivityIndicator, Text, View } from 'react-native';
import { connect } from 'react-redux';

import BloodGlucoseSummaryActions from './blood-glucose-summary.reducer';
import { KeyboardAwareScrollView } from 'react-native-keyboard-aware-scroll-view';
import FormButton from '../../../shared/components/form/jhi-form-button';
import FormField from '../../../shared/components/form/jhi-form-field';
import Form from '../../../shared/components/form/jhi-form';
import { useDidUpdateEffect } from '../../../shared/util/use-did-update-effect';
import styles from './blood-glucose-summary-styles';

function BloodGlucoseSummaryEditScreen(props) {
  const {
    getBloodGlucoseSummary,
    updateBloodGlucoseSummary,
    route,
    bloodGlucoseSummary,
    fetching,
    updating,
    errorUpdating,
    updateSuccess,
    navigation,
    reset,
  } = props;

  const [formValue, setFormValue] = React.useState();
  const [error, setError] = React.useState('');

  const isNewEntity = !(route.params && route.params.entityId);

  React.useEffect(() => {
    if (!isNewEntity) {
      getBloodGlucoseSummary(route.params.entityId);
    } else {
      reset();
    }
  }, [isNewEntity, getBloodGlucoseSummary, route, reset]);

  React.useEffect(() => {
    if (isNewEntity) {
      setFormValue(entityToFormValue({}));
    } else if (!fetching) {
      setFormValue(entityToFormValue(bloodGlucoseSummary));
    }
  }, [bloodGlucoseSummary, fetching, isNewEntity]);

  // fetch related entities
  React.useEffect(() => {}, []);

  useDidUpdateEffect(() => {
    if (updating === false) {
      if (errorUpdating) {
        setError(errorUpdating && errorUpdating.detail ? errorUpdating.detail : 'Something went wrong updating the entity');
      } else if (updateSuccess) {
        setError('');
        isNewEntity || !navigation.canGoBack()
          ? navigation.replace('BloodGlucoseSummaryDetail', { entityId: bloodGlucoseSummary?.id })
          : navigation.pop();
      }
    }
  }, [updateSuccess, errorUpdating, navigation]);

  const onSubmit = (data) => updateBloodGlucoseSummary(formValueToEntity(data));

  if (fetching) {
    return (
      <View style={styles.loading}>
        <ActivityIndicator size="large" />
      </View>
    );
  }

  const formRef = createRef();
  const usuarioIdRef = createRef();
  const empresaIdRef = createRef();
  const fieldAverageRef = createRef();
  const fieldMaxRef = createRef();
  const fieldMinRef = createRef();
  const intervalFoodRef = createRef();
  const mealTypeRef = createRef();
  const relationTemporalSleepRef = createRef();
  const sampleSourceRef = createRef();
  const startTimeRef = createRef();
  const endTimeRef = createRef();

  return (
    <View style={styles.container}>
      <KeyboardAwareScrollView
        enableResetScrollToCoords={false}
        testID="bloodGlucoseSummaryEditScrollView"
        keyboardShouldPersistTaps="handled"
        keyboardDismissMode="on-drag"
        contentContainerStyle={styles.paddedScrollView}>
        {!!error && <Text style={styles.errorText}>{error}</Text>}
        {formValue && (
          <Form initialValues={formValue} onSubmit={onSubmit} ref={formRef}>
            <FormField
              name="usuarioId"
              ref={usuarioIdRef}
              label="Usuario Id"
              placeholder="Enter Usuario Id"
              testID="usuarioIdInput"
              inputType="text"
              autoCapitalize="none"
              onSubmitEditing={() => empresaIdRef.current?.focus()}
            />
            <FormField
              name="empresaId"
              ref={empresaIdRef}
              label="Empresa Id"
              placeholder="Enter Empresa Id"
              testID="empresaIdInput"
              inputType="text"
              autoCapitalize="none"
              onSubmitEditing={() => fieldAverageRef.current?.focus()}
            />
            <FormField
              name="fieldAverage"
              ref={fieldAverageRef}
              label="Field Average"
              placeholder="Enter Field Average"
              testID="fieldAverageInput"
              inputType="number"
              onSubmitEditing={() => fieldMaxRef.current?.focus()}
            />
            <FormField
              name="fieldMax"
              ref={fieldMaxRef}
              label="Field Max"
              placeholder="Enter Field Max"
              testID="fieldMaxInput"
              inputType="number"
              onSubmitEditing={() => fieldMinRef.current?.focus()}
            />
            <FormField
              name="fieldMin"
              ref={fieldMinRef}
              label="Field Min"
              placeholder="Enter Field Min"
              testID="fieldMinInput"
              inputType="number"
              onSubmitEditing={() => intervalFoodRef.current?.focus()}
            />
            <FormField
              name="intervalFood"
              ref={intervalFoodRef}
              label="Interval Food"
              placeholder="Enter Interval Food"
              testID="intervalFoodInput"
              inputType="number"
              onSubmitEditing={() => mealTypeRef.current?.focus()}
            />
            <FormField
              name="mealType"
              ref={mealTypeRef}
              label="Meal Type"
              placeholder="Enter Meal Type"
              testID="mealTypeInput"
              onSubmitEditing={() => relationTemporalSleepRef.current?.focus()}
            />
            <FormField
              name="relationTemporalSleep"
              ref={relationTemporalSleepRef}
              label="Relation Temporal Sleep"
              placeholder="Enter Relation Temporal Sleep"
              testID="relationTemporalSleepInput"
              inputType="number"
              onSubmitEditing={() => sampleSourceRef.current?.focus()}
            />
            <FormField
              name="sampleSource"
              ref={sampleSourceRef}
              label="Sample Source"
              placeholder="Enter Sample Source"
              testID="sampleSourceInput"
              inputType="number"
              onSubmitEditing={() => startTimeRef.current?.focus()}
            />
            <FormField
              name="startTime"
              ref={startTimeRef}
              label="Start Time"
              placeholder="Enter Start Time"
              testID="startTimeInput"
              inputType="datetime"
              onSubmitEditing={() => endTimeRef.current?.focus()}
            />
            <FormField
              name="endTime"
              ref={endTimeRef}
              label="End Time"
              placeholder="Enter End Time"
              testID="endTimeInput"
              inputType="datetime"
            />

            <FormButton title={'Save'} testID={'submitButton'} />
          </Form>
        )}
      </KeyboardAwareScrollView>
    </View>
  );
}

// convenience methods for customizing the mapping of the entity to/from the form value
const entityToFormValue = (value) => {
  if (!value) {
    return {};
  }
  return {
    id: value.id ?? null,
    usuarioId: value.usuarioId ?? null,
    empresaId: value.empresaId ?? null,
    fieldAverage: value.fieldAverage ?? null,
    fieldMax: value.fieldMax ?? null,
    fieldMin: value.fieldMin ?? null,
    intervalFood: value.intervalFood ?? null,
    mealType: value.mealType ?? null,
    relationTemporalSleep: value.relationTemporalSleep ?? null,
    sampleSource: value.sampleSource ?? null,
    startTime: value.startTime ?? null,
    endTime: value.endTime ?? null,
  };
};
const formValueToEntity = (value) => {
  const entity = {
    id: value.id ?? null,
    usuarioId: value.usuarioId ?? null,
    empresaId: value.empresaId ?? null,
    fieldAverage: value.fieldAverage ?? null,
    fieldMax: value.fieldMax ?? null,
    fieldMin: value.fieldMin ?? null,
    intervalFood: value.intervalFood ?? null,
    mealType: value.mealType ?? null,
    relationTemporalSleep: value.relationTemporalSleep ?? null,
    sampleSource: value.sampleSource ?? null,
    startTime: value.startTime ?? null,
    endTime: value.endTime ?? null,
  };
  return entity;
};

const mapStateToProps = (state) => {
  return {
    bloodGlucoseSummary: state.bloodGlucoseSummaries.bloodGlucoseSummary,
    fetching: state.bloodGlucoseSummaries.fetchingOne,
    updating: state.bloodGlucoseSummaries.updating,
    updateSuccess: state.bloodGlucoseSummaries.updateSuccess,
    errorUpdating: state.bloodGlucoseSummaries.errorUpdating,
  };
};

const mapDispatchToProps = (dispatch) => {
  return {
    getBloodGlucoseSummary: (id) => dispatch(BloodGlucoseSummaryActions.bloodGlucoseSummaryRequest(id)),
    getAllBloodGlucoseSummaries: (options) => dispatch(BloodGlucoseSummaryActions.bloodGlucoseSummaryAllRequest(options)),
    updateBloodGlucoseSummary: (bloodGlucoseSummary) =>
      dispatch(BloodGlucoseSummaryActions.bloodGlucoseSummaryUpdateRequest(bloodGlucoseSummary)),
    reset: () => dispatch(BloodGlucoseSummaryActions.bloodGlucoseSummaryReset()),
  };
};

export default connect(mapStateToProps, mapDispatchToProps)(BloodGlucoseSummaryEditScreen);
