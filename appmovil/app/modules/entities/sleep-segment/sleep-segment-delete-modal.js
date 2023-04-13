import React from 'react';
import { TouchableHighlight, Modal, Text, View } from 'react-native';
import { connect } from 'react-redux';

import SleepSegmentActions from './sleep-segment.reducer';

import styles from './sleep-segment-styles';

function SleepSegmentDeleteModal(props) {
  const { visible, setVisible, entity, navigation, testID } = props;

  const deleteEntity = () => {
    props.deleteSleepSegment(entity.id);
    navigation.canGoBack() ? navigation.goBack() : navigation.navigate('SleepSegment');
  };
  return (
    <Modal animationType="slide" transparent={true} visible={visible}>
      <View testID={testID} style={styles.centeredView}>
        <View style={styles.modalView}>
          <View style={[styles.flex, styles.flexRow]}>
            <Text style={styles.modalText}>Delete SleepSegment {entity.id}?</Text>
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
    sleepSegment: state.sleepSegments.sleepSegment,
    fetching: state.sleepSegments.fetchingOne,
    deleting: state.sleepSegments.deleting,
    errorDeleting: state.sleepSegments.errorDeleting,
  };
};

const mapDispatchToProps = (dispatch) => {
  return {
    getSleepSegment: (id) => dispatch(SleepSegmentActions.sleepSegmentRequest(id)),
    getAllSleepSegments: (options) => dispatch(SleepSegmentActions.sleepSegmentAllRequest(options)),
    deleteSleepSegment: (id) => dispatch(SleepSegmentActions.sleepSegmentDeleteRequest(id)),
    resetSleepSegments: () => dispatch(SleepSegmentActions.sleepSegmentReset()),
  };
};

export default connect(mapStateToProps, mapDispatchToProps)(SleepSegmentDeleteModal);
