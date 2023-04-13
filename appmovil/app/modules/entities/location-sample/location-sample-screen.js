import React from 'react';
import { FlatList, Text, TouchableOpacity, View } from 'react-native';
import { connect } from 'react-redux';
import { useFocusEffect } from '@react-navigation/native';
import LocationSampleActions from './location-sample.reducer';
import styles from './location-sample-styles';
import AlertMessage from '../../../shared/components/alert-message/alert-message';

function LocationSampleScreen(props) {
  const [page, setPage] = React.useState(0);
  const [sort /*, setSort*/] = React.useState('id,asc');
  const [size /*, setSize*/] = React.useState(20);

  const { locationSample, locationSampleList, getAllLocationSamples, fetching } = props;

  useFocusEffect(
    React.useCallback(() => {
      console.debug('LocationSample entity changed and the list screen is now in focus, refresh');
      setPage(0);
      fetchLocationSamples();
      /* eslint-disable-next-line react-hooks/exhaustive-deps */
    }, [locationSample, fetchLocationSamples]),
  );

  const renderRow = ({ item }) => {
    return (
      <TouchableOpacity onPress={() => props.navigation.navigate('LocationSampleDetail', { entityId: item.id })}>
        <View style={styles.listRow}>
          <Text style={styles.whiteLabel}>ID: {item.id}</Text>
          {/* <Text style={styles.label}>{item.description}</Text> */}
        </View>
      </TouchableOpacity>
    );
  };

  // Render a header

  // Show this when data is empty
  const renderEmpty = () => <AlertMessage title="No LocationSamples Found" show={!fetching} />;

  const keyExtractor = (item, index) => `${index}`;

  // How many items should be kept im memory as we scroll?
  const oneScreensWorth = 20;

  const fetchLocationSamples = React.useCallback(() => {
    getAllLocationSamples({ page: page - 1, sort, size });
  }, [getAllLocationSamples, page, sort, size]);

  const handleLoadMore = () => {
    if (page < props.links.next || props.links.next === undefined || fetching) {
      return;
    }
    setPage(page + 1);
    fetchLocationSamples();
  };
  return (
    <View style={styles.container} testID="locationSampleScreen">
      <FlatList
        contentContainerStyle={styles.listContent}
        data={locationSampleList}
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
    locationSampleList: state.locationSamples.locationSampleList,
    locationSample: state.locationSamples.locationSample,
    fetching: state.locationSamples.fetchingAll,
    error: state.locationSamples.errorAll,
    links: state.locationSamples.links,
  };
};

const mapDispatchToProps = (dispatch) => {
  return {
    getAllLocationSamples: (options) => dispatch(LocationSampleActions.locationSampleAllRequest(options)),
  };
};

export default connect(mapStateToProps, mapDispatchToProps)(LocationSampleScreen);
