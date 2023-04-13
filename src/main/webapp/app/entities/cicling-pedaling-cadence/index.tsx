import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import CiclingPedalingCadence from './cicling-pedaling-cadence';
import CiclingPedalingCadenceDetail from './cicling-pedaling-cadence-detail';
import CiclingPedalingCadenceUpdate from './cicling-pedaling-cadence-update';
import CiclingPedalingCadenceDeleteDialog from './cicling-pedaling-cadence-delete-dialog';

const CiclingPedalingCadenceRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<CiclingPedalingCadence />} />
    <Route path="new" element={<CiclingPedalingCadenceUpdate />} />
    <Route path=":id">
      <Route index element={<CiclingPedalingCadenceDetail />} />
      <Route path="edit" element={<CiclingPedalingCadenceUpdate />} />
      <Route path="delete" element={<CiclingPedalingCadenceDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default CiclingPedalingCadenceRoutes;
