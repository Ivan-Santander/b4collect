import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import SleepScores from './sleep-scores';
import SleepScoresDetail from './sleep-scores-detail';
import SleepScoresUpdate from './sleep-scores-update';
import SleepScoresDeleteDialog from './sleep-scores-delete-dialog';

const SleepScoresRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<SleepScores />} />
    <Route path="new" element={<SleepScoresUpdate />} />
    <Route path=":id">
      <Route index element={<SleepScoresDetail />} />
      <Route path="edit" element={<SleepScoresUpdate />} />
      <Route path="delete" element={<SleepScoresDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default SleepScoresRoutes;
