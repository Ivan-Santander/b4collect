import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import UserMedicalInfo from './user-medical-info';
import UserMedicalInfoDetail from './user-medical-info-detail';
import UserMedicalInfoUpdate from './user-medical-info-update';
import UserMedicalInfoDeleteDialog from './user-medical-info-delete-dialog';

const UserMedicalInfoRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<UserMedicalInfo />} />
    <Route path="new" element={<UserMedicalInfoUpdate />} />
    <Route path=":id">
      <Route index element={<UserMedicalInfoDetail />} />
      <Route path="edit" element={<UserMedicalInfoUpdate />} />
      <Route path="delete" element={<UserMedicalInfoDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default UserMedicalInfoRoutes;
