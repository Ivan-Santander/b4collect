import React from 'react';
import { ActivityIndicator, ScrollView, Text, View } from 'react-native';
import { connect } from 'react-redux';
import { useFocusEffect } from '@react-navigation/native';

import StepCountDeltaActions from './step-count-delta.reducer';
import RoundedButton from '../../../shared/components/rounded-button/rounded-button';
import StepCountDeltaDeleteModal from './step-count-delta-delete-modal';
import styles from './step-count-delta-styles';

function StepCountDeltaDetailScreen(props) {
  const { route, getStepCountDelta, navigation, stepCountDelta, fetching, error } = props;
  const [deleteModalVisible, setDeleteModalVisible] = React.useState(false);
  // prevents display of stale reducer data
  const entityId = stepCountDelta?.id ?? null;
  const routeEntityId = route.params?.entityId ?? null;
  const correctEntityLoaded = routeEntityId && entityId && routeEntityId.toString() === entityId.toString();

  useFocusEffect(
    React.useCallback(() => {
      if (!routeEntityId) {
        navigation.navigate('StepCountDelta');
      } else {
        setDeleteModalVisible(false);
        getStepCountDelta(routeEntityId);
      }
    }, [routeEntityId, getStepCountDelta, navigation]),
  );

  if (!entityId && !fetching && error) {
    return (
      <View style={styles.loading}>
        <Text>Something went wrong fetching the StepCountDelta.</Text>
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
    <ScrollView style={styles.container} contentContainerStyle={styles.paddedScrollView} testID="stepCountDeltaDetailScrollView">
      <Text style={styles.label}>Id:</Text>
      <Text>{stepCountDelta.id}</Text>
      {/* UsuarioId Field */}
      <Text style={styles.label}>UsuarioId:</Text>
      <Text testID="usuarioId">{stepCountDelta.usuarioId}</Text>
      {/* EmpresaId Field */}
      <Text style={styles.label}>EmpresaId:</Text>
      <Text testID="empresaId">{stepCountDelta.empresaId}</Text>
      {/* Steps Field */}
      <Text style={styles.label}>Steps:</Text>
      <Text testID="steps">{stepCountDelta.steps}</Text>
      {/* StartTime Field */}
      <Text style={styles.label}>StartTime:</Text>
      <Text testID="startTime">{String(stepCountDelta.startTime)}</Text>
      {/* EndTime Field */}
      <Text style={styles.label}>EndTime:</Text>
      <Text testID="endTime">{String(stepCountDelta.endTime)}</Text>

      <View style={styles.entityButtons}>
        <RoundedButton
          text="Edit"
          onPress={() => navigation.navigate('StepCountDeltaEdit', { entityId })}
          accessibilityLabel={'StepCountDelta Edit Button'}
          testID="stepCountDeltaEditButton"
        />
        <RoundedButton
          text="Delete"
          onPress={() => setDeleteModalVisible(true)}
          accessibilityLabel={'StepCountDelta Delete Button'}
          testID="stepCountDeltaDeleteButton"
        />
        {deleteModalVisible && (
          <StepCountDeltaDeleteModal
            navigation={navigation}
            visible={deleteModalVisible}
            setVisible={setDeleteModalVisible}
            entity={stepCountDelta}
            testID="stepCountDeltaDeleteModal"
          />
        )}
      </View>
    </ScrollView>
  );
}

const mapStateToProps = (state) => {
  return {
    stepCountDelta: state.stepCountDeltas.stepCountDelta,
    error: state.stepCountDeltas.errorOne,
    fetching: state.stepCountDeltas.fetchingOne,
    deleting: state.stepCountDeltas.deleting,
    errorDeleting: state.stepCountDeltas.errorDeleting,
  };
};

const mapDispatchToProps = (dispatch) => {
  return {
    getStepCountDelta: (id) => dispatch(StepCountDeltaActions.stepCountDeltaRequest(id)),
    getAllStepCountDeltas: (options) => dispatch(StepCountDeltaActions.stepCountDeltaAllRequest(options)),
    deleteStepCountDelta: (id) => dispatch(StepCountDeltaActions.stepCountDeltaDeleteRequest(id)),
    resetStepCountDeltas: () => dispatch(StepCountDeltaActions.stepCountDeltaReset()),
  };
};

export default connect(mapStateToProps, mapDispatchToProps)(StepCountDeltaDetailScreen);
