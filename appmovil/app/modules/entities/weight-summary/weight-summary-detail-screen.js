import React from 'react';
import { ActivityIndicator, ScrollView, Text, View } from 'react-native';
import { connect } from 'react-redux';
import { useFocusEffect } from '@react-navigation/native';

import WeightSummaryActions from './weight-summary.reducer';
import RoundedButton from '../../../shared/components/rounded-button/rounded-button';
import WeightSummaryDeleteModal from './weight-summary-delete-modal';
import styles from './weight-summary-styles';

function WeightSummaryDetailScreen(props) {
  const { route, getWeightSummary, navigation, weightSummary, fetching, error } = props;
  const [deleteModalVisible, setDeleteModalVisible] = React.useState(false);
  // prevents display of stale reducer data
  const entityId = weightSummary?.id ?? null;
  const routeEntityId = route.params?.entityId ?? null;
  const correctEntityLoaded = routeEntityId && entityId && routeEntityId.toString() === entityId.toString();

  useFocusEffect(
    React.useCallback(() => {
      if (!routeEntityId) {
        navigation.navigate('WeightSummary');
      } else {
        setDeleteModalVisible(false);
        getWeightSummary(routeEntityId);
      }
    }, [routeEntityId, getWeightSummary, navigation]),
  );

  if (!entityId && !fetching && error) {
    return (
      <View style={styles.loading}>
        <Text>Something went wrong fetching the WeightSummary.</Text>
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
    <ScrollView style={styles.container} contentContainerStyle={styles.paddedScrollView} testID="weightSummaryDetailScrollView">
      <Text style={styles.label}>Id:</Text>
      <Text>{weightSummary.id}</Text>
      {/* UsuarioId Field */}
      <Text style={styles.label}>UsuarioId:</Text>
      <Text testID="usuarioId">{weightSummary.usuarioId}</Text>
      {/* EmpresaId Field */}
      <Text style={styles.label}>EmpresaId:</Text>
      <Text testID="empresaId">{weightSummary.empresaId}</Text>
      {/* FieldAverage Field */}
      <Text style={styles.label}>FieldAverage:</Text>
      <Text testID="fieldAverage">{weightSummary.fieldAverage}</Text>
      {/* FieldMax Field */}
      <Text style={styles.label}>FieldMax:</Text>
      <Text testID="fieldMax">{weightSummary.fieldMax}</Text>
      {/* FieldMin Field */}
      <Text style={styles.label}>FieldMin:</Text>
      <Text testID="fieldMin">{weightSummary.fieldMin}</Text>
      {/* StartTime Field */}
      <Text style={styles.label}>StartTime:</Text>
      <Text testID="startTime">{String(weightSummary.startTime)}</Text>
      {/* EndTime Field */}
      <Text style={styles.label}>EndTime:</Text>
      <Text testID="endTime">{String(weightSummary.endTime)}</Text>

      <View style={styles.entityButtons}>
        <RoundedButton
          text="Edit"
          onPress={() => navigation.navigate('WeightSummaryEdit', { entityId })}
          accessibilityLabel={'WeightSummary Edit Button'}
          testID="weightSummaryEditButton"
        />
        <RoundedButton
          text="Delete"
          onPress={() => setDeleteModalVisible(true)}
          accessibilityLabel={'WeightSummary Delete Button'}
          testID="weightSummaryDeleteButton"
        />
        {deleteModalVisible && (
          <WeightSummaryDeleteModal
            navigation={navigation}
            visible={deleteModalVisible}
            setVisible={setDeleteModalVisible}
            entity={weightSummary}
            testID="weightSummaryDeleteModal"
          />
        )}
      </View>
    </ScrollView>
  );
}

const mapStateToProps = (state) => {
  return {
    weightSummary: state.weightSummaries.weightSummary,
    error: state.weightSummaries.errorOne,
    fetching: state.weightSummaries.fetchingOne,
    deleting: state.weightSummaries.deleting,
    errorDeleting: state.weightSummaries.errorDeleting,
  };
};

const mapDispatchToProps = (dispatch) => {
  return {
    getWeightSummary: (id) => dispatch(WeightSummaryActions.weightSummaryRequest(id)),
    getAllWeightSummaries: (options) => dispatch(WeightSummaryActions.weightSummaryAllRequest(options)),
    deleteWeightSummary: (id) => dispatch(WeightSummaryActions.weightSummaryDeleteRequest(id)),
    resetWeightSummaries: () => dispatch(WeightSummaryActions.weightSummaryReset()),
  };
};

export default connect(mapStateToProps, mapDispatchToProps)(WeightSummaryDetailScreen);
