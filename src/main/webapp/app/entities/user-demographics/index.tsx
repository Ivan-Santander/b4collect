import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import UserDemographics from './user-demographics';
import UserDemographicsDetail from './user-demographics-detail';
import UserDemographicsUpdate from './user-demographics-update';
import UserDemographicsDeleteDialog from './user-demographics-delete-dialog';

const UserDemographicsRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<UserDemographics />} />
    <Route path="new" element={<UserDemographicsUpdate />} />
    <Route path=":id">
      <Route index element={<UserDemographicsDetail />} />
      <Route path="edit" element={<UserDemographicsUpdate />} />
      <Route path="delete" element={<UserDemographicsDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default UserDemographicsRoutes;
