import React from 'react';
import { TouchableHighlight, Modal, Text, View } from 'react-native';
import { connect } from 'react-redux';

import OxygenSaturationSummaryActions from './oxygen-saturation-summary.reducer';

import styles from './oxygen-saturation-summary-styles';

function OxygenSaturationSummaryDeleteModal(props) {
  const { visible, setVisible, entity, navigation, testID } = props;

  const deleteEntity = () => {
    props.deleteOxygenSaturationSummary(entity.id);
    navigation.canGoBack() ? navigation.goBack() : navigation.navigate('OxygenSaturationSummary');
  };
  return (
    <Modal animationType="slide" transparent={true} visible={visible}>
      <View testID={testID} style={styles.centeredView}>
        <View style={styles.modalView}>
          <View style={[styles.flex, styles.flexRow]}>
            <Text style={styles.modalText}>Delete OxygenSaturationSummary {entity.id}?</Text>
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
    oxygenSaturationSummary: state.oxygenSaturationSummaries.oxygenSaturationSummary,
    fetching: state.oxygenSaturationSummaries.fetchingOne,
    deleting: state.oxygenSaturationSummaries.deleting,
    errorDeleting: state.oxygenSaturationSummaries.errorDeleting,
  };
};

const mapDispatchToProps = (dispatch) => {
  return {
    getOxygenSaturationSummary: (id) => dispatch(OxygenSaturationSummaryActions.oxygenSaturationSummaryRequest(id)),
    getAllOxygenSaturationSummaries: (options) => dispatch(OxygenSaturationSummaryActions.oxygenSaturationSummaryAllRequest(options)),
    deleteOxygenSaturationSummary: (id) => dispatch(OxygenSaturationSummaryActions.oxygenSaturationSummaryDeleteRequest(id)),
    resetOxygenSaturationSummaries: () => dispatch(OxygenSaturationSummaryActions.oxygenSaturationSummaryReset()),
  };
};

export default connect(mapStateToProps, mapDispatchToProps)(OxygenSaturationSummaryDeleteModal);
