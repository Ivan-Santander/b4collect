import React from 'react';
import { FlatList, Text, TouchableOpacity, View } from 'react-native';
import { connect } from 'react-redux';
import { useFocusEffect } from '@react-navigation/native';
import BloodPressureSummaryActions from './blood-pressure-summary.reducer';
import styles from './blood-pressure-summary-styles';
import AlertMessage from '../../../shared/components/alert-message/alert-message';

function BloodPressureSummaryScreen(props) {
  const [page, setPage] = React.useState(0);
  const [sort /*, setSort*/] = React.useState('id,asc');
  const [size /*, setSize*/] = React.useState(20);

  const { bloodPressureSummary, bloodPressureSummaryList, getAllBloodPressureSummaries, fetching } = props;

  useFocusEffect(
    React.useCallback(() => {
      console.debug('BloodPressureSummary entity changed and the list screen is now in focus, refresh');
      setPage(0);
      fetchBloodPressureSummaries();
      /* eslint-disable-next-line react-hooks/exhaustive-deps */
    }, [bloodPressureSummary, fetchBloodPressureSummaries]),
  );

  const renderRow = ({ item }) => {
    return (
      <TouchableOpacity onPress={() => props.navigation.navigate('BloodPressureSummaryDetail', { entityId: item.id })}>
        <View style={styles.listRow}>
          <Text style={styles.whiteLabel}>ID: {item.id}</Text>
          {/* <Text style={styles.label}>{item.description}</Text> */}
        </View>
      </TouchableOpacity>
    );
  };

  // Render a header

  // Show this when data is empty
  const renderEmpty = () => <AlertMessage title="No BloodPressureSummaries Found" show={!fetching} />;

  const keyExtractor = (item, index) => `${index}`;

  // How many items should be kept im memory as we scroll?
  const oneScreensWorth = 20;

  const fetchBloodPressureSummaries = React.useCallback(() => {
    getAllBloodPressureSummaries({ page: page - 1, sort, size });
  }, [getAllBloodPressureSummaries, page, sort, size]);

  const handleLoadMore = () => {
    if (page < props.links.next || props.links.next === undefined || fetching) {
      return;
    }
    setPage(page + 1);
    fetchBloodPressureSummaries();
  };
  return (
    <View style={styles.container} testID="bloodPressureSummaryScreen">
      <FlatList
        contentContainerStyle={styles.listContent}
        data={bloodPressureSummaryList}
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
    bloodPressureSummaryList: state.bloodPressureSummaries.bloodPressureSummaryList,
    bloodPressureSummary: state.bloodPressureSummaries.bloodPressureSummary,
    fetching: state.bloodPressureSummaries.fetchingAll,
    error: state.bloodPressureSummaries.errorAll,
    links: state.bloodPressureSummaries.links,
  };
};

const mapDispatchToProps = (dispatch) => {
  return {
    getAllBloodPressureSummaries: (options) => dispatch(BloodPressureSummaryActions.bloodPressureSummaryAllRequest(options)),
  };
};

export default connect(mapStateToProps, mapDispatchToProps)(BloodPressureSummaryScreen);
