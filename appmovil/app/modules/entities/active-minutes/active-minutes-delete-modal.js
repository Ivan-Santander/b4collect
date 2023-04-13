import React from 'react';
import { TouchableHighlight, Modal, Text, View } from 'react-native';
import { connect } from 'react-redux';

import ActiveMinutesActions from './active-minutes.reducer';

import styles from './active-minutes-styles';

function ActiveMinutesDeleteModal(props) {
  const { visible, setVisible, entity, navigation, testID } = props;

  const deleteEntity = () => {
    props.deleteActiveMinutes(entity.id);
    navigation.canGoBack() ? navigation.goBack() : navigation.navigate('ActiveMinutes');
  };
  return (
    <Modal animationType="slide" transparent={true} visible={visible}>
      <View testID={testID} style={styles.centeredView}>
        <View style={styles.modalView}>
          <View style={[styles.flex, styles.flexRow]}>
            <Text style={styles.modalText}>Delete ActiveMinutes {entity.id}?</Text>
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
    activeMinutes: state.activeMinutes.activeMinutes,
    fetching: state.activeMinutes.fetchingOne,
    deleting: state.activeMinutes.deleting,
    errorDeleting: state.activeMinutes.errorDeleting,
  };
};

const mapDispatchToProps = (dispatch) => {
  return {
    getActiveMinutes: (id) => dispatch(ActiveMinutesActions.activeMinutesRequest(id)),
    getAllActiveMinutes: (options) => dispatch(ActiveMinutesActions.activeMinutesAllRequest(options)),
    deleteActiveMinutes: (id) => dispatch(ActiveMinutesActions.activeMinutesDeleteRequest(id)),
    resetActiveMinutes: () => dispatch(ActiveMinutesActions.activeMinutesReset()),
  };
};

export default connect(mapStateToProps, mapDispatchToProps)(ActiveMinutesDeleteModal);
