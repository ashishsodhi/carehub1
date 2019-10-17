import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IRecipient, defaultValue } from 'app/shared/model/recipient.model';

export const ACTION_TYPES = {
  FETCH_RECIPIENT_LIST: 'recipient/FETCH_RECIPIENT_LIST',
  FETCH_RECIPIENT: 'recipient/FETCH_RECIPIENT',
  CREATE_RECIPIENT: 'recipient/CREATE_RECIPIENT',
  UPDATE_RECIPIENT: 'recipient/UPDATE_RECIPIENT',
  DELETE_RECIPIENT: 'recipient/DELETE_RECIPIENT',
  RESET: 'recipient/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IRecipient>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type RecipientState = Readonly<typeof initialState>;

// Reducer

export default (state: RecipientState = initialState, action): RecipientState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_RECIPIENT_LIST):
    case REQUEST(ACTION_TYPES.FETCH_RECIPIENT):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_RECIPIENT):
    case REQUEST(ACTION_TYPES.UPDATE_RECIPIENT):
    case REQUEST(ACTION_TYPES.DELETE_RECIPIENT):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_RECIPIENT_LIST):
    case FAILURE(ACTION_TYPES.FETCH_RECIPIENT):
    case FAILURE(ACTION_TYPES.CREATE_RECIPIENT):
    case FAILURE(ACTION_TYPES.UPDATE_RECIPIENT):
    case FAILURE(ACTION_TYPES.DELETE_RECIPIENT):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_RECIPIENT_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_RECIPIENT):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_RECIPIENT):
    case SUCCESS(ACTION_TYPES.UPDATE_RECIPIENT):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_RECIPIENT):
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

const apiUrl = 'api/recipients';

// Actions

export const getEntities: ICrudGetAllAction<IRecipient> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_RECIPIENT_LIST,
  payload: axios.get<IRecipient>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<IRecipient> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_RECIPIENT,
    payload: axios.get<IRecipient>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IRecipient> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_RECIPIENT,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IRecipient> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_RECIPIENT,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IRecipient> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_RECIPIENT,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
