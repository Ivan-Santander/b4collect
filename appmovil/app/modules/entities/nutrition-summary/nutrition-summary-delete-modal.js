import React from 'react';
import { TouchableHighlight, Modal, Text, View } from 'react-native';
import { connect } from 'react-redux';

import NutritionSummaryActions from './nutrition-summary.reducer';

import styles from './nutrition-summary-styles';

function NutritionSummaryDeleteModal(props) {
  const { visible, setVisible, entity, navigation, testID } = props;

  const deleteEntity = () => {
    props.deleteNutritionSummary(entity.id);
    navigation.canGoBack() ? navigation.goBack() : navigation.navigate('NutritionSummary');
  };
  return (
    <Modal animationType="slide" transparent={true} visible={visible}>
      <View testID={testID} style={styles.centeredView}>
        <View style={styles.modalView}>
          <View style={[styles.flex, styles.flexRow]}>
            <Text style={styles.modalText}>Delete NutritionSummary {entity.id}?</Text>
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
    nutritionSummary: state.nutritionSummaries.nutritionSummary,
    fetching: state.nutritionSummaries.fetchingOne,
    deleting: state.nutritionSummaries.deleting,
    errorDeleting: state.nutritionSummaries.errorDeleting,
  };
};

const mapDispatchToProps = (dispatch) => {
  return {
    getNutritionSummary: (id) => dispatch(NutritionSummaryActions.nutritionSummaryRequest(id)),
    getAllNutritionSummaries: (options) => dispatch(NutritionSummaryActions.nutritionSummaryAllRequest(options)),
    deleteNutritionSummary: (id) => dispatch(NutritionSummaryActions.nutritionSummaryDeleteRequest(id)),
    resetNutritionSummaries: () => dispatch(NutritionSummaryActions.nutritionSummaryReset()),
  };
};

export default connect(mapStateToProps, mapDispatchToProps)(NutritionSummaryDeleteModal);
