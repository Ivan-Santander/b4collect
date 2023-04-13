import React from 'react';
import { TouchableHighlight, Modal, Text, View } from 'react-native';
import { connect } from 'react-redux';

import CyclingWheelRevolutionActions from './cycling-wheel-revolution.reducer';

import styles from './cycling-wheel-revolution-styles';

function CyclingWheelRevolutionDeleteModal(props) {
  const { visible, setVisible, entity, navigation, testID } = props;

  const deleteEntity = () => {
    props.deleteCyclingWheelRevolution(entity.id);
    navigation.canGoBack() ? navigation.goBack() : navigation.navigate('CyclingWheelRevolution');
  };
  return (
    <Modal animationType="slide" transparent={true} visible={visible}>
      <View testID={testID} style={styles.centeredView}>
        <View style={styles.modalView}>
          <View style={[styles.flex, styles.flexRow]}>
            <Text style={styles.modalText}>Delete CyclingWheelRevolution {entity.id}?</Text>
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
    cyclingWheelRevolution: state.cyclingWheelRevolutions.cyclingWheelRevolution,
    fetching: state.cyclingWheelRevolutions.fetchingOne,
    deleting: state.cyclingWheelRevolutions.deleting,
    errorDeleting: state.cyclingWheelRevolutions.errorDeleting,
  };
};

const mapDispatchToProps = (dispatch) => {
  return {
    getCyclingWheelRevolution: (id) => dispatch(CyclingWheelRevolutionActions.cyclingWheelRevolutionRequest(id)),
    getAllCyclingWheelRevolutions: (options) => dispatch(CyclingWheelRevolutionActions.cyclingWheelRevolutionAllRequest(options)),
    deleteCyclingWheelRevolution: (id) => dispatch(CyclingWheelRevolutionActions.cyclingWheelRevolutionDeleteRequest(id)),
    resetCyclingWheelRevolutions: () => dispatch(CyclingWheelRevolutionActions.cyclingWheelRevolutionReset()),
  };
};

export default connect(mapStateToProps, mapDispatchToProps)(CyclingWheelRevolutionDeleteModal);
