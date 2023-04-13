import React from 'react';
import { ActivityIndicator, ScrollView, Text, View } from 'react-native';
import { connect } from 'react-redux';
import { useFocusEffect } from '@react-navigation/native';

import HeartRateSummaryActions from './heart-rate-summary.reducer';
import RoundedButton from '../../../shared/components/rounded-button/rounded-button';
import HeartRateSummaryDeleteModal from './heart-rate-summary-delete-modal';
import styles from './heart-rate-summary-styles';

function HeartRateSummaryDetailScreen(props) {
  const { route, getHeartRateSummary, navigation, heartRateSummary, fetching, error } = props;
  const [deleteModalVisible, setDeleteModalVisible] = React.useState(false);
  // prevents display of stale reducer data
  const entityId = heartRateSummary?.id ?? null;
  const routeEntityId = route.params?.entityId ?? null;
  const correctEntityLoaded = routeEntityId && entityId && routeEntityId.toString() === entityId.toString();

  useFocusEffect(
    React.useCallback(() => {
      if (!routeEntityId) {
        navigation.navigate('HeartRateSummary');
      } else {
        setDeleteModalVisible(false);
        getHeartRateSummary(routeEntityId);
      }
    }, [routeEntityId, getHeartRateSummary, navigation]),
  );

  if (!entityId && !fetching && error) {
    return (
      <View style={styles.loading}>
        <Text>Something went wrong fetching the HeartRateSummary.</Text>
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
    <ScrollView style={styles.container} contentContainerStyle={styles.paddedScrollView} testID="heartRateSummaryDetailScrollView">
      <Text style={styles.label}>Id:</Text>
      <Text>{heartRateSummary.id}</Text>
      {/* UsuarioId Field */}
      <Text style={styles.label}>UsuarioId:</Text>
      <Text testID="usuarioId">{heartRateSummary.usuarioId}</Text>
      {/* EmpresaId Field */}
      <Text style={styles.label}>EmpresaId:</Text>
      <Text testID="empresaId">{heartRateSummary.empresaId}</Text>
      {/* FieldAverage Field */}
      <Text style={styles.label}>FieldAverage:</Text>
      <Text testID="fieldAverage">{heartRateSummary.fieldAverage}</Text>
      {/* FieldMax Field */}
      <Text style={styles.label}>FieldMax:</Text>
      <Text testID="fieldMax">{heartRateSummary.fieldMax}</Text>
      {/* FieldMin Field */}
      <Text style={styles.label}>FieldMin:</Text>
      <Text testID="fieldMin">{heartRateSummary.fieldMin}</Text>
      {/* StartTime Field */}
      <Text style={styles.label}>StartTime:</Text>
      <Text testID="startTime">{String(heartRateSummary.startTime)}</Text>
      {/* EndTime Field */}
      <Text style={styles.label}>EndTime:</Text>
      <Text testID="endTime">{String(heartRateSummary.endTime)}</Text>

      <View style={styles.entityButtons}>
        <RoundedButton
          text="Edit"
          onPress={() => navigation.navigate('HeartRateSummaryEdit', { entityId })}
          accessibilityLabel={'HeartRateSummary Edit Button'}
          testID="heartRateSummaryEditButton"
        />
        <RoundedButton
          text="Delete"
          onPress={() => setDeleteModalVisible(true)}
          accessibilityLabel={'HeartRateSummary Delete Button'}
          testID="heartRateSummaryDeleteButton"
        />
        {deleteModalVisible && (
          <HeartRateSummaryDeleteModal
            navigation={navigation}
            visible={deleteModalVisible}
            setVisible={setDeleteModalVisible}
            entity={heartRateSummary}
            testID="heartRateSummaryDeleteModal"
          />
        )}
      </View>
    </ScrollView>
  );
}

const mapStateToProps = (state) => {
  return {
    heartRateSummary: state.heartRateSummaries.heartRateSummary,
    error: state.heartRateSummaries.errorOne,
    fetching: state.heartRateSummaries.fetchingOne,
    deleting: state.heartRateSummaries.deleting,
    errorDeleting: state.heartRateSummaries.errorDeleting,
  };
};

const mapDispatchToProps = (dispatch) => {
  return {
    getHeartRateSummary: (id) => dispatch(HeartRateSummaryActions.heartRateSummaryRequest(id)),
    getAllHeartRateSummaries: (options) => dispatch(HeartRateSummaryActions.heartRateSummaryAllRequest(options)),
    deleteHeartRateSummary: (id) => dispatch(HeartRateSummaryActions.heartRateSummaryDeleteRequest(id)),
    resetHeartRateSummaries: () => dispatch(HeartRateSummaryActions.heartRateSummaryReset()),
  };
};

export default connect(mapStateToProps, mapDispatchToProps)(HeartRateSummaryDetailScreen);
