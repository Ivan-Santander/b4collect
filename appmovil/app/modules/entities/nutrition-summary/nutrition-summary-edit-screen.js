import React, { createRef } from 'react';
import { ActivityIndicator, Text, View } from 'react-native';
import { connect } from 'react-redux';

import NutritionSummaryActions from './nutrition-summary.reducer';
import { KeyboardAwareScrollView } from 'react-native-keyboard-aware-scroll-view';
import FormButton from '../../../shared/components/form/jhi-form-button';
import FormField from '../../../shared/components/form/jhi-form-field';
import Form from '../../../shared/components/form/jhi-form';
import { useDidUpdateEffect } from '../../../shared/util/use-did-update-effect';
import styles from './nutrition-summary-styles';

function NutritionSummaryEditScreen(props) {
  const {
    getNutritionSummary,
    updateNutritionSummary,
    route,
    nutritionSummary,
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
      getNutritionSummary(route.params.entityId);
    } else {
      reset();
    }
  }, [isNewEntity, getNutritionSummary, route, reset]);

  React.useEffect(() => {
    if (isNewEntity) {
      setFormValue(entityToFormValue({}));
    } else if (!fetching) {
      setFormValue(entityToFormValue(nutritionSummary));
    }
  }, [nutritionSummary, fetching, isNewEntity]);

  // fetch related entities
  React.useEffect(() => {}, []);

  useDidUpdateEffect(() => {
    if (updating === false) {
      if (errorUpdating) {
        setError(errorUpdating && errorUpdating.detail ? errorUpdating.detail : 'Something went wrong updating the entity');
      } else if (updateSuccess) {
        setError('');
        isNewEntity || !navigation.canGoBack()
          ? navigation.replace('NutritionSummaryDetail', { entityId: nutritionSummary?.id })
          : navigation.pop();
      }
    }
  }, [updateSuccess, errorUpdating, navigation]);

  const onSubmit = (data) => updateNutritionSummary(formValueToEntity(data));

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
  const mealTypeRef = createRef();
  const nutrientRef = createRef();
  const startTimeRef = createRef();
  const endTimeRef = createRef();

  return (
    <View style={styles.container}>
      <KeyboardAwareScrollView
        enableResetScrollToCoords={false}
        testID="nutritionSummaryEditScrollView"
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
              onSubmitEditing={() => mealTypeRef.current?.focus()}
            />
            <FormField
              name="mealType"
              ref={mealTypeRef}
              label="Meal Type"
              placeholder="Enter Meal Type"
              testID="mealTypeInput"
              onSubmitEditing={() => nutrientRef.current?.focus()}
            />
            <FormField
              name="nutrient"
              ref={nutrientRef}
              label="Nutrient"
              placeholder="Enter Nutrient"
              testID="nutrientInput"
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
    mealType: value.mealType ?? null,
    nutrient: value.nutrient ?? null,
    startTime: value.startTime ?? null,
    endTime: value.endTime ?? null,
  };
};
const formValueToEntity = (value) => {
  const entity = {
    id: value.id ?? null,
    usuarioId: value.usuarioId ?? null,
    empresaId: value.empresaId ?? null,
    mealType: value.mealType ?? null,
    nutrient: value.nutrient ?? null,
    startTime: value.startTime ?? null,
    endTime: value.endTime ?? null,
  };
  return entity;
};

const mapStateToProps = (state) => {
  return {
    nutritionSummary: state.nutritionSummaries.nutritionSummary,
    fetching: state.nutritionSummaries.fetchingOne,
    updating: state.nutritionSummaries.updating,
    updateSuccess: state.nutritionSummaries.updateSuccess,
    errorUpdating: state.nutritionSummaries.errorUpdating,
  };
};

const mapDispatchToProps = (dispatch) => {
  return {
    getNutritionSummary: (id) => dispatch(NutritionSummaryActions.nutritionSummaryRequest(id)),
    getAllNutritionSummaries: (options) => dispatch(NutritionSummaryActions.nutritionSummaryAllRequest(options)),
    updateNutritionSummary: (nutritionSummary) => dispatch(NutritionSummaryActions.nutritionSummaryUpdateRequest(nutritionSummary)),
    reset: () => dispatch(NutritionSummaryActions.nutritionSummaryReset()),
  };
};

export default connect(mapStateToProps, mapDispatchToProps)(NutritionSummaryEditScreen);
