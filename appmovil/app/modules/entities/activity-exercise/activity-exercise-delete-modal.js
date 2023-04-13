import React from 'react';
import { TouchableHighlight, Modal, Text, View } from 'react-native';
import { connect } from 'react-redux';

import ActivityExerciseActions from './activity-exercise.reducer';

import styles from './activity-exercise-styles';

function ActivityExerciseDeleteModal(props) {
  const { visible, setVisible, entity, navigation, testID } = props;

  const deleteEntity = () => {
    props.deleteActivityExercise(entity.id);
    navigation.canGoBack() ? navigation.goBack() : navigation.navigate('ActivityExercise');
  };
  return (
    <Modal animationType="slide" transparent={true} visible={visible}>
      <View testID={testID} style={styles.centeredView}>
        <View style={styles.modalView}>
          <View style={[styles.flex, styles.flexRow]}>
            <Text style={styles.modalText}>Delete ActivityExercise {entity.id}?</Text>
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
    activityExercise: state.activityExercises.activityExercise,
    fetching: state.activityExercises.fetchingOne,
    deleting: state.activityExercises.deleting,
    errorDeleting: state.activityExercises.errorDeleting,
  };
};

const mapDispatchToProps = (dispatch) => {
  return {
    getActivityExercise: (id) => dispatch(ActivityExerciseActions.activityExerciseRequest(id)),
    getAllActivityExercises: (options) => dispatch(ActivityExerciseActions.activityExerciseAllRequest(options)),
    deleteActivityExercise: (id) => dispatch(ActivityExerciseActions.activityExerciseDeleteRequest(id)),
    resetActivityExercises: () => dispatch(ActivityExerciseActions.activityExerciseReset()),
  };
};

export default connect(mapStateToProps, mapDispatchToProps)(ActivityExerciseDeleteModal);
