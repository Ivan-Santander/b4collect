import React from 'react';
import { FlatList, Text, TouchableOpacity, View } from 'react-native';
import { connect } from 'react-redux';
import { useFocusEffect } from '@react-navigation/native';
import StepCountCadenceActions from './step-count-cadence.reducer';
import styles from './step-count-cadence-styles';
import AlertMessage from '../../../shared/components/alert-message/alert-message';

function StepCountCadenceScreen(props) {
  const [page, setPage] = React.useState(0);
  const [sort /*, setSort*/] = React.useState('id,asc');
  const [size /*, setSize*/] = React.useState(20);

  const { stepCountCadence, stepCountCadenceList, getAllStepCountCadences, fetching } = props;

  useFocusEffect(
    React.useCallback(() => {
      console.debug('StepCountCadence entity changed and the list screen is now in focus, refresh');
      setPage(0);
      fetchStepCountCadences();
      /* eslint-disable-next-line react-hooks/exhaustive-deps */
    }, [stepCountCadence, fetchStepCountCadences]),
  );

  const renderRow = ({ item }) => {
    return (
      <TouchableOpacity onPress={() => props.navigation.navigate('StepCountCadenceDetail', { entityId: item.id })}>
        <View style={styles.listRow}>
          <Text style={styles.whiteLabel}>ID: {item.id}</Text>
          {/* <Text style={styles.label}>{item.description}</Text> */}
        </View>
      </TouchableOpacity>
    );
  };

  // Render a header

  // Show this when data is empty
  const renderEmpty = () => <AlertMessage title="No StepCountCadences Found" show={!fetching} />;

  const keyExtractor = (item, index) => `${index}`;

  // How many items should be kept im memory as we scroll?
  const oneScreensWorth = 20;

  const fetchStepCountCadences = React.useCallback(() => {
    getAllStepCountCadences({ page: page - 1, sort, size });
  }, [getAllStepCountCadences, page, sort, size]);

  const handleLoadMore = () => {
    if (page < props.links.next || props.links.next === undefined || fetching) {
      return;
    }
    setPage(page + 1);
    fetchStepCountCadences();
  };
  return (
    <View style={styles.container} testID="stepCountCadenceScreen">
      <FlatList
        contentContainerStyle={styles.listContent}
        data={stepCountCadenceList}
        renderItem={renderRow}
        keyExtractor={keyExtractor}
        initialNumToRender={oneScreensWorth}
        onEndReached={handleLoadMore}
        ListEmptyComponent={renderEmpty}
      />
    </View>
  );
}

const mapStateToProps = (state) => {
  return {
    // ...redux state to props here
    stepCountCadenceList: state.stepCountCadences.stepCountCadenceList,
    stepCountCadence: state.stepCountCadences.stepCountCadence,
    fetching: state.stepCountCadences.fetchingAll,
    error: state.stepCountCadences.errorAll,
    links: state.stepCountCadences.links,
  };
};

const mapDispatchToProps = (dispatch) => {
  return {
    getAllStepCountCadences: (options) => dispatch(StepCountCadenceActions.stepCountCadenceAllRequest(options)),
  };
};

export default connect(mapStateToProps, mapDispatchToProps)(StepCountCadenceScreen);
