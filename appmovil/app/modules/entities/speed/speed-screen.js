import React from 'react';
import { FlatList, Text, TouchableOpacity, View } from 'react-native';
import { connect } from 'react-redux';
import { useFocusEffect } from '@react-navigation/native';
import SpeedActions from './speed.reducer';
import styles from './speed-styles';
import AlertMessage from '../../../shared/components/alert-message/alert-message';

function SpeedScreen(props) {
  const [page, setPage] = React.useState(0);
  const [sort /*, setSort*/] = React.useState('id,asc');
  const [size /*, setSize*/] = React.useState(20);

  const { speed, speedList, getAllSpeeds, fetching } = props;

  useFocusEffect(
    React.useCallback(() => {
      console.debug('Speed entity changed and the list screen is now in focus, refresh');
      setPage(0);
      fetchSpeeds();
      /* eslint-disable-next-line react-hooks/exhaustive-deps */
    }, [speed, fetchSpeeds]),
  );

  const renderRow = ({ item }) => {
    return (
      <TouchableOpacity onPress={() => props.navigation.navigate('SpeedDetail', { entityId: item.id })}>
        <View style={styles.listRow}>
          <Text style={styles.whiteLabel}>ID: {item.id}</Text>
          {/* <Text style={styles.label}>{item.description}</Text> */}
        </View>
      </TouchableOpacity>
    );
  };

  // Render a header

  // Show this when data is empty
  const renderEmpty = () => <AlertMessage title="No Speeds Found" show={!fetching} />;

  const keyExtractor = (item, index) => `${index}`;

  // How many items should be kept im memory as we scroll?
  const oneScreensWorth = 20;

  const fetchSpeeds = React.useCallback(() => {
    getAllSpeeds({ page: page - 1, sort, size });
  }, [getAllSpeeds, page, sort, size]);

  const handleLoadMore = () => {
    if (page < props.links.next || props.links.next === undefined || fetching) {
      return;
    }
    setPage(page + 1);
    fetchSpeeds();
  };
  return (
    <View style={styles.container} testID="speedScreen">
      <FlatList
        contentContainerStyle={styles.listContent}
        data={speedList}
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
    speedList: state.speeds.speedList,
    speed: state.speeds.speed,
    fetching: state.speeds.fetchingAll,
    error: state.speeds.errorAll,
    links: state.speeds.links,
  };
};

const mapDispatchToProps = (dispatch) => {
  return {
    getAllSpeeds: (options) => dispatch(SpeedActions.speedAllRequest(options)),
  };
};

export default connect(mapStateToProps, mapDispatchToProps)(SpeedScreen);
