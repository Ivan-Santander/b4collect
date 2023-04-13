import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import BodyFatPercentageSummary from './body-fat-percentage-summary';
import BodyFatPercentageSummaryDetail from './body-fat-percentage-summary-detail';
import BodyFatPercentageSummaryUpdate from './body-fat-percentage-summary-update';
import BodyFatPercentageSummaryDeleteDialog from './body-fat-percentage-summary-delete-dialog';

const BodyFatPercentageSummaryRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<BodyFatPercentageSummary />} />
    <Route path="new" element={<BodyFatPercentageSummaryUpdate />} />
    <Route path=":id">
      <Route index element={<BodyFatPercentageSummaryDetail />} />
      <Route path="edit" element={<BodyFatPercentageSummaryUpdate />} />
      <Route path="delete" element={<BodyFatPercentageSummaryDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default BodyFatPercentageSummaryRoutes;
