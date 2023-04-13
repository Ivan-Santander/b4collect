import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import WeightSummary from './weight-summary';
import WeightSummaryDetail from './weight-summary-detail';
import WeightSummaryUpdate from './weight-summary-update';
import WeightSummaryDeleteDialog from './weight-summary-delete-dialog';

const WeightSummaryRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<WeightSummary />} />
    <Route path="new" element={<WeightSummaryUpdate />} />
    <Route path=":id">
      <Route index element={<WeightSummaryDetail />} />
      <Route path="edit" element={<WeightSummaryUpdate />} />
      <Route path="delete" element={<WeightSummaryDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default WeightSummaryRoutes;
