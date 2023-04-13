import React from 'react';
import { FlatList, Text, TouchableOpacity, View } from 'react-native';
import { connect } from 'react-redux';
import { useFocusEffect } from '@react-navigation/native';
import CaloriesBMRActions from './calories-bmr.reducer';
import styles from './calories-bmr-styles';
import AlertMessage from '../../../shared/components/alert-message/alert-message';

function CaloriesBMRScreen(props) {
  const [page, setPage] = React.useState(0);
  const [sort /*, setSort*/] = React.useState('id,asc');
  const [size /*, setSize*/] = React.useState(20);

  const { caloriesBMR, caloriesBMRList, getAllCaloriesBMRS, fetching } = props;

  useFocusEffect(
    React.useCallback(() => {
      console.debug('CaloriesBMR entity changed and the list screen is now in focus, refresh');
      setPage(0);
      fetchCaloriesBMRS();
      /* eslint-disable-next-line react-hooks/exhaustive-deps */
    }, [caloriesBMR, fetchCaloriesBMRS]),
  );

  const renderRow = ({ item }) => {
    return (
      <TouchableOpacity onPress={() => props.navigation.navigate('CaloriesBMRDetail', { entityId: item.id })}>
        <View style={styles.listRow}>
          <Text style={styles.whiteLabel}>ID: {item.id}</Text>
          {/* <Text style={styles.label}>{item.description}</Text> */}
        </View>
      </TouchableOpacity>
    );
  };

  // Render a header

  // Show this when data is empty
  const renderEmpty = () => <AlertMessage title="No CaloriesBMRS Found" show={!fetching} />;

  const keyExtractor = (item, index) => `${index}`;

  // How many items should be kept im memory as we scroll?
  const oneScreensWorth = 20;

  const fetchCaloriesBMRS = React.useCallback(() => {
    getAllCaloriesBMRS({ page: page - 1, sort, size });
  }, [getAllCaloriesBMRS, page, sort, size]);

  const handleLoadMore = () => {
    if (page < props.links.next || props.links.next === undefined || fetching) {
      return;
    }
    setPage(page + 1);
    fetchCaloriesBMRS();
  };
  return (
    <View style={styles.container} testID="caloriesBMRScreen">
      <FlatList
        contentContainerStyle={styles.listContent}
        data={caloriesBMRList}
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
    caloriesBMRList: state.caloriesBMRS.caloriesBMRList,
    caloriesBMR: state.caloriesBMRS.caloriesBMR,
    fetching: state.caloriesBMRS.fetchingAll,
    error: state.caloriesBMRS.errorAll,
    links: state.caloriesBMRS.links,
  };
};

const mapDispatchToProps = (dispatch) => {
  return {
    getAllCaloriesBMRS: (options) => dispatch(CaloriesBMRActions.caloriesBMRAllRequest(options)),
  };
};

export default connect(mapStateToProps, mapDispatchToProps)(CaloriesBMRScreen);
