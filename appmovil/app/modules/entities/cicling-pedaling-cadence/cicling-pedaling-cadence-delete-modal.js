import React from 'react';
import { TouchableHighlight, Modal, Text, View } from 'react-native';
import { connect } from 'react-redux';

import CiclingPedalingCadenceActions from './cicling-pedaling-cadence.reducer';

import styles from './cicling-pedaling-cadence-styles';

function CiclingPedalingCadenceDeleteModal(props) {
  const { visible, setVisible, entity, navigation, testID } = props;

  const deleteEntity = () => {
    props.deleteCiclingPedalingCadence(entity.id);
    navigation.canGoBack() ? navigation.goBack() : navigation.navigate('CiclingPedalingCadence');
  };
  return (
    <Modal animationType="slide" transparent={true} visible={visible}>
      <View testID={testID} style={styles.centeredView}>
        <View style={styles.modalView}>
          <View style={[styles.flex, styles.flexRow]}>
            <Text style={styles.modalText}>Delete CiclingPedalingCadence {entity.id}?</Text>
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
    ciclingPedalingCadence: state.ciclingPedalingCadences.ciclingPedalingCadence,
    fetching: state.ciclingPedalingCadences.fetchingOne,
    deleting: state.ciclingPedalingCadences.deleting,
    errorDeleting: state.ciclingPedalingCadences.errorDeleting,
  };
};

const mapDispatchToProps = (dispatch) => {
  return {
    getCiclingPedalingCadence: (id) => dispatch(CiclingPedalingCadenceActions.ciclingPedalingCadenceRequest(id)),
    getAllCiclingPedalingCadences: (options) => dispatch(CiclingPedalingCadenceActions.ciclingPedalingCadenceAllRequest(options)),
    deleteCiclingPedalingCadence: (id) => dispatch(CiclingPedalingCadenceActions.ciclingPedalingCadenceDeleteRequest(id)),
    resetCiclingPedalingCadences: () => dispatch(CiclingPedalingCadenceActions.ciclingPedalingCadenceReset()),
  };
};

export default connect(mapStateToProps, mapDispatchToProps)(CiclingPedalingCadenceDeleteModal);
