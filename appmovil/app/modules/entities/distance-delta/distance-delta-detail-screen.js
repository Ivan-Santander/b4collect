import React from 'react';
import { ActivityIndicator, ScrollView, Text, View } from 'react-native';
import { connect } from 'react-redux';
import { useFocusEffect } from '@react-navigation/native';

import DistanceDeltaActions from './distance-delta.reducer';
import RoundedButton from '../../../shared/components/rounded-button/rounded-button';
import DistanceDeltaDeleteModal from './distance-delta-delete-modal';
import styles from './distance-delta-styles';

function DistanceDeltaDetailScreen(props) {
  const { route, getDistanceDelta, navigation, distanceDelta, fetching, error } = props;
  const [deleteModalVisible, setDeleteModalVisible] = React.useState(false);
  // prevents display of stale reducer data
  const entityId = distanceDelta?.id ?? null;
  const routeEntityId = route.params?.entityId ?? null;
  const correctEntityLoaded = routeEntityId && entityId && routeEntityId.toString() === entityId.toString();

  useFocusEffect(
    React.useCallback(() => {
      if (!routeEntityId) {
        navigation.navigate('DistanceDelta');
      } else {
        setDeleteModalVisible(false);
        getDistanceDelta(routeEntityId);
      }
    }, [routeEntityId, getDistanceDelta, navigation]),
  );

  if (!entityId && !fetching && error) {
    return (
      <View style={styles.loading}>
        <Text>Something went wrong fetching the DistanceDelta.</Text>
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
    <ScrollView style={styles.container} contentContainerStyle={styles.paddedScrollView} testID="distanceDeltaDetailScrollView">
      <Text style={styles.label}>Id:</Text>
      <Text>{distanceDelta.id}</Text>
      {/* UsuarioId Field */}
      <Text style={styles.label}>UsuarioId:</Text>
      <Text testID="usuarioId">{distanceDelta.usuarioId}</Text>
      {/* EmpresaId Field */}
      <Text style={styles.label}>EmpresaId:</Text>
      <Text testID="empresaId">{distanceDelta.empresaId}</Text>
      {/* Distance Field */}
      <Text style={styles.label}>Distance:</Text>
      <Text testID="distance">{distanceDelta.distance}</Text>
      {/* StartTime Field */}
      <Text style={styles.label}>StartTime:</Text>
      <Text testID="startTime">{String(distanceDelta.startTime)}</Text>
      {/* EndTime Field */}
      <Text style={styles.label}>EndTime:</Text>
      <Text testID="endTime">{String(distanceDelta.endTime)}</Text>

      <View style={styles.entityButtons}>
        <RoundedButton
          text="Edit"
          onPress={() => navigation.navigate('DistanceDeltaEdit', { entityId })}
          accessibilityLabel={'DistanceDelta Edit Button'}
          testID="distanceDeltaEditButton"
        />
        <RoundedButton
          text="Delete"
          onPress={() => setDeleteModalVisible(true)}
          accessibilityLabel={'DistanceDelta Delete Button'}
          testID="distanceDeltaDeleteButton"
        />
        {deleteModalVisible && (
          <DistanceDeltaDeleteModal
            navigation={navigation}
            visible={deleteModalVisible}
            setVisible={setDeleteModalVisible}
            entity={distanceDelta}
            testID="distanceDeltaDeleteModal"
          />
        )}
      </View>
    </ScrollView>
  );
}

const mapStateToProps = (state) => {
  return {
    distanceDelta: state.distanceDeltas.distanceDelta,
    error: state.distanceDeltas.errorOne,
    fetching: state.distanceDeltas.fetchingOne,
    deleting: state.distanceDeltas.deleting,
    errorDeleting: state.distanceDeltas.errorDeleting,
  };
};

const mapDispatchToProps = (dispatch) => {
  return {
    getDistanceDelta: (id) => dispatch(DistanceDeltaActions.distanceDeltaRequest(id)),
    getAllDistanceDeltas: (options) => dispatch(DistanceDeltaActions.distanceDeltaAllRequest(options)),
    deleteDistanceDelta: (id) => dispatch(DistanceDeltaActions.distanceDeltaDeleteRequest(id)),
    resetDistanceDeltas: () => dispatch(DistanceDeltaActions.distanceDeltaReset()),
  };
};

export default connect(mapStateToProps, mapDispatchToProps)(DistanceDeltaDetailScreen);
