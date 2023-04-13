import React from 'react';
import { FlatList, Text, TouchableOpacity, View } from 'react-native';
import { connect } from 'react-redux';
import { useFocusEffect } from '@react-navigation/native';
import HeightSummaryActions from './height-summary.reducer';
import styles from './height-summary-styles';
import AlertMessage from '../../../shared/components/alert-message/alert-message';

function HeightSummaryScreen(props) {
  const [page, setPage] = React.useState(0);
  const [sort /*, setSort*/] = React.useState('id,asc');
  const [size /*, setSize*/] = React.useState(20);

  const { heightSummary, heightSummaryList, getAllHeightSummaries, fetching } = props;

  useFocusEffect(
    React.useCallback(() => {
      console.debug('HeightSummary entity changed and the list screen is now in focus, refresh');
      setPage(0);
      fetchHeightSummaries();
      /* eslint-disable-next-line react-hooks/exhaustive-deps */
    }, [heightSummary, fetchHeightSummaries]),
  );

  const renderRow = ({ item }) => {
    return (
      <TouchableOpacity onPress={() => props.navigation.navigate('HeightSummaryDetail', { entityId: item.id })}>
        <View style={styles.listRow}>
          <Text style={styles.whiteLabel}>ID: {item.id}</Text>
          {/* <Text style={styles.label}>{item.description}</Text> */}
        </View>
      </TouchableOpacity>
    );
  };

  // Render a header

  // Show this when data is empty
  const renderEmpty = () => <AlertMessage title="No HeightSummaries Found" show={!fetching} />;

  const keyExtractor = (item, index) => `${index}`;

  // How many items should be kept im memory as we scroll?
  const oneScreensWorth = 20;

  const fetchHeightSummaries = React.useCallback(() => {
    getAllHeightSummaries({ page: page - 1, sort, size });
  }, [getAllHeightSummaries, page, sort, size]);

  const handleLoadMore = () => {
    if (page < props.links.next || props.links.next === undefined || fetching) {
      return;
    }
    setPage(page + 1);
    fetchHeightSummaries();
  };
  return (
    <View style={styles.container} testID="heightSummaryScreen">
      <FlatList
        contentContainerStyle={styles.listContent}
        data={heightSummaryList}
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
    heightSummaryList: state.heightSummaries.heightSummaryList,
    heightSummary: state.heightSummaries.heightSummary,
    fetching: state.heightSummaries.fetchingAll,
    error: state.heightSummaries.errorAll,
    links: state.heightSummaries.links,
  };
};

const mapDispatchToProps = (dispatch) => {
  return {
    getAllHeightSummaries: (options) => dispatch(HeightSummaryActions.heightSummaryAllRequest(options)),
  };
};

export default connect(mapStateToProps, mapDispatchToProps)(HeightSummaryScreen);
