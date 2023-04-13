import React from 'react';
import { TouchableHighlight, Modal, Text, View } from 'react-native';
import { connect } from 'react-redux';

import SpeedSummaryActions from './speed-summary.reducer';

import styles from './speed-summary-styles';

function SpeedSummaryDeleteModal(props) {
  const { visible, setVisible, entity, navigation, testID } = props;

  const deleteEntity = () => {
    props.deleteSpeedSummary(entity.id);
    navigation.canGoBack() ? navigation.goBack() : navigation.navigate('SpeedSummary');
  };
  return (
    <Modal animationType="slide" transparent={true} visible={visible}>
      <View testID={testID} style={styles.centeredView}>
        <View style={styles.modalView}>
          <View style={[styles.flex, styles.flexRow]}>
            <Text style={styles.modalText}>Delete SpeedSummary {entity.id}?</Text>
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
    speedSummary: state.speedSummaries.speedSummary,
    fetching: state.speedSummaries.fetchingOne,
    deleting: state.speedSummaries.deleting,
    errorDeleting: state.speedSummaries.errorDeleting,
  };
};

const mapDispatchToProps = (dispatch) => {
  return {
    getSpeedSummary: (id) => dispatch(SpeedSummaryActions.speedSummaryRequest(id)),
    getAllSpeedSummaries: (options) => dispatch(SpeedSummaryActions.speedSummaryAllRequest(options)),
    deleteSpeedSummary: (id) => dispatch(SpeedSummaryActions.speedSummaryDeleteRequest(id)),
    resetSpeedSummaries: () => dispatch(SpeedSummaryActions.speedSummaryReset()),
  };
};

export default connect(mapStateToProps, mapDispatchToProps)(SpeedSummaryDeleteModal);
