import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import ActiveMinutes from './active-minutes';
import ActiveMinutesDetail from './active-minutes-detail';
import ActiveMinutesUpdate from './active-minutes-update';
import ActiveMinutesDeleteDialog from './active-minutes-delete-dialog';

const ActiveMinutesRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<ActiveMinutes />} />
    <Route path="new" element={<ActiveMinutesUpdate />} />
    <Route path=":id">
      <Route index element={<ActiveMinutesDetail />} />
      <Route path="edit" element={<ActiveMinutesUpdate />} />
      <Route path="delete" element={<ActiveMinutesDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default ActiveMinutesRoutes;
