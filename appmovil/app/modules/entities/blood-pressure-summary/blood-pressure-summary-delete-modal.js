import React from 'react';
import { TouchableHighlight, Modal, Text, View } from 'react-native';
import { connect } from 'react-redux';

import BloodPressureSummaryActions from './blood-pressure-summary.reducer';

import styles from './blood-pressure-summary-styles';

function BloodPressureSummaryDeleteModal(props) {
  const { visible, setVisible, entity, navigation, testID } = props;

  const deleteEntity = () => {
    props.deleteBloodPressureSummary(entity.id);
    navigation.canGoBack() ? navigation.goBack() : navigation.navigate('BloodPressureSummary');
  };
  return (
    <Modal animationType="slide" transparent={true} visible={visible}>
      <View testID={testID} style={styles.centeredView}>
        <View style={styles.modalView}>
          <View style={[styles.flex, styles.flexRow]}>
            <Text style={styles.modalText}>Delete BloodPressureSummary {entity.id}?</Text>
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
    bloodPressureSummary: state.bloodPressureSummaries.bloodPressureSummary,
    fetching: state.bloodPressureSummaries.fetchingOne,
    deleting: state.bloodPressureSummaries.deleting,
    errorDeleting: state.bloodPressureSummaries.errorDeleting,
  };
};

const mapDispatchToProps = (dispatch) => {
  return {
    getBloodPressureSummary: (id) => dispatch(BloodPressureSummaryActions.bloodPressureSummaryRequest(id)),
    getAllBloodPressureSummaries: (options) => dispatch(BloodPressureSummaryActions.bloodPressureSummaryAllRequest(options)),
    deleteBloodPressureSummary: (id) => dispatch(BloodPressureSummaryActions.bloodPressureSummaryDeleteRequest(id)),
    resetBloodPressureSummaries: () => dispatch(BloodPressureSummaryActions.bloodPressureSummaryReset()),
  };
};

export default connect(mapStateToProps, mapDispatchToProps)(BloodPressureSummaryDeleteModal);
