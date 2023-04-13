import React from 'react';
import { FlatList, Text, TouchableOpacity, View } from 'react-native';
import { connect } from 'react-redux';
import { useFocusEffect } from '@react-navigation/native';
import BodyFatPercentageActions from './body-fat-percentage.reducer';
import styles from './body-fat-percentage-styles';
import AlertMessage from '../../../shared/components/alert-message/alert-message';

function BodyFatPercentageScreen(props) {
  const [page, setPage] = React.useState(0);
  const [sort /*, setSort*/] = React.useState('id,asc');
  const [size /*, setSize*/] = React.useState(20);

  const { bodyFatPercentage, bodyFatPercentageList, getAllBodyFatPercentages, fetching } = props;

  useFocusEffect(
    React.useCallback(() => {
      console.debug('BodyFatPercentage entity changed and the list screen is now in focus, refresh');
      setPage(0);
      fetchBodyFatPercentages();
      /* eslint-disable-next-line react-hooks/exhaustive-deps */
    }, [bodyFatPercentage, fetchBodyFatPercentages]),
  );

  const renderRow = ({ item }) => {
    return (
      <TouchableOpacity onPress={() => props.navigation.navigate('BodyFatPercentageDetail', { entityId: item.id })}>
        <View style={styles.listRow}>
          <Text style={styles.whiteLabel}>ID: {item.id}</Text>
          {/* <Text style={styles.label}>{item.description}</Text> */}
        </View>
      </TouchableOpacity>
    );
  };

  // Render a header

  // Show this when data is empty
  const renderEmpty = () => <AlertMessage title="No BodyFatPercentages Found" show={!fetching} />;

  const keyExtractor = (item, index) => `${index}`;

  // How many items should be kept im memory as we scroll?
  const oneScreensWorth = 20;

  const fetchBodyFatPercentages = React.useCallback(() => {
    getAllBodyFatPercentages({ page: page - 1, sort, size });
  }, [getAllBodyFatPercentages, page, sort, size]);

  const handleLoadMore = () => {
    if (page < props.links.next || props.links.next === undefined || fetching) {
      return;
    }
    setPage(page + 1);
    fetchBodyFatPercentages();
  };
  return (
    <View style={styles.container} testID="bodyFatPercentageScreen">
      <FlatList
        contentContainerStyle={styles.listContent}
        data={bodyFatPercentageList}
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
    bodyFatPercentageList: state.bodyFatPercentages.bodyFatPercentageList,
    bodyFatPercentage: state.bodyFatPercentages.bodyFatPercentage,
    fetching: state.bodyFatPercentages.fetchingAll,
    error: state.bodyFatPercentages.errorAll,
    links: state.bodyFatPercentages.links,
  };
};

const mapDispatchToProps = (dispatch) => {
  return {
    getAllBodyFatPercentages: (options) => dispatch(BodyFatPercentageActions.bodyFatPercentageAllRequest(options)),
  };
};

export default connect(mapStateToProps, mapDispatchToProps)(BodyFatPercentageScreen);
