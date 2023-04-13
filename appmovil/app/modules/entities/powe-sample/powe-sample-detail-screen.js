import React from 'react';
import { ActivityIndicator, ScrollView, Text, View } from 'react-native';
import { connect } from 'react-redux';
import { useFocusEffect } from '@react-navigation/native';

import PoweSampleActions from './powe-sample.reducer';
import RoundedButton from '../../../shared/components/rounded-button/rounded-button';
import PoweSampleDeleteModal from './powe-sample-delete-modal';
import styles from './powe-sample-styles';

function PoweSampleDetailScreen(props) {
  const { route, getPoweSample, navigation, poweSample, fetching, error } = props;
  const [deleteModalVisible, setDeleteModalVisible] = React.useState(false);
  // prevents display of stale reducer data
  const entityId = poweSample?.id ?? null;
  const routeEntityId = route.params?.entityId ?? null;
  const correctEntityLoaded = routeEntityId && entityId && routeEntityId.toString() === entityId.toString();

  useFocusEffect(
    React.useCallback(() => {
      if (!routeEntityId) {
        navigation.navigate('PoweSample');
      } else {
        setDeleteModalVisible(false);
        getPoweSample(routeEntityId);
      }
    }, [routeEntityId, getPoweSample, navigation]),
  );

  if (!entityId && !fetching && error) {
    return (
      <View style={styles.loading}>
        <Text>Something went wrong fetching the PoweSample.</Text>
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
    <ScrollView style={styles.container} contentContainerStyle={styles.paddedScrollView} testID="poweSampleDetailScrollView">
      <Text style={styles.label}>Id:</Text>
      <Text>{poweSample.id}</Text>
      {/* UsuarioId Field */}
      <Text style={styles.label}>UsuarioId:</Text>
      <Text testID="usuarioId">{poweSample.usuarioId}</Text>
      {/* EmpresaId Field */}
      <Text style={styles.label}>EmpresaId:</Text>
      <Text testID="empresaId">{poweSample.empresaId}</Text>
      {/* Vatios Field */}
      <Text style={styles.label}>Vatios:</Text>
      <Text testID="vatios">{poweSample.vatios}</Text>
      {/* StartTime Field */}
      <Text style={styles.label}>StartTime:</Text>
      <Text testID="startTime">{String(poweSample.startTime)}</Text>
      {/* EndTime Field */}
      <Text style={styles.label}>EndTime:</Text>
      <Text testID="endTime">{String(poweSample.endTime)}</Text>

      <View style={styles.entityButtons}>
        <RoundedButton
          text="Edit"
          onPress={() => navigation.navigate('PoweSampleEdit', { entityId })}
          accessibilityLabel={'PoweSample Edit Button'}
          testID="poweSampleEditButton"
        />
        <RoundedButton
          text="Delete"
          onPress={() => setDeleteModalVisible(true)}
          accessibilityLabel={'PoweSample Delete Button'}
          testID="poweSampleDeleteButton"
        />
        {deleteModalVisible && (
          <PoweSampleDeleteModal
            navigation={navigation}
            visible={deleteModalVisible}
            setVisible={setDeleteModalVisible}
            entity={poweSample}
            testID="poweSampleDeleteModal"
          />
        )}
      </View>
    </ScrollView>
  );
}

const mapStateToProps = (state) => {
  return {
    poweSample: state.poweSamples.poweSample,
    error: state.poweSamples.errorOne,
    fetching: state.poweSamples.fetchingOne,
    deleting: state.poweSamples.deleting,
    errorDeleting: state.poweSamples.errorDeleting,
  };
};

const mapDispatchToProps = (dispatch) => {
  return {
    getPoweSample: (id) => dispatch(PoweSampleActions.poweSampleRequest(id)),
    getAllPoweSamples: (options) => dispatch(PoweSampleActions.poweSampleAllRequest(options)),
    deletePoweSample: (id) => dispatch(PoweSampleActions.poweSampleDeleteRequest(id)),
    resetPoweSamples: () => dispatch(PoweSampleActions.poweSampleReset()),
  };
};

export default connect(mapStateToProps, mapDispatchToProps)(PoweSampleDetailScreen);
