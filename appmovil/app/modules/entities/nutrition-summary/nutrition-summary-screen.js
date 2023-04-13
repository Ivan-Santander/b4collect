import React from 'react';
import { FlatList, Text, TouchableOpacity, View } from 'react-native';
import { connect } from 'react-redux';
import { useFocusEffect } from '@react-navigation/native';
import NutritionSummaryActions from './nutrition-summary.reducer';
import styles from './nutrition-summary-styles';
import AlertMessage from '../../../shared/components/alert-message/alert-message';

function NutritionSummaryScreen(props) {
  const [page, setPage] = React.useState(0);
  const [sort /*, setSort*/] = React.useState('id,asc');
  const [size /*, setSize*/] = React.useState(20);

  const { nutritionSummary, nutritionSummaryList, getAllNutritionSummaries, fetching } = props;

  useFocusEffect(
    React.useCallback(() => {
      console.debug('NutritionSummary entity changed and the list screen is now in focus, refresh');
      setPage(0);
      fetchNutritionSummaries();
      /* eslint-disable-next-line react-hooks/exhaustive-deps */
    }, [nutritionSummary, fetchNutritionSummaries]),
  );

  const renderRow = ({ item }) => {
    return (
      <TouchableOpacity onPress={() => props.navigation.navigate('NutritionSummaryDetail', { entityId: item.id })}>
        <View style={styles.listRow}>
          <Text style={styles.whiteLabel}>ID: {item.id}</Text>
          {/* <Text style={styles.label}>{item.description}</Text> */}
        </View>
      </TouchableOpacity>
    );
  };

  // Render a header

  // Show this when data is empty
  const renderEmpty = () => <AlertMessage title="No NutritionSummaries Found" show={!fetching} />;

  const keyExtractor = (item, index) => `${index}`;

  // How many items should be kept im memory as we scroll?
  const oneScreensWorth = 20;

  const fetchNutritionSummaries = React.useCallback(() => {
    getAllNutritionSummaries({ page: page - 1, sort, size });
  }, [getAllNutritionSummaries, page, sort, size]);

  const handleLoadMore = () => {
    if (page < props.links.next || props.links.next === undefined || fetching) {
      return;
    }
    setPage(page + 1);
    fetchNutritionSummaries();
  };
  return (
    <View style={styles.container} testID="nutritionSummaryScreen">
      <FlatList
        contentContainerStyle={styles.listContent}
        data={nutritionSummaryList}
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
    nutritionSummaryList: state.nutritionSummaries.nutritionSummaryList,
    nutritionSummary: state.nutritionSummaries.nutritionSummary,
    fetching: state.nutritionSummaries.fetchingAll,
    error: state.nutritionSummaries.errorAll,
    links: state.nutritionSummaries.links,
  };
};

const mapDispatchToProps = (dispatch) => {
  return {
    getAllNutritionSummaries: (options) => dispatch(NutritionSummaryActions.nutritionSummaryAllRequest(options)),
  };
};

export default connect(mapStateToProps, mapDispatchToProps)(NutritionSummaryScreen);
