import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import CaloriesBmrSummary from './calories-bmr-summary';
import CaloriesBmrSummaryDetail from './calories-bmr-summary-detail';
import CaloriesBmrSummaryUpdate from './calories-bmr-summary-update';
import CaloriesBmrSummaryDeleteDialog from './calories-bmr-summary-delete-dialog';

const CaloriesBmrSummaryRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<CaloriesBmrSummary />} />
    <Route path="new" element={<CaloriesBmrSummaryUpdate />} />
    <Route path=":id">
      <Route index element={<CaloriesBmrSummaryDetail />} />
      <Route path="edit" element={<CaloriesBmrSummaryUpdate />} />
      <Route path="delete" element={<CaloriesBmrSummaryDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default CaloriesBmrSummaryRoutes;
