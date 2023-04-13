import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import UserBodyInfo from './user-body-info';
import UserBodyInfoDetail from './user-body-info-detail';
import UserBodyInfoUpdate from './user-body-info-update';
import UserBodyInfoDeleteDialog from './user-body-info-delete-dialog';

const UserBodyInfoRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<UserBodyInfo />} />
    <Route path="new" element={<UserBodyInfoUpdate />} />
    <Route path=":id">
      <Route index element={<UserBodyInfoDetail />} />
      <Route path="edit" element={<UserBodyInfoUpdate />} />
      <Route path="delete" element={<UserBodyInfoDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default UserBodyInfoRoutes;
