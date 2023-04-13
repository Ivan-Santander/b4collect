import React from 'react';
import { ActivityIndicator, ScrollView, Text, View } from 'react-native';
import { connect } from 'react-redux';
import { useFocusEffect } from '@react-navigation/native';

import SleepSegmentActions from './sleep-segment.reducer';
import RoundedButton from '../../../shared/components/rounded-button/rounded-button';
import SleepSegmentDeleteModal from './sleep-segment-delete-modal';
import styles from './sleep-segment-styles';

function SleepSegmentDetailScreen(props) {
  const { route, getSleepSegment, navigation, sleepSegment, fetching, error } = props;
  const [deleteModalVisible, setDeleteModalVisible] = React.useState(false);
  // prevents display of stale reducer data
  const entityId = sleepSegment?.id ?? null;
  const routeEntityId = route.params?.entityId ?? null;
  const correctEntityLoaded = routeEntityId && entityId && routeEntityId.toString() === entityId.toString();

  useFocusEffect(
    React.useCallback(() => {
      if (!routeEntityId) {
        navigation.navigate('SleepSegment');
      } else {
        setDeleteModalVisible(false);
        getSleepSegment(routeEntityId);
      }
    }, [routeEntityId, getSleepSegment, navigation]),
  );

  if (!entityId && !fetching && error) {
    return (
      <View style={styles.loading}>
        <Text>Something went wrong fetching the SleepSegment.</Text>
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
    <ScrollView style={styles.container} contentContainerStyle={styles.paddedScrollView} testID="sleepSegmentDetailScrollView">
      <Text style={styles.label}>Id:</Text>
      <Text>{sleepSegment.id}</Text>
      {/* UsuarioId Field */}
      <Text style={styles.label}>UsuarioId:</Text>
      <Text testID="usuarioId">{sleepSegment.usuarioId}</Text>
      {/* EmpresaId Field */}
      <Text style={styles.label}>EmpresaId:</Text>
      <Text testID="empresaId">{sleepSegment.empresaId}</Text>
      {/* FieldSleepSegmentType Field */}
      <Text style={styles.label}>FieldSleepSegmentType:</Text>
      <Text testID="fieldSleepSegmentType">{sleepSegment.fieldSleepSegmentType}</Text>
      {/* FieldBloodGlucoseSpecimenSource Field */}
      <Text style={styles.label}>FieldBloodGlucoseSpecimenSource:</Text>
      <Text testID="fieldBloodGlucoseSpecimenSource">{sleepSegment.fieldBloodGlucoseSpecimenSource}</Text>
      {/* StartTime Field */}
      <Text style={styles.label}>StartTime:</Text>
      <Text testID="startTime">{String(sleepSegment.startTime)}</Text>
      {/* EndTime Field */}
      <Text style={styles.label}>EndTime:</Text>
      <Text testID="endTime">{String(sleepSegment.endTime)}</Text>

      <View style={styles.entityButtons}>
        <RoundedButton
          text="Edit"
          onPress={() => navigation.navigate('SleepSegmentEdit', { entityId })}
          accessibilityLabel={'SleepSegment Edit Button'}
          testID="sleepSegmentEditButton"
        />
        <RoundedButton
          text="Delete"
          onPress={() => setDeleteModalVisible(true)}
          accessibilityLabel={'SleepSegment Delete Button'}
          testID="sleepSegmentDeleteButton"
        />
        {deleteModalVisible && (
          <SleepSegmentDeleteModal
            navigation={navigation}
            visible={deleteModalVisible}
            setVisible={setDeleteModalVisible}
            entity={sleepSegment}
            testID="sleepSegmentDeleteModal"
          />
        )}
      </View>
    </ScrollView>
  );
}

const mapStateToProps = (state) => {
  return {
    sleepSegment: state.sleepSegments.sleepSegment,
    error: state.sleepSegments.errorOne,
    fetching: state.sleepSegments.fetchingOne,
    deleting: state.sleepSegments.deleting,
    errorDeleting: state.sleepSegments.errorDeleting,
  };
};

const mapDispatchToProps = (dispatch) => {
  return {
    getSleepSegment: (id) => dispatch(SleepSegmentActions.sleepSegmentRequest(id)),
    getAllSleepSegments: (options) => dispatch(SleepSegmentActions.sleepSegmentAllRequest(options)),
    deleteSleepSegment: (id) => dispatch(SleepSegmentActions.sleepSegmentDeleteRequest(id)),
    resetSleepSegments: () => dispatch(SleepSegmentActions.sleepSegmentReset()),
  };
};

export default connect(mapStateToProps, mapDispatchToProps)(SleepSegmentDetailScreen);
