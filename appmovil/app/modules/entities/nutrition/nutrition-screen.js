import React from 'react';
import { FlatList, Text, TouchableOpacity, View } from 'react-native';
import { connect } from 'react-redux';
import { useFocusEffect } from '@react-navigation/native';
import NutritionActions from './nutrition.reducer';
import styles from './nutrition-styles';
import AlertMessage from '../../../shared/components/alert-message/alert-message';

function NutritionScreen(props) {
  const [page, setPage] = React.useState(0);
  const [sort /*, setSort*/] = React.useState('id,asc');
  const [size /*, setSize*/] = React.useState(20);

  const { nutrition, nutritionList, getAllNutritions, fetching } = props;

  useFocusEffect(
    React.useCallback(() => {
      console.debug('Nutrition entity changed and the list screen is now in focus, refresh');
      setPage(0);
      fetchNutritions();
      /* eslint-disable-next-line react-hooks/exhaustive-deps */
    }, [nutrition, fetchNutritions]),
  );

  const renderRow = ({ item }) => {
    return (
      <TouchableOpacity onPress={() => props.navigation.navigate('NutritionDetail', { entityId: item.id })}>
        <View style={styles.listRow}>
          <Text style={styles.whiteLabel}>ID: {item.id}</Text>
          {/* <Text style={styles.label}>{item.description}</Text> */}
        </View>
      </TouchableOpacity>
    );
  };

  // Render a header

  // Show this when data is empty
  const renderEmpty = () => <AlertMessage title="No Nutritions Found" show={!fetching} />;

  const keyExtractor = (item, index) => `${index}`;

  // How many items should be kept im memory as we scroll?
  const oneScreensWorth = 20;

  const fetchNutritions = React.useCallback(() => {
    getAllNutritions({ page: page - 1, sort, size });
  }, [getAllNutritions, page, sort, size]);

  const handleLoadMore = () => {
    if (page < props.links.next || props.links.next === undefined || fetching) {
      return;
    }
    setPage(page + 1);
    fetchNutritions();
  };
  return (
    <View style={styles.container} testID="nutritionScreen">
      <FlatList
        contentContainerStyle={styles.listContent}
        data={nutritionList}
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
    nutritionList: state.nutritions.nutritionList,
    nutrition: state.nutritions.nutrition,
    fetching: state.nutritions.fetchingAll,
    error: state.nutritions.errorAll,
    links: state.nutritions.links,
  };
};

const mapDispatchToProps = (dispatch) => {
  return {
    getAllNutritions: (options) => dispatch(NutritionActions.nutritionAllRequest(options)),
  };
};

export default connect(mapStateToProps, mapDispatchToProps)(NutritionScreen);
