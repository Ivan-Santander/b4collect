import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import StepCountDelta from './step-count-delta';
import StepCountDeltaDetail from './step-count-delta-detail';
import StepCountDeltaUpdate from './step-count-delta-update';
import StepCountDeltaDeleteDialog from './step-count-delta-delete-dialog';

const StepCountDeltaRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<StepCountDelta />} />
    <Route path="new" element={<StepCountDeltaUpdate />} />
    <Route path=":id">
      <Route index element={<StepCountDeltaDetail />} />
      <Route path="edit" element={<StepCountDeltaUpdate />} />
      <Route path="delete" element={<StepCountDeltaDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default StepCountDeltaRoutes;
