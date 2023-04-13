import React, { createRef } from 'react';
import { ActivityIndicator, Text, View } from 'react-native';
import { connect } from 'react-redux';

import BloodPressureActions from './blood-pressure.reducer';
import { KeyboardAwareScrollView } from 'react-native-keyboard-aware-scroll-view';
import FormButton from '../../../shared/components/form/jhi-form-button';
import FormField from '../../../shared/components/form/jhi-form-field';
import Form from '../../../shared/components/form/jhi-form';
import { useDidUpdateEffect } from '../../../shared/util/use-did-update-effect';
import styles from './blood-pressure-styles';

function BloodPressureEditScreen(props) {
  const {
    getBloodPressure,
    updateBloodPressure,
    route,
    bloodPressure,
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
      getBloodPressure(route.params.entityId);
    } else {
      reset();
    }
  }, [isNewEntity, getBloodPressure, route, reset]);

  React.useEffect(() => {
    if (isNewEntity) {
      setFormValue(entityToFormValue({}));
    } else if (!fetching) {
      setFormValue(entityToFormValue(bloodPressure));
    }
  }, [bloodPressure, fetching, isNewEntity]);

  // fetch related entities
  React.useEffect(() => {}, []);

  useDidUpdateEffect(() => {
    if (updating === false) {
      if (errorUpdating) {
        setError(errorUpdating && errorUpdating.detail ? errorUpdating.detail : 'Something went wrong updating the entity');
      } else if (updateSuccess) {
        setError('');
        isNewEntity || !navigation.canGoBack()
          ? navigation.replace('BloodPressureDetail', { entityId: bloodPressure?.id })
          : navigation.pop();
      }
    }
  }, [updateSuccess, errorUpdating, navigation]);

  const onSubmit = (data) => updateBloodPressure(formValueToEntity(data));

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
  const fieldBloodPressureSystolicRef = createRef();
  const fieldBloodPressureDiastolicRef = createRef();
  const fieldBodyPositionRef = createRef();
  const fieldBloodPressureMeasureLocationRef = createRef();
  const endTimeRef = createRef();

  return (
    <View style={styles.container}>
      <KeyboardAwareScrollView
        enableResetScrollToCoords={false}
        testID="bloodPressureEditScrollView"
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
              onSubmitEditing={() => fieldBloodPressureSystolicRef.current?.focus()}
            />
            <FormField
              name="fieldBloodPressureSystolic"
              ref={fieldBloodPressureSystolicRef}
              label="Field Blood Pressure Systolic"
              placeholder="Enter Field Blood Pressure Systolic"
              testID="fieldBloodPressureSystolicInput"
              inputType="number"
              onSubmitEditing={() => fieldBloodPressureDiastolicRef.current?.focus()}
            />
            <FormField
              name="fieldBloodPressureDiastolic"
              ref={fieldBloodPressureDiastolicRef}
              label="Field Blood Pressure Diastolic"
              placeholder="Enter Field Blood Pressure Diastolic"
              testID="fieldBloodPressureDiastolicInput"
              inputType="number"
              onSubmitEditing={() => fieldBodyPositionRef.current?.focus()}
            />
            <FormField
              name="fieldBodyPosition"
              ref={fieldBodyPositionRef}
              label="Field Body Position"
              placeholder="Enter Field Body Position"
              testID="fieldBodyPositionInput"
              inputType="text"
              autoCapitalize="none"
              onSubmitEditing={() => fieldBloodPressureMeasureLocationRef.current?.focus()}
            />
            <FormField
              name="fieldBloodPressureMeasureLocation"
              ref={fieldBloodPressureMeasureLocationRef}
              label="Field Blood Pressure Measure Location"
              placeholder="Enter Field Blood Pressure Measure Location"
              testID="fieldBloodPressureMeasureLocationInput"
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
    fieldBloodPressureSystolic: value.fieldBloodPressureSystolic ?? null,
    fieldBloodPressureDiastolic: value.fieldBloodPressureDiastolic ?? null,
    fieldBodyPosition: value.fieldBodyPosition ?? null,
    fieldBloodPressureMeasureLocation: value.fieldBloodPressureMeasureLocation ?? null,
    endTime: value.endTime ?? null,
  };
};
const formValueToEntity = (value) => {
  const entity = {
    id: value.id ?? null,
    usuarioId: value.usuarioId ?? null,
    empresaId: value.empresaId ?? null,
    fieldBloodPressureSystolic: value.fieldBloodPressureSystolic ?? null,
    fieldBloodPressureDiastolic: value.fieldBloodPressureDiastolic ?? null,
    fieldBodyPosition: value.fieldBodyPosition ?? null,
    fieldBloodPressureMeasureLocation: value.fieldBloodPressureMeasureLocation ?? null,
    endTime: value.endTime ?? null,
  };
  return entity;
};

const mapStateToProps = (state) => {
  return {
    bloodPressure: state.bloodPressures.bloodPressure,
    fetching: state.bloodPressures.fetchingOne,
    updating: state.bloodPressures.updating,
    updateSuccess: state.bloodPressures.updateSuccess,
    errorUpdating: state.bloodPressures.errorUpdating,
  };
};

const mapDispatchToProps = (dispatch) => {
  return {
    getBloodPressure: (id) => dispatch(BloodPressureActions.bloodPressureRequest(id)),
    getAllBloodPressures: (options) => dispatch(BloodPressureActions.bloodPressureAllRequest(options)),
    updateBloodPressure: (bloodPressure) => dispatch(BloodPressureActions.bloodPressureUpdateRequest(bloodPressure)),
    reset: () => dispatch(BloodPressureActions.bloodPressureReset()),
  };
};

export default connect(mapStateToProps, mapDispatchToProps)(BloodPressureEditScreen);
