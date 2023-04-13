import React from 'react';
import { ActivityIndicator, ScrollView, Text, View } from 'react-native';
import { connect } from 'react-redux';
import { useFocusEffect } from '@react-navigation/native';

import BloodPressureActions from './blood-pressure.reducer';
import RoundedButton from '../../../shared/components/rounded-button/rounded-button';
import BloodPressureDeleteModal from './blood-pressure-delete-modal';
import styles from './blood-pressure-styles';

function BloodPressureDetailScreen(props) {
  const { route, getBloodPressure, navigation, bloodPressure, fetching, error } = props;
  const [deleteModalVisible, setDeleteModalVisible] = React.useState(false);
  // prevents display of stale reducer data
  const entityId = bloodPressure?.id ?? null;
  const routeEntityId = route.params?.entityId ?? null;
  const correctEntityLoaded = routeEntityId && entityId && routeEntityId.toString() === entityId.toString();

  useFocusEffect(
    React.useCallback(() => {
      if (!routeEntityId) {
        navigation.navigate('BloodPressure');
      } else {
        setDeleteModalVisible(false);
        getBloodPressure(routeEntityId);
      }
    }, [routeEntityId, getBloodPressure, navigation]),
  );

  if (!entityId && !fetching && error) {
    return (
      <View style={styles.loading}>
        <Text>Something went wrong fetching the BloodPressure.</Text>
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
    <ScrollView style={styles.container} contentContainerStyle={styles.paddedScrollView} testID="bloodPressureDetailScrollView">
      <Text style={styles.label}>Id:</Text>
      <Text>{bloodPressure.id}</Text>
      {/* UsuarioId Field */}
      <Text style={styles.label}>UsuarioId:</Text>
      <Text testID="usuarioId">{bloodPressure.usuarioId}</Text>
      {/* EmpresaId Field */}
      <Text style={styles.label}>EmpresaId:</Text>
      <Text testID="empresaId">{bloodPressure.empresaId}</Text>
      {/* FieldBloodPressureSystolic Field */}
      <Text style={styles.label}>FieldBloodPressureSystolic:</Text>
      <Text testID="fieldBloodPressureSystolic">{bloodPressure.fieldBloodPressureSystolic}</Text>
      {/* FieldBloodPressureDiastolic Field */}
      <Text style={styles.label}>FieldBloodPressureDiastolic:</Text>
      <Text testID="fieldBloodPressureDiastolic">{bloodPressure.fieldBloodPressureDiastolic}</Text>
      {/* FieldBodyPosition Field */}
      <Text style={styles.label}>FieldBodyPosition:</Text>
      <Text testID="fieldBodyPosition">{bloodPressure.fieldBodyPosition}</Text>
      {/* FieldBloodPressureMeasureLocation Field */}
      <Text style={styles.label}>FieldBloodPressureMeasureLocation:</Text>
      <Text testID="fieldBloodPressureMeasureLocation">{bloodPressure.fieldBloodPressureMeasureLocation}</Text>
      {/* EndTime Field */}
      <Text style={styles.label}>EndTime:</Text>
      <Text testID="endTime">{String(bloodPressure.endTime)}</Text>

      <View style={styles.entityButtons}>
        <RoundedButton
          text="Edit"
          onPress={() => navigation.navigate('BloodPressureEdit', { entityId })}
          accessibilityLabel={'BloodPressure Edit Button'}
          testID="bloodPressureEditButton"
        />
        <RoundedButton
          text="Delete"
          onPress={() => setDeleteModalVisible(true)}
          accessibilityLabel={'BloodPressure Delete Button'}
          testID="bloodPressureDeleteButton"
        />
        {deleteModalVisible && (
          <BloodPressureDeleteModal
            navigation={navigation}
            visible={deleteModalVisible}
            setVisible={setDeleteModalVisible}
            entity={bloodPressure}
            testID="bloodPressureDeleteModal"
          />
        )}
      </View>
    </ScrollView>
  );
}

const mapStateToProps = (state) => {
  return {
    bloodPressure: state.bloodPressures.bloodPressure,
    error: state.bloodPressures.errorOne,
    fetching: state.bloodPressures.fetchingOne,
    deleting: state.bloodPressures.deleting,
    errorDeleting: state.bloodPressures.errorDeleting,
  };
};

const mapDispatchToProps = (dispatch) => {
  return {
    getBloodPressure: (id) => dispatch(BloodPressureActions.bloodPressureRequest(id)),
    getAllBloodPressures: (options) => dispatch(BloodPressureActions.bloodPressureAllRequest(options)),
    deleteBloodPressure: (id) => dispatch(BloodPressureActions.bloodPressureDeleteRequest(id)),
    resetBloodPressures: () => dispatch(BloodPressureActions.bloodPressureReset()),
  };
};

export default connect(mapStateToProps, mapDispatchToProps)(BloodPressureDetailScreen);
