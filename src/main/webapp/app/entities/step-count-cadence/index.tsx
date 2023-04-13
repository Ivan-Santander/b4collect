import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import StepCountCadence from './step-count-cadence';
import StepCountCadenceDetail from './step-count-cadence-detail';
import StepCountCadenceUpdate from './step-count-cadence-update';
import StepCountCadenceDeleteDialog from './step-count-cadence-delete-dialog';

const StepCountCadenceRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<StepCountCadence />} />
    <Route path="new" element={<StepCountCadenceUpdate />} />
    <Route path=":id">
      <Route index element={<StepCountCadenceDetail />} />
      <Route path="edit" element={<StepCountCadenceUpdate />} />
      <Route path="delete" element={<StepCountCadenceDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default StepCountCadenceRoutes;
