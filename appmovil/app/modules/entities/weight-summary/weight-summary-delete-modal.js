import React from 'react';
import { TouchableHighlight, Modal, Text, View } from 'react-native';
import { connect } from 'react-redux';

import WeightSummaryActions from './weight-summary.reducer';

import styles from './weight-summary-styles';

function WeightSummaryDeleteModal(props) {
  const { visible, setVisible, entity, navigation, testID } = props;

  const deleteEntity = () => {
    props.deleteWeightSummary(entity.id);
    navigation.canGoBack() ? navigation.goBack() : navigation.navigate('WeightSummary');
  };
  return (
    <Modal animationType="slide" transparent={true} visible={visible}>
      <View testID={testID} style={styles.centeredView}>
        <View style={styles.modalView}>
          <View style={[styles.flex, styles.flexRow]}>
            <Text style={styles.modalText}>Delete WeightSummary {entity.id}?</Text>
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
    weightSummary: state.weightSummaries.weightSummary,
    fetching: state.weightSummaries.fetchingOne,
    deleting: state.weightSummaries.deleting,
    errorDeleting: state.weightSummaries.errorDeleting,
  };
};

const mapDispatchToProps = (dispatch) => {
  return {
    getWeightSummary: (id) => dispatch(WeightSummaryActions.weightSummaryRequest(id)),
    getAllWeightSummaries: (options) => dispatch(WeightSummaryActions.weightSummaryAllRequest(options)),
    deleteWeightSummary: (id) => dispatch(WeightSummaryActions.weightSummaryDeleteRequest(id)),
    resetWeightSummaries: () => dispatch(WeightSummaryActions.weightSummaryReset()),
  };
};

export default connect(mapStateToProps, mapDispatchToProps)(WeightSummaryDeleteModal);
