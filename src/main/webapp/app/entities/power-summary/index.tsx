import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import PowerSummary from './power-summary';
import PowerSummaryDetail from './power-summary-detail';
import PowerSummaryUpdate from './power-summary-update';
import PowerSummaryDeleteDialog from './power-summary-delete-dialog';

const PowerSummaryRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<PowerSummary />} />
    <Route path="new" element={<PowerSummaryUpdate />} />
    <Route path=":id">
      <Route index element={<PowerSummaryDetail />} />
      <Route path="edit" element={<PowerSummaryUpdate />} />
      <Route path="delete" element={<PowerSummaryDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default PowerSummaryRoutes;
