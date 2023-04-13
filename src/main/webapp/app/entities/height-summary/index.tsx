import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import HeightSummary from './height-summary';
import HeightSummaryDetail from './height-summary-detail';
import HeightSummaryUpdate from './height-summary-update';
import HeightSummaryDeleteDialog from './height-summary-delete-dialog';

const HeightSummaryRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<HeightSummary />} />
    <Route path="new" element={<HeightSummaryUpdate />} />
    <Route path=":id">
      <Route index element={<HeightSummaryDetail />} />
      <Route path="edit" element={<HeightSummaryUpdate />} />
      <Route path="delete" element={<HeightSummaryDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default HeightSummaryRoutes;
