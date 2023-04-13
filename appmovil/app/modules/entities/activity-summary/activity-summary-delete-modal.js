import React from 'react';
import { TouchableHighlight, Modal, Text, View } from 'react-native';
import { connect } from 'react-redux';

import ActivitySummaryActions from './activity-summary.reducer';

import styles from './activity-summary-styles';

function ActivitySummaryDeleteModal(props) {
  const { visible, setVisible, entity, navigation, testID } = props;

  const deleteEntity = () => {
    props.deleteActivitySummary(entity.id);
    navigation.canGoBack() ? navigation.goBack() : navigation.navigate('ActivitySummary');
  };
  return (
    <Modal animationType="slide" transparent={true} visible={visible}>
      <View testID={testID} style={styles.centeredView}>
        <View style={styles.modalView}>
          <View style={[styles.flex, styles.flexRow]}>
            <Text style={styles.modalText}>Delete ActivitySummary {entity.id}?</Text>
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
    activitySummary: state.activitySummaries.activitySummary,
    fetching: state.activitySummaries.fetchingOne,
    deleting: state.activitySummaries.deleting,
    errorDeleting: state.activitySummaries.errorDeleting,
  };
};

const mapDispatchToProps = (dispatch) => {
  return {
    getActivitySummary: (id) => dispatch(ActivitySummaryActions.activitySummaryRequest(id)),
    getAllActivitySummaries: (options) => dispatch(ActivitySummaryActions.activitySummaryAllRequest(options)),
    deleteActivitySummary: (id) => dispatch(ActivitySummaryActions.activitySummaryDeleteRequest(id)),
    resetActivitySummaries: () => dispatch(ActivitySummaryActions.activitySummaryReset()),
  };
};

export default connect(mapStateToProps, mapDispatchToProps)(ActivitySummaryDeleteModal);
