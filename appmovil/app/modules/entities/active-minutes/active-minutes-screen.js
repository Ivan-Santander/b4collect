import React from 'react';
import { FlatList, Text, TouchableOpacity, View } from 'react-native';
import { connect } from 'react-redux';
import { useFocusEffect } from '@react-navigation/native';
import ActiveMinutesActions from './active-minutes.reducer';
import styles from './active-minutes-styles';
import AlertMessage from '../../../shared/components/alert-message/alert-message';

function ActiveMinutesScreen(props) {
  const [page, setPage] = React.useState(0);
  const [sort /*, setSort*/] = React.useState('id,asc');
  const [size /*, setSize*/] = React.useState(20);

  const { activeMinutes, activeMinutesList, getAllActiveMinutes, fetching } = props;

  useFocusEffect(
    React.useCallback(() => {
      console.debug('ActiveMinutes entity changed and the list screen is now in focus, refresh');
      setPage(0);
      fetchActiveMinutes();
      /* eslint-disable-next-line react-hooks/exhaustive-deps */
    }, [activeMinutes, fetchActiveMinutes]),
  );

  const renderRow = ({ item }) => {
    return (
      <TouchableOpacity onPress={() => props.navigation.navigate('ActiveMinutesDetail', { entityId: item.id })}>
        <View style={styles.listRow}>
          <Text style={styles.whiteLabel}>ID: {item.id}</Text>
          {/* <Text style={styles.label}>{item.description}</Text> */}
        </View>
      </TouchableOpacity>
    );
  };

  // Render a header

  // Show this when data is empty
  const renderEmpty = () => <AlertMessage title="No ActiveMinutes Found" show={!fetching} />;

  const keyExtractor = (item, index) => `${index}`;

  // How many items should be kept im memory as we scroll?
  const oneScreensWorth = 20;

  const fetchActiveMinutes = React.useCallback(() => {
    getAllActiveMinutes({ page: page - 1, sort, size });
  }, [getAllActiveMinutes, page, sort, size]);

  const handleLoadMore = () => {
    if (page < props.links.next || props.links.next === undefined || fetching) {
      return;
    }
    setPage(page + 1);
    fetchActiveMinutes();
  };
  return (
    <View style={styles.container} testID="activeMinutesScreen">
      <FlatList
        contentContainerStyle={styles.listContent}
        data={activeMinutesList}
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
    activeMinutesList: state.activeMinutes.activeMinutesList,
    activeMinutes: state.activeMinutes.activeMinutes,
    fetching: state.activeMinutes.fetchingAll,
    error: state.activeMinutes.errorAll,
    links: state.activeMinutes.links,
  };
};

const mapDispatchToProps = (dispatch) => {
  return {
    getAllActiveMinutes: (options) => dispatch(ActiveMinutesActions.activeMinutesAllRequest(options)),
  };
};

export default connect(mapStateToProps, mapDispatchToProps)(ActiveMinutesScreen);
