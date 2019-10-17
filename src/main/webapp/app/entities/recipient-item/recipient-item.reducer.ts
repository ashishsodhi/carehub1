import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IRecipientItem, defaultValue } from 'app/shared/model/recipient-item.model';

export const ACTION_TYPES = {
  FETCH_RECIPIENTITEM_LIST: 'recipientItem/FETCH_RECIPIENTITEM_LIST',
  FETCH_RECIPIENTITEM: 'recipientItem/FETCH_RECIPIENTITEM',
  CREATE_RECIPIENTITEM: 'recipientItem/CREATE_RECIPIENTITEM',
  UPDATE_RECIPIENTITEM: 'recipientItem/UPDATE_RECIPIENTITEM',
  DELETE_RECIPIENTITEM: 'recipientItem/DELETE_RECIPIENTITEM',
  RESET: 'recipientItem/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IRecipientItem>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type RecipientItemState = Readonly<typeof initialState>;

// Reducer

export default (state: RecipientItemState = initialState, action): RecipientItemState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_RECIPIENTITEM_LIST):
    case REQUEST(ACTION_TYPES.FETCH_RECIPIENTITEM):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_RECIPIENTITEM):
    case REQUEST(ACTION_TYPES.UPDATE_RECIPIENTITEM):
    case REQUEST(ACTION_TYPES.DELETE_RECIPIENTITEM):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_RECIPIENTITEM_LIST):
    case FAILURE(ACTION_TYPES.FETCH_RECIPIENTITEM):
    case FAILURE(ACTION_TYPES.CREATE_RECIPIENTITEM):
    case FAILURE(ACTION_TYPES.UPDATE_RECIPIENTITEM):
    case FAILURE(ACTION_TYPES.DELETE_RECIPIENTITEM):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_RECIPIENTITEM_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_RECIPIENTITEM):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_RECIPIENTITEM):
    case SUCCESS(ACTION_TYPES.UPDATE_RECIPIENTITEM):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_RECIPIENTITEM):
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

const apiUrl = 'api/recipient-items';

// Actions

export const getEntities: ICrudGetAllAction<IRecipientItem> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_RECIPIENTITEM_LIST,
  payload: axios.get<IRecipientItem>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<IRecipientItem> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_RECIPIENTITEM,
    payload: axios.get<IRecipientItem>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IRecipientItem> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_RECIPIENTITEM,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IRecipientItem> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_RECIPIENTITEM,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IRecipientItem> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_RECIPIENTITEM,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
