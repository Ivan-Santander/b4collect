import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Height from './height';
import HeightDetail from './height-detail';
import HeightUpdate from './height-update';
import HeightDeleteDialog from './height-delete-dialog';

const HeightRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Height />} />
    <Route path="new" element={<HeightUpdate />} />
    <Route path=":id">
      <Route index element={<HeightDetail />} />
      <Route path="edit" element={<HeightUpdate />} />
      <Route path="delete" element={<HeightDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default HeightRoutes;
