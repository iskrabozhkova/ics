// eslint-disable-next-line import/no-extraneous-dependencies
import { createReducer, on } from '@ngrx/store';
import { setImageId } from './image.actions';
import { initialImageState } from './image.state';

export const imageReducer = createReducer(
  initialImageState,
  on(setImageId, (state, { id }) => ({ ...state, id: id })),
);