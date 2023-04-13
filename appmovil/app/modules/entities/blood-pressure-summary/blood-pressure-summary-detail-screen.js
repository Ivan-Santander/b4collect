import React from 'react';
import { ActivityIndicator, ScrollView, Text, View } from 'react-native';
import { connect } from 'react-redux';
import { useFocusEffect } from '@react-navigation/native';

import BloodPressureSummaryActions from './blood-pressure-summary.reducer';
import RoundedButton from '../../../shared/components/rounded-button/rounded-button';
import BloodPressureSummaryDeleteModal from './blood-pressure-summary-delete-modal';
import styles from './blood-pressure-summary-styles';

function BloodPressureSummaryDetailScreen(props) {
  const { route, getBloodPressureSummary, navigation, bloodPressureSummary, fetching, error } = props;
  const [deleteModalVisible, setDeleteModalVisible] = React.useState(false);
  // prevents display of stale reducer data
  const entityId = bloodPressureSummary?.id ?? null;
  const routeEntityId = route.params?.entityId ?? null;
  const correctEntityLoaded = routeEntityId && entityId && routeEntityId.toString() === entityId.toString();

  useFocusEffect(
    React.useCallback(() => {
      if (!routeEntityId) {
        navigation.navigate('BloodPressureSummary');
      } else {
        setDeleteModalVisible(false);
        getBloodPressureSummary(routeEntityId);
      }
    }, [routeEntityId, getBloodPressureSummary, navigation]),
  );

  if (!entityId && !fetching && error) {
    return (
      <View style={styles.loading}>
        <Text>Something went wrong fetching the BloodPressureSummary.</Text>
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
    <ScrollView style={styles.container} contentContainerStyle={styles.paddedScrollView} testID="bloodPressureSummaryDetailScrollView">
      <Text style={styles.label}>Id:</Text>
      <Text>{bloodPressureSummary.id}</Text>
      {/* UsuarioId Field */}
      <Text style={styles.label}>UsuarioId:</Text>
      <Text testID="usuarioId">{bloodPressureSummary.usuarioId}</Text>
      {/* EmpresaId Field */}
      <Text style={styles.label}>EmpresaId:</Text>
      <Text testID="empresaId">{bloodPressureSummary.empresaId}</Text>
      {/* FieldSistolicAverage Field */}
      <Text style={styles.label}>FieldSistolicAverage:</Text>
      <Text testID="fieldSistolicAverage">{bloodPressureSummary.fieldSistolicAverage}</Text>
      {/* FieldSistolicMax Field */}
      <Text style={styles.label}>FieldSistolicMax:</Text>
      <Text testID="fieldSistolicMax">{bloodPressureSummary.fieldSistolicMax}</Text>
      {/* FieldSistolicMin Field */}
      <Text style={styles.label}>FieldSistolicMin:</Text>
      <Text testID="fieldSistolicMin">{bloodPressureSummary.fieldSistolicMin}</Text>
      {/* FieldDiasatolicAverage Field */}
      <Text style={styles.label}>FieldDiasatolicAverage:</Text>
      <Text testID="fieldDiasatolicAverage">{bloodPressureSummary.fieldDiasatolicAverage}</Text>
      {/* FieldDiastolicMax Field */}
      <Text style={styles.label}>FieldDiastolicMax:</Text>
      <Text testID="fieldDiastolicMax">{bloodPressureSummary.fieldDiastolicMax}</Text>
      {/* FieldDiastolicMin Field */}
      <Text style={styles.label}>FieldDiastolicMin:</Text>
      <Text testID="fieldDiastolicMin">{bloodPressureSummary.fieldDiastolicMin}</Text>
      {/* BodyPosition Field */}
      <Text style={styles.label}>BodyPosition:</Text>
      <Text testID="bodyPosition">{bloodPressureSummary.bodyPosition}</Text>
      {/* MeasurementLocation Field */}
      <Text style={styles.label}>MeasurementLocation:</Text>
      <Text testID="measurementLocation">{bloodPressureSummary.measurementLocation}</Text>
      {/* StartTime Field */}
      <Text style={styles.label}>StartTime:</Text>
      <Text testID="startTime">{String(bloodPressureSummary.startTime)}</Text>
      {/* EndTime Field */}
      <Text style={styles.label}>EndTime:</Text>
      <Text testID="endTime">{String(bloodPressureSummary.endTime)}</Text>

      <View style={styles.entityButtons}>
        <RoundedButton
          text="Edit"
          onPress={() => navigation.navigate('BloodPressureSummaryEdit', { entityId })}
          accessibilityLabel={'BloodPressureSummary Edit Button'}
          testID="bloodPressureSummaryEditButton"
        />
        <RoundedButton
          text="Delete"
          onPress={() => setDeleteModalVisible(true)}
          accessibilityLabel={'BloodPressureSummary Delete Button'}
          testID="bloodPressureSummaryDeleteButton"
        />
        {deleteModalVisible && (
          <BloodPressureSummaryDeleteModal
            navigation={navigation}
            visible={deleteModalVisible}
            setVisible={setDeleteModalVisible}
            entity={bloodPressureSummary}
            testID="bloodPressureSummaryDeleteModal"
          />
        )}
      </View>
    </ScrollView>
  );
}

const mapStateToProps = (state) => {
  return {
    bloodPressureSummary: state.bloodPressureSummaries.bloodPressureSummary,
    error: state.bloodPressureSummaries.errorOne,
    fetching: state.bloodPressureSummaries.fetchingOne,
    deleting: state.bloodPressureSummaries.deleting,
    errorDeleting: state.bloodPressureSummaries.errorDeleting,
  };
};

const mapDispatchToProps = (dispatch) => {
  return {
    getBloodPressureSummary: (id) => dispatch(BloodPressureSummaryActions.bloodPressureSummaryRequest(id)),
    getAllBloodPressureSummaries: (options) => dispatch(BloodPressureSummaryActions.bloodPressureSummaryAllRequest(options)),
    deleteBloodPressureSummary: (id) => dispatch(BloodPressureSummaryActions.bloodPressureSummaryDeleteRequest(id)),
    resetBloodPressureSummaries: () => dispatch(BloodPressureSummaryActions.bloodPressureSummaryReset()),
  };
};

export default connect(mapStateToProps, mapDispatchToProps)(BloodPressureSummaryDetailScreen);
