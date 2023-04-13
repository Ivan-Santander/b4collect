import React from 'react';
import { FlatList, Text, TouchableOpacity, View } from 'react-native';
import { connect } from 'react-redux';
import { useFocusEffect } from '@react-navigation/native';
import PowerSummaryActions from './power-summary.reducer';
import styles from './power-summary-styles';
import AlertMessage from '../../../shared/components/alert-message/alert-message';

function PowerSummaryScreen(props) {
  const [page, setPage] = React.useState(0);
  const [sort /*, setSort*/] = React.useState('id,asc');
  const [size /*, setSize*/] = React.useState(20);

  const { powerSummary, powerSummaryList, getAllPowerSummaries, fetching } = props;

  useFocusEffect(
    React.useCallback(() => {
      console.debug('PowerSummary entity changed and the list screen is now in focus, refresh');
      setPage(0);
      fetchPowerSummaries();
      /* eslint-disable-next-line react-hooks/exhaustive-deps */
    }, [powerSummary, fetchPowerSummaries]),
  );

  const renderRow = ({ item }) => {
    return (
      <TouchableOpacity onPress={() => props.navigation.navigate('PowerSummaryDetail', { entityId: item.id })}>
        <View style={styles.listRow}>
          <Text style={styles.whiteLabel}>ID: {item.id}</Text>
          {/* <Text style={styles.label}>{item.description}</Text> */}
        </View>
      </TouchableOpacity>
    );
  };

  // Render a header

  // Show this when data is empty
  const renderEmpty = () => <AlertMessage title="No PowerSummaries Found" show={!fetching} />;

  const keyExtractor = (item, index) => `${index}`;

  // How many items should be kept im memory as we scroll?
  const oneScreensWorth = 20;

  const fetchPowerSummaries = React.useCallback(() => {
    getAllPowerSummaries({ page: page - 1, sort, size });
  }, [getAllPowerSummaries, page, sort, size]);

  const handleLoadMore = () => {
    if (page < props.links.next || props.links.next === undefined || fetching) {
      return;
    }
    setPage(page + 1);
    fetchPowerSummaries();
  };
  return (
    <View style={styles.container} testID="powerSummaryScreen">
      <FlatList
        contentContainerStyle={styles.listContent}
        data={powerSummaryList}
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
    powerSummaryList: state.powerSummaries.powerSummaryList,
    powerSummary: state.powerSummaries.powerSummary,
    fetching: state.powerSummaries.fetchingAll,
    error: state.powerSummaries.errorAll,
    links: state.powerSummaries.links,
  };
};

const mapDispatchToProps = (dispatch) => {
  return {
    getAllPowerSummaries: (options) => dispatch(PowerSummaryActions.powerSummaryAllRequest(options)),
  };
};

export default connect(mapStateToProps, mapDispatchToProps)(PowerSummaryScreen);
