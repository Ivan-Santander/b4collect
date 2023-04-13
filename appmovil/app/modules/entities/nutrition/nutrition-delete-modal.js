import React from 'react';
import { TouchableHighlight, Modal, Text, View } from 'react-native';
import { connect } from 'react-redux';

import NutritionActions from './nutrition.reducer';

import styles from './nutrition-styles';

function NutritionDeleteModal(props) {
  const { visible, setVisible, entity, navigation, testID } = props;

  const deleteEntity = () => {
    props.deleteNutrition(entity.id);
    navigation.canGoBack() ? navigation.goBack() : navigation.navigate('Nutrition');
  };
  return (
    <Modal animationType="slide" transparent={true} visible={visible}>
      <View testID={testID} style={styles.centeredView}>
        <View style={styles.modalView}>
          <View style={[styles.flex, styles.flexRow]}>
            <Text style={styles.modalText}>Delete Nutrition {entity.id}?</Text>
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
    nutrition: state.nutritions.nutrition,
    fetching: state.nutritions.fetchingOne,
    deleting: state.nutritions.deleting,
    errorDeleting: state.nutritions.errorDeleting,
  };
};

const mapDispatchToProps = (dispatch) => {
  return {
    getNutrition: (id) => dispatch(NutritionActions.nutritionRequest(id)),
    getAllNutritions: (options) => dispatch(NutritionActions.nutritionAllRequest(options)),
    deleteNutrition: (id) => dispatch(NutritionActions.nutritionDeleteRequest(id)),
    resetNutritions: () => dispatch(NutritionActions.nutritionReset()),
  };
};

export default connect(mapStateToProps, mapDispatchToProps)(NutritionDeleteModal);
