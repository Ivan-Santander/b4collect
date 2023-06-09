import React from 'react';
import { FlatList, Text, TouchableOpacity, View } from 'react-native';
import { connect } from 'react-redux';
import { useFocusEffect } from '@react-navigation/native';
import HeightActions from './height.reducer';
import styles from './height-styles';
import AlertMessage from '../../../shared/components/alert-message/alert-message';

function HeightScreen(props) {
  const [page, setPage] = React.useState(0);
  const [sort /*, setSort*/] = React.useState('id,asc');
  const [size /*, setSize*/] = React.useState(20);

  const { height, heightList, getAllHeights, fetching } = props;

  useFocusEffect(
    React.useCallback(() => {
      console.debug('Height entity changed and the list screen is now in focus, refresh');
      setPage(0);
      fetchHeights();
      /* eslint-disable-next-line react-hooks/exhaustive-deps */
    }, [height, fetchHeights]),
  );

  const renderRow = ({ item }) => {
    return (
      <TouchableOpacity onPress={() => props.navigation.navigate('HeightDetail', { entityId: item.id })}>
        <View style={styles.listRow}>
          <Text style={styles.whiteLabel}>ID: {item.id}</Text>
          {/* <Text style={styles.label}>{item.description}</Text> */}
        </View>
      </TouchableOpacity>
    );
  };

  // Render a header

  // Show this when data is empty
  const renderEmpty = () => <AlertMessage title="No Heights Found" show={!fetching} />;

  const keyExtractor = (item, index) => `${index}`;

  // How many items should be kept im memory as we scroll?
  const oneScreensWorth = 20;

  const fetchHeights = React.useCallback(() => {
    getAllHeights({ page: page - 1, sort, size });
  }, [getAllHeights, page, sort, size]);

  const handleLoadMore = () => {
    if (page < props.links.next || props.links.next === undefined || fetching) {
      return;
    }
    setPage(page + 1);
    fetchHeights();
  };
  return (
    <View style={styles.container} testID="heightScreen">
      <FlatList
        contentContainerStyle={styles.listContent}
        data={heightList}
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
    heightList: state.heights.heightList,
    height: state.heights.height,
    fetching: state.heights.fetchingAll,
    error: state.heights.errorAll,
    links: state.heights.links,
  };
};

const mapDispatchToProps = (dispatch) => {
  return {
    getAllHeights: (options) => dispatch(HeightActions.heightAllRequest(options)),
  };
};

export default connect(mapStateToProps, mapDispatchToProps)(HeightScreen);
