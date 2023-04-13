import React from 'react';
import { TouchableHighlight, Modal, Text, View } from 'react-native';
import { connect } from 'react-redux';

import HeartRateSummaryActions from './heart-rate-summary.reducer';

import styles from './heart-rate-summary-styles';

function HeartRateSummaryDeleteModal(props) {
  const { visible, setVisible, entity, navigation, testID } = props;

  const deleteEntity = () => {
    props.deleteHeartRateSummary(entity.id);
    navigation.canGoBack() ? navigation.goBack() : navigation.navigate('HeartRateSummary');
  };
  return (
    <Modal animationType="slide" transparent={true} visible={visible}>
      <View testID={testID} style={styles.centeredView}>
        <View style={styles.modalView}>
          <View style={[styles.flex, styles.flexRow]}>
            <Text style={styles.modalText}>Delete HeartRateSummary {entity.id}?</Text>
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
    heartRateSummary: state.heartRateSummaries.heartRateSummary,
    fetching: state.heartRateSummaries.fetchingOne,
    deleting: state.heartRateSummaries.deleting,
    errorDeleting: state.heartRateSummaries.errorDeleting,
  };
};

const mapDispatchToProps = (dispatch) => {
  return {
    getHeartRateSummary: (id) => dispatch(HeartRateSummaryActions.heartRateSummaryRequest(id)),
    getAllHeartRateSummaries: (options) => dispatch(HeartRateSummaryActions.heartRateSummaryAllRequest(options)),
    deleteHeartRateSummary: (id) => dispatch(HeartRateSummaryActions.heartRateSummaryDeleteRequest(id)),
    resetHeartRateSummaries: () => dispatch(HeartRateSummaryActions.heartRateSummaryReset()),
  };
};

export default connect(mapStateToProps, mapDispatchToProps)(HeartRateSummaryDeleteModal);
