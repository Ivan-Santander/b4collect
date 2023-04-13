import React from 'react';
import { ActivityIndicator, ScrollView, Text, View } from 'react-native';
import { connect } from 'react-redux';
import { useFocusEffect } from '@react-navigation/native';

import HeartRateBpmActions from './heart-rate-bpm.reducer';
import RoundedButton from '../../../shared/components/rounded-button/rounded-button';
import HeartRateBpmDeleteModal from './heart-rate-bpm-delete-modal';
import styles from './heart-rate-bpm-styles';

function HeartRateBpmDetailScreen(props) {
  const { route, getHeartRateBpm, navigation, heartRateBpm, fetching, error } = props;
  const [deleteModalVisible, setDeleteModalVisible] = React.useState(false);
  // prevents display of stale reducer data
  const entityId = heartRateBpm?.id ?? null;
  const routeEntityId = route.params?.entityId ?? null;
  const correctEntityLoaded = routeEntityId && entityId && routeEntityId.toString() === entityId.toString();

  useFocusEffect(
    React.useCallback(() => {
      if (!routeEntityId) {
        navigation.navigate('HeartRateBpm');
      } else {
        setDeleteModalVisible(false);
        getHeartRateBpm(routeEntityId);
      }
    }, [routeEntityId, getHeartRateBpm, navigation]),
  );

  if (!entityId && !fetching && error) {
    return (
      <View style={styles.loading}>
        <Text>Something went wrong fetching the HeartRateBpm.</Text>
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
    <ScrollView style={styles.container} contentContainerStyle={styles.paddedScrollView} testID="heartRateBpmDetailScrollView">
      <Text style={styles.label}>Id:</Text>
      <Text>{heartRateBpm.id}</Text>
      {/* UsuarioId Field */}
      <Text style={styles.label}>UsuarioId:</Text>
      <Text testID="usuarioId">{heartRateBpm.usuarioId}</Text>
      {/* EmpresaId Field */}
      <Text style={styles.label}>EmpresaId:</Text>
      <Text testID="empresaId">{heartRateBpm.empresaId}</Text>
      {/* Ppm Field */}
      <Text style={styles.label}>Ppm:</Text>
      <Text testID="ppm">{heartRateBpm.ppm}</Text>
      {/* EndTime Field */}
      <Text style={styles.label}>EndTime:</Text>
      <Text testID="endTime">{String(heartRateBpm.endTime)}</Text>

      <View style={styles.entityButtons}>
        <RoundedButton
          text="Edit"
          onPress={() => navigation.navigate('HeartRateBpmEdit', { entityId })}
          accessibilityLabel={'HeartRateBpm Edit Button'}
          testID="heartRateBpmEditButton"
        />
        <RoundedButton
          text="Delete"
          onPress={() => setDeleteModalVisible(true)}
          accessibilityLabel={'HeartRateBpm Delete Button'}
          testID="heartRateBpmDeleteButton"
        />
        {deleteModalVisible && (
          <HeartRateBpmDeleteModal
            navigation={navigation}
            visible={deleteModalVisible}
            setVisible={setDeleteModalVisible}
            entity={heartRateBpm}
            testID="heartRateBpmDeleteModal"
          />
        )}
      </View>
    </ScrollView>
  );
}

const mapStateToProps = (state) => {
  return {
    heartRateBpm: state.heartRateBpms.heartRateBpm,
    error: state.heartRateBpms.errorOne,
    fetching: state.heartRateBpms.fetchingOne,
    deleting: state.heartRateBpms.deleting,
    errorDeleting: state.heartRateBpms.errorDeleting,
  };
};

const mapDispatchToProps = (dispatch) => {
  return {
    getHeartRateBpm: (id) => dispatch(HeartRateBpmActions.heartRateBpmRequest(id)),
    getAllHeartRateBpms: (options) => dispatch(HeartRateBpmActions.heartRateBpmAllRequest(options)),
    deleteHeartRateBpm: (id) => dispatch(HeartRateBpmActions.heartRateBpmDeleteRequest(id)),
    resetHeartRateBpms: () => dispatch(HeartRateBpmActions.heartRateBpmReset()),
  };
};

export default connect(mapStateToProps, mapDispatchToProps)(HeartRateBpmDetailScreen);
