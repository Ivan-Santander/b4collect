import React from 'react';
import { ActivityIndicator, ScrollView, Text, View } from 'react-native';
import { connect } from 'react-redux';
import { useFocusEffect } from '@react-navigation/native';

import SpeedActions from './speed.reducer';
import RoundedButton from '../../../shared/components/rounded-button/rounded-button';
import SpeedDeleteModal from './speed-delete-modal';
import styles from './speed-styles';

function SpeedDetailScreen(props) {
  const { route, getSpeed, navigation, speed, fetching, error } = props;
  const [deleteModalVisible, setDeleteModalVisible] = React.useState(false);
  // prevents display of stale reducer data
  const entityId = speed?.id ?? null;
  const routeEntityId = route.params?.entityId ?? null;
  const correctEntityLoaded = routeEntityId && entityId && routeEntityId.toString() === entityId.toString();

  useFocusEffect(
    React.useCallback(() => {
      if (!routeEntityId) {
        navigation.navigate('Speed');
      } else {
        setDeleteModalVisible(false);
        getSpeed(routeEntityId);
      }
    }, [routeEntityId, getSpeed, navigation]),
  );

  if (!entityId && !fetching && error) {
    return (
      <View style={styles.loading}>
        <Text>Something went wrong fetching the Speed.</Text>
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
    <ScrollView style={styles.container} contentContainerStyle={styles.paddedScrollView} testID="speedDetailScrollView">
      <Text style={styles.label}>Id:</Text>
      <Text>{speed.id}</Text>
      {/* UsuarioId Field */}
      <Text style={styles.label}>UsuarioId:</Text>
      <Text testID="usuarioId">{speed.usuarioId}</Text>
      {/* EmpresaId Field */}
      <Text style={styles.label}>EmpresaId:</Text>
      <Text testID="empresaId">{speed.empresaId}</Text>
      {/* Speed Field */}
      <Text style={styles.label}>Speed:</Text>
      <Text testID="speed">{speed.speed}</Text>
      {/* EndTime Field */}
      <Text style={styles.label}>EndTime:</Text>
      <Text testID="endTime">{String(speed.endTime)}</Text>

      <View style={styles.entityButtons}>
        <RoundedButton
          text="Edit"
          onPress={() => navigation.navigate('SpeedEdit', { entityId })}
          accessibilityLabel={'Speed Edit Button'}
          testID="speedEditButton"
        />
        <RoundedButton
          text="Delete"
          onPress={() => setDeleteModalVisible(true)}
          accessibilityLabel={'Speed Delete Button'}
          testID="speedDeleteButton"
        />
        {deleteModalVisible && (
          <SpeedDeleteModal
            navigation={navigation}
            visible={deleteModalVisible}
            setVisible={setDeleteModalVisible}
            entity={speed}
            testID="speedDeleteModal"
          />
        )}
      </View>
    </ScrollView>
  );
}

const mapStateToProps = (state) => {
  return {
    speed: state.speeds.speed,
    error: state.speeds.errorOne,
    fetching: state.speeds.fetchingOne,
    deleting: state.speeds.deleting,
    errorDeleting: state.speeds.errorDeleting,
  };
};

const mapDispatchToProps = (dispatch) => {
  return {
    getSpeed: (id) => dispatch(SpeedActions.speedRequest(id)),
    getAllSpeeds: (options) => dispatch(SpeedActions.speedAllRequest(options)),
    deleteSpeed: (id) => dispatch(SpeedActions.speedDeleteRequest(id)),
    resetSpeeds: () => dispatch(SpeedActions.speedReset()),
  };
};

export default connect(mapStateToProps, mapDispatchToProps)(SpeedDetailScreen);
