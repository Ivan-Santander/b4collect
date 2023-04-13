import React from 'react';
import { FlatList, Text, TouchableOpacity, View } from 'react-native';
import { connect } from 'react-redux';
import { useFocusEffect } from '@react-navigation/native';
import BodyFatPercentageSummaryActions from './body-fat-percentage-summary.reducer';
import styles from './body-fat-percentage-summary-styles';
import AlertMessage from '../../../shared/components/alert-message/alert-message';

function BodyFatPercentageSummaryScreen(props) {
  const [page, setPage] = React.useState(0);
  const [sort /*, setSort*/] = React.useState('id,asc');
  const [size /*, setSize*/] = React.useState(20);

  const { bodyFatPercentageSummary, bodyFatPercentageSummaryList, getAllBodyFatPercentageSummaries, fetching } = props;

  useFocusEffect(
    React.useCallback(() => {
      console.debug('BodyFatPercentageSummary entity changed and the list screen is now in focus, refresh');
      setPage(0);
      fetchBodyFatPercentageSummaries();
      /* eslint-disable-next-line react-hooks/exhaustive-deps */
    }, [bodyFatPercentageSummary, fetchBodyFatPercentageSummaries]),
  );

  const renderRow = ({ item }) => {
    return (
      <TouchableOpacity onPress={() => props.navigation.navigate('BodyFatPercentageSummaryDetail', { entityId: item.id })}>
        <View style={styles.listRow}>
          <Text style={styles.whiteLabel}>ID: {item.id}</Text>
          {/* <Text style={styles.label}>{item.description}</Text> */}
        </View>
      </TouchableOpacity>
    );
  };

  // Render a header

  // Show this when data is empty
  const renderEmpty = () => <AlertMessage title="No BodyFatPercentageSummaries Found" show={!fetching} />;

  const keyExtractor = (item, index) => `${index}`;

  // How many items should be kept im memory as we scroll?
  const oneScreensWorth = 20;

  const fetchBodyFatPercentageSummaries = React.useCallback(() => {
    getAllBodyFatPercentageSummaries({ page: page - 1, sort, size });
  }, [getAllBodyFatPercentageSummaries, page, sort, size]);

  const handleLoadMore = () => {
    if (page < props.links.next || props.links.next === undefined || fetching) {
      return;
    }
    setPage(page + 1);
    fetchBodyFatPercentageSummaries();
  };
  return (
    <View style={styles.container} testID="bodyFatPercentageSummaryScreen">
      <FlatList
        contentContainerStyle={styles.listContent}
        data={bodyFatPercentageSummaryList}
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
    bodyFatPercentageSummaryList: state.bodyFatPercentageSummaries.bodyFatPercentageSummaryList,
    bodyFatPercentageSummary: state.bodyFatPercentageSummaries.bodyFatPercentageSummary,
    fetching: state.bodyFatPercentageSummaries.fetchingAll,
    error: state.bodyFatPercentageSummaries.errorAll,
    links: state.bodyFatPercentageSummaries.links,
  };
};

const mapDispatchToProps = (dispatch) => {
  return {
    getAllBodyFatPercentageSummaries: (options) => dispatch(BodyFatPercentageSummaryActions.bodyFatPercentageSummaryAllRequest(options)),
  };
};

export default connect(mapStateToProps, mapDispatchToProps)(BodyFatPercentageSummaryScreen);
