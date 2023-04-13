import React from 'react';
import { FlatList, Text, TouchableOpacity, View } from 'react-native';
import { connect } from 'react-redux';
import { useFocusEffect } from '@react-navigation/native';
import CyclingWheelRevolutionActions from './cycling-wheel-revolution.reducer';
import styles from './cycling-wheel-revolution-styles';
import AlertMessage from '../../../shared/components/alert-message/alert-message';

function CyclingWheelRevolutionScreen(props) {
  const [page, setPage] = React.useState(0);
  const [sort /*, setSort*/] = React.useState('id,asc');
  const [size /*, setSize*/] = React.useState(20);

  const { cyclingWheelRevolution, cyclingWheelRevolutionList, getAllCyclingWheelRevolutions, fetching } = props;

  useFocusEffect(
    React.useCallback(() => {
      console.debug('CyclingWheelRevolution entity changed and the list screen is now in focus, refresh');
      setPage(0);
      fetchCyclingWheelRevolutions();
      /* eslint-disable-next-line react-hooks/exhaustive-deps */
    }, [cyclingWheelRevolution, fetchCyclingWheelRevolutions]),
  );

  const renderRow = ({ item }) => {
    return (
      <TouchableOpacity onPress={() => props.navigation.navigate('CyclingWheelRevolutionDetail', { entityId: item.id })}>
        <View style={styles.listRow}>
          <Text style={styles.whiteLabel}>ID: {item.id}</Text>
          {/* <Text style={styles.label}>{item.description}</Text> */}
        </View>
      </TouchableOpacity>
    );
  };

  // Render a header

  // Show this when data is empty
  const renderEmpty = () => <AlertMessage title="No CyclingWheelRevolutions Found" show={!fetching} />;

  const keyExtractor = (item, index) => `${index}`;

  // How many items should be kept im memory as we scroll?
  const oneScreensWorth = 20;

  const fetchCyclingWheelRevolutions = React.useCallback(() => {
    getAllCyclingWheelRevolutions({ page: page - 1, sort, size });
  }, [getAllCyclingWheelRevolutions, page, sort, size]);

  const handleLoadMore = () => {
    if (page < props.links.next || props.links.next === undefined || fetching) {
      return;
    }
    setPage(page + 1);
    fetchCyclingWheelRevolutions();
  };
  return (
    <View style={styles.container} testID="cyclingWheelRevolutionScreen">
      <FlatList
        contentContainerStyle={styles.listContent}
        data={cyclingWheelRevolutionList}
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
    cyclingWheelRevolutionList: state.cyclingWheelRevolutions.cyclingWheelRevolutionList,
    cyclingWheelRevolution: state.cyclingWheelRevolutions.cyclingWheelRevolution,
    fetching: state.cyclingWheelRevolutions.fetchingAll,
    error: state.cyclingWheelRevolutions.errorAll,
    links: state.cyclingWheelRevolutions.links,
  };
};

const mapDispatchToProps = (dispatch) => {
  return {
    getAllCyclingWheelRevolutions: (options) => dispatch(CyclingWheelRevolutionActions.cyclingWheelRevolutionAllRequest(options)),
  };
};

export default connect(mapStateToProps, mapDispatchToProps)(CyclingWheelRevolutionScreen);
