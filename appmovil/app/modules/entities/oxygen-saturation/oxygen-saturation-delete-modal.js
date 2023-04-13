import React from 'react';
import { TouchableHighlight, Modal, Text, View } from 'react-native';
import { connect } from 'react-redux';

import OxygenSaturationActions from './oxygen-saturation.reducer';

import styles from './oxygen-saturation-styles';

function OxygenSaturationDeleteModal(props) {
  const { visible, setVisible, entity, navigation, testID } = props;

  const deleteEntity = () => {
    props.deleteOxygenSaturation(entity.id);
    navigation.canGoBack() ? navigation.goBack() : navigation.navigate('OxygenSaturation');
  };
  return (
    <Modal animationType="slide" transparent={true} visible={visible}>
      <View testID={testID} style={styles.centeredView}>
        <View style={styles.modalView}>
          <View style={[styles.flex, styles.flexRow]}>
            <Text style={styles.modalText}>Delete OxygenSaturation {entity.id}?</Text>
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
    oxygenSaturation: state.oxygenSaturations.oxygenSaturation,
    fetching: state.oxygenSaturations.fetchingOne,
    deleting: state.oxygenSaturations.deleting,
    errorDeleting: state.oxygenSaturations.errorDeleting,
  };
};

const mapDispatchToProps = (dispatch) => {
  return {
    getOxygenSaturation: (id) => dispatch(OxygenSaturationActions.oxygenSaturationRequest(id)),
    getAllOxygenSaturations: (options) => dispatch(OxygenSaturationActions.oxygenSaturationAllRequest(options)),
    deleteOxygenSaturation: (id) => dispatch(OxygenSaturationActions.oxygenSaturationDeleteRequest(id)),
    resetOxygenSaturations: () => dispatch(OxygenSaturationActions.oxygenSaturationReset()),
  };
};

export default connect(mapStateToProps, mapDispatchToProps)(OxygenSaturationDeleteModal);
