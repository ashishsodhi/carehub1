import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IProjectParticipant, defaultValue } from 'app/shared/model/project-participant.model';

export const ACTION_TYPES = {
  FETCH_PROJECTPARTICIPANT_LIST: 'projectParticipant/FETCH_PROJECTPARTICIPANT_LIST',
  FETCH_PROJECTPARTICIPANT: 'projectParticipant/FETCH_PROJECTPARTICIPANT',
  CREATE_PROJECTPARTICIPANT: 'projectParticipant/CREATE_PROJECTPARTICIPANT',
  UPDATE_PROJECTPARTICIPANT: 'projectParticipant/UPDATE_PROJECTPARTICIPANT',
  DELETE_PROJECTPARTICIPANT: 'projectParticipant/DELETE_PROJECTPARTICIPANT',
  RESET: 'projectParticipant/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IProjectParticipant>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type ProjectParticipantState = Readonly<typeof initialState>;

// Reducer

export default (state: ProjectParticipantState = initialState, action): ProjectParticipantState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_PROJECTPARTICIPANT_LIST):
    case REQUEST(ACTION_TYPES.FETCH_PROJECTPARTICIPANT):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_PROJECTPARTICIPANT):
    case REQUEST(ACTION_TYPES.UPDATE_PROJECTPARTICIPANT):
    case REQUEST(ACTION_TYPES.DELETE_PROJECTPARTICIPANT):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_PROJECTPARTICIPANT_LIST):
    case FAILURE(ACTION_TYPES.FETCH_PROJECTPARTICIPANT):
    case FAILURE(ACTION_TYPES.CREATE_PROJECTPARTICIPANT):
    case FAILURE(ACTION_TYPES.UPDATE_PROJECTPARTICIPANT):
    case FAILURE(ACTION_TYPES.DELETE_PROJECTPARTICIPANT):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_PROJECTPARTICIPANT_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_PROJECTPARTICIPANT):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_PROJECTPARTICIPANT):
    case SUCCESS(ACTION_TYPES.UPDATE_PROJECTPARTICIPANT):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_PROJECTPARTICIPANT):
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

const apiUrl = 'api/project-participants';

// Actions

export const getEntities: ICrudGetAllAction<IProjectParticipant> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_PROJECTPARTICIPANT_LIST,
  payload: axios.get<IProjectParticipant>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<IProjectParticipant> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_PROJECTPARTICIPANT,
    payload: axios.get<IProjectParticipant>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IProjectParticipant> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_PROJECTPARTICIPANT,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IProjectParticipant> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_PROJECTPARTICIPANT,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IProjectParticipant> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_PROJECTPARTICIPANT,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
