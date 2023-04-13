import React from 'react';
import { FlatList, Text, TouchableOpacity, View } from 'react-native';
import { connect } from 'react-redux';
import { useFocusEffect } from '@react-navigation/native';
import PoweSampleActions from './powe-sample.reducer';
import styles from './powe-sample-styles';
import AlertMessage from '../../../shared/components/alert-message/alert-message';

function PoweSampleScreen(props) {
  const [page, setPage] = React.useState(0);
  const [sort /*, setSort*/] = React.useState('id,asc');
  const [size /*, setSize*/] = React.useState(20);

  const { poweSample, poweSampleList, getAllPoweSamples, fetching } = props;

  useFocusEffect(
    React.useCallback(() => {
      console.debug('PoweSample entity changed and the list screen is now in focus, refresh');
      setPage(0);
      fetchPoweSamples();
      /* eslint-disable-next-line react-hooks/exhaustive-deps */
    }, [poweSample, fetchPoweSamples]),
  );

  const renderRow = ({ item }) => {
    return (
      <TouchableOpacity onPress={() => props.navigation.navigate('PoweSampleDetail', { entityId: item.id })}>
        <View style={styles.listRow}>
          <Text style={styles.whiteLabel}>ID: {item.id}</Text>
          {/* <Text style={styles.label}>{item.description}</Text> */}
        </View>
      </TouchableOpacity>
    );
  };

  // Render a header

  // Show this when data is empty
  const renderEmpty = () => <AlertMessage title="No PoweSamples Found" show={!fetching} />;

  const keyExtractor = (item, index) => `${index}`;

  // How many items should be kept im memory as we scroll?
  const oneScreensWorth = 20;

  const fetchPoweSamples = React.useCallback(() => {
    getAllPoweSamples({ page: page - 1, sort, size });
  }, [getAllPoweSamples, page, sort, size]);

  const handleLoadMore = () => {
    if (page < props.links.next || props.links.next === undefined || fetching) {
      return;
    }
    setPage(page + 1);
    fetchPoweSamples();
  };
  return (
    <View style={styles.container} testID="poweSampleScreen">
      <FlatList
        contentContainerStyle={styles.listContent}
        data={poweSampleList}
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
    poweSampleList: state.poweSamples.poweSampleList,
    poweSample: state.poweSamples.poweSample,
    fetching: state.poweSamples.fetchingAll,
    error: state.poweSamples.errorAll,
    links: state.poweSamples.links,
  };
};

const mapDispatchToProps = (dispatch) => {
  return {
    getAllPoweSamples: (options) => dispatch(PoweSampleActions.poweSampleAllRequest(options)),
  };
};

export default connect(mapStateToProps, mapDispatchToProps)(PoweSampleScreen);
