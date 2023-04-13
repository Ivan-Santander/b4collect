import React from 'react';
import { ActivityIndicator, ScrollView, Text, View } from 'react-native';
import { connect } from 'react-redux';
import { useFocusEffect } from '@react-navigation/native';

import NutritionSummaryActions from './nutrition-summary.reducer';
import RoundedButton from '../../../shared/components/rounded-button/rounded-button';
import NutritionSummaryDeleteModal from './nutrition-summary-delete-modal';
import styles from './nutrition-summary-styles';

function NutritionSummaryDetailScreen(props) {
  const { route, getNutritionSummary, navigation, nutritionSummary, fetching, error } = props;
  const [deleteModalVisible, setDeleteModalVisible] = React.useState(false);
  // prevents display of stale reducer data
  const entityId = nutritionSummary?.id ?? null;
  const routeEntityId = route.params?.entityId ?? null;
  const correctEntityLoaded = routeEntityId && entityId && routeEntityId.toString() === entityId.toString();

  useFocusEffect(
    React.useCallback(() => {
      if (!routeEntityId) {
        navigation.navigate('NutritionSummary');
      } else {
        setDeleteModalVisible(false);
        getNutritionSummary(routeEntityId);
      }
    }, [routeEntityId, getNutritionSummary, navigation]),
  );

  if (!entityId && !fetching && error) {
    return (
      <View style={styles.loading}>
        <Text>Something went wrong fetching the NutritionSummary.</Text>
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
    <ScrollView style={styles.container} contentContainerStyle={styles.paddedScrollView} testID="nutritionSummaryDetailScrollView">
      <Text style={styles.label}>Id:</Text>
      <Text>{nutritionSummary.id}</Text>
      {/* UsuarioId Field */}
      <Text style={styles.label}>UsuarioId:</Text>
      <Text testID="usuarioId">{nutritionSummary.usuarioId}</Text>
      {/* EmpresaId Field */}
      <Text style={styles.label}>EmpresaId:</Text>
      <Text testID="empresaId">{nutritionSummary.empresaId}</Text>
      {/* MealType Field */}
      <Text style={styles.label}>MealType:</Text>
      <Text testID="mealType">{nutritionSummary.mealType}</Text>
      {/* Nutrient Field */}
      <Text style={styles.label}>Nutrient:</Text>
      <Text testID="nutrient">{nutritionSummary.nutrient}</Text>
      {/* StartTime Field */}
      <Text style={styles.label}>StartTime:</Text>
      <Text testID="startTime">{String(nutritionSummary.startTime)}</Text>
      {/* EndTime Field */}
      <Text style={styles.label}>EndTime:</Text>
      <Text testID="endTime">{String(nutritionSummary.endTime)}</Text>

      <View style={styles.entityButtons}>
        <RoundedButton
          text="Edit"
          onPress={() => navigation.navigate('NutritionSummaryEdit', { entityId })}
          accessibilityLabel={'NutritionSummary Edit Button'}
          testID="nutritionSummaryEditButton"
        />
        <RoundedButton
          text="Delete"
          onPress={() => setDeleteModalVisible(true)}
          accessibilityLabel={'NutritionSummary Delete Button'}
          testID="nutritionSummaryDeleteButton"
        />
        {deleteModalVisible && (
          <NutritionSummaryDeleteModal
            navigation={navigation}
            visible={deleteModalVisible}
            setVisible={setDeleteModalVisible}
            entity={nutritionSummary}
            testID="nutritionSummaryDeleteModal"
          />
        )}
      </View>
    </ScrollView>
  );
}

const mapStateToProps = (state) => {
  return {
    nutritionSummary: state.nutritionSummaries.nutritionSummary,
    error: state.nutritionSummaries.errorOne,
    fetching: state.nutritionSummaries.fetchingOne,
    deleting: state.nutritionSummaries.deleting,
    errorDeleting: state.nutritionSummaries.errorDeleting,
  };
};

const mapDispatchToProps = (dispatch) => {
  return {
    getNutritionSummary: (id) => dispatch(NutritionSummaryActions.nutritionSummaryRequest(id)),
    getAllNutritionSummaries: (options) => dispatch(NutritionSummaryActions.nutritionSummaryAllRequest(options)),
    deleteNutritionSummary: (id) => dispatch(NutritionSummaryActions.nutritionSummaryDeleteRequest(id)),
    resetNutritionSummaries: () => dispatch(NutritionSummaryActions.nutritionSummaryReset()),
  };
};

export default connect(mapStateToProps, mapDispatchToProps)(NutritionSummaryDetailScreen);
