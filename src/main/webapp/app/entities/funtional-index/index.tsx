import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import FuntionalIndex from './funtional-index';
import FuntionalIndexDetail from './funtional-index-detail';
import FuntionalIndexUpdate from './funtional-index-update';
import FuntionalIndexDeleteDialog from './funtional-index-delete-dialog';

const FuntionalIndexRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<FuntionalIndex />} />
    <Route path="new" element={<FuntionalIndexUpdate />} />
    <Route path=":id">
      <Route index element={<FuntionalIndexDetail />} />
      <Route path="edit" element={<FuntionalIndexUpdate />} />
      <Route path="delete" element={<FuntionalIndexDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default FuntionalIndexRoutes;
