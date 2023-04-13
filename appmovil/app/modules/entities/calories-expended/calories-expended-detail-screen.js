import React from 'react';
import { ActivityIndicator, ScrollView, Text, View } from 'react-native';
import { connect } from 'react-redux';
import { useFocusEffect } from '@react-navigation/native';

import CaloriesExpendedActions from './calories-expended.reducer';
import RoundedButton from '../../../shared/components/rounded-button/rounded-button';
import CaloriesExpendedDeleteModal from './calories-expended-delete-modal';
import styles from './calories-expended-styles';

function CaloriesExpendedDetailScreen(props) {
  const { route, getCaloriesExpended, navigation, caloriesExpended, fetching, error } = props;
  const [deleteModalVisible, setDeleteModalVisible] = React.useState(false);
  // prevents display of stale reducer data
  const entityId = caloriesExpended?.id ?? null;
  const routeEntityId = route.params?.entityId ?? null;
  const correctEntityLoaded = routeEntityId && entityId && routeEntityId.toString() === entityId.toString();

  useFocusEffect(
    React.useCallback(() => {
      if (!routeEntityId) {
        navigation.navigate('CaloriesExpended');
      } else {
        setDeleteModalVisible(false);
        getCaloriesExpended(routeEntityId);
      }
    }, [routeEntityId, getCaloriesExpended, navigation]),
  );

  if (!entityId && !fetching && error) {
    return (
      <View style={styles.loading}>
        <Text>Something went wrong fetching the CaloriesExpended.</Text>
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
    <ScrollView style={styles.container} contentContainerStyle={styles.paddedScrollView} testID="caloriesExpendedDetailScrollView">
      <Text style={styles.label}>Id:</Text>
      <Text>{caloriesExpended.id}</Text>
      {/* UsuarioId Field */}
      <Text style={styles.label}>UsuarioId:</Text>
      <Text testID="usuarioId">{caloriesExpended.usuarioId}</Text>
      {/* EmpresaId Field */}
      <Text style={styles.label}>EmpresaId:</Text>
      <Text testID="empresaId">{caloriesExpended.empresaId}</Text>
      {/* Calorias Field */}
      <Text style={styles.label}>Calorias:</Text>
      <Text testID="calorias">{caloriesExpended.calorias}</Text>
      {/* StartTime Field */}
      <Text style={styles.label}>StartTime:</Text>
      <Text testID="startTime">{String(caloriesExpended.startTime)}</Text>
      {/* EndTime Field */}
      <Text style={styles.label}>EndTime:</Text>
      <Text testID="endTime">{String(caloriesExpended.endTime)}</Text>

      <View style={styles.entityButtons}>
        <RoundedButton
          text="Edit"
          onPress={() => navigation.navigate('CaloriesExpendedEdit', { entityId })}
          accessibilityLabel={'CaloriesExpended Edit Button'}
          testID="caloriesExpendedEditButton"
        />
        <RoundedButton
          text="Delete"
          onPress={() => setDeleteModalVisible(true)}
          accessibilityLabel={'CaloriesExpended Delete Button'}
          testID="caloriesExpendedDeleteButton"
        />
        {deleteModalVisible && (
          <CaloriesExpendedDeleteModal
            navigation={navigation}
            visible={deleteModalVisible}
            setVisible={setDeleteModalVisible}
            entity={caloriesExpended}
            testID="caloriesExpendedDeleteModal"
          />
        )}
      </View>
    </ScrollView>
  );
}

const mapStateToProps = (state) => {
  return {
    caloriesExpended: state.caloriesExpendeds.caloriesExpended,
    error: state.caloriesExpendeds.errorOne,
    fetching: state.caloriesExpendeds.fetchingOne,
    deleting: state.caloriesExpendeds.deleting,
    errorDeleting: state.caloriesExpendeds.errorDeleting,
  };
};

const mapDispatchToProps = (dispatch) => {
  return {
    getCaloriesExpended: (id) => dispatch(CaloriesExpendedActions.caloriesExpendedRequest(id)),
    getAllCaloriesExpendeds: (options) => dispatch(CaloriesExpendedActions.caloriesExpendedAllRequest(options)),
    deleteCaloriesExpended: (id) => dispatch(CaloriesExpendedActions.caloriesExpendedDeleteRequest(id)),
    resetCaloriesExpendeds: () => dispatch(CaloriesExpendedActions.caloriesExpendedReset()),
  };
};

export default connect(mapStateToProps, mapDispatchToProps)(CaloriesExpendedDetailScreen);
