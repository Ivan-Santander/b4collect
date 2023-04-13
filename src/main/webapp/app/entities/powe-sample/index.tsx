import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import PoweSample from './powe-sample';
import PoweSampleDetail from './powe-sample-detail';
import PoweSampleUpdate from './powe-sample-update';
import PoweSampleDeleteDialog from './powe-sample-delete-dialog';

const PoweSampleRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<PoweSample />} />
    <Route path="new" element={<PoweSampleUpdate />} />
    <Route path=":id">
      <Route index element={<PoweSampleDetail />} />
      <Route path="edit" element={<PoweSampleUpdate />} />
      <Route path="delete" element={<PoweSampleDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default PoweSampleRoutes;
