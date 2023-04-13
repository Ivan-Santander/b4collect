import React from 'react';
import { FlatList, Text, TouchableOpacity, View } from 'react-native';
import { connect } from 'react-redux';
import { useFocusEffect } from '@react-navigation/native';
import BloodGlucoseActions from './blood-glucose.reducer';
import styles from './blood-glucose-styles';
import AlertMessage from '../../../shared/components/alert-message/alert-message';

function BloodGlucoseScreen(props) {
  const [page, setPage] = React.useState(0);
  const [sort /*, setSort*/] = React.useState('id,asc');
  const [size /*, setSize*/] = React.useState(20);

  const { bloodGlucose, bloodGlucoseList, getAllBloodGlucoses, fetching } = props;

  useFocusEffect(
    React.useCallback(() => {
      console.debug('BloodGlucose entity changed and the list screen is now in focus, refresh');
      setPage(0);
      fetchBloodGlucoses();
      /* eslint-disable-next-line react-hooks/exhaustive-deps */
    }, [bloodGlucose, fetchBloodGlucoses]),
  );

  const renderRow = ({ item }) => {
    return (
      <TouchableOpacity onPress={() => props.navigation.navigate('BloodGlucoseDetail', { entityId: item.id })}>
        <View style={styles.listRow}>
          <Text style={styles.whiteLabel}>ID: {item.id}</Text>
          {/* <Text style={styles.label}>{item.description}</Text> */}
        </View>
      </TouchableOpacity>
    );
  };

  // Render a header

  // Show this when data is empty
  const renderEmpty = () => <AlertMessage title="No BloodGlucoses Found" show={!fetching} />;

  const keyExtractor = (item, index) => `${index}`;

  // How many items should be kept im memory as we scroll?
  const oneScreensWorth = 20;

  const fetchBloodGlucoses = React.useCallback(() => {
    getAllBloodGlucoses({ page: page - 1, sort, size });
  }, [getAllBloodGlucoses, page, sort, size]);

  const handleLoadMore = () => {
    if (page < props.links.next || props.links.next === undefined || fetching) {
      return;
    }
    setPage(page + 1);
    fetchBloodGlucoses();
  };
  return (
    <View style={styles.container} testID="bloodGlucoseScreen">
      <FlatList
        contentContainerStyle={styles.listContent}
        data={bloodGlucoseList}
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
    bloodGlucoseList: state.bloodGlucoses.bloodGlucoseList,
    bloodGlucose: state.bloodGlucoses.bloodGlucose,
    fetching: state.bloodGlucoses.fetchingAll,
    error: state.bloodGlucoses.errorAll,
    links: state.bloodGlucoses.links,
  };
};

const mapDispatchToProps = (dispatch) => {
  return {
    getAllBloodGlucoses: (options) => dispatch(BloodGlucoseActions.bloodGlucoseAllRequest(options)),
  };
};

export default connect(mapStateToProps, mapDispatchToProps)(BloodGlucoseScreen);
