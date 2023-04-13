import React from 'react';
import { FlatList, Text, TouchableOpacity, View } from 'react-native';
import { connect } from 'react-redux';
import { useFocusEffect } from '@react-navigation/native';
import BloodGlucoseSummaryActions from './blood-glucose-summary.reducer';
import styles from './blood-glucose-summary-styles';
import AlertMessage from '../../../shared/components/alert-message/alert-message';

function BloodGlucoseSummaryScreen(props) {
  const [page, setPage] = React.useState(0);
  const [sort /*, setSort*/] = React.useState('id,asc');
  const [size /*, setSize*/] = React.useState(20);

  const { bloodGlucoseSummary, bloodGlucoseSummaryList, getAllBloodGlucoseSummaries, fetching } = props;

  useFocusEffect(
    React.useCallback(() => {
      console.debug('BloodGlucoseSummary entity changed and the list screen is now in focus, refresh');
      setPage(0);
      fetchBloodGlucoseSummaries();
      /* eslint-disable-next-line react-hooks/exhaustive-deps */
    }, [bloodGlucoseSummary, fetchBloodGlucoseSummaries]),
  );

  const renderRow = ({ item }) => {
    return (
      <TouchableOpacity onPress={() => props.navigation.navigate('BloodGlucoseSummaryDetail', { entityId: item.id })}>
        <View style={styles.listRow}>
          <Text style={styles.whiteLabel}>ID: {item.id}</Text>
          {/* <Text style={styles.label}>{item.description}</Text> */}
        </View>
      </TouchableOpacity>
    );
  };

  // Render a header

  // Show this when data is empty
  const renderEmpty = () => <AlertMessage title="No BloodGlucoseSummaries Found" show={!fetching} />;

  const keyExtractor = (item, index) => `${index}`;

  // How many items should be kept im memory as we scroll?
  const oneScreensWorth = 20;

  const fetchBloodGlucoseSummaries = React.useCallback(() => {
    getAllBloodGlucoseSummaries({ page: page - 1, sort, size });
  }, [getAllBloodGlucoseSummaries, page, sort, size]);

  const handleLoadMore = () => {
    if (page < props.links.next || props.links.next === undefined || fetching) {
      return;
    }
    setPage(page + 1);
    fetchBloodGlucoseSummaries();
  };
  return (
    <View style={styles.container} testID="bloodGlucoseSummaryScreen">
      <FlatList
        contentContainerStyle={styles.listContent}
        data={bloodGlucoseSummaryList}
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
    bloodGlucoseSummaryList: state.bloodGlucoseSummaries.bloodGlucoseSummaryList,
    bloodGlucoseSummary: state.bloodGlucoseSummaries.bloodGlucoseSummary,
    fetching: state.bloodGlucoseSummaries.fetchingAll,
    error: state.bloodGlucoseSummaries.errorAll,
    links: state.bloodGlucoseSummaries.links,
  };
};

const mapDispatchToProps = (dispatch) => {
  return {
    getAllBloodGlucoseSummaries: (options) => dispatch(BloodGlucoseSummaryActions.bloodGlucoseSummaryAllRequest(options)),
  };
};

export default connect(mapStateToProps, mapDispatchToProps)(BloodGlucoseSummaryScreen);
