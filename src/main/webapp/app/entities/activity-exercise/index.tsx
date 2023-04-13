import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import ActivityExercise from './activity-exercise';
import ActivityExerciseDetail from './activity-exercise-detail';
import ActivityExerciseUpdate from './activity-exercise-update';
import ActivityExerciseDeleteDialog from './activity-exercise-delete-dialog';

const ActivityExerciseRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<ActivityExercise />} />
    <Route path="new" element={<ActivityExerciseUpdate />} />
    <Route path=":id">
      <Route index element={<ActivityExerciseDetail />} />
      <Route path="edit" element={<ActivityExerciseUpdate />} />
      <Route path="delete" element={<ActivityExerciseDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default ActivityExerciseRoutes;
