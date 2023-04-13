import React from 'react';
import { ActivityIndicator, ScrollView, Text, View } from 'react-native';
import { connect } from 'react-redux';
import { useFocusEffect } from '@react-navigation/native';

import HeightActions from './height.reducer';
import RoundedButton from '../../../shared/components/rounded-button/rounded-button';
import HeightDeleteModal from './height-delete-modal';
import styles from './height-styles';

function HeightDetailScreen(props) {
  const { route, getHeight, navigation, height, fetching, error } = props;
  const [deleteModalVisible, setDeleteModalVisible] = React.useState(false);
  // prevents display of stale reducer data
  const entityId = height?.id ?? null;
  const routeEntityId = route.params?.entityId ?? null;
  const correctEntityLoaded = routeEntityId && entityId && routeEntityId.toString() === entityId.toString();

  useFocusEffect(
    React.useCallback(() => {
      if (!routeEntityId) {
        navigation.navigate('Height');
      } else {
        setDeleteModalVisible(false);
        getHeight(routeEntityId);
      }
    }, [routeEntityId, getHeight, navigation]),
  );

  if (!entityId && !fetching && error) {
    return (
      <View style={styles.loading}>
        <Text>Something went wrong fetching the Height.</Text>
      </View>
    );
  }
  if (!entityId || fetching || !correctEntityLoaded) {
    return (
      <View style={styles.loading}>
        <ActivityIndicator size="large" />
      </View>
    );
  }
  return (
    <ScrollView style={styles.container} contentContainerStyle={styles.paddedScrollView} testID="heightDetailScrollView">
      <Text style={styles.label}>Id:</Text>
      <Text>{height.id}</Text>
      {/* UsuarioId Field */}
      <Text style={styles.label}>UsuarioId:</Text>
      <Text testID="usuarioId">{height.usuarioId}</Text>
      {/* EmpresaId Field */}
      <Text style={styles.label}>EmpresaId:</Text>
      <Text testID="empresaId">{height.empresaId}</Text>
      {/* FieldHeight Field */}
      <Text style={styles.label}>FieldHeight:</Text>
      <Text testID="fieldHeight">{height.fieldHeight}</Text>
      {/* EndTime Field */}
      <Text style={styles.label}>EndTime:</Text>
      <Text testID="endTime">{String(height.endTime)}</Text>

      <View style={styles.entityButtons}>
        <RoundedButton
          text="Edit"
          onPress={() => navigation.navigate('HeightEdit', { entityId })}
          accessibilityLabel={'Height Edit Button'}
          testID="heightEditButton"
        />
        <RoundedButton
          text="Delete"
          onPress={() => setDeleteModalVisible(true)}
          accessibilityLabel={'Height Delete Button'}
          testID="heightDeleteButton"
        />
        {deleteModalVisible && (
          <HeightDeleteModal
            navigation={navigation}
            visible={deleteModalVisible}
            setVisible={setDeleteModalVisible}
            entity={height}
            testID="heightDeleteModal"
          />
        )}
      </View>
    </ScrollView>
  );
}

const mapStateToProps = (state) => {
  return {
    height: state.heights.height,
    error: state.heights.errorOne,
    fetching: state.heights.fetchingOne,
    deleting: state.heights.deleting,
    errorDeleting: state.heights.errorDeleting,
  };
};

const mapDispatchToProps = (dispatch) => {
  return {
    getHeight: (id) => dispatch(HeightActions.heightRequest(id)),
    getAllHeights: (options) => dispatch(HeightActions.heightAllRequest(options)),
    deleteHeight: (id) => dispatch(HeightActions.heightDeleteRequest(id)),
    resetHeights: () => dispatch(HeightActions.heightReset()),
  };
};

export default connect(mapStateToProps, mapDispatchToProps)(HeightDetailScreen);
