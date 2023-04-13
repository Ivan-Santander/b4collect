import React from 'react';
import { ActivityIndicator, ScrollView, Text, View } from 'react-native';
import { connect } from 'react-redux';
import { useFocusEffect } from '@react-navigation/native';

import NutritionActions from './nutrition.reducer';
import RoundedButton from '../../../shared/components/rounded-button/rounded-button';
import NutritionDeleteModal from './nutrition-delete-modal';
import styles from './nutrition-styles';

function NutritionDetailScreen(props) {
  const { route, getNutrition, navigation, nutrition, fetching, error } = props;
  const [deleteModalVisible, setDeleteModalVisible] = React.useState(false);
  // prevents display of stale reducer data
  const entityId = nutrition?.id ?? null;
  const routeEntityId = route.params?.entityId ?? null;
  const correctEntityLoaded = routeEntityId && entityId && routeEntityId.toString() === entityId.toString();

  useFocusEffect(
    React.useCallback(() => {
      if (!routeEntityId) {
        navigation.navigate('Nutrition');
      } else {
        setDeleteModalVisible(false);
        getNutrition(routeEntityId);
      }
    }, [routeEntityId, getNutrition, navigation]),
  );

  if (!entityId && !fetching && error) {
    return (
      <View style={styles.loading}>
        <Text>Something went wrong fetching the Nutrition.</Text>
      </View>
    );
  }
  if (!entityId || fetching || !correctEntityLoaded) {
    return (
      <View style={styles.loading}>
        <ActivityIndicator size="large" />
      </View>
    );
  }
  return (
    <ScrollView style={styles.container} contentContainerStyle={styles.paddedScrollView} testID="nutritionDetailScrollView">
      <Text style={styles.label}>Id:</Text>
      <Text>{nutrition.id}</Text>
      {/* UsuarioId Field */}
      <Text style={styles.label}>UsuarioId:</Text>
      <Text testID="usuarioId">{nutrition.usuarioId}</Text>
      {/* EmpresaId Field */}
      <Text style={styles.label}>EmpresaId:</Text>
      <Text testID="empresaId">{nutrition.empresaId}</Text>
      {/* MealType Field */}
      <Text style={styles.label}>MealType:</Text>
      <Text testID="mealType">{nutrition.mealType}</Text>
      {/* Food Field */}
      <Text style={styles.label}>Food:</Text>
      <Text testID="food">{nutrition.food}</Text>
      {/* Nutrients Field */}
      <Text style={styles.label}>Nutrients:</Text>
      <Text testID="nutrients">{nutrition.nutrients}</Text>
      {/* EndTime Field */}
      <Text style={styles.label}>EndTime:</Text>
      <Text testID="endTime">{String(nutrition.endTime)}</Text>

      <View style={styles.entityButtons}>
        <RoundedButton
          text="Edit"
          onPress={() => navigation.navigate('NutritionEdit', { entityId })}
          accessibilityLabel={'Nutrition Edit Button'}
          testID="nutritionEditButton"
        />
        <RoundedButton
          text="Delete"
          onPress={() => setDeleteModalVisible(true)}
          accessibilityLabel={'Nutrition Delete Button'}
          testID="nutritionDeleteButton"
        />
        {deleteModalVisible && (
          <NutritionDeleteModal
            navigation={navigation}
            visible={deleteModalVisible}
            setVisible={setDeleteModalVisible}
            entity={nutrition}
            testID="nutritionDeleteModal"
          />
        )}
      </View>
    </ScrollView>
  );
}

const mapStateToProps = (state) => {
  return {
    nutrition: state.nutritions.nutrition,
    error: state.nutritions.errorOne,
    fetching: state.nutritions.fetchingOne,
    deleting: state.nutritions.deleting,
    errorDeleting: state.nutritions.errorDeleting,
  };
};

const mapDispatchToProps = (dispatch) => {
  return {
    getNutrition: (id) => dispatch(NutritionActions.nutritionRequest(id)),
    getAllNutritions: (options) => dispatch(NutritionActions.nutritionAllRequest(options)),
    deleteNutrition: (id) => dispatch(NutritionActions.nutritionDeleteRequest(id)),
    resetNutritions: () => dispatch(NutritionActions.nutritionReset()),
  };
};

export default connect(mapStateToProps, mapDispatchToProps)(NutritionDetailScreen);
