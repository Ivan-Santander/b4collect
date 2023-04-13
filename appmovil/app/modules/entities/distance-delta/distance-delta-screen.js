import React from 'react';
import { FlatList, Text, TouchableOpacity, View } from 'react-native';
import { connect } from 'react-redux';
import { useFocusEffect } from '@react-navigation/native';
import DistanceDeltaActions from './distance-delta.reducer';
import styles from './distance-delta-styles';
import AlertMessage from '../../../shared/components/alert-message/alert-message';

function DistanceDeltaScreen(props) {
  const [page, setPage] = React.useState(0);
  const [sort /*, setSort*/] = React.useState('id,asc');
  const [size /*, setSize*/] = React.useState(20);

  const { distanceDelta, distanceDeltaList, getAllDistanceDeltas, fetching } = props;

  useFocusEffect(
    React.useCallback(() => {
      console.debug('DistanceDelta entity changed and the list screen is now in focus, refresh');
      setPage(0);
      fetchDistanceDeltas();
      /* eslint-disable-next-line react-hooks/exhaustive-deps */
    }, [distanceDelta, fetchDistanceDeltas]),
  );

  const renderRow = ({ item }) => {
    return (
      <TouchableOpacity onPress={() => props.navigation.navigate('DistanceDeltaDetail', { entityId: item.id })}>
        <View style={styles.listRow}>
          <Text style={styles.whiteLabel}>ID: {item.id}</Text>
          {/* <Text style={styles.label}>{item.description}</Text> */}
        </View>
      </TouchableOpacity>
    );
  };

  // Render a header

  // Show this when data is empty
  const renderEmpty = () => <AlertMessage title="No DistanceDeltas Found" show={!fetching} />;

  const keyExtractor = (item, index) => `${index}`;

  // How many items should be kept im memory as we scroll?
  const oneScreensWorth = 20;

  const fetchDistanceDeltas = React.useCallback(() => {
    getAllDistanceDeltas({ page: page - 1, sort, size });
  }, [getAllDistanceDeltas, page, sort, size]);

  const handleLoadMore = () => {
    if (page < props.links.next || props.links.next === undefined || fetching) {
      return;
    }
    setPage(page + 1);
    fetchDistanceDeltas();
  };
  return (
    <View style={styles.container} testID="distanceDeltaScreen">
      <FlatList
        contentContainerStyle={styles.listContent}
        data={distanceDeltaList}
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
    distanceDeltaList: state.distanceDeltas.distanceDeltaList,
    distanceDelta: state.distanceDeltas.distanceDelta,
    fetching: state.distanceDeltas.fetchingAll,
    error: state.distanceDeltas.errorAll,
    links: state.distanceDeltas.links,
  };
};

const mapDispatchToProps = (dispatch) => {
  return {
    getAllDistanceDeltas: (options) => dispatch(DistanceDeltaActions.distanceDeltaAllRequest(options)),
  };
};

export default connect(mapStateToProps, mapDispatchToProps)(DistanceDeltaScreen);
