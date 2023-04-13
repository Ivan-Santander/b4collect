import React from 'react';
import { FlatList, Text, TouchableOpacity, View } from 'react-native';
import { connect } from 'react-redux';
import { useFocusEffect } from '@react-navigation/native';
import TemperatureSummaryActions from './temperature-summary.reducer';
import styles from './temperature-summary-styles';
import AlertMessage from '../../../shared/components/alert-message/alert-message';

function TemperatureSummaryScreen(props) {
  const [page, setPage] = React.useState(0);
  const [sort /*, setSort*/] = React.useState('id,asc');
  const [size /*, setSize*/] = React.useState(20);

  const { temperatureSummary, temperatureSummaryList, getAllTemperatureSummaries, fetching } = props;

  useFocusEffect(
    React.useCallback(() => {
      console.debug('TemperatureSummary entity changed and the list screen is now in focus, refresh');
      setPage(0);
      fetchTemperatureSummaries();
      /* eslint-disable-next-line react-hooks/exhaustive-deps */
    }, [temperatureSummary, fetchTemperatureSummaries]),
  );

  const renderRow = ({ item }) => {
    return (
      <TouchableOpacity onPress={() => props.navigation.navigate('TemperatureSummaryDetail', { entityId: item.id })}>
        <View style={styles.listRow}>
          <Text style={styles.whiteLabel}>ID: {item.id}</Text>
          {/* <Text style={styles.label}>{item.description}</Text> */}
        </View>
      </TouchableOpacity>
    );
  };

  // Render a header

  // Show this when data is empty
  const renderEmpty = () => <AlertMessage title="No TemperatureSummaries Found" show={!fetching} />;

  const keyExtractor = (item, index) => `${index}`;

  // How many items should be kept im memory as we scroll?
  const oneScreensWorth = 20;

  const fetchTemperatureSummaries = React.useCallback(() => {
    getAllTemperatureSummaries({ page: page - 1, sort, size });
  }, [getAllTemperatureSummaries, page, sort, size]);

  const handleLoadMore = () => {
    if (page < props.links.next || props.links.next === undefined || fetching) {
      return;
    }
    setPage(page + 1);
    fetchTemperatureSummaries();
  };
  return (
    <View style={styles.container} testID="temperatureSummaryScreen">
      <FlatList
        contentContainerStyle={styles.listContent}
        data={temperatureSummaryList}
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
    temperatureSummaryList: state.temperatureSummaries.temperatureSummaryList,
    temperatureSummary: state.temperatureSummaries.temperatureSummary,
    fetching: state.temperatureSummaries.fetchingAll,
    error: state.temperatureSummaries.errorAll,
    links: state.temperatureSummaries.links,
  };
};

const mapDispatchToProps = (dispatch) => {
  return {
    getAllTemperatureSummaries: (options) => dispatch(TemperatureSummaryActions.temperatureSummaryAllRequest(options)),
  };
};

export default connect(mapStateToProps, mapDispatchToProps)(TemperatureSummaryScreen);
