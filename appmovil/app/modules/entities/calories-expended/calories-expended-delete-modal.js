import React from 'react';
import { TouchableHighlight, Modal, Text, View } from 'react-native';
import { connect } from 'react-redux';

import CaloriesExpendedActions from './calories-expended.reducer';

import styles from './calories-expended-styles';

function CaloriesExpendedDeleteModal(props) {
  const { visible, setVisible, entity, navigation, testID } = props;

  const deleteEntity = () => {
    props.deleteCaloriesExpended(entity.id);
    navigation.canGoBack() ? navigation.goBack() : navigation.navigate('CaloriesExpended');
  };
  return (
    <Modal animationType="slide" transparent={true} visible={visible}>
      <View testID={testID} style={styles.centeredView}>
        <View style={styles.modalView}>
          <View style={[styles.flex, styles.flexRow]}>
            <Text style={styles.modalText}>Delete CaloriesExpended {entity.id}?</Text>
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
    caloriesExpended: state.caloriesExpendeds.caloriesExpended,
    fetching: state.caloriesExpendeds.fetchingOne,
    deleting: state.caloriesExpendeds.deleting,
    errorDeleting: state.caloriesExpendeds.errorDeleting,
  };
};

const mapDispatchToProps = (dispatch) => {
  return {
    getCaloriesExpended: (id) => dispatch(CaloriesExpendedActions.caloriesExpendedRequest(id)),
    getAllCaloriesExpendeds: (options) => dispatch(CaloriesExpendedActions.caloriesExpendedAllRequest(options)),
    deleteCaloriesExpended: (id) => dispatch(CaloriesExpendedActions.caloriesExpendedDeleteRequest(id)),
    resetCaloriesExpendeds: () => dispatch(CaloriesExpendedActions.caloriesExpendedReset()),
  };
};

export default connect(mapStateToProps, mapDispatchToProps)(CaloriesExpendedDeleteModal);
