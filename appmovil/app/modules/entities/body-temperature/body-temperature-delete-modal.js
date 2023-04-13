import React from 'react';
import { TouchableHighlight, Modal, Text, View } from 'react-native';
import { connect } from 'react-redux';

import BodyTemperatureActions from './body-temperature.reducer';

import styles from './body-temperature-styles';

function BodyTemperatureDeleteModal(props) {
  const { visible, setVisible, entity, navigation, testID } = props;

  const deleteEntity = () => {
    props.deleteBodyTemperature(entity.id);
    navigation.canGoBack() ? navigation.goBack() : navigation.navigate('BodyTemperature');
  };
  return (
    <Modal animationType="slide" transparent={true} visible={visible}>
      <View testID={testID} style={styles.centeredView}>
        <View style={styles.modalView}>
          <View style={[styles.flex, styles.flexRow]}>
            <Text style={styles.modalText}>Delete BodyTemperature {entity.id}?</Text>
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
    bodyTemperature: state.bodyTemperatures.bodyTemperature,
    fetching: state.bodyTemperatures.fetchingOne,
    deleting: state.bodyTemperatures.deleting,
    errorDeleting: state.bodyTemperatures.errorDeleting,
  };
};

const mapDispatchToProps = (dispatch) => {
  return {
    getBodyTemperature: (id) => dispatch(BodyTemperatureActions.bodyTemperatureRequest(id)),
    getAllBodyTemperatures: (options) => dispatch(BodyTemperatureActions.bodyTemperatureAllRequest(options)),
    deleteBodyTemperature: (id) => dispatch(BodyTemperatureActions.bodyTemperatureDeleteRequest(id)),
    resetBodyTemperatures: () => dispatch(BodyTemperatureActions.bodyTemperatureReset()),
  };
};

export default connect(mapStateToProps, mapDispatchToProps)(BodyTemperatureDeleteModal);
