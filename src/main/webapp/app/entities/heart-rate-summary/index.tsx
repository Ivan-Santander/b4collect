import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import HeartRateSummary from './heart-rate-summary';
import HeartRateSummaryDetail from './heart-rate-summary-detail';
import HeartRateSummaryUpdate from './heart-rate-summary-update';
import HeartRateSummaryDeleteDialog from './heart-rate-summary-delete-dialog';

const HeartRateSummaryRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<HeartRateSummary />} />
    <Route path="new" element={<HeartRateSummaryUpdate />} />
    <Route path=":id">
      <Route index element={<HeartRateSummaryDetail />} />
      <Route path="edit" element={<HeartRateSummaryUpdate />} />
      <Route path="delete" element={<HeartRateSummaryDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default HeartRateSummaryRoutes;
