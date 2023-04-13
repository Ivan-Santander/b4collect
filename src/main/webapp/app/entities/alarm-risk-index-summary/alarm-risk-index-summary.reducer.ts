import axios from 'axios';
import { createAsyncThunk, isFulfilled, isPending, isRejected } from '@reduxjs/toolkit';
import { loadMoreDataWhenScrolled, parseHeaderForLinks } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { IQueryParams, createEntitySlice, EntityState, serializeAxiosError } from 'app/shared/reducers/reducer.utils';
import { IAlarmRiskIndexSummary, defaultValue } from 'app/shared/model/alarm-risk-index-summary.model';

const initialState: EntityState<IAlarmRiskIndexSummary> = {
  loading: false,
  errorMessage: null,
  entities: [],
  entity: defaultValue,
  links: { next: 0 },
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

const apiUrl = 'api/alarm-risk-index-summaries';

// Actions

export const getEntities = createAsyncThunk('alarmRiskIndexSummary/fetch_entity_list', async ({ page, size, sort }: IQueryParams) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}&` : '?'}cacheBuster=${new Date().getTime()}`;
  return axios.get<IAlarmRiskIndexSummary[]>(requestUrl);
});

export const getEntity = createAsyncThunk(
  'alarmRiskIndexSummary/fetch_entity',
  async (id: string | number) => {
    const requestUrl = `${apiUrl}/${id}`;
    return axios.get<IAlarmRiskIndexSummary>(requestUrl);
  },
  { serializeError: serializeAxiosError }
);

export const createEntity = createAsyncThunk(
  'alarmRiskIndexSummary/create_entity',
  async (entity: IAlarmRiskIndexSummary, thunkAPI) => {
    return axios.post<IAlarmRiskIndexSummary>(apiUrl, cleanEntity(entity));
  },
  { serializeError: serializeAxiosError }
);

export const updateEntity = createAsyncThunk(
  'alarmRiskIndexSummary/update_entity',
  async (entity: IAlarmRiskIndexSummary, thunkAPI) => {
    return axios.put<IAlarmRiskIndexSummary>(`${apiUrl}/${entity.id}`, cleanEntity(entity));
  },
  { serializeError: serializeAxiosError }
);

export const partialUpdateEntity = createAsyncThunk(
  'alarmRiskIndexSummary/partial_update_entity',
  async (entity: IAlarmRiskIndexSummary, thunkAPI) => {
    return axios.patch<IAlarmRiskIndexSummary>(`${apiUrl}/${entity.id}`, cleanEntity(entity));
  },
  { serializeError: serializeAxiosError }
);

export const deleteEntity = createAsyncThunk(
  'alarmRiskIndexSummary/delete_entity',
  async (id: string | number, thunkAPI) => {
    const requestUrl = `${apiUrl}/${id}`;
    return await axios.delete<IAlarmRiskIndexSummary>(requestUrl);
  },
  { serializeError: serializeAxiosError }
);

// slice

export const AlarmRiskIndexSummarySlice = createEntitySlice({
  name: 'alarmRiskIndexSummary',
  initialState,
  extraReducers(builder) {
    builder
      .addCase(getEntity.fulfilled, (state, action) => {
        state.loading = false;
        state.entity = action.payload.data;
      })
      .addCase(deleteEntity.fulfilled, state => {
        state.updating = false;
        state.updateSuccess = true;
        state.entity = {};
      })
      .addMatcher(isFulfilled(getEntities), (state, action) => {
        const { data, headers } = action.payload;
        const links = parseHeaderForLinks(headers.link);

        return {
          ...state,
          loading: false,
          links,
          entities: loadMoreDataWhenScrolled(state.entities, data, links),
          totalItems: parseInt(headers['x-total-count'], 10),
        };
      })
      .addMatcher(isFulfilled(createEntity, updateEntity, partialUpdateEntity), (state, action) => {
        state.updating = false;
        state.loading = false;
        state.updateSuccess = true;
        state.entity = action.payload.data;
      })
      .addMatcher(isPending(getEntities, getEntity), state => {
        state.errorMessage = null;
        state.updateSuccess = false;
        state.loading = true;
      })
      .addMatcher(isPending(createEntity, updateEntity, partialUpdateEntity, deleteEntity), state => {
        state.errorMessage = null;
        state.updateSuccess = false;
        state.updating = true;
      });
  },
});

export const { reset } = AlarmRiskIndexSummarySlice.actions;

// Reducer
export default AlarmRiskIndexSummarySlice.reducer;
