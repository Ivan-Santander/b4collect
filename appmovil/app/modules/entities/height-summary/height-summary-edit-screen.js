import React, { createRef } from 'react';
import { ActivityIndicator, Text, View } from 'react-native';
import { connect } from 'react-redux';

import HeightSummaryActions from './height-summary.reducer';
import { KeyboardAwareScrollView } from 'react-native-keyboard-aware-scroll-view';
import FormButton from '../../../shared/components/form/jhi-form-button';
import FormField from '../../../shared/components/form/jhi-form-field';
import Form from '../../../shared/components/form/jhi-form';
import { useDidUpdateEffect } from '../../../shared/util/use-did-update-effect';
import styles from './height-summary-styles';

function HeightSummaryEditScreen(props) {
  const {
    getHeightSummary,
    updateHeightSummary,
    route,
    heightSummary,
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
      getHeightSummary(route.params.entityId);
    } else {
      reset();
    }
  }, [isNewEntity, getHeightSummary, route, reset]);

  React.useEffect(() => {
    if (isNewEntity) {
      setFormValue(entityToFormValue({}));
    } else if (!fetching) {
      setFormValue(entityToFormValue(heightSummary));
    }
  }, [heightSummary, fetching, isNewEntity]);

  // fetch related entities
  React.useEffect(() => {}, []);

  useDidUpdateEffect(() => {
    if (updating === false) {
      if (errorUpdating) {
        setError(errorUpdating && errorUpdating.detail ? errorUpdating.detail : 'Something went wrong updating the entity');
      } else if (updateSuccess) {
        setError('');
        isNewEntity || !navigation.canGoBack()
          ? navigation.replace('HeightSummaryDetail', { entityId: heightSummary?.id })
          : navigation.pop();
      }
    }
  }, [updateSuccess, errorUpdating, navigation]);

  const onSubmit = (data) => updateHeightSummary(formValueToEntity(data));

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
  const startTimeRef = createRef();
  const endTimeRef = createRef();

  return (
    <View style={styles.container}>
      <KeyboardAwareScrollView
        enableResetScrollToCoords={false}
        testID="heightSummaryEditScrollView"
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
    startTime: value.startTime ?? null,
    endTime: value.endTime ?? null,
  };
  return entity;
};

const mapStateToProps = (state) => {
  return {
    heightSummary: state.heightSummaries.heightSummary,
    fetching: state.heightSummaries.fetchingOne,
    updating: state.heightSummaries.updating,
    updateSuccess: state.heightSummaries.updateSuccess,
    errorUpdating: state.heightSummaries.errorUpdating,
  };
};

const mapDispatchToProps = (dispatch) => {
  return {
    getHeightSummary: (id) => dispatch(HeightSummaryActions.heightSummaryRequest(id)),
    getAllHeightSummaries: (options) => dispatch(HeightSummaryActions.heightSummaryAllRequest(options)),
    updateHeightSummary: (heightSummary) => dispatch(HeightSummaryActions.heightSummaryUpdateRequest(heightSummary)),
    reset: () => dispatch(HeightSummaryActions.heightSummaryReset()),
  };
};

export default connect(mapStateToProps, mapDispatchToProps)(HeightSummaryEditScreen);
