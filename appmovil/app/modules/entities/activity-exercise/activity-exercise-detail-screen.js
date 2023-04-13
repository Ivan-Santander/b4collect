import React from 'react';
import { ActivityIndicator, ScrollView, Text, View } from 'react-native';
import { connect } from 'react-redux';
import { useFocusEffect } from '@react-navigation/native';

import ActivityExerciseActions from './activity-exercise.reducer';
import RoundedButton from '../../../shared/components/rounded-button/rounded-button';
import ActivityExerciseDeleteModal from './activity-exercise-delete-modal';
import styles from './activity-exercise-styles';

function ActivityExerciseDetailScreen(props) {
  const { route, getActivityExercise, navigation, activityExercise, fetching, error } = props;
  const [deleteModalVisible, setDeleteModalVisible] = React.useState(false);
  // prevents display of stale reducer data
  const entityId = activityExercise?.id ?? null;
  const routeEntityId = route.params?.entityId ?? null;
  const correctEntityLoaded = routeEntityId && entityId && routeEntityId.toString() === entityId.toString();

  useFocusEffect(
    React.useCallback(() => {
      if (!routeEntityId) {
        navigation.navigate('ActivityExercise');
      } else {
        setDeleteModalVisible(false);
        getActivityExercise(routeEntityId);
      }
    }, [routeEntityId, getActivityExercise, navigation]),
  );

  if (!entityId && !fetching && error) {
    return (
      <View style={styles.loading}>
        <Text>Something went wrong fetching the ActivityExercise.</Text>
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
    <ScrollView style={styles.container} contentContainerStyle={styles.paddedScrollView} testID="activityExerciseDetailScrollView">
      <Text style={styles.label}>Id:</Text>
      <Text>{activityExercise.id}</Text>
      {/* UsuarioId Field */}
      <Text style={styles.label}>UsuarioId:</Text>
      <Text testID="usuarioId">{activityExercise.usuarioId}</Text>
      {/* EmpresaId Field */}
      <Text style={styles.label}>EmpresaId:</Text>
      <Text testID="empresaId">{activityExercise.empresaId}</Text>
      {/* Exercise Field */}
      <Text style={styles.label}>Exercise:</Text>
      <Text testID="exercise">{activityExercise.exercise}</Text>
      {/* Repetitions Field */}
      <Text style={styles.label}>Repetitions:</Text>
      <Text testID="repetitions">{activityExercise.repetitions}</Text>
      {/* TypeResistence Field */}
      <Text style={styles.label}>TypeResistence:</Text>
      <Text testID="typeResistence">{activityExercise.typeResistence}</Text>
      {/* ResistenceKg Field */}
      <Text style={styles.label}>ResistenceKg:</Text>
      <Text testID="resistenceKg">{activityExercise.resistenceKg}</Text>
      {/* Duration Field */}
      <Text style={styles.label}>Duration:</Text>
      <Text testID="duration">{activityExercise.duration}</Text>
      {/* StartTime Field */}
      <Text style={styles.label}>StartTime:</Text>
      <Text testID="startTime">{String(activityExercise.startTime)}</Text>
      {/* EndTime Field */}
      <Text style={styles.label}>EndTime:</Text>
      <Text testID="endTime">{String(activityExercise.endTime)}</Text>

      <View style={styles.entityButtons}>
        <RoundedButton
          text="Edit"
          onPress={() => navigation.navigate('ActivityExerciseEdit', { entityId })}
          accessibilityLabel={'ActivityExercise Edit Button'}
          testID="activityExerciseEditButton"
        />
        <RoundedButton
          text="Delete"
          onPress={() => setDeleteModalVisible(true)}
          accessibilityLabel={'ActivityExercise Delete Button'}
          testID="activityExerciseDeleteButton"
        />
        {deleteModalVisible && (
          <ActivityExerciseDeleteModal
            navigation={navigation}
            visible={deleteModalVisible}
            setVisible={setDeleteModalVisible}
            entity={activityExercise}
            testID="activityExerciseDeleteModal"
          />
        )}
      </View>
    </ScrollView>
  );
}

const mapStateToProps = (state) => {
  return {
    activityExercise: state.activityExercises.activityExercise,
    error: state.activityExercises.errorOne,
    fetching: state.activityExercises.fetchingOne,
    deleting: state.activityExercises.deleting,
    errorDeleting: state.activityExercises.errorDeleting,
  };
};

const mapDispatchToProps = (dispatch) => {
  return {
    getActivityExercise: (id) => dispatch(ActivityExerciseActions.activityExerciseRequest(id)),
    getAllActivityExercises: (options) => dispatch(ActivityExerciseActions.activityExerciseAllRequest(options)),
    deleteActivityExercise: (id) => dispatch(ActivityExerciseActions.activityExerciseDeleteRequest(id)),
    resetActivityExercises: () => dispatch(ActivityExerciseActions.activityExerciseReset()),
  };
};

export default connect(mapStateToProps, mapDispatchToProps)(ActivityExerciseDetailScreen);
