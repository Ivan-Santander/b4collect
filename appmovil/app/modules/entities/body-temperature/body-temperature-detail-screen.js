import React from 'react';
import { ActivityIndicator, ScrollView, Text, View } from 'react-native';
import { connect } from 'react-redux';
import { useFocusEffect } from '@react-navigation/native';

import BodyTemperatureActions from './body-temperature.reducer';
import RoundedButton from '../../../shared/components/rounded-button/rounded-button';
import BodyTemperatureDeleteModal from './body-temperature-delete-modal';
import styles from './body-temperature-styles';

function BodyTemperatureDetailScreen(props) {
  const { route, getBodyTemperature, navigation, bodyTemperature, fetching, error } = props;
  const [deleteModalVisible, setDeleteModalVisible] = React.useState(false);
  // prevents display of stale reducer data
  const entityId = bodyTemperature?.id ?? null;
  const routeEntityId = route.params?.entityId ?? null;
  const correctEntityLoaded = routeEntityId && entityId && routeEntityId.toString() === entityId.toString();

  useFocusEffect(
    React.useCallback(() => {
      if (!routeEntityId) {
        navigation.navigate('BodyTemperature');
      } else {
        setDeleteModalVisible(false);
        getBodyTemperature(routeEntityId);
      }
    }, [routeEntityId, getBodyTemperature, navigation]),
  );

  if (!entityId && !fetching && error) {
    return (
      <View style={styles.loading}>
        <Text>Something went wrong fetching the BodyTemperature.</Text>
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
    <ScrollView style={styles.container} contentContainerStyle={styles.paddedScrollView} testID="bodyTemperatureDetailScrollView">
      <Text style={styles.label}>Id:</Text>
      <Text>{bodyTemperature.id}</Text>
      {/* UsuarioId Field */}
      <Text style={styles.label}>UsuarioId:</Text>
      <Text testID="usuarioId">{bodyTemperature.usuarioId}</Text>
      {/* EmpresaId Field */}
      <Text style={styles.label}>EmpresaId:</Text>
      <Text testID="empresaId">{bodyTemperature.empresaId}</Text>
      {/* FieldBodyTemperature Field */}
      <Text style={styles.label}>FieldBodyTemperature:</Text>
      <Text testID="fieldBodyTemperature">{bodyTemperature.fieldBodyTemperature}</Text>
      {/* FieldBodyTemperatureMeasureLocation Field */}
      <Text style={styles.label}>FieldBodyTemperatureMeasureLocation:</Text>
      <Text testID="fieldBodyTemperatureMeasureLocation">{bodyTemperature.fieldBodyTemperatureMeasureLocation}</Text>
      {/* EndTime Field */}
      <Text style={styles.label}>EndTime:</Text>
      <Text testID="endTime">{String(bodyTemperature.endTime)}</Text>

      <View style={styles.entityButtons}>
        <RoundedButton
          text="Edit"
          onPress={() => navigation.navigate('BodyTemperatureEdit', { entityId })}
          accessibilityLabel={'BodyTemperature Edit Button'}
          testID="bodyTemperatureEditButton"
        />
        <RoundedButton
          text="Delete"
          onPress={() => setDeleteModalVisible(true)}
          accessibilityLabel={'BodyTemperature Delete Button'}
          testID="bodyTemperatureDeleteButton"
        />
        {deleteModalVisible && (
          <BodyTemperatureDeleteModal
            navigation={navigation}
            visible={deleteModalVisible}
            setVisible={setDeleteModalVisible}
            entity={bodyTemperature}
            testID="bodyTemperatureDeleteModal"
          />
        )}
      </View>
    </ScrollView>
  );
}

const mapStateToProps = (state) => {
  return {
    bodyTemperature: state.bodyTemperatures.bodyTemperature,
    error: state.bodyTemperatures.errorOne,
    fetching: state.bodyTemperatures.fetchingOne,
    deleting: state.bodyTemperatures.deleting,
    errorDeleting: state.bodyTemperatures.errorDeleting,
  };
};

const mapDispatchToProps = (dispatch) => {
  return {
    getBodyTemperature: (id) => dispatch(BodyTemperatureActions.bodyTemperatureRequest(id)),
    getAllBodyTemperatures: (options) => dispatch(BodyTemperatureActions.bodyTemperatureAllRequest(options)),
    deleteBodyTemperature: (id) => dispatch(BodyTemperatureActions.bodyTemperatureDeleteRequest(id)),
    resetBodyTemperatures: () => dispatch(BodyTemperatureActions.bodyTemperatureReset()),
  };
};

export default connect(mapStateToProps, mapDispatchToProps)(BodyTemperatureDetailScreen);
