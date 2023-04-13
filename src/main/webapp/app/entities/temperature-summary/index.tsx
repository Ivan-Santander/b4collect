import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import TemperatureSummary from './temperature-summary';
import TemperatureSummaryDetail from './temperature-summary-detail';
import TemperatureSummaryUpdate from './temperature-summary-update';
import TemperatureSummaryDeleteDialog from './temperature-summary-delete-dialog';

const TemperatureSummaryRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<TemperatureSummary />} />
    <Route path="new" element={<TemperatureSummaryUpdate />} />
    <Route path=":id">
      <Route index element={<TemperatureSummaryDetail />} />
      <Route path="edit" element={<TemperatureSummaryUpdate />} />
      <Route path="delete" element={<TemperatureSummaryDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default TemperatureSummaryRoutes;
