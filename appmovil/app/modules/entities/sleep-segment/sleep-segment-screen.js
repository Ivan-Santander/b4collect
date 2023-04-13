import React from 'react';
import { FlatList, Text, TouchableOpacity, View } from 'react-native';
import { connect } from 'react-redux';
import { useFocusEffect } from '@react-navigation/native';
import SleepSegmentActions from './sleep-segment.reducer';
import styles from './sleep-segment-styles';
import AlertMessage from '../../../shared/components/alert-message/alert-message';

function SleepSegmentScreen(props) {
  const [page, setPage] = React.useState(0);
  const [sort /*, setSort*/] = React.useState('id,asc');
  const [size /*, setSize*/] = React.useState(20);

  const { sleepSegment, sleepSegmentList, getAllSleepSegments, fetching } = props;

  useFocusEffect(
    React.useCallback(() => {
      console.debug('SleepSegment entity changed and the list screen is now in focus, refresh');
      setPage(0);
      fetchSleepSegments();
      /* eslint-disable-next-line react-hooks/exhaustive-deps */
    }, [sleepSegment, fetchSleepSegments]),
  );

  const renderRow = ({ item }) => {
    return (
      <TouchableOpacity onPress={() => props.navigation.navigate('SleepSegmentDetail', { entityId: item.id })}>
        <View style={styles.listRow}>
          <Text style={styles.whiteLabel}>ID: {item.id}</Text>
          {/* <Text style={styles.label}>{item.description}</Text> */}
        </View>
      </TouchableOpacity>
    );
  };

  // Render a header

  // Show this when data is empty
  const renderEmpty = () => <AlertMessage title="No SleepSegments Found" show={!fetching} />;

  const keyExtractor = (item, index) => `${index}`;

  // How many items should be kept im memory as we scroll?
  const oneScreensWorth = 20;

  const fetchSleepSegments = React.useCallback(() => {
    getAllSleepSegments({ page: page - 1, sort, size });
  }, [getAllSleepSegments, page, sort, size]);

  const handleLoadMore = () => {
    if (page < props.links.next || props.links.next === undefined || fetching) {
      return;
    }
    setPage(page + 1);
    fetchSleepSegments();
  };
  return (
    <View style={styles.container} testID="sleepSegmentScreen">
      <FlatList
        contentContainerStyle={styles.listContent}
        data={sleepSegmentList}
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
    sleepSegmentList: state.sleepSegments.sleepSegmentList,
    sleepSegment: state.sleepSegments.sleepSegment,
    fetching: state.sleepSegments.fetchingAll,
    error: state.sleepSegments.errorAll,
    links: state.sleepSegments.links,
  };
};

const mapDispatchToProps = (dispatch) => {
  return {
    getAllSleepSegments: (options) => dispatch(SleepSegmentActions.sleepSegmentAllRequest(options)),
  };
};

export default connect(mapStateToProps, mapDispatchToProps)(SleepSegmentScreen);
