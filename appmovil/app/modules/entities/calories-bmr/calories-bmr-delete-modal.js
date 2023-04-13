import React from 'react';
import { TouchableHighlight, Modal, Text, View } from 'react-native';
import { connect } from 'react-redux';

import CaloriesBMRActions from './calories-bmr.reducer';

import styles from './calories-bmr-styles';

function CaloriesBMRDeleteModal(props) {
  const { visible, setVisible, entity, navigation, testID } = props;

  const deleteEntity = () => {
    props.deleteCaloriesBMR(entity.id);
    navigation.canGoBack() ? navigation.goBack() : navigation.navigate('CaloriesBMR');
  };
  return (
    <Modal animationType="slide" transparent={true} visible={visible}>
      <View testID={testID} style={styles.centeredView}>
        <View style={styles.modalView}>
          <View style={[styles.flex, styles.flexRow]}>
            <Text style={styles.modalText}>Delete CaloriesBMR {entity.id}?</Text>
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
    caloriesBMR: state.caloriesBMRS.caloriesBMR,
    fetching: state.caloriesBMRS.fetchingOne,
    deleting: state.caloriesBMRS.deleting,
    errorDeleting: state.caloriesBMRS.errorDeleting,
  };
};

const mapDispatchToProps = (dispatch) => {
  return {
    getCaloriesBMR: (id) => dispatch(CaloriesBMRActions.caloriesBMRRequest(id)),
    getAllCaloriesBMRS: (options) => dispatch(CaloriesBMRActions.caloriesBMRAllRequest(options)),
    deleteCaloriesBMR: (id) => dispatch(CaloriesBMRActions.caloriesBMRDeleteRequest(id)),
    resetCaloriesBMRS: () => dispatch(CaloriesBMRActions.caloriesBMRReset()),
  };
};

export default connect(mapStateToProps, mapDispatchToProps)(CaloriesBMRDeleteModal);
