import React from 'react';
import { ActivityIndicator, ScrollView, Text, View } from 'react-native';
import { connect } from 'react-redux';
import { useFocusEffect } from '@react-navigation/native';

import BodyFatPercentageSummaryActions from './body-fat-percentage-summary.reducer';
import RoundedButton from '../../../shared/components/rounded-button/rounded-button';
import BodyFatPercentageSummaryDeleteModal from './body-fat-percentage-summary-delete-modal';
import styles from './body-fat-percentage-summary-styles';

function BodyFatPercentageSummaryDetailScreen(props) {
  const { route, getBodyFatPercentageSummary, navigation, bodyFatPercentageSummary, fetching, error } = props;
  const [deleteModalVisible, setDeleteModalVisible] = React.useState(false);
  // prevents display of stale reducer data
  const entityId = bodyFatPercentageSummary?.id ?? null;
  const routeEntityId = route.params?.entityId ?? null;
  const correctEntityLoaded = routeEntityId && entityId && routeEntityId.toString() === entityId.toString();

  useFocusEffect(
    React.useCallback(() => {
      if (!routeEntityId) {
        navigation.navigate('BodyFatPercentageSummary');
      } else {
        setDeleteModalVisible(false);
        getBodyFatPercentageSummary(routeEntityId);
      }
    }, [routeEntityId, getBodyFatPercentageSummary, navigation]),
  );

  if (!entityId && !fetching && error) {
    return (
      <View style={styles.loading}>
        <Text>Something went wrong fetching the BodyFatPercentageSummary.</Text>
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
    <ScrollView style={styles.container} contentContainerStyle={styles.paddedScrollView} testID="bodyFatPercentageSummaryDetailScrollView">
      <Text style={styles.label}>Id:</Text>
      <Text>{bodyFatPercentageSummary.id}</Text>
      {/* UsuarioId Field */}
      <Text style={styles.label}>UsuarioId:</Text>
      <Text testID="usuarioId">{bodyFatPercentageSummary.usuarioId}</Text>
      {/* EmpresaId Field */}
      <Text style={styles.label}>EmpresaId:</Text>
      <Text testID="empresaId">{bodyFatPercentageSummary.empresaId}</Text>
      {/* FieldAverage Field */}
      <Text style={styles.label}>FieldAverage:</Text>
      <Text testID="fieldAverage">{bodyFatPercentageSummary.fieldAverage}</Text>
      {/* FieldMax Field */}
      <Text style={styles.label}>FieldMax:</Text>
      <Text testID="fieldMax">{bodyFatPercentageSummary.fieldMax}</Text>
      {/* FieldMin Field */}
      <Text style={styles.label}>FieldMin:</Text>
      <Text testID="fieldMin">{bodyFatPercentageSummary.fieldMin}</Text>
      {/* StartTime Field */}
      <Text style={styles.label}>StartTime:</Text>
      <Text testID="startTime">{String(bodyFatPercentageSummary.startTime)}</Text>
      {/* EndTime Field */}
      <Text style={styles.label}>EndTime:</Text>
      <Text testID="endTime">{String(bodyFatPercentageSummary.endTime)}</Text>

      <View style={styles.entityButtons}>
        <RoundedButton
          text="Edit"
          onPress={() => navigation.navigate('BodyFatPercentageSummaryEdit', { entityId })}
          accessibilityLabel={'BodyFatPercentageSummary Edit Button'}
          testID="bodyFatPercentageSummaryEditButton"
        />
        <RoundedButton
          text="Delete"
          onPress={() => setDeleteModalVisible(true)}
          accessibilityLabel={'BodyFatPercentageSummary Delete Button'}
          testID="bodyFatPercentageSummaryDeleteButton"
        />
        {deleteModalVisible && (
          <BodyFatPercentageSummaryDeleteModal
            navigation={navigation}
            visible={deleteModalVisible}
            setVisible={setDeleteModalVisible}
            entity={bodyFatPercentageSummary}
            testID="bodyFatPercentageSummaryDeleteModal"
          />
        )}
      </View>
    </ScrollView>
  );
}

const mapStateToProps = (state) => {
  return {
    bodyFatPercentageSummary: state.bodyFatPercentageSummaries.bodyFatPercentageSummary,
    error: state.bodyFatPercentageSummaries.errorOne,
    fetching: state.bodyFatPercentageSummaries.fetchingOne,
    deleting: state.bodyFatPercentageSummaries.deleting,
    errorDeleting: state.bodyFatPercentageSummaries.errorDeleting,
  };
};

const mapDispatchToProps = (dispatch) => {
  return {
    getBodyFatPercentageSummary: (id) => dispatch(BodyFatPercentageSummaryActions.bodyFatPercentageSummaryRequest(id)),
    getAllBodyFatPercentageSummaries: (options) => dispatch(BodyFatPercentageSummaryActions.bodyFatPercentageSummaryAllRequest(options)),
    deleteBodyFatPercentageSummary: (id) => dispatch(BodyFatPercentageSummaryActions.bodyFatPercentageSummaryDeleteRequest(id)),
    resetBodyFatPercentageSummaries: () => dispatch(BodyFatPercentageSummaryActions.bodyFatPercentageSummaryReset()),
  };
};

export default connect(mapStateToProps, mapDispatchToProps)(BodyFatPercentageSummaryDetailScreen);
