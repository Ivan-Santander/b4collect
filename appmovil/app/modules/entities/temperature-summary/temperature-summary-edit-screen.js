import React, { createRef } from 'react';
import { ActivityIndicator, Text, View } from 'react-native';
import { connect } from 'react-redux';

import TemperatureSummaryActions from './temperature-summary.reducer';
import { KeyboardAwareScrollView } from 'react-native-keyboard-aware-scroll-view';
import FormButton from '../../../shared/components/form/jhi-form-button';
import FormField from '../../../shared/components/form/jhi-form-field';
import Form from '../../../shared/components/form/jhi-form';
import { useDidUpdateEffect } from '../../../shared/util/use-did-update-effect';
import styles from './temperature-summary-styles';

function TemperatureSummaryEditScreen(props) {
  const {
    getTemperatureSummary,
    updateTemperatureSummary,
    route,
    temperatureSummary,
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
      getTemperatureSummary(route.params.entityId);
    } else {
      reset();
    }
  }, [isNewEntity, getTemperatureSummary, route, reset]);

  React.useEffect(() => {
    if (isNewEntity) {
      setFormValue(entityToFormValue({}));
    } else if (!fetching) {
      setFormValue(entityToFormValue(temperatureSummary));
    }
  }, [temperatureSummary, fetching, isNewEntity]);

  // fetch related entities
  React.useEffect(() => {}, []);

  useDidUpdateEffect(() => {
    if (updating === false) {
      if (errorUpdating) {
        setError(errorUpdating && errorUpdating.detail ? errorUpdating.detail : 'Something went wrong updating the entity');
      } else if (updateSuccess) {
        setError('');
        isNewEntity || !navigation.canGoBack()
          ? navigation.replace('TemperatureSummaryDetail', { entityId: temperatureSummary?.id })
          : navigation.pop();
      }
    }
  }, [updateSuccess, errorUpdating, navigation]);

  const onSubmit = (data) => updateTemperatureSummary(formValueToEntity(data));

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
  const measurementLocationRef = createRef();
  const startTimeRef = createRef();
  const endTimeRef = createRef();

  return (
    <View style={styles.container}>
      <KeyboardAwareScrollView
        enableResetScrollToCoords={false}
        testID="temperatureSummaryEditScrollView"
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
    fieldAverage: value.fieldAverage ?? null,
    fieldMax: value.fieldMax ?? null,
    fieldMin: value.fieldMin ?? null,
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
    fieldAverage: value.fieldAverage ?? null,
    fieldMax: value.fieldMax ?? null,
    fieldMin: value.fieldMin ?? null,
    measurementLocation: value.measurementLocation ?? null,
    startTime: value.startTime ?? null,
    endTime: value.endTime ?? null,
  };
  return entity;
};

const mapStateToProps = (state) => {
  return {
    temperatureSummary: state.temperatureSummaries.temperatureSummary,
    fetching: state.temperatureSummaries.fetchingOne,
    updating: state.temperatureSummaries.updating,
    updateSuccess: state.temperatureSummaries.updateSuccess,
    errorUpdating: state.temperatureSummaries.errorUpdating,
  };
};

const mapDispatchToProps = (dispatch) => {
  return {
    getTemperatureSummary: (id) => dispatch(TemperatureSummaryActions.temperatureSummaryRequest(id)),
    getAllTemperatureSummaries: (options) => dispatch(TemperatureSummaryActions.temperatureSummaryAllRequest(options)),
    updateTemperatureSummary: (temperatureSummary) =>
      dispatch(TemperatureSummaryActions.temperatureSummaryUpdateRequest(temperatureSummary)),
    reset: () => dispatch(TemperatureSummaryActions.temperatureSummaryReset()),
  };
};

export default connect(mapStateToProps, mapDispatchToProps)(TemperatureSummaryEditScreen);
