import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import NutritionSummary from './nutrition-summary';
import NutritionSummaryDetail from './nutrition-summary-detail';
import NutritionSummaryUpdate from './nutrition-summary-update';
import NutritionSummaryDeleteDialog from './nutrition-summary-delete-dialog';

const NutritionSummaryRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<NutritionSummary />} />
    <Route path="new" element={<NutritionSummaryUpdate />} />
    <Route path=":id">
      <Route index element={<NutritionSummaryDetail />} />
      <Route path="edit" element={<NutritionSummaryUpdate />} />
      <Route path="delete" element={<NutritionSummaryDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default NutritionSummaryRoutes;
