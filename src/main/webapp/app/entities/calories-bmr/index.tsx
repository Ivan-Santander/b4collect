import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import CaloriesBMR from './calories-bmr';
import CaloriesBMRDetail from './calories-bmr-detail';
import CaloriesBMRUpdate from './calories-bmr-update';
import CaloriesBMRDeleteDialog from './calories-bmr-delete-dialog';

const CaloriesBMRRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<CaloriesBMR />} />
    <Route path="new" element={<CaloriesBMRUpdate />} />
    <Route path=":id">
      <Route index element={<CaloriesBMRDetail />} />
      <Route path="edit" element={<CaloriesBMRUpdate />} />
      <Route path="delete" element={<CaloriesBMRDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default CaloriesBMRRoutes;
