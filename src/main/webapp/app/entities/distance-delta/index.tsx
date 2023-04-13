import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import DistanceDelta from './distance-delta';
import DistanceDeltaDetail from './distance-delta-detail';
import DistanceDeltaUpdate from './distance-delta-update';
import DistanceDeltaDeleteDialog from './distance-delta-delete-dialog';

const DistanceDeltaRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<DistanceDelta />} />
    <Route path="new" element={<DistanceDeltaUpdate />} />
    <Route path=":id">
      <Route index element={<DistanceDeltaDetail />} />
      <Route path="edit" element={<DistanceDeltaUpdate />} />
      <Route path="delete" element={<DistanceDeltaDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default DistanceDeltaRoutes;
