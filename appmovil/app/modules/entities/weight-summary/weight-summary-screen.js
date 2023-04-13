import React from 'react';
import { FlatList, Text, TouchableOpacity, View } from 'react-native';
import { connect } from 'react-redux';
import { useFocusEffect } from '@react-navigation/native';
import WeightSummaryActions from './weight-summary.reducer';
import styles from './weight-summary-styles';
import AlertMessage from '../../../shared/components/alert-message/alert-message';

function WeightSummaryScreen(props) {
  const [page, setPage] = React.useState(0);
  const [sort /*, setSort*/] = React.useState('id,asc');
  const [size /*, setSize*/] = React.useState(20);

  const { weightSummary, weightSummaryList, getAllWeightSummaries, fetching } = props;

  useFocusEffect(
    React.useCallback(() => {
      console.debug('WeightSummary entity changed and the list screen is now in focus, refresh');
      setPage(0);
      fetchWeightSummaries();
      /* eslint-disable-next-line react-hooks/exhaustive-deps */
    }, [weightSummary, fetchWeightSummaries]),
  );

  const renderRow = ({ item }) => {
    return (
      <TouchableOpacity onPress={() => props.navigation.navigate('WeightSummaryDetail', { entityId: item.id })}>
        <View style={styles.listRow}>
          <Text style={styles.whiteLabel}>ID: {item.id}</Text>
          {/* <Text style={styles.label}>{item.description}</Text> */}
        </View>
      </TouchableOpacity>
    );
  };

  // Render a header

  // Show this when data is empty
  const renderEmpty = () => <AlertMessage title="No WeightSummaries Found" show={!fetching} />;

  const keyExtractor = (item, index) => `${index}`;

  // How many items should be kept im memory as we scroll?
  const oneScreensWorth = 20;

  const fetchWeightSummaries = React.useCallback(() => {
    getAllWeightSummaries({ page: page - 1, sort, size });
  }, [getAllWeightSummaries, page, sort, size]);

  const handleLoadMore = () => {
    if (page < props.links.next || props.links.next === undefined || fetching) {
      return;
    }
    setPage(page + 1);
    fetchWeightSummaries();
  };
  return (
    <View style={styles.container} testID="weightSummaryScreen">
      <FlatList
        contentContainerStyle={styles.listContent}
        data={weightSummaryList}
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
    weightSummaryList: state.weightSummaries.weightSummaryList,
    weightSummary: state.weightSummaries.weightSummary,
    fetching: state.weightSummaries.fetchingAll,
    error: state.weightSummaries.errorAll,
    links: state.weightSummaries.links,
  };
};

const mapDispatchToProps = (dispatch) => {
  return {
    getAllWeightSummaries: (options) => dispatch(WeightSummaryActions.weightSummaryAllRequest(options)),
  };
};

export default connect(mapStateToProps, mapDispatchToProps)(WeightSummaryScreen);
