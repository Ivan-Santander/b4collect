import React from 'react';
import { TouchableHighlight, Modal, Text, View } from 'react-native';
import { connect } from 'react-redux';

import BloodGlucoseSummaryActions from './blood-glucose-summary.reducer';

import styles from './blood-glucose-summary-styles';

function BloodGlucoseSummaryDeleteModal(props) {
  const { visible, setVisible, entity, navigation, testID } = props;

  const deleteEntity = () => {
    props.deleteBloodGlucoseSummary(entity.id);
    navigation.canGoBack() ? navigation.goBack() : navigation.navigate('BloodGlucoseSummary');
  };
  return (
    <Modal animationType="slide" transparent={true} visible={visible}>
      <View testID={testID} style={styles.centeredView}>
        <View style={styles.modalView}>
          <View style={[styles.flex, styles.flexRow]}>
            <Text style={styles.modalText}>Delete BloodGlucoseSummary {entity.id}?</Text>
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
    bloodGlucoseSummary: state.bloodGlucoseSummaries.bloodGlucoseSummary,
    fetching: state.bloodGlucoseSummaries.fetchingOne,
    deleting: state.bloodGlucoseSummaries.deleting,
    errorDeleting: state.bloodGlucoseSummaries.errorDeleting,
  };
};

const mapDispatchToProps = (dispatch) => {
  return {
    getBloodGlucoseSummary: (id) => dispatch(BloodGlucoseSummaryActions.bloodGlucoseSummaryRequest(id)),
    getAllBloodGlucoseSummaries: (options) => dispatch(BloodGlucoseSummaryActions.bloodGlucoseSummaryAllRequest(options)),
    deleteBloodGlucoseSummary: (id) => dispatch(BloodGlucoseSummaryActions.bloodGlucoseSummaryDeleteRequest(id)),
    resetBloodGlucoseSummaries: () => dispatch(BloodGlucoseSummaryActions.bloodGlucoseSummaryReset()),
  };
};

export default connect(mapStateToProps, mapDispatchToProps)(BloodGlucoseSummaryDeleteModal);
