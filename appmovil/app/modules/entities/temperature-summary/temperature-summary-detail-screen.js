import React from 'react';
import { ActivityIndicator, ScrollView, Text, View } from 'react-native';
import { connect } from 'react-redux';
import { useFocusEffect } from '@react-navigation/native';

import TemperatureSummaryActions from './temperature-summary.reducer';
import RoundedButton from '../../../shared/components/rounded-button/rounded-button';
import TemperatureSummaryDeleteModal from './temperature-summary-delete-modal';
import styles from './temperature-summary-styles';

function TemperatureSummaryDetailScreen(props) {
  const { route, getTemperatureSummary, navigation, temperatureSummary, fetching, error } = props;
  const [deleteModalVisible, setDeleteModalVisible] = React.useState(false);
  // prevents display of stale reducer data
  const entityId = temperatureSummary?.id ?? null;
  const routeEntityId = route.params?.entityId ?? null;
  const correctEntityLoaded = routeEntityId && entityId && routeEntityId.toString() === entityId.toString();

  useFocusEffect(
    React.useCallback(() => {
      if (!routeEntityId) {
        navigation.navigate('TemperatureSummary');
      } else {
        setDeleteModalVisible(false);
        getTemperatureSummary(routeEntityId);
      }
    }, [routeEntityId, getTemperatureSummary, navigation]),
  );

  if (!entityId && !fetching && error) {
    return (
      <View style={styles.loading}>
        <Text>Something went wrong fetching the TemperatureSummary.</Text>
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
    <ScrollView style={styles.container} contentContainerStyle={styles.paddedScrollView} testID="temperatureSummaryDetailScrollView">
      <Text style={styles.label}>Id:</Text>
      <Text>{temperatureSummary.id}</Text>
      {/* UsuarioId Field */}
      <Text style={styles.label}>UsuarioId:</Text>
      <Text testID="usuarioId">{temperatureSummary.usuarioId}</Text>
      {/* EmpresaId Field */}
      <Text style={styles.label}>EmpresaId:</Text>
      <Text testID="empresaId">{temperatureSummary.empresaId}</Text>
      {/* FieldAverage Field */}
      <Text style={styles.label}>FieldAverage:</Text>
      <Text testID="fieldAverage">{temperatureSummary.fieldAverage}</Text>
      {/* FieldMax Field */}
      <Text style={styles.label}>FieldMax:</Text>
      <Text testID="fieldMax">{temperatureSummary.fieldMax}</Text>
      {/* FieldMin Field */}
      <Text style={styles.label}>FieldMin:</Text>
      <Text testID="fieldMin">{temperatureSummary.fieldMin}</Text>
      {/* MeasurementLocation Field */}
      <Text style={styles.label}>MeasurementLocation:</Text>
      <Text testID="measurementLocation">{temperatureSummary.measurementLocation}</Text>
      {/* StartTime Field */}
      <Text style={styles.label}>StartTime:</Text>
      <Text testID="startTime">{String(temperatureSummary.startTime)}</Text>
      {/* EndTime Field */}
      <Text style={styles.label}>EndTime:</Text>
      <Text testID="endTime">{String(temperatureSummary.endTime)}</Text>

      <View style={styles.entityButtons}>
        <RoundedButton
          text="Edit"
          onPress={() => navigation.navigate('TemperatureSummaryEdit', { entityId })}
          accessibilityLabel={'TemperatureSummary Edit Button'}
          testID="temperatureSummaryEditButton"
        />
        <RoundedButton
          text="Delete"
          onPress={() => setDeleteModalVisible(true)}
          accessibilityLabel={'TemperatureSummary Delete Button'}
          testID="temperatureSummaryDeleteButton"
        />
        {deleteModalVisible && (
          <TemperatureSummaryDeleteModal
            navigation={navigation}
            visible={deleteModalVisible}
            setVisible={setDeleteModalVisible}
            entity={temperatureSummary}
            testID="temperatureSummaryDeleteModal"
          />
        )}
      </View>
    </ScrollView>
  );
}

const mapStateToProps = (state) => {
  return {
    temperatureSummary: state.temperatureSummaries.temperatureSummary,
    error: state.temperatureSummaries.errorOne,
    fetching: state.temperatureSummaries.fetchingOne,
    deleting: state.temperatureSummaries.deleting,
    errorDeleting: state.temperatureSummaries.errorDeleting,
  };
};

const mapDispatchToProps = (dispatch) => {
  return {
    getTemperatureSummary: (id) => dispatch(TemperatureSummaryActions.temperatureSummaryRequest(id)),
    getAllTemperatureSummaries: (options) => dispatch(TemperatureSummaryActions.temperatureSummaryAllRequest(options)),
    deleteTemperatureSummary: (id) => dispatch(TemperatureSummaryActions.temperatureSummaryDeleteRequest(id)),
    resetTemperatureSummaries: () => dispatch(TemperatureSummaryActions.temperatureSummaryReset()),
  };
};

export default connect(mapStateToProps, mapDispatchToProps)(TemperatureSummaryDetailScreen);
