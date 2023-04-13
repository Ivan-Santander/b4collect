import React from 'react';
import { FlatList, Text, TouchableOpacity, View } from 'react-native';
import { connect } from 'react-redux';
import { useFocusEffect } from '@react-navigation/native';
import HeartRateBpmActions from './heart-rate-bpm.reducer';
import styles from './heart-rate-bpm-styles';
import AlertMessage from '../../../shared/components/alert-message/alert-message';

function HeartRateBpmScreen(props) {
  const [page, setPage] = React.useState(0);
  const [sort /*, setSort*/] = React.useState('id,asc');
  const [size /*, setSize*/] = React.useState(20);

  const { heartRateBpm, heartRateBpmList, getAllHeartRateBpms, fetching } = props;

  useFocusEffect(
    React.useCallback(() => {
      console.debug('HeartRateBpm entity changed and the list screen is now in focus, refresh');
      setPage(0);
      fetchHeartRateBpms();
      /* eslint-disable-next-line react-hooks/exhaustive-deps */
    }, [heartRateBpm, fetchHeartRateBpms]),
  );

  const renderRow = ({ item }) => {
    return (
      <TouchableOpacity onPress={() => props.navigation.navigate('HeartRateBpmDetail', { entityId: item.id })}>
        <View style={styles.listRow}>
          <Text style={styles.whiteLabel}>ID: {item.id}</Text>
          {/* <Text style={styles.label}>{item.description}</Text> */}
        </View>
      </TouchableOpacity>
    );
  };

  // Render a header

  // Show this when data is empty
  const renderEmpty = () => <AlertMessage title="No HeartRateBpms Found" show={!fetching} />;

  const keyExtractor = (item, index) => `${index}`;

  // How many items should be kept im memory as we scroll?
  const oneScreensWorth = 20;

  const fetchHeartRateBpms = React.useCallback(() => {
    getAllHeartRateBpms({ page: page - 1, sort, size });
  }, [getAllHeartRateBpms, page, sort, size]);

  const handleLoadMore = () => {
    if (page < props.links.next || props.links.next === undefined || fetching) {
      return;
    }
    setPage(page + 1);
    fetchHeartRateBpms();
  };
  return (
    <View style={styles.container} testID="heartRateBpmScreen">
      <FlatList
        contentContainerStyle={styles.listContent}
        data={heartRateBpmList}
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
    heartRateBpmList: state.heartRateBpms.heartRateBpmList,
    heartRateBpm: state.heartRateBpms.heartRateBpm,
    fetching: state.heartRateBpms.fetchingAll,
    error: state.heartRateBpms.errorAll,
    links: state.heartRateBpms.links,
  };
};

const mapDispatchToProps = (dispatch) => {
  return {
    getAllHeartRateBpms: (options) => dispatch(HeartRateBpmActions.heartRateBpmAllRequest(options)),
  };
};

export default connect(mapStateToProps, mapDispatchToProps)(HeartRateBpmScreen);
