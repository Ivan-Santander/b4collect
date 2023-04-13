import React, { createRef } from 'react';
import { ActivityIndicator, Text, View } from 'react-native';
import { connect } from 'react-redux';

import CiclingPedalingCadenceActions from './cicling-pedaling-cadence.reducer';
import { KeyboardAwareScrollView } from 'react-native-keyboard-aware-scroll-view';
import FormButton from '../../../shared/components/form/jhi-form-button';
import FormField from '../../../shared/components/form/jhi-form-field';
import Form from '../../../shared/components/form/jhi-form';
import { useDidUpdateEffect } from '../../../shared/util/use-did-update-effect';
import styles from './cicling-pedaling-cadence-styles';

function CiclingPedalingCadenceEditScreen(props) {
  const {
    getCiclingPedalingCadence,
    updateCiclingPedalingCadence,
    route,
    ciclingPedalingCadence,
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
      getCiclingPedalingCadence(route.params.entityId);
    } else {
      reset();
    }
  }, [isNewEntity, getCiclingPedalingCadence, route, reset]);

  React.useEffect(() => {
    if (isNewEntity) {
      setFormValue(entityToFormValue({}));
    } else if (!fetching) {
      setFormValue(entityToFormValue(ciclingPedalingCadence));
    }
  }, [ciclingPedalingCadence, fetching, isNewEntity]);

  // fetch related entities
  React.useEffect(() => {}, []);

  useDidUpdateEffect(() => {
    if (updating === false) {
      if (errorUpdating) {
        setError(errorUpdating && errorUpdating.detail ? errorUpdating.detail : 'Something went wrong updating the entity');
      } else if (updateSuccess) {
        setError('');
        isNewEntity || !navigation.canGoBack()
          ? navigation.replace('CiclingPedalingCadenceDetail', { entityId: ciclingPedalingCadence?.id })
          : navigation.pop();
      }
    }
  }, [updateSuccess, errorUpdating, navigation]);

  const onSubmit = (data) => updateCiclingPedalingCadence(formValueToEntity(data));

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
  const rpmRef = createRef();
  const startTimeRef = createRef();
  const endTimeRef = createRef();

  return (
    <View style={styles.container}>
      <KeyboardAwareScrollView
        enableResetScrollToCoords={false}
        testID="ciclingPedalingCadenceEditScrollView"
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
              onSubmitEditing={() => rpmRef.current?.focus()}
            />
            <FormField
              name="rpm"
              ref={rpmRef}
              label="Rpm"
              placeholder="Enter Rpm"
              testID="rpmInput"
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
    rpm: value.rpm ?? null,
    startTime: value.startTime ?? null,
    endTime: value.endTime ?? null,
  };
};
const formValueToEntity = (value) => {
  const entity = {
    id: value.id ?? null,
    usuarioId: value.usuarioId ?? null,
    empresaId: value.empresaId ?? null,
    rpm: value.rpm ?? null,
    startTime: value.startTime ?? null,
    endTime: value.endTime ?? null,
  };
  return entity;
};

const mapStateToProps = (state) => {
  return {
    ciclingPedalingCadence: state.ciclingPedalingCadences.ciclingPedalingCadence,
    fetching: state.ciclingPedalingCadences.fetchingOne,
    updating: state.ciclingPedalingCadences.updating,
    updateSuccess: state.ciclingPedalingCadences.updateSuccess,
    errorUpdating: state.ciclingPedalingCadences.errorUpdating,
  };
};

const mapDispatchToProps = (dispatch) => {
  return {
    getCiclingPedalingCadence: (id) => dispatch(CiclingPedalingCadenceActions.ciclingPedalingCadenceRequest(id)),
    getAllCiclingPedalingCadences: (options) => dispatch(CiclingPedalingCadenceActions.ciclingPedalingCadenceAllRequest(options)),
    updateCiclingPedalingCadence: (ciclingPedalingCadence) =>
      dispatch(CiclingPedalingCadenceActions.ciclingPedalingCadenceUpdateRequest(ciclingPedalingCadence)),
    reset: () => dispatch(CiclingPedalingCadenceActions.ciclingPedalingCadenceReset()),
  };
};

export default connect(mapStateToProps, mapDispatchToProps)(CiclingPedalingCadenceEditScreen);
