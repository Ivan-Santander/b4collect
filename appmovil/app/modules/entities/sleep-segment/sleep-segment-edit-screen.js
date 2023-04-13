import React, { createRef } from 'react';
import { ActivityIndicator, Text, View } from 'react-native';
import { connect } from 'react-redux';

import SleepSegmentActions from './sleep-segment.reducer';
import { KeyboardAwareScrollView } from 'react-native-keyboard-aware-scroll-view';
import FormButton from '../../../shared/components/form/jhi-form-button';
import FormField from '../../../shared/components/form/jhi-form-field';
import Form from '../../../shared/components/form/jhi-form';
import { useDidUpdateEffect } from '../../../shared/util/use-did-update-effect';
import styles from './sleep-segment-styles';

function SleepSegmentEditScreen(props) {
  const { getSleepSegment, updateSleepSegment, route, sleepSegment, fetching, updating, errorUpdating, updateSuccess, navigation, reset } =
    props;

  const [formValue, setFormValue] = React.useState();
  const [error, setError] = React.useState('');

  const isNewEntity = !(route.params && route.params.entityId);

  React.useEffect(() => {
    if (!isNewEntity) {
      getSleepSegment(route.params.entityId);
    } else {
      reset();
    }
  }, [isNewEntity, getSleepSegment, route, reset]);

  React.useEffect(() => {
    if (isNewEntity) {
      setFormValue(entityToFormValue({}));
    } else if (!fetching) {
      setFormValue(entityToFormValue(sleepSegment));
    }
  }, [sleepSegment, fetching, isNewEntity]);

  // fetch related entities
  React.useEffect(() => {}, []);

  useDidUpdateEffect(() => {
    if (updating === false) {
      if (errorUpdating) {
        setError(errorUpdating && errorUpdating.detail ? errorUpdating.detail : 'Something went wrong updating the entity');
      } else if (updateSuccess) {
        setError('');
        isNewEntity || !navigation.canGoBack()
          ? navigation.replace('SleepSegmentDetail', { entityId: sleepSegment?.id })
          : navigation.pop();
      }
    }
  }, [updateSuccess, errorUpdating, navigation]);

  const onSubmit = (data) => updateSleepSegment(formValueToEntity(data));

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
  const fieldSleepSegmentTypeRef = createRef();
  const fieldBloodGlucoseSpecimenSourceRef = createRef();
  const startTimeRef = createRef();
  const endTimeRef = createRef();

  return (
    <View style={styles.container}>
      <KeyboardAwareScrollView
        enableResetScrollToCoords={false}
        testID="sleepSegmentEditScrollView"
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
              onSubmitEditing={() => fieldSleepSegmentTypeRef.current?.focus()}
            />
            <FormField
              name="fieldSleepSegmentType"
              ref={fieldSleepSegmentTypeRef}
              label="Field Sleep Segment Type"
              placeholder="Enter Field Sleep Segment Type"
              testID="fieldSleepSegmentTypeInput"
              inputType="number"
              onSubmitEditing={() => fieldBloodGlucoseSpecimenSourceRef.current?.focus()}
            />
            <FormField
              name="fieldBloodGlucoseSpecimenSource"
              ref={fieldBloodGlucoseSpecimenSourceRef}
              label="Field Blood Glucose Specimen Source"
              placeholder="Enter Field Blood Glucose Specimen Source"
              testID="fieldBloodGlucoseSpecimenSourceInput"
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
    fieldSleepSegmentType: value.fieldSleepSegmentType ?? null,
    fieldBloodGlucoseSpecimenSource: value.fieldBloodGlucoseSpecimenSource ?? null,
    startTime: value.startTime ?? null,
    endTime: value.endTime ?? null,
  };
};
const formValueToEntity = (value) => {
  const entity = {
    id: value.id ?? null,
    usuarioId: value.usuarioId ?? null,
    empresaId: value.empresaId ?? null,
    fieldSleepSegmentType: value.fieldSleepSegmentType ?? null,
    fieldBloodGlucoseSpecimenSource: value.fieldBloodGlucoseSpecimenSource ?? null,
    startTime: value.startTime ?? null,
    endTime: value.endTime ?? null,
  };
  return entity;
};

const mapStateToProps = (state) => {
  return {
    sleepSegment: state.sleepSegments.sleepSegment,
    fetching: state.sleepSegments.fetchingOne,
    updating: state.sleepSegments.updating,
    updateSuccess: state.sleepSegments.updateSuccess,
    errorUpdating: state.sleepSegments.errorUpdating,
  };
};

const mapDispatchToProps = (dispatch) => {
  return {
    getSleepSegment: (id) => dispatch(SleepSegmentActions.sleepSegmentRequest(id)),
    getAllSleepSegments: (options) => dispatch(SleepSegmentActions.sleepSegmentAllRequest(options)),
    updateSleepSegment: (sleepSegment) => dispatch(SleepSegmentActions.sleepSegmentUpdateRequest(sleepSegment)),
    reset: () => dispatch(SleepSegmentActions.sleepSegmentReset()),
  };
};

export default connect(mapStateToProps, mapDispatchToProps)(SleepSegmentEditScreen);
