import React, { createRef } from 'react';
import { ActivityIndicator, Text, View } from 'react-native';
import { connect } from 'react-redux';

import LocationSampleActions from './location-sample.reducer';
import { KeyboardAwareScrollView } from 'react-native-keyboard-aware-scroll-view';
import FormButton from '../../../shared/components/form/jhi-form-button';
import FormField from '../../../shared/components/form/jhi-form-field';
import Form from '../../../shared/components/form/jhi-form';
import { useDidUpdateEffect } from '../../../shared/util/use-did-update-effect';
import styles from './location-sample-styles';

function LocationSampleEditScreen(props) {
  const {
    getLocationSample,
    updateLocationSample,
    route,
    locationSample,
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
      getLocationSample(route.params.entityId);
    } else {
      reset();
    }
  }, [isNewEntity, getLocationSample, route, reset]);

  React.useEffect(() => {
    if (isNewEntity) {
      setFormValue(entityToFormValue({}));
    } else if (!fetching) {
      setFormValue(entityToFormValue(locationSample));
    }
  }, [locationSample, fetching, isNewEntity]);

  // fetch related entities
  React.useEffect(() => {}, []);

  useDidUpdateEffect(() => {
    if (updating === false) {
      if (errorUpdating) {
        setError(errorUpdating && errorUpdating.detail ? errorUpdating.detail : 'Something went wrong updating the entity');
      } else if (updateSuccess) {
        setError('');
        isNewEntity || !navigation.canGoBack()
          ? navigation.replace('LocationSampleDetail', { entityId: locationSample?.id })
          : navigation.pop();
      }
    }
  }, [updateSuccess, errorUpdating, navigation]);

  const onSubmit = (data) => updateLocationSample(formValueToEntity(data));

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
  const latitudMinRef = createRef();
  const longitudMinRef = createRef();
  const latitudMaxRef = createRef();
  const longitudMaxRef = createRef();
  const accuracyRef = createRef();
  const altitudRef = createRef();
  const startTimeRef = createRef();
  const endTimeRef = createRef();

  return (
    <View style={styles.container}>
      <KeyboardAwareScrollView
        enableResetScrollToCoords={false}
        testID="locationSampleEditScrollView"
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
              onSubmitEditing={() => latitudMinRef.current?.focus()}
            />
            <FormField
              name="latitudMin"
              ref={latitudMinRef}
              label="Latitud Min"
              placeholder="Enter Latitud Min"
              testID="latitudMinInput"
              inputType="number"
              onSubmitEditing={() => longitudMinRef.current?.focus()}
            />
            <FormField
              name="longitudMin"
              ref={longitudMinRef}
              label="Longitud Min"
              placeholder="Enter Longitud Min"
              testID="longitudMinInput"
              inputType="number"
              onSubmitEditing={() => latitudMaxRef.current?.focus()}
            />
            <FormField
              name="latitudMax"
              ref={latitudMaxRef}
              label="Latitud Max"
              placeholder="Enter Latitud Max"
              testID="latitudMaxInput"
              inputType="number"
              onSubmitEditing={() => longitudMaxRef.current?.focus()}
            />
            <FormField
              name="longitudMax"
              ref={longitudMaxRef}
              label="Longitud Max"
              placeholder="Enter Longitud Max"
              testID="longitudMaxInput"
              inputType="number"
              onSubmitEditing={() => accuracyRef.current?.focus()}
            />
            <FormField
              name="accuracy"
              ref={accuracyRef}
              label="Accuracy"
              placeholder="Enter Accuracy"
              testID="accuracyInput"
              inputType="number"
              onSubmitEditing={() => altitudRef.current?.focus()}
            />
            <FormField
              name="altitud"
              ref={altitudRef}
              label="Altitud"
              placeholder="Enter Altitud"
              testID="altitudInput"
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
    latitudMin: value.latitudMin ?? null,
    longitudMin: value.longitudMin ?? null,
    latitudMax: value.latitudMax ?? null,
    longitudMax: value.longitudMax ?? null,
    accuracy: value.accuracy ?? null,
    altitud: value.altitud ?? null,
    startTime: value.startTime ?? null,
    endTime: value.endTime ?? null,
  };
};
const formValueToEntity = (value) => {
  const entity = {
    id: value.id ?? null,
    usuarioId: value.usuarioId ?? null,
    empresaId: value.empresaId ?? null,
    latitudMin: value.latitudMin ?? null,
    longitudMin: value.longitudMin ?? null,
    latitudMax: value.latitudMax ?? null,
    longitudMax: value.longitudMax ?? null,
    accuracy: value.accuracy ?? null,
    altitud: value.altitud ?? null,
    startTime: value.startTime ?? null,
    endTime: value.endTime ?? null,
  };
  return entity;
};

const mapStateToProps = (state) => {
  return {
    locationSample: state.locationSamples.locationSample,
    fetching: state.locationSamples.fetchingOne,
    updating: state.locationSamples.updating,
    updateSuccess: state.locationSamples.updateSuccess,
    errorUpdating: state.locationSamples.errorUpdating,
  };
};

const mapDispatchToProps = (dispatch) => {
  return {
    getLocationSample: (id) => dispatch(LocationSampleActions.locationSampleRequest(id)),
    getAllLocationSamples: (options) => dispatch(LocationSampleActions.locationSampleAllRequest(options)),
    updateLocationSample: (locationSample) => dispatch(LocationSampleActions.locationSampleUpdateRequest(locationSample)),
    reset: () => dispatch(LocationSampleActions.locationSampleReset()),
  };
};

export default connect(mapStateToProps, mapDispatchToProps)(LocationSampleEditScreen);
