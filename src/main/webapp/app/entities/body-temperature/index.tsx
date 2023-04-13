import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import BodyTemperature from './body-temperature';
import BodyTemperatureDetail from './body-temperature-detail';
import BodyTemperatureUpdate from './body-temperature-update';
import BodyTemperatureDeleteDialog from './body-temperature-delete-dialog';

const BodyTemperatureRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<BodyTemperature />} />
    <Route path="new" element={<BodyTemperatureUpdate />} />
    <Route path=":id">
      <Route index element={<BodyTemperatureDetail />} />
      <Route path="edit" element={<BodyTemperatureUpdate />} />
      <Route path="delete" element={<BodyTemperatureDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default BodyTemperatureRoutes;
