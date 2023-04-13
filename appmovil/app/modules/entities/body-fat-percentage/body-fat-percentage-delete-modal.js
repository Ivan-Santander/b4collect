import React from 'react';
import { TouchableHighlight, Modal, Text, View } from 'react-native';
import { connect } from 'react-redux';

import BodyFatPercentageActions from './body-fat-percentage.reducer';

import styles from './body-fat-percentage-styles';

function BodyFatPercentageDeleteModal(props) {
  const { visible, setVisible, entity, navigation, testID } = props;

  const deleteEntity = () => {
    props.deleteBodyFatPercentage(entity.id);
    navigation.canGoBack() ? navigation.goBack() : navigation.navigate('BodyFatPercentage');
  };
  return (
    <Modal animationType="slide" transparent={true} visible={visible}>
      <View testID={testID} style={styles.centeredView}>
        <View style={styles.modalView}>
          <View style={[styles.flex, styles.flexRow]}>
            <Text style={styles.modalText}>Delete BodyFatPercentage {entity.id}?</Text>
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
    bodyFatPercentage: state.bodyFatPercentages.bodyFatPercentage,
    fetching: state.bodyFatPercentages.fetchingOne,
    deleting: state.bodyFatPercentages.deleting,
    errorDeleting: state.bodyFatPercentages.errorDeleting,
  };
};

const mapDispatchToProps = (dispatch) => {
  return {
    getBodyFatPercentage: (id) => dispatch(BodyFatPercentageActions.bodyFatPercentageRequest(id)),
    getAllBodyFatPercentages: (options) => dispatch(BodyFatPercentageActions.bodyFatPercentageAllRequest(options)),
    deleteBodyFatPercentage: (id) => dispatch(BodyFatPercentageActions.bodyFatPercentageDeleteRequest(id)),
    resetBodyFatPercentages: () => dispatch(BodyFatPercentageActions.bodyFatPercentageReset()),
  };
};

export default connect(mapStateToProps, mapDispatchToProps)(BodyFatPercentageDeleteModal);
