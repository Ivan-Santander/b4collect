import React, { createRef } from 'react';
import { ActivityIndicator, Text, View } from 'react-native';
import { connect } from 'react-redux';

import HeartRateBpmActions from './heart-rate-bpm.reducer';
import { KeyboardAwareScrollView } from 'react-native-keyboard-aware-scroll-view';
import FormButton from '../../../shared/components/form/jhi-form-button';
import FormField from '../../../shared/components/form/jhi-form-field';
import Form from '../../../shared/components/form/jhi-form';
import { useDidUpdateEffect } from '../../../shared/util/use-did-update-effect';
import styles from './heart-rate-bpm-styles';

function HeartRateBpmEditScreen(props) {
  const { getHeartRateBpm, updateHeartRateBpm, route, heartRateBpm, fetching, updating, errorUpdating, updateSuccess, navigation, reset } =
    props;

  const [formValue, setFormValue] = React.useState();
  const [error, setError] = React.useState('');

  const isNewEntity = !(route.params && route.params.entityId);

  React.useEffect(() => {
    if (!isNewEntity) {
      getHeartRateBpm(route.params.entityId);
    } else {
      reset();
    }
  }, [isNewEntity, getHeartRateBpm, route, reset]);

  React.useEffect(() => {
    if (isNewEntity) {
      setFormValue(entityToFormValue({}));
    } else if (!fetching) {
      setFormValue(entityToFormValue(heartRateBpm));
    }
  }, [heartRateBpm, fetching, isNewEntity]);

  // fetch related entities
  React.useEffect(() => {}, []);

  useDidUpdateEffect(() => {
    if (updating === false) {
      if (errorUpdating) {
        setError(errorUpdating && errorUpdating.detail ? errorUpdating.detail : 'Something went wrong updating the entity');
      } else if (updateSuccess) {
        setError('');
        isNewEntity || !navigation.canGoBack()
          ? navigation.replace('HeartRateBpmDetail', { entityId: heartRateBpm?.id })
          : navigation.pop();
      }
    }
  }, [updateSuccess, errorUpdating, navigation]);

  const onSubmit = (data) => updateHeartRateBpm(formValueToEntity(data));

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
  const ppmRef = createRef();
  const endTimeRef = createRef();

  return (
    <View style={styles.container}>
      <KeyboardAwareScrollView
        enableResetScrollToCoords={false}
        testID="heartRateBpmEditScrollView"
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
              onSubmitEditing={() => ppmRef.current?.focus()}
            />
            <FormField
              name="ppm"
              ref={ppmRef}
              label="Ppm"
              placeholder="Enter Ppm"
              testID="ppmInput"
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
    ppm: value.ppm ?? null,
    endTime: value.endTime ?? null,
  };
};
const formValueToEntity = (value) => {
  const entity = {
    id: value.id ?? null,
    usuarioId: value.usuarioId ?? null,
    empresaId: value.empresaId ?? null,
    ppm: value.ppm ?? null,
    endTime: value.endTime ?? null,
  };
  return entity;
};

const mapStateToProps = (state) => {
  return {
    heartRateBpm: state.heartRateBpms.heartRateBpm,
    fetching: state.heartRateBpms.fetchingOne,
    updating: state.heartRateBpms.updating,
    updateSuccess: state.heartRateBpms.updateSuccess,
    errorUpdating: state.heartRateBpms.errorUpdating,
  };
};

const mapDispatchToProps = (dispatch) => {
  return {
    getHeartRateBpm: (id) => dispatch(HeartRateBpmActions.heartRateBpmRequest(id)),
    getAllHeartRateBpms: (options) => dispatch(HeartRateBpmActions.heartRateBpmAllRequest(options)),
    updateHeartRateBpm: (heartRateBpm) => dispatch(HeartRateBpmActions.heartRateBpmUpdateRequest(heartRateBpm)),
    reset: () => dispatch(HeartRateBpmActions.heartRateBpmReset()),
  };
};

export default connect(mapStateToProps, mapDispatchToProps)(HeartRateBpmEditScreen);
