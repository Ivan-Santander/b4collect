import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Speed from './speed';
import SpeedDetail from './speed-detail';
import SpeedUpdate from './speed-update';
import SpeedDeleteDialog from './speed-delete-dialog';

const SpeedRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Speed />} />
    <Route path="new" element={<SpeedUpdate />} />
    <Route path=":id">
      <Route index element={<SpeedDetail />} />
      <Route path="edit" element={<SpeedUpdate />} />
      <Route path="delete" element={<SpeedDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default SpeedRoutes;
