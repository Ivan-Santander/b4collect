import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import CaloriesExpended from './calories-expended';
import CaloriesExpendedDetail from './calories-expended-detail';
import CaloriesExpendedUpdate from './calories-expended-update';
import CaloriesExpendedDeleteDialog from './calories-expended-delete-dialog';

const CaloriesExpendedRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<CaloriesExpended />} />
    <Route path="new" element={<CaloriesExpendedUpdate />} />
    <Route path=":id">
      <Route index element={<CaloriesExpendedDetail />} />
      <Route path="edit" element={<CaloriesExpendedUpdate />} />
      <Route path="delete" element={<CaloriesExpendedDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default CaloriesExpendedRoutes;
