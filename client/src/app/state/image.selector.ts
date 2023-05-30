// eslint-disable-next-line import/no-extraneous-dependencies
import { createFeatureSelector, createSelector } from '@ngrx/store';
import { ImageState } from './image.state';

export const selectImageState = createFeatureSelector<ImageState>('id');

export const selectImageId = createSelector(
  selectImageState,
  (state: ImageState) => state.id,
);
