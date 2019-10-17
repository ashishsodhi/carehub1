import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IProjectTemplate, defaultValue } from 'app/shared/model/project-template.model';

export const ACTION_TYPES = {
  FETCH_PROJECTTEMPLATE_LIST: 'projectTemplate/FETCH_PROJECTTEMPLATE_LIST',
  FETCH_PROJECTTEMPLATE: 'projectTemplate/FETCH_PROJECTTEMPLATE',
  CREATE_PROJECTTEMPLATE: 'projectTemplate/CREATE_PROJECTTEMPLATE',
  UPDATE_PROJECTTEMPLATE: 'projectTemplate/UPDATE_PROJECTTEMPLATE',
  DELETE_PROJECTTEMPLATE: 'projectTemplate/DELETE_PROJECTTEMPLATE',
  RESET: 'projectTemplate/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IProjectTemplate>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type ProjectTemplateState = Readonly<typeof initialState>;

// Reducer

export default (state: ProjectTemplateState = initialState, action): ProjectTemplateState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_PROJECTTEMPLATE_LIST):
    case REQUEST(ACTION_TYPES.FETCH_PROJECTTEMPLATE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_PROJECTTEMPLATE):
    case REQUEST(ACTION_TYPES.UPDATE_PROJECTTEMPLATE):
    case REQUEST(ACTION_TYPES.DELETE_PROJECTTEMPLATE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_PROJECTTEMPLATE_LIST):
    case FAILURE(ACTION_TYPES.FETCH_PROJECTTEMPLATE):
    case FAILURE(ACTION_TYPES.CREATE_PROJECTTEMPLATE):
    case FAILURE(ACTION_TYPES.UPDATE_PROJECTTEMPLATE):
    case FAILURE(ACTION_TYPES.DELETE_PROJECTTEMPLATE):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_PROJECTTEMPLATE_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_PROJECTTEMPLATE):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_PROJECTTEMPLATE):
    case SUCCESS(ACTION_TYPES.UPDATE_PROJECTTEMPLATE):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_PROJECTTEMPLATE):
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

const apiUrl = 'api/project-templates';

// Actions

export const getEntities: ICrudGetAllAction<IProjectTemplate> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_PROJECTTEMPLATE_LIST,
  payload: axios.get<IProjectTemplate>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<IProjectTemplate> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_PROJECTTEMPLATE,
    payload: axios.get<IProjectTemplate>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IProjectTemplate> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_PROJECTTEMPLATE,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IProjectTemplate> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_PROJECTTEMPLATE,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IProjectTemplate> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_PROJECTTEMPLATE,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
