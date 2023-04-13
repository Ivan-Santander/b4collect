import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import LocationSample from './location-sample';
import LocationSampleDetail from './location-sample-detail';
import LocationSampleUpdate from './location-sample-update';
import LocationSampleDeleteDialog from './location-sample-delete-dialog';

const LocationSampleRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<LocationSample />} />
    <Route path="new" element={<LocationSampleUpdate />} />
    <Route path=":id">
      <Route index element={<LocationSampleDetail />} />
      <Route path="edit" element={<LocationSampleUpdate />} />
      <Route path="delete" element={<LocationSampleDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default LocationSampleRoutes;
