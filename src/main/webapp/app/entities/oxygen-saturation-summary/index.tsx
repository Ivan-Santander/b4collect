import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import OxygenSaturationSummary from './oxygen-saturation-summary';
import OxygenSaturationSummaryDetail from './oxygen-saturation-summary-detail';
import OxygenSaturationSummaryUpdate from './oxygen-saturation-summary-update';
import OxygenSaturationSummaryDeleteDialog from './oxygen-saturation-summary-delete-dialog';

const OxygenSaturationSummaryRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<OxygenSaturationSummary />} />
    <Route path="new" element={<OxygenSaturationSummaryUpdate />} />
    <Route path=":id">
      <Route index element={<OxygenSaturationSummaryDetail />} />
      <Route path="edit" element={<OxygenSaturationSummaryUpdate />} />
      <Route path="delete" element={<OxygenSaturationSummaryDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default OxygenSaturationSummaryRoutes;
