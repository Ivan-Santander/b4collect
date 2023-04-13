import React from 'react';
import { TouchableHighlight, Modal, Text, View } from 'react-native';
import { connect } from 'react-redux';

import PoweSampleActions from './powe-sample.reducer';

import styles from './powe-sample-styles';

function PoweSampleDeleteModal(props) {
  const { visible, setVisible, entity, navigation, testID } = props;

  const deleteEntity = () => {
    props.deletePoweSample(entity.id);
    navigation.canGoBack() ? navigation.goBack() : navigation.navigate('PoweSample');
  };
  return (
    <Modal animationType="slide" transparent={true} visible={visible}>
      <View testID={testID} style={styles.centeredView}>
        <View style={styles.modalView}>
          <View style={[styles.flex, styles.flexRow]}>
            <Text style={styles.modalText}>Delete PoweSample {entity.id}?</Text>
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
    poweSample: state.poweSamples.poweSample,
    fetching: state.poweSamples.fetchingOne,
    deleting: state.poweSamples.deleting,
    errorDeleting: state.poweSamples.errorDeleting,
  };
};

const mapDispatchToProps = (dispatch) => {
  return {
    getPoweSample: (id) => dispatch(PoweSampleActions.poweSampleRequest(id)),
    getAllPoweSamples: (options) => dispatch(PoweSampleActions.poweSampleAllRequest(options)),
    deletePoweSample: (id) => dispatch(PoweSampleActions.poweSampleDeleteRequest(id)),
    resetPoweSamples: () => dispatch(PoweSampleActions.poweSampleReset()),
  };
};

export default connect(mapStateToProps, mapDispatchToProps)(PoweSampleDeleteModal);
