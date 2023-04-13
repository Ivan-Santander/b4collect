import React from 'react';
import { FlatList, Text, TouchableOpacity, View } from 'react-native';
import { connect } from 'react-redux';
import { useFocusEffect } from '@react-navigation/native';
import HeartMinutesActions from './heart-minutes.reducer';
import styles from './heart-minutes-styles';
import AlertMessage from '../../../shared/components/alert-message/alert-message';

function HeartMinutesScreen(props) {
  const [page, setPage] = React.useState(0);
  const [sort /*, setSort*/] = React.useState('id,asc');
  const [size /*, setSize*/] = React.useState(20);

  const { heartMinutes, heartMinutesList, getAllHeartMinutes, fetching } = props;

  useFocusEffect(
    React.useCallback(() => {
      console.debug('HeartMinutes entity changed and the list screen is now in focus, refresh');
      setPage(0);
      fetchHeartMinutes();
      /* eslint-disable-next-line react-hooks/exhaustive-deps */
    }, [heartMinutes, fetchHeartMinutes]),
  );

  const renderRow = ({ item }) => {
    return (
      <TouchableOpacity onPress={() => props.navigation.navigate('HeartMinutesDetail', { entityId: item.id })}>
        <View style={styles.listRow}>
          <Text style={styles.whiteLabel}>ID: {item.id}</Text>
          {/* <Text style={styles.label}>{item.description}</Text> */}
        </View>
      </TouchableOpacity>
    );
  };

  // Render a header

  // Show this when data is empty
  const renderEmpty = () => <AlertMessage title="No HeartMinutes Found" show={!fetching} />;

  const keyExtractor = (item, index) => `${index}`;

  // How many items should be kept im memory as we scroll?
  const oneScreensWorth = 20;

  const fetchHeartMinutes = React.useCallback(() => {
    getAllHeartMinutes({ page: page - 1, sort, size });
  }, [getAllHeartMinutes, page, sort, size]);

  const handleLoadMore = () => {
    if (page < props.links.next || props.links.next === undefined || fetching) {
      return;
    }
    setPage(page + 1);
    fetchHeartMinutes();
  };
  return (
    <View style={styles.container} testID="heartMinutesScreen">
      <FlatList
        contentContainerStyle={styles.listContent}
        data={heartMinutesList}
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
    heartMinutesList: state.heartMinutes.heartMinutesList,
    heartMinutes: state.heartMinutes.heartMinutes,
    fetching: state.heartMinutes.fetchingAll,
    error: state.heartMinutes.errorAll,
    links: state.heartMinutes.links,
  };
};

const mapDispatchToProps = (dispatch) => {
  return {
    getAllHeartMinutes: (options) => dispatch(HeartMinutesActions.heartMinutesAllRequest(options)),
  };
};

export default connect(mapStateToProps, mapDispatchToProps)(HeartMinutesScreen);
