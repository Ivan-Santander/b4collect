import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import SleepSegment from './sleep-segment';
import SleepSegmentDetail from './sleep-segment-detail';
import SleepSegmentUpdate from './sleep-segment-update';
import SleepSegmentDeleteDialog from './sleep-segment-delete-dialog';

const SleepSegmentRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<SleepSegment />} />
    <Route path="new" element={<SleepSegmentUpdate />} />
    <Route path=":id">
      <Route index element={<SleepSegmentDetail />} />
      <Route path="edit" element={<SleepSegmentUpdate />} />
      <Route path="delete" element={<SleepSegmentDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default SleepSegmentRoutes;
