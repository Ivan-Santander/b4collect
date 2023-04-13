import React from 'react';
import { TouchableHighlight, Modal, Text, View } from 'react-native';
import { connect } from 'react-redux';

import LocationSampleActions from './location-sample.reducer';

import styles from './location-sample-styles';

function LocationSampleDeleteModal(props) {
  const { visible, setVisible, entity, navigation, testID } = props;

  const deleteEntity = () => {
    props.deleteLocationSample(entity.id);
    navigation.canGoBack() ? navigation.goBack() : navigation.navigate('LocationSample');
  };
  return (
    <Modal animationType="slide" transparent={true} visible={visible}>
      <View testID={testID} style={styles.centeredView}>
        <View style={styles.modalView}>
          <View style={[styles.flex, styles.flexRow]}>
            <Text style={styles.modalText}>Delete LocationSample {entity.id}?</Text>
          </View>
          <View style={[styles.flexRow]}>
            <TouchableHighlight
              style={[styles.openButton, styles.cancelButton]}
              onPress={() => {
                setVisible(false);
              }}>
              <Text style={styles.textStyle}>Cancel</Text>
            </TouchableHighlight>
            <TouchableHighlight style={[styles.openButton, styles.submitButton]} onPress={deleteEntity} testID="deleteButton">
              <Text style={styles.textStyle}>Delete</Text>
            </TouchableHighlight>
          </View>
        </View>
      </View>
    </Modal>
  );
}

const mapStateToProps = (state) => {
  return {
    locationSample: state.locationSamples.locationSample,
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

export default connect(mapStateToProps, mapDispatchToProps)(LocationSampleDeleteModal);
