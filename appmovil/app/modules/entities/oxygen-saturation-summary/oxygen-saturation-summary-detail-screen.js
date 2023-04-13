import React from 'react';
import { ActivityIndicator, ScrollView, Text, View } from 'react-native';
import { connect } from 'react-redux';
import { useFocusEffect } from '@react-navigation/native';

import OxygenSaturationSummaryActions from './oxygen-saturation-summary.reducer';
import RoundedButton from '../../../shared/components/rounded-button/rounded-button';
import OxygenSaturationSummaryDeleteModal from './oxygen-saturation-summary-delete-modal';
import styles from './oxygen-saturation-summary-styles';

function OxygenSaturationSummaryDetailScreen(props) {
  const { route, getOxygenSaturationSummary, navigation, oxygenSaturationSummary, fetching, error } = props;
  const [deleteModalVisible, setDeleteModalVisible] = React.useState(false);
  // prevents display of stale reducer data
  const entityId = oxygenSaturationSummary?.id ?? null;
  const routeEntityId = route.params?.entityId ?? null;
  const correctEntityLoaded = routeEntityId && entityId && routeEntityId.toString() === entityId.toString();

  useFocusEffect(
    React.useCallback(() => {
      if (!routeEntityId) {
        navigation.navigate('OxygenSaturationSummary');
      } else {
        setDeleteModalVisible(false);
        getOxygenSaturationSummary(routeEntityId);
      }
    }, [routeEntityId, getOxygenSaturationSummary, navigation]),
  );

  if (!entityId && !fetching && error) {
    return (
      <View style={styles.loading}>
        <Text>Something went wrong fetching the OxygenSaturationSummary.</Text>
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
    <ScrollView style={styles.container} contentContainerStyle={styles.paddedScrollView} testID="oxygenSaturationSummaryDetailScrollView">
      <Text style={styles.label}>Id:</Text>
      <Text>{oxygenSaturationSummary.id}</Text>
      {/* UsuarioId Field */}
      <Text style={styles.label}>UsuarioId:</Text>
      <Text testID="usuarioId">{oxygenSaturationSummary.usuarioId}</Text>
      {/* EmpresaId Field */}
      <Text style={styles.label}>EmpresaId:</Text>
      <Text testID="empresaId">{oxygenSaturationSummary.empresaId}</Text>
      {/* FieldOxigenSaturationAverage Field */}
      <Text style={styles.label}>FieldOxigenSaturationAverage:</Text>
      <Text testID="fieldOxigenSaturationAverage">{oxygenSaturationSummary.fieldOxigenSaturationAverage}</Text>
      {/* FieldOxigenSaturationMax Field */}
      <Text style={styles.label}>FieldOxigenSaturationMax:</Text>
      <Text testID="fieldOxigenSaturationMax">{oxygenSaturationSummary.fieldOxigenSaturationMax}</Text>
      {/* FieldOxigenSaturationMin Field */}
      <Text style={styles.label}>FieldOxigenSaturationMin:</Text>
      <Text testID="fieldOxigenSaturationMin">{oxygenSaturationSummary.fieldOxigenSaturationMin}</Text>
      {/* FieldSuplementalOxigenFlowRateAverage Field */}
      <Text style={styles.label}>FieldSuplementalOxigenFlowRateAverage:</Text>
      <Text testID="fieldSuplementalOxigenFlowRateAverage">{oxygenSaturationSummary.fieldSuplementalOxigenFlowRateAverage}</Text>
      {/* FieldSuplementalOxigenFlowRateMax Field */}
      <Text style={styles.label}>FieldSuplementalOxigenFlowRateMax:</Text>
      <Text testID="fieldSuplementalOxigenFlowRateMax">{oxygenSaturationSummary.fieldSuplementalOxigenFlowRateMax}</Text>
      {/* FieldSuplementalOxigenFlowRateMin Field */}
      <Text style={styles.label}>FieldSuplementalOxigenFlowRateMin:</Text>
      <Text testID="fieldSuplementalOxigenFlowRateMin">{oxygenSaturationSummary.fieldSuplementalOxigenFlowRateMin}</Text>
      {/* FieldOxigenTherapyAdministrationMode Field */}
      <Text style={styles.label}>FieldOxigenTherapyAdministrationMode:</Text>
      <Text testID="fieldOxigenTherapyAdministrationMode">{oxygenSaturationSummary.fieldOxigenTherapyAdministrationMode}</Text>
      {/* FieldOxigenSaturationMode Field */}
      <Text style={styles.label}>FieldOxigenSaturationMode:</Text>
      <Text testID="fieldOxigenSaturationMode">{oxygenSaturationSummary.fieldOxigenSaturationMode}</Text>
      {/* FieldOxigenSaturationMeasurementMethod Field */}
      <Text style={styles.label}>FieldOxigenSaturationMeasurementMethod:</Text>
      <Text testID="fieldOxigenSaturationMeasurementMethod">{oxygenSaturationSummary.fieldOxigenSaturationMeasurementMethod}</Text>
      {/* EndTime Field */}
      <Text style={styles.label}>EndTime:</Text>
      <Text testID="endTime">{String(oxygenSaturationSummary.endTime)}</Text>

      <View style={styles.entityButtons}>
        <RoundedButton
          text="Edit"
          onPress={() => navigation.navigate('OxygenSaturationSummaryEdit', { entityId })}
          accessibilityLabel={'OxygenSaturationSummary Edit Button'}
          testID="oxygenSaturationSummaryEditButton"
        />
        <RoundedButton
          text="Delete"
          onPress={() => setDeleteModalVisible(true)}
          accessibilityLabel={'OxygenSaturationSummary Delete Button'}
          testID="oxygenSaturationSummaryDeleteButton"
        />
        {deleteModalVisible && (
          <OxygenSaturationSummaryDeleteModal
            navigation={navigation}
            visible={deleteModalVisible}
            setVisible={setDeleteModalVisible}
            entity={oxygenSaturationSummary}
            testID="oxygenSaturationSummaryDeleteModal"
          />
        )}
      </View>
    </ScrollView>
  );
}

const mapStateToProps = (state) => {
  return {
    oxygenSaturationSummary: state.oxygenSaturationSummaries.oxygenSaturationSummary,
    error: state.oxygenSaturationSummaries.errorOne,
    fetching: state.oxygenSaturationSummaries.fetchingOne,
    deleting: state.oxygenSaturationSummaries.deleting,
    errorDeleting: state.oxygenSaturationSummaries.errorDeleting,
  };
};

const mapDispatchToProps = (dispatch) => {
  return {
    getOxygenSaturationSummary: (id) => dispatch(OxygenSaturationSummaryActions.oxygenSaturationSummaryRequest(id)),
    getAllOxygenSaturationSummaries: (options) => dispatch(OxygenSaturationSummaryActions.oxygenSaturationSummaryAllRequest(options)),
    deleteOxygenSaturationSummary: (id) => dispatch(OxygenSaturationSummaryActions.oxygenSaturationSummaryDeleteRequest(id)),
    resetOxygenSaturationSummaries: () => dispatch(OxygenSaturationSummaryActions.oxygenSaturationSummaryReset()),
  };
};

export default connect(mapStateToProps, mapDispatchToProps)(OxygenSaturationSummaryDetailScreen);
