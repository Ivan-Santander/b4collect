import React from 'react';
import { TouchableHighlight, Modal, Text, View } from 'react-native';
import { connect } from 'react-redux';

import BloodGlucoseActions from './blood-glucose.reducer';

import styles from './blood-glucose-styles';

function BloodGlucoseDeleteModal(props) {
  const { visible, setVisible, entity, navigation, testID } = props;

  const deleteEntity = () => {
    props.deleteBloodGlucose(entity.id);
    navigation.canGoBack() ? navigation.goBack() : navigation.navigate('BloodGlucose');
  };
  return (
    <Modal animationType="slide" transparent={true} visible={visible}>
      <View testID={testID} style={styles.centeredView}>
        <View style={styles.modalView}>
          <View style={[styles.flex, styles.flexRow]}>
            <Text style={styles.modalText}>Delete BloodGlucose {entity.id}?</Text>
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
    bloodGlucose: state.bloodGlucoses.bloodGlucose,
    fetching: state.bloodGlucoses.fetchingOne,
    deleting: state.bloodGlucoses.deleting,
    errorDeleting: state.bloodGlucoses.errorDeleting,
  };
};

const mapDispatchToProps = (dispatch) => {
  return {
    getBloodGlucose: (id) => dispatch(BloodGlucoseActions.bloodGlucoseRequest(id)),
    getAllBloodGlucoses: (options) => dispatch(BloodGlucoseActions.bloodGlucoseAllRequest(options)),
    deleteBloodGlucose: (id) => dispatch(BloodGlucoseActions.bloodGlucoseDeleteRequest(id)),
    resetBloodGlucoses: () => dispatch(BloodGlucoseActions.bloodGlucoseReset()),
  };
};

export default connect(mapStateToProps, mapDispatchToProps)(BloodGlucoseDeleteModal);
