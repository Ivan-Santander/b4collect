import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import BloodGlucoseSummary from './blood-glucose-summary';
import BloodGlucoseSummaryDetail from './blood-glucose-summary-detail';
import BloodGlucoseSummaryUpdate from './blood-glucose-summary-update';
import BloodGlucoseSummaryDeleteDialog from './blood-glucose-summary-delete-dialog';

const BloodGlucoseSummaryRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<BloodGlucoseSummary />} />
    <Route path="new" element={<BloodGlucoseSummaryUpdate />} />
    <Route path=":id">
      <Route index element={<BloodGlucoseSummaryDetail />} />
      <Route path="edit" element={<BloodGlucoseSummaryUpdate />} />
      <Route path="delete" element={<BloodGlucoseSummaryDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default BloodGlucoseSummaryRoutes;
