import React, { createRef } from 'react';
import { ActivityIndicator, Text, View } from 'react-native';
import { connect } from 'react-redux';

import OxygenSaturationActions from './oxygen-saturation.reducer';
import { KeyboardAwareScrollView } from 'react-native-keyboard-aware-scroll-view';
import FormButton from '../../../shared/components/form/jhi-form-button';
import FormField from '../../../shared/components/form/jhi-form-field';
import Form from '../../../shared/components/form/jhi-form';
import { useDidUpdateEffect } from '../../../shared/util/use-did-update-effect';
import styles from './oxygen-saturation-styles';

function OxygenSaturationEditScreen(props) {
  const {
    getOxygenSaturation,
    updateOxygenSaturation,
    route,
    oxygenSaturation,
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
      getOxygenSaturation(route.params.entityId);
    } else {
      reset();
    }
  }, [isNewEntity, getOxygenSaturation, route, reset]);

  React.useEffect(() => {
    if (isNewEntity) {
      setFormValue(entityToFormValue({}));
    } else if (!fetching) {
      setFormValue(entityToFormValue(oxygenSaturation));
    }
  }, [oxygenSaturation, fetching, isNewEntity]);

  // fetch related entities
  React.useEffect(() => {}, []);

  useDidUpdateEffect(() => {
    if (updating === false) {
      if (errorUpdating) {
        setError(errorUpdating && errorUpdating.detail ? errorUpdating.detail : 'Something went wrong updating the entity');
      } else if (updateSuccess) {
        setError('');
        isNewEntity || !navigation.canGoBack()
          ? navigation.replace('OxygenSaturationDetail', { entityId: oxygenSaturation?.id })
          : navigation.pop();
      }
    }
  }, [updateSuccess, errorUpdating, navigation]);

  const onSubmit = (data) => updateOxygenSaturation(formValueToEntity(data));

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
  const fieldOxigenSaturationRef = createRef();
  const fieldSuplementalOxigenFlowRateRef = createRef();
  const fieldOxigenTherapyAdministrationModeRef = createRef();
  const fieldOxigenSaturationModeRef = createRef();
  const fieldOxigenSaturationMeasurementMethodRef = createRef();
  const endTimeRef = createRef();

  return (
    <View style={styles.container}>
      <KeyboardAwareScrollView
        enableResetScrollToCoords={false}
        testID="oxygenSaturationEditScrollView"
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
              onSubmitEditing={() => fieldOxigenSaturationRef.current?.focus()}
            />
            <FormField
              name="fieldOxigenSaturation"
              ref={fieldOxigenSaturationRef}
              label="Field Oxigen Saturation"
              placeholder="Enter Field Oxigen Saturation"
              testID="fieldOxigenSaturationInput"
              inputType="number"
              onSubmitEditing={() => fieldSuplementalOxigenFlowRateRef.current?.focus()}
            />
            <FormField
              name="fieldSuplementalOxigenFlowRate"
              ref={fieldSuplementalOxigenFlowRateRef}
              label="Field Suplemental Oxigen Flow Rate"
              placeholder="Enter Field Suplemental Oxigen Flow Rate"
              testID="fieldSuplementalOxigenFlowRateInput"
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
    fieldOxigenSaturation: value.fieldOxigenSaturation ?? null,
    fieldSuplementalOxigenFlowRate: value.fieldSuplementalOxigenFlowRate ?? null,
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
    fieldOxigenSaturation: value.fieldOxigenSaturation ?? null,
    fieldSuplementalOxigenFlowRate: value.fieldSuplementalOxigenFlowRate ?? null,
    fieldOxigenTherapyAdministrationMode: value.fieldOxigenTherapyAdministrationMode ?? null,
    fieldOxigenSaturationMode: value.fieldOxigenSaturationMode ?? null,
    fieldOxigenSaturationMeasurementMethod: value.fieldOxigenSaturationMeasurementMethod ?? null,
    endTime: value.endTime ?? null,
  };
  return entity;
};

const mapStateToProps = (state) => {
  return {
    oxygenSaturation: state.oxygenSaturations.oxygenSaturation,
    fetching: state.oxygenSaturations.fetchingOne,
    updating: state.oxygenSaturations.updating,
    updateSuccess: state.oxygenSaturations.updateSuccess,
    errorUpdating: state.oxygenSaturations.errorUpdating,
  };
};

const mapDispatchToProps = (dispatch) => {
  return {
    getOxygenSaturation: (id) => dispatch(OxygenSaturationActions.oxygenSaturationRequest(id)),
    getAllOxygenSaturations: (options) => dispatch(OxygenSaturationActions.oxygenSaturationAllRequest(options)),
    updateOxygenSaturation: (oxygenSaturation) => dispatch(OxygenSaturationActions.oxygenSaturationUpdateRequest(oxygenSaturation)),
    reset: () => dispatch(OxygenSaturationActions.oxygenSaturationReset()),
  };
};

export default connect(mapStateToProps, mapDispatchToProps)(OxygenSaturationEditScreen);
