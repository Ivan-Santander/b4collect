import React from 'react';
import { ActivityIndicator, ScrollView, Text, View } from 'react-native';
import { connect } from 'react-redux';
import { useFocusEffect } from '@react-navigation/native';

import BloodGlucoseActions from './blood-glucose.reducer';
import RoundedButton from '../../../shared/components/rounded-button/rounded-button';
import BloodGlucoseDeleteModal from './blood-glucose-delete-modal';
import styles from './blood-glucose-styles';

function BloodGlucoseDetailScreen(props) {
  const { route, getBloodGlucose, navigation, bloodGlucose, fetching, error } = props;
  const [deleteModalVisible, setDeleteModalVisible] = React.useState(false);
  // prevents display of stale reducer data
  const entityId = bloodGlucose?.id ?? null;
  const routeEntityId = route.params?.entityId ?? null;
  const correctEntityLoaded = routeEntityId && entityId && routeEntityId.toString() === entityId.toString();

  useFocusEffect(
    React.useCallback(() => {
      if (!routeEntityId) {
        navigation.navigate('BloodGlucose');
      } else {
        setDeleteModalVisible(false);
        getBloodGlucose(routeEntityId);
      }
    }, [routeEntityId, getBloodGlucose, navigation]),
  );

  if (!entityId && !fetching && error) {
    return (
      <View style={styles.loading}>
        <Text>Something went wrong fetching the BloodGlucose.</Text>
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
    <ScrollView style={styles.container} contentContainerStyle={styles.paddedScrollView} testID="bloodGlucoseDetailScrollView">
      <Text style={styles.label}>Id:</Text>
      <Text>{bloodGlucose.id}</Text>
      {/* UsuarioId Field */}
      <Text style={styles.label}>UsuarioId:</Text>
      <Text testID="usuarioId">{bloodGlucose.usuarioId}</Text>
      {/* EmpresaId Field */}
      <Text style={styles.label}>EmpresaId:</Text>
      <Text testID="empresaId">{bloodGlucose.empresaId}</Text>
      {/* FieldBloodGlucoseLevel Field */}
      <Text style={styles.label}>FieldBloodGlucoseLevel:</Text>
      <Text testID="fieldBloodGlucoseLevel">{bloodGlucose.fieldBloodGlucoseLevel}</Text>
      {/* FieldTemporalRelationToMeal Field */}
      <Text style={styles.label}>FieldTemporalRelationToMeal:</Text>
      <Text testID="fieldTemporalRelationToMeal">{bloodGlucose.fieldTemporalRelationToMeal}</Text>
      {/* FieldMealType Field */}
      <Text style={styles.label}>FieldMealType:</Text>
      <Text testID="fieldMealType">{bloodGlucose.fieldMealType}</Text>
      {/* FieldTemporalRelationToSleep Field */}
      <Text style={styles.label}>FieldTemporalRelationToSleep:</Text>
      <Text testID="fieldTemporalRelationToSleep">{bloodGlucose.fieldTemporalRelationToSleep}</Text>
      {/* FieldBloodGlucoseSpecimenSource Field */}
      <Text style={styles.label}>FieldBloodGlucoseSpecimenSource:</Text>
      <Text testID="fieldBloodGlucoseSpecimenSource">{bloodGlucose.fieldBloodGlucoseSpecimenSource}</Text>
      {/* EndTime Field */}
      <Text style={styles.label}>EndTime:</Text>
      <Text testID="endTime">{String(bloodGlucose.endTime)}</Text>

      <View style={styles.entityButtons}>
        <RoundedButton
          text="Edit"
          onPress={() => navigation.navigate('BloodGlucoseEdit', { entityId })}
          accessibilityLabel={'BloodGlucose Edit Button'}
          testID="bloodGlucoseEditButton"
        />
        <RoundedButton
          text="Delete"
          onPress={() => setDeleteModalVisible(true)}
          accessibilityLabel={'BloodGlucose Delete Button'}
          testID="bloodGlucoseDeleteButton"
        />
        {deleteModalVisible && (
          <BloodGlucoseDeleteModal
            navigation={navigation}
            visible={deleteModalVisible}
            setVisible={setDeleteModalVisible}
            entity={bloodGlucose}
            testID="bloodGlucoseDeleteModal"
          />
        )}
      </View>
    </ScrollView>
  );
}

const mapStateToProps = (state) => {
  return {
    bloodGlucose: state.bloodGlucoses.bloodGlucose,
    error: state.bloodGlucoses.errorOne,
    fetching: state.bloodGlucoses.fetchingOne,
    deleting: state.bloodGlucoses.deleting,
    errorDeleting: state.bloodGlucoses.errorDeleting,
  };
};

const mapDispatchToProps = (dispatch) => {
  return {
    getBloodGlucose: (id) => dispatch(BloodGlucoseActions.bloodGlucoseRequest(id)),
    getAllBloodGlucoses: (options) => dispatch(BloodGlucoseActions.bloodGlucoseAllRequest(options)),
    deleteBloodGlucose: (id) => dispatch(BloodGlucoseActions.bloodGlucoseDeleteRequest(id)),
    resetBloodGlucoses: () => dispatch(BloodGlucoseActions.bloodGlucoseReset()),
  };
};

export default connect(mapStateToProps, mapDispatchToProps)(BloodGlucoseDetailScreen);
