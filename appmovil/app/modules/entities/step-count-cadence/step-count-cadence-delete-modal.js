import React from 'react';
import { TouchableHighlight, Modal, Text, View } from 'react-native';
import { connect } from 'react-redux';

import StepCountCadenceActions from './step-count-cadence.reducer';

import styles from './step-count-cadence-styles';

function StepCountCadenceDeleteModal(props) {
  const { visible, setVisible, entity, navigation, testID } = props;

  const deleteEntity = () => {
    props.deleteStepCountCadence(entity.id);
    navigation.canGoBack() ? navigation.goBack() : navigation.navigate('StepCountCadence');
  };
  return (
    <Modal animationType="slide" transparent={true} visible={visible}>
      <View testID={testID} style={styles.centeredView}>
        <View style={styles.modalView}>
          <View style={[styles.flex, styles.flexRow]}>
            <Text style={styles.modalText}>Delete StepCountCadence {entity.id}?</Text>
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
    stepCountCadence: state.stepCountCadences.stepCountCadence,
    fetching: state.stepCountCadences.fetchingOne,
    deleting: state.stepCountCadences.deleting,
    errorDeleting: state.stepCountCadences.errorDeleting,
  };
};

const mapDispatchToProps = (dispatch) => {
  return {
    getStepCountCadence: (id) => dispatch(StepCountCadenceActions.stepCountCadenceRequest(id)),
    getAllStepCountCadences: (options) => dispatch(StepCountCadenceActions.stepCountCadenceAllRequest(options)),
    deleteStepCountCadence: (id) => dispatch(StepCountCadenceActions.stepCountCadenceDeleteRequest(id)),
    resetStepCountCadences: () => dispatch(StepCountCadenceActions.stepCountCadenceReset()),
  };
};

export default connect(mapStateToProps, mapDispatchToProps)(StepCountCadenceDeleteModal);
