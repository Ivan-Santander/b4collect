import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import FuntionalIndexSummary from './funtional-index-summary';
import FuntionalIndexSummaryDetail from './funtional-index-summary-detail';
import FuntionalIndexSummaryUpdate from './funtional-index-summary-update';
import FuntionalIndexSummaryDeleteDialog from './funtional-index-summary-delete-dialog';

const FuntionalIndexSummaryRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<FuntionalIndexSummary />} />
    <Route path="new" element={<FuntionalIndexSummaryUpdate />} />
    <Route path=":id">
      <Route index element={<FuntionalIndexSummaryDetail />} />
      <Route path="edit" element={<FuntionalIndexSummaryUpdate />} />
      <Route path="delete" element={<FuntionalIndexSummaryDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default FuntionalIndexSummaryRoutes;
