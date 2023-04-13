import React from 'react';
import { FlatList, Text, TouchableOpacity, View } from 'react-native';
import { connect } from 'react-redux';
import { useFocusEffect } from '@react-navigation/native';
import HeartRateSummaryActions from './heart-rate-summary.reducer';
import styles from './heart-rate-summary-styles';
import AlertMessage from '../../../shared/components/alert-message/alert-message';

function HeartRateSummaryScreen(props) {
  const [page, setPage] = React.useState(0);
  const [sort /*, setSort*/] = React.useState('id,asc');
  const [size /*, setSize*/] = React.useState(20);

  const { heartRateSummary, heartRateSummaryList, getAllHeartRateSummaries, fetching } = props;

  useFocusEffect(
    React.useCallback(() => {
      console.debug('HeartRateSummary entity changed and the list screen is now in focus, refresh');
      setPage(0);
      fetchHeartRateSummaries();
      /* eslint-disable-next-line react-hooks/exhaustive-deps */
    }, [heartRateSummary, fetchHeartRateSummaries]),
  );

  const renderRow = ({ item }) => {
    return (
      <TouchableOpacity onPress={() => props.navigation.navigate('HeartRateSummaryDetail', { entityId: item.id })}>
        <View style={styles.listRow}>
          <Text style={styles.whiteLabel}>ID: {item.id}</Text>
          {/* <Text style={styles.label}>{item.description}</Text> */}
        </View>
      </TouchableOpacity>
    );
  };

  // Render a header

  // Show this when data is empty
  const renderEmpty = () => <AlertMessage title="No HeartRateSummaries Found" show={!fetching} />;

  const keyExtractor = (item, index) => `${index}`;

  // How many items should be kept im memory as we scroll?
  const oneScreensWorth = 20;

  const fetchHeartRateSummaries = React.useCallback(() => {
    getAllHeartRateSummaries({ page: page - 1, sort, size });
  }, [getAllHeartRateSummaries, page, sort, size]);

  const handleLoadMore = () => {
    if (page < props.links.next || props.links.next === undefined || fetching) {
      return;
    }
    setPage(page + 1);
    fetchHeartRateSummaries();
  };
  return (
    <View style={styles.container} testID="heartRateSummaryScreen">
      <FlatList
        contentContainerStyle={styles.listContent}
        data={heartRateSummaryList}
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
    heartRateSummaryList: state.heartRateSummaries.heartRateSummaryList,
    heartRateSummary: state.heartRateSummaries.heartRateSummary,
    fetching: state.heartRateSummaries.fetchingAll,
    error: state.heartRateSummaries.errorAll,
    links: state.heartRateSummaries.links,
  };
};

const mapDispatchToProps = (dispatch) => {
  return {
    getAllHeartRateSummaries: (options) => dispatch(HeartRateSummaryActions.heartRateSummaryAllRequest(options)),
  };
};

export default connect(mapStateToProps, mapDispatchToProps)(HeartRateSummaryScreen);
