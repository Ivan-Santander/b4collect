import React from 'react';
import { ActivityIndicator, ScrollView, Text, View } from 'react-native';
import { connect } from 'react-redux';
import { useFocusEffect } from '@react-navigation/native';

import BloodGlucoseSummaryActions from './blood-glucose-summary.reducer';
import RoundedButton from '../../../shared/components/rounded-button/rounded-button';
import BloodGlucoseSummaryDeleteModal from './blood-glucose-summary-delete-modal';
import styles from './blood-glucose-summary-styles';

function BloodGlucoseSummaryDetailScreen(props) {
  const { route, getBloodGlucoseSummary, navigation, bloodGlucoseSummary, fetching, error } = props;
  const [deleteModalVisible, setDeleteModalVisible] = React.useState(false);
  // prevents display of stale reducer data
  const entityId = bloodGlucoseSummary?.id ?? null;
  const routeEntityId = route.params?.entityId ?? null;
  const correctEntityLoaded = routeEntityId && entityId && routeEntityId.toString() === entityId.toString();

  useFocusEffect(
    React.useCallback(() => {
      if (!routeEntityId) {
        navigation.navigate('BloodGlucoseSummary');
      } else {
        setDeleteModalVisible(false);
        getBloodGlucoseSummary(routeEntityId);
      }
    }, [routeEntityId, getBloodGlucoseSummary, navigation]),
  );

  if (!entityId && !fetching && error) {
    return (
      <View style={styles.loading}>
        <Text>Something went wrong fetching the BloodGlucoseSummary.</Text>
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
    <ScrollView style={styles.container} contentContainerStyle={styles.paddedScrollView} testID="bloodGlucoseSummaryDetailScrollView">
      <Text style={styles.label}>Id:</Text>
      <Text>{bloodGlucoseSummary.id}</Text>
      {/* UsuarioId Field */}
      <Text style={styles.label}>UsuarioId:</Text>
      <Text testID="usuarioId">{bloodGlucoseSummary.usuarioId}</Text>
      {/* EmpresaId Field */}
      <Text style={styles.label}>EmpresaId:</Text>
      <Text testID="empresaId">{bloodGlucoseSummary.empresaId}</Text>
      {/* FieldAverage Field */}
      <Text style={styles.label}>FieldAverage:</Text>
      <Text testID="fieldAverage">{bloodGlucoseSummary.fieldAverage}</Text>
      {/* FieldMax Field */}
      <Text style={styles.label}>FieldMax:</Text>
      <Text testID="fieldMax">{bloodGlucoseSummary.fieldMax}</Text>
      {/* FieldMin Field */}
      <Text style={styles.label}>FieldMin:</Text>
      <Text testID="fieldMin">{bloodGlucoseSummary.fieldMin}</Text>
      {/* IntervalFood Field */}
      <Text style={styles.label}>IntervalFood:</Text>
      <Text testID="intervalFood">{bloodGlucoseSummary.intervalFood}</Text>
      {/* MealType Field */}
      <Text style={styles.label}>MealType:</Text>
      <Text testID="mealType">{bloodGlucoseSummary.mealType}</Text>
      {/* RelationTemporalSleep Field */}
      <Text style={styles.label}>RelationTemporalSleep:</Text>
      <Text testID="relationTemporalSleep">{bloodGlucoseSummary.relationTemporalSleep}</Text>
      {/* SampleSource Field */}
      <Text style={styles.label}>SampleSource:</Text>
      <Text testID="sampleSource">{bloodGlucoseSummary.sampleSource}</Text>
      {/* StartTime Field */}
      <Text style={styles.label}>StartTime:</Text>
      <Text testID="startTime">{String(bloodGlucoseSummary.startTime)}</Text>
      {/* EndTime Field */}
      <Text style={styles.label}>EndTime:</Text>
      <Text testID="endTime">{String(bloodGlucoseSummary.endTime)}</Text>

      <View style={styles.entityButtons}>
        <RoundedButton
          text="Edit"
          onPress={() => navigation.navigate('BloodGlucoseSummaryEdit', { entityId })}
          accessibilityLabel={'BloodGlucoseSummary Edit Button'}
          testID="bloodGlucoseSummaryEditButton"
        />
        <RoundedButton
          text="Delete"
          onPress={() => setDeleteModalVisible(true)}
          accessibilityLabel={'BloodGlucoseSummary Delete Button'}
          testID="bloodGlucoseSummaryDeleteButton"
        />
        {deleteModalVisible && (
          <BloodGlucoseSummaryDeleteModal
            navigation={navigation}
            visible={deleteModalVisible}
            setVisible={setDeleteModalVisible}
            entity={bloodGlucoseSummary}
            testID="bloodGlucoseSummaryDeleteModal"
          />
        )}
      </View>
    </ScrollView>
  );
}

const mapStateToProps = (state) => {
  return {
    bloodGlucoseSummary: state.bloodGlucoseSummaries.bloodGlucoseSummary,
    error: state.bloodGlucoseSummaries.errorOne,
    fetching: state.bloodGlucoseSummaries.fetchingOne,
    deleting: state.bloodGlucoseSummaries.deleting,
    errorDeleting: state.bloodGlucoseSummaries.errorDeleting,
  };
};

const mapDispatchToProps = (dispatch) => {
  return {
    getBloodGlucoseSummary: (id) => dispatch(BloodGlucoseSummaryActions.bloodGlucoseSummaryRequest(id)),
    getAllBloodGlucoseSummaries: (options) => dispatch(BloodGlucoseSummaryActions.bloodGlucoseSummaryAllRequest(options)),
    deleteBloodGlucoseSummary: (id) => dispatch(BloodGlucoseSummaryActions.bloodGlucoseSummaryDeleteRequest(id)),
    resetBloodGlucoseSummaries: () => dispatch(BloodGlucoseSummaryActions.bloodGlucoseSummaryReset()),
  };
};

export default connect(mapStateToProps, mapDispatchToProps)(BloodGlucoseSummaryDetailScreen);
