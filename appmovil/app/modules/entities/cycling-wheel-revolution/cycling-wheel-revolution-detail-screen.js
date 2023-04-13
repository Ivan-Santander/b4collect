import React from 'react';
import { ActivityIndicator, ScrollView, Text, View } from 'react-native';
import { connect } from 'react-redux';
import { useFocusEffect } from '@react-navigation/native';

import CyclingWheelRevolutionActions from './cycling-wheel-revolution.reducer';
import RoundedButton from '../../../shared/components/rounded-button/rounded-button';
import CyclingWheelRevolutionDeleteModal from './cycling-wheel-revolution-delete-modal';
import styles from './cycling-wheel-revolution-styles';

function CyclingWheelRevolutionDetailScreen(props) {
  const { route, getCyclingWheelRevolution, navigation, cyclingWheelRevolution, fetching, error } = props;
  const [deleteModalVisible, setDeleteModalVisible] = React.useState(false);
  // prevents display of stale reducer data
  const entityId = cyclingWheelRevolution?.id ?? null;
  const routeEntityId = route.params?.entityId ?? null;
  const correctEntityLoaded = routeEntityId && entityId && routeEntityId.toString() === entityId.toString();

  useFocusEffect(
    React.useCallback(() => {
      if (!routeEntityId) {
        navigation.navigate('CyclingWheelRevolution');
      } else {
        setDeleteModalVisible(false);
        getCyclingWheelRevolution(routeEntityId);
      }
    }, [routeEntityId, getCyclingWheelRevolution, navigation]),
  );

  if (!entityId && !fetching && error) {
    return (
      <View style={styles.loading}>
        <Text>Something went wrong fetching the CyclingWheelRevolution.</Text>
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
    <ScrollView style={styles.container} contentContainerStyle={styles.paddedScrollView} testID="cyclingWheelRevolutionDetailScrollView">
      <Text style={styles.label}>Id:</Text>
      <Text>{cyclingWheelRevolution.id}</Text>
      {/* UsuarioId Field */}
      <Text style={styles.label}>UsuarioId:</Text>
      <Text testID="usuarioId">{cyclingWheelRevolution.usuarioId}</Text>
      {/* EmpresaId Field */}
      <Text style={styles.label}>EmpresaId:</Text>
      <Text testID="empresaId">{cyclingWheelRevolution.empresaId}</Text>
      {/* Revolutions Field */}
      <Text style={styles.label}>Revolutions:</Text>
      <Text testID="revolutions">{cyclingWheelRevolution.revolutions}</Text>
      {/* StartTime Field */}
      <Text style={styles.label}>StartTime:</Text>
      <Text testID="startTime">{String(cyclingWheelRevolution.startTime)}</Text>
      {/* EndTime Field */}
      <Text style={styles.label}>EndTime:</Text>
      <Text testID="endTime">{String(cyclingWheelRevolution.endTime)}</Text>

      <View style={styles.entityButtons}>
        <RoundedButton
          text="Edit"
          onPress={() => navigation.navigate('CyclingWheelRevolutionEdit', { entityId })}
          accessibilityLabel={'CyclingWheelRevolution Edit Button'}
          testID="cyclingWheelRevolutionEditButton"
        />
        <RoundedButton
          text="Delete"
          onPress={() => setDeleteModalVisible(true)}
          accessibilityLabel={'CyclingWheelRevolution Delete Button'}
          testID="cyclingWheelRevolutionDeleteButton"
        />
        {deleteModalVisible && (
          <CyclingWheelRevolutionDeleteModal
            navigation={navigation}
            visible={deleteModalVisible}
            setVisible={setDeleteModalVisible}
            entity={cyclingWheelRevolution}
            testID="cyclingWheelRevolutionDeleteModal"
          />
        )}
      </View>
    </ScrollView>
  );
}

const mapStateToProps = (state) => {
  return {
    cyclingWheelRevolution: state.cyclingWheelRevolutions.cyclingWheelRevolution,
    error: state.cyclingWheelRevolutions.errorOne,
    fetching: state.cyclingWheelRevolutions.fetchingOne,
    deleting: state.cyclingWheelRevolutions.deleting,
    errorDeleting: state.cyclingWheelRevolutions.errorDeleting,
  };
};

const mapDispatchToProps = (dispatch) => {
  return {
    getCyclingWheelRevolution: (id) => dispatch(CyclingWheelRevolutionActions.cyclingWheelRevolutionRequest(id)),
    getAllCyclingWheelRevolutions: (options) => dispatch(CyclingWheelRevolutionActions.cyclingWheelRevolutionAllRequest(options)),
    deleteCyclingWheelRevolution: (id) => dispatch(CyclingWheelRevolutionActions.cyclingWheelRevolutionDeleteRequest(id)),
    resetCyclingWheelRevolutions: () => dispatch(CyclingWheelRevolutionActions.cyclingWheelRevolutionReset()),
  };
};

export default connect(mapStateToProps, mapDispatchToProps)(CyclingWheelRevolutionDetailScreen);
