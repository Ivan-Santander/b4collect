import React, { createRef } from 'react';
import { ActivityIndicator, Text, View } from 'react-native';
import { connect } from 'react-redux';

import NutritionActions from './nutrition.reducer';
import { KeyboardAwareScrollView } from 'react-native-keyboard-aware-scroll-view';
import FormButton from '../../../shared/components/form/jhi-form-button';
import FormField from '../../../shared/components/form/jhi-form-field';
import Form from '../../../shared/components/form/jhi-form';
import { useDidUpdateEffect } from '../../../shared/util/use-did-update-effect';
import styles from './nutrition-styles';

function NutritionEditScreen(props) {
  const { getNutrition, updateNutrition, route, nutrition, fetching, updating, errorUpdating, updateSuccess, navigation, reset } = props;

  const [formValue, setFormValue] = React.useState();
  const [error, setError] = React.useState('');

  const isNewEntity = !(route.params && route.params.entityId);

  React.useEffect(() => {
    if (!isNewEntity) {
      getNutrition(route.params.entityId);
    } else {
      reset();
    }
  }, [isNewEntity, getNutrition, route, reset]);

  React.useEffect(() => {
    if (isNewEntity) {
      setFormValue(entityToFormValue({}));
    } else if (!fetching) {
      setFormValue(entityToFormValue(nutrition));
    }
  }, [nutrition, fetching, isNewEntity]);

  // fetch related entities
  React.useEffect(() => {}, []);

  useDidUpdateEffect(() => {
    if (updating === false) {
      if (errorUpdating) {
        setError(errorUpdating && errorUpdating.detail ? errorUpdating.detail : 'Something went wrong updating the entity');
      } else if (updateSuccess) {
        setError('');
        isNewEntity || !navigation.canGoBack() ? navigation.replace('NutritionDetail', { entityId: nutrition?.id }) : navigation.pop();
      }
    }
  }, [updateSuccess, errorUpdating, navigation]);

  const onSubmit = (data) => updateNutrition(formValueToEntity(data));

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
  const foodRef = createRef();
  const nutrientsRef = createRef();
  const endTimeRef = createRef();

  return (
    <View style={styles.container}>
      <KeyboardAwareScrollView
        enableResetScrollToCoords={false}
        testID="nutritionEditScrollView"
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
              inputType="number"
              onSubmitEditing={() => foodRef.current?.focus()}
            />
            <FormField
              name="food"
              ref={foodRef}
              label="Food"
              placeholder="Enter Food"
              testID="foodInput"
              onSubmitEditing={() => nutrientsRef.current?.focus()}
            />
            <FormField
              name="nutrients"
              ref={nutrientsRef}
              label="Nutrients"
              placeholder="Enter Nutrients"
              testID="nutrientsInput"
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
    food: value.food ?? null,
    nutrients: value.nutrients ?? null,
    endTime: value.endTime ?? null,
  };
};
const formValueToEntity = (value) => {
  const entity = {
    id: value.id ?? null,
    usuarioId: value.usuarioId ?? null,
    empresaId: value.empresaId ?? null,
    mealType: value.mealType ?? null,
    food: value.food ?? null,
    nutrients: value.nutrients ?? null,
    endTime: value.endTime ?? null,
  };
  return entity;
};

const mapStateToProps = (state) => {
  return {
    nutrition: state.nutritions.nutrition,
    fetching: state.nutritions.fetchingOne,
    updating: state.nutritions.updating,
    updateSuccess: state.nutritions.updateSuccess,
    errorUpdating: state.nutritions.errorUpdating,
  };
};

const mapDispatchToProps = (dispatch) => {
  return {
    getNutrition: (id) => dispatch(NutritionActions.nutritionRequest(id)),
    getAllNutritions: (options) => dispatch(NutritionActions.nutritionAllRequest(options)),
    updateNutrition: (nutrition) => dispatch(NutritionActions.nutritionUpdateRequest(nutrition)),
    reset: () => dispatch(NutritionActions.nutritionReset()),
  };
};

export default connect(mapStateToProps, mapDispatchToProps)(NutritionEditScreen);
