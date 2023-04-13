import React from 'react';
import { TouchableHighlight, Modal, Text, View } from 'react-native';
import { connect } from 'react-redux';

import BodyFatPercentageSummaryActions from './body-fat-percentage-summary.reducer';

import styles from './body-fat-percentage-summary-styles';

function BodyFatPercentageSummaryDeleteModal(props) {
  const { visible, setVisible, entity, navigation, testID } = props;

  const deleteEntity = () => {
    props.deleteBodyFatPercentageSummary(entity.id);
    navigation.canGoBack() ? navigation.goBack() : navigation.navigate('BodyFatPercentageSummary');
  };
  return (
    <Modal animationType="slide" transparent={true} visible={visible}>
      <View testID={testID} style={styles.centeredView}>
        <View style={styles.modalView}>
          <View style={[styles.flex, styles.flexRow]}>
            <Text style={styles.modalText}>Delete BodyFatPercentageSummary {entity.id}?</Text>
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
    bodyFatPercentageSummary: state.bodyFatPercentageSummaries.bodyFatPercentageSummary,
    fetching: state.bodyFatPercentageSummaries.fetchingOne,
    deleting: state.bodyFatPercentageSummaries.deleting,
    errorDeleting: state.bodyFatPercentageSummaries.errorDeleting,
  };
};

const mapDispatchToProps = (dispatch) => {
  return {
    getBodyFatPercentageSummary: (id) => dispatch(BodyFatPercentageSummaryActions.bodyFatPercentageSummaryRequest(id)),
    getAllBodyFatPercentageSummaries: (options) => dispatch(BodyFatPercentageSummaryActions.bodyFatPercentageSummaryAllRequest(options)),
    deleteBodyFatPercentageSummary: (id) => dispatch(BodyFatPercentageSummaryActions.bodyFatPercentageSummaryDeleteRequest(id)),
    resetBodyFatPercentageSummaries: () => dispatch(BodyFatPercentageSummaryActions.bodyFatPercentageSummaryReset()),
  };
};

export default connect(mapStateToProps, mapDispatchToProps)(BodyFatPercentageSummaryDeleteModal);
