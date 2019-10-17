import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IResponsibilities, defaultValue } from 'app/shared/model/responsibilities.model';

export const ACTION_TYPES = {
  FETCH_RESPONSIBILITIES_LIST: 'responsibilities/FETCH_RESPONSIBILITIES_LIST',
  FETCH_RESPONSIBILITIES: 'responsibilities/FETCH_RESPONSIBILITIES',
  CREATE_RESPONSIBILITIES: 'responsibilities/CREATE_RESPONSIBILITIES',
  UPDATE_RESPONSIBILITIES: 'responsibilities/UPDATE_RESPONSIBILITIES',
  DELETE_RESPONSIBILITIES: 'responsibilities/DELETE_RESPONSIBILITIES',
  RESET: 'responsibilities/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IResponsibilities>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type ResponsibilitiesState = Readonly<typeof initialState>;

// Reducer

export default (state: ResponsibilitiesState = initialState, action): ResponsibilitiesState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_RESPONSIBILITIES_LIST):
    case REQUEST(ACTION_TYPES.FETCH_RESPONSIBILITIES):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_RESPONSIBILITIES):
    case REQUEST(ACTION_TYPES.UPDATE_RESPONSIBILITIES):
    case REQUEST(ACTION_TYPES.DELETE_RESPONSIBILITIES):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_RESPONSIBILITIES_LIST):
    case FAILURE(ACTION_TYPES.FETCH_RESPONSIBILITIES):
    case FAILURE(ACTION_TYPES.CREATE_RESPONSIBILITIES):
    case FAILURE(ACTION_TYPES.UPDATE_RESPONSIBILITIES):
    case FAILURE(ACTION_TYPES.DELETE_RESPONSIBILITIES):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_RESPONSIBILITIES_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_RESPONSIBILITIES):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_RESPONSIBILITIES):
    case SUCCESS(ACTION_TYPES.UPDATE_RESPONSIBILITIES):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_RESPONSIBILITIES):
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

const apiUrl = 'api/responsibilities';

// Actions

export const getEntities: ICrudGetAllAction<IResponsibilities> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_RESPONSIBILITIES_LIST,
  payload: axios.get<IResponsibilities>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<IResponsibilities> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_RESPONSIBILITIES,
    payload: axios.get<IResponsibilities>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IResponsibilities> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_RESPONSIBILITIES,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IResponsibilities> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_RESPONSIBILITIES,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IResponsibilities> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_RESPONSIBILITIES,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
