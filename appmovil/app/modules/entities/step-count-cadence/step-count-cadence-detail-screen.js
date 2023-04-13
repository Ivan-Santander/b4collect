import React from 'react';
import { ActivityIndicator, ScrollView, Text, View } from 'react-native';
import { connect } from 'react-redux';
import { useFocusEffect } from '@react-navigation/native';

import StepCountCadenceActions from './step-count-cadence.reducer';
import RoundedButton from '../../../shared/components/rounded-button/rounded-button';
import StepCountCadenceDeleteModal from './step-count-cadence-delete-modal';
import styles from './step-count-cadence-styles';

function StepCountCadenceDetailScreen(props) {
  const { route, getStepCountCadence, navigation, stepCountCadence, fetching, error } = props;
  const [deleteModalVisible, setDeleteModalVisible] = React.useState(false);
  // prevents display of stale reducer data
  const entityId = stepCountCadence?.id ?? null;
  const routeEntityId = route.params?.entityId ?? null;
  const correctEntityLoaded = routeEntityId && entityId && routeEntityId.toString() === entityId.toString();

  useFocusEffect(
    React.useCallback(() => {
      if (!routeEntityId) {
        navigation.navigate('StepCountCadence');
      } else {
        setDeleteModalVisible(false);
        getStepCountCadence(routeEntityId);
      }
    }, [routeEntityId, getStepCountCadence, navigation]),
  );

  if (!entityId && !fetching && error) {
    return (
      <View style={styles.loading}>
        <Text>Something went wrong fetching the StepCountCadence.</Text>
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
    <ScrollView style={styles.container} contentContainerStyle={styles.paddedScrollView} testID="stepCountCadenceDetailScrollView">
      <Text style={styles.label}>Id:</Text>
      <Text>{stepCountCadence.id}</Text>
      {/* UsuarioId Field */}
      <Text style={styles.label}>UsuarioId:</Text>
      <Text testID="usuarioId">{stepCountCadence.usuarioId}</Text>
      {/* EmpresaId Field */}
      <Text style={styles.label}>EmpresaId:</Text>
      <Text testID="empresaId">{stepCountCadence.empresaId}</Text>
      {/* Rpm Field */}
      <Text style={styles.label}>Rpm:</Text>
      <Text testID="rpm">{stepCountCadence.rpm}</Text>
      {/* StartTime Field */}
      <Text style={styles.label}>StartTime:</Text>
      <Text testID="startTime">{String(stepCountCadence.startTime)}</Text>
      {/* EndTime Field */}
      <Text style={styles.label}>EndTime:</Text>
      <Text testID="endTime">{String(stepCountCadence.endTime)}</Text>

      <View style={styles.entityButtons}>
        <RoundedButton
          text="Edit"
          onPress={() => navigation.navigate('StepCountCadenceEdit', { entityId })}
          accessibilityLabel={'StepCountCadence Edit Button'}
          testID="stepCountCadenceEditButton"
        />
        <RoundedButton
          text="Delete"
          onPress={() => setDeleteModalVisible(true)}
          accessibilityLabel={'StepCountCadence Delete Button'}
          testID="stepCountCadenceDeleteButton"
        />
        {deleteModalVisible && (
          <StepCountCadenceDeleteModal
            navigation={navigation}
            visible={deleteModalVisible}
            setVisible={setDeleteModalVisible}
            entity={stepCountCadence}
            testID="stepCountCadenceDeleteModal"
          />
        )}
      </View>
    </ScrollView>
  );
}

const mapStateToProps = (state) => {
  return {
    stepCountCadence: state.stepCountCadences.stepCountCadence,
    error: state.stepCountCadences.errorOne,
    fetching: state.stepCountCadences.fetchingOne,
    deleting: state.stepCountCadences.deleting,
    errorDeleting: state.stepCountCadences.errorDeleting,
  };
};

const mapDispatchToProps = (dispatch) => {
  return {
    getStepCountCadence: (id) => dispatch(StepCountCadenceActions.stepCountCadenceRequest(id)),
    getAllStepCountCadences: (options) => dispatch(StepCountCadenceActions.stepCountCadenceAllRequest(options)),
    deleteStepCountCadence: (id) => dispatch(StepCountCadenceActions.stepCountCadenceDeleteRequest(id)),
    resetStepCountCadences: () => dispatch(StepCountCadenceActions.stepCountCadenceReset()),
  };
};

export default connect(mapStateToProps, mapDispatchToProps)(StepCountCadenceDetailScreen);
