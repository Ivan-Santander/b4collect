import React from 'react';
import { ActivityIndicator, ScrollView, Text, View } from 'react-native';
import { connect } from 'react-redux';
import { useFocusEffect } from '@react-navigation/native';

import HeightSummaryActions from './height-summary.reducer';
import RoundedButton from '../../../shared/components/rounded-button/rounded-button';
import HeightSummaryDeleteModal from './height-summary-delete-modal';
import styles from './height-summary-styles';

function HeightSummaryDetailScreen(props) {
  const { route, getHeightSummary, navigation, heightSummary, fetching, error } = props;
  const [deleteModalVisible, setDeleteModalVisible] = React.useState(false);
  // prevents display of stale reducer data
  const entityId = heightSummary?.id ?? null;
  const routeEntityId = route.params?.entityId ?? null;
  const correctEntityLoaded = routeEntityId && entityId && routeEntityId.toString() === entityId.toString();

  useFocusEffect(
    React.useCallback(() => {
      if (!routeEntityId) {
        navigation.navigate('HeightSummary');
      } else {
        setDeleteModalVisible(false);
        getHeightSummary(routeEntityId);
      }
    }, [routeEntityId, getHeightSummary, navigation]),
  );

  if (!entityId && !fetching && error) {
    return (
      <View style={styles.loading}>
        <Text>Something went wrong fetching the HeightSummary.</Text>
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
    <ScrollView style={styles.container} contentContainerStyle={styles.paddedScrollView} testID="heightSummaryDetailScrollView">
      <Text style={styles.label}>Id:</Text>
      <Text>{heightSummary.id}</Text>
      {/* UsuarioId Field */}
      <Text style={styles.label}>UsuarioId:</Text>
      <Text testID="usuarioId">{heightSummary.usuarioId}</Text>
      {/* EmpresaId Field */}
      <Text style={styles.label}>EmpresaId:</Text>
      <Text testID="empresaId">{heightSummary.empresaId}</Text>
      {/* FieldAverage Field */}
      <Text style={styles.label}>FieldAverage:</Text>
      <Text testID="fieldAverage">{heightSummary.fieldAverage}</Text>
      {/* FieldMax Field */}
      <Text style={styles.label}>FieldMax:</Text>
      <Text testID="fieldMax">{heightSummary.fieldMax}</Text>
      {/* FieldMin Field */}
      <Text style={styles.label}>FieldMin:</Text>
      <Text testID="fieldMin">{heightSummary.fieldMin}</Text>
      {/* StartTime Field */}
      <Text style={styles.label}>StartTime:</Text>
      <Text testID="startTime">{String(heightSummary.startTime)}</Text>
      {/* EndTime Field */}
      <Text style={styles.label}>EndTime:</Text>
      <Text testID="endTime">{String(heightSummary.endTime)}</Text>

      <View style={styles.entityButtons}>
        <RoundedButton
          text="Edit"
          onPress={() => navigation.navigate('HeightSummaryEdit', { entityId })}
          accessibilityLabel={'HeightSummary Edit Button'}
          testID="heightSummaryEditButton"
        />
        <RoundedButton
          text="Delete"
          onPress={() => setDeleteModalVisible(true)}
          accessibilityLabel={'HeightSummary Delete Button'}
          testID="heightSummaryDeleteButton"
        />
        {deleteModalVisible && (
          <HeightSummaryDeleteModal
            navigation={navigation}
            visible={deleteModalVisible}
            setVisible={setDeleteModalVisible}
            entity={heightSummary}
            testID="heightSummaryDeleteModal"
          />
        )}
      </View>
    </ScrollView>
  );
}

const mapStateToProps = (state) => {
  return {
    heightSummary: state.heightSummaries.heightSummary,
    error: state.heightSummaries.errorOne,
    fetching: state.heightSummaries.fetchingOne,
    deleting: state.heightSummaries.deleting,
    errorDeleting: state.heightSummaries.errorDeleting,
  };
};

const mapDispatchToProps = (dispatch) => {
  return {
    getHeightSummary: (id) => dispatch(HeightSummaryActions.heightSummaryRequest(id)),
    getAllHeightSummaries: (options) => dispatch(HeightSummaryActions.heightSummaryAllRequest(options)),
    deleteHeightSummary: (id) => dispatch(HeightSummaryActions.heightSummaryDeleteRequest(id)),
    resetHeightSummaries: () => dispatch(HeightSummaryActions.heightSummaryReset()),
  };
};

export default connect(mapStateToProps, mapDispatchToProps)(HeightSummaryDetailScreen);
