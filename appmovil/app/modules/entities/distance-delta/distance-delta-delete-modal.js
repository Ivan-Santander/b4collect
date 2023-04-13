import React from 'react';
import { TouchableHighlight, Modal, Text, View } from 'react-native';
import { connect } from 'react-redux';

import DistanceDeltaActions from './distance-delta.reducer';

import styles from './distance-delta-styles';

function DistanceDeltaDeleteModal(props) {
  const { visible, setVisible, entity, navigation, testID } = props;

  const deleteEntity = () => {
    props.deleteDistanceDelta(entity.id);
    navigation.canGoBack() ? navigation.goBack() : navigation.navigate('DistanceDelta');
  };
  return (
    <Modal animationType="slide" transparent={true} visible={visible}>
      <View testID={testID} style={styles.centeredView}>
        <View style={styles.modalView}>
          <View style={[styles.flex, styles.flexRow]}>
            <Text style={styles.modalText}>Delete DistanceDelta {entity.id}?</Text>
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
    distanceDelta: state.distanceDeltas.distanceDelta,
    fetching: state.distanceDeltas.fetchingOne,
    deleting: state.distanceDeltas.deleting,
    errorDeleting: state.distanceDeltas.errorDeleting,
  };
};

const mapDispatchToProps = (dispatch) => {
  return {
    getDistanceDelta: (id) => dispatch(DistanceDeltaActions.distanceDeltaRequest(id)),
    getAllDistanceDeltas: (options) => dispatch(DistanceDeltaActions.distanceDeltaAllRequest(options)),
    deleteDistanceDelta: (id) => dispatch(DistanceDeltaActions.distanceDeltaDeleteRequest(id)),
    resetDistanceDeltas: () => dispatch(DistanceDeltaActions.distanceDeltaReset()),
  };
};

export default connect(mapStateToProps, mapDispatchToProps)(DistanceDeltaDeleteModal);
