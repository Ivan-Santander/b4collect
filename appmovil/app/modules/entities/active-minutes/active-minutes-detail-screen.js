import React from 'react';
import { ActivityIndicator, ScrollView, Text, View } from 'react-native';
import { connect } from 'react-redux';
import { useFocusEffect } from '@react-navigation/native';

import ActiveMinutesActions from './active-minutes.reducer';
import RoundedButton from '../../../shared/components/rounded-button/rounded-button';
import ActiveMinutesDeleteModal from './active-minutes-delete-modal';
import styles from './active-minutes-styles';

function ActiveMinutesDetailScreen(props) {
  const { route, getActiveMinutes, navigation, activeMinutes, fetching, error } = props;
  const [deleteModalVisible, setDeleteModalVisible] = React.useState(false);
  // prevents display of stale reducer data
  const entityId = activeMinutes?.id ?? null;
  const routeEntityId = route.params?.entityId ?? null;
  const correctEntityLoaded = routeEntityId && entityId && routeEntityId.toString() === entityId.toString();

  useFocusEffect(
    React.useCallback(() => {
      if (!routeEntityId) {
        navigation.navigate('ActiveMinutes');
      } else {
        setDeleteModalVisible(false);
        getActiveMinutes(routeEntityId);
      }
    }, [routeEntityId, getActiveMinutes, navigation]),
  );

  if (!entityId && !fetching && error) {
    return (
      <View style={styles.loading}>
        <Text>Something went wrong fetching the ActiveMinutes.</Text>
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
    <ScrollView style={styles.container} contentContainerStyle={styles.paddedScrollView} testID="activeMinutesDetailScrollView">
      <Text style={styles.label}>Id:</Text>
      <Text>{activeMinutes.id}</Text>
      {/* UsuarioId Field */}
      <Text style={styles.label}>UsuarioId:</Text>
      <Text testID="usuarioId">{activeMinutes.usuarioId}</Text>
      {/* EmpresaId Field */}
      <Text style={styles.label}>EmpresaId:</Text>
      <Text testID="empresaId">{activeMinutes.empresaId}</Text>
      {/* Calorias Field */}
      <Text style={styles.label}>Calorias:</Text>
      <Text testID="calorias">{activeMinutes.calorias}</Text>
      {/* StartTime Field */}
      <Text style={styles.label}>StartTime:</Text>
      <Text testID="startTime">{String(activeMinutes.startTime)}</Text>
      {/* EndTime Field */}
      <Text style={styles.label}>EndTime:</Text>
      <Text testID="endTime">{String(activeMinutes.endTime)}</Text>

      <View style={styles.entityButtons}>
        <RoundedButton
          text="Edit"
          onPress={() => navigation.navigate('ActiveMinutesEdit', { entityId })}
          accessibilityLabel={'ActiveMinutes Edit Button'}
          testID="activeMinutesEditButton"
        />
        <RoundedButton
          text="Delete"
          onPress={() => setDeleteModalVisible(true)}
          accessibilityLabel={'ActiveMinutes Delete Button'}
          testID="activeMinutesDeleteButton"
        />
        {deleteModalVisible && (
          <ActiveMinutesDeleteModal
            navigation={navigation}
            visible={deleteModalVisible}
            setVisible={setDeleteModalVisible}
            entity={activeMinutes}
            testID="activeMinutesDeleteModal"
          />
        )}
      </View>
    </ScrollView>
  );
}

const mapStateToProps = (state) => {
  return {
    activeMinutes: state.activeMinutes.activeMinutes,
    error: state.activeMinutes.errorOne,
    fetching: state.activeMinutes.fetchingOne,
    deleting: state.activeMinutes.deleting,
    errorDeleting: state.activeMinutes.errorDeleting,
  };
};

const mapDispatchToProps = (dispatch) => {
  return {
    getActiveMinutes: (id) => dispatch(ActiveMinutesActions.activeMinutesRequest(id)),
    getAllActiveMinutes: (options) => dispatch(ActiveMinutesActions.activeMinutesAllRequest(options)),
    deleteActiveMinutes: (id) => dispatch(ActiveMinutesActions.activeMinutesDeleteRequest(id)),
    resetActiveMinutes: () => dispatch(ActiveMinutesActions.activeMinutesReset()),
  };
};

export default connect(mapStateToProps, mapDispatchToProps)(ActiveMinutesDetailScreen);
