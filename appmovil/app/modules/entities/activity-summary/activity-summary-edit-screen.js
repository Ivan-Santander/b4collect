import React, { createRef } from 'react';
import { ActivityIndicator, Text, View } from 'react-native';
import { connect } from 'react-redux';

import ActivitySummaryActions from './activity-summary.reducer';
import { KeyboardAwareScrollView } from 'react-native-keyboard-aware-scroll-view';
import FormButton from '../../../shared/components/form/jhi-form-button';
import FormField from '../../../shared/components/form/jhi-form-field';
import Form from '../../../shared/components/form/jhi-form';
import { useDidUpdateEffect } from '../../../shared/util/use-did-update-effect';
import styles from './activity-summary-styles';

function ActivitySummaryEditScreen(props) {
  const {
    getActivitySummary,
    updateActivitySummary,
    route,
    activitySummary,
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
      getActivitySummary(route.params.entityId);
    } else {
      reset();
    }
  }, [isNewEntity, getActivitySummary, route, reset]);

  React.useEffect(() => {
    if (isNewEntity) {
      setFormValue(entityToFormValue({}));
    } else if (!fetching) {
      setFormValue(entityToFormValue(activitySummary));
    }
  }, [activitySummary, fetching, isNewEntity]);

  // fetch related entities
  React.useEffect(() => {}, []);

  useDidUpdateEffect(() => {
    if (updating === false) {
      if (errorUpdating) {
        setError(errorUpdating && errorUpdating.detail ? errorUpdating.detail : 'Something went wrong updating the entity');
      } else if (updateSuccess) {
        setError('');
        isNewEntity || !navigation.canGoBack()
          ? navigation.replace('ActivitySummaryDetail', { entityId: activitySummary?.id })
          : navigation.pop();
      }
    }
  }, [updateSuccess, errorUpdating, navigation]);

  const onSubmit = (data) => updateActivitySummary(formValueToEntity(data));

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
  const fieldActivityRef = createRef();
  const fieldDurationRef = createRef();
  const fieldNumSegmentsRef = createRef();
  const startTimeRef = createRef();
  const endTimeRef = createRef();

  return (
    <View style={styles.container}>
      <KeyboardAwareScrollView
        enableResetScrollToCoords={false}
        testID="activitySummaryEditScrollView"
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
              onSubmitEditing={() => fieldActivityRef.current?.focus()}
            />
            <FormField
              name="fieldActivity"
              ref={fieldActivityRef}
              label="Field Activity"
              placeholder="Enter Field Activity"
              testID="fieldActivityInput"
              inputType="number"
              onSubmitEditing={() => fieldDurationRef.current?.focus()}
            />
            <FormField
              name="fieldDuration"
              ref={fieldDurationRef}
              label="Field Duration"
              placeholder="Enter Field Duration"
              testID="fieldDurationInput"
              inputType="number"
              onSubmitEditing={() => fieldNumSegmentsRef.current?.focus()}
            />
            <FormField
              name="fieldNumSegments"
              ref={fieldNumSegmentsRef}
              label="Field Num Segments"
              placeholder="Enter Field Num Segments"
              testID="fieldNumSegmentsInput"
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
    fieldActivity: value.fieldActivity ?? null,
    fieldDuration: value.fieldDuration ?? null,
    fieldNumSegments: value.fieldNumSegments ?? null,
    startTime: value.startTime ?? null,
    endTime: value.endTime ?? null,
  };
};
const formValueToEntity = (value) => {
  const entity = {
    id: value.id ?? null,
    usuarioId: value.usuarioId ?? null,
    empresaId: value.empresaId ?? null,
    fieldActivity: value.fieldActivity ?? null,
    fieldDuration: value.fieldDuration ?? null,
    fieldNumSegments: value.fieldNumSegments ?? null,
    startTime: value.startTime ?? null,
    endTime: value.endTime ?? null,
  };
  return entity;
};

const mapStateToProps = (state) => {
  return {
    activitySummary: state.activitySummaries.activitySummary,
    fetching: state.activitySummaries.fetchingOne,
    updating: state.activitySummaries.updating,
    updateSuccess: state.activitySummaries.updateSuccess,
    errorUpdating: state.activitySummaries.errorUpdating,
  };
};

const mapDispatchToProps = (dispatch) => {
  return {
    getActivitySummary: (id) => dispatch(ActivitySummaryActions.activitySummaryRequest(id)),
    getAllActivitySummaries: (options) => dispatch(ActivitySummaryActions.activitySummaryAllRequest(options)),
    updateActivitySummary: (activitySummary) => dispatch(ActivitySummaryActions.activitySummaryUpdateRequest(activitySummary)),
    reset: () => dispatch(ActivitySummaryActions.activitySummaryReset()),
  };
};

export default connect(mapStateToProps, mapDispatchToProps)(ActivitySummaryEditScreen);
