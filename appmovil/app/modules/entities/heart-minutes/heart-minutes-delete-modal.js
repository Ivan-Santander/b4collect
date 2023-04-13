import React from 'react';
import { TouchableHighlight, Modal, Text, View } from 'react-native';
import { connect } from 'react-redux';

import HeartMinutesActions from './heart-minutes.reducer';

import styles from './heart-minutes-styles';

function HeartMinutesDeleteModal(props) {
  const { visible, setVisible, entity, navigation, testID } = props;

  const deleteEntity = () => {
    props.deleteHeartMinutes(entity.id);
    navigation.canGoBack() ? navigation.goBack() : navigation.navigate('HeartMinutes');
  };
  return (
    <Modal animationType="slide" transparent={true} visible={visible}>
      <View testID={testID} style={styles.centeredView}>
        <View style={styles.modalView}>
          <View style={[styles.flex, styles.flexRow]}>
            <Text style={styles.modalText}>Delete HeartMinutes {entity.id}?</Text>
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
    heartMinutes: state.heartMinutes.heartMinutes,
    fetching: state.heartMinutes.fetchingOne,
    deleting: state.heartMinutes.deleting,
    errorDeleting: state.heartMinutes.errorDeleting,
  };
};

const mapDispatchToProps = (dispatch) => {
  return {
    getHeartMinutes: (id) => dispatch(HeartMinutesActions.heartMinutesRequest(id)),
    getAllHeartMinutes: (options) => dispatch(HeartMinutesActions.heartMinutesAllRequest(options)),
    deleteHeartMinutes: (id) => dispatch(HeartMinutesActions.heartMinutesDeleteRequest(id)),
    resetHeartMinutes: () => dispatch(HeartMinutesActions.heartMinutesReset()),
  };
};

export default connect(mapStateToProps, mapDispatchToProps)(HeartMinutesDeleteModal);
