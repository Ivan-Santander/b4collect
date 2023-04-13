import React from 'react';
import { FlatList, Text, TouchableOpacity, View } from 'react-native';
import { connect } from 'react-redux';
import { useFocusEffect } from '@react-navigation/native';
import StepCountDeltaActions from './step-count-delta.reducer';
import styles from './step-count-delta-styles';
import AlertMessage from '../../../shared/components/alert-message/alert-message';

function StepCountDeltaScreen(props) {
  const [page, setPage] = React.useState(0);
  const [sort /*, setSort*/] = React.useState('id,asc');
  const [size /*, setSize*/] = React.useState(20);

  const { stepCountDelta, stepCountDeltaList, getAllStepCountDeltas, fetching } = props;

  useFocusEffect(
    React.useCallback(() => {
      console.debug('StepCountDelta entity changed and the list screen is now in focus, refresh');
      setPage(0);
      fetchStepCountDeltas();
      /* eslint-disable-next-line react-hooks/exhaustive-deps */
    }, [stepCountDelta, fetchStepCountDeltas]),
  );

  const renderRow = ({ item }) => {
    return (
      <TouchableOpacity onPress={() => props.navigation.navigate('StepCountDeltaDetail', { entityId: item.id })}>
        <View style={styles.listRow}>
          <Text style={styles.whiteLabel}>ID: {item.id}</Text>
          {/* <Text style={styles.label}>{item.description}</Text> */}
        </View>
      </TouchableOpacity>
    );
  };

  // Render a header

  // Show this when data is empty
  const renderEmpty = () => <AlertMessage title="No StepCountDeltas Found" show={!fetching} />;

  const keyExtractor = (item, index) => `${index}`;

  // How many items should be kept im memory as we scroll?
  const oneScreensWorth = 20;

  const fetchStepCountDeltas = React.useCallback(() => {
    getAllStepCountDeltas({ page: page - 1, sort, size });
  }, [getAllStepCountDeltas, page, sort, size]);

  const handleLoadMore = () => {
    if (page < props.links.next || props.links.next === undefined || fetching) {
      return;
    }
    setPage(page + 1);
    fetchStepCountDeltas();
  };
  return (
    <View style={styles.container} testID="stepCountDeltaScreen">
      <FlatList
        contentContainerStyle={styles.listContent}
        data={stepCountDeltaList}
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
    stepCountDeltaList: state.stepCountDeltas.stepCountDeltaList,
    stepCountDelta: state.stepCountDeltas.stepCountDelta,
    fetching: state.stepCountDeltas.fetchingAll,
    error: state.stepCountDeltas.errorAll,
    links: state.stepCountDeltas.links,
  };
};

const mapDispatchToProps = (dispatch) => {
  return {
    getAllStepCountDeltas: (options) => dispatch(StepCountDeltaActions.stepCountDeltaAllRequest(options)),
  };
};

export default connect(mapStateToProps, mapDispatchToProps)(StepCountDeltaScreen);
