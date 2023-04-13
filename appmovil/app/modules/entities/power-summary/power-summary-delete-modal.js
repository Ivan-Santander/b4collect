import React from 'react';
import { TouchableHighlight, Modal, Text, View } from 'react-native';
import { connect } from 'react-redux';

import PowerSummaryActions from './power-summary.reducer';

import styles from './power-summary-styles';

function PowerSummaryDeleteModal(props) {
  const { visible, setVisible, entity, navigation, testID } = props;

  const deleteEntity = () => {
    props.deletePowerSummary(entity.id);
    navigation.canGoBack() ? navigation.goBack() : navigation.navigate('PowerSummary');
  };
  return (
    <Modal animationType="slide" transparent={true} visible={visible}>
      <View testID={testID} style={styles.centeredView}>
        <View style={styles.modalView}>
          <View style={[styles.flex, styles.flexRow]}>
            <Text style={styles.modalText}>Delete PowerSummary {entity.id}?</Text>
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
    powerSummary: state.powerSummaries.powerSummary,
    fetching: state.powerSummaries.fetchingOne,
    deleting: state.powerSummaries.deleting,
    errorDeleting: state.powerSummaries.errorDeleting,
  };
};

const mapDispatchToProps = (dispatch) => {
  return {
    getPowerSummary: (id) => dispatch(PowerSummaryActions.powerSummaryRequest(id)),
    getAllPowerSummaries: (options) => dispatch(PowerSummaryActions.powerSummaryAllRequest(options)),
    deletePowerSummary: (id) => dispatch(PowerSummaryActions.powerSummaryDeleteRequest(id)),
    resetPowerSummaries: () => dispatch(PowerSummaryActions.powerSummaryReset()),
  };
};

export default connect(mapStateToProps, mapDispatchToProps)(PowerSummaryDeleteModal);
