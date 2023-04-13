import React from 'react';
import { ActivityIndicator, ScrollView, Text, View } from 'react-native';
import { connect } from 'react-redux';
import { useFocusEffect } from '@react-navigation/native';

import CaloriesBMRActions from './calories-bmr.reducer';
import RoundedButton from '../../../shared/components/rounded-button/rounded-button';
import CaloriesBMRDeleteModal from './calories-bmr-delete-modal';
import styles from './calories-bmr-styles';

function CaloriesBMRDetailScreen(props) {
  const { route, getCaloriesBMR, navigation, caloriesBMR, fetching, error } = props;
  const [deleteModalVisible, setDeleteModalVisible] = React.useState(false);
  // prevents display of stale reducer data
  const entityId = caloriesBMR?.id ?? null;
  const routeEntityId = route.params?.entityId ?? null;
  const correctEntityLoaded = routeEntityId && entityId && routeEntityId.toString() === entityId.toString();

  useFocusEffect(
    React.useCallback(() => {
      if (!routeEntityId) {
        navigation.navigate('CaloriesBMR');
      } else {
        setDeleteModalVisible(false);
        getCaloriesBMR(routeEntityId);
      }
    }, [routeEntityId, getCaloriesBMR, navigation]),
  );

  if (!entityId && !fetching && error) {
    return (
      <View style={styles.loading}>
        <Text>Something went wrong fetching the CaloriesBMR.</Text>
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
    <ScrollView style={styles.container} contentContainerStyle={styles.paddedScrollView} testID="caloriesBMRDetailScrollView">
      <Text style={styles.label}>Id:</Text>
      <Text>{caloriesBMR.id}</Text>
      {/* UsuarioId Field */}
      <Text style={styles.label}>UsuarioId:</Text>
      <Text testID="usuarioId">{caloriesBMR.usuarioId}</Text>
      {/* EmpresaId Field */}
      <Text style={styles.label}>EmpresaId:</Text>
      <Text testID="empresaId">{caloriesBMR.empresaId}</Text>
      {/* Calorias Field */}
      <Text style={styles.label}>Calorias:</Text>
      <Text testID="calorias">{caloriesBMR.calorias}</Text>
      {/* StartTime Field */}
      <Text style={styles.label}>StartTime:</Text>
      <Text testID="startTime">{String(caloriesBMR.startTime)}</Text>
      {/* EndTime Field */}
      <Text style={styles.label}>EndTime:</Text>
      <Text testID="endTime">{String(caloriesBMR.endTime)}</Text>

      <View style={styles.entityButtons}>
        <RoundedButton
          text="Edit"
          onPress={() => navigation.navigate('CaloriesBMREdit', { entityId })}
          accessibilityLabel={'CaloriesBMR Edit Button'}
          testID="caloriesBMREditButton"
        />
        <RoundedButton
          text="Delete"
          onPress={() => setDeleteModalVisible(true)}
          accessibilityLabel={'CaloriesBMR Delete Button'}
          testID="caloriesBMRDeleteButton"
        />
        {deleteModalVisible && (
          <CaloriesBMRDeleteModal
            navigation={navigation}
            visible={deleteModalVisible}
            setVisible={setDeleteModalVisible}
            entity={caloriesBMR}
            testID="caloriesBMRDeleteModal"
          />
        )}
      </View>
    </ScrollView>
  );
}

const mapStateToProps = (state) => {
  return {
    caloriesBMR: state.caloriesBMRS.caloriesBMR,
    error: state.caloriesBMRS.errorOne,
    fetching: state.caloriesBMRS.fetchingOne,
    deleting: state.caloriesBMRS.deleting,
    errorDeleting: state.caloriesBMRS.errorDeleting,
  };
};

const mapDispatchToProps = (dispatch) => {
  return {
    getCaloriesBMR: (id) => dispatch(CaloriesBMRActions.caloriesBMRRequest(id)),
    getAllCaloriesBMRS: (options) => dispatch(CaloriesBMRActions.caloriesBMRAllRequest(options)),
    deleteCaloriesBMR: (id) => dispatch(CaloriesBMRActions.caloriesBMRDeleteRequest(id)),
    resetCaloriesBMRS: () => dispatch(CaloriesBMRActions.caloriesBMRReset()),
  };
};

export default connect(mapStateToProps, mapDispatchToProps)(CaloriesBMRDetailScreen);
