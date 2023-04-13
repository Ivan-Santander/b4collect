import React from 'react';
import { TouchableHighlight, Modal, Text, View } from 'react-native';
import { connect } from 'react-redux';

import TemperatureSummaryActions from './temperature-summary.reducer';

import styles from './temperature-summary-styles';

function TemperatureSummaryDeleteModal(props) {
  const { visible, setVisible, entity, navigation, testID } = props;

  const deleteEntity = () => {
    props.deleteTemperatureSummary(entity.id);
    navigation.canGoBack() ? navigation.goBack() : navigation.navigate('TemperatureSummary');
  };
  return (
    <Modal animationType="slide" transparent={true} visible={visible}>
      <View testID={testID} style={styles.centeredView}>
        <View style={styles.modalView}>
          <View style={[styles.flex, styles.flexRow]}>
            <Text style={styles.modalText}>Delete TemperatureSummary {entity.id}?</Text>
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
    temperatureSummary: state.temperatureSummaries.temperatureSummary,
    fetching: state.temperatureSummaries.fetchingOne,
    deleting: state.temperatureSummaries.deleting,
    errorDeleting: state.temperatureSummaries.errorDeleting,
  };
};

const mapDispatchToProps = (dispatch) => {
  return {
    getTemperatureSummary: (id) => dispatch(TemperatureSummaryActions.temperatureSummaryRequest(id)),
    getAllTemperatureSummaries: (options) => dispatch(TemperatureSummaryActions.temperatureSummaryAllRequest(options)),
    deleteTemperatureSummary: (id) => dispatch(TemperatureSummaryActions.temperatureSummaryDeleteRequest(id)),
    resetTemperatureSummaries: () => dispatch(TemperatureSummaryActions.temperatureSummaryReset()),
  };
};

export default connect(mapStateToProps, mapDispatchToProps)(TemperatureSummaryDeleteModal);
