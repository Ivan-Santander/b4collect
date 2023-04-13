import React from 'react';
import { ActivityIndicator, ScrollView, Text, View } from 'react-native';
import { connect } from 'react-redux';
import { useFocusEffect } from '@react-navigation/native';

import ActivitySummaryActions from './activity-summary.reducer';
import RoundedButton from '../../../shared/components/rounded-button/rounded-button';
import ActivitySummaryDeleteModal from './activity-summary-delete-modal';
import styles from './activity-summary-styles';

function ActivitySummaryDetailScreen(props) {
  const { route, getActivitySummary, navigation, activitySummary, fetching, error } = props;
  const [deleteModalVisible, setDeleteModalVisible] = React.useState(false);
  // prevents display of stale reducer data
  const entityId = activitySummary?.id ?? null;
  const routeEntityId = route.params?.entityId ?? null;
  const correctEntityLoaded = routeEntityId && entityId && routeEntityId.toString() === entityId.toString();

  useFocusEffect(
    React.useCallback(() => {
      if (!routeEntityId) {
        navigation.navigate('ActivitySummary');
      } else {
        setDeleteModalVisible(false);
        getActivitySummary(routeEntityId);
      }
    }, [routeEntityId, getActivitySummary, navigation]),
  );

  if (!entityId && !fetching && error) {
    return (
      <View style={styles.loading}>
        <Text>Something went wrong fetching the ActivitySummary.</Text>
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
    <ScrollView style={styles.container} contentContainerStyle={styles.paddedScrollView} testID="activitySummaryDetailScrollView">
      <Text style={styles.label}>Id:</Text>
      <Text>{activitySummary.id}</Text>
      {/* UsuarioId Field */}
      <Text style={styles.label}>UsuarioId:</Text>
      <Text testID="usuarioId">{activitySummary.usuarioId}</Text>
      {/* EmpresaId Field */}
      <Text style={styles.label}>EmpresaId:</Text>
      <Text testID="empresaId">{activitySummary.empresaId}</Text>
      {/* FieldActivity Field */}
      <Text style={styles.label}>FieldActivity:</Text>
      <Text testID="fieldActivity">{activitySummary.fieldActivity}</Text>
      {/* FieldDuration Field */}
      <Text style={styles.label}>FieldDuration:</Text>
      <Text testID="fieldDuration">{activitySummary.fieldDuration}</Text>
      {/* FieldNumSegments Field */}
      <Text style={styles.label}>FieldNumSegments:</Text>
      <Text testID="fieldNumSegments">{activitySummary.fieldNumSegments}</Text>
      {/* StartTime Field */}
      <Text style={styles.label}>StartTime:</Text>
      <Text testID="startTime">{String(activitySummary.startTime)}</Text>
      {/* EndTime Field */}
      <Text style={styles.label}>EndTime:</Text>
      <Text testID="endTime">{String(activitySummary.endTime)}</Text>

      <View style={styles.entityButtons}>
        <RoundedButton
          text="Edit"
          onPress={() => navigation.navigate('ActivitySummaryEdit', { entityId })}
          accessibilityLabel={'ActivitySummary Edit Button'}
          testID="activitySummaryEditButton"
        />
        <RoundedButton
          text="Delete"
          onPress={() => setDeleteModalVisible(true)}
          accessibilityLabel={'ActivitySummary Delete Button'}
          testID="activitySummaryDeleteButton"
        />
        {deleteModalVisible && (
          <ActivitySummaryDeleteModal
            navigation={navigation}
            visible={deleteModalVisible}
            setVisible={setDeleteModalVisible}
            entity={activitySummary}
            testID="activitySummaryDeleteModal"
          />
        )}
      </View>
    </ScrollView>
  );
}

const mapStateToProps = (state) => {
  return {
    activitySummary: state.activitySummaries.activitySummary,
    error: state.activitySummaries.errorOne,
    fetching: state.activitySummaries.fetchingOne,
    deleting: state.activitySummaries.deleting,
    errorDeleting: state.activitySummaries.errorDeleting,
  };
};

const mapDispatchToProps = (dispatch) => {
  return {
    getActivitySummary: (id) => dispatch(ActivitySummaryActions.activitySummaryRequest(id)),
    getAllActivitySummaries: (options) => dispatch(ActivitySummaryActions.activitySummaryAllRequest(options)),
    deleteActivitySummary: (id) => dispatch(ActivitySummaryActions.activitySummaryDeleteRequest(id)),
    resetActivitySummaries: () => dispatch(ActivitySummaryActions.activitySummaryReset()),
  };
};

export default connect(mapStateToProps, mapDispatchToProps)(ActivitySummaryDetailScreen);
