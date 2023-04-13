import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import SpeedSummary from './speed-summary';
import SpeedSummaryDetail from './speed-summary-detail';
import SpeedSummaryUpdate from './speed-summary-update';
import SpeedSummaryDeleteDialog from './speed-summary-delete-dialog';

const SpeedSummaryRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<SpeedSummary />} />
    <Route path="new" element={<SpeedSummaryUpdate />} />
    <Route path=":id">
      <Route index element={<SpeedSummaryDetail />} />
      <Route path="edit" element={<SpeedSummaryUpdate />} />
      <Route path="delete" element={<SpeedSummaryDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default SpeedSummaryRoutes;
