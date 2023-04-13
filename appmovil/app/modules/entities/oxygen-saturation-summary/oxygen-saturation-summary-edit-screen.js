import React, { createRef } from 'react';
import { ActivityIndicator, Text, View } from 'react-native';
import { connect } from 'react-redux';

import OxygenSaturationSummaryActions from './oxygen-saturation-summary.reducer';
import { KeyboardAwareScrollView } from 'react-native-keyboard-aware-scroll-view';
import FormButton from '../../../shared/components/form/jhi-form-button';
import FormField from '../../../shared/components/form/jhi-form-field';
import Form from '../../../shared/components/form/jhi-form';
import { useDidUpdateEffect } from '../../../shared/util/use-did-update-effect';
import styles from './oxygen-saturation-summary-styles';

function OxygenSaturationSummaryEditScreen(props) {
  const {
    getOxygenSaturationSummary,
    updateOxygenSaturationSummary,
    route,
    oxygenSaturationSummary,
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
      getOxygenSaturationSummary(route.params.entityId);
    } else {
      reset();
    }
  }, [isNewEntity, getOxygenSaturationSummary, route, reset]);

  React.useEffect(() => {
    if (isNewEntity) {
      setFormValue(entityToFormValue({}));
    } else if (!fetching) {
      setFormValue(entityToFormValue(oxygenSaturationSummary));
    }
  }, [oxygenSaturationSummary, fetching, isNewEntity]);

  // fetch related entities
  React.useEffect(() => {}, []);

  useDidUpdateEffect(() => {
    if (updating === false) {
      if (errorUpdating) {
        setError(errorUpdating && errorUpdating.detail ? errorUpdating.detail : 'Something went wrong updating the entity');
      } else if (updateSuccess) {
        setError('');
        isNewEntity || !navigation.canGoBack()
          ? navigation.replace('OxygenSaturationSummaryDetail', { entityId: oxygenSaturationSummary?.id })
          : navigation.pop();
      }
    }
  }, [updateSuccess, errorUpdating, navigation]);

  const onSubmit = (data) => updateOxygenSaturationSummary(formValueToEntity(data));

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
  const fieldOxigenSaturationAverageRef = createRef();
  const fieldOxigenSaturationMaxRef = createRef();
  const fieldOxigenSaturationMinRef = createRef();
  const fieldSuplementalOxigenFlowRateAverageRef = createRef();
  const fieldSuplementalOxigenFlowRateMaxRef = createRef();
  const fieldSuplementalOxigenFlowRateMinRef = createRef();
  const fieldOxigenTherapyAdministrationModeRef = createRef();
  const fieldOxigenSaturationModeRef = createRef();
  const fieldOxigenSaturationMeasurementMethodRef = createRef();
  const endTimeRef = createRef();

  return (
    <View style={styles.container}>
      <KeyboardAwareScrollView
        enableResetScrollToCoords={false}
        testID="oxygenSaturationSummaryEditScrollView"
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
              onSubmitEditing={() => fieldOxigenSaturationAverageRef.current?.focus()}
            />
            <FormField
              name="fieldOxigenSaturationAverage"
              ref={fieldOxigenSaturationAverageRef}
              label="Field Oxigen Saturation Average"
              placeholder="Enter Field Oxigen Saturation Average"
              testID="fieldOxigenSaturationAverageInput"
              inputType="number"
              onSubmitEditing={() => fieldOxigenSaturationMaxRef.current?.focus()}
            />
            <FormField
              name="fieldOxigenSaturationMax"
              ref={fieldOxigenSaturationMaxRef}
              label="Field Oxigen Saturation Max"
              placeholder="Enter Field Oxigen Saturation Max"
              testID="fieldOxigenSaturationMaxInput"
              inputType="number"
              onSubmitEditing={() => fieldOxigenSaturationMinRef.current?.focus()}
            />
            <FormField
              name="fieldOxigenSaturationMin"
              ref={fieldOxigenSaturationMinRef}
              label="Field Oxigen Saturation Min"
              placeholder="Enter Field Oxigen Saturation Min"
              testID="fieldOxigenSaturationMinInput"
              inputType="number"
              onSubmitEditing={() => fieldSuplementalOxigenFlowRateAverageRef.current?.focus()}
            />
            <FormField
              name="fieldSuplementalOxigenFlowRateAverage"
              ref={fieldSuplementalOxigenFlowRateAverageRef}
              label="Field Suplemental Oxigen Flow Rate Average"
              placeholder="Enter Field Suplemental Oxigen Flow Rate Average"
              testID="fieldSuplementalOxigenFlowRateAverageInput"
              inputType="number"
              onSubmitEditing={() => fieldSuplementalOxigenFlowRateMaxRef.current?.focus()}
            />
            <FormField
              name="fieldSuplementalOxigenFlowRateMax"
              ref={fieldSuplementalOxigenFlowRateMaxRef}
              label="Field Suplemental Oxigen Flow Rate Max"
              placeholder="Enter Field Suplemental Oxigen Flow Rate Max"
              testID="fieldSuplementalOxigenFlowRateMaxInput"
              inputType="number"
              onSubmitEditing={() => fieldSuplementalOxigenFlowRateMinRef.current?.focus()}
            />
            <FormField
              name="fieldSuplementalOxigenFlowRateMin"
              ref={fieldSuplementalOxigenFlowRateMinRef}
              label="Field Suplemental Oxigen Flow Rate Min"
              placeholder="Enter Field Suplemental Oxigen Flow Rate Min"
              testID="fieldSuplementalOxigenFlowRateMinInput"
              inputType="number"
              onSubmitEditing={() => fieldOxigenTherapyAdministrationModeRef.current?.focus()}
            />
            <FormField
              name="fieldOxigenTherapyAdministrationMode"
              ref={fieldOxigenTherapyAdministrationModeRef}
              label="Field Oxigen Therapy Administration Mode"
              placeholder="Enter Field Oxigen Therapy Administration Mode"
              testID="fieldOxigenTherapyAdministrationModeInput"
              inputType="number"
              onSubmitEditing={() => fieldOxigenSaturationModeRef.current?.focus()}
            />
            <FormField
              name="fieldOxigenSaturationMode"
              ref={fieldOxigenSaturationModeRef}
              label="Field Oxigen Saturation Mode"
              placeholder="Enter Field Oxigen Saturation Mode"
              testID="fieldOxigenSaturationModeInput"
              inputType="number"
              onSubmitEditing={() => fieldOxigenSaturationMeasurementMethodRef.current?.focus()}
            />
            <FormField
              name="fieldOxigenSaturationMeasurementMethod"
              ref={fieldOxigenSaturationMeasurementMethodRef}
              label="Field Oxigen Saturation Measurement Method"
              placeholder="Enter Field Oxigen Saturation Measurement Method"
              testID="fieldOxigenSaturationMeasurementMethodInput"
              inputType="number"
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
    fieldOxigenSaturationAverage: value.fieldOxigenSaturationAverage ?? null,
    fieldOxigenSaturationMax: value.fieldOxigenSaturationMax ?? null,
    fieldOxigenSaturationMin: value.fieldOxigenSaturationMin ?? null,
    fieldSuplementalOxigenFlowRateAverage: value.fieldSuplementalOxigenFlowRateAverage ?? null,
    fieldSuplementalOxigenFlowRateMax: value.fieldSuplementalOxigenFlowRateMax ?? null,
    fieldSuplementalOxigenFlowRateMin: value.fieldSuplementalOxigenFlowRateMin ?? null,
    fieldOxigenTherapyAdministrationMode: value.fieldOxigenTherapyAdministrationMode ?? null,
    fieldOxigenSaturationMode: value.fieldOxigenSaturationMode ?? null,
    fieldOxigenSaturationMeasurementMethod: value.fieldOxigenSaturationMeasurementMethod ?? null,
    endTime: value.endTime ?? null,
  };
};
const formValueToEntity = (value) => {
  const entity = {
    id: value.id ?? null,
    usuarioId: value.usuarioId ?? null,
    empresaId: value.empresaId ?? null,
    fieldOxigenSaturationAverage: value.fieldOxigenSaturationAverage ?? null,
    fieldOxigenSaturationMax: value.fieldOxigenSaturationMax ?? null,
    fieldOxigenSaturationMin: value.fieldOxigenSaturationMin ?? null,
    fieldSuplementalOxigenFlowRateAverage: value.fieldSuplementalOxigenFlowRateAverage ?? null,
    fieldSuplementalOxigenFlowRateMax: value.fieldSuplementalOxigenFlowRateMax ?? null,
    fieldSuplementalOxigenFlowRateMin: value.fieldSuplementalOxigenFlowRateMin ?? null,
    fieldOxigenTherapyAdministrationMode: value.fieldOxigenTherapyAdministrationMode ?? null,
    fieldOxigenSaturationMode: value.fieldOxigenSaturationMode ?? null,
    fieldOxigenSaturationMeasurementMethod: value.fieldOxigenSaturationMeasurementMethod ?? null,
    endTime: value.endTime ?? null,
  };
  return entity;
};

const mapStateToProps = (state) => {
  return {
    oxygenSaturationSummary: state.oxygenSaturationSummaries.oxygenSaturationSummary,
    fetching: state.oxygenSaturationSummaries.fetchingOne,
    updating: state.oxygenSaturationSummaries.updating,
    updateSuccess: state.oxygenSaturationSummaries.updateSuccess,
    errorUpdating: state.oxygenSaturationSummaries.errorUpdating,
  };
};

const mapDispatchToProps = (dispatch) => {
  return {
    getOxygenSaturationSummary: (id) => dispatch(OxygenSaturationSummaryActions.oxygenSaturationSummaryRequest(id)),
    getAllOxygenSaturationSummaries: (options) => dispatch(OxygenSaturationSummaryActions.oxygenSaturationSummaryAllRequest(options)),
    updateOxygenSaturationSummary: (oxygenSaturationSummary) =>
      dispatch(OxygenSaturationSummaryActions.oxygenSaturationSummaryUpdateRequest(oxygenSaturationSummary)),
    reset: () => dispatch(OxygenSaturationSummaryActions.oxygenSaturationSummaryReset()),
  };
};

export default connect(mapStateToProps, mapDispatchToProps)(OxygenSaturationSummaryEditScreen);
