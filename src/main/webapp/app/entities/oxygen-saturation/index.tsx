import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import OxygenSaturation from './oxygen-saturation';
import OxygenSaturationDetail from './oxygen-saturation-detail';
import OxygenSaturationUpdate from './oxygen-saturation-update';
import OxygenSaturationDeleteDialog from './oxygen-saturation-delete-dialog';

const OxygenSaturationRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<OxygenSaturation />} />
    <Route path="new" element={<OxygenSaturationUpdate />} />
    <Route path=":id">
      <Route index element={<OxygenSaturationDetail />} />
      <Route path="edit" element={<OxygenSaturationUpdate />} />
      <Route path="delete" element={<OxygenSaturationDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default OxygenSaturationRoutes;
