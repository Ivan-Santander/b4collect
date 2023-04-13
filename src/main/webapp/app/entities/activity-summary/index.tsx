import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import ActivitySummary from './activity-summary';
import ActivitySummaryDetail from './activity-summary-detail';
import ActivitySummaryUpdate from './activity-summary-update';
import ActivitySummaryDeleteDialog from './activity-summary-delete-dialog';

const ActivitySummaryRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<ActivitySummary />} />
    <Route path="new" element={<ActivitySummaryUpdate />} />
    <Route path=":id">
      <Route index element={<ActivitySummaryDetail />} />
      <Route path="edit" element={<ActivitySummaryUpdate />} />
      <Route path="delete" element={<ActivitySummaryDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default ActivitySummaryRoutes;
