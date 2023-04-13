import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import HeartRateBpm from './heart-rate-bpm';
import HeartRateBpmDetail from './heart-rate-bpm-detail';
import HeartRateBpmUpdate from './heart-rate-bpm-update';
import HeartRateBpmDeleteDialog from './heart-rate-bpm-delete-dialog';

const HeartRateBpmRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<HeartRateBpm />} />
    <Route path="new" element={<HeartRateBpmUpdate />} />
    <Route path=":id">
      <Route index element={<HeartRateBpmDetail />} />
      <Route path="edit" element={<HeartRateBpmUpdate />} />
      <Route path="delete" element={<HeartRateBpmDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default HeartRateBpmRoutes;
