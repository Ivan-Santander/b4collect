import React from 'react';
import { FlatList, Text, TouchableOpacity, View } from 'react-native';
import { connect } from 'react-redux';
import { useFocusEffect } from '@react-navigation/native';
import CaloriesExpendedActions from './calories-expended.reducer';
import styles from './calories-expended-styles';
import AlertMessage from '../../../shared/components/alert-message/alert-message';

function CaloriesExpendedScreen(props) {
  const [page, setPage] = React.useState(0);
  const [sort /*, setSort*/] = React.useState('id,asc');
  const [size /*, setSize*/] = React.useState(20);

  const { caloriesExpended, caloriesExpendedList, getAllCaloriesExpendeds, fetching } = props;

  useFocusEffect(
    React.useCallback(() => {
      console.debug('CaloriesExpended entity changed and the list screen is now in focus, refresh');
      setPage(0);
      fetchCaloriesExpendeds();
      /* eslint-disable-next-line react-hooks/exhaustive-deps */
    }, [caloriesExpended, fetchCaloriesExpendeds]),
  );

  const renderRow = ({ item }) => {
    return (
      <TouchableOpacity onPress={() => props.navigation.navigate('CaloriesExpendedDetail', { entityId: item.id })}>
        <View style={styles.listRow}>
          <Text style={styles.whiteLabel}>ID: {item.id}</Text>
          {/* <Text style={styles.label}>{item.description}</Text> */}
        </View>
      </TouchableOpacity>
    );
  };

  // Render a header

  // Show this when data is empty
  const renderEmpty = () => <AlertMessage title="No CaloriesExpendeds Found" show={!fetching} />;

  const keyExtractor = (item, index) => `${index}`;

  // How many items should be kept im memory as we scroll?
  const oneScreensWorth = 20;

  const fetchCaloriesExpendeds = React.useCallback(() => {
    getAllCaloriesExpendeds({ page: page - 1, sort, size });
  }, [getAllCaloriesExpendeds, page, sort, size]);

  const handleLoadMore = () => {
    if (page < props.links.next || props.links.next === undefined || fetching) {
      return;
    }
    setPage(page + 1);
    fetchCaloriesExpendeds();
  };
  return (
    <View style={styles.container} testID="caloriesExpendedScreen">
      <FlatList
        contentContainerStyle={styles.listContent}
        data={caloriesExpendedList}
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
    caloriesExpendedList: state.caloriesExpendeds.caloriesExpendedList,
    caloriesExpended: state.caloriesExpendeds.caloriesExpended,
    fetching: state.caloriesExpendeds.fetchingAll,
    error: state.caloriesExpendeds.errorAll,
    links: state.caloriesExpendeds.links,
  };
};

const mapDispatchToProps = (dispatch) => {
  return {
    getAllCaloriesExpendeds: (options) => dispatch(CaloriesExpendedActions.caloriesExpendedAllRequest(options)),
  };
};

export default connect(mapStateToProps, mapDispatchToProps)(CaloriesExpendedScreen);
