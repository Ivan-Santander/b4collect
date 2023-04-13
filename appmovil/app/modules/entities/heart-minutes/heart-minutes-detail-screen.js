import React from 'react';
import { ActivityIndicator, ScrollView, Text, View } from 'react-native';
import { connect } from 'react-redux';
import { useFocusEffect } from '@react-navigation/native';

import HeartMinutesActions from './heart-minutes.reducer';
import RoundedButton from '../../../shared/components/rounded-button/rounded-button';
import HeartMinutesDeleteModal from './heart-minutes-delete-modal';
import styles from './heart-minutes-styles';

function HeartMinutesDetailScreen(props) {
  const { route, getHeartMinutes, navigation, heartMinutes, fetching, error } = props;
  const [deleteModalVisible, setDeleteModalVisible] = React.useState(false);
  // prevents display of stale reducer data
  const entityId = heartMinutes?.id ?? null;
  const routeEntityId = route.params?.entityId ?? null;
  const correctEntityLoaded = routeEntityId && entityId && routeEntityId.toString() === entityId.toString();

  useFocusEffect(
    React.useCallback(() => {
      if (!routeEntityId) {
        navigation.navigate('HeartMinutes');
      } else {
        setDeleteModalVisible(false);
        getHeartMinutes(routeEntityId);
      }
    }, [routeEntityId, getHeartMinutes, navigation]),
  );

  if (!entityId && !fetching && error) {
    return (
      <View style={styles.loading}>
        <Text>Something went wrong fetching the HeartMinutes.</Text>
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
    <ScrollView style={styles.container} contentContainerStyle={styles.paddedScrollView} testID="heartMinutesDetailScrollView">
      <Text style={styles.label}>Id:</Text>
      <Text>{heartMinutes.id}</Text>
      {/* UsuarioId Field */}
      <Text style={styles.label}>UsuarioId:</Text>
      <Text testID="usuarioId">{heartMinutes.usuarioId}</Text>
      {/* EmpresaId Field */}
      <Text style={styles.label}>EmpresaId:</Text>
      <Text testID="empresaId">{heartMinutes.empresaId}</Text>
      {/* Severity Field */}
      <Text style={styles.label}>Severity:</Text>
      <Text testID="severity">{heartMinutes.severity}</Text>
      {/* StartTime Field */}
      <Text style={styles.label}>StartTime:</Text>
      <Text testID="startTime">{String(heartMinutes.startTime)}</Text>
      {/* EndTime Field */}
      <Text style={styles.label}>EndTime:</Text>
      <Text testID="endTime">{String(heartMinutes.endTime)}</Text>

      <View style={styles.entityButtons}>
        <RoundedButton
          text="Edit"
          onPress={() => navigation.navigate('HeartMinutesEdit', { entityId })}
          accessibilityLabel={'HeartMinutes Edit Button'}
          testID="heartMinutesEditButton"
        />
        <RoundedButton
          text="Delete"
          onPress={() => setDeleteModalVisible(true)}
          accessibilityLabel={'HeartMinutes Delete Button'}
          testID="heartMinutesDeleteButton"
        />
        {deleteModalVisible && (
          <HeartMinutesDeleteModal
            navigation={navigation}
            visible={deleteModalVisible}
            setVisible={setDeleteModalVisible}
            entity={heartMinutes}
            testID="heartMinutesDeleteModal"
          />
        )}
      </View>
    </ScrollView>
  );
}

const mapStateToProps = (state) => {
  return {
    heartMinutes: state.heartMinutes.heartMinutes,
    error: state.heartMinutes.errorOne,
    fetching: state.heartMinutes.fetchingOne,
    deleting: state.heartMinutes.deleting,
    errorDeleting: state.heartMinutes.errorDeleting,
  };
};

const mapDispatchToProps = (dispatch) => {
  return {
    getHeartMinutes: (id) => dispatch(HeartMinutesActions.heartMinutesRequest(id)),
    getAllHeartMinutes: (options) => dispatch(HeartMinutesActions.heartMinutesAllRequest(options)),
    deleteHeartMinutes: (id) => dispatch(HeartMinutesActions.heartMinutesDeleteRequest(id)),
    resetHeartMinutes: () => dispatch(HeartMinutesActions.heartMinutesReset()),
  };
};

export default connect(mapStateToProps, mapDispatchToProps)(HeartMinutesDetailScreen);
