import React, { createRef } from 'react';
import { ActivityIndicator, Text, View } from 'react-native';
import { connect } from 'react-redux';

import DistanceDeltaActions from './distance-delta.reducer';
import { KeyboardAwareScrollView } from 'react-native-keyboard-aware-scroll-view';
import FormButton from '../../../shared/components/form/jhi-form-button';
import FormField from '../../../shared/components/form/jhi-form-field';
import Form from '../../../shared/components/form/jhi-form';
import { useDidUpdateEffect } from '../../../shared/util/use-did-update-effect';
import styles from './distance-delta-styles';

function DistanceDeltaEditScreen(props) {
  const {
    getDistanceDelta,
    updateDistanceDelta,
    route,
    distanceDelta,
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
      getDistanceDelta(route.params.entityId);
    } else {
      reset();
    }
  }, [isNewEntity, getDistanceDelta, route, reset]);

  React.useEffect(() => {
    if (isNewEntity) {
      setFormValue(entityToFormValue({}));
    } else if (!fetching) {
      setFormValue(entityToFormValue(distanceDelta));
    }
  }, [distanceDelta, fetching, isNewEntity]);

  // fetch related entities
  React.useEffect(() => {}, []);

  useDidUpdateEffect(() => {
    if (updating === false) {
      if (errorUpdating) {
        setError(errorUpdating && errorUpdating.detail ? errorUpdating.detail : 'Something went wrong updating the entity');
      } else if (updateSuccess) {
        setError('');
        isNewEntity || !navigation.canGoBack()
          ? navigation.replace('DistanceDeltaDetail', { entityId: distanceDelta?.id })
          : navigation.pop();
      }
    }
  }, [updateSuccess, errorUpdating, navigation]);

  const onSubmit = (data) => updateDistanceDelta(formValueToEntity(data));

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
  const distanceRef = createRef();
  const startTimeRef = createRef();
  const endTimeRef = createRef();

  return (
    <View style={styles.container}>
      <KeyboardAwareScrollView
        enableResetScrollToCoords={false}
        testID="distanceDeltaEditScrollView"
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
              onSubmitEditing={() => distanceRef.current?.focus()}
            />
            <FormField
              name="distance"
              ref={distanceRef}
              label="Distance"
              placeholder="Enter Distance"
              testID="distanceInput"
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
    distance: value.distance ?? null,
    startTime: value.startTime ?? null,
    endTime: value.endTime ?? null,
  };
};
const formValueToEntity = (value) => {
  const entity = {
    id: value.id ?? null,
    usuarioId: value.usuarioId ?? null,
    empresaId: value.empresaId ?? null,
    distance: value.distance ?? null,
    startTime: value.startTime ?? null,
    endTime: value.endTime ?? null,
  };
  return entity;
};

const mapStateToProps = (state) => {
  return {
    distanceDelta: state.distanceDeltas.distanceDelta,
    fetching: state.distanceDeltas.fetchingOne,
    updating: state.distanceDeltas.updating,
    updateSuccess: state.distanceDeltas.updateSuccess,
    errorUpdating: state.distanceDeltas.errorUpdating,
  };
};

const mapDispatchToProps = (dispatch) => {
  return {
    getDistanceDelta: (id) => dispatch(DistanceDeltaActions.distanceDeltaRequest(id)),
    getAllDistanceDeltas: (options) => dispatch(DistanceDeltaActions.distanceDeltaAllRequest(options)),
    updateDistanceDelta: (distanceDelta) => dispatch(DistanceDeltaActions.distanceDeltaUpdateRequest(distanceDelta)),
    reset: () => dispatch(DistanceDeltaActions.distanceDeltaReset()),
  };
};

export default connect(mapStateToProps, mapDispatchToProps)(DistanceDeltaEditScreen);
