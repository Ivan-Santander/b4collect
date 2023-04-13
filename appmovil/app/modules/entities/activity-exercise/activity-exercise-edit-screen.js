import React, { createRef } from 'react';
import { ActivityIndicator, Text, View } from 'react-native';
import { connect } from 'react-redux';

import ActivityExerciseActions from './activity-exercise.reducer';
import { KeyboardAwareScrollView } from 'react-native-keyboard-aware-scroll-view';
import FormButton from '../../../shared/components/form/jhi-form-button';
import FormField from '../../../shared/components/form/jhi-form-field';
import Form from '../../../shared/components/form/jhi-form';
import { useDidUpdateEffect } from '../../../shared/util/use-did-update-effect';
import styles from './activity-exercise-styles';

function ActivityExerciseEditScreen(props) {
  const {
    getActivityExercise,
    updateActivityExercise,
    route,
    activityExercise,
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
      getActivityExercise(route.params.entityId);
    } else {
      reset();
    }
  }, [isNewEntity, getActivityExercise, route, reset]);

  React.useEffect(() => {
    if (isNewEntity) {
      setFormValue(entityToFormValue({}));
    } else if (!fetching) {
      setFormValue(entityToFormValue(activityExercise));
    }
  }, [activityExercise, fetching, isNewEntity]);

  // fetch related entities
  React.useEffect(() => {}, []);

  useDidUpdateEffect(() => {
    if (updating === false) {
      if (errorUpdating) {
        setError(errorUpdating && errorUpdating.detail ? errorUpdating.detail : 'Something went wrong updating the entity');
      } else if (updateSuccess) {
        setError('');
        isNewEntity || !navigation.canGoBack()
          ? navigation.replace('ActivityExerciseDetail', { entityId: activityExercise?.id })
          : navigation.pop();
      }
    }
  }, [updateSuccess, errorUpdating, navigation]);

  const onSubmit = (data) => updateActivityExercise(formValueToEntity(data));

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
  const exerciseRef = createRef();
  const repetitionsRef = createRef();
  const typeResistenceRef = createRef();
  const resistenceKgRef = createRef();
  const durationRef = createRef();
  const startTimeRef = createRef();
  const endTimeRef = createRef();

  return (
    <View style={styles.container}>
      <KeyboardAwareScrollView
        enableResetScrollToCoords={false}
        testID="activityExerciseEditScrollView"
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
              onSubmitEditing={() => exerciseRef.current?.focus()}
            />
            <FormField
              name="exercise"
              ref={exerciseRef}
              label="Exercise"
              placeholder="Enter Exercise"
              testID="exerciseInput"
              inputType="text"
              autoCapitalize="none"
              onSubmitEditing={() => repetitionsRef.current?.focus()}
            />
            <FormField
              name="repetitions"
              ref={repetitionsRef}
              label="Repetitions"
              placeholder="Enter Repetitions"
              testID="repetitionsInput"
              inputType="number"
              onSubmitEditing={() => typeResistenceRef.current?.focus()}
            />
            <FormField
              name="typeResistence"
              ref={typeResistenceRef}
              label="Type Resistence"
              placeholder="Enter Type Resistence"
              testID="typeResistenceInput"
              inputType="text"
              autoCapitalize="none"
              onSubmitEditing={() => resistenceKgRef.current?.focus()}
            />
            <FormField
              name="resistenceKg"
              ref={resistenceKgRef}
              label="Resistence Kg"
              placeholder="Enter Resistence Kg"
              testID="resistenceKgInput"
              inputType="number"
              onSubmitEditing={() => durationRef.current?.focus()}
            />
            <FormField
              name="duration"
              ref={durationRef}
              label="Duration"
              placeholder="Enter Duration"
              testID="durationInput"
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
    exercise: value.exercise ?? null,
    repetitions: value.repetitions ?? null,
    typeResistence: value.typeResistence ?? null,
    resistenceKg: value.resistenceKg ?? null,
    duration: value.duration ?? null,
    startTime: value.startTime ?? null,
    endTime: value.endTime ?? null,
  };
};
const formValueToEntity = (value) => {
  const entity = {
    id: value.id ?? null,
    usuarioId: value.usuarioId ?? null,
    empresaId: value.empresaId ?? null,
    exercise: value.exercise ?? null,
    repetitions: value.repetitions ?? null,
    typeResistence: value.typeResistence ?? null,
    resistenceKg: value.resistenceKg ?? null,
    duration: value.duration ?? null,
    startTime: value.startTime ?? null,
    endTime: value.endTime ?? null,
  };
  return entity;
};

const mapStateToProps = (state) => {
  return {
    activityExercise: state.activityExercises.activityExercise,
    fetching: state.activityExercises.fetchingOne,
    updating: state.activityExercises.updating,
    updateSuccess: state.activityExercises.updateSuccess,
    errorUpdating: state.activityExercises.errorUpdating,
  };
};

const mapDispatchToProps = (dispatch) => {
  return {
    getActivityExercise: (id) => dispatch(ActivityExerciseActions.activityExerciseRequest(id)),
    getAllActivityExercises: (options) => dispatch(ActivityExerciseActions.activityExerciseAllRequest(options)),
    updateActivityExercise: (activityExercise) => dispatch(ActivityExerciseActions.activityExerciseUpdateRequest(activityExercise)),
    reset: () => dispatch(ActivityExerciseActions.activityExerciseReset()),
  };
};

export default connect(mapStateToProps, mapDispatchToProps)(ActivityExerciseEditScreen);
