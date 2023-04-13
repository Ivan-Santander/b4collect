import React from 'react';
import { FlatList, Text, TouchableOpacity, View } from 'react-native';
import { connect } from 'react-redux';
import { useFocusEffect } from '@react-navigation/native';
import ActivitySummaryActions from './activity-summary.reducer';
import styles from './activity-summary-styles';
import AlertMessage from '../../../shared/components/alert-message/alert-message';

function ActivitySummaryScreen(props) {
  const [page, setPage] = React.useState(0);
  const [sort /*, setSort*/] = React.useState('id,asc');
  const [size /*, setSize*/] = React.useState(20);

  const { activitySummary, activitySummaryList, getAllActivitySummaries, fetching } = props;

  useFocusEffect(
    React.useCallback(() => {
      console.debug('ActivitySummary entity changed and the list screen is now in focus, refresh');
      setPage(0);
      fetchActivitySummaries();
      /* eslint-disable-next-line react-hooks/exhaustive-deps */
    }, [activitySummary, fetchActivitySummaries]),
  );

  const renderRow = ({ item }) => {
    return (
      <TouchableOpacity onPress={() => props.navigation.navigate('ActivitySummaryDetail', { entityId: item.id })}>
        <View style={styles.listRow}>
          <Text style={styles.whiteLabel}>ID: {item.id}</Text>
          {/* <Text style={styles.label}>{item.description}</Text> */}
        </View>
      </TouchableOpacity>
    );
  };

  // Render a header

  // Show this when data is empty
  const renderEmpty = () => <AlertMessage title="No ActivitySummaries Found" show={!fetching} />;

  const keyExtractor = (item, index) => `${index}`;

  // How many items should be kept im memory as we scroll?
  const oneScreensWorth = 20;

  const fetchActivitySummaries = React.useCallback(() => {
    getAllActivitySummaries({ page: page - 1, sort, size });
  }, [getAllActivitySummaries, page, sort, size]);

  const handleLoadMore = () => {
    if (page < props.links.next || props.links.next === undefined || fetching) {
      return;
    }
    setPage(page + 1);
    fetchActivitySummaries();
  };
  return (
    <View style={styles.container} testID="activitySummaryScreen">
      <FlatList
        contentContainerStyle={styles.listContent}
        data={activitySummaryList}
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
    activitySummaryList: state.activitySummaries.activitySummaryList,
    activitySummary: state.activitySummaries.activitySummary,
    fetching: state.activitySummaries.fetchingAll,
    error: state.activitySummaries.errorAll,
    links: state.activitySummaries.links,
  };
};

const mapDispatchToProps = (dispatch) => {
  return {
    getAllActivitySummaries: (options) => dispatch(ActivitySummaryActions.activitySummaryAllRequest(options)),
  };
};

export default connect(mapStateToProps, mapDispatchToProps)(ActivitySummaryScreen);
