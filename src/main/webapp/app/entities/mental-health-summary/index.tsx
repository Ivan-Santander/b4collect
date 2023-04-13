import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import MentalHealthSummary from './mental-health-summary';
import MentalHealthSummaryDetail from './mental-health-summary-detail';
import MentalHealthSummaryUpdate from './mental-health-summary-update';
import MentalHealthSummaryDeleteDialog from './mental-health-summary-delete-dialog';

const MentalHealthSummaryRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<MentalHealthSummary />} />
    <Route path="new" element={<MentalHealthSummaryUpdate />} />
    <Route path=":id">
      <Route index element={<MentalHealthSummaryDetail />} />
      <Route path="edit" element={<MentalHealthSummaryUpdate />} />
      <Route path="delete" element={<MentalHealthSummaryDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default MentalHealthSummaryRoutes;
