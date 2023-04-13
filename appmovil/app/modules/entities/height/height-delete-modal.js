import React from 'react';
import { TouchableHighlight, Modal, Text, View } from 'react-native';
import { connect } from 'react-redux';

import HeightActions from './height.reducer';

import styles from './height-styles';

function HeightDeleteModal(props) {
  const { visible, setVisible, entity, navigation, testID } = props;

  const deleteEntity = () => {
    props.deleteHeight(entity.id);
    navigation.canGoBack() ? navigation.goBack() : navigation.navigate('Height');
  };
  return (
    <Modal animationType="slide" transparent={true} visible={visible}>
      <View testID={testID} style={styles.centeredView}>
        <View style={styles.modalView}>
          <View style={[styles.flex, styles.flexRow]}>
            <Text style={styles.modalText}>Delete Height {entity.id}?</Text>
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
    height: state.heights.height,
    fetching: state.heights.fetchingOne,
    deleting: state.heights.deleting,
    errorDeleting: state.heights.errorDeleting,
  };
};

const mapDispatchToProps = (dispatch) => {
  return {
    getHeight: (id) => dispatch(HeightActions.heightRequest(id)),
    getAllHeights: (options) => dispatch(HeightActions.heightAllRequest(options)),
    deleteHeight: (id) => dispatch(HeightActions.heightDeleteRequest(id)),
    resetHeights: () => dispatch(HeightActions.heightReset()),
  };
};

export default connect(mapStateToProps, mapDispatchToProps)(HeightDeleteModal);
