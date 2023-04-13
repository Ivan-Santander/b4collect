import React from 'react';
import { ActivityIndicator, ScrollView, Text, View } from 'react-native';
import { connect } from 'react-redux';
import { useFocusEffect } from '@react-navigation/native';

import CiclingPedalingCadenceActions from './cicling-pedaling-cadence.reducer';
import RoundedButton from '../../../shared/components/rounded-button/rounded-button';
import CiclingPedalingCadenceDeleteModal from './cicling-pedaling-cadence-delete-modal';
import styles from './cicling-pedaling-cadence-styles';

function CiclingPedalingCadenceDetailScreen(props) {
  const { route, getCiclingPedalingCadence, navigation, ciclingPedalingCadence, fetching, error } = props;
  const [deleteModalVisible, setDeleteModalVisible] = React.useState(false);
  // prevents display of stale reducer data
  const entityId = ciclingPedalingCadence?.id ?? null;
  const routeEntityId = route.params?.entityId ?? null;
  const correctEntityLoaded = routeEntityId && entityId && routeEntityId.toString() === entityId.toString();

  useFocusEffect(
    React.useCallback(() => {
      if (!routeEntityId) {
        navigation.navigate('CiclingPedalingCadence');
      } else {
        setDeleteModalVisible(false);
        getCiclingPedalingCadence(routeEntityId);
      }
    }, [routeEntityId, getCiclingPedalingCadence, navigation]),
  );

  if (!entityId && !fetching && error) {
    return (
      <View style={styles.loading}>
        <Text>Something went wrong fetching the CiclingPedalingCadence.</Text>
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
    <ScrollView style={styles.container} contentContainerStyle={styles.paddedScrollView} testID="ciclingPedalingCadenceDetailScrollView">
      <Text style={styles.label}>Id:</Text>
      <Text>{ciclingPedalingCadence.id}</Text>
      {/* UsuarioId Field */}
      <Text style={styles.label}>UsuarioId:</Text>
      <Text testID="usuarioId">{ciclingPedalingCadence.usuarioId}</Text>
      {/* EmpresaId Field */}
      <Text style={styles.label}>EmpresaId:</Text>
      <Text testID="empresaId">{ciclingPedalingCadence.empresaId}</Text>
      {/* Rpm Field */}
      <Text style={styles.label}>Rpm:</Text>
      <Text testID="rpm">{ciclingPedalingCadence.rpm}</Text>
      {/* StartTime Field */}
      <Text style={styles.label}>StartTime:</Text>
      <Text testID="startTime">{String(ciclingPedalingCadence.startTime)}</Text>
      {/* EndTime Field */}
      <Text style={styles.label}>EndTime:</Text>
      <Text testID="endTime">{String(ciclingPedalingCadence.endTime)}</Text>

      <View style={styles.entityButtons}>
        <RoundedButton
          text="Edit"
          onPress={() => navigation.navigate('CiclingPedalingCadenceEdit', { entityId })}
          accessibilityLabel={'CiclingPedalingCadence Edit Button'}
          testID="ciclingPedalingCadenceEditButton"
        />
        <RoundedButton
          text="Delete"
          onPress={() => setDeleteModalVisible(true)}
          accessibilityLabel={'CiclingPedalingCadence Delete Button'}
          testID="ciclingPedalingCadenceDeleteButton"
        />
        {deleteModalVisible && (
          <CiclingPedalingCadenceDeleteModal
            navigation={navigation}
            visible={deleteModalVisible}
            setVisible={setDeleteModalVisible}
            entity={ciclingPedalingCadence}
            testID="ciclingPedalingCadenceDeleteModal"
          />
        )}
      </View>
    </ScrollView>
  );
}

const mapStateToProps = (state) => {
  return {
    ciclingPedalingCadence: state.ciclingPedalingCadences.ciclingPedalingCadence,
    error: state.ciclingPedalingCadences.errorOne,
    fetching: state.ciclingPedalingCadences.fetchingOne,
    deleting: state.ciclingPedalingCadences.deleting,
    errorDeleting: state.ciclingPedalingCadences.errorDeleting,
  };
};

const mapDispatchToProps = (dispatch) => {
  return {
    getCiclingPedalingCadence: (id) => dispatch(CiclingPedalingCadenceActions.ciclingPedalingCadenceRequest(id)),
    getAllCiclingPedalingCadences: (options) => dispatch(CiclingPedalingCadenceActions.ciclingPedalingCadenceAllRequest(options)),
    deleteCiclingPedalingCadence: (id) => dispatch(CiclingPedalingCadenceActions.ciclingPedalingCadenceDeleteRequest(id)),
    resetCiclingPedalingCadences: () => dispatch(CiclingPedalingCadenceActions.ciclingPedalingCadenceReset()),
  };
};

export default connect(mapStateToProps, mapDispatchToProps)(CiclingPedalingCadenceDetailScreen);
