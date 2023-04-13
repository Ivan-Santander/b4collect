import React from 'react';
import { TouchableHighlight, Modal, Text, View } from 'react-native';
import { connect } from 'react-redux';

import StepCountDeltaActions from './step-count-delta.reducer';

import styles from './step-count-delta-styles';

function StepCountDeltaDeleteModal(props) {
  const { visible, setVisible, entity, navigation, testID } = props;

  const deleteEntity = () => {
    props.deleteStepCountDelta(entity.id);
    navigation.canGoBack() ? navigation.goBack() : navigation.navigate('StepCountDelta');
  };
  return (
    <Modal animationType="slide" transparent={true} visible={visible}>
      <View testID={testID} style={styles.centeredView}>
        <View style={styles.modalView}>
          <View style={[styles.flex, styles.flexRow]}>
            <Text style={styles.modalText}>Delete StepCountDelta {entity.id}?</Text>
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
    stepCountDelta: state.stepCountDeltas.stepCountDelta,
    fetching: state.stepCountDeltas.fetchingOne,
    deleting: state.stepCountDeltas.deleting,
    errorDeleting: state.stepCountDeltas.errorDeleting,
  };
};

const mapDispatchToProps = (dispatch) => {
  return {
    getStepCountDelta: (id) => dispatch(StepCountDeltaActions.stepCountDeltaRequest(id)),
    getAllStepCountDeltas: (options) => dispatch(StepCountDeltaActions.stepCountDeltaAllRequest(options)),
    deleteStepCountDelta: (id) => dispatch(StepCountDeltaActions.stepCountDeltaDeleteRequest(id)),
    resetStepCountDeltas: () => dispatch(StepCountDeltaActions.stepCountDeltaReset()),
  };
};

export default connect(mapStateToProps, mapDispatchToProps)(StepCountDeltaDeleteModal);
