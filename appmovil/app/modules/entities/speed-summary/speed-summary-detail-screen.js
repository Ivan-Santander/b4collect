import React from 'react';
import { ActivityIndicator, ScrollView, Text, View } from 'react-native';
import { connect } from 'react-redux';
import { useFocusEffect } from '@react-navigation/native';

import SpeedSummaryActions from './speed-summary.reducer';
import RoundedButton from '../../../shared/components/rounded-button/rounded-button';
import SpeedSummaryDeleteModal from './speed-summary-delete-modal';
import styles from './speed-summary-styles';

function SpeedSummaryDetailScreen(props) {
  const { route, getSpeedSummary, navigation, speedSummary, fetching, error } = props;
  const [deleteModalVisible, setDeleteModalVisible] = React.useState(false);
  // prevents display of stale reducer data
  const entityId = speedSummary?.id ?? null;
  const routeEntityId = route.params?.entityId ?? null;
  const correctEntityLoaded = routeEntityId && entityId && routeEntityId.toString() === entityId.toString();

  useFocusEffect(
    React.useCallback(() => {
      if (!routeEntityId) {
        navigation.navigate('SpeedSummary');
      } else {
        setDeleteModalVisible(false);
        getSpeedSummary(routeEntityId);
      }
    }, [routeEntityId, getSpeedSummary, navigation]),
  );

  if (!entityId && !fetching && error) {
    return (
      <View style={styles.loading}>
        <Text>Something went wrong fetching the SpeedSummary.</Text>
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
    <ScrollView style={styles.container} contentContainerStyle={styles.paddedScrollView} testID="speedSummaryDetailScrollView">
      <Text style={styles.label}>Id:</Text>
      <Text>{speedSummary.id}</Text>
      {/* UsuarioId Field */}
      <Text style={styles.label}>UsuarioId:</Text>
      <Text testID="usuarioId">{speedSummary.usuarioId}</Text>
      {/* EmpresaId Field */}
      <Text style={styles.label}>EmpresaId:</Text>
      <Text testID="empresaId">{speedSummary.empresaId}</Text>
      {/* FieldAverage Field */}
      <Text style={styles.label}>FieldAverage:</Text>
      <Text testID="fieldAverage">{speedSummary.fieldAverage}</Text>
      {/* FieldMax Field */}
      <Text style={styles.label}>FieldMax:</Text>
      <Text testID="fieldMax">{speedSummary.fieldMax}</Text>
      {/* FieldMin Field */}
      <Text style={styles.label}>FieldMin:</Text>
      <Text testID="fieldMin">{speedSummary.fieldMin}</Text>
      {/* StartTime Field */}
      <Text style={styles.label}>StartTime:</Text>
      <Text testID="startTime">{String(speedSummary.startTime)}</Text>
      {/* EndTime Field */}
      <Text style={styles.label}>EndTime:</Text>
      <Text testID="endTime">{String(speedSummary.endTime)}</Text>

      <View style={styles.entityButtons}>
        <RoundedButton
          text="Edit"
          onPress={() => navigation.navigate('SpeedSummaryEdit', { entityId })}
          accessibilityLabel={'SpeedSummary Edit Button'}
          testID="speedSummaryEditButton"
        />
        <RoundedButton
          text="Delete"
          onPress={() => setDeleteModalVisible(true)}
          accessibilityLabel={'SpeedSummary Delete Button'}
          testID="speedSummaryDeleteButton"
        />
        {deleteModalVisible && (
          <SpeedSummaryDeleteModal
            navigation={navigation}
            visible={deleteModalVisible}
            setVisible={setDeleteModalVisible}
            entity={speedSummary}
            testID="speedSummaryDeleteModal"
          />
        )}
      </View>
    </ScrollView>
  );
}

const mapStateToProps = (state) => {
  return {
    speedSummary: state.speedSummaries.speedSummary,
    error: state.speedSummaries.errorOne,
    fetching: state.speedSummaries.fetchingOne,
    deleting: state.speedSummaries.deleting,
    errorDeleting: state.speedSummaries.errorDeleting,
  };
};

const mapDispatchToProps = (dispatch) => {
  return {
    getSpeedSummary: (id) => dispatch(SpeedSummaryActions.speedSummaryRequest(id)),
    getAllSpeedSummaries: (options) => dispatch(SpeedSummaryActions.speedSummaryAllRequest(options)),
    deleteSpeedSummary: (id) => dispatch(SpeedSummaryActions.speedSummaryDeleteRequest(id)),
    resetSpeedSummaries: () => dispatch(SpeedSummaryActions.speedSummaryReset()),
  };
};

export default connect(mapStateToProps, mapDispatchToProps)(SpeedSummaryDetailScreen);
