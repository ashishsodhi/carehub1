import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IItemParticipant, defaultValue } from 'app/shared/model/item-participant.model';

export const ACTION_TYPES = {
  FETCH_ITEMPARTICIPANT_LIST: 'itemParticipant/FETCH_ITEMPARTICIPANT_LIST',
  FETCH_ITEMPARTICIPANT: 'itemParticipant/FETCH_ITEMPARTICIPANT',
  CREATE_ITEMPARTICIPANT: 'itemParticipant/CREATE_ITEMPARTICIPANT',
  UPDATE_ITEMPARTICIPANT: 'itemParticipant/UPDATE_ITEMPARTICIPANT',
  DELETE_ITEMPARTICIPANT: 'itemParticipant/DELETE_ITEMPARTICIPANT',
  RESET: 'itemParticipant/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IItemParticipant>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type ItemParticipantState = Readonly<typeof initialState>;

// Reducer

export default (state: ItemParticipantState = initialState, action): ItemParticipantState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_ITEMPARTICIPANT_LIST):
    case REQUEST(ACTION_TYPES.FETCH_ITEMPARTICIPANT):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_ITEMPARTICIPANT):
    case REQUEST(ACTION_TYPES.UPDATE_ITEMPARTICIPANT):
    case REQUEST(ACTION_TYPES.DELETE_ITEMPARTICIPANT):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_ITEMPARTICIPANT_LIST):
    case FAILURE(ACTION_TYPES.FETCH_ITEMPARTICIPANT):
    case FAILURE(ACTION_TYPES.CREATE_ITEMPARTICIPANT):
    case FAILURE(ACTION_TYPES.UPDATE_ITEMPARTICIPANT):
    case FAILURE(ACTION_TYPES.DELETE_ITEMPARTICIPANT):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_ITEMPARTICIPANT_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_ITEMPARTICIPANT):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_ITEMPARTICIPANT):
    case SUCCESS(ACTION_TYPES.UPDATE_ITEMPARTICIPANT):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_ITEMPARTICIPANT):
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

const apiUrl = 'api/item-participants';

// Actions

export const getEntities: ICrudGetAllAction<IItemParticipant> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_ITEMPARTICIPANT_LIST,
  payload: axios.get<IItemParticipant>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<IItemParticipant> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_ITEMPARTICIPANT,
    payload: axios.get<IItemParticipant>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IItemParticipant> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_ITEMPARTICIPANT,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IItemParticipant> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_ITEMPARTICIPANT,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IItemParticipant> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_ITEMPARTICIPANT,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
