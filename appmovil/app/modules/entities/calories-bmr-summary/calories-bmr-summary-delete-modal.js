import React from 'react';
import { TouchableHighlight, Modal, Text, View } from 'react-native';
import { connect } from 'react-redux';

import CaloriesBmrSummaryActions from './calories-bmr-summary.reducer';

import styles from './calories-bmr-summary-styles';

function CaloriesBmrSummaryDeleteModal(props) {
  const { visible, setVisible, entity, navigation, testID } = props;

  const deleteEntity = () => {
    props.deleteCaloriesBmrSummary(entity.id);
    navigation.canGoBack() ? navigation.goBack() : navigation.navigate('CaloriesBmrSummary');
  };
  return (
    <Modal animationType="slide" transparent={true} visible={visible}>
      <View testID={testID} style={styles.centeredView}>
        <View style={styles.modalView}>
          <View style={[styles.flex, styles.flexRow]}>
            <Text style={styles.modalText}>Delete CaloriesBmrSummary {entity.id}?</Text>
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
    caloriesBmrSummary: state.caloriesBmrSummaries.caloriesBmrSummary,
    fetching: state.caloriesBmrSummaries.fetchingOne,
    deleting: state.caloriesBmrSummaries.deleting,
    errorDeleting: state.caloriesBmrSummaries.errorDeleting,
  };
};

const mapDispatchToProps = (dispatch) => {
  return {
    getCaloriesBmrSummary: (id) => dispatch(CaloriesBmrSummaryActions.caloriesBmrSummaryRequest(id)),
    getAllCaloriesBmrSummaries: (options) => dispatch(CaloriesBmrSummaryActions.caloriesBmrSummaryAllRequest(options)),
    deleteCaloriesBmrSummary: (id) => dispatch(CaloriesBmrSummaryActions.caloriesBmrSummaryDeleteRequest(id)),
    resetCaloriesBmrSummaries: () => dispatch(CaloriesBmrSummaryActions.caloriesBmrSummaryReset()),
  };
};

export default connect(mapStateToProps, mapDispatchToProps)(CaloriesBmrSummaryDeleteModal);
