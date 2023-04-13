import React from 'react';
import { ActivityIndicator, ScrollView, Text, View } from 'react-native';
import { connect } from 'react-redux';
import { useFocusEffect } from '@react-navigation/native';

import PowerSummaryActions from './power-summary.reducer';
import RoundedButton from '../../../shared/components/rounded-button/rounded-button';
import PowerSummaryDeleteModal from './power-summary-delete-modal';
import styles from './power-summary-styles';

function PowerSummaryDetailScreen(props) {
  const { route, getPowerSummary, navigation, powerSummary, fetching, error } = props;
  const [deleteModalVisible, setDeleteModalVisible] = React.useState(false);
  // prevents display of stale reducer data
  const entityId = powerSummary?.id ?? null;
  const routeEntityId = route.params?.entityId ?? null;
  const correctEntityLoaded = routeEntityId && entityId && routeEntityId.toString() === entityId.toString();

  useFocusEffect(
    React.useCallback(() => {
      if (!routeEntityId) {
        navigation.navigate('PowerSummary');
      } else {
        setDeleteModalVisible(false);
        getPowerSummary(routeEntityId);
      }
    }, [routeEntityId, getPowerSummary, navigation]),
  );

  if (!entityId && !fetching && error) {
    return (
      <View style={styles.loading}>
        <Text>Something went wrong fetching the PowerSummary.</Text>
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
    <ScrollView style={styles.container} contentContainerStyle={styles.paddedScrollView} testID="powerSummaryDetailScrollView">
      <Text style={styles.label}>Id:</Text>
      <Text>{powerSummary.id}</Text>
      {/* UsuarioId Field */}
      <Text style={styles.label}>UsuarioId:</Text>
      <Text testID="usuarioId">{powerSummary.usuarioId}</Text>
      {/* EmpresaId Field */}
      <Text style={styles.label}>EmpresaId:</Text>
      <Text testID="empresaId">{powerSummary.empresaId}</Text>
      {/* FieldAverage Field */}
      <Text style={styles.label}>FieldAverage:</Text>
      <Text testID="fieldAverage">{powerSummary.fieldAverage}</Text>
      {/* FieldMax Field */}
      <Text style={styles.label}>FieldMax:</Text>
      <Text testID="fieldMax">{powerSummary.fieldMax}</Text>
      {/* FieldMin Field */}
      <Text style={styles.label}>FieldMin:</Text>
      <Text testID="fieldMin">{powerSummary.fieldMin}</Text>
      {/* StartTime Field */}
      <Text style={styles.label}>StartTime:</Text>
      <Text testID="startTime">{String(powerSummary.startTime)}</Text>
      {/* EndTime Field */}
      <Text style={styles.label}>EndTime:</Text>
      <Text testID="endTime">{String(powerSummary.endTime)}</Text>

      <View style={styles.entityButtons}>
        <RoundedButton
          text="Edit"
          onPress={() => navigation.navigate('PowerSummaryEdit', { entityId })}
          accessibilityLabel={'PowerSummary Edit Button'}
          testID="powerSummaryEditButton"
        />
        <RoundedButton
          text="Delete"
          onPress={() => setDeleteModalVisible(true)}
          accessibilityLabel={'PowerSummary Delete Button'}
          testID="powerSummaryDeleteButton"
        />
        {deleteModalVisible && (
          <PowerSummaryDeleteModal
            navigation={navigation}
            visible={deleteModalVisible}
            setVisible={setDeleteModalVisible}
            entity={powerSummary}
            testID="powerSummaryDeleteModal"
          />
        )}
      </View>
    </ScrollView>
  );
}

const mapStateToProps = (state) => {
  return {
    powerSummary: state.powerSummaries.powerSummary,
    error: state.powerSummaries.errorOne,
    fetching: state.powerSummaries.fetchingOne,
    deleting: state.powerSummaries.deleting,
    errorDeleting: state.powerSummaries.errorDeleting,
  };
};

const mapDispatchToProps = (dispatch) => {
  return {
    getPowerSummary: (id) => dispatch(PowerSummaryActions.powerSummaryRequest(id)),
    getAllPowerSummaries: (options) => dispatch(PowerSummaryActions.powerSummaryAllRequest(options)),
    deletePowerSummary: (id) => dispatch(PowerSummaryActions.powerSummaryDeleteRequest(id)),
    resetPowerSummaries: () => dispatch(PowerSummaryActions.powerSummaryReset()),
  };
};

export default connect(mapStateToProps, mapDispatchToProps)(PowerSummaryDetailScreen);
