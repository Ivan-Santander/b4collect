import React from 'react';
import { FlatList, Text, TouchableOpacity, View } from 'react-native';
import { connect } from 'react-redux';
import { useFocusEffect } from '@react-navigation/native';
import OxygenSaturationSummaryActions from './oxygen-saturation-summary.reducer';
import styles from './oxygen-saturation-summary-styles';
import AlertMessage from '../../../shared/components/alert-message/alert-message';

function OxygenSaturationSummaryScreen(props) {
  const [page, setPage] = React.useState(0);
  const [sort /*, setSort*/] = React.useState('id,asc');
  const [size /*, setSize*/] = React.useState(20);

  const { oxygenSaturationSummary, oxygenSaturationSummaryList, getAllOxygenSaturationSummaries, fetching } = props;

  useFocusEffect(
    React.useCallback(() => {
      console.debug('OxygenSaturationSummary entity changed and the list screen is now in focus, refresh');
      setPage(0);
      fetchOxygenSaturationSummaries();
      /* eslint-disable-next-line react-hooks/exhaustive-deps */
    }, [oxygenSaturationSummary, fetchOxygenSaturationSummaries]),
  );

  const renderRow = ({ item }) => {
    return (
      <TouchableOpacity onPress={() => props.navigation.navigate('OxygenSaturationSummaryDetail', { entityId: item.id })}>
        <View style={styles.listRow}>
          <Text style={styles.whiteLabel}>ID: {item.id}</Text>
          {/* <Text style={styles.label}>{item.description}</Text> */}
        </View>
      </TouchableOpacity>
    );
  };

  // Render a header

  // Show this when data is empty
  const renderEmpty = () => <AlertMessage title="No OxygenSaturationSummaries Found" show={!fetching} />;

  const keyExtractor = (item, index) => `${index}`;

  // How many items should be kept im memory as we scroll?
  const oneScreensWorth = 20;

  const fetchOxygenSaturationSummaries = React.useCallback(() => {
    getAllOxygenSaturationSummaries({ page: page - 1, sort, size });
  }, [getAllOxygenSaturationSummaries, page, sort, size]);

  const handleLoadMore = () => {
    if (page < props.links.next || props.links.next === undefined || fetching) {
      return;
    }
    setPage(page + 1);
    fetchOxygenSaturationSummaries();
  };
  return (
    <View style={styles.container} testID="oxygenSaturationSummaryScreen">
      <FlatList
        contentContainerStyle={styles.listContent}
        data={oxygenSaturationSummaryList}
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
    oxygenSaturationSummaryList: state.oxygenSaturationSummaries.oxygenSaturationSummaryList,
    oxygenSaturationSummary: state.oxygenSaturationSummaries.oxygenSaturationSummary,
    fetching: state.oxygenSaturationSummaries.fetchingAll,
    error: state.oxygenSaturationSummaries.errorAll,
    links: state.oxygenSaturationSummaries.links,
  };
};

const mapDispatchToProps = (dispatch) => {
  return {
    getAllOxygenSaturationSummaries: (options) => dispatch(OxygenSaturationSummaryActions.oxygenSaturationSummaryAllRequest(options)),
  };
};

export default connect(mapStateToProps, mapDispatchToProps)(OxygenSaturationSummaryScreen);
