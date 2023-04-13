import React, { createRef } from 'react';
import { ActivityIndicator, Text, View } from 'react-native';
import { connect } from 'react-redux';

import BodyTemperatureActions from './body-temperature.reducer';
import { KeyboardAwareScrollView } from 'react-native-keyboard-aware-scroll-view';
import FormButton from '../../../shared/components/form/jhi-form-button';
import FormField from '../../../shared/components/form/jhi-form-field';
import Form from '../../../shared/components/form/jhi-form';
import { useDidUpdateEffect } from '../../../shared/util/use-did-update-effect';
import styles from './body-temperature-styles';

function BodyTemperatureEditScreen(props) {
  const {
    getBodyTemperature,
    updateBodyTemperature,
    route,
    bodyTemperature,
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
      getBodyTemperature(route.params.entityId);
    } else {
      reset();
    }
  }, [isNewEntity, getBodyTemperature, route, reset]);

  React.useEffect(() => {
    if (isNewEntity) {
      setFormValue(entityToFormValue({}));
    } else if (!fetching) {
      setFormValue(entityToFormValue(bodyTemperature));
    }
  }, [bodyTemperature, fetching, isNewEntity]);

  // fetch related entities
  React.useEffect(() => {}, []);

  useDidUpdateEffect(() => {
    if (updating === false) {
      if (errorUpdating) {
        setError(errorUpdating && errorUpdating.detail ? errorUpdating.detail : 'Something went wrong updating the entity');
      } else if (updateSuccess) {
        setError('');
        isNewEntity || !navigation.canGoBack()
          ? navigation.replace('BodyTemperatureDetail', { entityId: bodyTemperature?.id })
          : navigation.pop();
      }
    }
  }, [updateSuccess, errorUpdating, navigation]);

  const onSubmit = (data) => updateBodyTemperature(formValueToEntity(data));

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
  const fieldBodyTemperatureRef = createRef();
  const fieldBodyTemperatureMeasureLocationRef = createRef();
  const endTimeRef = createRef();

  return (
    <View style={styles.container}>
      <KeyboardAwareScrollView
        enableResetScrollToCoords={false}
        testID="bodyTemperatureEditScrollView"
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
              onSubmitEditing={() => fieldBodyTemperatureRef.current?.focus()}
            />
            <FormField
              name="fieldBodyTemperature"
              ref={fieldBodyTemperatureRef}
              label="Field Body Temperature"
              placeholder="Enter Field Body Temperature"
              testID="fieldBodyTemperatureInput"
              inputType="number"
              onSubmitEditing={() => fieldBodyTemperatureMeasureLocationRef.current?.focus()}
            />
            <FormField
              name="fieldBodyTemperatureMeasureLocation"
              ref={fieldBodyTemperatureMeasureLocationRef}
              label="Field Body Temperature Measure Location"
              placeholder="Enter Field Body Temperature Measure Location"
              testID="fieldBodyTemperatureMeasureLocationInput"
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
    fieldBodyTemperature: value.fieldBodyTemperature ?? null,
    fieldBodyTemperatureMeasureLocation: value.fieldBodyTemperatureMeasureLocation ?? null,
    endTime: value.endTime ?? null,
  };
};
const formValueToEntity = (value) => {
  const entity = {
    id: value.id ?? null,
    usuarioId: value.usuarioId ?? null,
    empresaId: value.empresaId ?? null,
    fieldBodyTemperature: value.fieldBodyTemperature ?? null,
    fieldBodyTemperatureMeasureLocation: value.fieldBodyTemperatureMeasureLocation ?? null,
    endTime: value.endTime ?? null,
  };
  return entity;
};

const mapStateToProps = (state) => {
  return {
    bodyTemperature: state.bodyTemperatures.bodyTemperature,
    fetching: state.bodyTemperatures.fetchingOne,
    updating: state.bodyTemperatures.updating,
    updateSuccess: state.bodyTemperatures.updateSuccess,
    errorUpdating: state.bodyTemperatures.errorUpdating,
  };
};

const mapDispatchToProps = (dispatch) => {
  return {
    getBodyTemperature: (id) => dispatch(BodyTemperatureActions.bodyTemperatureRequest(id)),
    getAllBodyTemperatures: (options) => dispatch(BodyTemperatureActions.bodyTemperatureAllRequest(options)),
    updateBodyTemperature: (bodyTemperature) => dispatch(BodyTemperatureActions.bodyTemperatureUpdateRequest(bodyTemperature)),
    reset: () => dispatch(BodyTemperatureActions.bodyTemperatureReset()),
  };
};

export default connect(mapStateToProps, mapDispatchToProps)(BodyTemperatureEditScreen);
