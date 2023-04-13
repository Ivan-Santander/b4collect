import React, { createRef } from 'react';
import { ActivityIndicator, Text, View } from 'react-native';
import { connect } from 'react-redux';

import BloodGlucoseActions from './blood-glucose.reducer';
import { KeyboardAwareScrollView } from 'react-native-keyboard-aware-scroll-view';
import FormButton from '../../../shared/components/form/jhi-form-button';
import FormField from '../../../shared/components/form/jhi-form-field';
import Form from '../../../shared/components/form/jhi-form';
import { useDidUpdateEffect } from '../../../shared/util/use-did-update-effect';
import styles from './blood-glucose-styles';

function BloodGlucoseEditScreen(props) {
  const { getBloodGlucose, updateBloodGlucose, route, bloodGlucose, fetching, updating, errorUpdating, updateSuccess, navigation, reset } =
    props;

  const [formValue, setFormValue] = React.useState();
  const [error, setError] = React.useState('');

  const isNewEntity = !(route.params && route.params.entityId);

  React.useEffect(() => {
    if (!isNewEntity) {
      getBloodGlucose(route.params.entityId);
    } else {
      reset();
    }
  }, [isNewEntity, getBloodGlucose, route, reset]);

  React.useEffect(() => {
    if (isNewEntity) {
      setFormValue(entityToFormValue({}));
    } else if (!fetching) {
      setFormValue(entityToFormValue(bloodGlucose));
    }
  }, [bloodGlucose, fetching, isNewEntity]);

  // fetch related entities
  React.useEffect(() => {}, []);

  useDidUpdateEffect(() => {
    if (updating === false) {
      if (errorUpdating) {
        setError(errorUpdating && errorUpdating.detail ? errorUpdating.detail : 'Something went wrong updating the entity');
      } else if (updateSuccess) {
        setError('');
        isNewEntity || !navigation.canGoBack()
          ? navigation.replace('BloodGlucoseDetail', { entityId: bloodGlucose?.id })
          : navigation.pop();
      }
    }
  }, [updateSuccess, errorUpdating, navigation]);

  const onSubmit = (data) => updateBloodGlucose(formValueToEntity(data));

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
  const fieldBloodGlucoseLevelRef = createRef();
  const fieldTemporalRelationToMealRef = createRef();
  const fieldMealTypeRef = createRef();
  const fieldTemporalRelationToSleepRef = createRef();
  const fieldBloodGlucoseSpecimenSourceRef = createRef();
  const endTimeRef = createRef();

  return (
    <View style={styles.container}>
      <KeyboardAwareScrollView
        enableResetScrollToCoords={false}
        testID="bloodGlucoseEditScrollView"
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
              onSubmitEditing={() => fieldBloodGlucoseLevelRef.current?.focus()}
            />
            <FormField
              name="fieldBloodGlucoseLevel"
              ref={fieldBloodGlucoseLevelRef}
              label="Field Blood Glucose Level"
              placeholder="Enter Field Blood Glucose Level"
              testID="fieldBloodGlucoseLevelInput"
              inputType="number"
              onSubmitEditing={() => fieldTemporalRelationToMealRef.current?.focus()}
            />
            <FormField
              name="fieldTemporalRelationToMeal"
              ref={fieldTemporalRelationToMealRef}
              label="Field Temporal Relation To Meal"
              placeholder="Enter Field Temporal Relation To Meal"
              testID="fieldTemporalRelationToMealInput"
              inputType="number"
              onSubmitEditing={() => fieldMealTypeRef.current?.focus()}
            />
            <FormField
              name="fieldMealType"
              ref={fieldMealTypeRef}
              label="Field Meal Type"
              placeholder="Enter Field Meal Type"
              testID="fieldMealTypeInput"
              inputType="number"
              onSubmitEditing={() => fieldTemporalRelationToSleepRef.current?.focus()}
            />
            <FormField
              name="fieldTemporalRelationToSleep"
              ref={fieldTemporalRelationToSleepRef}
              label="Field Temporal Relation To Sleep"
              placeholder="Enter Field Temporal Relation To Sleep"
              testID="fieldTemporalRelationToSleepInput"
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
    fieldBloodGlucoseLevel: value.fieldBloodGlucoseLevel ?? null,
    fieldTemporalRelationToMeal: value.fieldTemporalRelationToMeal ?? null,
    fieldMealType: value.fieldMealType ?? null,
    fieldTemporalRelationToSleep: value.fieldTemporalRelationToSleep ?? null,
    fieldBloodGlucoseSpecimenSource: value.fieldBloodGlucoseSpecimenSource ?? null,
    endTime: value.endTime ?? null,
  };
};
const formValueToEntity = (value) => {
  const entity = {
    id: value.id ?? null,
    usuarioId: value.usuarioId ?? null,
    empresaId: value.empresaId ?? null,
    fieldBloodGlucoseLevel: value.fieldBloodGlucoseLevel ?? null,
    fieldTemporalRelationToMeal: value.fieldTemporalRelationToMeal ?? null,
    fieldMealType: value.fieldMealType ?? null,
    fieldTemporalRelationToSleep: value.fieldTemporalRelationToSleep ?? null,
    fieldBloodGlucoseSpecimenSource: value.fieldBloodGlucoseSpecimenSource ?? null,
    endTime: value.endTime ?? null,
  };
  return entity;
};

const mapStateToProps = (state) => {
  return {
    bloodGlucose: state.bloodGlucoses.bloodGlucose,
    fetching: state.bloodGlucoses.fetchingOne,
    updating: state.bloodGlucoses.updating,
    updateSuccess: state.bloodGlucoses.updateSuccess,
    errorUpdating: state.bloodGlucoses.errorUpdating,
  };
};

const mapDispatchToProps = (dispatch) => {
  return {
    getBloodGlucose: (id) => dispatch(BloodGlucoseActions.bloodGlucoseRequest(id)),
    getAllBloodGlucoses: (options) => dispatch(BloodGlucoseActions.bloodGlucoseAllRequest(options)),
    updateBloodGlucose: (bloodGlucose) => dispatch(BloodGlucoseActions.bloodGlucoseUpdateRequest(bloodGlucose)),
    reset: () => dispatch(BloodGlucoseActions.bloodGlucoseReset()),
  };
};

export default connect(mapStateToProps, mapDispatchToProps)(BloodGlucoseEditScreen);
