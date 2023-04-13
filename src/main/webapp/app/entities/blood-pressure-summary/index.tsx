import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import BloodPressureSummary from './blood-pressure-summary';
import BloodPressureSummaryDetail from './blood-pressure-summary-detail';
import BloodPressureSummaryUpdate from './blood-pressure-summary-update';
import BloodPressureSummaryDeleteDialog from './blood-pressure-summary-delete-dialog';

const BloodPressureSummaryRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<BloodPressureSummary />} />
    <Route path="new" element={<BloodPressureSummaryUpdate />} />
    <Route path=":id">
      <Route index element={<BloodPressureSummaryDetail />} />
      <Route path="edit" element={<BloodPressureSummaryUpdate />} />
      <Route path="delete" element={<BloodPressureSummaryDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default BloodPressureSummaryRoutes;
