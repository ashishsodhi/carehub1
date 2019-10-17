import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IConcerns, defaultValue } from 'app/shared/model/concerns.model';

export const ACTION_TYPES = {
  FETCH_CONCERNS_LIST: 'concerns/FETCH_CONCERNS_LIST',
  FETCH_CONCERNS: 'concerns/FETCH_CONCERNS',
  CREATE_CONCERNS: 'concerns/CREATE_CONCERNS',
  UPDATE_CONCERNS: 'concerns/UPDATE_CONCERNS',
  DELETE_CONCERNS: 'concerns/DELETE_CONCERNS',
  RESET: 'concerns/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IConcerns>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type ConcernsState = Readonly<typeof initialState>;

// Reducer

export default (state: ConcernsState = initialState, action): ConcernsState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_CONCERNS_LIST):
    case REQUEST(ACTION_TYPES.FETCH_CONCERNS):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_CONCERNS):
    case REQUEST(ACTION_TYPES.UPDATE_CONCERNS):
    case REQUEST(ACTION_TYPES.DELETE_CONCERNS):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_CONCERNS_LIST):
    case FAILURE(ACTION_TYPES.FETCH_CONCERNS):
    case FAILURE(ACTION_TYPES.CREATE_CONCERNS):
    case FAILURE(ACTION_TYPES.UPDATE_CONCERNS):
    case FAILURE(ACTION_TYPES.DELETE_CONCERNS):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_CONCERNS_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_CONCERNS):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_CONCERNS):
    case SUCCESS(ACTION_TYPES.UPDATE_CONCERNS):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_CONCERNS):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {}
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState
      };
    default:
      return state;
  }
};

const apiUrl = 'api/concerns';

// Actions

export const getEntities: ICrudGetAllAction<IConcerns> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_CONCERNS_LIST,
  payload: axios.get<IConcerns>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<IConcerns> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_CONCERNS,
    payload: axios.get<IConcerns>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IConcerns> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_CONCERNS,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IConcerns> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_CONCERNS,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IConcerns> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_CONCERNS,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
