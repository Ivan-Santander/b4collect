import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import BodyFatPercentage from './body-fat-percentage';
import BodyFatPercentageDetail from './body-fat-percentage-detail';
import BodyFatPercentageUpdate from './body-fat-percentage-update';
import BodyFatPercentageDeleteDialog from './body-fat-percentage-delete-dialog';

const BodyFatPercentageRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<BodyFatPercentage />} />
    <Route path="new" element={<BodyFatPercentageUpdate />} />
    <Route path=":id">
      <Route index element={<BodyFatPercentageDetail />} />
      <Route path="edit" element={<BodyFatPercentageUpdate />} />
      <Route path="delete" element={<BodyFatPercentageDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default BodyFatPercentageRoutes;
