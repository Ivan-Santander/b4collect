import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import AlarmRiskIndexSummary from './alarm-risk-index-summary';
import AlarmRiskIndexSummaryDetail from './alarm-risk-index-summary-detail';
import AlarmRiskIndexSummaryUpdate from './alarm-risk-index-summary-update';
import AlarmRiskIndexSummaryDeleteDialog from './alarm-risk-index-summary-delete-dialog';

const AlarmRiskIndexSummaryRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<AlarmRiskIndexSummary />} />
    <Route path="new" element={<AlarmRiskIndexSummaryUpdate />} />
    <Route path=":id">
      <Route index element={<AlarmRiskIndexSummaryDetail />} />
      <Route path="edit" element={<AlarmRiskIndexSummaryUpdate />} />
      <Route path="delete" element={<AlarmRiskIndexSummaryDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default AlarmRiskIndexSummaryRoutes;
