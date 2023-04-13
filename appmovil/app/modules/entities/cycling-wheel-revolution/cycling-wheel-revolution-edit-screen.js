import React, { createRef } from 'react';
import { ActivityIndicator, Text, View } from 'react-native';
import { connect } from 'react-redux';

import CyclingWheelRevolutionActions from './cycling-wheel-revolution.reducer';
import { KeyboardAwareScrollView } from 'react-native-keyboard-aware-scroll-view';
import FormButton from '../../../shared/components/form/jhi-form-button';
import FormField from '../../../shared/components/form/jhi-form-field';
import Form from '../../../shared/components/form/jhi-form';
import { useDidUpdateEffect } from '../../../shared/util/use-did-update-effect';
import styles from './cycling-wheel-revolution-styles';

function CyclingWheelRevolutionEditScreen(props) {
  const {
    getCyclingWheelRevolution,
    updateCyclingWheelRevolution,
    route,
    cyclingWheelRevolution,
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
      getCyclingWheelRevolution(route.params.entityId);
    } else {
      reset();
    }
  }, [isNewEntity, getCyclingWheelRevolution, route, reset]);

  React.useEffect(() => {
    if (isNewEntity) {
      setFormValue(entityToFormValue({}));
    } else if (!fetching) {
      setFormValue(entityToFormValue(cyclingWheelRevolution));
    }
  }, [cyclingWheelRevolution, fetching, isNewEntity]);

  // fetch related entities
  React.useEffect(() => {}, []);

  useDidUpdateEffect(() => {
    if (updating === false) {
      if (errorUpdating) {
        setError(errorUpdating && errorUpdating.detail ? errorUpdating.detail : 'Something went wrong updating the entity');
      } else if (updateSuccess) {
        setError('');
        isNewEntity || !navigation.canGoBack()
          ? navigation.replace('CyclingWheelRevolutionDetail', { entityId: cyclingWheelRevolution?.id })
          : navigation.pop();
      }
    }
  }, [updateSuccess, errorUpdating, navigation]);

  const onSubmit = (data) => updateCyclingWheelRevolution(formValueToEntity(data));

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
  const revolutionsRef = createRef();
  const startTimeRef = createRef();
  const endTimeRef = createRef();

  return (
    <View style={styles.container}>
      <KeyboardAwareScrollView
        enableResetScrollToCoords={false}
        testID="cyclingWheelRevolutionEditScrollView"
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
              onSubmitEditing={() => revolutionsRef.current?.focus()}
            />
            <FormField
              name="revolutions"
              ref={revolutionsRef}
              label="Revolutions"
              placeholder="Enter Revolutions"
              testID="revolutionsInput"
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
    revolutions: value.revolutions ?? null,
    startTime: value.startTime ?? null,
    endTime: value.endTime ?? null,
  };
};
const formValueToEntity = (value) => {
  const entity = {
    id: value.id ?? null,
    usuarioId: value.usuarioId ?? null,
    empresaId: value.empresaId ?? null,
    revolutions: value.revolutions ?? null,
    startTime: value.startTime ?? null,
    endTime: value.endTime ?? null,
  };
  return entity;
};

const mapStateToProps = (state) => {
  return {
    cyclingWheelRevolution: state.cyclingWheelRevolutions.cyclingWheelRevolution,
    fetching: state.cyclingWheelRevolutions.fetchingOne,
    updating: state.cyclingWheelRevolutions.updating,
    updateSuccess: state.cyclingWheelRevolutions.updateSuccess,
    errorUpdating: state.cyclingWheelRevolutions.errorUpdating,
  };
};

const mapDispatchToProps = (dispatch) => {
  return {
    getCyclingWheelRevolution: (id) => dispatch(CyclingWheelRevolutionActions.cyclingWheelRevolutionRequest(id)),
    getAllCyclingWheelRevolutions: (options) => dispatch(CyclingWheelRevolutionActions.cyclingWheelRevolutionAllRequest(options)),
    updateCyclingWheelRevolution: (cyclingWheelRevolution) =>
      dispatch(CyclingWheelRevolutionActions.cyclingWheelRevolutionUpdateRequest(cyclingWheelRevolution)),
    reset: () => dispatch(CyclingWheelRevolutionActions.cyclingWheelRevolutionReset()),
  };
};

export default connect(mapStateToProps, mapDispatchToProps)(CyclingWheelRevolutionEditScreen);
