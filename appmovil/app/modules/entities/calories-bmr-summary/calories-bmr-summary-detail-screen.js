import React from 'react';
import { ActivityIndicator, ScrollView, Text, View } from 'react-native';
import { connect } from 'react-redux';
import { useFocusEffect } from '@react-navigation/native';

import CaloriesBmrSummaryActions from './calories-bmr-summary.reducer';
import RoundedButton from '../../../shared/components/rounded-button/rounded-button';
import CaloriesBmrSummaryDeleteModal from './calories-bmr-summary-delete-modal';
import styles from './calories-bmr-summary-styles';

function CaloriesBmrSummaryDetailScreen(props) {
  const { route, getCaloriesBmrSummary, navigation, caloriesBmrSummary, fetching, error } = props;
  const [deleteModalVisible, setDeleteModalVisible] = React.useState(false);
  // prevents display of stale reducer data
  const entityId = caloriesBmrSummary?.id ?? null;
  const routeEntityId = route.params?.entityId ?? null;
  const correctEntityLoaded = routeEntityId && entityId && routeEntityId.toString() === entityId.toString();

  useFocusEffect(
    React.useCallback(() => {
      if (!routeEntityId) {
        navigation.navigate('CaloriesBmrSummary');
      } else {
        setDeleteModalVisible(false);
        getCaloriesBmrSummary(routeEntityId);
      }
    }, [routeEntityId, getCaloriesBmrSummary, navigation]),
  );

  if (!entityId && !fetching && error) {
    return (
      <View style={styles.loading}>
        <Text>Something went wrong fetching the CaloriesBmrSummary.</Text>
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
    <ScrollView style={styles.container} contentContainerStyle={styles.paddedScrollView} testID="caloriesBmrSummaryDetailScrollView">
      <Text style={styles.label}>Id:</Text>
      <Text>{caloriesBmrSummary.id}</Text>
      {/* UsuarioId Field */}
      <Text style={styles.label}>UsuarioId:</Text>
      <Text testID="usuarioId">{caloriesBmrSummary.usuarioId}</Text>
      {/* EmpresaId Field */}
      <Text style={styles.label}>EmpresaId:</Text>
      <Text testID="empresaId">{caloriesBmrSummary.empresaId}</Text>
      {/* FieldAverage Field */}
      <Text style={styles.label}>FieldAverage:</Text>
      <Text testID="fieldAverage">{caloriesBmrSummary.fieldAverage}</Text>
      {/* FieldMax Field */}
      <Text style={styles.label}>FieldMax:</Text>
      <Text testID="fieldMax">{caloriesBmrSummary.fieldMax}</Text>
      {/* FieldMin Field */}
      <Text style={styles.label}>FieldMin:</Text>
      <Text testID="fieldMin">{caloriesBmrSummary.fieldMin}</Text>
      {/* StartTime Field */}
      <Text style={styles.label}>StartTime:</Text>
      <Text testID="startTime">{String(caloriesBmrSummary.startTime)}</Text>
      {/* EndTime Field */}
      <Text style={styles.label}>EndTime:</Text>
      <Text testID="endTime">{String(caloriesBmrSummary.endTime)}</Text>

      <View style={styles.entityButtons}>
        <RoundedButton
          text="Edit"
          onPress={() => navigation.navigate('CaloriesBmrSummaryEdit', { entityId })}
          accessibilityLabel={'CaloriesBmrSummary Edit Button'}
          testID="caloriesBmrSummaryEditButton"
        />
        <RoundedButton
          text="Delete"
          onPress={() => setDeleteModalVisible(true)}
          accessibilityLabel={'CaloriesBmrSummary Delete Button'}
          testID="caloriesBmrSummaryDeleteButton"
        />
        {deleteModalVisible && (
          <CaloriesBmrSummaryDeleteModal
            navigation={navigation}
            visible={deleteModalVisible}
            setVisible={setDeleteModalVisible}
            entity={caloriesBmrSummary}
            testID="caloriesBmrSummaryDeleteModal"
          />
        )}
      </View>
    </ScrollView>
  );
}

const mapStateToProps = (state) => {
  return {
    caloriesBmrSummary: state.caloriesBmrSummaries.caloriesBmrSummary,
    error: state.caloriesBmrSummaries.errorOne,
    fetching: state.caloriesBmrSummaries.fetchingOne,
    deleting: state.caloriesBmrSummaries.deleting,
    errorDeleting: state.caloriesBmrSummaries.errorDeleting,
  };
};

const mapDispatchToProps = (dispatch) => {
  return {
    getCaloriesBmrSummary: (id) => dispatch(CaloriesBmrSummaryActions.caloriesBmrSummaryRequest(id)),
    getAllCaloriesBmrSummaries: (options) => dispatch(CaloriesBmrSummaryActions.caloriesBmrSummaryAllRequest(options)),
    deleteCaloriesBmrSummary: (id) => dispatch(CaloriesBmrSummaryActions.caloriesBmrSummaryDeleteRequest(id)),
    resetCaloriesBmrSummaries: () => dispatch(CaloriesBmrSummaryActions.caloriesBmrSummaryReset()),
  };
};

export default connect(mapStateToProps, mapDispatchToProps)(CaloriesBmrSummaryDetailScreen);
