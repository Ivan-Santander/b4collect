import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import CyclingWheelRevolution from './cycling-wheel-revolution';
import CyclingWheelRevolutionDetail from './cycling-wheel-revolution-detail';
import CyclingWheelRevolutionUpdate from './cycling-wheel-revolution-update';
import CyclingWheelRevolutionDeleteDialog from './cycling-wheel-revolution-delete-dialog';

const CyclingWheelRevolutionRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<CyclingWheelRevolution />} />
    <Route path="new" element={<CyclingWheelRevolutionUpdate />} />
    <Route path=":id">
      <Route index element={<CyclingWheelRevolutionDetail />} />
      <Route path="edit" element={<CyclingWheelRevolutionUpdate />} />
      <Route path="delete" element={<CyclingWheelRevolutionDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default CyclingWheelRevolutionRoutes;
