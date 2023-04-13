import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import HeartMinutes from './heart-minutes';
import HeartMinutesDetail from './heart-minutes-detail';
import HeartMinutesUpdate from './heart-minutes-update';
import HeartMinutesDeleteDialog from './heart-minutes-delete-dialog';

const HeartMinutesRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<HeartMinutes />} />
    <Route path="new" element={<HeartMinutesUpdate />} />
    <Route path=":id">
      <Route index element={<HeartMinutesDetail />} />
      <Route path="edit" element={<HeartMinutesUpdate />} />
      <Route path="delete" element={<HeartMinutesDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default HeartMinutesRoutes;
