import React from 'react';
import { ActivityIndicator, ScrollView, Text, View } from 'react-native';
import { connect } from 'react-redux';
import { useFocusEffect } from '@react-navigation/native';

import LocationSampleActions from './location-sample.reducer';
import RoundedButton from '../../../shared/components/rounded-button/rounded-button';
import LocationSampleDeleteModal from './location-sample-delete-modal';
import styles from './location-sample-styles';

function LocationSampleDetailScreen(props) {
  const { route, getLocationSample, navigation, locationSample, fetching, error } = props;
  const [deleteModalVisible, setDeleteModalVisible] = React.useState(false);
  // prevents display of stale reducer data
  const entityId = locationSample?.id ?? null;
  const routeEntityId = route.params?.entityId ?? null;
  const correctEntityLoaded = routeEntityId && entityId && routeEntityId.toString() === entityId.toString();

  useFocusEffect(
    React.useCallback(() => {
      if (!routeEntityId) {
        navigation.navigate('LocationSample');
      } else {
        setDeleteModalVisible(false);
        getLocationSample(routeEntityId);
      }
    }, [routeEntityId, getLocationSample, navigation]),
  );

  if (!entityId && !fetching && error) {
    return (
      <View style={styles.loading}>
        <Text>Something went wrong fetching the LocationSample.</Text>
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
    <ScrollView style={styles.container} contentContainerStyle={styles.paddedScrollView} testID="locationSampleDetailScrollView">
      <Text style={styles.label}>Id:</Text>
      <Text>{locationSample.id}</Text>
      {/* UsuarioId Field */}
      <Text style={styles.label}>UsuarioId:</Text>
      <Text testID="usuarioId">{locationSample.usuarioId}</Text>
      {/* EmpresaId Field */}
      <Text style={styles.label}>EmpresaId:</Text>
      <Text testID="empresaId">{locationSample.empresaId}</Text>
      {/* LatitudMin Field */}
      <Text style={styles.label}>LatitudMin:</Text>
      <Text testID="latitudMin">{locationSample.latitudMin}</Text>
      {/* LongitudMin Field */}
      <Text style={styles.label}>LongitudMin:</Text>
      <Text testID="longitudMin">{locationSample.longitudMin}</Text>
      {/* LatitudMax Field */}
      <Text style={styles.label}>LatitudMax:</Text>
      <Text testID="latitudMax">{locationSample.latitudMax}</Text>
      {/* LongitudMax Field */}
      <Text style={styles.label}>LongitudMax:</Text>
      <Text testID="longitudMax">{locationSample.longitudMax}</Text>
      {/* Accuracy Field */}
      <Text style={styles.label}>Accuracy:</Text>
      <Text testID="accuracy">{locationSample.accuracy}</Text>
      {/* Altitud Field */}
      <Text style={styles.label}>Altitud:</Text>
      <Text testID="altitud">{locationSample.altitud}</Text>
      {/* StartTime Field */}
      <Text style={styles.label}>StartTime:</Text>
      <Text testID="startTime">{String(locationSample.startTime)}</Text>
      {/* EndTime Field */}
      <Text style={styles.label}>EndTime:</Text>
      <Text testID="endTime">{String(locationSample.endTime)}</Text>

      <View style={styles.entityButtons}>
        <RoundedButton
          text="Edit"
          onPress={() => navigation.navigate('LocationSampleEdit', { entityId })}
          accessibilityLabel={'LocationSample Edit Button'}
          testID="locationSampleEditButton"
        />
        <RoundedButton
          text="Delete"
          onPress={() => setDeleteModalVisible(true)}
          accessibilityLabel={'LocationSample Delete Button'}
          testID="locationSampleDeleteButton"
        />
        {deleteModalVisible && (
          <LocationSampleDeleteModal
            navigation={navigation}
            visible={deleteModalVisible}
            setVisible={setDeleteModalVisible}
            entity={locationSample}
            testID="locationSampleDeleteModal"
          />
        )}
      </View>
    </ScrollView>
  );
}

const mapStateToProps = (state) => {
  return {
    locationSample: state.locationSamples.locationSample,
    error: state.locationSamples.errorOne,
    fetching: state.locationSamples.fetchingOne,
    deleting: state.locationSamples.deleting,
    errorDeleting: state.locationSamples.errorDeleting,
  };
};

const mapDispatchToProps = (dispatch) => {
  return {
    getLocationSample: (id) => dispatch(LocationSampleActions.locationSampleRequest(id)),
    getAllLocationSamples: (options) => dispatch(LocationSampleActions.locationSampleAllRequest(options)),
    deleteLocationSample: (id) => dispatch(LocationSampleActions.locationSampleDeleteRequest(id)),
    resetLocationSamples: () => dispatch(LocationSampleActions.locationSampleReset()),
  };
};

export default connect(mapStateToProps, mapDispatchToProps)(LocationSampleDetailScreen);
