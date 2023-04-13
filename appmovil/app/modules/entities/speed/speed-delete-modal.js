import React from 'react';
import { TouchableHighlight, Modal, Text, View } from 'react-native';
import { connect } from 'react-redux';

import SpeedActions from './speed.reducer';

import styles from './speed-styles';

function SpeedDeleteModal(props) {
  const { visible, setVisible, entity, navigation, testID } = props;

  const deleteEntity = () => {
    props.deleteSpeed(entity.id);
    navigation.canGoBack() ? navigation.goBack() : navigation.navigate('Speed');
  };
  return (
    <Modal animationType="slide" transparent={true} visible={visible}>
      <View testID={testID} style={styles.centeredView}>
        <View style={styles.modalView}>
          <View style={[styles.flex, styles.flexRow]}>
            <Text style={styles.modalText}>Delete Speed {entity.id}?</Text>
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
    speed: state.speeds.speed,
    fetching: state.speeds.fetchingOne,
    deleting: state.speeds.deleting,
    errorDeleting: state.speeds.errorDeleting,
  };
};

const mapDispatchToProps = (dispatch) => {
  return {
    getSpeed: (id) => dispatch(SpeedActions.speedRequest(id)),
    getAllSpeeds: (options) => dispatch(SpeedActions.speedAllRequest(options)),
    deleteSpeed: (id) => dispatch(SpeedActions.speedDeleteRequest(id)),
    resetSpeeds: () => dispatch(SpeedActions.speedReset()),
  };
};

export default connect(mapStateToProps, mapDispatchToProps)(SpeedDeleteModal);
