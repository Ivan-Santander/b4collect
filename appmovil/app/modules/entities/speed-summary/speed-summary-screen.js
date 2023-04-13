import React from 'react';
import { FlatList, Text, TouchableOpacity, View } from 'react-native';
import { connect } from 'react-redux';
import { useFocusEffect } from '@react-navigation/native';
import SpeedSummaryActions from './speed-summary.reducer';
import styles from './speed-summary-styles';
import AlertMessage from '../../../shared/components/alert-message/alert-message';

function SpeedSummaryScreen(props) {
  const [page, setPage] = React.useState(0);
  const [sort /*, setSort*/] = React.useState('id,asc');
  const [size /*, setSize*/] = React.useState(20);

  const { speedSummary, speedSummaryList, getAllSpeedSummaries, fetching } = props;

  useFocusEffect(
    React.useCallback(() => {
      console.debug('SpeedSummary entity changed and the list screen is now in focus, refresh');
      setPage(0);
      fetchSpeedSummaries();
      /* eslint-disable-next-line react-hooks/exhaustive-deps */
    }, [speedSummary, fetchSpeedSummaries]),
  );

  const renderRow = ({ item }) => {
    return (
      <TouchableOpacity onPress={() => props.navigation.navigate('SpeedSummaryDetail', { entityId: item.id })}>
        <View style={styles.listRow}>
          <Text style={styles.whiteLabel}>ID: {item.id}</Text>
          {/* <Text style={styles.label}>{item.description}</Text> */}
        </View>
      </TouchableOpacity>
    );
  };

  // Render a header

  // Show this when data is empty
  const renderEmpty = () => <AlertMessage title="No SpeedSummaries Found" show={!fetching} />;

  const keyExtractor = (item, index) => `${index}`;

  // How many items should be kept im memory as we scroll?
  const oneScreensWorth = 20;

  const fetchSpeedSummaries = React.useCallback(() => {
    getAllSpeedSummaries({ page: page - 1, sort, size });
  }, [getAllSpeedSummaries, page, sort, size]);

  const handleLoadMore = () => {
    if (page < props.links.next || props.links.next === undefined || fetching) {
      return;
    }
    setPage(page + 1);
    fetchSpeedSummaries();
  };
  return (
    <View style={styles.container} testID="speedSummaryScreen">
      <FlatList
        contentContainerStyle={styles.listContent}
        data={speedSummaryList}
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
    speedSummaryList: state.speedSummaries.speedSummaryList,
    speedSummary: state.speedSummaries.speedSummary,
    fetching: state.speedSummaries.fetchingAll,
    error: state.speedSummaries.errorAll,
    links: state.speedSummaries.links,
  };
};

const mapDispatchToProps = (dispatch) => {
  return {
    getAllSpeedSummaries: (options) => dispatch(SpeedSummaryActions.speedSummaryAllRequest(options)),
  };
};

export default connect(mapStateToProps, mapDispatchToProps)(SpeedSummaryScreen);
