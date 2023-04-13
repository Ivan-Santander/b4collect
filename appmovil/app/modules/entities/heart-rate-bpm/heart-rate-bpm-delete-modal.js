import React from 'react';
import { TouchableHighlight, Modal, Text, View } from 'react-native';
import { connect } from 'react-redux';

import HeartRateBpmActions from './heart-rate-bpm.reducer';

import styles from './heart-rate-bpm-styles';

function HeartRateBpmDeleteModal(props) {
  const { visible, setVisible, entity, navigation, testID } = props;

  const deleteEntity = () => {
    props.deleteHeartRateBpm(entity.id);
    navigation.canGoBack() ? navigation.goBack() : navigation.navigate('HeartRateBpm');
  };
  return (
    <Modal animationType="slide" transparent={true} visible={visible}>
      <View testID={testID} style={styles.centeredView}>
        <View style={styles.modalView}>
          <View style={[styles.flex, styles.flexRow]}>
            <Text style={styles.modalText}>Delete HeartRateBpm {entity.id}?</Text>
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
    heartRateBpm: state.heartRateBpms.heartRateBpm,
    fetching: state.heartRateBpms.fetchingOne,
    deleting: state.heartRateBpms.deleting,
    errorDeleting: state.heartRateBpms.errorDeleting,
  };
};

const mapDispatchToProps = (dispatch) => {
  return {
    getHeartRateBpm: (id) => dispatch(HeartRateBpmActions.heartRateBpmRequest(id)),
    getAllHeartRateBpms: (options) => dispatch(HeartRateBpmActions.heartRateBpmAllRequest(options)),
    deleteHeartRateBpm: (id) => dispatch(HeartRateBpmActions.heartRateBpmDeleteRequest(id)),
    resetHeartRateBpms: () => dispatch(HeartRateBpmActions.heartRateBpmReset()),
  };
};

export default connect(mapStateToProps, mapDispatchToProps)(HeartRateBpmDeleteModal);
