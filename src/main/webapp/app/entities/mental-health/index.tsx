import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import MentalHealth from './mental-health';
import MentalHealthDetail from './mental-health-detail';
import MentalHealthUpdate from './mental-health-update';
import MentalHealthDeleteDialog from './mental-health-delete-dialog';

const MentalHealthRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<MentalHealth />} />
    <Route path="new" element={<MentalHealthUpdate />} />
    <Route path=":id">
      <Route index element={<MentalHealthDetail />} />
      <Route path="edit" element={<MentalHealthUpdate />} />
      <Route path="delete" element={<MentalHealthDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default MentalHealthRoutes;
