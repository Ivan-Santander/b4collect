import React from 'react';
import { FlatList, Text, TouchableOpacity, View } from 'react-native';
import { connect } from 'react-redux';
import { useFocusEffect } from '@react-navigation/native';
import BodyTemperatureActions from './body-temperature.reducer';
import styles from './body-temperature-styles';
import AlertMessage from '../../../shared/components/alert-message/alert-message';

function BodyTemperatureScreen(props) {
  const [page, setPage] = React.useState(0);
  const [sort /*, setSort*/] = React.useState('id,asc');
  const [size /*, setSize*/] = React.useState(20);

  const { bodyTemperature, bodyTemperatureList, getAllBodyTemperatures, fetching } = props;

  useFocusEffect(
    React.useCallback(() => {
      console.debug('BodyTemperature entity changed and the list screen is now in focus, refresh');
      setPage(0);
      fetchBodyTemperatures();
      /* eslint-disable-next-line react-hooks/exhaustive-deps */
    }, [bodyTemperature, fetchBodyTemperatures]),
  );

  const renderRow = ({ item }) => {
    return (
      <TouchableOpacity onPress={() => props.navigation.navigate('BodyTemperatureDetail', { entityId: item.id })}>
        <View style={styles.listRow}>
          <Text style={styles.whiteLabel}>ID: {item.id}</Text>
          {/* <Text style={styles.label}>{item.description}</Text> */}
        </View>
      </TouchableOpacity>
    );
  };

  // Render a header

  // Show this when data is empty
  const renderEmpty = () => <AlertMessage title="No BodyTemperatures Found" show={!fetching} />;

  const keyExtractor = (item, index) => `${index}`;

  // How many items should be kept im memory as we scroll?
  const oneScreensWorth = 20;

  const fetchBodyTemperatures = React.useCallback(() => {
    getAllBodyTemperatures({ page: page - 1, sort, size });
  }, [getAllBodyTemperatures, page, sort, size]);

  const handleLoadMore = () => {
    if (page < props.links.next || props.links.next === undefined || fetching) {
      return;
    }
    setPage(page + 1);
    fetchBodyTemperatures();
  };
  return (
    <View style={styles.container} testID="bodyTemperatureScreen">
      <FlatList
        contentContainerStyle={styles.listContent}
        data={bodyTemperatureList}
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
    bodyTemperatureList: state.bodyTemperatures.bodyTemperatureList,
    bodyTemperature: state.bodyTemperatures.bodyTemperature,
    fetching: state.bodyTemperatures.fetchingAll,
    error: state.bodyTemperatures.errorAll,
    links: state.bodyTemperatures.links,
  };
};

const mapDispatchToProps = (dispatch) => {
  return {
    getAllBodyTemperatures: (options) => dispatch(BodyTemperatureActions.bodyTemperatureAllRequest(options)),
  };
};

export default connect(mapStateToProps, mapDispatchToProps)(BodyTemperatureScreen);
