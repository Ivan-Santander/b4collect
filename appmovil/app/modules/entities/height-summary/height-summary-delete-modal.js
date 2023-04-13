import React from 'react';
import { TouchableHighlight, Modal, Text, View } from 'react-native';
import { connect } from 'react-redux';

import HeightSummaryActions from './height-summary.reducer';

import styles from './height-summary-styles';

function HeightSummaryDeleteModal(props) {
  const { visible, setVisible, entity, navigation, testID } = props;

  const deleteEntity = () => {
    props.deleteHeightSummary(entity.id);
    navigation.canGoBack() ? navigation.goBack() : navigation.navigate('HeightSummary');
  };
  return (
    <Modal animationType="slide" transparent={true} visible={visible}>
      <View testID={testID} style={styles.centeredView}>
        <View style={styles.modalView}>
          <View style={[styles.flex, styles.flexRow]}>
            <Text style={styles.modalText}>Delete HeightSummary {entity.id}?</Text>
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
    heightSummary: state.heightSummaries.heightSummary,
    fetching: state.heightSummaries.fetchingOne,
    deleting: state.heightSummaries.deleting,
    errorDeleting: state.heightSummaries.errorDeleting,
  };
};

const mapDispatchToProps = (dispatch) => {
  return {
    getHeightSummary: (id) => dispatch(HeightSummaryActions.heightSummaryRequest(id)),
    getAllHeightSummaries: (options) => dispatch(HeightSummaryActions.heightSummaryAllRequest(options)),
    deleteHeightSummary: (id) => dispatch(HeightSummaryActions.heightSummaryDeleteRequest(id)),
    resetHeightSummaries: () => dispatch(HeightSummaryActions.heightSummaryReset()),
  };
};

export default connect(mapStateToProps, mapDispatchToProps)(HeightSummaryDeleteModal);
