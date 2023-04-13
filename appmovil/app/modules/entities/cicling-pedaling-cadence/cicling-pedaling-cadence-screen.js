import React from 'react';
import { FlatList, Text, TouchableOpacity, View } from 'react-native';
import { connect } from 'react-redux';
import { useFocusEffect } from '@react-navigation/native';
import CiclingPedalingCadenceActions from './cicling-pedaling-cadence.reducer';
import styles from './cicling-pedaling-cadence-styles';
import AlertMessage from '../../../shared/components/alert-message/alert-message';

function CiclingPedalingCadenceScreen(props) {
  const [page, setPage] = React.useState(0);
  const [sort /*, setSort*/] = React.useState('id,asc');
  const [size /*, setSize*/] = React.useState(20);

  const { ciclingPedalingCadence, ciclingPedalingCadenceList, getAllCiclingPedalingCadences, fetching } = props;

  useFocusEffect(
    React.useCallback(() => {
      console.debug('CiclingPedalingCadence entity changed and the list screen is now in focus, refresh');
      setPage(0);
      fetchCiclingPedalingCadences();
      /* eslint-disable-next-line react-hooks/exhaustive-deps */
    }, [ciclingPedalingCadence, fetchCiclingPedalingCadences]),
  );

  const renderRow = ({ item }) => {
    return (
      <TouchableOpacity onPress={() => props.navigation.navigate('CiclingPedalingCadenceDetail', { entityId: item.id })}>
        <View style={styles.listRow}>
          <Text style={styles.whiteLabel}>ID: {item.id}</Text>
          {/* <Text style={styles.label}>{item.description}</Text> */}
        </View>
      </TouchableOpacity>
    );
  };

  // Render a header

  // Show this when data is empty
  const renderEmpty = () => <AlertMessage title="No CiclingPedalingCadences Found" show={!fetching} />;

  const keyExtractor = (item, index) => `${index}`;

  // How many items should be kept im memory as we scroll?
  const oneScreensWorth = 20;

  const fetchCiclingPedalingCadences = React.useCallback(() => {
    getAllCiclingPedalingCadences({ page: page - 1, sort, size });
  }, [getAllCiclingPedalingCadences, page, sort, size]);

  const handleLoadMore = () => {
    if (page < props.links.next || props.links.next === undefined || fetching) {
      return;
    }
    setPage(page + 1);
    fetchCiclingPedalingCadences();
  };
  return (
    <View style={styles.container} testID="ciclingPedalingCadenceScreen">
      <FlatList
        contentContainerStyle={styles.listContent}
        data={ciclingPedalingCadenceList}
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
    ciclingPedalingCadenceList: state.ciclingPedalingCadences.ciclingPedalingCadenceList,
    ciclingPedalingCadence: state.ciclingPedalingCadences.ciclingPedalingCadence,
    fetching: state.ciclingPedalingCadences.fetchingAll,
    error: state.ciclingPedalingCadences.errorAll,
    links: state.ciclingPedalingCadences.links,
  };
};

const mapDispatchToProps = (dispatch) => {
  return {
    getAllCiclingPedalingCadences: (options) => dispatch(CiclingPedalingCadenceActions.ciclingPedalingCadenceAllRequest(options)),
  };
};

export default connect(mapStateToProps, mapDispatchToProps)(CiclingPedalingCadenceScreen);
