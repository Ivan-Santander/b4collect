import React from 'react';
import { ActivityIndicator, ScrollView, Text, View } from 'react-native';
import { connect } from 'react-redux';
import { useFocusEffect } from '@react-navigation/native';

import BodyFatPercentageActions from './body-fat-percentage.reducer';
import RoundedButton from '../../../shared/components/rounded-button/rounded-button';
import BodyFatPercentageDeleteModal from './body-fat-percentage-delete-modal';
import styles from './body-fat-percentage-styles';

function BodyFatPercentageDetailScreen(props) {
  const { route, getBodyFatPercentage, navigation, bodyFatPercentage, fetching, error } = props;
  const [deleteModalVisible, setDeleteModalVisible] = React.useState(false);
  // prevents display of stale reducer data
  const entityId = bodyFatPercentage?.id ?? null;
  const routeEntityId = route.params?.entityId ?? null;
  const correctEntityLoaded = routeEntityId && entityId && routeEntityId.toString() === entityId.toString();

  useFocusEffect(
    React.useCallback(() => {
      if (!routeEntityId) {
        navigation.navigate('BodyFatPercentage');
      } else {
        setDeleteModalVisible(false);
        getBodyFatPercentage(routeEntityId);
      }
    }, [routeEntityId, getBodyFatPercentage, navigation]),
  );

  if (!entityId && !fetching && error) {
    return (
      <View style={styles.loading}>
        <Text>Something went wrong fetching the BodyFatPercentage.</Text>
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
    <ScrollView style={styles.container} contentContainerStyle={styles.paddedScrollView} testID="bodyFatPercentageDetailScrollView">
      <Text style={styles.label}>Id:</Text>
      <Text>{bodyFatPercentage.id}</Text>
      {/* UsuarioId Field */}
      <Text style={styles.label}>UsuarioId:</Text>
      <Text testID="usuarioId">{bodyFatPercentage.usuarioId}</Text>
      {/* EmpresaId Field */}
      <Text style={styles.label}>EmpresaId:</Text>
      <Text testID="empresaId">{bodyFatPercentage.empresaId}</Text>
      {/* FieldPorcentage Field */}
      <Text style={styles.label}>FieldPorcentage:</Text>
      <Text testID="fieldPorcentage">{bodyFatPercentage.fieldPorcentage}</Text>
      {/* EndTime Field */}
      <Text style={styles.label}>EndTime:</Text>
      <Text testID="endTime">{String(bodyFatPercentage.endTime)}</Text>

      <View style={styles.entityButtons}>
        <RoundedButton
          text="Edit"
          onPress={() => navigation.navigate('BodyFatPercentageEdit', { entityId })}
          accessibilityLabel={'BodyFatPercentage Edit Button'}
          testID="bodyFatPercentageEditButton"
        />
        <RoundedButton
          text="Delete"
          onPress={() => setDeleteModalVisible(true)}
          accessibilityLabel={'BodyFatPercentage Delete Button'}
          testID="bodyFatPercentageDeleteButton"
        />
        {deleteModalVisible && (
          <BodyFatPercentageDeleteModal
            navigation={navigation}
            visible={deleteModalVisible}
            setVisible={setDeleteModalVisible}
            entity={bodyFatPercentage}
            testID="bodyFatPercentageDeleteModal"
          />
        )}
      </View>
    </ScrollView>
  );
}

const mapStateToProps = (state) => {
  return {
    bodyFatPercentage: state.bodyFatPercentages.bodyFatPercentage,
    error: state.bodyFatPercentages.errorOne,
    fetching: state.bodyFatPercentages.fetchingOne,
    deleting: state.bodyFatPercentages.deleting,
    errorDeleting: state.bodyFatPercentages.errorDeleting,
  };
};

const mapDispatchToProps = (dispatch) => {
  return {
    getBodyFatPercentage: (id) => dispatch(BodyFatPercentageActions.bodyFatPercentageRequest(id)),
    getAllBodyFatPercentages: (options) => dispatch(BodyFatPercentageActions.bodyFatPercentageAllRequest(options)),
    deleteBodyFatPercentage: (id) => dispatch(BodyFatPercentageActions.bodyFatPercentageDeleteRequest(id)),
    resetBodyFatPercentages: () => dispatch(BodyFatPercentageActions.bodyFatPercentageReset()),
  };
};

export default connect(mapStateToProps, mapDispatchToProps)(BodyFatPercentageDetailScreen);
