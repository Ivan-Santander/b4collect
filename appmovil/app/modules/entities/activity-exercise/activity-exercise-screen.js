import React from 'react';
import { FlatList, Text, TouchableOpacity, View } from 'react-native';
import { connect } from 'react-redux';
import { useFocusEffect } from '@react-navigation/native';
import ActivityExerciseActions from './activity-exercise.reducer';
import styles from './activity-exercise-styles';
import AlertMessage from '../../../shared/components/alert-message/alert-message';

function ActivityExerciseScreen(props) {
  const [page, setPage] = React.useState(0);
  const [sort /*, setSort*/] = React.useState('id,asc');
  const [size /*, setSize*/] = React.useState(20);

  const { activityExercise, activityExerciseList, getAllActivityExercises, fetching } = props;

  useFocusEffect(
    React.useCallback(() => {
      console.debug('ActivityExercise entity changed and the list screen is now in focus, refresh');
      setPage(0);
      fetchActivityExercises();
      /* eslint-disable-next-line react-hooks/exhaustive-deps */
    }, [activityExercise, fetchActivityExercises]),
  );

  const renderRow = ({ item }) => {
    return (
      <TouchableOpacity onPress={() => props.navigation.navigate('ActivityExerciseDetail', { entityId: item.id })}>
        <View style={styles.listRow}>
          <Text style={styles.whiteLabel}>ID: {item.id}</Text>
          {/* <Text style={styles.label}>{item.description}</Text> */}
        </View>
      </TouchableOpacity>
    );
  };

  // Render a header

  // Show this when data is empty
  const renderEmpty = () => <AlertMessage title="No ActivityExercises Found" show={!fetching} />;

  const keyExtractor = (item, index) => `${index}`;

  // How many items should be kept im memory as we scroll?
  const oneScreensWorth = 20;

  const fetchActivityExercises = React.useCallback(() => {
    getAllActivityExercises({ page: page - 1, sort, size });
  }, [getAllActivityExercises, page, sort, size]);

  const handleLoadMore = () => {
    if (page < props.links.next || props.links.next === undefined || fetching) {
      return;
    }
    setPage(page + 1);
    fetchActivityExercises();
  };
  return (
    <View style={styles.container} testID="activityExerciseScreen">
      <FlatList
        contentContainerStyle={styles.listContent}
        data={activityExerciseList}
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
    activityExerciseList: state.activityExercises.activityExerciseList,
    activityExercise: state.activityExercises.activityExercise,
    fetching: state.activityExercises.fetchingAll,
    error: state.activityExercises.errorAll,
    links: state.activityExercises.links,
  };
};

const mapDispatchToProps = (dispatch) => {
  return {
    getAllActivityExercises: (options) => dispatch(ActivityExerciseActions.activityExerciseAllRequest(options)),
  };
};

export default connect(mapStateToProps, mapDispatchToProps)(ActivityExerciseScreen);
