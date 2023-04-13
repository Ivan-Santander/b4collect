import React, { createRef } from 'react';
import { ActivityIndicator, Text, View } from 'react-native';
import { connect } from 'react-redux';

import BloodPressureSummaryActions from './blood-pressure-summary.reducer';
import { KeyboardAwareScrollView } from 'react-native-keyboard-aware-scroll-view';
import FormButton from '../../../shared/components/form/jhi-form-button';
import FormField from '../../../shared/components/form/jhi-form-field';
import Form from '../../../shared/components/form/jhi-form';
import { useDidUpdateEffect } from '../../../shared/util/use-did-update-effect';
import styles from './blood-pressure-summary-styles';

function BloodPressureSummaryEditScreen(props) {
  const {
    getBloodPressureSummary,
    updateBloodPressureSummary,
    route,
    bloodPressureSummary,
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
      getBloodPressureSummary(route.params.entityId);
    } else {
      reset();
    }
  }, [isNewEntity, getBloodPressureSummary, route, reset]);

  React.useEffect(() => {
    if (isNewEntity) {
      setFormValue(entityToFormValue({}));
    } else if (!fetching) {
      setFormValue(entityToFormValue(bloodPressureSummary));
    }
  }, [bloodPressureSummary, fetching, isNewEntity]);

  // fetch related entities
  React.useEffect(() => {}, []);

  useDidUpdateEffect(() => {
    if (updating === false) {
      if (errorUpdating) {
        setError(errorUpdating && errorUpdating.detail ? errorUpdating.detail : 'Something went wrong updating the entity');
      } else if (updateSuccess) {
        setError('');
        isNewEntity || !navigation.canGoBack()
          ? navigation.replace('BloodPressureSummaryDetail', { entityId: bloodPressureSummary?.id })
          : navigation.pop();
      }
    }
  }, [updateSuccess, errorUpdating, navigation]);

  const onSubmit = (data) => updateBloodPressureSummary(formValueToEntity(data));

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
  const fieldSistolicAverageRef = createRef();
  const fieldSistolicMaxRef = createRef();
  const fieldSistolicMinRef = createRef();
  const fieldDiasatolicAverageRef = createRef();
  const fieldDiastolicMaxRef = createRef();
  const fieldDiastolicMinRef = createRef();
  const bodyPositionRef = createRef();
  const measurementLocationRef = createRef();
  const startTimeRef = createRef();
  const endTimeRef = createRef();

  return (
    <View style={styles.container}>
      <KeyboardAwareScrollView
        enableResetScrollToCoords={false}
        testID="bloodPressureSummaryEditScrollView"
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
              onSubmitEditing={() => fieldSistolicAverageRef.current?.focus()}
            />
            <FormField
              name="fieldSistolicAverage"
              ref={fieldSistolicAverageRef}
              label="Field Sistolic Average"
              placeholder="Enter Field Sistolic Average"
              testID="fieldSistolicAverageInput"
              inputType="number"
              onSubmitEditing={() => fieldSistolicMaxRef.current?.focus()}
            />
            <FormField
              name="fieldSistolicMax"
              ref={fieldSistolicMaxRef}
              label="Field Sistolic Max"
              placeholder="Enter Field Sistolic Max"
              testID="fieldSistolicMaxInput"
              inputType="number"
              onSubmitEditing={() => fieldSistolicMinRef.current?.focus()}
            />
            <FormField
              name="fieldSistolicMin"
              ref={fieldSistolicMinRef}
              label="Field Sistolic Min"
              placeholder="Enter Field Sistolic Min"
              testID="fieldSistolicMinInput"
              inputType="number"
              onSubmitEditing={() => fieldDiasatolicAverageRef.current?.focus()}
            />
            <FormField
              name="fieldDiasatolicAverage"
              ref={fieldDiasatolicAverageRef}
              label="Field Diasatolic Average"
              placeholder="Enter Field Diasatolic Average"
              testID="fieldDiasatolicAverageInput"
              inputType="number"
              onSubmitEditing={() => fieldDiastolicMaxRef.current?.focus()}
            />
            <FormField
              name="fieldDiastolicMax"
              ref={fieldDiastolicMaxRef}
              label="Field Diastolic Max"
              placeholder="Enter Field Diastolic Max"
              testID="fieldDiastolicMaxInput"
              inputType="number"
              onSubmitEditing={() => fieldDiastolicMinRef.current?.focus()}
            />
            <FormField
              name="fieldDiastolicMin"
              ref={fieldDiastolicMinRef}
              label="Field Diastolic Min"
              placeholder="Enter Field Diastolic Min"
              testID="fieldDiastolicMinInput"
              inputType="number"
              onSubmitEditing={() => bodyPositionRef.current?.focus()}
            />
            <FormField
              name="bodyPosition"
              ref={bodyPositionRef}
              label="Body Position"
              placeholder="Enter Body Position"
              testID="bodyPositionInput"
              inputType="number"
              onSubmitEditing={() => measurementLocationRef.current?.focus()}
            />
            <FormField
              name="measurementLocation"
              ref={measurementLocationRef}
              label="Measurement Location"
              placeholder="Enter Measurement Location"
              testID="measurementLocationInput"
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
    fieldSistolicAverage: value.fieldSistolicAverage ?? null,
    fieldSistolicMax: value.fieldSistolicMax ?? null,
    fieldSistolicMin: value.fieldSistolicMin ?? null,
    fieldDiasatolicAverage: value.fieldDiasatolicAverage ?? null,
    fieldDiastolicMax: value.fieldDiastolicMax ?? null,
    fieldDiastolicMin: value.fieldDiastolicMin ?? null,
    bodyPosition: value.bodyPosition ?? null,
    measurementLocation: value.measurementLocation ?? null,
    startTime: value.startTime ?? null,
    endTime: value.endTime ?? null,
  };
};
const formValueToEntity = (value) => {
  const entity = {
    id: value.id ?? null,
    usuarioId: value.usuarioId ?? null,
    empresaId: value.empresaId ?? null,
    fieldSistolicAverage: value.fieldSistolicAverage ?? null,
    fieldSistolicMax: value.fieldSistolicMax ?? null,
    fieldSistolicMin: value.fieldSistolicMin ?? null,
    fieldDiasatolicAverage: value.fieldDiasatolicAverage ?? null,
    fieldDiastolicMax: value.fieldDiastolicMax ?? null,
    fieldDiastolicMin: value.fieldDiastolicMin ?? null,
    bodyPosition: value.bodyPosition ?? null,
    measurementLocation: value.measurementLocation ?? null,
    startTime: value.startTime ?? null,
    endTime: value.endTime ?? null,
  };
  return entity;
};

const mapStateToProps = (state) => {
  return {
    bloodPressureSummary: state.bloodPressureSummaries.bloodPressureSummary,
    fetching: state.bloodPressureSummaries.fetchingOne,
    updating: state.bloodPressureSummaries.updating,
    updateSuccess: state.bloodPressureSummaries.updateSuccess,
    errorUpdating: state.bloodPressureSummaries.errorUpdating,
  };
};

const mapDispatchToProps = (dispatch) => {
  return {
    getBloodPressureSummary: (id) => dispatch(BloodPressureSummaryActions.bloodPressureSummaryRequest(id)),
    getAllBloodPressureSummaries: (options) => dispatch(BloodPressureSummaryActions.bloodPressureSummaryAllRequest(options)),
    updateBloodPressureSummary: (bloodPressureSummary) =>
      dispatch(BloodPressureSummaryActions.bloodPressureSummaryUpdateRequest(bloodPressureSummary)),
    reset: () => dispatch(BloodPressureSummaryActions.bloodPressureSummaryReset()),
  };
};

export default connect(mapStateToProps, mapDispatchToProps)(BloodPressureSummaryEditScreen);
