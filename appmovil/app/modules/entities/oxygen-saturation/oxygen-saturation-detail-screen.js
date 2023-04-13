import React from 'react';
import { ActivityIndicator, ScrollView, Text, View } from 'react-native';
import { connect } from 'react-redux';
import { useFocusEffect } from '@react-navigation/native';

import OxygenSaturationActions from './oxygen-saturation.reducer';
import RoundedButton from '../../../shared/components/rounded-button/rounded-button';
import OxygenSaturationDeleteModal from './oxygen-saturation-delete-modal';
import styles from './oxygen-saturation-styles';

function OxygenSaturationDetailScreen(props) {
  const { route, getOxygenSaturation, navigation, oxygenSaturation, fetching, error } = props;
  const [deleteModalVisible, setDeleteModalVisible] = React.useState(false);
  // prevents display of stale reducer data
  const entityId = oxygenSaturation?.id ?? null;
  const routeEntityId = route.params?.entityId ?? null;
  const correctEntityLoaded = routeEntityId && entityId && routeEntityId.toString() === entityId.toString();

  useFocusEffect(
    React.useCallback(() => {
      if (!routeEntityId) {
        navigation.navigate('OxygenSaturation');
      } else {
        setDeleteModalVisible(false);
        getOxygenSaturation(routeEntityId);
      }
    }, [routeEntityId, getOxygenSaturation, navigation]),
  );

  if (!entityId && !fetching && error) {
    return (
      <View style={styles.loading}>
        <Text>Something went wrong fetching the OxygenSaturation.</Text>
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
    <ScrollView style={styles.container} contentContainerStyle={styles.paddedScrollView} testID="oxygenSaturationDetailScrollView">
      <Text style={styles.label}>Id:</Text>
      <Text>{oxygenSaturation.id}</Text>
      {/* UsuarioId Field */}
      <Text style={styles.label}>UsuarioId:</Text>
      <Text testID="usuarioId">{oxygenSaturation.usuarioId}</Text>
      {/* EmpresaId Field */}
      <Text style={styles.label}>EmpresaId:</Text>
      <Text testID="empresaId">{oxygenSaturation.empresaId}</Text>
      {/* FieldOxigenSaturation Field */}
      <Text style={styles.label}>FieldOxigenSaturation:</Text>
      <Text testID="fieldOxigenSaturation">{oxygenSaturation.fieldOxigenSaturation}</Text>
      {/* FieldSuplementalOxigenFlowRate Field */}
      <Text style={styles.label}>FieldSuplementalOxigenFlowRate:</Text>
      <Text testID="fieldSuplementalOxigenFlowRate">{oxygenSaturation.fieldSuplementalOxigenFlowRate}</Text>
      {/* FieldOxigenTherapyAdministrationMode Field */}
      <Text style={styles.label}>FieldOxigenTherapyAdministrationMode:</Text>
      <Text testID="fieldOxigenTherapyAdministrationMode">{oxygenSaturation.fieldOxigenTherapyAdministrationMode}</Text>
      {/* FieldOxigenSaturationMode Field */}
      <Text style={styles.label}>FieldOxigenSaturationMode:</Text>
      <Text testID="fieldOxigenSaturationMode">{oxygenSaturation.fieldOxigenSaturationMode}</Text>
      {/* FieldOxigenSaturationMeasurementMethod Field */}
      <Text style={styles.label}>FieldOxigenSaturationMeasurementMethod:</Text>
      <Text testID="fieldOxigenSaturationMeasurementMethod">{oxygenSaturation.fieldOxigenSaturationMeasurementMethod}</Text>
      {/* EndTime Field */}
      <Text style={styles.label}>EndTime:</Text>
      <Text testID="endTime">{String(oxygenSaturation.endTime)}</Text>

      <View style={styles.entityButtons}>
        <RoundedButton
          text="Edit"
          onPress={() => navigation.navigate('OxygenSaturationEdit', { entityId })}
          accessibilityLabel={'OxygenSaturation Edit Button'}
          testID="oxygenSaturationEditButton"
        />
        <RoundedButton
          text="Delete"
          onPress={() => setDeleteModalVisible(true)}
          accessibilityLabel={'OxygenSaturation Delete Button'}
          testID="oxygenSaturationDeleteButton"
        />
        {deleteModalVisible && (
          <OxygenSaturationDeleteModal
            navigation={navigation}
            visible={deleteModalVisible}
            setVisible={setDeleteModalVisible}
            entity={oxygenSaturation}
            testID="oxygenSaturationDeleteModal"
          />
        )}
      </View>
    </ScrollView>
  );
}

const mapStateToProps = (state) => {
  return {
    oxygenSaturation: state.oxygenSaturations.oxygenSaturation,
    error: state.oxygenSaturations.errorOne,
    fetching: state.oxygenSaturations.fetchingOne,
    deleting: state.oxygenSaturations.deleting,
    errorDeleting: state.oxygenSaturations.errorDeleting,
  };
};

const mapDispatchToProps = (dispatch) => {
  return {
    getOxygenSaturation: (id) => dispatch(OxygenSaturationActions.oxygenSaturationRequest(id)),
    getAllOxygenSaturations: (options) => dispatch(OxygenSaturationActions.oxygenSaturationAllRequest(options)),
    deleteOxygenSaturation: (id) => dispatch(OxygenSaturationActions.oxygenSaturationDeleteRequest(id)),
    resetOxygenSaturations: () => dispatch(OxygenSaturationActions.oxygenSaturationReset()),
  };
};

export default connect(mapStateToProps, mapDispatchToProps)(OxygenSaturationDetailScreen);
